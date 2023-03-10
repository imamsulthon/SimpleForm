package com.imams.simpleform.ui.page.review

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.imams.simpleform.R
import com.imams.simpleform.data.model.RegistrationInfo
import com.imams.simpleform.databinding.ActivityReviewPageBinding
import com.imams.simpleform.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewPageActivity : AppCompatActivity() {

    private val binding by lazy { ActivityReviewPageBinding.inflate(layoutInflater) }

    private val viewModel: ReviewPageVM by viewModels()

    private var sequentialNavigation = true

    companion object {
        const val TAG = "tag_review"
        const val NAV = "tag_nav"
        const val DATA = "tag_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initViewListener()
        initLiveData()
        fetchData()
    }

    private fun initView() {
        binding.appbar.toolbar.setTitle(R.string.page_form_title_review)
    }

    private fun fetchData() {
        intent.getBooleanExtra(NAV, sequentialNavigation).let { sequentialNavigation = it }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(DATA, RegistrationInfo::class.java)?.let {
                printLog("intent with data $it")
                viewModel.fetchData(it)
                return
            }
        }
        printLog("intent with no data")
        intent.getStringExtra(TAG)?.let {
            viewModel.fetchData(it)
        }
    }

    private fun initLiveData() {
        with(viewModel) {
            data.observe(this@ReviewPageActivity) {
                it?.let { setContent(it) }
            }
            doneSubmit.observe(this@ReviewPageActivity) {
                it?.let { if (it.first) navigate(sequentialNavigation, it.second) }
            }
        }
    }

    private fun initViewListener() {
        with(binding) {
            btnSave.setOnClickListener {
                if (sequentialNavigation) viewModel.submitReview()
                else navigate(false, "passed")
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setContent(data: RegistrationInfo) {
        with(binding) {
            data.let {
                tvValueIdNumber.text = it.id
                tvValueName.text= it.fullName
                tvValueBank.text = it.bankAccount
                tvValueEdu.text= it.education
                tvValueDob.text = it.dob
                tvValueAddress.text= "$it.address ${it.addressNo}"
                tvValueProvince.text = it.province
            }
        }
    }

    private fun navigate(sequentialNav: Boolean, id: String) {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        )
        finish()
    }

    // todo delete
    private fun printLog(msg: String, tag: String? = "ReviewRegistration Page") {
        println("$tag: msg -> $msg")
    }

}