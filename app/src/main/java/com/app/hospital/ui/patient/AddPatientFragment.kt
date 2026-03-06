package com.app.hospital.ui.patient

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.hospital.databinding.FragmentAddPatientBinding

class AddPatientFragment : Fragment() {

    private var _binding: FragmentAddPatientBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddPatientBinding.inflate(inflater, container, false)

        binding.btnSavePatient.setOnClickListener {

            Toast.makeText(requireContext(), "Patient Added", Toast.LENGTH_SHORT).show()

            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}