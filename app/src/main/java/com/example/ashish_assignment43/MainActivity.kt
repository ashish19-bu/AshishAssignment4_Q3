package com.example.ashish_assignment43

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ashish_assignment43.ui.theme.Ashish_Assignment43Theme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ashish_Assignment43Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MapScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    
    // Polyline states
    var polylineWidth by remember { mutableFloatStateOf(10f) }
    var polylineColor by remember { mutableStateOf(Color.Blue) }
    
    // Polygon states
    var polygonStrokeWidth by remember { mutableFloatStateOf(5f) }
    var polygonFillColor by remember { mutableStateOf(Color.Green.copy(alpha = 0.3f)) }

    val trailPoints = listOf(
        LatLng(37.7749, -122.4194),
        LatLng(37.7794, -122.4174),
        LatLng(37.7844, -122.4094),
        LatLng(37.7884, -122.4014)
    )

    val parkPoints = listOf(
        LatLng(37.7700, -122.4100),
        LatLng(37.7750, -122.4100),
        LatLng(37.7750, -122.4050),
        LatLng(37.7700, -122.4050)
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(trailPoints[0], 14f)
    }

    Box(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Polyline(
                points = trailPoints,
                clickable = true,
                color = polylineColor,
                width = polylineWidth,
                onClick = {
                    Toast.makeText(context, "Hiking Trail: SF Downtown Trek", Toast.LENGTH_SHORT).show()
                }
            )

            Polygon(
                points = parkPoints,
                clickable = true,
                fillColor = polygonFillColor,
                strokeColor = Color.Black,
                strokeWidth = polygonStrokeWidth,
                onClick = {
                    Toast.makeText(context, "Park: Central Square Park", Toast.LENGTH_SHORT).show()
                }
            )
        }

        // Controls overlay
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.8f))
                .padding(16.dp)
        ) {
            Text("Customize Trail (Polyline)", style = MaterialTheme.typography.titleSmall)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Width: ")
                Slider(
                    value = polylineWidth,
                    onValueChange = { polylineWidth = it },
                    valueRange = 1f..30f,
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = { 
                    polylineColor = if (polylineColor == Color.Blue) Color.Red else Color.Blue 
                }) {
                    Text("Toggle Color")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("Customize Park (Polygon)", style = MaterialTheme.typography.titleSmall)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Stroke: ")
                Slider(
                    value = polygonStrokeWidth,
                    onValueChange = { polygonStrokeWidth = it },
                    valueRange = 1f..20f,
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = { 
                    polygonFillColor = if (polygonFillColor == Color.Green.copy(alpha = 0.3f)) 
                        Color.Yellow.copy(alpha = 0.3f) else Color.Green.copy(alpha = 0.3f)
                }) {
                    Text("Toggle Fill")
                }
            }
        }
    }
}
