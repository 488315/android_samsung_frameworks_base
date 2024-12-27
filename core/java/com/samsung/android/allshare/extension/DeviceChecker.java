package com.samsung.android.allshare.extension;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import com.samsung.android.allshare.DLog;
import com.samsung.android.allshare.Device;
import com.samsung.android.allshare.media.AVPlayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes3.dex */
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
            Context server =
                    context.createPackageContext("com.samsung.android.nearby.mediaserver", 2);
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

    public static ArrayList<Device> getDeviceCheckedList(
            ArrayList<Device> deviceList, String avPlayerType) {
        return getDeviceCheckedList(deviceList, null, avPlayerType);
    }

    public static ArrayList<Device> getDeviceCheckedList(
            ArrayList<Device> deviceList, Context context) {
        return getDeviceCheckedList(deviceList, context, null);
    }

    private static ArrayList<Device> getDeviceCheckedList(
            ArrayList<Device> deviceList, Context context, String avPlayerType) {
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
                    if (avPlayerType == null
                            || ((device instanceof AVPlayer)
                                    && ((AVPLAYER_VIDEO.equals(avPlayerType)
                                                    && ((AVPlayer) device).isSupportVideo())
                                            || (AVPLAYER_AUDIO.equals(avPlayerType)
                                                    && ((AVPlayer) device).isSupportAudio())))) {
                        adaptiveList.add(device);
                    }
                } else if (avPlayerType == null
                        || ((device instanceof AVPlayer)
                                && ((AVPLAYER_VIDEO.equals(avPlayerType)
                                                && ((AVPlayer) device).isSupportVideo())
                                        || (AVPLAYER_AUDIO.equals(avPlayerType)
                                                && ((AVPlayer) device).isSupportAudio())))) {
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
        DLog.i_api(
                TAG_CLASS,
                "getDeviceCheckedList() with CONCURRENT_MODE count:" + adaptiveList.size());
        return adaptiveList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x004d, code lost:

       r7 = r6[3];
    */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0056, code lost:

       if (r7.matches("..:..:..:..:..:..") == false) goto L67;
    */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0058, code lost:

       r3 = r7.trim();
    */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x005e, code lost:

       r4.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0067, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0068, code lost:

       com.samsung.android.allshare.DLog.w_api(com.samsung.android.allshare.extension.DeviceChecker.TAG_CLASS, "getMacAddrFromArpTable br.close() IOE", r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0062, code lost:

       r1 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0063, code lost:

       com.samsung.android.allshare.DLog.w_api(com.samsung.android.allshare.extension.DeviceChecker.TAG_CLASS, "getMacAddrFromArpTable br.close() E", r1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x006f, code lost:

       r4.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0078, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0079, code lost:

       com.samsung.android.allshare.DLog.w_api(com.samsung.android.allshare.extension.DeviceChecker.TAG_CLASS, "getMacAddrFromArpTable br.close() IOE", r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0073, code lost:

       r1 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0074, code lost:

       com.samsung.android.allshare.DLog.w_api(com.samsung.android.allshare.extension.DeviceChecker.TAG_CLASS, "getMacAddrFromArpTable br.close() E", r1);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getMacAddrFromArpTable(java.lang.String r9) {
        /*
            java.lang.String r0 = "getMacAddrFromArpTable br.close() E"
            java.lang.String r1 = "getMacAddrFromArpTable br.close() IOE"
            java.lang.String r2 = "DeviceChecker"
            r3 = 0
            if (r9 != 0) goto La
            return r3
        La:
            r4 = 0
            java.lang.String r5 = "/"
            java.lang.String r6 = ""
            java.lang.String r9 = r9.replace(r5, r6)
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L81
            java.io.FileReader r6 = new java.io.FileReader     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L81
            java.lang.String r7 = "/proc/net/arp"
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L81
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L81
            r4 = r5
            r5 = 0
        L21:
            java.lang.String r6 = r4.readLine()     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L81
            r5 = r6
            if (r5 != 0) goto L38
        L2a:
            r4.close()     // Catch: java.lang.Exception -> L2e java.io.IOException -> L33
        L2d:
            goto L8d
        L2e:
            r1 = move-exception
            com.samsung.android.allshare.DLog.w_api(r2, r0, r1)
            goto L2d
        L33:
            r0 = move-exception
            com.samsung.android.allshare.DLog.w_api(r2, r1, r0)
            goto L2d
        L38:
            java.lang.String r6 = " +"
            java.lang.String[] r6 = r5.split(r6)     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L81
            if (r6 == 0) goto L7e
            int r7 = r6.length     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L81
            r8 = 4
            if (r7 < r8) goto L7e
            r7 = 0
            r7 = r6[r7]     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L81
            boolean r7 = r9.equals(r7)     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L81
            if (r7 == 0) goto L7e
            r7 = 3
            r7 = r6[r7]     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L81
            java.lang.String r8 = "..:..:..:..:..:.."
            boolean r8 = r7.matches(r8)     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L81
            if (r8 == 0) goto L6d
            java.lang.String r3 = r7.trim()     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L81
            r4.close()     // Catch: java.lang.Exception -> L62 java.io.IOException -> L67
        L61:
            goto L6c
        L62:
            r1 = move-exception
            com.samsung.android.allshare.DLog.w_api(r2, r0, r1)
            goto L6c
        L67:
            r0 = move-exception
            com.samsung.android.allshare.DLog.w_api(r2, r1, r0)
            goto L61
        L6c:
            return r3
        L6d:
            r4.close()     // Catch: java.lang.Exception -> L73 java.io.IOException -> L78
        L72:
            goto L7d
        L73:
            r1 = move-exception
            com.samsung.android.allshare.DLog.w_api(r2, r0, r1)
            goto L7d
        L78:
            r0 = move-exception
            com.samsung.android.allshare.DLog.w_api(r2, r1, r0)
            goto L72
        L7d:
            return r3
        L7e:
            goto L21
        L7f:
            r3 = move-exception
            goto L8e
        L81:
            r5 = move-exception
            java.lang.String r6 = "getMacAddrFromArpTable Exception"
            com.samsung.android.allshare.DLog.w_api(r2, r6, r5)     // Catch: java.lang.Throwable -> L7f
            if (r4 == 0) goto L8d
            r4.close()     // Catch: java.lang.Exception -> L2e java.io.IOException -> L33
            goto L2d
        L8d:
            return r3
        L8e:
            if (r4 == 0) goto L9e
            r4.close()     // Catch: java.lang.Exception -> L94 java.io.IOException -> L99
        L93:
            goto L9e
        L94:
            r1 = move-exception
            com.samsung.android.allshare.DLog.w_api(r2, r0, r1)
            goto L9e
        L99:
            r0 = move-exception
            com.samsung.android.allshare.DLog.w_api(r2, r1, r0)
            goto L93
        L9e:
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.allshare.extension.DeviceChecker.getMacAddrFromArpTable(java.lang.String):java.lang.String");
    }
}
