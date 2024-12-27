package com.android.server.appfunctions;

import android.R;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.appfunctions.AppFunctionExecutionRecord;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemProperties;
import android.util.IndentingPrintWriter;

import com.samsung.android.feature.SemFloatingFeature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppFunctionLoggerHelper {
    public final AppFunctionExecutionRecord[] mAppFunctionExecutionHistory =
            new AppFunctionExecutionRecord[10];
    public int mAppFunctionExecutionHistoryIdx = 0;
    public final Context mContext;
    public final PackageManager mPackageManager;
    public static final boolean USER_BUILD = "user".equals(Build.TYPE);
    public static final boolean SHIP_BUILD =
            "true".equals(SystemProperties.get("ro.product_ship", "false"));
    public static final boolean SA_LOG_ENABLED =
            SemFloatingFeature.getInstance()
                    .getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE");

    public AppFunctionLoggerHelper(Context context) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter, String[] strArr) {
        boolean z;
        indentingPrintWriter.println("App Function Execution History:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mAppFunctionExecutionHistory) {
            try {
                AppFunctionExecutionRecord[] appFunctionExecutionRecordArr =
                        this.mAppFunctionExecutionHistory;
                AppFunctionExecutionRecord[] appFunctionExecutionRecordArr2 =
                        (AppFunctionExecutionRecord[])
                                Arrays.copyOf(
                                        appFunctionExecutionRecordArr,
                                        appFunctionExecutionRecordArr.length);
                Arrays.sort(
                        appFunctionExecutionRecordArr2,
                        Comparator.nullsLast(
                                Comparator.comparing(
                                        new AppFunctionLoggerHelper$$ExternalSyntheticLambda1(),
                                        Comparator.nullsLast(Comparator.naturalOrder()))));
                for (AppFunctionExecutionRecord appFunctionExecutionRecord :
                        appFunctionExecutionRecordArr2) {
                    if (appFunctionExecutionRecord != null) {
                        if (strArr.length <= 0 || !"-s".equals(strArr[0])) {
                            if (!USER_BUILD && !SHIP_BUILD) {
                                z = false;
                                indentingPrintWriter.println(
                                        appFunctionExecutionRecord.toFullString(z));
                            }
                            z = true;
                            indentingPrintWriter.println(
                                    appFunctionExecutionRecord.toFullString(z));
                        } else {
                            indentingPrintWriter.println(
                                    appFunctionExecutionRecord.toSummaryString());
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final HashMap getDimensionMap(AppFunctionExecutionRecord appFunctionExecutionRecord) {
        String str;
        ComponentName componentName;
        String str2 = "";
        HashMap hashMap = new HashMap();
        hashMap.put("caller_app", appFunctionExecutionRecord.getCallingPackage());
        try {
            PackageInfo packageInfo =
                    this.mPackageManager.getPackageInfo(
                            appFunctionExecutionRecord.getCallingPackage(), 0);
            str = String.valueOf(packageInfo != null ? packageInfo.getLongVersionCode() : -1L);
        } catch (Exception unused) {
            str = "";
        }
        hashMap.put("caller_app_version", str);
        hashMap.put("target_app", appFunctionExecutionRecord.getTargetPackageName());
        try {
            PackageInfo packageInfo2 =
                    this.mPackageManager.getPackageInfo(
                            appFunctionExecutionRecord.getTargetPackageName(), 0);
            str2 = String.valueOf(packageInfo2 != null ? packageInfo2.getLongVersionCode() : -1L);
        } catch (Exception unused2) {
        }
        hashMap.put("target_app_version", str2);
        hashMap.put("function_id", appFunctionExecutionRecord.getFunctionIdentifier());
        hashMap.put("result_code", appFunctionExecutionRecord.getResultCode());
        hashMap.put("duration", String.valueOf(appFunctionExecutionRecord.getDuration()));
        List<ActivityManager.RunningTaskInfo> runningTasks =
                ((ActivityManager) this.mContext.getSystemService(ActivityManager.class))
                        .getRunningTasks(Integer.MAX_VALUE);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < runningTasks.size(); i++) {
            ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(i);
            if (runningTaskInfo != null
                    && (componentName = runningTaskInfo.topActivity) != null
                    && runningTaskInfo.isVisible) {
                arrayList.add(componentName.getPackageName());
            }
        }
        ArrayList arrayList2 = new ArrayList(arrayList);
        KeyguardManager keyguardManager =
                (KeyguardManager) this.mContext.getSystemService(KeyguardManager.class);
        if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
            arrayList2.add(this.mContext.getResources().getString(R.string.config_systemUi));
        }
        hashMap.put("foreground_app", String.join(",", arrayList2));
        return hashMap;
    }
}
