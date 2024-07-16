package com.abplus.k2a2.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.abplus.k2a2.model.BloodPressure

@Composable
fun BloodPressureList(
    bloodPressures: MutableState<List<BloodPressure>>,
    onItemClicked: (BloodPressure) -> Unit,
    doItemDelete: (BloodPressure) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(items = bloodPressures.value) { bloodPressure ->
            BloodPressureItem(
                bloodPressure = bloodPressure,
                onItemClicked = { clicked ->
                    onItemClicked(clicked)
                },
                doItemDelete = {
                    doItemDelete(bloodPressure)
                }
            )
        }
    }
}
