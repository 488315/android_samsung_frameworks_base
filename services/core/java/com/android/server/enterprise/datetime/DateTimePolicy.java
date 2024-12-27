package com.android.server.enterprise.datetime;

import android.app.AlarmManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.SystemClock;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.text.format.DateFormat;
import android.util.Log;

import com.android.internal.util.FunctionalUtils;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.Utils;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.datetime.IDateTimePolicy;
import com.samsung.android.knox.datetime.NtpInfo;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public final class DateTimePolicy extends IDateTimePolicy.Stub
        implements EnterpriseServiceCallback {
    public final AnonymousClass1 mBroadcastReceiver;
    public final Context mContext;
    public final EdmStorageProvider mEdmStorageProvider;
    public final EnterpriseDumpHelper mEnterpriseDumpHelper;
    public final NtpInfo mNtpInfo;
    public EnterpriseDeviceManager mEDM = null;
    public final Object mNtpInfoLock = new Object();

    public DateTimePolicy(Context context) {
        this.mEdmStorageProvider = null;
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.enterprise.datetime.DateTimePolicy.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        Log.d("DateTimePolicyService", intent.getAction());
                        if ("android.intent.action.LOCKED_BOOT_COMPLETED"
                                .equals(intent.getAction())) {
                            synchronized (DateTimePolicy.this.mNtpInfoLock) {
                                try {
                                    NtpInfo ntpInfo = DateTimePolicy.this.mNtpInfo;
                                    if (ntpInfo != null && ntpInfo.getServer() != null) {
                                        DateTimePolicy dateTimePolicy = DateTimePolicy.this;
                                        dateTimePolicy.getClass();
                                        Binder.withCleanCallingIdentity(
                                                new DateTimePolicy$$ExternalSyntheticLambda0(
                                                        dateTimePolicy));
                                    }
                                } finally {
                                }
                            }
                        }
                    }
                };
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
        context.registerReceiver(broadcastReceiver, intentFilter);
        this.mNtpInfo = new NtpInfo(context);
        this.mEnterpriseDumpHelper = new EnterpriseDumpHelper(context);
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump DateTimePolicy");
        } else {
            this.mEnterpriseDumpHelper.dumpTable(
                    printWriter, "RESTRICTION", new String[] {"adminUid", "DateTimeEnabled"}, null);
            this.mEnterpriseDumpHelper.dumpTable(
                    printWriter,
                    "generic",
                    new String[] {
                        "ntpServer",
                        "ntpTimeout",
                        "ntpMaxAttempts",
                        "ntpPollInterval",
                        "ntpPollInterShorter",
                        "ntpLastAdminUid"
                    },
                    null);
        }
    }

    public final ContextInfo enforceOwnerOnlyAndDateTimePermission(ContextInfo contextInfo) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM.enforceOwnerOnlyAndActiveAdminPermission(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_DATE_TIME")));
    }

    public final boolean getAutomaticTime(ContextInfo contextInfo) {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "auto_time", 0) > 0;
    }

    public final String getDateFormat(ContextInfo contextInfo) {
        return ((SimpleDateFormat) DateFormat.getDateFormat(this.mContext))
                .toLocalizedPattern()
                .replaceAll("\\byy\\b", "y")
                .replaceAll("\\bMM\\b", "M")
                .replaceAll("\\bdd\\b", "d")
                .toUpperCase();
    }

    public final long getDateTime(ContextInfo contextInfo) {
        return Calendar.getInstance().getTimeInMillis();
    }

    public final boolean getDaylightSavingTime(ContextInfo contextInfo) {
        return Calendar.getInstance().getTimeZone().inDaylightTime(new Date());
    }

    public final NtpInfo getNtpInfo(ContextInfo contextInfo) {
        NtpInfo ntpInfo;
        enforceOwnerOnlyAndDateTimePermission(contextInfo);
        synchronized (this.mNtpInfoLock) {
            ntpInfo = this.mNtpInfo;
        }
        return ntpInfo;
    }

    public final String getNtpServer() {
        return this.mNtpInfo.getServer();
    }

    public final long getNtpTimeout() {
        return this.mNtpInfo.getTimeout();
    }

    public final String getTimeFormat(ContextInfo contextInfo) {
        final int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        return (String)
                Binder.withCleanCallingIdentity(
                        new FunctionalUtils
                                .ThrowingSupplier() { // from class:
                                                      // com.android.server.enterprise.datetime.DateTimePolicy$$ExternalSyntheticLambda1
                            public final Object getOrThrow() {
                                DateTimePolicy dateTimePolicy = DateTimePolicy.this;
                                return Settings.System.getStringForUser(
                                        dateTimePolicy.mContext.getContentResolver(),
                                        "time_12_24",
                                        callingOrCurrentUserId);
                            }
                        });
    }

    public final String getTimeZone(ContextInfo contextInfo) {
        return Calendar.getInstance().getTimeZone().getID();
    }

    public final boolean isDateTimeChangeEnabled(ContextInfo contextInfo) {
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "RESTRICTION", "DateTimeEnabled")
                        .iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (!booleanValue) {
                return booleanValue;
            }
        }
        return true;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        int i2;
        String genericValueAsUser =
                this.mEdmStorageProvider.getGenericValueAsUser(0, "ntpLastAdminUid");
        if (genericValueAsUser != null) {
            try {
                i2 = Integer.parseInt(genericValueAsUser);
            } catch (NumberFormatException unused) {
                i2 = -1;
            }
            if (i2 == i) {
                Log.v("DateTimePolicyService", "admin removed, reverting ntp info");
                synchronized (this.mNtpInfoLock) {
                    Log.v("DateTimePolicyService", "resetNtpInfo");
                    saveNtpInfoInDbAndUpdateCache(0, 0, 0L, 0L, 0L, null);
                    Binder.withCleanCallingIdentity(
                            new DateTimePolicy$$ExternalSyntheticLambda0(this));
                }
            }
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {}

    public final boolean saveNtpInfoInDbAndUpdateCache(
            int i, int i2, long j, long j2, long j3, String str) {
        boolean putGenericValueAsUser =
                this.mEdmStorageProvider.putGenericValueAsUser(0, "ntpServer", str)
                        & this.mEdmStorageProvider.putGenericValueAsUser(
                                0, "ntpTimeout", j == 0 ? null : String.valueOf(j))
                        & this.mEdmStorageProvider.putGenericValueAsUser(
                                0, "ntpPollInterval", j2 == 0 ? null : String.valueOf(j2))
                        & this.mEdmStorageProvider.putGenericValueAsUser(
                                0, "ntpPollInterShorter", j3 == 0 ? null : String.valueOf(j3))
                        & this.mEdmStorageProvider.putGenericValueAsUser(
                                0, "ntpMaxAttempts", i == 0 ? null : String.valueOf(i));
        this.mNtpInfo.setServer(str);
        this.mNtpInfo.setMaxAttempts(i);
        this.mNtpInfo.setTimeout(j);
        this.mNtpInfo.setPollingInterval(j2);
        this.mNtpInfo.setPollingIntervalShorter(j3);
        return this.mEdmStorageProvider.putGenericValueAsUser(
                        0, "ntpLastAdminUid", i2 == 0 ? null : String.valueOf(i2))
                & putGenericValueAsUser;
    }

    public final boolean setAutomaticTime(ContextInfo contextInfo, final boolean z) {
        final DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        final ContextInfo enforceOwnerOnlyAndDateTimePermission =
                enforceOwnerOnlyAndDateTimePermission(contextInfo);
        if (isDateTimeChangeEnabled(enforceOwnerOnlyAndDateTimePermission)) {
            return ((Boolean)
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils.ThrowingSupplier() { // from class:
                                        // com.android.server.enterprise.datetime.DateTimePolicy$$ExternalSyntheticLambda6
                                        public final Object getOrThrow() {
                                            DateTimePolicy dateTimePolicy = DateTimePolicy.this;
                                            DevicePolicyManager devicePolicyManager2 =
                                                    devicePolicyManager;
                                            boolean z2 = z;
                                            ContextInfo contextInfo2 =
                                                    enforceOwnerOnlyAndDateTimePermission;
                                            dateTimePolicy.getClass();
                                            Boolean bool = Boolean.FALSE;
                                            if (!devicePolicyManager2.getAutoTimeRequired()) {
                                                boolean putInt =
                                                        Settings.Global.putInt(
                                                                        dateTimePolicy.mContext
                                                                                .getContentResolver(),
                                                                        "auto_time_zone",
                                                                        z2 ? 1 : 0)
                                                                & Settings.Global.putInt(
                                                                        dateTimePolicy.mContext
                                                                                .getContentResolver(),
                                                                        "auto_time",
                                                                        z2 ? 1 : 0);
                                                bool = Boolean.valueOf(putInt);
                                                if (putInt) {
                                                    AuditLog.logAsUser(
                                                            5,
                                                            1,
                                                            true,
                                                            Process.myPid(),
                                                            "DateTimePolicy",
                                                            String.format(
                                                                    z2
                                                                            ? "Admin %d has enabled"
                                                                                  + " automatic"
                                                                                  + " time."
                                                                            : "Admin %d has"
                                                                                  + " disabled"
                                                                                  + " automatic"
                                                                                  + " time.",
                                                                    Integer.valueOf(
                                                                            contextInfo2
                                                                                    .mCallerUid)),
                                                            Utils.getCallingOrCurrentUserId(
                                                                    contextInfo2));
                                                }
                                            }
                                            return bool;
                                        }
                                    }))
                    .booleanValue();
        }
        return false;
    }

    public final boolean setDateFormat(ContextInfo contextInfo, String str) {
        return true;
    }

    public final boolean setDateTime(ContextInfo contextInfo, final long j) {
        ContextInfo enforceOwnerOnlyAndDateTimePermission =
                enforceOwnerOnlyAndDateTimePermission(contextInfo);
        if (getAutomaticTime(enforceOwnerOnlyAndDateTimePermission)
                || !isDateTimeChangeEnabled(enforceOwnerOnlyAndDateTimePermission)
                || j / 1000 >= 2147483647L) {
            return false;
        }
        Binder.withCleanCallingIdentity(
                new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // com.android.server.enterprise.datetime.DateTimePolicy$$ExternalSyntheticLambda4
                    public final void runOrThrow() {
                        SystemClock.setCurrentTimeMillis(j);
                    }
                });
        return true;
    }

    public final boolean setDateTimeChangeEnabled(ContextInfo contextInfo, final boolean z) {
        final ContextInfo enforceOwnerOnlyAndDateTimePermission =
                enforceOwnerOnlyAndDateTimePermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndDateTimePermission.mCallerUid,
                        z,
                        0,
                        "DateTimeEnabled");
        if (putBoolean) {
            final int callingOrCurrentUserId =
                    Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndDateTimePermission);
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                            .ThrowingRunnable() { // from class:
                                                  // com.android.server.enterprise.datetime.DateTimePolicy$$ExternalSyntheticLambda5
                        public final void runOrThrow() {
                            boolean z2 = z;
                            ContextInfo contextInfo2 = enforceOwnerOnlyAndDateTimePermission;
                            AuditLog.logAsUser(
                                    5,
                                    1,
                                    true,
                                    Process.myPid(),
                                    "DateTimePolicy",
                                    String.format(
                                            z2
                                                    ? "Admin %d has enabled date time changes."
                                                    : "Admin %d has disabled date time changes.",
                                            Integer.valueOf(contextInfo2.mCallerUid)),
                                    callingOrCurrentUserId);
                        }
                    });
        }
        return putBoolean;
    }

    public final boolean setNtpInfo(ContextInfo contextInfo, NtpInfo ntpInfo) {
        boolean saveNtpInfoInDbAndUpdateCache;
        ContextInfo enforceOwnerOnlyAndDateTimePermission =
                enforceOwnerOnlyAndDateTimePermission(contextInfo);
        if (ntpInfo == null) {
            Log.e("DateTimePolicyService", "NtpInfo object should not be null!");
            return false;
        }
        synchronized (this.mNtpInfoLock) {
            try {
                String server = ntpInfo.getServer();
                if (server != null) {
                    long timeout = ntpInfo.getTimeout();
                    long pollingInterval = ntpInfo.getPollingInterval();
                    long pollingIntervalShorter = ntpInfo.getPollingIntervalShorter();
                    int maxAttempts = ntpInfo.getMaxAttempts();
                    if (timeout >= 0
                            && pollingInterval >= 0
                            && pollingIntervalShorter >= 0
                            && maxAttempts >= 0) {
                        saveNtpInfoInDbAndUpdateCache =
                                saveNtpInfoInDbAndUpdateCache(
                                        maxAttempts,
                                        enforceOwnerOnlyAndDateTimePermission.mCallerUid,
                                        timeout,
                                        pollingInterval,
                                        pollingIntervalShorter,
                                        server);
                    }
                    Log.e(
                            "DateTimePolicyService",
                            "invalid parameters provided, all values should be 0 or higher");
                    return false;
                }
                Log.v("DateTimePolicyService", "resetNtpInfo");
                saveNtpInfoInDbAndUpdateCache =
                        saveNtpInfoInDbAndUpdateCache(0, 0, 0L, 0L, 0L, null);
                Binder.withCleanCallingIdentity(new DateTimePolicy$$ExternalSyntheticLambda0(this));
                return saveNtpInfoInDbAndUpdateCache;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setTimeFormat(ContextInfo contextInfo, final String str) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        final int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(
                        this.mEDM.enforceActiveAdminPermissionByContext(
                                contextInfo,
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_DATE_TIME"))));
        ((PersonaManagerAdapter)
                        ((IPersonaManagerAdapter)
                                AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class)))
                .getClass();
        if (SemPersonaManager.isKnoxId(callingOrCurrentUserId)
                || Utils.createContextAsUser(this.mContext, "android", 0, callingOrCurrentUserId)
                        == null
                || str == null) {
            return false;
        }
        if (str.equals("12") || str.equals("24")) {
            return ((Boolean)
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingSupplier() { // from class:
                                                                  // com.android.server.enterprise.datetime.DateTimePolicy$$ExternalSyntheticLambda2
                                        public final Object getOrThrow() {
                                            DateTimePolicy dateTimePolicy = DateTimePolicy.this;
                                            Boolean valueOf =
                                                    Boolean.valueOf(
                                                            Settings.System.putStringForUser(
                                                                    dateTimePolicy.mContext
                                                                            .getContentResolver(),
                                                                    "time_12_24",
                                                                    str,
                                                                    callingOrCurrentUserId));
                                            dateTimePolicy.mContext.sendBroadcast(
                                                    new Intent("android.intent.action.TIME_SET"));
                                            return valueOf;
                                        }
                                    }))
                    .booleanValue();
        }
        return false;
    }

    public final boolean setTimeZone(ContextInfo contextInfo, String str) {
        enforceOwnerOnlyAndDateTimePermission(contextInfo);
        final String str2 = null;
        if (str != null) {
            try {
                String trim = str.trim();
                if (trim.length() > 0) {
                    str2 = trim;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (str2 == null) {
            Log.d("DateTimePolicyService", "setTimeZone() has failed : Invalid input.");
            return false;
        }
        if (!getAutomaticTime(contextInfo) && isDateTimeChangeEnabled(contextInfo)) {
            return ((Boolean)
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils.ThrowingSupplier() { // from class:
                                        // com.android.server.enterprise.datetime.DateTimePolicy$$ExternalSyntheticLambda3
                                        public final Object getOrThrow() {
                                            DateTimePolicy dateTimePolicy = DateTimePolicy.this;
                                            String str3 = str2;
                                            dateTimePolicy.getClass();
                                            try {
                                                ((AlarmManager)
                                                                dateTimePolicy.mContext
                                                                        .getSystemService("alarm"))
                                                        .setTimeZone(str3);
                                                return Boolean.TRUE;
                                            } catch (Exception e2) {
                                                Log.e(
                                                        "DateTimePolicyService",
                                                        "setTimeZone() has failed by unexpected"
                                                            + " excepion. ",
                                                        e2);
                                                return Boolean.FALSE;
                                            }
                                        }
                                    }))
                    .booleanValue();
        }
        Log.d("DateTimePolicyService", "setTimeZone() has failed : Not allowed by admin.");
        return false;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        synchronized (this.mNtpInfoLock) {
            this.mNtpInfo.setServer(this.mEdmStorageProvider.getGenericValueAsUser(0, "ntpServer"));
            String genericValueAsUser =
                    this.mEdmStorageProvider.getGenericValueAsUser(0, "ntpMaxAttempts");
            if (genericValueAsUser != null) {
                try {
                    this.mNtpInfo.setMaxAttempts(Integer.parseInt(genericValueAsUser));
                } catch (NumberFormatException unused) {
                    Log.e("DateTimePolicyService", "failed to convert max attempts");
                    this.mNtpInfo.setMaxAttempts(0);
                }
            } else {
                this.mNtpInfo.setMaxAttempts(0);
            }
            String genericValueAsUser2 =
                    this.mEdmStorageProvider.getGenericValueAsUser(0, "ntpPollInterval");
            if (genericValueAsUser2 != null) {
                try {
                    this.mNtpInfo.setPollingInterval(Long.parseLong(genericValueAsUser2));
                } catch (NumberFormatException unused2) {
                    Log.e("DateTimePolicyService", "failed to convert polling interval");
                    this.mNtpInfo.setPollingInterval(0L);
                }
            } else {
                this.mNtpInfo.setPollingInterval(0L);
            }
            String genericValueAsUser3 =
                    this.mEdmStorageProvider.getGenericValueAsUser(0, "ntpPollInterShorter");
            if (genericValueAsUser3 != null) {
                try {
                    this.mNtpInfo.setPollingIntervalShorter(Long.parseLong(genericValueAsUser3));
                } catch (NumberFormatException unused3) {
                    Log.e("DateTimePolicyService", "failed to convert polling interval shorter");
                    this.mNtpInfo.setPollingIntervalShorter(0L);
                }
            } else {
                this.mNtpInfo.setPollingIntervalShorter(0L);
            }
            String genericValueAsUser4 =
                    this.mEdmStorageProvider.getGenericValueAsUser(0, "ntpTimeout");
            if (genericValueAsUser4 != null) {
                try {
                    this.mNtpInfo.setTimeout(Long.parseLong(genericValueAsUser4));
                } catch (NumberFormatException unused4) {
                    Log.e("DateTimePolicyService", "failed to convert timeout");
                    this.mNtpInfo.setTimeout(0L);
                }
            } else {
                this.mNtpInfo.setTimeout(0L);
            }
        }
    }
}
