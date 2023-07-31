package com.example.appointment.features.calendar

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.appointment.data.repositories.IUserRepisitory
import com.example.appointment.hilt.IAppSettings
import com.example.appointment.models.local.GameEventModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class CalendarViewModel @Inject constructor(
    //private val repository : BaseAuthRepository
) : ViewModel()
{

    @Inject
    lateinit var settings: IAppSettings
    @Inject
    lateinit var userRepository: IUserRepisitory

    init {

    }
    private val dates: HashSet<CalendarDay>? = null
    var startDay = SimpleDateFormat("DD.").format(Date())
    var startMonth = SimpleDateFormat("MM.").format(Date())
    var startYear = SimpleDateFormat("YYYY").format(Date())

    //Выбранная дата (при запуске это сегодняшняя)
    val calendarDay = MutableLiveData(CalendarDate(startDay,startMonth,startYear) )
    val db = Firebase.firestore
    //все события в данный месяц
    var eventListMonth = MutableLiveData<List<GameEventModel>>()
    //события дня на выбранную дату. Отображение в recyclerView
    val eventListDay = Transformations.switchMap(eventListMonth) { listMonth ->
        Transformations.map(calendarDay) { calendarDayValue ->
            listMonth.filter {
                  it.day == calendarDayValue.getFullDate()
            }
        }
    }
    //Список дат событий для индикатора
    val dayDecorator = Transformations.map(eventListMonth) { listMonth ->
          listMonth.map { event ->
            val dayString = event.day.orEmpty()
            val format = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val date: LocalDate  =  LocalDate.parse(dayString, format)
            CalendarDay.from(date.year, date.monthValue, date.dayOfMonth)
        }
    }

    fun getDocumentOnFirebase() {
        calendarDay.value?.getMonthYear()?.let { date ->
            val docRef = db.collection(date)
            val dockList = mutableListOf<GameEventModel>()
            docRef.get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val day = document[KEY_DAY].toString()
                    val logo = document[KEY_LOGO].toString()
                    val nameGame = document[KEY_NAMEGAME].toString()
                    val nameOrg = document[KEY_NAMEORG].toString()
                    val timelinne = document[KEY_TIMELINNE].toString()
                    dockList.add(GameEventModel(day = day, logo = logo, gameEventName = nameGame, orgName=nameOrg, timeLine=timelinne))
                }
                eventListMonth.value = dockList
            }
        }
    }
    //private val _firebaseUser = MutableLiveData<FirebaseUser?>()

    fun onListItemClick (position: Int){
        Log.e("position55555555","$position")
    }
    //вызывается из фрагмента по нажатию на календарь и передает выбраную дату
    fun onCalendarSetDate (year: String, month: String, day : String) {
        //меняет тип месяца (добавлет 0 к месяцам 1...9)
        val newMonth = if (month.length < 2)
            "0$month."
        else
            "$month."
        //Приведение типа данных для calendarDay.
        val newCalendarDate = CalendarDate(
            day = "$day.",
            month = newMonth,
            year = year
        )
        //проверка на изменения месяца или года для обновления списка собитий на месяц.
        if(calendarDay.value?.month != newMonth || calendarDay.value?.year != year){
            calendarDay.value = newCalendarDate
            getDocumentOnFirebase()
        } else
            calendarDay.value = newCalendarDate
    }

    companion object {
        const val KEY_DAY = "Day"
        const val KEY_LOGO = "Logo"
        const val KEY_NAMEGAME = "NameGame"
        const val KEY_NAMEORG = "NameOrg"
        const val KEY_TIMELINNE = "Time"
    }
}