package com.untils;

import java.net.InetAddress;

/**
 * @author CBeann
 * @create 2020-03-07 14:26
 */
public class IPAddressUntils {

    public static String address(){
        String ip = "";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            ip = inetAddress.getHostAddress();//获得本机Ip
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ip;
    }

}
