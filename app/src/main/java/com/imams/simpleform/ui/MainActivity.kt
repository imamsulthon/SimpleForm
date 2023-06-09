package com.imams.simpleform.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.imams.simpleform.databinding.ActivityMainBinding
import com.imams.simpleform.ui.page.form1.FormPersonalInfoActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initListener()
    }

    private fun initListener() {
        with(binding) {
            btnRegistrationPageSequential.setOnClickListener {
                startActivity(Intent(this@MainActivity, FormPersonalInfoActivity::class.java))
            }

            btnRegistrationPageParallel.isEnabled = false
            btnRegistrationPageParallel.setOnClickListener {
            }

            btnConfiguration.isEnabled = false
            btnConfiguration.setOnClickListener {
            }

            btnShowAll.setOnClickListener {
                startActivity(Intent(this@MainActivity, AllRegistrationActivity::class.java))
            }
        }
    }


}