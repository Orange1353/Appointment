package com.example.appointment.features.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appointment.R
import com.example.appointment.databinding.FragmentMenuBinding
import com.example.appointment.features.menu.MenuViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [AuthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.fragment_menu) {
    private var _binding : FragmentMenuBinding? = null
    private val binding get() = _binding
    private val viewModel : MenuViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //Initial binding
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        getUser()
        registerObserver()
        listenToChannels()
        val view = binding?.root

        // получение viewModel, для доспупа к методам vm
        val viewModel: MenuViewModel by viewModels()
        binding?.vm = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner
        return view
    }
    private fun getUser() {
        viewModel.getCurrentUser()
    }
    private fun registerObserver() {
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding?.apply {
                    logoutButton.setOnClickListener {
                        viewModel.signOut()
                        findNavController().navigate(R.id.action_menuFragment_to_signInFragment)
                    }
                }
            }
        }
    }

    private fun listenToChannels() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allEventsFlow.collect { event ->
                when {
                    event is AllEvents.LogOutSuccess ->{
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_menuFragment_to_signInFragment)
                    }
                    event is AllEvents.Message ->{
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}
