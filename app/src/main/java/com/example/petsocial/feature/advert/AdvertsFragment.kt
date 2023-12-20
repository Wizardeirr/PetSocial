package com.example.petsocial.feature.advert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petsocial.common.BaseViewBindingFragment
import com.example.petsocial.databinding.FragmentAdvertsBinding

class AdvertsFragment : BaseViewBindingFragment<FragmentAdvertsBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdvertsBinding {
        return FragmentAdvertsBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}