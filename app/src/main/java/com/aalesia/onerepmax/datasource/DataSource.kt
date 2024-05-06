package com.aalesia.onerepmax.datasource

import com.aalesia.onerepmax.entity.WorkoutRecordEntity

interface DataSource {
    fun addWorkoutRecord(workoutRecord: WorkoutRecordEntity)
    fun getWorkoutRecords(): List<WorkoutRecordEntity>
}

class DataSourceImpl : DataSource {
    private val workoutRecords = mutableListOf<WorkoutRecordEntity>()

    override fun addWorkoutRecord(workoutRecord: WorkoutRecordEntity) {
        workoutRecords.add(workoutRecord)
    }

    override fun getWorkoutRecords(): List<WorkoutRecordEntity> {
        return workoutRecords
    }
}