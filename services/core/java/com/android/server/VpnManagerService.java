package com.android.server;

import android.annotation.SystemApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.INetd;
import android.net.IVpnManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.ProxyInfo;
import android.net.UnderlyingNetworkInfo;
import android.net.Uri;
import android.net.VpnProfileState;
import android.net.util.NetdService;
import android.os.Binder;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.INetworkManagementService;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.net.KnoxVpnProfile;
import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import com.android.internal.net.VpnProfile;
import com.android.internal.util.DumpUtils;
import com.android.net.module.util.PermissionUtils;
import com.android.server.connectivity.EnterpriseVpn;
import com.android.server.connectivity.Vpn;
import com.android.server.connectivity.VpnProfileStore;
import com.android.server.net.LockdownVpnTracker;
import com.android.server.pm.UserManagerInternal;
import com.google.android.collect.Lists;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.vpn.IKnoxVpnPolicy;
import com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class VpnManagerService extends IVpnManager.Stub {
  public final ConnectivityManager mCm;
  public final Context mContext;
  public final Dependencies mDeps;
  public final Handler mHandler;
  protected final HandlerThread mHandlerThread;
  public boolean mLockdownEnabled;
  public LockdownVpnTracker mLockdownTracker;
  public final int mMainUserId;
  public final INetworkManagementService mNMS;
  public final INetd mNetd;
  public final Context mUserAllContext;
  public final UserManager mUserManager;
  public final VpnProfileStore mVpnProfileStore;
  public static final String TAG = VpnManagerService.class.getSimpleName();
  public static final boolean DBG = Debug.semIsProductDev();
  protected final SparseArray mVpns = new SparseArray();
  public BroadcastReceiver mIntentReceiver =
      new BroadcastReceiver() { // from class: com.android.server.VpnManagerService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          VpnManagerService.this.ensureRunningOnHandlerThread();
          String action = intent.getAction();
          int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
          int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
          Uri data = intent.getData();
          String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
          if ("com.android.server.action.LOCKDOWN_RESET".equals(action)) {
            VpnManagerService.this.onVpnLockdownReset();
            return;
          }
          if (intExtra == -10000) {
            return;
          }
          if ("android.intent.action.USER_STARTED".equals(action)) {
            VpnManagerService.this.onUserStarted(intExtra);
            return;
          }
          if ("android.intent.action.USER_STOPPED".equals(action)) {
            VpnManagerService.this.onUserStopped(intExtra);
            return;
          }
          if ("android.intent.action.USER_ADDED".equals(action)) {
            VpnManagerService.this.onUserAdded(intExtra);
            return;
          }
          if ("android.intent.action.USER_REMOVED".equals(action)) {
            VpnManagerService.this.onUserRemoved(intExtra);
            return;
          }
          if ("android.intent.action.USER_UNLOCKED".equals(action)) {
            VpnManagerService.this.onUserUnlocked(intExtra);
            return;
          }
          if ("android.intent.action.PACKAGE_REPLACED".equals(action)) {
            VpnManagerService.this.onPackageReplaced(schemeSpecificPart, intExtra2);
            return;
          }
          if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
            VpnManagerService.this.onPackageRemoved(
                schemeSpecificPart,
                intExtra2,
                intent.getBooleanExtra("android.intent.extra.REPLACING", false));
            return;
          }
          if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            VpnManagerService.this.onPackageAdded(
                schemeSpecificPart,
                intExtra2,
                intent.getBooleanExtra("android.intent.extra.REPLACING", false));
            return;
          }
          Log.wtf(VpnManagerService.TAG, "received unexpected intent: " + action);
        }
      };
  public BroadcastReceiver mUserPresentReceiver =
      new BroadcastReceiver() { // from class: com.android.server.VpnManagerService.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          VpnManagerService.this.ensureRunningOnHandlerThread();
          VpnManagerService.this.updateLockdownVpn();
          context.unregisterReceiver(this);
        }
      };
  public final SparseArray mEnterpriseVpnStoreObj = new SparseArray();
  public final Object knoxVpnLock = new Object();
  public SemPersonaManager mPersonaManager = null;
  public final boolean mIsKnoxContainerV30 =
      "v30".equals(SystemProperties.get("ro.config.knox", "0"));
  public NetworkCallback mDefaultNetworkCallback = null;

  public LegacyVpnInfo getLegacyKnoxVpnInfo(int i) {
    return null;
  }

  public void startLegacyKnoxVpn(int i, KnoxVpnProfile knoxVpnProfile) {}

  public void stopLegacyKnoxVpn(int i, String str, String str2) {}

  public class Dependencies {
    public int getCallingUid() {
      return Binder.getCallingUid();
    }

    public HandlerThread makeHandlerThread() {
      return new HandlerThread("VpnManagerService");
    }

    public VpnProfileStore getVpnProfileStore() {
      return new VpnProfileStore();
    }

    public INetd getNetd() {
      return NetdService.getInstance();
    }

    public INetworkManagementService getINetworkManagementService() {
      return INetworkManagementService.Stub.asInterface(
          ServiceManager.getService("network_management"));
    }

    public Vpn createVpn(
        Looper looper,
        Context context,
        INetworkManagementService iNetworkManagementService,
        INetd iNetd,
        int i) {
      return new Vpn(looper, context, iNetworkManagementService, iNetd, i, new VpnProfileStore());
    }

    public LockdownVpnTracker createLockDownVpnTracker(
        Context context, Handler handler, Vpn vpn, VpnProfile vpnProfile) {
      return new LockdownVpnTracker(context, handler, vpn, vpnProfile);
    }

    public int getMainUserId() {
      return ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class))
          .getMainUserId();
    }
  }

  public VpnManagerService(Context context, Dependencies dependencies) {
    Context createAttributionContext = context.createAttributionContext("VPN_MANAGER");
    this.mContext = createAttributionContext;
    this.mDeps = dependencies;
    HandlerThread makeHandlerThread = dependencies.makeHandlerThread();
    this.mHandlerThread = makeHandlerThread;
    makeHandlerThread.start();
    this.mHandler = makeHandlerThread.getThreadHandler();
    this.mVpnProfileStore = dependencies.getVpnProfileStore();
    this.mUserAllContext = createAttributionContext.createContextAsUser(UserHandle.ALL, 0);
    this.mCm =
        (ConnectivityManager) createAttributionContext.getSystemService(ConnectivityManager.class);
    this.mNMS = dependencies.getINetworkManagementService();
    this.mNetd = dependencies.getNetd();
    this.mUserManager = (UserManager) createAttributionContext.getSystemService(UserManager.class);
    this.mMainUserId = dependencies.getMainUserId();
    registerReceivers();
    log("VpnManagerService starting up");
  }

  public static VpnManagerService create(Context context) {
    return new VpnManagerService(context, new Dependencies());
  }

  public void systemReady() {
    updateLockdownVpn();
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
      IndentingPrintWriter indentingPrintWriter =
          new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
      indentingPrintWriter.println("VPNs:");
      indentingPrintWriter.increaseIndent();
      synchronized (this.mVpns) {
        for (int i = 0; i < this.mVpns.size(); i++) {
          indentingPrintWriter.println(
              this.mVpns.keyAt(i) + ": " + ((Vpn) this.mVpns.valueAt(i)).getPackage());
          indentingPrintWriter.increaseIndent();
          ((Vpn) this.mVpns.valueAt(i)).dump(indentingPrintWriter);
          indentingPrintWriter.decreaseIndent();
          indentingPrintWriter.println();
        }
        indentingPrintWriter.decreaseIndent();
      }
    }
  }

  public boolean prepareVpn(String str, String str2, int i) {
    enforceCrossUserPermission(i);
    synchronized (this.mVpns) {
      throwIfLockdownEnabled();
      Vpn vpn = (Vpn) this.mVpns.get(i);
      if (vpn == null) {
        return false;
      }
      return vpn.prepare(str, str2, 1);
    }
  }

  public void setVpnPackageAuthorization(String str, int i, int i2) {
    enforceCrossUserPermission(i);
    synchronized (this.mVpns) {
      Vpn vpn = (Vpn) this.mVpns.get(i);
      if (vpn != null) {
        vpn.setPackageAuthorization(str, i2);
      }
    }
  }

  public ParcelFileDescriptor establishVpn(VpnConfig vpnConfig) {
    ParcelFileDescriptor establish;
    int userId = UserHandle.getUserId(this.mDeps.getCallingUid());
    int callingUid = Binder.getCallingUid();
    String packageNameFromUid = getPackageNameFromUid(callingUid);
    String str = TAG;
    Log.d(str, "establishVpn called by " + packageNameFromUid);
    if (checkKnoxVpnProfileType(vpnConfig.session, callingUid, userId) == 1) {
      Log.d(str, "establishVpn called by knox per app vpn");
      establish = establishEnterpriseVpn(vpnConfig, userId);
    } else {
      synchronized (this.mVpns) {
        Log.d(str, "establishVpn called by non-knox vpn");
        throwIfLockdownEnabled();
        establish = ((Vpn) this.mVpns.get(userId)).establish(vpnConfig);
      }
    }
    if (DBG) {
      Log.d(str, "establishVpn: config.session value is " + vpnConfig.session);
    }
    return establish;
  }

  public boolean addVpnAddress(String str, int i) {
    boolean addAddress;
    int userId = UserHandle.getUserId(this.mDeps.getCallingUid());
    synchronized (this.mVpns) {
      throwIfLockdownEnabled();
      addAddress = ((Vpn) this.mVpns.get(userId)).addAddress(str, i);
    }
    return addAddress;
  }

  public boolean removeVpnAddress(String str, int i) {
    boolean removeAddress;
    int userId = UserHandle.getUserId(this.mDeps.getCallingUid());
    synchronized (this.mVpns) {
      throwIfLockdownEnabled();
      removeAddress = ((Vpn) this.mVpns.get(userId)).removeAddress(str, i);
    }
    return removeAddress;
  }

  public boolean setUnderlyingNetworksForVpn(Network[] networkArr) {
    boolean underlyingNetworks;
    int userId = UserHandle.getUserId(this.mDeps.getCallingUid());
    synchronized (this.mVpns) {
      underlyingNetworks = ((Vpn) this.mVpns.get(userId)).setUnderlyingNetworks(networkArr);
    }
    return underlyingNetworks;
  }

  public boolean provisionVpnProfile(VpnProfile vpnProfile, String str) {
    boolean provisionVpnProfile;
    int userId = UserHandle.getUserId(this.mDeps.getCallingUid());
    synchronized (this.mVpns) {
      provisionVpnProfile = ((Vpn) this.mVpns.get(userId)).provisionVpnProfile(str, vpnProfile);
    }
    return provisionVpnProfile;
  }

  public void deleteVpnProfile(String str) {
    int userId = UserHandle.getUserId(this.mDeps.getCallingUid());
    synchronized (this.mVpns) {
      ((Vpn) this.mVpns.get(userId)).deleteVpnProfile(str);
    }
  }

  public final int getAppUid(String str, int i) {
    PackageManager packageManager = this.mContext.getPackageManager();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      int packageUidAsUser = packageManager.getPackageUidAsUser(str, i);
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return packageUidAsUser;
    } catch (PackageManager.NameNotFoundException unused) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return -1;
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public final void verifyCallingUidAndPackage(String str, int i) {
    if (getAppUid(str, UserHandle.getUserId(i)) == i) {
      return;
    }
    throw new SecurityException(str + " does not belong to uid " + i);
  }

  public String startVpnProfile(String str) {
    String startVpnProfile;
    int callingUid = Binder.getCallingUid();
    verifyCallingUidAndPackage(str, callingUid);
    int userId = UserHandle.getUserId(callingUid);
    synchronized (this.mVpns) {
      throwIfLockdownEnabled();
      startVpnProfile = ((Vpn) this.mVpns.get(userId)).startVpnProfile(str);
    }
    return startVpnProfile;
  }

  public void stopVpnProfile(String str) {
    int callingUid = Binder.getCallingUid();
    verifyCallingUidAndPackage(str, callingUid);
    int userId = UserHandle.getUserId(callingUid);
    synchronized (this.mVpns) {
      ((Vpn) this.mVpns.get(userId)).stopVpnProfile(str);
    }
  }

  public VpnProfileState getProvisionedVpnProfileState(String str) {
    VpnProfileState provisionedVpnProfileState;
    int callingUid = Binder.getCallingUid();
    verifyCallingUidAndPackage(str, callingUid);
    int userId = UserHandle.getUserId(callingUid);
    synchronized (this.mVpns) {
      provisionedVpnProfileState =
          ((Vpn) this.mVpns.get(userId)).getProvisionedVpnProfileState(str);
    }
    return provisionedVpnProfileState;
  }

  public void startLegacyVpn(VpnProfile vpnProfile) {
    LinkProperties linkProperties;
    if (Build.VERSION.DEVICE_INITIAL_SDK_INT >= 31 && VpnProfile.isLegacyType(vpnProfile.type)) {
      throw new UnsupportedOperationException("Legacy VPN is deprecated");
    }
    int userId = UserHandle.getUserId(this.mDeps.getCallingUid());
    if (knoxVpnProfileType(vpnProfile.name) == 1) {
      linkProperties = this.mDefaultNetworkCallback.getLinkProperties();
    } else {
      ConnectivityManager connectivityManager = this.mCm;
      linkProperties =
          connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork());
    }
    if (linkProperties == null) {
      throw new IllegalStateException("Missing active network connection");
    }
    synchronized (this.mVpns) {
      throwIfLockdownEnabled();
      ((Vpn) this.mVpns.get(userId)).startLegacyVpn(vpnProfile, null, linkProperties);
    }
  }

  public LegacyVpnInfo getLegacyVpnInfo(int i) {
    enforceCrossUserPermission(i);
    synchronized (this.mVpns) {
      Vpn vpn = (Vpn) this.mVpns.get(i);
      if (vpn == null) {
        return null;
      }
      return vpn.getLegacyVpnInfo();
    }
  }

  public VpnConfig getVpnConfig(int i) {
    enforceCrossUserPermission(i);
    synchronized (this.mVpns) {
      Vpn vpn = (Vpn) this.mVpns.get(i);
      if (vpn == null) {
        return null;
      }
      return vpn.getVpnConfig();
    }
  }

  public final boolean isLockdownVpnEnabled() {
    return this.mVpnProfileStore.get("LOCKDOWN_VPN") != null;
  }

  public boolean updateLockdownVpn() {
    if (this.mDeps.getCallingUid() != 1000
        && this.mDeps.getCallingUid() != UserHandle.getUid(this.mMainUserId, 1000)
        && Binder.getCallingPid() != Process.myPid()) {
      logw("Lockdown VPN only available to system process or AID_SYSTEM on main user");
      return false;
    }
    synchronized (this.mVpns) {
      boolean isLockdownVpnEnabled = isLockdownVpnEnabled();
      this.mLockdownEnabled = isLockdownVpnEnabled;
      if (!isLockdownVpnEnabled) {
        setLockdownTracker(null);
        return true;
      }
      byte[] bArr = this.mVpnProfileStore.get("LOCKDOWN_VPN");
      if (bArr == null) {
        loge("Lockdown VPN configured but cannot be read from keystore");
        return false;
      }
      String str = new String(bArr);
      VpnProfile decode = VpnProfile.decode(str, this.mVpnProfileStore.get("VPN_" + str));
      if (decode == null) {
        loge("Lockdown VPN configured invalid profile " + str);
        setLockdownTracker(null);
        return true;
      }
      int userId = UserHandle.getUserId(this.mDeps.getCallingUid());
      Vpn vpn = (Vpn) this.mVpns.get(userId);
      if (vpn == null) {
        logw("VPN for user " + userId + " not ready yet. Skipping lockdown");
        return false;
      }
      setLockdownTracker(
          this.mDeps.createLockDownVpnTracker(this.mContext, this.mHandler, vpn, decode));
      return true;
    }
  }

  public final void setLockdownTracker(LockdownVpnTracker lockdownVpnTracker) {
    LockdownVpnTracker lockdownVpnTracker2 = this.mLockdownTracker;
    this.mLockdownTracker = null;
    if (lockdownVpnTracker2 != null) {
      lockdownVpnTracker2.shutdown();
    }
    if (lockdownVpnTracker != null) {
      this.mLockdownTracker = lockdownVpnTracker;
      lockdownVpnTracker.init();
    }
  }

  public final void throwIfLockdownEnabled() {
    if (this.mLockdownEnabled) {
      throw new IllegalStateException("Unavailable in lockdown mode");
    }
  }

  public final boolean startAlwaysOnVpn(int i) {
    synchronized (this.mVpns) {
      Vpn vpn = (Vpn) this.mVpns.get(i);
      if (vpn == null) {
        Log.wtf(TAG, "User " + i + " has no Vpn configuration");
        return false;
      }
      return vpn.startAlwaysOnVpn();
    }
  }

  public boolean isAlwaysOnVpnPackageSupported(int i, String str) {
    enforceSettingsPermission();
    enforceCrossUserPermission(i);
    synchronized (this.mVpns) {
      Vpn vpn = (Vpn) this.mVpns.get(i);
      if (vpn == null) {
        logw("User " + i + " has no Vpn configuration");
        return false;
      }
      return vpn.isAlwaysOnPackageSupported(str);
    }
  }

  public boolean setAlwaysOnVpnPackage(int i, String str, boolean z, List list) {
    enforceControlAlwaysOnVpnPermission();
    enforceCrossUserPermission(i);
    synchronized (this.mVpns) {
      if (isLockdownVpnEnabled()) {
        return false;
      }
      Vpn vpn = (Vpn) this.mVpns.get(i);
      if (vpn == null) {
        logw("User " + i + " has no Vpn configuration");
        return false;
      }
      if (!vpn.setAlwaysOnPackage(str, z, list)) {
        return false;
      }
      if (startAlwaysOnVpn(i)) {
        return true;
      }
      vpn.setAlwaysOnPackage(null, false, null);
      return false;
    }
  }

  public String getAlwaysOnVpnPackage(int i) {
    enforceControlAlwaysOnVpnPermission();
    enforceCrossUserPermission(i);
    synchronized (this.mVpns) {
      Vpn vpn = (Vpn) this.mVpns.get(i);
      if (vpn == null) {
        logw("User " + i + " has no Vpn configuration");
        return null;
      }
      return vpn.getAlwaysOnPackage();
    }
  }

  public boolean isVpnLockdownEnabled(int i) {
    enforceControlAlwaysOnVpnPermission();
    enforceCrossUserPermission(i);
    synchronized (this.mVpns) {
      Vpn vpn = (Vpn) this.mVpns.get(i);
      if (vpn == null) {
        logw("User " + i + " has no Vpn configuration");
        return false;
      }
      return vpn.getLockdown();
    }
  }

  public List getVpnLockdownAllowlist(int i) {
    enforceControlAlwaysOnVpnPermission();
    enforceCrossUserPermission(i);
    synchronized (this.mVpns) {
      Vpn vpn = (Vpn) this.mVpns.get(i);
      if (vpn == null) {
        logw("User " + i + " has no Vpn configuration");
        return null;
      }
      return vpn.getLockdownAllowlist();
    }
  }

  public final Vpn getVpnIfOwner() {
    return getVpnIfOwner(this.mDeps.getCallingUid());
  }

  public final Vpn getVpnIfOwner(int i) {
    Vpn vpn = (Vpn) this.mVpns.get(UserHandle.getUserId(i));
    if (vpn == null) {
      return null;
    }
    UnderlyingNetworkInfo underlyingNetworkInfo = vpn.getUnderlyingNetworkInfo();
    if (underlyingNetworkInfo == null || underlyingNetworkInfo.getOwnerUid() != i) {
      return null;
    }
    return vpn;
  }

  public final void registerReceivers() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.USER_STARTED");
    intentFilter.addAction("android.intent.action.USER_STOPPED");
    intentFilter.addAction("android.intent.action.USER_ADDED");
    intentFilter.addAction("android.intent.action.USER_REMOVED");
    intentFilter.addAction("android.intent.action.USER_UNLOCKED");
    this.mUserAllContext.registerReceiver(this.mIntentReceiver, intentFilter, null, this.mHandler);
    this.mContext
        .createContextAsUser(UserHandle.of(this.mMainUserId), 0)
        .registerReceiver(
            this.mUserPresentReceiver,
            new IntentFilter("android.intent.action.USER_PRESENT"),
            null,
            this.mHandler);
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
    intentFilter2.addAction("android.intent.action.PACKAGE_REPLACED");
    intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
    intentFilter2.addDataScheme("package");
    this.mUserAllContext.registerReceiver(this.mIntentReceiver, intentFilter2, null, this.mHandler);
    IntentFilter intentFilter3 = new IntentFilter();
    intentFilter3.addAction("com.android.server.action.LOCKDOWN_RESET");
    this.mUserAllContext.registerReceiver(
        this.mIntentReceiver, intentFilter3, "android.permission.NETWORK_STACK", this.mHandler, 2);
  }

  public final void onUserStarted(int i) {
    if (this.mUserManager.getUserInfo(i) == null) {
      logw("Started user doesn't exist. UserId: " + i);
      return;
    }
    synchronized (this.mVpns) {
      if (((Vpn) this.mVpns.get(i)) != null) {
        loge("Starting user already has a VPN");
        return;
      }
      this.mVpns.put(
          i,
          this.mDeps.createVpn(this.mHandler.getLooper(), this.mContext, this.mNMS, this.mNetd, i));
      if (i == this.mMainUserId && isLockdownVpnEnabled()) {
        updateLockdownVpn();
      }
    }
  }

  public final void onUserStopped(int i) {
    synchronized (this.mVpns) {
      Vpn vpn = (Vpn) this.mVpns.get(i);
      if (vpn == null) {
        loge("Stopped user has no VPN");
      } else {
        vpn.onUserStopped();
        this.mVpns.delete(i);
      }
    }
  }

  public boolean isCallerCurrentAlwaysOnVpnApp() {
    boolean z;
    synchronized (this.mVpns) {
      Vpn vpnIfOwner = getVpnIfOwner();
      z = vpnIfOwner != null && vpnIfOwner.getAlwaysOn();
    }
    return z;
  }

  public boolean isCallerCurrentAlwaysOnVpnLockdownApp() {
    boolean z;
    synchronized (this.mVpns) {
      Vpn vpnIfOwner = getVpnIfOwner();
      z = vpnIfOwner != null && vpnIfOwner.getLockdown();
    }
    return z;
  }

  public final void onUserAdded(int i) {
    synchronized (this.mVpns) {
      int size = this.mVpns.size();
      for (int i2 = 0; i2 < size; i2++) {
        ((Vpn) this.mVpns.valueAt(i2)).onUserAdded(i);
      }
    }
  }

  public final void onUserRemoved(int i) {
    synchronized (this.mVpns) {
      int size = this.mVpns.size();
      for (int i2 = 0; i2 < size; i2++) {
        ((Vpn) this.mVpns.valueAt(i2)).onUserRemoved(i);
      }
    }
  }

  public final void onPackageReplaced(String str, int i) {
    if (TextUtils.isEmpty(str) || i < 0) {
      Log.wtf(TAG, "Invalid package in onPackageReplaced: " + str + " | " + i);
      return;
    }
    int userId = UserHandle.getUserId(i);
    synchronized (this.mVpns) {
      Vpn vpn = (Vpn) this.mVpns.get(userId);
      if (vpn == null) {
        return;
      }
      if (TextUtils.equals(vpn.getAlwaysOnPackage(), str)) {
        log("Restarting always-on VPN package " + str + " for user " + userId);
        vpn.startAlwaysOnVpn();
      }
    }
  }

  public final void onPackageRemoved(String str, int i, boolean z) {
    if (TextUtils.isEmpty(str) || i < 0) {
      Log.wtf(TAG, "Invalid package in onPackageRemoved: " + str + " | " + i);
      return;
    }
    int userId = UserHandle.getUserId(i);
    synchronized (this.mVpns) {
      Vpn vpn = (Vpn) this.mVpns.get(userId);
      if (vpn != null && !z) {
        if (TextUtils.equals(vpn.getAlwaysOnPackage(), str)) {
          log("Removing always-on VPN package " + str + " for user " + userId);
          vpn.setAlwaysOnPackage(null, false, null);
        }
        vpn.refreshPlatformVpnAppExclusionList();
      }
    }
  }

  public final void onPackageAdded(String str, int i, boolean z) {
    if (TextUtils.isEmpty(str) || i < 0) {
      Log.wtf(TAG, "Invalid package in onPackageAdded: " + str + " | " + i);
      return;
    }
    int userId = UserHandle.getUserId(i);
    synchronized (this.mVpns) {
      Vpn vpn = (Vpn) this.mVpns.get(userId);
      if (vpn != null && !z) {
        vpn.refreshPlatformVpnAppExclusionList();
      }
    }
  }

  public final void onUserUnlocked(int i) {
    synchronized (this.mVpns) {
      if (i == this.mMainUserId && isLockdownVpnEnabled()) {
        updateLockdownVpn();
      } else {
        startAlwaysOnVpn(i);
      }
    }
  }

  public final void onVpnLockdownReset() {
    synchronized (this.mVpns) {
      LockdownVpnTracker lockdownVpnTracker = this.mLockdownTracker;
      if (lockdownVpnTracker != null) {
        lockdownVpnTracker.reset();
      }
    }
  }

  public boolean setAppExclusionList(int i, String str, List list) {
    boolean appExclusionList;
    enforceSettingsPermission();
    enforceCrossUserPermission(i);
    synchronized (this.mVpns) {
      Vpn vpn = (Vpn) this.mVpns.get(i);
      if (vpn != null) {
        appExclusionList = vpn.setAppExclusionList(str, list);
      } else {
        logw("User " + i + " has no Vpn configuration");
        throw new IllegalStateException(
            "VPN for user " + i + " not ready yet. Skipping setting the list");
      }
    }
    return appExclusionList;
  }

  public List getAppExclusionList(int i, String str) {
    enforceSettingsPermission();
    enforceCrossUserPermission(i);
    synchronized (this.mVpns) {
      Vpn vpn = (Vpn) this.mVpns.get(i);
      if (vpn != null) {
        return vpn.getAppExclusionList(str);
      }
      logw("User " + i + " has no Vpn configuration");
      return null;
    }
  }

  public void factoryReset() {
    enforceSettingsPermission();
    if (this.mUserManager.hasUserRestriction("no_network_reset")
        || this.mUserManager.hasUserRestriction("no_config_vpn")) {
      return;
    }
    int callingUserId = UserHandle.getCallingUserId();
    synchronized (this.mVpns) {
      String alwaysOnVpnPackage = getAlwaysOnVpnPackage(callingUserId);
      if (alwaysOnVpnPackage != null) {
        setAlwaysOnVpnPackage(callingUserId, null, false, null);
        setVpnPackageAuthorization(alwaysOnVpnPackage, callingUserId, -1);
      }
      if (this.mLockdownEnabled && callingUserId == this.mMainUserId) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          this.mVpnProfileStore.remove("LOCKDOWN_VPN");
          this.mLockdownEnabled = false;
          setLockdownTracker(null);
          Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
          Binder.restoreCallingIdentity(clearCallingIdentity);
          throw th;
        }
      }
      VpnConfig vpnConfig = getVpnConfig(callingUserId);
      if (vpnConfig != null) {
        if (vpnConfig.legacy) {
          prepareVpn("[Legacy VPN]", "[Legacy VPN]", callingUserId);
        } else {
          setVpnPackageAuthorization(vpnConfig.user, callingUserId, -1);
          prepareVpn(null, "[Legacy VPN]", callingUserId);
        }
      }
    }
  }

  public final void ensureRunningOnHandlerThread() {
    if (this.mHandler.getLooper().getThread() == Thread.currentThread()) {
      return;
    }
    throw new IllegalStateException(
        "Not running on VpnManagerService thread: " + Thread.currentThread().getName());
  }

  public final void enforceControlAlwaysOnVpnPermission() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONTROL_ALWAYS_ON_VPN", "VpnManagerService");
  }

  public final void enforceCrossUserPermission(int i) {
    if (i == UserHandle.getCallingUserId()) {
      return;
    }
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.INTERACT_ACROSS_USERS_FULL", "VpnManagerService");
  }

  public final void enforceSettingsPermission() {
    PermissionUtils.enforceAnyPermissionOf(
        this.mContext,
        new String[] {
          "android.permission.NETWORK_SETTINGS", "android.permission.MAINLINE_NETWORK_STACK"
        });
  }

  public static void log(String str) {
    Log.d(TAG, str);
  }

  public static void logw(String str) {
    Log.w(TAG, str);
  }

  public static void loge(String str) {
    Log.e(TAG, str);
  }

  public static IKnoxVpnPolicy getService() {
    return IKnoxVpnPolicy.Stub.asInterface(ServiceManager.getService("knox_vpn_policy"));
  }

  public boolean getChainingEnabledForProfile(int i) {
    try {
      return getService().getChainingEnabledForProfile(i) == 1;
    } catch (Exception unused) {
      return false;
    }
  }

  public int knoxVpnProfileType(String str) {
    try {
      return getService().getKnoxVpnProfileType(str);
    } catch (Exception unused) {
      return 0;
    }
  }

  public String[] getProxyInfoForUid(int i) {
    String[] strArr = new String[2];
    try {
      return getService().getProxyInfoForUid(i);
    } catch (Exception unused) {
      Log.e(
          TAG, "Exception occured while trying to get the getProxyInfoForUid for knoxvpn uid " + i);
      return strArr;
    }
  }

  public int[] getKnoxVpnZtnaProxyInfoForUid(int i, String str) {
    int[] iArr = new int[2];
    try {
      iArr[0] = Integer.parseInt(getProxyInfoForUid(i)[1]);
    } catch (Exception unused) {
      Log.e(
          TAG, "Exception occured while trying to get the getProxyInfoForUid for knoxvpn uid " + i);
    }
    iArr[1] = getKnoxNwFilterHttpProxyPort(UserHandle.getUserId(i), str);
    return iArr;
  }

  public boolean checkIfLocalProxyPortExists(int i) {
    try {
      return getService().checkIfLocalProxyPortExists(i);
    } catch (Exception unused) {
      Log.e(TAG, "Exception occured while trying to checkIfLocalProxyPortExists");
      return false;
    }
  }

  public boolean checkIfUidIsExempted(int i) {
    try {
      return getService().checkIfUidIsExempted(i);
    } catch (Exception e) {
      Log.e(
          TAG,
          "Error while checking if the uid of the app which originated the download is exempted or"
              + " not "
              + Log.getStackTraceString(e));
      return false;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:10:0x0026, code lost:

     r6 = r4.getDnsServers().iterator();
  */
  /* JADX WARN: Code restructure failed: missing block: B:12:0x0032, code lost:

     if (r6.hasNext() == false) goto L27;
  */
  /* JADX WARN: Code restructure failed: missing block: B:13:0x0034, code lost:

     r7 = r6.next();
  */
  /* JADX WARN: Code restructure failed: missing block: B:14:0x003c, code lost:

     if (com.android.server.VpnManagerService.DBG == false) goto L29;
  */
  /* JADX WARN: Code restructure failed: missing block: B:15:0x003e, code lost:

     android.util.Log.d(com.android.server.VpnManagerService.TAG, "The knox vpn dns server being added for usb tethering use-case is " + r7.getHostAddress());
  */
  /* JADX WARN: Code restructure failed: missing block: B:17:0x0058, code lost:

     r0.add(r7.getHostAddress());
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public String[] getDnsServerListForInterface(String str) {
    ArrayList newArrayList = Lists.newArrayList();
    try {
      Network[] allNetworks = this.mCm.getAllNetworks();
      int length = allNetworks.length;
      int i = 0;
      while (true) {
        if (i >= length) {
          break;
        }
        LinkProperties linkProperties = this.mCm.getLinkProperties(allNetworks[i]);
        if (linkProperties.getInterfaceName() != null
            && linkProperties.getInterfaceName().equals(str)) {
          break;
        }
        i++;
      }
    } catch (Exception unused) {
    }
    return (String[]) newArrayList.toArray(new String[newArrayList.size()]);
  }

  public boolean isProxyConfiguredForKnoxVpn(int i) {
    try {
      return getService().isProxyConfiguredForKnoxVpn(i);
    } catch (Exception unused) {
      Log.e(
          TAG, "Exception occured while trying to get the getProxyInfoConfig for knoxvpn uid " + i);
      return false;
    }
  }

  public void resetUidListInNetworkCapabilities(String str, int i, String str2) {
    if (Binder.getCallingUid() != 1000) {
      return;
    }
    if (str2.equals("com.samsung.sVpn")) {
      synchronized (this.mVpns) {
        Vpn vpn = (Vpn) this.mVpns.get(i);
        if (vpn != null) {
          vpn.resetUidListInNetworkCapabilities();
        }
      }
      return;
    }
    synchronized (this.knoxVpnLock) {
      EnterpriseVpn enterpriseVpn =
          this.mEnterpriseVpnStoreObj.get(i) != null
              ? (EnterpriseVpn) ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).get(str)
              : null;
      if (enterpriseVpn != null) {
        enterpriseVpn.resetUidListInNetworkCapabilities();
      }
    }
  }

  public void updateLocalProxyInfo(String str, int i, String str2, ProxyInfo proxyInfo) {
    if (Binder.getCallingUid() != 1000) {
      return;
    }
    if (str2.equals("com.samsung.sVpn")) {
      synchronized (this.mVpns) {
        Vpn vpn = (Vpn) this.mVpns.get(i);
        if (vpn != null) {
          vpn.updateLocalProxyInfo(proxyInfo);
        }
      }
      return;
    }
    synchronized (this.knoxVpnLock) {
      EnterpriseVpn enterpriseVpn =
          this.mEnterpriseVpnStoreObj.get(i) != null
              ? (EnterpriseVpn) ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).get(str)
              : null;
      if (enterpriseVpn != null) {
        enterpriseVpn.updateLocalProxyInfo(proxyInfo);
      }
    }
  }

  public void applyBlockingRulesToUidRange(String str, int i, boolean z, String str2) {
    if (Binder.getCallingUid() != 1000) {
      return;
    }
    if (str2.equals("com.samsung.sVpn")) {
      synchronized (this.mVpns) {
        Vpn vpn = (Vpn) this.mVpns.get(i);
        if (vpn != null) {
          vpn.applyBlockingRulesToUidRange(z);
        }
      }
      return;
    }
    synchronized (this.knoxVpnLock) {
      EnterpriseVpn enterpriseVpn =
          this.mEnterpriseVpnStoreObj.get(i) != null
              ? (EnterpriseVpn) ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).get(str)
              : null;
      if (enterpriseVpn != null) {
        enterpriseVpn.applyBlockingRulesToUidRange(z);
      }
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:13:0x004e, code lost:

     if (getService().getUidPidEnabled(r1, r6) == 0) goto L14;
  */
  /* JADX WARN: Code restructure failed: missing block: B:16:0x0058, code lost:

     throw new java.lang.SecurityException("Unauthorized Call to enable meta data");
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean prepareEnterpriseVpnExt(String str, boolean z) {
    EnterpriseVpn enterpriseVpn;
    int userId = UserHandle.getUserId(Binder.getCallingUid());
    int callingUid = Binder.getCallingUid();
    try {
      if (Binder.getCallingUid() != 1000
          && !getService().checkIfCallerIsVpnVendor(Binder.getCallingUid())) {
        Log.e(TAG, "No permission to call prepareEnterpriseVpnExt : " + Binder.getCallingUid());
        throw new SecurityException();
      }
      if (getService().checkIfVendorCreatedKnoxProfile(str, callingUid, userId)) {
        int knoxVpnProfileType = getService().getKnoxVpnProfileType(str);
        if (knoxVpnProfileType == 0) {
          Log.e(TAG, "Non knox mode is not supported");
          return false;
        }
        if (knoxVpnProfileType == 1) {
          synchronized (this.knoxVpnLock) {
            if (this.mEnterpriseVpnStoreObj.get(userId) != null
                && (enterpriseVpn =
                        (EnterpriseVpn)
                            ((HashMap) this.mEnterpriseVpnStoreObj.get(userId)).get(str))
                    != null) {
              enterpriseVpn.setMetaDataStateInIP(z);
            }
          }
        }
      }
    } catch (Exception unused) {
    }
    Log.d(TAG, "The return value in prepareEnterpriseVpnExt true");
    return true;
  }

  public boolean disconnectKnoxVpn(String str, int i) {
    if (Binder.getCallingUid() != 1000) {
      throw new SecurityException();
    }
    if (DBG) {
      Log.d(TAG, "vpn disconnect :  Profile: " + str + " callingUid: " + i);
    }
    synchronized (this.knoxVpnLock) {
      int userId = UserHandle.getUserId(i);
      EnterpriseVpn enterpriseVpn =
          this.mEnterpriseVpnStoreObj.get(userId) != null
              ? (EnterpriseVpn) ((HashMap) this.mEnterpriseVpnStoreObj.get(userId)).get(str)
              : null;
      if (enterpriseVpn == null) {
        return false;
      }
      NetworkInfo.State state = enterpriseVpn.getNetworkInfo().getState();
      if (state != NetworkInfo.State.DISCONNECTED && state != NetworkInfo.State.DISCONNECTING) {
        return false;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      enterpriseVpn.showNotification(str, false);
      Binder.restoreCallingIdentity(clearCallingIdentity);
      long clearCallingIdentity2 = Binder.clearCallingIdentity();
      List domainsByProfileName = getDomainsByProfileName(str);
      if (domainsByProfileName != null && domainsByProfileName.size() > 0) {
        for (int i2 = 0; i2 < domainsByProfileName.size(); i2++) {
          if (!hasInterfaceAsUser(Integer.parseInt((String) domainsByProfileName.get(i2)))) {
            if (DBG) {
              Log.d(
                  TAG,
                  "The connected Vpn is not exists in user "
                      + ((String) domainsByProfileName.get(i2)));
            }
            enterpriseVpn.hideNotification(Integer.parseInt((String) domainsByProfileName.get(i2)));
          }
        }
      } else {
        List personaUserIds = getPersonaUserIds();
        if (personaUserIds == null) {
          personaUserIds = new ArrayList();
        }
        personaUserIds.add(0);
        Iterator it = personaUserIds.iterator();
        while (it.hasNext()) {
          int intValue = ((Integer) it.next()).intValue();
          if (!hasInterfaceAsUser(intValue)) {
            if (DBG) {
              Log.d(TAG, "The connected Vpn is not exists in user " + intValue);
            }
            enterpriseVpn.hideNotification(intValue);
          }
        }
      }
      Binder.restoreCallingIdentity(clearCallingIdentity2);
      return true;
    }
  }

  public void updateEnterpriseVpn(String str, int i, boolean z) {
    if (Binder.getCallingUid() != 1000) {
      throw new SecurityException();
    }
    synchronized (this.knoxVpnLock) {
      EnterpriseVpn enterpriseVpn = null;
      for (int i2 = 0; i2 < this.mEnterpriseVpnStoreObj.size(); i2++) {
        int keyAt = this.mEnterpriseVpnStoreObj.keyAt(i2);
        if (this.mEnterpriseVpnStoreObj.get(keyAt) != null
            && (enterpriseVpn =
                    (EnterpriseVpn) ((HashMap) this.mEnterpriseVpnStoreObj.get(keyAt)).get(str))
                != null) {
          break;
        }
      }
      if (enterpriseVpn != null) {
        NetworkInfo.State state = enterpriseVpn.getNetworkInfo().getState();
        if (enterpriseVpn.getInterfaceName() != null && state == NetworkInfo.State.CONNECTED) {
          if (!z) {
            if (!hasInterfaceAsUser(i)) {
              enterpriseVpn.refreshNotification(z, i);
              enterpriseVpn.hideNotification(i);
            }
          } else {
            enterpriseVpn.refreshNotification(z, i);
          }
        }
      }
    }
  }

  public void setNotificationDismissibleFlag(String str, int i, int i2) {
    if (Binder.getCallingUid() != 1000) {
      throw new SecurityException();
    }
    synchronized (this.mVpns) {
      Vpn vpn = (Vpn) this.mVpns.get(i);
      if (vpn != null) {
        vpn.setNotificationDismissibleFlagForKnoxStrongSwan(i, i2);
      }
    }
    synchronized (this.knoxVpnLock) {
      EnterpriseVpn enterpriseVpn = null;
      for (int i3 = 0; i3 < this.mEnterpriseVpnStoreObj.size(); i3++) {
        int keyAt = this.mEnterpriseVpnStoreObj.keyAt(i3);
        if (this.mEnterpriseVpnStoreObj.get(keyAt) != null
            && (enterpriseVpn =
                    (EnterpriseVpn) ((HashMap) this.mEnterpriseVpnStoreObj.get(keyAt)).get(str))
                != null) {
          break;
        }
      }
      if (enterpriseVpn != null) {
        enterpriseVpn.setNotificationDismissibleFlag(i, i2);
      }
    }
  }

  public void createEnterpriseVpnInstance(String str, String str2, int i, int i2) {
    if (Binder.getCallingUid() == 1000 && !str.equals("com.samsung.sVpn")) {
      synchronized (this.knoxVpnLock) {
        if (!checkIfKnoxVpnProfileExists(str2, i)) {
          EnterpriseVpn enterpriseVpn =
              new EnterpriseVpn(
                  this.mHandler.getLooper(), this.mContext, this.mNMS, this.mNetd, i, str, i2);
          HashMap hashMap = (HashMap) this.mEnterpriseVpnStoreObj.get(i);
          if (hashMap == null) {
            hashMap = new HashMap();
          }
          hashMap.put(str2, enterpriseVpn);
          this.mEnterpriseVpnStoreObj.put(i, hashMap);
        } else {
          Log.d(TAG, "createEnterpriseVpnInstance failed for " + str2 + " in user " + i);
        }
      }
    }
  }

  public void removeEnterpriseVpnInstance(String str, String str2, int i) {
    if (Binder.getCallingUid() != 1000) {
      return;
    }
    if (str.equals("com.samsung.sVpn")) {
      synchronized (this.mVpns) {
        Vpn vpn = (Vpn) this.mVpns.get(i);
        if (vpn != null) {
          vpn.cleanupObjects();
        }
      }
      return;
    }
    synchronized (this.knoxVpnLock) {
      if (checkIfKnoxVpnProfileExists(str2, i)) {
        Log.d(
            TAG, "removeEnterpriseVpnInstance: profile " + str2 + " in user " + i + " is removed");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        ((EnterpriseVpn) ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).get(str2))
            .showNotification(str2, false);
        ((EnterpriseVpn) ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).get(str2)).cleanupObjects();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).remove(str2);
      }
      HashMap hashMap = (HashMap) this.mEnterpriseVpnStoreObj.get(i);
      if (hashMap == null || hashMap.size() == 0 || hashMap.isEmpty()) {
        Log.d(TAG, "removeEnterpriseVpnInstance all profiles in user " + i + " is removed");
        this.mEnterpriseVpnStoreObj.delete(i);
      }
    }
  }

  public void updateUidRangesToPerAppVpn(String str, int i, boolean z, int[] iArr, String str2) {
    if (Binder.getCallingUid() != 1000) {
      return;
    }
    int i2 = 0;
    if (str2.equals("com.samsung.sVpn")) {
      synchronized (this.mVpns) {
        Vpn vpn = (Vpn) this.mVpns.get(i);
        if (vpn != null) {
          HashSet hashSet = new HashSet();
          int length = iArr.length;
          while (i2 < length) {
            hashSet.add(Integer.valueOf(iArr[i2]));
            i2++;
          }
          vpn.updateUidRangesToPerAppVpn(z, hashSet);
        }
      }
      return;
    }
    synchronized (this.knoxVpnLock) {
      EnterpriseVpn enterpriseVpn =
          this.mEnterpriseVpnStoreObj.get(i) != null
              ? (EnterpriseVpn) ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).get(str)
              : null;
      if (enterpriseVpn != null) {
        HashSet hashSet2 = new HashSet();
        int length2 = iArr.length;
        while (i2 < length2) {
          hashSet2.add(Integer.valueOf(iArr[i2]));
          i2++;
        }
        enterpriseVpn.updateUidRangesToPerAppVpn(z, hashSet2);
      }
    }
  }

  public void updateUidRangesToUserVpn(String str, int i, boolean z, int i2, String str2) {
    if (Binder.getCallingUid() != 1000) {
      return;
    }
    if (str2.equals("com.samsung.sVpn")) {
      synchronized (this.mVpns) {
        Vpn vpn = (Vpn) this.mVpns.get(i);
        if (vpn != null) {
          vpn.updateUidRangesToUserVpn(z, i2);
        }
      }
      return;
    }
    synchronized (this.knoxVpnLock) {
      EnterpriseVpn enterpriseVpn =
          this.mEnterpriseVpnStoreObj.get(i) != null
              ? (EnterpriseVpn) ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).get(str)
              : null;
      if (enterpriseVpn != null) {
        enterpriseVpn.updateUidRangesToUserVpn(z, i2);
      }
    }
  }

  public void updateUidRangesToUserVpnWithBlackList(
      String str, int i, int i2, int[] iArr, String str2) {
    if (Binder.getCallingUid() != 1000) {
      return;
    }
    int i3 = 0;
    if (str2.equals("com.samsung.sVpn")) {
      synchronized (this.mVpns) {
        Vpn vpn = (Vpn) this.mVpns.get(i);
        if (vpn != null) {
          HashSet hashSet = new HashSet();
          int length = iArr.length;
          while (i3 < length) {
            hashSet.add(Integer.valueOf(iArr[i3]));
            i3++;
          }
          vpn.updateUidRangesToUserVpnWithBlackList(i2, hashSet);
        }
      }
      return;
    }
    synchronized (this.knoxVpnLock) {
      EnterpriseVpn enterpriseVpn =
          this.mEnterpriseVpnStoreObj.get(i) != null
              ? (EnterpriseVpn) ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).get(str)
              : null;
      if (enterpriseVpn != null) {
        HashSet hashSet2 = new HashSet();
        int length2 = iArr.length;
        while (i3 < length2) {
          hashSet2.add(Integer.valueOf(iArr[i3]));
          i3++;
        }
        enterpriseVpn.updateUidRangesToUserVpnWithBlackList(i2, hashSet2);
      }
    }
  }

  public void registerSystemDefaultNetworkCallback() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    NetworkCallback networkCallback = new NetworkCallback();
    this.mDefaultNetworkCallback = networkCallback;
    try {
      try {
        this.mCm.registerSystemDefaultNetworkCallback(networkCallback, this.mHandler);
      } catch (RuntimeException e) {
        Log.e(
            TAG,
            "Failed to register system default network callback " + Log.getStackTraceString(e));
        this.mDefaultNetworkCallback = null;
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void unregisterSystemDefaultNetworkCallback() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        NetworkCallback networkCallback = this.mDefaultNetworkCallback;
        if (networkCallback != null) {
          this.mCm.unregisterNetworkCallback(networkCallback);
        }
        this.mDefaultNetworkCallback = null;
      } catch (RuntimeException e) {
        Log.e(
            TAG,
            "Failed to unregister system default network callback " + Log.getStackTraceString(e));
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public int getNetIdforActiveDefaultInterface() {
    NetworkCallback networkCallback = this.mDefaultNetworkCallback;
    if (networkCallback != null) {
      return networkCallback.getNetwork().getNetId();
    }
    return 0;
  }

  public Network getActiveDefaultNetwork() {
    NetworkCallback networkCallback = this.mDefaultNetworkCallback;
    if (networkCallback != null) {
      return networkCallback.getNetwork();
    }
    return null;
  }

  public String getActiveDefaultInterface() {
    NetworkCallback networkCallback = this.mDefaultNetworkCallback;
    if (networkCallback == null || networkCallback.getLinkProperties() == null) {
      return null;
    }
    return this.mDefaultNetworkCallback.getLinkProperties().getInterfaceName();
  }

  public static IKnoxNetworkFilterService getNwFilterService() {
    return IKnoxNetworkFilterService.Stub.asInterface(
        ServiceManager.getService("knox_nwFilterMgr_policy"));
  }

  public int getKnoxNwFilterHttpProxyPort(int i, String str) {
    try {
      return getNwFilterService().getKnoxNwFilterHttpProxyPort(i, str);
    } catch (Exception e) {
      Log.d(TAG, "Exception: " + Log.getStackTraceString(e));
      return -1;
    }
  }

  @SystemApi
  public boolean isVpnConfigured(int i) {
    int userId = UserHandle.getUserId(Binder.getCallingUid());
    synchronized (this.mVpns) {
      Vpn vpn = (Vpn) this.mVpns.get(userId);
      return (vpn == null || vpn.getNetwork() == null || vpn.getNetwork().getNetId() != i)
          ? false
          : true;
    }
  }

  public final boolean hasInterfaceAsUser(int i) {
    EnterpriseVpn enterpriseVpn;
    List profilesByDomain = getProfilesByDomain(i);
    if (DBG) {
      Log.d(TAG, "hasInterfaceAsUser > profiles.size : " + profilesByDomain.size());
    }
    for (int i2 = 0; i2 < profilesByDomain.size(); i2++) {
      for (int i3 = 0; i3 < this.mEnterpriseVpnStoreObj.size(); i3++) {
        int keyAt = this.mEnterpriseVpnStoreObj.keyAt(i3);
        boolean z = DBG;
        if (z) {
          Log.d(
              TAG,
              "hasInterfaceAsUser > vpn key : "
                  + keyAt
                  + ", profileName : "
                  + ((String) profilesByDomain.get(i2)));
        }
        if (this.mEnterpriseVpnStoreObj.get(keyAt) != null
            && (enterpriseVpn =
                    (EnterpriseVpn)
                        ((HashMap) this.mEnterpriseVpnStoreObj.get(keyAt))
                            .get(profilesByDomain.get(i2)))
                != null) {
          NetworkInfo.State state = enterpriseVpn.getNetworkInfo().getState();
          if (enterpriseVpn.getInterfaceName() != null && state == NetworkInfo.State.CONNECTED) {
            if (!z) {
              return true;
            }
            Log.d(TAG, "hasInterfaceAsUser : return true");
            return true;
          }
        }
      }
    }
    return false;
  }

  public final List getDomainsByProfileName(String str) {
    try {
      return getService().getDomainsByProfileName(str);
    } catch (Exception unused) {
      return new ArrayList();
    }
  }

  public final List getProfilesByDomain(int i) {
    List arrayList = new ArrayList();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        arrayList = getService().getProfilesByDomain(String.valueOf(i));
      } catch (Exception e) {
        Log.d(TAG, "Exception: " + Log.getStackTraceString(e));
      }
      return arrayList;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final boolean checkIfKnoxVpnProfileExists(String str, int i) {
    HashMap hashMap = (HashMap) this.mEnterpriseVpnStoreObj.get(i);
    return hashMap != null && hashMap.containsKey(str);
  }

  public final String getPackageNameFromUid(int i) {
    String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
    if (packagesForUid != null) {
      return packagesForUid[0];
    }
    return null;
  }

  public final ParcelFileDescriptor establishEnterpriseVpn(VpnConfig vpnConfig, int i) {
    try {
      getService()
          .setInitialTimeToTunnelEstablishment(vpnConfig.session, System.currentTimeMillis());
    } catch (Exception unused) {
    }
    String str = TAG;
    Log.d(
        str,
        "establishEnterpriseVpn : user = " + vpnConfig.user + " Profile: " + vpnConfig.session);
    String str2 = SystemProperties.get("net.vpn.framework");
    Log.d(str, "establishEnterpriseVpn: getProperty value is '" + str2 + "'");
    synchronized (this.knoxVpnLock) {
      ParcelFileDescriptor parcelFileDescriptor = null;
      if (this.mEnterpriseVpnStoreObj.get(i) != null) {
        EnterpriseVpn enterpriseVpn =
            (EnterpriseVpn) ((HashMap) this.mEnterpriseVpnStoreObj.get(i)).get(vpnConfig.session);
        if (this.mIsKnoxContainerV30 && enterpriseVpn != null && str2.equals("2.0")) {
          vpnConfig.routes = null;
          parcelFileDescriptor = enterpriseVpn.establish(vpnConfig);
        }
        Log.d(str, "establishEnterpriseVpn: knoxVpnFd value is " + parcelFileDescriptor);
        return parcelFileDescriptor;
      }
      Log.e(str, "Unable to find enterpriseVpn object in hashmap : user = " + i);
      return null;
    }
  }

  public final int checkKnoxVpnProfileType(String str, int i, int i2) {
    try {
      boolean checkIfVendorCreatedKnoxProfile =
          getService().checkIfVendorCreatedKnoxProfile(str, i, i2);
      Log.d(
          TAG,
          "checkKnoxVpnProfileType: profileCreatedByKnoxAdmin value is "
              + checkIfVendorCreatedKnoxProfile);
      if (checkIfVendorCreatedKnoxProfile) {
        return knoxVpnProfileType(str);
      }
      return 0;
    } catch (Exception unused) {
      return 0;
    }
  }

  public final List getPersonaUserIds() {
    if (getPersonaManager() != null) {
      return getPersonaManager().getKnoxIds(false);
    }
    return null;
  }

  public final SemPersonaManager getPersonaManager() {
    if (this.mPersonaManager == null) {
      this.mPersonaManager = (SemPersonaManager) this.mContext.getSystemService("persona");
    }
    return this.mPersonaManager;
  }

  public class NetworkCallback extends ConnectivityManager.NetworkCallback {
    public LinkProperties mLinkProperties;
    public Network mNetwork;

    public NetworkCallback() {
      this.mNetwork = null;
      this.mLinkProperties = null;
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
      Log.d(
          VpnManagerService.TAG,
          "onLinkPropertiesChanged being called for netId "
              + network.getNetId()
              + " for interface "
              + linkProperties.getInterfaceName());
      this.mNetwork = network;
      this.mLinkProperties = linkProperties;
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onLost(Network network) {
      Log.d(VpnManagerService.TAG, "onLost being called for netId " + network.getNetId());
      this.mNetwork = null;
      this.mLinkProperties = null;
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onAvailable(Network network) {
      Log.d(VpnManagerService.TAG, "onAvailable being called for netId " + network.getNetId());
      this.mNetwork = network;
    }

    public Network getNetwork() {
      return this.mNetwork;
    }

    public LinkProperties getLinkProperties() {
      return this.mLinkProperties;
    }
  }
}
