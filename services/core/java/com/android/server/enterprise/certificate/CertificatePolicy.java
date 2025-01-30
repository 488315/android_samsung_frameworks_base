package com.android.server.enterprise.certificate;

import android.R;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.security.Credentials;
import android.security.IKeyChainService;
import android.security.KeyChain;
import android.security.KeyStore;
import android.text.TextUtils;
import android.util.Log;
import com.android.org.conscrypt.TrustedCertificateStore;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.application.ApplicationPolicy;
import com.android.server.enterprise.common.EnterprisePermissionChecker;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.ucm.UniversalCredentialManagerService;
import com.android.server.enterprise.utils.CertificateUtil;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.SecContentProviderUtil;
import com.android.server.enterprise.utils.Utils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.keystore.CertificateControlInfo;
import com.samsung.android.knox.keystore.CertificateInfo;
import com.samsung.android.knox.keystore.ICertificatePolicy;
import com.samsung.android.knox.keystore.PermissionApplicationPrivateKey;
import com.samsung.android.knox.ucm.configurator.CredentialStorage;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.io.ByteArrayInputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes2.dex */
public class CertificatePolicy extends ICertificatePolicy.Stub
    implements EnterpriseServiceCallback {
  public boolean mBootCompleted;
  public Context mContext;
  public EnterpriseDeviceManager mEDM;
  public EdmStorageProvider mEdmStorageProvider;
  public EnterpriseDumpHelper mEnterpriseDumpHelper;
  public EdmKeyStore mNativeKeyStore;
  public PackageManager mPackageManager;
  public PackageManagerAdapter mPackageManagerAdapter;
  public final RollbackRefreshOperation mRollbackRefresh;
  public final RollbackRefreshHandler mRollbackRefreshHandler;
  public CertificateCache mTrustedCache;
  public EdmKeyStore mTrustedKeyStore;
  public UniversalCredentialManagerService mUCMService;
  public CertificateCache mUntrustedCache;
  public EdmKeyStore mUntrustedKeyStore;
  public EdmKeyStore mUserKeyStore;
  public CertificateUtil mUtils;
  public BroadcastReceiver mPackageChangeIntentReceiver = null;
  public final Object mLock = new Object();
  public final TrustedCertificateStore mCertStore = new TrustedCertificateStore();
  public ThreadLocal mCheckRevocation =
      new ThreadLocal() { // from class:
                          // com.android.server.enterprise.certificate.CertificatePolicy.1
        @Override // java.lang.ThreadLocal
        public Boolean initialValue() {
          return Boolean.FALSE;
        }
      };
  public BroadcastReceiver mBootReceiver =
      new BroadcastReceiver() { // from class:
                                // com.android.server.enterprise.certificate.CertificatePolicy.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          Log.d("CertificatePolicy", action);
          if (action != null) {
            if (action.equals("android.intent.action.LOCKED_BOOT_COMPLETED")
                || action.equals(
                    "com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL")) {
              CertificatePolicy.this.mBootCompleted = true;
              CertificatePolicy.this.mContext.unregisterReceiver(
                  CertificatePolicy.this.mBootReceiver);
              CertificatePolicy.this.loadCache();
              CertificatePolicy.this.executeRollbackRefresh(3, -1);
            }
          }
        }
      };
  public BroadcastReceiver mUserReceiver =
      new BroadcastReceiver() { // from class:
                                // com.android.server.enterprise.certificate.CertificatePolicy.5
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          if (action != null) {
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", 0);
            if (action.equals("android.intent.action.USER_REMOVED")) {
              if (intExtra != 0) {
                CertificatePolicy.this.onUserRemoved(intExtra);
              }
            } else {
              if ("android.intent.action.USER_ADDED".equals(action)) {
                if (CertificatePolicy.this.getPersonaManagerAdapter().isValidKnoxId(intExtra)) {
                  return;
                }
                Log.i("CertificatePolicy", "Reloading cache for new user");
                CertificatePolicy.this.loadCache();
                return;
              }
              if (("android.intent.action.DEVICE_LOCKED_CHANGED".equals(action)
                      || "android.intent.action.BOOT_COMPLETED".equals(action))
                  && KeyStore.getInstance().state(intExtra) != KeyStore.State.LOCKED) {
                CertificatePolicy.this.notifyUserKeystoreUnlocked(intExtra);
              }
            }
          }
        }
      };

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void notifyToAddSystemService(String str, IBinder iBinder) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminAdded(int i) {}

  public CertificatePolicy(Context context) {
    this.mContext = context;
    this.mEdmStorageProvider = new EdmStorageProvider(context);
    this.mPackageManager = this.mContext.getPackageManager();
    registerReceivers();
    this.mTrustedKeyStore = EdmKeyStore.getInstance(0);
    this.mUserKeyStore = EdmKeyStore.getInstance(1);
    this.mNativeKeyStore = EdmKeyStore.getInstance(2);
    this.mUntrustedKeyStore = EdmKeyStore.getInstance(3);
    this.mUtils = new CertificateUtil(this.mContext);
    this.mTrustedCache = new CertificateCache(this.mContext, this.mEdmStorageProvider);
    this.mUntrustedCache = new CertificateCache(this.mContext, this.mEdmStorageProvider);
    HandlerThread handlerThread = new HandlerThread("RollbackRefreshHandler");
    handlerThread.start();
    this.mRollbackRefreshHandler = new RollbackRefreshHandler(handlerThread.getLooper());
    this.mRollbackRefresh = new RollbackRefreshOperation(this, this.mContext);
    this.mPackageManagerAdapter = PackageManagerAdapter.getInstance(this.mContext);
    this.mEnterpriseDumpHelper = new EnterpriseDumpHelper(this.mContext);
  }

  public final void registerReceivers() {
    registerPackageChangeReceiver();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
    intentFilter.addAction("com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL");
    this.mContext.registerReceiver(this.mBootReceiver, intentFilter);
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("android.intent.action.USER_SWITCHED");
    intentFilter2.addAction("android.intent.action.USER_REMOVED");
    intentFilter2.addAction("android.intent.action.USER_ADDED");
    intentFilter2.addAction("android.intent.action.DEVICE_LOCKED_CHANGED");
    intentFilter2.addAction("android.intent.action.BOOT_COMPLETED");
    this.mContext.registerReceiver(this.mUserReceiver, intentFilter2);
  }

  public final EnterpriseDeviceManager getEDM() {
    if (this.mEDM == null) {
      this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
    }
    return this.mEDM;
  }

  public final synchronized UniversalCredentialManagerService getUCMService() {
    if (this.mUCMService == null) {
      this.mUCMService =
          (UniversalCredentialManagerService)
              EnterpriseService.getPolicyService("knox_ucsm_policy");
    }
    return this.mUCMService;
  }

  public final ContextInfo enforceNotifyPermission(ContextInfo contextInfo) {
    return getEDM()
        .enforcePermissionByContext(
            contextInfo,
            new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CERTIFICATE")));
  }

  public final ContextInfo enforceCertificatePermission(ContextInfo contextInfo) {
    return getEDM()
        .enforceActiveAdminPermissionByContext(
            contextInfo,
            new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CERTIFICATE")));
  }

  public final boolean enforceUCMPermission(ContextInfo contextInfo, String str) {
    boolean z = false;
    if (getUCMService() != null) {
      CredentialStorage[] availableCredentialStorages =
          this.mUCMService.getAvailableCredentialStorages(contextInfo);
      if (availableCredentialStorages != null) {
        int length = availableCredentialStorages.length;
        int i = 0;
        while (true) {
          if (i < length) {
            CredentialStorage credentialStorage = availableCredentialStorages[i];
            if (credentialStorage != null && str.equals(credentialStorage.name)) {
              this.mUCMService.enforceSecurityPermission(contextInfo, credentialStorage);
              z = true;
              break;
            }
            i++;
          } else {
            break;
          }
        }
      }
      if (!z) {
        Log.d("CertificatePolicy", "Not able to find credential storage " + str);
      }
    } else {
      Log.d("CertificatePolicy", "Couldn't enforce UCM permission. Is UCM service running?");
    }
    return z;
  }

  public final void enforceSystemUser() {
    if (UserHandle.getAppId(Binder.getCallingUid()) != Process.myUid()) {
      throw new SecurityException("Can only be called by system user");
    }
  }

  public final boolean isSystem(String str) {
    if (str == null) {
      return false;
    }
    return str.startsWith("system:");
  }

  public boolean addTrustedCaCertificateList(ContextInfo contextInfo, List list) {
    return addCertificateList(enforceCertificatePermission(contextInfo), list, 0);
  }

  public boolean removeTrustedCaCertificateList(ContextInfo contextInfo, List list) {
    return removeCertificateList(enforceCertificatePermission(contextInfo), list, 2);
  }

  public List getTrustedCaCertificateList(ContextInfo contextInfo) {
    return getCertificatesList(
        UserHandle.getUserId(enforceCertificatePermission(contextInfo).mCallerUid), 4);
  }

  public boolean clearTrustedCaCertificateList(ContextInfo contextInfo) {
    return clearCertificates(enforceCertificatePermission(contextInfo).mCallerUid, 2);
  }

  public boolean addUntrustedCertificateList(ContextInfo contextInfo, List list) {
    return addCertificateList(enforceCertificatePermission(contextInfo), list, 1);
  }

  public boolean removeUntrustedCertificateList(ContextInfo contextInfo, List list) {
    return removeCertificateList(enforceCertificatePermission(contextInfo), list, 3);
  }

  public List getUntrustedCertificateList(ContextInfo contextInfo) {
    return getCertificatesList(
        UserHandle.getUserId(enforceCertificatePermission(contextInfo).mCallerUid), 5);
  }

  public boolean clearUntrustedCertificateList(ContextInfo contextInfo) {
    return clearCertificates(enforceCertificatePermission(contextInfo).mCallerUid, 3);
  }

  public boolean isCaCertificateDisabledAsUser(String str, int i) {
    return getGenericListAsUser("systemDisabledList", i).contains(str);
  }

  public boolean isCaCertificateTrustedAsUser(CertificateInfo certificateInfo, boolean z, int i) {
    return isCaCertificateTrustedAsUser(certificateInfo, z, true, i);
  }

  public boolean isCaCertificateTrustedAsUser(
      CertificateInfo certificateInfo, boolean z, boolean z2, int i) {
    boolean verifyCertificateTrustful;
    if (this.mTrustedCache.getCacheEntrySize(i) == 0
        && this.mUntrustedCache.getCacheEntrySize(i) == 0) {
      return true;
    }
    X509Certificate x509Certificate = (X509Certificate) certificateInfo.getCertificate();
    if (isSystem(this.mCertStore.getCertificateAlias(x509Certificate, true)) || !z2) {
      verifyCertificateTrustful = verifyCertificateTrustful(x509Certificate, 2, i) & true;
    } else {
      verifyCertificateTrustful = verifyCertificateTrustful(x509Certificate, 3, i) & true;
    }
    if (z && !verifyCertificateTrustful) {
      RestrictionToastManager.show(R.string.config_platformVpnConfirmDialogComponent);
    }
    return verifyCertificateTrustful;
  }

  public boolean addCertificateList(ContextInfo contextInfo, List list, int i) {
    int i2 = contextInfo.mCallerUid;
    int userId = UserHandle.getUserId(i2);
    if (list == null || list.size() == 0) {
      return false;
    }
    TrustListOperation trustListOperation = new TrustListOperation(i);
    CertificateCache cache = trustListOperation.getCache();
    int cacheEntrySize = cache.getCacheEntrySize(userId);
    List convertToX509List = CertificateUtil.convertToX509List(list);
    List installCertificates =
        installCertificates(trustListOperation.getKeystore(), convertToX509List, i, userId);
    if (installCertificates == null) {
      return false;
    }
    boolean z = (installCertificates.size() == list.size()) & true;
    List addListAsStringOnDatabase =
        addListAsStringOnDatabase(i2, installCertificates, trustListOperation.getDbColumn());
    if (addListAsStringOnDatabase.size() == 0) {
      return false;
    }
    cache.addToCache(userId, i2, addListAsStringOnDatabase);
    if (z) {
      auditLog(convertToX509List, i2, trustListOperation.getAuditMessageFormat());
    }
    updateKeystores(trustListOperation.getAction(), cacheEntrySize, i2);
    return z;
  }

  public final List installCertificates(EdmKeyStore edmKeyStore, List list, int i, int i2) {
    if (i != 1) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        X509Certificate x509Certificate = (X509Certificate) it.next();
        if (!EdmKeyStore.isCa(x509Certificate) && !EdmKeyStore.isSelfSigned(x509Certificate)) {
          return null;
        }
      }
    }
    return edmKeyStore.installCertificates(list, i2);
  }

  public final List getCertificatesList(int i, int i2) {
    TrustListOperation trustListOperation = new TrustListOperation(i2);
    if (trustListOperation.getCache().getCacheEntrySize(i) == 0) {
      return new ArrayList();
    }
    List certificatesFromDb =
        getCertificatesFromDb(
            i, trustListOperation.getDbColumn(), trustListOperation.getKeystore());
    if (i != 0) {
      certificatesFromDb.addAll(
          getCertificatesFromDb(
              0, trustListOperation.getDbColumn(), trustListOperation.getKeystore()));
    }
    return certificatesFromDb;
  }

  public final List getCertificatesFromDb(int i, String str, EdmKeyStore edmKeyStore) {
    ArrayList arrayList = new ArrayList();
    List<ContentValues> valuesListAsUser =
        this.mEdmStorageProvider.getValuesListAsUser(
            "CERTIFICATE", new String[] {"adminUid", str}, i);
    ArrayList arrayList2 = new ArrayList();
    for (ContentValues contentValues : valuesListAsUser) {
      String asString = contentValues.getAsString(str);
      if (!TextUtils.isEmpty(asString)) {
        CertificateControlInfo certificateControlInfo = new CertificateControlInfo();
        Integer asInteger = contentValues.getAsInteger("adminUid");
        if (asInteger != null) {
          certificateControlInfo.adminPackageName = getPackageNameForUid(asInteger.intValue());
        }
        arrayList2.clear();
        arrayList2.addAll(
            edmKeyStore.getCertificates(Utils.convertStringToList(asString, ","), i).values());
        certificateControlInfo.entries = arrayList2;
        arrayList.add(certificateControlInfo);
      }
    }
    return arrayList;
  }

  public boolean removeCertificateList(ContextInfo contextInfo, List list, int i) {
    int i2 = contextInfo.mCallerUid;
    if (list == null || list.size() == 0) {
      return false;
    }
    TrustListOperation trustListOperation = new TrustListOperation(i);
    List generateAliases = generateAliases(list, trustListOperation.getKeystore());
    List removeListFromDatabase =
        removeListFromDatabase(i2, generateAliases, trustListOperation.getDbColumn());
    ArrayList arrayList = new ArrayList();
    for (int i3 = 0; i3 < generateAliases.size(); i3++) {
      if (removeListFromDatabase.contains(generateAliases.get(i3))) {
        arrayList.add((CertificateInfo) list.get(i3));
      }
    }
    return removeAliases(
        trustListOperation,
        i2,
        removeListFromDatabase,
        CertificateUtil.convertToX509List(arrayList));
  }

  public final boolean removeAliases(
      TrustListOperation trustListOperation, int i, List list, List list2) {
    if (list.size() == 0) {
      return false;
    }
    int userId = UserHandle.getUserId(i);
    CertificateCache cache = trustListOperation.getCache();
    auditLog(list2, i, trustListOperation.getAuditMessageFormat());
    cache.removeFromCache(userId, i, list);
    ArrayList arrayList = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      String str = (String) it.next();
      if (!cache.isAliasPresent(str, userId)) {
        arrayList.add(str);
      }
    }
    trustListOperation.getKeystore().removeCertificates(arrayList, userId);
    updateKeystores(trustListOperation.getAction(), cache.getCacheEntrySize(userId), i);
    return true;
  }

  public List generateAliases(List list, EdmKeyStore edmKeyStore) {
    ArrayList arrayList = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      arrayList.add(
          edmKeyStore.generateAlias(
              (X509Certificate) ((CertificateInfo) it.next()).getCertificate()));
    }
    return arrayList;
  }

  public final boolean clearCertificates(int i, int i2) {
    int userId = UserHandle.getUserId(i);
    TrustListOperation trustListOperation = new TrustListOperation(i2);
    if (trustListOperation.getCache().getCacheEntrySize(userId) == 0) {
      return true;
    }
    List clearTrustDB = clearTrustDB(i, trustListOperation.getDbColumn());
    if (clearTrustDB == null || clearTrustDB.size() <= 0) {
      return false;
    }
    ArrayList arrayList = new ArrayList();
    arrayList.addAll(
        trustListOperation.getKeystore().getCertificates(clearTrustDB, userId).values());
    removeAliases(trustListOperation, i, clearTrustDB, arrayList);
    return true;
  }

  public final List clearTrustDB(int i, String str) {
    String string = this.mEdmStorageProvider.getString(i, "CERTIFICATE", str);
    if (this.mEdmStorageProvider.putString(i, "CERTIFICATE", str, null)) {
      return Utils.convertStringToList(string, ",");
    }
    return null;
  }

  public void updateKeystores(int i, int i2, int i3) {
    int userId = UserHandle.getUserId(i3);
    if (i == 0) {
      if (i2 == 0) {
        executeRollbackRefresh(1, userId);
        return;
      } else {
        executeRollbackRefresh(0, userId);
        triggerContainerOperation(userId, i3, 1, 1);
        return;
      }
    }
    if (i == 1) {
      executeRollbackRefresh(1, userId);
      return;
    }
    if (i != 2) {
      if (i != 3) {
        return;
      }
      executeRollbackRefresh(0, userId);
    } else if (i2 == 0) {
      executeRollbackRefresh(0, userId);
    } else {
      executeRollbackRefresh(1, userId);
      triggerContainerOperation(userId, i3, 0, 0);
    }
  }

  public final void triggerContainerOperation(int i, int i2, int i3, int i4) {
    if (i == 0) {
      Iterator it = getContainersForRollbackOrRefresh(i2, i4).iterator();
      while (it.hasNext()) {
        executeRollbackRefresh(i3, ((Integer) it.next()).intValue());
      }
    }
  }

  public final List getContainersForRollbackOrRefresh(int i, int i2) {
    ArrayList arrayList = new ArrayList();
    Iterator it = this.mUtils.getAllUsersId().iterator();
    while (it.hasNext()) {
      int intValue = ((Integer) it.next()).intValue();
      if (getPersonaManagerAdapter().isValidKnoxId(intValue)
          && i == this.mEdmStorageProvider.getMUMContainerOwnerUid(intValue)
          && this.mTrustedCache.getNumAliasesForUser(intValue) == i2) {
        arrayList.add(Integer.valueOf(intValue));
      }
    }
    return arrayList;
  }

  public final void auditLog(List list, int i, String str) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        X509Certificate x509Certificate = (X509Certificate) it.next();
        if (x509Certificate != null) {
          AuditLog.logAsUser(
              5,
              1,
              true,
              Process.myPid(),
              "CertificatePolicy",
              String.format(
                  str,
                  Integer.valueOf(i),
                  x509Certificate.getSubjectDN() != null
                      ? x509Certificate.getSubjectDN().toString()
                      : "null",
                  x509Certificate.getIssuerDN() != null
                      ? x509Certificate.getIssuerDN().toString()
                      : "null"),
              UserHandle.getUserId(i));
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void loadCache() {
    initCache(0);
    initCache(1);
  }

  public final void initCache(int i) {
    Integer asInteger;
    TrustListOperation trustListOperation = new TrustListOperation(i);
    CertificateCache cache = trustListOperation.getCache();
    cache.clear();
    String[] strArr = {"adminUid", trustListOperation.getDbColumn()};
    Iterator it = this.mUtils.getAllUsersId().iterator();
    while (it.hasNext()) {
      int intValue = ((Integer) it.next()).intValue();
      for (ContentValues contentValues :
          this.mEdmStorageProvider.getValuesListAsUser("CERTIFICATE", strArr, intValue)) {
        String asString = contentValues.getAsString(trustListOperation.getDbColumn());
        if (asString != null && (asInteger = contentValues.getAsInteger("adminUid")) != null) {
          cache.addToCache(
              intValue, asInteger.intValue(), Utils.convertStringToList(asString, ","));
        }
      }
    }
  }

  public boolean verifyCertificateTrustful(X509Certificate x509Certificate, int i, int i2) {
    boolean containsCertificateOrChain =
        ((i & 1) != 1 || this.mTrustedCache.getCacheEntrySize(i2) == 0)
            ? true
            : this.mTrustedKeyStore.containsCertificateOrChain(
                    this.mContext,
                    this.mTrustedCache,
                    x509Certificate,
                    i2,
                    getPersonaManagerAdapter().isValidKnoxId(i2)
                        ? this.mEdmStorageProvider.getMUMContainerOwnerUid(i2)
                        : -1)
                & true;
    return (containsCertificateOrChain
            && (i & 2) == 2
            && this.mUntrustedCache.getCacheEntrySize(i2) != 0)
        ? containsCertificateOrChain
            & (!this.mUntrustedKeyStore.containsCertificateOrChain(
                this.mContext, this.mUntrustedCache, x509Certificate, i2, r0))
        : containsCertificateOrChain;
  }

  public boolean allowUserRemoveCertificates(ContextInfo contextInfo, boolean z) {
    ContextInfo enforceCertificatePermission = enforceCertificatePermission(contextInfo);
    if (enforceCertificatePermission == null) {
      return false;
    }
    boolean putBoolean =
        this.mEdmStorageProvider.putBoolean(
            enforceCertificatePermission.mCallerUid,
            "CERTIFICATE",
            "allowUserRemoveCertificate",
            z);
    if (!putBoolean) {
      return putBoolean;
    }
    sendIntentToSettings(UserHandle.getUserId(enforceCertificatePermission.mCallerUid));
    return putBoolean;
  }

  public boolean isUserRemoveCertificatesAllowed(ContextInfo contextInfo) {
    return isUserRemoveCertificatesAllowedAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
  }

  public boolean isUserRemoveCertificatesAllowedAsUser(int i) {
    return getPolicyValueBackwardCompatibleAsUser("allowUserRemoveCertificate", true, i);
  }

  public boolean isCertificateTrustedUntrustedEnabledAsUser(int i) {
    return (this.mTrustedCache.getCacheEntrySize(i) == 0
            && this.mUntrustedCache.getCacheEntrySize(i) == 0)
        ? false
        : true;
  }

  /* JADX WARN: Removed duplicated region for block: B:10:0x0044  */
  /* JADX WARN: Removed duplicated region for block: B:16:0x004b  */
  /* JADX WARN: Removed duplicated region for block: B:20:0x0041  */
  /* JADX WARN: Removed duplicated region for block: B:8:0x003c  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean getPolicyValueBackwardCompatibleAsUser(String str, boolean z, int i) {
    boolean z2;
    if (getPersonaManagerAdapter().isValidKnoxId(i)) {
      int mUMContainerOwnerUid = this.mEdmStorageProvider.getMUMContainerOwnerUid(i);
      if (mUMContainerOwnerUid != -1) {
        try {
          z2 = this.mEdmStorageProvider.getBoolean(mUMContainerOwnerUid, "CERTIFICATE", str);
        } catch (SettingNotFoundException e) {
          Log.e("CertificatePolicy", "getPolicyValueBackwardCompatibleAsUser: " + e);
        }
        boolean strictestValueAsUser =
            i == UserHandle.getUserId(mUMContainerOwnerUid)
                ? getStrictestValueAsUser(str, z, i)
                : z2;
        if (!z) {
          if (z2 && strictestValueAsUser) {
            return true;
          }
        } else if (z2 || strictestValueAsUser) {
          return true;
        }
      }
      z2 = z;
      if (i == UserHandle.getUserId(mUMContainerOwnerUid)) {}
      if (!z) {}
    } else if (i > 0 && !getPersonaManagerAdapter().isValidKnoxId(i)) {
      boolean strictestValueAsUser2 = getStrictestValueAsUser(str, z, i);
      boolean strictestValueAsUser3 = getStrictestValueAsUser(str, z, 0);
      if (z) {
        if (strictestValueAsUser2 && strictestValueAsUser3) {
          return true;
        }
      } else if (strictestValueAsUser2 || strictestValueAsUser3) {
        return true;
      }
    } else {
      return getStrictestValueAsUser(str, z, 0);
    }
    return false;
  }

  public final boolean getStrictestValueAsUser(String str, boolean z, int i) {
    Iterator it = this.mEdmStorageProvider.getBooleanListAsUser("CERTIFICATE", str, i).iterator();
    while (it.hasNext()) {
      boolean booleanValue = ((Boolean) it.next()).booleanValue();
      if (booleanValue != z) {
        return booleanValue;
      }
    }
    return z;
  }

  public void sendIntentToSettings(int i) {
    this.mUtils.sendIntentToSettings(i, this.mBootCompleted);
  }

  public final void executeRollbackRefresh(int i, int i2) {
    Message message = new Message();
    message.what = i;
    message.arg1 = i2;
    this.mRollbackRefreshHandler.sendMessage(message);
  }

  public class RollbackRefreshHandler extends Handler {
    public RollbackRefreshHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i = message.what;
      if (i == 0 || i == 1 || i == 2) {
        CertificatePolicy.this.mRollbackRefresh.execute(message.what, message.arg1);
      } else if (i == 3) {
        CertificatePolicy.this.mRollbackRefresh.initPendingActionList();
      } else {
        if (i != 4) {
          return;
        }
        CertificatePolicy.this.mRollbackRefresh.removeUserIdFromPendingList(message.arg1);
      }
    }
  }

  public final List addListAsStringOnDatabase(int i, List list, String str) {
    StringBuilder sb = new StringBuilder();
    ArrayList arrayList = new ArrayList();
    String string = this.mEdmStorageProvider.getString(i, "CERTIFICATE", str);
    if (string == null || string.length() == 0) {
      string = "";
    } else {
      sb.append(string + ",");
    }
    Iterator it = list.iterator();
    while (it.hasNext()) {
      String str2 = (String) it.next();
      if (!string.contains(str2) && !TextUtils.isEmpty(str2)) {
        arrayList.add(str2);
        sb.append(str2 + ",");
      }
    }
    return this.mEdmStorageProvider.putString(
            i,
            "CERTIFICATE",
            str,
            sb.toString().length() > 0 ? sb.substring(0, sb.lastIndexOf(",")) : "")
        ? arrayList
        : new ArrayList();
  }

  public final List removeListFromDatabase(int i, List list, String str) {
    StringBuilder sb = new StringBuilder();
    ArrayList arrayList = new ArrayList();
    String string = this.mEdmStorageProvider.getString(i, "CERTIFICATE", str);
    if (string != null) {
      for (String str2 : new HashSet(Utils.convertStringToList(string, ","))) {
        if (list.contains(str2)) {
          arrayList.add(str2);
        } else {
          sb.append(str2 + ",");
        }
      }
      if (!this.mEdmStorageProvider.putString(
          i,
          "CERTIFICATE",
          str,
          sb.toString().length() > 0 ? sb.substring(0, sb.lastIndexOf(",")) : null)) {
        return new ArrayList();
      }
    }
    return arrayList;
  }

  public List getGenericListAsUser(String str, int i) {
    return Utils.convertStringToList(this.mEdmStorageProvider.getGenericValueAsUser(str, i), ",");
  }

  public boolean putGenericListAsUser(String str, Collection collection, int i) {
    return this.mEdmStorageProvider.putGenericValueAsUser(
        str, Utils.convertListToString(collection, ","), i);
  }

  public final String getPackageNameForUid(int i) {
    return this.mEdmStorageProvider.getPackageNameForUid(i);
  }

  public boolean enableSignatureIdentityInformation(ContextInfo contextInfo, boolean z) {
    return this.mEdmStorageProvider.putBoolean(
        enforceCertificatePermission(contextInfo).mCallerUid,
        "CERTIFICATE",
        "signatureIdentityInformationEnabled",
        z);
  }

  public boolean isSignatureIdentityInformationEnabled(ContextInfo contextInfo, boolean z) {
    Iterator it =
        this.mEdmStorageProvider
            .getBooleanListAsUser(
                "CERTIFICATE",
                "signatureIdentityInformationEnabled",
                UserHandle.getUserId(
                    checkPackageCallerOrEnforcePermission(
                            contextInfo, "com.android.packageinstaller")
                        .mCallerUid))
            .iterator();
    while (it.hasNext()) {
      if (((Boolean) it.next()).booleanValue()) {
        return true;
      }
    }
    return false;
  }

  public final ContextInfo checkPackageCallerOrEnforcePermission(
      ContextInfo contextInfo, String str) {
    if (contextInfo == null) {
      contextInfo = new ContextInfo(Binder.getCallingUid());
    }
    String nameForUid = this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid);
    return (nameForUid == null || !nameForUid.equals(str))
        ? enforceCertificatePermission(contextInfo)
        : contextInfo;
  }

  public List getIdentitiesFromSignatures(ContextInfo contextInfo, Signature[] signatureArr) {
    ArrayList arrayList = new ArrayList();
    for (Signature signature : signatureArr) {
      try {
        try {
          SslCertificate sslCertificate =
              new SslCertificate(
                  (X509Certificate)
                      CertificateFactory.getInstance("X509")
                          .generateCertificate(new ByteArrayInputStream(signature.toByteArray())));
          String cName = sslCertificate.getIssuedTo().getCName();
          String oName = sslCertificate.getIssuedTo().getOName();
          String uName = sslCertificate.getIssuedTo().getUName();
          if (!oName.isEmpty()) {
            if (!cName.isEmpty()) {
              uName = cName;
            }
            cName = oName;
          } else {
            uName = "";
            if (cName.isEmpty()) {
              cName = sslCertificate.getIssuedTo().getDName();
            }
          }
          arrayList.add(new String[] {cName, uName});
        } catch (CertificateException unused) {
          Log.w("CertificatePolicy", "X509Certificate error");
          return arrayList;
        }
      } catch (CertificateException unused2) {
        Log.w("CertificatePolicy", "CertificateFactory error");
      }
    }
    return arrayList;
  }

  public final void registerPackageChangeReceiver() {
    try {
      if (this.mPackageChangeIntentReceiver == null) {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        BroadcastReceiver broadcastReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.server.enterprise.certificate.CertificatePolicy.2
              @Override // android.content.BroadcastReceiver
              public void onReceive(Context context, Intent intent) {
                String packageName = CertificatePolicy.this.getPackageName(intent);
                String action = intent.getAction();
                boolean booleanExtra = intent.getBooleanExtra("isMarketInstallation", false);
                if (packageName != null) {
                  try {
                    String trim = packageName.trim();
                    if (trim.length() <= 0 || action == null) {
                      return;
                    }
                    String trim2 = action.trim();
                    if (trim2.length() > 0
                        && trim2.equals("android.intent.action.PACKAGE_ADDED")
                        && booleanExtra) {
                      CertificatePolicy.this.displayAppSignature(trim);
                    }
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
              }
            };
        this.mPackageChangeIntentReceiver = broadcastReceiver;
        this.mContext.registerReceiver(broadcastReceiver, intentFilter);
        Log.d("CertificatePolicy", "registerPackageChangeReceiver() : Done");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void displayAppSignature(String str) {
    PackageInfo packageInfo;
    ApplicationInfo applicationInfo;
    int callingUid = Binder.getCallingUid();
    if (isSignatureIdentityInformationEnabled(null, false)) {
      try {
        packageInfo =
            this.mPackageManagerAdapter.getPackageInfo(str, 8768, UserHandle.getUserId(callingUid));
      } catch (Exception e) {
        e.printStackTrace();
        packageInfo = null;
      }
      if (packageInfo == null || packageInfo.signatures.length <= 0) {
        return;
      }
      try {
        applicationInfo = this.mPackageManager.getApplicationInfo(str, 0);
      } catch (PackageManager.NameNotFoundException unused) {
        Log.w("CertificatePolicy", "Name not found");
        applicationInfo = null;
      }
      String string =
          applicationInfo != null
              ? this.mContext.getString(
                  17042828, this.mPackageManager.getApplicationLabel(applicationInfo).toString())
              : null;
      StringBuilder sb = new StringBuilder();
      for (String[] strArr : getIdentitiesFromSignatures(null, packageInfo.signatures)) {
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append(strArr[0]);
        if (!strArr[1].isEmpty()) {
          sb.append(" / ");
          sb.append(strArr[1]);
        }
      }
      String str2 = string + sb.toString();
      NotificationManager notificationManager =
          (NotificationManager) this.mContext.getSystemService("notification");
      long currentTimeMillis = System.currentTimeMillis();
      String replace = sb.toString().replace(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE, "");
      Notification notification =
          new Notification(R.drawable.stat_notify_error, str2, currentTimeMillis);
      notification.flags |= 16;
      Intent intent =
          new Intent(
              "android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", str, null));
      intent.putExtra("appInfoPkgName", str);
      intent.setFlags(268435456);
      notification.setLatestEventInfo(
          this.mContext,
          string,
          replace,
          PendingIntent.getActivity(this.mContext, 0, intent, 67108864));
      notificationManager.notify(this.mUtils.getRandomInt(), notification);
    }
  }

  public final String getPackageName(Intent intent) {
    Uri data = intent.getData();
    if (data != null) {
      return data.getSchemeSpecificPart();
    }
    return null;
  }

  public boolean enableCertificateFailureNotification(ContextInfo contextInfo, boolean z) {
    return this.mEdmStorageProvider.putBoolean(
        enforceCertificatePermission(contextInfo).mCallerUid,
        "CERTIFICATE",
        "notificateSignatureFailureToUser",
        z);
  }

  public boolean isCertificateFailureNotificationEnabled(ContextInfo contextInfo) {
    enforceCertificatePermission(contextInfo);
    return getPolicyValueBackwardCompatibleAsUser(
        "notificateSignatureFailureToUser", false, Utils.getCallingOrCurrentUserId(contextInfo));
  }

  public void notifyCertificateFailure(String str, String str2, boolean z) {
    notifyCertificateFailureAsUser(str, str2, z, Utils.getCallingOrCurrentUserId(null));
  }

  public void notifyCertificateFailureAsUser(String str, String str2, boolean z, final int i) {
    try {
      enforceNotifyPermission(new ContextInfo(Binder.getCallingUid()));
    } catch (SecurityException unused) {
      EnterprisePermissionChecker.getInstance(this.mContext)
          .enforceAuthorization("CertificatePolicy", "notifyCertificateFailure");
    }
    final String[] obtainMsgFromModule = obtainMsgFromModule(str, str2);
    final Intent intent = new Intent("com.samsung.android.knox.intent.action.CERTIFICATE_FAILURE");
    intent.putExtra(
        "com.samsung.android.knox.intent.extra.CERTIFICATE_FAILURE_MODULE", obtainMsgFromModule[0]);
    intent.putExtra(
        "com.samsung.android.knox.intent.extra.CERTIFICATE_FAILURE_MESSAGE",
        obtainMsgFromModule[1]);
    intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", i);
    new Thread() { // from class: com.android.server.enterprise.certificate.CertificatePolicy.3
      @Override // java.lang.Thread, java.lang.Runnable
      public void run() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          int i2 = i;
          if (CertificatePolicy.this.getPersonaManagerAdapter().isValidKnoxId(i)) {
            i2 =
                UserHandle.getUserId(
                    CertificatePolicy.this.mEdmStorageProvider.getMUMContainerOwnerUid(i));
          }
          if (CertificatePolicy.this.mBootCompleted) {
            Log.d(
                "CertificatePolicy",
                "Sending certificate failure intent to user "
                    + i2
                    + " containing: "
                    + obtainMsgFromModule[0]
                    + " (module), "
                    + obtainMsgFromModule[1]
                    + " (message), "
                    + i
                    + " (userId)");
            CertificatePolicy.this.mContext.sendBroadcastAsUser(
                intent, new UserHandle(i2), "com.samsung.android.knox.permission.KNOX_CERTIFICATE");
          }
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      }
    }.start();
    if (getPolicyValueBackwardCompatibleAsUser("notificateSignatureFailureToUser", false, i) && z) {
      RestrictionToastManager.show(
          this.mContext.getString(R.string.gpsNotifTitle, Integer.valueOf(i))
              + " "
              + obtainMsgFromModule[0]
              + " - "
              + obtainMsgFromModule[1]);
    }
  }

  public final IPersonaManagerAdapter getPersonaManagerAdapter() {
    return (IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class);
  }

  public final void onUserRemoved(int i) {
    synchronized (this.mLock) {
      this.mNativeKeyStore.cleanUid(i);
      this.mUserKeyStore.cleanUid(i);
      if (this.mRollbackRefresh.hasPendingActionForUser(i)) {
        executeRollbackRefresh(4, i);
      }
      this.mTrustedCache.removeUserFromCache(i);
      this.mUntrustedCache.removeUserFromCache(i);
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:46:0x00e1, code lost:

     if (r7 != 13) goto L74;
  */
  /* JADX WARN: Code restructure failed: missing block: B:82:0x016b, code lost:

     if (r7 != 11) goto L74;
  */
  /* JADX WARN: Code restructure failed: missing block: B:96:0x01a0, code lost:

     if (r7 != 13) goto L74;
  */
  /* JADX WARN: Removed duplicated region for block: B:13:0x01c5  */
  /* JADX WARN: Removed duplicated region for block: B:16:0x01e3  */
  /* JADX WARN: Removed duplicated region for block: B:17:0x0201  */
  /* JADX WARN: Removed duplicated region for block: B:18:0x021f  */
  /* JADX WARN: Removed duplicated region for block: B:19:0x023d  */
  /* JADX WARN: Removed duplicated region for block: B:20:0x025b  */
  /* JADX WARN: Removed duplicated region for block: B:21:0x0279  */
  /* JADX WARN: Removed duplicated region for block: B:22:0x0297  */
  /* JADX WARN: Removed duplicated region for block: B:23:0x02b5  */
  /* JADX WARN: Removed duplicated region for block: B:24:0x02d3  */
  /* JADX WARN: Removed duplicated region for block: B:25:0x02f0  */
  /* JADX WARN: Removed duplicated region for block: B:26:0x030d  */
  /* JADX WARN: Removed duplicated region for block: B:27:0x032a  */
  /* JADX WARN: Removed duplicated region for block: B:28:0x0347  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final String[] obtainMsgFromModule(String str, String str2) {
    int i;
    Log.w("CertificatePolicy", "obtainMsgFromModule");
    int i2 = 2;
    String[] strArr = new String[2];
    strArr[1] = this.mContext.getString(R.string.config_networkOverLimitComponent);
    int i3 = -1;
    if (str.equals("wifi_module")) {
      strArr[0] = this.mContext.getString(17043367);
      String[] split = str2.split(" ");
      Log.w("CertificatePolicy", "eventReason " + Arrays.toString(split));
      if (split.length > 1) {
        String str3 = split[1];
        Log.w("CertificatePolicy", "reasonStr " + str3);
        if (str3.startsWith("reason=")) {
          try {
            i = Integer.parseInt(str3.substring(7));
          } catch (NumberFormatException e) {
            Log.w("CertificatePolicy", "error converting reason to integer " + e);
          }
        }
      }
      i2 = -1;
      Log.w("CertificatePolicy", "reason " + i2);
      switch (i2) {
        case 1:
          strArr[1] =
              strArr[1] + this.mContext.getString(R.string.config_persistentDataPackageName);
          return strArr;
        case 2:
          strArr[1] = strArr[1] + this.mContext.getString(R.string.config_packagedKeyboardName);
          return strArr;
        case 3:
          strArr[1] =
              strArr[1] + this.mContext.getString(R.string.config_overrideComponentUiPackage);
          return strArr;
        case 4:
          strArr[1] =
              strArr[1]
                  + this.mContext.getString(R.string.config_oem_enabled_satellite_s2cell_file);
          return strArr;
        case 5:
          strArr[1] =
              strArr[1]
                  + this.mContext.getString(
                      R.string.config_pdp_reject_multi_conn_to_same_pdn_not_allowed);
          return strArr;
        case 6:
          strArr[1] =
              strArr[1] + this.mContext.getString(R.string.config_notificationHandlerPackage);
          return strArr;
        case 7:
          strArr[1] =
              strArr[1]
                  + this.mContext.getString(R.string.config_oemCredentialManagerDialogComponent);
          return strArr;
        case 8:
          strArr[1] = strArr[1] + this.mContext.getString(R.string.config_pdp_reject_dialog_title);
          return strArr;
        case 9:
          strArr[1] =
              strArr[1]
                  + this.mContext.getString(R.string.config_notificationAccessConfirmationActivity);
          return strArr;
        case 10:
          strArr[1] =
              strArr[1]
                  + this.mContext.getString(
                      R.string.config_onDeviceIntelligenceModelUnloadedBroadcastKey);
          return strArr;
        case 11:
          strArr[1] =
              strArr[1]
                  + this.mContext.getString(R.string.config_oem_enabled_satellite_sos_handover_app);
          return strArr;
        case 12:
          strArr[1] =
              strArr[1]
                  + this.mContext.getString(
                      R.string.config_onDeviceIntelligenceModelLoadedBroadcastKey);
          return strArr;
        case 13:
          strArr[1] =
              strArr[1]
                  + this.mContext.getString(R.string.config_pdp_reject_service_not_subscribed);
          return strArr;
        default:
          strArr[1] =
              strArr[1]
                  + this.mContext.getString(R.string.config_pdp_reject_user_authentication_failed);
          return strArr;
      }
    }
    if (str.equals("browser_module")) {
      strArr[0] = this.mContext.getString(R.string.config_display_features);
      if (str2 != null) {
        try {
          i3 = Integer.parseInt(str2);
        } catch (NumberFormatException e2) {
          Log.w("CertificatePolicy", "error converting reason to integer " + e2);
        }
        Log.w("CertificatePolicy", "errorCode " + i3);
        if (i3 != 1) {
          if (i3 != 2) {
            if (i3 != 3) {
              if (i3 != 4) {
                i2 = 5;
                if (i3 != 5) {}
              }
              i2 = 4;
            }
            i2 = 3;
          }
        }
        i2 = 1;
      }
      i2 = -1;
    } else if (str.equals("package_manager_module")) {
      strArr[0] = this.mContext.getString(R.string.roamingText2);
      if (!TextUtils.isEmpty(str2)) {
        try {
          i3 = Integer.parseInt(str2);
        } catch (NumberFormatException e3) {
          Log.w("CertificatePolicy", "error converting reason to integer " + e3);
        }
        switch (i3) {
          case -105:
            i2 = 11;
            break;
          case -104:
            i = 12;
            break;
          case -103:
            i2 = 10;
            break;
          default:
            i2 = 0;
            break;
        }
      }
      i2 = -1;
    } else if (str.equals("installer_module")) {
      strArr[0] = this.mContext.getString(R.string.mmcc_authentication_reject_msim_template);
      if (!TextUtils.isEmpty(str2)) {
        try {
          i3 = Integer.parseInt(str2);
        } catch (NumberFormatException e4) {
          Log.w("CertificatePolicy", "error converting reason to integer " + e4);
        }
        if (i3 != 1) {
          if (i3 != 2) {
            if (i3 != 3) {
              if (i3 != 4) {
                i2 = 8;
                if (i3 != 8) {
                  if (i3 != 13) {
                    if (i3 != 10) {}
                    i2 = 10;
                  }
                  i2 = 13;
                }
              }
              i2 = 4;
            }
            i2 = 3;
          }
        }
        i2 = 1;
      }
      i2 = -1;
    } else {
      if (str.equals("Chrome_module")) {
        strArr[0] = "CHROME";
        if (!TextUtils.isEmpty(str2)) {
          try {
            i3 = Integer.parseInt(str2);
          } catch (NumberFormatException e5) {
            Log.w("CertificatePolicy", "error converting reason to integer " + e5);
          }
          if (i3 != 1) {
            if (i3 != 2) {
              if (i3 != 3) {
                if (i3 != 4) {}
                i2 = 4;
              }
              i2 = 3;
            }
          }
          i2 = 1;
        }
      }
      i2 = -1;
    }
    Log.w("CertificatePolicy", "reason " + i2);
    switch (i2) {
    }
    i2 = i;
    Log.w("CertificatePolicy", "reason " + i2);
    switch (i2) {
    }
  }

  public final ApplicationPolicy getApplicationPolicy() {
    return (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
  }

  public final List getPackagesForPid(int i) {
    ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      List<ActivityManager.RunningAppProcessInfo> runningAppProcesses =
          activityManager.getRunningAppProcesses();
      Binder.restoreCallingIdentity(clearCallingIdentity);
      if (runningAppProcesses == null) {
        return null;
      }
      for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
        if (runningAppProcessInfo.pid == i) {
          String[] strArr = runningAppProcessInfo.pkgList;
          if (strArr != null) {
            return Arrays.asList(strArr);
          }
          return null;
        }
      }
      return null;
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public boolean isRevocationCheckEnabled(ContextInfo contextInfo) {
    List packagesForPid = getPackagesForPid(Binder.getCallingPid());
    if (packagesForPid != null
        && packagesForPid.contains("com.android.certinstaller")
        && isCertificateValidationAtInstallEnabled(contextInfo)) {
      return ((Boolean) this.mCheckRevocation.get()).booleanValue();
    }
    ApplicationPolicy applicationPolicy = getApplicationPolicy();
    if (packagesForPid != null && applicationPolicy != null) {
      Iterator it = packagesForPid.iterator();
      while (it.hasNext()) {
        if (applicationPolicy.isRevocationCheckEnabled(contextInfo, (String) it.next())
            || applicationPolicy.isRevocationCheckEnabled(contextInfo, "*")) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isOcspCheckEnabled(ContextInfo contextInfo) {
    List packagesForPid = getPackagesForPid(Binder.getCallingPid());
    if (packagesForPid != null
        && packagesForPid.contains("com.android.certinstaller")
        && isCertificateValidationAtInstallEnabled(contextInfo)) {
      return ((Boolean) this.mCheckRevocation.get()).booleanValue();
    }
    ApplicationPolicy applicationPolicy = getApplicationPolicy();
    if (packagesForPid != null && applicationPolicy != null) {
      Iterator it = packagesForPid.iterator();
      while (it.hasNext()) {
        if (applicationPolicy.isOcspCheckEnabled(contextInfo, (String) it.next())
            || applicationPolicy.isOcspCheckEnabled(contextInfo, "*")) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean enableCertificateValidationAtInstall(ContextInfo contextInfo, boolean z) {
    boolean putBoolean =
        this.mEdmStorageProvider.putBoolean(
            enforceCertificatePermission(contextInfo).mCallerUid,
            "CERTIFICATE",
            "validateCertificateAtInstall",
            z);
    if (putBoolean) {
      sendCertificatePolicyCacheUpdateCommand(this.mContext, "CERTIFICATE_VALIDATION");
    }
    return putBoolean;
  }

  public boolean isCertificateValidationAtInstallEnabled(ContextInfo contextInfo) {
    return isCertificateValidationAtInstallEnabledAsUser(
        Utils.getCallingOrCurrentUserId(contextInfo));
  }

  public boolean isCertificateValidationAtInstallEnabledAsUser(int i) {
    return getPolicyValueBackwardCompatibleAsUser("validateCertificateAtInstall", false, i);
  }

  public int validateCertificateAtInstall(CertificateInfo certificateInfo) {
    return validateCertificateAtInstallAsUser(
        certificateInfo, Utils.getCallingOrCurrentUserId(null));
  }

  public int validateCertificateAtInstallAsUser(CertificateInfo certificateInfo, int i) {
    return validateCerts(i, (X509Certificate) certificateInfo.getCertificate());
  }

  public int validateChainAtInstall(List list) {
    return validateChainAtInstallAsUser(list, Utils.getCallingOrCurrentUserId(null));
  }

  public int validateChainAtInstallAsUser(List list, int i) {
    int validateCerts =
        validateCerts(
            i, (X509Certificate) ((CertificateInfo) list.get(list.size() - 1)).getCertificate());
    if (validateCerts != -1) {
      return validateCerts;
    }
    ArrayList arrayList = new ArrayList(list.size());
    Iterator it = list.iterator();
    while (it.hasNext()) {
      arrayList.add((X509Certificate) ((CertificateInfo) it.next()).getCertificate());
    }
    return validateCerts(
        i, (X509Certificate[]) arrayList.toArray(new X509Certificate[arrayList.size()]));
  }

  public final int validateCerts(int i, X509Certificate... x509CertificateArr) {
    X509Certificate x509Certificate;
    Throwable cause;
    List asList = Arrays.asList(x509CertificateArr);
    try {
      List<? extends Certificate> arrayList = new ArrayList<>();
      if (asList.size() == 1) {
        arrayList = getCertificateChainFromSystem((X509Certificate) asList.get(0), i);
      } else {
        arrayList.addAll(asList);
      }
      if (arrayList.size() == 1) {
        this.mCheckRevocation.set(Boolean.FALSE);
        x509Certificate = (X509Certificate) arrayList.get(0);
      } else {
        this.mCheckRevocation.set(Boolean.TRUE);
        x509Certificate = (X509Certificate) arrayList.remove(arrayList.size() - 1);
      }
      Set singleton = Collections.singleton(new TrustAnchor(x509Certificate, null));
      CertPath generateCertPath =
          CertificateFactory.getInstance("X.509").generateCertPath(arrayList);
      try {
        PKIXParameters pKIXParameters = new PKIXParameters((Set<TrustAnchor>) singleton);
        try {
          CertPathValidator certPathValidator = CertPathValidator.getInstance("PKIX");
          pKIXParameters.setRevocationEnabled(false);
          try {
            certPathValidator.validate(generateCertPath, pKIXParameters);
            Log.d("CertificatePolicy", "Validation success");
            return -1;
          } catch (InvalidAlgorithmParameterException e) {
            Log.e("CertificatePolicy", "Should not happen!" + e);
            return 0;
          } catch (CertPathValidatorException e2) {
            String message = e2.getMessage();
            Log.e("CertificatePolicy", "Validation failed: " + message);
            if (message.contains("Additional certificate path checker failed.")
                && (cause = e2.getCause()) != null
                && cause.getMessage() != null) {
              message = cause.getMessage();
            }
            if (message.contains("is revoked")
                || message.contains("Certificate revocation after")) {
              return 2;
            }
            if (message.contains("OCSP check failed!")
                || message.contains("Certificate status could not be determined.")
                || message.contains("CRL distribution point extension could not be read")
                || message.contains(
                    "No additional CRL locations could be decoded from CRL distribution point"
                        + " extension.")
                || message.contains("Distribution points could not be read.")
                || message.contains("No valid CRL found.")
                || message.contains("Unable to get CRL for certificate")) {
              return 13;
            }
            if (message.contains(", expiration time")
                || (e2.getCause() instanceof CertificateExpiredException)) {
              return 4;
            }
            return (message.contains(", validation time")
                    || (e2.getCause() instanceof CertificateNotYetValidException))
                ? 3
                : 0;
          }
        } catch (NoSuchAlgorithmException e3) {
          Log.d("CertificatePolicy", "Should not happen!" + e3);
          return 0;
        }
      } catch (InvalidAlgorithmParameterException e4) {
        Log.e("CertificatePolicy", "Should not happen!" + e4);
        return 0;
      }
    } catch (AssertionError e5) {
      Log.e("CertificatePolicy", "If FIPS mode is turned on, cannot use MD5 algorithm : " + e5);
      return 0;
    } catch (CertificateException e6) {
      Log.e("CertificatePolicy", "Failure generating cert path: " + e6);
      return 8;
    }
  }

  public final List getCertificateChainFromSystem(X509Certificate x509Certificate, int i) {
    ArrayList arrayList = new ArrayList();
    arrayList.add(x509Certificate);
    int i2 = 0;
    while (true) {
      X509Certificate x509Certificate2 = (X509Certificate) arrayList.get(i2);
      if (x509Certificate2
          .getSubjectX500Principal()
          .equals(x509Certificate2.getIssuerX500Principal())) {
        return arrayList;
      }
      X509Certificate findIssuerInAndroidKeystore =
          findIssuerInAndroidKeystore(this.mContext, x509Certificate2, i);
      if (findIssuerInAndroidKeystore == null) {
        Log.d("CertificatePolicy", "getCertificateChain error. Could not find certificate.");
        throw new CertificateException(
            "Could not build certificate chain; certificate not found: "
                + x509Certificate2.getIssuerX500Principal().getName());
      }
      arrayList.add(findIssuerInAndroidKeystore);
      i2++;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:36:0x00be, code lost:

     if (r7 == null) goto L25;
  */
  /* JADX WARN: Code restructure failed: missing block: B:5:0x0082, code lost:

     if (r7 != null) goto L24;
  */
  /* JADX WARN: Removed duplicated region for block: B:41:0x00c6  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static X509Certificate findIssuerInAndroidKeystore(
      Context context, X509Certificate x509Certificate, int i) {
    KeyChain.KeyChainConnection keyChainConnection;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    Context context2 = null;
    r4 = null;
    r4 = null;
    r4 = null;
    r4 = null;
    r4 = null;
    r4 = null;
    r4 = null;
    r4 = null;
    r4 = null;
    X509Certificate x509Certificate2 = null;
    try {
      try {
        keyChainConnection = KeyChain.bindAsUser(context, new UserHandle(i));
        if (keyChainConnection != null) {
          try {
            IKeyChainService service = keyChainConnection.getService();
            if (service != null) {
              try {
                byte[] findIssuer =
                    service.findIssuer(
                        Credentials.convertToPem(new Certificate[] {x509Certificate}));
                if (findIssuer != null) {
                  X509Certificate x509Certificate3 =
                      (X509Certificate)
                          CertificateFactory.getInstance("X.509")
                              .generateCertificate(new ByteArrayInputStream(findIssuer));
                  if (x509Certificate3 != null) {
                    keyChainConnection.close();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return x509Certificate3;
                  }
                  x509Certificate2 = x509Certificate3;
                }
              } catch (RemoteException e) {
                Log.e("CertificatePolicy", "findIssuerInAndroidKeystore " + e);
              } catch (IOException e2) {
                Log.e("CertificatePolicy", "findIssuerInAndroidKeystore " + e2);
              } catch (CertificateException e3) {
                Log.e("CertificatePolicy", "findIssuerInAndroidKeystore " + e3);
              }
            }
          } catch (AssertionError unused) {
            Log.e(
                "CertificatePolicy",
                "findIssuerInAndroidKeystore - is KeyChainService running for user " + i + "?");
            if (keyChainConnection != null) {
              keyChainConnection.close();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return x509Certificate2;
          } catch (InterruptedException e4) {
            e = e4;
            Log.e("CertificatePolicy", "findIssuerInAndroidKeystore " + e);
          }
        }
      } catch (Throwable th) {
        th = th;
        context2 = context;
        if (context2 != null) {
          context2.close();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    } catch (AssertionError unused2) {
      keyChainConnection = null;
    } catch (InterruptedException e5) {
      e = e5;
      keyChainConnection = null;
    } catch (Throwable th2) {
      th = th2;
      if (context2 != null) {}
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public void notifyCertificateRemovedAsUser(String str, int i) {
    enforceSystemUser();
    Intent intent = new Intent("com.samsung.android.knox.intent.action.CERTIFICATE_REMOVED");
    intent.putExtra("com.samsung.android.knox.intent.extra.CERTIFICATE_REMOVED_SUBJECT", str);
    intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", i);
    int userId =
        getPersonaManagerAdapter().isValidKnoxId(i)
            ? UserHandle.getUserId(this.mEdmStorageProvider.getMUMContainerOwnerUid(i))
            : i;
    Log.d(
        "CertificatePolicy",
        "Sending certificate removed intent to user "
            + userId
            + " containing: "
            + str
            + " (subject), "
            + i
            + " (userId)");
    this.mContext.sendBroadcastAsUser(
        intent, new UserHandle(userId), "com.samsung.android.knox.permission.KNOX_CERTIFICATE");
    SecContentProviderUtil.notifyPolicyChangesAsUser(
        this.mContext, "CertificatePolicy/certificateRemoved", i);
  }

  public boolean addPermissionApplicationPrivateKey(
      ContextInfo contextInfo, PermissionApplicationPrivateKey permissionApplicationPrivateKey) {
    int i = enforceCertificatePermission(contextInfo).mCallerUid;
    PermissionApplicationPrivateKey validatePkey = validatePkey(permissionApplicationPrivateKey);
    if (validatePkey == null) {
      return false;
    }
    if (!TextUtils.isEmpty(validatePkey.getStorageName())
        && !enforceUCMPermission(contextInfo, validatePkey.getStorageName())) {
      return false;
    }
    int userId = UserHandle.getUserId(i);
    if (validatePkey.getHost().equals("*")) {
      for (PermissionApplicationPrivateKey permissionApplicationPrivateKey2 :
          retrieveAppPermissionsFromDb(userId)) {
        if (validatePkey
                .getPackageName()
                .equalsIgnoreCase(permissionApplicationPrivateKey2.getPackageName())
            && permissionApplicationPrivateKey2.getHost().equals("*")) {
          Log.e(
              "CertificatePolicy",
              "Operation not allowed, another rule for given package name has host set to"
                  + " wildcard");
          return false;
        }
      }
    }
    boolean privateKeyGrant =
        setPrivateKeyGrant(
            validatePkey.getPackageName(),
            userId,
            validatePkey.getAlias(),
            validatePkey.getStorageName(),
            true);
    if (!privateKeyGrant) {
      return privateKeyGrant;
    }
    ContentValues contentValues = toContentValues(validatePkey);
    contentValues.put("adminUid", Integer.valueOf(i));
    return this.mEdmStorageProvider.insert("PermAppPrivateKey", contentValues) > 0;
  }

  public boolean removePermissionApplicationPrivateKey(
      ContextInfo contextInfo, PermissionApplicationPrivateKey permissionApplicationPrivateKey) {
    int i = enforceCertificatePermission(contextInfo).mCallerUid;
    PermissionApplicationPrivateKey validatePkey = validatePkey(permissionApplicationPrivateKey);
    if (validatePkey == null) {
      return false;
    }
    ContentValues contentValues = toContentValues(validatePkey);
    contentValues.put("adminUid", Integer.valueOf(i));
    boolean z = this.mEdmStorageProvider.delete("PermAppPrivateKey", contentValues) > 0;
    return z
        ? z
            & setPrivateKeyGrant(
                validatePkey.getPackageName(),
                UserHandle.getUserId(i),
                validatePkey.getAlias(),
                validatePkey.getStorageName(),
                false)
        : z;
  }

  public boolean clearPermissionApplicationPrivateKey(ContextInfo contextInfo) {
    return clearPermissionApplicationPrivateKey(
        enforceCertificatePermission(contextInfo).mCallerUid);
  }

  public final boolean clearPermissionApplicationPrivateKey(int i) {
    ContentValues contentValues = new ContentValues();
    contentValues.put("adminUid", Integer.valueOf(i));
    for (ContentValues contentValues2 :
        this.mEdmStorageProvider.getValues(
            "PermAppPrivateKey", new String[] {"pkgName", "alias", "storageName"}, contentValues)) {
      setPrivateKeyGrant(
          contentValues2.getAsString("pkgName"),
          UserHandle.getUserId(i),
          contentValues2.getAsString("alias"),
          contentValues2.getAsString("storageName"),
          false);
    }
    return this.mEdmStorageProvider.delete("PermAppPrivateKey", contentValues) > 0;
  }

  public List getListPermissionApplicationPrivateKey(ContextInfo contextInfo) {
    enforceCertificatePermission(contextInfo);
    return retrieveAppPermissionsFromDb(Utils.getCallingOrCurrentUserId(contextInfo));
  }

  public String isPrivateKeyApplicationPermitted(
      ContextInfo contextInfo, String str, String str2, int i, List list) {
    return isPrivateKeyApplicationPermittedAsUser(
        str, str2, i, list, Utils.getCallingOrCurrentUserId(contextInfo));
  }

  public String isPrivateKeyApplicationPermittedAsUser(
      String str, String str2, int i, List list, int i2) {
    String validStr = getValidStr(str);
    String str3 = null;
    if (validStr != null) {
      for (PermissionApplicationPrivateKey permissionApplicationPrivateKey :
          retrieveAppPermissionsFromDb(i2)) {
        if (validStr.equalsIgnoreCase(permissionApplicationPrivateKey.getPackageName())) {
          if (permissionApplicationPrivateKey.getHost().equals("*")) {
            str3 =
                validateAlias(
                    permissionApplicationPrivateKey.getStorageName(),
                    permissionApplicationPrivateKey.getAlias(),
                    validStr,
                    i2,
                    list);
          } else {
            str2 = getValidStr(str2);
            if (str2 != null) {
              String host = permissionApplicationPrivateKey.getHost();
              if (!host.startsWith(".")) {
                host = "." + host;
              }
              if (!str2.equalsIgnoreCase(permissionApplicationPrivateKey.getHost())) {
                Locale locale = Locale.ENGLISH;
                if (!str2.toLowerCase(locale).endsWith(host.toLowerCase(locale))) {}
              }
              if (permissionApplicationPrivateKey.getPort() == -1
                  || i == permissionApplicationPrivateKey.getPort()) {
                str3 =
                    validateAlias(
                        permissionApplicationPrivateKey.getStorageName(),
                        permissionApplicationPrivateKey.getAlias(),
                        validStr,
                        i2,
                        list);
              }
            }
          }
        }
        if (str3 != null) {
          break;
        }
      }
    }
    return str3;
  }

  public final String validateAlias(String str, String str2, String str3, int i, List list) {
    String str4;
    UniversalCredentialUtil universalCredentialUtil;
    if (TextUtils.isEmpty(str)) {
      Log.d(
          "CertificatePolicy",
          " validateAlias called. storage name : null or empty, alias : "
              + str2
              + ", packageName : "
              + str3
              + ", userId : "
              + i);
      str4 = str2;
    } else {
      Log.d(
          "CertificatePolicy",
          " validateAlias called. storage name : "
              + str
              + ", alias : "
              + str2
              + ", packageName : "
              + str3
              + ", userId : "
              + i);
      PackageInfo packageInfo = this.mPackageManagerAdapter.getPackageInfo(str3, 0, i);
      if (packageInfo == null
          || packageInfo.applicationInfo == null
          || (universalCredentialUtil = UniversalCredentialUtil.getInstance()) == null) {
        str4 = null;
      } else {
        UniversalCredentialUtil.UcmUriBuilder uid =
            new UniversalCredentialUtil.UcmUriBuilder(str)
                .setResourceId(1)
                .setUid(packageInfo.applicationInfo.uid);
        String build = uid.build();
        str4 = uid.setAlias(str2).build();
        Bundle saw = universalCredentialUtil.saw(build, 1);
        if (saw != null) {
          String[] stringArray = saw.getStringArray("stringarrayresponse");
          Log.d("CertificatePolicy", "statusCode - " + saw.getInt("errorresponse", -1));
          if (stringArray == null) {
            return null;
          }
          list = Arrays.asList(stringArray);
        }
      }
    }
    if (list == null) {
      list = Collections.emptyList();
    }
    if (list.contains(str2)) {
      return str4;
    }
    return null;
  }

  public final List retrieveAppPermissionsFromDb(int i) {
    ArrayList arrayList = new ArrayList();
    ContentValues contentValues = new ContentValues();
    contentValues.put(EdmStorageProviderBase.getAdminLUIDWhereIn(0, i), "#SelectClause#");
    Iterator it =
        this.mEdmStorageProvider
            .getValues(
                "PermAppPrivateKey",
                new String[] {"adminUid", "pkgName", "host", "port", "alias", "storageName"},
                contentValues)
            .iterator();
    while (it.hasNext()) {
      PermissionApplicationPrivateKey fromContentValues =
          fromContentValues((ContentValues) it.next());
      if (fromContentValues != null) {
        arrayList.add(fromContentValues);
      }
    }
    return arrayList;
  }

  public final PermissionApplicationPrivateKey validatePkey(
      PermissionApplicationPrivateKey permissionApplicationPrivateKey) {
    if (permissionApplicationPrivateKey == null) {
      return null;
    }
    String validStr = getValidStr(permissionApplicationPrivateKey.getPackageName());
    String validStr2 = getValidStr(permissionApplicationPrivateKey.getHost());
    String validStr3 = getValidStr(permissionApplicationPrivateKey.getAlias());
    int port = permissionApplicationPrivateKey.getPort();
    String validStr4 = getValidStr(permissionApplicationPrivateKey.getStorageName());
    if (validStr == null || validStr2 == null || validStr3 == null) {
      return null;
    }
    return new PermissionApplicationPrivateKey(validStr, validStr2, port, validStr3, validStr4);
  }

  public static String getValidStr(String str) {
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

  public final ContentValues toContentValues(
      PermissionApplicationPrivateKey permissionApplicationPrivateKey) {
    ContentValues contentValues = new ContentValues();
    contentValues.put("pkgName", permissionApplicationPrivateKey.getPackageName());
    contentValues.put("host", permissionApplicationPrivateKey.getHost());
    contentValues.put("port", Integer.valueOf(permissionApplicationPrivateKey.getPort()));
    contentValues.put("alias", permissionApplicationPrivateKey.getAlias());
    if (permissionApplicationPrivateKey.getStorageName() != null) {
      contentValues.put("storageName", permissionApplicationPrivateKey.getStorageName());
    }
    return contentValues;
  }

  public final PermissionApplicationPrivateKey fromContentValues(ContentValues contentValues) {
    PermissionApplicationPrivateKey permissionApplicationPrivateKey = null;
    if (contentValues == null || contentValues.size() <= 0) {
      return null;
    }
    try {
      Long asLong = contentValues.getAsLong("adminUid");
      String asString = contentValues.getAsString("pkgName");
      String asString2 = contentValues.getAsString("host");
      Integer asInteger = contentValues.getAsInteger("port");
      if (asInteger == null) {
        asInteger = -1;
      }
      PermissionApplicationPrivateKey permissionApplicationPrivateKey2 =
          new PermissionApplicationPrivateKey(
              asString,
              asString2,
              asInteger.intValue(),
              contentValues.getAsString("alias"),
              contentValues.getAsString("storageName"));
      if (asLong != null) {
        try {
          permissionApplicationPrivateKey2.setAdminPkgName(
              getPackageNameForUid(EdmStorageProviderBase.getAdminUidFromLUID(asLong.longValue())));
        } catch (Exception e) {
          e = e;
          permissionApplicationPrivateKey = permissionApplicationPrivateKey2;
          Log.d("CertificatePolicy", "Parsing ContentValues error" + e);
          return permissionApplicationPrivateKey;
        }
      }
      return permissionApplicationPrivateKey2;
    } catch (Exception e2) {
      e = e2;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:29:0x0058, code lost:

     if (r6 != null) goto L20;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean setPrivateKeyGrant(String str, int i, String str2, String str3, boolean z) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      boolean z2 = false;
      ApplicationInfo applicationInfo =
          PackageManagerAdapter.getInstance(this.mContext).getApplicationInfo(str, 0, i);
      Binder.restoreCallingIdentity(clearCallingIdentity);
      if (applicationInfo == null) {
        return !z;
      }
      int i2 = applicationInfo.uid;
      clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        try {
          KeyChain.KeyChainConnection bindAsUser =
              KeyChain.bindAsUser(this.mContext, new UserHandle(i));
          if (bindAsUser != null) {
            if (str3 != null) {
              try {
                str2 =
                    new UniversalCredentialUtil.UcmUriBuilder(str3)
                        .setResourceId(1)
                        .setUid(i2)
                        .setAlias(str2)
                        .build();
              } catch (RemoteException | IllegalArgumentException unused) {
                bindAsUser.close();
                if (z2) {
                  str.equals(KnoxCustomManagerService.SBROWSER_CHAMELEON_PACKAGE_NAME);
                }
                return z2;
              } catch (Throwable th) {
                bindAsUser.close();
                throw th;
              }
            }
            bindAsUser.getService().setGrant(i2, str2, z);
            z2 = true;
          }
        } catch (Throwable th2) {
          throw th2;
        }
      } catch (AssertionError unused2) {
        Log.e(
            "CertificatePolicy",
            "setPrivateKeyGrant - is KeyChainService running for user " + i + "?");
        return false;
      } catch (InterruptedException unused3) {
        return false;
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminRemoved(int i) {
    sendCertificatePolicyCacheUpdateCommand(this.mContext, null);
    sendIntentToSettings(UserHandle.getUserId(i));
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void systemReady() {
    performUpgrade();
  }

  public void performUpgrade() {
    String genericValue = this.mEdmStorageProvider.getGenericValue("cert_migration");
    if (genericValue == null || !"ok".equals(genericValue)) {
      performKeystoreUpgrade();
      performGenericTableUpgrade();
      this.mEdmStorageProvider.putGenericValue("cert_migration", "ok");
    }
  }

  public final void performKeystoreUpgrade() {
    this.mTrustedKeyStore.performKeystoreUpgrade();
    this.mUntrustedKeyStore.performKeystoreUpgrade();
    this.mUserKeyStore.performKeystoreUpgrade();
    this.mNativeKeyStore.performKeystoreUpgrade();
  }

  public final void performGenericTableUpgrade() {
    int i;
    Iterator it = this.mUtils.getAllUsersId().iterator();
    while (it.hasNext()) {
      int intValue = ((Integer) it.next()).intValue();
      List<String> genericListAsUser = getGenericListAsUser("userRemovedList", intValue);
      ArrayList arrayList = new ArrayList();
      if (genericListAsUser != null) {
        for (String str : genericListAsUser) {
          int indexOf = str.indexOf("_");
          if (indexOf != -1) {
            if (Utils.isInteger(str.substring(0, indexOf)) && str.length() > (i = indexOf + 1)) {
              arrayList.add(str.substring(i));
            } else {
              arrayList.add(str);
            }
          } else {
            arrayList.add(str);
          }
        }
      }
      putGenericListAsUser("userRemovedList", arrayList, intValue);
    }
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onPreAdminRemoval(int i) {
    Log.d("CertificatePolicy", "onPreAdminRemoval...");
    clearCertificates(i, 2);
    clearCertificates(i, 3);
  }

  public void notifyUserKeystoreUnlocked(int i) {
    if (this.mRollbackRefresh.hasPendingActionForUser(i)) {
      executeRollbackRefresh(4, i);
      executeRollbackRefresh(2, i);
    }
  }

  public class TrustListOperation {
    public int mAction;
    public String mAuditMessageFormat;
    public CertificateCache mCache;
    public String mDbColumn;
    public EdmKeyStore mKeyStore;

    public TrustListOperation(int i) {
      this.mAction = i;
      if (i != 0) {
        if (i != 1) {
          if (i == 2) {
            this.mAuditMessageFormat =
                "Admin %d has removed a certificate from the trusted DB. Subject : %s, Issuer : %s";
            this.mKeyStore = CertificatePolicy.this.mTrustedKeyStore;
            this.mDbColumn = "trustedCaList";
            this.mCache = CertificatePolicy.this.mTrustedCache;
            return;
          }
          if (i == 3) {
            this.mAuditMessageFormat =
                "Admin %d has removed a certificate from the untrusted DB. Subject : %s, Issuer :"
                    + " %s";
            this.mKeyStore = CertificatePolicy.this.mUntrustedKeyStore;
            this.mDbColumn = "untrustedCertsList";
            this.mCache = CertificatePolicy.this.mUntrustedCache;
            return;
          }
          if (i != 4) {
            if (i != 5) {
              return;
            }
          }
        }
        this.mAuditMessageFormat =
            "Admin %d has added a certificate to the untrusted DB. Subject : %s, Issuer : %s";
        this.mKeyStore = CertificatePolicy.this.mUntrustedKeyStore;
        this.mDbColumn = "untrustedCertsList";
        this.mCache = CertificatePolicy.this.mUntrustedCache;
        return;
      }
      this.mAuditMessageFormat =
          "Admin %d has added a certificate to the trusted DB. Subject : %s, Issuer : %s";
      this.mKeyStore = CertificatePolicy.this.mTrustedKeyStore;
      this.mDbColumn = "trustedCaList";
      this.mCache = CertificatePolicy.this.mTrustedCache;
    }

    public CertificateCache getCache() {
      return this.mCache;
    }

    public String getDbColumn() {
      return this.mDbColumn;
    }

    public EdmKeyStore getKeystore() {
      return this.mKeyStore;
    }

    public int getAction() {
      return this.mAction;
    }

    public String getAuditMessageFormat() {
      return this.mAuditMessageFormat;
    }
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
      printWriter.println("Permission Denial: can't dump CertificatePolicy");
      return;
    }
    this.mTrustedCache.dump(printWriter, "[Trusted Cache]" + System.lineSeparator());
    this.mUntrustedCache.dump(printWriter, "[Untrusted Cache]" + System.lineSeparator());
    this.mTrustedKeyStore.dump(printWriter, "[Trusted Keystore]" + System.lineSeparator());
    this.mUserKeyStore.dump(printWriter, "[User Keystore]" + System.lineSeparator());
    this.mNativeKeyStore.dump(printWriter, "[Native Keystore]" + System.lineSeparator());
    this.mUntrustedKeyStore.dump(printWriter, "[Untrusted Keystore]" + System.lineSeparator());
    this.mEnterpriseDumpHelper.dumpTable(
        printWriter,
        "PermAppPrivateKey",
        new String[] {"adminUid", "pkgName", "host", "port", "alias", "storageName"});
    this.mEnterpriseDumpHelper.dumpTable(
        printWriter,
        "CERTIFICATE",
        new String[] {
          "trustedCaList",
          "untrustedCertsList",
          "signatureIdentityInformationEnabled",
          "notificateSignatureFailureToUser",
          "validateCertificateAtInstall",
          "allowUserRemoveCertificate"
        });
    this.mEnterpriseDumpHelper.dumpTable(
        printWriter,
        "generic",
        new String[] {
          "systemDisabledList",
          "systemPrevDisabledList",
          "userRemovedList",
          "nativeRemovedList",
          "nativeRemovedList_wifi"
        });
  }

  public static void sendCertificatePolicyCacheUpdateCommand(Context context, String str) {
    Intent intent =
        new Intent("com.samsung.android.knox.intent.action.CERTIFICATE_POLICY_CHANGED_INTERNAL");
    if (str != null) {
      intent.putExtra(
          "com.samsung.android.knox.intent.extra.CERTIFICATE_POLICY_TYPE_CHANGED_INTERNAL", str);
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      context.sendBroadcastAsUser(intent, UserHandle.ALL);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }
}
