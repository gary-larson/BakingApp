package mobi.thalic.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.jetbrains.annotations.NotNull;

import mobi.thalic.bakingapp.databinding.FragmentExoPlayerBinding;
import mobi.thalic.bakingapp.viewmodel.BakingViewModel;

/**
 * Class to handle exo player fragment
 */
public class ExoPlayerFragment extends Fragment implements ExoPlayer.EventListener {
    // Declare constants
    private static final String EXO_PLAYER_PLAY_WHEN_READY = "PlayWhenReady";
    private static final String EXO_PLAYER_POSITION = "PlayerPosition";
    private static final String URL = "url";
    private static final String TITLE = "Title";
    private static final String TAG = ExoPlayerFragment.class.getSimpleName();
    // Declare variables
    private FragmentExoPlayerBinding binding;
    private BakingActivity mBakingActivity;
    BakingViewModel mBakingViewModel;
    private boolean mPlayWhenReady;
    private long mPlayerPosition;
    private String mTitle;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private String mUrl;


    /**
     * Default constructor
     */
    public ExoPlayerFragment() {}

    /**
     * Method to create and inflate exoPlayer fragment
     * @param inflater to use
     * @param container for view
     * @param savedInstanceState to save state
     * @return created view
     */
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExoPlayerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mBakingActivity = (BakingActivity) getActivity();
        // initialize variables
        mBakingViewModel = new ViewModelProvider(requireActivity()).get(BakingViewModel.class);
        mPlayerView = binding.playerView;
        mUrl = "";
        if (savedInstanceState != null) {
            mPlayWhenReady = savedInstanceState.getBoolean(EXO_PLAYER_PLAY_WHEN_READY);
            mPlayerPosition = savedInstanceState.getLong(EXO_PLAYER_POSITION);
            mUrl = savedInstanceState.getString((URL));
            mTitle = savedInstanceState.getString(TITLE);
            mBakingActivity.setTitle(mTitle);

        } else {
            mPlayerPosition = 0;
            mPlayWhenReady = true;
        }
        // Initialize the Media Session.
        initializeMediaSession();
        // set baking steps observer
        mBakingViewModel.getLiveDataBakingStep().observe(getViewLifecycleOwner(), newBakingStep -> {
            // test for data
            if (newBakingStep != null) {
                if (mExoPlayer != null) {
                    releasePlayer();
                }
                // initialize variables
                String url = "";
                // get url if one exists
                if (!TextUtils.isEmpty(newBakingStep.getVideoURL())) {
                    url = newBakingStep.getVideoURL();
                } else if (!TextUtils.isEmpty(newBakingStep.getThumbnailURL())) {
                    url = newBakingStep.getThumbnailURL();
                }
                // test for url
                if (!TextUtils.isEmpty(url)) {
                    // test if it is same as last time created
                    if (!mUrl.equals(url)) {
                        mUrl = url;
                        mPlayerPosition = 0;
                    }
                } else {
                    // reset if no url
                    mUrl = "";
                    mPlayerPosition = 0;
                }
                // check for two pane
                if (getResources().getBoolean(R.bool.is_two_pane)) {
                    // get title
                    mTitle = mBakingViewModel.getRecipeName() + " - " +
                            newBakingStep.getShortDescription();
                } else {
                    // get title
                    mTitle = newBakingStep.getShortDescription();
                }
                // set title
                mBakingActivity.setTitle(mTitle);
                // test for video url
                if (!TextUtils.isEmpty(mUrl)) {
                    // Initialize the player.
                    initializePlayer(Uri.parse(mUrl));
                    // set visibility
                    binding.playerView.setVisibility(View.VISIBLE);
                    binding.tvNoVideoMessage.setVisibility(View.GONE);
                    // test for full screen mode
                    checkFullScreen();
                } else {
                    // test for full sceer without video
                    if (getResources().getBoolean(R.bool.is_landscape) &&
                            !getResources().getBoolean(R.bool.is_two_pane)) {
                        // set visibility
                        binding.playerView.setVisibility(View.GONE);
                        binding.tvNoVideoMessage.setVisibility(View.GONE);
                    } else {
                        // set visibility
                        binding.playerView.setVisibility(View.GONE);
                        binding.tvNoVideoMessage.setVisibility(View.VISIBLE);
                    }
                    if (mBakingActivity != null && mBakingActivity.getSupportActionBar() != null) {
                        // show title toolbar
                        mBakingActivity.getSupportActionBar().show();
                    }
                }
            }
        });
        return view;
    }

    /**
     * Method to check for full screen and set visibility as appropriate
     */
    private void checkFullScreen() {
        if (getResources().getBoolean(R.bool.is_landscape) &&
                !getResources().getBoolean((R.bool.is_two_pane))) {
            if (mBakingActivity != null && mBakingActivity.getSupportActionBar() != null) {
                // hide title toolbar
                mBakingActivity.getSupportActionBar().hide();
            }
        } else {
            if (mBakingActivity != null && mBakingActivity.getSupportActionBar() != null) {
                // show title toolbar
                mBakingActivity.getSupportActionBar().show();
            }
        }
    }

    /**
     * Method to save state
     * @param outState to save
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXO_PLAYER_PLAY_WHEN_READY, mPlayWhenReady);
        outState.putLong(EXO_PLAYER_POSITION, mPlayerPosition);
        outState.putString(URL, mUrl);
        outState.putString(TITLE, mTitle);
    }

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(mBakingActivity, TAG);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS);
        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new ExoPlayerFragment.MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create the instance of the ExoPlayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(mBakingActivity, trackSelector,
                    loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(mBakingActivity, getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    mBakingActivity, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            if (mPlayerPosition > 0) {
                mExoPlayer.seekTo(mPlayerPosition);
            }
            mExoPlayer.setPlayWhenReady(mPlayWhenReady);
        }
    }

    /**
     * Release ExoPlayer
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if (!TextUtils.isEmpty(mUrl)) {
                initializePlayer(Uri.parse(mUrl));
            }
        }
    }

    /**
     * Method to suspend fragment
     */
    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mPlayerPosition = mExoPlayer.getCurrentPosition();
        }
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    /**
     * Method to reset fragment
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mExoPlayer != null) {
            if (mPlayerPosition > 0) {
                mExoPlayer.seekTo(mPlayerPosition);
            }
            mExoPlayer.setPlayWhenReady(mPlayWhenReady);
        }
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            if (!TextUtils.isEmpty(mUrl)) {
                initializePlayer(Uri.parse(mUrl));
            }
        }
    }

    /**
     * Method to clean up exo player
     */
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    /**
     * Method to release resources
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaSession.setActive(false);
        binding = null;
    }
    // required overrides for Exo player
    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if (playbackState == ExoPlayer.STATE_ENDED){
            mPlayerPosition = 0;
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        // required overrides for media session callback
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }
}