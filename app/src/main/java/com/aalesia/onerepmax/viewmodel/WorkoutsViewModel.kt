package com.aalesia.onerepmax.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aalesia.onerepmax.entity.WorkoutEntity
import com.aalesia.onerepmax.usecase.ParseWorkoutLogUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutsViewModel : ViewModel() {
    private val parseWorkoutLogUseCase = ParseWorkoutLogUseCase()

    private val _workouts = MutableStateFlow<List<WorkoutEntity>>(emptyList())
    val workouts: StateFlow<List<WorkoutEntity>> = _workouts

    fun getWorkouts(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val workouts = parseWorkoutLogUseCase.invoke(context)
            Log.d("WorkoutsViewModel", "workouts=$workouts")
            _workouts.value = workouts
        }
    }
}