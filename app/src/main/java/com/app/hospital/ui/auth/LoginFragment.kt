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
import com.app.hospital.databinding.FragmentLoginBinding
import com.app.hospital.utils.NetworkResult
import com.app.hospital.utils.gone
import com.app.hospital.utils.showToast
import com.app.hospital.utils.visible
import com.app.hospital.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        observeLoginState()
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text?.toString()?.trim() ?: ""
            val password = binding.etPassword.text?.toString()?.trim() ?: ""
            viewModel.login(username, password)
        }

        binding.tvGoToSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }

    private fun observeLoginState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginState.collect { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        binding.progressBar.visible()
                        binding.btnLogin.isEnabled = false
                    }
                    is NetworkResult.Success -> {
                        binding.progressBar.gone()
                        binding.btnLogin.isEnabled = true
                        viewModel.resetLoginState()
                        findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                    }
                    is NetworkResult.Error -> {
                        binding.progressBar.gone()
                        binding.btnLogin.isEnabled = true
                        showToast(result.message)
                        if (result.code == 401) {
                            binding.tilUsername.error = "Invalid credentials"
                            binding.tilPassword.error = "Invalid credentials"
                        }
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
