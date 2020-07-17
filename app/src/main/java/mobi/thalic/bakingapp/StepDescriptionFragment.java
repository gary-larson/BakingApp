package mobi.thalic.bakingapp;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import mobi.thalic.bakingapp.adapter.BakingStepRecyclerViewAdapter;
import mobi.thalic.bakingapp.data.BakingStep;
import mobi.thalic.bakingapp.databinding.FragmentStepDescriptionBinding;
import mobi.thalic.bakingapp.viewmodel.BakingViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepDescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
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
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StepDescriptionFragment.
     */
    public static StepDescriptionFragment newInstance() {
        return new StepDescriptionFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStepDescriptionBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // initialize variables
        mBakingViewModel = new ViewModelProvider(requireActivity()).get(BakingViewModel.class);
        // set baking steps observer
        mBakingViewModel.getBakingStepDescription().observe(getViewLifecycleOwner(), newDescription -> {
            if (newDescription != null) {
                binding.tvStepDescription.setText(newDescription);
            }
        });
        return view;
    }
}