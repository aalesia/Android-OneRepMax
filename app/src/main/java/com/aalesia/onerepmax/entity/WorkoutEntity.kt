package com.aalesia.onerepmax.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkoutEntity(
    val name: String,
    val maxRep: Int,
    val records: List<WorkoutRecordEntity>
) : Parcelable