package com.imams.simpleform

import com.imams.simpleform.ui.page.form2.AddressInfoVM
import org.junit.Assert
import org.junit.Test

class FormAddressInfoTest {

    @Test
    fun validateFieldState() {
        val vcFieldDefault = AddressInfoVM.ValidityCheck() // default all false
        Assert.assertEquals(vcFieldDefault.allValid(), false)

        val onlyFieldIdTrue = AddressInfoVM.ValidityCheck().apply { address = true }
        Assert.assertEquals(onlyFieldIdTrue.allValid(), false)

        val vcFieldIdAllTrue = AddressInfoVM.ValidityCheck().apply {
            address = true
            houseType = true
            addressNo = true
            province = true
        }
        Assert.assertEquals(vcFieldIdAllTrue.allValid(), true)
    }


}