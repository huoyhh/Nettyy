package com.zk;

import java.net.InetAddress;

/**
 * @author CBeann
 * @create 2020-03-07 14:17
 */
public class IPAddress {
    public static void main(String[] args) throws Exception {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String ip = inetAddress.getHostAddress();//获得本机Ip
        System.out.println(ip);
    }
}
