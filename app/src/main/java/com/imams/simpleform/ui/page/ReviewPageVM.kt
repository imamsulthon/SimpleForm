package com.imams.simpleform.ui.page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imams.simpleform.data.mapper.Mapper.toEntity
import com.imams.simpleform.data.model.RegistrationInfo
import com.imams.simpleform.data.repository.RegistrationDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewPageVM @Inject constructor(
    private val repository: RegistrationDataRepository,
) : ViewModel() {

    private val _data = MutableLiveData<RegistrationInfo>()
    var data: LiveData<RegistrationInfo> = _data

    private val _doneSubmit = MutableLiveData<Pair<Boolean, String>>()
    val doneSubmit = _doneSubmit

    fun fetchData(init: RegistrationInfo) {
        viewModelScope.launch {
            _data.postValue(init)
        }
    }

    fun fetchData(id: String) {
        viewModelScope.launch {
            repository.getCompleteRegistrationData(id).collectLatest {
                _data.postValue(it)
            }
        }
    }

    fun submitReview() {
        viewModelScope.launch {
            _data.value?.let {
            repository.saveCompleteRegistration(it.toEntity())
                delay(1000)
                _doneSubmit.postValue(Pair(true, it.id))
            }
        }
    }

}