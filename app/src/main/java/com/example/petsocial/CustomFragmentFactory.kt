package com.example.petsocial

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.petsocial.feature.SplashFragment
import com.example.petsocial.feature.login.LoginFragment
import com.example.petsocial.feature.post.PostFragment
import com.example.petsocial.feature.profile.ProfileFragment
import com.example.petsocial.feature.registiration.RegistirationFragment
import javax.inject.Inject

class CustomFragmentFactory@Inject constructor(
    val glide:RequestManager
):FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            RegistirationFragment::class.java.name -> RegistirationFragment()
            SplashFragment::class.java.name->SplashFragment()
            ProfileFragment::class.java.name -> ProfileFragment(glide)
            LoginFragment::class.java.name->LoginFragment()
            PostFragment::class.java.name->PostFragment(glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}
