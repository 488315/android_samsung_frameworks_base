package com.samsung.android.allshare.extension;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.p002pm.PackageManager;
import com.samsung.android.allshare.DLog;
import com.samsung.android.allshare.Device;
import com.samsung.android.allshare.media.AVPlayer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class DeviceChecker {
    public static final String AVPLAYER_AUDIO = "AVPLAYER_AUDIO";
    public static final String AVPLAYER_VIDEO = "AVPLAYER_VIDEO";
    private static final String KEY_UDN = "udn";
    private static final String NIC_P2P = "p2p-wlan0-0";
    private static final String NIC_WLAN = "wlan0";
    private static final String PREFERENCE = "AllShareMediaServer";
    private static final String TAG_CLASS = "DeviceChecker";

    public static boolean isMyLocalProvider(Context context, String deviceId) {
        if (deviceId == null || deviceId.isEmpty()) {
            return false;
        }
        try {
            Context server = context.createPackageContext("com.samsung.android.nearby.mediaserver", 2);
            if (server != null) {
                SharedPreferences preference = server.getSharedPreferences(PREFERENCE, 5);
                String udn = preference.getString(KEY_UDN, "");
                if (!udn.isEmpty() && deviceId.contains(udn.replaceFirst("uuid:", ""))) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            DLog.w_api(TAG_CLASS, "isMyLocalProvider NameNotFoundException", e);
            return false;
        }
    }

    public static ArrayList<Device> getDeviceCheckedList(ArrayList<Device> deviceList) {
        return getDeviceCheckedList(deviceList, null, null);
    }

    public static ArrayList<Device> getDeviceCheckedList(ArrayList<Device> deviceList, String avPlayerType) {
        return getDeviceCheckedList(deviceList, null, avPlayerType);
    }

    public static ArrayList<Device> getDeviceCheckedList(ArrayList<Device> deviceList, Context context) {
        return getDeviceCheckedList(deviceList, context, null);
    }

    private static ArrayList<Device> getDeviceCheckedList(ArrayList<Device> deviceList, Context context, String avPlayerType) {
        DLog.v_api(TAG_CLASS, "getDeviceCheckedList()");
        ArrayList<Device> adaptiveList = new ArrayList<>();
        ArrayList<Device> wlanDeviceList = new ArrayList<>();
        HashSet<String> deviceUDN = new HashSet<>();
        if (deviceList == null) {
            return adaptiveList;
        }
        Iterator<Device> it = deviceList.iterator();
        while (it.hasNext()) {
            Device device = it.next();
            if (device.getDeviceDomain() != Device.DeviceDomain.MY_DEVICE) {
                if (NIC_P2P.equals(device.getNIC())) {
                    String id = device.getID();
                    int point = id.indexOf("+");
                    if (point > 0) {
                        id = id.substring(0, point);
                    }
                    deviceUDN.add(id);
                    if (avPlayerType == null || ((device instanceof AVPlayer) && ((AVPLAYER_VIDEO.equals(avPlayerType) && ((AVPlayer) device).isSupportVideo()) || (AVPLAYER_AUDIO.equals(avPlayerType) && ((AVPlayer) device).isSupportAudio())))) {
                        adaptiveList.add(device);
                    }
                } else if (avPlayerType == null || ((device instanceof AVPlayer) && ((AVPLAYER_VIDEO.equals(avPlayerType) && ((AVPlayer) device).isSupportVideo()) || (AVPLAYER_AUDIO.equals(avPlayerType) && ((AVPlayer) device).isSupportAudio())))) {
                    wlanDeviceList.add(device);
                }
            }
        }
        if (wlanDeviceList.size() == 0) {
            return adaptiveList;
        }
        if (adaptiveList.size() == 0) {
            return wlanDeviceList;
        }
        Iterator<Device> it2 = wlanDeviceList.iterator();
        while (it2.hasNext()) {
            Device device2 = it2.next();
            String id2 = device2.getID();
            int point2 = id2.indexOf("+");
            if (point2 > 0) {
                id2 = id2.substring(0, point2);
            }
            if (deviceUDN.add(id2)) {
                adaptiveList.add(device2);
            }
        }
        DLog.i_api(TAG_CLASS, "getDeviceCheckedList() with CONCURRENT_MODE count:" + adaptiveList.size());
        return adaptiveList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x004f, code lost:
    
        r7 = r6[3];
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0058, code lost:
    
        if (r7.matches("..:..:..:..:..:..") == false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x005a, code lost:
    
        r3 = r7.trim();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0060, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0069, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x006a, code lost:
    
        com.samsung.android.allshare.DLog.w_api(com.samsung.android.allshare.extension.DeviceChecker.TAG_CLASS, "getMacAddrFromArpTable br.close() IOE", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0064, code lost:
    
        r1 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0065, code lost:
    
        com.samsung.android.allshare.DLog.w_api(com.samsung.android.allshare.extension.DeviceChecker.TAG_CLASS, "getMacAddrFromArpTable br.close() E", r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0071, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x007a, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x007b, code lost:
    
        com.samsung.android.allshare.DLog.w_api(com.samsung.android.allshare.extension.DeviceChecker.TAG_CLASS, "getMacAddrFromArpTable br.close() IOE", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0075, code lost:
    
        r1 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0076, code lost:
    
        com.samsung.android.allshare.DLog.w_api(com.samsung.android.allshare.extension.DeviceChecker.TAG_CLASS, "getMacAddrFromArpTable br.close() E", r1);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getMacAddrFromArpTable(String ipAddr) {
        String mac;
        if (ipAddr == null) {
            return null;
        }
        BufferedReader br = null;
        String ipAddr2 = ipAddr.replace("/", "");
        try {
            try {
                try {
                    br = new BufferedReader(new FileReader("/proc/net/arp"));
                    while (true) {
                        String line = br.readLine();
                        if (line != null) {
                            String[] splitted = line.split(" +");
                            if (splitted != null && splitted.length >= 4 && ipAddr2.equals(splitted[0])) {
                                break;
                            }
                        } else {
                            br.close();
                            break;
                        }
                    }
                } catch (Exception e) {
                    DLog.w_api(TAG_CLASS, "getMacAddrFromArpTable Exception", e);
                    if (br != null) {
                        br.close();
                    }
                    return null;
                }
            } catch (Throwable th) {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e2) {
                        DLog.w_api(TAG_CLASS, "getMacAddrFromArpTable br.close() IOE", e2);
                    } catch (Exception e3) {
                        DLog.w_api(TAG_CLASS, "getMacAddrFromArpTable br.close() E", e3);
                    }
                }
                throw th;
            }
        } catch (IOException e4) {
            DLog.w_api(TAG_CLASS, "getMacAddrFromArpTable br.close() IOE", e4);
        } catch (Exception e5) {
            DLog.w_api(TAG_CLASS, "getMacAddrFromArpTable br.close() E", e5);
        }
        return mac;
        return null;
    }
}
