package com.example.appointment.features.eventPage


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.appointment.databinding.FragmentPersonalDataBinding
import com.example.appointment.features.BaseFragment
import com.example.appointment.features.auth.AllEvents
import com.example.appointment.features.personalData.PersonalDataViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_personal_data.write_button
import kotlinx.coroutines.launch


@AndroidEntryPoint

class PersonalDataFragment: BaseFragment()  {
    private lateinit var binding: FragmentPersonalDataBinding
    private val viewModel : PersonalDataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Initial binding
        binding = FragmentPersonalDataBinding.inflate(inflater, container, false)
        //for two-way data binding
        binding.lifecycleOwner = this
        binding.personalDataViewModel = viewModel

        viewModel.getDatabaseReference()
        readUserOnce()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavListener?.showBottomNavigation(true)
        saveChanges()
        listenToChannels()
    }

    private fun saveChanges(){
        write_button.setOnClickListener {
            val user  = Firebase.auth.currentUser
            user?.let {
                viewModel.writeUser()
            }
        }
    }
    private fun readUserOnce(){
        val user = Firebase.auth.currentUser
        user?.let {
            val uid = it.uid
            viewModel.reedUserOnce(uid)
        }
    }

    private fun listenToChannels() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allEventsFlow.collect { event ->
                when {
                    event is AllEvents.Success -> {
                        Toast.makeText(requireContext(), "Успешно сохранено", Toast.LENGTH_SHORT).show()
                    }
                    event is AllEvents.Error -> {
                        Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}