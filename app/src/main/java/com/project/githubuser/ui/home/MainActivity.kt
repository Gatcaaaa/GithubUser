package com.project.githubuser.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubuser.R
import com.project.githubuser.data.datastore.SettingPreferences
import com.project.githubuser.data.response.UserResponse
import com.project.githubuser.databinding.ActivityMainBinding
import com.project.githubuser.ui.detail.DetailActivity
import com.project.githubuser.ui.favorite.FavoriteActivity
import com.project.githubuser.ui.setting.SettingActivity
import com.project.githubuser.ui.utils.Result

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter { user ->
            startActivityDetail(user)
        }
    }
    private val viewModel by viewModels<mainViewModel>{
        mainViewModel.Factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getListRecyclerView()
        getThemeMode()
        getSearchViewData()
        getResultData()

        viewModel.getUser("dicoding")
    }

    private fun getThemeMode() {
        viewModel.getThemeMode().observe(this){
            val modeMalam = if (it){
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(modeMalam)
        }
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
    startActivity
    intent ke
    detail
     */
    private fun startActivityDetail(user : UserResponse.ItemsItem) {
        Intent(this, DetailActivity::class.java).apply {
            putExtra("item", user)
            startActivity(this)
        }
    }

    /*
    Override OptionMenu
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val  inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> startFavoriteActivity()
            R.id.setting -> startSettingActivity()
        }

        return super.onOptionsItemSelected(item)
    }
    /*
    startactivity
    intent
    favorit
     */

    private fun startFavoriteActivity() {
        Intent(this, FavoriteActivity::class.java).apply {
            startActivity(this)
        }
    }

    /*
    startactivity
    intent
    setting
     */

    private fun startSettingActivity(){
        Intent(this, SettingActivity::class.java).apply {
            startActivity(this)
        }
    }
}