package com.bftv.udpapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bftv.udpapp.DeviceSearcher.DeviceBean;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Set;

/**
 * @author Junpu
 * @time 2018/8/28 15:11
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private List<DeviceBean> mDeviceList;

    /**
     * 主机——demo核心代码：
     */
    private void searchDevices_broadcast() {
        new DeviceSearcher() {
            @Override
            public void onSearchStart() {
//                startSearch(); // 主要用于在UI上展示正在搜索
            }

            @Override
            public void onSearchFinish(Set deviceSet) {
//                endSearch(); // 结束UI上的正在搜索
                mDeviceList.clear();
                mDeviceList.addAll(deviceSet);
//                mHandler.sendEmptyMessage(0); // 在UI上更新设备列表
            }
        }.start();
    }


    /**
     * 设备——demo核心代码：
     */
    private void initData() {
        new DeviceWaitingSearch(this, "日灯光", "客厅") {
            @Override
            public void onDeviceSearched(InetSocketAddress socketAddr) {
//                pushMsgToMain("已上线，搜索主机：" + socketAddr.getAddress().getHostAddress() + ":" + socketAddr.getPort());
            }
        }.start();
    }
}
