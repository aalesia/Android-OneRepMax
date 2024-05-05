package com.aalesia.onerepmax.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class WorkoutRecordEntity(
    val date: Date,
    val workout: String,
    val reps: Int,
    val weight: Int
) : Parcelable