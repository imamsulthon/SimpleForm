package com.imams.simpleform.ui.page.form1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imams.simpleform.data.model.PersonalInfo
import com.imams.simpleform.data.util.DataExt.checkValidDate
import com.imams.simpleform.data.util.DataExt.checkValidIdCardNumber
import com.imams.simpleform.domain.usecase.FormPersonalInfoUseCase
import com.imams.simpleform.ui.common.FieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalInfoVM @Inject constructor(
    private val useCase: FormPersonalInfoUseCase,
) : ViewModel() {

    private val _initData = MutableLiveData<PersonalInfo>()
    val initData: LiveData<PersonalInfo> = _initData

    private val _idField = MutableLiveData<FieldState<Long>>()
    val idField: LiveData<FieldState<Long>> = _idField

    private val _fullNameField = MutableLiveData<FieldState<String>>()
    val fullNameField: LiveData<FieldState<String>> = _fullNameField

    private val _bankAccountField = MutableLiveData<FieldState<String>>()
    val bankAccountField: LiveData<FieldState<String>> = _bankAccountField

    private val _educationField = MutableLiveData<FieldState<String>>()
    val educationField: LiveData<FieldState<String>> = _educationField

    private val _dobField = MutableLiveData<FieldState<String>>()
    val dobField: LiveData<FieldState<String>> = _dobField

    private val _doneSave = MutableLiveData<Pair<Boolean, String>>()
    val doneSave: LiveData<Pair<Boolean, String>> = _doneSave

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun initData(id: String) {
        fetchData(id)
    }

    private fun fetchData(id: String) {
        viewModelScope.launch {
            useCase.getPersonalInfo(id).collectLatest {
                _initData.postValue(it)
            }
        }
    }

    fun performSave(
        id: String?,
        name: String?,
        bankAccount: String?,
        education: String?,
        dob: String?,
    ) {
        viewModelScope.launch {
            val vc = ValidityCheck()

            if (id == null) {
                _idField.postValue(FieldState.IsNullOrEmpty(true))
            } else if (!id.checkValidIdCardNumber()) {
                _idField.postValue(FieldState.Warn("Nomor KTP harus 15 digit angka"))
            } else {
                _idField.postValue(FieldState.Valid())
                vc.id = true
            }

            if (name.isNullOrEmpty()) {
                _fullNameField.postValue(FieldState.IsNullOrEmpty(true))
            } else {
                _fullNameField.postValue(FieldState.Valid())
                vc.name = true
            }

            if (bankAccount.isNullOrEmpty()) {
                _bankAccountField.postValue(FieldState.IsNullOrEmpty(true))
            } else {
                _bankAccountField.postValue(FieldState.Valid())
                vc.bankAcc = true
            }

            if (education.isNullOrEmpty()) {
                _educationField.postValue(FieldState.IsNullOrEmpty(true))
            } else {
                _educationField.postValue(FieldState.Valid())
                vc.edu = true
            }

            if (dob.isNullOrEmpty()) {
                _dobField.postValue(FieldState.IsNullOrEmpty(true))
            } else if (!dob.checkValidDate()) {
                _dobField.postValue(FieldState.Warn("Tanggal lahir tidak valid. Contoh: 17 Mei 1996 -> 17051996"))
            } else {
                _dobField.postValue(FieldState.Valid())
                vc.dob = true
            }

            if (vc.allValid()) {
                savePersonalInfoData(
                    PersonalInfo(id.orEmpty(), name.orEmpty(), bankAccount.orEmpty(), education.orEmpty(), dob.orEmpty())
                )
            } else {
                _doneSave.postValue(Pair(false, "invalid"))
            }
        }
    }

    private fun savePersonalInfoData(data: PersonalInfo) {
        viewModelScope.launch {
            _loading.postValue(true)
            useCase.savePersonalInfo(data)
            delay(1000)
            _doneSave.postValue(Pair(true, data.id))
            _loading.postValue(false)
        }
    }

    class ValidityCheck(
        var id: Boolean = false,
        var name: Boolean = false,
        var bankAcc: Boolean = false,
        var edu: Boolean = true, // todo false
        var dob: Boolean = false,
    ) {
        fun allValid() = id && name && bankAcc && edu && dob
    }

}