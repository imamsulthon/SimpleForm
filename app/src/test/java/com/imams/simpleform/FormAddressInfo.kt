package com.imams.simpleform

import com.imams.simpleform.ui.page.AddressInfoVM
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FormAddressInfo {

    @Before
    fun setUp() {
        // todo
    }

    @After
    fun tearDown() {
        // todo
    }

    @Test
    fun validateResponse() {

    }

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