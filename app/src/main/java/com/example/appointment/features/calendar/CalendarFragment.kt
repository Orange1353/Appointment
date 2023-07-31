package com.example.appointment.features.calendar

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appointment.R
import com.example.appointment.databinding.FragmentCalendarBinding
import com.example.appointment.features.write.WriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_calendar.calendarView
import kotlinx.android.synthetic.main.fragment_calendar.game_event_recycler


@AndroidEntryPoint
class CalendarFragment: Fragment(R.layout.fragment_calendar) {
    private var _binding : FragmentCalendarBinding? = null
    private val binding get() = _binding
    private val viewModel : CalendarViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //Initial binding
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val view = binding?.root

        // получение viewModel, для доспупа к методам vm
        val viewModel: WriteViewModel by viewModels()
        binding?.vm = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //создание Адаптера? Передаем ему ивенты из фаербазы
        viewModel.getDocumentOnFirebase()
        //СОздание пустого адптера
        val gameEventAdapter = AdapterGameEvent(){ position -> viewModel.onListItemClick(position) }
        // Set the LayoutManager that
        // this RecyclerView will use.
        val layoutManagerGameEvent = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
        game_event_recycler.layoutManager = layoutManagerGameEvent

        game_event_recycler.adapter = gameEventAdapter


        viewModel.eventListDay.observe(viewLifecycleOwner, Observer { updatedEventList ->
            //устранавливает обновленный лист в адаптер
            gameEventAdapter.setGameEventList(updatedEventList)
        })

        calendarView.setOnDateChangedListener { widget, date, selected ->
            viewModel.onCalendarSetDate(date.year.toString(), date.month.toString(), date.day.toString()) }
        calendarView.setOnMonthChangedListener { widget, date ->
            viewModel.onCalendarSetDate(date.year.toString(), date.month.toString(), date.day.toString()) }
        observeDecorateEventsDay()

    }
    fun observeDecorateEventsDay(){
        viewModel.dayDecorator.observe(viewLifecycleOwner, Observer { dayDecorator ->
            calendarView.addDecorators(EventDecorator(Color.RED, dayDecorator))
        })
    }
}
