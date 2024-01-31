package com.example.hw21.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hw21.R
import com.example.hw21.databinding.PurchaseItemBinding
import com.example.hw21.presentation.base.loadImage
import com.example.hw21.presentation.model.ClothesUiModel


class ClothesAdapter :
    ListAdapter<ClothesUiModel, ClothesAdapter.ClothesViewHolder>(ClothesDiffCallback) {

    var onClick: ((ClothesUiModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothesViewHolder {
        return ClothesViewHolder(PurchaseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ClothesViewHolder, position: Int) {
        holder.bind()
    }

    inner class ClothesViewHolder(private val binding: PurchaseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val clothes = currentList[adapterPosition]
            with(binding) {
                ivPhoto.loadImage(clothes.cover)
                tvTitle.text = clothes.title
                tvPrice.text = clothes.price

                with(ivHeart) {
                    if (clothes.favorite) {
                        setImageResource(R.drawable.ic_heart)
                    } else {
                        setImageResource(R.drawable.ic_heart_empty)
                    }

                    setOnClickListener {
                        onClick?.invoke(clothes)
                    }
                }
            }
        }
    }

    companion object {
        private val ClothesDiffCallback = object : DiffUtil.ItemCallback<ClothesUiModel>() {

            override fun areItemsTheSame(oldItem: ClothesUiModel, newItem: ClothesUiModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ClothesUiModel, newItem: ClothesUiModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}