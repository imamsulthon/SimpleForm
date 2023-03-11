package com.imams.simpleform.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.imams.simpleform.data.model.RegistrationInfo
import com.imams.simpleform.databinding.ActivityAllRegistrationBinding
import com.imams.simpleform.ui.common.ItemSubmitAdapter
import com.imams.simpleform.ui.page.form1.FormPersonalInfoActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllRegistrationActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAllRegistrationBinding.inflate(layoutInflater) }

    private val viewModel: AllRegistrationVM by viewModels()

    private val adapter by lazy { ItemSubmitAdapter(mutableListOf(), callback = {
        println("AllRegistrationPage adapterClick: $it")
        startActivity(Intent(this, FormPersonalInfoActivity::class.java).apply {
            putExtra(FormPersonalInfoActivity.NAV, true)
            putExtra(FormPersonalInfoActivity.TAG, it.id)
        })
    }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initLiveData()
        fetchData()
    }

    private fun initView() {
        with(binding) {
            btnDelete.setOnClickListener {
                viewModel.clearAllData()
            }
            recyclerview.layoutManager = LinearLayoutManager(this@AllRegistrationActivity,
                LinearLayoutManager.VERTICAL, false)
            recyclerview.adapter = adapter
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
        val string = "Total data: ${list.size} \n"
        binding.textview.text = string
        adapter.submitData(list.toMutableList())
    }

}