package com.shobhit97.sample.presentation.users_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.shobhit97.sample.R
import com.shobhit97.sample.databinding.FragmentUserListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment() {
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserListViewModel by viewModels()
    private var userAdapter: UserAdapter? = null
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        viewModel.getUsers()

        binding.favoriteListTxt.setOnClickListener {
            navController.navigate(R.id.action_userListFragment_to_favoriteUserListFragment)
        }

        bindRecyclerView()
        bindStateObserver()

    }

    private fun bindStateObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.state.collect {
                        //loading
                        if (it.isLoading) {
                            binding.loadingStatus.visibility = View.VISIBLE
                        } else {
                            binding.loadingStatus.visibility = View.GONE
                        }

                        //error
                        it.error?.let { error ->
                            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                        }

                        //users data
                        it.users?.data.let { users ->
                            userAdapter?.submitList(users)
                        }
                    }
                }
            }
        }
    }

    private fun bindRecyclerView() {
        userAdapter = UserAdapter(
            requireContext(),
            onLikeClick = { user ->
                viewModel.addFavUser(user)
            },
            onUnLikeClick = { user ->
                viewModel.removeFavUser(user)
            }
        )
        binding.usersRv.apply {
            adapter = userAdapter
            setHasFixedSize(true)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}