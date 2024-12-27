package com.android.server.sepunion.cover;

import android.content.Context;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.samsung.android.cover.CoverState;
import com.samsung.android.os.SemDvfsManager;
import com.samsung.android.sepunion.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CoverManagerUtils {
    public static final boolean isSupportWirelessCharge;
    public static SemDvfsManager sCoverBusBooster;
    public static SemDvfsManager sCoverCoreNumLockHelper;
    public static SemDvfsManager sCoverCpuBooster;

    static {
        String valueFromSysFS = getValueFromSysFS("/sys/class/power_supply/wireless/present", "0");
        boolean z = false;
        if (valueFromSysFS == null) {
            Log.d("CoverManager_CoverManagerUtils", "Feature for Wireless Charge is NOT existed");
        } else if ("0".equals(valueFromSysFS.trim())) {
            Log.d("CoverManager_CoverManagerUtils", "Wireless Charge is NOT Supported");
        } else {
            Log.d("CoverManager_CoverManagerUtils", "Wireless Charge is Supported: Type ".concat(valueFromSysFS));
            z = true;
        }
        isSupportWirelessCharge = z;
    }

    public static void fileWriteInt(int i) {
        Log.d("CoverManager_CoverManagerUtils", "fileWriteInt to /sys/class/power_supply/battery/led_cover, " + i);
        if (i != 0 && i != 1) {
            Log.e("CoverManager_CoverManagerUtils", "Invalid value : " + i);
            return;
        }
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(new File("/sys/class/power_supply/battery/led_cover"));
                try {
                    fileOutputStream2.write(Integer.toString(i).getBytes());
                    fileOutputStream2.close();
                } catch (IOException e) {
                    e = e;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    try {
                        fileOutputStream.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e3) {
                e3.printStackTrace();
            }
        } catch (IOException e4) {
            e = e4;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x003b, code lost:
    
        if (r0 == null) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0037, code lost:
    
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0035, code lost:
    
        if (r0 == null) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getValueFromSysFS(int r4, java.lang.String r5) {
        /*
            boolean r0 = isFileExists(r5)
            if (r0 == 0) goto L3e
            r0 = 0
            java.io.FileReader r1 = new java.io.FileReader     // Catch: java.lang.Throwable -> L2e java.lang.NumberFormatException -> L35 java.io.IOException -> L3b
            r1.<init>(r5)     // Catch: java.lang.Throwable -> L2e java.lang.NumberFormatException -> L35 java.io.IOException -> L3b
            r5 = 15
            char[] r5 = new char[r5]     // Catch: java.lang.Throwable -> L23 java.lang.NumberFormatException -> L26 java.io.IOException -> L28
            int r0 = r1.read(r5)     // Catch: java.lang.Throwable -> L23 java.lang.NumberFormatException -> L26 java.io.IOException -> L28
            if (r0 <= 0) goto L2a
            java.lang.String r2 = new java.lang.String     // Catch: java.lang.Throwable -> L23 java.lang.NumberFormatException -> L26 java.io.IOException -> L28
            int r0 = r0 + (-1)
            r3 = 0
            r2.<init>(r5, r3, r0)     // Catch: java.lang.Throwable -> L23 java.lang.NumberFormatException -> L26 java.io.IOException -> L28
            int r4 = java.lang.Integer.parseInt(r2)     // Catch: java.lang.Throwable -> L23 java.lang.NumberFormatException -> L26 java.io.IOException -> L28
            goto L2a
        L23:
            r4 = move-exception
            r0 = r1
            goto L2f
        L26:
            r0 = r1
            goto L35
        L28:
            r0 = r1
            goto L3b
        L2a:
            r1.close()     // Catch: java.io.IOException -> L3e
            goto L3e
        L2e:
            r4 = move-exception
        L2f:
            if (r0 == 0) goto L34
            r0.close()     // Catch: java.io.IOException -> L34
        L34:
            throw r4
        L35:
            if (r0 == 0) goto L3e
        L37:
            r0.close()     // Catch: java.io.IOException -> L3e
            goto L3e
        L3b:
            if (r0 == 0) goto L3e
            goto L37
        L3e:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.sepunion.cover.CoverManagerUtils.getValueFromSysFS(int, java.lang.String):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0038, code lost:
    
        if (r0 == null) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0034, code lost:
    
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0032, code lost:
    
        if (r0 == null) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getValueFromSysFS(java.lang.String r4, java.lang.String r5) {
        /*
            boolean r0 = isFileExists(r4)
            if (r0 == 0) goto L3b
            r0 = 0
            java.io.FileReader r1 = new java.io.FileReader     // Catch: java.lang.Throwable -> L2b java.lang.NumberFormatException -> L32 java.io.IOException -> L38
            r1.<init>(r4)     // Catch: java.lang.Throwable -> L2b java.lang.NumberFormatException -> L32 java.io.IOException -> L38
            r4 = 15
            char[] r4 = new char[r4]     // Catch: java.lang.Throwable -> L20 java.lang.NumberFormatException -> L23 java.io.IOException -> L25
            int r0 = r1.read(r4)     // Catch: java.lang.Throwable -> L20 java.lang.NumberFormatException -> L23 java.io.IOException -> L25
            if (r0 <= 0) goto L27
            java.lang.String r2 = new java.lang.String     // Catch: java.lang.Throwable -> L20 java.lang.NumberFormatException -> L23 java.io.IOException -> L25
            int r0 = r0 + (-1)
            r3 = 0
            r2.<init>(r4, r3, r0)     // Catch: java.lang.Throwable -> L20 java.lang.NumberFormatException -> L23 java.io.IOException -> L25
            r5 = r2
            goto L27
        L20:
            r4 = move-exception
            r0 = r1
            goto L2c
        L23:
            r0 = r1
            goto L32
        L25:
            r0 = r1
            goto L38
        L27:
            r1.close()     // Catch: java.io.IOException -> L3b
            goto L3b
        L2b:
            r4 = move-exception
        L2c:
            if (r0 == 0) goto L31
            r0.close()     // Catch: java.io.IOException -> L31
        L31:
            throw r4
        L32:
            if (r0 == 0) goto L3b
        L34:
            r0.close()     // Catch: java.io.IOException -> L3b
            goto L3b
        L38:
            if (r0 == 0) goto L3b
            goto L34
        L3b:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.sepunion.cover.CoverManagerUtils.getValueFromSysFS(java.lang.String, java.lang.String):java.lang.String");
    }

    public static boolean isBackCover(CoverState coverState) {
        int type = coverState.getType();
        return type == 9 || type == 10 || type == 14 || type == 13 || type == 12;
    }

    public static boolean isFileExists(String str) {
        return BatteryService$$ExternalSyntheticOutline0.m45m(str);
    }

    public static void performCPUBoostCover(Context context) {
        int[] supportedFrequency;
        int[] supportedFrequency2;
        int i;
        int[] supportedFrequency3;
        if (sCoverCpuBooster == null) {
            SemDvfsManager createInstance = SemDvfsManager.createInstance(context, "COVER_BOOSTER", 12);
            sCoverCpuBooster = createInstance;
            if (createInstance != null && (supportedFrequency3 = createInstance.getSupportedFrequency()) != null) {
                sCoverCpuBooster.setDvfsValue(supportedFrequency3[0]);
            }
        }
        SemDvfsManager semDvfsManager = sCoverCpuBooster;
        if (semDvfsManager != null) {
            try {
                semDvfsManager.acquire(2000);
            } catch (Exception e) {
                Log.e("CoverManager_CoverManagerUtils", "sCoverCpuBooster.acquire is failed", e);
            }
        }
        if (sCoverCoreNumLockHelper == null) {
            SemDvfsManager createInstance2 = SemDvfsManager.createInstance(context, "COVER_CORE_BOOSTER", 14);
            sCoverCoreNumLockHelper = createInstance2;
            if (createInstance2 != null && (supportedFrequency2 = createInstance2.getSupportedFrequency()) != null && supportedFrequency2.length > 0 && (i = supportedFrequency2[0]) >= 2) {
                sCoverCoreNumLockHelper.setDvfsValue(i);
            }
        }
        SemDvfsManager semDvfsManager2 = sCoverCoreNumLockHelper;
        if (semDvfsManager2 != null) {
            try {
                semDvfsManager2.acquire(2000);
            } catch (Exception e2) {
                Log.e("CoverManager_CoverManagerUtils", "sCoverCoreNumLockHelper.acquire is failed", e2);
            }
        }
        if (sCoverBusBooster == null) {
            SemDvfsManager createInstance3 = SemDvfsManager.createInstance(context, "COVER_BUS_BOOSTER", 19);
            sCoverBusBooster = createInstance3;
            if (createInstance3 != null && (supportedFrequency = createInstance3.getSupportedFrequency()) != null && supportedFrequency.length > 0) {
                sCoverBusBooster.setDvfsValue(supportedFrequency[0]);
            }
        }
        SemDvfsManager semDvfsManager3 = sCoverBusBooster;
        if (semDvfsManager3 != null) {
            try {
                semDvfsManager3.acquire(2000);
            } catch (Exception e3) {
                Log.e("CoverManager_CoverManagerUtils", "sCoverBusBooster.acquire is failed", e3);
            }
        }
    }
}
