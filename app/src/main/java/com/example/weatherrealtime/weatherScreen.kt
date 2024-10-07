package com.example.weatherrealtime


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.weatherrealtime.api.NetworkResponse

@Composable
fun weatherScreen(viewModel: WeatherViewModel) {
    var city by remember { mutableStateOf("") }
    val weatherResult = viewModel.weatherResult.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier.fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF5B86E5),
                        Color(0xFF36D1DC)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text(text = "Search For Any Location", color = Color.DarkGray) },
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.getData(city)
                            keyboardController?.hide()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.Gray // Set icon color to match the aesthetic
                            )
                        }
                    },
                    modifier = Modifier.weight(1f).fillMaxWidth().padding(8.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Blue,
                        unfocusedIndicatorColor = Color.Gray,
                        focusedContainerColor = Color.White, // White background when focused for contrast
                        unfocusedContainerColor = Color(0xFFF2F2F2) // Light gray when unfocused for subtlety
                    ),
                    shape = RoundedCornerShape(10.dp) // Rounded corners for a modern look
                )
            }

            when (val result = weatherResult.value) {
                is NetworkResponse.Error -> {
                    Text(text = result.message, color = Color.Red, modifier = Modifier.padding(top = 16.dp))
                }
                NetworkResponse.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                }
                is NetworkResponse.Success -> {
                    WeatherDetails(data = result.data)
                }
                null -> {}
            }
        }
    }
}