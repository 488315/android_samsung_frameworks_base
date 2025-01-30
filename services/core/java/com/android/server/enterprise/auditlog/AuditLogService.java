package com.android.server.enterprise.auditlog;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.StatFs;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.os.storage.VolumeInfo;
import android.text.TextUtils;
import android.util.Log;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.common.EnterprisePermissionChecker;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.SecContentProviderUtil;
import com.android.server.enterprise.utils.Utils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.analytics.activation.DevicePolicyListener;
import com.samsung.android.knox.deviceinfo.DeviceInventory;
import com.samsung.android.knox.log.AuditLogRulesInfo;
import com.samsung.android.knox.log.IAuditLog;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class AuditLogService extends IAuditLog.Stub implements EnterpriseServiceCallback {
  public static final String[] swComponentWhitelist = {
    "KeyStore",
    "keystore",
    "AndroidKeyStoreKeyPairGeneratorSpi",
    "AndroidKeyStoreSpi",
    "OkHostnameVerifier",
    "OpenSSLRandom",
    "PKIXRevocationChecker",
    "ConscryptFileDescriptorSocket",
    "Connection",
    "CertPathValidator",
    "ecryptfs",
    "conscrypt",
    "fscrypt",
    "AndroidKeyStoreMaintenance",
    "KeyStoreSecurityLevel",
    "KeyStore2"
  };
  public BroadcastReceiver mBroadcastReceiver;
  public ContentValues mContainerOwnerCache;
  public final Context mContext;
  public EnterpriseDeviceManager mEDM;
  public final EdmStorageProvider mEdmStorageProvider;
  public final Injector mInjector;
  public volatile boolean mIsBootCompleted;
  public Map mLinkedHashMap;
  public final String mMessagePackage;
  public final Pattern mPattern;
  public BroadcastReceiver mRemovableMediaBroadcastReceiver;

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void notifyToAddSystemService(String str, IBinder iBinder) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminAdded(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void systemReady() {}

  class Injector {
    public final Context mContext;

    public boolean isNeedToRunCreateAdmins() {
      return true;
    }

    public Injector(Context context) {
      this.mContext = context;
    }

    public List storageManagerGetVolumes() {
      return ((StorageManager) this.mContext.getSystemService("storage")).getVolumes();
    }
  }

  public AuditLogService(Context context) {
    this(new Injector(context));
  }

  public AuditLogService(Injector injector) {
    this.mRemovableMediaBroadcastReceiver =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.enterprise.auditlog.AuditLogService.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.MEDIA_MOUNTED")) {
              logRemovableMediaEvents(context, intent, "Mounted");
            } else if (intent.getAction().equals("android.intent.action.MEDIA_UNMOUNTED")) {
              logRemovableMediaEvents(context, intent, "Unmounted");
            }
          }

          public final void logRemovableMediaEvents(Context context, Intent intent, String str) {
            StorageVolume storageVolume;
            Bundle extras = intent.getExtras();
            if (extras == null
                || (storageVolume =
                        (StorageVolume)
                            extras.getParcelable("android.os.storage.extra.STORAGE_VOLUME"))
                    == null
                || storageVolume.getUuid() == null) {
              return;
            }
            for (VolumeInfo volumeInfo :
                AuditLogService.this.mInjector.storageManagerGetVolumes()) {
              if (volumeInfo.getDisk() != null
                  && volumeInfo.getFsUuid() != null
                  && volumeInfo.getFsUuid().equals(storageVolume.getUuid())) {
                if (volumeInfo.getDisk().isSd()) {
                  AuditLogService.this.AuditLogger(
                      null,
                      5,
                      2,
                      true,
                      Process.myPid(),
                      "AuditLogService",
                      String.format("Removable Media Event: External SD Card %s", str));
                }
                if (volumeInfo.getDisk().isUsb()) {
                  AuditLogService.this.AuditLogger(
                      null,
                      5,
                      2,
                      true,
                      Process.myPid(),
                      "AuditLogService",
                      String.format("Removable Media Event: USB HOST MEMORY %s", str));
                }
              }
            }
          }
        };
    this.mBroadcastReceiver =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.enterprise.auditlog.AuditLogService.2
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            int identifier;
            if (intent.getAction().equals("android.intent.action.ACTION_SHUTDOWN")
                || intent.getAction().equals("android.intent.action.REBOOT")) {
              synchronized (AuditLogService.this.mLinkedHashMap) {
                Iterator it = AuditLogService.this.mLinkedHashMap.values().iterator();
                while (it.hasNext()) {
                  ((Admin) it.next()).shutdown();
                }
              }
              return;
            }
            if (intent.getAction().equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
              Log.d("AuditLogService", "ACTION_LOCKED_BOOT_COMPLETED");
              AuditLogService.this.mIsBootCompleted = true;
              synchronized (AuditLogService.this.mLinkedHashMap) {
                Iterator it2 = AuditLogService.this.mLinkedHashMap.values().iterator();
                while (it2.hasNext()) {
                  ((Admin) it2.next()).setBootCompleted(true);
                }
              }
              return;
            }
            if (intent.getAction().equals("android.intent.action.TIME_SET")) {
              AuditLogService.this.AuditLogger(
                  null,
                  5,
                  2,
                  true,
                  Process.myPid(),
                  "AuditLogService",
                  String.format(
                      "The device time has been changed. Current Time = %d",
                      Long.valueOf(System.currentTimeMillis())));
              return;
            }
            if (intent.getAction().equals(DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED)) {
              UserHandle userHandle = (UserHandle) intent.getExtra("android.intent.extra.USER");
              identifier = userHandle != null ? userHandle.getIdentifier() : 0;
              AuditLogService.this.AuditLogger(
                  null,
                  5,
                  2,
                  true,
                  Process.myPid(),
                  "AuditLogService",
                  identifier > 0
                      ? String.format(
                          "Managed Profile has been created successfully - user %d",
                          Integer.valueOf(identifier))
                      : "Managed Profile has been created successfully");
            } else if (intent
                .getAction()
                .equals(DevicePolicyListener.ACTION_PROFILE_OWNER_REMOVED)) {
              UserHandle userHandle2 = (UserHandle) intent.getExtra("android.intent.extra.USER");
              identifier = userHandle2 != null ? userHandle2.getIdentifier() : 0;
              if (identifier > 0) {
                AuditLogService.this.AuditLogger(
                    null,
                    5,
                    2,
                    true,
                    Process.myPid(),
                    "AuditLogService",
                    String.format(
                        "Managed Profile has been removed - user %d", Integer.valueOf(identifier)));
              }
            }
          }
        };
    this.mInjector = injector;
    Context context = injector.mContext;
    Objects.requireNonNull(context);
    this.mContext = context;
    this.mEdmStorageProvider = new EdmStorageProvider(context);
    this.mIsBootCompleted = false;
    this.mLinkedHashMap = Collections.synchronizedMap(new HashMap());
    this.mContainerOwnerCache = new ContentValues();
    createAdmins();
    this.mPattern = Pattern.compile("(^Admin) ([0-9]+) (.*$)");
    InformFailure.getInstance().setContext(context);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
    intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
    intentFilter.addAction("android.intent.action.REBOOT");
    intentFilter.addAction("android.intent.action.TIME_SET");
    intentFilter.addAction(DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED);
    intentFilter.addAction(DevicePolicyListener.ACTION_PROFILE_OWNER_REMOVED);
    context.registerReceiver(this.mBroadcastReceiver, intentFilter);
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("android.intent.action.MEDIA_MOUNTED");
    intentFilter2.addAction("android.intent.action.MEDIA_UNMOUNTED");
    intentFilter2.addDataScheme("file");
    context.registerReceiverAsUser(
        this.mRemovableMediaBroadcastReceiver, UserHandle.SYSTEM, intentFilter2, null, null);
    this.mMessagePackage = Utils.getMessagePackageName(context);
  }

  public final List getDeviceInfo() {
    DeviceInventory deviceInventory = getEDM().getDeviceInventory();
    ArrayList arrayList = new ArrayList();
    arrayList.add(" -----------------------------------------\n");
    arrayList.add("OEM: " + Build.MANUFACTURER);
    arrayList.add("DEVICE: " + Build.MODEL);
    arrayList.add("PLATFORM: " + Build.VERSION.RELEASE);
    arrayList.add("OS: " + deviceInventory.getDeviceOS());
    arrayList.add("OS VERSION: " + deviceInventory.getDeviceOSVersion());
    String radioVersion = Build.getRadioVersion();
    if (radioVersion != null) {
      arrayList.add("BASEBAND: " + radioVersion);
    }
    arrayList.add("DEVICE ID: " + getSerialNumberInternal());
    arrayList.add(" -----------------------------------------\n");
    return arrayList;
  }

  public final void createAdmins() {
    if (this.mInjector.isNeedToRunCreateAdmins()) {
      for (ContentValues contentValues :
          this.mEdmStorageProvider.getValues("AUDITLOG", (String[]) null, (ContentValues) null)) {
        int intValue = Integer.valueOf(contentValues.getAsString("adminUid")).intValue();
        if (contentValues.get("auditLogEnabled").equals("true")) {
          Admin admin = new Admin(intValue, this.mContext, getAdminPackageNameForUid(intValue));
          Integer asInteger = contentValues.getAsInteger("auditCriticalSize");
          if (asInteger != null) {
            admin.setCriticalLogSize(asInteger.intValue());
          }
          Integer asInteger2 = contentValues.getAsInteger("auditMaximumSize");
          if (asInteger2 != null) {
            admin.setMaximumLogSize(asInteger2.intValue());
          }
          admin.setMDMLogging(contentValues.get("auditLogMDM").equals("true"));
          Long asLong = contentValues.getAsLong("auditLogBufferSize");
          if (asLong != null) {
            admin.setBufferSize(asLong.longValue());
          }
          admin.setAuditLogRulesInfo(extractRulesFromCv(contentValues));
          synchronized (this.mLinkedHashMap) {
            this.mLinkedHashMap.put(Integer.valueOf(intValue), admin);
          }
          admin.setDeviceInfo(getDeviceInfo());
        }
      }
    }
  }

  public final AuditLogRulesInfo extractRulesFromCv(ContentValues contentValues) {
    ArrayList arrayList;
    AuditLogRulesInfo auditLogRulesInfo = new AuditLogRulesInfo();
    if (contentValues != null) {
      Integer asInteger = contentValues.getAsInteger("auditLogRuleSeverity");
      Integer asInteger2 = contentValues.getAsInteger("auditLogRuleOutcome");
      String asString = contentValues.getAsString("auditLogRuleGroups");
      ArrayList arrayList2 = null;
      if (TextUtils.isEmpty(asString)) {
        arrayList = null;
      } else {
        arrayList = new ArrayList();
        for (String str : asString.split(",")) {
          arrayList.add(Integer.valueOf(str));
        }
      }
      String asString2 = contentValues.getAsString("auditLogRuleUsers");
      if (!TextUtils.isEmpty(asString2)) {
        arrayList2 = new ArrayList();
        for (String str2 : asString2.split(",")) {
          arrayList2.add(Integer.valueOf(str2));
        }
      }
      if (asInteger != null) {
        auditLogRulesInfo.setSeverityRule(asInteger.intValue());
      }
      if (asInteger2 != null) {
        auditLogRulesInfo.setOutcomeRule(asInteger2.intValue());
      }
      auditLogRulesInfo.setGroupsRule(arrayList);
      auditLogRulesInfo.setUsersRule(arrayList2);
    }
    return auditLogRulesInfo;
  }

  public boolean isAuditLogEnabled(ContextInfo contextInfo) {
    return isAuditLogEnabledInternal(enforceAuditLogPermission(contextInfo).mCallerUid);
  }

  public boolean isAuditLogEnabledAsUser(int i) {
    Iterator it = this.mLinkedHashMap.keySet().iterator();
    while (it.hasNext()) {
      int intValue = ((Integer) it.next()).intValue();
      int userId = UserHandle.getUserId(intValue);
      if (i == -1) {
        return true;
      }
      if (getPersonaManagerAdapter().isValidKnoxId(i)) {
        if (checkOwnContainerOrSelf(i, intValue)) {
          return true;
        }
      } else if (userId == 0 || userId == i) {
        return true;
      }
    }
    return false;
  }

  public final IPersonaManagerAdapter getPersonaManagerAdapter() {
    return (IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class);
  }

  public final boolean isAuditLogEnabledInternal(int i) {
    return this.mLinkedHashMap.get(Integer.valueOf(i)) != null;
  }

  public boolean isAuditServiceRunning() {
    return !this.mLinkedHashMap.isEmpty();
  }

  public AuditLogRulesInfo getAuditLogRules(ContextInfo contextInfo) {
    int i = enforceAuditLogPermission(contextInfo).mCallerUid;
    Admin admin = (Admin) this.mLinkedHashMap.get(Integer.valueOf(i));
    if (admin != null) {
      return admin.getAuditLogRulesInfo();
    }
    return getRulesInfoFromDB(i);
  }

  public boolean setAuditLogRules(ContextInfo contextInfo, AuditLogRulesInfo auditLogRulesInfo) {
    ContextInfo enforceAuditLogPermission = enforceAuditLogPermission(contextInfo);
    int i = enforceAuditLogPermission.mCallerUid;
    if (!validateRulesParameters(auditLogRulesInfo)) {
      return false;
    }
    Admin admin = (Admin) this.mLinkedHashMap.get(Integer.valueOf(i));
    ContentValues contentValues = new ContentValues();
    if (admin != null) {
      contentValues.put("auditLogEnabled", String.valueOf(true));
    }
    StringBuilder sb = new StringBuilder();
    if (auditLogRulesInfo.getGroupsRule() != null && !auditLogRulesInfo.getGroupsRule().isEmpty()) {
      Iterator it = auditLogRulesInfo.getGroupsRule().iterator();
      while (it.hasNext()) {
        sb.append(((Integer) it.next()).toString() + ",");
      }
    }
    StringBuilder sb2 = new StringBuilder();
    if (auditLogRulesInfo.getUsersRule() != null && !auditLogRulesInfo.getUsersRule().isEmpty()) {
      Iterator it2 = auditLogRulesInfo.getUsersRule().iterator();
      while (it2.hasNext()) {
        sb2.append(((Integer) it2.next()).toString() + ",");
      }
    }
    contentValues.put("auditLogRuleOutcome", Integer.valueOf(auditLogRulesInfo.getOutcomeRule()));
    contentValues.put("auditLogRuleSeverity", Integer.valueOf(auditLogRulesInfo.getSeverityRule()));
    contentValues.put("auditLogRuleGroups", sb.toString());
    contentValues.put("auditLogRuleUsers", sb2.toString());
    ContentValues contentValues2 = new ContentValues();
    contentValues2.put("adminUid", Integer.valueOf(i));
    boolean put = this.mEdmStorageProvider.put("AUDITLOG", contentValues, contentValues2);
    if (!put) {
      InformFailure.getInstance()
          .broadcastFailure(
              "Cannot set filter on Database", admin != null ? admin.getPackageName() : "");
    }
    if (admin != null) {
      admin.setAuditLogRulesInfo(auditLogRulesInfo);
    }
    if (put) {
      AuditLoggerAsUser(
          enforceAuditLogPermission,
          5,
          2,
          true,
          Process.myPid(),
          "AuditLogService",
          "AuditLog filter rules has changed.",
          UserHandle.getUserId(i));
    }
    return put;
  }

  public final AuditLogRulesInfo getRulesInfoFromDB(int i) {
    AuditLogRulesInfo auditLogRulesInfo = new AuditLogRulesInfo();
    ContentValues contentValues = new ContentValues();
    contentValues.put("adminUid", Integer.valueOf(i));
    List values = this.mEdmStorageProvider.getValues("AUDITLOG", (String[]) null, contentValues);
    return !values.isEmpty()
        ? extractRulesFromCv((ContentValues) values.get(0))
        : auditLogRulesInfo;
  }

  public final void enforceLoggerPermission() {
    if (this.mContext.checkCallingOrSelfPermission(
            "com.samsung.android.knox.permission.KNOX_AUDIT_LOG")
        != 0) {
      throw new SecurityException(
          "Admin does not have com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
    }
  }

  public final ContextInfo enforceAuditLogPermission(ContextInfo contextInfo) {
    return getEDM()
        .enforceActiveAdminPermissionByContext(
            contextInfo,
            new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_AUDIT_LOG")));
  }

  public final EnterpriseDeviceManager getEDM() {
    if (this.mEDM == null) {
      this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
    }
    return this.mEDM;
  }

  /* JADX WARN: Removed duplicated region for block: B:26:0x00a3  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean enableAuditLog(ContextInfo contextInfo) {
    boolean z;
    ContextInfo enforceAuditLogPermission = enforceAuditLogPermission(contextInfo);
    int i = enforceAuditLogPermission.mCallerUid;
    try {
    } catch (Exception e) {
      e = e;
      e.printStackTrace();
      InformFailure.getInstance().broadcastFailure(e, getAdminPackageNameForUid(i));
      z = r0;
      if (z) {}
      return z;
    }
    if (this.mLinkedHashMap.get(Integer.valueOf(i)) != null) {
      return true;
    }
    ContentValues contentValues = new ContentValues();
    contentValues.put("auditLogEnabled", String.valueOf(true));
    boolean putValues = this.mEdmStorageProvider.putValues(i, "AUDITLOG", contentValues);
    try {
      long availableSize = setAvailableSize(i);
      r0 = availableSize >= 0 ? putValues : false;
      if (r0) {
        new ContentValues().put("adminUid", Integer.valueOf(i));
        this.mIsBootCompleted = true;
        Admin admin = new Admin(i, this.mContext, getAdminPackageNameForUid(i));
        admin.setDeviceInfo(getDeviceInfo());
        admin.setBootCompleted(this.mIsBootCompleted);
        admin.setAuditLogRulesInfo(getRulesInfoFromDB(i));
        admin.setBufferSize(availableSize);
        admin.createBubbleDirectory();
        admin.createBubbleFile();
        synchronized (this.mLinkedHashMap) {
          this.mLinkedHashMap.put(Integer.valueOf(i), admin);
        }
        SecContentProviderUtil.notifyPolicyChangesAsUser(
            this.mContext, "AuditLog/isAuditLogEnabled", UserHandle.getUserId(i));
      }
    } catch (Exception e2) {
      e = e2;
      r0 = putValues;
      e.printStackTrace();
      InformFailure.getInstance().broadcastFailure(e, getAdminPackageNameForUid(i));
      z = r0;
      if (z) {}
      return z;
    }
    z = r0;
    if (z) {
      AuditLoggerAsUser(
          enforceAuditLogPermission,
          5,
          2,
          true,
          Process.myPid(),
          "AuditLogService",
          "AuditLog status has changed to enable",
          UserHandle.getUserId(i));
    }
    return z;
  }

  public final long setAvailableSize(int i) {
    StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
    long availableBlocks = ((statFs.getAvailableBlocks() * statFs.getBlockSize()) * 5) / 100;
    if (availableBlocks >= 10485760 && availableBlocks <= 52428800) {
      if (this.mEdmStorageProvider.putLong(i, "AUDITLOG", "auditLogBufferSize", availableBlocks)) {
        return availableBlocks;
      }
    } else if (availableBlocks >= 52428800
        && this.mEdmStorageProvider.putLong(i, "AUDITLOG", "auditLogBufferSize", 52428800L)) {
      return 52428800L;
    }
    return -1L;
  }

  /* JADX WARN: Removed duplicated region for block: B:18:0x0060  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean disableAuditLog(ContextInfo contextInfo) {
    boolean z;
    ContextInfo enforceAuditLogPermission = enforceAuditLogPermission(contextInfo);
    int i = enforceAuditLogPermission.mCallerUid;
    Admin admin = (Admin) this.mLinkedHashMap.get(Integer.valueOf(i));
    if (admin != null) {
      ContentValues contentValues = new ContentValues();
      contentValues.put("auditLogEnabled", String.valueOf(false));
      ContentValues contentValues2 = new ContentValues();
      contentValues2.put("adminUid", Integer.valueOf(i));
      if (this.mEdmStorageProvider.update("AUDITLOG", contentValues, contentValues2) <= 0) {
        z = false;
        if (z) {
          AuditLoggerAsUser(
              enforceAuditLogPermission,
              5,
              2,
              true,
              Process.myPid(),
              "AuditLogService",
              "AuditLog status has changed to disable",
              UserHandle.getUserId(i));
        }
        return z;
      }
      admin.deleteAllFiles();
      synchronized (this.mLinkedHashMap) {
        this.mLinkedHashMap.remove(Integer.valueOf(i));
      }
      SecContentProviderUtil.notifyPolicyChangesAsUser(
          this.mContext, "AuditLog/isAuditLogEnabled", UserHandle.getUserId(i));
    }
    z = true;
    if (z) {}
    return z;
  }

  public final boolean checkAuditLogEnforce(String str, String str2) {
    boolean z = false;
    if (isAuditServiceRunning()) {
      if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
        Log.d("AuditLogService", "Error: Invalid auditlog parameters!");
      } else {
        int callingUid = Binder.getCallingUid();
        String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
        if (!this.mMessagePackage.equals(nameForUid) && callingUid != 1999) {
          z = true;
        }
        if (z) {
          try {
            enforceLoggerPermission();
          } catch (SecurityException unused) {
            if (!"com.android.chrome".equals(nameForUid)) {
              Log.w(
                  "AuditLogService",
                  "AuditLogger: Module does not have AuditLog permission. Package = " + nameForUid);
            }
            EnterprisePermissionChecker.getInstance(this.mContext)
                .enforceAuthorization("AuditLogService", "AuditLogger");
          }
        }
        return true;
      }
    }
    return false;
  }

  public void AuditLogger(
      ContextInfo contextInfo, int i, int i2, boolean z, int i3, String str, String str2) {
    RedactedAuditLogger(contextInfo, i, i2, z, i3, str, str2, null);
  }

  public void RedactedAuditLogger(
      ContextInfo contextInfo,
      int i,
      int i2,
      boolean z,
      int i3,
      String str,
      String str2,
      String str3) {
    if (checkAuditLogEnforce(str, str2)) {
      AuditLoggerInternal(contextInfo, i, i2, z, i3, str, str2, str3, -1, false);
    }
  }

  public void AuditLoggerAsUser(
      ContextInfo contextInfo, int i, int i2, boolean z, int i3, String str, String str2, int i4) {
    RedactedAuditLoggerAsUser(contextInfo, i, i2, z, i3, str, str2, null, i4);
  }

  public void RedactedAuditLoggerAsUser(
      ContextInfo contextInfo,
      int i,
      int i2,
      boolean z,
      int i3,
      String str,
      String str2,
      String str3,
      int i4) {
    String str4 = str2;
    if (checkAuditLogEnforce(str, str4)) {
      Matcher matcher = this.mPattern.matcher(str4);
      if (matcher.find()) {
        str4 =
            matcher.replaceFirst(
                "$1 "
                    + this.mContext
                        .getPackageManager()
                        .getNameForUid(UserHandle.getAppId(Integer.parseInt(matcher.group(2))))
                    + " $3");
      }
      AuditLoggerInternal(contextInfo, i, i2, z, i3, str, str4, str3, i4, true);
    }
  }

  public void AuditLoggerPrivileged(
      ContextInfo contextInfo, int i, int i2, boolean z, int i3, String str, String str2) {
    RedactedAuditLoggerPrivileged(contextInfo, i, i2, z, i3, str, str2, null);
  }

  public void RedactedAuditLoggerPrivileged(
      ContextInfo contextInfo,
      int i,
      int i2,
      boolean z,
      int i3,
      String str,
      String str2,
      String str3) {
    if (checkAuditPrivilegedAllowed(str, str2)) {
      AuditLoggerInternal(
          contextInfo, i, i2, z, i3, str, appendLogMessageWithCallingUser(str2), str3, -1, false);
    }
  }

  public void AuditLoggerPrivilegedAsUser(
      ContextInfo contextInfo, int i, int i2, boolean z, int i3, String str, String str2, int i4) {
    RedactedAuditLoggerPrivilegedAsUser(contextInfo, i, i2, z, i3, str, str2, null, i4);
  }

  public void RedactedAuditLoggerPrivilegedAsUser(
      ContextInfo contextInfo,
      int i,
      int i2,
      boolean z,
      int i3,
      String str,
      String str2,
      String str3,
      int i4) {
    if (checkAuditPrivilegedAllowed(str, str2)) {
      AuditLoggerInternal(
          contextInfo, i, i2, z, i3, str, appendLogMessageWithCallingUser(str2), str3, i4, true);
    }
  }

  public final boolean checkAuditPrivilegedAllowed(String str, String str2) {
    if (isAuditServiceRunning()) {
      if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
        Log.d("AuditLogService", "Error: Invalid auditlog parameters!");
      } else {
        for (String str3 : swComponentWhitelist) {
          if (str.equals(str3)) {
            return true;
          }
        }
        return false;
      }
    }
    return false;
  }

  public final String appendLogMessageWithCallingUser(String str) {
    StringBuilder sb = new StringBuilder();
    try {
      int callingUid = Binder.getCallingUid();
      int callingPid = Binder.getCallingPid();
      String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
      String processName = getProcessName(callingPid);
      sb.append(str);
      sb.append("\n[logged by: ");
      sb.append(processName);
      sb.append("/");
      sb.append(nameForUid);
      sb.append(", pid: ");
      sb.append(callingPid);
      sb.append("]");
    } catch (IOException e) {
      Log.e("AuditLogService", "Exception: " + e.getMessage());
    }
    return sb.toString();
  }

  /* JADX WARN: Code restructure failed: missing block: B:25:0x007b, code lost:

     if (r1 == null) goto L33;
  */
  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Not initialized variable reg: 2, insn: 0x0086: MOVE (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:45:0x0086 */
  /* JADX WARN: Removed duplicated region for block: B:22:0x0073  */
  /* JADX WARN: Removed duplicated region for block: B:24:0x0078  */
  /* JADX WARN: Type inference failed for: r8v0, types: [int] */
  /* JADX WARN: Type inference failed for: r8v1 */
  /* JADX WARN: Type inference failed for: r8v11, types: [java.io.InputStreamReader, java.io.Reader] */
  /* JADX WARN: Type inference failed for: r8v4 */
  /* JADX WARN: Type inference failed for: r8v5, types: [java.io.InputStreamReader] */
  /* JADX WARN: Type inference failed for: r8v6, types: [java.io.InputStreamReader] */
  /* JADX WARN: Type inference failed for: r8v9 */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final String getProcessName(int i) {
    FileInputStream fileInputStream;
    BufferedReader bufferedReader;
    BufferedReader bufferedReader2;
    IOException e;
    StringBuilder sb = new StringBuilder();
    BufferedReader bufferedReader3 = null;
    try {
      try {
        fileInputStream = new FileInputStream("/proc/" + ((int) i) + "/cmdline");
        try {
          i = new InputStreamReader(fileInputStream, "iso-8859-1");
          try {
            bufferedReader = new BufferedReader(i);
            while (true) {
              try {
                int read = bufferedReader.read();
                if (read <= 0) {
                  break;
                }
                sb.append((char) read);
              } catch (IOException e2) {
                e = e2;
                Log.e("AuditLogService", "Exception: " + e.getMessage());
                if (bufferedReader != null) {
                  bufferedReader.close();
                }
                if (i != 0) {
                  i.close();
                }
              }
            }
            bufferedReader.close();
            i.close();
          } catch (IOException e3) {
            bufferedReader = null;
            e = e3;
          } catch (Throwable th) {
            th = th;
            if (bufferedReader3 != null) {
              bufferedReader3.close();
            }
            if (i != 0) {
              i.close();
            }
            if (fileInputStream != null) {
              fileInputStream.close();
            }
            throw th;
          }
        } catch (IOException e4) {
          e = e4;
          bufferedReader = null;
          e = e;
          i = bufferedReader;
          Log.e("AuditLogService", "Exception: " + e.getMessage());
          if (bufferedReader != null) {}
          if (i != 0) {}
        } catch (Throwable th2) {
          th = th2;
          i = 0;
        }
      } catch (Throwable th3) {
        th = th3;
        bufferedReader3 = bufferedReader2;
      }
    } catch (IOException e5) {
      e = e5;
      fileInputStream = null;
      bufferedReader = null;
    } catch (Throwable th4) {
      th = th4;
      i = 0;
      fileInputStream = null;
    }
    fileInputStream.close();
    return sb.toString();
  }

  public final void AuditLoggerInternal(
      ContextInfo contextInfo,
      int i,
      int i2,
      boolean z,
      int i3,
      String str,
      String str2,
      String str3,
      int i4,
      boolean z2) {
    int i5;
    if (z2) {
      i5 = i4;
    } else {
      i5 =
          (Binder.getCallingPid() == Process.myPid() || i3 == Process.myPid())
              ? -1
              : UserHandle.getUserId(
                  contextInfo != null ? contextInfo.mCallerUid : Binder.getCallingUid());
    }
    if (this.mLinkedHashMap.isEmpty()) {
      return;
    }
    Collection<Admin> values = this.mLinkedHashMap.values();
    synchronized (this.mLinkedHashMap) {
      for (Admin admin : values) {
        String evaluateLogMessageForWpcod = evaluateLogMessageForWpcod(admin, str2, str3, i5);
        if (!TextUtils.isEmpty(evaluateLogMessageForWpcod)) {
          int userId = UserHandle.getUserId(admin.getUid());
          if ((i5 == -1
                  || ((userId == 0 && !getPersonaManagerAdapter().isValidKnoxId(i5))
                      || ((userId == i5 && userId != 0)
                          || checkOwnContainerOrSelf(i5, admin.getUid()))))
              && filterLoggingMessage(
                  admin.getAuditLogRulesInfo(),
                  i,
                  z,
                  i2,
                  str,
                  i5,
                  evaluateLogMessageForWpcod,
                  admin)) {
            StringBuilder sb = new StringBuilder();
            sb.append(new Date().getTime());
            sb.append(" ");
            sb.append(i);
            sb.append("/");
            sb.append(i2);
            sb.append("/");
            sb.append(z ? "1" : "0");
            sb.append("/");
            sb.append(i3);
            sb.append("/");
            sb.append(i5);
            sb.append("/");
            sb.append(str);
            sb.append("/");
            sb.append(evaluateLogMessageForWpcod);
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            admin.write(sb.toString());
          }
        }
      }
    }
  }

  public final String evaluateLogMessageForWpcod(Admin admin, String str, String str2, int i) {
    if (admin.isPseudoAdminOfOrganizationOwnedDevice()) {
      if (str2 == null) {
        return str;
      }
    } else if (!admin.isProfileOwnerOfOrganizationOwnedDevice() || i != -1 || str2 == null) {
      return str;
    }
    return str2;
  }

  public final boolean checkOwnContainerOrSelf(int i, int i2) {
    Integer asInteger;
    if (!getPersonaManagerAdapter().isValidKnoxId(i)) {
      return false;
    }
    if (this.mContainerOwnerCache.containsKey(String.valueOf(i))
        && (asInteger = this.mContainerOwnerCache.getAsInteger(String.valueOf(i))) != null) {
      if (asInteger.intValue() == i2) {
        return true;
      }
      int appId = UserHandle.getAppId(asInteger.intValue());
      int appId2 = UserHandle.getAppId(i2);
      int userId = UserHandle.getUserId(i2);
      if (appId == appId2 && i == userId) {
        return true;
      }
    }
    int mUMContainerOwnerUid = this.mEdmStorageProvider.getMUMContainerOwnerUid(i);
    this.mContainerOwnerCache.put(String.valueOf(i), Integer.valueOf(mUMContainerOwnerUid));
    if (mUMContainerOwnerUid == i2) {
      return true;
    }
    return UserHandle.getAppId(mUMContainerOwnerUid) == UserHandle.getAppId(i2)
        && i == UserHandle.getUserId(i2);
  }

  public int getCurrentLogFileSize(ContextInfo contextInfo) {
    Admin admin =
        (Admin)
            this.mLinkedHashMap.get(
                Integer.valueOf(enforceAuditLogPermission(contextInfo).mCallerUid));
    if (admin != null) {
      return admin.getCurrentLogFileSize();
    }
    return 0;
  }

  public boolean setCriticalLogSize(ContextInfo contextInfo, int i) {
    boolean z = false;
    if (i >= 1 && i <= 99) {
      ContextInfo enforceAuditLogPermission = enforceAuditLogPermission(contextInfo);
      int i2 = enforceAuditLogPermission.mCallerUid;
      Admin admin = (Admin) this.mLinkedHashMap.get(Integer.valueOf(i2));
      if (admin != null) {
        z = this.mEdmStorageProvider.putInt(i2, "AUDITLOG", "auditCriticalSize", i);
        if (!z) {
          InformFailure.getInstance()
              .broadcastFailure("Cannot set critcal log size on Databank", admin.getPackageName());
        }
        admin.setCriticalLogSize(i);
      }
      if (z) {
        AuditLoggerAsUser(
            enforceAuditLogPermission,
            5,
            2,
            true,
            Process.myPid(),
            "AuditLogService",
            String.format("AuditLog critical size has changed to %d", Integer.valueOf(i)),
            UserHandle.getUserId(i2));
      }
    }
    return z;
  }

  public int getCriticalLogSize(ContextInfo contextInfo) {
    Admin admin =
        (Admin)
            this.mLinkedHashMap.get(
                Integer.valueOf(enforceAuditLogPermission(contextInfo).mCallerUid));
    if (admin != null) {
      return admin.getCriticalLogSize();
    }
    return 0;
  }

  public boolean setMaximumLogSize(ContextInfo contextInfo, int i) {
    boolean z = false;
    if (i >= 1 && i <= 99) {
      ContextInfo enforceAuditLogPermission = enforceAuditLogPermission(contextInfo);
      int i2 = enforceAuditLogPermission.mCallerUid;
      Admin admin = (Admin) this.mLinkedHashMap.get(Integer.valueOf(i2));
      if (admin != null) {
        z = this.mEdmStorageProvider.putInt(i2, "AUDITLOG", "auditMaximumSize", i);
        if (!z) {
          InformFailure.getInstance()
              .broadcastFailure("Cannot set maximum log size on databank", admin.getPackageName());
        }
        admin.setMaximumLogSize(i);
      }
      if (z) {
        AuditLoggerAsUser(
            enforceAuditLogPermission,
            5,
            2,
            true,
            Process.myPid(),
            "AuditLogService",
            String.format("AuditLog maximum size has changed to %d", Integer.valueOf(i)),
            UserHandle.getUserId(i2));
      }
    }
    return z;
  }

  public int getMaximumLogSize(ContextInfo contextInfo) {
    Admin admin =
        (Admin)
            this.mLinkedHashMap.get(
                Integer.valueOf(enforceAuditLogPermission(contextInfo).mCallerUid));
    if (admin != null) {
      return admin.getMaximumLogSize();
    }
    return 0;
  }

  public synchronized boolean dumpLogFile(
      ContextInfo contextInfo,
      long j,
      long j2,
      String str,
      ParcelFileDescriptor parcelFileDescriptor) {
    if (parcelFileDescriptor != null) {
      if (parcelFileDescriptor.getFileDescriptor() != null
          && parcelFileDescriptor.getFileDescriptor().valid()) {
        if (parcelFileDescriptor.canDetectErrors()) {
          try {
            Log.v("AuditLogService", "check error");
            parcelFileDescriptor.checkError();
          } catch (Exception unused) {
            Log.v("AuditLogService", "error checking file descriptor");
            return false;
          }
        }
        try {
          new SecurityManager().checkWrite(parcelFileDescriptor.getFileDescriptor());
          Admin admin =
              (Admin)
                  this.mLinkedHashMap.get(
                      Integer.valueOf(enforceAuditLogPermission(contextInfo).mCallerUid));
          if (admin == null || admin.getDumpState()) {
            return false;
          }
          if (!admin.setFilter(str)) {
            return false;
          }
          return admin.dump(j, j2, parcelFileDescriptor);
        } catch (SecurityException unused2) {
          Log.w("AuditLogService", "can't write to file descriptor");
          return false;
        }
      }
    }
    Log.e("AuditLogService", "invalid output file");
    return false;
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onPreAdminRemoval(int i) {
    Admin admin = (Admin) this.mLinkedHashMap.get(Integer.valueOf(i));
    if (admin != null) {
      synchronized (this.mLinkedHashMap) {
        this.mLinkedHashMap.remove(Integer.valueOf(i));
      }
      admin.shutdown();
      admin.deleteAllFiles();
    }
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminRemoved(int i) {
    Admin admin = (Admin) this.mLinkedHashMap.get(Integer.valueOf(i));
    if (admin != null) {
      synchronized (this.mLinkedHashMap) {
        this.mLinkedHashMap.remove(Integer.valueOf(i));
      }
      admin.shutdown();
      admin.deleteAllFiles();
    }
  }

  public final String getAdminPackageNameForUid(int i) {
    int appId = UserHandle.getAppId(i);
    if (i != 1000 && (appId < 10000 || appId > 19999)) {
      return "com.sec.enterprise.knox.cloudmdm.smdms";
    }
    String packageNameForUid = this.mEdmStorageProvider.getPackageNameForUid(i);
    return packageNameForUid == null
        ? this.mContext.getPackageManager().getNameForUid(i)
        : packageNameForUid;
  }

  public final boolean filterLoggingMessage(
      AuditLogRulesInfo auditLogRulesInfo,
      int i,
      boolean z,
      int i2,
      String str,
      int i3,
      String str2,
      Admin admin) {
    return filterBySeverity(i, auditLogRulesInfo)
        && filterByOutcome(z, auditLogRulesInfo)
        && filterByGroup(i2, auditLogRulesInfo)
        && filterByUser(i3, auditLogRulesInfo);
  }

  public final boolean filterBySeverity(int i, AuditLogRulesInfo auditLogRulesInfo) {
    return i <= auditLogRulesInfo.getSeverityRule();
  }

  public final boolean filterByOutcome(boolean z, AuditLogRulesInfo auditLogRulesInfo) {
    return auditLogRulesInfo.getOutcomeRule() == 2
        || (z && auditLogRulesInfo.getOutcomeRule() == 1)
        || (!z && auditLogRulesInfo.getOutcomeRule() == 0);
  }

  public final boolean filterByGroup(int i, AuditLogRulesInfo auditLogRulesInfo) {
    return auditLogRulesInfo.getGroupsRule() == null
        || auditLogRulesInfo.getGroupsRule().isEmpty()
        || auditLogRulesInfo.getGroupsRule().contains(Integer.valueOf(i));
  }

  public final boolean filterByUser(int i, AuditLogRulesInfo auditLogRulesInfo) {
    return auditLogRulesInfo.getUsersRule() == null
        || auditLogRulesInfo.getUsersRule().isEmpty()
        || auditLogRulesInfo.getUsersRule().contains(Integer.valueOf(i))
        || i == -1;
  }

  public final boolean validateRulesParameters(AuditLogRulesInfo auditLogRulesInfo) {
    return auditLogRulesInfo != null
        && auditLogRulesInfo.getSeverityRule() <= 5
        && auditLogRulesInfo.getSeverityRule() >= 1
        && auditLogRulesInfo.getOutcomeRule() >= 0
        && auditLogRulesInfo.getOutcomeRule() <= 2;
  }

  public final String getSerialNumberInternal() {
    String str;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    String str2 = null;
    try {
      try {
        str = SystemProperties.get("ril.serialnumber", "");
      } catch (Exception unused) {
        Log.w("AuditLogService", "could not get property");
      }
      if (!TextUtils.isEmpty(str) && !str.equals("00000000000")) {
        str2 = str;
        return str2;
      }
      String str3 = SystemProperties.get("ro.boot.serialno", "");
      if (!TextUtils.isEmpty(str3)) {
        str2 = str3;
      }
      return str2;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }
}
