package com.project.githubuser.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.project.githubuser.data.response.ItemsItem
import com.project.githubuser.databinding.ItemLayoutBinding

class UserAdapter(private val data : MutableList<ItemsItem> = mutableListOf(),
                  private val listener:(ItemsItem)-> Unit
):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(){
        fun setData(data: MutableList<ItemsItem>){
            this.data.clear()
            this.data.addAll(data)
            notifyDataSetChanged()
        }

    class UserViewHolder(private val binding: ItemLayoutBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(item: ItemsItem){
                    binding.imgProfile.load(item.avatarUrl){
                        transformations(CircleCropTransformation())
                    }
                    binding.tvUsername.text = item.login
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false ))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val  item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            listener(item)
        }
    }
}