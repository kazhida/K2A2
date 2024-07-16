package com.abplus.k2a2.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abplus.k2a2.model.BloodPressure

@Composable
fun BloodPressureItem(
    modifier: Modifier = Modifier,
    bloodPressure: BloodPressure,
    onItemClicked: (BloodPressure) -> Unit,
    doItemDelete: (BloodPressure) -> Unit
) {
    Card(
        modifier = modifier.padding(4.dp, 1.dp),
        shape = RoundedCornerShape(1.dp),
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        )
    ) {
        Row {
            Column(
                modifier = Modifier.weight(1f).clickable {
                    onItemClicked(bloodPressure)
                }
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 16.dp, 16.dp, 4.dp)
                ) {
                    val textStyle = TextStyle(fontSize = 16.sp)
                    Text(text = bloodPressure.date, style = textStyle)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = bloodPressure.time, style = textStyle)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 16.dp)
                ) {
                    val textStyle = TextStyle(fontSize = 20.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = bloodPressure.systolicBP.toString(), style = textStyle)
                    Text(text = "/", style = textStyle)
                    Text(text = bloodPressure.diastolicBP.toString(), style = textStyle)
                    Text(text = " mmHg ", style = textStyle)
                    Text(text = bloodPressure.pulseRate.toString(), style = textStyle)
                    Text(text = " pulse/min", style = textStyle)
                }
            }
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(8.dp)
                    .clickable {
                        doItemDelete(bloodPressure)
                    }
            ) {
                Icon(Icons.Default.Delete, "Remove Blood Pressure")
            }
        }
    }
}
