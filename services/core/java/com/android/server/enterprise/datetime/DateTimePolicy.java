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
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.Utils;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
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

/* loaded from: classes2.dex */
public class DateTimePolicy extends IDateTimePolicy.Stub implements EnterpriseServiceCallback {
  public Context mContext;
  public EdmStorageProvider mEdmStorageProvider;
  public EnterpriseDumpHelper mEnterpriseDumpHelper;
  public NtpInfo mNtpInfo;
  public EnterpriseDeviceManager mEDM = null;
  public Object mNtpInfoLock = new Object();
  public BroadcastReceiver mBroadcastReceiver =
      new BroadcastReceiver() { // from class:
                                // com.android.server.enterprise.datetime.DateTimePolicy.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          Log.d("DateTimePolicyService", intent.getAction());
          if ("android.intent.action.LOCKED_BOOT_COMPLETED".equals(intent.getAction())) {
            synchronized (DateTimePolicy.this.mNtpInfoLock) {
              if (DateTimePolicy.this.mNtpInfo != null
                  && DateTimePolicy.this.mNtpInfo.getServer() != null) {
                DateTimePolicy.this.sendBroadcastToNtpServices();
              }
            }
          }
        }
      };

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void notifyToAddSystemService(String str, IBinder iBinder) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminAdded(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onPreAdminRemoval(int i) {}

  public boolean setDateFormat(ContextInfo contextInfo, String str) {
    return true;
  }

  public DateTimePolicy(Context context) {
    this.mEdmStorageProvider = null;
    this.mContext = context;
    this.mEdmStorageProvider = new EdmStorageProvider(context);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
    this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
    this.mNtpInfo = new NtpInfo(this.mContext);
    this.mEnterpriseDumpHelper = new EnterpriseDumpHelper(this.mContext);
  }

  public final ContextInfo enforceDateTimePermission(ContextInfo contextInfo) {
    return getEDM()
        .enforceActiveAdminPermissionByContext(
            contextInfo,
            new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_DATE_TIME")));
  }

  public final ContextInfo enforceOwnerOnlyAndDateTimePermission(ContextInfo contextInfo) {
    return getEDM()
        .enforceOwnerOnlyAndActiveAdminPermission(
            contextInfo,
            new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_DATE_TIME")));
  }

  public final EnterpriseDeviceManager getEDM() {
    if (this.mEDM == null) {
      this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
    }
    return this.mEDM;
  }

  public boolean setDateTime(ContextInfo contextInfo, final long j) {
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
                                  // com.android.server.enterprise.datetime.DateTimePolicy$$ExternalSyntheticLambda5
          public final void runOrThrow() {
            SystemClock.setCurrentTimeMillis(j);
          }
        });
    return true;
  }

  public long getDateTime(ContextInfo contextInfo) {
    return Calendar.getInstance().getTimeInMillis();
  }

  public boolean setTimeZone(ContextInfo contextInfo, String str) {
    enforceOwnerOnlyAndDateTimePermission(contextInfo);
    final String validStr = getValidStr(str);
    if (validStr == null) {
      Log.d("DateTimePolicyService", "setTimeZone() has failed : Invalid input.");
      return false;
    }
    if (getAutomaticTime(contextInfo) || !isDateTimeChangeEnabled(contextInfo)) {
      Log.d("DateTimePolicyService", "setTimeZone() has failed : Not allowed by admin.");
      return false;
    }
    return ((Boolean)
            Binder.withCleanCallingIdentity(
                new FunctionalUtils
                    .ThrowingSupplier() { // from class:
                                          // com.android.server.enterprise.datetime.DateTimePolicy$$ExternalSyntheticLambda0
                  public final Object getOrThrow() {
                    Boolean lambda$setTimeZone$1;
                    lambda$setTimeZone$1 = DateTimePolicy.this.lambda$setTimeZone$1(validStr);
                    return lambda$setTimeZone$1;
                  }
                }))
        .booleanValue();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ Boolean lambda$setTimeZone$1(String str) {
    try {
      ((AlarmManager) this.mContext.getSystemService("alarm")).setTimeZone(str);
      return Boolean.TRUE;
    } catch (Exception e) {
      Log.e("DateTimePolicyService", "setTimeZone() has failed by unexpected excepion. ", e);
      return Boolean.FALSE;
    }
  }

  public String getTimeZone(ContextInfo contextInfo) {
    return Calendar.getInstance().getTimeZone().getID();
  }

  public boolean setTimeFormat(ContextInfo contextInfo, final String str) {
    final int callingOrCurrentUserId =
        Utils.getCallingOrCurrentUserId(enforceDateTimePermission(contextInfo));
    if (((IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class))
            .isValidKnoxId(callingOrCurrentUserId)
        || Utils.createContextAsUser(this.mContext, "android", 0, callingOrCurrentUserId) == null
        || str == null) {
      return false;
    }
    if (str.equals("12") || str.equals("24")) {
      return ((Boolean)
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingSupplier() { // from class:
                                            // com.android.server.enterprise.datetime.DateTimePolicy$$ExternalSyntheticLambda4
                    public final Object getOrThrow() {
                      Boolean lambda$setTimeFormat$2;
                      lambda$setTimeFormat$2 =
                          DateTimePolicy.this.lambda$setTimeFormat$2(str, callingOrCurrentUserId);
                      return lambda$setTimeFormat$2;
                    }
                  }))
          .booleanValue();
    }
    return false;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ Boolean lambda$setTimeFormat$2(String str, int i) {
    Boolean valueOf =
        Boolean.valueOf(
            Settings.System.putStringForUser(
                this.mContext.getContentResolver(), "time_12_24", str, i));
    this.mContext.sendBroadcast(new Intent("android.intent.action.TIME_SET"));
    return valueOf;
  }

  public String getTimeFormat(ContextInfo contextInfo) {
    final int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
    return (String)
        Binder.withCleanCallingIdentity(
            new FunctionalUtils
                .ThrowingSupplier() { // from class:
                                      // com.android.server.enterprise.datetime.DateTimePolicy$$ExternalSyntheticLambda3
              public final Object getOrThrow() {
                String lambda$getTimeFormat$3;
                lambda$getTimeFormat$3 =
                    DateTimePolicy.this.lambda$getTimeFormat$3(callingOrCurrentUserId);
                return lambda$getTimeFormat$3;
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ String lambda$getTimeFormat$3(int i) {
    return Settings.System.getStringForUser(this.mContext.getContentResolver(), "time_12_24", i);
  }

  public String getDateFormat(ContextInfo contextInfo) {
    return ((SimpleDateFormat) DateFormat.getDateFormat(this.mContext))
        .toLocalizedPattern()
        .replaceAll("\\byy\\b", "y")
        .replaceAll("\\bMM\\b", "M")
        .replaceAll("\\bdd\\b", "d")
        .toUpperCase();
  }

  public boolean setAutomaticTime(ContextInfo contextInfo, final boolean z) {
    final DevicePolicyManager devicePolicyManager =
        (DevicePolicyManager) this.mContext.getSystemService("device_policy");
    final ContextInfo enforceOwnerOnlyAndDateTimePermission =
        enforceOwnerOnlyAndDateTimePermission(contextInfo);
    if (isDateTimeChangeEnabled(enforceOwnerOnlyAndDateTimePermission)) {
      return ((Boolean)
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingSupplier() { // from class:
                                            // com.android.server.enterprise.datetime.DateTimePolicy$$ExternalSyntheticLambda1
                    public final Object getOrThrow() {
                      Boolean lambda$setAutomaticTime$4;
                      lambda$setAutomaticTime$4 =
                          DateTimePolicy.this.lambda$setAutomaticTime$4(
                              devicePolicyManager, z, enforceOwnerOnlyAndDateTimePermission);
                      return lambda$setAutomaticTime$4;
                    }
                  }))
          .booleanValue();
    }
    return false;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ Boolean lambda$setAutomaticTime$4(
      DevicePolicyManager devicePolicyManager, boolean z, ContextInfo contextInfo) {
    Boolean bool = Boolean.FALSE;
    if (!devicePolicyManager.getAutoTimeRequired()) {
      bool =
          Boolean.valueOf(
              Settings.Global.putInt(
                      this.mContext.getContentResolver(), "auto_time_zone", z ? 1 : 0)
                  & Boolean.valueOf(
                          Settings.Global.putInt(
                              this.mContext.getContentResolver(), "auto_time", z ? 1 : 0))
                      .booleanValue());
      if (bool.booleanValue()) {
        AuditLog.logAsUser(
            5,
            1,
            true,
            Process.myPid(),
            "DateTimePolicy",
            String.format(
                z
                    ? "Admin %d has enabled automatic time."
                    : "Admin %d has disabled automatic time.",
                Integer.valueOf(contextInfo.mCallerUid)),
            Utils.getCallingOrCurrentUserId(contextInfo));
      }
    }
    return bool;
  }

  public boolean getAutomaticTime(ContextInfo contextInfo) {
    return Settings.Global.getInt(this.mContext.getContentResolver(), "auto_time", 0) > 0;
  }

  public boolean getDaylightSavingTime(ContextInfo contextInfo) {
    return Calendar.getInstance().getTimeZone().inDaylightTime(new Date());
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminRemoved(int i) {
    int i2;
    String genericValue = this.mEdmStorageProvider.getGenericValue("ntpLastAdminUid");
    if (genericValue != null) {
      try {
        i2 = Integer.parseInt(genericValue);
      } catch (NumberFormatException unused) {
        i2 = -1;
      }
      if (i2 == i) {
        Log.v("DateTimePolicyService", "admin removed, reverting ntp info");
        synchronized (this.mNtpInfoLock) {
          resetNtpInfo();
          sendBroadcastToNtpServices();
        }
      }
    }
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void systemReady() {
    loadNtpInfo();
  }

  public boolean setDateTimeChangeEnabled(ContextInfo contextInfo, final boolean z) {
    final ContextInfo enforceOwnerOnlyAndDateTimePermission =
        enforceOwnerOnlyAndDateTimePermission(contextInfo);
    boolean putBoolean =
        this.mEdmStorageProvider.putBoolean(
            enforceOwnerOnlyAndDateTimePermission.mCallerUid, "RESTRICTION", "DateTimeEnabled", z);
    if (putBoolean) {
      final int callingOrCurrentUserId =
          Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndDateTimePermission);
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // com.android.server.enterprise.datetime.DateTimePolicy$$ExternalSyntheticLambda2
            public final void runOrThrow() {
              DateTimePolicy.lambda$setDateTimeChangeEnabled$5(
                  z, enforceOwnerOnlyAndDateTimePermission, callingOrCurrentUserId);
            }
          });
    }
    return putBoolean;
  }

  public static /* synthetic */ void lambda$setDateTimeChangeEnabled$5(
      boolean z, ContextInfo contextInfo, int i) {
    AuditLog.logAsUser(
        5,
        1,
        true,
        Process.myPid(),
        "DateTimePolicy",
        String.format(
            z
                ? "Admin %d has enabled date time changes."
                : "Admin %d has disabled date time changes.",
            Integer.valueOf(contextInfo.mCallerUid)),
        i);
  }

  public boolean isDateTimeChangeEnabled(ContextInfo contextInfo) {
    Iterator it =
        this.mEdmStorageProvider.getBooleanList("RESTRICTION", "DateTimeEnabled").iterator();
    while (it.hasNext()) {
      boolean booleanValue = ((Boolean) it.next()).booleanValue();
      if (!booleanValue) {
        return booleanValue;
      }
    }
    return true;
  }

  public boolean setNtpInfo(ContextInfo contextInfo, NtpInfo ntpInfo) {
    boolean resetNtpInfo;
    ContextInfo enforceOwnerOnlyAndDateTimePermission =
        enforceOwnerOnlyAndDateTimePermission(contextInfo);
    if (ntpInfo == null) {
      Log.e("DateTimePolicyService", "NtpInfo object should not be null!");
      return false;
    }
    synchronized (this.mNtpInfoLock) {
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
          resetNtpInfo =
              saveNtpInfoInDbAndUpdateCache(
                  server,
                  timeout,
                  pollingInterval,
                  pollingIntervalShorter,
                  maxAttempts,
                  enforceOwnerOnlyAndDateTimePermission.mCallerUid);
        }
        Log.e(
            "DateTimePolicyService",
            "invalid parameters provided, all values should be 0 or higher");
        return false;
      }
      resetNtpInfo = resetNtpInfo();
      sendBroadcastToNtpServices();
      return resetNtpInfo;
    }
  }

  public NtpInfo getNtpInfo(ContextInfo contextInfo) {
    NtpInfo ntpInfo;
    enforceOwnerOnlyAndDateTimePermission(contextInfo);
    synchronized (this.mNtpInfoLock) {
      ntpInfo = this.mNtpInfo;
    }
    return ntpInfo;
  }

  public final void loadNtpInfo() {
    synchronized (this.mNtpInfoLock) {
      this.mNtpInfo.setServer(this.mEdmStorageProvider.getGenericValue("ntpServer"));
      String genericValue = this.mEdmStorageProvider.getGenericValue("ntpMaxAttempts");
      if (genericValue != null) {
        try {
          this.mNtpInfo.setMaxAttempts(Integer.parseInt(genericValue));
        } catch (NumberFormatException unused) {
          Log.e("DateTimePolicyService", "failed to convert max attempts");
          this.mNtpInfo.setMaxAttempts(0);
        }
      } else {
        this.mNtpInfo.setMaxAttempts(0);
      }
      String genericValue2 = this.mEdmStorageProvider.getGenericValue("ntpPollInterval");
      if (genericValue2 != null) {
        try {
          this.mNtpInfo.setPollingInterval(Long.parseLong(genericValue2));
        } catch (NumberFormatException unused2) {
          Log.e("DateTimePolicyService", "failed to convert polling interval");
          this.mNtpInfo.setPollingInterval(0L);
        }
      } else {
        this.mNtpInfo.setPollingInterval(0L);
      }
      String genericValue3 = this.mEdmStorageProvider.getGenericValue("ntpPollInterShorter");
      if (genericValue3 != null) {
        try {
          this.mNtpInfo.setPollingIntervalShorter(Long.parseLong(genericValue3));
        } catch (NumberFormatException unused3) {
          Log.e("DateTimePolicyService", "failed to convert polling interval shorter");
          this.mNtpInfo.setPollingIntervalShorter(0L);
        }
      } else {
        this.mNtpInfo.setPollingIntervalShorter(0L);
      }
      String genericValue4 = this.mEdmStorageProvider.getGenericValue("ntpTimeout");
      if (genericValue4 != null) {
        try {
          this.mNtpInfo.setTimeout(Long.parseLong(genericValue4));
        } catch (NumberFormatException unused4) {
          Log.e("DateTimePolicyService", "failed to convert timeout");
          this.mNtpInfo.setTimeout(0L);
        }
      } else {
        this.mNtpInfo.setTimeout(0L);
      }
    }
  }

  public final boolean resetNtpInfo() {
    Log.v("DateTimePolicyService", "resetNtpInfo");
    return saveNtpInfoInDbAndUpdateCache(null, 0L, 0L, 0L, 0, 0);
  }

  public final boolean saveNtpInfoInDbAndUpdateCache(
      String str, long j, long j2, long j3, int i, int i2) {
    boolean putGenericValue =
        this.mEdmStorageProvider.putGenericValue("ntpServer", str)
            & this.mEdmStorageProvider.putGenericValue(
                "ntpTimeout", j == 0 ? null : String.valueOf(j))
            & this.mEdmStorageProvider.putGenericValue(
                "ntpPollInterval", j2 == 0 ? null : String.valueOf(j2))
            & this.mEdmStorageProvider.putGenericValue(
                "ntpPollInterShorter", j3 == 0 ? null : String.valueOf(j3))
            & this.mEdmStorageProvider.putGenericValue(
                "ntpMaxAttempts", i == 0 ? null : String.valueOf(i));
    this.mNtpInfo.setServer(str);
    this.mNtpInfo.setMaxAttempts(i);
    this.mNtpInfo.setTimeout(j);
    this.mNtpInfo.setPollingInterval(j2);
    this.mNtpInfo.setPollingIntervalShorter(j3);
    return this.mEdmStorageProvider.putGenericValue(
            "ntpLastAdminUid", i2 != 0 ? String.valueOf(i2) : null)
        & putGenericValue;
  }

  public final void sendBroadcastToNtpServices() {
    Binder.withCleanCallingIdentity(
        new FunctionalUtils
            .ThrowingRunnable() { // from class:
                                  // com.android.server.enterprise.datetime.DateTimePolicy$$ExternalSyntheticLambda6
          public final void runOrThrow() {
            DateTimePolicy.this.lambda$sendBroadcastToNtpServices$6();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendBroadcastToNtpServices$6() {
    this.mContext.sendBroadcast(
        new Intent("com.samsung.android.knox.intent.action.UPDATE_NTP_PARAMETERS_INTERNAL"));
  }

  public String getValidStr(String str) {
    if (str == null) {
      return null;
    }
    try {
      String trim = str.trim();
      if (trim.length() > 0) {
        return trim;
      }
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public String getNtpServer() {
    return this.mNtpInfo.getServer();
  }

  public long getNtpTimeout() {
    return this.mNtpInfo.getTimeout();
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
      printWriter.println("Permission Denial: can't dump DateTimePolicy");
    } else {
      this.mEnterpriseDumpHelper.dumpTable(
          printWriter, "RESTRICTION", new String[] {"adminUid", "DateTimeEnabled"});
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
          });
    }
  }
}
