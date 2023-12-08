package com.shobhit97.sample.presentation.users_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shobhit97.sample.R
import com.shobhit97.sample.databinding.ListItemBinding
import com.shobhit97.sample.domain.model.User

class UserAdapter(
    private val context: Context,
    private val onLikeClick: (user:User) -> Unit,
    private val onUnLikeClick:(user:User) -> Unit,
) : ListAdapter<User, UserAdapter.ViewHolder>(DIFF_UTIL) {

   inner class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            Glide.with(binding.root)
                .load(user.avatar)
                .error(R.drawable.ic_launcher_background)
                .into(binding.profilePicture)

            binding.name.text =  context.getString(R.string.user_full_name,user.first_name,user.last_name)
            binding.email.text = user.email
            binding.favoriteIcon.setOnClickListener {
                onLikeClick(user)
            }
            binding.notFavoriteIcon.setOnClickListener {
                onUnLikeClick(user)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(
                oldItem: User, newItem: User
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: User, newItem: User
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}