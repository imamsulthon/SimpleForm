package com.imams.simpleform.ui.page.form2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imams.simpleform.data.model.AddressInfo
import com.imams.simpleform.data.model.PersonalInfo
import com.imams.simpleform.data.model.Province
import com.imams.simpleform.domain.usecase.AddressFormUseCase
import com.imams.simpleform.ui.common.FieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressInfoVM @Inject constructor(
    private val useCase: AddressFormUseCase,
): ViewModel() {

    private val _initData = MutableLiveData<AddressInfo>()
    var initData: LiveData<AddressInfo> = _initData

    private val _addressField = MutableLiveData<FieldState<Long>>()
    val addressField: LiveData<FieldState<Long>> = _addressField

    private val _houseTypeField = MutableLiveData<FieldState<String>>()
    val houseTypeField: LiveData<FieldState<String>> = _houseTypeField

    private val _addressNoField = MutableLiveData<FieldState<String>>()
    val addressNoField: LiveData<FieldState<String>> = _addressNoField

    private val _provinceField = MutableLiveData<FieldState<String>>()
    val provinceField: LiveData<FieldState<String>> = _provinceField

    private val _doneSave = MutableLiveData<Pair<Boolean, String>>()
    val doneSave: LiveData<Pair<Boolean, String>> = _doneSave

    private val _provinceData = MutableLiveData<List<Province>>()
    val provinceList: LiveData<List<Province>> = _provinceData

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    var personalInfo: PersonalInfo? = null

    fun fetchData(id: String?) {
        fetchProvinceData()
        id?.let { initData(it) }
    }

    private fun initData(id: String) {
        viewModelScope.launch {
            useCase.getPersonalInfo(id).collectLatest { personalInfo = it }
        }
        viewModelScope.launch {
            useCase.getAddressInfo(id).collectLatest { _initData.postValue(it) }
        }
    }

    private fun fetchProvinceData() {
        viewModelScope.launch {
            useCase.getProvinces().collectLatest {
                _provinceData.postValue(it)
            }
        }
    }

    fun performSave(
        address: String?,
        houseType: String?,
        addressNo: String?,
        province: String?,
    ) {
        viewModelScope.launch {
            val vc = ValidityCheck()

            if (address.isNullOrEmpty()) {
                _addressField.postValue(FieldState.IsNullOrEmpty(true))
            } else {
                _addressField.postValue(FieldState.Valid())
                vc.address = true
            }

            if (houseType.isNullOrEmpty()) {
                _houseTypeField.postValue(FieldState.IsNullOrEmpty(true))
            } else {
                _houseTypeField.postValue(FieldState.Valid())
                vc.houseType = true
            }

            if (addressNo.isNullOrEmpty()) {
                _addressNoField.postValue(FieldState.IsNullOrEmpty(true))
            } else {
                _addressNoField.postValue(FieldState.Valid())
                vc.addressNo = true
            }

            if (province.isNullOrEmpty()) {
                _provinceField.postValue(FieldState.IsNullOrEmpty(true))
            } else {
                _provinceField.postValue(FieldState.Valid())
                vc.province = true
            }

            if (vc.allValid() && personalInfo != null) {
                submitAddress(
                    AddressInfo("", address.orEmpty(), houseType.orEmpty(), addressNo.orEmpty(), province.orEmpty())
                )
            } else {
                val msg = "some fields are not valid or you access from restricted entry point"
                _doneSave.postValue(Pair(false, msg))
            }
        }
    }

    private fun submitAddress(data: AddressInfo) {
        if (personalInfo == null) {
            val msg = "invalid. You hadn't filled data on Personal Info Page"
            _doneSave.postValue(Pair(false, msg))
            return
        }
        personalInfo?.let { saveCompleteData(it, data.apply { id = it.id }) }
    }

    private fun saveCompleteData(personalInfo: PersonalInfo, addressInfo: AddressInfo) {
        viewModelScope.launch {
            _loading.postValue(true)
            useCase.saveAddressInfo(addressInfo, personalInfo)
            delay(1000)
            _doneSave.postValue(Pair(true, addressInfo.id))
            _loading.postValue(false)
        }
    }

    class ValidityCheck(
        var address: Boolean = false,
        var houseType: Boolean = false,
        var addressNo: Boolean = false,
        var province: Boolean = false, // todo false
    ) {
        fun allValid() = address && houseType && addressNo && province
    }
    
}