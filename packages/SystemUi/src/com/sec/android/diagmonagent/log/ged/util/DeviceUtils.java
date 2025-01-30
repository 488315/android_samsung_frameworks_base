package com.sec.android.diagmonagent.log.ged.util;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.provider.utils.DiagMonUtil;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DeviceUtils {
    public static final String TAG;

    static {
        String str;
        StringBuilder sb = new StringBuilder("GED_DIAGMON_SDK[");
        int i = DiagMonUtil.$r8$clinit;
        try {
            str = String.valueOf(605033);
        } catch (Exception unused) {
            str = "";
        }
        TAG = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, str, "]");
    }

    public static void deleteFiles(String str) {
        File[] listFiles;
        File file = new File(str);
        if (file.isDirectory() && (listFiles = file.listFiles()) != null) {
            for (File file2 : listFiles) {
                file2.delete();
            }
        }
        file.delete();
    }

    public static JSONObject getSimpleDeviceInfo(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            String str = Build.MODEL;
            if (!TextUtils.isEmpty(str)) {
                jSONObject.put("model", str);
            }
            String str2 = Build.TYPE;
            if (!TextUtils.isEmpty(str2)) {
                jSONObject.put("binaryType", str2);
            }
            String str3 = Build.FINGERPRINT;
            if (!TextUtils.isEmpty(str3)) {
                jSONObject.put("fingerprint", str3);
            }
            if (!TextUtils.isEmpty(getTmcc(context))) {
                jSONObject.put("tmcc", getTmcc(context));
            }
            if (!TextUtils.isEmpty(getSmcc(context))) {
                jSONObject.put("smcc", getSmcc(context));
            }
        } catch (JSONException e) {
            AppLog.m270e("Failed to get device info : " + e.getMessage());
        }
        return jSONObject;
    }

    public static String getSmcc(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            return null;
        }
        String simOperator = telephonyManager.getSimOperator();
        if (simOperator.length() > 3) {
            simOperator = simOperator.substring(0, 3);
        }
        if ("001,002,999,@65".contains(simOperator)) {
            return null;
        }
        return simOperator;
    }

    public static String getTmcc(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            return null;
        }
        String networkOperator = telephonyManager.getNetworkOperator();
        if (networkOperator.length() > 3) {
            networkOperator = networkOperator.substring(0, 3);
        }
        if ("001,002,999,@65".contains(networkOperator)) {
            return null;
        }
        return networkOperator;
    }
}
