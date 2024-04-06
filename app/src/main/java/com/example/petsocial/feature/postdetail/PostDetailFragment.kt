package com.example.petsocial.feature.postdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.petsocial.R
import com.example.petsocial.common.BaseViewBindingFragment
import com.example.petsocial.databinding.FragmentPostDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PostDetailFragment: BaseViewBindingFragment<FragmentPostDetailBinding>() {
    @get:Inject
    val viewModel: PostDetailViewModel by viewModels()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPostDetailBinding {
        return FragmentPostDetailBinding.inflate(inflater, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args = requireArguments()
        val postID = args.getString("postID")
        val userID = args.getString("userID")
        val bundleOfUserID= bundleOf("userID" to userID)
        binding.contactUserID.setOnClickListener {
            Toast.makeText(requireContext(), userID, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_postDetailFragment_to_privateMessageFragment,bundleOfUserID)
        }
        bindPostDetailsToViews()
        viewModel.showPostDetails(postID,binding.postImage)

        initObserve()
        super.onViewCreated(view, savedInstanceState)
    }
    private fun initObserve(){
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.loadingState.collect{
                if (it == true){
                    binding.takePostPB.visibility=View.INVISIBLE
                }else{
                    binding.takePostPB.visibility=View.VISIBLE

                }
            }
        }

    }
    @SuppressLint("SetTextI18n")
    private fun bindPostDetailsToViews() {
        val args = requireArguments()
        binding.apply {
            animalAgeText.text = "Age: ${args.getString("animalAge")}"
            animalGeniusText.text = "Genius: ${args.getString("animalGenius")}"
            animalEstrusPeriodText.text = "Estrus Period: ${args.getString("animalEstrusPeriod")}"
            animalVacationText.text = "Vacation: ${args.getString("animalVacation")}"
            postTitleText.text = args.getString("postTitle")
            animalTypeText.text = "Type: ${args.getString("animalType")}"
        }
    }
}