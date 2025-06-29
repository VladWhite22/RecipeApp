package com.example.recipeapp.categoresList

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ItemCategoryBinding
import com.example.recipeapp.model.Category
import com.bumptech.glide.Glide
import com.example.recipeapp.IMAGE_BASE_URL

class CategoriesListAdapter(var dataSet: List<Category>) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(categoryId: Int)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.tvCategoryName.text = category.title
            binding.tvCategoryDescription.text = category.description
            try {
                Glide.with(itemView.context)
                    .load(IMAGE_BASE_URL + category.imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_error)
                    .into(binding.ivCategory)
            } catch (e: Exception) {
                Log.e("!!", "Ошибка загрузки изображения: ${category.title}")
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = ItemCategoryBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val category = dataSet[position]
        viewHolder.bind(category)

        viewHolder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(categoryId = category.id)
        }
    }

    fun newData(dataSet: List<Category>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataSet.size
}