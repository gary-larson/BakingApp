package mobi.thalic.bakingapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import mobi.thalic.bakingapp.databinding.FragmentBakingStepDetailBinding;
import mobi.thalic.bakingapp.viewmodel.BakingViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BakingStepDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BakingStepDetailFragment extends Fragment {
    private BakingViewModel mBakingViewModel;

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
        // Declare variables
        mobi.thalic.bakingapp.databinding.FragmentBakingStepDetailBinding binding =
                FragmentBakingStepDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // initialize variable
        mBakingViewModel = new ViewModelProvider(requireActivity()).get(BakingViewModel.class);
        mBakingViewModel.getLiveDataBakingStep().observe(getViewLifecycleOwner(), newBakingStep -> {
            if (newBakingStep != null) {
                if (getResources().getBoolean(R.bool.is_landscape) &&
                        !getResources().getBoolean(R.bool.is_two_pane)) {
                    if (TextUtils.isEmpty(newBakingStep.getVideoURL()) &&
                            TextUtils.isEmpty(newBakingStep.getThumbnailURL())) {
                        binding.fcvExoPlayer.setVisibility(View.GONE);
                        binding.fcvStepDescription.setVisibility(View.VISIBLE);
                        binding.fcvStepNavigation.setVisibility(View.VISIBLE);
                    } else {
                        binding.fcvExoPlayer.setVisibility(View.VISIBLE);
                        binding.fcvStepDescription.setVisibility((View.GONE));
                        binding.fcvStepNavigation.setVisibility(View.GONE);
                    }
                }
            }
        });
        return view;
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
        if (bakingActivity != null && mBakingViewModel != null) {
            bakingActivity.setTitle(mBakingViewModel.getBakingStep().getShortDescription());
        }
    }
}