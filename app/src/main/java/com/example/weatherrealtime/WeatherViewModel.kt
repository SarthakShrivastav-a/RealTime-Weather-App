package com.example.weatherrealtime


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherrealtime.api.Constant
import com.example.weatherrealtime.api.NetworkResponse
import com.example.weatherrealtime.api.RetrofitInstance
import com.example.weatherrealtime.api.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData (city:String){
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {

           try {
               val response = weatherApi.getWeather(apikey = Constant.apiKey,city,"yes")

               if(response.isSuccessful){

                   response.body()?.let {

                       _weatherResult.value = NetworkResponse.Success(it)

                   }


//                Log.i("response",response.body().toString())
               }
               else{

                   _weatherResult.value = NetworkResponse.Error("Failed To Load Data")
//                Log.i("Error",response.message())
               }

           }
           catch (e : Exception){
               _weatherResult.value = NetworkResponse.Error("Failed To Load Data")
           }
        }

    }

}
