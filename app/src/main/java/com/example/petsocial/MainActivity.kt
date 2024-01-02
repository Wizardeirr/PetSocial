package com.example.petsocial

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.petsocial.databinding.ActivityMainBinding
import com.example.petsocial.util.Util
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    @Inject
    lateinit var fragmentFactory: CustomFragmentFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportFragmentManager.fragmentFactory=fragmentFactory
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        NavigationUI.setupWithNavController(
            binding.bottomNavigation,
            navHostFragment.navController
        )

        val authStateListener =
            FirebaseAuth.AuthStateListener { firebaseAuth ->
                if (firebaseAuth.currentUser == null) {
                    navHostFragment.findNavController().navigate(R.id.splashFragment)
                }
            }


        Util.auth.addAuthStateListener(authStateListener)

        navHostFragment.navController.addOnDestinationChangedListener{_, destination, _ ->

            when(destination.id){
                R.id.splashFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.homeFragment ->{
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.registirationFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.loginFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.postFragment->{
                    binding.bottomNavigation.visibility=View.GONE
                }
                else -> {
                binding.bottomNavigation.visibility = View.VISIBLE
                }

            }

        }




    }
}