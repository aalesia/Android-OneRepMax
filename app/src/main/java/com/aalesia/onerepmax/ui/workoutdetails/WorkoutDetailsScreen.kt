package com.aalesia.onerepmax.ui.workoutdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aalesia.onerepmax.R
import com.aalesia.onerepmax.entity.WorkoutEntity
import com.aalesia.onerepmax.ui.chart.LineGraph
import com.aalesia.onerepmax.ui.shared.WorkoutItem
import com.aalesia.onerepmax.ui.theme.OneRepMaxTheme
import com.aalesia.onerepmax.ui.theme.PrimaryColor
import com.aalesia.onerepmax.viewmodel.WorkoutDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailsScreen(navController: NavController, workout: WorkoutEntity) {
    val viewModel: WorkoutDetailsViewModel = viewModel()
    val workoutDaysAndMaxWeights = remember {
        mutableStateOf(Pair(emptyList<Float>(), emptyList<Float>()))
    }

    LaunchedEffect(key1 = true) {
        viewModel.getWorkoutDaysAndMaxWeights(workout)
    }

    LaunchedEffect(key1 = viewModel.workoutDaysAndMaxWeights) {
        viewModel.workoutDaysAndMaxWeights.collect { results ->
            workoutDaysAndMaxWeights.value = results
        }
    }

    OneRepMaxTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = workout.name)
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = PrimaryColor,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            },
            content = { padding ->
                Column(
                    modifier = Modifier.padding(padding)
                ) {
                    WorkoutItem(workout = workout)
                    if (workoutDaysAndMaxWeights.value.first.isNotEmpty() &&
                        workoutDaysAndMaxWeights.value.second.isNotEmpty()) {
                        LineGraph(
                            xData = workoutDaysAndMaxWeights.value.first,
                            yData = workoutDaysAndMaxWeights.value.second,
                            dataLabel = stringResource(id = R.string.weight)
                        )
                    }
                }
            }
        )
    }
}