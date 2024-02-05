package com.example.hw21.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.hw21.R
import com.example.hw21.databinding.FilterItemBinding
import com.example.hw21.presentation.model.FilterItemUiModel

class CategoryAdapter (private val click: (FilterItemUiModel) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.FilterListVH>() {
    private var list: List<FilterItemUiModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<FilterItemUiModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class FilterListVH(binding: FilterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val filterType = binding.tvFilter
        private val cv = binding.cvFilterItem
        fun bind(model: FilterItemUiModel, click: (FilterItemUiModel) -> Unit) {
            filterType.text = model.filterName.name
            filterType.setTextColor(model.txtColor)
            itemView.setOnClickListener {
                click(model)
            }
            if (model.isActive) {
                filterType.setTextColor(ContextCompat.getColor(filterType.context, R.color.white))
                cv.setBackgroundColor(ContextCompat.getColor(filterType.context, R.color.green))
            } else {
                filterType.setTextColor(ContextCompat.getColor(filterType.context, R.color.txtGray))
                cv.setBackgroundColor(ContextCompat.getColor(filterType.context, R.color.darkBlue))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterListVH {
        val binding = FilterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterListVH(binding)
    }

    override fun onBindViewHolder(holder: FilterListVH, position: Int) {
        val item = list[position]
        holder.bind(item, click)
    }

    override fun getItemCount(): Int = list.size
}