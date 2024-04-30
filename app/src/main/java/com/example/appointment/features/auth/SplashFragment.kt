package com.example.appointment.features.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appointment.R
import com.example.appointment.databinding.FragmentSplashBinding
import com.example.appointment.features.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [AuthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    //private lateinit var binding : FragmentAuthBinding

    private var _binding : FragmentSplashBinding? = null
    private val binding get() = _binding
    private val viewModel : SplashViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Initial binding
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding?.root
        binding?.vm = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        getUser()
        registerObserver()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        //не onViewCreated потому что он чууть чуть раньше вызывается
        super.onActivityCreated(savedInstanceState)
        bottomNavListener?.showBottomNavigation(false)
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
                        findNavController().navigate(R.id.action_splashFragment_to_personal_data)
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