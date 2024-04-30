package com.example.appointment.features.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.appointment.R
import com.example.appointment.databinding.FragmentMenuBinding
import com.example.appointment.features.BaseFragment
import com.example.appointment.features.menu.MenuViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_menu.personal_data_button
import kotlinx.android.synthetic.main.fragment_menu.write_button

/**
 * A simple [Fragment] subclass.
 * Use the [AuthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MenuFragment : BaseFragment() {
    private var _binding : FragmentMenuBinding? = null
    private val binding get() = _binding
    private val viewModel : MenuViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavListener?.showBottomNavigation(true)

        MenuToCalendar()
//        MenuToSplash()
        MenuToPersonalData()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //Initial binding
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        getUser()
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

//    fun MenuToSplash (){
//        logout_button.setOnClickListener{
//            findNavController().navigate(R.id.action_menuFragment_to_signInFragment)
//        }
//    }
    fun MenuToCalendar(){
        write_button.setOnClickListener{
            findNavController().navigate(R.id.action_menuFragment_to_calendarFragment)
        }
    }
    fun MenuToPersonalData(){
        personal_data_button.setOnClickListener{
            findNavController().navigate(R.id.action_menuFragment_to_personalDataFragment)
        }
    }
}
