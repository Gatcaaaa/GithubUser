package com.project.githubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubuser.database.DatabaseConfig
import com.project.githubuser.databinding.ActivityFavoriteBinding
import com.project.githubuser.ui.detail.DetailActivity
import com.project.githubuser.ui.home.UserAdapter

class FavoriteActivity : AppCompatActivity() {


    private val viewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModel.factory(DatabaseConfig(this))
    }

    private lateinit var binding: ActivityFavoriteBinding
    private val adapter by lazy {
        UserAdapter {user ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra("item", user)
                startActivity(this)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerData()
    }

    override fun onResume() {
        super.onResume()
 
        viewModel.getFavoriteUser().observe(this){ favoriteList ->
            adapter.setData(favoriteList)

        }
    }

    private fun setRecyclerData(){
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter =  adapter
    }
}