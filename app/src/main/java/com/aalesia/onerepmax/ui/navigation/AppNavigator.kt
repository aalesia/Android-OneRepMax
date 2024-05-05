package com.aalesia.onerepmax.ui.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aalesia.onerepmax.entity.WorkoutEntity
import com.aalesia.onerepmax.ui.workoutdetails.WorkoutDetailsScreen
import com.aalesia.onerepmax.ui.workouts.WorkoutsScreen
import com.google.gson.Gson

@Suppress("DEPRECATION")
@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "workouts"
    ) {
        composable(route = "workouts") {
            WorkoutsScreen(navController)
        }
        composable(
            route = "workoutDetails/{workout}",
            arguments = listOf(
                navArgument("workout") {
                    type = AssetParamType()
                }
            )
        ) {
            val workout = it.arguments?.getParcelable<WorkoutEntity>("workout")
            workout?.let { workoutEntity ->
                WorkoutDetailsScreen(navController, workoutEntity)
            }
        }
    }
}

@Suppress("DEPRECATION")
class AssetParamType : NavType<WorkoutEntity>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): WorkoutEntity? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): WorkoutEntity {
        return Gson().fromJson(value, WorkoutEntity::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: WorkoutEntity) {
        bundle.putParcelable(key, value)
    }
}