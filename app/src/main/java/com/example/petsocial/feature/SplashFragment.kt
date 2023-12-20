package com.example.petsocial.feature

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.petsocial.R
import com.example.petsocial.common.BaseViewBindingFragment
import com.example.petsocial.databinding.FragmentSplashBinding
import com.example.petsocial.util.Util


class SplashFragment : BaseViewBindingFragment<FragmentSplashBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
                if (Util.auth.currentUser != null) {
                    lifecycleScope.launchWhenResumed {
                        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                    }
                } else{
                    lifecycleScope.launchWhenResumed {
                        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    }
                }
        }, 3000)
    }
}