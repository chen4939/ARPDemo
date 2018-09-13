package com.bftv.arpinfo.arp

import android.content.Context
import android.os.Build
import com.abooc.util.Debug
import com.baofeng.utils.NetworkUtils
import com.bftv.arpinfo.isNotNullOrEmpty
import com.bftv.arpinfo.model.Device
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import java.io.BufferedReader
import java.io.FileReader
import java.net.InetAddress
import java.util.*

/**
 * ARP管理
 * @author Junpu
 * @time 2018/9/4 11:09
 */
class ArpManager(private val context: Context, private var callback: ArpCallback? = null) {

    private val arpList = arrayListOf<Device>()

    /**
     * 获取本机数据
     */
    fun getLocalInfo(): Device? {
        val ip = NetworkUtils.getLocalIpAddress()
        val mac = NetworkUtils.getMacAddress(context)
        val model = Build.MODEL
        val manufacturer = Build.MANUFACTURER
        return Device(ip, mac, model, manufacturer)
    }

    /**
     * 生成网段内的所有IP
     */
    private fun buildIps(ip: String?): List<String> {
        val list = arrayListOf<String>()
        if (ip.isNotNullOrEmpty()) {
            val ipSeg = ip?.substring(0, ip.lastIndexOf("") + 1)
            for (i in 1 until 255) {
                val newIp = "$ipSeg$i"
                if (newIp == ip) continue
                list.add(newIp)
            }
        }
        return list
    }

    /**
     * Arp扫描
     */
    fun discover() {
        val ip = NetworkUtils.getLocalIpAddress()
        discover(ip)
    }

    /**
     * Arp扫描
     */
    fun discover(ip: String?) {
        val ips = buildIps(ip)
        ips.forEach { Thread(DiscoverRunnable(it)).start() }
    }

    /**
     * 读取ARP文件
     */
    fun readArp(): List<Device> {
        arpList.clear()
        var br: BufferedReader? = null
        try {
            br = BufferedReader(FileReader("/proc/net/arp"))
            var line: String
            while (true) {
                line = br.readLine() ?: break
                line = line.trim()
                if (line.length < 63) continue
                if (line.toUpperCase(Locale.US).contains("IP")) continue
                val ip = line.substring(0, 17).trim()
                val mac = line.substring(41, 63).trim()
                if (mac.contains("00:00:00:00:00:00")) continue
                println("$ip ---> $mac")
                val manufacturer = Manufacturer.instance.getManufacture(context, mac)
                val device = Device(ip, mac, null, manufacturer)
                getDeviceName(device)
                arpList.add(device)
            }
            br.close()
        } catch (e: Exception) {
            Debug.printStackTrace(e)
        } finally {
            br?.close()
        }
        return arpList
    }

    /**
     * 获取设备名称
     */
    private fun getDeviceName(device: Device) {
        doAsync {
            val address = InetAddress.getByName(device.ip)
            val hostName = address.hostName
            Debug.out("readArp: hostName ---> $hostName")
            device.name = hostName
            context.runOnUiThread { callback?.callback(device) }
        }
    }

}