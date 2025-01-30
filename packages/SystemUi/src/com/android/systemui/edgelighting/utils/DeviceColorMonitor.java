package com.android.systemui.edgelighting.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Debug;
import android.os.SemSystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DeviceColorMonitor {
    public static final HashMap mWallPaperColorMap;

    static {
        Debug.semIsProductDev();
        mWallPaperColorMap = new HashMap<String, Integer>() { // from class: com.android.systemui.edgelighting.utils.DeviceColorMonitor.1
            {
                put("zk", -13935182);
                put("ck", -13935182);
                put("zb", -13935182);
                put("cw", -10406);
                put("zw", -5080200);
                put("di", -5080200);
                put("zg", -12673368);
                put("zy", -3942561);
                put("zp", -8833114);
                put("zv", -8833114);
                put("zn", -5866694);
                put("zd", -5866694);
                put("zr", -1888979);
                put("zs", -7361340);
                put("za", -7361340);
                put("zi", -5080200);
            }
        };
        new HashMap<String, Integer>() { // from class: com.android.systemui.edgelighting.utils.DeviceColorMonitor.2
            {
                put("zk", -13422266);
                put("ck", -8029834);
                put("cw", -3886163);
                put("zw", -4738129);
                put("zg", -6378837);
                put("zy", -4743076);
                put("zb", -9069120);
                put("zi", -4482659);
                put("zs", -9069120);
                put("zr", -1888979);
                put("silver", -1513240);
                put("cooper", -3440043);
                put("burgundyred", 9448511);
            }
        };
    }

    public DeviceColorMonitor(Context context) {
    }

    public static int getDeviceWallPaperColorIndex(ContentResolver contentResolver) {
        String str = SemSystemProperties.get("ril.product_code", "none");
        boolean isEmpty = TextUtils.isEmpty(str);
        HashMap hashMap = mWallPaperColorMap;
        if (isEmpty || str.length() <= 10) {
            Slog.e("DeviceColorMonitor", "getDeviceWallPaperColorIndex setttingDB : " + Settings.System.getString(contentResolver, "cmf_color_code"));
        } else {
            try {
                String lowerCase = str.substring(8, 10).toLowerCase();
                if (hashMap.containsKey(lowerCase)) {
                    return ((Integer) hashMap.get(lowerCase)).intValue();
                }
            } catch (IndexOutOfBoundsException unused) {
                Slog.e("DeviceColorMonitor", "IndexOutOfBoundsException occurred on getDeviceColorCodeFromSystemProperty");
            }
        }
        Slog.e("DeviceColorMonitor", "getDeviceWallPaperColorIndex not existed color");
        return ((Integer) hashMap.get("zk")).intValue();
    }
}
