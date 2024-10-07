package com.example.weatherrealtime

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherrealtime.api.WeatherModel

@Composable
fun WeatherDetails(data: WeatherModel) {
    Column {


    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp), // Rounded corners for the card
        colors = CardDefaults.cardColors(containerColor = Color.Transparent) // Set to transparent for gradient
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF4A90E2),
                            Color(0xFF00BFFF)
                        )
                    )
                )
                .padding(24.dp) // Padding for card content
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location icon",
                        modifier = Modifier.size(40.dp),
                        tint = Color.White // White icon for contrast
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = data.location.name,
                            fontSize = 30.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold // Bold title for emphasis
                        )
                        Text(
                            text = "${data.location.country}, ${data.location.localtime}",
                            fontSize = 20.sp,
                            color = Color.LightGray // Lighter color for secondary info
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${data.current.temp_c}°C",
                        fontSize = 48.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold // Emphasized temperature display
                    )
                    AsyncImage(
                        model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
                        contentDescription = "Weather Image",
                        modifier = Modifier.size(100.dp) // Adjusted size for better layout
                    )
                }

                Text(
                    text = data.current.condition.text,
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
        }
        Hello(data)
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Hello(data: WeatherModel) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .size(500.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp), // Rounded corners for the card
        colors = CardDefaults.cardColors(containerColor = Color.Transparent) // Set to transparent for gradient
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF4A90E2),
                            Color(0xFF00BFFF)
                        )
                    )
                )
                .padding(24.dp, 12.dp, 24.dp, 12.dp) // Padding for card content
                .size(400.dp)
        ) {
                Column(
                    modifier = Modifier
//                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(500.dp), // Adjusted height to provide enough space for content and dots
                    verticalArrangement = Arrangement.SpaceBetween // Space content evenly
                ) {
                    val pagerState = rememberPagerState(pageCount = { 2 }) // Directly pass the page count
                    DotsIndicator(pagerState = pagerState)
//                    Spacer(modifier = Modifier.height(20.dp))
                    HorizontalPager(state = pagerState) { page ->
                        when (page) {
                            0 -> FirstPageContent(data) // Content for the first page
                            1 -> SecondPageContent(data) // Content for the second page
                        }
                    }

                }

        }
    }
}
@Composable
fun FirstPageContent(data: WeatherModel) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Weather Details",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.White // Set text color to white
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Create a grid-like layout for better organization
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoItem(label = "Humidity", value = "${data.current.humidity}%", imageUrl = "https://icons.veryicon.com/png/o/object/material_design_icons/water-13.png")
            InfoItem(label = "Wind KPH", value = "${data.current.wind_kph} kph", imageUrl = "https://icons.veryicon.com/png/o/weather/weather-5/wind.png")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoItem(label = "UV Index", value = "${data.current.uv}", imageUrl = "https://icons.veryicon.com/png/o/system/eva-all/sun-39.png")
            InfoItem(label = "Gust KPH", value = "${data.current.gust_kph} kph", imageUrl = "https://icons.veryicon.com/png/o/miscellaneous/system-icon-2/wind-61.png")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoItem(label = "Rain Prob", value = "${data.current.precip_in} in", imageUrl = "https://icons.veryicon.com/png/128/business/meow-1/rain-26.png")
            InfoItem(label = "Heat Index", value = "${data.current.heatindex_c}°C", imageUrl = "https://icons.veryicon.com/png/o/internet--web/common-web-icons/w_-heat.png")
        }
    }
}

@Composable
fun SecondPageContent(data: WeatherModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Air Quality µg/m³ ",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.White // Set text color to white
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Create a grid-like layout for air quality metrics
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InfoItem(label = "CO", value = "${data.current.air_quality.co}", imageUrl = "https://icons.veryicon.com/png/o/miscellaneous/wugu-yuncube-icon-library/carbon-monoxide.png")
            Spacer(modifier = Modifier.size(8.dp))
            InfoItem(label = "NO2", value = "${data.current.air_quality.no2}", imageUrl= "https://icons.veryicon.com/png/128/miscellaneous/wugu-yuncube-icon-library/nitrogen-dioxide.png")
        }

        Spacer(modifier = Modifier.height(2.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InfoItem(label = "O3", value="${data.current.air_quality.o3}", imageUrl= "https://icons.veryicon.com/png/o/miscellaneous/operation-scenarios-menu-overview/operation-of-ozone-blowing.png")
            Spacer(modifier = Modifier.size(12.dp))
            InfoItem(label="PM10", value="${data.current.air_quality.pm10}", imageUrl= "https://icons.veryicon.com/png/o/miscellaneous/operation-scenarios-menu-overview/operation-of-ozone-blowing.png")
        }

        Spacer(modifier=Modifier.height(12.dp))

        Row(
            modifier=Modifier.fillMaxWidth(),
            horizontalArrangement=Arrangement.SpaceEvenly
        ) {
            InfoItem(label="PM2.5", value="${data.current.air_quality.pm2_5}", imageUrl= "https://icons.veryicon.com/png/o/application/app-icons/pm25.png")
            Spacer(modifier = Modifier.size(12.dp))
            InfoItem(label="SO2", value="${data.current.air_quality.so2}", imageUrl= "https://cdn3.iconfinder.com/data/icons/allergy-12/60/sulfurdioxide__allergy__dust__chemical__medical-512.png")
        }
    }
}

@Composable
fun InfoItem(label: String, value: String, imageUrl: String) {
    Column(horizontalAlignment=Alignment.CenterHorizontally) {
        AsyncImage(
            model=imageUrl,
            contentDescription=null,
            modifier= Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.LightGray) // Background color for visibility
                .padding(4.dp)
        )

        Spacer(modifier=Modifier.height(4.dp)) // Space between image and label

        Text(text=label, style=MaterialTheme.typography.bodyMedium.copy(fontSize=18.sp, color=Color.White)) // Set label text color to white

        Text(text=value, style=MaterialTheme.typography.bodyLarge.copy(fontSize=20.sp, color=Color.White)) // Set value text color to white
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DotsIndicator(pagerState: PagerState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { index ->
            val color = if (index == pagerState.currentPage) Color.White else Color.LightGray
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(color)
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}