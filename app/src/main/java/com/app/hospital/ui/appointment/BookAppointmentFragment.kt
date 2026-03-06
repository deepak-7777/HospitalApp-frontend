package com.app.hospital.ui.appointment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.hospital.databinding.FragmentBookAppointmentBinding
import com.app.hospital.viewmodel.AppointmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookAppointmentFragment : Fragment() {

    private var _binding: FragmentBookAppointmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AppointmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBookAppointmentBinding.inflate(inflater, container, false)

        binding.btnBookAppointment.setOnClickListener {

            val patient = binding.etPatientName.text.toString()
            val doctor = binding.etDoctorName.text.toString()
            val date = binding.etDate.text.toString()
            val time = binding.etTime.text.toString()

            if (patient.isEmpty() || doctor.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.bookAppointment(patient, doctor, date, time)

            Toast.makeText(requireContext(), "Appointment booked", Toast.LENGTH_SHORT).show()

            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}