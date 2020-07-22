package mobi.thalic.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import mobi.thalic.bakingapp.StepDescriptionListFragment;
import mobi.thalic.bakingapp.data.BakingStep;
import mobi.thalic.bakingapp.databinding.FragmentStepDescriptionListItemBinding;

/**
 * Class to handle adapter for baking step recyclerview.
 */
public class BakingStepRecyclerViewAdapter extends RecyclerView.Adapter<BakingStepRecyclerViewAdapter.ViewHolder> {
    // Declare variables
    FragmentStepDescriptionListItemBinding binding;
    Context context;

    private List<BakingStep> mBakingSteps;
    private final StepDescriptionListFragment.OnListFragmentInteractionListener mListener;

    /**
     * Constructor to set listener
     * @param listener to set
     */
    public BakingStepRecyclerViewAdapter(StepDescriptionListFragment.OnListFragmentInteractionListener
                                                 listener, List<BakingStep> bakingSteps) {
       mListener = listener;
       mBakingSteps = bakingSteps;
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
        binding = FragmentStepDescriptionListItemBinding.inflate(LayoutInflater.from(context), parent,
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
        // set description of baking step
        if (mBakingSteps.get(position) != null) {
            holder.mBakingStep = mBakingSteps.get(position);
            binding.tvStepDescription.setText(holder.mBakingStep.getShortDescription());
        } else {
            binding.tvStepDescription.setText("");
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}