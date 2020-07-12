package com.larsonapps.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.larsonapps.bakingapp.BakingFragment;
import com.larsonapps.bakingapp.R;
import com.larsonapps.bakingapp.data.BakingRecipeEntity;
import com.larsonapps.bakingapp.databinding.FragmentBakingListItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Class to handle baking recipe recyclerview adapter
 */
public class BakingRecipeRecyclerViewAdapter extends RecyclerView.Adapter<BakingRecipeRecyclerViewAdapter.ViewHolder> {
    // Declare Variables
    private FragmentBakingListItemBinding binding;
    Context context;
    private List<BakingRecipeEntity> mBakingRecipes;

    // Variable for listener
    private final BakingFragment.OnListFragmentInteractionListener mListener;

    /**
     * Constructor that sets listener
     * @param listener to set
     */
    public BakingRecipeRecyclerViewAdapter (BakingFragment.OnListFragmentInteractionListener
                                                    listener) {
        mListener = listener;
    }

    /**
     * Method to create the view holder
     * @param parent of the view holder
     * @param viewType of the view holder
     * @return view holder created
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        // set view binding
        binding = FragmentBakingListItemBinding.inflate(LayoutInflater.from(context), parent,
                false);
        // get view
        View view = binding.getRoot();
        // return view holder created
        return new ViewHolder(view);
    }

    /**
     * Method to handle view holder binding
     * @param holder to bind
     * @param position of the holder
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Declare a variable of the baking recipes
        final List<BakingRecipeEntity> bakingRecipes = mBakingRecipes;
        if (mBakingRecipes.get(position) != null) {
            // set holder baking recipe
            holder.mBakingRecipe = mBakingRecipes.get(position);
            // if image use picasso to display it (show nothing if no image)
            if (bakingRecipes.get(position).getImage() != null &&
                    !bakingRecipes.get(position).getImage().equals("")) {
                Picasso.get().load(bakingRecipes.get(position).getImage())
                        .noPlaceholder()
                        .centerInside()
                        .into(holder.mImageView);
            }
            // set name of recipe
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

    /**
     * Method to get the number of baking recipes or 0
     * @return number of baking recipes
     */
    @Override
    public int getItemCount() {
        if (mBakingRecipes != null && mBakingRecipes.size() > 0) {
            return mBakingRecipes.size();
        }
        return 0;
    }

    /**
     * Method to set new data and refresh adapter
     * @param bakingRecipes to set
     */
    public void setBakingRecipes (List<BakingRecipeEntity> bakingRecipes) {
        mBakingRecipes = bakingRecipes;
        notifyDataSetChanged();
    }

    /**
     * Class for the view holders
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        // Declare variables
        final View mView;
        public BakingRecipeEntity mBakingRecipe;
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
