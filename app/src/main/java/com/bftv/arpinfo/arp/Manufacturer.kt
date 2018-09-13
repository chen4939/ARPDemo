package com.bftv.arpinfo.arp

import android.content.Context
import com.abooc.util.Debug
import java.util.*

/**
 * 厂商匹配
 * @author Junpu
 * @time 2018/9/6 16:57
 */
class Manufacturer private constructor() {

    companion object {
        val instance by lazy { Manufacturer() }
    }

    private var manufacturer: Properties? = null

    /**
     * 初始化Manufacturer数据文件
     */
    fun init(context: Context) {
        if (manufacturer == null) {
            manufacturer = Properties()
            val inputStream = context.applicationContext.resources.assets.open("manufacturer.txt")
//            val inputStream = context.applicationContext.resources.openRawResource(R.raw.manufacturer)
            manufacturer?.load(inputStream)
        }
    }

    /**
     * 根据MAC地址匹配厂商信息
     */
    fun getManufacture(context: Context, mac: String?): String? {
        if (mac == null || mac.length < 8) return null
        var vendor: String? = null
        try {
            synchronized(this) {
                init(context)

                val key = mac.substring(0, 2) + mac.substring(3, 5) + mac.substring(6, 8)
                vendor = manufacturer?.getProperty(key.toUpperCase())
                vendor = vendor?.let { String(it.toByteArray(charset("ISO8859-1"))) }
                Debug.out("getManufacture: key ---> $key")
                Debug.out("getManufacture: vendor ---> $vendor")
            }
        } catch (e: Exception) {
            Debug.printStackTrace(e)
        }
        return vendor
    }
}