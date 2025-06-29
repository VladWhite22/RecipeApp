package com.example.recipeapp.ui.recipes.favorites

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.IMAGE_BASE_URL
import com.example.recipeapp.R
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.databinding.ItemRecipeBinding

class FavoriteListAdapter(var dataSet: List<Recipe>) :
    RecyclerView.Adapter<FavoriteListAdapter.ViewHolder>() {

    fun interface OnItemClickListener {
        fun onItemClick(recipeId: Int)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("StringFormatInvalid")
        fun bind(recipe: Recipe) {
            binding.tvRecipeName.text = recipe.title
            try {
                Glide.with(itemView.context)
                    .load(IMAGE_BASE_URL+recipe.imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_error)
                    .into(binding.ivRecipe)
                binding.ivRecipe.contentDescription = String.format(
                    itemView.context.getString(R.string.recipe_image_description),
                    recipe.title
                )
            } catch (e: Exception) {
                Log.e("RecipesListFragment", "Ошибка загрузки изображения: ${recipe.title}", e)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = ItemRecipeBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val recipe = dataSet[position]
        viewHolder.bind(recipe)

        viewHolder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(recipeId = recipe.id)
        }
    }
    fun newData(dataSet: List<Recipe>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataSet.size
}