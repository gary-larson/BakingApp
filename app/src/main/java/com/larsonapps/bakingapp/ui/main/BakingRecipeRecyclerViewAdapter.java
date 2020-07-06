package com.larsonapps.bakingapp.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.larsonapps.bakingapp.R;
import com.larsonapps.bakingapp.data.BakingRecipe;
import com.larsonapps.bakingapp.databinding.BakingFragmentListItemBinding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class BakingRecipeRecyclerViewAdapter extends RecyclerView.Adapter<BakingRecipeRecyclerViewAdapter.ViewHolder> {
    // Declare Variables
    private BakingFragmentListItemBinding binding;
    Context context;
    private List<BakingRecipe> mBakingRecipes;

    // Variable for listener
    private final BakingFragment.OnListFragmentInteractionListener mListener;

    public BakingRecipeRecyclerViewAdapter (BakingFragment.OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        binding = BakingFragmentListItemBinding.inflate(LayoutInflater.from(context), parent,
                false);
        View view = binding.getRoot();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Declare a variable of the movie results
        final List<BakingRecipe> bakingRecipes = mBakingRecipes;
        if (mBakingRecipes.get(position) != null) {
            holder.mBakingRecipe = mBakingRecipes.get(position);
            if (bakingRecipes.get(position).getImage() != null &&
                    !bakingRecipes.get(position).getImage().equals("")) {
                Picasso.get().load(bakingRecipes.get(position).getImage())
                        // TODO create error drawable .error(R.mipmap.error)
                        .noPlaceholder()
                        .centerInside()
                        .into(holder.mImageView);
            }
            binding.tvRecipeName.setText(bakingRecipes.get(position).getName());

            // Display servings
            String temp = context.getString(R.string.baking_recipe_serving,
                    bakingRecipes.get(position).getServings());
            binding.tvRecipeServings.setText(temp);

            // set up on click listener
            holder.mView.setOnClickListener(v -> {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mBakingRecipe);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mBakingRecipes != null && mBakingRecipes.size() > 0) {
            return mBakingRecipes.size();
        }
        return 0;
    }

    public void setBakingRecipes (List<BakingRecipe> bakingRecipes) {
        mBakingRecipes = bakingRecipes;
        notifyDataSetChanged();
    }

    /**
     * Class for the view holders
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        // Declare variables
        final View mView;
        public BakingRecipe mBakingRecipe;
        ImageView mImageView;

        /**
         * Constructor for the view holder class
         * @param view to use
         */
        ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = binding.ivRecipeImage;
        }
    }

}
