//package com.bftv.udpapp
//
//import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
//import com.abooc.util.Debug
//import com.bftv.udpapp.DeviceSearcher.DeviceBean
//import kotlinx.android.synthetic.main.activity_main.*
//import java.net.InetSocketAddress
//
///**
// *
// * @author Junpu
// * @time 2018/8/28 14:56
// */
//class MainActivity : AppCompatActivity() {
//
//    private val mDeviceList = mutableListOf<DeviceBean>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        btnSearch.setOnClickListener {
//
//        }
//
//    }
//
//    /**
//     * 主机——demo核心代码：
//     */
//    private fun searchDevices_broadcast() {
//        object : DeviceSearcher() {
//            override fun onSearchStart() {
////                startSearch() // 主要用于在UI上展示正在搜索
//            }
//
//            override fun onSearchFinish(deviceSet: Set<*>) {
//                Debug.out("onSearchFinish: deviceSet ---> $deviceSet")
////                endSearch() // 结束UI上的正在搜索
//                mDeviceList.clear()
////                mDeviceList.addAll(deviceSet)
////                mHandler.sendEmptyMessage(0) // 在UI上更新设备列表
//            }
//        }.start()
//    }
//
//    /**
//     * 设备——demo核心代码：
//     */
//    private fun initData() {
//        object : DeviceWaitingSearch(this, "日灯光", "客厅") {
//            fun onDeviceSearched(socketAddr: InetSocketAddress) {
//                pushMsgToMain("已上线，搜索主机：" + socketAddr.getAddress().getHostAddress() + ":" + socketAddr.getPort())
//            }
//        }.start()
//    }
//}