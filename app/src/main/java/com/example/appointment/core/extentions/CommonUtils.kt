package com.example.appointment.core.extentions

inline fun <reified T> T?.orIfNull(input: () -> T): T {
    return this ?: input()
}