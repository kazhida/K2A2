package com.abplus.k2a2.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abplus.k2a2.di.RealmBloodPressure
import com.abplus.k2a2.model.BloodPressure
import com.abplus.k2a2.model.BloodPressureRepository
import com.abplus.k2a2.ui.theme.K2A2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val repository: BloodPressureRepository = RealmBloodPressure.Repository()
    private lateinit var bloodPressure: BloodPressure

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bloodPressure = intent.getParcelableExtra("bp") ?: repository.latest().copy(id = 0L)

        setContent {
            K2A2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(),
                        elevation = CardDefaults.cardElevation()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            DateTime(bloodPressure = bloodPressure)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .align(alignment = Alignment.CenterHorizontally)
                            ) {
                                BloodPressurePicker(
                                    systolicBP = bloodPressure.systolicBP,
                                    diastolicBP = bloodPressure.diastolicBP,
                                    pulseRate = bloodPressure.pulseRate,
                                    textStyle = TextStyle(fontSize = 24.sp),
                                    onChange = { systolicBP, diastolicBP, pulseRate ->
                                        bloodPressure = bloodPressure.copy(
                                            systolicBP = systolicBP,
                                            diastolicBP = diastolicBP,
                                            pulseRate = pulseRate
                                        )
                                    }
                                )
                            }
                            Buttons()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun DateTime(bloodPressure: BloodPressure) {
        val textStyle = TextStyle(fontSize = 24.sp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = bloodPressure.date, style = textStyle)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = bloodPressure.time, style = textStyle)
        }
    }

    @Composable
    fun Buttons() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.wrapContentWidth(),
                onClick = {
                    onBackPressedDispatcher.onBackPressed()
                }
            ) {
                Text(text = "cancel")
            }
            Spacer(modifier = Modifier.weight(1f))
            ElevatedButton(
                modifier = Modifier
                    .wrapContentWidth(),
                onClick = {
                    if (bloodPressure.id == 0L) {
                        repository.add(bloodPressure)
                        gotoListActivity()
                        finish()
                    } else {
                        repository.save(bloodPressure)
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            ) {
                Text(text = "save")
            }
        }
    }

    fun gotoListActivity() {
        startActivity(Intent(this, ListActivity::class.java))
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    K2A2Theme {
//        Greeting("Android")
    }
}