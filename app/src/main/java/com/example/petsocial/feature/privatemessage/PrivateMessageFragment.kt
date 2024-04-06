package com.example.petsocial.feature.privatemessage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.petsocial.R
import com.example.petsocial.common.BaseViewBindingFragment
import com.example.petsocial.databinding.FragmentPrivateMessageBinding
import com.example.petsocial.util.Util.auth

class PrivateMessageFragment : BaseViewBindingFragment<FragmentPrivateMessageBinding>() {
    private val viewModel: PrivateMessageViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPrivateMessageBinding {
        return FragmentPrivateMessageBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        val args = requireArguments()
        val userID = args.getString("userID")
        binding.buttonSend.setOnClickListener {
            if (userID!=null&& !auth.currentUser?.uid.isNullOrEmpty()){
                viewModel.sendPrivateMessage(auth.currentUser!!.uid,userID,binding.editTextMessage.text.toString())
            }
        }
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.user_settings, menu)

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.report_user -> {
                        // clearCompletedTasks()
                        true
                    }
                    R.id.block_user -> {
                        // loadTasks(true)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}