package com.example.testapp.ui.dogsForBreedList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.testapp.data.network.response.Breed
import com.example.testapp.databinding.ItemBreedBinding
import com.example.testapp.databinding.ItemDogForBreedBinding

class DogsForBreedAdapter() :
    RecyclerView.Adapter<DogsForBreedAdapter.DogViewHolder>() {

    private val mainList: ArrayList<String> = ArrayList()

    fun setDogsImages(list: List<String>) {
        val breedDiffUtilCallback = DogDiffUtilCallback(mainList, list)
        val result = DiffUtil.calculateDiff(breedDiffUtilCallback)

        mainList.clear()
        mainList.addAll(list)

        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding =
            ItemDogForBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val item = mainList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = mainList.size

    inner class DogViewHolder(private val binding: ItemDogForBreedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.run {

                val circularProgressDrawable = CircularProgressDrawable(image.context)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 50f
                circularProgressDrawable.start()

                Glide.with(image)
                    .load(item)
                    .placeholder(circularProgressDrawable)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image)

            }
        }

    }

    private class DogDiffUtilCallback(
        private val oldList: List<String>,
        private val newList: List<String>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true

    }

}