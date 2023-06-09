package com.imams.simpleform.ui.page.form2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.imams.simpleform.R
import com.imams.simpleform.data.model.Province
import com.imams.simpleform.databinding.FormAddressInfoBinding
import com.imams.simpleform.ui.common.FieldState
import com.imams.simpleform.ui.page.review.ReviewPageActivity
import com.imams.simpleform.util.errorMessage
import com.imams.simpleform.util.maxInput
import com.imams.simpleform.util.stringOrNull
import com.imams.simpleform.util.warnMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormAddressInfoActivity: AppCompatActivity() {

    private val binding by lazy { FormAddressInfoBinding.inflate(layoutInflater) }

    private val viewModel: AddressInfoVM by viewModels()

    private var provinceList: MutableList<String> = mutableListOf()

    private val provinceAdapter by lazy {
        ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, provinceList)
    }

    private var sequentialNavigation = true

    companion object {
        const val TAG = "tag_address"
        const val DATA = "tag_data"
        const val NAV = "tag_navigation"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initViewListener()
        initLiveData()
        fetchData()
    }

    private fun fetchData() {
        with(intent) {
            sequentialNavigation = getBooleanExtra(NAV, sequentialNavigation)
            getStringExtra(TAG).let { viewModel.fetchData(it) }
        }
    }

    private fun initLiveData() {
        with(viewModel) {
            initData.observe(this@FormAddressInfoActivity) {
                it?.let {
                    binding.etAddress.setText(it.address)
                    binding.etAddressNo.setText(it.addressNo)
                    binding.etHousing.setText(it.houseType)
                    binding.etProvince.setText(it.province)
                }
            }

            addressField.observe(this@FormAddressInfoActivity) {
                it?.let {
                    when (it) {
                        is FieldState.Init -> {
                            binding.etAddress.setText(it.data.toString())
                        }
                        is FieldState.IsNullOrEmpty -> {
                            binding.tilAddress.errorMessage(getString(R.string.field_label_address))
                        }
                        is FieldState.Warn -> {
                            binding.tilAddress.warnMessage(it.message)
                        }
                        else -> {
                            binding.tilAddress.errorMessage(null)
                        }
                    }
                }
            }
            houseTypeField.observe(this@FormAddressInfoActivity) {
                it?.let {
                    when (it) {
                        is FieldState.Init -> {
                            binding.etHousing.setText(it.data)
                        }
                        is FieldState.IsNullOrEmpty -> {
                            binding.tilHousing.errorMessage(getString(R.string.field_label_housing))
                        }
                        is FieldState.Warn -> {
                            binding.tilHousing.warnMessage(it.message)
                        }
                        else -> {
                            binding.tilHousing.errorMessage(null)
                        }
                    }
                }
            }
            addressNoField.observe(this@FormAddressInfoActivity) {
                it?.let {
                    when (it) {
                        is FieldState.Init -> {
                            binding.etAddressNo.setText(it.data)
                        }
                        is FieldState.IsNullOrEmpty -> {
                            binding.tilAddressNo.errorMessage(getString(R.string.field_label_address_no))
                        }
                        is FieldState.Warn -> {
                            binding.tilAddressNo.warnMessage(it.message)
                        }
                        else -> {
                            binding.tilAddressNo.errorMessage(null)
                        }
                    }
                }
            }

            provinceField.observe(this@FormAddressInfoActivity) {
                it?.let {
                    when (it) {
                        is FieldState.Init -> {
                            binding.etProvince.setText(it.data)
                        }
                        is FieldState.IsNullOrEmpty -> {
                            binding.tilProvince.errorMessage(getString(R.string.field_label_province))
                        }
                        is FieldState.Warn -> {
                            binding.tilProvince.warnMessage(it.message)
                        }
                        else -> {
                            binding.tilProvince.errorMessage(null)
                        }
                    }
                }
            }

            doneSave.observe(this@FormAddressInfoActivity) {
                it?.let { if (it.first) navigate(sequentialNavigation, it.second) else showToast(it.second) }
            }

            provinceList.observe(this@FormAddressInfoActivity) {
                it?.let { updateProvinceOptionals(it) }
            }

            loading.observe(this@FormAddressInfoActivity) {
                it?.let { showLoading(it) }
            }
        }
    }

    private fun initView() {
        with(binding) {
            appbar.toolbar.setTitle(R.string.page_form_title_address_info)
            etAddress.maxInput(100)
            etAddressNo.maxInput(10)

            val arr = resources.getStringArray(R.array.housing_type_array)
            ArrayAdapter.createFromResource(this@FormAddressInfoActivity, R.array.housing_type_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerHousing.setSelection(-1)
                spinnerHousing.adapter = adapter
                spinnerHousing.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        if (p2 > 0) etHousing.setText(arr[p2])
                        else etHousing.text = null
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }
            }

            provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerProvince.setSelection(-1)
            spinnerProvince.adapter = provinceAdapter
            spinnerProvince.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p2 > 0) etProvince.setText(provinceList[p2])
                    else etProvince.text = null
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun initViewListener() {
        with(binding) {
            btnSave.setOnClickListener {
                viewModel.performSave(
                    address = etAddress.text.stringOrNull(),
                    houseType = etHousing.text.stringOrNull(),
                    addressNo = etAddressNo.text.stringOrNull(),
                    province = etProvince.text.stringOrNull(),
                )
            }
        }
    }

    private fun showLoading(visible: Boolean) {
        with(binding) {
            loading.isVisible= visible
            btnSave.isEnabled = !visible
        }
    }

    private fun updateProvinceOptionals(list: List<Province>) {
        val l = mutableListOf("Pilih Provinsi")
        l.addAll(list.map { it.name })
        provinceList.clear()
        provinceList.addAll(l)
        provinceAdapter.notifyDataSetChanged()
    }

    private fun navigate(sequentialNav: Boolean, id: String) {
        if (sequentialNav) {
            startActivity(Intent(this, ReviewPageActivity::class.java).apply {
                putExtra(ReviewPageActivity.TAG, id)
            })
        } else {
            finish()
        }
    }

    // todo: change to SnackBar
    private fun showToast(msg: String, page: String = "Form Address Info") {
        Toast.makeText(this, "$page: $msg", Toast.LENGTH_LONG).show()
    }

}