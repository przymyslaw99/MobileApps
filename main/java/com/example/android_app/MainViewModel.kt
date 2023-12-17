package com.example.android_app

import UIState
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_app.repository.StarRepository
import com.example.android_app.repository.Starship
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import perfetto.protos.UiState

class MainViewModel : ViewModel() {

    private val starRepository = StarRepository()

//    private val mutableStarshipsData = MutableLiveData<List<Starship>>()
//    val immutableStarshipsData: LiveData<List<Starship>> = mutableStarshipsData

    private val mutableStarshipsData = MutableLiveData<UIState<List<Starship>>>()
    val immutableStarshipsData: LiveData<UIState<List<Starship>>> = mutableStarshipsData

    val liveDataStarships = MutableLiveData<UIState<List<Starship>>>()

    fun getData() {
        liveDataStarships.postValue(UIState(isLoading = true))

        viewModelScope.launch(Dispatchers.IO) {

            try{
                val request = starRepository.getStarResponse()
                Log.d("MainViewModel", "Request response code:")
                if (request.isSuccessful){
                    val starships = request.body()?.results ?: emptyList()
                    liveDataStarships.postValue(UIState(data = starships))
                }else{
                    liveDataStarships.postValue(UIState(error="${request.code()}"))
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Request failed ${e.message}", e)
            }

        }
    }
}