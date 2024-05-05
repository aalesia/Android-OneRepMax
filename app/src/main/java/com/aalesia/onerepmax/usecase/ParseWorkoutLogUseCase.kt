package com.aalesia.onerepmax.usecase

import android.content.Context
import com.aalesia.onerepmax.R
import com.aalesia.onerepmax.entity.WorkoutEntity
import com.aalesia.onerepmax.entity.WorkoutRecordEntity
import com.aalesia.onerepmax.extension.toDate

class ParseWorkoutLogUseCase {
    operator fun invoke(context: Context): List<WorkoutEntity> {
        val workoutRecords = fetchWorkoutRecords(context)
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

    private fun fetchWorkoutRecords(context: Context): List<WorkoutRecordEntity> {
        val workoutData = context.resources.openRawResource(R.raw.workoutdata).bufferedReader().use {
            it.readText()
        }
        val workoutRecords = mutableListOf<WorkoutRecordEntity>()

        workoutData.split("\n").forEach { line ->
            val values = line.split(",")
            if (values.size == 4) {
                val dateString = values[0]
                val workout = values[1]
                val reps = values[2].toInt()
                val weight = values[3].toInt()

                dateString.toDate()?.let { date ->
                    workoutRecords.add(WorkoutRecordEntity(date, workout, reps, weight))
                }
            }
        }

        return workoutRecords
    }

    // Calculate the one rep max using the Brzycki formula
    private fun calculateOneRepMax(workoutRecords: List<WorkoutRecordEntity>): Int {
        val sortedRecords = workoutRecords.sortedByDescending { it.weight }
        val heaviestWeight = sortedRecords.first().weight
        val heaviestWeightReps = sortedRecords.first().reps

        return (heaviestWeight.toFloat() * (36f / (37f - heaviestWeightReps.toFloat()))).toInt()
    }
}