package com.abplus.k2a2.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abplus.k2a2.model.BloodPressure
import com.abplus.k2a2.model.BloodPressureRepository

@Composable
fun EntryCard(
    bloodPressure: MutableState<BloodPressure>,
    repository: BloodPressureRepository,
) {
    var isVisible by remember { mutableStateOf(true) }

    if (isVisible) {
        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(),
            elevation = CardDefaults.cardElevation()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                //  日付
                val textStyle = TextStyle(fontSize = 24.sp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = bloodPressure.value.date, style = textStyle)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = bloodPressure.value.time, style = textStyle)
                }
                // 入力欄
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                ) {
                    BloodPressurePicker(
                        systolicBP = bloodPressure.value.systolicBP,
                        diastolicBP = bloodPressure.value.diastolicBP,
                        pulseRate = bloodPressure.value.pulseRate,
                        textStyle = TextStyle(fontSize = 24.sp),
                        onChange = { systolicBP, diastolicBP, pulseRate ->
                            bloodPressure.value = bloodPressure.value.copy(
                                systolicBP = systolicBP,
                                diastolicBP = diastolicBP,
                                pulseRate = pulseRate
                            )
                        }
                    )
                }
                // 下部ボタン
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    OutlinedButton(
                        modifier = Modifier.wrapContentWidth(),
                        onClick = {
                            isVisible = false
                        }
                    ) {
                        Text(text = "cancel")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    ElevatedButton(
                        modifier = Modifier
                            .wrapContentWidth(),
                        onClick = {
                            if (bloodPressure.value.id == 0L) {
                                repository.add(bloodPressure.value)
                            } else {
                                repository.save(bloodPressure.value)
                            }
                            isVisible = false
                        }
                    ) {
                        Text(text = "save")
                    }
                }
            }
        }
    }
}
