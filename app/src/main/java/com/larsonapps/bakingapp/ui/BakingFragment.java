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

public class BakingFragment extends Fragment {
    // Declare variables
    private FragmentBakingListBinding binding;
    private BakingViewModel mBakingViewModel;
    private BakingActivity mBakingActivity;
    private OnListFragmentInteractionListener mListener;

    public static BakingFragment newInstance() {
        return new BakingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBakingListBinding.inflate(inflater, container, false);
        View mView = binding.getRoot();
        mBakingActivity = (BakingActivity) getActivity();
        mBakingViewModel = new ViewModelProvider(requireActivity()).get(BakingViewModel.class);
        BakingRecipeRecyclerViewAdapter bakingAdapter = new BakingRecipeRecyclerViewAdapter(mListener);
        int mColumnCount = getResources().getInteger(R.integer.number_of_horizontal_columns);
        if (mColumnCount <= 1) {
            binding.rvRecipeList.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            binding.rvRecipeList.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }
        binding.rvRecipeList.setHasFixedSize(true);
        binding.rvRecipeList.setAdapter(bakingAdapter);

        mBakingViewModel.getBakingRecipes().observe(getViewLifecycleOwner(),
                newBakingRecipes -> {
                    //
                    if (newBakingRecipes instanceof BakingResult.Error) {
                        binding.tvErrorMessage
                                .setText(((BakingResult.Error<List<BakingRecipeEntity>>)
                                        newBakingRecipes).mErrorMessage);
                        showErrorMessage();
                    } else {
                        BakingResult.Success<List<BakingRecipeEntity>> result =
                                (BakingResult.Success<List<BakingRecipeEntity>>) newBakingRecipes;
                        if (result != null) {
                            List<BakingRecipeEntity> bakingRecipes = result.data;
                            bakingAdapter.setBakingRecipes(bakingRecipes);
                            showRecyclerview();
                        } else {
                            showErrorMessage();
                        }
                    }
                });
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

    private void showErrorMessage () {
        binding.rvRecipeList.setVisibility(View.INVISIBLE);
        binding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        binding.tvErrorMessage.setVisibility(View.VISIBLE);
    }

    private void showRecyclerview () {
        binding.rvRecipeList.setVisibility(View.VISIBLE);
        binding.tvErrorMessage.setVisibility(View.GONE);
        binding.pbLoadingIndicator.setVisibility(View.GONE);
    }

}