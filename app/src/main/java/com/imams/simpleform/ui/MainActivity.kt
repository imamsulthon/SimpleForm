package com.imams.simpleform.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.imams.simpleform.databinding.ActivityMainBinding
import com.imams.simpleform.ui.page.form1.FormPersonalInfoActivity
import com.imams.simpleform.ui.page.form2.FormAddressInfoActivity
import com.imams.simpleform.ui.page.review.ReviewPageActivity
import com.imams.simpleform.util.DummyData
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

            btnRegistrationPageParallel.setOnClickListener {
                // todo delete for testing purpose
                startActivity(Intent(this@MainActivity, FormAddressInfoActivity::class.java).apply {
                    putExtra(FormAddressInfoActivity.NAV, false)
                })
            }

            btnConfiguration.setOnClickListener {
                // todo delete for testing purpose
                startActivity(Intent(this@MainActivity, ReviewPageActivity::class.java).apply {
                    putExtra(ReviewPageActivity.NAV, false)
                    putExtra(ReviewPageActivity.DATA, DummyData.createFullData())
                })
            }

            btnShowAll.setOnClickListener {
                startActivity(Intent(this@MainActivity, AllRegistrationActivity::class.java))
            }
        }
    }


}