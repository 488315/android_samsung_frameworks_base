package com.android.server.enterprise.license;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;
import android.util.Log;
import android.util.Slog;

import com.android.server.backup.BackupManagerConstants;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.Utils;

import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.analytics.service.EventQueue;
import com.samsung.android.knox.analytics.service.KnoxAnalyticsSystemService;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Calendar;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LicenseLogService extends Binder implements EnterpriseServiceCallback {
    public static EdmStorageProvider mEdmStorageProvider;
    public static LogHandler mHandler;
    public static EnterpriseLicenseService mLicenseService;
    public final Context mContext;
    public final HandlerThread mHandlerThread;
    public final AnonymousClass1 mReceiver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.license.LicenseLogService$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.LOCKED_BOOT_COMPLETED".equals(action)
                    || "com.samsung.android.knox.intent.action.LICENSE_LOG_DELETE_OLD_RECORDS_INTERNAL"
                            .equals(action)) {
                Log.v("LicenseLogService", "License log delete old records for action " + action);
                LicenseLogService.mHandler.sendMessage(LicenseLogService.mHandler.obtainMessage(1));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LogHandler extends Handler {
        public LogHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            long clearCallingIdentity;
            String str;
            EnterpriseLicenseService enterpriseLicenseService;
            if (message != null) {
                int i = message.what;
                LicenseLogService licenseLogService = LicenseLogService.this;
                if (i != 1) {
                    if (i != 2) {
                        return;
                    }
                    Bundle data = message.getData();
                    String string = data.getString("apiName");
                    int i2 = data.getInt("adminUid");
                    boolean z = data.getBoolean(EventQueue.API_USAGE_GET_KEY);
                    boolean z2 = data.getBoolean("isOldNamespace");
                    int i3 = data.getInt("profileUserId");
                    boolean z3 = data.getBoolean("parent");
                    int i4 = data.getInt("dalessCallerPackage");
                    licenseLogService.getClass();
                    long clearCallingIdentity2 = Binder.clearCallingIdentity();
                    try {
                        try {
                            String packageNameForUid =
                                    LicenseLogService.mEdmStorageProvider.getPackageNameForUid(i2);
                            if (packageNameForUid == null) {
                                packageNameForUid =
                                        licenseLogService
                                                .mContext
                                                .getPackageManager()
                                                .getNameForUid(i2);
                            }
                            if (i4 > 0) {
                                str =
                                        LicenseLogService.mEdmStorageProvider.getPackageNameForUid(
                                                i4);
                                if (str == null) {
                                    str =
                                            licenseLogService
                                                    .mContext
                                                    .getPackageManager()
                                                    .getNameForUid(i4);
                                }
                            } else {
                                str = null;
                            }
                            if (packageNameForUid != null) {
                                IEDMProxy service =
                                        EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
                                if (service != null
                                        && service.isAllowedMamPackage(packageNameForUid)) {
                                    Log.d(
                                            "LicenseLogService",
                                            "skip _log - caller: "
                                                    + packageNameForUid
                                                    + ", apiName: "
                                                    + string);
                                } else if (Utils.isPlatformSignedApp(
                                                licenseLogService.mContext,
                                                packageNameForUid,
                                                UserHandle.getUserId(i2))
                                        || ((enterpriseLicenseService =
                                                                LicenseLogService.mLicenseService)
                                                        != null
                                                && enterpriseLicenseService.getLicenseInfoByAdmin(
                                                                packageNameForUid)
                                                        != null)) {
                                    LicenseLogService._log_for_old(string, packageNameForUid);
                                    licenseLogService._log_for_ka(
                                            string, packageNameForUid, z, z2, i3, z3, str);
                                }
                            }
                        } catch (Exception e) {
                            Log.w("LicenseLogService", "_log() failed");
                            Slog.w("LicenseLogService", "_log() failed", e);
                        }
                        return;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity2);
                    }
                }
                Log.i("LicenseLogService", "MSG_CLEAN_OLD_RECORDS");
                licenseLogService.getClass();
                Log.v("LicenseLogService", "handleLicenseLogCleanNotification()");
                Calendar calendar = Calendar.getInstance();
                calendar.set(11, 0);
                calendar.set(12, 0);
                calendar.set(13, 0);
                calendar.set(14, 0);
                long timeInMillis = calendar.getTimeInMillis();
                long j = timeInMillis - 2592000000L;
                try {
                    try {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("date<=?", Long.valueOf(j));
                        LicenseLogService.mEdmStorageProvider.delete("LICENSE_LOG", contentValues);
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        long j2 =
                                timeInMillis
                                        + BackupManagerConstants
                                                .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
                        try {
                            AlarmManager alarmManager =
                                    (AlarmManager)
                                            licenseLogService.mContext.getSystemService("alarm");
                            PendingIntent broadcast =
                                    PendingIntent.getBroadcast(
                                            licenseLogService.mContext,
                                            0,
                                            new Intent(
                                                    "com.samsung.android.knox.intent.action.LICENSE_LOG_DELETE_OLD_RECORDS_INTERNAL"),
                                            1207959552);
                            alarmManager.cancel(broadcast);
                            if (j2 != 0) {
                                alarmManager.set(1, j2, broadcast);
                            }
                        } catch (Exception e2) {
                            e = e2;
                            Log.w(
                                    "LicenseLogService",
                                    "handleLicenseLogCleanNotification() failed");
                            Slog.w(
                                    "LicenseLogService",
                                    "handleLicenseLogCleanNotification() failed",
                                    e);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    } catch (Throwable th) {
                        long clearCallingIdentity3 = Binder.clearCallingIdentity();
                        long j3 =
                                timeInMillis
                                        + BackupManagerConstants
                                                .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
                        try {
                            AlarmManager alarmManager2 =
                                    (AlarmManager)
                                            licenseLogService.mContext.getSystemService("alarm");
                            PendingIntent broadcast2 =
                                    PendingIntent.getBroadcast(
                                            licenseLogService.mContext,
                                            0,
                                            new Intent(
                                                    "com.samsung.android.knox.intent.action.LICENSE_LOG_DELETE_OLD_RECORDS_INTERNAL"),
                                            1207959552);
                            alarmManager2.cancel(broadcast2);
                            if (j3 != 0) {
                                alarmManager2.set(1, j3, broadcast2);
                            }
                        } catch (Exception e3) {
                            Log.w(
                                    "LicenseLogService",
                                    "handleLicenseLogCleanNotification() failed");
                            Slog.w(
                                    "LicenseLogService",
                                    "handleLicenseLogCleanNotification() failed",
                                    e3);
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity3);
                        throw th;
                    }
                } catch (Exception e4) {
                    Log.w("LicenseLogService", "handleLicenseLogCleanNotification() failed");
                    Slog.w("LicenseLogService", "handleLicenseLogCleanNotification() failed", e4);
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    long j4 =
                            timeInMillis
                                    + BackupManagerConstants
                                            .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
                    try {
                        AlarmManager alarmManager3 =
                                (AlarmManager) licenseLogService.mContext.getSystemService("alarm");
                        PendingIntent broadcast3 =
                                PendingIntent.getBroadcast(
                                        licenseLogService.mContext,
                                        0,
                                        new Intent(
                                                "com.samsung.android.knox.intent.action.LICENSE_LOG_DELETE_OLD_RECORDS_INTERNAL"),
                                        1207959552);
                        alarmManager3.cancel(broadcast3);
                        if (j4 != 0) {
                            alarmManager3.set(1, j4, broadcast3);
                        }
                    } catch (Exception e5) {
                        e = e5;
                        Log.w("LicenseLogService", "handleLicenseLogCleanNotification() failed");
                        Slog.w(
                                "LicenseLogService",
                                "handleLicenseLogCleanNotification() failed",
                                e);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public LicenseLogService(Context context) {
        this.mHandlerThread = null;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mContext = context;
        mEdmStorageProvider = new EdmStorageProvider(context);
        mLicenseService =
                (EnterpriseLicenseService)
                        EnterpriseService.getPolicyService("enterprise_license_policy");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
        intentFilter.addAction(
                "com.samsung.android.knox.intent.action.LICENSE_LOG_DELETE_OLD_RECORDS_INTERNAL");
        context.registerReceiver(anonymousClass1, intentFilter, 2);
        HandlerThread handlerThread = new HandlerThread("LicenseLogService");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        mHandler = new LogHandler(this.mHandlerThread.getLooper());
    }

    public static void _log_for_old(String str, String str2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        long timeInMillis = calendar.getTimeInMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pkgName", str2);
        contentValues.put("id", str);
        contentValues.put("date", String.valueOf(timeInMillis));
        ContentValues value = mEdmStorageProvider.getValue(contentValues, "LICENSE_LOG", "value");
        if (value == null) {
            mEdmStorageProvider.putValuesNoUpdate("LICENSE_LOG", contentValues);
            return;
        }
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("value", Integer.valueOf(value.getAsInteger("value").intValue() + 1));
        mEdmStorageProvider.putValues("LICENSE_LOG", contentValues2, contentValues);
    }

    public final void _log_for_ka(
            String str, String str2, boolean z, boolean z2, int i, boolean z3, String str3) {
        KnoxAnalyticsData knoxAnalyticsData =
                new KnoxAnalyticsData(EventQueue.API_USAGE_FEATURE_NAME, 1, str);
        knoxAnalyticsData.setProperty("pN", str3 != null ? str3 : str2);
        knoxAnalyticsData.setUserTypeProperty(i);
        knoxAnalyticsData.setProperty("ppi", z3 ? 1 : 0);
        knoxAnalyticsData.setProperty("NON_DA", str3 != null ? 1 : 0);
        if (z) {
            knoxAnalyticsData.setProperty(EventQueue.API_USAGE_GET_KEY, true);
        }
        if (z2) {
            knoxAnalyticsData.setProperty("ons", 1);
        }
        try {
            knoxAnalyticsData.setProperty(
                    KnoxAnalyticsSystemService.PUB_KEY_MD5_PARAMETER_NAME,
                    Utils.getApplicationPubKeyMD5(this.mContext, str2));
        } catch (PackageManager.NameNotFoundException
                | NoSuchAlgorithmException
                | CertificateException unused) {
            Log.e("LicenseLogService", "error getApplicationPubKeyMD5");
        }
        KnoxAnalytics.log(knoxAnalyticsData);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {}
}
