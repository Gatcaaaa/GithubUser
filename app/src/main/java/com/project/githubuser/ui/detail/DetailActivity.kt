package com.project.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.project.githubuser.R
import com.project.githubuser.data.response.DetailUserResponse
import com.project.githubuser.databinding.ActivityDetailBinding
import com.project.githubuser.ui.follow.FollowFragment
import com.project.githubuser.ui.follow.PageAdapter
import com.project.githubuser.ui.utils.Result

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra("username") ?: ""

        viewModel.resultDetailUser.observe(this){
            when(it){
                is Result.Success<*> -> {
                    val user = it.data as DetailUserResponse
                    binding.ivProfile.load(user.avatarUrl){
                        transformations(CircleCropTransformation())
                    }

                    binding.tvName.text = user.name
                    binding.tvUsername.text = user.login

                    val followersText = getString(R.string.followersTemplate, user.followers.toString())
                    val followingText = getString(R.string.followingTemplate, user.following.toString())

                    binding.tvTotalFollowers.text = followersText
                    binding.tvTotalFollowing.text = followingText
                }
                is Result.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }
        viewModel.getDetailUser(username)

        val fragment = mutableListOf<Fragment>(
            FollowFragment.newInstance(FollowFragment.FOLLOWERS),
            FollowFragment.newInstance(FollowFragment.FOLLOWING)
        )

        val titleFragment = mutableListOf(
            getString(R.string.tab_text_1),
            getString(R.string.tab_text_2)
        )
        val adapter = PageAdapter(this, fragment)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager){tab, position ->
            tab.text = titleFragment[position]
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0){
                    viewModel.getFollowers(username)
                }else{
                    viewModel.getFollowing(username)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        viewModel.getFollowers(username)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}