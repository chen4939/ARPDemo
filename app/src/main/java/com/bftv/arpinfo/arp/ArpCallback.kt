package com.bftv.arpinfo.arp

import com.bftv.arpinfo.model.Device

/**
 * Arp回调
 * @author Junpu
 * @time 2018/9/4 18:10
 */
interface ArpCallback {
    fun callback(device: Device)
}