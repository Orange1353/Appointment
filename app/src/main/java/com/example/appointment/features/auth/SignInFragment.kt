package com.example.appointment.features.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appointment.R
import com.example.appointment.databinding.FragmentSigninBinding
import com.example.appointment.features.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignInFragment : BaseFragment() {
    private val viewModel : SignInViewModel by activityViewModels()
    private var _binding : FragmentSigninBinding? = null
    private val binding get() = _binding
    private val TAG = "SignInFragment"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSigninBinding.inflate(inflater , container , false)
        if (viewModel.isAuthUser()) {
            findNavController().navigate(R.id.action_signInFragment_to_personal_data)
        } else {
            listenToChannels()
            binding?.apply {
                signInButton.setOnClickListener  {
                    progressBarSignin.isVisible = true
                    val email = userEmailEtvl.text.toString()
                    val password = userPasswordEtvl.text.toString()
                    viewModel.signInUser(email, password)
                }

                signUpTxt.setOnClickListener {
                    findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
                }
                forgotPassTxt.setOnClickListener {
                    findNavController().navigate(R.id.action_signInFragment_to_resetPasswordFragment)
                }
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavListener?.showBottomNavigation(false)
    }

    private fun registerObservers() {
        if (viewModel.isAuthUser()) {
            findNavController().navigate(R.id.action_signInFragment_to_personal_data)
        }
    }

    private fun listenToChannels() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allEventsFlow.collect { event ->
                when(event){
                    is AllEvents.Error -> {
                        binding?.apply {
                            errorTxt.text =  event.error
                            progressBarSignin.isInvisible = true
                        }
                    }
                    is AllEvents.LogInSuccess -> {
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_signInFragment_to_personal_data)
                    }
                    is AllEvents.ErrorCode -> {
                        if (event.code == 1)
                            binding?.apply {
                                userEmailEtvl.error = "email should not be empty"
                                progressBarSignin.isInvisible = true
                            }


                        if(event.code == 2)
                            binding?.apply {
                                userPasswordEtvl.error = "password should not be empty"
                                progressBarSignin.isInvisible = true
                            }
                    }

                    else ->{
                        Log.d(TAG, "listenToChannels: No event received so far")
                    }
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
