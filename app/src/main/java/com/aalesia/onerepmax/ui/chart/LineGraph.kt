package com.aalesia.onerepmax.ui.chart

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.aalesia.onerepmax.R
import com.aalesia.onerepmax.extension.toFormattedString
import com.aalesia.onerepmax.ui.theme.PrimaryColor
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.Date

@Composable
fun LineGraph(
    xData: List<Float>,
    yData: List<Float>,
    dataLabel: String,
    modifier: Modifier = Modifier,
    graphColor: Color = PrimaryColor
){
    val textColor = if(isSystemInDarkTheme()) Color.White else Color.Black
    val suffix = stringResource(id = R.string.lbs)

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            val chart = LineChart(context)
            val xDataIndices = List(xData.size) { index -> index.toFloat() }
            val entries: List<Entry> = xDataIndices.zip(yData) { x, y -> Entry(x, y) }
            val dataSet = LineDataSet(entries, dataLabel)
            dataSet.setDrawValues(false)
            dataSet.setCircleColor(graphColor.toArgb())
            dataSet.color = graphColor.toArgb()
            dataSet.circleHoleColor = graphColor.toArgb()

            chart.data = LineData(dataSet)
            chart.xAxis.valueFormatter = MyXAxisValueFormatter(xData)
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            chart.xAxis.textColor = textColor.toArgb()
            chart.axisLeft.valueFormatter = MyYAxisValueFormatter(suffix)
            chart.axisLeft.textColor = textColor.toArgb()
            chart.axisRight.isEnabled = false
            chart.description.isEnabled = false
            chart.legend.isEnabled = false

            chart.invalidate()
            chart
        }
    )
}

internal class MyXAxisValueFormatter(private var datesList: List<Float>) :
    ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        return Date(datesList[value.toInt()].toLong()).toFormattedString()
    }
}

internal class MyYAxisValueFormatter(private val suffix:String) : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return  value.toInt().toString() + " " + suffix
    }
}