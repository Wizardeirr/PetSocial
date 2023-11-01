package com.example.petsocial.feature.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petsocial.R
import com.example.petsocial.common.BaseViewBindingFragment
import com.example.petsocial.databinding.FragmentSignInBinding
import com.example.petsocial.databinding.FragmentSignUpBinding

class SignUpFragment : BaseViewBindingFragment<FragmentSignUpBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpBinding {
        return FragmentSignUpBinding.inflate(inflater,container,false)
    }
}