package com.example.petsocial.feature.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petsocial.common.BaseViewBindingFragment
import com.example.petsocial.databinding.FragmentMessagesBinding


class MessagesFragment : BaseViewBindingFragment<FragmentMessagesBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMessagesBinding {
        return FragmentMessagesBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }

}