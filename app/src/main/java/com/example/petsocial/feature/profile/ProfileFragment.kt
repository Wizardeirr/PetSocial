package com.example.petsocial.feature.profile

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.ext.SdkExtensions
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.bumptech.glide.RequestManager
import com.example.petsocial.common.BaseViewBindingFragment
import com.example.petsocial.databinding.FragmentProfileBinding
import com.example.petsocial.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment @Inject constructor(private val glide: RequestManager) :
    BaseViewBindingFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.updateInformation.setOnClickListener {
            updateUserInfo()
        }
        binding.logoutButton.setOnClickListener {
            Util.auth.signOut()
        }
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                val gallery =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && SdkExtensions.getExtensionVersion(
                            Build.VERSION_CODES.R) >= 2) {
                        Intent(MediaStore.ACTION_PICK_IMAGES)
                    } else {
                        TODO("SdkExtensions.getExtensionVersion(R) < 2")
                    }
                startActivity(gallery)
            } else {
                Log.d("isGranted", "onViewCreated:  İzin hatası ")
            }
        }
        binding.imageAddButton.setOnClickListener {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
            }
        }

        initObserve()
    }

    private fun initObserve() {

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.user.collect {
                it?.let { userInfo ->
                    with(binding) {
                        userEmail.setText(userInfo.userEmail)
                        username.setText(userInfo.userName)
                        userPetName.setText(userInfo.petName)
                        birthday.setText(userInfo.birthday)
                    }
                }
            }
        }

    }
    private fun updateUserInfo(){
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.userUpdate.collect{
                it?.let {
                   with(binding){
                       userEmail.setText(it.userEmail)
                       username.setText(it.userName)
                       userPetName.setText(it.petName)
                       birthday.setText(it.birthday)
                   }
                }
            }
        }

    }

}