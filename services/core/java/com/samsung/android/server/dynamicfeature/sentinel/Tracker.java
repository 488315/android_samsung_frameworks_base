package com.samsung.android.server.dynamicfeature.sentinel;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Slog;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.samsung.android.server.dynamicfeature.DynamicFeatureService;
import java.util.List;
import org.json.JSONObject;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Tracker extends BroadcastReceiver {
    public DynamicFeatureService.AnonymousClass1 serviceCallback;

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String stringExtra;
        if (!intent.getAction().equals("android.intent.action.DROPBOX_ENTRY_ADDED") || (stringExtra = intent.getStringExtra("tag")) == null) {
            return;
        }
        if (stringExtra.endsWith("crash") || stringExtra.endsWith("anr")) {
            DeviceIdleController$$ExternalSyntheticOutline0.m("DropBoxManager report this: \"", stringExtra, "\" event", "dynamicfeature_Tracker");
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            if (activityManager == null) {
                Slog.d("dynamicfeature_Tracker", "AMS is null");
                return;
            }
            List<ActivityManager.ProcessErrorStateInfo> processesInErrorState = activityManager.getProcessesInErrorState();
            if (processesInErrorState == null) {
                Slog.d("dynamicfeature_Tracker", "ProcessErrorStateInfo list is null");
                return;
            }
            PackageManager packageManager = context.getPackageManager();
            String str = "none";
            String str2 = "none";
            String str3 = str2;
            String str4 = str3;
            for (ActivityManager.ProcessErrorStateInfo processErrorStateInfo : processesInErrorState) {
                String str5 = processErrorStateInfo.processName;
                if (str5 != null) {
                    str = str5;
                }
                if (packageManager != null) {
                    try {
                        str2 = packageManager.getPackageInfo(str.contains(":") ? str.split(":")[0] : str, 0).versionName;
                    } catch (Exception unused) {
                        Slog.d("dynamicfeature_Tracker", "app version is invalid");
                    }
                }
                String str6 = processErrorStateInfo.shortMsg;
                if (str6 != null) {
                    str4 = str6.replace("\"", "'");
                    if (str4.length() > 10000) {
                        str4 = str4.substring(0, 9999);
                    }
                }
                String str7 = processErrorStateInfo.stackTrace;
                if (str7 != null) {
                    str3 = str7.replace("\"", "'");
                    if (str3.length() > 10000) {
                        str3 = str3.substring(0, 9999);
                    }
                }
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("cond", processErrorStateInfo.condition);
                    jSONObject.put("proc", processErrorStateInfo.processName);
                    jSONObject.put("short", processErrorStateInfo.shortMsg);
                    jSONObject.put("version", str2);
                    jSONObject.put("stack", str3);
                    jSONObject.put("client_version", "0.1.1");
                    jSONObject.put("reason", str4);
                } catch (Exception e) {
                    Slog.e("dynamicfeature_Tracker", "unknown error while:" + e.getClass());
                }
                this.serviceCallback.onSignalFire(jSONObject.toString());
                Slog.d("dynamicfeature", jSONObject.toString());
            }
        }
    }
}
