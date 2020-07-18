package mobi.thalic.bakingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import mobi.thalic.bakingapp.databinding.FragmentStepDescriptionBinding;
import mobi.thalic.bakingapp.viewmodel.BakingViewModel;

/**
 * Class to handle step description fragment
 */
public class StepDescriptionFragment extends Fragment {
    // Declare variables
    FragmentStepDescriptionBinding binding;
    BakingViewModel mBakingViewModel;

    /**
     * Default constructor
     */
    public StepDescriptionFragment() {}

    /**
     * Method to create and inflate step description fragment
     * @param inflater to use
     * @param container of the view
     * @param savedInstanceState for state changes
     * @return created view
     */
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStepDescriptionBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // initialize variables
        mBakingViewModel = new ViewModelProvider(requireActivity()).get(BakingViewModel.class);
        // set baking step description observer
        mBakingViewModel.getBakingStepDescription().observe(getViewLifecycleOwner(), newDescription -> {
            if (newDescription != null) {
                binding.tvStepDescription.setText(newDescription);
            }
        });
        return view;
    }
}