package com.example.appointment.features.eventPage


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appointment.R
import com.example.appointment.databinding.FragmentPersonalDataBinding
import com.example.appointment.features.BaseFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_personal_data.camo_editText
import kotlinx.android.synthetic.main.fragment_personal_data.dayOfBirdh_editView
import kotlinx.android.synthetic.main.fragment_personal_data.logout_button
import kotlinx.android.synthetic.main.fragment_personal_data.name_editView
import kotlinx.android.synthetic.main.fragment_personal_data.team_name_editText
import kotlinx.android.synthetic.main.fragment_personal_data.write_button


@AndroidEntryPoint

class PersonalDataFragment: BaseFragment()  {
    private lateinit var binding: FragmentPersonalDataBinding
    private lateinit var viewModel : PersonalDataViewModel// by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Initial binding
        binding = FragmentPersonalDataBinding.inflate(inflater, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_personal_data, container, false)
        viewModel = ViewModelProvider(this).get(PersonalDataViewModel::class.java)
        binding.personalDataViewModel = viewModel
        // Установка ViewModel для привязки данных
        viewModel.getDatabaseReference()
        //ВЫЗОВ МЕТОДОВ
        readOnceUser()
        observePersonalDataChange()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavListener?.showBottomNavigation(true)
        writeChenges()
    }
    fun writeChenges(){
        write_button.setOnClickListener {
            val user  = Firebase.auth.currentUser
            user?.let {
                val uid = it.uid
                viewModel.writeUser(uid, name_editView.text.toString(), dayOfBirdh_editView.text.toString(), team_name_editText.text.toString(), camo_editText.text.toString())

            }
        }
        logout_button.setOnClickListener {
            //Log.e("555555 CHECK", "${viewModel.testLive.value}")
        }
    }
    fun readOnceUser(){
        val user  = Firebase.auth.currentUser
        user?.let {
            val uid = it.uid
            viewModel.reedOnceUser(uid)
        }
    }
    fun observePersonalDataChange(){
        viewModel.userUId.observe(viewLifecycleOwner) { userData ->
            userData?.let {
                binding.invalidateAll()
            }
        }
    }
}