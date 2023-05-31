package com.example.appointment.features.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appointment.R
import com.example.appointment.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [AuthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SplashFragment : Fragment() {

    //private lateinit var binding : FragmentAuthBinding

    private var _binding : FragmentSplashBinding? = null
    private val binding get() = _binding
    private val viewModel : SplashViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //Initial binding
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        getUser()
        registerObserver()
        val view = binding?.root

        // получение viewModel, для доспупа к методам vm
        val viewModel: SplashViewModel by viewModels()
        binding?.vm = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner
        return view
    }
    private fun getUser() {
        viewModel.getCurrentUser()
    }
    //проверка входа пользователя и отправления его на соотвествующтий экран
    private fun registerObserver() {
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            lifecycleScope.launch{
                delay(1000L)
                user?.let {
                    binding?.apply {
                        findNavController().navigate(R.id.action_splashFragment_to_menuFragment)
                    }
                } ?: binding?.apply {
                    findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
                }
            }
        }
    }
    //Смотри название метода
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}