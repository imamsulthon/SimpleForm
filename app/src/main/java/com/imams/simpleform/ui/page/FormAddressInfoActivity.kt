package com.imams.simpleform.ui.page

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.imams.simpleform.R
import com.imams.simpleform.data.model.Province
import com.imams.simpleform.databinding.FormAddressInfoBinding
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
        viewModel.fetchData()
    }

    private fun initLiveData() {
        with(viewModel) {
            addressField.observe(this@FormAddressInfoActivity) {
                it?.let {
                    when (it) {
                        is FieldState.Init -> {
                            binding.etAddress.setText(it.data.toString())
                        }
                        is FieldState.IsNullOrEmpty -> {
                            binding.tilAddress.errorMessage("Alamat KTP")
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
                            binding.tilHousing.errorMessage("Tipe Rumah")
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
                            binding.etAddressNo.setText(it.data.toString())
                        }
                        is FieldState.IsNullOrEmpty -> {
                            binding.tilAddressNo.errorMessage("Nomor Rumah")
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
                            binding.tilProvince.errorMessage("Provinsi")
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
                it?.let { if (it) navigate() }
            }

            provinceList.observe(this@FormAddressInfoActivity) {
                it?.let { updateProvinceOptionals(it) }
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
                spinnerHousing.setSelection(-1);
                spinnerHousing.adapter = adapter
                spinnerHousing.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        printLog("spinner ${arr[p2]} $p2")
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
                    printLog("spinner ${provinceList[p2]} $p2")
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
                printLog("initViewListener")
                viewModel.performSave(
                    address = etAddress.text.stringOrNull(),
                    houseType = etHousing.text.stringOrNull(),
                    addressNo = etAddressNo.text.stringOrNull(),
                    province = etProvince.text.stringOrNull(),
                )
            }
        }
    }

    private fun updateProvinceOptionals(list: List<Province>) {
        printLog("setProvinceData init ${list.size} $list")
        provinceList.clear()
        provinceList.addAll(list.map { it.name })
        provinceAdapter.notifyDataSetChanged()
    }

    private fun navigate() {
        if (sequentialNavigation) {
//            startActivity(Intent(this, FormAddressInfoActivity::class.java))
            finish()
        } else {
            finish()
        }
    }

    private fun printLog(msg: String, tag: String? = "FormAddressInfo Page") {
        println("$tag: msg -> $msg")
    }

}