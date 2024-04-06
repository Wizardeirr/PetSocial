package com.example.petsocial.feature.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.petsocial.R
import com.example.petsocial.common.BaseViewBindingFragment
import com.example.petsocial.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseViewBindingFragment<FragmentHomeBinding>() {
    @Inject
    lateinit var homeRecyclerAdapter: HomeRecyclerAdapter
    private val viewModel: HomeViewModel by viewModels()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_postFragment)
        }
        binding.usersHomeFragmentRecycler.adapter = homeRecyclerAdapter
        binding.usersHomeFragmentRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        initObserve()
        viewModel.getPostsData()
    }
    private fun initObserve() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.postList.collect {
                homeRecyclerAdapter.posts = it
            }
        }
    }
}
