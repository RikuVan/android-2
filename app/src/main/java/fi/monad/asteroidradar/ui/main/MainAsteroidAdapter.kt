package fi.monad.asteroidradar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fi.monad.asteroidradar.databinding.AsteroidListItemBinding
import fi.monad.asteroidradar.domain.Asteroid

class MainAsteroidAdapter(private val onClick: (Asteroid) -> Unit) :
    ListAdapter<Asteroid, MainAsteroidAdapter.AsteroidViewHolder>(AsteroidDiffCallback) {

    class AsteroidViewHolder(private val binding: AsteroidListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): AsteroidViewHolder {
                val view = AsteroidListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return AsteroidViewHolder(view)
            }
        }

        fun bind(item: Asteroid) {
            binding.asteroid = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bind(asteroid)
        holder.itemView.setOnClickListener {
            onClick.invoke(asteroid)
        }
    }
}

object AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }
}