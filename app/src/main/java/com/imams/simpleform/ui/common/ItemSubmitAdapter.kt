package com.imams.simpleform.ui.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imams.simpleform.R
import com.imams.simpleform.data.model.RegistrationInfo
import com.imams.simpleform.databinding.ItemSubmitedBinding

class ItemSubmitAdapter constructor(
    private var list: MutableList<RegistrationInfo>,
    private val callback: ((RegistrationInfo) -> Unit)? = null
) : RecyclerView.Adapter<ItemSubmitAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(list: MutableList<RegistrationInfo>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemSubmitedBinding = ItemSubmitedBinding.bind(itemView)

        fun bind(item: RegistrationInfo) {
            with(binding) {
                item.let {
                    val t1 = "ID KTP: ${it.id}"
                    tvIdCard.text = t1
                    val t2 = "Address: ${it.address} No. ${it.addressNo}, ${it.province} \n${it.houseType}"
                    tvAddressInfo.text = t2
                    val t3 = "Profile: ${it.fullName}, DOB: ${it.dob} Bank Acc. ${it.bankAccount}, \nedu: ${it.education}"
                    tvPersonalInfo.text = t3
                    val status = if (it.isSubmit) "submitted" else "Not submitted"
                    tvStatus.text = status
                }
                itemView.setOnClickListener {
                    callback?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_submited, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

}