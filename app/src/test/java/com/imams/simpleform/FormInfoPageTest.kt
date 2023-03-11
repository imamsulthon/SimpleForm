package com.imams.simpleform

import com.imams.simpleform.data.util.DataExt
import com.imams.simpleform.data.util.DataExt.checkValidDate
import com.imams.simpleform.data.util.DataExt.checkValidIdCardNumber
import com.imams.simpleform.ui.page.form1.PersonalInfoVM
import org.junit.Assert
import org.junit.Test

class FormInfoPageTest {

    @Test
    fun validateDateInputFormat() {
        val amount = "12081993" // valid input
        val validDate = amount.checkValidDate()
        val amount2 = "4546490"
        val invalidDate = amount2.checkValidDate()
        val amount3 = "12/09/1993"
        val invalidDate3 = amount3.checkValidDate()
        Assert.assertEquals(validDate, true)
        Assert.assertEquals(invalidDate, false)
        Assert.assertEquals(invalidDate3, true)
    }

    @Test
    fun validateIdCardNumberInput() {
        val validId = "1241993212121212" // valid input should be 16 digits
        val validDigit = validId.checkValidIdCardNumber()
        Assert.assertEquals(validDigit, true)

        val lessId = "4546490"
        val lessDigit = lessId.checkValidIdCardNumber()
        Assert.assertEquals(lessDigit, false)

        val overId = "4546490999404033003"
        val overDigit = overId.checkValidIdCardNumber()
        Assert.assertEquals(overDigit, false)

        val invalidInput = "124199321212121x"
        val invalidDigit = invalidInput.checkValidIdCardNumber()
        Assert.assertEquals(invalidDigit, false)
    }

    @Test
    fun allFieldValidationState() {
        val vcFieldDefault = PersonalInfoVM.ValidityCheck() // default all false
        Assert.assertEquals(vcFieldDefault.allValid(), false)

        val onlyFieldIdTrue = PersonalInfoVM.ValidityCheck().apply { id = true }
        Assert.assertEquals(onlyFieldIdTrue.allValid(), false)

        val vcFieldIdAllTrue = PersonalInfoVM.ValidityCheck().apply {
            id = true
            name = true
            bankAcc = true
            edu = true
            dob = true
        }
        Assert.assertEquals(vcFieldIdAllTrue.allValid(), true)
    }

    @Test
    fun creatingDateTest() {
        val d1 = DataExt.creatingDate(1, 10, 1980)
        Assert.assertEquals(d1, "01/11/1980")
        val d2 = DataExt.creatingDate(32, 12, 1993)
        Assert.assertEquals(d2, "")
    }

}