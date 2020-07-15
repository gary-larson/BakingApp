package mobi.thalic.bakingapp.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mobi.thalic.bakingapp.BakingDetailFragment;
import mobi.thalic.bakingapp.data.BakingStep;
import mobi.thalic.bakingapp.databinding.FragmentBakingDetailListItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Class to handle adapter for baking step recyclerview.
 */
public class BakingStepRecyclerViewAdapter extends RecyclerView.Adapter<BakingStepRecyclerViewAdapter.ViewHolder> {
    // Declare variables
    FragmentBakingDetailListItemBinding binding;
    Context context;

    private List<BakingStep> mBakingSteps;
    private final BakingDetailFragment.OnListFragmentInteractionListener mListener;

    /**
     * Constructor to set listener
     * @param listener to set
     */
    public BakingStepRecyclerViewAdapter(BakingDetailFragment.OnListFragmentInteractionListener
                                                 listener) {
       mListener = listener;
    }

    /**
     * Method to create a view holder for a baking step
     * @param parent of the view holder
     * @param viewType of the holder
     * @return view holder created
     */
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // initialize variables
        context = parent.getContext();
        // set view binding
        binding = FragmentBakingDetailListItemBinding.inflate(LayoutInflater.from(context), parent,
                false);
        // get view
        View view = binding.getRoot();
        // return view holder created
        return new ViewHolder(view);
    }

    /**
     * Method to bind data to the view holder
     * @param holder to bind data to
     * @param position of the data
     */
    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, int position) {
        // declare and initialize baking steps
        final List<BakingStep> bakingSteps = mBakingSteps;
        // set description of baking step
        if (bakingSteps.get(position) != null) {
            holder.mBakingStep = bakingSteps.get(position);
            binding.tvStepDescription.setText(holder.mBakingStep.getShortDescription());
        }
        // set up on click listener
        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(mBakingSteps.get(position));
            }
        });
    }

    /**
     * Method to get count of baking steps or 0
     * @return count of baking steps
     */
    @Override
    public int getItemCount() {
        if (mBakingSteps != null) {
            return mBakingSteps.size();
        }
        return 0;
    }

    /**
     * Method to set new baking steps data and refresh adapter
     * @param bakingSteps to set
     */
    public void setBakingSteps (List<BakingStep> bakingSteps) {
        mBakingSteps = bakingSteps;
        notifyDataSetChanged();
    }

    /**
     * Class for the view holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Declare variables
        public final View mView;
        public BakingStep mBakingStep;
        public final TextView mDescriptionView;

        /**
         * Constructor for view holder
         * @param view of the view holder
         */
        public ViewHolder(View view) {
            super(view);
            // set view
            mView = view;
            // get step description view
            mDescriptionView = binding.tvStepDescription;
        }

        /**
         * Method to repreesent view holder as a string
         * @return string representation
         */
        @NotNull
        @Override
        public String toString() {
            return super.toString() + " '" + mDescriptionView.getText() + "'";
        }
    }
}