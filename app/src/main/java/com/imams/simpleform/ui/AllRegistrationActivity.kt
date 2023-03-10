package com.imams.simpleform.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.imams.simpleform.data.model.RegistrationInfo
import com.imams.simpleform.databinding.ActivityAllRegistrationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllRegistrationActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAllRegistrationBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observeLiveData()
        fetchData()
    }

    private fun fetchData() {
        viewModel.fetchData()
    }

    private fun observeLiveData() {
        with(viewModel) {
            data.observe(this@AllRegistrationActivity) {
                it?.let { setContent(it) }
            }
        }
    }

    private fun setContent(list: List<RegistrationInfo>) {
        lifecycleScope.launch {
            var string = "data size: ${list.size} \n "
            list.forEach {
                string += " object ${it.id}, ${it.fullName}, "
            }
            binding.textview.text = string
        }
    }

}