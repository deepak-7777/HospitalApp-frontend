package com.app.hospital.ui.doctor

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.hospital.databinding.FragmentAddDoctorBinding
import com.app.hospital.viewmodel.DoctorViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDoctorFragment : Fragment() {

    private var _binding: FragmentAddDoctorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DoctorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddDoctorBinding.inflate(inflater, container, false)

        binding.btnSaveDoctor.setOnClickListener {

            val name = binding.inputName.text.toString()
            val department = binding.inputDepartment.text.toString()
            val phone = binding.inputPhone.text.toString()

            viewModel.addDoctor(name, department, phone)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}