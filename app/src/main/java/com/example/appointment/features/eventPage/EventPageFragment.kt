package com.example.appointment.features.eventPage


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.appointment.R
import com.example.appointment.databinding.FragmentEventPageBinding
import com.example.appointment.models.local.GameEventModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class EventPageFragment: Fragment(R.layout.fragment_event_page)  {
    private var _binding : FragmentEventPageBinding? = null
    private val binding get() = _binding
    private val viewModel : EventPageViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Здесь так же используется Kotlin экстеншен
        val selectedGameEvent =  arguments?.getParcelable<GameEventModel>("SelectedGameEvent")
        viewModel.getGameEventOnFirebase(selectedGameEvent)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //Initial binding
        _binding = FragmentEventPageBinding.inflate(inflater, container, false)
        val view = binding?.root

        // получение viewModel, для доспупа к методам vm
        val viewModel: EventPageViewModel by viewModels()
        binding?.vm = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

//        regaOnGameBut.setOnClickListener {
//            viewModel.regaOnGame()
//        }

        return view
    }

}