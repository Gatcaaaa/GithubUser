package com.project.githubuser.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubuser.data.response.ItemsItem
import com.project.githubuser.databinding.ActivityMainBinding
import com.project.githubuser.ui.detail.DetailActivity
import com.project.githubuser.ui.utils.Result

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter{
            Intent(this, DetailActivity::class.java).apply {
                putExtra("username", it.login)
                startActivity(this)
            }
        }
    }
    private val viewModel by viewModels<mainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getUser(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getUser(newText.toString())
                return true
            }
        })

        viewModel.resultUser.observe(this){
            when(it){
                is  Result.Success<*> -> {
                    adapter.setData(it.data as MutableList<ItemsItem>)
                }
                is  Result.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }

        viewModel.getUser()
    }
}