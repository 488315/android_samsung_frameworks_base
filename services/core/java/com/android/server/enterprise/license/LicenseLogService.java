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
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.analytics.service.EventQueue;
import com.samsung.android.knox.analytics.service.KnoxAnalyticsSystemService;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.List;

/* loaded from: classes2.dex */
public class LicenseLogService extends Binder implements EnterpriseServiceCallback {
    public static EdmStorageProvider mEdmStorageProvider;
    public static LogHandler mHandler;
    public static EnterpriseLicenseService mLicenseService;
    public Context mContext;
    public HandlerThread mHandlerThread = null;
    public BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.license.LicenseLogService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.LOCKED_BOOT_COMPLETED".equals(action) || "com.samsung.android.knox.intent.action.LICENSE_LOG_DELETE_OLD_RECORDS_INTERNAL".equals(action)) {
                Log.v("LicenseLogService", "License log delete old records for action " + action);
                LicenseLogService.mHandler.sendMessage(LicenseLogService.mHandler.obtainMessage(1));
            }
        }
    };

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
    }

    public LicenseLogService(Context context) {
        this.mContext = context;
        mEdmStorageProvider = new EdmStorageProvider(context);
        mLicenseService = (EnterpriseLicenseService) EnterpriseService.getPolicyService("enterprise_license_policy");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
        intentFilter.addAction("com.samsung.android.knox.intent.action.LICENSE_LOG_DELETE_OLD_RECORDS_INTERNAL");
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
        initializeHandlerThread();
    }

    public final void initializeHandlerThread() {
        HandlerThread handlerThread = new HandlerThread("LicenseLogService");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        mHandler = new LogHandler(this.mHandlerThread.getLooper());
    }

    public static void log(ContextInfo contextInfo, String str, boolean z, boolean z2) {
        if (str == null || contextInfo == null || mHandler == null) {
            return;
        }
        try {
            int appId = UserHandle.getAppId(Binder.getCallingUid());
            if (appId >= 10000 && appId <= 19999) {
                Message obtainMessage = mHandler.obtainMessage(2);
                Bundle bundle = new Bundle();
                bundle.putString("apiName", str);
                bundle.putInt("adminUid", contextInfo.mCallerUid);
                bundle.putBoolean(EventQueue.API_USAGE_GET_KEY, z);
                bundle.putBoolean("isOldNamespace", z2);
                bundle.putInt("profileUserId", contextInfo.mContainerId);
                bundle.putBoolean("parent", contextInfo.mParent);
                bundle.putInt("dalessCallerPackage", contextInfo.mDALessCallerUid);
                obtainMessage.setData(bundle);
                mHandler.sendMessage(obtainMessage);
            }
        } catch (Exception e) {
            Log.w("LicenseLogService", "log() failed");
            Slog.w("LicenseLogService", "log() failed", e);
        }
    }

    public final void _log(String str, int i, boolean z, boolean z2, int i2, boolean z3, int i3) {
        String packageNameForUid;
        String packageNameForUid2;
        EnterpriseLicenseService enterpriseLicenseService;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                packageNameForUid = getPackageNameForUid(i);
                packageNameForUid2 = i3 > 0 ? getPackageNameForUid(i3) : null;
            } catch (Exception e) {
                Log.w("LicenseLogService", "_log() failed");
                Slog.w("LicenseLogService", "_log() failed", e);
            }
            if (packageNameForUid == null) {
                return;
            }
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null && service.isAllowedMamPackage(packageNameForUid)) {
                Log.d("LicenseLogService", "skip _log - caller: " + packageNameForUid + ", apiName: " + str);
                return;
            }
            if (Utils.isPlatformSignedApp(this.mContext, packageNameForUid, i) || ((enterpriseLicenseService = mLicenseService) != null && enterpriseLicenseService.getLicenseInfoByAdmin(packageNameForUid) != null)) {
                _log_for_old(str, packageNameForUid);
                _log_for_ka(str, packageNameForUid, z, z2, i2, z3, packageNameForUid2);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void _log_for_old(String str, String str2) {
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
        ContentValues value = mEdmStorageProvider.getValue("LICENSE_LOG", "value", contentValues);
        if (value != null) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("value", Integer.valueOf(value.getAsInteger("value").intValue() + 1));
            mEdmStorageProvider.putValues("LICENSE_LOG", contentValues2, contentValues);
            return;
        }
        mEdmStorageProvider.putValuesNoUpdate("LICENSE_LOG", contentValues);
    }

    public final void _log_for_ka(String str, String str2, boolean z, boolean z2, int i, boolean z3, String str3) {
        KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData(EventQueue.API_USAGE_FEATURE_NAME, 1, str);
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
            knoxAnalyticsData.setProperty(KnoxAnalyticsSystemService.PUB_KEY_MD5_PARAMETER_NAME, Utils.getApplicationPubKeyMD5(this.mContext, str2));
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException | CertificateException unused) {
            Log.e("LicenseLogService", "error getApplicationPubKeyMD5");
        }
        KnoxAnalytics.log(knoxAnalyticsData);
    }

    public static Bundle getLog(String str) {
        try {
            Bundle bundle = new Bundle();
            int i = 0;
            ContentValues contentValues = new ContentValues();
            contentValues.put("pkgName", str);
            List<ContentValues> valuesList = mEdmStorageProvider.getValuesList("LICENSE_LOG", new String[]{"date", "id", "value"}, contentValues);
            if (valuesList != null && !valuesList.isEmpty()) {
                String str2 = null;
                String str3 = null;
                for (ContentValues contentValues2 : valuesList) {
                    if (contentValues2 != null) {
                        str3 = contentValues2.getAsString("date");
                        str2 = contentValues2.getAsString("id");
                        i = contentValues2.getAsInteger("value").intValue();
                    }
                    if (bundle.getBundle(str3) == null) {
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt(str2, i);
                        bundle.putBundle(str3, bundle2);
                    } else {
                        bundle.getBundle(str3).putInt(str2, i);
                    }
                }
                return bundle;
            }
        } catch (Exception e) {
            Log.w("LicenseLogService", "getLog() failed");
            Slog.w("LicenseLogService", "getLog() failed", e);
        }
        return null;
    }

    public static boolean deleteLog(String str) {
        return mEdmStorageProvider.deleteDataByFields("LICENSE_LOG", new String[]{"pkgName"}, new String[]{str});
    }

    public static boolean deleteAllLog() {
        return mEdmStorageProvider.deleteDataByFields("LICENSE_LOG", null, null);
    }

    public final String getPackageNameForUid(int i) {
        String packageNameForUid = mEdmStorageProvider.getPackageNameForUid(i);
        return packageNameForUid == null ? this.mContext.getPackageManager().getNameForUid(i) : packageNameForUid;
    }

    public final class LogHandler extends Handler {
        public LogHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message != null) {
                int i = message.what;
                if (i == 1) {
                    Log.i("LicenseLogService", "MSG_CLEAN_OLD_RECORDS");
                    LicenseLogService.this.handleLicenseLogCleanNotification();
                } else {
                    if (i != 2) {
                        return;
                    }
                    Bundle data = message.getData();
                    LicenseLogService.this._log(data.getString("apiName"), data.getInt("adminUid"), data.getBoolean(EventQueue.API_USAGE_GET_KEY), data.getBoolean("isOldNamespace"), data.getInt("profileUserId"), data.getBoolean("parent"), data.getInt("dalessCallerPackage"));
                }
            }
        }
    }

    public final void handleLicenseLogCleanNotification() {
        long clearCallingIdentity;
        long j;
        Log.v("LicenseLogService", "handleLicenseLogCleanNotification()");
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        long timeInMillis = calendar.getTimeInMillis();
        long j2 = timeInMillis - 2592000000L;
        try {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("date<=?", Long.valueOf(j2));
                mEdmStorageProvider.delete("LICENSE_LOG", contentValues);
                clearCallingIdentity = Binder.clearCallingIdentity();
                j = timeInMillis + BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
            } catch (Exception e) {
                Log.w("LicenseLogService", "handleLicenseLogCleanNotification() failed");
                Slog.w("LicenseLogService", "handleLicenseLogCleanNotification() failed", e);
                clearCallingIdentity = Binder.clearCallingIdentity();
                long j3 = timeInMillis + BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
                try {
                    AlarmManager alarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
                    PendingIntent broadcast = PendingIntent.getBroadcast(this.mContext, 0, new Intent("com.samsung.android.knox.intent.action.LICENSE_LOG_DELETE_OLD_RECORDS_INTERNAL"), 1207959552);
                    alarmManager.cancel(broadcast);
                    if (j3 != 0) {
                        alarmManager.set(1, j3, broadcast);
                    }
                } catch (Exception e2) {
                    e = e2;
                    Log.w("LicenseLogService", "handleLicenseLogCleanNotification() failed");
                    Slog.w("LicenseLogService", "handleLicenseLogCleanNotification() failed", e);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            try {
                AlarmManager alarmManager2 = (AlarmManager) this.mContext.getSystemService("alarm");
                PendingIntent broadcast2 = PendingIntent.getBroadcast(this.mContext, 0, new Intent("com.samsung.android.knox.intent.action.LICENSE_LOG_DELETE_OLD_RECORDS_INTERNAL"), 1207959552);
                alarmManager2.cancel(broadcast2);
                if (j != 0) {
                    alarmManager2.set(1, j, broadcast2);
                }
            } catch (Exception e3) {
                e = e3;
                Log.w("LicenseLogService", "handleLicenseLogCleanNotification() failed");
                Slog.w("LicenseLogService", "handleLicenseLogCleanNotification() failed", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            long clearCallingIdentity2 = Binder.clearCallingIdentity();
            long j4 = timeInMillis + BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
            try {
                AlarmManager alarmManager3 = (AlarmManager) this.mContext.getSystemService("alarm");
                PendingIntent broadcast3 = PendingIntent.getBroadcast(this.mContext, 0, new Intent("com.samsung.android.knox.intent.action.LICENSE_LOG_DELETE_OLD_RECORDS_INTERNAL"), 1207959552);
                alarmManager3.cancel(broadcast3);
                if (j4 != 0) {
                    alarmManager3.set(1, j4, broadcast3);
                }
            } catch (Exception e4) {
                Log.w("LicenseLogService", "handleLicenseLogCleanNotification() failed");
                Slog.w("LicenseLogService", "handleLicenseLogCleanNotification() failed", e4);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity2);
            throw th;
        }
    }
}
