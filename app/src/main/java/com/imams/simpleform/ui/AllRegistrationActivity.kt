package com.imams.simpleform.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.imams.simpleform.data.model.RegistrationInfo
import com.imams.simpleform.databinding.ActivityAllRegistrationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllRegistrationActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAllRegistrationBinding.inflate(layoutInflater) }

    private val viewModel: AllRegistrationVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initLiveData()
        fetchData()
    }

    private fun initView() {
        binding.btnDelete.setOnClickListener {
            viewModel.clearAllData()
            Snackbar.make(this, it, "Clear Data", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun fetchData() {
        viewModel.fetchData()
    }

    private fun initLiveData() {
        with(viewModel) {
            data.observe(this@AllRegistrationActivity) {
                it?.let { setContent(it) }
            }
        }
    }

    private fun setContent(list: List<RegistrationInfo>) {
        lifecycleScope.launch {
            var string = "data size: ${list.size} \n"
            list.forEach {
                string += "object: ${it.id}, ${it.fullName}, ${it.dob}, ${it.address}, ${it.province},\n"
            }
            binding.textview.text = string
        }
    }

}