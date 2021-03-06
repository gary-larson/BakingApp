package mobi.thalic.bakingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import mobi.thalic.bakingapp.databinding.FragmentStepNavigationBinding;
import mobi.thalic.bakingapp.viewmodel.BakingViewModel;

/**
 * Class to handle Step navigation fragment
 */
public class StepNavigationFragment extends Fragment implements View.OnClickListener {
    // Declare variables
    private FragmentStepNavigationBinding binding;
    BakingViewModel mBakingViewModel;

    /**
     * Default Constructor
     */
    public StepNavigationFragment() {}

    /**
     * Method to create and inflate step navigation fragment
     * @param inflater to use
     * @param container that contains fragment
     * @param savedInstanceState for state information
     * @return created fragment
     */
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStepNavigationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mBakingViewModel = new ViewModelProvider(requireActivity()).get(BakingViewModel.class);
        if (mBakingViewModel.getBakingStep().getId() > 0) {
            binding.bPreviousStep.setEnabled(true);
        } else {
            binding.bPreviousStep.setEnabled(false);
        }
        if (mBakingViewModel.getBakingStep().getId() < mBakingViewModel.getLastStepId()) {
            binding.bNextStep.setEnabled(true);
        } else {
            binding.bNextStep.setEnabled(false);
        }
        binding.bPreviousStep.setOnClickListener(this);
        binding.bNextStep.setOnClickListener(this);
        return view;
    }

    /**
     * Method to handle clicks
     * @param v view that was clicked
     */
    @Override
    public void onClick(View v) {
        // Declare variables
        int viewId = v.getId();
        int bakingStepId = mBakingViewModel.getBakingStep().getId();

        // get baking step to change to
        if (viewId == binding.bPreviousStep.getId()) {
            mBakingViewModel.setPreviousBakingStep(bakingStepId);
        } else if (viewId == binding.bNextStep.getId()) {
            mBakingViewModel.setNextBakingStep(bakingStepId);
        }
        // update baking step
        bakingStepId = mBakingViewModel.getBakingStep().getId();
        // set whether buttons are enabled or not
        binding.bPreviousStep.setEnabled(bakingStepId > 0);
        binding.bNextStep.setEnabled(bakingStepId < mBakingViewModel.getLastStepId());
    }

    /**
     * Method to cleanup binding
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}