package com.samsung.android.allshare.extension;

import android.content.Context;
import android.content.pm.PackageManager;
import com.samsung.android.allshare.DLog;
import com.samsung.android.allshare.Device;
import com.samsung.android.allshare.media.AVPlayer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class DeviceChecker {
    public static final String AVPLAYER_AUDIO = "AVPLAYER_AUDIO";
    public static final String AVPLAYER_VIDEO = "AVPLAYER_VIDEO";
    private static final String KEY_UDN = "udn";
    private static final String NIC_P2P = "p2p-wlan0-0";
    private static final String NIC_WLAN = "wlan0";
    private static final String PREFERENCE = "AllShareMediaServer";
    private static final String TAG_CLASS = "DeviceChecker";

    public static boolean isMyLocalProvider(Context context, String str) {
        if (str != null && !str.isEmpty()) {
            try {
                Context createPackageContext = context.createPackageContext("com.samsung.android.nearby.mediaserver", 2);
                if (createPackageContext != null) {
                    String string = createPackageContext.getSharedPreferences(PREFERENCE, 5).getString(KEY_UDN, "");
                    if (!string.isEmpty() && str.contains(string.replaceFirst("uuid:", ""))) {
                        return true;
                    }
                }
                return false;
            } catch (PackageManager.NameNotFoundException e) {
                DLog.w_api(TAG_CLASS, "isMyLocalProvider NameNotFoundException", e);
            }
        }
        return false;
    }

    public static ArrayList<Device> getDeviceCheckedList(ArrayList<Device> arrayList) {
        return getDeviceCheckedList(arrayList, null, null);
    }

    public static ArrayList<Device> getDeviceCheckedList(ArrayList<Device> arrayList, String str) {
        return getDeviceCheckedList(arrayList, null, str);
    }

    public static ArrayList<Device> getDeviceCheckedList(ArrayList<Device> arrayList, Context context) {
        return getDeviceCheckedList(arrayList, context, null);
    }

    private static ArrayList<Device> getDeviceCheckedList(ArrayList<Device> arrayList, Context context, String str) {
        DLog.v_api(TAG_CLASS, "getDeviceCheckedList()");
        ArrayList<Device> arrayList2 = new ArrayList<>();
        ArrayList<Device> arrayList3 = new ArrayList<>();
        HashSet hashSet = new HashSet();
        if (arrayList != null) {
            Iterator<Device> it = arrayList.iterator();
            while (it.hasNext()) {
                Device next = it.next();
                if (next.getDeviceDomain() != Device.DeviceDomain.MY_DEVICE) {
                    if (NIC_P2P.equals(next.getNIC())) {
                        String id = next.getID();
                        int indexOf = id.indexOf("+");
                        if (indexOf > 0) {
                            id = id.substring(0, indexOf);
                        }
                        hashSet.add(id);
                        if (str == null || ((next instanceof AVPlayer) && ((AVPLAYER_VIDEO.equals(str) && ((AVPlayer) next).isSupportVideo()) || (AVPLAYER_AUDIO.equals(str) && ((AVPlayer) next).isSupportAudio())))) {
                            arrayList2.add(next);
                        }
                    } else if (str == null || ((next instanceof AVPlayer) && ((AVPLAYER_VIDEO.equals(str) && ((AVPlayer) next).isSupportVideo()) || (AVPLAYER_AUDIO.equals(str) && ((AVPlayer) next).isSupportAudio())))) {
                        arrayList3.add(next);
                    }
                }
            }
            if (arrayList3.size() != 0) {
                if (arrayList2.size() == 0) {
                    return arrayList3;
                }
                Iterator<Device> it2 = arrayList3.iterator();
                while (it2.hasNext()) {
                    Device next2 = it2.next();
                    String id2 = next2.getID();
                    int indexOf2 = id2.indexOf("+");
                    if (indexOf2 > 0) {
                        id2 = id2.substring(0, indexOf2);
                    }
                    if (hashSet.add(id2)) {
                        arrayList2.add(next2);
                    }
                }
                DLog.i_api(TAG_CLASS, "getDeviceCheckedList() with CONCURRENT_MODE count:" + arrayList2.size());
                return arrayList2;
            }
        }
        return arrayList2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x003d, code lost:
    
        r8 = r5[3];
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0046, code lost:
    
        if (r8.matches("..:..:..:..:..:..") == false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0048, code lost:
    
        r8 = r8.trim();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x004c, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x004f, code lost:
    
        return r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0055, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0056, code lost:
    
        com.samsung.android.allshare.DLog.w_api(com.samsung.android.allshare.extension.DeviceChecker.TAG_CLASS, "getMacAddrFromArpTable br.close() IOE", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0059, code lost:
    
        return r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0050, code lost:
    
        r1 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0051, code lost:
    
        com.samsung.android.allshare.DLog.w_api(com.samsung.android.allshare.extension.DeviceChecker.TAG_CLASS, "getMacAddrFromArpTable br.close() E", r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x005a, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x005d, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0063, code lost:
    
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0064, code lost:
    
        com.samsung.android.allshare.DLog.w_api(com.samsung.android.allshare.extension.DeviceChecker.TAG_CLASS, "getMacAddrFromArpTable br.close() IOE", r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0067, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x005e, code lost:
    
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x005f, code lost:
    
        com.samsung.android.allshare.DLog.w_api(com.samsung.android.allshare.extension.DeviceChecker.TAG_CLASS, "getMacAddrFromArpTable br.close() E", r8);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0087 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getMacAddrFromArpTable(java.lang.String r8) {
        /*
            java.lang.String r0 = "getMacAddrFromArpTable br.close() E"
            java.lang.String r1 = "getMacAddrFromArpTable br.close() IOE"
            java.lang.String r2 = "DeviceChecker"
            r3 = 0
            if (r8 != 0) goto La
            return r3
        La:
            java.lang.String r4 = "/"
            java.lang.String r5 = ""
            java.lang.String r8 = r8.replace(r4, r5)
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6c
            java.io.FileReader r5 = new java.io.FileReader     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6c
            java.lang.String r6 = "/proc/net/arp"
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6c
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6c
        L1e:
            java.lang.String r5 = r4.readLine()     // Catch: java.lang.Exception -> L68 java.lang.Throwable -> L83
            if (r5 != 0) goto L28
            r4.close()     // Catch: java.lang.Exception -> L79 java.io.IOException -> L7e
            goto L82
        L28:
            java.lang.String r6 = " +"
            java.lang.String[] r5 = r5.split(r6)     // Catch: java.lang.Exception -> L68 java.lang.Throwable -> L83
            if (r5 == 0) goto L1e
            int r6 = r5.length     // Catch: java.lang.Exception -> L68 java.lang.Throwable -> L83
            r7 = 4
            if (r6 < r7) goto L1e
            r6 = 0
            r6 = r5[r6]     // Catch: java.lang.Exception -> L68 java.lang.Throwable -> L83
            boolean r6 = r8.equals(r6)     // Catch: java.lang.Exception -> L68 java.lang.Throwable -> L83
            if (r6 == 0) goto L1e
            r8 = 3
            r8 = r5[r8]     // Catch: java.lang.Exception -> L68 java.lang.Throwable -> L83
            java.lang.String r5 = "..:..:..:..:..:.."
            boolean r5 = r8.matches(r5)     // Catch: java.lang.Exception -> L68 java.lang.Throwable -> L83
            if (r5 == 0) goto L5a
            java.lang.String r8 = r8.trim()     // Catch: java.lang.Exception -> L68 java.lang.Throwable -> L83
            r4.close()     // Catch: java.lang.Exception -> L50 java.io.IOException -> L55
            return r8
        L50:
            r1 = move-exception
            com.samsung.android.allshare.DLog.w_api(r2, r0, r1)
            goto L59
        L55:
            r0 = move-exception
            com.samsung.android.allshare.DLog.w_api(r2, r1, r0)
        L59:
            return r8
        L5a:
            r4.close()     // Catch: java.lang.Exception -> L5e java.io.IOException -> L63
            return r3
        L5e:
            r8 = move-exception
            com.samsung.android.allshare.DLog.w_api(r2, r0, r8)
            goto L67
        L63:
            r8 = move-exception
            com.samsung.android.allshare.DLog.w_api(r2, r1, r8)
        L67:
            return r3
        L68:
            r8 = move-exception
            goto L6e
        L6a:
            r8 = move-exception
            goto L85
        L6c:
            r8 = move-exception
            r4 = r3
        L6e:
            java.lang.String r5 = "getMacAddrFromArpTable Exception"
            com.samsung.android.allshare.DLog.w_api(r2, r5, r8)     // Catch: java.lang.Throwable -> L83
            if (r4 == 0) goto L82
            r4.close()     // Catch: java.lang.Exception -> L79 java.io.IOException -> L7e
            goto L82
        L79:
            r8 = move-exception
            com.samsung.android.allshare.DLog.w_api(r2, r0, r8)
            goto L82
        L7e:
            r8 = move-exception
            com.samsung.android.allshare.DLog.w_api(r2, r1, r8)
        L82:
            return r3
        L83:
            r8 = move-exception
            r3 = r4
        L85:
            if (r3 == 0) goto L94
            r3.close()     // Catch: java.lang.Exception -> L8b java.io.IOException -> L90
            goto L94
        L8b:
            r1 = move-exception
            com.samsung.android.allshare.DLog.w_api(r2, r0, r1)
            goto L94
        L90:
            r0 = move-exception
            com.samsung.android.allshare.DLog.w_api(r2, r1, r0)
        L94:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.allshare.extension.DeviceChecker.getMacAddrFromArpTable(java.lang.String):java.lang.String");
    }
}
