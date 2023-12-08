package com.shobhit97.sample.presentation.favorite_user_list

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
import com.shobhit97.sample.R
import com.shobhit97.sample.databinding.FragmentFavoriteUserListBinding
import com.shobhit97.sample.databinding.FragmentUserListBinding
import com.shobhit97.sample.presentation.users_list.UserAdapter
import com.shobhit97.sample.presentation.users_list.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteUserListFragment : Fragment() {

    private var _binding: FragmentFavoriteUserListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavUserViewModel by viewModels()
    private var favoriteUserAdapter: FavoriteUserAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindRecyclerView()
        bindStateObserver()
    }

    private fun bindStateObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.getFavUsers().collect {
                        favoriteUserAdapter?.submitList(it)
                    }
                }
            }
        }
    }

    private fun bindRecyclerView() {
        favoriteUserAdapter = FavoriteUserAdapter(
            requireContext(),
            onUnlikeClick = {user ->
                viewModel.removeFavUser(user)
            }
        )
        binding.favUsersRv.apply {
            adapter = favoriteUserAdapter
            setHasFixedSize(true)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}