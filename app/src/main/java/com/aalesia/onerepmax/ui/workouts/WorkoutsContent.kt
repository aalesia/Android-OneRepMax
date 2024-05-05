package com.aalesia.onerepmax.ui.workouts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.aalesia.onerepmax.R
import com.aalesia.onerepmax.entity.WorkoutEntity
import com.aalesia.onerepmax.ui.theme.Typography

@Composable
internal fun WorkoutsContent(
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
        }
    }
}

@Composable
fun WorkoutItem(
    workout: WorkoutEntity,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.horizontal_margin),
                vertical = dimensionResource(id = R.dimen.list_item_padding),
            )
    ) {
        Column {
            Row {
                Text(
                    text = workout.name,
                    style = Typography.bodyLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = workout.maxRep.toString(),
                    style = Typography.bodyLarge
                )
            }
            Row {
                Text(
                    text = stringResource(id = R.string.one_rep_max_record),
                    style = Typography.bodySmall
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(id = R.string.lbs),
                    style = Typography.bodySmall
                )
            }
        }
    }
}