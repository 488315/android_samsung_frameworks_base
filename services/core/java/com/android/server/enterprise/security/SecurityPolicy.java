package com.android.server.enterprise.security;

import android.R;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActivityManagerNative;
import android.app.IUserSwitchObserver;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.security.AndroidKeyStoreMaintenance;
import android.security.Credentials;
import android.security.IKeyChainService;
import android.security.KeyChain;
import android.security.KeyStore;
import android.service.persistentdata.PersistentDataBlockManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.MasterClearReceiver;
import com.android.server.enterprise.EnterpriseDeviceManagerService;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapter.IStorageManagerAdapter;
import com.android.server.enterprise.adapterlayer.EncryptionManagerAdapter;
import com.android.server.enterprise.application.ApplicationPolicy;
import com.android.server.enterprise.common.KeyCodeMediator;
import com.android.server.enterprise.common.KeyCodeRestrictionCallback;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.utils.CertificateUtil;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.Utils;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.ISecurityPolicy;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.keystore.CertificateInfo;
import com.samsung.android.knox.keystore.CertificateProvisioning;
import com.samsung.android.knox.localservice.SecurityPolicyInternal;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.crypto.SecretKey;

/* loaded from: classes2.dex */
public class SecurityPolicy extends ISecurityPolicy.Stub
    implements EnterpriseServiceCallback, KeyCodeRestrictionCallback {
  public static Map mBannerMap;
  public FactoryWipeReceiver factoryReceiver;
  public final SemDesktopModeManager.DesktopModeBlocker mBlocker;
  public boolean mBootCompleted;
  public BroadcastReceiver mBootReceiver;
  public Context mContext;
  public EnterpriseDeviceManager mEDM;
  public EdmStorageProvider mEdmStorageProvider;
  public SemEmergencyManager mEmergencyMgr;
  public EnterpriseDumpHelper mEnterpriseDumpHelper;
  public final Handler mHandler;
  public final Injector mInjector;
  public KeyCodeMediator mKeyCodeMediator;
  public final LocalService mLocalService;
  public boolean mMediaFormatRet;
  public HashMap mPendingGetCerificates;
  public SecureRandom mSecureRandom;
  public IStatusBarService mStatusBarService;
  public IBinder mToken;
  public ArrayList pkgNameList_allowed;
  public SecretKey secretKey;

  public boolean deleteCertificateFromUserKeystore(
      ContextInfo contextInfo, CertificateInfo certificateInfo, int i) {
    return false;
  }

  public String[] formatSelective(ContextInfo contextInfo, String[] strArr, String[] strArr2) {
    return null;
  }

  public List getCertificatesFromUserKeystore(ContextInfo contextInfo, int i) {
    return null;
  }

  @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
  public String getServiceName() {
    return "SecurityPolicy";
  }

  public boolean installCertificateToUserKeystore(
      ContextInfo contextInfo, String str, byte[] bArr, String str2, String str3, int i) {
    return false;
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void notifyToAddSystemService(String str, IBinder iBinder) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminAdded(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminRemoved(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onPreAdminRemoval(int i) {}

  public final boolean validateKeystoreParam(int i) {
    return (i & 7) != 0 && (i | 7) == 7;
  }

  class Injector {
    public final Context mContext;

    public Injector(Context context) {
      this.mContext = context;
    }

    public EdmStorageProvider getStorageProvider() {
      return new EdmStorageProvider(this.mContext);
    }

    public EnterpriseDumpHelper getEnterpriseDumpHelper() {
      return new EnterpriseDumpHelper(this.mContext);
    }

    public SemEmergencyManager getSemEmergencyManager() {
      return SemEmergencyManager.getInstance(this.mContext);
    }

    public EnterpriseDeviceManager getEDM() {
      return EnterpriseDeviceManager.getInstance(this.mContext);
    }
  }

  public SecurityPolicy(Context context) {
    this(new Injector(context));
  }

  /* JADX WARN: Multi-variable type inference failed */
  public SecurityPolicy(Injector injector) {
    this.mMediaFormatRet = false;
    this.pkgNameList_allowed = new ArrayList();
    Object[] objArr = 0;
    this.mSecureRandom = null;
    this.secretKey = null;
    this.mStatusBarService = null;
    this.mToken = new Binder();
    this.mBlocker =
        new SemDesktopModeManager
            .DesktopModeBlocker() { // from class:
                                    // com.android.server.enterprise.security.SecurityPolicy.1
          public String onBlocked() {
            return SecurityPolicy.this.mContext.getString(R.string.gadget_host_error_inflating);
          }
        };
    this.mEmergencyMgr = null;
    this.mEDM = null;
    this.mBootReceiver =
        new BroadcastReceiver() { // from class:
          // com.android.server.enterprise.security.SecurityPolicy.3
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
            Log.d("SecurityPolicy", "action = " + action + ", userId = " + intExtra);
            try {
              if (action.equals("android.intent.action.LOCKED_BOOT_COMPLETED")
                  || action.equals(
                      "com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL")) {
                SecurityPolicy.this.mBootCompleted = true;
                if (((IPersonaManagerAdapter)
                        AdapterRegistry.getAdapter(IPersonaManagerAdapter.class))
                    .isValidKnoxId(intExtra)) {
                  return;
                }
                if (SecurityPolicy.this.isRebootBannerEnabled(0)) {
                  if (context.getPackageManager().isSafeMode()) {
                    Log.i("SecurityPolicy", "Saving Device safe mode to true in generic table");
                    SecurityPolicy.this.saveDeviceBootMode(true);
                  } else if (SecurityPolicy.this.isLastBootInSafeMode()) {
                    Log.i(
                        "SecurityPolicy",
                        "Sending broadcast:"
                            + " com.samsung.android.knox.intent.action.LAST_BOOT_SAFE_MODE_INTERNAL");
                    context.sendBroadcast(
                        new Intent(
                                "com.samsung.android.knox.intent.action.LAST_BOOT_SAFE_MODE_INTERNAL")
                            .addFlags(16777216));
                    SecurityPolicy.this.saveDeviceBootMode(false);
                  }
                }
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        };
    this.mPendingGetCerificates = new HashMap();
    this.mInjector = injector;
    Context context = injector.mContext;
    Objects.requireNonNull(context);
    this.mContext = context;
    this.mHandler = new Handler();
    this.mEdmStorageProvider = injector.getStorageProvider();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
    intentFilter.addAction("com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL");
    intentFilter.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
    this.mContext.registerReceiverAsUser(
        this.mBootReceiver, UserHandle.ALL, intentFilter, null, null);
    mBannerMap = new HashMap();
    this.pkgNameList_allowed.add("com.samsung.android.email.provider");
    this.mEnterpriseDumpHelper = injector.getEnterpriseDumpHelper();
    Log.d("SecurityPolicy", "SEC_PRODUCT_FEATURE_COMMON_SUPPORT_KNOX_DESKTOP is true");
    SemDesktopModeManager semDesktopModeManager =
        (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
    if (semDesktopModeManager != null) {
      semDesktopModeManager.registerListener(
          new SemDesktopModeManager
              .DesktopModeListener() { // from class:
                                       // com.android.server.enterprise.security.SecurityPolicy.2
            public void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
              if (semDesktopModeState.state == 20 && semDesktopModeState.enabled == 3) {
                Log.d("SecurityPolicy", "listener - Dex Enabling");
                if (SecurityPolicy.this.isDodBannerVisibleAsUser(0)) {
                  SecurityPolicy.this.registerDexBlocker();
                }
              }
            }
          });
    }
    try {
      ActivityManagerNative.getDefault()
          .registerUserSwitchObserver(new UserSwitchObserver(), getClass().getSimpleName());
    } catch (RemoteException e) {
      Slog.w("SecurityPolicy", "Exception during register UserSwitchObserver ", e);
    }
    this.mEmergencyMgr = this.mInjector.getSemEmergencyManager();
    if (EnterpriseDeviceManagerService.getInstance().getFirmwareUpgrade()
        && isRebootBannerEnabled(0)) {
      addBannerAppToBatteryOptimizationWhitelist(new ContextInfo(), true);
    }
    LocalService localService = new LocalService();
    this.mLocalService = localService;
    LocalServices.addService(SecurityPolicyInternal.class, localService);
  }

  public final EnterpriseDeviceManager getEDM() {
    if (this.mEDM == null) {
      this.mEDM = this.mInjector.getEDM();
    }
    return this.mEDM;
  }

  public final FactoryWipeReceiver getFactoryReceiver() {
    if (this.factoryReceiver == null) {
      this.factoryReceiver = new FactoryWipeReceiver();
    }
    return this.factoryReceiver;
  }

  public final synchronized IStatusBarService getStatusBarService() {
    if (this.mStatusBarService == null) {
      IStatusBarService asInterface =
          IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
      this.mStatusBarService = asInterface;
      if (asInterface == null) {
        Log.d("SecurityPolicy", "warning: no STATUS_BAR_SERVICE");
      }
    }
    return this.mStatusBarService;
  }

  public void onKeyguardLaunched() {
    enforceOnlySecurityPermission(new ContextInfo(Binder.getCallingUid()));
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        if (isRebootBannerEnabled(0)) {
          startBannerService(0);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final class UserSwitchObserver extends IUserSwitchObserver.Stub {
    public void onBeforeUserSwitching(int i) {}

    public void onForegroundProfileSwitch(int i) {}

    public void onLockedBootComplete(int i) {}

    public void onUserSwitchComplete(int i) {}

    public void onUserSwitching(int i, IRemoteCallback iRemoteCallback) {}

    public UserSwitchObserver() {}
  }

  public final ContextInfo enforceSecurityPermission(ContextInfo contextInfo) {
    return getEDM()
        .enforceActiveAdminPermissionByContext(
            contextInfo,
            new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SECURITY")));
  }

  public final ContextInfo enforceCertificateProvisioningPermission(ContextInfo contextInfo) {
    return getEDM()
        .enforceActiveAdminPermissionByContext(
            contextInfo,
            new ArrayList(
                Arrays.asList(
                    "com.samsung.android.knox.permission.KNOX_SECURITY",
                    "com.samsung.android.knox.permission.KNOX_CERT_PROVISIONING")));
  }

  public final ContextInfo enforceOnlySecurityPermission(ContextInfo contextInfo) {
    return getEDM()
        .enforcePermissionByContext(
            contextInfo,
            new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SECURITY")));
  }

  public final ContextInfo enforceOnlyKnoxInternalPermission(ContextInfo contextInfo) {
    return getEDM()
        .enforcePermissionByContext(
            contextInfo,
            new ArrayList(
                Arrays.asList("com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION")));
  }

  public final ContextInfo enforceOnlyCertProvisioningPermission(ContextInfo contextInfo) {
    return getEDM()
        .enforcePermissionByContext(
            contextInfo,
            new ArrayList(
                Arrays.asList("com.samsung.android.knox.permission.KNOX_CERT_PROVISIONING")));
  }

  public final ContextInfo enforceOwnerOnlyAndSecurityPermission(ContextInfo contextInfo) {
    return getEDM()
        .enforceOwnerOnlyAndActiveAdminPermission(
            contextInfo,
            new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SECURITY")));
  }

  public final ContextInfo enforceOwnerOnlyAndCertProvisioningPermission(ContextInfo contextInfo) {
    return getEDM()
        .enforceOwnerOnlyAndActiveAdminPermission(
            contextInfo,
            new ArrayList(
                Arrays.asList("com.samsung.android.knox.permission.KNOX_CERT_PROVISIONING")));
  }

  public final ContextInfo enforceAdminPermissionIfCallerInCertWhiteList(
      ContextInfo contextInfo, int i) {
    ContextInfo adminContextIfCallerInCertWhiteList =
        getEDM()
            .getAdminContextIfCallerInCertWhiteList(
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SECURITY")));
    if (adminContextIfCallerInCertWhiteList == null) {
      if ((i & 2) != 0) {
        return enforceOwnerOnlyAndCertProvisioningPermission(contextInfo);
      }
      return enforceCertificateProvisioningPermission(contextInfo);
    }
    if ((i & 2) == 0 || UserHandle.getUserId(adminContextIfCallerInCertWhiteList.mCallerUid) == 0) {
      return adminContextIfCallerInCertWhiteList;
    }
    throw new SecurityException("Operation supported only on owner space");
  }

  public boolean startBannerService(int i) {
    SemEmergencyManager semEmergencyManager = this.mEmergencyMgr;
    boolean z = false;
    if (semEmergencyManager != null) {
      if (semEmergencyManager.isEmergencyMode() && i != 0) {
        Log.d("SecurityPolicy", "startBannerService() emergency mode on and user not owner :" + i);
        return false;
      }
    } else {
      Log.d("SecurityPolicy", "startBannerService() emergency service is null");
    }
    if (i == 0 || mBannerMap.containsKey(Integer.valueOf(i))) {
      Intent intent = new Intent();
      intent.setAction("android.intent.action.MAIN");
      intent.addCategory("android.intent.category.LAUNCHER");
      intent.setClassName(
          KnoxCustomManagerService.KNOX_PP_AGENT_PKG_NAME, "com.samsung.android.mdm.DodBanner");
      if (this.mContext.startServiceAsUser(intent, new UserHandle(i)) != null) {
        z = true;
      }
    }
    if (!z) {
      Log.d("SecurityPolicy", "startBannerService() failed. userId = " + i + ", ret = " + z);
    }
    return z;
  }

  public final String getValidStr(String str) {
    if (str == null) {
      return null;
    }
    try {
      String trim = str.trim();
      if (trim.length() > 0) {
        return trim;
      }
      return null;
    } catch (Exception unused) {
      Log.w("SecurityPolicy", "is string valid?");
      return null;
    }
  }

  public void installCertificateWithType(ContextInfo contextInfo, String str, byte[] bArr) {
    enforceOwnerOnlyAndCertProvisioningPermission(contextInfo);
    String validStr = getValidStr(str);
    if (validStr == null || bArr == null || bArr.length <= 0) {
      return;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        Intent intent = new Intent("android.credentials.INSTALL");
        intent.addFlags(268435456);
        intent.putExtra("senderpackagename", "SecurityPolicy");
        intent.putExtra(validStr, bArr);
        this.mContext.startActivity(intent);
      } catch (ActivityNotFoundException e) {
        Log.d("SecurityPolicy", "::installCertificateWithType() : " + e.toString());
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void installCertificatesFromSdCard(ContextInfo contextInfo) {
    enforceOwnerOnlyAndCertProvisioningPermission(contextInfo);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        Intent intent = new Intent("android.credentials.INSTALL");
        intent.addFlags(268435456);
        intent.putExtra("senderpackagename", "SecurityPolicy");
        this.mContext.startActivity(intent);
      } catch (ActivityNotFoundException e) {
        Log.d("SecurityPolicy", "::installCertificatesFromSdCard() : " + e.toString());
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public class ResponseHandler extends Handler {
    public ResponseHandler() {}

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      super.handleMessage(message);
      if (message.what == 1) {
        if (((Boolean) message.obj).booleanValue()) {
          SecurityPolicy securityPolicy = SecurityPolicy.this;
          securityPolicy.factoryReceiver = securityPolicy.getFactoryReceiver();
          SecurityPolicy.this.mContext.unregisterReceiver(SecurityPolicy.this.factoryReceiver);
          Log.d("SecurityPolicy", "successful unregister of Broadcast Receiver ");
          return;
        }
        Log.d("SecurityPolicy", "message not send from Broadcast Receiver ");
        return;
      }
      Log.d("SecurityPolicy", "unknown msg type " + message.what);
    }
  }

  public final IStorageManagerAdapter getStorageAdapter() {
    return (IStorageManagerAdapter) AdapterRegistry.getAdapter(IStorageManagerAdapter.class);
  }

  public final boolean formatExternalStorageCard() {
    try {
      ((StorageManager) this.mContext.getSystemService("storage")).wipeAdoptableDisks();
      final Object obj = new Object();
      BroadcastReceiver broadcastReceiver =
          new BroadcastReceiver() { // from class:
                                    // com.android.server.enterprise.security.SecurityPolicy.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
              if (intent.getAction().equals("android.intent.action.MEDIA_MOUNTED")) {
                synchronized (obj) {
                  try {
                    SecurityPolicy.this.mMediaFormatRet = true;
                    obj.notify();
                  } catch (IllegalMonitorStateException unused) {
                    Log.w("SecurityPolicy", "formatStorageCard - IllegalMonitorStateException");
                  }
                }
              }
            }
          };
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
      intentFilter.addDataScheme("file");
      this.mContext.registerReceiver(broadcastReceiver, intentFilter);
      synchronized (obj) {
        try {
          obj.wait(7000L);
        } catch (InterruptedException unused) {
          Log.w("SecurityPolicy", "formatStorageCard - InterruptedException");
        }
      }
      this.mContext.unregisterReceiver(broadcastReceiver);
    } catch (Exception unused2) {
      Log.w("SecurityPolicy", "formatStorageCard fail");
    }
    return this.mMediaFormatRet;
  }

  public void setInternalStorageEncryption(ContextInfo contextInfo, boolean z) {
    DevicePolicyManager devicePolicyManager;
    enforceOwnerOnlyAndSecurityPermission(contextInfo);
    if (this.mContext == null) {
      return;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
      } catch (Exception unused) {
        Log.w("SecurityPolicy", "is Internal Storage Encrypted?");
      }
      if (!z && devicePolicyManager.getStorageEncryption(null)) {
        Log.d("SecurityPolicy", "SD Encryption enabled by some other admin cannot decrypt");
        return;
      }
      if (!z && !isInternalStorageEncrypted(contextInfo)) {
        Log.w("SecurityPolicy", "setInternalStorageEncryption : Not encrypted");
        return;
      }
      if (z && isInternalStorageEncrypted(contextInfo)) {
        Log.w("SecurityPolicy", "setInternalStorageEncryption : device is already encrypted");
        return;
      }
      Log.d("SecurityPolicy", "setInternalStorageEncryption : Launching Encrption activity");
      if (z) {
        if (isInternalStorageEncryptedbyDefaultKey(contextInfo)) {
          Intent intent = new Intent("android.app.action.START_CRYPT_INTERSTITIAL");
          intent.addFlags(268435456);
          this.mContext.startActivity(intent);
        } else {
          Intent intent2 = new Intent("android.app.action.START_ENCRYPTION");
          intent2.addFlags(268435456);
          this.mContext.startActivity(intent2);
        }
        AuditLog.logAsUser(
            5,
            1,
            true,
            Process.myPid(),
            "SecurityPolicy",
            String.format(
                "Admin %d has requested encryption of internal storage",
                Integer.valueOf(contextInfo.mCallerUid)),
            UserHandle.getUserId(contextInfo.mCallerUid));
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setExternalStorageEncryption(ContextInfo contextInfo, boolean z) {
    enforceOwnerOnlyAndSecurityPermission(contextInfo);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        EncryptionManagerAdapter encryptionManagerAdapter =
            EncryptionManagerAdapter.getInstance(this.mContext);
        if (encryptionManagerAdapter.isEncryptionFeatureEnabled()) {
          if (!z && encryptionManagerAdapter.getRequireStorageCardEncryption()) {
            Log.d("SecurityPolicy", "SD Encryption enabled by some other admin cannot decrypt");
            return;
          } else if (z) {
            encryptionManagerAdapter.enableStorageCardEncryptionPolicy();
          } else {
            encryptionManagerAdapter.disableStorageCardEncryptionPolicy();
          }
        }
        if (z) {
          AuditLog.logAsUser(
              5,
              1,
              true,
              Process.myPid(),
              "SecurityPolicy",
              String.format(
                  "Admin %d has requested encryption of external storage",
                  Integer.valueOf(contextInfo.mCallerUid)),
              UserHandle.getUserId(contextInfo.mCallerUid));
        }
      } catch (Exception unused) {
        Log.w("SecurityPolicy", "is External Storage Encrypted?");
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean isInternalStorageEncrypted(ContextInfo contextInfo) {
    enforceOwnerOnlyAndSecurityPermission(contextInfo);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      int storageEncryptionStatus =
          ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
              .getStorageEncryptionStatus();
      if (storageEncryptionStatus != 3 && storageEncryptionStatus != 5) {
        return false;
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return true;
    } catch (Exception unused) {
      Log.w("SecurityPolicy", "is Internal Storage Encrypted ?");
      return false;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean isExternalStorageEncrypted(ContextInfo contextInfo) {
    enforceOwnerOnlyAndSecurityPermission(contextInfo);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        return EncryptionManagerAdapter.getInstance(this.mContext).isStorageCardEncrypted();
      } catch (Exception unused) {
        Log.w("SecurityPolicy", "is External Storage Encrypted ?");
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setRequireDeviceEncryption(
      ContextInfo contextInfo, ComponentName componentName, boolean z) {
    enforceOwnerOnlyAndSecurityPermission(contextInfo);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
            .setStorageEncryption(componentName, z);
      } catch (Exception e) {
        Log.w("SecurityPolicy", "setRequireDeviceEncryption Ex" + e.getMessage());
        e.printStackTrace();
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean getRequireDeviceEncryption(ContextInfo contextInfo, ComponentName componentName) {
    enforceOwnerOnlyAndSecurityPermission(contextInfo);
    try {
      return ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
          .getStorageEncryption(componentName);
    } catch (Exception e) {
      Log.w("SecurityPolicy", "getRequireDeviceEncryption Ex" + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public void setRequireStorageCardEncryption(
      ContextInfo contextInfo, ComponentName componentName, boolean z) {
    enforceOwnerOnlyAndSecurityPermission(contextInfo);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
            .semSetRequireStorageCardEncryption(componentName, z, contextInfo.mParent);
      } catch (Exception e) {
        Log.w("SecurityPolicy", "setRequireStorageCardEncryption Ex" + e.getMessage());
        e.printStackTrace();
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean getRequireStorageCardEncryption(
      ContextInfo contextInfo, ComponentName componentName) {
    enforceOwnerOnlyAndSecurityPermission(contextInfo);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        return ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
            .semGetRequireStorageCardEncryption(componentName, contextInfo.mParent);
      } catch (Exception e) {
        Log.w("SecurityPolicy", "getRequireStorageCardEncryption Ex" + e.getMessage());
        e.printStackTrace();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public List getSystemCertificates(ContextInfo contextInfo) {
    return getSystemCertificatesAsUser(
        false,
        UserHandle.getUserId(enforceCertificateProvisioningPermission(contextInfo).mCallerUid));
  }

  public final List getNativeInstalledCertificates(String str, int i) {
    return getNativeInstalledCertificatesAsUser(str, i, 0);
  }

  public final List getNativeInstalledCertificatesAsUser(String str, int i, int i2) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    int i3 = i == 4 ? -1 : 1010;
    CertificateUtil.KeyChainCRUD keyChainCRUD = null;
    try {
      CertificateUtil.KeyChainCRUD keyChainCRUD2 =
          new CertificateUtil.KeyChainCRUD(this.mContext, i2);
      try {
        String[] listAliases = keyChainCRUD2.listAliases(str, i3);
        if (listAliases == null) {
          List list = Collections.EMPTY_LIST;
          keyChainCRUD2.disconnect();
          Binder.restoreCallingIdentity(clearCallingIdentity);
          return list;
        }
        ArrayList arrayList = new ArrayList();
        for (String str2 : listAliases) {
          byte[] bArr = keyChainCRUD2.get(str2, str, i3);
          if (bArr != null) {
            for (X509Certificate x509Certificate : CertificateUtil.toCertificates(bArr)) {
              if (x509Certificate != null) {
                CertificateInfo certificateInfo = new CertificateInfo();
                certificateInfo.setCertificate(x509Certificate);
                certificateInfo.setKeystore(i);
                certificateInfo.setAlias(str2);
                if (str.equals("USRCERT_")) {
                  certificateInfo.setHasPrivateKey(true);
                }
                arrayList.add(certificateInfo);
              }
            }
          }
        }
        keyChainCRUD2.disconnect();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return arrayList;
      } catch (Throwable th) {
        th = th;
        keyChainCRUD = keyChainCRUD2;
        if (keyChainCRUD != null) {
          keyChainCRUD.disconnect();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    } catch (Throwable th2) {
      th = th2;
    }
  }

  public final List getAndroidInstalledCertificatesAsUser(int i) {
    ArrayList arrayList = new ArrayList();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        KeyChain.KeyChainConnection bindAsUser =
            KeyChain.bindAsUser(this.mContext, new UserHandle(i));
        IKeyChainService service = bindAsUser.getService();
        if (service != null) {
          try {
            try {
              Iterator it = service.userAliases().iterator();
              while (it.hasNext()) {
                byte[] certificateFromTrustCredential =
                    service.getCertificateFromTrustCredential((String) it.next(), false);
                if (certificateFromTrustCredential != null) {
                  try {
                    for (X509Certificate x509Certificate :
                        Credentials.convertFromPem(certificateFromTrustCredential)) {
                      if (x509Certificate != null) {
                        CertificateInfo certificateInfo = new CertificateInfo();
                        certificateInfo.setCertificate(x509Certificate);
                        certificateInfo.setKeystore(1);
                        certificateInfo.setSystemPreloaded(false);
                        arrayList.add(certificateInfo);
                      }
                    }
                  } catch (IOException e) {
                    Log.e("SecurityPolicy", "getAndroidInstalledCertificates " + e);
                  } catch (CertificateException e2) {
                    Log.e("SecurityPolicy", "getAndroidInstalledCertificates " + e2);
                  }
                }
              }
            } catch (RemoteException e3) {
              Log.e("SecurityPolicy", "getAndroidInstalledCertificates " + e3);
            }
          } finally {
            bindAsUser.close();
          }
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    } catch (AssertionError unused) {
      Log.e(
          "SecurityPolicy",
          "getAndroidInstalledCertificatesAsUser - is KeyChainService running for user " + i + "?");
    } catch (InterruptedException e4) {
      Log.e("SecurityPolicy", "getSystemCertificatesAsUser " + e4);
    }
    return arrayList;
  }

  public final List getSystemCertificatesAsUser(boolean z, int i) {
    ArrayList arrayList = new ArrayList();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        try {
          KeyChain.KeyChainConnection bindAsUser =
              KeyChain.bindAsUser(this.mContext, new UserHandle(i));
          IKeyChainService service = bindAsUser.getService();
          try {
            if (service != null) {
              try {
                for (String str : service.allSystemAliases()) {
                  CertificateInfo certificateInfo = new CertificateInfo();
                  byte[] certificateFromTrustCredential =
                      service.getCertificateFromTrustCredential(str, z);
                  if (certificateFromTrustCredential != null) {
                    try {
                      for (X509Certificate x509Certificate :
                          Credentials.convertFromPem(certificateFromTrustCredential)) {
                        if (x509Certificate != null) {
                          certificateInfo.setCertificate(x509Certificate);
                          certificateInfo.setKeystore(1);
                          certificateInfo.setSystemPreloaded(true);
                          certificateInfo.setEnabled(service.containsAlias(str));
                          arrayList.add(certificateInfo);
                        }
                      }
                    } catch (IOException e) {
                      Log.e("SecurityPolicy", "getSystemCertificatesAsUser " + e);
                    } catch (CertificateException e2) {
                      Log.e("SecurityPolicy", "getSystemCertificatesAsUser " + e2);
                    }
                  }
                }
              } catch (RemoteException e3) {
                Log.e("SecurityPolicy", "getSystemCertificatesAsUser " + e3);
              }
            }
          } finally {
            bindAsUser.close();
          }
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      } catch (InterruptedException e4) {
        Log.e("SecurityPolicy", "getSystemCertificatesAsUser " + e4);
      }
    } catch (AssertionError unused) {
      Log.e(
          "SecurityPolicy",
          "getSystemCertificatesAsUser - is KeyChainService running for user " + i + "?");
    }
    return arrayList;
  }

  public final List getNativeInstalledCertificateNamesAsUser(String str, int i, int i2) {
    String[] strArr;
    String validStr = getValidStr(str);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    if (validStr != null) {
      try {
        strArr =
            CertificateUtil.KeyChainCRUD.listAliases(
                this.mContext, validStr, i == 4 ? -1 : 1010, i2);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    } else {
      strArr = null;
    }
    return strArr != null ? Arrays.asList(strArr) : new ArrayList();
  }

  public int getCredentialStorageStatus(ContextInfo contextInfo) {
    KeyStore keyStore = KeyStore.getInstance();
    ContextInfo adminContextIfCallerInCertWhiteList =
        getEDM()
            .getAdminContextIfCallerInCertWhiteList(
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SECURITY")));
    if (adminContextIfCallerInCertWhiteList == null) {
      if (needtoCheckPackageCaller()) {
        adminContextIfCallerInCertWhiteList = enforceCertificateProvisioningPermission(contextInfo);
      } else {
        adminContextIfCallerInCertWhiteList = enforceOnlyCertProvisioningPermission(contextInfo);
      }
    }
    int userId = UserHandle.getUserId(adminContextIfCallerInCertWhiteList.mCallerUid);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    int i = 4;
    try {
      try {
        KeyStore.State state = keyStore.state(userId);
        if (state.equals(KeyStore.State.UNLOCKED)) {
          i = 1;
        } else if (state.equals(KeyStore.State.LOCKED)) {
          i = 2;
        } else if (state.equals(KeyStore.State.UNINITIALIZED)) {
          i = 3;
        }
      } catch (AssertionError e) {
        Log.d("SecurityPolicy", "Keystore State Error: " + e.getMessage());
      }
      return i;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean resetCredentialStorage(ContextInfo contextInfo) {
    ContextInfo enforceCertificateProvisioningPermission =
        enforceCertificateProvisioningPermission(contextInfo);
    int userId = UserHandle.getUserId(enforceCertificateProvisioningPermission.mCallerUid);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      AuditLog.logAsUser(
          5,
          1,
          true,
          Process.myPid(),
          "SecurityPolicy",
          String.format(
              "Admin %d has requested to clear credential storages",
              Integer.valueOf(enforceCertificateProvisioningPermission.mCallerUid)),
          userId);
      boolean z =
          AndroidKeyStoreMaintenance.clearNamespace(0, (long) UserHandle.getUid(userId, 1000)) == 0;
      if (userId == 0) {
        z &= AndroidKeyStoreMaintenance.clearNamespace(2, 102L) == 0;
      }
      try {
        z &=
            ((Boolean)
                    new ResetKeyChain()
                        .execute(Integer.valueOf(userId))
                        .get(3000L, TimeUnit.MILLISECONDS))
                .booleanValue();
        sendIntentToSettings(userId);
      } catch (Exception unused) {
        Log.e("SecurityPolicy", "resetCredentialStorage EX: ");
      }
      if (z) {
        KnoxAnalyticsData knoxAnalyticsData =
            new KnoxAnalyticsData("KNOX_AKS", 1, "API:resetCredentialStorage");
        knoxAnalyticsData.setProperty("cId", enforceCertificateProvisioningPermission.mCallerUid);
        knoxAnalyticsData.setProperty("uId", userId);
        knoxAnalyticsData.setProperty(
            "pN",
            this.mContext
                .getPackageManager()
                .getNameForUid(enforceCertificateProvisioningPermission.mCallerUid));
        KnoxAnalytics.log(knoxAnalyticsData);
      }
      return z;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public class ResetKeyChain extends AsyncTask {
    public ResetKeyChain() {}

    @Override // android.os.AsyncTask
    public Boolean doInBackground(Integer... numArr) {
      int intValue = numArr[0].intValue();
      try {
        KeyChain.KeyChainConnection bindAsUser =
            KeyChain.bindAsUser(SecurityPolicy.this.mContext, new UserHandle(intValue));
        try {
          return Boolean.valueOf(bindAsUser.getService().reset());
        } catch (RemoteException unused) {
          return Boolean.FALSE;
        } finally {
          bindAsUser.close();
        }
      } catch (AssertionError unused2) {
        Log.e(
            "SecurityPolicy",
            "ResetKeyChain - is KeyChainService running for user " + intValue + "?");
        return Boolean.FALSE;
      } catch (InterruptedException unused3) {
        return Boolean.FALSE;
      }
    }
  }

  public class FactoryWipeReceiver extends MasterClearReceiver {
    public FactoryWipeReceiver() {}

    @Override // com.android.server.MasterClearReceiver, android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      try {
        super.onReceive(context, intent);
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.sendMessage(responseHandler.obtainMessage(1, Boolean.TRUE));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /* JADX WARN: Can't wrap try/catch for region: R(18:0|1|(4:4|(3:15|16|17)|18|2)|23|24|(6:(8:56|57|58|(6:37|38|(1:42)|43|(1:45)(1:48)|46)|30|(1:33)|34|35)(2:52|53)|50|30|(1:33)|34|35)|27|(0)|37|38|(2:40|42)|43|(0)(0)|46|30|(0)|34|35) */
  /* JADX WARN: Code restructure failed: missing block: B:49:0x00fe, code lost:

     r4 = r7;
  */
  /* JADX WARN: Removed duplicated region for block: B:32:0x0109 A[ADDED_TO_REGION] */
  /* JADX WARN: Removed duplicated region for block: B:45:0x00b5 A[Catch: Exception -> 0x00fe, TryCatch #1 {Exception -> 0x00fe, blocks: (B:38:0x0063, B:40:0x0071, B:42:0x007e, B:43:0x0081, B:45:0x00b5, B:46:0x00bb), top: B:37:0x0063 }] */
  /* JADX WARN: Removed duplicated region for block: B:48:0x00b8  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean wipeDevice(ContextInfo contextInfo, int i) {
    boolean z;
    PersistentDataBlockManager persistentDataBlockManager;
    ContextInfo enforceOwnerOnlyAndSecurityPermission =
        enforceOwnerOnlyAndSecurityPermission(contextInfo);
    boolean z2 = false;
    this.mMediaFormatRet = false;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    boolean z3 = false;
    for (VolumeInfo volumeInfo : getStorageAdapter().getVolumes()) {
      if (volumeInfo != null
          && volumeInfo.getType() == 0
          && volumeInfo.getDisk() != null
          && volumeInfo.getDisk().isSd()) {
        z3 = true;
      }
    }
    if (i == 2 || i == 3) {
      if (z3) {
        try {
          formatExternalStorageCard();
          z = true;
          if (i != 1 || i == 3) {
            if (Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0)
                    != 0
                && (persistentDataBlockManager =
                        (PersistentDataBlockManager)
                            this.mContext.getSystemService("persistent_data_block"))
                    != null) {
              persistentDataBlockManager.wipe();
            }
            this.factoryReceiver = getFactoryReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.MASTER_CLEAR");
            this.mContext.registerReceiver(this.factoryReceiver, intentFilter);
            Intent intent = new Intent("android.intent.action.MASTER_CLEAR");
            String[] packagesForUid =
                this.mContext
                    .getPackageManager()
                    .getPackagesForUid(enforceOwnerOnlyAndSecurityPermission.mCallerUid);
            StringBuilder sb = new StringBuilder();
            sb.append("DeviceWipeByMDM(");
            sb.append(packagesForUid == null ? packagesForUid[0] : "null!");
            sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
            sb.append(enforceOwnerOnlyAndSecurityPermission.mCallerUid);
            sb.append(")");
            intent.putExtra("android.intent.extra.REASON", sb.toString());
            this.mContext.sendBroadcast(intent);
            this.mMediaFormatRet = true;
            AuditLog.logAsUser(
                5,
                1,
                true,
                Process.myPid(),
                "SecurityPolicy",
                String.format(
                    "Admin %d has requested full wipe of device.",
                    Integer.valueOf(enforceOwnerOnlyAndSecurityPermission.mCallerUid)),
                UserHandle.getUserId(enforceOwnerOnlyAndSecurityPermission.mCallerUid));
          }
        } catch (Exception unused) {
          z2 = true;
        }
        if (this.mMediaFormatRet && z) {
          AuditLog.logAsUser(
              5,
              1,
              true,
              Process.myPid(),
              "SecurityPolicy",
              String.format(
                  "Admin %d has requested wipe of device external memory.",
                  Integer.valueOf(enforceOwnerOnlyAndSecurityPermission.mCallerUid)),
              UserHandle.getUserId(enforceOwnerOnlyAndSecurityPermission.mCallerUid));
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        Log.d(
            "SecurityPolicy",
            "formatStorageCard() = " + this.mMediaFormatRet + ", isExternal = " + z);
        return this.mMediaFormatRet;
      }
      try {
        Log.w("SecurityPolicy", "This device doesn't suppot External SD card");
        this.mMediaFormatRet = false;
      } catch (Exception unused2) {
      }
      Log.w("SecurityPolicy", "formatStorageCard fail");
      z = z2;
      if (this.mMediaFormatRet) {
        AuditLog.logAsUser(
            5,
            1,
            true,
            Process.myPid(),
            "SecurityPolicy",
            String.format(
                "Admin %d has requested wipe of device external memory.",
                Integer.valueOf(enforceOwnerOnlyAndSecurityPermission.mCallerUid)),
            UserHandle.getUserId(enforceOwnerOnlyAndSecurityPermission.mCallerUid));
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      Log.d(
          "SecurityPolicy",
          "formatStorageCard() = " + this.mMediaFormatRet + ", isExternal = " + z);
      return this.mMediaFormatRet;
    }
    z = false;
    if (i != 1) {}
    if (Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0) {
      persistentDataBlockManager.wipe();
    }
    this.factoryReceiver = getFactoryReceiver();
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("android.intent.action.MASTER_CLEAR");
    this.mContext.registerReceiver(this.factoryReceiver, intentFilter2);
    Intent intent2 = new Intent("android.intent.action.MASTER_CLEAR");
    String[] packagesForUid2 =
        this.mContext
            .getPackageManager()
            .getPackagesForUid(enforceOwnerOnlyAndSecurityPermission.mCallerUid);
    StringBuilder sb2 = new StringBuilder();
    sb2.append("DeviceWipeByMDM(");
    sb2.append(packagesForUid2 == null ? packagesForUid2[0] : "null!");
    sb2.append(XmlUtils.STRING_ARRAY_SEPARATOR);
    sb2.append(enforceOwnerOnlyAndSecurityPermission.mCallerUid);
    sb2.append(")");
    intent2.putExtra("android.intent.extra.REASON", sb2.toString());
    this.mContext.sendBroadcast(intent2);
    this.mMediaFormatRet = true;
    AuditLog.logAsUser(
        5,
        1,
        true,
        Process.myPid(),
        "SecurityPolicy",
        String.format(
            "Admin %d has requested full wipe of device.",
            Integer.valueOf(enforceOwnerOnlyAndSecurityPermission.mCallerUid)),
        UserHandle.getUserId(enforceOwnerOnlyAndSecurityPermission.mCallerUid));
    if (this.mMediaFormatRet) {}
    Binder.restoreCallingIdentity(clearCallingIdentity);
    Log.d(
        "SecurityPolicy", "formatStorageCard() = " + this.mMediaFormatRet + ", isExternal = " + z);
    return this.mMediaFormatRet;
  }

  public boolean removeAccountsByType(ContextInfo contextInfo, String str) {
    return removeAccountsInternal(
        "removeAccountsByType", enforceSecurityPermission(contextInfo), str);
  }

  public boolean removeAccountsWithoutAdminPrivilege(ContextInfo contextInfo, String str) {
    return removeAccountsInternal(
        "removeAccountsWithoutAdminPrivilege", enforceOnlyKnoxInternalPermission(contextInfo), str);
  }

  public final boolean removeAccountsInternal(String str, ContextInfo contextInfo, String str2) {
    boolean z = false;
    if (str2 == null) {
      Log.i("SecurityPolicy", str + "() failed - type is invalid");
      return false;
    }
    int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        AccountManager accountManager = AccountManager.get(this.mContext);
        Account[] accountsByTypeAsUser =
            accountManager.getAccountsByTypeAsUser(str2, new UserHandle(callingOrCurrentUserId));
        if (accountsByTypeAsUser != null && accountsByTypeAsUser.length > 0) {
          for (Account account : accountsByTypeAsUser) {
            Log.i("SecurityPolicy", str + "() account = " + account.name);
            accountManager.removeAccountAsUser(
                account, null, null, new UserHandle(callingOrCurrentUserId));
          }
        } else {
          Log.i("SecurityPolicy", str + "() : there is no account for type - " + str2);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        z = true;
      } catch (Exception e) {
        Log.e("SecurityPolicy", str + "() : failed. error occurs.", e);
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
      if (!z) {
        Log.e("SecurityPolicy", str + "() : has failed. type - " + str2);
      }
      return z;
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public boolean enableRebootBanner(ContextInfo contextInfo, boolean z) {
    boolean enableRebootBannerInternal = enableRebootBannerInternal(contextInfo, z, null);
    int userId = UserHandle.getUserId(contextInfo.mCallerUid);
    if (enableRebootBannerInternal) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (z) {
          AuditLog.logAsUser(
              5,
              1,
              true,
              Process.myPid(),
              "SecurityPolicy",
              String.format(
                  "Admin %d has enabled reboot banner.", Integer.valueOf(contextInfo.mCallerUid)),
              userId);
        } else {
          AuditLog.logAsUser(
              5,
              1,
              true,
              Process.myPid(),
              "SecurityPolicy",
              String.format(
                  "Admin %d has disabled reboot banner.", Integer.valueOf(contextInfo.mCallerUid)),
              userId);
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
    return enableRebootBannerInternal;
  }

  public boolean enableRebootBannerWithText(ContextInfo contextInfo, boolean z, String str) {
    boolean enableRebootBannerInternal = enableRebootBannerInternal(contextInfo, z, str);
    int userId = UserHandle.getUserId(contextInfo.mCallerUid);
    if (enableRebootBannerInternal) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (z) {
          AuditLog.logAsUser(
              5,
              1,
              true,
              Process.myPid(),
              "SecurityPolicy",
              String.format(
                  "Admin %d has enabled reboot banner with text %s",
                  Integer.valueOf(contextInfo.mCallerUid), str),
              userId);
        } else {
          AuditLog.logAsUser(
              5,
              1,
              true,
              Process.myPid(),
              "SecurityPolicy",
              String.format(
                  "Admin %d has disabled reboot banner.", Integer.valueOf(contextInfo.mCallerUid)),
              userId);
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
    return enableRebootBannerInternal;
  }

  public final boolean enableRebootBannerInternal(ContextInfo contextInfo, boolean z, String str) {
    boolean z2;
    ContextInfo enforceOwnerOnlyAndSecurityPermission =
        enforceOwnerOnlyAndSecurityPermission(contextInfo);
    int i = enforceOwnerOnlyAndSecurityPermission.mCallerUid;
    if (!z) {
      str = null;
    }
    try {
      this.mEdmStorageProvider.putBoolean(i, "SECURITY", "deviceEnrolled", z);
      this.mEdmStorageProvider.putString(i, "SECURITY", "bannerText", str);
      z2 = true;
    } catch (Exception unused) {
      z2 = false;
    }
    addBannerAppToBatteryOptimizationWhitelist(enforceOwnerOnlyAndSecurityPermission, z);
    return z2;
  }

  public boolean isRebootBannerEnabled(ContextInfo contextInfo) {
    return isRebootBannerEnabled(Utils.getCallingOrCurrentUserId(contextInfo));
  }

  public boolean isRebootBannerEnabled(int i) {
    Iterator it =
        this.mEdmStorageProvider.getBooleanListAsUser("SECURITY", "deviceEnrolled", i).iterator();
    while (it.hasNext()) {
      boolean booleanValue = ((Boolean) it.next()).booleanValue();
      if (booleanValue) {
        return booleanValue;
      }
    }
    return false;
  }

  public String getRebootBannerText(ContextInfo contextInfo) {
    List<String> stringListAsUser =
        this.mEdmStorageProvider.getStringListAsUser(
            "SECURITY", "bannerText", Utils.getCallingOrCurrentUserId(contextInfo));
    if (stringListAsUser == null) {
      return null;
    }
    for (String str : stringListAsUser) {
      if (str != null) {
        return str;
      }
    }
    return null;
  }

  public boolean setDodBannerVisibleStatus(ContextInfo contextInfo, boolean z) {
    ContextInfo enforceOnlySecurityPermission = enforceOnlySecurityPermission(contextInfo);
    if (!isBannerApp(enforceOnlySecurityPermission.mCallerUid)) {
      return false;
    }
    int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOnlySecurityPermission);
    try {
      this.mEdmStorageProvider.putGenericValueAsUser(
          "dodBannerVisible", Integer.toString(z ? 1 : 0), callingOrCurrentUserId);
      if (callingOrCurrentUserId == 0) {
        if (z) {
          registerDexBlocker();
          setHomeAndRecentKey(false);
        } else {
          unRegisterDexBlocker();
          setHomeAndRecentKey(true);
        }
      }
      return true;
    } catch (Exception unused) {
      return false;
    }
  }

  public final boolean isBannerApp(int i) {
    return this.mContext
        .getPackageManager()
        .getNameForUid(i)
        .equals(KnoxCustomManagerService.KNOX_PP_AGENT_PKG_NAME);
  }

  public boolean isDodBannerVisible(ContextInfo contextInfo) {
    return isDodBannerVisibleAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
  }

  public boolean isDodBannerVisibleAsUser(int i) {
    String str;
    try {
      str = this.mEdmStorageProvider.getGenericValueAsUser("dodBannerVisible", i);
    } catch (Exception unused) {
      Log.i("SecurityPolicy", "isDodBannerVisibleAsUser facing exception, return default value");
      str = null;
    }
    return str != null && str.equals("1");
  }

  public boolean setDeviceLastAccessDate(ContextInfo contextInfo, String str) {
    ContextInfo enforceOnlySecurityPermission = enforceOnlySecurityPermission(contextInfo);
    if (!isBannerApp(enforceOnlySecurityPermission.mCallerUid)) {
      return false;
    }
    int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOnlySecurityPermission);
    try {
      this.mEdmStorageProvider.putGenericValueAsUser(
          "deviceLastAccessDate", str, callingOrCurrentUserId);
      mBannerMap.remove(Integer.valueOf(callingOrCurrentUserId));
      return true;
    } catch (Exception unused) {
      return false;
    }
  }

  public String getDeviceLastAccessDate(ContextInfo contextInfo) {
    return this.mEdmStorageProvider.getGenericValueAsUser(
        "deviceLastAccessDate", Utils.getCallingOrCurrentUserId(contextInfo));
  }

  public final boolean saveDeviceBootMode(boolean z) {
    try {
      int i = 1;
      boolean putGenericValue =
          this.mEdmStorageProvider.putGenericValue("deviceBootMode", Integer.toString(z ? 1 : 0));
      StringBuilder sb = new StringBuilder();
      sb.append("Device safe mode saved in generic table : ");
      if (!z) {
        i = 0;
      }
      sb.append(Integer.toString(i));
      Log.i("SecurityPolicy", sb.toString());
      return putGenericValue;
    } catch (Exception unused) {
      return false;
    }
  }

  public final boolean isLastBootInSafeMode() {
    String genericValue = this.mEdmStorageProvider.getGenericValue("deviceBootMode");
    return genericValue != null && genericValue.equals("1");
  }

  public int installCertificateToKeystore(
      ContextInfo contextInfo,
      String str,
      byte[] bArr,
      String str2,
      String str3,
      int i,
      boolean z) {
    int i2;
    int i3;
    ContextInfo enforceAdminPermissionIfCallerInCertWhiteList =
        enforceAdminPermissionIfCallerInCertWhiteList(contextInfo, i);
    int userId = UserHandle.getUserId(enforceAdminPermissionIfCallerInCertWhiteList.mCallerUid);
    String validStr = getValidStr(str);
    String validStr2 = getValidStr(str2);
    String trim = str3 != null ? str3.trim() : str3;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      int credentialStorageStatus =
          getCredentialStorageStatus(enforceAdminPermissionIfCallerInCertWhiteList);
      if (credentialStorageStatus != 1 && credentialStorageStatus != 3) {
        Log.d(
            "SecurityPolicy",
            "installCertificateToKeystore: Keystore error " + credentialStorageStatus);
        i2 = userId;
        i3 = 1;
      } else {
        if (validateKeystoreParam(i)
            && ((z || validStr != null) && bArr != null && bArr.length != 0 && validStr2 != null)) {
          AuditLog.logAsUser(
              5,
              1,
              true,
              Process.myPid(),
              "SecurityPolicy",
              String.format(
                  "Admin %d has requested to install a certificate. Keystore(s) : %s, Name : %s",
                  Integer.valueOf(enforceAdminPermissionIfCallerInCertWhiteList.mCallerUid),
                  getKeystoreString(i),
                  validStr2),
              userId);
          i3 = 1;
          i2 = userId;
          credentialStorageStatus =
              new CertificateUtil(this.mContext)
                  .installAsUser(validStr, bArr, validStr2, trim, i, userId);
        }
        i2 = userId;
        i3 = 1;
        Log.d("SecurityPolicy", "installCertificateToKeystore: Invalid parameter(s)");
        credentialStorageStatus = -1;
      }
      sendIntentToSettings(i2);
      if (!z && credentialStorageStatus == 0) {
        KnoxAnalyticsData knoxAnalyticsData =
            new KnoxAnalyticsData("KNOX_AKS", i3, "API:installCertificateToKeystore");
        knoxAnalyticsData.setProperty(
            "cId", enforceAdminPermissionIfCallerInCertWhiteList.mCallerUid);
        knoxAnalyticsData.setProperty("uId", i2);
        knoxAnalyticsData.setProperty(
            "pN",
            this.mContext
                .getPackageManager()
                .getNameForUid(enforceAdminPermissionIfCallerInCertWhiteList.mCallerUid));
        knoxAnalyticsData.setProperty("key", getKeystoreString(i));
        KnoxAnalytics.log(knoxAnalyticsData);
      }
      return credentialStorageStatus;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public List getCertificatesFromKeystore(ContextInfo contextInfo, int i, int i2) {
    int userId =
        UserHandle.getUserId(
            enforceAdminPermissionIfCallerInCertWhiteList(contextInfo, i).mCallerUid);
    ArrayList arrayList = new ArrayList();
    if (this.mPendingGetCerificates.containsKey(Integer.valueOf(i2))) {
      arrayList.addAll((Collection) this.mPendingGetCerificates.get(Integer.valueOf(i2)));
    } else {
      if (!validateKeystoreParam(i)) {
        return null;
      }
      if ((i & 1) != 0) {
        arrayList.addAll(getAndroidInstalledCertificatesAsUser(userId));
        arrayList.addAll(getSystemCertificatesAsUser(true, userId));
      }
      if ((i & 2) != 0) {
        arrayList.addAll(getNativeInstalledCertificates("USRCERT_", 2));
        arrayList.addAll(getNativeInstalledCertificates("CACERT_", 2));
      }
      if ((i & 4) != 0) {
        arrayList.addAll(getNativeInstalledCertificatesAsUser("USRCERT_", 4, userId));
        arrayList.addAll(getNativeInstalledCertificatesAsUser("CACERT_", 4, userId));
      }
    }
    if (arrayList.size() >= CertificateProvisioning.MAXIMUM_CERTIFICATE_NUMBERS) {
      this.mPendingGetCerificates.put(
          Integer.valueOf(i2),
          arrayList.subList(CertificateProvisioning.MAXIMUM_CERTIFICATE_NUMBERS, arrayList.size()));
      return arrayList.subList(0, CertificateProvisioning.MAXIMUM_CERTIFICATE_NUMBERS);
    }
    this.mPendingGetCerificates.remove(Integer.valueOf(i2));
    return arrayList.subList(0, arrayList.size());
  }

  public final String retrieveCertificateAliasFromKeyChain(
      IKeyChainService iKeyChainService, Certificate certificate) {
    if (iKeyChainService != null) {
      try {
        return iKeyChainService.getCertificateAlias(
            Credentials.convertToPem(new Certificate[] {certificate}));
      } catch (RemoteException e) {
        Log.e("SecurityPolicy", "retrieveCertificateAliasFromKeyChain: " + e.toString());
      } catch (IOException e2) {
        Log.e("SecurityPolicy", "retrieveCertificateAliasFromKeyChain: " + e2.toString());
      } catch (CertificateEncodingException e3) {
        Log.e("SecurityPolicy", "retrieveCertificateAliasFromKeyChain: " + e3.toString());
      }
    }
    return null;
  }

  /* JADX WARN: Removed duplicated region for block: B:35:0x016a A[Catch: all -> 0x010e, TRY_ENTER, TRY_LEAVE, TryCatch #10 {all -> 0x010e, blocks: (B:32:0x0107, B:35:0x016a, B:53:0x01a1, B:50:0x019d, B:51:0x01a0, B:56:0x01a4, B:58:0x01a8, B:59:0x01b3, B:61:0x01b7, B:62:0x01c1, B:55:0x0170, B:40:0x017b, B:46:0x0184), top: B:31:0x0107, inners: #12 }] */
  /* JADX WARN: Removed duplicated region for block: B:58:0x01a8 A[Catch: all -> 0x010e, TryCatch #10 {all -> 0x010e, blocks: (B:32:0x0107, B:35:0x016a, B:53:0x01a1, B:50:0x019d, B:51:0x01a0, B:56:0x01a4, B:58:0x01a8, B:59:0x01b3, B:61:0x01b7, B:62:0x01c1, B:55:0x0170, B:40:0x017b, B:46:0x0184), top: B:31:0x0107, inners: #12 }] */
  /* JADX WARN: Removed duplicated region for block: B:61:0x01b7 A[Catch: all -> 0x010e, TryCatch #10 {all -> 0x010e, blocks: (B:32:0x0107, B:35:0x016a, B:53:0x01a1, B:50:0x019d, B:51:0x01a0, B:56:0x01a4, B:58:0x01a8, B:59:0x01b3, B:61:0x01b7, B:62:0x01c1, B:55:0x0170, B:40:0x017b, B:46:0x0184), top: B:31:0x0107, inners: #12 }] */
  /* JADX WARN: Removed duplicated region for block: B:64:0x01c6  */
  /* JADX WARN: Removed duplicated region for block: B:66:0x01cb  */
  /* JADX WARN: Removed duplicated region for block: B:71:0x020f  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean deleteCertificateFromKeystore(
      ContextInfo contextInfo, CertificateInfo certificateInfo, int i) {
    KeyChain.KeyChainConnection keyChainConnection;
    ContextInfo contextInfo2;
    String str;
    String str2;
    String str3;
    IKeyChainService iKeyChainService;
    KeyChain.KeyChainConnection keyChainConnection2;
    IKeyChainService service;
    String str4;
    IKeyChainService iKeyChainService2;
    boolean z;
    boolean z2;
    String alias;
    int myPid;
    Object[] objArr;
    ContextInfo enforceAdminPermissionIfCallerInCertWhiteList =
        enforceAdminPermissionIfCallerInCertWhiteList(contextInfo, i);
    if (!validateKeystoreParam(i)
        || certificateInfo == null
        || certificateInfo.getCertificate() == null) {
      return false;
    }
    int userId = UserHandle.getUserId(enforceAdminPermissionIfCallerInCertWhiteList.mCallerUid);
    try {
      Certificate certificate = certificateInfo.getCertificate();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      int i2 = i & 1;
      try {
        if (i2 != 0) {
          try {
            KeyChain.KeyChainConnection bindAsUser =
                KeyChain.bindAsUser(this.mContext, new UserHandle(userId));
            try {
              keyChainConnection2 = bindAsUser;
              service = bindAsUser.getService();
            } catch (AssertionError unused) {
              contextInfo2 = enforceAdminPermissionIfCallerInCertWhiteList;
              keyChainConnection = bindAsUser;
              str = "SecurityPolicy";
              str2 = "deleteCertificateFromKeystore: ";
              str3 = null;
              iKeyChainService = null;
              Log.e(
                  str,
                  "deleteCertificateFromKeystore - is KeyChainService running for user "
                      + userId
                      + "?");
              try {
                keyChainConnection2 = keyChainConnection;
                str4 = str3;
                iKeyChainService2 = iKeyChainService;
                z = false;
                if (i2 != 0) {}
                if ((i & 2) != 0) {}
                if ((i & 4) != 0) {}
                sendIntentToSettings(userId);
                if (keyChainConnection2 != null) {}
                if (z) {}
                return z;
              } catch (Throwable th) {
                th = th;
                if (keyChainConnection != null) {
                  keyChainConnection.close();
                }
                throw th;
              }
            } catch (InterruptedException e) {
              e = e;
              contextInfo2 = enforceAdminPermissionIfCallerInCertWhiteList;
              keyChainConnection = bindAsUser;
              str = "SecurityPolicy";
              str2 = "deleteCertificateFromKeystore: ";
              str3 = null;
              iKeyChainService = null;
              Log.e(str, str2 + e.toString());
              keyChainConnection2 = keyChainConnection;
              str4 = str3;
              iKeyChainService2 = iKeyChainService;
              z = false;
              if (i2 != 0) {}
              if ((i & 2) != 0) {}
              if ((i & 4) != 0) {}
              sendIntentToSettings(userId);
              if (keyChainConnection2 != null) {}
              if (z) {}
              return z;
            } catch (Throwable th2) {
              th = th2;
              keyChainConnection = bindAsUser;
              throw th;
            }
          } catch (AssertionError unused2) {
            contextInfo2 = enforceAdminPermissionIfCallerInCertWhiteList;
            str = "SecurityPolicy";
            str2 = "deleteCertificateFromKeystore: ";
            keyChainConnection = null;
          } catch (InterruptedException e2) {
            e = e2;
            contextInfo2 = enforceAdminPermissionIfCallerInCertWhiteList;
            str = "SecurityPolicy";
            str2 = "deleteCertificateFromKeystore: ";
            keyChainConnection = null;
          } catch (Throwable th3) {
            th = th3;
            keyChainConnection = null;
          }
        } else {
          service = null;
          keyChainConnection2 = null;
        }
        try {
          try {
            if (AuditLog.isAuditLogEnabledAsUser(userId)) {
              if (i2 != 0) {
                alias = retrieveCertificateAliasFromKeyChain(service, certificate);
              } else {
                alias = certificateInfo.getAlias();
              }
              str3 = alias;
              try {
                Certificate certificate2 = certificateInfo.getCertificate();
                String name =
                    certificate2 instanceof X509Certificate
                        ? ((X509Certificate) certificate2).getIssuerDN().getName()
                        : null;
                myPid = Process.myPid();
                objArr = new Object[5];
                objArr[0] =
                    Integer.valueOf(enforceAdminPermissionIfCallerInCertWhiteList.mCallerUid);
                objArr[1] = getKeystoreString(i);
                objArr[2] = str3 == null ? "Not available" : str3;
                objArr[3] = certificateInfo.getSubject();
                objArr[4] = name;
                iKeyChainService = service;
                str = "SecurityPolicy";
                contextInfo2 = enforceAdminPermissionIfCallerInCertWhiteList;
                str2 = "deleteCertificateFromKeystore: ";
              } catch (AssertionError unused3) {
                contextInfo2 = enforceAdminPermissionIfCallerInCertWhiteList;
                iKeyChainService = service;
                str = "SecurityPolicy";
                str2 = "deleteCertificateFromKeystore: ";
              } catch (InterruptedException e3) {
                e = e3;
                contextInfo2 = enforceAdminPermissionIfCallerInCertWhiteList;
                iKeyChainService = service;
                str = "SecurityPolicy";
                str2 = "deleteCertificateFromKeystore: ";
              }
              try {
                AuditLog.logAsUser(
                    5,
                    1,
                    true,
                    myPid,
                    "SecurityPolicy",
                    String.format(
                        "Admin %d has requested to delete a certificate. Keystore(s) : %s, Alias :"
                            + " %s, Subject : %s, Issuer : %s",
                        objArr),
                    userId);
                str4 = str3;
              } catch (AssertionError unused4) {
                keyChainConnection = keyChainConnection2;
                Log.e(
                    str,
                    "deleteCertificateFromKeystore - is KeyChainService running for user "
                        + userId
                        + "?");
                keyChainConnection2 = keyChainConnection;
                str4 = str3;
                iKeyChainService2 = iKeyChainService;
                z = false;
                if (i2 != 0) {}
                if ((i & 2) != 0) {}
                if ((i & 4) != 0) {}
                sendIntentToSettings(userId);
                if (keyChainConnection2 != null) {}
                if (z) {}
                return z;
              } catch (InterruptedException e4) {
                e = e4;
                keyChainConnection = keyChainConnection2;
                Log.e(str, str2 + e.toString());
                keyChainConnection2 = keyChainConnection;
                str4 = str3;
                iKeyChainService2 = iKeyChainService;
                z = false;
                if (i2 != 0) {}
                if ((i & 2) != 0) {}
                if ((i & 4) != 0) {}
                sendIntentToSettings(userId);
                if (keyChainConnection2 != null) {}
                if (z) {}
                return z;
              }
            } else {
              contextInfo2 = enforceAdminPermissionIfCallerInCertWhiteList;
              iKeyChainService = service;
              str = "SecurityPolicy";
              str2 = "deleteCertificateFromKeystore: ";
              str4 = null;
            }
            try {
              iKeyChainService2 = iKeyChainService;
              z = true;
            } catch (Throwable th4) {
              th = th4;
              keyChainConnection = keyChainConnection2;
              if (keyChainConnection != null) {}
              throw th;
            }
          } catch (Throwable th5) {
            th = th5;
            keyChainConnection = keyChainConnection2;
            throw th;
          }
        } catch (AssertionError unused5) {
          contextInfo2 = enforceAdminPermissionIfCallerInCertWhiteList;
          iKeyChainService = service;
          str = "SecurityPolicy";
          str2 = "deleteCertificateFromKeystore: ";
          keyChainConnection = keyChainConnection2;
          str3 = null;
        } catch (InterruptedException e5) {
          e = e5;
          contextInfo2 = enforceAdminPermissionIfCallerInCertWhiteList;
          iKeyChainService = service;
          str = "SecurityPolicy";
          str2 = "deleteCertificateFromKeystore: ";
          keyChainConnection = keyChainConnection2;
          str3 = null;
        }
        if (i2 != 0) {
          clearCallingIdentity = Binder.clearCallingIdentity();
          if (str4 == null) {
            try {
              str4 = retrieveCertificateAliasFromKeyChain(iKeyChainService2, certificate);
            } finally {
              Binder.restoreCallingIdentity(clearCallingIdentity);
            }
          }
          if (str4 != null && iKeyChainService2 != null) {
            try {
              z2 = iKeyChainService2.deleteCaCertificate(str4) & z;
            } catch (RemoteException e6) {
              Log.e(str, str2 + e6.toString());
              z2 = z & false;
            }
            z = z2;
          }
        }
        if ((i & 2) != 0) {
          z &= retrieveAliasAndDeleteCertificate(certificate, certificateInfo.getAlias(), 2, 0);
        }
        if ((i & 4) != 0) {
          z &=
              retrieveAliasAndDeleteCertificate(certificate, certificateInfo.getAlias(), 4, userId);
        }
        sendIntentToSettings(userId);
        if (keyChainConnection2 != null) {
          keyChainConnection2.close();
        }
        if (z) {
          KnoxAnalyticsData knoxAnalyticsData =
              new KnoxAnalyticsData("KNOX_AKS", 1, "API:deleteCertificateFromKeystore");
          ContextInfo contextInfo3 = contextInfo2;
          knoxAnalyticsData.setProperty("cId", contextInfo3.mCallerUid);
          knoxAnalyticsData.setProperty("uId", userId);
          knoxAnalyticsData.setProperty(
              "pN", this.mContext.getPackageManager().getNameForUid(contextInfo3.mCallerUid));
          knoxAnalyticsData.setProperty("key", getKeystoreString(i));
          KnoxAnalytics.log(knoxAnalyticsData);
        }
        return z;
      } catch (Throwable th6) {
        th = th6;
      }
    } catch (Throwable th7) {
      th = th7;
      keyChainConnection = null;
    }
  }

  public final boolean retrieveAliasAndDeleteCertificate(
      Certificate certificate, String str, int i, int i2) {
    String retrieveAliasToBeDeleted = retrieveAliasToBeDeleted(certificate, str, "CACERT_", i, i2);
    if (retrieveAliasToBeDeleted == null) {
      retrieveAliasToBeDeleted = retrieveAliasToBeDeleted(certificate, str, "USRCERT_", i, i2);
    }
    return deleteCertificateFromNativeKeystoreAsUser(retrieveAliasToBeDeleted, i, i2);
  }

  public final String retrieveAliasToBeDeleted(
      Certificate certificate, String str, String str2, int i, int i2) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    CertificateUtil.KeyChainCRUD keyChainCRUD = null;
    String str3 = null;
    try {
      List<String> arrayList = new ArrayList();
      if (!TextUtils.isEmpty(str)) {
        arrayList.add(str);
      } else {
        arrayList = getNativeInstalledCertificateNamesAsUser(str2, i, i2);
      }
      int i3 = i == 4 ? -1 : 1010;
      CertificateUtil.KeyChainCRUD keyChainCRUD2 =
          new CertificateUtil.KeyChainCRUD(this.mContext, i2);
      try {
        for (String str4 : arrayList) {
          byte[] bArr = keyChainCRUD2.get(str4, str2, i3);
          if (bArr != null) {
            Iterator it = CertificateUtil.toCertificates(bArr).iterator();
            while (true) {
              if (!it.hasNext()) {
                break;
              }
              X509Certificate x509Certificate = (X509Certificate) it.next();
              if (x509Certificate != null && x509Certificate.equals(certificate)) {
                str3 = str4;
                break;
              }
            }
          }
        }
        keyChainCRUD2.disconnect();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return str3;
      } catch (Throwable th) {
        th = th;
        keyChainCRUD = keyChainCRUD2;
        if (keyChainCRUD != null) {
          keyChainCRUD.disconnect();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    } catch (Throwable th2) {
      th = th2;
    }
  }

  public final boolean deleteCertificateFromNativeKeystoreAsUser(String str, int i, int i2) {
    if (str == null) {
      Log.d(
          "SecurityPolicy",
          "deleteCertificateFromNativeKeystoreAsUser: alias is null for keystore = "
              + i
              + ", userId = "
              + i2);
      return true;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    CertificateUtil.KeyChainCRUD keyChainCRUD = null;
    try {
      CertificateUtil.KeyChainCRUD keyChainCRUD2 =
          new CertificateUtil.KeyChainCRUD(this.mContext, i2);
      try {
        boolean deleteEntry = keyChainCRUD2.deleteEntry(str, i == 4 ? -1 : 1010);
        Log.d("SecurityPolicy", "Delete state : " + deleteEntry);
        keyChainCRUD2.disconnect();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return deleteEntry;
      } catch (Throwable th) {
        th = th;
        keyChainCRUD = keyChainCRUD2;
        if (keyChainCRUD != null) {
          keyChainCRUD.disconnect();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    } catch (Throwable th2) {
      th = th2;
    }
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void systemReady() {
    Log.d("SecurityPolicy", "systemReady()");
  }

  @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
  public Set getRestrictedKeyCodes() {
    if (isDodBannerVisibleAsUser(0)) {
      return new HashSet(
          Arrays.asList(
              3,
              1001,
              Integer.valueOf(
                  FrameworkStatsLog
                      .DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED)));
    }
    return null;
  }

  public final boolean needtoCheckPackageCaller() {
    String nameForUid = this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
    return nameForUid == null || !this.pkgNameList_allowed.contains(nameForUid);
  }

  public final void sendIntentToSettings(int i) {
    new CertificateUtil(this.mContext).sendIntentToSettings(i, this.mBootCompleted);
  }

  public final String getKeystoreString(int i) {
    StringBuilder sb = new StringBuilder();
    if ((i & 1) != 0) {
      sb.append("Default");
    }
    if ((i & 2) != 0) {
      if (sb.length() != 0) {
        sb.append("/");
      }
      sb.append("Wi-Fi");
    }
    if ((i & 4) != 0) {
      if (sb.length() != 0) {
        sb.append("/");
      }
      sb.append("VPN and Apps");
    }
    if (sb.length() == 0) {
      sb.append("None");
    }
    return sb.toString();
  }

  @Override // com.android.server.enterprise.common.KeyCodeCallback
  public void setMediator(KeyCodeMediator keyCodeMediator) {
    if (this.mKeyCodeMediator == null) {
      this.mKeyCodeMediator = keyCodeMediator;
      keyCodeMediator.registerCallback(this);
    }
  }

  @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
  public boolean isKeyCodeInputAllowed(int i) {
    if (i == 3 || i == 187 || i == 1001) {
      return !isDodBannerVisibleAsUser(0);
    }
    return true;
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
      printWriter.println("Permission Denial: can't dump SecurityPolicy");
      return;
    }
    StringBuilder sb = new StringBuilder();
    List allUsersId = new CertificateUtil(this.mContext).getAllUsersId();
    sb.append("[VPN and Apps keystore]" + System.lineSeparator());
    Iterator it = allUsersId.iterator();
    while (it.hasNext()) {
      int intValue = ((Integer) it.next()).intValue();
      sb.append("Aliases for user ");
      sb.append(intValue);
      sb.append(": ");
      sb.append(
          dumpAliases(CertificateUtil.KeyChainCRUD.listAliases(this.mContext, null, -1, intValue)));
    }
    sb.append(System.lineSeparator());
    sb.append("[Wifi keystore]" + System.lineSeparator() + "Aliases: ");
    sb.append(dumpAliases(CertificateUtil.KeyChainCRUD.listAliases(this.mContext, null, 1010, 0)));
    sb.append(System.lineSeparator());
    sb.append("[Default keystore]" + System.lineSeparator());
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        Iterator it2 = allUsersId.iterator();
        while (it2.hasNext()) {
          int intValue2 = ((Integer) it2.next()).intValue();
          IKeyChainService service =
              KeyChain.bindAsUser(this.mContext, new UserHandle(intValue2)).getService();
          if (service != null) {
            try {
              try {
                sb.append("Aliases for user ");
                sb.append(intValue2);
                sb.append(": ");
                sb.append(dumpAliases(service.userAliases()));
              } catch (RemoteException e) {
                Log.e("SecurityPolicy", "Failed to dump Default keystore " + e);
              }
            } finally {
            }
          }
        }
      } catch (AssertionError e2) {
        Log.e("SecurityPolicy", "Failed to dump Default keystore " + e2);
      } catch (InterruptedException e3) {
        Log.e("SecurityPolicy", "Failed to dump Default keystore " + e3);
      }
      sb.append(System.lineSeparator());
      printWriter.write(sb.toString());
      this.mEnterpriseDumpHelper.dumpTable(
          printWriter, "SECURITY", new String[] {"deviceEnrolled", "bannerText"});
      this.mEnterpriseDumpHelper.dumpTable(
          printWriter,
          "generic",
          new String[] {"dodBannerVisible", "deviceLastAccessDate", "deviceBootMode"});
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final String dumpAliases(String[] strArr) {
    return dumpAliases(strArr != null ? Arrays.asList(strArr) : null);
  }

  public final String dumpAliases(List list) {
    StringBuilder sb = new StringBuilder();
    if (list != null) {
      sb.append("{");
      Iterator it = list.iterator();
      while (it.hasNext()) {
        sb.append((String) it.next());
        if (it.hasNext()) {
          sb.append(", ");
        }
      }
      sb.append("}");
      sb.append(System.lineSeparator());
    }
    return sb.toString();
  }

  public final boolean isInternalStorageEncryptedbyDefaultKey(ContextInfo contextInfo) {
    enforceOwnerOnlyAndSecurityPermission(contextInfo);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
              .getStorageEncryptionStatus()
          == 4;
    } catch (Exception unused) {
      Log.w("SecurityPolicy", "is Internal Storage Encrypted by Default key?");
      return false;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean addPackagesToCertificateWhiteList(ContextInfo contextInfo, List list) {
    boolean z;
    boolean z2;
    ContextInfo enforceCertificateProvisioningPermission =
        enforceCertificateProvisioningPermission(contextInfo);
    int userId = UserHandle.getUserId(enforceCertificateProvisioningPermission.mCallerUid);
    Context createContextAsUser = Utils.createContextAsUser(this.mContext, "android", 0, userId);
    if (list == null || list.isEmpty()) {
      return false;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      Iterator it = list.iterator();
      String str = null;
      String str2 = null;
      boolean z3 = true;
      while (it.hasNext()) {
        AppIdentity appIdentity = (AppIdentity) it.next();
        if (appIdentity != null) {
          str = appIdentity.getPackageName();
          str2 = appIdentity.getSignature();
        }
        if (validatePackageName(str) && !isPackageAlreadyWhiteListed(str, userId)) {
          if (str2 == null || Utils.comparePackageSignature(createContextAsUser, str, str2)) {
            z = false;
            z2 = true;
          } else {
            z = isApplicationInstalled(createContextAsUser, str);
            z2 = false;
          }
          if (z2 || !z) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(
                "adminUid", Integer.valueOf(enforceCertificateProvisioningPermission.mCallerUid));
            contentValues.put("packageName", str);
            contentValues.put("signature", str2);
            z3 &= this.mEdmStorageProvider.insert("CertificateWhiteListTable", contentValues) > 0;
          }
        }
        z3 = false;
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return z3;
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public final boolean validatePackageName(String str) {
    if (TextUtils.isEmpty(str) || "*".equals(str)) {
      return false;
    }
    String[] split = str.split("\\.");
    int i = 0;
    for (int i2 = 0; i2 < str.length(); i2++) {
      if (str.charAt(i2) == '.') {
        i++;
      }
    }
    if (i >= split.length) {
      return false;
    }
    for (String str2 : split) {
      if (!str2.matches("^[A-Za-z0-9_]+$")
          || str2.charAt(0) == '_'
          || (str2.charAt(0) >= '0' && str2.charAt(0) <= '9')) {
        return false;
      }
    }
    return true;
  }

  public final boolean isApplicationInstalled(Context context, String str) {
    if (context != null) {
      try {
        if (context.getPackageManager() != null) {
          context.getPackageManager().getPackageInfo(str, 1);
          return true;
        }
      } catch (PackageManager.NameNotFoundException unused) {
      }
    }
    Log.d("SecurityPolicy", "context or PackageManager is null : returning false");
    return false;
  }

  public final boolean isPackageAlreadyWhiteListed(String str, int i) {
    return getAdminUidFromWhiteListedPackage(str, i) != -1;
  }

  public int getAdminUidFromWhiteListedPackage(String str, int i) {
    ContentValues contentValues = new ContentValues();
    contentValues.put("packageName", str);
    contentValues.put(EdmStorageProviderBase.getAdminLUIDWhereIn(0, i), "#SelectClause#");
    List values =
        this.mEdmStorageProvider.getValues(
            "CertificateWhiteListTable", new String[] {"adminUid"}, contentValues);
    if (values.size() > 0) {
      return ((ContentValues) values.get(0)).getAsInteger("adminUid").intValue();
    }
    return -1;
  }

  public List getPackagesFromCertificateWhiteList(ContextInfo contextInfo) {
    ContextInfo enforceCertificateProvisioningPermission =
        enforceCertificateProvisioningPermission(contextInfo);
    ContentValues contentValues = new ContentValues();
    contentValues.put(
        "adminUid", Integer.valueOf(enforceCertificateProvisioningPermission.mCallerUid));
    List<ContentValues> values =
        this.mEdmStorageProvider.getValues(
            "CertificateWhiteListTable", new String[] {"packageName", "signature"}, contentValues);
    ArrayList arrayList = new ArrayList();
    for (ContentValues contentValues2 : values) {
      arrayList.add(
          new AppIdentity(
              contentValues2.getAsString("packageName"), contentValues2.getAsString("signature")));
    }
    return arrayList;
  }

  public boolean removePackagesFromCertificateWhiteList(ContextInfo contextInfo, List list) {
    ContextInfo enforceCertificateProvisioningPermission =
        enforceCertificateProvisioningPermission(contextInfo);
    if (list != null) {
      if (list.isEmpty()) {
        return false;
      }
      Iterator it = list.iterator();
      boolean z = true;
      while (it.hasNext()) {
        AppIdentity appIdentity = (AppIdentity) it.next();
        if (appIdentity == null) {
          z = false;
        } else {
          ContentValues contentValues = new ContentValues();
          contentValues.put(
              "adminUid", Integer.valueOf(enforceCertificateProvisioningPermission.mCallerUid));
          contentValues.put("packageName", appIdentity.getPackageName());
          if (appIdentity.getSignature() != null) {
            contentValues.put("signature", appIdentity.getSignature());
          }
          z &= this.mEdmStorageProvider.delete("CertificateWhiteListTable", contentValues) > 0;
        }
      }
      return z;
    }
    ContentValues contentValues2 = new ContentValues();
    contentValues2.put(
        "adminUid", Integer.valueOf(enforceCertificateProvisioningPermission.mCallerUid));
    return (this.mEdmStorageProvider.delete("CertificateWhiteListTable", contentValues2) > 0)
        & true;
  }

  public final void registerDexBlocker() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      ((SemDesktopModeManager)
              this.mContext.getApplicationContext().getSystemService("desktopmode"))
          .registerBlocker(this.mBlocker);
      Log.d("SecurityPolicy", "DexBlocker was registered");
    } catch (Exception unused) {
      Log.d("SecurityPolicy", "DexBlocker was failed");
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public final void unRegisterDexBlocker() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      ((SemDesktopModeManager)
              this.mContext.getApplicationContext().getSystemService("desktopmode"))
          .unregisterBlocker(this.mBlocker);
      Log.d("SecurityPolicy", "DexBlocker was unregistered");
    } catch (Exception unused) {
      Log.d("SecurityPolicy", "DexBlocker was failed");
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public final void addBannerAppToBatteryOptimizationWhitelist(ContextInfo contextInfo, boolean z) {
    ApplicationPolicy applicationPolicy =
        (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
    if (z) {
      applicationPolicy.addPackageToBatteryOptimizationWhiteList(
          contextInfo,
          new AppIdentity(KnoxCustomManagerService.KNOX_PP_AGENT_PKG_NAME, (String) null));
    } else {
      applicationPolicy.removePackageFromBatteryOptimizationWhiteList(
          contextInfo,
          new AppIdentity(KnoxCustomManagerService.KNOX_PP_AGENT_PKG_NAME, (String) null));
    }
  }

  public final void setHomeAndRecentKey(boolean z) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      if (this.mStatusBarService == null) {
        this.mStatusBarService = getStatusBarService();
      }
      IStatusBarService iStatusBarService = this.mStatusBarService;
      if (iStatusBarService != null) {
        if (!z) {
          iStatusBarService.disable(18874368, this.mToken, "SecurityPolicy");
        } else {
          iStatusBarService.disable(0, this.mToken, "SecurityPolicy");
        }
      }
      KeyCodeMediator keyCodeMediator = this.mKeyCodeMediator;
      if (keyCodeMediator == null) {
        Log.e(
            "SecurityPolicy",
            "mKeyCodeMediator must not be null! This will cause problems on hardware key"
                + " restriction.");
      } else {
        keyCodeMediator.update(3);
        this.mKeyCodeMediator.update(1001);
        this.mKeyCodeMediator.update(
            FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED);
      }
    } catch (Exception unused) {
      Log.d("SecurityPolicy", "setHomeAndRecentKey was failed");
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public class LocalService extends SecurityPolicyInternal {
    public LocalService() {}

    public boolean isDodBannerVisibleAsUser(int i) {
      return SecurityPolicy.this.isDodBannerVisibleAsUser(i);
    }
  }
}
