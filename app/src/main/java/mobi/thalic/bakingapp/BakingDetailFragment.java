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

import java.util.List;

import mobi.thalic.bakingapp.adapter.BakingStepRecyclerViewAdapter;
import mobi.thalic.bakingapp.data.BakingStep;
import mobi.thalic.bakingapp.databinding.FragmentBakingDetailListBinding;
import mobi.thalic.bakingapp.viewmodel.BakingViewModel;

/**
 * A fragment representing a list of Items.
 */
public class BakingDetailFragment extends Fragment {
    // Declare variables
    FragmentBakingDetailListBinding binding;
    BakingActivity mBakingActivity;
    BakingViewModel mBakingViewModel;
    private OnListFragmentInteractionListener mListener;

    /**
     * Default constructor
     */
    public BakingDetailFragment() {
    }

    /**
     * Method to create an instance of baking detail fragment
     * @return baking detail fragment
     */
    public static BakingDetailFragment newInstance() {
        return new BakingDetailFragment();
    }

    /**
     * Method to create view for baking detail fragment
     * @param inflater to use to inflate view
     * @param container of the view
     * @param savedInstanceState for state issues
     * @return view created
     */
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // use view binding
        binding = FragmentBakingDetailListBinding.inflate(inflater, container, false);
        // set view
        View view = binding.getRoot();
        // initialize variables
        mBakingActivity = (BakingActivity) getActivity();
        mBakingViewModel = new ViewModelProvider(requireActivity()).get(BakingViewModel.class);
        mBakingActivity.setTitle(mBakingViewModel.getRecipeName());
        BakingStepRecyclerViewAdapter bakingStepRecyclerViewAdapter = new BakingStepRecyclerViewAdapter(mListener);
        // set recyclerview parameters
        binding.rvDetailList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDetailList.setHasFixedSize(false);
        binding.rvDetailList.setAdapter(bakingStepRecyclerViewAdapter);

        // set baking ingredients observer
        mBakingViewModel.getBakingIngredients().observe(getViewLifecycleOwner(), newBakingIngredients -> {
            if (newBakingIngredients != null && newBakingIngredients.size() > 0) {
                // create a string of the ingredients
                StringBuilder temp = new StringBuilder();
                for (int i = 0; i < newBakingIngredients.size(); i++) {
                    if (temp.toString().equals("")) {
                        temp = new StringBuilder(mBakingActivity.getString(R.string.baking_ingrediant_string,
                                newBakingIngredients.get(i).getIngredient(),
                                newBakingIngredients.get(i).getQuantity(),
                                newBakingIngredients.get(i).getMeasure()));
                    } else {
                        temp.append("\n").append(mBakingActivity.getString(R.string.baking_ingrediant_string,
                                newBakingIngredients.get(i).getIngredient(),
                                newBakingIngredients.get(i).getQuantity(),
                                newBakingIngredients.get(i).getMeasure()));
                    }
                }
                // set ingredients text to string created
                binding.tvIngredients.setText(temp.toString());
            }
        });
        // set baking steps observer
        mBakingViewModel.getBakingSteps().observe(getViewLifecycleOwner(), newBakingSteps -> {
            if (newBakingSteps != null && newBakingSteps.size() > 0) {
                // if data set new data on adapter
                bakingStepRecyclerViewAdapter.setBakingSteps(newBakingSteps);
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
        if (context instanceof BakingDetailFragment.OnListFragmentInteractionListener) {
            mListener = (BakingDetailFragment.OnListFragmentInteractionListener) context;
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