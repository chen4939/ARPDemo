package com.bftv.udpsample;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Junpu
 * @time 2018/8/27 17:29
 */
public class ServerLocal {
    public static final int FLAG_MULTIHEAD = mmioFOURCC1('M', 'H', 'E', 'D');
    public static final int MULTICAST_VER = 0x10001;
    public static final int MULTICAST_CMD_SEARCH = 1; // 由搜索器发出的搜索指令
    public static String TAG = "ServerLocal";
    private InetAddress m_BroadcastAddr = null;
    private Thread thread;
    private boolean m_bBroadcastStarted = false;
    private byte[] m_BroadcastBuffer = new byte[1024];

    private static String LocalAddress = "234.55.55.55";
    private static int LocalPort = 23456;
    private MulticastSocket m_MulticastSocket;

    private Timer m_PlayTimer;
    private TimerTask m_PlayTimerTask;

    static int mmioFOURCC1(char ch0, char ch1, char ch2, char ch3) {
        return ((int) (byte) ch0 | (int) (((byte) ch1) << 8)) | (int) (((byte) ch2) << 16) | ((int) (((byte) ch3) << 24));
    }

    public ServerLocal() {
    }

    /* 开启连接，加入局域网 */
    public void ConnectDevice() {
        try {
            m_BroadcastAddr = InetAddress.getByName(LocalAddress);
            m_MulticastSocket = new MulticastSocket(LocalPort);
            m_MulticastSocket.joinGroup(m_BroadcastAddr);
            m_MulticastSocket.setTimeToLive(255);
            m_MulticastSocket.setBroadcast(true);
            startReceiveThread();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* 打开接收和搜索线程 */
    public void startReceiveThread() {
        m_PlayTimer = new Timer();
        m_PlayTimerTask = new TimerTask() {
            public void run() {
                SerachDevice();
            }
        };
        m_PlayTimer.schedule(m_PlayTimerTask, 0, 10000);// 十秒搜索一次
        if (thread == null) {
            thread = new Thread(null, doBackgroundThreadProcessing, "SocketThread");
            thread.start();
        }
    }

    public Runnable doBackgroundThreadProcessing = new Runnable() {
        public void run() {
            backgroundThreadProcessing();
        }
    };

    public void backgroundThreadProcessing() {
        m_bBroadcastStarted = true;
        while (m_bBroadcastStarted) {
            DatagramPacket packet = new DatagramPacket(m_BroadcastBuffer, m_BroadcastBuffer.length);
            try {
                if (m_MulticastSocket == null) {
                    continue;
                }
                m_MulticastSocket.receive(packet);
                byte[] data = packet.getData();
                MultiCastInfo multicastInfo = MultiCastInfo.deserialize(data, 0);
                if (multicastInfo.ipaddr == 0) {
                    continue;

                }
                //处理接收的数据
            } catch (IOException e) {
                // e.printStackTrace();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopSearchDevice() {
        m_bBroadcastStarted = false;

        if (m_PlayTimer != null) {
            m_PlayTimer.cancel();
        }
        if (m_PlayTimerTask != null) {
            m_PlayTimerTask.cancel();
        }

        if (m_MulticastSocket != null) {
            m_MulticastSocket.close();
            m_MulticastSocket = null;
        }
//        if (m_DeviceInfo != null) {
//            m_DeviceInfo.clear();
//            m_DeviceInfo = null;
//        }
    }

    public void SerachDevice() {
        MultiCastInfo info = new MultiCastInfo();
        info.head = FLAG_MULTIHEAD;
        info.cmd = MULTICAST_CMD_SEARCH;
        info.ver = MULTICAST_VER;

        byte[] cmdBuff = new byte[MultiCastInfo.GetStructSize()];

        try {
            DatagramPacket dataPacket = new DatagramPacket(cmdBuff, cmdBuff.length, m_BroadcastAddr, 23456);
            dataPacket.setData(info.serialize());
            m_MulticastSocket.send(dataPacket);
        } catch (Exception e) {
        }
    }

    // 将int类型转换为二进制字符串
    public String IntToBinaryString(int iValue) {
        StringBuffer sb = new StringBuffer("");

        sb.append(String.valueOf(iValue & 0x000000FF));
        sb.append(".");
        sb.append(String.valueOf((iValue & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf((iValue & 0x00FFFFFF) >>> 16)); // 将高8位置0，然后右移16位
        sb.append(".");
        sb.append(String.valueOf(iValue >>> 24));// 直接右移24位

        return sb.toString();
    }

}