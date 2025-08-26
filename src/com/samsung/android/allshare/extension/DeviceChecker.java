package com.samsung.android.allshare.extension;

import android.content.Context;
import android.content.pm.PackageManager;
import com.samsung.android.allshare.DLog;
import com.samsung.android.allshare.Device;
import com.samsung.android.allshare.media.AVPlayer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
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
                Context contextCreatePackageContext = context.createPackageContext("com.samsung.android.nearby.mediaserver", 2);
                if (contextCreatePackageContext != null) {
                    String string = contextCreatePackageContext.getSharedPreferences(PREFERENCE, 5).getString(KEY_UDN, "");
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
                        int iIndexOf = id.indexOf("+");
                        if (iIndexOf > 0) {
                            id = id.substring(0, iIndexOf);
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
                    int iIndexOf2 = id2.indexOf("+");
                    if (iIndexOf2 > 0) {
                        id2 = id2.substring(0, iIndexOf2);
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

    /* JADX WARN: Code restructure failed: missing block: B:17:0x003d, code lost:
    
        r8 = r5[3];
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0046, code lost:
    
        if (r8.matches("..:..:..:..:..:..") == false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0048, code lost:
    
        r8 = r8.trim();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x004c, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004f, code lost:
    
        return r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0050, code lost:
    
        r1 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0051, code lost:
    
        com.samsung.android.allshare.DLog.w_api(com.samsung.android.allshare.extension.DeviceChecker.TAG_CLASS, "getMacAddrFromArpTable br.close() E", r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0055, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0056, code lost:
    
        com.samsung.android.allshare.DLog.w_api(com.samsung.android.allshare.extension.DeviceChecker.TAG_CLASS, "getMacAddrFromArpTable br.close() IOE", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0059, code lost:
    
        return r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005a, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x005d, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x005e, code lost:
    
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x005f, code lost:
    
        com.samsung.android.allshare.DLog.w_api(com.samsung.android.allshare.extension.DeviceChecker.TAG_CLASS, "getMacAddrFromArpTable br.close() E", r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0063, code lost:
    
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0064, code lost:
    
        com.samsung.android.allshare.DLog.w_api(com.samsung.android.allshare.extension.DeviceChecker.TAG_CLASS, "getMacAddrFromArpTable br.close() IOE", r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0067, code lost:
    
        return null;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0087 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getMacAddrFromArpTable(String str) throws Throwable {
        BufferedReader bufferedReader;
        LineNumberReader lineNumberReader = 0;
        if (str == null) {
            return null;
        }
        String strReplace = str.replace("/", "");
        try {
            try {
                try {
                    bufferedReader = new BufferedReader(new FileReader("/proc/net/arp"));
                    while (true) {
                        try {
                            String line = bufferedReader.readLine();
                            if (line != null) {
                                String[] strArrSplit = line.split(" +");
                                if (strArrSplit != null && strArrSplit.length >= 4 && strReplace.equals(strArrSplit[0])) {
                                    break;
                                }
                            } else {
                                bufferedReader.close();
                                break;
                            }
                        } catch (Exception e) {
                            e = e;
                            DLog.w_api(TAG_CLASS, "getMacAddrFromArpTable Exception", e);
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return null;
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    lineNumberReader = "/";
                    if (lineNumberReader != 0) {
                        try {
                            lineNumberReader.close();
                        } catch (IOException e2) {
                            DLog.w_api(TAG_CLASS, "getMacAddrFromArpTable br.close() IOE", e2);
                        } catch (Exception e3) {
                            DLog.w_api(TAG_CLASS, "getMacAddrFromArpTable br.close() E", e3);
                        }
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                bufferedReader = null;
            } catch (Throwable th2) {
                th = th2;
                if (lineNumberReader != 0) {
                }
                throw th;
            }
        } catch (IOException e5) {
            DLog.w_api(TAG_CLASS, "getMacAddrFromArpTable br.close() IOE", e5);
        } catch (Exception e6) {
            DLog.w_api(TAG_CLASS, "getMacAddrFromArpTable br.close() E", e6);
        }
    }
}
