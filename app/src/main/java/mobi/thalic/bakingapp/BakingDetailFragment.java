package mobi.thalic.bakingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

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
    //private OnListFragmentInteractionListener mListener;
   // long mPlayerPosition;

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
        //View view = inflater.inflate(R.layout.fragment_baking_detail_list, container, false);
        // initialize variables
        mBakingActivity = (BakingActivity) getActivity();
        mBakingViewModel = new ViewModelProvider(requireActivity()).get(BakingViewModel.class);
        mBakingActivity.setTitle(mBakingViewModel.getRecipeName());
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