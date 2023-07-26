package com.example.appointment.features.calendar

data class CalendarDate (
    var day :String,
    var month :String,
    var year :String
        )
fun CalendarDate.getMonthYear() = month + year
fun CalendarDate.getFullDate() = day + month + year