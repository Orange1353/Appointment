package com.example.appointment.models.navigation

import com.example.appointment.models.local.GameEventModel

sealed  class NavigationEvent {
    //Навигация на выбраный в календаре эвент (EvetnPage)
    class NavigateToEvent (val gameEventModel: GameEventModel): NavigationEvent()
    // add more navigation events here
}