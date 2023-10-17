package com.example.appointment.features.eventPage


import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.appointment.R
import com.example.appointment.databinding.FragmentEventPageBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class PersonalDataFragment: Fragment(R.layout.fragment_personal_data)  {
    private var _binding : FragmentEventPageBinding? = null
    private val binding get() = _binding
    private val viewModel : PersonalDataViewModel by activityViewModels()

}