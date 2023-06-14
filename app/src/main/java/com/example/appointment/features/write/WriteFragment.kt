package com.example.appointment.features.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appointment.R
import com.example.appointment.databinding.FragmentWriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_write.location_recycler
import kotlinx.android.synthetic.main.fragment_write.route_recycler
import kotlinx.android.synthetic.main.fragment_write.write_button


@AndroidEntryPoint
class WriteFragment : Fragment(R.layout.fragment_write) {
    private var _binding : FragmentWriteBinding? = null
    private val binding get() = _binding
    private val viewModel : WriteViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // getting the location list
        val Lockationlist = viewModel.LoacationList
        val RouteList = viewModel.RouteList
        // Assign employeelist to ItemAdapter
        val locationAdapter = AdapterLocation(Lockationlist){ position -> viewModel.onListItemClick(position) }
        val routeAdapter = AdapterRoute(RouteList){ position -> viewModel.onListItemClick(position) }

        // Set the LayoutManager that
        // this RecyclerView will use.
        val location_recyclerView:RecyclerView = location_recycler
        val layoutManager_location = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true)
        location_recyclerView.layoutManager = layoutManager_location
        // adapter instance is set to the
        // recyclerview to inflate the items.
        location_recyclerView.adapter = locationAdapter


        val route_recyclerView:RecyclerView = route_recycler
        val layoutManager_roure = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
        route_recyclerView.layoutManager = layoutManager_roure
        route_recyclerView.adapter = routeAdapter



        write_button.setOnClickListener  {
            findNavController().navigate(R.id.action_writeFragment_to_menuFragment)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //Initial binding
        _binding = FragmentWriteBinding.inflate(inflater, container, false)
        val view = binding?.root

        // получение viewModel, для доспупа к методам vm
        val viewModel: WriteViewModel by viewModels()
        binding?.vm = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner



        return view
    }
}
