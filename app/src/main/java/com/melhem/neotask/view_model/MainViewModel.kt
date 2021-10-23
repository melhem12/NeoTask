package com.melhem.neotask.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.melhem.neotask.Models.History
import com.melhem.neotask.Models.Options
import com.melhem.neotask.Models.Portfolio
import com.melhem.neotask.repositories.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {

    val portfolioList = MutableLiveData<List<Portfolio>>()
    val optionsList = MutableLiveData<List<Options>>()
    val historyList = MutableLiveData<List<History>>()

    val errorMessage = MutableLiveData<String>()
    val errorMessageOptions = MutableLiveData<String>()
    val errorMessageHistory = MutableLiveData<String>()

    fun getAllPortFolios() {

        val response = repository.getAllPortFolios()
        response.enqueue(object : Callback<List<Portfolio>> {
            override fun onResponse(call: Call<List<Portfolio>>, response: Response<List<Portfolio>>) {
                portfolioList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Portfolio>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getAllOptions() {

        val response = repository.getAllOptions()
        response.enqueue(object : Callback<List<Options>> {
            override fun onResponse(call: Call<List<Options>>, response: Response<List<Options>>) {
                optionsList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Options>>, t: Throwable) {
                errorMessageOptions.postValue(t.message)
            }
        })
    }


    fun getAllHistorical() {

        val response = repository.getAllHistorical()
        response.enqueue(object : Callback<List<History>> {
            override fun onResponse(call: Call<List<History>>, response: Response<List<History>>) {
                historyList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<History>>, t: Throwable) {
                errorMessageHistory.postValue(t.message)
            }
        })
    }


}