package com.example.appointment.features.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.appointment.databinding.FragmentMenuBinding
import com.example.appointment.features.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private lateinit var binding : FragmentMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       /*передача аргументов
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        */

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //Initial binding
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        val view = binding.root

        // получение viewModel, для доспупа к методам vm
        val viewModel: AuthViewModel by viewModels()
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val messageAuthTOmenu = requireArguments().getString(MESSAGEAuthTOMenu_KEY)
        Toast.makeText(requireContext().applicationContext,messageAuthTOmenu, Toast.LENGTH_SHORT).show()

        return view
    }



    companion object {

        const val MESSAGEAuthTOMenu_KEY : String = "Ты Долбаеб"


        // передача аргументов
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MenuFragment.
         */
       /*
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        */
    }
}