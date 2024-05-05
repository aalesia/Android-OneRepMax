package com.aalesia.onerepmax.usecase

import com.aalesia.onerepmax.entity.WorkoutRecordEntity

class GetMaxWeightPerDayListUseCase {
    operator fun invoke(workoutRecords: List<WorkoutRecordEntity>): List<Float> {
        return workoutRecords.groupBy {
            it.date
        }.map {
            it.value.maxOf {
                record -> record.weight.toFloat()
            }
        }
    }
}