package com.samsung.android.knox.log;

import android.content.Context;
import android.os.Binder;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.log.IAuditLog;

/* loaded from: classes4.dex */
public class AuditLog {
    public static final String ACTION_AUDIT_CRITICAL_SIZE = "com.samsung.android.knox.intent.action.AUDIT_CRITICAL_SIZE";
    public static final String ACTION_AUDIT_FULL_SIZE = "com.samsung.android.knox.intent.action.AUDIT_FULL_SIZE";
    public static final String ACTION_AUDIT_MAXIMUM_SIZE = "com.samsung.android.knox.intent.action.AUDIT_MAXIMUM_SIZE";
    public static final String ACTION_DUMP_LOG_RESULT = "com.samsung.android.knox.intent.action.DUMP_LOG_RESULT";
    public static final String ACTION_LOG_EXCEPTION = "com.samsung.android.knox.intent.action.LOG_EXCEPTION";
    public static final int AUDIT_LOG_GROUP_APPLICATION = 5;
    public static final int AUDIT_LOG_GROUP_EVENTS = 4;
    public static final int AUDIT_LOG_GROUP_NETWORK = 3;
    public static final int AUDIT_LOG_GROUP_SECURITY = 1;
    public static final int AUDIT_LOG_GROUP_SYSTEM = 2;
    public static final int AUDIT_LOG_SEVERITY_ALERT = 1;
    public static final int AUDIT_LOG_SEVERITY_CRITICAL = 2;
    public static final int AUDIT_LOG_SEVERITY_ERROR = 3;
    public static final int AUDIT_LOG_SEVERITY_NOTICE = 5;
    public static final int AUDIT_LOG_SEVERITY_WARNING = 4;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_UNKNOWN = -2000;
    public static final String EXTRA_AUDIT_RESULT = "com.samsung.android.knox.intent.extra.AUDIT_RESULT";
    public static final String TAG = "AuditLog";
    public static AuditLog mAuditLog;
    public static final Object mSync = new Object();
    public IAuditLog mAuditService;
    public ContextInfo mContextInfo;

    private AuditLog(ContextInfo contextInfo, Context context) {
        this.mContextInfo = contextInfo;
    }

    public static void a(int i, boolean z, int i2, String str, String str2) {
        EnterpriseLicenseManager.log(new ContextInfo(Binder.getCallingUid()), "AuditLog.a");
        IAuditLog iAuditLogAsInterface = IAuditLog.Stub.asInterface(ServiceManager.getService("auditlog"));
        if (iAuditLogAsInterface != null) {
            try {
                iAuditLogAsInterface.logEventForLegacyComponents(i2, new LegacyEvent(-1, str, Integer.valueOf(i), str2, null, Boolean.valueOf(z), 1));
            } catch (RemoteException unused) {
                Log.w(TAG, "Access to auditLogger not allowed");
            }
        }
    }

    public static void c(int i, boolean z, int i2, String str, String str2) {
        EnterpriseLicenseManager.log(new ContextInfo(Binder.getCallingUid()), "AuditLog.c");
        IAuditLog iAuditLogAsInterface = IAuditLog.Stub.asInterface(ServiceManager.getService("auditlog"));
        if (iAuditLogAsInterface != null) {
            try {
                iAuditLogAsInterface.logEventForLegacyComponents(i2, new LegacyEvent(-1, str, Integer.valueOf(i), str2, null, Boolean.valueOf(z), 2));
            } catch (RemoteException unused) {
                Log.w(TAG, "Access to auditLogger not allowed");
            }
        }
    }

    public static AuditLog createInstance(ContextInfo contextInfo, Context context) {
        return new AuditLog(contextInfo, context.getApplicationContext());
    }

    public static void e(int i, boolean z, int i2, String str, String str2) {
        EnterpriseLicenseManager.log(new ContextInfo(Binder.getCallingUid()), "AuditLog.e");
        IAuditLog iAuditLogAsInterface = IAuditLog.Stub.asInterface(ServiceManager.getService("auditlog"));
        if (iAuditLogAsInterface != null) {
            try {
                iAuditLogAsInterface.logEventForLegacyComponents(i2, new LegacyEvent(-1, str, Integer.valueOf(i), str2, null, Boolean.valueOf(z), 3));
            } catch (RemoteException unused) {
                Log.d(TAG, "Access to auditLogger not allowed");
            }
        }
    }

    public static AuditLog getInstance(Context context) {
        AuditLog auditLog;
        synchronized (mSync) {
            try {
                if (mAuditLog == null && context != null) {
                    mAuditLog = new AuditLog(new ContextInfo(Process.myUid()), context.getApplicationContext());
                }
                auditLog = mAuditLog;
            } catch (Throwable th) {
                throw th;
            }
        }
        return auditLog;
    }

    public static void n(int i, boolean z, int i2, String str, String str2) {
        EnterpriseLicenseManager.log(new ContextInfo(Binder.getCallingUid()), "AuditLog.n");
        IAuditLog iAuditLogAsInterface = IAuditLog.Stub.asInterface(ServiceManager.getService("auditlog"));
        if (iAuditLogAsInterface != null) {
            try {
                iAuditLogAsInterface.logEventForLegacyComponents(i2, new LegacyEvent(-1, str, Integer.valueOf(i), str2, null, Boolean.valueOf(z), 5));
            } catch (RemoteException unused) {
                Log.d(TAG, "Access to auditLogger not allowed");
            }
        }
    }

    public static void w(int i, boolean z, int i2, String str, String str2) {
        EnterpriseLicenseManager.log(new ContextInfo(Binder.getCallingUid()), "AuditLog.w");
        IAuditLog iAuditLogAsInterface = IAuditLog.Stub.asInterface(ServiceManager.getService("auditlog"));
        if (iAuditLogAsInterface != null) {
            try {
                iAuditLogAsInterface.logEventForLegacyComponents(i2, new LegacyEvent(-1, str, Integer.valueOf(i), str2, null, Boolean.valueOf(z), 4));
            } catch (RemoteException unused) {
                Log.d(TAG, "Access to auditLogger not allowed");
            }
        }
    }

    public boolean disableAuditLog() {
        EnterpriseLicenseManager.log(this.mContextInfo, "AuditLog.disableAuditLog");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mAuditService.disableAuditLog(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to disableAuditLog");
            return false;
        }
    }

    public boolean disableIPTablesLogging() {
        return false;
    }

    public boolean dumpLogFile(long j, long j2, String str, ParcelFileDescriptor parcelFileDescriptor) {
        EnterpriseLicenseManager.log(this.mContextInfo, "AuditLog.dumpLogFile");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mAuditService.dumpLogFile(this.mContextInfo, j, j2, str, parcelFileDescriptor);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to dumpLogFile");
            return false;
        }
    }

    public boolean enableAuditLog() {
        EnterpriseLicenseManager.log(this.mContextInfo, "AuditLog.enableAuditLog");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mAuditService.enableAuditLog(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to enableAuditLog");
            return false;
        }
    }

    public boolean enableIPTablesLogging() {
        return false;
    }

    public AuditLogRulesInfo getAuditLogRules() {
        if (getService() == null) {
            return null;
        }
        if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 14) {
            Log.i(TAG, "getAuditLogRules() : This device doesn't support this API.");
            return null;
        }
        try {
            return this.mAuditService.getAuditLogRules(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to getAuditLogFilter");
            return null;
        }
    }

    public int getCriticalLogSize() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mAuditService.getCriticalLogSize(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to get current log size");
            return 0;
        }
    }

    public int getCurrentLogFileSize() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mAuditService.getCurrentLogFileSize(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to get current log size");
            return 0;
        }
    }

    public int getMaximumLogSize() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mAuditService.getMaximumLogSize(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to get current log size");
            return 0;
        }
    }

    public final IAuditLog getService() {
        if (this.mAuditService == null) {
            this.mAuditService = IAuditLog.Stub.asInterface(ServiceManager.getService("auditlog"));
        }
        return this.mAuditService;
    }

    public boolean isAuditLogEnabled() {
        EnterpriseLicenseManager.log(this.mContextInfo, "AuditLog.isAuditLogEnabled", true);
        if (getService() == null) {
            return false;
        }
        try {
            return this.mAuditService.isAuditLogEnabled(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to isAuditLogEnabled");
            return false;
        }
    }

    public boolean isIPTablesLoggingEnabled() {
        return false;
    }

    public boolean setAuditLogRules(AuditLogRulesInfo auditLogRulesInfo) {
        EnterpriseLicenseManager.log(this.mContextInfo, "AuditLog.setAuditLogRules");
        if (getService() == null) {
            return false;
        }
        if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 14) {
            Log.i(TAG, "setAuditLogRules() : This device doesn't support this API.");
            return false;
        }
        try {
            return this.mAuditService.setAuditLogRules(this.mContextInfo, auditLogRulesInfo);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to setAuditLogFilter");
            return false;
        }
    }

    public boolean setCriticalLogSize(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "AuditLog.setCriticalLogSize");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mAuditService.setCriticalLogSize(this.mContextInfo, i);
        } catch (RemoteException unused) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "Failed to setCriticalLogSize size=", TAG);
            return false;
        }
    }

    public boolean setMaximumLogSize(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "AuditLog.setMaximumLogSize");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mAuditService.setMaximumLogSize(this.mContextInfo, i);
        } catch (RemoteException unused) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "Failed to setMaximumLogSize size=", TAG);
            return false;
        }
    }
}
