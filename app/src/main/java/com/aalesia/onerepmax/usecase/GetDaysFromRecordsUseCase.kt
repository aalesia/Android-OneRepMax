package com.aalesia.onerepmax.usecase

import com.aalesia.onerepmax.entity.WorkoutRecordEntity

class GetDaysFromRecordsUseCase {
    operator fun invoke(workoutRecords: List<WorkoutRecordEntity>): List<Float> {
        return workoutRecords.groupBy {
            it.date
        }.map {
            it.key.time.toFloat()
        }
    }
}