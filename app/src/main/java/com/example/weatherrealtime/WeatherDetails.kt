package com.example.weatherrealtime

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherrealtime.api.WeatherModel

@Composable
fun WeatherDetails(data: WeatherModel){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ){
            Icon(
                imageVector =Icons.Default.LocationOn ,
                contentDescription = "Location icon",
                modifier = Modifier.size(40.dp)
                )
            Text(text = data.location.name, fontSize = 30.sp)
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = data.location.country, fontSize = 20.sp, color = Color.Gray)
        }
        Text(text = "${data.current.temp_c}°C", fontSize = 90.sp)

        AsyncImage(
            model = "https:${data.current.condition.icon}".replace("64x64","128x128"),
            contentDescription = "Weather Image",
            modifier = Modifier.size(250.dp)

            )
    }
}