package com.bftv.arpinfo

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.abooc.joker.adapter.recyclerview.BaseRecyclerAdapter
import com.abooc.joker.adapter.recyclerview.BaseViewHolder
import com.abooc.joker.adapter.recyclerview.ViewHolder
import com.bftv.arpinfo.model.Device
import kotlinx.android.synthetic.main.list_item.view.*

/**
 *
 * @author Junpu
 * @time 2018/8/27 15:56
 */
class DeviceAdapter(context: Context) : BaseRecyclerAdapter<Device>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = context.inflate(R.layout.list_item, parent, false)
        return DeviceHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val conversation = getItem(position) ?: return
        holder as DeviceHolder
        holder.bindData(conversation)
    }

}

class DeviceHolder(view: View, listener: OnRecyclerItemClickListener?) : BaseViewHolder<Device>(view, listener) {

    override fun bindData(device: Device?) {
        itemView?.ip?.text = device?.ip
        itemView?.macAddress?.text = device?.mac
        itemView?.name?.text = device?.name
        itemView?.manufacturer?.text = device?.manufacturer
    }
}