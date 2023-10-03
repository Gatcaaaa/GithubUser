package com.project.githubuser.ui.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubuser.data.response.UserResponse
import com.project.githubuser.databinding.FragmentFollowBinding
import com.project.githubuser.ui.detail.DetailViewModel
import com.project.githubuser.ui.home.UserAdapter
import com.project.githubuser.ui.utils.Result

class FollowFragment : Fragment() {
   private var binding: FragmentFollowBinding? = null
    private val adapter by lazy {
        UserAdapter {

        }
    }
    private val viewModel by activityViewModels<DetailViewModel>()
    var type = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.rvFollows?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowFragment.adapter
        }
        when(type){
            FOLLOWERS -> {
                viewModel.resultFollowers.observe(viewLifecycleOwner, this::manageResultFollows)
            }
            FOLLOWING -> {
                viewModel.resultFollowing.observe(viewLifecycleOwner, this::manageResultFollows)
            }
        }
    }

    private fun manageResultFollows(state: Result){
        when(state){
            is Result.Success<*> -> {
                adapter.setData(state.data as MutableList<UserResponse.ItemsItem>)
            }
            is Result.Error -> {
                Toast.makeText(requireContext(),state.exception.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Result.Loading -> {
                binding?.progressBar?.isVisible = state.isLoading
            }
        }
    }

    companion object {
        const val FOLLOWING = 100
        const val FOLLOWERS = 101

        fun newInstance(type: Int) = FollowFragment()
            .apply {
                this.type = type
            }

    }
}