package com.example.petsocial.feature.post

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.example.petsocial.R
import com.example.petsocial.common.BaseViewBindingFragment
import com.example.petsocial.databinding.FragmentPostBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PostFragment @Inject constructor(private val glide: RequestManager)
    :BaseViewBindingFragment<FragmentPostBinding>() {

    private val viewModel: PostViewModel by viewModels()
    private var picture:String=""
    private var animalType:String=""
    private var animalEstrusPeriod:String=""
    private var geniusInfo:String=""
    private var vaccationInfo:String=""

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPostBinding {
        return FragmentPostBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        takesPermAndPhoto()
        animalRadioGroupChange()
        estrusRadioGroupChange()
        binding.createPostButton.setOnClickListener {
            val ageInfo=binding.ageEditText.text.toString()
            val postData=PostData(picture,animalType,geniusInfo,ageInfo,vaccationInfo,animalEstrusPeriod)
            viewModel.postSave(postData)
        }
    }

    private fun takesPermAndPhoto(){
        val register=registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                data?.data
                glide.load(data?.data).into(binding.selectedImage)
                println( data?.data)
            }
        }
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                val gallery = Intent(MediaStore.ACTION_PICK_IMAGES)
                gallery.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*")
                register.launch(gallery)
                picture= gallery.data.toString()

            } else {
                Log.d("isGranted", "onViewCreated:  İzin hatası ")
            }
        }
        binding.postPhotoAdd.setOnClickListener {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
            }
        }
    }
    private fun showCatGeniusList(){
        lifecycleScope.launch(Dispatchers.Main){
            viewModel.catGeniusOptions.collect {
                updateGeniusSpinner(it)
            }
        }
    }
    private fun showDogGeniusList(){
        lifecycleScope.launch(Dispatchers.Main){
            viewModel.dogGeniusOptions.collect {
                updateGeniusSpinner(it)
            }
        }
    }
    private fun showCatVacList(){
        lifecycleScope.launch(Dispatchers.Main){
            viewModel.catVaccineOptions.collect {
                updateVaccinationSpinner(it)
            }
        }
    }
    private fun showDogVacList(){
        lifecycleScope.launch(Dispatchers.Main){
            viewModel.dogVaccineOptions.collect {
                updateVaccinationSpinner(it)
            }
        }
    }
    private fun updateGeniusSpinner(options: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.geniusInfoSpinner.adapter = adapter
        binding.geniusInfoSpinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedGenius = p0?.getItemAtPosition(p2).toString()
                geniusInfo=selectedGenius
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }
    private fun updateVaccinationSpinner(options: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.vaccinationSpinner.adapter = adapter
        binding.vaccinationSpinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedVac = p0?.getItemAtPosition(p2).toString()
                vaccationInfo=selectedVac
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }
    private fun animalRadioGroupChange(): View? {
        binding.animalTypeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            // checkedId, seçilen RadioButton'ın ID'sidir
            val selectedRadioButton: RadioButton = requireActivity().findViewById(checkedId)
            val selectedAnimalType: String = selectedRadioButton.text.toString()
            animalType=selectedAnimalType
            when (checkedId) {
                R.id.catRadioButton -> {
                    showCatGeniusList()
                    showCatVacList()
                }
                R.id.dogRadioButton -> {
                    showDogGeniusList()
                    showDogVacList()
                }
            }
        }
        return view
    }
    private fun estrusRadioGroupChange(): View? {
        binding.animalsEstrusPeriod.setOnCheckedChangeListener { _, checkedId ->
            // checkedId, seçilen RadioButton'ın ID'sidir
            val selectedRadioButton: RadioButton = requireActivity().findViewById(checkedId)
            val selectedEstrusPeriod: String = selectedRadioButton.text.toString()
            animalEstrusPeriod=selectedEstrusPeriod
            when (checkedId) {
                R.id.yesRadio -> {
                    // Kedi seçildiğinde yapılacak işlemler
                }
                R.id.noRadio -> {
                    // Köpek seçildiğinde yapılacak işlemler
                }
            }
        }
        return view
    }
    }
