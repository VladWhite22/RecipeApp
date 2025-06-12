package com.example.recipeapp.ui.recipes.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.ItemMethodBinding

class MethodsAdapther(public var dataSet: List<String>) :
    RecyclerView.Adapter<MethodsAdapther.ViewHolder>() {

    class ViewHolder(binding: ItemMethodBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.tvMethod
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            ItemMethodBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val method: String = dataSet[position]

        holder.textView.text = "${position + 1}. $method"

    }
    fun newData(dataSet: List<String>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataSet.size

}
