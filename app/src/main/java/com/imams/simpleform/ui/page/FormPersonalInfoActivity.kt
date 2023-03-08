package com.imams.simpleform.ui.page

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.imams.simpleform.databinding.ActivityFormPersonalInfoBinding
import com.imams.simpleform.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormPersonalInfoActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFormPersonalInfoBinding.inflate(layoutInflater) }

    private val viewModel: PersonalInfoVM by viewModels()

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
        }
    }

    private fun initView() {
        with(binding) {
            etIdCard.maxInput(16)
            etFullName.maxInput(32)
            etBankAccount.maxInput(18)
            etEducation.maxInput(10)
            etDob.maxInput(8)
        }
    }

    private fun initViewListener() {
        with(binding) {
            btnSave.setOnClickListener {
                printLog("initViewListener")
                viewModel.performSave(
                    id = etIdCard.text.longOrNull(),
                    name = etFullName.text.stringOrNull(),
                    bankAccount = etBankAccount.text.stringOrNull(),
                    education = etEducation.text.stringOrNull(),
                    dob = etDob.text.stringOrNull(),
                )
            }
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