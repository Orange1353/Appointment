package com.example.appointment.features.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.appointment.R
import com.example.appointment.databinding.FragmentAuthBinding
import com.example.appointment.features.menu.MenuFragment

/**
 * A simple [Fragment] subclass.
 * Use the [AuthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AuthFragment : Fragment() {

    private lateinit var binding : FragmentAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //Initial binding
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        val view = binding.root

        // получение viewModel, для доспупа к методам vm
        val viewModel: AuthViewModel by viewModels()
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.NextFrag.setOnClickListener{
            openNextFrag("Успех!")
        }

        return view
    }

    private fun openNextFrag(value: String){
        findNavController().navigate(R.id.action_authFragment_to_menuFragment, bundleOf(MenuFragment.MESSAGEAuthTOMenu_KEY to value))
    }

    companion object {


       /*
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AuthFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }*/
    }
}