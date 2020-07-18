package mobi.thalic.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.jetbrains.annotations.NotNull;

import mobi.thalic.bakingapp.adapter.BakingStepRecyclerViewAdapter;
import mobi.thalic.bakingapp.data.BakingStep;
import mobi.thalic.bakingapp.databinding.FragmentStepDescriptionListBinding;
import mobi.thalic.bakingapp.viewmodel.BakingViewModel;

/**
 * Class to handle step description list fragment
 */
public class StepDescriptionListFragment extends Fragment {
    // Declare variables
    private FragmentStepDescriptionListBinding binding;
    private BakingViewModel mBakingViewModel;
    private OnListFragmentInteractionListener mListener;

    /**
     * Default constructor
     */
    public StepDescriptionListFragment() {}

    /**
     * Method to create and inflate step description list fragment
     * @param inflater to use
     * @param container for view
     * @param savedInstanceState to handle state changes
     * @return created view
     */
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStepDescriptionListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // initialize variables
        BakingActivity mBakingActivity = (BakingActivity) getActivity();
        mBakingViewModel = new ViewModelProvider(requireActivity()).get(BakingViewModel.class);
        if (mBakingActivity != null) {
            mBakingActivity.setTitle(mBakingViewModel.getRecipeName());
        }
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