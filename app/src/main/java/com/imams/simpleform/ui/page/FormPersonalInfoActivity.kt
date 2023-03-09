package com.imams.simpleform.ui.page

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.imams.simpleform.R
import com.imams.simpleform.databinding.ActivityFormPersonalInfoBinding
import com.imams.simpleform.util.errorMessage
import com.imams.simpleform.util.maxInput
import com.imams.simpleform.util.stringOrNull
import com.imams.simpleform.util.warnMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormPersonalInfoActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFormPersonalInfoBinding.inflate(layoutInflater) }

    private val viewModel: PersonalInfoVM by viewModels()

    private val sequentialNavigation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initViewListener()
        initLiveData()
        fetchData()
    }

    private fun fetchData() {

    }

    private fun initLiveData() {
        with(viewModel) {
            idField.observe(this@FormPersonalInfoActivity) {
                it?.let {
                    when (it) {
                        is FieldState.Init -> {
                            binding.etIdCard.setText(it.data.toString())
                        }
                        is FieldState.IsNullOrEmpty -> {
                            binding.tilIdCard.errorMessage("Nomor KTP")
                        }
                        is FieldState.Warn -> {
                            binding.tilIdCard.warnMessage(it.message)
                        }
                        else -> {
                            binding.tilIdCard.errorMessage(null)
                        }
                    }
                }
            }
            fullNameField.observe(this@FormPersonalInfoActivity) {
                it?.let {
                    when (it) {
                        is FieldState.Init -> {
                            binding.etFullName.setText(it.data)
                        }
                        is FieldState.IsNullOrEmpty -> {
                            binding.tilFullName.errorMessage("Nama Lengkap")
                        }
                        is FieldState.Warn -> {
                            binding.tilFullName.warnMessage(it.message)
                        }
                        else -> {
                            binding.tilFullName.errorMessage(null)
                        }
                    }
                }
            }
            bankAccountField.observe(this@FormPersonalInfoActivity) {
                it?.let {
                    when (it) {
                        is FieldState.Init -> {
                            binding.etBankAccount.setText(it.data.toString())
                        }
                        is FieldState.IsNullOrEmpty -> {
                            binding.tilBankAccount.errorMessage("Nomor Rekening")
                        }
                        is FieldState.Warn -> {
                            binding.tilBankAccount.warnMessage(it.message)
                        }
                        else -> {
                            binding.tilBankAccount.errorMessage(null)

                        }
                    }
                }
            }

            educationField.observe(this@FormPersonalInfoActivity) {
                it?.let {
                    when (it) {
                        is FieldState.Init -> {
                            binding.etEducation.setText(it.data)
                        }
                        is FieldState.IsNullOrEmpty -> {
                            binding.tilEducation.errorMessage("Pendidikan")
                        }
                        is FieldState.Warn -> {
                            binding.tilEducation.warnMessage(it.message)
                        }
                        else -> {
                            binding.tilEducation.errorMessage(null)
                        }
                    }
                }
            }

            dobField.observe(this@FormPersonalInfoActivity) {
                it?.let {
                    when (it) {
                        is FieldState.Init -> {
                            binding.etDob.setText(it.data)
                        }
                        is FieldState.IsNullOrEmpty -> {
                            binding.tilDob.errorMessage("Tanggal Lahir")
                        }
                        is FieldState.Warn -> {
                            binding.tilDob.warnMessage(it.message)
                        }
                        else -> {
                            binding.tilDob.errorMessage(null)
                        }
                    }
                }
            }
            doneSave.observe(this@FormPersonalInfoActivity) {
                it?.let { if (it) navigate() }
            }
        }
    }

    private fun initView() {
        with(binding) {
            appbar.toolbar.setTitle(R.string.page_form_title_personal_info)
            etIdCard.maxInput(16)
            etFullName.maxInput(32)
            etBankAccount.maxInput(18)
            etEducation.maxInput(10)
            etDob.maxInput(8)
            val arr = resources.getStringArray(R.array.education_array)
            ArrayAdapter.createFromResource(this@FormPersonalInfoActivity, R.array.education_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerEducation.setSelection(-1);
                spinnerEducation.adapter = adapter
                spinnerEducation.onItemSelectedListener = object : OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        printLog("spinner ${arr[p2]} $p2")
                        if (p2 > 0) etEducation.setText(arr[p2])
                        else etEducation.text = null
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) { printLog("spinner onNothing") }
                }

            }
        }
    }

    private fun initViewListener() {
        with(binding) {
            btnSave.setOnClickListener {
                printLog("initViewListener")
                viewModel.performSave(
                    id = etIdCard.text.stringOrNull(),
                    name = etFullName.text.stringOrNull(),
                    bankAccount = etBankAccount.text.stringOrNull(),
                    education = etEducation.text.stringOrNull(),
                    dob = etDob.text.stringOrNull(),
                )
            }
        }
    }

    private fun navigate() {
        if (sequentialNavigation) {
            startActivity(Intent(this, FormAddressInfoActivity::class.java))
        } else {
            finish()
        }
    }

    private fun warnField(fieldName: String) {
        showToast("$fieldName cannot be empty")
    }

    private fun showToast(msg: String, page: String = "Form Personal Info") {
        Toast.makeText(this, "$page: $msg", Toast.LENGTH_SHORT).show()
    }

    private fun printLog(msg: String, tag: String? = "PersonalInfoVM") {
        println("$tag: msg -> $msg")
    }

}