package com.android.server;

import android.app.ActivityManagerInternal;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SemHqmManager;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.BinderCallsStats;
import com.android.server.backup.BackupManagerConstants;
import com.samsung.android.privilege.SemPrivilegeManager;
import java.util.function.BiFunction;

/* loaded from: classes.dex */
public class CustomizedBinderCallsStatsInternal {
    public ActivityManagerInternal mAm;
    public final BinderCallsStats mBinderCallsStats;
    public Context mContext;
    public long mLastWriteTime = 0;
    public long mLastStoreTime = 0;
    public long mLastNotifyTime = 0;
    public long mLastAtraceTime = 0;

    public CustomizedBinderCallsStatsInternal(BinderCallsStats binderCallsStats, Context context) {
        this.mBinderCallsStats = binderCallsStats;
        this.mContext = context;
    }

    public ActivityManagerInternal getActivityManager() {
        if (this.mAm == null) {
            this.mAm = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        }
        return this.mAm;
    }

    public void reportProcessDied(int i, int i2, String str) {
        this.mBinderCallsStats.reportProcessDied(i, i2, str);
    }

    public void reportCpuUsage(final int i) {
        if (!isPassedInterval(this.mLastStoreTime, 55000L)) {
            Slog.i("CustomizedBinderCallsStatsInternal", "1 minute has NOT pass since last binder stats.");
            return;
        }
        Slog.d("CustomizedBinderCallsStatsInternal", "reportCpuUsage() : " + i + "%");
        BackgroundThread.getHandler().post(new Runnable() { // from class: com.android.server.CustomizedBinderCallsStatsInternal$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CustomizedBinderCallsStatsInternal.this.lambda$reportCpuUsage$1(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportCpuUsage$1(int i) {
        this.mBinderCallsStats.store(5, i);
        this.mLastStoreTime = System.currentTimeMillis();
        if (isPassedInterval(this.mLastWriteTime, 1800000L)) {
            Slog.i("CustomizedBinderCallsStatsInternal", "should write the current data!!");
            IoThread.getHandler().post(new Runnable() { // from class: com.android.server.CustomizedBinderCallsStatsInternal$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CustomizedBinderCallsStatsInternal.this.lambda$reportCpuUsage$0();
                }
            });
            this.mLastWriteTime = System.currentTimeMillis();
        }
        leaveAtraceLog(i);
        detectHeaviestApp(i);
        if (this.mBinderCallsStats.isNeededResetData()) {
            Slog.i("CustomizedBinderCallsStatsInternal", "Clear all data");
            this.mBinderCallsStats.reset(new boolean[]{true});
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportCpuUsage$0() {
        this.mBinderCallsStats.writeToFile();
    }

    public final void leaveAtraceLog(int i) {
        if (System.currentTimeMillis() - SystemProperties.getLong("ro.runtime.firstboot", 0L) < 600000 || !isPackageInstalled("com.salab.issuetracker") || i < 10 || !isPassedInterval(this.mLastAtraceTime, BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS)) {
            return;
        }
        Slog.d("CustomizedBinderCallsStatsInternal", "leave atrace log...");
        ((SemPrivilegeManager) this.mContext.getSystemService("semprivilege")).setAtraceControlMode(3);
        this.mLastAtraceTime = System.currentTimeMillis();
    }

    public final void detectHeaviestApp(int i) {
        BinderCallsStats.HeavyBinderCallerInfo heaviestApplicationUid;
        if (i < 20 || (heaviestApplicationUid = this.mBinderCallsStats.getHeaviestApplicationUid(90)) == null) {
            return;
        }
        Slog.i("CustomizedBinderCallsStatsInternal", "heavy binder caller : " + heaviestApplicationUid.mPackageName + "(" + heaviestApplicationUid.mUid + ")");
        if (isPassedInterval(this.mLastNotifyTime, BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS)) {
            notifyAnomalyApp(heaviestApplicationUid);
            sendAnomalyAppToHWParam(heaviestApplicationUid, i);
            this.mLastNotifyTime = System.currentTimeMillis();
        }
    }

    public final boolean isPackageInstalled(String str) {
        try {
            return this.mContext.getPackageManager().getPackageInfo(str, 0) != null;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final boolean isPassedInterval(long j, long j2) {
        return j == 0 || System.currentTimeMillis() - j >= j2;
    }

    public final void notifyAnomalyApp(BinderCallsStats.HeavyBinderCallerInfo heavyBinderCallerInfo) {
        ActivityManagerInternal activityManager = getActivityManager();
        Intent intent = new Intent("com.sec.android.sdhms.action.APP_ERROR");
        intent.putExtra("pkgName", heavyBinderCallerInfo.mPackageName);
        intent.putExtra("userId", 0);
        intent.putExtra("uid", heavyBinderCallerInfo.mUid);
        intent.putExtra("type", "excessive_binder");
        intent.setPackage("com.sec.android.sdhms");
        activityManager.broadcastIntent(intent, (IIntentReceiver) null, (String[]) null, false, 0, (int[]) null, (BiFunction) null, (Bundle) null);
    }

    public final void sendAnomalyAppToHWParam(BinderCallsStats.HeavyBinderCallerInfo heavyBinderCallerInfo, int i) {
        SemHqmManager semHqmManager = (SemHqmManager) this.mContext.getSystemService("HqmManagerService");
        if (semHqmManager != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("\"PKG\":\"" + heavyBinderCallerInfo.mPackageName + "\",");
            sb.append("\"SSCPU\":\"" + i + "\",");
            sb.append("\"RATIO\":\"" + String.format("%.2f", Float.valueOf(heavyBinderCallerInfo.mRatio)) + "\",");
            int length = sb.length() + heavyBinderCallerInfo.mExtraInfo.length() + 10;
            if (length > 1000) {
                sb.append("\"EXTRA\":\"" + heavyBinderCallerInfo.mExtraInfo.substring(0, heavyBinderCallerInfo.mExtraInfo.length() - (length - 1000)) + "\"");
            } else {
                sb.append("\"EXTRA\":\"" + heavyBinderCallerInfo.mExtraInfo + "\"");
            }
            Slog.i("CustomizedBinderCallsStatsInternal", "Data is : " + sb.toString());
            if (semHqmManager.sendHWParamToHQM(0, "AP", "HBCA", "sm", "0.0", "", "", sb.toString(), "")) {
                return;
            }
            Slog.e("CustomizedBinderCallsStatsInternal", "Failed to send anomaly application info. to HWParam");
            return;
        }
        Slog.e("CustomizedBinderCallsStatsInternal", "Cannot get HqmManagerService !!!");
    }

    public void shutdown() {
        this.mBinderCallsStats.writeToFile();
    }
}
