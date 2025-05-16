package com.example.recipeapp.Recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aseducationalproject.Domain.Ingredient
import com.example.recipeapp.databinding.ItemIngredientBinding

class IngredientsAdapter(private val dataSet: List<Ingredient>):
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemIngredientBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.tvIngredient
        val quantity: TextView = binding.tvQuantity
}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {

        val binding =
            ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ingredient: Ingredient = dataSet[position]

        holder.textView.text = ingredient.description
        holder.quantity.text = "${ingredient.quantity} ${ingredient.unitOfMeasure}"

    }

    override fun getItemCount(): Int = dataSet.size


}
