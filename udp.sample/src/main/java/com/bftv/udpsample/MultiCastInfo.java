//package com.bftv.udpsample;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//
///**
// * @author Junpu
// * @time 2018/8/27 17:31
// */
//public class MultiCastInfo {
//
//    public int head; // 是FLAG_MULTIHEAD
//    public int ver; // 结构信息版本
//    public int cmd;
//    public byte[] DevName = new byte[20]; // 如果此编码格式为UTF8请将版本号设置为0x10001
//    public byte[] MacAddr = new byte[6];
//    public short netport;
//    public int ipaddr;
//    public int netmask;
//    public int route;
//    public int softver;
//    public int softbuilddate; // 没有用了
//    public short nHttpPort;
//    public byte[] NoUsed = new byte[2];
//    public int deviceType;
//    public int productType;
//    public int kernelVersion;
//    public int inputform;
//    public int devVer; // 设备硬件版本号
//    public byte[] szPasswd = new byte[28]; // 设置IP必须输入正确的admin密码，采用base64编码
//    public byte[] Resved = new byte[28]; // 以后增加功能就利用这些字节
//
//    public static int GetStructSize() {
//        return 140;
//    }
//
//    public byte[] serialize() throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        DataOutputStream dos = new DataOutputStream(baos);
//        MyUtil m_util = new MyUtil();
//
//        head = m_util.ntohl(head);
//        dos.writeInt(head);
//        ver = m_util.ntohl(ver);
//        dos.writeInt(ver);
//        cmd = m_util.ntohl(cmd);
//        dos.writeInt(cmd);
//        dos.write(DevName, 0, DevName.length);
//        dos.write(MacAddr, 0, MacAddr.length);
//        dos.writeShort(netport);
//        ipaddr = m_util.ntohl(ipaddr);
//        dos.writeInt(ipaddr);
//        netmask = m_util.ntohl(netmask);
//        dos.writeInt(netmask);
//        route = m_util.ntohl(route);
//        dos.writeInt(route);
//        softver = m_util.ntohl(softver);
//        dos.writeInt(softver);
//        softbuilddate = m_util.ntohl(softbuilddate);
//        dos.writeInt(softbuilddate);
//        dos.writeShort(nHttpPort);
//        dos.write(NoUsed, 0, NoUsed.length);
//        deviceType = m_util.ntohl(deviceType);
//        dos.writeInt(deviceType);
//        productType = m_util.ntohl(productType);
//        dos.writeInt(productType);
//        kernelVersion = m_util.ntohl(kernelVersion);
//        dos.writeInt(kernelVersion);
//        inputform = m_util.ntohl(inputform);
//        dos.writeInt(inputform);
//        devVer = m_util.ntohl(devVer);
//        dos.writeInt(devVer);
//        dos.write(szPasswd, 0, szPasswd.length);
//        dos.write(Resved, 0, Resved.length);
//
//        baos.close();
//        dos.close();
//        return baos.toByteArray();
//    }
//
//    public static MultiCastInfo deserialize(byte[] data, int offset) throws IOException {
//
//        ByteArrayInputStream bais = new ByteArrayInputStream(data);
//        DataInputStream dis = new DataInputStream(bais);
//        MyUtil m_util = new MyUtil();
//        MultiCastInfo info = new MultiCastInfo();
//
//        dis.read(data, 0, offset);
//
//        byte[] testbyte = new byte[28];
//        dis.read(testbyte, 0, 4);
//        info.head = m_util.bytes2int(testbyte);
//
//        dis.read(testbyte, 0, 4);
//        info.ver = m_util.bytes2int(testbyte);
//
//        dis.read(testbyte, 0, 4);
//        info.cmd = m_util.bytes2int(testbyte);
//
//        dis.read(info.DevName, 0, 20);
//        dis.read(info.MacAddr, 0, 6);
//
//        dis.read(testbyte, 0, 2);
//        info.netport = m_util.bytes2short(testbyte);
//
//        dis.read(testbyte, 0, 4);
//        info.ipaddr = m_util.bytes2int(testbyte);
//
//        dis.read(testbyte, 0, 4);
//        info.netmask = m_util.bytes2int(testbyte);
//
//        dis.read(testbyte, 0, 4);
//        info.route = m_util.bytes2int(testbyte);
//
//        dis.read(testbyte, 0, 4);
//        info.softver = m_util.bytes2int(testbyte);
//
//        dis.read(testbyte, 0, 4);
//        info.softbuilddate = m_util.bytes2int(testbyte);
//
//        dis.read(testbyte, 0, 2);
//        info.nHttpPort = m_util.bytes2short(testbyte);
//
//        dis.read(info.NoUsed, 0, info.NoUsed.length);
//
//        dis.read(testbyte, 0, 4);
//        info.deviceType = m_util.bytes2int(testbyte);
//
//        dis.read(testbyte, 0, 4);
//        info.productType = m_util.bytes2int(testbyte);
//
//        dis.read(testbyte, 0, 4);
//        info.kernelVersion = m_util.bytes2int(testbyte);
//
//        dis.read(testbyte, 0, 4);
//        info.inputform = m_util.bytes2int(testbyte);
//
//        dis.read(testbyte, 0, 4);
//        info.devVer = m_util.bytes2int(testbyte);
//
//        dis.read(info.szPasswd, 0, info.szPasswd.length);
//        dis.read(info.Resved, 0, info.Resved.length);
//
//        bais.close();
//        dis.close();
//
//        return info;
//    }
//
//}
