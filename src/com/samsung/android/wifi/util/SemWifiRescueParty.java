package com.samsung.android.wifi.util;

import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class SemWifiRescueParty {
    private static final String APEX_WIFI_DATA_PATH = "apexdata/com.android.wifi";
    private static final String MISC_PATH = "/data/misc/";
    private static final String MISC_USER_PATH = "/data/misc_ce/";
    private static final String SYSTEM_DB_PATH = "/data/system/";
    private static final String TAG = "SemWifiRescueParty";

    /* JADX WARN: Type inference failed for: r0v1, types: [boolean, int] */
    public static int resetAllWifiStoredData(int[] iArr) {
        ?? removeFile = removeFile("/data/system/wifigeofence.db");
        int i = removeFile;
        if (removeFile("/data/system/wifigeofence.db-journal")) {
            i = removeFile + 1;
        }
        int i2 = i;
        if (removeFile("/data/system/WifiHistory.db")) {
            i2 = i + 1;
        }
        int i3 = i2;
        if (removeFile("/data/system/WifiHistory.db-journal")) {
            i3 = i2 + 1;
        }
        int i4 = i3;
        if (removeFile("/data/system/WifiConfigStore.db")) {
            i4 = i3 + 1;
        }
        int i5 = i4;
        if (removeFile("/data/system/WifiConfigStore.db-journal")) {
            i5 = i4 + 1;
        }
        int removeFiles = i5 + removeFiles("/data/misc/wifi") + removeFiles("/data/misc/apexdata/com.android.wifi");
        for (int i6 : iArr) {
            removeFiles = removeFiles + removeFiles(MISC_USER_PATH + i6 + "/wifi") + removeFiles(MISC_USER_PATH + i6 + "/apexdata/com.android.wifi");
        }
        Log.e(TAG, "reset all Wi-Fi stored files: " + removeFiles);
        return removeFiles;
    }

    private static boolean removeFile(String str) {
        try {
            return new File(str).delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static int removeFiles(String str) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        fetchCompleteList(arrayList, arrayList2, str);
        Iterator it = arrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (removeFile((String) it.next())) {
                i++;
            }
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            if (removeFile((String) it2.next())) {
                i++;
            }
        }
        return i;
    }

    private static void fetchCompleteList(List<String> list, List<String> list2, String str) {
        File[] listFiles = new File(str).listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    list2.add(file.getAbsolutePath());
                    fetchCompleteList(list, list2, file.getAbsolutePath());
                } else {
                    list.add(file.getAbsolutePath());
                }
            }
        }
    }
}
