package com.abplus.k2a2.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abplus.k2a2.di.RealmBloodPressure
import com.abplus.k2a2.model.BloodPressure
import com.abplus.k2a2.model.BloodPressureRepository
import com.abplus.k2a2.ui.composable.EntryCard
import com.abplus.k2a2.ui.theme.K2A2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val repository: BloodPressureRepository = RealmBloodPressure.Repository()
    private var bloodPressure: MutableState<BloodPressure> = mutableStateOf(BloodPressure(0, 0, 0, 0, 0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bloodPressure.value = intent.getParcelableExtra("bp") ?: repository.latest().copy(id = 0L)

        setContent {
            K2A2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(4.dp)
                    ) {
                        EntryCard(bloodPressure = bloodPressure, repository = repository)
                    }
                }
            }
        }
    }
}
