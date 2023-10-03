package com.project.githubuser.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubuser.data.response.UserResponse
import com.project.githubuser.databinding.ActivityMainBinding
import com.project.githubuser.ui.detail.DetailActivity
import com.project.githubuser.ui.utils.Result

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter { user ->
            startActivityDetail(user)
        }
    }
    private val viewModel by viewModels<mainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getListRecyclerView()
        /*
        getThemeMode()
         */
        getSearchViewData()
        getResultData()

        viewModel.getUser("dicoding")
    }
    private fun getResultData(){
        viewModel.resultUser.observe(this){
            when(it){
                is  Result.Success<*> -> {
                   val data = it.data as MutableList<UserResponse.ItemsItem>
                    adapter.setData(data)
                }
                is  Result.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }
    }
    private fun getSearchViewData(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               query?.let {
                   viewModel.getUser(it)
               }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
    private fun getListRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    /*
    startDetailActivity
     */
    private fun startActivityDetail(user : UserResponse.ItemsItem) {
        Intent(this, DetailActivity::class.java).apply {
            putExtra("item", user)
            startActivity(this)
        }
    }
}