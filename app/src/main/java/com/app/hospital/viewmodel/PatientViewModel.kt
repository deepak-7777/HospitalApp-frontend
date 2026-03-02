package com.app.hospital.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.hospital.data.api.PatientProfile
import com.app.hospital.data.repository.PatientRepository
import com.app.hospital.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientViewModel @Inject constructor(
    private val patientRepository: PatientRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow<NetworkResult<PatientProfile>?>(null)
    val profileState: StateFlow<NetworkResult<PatientProfile>?> = _profileState

    fun fetchProfile() {
        viewModelScope.launch {
            _profileState.value = NetworkResult.Loading()
            _profileState.value = patientRepository.getPatientProfile()
        }
    }
}
