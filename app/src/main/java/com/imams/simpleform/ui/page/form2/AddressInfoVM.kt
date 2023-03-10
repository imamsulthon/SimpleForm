package com.imams.simpleform.ui.page.form2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imams.simpleform.data.mapper.Mapper.toEntity
import com.imams.simpleform.data.model.AddressInfo
import com.imams.simpleform.data.model.PersonalInfo
import com.imams.simpleform.data.model.Province
import com.imams.simpleform.data.repository.ProvinceDataRepository
import com.imams.simpleform.data.repository.RegistrationDataRepository
import com.imams.simpleform.ui.page.FieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressInfoVM @Inject constructor(
    private val repository: RegistrationDataRepository,
    private val provinceRepository: ProvinceDataRepository,
): ViewModel() {

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

    var personalInfo: PersonalInfo? = null

    fun fetchData(id: String?) {
        fetchProvinceData()
        id?.let { initData(it) }
    }

    private fun initData(id: String) {
        viewModelScope.launch {
            repository.getPersonalInfo(id).collectLatest {
                personalInfo = it
            }
            repository.getAddressInfo(id).collectLatest {
                printLog("address $it")
            }
        }
    }

    private fun fetchProvinceData() {
        viewModelScope.launch {
            provinceRepository.getProvinces().collectLatest {
                printLog("fetchProvinceData ${it.size} $it")
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
            printLog("id: $address, name: $houseType, bank: $addressNo, edu: $province")
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
                printLog("all field is valid")
                submitAddress(
                    AddressInfo("", address.orEmpty(), houseType.orEmpty(), addressNo.orEmpty(), province.orEmpty())
                )
            } else {
                val msg = "some fields are not valid or you access from restricted entry point"
                printLog(msg)
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
            repository.saveCompleteRegistration(personalInfo.toEntity(addressInfo))
            delay(1000)
            _doneSave.postValue(Pair(true, addressInfo.id))
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

    private fun printLog(msg: String, tag: String? = "AddressInfoVM") {
        println("$tag: msg -> $msg")
    }
    
}