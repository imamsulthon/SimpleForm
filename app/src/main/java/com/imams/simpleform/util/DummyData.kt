package com.imams.simpleform.util

import com.imams.simpleform.data.model.RegistrationInfo

object DummyData {

    fun createFullData() = RegistrationInfo(
        id = "12345678900101",
        fullName = "Imam Sulthon",
        bankAccount = "829992777",
        education = "S1",
        dob = "01051994",
        address = "Jalan Damai ",
        addressNo = "No B2 12",
        province = "Nusa Tenggara Barat",
        houseType = "Rumah",
    )
}