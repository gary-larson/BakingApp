package mobi.thalic.bakingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import mobi.thalic.bakingapp.databinding.FragmentIngredientBinding;
import mobi.thalic.bakingapp.viewmodel.BakingViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientFragment#newInstance} factory method to
 * create an instance of this fragment.
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
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment IngredientFragment.
     */
    public static IngredientFragment newInstance() {
        return new IngredientFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentIngredientBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
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
        return view;
    }
}