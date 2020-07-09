package com.larsonapps.bakingapp.ui;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.larsonapps.bakingapp.data.BakingStep;
import com.larsonapps.bakingapp.databinding.FragmentBakingDetailListItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BakingStep}.
 */
public class BakingStepRecyclerViewAdapter extends RecyclerView.Adapter<BakingStepRecyclerViewAdapter.ViewHolder> {
    // Declare variables
    FragmentBakingDetailListItemBinding binding;
    Context context;

    private List<BakingStep> mBakingSteps;
    private final BakingDetailFragment.OnListFragmentInteractionListener mListener;

    public BakingStepRecyclerViewAdapter(BakingDetailFragment.OnListFragmentInteractionListener
                                                 listener) {
       mListener = listener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        binding = FragmentBakingDetailListItemBinding.inflate(LayoutInflater.from(context), parent,
                false);
        View view = binding.getRoot();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, int position) {
        final List<BakingStep> bakingSteps = mBakingSteps;
        if (bakingSteps.get(position) != null) {
            holder.mBakingStep = bakingSteps.get(position);
            binding.tvStepDescription.setText(holder.mBakingStep.getShortDescription());
        }
        // set up on click listener
        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(holder.mBakingStep);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mBakingSteps != null) {
            return mBakingSteps.size();
        }
        return 0;
    }

    public void setBakingSteps (List<BakingStep> bakingSteps) {
        mBakingSteps = bakingSteps;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public BakingStep mBakingStep;
        public final TextView mDescriptionView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDescriptionView = binding.tvStepDescription;
        }

        @NotNull
        @Override
        public String toString() {
            return super.toString() + " '" + mDescriptionView.getText() + "'";
        }
    }
}