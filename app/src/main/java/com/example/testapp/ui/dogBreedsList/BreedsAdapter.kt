package com.example.testapp.ui.dogBreedsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.data.network.response.Breed
import com.example.testapp.databinding.ItemBreedBinding
import com.example.testapp.tools.setVisible

class BreedsAdapter(private val DogClickCallback: ((Breed) -> Unit)) :
    RecyclerView.Adapter<BreedsAdapter.BreedViewHolder>() {

    private val mainList: ArrayList<Breed> = ArrayList()

    fun setBreeds(list: List<Breed>) {
        val breedDiffUtilCallback = DogDiffUtilCallback(mainList, list)
        val result = DiffUtil.calculateDiff(breedDiffUtilCallback)

        mainList.clear()
        mainList.addAll(list)

        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val binding =
            ItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        val item = mainList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = mainList.size

    inner class BreedViewHolder(private val binding: ItemBreedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                mainList.getOrNull(adapterPosition)?.let(DogClickCallback)
            }
        }

        fun bind(item: Breed) {
            binding.run {
                breedTitle.text = item.value.replaceFirstChar(Char::titlecase)
                if (item.subBreeds.isNotEmpty()) {
                    val subBreeds =
                        item.subBreeds.joinToString("\n") { it.value.replaceFirstChar(Char::titlecase) }
                    breedDescription.text = subBreeds
                    breedDescription.setVisible(true)
                } else {
                    breedDescription.setVisible(false)
                }
            }
        }

    }

    private class DogDiffUtilCallback(
        private val oldList: List<Breed>,
        private val newList: List<Breed>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return (oldItem.value == newItem.value) && (oldItem.subBreeds.size == newItem.subBreeds.size)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true

    }

}