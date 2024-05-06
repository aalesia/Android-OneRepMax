package com.aalesia.onerepmax.usecase

import android.content.Context
import com.aalesia.onerepmax.entity.WorkoutEntity
import com.aalesia.onerepmax.entity.WorkoutRecordEntity
import com.aalesia.onerepmax.repository.Repository

class ParseWorkoutLogUseCase(private val repository: Repository) {
    operator fun invoke(context: Context): List<WorkoutEntity> {
        val workoutRecords = repository.fetchWorkoutRecords(context)
        val groupedWorkoutRecords = workoutRecords.groupBy { it.workout }
        val sortedWorkoutRecords = groupedWorkoutRecords.map { (workout, records) ->
            val sortedRecords = records.sortedBy { it.date }
            val oneRepMax = calculateOneRepMax(sortedRecords)
            WorkoutEntity(workout, oneRepMax, sortedRecords)
        }.sortedBy {
            it.name
        }

        return sortedWorkoutRecords
    }

    // Calculate the one rep max using the Brzycki formula
    private fun calculateOneRepMax(workoutRecords: List<WorkoutRecordEntity>): Int {
        val sortedRecords = workoutRecords.sortedByDescending { it.weight }
        val heaviestWeight = sortedRecords.first().weight
        val heaviestWeightReps = sortedRecords.first().reps

        return (heaviestWeight.toFloat() * (36f / (37f - heaviestWeightReps.toFloat()))).toInt()
    }
}