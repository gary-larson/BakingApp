package mobi.thalic.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import mobi.thalic.bakingapp.databinding.FragmentStepNavigationBinding;
import mobi.thalic.bakingapp.viewmodel.BakingViewModel;

/**
 * Class to handle Step navigation fragment
 */
public class StepNavigationFragment extends Fragment {
    // Declare variables
    private FragmentStepNavigationBinding binding;
    private OnListFragmentInteractionListener mListener;
    BakingViewModel mBakingViewModel;

    /**
     * Default Constructor
     */
    public StepNavigationFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment StepNavigationFragment.
     */
    public static StepNavigationFragment newInstance() {
        return new StepNavigationFragment();
    }

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
            binding.bPreviousStep.setOnClickListener((v -> {
                if (mListener != null) {
                    mListener.onListFragmentInteraction(binding.bPreviousStep);
                }
            }));
        } else {
            binding.bPreviousStep.setEnabled(false);
        }
        if (mBakingViewModel.getBakingStep().getId() < mBakingViewModel.getLastStepId()) {
            binding.bNextStep.setEnabled(true);
            binding.bNextStep.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onListFragmentInteraction(binding.bNextStep);
                }
            });
        } else {
            binding.bNextStep.setEnabled(false);
        }
        return view;
    }

    /**
     * Method that initializes the listener
     * @param context to use
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BakingFragment.OnListFragmentInteractionListener) {
            mListener = (StepNavigationFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() );
        }
    }

    /**
     * Method to remove listener
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Interface for the click listener
     */
    public interface OnListFragmentInteractionListener {
        // set arguments type and name
        void onListFragmentInteraction(View v);
    }
}