package com.aalesia.onerepmax.repository

import android.content.Context
import com.aalesia.onerepmax.R
import com.aalesia.onerepmax.datasource.DataSource
import com.aalesia.onerepmax.entity.WorkoutRecordEntity
import com.aalesia.onerepmax.extension.toDate

interface Repository {
    fun fetchWorkoutRecords(context: Context): List<WorkoutRecordEntity>
}

class RepositoryImpl(private val dataSource: DataSource) : Repository {
    override fun fetchWorkoutRecords(context: Context): List<WorkoutRecordEntity> {
        val workoutData = context.resources.openRawResource(R.raw.workoutdata).bufferedReader().use {
            it.readText()
        }

        workoutData.split("\n").forEach { line ->
            val values = line.split(",")
            if (values.size == 4) {
                val dateString = values[0]
                val workout = values[1]
                val reps = values[2].toInt()
                val weight = values[3].toInt()

                dateString.toDate()?.let { date ->
                    dataSource.addWorkoutRecord(WorkoutRecordEntity(date, workout, reps, weight))
                }
            }
        }

        return dataSource.getWorkoutRecords()
    }
}