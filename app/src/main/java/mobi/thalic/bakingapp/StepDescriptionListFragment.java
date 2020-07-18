package mobi.thalic.bakingapp;

import android.content.Context;
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
import mobi.thalic.bakingapp.databinding.FragmentBakingStepDetailBinding;
import mobi.thalic.bakingapp.databinding.FragmentStepDescriptionListBinding;
import mobi.thalic.bakingapp.viewmodel.BakingViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepDescriptionListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepDescriptionListFragment extends Fragment {
    // Declare constants
    private static final String BAKING_STEP = "BakingStep";
    // Declare variables
    private FragmentStepDescriptionListBinding binding;
    private BakingActivity mBakingActivity;
    private BakingViewModel mBakingViewModel;
    private OnListFragmentInteractionListener mListener;

    /**
     * Default constructor
     */
    public StepDescriptionListFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StepDescrptionListFragment.
     */
    public static StepDescriptionListFragment newInstance() {
        return new StepDescriptionListFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStepDescriptionListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // initialize variables
        mBakingActivity = (BakingActivity) getActivity();
        mBakingViewModel = new ViewModelProvider(requireActivity()).get(BakingViewModel.class);
        mBakingActivity.setTitle(mBakingViewModel.getRecipeName());
        // set baking steps observer
        mBakingViewModel.getBakingSteps().observe(getViewLifecycleOwner(), newBakingSteps -> {
            if (newBakingSteps != null && newBakingSteps.size() > 0) {
                BakingStepRecyclerViewAdapter bakingStepRecyclerViewAdapter = new
                        BakingStepRecyclerViewAdapter(mListener, newBakingSteps);
                // set recyclerview parameters
                binding.rvDetailList.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.rvDetailList.setHasFixedSize(false);
                binding.rvDetailList.setAdapter(bakingStepRecyclerViewAdapter);
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
            bakingActivity.setTitle(mBakingViewModel.getRecipeName());
        }
    }

    /**
     * Method that initializes the listener
     * @param context to use
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof StepDescriptionListFragment.OnListFragmentInteractionListener) {
            mListener = (StepDescriptionListFragment.OnListFragmentInteractionListener) context;
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
        void onListFragmentInteraction(BakingStep bakingStep);
    }
}