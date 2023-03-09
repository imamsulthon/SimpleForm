package com.imams.simpleform.ui.page

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.imams.simpleform.databinding.ActivityFormPersonalInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormAddressInfoActivity: AppCompatActivity() {

    private val binding by lazy { ActivityFormPersonalInfoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }


}