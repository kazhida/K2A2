package com.abplus.k2a2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.NumberPicker

@Composable
fun BloodPressurePicker(
    modifier: Modifier = Modifier,
    dividersColor: Color = MaterialTheme.colorScheme.primary,
    textStyle: TextStyle = LocalTextStyle.current,
    systolicBP: Int = 160,
    diastolicBP: Int = 100,
    pulseRate: Int = 90
) {
    var v0: Int by remember { mutableIntStateOf(systolicBP) }
    var v1: Int by remember { mutableIntStateOf(diastolicBP) }
    var v2: Int by remember { mutableIntStateOf(pulseRate) }

    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clip(Shapes().medium)
    ) {
        val pickerModifier = Modifier.padding(4.dp)
        // 最高血圧
        NumberPicker(
            modifier = pickerModifier,
            value = v0,
            range = 0..200,
            textStyle = textStyle,
            dividersColor = dividersColor,
            onValueChange = { v0 = it }
        )
        Text(
            text = "",
            style = textStyle
        )
        //  最低血圧
        NumberPicker(
            modifier = pickerModifier,
            value = v1,
            range = 0..200,
            textStyle = textStyle,
            dividersColor = dividersColor,
            onValueChange = { v1 = it }
        )
        Text(
            text = "mmHg ",
            modifier = Modifier.align(alignment = Alignment.CenterVertically),
            style = textStyle
        )
        NumberPicker(
            modifier = pickerModifier,
            value = v2,
            range = 0..150,
            textStyle = textStyle,
            dividersColor = dividersColor,
            onValueChange = { v2 = it }
        )
        Text(
            text = "pulse/min",
            modifier = Modifier.align(alignment = Alignment.CenterVertically),
            style = textStyle
        )
    }
}
