package com.example.petsocial.feature.login

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.petsocial.R
import com.example.petsocial.common.BaseViewBindingFragment
import com.example.petsocial.databinding.FragmentLoginBinding
import com.example.petsocial.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseViewBindingFragment<FragmentLoginBinding>() {
    lateinit var viewModel: LoginViewModel
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater,container,false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            if (Util.auth.currentUser != null) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }, 3000)
        binding.loginButton.setOnClickListener{
            with(binding){
                viewModel.loginUser(userLog.text.toString(), passwordLog.text.toString())

            }
        }
        binding.signUpText.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registirationFragment)
        }

        initObserve()

    }
    private fun initObserve(){
        CoroutineScope(Dispatchers.Main).launch{
            viewModel.loginSuccess.collect{success ->
                success?.let {
                    if (it){
                        viewModel.getUserInfo()
                    }else{
                        Toast.makeText(context, "Unsuccessfully login", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch{
            viewModel.userInfo.collect{success ->
                success?.let {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }

            }
        }

    }
}