package com.bftv.arpinfo.arp

import com.abooc.util.Debug
import java.io.IOException
import java.net.*

/**
 * 获取设备名
 * @author Junpu
 * @time 2018/9/3 18:12
 */
class DeviceRunable(private var targetIp: String?) : Runnable {

    companion object {
        val NBREQ = byteArrayOf(
                0x82.toByte(), 0x28.toByte(), 0x0.toByte(), 0x0.toByte(), 0x0.toByte(),
                0x1.toByte(), 0x0.toByte(), 0x0.toByte(), 0x0.toByte(), 0x0.toByte(),
                0x0.toByte(), 0x0.toByte(), 0x20.toByte(), 0x43.toByte(), 0x4B.toByte(),
                0x41.toByte(), 0x41.toByte(), 0x41.toByte(), 0x41.toByte(), 0x41.toByte(),
                0x41.toByte(), 0x41.toByte(), 0x41.toByte(), 0x41.toByte(), 0x41.toByte(),
                0x41.toByte(), 0x41.toByte(), 0x41.toByte(), 0x41.toByte(), 0x41.toByte(),
                0x41.toByte(), 0x41.toByte(), 0x41.toByte(), 0x41.toByte(), 0x41.toByte(),
                0x41.toByte(), 0x41.toByte(), 0x41.toByte(), 0x41.toByte(), 0x41.toByte(),
                0x41.toByte(), 0x41.toByte(), 0x41.toByte(), 0x41.toByte(), 0x41.toByte(),
                0x0.toByte(), 0x0.toByte(), 0x21.toByte(), 0x0.toByte(), 0x1.toByte())
        const val NBUDPP: Short = 137
    }

    @Synchronized
    override fun run() {
        if (targetIp.isNullOrEmpty()) return
        var socket: DatagramSocket? = null
        var address: InetAddress? = null
        var packet: DatagramPacket? = null // 单播
        try {
            address = InetAddress.getByName(targetIp)
            packet = DatagramPacket(NBREQ, NBREQ.size, address, NBUDPP.toInt())
            socket = DatagramSocket()
            socket.soTimeout = 500
            socket.send(packet)
            receiver(socket)
            socket.close()
        } catch (se: SocketException) {
            Debug.printStackTrace(se)
        } catch (e: UnknownHostException) {
            Debug.printStackTrace(e)
        } catch (e: IOException) {
            Debug.printStackTrace(e)
        } finally {
            socket?.close()
        }
    }

    private fun receiver(socket: DatagramSocket?) {
        val buffer = ByteArray(1024)
        val dp = DatagramPacket(buffer, buffer.size)
        socket?.receive(dp)
        val hostName = dp.address.hostName
        val content = String(dp.data, 0, dp.length)
        Debug.out("DeviceRunable.receiver: $targetIp ---> $hostName")
        Debug.out("DeviceRunable.receiver: $targetIp ---> $content")
    }

}
