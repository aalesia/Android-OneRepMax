package com.aalesia.onerepmax.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aalesia.onerepmax.entity.WorkoutEntity
import com.aalesia.onerepmax.usecase.GetDaysFromRecordsUseCase
import com.aalesia.onerepmax.usecase.GetMaxWeightPerDayListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutDetailsViewModel : ViewModel() {
    private val getDaysFromRecordsUseCase = GetDaysFromRecordsUseCase()
    private val getMaxWeightPerDayListUseCase = GetMaxWeightPerDayListUseCase()

    private val _workoutDaysAndMaxWeights = MutableStateFlow<Pair<List<Float>, List<Float>>>(Pair(emptyList(), emptyList()))
    val workoutDaysAndMaxWeights: StateFlow<Pair<List<Float>, List<Float>>> = _workoutDaysAndMaxWeights

    fun getWorkoutDaysAndMaxWeights(workout: WorkoutEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val daysList = getDaysFromRecordsUseCase.invoke(workout.records)
            val maxWeightList = getMaxWeightPerDayListUseCase(workout.records)
            val results = Pair(daysList, maxWeightList)
            Log.d("WorkoutDetailsViewModel", "results=$results")
            _workoutDaysAndMaxWeights.value = results
        }
    }
}