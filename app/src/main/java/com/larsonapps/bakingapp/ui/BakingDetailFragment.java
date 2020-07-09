package com.larsonapps.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.larsonapps.bakingapp.BakingActivity;
import com.larsonapps.bakingapp.R;
import com.larsonapps.bakingapp.data.BakingStep;
import com.larsonapps.bakingapp.databinding.FragmentBakingDetailListBinding;

import org.jetbrains.annotations.NotNull;

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


    public static BakingDetailFragment newInstance() {
        return new BakingDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBakingDetailListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mBakingActivity = (BakingActivity) getActivity();
        mBakingViewModel = new ViewModelProvider(requireActivity()).get(BakingViewModel.class);
        BakingStepRecyclerViewAdapter bakingStepRecyclerViewAdapter = new BakingStepRecyclerViewAdapter(mListener);
        binding.rvDetailList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDetailList.setHasFixedSize(false);
        binding.rvDetailList.setAdapter(bakingStepRecyclerViewAdapter);

        // set ingredients observer
        mBakingViewModel.getBakingIngredients().observe(getViewLifecycleOwner(), newBakingIngredients -> {
            if (newBakingIngredients != null && newBakingIngredients.size() > 0) {
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
                binding.tvIngredients.setText(temp.toString());
            }
        });
        mBakingViewModel.getBakingSteps().observe(getViewLifecycleOwner(), newBakingSteps -> {
            if (newBakingSteps != null && newBakingSteps.size() > 0) {
                bakingStepRecyclerViewAdapter.setBakingSteps(newBakingSteps);
            }
        });
        return view;
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