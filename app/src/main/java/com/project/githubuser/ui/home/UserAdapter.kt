package com.project.githubuser.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.project.githubuser.data.response.UserResponse
import com.project.githubuser.databinding.ItemLayoutBinding

class UserAdapter(
    private val data: MutableList<UserResponse.ItemsItem> = mutableListOf(),
    private val listener: (UserResponse.ItemsItem) -> Unit
) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    fun setData(data: MutableList<UserResponse.ItemsItem>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val b: ItemLayoutBinding) :
        RecyclerView.ViewHolder(b.root) {
        fun bind(item: UserResponse.ItemsItem) {
            b.imgProfile.load(item.avatarUrl) {
                transformations(CircleCropTransformation())
            }
            b.tvUsername.text = item.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }
}