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
import com.example.appointment.databinding.FragmentSignupBinding
import com.example.appointment.features.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_signup.confirm_password_etvl
import kotlinx.android.synthetic.main.fragment_signup.user_email_etvl
import kotlinx.android.synthetic.main.fragment_signup.user_password_etvl
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment() {
    private val viewModel : SignUpViewModel by activityViewModels()
    private var _binding : FragmentSignupBinding? = null
    private val binding get()  = _binding
    private val TAG = "SignUpFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater , container , false)

        registerObservers()
        listenToChannels()
        binding?.apply {
            signUpButton.setOnClickListener {
                progressBarSignup.isVisible = true
                val email = user_email_etvl.text.toString()
                val password = user_password_etvl.text.toString()
                val confirmPass = confirm_password_etvl.text.toString()
                viewModel.signUpUser(email , password , confirmPass)


            }

            signInTxt.setOnClickListener {
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            }

        }

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun registerObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner, { user ->
            user?.let {
                findNavController().navigate(R.id.action_signUpFragment_to_menuFragment)
            }
        })
    }

    private fun listenToChannels() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allEventsFlow.collect { event ->
                when(event){
                    is AllEvents.Error -> {
                        binding?.apply {
                            errorTxt.text = event.error
                            progressBarSignup.isInvisible = true
                        }
                    }
                    is AllEvents.Message -> {
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                    }
                    is AllEvents.ErrorCode -> {
                        if (event.code == 1)
                            binding?.apply {
                                userEmailEtvl.error = "email should not be empty"
                                progressBarSignup.isInvisible = true
                            }


                        if(event.code == 2)
                            binding?.apply {
                                userPasswordEtvl.error = "password should not be empty"
                                progressBarSignup.isInvisible = true
                            }

                        if(event.code == 3)
                            binding?.apply {
                                confirmPasswordEtvl.error = "passwords do not match"
                                progressBarSignup.isInvisible = true
                            }
                    }

                    else ->{
                        Log.d(TAG, "listenToChannels: No event received so far")
                    }
                }

            }
        }
    }
}
