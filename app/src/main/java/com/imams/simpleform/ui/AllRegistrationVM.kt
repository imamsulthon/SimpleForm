package com.imams.simpleform.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imams.simpleform.data.model.RegistrationInfo
import com.imams.simpleform.data.repository.RegistrationDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllRegistrationVM @Inject constructor(
    private val repository: RegistrationDataRepository
): ViewModel() {


    private val _data = MutableLiveData<List<RegistrationInfo>>()
    val data: LiveData<List<RegistrationInfo>> = _data

    fun fetchData() {
        viewModelScope.launch {
            repository.getAllCompleteUsers().collectLatest {
                printLog("collectAll total ${  it.size}, data $it")
                _data.postValue(it)
            }
        }
    }

    fun clearAllData() {
        viewModelScope.launch {
            repository.clearAllData()
            delay(1000)
        }
    }

    private fun printLog(msg: String, tag: String? = "MainViewModel") {
        println("$tag: msg -> $msg")
    }
}