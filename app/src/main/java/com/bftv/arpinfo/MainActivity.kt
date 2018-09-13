package com.bftv.arpinfo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.baofeng.utils.NetworkUtils
import com.bftv.arpinfo.arp.ArpCallback
import com.bftv.arpinfo.arp.ArpManager
import com.bftv.arpinfo.model.Device
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var adapter: DeviceAdapter? = null
    private var arpManager: ArpManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView?.layoutManager = LinearLayoutManager(this)
        adapter = DeviceAdapter(this)
        recyclerView?.adapter = adapter

        arpManager = ArpManager(this, arpCallback)
        scan()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.menu_refresh -> scan()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setLocalInfo() {
        val localDevice = arpManager?.getLocalInfo()
        val wifiName = NetworkUtils.getWifiName(this)
        val name = localDevice?.name
        val manufacturer = localDevice?.manufacturer
        val ip = localDevice?.ip
        val mac = localDevice?.mac
        localInfo?.text = "WIFI:$wifiName\n$name\n$manufacturer\n$ip\n$mac"
    }

    private fun scan() {
        adapter?.clear()
        setLocalInfo()
        arpManager?.discover()
        arpManager?.readArp()
    }

    private val arpCallback = object : ArpCallback {
        override fun callback(device: Device) {
            adapter?.add(device)
        }
    }

}
