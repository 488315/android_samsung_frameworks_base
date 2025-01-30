package com.android.server.enterprise.dualdar;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;
import android.app.admin.PasswordMetrics;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.IPackageDataObserver;
import android.content.pm.PackageInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.IInstalld;
import android.sec.enterprise.auditlog.AuditLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockSettingsInternal;
import com.android.server.LocalServices;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.knox.dar.ddar.DDLog;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.pm.PersonaServiceHelper;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.dar.VirtualLockUtils;
import com.samsung.android.knox.dar.ddar.DualDarAuthUtils;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.ddar.IDualDARPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/* loaded from: classes2.dex */
public class DualDARPolicy extends IDualDARPolicy.Stub implements EnterpriseServiceCallback {
  public static final boolean IS_USER_DEVICE = SystemProperties.getBoolean("ro.product_ship", true);
  public Context context;
  public Map dualDarConfigCache;
  public EnterpriseDeviceManager edm;
  public EdmStorageProvider edmStorageProvider;
  public DualDarAuthUtils mDualDarAuthUtils;
  public final Injector mInjector;
  public VirtualLockUtils mVirtualLockUtils;

  public static long getMinDataLockTimeoutManagedDevice() {
    return BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void notifyToAddSystemService(String str, IBinder iBinder) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminAdded(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminRemoved(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onPreAdminRemoval(int i) {}

  public DualDARPolicy(Context context) {
    this(new Injector(context));
  }

  public DualDARPolicy(Injector injector) {
    this.mInjector = injector;
    this.context = injector.mContext;
    this.edmStorageProvider = injector.getEdmStorageProvider();
    this.edm = null;
    this.dualDarConfigCache = new HashMap();
    this.mDualDarAuthUtils = new DualDarAuthUtils(this.context);
    this.mVirtualLockUtils = new VirtualLockUtils();
  }

  public Bundle getConfig(ContextInfo contextInfo) {
    return getConfigInternal(enforceDualDARPermission(contextInfo).mContainerId);
  }

  public final Bundle getConfigInternal(int i) {
    return (Bundle) this.dualDarConfigCache.get(Integer.valueOf(i));
  }

  public final Bundle getConfigFromDb(int i, int i2) {
    int i3;
    String str;
    String str2;
    try {
      DDLog.m33d(
          "DualDARPolicy", "getConfigFromDb adminId : " + i + " userId : " + i2, new Object[0]);
      String string = this.edmStorageProvider.getString(i, i2, "DUAL_DAR", "dataLockTimeOut");
      if (string == null) {
        if (DualDarManager.isOnDeviceOwner(i2)) {
          DDLog.m33d("DualDARPolicy", "getConfig : DualDAR at DO case.", new Object[0]);
          string = "-1";
        } else {
          DDLog.m34e("DualDARPolicy", "getConfig : DualDAR not enabled for: " + i2, new Object[0]);
          return null;
        }
      }
      long parseLong = Long.parseLong(string);
      boolean z = true;
      try {
        i3 = this.edmStorageProvider.getInt(i, i2, "DUAL_DAR", "deRestriction");
      } catch (SettingNotFoundException unused) {
        DDLog.m34e("DualDARPolicy", "Unable to read deRestriction field", new Object[0]);
        i3 = 1;
      }
      if (SemPersonaManager.isDualDARNativeCrypto(i2)) {
        str = "";
        str2 = "";
      } else {
        str = this.edmStorageProvider.getString(i, i2, "DUAL_DAR", "clientAppPackageName");
        str2 = this.edmStorageProvider.getString(i, i2, "DUAL_DAR", "clientAppSignature");
      }
      Bundle bundle = new Bundle();
      bundle.putString("dualdar-config-client-package", str);
      bundle.putString("dualdar-config-client-signature", str2);
      if (i3 <= 0) {
        z = false;
      }
      bundle.putBoolean("dualdar-config-de-restriction", z);
      bundle.putLong("dualdar-config-datalock-timeout", parseLong);
      bundle.putParcelableArray(
          "dualdar-config-datalock-whitelistpackages", getWhitelistedAppsFromStorage(i, i2));
      return bundle;
    } catch (Exception e) {
      DDLog.m34e("DualDARPolicy", "getConfigFromDb has an exception: " + e, new Object[0]);
      e.printStackTrace();
      return null;
    }
  }

  public final AppIdentity[] getWhitelistedAppsFromStorage(int i, int i2) {
    List unFlattenPackages =
        unFlattenPackages(
            this.edmStorageProvider.getString(i, i2, "DUAL_DAR", "whiteListPkgsForDataLockState"));
    List unFlattenSignatures =
        unFlattenSignatures(
            this.edmStorageProvider.getString(
                i, i2, "DUAL_DAR", "whiteListPkgSignaturesForDataLockState"));
    ArrayList arrayList = new ArrayList();
    for (int i3 = 0; i3 < unFlattenPackages.size(); i3++) {
      arrayList.add(
          new AppIdentity(
              (String) unFlattenPackages.get(i3), (String) unFlattenSignatures.get(i3)));
    }
    return (AppIdentity[]) arrayList.toArray(new AppIdentity[0]);
  }

  public void setClientInfo(ContextInfo contextInfo, String str, String str2, String str3) {
    boolean z;
    DDLog.m33d("DualDARPolicy", "setClientInfo called, clientPkgName: " + str, new Object[0]);
    ContextInfo enforceDualDARPermission = enforceDualDARPermission(contextInfo);
    ContentValues contentValues = new ContentValues();
    if (SemPersonaManager.isDualDARNativeCrypto(enforceDualDARPermission.mContainerId)
        || str == null) {
      z = true;
    } else {
      DDLog.m33d("DualDARPolicy", "setClientInfo called, pkgName not null", new Object[0]);
      contentValues.put("clientAppPackageName", str);
      if (str2 != null) {
        DDLog.m33d("DualDARPolicy", "setClientInfo called, pkgSignature not null", new Object[0]);
        contentValues.put("clientAppSignature", str2);
      }
      DDLog.m33d(
          "DualDARPolicy",
          "setClientInfo CallerUid : "
              + enforceDualDARPermission.mCallerUid
              + ", ContainerId = "
              + enforceDualDARPermission.mContainerId,
          new Object[0]);
      z =
          this.edmStorageProvider.putValues(
              enforceDualDARPermission.mCallerUid,
              enforceDualDARPermission.mContainerId,
              "DUAL_DAR",
              contentValues);
    }
    if (z) {
      DDLog.m33d("DualDARPolicy", "Add client app and PO pkgs to whitelist", new Object[0]);
      setDefaultWhitelistedApps(enforceDualDARPermission);
      DDLog.m33d(
          "DualDARPolicy",
          "Force refresh the in-memory cache, callerUid = "
              + enforceDualDARPermission.mCallerUid
              + ", mContainerId = "
              + enforceDualDARPermission.mContainerId,
          new Object[0]);
      refreshConfig(enforceDualDARPermission.mCallerUid, enforceDualDARPermission.mContainerId);
    }
    if (SemPersonaManager.isDualDARNativeCrypto(enforceDualDARPermission.mContainerId)) {
      str3 = getDualDarVersion();
      str = "Samsung";
    }
    DDLog.m33d(
        "DualDARPolicy:DualDARAnalytics",
        "Final Logging Crypto: pkg Name :" + str + ", clientVersion: " + str3,
        new Object[0]);
    AuditLog.logAsUser(
        5,
        1,
        true,
        Process.myPid(),
        "DualDARPolicy",
        String.format(
            "Admin created DualDAR with Third Party client package: %s, client library version: %s",
            str, str3),
        enforceDualDARPermission.mContainerId);
  }

  public String getClientInfo(int i) {
    return (!this.dualDarConfigCache.containsKey(Integer.valueOf(i))
            || this.dualDarConfigCache.get(Integer.valueOf(i)) == null)
        ? ""
        : ((Bundle) this.dualDarConfigCache.get(Integer.valueOf(i)))
            .getString("dualdar-config-client-package");
  }

  public boolean clearPolicy(ContextInfo contextInfo) {
    ContextInfo enforceDualDARPermission = enforceDualDARPermission(contextInfo);
    return clearPolicyInternal(
        enforceDualDARPermission.mCallerUid, enforceDualDARPermission.mContainerId);
  }

  public final boolean clearPolicyInternal(int i, int i2) {
    try {
      boolean removeByAdmin = this.edmStorageProvider.removeByAdmin("DUAL_DAR", i, i2);
      this.dualDarConfigCache.remove(Integer.valueOf(i2));
      DDLog.m33d("DualDARPolicy", "clearPolicy policy passed? : " + removeByAdmin, new Object[0]);
      return removeByAdmin;
    } catch (Exception e) {
      DDLog.m34e("DualDARPolicy", "Exception while clearing policy, " + e, new Object[0]);
      e.printStackTrace();
      return false;
    }
  }

  public int setConfig(ContextInfo contextInfo, Bundle bundle) {
    ContextInfo enforceDualDARPermission = enforceDualDARPermission(contextInfo);
    DDLog.m33d(
        "DualDARPolicy",
        "setConfig : callingUid : " + enforceDualDARPermission.mCallerUid,
        new Object[0]);
    if (!SemPersonaManager.isDarDualEncryptionEnabled(enforceDualDARPermission.mContainerId)) {
      DDLog.m34e(
          "DualDARPolicy",
          "setConfig : DualDAR not enabled for: " + enforceDualDARPermission.mContainerId,
          new Object[0]);
      return -1;
    }
    int whitelistPackagesForDataLockedState =
        bundle.containsKey("dualdar-config-datalock-whitelistpackages")
            ? setWhitelistPackagesForDataLockedState(enforceDualDARPermission, bundle)
            : 0;
    int dataLockTimeout =
        bundle.containsKey("dualdar-config-datalock-timeout")
            ? setDataLockTimeout(enforceDualDARPermission, bundle)
            : 0;
    int deRestriction =
        bundle.containsKey("dualdar-config-de-restriction")
            ? setDeRestriction(enforceDualDARPermission, bundle)
            : 0;
    if (whitelistPackagesForDataLockedState == 0 || dataLockTimeout == 0 || deRestriction == 0) {
      refreshConfig(enforceDualDARPermission.mContainerId);
    }
    doAuditLog(enforceDualDARPermission, bundle);
    if (whitelistPackagesForDataLockedState == 0 && dataLockTimeout == 0 && deRestriction == 0) {
      return 0;
    }
    return whitelistPackagesForDataLockedState != 0
        ? whitelistPackagesForDataLockedState
        : dataLockTimeout != 0 ? dataLockTimeout : deRestriction;
  }

  public boolean setPasswordMinimumLengthForInner(ContextInfo contextInfo, int i) {
    ContextInfo enforceDualDAROnDOPermission = enforceDualDAROnDOPermission(contextInfo);
    if (!hasMinimumNumericQuality()) {
      throw new IllegalStateException(
          String.format(
              "password quality should be at least %d for setPasswordMinimumLengthForInner",
              Integer.valueOf(IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES)));
    }
    ContentValues contentValues = new ContentValues();
    contentValues.put("innerPasswordMinLen", Integer.valueOf(i));
    return this.edmStorageProvider.putValues(
        enforceDualDAROnDOPermission.mCallerUid,
        enforceDualDAROnDOPermission.mContainerId,
        "DUAL_DAR",
        contentValues);
  }

  public int getPasswordMinimumLengthForInner(ContextInfo contextInfo) {
    if (hasMinimumNumericQuality()) {
      return getPasswordMinimumLengthForInnerInternal();
    }
    return 0;
  }

  public boolean isActivePasswordSufficientForInner(ContextInfo contextInfo) {
    int userId = UserHandle.getUserId(enforceDualDAROnDOPermission(contextInfo).mCallerUid);
    PasswordMetrics passwordMetrics = getPasswordMetrics(userId);
    PasswordMetrics passwordMinimumMetrics = getPasswordMinimumMetrics(userId);
    if (passwordMetrics == null || passwordMinimumMetrics == null) {
      DDLog.m34e("DualDARPolicy", "Invalid metrics", new Object[0]);
      return false;
    }
    List validatePasswordMetrics =
        PasswordMetrics.validatePasswordMetrics(passwordMinimumMetrics, 0, passwordMetrics);
    boolean z = !validatePasswordMetrics.isEmpty();
    if (z) {
      DDLog.m33d(
          "DualDARPolicy", "Password validation errors: " + validatePasswordMetrics, new Object[0]);
    }
    return !z;
  }

  public boolean setResetPasswordTokenForInner(ContextInfo contextInfo, byte[] bArr) {
    ContextInfo enforceDualDAROnDOPermission = enforceDualDAROnDOPermission(contextInfo);
    DDLog.m33d(
        "DualDARPolicy",
        "setResetPasswordTokenForInner: " + enforceDualDAROnDOPermission.mContainerId,
        new Object[0]);
    int innerAuthUserId =
        this.mDualDarAuthUtils.getInnerAuthUserId(
            UserHandle.getUserId(enforceDualDAROnDOPermission.mCallerUid));
    if (innerAuthUserId < 0) {
      return false;
    }
    return this.mVirtualLockUtils.setResetPasswordToken(bArr, innerAuthUserId);
  }

  public boolean clearResetPasswordTokenForInner(ContextInfo contextInfo) {
    ContextInfo enforceDualDAROnDOPermission = enforceDualDAROnDOPermission(contextInfo);
    DDLog.m33d(
        "DualDARPolicy",
        "clearResetPasswordTokenForInner: " + enforceDualDAROnDOPermission.mContainerId,
        new Object[0]);
    int innerAuthUserId =
        this.mDualDarAuthUtils.getInnerAuthUserId(
            UserHandle.getUserId(enforceDualDAROnDOPermission.mCallerUid));
    if (innerAuthUserId < 0) {
      return false;
    }
    return this.mVirtualLockUtils.clearResetPasswordToken(innerAuthUserId);
  }

  public boolean isResetPasswordTokenActiveForInner(ContextInfo contextInfo) {
    int innerAuthUserId =
        this.mDualDarAuthUtils.getInnerAuthUserId(
            UserHandle.getUserId(enforceDualDAROnDOPermission(contextInfo).mCallerUid));
    if (innerAuthUserId < 0) {
      return false;
    }
    return this.mVirtualLockUtils.isResetPasswordTokenActive(innerAuthUserId);
  }

  public boolean resetPasswordWithTokenForInner(
      ContextInfo contextInfo, final String str, final byte[] bArr) {
    ContextInfo enforceDualDAROnDOPermission = enforceDualDAROnDOPermission(contextInfo);
    DDLog.m33d(
        "DualDARPolicy",
        "resetPasswordWithTokenForInner: " + enforceDualDAROnDOPermission.mContainerId,
        new Object[0]);
    final int userId = UserHandle.getUserId(enforceDualDAROnDOPermission.mCallerUid);
    final int innerAuthUserId = this.mDualDarAuthUtils.getInnerAuthUserId(userId);
    if (innerAuthUserId < 0) {
      DDLog.m34e(
          "DualDARPolicy",
          "resetPasswordWithTokenForInner: Invalid inner auth user : " + innerAuthUserId,
          new Object[0]);
      return false;
    }
    return ((Boolean)
            Binder.withCleanCallingIdentity(
                new FunctionalUtils
                    .ThrowingSupplier() { // from class:
                                          // com.android.server.enterprise.dualdar.DualDARPolicy$$ExternalSyntheticLambda0
                  public final Object getOrThrow() {
                    Boolean lambda$resetPasswordWithTokenForInner$0;
                    lambda$resetPasswordWithTokenForInner$0 =
                        DualDARPolicy.this.lambda$resetPasswordWithTokenForInner$0(
                            userId, str, bArr, innerAuthUserId);
                    return lambda$resetPasswordWithTokenForInner$0;
                  }
                }))
        .booleanValue();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ Boolean lambda$resetPasswordWithTokenForInner$0(
      int i, String str, byte[] bArr, int i2) {
    if (!new LockPatternUtils(this.mInjector.getContext()).isSecure(i)) {
      DDLog.m34e(
          "DualDARPolicy",
          "resetPasswordWithTokenForInner : Not permitted while device insecure",
          new Object[0]);
      return Boolean.FALSE;
    }
    if (str == null) {
      str = "";
    }
    boolean isNumericOnly = PasswordMetrics.isNumericOnly(str);
    PasswordMetrics passwordMinimumMetrics = getPasswordMinimumMetrics(i);
    if (str.isEmpty()) {
      DDLog.m34e(
          "DualDARPolicy",
          "resetPasswordWithTokenForInner : Not permitted for empty password",
          new Object[0]);
      return Boolean.FALSE;
    }
    if (passwordMinimumMetrics != null) {
      List validatePassword =
          PasswordMetrics.validatePassword(
              passwordMinimumMetrics, 0, isNumericOnly, str.getBytes());
      if (!validatePassword.isEmpty()) {
        DDLog.m34e(
            "DualDARPolicy",
            "Failed to reset password due to constraint violation: " + validatePassword.get(0),
            new Object[0]);
        return Boolean.FALSE;
      }
    }
    return Boolean.valueOf(this.mVirtualLockUtils.resetPasswordWithToken(str, bArr, i2));
  }

  public final boolean hasMinimumNumericQuality() {
    return getEDM().getPasswordQuality() >= 131072;
  }

  public final int getPasswordMinimumLengthForInnerInternal() {
    ArrayList intList = this.edmStorageProvider.getIntList("DUAL_DAR", "innerPasswordMinLen");
    if (intList == null || intList.size() == 0) {
      DDLog.m34e("DualDARPolicy", "Unable to read field", new Object[0]);
      return 0;
    }
    return ((Integer) Collections.max(intList)).intValue();
  }

  public final PasswordMetrics getPasswordMetrics(int i) {
    LockSettingsInternal lockSettingsInternal =
        (LockSettingsInternal) LocalServices.getService(LockSettingsInternal.class);
    if (lockSettingsInternal != null) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return lockSettingsInternal.getUserPasswordMetrics(
            this.mDualDarAuthUtils.getInnerAuthUserId(i));
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
    DDLog.m34e("DualDARPolicy", "Couldn't access service", new Object[0]);
    return null;
  }

  public final PasswordMetrics getPasswordMinimumMetrics(int i) {
    DevicePolicyManager devicePolicyManager =
        (DevicePolicyManager) this.context.getSystemService("device_policy");
    if (devicePolicyManager == null) {
      DDLog.m34e("DualDARPolicy", "Couldn't access service", new Object[0]);
      return null;
    }
    int passwordMinimumLengthForInnerInternal = getPasswordMinimumLengthForInnerInternal();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      PasswordMetrics passwordMinimumMetrics = devicePolicyManager.getPasswordMinimumMetrics(i);
      if (passwordMinimumMetrics == null) {
        DDLog.m34e("DualDARPolicy", "Failed to dpm.getPasswordMinimumMetrics()", new Object[0]);
        return null;
      }
      passwordMinimumMetrics.length =
          Math.min(256, Math.max(passwordMinimumLengthForInnerInternal, 4));
      return passwordMinimumMetrics;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void doAuditLog(ContextInfo contextInfo, Bundle bundle) {
    int i = contextInfo.mContainerId;
    SemPersonaManager.isDoEnabled(i);
    int dualDARType = PersonaServiceHelper.getDualDARType(i);
    String whitelistPkgesFromConfig = getWhitelistPkgesFromConfig(bundle);
    DDLog.m33d(
        "DualDARPolicy:DualDARAnalytics",
        "Admin created DualDAR with configuration, Type : "
            + dualDARType
            + ",  Version: "
            + getDualDarVersion()
            + ",  Lock Timeout :"
            + bundle.getLong("dualdar-config-datalock-timeout")
            + ",  Device Encryption storage access restriction: "
            + bundle.getBoolean("dualdar-config-de-restriction")
            + ",  Whitelisted Packages: "
            + whitelistPkgesFromConfig,
        new Object[0]);
    AuditLog.logAsUser(
        5,
        1,
        true,
        Process.myPid(),
        "DualDARPolicy",
        String.format(
            "Admin created DualDAR with configuration, Type: %s, Version: %s, Lock Timeout: %s,"
                + " Device Encryption storage access restriction: %s, Whitelisted Packages: %s",
            Integer.valueOf(dualDARType),
            getDualDarVersion(),
            Long.valueOf(bundle.getLong("dualdar-config-datalock-timeout")),
            Boolean.valueOf(bundle.getBoolean("dualdar-config-de-restriction")),
            whitelistPkgesFromConfig),
        i);
  }

  public final String getDualDarVersion() {
    String dualDARVersion = com.samsung.android.knox.ddar.DualDARPolicy.getDualDARVersion();
    return dualDARVersion == null ? "" : dualDARVersion;
  }

  public final String getWhitelistPkgesFromConfig(Bundle bundle) {
    ArrayList arrayList = new ArrayList();
    Parcelable[] parcelableArray =
        bundle.getParcelableArray("dualdar-config-datalock-whitelistpackages");
    if (parcelableArray != null && parcelableArray.length > 0) {
      for (Parcelable parcelable : parcelableArray) {
        AppIdentity appIdentity = (AppIdentity) parcelable;
        if (validPackageName(appIdentity)) {
          arrayList.add(appIdentity.getPackageName());
        }
      }
    }
    return flatten(arrayList);
  }

  public final int setDeRestriction(ContextInfo contextInfo, Bundle bundle) {
    boolean z = bundle.getBoolean("dualdar-config-de-restriction");
    DDLog.m33d("DualDARPolicy", "setDeRestriction called, deRestriction: " + z, new Object[0]);
    ContentValues contentValues = new ContentValues();
    contentValues.put("deRestriction", Integer.valueOf(z ? 1 : 0));
    boolean putValues =
        this.edmStorageProvider.putValues(
            contextInfo.mCallerUid, contextInfo.mContainerId, "DUAL_DAR", contentValues);
    if (putValues) {
      DDLog.m33d("DualDARPolicy", "Successfully changed DE restriction config", new Object[0]);
      logDualDarAnalytics(contextInfo, bundle, "de_access_restriction");
    } else {
      DDLog.m34e("DualDARPolicy", "Failed to write new DE restriction config", new Object[0]);
    }
    return putValues ? 0 : -3;
  }

  public final void setDefaultWhitelistedApps(ContextInfo contextInfo) {
    ArrayList arrayList = new ArrayList();
    getClientAndProfileOwnerApps(contextInfo, arrayList, new ArrayList());
    DDLog.m33d(
        "DualDARPolicy",
        "setDefaultWhitelistedApps called, whitelistAppCount: " + arrayList.size(),
        new Object[0]);
    ContentValues contentValues = new ContentValues();
    contentValues.put("whiteListPkgsForDataLockState", flattenPackages(arrayList));
    contentValues.put("whiteListPkgSignaturesForDataLockState", flattenSignatures(arrayList));
    if (this.edmStorageProvider.putValues(
        contextInfo.mCallerUid, contextInfo.mContainerId, "DUAL_DAR", contentValues)) {
      DDLog.m33d("DualDARPolicy", "successfully added whitelist packages", new Object[0]);
    } else {
      DDLog.m34e(
          "DualDARPolicy",
          "failed to write new whitelist packages to persistent storage",
          new Object[0]);
    }
  }

  public final int setWhitelistPackagesForDataLockedState(ContextInfo contextInfo, Bundle bundle) {
    int i;
    ArrayList arrayList;
    ArrayList arrayList2;
    AppIdentity[] appIdentityArr;
    ArrayList arrayList3 = new ArrayList();
    ArrayList arrayList4 = new ArrayList();
    ArrayList arrayList5 = new ArrayList();
    ArrayList arrayList6 = new ArrayList();
    getClientAndProfileOwnerApps(contextInfo, arrayList5, arrayList6);
    Parcelable[] parcelableArray =
        bundle.getParcelableArray("dualdar-config-datalock-whitelistpackages");
    if (parcelableArray != null && parcelableArray.length > 0) {
      for (Parcelable parcelable : parcelableArray) {
        AppIdentity appIdentity = (AppIdentity) parcelable;
        if (validPackageName(appIdentity) && !arrayList6.contains(appIdentity.getPackageName())) {
          arrayList3.add(appIdentity);
          arrayList4.add(appIdentity.getPackageName());
        }
      }
    }
    AppIdentity[] whitelistedAppsFromStorage =
        getWhitelistedAppsFromStorage(contextInfo.mCallerUid, contextInfo.mContainerId);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    if (whitelistedAppsFromStorage != null && whitelistedAppsFromStorage.length != 0) {
      int length = whitelistedAppsFromStorage.length;
      int i2 = 0;
      while (i2 < length) {
        AppIdentity appIdentity2 = whitelistedAppsFromStorage[i2];
        if (PersonaServiceHelper.isSystemApp(
                contextInfo.mContainerId, appIdentity2.getPackageName())
            || arrayList4.contains(appIdentity2.getPackageName())
            || arrayList6.contains(appIdentity2.getPackageName())) {
          arrayList = arrayList4;
          arrayList2 = arrayList6;
          appIdentityArr = whitelistedAppsFromStorage;
        } else {
          try {
            arrayList = arrayList4;
            try {
              arrayList2 = arrayList6;
              appIdentityArr = whitelistedAppsFromStorage;
            } catch (Exception e) {
              e = e;
              arrayList2 = arrayList6;
              appIdentityArr = whitelistedAppsFromStorage;
              e.printStackTrace();
              DDLog.m34e(
                  "DualDARPolicy",
                  "Exception in clearing user data for: "
                      + appIdentity2.getPackageName()
                      + " installed in: "
                      + contextInfo.mContainerId,
                  new Object[0]);
              i2++;
              arrayList4 = arrayList;
              arrayList6 = arrayList2;
              whitelistedAppsFromStorage = appIdentityArr;
            }
            try {
              ActivityManager.getService()
                  .clearApplicationUserData(
                      appIdentity2.getPackageName(),
                      false,
                      (IPackageDataObserver) null,
                      contextInfo.mContainerId);
            } catch (Exception e2) {
              e = e2;
              e.printStackTrace();
              DDLog.m34e(
                  "DualDARPolicy",
                  "Exception in clearing user data for: "
                      + appIdentity2.getPackageName()
                      + " installed in: "
                      + contextInfo.mContainerId,
                  new Object[0]);
              i2++;
              arrayList4 = arrayList;
              arrayList6 = arrayList2;
              whitelistedAppsFromStorage = appIdentityArr;
            }
          } catch (Exception e3) {
            e = e3;
            arrayList = arrayList4;
          }
        }
        i2++;
        arrayList4 = arrayList;
        arrayList6 = arrayList2;
        whitelistedAppsFromStorage = appIdentityArr;
      }
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
    arrayList3.addAll(arrayList5);
    DDLog.m33d(
        "DualDARPolicy",
        "setWhitelistPackagesForDataLockedState called, whitelistAppCount: " + arrayList3.size(),
        new Object[0]);
    ContentValues contentValues = new ContentValues();
    contentValues.put("whiteListPkgsForDataLockState", flattenPackages(arrayList3));
    contentValues.put("whiteListPkgSignaturesForDataLockState", flattenSignatures(arrayList3));
    boolean putValues =
        this.edmStorageProvider.putValues(
            contextInfo.mCallerUid, contextInfo.mContainerId, "DUAL_DAR", contentValues);
    if (putValues) {
      i = 0;
      DDLog.m33d(
          "DualDARPolicy",
          "successfully changed whitelist packages for datalock state",
          new Object[0]);
      logDualDarAnalytics(contextInfo, bundle, "whitelisted_apps");
    } else {
      i = 0;
      DDLog.m34e(
          "DualDARPolicy",
          "failed to write new whitelist packages to persistent storage",
          new Object[0]);
    }
    if (putValues) {
      return i;
    }
    return -2;
  }

  public final int setDataLockTimeout(ContextInfo contextInfo, Bundle bundle) {
    long j = bundle.getLong("dualdar-config-datalock-timeout");
    long j2 =
        DualDarManager.isOnDeviceOwner(contextInfo.mContainerId)
            ? BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS
            : 60000L;
    DDLog.m33d("DualDARPolicy", "setDataLockTimeout called, dataLockTimeout: " + j, new Object[0]);
    if ((j >= j2 && j < Long.MAX_VALUE) || j == -1) {
      ContentValues contentValues = new ContentValues();
      contentValues.put("dataLockTimeOut", "" + j);
      boolean putValues =
          this.edmStorageProvider.putValues(
              contextInfo.mCallerUid, contextInfo.mContainerId, "DUAL_DAR", contentValues);
      if (putValues) {
        DDLog.m33d("DualDARPolicy", "successfully changed datalock timeout", new Object[0]);
        logDualDarAnalytics(contextInfo, bundle, "datalock_timeout");
      } else {
        DDLog.m34e(
            "DualDARPolicy",
            "failed to write new datalock timeout to persistent storage",
            new Object[0]);
      }
      return putValues ? 0 : -1;
    }
    DDLog.m34e(
        "DualDARPolicy",
        "setDataLockTimeout Invalid datalock timeout. It should be in range ["
            + j2
            + ", 9223372036854775807] ms",
        new Object[0]);
    return -1;
  }

  public final boolean validPackageName(AppIdentity appIdentity) {
    return (appIdentity == null
            || appIdentity.getPackageName() == null
            || appIdentity.getPackageName().length() == 0)
        ? false
        : true;
  }

  public final void getClientAndProfileOwnerApps(ContextInfo contextInfo, List list, List list2) {
    List<PackageInfo> list3;
    String nameForUid = this.context.getPackageManager().getNameForUid(contextInfo.mCallerUid);
    if (nameForUid != null) {
      list2.add(nameForUid);
      list.add(new AppIdentity(nameForUid, ""));
    }
    try {
      list3 =
          AppGlobals.getPackageManager()
              .getPackagesHoldingPermissions(
                  new String[] {"com.samsung.android.knox.permission.KNOX_KPU_INTERNAL"},
                  0L,
                  contextInfo.mContainerId)
              .getList();
    } catch (RemoteException e) {
      DDLog.m33d(
          "DualDARPolicy",
          "RemoteException in getClientAndProfileOwnerApps while checking permissions "
              + e.getMessage(),
          new Object[0]);
      list3 = null;
    }
    if (list3 != null) {
      for (PackageInfo packageInfo : list3) {
        list2.add(packageInfo.packageName);
        list.add(new AppIdentity(packageInfo.packageName, ""));
      }
    }
    Bundle configFromDb = getConfigFromDb(contextInfo.mCallerUid, contextInfo.mContainerId);
    if (configFromDb != null) {
      String string = configFromDb.getString("dualdar-config-client-package");
      String string2 = configFromDb.getString("dualdar-config-client-signature");
      if (string != null) {
        list2.add(string);
        list.add(new AppIdentity(string, string2));
      }
    }
  }

  public final void logDualDarAnalytics(ContextInfo contextInfo, Bundle bundle, String str) {
    DDLog.m33d(
        "DualDARPolicy:DualDARAnalytics",
        "logDualDarAnalytics, userID" + contextInfo.mContainerId + ", event: " + str,
        new Object[0]);
    if (str.equals("datalock_timeout")) {
      KnoxAnalyticsData knoxAnalyticsData =
          new KnoxAnalyticsData("KNOX_DUALDAR", 1, "DATALOCK_TIMEOUT");
      long j = bundle.getLong("dualdar-config-datalock-timeout");
      knoxAnalyticsData.setProperty("datalckTmoutV", j);
      DDLog.m33d(
          "DualDARPolicy:DualDARAnalytics", "EVENT : DATALOCK_TIMEOUT TIME: " + j, new Object[0]);
      DDLog.m33d(
          "DualDARPolicy:DualDARAnalytics",
          "Payload / " + knoxAnalyticsData.toString(),
          new Object[0]);
      KnoxAnalytics.log(knoxAnalyticsData);
      return;
    }
    if (str.equals("whitelisted_apps")) {
      KnoxAnalyticsData knoxAnalyticsData2 =
          new KnoxAnalyticsData("KNOX_DUALDAR", 1, "WHITELISTED_APPS");
      String whitelistPkgesFromConfig = getWhitelistPkgesFromConfig(bundle);
      knoxAnalyticsData2.setProperty("wPkgLst", whitelistPkgesFromConfig);
      DDLog.m33d(
          "DualDARPolicy:DualDARAnalytics",
          "EVENT:WHITELISTED_APPS, WhiteListed Packages: " + whitelistPkgesFromConfig,
          new Object[0]);
      DDLog.m33d(
          "DualDARPolicy:DualDARAnalytics",
          "Payload / " + knoxAnalyticsData2.toString(),
          new Object[0]);
      KnoxAnalytics.log(knoxAnalyticsData2);
      return;
    }
    if (str.equals("de_access_restriction")) {
      DDLog.m33d(
          "DualDARPolicy:DualDARAnalytics",
          "DE Restriction Value: " + bundle.getBoolean("dualdar-config-de-restriction"),
          new Object[0]);
    }
  }

  public final List unFlattenPackages(String str) {
    ArrayList arrayList = new ArrayList();
    if (str != null && str.length() > 0) {
      StringTokenizer stringTokenizer = new StringTokenizer(str, ", ");
      while (stringTokenizer.hasMoreTokens()) {
        arrayList.add(stringTokenizer.nextToken().trim());
      }
    }
    return arrayList;
  }

  public final List unFlattenSignatures(String str) {
    ArrayList arrayList = new ArrayList();
    if (str != null && str.length() > 0) {
      StringTokenizer stringTokenizer = new StringTokenizer(str, ", ");
      while (stringTokenizer.hasMoreTokens()) {
        String trim = stringTokenizer.nextToken().trim();
        if (trim.compareTo(PackageManagerShellCommandDataLoader.STDIN_PATH) == 0) {
          trim = "";
        }
        arrayList.add(trim);
      }
    }
    return arrayList;
  }

  public final String flatten(List list) {
    StringBuilder sb = new StringBuilder();
    sb.append("");
    Iterator it = list.iterator();
    int i = 0;
    while (it.hasNext()) {
      sb.append((String) it.next());
      i++;
      if (i < list.size()) {
        sb.append(", ");
      }
    }
    return sb.toString();
  }

  public final String flattenPackages(List list) {
    ArrayList arrayList = new ArrayList();
    if (list != null) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        String packageName = ((AppIdentity) it.next()).getPackageName();
        if (packageName != null) {
          packageName = packageName.trim();
        }
        arrayList.add(packageName);
      }
    }
    return flatten(arrayList);
  }

  public final String flattenSignatures(List list) {
    ArrayList arrayList = new ArrayList();
    if (list != null) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        String signature = ((AppIdentity) it.next()).getSignature();
        String trim = signature != null ? signature.trim() : "";
        if (trim.length() == 0) {
          trim = PackageManagerShellCommandDataLoader.STDIN_PATH;
        }
        arrayList.add(trim);
      }
    }
    return flatten(arrayList);
  }

  public final EnterpriseDeviceManager getEDM() {
    if (this.edm == null) {
      this.edm = this.mInjector.getEDM();
    }
    return this.edm;
  }

  public final ContextInfo enforceDualDARPermission(ContextInfo contextInfo) {
    return getEDM()
        .enforceActiveAdminPermissionByContext(
            contextInfo,
            new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_DUAL_DAR")));
  }

  public final ContextInfo enforceDualDAROnDOPermission(ContextInfo contextInfo) {
    ContextInfo enforceDualDARPermission = enforceDualDARPermission(contextInfo);
    if (DualDarManager.isOnDeviceOwner(enforceDualDARPermission.mContainerId)) {
      return enforceDualDARPermission;
    }
    throw new SecurityException("This API works only with managed device (DO enabled)");
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void systemReady() {
    int dualDARUser = PersonaServiceHelper.getDualDARUser();
    if (dualDARUser != -1) {
      refreshConfig(dualDARUser);
    }
  }

  public final void refreshConfig(int i) {
    refreshConfig(this.edmStorageProvider.getMUMContainerOwnerUid(i), i);
  }

  public final void refreshConfig(int i, int i2) {
    DDLog.m33d("DualDARPolicy", "refreshConfig adminId : " + i + ", userId : " + i2, new Object[0]);
    Bundle configFromDb = getConfigFromDb(i, i2);
    if (configFromDb != null) {
      DDLog.m33d("DualDARPolicy", "refreshConfig cache", new Object[0]);
      this.dualDarConfigCache.put(Integer.valueOf(i2), configFromDb);
    }
  }

  public class Injector {
    public final Context mContext;

    public Injector(Context context) {
      this.mContext = context;
    }

    public Context getContext() {
      return this.mContext;
    }

    public EdmStorageProvider getEdmStorageProvider() {
      return new EdmStorageProvider(this.mContext);
    }

    public EnterpriseDeviceManager getEDM() {
      return EnterpriseDeviceManager.getInstance(this.mContext);
    }
  }
}
