package com.subreax.hackaton.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.subreax.hackaton.service.LocationTrackerServiceController

@Composable
fun HomeScreen(
    locationTracker: LocationTrackerServiceController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    Surface(Modifier.fillMaxSize()) {
        Column(Modifier.padding(16.dp)) {
            Button(onClick = {
                locationTracker.startLocationTracker()
            }) {
                Text(text = "Start foreground")
            }

            Button(onClick = {
                locationTracker.stopLocationTracker()
            }) {
                Text(text = "Stop foreground")
            }
        }
    }
}
