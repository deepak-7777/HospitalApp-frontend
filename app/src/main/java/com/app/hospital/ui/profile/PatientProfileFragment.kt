package com.app.hospital.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.hospital.R
import com.app.hospital.data.api.PatientProfile
import com.app.hospital.databinding.FragmentPatientProfileBinding
import com.app.hospital.utils.NetworkResult
import com.app.hospital.utils.gone
import com.app.hospital.utils.showToast
import com.app.hospital.utils.visible
import com.app.hospital.viewmodel.PatientViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PatientProfileFragment : Fragment() {

    private var _binding: FragmentPatientProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PatientViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPatientProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnRetry.setOnClickListener {
            viewModel.fetchProfile()
        }

        observeProfileState()
        viewModel.fetchProfile()
    }

    private fun observeProfileState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profileState.collect { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        binding.progressBar.visible()
                        binding.layoutContent.gone()
                        binding.layoutError.gone()
                    }
                    is NetworkResult.Success -> {
                        binding.progressBar.gone()
                        binding.layoutError.gone()
                        binding.layoutContent.visible()
                        populateProfile(result.data)
                    }
                    is NetworkResult.Error -> {
                        binding.progressBar.gone()
                        binding.layoutContent.gone()
                        binding.layoutError.visible()
                        binding.tvErrorMessage.text = result.message
                        showToast(result.message)
                        // If 401, redirect to login
                        if (result.code == 401) {
                            findNavController().navigate(R.id.action_patientProfileFragment_to_loginFragment)
                        }
                    }
                    null -> { /* idle */ }
                }
            }
        }
    }

    private fun populateProfile(profile: PatientProfile) {
        binding.tvName.text = profile.name
        binding.tvUsername.text = "@${profile.username}"
        binding.tvEmail.text = profile.email ?: "Not provided"
        binding.tvPhone.text = profile.phone ?: "Not provided"
        binding.tvDob.text = profile.dateOfBirth ?: "Not provided"
        binding.tvGender.text = profile.gender ?: "Not provided"
        binding.tvAddress.text = profile.address ?: "Not provided"
        binding.tvBloodGroup.text = profile.bloodGroup ?: "Not provided"
        binding.tvRoles.text = profile.roles.joinToString(", ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
