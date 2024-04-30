package com.example.appointment.features.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appointment.R
import com.example.appointment.databinding.FragmentSettingsBinding
import com.example.appointment.features.BaseFragment
import com.example.appointment.features.auth.AllEvents
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private val viewModel: SettingsViewModel by viewModels()
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        listenToChannels()

        //Initial binding
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding?.root

        // получение viewModel, для доспупа к методам vm
        val viewModel: SettingsViewModel by viewModels()
        binding?.vm = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        return view
    }

    private fun listenToChannels() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allEventsFlow.collect { event ->
                when {
                    event is AllEvents.LogOutSuccess -> {
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_settingFragment_to_signInFragment)
                    }
                    event is AllEvents.Message -> {
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}