package mobi.thalic.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * A simple {@link Fragment} subclass.
 * Use the {@link ExoPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExoPlayerFragment extends Fragment implements ExoPlayer.EventListener {
    // Declare constants
    private static final String EXO_PLAYER_POSITION = "PlayerPosition";
    private static final String TAG = ExoPlayerFragment.class.getSimpleName();
    // Declare variables
    private FragmentExoPlayerBinding binding;
    private BakingActivity mBakingActivity;
    BakingViewModel mBakingViewModel;
    private long mPlayerPosition;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private String mVideoUrl;
    private String mThumbnailUrl;

    /**
     * Default constructor
     */
    public ExoPlayerFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ExoPlayerFragment.
     */
    public static ExoPlayerFragment newInstance() {
        return new ExoPlayerFragment();
    }

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
        if (savedInstanceState != null) {
            mPlayerPosition = savedInstanceState.getLong(EXO_PLAYER_POSITION);
        } else {
            mPlayerPosition = 0;
        }
        // Initialize the Media Session.
        initializeMediaSession();
        // set baking steps observer
        mBakingViewModel.getLiveDataBakingStep().observe(getViewLifecycleOwner(), newBakingStep -> {
            if (newBakingStep != null) {
                mThumbnailUrl = newBakingStep.getThumbnailURL();
                mVideoUrl = newBakingStep.getVideoURL();
                mBakingActivity.setTitle(newBakingStep.getShortDescription());
                updateUI();
            }
        });
        return view;
    }

    /**
     * Method to update the user interface
     */
    private void updateUI() {
        if (mVideoUrl != null && !mVideoUrl.equals("")) {
            // Initialize the player.
            initializePlayer(Uri.parse(mVideoUrl));
            binding.playerView.setVisibility(View.VISIBLE);
            binding.tvNoVideoMessage.setVisibility(View.GONE);
            if (getResources().getBoolean(R.bool.is_landscape) &&
                    !getResources().getBoolean((R.bool.is_two_pane))) {
                if (mBakingActivity != null && mBakingActivity.getSupportActionBar() != null) {
                    mBakingActivity.getSupportActionBar().hide();
                }
            }
            else {
                if (mBakingActivity != null && mBakingActivity.getSupportActionBar() != null) {
                    mBakingActivity.getSupportActionBar().show();
                }
            }
        } else {
            if (mThumbnailUrl != null && !mThumbnailUrl.equals("")) {
                // Initialize the player.
                initializePlayer(Uri.parse(mThumbnailUrl));
                binding.playerView.setVisibility(View.VISIBLE);
                binding.tvNoVideoMessage.setVisibility(View.GONE);
                if (getResources().getBoolean(R.bool.is_landscape) &&
                        !getResources().getBoolean((R.bool.is_two_pane))) {
                    if (mBakingActivity != null && mBakingActivity.getSupportActionBar() != null) {
                        mBakingActivity.getSupportActionBar().hide();
                    }
                } else {
                    if (mBakingActivity != null && mBakingActivity.getSupportActionBar() != null) {
                        mBakingActivity.getSupportActionBar().show();
                    }
                }
            } else {
                binding.playerView.setVisibility(View.GONE);
                binding.tvNoVideoMessage.setVisibility(View.VISIBLE);
                if (mBakingActivity != null && mBakingActivity.getSupportActionBar() != null) {
                    mBakingActivity.getSupportActionBar().show();
                }
            }
        }
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
            mExoPlayer.setPlayWhenReady(true);
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

    /**
     * Method to set up fragment
     */
    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mExoPlayer.setPlayWhenReady(false);
            mPlayerPosition = mExoPlayer.getCurrentPosition();
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
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mExoPlayer != null) {
            releasePlayer();
        }
        mMediaSession.setActive(false);
    }

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