package mobi.thalic.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
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

import mobi.thalic.bakingapp.data.BakingStep;
import mobi.thalic.bakingapp.databinding.FragmentBakingStepDetailBinding;
import mobi.thalic.bakingapp.viewmodel.BakingViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BakingStepDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BakingStepDetailFragment extends Fragment implements View.OnClickListener,
        ExoPlayer.EventListener {
    // Declare constants
    private static final String EXO_PLAYER_POSITION = "PlayerPosition";
    // Declare variables
    private FragmentBakingStepDetailBinding binding;
    private BakingActivity mBakingActivity;
    private BakingViewModel mBakingViewModel;
    private BakingStep mBakingStep;
    long mPlayerPosition;
    private static final String TAG = BakingStepDetailFragment.class.getSimpleName();
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    /**
     * Default constructor
     */
    public BakingStepDetailFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BakingStepDetailFragment.
     */
    public static BakingStepDetailFragment newInstance() {
        return new BakingStepDetailFragment();
    }

    /**
     * Method to create and inflate baking step detail fragment view
     * @param inflater to use
     * @param container for view
     * @param savedInstanceState to kee track of state
     * @return created view
     */
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBakingStepDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // initialize variables
        mBakingActivity = (BakingActivity) getActivity();
        mBakingViewModel = new ViewModelProvider(requireActivity()).get(BakingViewModel.class);
        mPlayerView = binding.playerView;
        if (savedInstanceState != null) {
            mPlayerPosition = savedInstanceState.getLong(EXO_PLAYER_POSITION);
        } else {
            mPlayerPosition = 0;
        }
        mBakingStep = mBakingViewModel.getBakingStep();

        if (mBakingStep == null) {
            return view;
        }

        // Initialize the Media Session.
        initializeMediaSession();
        updateUI();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(EXO_PLAYER_POSITION, mPlayerPosition);
    }

    /**
     * Method to update the user interface
     */
    private void updateUI() {
        // SetTitle of Activity
        mBakingActivity.setTitle(mBakingStep.getShortDescription());
        if (mBakingStep.getVideoURL() != null && !mBakingStep.getVideoURL().equals("")) {
            // Initialize the player.
            initializePlayer(Uri.parse(mBakingStep.getVideoURL()));
            binding.playerView.setVisibility(View.VISIBLE);
            binding.tvNoVideoMessage.setVisibility(View.GONE);
        } else {
            if (mBakingStep.getThumbnailURL() != null && !mBakingStep.getThumbnailURL().equals("")) {
                // Initialize the player.
                initializePlayer(Uri.parse(mBakingStep.getThumbnailURL()));
                binding.playerView.setVisibility(View.VISIBLE);
                binding.tvNoVideoMessage.setVisibility(View.GONE);
            } else {
                binding.playerView.setVisibility(View.GONE);
                binding.tvNoVideoMessage.setVisibility(View.VISIBLE);
            }
        }
        binding.tvStepDescription.setText(mBakingStep.getDescription());
        if (mBakingStep.getId() > 0) {
            binding.bPreviousStep.setEnabled(true);
            binding.bPreviousStep.setOnClickListener(this);
        } else {
            binding.bPreviousStep.setEnabled(false);
        }
        if (mBakingStep.getId() < mBakingViewModel.getLastStepId()) {
            binding.bNextStep.setEnabled(true);
            binding.bNextStep.setOnClickListener(this);
        } else {
            binding.bNextStep.setEnabled(false);
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
        mMediaSession.setCallback(new MySessionCallback());

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
        // Get Activity
        BakingActivity bakingActivity = (BakingActivity) getActivity();
        if (bakingActivity != null) {
            // reset title
            bakingActivity.setTitle(getString(R.string.app_name));
        }
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
        // Get activity
        BakingActivity bakingActivity = (BakingActivity) getActivity();
        // set title
        if (bakingActivity != null && mBakingStep != null) {
            bakingActivity.setTitle(mBakingStep.getShortDescription());
        }
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
    public void onClick(View v) {
        // Declare variables
        int viewId = v.getId();
        BakingStep bakingStep = null;

        // get baking step to change to
        if (viewId == R.id.b_previous_step) {
            bakingStep = mBakingViewModel.getPreviousBakingStep(mBakingStep.getId());
        } else if (viewId == R.id.b_next_step) {
            bakingStep = mBakingViewModel.getNextBakingStep(mBakingStep.getId());
        }
        if (bakingStep != null) {
            mBakingStep = bakingStep;
            if (mExoPlayer != null) {
                releasePlayer();
            }
            updateUI();
        }
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