package com.example.petsocial.feature.registiration

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.petsocial.common.BaseViewBindingFragment
import com.example.petsocial.databinding.FragmentRegistirationBinding
import com.example.petsocial.room.UserInfo
import com.example.petsocial.util.ValidationUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.UUID


@AndroidEntryPoint
class RegistirationFragment : BaseViewBindingFragment<FragmentRegistirationBinding>(),
    DatePickerDialog.OnDateSetListener {
    private var date:String?=null
    private val viewModel: RegistirationViewModel by viewModels()


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegistirationBinding {
        return FragmentRegistirationBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButton.setOnClickListener {
            signUp()
        }
        binding.birthday.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun signUp() {
        val userEmail = binding.userSign.text.toString().trim()
        val userPassword = binding.passwordSign.text.toString().trim()
        val userName = binding.userNameET.text.toString()
        val userPetName = binding.petName.text.toString()
        val userImage = binding.signImageView.toString()
        val uuid = UUID.randomUUID().toString()

        if (!ValidationUtil.isValidEmail(userEmail)) {
            Toast.makeText(
                requireContext(),
                "Please fill true an email address",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        ValidationUtil.isValidPassword(userPassword)
        if(date==null){
            Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
            return
        }
        val userInfo = UserInfo(uuid, userEmail, userName, userPetName, userImage, date.toString())
        createNewUser(userInfo, userPassword)
    }

    private fun createNewUser(userInfo: UserInfo, userPassword: String) {
        viewModel.createUser(userInfo, userPassword)
        initObserve()
    }
    private fun initObserve() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.registrationSuccess.collect { success ->
                success?.let {
                    if (it) {
                        findNavController().popBackStack()
                        Toast.makeText(context, "Sign in Success", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Sign in Unsuccessful", Toast.LENGTH_LONG)
                            .show()
                        Log.d("initObserve", "initObserve: signIn Unsuccess")
                    }
                }
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.errorString.collect { error ->
                withContext(Dispatchers.Main) {
                    error?.let {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }

                }

            }
        }
        CoroutineScope(Dispatchers.IO).launch {

            viewModel.loadingState.collect { loading ->
                withContext(Dispatchers.Main) {
                    loading?.let {
                        if (loading) {
                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }

    }


    private fun showDatePickerDialog() {
        CoroutineScope(Dispatchers.Main).launch {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog =
                DatePickerDialog(requireContext(), this@RegistirationFragment, year, month, day)
            datePickerDialog.show()
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            date = String.format("%02d / %02d / %d", day, month, year)
            binding.birthday.setText(date)
            viewModel.formatDate(year, month, day)

        }
    }

}