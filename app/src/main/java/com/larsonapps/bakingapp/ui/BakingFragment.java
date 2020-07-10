package com.larsonapps.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.larsonapps.bakingapp.BakingActivity;
import com.larsonapps.bakingapp.R;
import com.larsonapps.bakingapp.data.BakingRecipeEntity;
import com.larsonapps.bakingapp.databinding.FragmentBakingListBinding;
import com.larsonapps.bakingapp.utilities.BakingResult;

import java.util.List;

/**
 * Class for the baking Fragment
 */
public class BakingFragment extends Fragment {
    // Declare variables
    private FragmentBakingListBinding binding;
    private BakingViewModel mBakingViewModel;
    private BakingActivity mBakingActivity;
    private OnListFragmentInteractionListener mListener;

    /**
     * Method to create the baking fragment instance
     * @return baking fragment instance
     */
    public static BakingFragment newInstance() {
        return new BakingFragment();
    }

    /**
     * Method to create the baking fragment view
     * @param inflater to use
     * @param container that contains the fragment
     * @param savedInstanceState of the fragment
     * @return the view created
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // set view binding
        binding = FragmentBakingListBinding.inflate(inflater, container, false);
        // get the view
        View mView = binding.getRoot();
        // initialize variables
        mBakingActivity = (BakingActivity) getActivity();
        mBakingViewModel = new ViewModelProvider(requireActivity()).get(BakingViewModel.class);
        BakingRecipeRecyclerViewAdapter bakingAdapter = new BakingRecipeRecyclerViewAdapter(mListener);
        // sget column count for the recyclerview
        int mColumnCount = getResources().getInteger(R.integer.number_of_horizontal_columns);
        // set layout manager based on column count
        if (mColumnCount <= 1) {
            binding.rvRecipeList.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            binding.rvRecipeList.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }
        // set recyclerview parameters
        binding.rvRecipeList.setHasFixedSize(true);
        binding.rvRecipeList.setAdapter(bakingAdapter);
        // set observer for baking recipes live data
        mBakingViewModel.getBakingRecipes().observe(getViewLifecycleOwner(),
                newBakingRecipes -> {
                    // if error set error message
                    if (newBakingRecipes instanceof BakingResult.Error) {
                        binding.tvErrorMessage
                                .setText(((BakingResult.Error<List<BakingRecipeEntity>>)
                                        newBakingRecipes).mErrorMessage);
                        showErrorMessage();
                    } else {
                        //if success set recipes in adapter
                        BakingResult.Success<List<BakingRecipeEntity>> result =
                                (BakingResult.Success<List<BakingRecipeEntity>>) newBakingRecipes;
                        if (result != null) {
                            List<BakingRecipeEntity> bakingRecipes = result.data;
                            bakingAdapter.setBakingRecipes(bakingRecipes);
                            showRecyclerview();
                        } else {
                            // otherwise shown default error message
                            showErrorMessage();
                        }
                    }
                });
        // return view created
        return mView;
    }

    /**
     * Method that initializes the listener
     * @param context to use
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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
        void onListFragmentInteraction(BakingRecipeEntity bakingRecipe);
    }

    /**
     * Method to show error message and hide loading indicator and recyclerview
     */
    private void showErrorMessage () {
        binding.rvRecipeList.setVisibility(View.INVISIBLE);
        binding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        binding.tvErrorMessage.setVisibility(View.VISIBLE);
    }

    /**
     * Method to show recyclerview and hide error message and loading indicator
     */
    private void showRecyclerview () {
        binding.rvRecipeList.setVisibility(View.VISIBLE);
        binding.tvErrorMessage.setVisibility(View.GONE);
        binding.pbLoadingIndicator.setVisibility(View.GONE);
    }
}