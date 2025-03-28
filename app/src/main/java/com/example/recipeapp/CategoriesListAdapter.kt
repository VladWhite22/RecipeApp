package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoriesListAdapter(private val dataSet: List<Category>) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val ivCategory: ImageView = view.findViewById(R.id.ivCategory)
        val tvCategoryName: TextView = view.findViewById(R.id.tvCategoryName)
        val tvCategoryDescription: TextView = view.findViewById(R.id.tvCategoryDescription)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.item_category,viewGroup,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val category: Category = dataSet[position]
        //viewHolder.ivCategory. = category.imageUrl
        viewHolder.tvCategoryName.text = category.title
        viewHolder.tvCategoryDescription.text = category.description
    }

    override fun getItemCount() = dataSet.size


}