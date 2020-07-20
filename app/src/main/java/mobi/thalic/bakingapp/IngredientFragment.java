package mobi.thalic.bakingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import mobi.thalic.bakingapp.databinding.FragmentIngredientBinding;
import mobi.thalic.bakingapp.viewmodel.BakingViewModel;

/**
 * Class to handle ingredient fragment
 */
public class IngredientFragment extends Fragment {
    // Declare variables
    FragmentIngredientBinding binding;
    BakingActivity mBakingActivity;
    BakingViewModel mBakingViewModel;

    /**
     * Default Constructor
     */
    public IngredientFragment() {}

    /**
     * Method to create and inflate ingredient fragment
     * @param inflater to use
     * @param container of the view
     * @param savedInstanceState to handle state changes
     * @return created view
     */
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentIngredientBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // initialize view model
        mBakingActivity = (BakingActivity) getActivity();
        mBakingViewModel = new ViewModelProvider(requireActivity()).get(BakingViewModel.class);
        // set baking ingredients observer
        mBakingViewModel.getBakingIngredients().observe(getViewLifecycleOwner(), newBakingIngredients -> {
            if (newBakingIngredients != null && newBakingIngredients.size() > 0) {
                // create a string of the ingredients
                StringBuilder temp = new StringBuilder();
                for (int i = 0; i < newBakingIngredients.size(); i++) {
                    if (temp.toString().equals("")) {
                        temp = new StringBuilder(mBakingActivity.getString(R.string.baking_ingredient_string,
                                newBakingIngredients.get(i).getIngredient(),
                                newBakingIngredients.get(i).getQuantity(),
                                newBakingIngredients.get(i).getMeasure()));
                    } else {
                        temp.append("\n").append(mBakingActivity.getString(R.string.baking_ingredient_string,
                                newBakingIngredients.get(i).getIngredient(),
                                newBakingIngredients.get(i).getQuantity(),
                                newBakingIngredients.get(i).getMeasure()));
                    }
                }
                // set ingredients text to string created
                binding.tvIngredients.setText(temp.toString());
            }
        });
        return view;
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