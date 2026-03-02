package com.app.hospital.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.hospital.R
import com.app.hospital.databinding.FragmentSignupBinding
import com.app.hospital.utils.NetworkResult
import com.app.hospital.utils.gone
import com.app.hospital.utils.showToast
import com.app.hospital.utils.visible
import com.app.hospital.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        observeSignupState()
    }

    private fun setupClickListeners() {
        binding.btnSignup.setOnClickListener {
            clearErrors()
            val name = binding.etName.text?.toString()?.trim() ?: ""
            val username = binding.etUsername.text?.toString()?.trim() ?: ""
            val password = binding.etPassword.text?.toString()?.trim() ?: ""
            val confirmPassword = binding.etConfirmPassword.text?.toString()?.trim() ?: ""
            viewModel.signup(username, password, confirmPassword, name)
        }

        binding.tvGoToLogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun clearErrors() {
        binding.tilName.error = null
        binding.tilUsername.error = null
        binding.tilPassword.error = null
        binding.tilConfirmPassword.error = null
    }

    private fun observeSignupState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.signupState.collect { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        binding.progressBar.visible()
                        binding.btnSignup.isEnabled = false
                    }
                    is NetworkResult.Success -> {
                        binding.progressBar.gone()
                        binding.btnSignup.isEnabled = true
                        viewModel.resetSignupState()
                        showToast("Account created! Please login.")
                        findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
                    }
                    is NetworkResult.Error -> {
                        binding.progressBar.gone()
                        binding.btnSignup.isEnabled = true
                        showToast(result.message)
                    }
                    null -> { /* idle */ }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
