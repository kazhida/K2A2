package com.abplus.k2a2.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abplus.k2a2.di.RealmBloodPressure
import com.abplus.k2a2.model.BloodPressure
import com.abplus.k2a2.model.BloodPressureRepository
import com.abplus.k2a2.ui.composable.BloodPressureList
import com.abplus.k2a2.ui.composable.EntryCard
import com.abplus.k2a2.ui.theme.K2A2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val repository: BloodPressureRepository = RealmBloodPressure.Repository()
    private var bloodPressure: MutableState<BloodPressure> = mutableStateOf(BloodPressure(0, 0, 0, 0, 0))
    private var records: MutableState<List<BloodPressure>> = mutableStateOf(listOf())
    private var isEntryVisible: MutableState<Boolean> = mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bloodPressure.value = intent.getParcelableExtra("bp") ?: repository.latest().cleared

        setContent {
            K2A2Theme {
                Scaffold(
                    content = { _ ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                ) {
                                    EntryCard(
                                        bloodPressure = bloodPressure,
                                        isVisible = isEntryVisible,
                                        repository = repository,
                                        onClose = { reload() }
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    BloodPressureList(
                                        bloodPressures = records,
                                        onItemClicked = {
                                            bloodPressure.value = it
                                            isEntryVisible.value = true
                                        },
                                        doItemDelete = {
                                            repository.delete(it)
                                            reload()
                                        }
                                    )
                                }
                            }
                        }

                    },
                    floatingActionButton = {
                        SmallFloatingActionButton(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.secondary,
                            onClick = {
                                bloodPressure.value = repository.latest().cleared
                                isEntryVisible.value = true
                            },
                        ) {
                            Icon(Icons.Filled.Add, "Small floating action button.")
                        }
                    }
                )
            }
        }
        reload()
    }

    private fun reload() {
        Handler(Looper.getMainLooper()).postDelayed({
            records.value = repository.load()
        }, 200)
    }

    private val BloodPressure.cleared: BloodPressure get() = copy(id = 0L, timeInMillis = System.currentTimeMillis())
}
