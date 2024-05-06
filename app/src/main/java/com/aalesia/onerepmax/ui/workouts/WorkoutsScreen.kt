package com.aalesia.onerepmax.ui.workouts

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aalesia.onerepmax.R
import com.aalesia.onerepmax.entity.WorkoutEntity
import com.aalesia.onerepmax.ui.shared.WorkoutItem
import com.aalesia.onerepmax.ui.theme.OneRepMaxTheme
import com.aalesia.onerepmax.ui.theme.PrimaryColor
import com.aalesia.onerepmax.viewmodel.WorkoutsViewModel
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutsScreen(navController: NavController) {
    val viewModel: WorkoutsViewModel = viewModel()
    val workouts = remember { mutableStateOf(emptyList<WorkoutEntity>()) }

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.getWorkouts(context)
    }

    LaunchedEffect(key1 = viewModel.workouts) {
        viewModel.workouts.collect { newWorkouts ->
            workouts.value = newWorkouts
        }
    }

    OneRepMaxTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.title_activity_main))
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = PrimaryColor,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            },
            content = { padding ->
                WorkoutsContent(
                    workouts = workouts.value,
                    modifier = Modifier.padding(padding)
                ) { workout ->
                        val json = Uri.encode(Gson().toJson(workout))
                        navController.navigate("workoutDetails/$json")
                }
            }
        )
    }
}

@Composable
fun WorkoutsContent(
    workouts: List<WorkoutEntity>,
    modifier: Modifier = Modifier,
    onWorkoutClick: (WorkoutEntity) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(workouts) { workout ->
            WorkoutItem(
                workout = workout,
                modifier = Modifier.clickable {
                    onWorkoutClick(workout)
                }
            )
            HorizontalDivider()
        }
    }
}