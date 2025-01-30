package com.android.server.enterprise.vpn.knoxvpn;

import android.R;
import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.hardware.usb.UsbManager;
import android.net.ConnectivityManager;
import android.net.INetd;
import android.net.IVpnManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkPolicyManager;
import android.net.NetworkRequest;
import android.net.ProxyInfo;
import android.net.UidRangeParcel;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.security.LegacyVpnProfileStore;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.Toast;
import com.android.internal.net.IOemNetd;
import com.android.internal.net.VpnProfile;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.server.am.ActivityManagerService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnPackageInfo;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo;
import com.android.server.enterprise.vpn.knoxvpn.storage.KnoxVpnStorageProvider;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.IEnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.net.vpn.EnterpriseResponseData;
import com.samsung.android.knox.net.vpn.IKnoxVpnPolicy;
import com.samsung.android.knox.net.vpn.IVpnInfoPolicy;
import com.samsung.android.knox.net.vpn.KnoxVpnContext;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class KnoxVpnEngineService extends IKnoxVpnPolicy.Stub implements EnterpriseServiceCallback {
  public static INetd mNetdService;
  public static IOemNetd mOemNetdService;
  public boolean isDeviceBootCompleted;
  public ActivityManagerService mAMS;
  public final Object mCaptiveExemptLock;
  public ChainingStateMachine mChainingStateMachine;
  public ConnectivityManager mConnectivityManager;
  public Context mContext;
  public KnoxAnalyticsData mData;
  public EnterpriseDeviceManager mEDM;
  public IEnterpriseDeviceManager mEnterpriseDeviceManagerService;
  public KnoxVpnFirewallHelper mFirewallHelper;
  public KnoxVpnHandler mHandler;
  public HandlerThread mHandlerThread;
  public ConcurrentHashMap mIgnoreConnectCheckForChaining;
  public Injector mInjector;
  public KnoxVpnApiValidation mKnoxVpnApiValidation;
  public KnoxVpnHelper mKnoxVpnHelper;
  public KnoxVpnPacProcessor mKnoxVpnPacProcessor;
  public KnoxVpnTetherAuthentication mKnoxVpnTetherAuthentication;
  public ConnectivityManager.NetworkCallback mNetworkCallback;
  public INetworkManagementService mNetworkManagementService;
  public NetworkPolicyManager mNetworkPolicyManager;
  public NotificationManager mNotificationManager;
  public ConcurrentHashMap mNotificationMap;
  public PowerManager mPowerManager;
  public KnoxVpnProcessManager mProcessManager;
  public UsbManager mUsbManager;
  public List mVpnClientStatus;
  public HashMap mVpnConnectionList;
  public IVpnInfoPolicy mVpnInfoPolicy;
  public KnoxVpnStorageProvider mVpnStorageProvider;
  public ConcurrentHashMap notificationFlagState;
  public VpnReceiver receiver;
  public VpnProfileConfig vpnConfig;
  public ConcurrentHashMap vpnInterfaceMap;
  public static final boolean DBG = Debug.semIsProductDev();
  public static volatile boolean mIsCaptiveExempt = false;
  public static final Object NULL_OBJECT = new Object();

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void notifyToAddSystemService(String str, IBinder iBinder) {}

  class Injector {
    public final Context mContext;

    public Injector(Context context) {
      this.mContext = context;
    }

    public KnoxVpnStorageProvider getKnoxVpnStorageProviderInstance() {
      return KnoxVpnStorageProvider.getInstance(this.mContext);
    }

    public KnoxVpnApiValidation getKnoxVpnApiValidationInstance() {
      return KnoxVpnApiValidation.getInstance(this.mContext);
    }

    public KnoxVpnPacProcessor getKnoxVpnPacProcessorInstance() {
      return KnoxVpnPacProcessor.getInstance(this.mContext);
    }

    public VpnProfileConfig getVpnProfileConfigInstance() {
      return VpnProfileConfig.getInstance();
    }

    public EnterpriseDeviceManager getEnterpriseDeviceManagerInstance() {
      return EnterpriseDeviceManager.getInstance(this.mContext);
    }

    public IKnoxVpnService getKnoxVpnServiceInstance(IBinder iBinder) {
      return IKnoxVpnService.Stub.asInterface(iBinder);
    }

    public KnoxVpnHelper getKnoxVpnHelperInstance() {
      return KnoxVpnHelper.getInstance(this.mContext);
    }

    public KnoxVpnFirewallHelper getKnoxFirewallHelperInstance() {
      return KnoxVpnFirewallHelper.getInstance();
    }

    public IVpnManager getVpnManagerServiceInstance() {
      return IVpnManager.Stub.asInterface(ServiceManager.getService("vpn_management"));
    }

    public IVpnInfoPolicy getVpnInfoPolicyInstance() {
      return IVpnInfoPolicy.Stub.asInterface(ServiceManager.getService("vpn_policy"));
    }
  }

  public KnoxVpnEngineService() {
    this.mContext = null;
    this.mHandlerThread = null;
    this.mHandler = null;
    this.mNetworkManagementService = null;
    this.mEnterpriseDeviceManagerService = null;
    this.mNotificationMap = null;
    this.mKnoxVpnApiValidation = null;
    this.mKnoxVpnPacProcessor = null;
    this.mProcessManager = null;
    this.mChainingStateMachine = null;
    this.mAMS = null;
    this.mNotificationManager = null;
    this.mPowerManager = null;
    this.mNetworkPolicyManager = null;
    this.mEDM = null;
    this.receiver = null;
    this.mIgnoreConnectCheckForChaining = null;
    this.mVpnConnectionList = new HashMap();
    this.mVpnClientStatus = new ArrayList();
    this.mData = null;
    this.mUsbManager = null;
    this.mInjector = null;
    this.mKnoxVpnTetherAuthentication = null;
    this.mCaptiveExemptLock = new Object();
    this.notificationFlagState = null;
    this.isDeviceBootCompleted = false;
    this.mNetworkCallback =
        new ConnectivityManager
            .NetworkCallback() { // from class:
                                 // com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.1
          @Override // android.net.ConnectivityManager.NetworkCallback
          public void onCapabilitiesChanged(
              Network network, NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            if (KnoxVpnEngineService.mIsCaptiveExempt && networkCapabilities.hasCapability(16)) {
              Bundle bundle = new Bundle();
              bundle.putInt("captive", 0);
              KnoxVpnEngineService.this.sendMessageToHandler(29, bundle);
            }
          }
        };
  }

  public KnoxVpnEngineService(Context context) {
    this(new Injector(context));
  }

  public KnoxVpnEngineService(Injector injector) {
    this.mContext = null;
    this.mHandlerThread = null;
    this.mHandler = null;
    this.mNetworkManagementService = null;
    this.mEnterpriseDeviceManagerService = null;
    this.mNotificationMap = null;
    this.mKnoxVpnApiValidation = null;
    this.mKnoxVpnPacProcessor = null;
    this.mProcessManager = null;
    this.mChainingStateMachine = null;
    this.mAMS = null;
    this.mNotificationManager = null;
    this.mPowerManager = null;
    this.mNetworkPolicyManager = null;
    this.mEDM = null;
    this.receiver = null;
    this.mIgnoreConnectCheckForChaining = null;
    this.mVpnConnectionList = new HashMap();
    this.mVpnClientStatus = new ArrayList();
    this.mData = null;
    this.mUsbManager = null;
    this.mInjector = null;
    this.mKnoxVpnTetherAuthentication = null;
    this.mCaptiveExemptLock = new Object();
    this.notificationFlagState = null;
    this.isDeviceBootCompleted = false;
    this.mNetworkCallback =
        new ConnectivityManager
            .NetworkCallback() { // from class:
                                 // com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.1
          @Override // android.net.ConnectivityManager.NetworkCallback
          public void onCapabilitiesChanged(
              Network network, NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            if (KnoxVpnEngineService.mIsCaptiveExempt && networkCapabilities.hasCapability(16)) {
              Bundle bundle = new Bundle();
              bundle.putInt("captive", 0);
              KnoxVpnEngineService.this.sendMessageToHandler(29, bundle);
            }
          }
        };
    Log.d("KnoxVpnEngineService", "Initializing in constructor");
    this.mInjector = injector;
    Context context = injector.mContext;
    Objects.requireNonNull(context);
    this.mContext = context;
    this.vpnInterfaceMap = new ConcurrentHashMap();
    this.mNotificationMap = new ConcurrentHashMap();
    this.mIgnoreConnectCheckForChaining = new ConcurrentHashMap();
    this.mKnoxVpnHelper = this.mInjector.getKnoxVpnHelperInstance();
    this.vpnConfig = this.mInjector.getVpnProfileConfigInstance();
    this.mVpnStorageProvider = this.mInjector.getKnoxVpnStorageProviderInstance();
    this.mKnoxVpnApiValidation = this.mInjector.getKnoxVpnApiValidationInstance();
    this.mKnoxVpnPacProcessor = this.mInjector.getKnoxVpnPacProcessorInstance();
    this.mProcessManager = KnoxVpnProcessManager.getInstance(this);
    this.notificationFlagState = new ConcurrentHashMap();
    initializeHandlerThread();
    handleKnoxVpnBuildUpdate();
    initializeVpnDataAndVendors();
    KnoxVpnVersion.writeVersionInProperties();
  }

  public static void connectNativeNetdService() {
    boolean z;
    INetd asInterface;
    try {
      asInterface =
          INetd.Stub.asInterface(
              ServiceManager.getService(KnoxVpnFirewallHelper.NETD_SERVICE_NAME));
      mNetdService = asInterface;
    } catch (RemoteException unused) {
      mNetdService = null;
      z = false;
    }
    if (asInterface == null) {
      return;
    }
    z = asInterface.isAlive();
    if (z || !DBG) {
      return;
    }
    Log.e("KnoxVpnEngineService", "Can't connect to NativeNetdService netd");
  }

  public static IOemNetd getOemNetdService() {
    IOemNetd iOemNetd = mOemNetdService;
    if (iOemNetd != null) {
      return iOemNetd;
    }
    if (mNetdService == null) {
      connectNativeNetdService();
    }
    INetd iNetd = mNetdService;
    if (iNetd != null) {
      try {
        mOemNetdService = IOemNetd.Stub.asInterface(iNetd.getOemNetd());
      } catch (RemoteException e) {
        Log.e("KnoxVpnEngineService", "Failed to get OemNetd listener " + e.getMessage());
      }
    }
    return mOemNetdService;
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminAdded(int i) {
    Log.d("KnoxVpnEngineService", "[onAdminAdded]");
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminRemoved(int i) {
    Log.d("KnoxVpnEngineService", "[onAdminRemoved]");
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void systemReady() {
    Log.d("KnoxVpnEngineService", "system ready is being called");
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onPreAdminRemoval(int i) {
    Log.d("KnoxVpnEngineService", "[onPreAdminRemoval]");
    if (DBG) {
      Log.d("KnoxVpnEngineService", "Admin has VPN Permission : Pre admin remove " + i);
    }
    try {
      Bundle bundle = new Bundle();
      bundle.putInt("uid", i);
      sendMessageToHandler(19, bundle);
    } catch (Exception unused) {
    }
  }

  public final void initializeHandlerThread() {
    HandlerThread handlerThread = new HandlerThread("KnoxVpnHandler", 10);
    this.mHandlerThread = handlerThread;
    handlerThread.start();
    this.mHandler = new KnoxVpnHandler(this.mHandlerThread.getLooper());
  }

  public boolean bindKnoxVpnInterface(KnoxVpnContext knoxVpnContext) {
    if (updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission(knoxVpnContext).mCallerUid)) {
      return bindKnoxVpnInterface(
          this.mKnoxVpnHelper.getPersonifiedName(
              knoxVpnContext.getPersonaId(), knoxVpnContext.getVendorName()),
          knoxVpnContext.adminId);
    }
    return false;
  }

  public final boolean bindKnoxVpnInterface(String str, int i) {
    Log.d("KnoxVpnEngineService", "Bind to Vpn Vendor Service : vendorName = " + str);
    String regularPackageName = this.mKnoxVpnHelper.getRegularPackageName(str);
    int containerIdFromPackageName = this.mKnoxVpnHelper.getContainerIdFromPackageName(str);
    Log.d("KnoxVpnEngineService", "bindKnoxVpnInterface is being called by the admin " + i);
    if (this.mKnoxVpnHelper.getUIDForPackage(containerIdFromPackageName, regularPackageName) < 0) {
      Log.d("KnoxVpnEngineService", "Bind to VPN Vendor is fail ");
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Bind to vpn failed. Could not find package %s", regularPackageName),
          containerIdFromPackageName);
      return false;
    }
    if (DBG) {
      Log.d(
          "KnoxVpnEngineService",
          "bindKnoxVpnInterface : user > "
              + containerIdFromPackageName
              + ", package name > "
              + regularPackageName);
    }
    if (getVpnInterface(str) != null) {
      sendBindSuccessIntent(str, i);
      return true;
    }
    if (this.mVpnConnectionList.containsKey(str)) {
      Log.d(
          "KnoxVpnEngineService",
          "unbinding previous service before binding again for the vpn client " + str);
      try {
        this.mContext.unbindService((ServiceConnection) this.mVpnConnectionList.get(str));
      } catch (Exception unused) {
        Log.e(
            "KnoxVpnEngineService",
            "unbinding failed since the service is already unbinded or not existing");
      }
      this.mVpnConnectionList.remove(str);
    }
    String str2 = regularPackageName + ".BIND_SERVICE_NEW";
    Log.d(
        "KnoxVpnEngineService",
        "Bind to Vpn Vendor Service : vendorAction = "
            + str2
            + " container ID : "
            + containerIdFromPackageName);
    VpnServiceConnection vpnServiceConnection =
        new VpnServiceConnection(containerIdFromPackageName, i);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      Intent intent = new Intent(str2);
      List<ResolveInfo> queryIntentServicesAsUser =
          this.mContext
              .getPackageManager()
              .queryIntentServicesAsUser(intent, 0, containerIdFromPackageName);
      if (queryIntentServicesAsUser.size() > 0) {
        for (ResolveInfo resolveInfo : queryIntentServicesAsUser) {
          if (resolveInfo.serviceInfo.packageName.equalsIgnoreCase(regularPackageName)) {
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            intent.setComponent(new ComponentName(serviceInfo.packageName, serviceInfo.name));
          }
        }
      }
      intent.setPackage(regularPackageName);
      boolean bindServiceAsUser =
          this.mContext.bindServiceAsUser(
              intent, vpnServiceConnection, 83886081, new UserHandle(containerIdFromPackageName));
      if (bindServiceAsUser) {
        this.mVpnConnectionList.put(str, vpnServiceConnection);
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      Log.d(
          "KnoxVpnEngineService",
          "Bind to Vpn Vendor Service : bindSuccess = " + bindServiceAsUser);
      if (bindServiceAsUser) {
        AuditLog.logAsUser(
            5,
            5,
            true,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format("Bind to vpn vendor service %s successfully", regularPackageName),
            containerIdFromPackageName);
      } else {
        AuditLog.logAsUser(
            3,
            5,
            false,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format("Bind to vpn vendor service %s failed", regularPackageName),
            containerIdFromPackageName);
      }
      return bindServiceAsUser;
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public final INetworkManagementService getNetworkManagementService() {
    boolean z = DBG;
    if (z) {
      Log.d("KnoxVpnEngineService", "getNetworkManagementService is reached initially");
    }
    IBinder service = ServiceManager.getService("network_management");
    if (z) {
      Log.d("KnoxVpnEngineService", "getNetworkManagementService binder value is" + service);
    }
    if (service != null && this.mNetworkManagementService == null) {
      this.mNetworkManagementService = INetworkManagementService.Stub.asInterface(service);
      if (z) {
        Log.d(
            "KnoxVpnEngineService",
            "getNetworkManagementService mNetworkManagementService value is"
                + this.mNetworkManagementService);
      }
    }
    return this.mNetworkManagementService;
  }

  public final IVpnManager getVpnManagerService() {
    return this.mInjector.getVpnManagerServiceInstance();
  }

  public final IEnterpriseDeviceManager getEnterpriseDeviceManagerService() {
    IBinder service = ServiceManager.getService("enterprise_policy");
    if (service != null && this.mEnterpriseDeviceManagerService == null) {
      this.mEnterpriseDeviceManagerService = IEnterpriseDeviceManager.Stub.asInterface(service);
      if (DBG) {
        Log.d(
            "KnoxVpnEngineService",
            "getEnterpriseDeviceManagerService value is" + this.mEnterpriseDeviceManagerService);
      }
    }
    return this.mEnterpriseDeviceManagerService;
  }

  public final ConnectivityManager getConnectivityManager() {
    if (this.mConnectivityManager == null) {
      this.mConnectivityManager =
          (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
    }
    return this.mConnectivityManager;
  }

  public final KnoxVpnHelper getKnoxVpnHelperInstance() {
    if (this.mKnoxVpnHelper == null) {
      this.mKnoxVpnHelper = KnoxVpnHelper.getInstance(this.mContext);
    }
    return this.mKnoxVpnHelper;
  }

  public final ActivityManagerService getAMSInstance() {
    if (this.mAMS == null) {
      this.mAMS = (ActivityManagerService) ServiceManager.getService("activity");
    }
    return this.mAMS;
  }

  public NotificationManager getNotificationManager() {
    if (this.mNotificationManager == null) {
      this.mNotificationManager =
          (NotificationManager) this.mContext.getSystemService("notification");
    }
    return this.mNotificationManager;
  }

  public final KnoxVpnPacProcessor getKnoxVpnPacProcessor() {
    if (this.mKnoxVpnPacProcessor == null) {
      this.mKnoxVpnPacProcessor = KnoxVpnPacProcessor.getInstance(this.mContext);
    }
    return this.mKnoxVpnPacProcessor;
  }

  public final EnterpriseDeviceManager getEnterpriseDeviceManager() {
    if (this.mEDM == null) {
      this.mEDM = this.mInjector.getEnterpriseDeviceManagerInstance();
    }
    EnterpriseDeviceManager enterpriseDeviceManager = this.mEDM;
    if (enterpriseDeviceManager != null) {
      return enterpriseDeviceManager;
    }
    throw new SecurityException("Admin cannot be verified");
  }

  public final IVpnInfoPolicy getVpnInfoPolicy() {
    if (this.mVpnInfoPolicy == null) {
      this.mVpnInfoPolicy = this.mInjector.getVpnInfoPolicyInstance();
    }
    return this.mVpnInfoPolicy;
  }

  public final void sendBindSuccessIntent(String str, int i) {
    String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      String regularPackageName = this.mKnoxVpnHelper.getRegularPackageName(str);
      int containerIdFromPackageName = this.mKnoxVpnHelper.getContainerIdFromPackageName(str);
      if (packagesForUid != null && i != -1) {
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.knox.intent.action.VPN_BIND_RESULT");
        intent.putExtra(
            "com.samsung.android.knox.intent.extra.VPN_BIND_VENDOR", regularPackageName);
        intent.putExtra(
            "com.samsung.android.knox.intent.extra.VPN_BIND_CID", containerIdFromPackageName);
        intent.putExtra("com.samsung.android.knox.intent.extra.VPN_BIND_STATUS", true);
        Log.d(
            "KnoxVpnEngineService",
            "Sending bind success intent to User " + containerIdFromPackageName);
        this.mContext.sendBroadcastAsUser(
            intent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_VPN_GENERIC");
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void sendVpnConnectionFailIntent(String str, String str2, int i) {
    String[] strArr;
    int i2;
    int containerIdFromPackageName = this.mKnoxVpnHelper.getContainerIdFromPackageName(str);
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str2);
    int i3 = 0;
    if (profileEntry != null) {
      strArr = new String[profileEntry.getPackageList().size()];
      for (VpnPackageInfo vpnPackageInfo : profileEntry.getPackageList()) {
        if (this.mKnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)) {
          strArr =
              this.mKnoxVpnHelper.getUserPackageListForProfile(str2, containerIdFromPackageName);
        } else if (vpnPackageInfo.getPackageName() != null) {
          if (vpnPackageInfo.getPackageName().contains("_")
              && vpnPackageInfo.getPackageName().split("_", 2).length >= 1) {
            i2 = i3 + 1;
            strArr[i3] = vpnPackageInfo.getPackageName().split("_", 2)[1];
          } else {
            i2 = i3 + 1;
            strArr[i3] = vpnPackageInfo.getPackageName();
          }
          i3 = i2;
        }
      }
    } else {
      strArr = new String[0];
    }
    Intent intent = new Intent();
    intent.setAction("com.samsung.android.knox.intent.action.ACTION_RECREATE_VPN_PROFILE_FAIL");
    if (str.contains("_") && str.split("_", 2).length >= 1) {
      intent.putExtra(
          "com.samsung.android.knox.intent.extra.EXTRA_VENDOR_NAME", str.split("_", 2)[1]);
    } else {
      intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_VENDOR_NAME", str);
    }
    intent.putExtra(
        "com.samsung.android.knox.intent.extra.EXTRA_VENDOR_NAME", str.split("_", 2)[1]);
    intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_PROFILE_NAME", str2);
    intent.putExtra(
        "com.samsung.android.knox.intent.extra.EXTRA_USER_ID", containerIdFromPackageName);
    intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_PACKAGE_LIST", strArr);
    Log.v("KnoxVpnEngineService", "Sending Vpn Connection Fail intent - profile: " + str2);
    this.mContext.sendBroadcastAsUser(
        intent,
        new UserHandle(UserHandle.getUserId(i)),
        "com.samsung.android.knox.permission.KNOX_VPN_GENERIC");
    profileFailInfoBeSaved(i, str, str2, containerIdFromPackageName, strArr);
  }

  public final void profileFailInfoBeSaved(
      int i, String str, String str2, int i2, String[] strArr) {
    if (this.vpnConfig.getProfileEntry(str2) != null) {
      ContentValues contentValues = new ContentValues();
      contentValues.put("profileName", str2);
      contentValues.put("adminUid", Integer.valueOf(i));
      contentValues.put("errorType", Integer.valueOf(FrameworkStatsLog.f76x6a63db72));
      if (str.contains("_") && str.split("_", 2).length >= 1) {
        contentValues.put("vendorName", str.split("_", 2)[1]);
      } else {
        contentValues.put("vendorName", str);
      }
      contentValues.put("containerID", Integer.valueOf(i2));
      contentValues.put("packageList", arrayToString(strArr));
      boolean putDataByFields =
          this.mVpnStorageProvider.putDataByFields("vpnConnectionFail", null, null, contentValues);
      if (DBG) {
        Log.d(
            "KnoxVpnEngineService",
            "add profile in database (vpnConnectionFail): status is "
                + putDataByFields
                + "profile Name is"
                + str2);
      }
    }
  }

  public final String toJSONObject(ContentValues contentValues) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("errorType", String.valueOf(contentValues.getAsInteger("errorType")));
      jSONObject.put("profileName", contentValues.getAsString("profileName"));
      jSONObject.put("vendorName", contentValues.getAsString("vendorName"));
      jSONObject.put("userID", contentValues.getAsString("containerID"));
      jSONObject.put("packageList", contentValues.getAsString("packageList"));
    } catch (JSONException unused) {
      Log.e("KnoxVpnEngineService", "toJSONObject(): JSONException");
    }
    return jSONObject.toString();
  }

  public String arrayToString(String[] strArr) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("[");
    if (strArr != null) {
      for (int i = 0; i < strArr.length; i++) {
        if (i > 0) {
          stringBuffer.append(", ");
        }
        stringBuffer.append(strArr[i]);
      }
    }
    stringBuffer.append("]");
    return stringBuffer.toString();
  }

  public class VpnServiceConnection implements ServiceConnection {
    public int adminUid;
    public int containerId;

    public VpnServiceConnection(int i, int i2) {
      this.containerId = i;
      this.adminUid = i2;
    }

    @Override // android.content.ServiceConnection
    public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      IKnoxVpnService knoxVpnServiceInstance =
          KnoxVpnEngineService.this.mInjector.getKnoxVpnServiceInstance(iBinder);
      KnoxVpnEngineService.this.logVpnVendor(componentName);
      String personifiedName =
          KnoxVpnEngineService.this.mKnoxVpnHelper.getPersonifiedName(
              this.containerId, componentName.getPackageName());
      Log.d("KnoxVpnEngineService", "Vendor VPN service connected: pkgName = " + personifiedName);
      KnoxVpnEngineService.this.setVpnInterface(personifiedName, knoxVpnServiceInstance);
      KnoxVpnEngineService.this.mVpnClientStatus.add(
          "onServiceConnected callback has been recieved for the vpn client "
              + personifiedName
              + " at "
              + System.currentTimeMillis());
      KnoxVpnEngineService.this.validateProfilesForVendor(personifiedName, knoxVpnServiceInstance);
      if (KnoxVpnEngineService.this.isNetworkConnected()) {
        KnoxVpnEngineService.this.startVpnConnectionForBindedClient(
            personifiedName, knoxVpnServiceInstance);
      }
      KnoxVpnEngineService.this.sendBindSuccessIntent(personifiedName, this.adminUid);
    }

    @Override // android.content.ServiceConnection
    public synchronized void onServiceDisconnected(ComponentName componentName) {
      String packageName = componentName.getPackageName();
      String personifiedName =
          KnoxVpnEngineService.this.mKnoxVpnHelper.getPersonifiedName(
              this.containerId, packageName);
      Log.d(
          "KnoxVpnEngineService",
          "Vendor VPN service disconnected : vendorName = " + personifiedName);
      KnoxVpnEngineService.this.stopStrongwanProxyConnection(
          packageName, personifiedName, this.containerId);
      KnoxVpnEngineService.this.setVpnInterface(personifiedName, null);
      KnoxVpnEngineService.this.mVpnClientStatus.add(
          "onServiceDisconnected callback has been recieved for the vpn client "
              + personifiedName
              + " at "
              + System.currentTimeMillis());
      StringBuilder sb = new StringBuilder();
      sb.append("Trying to bind again.. Vendor: ");
      sb.append(personifiedName);
      Log.d("KnoxVpnEngineService", sb.toString());
      KnoxVpnEngineService.this.bindKnoxVpnInterface(personifiedName, this.adminUid);
    }

    @Override // android.content.ServiceConnection
    public synchronized void onBindingDied(ComponentName componentName) {
      String personifiedName =
          KnoxVpnEngineService.this.mKnoxVpnHelper.getPersonifiedName(
              this.containerId, componentName.getPackageName());
      Log.d(
          "KnoxVpnEngineService",
          "onBindingDied has been called for the vpn client " + personifiedName);
      KnoxVpnEngineService.this.mVpnClientStatus.add(
          "onBindingDied callback has been recieved for the vpn client "
              + personifiedName
              + " at "
              + System.currentTimeMillis());
    }
  }

  public synchronized void initializeVpnDataAndVendors() {
    if (DBG) {
      Log.d("KnoxVpnEngineService", "initializeVpnDataAndVendors");
    }
    try {
      initializeHashtable();
      if (!this.mKnoxVpnHelper.checkIfProfileListEmpty()) {
        setupIntentFilter();
        this.mFirewallHelper = this.mInjector.getKnoxFirewallHelperInstance();
        getVpnManagerService().registerSystemDefaultNetworkCallback();
        addBlockingRulesForPackages();
        this.mKnoxVpnTetherAuthentication = KnoxVpnTetherAuthentication.getInstance(this.mContext);
      }
    } catch (Exception unused) {
      Log.e("KnoxVpnEngineService", "Error occured while trying to ini the BroadcastReceiver");
    }
  }

  public synchronized EnterpriseResponseData createVpnProfile(
      KnoxVpnContext knoxVpnContext, String str) {
    Exception exc;
    int i;
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(-1);
    enterpriseResponseData.setStatus(1, -1);
    if (knoxVpnContext == null) {
      return enterpriseResponseData;
    }
    if (this.mKnoxVpnHelper.isUsbTetheringConfigured(str)) {
      try {
        try {
          if (!getEnterpriseDeviceManagerService().isCallerValidKPU(checkCallingUidPermission)) {
            enterpriseResponseData.setData(141);
            return enterpriseResponseData;
          }
        } catch (RemoteException unused) {
          enterpriseResponseData.setData(141);
          return enterpriseResponseData;
        }
      } catch (SecurityException unused2) {
        enterpriseResponseData.setData(141);
        return enterpriseResponseData;
      }
    }
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      enterpriseResponseData.setData(140);
      return enterpriseResponseData;
    }
    String str2 = knoxVpnContext.vendorName;
    Log.d(
        "KnoxVpnEngineService",
        "createVpnProfile: vendorName = " + str2 + " : personaId = " + knoxVpnContext.personaId);
    int createVpnProfileValidation =
        this.mKnoxVpnApiValidation.createVpnProfileValidation(knoxVpnContext, str);
    if (createVpnProfileValidation != 100) {
      enterpriseResponseData.setData(Integer.valueOf(createVpnProfileValidation));
      Log.d(
          "KnoxVpnEngineService",
          "knox vpn profile creation failed: The error code is " + createVpnProfileValidation);
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Error occurred while validating profile information for vendor %s", str2),
          knoxVpnContext.personaId);
      return enterpriseResponseData;
    }
    try {
      String personifiedName =
          this.mKnoxVpnHelper.getPersonifiedName(knoxVpnContext.personaId, str2);
      IKnoxVpnService vpnInterface = getVpnInterface(personifiedName);
      if (vpnInterface == null) {
        enterpriseResponseData.setData(110);
        Log.d("KnoxVpnEngineService", "knox vpn profile creation failed: The error code is 110");
        return enterpriseResponseData;
      }
      String profileNameFromJsonString = this.mKnoxVpnHelper.getProfileNameFromJsonString(str);
      syncVpnProfile(vpnInterface, profileNameFromJsonString);
      int createConnection = vpnInterface.createConnection(str);
      if (createConnection != 0) {
        enterpriseResponseData.setData(102);
        Log.d("KnoxVpnEngineService", "knox vpn profile creation failed: The error code is 102");
        AuditLog.logAsUser(
            3,
            5,
            false,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format(
                "Error received from vendor while creating vpn connection for profile %s",
                profileNameFromJsonString),
            knoxVpnContext.personaId);
        return enterpriseResponseData;
      }
      int profileId = this.mVpnStorageProvider.getProfileId();
      if (!this.mKnoxVpnHelper.addVpnProfileToDatabase(knoxVpnContext, str, profileId)) {
        boolean z = DBG;
        if (z) {
          Log.d(
              "KnoxVpnEngineService",
              "knox vpn profile creation failed : failure to add the entry to db");
        }
        Bundle bundle = new Bundle();
        bundle.putString("profileName", profileNameFromJsonString);
        sendMessageToHandler(16, bundle);
        int removeConnection = vpnInterface.removeConnection(profileNameFromJsonString);
        if (z) {
          Log.d(
              "KnoxVpnEngineService",
              "knox vpn profile creation failed : removeStatus value is " + removeConnection);
        }
        enterpriseResponseData.setData(126);
        Log.d("KnoxVpnEngineService", "knox vpn profile creation failed: The error code is 126");
        AuditLog.logAsUser(
            3,
            5,
            false,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format(
                "Error occurred while adding profile %s into database", profileNameFromJsonString),
            knoxVpnContext.personaId);
        return enterpriseResponseData;
      }
      if (!this.mKnoxVpnHelper.addVpnProfileToMap(knoxVpnContext, str, profileId)) {
        boolean z2 = DBG;
        if (z2) {
          Log.d(
              "KnoxVpnEngineService",
              "knox vpn profile creation failed : failure to add the entry to local entry");
        }
        Bundle bundle2 = new Bundle();
        bundle2.putString("profileName", profileNameFromJsonString);
        sendMessageToHandler(16, bundle2);
        int removeConnection2 = vpnInterface.removeConnection(profileNameFromJsonString);
        if (z2) {
          Log.d(
              "KnoxVpnEngineService",
              "knox vpn profile creation failed : removeStatus value is " + removeConnection2);
        }
        this.mVpnStorageProvider.deleteDataByFields(
            "VpnProfileInfo",
            new String[] {"profileName"},
            new String[] {profileNameFromJsonString});
        enterpriseResponseData.setData(127);
        Log.d("KnoxVpnEngineService", "knox vpn profile creation failed: The error code is 127");
        AuditLog.logAsUser(
            3,
            5,
            false,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format(
                "Error occurred while adding vpn profile %s in vpn map", profileNameFromJsonString),
            knoxVpnContext.personaId);
        return enterpriseResponseData;
      }
      if (this.vpnConfig.getProfileEntries().size() == 1) {
        setupIntentFilter();
        KnoxVpnFirewallHelper knoxFirewallHelperInstance =
            this.mInjector.getKnoxFirewallHelperInstance();
        this.mFirewallHelper = knoxFirewallHelperInstance;
        knoxFirewallHelperInstance.updateIpBlockingRule();
        KnoxVpnFirewallHelper knoxVpnFirewallHelper = this.mFirewallHelper;
        String str3 = KnoxVpnConstants.BLOCK_APP_TRAFFIC;
        knoxVpnFirewallHelper.addRulesForNoUidPackets(str3, str3, 3);
        Log.d(
            "KnoxVpnEngineService",
            "Setting the system property to confirm Generic vpn policy has been configured");
        SystemProperties.set("net.vpn.framework", "2.0");
        allowAppsToMakeDnsQueryForNetId();
        this.mKnoxVpnHelper.addOrRemoveAppsFromBatteryOptimization(
            profileNameFromJsonString, "com.android.vpndialogs", true);
        getVpnManagerService().registerSystemDefaultNetworkCallback();
        this.mKnoxVpnTetherAuthentication = KnoxVpnTetherAuthentication.getInstance(this.mContext);
      }
      if (this.mKnoxVpnHelper.getConnectionType(profileNameFromJsonString) == 1
          && !this.mProcessManager.isProcessObserverRegistered()) {
        this.mProcessManager.registerProcessObserver();
      }
      try {
        addExemptRulesForUid(
            this.mKnoxVpnHelper.getUidForPackageName(personifiedName), profileNameFromJsonString);
        this.mKnoxVpnHelper.addOrRemoveSystemAppFromBatteryOptimization(
            profileNameFromJsonString, "com.knox.vpn.proxyhandler", true);
        this.mKnoxVpnHelper.addOrRemoveAppsFromBatteryOptimization(
            profileNameFromJsonString, str2, true);
        this.mKnoxVpnHelper.addOrRemoveSystemAppFromDataSaverWhitelist(
            profileNameFromJsonString, UserHandle.getUid(knoxVpnContext.personaId, 1002), true);
        String randomIpChainName =
            this.mKnoxVpnHelper.setRandomIpChainName(profileNameFromJsonString);
        Log.d(
            "KnoxVpnEngineService",
            "The IP Chain Name obtained for the profile "
                + profileNameFromJsonString
                + " is "
                + randomIpChainName);
        this.mFirewallHelper.createIpChainForProfile(randomIpChainName);
        this.mFirewallHelper.addRulesInOutputChain(randomIpChainName);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          getVpnManagerService()
              .createEnterpriseVpnInstance(
                  str2,
                  profileNameFromJsonString,
                  knoxVpnContext.personaId,
                  this.mKnoxVpnHelper.getChainingValueForProfile(profileNameFromJsonString));
          Binder.restoreCallingIdentity(clearCallingIdentity);
          Log.d("KnoxVpnEngineService", "profileid : " + profileId);
          String[] strArr = {String.valueOf(profileId)};
          ContentValues contentValues = new ContentValues();
          contentValues.put("profileCount", Integer.valueOf(profileId + 1));
          this.mVpnStorageProvider.putDataByFields(
              "VpnAnalyticsTable", new String[] {"profileCount"}, strArr, contentValues);
          Log.d(
              "KnoxVpnEngineService",
              "knox vpn profile creation success: The error code is " + createConnection);
          AuditLog.logAsUser(
              5,
              5,
              true,
              Process.myPid(),
              "KnoxVpnEngineService",
              String.format("Vpn profile %s successfully created", profileNameFromJsonString),
              knoxVpnContext.personaId);
          try {
            this.mVpnStorageProvider.deleteDataByFields(
                "vpnConnectionFail",
                new String[] {"profileName", "adminUid"},
                new String[] {
                  profileNameFromJsonString, String.valueOf(checkCallingUidPermission.mCallerUid)
                });
          } catch (Exception e) {
            Log.e(
                "KnoxVpnEngineService",
                "Exception at removeProfileByEnterpriseVpnConnectionFailTable "
                    + Log.getStackTraceString(e));
          }
          enterpriseResponseData.setData(Integer.valueOf(createConnection));
          enterpriseResponseData.setStatus(0, 0);
          try {
            if (((Integer) enterpriseResponseData.getData()).intValue() == 0) {
              KnoxAnalyticsData knoxAnalyticsData =
                  new KnoxAnalyticsData("KNOX_VPN", 1, "API:createVpnProfile");
              this.mData = knoxAnalyticsData;
              setCommonProperties(knoxAnalyticsData, knoxVpnContext, profileNameFromJsonString, -1);
              setPropertiesWithLocalEntry(this.mData, profileNameFromJsonString);
              KnoxAnalytics.log(this.mData);
            }
          } catch (Exception e2) {
            Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e2));
          }
          return enterpriseResponseData;
        } catch (Throwable th) {
          Binder.restoreCallingIdentity(clearCallingIdentity);
          throw th;
        }
      } catch (Exception e3) {
        exc = e3;
        i = -1;
        enterpriseResponseData.setData(Integer.valueOf(i));
        Log.d(
            "KnoxVpnEngineService",
            "knox vpn profile creation failed: exception occured: The error code is -1"
                + Log.getStackTraceString(exc));
        AuditLog.logAsUser(
            3,
            5,
            false,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format("Exception occurred while creating vpn profile for vendor %s", str2),
            knoxVpnContext.personaId);
        return enterpriseResponseData;
      }
    } catch (Exception e4) {
      exc = e4;
      i = -1;
    }
  }

  public synchronized EnterpriseResponseData getVpnProfile(
      KnoxVpnContext knoxVpnContext, String str) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    Log.d("KnoxVpnEngineService", "profile info is going to be fetched for the proifle " + str);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData((Object) null);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      return enterpriseResponseData;
    }
    try {
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "getVpnProfile exception result is " + Log.getStackTraceString(e));
    }
    if (this.mKnoxVpnApiValidation.getVpnProfileValidation(knoxVpnContext, str) == null) {
      Log.d(
          "KnoxVpnEngineService",
          "getting vpn profile Info failed: Error occured while validating the profile");
      return enterpriseResponseData;
    }
    IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
    if (binderInterfaceForProfile == null) {
      Log.d(
          "KnoxVpnEngineService",
          "getting vpn profile Info failed: Error occured due to invalid interface");
      return enterpriseResponseData;
    }
    String connection = binderInterfaceForProfile.getConnection(str);
    if (DBG) {
      Log.d("KnoxVpnEngineService", "getVpnProfile: jsonProfile value is " + connection);
    }
    enterpriseResponseData.setData(connection);
    enterpriseResponseData.setStatus(0, 0);
    return enterpriseResponseData;
  }

  public synchronized EnterpriseResponseData removeVpnProfile(
      KnoxVpnContext knoxVpnContext, String str) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    Log.d(
        "KnoxVpnEngineService",
        "knox vpn profile is going to be removed : profileName value is " + str);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(-1);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      enterpriseResponseData.setData(140);
      return enterpriseResponseData;
    }
    if ("com.android.settings".equals(knoxVpnContext.vendorName) && getVpnInfoPolicy() != null) {
      try {
        if (this.mVpnInfoPolicy.deleteProfile(checkCallingUidPermission, str)) {
          enterpriseResponseData.setData(0);
          enterpriseResponseData.setStatus(0, 0);
        }
      } catch (RemoteException unused) {
        Log.e("KnoxVpnEngineService", "Exception occured while removing vpn settings profile");
      }
      return enterpriseResponseData;
    }
    try {
      int removeVpnProfileValidation =
          this.mKnoxVpnApiValidation.removeVpnProfileValidation(knoxVpnContext, str);
      if (removeVpnProfileValidation != 100) {
        enterpriseResponseData.setData(Integer.valueOf(removeVpnProfileValidation));
        Log.d(
            "KnoxVpnEngineService",
            "Error occured while removing the vpn profile: The error code is "
                + removeVpnProfileValidation);
        AuditLog.logAsUser(
            3,
            5,
            false,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format("Error validating information from profile %s before removing", str),
            knoxVpnContext.personaId);
        return enterpriseResponseData;
      }
      IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
      if (binderInterfaceForProfile == null) {
        enterpriseResponseData.setData(110);
        Log.d(
            "KnoxVpnEngineService",
            "Error occured while removing the vpn profile: The error code is 110");
        return enterpriseResponseData;
      }
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry.getActivateState() == 1) {
        int state = binderInterfaceForProfile.getState(str);
        Log.d(
            "KnoxVpnEngineService",
            "knox vpn profile is going to be removed: currentStateOfProfile value is " + state);
        if (state != 1 || state != 5) {
          if (binderInterfaceForProfile.stopConnection(str) != 0) {
            AuditLog.logAsUser(
                3,
                5,
                false,
                Process.myPid(),
                "KnoxVpnEngineService",
                String.format("Error occurred trying to stop vpn connection from profile %s", str),
                knoxVpnContext.personaId);
          } else {
            AuditLog.logAsUser(
                5,
                5,
                true,
                Process.myPid(),
                "KnoxVpnEngineService",
                String.format("Connection with vpn vendor service stopped for profile %s", str),
                knoxVpnContext.personaId);
          }
          Log.d(
              "KnoxVpnEngineService",
              "knox vpn profile is going to be removed: stopConnectionStatus value is " + state);
        }
        int state2 = binderInterfaceForProfile.getState(str);
        Log.d(
            "KnoxVpnEngineService",
            "knox vpn profile is going to be removed: currentStateOfProfile after stopping the"
                + " connection is "
                + state2);
        if (state2 != 1 && state2 != 5) {
          enterpriseResponseData.setData(306);
          Log.d(
              "KnoxVpnEngineService",
              "Error occured while removing the vpn profile: The error code is 306");
          AuditLog.logAsUser(
              3,
              5,
              false,
              Process.myPid(),
              "KnoxVpnEngineService",
              String.format(
                  "Error occurred while removing vpn profile %s. Stop vpn connection before"
                      + " removing profile",
                  str),
              knoxVpnContext.personaId);
          return enterpriseResponseData;
        }
      }
      int profileId = profileEntry.getProfileId();
      int removeConnection = binderInterfaceForProfile.removeConnection(str);
      if (removeConnection == 0) {
        removeProfileFromHashMapAndDB(str);
        Log.d(
            "KnoxVpnEngineService",
            "knox vpn profile removal is a success: The error code is " + removeConnection);
        AuditLog.logAsUser(
            5,
            5,
            true,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format("Vpn profile %s successfully removed", str),
            knoxVpnContext.personaId);
        enterpriseResponseData.setData(Integer.valueOf(removeConnection));
        enterpriseResponseData.setStatus(0, 0);
        try {
          if (((Integer) enterpriseResponseData.getData()).intValue() == 0) {
            KnoxAnalyticsData knoxAnalyticsData =
                new KnoxAnalyticsData("KNOX_VPN", 1, "API:removeVpnProfile");
            this.mData = knoxAnalyticsData;
            setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, profileId);
            KnoxAnalytics.log(this.mData);
          }
        } catch (Exception e) {
          Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e));
        }
        return enterpriseResponseData;
      }
      enterpriseResponseData.setData(102);
      Log.d(
          "KnoxVpnEngineService",
          "Error occured while removing the vpn profile: The error code is 102");
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format(
              "Error received from vendor while removing vpn connection for profile %s", str),
          knoxVpnContext.personaId);
      return enterpriseResponseData;
    } catch (Exception e2) {
      Log.d(
          "KnoxVpnEngineService",
          "Error occured while removing the vpn profile: exception occured: The error code is -1"
              + Log.getStackTraceString(e2));
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Exception occurred while removing vpn profile %s", str),
          knoxVpnContext.personaId);
      return enterpriseResponseData;
    }
  }

  public synchronized EnterpriseResponseData activateVpnProfile(
      KnoxVpnContext knoxVpnContext, String str, boolean z) {
    int activateVpnProfileValidation;
    String str2;
    String interfaceNameForUsbtethering;
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    Log.d(
        "KnoxVpnEngineService",
        "knox vpn profile activation is going to take place: profileName value is " + str);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(-1);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      enterpriseResponseData.setData(140);
      return enterpriseResponseData;
    }
    try {
      activateVpnProfileValidation =
          this.mKnoxVpnApiValidation.activateVpnProfileValidation(knoxVpnContext, str, z);
    } catch (Exception e) {
      Log.d(
          "KnoxVpnEngineService",
          "Error occured while activating the vpn profile: exception occured: The error code is -1"
              + Log.getStackTraceString(e));
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Exception occurred while activating vpn profile %s", str),
          knoxVpnContext.personaId);
    }
    if (activateVpnProfileValidation != 100) {
      enterpriseResponseData.setData(Integer.valueOf(activateVpnProfileValidation));
      Log.d(
          "KnoxVpnEngineService",
          "Error occured while activating the vpn profile: The error code is "
              + activateVpnProfileValidation);
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Error validating information from profile %s before activation", str),
          knoxVpnContext.personaId);
      return enterpriseResponseData;
    }
    IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
    if (binderInterfaceForProfile == null) {
      enterpriseResponseData.setData(110);
      Log.d(
          "KnoxVpnEngineService",
          "Error occured while activating the vpn profile: The error code is 110");
      return enterpriseResponseData;
    }
    boolean z2 = false;
    int i = z ? 1 : 0;
    Log.d("KnoxVpnEngineService", "current profile activation state is " + i);
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry == null) {
      enterpriseResponseData.setData(108);
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format(
              "Error occurred while activating vpn profile %s. Profile does not exist in device.",
              str),
          knoxVpnContext.personaId);
      return enterpriseResponseData;
    }
    char c = 65534;
    if (i == 1) {
      boolean activate = setActivate(str, i);
      if (DBG) {
        Log.d(
            "KnoxVpnEngineService",
            "activateVpnProfile: insertActivateInfoInDb value is " + activate);
      }
      if (!activate) {
        enterpriseResponseData.setData(126);
        Log.d(
            "KnoxVpnEngineService",
            "Error occured while activating the vpn profile: The error code is 126");
        AuditLog.logAsUser(
            3,
            5,
            false,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format(
                "Error adding vpn profile info in database while activating profile %s", str),
            knoxVpnContext.personaId);
        return enterpriseResponseData;
      }
      String activeNetworkInterface = this.mKnoxVpnHelper.getActiveNetworkInterface();
      if (activeNetworkInterface == null) {
        enterpriseResponseData.setData(Integer.valueOf(FrameworkStatsLog.f85x3da64833));
        return enterpriseResponseData;
      }
      this.mKnoxVpnHelper.applyBlockingRulesToUidRange(str, true);
      if (profileEntry.getUsbTethering() == 1
          && (interfaceNameForUsbtethering = this.mKnoxVpnHelper.getInterfaceNameForUsbtethering())
              != null) {
        Log.d(
            "KnoxVpnEngineService",
            "Applying rules to drop tether packets after the profile is activated and before vpn"
                + " comes up");
        this.mFirewallHelper.addRulesForDroppingTetherPackets(interfaceNameForUsbtethering);
      }
      Iterator it = profileEntry.getExemptPackageList().iterator();
      while (it.hasNext()) {
        int intValue = ((Integer) it.next()).intValue();
        String str3 = activeNetworkInterface;
        updateRulesToExemptUid(str, 1, 1, intValue, 0, null, null);
        updateRulesToExemptUid(str, 1, 2, intValue, 3, null, str3);
        z2 = z2;
        activeNetworkInterface = str3;
        c = 65534;
      }
      boolean z3 = z2;
      String str4 = activeNetworkInterface;
      if (getChainingEnabledForProfile(profileEntry.getVendorUid()) == 1) {
        str2 = str4;
      } else if (this.mKnoxVpnHelper.isNativeVpnClient(str)) {
        str2 = str4;
        this.mFirewallHelper.addIpRulesForExemptedUid(1016, str2, 3);
      } else {
        str2 = str4;
        this.mFirewallHelper.addIpRulesForExemptedUid(profileEntry.getVendorUid(), str2, 3);
      }
      Iterator it2 = this.mKnoxVpnHelper.getuserIdForExemptedUidByProfile(str).iterator();
      while (it2.hasNext()) {
        this.mFirewallHelper.addExemptRulesForDownloadManagerUid(
            this.mKnoxVpnHelper.getUIDForPackage(
                ((Integer) it2.next()).intValue(), "com.android.providers.downloads"),
            str2);
      }
      String regularPackageName =
          this.mKnoxVpnHelper.getRegularPackageName(profileEntry.getVendorPkgName());
      this.mFirewallHelper.addMarkingRulesForFilteredPackages(
          KnoxVpnConstants.BLOCK_APP_TRAFFIC, profileEntry.getIpChainName(), 3);
      this.mFirewallHelper.addInputFilterDropRulesForInterface(
          regularPackageName, 1, this.mKnoxVpnHelper.getuserIdListForProfile(str), str2);
      this.mFirewallHelper.addInputFilterDropRulesForInterface(
          regularPackageName, z3 ? 1 : 0, this.mKnoxVpnHelper.getuidListForProfile(str), str2);
      ArrayList arrayList = new ArrayList();
      boolean z4 = z3 ? 1 : 0;
      for (VpnPackageInfo vpnPackageInfo : profileEntry.getPackageList()) {
        if (vpnPackageInfo.getUid() == -2) {
          if (vpnPackageInfo.getCid() == 0) {
            blockIncomingICMPPackets(true);
            Integer[] numArr = KnoxVpnConstants.AID_EXEMPT_LIST;
            int length = numArr.length;
            for (int i2 = z3 ? 1 : 0; i2 < length; i2++) {
              this.mFirewallHelper.addExemptRulesForUid(numArr[i2].intValue());
            }
          }
          getOemNetdService().knoxVpnBlockUserWideDnsQuery(true, vpnPackageInfo.getCid());
          int startVpnForUserwideVpn = startVpnForUserwideVpn(str, vpnPackageInfo.getCid(), z3);
          if (startVpnForUserwideVpn != 0) {
            enterpriseResponseData.setData(Integer.valueOf(startVpnForUserwideVpn));
            return enterpriseResponseData;
          }
          z4 = true;
        } else if (vpnPackageInfo.getUid() != -1) {
          arrayList.add(Integer.valueOf(vpnPackageInfo.getUid()));
        }
      }
      boolean z5 = z4;
      if (!arrayList.isEmpty()) {
        int startVpnForPerApplication = startVpnForPerApplication(str, arrayList, z3);
        if (startVpnForPerApplication != 0) {
          enterpriseResponseData.setData(Integer.valueOf(startVpnForPerApplication));
          return enterpriseResponseData;
        }
        z5 = true;
      }
      if (z5) {
        int startVpnProfile = startVpnProfile(str);
        if (startVpnProfile == 303) {
          Log.d(
              "KnoxVpnEngineService",
              "starting the vpn connection delayed due to app not started on on-demand config "
                  + startVpnProfile);
        } else if (startVpnProfile == 302) {
          Log.d(
              "KnoxVpnEngineService",
              "starting the vpn connection delayed since no packages were added to vpn profile"
                  + startVpnProfile);
        } else if (startVpnProfile != 0) {
          Log.e(
              "KnoxVpnEngineService",
              "Error occurred while starting the vpn profile: The error code is "
                  + startVpnProfile);
          enterpriseResponseData.setData(Integer.valueOf(startVpnProfile));
          return enterpriseResponseData;
        }
      }
      enterpriseResponseData.setData(Integer.valueOf(z3 ? 1 : 0));
      enterpriseResponseData.setStatus(z3 ? 1 : 0, z3 ? 1 : 0);
      AuditLog.logAsUser(
          5,
          5,
          true,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Vpn profile %s successfully activated", str),
          knoxVpnContext.personaId);
    } else if (i == 0) {
      boolean activate2 = setActivate(str, i);
      if (DBG) {
        Log.d(
            "KnoxVpnEngineService",
            "deactivateVpnProfile: insertActivateInfoInDb value is " + activate2);
      }
      if (!activate2) {
        enterpriseResponseData.setData(126);
        Log.d(
            "KnoxVpnEngineService",
            "Error occured while deactivating the vpn profile: The error code is 126");
        AuditLog.logAsUser(
            3,
            5,
            false,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format(
                "Error adding vpn profile info in database while deactivating profile %s", str),
            knoxVpnContext.personaId);
        return enterpriseResponseData;
      }
      this.mKnoxVpnHelper.applyBlockingRulesToUidRange(str, false);
      Iterator it3 = this.mKnoxVpnHelper.getuserIdForExemptedUidByProfile(str).iterator();
      while (it3.hasNext()) {
        this.mFirewallHelper.removeExemptRulesForDownloadManagerUid(
            this.mKnoxVpnHelper.getUIDForPackage(
                ((Integer) it3.next()).intValue(), "com.android.providers.downloads"));
      }
      Iterator it4 = profileEntry.getExemptPackageList().iterator();
      while (it4.hasNext()) {
        updateRulesToExemptUid(str, 0, 1, ((Integer) it4.next()).intValue(), 0, null, null);
        binderInterfaceForProfile = binderInterfaceForProfile;
      }
      IKnoxVpnService iKnoxVpnService = binderInterfaceForProfile;
      Iterator it5 = profileEntry.getPackageList().iterator();
      while (true) {
        if (!it5.hasNext()) {
          break;
        }
        VpnPackageInfo vpnPackageInfo2 = (VpnPackageInfo) it5.next();
        if (vpnPackageInfo2.getUid() == -2) {
          getOemNetdService().knoxVpnBlockUserWideDnsQuery(false, vpnPackageInfo2.getCid());
          if (vpnPackageInfo2.getCid() == 0) {
            blockIncomingICMPPackets(false);
            for (Integer num : KnoxVpnConstants.AID_EXEMPT_LIST) {
              this.mFirewallHelper.removeExemptRulesForUid(num.intValue());
            }
          }
        }
      }
      this.mFirewallHelper.flushMarkingRulesForFilteredPackages(profileEntry.getIpChainName());
      this.mFirewallHelper.removeInputFilterDropRulesForInterface(
          1, this.mKnoxVpnHelper.getuserIdListForProfile(str));
      this.mFirewallHelper.removeInputFilterDropRulesForInterface(
          0, this.mKnoxVpnHelper.getuidListForProfile(str));
      if (profileEntry.getProxyServer() != KnoxVpnConstants.DEFAULT_PROXY_SERVER
          || profileEntry.getPacurl() != Uri.EMPTY) {
        getKnoxVpnPacProcessor().stopPacSupport(str);
        updateProxyRules(0, str, getKnoxVpnHelperInstance().getListOfUid(str));
      }
      removeMiscRulesForProfile(str);
      int state = iKnoxVpnService.getState(str);
      if (state != 1 || state != 5) {
        int stopConnection = iKnoxVpnService.stopConnection(str);
        boolean z6 = DBG;
        if (z6) {
          Log.d(
              "KnoxVpnEngineService",
              "deactivateVpnProfile: stopStatus value is " + stopConnection);
        }
        if (stopConnection != 0) {
          int stopConnection2 = iKnoxVpnService.stopConnection(str);
          if (stopConnection2 != 0) {
            AuditLog.logAsUser(
                3,
                5,
                false,
                Process.myPid(),
                "KnoxVpnEngineService",
                String.format("Error occurred trying to stop vpn connection from profile %s", str),
                knoxVpnContext.personaId);
          } else {
            AuditLog.logAsUser(
                5,
                5,
                true,
                Process.myPid(),
                "KnoxVpnEngineService",
                String.format("Connection with vpn vendor service stopped for profile %s", str),
                knoxVpnContext.personaId);
          }
          if (z6) {
            Log.d(
                "KnoxVpnEngineService",
                "deactivateVpnProfile: stopStatus for lockup case value is " + stopConnection2);
          }
        } else {
          AuditLog.logAsUser(
              5,
              5,
              true,
              Process.myPid(),
              "KnoxVpnEngineService",
              String.format("Connection with vpn vendor service stopped for profile %s", str),
              knoxVpnContext.personaId);
        }
      }
      Log.d(
          "KnoxVpnEngineService",
          "De-Activating the vpn profile is a success: The error code is 0");
      AuditLog.logAsUser(
          5,
          5,
          true,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Vpn profile %s successfully deactivated", str),
          knoxVpnContext.personaId);
      enterpriseResponseData.setData(0);
      enterpriseResponseData.setStatus(0, 0);
    }
    try {
      if (((Integer) enterpriseResponseData.getData()).intValue() == 0) {
        KnoxAnalyticsData knoxAnalyticsData =
            new KnoxAnalyticsData("KNOX_VPN", 1, "API:activateVpnProfile");
        this.mData = knoxAnalyticsData;
        setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
        this.mData.setProperty("enb", z);
        KnoxAnalytics.log(this.mData);
      }
    } catch (Exception e2) {
      Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e2));
    }
    return enterpriseResponseData;
  }

  public synchronized EnterpriseResponseData getAllVpnProfiles(KnoxVpnContext knoxVpnContext) {
    IKnoxVpnService vpnInterface;
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData((Object) null);
    enterpriseResponseData.setStatus(1, -1);
    if (knoxVpnContext == null) {
      return enterpriseResponseData;
    }
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      return enterpriseResponseData;
    }
    int i = knoxVpnContext.adminId;
    String str = knoxVpnContext.vendorName;
    int i2 = knoxVpnContext.personaId;
    String personifiedName = this.mKnoxVpnHelper.getPersonifiedName(i2, str);
    Log.d(
        "KnoxVpnEngineService", "getAllVpnProfiles: vendorNameWithCid value is " + personifiedName);
    if ("com.android.settings".equals(str) && getVpnInfoPolicy() != null) {
      try {
        List allVpnSettingsProfiles =
            this.mVpnInfoPolicy.getAllVpnSettingsProfiles(checkCallingUidPermission);
        if (allVpnSettingsProfiles != null) {
          enterpriseResponseData.setData(allVpnSettingsProfiles);
          enterpriseResponseData.setStatus(0, 0);
        }
      } catch (RemoteException unused) {
        Log.e("KnoxVpnEngineService", "Exception occured while getting vpn settings profiles");
      }
      return enterpriseResponseData;
    }
    try {
      vpnInterface = getVpnInterface(personifiedName);
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "getAllVpnProfiles exception result is " + Log.getStackTraceString(e));
    }
    if (vpnInterface == null) {
      Log.d("KnoxVpnEngineService", "getAllVpnProfiles: interface returned null");
      enterpriseResponseData.setData((Object) null);
      return enterpriseResponseData;
    }
    List<String> allConnections = vpnInterface.getAllConnections();
    if (allConnections != null) {
      r1 = allConnections.size() > 0 ? new ArrayList() : null;
      for (String str2 : allConnections) {
        String profileNameFromJsonString = this.mKnoxVpnHelper.getProfileNameFromJsonString(str2);
        boolean z = DBG;
        if (z) {
          Log.d(
              "KnoxVpnEngineService",
              "getAllVpnProfiles: profileName > " + profileNameFromJsonString);
        }
        if (z) {
          Log.d("KnoxVpnEngineService", "getAllVpnProfiles: profile > " + str2);
        }
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(profileNameFromJsonString);
        if (profileEntry != null
            && (i == profileEntry.getAdminId() || UserHandle.getAppId(i) == 1000)) {
          if (i2 == profileEntry.getPersonaId()) {
            r1.add(str2);
          }
        }
      }
    }
    enterpriseResponseData.setData(r1);
    enterpriseResponseData.setStatus(0, 0);
    return enterpriseResponseData;
  }

  public synchronized EnterpriseResponseData setUserCertificate(
      KnoxVpnContext knoxVpnContext, String str, byte[] bArr, String str2) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(Boolean.FALSE);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      return enterpriseResponseData;
    }
    if (str != null) {
      try {
        if (validateAdminAndVendorForProfile(knoxVpnContext, str, enterpriseResponseData) != 0) {
          return enterpriseResponseData;
        }
        IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
        if (binderInterfaceForProfile == null) {
          Log.e("KnoxVpnEngineService", "setting user certificate : VPN Service not started");
          return enterpriseResponseData;
        }
        enterpriseResponseData.setData(
            Boolean.valueOf(binderInterfaceForProfile.setUserCertificate(str, bArr, str2)));
        enterpriseResponseData.setStatus(0, 0);
      } catch (Exception e) {
        Log.e(
            "KnoxVpnEngineService",
            "setting user certificate : Failure at " + Log.getStackTraceString(e));
        return null;
      }
    }
    try {
      if (((Boolean) enterpriseResponseData.getData()).booleanValue()) {
        KnoxAnalyticsData knoxAnalyticsData =
            new KnoxAnalyticsData("KNOX_VPN", 1, "API:setUserCertificate");
        this.mData = knoxAnalyticsData;
        setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
        KnoxAnalytics.log(this.mData);
      }
    } catch (Exception e2) {
      Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e2));
    }
    return enterpriseResponseData;
  }

  public synchronized EnterpriseResponseData getUserCertificate(
      KnoxVpnContext knoxVpnContext, String str) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData((Object) null);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      return enterpriseResponseData;
    }
    try {
      if (validateAdminAndVendorForProfile(knoxVpnContext, str, enterpriseResponseData) != 0) {
        return enterpriseResponseData;
      }
      IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
      if (binderInterfaceForProfile == null) {
        Log.e("KnoxVpnEngineService", "getting user certificate : VPN Service not started");
        return enterpriseResponseData;
      }
      enterpriseResponseData.setData(binderInterfaceForProfile.getUserCertificate(str));
      enterpriseResponseData.setStatus(0, 0);
      return enterpriseResponseData;
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "getting user certificate : Failure at " + Log.getStackTraceString(e));
      return null;
    }
  }

  public EnterpriseResponseData setCACertificate(
      KnoxVpnContext knoxVpnContext, String str, byte[] bArr) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(Boolean.FALSE);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      return enterpriseResponseData;
    }
    try {
      if (validateAdminAndVendorForProfile(knoxVpnContext, str, enterpriseResponseData) != 0) {
        return enterpriseResponseData;
      }
      IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
      if (binderInterfaceForProfile == null) {
        Log.e("KnoxVpnEngineService", "setting CACertificate : Service is not started");
        return enterpriseResponseData;
      }
      enterpriseResponseData.setData(
          Boolean.valueOf(binderInterfaceForProfile.setCACertificate(str, bArr)));
      enterpriseResponseData.setStatus(0, 0);
      try {
        if (((Boolean) enterpriseResponseData.getData()).booleanValue()) {
          KnoxAnalyticsData knoxAnalyticsData =
              new KnoxAnalyticsData("KNOX_VPN", 1, "API:setCACertificate");
          this.mData = knoxAnalyticsData;
          setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
          KnoxAnalytics.log(this.mData);
        }
      } catch (Exception e) {
        Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e));
      }
      return enterpriseResponseData;
    } catch (Exception e2) {
      Log.e(
          "KnoxVpnEngineService",
          "setting CACertificate : Failure at " + Log.getStackTraceString(e2));
      return null;
    }
  }

  public EnterpriseResponseData getCACertificate(KnoxVpnContext knoxVpnContext, String str) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData((Object) null);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      return enterpriseResponseData;
    }
    try {
      if (validateAdminAndVendorForProfile(knoxVpnContext, str, enterpriseResponseData) != 0) {
        return enterpriseResponseData;
      }
      IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
      if (binderInterfaceForProfile == null) {
        Log.e("KnoxVpnEngineService", "getting CACertificate : Service is not started");
        return null;
      }
      enterpriseResponseData.setData(binderInterfaceForProfile.getCACertificate(str));
      enterpriseResponseData.setStatus(0, 0);
      return enterpriseResponseData;
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "getting CACertificate : Failure at " + Log.getStackTraceString(e));
      return null;
    }
  }

  public synchronized EnterpriseResponseData startConnection(
      KnoxVpnContext knoxVpnContext, String str) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    Log.d("KnoxVpnEngineService", "knox vpn profile is going to be started for the profile " + str);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(-1);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      enterpriseResponseData.setData(140);
      return enterpriseResponseData;
    }
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry == null) {
      return enterpriseResponseData;
    }
    try {
      if (validateAdminAndVendorForProfile(knoxVpnContext, str, enterpriseResponseData) != 0) {
        enterpriseResponseData.setStatus(1, 8);
        AuditLog.logAsUser(
            3,
            5,
            false,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format("Error while validating admin and vendor for profile %s", str),
            knoxVpnContext.personaId);
        return enterpriseResponseData;
      }
      IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
      if (binderInterfaceForProfile == null) {
        Log.e("KnoxVpnEngineService", "starting vpn connection : Mocana Service is not started");
        return null;
      }
      int vPNTransitionState = getVPNTransitionState(str);
      int activateState = profileEntry.getActivateState();
      int vpnConnectionType = profileEntry.getVpnConnectionType();
      boolean z =
          !(vPNTransitionState == 1 || vPNTransitionState == 5 || vPNTransitionState == -1)
              || activateState == 0;
      boolean z2 = vpnConnectionType == 0;
      boolean z3 = vpnConnectionType == 1 && this.mProcessManager.processRunCheck(profileEntry);
      if (z) {
        Log.d(
            "KnoxVpnEngineService",
            "not allowd state for starting a vpn : profileName = "
                + str
                + ": state = "
                + vPNTransitionState);
        AuditLog.logAsUser(
            3,
            5,
            false,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format(
                "Not allowed to start connection for profile %s. Check vpn and profile status",
                str),
            knoxVpnContext.personaId);
        enterpriseResponseData.setStatus(1, 12);
        return enterpriseResponseData;
      }
      if (DBG) {
        Log.d("KnoxVpnEngineService", "keepOn =  " + z2 + " / onDemand = " + z3);
      }
      if (z2 || z3) {
        int startConnection = binderInterfaceForProfile.startConnection(str);
        enterpriseResponseData.setData(Integer.valueOf(startConnection));
        enterpriseResponseData.setStatus(0, 0);
        if (startConnection != 0) {
          AuditLog.logAsUser(
              3,
              5,
              false,
              Process.myPid(),
              "KnoxVpnEngineService",
              String.format("Error trying to start vpn connection for profile %s", str),
              knoxVpnContext.personaId);
        } else {
          AuditLog.logAsUser(
              5,
              5,
              true,
              Process.myPid(),
              "KnoxVpnEngineService",
              String.format("Connection established for vpn profile %s", str),
              knoxVpnContext.personaId);
        }
      }
      AuditLog.logAsUser(
          5,
          5,
          true,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Vpn connection established for profile %s", str),
          knoxVpnContext.personaId);
      return enterpriseResponseData;
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "starting vpn connection : Failure at " + Log.getStackTraceString(e));
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Exception starting connection for profile %s", str),
          knoxVpnContext.personaId);
      return null;
    }
  }

  public synchronized EnterpriseResponseData stopConnection(
      KnoxVpnContext knoxVpnContext, String str) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    Log.d("KnoxVpnEngineService", "knox vpn profile is going to be stopped for the profile " + str);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(-1);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      enterpriseResponseData.setData(140);
      return enterpriseResponseData;
    }
    try {
      if (validateAdminAndVendorForProfile(knoxVpnContext, str, enterpriseResponseData) != 0) {
        return enterpriseResponseData;
      }
      IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
      if (binderInterfaceForProfile == null) {
        Log.e("KnoxVpnEngineService", "stopping vpn connection : Service is not started");
        return enterpriseResponseData;
      }
      int stopConnection = binderInterfaceForProfile.stopConnection(str);
      enterpriseResponseData.setData(Integer.valueOf(stopConnection));
      enterpriseResponseData.setStatus(0, 0);
      if (stopConnection != 0) {
        AuditLog.logAsUser(
            3,
            5,
            false,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format("Error occurred trying to stop vpn connection from profile %s", str),
            knoxVpnContext.personaId);
      } else {
        AuditLog.logAsUser(
            5,
            5,
            true,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format("Connection with vpn vendor service stopped for profile %s", str),
            knoxVpnContext.personaId);
      }
      return enterpriseResponseData;
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "stopping vpn connection : Failure at " + Log.getStackTraceString(e));
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Exception stopping connection for profile %s", str),
          knoxVpnContext.personaId);
      return enterpriseResponseData;
    }
  }

  public EnterpriseResponseData getState(KnoxVpnContext knoxVpnContext, String str) {
    int stateValidation;
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    Log.d("KnoxVpnEngineService", "getState API called for profileName = " + str);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(-1);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      enterpriseResponseData.setData(140);
      return enterpriseResponseData;
    }
    try {
      stateValidation = this.mKnoxVpnApiValidation.getStateValidation(knoxVpnContext, str);
    } catch (Exception e) {
      if (DBG) {
        Log.e(
            "KnoxVpnEngineService", "getting vpn state : Failure at " + Log.getStackTraceString(e));
      }
    }
    if (stateValidation != 100) {
      enterpriseResponseData.setData(Integer.valueOf(stateValidation));
      Log.d(
          "KnoxVpnEngineService",
          "knox vpn profile current state request failed: The error code is " + stateValidation);
      return enterpriseResponseData;
    }
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry == null) {
      return enterpriseResponseData;
    }
    if (profileEntry.getActivateState() == 0) {
      enterpriseResponseData.setData(0);
      enterpriseResponseData.setStatus(0, 0);
      return enterpriseResponseData;
    }
    IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
    if (binderInterfaceForProfile == null) {
      enterpriseResponseData.setData(110);
      Log.d(
          "KnoxVpnEngineService",
          "knox vpn profile current state request failed: The error code is 110");
      return enterpriseResponseData;
    }
    enterpriseResponseData.setData(Integer.valueOf(binderInterfaceForProfile.getState(str)));
    enterpriseResponseData.setStatus(0, 0);
    return enterpriseResponseData;
  }

  public EnterpriseResponseData getErrorString(KnoxVpnContext knoxVpnContext, String str) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    Log.d("KnoxVpnEngineService", "getErrorString API called for profileName = " + str);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData((Object) null);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      return enterpriseResponseData;
    }
    try {
    } catch (Exception e) {
      if (DBG) {
        Log.e(
            "KnoxVpnEngineService",
            "getting vpn error string : Failure at " + Log.getStackTraceString(e));
      }
    }
    if (this.mKnoxVpnApiValidation.getErrorStringValidation(knoxVpnContext, str) == null) {
      Log.d(
          "KnoxVpnEngineService",
          "getting error string for the profile failed: Error occured while validating the"
              + " profile");
      ArrayList dataByFields =
          this.mVpnStorageProvider.getDataByFields(
              "vpnConnectionFail",
              new String[] {"profileName"},
              new String[] {str},
              new String[] {
                "profileName", "adminUid", "errorType", "vendorName", "containerID", "packageList"
              });
      if (dataByFields != null
          && dataByFields.size() > 0
          && ((ContentValues) dataByFields.get(0)).getAsInteger("adminUid").intValue()
              == checkCallingUidPermission.mCallerUid) {
        Log.d("KnoxVpnEngineService", "Error occured while try to recreate the profile");
        enterpriseResponseData.setData(toJSONObject((ContentValues) dataByFields.get(0)));
        enterpriseResponseData.setStatus(2, -1);
        try {
          this.mVpnStorageProvider.deleteDataByFields(
              "vpnConnectionFail", new String[] {"profileName"}, new String[] {str});
        } catch (Exception e2) {
          Log.e(
              "KnoxVpnEngineService",
              "Exception at removeProfileByEnterpriseVpnConnectionFailTable "
                  + Log.getStackTraceString(e2));
        }
      }
      return enterpriseResponseData;
    }
    IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
    if (binderInterfaceForProfile == null) {
      Log.d(
          "KnoxVpnEngineService",
          "getting error string for the profile failed: Error occured since the service is not"
              + " started");
      return enterpriseResponseData;
    }
    enterpriseResponseData.setData(binderInterfaceForProfile.getErrorString(str));
    enterpriseResponseData.setStatus(0, 0);
    return enterpriseResponseData;
  }

  public final EnterpriseResponseData addPackages(
      KnoxVpnContext knoxVpnContext, String[] strArr, String str) {
    IPackageManager iPackageManager;
    int i;
    String[] strArr2 = strArr;
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(2);
    enterpriseResponseData.setStatus(1, -1);
    if (knoxVpnContext == null) {
      return enterpriseResponseData;
    }
    int i2 = knoxVpnContext.personaId;
    ArrayList arrayList = new ArrayList();
    String ipChainNameForProfile = this.mKnoxVpnHelper.getIpChainNameForProfile(str);
    Log.d(
        "KnoxVpnEngineService",
        "addPackages : profileName value is " + str + "personaId value is" + i2);
    try {
      int addPackagesToVpnValidation =
          this.mKnoxVpnApiValidation.addPackagesToVpnValidation(knoxVpnContext, strArr2, str);
      if (addPackagesToVpnValidation != 100) {
        enterpriseResponseData.setData(Integer.valueOf(addPackagesToVpnValidation));
        Log.d(
            "KnoxVpnEngineService",
            "Error occured while adding packages to vpn(addPackagesToVpnValidation): The error code"
                + " is "
                + addPackagesToVpnValidation);
        return enterpriseResponseData;
      }
      IPackageManager asInterface =
          IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
      StringBuilder sb = new StringBuilder();
      String personifiedName = this.mKnoxVpnHelper.getPersonifiedName(i2, "ADD_ALL_PACKAGES");
      int length = strArr2.length;
      int i3 = 0;
      while (i3 < length) {
        String str2 = strArr2[i3];
        int uIDForPackage = this.mKnoxVpnHelper.getUIDForPackage(i2, str2);
        if (uIDForPackage > 0) {
          i = length;
          String profileOwningTheUid = this.mKnoxVpnHelper.getProfileOwningTheUid(uIDForPackage);
          if (profileOwningTheUid != null && profileOwningTheUid.equalsIgnoreCase(str)) {
            Log.d(
                "KnoxVpnEngineService",
                "addPackages:uid already added to the profile, skipping it "
                    + uIDForPackage
                    + str2);
          } else {
            String profileOwningThePackage =
                this.mKnoxVpnHelper.getProfileOwningThePackage(personifiedName);
            if (profileOwningThePackage != null
                && profileOwningThePackage.equalsIgnoreCase(str)
                && i2 == UserHandle.getUserId(uIDForPackage)) {
              Log.d(
                  "KnoxVpnEngineService",
                  "addPackages:userId for the appId already added to the profile, skipping it "
                      + uIDForPackage);
            } else if (asInterface == null) {
              iPackageManager = asInterface;
            } else if (asInterface.checkUidPermission("android.permission.INTERNET", uIDForPackage)
                != 0) {
              Log.d(
                  "KnoxVpnEngineService",
                  "addPackages:uid is not granted internet permission " + uIDForPackage + str2);
              writePackagestoPermissionCheckDb(str, str2, i2, uIDForPackage);
            } else {
              StringBuilder sb2 = new StringBuilder();
              iPackageManager = asInterface;
              sb2.append("addPackages:uid is granted internet permission ");
              sb2.append(uIDForPackage);
              sb2.append(str2);
              Log.d("KnoxVpnEngineService", sb2.toString());
              arrayList.add(Integer.valueOf(uIDForPackage));
            }
          }
          iPackageManager = asInterface;
          i3++;
          strArr2 = strArr;
          length = i;
          asInterface = iPackageManager;
        } else {
          iPackageManager = asInterface;
          i = length;
        }
        int writePackageToDB = writePackageToDB(str, str2, uIDForPackage, i2);
        if (writePackageToDB == -1) {
          Log.d(
              "KnoxVpnEngineService",
              "Error occured while adding packages to vpn(writePackageToDB): The error code is "
                  + writePackageToDB);
          enterpriseResponseData.setData(1);
          if (sb.length() > 0) {
            AuditLog.logAsUser(
                5,
                5,
                true,
                Process.myPid(),
                "KnoxVpnEngineService",
                String.format(
                    "%s added to vpn for profile %s",
                    sb.subSequence(0, sb.length() - 2).toString(), str),
                knoxVpnContext.personaId);
          }
          AuditLog.logAsUser(
              3,
              5,
              false,
              Process.myPid(),
              "KnoxVpnEngineService",
              String.format(
                  "Error occurred while adding package %s to vpn for profile %s", str2, str),
              knoxVpnContext.personaId);
          return enterpriseResponseData;
        }
        sb.append(str2);
        sb.append(", ");
        i3++;
        strArr2 = strArr;
        length = i;
        asInterface = iPackageManager;
      }
      this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
      if (sb.length() > 0) {
        AuditLog.logAsUser(
            5,
            5,
            true,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format(
                "%s added to vpn for profile %s",
                sb.subSequence(0, sb.length() - 2).toString(), str),
            knoxVpnContext.personaId);
      }
      this.mFirewallHelper.addRulesForFilteredPackages(
          knoxVpnContext.getVendorName(),
          ipChainNameForProfile,
          arrayList,
          this.mKnoxVpnHelper.getDefaultNetworkInterface(str));
      refreshDomainInHashMap(str);
      updateNotification(str, i2, true);
      Log.d(
          "KnoxVpnEngineService",
          "Error occured while adding packages to vpn: The error code is 0");
      enterpriseResponseData.setData(0);
      enterpriseResponseData.setStatus(0, 0);
      return enterpriseResponseData;
    } catch (Exception e) {
      Log.d(
          "KnoxVpnEngineService",
          "Error occured while adding packages to vpn: exception occured: The error code is -1"
              + Log.getStackTraceString(e));
      enterpriseResponseData.setData(-1);
      enterpriseResponseData.setStatus(1, 2);
      return enterpriseResponseData;
    }
  }

  public final void writePackagestoPermissionCheckDb(String str, String str2, int i, int i2) {
    try {
      String personifiedName = this.mKnoxVpnHelper.getPersonifiedName(i, str2);
      ArrayList dataByFields =
          this.mVpnStorageProvider.getDataByFields(
              "vpnNoInternetPermission",
              new String[] {"packageUid"},
              new String[] {Integer.toString(i2)},
              new String[] {"packageUid"});
      if (dataByFields != null && dataByFields.size() > 0) {
        Log.e(
            "KnoxVpnEngineService",
            "Error adding the package to permission check db since it is already added");
        return;
      }
      ContentValues contentValues = new ContentValues();
      contentValues.put("profileName", str);
      contentValues.put("packageName", personifiedName);
      contentValues.put("packageUid", Integer.valueOf(i2));
      if (this.mVpnStorageProvider.putDataByFields(
          "vpnNoInternetPermission", null, null, contentValues)) {
        return;
      }
      Log.e(
          "KnoxVpnEngineService", "Error adding the package info to permission exempt list in db");
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception at writePackagestoPermissionCheckDb API " + Log.getStackTraceString(e));
    }
  }

  public final void removePackagesFromPermissionCheckDb(int i) {
    try {
      this.mVpnStorageProvider.deleteDataByFields(
          "vpnNoInternetPermission",
          new String[] {"packageUid"},
          new String[] {Integer.toString(i)});
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception at removePackagestoPermissionCheckDb API " + Log.getStackTraceString(e));
    }
  }

  public final int writePackageToDB(String str, String str2, int i, int i2) {
    int i3;
    boolean z = DBG;
    if (z) {
      Log.d(
          "KnoxVpnEngineService",
          "write package DB : profileName = " + str + " :packageName = " + str2 + " :cid = " + i2);
    }
    try {
      String personifiedName = this.mKnoxVpnHelper.getPersonifiedName(i2, str2);
      if (z) {
        Log.d("KnoxVpnEngineService", "write package DB : Transformed UID: " + i);
      }
      if (z) {
        Log.d(
            "KnoxVpnEngineService",
            "write package DB : Transformed packagename before adding to db : " + personifiedName);
      }
      ContentValues contentValues = new ContentValues();
      contentValues.put("packageName", personifiedName);
      contentValues.put("profileName", str);
      contentValues.put("packageUid", Integer.valueOf(i));
      contentValues.put("packageCid", Integer.valueOf(i2));
      if (!this.mVpnStorageProvider.putDataByFields("VpnPackageInfo", null, null, contentValues)) {
        return 126;
      }
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry != null) {
        profileEntry.addPackageEntry(personifiedName, i, i2);
      }
      if (i != -1) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(i));
        i3 = startVpnForPerApplication(str, arrayList, true);
      } else {
        i3 = 0;
      }
      if (z) {
        printProfileVpnMap();
      }
      return i3;
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "write package DB : Exception occured for adding vpn, package map."
              + Log.getStackTraceString(e));
      return -1;
    }
  }

  public final int writeAddAllPackageToDB(KnoxVpnContext knoxVpnContext, String str, String str2) {
    if (DBG) {
      Log.d(
          "KnoxVpnEngineService",
          "writeAddAllPackageToDB:profileName = " + str2 + " :packageName = " + str);
    }
    int i = knoxVpnContext.adminId;
    int i2 = knoxVpnContext.personaId;
    try {
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str2);
      ContentValues contentValues = new ContentValues();
      contentValues.put("packageName", str);
      contentValues.put("profileName", str2);
      contentValues.put("packageUid", (Integer) (-2));
      contentValues.put("packageCid", Integer.valueOf(i2));
      boolean putDataByFields =
          this.mVpnStorageProvider.putDataByFields("VpnPackageInfo", null, null, contentValues);
      Log.d("KnoxVpnEngineService", "writeAddAllPackageToDB: status value is" + putDataByFields);
      if (!putDataByFields) {
        return -1;
      }
      if (profileEntry == null) {
        return 0;
      }
      profileEntry.addPackageEntry(str, -2, i2);
      return 0;
    } catch (Exception e) {
      if (DBG) {
        Log.e(
            "KnoxVpnEngineService",
            "Exception at writeAddAllPackageToDB API" + Log.getStackTraceString(e));
      }
      return -1;
    }
  }

  public final int removeAddAllPackageInfofromDatabase(String str, int i) {
    int i2 = -1;
    try {
      String personifiedName = this.mKnoxVpnHelper.getPersonifiedName(i, "ADD_ALL_PACKAGES");
      if (DBG) {
        Log.d(
            "KnoxVpnEngineService",
            "removeAddAllPackageInfofromDatabase: removePackage value is " + personifiedName);
      }
      if (this.mVpnStorageProvider.deleteDataByFields(
          "VpnPackageInfo",
          new String[] {"packageName", "profileName"},
          new String[] {personifiedName, str})) {
        i2 = 0;
      }
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception at removeAddAllPackageInfofromDatabase API " + Log.getStackTraceString(e));
    }
    if (DBG) {
      Log.d("KnoxVpnEngineService", "removeAddAllPackageInfofromDatabase: status value is " + i2);
    }
    return i2;
  }

  public final boolean removePackagesFromDatabase(String str, String str2) {
    try {
      return this.mVpnStorageProvider.deleteDataByFields(
          "VpnPackageInfo", new String[] {"packageName", "profileName"}, new String[] {str2, str});
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception at removePackagesFromDatabase API " + Log.getStackTraceString(e));
      return false;
    }
  }

  public synchronized EnterpriseResponseData removePackagesFromVpn(
      KnoxVpnContext knoxVpnContext, String[] strArr, String str) {
    String str2;
    int i;
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    Log.d("KnoxVpnEngineService", "package is going to be removed from vpn for the profile " + str);
    int personaId = knoxVpnContext.getPersonaId();
    String personifiedName = this.mKnoxVpnHelper.getPersonifiedName(personaId, "ADD_ALL_PACKAGES");
    StringBuilder sb = new StringBuilder();
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(2);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      enterpriseResponseData.setData(140);
      return enterpriseResponseData;
    }
    try {
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry == null) {
        enterpriseResponseData.setData(108);
        Log.d(
            "KnoxVpnEngineService",
            "Error while removing the packages from vpn: The error code is 108");
        return enterpriseResponseData;
      }
      if (profileEntry.getActivateState() == 1 && this.mKnoxVpnHelper.isWideVpnExists(personaId)) {
        Log.d(
            "KnoxVpnEngineService",
            "removePackagesFromVpn: removing previously added rules before updating");
        this.mFirewallHelper.removeMiscRulesRange(
            personaId, profileEntry.getInterfaceName(), getVirtualInterfaceType(str));
      }
      int removePackagesFromVpnValidation =
          this.mKnoxVpnApiValidation.removePackagesFromVpnValidation(knoxVpnContext, strArr, str);
      if (removePackagesFromVpnValidation != 100) {
        enterpriseResponseData.setData(Integer.valueOf(removePackagesFromVpnValidation));
        Log.d(
            "KnoxVpnEngineService",
            "Error occured while removing packages from vpn: The error code is "
                + removePackagesFromVpnValidation);
        return enterpriseResponseData;
      }
      if (profileEntry.getActivateState() == 1 && this.mKnoxVpnHelper.isWideVpnExists(personaId)) {
        Log.d("KnoxVpnEngineService", "removePackagesFromVpn: adding rules for newly added rules");
        this.mFirewallHelper.addMiscRulesRange(
            personaId, profileEntry.getInterfaceName(), getVirtualInterfaceType(str));
      }
      ArrayList arrayList = new ArrayList();
      ArrayList arrayList2 = new ArrayList();
      int length = strArr.length;
      int i2 = 0;
      while (i2 < length) {
        String str3 = strArr[i2];
        if (!str3.equalsIgnoreCase(personifiedName)) {
          String personifiedName2 = this.mKnoxVpnHelper.getPersonifiedName(personaId, str3);
          ArrayList arrayList3 = null;
          String[] strArr2 = null;
          if (removePackagesFromDatabase(str, personifiedName2)) {
            str2 = personifiedName;
            i = length;
          } else {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
              strArr2 = getSharedUidPackges(new String[] {str3}, personaId);
            } catch (Exception unused) {
            } catch (Throwable th) {
              Binder.restoreCallingIdentity(clearCallingIdentity);
              throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            String[] strArr3 = strArr2;
            if (strArr3 != null && strArr3.length > 0) {
              ArrayList arrayList4 = new ArrayList();
              str2 = personifiedName;
              int length2 = strArr3.length;
              i = length;
              int i3 = 0;
              while (i3 < length2) {
                int i4 = length2;
                String[] strArr4 = strArr3;
                String personifiedName3 =
                    this.mKnoxVpnHelper.getPersonifiedName(personaId, strArr3[i3]);
                if (removePackagesFromDatabase(str, personifiedName3)) {
                  arrayList4.add(personifiedName3);
                }
                i3++;
                length2 = i4;
                strArr3 = strArr4;
              }
              arrayList3 = arrayList4;
            }
          }
          if (arrayList3 != null && arrayList3.size() > 0) {
            Iterator it = arrayList3.iterator();
            while (it.hasNext()) {
              String str4 = (String) it.next();
              VpnPackageInfo vpnPackageInfo = profileEntry.getPackage(str4);
              if (vpnPackageInfo != null && vpnPackageInfo.getCid() == personaId) {
                arrayList2.add(Integer.valueOf(vpnPackageInfo.getUid()));
                if (profileEntry.getActivateState() == 0) {
                  profileEntry.removePackageEntry(str4);
                  sb.append(str4);
                  sb.append(", ");
                } else {
                  int uid = vpnPackageInfo.getUid();
                  arrayList.add(Integer.valueOf(uid));
                  removePackagesFromPermissionCheckDb(uid);
                  unsetDnsSystemProperty(str, uid, uid);
                  updateProxyRules(2, str, getKnoxVpnHelperInstance().updateProxyList(uid, false));
                  profileEntry.removePackageEntry(str4);
                  sb.append(str4);
                  sb.append(", ");
                }
              }
            }
            i2++;
            personifiedName = str2;
            length = i;
          }
          VpnPackageInfo vpnPackageInfo2 = profileEntry.getPackage(personifiedName2);
          if (vpnPackageInfo2 != null && vpnPackageInfo2.getCid() == personaId) {
            arrayList2.add(Integer.valueOf(vpnPackageInfo2.getUid()));
            if (profileEntry.getActivateState() == 0) {
              profileEntry.removePackageEntry(personifiedName2);
              sb.append(personifiedName2);
              sb.append(", ");
            } else {
              int uid2 = vpnPackageInfo2.getUid();
              arrayList.add(Integer.valueOf(uid2));
              removePackagesFromPermissionCheckDb(uid2);
              unsetDnsSystemProperty(str, uid2, uid2);
              updateProxyRules(2, str, getKnoxVpnHelperInstance().updateProxyList(uid2, false));
              profileEntry.removePackageEntry(personifiedName2);
              sb.append(personifiedName2);
              sb.append(", ");
            }
          }
          i2++;
          personifiedName = str2;
          length = i;
        }
        str2 = personifiedName;
        i = length;
        i2++;
        personifiedName = str2;
        length = i;
      }
      this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
      this.mFirewallHelper.removeMiscRules(
          arrayList, profileEntry.getInterfaceName(), getVirtualInterfaceType(str));
      this.mFirewallHelper.removeRulesForFilteredPackages(
          profileEntry.getVendorPkgName(), profileEntry.getIpChainName(), arrayList2);
      refreshDomainInHashMap(str);
      updateNotification(str, personaId, false);
      int packageCount = profileEntry.getPackageCount();
      boolean z =
          profileEntry.getVpnConnectionType() == 1
              && !this.mProcessManager.processRunCheck(profileEntry);
      if ((packageCount <= 0 || z) && profileEntry.getActivateState() == 1) {
        try {
          IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
          if (binderInterfaceForProfile != null) {
            int stopConnection = binderInterfaceForProfile.stopConnection(str);
            if (stopConnection != 0) {
              AuditLog.logAsUser(
                  3,
                  5,
                  false,
                  Process.myPid(),
                  "KnoxVpnEngineService",
                  String.format(
                      "Error stopping connection for vpn profile %s. Vendor service might not be"
                          + " running",
                      str),
                  knoxVpnContext.personaId);
            } else {
              AuditLog.logAsUser(
                  5,
                  5,
                  true,
                  Process.myPid(),
                  "KnoxVpnEngineService",
                  String.format(
                      "Connection with vpn vendor service stopped for profile %s after removing all"
                          + " the packages",
                      str),
                  knoxVpnContext.personaId);
            }
            Log.d(
                "KnoxVpnEngineService",
                "stopping the vpn connection status for on-demand configuration after removing all"
                    + " the packages "
                    + stopConnection);
          }
        } catch (Exception e) {
          AuditLog.logAsUser(
              3,
              5,
              false,
              Process.myPid(),
              "KnoxVpnEngineService",
              String.format(
                  "Exception stopping connection for profile %s after removing packages from vpn",
                  str),
              knoxVpnContext.personaId);
          Log.d(
              "KnoxVpnEngineService",
              "stopping the vpn connection failed for on-demand configuration after removing all"
                  + " the packages "
                  + Log.getStackTraceString(e));
        }
      }
      Log.d("KnoxVpnEngineService", "removing packages from vpn is a success: The error code is 0");
      enterpriseResponseData.setData(0);
      enterpriseResponseData.setStatus(0, 0);
      try {
        if (((Integer) enterpriseResponseData.getData()).intValue() == 0) {
          KnoxAnalyticsData knoxAnalyticsData =
              new KnoxAnalyticsData("KNOX_VPN", 1, "API:removePackagesFromVpn");
          this.mData = knoxAnalyticsData;
          setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
          Arrays.sort(strArr);
          StringBuffer stringBuffer = new StringBuffer(strArr[0]);
          for (int i5 = 1; i5 < strArr.length; i5++) {
            stringBuffer.append("," + strArr[i5]);
          }
          this.mData.setProperty("pkgLst", stringBuffer.toString());
          KnoxAnalytics.log(this.mData);
        }
      } catch (Exception e2) {
        Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e2));
      }
      if (sb.length() > 0) {
        AuditLog.logAsUser(
            5,
            5,
            true,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format(
                "%s removed from vpn for profile %s",
                sb.subSequence(0, sb.length() - 2).toString(), str),
            knoxVpnContext.personaId);
      }
      return enterpriseResponseData;
    } catch (Exception e3) {
      Log.d(
          "KnoxVpnEngineService",
          "Error occured while removing packages from vpn: exception occured: The error code is -1"
              + Log.getStackTraceString(e3));
      enterpriseResponseData.setData(-1);
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Exception while removing packages from vpn for profile %s", str),
          knoxVpnContext.personaId);
      return enterpriseResponseData;
    }
  }

  public final EnterpriseResponseData getAllRangedPackages(
      KnoxVpnContext knoxVpnContext, String str) {
    if (DBG) {
      Log.d("KnoxVpnEngineService", "get all ranged packages : Profile : " + str);
    }
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setStatus(1, -1);
    enterpriseResponseData.setData((Object) null);
    try {
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry != null) {
        for (VpnPackageInfo vpnPackageInfo : profileEntry.getPackageList()) {
          if (this.mKnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)) {
            int containerIdFromPackageName =
                this.mKnoxVpnHelper.getContainerIdFromPackageName(vpnPackageInfo.getPackageName());
            if (DBG) {
              Log.d(
                  "KnoxVpnEngineService",
                  "get all ranged packages : containerId " + containerIdFromPackageName);
            }
            if (containerIdFromPackageName == knoxVpnContext.personaId) {
              enterpriseResponseData.setData(
                  this.mKnoxVpnHelper.getUserPackageListForProfile(
                      str, containerIdFromPackageName));
              enterpriseResponseData.setStatus(0, 0);
              return enterpriseResponseData;
            }
          }
        }
      }
    } catch (Exception e) {
      Log.d(
          "KnoxVpnEngineService",
          "Error occured while fetching all packages of a profile: exception occured: The error"
              + " code is -1"
              + Log.getStackTraceString(e));
    }
    return enterpriseResponseData;
  }

  public final EnterpriseResponseData getAllPackages(KnoxVpnContext knoxVpnContext, String str) {
    boolean z = DBG;
    if (z) {
      Log.d("KnoxVpnEngineService", "get all packages : Profile : " + str);
    }
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setStatus(1, -1);
    enterpriseResponseData.setData((Object) null);
    try {
      int personaId = knoxVpnContext.getPersonaId();
      ArrayList arrayList = new ArrayList();
      ArrayList dataByFields =
          this.mVpnStorageProvider.getDataByFields(
              "VpnPackageInfo",
              new String[] {"profileName", "packageCid"},
              new String[] {str, Integer.toString(personaId)},
              new String[] {"packageUid", "packageName"});
      if (z) {
        printProfileVpnMap();
      }
      if (dataByFields != null && dataByFields.size() > 0) {
        Iterator it = dataByFields.iterator();
        while (it.hasNext()) {
          ContentValues contentValues = (ContentValues) it.next();
          String asString = contentValues.getAsString("packageName");
          Log.d("KnoxVpnEngineService", "getAllPackages_personifiedPackageName::" + asString);
          int intValue = contentValues.getAsInteger("packageUid").intValue();
          Log.d("KnoxVpnEngineService", "getAllPackages_uid::" + intValue);
          if (!asString.equalsIgnoreCase(
              this.mKnoxVpnHelper.getPersonifiedName(personaId, "ADD_ALL_PACKAGES"))) {
            if (intValue == -1) {
              arrayList.add(this.mKnoxVpnHelper.getRegularPackageName(asString));
            } else {
              long clearCallingIdentity = Binder.clearCallingIdentity();
              String[] packagesForUid =
                  this.mContext.getPackageManager().getPackagesForUid(intValue);
              Log.d(
                  "KnoxVpnEngineService",
                  "getAllPackages_packagesforUid[]::"
                      + this.mContext.getPackageManager().getPackagesForUid(intValue)
                      + " uid::"
                      + intValue);
              Binder.restoreCallingIdentity(clearCallingIdentity);
              if (packagesForUid != null) {
                for (String str2 : packagesForUid) {
                  arrayList.add(str2);
                }
              }
            }
          }
        }
      }
      Iterator it2 = getPackageListNoInternetPermission(str, personaId).iterator();
      while (it2.hasNext()) {
        arrayList.add((String) it2.next());
      }
      enterpriseResponseData.setData((String[]) arrayList.toArray(new String[0]));
      enterpriseResponseData.setStatus(0, 0);
    } catch (Exception e) {
      Log.d(
          "KnoxVpnEngineService",
          "get all packages : Exceptionin notify: " + Log.getStackTraceString(e));
    }
    return enterpriseResponseData;
  }

  public final String[] getSharedUidPackges(String[] strArr, int i) {
    IPackageManager asInterface;
    if (strArr == null
        || strArr.length <= 0
        || (asInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package")))
            == null) {
      return null;
    }
    List installedPackagesAsUser =
        this.mContext.getPackageManager().getInstalledPackagesAsUser(0, i);
    HashSet hashSet = new HashSet();
    for (String str : strArr) {
      Set _getSharedUidPackges = _getSharedUidPackges(str, i, asInterface, installedPackagesAsUser);
      if (_getSharedUidPackges != null) {
        hashSet.addAll(_getSharedUidPackges);
      }
    }
    return (String[]) hashSet.toArray(new String[0]);
  }

  public final Set _getSharedUidPackges(
      String str, int i, IPackageManager iPackageManager, List list) {
    if (str != null) {
      HashSet hashSet = new HashSet();
      hashSet.add(str);
      try {
        ApplicationInfo applicationInfo = iPackageManager.getApplicationInfo(str, 0L, i);
        if (applicationInfo == null) {
          return hashSet;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
          PackageInfo packageInfo = (PackageInfo) it.next();
          if (packageInfo.applicationInfo.uid == applicationInfo.uid
              && !packageInfo.packageName.equals(str)) {
            hashSet.add(packageInfo.packageName);
          }
        }
        return hashSet;
      } catch (Exception e) {
        Log.e("KnoxVpnEngineService", "_getSharedUidPackges: Exception", e);
      }
    }
    return null;
  }

  public final List getPackageListNoInternetPermission(String str, int i) {
    ArrayList arrayList = new ArrayList();
    try {
      ArrayList dataByFields =
          this.mVpnStorageProvider.getDataByFields(
              "vpnNoInternetPermission",
              new String[] {"profileName"},
              new String[] {str},
              new String[] {"packageUid"});
      if (dataByFields != null && dataByFields.size() > 0) {
        Iterator it = dataByFields.iterator();
        while (it.hasNext()) {
          int intValue = ((ContentValues) it.next()).getAsInteger("packageUid").intValue();
          if (intValue != -1 && UserHandle.getUserId(intValue) == i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(intValue);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            for (String str2 : packagesForUid) {
              arrayList.add(str2);
            }
          }
        }
      }
    } catch (Exception unused) {
    }
    return arrayList;
  }

  public final synchronized EnterpriseResponseData addAllPackages(
      KnoxVpnContext knoxVpnContext, String str) {
    VpnProfileInfo profileEntry;
    Log.d(
        "KnoxVpnEngineService",
        "addAllPackages : vpnContext.personaId value is "
            + knoxVpnContext.personaId
            + " profileName value is "
            + str);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(-1);
    enterpriseResponseData.setStatus(1, -1);
    try {
      profileEntry = this.vpnConfig.getProfileEntry(str);
    } catch (Exception e) {
      Log.d(
          "KnoxVpnEngineService",
          "Error while adding all the packages to vpn: exception occured: The error code is -1"
              + Log.getStackTraceString(e));
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Exception while adding all the packages to vpn for profile %s", str),
          knoxVpnContext.personaId);
    }
    if (profileEntry == null) {
      Log.d(
          "KnoxVpnEngineService",
          "Error while adding all the packages to vpn: The error code is -1");
      return enterpriseResponseData;
    }
    if (writeAddAllPackageToDB(
            knoxVpnContext,
            this.mKnoxVpnHelper.getPersonifiedName(knoxVpnContext.personaId, "ADD_ALL_PACKAGES"),
            str)
        == -1) {
      enterpriseResponseData.setData(126);
      Log.d(
          "KnoxVpnEngineService",
          "Error while adding all the packages to vpn: The error code is 126");
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Error while adding all packages to vpn for profile %s", str),
          knoxVpnContext.personaId);
      return enterpriseResponseData;
    }
    if (profileEntry.getActivateState() == 1) {
      if (knoxVpnContext.personaId == 0) {
        blockIncomingICMPPackets(true);
        this.mKnoxVpnHelper.getActiveNetworkInterface();
        for (Integer num : KnoxVpnConstants.AID_EXEMPT_LIST) {
          this.mFirewallHelper.addExemptRulesForUid(num.intValue());
        }
      }
      getOemNetdService().knoxVpnBlockUserWideDnsQuery(true, knoxVpnContext.personaId);
    }
    String defaultNetworkInterface = this.mKnoxVpnHelper.getDefaultNetworkInterface(str);
    refreshDomainInHashMap(str);
    int vPNTransitionState = getVPNTransitionState(str);
    if (vPNTransitionState != -1 && vPNTransitionState != 1) {
      if (vPNTransitionState == 2) {
        this.mFirewallHelper.addMiscRulesRange(
            knoxVpnContext.personaId, null, getVirtualInterfaceType(str));
        this.mFirewallHelper.addRangeRulesForFilteredPackages(
            profileEntry.getVendorPkgName(),
            profileEntry.getIpChainName(),
            knoxVpnContext.personaId,
            defaultNetworkInterface);
        updateProxyRules(
            3, str, getKnoxVpnHelperInstance().updateProxyList(knoxVpnContext.personaId, true));
      } else if (vPNTransitionState == 3) {
        this.mFirewallHelper.addMiscRulesRange(
            knoxVpnContext.personaId, null, getVirtualInterfaceType(str));
        this.mFirewallHelper.addRangeRulesForFilteredPackages(
            profileEntry.getVendorPkgName(),
            profileEntry.getIpChainName(),
            knoxVpnContext.personaId,
            defaultNetworkInterface);
      } else if (vPNTransitionState == 4) {
        String interfaceName = profileEntry.getInterfaceName();
        int netId = profileEntry.getNetId();
        Log.d(
            "KnoxVpnEngineService",
            "addAllPackages : profileName = " + str + "interfaceValue = " + interfaceName);
        if (interfaceName != null) {
          this.mFirewallHelper.addMiscRulesRange(
              knoxVpnContext.personaId, interfaceName, getVirtualInterfaceType(str));
          this.mFirewallHelper.addRangeRulesForFilteredPackages(
              profileEntry.getVendorPkgName(),
              profileEntry.getIpChainName(),
              knoxVpnContext.personaId,
              defaultNetworkInterface);
          setDnsSystemProperty(
              str,
              this.mKnoxVpnHelper.startUid(knoxVpnContext.personaId),
              this.mKnoxVpnHelper.stopUid(knoxVpnContext.personaId),
              interfaceName,
              netId);
          updateProxyRules(
              3, str, getKnoxVpnHelperInstance().updateProxyList(knoxVpnContext.personaId, true));
        }
      } else if (vPNTransitionState != 5) {
        this.mFirewallHelper.addRangeRulesForFilteredPackages(
            profileEntry.getVendorPkgName(),
            profileEntry.getIpChainName(),
            knoxVpnContext.personaId,
            defaultNetworkInterface);
        Log.e("KnoxVpnEngineService", "addAllPackages : VPN State not valid");
      }
      updateNotification(str, knoxVpnContext.personaId, true);
      this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
      Log.d(
          "KnoxVpnEngineService",
          "success while adding all the packages to vpn: The error code is 0");
      enterpriseResponseData.setData(0);
      enterpriseResponseData.setStatus(0, 0);
      AuditLog.logAsUser(
          5,
          5,
          true,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Success while adding all the packages to vpn for profile %s", str),
          knoxVpnContext.personaId);
      return enterpriseResponseData;
    }
    this.mFirewallHelper.addMiscRulesRange(
        knoxVpnContext.personaId, null, getVirtualInterfaceType(str));
    this.mFirewallHelper.addRangeRulesForFilteredPackages(
        profileEntry.getVendorPkgName(),
        profileEntry.getIpChainName(),
        knoxVpnContext.personaId,
        defaultNetworkInterface);
    updateProxyRules(
        3, str, getKnoxVpnHelperInstance().updateProxyList(knoxVpnContext.personaId, true));
    startVpnProfile(str);
    Log.d("KnoxVpnEngineService", "addAllPackages : start connection called. profileName = " + str);
    updateNotification(str, knoxVpnContext.personaId, true);
    this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
    Log.d(
        "KnoxVpnEngineService",
        "success while adding all the packages to vpn: The error code is 0");
    enterpriseResponseData.setData(0);
    enterpriseResponseData.setStatus(0, 0);
    AuditLog.logAsUser(
        5,
        5,
        true,
        Process.myPid(),
        "KnoxVpnEngineService",
        String.format("Success while adding all the packages to vpn for profile %s", str),
        knoxVpnContext.personaId);
    return enterpriseResponseData;
  }

  /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:79:0x02ca -> B:75:0x02df). Please report as a decompilation issue!!! */
  public final synchronized EnterpriseResponseData removeAllPackages(
      KnoxVpnContext knoxVpnContext, String str) {
    VpnProfileInfo profileEntry;
    Log.d(
        "KnoxVpnEngineService",
        "removeAllPackages : vpnContext.personaId value is "
            + knoxVpnContext.personaId
            + " profileName value is "
            + str);
    int i = knoxVpnContext.personaId;
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(-1);
    enterpriseResponseData.setStatus(1, -1);
    try {
      profileEntry = this.vpnConfig.getProfileEntry(str);
    } catch (Exception e) {
      Log.d(
          "KnoxVpnEngineService",
          "removeAllPackages failed: exception occured: The error code is -1"
              + Log.getStackTraceString(e));
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Exception while removing all packages from vpn for profile %s", str),
          knoxVpnContext.personaId);
    }
    if (profileEntry == null) {
      enterpriseResponseData.setData(108);
      Log.d(
          "KnoxVpnEngineService",
          "Error while removing all the packages from vpn: The error code is 108");
      return enterpriseResponseData;
    }
    String personifiedName = this.mKnoxVpnHelper.getPersonifiedName(i, "ADD_ALL_PACKAGES");
    Iterator it = profileEntry.getPackageList().iterator();
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    while (it.hasNext()) {
      VpnPackageInfo vpnPackageInfo = (VpnPackageInfo) it.next();
      if (vpnPackageInfo.getCid() == i) {
        String packageName = vpnPackageInfo.getPackageName();
        this.mKnoxVpnHelper.getRegularPackageName(packageName);
        int uid = vpnPackageInfo.getUid();
        if (packageName.equalsIgnoreCase(personifiedName)) {
          removeAddAllPackageInfofromDatabase(str, i);
          if (profileEntry.getActivateState() == 0) {
            this.mFirewallHelper.removeRangeRulesForFilteredPackages(
                profileEntry.getVendorPkgName(), profileEntry.getIpChainName(), i);
            it.remove();
          } else {
            if (i == 0) {
              blockIncomingICMPPackets(false);
              for (Integer num : KnoxVpnConstants.AID_EXEMPT_LIST) {
                this.mFirewallHelper.removeExemptRulesForUid(num.intValue());
              }
            }
            getOemNetdService().knoxVpnBlockUserWideDnsQuery(false, i);
            this.mFirewallHelper.removeMiscRulesRange(
                i, profileEntry.getInterfaceName(), getVirtualInterfaceType(str));
            this.mFirewallHelper.removeRangeRulesForFilteredPackages(
                profileEntry.getVendorPkgName(), profileEntry.getIpChainName(), i);
            unsetDnsSystemProperty(
                str, this.mKnoxVpnHelper.startUid(i), this.mKnoxVpnHelper.stopUid(i));
            updateProxyRules(2, str, getKnoxVpnHelperInstance().updateProxyList(i, true));
            it.remove();
          }
        } else {
          boolean removePackagesFromDatabase = removePackagesFromDatabase(str, packageName);
          Log.d(
              "KnoxVpnEngineService",
              "removeAllPackage is called, removing per-app packets from db "
                  + removePackagesFromDatabase);
          if (removePackagesFromDatabase) {
            arrayList2.add(Integer.valueOf(uid));
            if (profileEntry.getActivateState() == 0) {
              it.remove();
            } else {
              arrayList.add(Integer.valueOf(uid));
              unsetDnsSystemProperty(str, uid, uid);
              updateProxyRules(2, str, getKnoxVpnHelperInstance().updateProxyList(uid, false));
              it.remove();
            }
          }
        }
      }
    }
    this.mKnoxVpnHelper.removePackagesFromPermissionDb(str, i);
    if (!arrayList.isEmpty()) {
      this.mFirewallHelper.removeMiscRules(
          arrayList, profileEntry.getInterfaceName(), getVirtualInterfaceType(str));
    }
    if (!arrayList2.isEmpty()) {
      this.mFirewallHelper.removeRulesForFilteredPackages(
          profileEntry.getVendorPkgName(), profileEntry.getIpChainName(), arrayList2);
    }
    Iterator it2 = this.mKnoxVpnHelper.getuserIdForExemptedUids().iterator();
    while (it2.hasNext()) {
      int intValue = ((Integer) it2.next()).intValue();
      if (intValue == i) {
        this.mFirewallHelper.removeExemptRulesForDownloadManagerUid(
            this.mKnoxVpnHelper.getUIDForPackage(intValue, "com.android.providers.downloads"));
      }
    }
    Iterator it3 = profileEntry.getExemptPackageList().iterator();
    while (it3.hasNext()) {
      int intValue2 = ((Integer) it3.next()).intValue();
      if (UserHandle.getUserId(intValue2) == i) {
        boolean removeExemptedListToDatabase =
            this.mKnoxVpnHelper.removeExemptedListToDatabase(str, intValue2);
        Log.d(
            "KnoxVpnEngineService",
            "removeAllPackages: removing from exempeted list " + removeExemptedListToDatabase);
        if (removeExemptedListToDatabase) {
          it3.remove();
          updateRulesToExemptUid(str, 0, 1, intValue2, 0, null, null);
          updateRulesToExemptUid(
              str,
              0,
              2,
              intValue2,
              profileEntry.getInterfaceType(),
              profileEntry.getInterfaceName(),
              profileEntry.getDefaultInterface());
          updateRulesToExemptUid(
              str,
              0,
              3,
              intValue2,
              profileEntry.getInterfaceType(),
              profileEntry.getInterfaceName(),
              profileEntry.getDefaultInterface());
        }
      }
    }
    Iterator it4 = this.mKnoxVpnHelper.getUninsalledAppsFromExemptedList(i, str).iterator();
    while (it4.hasNext()) {
      this.mKnoxVpnHelper.removeExemptedListToDatabase(str, (String) it4.next());
    }
    IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
    if (binderInterfaceForProfile == null) {
      enterpriseResponseData.setData(110);
      Log.d(
          "KnoxVpnEngineService",
          "Error while removing all the packages from vpn: The error code is110");
      return enterpriseResponseData;
    }
    int packageCount = profileEntry.getPackageCount();
    profileEntry.getVpnConnectionType();
    if (packageCount <= 0 && profileEntry.getActivateState() == 1) {
      if (DBG) {
        Log.d(
            "KnoxVpnEngineService",
            "removeAllPackages: The profile is going to be stopped since no packages are present "
                + str);
      }
      try {
        if (binderInterfaceForProfile.stopConnection(str) != 0) {
          AuditLog.logAsUser(
              3,
              5,
              false,
              Process.myPid(),
              "KnoxVpnEngineService",
              String.format("Error occurred trying to stop vpn connection from profile %s", str),
              knoxVpnContext.personaId);
        } else {
          AuditLog.logAsUser(
              5,
              5,
              true,
              Process.myPid(),
              "KnoxVpnEngineService",
              String.format("Connection with vpn vendor service stopped for profile %s", str),
              knoxVpnContext.personaId);
        }
      } catch (Exception unused) {
        AuditLog.logAsUser(
            3,
            5,
            false,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format(
                "Exception stopping connection for profile %s after removing all packages", str),
            knoxVpnContext.personaId);
      }
    }
    this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
    refreshDomainInHashMap(str);
    updateNotification(str, i, false);
    Log.d("KnoxVpnEngineService", "removeAllPackages success: The error code is 0");
    enterpriseResponseData.setData(0);
    enterpriseResponseData.setStatus(0, 0);
    AuditLog.logAsUser(
        5,
        5,
        true,
        Process.myPid(),
        "KnoxVpnEngineService",
        String.format("Success while removing all packages from vpn for profile %s", str),
        knoxVpnContext.personaId);
    return enterpriseResponseData;
  }

  public synchronized EnterpriseResponseData addContainerPackagesToVpn(
      KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    Log.d(
        "KnoxVpnEngineService",
        "addContainerPackagesToVpn : containerId value is " + i + " profileName value is " + str);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(-1);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      enterpriseResponseData.setData(140);
      return enterpriseResponseData;
    }
    int addContainerPackagesToVpnValidation =
        this.mKnoxVpnApiValidation.addContainerPackagesToVpnValidation(
            knoxVpnContext, i, strArr, str);
    if (addContainerPackagesToVpnValidation != 100) {
      enterpriseResponseData.setData(Integer.valueOf(addContainerPackagesToVpnValidation));
      Log.d(
          "KnoxVpnEngineService",
          "Error occured while adding container packages to vpn: The error code is "
              + addContainerPackagesToVpnValidation);
      if (addContainerPackagesToVpnValidation == 113) {
        enterpriseResponseData.setStatus(1, 11);
      }
      return enterpriseResponseData;
    }
    knoxVpnContext.personaId = i;
    EnterpriseResponseData addPackages = addPackages(knoxVpnContext, strArr, str);
    try {
      if (((Integer) addPackages.getData()).intValue() == 0) {
        KnoxAnalyticsData knoxAnalyticsData =
            new KnoxAnalyticsData("KNOX_VPN", 1, "API:addContainerPackagesToVpn");
        this.mData = knoxAnalyticsData;
        setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
        Arrays.sort(strArr);
        StringBuffer stringBuffer = new StringBuffer(strArr[0]);
        for (int i2 = 1; i2 < strArr.length; i2++) {
          stringBuffer.append("," + strArr[i2]);
        }
        this.mData.setProperty("pkgLst", stringBuffer.toString());
        int i3 = knoxVpnContext.personaId;
        if (i3 != 0) {
          this.mData.setProperty("cid", i3);
        }
        KnoxAnalytics.log(this.mData);
      }
    } catch (Exception e) {
      Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e));
    }
    return addPackages;
  }

  public synchronized EnterpriseResponseData addPackagesToVpn(
      KnoxVpnContext knoxVpnContext, String[] strArr, String str) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData((Object) null);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      return enterpriseResponseData;
    }
    EnterpriseResponseData addPackages = addPackages(knoxVpnContext, strArr, str);
    try {
      if (((Integer) addPackages.getData()).intValue() == 0) {
        KnoxAnalyticsData knoxAnalyticsData =
            new KnoxAnalyticsData("KNOX_VPN", 1, "API:addPackagesToVpn");
        this.mData = knoxAnalyticsData;
        setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
        Arrays.sort(strArr);
        StringBuffer stringBuffer = new StringBuffer(strArr[0]);
        for (int i = 1; i < strArr.length; i++) {
          stringBuffer.append("," + strArr[i]);
        }
        this.mData.setProperty("pkgLst", stringBuffer.toString());
        int i2 = knoxVpnContext.personaId;
        if (i2 != 0) {
          this.mData.setProperty("cid", i2);
        }
        KnoxAnalytics.log(this.mData);
      }
    } catch (Exception e) {
      Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e));
    }
    return addPackages;
  }

  public synchronized EnterpriseResponseData removeContainerPackagesFromVpn(
      KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str) {
    String str2;
    int i2;
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    Log.d(
        "KnoxVpnEngineService",
        "removeContainerPackagesFromVpn : containerId value is "
            + i
            + " profileName value is "
            + str);
    String personifiedName = this.mKnoxVpnHelper.getPersonifiedName(i, "ADD_ALL_PACKAGES");
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(-1);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      enterpriseResponseData.setData(140);
      return enterpriseResponseData;
    }
    try {
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry == null) {
        enterpriseResponseData.setData(108);
        Log.d(
            "KnoxVpnEngineService",
            "Error while removing the container packages from vpn: The error code is 108");
        return enterpriseResponseData;
      }
      if (profileEntry.getActivateState() == 1 && this.mKnoxVpnHelper.isWideVpnExists(i)) {
        Log.d(
            "KnoxVpnEngineService",
            "removeContainerPackagesFromVpn: removing previously added rules before updating");
        this.mFirewallHelper.removeMiscRulesRange(
            i, profileEntry.getInterfaceName(), getVirtualInterfaceType(str));
      }
      int removeContainerPackagesFromVpnValidation =
          this.mKnoxVpnApiValidation.removeContainerPackagesFromVpnValidation(
              knoxVpnContext, i, strArr, str);
      if (removeContainerPackagesFromVpnValidation != 100) {
        enterpriseResponseData.setData(Integer.valueOf(removeContainerPackagesFromVpnValidation));
        Log.d(
            "KnoxVpnEngineService",
            "Error occured while removing the container packages from vpn: The error code is "
                + removeContainerPackagesFromVpnValidation);
        if (removeContainerPackagesFromVpnValidation == 113) {
          enterpriseResponseData.setStatus(1, 11);
        }
        return enterpriseResponseData;
      }
      if (profileEntry.getActivateState() == 1 && this.mKnoxVpnHelper.isWideVpnExists(i)) {
        Log.d(
            "KnoxVpnEngineService",
            "removeContainerPackagesFromVpn: adding rules for newly added rules");
        this.mFirewallHelper.addMiscRulesRange(
            i, profileEntry.getInterfaceName(), getVirtualInterfaceType(str));
      }
      ArrayList arrayList = new ArrayList();
      ArrayList arrayList2 = new ArrayList();
      int i3 = 0;
      for (int length = strArr.length; i3 < length; length = i2) {
        String str3 = strArr[i3];
        if (!str3.equalsIgnoreCase(personifiedName)) {
          String personifiedName2 = this.mKnoxVpnHelper.getPersonifiedName(i, str3);
          ArrayList arrayList3 = null;
          String[] strArr2 = null;
          if (removePackagesFromDatabase(str, personifiedName2)) {
            str2 = personifiedName;
            i2 = length;
          } else {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
              strArr2 = getSharedUidPackges(new String[] {str3}, i);
            } catch (Exception unused) {
            } catch (Throwable th) {
              Binder.restoreCallingIdentity(clearCallingIdentity);
              throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            String[] strArr3 = strArr2;
            if (strArr3 != null && strArr3.length > 0) {
              ArrayList arrayList4 = new ArrayList();
              str2 = personifiedName;
              int length2 = strArr3.length;
              i2 = length;
              int i4 = 0;
              while (i4 < length2) {
                int i5 = length2;
                String[] strArr4 = strArr3;
                String personifiedName3 = this.mKnoxVpnHelper.getPersonifiedName(i, strArr3[i4]);
                if (removePackagesFromDatabase(str, personifiedName3)) {
                  arrayList4.add(personifiedName3);
                }
                i4++;
                length2 = i5;
                strArr3 = strArr4;
              }
              arrayList3 = arrayList4;
            }
          }
          if (arrayList3 != null && arrayList3.size() > 0) {
            Iterator it = arrayList3.iterator();
            while (it.hasNext()) {
              String str4 = (String) it.next();
              VpnPackageInfo vpnPackageInfo = profileEntry.getPackage(str4);
              if (vpnPackageInfo != null && vpnPackageInfo.getCid() == i) {
                arrayList2.add(Integer.valueOf(vpnPackageInfo.getUid()));
                if (profileEntry.getActivateState() == 0) {
                  profileEntry.removePackageEntry(str4);
                } else {
                  int uid = vpnPackageInfo.getUid();
                  arrayList.add(Integer.valueOf(uid));
                  removePackagesFromPermissionCheckDb(uid);
                  unsetDnsSystemProperty(str, uid, uid);
                  updateProxyRules(2, str, getKnoxVpnHelperInstance().updateProxyList(uid, false));
                  profileEntry.removePackageEntry(str4);
                }
              }
            }
            i3++;
            personifiedName = str2;
          }
          VpnPackageInfo vpnPackageInfo2 = profileEntry.getPackage(personifiedName2);
          if (vpnPackageInfo2 != null && vpnPackageInfo2.getCid() == i) {
            arrayList2.add(Integer.valueOf(vpnPackageInfo2.getUid()));
            if (profileEntry.getActivateState() == 0) {
              profileEntry.removePackageEntry(str3);
            } else {
              int uid2 = vpnPackageInfo2.getUid();
              arrayList.add(Integer.valueOf(uid2));
              removePackagesFromPermissionCheckDb(uid2);
              unsetDnsSystemProperty(str, uid2, uid2);
              updateProxyRules(2, str, getKnoxVpnHelperInstance().updateProxyList(uid2, false));
              profileEntry.removePackageEntry(personifiedName2);
            }
          }
          i3++;
          personifiedName = str2;
        }
        str2 = personifiedName;
        i2 = length;
        i3++;
        personifiedName = str2;
      }
      this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
      this.mFirewallHelper.removeMiscRules(
          arrayList, profileEntry.getInterfaceName(), getVirtualInterfaceType(str));
      this.mFirewallHelper.removeRulesForFilteredPackages(
          profileEntry.getVendorPkgName(), profileEntry.getIpChainName(), arrayList2);
      refreshDomainInHashMap(str);
      updateNotification(str, i, false);
      int packageCount = profileEntry.getPackageCount();
      boolean z =
          profileEntry.getVpnConnectionType() == 1
              && !this.mProcessManager.processRunCheck(profileEntry);
      if ((packageCount <= 0 || z) && profileEntry.getActivateState() == 1) {
        try {
          IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
          if (binderInterfaceForProfile != null) {
            int stopConnection = binderInterfaceForProfile.stopConnection(str);
            if (stopConnection != 0) {
              AuditLog.logAsUser(
                  3,
                  5,
                  false,
                  Process.myPid(),
                  "KnoxVpnEngineService",
                  String.format(
                      "Error occurred trying to stop vpn connection from profile %s", str),
                  knoxVpnContext.personaId);
            } else {
              AuditLog.logAsUser(
                  5,
                  5,
                  true,
                  Process.myPid(),
                  "KnoxVpnEngineService",
                  String.format("Connection with vpn vendor service stopped for profile %s", str),
                  knoxVpnContext.personaId);
            }
            Log.d(
                "KnoxVpnEngineService",
                "stopping the vpn connection status for on-demand configuration after removing all"
                    + " the container packages "
                    + stopConnection);
          }
        } catch (Exception e) {
          AuditLog.logAsUser(
              3,
              5,
              false,
              Process.myPid(),
              "KnoxVpnEngineService",
              String.format(
                  "Exception stopping connection for profile %s after removing all container"
                      + " packages",
                  str),
              knoxVpnContext.personaId);
          Log.d(
              "KnoxVpnEngineService",
              "stopping the vpn connection failed for on-demand configuration after removing all"
                  + " the container packages "
                  + Log.getStackTraceString(e));
        }
      }
      Log.d(
          "KnoxVpnEngineService",
          "removing all container packages from vpn is a success: The error code is 0");
      enterpriseResponseData.setData(0);
      enterpriseResponseData.setStatus(0, 0);
      AuditLog.logAsUser(
          5,
          5,
          true,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Success while removing all the packages from vpn for profile %s", str),
          knoxVpnContext.personaId);
      try {
        if (((Integer) enterpriseResponseData.getData()).intValue() == 0) {
          KnoxAnalyticsData knoxAnalyticsData =
              new KnoxAnalyticsData("KNOX_VPN", 1, "API:removeContainerPackagesFromVpn");
          this.mData = knoxAnalyticsData;
          setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
          Arrays.sort(strArr);
          StringBuffer stringBuffer = new StringBuffer(strArr[0]);
          for (int i6 = 1; i6 < strArr.length; i6++) {
            stringBuffer.append("," + strArr[i6]);
          }
          this.mData.setProperty("pkgLst", stringBuffer.toString());
          this.mData.setProperty("cid", i);
          KnoxAnalytics.log(this.mData);
        }
      } catch (Exception e2) {
        Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e2));
      }
      return enterpriseResponseData;
    } catch (Exception e3) {
      Log.d(
          "KnoxVpnEngineService",
          "Error occured while removing all container packages from vpn: exception occured: The"
              + " error code is -1"
              + Log.getStackTraceString(e3));
      enterpriseResponseData.setData(-1);
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format(
              "Exception while removing all container packages from vpn for profile %s", str),
          knoxVpnContext.personaId);
      return enterpriseResponseData;
    }
  }

  public EnterpriseResponseData getAllContainerPackagesInVpnProfile(
      KnoxVpnContext knoxVpnContext, int i, String str) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    Log.d(
        "KnoxVpnEngineService",
        "getAllContainerPackagesInVpnProfile : containerId value is "
            + i
            + " profileName value is "
            + str);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData((Object) null);
    boolean z = true;
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
            knoxVpnContext, checkCallingUidPermission.mCallerUid)
        || this.mKnoxVpnApiValidation.getAllContainerPackagesInVpnProfileValidation(
                knoxVpnContext, i, str)
            == null) {
      return enterpriseResponseData;
    }
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    boolean z2 = false;
    if (profileEntry != null) {
      Iterator it = profileEntry.getPackageList().iterator();
      while (true) {
        if (!it.hasNext()) {
          z = false;
          break;
        }
        VpnPackageInfo vpnPackageInfo = (VpnPackageInfo) it.next();
        int cid = vpnPackageInfo.getCid();
        if (this.mKnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo) && i == cid) {
          break;
        }
      }
      z2 = z;
    } else {
      Log.d(
          "KnoxVpnEngineService",
          "getAllContainerPackagesInVpnProfile: profile hashmap is  " + profileEntry);
    }
    if (z2) {
      knoxVpnContext.personaId = i;
      return getAllRangedPackages(knoxVpnContext, str);
    }
    knoxVpnContext.personaId = i;
    return getAllPackages(knoxVpnContext, str);
  }

  public EnterpriseResponseData getAllPackagesInVpnProfile(
      KnoxVpnContext knoxVpnContext, String str) {
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData((Object) null);
    boolean z = true;
    enterpriseResponseData.setStatus(1, -1);
    if (knoxVpnContext == null
        || !updateIfNonLegacyUserAndCheckIfVendorAllowed(
            knoxVpnContext, checkCallingUidPermission(knoxVpnContext).mCallerUid)) {
      return enterpriseResponseData;
    }
    Log.d(
        "KnoxVpnEngineService",
        "getAllPackagesInVpnProfile: profileName value is "
            + str
            + "vpnContext.personaId value is "
            + knoxVpnContext.personaId);
    if (this.mKnoxVpnApiValidation.getAllPackagesInVpnProfileValidation(knoxVpnContext, str)
        == null) {
      return enterpriseResponseData;
    }
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry == null) {
      Log.d(
          "KnoxVpnEngineService",
          "Error occured while getting all packages in vpn profile: The error code is 108");
      return enterpriseResponseData;
    }
    Iterator it = profileEntry.getPackageList().iterator();
    while (true) {
      if (!it.hasNext()) {
        z = false;
        break;
      }
      VpnPackageInfo vpnPackageInfo = (VpnPackageInfo) it.next();
      int cid = vpnPackageInfo.getCid();
      if (this.mKnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)
          && knoxVpnContext.personaId == cid) {
        break;
      }
    }
    if (z) {
      return getAllRangedPackages(knoxVpnContext, str);
    }
    return getAllPackages(knoxVpnContext, str);
  }

  public EnterpriseResponseData addAllContainerPackagesToVpn(
      KnoxVpnContext knoxVpnContext, int i, String str) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    Log.d(
        "KnoxVpnEngineService",
        "addAllContainerPackagesToVpn : containerId value is "
            + i
            + " profileName value is "
            + str);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(-1);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      enterpriseResponseData.setData(140);
      return enterpriseResponseData;
    }
    int addAllContainerPackagesToVpnValidation =
        this.mKnoxVpnApiValidation.addAllContainerPackagesToVpnValidation(knoxVpnContext, i, str);
    if (addAllContainerPackagesToVpnValidation != 100) {
      enterpriseResponseData.setData(Integer.valueOf(addAllContainerPackagesToVpnValidation));
      Log.d(
          "KnoxVpnEngineService",
          "addAllContainerPackagesToVpn failed: The error code is "
              + addAllContainerPackagesToVpnValidation);
      if (addAllContainerPackagesToVpnValidation == 113) {
        enterpriseResponseData.setStatus(1, 11);
      }
      return enterpriseResponseData;
    }
    knoxVpnContext.personaId = i;
    EnterpriseResponseData addAllPackages = addAllPackages(knoxVpnContext, str);
    try {
      if (((Integer) addAllPackages.getData()).intValue() == 0) {
        KnoxAnalyticsData knoxAnalyticsData =
            new KnoxAnalyticsData("KNOX_VPN", 1, "API:addAllContainerPackagesToVpn");
        this.mData = knoxAnalyticsData;
        setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
        int i2 = knoxVpnContext.personaId;
        if (i2 != 0) {
          this.mData.setProperty("cid", i2);
        }
        KnoxAnalytics.log(this.mData);
      }
    } catch (Exception e) {
      Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e));
    }
    return addAllPackages;
  }

  public EnterpriseResponseData addAllPackagesToVpn(KnoxVpnContext knoxVpnContext, String str) {
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(-1);
    enterpriseResponseData.setStatus(1, -1);
    if (knoxVpnContext == null) {
      return enterpriseResponseData;
    }
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission(knoxVpnContext).mCallerUid)) {
      enterpriseResponseData.setData(140);
      return enterpriseResponseData;
    }
    Log.d(
        "KnoxVpnEngineService",
        "addAllPackagesToVpn : vpnContext.personaId value is "
            + knoxVpnContext.personaId
            + " profileName value is "
            + str);
    int addAllPackagesToVpnValidation =
        this.mKnoxVpnApiValidation.addAllPackagesToVpnValidation(knoxVpnContext, str);
    if (addAllPackagesToVpnValidation != 100) {
      enterpriseResponseData.setData(Integer.valueOf(addAllPackagesToVpnValidation));
      Log.d(
          "KnoxVpnEngineService",
          "Adding all the packages to vpn failed: The error code is "
              + addAllPackagesToVpnValidation);
      return enterpriseResponseData;
    }
    EnterpriseResponseData addAllPackages = addAllPackages(knoxVpnContext, str);
    try {
      if (((Integer) addAllPackages.getData()).intValue() == 0) {
        KnoxAnalyticsData knoxAnalyticsData =
            new KnoxAnalyticsData("KNOX_VPN", 1, "API:addAllPackagesToVpn");
        this.mData = knoxAnalyticsData;
        setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
        int i = knoxVpnContext.personaId;
        if (i != 0) {
          this.mData.setProperty("cid", i);
        }
        KnoxAnalytics.log(this.mData);
      }
    } catch (Exception e) {
      Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e));
    }
    return addAllPackages;
  }

  public EnterpriseResponseData removeAllContainerPackagesFromVpn(
      KnoxVpnContext knoxVpnContext, int i, String str) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    Log.d(
        "KnoxVpnEngineService",
        "removeAllContainerPackagesFromVpn : containerId value is "
            + i
            + " profileName value is "
            + str);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(-1);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      enterpriseResponseData.setData(140);
      return enterpriseResponseData;
    }
    int removeAllContainerPackagesFromVpnValidation =
        this.mKnoxVpnApiValidation.removeAllContainerPackagesFromVpnValidation(
            knoxVpnContext, i, str);
    if (removeAllContainerPackagesFromVpnValidation != 100) {
      enterpriseResponseData.setData(Integer.valueOf(removeAllContainerPackagesFromVpnValidation));
      Log.d(
          "KnoxVpnEngineService",
          "removing all the container packages from vpn failed: The error code is "
              + removeAllContainerPackagesFromVpnValidation);
      if (removeAllContainerPackagesFromVpnValidation == 113) {
        enterpriseResponseData.setStatus(1, 11);
      }
      return enterpriseResponseData;
    }
    knoxVpnContext.personaId = i;
    EnterpriseResponseData removeAllPackages = removeAllPackages(knoxVpnContext, str);
    try {
      if (((Integer) removeAllPackages.getData()).intValue() == 0) {
        KnoxAnalyticsData knoxAnalyticsData =
            new KnoxAnalyticsData("KNOX_VPN", 1, "API:removeAllContainerPackagesFromVpn");
        this.mData = knoxAnalyticsData;
        setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
        int i2 = knoxVpnContext.personaId;
        if (i2 != 0) {
          this.mData.setProperty("cid", i2);
        }
        KnoxAnalytics.log(this.mData);
      }
    } catch (Exception e) {
      Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e));
    }
    return removeAllPackages;
  }

  public synchronized EnterpriseResponseData removeAllPackagesFromVpn(
      KnoxVpnContext knoxVpnContext, String str) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    if (DBG) {
      Log.d(
          "KnoxVpnEngineService",
          "removeAllPackagesFromVpn: vpnContext.personaId value is "
              + knoxVpnContext.personaId
              + "profileName value is "
              + str);
    }
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(-1);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      enterpriseResponseData.setData(140);
      return enterpriseResponseData;
    }
    int removeAllPackagesFromVpnValidation =
        this.mKnoxVpnApiValidation.removeAllPackagesFromVpnValidation(knoxVpnContext, str);
    if (removeAllPackagesFromVpnValidation != 100) {
      enterpriseResponseData.setData(Integer.valueOf(removeAllPackagesFromVpnValidation));
      Log.d(
          "KnoxVpnEngineService",
          "removing all the packages from vpn failed: The error code is "
              + removeAllPackagesFromVpnValidation);
      return enterpriseResponseData;
    }
    EnterpriseResponseData removeAllPackages = removeAllPackages(knoxVpnContext, str);
    try {
      if (((Integer) removeAllPackages.getData()).intValue() == 0) {
        KnoxAnalyticsData knoxAnalyticsData =
            new KnoxAnalyticsData("KNOX_VPN", 1, "API:removeAllPackagesFromVpn");
        this.mData = knoxAnalyticsData;
        setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
        int i = knoxVpnContext.personaId;
        if (i != 0) {
          this.mData.setProperty("cid", i);
        }
        KnoxAnalytics.log(this.mData);
      }
    } catch (Exception e) {
      Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e));
    }
    return removeAllPackages;
  }

  public synchronized EnterpriseResponseData setVpnModeOfOperation(
      KnoxVpnContext knoxVpnContext, String str, int i) {
    int vpnModeOfOperationValidation;
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(-1);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      enterpriseResponseData.setData(140);
      return enterpriseResponseData;
    }
    try {
      vpnModeOfOperationValidation =
          this.mKnoxVpnApiValidation.setVpnModeOfOperationValidation(knoxVpnContext, str, i);
    } catch (Exception e) {
      if (DBG) {
        Log.e(
            "KnoxVpnEngineService", "setting vpn mode : Failure at " + Log.getStackTraceString(e));
      }
    }
    if (vpnModeOfOperationValidation != 100) {
      enterpriseResponseData.setData(Integer.valueOf(vpnModeOfOperationValidation));
      Log.d(
          "KnoxVpnEngineService",
          "setting the mode of operation for the profile has failed:The error code is "
              + vpnModeOfOperationValidation);
      return enterpriseResponseData;
    }
    IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
    if (binderInterfaceForProfile == null) {
      enterpriseResponseData.setData(110);
      Log.d(
          "KnoxVpnEngineService",
          "setting the mode of operation for the profile has failed: The error code is 110");
      return enterpriseResponseData;
    }
    int vpnModeOfOperation = binderInterfaceForProfile.setVpnModeOfOperation(str, i);
    Log.d(
        "KnoxVpnEngineService",
        "setVpnModeOfOperation : profileName = " + str + " :setMode = " + vpnModeOfOperation);
    enterpriseResponseData.setData(Integer.valueOf(vpnModeOfOperation));
    enterpriseResponseData.setStatus(0, 0);
    try {
      if (((Integer) enterpriseResponseData.getData()).intValue() == 0) {
        KnoxAnalyticsData knoxAnalyticsData =
            new KnoxAnalyticsData("KNOX_VPN", 1, "API:setVpnModeOfOperation");
        this.mData = knoxAnalyticsData;
        setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
        this.mData.setProperty("vpnMd", i);
        KnoxAnalytics.log(this.mData);
      }
    } catch (Exception e2) {
      Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e2));
    }
    return enterpriseResponseData;
  }

  public synchronized EnterpriseResponseData getVpnModeOfOperation(
      KnoxVpnContext knoxVpnContext, String str) {
    int vpnModeOfOperationValidation;
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(-1);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      enterpriseResponseData.setData(140);
      return enterpriseResponseData;
    }
    try {
      vpnModeOfOperationValidation =
          this.mKnoxVpnApiValidation.getVpnModeOfOperationValidation(knoxVpnContext, str);
    } catch (Exception e) {
      if (DBG) {
        Log.e(
            "KnoxVpnEngineService", "getting vpn mode : Failure at " + Log.getStackTraceString(e));
      }
    }
    if (vpnModeOfOperationValidation != 100) {
      enterpriseResponseData.setData(Integer.valueOf(vpnModeOfOperationValidation));
      Log.d(
          "KnoxVpnEngineService",
          "getting the mode of operation for the profile has failed:The error code is "
              + vpnModeOfOperationValidation);
      return enterpriseResponseData;
    }
    IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
    if (binderInterfaceForProfile == null) {
      enterpriseResponseData.setData(110);
      Log.d(
          "KnoxVpnEngineService",
          "getting the mode of operation for the profile has failed: The error code is 110");
      return enterpriseResponseData;
    }
    int vpnModeOfOperation = binderInterfaceForProfile.getVpnModeOfOperation(str);
    Log.d(
        "KnoxVpnEngineService",
        "getVpnModeOfOperation : profileName = " + str + " :currentMode = " + vpnModeOfOperation);
    enterpriseResponseData.setData(Integer.valueOf(vpnModeOfOperation));
    enterpriseResponseData.setStatus(0, 0);
    return enterpriseResponseData;
  }

  public EnterpriseResponseData setServerCertValidationUserAcceptanceCriteria(
      KnoxVpnContext knoxVpnContext, String str, boolean z, List list, int i) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(Boolean.FALSE);
    enterpriseResponseData.setStatus(1, -1);
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
      return enterpriseResponseData;
    }
    if (str != null) {
      try {
        if (validateAdminAndVendorForProfile(knoxVpnContext, str, enterpriseResponseData) != 0) {
          return enterpriseResponseData;
        }
        IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
        if (binderInterfaceForProfile == null) {
          Log.e("KnoxVpnEngineService", "setting server cert validation : VPN Service not started");
          return enterpriseResponseData;
        }
        enterpriseResponseData.setData(
            Boolean.valueOf(
                binderInterfaceForProfile.setServerCertValidationUserAcceptanceCriteria(
                    str, z, list, i)));
        enterpriseResponseData.setStatus(0, 0);
      } catch (Exception e) {
        Log.e(
            "KnoxVpnEngineService",
            "setting server cert validation : Failure at " + Log.getStackTraceString(e));
        return null;
      }
    }
    try {
      if (((Boolean) enterpriseResponseData.getData()).booleanValue()) {
        KnoxAnalyticsData knoxAnalyticsData =
            new KnoxAnalyticsData(
                "KNOX_VPN", 1, "API:setServerCertValidationUserAcceptanceCriteria");
        this.mData = knoxAnalyticsData;
        setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
        this.mData.setProperty("enb", z);
        StringBuffer stringBuffer = new StringBuffer(String.valueOf(list.get(0)));
        for (int i2 = 1; i2 < list.size(); i2++) {
          stringBuffer.append("," + String.valueOf(list.get(i2)));
        }
        this.mData.setProperty("cndt", stringBuffer.toString());
        this.mData.setProperty("fq", i);
        KnoxAnalytics.log(this.mData);
      }
    } catch (Exception e2) {
      Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e2));
    }
    return enterpriseResponseData;
  }

  public EnterpriseResponseData setAutoRetryOnConnectionError(
      KnoxVpnContext knoxVpnContext, String str, boolean z) {
    EnterpriseResponseData enterpriseResponseData = new EnterpriseResponseData();
    enterpriseResponseData.setData(Boolean.FALSE);
    enterpriseResponseData.setStatus(1, -1);
    if (knoxVpnContext == null) {
      if (DBG) {
        Log.e("KnoxVpnEngineService", "setAutoRetryOnConnectionError : vpnContext is null");
      }
      return enterpriseResponseData;
    }
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission(knoxVpnContext).mCallerUid)) {
      return enterpriseResponseData;
    }
    if (str != null) {
      try {
        if (validateAdminAndVendorForProfile(knoxVpnContext, str, enterpriseResponseData) != 0) {
          return enterpriseResponseData;
        }
        IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
        if (binderInterfaceForProfile == null) {
          Log.e("KnoxVpnEngineService", "setAutoRetryOnConnectionError : VPN Service not started");
          return enterpriseResponseData;
        }
        boolean autoRetryOnConnectionError =
            binderInterfaceForProfile.setAutoRetryOnConnectionError(str, z);
        Log.d(
            "KnoxVpnEngineService",
            "setAutoRetryOnConnectionError : success = " + autoRetryOnConnectionError);
        enterpriseResponseData.setData(Boolean.valueOf(autoRetryOnConnectionError));
        enterpriseResponseData.setStatus(0, 0);
      } catch (Exception e) {
        Log.e(
            "KnoxVpnEngineService",
            "setAutoRetryOnConnectionError : Failure at " + Log.getStackTraceString(e));
        return null;
      }
    }
    try {
      if (((Boolean) enterpriseResponseData.getData()).booleanValue()) {
        KnoxAnalyticsData knoxAnalyticsData =
            new KnoxAnalyticsData("KNOX_VPN", 1, "API:setAutoRetryOnConnectionError");
        this.mData = knoxAnalyticsData;
        setCommonProperties(knoxAnalyticsData, knoxVpnContext, str, -1);
        this.mData.setProperty("enb", z);
        KnoxAnalytics.log(this.mData);
      }
    } catch (Exception e2) {
      Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e2));
    }
    return enterpriseResponseData;
  }

  public synchronized int allowNoAuthUsbTetheringOverVpn(
      KnoxVpnContext knoxVpnContext, String str) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    try {
      if (!getEnterpriseDeviceManagerService().isCallerValidKPU(checkCallingUidPermission)) {
        return 141;
      }
      if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
          knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
        return 140;
      }
      int allowUsbTetheringValidation =
          this.mKnoxVpnApiValidation.allowUsbTetheringValidation(knoxVpnContext, str, null);
      if (allowUsbTetheringValidation != 100) {
        return allowUsbTetheringValidation;
      }
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry == null) {
        return 108;
      }
      try {
      } catch (Exception unused) {
        allowUsbTetheringValidation = 101;
      }
      if (profileEntry.getUsbTethering() == 1) {
        return allowUsbTetheringValidation;
      }
      this.mKnoxVpnHelper.updateUsbTetheringForProfileInDb(str, 1);
      profileEntry.setUsbTethering(1);
      this.mKnoxVpnHelper.registerNetdTetherEventListener(true);
      this.mKnoxVpnHelper.enableKnoxVpnFlagForTether(true);
      String interfaceNameForUsbtethering = this.mKnoxVpnHelper.getInterfaceNameForUsbtethering();
      String interfaceName = profileEntry.getInterfaceName();
      int activateState = profileEntry.getActivateState();
      if (interfaceNameForUsbtethering != null) {
        if (interfaceName == null && activateState == 1) {
          Log.d(
              "KnoxVpnEngineService",
              "Applying rules to drop tether packets after enableUsbTethering API is called and"
                  + " before vpn comes up");
          this.mFirewallHelper.addRulesForDroppingTetherPackets(interfaceNameForUsbtethering);
        } else if (interfaceName != null && activateState == 1) {
          this.mFirewallHelper.addRulesForUsbTethering(
              interfaceName,
              getVirtualInterfaceType(str),
              getVpnManagerService().getDnsServerListForInterface(interfaceName),
              interfaceNameForUsbtethering,
              this.mKnoxVpnHelper.getNetworkPartWithMask(interfaceNameForUsbtethering));
        }
      }
      return allowUsbTetheringValidation;
    } catch (RemoteException unused2) {
      return 141;
    } catch (SecurityException unused3) {
      return 141;
    }
  }

  public synchronized int allowAuthUsbTetheringOverVpn(
      KnoxVpnContext knoxVpnContext, String str, Bundle bundle) {
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission(knoxVpnContext).mCallerUid)) {
      return 140;
    }
    int allowUsbTetheringValidation =
        this.mKnoxVpnApiValidation.allowUsbTetheringValidation(knoxVpnContext, str, bundle);
    if (allowUsbTetheringValidation != 100) {
      return allowUsbTetheringValidation;
    }
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry == null) {
      return 108;
    }
    try {
    } catch (Exception unused) {
      allowUsbTetheringValidation = 101;
    }
    if (profileEntry.getUsbTethering() == 1) {
      return allowUsbTetheringValidation;
    }
    this.mKnoxVpnHelper.updateUsbTetheringForProfileInDb(str, 1);
    profileEntry.setUsbTethering(1);
    this.mKnoxVpnHelper.registerNetdTetherEventListener(true);
    this.mKnoxVpnHelper.enableKnoxVpnFlagForTether(true);
    profileEntry.setUsbTetherAuth(1);
    this.mKnoxVpnHelper.updateUsbTetherAuthDetails(str, bundle, true);
    this.mKnoxVpnHelper.addOrRemoveAppsFromBatteryOptimization(
        str, "com.samsung.knox.vpn.tether.auth", true);
    this.mKnoxVpnTetherAuthentication.bindTetherAuthService(
        str, knoxVpnContext.getPersonaId(), bundle);
    String interfaceNameForUsbtethering = this.mKnoxVpnHelper.getInterfaceNameForUsbtethering();
    String interfaceName = profileEntry.getInterfaceName();
    int activateState = profileEntry.getActivateState();
    if (interfaceNameForUsbtethering != null) {
      if (interfaceName == null && activateState == 1) {
        Log.d(
            "KnoxVpnTetherAuthentication",
            "Applying rules to drop tether packets after allowAuthUsbTetheringOverVpn API is called"
                + " and before vpn comes up");
        this.mFirewallHelper.addRulesForDroppingTetherPackets(interfaceNameForUsbtethering);
      } else if (interfaceName != null && activateState == 1) {
        Log.d(
            "KnoxVpnTetherAuthentication",
            "start tether auth process after allowAuthUsbTetheringOverVpn API is called");
        this.mKnoxVpnTetherAuthentication.startTetherAuthProcess(
            profileEntry.getPersonaId(),
            interfaceNameForUsbtethering,
            this.mKnoxVpnHelper.getIpAddressForUsbTetheringInterface());
      }
    }
    return allowUsbTetheringValidation;
  }

  public synchronized int disallowUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str) {
    ContextInfo checkCallingUidPermission = checkCallingUidPermission(knoxVpnContext);
    try {
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry != null) {
        if (profileEntry.getUsbTethering() == 0) {
          return 100;
        }
        if (profileEntry.getUsbtetherAuth() == 0) {
          if (!getEnterpriseDeviceManagerService().isCallerValidKPU(checkCallingUidPermission)) {
            return 141;
          }
        }
      }
      if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
          knoxVpnContext, checkCallingUidPermission.mCallerUid)) {
        return 140;
      }
      int disallowUsbTetheringValidation =
          this.mKnoxVpnApiValidation.disallowUsbTetheringValidation(knoxVpnContext, str);
      if (disallowUsbTetheringValidation != 100) {
        return disallowUsbTetheringValidation;
      }
      VpnProfileInfo profileEntry2 = this.vpnConfig.getProfileEntry(str);
      if (profileEntry2 == null) {
        return 108;
      }
      try {
        this.mKnoxVpnHelper.updateUsbTetheringForProfileInDb(str, 0);
        profileEntry2.setUsbTethering(0);
        this.mKnoxVpnHelper.registerNetdTetherEventListener(false);
        this.mKnoxVpnHelper.enableKnoxVpnFlagForTether(false);
        this.mFirewallHelper.removeRulesForUsbTethering(
            profileEntry2.getInterfaceType(), profileEntry2.getInterfaceName());
        if (profileEntry2.getUsbtetherAuth() == 1) {
          this.mKnoxVpnHelper.updateUsbTetherAuthDetails(str, null, false);
          profileEntry2.setUsbTetherAuth(0);
          this.mKnoxVpnTetherAuthentication.unbindTetherAuthService(true);
          this.mKnoxVpnHelper.addOrRemoveAppsFromBatteryOptimization(
              str, "com.samsung.knox.vpn.tether.auth", false);
        }
      } catch (Exception unused) {
        disallowUsbTetheringValidation = 101;
      }
      return disallowUsbTetheringValidation;
    } catch (RemoteException unused2) {
      return 141;
    } catch (SecurityException unused3) {
      return 141;
    }
  }

  public synchronized int isUsbTetheringOverVpnEnabled(KnoxVpnContext knoxVpnContext, String str) {
    int i;
    if (!updateIfNonLegacyUserAndCheckIfVendorAllowed(
        knoxVpnContext, checkCallingUidPermission(knoxVpnContext).mCallerUid)) {
      return 140;
    }
    int isUsbTetheringOverVpnEnabledValidation =
        this.mKnoxVpnApiValidation.isUsbTetheringOverVpnEnabledValidation(knoxVpnContext, str);
    if (isUsbTetheringOverVpnEnabledValidation != 100) {
      return isUsbTetheringOverVpnEnabledValidation;
    }
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry == null) {
      return 108;
    }
    try {
      i = profileEntry.getUsbTethering();
    } catch (Exception unused) {
      i = 101;
    }
    return i;
  }

  public int getUidPidEnabled(int i, String str) {
    int uidPidSearchEnabled;
    int i2 = 0;
    try {
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry != null) {
        if (DBG) {
          Log.d(
              "KnoxVpnEngineService",
              "The packageName stored in database is "
                  + profileEntry.getVendorPkgName()
                  + "callerUid is "
                  + i);
        }
        if (profileEntry.getVendorUid() == i) {
          if (profileEntry.getRouteType() == 0) {
            uidPidSearchEnabled = profileEntry.getUidPidSearchEnabled();
          } else if (profileEntry.getRouteType() == 1) {
            Log.d(
                "KnoxVpnEngineService",
                "activated state of the profile " + profileEntry.getActivateState());
            uidPidSearchEnabled = profileEntry.getUidPidSearchEnabled();
          }
          i2 = uidPidSearchEnabled;
        }
      }
    } catch (Exception e) {
      Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e));
    }
    if (DBG) {
      Log.d(
          "KnoxVpnEngineService",
          "getUidPidSearchEnabledForProfile: uidPidSearchEnabled value is " + i2);
    }
    return i2;
  }

  public boolean checkIfVendorCreatedKnoxProfile(String str, int i, int i2) {
    boolean z = false;
    try {
      boolean z2 = DBG;
      if (z2) {
        Log.d(
            "KnoxVpnEngineService",
            "checkIfVendorCreatedKnoxProfile: profile name and the vendor uid is " + str + i);
      }
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry != null) {
        String regularPackageName =
            this.mKnoxVpnHelper.getRegularPackageName(profileEntry.getVendorPkgName());
        if (z2) {
          Log.d(
              "KnoxVpnEngineService",
              "The vendor name who created the profile is " + regularPackageName);
        }
        if (this.mKnoxVpnHelper.getUIDForPackage(i2, regularPackageName) == i) {
          if (z2) {
            Log.d(
                "KnoxVpnEngineService",
                "The vendor name who created the profile and the caller has the same uid");
          }
          z = true;
        }
      }
    } catch (Exception e) {
      if (DBG) {
        Log.e(
            "KnoxVpnEngineService",
            "checkIfVendorCreatedKnoxProfile Exception :" + Log.getStackTraceString(e));
      }
    }
    if (DBG) {
      Log.d(
          "KnoxVpnEngineService",
          "checkIfVendorCreatedKnoxProfile: profilecreatedByKnoxVendor " + z);
    }
    return z;
  }

  public int getKnoxVpnProfileType(String str) {
    if (str == null) {
      return -1;
    }
    try {
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry != null) {
        return profileEntry.getRouteType();
      }
      return -1;
    } catch (Exception e) {
      Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e));
      return -1;
    }
  }

  public final List getActiveProfilesForVendor(String str) {
    ArrayList arrayList = new ArrayList();
    if (str != null) {
      try {
        for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
          String vendorPkgName = vpnProfileInfo.getVendorPkgName();
          int activateState = vpnProfileInfo.getActivateState();
          vpnProfileInfo.getPersonaId();
          if (vendorPkgName.equals(str)
              && activateState == 1
              && !this.mKnoxVpnHelper.checkIfVpnProfileTableIsEmpty(vendorPkgName)) {
            String profileName = vpnProfileInfo.getProfileName();
            Log.d("KnoxVpnEngineService", "profileName = " + profileName);
            arrayList.add(profileName);
          }
        }
      } catch (Exception e) {
        Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e));
      }
    }
    return arrayList;
  }

  public int startVpnForUserwideVpn(String str, int i, boolean z) {
    Log.d(
        "KnoxVpnEngineService",
        "startVpnForPackage: profileName value is " + str + " container id value is " + i);
    try {
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry == null) {
        return 108;
      }
      if (this.mKnoxVpnHelper.checkIfProfileHasChainingFeature(str) == 1
          && checkChainingStatus(str) == 0) {
        return EndpointMonitorConst.TRACE_EVENT_SCHED_CLS_EGRESS;
      }
      if (profileEntry.getActivateState() == 0) {
        return 301;
      }
      int vPNTransitionState = getVPNTransitionState(str);
      if (vPNTransitionState != -1 && vPNTransitionState != 1) {
        if (vPNTransitionState == 2) {
          this.mFirewallHelper.addMiscRulesRange(i, null, getVirtualInterfaceType(str));
        } else if (vPNTransitionState == 3) {
          this.mFirewallHelper.addMiscRulesRange(i, null, getVirtualInterfaceType(str));
        } else if (vPNTransitionState == 4) {
          String interfaceName = profileEntry.getInterfaceName();
          int netId = profileEntry.getNetId();
          if (DBG) {
            Log.d(
                "KnoxVpnEngineService",
                "startVpnForPackage: for connected state profileName value is "
                    + str
                    + "interfaceValue value is "
                    + interfaceName);
          }
          if (interfaceName == null) {
            return -1;
          }
          this.mFirewallHelper.addMiscRulesRange(i, interfaceName, getVirtualInterfaceType(str));
          setDnsSystemProperty(
              str,
              this.mKnoxVpnHelper.startUid(i),
              this.mKnoxVpnHelper.stopUid(i),
              interfaceName,
              netId);
          updateProxyRules(3, str, getKnoxVpnHelperInstance().updateProxyList(i, true));
        } else if (vPNTransitionState != 5) {
          Log.e("KnoxVpnEngineService", "startVpnForPackage : VPN State not valid");
          return -1;
        }
        return 0;
      }
      if (DBG) {
        Log.d("KnoxVpnEngineService", "startVpnForPackage: profileName : " + str);
      }
      this.mFirewallHelper.addMiscRulesRange(i, null, getVirtualInterfaceType(str));
      if (z) {
        startVpnProfile(str);
      }
      return 0;
    } catch (Exception e) {
      Log.e("KnoxVpnEngineService", "startVpnForPackage: " + Log.getStackTraceString(e));
      return -1;
    }
  }

  public int startVpnForPerApplication(String str, List list, boolean z) {
    Log.d("KnoxVpnEngineService", "startVpnForPerApplication: profileName value is " + str);
    try {
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry == null) {
        return 108;
      }
      if (this.mKnoxVpnHelper.checkIfProfileHasChainingFeature(str) == 1
          && checkChainingStatus(str) == 0) {
        return EndpointMonitorConst.TRACE_EVENT_SCHED_CLS_EGRESS;
      }
      if (profileEntry.getActivateState() == 0) {
        return 301;
      }
      int vPNTransitionState = getVPNTransitionState(str);
      if (vPNTransitionState != -1 && vPNTransitionState != 1) {
        if (vPNTransitionState == 2) {
          this.mFirewallHelper.addMiscRules(list, null, getVirtualInterfaceType(str));
        } else if (vPNTransitionState == 3) {
          this.mFirewallHelper.addMiscRules(list, null, getVirtualInterfaceType(str));
        } else if (vPNTransitionState == 4) {
          String interfaceName = profileEntry.getInterfaceName();
          int netId = profileEntry.getNetId();
          if (DBG) {
            Log.d(
                "KnoxVpnEngineService",
                "startVpnForPackage: for connected state profileName value is "
                    + str
                    + "interfaceValue value is "
                    + interfaceName);
          }
          if (interfaceName == null) {
            return -1;
          }
          this.mFirewallHelper.addMiscRules(
              list, profileEntry.getInterfaceName(), getVirtualInterfaceType(str));
          Iterator it = list.iterator();
          while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            setDnsSystemProperty(str, intValue, intValue, interfaceName, netId);
            updateProxyRules(3, str, getKnoxVpnHelperInstance().updateProxyList(intValue, false));
          }
        } else if (vPNTransitionState != 5) {
          Log.e("KnoxVpnEngineService", "startVpnForPackage : VPN State not valid");
          return -1;
        }
        return 0;
      }
      if (DBG) {
        Log.d("KnoxVpnEngineService", "startVpnForPackage: profileName : " + str);
      }
      this.mFirewallHelper.addMiscRules(list, null, getVirtualInterfaceType(str));
      if (z) {
        startVpnProfile(str);
      }
      return 0;
    } catch (Exception e) {
      Log.e("KnoxVpnEngineService", "startVpnForPackage: " + Log.getStackTraceString(e));
      return -1;
    }
  }

  public final boolean isNetworkConnected() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      if (getVpnManagerService().getActiveDefaultNetwork() != null) {
        Log.d("KnoxVpnEngineService", "check network connection : returns true");
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return true;
      }
      Log.d("KnoxVpnEngineService", "check network connection : returns false");
      return false;
    } catch (Exception e) {
      Log.v("KnoxVpnEngineService", "Exception: " + Log.getStackTraceString(e));
      return false;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void printProfileVpnMap() {
    if (DBG) {
      Log.v(
          "KnoxVpnEngineService", "********************Printing profile map ********************");
      try {
        Log.v("KnoxVpnEngineService", "No of profiles: " + this.vpnConfig.getProfileCount());
        for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
          if (vpnProfileInfo != null) {
            String profileName = vpnProfileInfo.getProfileName();
            String interfaceName = vpnProfileInfo.getInterfaceName();
            int vPNTransitionState = getVPNTransitionState(profileName);
            Log.v("KnoxVpnEngineService", "{ProfileName = " + profileName + ": [");
            Log.v(
                "KnoxVpnEngineService",
                "iface:"
                    + interfaceName
                    + " ; personaId: "
                    + vpnProfileInfo.getPersonaId()
                    + " ; adminId: "
                    + vpnProfileInfo.getAdminId());
            StringBuilder sb = new StringBuilder();
            sb.append("state: ");
            sb.append(vPNTransitionState);
            Log.v("KnoxVpnEngineService", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("activated:");
            sb2.append(vpnProfileInfo.getActivateState() == 0 ? "false " : "true ");
            Log.v("KnoxVpnEngineService", sb2.toString());
            for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.getPackageList()) {
              Log.v(
                  "KnoxVpnEngineService",
                  "{ uid:"
                      + vpnPackageInfo.getUid()
                      + ", packageName:"
                      + vpnPackageInfo.getPackageName()
                      + ", profileName:"
                      + profileName
                      + "}},");
            }
          } else {
            Log.v("KnoxVpnEngineService", "VpnProfileInfo contains NULL object.");
          }
          Log.v("KnoxVpnEngineService", "]},");
        }
        Log.v("KnoxVpnEngineService", "] ");
      } catch (Exception e) {
        Log.v("KnoxVpnEngineService", "Exception: " + Log.getStackTraceString(e));
      }
      Log.v(
          "KnoxVpnEngineService",
          "-----------------Printing profile map --------------------------");
    }
  }

  public final synchronized void initializeVpnVendorForUserAfterLockBootComplete(int i) {
    try {
      Log.d("KnoxVpnEngineService", "initializeVpnVendorForUserAfterLockBootComplete " + i);
      ArrayList arrayList = new ArrayList();
      for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
        String profileName = vpnProfileInfo.getProfileName();
        int personaId = vpnProfileInfo.getPersonaId();
        boolean z = DBG;
        if (z) {
          Log.d(
              "KnoxVpnEngineService",
              "initializeVpnVendorForUserAfterLockBootComplete : ProfileName: "
                  + profileName
                  + " , cid = "
                  + personaId);
        }
        String vendorPkgName = vpnProfileInfo.getVendorPkgName();
        if (z) {
          Log.d(
              "KnoxVpnEngineService",
              "initializeVpnVendorForUserAfterLockBootComplete : Checking vendor : "
                  + vendorPkgName);
        }
        if (personaId == i && vendorPkgName != null && !arrayList.contains(vendorPkgName)) {
          if (z) {
            Log.d(
                "KnoxVpnEngineService",
                "initializeVpnVendorForUserAfterLockBootComplete : Adding vendor : "
                    + vendorPkgName);
          }
          arrayList.add(vendorPkgName);
        }
      }
      Iterator it = arrayList.iterator();
      while (it.hasNext()) {
        String str = (String) it.next();
        if (this.mVpnConnectionList.containsKey(str)) {
          Log.d(
              "KnoxVpnEngineService",
              "initializeVpnVendorForUserAfterLockBootComplete: skip binding for " + str);
        } else {
          Log.d(
              "KnoxVpnEngineService",
              "initializeVpnVendorForUserAfterLockBootComplete : Binding to vendor : " + str);
          bindKnoxVpnInterface(str, this.mKnoxVpnHelper.getAdminIdFromPackageName(str));
        }
      }
    } catch (Exception e) {
      Log.d(
          "KnoxVpnEngineService",
          "initializeVpnVendorForUserAfterLockBootComplete : Exception: "
              + Log.getStackTraceString(e));
    }
  }

  public final synchronized void initializeVpnVendorForUserAfterBootComplete(int i) {
    int personaId;
    try {
      Log.d("KnoxVpnEngineService", "initializeVpnVendorForUserAfterBootComplete " + i);
      Iterator it = this.vpnConfig.getProfileEntries().iterator();
      while (true) {
        if (!it.hasNext()) {
          break;
        }
        VpnProfileInfo vpnProfileInfo = (VpnProfileInfo) it.next();
        if (vpnProfileInfo.getUsbtetherAuth() == 1
            && (personaId = vpnProfileInfo.getPersonaId()) == i) {
          String profileName = vpnProfileInfo.getProfileName();
          this.mKnoxVpnTetherAuthentication.bindTetherAuthService(
              profileName,
              personaId,
              this.mKnoxVpnHelper.getTetherAuthDetailsFromDatabase(profileName));
          break;
        }
      }
      ArrayList arrayList = new ArrayList();
      for (VpnProfileInfo vpnProfileInfo2 : this.vpnConfig.getProfileEntries()) {
        String profileName2 = vpnProfileInfo2.getProfileName();
        int personaId2 = vpnProfileInfo2.getPersonaId();
        boolean z = DBG;
        if (z) {
          Log.d(
              "KnoxVpnEngineService",
              "initializeVpnVendorForUserAfterBootComplete : ProfileName: "
                  + profileName2
                  + " , cid = "
                  + personaId2);
        }
        String vendorPkgName = vpnProfileInfo2.getVendorPkgName();
        if (z) {
          Log.d(
              "KnoxVpnEngineService",
              "initializeVpnVendorForUserAfterBootComplete : Checking vendor : " + vendorPkgName);
        }
        if (personaId2 == i && vendorPkgName != null && !arrayList.contains(vendorPkgName)) {
          if (z) {
            Log.d(
                "KnoxVpnEngineService",
                "initializeVpnVendorForUserAfterBootComplete : Adding vendor : " + vendorPkgName);
          }
          arrayList.add(vendorPkgName);
        }
      }
      Iterator it2 = arrayList.iterator();
      while (it2.hasNext()) {
        String str = (String) it2.next();
        if (this.mVpnConnectionList.containsKey(str)) {
          startVpnConnectionForBindedClient(str, getVpnInterface(str));
        } else {
          Log.d(
              "KnoxVpnEngineService",
              "initializeVpnVendorForUserAfterBootComplete : Binding to vendor : " + str);
          bindKnoxVpnInterface(str, this.mKnoxVpnHelper.getAdminIdFromPackageName(str));
        }
      }
    } catch (Exception e) {
      Log.d(
          "KnoxVpnEngineService",
          "initializeVpnVendorForUserAfterBootComplete : Exception: " + Log.getStackTraceString(e));
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:55:0x025d A[Catch: Exception -> 0x0275, all -> 0x03d1, TryCatch #2 {Exception -> 0x0275, blocks: (B:7:0x000b, B:9:0x0017, B:11:0x001d, B:13:0x0021, B:14:0x0028, B:16:0x0032, B:17:0x0039, B:18:0x003e, B:20:0x0044, B:23:0x00a0, B:25:0x0122, B:27:0x0167, B:29:0x0170, B:30:0x0181, B:33:0x018b, B:38:0x0196, B:39:0x019d, B:41:0x01fc, B:43:0x0202, B:44:0x0205, B:46:0x020c, B:48:0x0212, B:51:0x0219, B:53:0x0253, B:55:0x025d, B:56:0x026d, B:58:0x0268, B:59:0x0221, B:65:0x0085), top: B:6:0x000b, outer: #0 }] */
  /* JADX WARN: Removed duplicated region for block: B:58:0x0268 A[Catch: Exception -> 0x0275, all -> 0x03d1, TryCatch #2 {Exception -> 0x0275, blocks: (B:7:0x000b, B:9:0x0017, B:11:0x001d, B:13:0x0021, B:14:0x0028, B:16:0x0032, B:17:0x0039, B:18:0x003e, B:20:0x0044, B:23:0x00a0, B:25:0x0122, B:27:0x0167, B:29:0x0170, B:30:0x0181, B:33:0x018b, B:38:0x0196, B:39:0x019d, B:41:0x01fc, B:43:0x0202, B:44:0x0205, B:46:0x020c, B:48:0x0212, B:51:0x0219, B:53:0x0253, B:55:0x025d, B:56:0x026d, B:58:0x0268, B:59:0x0221, B:65:0x0085), top: B:6:0x000b, outer: #0 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final synchronized void initializeHashtable() {
    VpnProfileInfo profileEntry;
    VpnProfileInfo profileEntry2;
    int i;
    String str;
    int i2;
    Log.d("KnoxVpnEngineService", "initializeHashtable ");
    try {
      ArrayList dataByFields =
          this.mVpnStorageProvider.getDataByFields("VpnProfileInfo", null, null, null);
      String str2 = KnoxVpnConstants.BLOCK_APP_TRAFFIC;
      if (dataByFields != null && dataByFields.size() > 0) {
        boolean z = DBG;
        if (z) {
          Log.d(
              "KnoxVpnEngineService",
              "initializeHashtable : VPN INFO: New Framework is enabled. Set the system property");
        }
        SystemProperties.set("net.vpn.framework", "2.0");
        if (z) {
          Log.d("KnoxVpnEngineService", "initializeHashtable : Cursor not null");
        }
        Iterator it = dataByFields.iterator();
        int i3 = -1;
        while (it.hasNext()) {
          ContentValues contentValues = (ContentValues) it.next();
          String asString = contentValues.getAsString("profileName");
          int intValue = contentValues.getAsInteger("defaultRoute").intValue();
          int intValue2 = contentValues.getAsInteger("adminUid").intValue();
          int intValue3 = contentValues.getAsInteger("personaId").intValue();
          String asString2 = contentValues.getAsString("vendorName");
          try {
            i3 = contentValues.getAsInteger("profileId").intValue();
          } catch (Exception e) {
            Log.e(
                "KnoxVpnEngineService",
                "profileID exception when DB migration on OS upgrade" + Log.getStackTraceString(e));
          }
          String asString3 = contentValues.getAsString("protocolType");
          int intValue4 = contentValues.getAsInteger("activateState").intValue();
          int intValue5 = contentValues.getAsInteger("vpnConnectionType").intValue();
          int intValue6 = contentValues.getAsInteger("chainingEnabled").intValue();
          int intValue7 = contentValues.getAsInteger("uidPidSearch").intValue();
          String asString4 = contentValues.getAsString("proxyServer");
          Iterator it2 = it;
          int intValue8 = contentValues.getAsInteger("proxyPort").intValue();
          String asString5 = contentValues.getAsString("pacurl");
          int intValue9 = contentValues.getAsInteger("proxyAuth").intValue();
          String asString6 = contentValues.getAsString("vpnIpChainName");
          int intValue10 = contentValues.getAsInteger("usbTethering").intValue();
          int intValue11 = contentValues.getAsInteger("usbTetheringAuth").intValue();
          boolean z2 = DBG;
          if (z2) {
            i2 = intValue11;
            str = asString4;
            StringBuilder sb = new StringBuilder();
            i = intValue7;
            sb.append("initializeHashtable : adminId :");
            sb.append(intValue2);
            sb.append(" Profile :");
            sb.append(asString);
            sb.append(" vendorName :");
            sb.append(asString2);
            sb.append(", vpnConnectionType: ");
            sb.append(intValue5);
            sb.append(", chainingEnabled: ");
            sb.append(intValue6);
            Log.d("KnoxVpnEngineService", sb.toString());
          } else {
            i = intValue7;
            str = asString4;
            i2 = intValue11;
          }
          if (asString != null) {
            if (!this.vpnConfig.containsProfileEntry(asString)) {
              VpnProfileInfo vpnProfileInfo = new VpnProfileInfo();
              vpnProfileInfo.setInterfaceName(null);
              vpnProfileInfo.setNetId(0);
              this.vpnConfig.addProfileEntry(asString, vpnProfileInfo);
            }
            VpnProfileInfo profileEntry3 = this.vpnConfig.getProfileEntry(asString);
            if (profileEntry3 != null) {
              if (z2) {
                Log.d(
                    "KnoxVpnEngineService",
                    "initializeHashtable :  ProfileName found in map. Add to the list.");
              }
              profileEntry3.setProfileName(asString);
              profileEntry3.setRouteType(intValue);
              profileEntry3.setAdminId(intValue2);
              profileEntry3.setPersonaId(intValue3);
              profileEntry3.setVendorPkgName(asString2);
              profileEntry3.setVendorUid(this.mKnoxVpnHelper.getUidForPackageName(asString2));
              profileEntry3.setProfileId(i3);
              profileEntry3.setProtocolType(asString3);
              profileEntry3.setActivateState(intValue4);
              profileEntry3.setUidPidSearchEnabled(i);
              profileEntry3.setVpnConnectionType(intValue5);
              profileEntry3.setChainingEnabled(intValue6);
              profileEntry3.setProxyServer(str);
              profileEntry3.setProxyPort(intValue8);
              profileEntry3.setProxyUsername(
                  this.mKnoxVpnHelper.retrieveProfileCredentials(asString, "proxy-username"));
              profileEntry3.setProxyPassword(
                  this.mKnoxVpnHelper.retrieveProfileCredentials(asString, "proxy-password"));
              profileEntry3.setPacurl(asString5);
              profileEntry3.setPacAuthRequired(intValue9);
              if (profileEntry3.getProxyUsername() != null
                  && profileEntry3.getProxyPassword() != null) {
                profileEntry3.proxyCredentialsPreDefined(true);
              }
              this.mKnoxVpnHelper.getExemptedListFromDatabase(asString);
              if (asString6 != null && !asString6.isEmpty() && asString6.length() > 0) {
                profileEntry3.setIpChainName(asString6);
                profileEntry3.setUsbTethering(intValue10);
                profileEntry3.setUsbTetherAuth(i2);
                if (intValue10 != 1) {
                  this.mKnoxVpnHelper.registerNetdTetherEventListener(true);
                  this.mKnoxVpnHelper.enableKnoxVpnFlagForTether(true);
                } else {
                  this.mKnoxVpnHelper.enableKnoxVpnFlagForTether(false);
                }
                refreshDomainInHashMap(asString);
              }
              String ipChainNameAfterUpgrade =
                  this.mKnoxVpnHelper.getIpChainNameAfterUpgrade(asString);
              Log.d(
                  "KnoxVpnEngineService",
                  "IPChain name is being set as "
                      + ipChainNameAfterUpgrade
                      + " for profile "
                      + asString
                      + " after device upgrade from O to P");
              this.mKnoxVpnHelper.updateIpChainNameForProfile(asString, ipChainNameAfterUpgrade);
              profileEntry3.setIpChainName(ipChainNameAfterUpgrade);
              profileEntry3.setUsbTethering(intValue10);
              profileEntry3.setUsbTetherAuth(i2);
              if (intValue10 != 1) {}
              refreshDomainInHashMap(asString);
            } else if (z2) {
              Log.d(
                  "KnoxVpnEngineService",
                  "initializeHashtable :  profileName is not exist in vpnConfig");
            }
          }
          it = it2;
        }
      }
    } catch (Exception e2) {
      Log.e(
          "KnoxVpnEngineService",
          "init :  Exception when reading Profile DB:" + Log.getStackTraceString(e2));
    }
    try {
      ArrayList dataByFields2 =
          this.mVpnStorageProvider.getDataByFields("VpnPackageInfo", null, null, null);
      if (dataByFields2 != null) {
        if (DBG) {
          Log.d("KnoxVpnEngineService", "initializeHashtable : step#2 : Cursor not null");
        }
        Iterator it3 = dataByFields2.iterator();
        while (it3.hasNext()) {
          ContentValues contentValues2 = (ContentValues) it3.next();
          String asString7 = contentValues2.getAsString("profileName");
          String asString8 = contentValues2.getAsString("packageName");
          int intValue12 = contentValues2.getAsInteger("packageUid").intValue();
          int intValue13 = contentValues2.getAsInteger("packageCid").intValue();
          if (DBG) {
            Log.d(
                "KnoxVpnEngineService",
                "initializeHashtable :  Cid : "
                    + intValue13
                    + "Uid:"
                    + intValue12
                    + " Profile:"
                    + asString7
                    + " packageName:"
                    + asString8);
          }
          if (intValue12 == -2) {
            if (asString8.equalsIgnoreCase(
                    this.mKnoxVpnHelper.getPersonifiedName(intValue13, "ADD_ALL_PACKAGES"))
                && (profileEntry = this.vpnConfig.getProfileEntry(asString7)) != null) {
              profileEntry.addPackageEntry(asString8, intValue12, intValue13);
            }
          } else if (intValue12 > -1
              && (profileEntry2 = this.vpnConfig.getProfileEntry(asString7)) != null) {
            if (this.mKnoxVpnHelper.isPackageInstalled(
                this.mKnoxVpnHelper.getRegularPackageName(asString8),
                profileEntry2.getPersonaId())) {
              profileEntry2.addPackageEntry(asString8, intValue12, intValue13);
            } else {
              deleteUIDFromPackageData(asString8);
            }
          }
        }
      }
    } catch (Exception e3) {
      Log.e(
          "KnoxVpnEngineService",
          "init2 :  Exception when reading package DB:" + Log.getStackTraceString(e3));
    }
    try {
      ArrayList dataByFields3 =
          this.mVpnStorageProvider.getDataByFields("VpnNotificationTable", null, null, null);
      if (dataByFields3 != null) {
        Iterator it4 = dataByFields3.iterator();
        while (it4.hasNext()) {
          ContentValues contentValues3 = (ContentValues) it4.next();
          this.notificationFlagState.put(
              Integer.valueOf(contentValues3.getAsInteger("notificationUserId").intValue()),
              Integer.valueOf(contentValues3.getAsInteger("dismissFlag").intValue()));
        }
      }
    } catch (Exception e4) {
      Log.e(
          "KnoxVpnEngineService",
          "init2 :  Exception when reading Notification DB:" + Log.getStackTraceString(e4));
    }
    if (DBG) {
      printProfileVpnMap();
    }
  }

  public final synchronized void addBlockingRulesForPackages() {
    Collection profileEntries;
    char c;
    Log.d("KnoxVpnEngineService", "addBlockingRulesForPackages");
    try {
      profileEntries = this.vpnConfig.getProfileEntries();
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "addBlockingRulesForPackages : Exception when reading package DB:"
              + Log.getStackTraceString(e));
    }
    if (profileEntries != null && profileEntries.size() > 0) {
      if (profileEntries.size() > 0) {
        this.mFirewallHelper.updateIpBlockingRule();
        KnoxVpnFirewallHelper knoxVpnFirewallHelper = this.mFirewallHelper;
        String str = KnoxVpnConstants.BLOCK_APP_TRAFFIC;
        knoxVpnFirewallHelper.addRulesForNoUidPackets(str, str, 3);
        allowAppsToMakeDnsQueryForNetId();
      }
      Iterator it = profileEntries.iterator();
      while (it.hasNext()) {
        VpnProfileInfo vpnProfileInfo = (VpnProfileInfo) it.next();
        String profileName = vpnProfileInfo.getProfileName();
        String interfaceName = vpnProfileInfo.getInterfaceName();
        int vendorUid = vpnProfileInfo.getVendorUid();
        int activateState = vpnProfileInfo.getActivateState();
        String ipChainName = vpnProfileInfo.getIpChainName();
        String vendorPkgName = vpnProfileInfo.getVendorPkgName();
        int personaId = vpnProfileInfo.getPersonaId();
        String regularPackageName = this.mKnoxVpnHelper.getRegularPackageName(vendorPkgName);
        int chainingEnabled = vpnProfileInfo.getChainingEnabled();
        ArrayList arrayList = new ArrayList();
        getVpnManagerService()
            .createEnterpriseVpnInstance(
                regularPackageName, profileName, personaId, chainingEnabled);
        this.mKnoxVpnHelper.updateUidsToVpnUidRange(profileName);
        this.mFirewallHelper.createIpChainForProfile(ipChainName);
        this.mFirewallHelper.addRulesInOutputChain(ipChainName);
        addExemptRulesForUid(vendorUid, profileName);
        Iterator it2 = vpnProfileInfo.getExemptPackageList().iterator();
        while (it2.hasNext()) {
          updateRulesToExemptUid(
              profileName, 1, 1, ((Integer) it2.next()).intValue(), 0, null, null);
          vendorPkgName = vendorPkgName;
          arrayList = arrayList;
          it = it;
        }
        Iterator it3 = it;
        ArrayList arrayList2 = arrayList;
        String str2 = vendorPkgName;
        for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.getPackageList()) {
          if (this.mKnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)) {
            int containerIdFromPackageName =
                this.mKnoxVpnHelper.getContainerIdFromPackageName(vpnPackageInfo.getPackageName());
            if (activateState == 1) {
              getOemNetdService().knoxVpnBlockUserWideDnsQuery(true, containerIdFromPackageName);
              this.mFirewallHelper.addMiscRulesRange(
                  containerIdFromPackageName, interfaceName, getVirtualInterfaceType(profileName));
            }
            this.mFirewallHelper.addRangeRulesForFilteredPackages(
                str2,
                ipChainName,
                containerIdFromPackageName,
                this.mKnoxVpnHelper.getDefaultNetworkInterface(profileName));
          } else {
            arrayList2.add(Integer.valueOf(vpnPackageInfo.getUid()));
          }
        }
        if (!arrayList2.isEmpty()) {
          if (activateState == 1) {
            this.mFirewallHelper.addMiscRules(
                arrayList2, interfaceName, getVirtualInterfaceType(profileName));
          }
          this.mFirewallHelper.addRulesForFilteredPackages(
              str2,
              ipChainName,
              arrayList2,
              this.mKnoxVpnHelper.getDefaultNetworkInterface(profileName));
        }
        if (activateState == 1) {
          c = 3;
          this.mFirewallHelper.addMarkingRulesForFilteredPackages(
              KnoxVpnConstants.BLOCK_APP_TRAFFIC, ipChainName, 3);
        } else {
          c = 3;
        }
        it = it3;
      }
    }
  }

  public final void removeFromHashMapByProfileName(String str) {
    Log.d("KnoxVpnEngineService", "profile info is going to be removed from the device");
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry == null) {
      Log.d(
          "KnoxVpnEngineService",
          "Error occured while removing hashmap by profile: The error code is 108");
      return;
    }
    try {
      if (this.vpnConfig.containsProfileEntry(str)) {
        String vendorPkgName = profileEntry.getVendorPkgName();
        String regularPackageName = this.mKnoxVpnHelper.getRegularPackageName(vendorPkgName);
        if (profileEntry.getProxyServer() != KnoxVpnConstants.DEFAULT_PROXY_SERVER
            || profileEntry.getPacurl() != Uri.EMPTY) {
          getKnoxVpnPacProcessor().stopPacSupport(str);
          updateProxyRules(0, str, getKnoxVpnHelperInstance().getListOfUid(str));
          this.mFirewallHelper.removeRulesToAcceptProxyPackets(
              profileEntry.getInterfaceType(),
              profileEntry.getInterfaceName(),
              UserHandle.getUid(profileEntry.getPersonaId(), 1002));
        }
        removeMiscRulesForProfile(str);
        this.mFirewallHelper.removeRulesForNoUidPackets(
            profileEntry.getInterfaceName(),
            profileEntry.getInterfaceType(),
            profileEntry.getIpChainName());
        if (getKnoxVpnHelperInstance().checkIfVpnProfileTableIsEmpty(vendorPkgName)) {
          removeExemptRulesForUid(profileEntry.getVendorUid(), str);
          if (this.mKnoxVpnHelper.isNativeVpnClient(str)) {
            this.mFirewallHelper.removeIpRulesForExemptedUid(1016, 3);
          } else {
            this.mFirewallHelper.removeIpRulesForExemptedUid(profileEntry.getVendorUid(), 3);
          }
          this.mKnoxVpnHelper.addOrRemoveAppsFromBatteryOptimization(
              str, regularPackageName, false);
        }
        Iterator it = profileEntry.getExemptPackageList().iterator();
        while (it.hasNext()) {
          int intValue = ((Integer) it.next()).intValue();
          updateRulesToExemptUid(str, 0, 1, intValue, 0, null, null);
          updateRulesToExemptUid(
              str,
              0,
              2,
              intValue,
              profileEntry.getInterfaceType(),
              profileEntry.getInterfaceName(),
              profileEntry.getDefaultInterface());
          updateRulesToExemptUid(
              str,
              0,
              3,
              intValue,
              profileEntry.getInterfaceType(),
              profileEntry.getInterfaceName(),
              profileEntry.getDefaultInterface());
        }
        this.mFirewallHelper.removeIpChainForProfile(profileEntry.getIpChainName());
        this.mFirewallHelper.removeNatRules(
            profileEntry.getInterfaceName(), getVirtualInterfaceType(str));
        this.mFirewallHelper.updateDropRulesForNoUidPackets(
            1,
            profileEntry.getInterfaceName(),
            profileEntry.getInterfaceAddress(),
            profileEntry.getDefaultInterface(),
            profileEntry.getV6InterfaceAddress());
        this.mFirewallHelper.removeIpRouteAndPolicyRules(
            profileEntry.getInterfaceName(), getVirtualInterfaceType(str));
        this.mFirewallHelper.removeRulesToAcceptIncomingPackets(
            profileEntry.getInterfaceType(), profileEntry.getInterfaceName());
        Bundle bundle = new Bundle();
        bundle.putString("profileName", str);
        sendMessageToHandler(16, bundle);
        removeProcessKillNotification(str);
        this.mNotificationMap.remove(str);
        if (profileEntry.getUsbTethering() == 1) {
          this.mFirewallHelper.removeRulesForUsbTethering(
              profileEntry.getInterfaceType(), profileEntry.getInterfaceName());
          this.mKnoxVpnHelper.registerNetdTetherEventListener(false);
          this.mKnoxVpnHelper.enableKnoxVpnFlagForTether(false);
        }
        if (profileEntry.getUsbtetherAuth() == 1) {
          Log.d(
              "KnoxVpnTetherAuthentication",
              "unbind usb auth service after profile removal or user removal");
          this.mKnoxVpnHelper.addOrRemoveAppsFromBatteryOptimization(
              str, "com.samsung.knox.vpn.tether.auth", false);
          this.mKnoxVpnTetherAuthentication.unbindTetherAuthService(true);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          getVpnManagerService()
              .removeEnterpriseVpnInstance(regularPackageName, str, profileEntry.getPersonaId());
          Binder.restoreCallingIdentity(clearCallingIdentity);
          this.vpnConfig.removeProfileEntry(str);
          if (this.vpnConfig.getProfileEntries().isEmpty()) {
            this.mFirewallHelper.applyBlockingRulesForDns(0, 0, 5);
            this.mFirewallHelper.removeIpBlockingRule();
            KnoxVpnFirewallHelper knoxVpnFirewallHelper = this.mFirewallHelper;
            String str2 = KnoxVpnConstants.BLOCK_APP_TRAFFIC;
            knoxVpnFirewallHelper.removeRulesForNoUidPackets(str2, 3, str2);
            SystemProperties.set("net.vpn.framework", "0.0");
            Iterator it2 = this.mKnoxVpnHelper.getListOfActiveUsers().iterator();
            while (it2.hasNext()) {
              getKnoxVpnPacProcessor().unBindPacService(((Integer) it2.next()).intValue());
            }
            denyAppsToMakeDnsQueryForNetId();
            this.mKnoxVpnHelper.addOrRemoveSystemAppFromBatteryOptimization(
                str, "com.knox.vpn.proxyhandler", false);
            this.mKnoxVpnHelper.addOrRemoveAppsFromBatteryOptimization(
                str, "com.android.vpndialogs", false);
            Iterator it3 = this.mKnoxVpnHelper.getListOfActiveUsers().iterator();
            while (it3.hasNext()) {
              this.mKnoxVpnHelper.addOrRemoveSystemAppFromDataSaverWhitelist(
                  str, UserHandle.getUid(((Integer) it3.next()).intValue(), 1002), false);
            }
            unregisterFilterList();
            this.mFirewallHelper.removeExemptRulesForDownloadManagerUid(
                this.mKnoxVpnHelper.getUIDForPackage(0, "com.android.providers.downloads"));
            this.mProcessManager.unregisterProcessObserver();
            for (Integer num : KnoxVpnConstants.AID_EXEMPT_LIST) {
              this.mFirewallHelper.removeExemptRulesForUid(num.intValue());
            }
            this.mFirewallHelper.clearEbpfMap(0);
            getVpnManagerService().unregisterSystemDefaultNetworkCallback();
          }
        } catch (Throwable th) {
          Binder.restoreCallingIdentity(clearCallingIdentity);
          throw th;
        }
      }
    } catch (Exception e) {
      if (DBG) {
        Log.d(
            "KnoxVpnEngineService",
            "Exception occured while removing the profile info from the device"
                + Log.getStackTraceString(e));
      }
    }
    if (DBG) {
      printProfileVpnMap();
    }
  }

  public final void removePackageListByUid(String str, String str2, int i) {
    boolean z;
    int i2;
    int i3;
    String[] strArr;
    String str3;
    boolean z2;
    int i4 = i;
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry == null) {
      return;
    }
    int userId = UserHandle.getUserId(i);
    ArrayList arrayList = new ArrayList();
    String ipChainName = profileEntry.getIpChainName();
    try {
      String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i4);
      int i5 = 2;
      String str4 = "%s removed from vpn for profile %s";
      boolean z3 = false;
      if (packagesForUid == null) {
        String personifiedName = this.mKnoxVpnHelper.getPersonifiedName(userId, str2);
        if (profileEntry.getPackage(personifiedName) == null
            || !deleteUIDFromPackageData(personifiedName)) {
          return;
        }
        arrayList.add(Integer.valueOf(i));
        if (profileEntry.getActivateState() == 0) {
          profileEntry.removePackageEntry(personifiedName);
          AuditLog.logAsUser(
              5,
              5,
              true,
              Process.myPid(),
              "KnoxVpnEngineService",
              String.format("%s removed from vpn for profile %s", personifiedName, str),
              userId);
          return;
        }
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(Integer.valueOf(i));
        this.mFirewallHelper.removeMiscRules(
            arrayList2, profileEntry.getInterfaceName(), getVirtualInterfaceType(str));
        unsetDnsSystemProperty(str, i4, i4);
        updateProxyRules(2, str, getKnoxVpnHelperInstance().updateProxyList(i4, false));
        profileEntry.removePackageEntry(personifiedName);
        this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
        z = false;
        AuditLog.logAsUser(
            5,
            5,
            true,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format("%s removed from vpn for profile %s", personifiedName, str),
            userId);
      } else {
        int length = packagesForUid.length;
        int i6 = 0;
        while (i6 < length) {
          String personifiedName2 =
              this.mKnoxVpnHelper.getPersonifiedName(userId, packagesForUid[i6]);
          if (profileEntry.getPackage(personifiedName2) != null
              && removePackagesFromDatabase(str, personifiedName2)) {
            arrayList.add(Integer.valueOf(i));
            if (profileEntry.getActivateState() == 0) {
              profileEntry.removePackageEntry(personifiedName2);
              i2 = i6;
              i3 = length;
              strArr = packagesForUid;
              AuditLog.logAsUser(
                  5,
                  5,
                  true,
                  Process.myPid(),
                  "KnoxVpnEngineService",
                  String.format(str4, personifiedName2, str),
                  userId);
              str3 = str4;
              z2 = false;
            } else {
              i2 = i6;
              i3 = length;
              strArr = packagesForUid;
              ArrayList arrayList3 = new ArrayList();
              arrayList3.add(Integer.valueOf(i));
              this.mFirewallHelper.removeMiscRules(
                  arrayList3, profileEntry.getInterfaceName(), getVirtualInterfaceType(str));
              unsetDnsSystemProperty(str, i4, i4);
              updateProxyRules(i5, str, getKnoxVpnHelperInstance().updateProxyList(i4, false));
              profileEntry.removePackageEntry(personifiedName2);
              this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
              str3 = str4;
              z2 = false;
              AuditLog.logAsUser(
                  5,
                  5,
                  true,
                  Process.myPid(),
                  "KnoxVpnEngineService",
                  String.format(str3, personifiedName2, str),
                  userId);
            }
            i6 = i2 + 1;
            z3 = z2;
            str4 = str3;
            length = i3;
            packagesForUid = strArr;
            i5 = 2;
            i4 = i;
          }
          i2 = i6;
          i3 = length;
          z2 = z3;
          strArr = packagesForUid;
          str3 = str4;
          i6 = i2 + 1;
          z3 = z2;
          str4 = str3;
          length = i3;
          packagesForUid = strArr;
          i5 = 2;
          i4 = i;
        }
        z = z3;
      }
      this.mFirewallHelper.removeRulesForFilteredPackages(
          profileEntry.getVendorPkgName(), ipChainName, arrayList);
      refreshDomainInHashMap(str);
      updateNotification(str, userId, z);
      int packageCount = profileEntry.getPackageCount();
      if (profileEntry.getVpnConnectionType() == 1
          && !this.mProcessManager.processRunCheck(profileEntry)) {
        z = true;
      }
      if ((packageCount <= 0 || z) && profileEntry.getActivateState() == 1) {
        try {
          IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
          if (binderInterfaceForProfile != null) {
            int stopConnection = binderInterfaceForProfile.stopConnection(str);
            if (stopConnection != 0) {
              AuditLog.logAsUser(
                  3,
                  5,
                  false,
                  Process.myPid(),
                  "KnoxVpnEngineService",
                  String.format(
                      "Error occurred trying to stop vpn connection from profile %s", str),
                  userId);
            } else {
              AuditLog.logAsUser(
                  5,
                  5,
                  true,
                  Process.myPid(),
                  "KnoxVpnEngineService",
                  String.format("Connection with vpn vendor service stopped for profile %s", str),
                  userId);
            }
            Log.d(
                "KnoxVpnEngineService",
                "removePackagesAfterUninstall: stopping the vpn connection status for on-demand"
                    + " configuration after removing all the packages "
                    + stopConnection);
          }
        } catch (Exception e) {
          AuditLog.logAsUser(
              3,
              5,
              false,
              Process.myPid(),
              "KnoxVpnEngineService",
              String.format(
                  "Exception stopping connection for profile %s after removing package list by uid",
                  str),
              userId);
          Log.d(
              "KnoxVpnEngineService",
              "removePackagesAfterUninstall: stopping the vpn connection failed for on-demand"
                  + " configuration after removing all the packages "
                  + Log.getStackTraceString(e));
        }
      }
    } catch (Exception e2) {
      Log.e(
          "KnoxVpnEngineService",
          "removePackagesAfterUninstall exception " + Log.getStackTraceString(e2));
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format(
              "Exception while removing packages from vpn after uninstallation for profile %s",
              str),
          userId);
    }
  }

  public final void removeExemptedUidDetailsAfterUninstall(String str, String str2, int i) {
    String str3;
    String str4;
    int i2;
    String str5;
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry == null) {
      return;
    }
    int userId = UserHandle.getUserId(i);
    try {
      int i3 = 2;
      String str6 =
          "Success removing uid = %d from exempted list after uninstallation for profile %s";
      String str7 = "removeExemptedUidDetailsAfterUninstall: removing from exempeted list ";
      if (this.mContext.getPackageManager().getPackagesForUid(i) == null) {
        Iterator it = profileEntry.getExemptPackageList().iterator();
        while (it.hasNext()) {
          int intValue = ((Integer) it.next()).intValue();
          if (intValue == i) {
            boolean updateExemptedListToDatabase =
                this.mKnoxVpnHelper.updateExemptedListToDatabase(str, str2, -1);
            Log.d("KnoxVpnEngineService", str7 + updateExemptedListToDatabase);
            if (updateExemptedListToDatabase) {
              str4 = str7;
              i2 = i3;
              updateRulesToExemptUid(str, 0, 1, intValue, 0, null, null);
              updateRulesToExemptUid(
                  str,
                  0,
                  2,
                  intValue,
                  profileEntry.getInterfaceType(),
                  profileEntry.getInterfaceName(),
                  profileEntry.getDefaultInterface());
              updateRulesToExemptUid(
                  str,
                  0,
                  3,
                  intValue,
                  profileEntry.getInterfaceType(),
                  profileEntry.getInterfaceName(),
                  profileEntry.getDefaultInterface());
              it.remove();
              int myPid = Process.myPid();
              Object[] objArr = new Object[i2];
              objArr[0] = Integer.valueOf(intValue);
              objArr[1] = str;
              str5 = str6;
              AuditLog.logAsUser(
                  5, 5, true, myPid, "KnoxVpnEngineService", String.format(str5, objArr), userId);
              str6 = str5;
              i3 = i2;
              str7 = str4;
            }
          }
          str4 = str7;
          i2 = i3;
          str5 = str6;
          str6 = str5;
          i3 = i2;
          str7 = str4;
        }
      } else {
        String str8 = "removeExemptedUidDetailsAfterUninstall: removing from exempeted list ";
        String str9 =
            "Success removing uid = %d from exempted list after uninstallation for profile %s";
        Iterator it2 = profileEntry.getExemptPackageList().iterator();
        while (it2.hasNext()) {
          int intValue2 = ((Integer) it2.next()).intValue();
          if (intValue2 == i) {
            boolean removeExemptedListToDatabase =
                this.mKnoxVpnHelper.removeExemptedListToDatabase(str, i);
            StringBuilder sb = new StringBuilder();
            String str10 = str8;
            sb.append(str10);
            sb.append(removeExemptedListToDatabase);
            Log.d("KnoxVpnEngineService", sb.toString());
            if (removeExemptedListToDatabase) {
              str8 = str10;
              String str11 = str9;
              updateRulesToExemptUid(str, 0, 1, intValue2, 0, null, null);
              updateRulesToExemptUid(
                  str,
                  0,
                  2,
                  intValue2,
                  profileEntry.getInterfaceType(),
                  profileEntry.getInterfaceName(),
                  profileEntry.getDefaultInterface());
              updateRulesToExemptUid(
                  str,
                  0,
                  3,
                  intValue2,
                  profileEntry.getInterfaceType(),
                  profileEntry.getInterfaceName(),
                  profileEntry.getDefaultInterface());
              it2.remove();
              str3 = str11;
              AuditLog.logAsUser(
                  5,
                  5,
                  true,
                  Process.myPid(),
                  "KnoxVpnEngineService",
                  String.format(str11, Integer.valueOf(intValue2), str),
                  userId);
              str9 = str3;
            } else {
              str8 = str10;
            }
          }
          str3 = str9;
          str9 = str3;
        }
      }
      if (this.mKnoxVpnHelper.getuserIdForExemptedUids().contains(Integer.valueOf(userId))) {
        return;
      }
      this.mFirewallHelper.removeExemptRulesForDownloadManagerUid(
          this.mKnoxVpnHelper.getUIDForPackage(userId, "com.android.providers.downloads"));
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "removePackagesAfterUninstall exception " + Log.getStackTraceString(e));
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format(
              "Exception while removing packages exempted from vpn after uninstallation for profile"
                  + " %s",
              str),
          userId);
    }
  }

  public final boolean deleteUIDFromPackageData(String str) {
    Log.d("KnoxVpnEngineService", "Inside deleteUIDFromPackageData");
    return updatePackageData(str, -1);
  }

  public final boolean updatePackageData(String str, int i) {
    if (str != null) {
      try {
        Log.d("KnoxVpnEngineService", "Inside updatePackageData : packageName = " + str);
        ArrayList dataByFields =
            this.mVpnStorageProvider.getDataByFields(
                "VpnPackageInfo",
                new String[] {"packageName"},
                new String[] {str},
                new String[] {"profileName", "packageUid"});
        if (dataByFields != null && dataByFields.size() > 0) {
          Log.d(
              "KnoxVpnEngineService",
              "update to package : Cursor not null and data present, so update packageData UID in"
                  + " DB");
          ContentValues contentValues = new ContentValues();
          contentValues.put("packageUid", Integer.valueOf(i));
          String[] strArr = {"packageName"};
          String[] strArr2 = {str};
          Log.d(
              "KnoxVpnEngineService",
              "update to package : update to package before DB insert:" + strArr2[0] + strArr[0]);
          return this.mVpnStorageProvider.putDataByFields(
              "VpnPackageInfo", strArr, strArr2, contentValues);
        }
      } catch (Exception e) {
        Log.d(
            "KnoxVpnEngineService", "update to package : Exception:" + Log.getStackTraceString(e));
      }
    }
    return false;
  }

  public final void handleActionAdminChanged(Bundle bundle) {
    int i = bundle.getInt("android.intent.extra.user_handle", -1);
    int i2 = bundle.getInt("android.intent.extra.UID", -1);
    if (i == -1 || i2 == -1) {
      return;
    }
    stopVpnConnectionAfterAdminRemoval(i2);
  }

  public final synchronized void stopVpnConnectionAfterAdminRemoval(int i) {
    ConcurrentHashMap concurrentHashMap;
    Log.d("KnoxVpnEngineService", "stopVpnConnectionAfterAdminRemoval :  beginning");
    try {
      String str = null;
      ArrayList dataByFields =
          this.mVpnStorageProvider.getDataByFields(
              "VpnProfileInfo", null, null, new String[] {"profileName", "adminUid"});
      Log.d(
          "KnoxVpnEngineService",
          "stopVpnConnectionAfterAdminRemoval :  got cvList from DB : " + dataByFields);
      int i2 = -1;
      if (dataByFields != null && dataByFields.size() > 0) {
        Iterator it = dataByFields.iterator();
        while (it.hasNext()) {
          ContentValues contentValues = (ContentValues) it.next();
          str = contentValues.getAsString("profileName");
          i2 = contentValues.getAsInteger("adminUid").intValue();
          Log.d(
              "KnoxVpnEngineService",
              "stopVpnConnectionAfterAdminRemoval :  checking profile : " + str + " admin: " + i2);
          if (i2 == i) {
            Log.d(
                "KnoxVpnEngineService",
                "stopVpnConnectionAfterAdminRemoval :   admin matched: " + i);
            Log.d(
                "KnoxVpnEngineService",
                "stopVpnConnectionAfterAdminRemoval :  removing hashmap and db: " + str);
            IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
            Log.d(
                "KnoxVpnEngineService",
                "stopVpnConnectionAfterAdminRemoval :  vpnInterface: " + binderInterfaceForProfile);
            if (binderInterfaceForProfile != null) {
              if (getActivate(str) == 1) {
                int state = binderInterfaceForProfile.getState(str);
                Log.d(
                    "KnoxVpnEngineService",
                    "stopVpnConnectionAfterAdminRemoval: currentStateOfProfile value is " + state);
                if (state != 1 || state != 5) {
                  try {
                    int stopConnection = binderInterfaceForProfile.stopConnection(str);
                    if (stopConnection != 0) {
                      AuditLog.logAsUser(
                          3,
                          5,
                          false,
                          Process.myPid(),
                          "KnoxVpnEngineService",
                          String.format(
                              "Error occurred trying to stop vpn connection from profile %s", str),
                          UserHandle.getUserId(i));
                    } else {
                      AuditLog.logAsUser(
                          5,
                          5,
                          true,
                          Process.myPid(),
                          "KnoxVpnEngineService",
                          String.format(
                              "Connection with vpn vendor service stopped for profile %s", str),
                          UserHandle.getUserId(i));
                    }
                    Log.d(
                        "KnoxVpnEngineService",
                        "stopVpnConnectionAfterAdminRemoval: stopConnectionStatus value is "
                            + stopConnection);
                  } catch (Exception unused) {
                    AuditLog.logAsUser(
                        3,
                        5,
                        false,
                        Process.myPid(),
                        "KnoxVpnEngineService",
                        "Exception stopping vpn connection after admin removal",
                        UserHandle.getUserId(i));
                  }
                }
              }
              Log.d(
                  "KnoxVpnEngineService",
                  "stopVpnConnectionAfterAdminRemoval :  Calling remove Connection: ");
              Log.d(
                  "KnoxVpnEngineService",
                  "stopVpnConnectionAfterAdminRemoval : remove Connection. Status = "
                      + binderInterfaceForProfile.removeConnection(str));
            }
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
              removeExemptRulesForUid(profileEntry.getVendorUid(), str);
            }
            removeFromHashMapByProfileName(str);
          }
        }
      }
      if (i2 > 0) {
        int userId = UserHandle.getUserId(i2);
        if (userId != 0 && i2 == i) {
          this.mKnoxVpnHelper.addOrRemoveSystemAppFromDataSaverWhitelist(
              str, UserHandle.getUid(userId, 1002), false);
          ConcurrentHashMap concurrentHashMap2 = this.mIgnoreConnectCheckForChaining;
          if (concurrentHashMap2 != null
              && concurrentHashMap2.containsKey(Integer.valueOf(userId))) {
            this.mIgnoreConnectCheckForChaining.remove(Integer.valueOf(userId));
          }
          this.mFirewallHelper.removeExemptRulesForDownloadManagerUid(
              this.mKnoxVpnHelper.getUIDForPackage(userId, "com.android.providers.downloads"));
        }
        if (i2 == i
            && (concurrentHashMap = this.notificationFlagState) != null
            && concurrentHashMap.containsKey(Integer.valueOf(userId))) {
          this.notificationFlagState.remove(Integer.valueOf(userId));
        }
      }
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "stop vpn connection after admin remove : Failed " + Log.getStackTraceString(e));
    }
  }

  public final void removeProfileFromHashMapAndDB(String str) {
    Log.d(
        "KnoxVpnEngineService",
        "removeProfileFromHashMapAndDB : removeProfileFromHashMapAndDB beginning");
    try {
      boolean deleteDataByFields =
          this.mVpnStorageProvider.deleteDataByFields(
              "VpnProfileInfo", new String[] {"profileName"}, new String[] {str});
      if (deleteDataByFields) {
        removeFromHashMapByProfileName(str);
      }
      Log.d(
          "KnoxVpnEngineService",
          "remove vpn connection for per app : success : " + deleteDataByFields);
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "remove vpn connection for perapp : Error in handling remove connection for per app vpn"
              + " Feature"
              + Log.getStackTraceString(e));
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:24:0x0075, code lost:

     android.util.Log.d("KnoxVpnEngineService", "run all vpn : startVpnProfile : profileName " + r3 + " state : " + r4);
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void runAllVpnService() {
    Log.d("KnoxVpnEngineService", "run all vpn : runAllVpnService beginning");
    try {
      for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
        String profileName = vpnProfileInfo.getProfileName();
        if (getBinderInterfaceForProfile(profileName) == null) {
          Log.d(
              "KnoxVpnEngineService",
              "runAllVpnService:binder value is null for the profile " + profileName);
        } else {
          int vPNTransitionState = getVPNTransitionState(profileName);
          boolean z = DBG;
          if (z) {
            Log.d(
                "KnoxVpnEngineService",
                "run all vpn : profileName " + profileName + " state : " + vPNTransitionState);
          }
          if (vpnProfileInfo.getRouteType() != 0
              && (vPNTransitionState == 1 || vPNTransitionState == 5 || vPNTransitionState == -1)) {
            startVpnProfile(profileName);
          }
        }
      }
    } catch (Exception e) {
      Log.d(
          "KnoxVpnEngineService",
          "Exception occured while doing runAllVpnService " + Log.getStackTraceString(e));
    }
  }

  public final void startVpnConnectionForBindedClient(String str, IKnoxVpnService iKnoxVpnService) {
    try {
      for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
        if (vpnProfileInfo.getVendorPkgName().equalsIgnoreCase(str)
            && vpnProfileInfo.getRouteType() != 0
            && vpnProfileInfo.getActivateState() != 0) {
          String profileName = vpnProfileInfo.getProfileName();
          int state = iKnoxVpnService.getState(profileName);
          Log.d(
              "KnoxVpnEngineService",
              "Start the vpn connection after binding successfully for the profile "
                  + profileName
                  + " with the vpn client "
                  + vpnProfileInfo.getVendorPkgName()
                  + " whose current state is "
                  + state);
          if (state == 1 || state == 5 || state == -1) {
            startVpnProfile(profileName);
          }
        }
      }
    } catch (Exception e) {
      Log.d(
          "KnoxVpnEngineService",
          "Exception occured while doing runAllVpnService " + Log.getStackTraceString(e));
    }
  }

  public final synchronized void startVpnConnectionForAllClients() {
    try {
      HashSet<String> hashSet = new HashSet();
      for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
        String profileName = vpnProfileInfo.getProfileName();
        if (getBinderInterfaceForProfile(profileName) == null) {
          if (this.mVpnConnectionList.containsKey(vpnProfileInfo.getVendorPkgName())) {
            Log.d(
                "KnoxVpnEngineService",
                "startVpnConnectionForAllClients: skip binding for "
                    + vpnProfileInfo.getVendorPkgName());
          } else {
            hashSet.add(vpnProfileInfo.getVendorPkgName());
          }
        } else {
          int vPNTransitionState = getVPNTransitionState(profileName);
          if (vpnProfileInfo.getRouteType() != 0
              && (vPNTransitionState == 1 || vPNTransitionState == 5 || vPNTransitionState == -1)) {
            Log.d(
                "KnoxVpnEngineService",
                "starting the vpn connection for the profile after UPSM is switched off "
                    + profileName
                    + "state:"
                    + vPNTransitionState);
            startVpnProfile(profileName);
          }
        }
      }
      for (String str : hashSet) {
        Log.d(
            "KnoxVpnEngineService",
            "The client binder object was set to null due to app getting killed when UPSM was"
                + " switched on and trying to bind again");
        bindKnoxVpnInterface(str, this.mKnoxVpnHelper.getAdminIdFromPackageName(str));
      }
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception occured while trying to start the vpn connection after UPSM is switched off"
              + Log.getStackTraceString(e));
    }
  }

  public final synchronized void startVpnConnectionForClient(int i) {
    try {
      HashSet<String> hashSet = new HashSet();
      for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
        if (vpnProfileInfo.getVendorUid() == i) {
          String profileName = vpnProfileInfo.getProfileName();
          if (getBinderInterfaceForProfile(profileName) == null) {
            if (this.mVpnConnectionList.containsKey(vpnProfileInfo.getVendorPkgName())) {
              Log.d(
                  "KnoxVpnEngineService",
                  "startVpnConnectionForClient: skip binding for "
                      + vpnProfileInfo.getVendorPkgName());
            } else {
              hashSet.add(vpnProfileInfo.getVendorPkgName());
            }
          } else {
            int vPNTransitionState = getVPNTransitionState(profileName);
            if (vpnProfileInfo.getRouteType() != 0
                && (vPNTransitionState == 1
                    || vPNTransitionState == 5
                    || vPNTransitionState == -1)) {
              Log.d(
                  "KnoxVpnEngineService",
                  "starting the vpn connection for the profile after system restriction is removed "
                      + profileName
                      + "state:"
                      + vPNTransitionState
                      + "vpnClient:"
                      + i);
              startVpnProfile(profileName);
            }
          }
        }
      }
      for (String str : hashSet) {
        Log.d(
            "KnoxVpnEngineService",
            "The client binder object was set to null due to app getting killed during idle or"
                + " power mode and trying to bind again");
        bindKnoxVpnInterface(str, this.mKnoxVpnHelper.getAdminIdFromPackageName(str));
      }
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception occured while trying to start the vpn connection after the system restriction"
              + " is removed"
              + Log.getStackTraceString(e));
    }
  }

  public final synchronized void stopVpnConnectionForClient(int i) {
    IKnoxVpnService binderInterfaceForProfile;
    try {
      for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
        if (vpnProfileInfo.getVendorUid() == i) {
          String profileName = vpnProfileInfo.getProfileName();
          Log.d(
              "KnoxVpnEngineService",
              "stopping the vpn connection for the profile due to system restriction"
                  + profileName
                  + "vpnClient: "
                  + i);
          if (vpnProfileInfo.getRouteType() != 0
              && (binderInterfaceForProfile = getBinderInterfaceForProfile(profileName)) != null) {
            if (binderInterfaceForProfile.stopConnection(profileName) != 0) {
              AuditLog.logAsUser(
                  3,
                  5,
                  false,
                  Process.myPid(),
                  "KnoxVpnEngineService",
                  String.format(
                      "Error occurred trying to stop vpn connection from profile %s", profileName),
                  UserHandle.getUserId(i));
            } else {
              AuditLog.logAsUser(
                  5,
                  5,
                  true,
                  Process.myPid(),
                  "KnoxVpnEngineService",
                  String.format(
                      "Connection with vpn vendor service stopped for profile %s", profileName),
                  UserHandle.getUserId(i));
            }
          }
        }
      }
    } catch (Exception e) {
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format(
              "Exception stopping vpn connection for client with uid %d", Integer.valueOf(i)),
          UserHandle.getUserId(i));
      Log.e(
          "KnoxVpnEngineService",
          "Exception occured while trying to stopping the vpn connection due to system restriction"
              + Log.getStackTraceString(e));
    }
  }

  public final void setupIntentFilter() {
    Log.d("KnoxVpnEngineService", "setup intent filter is called");
    if (this.receiver != null) {
      Log.d("KnoxVpnEngineService", "VpnReceiver already registered");
      return;
    }
    Log.d("KnoxVpnEngineService", "Registering VpnReceiver is successful");
    this.receiver = new VpnReceiver();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
      intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
      intentFilter.addCategory("android.intent.category.DEFAULT");
      intentFilter.addDataScheme("package");
      this.mContext.registerReceiverAsUser(this.receiver, UserHandle.ALL, intentFilter, null, null);
      IntentFilter intentFilter2 = new IntentFilter();
      intentFilter2.addAction("android.intent.action.BOOT_COMPLETED");
      intentFilter2.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
      intentFilter2.addAction("android.intent.action.USER_STARTED");
      intentFilter2.addAction("android.intent.action.USER_SWITCHED");
      intentFilter2.addAction("android.intent.action.USER_REMOVED");
      intentFilter2.addAction("android.net.conn.CONNECTIVITY_CHANGE");
      intentFilter2.addAction("android.intent.action.AIRPLANE_MODE");
      intentFilter2.addAction("android.intent.action.USER_PRESENT");
      intentFilter2.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
      intentFilter2.addAction("com.samsung.android.knox.intent.action.UCM_REFRESH_DONE");
      intentFilter2.addAction("android.net.wifi.STATE_CHANGE");
      intentFilter2.addAction("android.hardware.usb.action.USB_STATE");
      this.mContext.registerReceiverAsUser(
          this.receiver, UserHandle.ALL, intentFilter2, null, null);
      IntentFilter intentFilter3 = new IntentFilter();
      intentFilter3.addAction("enterprise.container.uninstalled");
      intentFilter3.addAction("enterprise.container.admin.changed");
      this.mContext.registerReceiverAsUser(
          this.receiver,
          UserHandle.ALL,
          intentFilter3,
          "com.samsung.android.knox.permission.KNOX_CONTAINER",
          null);
      IntentFilter intentFilter4 = new IntentFilter();
      intentFilter4.addAction("com.samsung.android.knox.intent.action.INTERFACE_STATUS_INTERNAL");
      intentFilter4.addAction(
          "com.samsung.android.knox.intent.action.VPN_PROXY_BROADCAST_INTERNAL");
      this.mContext.registerReceiverAsUser(
          this.receiver,
          UserHandle.ALL,
          intentFilter4,
          "com.samsung.android.knox.permission.KNOX_VPN_INTERNAL",
          null);
      IntentFilter intentFilter5 = new IntentFilter();
      intentFilter5.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
      intentFilter5.addDataScheme("package");
      this.mContext.registerReceiverAsUser(
          this.receiver, UserHandle.ALL, intentFilter5, null, null);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void unregisterFilterList() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        if (this.receiver != null) {
          Log.d("KnoxVpnEngineService", "unregistering VpnReceiver is successful");
          this.mContext.unregisterReceiver(this.receiver);
        }
        this.receiver = null;
      } catch (Exception unused) {
        Log.e("KnoxVpnEngineService", "Error occured while trying to unregister the reciever");
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public class VpnReceiver extends BroadcastReceiver {
    public VpnReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      String schemeSpecificPart;
      String action = intent.getAction();
      Log.d("KnoxVpnEngineService", "Vpn Receiver : " + action);
      if (isInitialStickyBroadcast()) {
        return;
      }
      Bundle bundle = new Bundle();
      if (action.equalsIgnoreCase("android.intent.action.PACKAGE_ADDED")) {
        Uri data = intent.getData();
        schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
        boolean booleanExtra = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
        if (KnoxVpnEngineService.DBG) {
          Log.d("KnoxVpnEngineService", "Vpn Receiver : The extra value is " + booleanExtra);
        }
        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
        if (intExtra > -1) {
          if (KnoxVpnEngineService.DBG) {
            Log.i("KnoxVpnEngineService", "Vpn Receiver : Package Added = " + schemeSpecificPart);
          }
          bundle.putInt("uid", intExtra);
          bundle.putString("package", schemeSpecificPart);
          bundle.putBoolean("new_install_or_update", booleanExtra);
          KnoxVpnEngineService.this.sendMessageToHandler(2, bundle);
          return;
        }
        return;
      }
      if (action.equalsIgnoreCase("android.intent.action.PACKAGE_REMOVED")) {
        Uri data2 = intent.getData();
        schemeSpecificPart = data2 != null ? data2.getSchemeSpecificPart() : null;
        int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
        boolean booleanExtra2 = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
        if (KnoxVpnEngineService.DBG) {
          Log.i("KnoxVpnEngineService", "Vpn Receiver : Package Removed = " + schemeSpecificPart);
        }
        bundle.putInt("uid", intExtra2);
        bundle.putString("package", schemeSpecificPart);
        bundle.putBoolean("new_install_or_update", booleanExtra2);
        KnoxVpnEngineService.this.sendMessageToHandler(3, bundle);
        return;
      }
      if (action.equalsIgnoreCase("android.intent.action.AIRPLANE_MODE")) {
        Log.i("KnoxVpnEngineService", "Airplane Event received.");
        if (intent.getBooleanExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, false)) {
          KnoxVpnEngineService.this.sendMessageToHandler(20, null);
          return;
        }
        return;
      }
      if (action.equalsIgnoreCase("android.intent.action.USER_PRESENT")) {
        KnoxVpnEngineService.this.sendMessageToHandler(21, null);
        return;
      }
      if (action.equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")) {
        KnoxVpnEngineService.this.sendMessageToHandler(15, intent.getExtras());
        return;
      }
      if (action.equalsIgnoreCase("android.intent.action.LOCKED_BOOT_COMPLETED")) {
        KnoxVpnEngineService.this.sendMessageToHandler(14, intent.getExtras());
        return;
      }
      if (action.equalsIgnoreCase("android.intent.action.USER_STARTED")) {
        KnoxVpnEngineService.this.sendMessageToHandler(9, intent.getExtras());
        return;
      }
      if (action.equalsIgnoreCase("android.intent.action.USER_SWITCHED")) {
        KnoxVpnEngineService.this.sendMessageToHandler(13, intent.getExtras());
        return;
      }
      if (action.equalsIgnoreCase("android.net.conn.CONNECTIVITY_CHANGE")) {
        NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
        if (networkInfo == null) {
          Log.d("KnoxVpnEngineService", "networkInfo is null");
          return;
        }
        Log.d(
            "KnoxVpnEngineService",
            "change in connectivity has occured  for the network type "
                + networkInfo.getType()
                + networkInfo.getDetailedState()
                + networkInfo.getState());
        if (networkInfo.getType() == 1) {
          Bundle extras = intent.getExtras();
          if (networkInfo.getState() == NetworkInfo.State.CONNECTED
              || networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
            extras.putString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, "CONNECTED");
            KnoxVpnEngineService.this.sendMessageToHandler(25, extras);
          } else {
            if (networkInfo.getState() != NetworkInfo.State.DISCONNECTED
                && networkInfo.getDetailedState() != NetworkInfo.DetailedState.DISCONNECTED) {
              return;
            }
            extras.putString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, "DISCONNECTED");
            KnoxVpnEngineService.this.sendMessageToHandler(25, extras);
          }
        } else if (networkInfo.getType() == 0) {
          Bundle extras2 = intent.getExtras();
          if (networkInfo.getState() == NetworkInfo.State.CONNECTED
              || networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
            extras2.putString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, "CONNECTED");
            KnoxVpnEngineService.this.sendMessageToHandler(26, extras2);
          } else {
            if (networkInfo.getState() != NetworkInfo.State.DISCONNECTED
                && networkInfo.getDetailedState() != NetworkInfo.DetailedState.DISCONNECTED) {
              return;
            }
            extras2.putString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, "DISCONNECTED");
            KnoxVpnEngineService.this.sendMessageToHandler(26, extras2);
          }
        } else if (networkInfo.getType() == 9) {
          Bundle extras3 = intent.getExtras();
          if (networkInfo.getState() == NetworkInfo.State.CONNECTED
              || networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
            extras3.putString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, "CONNECTED");
            KnoxVpnEngineService.this.sendMessageToHandler(27, extras3);
          } else {
            if (networkInfo.getState() != NetworkInfo.State.DISCONNECTED
                && networkInfo.getDetailedState() != NetworkInfo.DetailedState.DISCONNECTED) {
              return;
            }
            extras3.putString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, "DISCONNECTED");
            KnoxVpnEngineService.this.sendMessageToHandler(27, extras3);
          }
        }
        if (networkInfo.getType() == 17 || networkInfo.getType() == 7) {
          return;
        }
        KnoxVpnEngineService.this.sendMessageToHandler(4, intent.getExtras());
        return;
      }
      if (action.equals("android.intent.action.USER_REMOVED")) {
        KnoxVpnEngineService.this.sendMessageToHandler(6, intent.getExtras());
        return;
      }
      if (action.equals("com.samsung.android.knox.intent.action.INTERFACE_STATUS_INTERNAL")) {
        KnoxVpnEngineService.this.sendMessageToHandler(1, intent.getExtras());
        return;
      }
      if (action.equals("enterprise.container.admin.changed")) {
        bundle.putInt("android.intent.extra.user_handle", intent.getIntExtra("containerid", -1));
        bundle.putInt(
            "android.intent.extra.UID", intent.getIntExtra("android.intent.extra.UID", -1));
        KnoxVpnEngineService.this.sendMessageToHandler(12, bundle);
        return;
      }
      if (action.equals("com.samsung.android.knox.intent.action.VPN_PROXY_BROADCAST_INTERNAL")) {
        bundle.putInt(
            "uid", intent.getIntExtra("com.samsung.android.knox.intent.extra.caller", -1));
        KnoxVpnEngineService.this.sendMessageToHandler(18, bundle);
        return;
      }
      if (action.equals("com.samsung.intent.action.EMERGENCY_STATE_CHANGED")) {
        KnoxVpnEngineService.this.sendMessageToHandler(22, intent.getExtras());
        return;
      }
      if (action.equals("com.samsung.android.knox.intent.action.UCM_REFRESH_DONE")) {
        KnoxVpnEngineService.this.sendMessageToHandler(28, intent.getExtras());
        return;
      }
      if (action.equalsIgnoreCase("android.net.wifi.STATE_CHANGE")) {
        Bundle extras4 = intent.getExtras();
        NetworkInfo networkInfo2 = (NetworkInfo) extras4.getParcelable("networkInfo");
        if (networkInfo2.getDetailedState() == NetworkInfo.DetailedState.CONNECTING) {
          extras4.putInt("captive", 1);
          KnoxVpnEngineService.this.sendMessageToHandler(29, extras4);
          return;
        } else {
          if (networkInfo2.getDetailedState() == NetworkInfo.DetailedState.DISCONNECTED) {
            extras4.putInt("captive", 0);
            KnoxVpnEngineService.this.sendMessageToHandler(29, extras4);
            return;
          }
          return;
        }
      }
      if (action.equals("android.hardware.usb.action.USB_STATE")) {
        boolean booleanExtra3 = intent.getBooleanExtra("connected", false);
        boolean booleanExtra4 = intent.getBooleanExtra("configured", false);
        boolean booleanExtra5 = intent.getBooleanExtra("rndis", false);
        Log.d(
            "KnoxVpnEngineService",
            "usb_tethering status: usbConnected "
                + booleanExtra3
                + " usbConfigured "
                + booleanExtra4
                + " rndisEnabled "
                + booleanExtra5);
        if (!booleanExtra3) {
          bundle.putInt("bundle_usb_status", 1);
          KnoxVpnEngineService.this.sendMessageToHandler(33, bundle);
          return;
        }
        if (booleanExtra3 && booleanExtra4 && booleanExtra5) {
          bundle.putInt("bundle_usb_status", 0);
          KnoxVpnEngineService.this.sendMessageToHandler(33, bundle);
          return;
        } else {
          if ((booleanExtra3 || booleanExtra4) && !booleanExtra5) {
            bundle.putInt("bundle_usb_status", 1);
            KnoxVpnEngineService.this.sendMessageToHandler(33, bundle);
            return;
          }
          return;
        }
      }
      if (action.equalsIgnoreCase("android.intent.action.PACKAGE_DATA_CLEARED")) {
        KnoxVpnEngineService.this.sendMessageToHandler(34, intent.getExtras());
      }
    }
  }

  public final void sendMessageToHandler(int i, Bundle bundle) {
    KnoxVpnHandler knoxVpnHandler = this.mHandler;
    if (knoxVpnHandler != null) {
      this.mHandler.sendMessage(Message.obtain(knoxVpnHandler, i, 0, 0, bundle));
    }
  }

  public class KnoxVpnHandler extends Handler {
    public KnoxVpnHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      Log.i("KnoxVpnEngineService", "vpn handle : Message received");
      Bundle bundle = (Bundle) message.obj;
      switch (message.what) {
        case 1:
          KnoxVpnEngineService.this.handleVpnInterfaceState(bundle);
          break;
        case 2:
          KnoxVpnEngineService.this.handleActionPackageAdded(bundle);
          break;
        case 3:
          KnoxVpnEngineService.this.handleActionPackageRemoved(bundle);
          break;
        case 4:
          KnoxVpnEngineService.this.handleConnectivityAction(bundle);
          break;
        case 6:
          KnoxVpnEngineService.this.handleActionUserRemoved(bundle);
          break;
        case 12:
          KnoxVpnEngineService.this.handleActionAdminChanged(bundle);
          break;
        case 14:
          int i = bundle.getInt("android.intent.extra.user_handle", -10000);
          SemEmergencyManager.getInstance(KnoxVpnEngineService.this.mContext);
          if (!SemEmergencyManager.isEmergencyMode(KnoxVpnEngineService.this.mContext)) {
            KnoxVpnEngineService.this.handleActionLockBootCompleted(i);
            KnoxVpnEngineService.this.initializeVpnVendorForUserAfterLockBootComplete(i);
            KnoxVpnEngineService.this.registerNetworkCallback();
            break;
          }
          break;
        case 15:
          int i2 = bundle.getInt("android.intent.extra.user_handle", -10000);
          if (i2 == 0) {
            KnoxVpnEngineService.this.isDeviceBootCompleted = true;
          }
          SemEmergencyManager.getInstance(KnoxVpnEngineService.this.mContext);
          if (!SemEmergencyManager.isEmergencyMode(KnoxVpnEngineService.this.mContext)) {
            KnoxVpnEngineService.this.initializeVpnVendorForUserAfterBootComplete(i2);
            break;
          }
          break;
        case 16:
          KnoxVpnEngineService.this.mKnoxVpnHelper.removeProfileCredentials(
              bundle.getString("profileName"));
          break;
        case 19:
          KnoxVpnEngineService.this.stopVpnConnectionAfterAdminRemoval(bundle.getInt("uid"));
          break;
        case 20:
          KnoxVpnEngineService.this.stopVpnConnectionForAirplaneMode();
          break;
        case 21:
          KnoxVpnEngineService.this.handleUserScreenUnlock();
          break;
        case 22:
          if (bundle.getInt("reason", 0) == 5) {
            Log.d("KnoxVpnEngineService", "ultra power saving mode has been disabled");
            KnoxVpnEngineService.this.startVpnConnectionForAllClients();
            break;
          }
          break;
        case 23:
          KnoxVpnEngineService.this.startVpnConnectionForClient(bundle.getInt("uid"));
          break;
        case 24:
          KnoxVpnEngineService.this.stopVpnConnectionForClient(bundle.getInt("uid"));
          break;
        case 25:
          KnoxVpnEngineService.this.handleActionWifiChanged(bundle);
          break;
        case 26:
          KnoxVpnEngineService.this.handleActionMobileChanged(bundle);
          break;
        case 27:
          KnoxVpnEngineService.this.handleActionEthernetChanged(bundle);
          break;
        case 28:
          if (KnoxVpnEngineService.this.isDeviceBootCompleted) {
            KnoxVpnEngineService.this.startVpnConnectionForAllClients();
            break;
          }
          break;
        case 29:
          KnoxVpnEngineService.this.handleCaptivePortal(bundle);
          break;
        case 32:
          String string = message.getData().getString("message");
          Log.d("KnoxVpnEngineService", "case MSG_SHOW_TOAST: - START");
          if (string != null && KnoxVpnEngineService.this.mContext != null) {
            Toast.makeText(
                    new ContextThemeWrapper(
                        KnoxVpnEngineService.this.mContext, R.style.Theme.DeviceDefault.Light),
                    string,
                    1)
                .show();
          }
          Log.d("KnoxVpnEngineService", "case MSG_SHOW_TOAST: - END");
          break;
        case 33:
          KnoxVpnEngineService.this.handleActionUsbTethering(bundle);
          break;
        case 34:
          KnoxVpnEngineService.this.handleActionClearData(bundle);
          break;
      }
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:19:0x004f, code lost:

     r2 = r3.getAdminId();
     android.util.Log.d("KnoxVpnEngineService", "binding to vpn client after end-user cleared the data for client " + r0 + " with uid " + r7);
     bindKnoxVpnInterface(r1, r2);
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final synchronized void handleActionClearData(Bundle bundle) {
    int i = bundle.getInt("android.intent.extra.UID");
    String packageNameForUid = getKnoxVpnHelperInstance().getPackageNameForUid(i);
    String personifiedName =
        this.mKnoxVpnHelper.getPersonifiedName(UserHandle.getUserId(i), packageNameForUid);
    Collection profileEntries = this.vpnConfig.getProfileEntries();
    if (profileEntries != null && profileEntries.size() > 0) {
      Iterator it = this.vpnConfig.getProfileEntries().iterator();
      while (true) {
        if (!it.hasNext()) {
          break;
        }
        VpnProfileInfo vpnProfileInfo = (VpnProfileInfo) it.next();
        if (vpnProfileInfo != null
            && vpnProfileInfo.getVendorPkgName() != null
            && vpnProfileInfo.getVendorPkgName().equalsIgnoreCase(personifiedName)) {
          break;
        }
      }
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:13:0x002c, code lost:

     r0 = r1.getInterfaceName();
     r5 = r5.getInt("bundle_usb_status");
  */
  /* JADX WARN: Code restructure failed: missing block: B:14:0x0036, code lost:

     if (r5 != 0) goto L15;
  */
  /* JADX WARN: Code restructure failed: missing block: B:16:0x0039, code lost:

     if (r5 != 1) goto L27;
  */
  /* JADX WARN: Code restructure failed: missing block: B:17:0x003b, code lost:

     if (r0 == null) goto L20;
  */
  /* JADX WARN: Code restructure failed: missing block: B:19:0x0041, code lost:

     if (r0.isEmpty() != false) goto L20;
  */
  /* JADX WARN: Code restructure failed: missing block: B:20:0x0043, code lost:

     r4.mFirewallHelper.removeRulesForUsbTethering(r1.getInterfaceType(), r0);
  */
  /* JADX WARN: Code restructure failed: missing block: B:22:0x0050, code lost:

     if (r1.getUsbtetherAuth() != 1) goto L27;
  */
  /* JADX WARN: Code restructure failed: missing block: B:23:0x0052, code lost:

     android.util.Log.d("KnoxVpnTetherAuthentication", "usb tether auth process is going to be stopped after usb or usb tethering is disabled");
     r4.mKnoxVpnTetherAuthentication.stopTetheringAuthProcess(true);
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final synchronized void handleActionUsbTethering(Bundle bundle) {
    try {
      Collection profileEntries = this.vpnConfig.getProfileEntries();
      if (profileEntries != null && profileEntries.size() > 0) {
        Iterator it = this.vpnConfig.getProfileEntries().iterator();
        while (true) {
          if (!it.hasNext()) {
            break;
          }
          VpnProfileInfo vpnProfileInfo = (VpnProfileInfo) it.next();
          if (vpnProfileInfo.getUsbTethering() == 1) {
            break;
          }
        }
      }
    } catch (Exception unused) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception occured while trying to enable usb tethering after the usb cable is attached");
    }
  }

  public final void handleUserScreenUnlock() {
    getKnoxVpnPacProcessor().handleScreenunlock();
  }

  public final void stopVpnConnectionForAirplaneMode() {
    int i = 0;
    try {
      for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
        i = vpnProfileInfo.getPersonaId();
        if (vpnProfileInfo.getActivateState() == 1) {
          String profileName = vpnProfileInfo.getProfileName();
          Log.i("KnoxVpnEngineService", "Airplane Mode: Stopping connection for " + profileName);
          AuditLog.logAsUser(
              5,
              5,
              false,
              Process.myPid(),
              "KnoxVpnEngineService",
              String.format("Airplane Mode: Stopping connection for profile %s", profileName),
              i);
          IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(profileName);
          if ((binderInterfaceForProfile != null
                  ? binderInterfaceForProfile.stopConnection(profileName)
                  : -1)
              != 0) {
            Log.i(
                "KnoxVpnEngineService",
                "Airplane Mode: Stopping connection for " + profileName + " failed!!");
          }
        }
      }
    } catch (Exception e) {
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          "Exception stopping vpn connection for airplane mode",
          i);
      Log.i("KnoxVpnEngineService", "Airplane Mode: ACTION_AIRPLANE_MODE_CHANGED Exception", e);
    }
  }

  public final void handleKnoxVpnBuildUpdate() {
    try {
      String str = SystemProperties.get("ro.build.date", "NONE");
      if (!str.equals(SystemProperties.get("persist.sys.knoxvpn.date", "NONE"))) {
        if (DBG) {
          Log.d("KnoxVpnEngineService", "handleKnoxVpnBuildUpdate - updated");
        }
        SystemProperties.set("persist.sys.knoxvpn.date", str);
        checkToUpdateDb();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void checkToUpdateDb() {
    Log.d("KnoxVpnEngineService", "checkToUpdateDb");
    try {
      int profileId = this.mVpnStorageProvider.getProfileId();
      ArrayList dataByFields =
          this.mVpnStorageProvider.getDataByFields("VpnProfileInfo", null, null, null);
      if (profileId != 0 || dataByFields == null || dataByFields.size() <= 0) {
        return;
      }
      Iterator it = dataByFields.iterator();
      while (it.hasNext()) {
        ContentValues contentValues = (ContentValues) it.next();
        String asString = contentValues.getAsString("profileName");
        if (asString != null) {
          contentValues.put("profileId", Integer.valueOf(profileId));
          this.mVpnStorageProvider.putDataByFields(
              "VpnProfileInfo",
              new String[] {"profileName"},
              new String[] {asString},
              contentValues);
          String[] strArr = {String.valueOf(profileId)};
          profileId++;
          ContentValues contentValues2 = new ContentValues();
          contentValues2.put("profileCount", Integer.valueOf(profileId));
          this.mVpnStorageProvider.putDataByFields(
              "VpnAnalyticsTable", new String[] {"profileCount"}, strArr, contentValues2);
        }
      }
    } catch (Exception e) {
      Log.e("KnoxVpnEngineService", "exception - " + Log.getStackTraceString(e));
    }
  }

  public final void registerNetworkCallback() {
    Log.d("KnoxVpnEngineService", "Registering network callback");
    Binder.withCleanCallingIdentity(
        new FunctionalUtils
            .ThrowingRunnable() { // from class:
                                  // com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService$$ExternalSyntheticLambda0
          public final void runOrThrow() {
            KnoxVpnEngineService.this.lambda$registerNetworkCallback$0();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$registerNetworkCallback$0() {
    getConnectivityManager()
        .registerNetworkCallback(
            new NetworkRequest.Builder().addTransportType(1).addCapability(16).build(),
            this.mNetworkCallback);
  }

  public final synchronized void handleCaptivePortal(Bundle bundle) {
    Collection profileEntries;
    try {
      profileEntries = this.vpnConfig.getProfileEntries();
    } catch (Exception unused) {
      Log.e(
          "KnoxVpnEngineService", "Exception occured while handling captive portal firewall rules");
    }
    if (profileEntries == null) {
      return;
    }
    if (profileEntries.size() > 0) {
      int uIDForPackage =
          this.mKnoxVpnHelper.getUIDForPackage(0, "com.google.android.captiveportallogin");
      if (uIDForPackage != -1 ? this.mKnoxVpnHelper.checKIfUidIsExempted(uIDForPackage) : false) {
        if (mIsCaptiveExempt && bundle.getInt("captive") == 0) {
          Log.i("KnoxVpnEngineService", "Removing iptables captive rules");
          this.mFirewallHelper.removeRulesToDropIpv6SystemQueries(uIDForPackage);
          this.mFirewallHelper.removeRulesToDropIpv6SystemQueries(1000);
          this.mFirewallHelper.removeRulesToDropIpv6SystemQueries(1073);
          this.mFirewallHelper.removeRulesToExemptCaptivePortalQueries(1000);
          this.mFirewallHelper.removeRulesToExemptCaptivePortalQueries(uIDForPackage);
          this.mFirewallHelper.removeRulesToExemptCaptivePortalQueries(1073);
          String activeNetworkInterface = this.mKnoxVpnHelper.getActiveNetworkInterface();
          for (Integer num : KnoxVpnConstants.AID_EXEMPT_LIST) {
            this.mFirewallHelper.addIpRulesForExemptedUid(
                num.intValue(), activeNetworkInterface, 3);
          }
          synchronized (this.mCaptiveExemptLock) {
            mIsCaptiveExempt = false;
          }
        } else if (!mIsCaptiveExempt && bundle.getInt("captive") == 1) {
          Log.i("KnoxVpnEngineService", "Adding iptables captive rules");
          this.mFirewallHelper.insertRulesToDropIpv6SystemQueries(uIDForPackage);
          this.mFirewallHelper.insertRulesToDropIpv6SystemQueries(1000);
          this.mFirewallHelper.insertRulesToDropIpv6SystemQueries(1073);
          for (Integer num2 : KnoxVpnConstants.AID_EXEMPT_LIST) {
            this.mFirewallHelper.addIpRulesForExemptedUid(num2.intValue(), "wlan0", 3);
          }
          this.mFirewallHelper.addRulesToExemptCaptivePortalQueries("wlan0", 1000);
          this.mFirewallHelper.addRulesToExemptCaptivePortalQueries("wlan0", uIDForPackage);
          this.mFirewallHelper.addRulesToExemptCaptivePortalQueries("wlan0", 1073);
          synchronized (this.mCaptiveExemptLock) {
            mIsCaptiveExempt = true;
          }
        }
      }
    }
  }

  public final synchronized void handleActionWifiChanged(Bundle bundle) {
    Collection<VpnProfileInfo> profileEntries;
    try {
      profileEntries = this.vpnConfig.getProfileEntries();
    } catch (Throwable unused) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception occured while trying to apply iptable rule for user 0 during connectivity"
              + " change of wifi network");
    }
    if (profileEntries == null) {
      return;
    }
    if (profileEntries.size() > 0) {
      boolean z = false;
      for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
        int activateState = vpnProfileInfo.getActivateState();
        Iterator it = vpnProfileInfo.getPackageList().iterator();
        while (true) {
          if (!it.hasNext()) {
            break;
          }
          VpnPackageInfo vpnPackageInfo = (VpnPackageInfo) it.next();
          if (this.mKnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)
              && activateState == 1
              && this.mKnoxVpnHelper.getContainerIdFromPackageName(vpnPackageInfo.getPackageName())
                  == 0) {
            z = true;
            break;
          }
        }
        if (z) {
          break;
        }
      }
      for (VpnProfileInfo vpnProfileInfo2 : profileEntries) {
        this.mFirewallHelper.removeInputFilterDropRulesForInterface(
            1, this.mKnoxVpnHelper.getuserIdListForProfile(vpnProfileInfo2.getProfileName()));
        this.mFirewallHelper.removeInputFilterDropRulesForInterface(
            0, this.mKnoxVpnHelper.getuidListForProfile(vpnProfileInfo2.getProfileName()));
        if (this.mKnoxVpnHelper.isNativeVpnClient(vpnProfileInfo2.getProfileName())) {
          this.mFirewallHelper.removeIpRulesForExemptedUid(1016, 3);
        } else {
          this.mFirewallHelper.removeIpRulesForExemptedUid(vpnProfileInfo2.getVendorUid(), 3);
        }
        Iterator it2 = vpnProfileInfo2.getExemptPackageList().iterator();
        while (it2.hasNext()) {
          updateRulesToExemptUid(
              vpnProfileInfo2.getProfileName(),
              0,
              2,
              ((Integer) it2.next()).intValue(),
              3,
              null,
              null);
        }
      }
      HashSet hashSet = this.mKnoxVpnHelper.getuserIdForExemptedUids();
      Iterator it3 = hashSet.iterator();
      while (it3.hasNext()) {
        this.mFirewallHelper.removeExemptRulesForDownloadManagerUid(
            this.mKnoxVpnHelper.getUIDForPackage(
                ((Integer) it3.next()).intValue(), "com.android.providers.downloads"));
      }
      if (bundle.getBoolean("noConnectivity")) {
        Log.d("KnoxVpnEngineService", "Default Wifi Network lost connectivity");
        return;
      }
      if (bundle
          .getString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN)
          .equalsIgnoreCase("DISCONNECTED")) {
        Log.d("KnoxVpnEngineService", "Default Wifi Network is disconnected");
        return;
      }
      String activeNetworkInterface = getKnoxVpnHelperInstance().getActiveNetworkInterface();
      Log.d(
          "KnoxVpnEngineService",
          "Default Wifi Network interface Name is " + activeNetworkInterface);
      for (VpnProfileInfo vpnProfileInfo3 : profileEntries) {
        int activateState2 = vpnProfileInfo3.getActivateState();
        vpnProfileInfo3.getIpChainName();
        Iterator it4 = vpnProfileInfo3.getPackageList().iterator();
        while (true) {
          if (!it4.hasNext()) {
            break;
          }
          VpnPackageInfo vpnPackageInfo2 = (VpnPackageInfo) it4.next();
          if (this.mKnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo2)
              && activateState2 == 1
              && this.mKnoxVpnHelper.getContainerIdFromPackageName(vpnPackageInfo2.getPackageName())
                  == 0) {
            blockIncomingICMPPackets(true);
            break;
          }
        }
        if (activateState2 == 1) {
          String regularPackageName =
              this.mKnoxVpnHelper.getRegularPackageName(vpnProfileInfo3.getVendorPkgName());
          this.mFirewallHelper.addInputFilterDropRulesForInterface(
              regularPackageName,
              1,
              this.mKnoxVpnHelper.getuserIdListForProfile(vpnProfileInfo3.getProfileName()),
              activeNetworkInterface);
          this.mFirewallHelper.addInputFilterDropRulesForInterface(
              regularPackageName,
              0,
              this.mKnoxVpnHelper.getuidListForProfile(vpnProfileInfo3.getProfileName()),
              activeNetworkInterface);
          if (getChainingEnabledForProfile(vpnProfileInfo3.getVendorUid()) != 1) {
            if (this.mKnoxVpnHelper.isNativeVpnClient(vpnProfileInfo3.getProfileName())) {
              this.mFirewallHelper.addIpRulesForExemptedUid(1016, activeNetworkInterface, 3);
            } else {
              this.mFirewallHelper.addIpRulesForExemptedUid(
                  vpnProfileInfo3.getVendorUid(), activeNetworkInterface, 3);
            }
          }
          Iterator it5 = vpnProfileInfo3.getExemptPackageList().iterator();
          while (it5.hasNext()) {
            updateRulesToExemptUid(
                vpnProfileInfo3.getProfileName(),
                1,
                2,
                ((Integer) it5.next()).intValue(),
                3,
                null,
                activeNetworkInterface);
          }
        }
      }
      Iterator it6 = hashSet.iterator();
      while (it6.hasNext()) {
        this.mFirewallHelper.addExemptRulesForDownloadManagerUid(
            this.mKnoxVpnHelper.getUIDForPackage(
                ((Integer) it6.next()).intValue(), "com.android.providers.downloads"),
            activeNetworkInterface);
      }
    }
  }

  public final synchronized void handleActionMobileChanged(Bundle bundle) {
    Collection<VpnProfileInfo> profileEntries;
    try {
      profileEntries = this.vpnConfig.getProfileEntries();
    } catch (Throwable unused) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception occured while trying to apply iptable rule for user 0 during connectivity"
              + " change of mobile network");
    }
    if (profileEntries == null) {
      return;
    }
    if (this.vpnConfig.getProfileEntries().size() > 0) {
      boolean z = false;
      for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
        int activateState = vpnProfileInfo.getActivateState();
        Iterator it = vpnProfileInfo.getPackageList().iterator();
        while (true) {
          if (!it.hasNext()) {
            break;
          }
          VpnPackageInfo vpnPackageInfo = (VpnPackageInfo) it.next();
          if (this.mKnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)
              && activateState == 1
              && this.mKnoxVpnHelper.getContainerIdFromPackageName(vpnPackageInfo.getPackageName())
                  == 0) {
            z = true;
            break;
          }
        }
        if (z) {
          break;
        }
      }
      for (VpnProfileInfo vpnProfileInfo2 : profileEntries) {
        this.mFirewallHelper.removeInputFilterDropRulesForInterface(
            1, this.mKnoxVpnHelper.getuserIdListForProfile(vpnProfileInfo2.getProfileName()));
        this.mFirewallHelper.removeInputFilterDropRulesForInterface(
            0, this.mKnoxVpnHelper.getuidListForProfile(vpnProfileInfo2.getProfileName()));
        if (this.mKnoxVpnHelper.isNativeVpnClient(vpnProfileInfo2.getProfileName())) {
          this.mFirewallHelper.removeIpRulesForExemptedUid(1016, 3);
        } else {
          this.mFirewallHelper.removeIpRulesForExemptedUid(vpnProfileInfo2.getVendorUid(), 3);
        }
        Iterator it2 = vpnProfileInfo2.getExemptPackageList().iterator();
        while (it2.hasNext()) {
          updateRulesToExemptUid(
              vpnProfileInfo2.getProfileName(),
              0,
              2,
              ((Integer) it2.next()).intValue(),
              3,
              null,
              null);
        }
      }
      HashSet hashSet = this.mKnoxVpnHelper.getuserIdForExemptedUids();
      Iterator it3 = hashSet.iterator();
      while (it3.hasNext()) {
        this.mFirewallHelper.removeExemptRulesForDownloadManagerUid(
            this.mKnoxVpnHelper.getUIDForPackage(
                ((Integer) it3.next()).intValue(), "com.android.providers.downloads"));
      }
      if (bundle.getBoolean("noConnectivity")) {
        Log.d("KnoxVpnEngineService", "Default Mobile Network lost connectivity");
        return;
      }
      if (bundle
          .getString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN)
          .equalsIgnoreCase("DISCONNECTED")) {
        Log.d("KnoxVpnEngineService", "Default Mobile Network is disconnected");
        return;
      }
      String activeNetworkInterface = getKnoxVpnHelperInstance().getActiveNetworkInterface();
      Log.d(
          "KnoxVpnEngineService",
          "Default Mobile Network interface Name is " + activeNetworkInterface);
      for (VpnProfileInfo vpnProfileInfo3 : profileEntries) {
        int activateState2 = vpnProfileInfo3.getActivateState();
        vpnProfileInfo3.getIpChainName();
        Iterator it4 = vpnProfileInfo3.getPackageList().iterator();
        while (true) {
          if (!it4.hasNext()) {
            break;
          }
          VpnPackageInfo vpnPackageInfo2 = (VpnPackageInfo) it4.next();
          if (this.mKnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo2)
              && activateState2 == 1
              && this.mKnoxVpnHelper.getContainerIdFromPackageName(vpnPackageInfo2.getPackageName())
                  == 0) {
            blockIncomingICMPPackets(true);
            break;
          }
        }
        if (activateState2 == 1) {
          String regularPackageName =
              this.mKnoxVpnHelper.getRegularPackageName(vpnProfileInfo3.getVendorPkgName());
          this.mFirewallHelper.addInputFilterDropRulesForInterface(
              regularPackageName,
              1,
              this.mKnoxVpnHelper.getuserIdListForProfile(vpnProfileInfo3.getProfileName()),
              activeNetworkInterface);
          this.mFirewallHelper.addInputFilterDropRulesForInterface(
              regularPackageName,
              0,
              this.mKnoxVpnHelper.getuidListForProfile(vpnProfileInfo3.getProfileName()),
              activeNetworkInterface);
          if (getChainingEnabledForProfile(vpnProfileInfo3.getVendorUid()) != 1) {
            if (this.mKnoxVpnHelper.isNativeVpnClient(vpnProfileInfo3.getProfileName())) {
              this.mFirewallHelper.addIpRulesForExemptedUid(1016, activeNetworkInterface, 3);
            } else {
              this.mFirewallHelper.addIpRulesForExemptedUid(
                  vpnProfileInfo3.getVendorUid(), activeNetworkInterface, 3);
            }
          }
          Iterator it5 = vpnProfileInfo3.getExemptPackageList().iterator();
          while (it5.hasNext()) {
            updateRulesToExemptUid(
                vpnProfileInfo3.getProfileName(),
                1,
                2,
                ((Integer) it5.next()).intValue(),
                3,
                null,
                activeNetworkInterface);
          }
        }
      }
      Iterator it6 = hashSet.iterator();
      while (it6.hasNext()) {
        this.mFirewallHelper.addExemptRulesForDownloadManagerUid(
            this.mKnoxVpnHelper.getUIDForPackage(
                ((Integer) it6.next()).intValue(), "com.android.providers.downloads"),
            activeNetworkInterface);
      }
    }
  }

  public final synchronized void handleActionEthernetChanged(Bundle bundle) {
    Collection<VpnProfileInfo> profileEntries;
    try {
      profileEntries = this.vpnConfig.getProfileEntries();
    } catch (Throwable unused) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception occured while trying to apply iptable rule for user 0 during connectivity"
              + " change of ethernet network");
    }
    if (profileEntries == null) {
      return;
    }
    if (this.vpnConfig.getProfileEntries().size() > 0) {
      boolean z = false;
      for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
        int activateState = vpnProfileInfo.getActivateState();
        Iterator it = vpnProfileInfo.getPackageList().iterator();
        while (true) {
          if (!it.hasNext()) {
            break;
          }
          VpnPackageInfo vpnPackageInfo = (VpnPackageInfo) it.next();
          if (this.mKnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)
              && activateState == 1
              && this.mKnoxVpnHelper.getContainerIdFromPackageName(vpnPackageInfo.getPackageName())
                  == 0) {
            z = true;
            break;
          }
        }
        if (z) {
          break;
        }
      }
      for (VpnProfileInfo vpnProfileInfo2 : profileEntries) {
        this.mFirewallHelper.removeInputFilterDropRulesForInterface(
            1, this.mKnoxVpnHelper.getuserIdListForProfile(vpnProfileInfo2.getProfileName()));
        this.mFirewallHelper.removeInputFilterDropRulesForInterface(
            0, this.mKnoxVpnHelper.getuidListForProfile(vpnProfileInfo2.getProfileName()));
        if (this.mKnoxVpnHelper.isNativeVpnClient(vpnProfileInfo2.getProfileName())) {
          this.mFirewallHelper.removeIpRulesForExemptedUid(1016, 3);
        } else {
          this.mFirewallHelper.removeIpRulesForExemptedUid(vpnProfileInfo2.getVendorUid(), 3);
        }
        Iterator it2 = vpnProfileInfo2.getExemptPackageList().iterator();
        while (it2.hasNext()) {
          updateRulesToExemptUid(
              vpnProfileInfo2.getProfileName(),
              0,
              2,
              ((Integer) it2.next()).intValue(),
              3,
              null,
              null);
        }
      }
      HashSet hashSet = this.mKnoxVpnHelper.getuserIdForExemptedUids();
      Iterator it3 = hashSet.iterator();
      while (it3.hasNext()) {
        this.mFirewallHelper.removeExemptRulesForDownloadManagerUid(
            this.mKnoxVpnHelper.getUIDForPackage(
                ((Integer) it3.next()).intValue(), "com.android.providers.downloads"));
      }
      if (bundle.getBoolean("noConnectivity")) {
        Log.d("KnoxVpnEngineService", "Default Ethernet Network lost connectivity");
        return;
      }
      if (bundle
          .getString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN)
          .equalsIgnoreCase("DISCONNECTED")) {
        Log.d("KnoxVpnEngineService", "Default Ethernet Network is disconnected");
        return;
      }
      String activeNetworkInterface = getKnoxVpnHelperInstance().getActiveNetworkInterface();
      Log.d(
          "KnoxVpnEngineService",
          "Default Ethernet Network interface Name is " + activeNetworkInterface);
      for (VpnProfileInfo vpnProfileInfo3 : profileEntries) {
        int activateState2 = vpnProfileInfo3.getActivateState();
        vpnProfileInfo3.getIpChainName();
        Iterator it4 = vpnProfileInfo3.getPackageList().iterator();
        while (true) {
          if (!it4.hasNext()) {
            break;
          }
          VpnPackageInfo vpnPackageInfo2 = (VpnPackageInfo) it4.next();
          if (this.mKnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo2)
              && activateState2 == 1
              && this.mKnoxVpnHelper.getContainerIdFromPackageName(vpnPackageInfo2.getPackageName())
                  == 0) {
            blockIncomingICMPPackets(true);
            break;
          }
        }
        if (activateState2 == 1) {
          String regularPackageName =
              this.mKnoxVpnHelper.getRegularPackageName(vpnProfileInfo3.getVendorPkgName());
          this.mFirewallHelper.addInputFilterDropRulesForInterface(
              regularPackageName,
              1,
              this.mKnoxVpnHelper.getuserIdListForProfile(vpnProfileInfo3.getProfileName()),
              activeNetworkInterface);
          this.mFirewallHelper.addInputFilterDropRulesForInterface(
              regularPackageName,
              0,
              this.mKnoxVpnHelper.getuidListForProfile(vpnProfileInfo3.getProfileName()),
              activeNetworkInterface);
          if (getChainingEnabledForProfile(vpnProfileInfo3.getVendorUid()) != 1) {
            if (this.mKnoxVpnHelper.isNativeVpnClient(vpnProfileInfo3.getProfileName())) {
              this.mFirewallHelper.addIpRulesForExemptedUid(1016, activeNetworkInterface, 3);
            } else {
              this.mFirewallHelper.addIpRulesForExemptedUid(
                  vpnProfileInfo3.getVendorUid(), activeNetworkInterface, 3);
            }
          }
          Iterator it5 = vpnProfileInfo3.getExemptPackageList().iterator();
          while (it5.hasNext()) {
            updateRulesToExemptUid(
                vpnProfileInfo3.getProfileName(),
                1,
                2,
                ((Integer) it5.next()).intValue(),
                3,
                null,
                activeNetworkInterface);
          }
        }
      }
      Iterator it6 = hashSet.iterator();
      while (it6.hasNext()) {
        this.mFirewallHelper.addExemptRulesForDownloadManagerUid(
            this.mKnoxVpnHelper.getUIDForPackage(
                ((Integer) it6.next()).intValue(), "com.android.providers.downloads"),
            activeNetworkInterface);
      }
    }
  }

  public final synchronized void handleConnectivityAction(Bundle bundle) {
    boolean z = bundle.getBoolean("noConnectivity");
    Log.d(
        "KnoxVpnEngineService",
        "vpn handle : connectivity action : Handle MSG CONNECTIVITY_ACTION. networkDown = " + z);
    if (!z) {
      runAllVpnService();
    }
  }

  public synchronized void removeVpnUidRanges(String str) {
    if (Binder.getCallingUid() != 1000) {
      return;
    }
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry == null) {
      return;
    }
    if (profileEntry.getRouteType() == 0) {
      return;
    }
    Log.d("KnoxVpnEngineService", "unsetting dns rules after vpn is down");
    for (VpnPackageInfo vpnPackageInfo : profileEntry.getPackageList()) {
      if (this.mKnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)) {
        int containerIdFromPackageName =
            this.mKnoxVpnHelper.getContainerIdFromPackageName(vpnPackageInfo.getPackageName());
        unsetDnsSystemProperty(
            str,
            this.mKnoxVpnHelper.startUid(containerIdFromPackageName),
            this.mKnoxVpnHelper.stopUid(containerIdFromPackageName));
      } else {
        int uid = vpnPackageInfo.getUid();
        unsetDnsSystemProperty(str, uid, uid);
      }
    }
    Log.d("KnoxVpnEngineService", "updating firewall rules after vpn is down");
    long currentTimeMillis = System.currentTimeMillis() - profileEntry.getVpnStartTime();
    KnoxAnalyticsData knoxAnalyticsData =
        new KnoxAnalyticsData("KNOX_VPN", 1, "API:removeVpnUidRanges");
    this.mData = knoxAnalyticsData;
    knoxAnalyticsData.setProperty("vpnCt", currentTimeMillis);
    this.mData.setProperty("vndrPkgN", profileEntry.getVendorPkgName());
    KnoxAnalytics.log(this.mData);
    Log.d(
        "KnoxVpnEngineService",
        "removeVpnUidRanges:defaultInterface value to which the virual tunnel was added is "
            + profileEntry.getDefaultInterface()
            + " for the profile "
            + str);
    this.mFirewallHelper.removeNatRules(
        profileEntry.getInterfaceName(), getVirtualInterfaceType(str));
    this.mFirewallHelper.removeIpRouteAndPolicyRules(
        profileEntry.getInterfaceName(), getVirtualInterfaceType(str));
    this.mFirewallHelper.removeRulesForNoUidPackets(
        profileEntry.getInterfaceName(),
        profileEntry.getInterfaceType(),
        profileEntry.getIpChainName());
    this.mFirewallHelper.removeRulesToAcceptIncomingPackets(
        profileEntry.getInterfaceType(), profileEntry.getInterfaceName());
    this.mFirewallHelper.updateDropRulesForNoUidPackets(
        1,
        profileEntry.getInterfaceName(),
        profileEntry.getInterfaceAddress(),
        profileEntry.getDefaultInterface(),
        profileEntry.getV6InterfaceAddress());
    if (profileEntry.getProxyServer() != KnoxVpnConstants.DEFAULT_PROXY_SERVER
        || profileEntry.getPacurl() != Uri.EMPTY) {
      this.mFirewallHelper.removeRulesToAcceptProxyPackets(
          profileEntry.getInterfaceType(),
          profileEntry.getInterfaceName(),
          UserHandle.getUid(profileEntry.getPersonaId(), 1002));
      if (profileEntry.getProxyInfo() != null) {
        this.mFirewallHelper.removeRulesToAllowAccessToLocalHostWithValidMark(
            profileEntry.getInterfaceName(),
            profileEntry.getProxyInfo().getPort(),
            profileEntry.getInterfaceType());
      }
      if (profileEntry.getActivateState() == 1) {
        getKnoxVpnPacProcessor().resetInterfaceName(str);
      }
    }
    if (profileEntry.getUsbTethering() == 1) {
      if (profileEntry.getActivateState() == 1) {
        Log.d(
            "KnoxVpnEngineService",
            "Applying rules to drop tether packets since vpn is down, but still in activated"
                + " state");
        String interfaceNameForUsbtethering = this.mKnoxVpnHelper.getInterfaceNameForUsbtethering();
        if (interfaceNameForUsbtethering != null) {
          this.mFirewallHelper.addRulesForDroppingTetherPackets(interfaceNameForUsbtethering);
        }
      }
      this.mFirewallHelper.removeRulesForUsbTethering(
          profileEntry.getInterfaceType(), profileEntry.getInterfaceName());
    }
    int activateState = profileEntry.getActivateState();
    if (activateState == 0) {
      this.mFirewallHelper.flushMarkingRulesForFilteredPackages(profileEntry.getIpChainName());
      removeMiscRulesForProfile(str);
    } else if (activateState == 1) {
      this.mFirewallHelper.addMarkingRulesForFilteredPackages(
          KnoxVpnConstants.BLOCK_APP_TRAFFIC, profileEntry.getIpChainName(), 3);
      updateBlockingRules(str);
    }
    resetInterfaceParameter(str);
    Log.d("KnoxVpnEngineService", "updating firewall rules after vpn down is finished");
  }

  public void setInitialTimeToTunnelEstablishment(String str, long j) {
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry == null) {
      return;
    }
    profileEntry.setVpnStartTimeToConnect(j);
  }

  public final synchronized void handleVpnInterfaceState(Bundle bundle) {
    Collection profileEntries;
    try {
      String string = bundle.getString("com.samsung.android.knox.intent.extra.ACTION_INTERNAL");
      if (string != null && string.equals("pac_info")) {
        String string2 =
            bundle.getString("com.samsung.android.knox.intent.extra.PROFILE_NAME_INTERNAL");
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(string2);
        if (profileEntry == null) {
          return;
        }
        if (profileEntry.getRouteType() == 0) {
          return;
        }
        int i = bundle.getInt("com.samsung.android.knox.intent.extra.STATE_INTERNAL");
        if (i == 0) {
          Log.e(
              "KnoxVpnEngineService",
              "The PAC Service has been disconnected for unknown reason,removing rules");
          updateProxyRules(0, string2, getKnoxVpnHelperInstance().getListOfUid(string2));
        } else if (i == 1) {
          Log.e(
              "KnoxVpnEngineService",
              "The PAC Service has been connected successfully,adding rules");
          updateProxyRules(1, string2, getKnoxVpnHelperInstance().getListOfUid(string2));
        }
      } else if (string != null && string.equals("VpnUtils_info")) {
        for (String str : this.mKnoxVpnHelper.profileListForClient("com.samsung.sVpn")) {
          removeProfileFromHashMapAndDB(str);
          IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
          if (binderInterfaceForProfile != null) {
            binderInterfaceForProfile.removeConnection(str);
          }
        }
      } else if (string != null
          && string.equals("tethering_info")
          && (profileEntries = this.vpnConfig.getProfileEntries()) != null
          && profileEntries.size() > 0) {
        Iterator it = this.vpnConfig.getProfileEntries().iterator();
        while (true) {
          if (!it.hasNext()) {
            break;
          }
          VpnProfileInfo vpnProfileInfo = (VpnProfileInfo) it.next();
          if (vpnProfileInfo.getUsbTethering() == 1) {
            int activateState = vpnProfileInfo.getActivateState();
            String interfaceName = vpnProfileInfo.getInterfaceName();
            String interfaceNameForUsbtethering =
                this.mKnoxVpnHelper.getInterfaceNameForUsbtethering();
            int usbtetherAuth = vpnProfileInfo.getUsbtetherAuth();
            if (interfaceNameForUsbtethering != null) {
              if (activateState != 1 || interfaceName == null || interfaceName.isEmpty()) {
                if (activateState == 1) {
                  Log.d(
                      "KnoxVpnEngineService",
                      "Applying rules to drop tether packets since vpn is not connected, but still"
                          + " in activated state when the usb is plugged");
                  this.mFirewallHelper.addRulesForDroppingTetherPackets(
                      interfaceNameForUsbtethering);
                }
              } else if (usbtetherAuth == 1) {
                Log.d(
                    "KnoxVpnTetherAuthentication",
                    "usb tether auth process is started after usb is plugged");
                this.mKnoxVpnTetherAuthentication.startTetherAuthProcess(
                    vpnProfileInfo.getPersonaId(),
                    interfaceNameForUsbtethering,
                    this.mKnoxVpnHelper.getIpAddressForUsbTetheringInterface());
              } else {
                this.mFirewallHelper.addRulesForUsbTethering(
                    interfaceName,
                    vpnProfileInfo.getInterfaceType(),
                    getVpnManagerService().getDnsServerListForInterface(interfaceName),
                    interfaceNameForUsbtethering,
                    this.mKnoxVpnHelper.getNetworkPartWithMask(interfaceNameForUsbtethering));
              }
            }
          }
        }
      }
    } catch (Exception e) {
      Log.d(
          "KnoxVpnEngineService",
          "handleVpnInterfaceState : Exception : " + Log.getStackTraceString(e));
    }
  }

  public final void resetInterfaceParameter(String str) {
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry != null) {
      profileEntry.setInterfaceName(null);
      profileEntry.setInterfaceAddress(null);
      profileEntry.setV6InterfaceAddress(null);
      profileEntry.setDefaultInterface(null);
      profileEntry.setInterfaceType(0);
      profileEntry.setNetId(0);
    }
  }

  public final void addExemptRulesForUid(int i, String str) {
    Log.d("KnoxVpnEngineService", "addExemptRulesForUid : vendor = " + i + " for profile " + str);
    if (getChainingEnabledForProfile(i) != 1) {
      Log.d("KnoxVpnEngineService", "addExemptRulesForUid : uid = " + i);
      if (UserHandle.getAppId(i) == 1000) {
        this.mFirewallHelper.addExemptRulesForStrongswan(i);
      } else {
        this.mFirewallHelper.addExemptRulesForUid(i);
      }
    }
  }

  public final void removeExemptRulesForUid(int i, String str) {
    Log.d(
        "KnoxVpnEngineService", "removeExemptRulesForUid : vendor = " + i + " for profile " + str);
    if (getChainingEnabledForProfile(i) != 1) {
      Log.d("KnoxVpnEngineService", "removeExemptRulesForUid : vendorUid = " + i);
      if (UserHandle.getAppId(i) == 1000) {
        this.mFirewallHelper.removeExemptRulesForStrongswan(i);
      } else {
        this.mFirewallHelper.removeExemptRulesForUid(i);
      }
    }
  }

  public final synchronized void handleActionPackageAdded(Bundle bundle) {
    int i;
    String string;
    int userId;
    boolean z;
    String personifiedName;
    boolean z2;
    String profileOwningTheUid;
    Log.d("KnoxVpnEngineService", "vpn handle : Handle MSG package add");
    try {
      i = bundle.getInt("uid");
      string = bundle.getString("package");
      userId = UserHandle.getUserId(i);
      z = bundle.getBoolean("new_install_or_update");
      personifiedName = this.mKnoxVpnHelper.getPersonifiedName(userId, string);
      z2 = DBG;
      if (z2) {
        Log.d(
            "KnoxVpnEngineService",
            "vpn handle : package added : package =  " + personifiedName + " : uid = " + i);
      }
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "vpn handle : pakcage add : Exception:" + Log.getStackTraceString(e));
    }
    if (personifiedName == null) {
      return;
    }
    if (hasVpnInterface(personifiedName)) {
      if (z2) {
        Log.d(
            "KnoxVpnEngineService",
            "vpn handle : package added : calling bind package =  " + personifiedName);
      }
      bindKnoxVpnInterface(
          personifiedName, this.mKnoxVpnHelper.getAdminIdFromPackageName(personifiedName));
      return;
    }
    if (isProxyServicePackage(userId, personifiedName)) {
      Log.d(
          "KnoxVpnEngineService",
          "Knox vpn proxy support package has been added in user " + userId);
      if (z) {
        Log.d(
            "KnoxVpnEngineService",
            "Knox vpn proxy support package has been updated in user " + userId);
        for (String str : this.vpnConfig.getProfileNames()) {
          VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
          if (profileEntry != null) {
            Uri pacurl = profileEntry.getPacurl();
            String proxyServer = profileEntry.getProxyServer();
            String interfaceName = profileEntry.getInterfaceName();
            int netId = profileEntry.getNetId();
            if (pacurl != Uri.EMPTY || proxyServer != KnoxVpnConstants.DEFAULT_PROXY_SERVER) {
              if (interfaceName != null) {
                Log.d("KnoxVpnEngineService", "Binding again to pac service after package update");
                getKnoxVpnPacProcessor().bindProxyService(str, interfaceName, pacurl, netId);
              }
            }
          }
        }
      }
      return;
    }
    if (string.equalsIgnoreCase("com.knox.vpn.proxyhandler")) {
      Log.d(
          "KnoxVpnEngineService",
          "Knox vpn proxy support package has been added on profile users " + userId);
      return;
    }
    if (string.equalsIgnoreCase("com.samsung.knox.vpn.tether.auth")
        && this.mKnoxVpnHelper.checkIfPlatformSigned(
            UserHandle.getUserId(i), "com.samsung.knox.vpn.tether.auth")) {
      if (this.mKnoxVpnTetherAuthentication.getTetherAuthStatus()) {
        Log.d(
            "KnoxVpnTetherAuthentication",
            "usb tether auth application is installed and mutual authentication not needed");
        return;
      }
      Iterator it = this.vpnConfig.getProfileEntries().iterator();
      while (true) {
        if (!it.hasNext()) {
          break;
        }
        VpnProfileInfo vpnProfileInfo = (VpnProfileInfo) it.next();
        if (vpnProfileInfo.getUsbtetherAuth() == 1
            && UserHandle.getUserId(i) == vpnProfileInfo.getPersonaId()) {
          String profileName = vpnProfileInfo.getProfileName();
          this.mKnoxVpnHelper.addOrRemoveAppsFromBatteryOptimization(
              profileName, "com.samsung.knox.vpn.tether.auth", true);
          Log.d(
              "KnoxVpnTetherAuthentication",
              "usb tether auth application is installed, start bind to usb auth service");
          this.mKnoxVpnTetherAuthentication.bindTetherAuthService(
              profileName, UserHandle.getUserId(i));
          String interfaceNameForUsbtethering =
              this.mKnoxVpnHelper.getInterfaceNameForUsbtethering();
          String interfaceName2 = vpnProfileInfo.getInterfaceName();
          int activateState = vpnProfileInfo.getActivateState();
          if (interfaceNameForUsbtethering != null) {
            if (interfaceName2 == null && activateState == 1) {
              Log.d(
                  "KnoxVpnTetherAuthentication",
                  "usb tether auth application is installed, adding blocking rules since vpn is not"
                      + " up");
              this.mFirewallHelper.addRulesForDroppingTetherPackets(interfaceNameForUsbtethering);
            } else if (interfaceName2 != null && activateState == 1) {
              Log.d(
                  "KnoxVpnTetherAuthentication",
                  "usb tether auth application is installed, start mutual authentication");
              this.mFirewallHelper.removeRulesForDroppingTethePackets();
              this.mKnoxVpnTetherAuthentication.startTetherAuthProcess(
                  vpnProfileInfo.getPersonaId(),
                  interfaceNameForUsbtethering,
                  this.mKnoxVpnHelper.getIpAddressForUsbTetheringInterface());
            }
          }
        }
      }
    }
    if (z) {
      Log.d("KnoxVpnEngineService", "vpn handle : package added: package being updated");
      int checkIfUidHasInternetPermission = this.mKnoxVpnHelper.checkIfUidHasInternetPermission(i);
      if (checkIfUidHasInternetPermission == 0) {
        String profileNameForPermissionUpdatedApp =
            this.mKnoxVpnHelper.getProfileNameForPermissionUpdatedApp(i);
        if (profileNameForPermissionUpdatedApp != null) {
          Log.d(
              "KnoxVpnEngineService",
              "The app is being upgraded with internet permission, adding to vpn profile "
                  + profileNameForPermissionUpdatedApp);
          VpnProfileInfo profileEntry2 =
              this.vpnConfig.getProfileEntry(profileNameForPermissionUpdatedApp);
          if (profileEntry2 != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(i));
            this.mFirewallHelper.addRulesForFilteredPackages(
                profileEntry2.getVendorPkgName(),
                profileEntry2.getIpChainName(),
                arrayList,
                this.mKnoxVpnHelper.getDefaultNetworkInterface(profileNameForPermissionUpdatedApp));
            this.mKnoxVpnHelper.updateUidsToVpnUidRange(profileNameForPermissionUpdatedApp);
          }
          removePackagesFromPermissionCheckDb(i);
          writePackageToDB(profileNameForPermissionUpdatedApp, string, i, userId);
          refreshDomainInHashMap(profileNameForPermissionUpdatedApp);
        }
      } else if (checkIfUidHasInternetPermission == 1
          && (profileOwningTheUid = this.mKnoxVpnHelper.getProfileOwningTheUid(i)) != null) {
        Log.d(
            "KnoxVpnEngineService",
            "The app is being downgraded without internet permission, removing from vpn profile "
                + profileOwningTheUid);
        removePackageListByUid(profileOwningTheUid, string, i);
        writePackagestoPermissionCheckDb(profileOwningTheUid, string, userId, i);
      }
      return;
    }
    if (this.mKnoxVpnHelper.isWideVpnExists(userId)) {
      Log.d(
          "KnoxVpnEngineService",
          "No action needed for package added use-case, since user-wide vpn is configured ");
      KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
      String profileOwningThePackage =
          knoxVpnHelper.getProfileOwningThePackage(
              knoxVpnHelper.getPersonifiedName(userId, "ADD_ALL_PACKAGES"));
      VpnProfileInfo profileEntry3 = this.vpnConfig.getProfileEntry(profileOwningThePackage);
      if (profileEntry3 != null && profileEntry3.getActivateState() == 1) {
        if (!this.mKnoxVpnHelper.updateExemptedListToDatabase(
            profileOwningThePackage, personifiedName, i)) {
          return;
        }
        if (!this.mKnoxVpnHelper.getuserIdForExemptedUids().contains(Integer.valueOf(userId))) {
          this.mFirewallHelper.addExemptRulesForDownloadManagerUid(
              this.mKnoxVpnHelper.getUIDForPackage(userId, "com.android.providers.downloads"),
              this.mKnoxVpnHelper.getActiveNetworkInterface());
        }
        profileEntry3.getExemptPackageList().add(Integer.valueOf(i));
        updateRulesToExemptUid(profileOwningThePackage, 1, 1, i, 0, null, null);
        updateRulesToExemptUid(
            profileOwningThePackage,
            1,
            2,
            i,
            profileEntry3.getInterfaceType(),
            profileEntry3.getInterfaceName(),
            profileEntry3.getDefaultInterface());
        updateRulesToExemptUid(
            profileOwningThePackage,
            1,
            3,
            i,
            profileEntry3.getInterfaceType(),
            profileEntry3.getInterfaceName(),
            profileEntry3.getDefaultInterface());
        this.mKnoxVpnHelper.updateUidsToVpnUidRange(profileOwningThePackage);
      }
      return;
    }
    String profileOwningThePackage2 =
        this.mKnoxVpnHelper.getProfileOwningThePackage(personifiedName);
    if (profileOwningThePackage2 != null) {
      VpnProfileInfo profileEntry4 = this.vpnConfig.getProfileEntry(profileOwningThePackage2);
      String str2 = null;
      VpnPackageInfo vpnPackageInfo =
          profileEntry4 != null ? profileEntry4.getPackage(personifiedName) : null;
      int i2 = -1;
      if (vpnPackageInfo == null) {
        Log.d(
            "KnoxVpnEngineService",
            "The application added might be an update to an existing one to the profile");
      } else if (vpnPackageInfo.getUid() == -1) {
        Log.d(
            "KnoxVpnEngineService",
            "The application added by admin but not present in the device till now has been"
                + " installed");
      } else {
        Log.d(
            "KnoxVpnEngineService",
            "The application added is already added to a vpn profile, so cancelling further calls");
        return;
      }
      String profileOwningTheUid2 = this.mKnoxVpnHelper.getProfileOwningTheUid(i);
      if (profileOwningTheUid2 != null
          && !profileOwningTheUid2.equalsIgnoreCase(profileOwningThePackage2)) {
        Log.d(
            "KnoxVpnEngineService",
            "Multiple profile exists with same uid, so cancelling adding further attempts");
        return;
      }
      if (DBG) {
        Log.d(
            "KnoxVpnEngineService",
            "vpn handle : package added : checking update package =  " + personifiedName);
      }
      if (updatePackageData(personifiedName, i)) {
        Log.d(
            "KnoxVpnEngineService",
            "vpn handle : package add : Package found in DB with rule proceed with logic");
        ArrayList dataByFields =
            this.mVpnStorageProvider.getDataByFields(
                "VpnPackageInfo",
                new String[] {"packageName"},
                new String[] {personifiedName},
                new String[] {"profileName", "packageUid"});
        if (dataByFields != null) {
          try {
            Log.d("KnoxVpnEngineService", "getting vpn object from database : Cursor not null");
            Iterator it2 = dataByFields.iterator();
            while (it2.hasNext()) {
              ContentValues contentValues = (ContentValues) it2.next();
              str2 = contentValues.getAsString("profileName");
              i2 = contentValues.getAsInteger("packageUid").intValue();
            }
          } catch (Exception e2) {
            Log.e(
                "KnoxVpnEngineService",
                "getting vpn object from database : Exception: " + Log.getStackTraceString(e2));
          }
        }
        VpnProfileInfo profileEntry5 = this.vpnConfig.getProfileEntry(str2);
        if (profileEntry5 != null) {
          profileEntry5.addPackageEntry(personifiedName, i2, userId);
        }
        if (profileEntry5.getActivateState() == 1
            && getVPNTransitionState(str2) == 4
            && this.mKnoxVpnHelper.getpackageCountByUserId(str2, userId) == 1) {
          refreshDomainInHashMap(str2);
          updateNotification(str2, userId, true);
        }
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(Integer.valueOf(i2));
        this.mKnoxVpnHelper.updateUidsToVpnUidRange(str2);
        this.mFirewallHelper.addRulesForFilteredPackages(
            profileEntry5.getVendorPkgName(),
            profileEntry5.getIpChainName(),
            arrayList2,
            this.mKnoxVpnHelper.getDefaultNetworkInterface(str2));
        startVpnForPerApplication(str2, arrayList2, true);
        if (DBG) {
          printProfileVpnMap();
        }
      }
    }
  }

  public String getVendorNameForProfile(String str) {
    String str2 = null;
    try {
      boolean z = DBG;
      if (z) {
        Log.d("KnoxVpnEngineService", "getVendorNameForProfile: profile name  " + str);
      }
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry != null) {
        str2 = this.mKnoxVpnHelper.getRegularPackageName(profileEntry.getVendorPkgName());
        if (z) {
          Log.d("KnoxVpnEngineService", "getVendorNameForProfile: vendorName  " + str2);
        }
      }
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception at getVendorNameForProfile API " + Log.getStackTraceString(e));
    }
    return str2;
  }

  public synchronized void handleActionPackageRemoved(Bundle bundle) {
    String interfaceNameForUsbtethering;
    Log.d("KnoxVpnEngineService", "handleActionPackageRemoved");
    int i = bundle.getInt("uid");
    String string = bundle.getString("package");
    String personifiedName =
        this.mKnoxVpnHelper.getPersonifiedName(UserHandle.getUserId(i), string);
    boolean z = bundle.getBoolean("new_install_or_update");
    Log.d(
        "KnoxVpnEngineService",
        "handleActionPackageRemoved : packageName = " + personifiedName + " : replacing = " + z);
    if (hasVpnInterface(personifiedName)) {
      if (z) {
        Log.d("KnoxVpnEngineService", "Package is being reinstalled. Skip remove profile");
        return;
      }
      Log.d("KnoxVpnEngineService", "handleActionPackageRemoved : packageName is Vpn Vendor");
      try {
        Log.d(
            "KnoxVpnEngineService",
            "handleActionPackageRemoved : Getting profile list for vendor from DB");
        ArrayList dataByFields =
            this.mVpnStorageProvider.getDataByFields(
                "VpnProfileInfo",
                new String[] {"vendorName"},
                new String[] {personifiedName},
                null);
        if (dataByFields != null) {
          Iterator it = dataByFields.iterator();
          while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            String asString = contentValues.getAsString("profileName");
            int intValue = contentValues.getAsInteger("activateState").intValue();
            int intValue2 = contentValues.getAsInteger("adminUid").intValue();
            String asString2 = contentValues.getAsString("vendorName");
            if (intValue == 1) {
              sendVpnConnectionFailIntent(asString2, asString, intValue2);
            }
            Log.d(
                "KnoxVpnEngineService",
                "handleActionPackageRemoved : Remvoing profile = " + asString);
            removeProfileFromKeyStore(asString, i, personifiedName);
            removeProfileFromHashMapAndDB(asString);
          }
        }
      } catch (Exception e) {
        Log.e(
            "KnoxVpnEngineService",
            "handleActionPackageRemoved : Failure at " + Log.getStackTraceString(e));
      }
      EnterpriseDeviceManager.getInstance(this.mContext);
    }
    if (z) {
      Log.d("KnoxVpnEngineService", "vpn handle : package removed: package being updated");
      return;
    }
    if (string.equalsIgnoreCase("com.samsung.knox.vpn.tether.auth")) {
      Iterator it2 = this.vpnConfig.getProfileEntries().iterator();
      while (true) {
        if (!it2.hasNext()) {
          break;
        }
        VpnProfileInfo vpnProfileInfo = (VpnProfileInfo) it2.next();
        if (vpnProfileInfo.getUsbtetherAuth() == 1
            && UserHandle.getUserId(i) == vpnProfileInfo.getPersonaId()) {
          Log.d(
              "KnoxVpnTetherAuthentication",
              "unbind from usb tethering service after usb tether app is uninstalled");
          try {
            this.mKnoxVpnTetherAuthentication.unbindTetherAuthService(true);
          } catch (RemoteException unused) {
          }
          if (vpnProfileInfo.getActivateState() == 1
              && (interfaceNameForUsbtethering =
                      this.mKnoxVpnHelper.getInterfaceNameForUsbtethering())
                  != null) {
            Log.d(
                "KnoxVpnTetherAuthentication",
                "Adding tethering blocking rules after usb tether app is uninstalled");
            this.mFirewallHelper.addRulesForDroppingTetherPackets(interfaceNameForUsbtethering);
          }
        }
      }
    }
    String profileOwningTheUid = this.mKnoxVpnHelper.getProfileOwningTheUid(i);
    if (profileOwningTheUid != null) {
      removePackageListByUid(profileOwningTheUid, string, i);
    }
    String profileNameForExemptedUid = this.mKnoxVpnHelper.getProfileNameForExemptedUid(i);
    if (profileNameForExemptedUid != null) {
      removeExemptedUidDetailsAfterUninstall(profileNameForExemptedUid, personifiedName, i);
    }
    removePackagesFromPermissionCheckDb(i);
  }

  public final synchronized void handleActionLockBootCompleted(int i) {
    VpnProfileConfig vpnProfileConfig;
    Log.d("KnoxVpnEngineService", "handleActionLockBootCompleted: user id is " + i);
    if (i == 0
        && (vpnProfileConfig = this.vpnConfig) != null
        && vpnProfileConfig.getProfileEntries().size() > 0) {
      for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
        if (vpnProfileInfo.getProxyServer() != KnoxVpnConstants.DEFAULT_PROXY_SERVER
            || vpnProfileInfo.getPacurl() != Uri.EMPTY) {
          this.mKnoxVpnHelper.addOrRemoveSystemAppFromBatteryOptimization(
              vpnProfileInfo.getProfileName(), "com.knox.vpn.proxyhandler", true);
          this.mKnoxVpnHelper.addOrRemoveSystemAppFromDataSaverWhitelist(
              vpnProfileInfo.getProfileName(),
              UserHandle.getUid(vpnProfileInfo.getPersonaId(), 1002),
              true);
        }
        if (vpnProfileInfo.getUsbtetherAuth() == 1) {
          this.mKnoxVpnHelper.addOrRemoveAppsFromBatteryOptimization(
              vpnProfileInfo.getProfileName(), "com.samsung.knox.vpn.tether.auth", true);
        }
        this.mKnoxVpnHelper.addOrRemoveAppsFromBatteryOptimization(
            vpnProfileInfo.getProfileName(),
            this.mKnoxVpnHelper.getRegularPackageName(vpnProfileInfo.getVendorPkgName()),
            true);
        this.mKnoxVpnHelper.addOrRemoveAppsFromBatteryOptimization(
            vpnProfileInfo.getProfileName(), "com.android.vpndialogs", true);
      }
      Iterator it = this.vpnConfig.getProfileEntries().iterator();
      while (true) {
        if (it.hasNext()) {
          if (((VpnProfileInfo) it.next()).getVpnConnectionType() == 1) {
            this.mProcessManager.registerProcessObserver();
            break;
          }
        } else {
          break;
        }
      }
      boolean z = false;
      for (VpnProfileInfo vpnProfileInfo2 : this.vpnConfig.getProfileEntries()) {
        int activateState = vpnProfileInfo2.getActivateState();
        Iterator it2 = vpnProfileInfo2.getPackageList().iterator();
        while (true) {
          if (!it2.hasNext()) {
            break;
          }
          VpnPackageInfo vpnPackageInfo = (VpnPackageInfo) it2.next();
          if (this.mKnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)
              && activateState == 1
              && this.mKnoxVpnHelper.getContainerIdFromPackageName(vpnPackageInfo.getPackageName())
                  == 0) {
            z = true;
            break;
          }
        }
        if (z) {
          break;
        }
      }
    }
  }

  public final synchronized void handleActionUserRemoved(Bundle bundle) {
    int i = bundle.getInt("android.intent.extra.user_handle", -10000);
    Log.d("KnoxVpnEngineService", "handleActionUserRemoved : Got USERREMOVED intent : " + i);
    try {
      this.mFirewallHelper.removeExemptRulesForDownloadManagerUid(
          this.mKnoxVpnHelper.getUIDForPackage(i, "com.android.providers.downloads"));
    } catch (Exception unused) {
    }
    this.mKnoxVpnHelper.addOrRemoveSystemAppFromDataSaverWhitelist(
        null, UserHandle.getUid(i, 1002), false);
    ConcurrentHashMap concurrentHashMap = this.mIgnoreConnectCheckForChaining;
    if (concurrentHashMap != null && concurrentHashMap.containsKey(Integer.valueOf(i))) {
      this.mIgnoreConnectCheckForChaining.remove(Integer.valueOf(i));
    }
    try {
      ArrayList dataByFields =
          this.mVpnStorageProvider.getDataByFields(
              "VpnProfileInfo",
              new String[] {"personaId"},
              new String[] {Integer.toString(i)},
              null);
      if (DBG) {
        Log.d(
            "KnoxVpnEngineService",
            "handleActionUserRemoved : #1 cvList.size() : " + dataByFields.size());
      }
      if (dataByFields != null) {
        Iterator it = dataByFields.iterator();
        while (it.hasNext()) {
          ContentValues contentValues = (ContentValues) it.next();
          contentValues.getAsInteger("adminUid").intValue();
          String asString = contentValues.getAsString("profileName");
          if (DBG) {
            Log.d("KnoxVpnEngineService", "handleActionUserRemoved : profileName = " + asString);
          }
          VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(asString);
          if (profileEntry != null && UserHandle.getUserId(profileEntry.getVendorUid()) == i) {
            removeProfileFromKeyStore(
                asString, profileEntry.getVendorUid(), profileEntry.getVendorPkgName());
            removeProfileFromHashMapAndDB(asString);
          }
        }
      }
    } catch (Exception e) {
      Log.d(
          "KnoxVpnEngineService",
          "handleActionUserRemoved : Exception : " + Log.getStackTraceString(e));
    }
    try {
      ArrayList dataByFields2 =
          this.mVpnStorageProvider.getDataByFields(
              "VpnPackageInfo",
              new String[] {"packageCid"},
              new String[] {Integer.toString(i)},
              null);
      if (DBG) {
        Log.d(
            "KnoxVpnEngineService",
            "handleActionUserRemoved : #3 cvList.size() : " + dataByFields2.size());
      }
      if (dataByFields2 != null) {
        Iterator it2 = dataByFields2.iterator();
        while (it2.hasNext()) {
          ContentValues contentValues2 = (ContentValues) it2.next();
          String asString2 = contentValues2.getAsString("profileName");
          String asString3 = contentValues2.getAsString("packageName");
          VpnProfileInfo profileEntry2 = this.vpnConfig.getProfileEntry(asString2);
          if (profileEntry2 != null) {
            int adminId = profileEntry2.getAdminId();
            boolean z = DBG;
            if (z) {
              Log.d(
                  "KnoxVpnEngineService",
                  "handleActionUserRemoved : profileName = "
                      + asString2
                      + " adminId = "
                      + adminId
                      + " transformedPackageName = "
                      + asString3);
            }
            if (z) {
              Log.d(
                  "KnoxVpnEngineService",
                  "handleActionUserRemoved : package = "
                      + this.mKnoxVpnHelper.getRegularPackageName(
                          profileEntry2.getVendorPkgName()));
            }
            KnoxVpnContext knoxVpnContext =
                new KnoxVpnContext(
                    adminId,
                    i,
                    this.mKnoxVpnHelper.getRegularPackageName(profileEntry2.getVendorPkgName()));
            if (getKnoxVpnHelperInstance().checkIfAddAllPackage(asString3)) {
              removeAllPackagesFromVpn(knoxVpnContext, asString2);
            } else {
              removePackagesFromVpn(
                  knoxVpnContext,
                  new String[] {this.mKnoxVpnHelper.getRegularPackageName(asString3)},
                  asString2);
            }
          }
        }
      }
    } catch (Exception e2) {
      Log.d(
          "KnoxVpnEngineService",
          "handleActionUserRemoved : Exception : " + Log.getStackTraceString(e2));
    }
  }

  public final void validateProfilesForVendor(String str, IKnoxVpnService iKnoxVpnService) {
    if (DBG) {
      Log.d(
          "KnoxVpnEngineService",
          "validateProfilesForVendor - vendorNameWithCid = "
              + str
              + " vpnInterface = "
              + iKnoxVpnService);
    }
    try {
      ArrayList dataByFields =
          this.mVpnStorageProvider.getDataByFields(
              "VpnProfileInfo", new String[] {"vendorName"}, new String[] {str}, null);
      if (dataByFields == null || iKnoxVpnService == null) {
        return;
      }
      Iterator it = dataByFields.iterator();
      while (it.hasNext()) {
        ContentValues contentValues = (ContentValues) it.next();
        String asString = contentValues.getAsString("profileName");
        String asString2 = contentValues.getAsString("profileJson");
        int intValue = contentValues.getAsInteger("adminUid").intValue();
        int intValue2 = contentValues.getAsInteger("activateState").intValue();
        if (DBG) {
          Log.d("KnoxVpnEngineService", "validateProfilesForVendor : profileName = " + asString);
        }
        if (iKnoxVpnService.getConnection(asString) == null) {
          VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(asString);
          removeProfileFromKeyStore(
              asString, profileEntry.getVendorUid(), profileEntry.getVendorPkgName());
          iKnoxVpnService.createConnection(asString2);
          if (iKnoxVpnService.getConnection(asString) == null) {
            if (intValue2 == 1) {
              sendVpnConnectionFailIntent(str, asString, intValue);
            }
            Log.d("KnoxVpnEngineService", "JsonProfile null and recreate connection fail");
            removeProfileFromHashMapAndDB(asString);
          }
        }
      }
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "validateProfilesForVendor : Failure at " + Log.getStackTraceString(e));
    }
  }

  public final void addMiscRulesForProfile(String str) {
    Log.d("KnoxVpnEngineService", "addMiscRulesForProfile : profileName =  " + str);
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    ArrayList arrayList = new ArrayList();
    if (profileEntry == null || profileEntry.getActivateState() == 0) {
      return;
    }
    String interfaceName = profileEntry.getInterfaceName();
    for (VpnPackageInfo vpnPackageInfo : profileEntry.getPackageList()) {
      if (this.mKnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)) {
        this.mFirewallHelper.addMiscRulesRange(
            this.mKnoxVpnHelper.getContainerIdFromPackageName(vpnPackageInfo.getPackageName()),
            interfaceName,
            getVirtualInterfaceType(str));
      } else {
        arrayList.add(Integer.valueOf(vpnPackageInfo.getUid()));
      }
    }
    if (arrayList.isEmpty()) {
      return;
    }
    this.mFirewallHelper.addMiscRules(arrayList, interfaceName, getVirtualInterfaceType(str));
  }

  /* JADX WARN: Removed duplicated region for block: B:34:0x00b0 A[Catch: all -> 0x02ec, LOOP:0: B:32:0x00aa->B:34:0x00b0, LOOP_END, TryCatch #2 {, blocks: (B:4:0x000d, B:10:0x0017, B:14:0x0021, B:18:0x0029, B:22:0x0054, B:26:0x005e, B:31:0x007c, B:32:0x00aa, B:34:0x00b0, B:36:0x00de, B:38:0x00ec, B:40:0x00f6, B:41:0x0100, B:85:0x0106, B:43:0x010b, B:44:0x012a, B:46:0x0130, B:53:0x013e, B:49:0x0160, B:56:0x0171, B:73:0x01d3, B:76:0x01e1, B:78:0x01e9, B:79:0x020b, B:80:0x0223, B:58:0x023c, B:60:0x0244, B:62:0x029a, B:64:0x02a1, B:65:0x02a8, B:68:0x024c, B:70:0x0261, B:71:0x026d, B:88:0x00fb, B:94:0x0038, B:97:0x02e3, B:98:0x02e6, B:99:0x02eb), top: B:3:0x000d }] */
  /* JADX WARN: Removed duplicated region for block: B:46:0x0130 A[Catch: all -> 0x02ec, TryCatch #2 {, blocks: (B:4:0x000d, B:10:0x0017, B:14:0x0021, B:18:0x0029, B:22:0x0054, B:26:0x005e, B:31:0x007c, B:32:0x00aa, B:34:0x00b0, B:36:0x00de, B:38:0x00ec, B:40:0x00f6, B:41:0x0100, B:85:0x0106, B:43:0x010b, B:44:0x012a, B:46:0x0130, B:53:0x013e, B:49:0x0160, B:56:0x0171, B:73:0x01d3, B:76:0x01e1, B:78:0x01e9, B:79:0x020b, B:80:0x0223, B:58:0x023c, B:60:0x0244, B:62:0x029a, B:64:0x02a1, B:65:0x02a8, B:68:0x024c, B:70:0x0261, B:71:0x026d, B:88:0x00fb, B:94:0x0038, B:97:0x02e3, B:98:0x02e6, B:99:0x02eb), top: B:3:0x000d }] */
  /* JADX WARN: Removed duplicated region for block: B:64:0x02a1 A[Catch: all -> 0x02ec, TryCatch #2 {, blocks: (B:4:0x000d, B:10:0x0017, B:14:0x0021, B:18:0x0029, B:22:0x0054, B:26:0x005e, B:31:0x007c, B:32:0x00aa, B:34:0x00b0, B:36:0x00de, B:38:0x00ec, B:40:0x00f6, B:41:0x0100, B:85:0x0106, B:43:0x010b, B:44:0x012a, B:46:0x0130, B:53:0x013e, B:49:0x0160, B:56:0x0171, B:73:0x01d3, B:76:0x01e1, B:78:0x01e9, B:79:0x020b, B:80:0x0223, B:58:0x023c, B:60:0x0244, B:62:0x029a, B:64:0x02a1, B:65:0x02a8, B:68:0x024c, B:70:0x0261, B:71:0x026d, B:88:0x00fb, B:94:0x0038, B:97:0x02e3, B:98:0x02e6, B:99:0x02eb), top: B:3:0x000d }] */
  /* JADX WARN: Removed duplicated region for block: B:70:0x0261 A[Catch: all -> 0x02ec, TryCatch #2 {, blocks: (B:4:0x000d, B:10:0x0017, B:14:0x0021, B:18:0x0029, B:22:0x0054, B:26:0x005e, B:31:0x007c, B:32:0x00aa, B:34:0x00b0, B:36:0x00de, B:38:0x00ec, B:40:0x00f6, B:41:0x0100, B:85:0x0106, B:43:0x010b, B:44:0x012a, B:46:0x0130, B:53:0x013e, B:49:0x0160, B:56:0x0171, B:73:0x01d3, B:76:0x01e1, B:78:0x01e9, B:79:0x020b, B:80:0x0223, B:58:0x023c, B:60:0x0244, B:62:0x029a, B:64:0x02a1, B:65:0x02a8, B:68:0x024c, B:70:0x0261, B:71:0x026d, B:88:0x00fb, B:94:0x0038, B:97:0x02e3, B:98:0x02e6, B:99:0x02eb), top: B:3:0x000d }] */
  /* JADX WARN: Removed duplicated region for block: B:71:0x026d A[Catch: all -> 0x02ec, TryCatch #2 {, blocks: (B:4:0x000d, B:10:0x0017, B:14:0x0021, B:18:0x0029, B:22:0x0054, B:26:0x005e, B:31:0x007c, B:32:0x00aa, B:34:0x00b0, B:36:0x00de, B:38:0x00ec, B:40:0x00f6, B:41:0x0100, B:85:0x0106, B:43:0x010b, B:44:0x012a, B:46:0x0130, B:53:0x013e, B:49:0x0160, B:56:0x0171, B:73:0x01d3, B:76:0x01e1, B:78:0x01e9, B:79:0x020b, B:80:0x0223, B:58:0x023c, B:60:0x0244, B:62:0x029a, B:64:0x02a1, B:65:0x02a8, B:68:0x024c, B:70:0x0261, B:71:0x026d, B:88:0x00fb, B:94:0x0038, B:97:0x02e3, B:98:0x02e6, B:99:0x02eb), top: B:3:0x000d }] */
  /* JADX WARN: Removed duplicated region for block: B:72:0x01d3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:84:0x0106 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public synchronized void addVpnUidRanges(
      String str, int i, String str2, String str3, String str4) {
    int i2;
    int vendorUid;
    Iterator it;
    int i3;
    int i4;
    int i5;
    if (Binder.getCallingUid() != 1000) {
      return;
    }
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry == null) {
      return;
    }
    if (profileEntry.getRouteType() == 0) {
      return;
    }
    int activateState = profileEntry.getActivateState();
    int packageCount = profileEntry.getPackageCount();
    if (activateState == 0 || (packageCount <= 0 && activateState == 1)) {
      Log.d(
          "KnoxVpnEngineService",
          "Applying firewall rules after vpn is up is stopped for the profile " + str);
      IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
      if (binderInterfaceForProfile != null) {
        try {
          binderInterfaceForProfile.stopConnection(str);
        } catch (RemoteException unused) {
        }
        throw new IllegalStateException();
      }
    }
    String activeNetworkInterface = this.mKnoxVpnHelper.getActiveNetworkInterface();
    if (activeNetworkInterface == null) {
      return;
    }
    profileEntry.setDefaultInterface(activeNetworkInterface);
    profileEntry.setInterfaceName(str2);
    profileEntry.setInterfaceAddress(str3);
    profileEntry.setV6InterfaceAddress(str4);
    profileEntry.setNetId(i);
    if (str3 == null || str4 == null) {
      i5 = (str3 == null && str4 != null) ? 2 : 3;
      i2 = 1;
      profileEntry.setInterfaceType(i2);
      String ipChainName = profileEntry.getIpChainName();
      vendorUid = profileEntry.getVendorUid();
      String vendorPkgName = profileEntry.getVendorPkgName();
      Log.d("KnoxVpnEngineService", "start applying exempt rules for knox vpn profile " + str);
      it = profileEntry.getExemptPackageList().iterator();
      while (it.hasNext()) {
        String str5 = activeNetworkInterface;
        updateRulesToExemptUid(str, 1, 3, ((Integer) it.next()).intValue(), i2, str2, str5);
        i2 = i2;
        activeNetworkInterface = str5;
        vendorPkgName = vendorPkgName;
        ipChainName = ipChainName;
      }
      String str6 = vendorPkgName;
      String str7 = ipChainName;
      i3 = i2;
      String str8 = activeNetworkInterface;
      if (UserHandle.getAppId(vendorUid) != 1000 && str6.contains("com.samsung.sVpn")) {
        i4 = 1;
        profileEntry.setVpnType(1);
      } else {
        i4 = 1;
        profileEntry.setVpnType(0);
      }
      if (profileEntry.getVpnType() == i4) {
        try {
          this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
        } catch (RemoteException unused2) {
        }
      }
      Log.d("KnoxVpnEngineService", "start applying dns rules for knox vpn profile " + str);
      for (VpnPackageInfo vpnPackageInfo : profileEntry.getPackageList()) {
        if (this.mKnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)) {
          int containerIdFromPackageName =
              this.mKnoxVpnHelper.getContainerIdFromPackageName(vpnPackageInfo.getPackageName());
          setDnsSystemProperty(
              str,
              this.mKnoxVpnHelper.startUid(containerIdFromPackageName),
              this.mKnoxVpnHelper.stopUid(containerIdFromPackageName),
              str2,
              i);
        } else {
          int uid = vpnPackageInfo.getUid();
          setDnsSystemProperty(str, uid, uid, str2, i);
        }
      }
      Log.d(
          "KnoxVpnEngineService",
          "addVpnUidRanges:defaultInterface value to which the virual tunnel is added is "
              + str8
              + " for the profile "
              + str
              + " with interface type "
              + i3
              + " netId "
              + i);
      this.mFirewallHelper.addNatRules(str2, i3);
      this.mFirewallHelper.addIpRouteAndPolicyRules(str2, i3);
      this.mFirewallHelper.addRulesForNoUidPackets(str7, str2, i3);
      this.mFirewallHelper.addMarkingRulesForFilteredPackages(str2, str7, i3);
      addMiscRulesForProfile(str);
      this.mFirewallHelper.addRulesToAcceptIncomingPackets(i3, str2);
      this.mFirewallHelper.updateDropRulesForNoUidPackets(0, str2, str3, str8, str4);
      if (profileEntry.getUsbTethering() == i4) {
        try {
          String interfaceNameForUsbtethering =
              this.mKnoxVpnHelper.getInterfaceNameForUsbtethering();
          int usbtetherAuth = profileEntry.getUsbtetherAuth();
          if (interfaceNameForUsbtethering != null) {
            if (usbtetherAuth == i4) {
              if (this.mKnoxVpnTetherAuthentication.getTetherAuthStatus()) {
                Log.d(
                    "KnoxVpnTetherAuthentication",
                    "vpn is up and and if mutual authentication is already validated");
                this.mFirewallHelper.addRulesForUsbTethering(
                    str2,
                    getVirtualInterfaceType(str),
                    getVpnManagerService().getDnsServerListForInterface(str2),
                    interfaceNameForUsbtethering,
                    this.mKnoxVpnHelper.getNetworkPartWithMask(interfaceNameForUsbtethering));
              } else {
                Log.d(
                    "KnoxVpnTetherAuthentication",
                    "vpn is up and if mutual authentication is going to be validated");
                this.mKnoxVpnTetherAuthentication.startTetherAuthProcess(
                    profileEntry.getPersonaId(),
                    interfaceNameForUsbtethering,
                    this.mKnoxVpnHelper.getIpAddressForUsbTetheringInterface());
              }
            } else {
              this.mFirewallHelper.addRulesForUsbTethering(
                  str2,
                  getVirtualInterfaceType(str),
                  getVpnManagerService().getDnsServerListForInterface(str2),
                  interfaceNameForUsbtethering,
                  this.mKnoxVpnHelper.getNetworkPartWithMask(interfaceNameForUsbtethering));
            }
          }
        } catch (RemoteException unused3) {
        }
      }
      if (profileEntry.getProxyServer() == KnoxVpnConstants.DEFAULT_PROXY_SERVER
          || profileEntry.getPacurl() != Uri.EMPTY) {
        this.mFirewallHelper.addRulesToAcceptProxyPackets(
            i3, str2, UserHandle.getUid(profileEntry.getPersonaId(), 1002));
        if (profileEntry.getProxyInfo() != null) {
          getKnoxVpnPacProcessor().bindProxyService(str, str2, profileEntry.getPacurl(), i);
        } else {
          getKnoxVpnPacProcessor().setCurrentProxyScript(str, str2, profileEntry.getPacurl());
          getKnoxVpnPacProcessor().setMiscValueForPacProfile(str, str2, i);
          this.mFirewallHelper.removeRulesToAllowAccessToLocalHostWithValidMark(
              null, profileEntry.getProxyInfo().getPort(), i3);
          this.mFirewallHelper.addRulesToAllowAccessToLocalHostWithValidMark(
              str2, profileEntry.getProxyInfo().getPort(), i3);
        }
      }
      handleChainingScenarioForStartConnection(str);
      if (DBG) {
        Log.d("KnoxVpnEngineService", "Applying Knox VPN Analytics rules after vpn is up");
      }
      profileEntry.setVpnStartTime(System.currentTimeMillis());
      long currentTimeMillis = System.currentTimeMillis() - profileEntry.getVpnStartTimeToConnect();
      KnoxAnalyticsData knoxAnalyticsData =
          new KnoxAnalyticsData("KNOX_VPN", i4, "API:addVpnUidRanges");
      this.mData = knoxAnalyticsData;
      knoxAnalyticsData.setProperty("vpnEt", currentTimeMillis);
      this.mData.setProperty("vndrPkgN", profileEntry.getVendorPkgName());
      KnoxAnalytics.log(this.mData);
      Log.d("KnoxVpnEngineService", "Applying firewall rules after vpn is up is finished");
    }
    i2 = i5;
    profileEntry.setInterfaceType(i2);
    String ipChainName2 = profileEntry.getIpChainName();
    vendorUid = profileEntry.getVendorUid();
    String vendorPkgName2 = profileEntry.getVendorPkgName();
    Log.d("KnoxVpnEngineService", "start applying exempt rules for knox vpn profile " + str);
    it = profileEntry.getExemptPackageList().iterator();
    while (it.hasNext()) {}
    String str62 = vendorPkgName2;
    String str72 = ipChainName2;
    i3 = i2;
    String str82 = activeNetworkInterface;
    if (UserHandle.getAppId(vendorUid) != 1000) {}
    i4 = 1;
    profileEntry.setVpnType(0);
    if (profileEntry.getVpnType() == i4) {}
    Log.d("KnoxVpnEngineService", "start applying dns rules for knox vpn profile " + str);
    while (r8.hasNext()) {}
    Log.d(
        "KnoxVpnEngineService",
        "addVpnUidRanges:defaultInterface value to which the virual tunnel is added is "
            + str82
            + " for the profile "
            + str
            + " with interface type "
            + i3
            + " netId "
            + i);
    this.mFirewallHelper.addNatRules(str2, i3);
    this.mFirewallHelper.addIpRouteAndPolicyRules(str2, i3);
    this.mFirewallHelper.addRulesForNoUidPackets(str72, str2, i3);
    this.mFirewallHelper.addMarkingRulesForFilteredPackages(str2, str72, i3);
    addMiscRulesForProfile(str);
    this.mFirewallHelper.addRulesToAcceptIncomingPackets(i3, str2);
    this.mFirewallHelper.updateDropRulesForNoUidPackets(0, str2, str3, str82, str4);
    if (profileEntry.getUsbTethering() == i4) {}
    if (profileEntry.getProxyServer() == KnoxVpnConstants.DEFAULT_PROXY_SERVER) {}
    this.mFirewallHelper.addRulesToAcceptProxyPackets(
        i3, str2, UserHandle.getUid(profileEntry.getPersonaId(), 1002));
    if (profileEntry.getProxyInfo() != null) {}
    handleChainingScenarioForStartConnection(str);
    if (DBG) {}
    profileEntry.setVpnStartTime(System.currentTimeMillis());
    long currentTimeMillis2 = System.currentTimeMillis() - profileEntry.getVpnStartTimeToConnect();
    KnoxAnalyticsData knoxAnalyticsData2 =
        new KnoxAnalyticsData("KNOX_VPN", i4, "API:addVpnUidRanges");
    this.mData = knoxAnalyticsData2;
    knoxAnalyticsData2.setProperty("vpnEt", currentTimeMillis2);
    this.mData.setProperty("vndrPkgN", profileEntry.getVendorPkgName());
    KnoxAnalytics.log(this.mData);
    Log.d("KnoxVpnEngineService", "Applying firewall rules after vpn is up is finished");
  }

  public final void removeMiscRulesForProfile(String str) {
    Log.d("KnoxVpnEngineService", "removeMiscRulesForProfile : profileName =  " + str);
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    ArrayList arrayList = new ArrayList();
    if (profileEntry != null) {
      String interfaceName = profileEntry.getInterfaceName();
      for (VpnPackageInfo vpnPackageInfo : profileEntry.getPackageList()) {
        if (this.mKnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)) {
          this.mFirewallHelper.removeMiscRulesRange(
              this.mKnoxVpnHelper.getContainerIdFromPackageName(vpnPackageInfo.getPackageName()),
              interfaceName,
              getVirtualInterfaceType(str));
        } else {
          arrayList.add(Integer.valueOf(vpnPackageInfo.getUid()));
        }
      }
      if (arrayList.isEmpty()) {
        return;
      }
      this.mFirewallHelper.removeMiscRules(
          arrayList, profileEntry.getInterfaceName(), getVirtualInterfaceType(str));
    }
  }

  public void updateBlockingRules(String str) {
    ArrayList arrayList = new ArrayList();
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry == null) {
      return;
    }
    String interfaceName = profileEntry.getInterfaceName();
    for (VpnPackageInfo vpnPackageInfo : profileEntry.getPackageList()) {
      if (this.mKnoxVpnHelper.isPackageForAddAllPackages(vpnPackageInfo)) {
        this.mFirewallHelper.removeMiscRulesRange(
            this.mKnoxVpnHelper.getContainerIdFromPackageName(vpnPackageInfo.getPackageName()),
            interfaceName,
            getVirtualInterfaceType(str));
      } else {
        arrayList.add(Integer.valueOf(vpnPackageInfo.getUid()));
      }
    }
    if (!arrayList.isEmpty()) {
      this.mFirewallHelper.removeMiscRules(arrayList, interfaceName, getVirtualInterfaceType(str));
    }
    profileEntry.setInterfaceName(null);
    profileEntry.setNetId(0);
    addMiscRulesForProfile(str);
  }

  public final synchronized int startVpnProfile(String str) {
    boolean z = DBG;
    if (z) {
      Log.d("KnoxVpnEngineService", "startVpnProfile : profileName value is " + str);
    }
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry == null) {
      return 108;
    }
    int i = -1;
    try {
    } catch (Exception e) {
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Exception trying to start vpn profile %s", str),
          profileEntry.getPersonaId());
      Log.e("KnoxVpnEngineService", "startVpnProfile : Exception : " + Log.getStackTraceString(e));
    }
    if (profileEntry.getActivateState() == 0) {
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Error trying to start vpn profile %s. Profile is not activated", str),
          profileEntry.getPersonaId());
      return 301;
    }
    if (!isNetworkConnected()) {
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Error trying to start vpn profile %s. Network is not available", str),
          profileEntry.getPersonaId());
      return FrameworkStatsLog.f85x3da64833;
    }
    boolean z2 = true;
    if (this.mKnoxVpnHelper.checkIfProfileHasChainingFeature(str) == 1
        && checkChainingStatus(str) == 0) {
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          "Error trying to start vpn profile. Chaining is not ready",
          profileEntry.getPersonaId());
      return EndpointMonitorConst.TRACE_EVENT_SCHED_CLS_EGRESS;
    }
    if (profileEntry.getPackageCount() <= 0) {
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Vpn connection not established. No package added to profile %s", str),
          profileEntry.getPersonaId());
      return FrameworkStatsLog.f63x1c64b730;
    }
    if (getVPNTransitionState(str) == 4) {
      return 0;
    }
    IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
    if (binderInterfaceForProfile != null) {
      int vPNTransitionState = getVPNTransitionState(str);
      int activateState = profileEntry.getActivateState();
      int vpnConnectionType = profileEntry.getVpnConnectionType();
      boolean z3 =
          !(vPNTransitionState == 1 || vPNTransitionState == 5 || vPNTransitionState == -1)
              || activateState == 0;
      boolean z4 = vpnConnectionType == 0;
      boolean processRunCheck =
          vpnConnectionType == 1 ? this.mProcessManager.processRunCheck(profileEntry) : false;
      boolean z5 = vpnConnectionType == 1 && processRunCheck;
      if (vpnConnectionType != 1 || processRunCheck) {
        z2 = false;
      }
      if (z3) {
        Log.d(
            "KnoxVpnEngineService",
            "not allowd state for starting a vpn : profileName = "
                + str
                + ": state = "
                + vPNTransitionState);
        return 305;
      }
      if (z) {
        Log.d(
            "KnoxVpnEngineService",
            "startVpnProfile : keepOn =  " + z4 + " / startOnDemand = " + z5);
      }
      if (!z4 && !z5) {
        if (z2) {
          i = FrameworkStatsLog.f111x2b18bc4b;
        }
      }
      i = binderInterfaceForProfile.startConnection(str);
      Log.d("KnoxVpnEngineService", "startVpnProfile: start result : " + i);
      if (i != 0) {
        AuditLog.logAsUser(
            3,
            5,
            false,
            Process.myPid(),
            "KnoxVpnEngineService",
            String.format("Error trying to start vpn connection for profile %s", str),
            profileEntry.getPersonaId());
        return 102;
      }
      AuditLog.logAsUser(
          5,
          5,
          true,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format("Connection established for vpn profile %s", str),
          profileEntry.getPersonaId());
    }
    return i;
  }

  public static UidRangeParcel[] toUidRangeStableParcels(Set set) {
    UidRangeParcel[] uidRangeParcelArr = new UidRangeParcel[set.size()];
    Iterator it = set.iterator();
    int i = 0;
    while (it.hasNext()) {
      UidRangeParcel uidRangeParcel = (UidRangeParcel) it.next();
      uidRangeParcelArr[i] = new UidRangeParcel(uidRangeParcel.start, uidRangeParcel.stop);
      i++;
    }
    return uidRangeParcelArr;
  }

  public final void setDnsSystemProperty(String str, int i, int i2, String str2, int i3) {
    if (str != null) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        try {
          if (this.vpnConfig.getProfileEntry(str) != null) {
            if (DBG) {
              Log.d(
                  "KnoxVpnEngineService",
                  "setDnsSystemProperty is reached : whose profileName is "
                      + str
                      + "whose start uid is "
                      + i
                      + " whose stop uid is "
                      + i2
                      + " whose interface "
                      + str2
                      + " whose netId is "
                      + i3);
            }
            ArraySet arraySet = new ArraySet();
            arraySet.add(new UidRangeParcel(i, i2));
            getOemNetdService().networkAddUidRanges(i3, toUidRangeStableParcels(arraySet));
          }
        } catch (Exception unused) {
          Log.e(
              "KnoxVpnEngineService",
              "Exception occured while trying to set the dns entry for the profile " + str);
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public final void unsetDnsSystemProperty(String str, int i, int i2) {
    if (str != null) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        try {
          VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
          if (profileEntry != null && profileEntry.getInterfaceName() != null) {
            boolean z = DBG;
            if (z) {
              Log.d("KnoxVpnEngineService", "unsetDnsSystemProperty: interface name is not null");
            }
            String interfaceName = profileEntry.getInterfaceName();
            int netId = profileEntry.getNetId();
            if (netId == 0) {
              return;
            }
            if (z) {
              Log.d(
                  "KnoxVpnEngineService",
                  "unsetDnsSystemProperty is reached : whose profileName is "
                      + str
                      + "whose start uid is "
                      + i
                      + " whose stop uid is "
                      + i2
                      + " whose interface "
                      + interfaceName
                      + " whose netId is "
                      + netId);
            }
            ArraySet arraySet = new ArraySet();
            arraySet.add(new UidRangeParcel(i, i2));
            getOemNetdService().networkRemoveUidRanges(netId, toUidRangeStableParcels(arraySet));
          }
        } catch (Exception unused) {
          Log.e(
              "KnoxVpnEngineService",
              "Exception occured while trying to unset the dns entry for the profile " + str);
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public final void removeUidFromExemptList(String str, String str2, int i, int i2) {
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry == null) {
      return;
    }
    int netId = profileEntry.getNetId();
    Log.d(
        "KnoxVpnEngineService",
        "The following app removed from the interface "
            + str2
            + "will not go through vpn since it was blacklisted "
            + i);
    if (getNetworkManagementService() != null) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        try {
          ArrayList arrayList = new ArrayList();
          arrayList.add(new UidRangeParcel(i, i2));
          getOemNetdService()
              .knoxVpnRemoveExemptUidFromKnoxVpn(
                  netId,
                  (UidRangeParcel[]) arrayList.toArray(new UidRangeParcel[arrayList.size()]));
        } catch (Exception unused) {
          Log.e(
              "KnoxVpnEngineService",
              "Exception occured while trying to remove Uid From Vpn List ");
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public final void addUidToExemptList(String str, String str2, int i, int i2) {
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry == null) {
      return;
    }
    int netId = profileEntry.getNetId();
    Log.d(
        "KnoxVpnEngineService",
        "The following app added to the interface "
            + str2
            + "will not go through vpn since it was blacklisted "
            + i);
    if (getNetworkManagementService() != null) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        try {
          ArrayList arrayList = new ArrayList();
          arrayList.add(new UidRangeParcel(i, i2));
          getOemNetdService()
              .knoxVpnExemptUidFromKnoxVpn(
                  netId,
                  (UidRangeParcel[]) arrayList.toArray(new UidRangeParcel[arrayList.size()]));
        } catch (Exception unused) {
          Log.e("KnoxVpnEngineService", "Exception occured while trying to add Uid From Vpn List ");
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void allowAppsToMakeDnsQueryForNetId() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        getOemNetdService()
            .knoxVpnInsertUidForDnsAuthorization(
                new int[] {
                  this.mKnoxVpnHelper.getUIDForPackage(0, "com.android.providers.downloads")
                });
        getOemNetdService().knoxVpnInsertUidForDnsAuthorization(new int[] {1002});
        getOemNetdService().knoxVpnInsertUidForDnsAuthorization(new int[] {1000});
      } catch (Exception unused) {
        Log.e("KnoxVpnEngineService", "Error at allowAppsToMakeDnsQueryForNetId");
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void denyAppsToMakeDnsQueryForNetId() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        getOemNetdService().knoxVpnRemoveUidFromDnsAuthorization();
      } catch (Exception unused) {
        Log.e("KnoxVpnEngineService", "Error at denyAppsToMakeDnsQueryForNetId");
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final ContextInfo checkCallingUidPermission(KnoxVpnContext knoxVpnContext) {
    ArrayList arrayList =
        new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_VPN_GENERIC"));
    return getEnterpriseDeviceManager()
        .enforceActiveAdminPermissionByContext(
            new ContextInfo(knoxVpnContext.adminId, knoxVpnContext.personaId), arrayList);
  }

  public final boolean checkIfLegacyUser(int i) {
    boolean z = false;
    try {
      if (SemPersonaManager.isKnoxId(i)) {
        if (SemPersonaManager.isSecureFolderId(i)) {
          z = true;
        }
      }
    } catch (Exception unused) {
    }
    if (DBG) {
      Log.d("KnoxVpnEngineService", "check to see userId type " + i + " " + z);
    }
    return z;
  }

  public final boolean updateIfNonLegacyUserAndCheckIfVendorAllowed(
      KnoxVpnContext knoxVpnContext, int i) {
    if (!checkIfLegacyUser(knoxVpnContext.personaId)) {
      knoxVpnContext.adminId = i;
    }
    return checkIfVendorAllowed(knoxVpnContext);
  }

  public final boolean checkIfVendorAllowed(KnoxVpnContext knoxVpnContext) {
    if (!"com.android.settings".equals(knoxVpnContext.vendorName)) {
      return true;
    }
    StackTraceElement[] stackTrace = new Throwable().getStackTrace();
    if (stackTrace != null
        && stackTrace.length > 2
        && isMethodAllowedForVendorSettings(stackTrace[2].getMethodName())) {
      Log.d("KnoxVpnEngineService", "checkIfVendorAllowed: settings vendor allowed");
      return true;
    }
    Log.d("KnoxVpnEngineService", "checkIfVendorAllowed: settings vendor not allowed");
    return false;
  }

  public final boolean isMethodAllowedForVendorSettings(String str) {
    for (String str2 : KnoxVpnPolicyConstants.ALLOWED_METHODS_VENDOR_SETTINGS) {
      if (str2.equalsIgnoreCase(str)) {
        return true;
      }
    }
    return false;
  }

  public final int validateAdminAndVendorForProfile(
      KnoxVpnContext knoxVpnContext, String str, EnterpriseResponseData enterpriseResponseData) {
    int i = knoxVpnContext.adminId;
    String str2 = knoxVpnContext.vendorName;
    this.mKnoxVpnHelper.getPersonifiedName(knoxVpnContext.personaId, str2);
    if (str == null || str2 == null || enterpriseResponseData == null) {
      Log.d("KnoxVpnEngineService", "Admin check null for profile : " + str + " :admin = " + i);
      if (enterpriseResponseData != null) {
        enterpriseResponseData.setStatus(1, 7);
      }
      return -1;
    }
    if (this.vpnConfig.checkAdminForProfile(i, str) == 0) {
      return 0;
    }
    Log.d("KnoxVpnEngineService", "Admin check failed for profile : " + str + " :admin = " + i);
    enterpriseResponseData.setStatus(1, 8);
    return 1;
  }

  public IKnoxVpnService getBinderInterfaceForProfile(String str) {
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry == null) {
      AuditLog.logAsUser(
          3,
          5,
          false,
          Process.myPid(),
          "KnoxVpnEngineService",
          String.format(
              "Error getting binder for profile %s. Vendor service might not be running", str),
          -1);
      Log.e("KnoxVpnEngineService", "get binder for profile : Profile does not exist : " + str);
      return null;
    }
    return getVpnInterface(profileEntry.getVendorPkgName());
  }

  public IKnoxVpnService getVpnInterface(String str) {
    if (str == null) {
      return null;
    }
    try {
      Object obj = this.vpnInterfaceMap.get(str);
      if (obj != NULL_OBJECT) {
        return (IKnoxVpnService) obj;
      }
      return null;
    } catch (Exception e) {
      Log.d(
          "KnoxVpnEngineService",
          "Exception when retrieving Binder interface : " + Log.getStackTraceString(e));
      return null;
    }
  }

  public final void setVpnInterface(String str, Object obj) {
    if (DBG) {
      Log.d(
          "KnoxVpnEngineService",
          "setVpnInterface: vendorName value is " + str + "vpnInterface value is " + obj);
    }
    if (obj == null) {
      obj = NULL_OBJECT;
    }
    this.vpnInterfaceMap.put(str, obj);
  }

  public final boolean hasVpnInterface(String str) {
    return this.vpnInterfaceMap.containsKey(str);
  }

  public final boolean isProxyServicePackage(int i, String str) {
    return str.equalsIgnoreCase(
        this.mKnoxVpnHelper.getPersonifiedName(0, "com.knox.vpn.proxyhandler"));
  }

  public final int getActivate(String str) {
    try {
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry == null) {
        return -1;
      }
      return profileEntry.getActivateState();
    } catch (Exception unused) {
      Log.e("KnoxVpnEngineService", "getActivate error");
      return -1;
    }
  }

  public final boolean setActivate(String str, int i) {
    VpnProfileInfo profileEntry;
    Log.d(
        "KnoxVpnEngineService",
        "setActivate: profileName value is " + str + "activateState value is " + i);
    boolean z = false;
    try {
      profileEntry = this.vpnConfig.getProfileEntry(str);
    } catch (Exception e) {
      if (DBG) {
        Log.e(
            "KnoxVpnEngineService",
            "Exception occured while storing activateState info in db "
                + Log.getStackTraceString(e));
      }
    }
    if (profileEntry == null) {
      return false;
    }
    int activateState = profileEntry.getActivateState();
    ContentValues contentValues = new ContentValues();
    contentValues.put("activateState", Integer.valueOf(i));
    if (this.mVpnStorageProvider.putDataByFields(
        "VpnProfileInfo", new String[] {"profileName"}, new String[] {str}, contentValues)) {
      profileEntry.setActivateState(i);
      z = true;
    } else {
      profileEntry.setActivateState(activateState);
    }
    Log.d("KnoxVpnEngineService", "setActivate: isActivateInfoSaved value is " + z);
    return z;
  }

  public final synchronized int getVPNTransitionState(String str) {
    int vPNState = getVPNState(str);
    if (vPNState < 0) {
      return -1;
    }
    if (5 < vPNState) {
      return -1;
    }
    int activate = getActivate(str);
    if (activate < 0) {
      return -1;
    }
    if (activate == 0) {
      vPNState += 100;
    }
    if (DBG) {
      Log.d("KnoxVpnEngineService", "getVPNTransitionState : VpnState : " + vPNState);
    }
    return vPNState;
  }

  public final int getVPNState(String str) {
    try {
      if (this.vpnConfig.getProfileEntry(str) == null) {
        return -1;
      }
      IKnoxVpnService binderInterfaceForProfile = getBinderInterfaceForProfile(str);
      if (binderInterfaceForProfile == null) {
        Log.e("KnoxVpnEngineService", "getVPNState : Interface null for profile : " + str);
        return -1;
      }
      int state = binderInterfaceForProfile.getState(str);
      Log.d(
          "KnoxVpnEngineService",
          "State of vpn profile received from vpn vendor for profileName is = "
              + str
              + " state = "
              + state);
      return state;
    } catch (Exception e) {
      Log.e("KnoxVpnEngineService", "getVPNState : Failure at " + Log.getStackTraceString(e));
      return -1;
    }
  }

  public Context getContext() {
    return this.mContext;
  }

  public List getDomainsByProfileName(String str) {
    refreshDomainInHashMap(str);
    return (List) this.mNotificationMap.get(str);
  }

  public List getProfilesByDomain(String str) {
    ArrayList arrayList = new ArrayList();
    if (Binder.getCallingUid() != 1000) {
      return arrayList;
    }
    for (String str2 : this.mNotificationMap.keySet()) {
      ArrayList arrayList2 = (ArrayList) this.mNotificationMap.get(str2);
      int i = 0;
      while (true) {
        if (i >= arrayList2.size()) {
          break;
        }
        if (((String) arrayList2.get(i)).equals(str)) {
          arrayList.add(str2);
          break;
        }
        i++;
      }
    }
    return arrayList;
  }

  public final void refreshDomainInHashMap(String str) {
    ArrayList domainsByProfileName = this.mVpnStorageProvider.getDomainsByProfileName(str);
    if (domainsByProfileName != null) {
      this.mNotificationMap.put(str, domainsByProfileName);
    }
    if (DBG) {
      Log.v(
          "KnoxVpnEngineService", "#################### Printing domain map ####################");
      try {
        Log.v("KnoxVpnEngineService", "Domain Count : " + this.mNotificationMap.size() + " [ ");
        for (String str2 : this.mNotificationMap.keySet()) {
          ArrayList arrayList = (ArrayList) this.mNotificationMap.get(str2);
          String str3 = "";
          String str4 = "";
          for (int i = 0; i < arrayList.size(); i++) {
            str4 = str4 + "," + ((String) arrayList.get(i));
          }
          Log.v("KnoxVpnEngineService", "{ProfileName = " + str2 + ": [");
          StringBuilder sb = new StringBuilder();
          sb.append("    domains : ");
          if (!str4.equals("")) {
            str3 = str4.substring(1);
          }
          sb.append(str3);
          Log.v("KnoxVpnEngineService", sb.toString());
          Log.v("KnoxVpnEngineService", "]},");
        }
        Log.v("KnoxVpnEngineService", "] ");
      } catch (Exception e) {
        Log.v("KnoxVpnEngineService", "Exception: " + Log.getStackTraceString(e));
      }
      Log.v(
          "KnoxVpnEngineService", "#################### Printing domain map ####################");
    }
  }

  public final void updateNotification(String str, int i, boolean z) {
    VpnProfileInfo profileEntry;
    if (DBG) {
      Log.d("KnoxVpnEngineService", "updateNotification profileName : " + str + " , flag : " + z);
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        profileEntry = this.vpnConfig.getProfileEntry(str);
      } catch (Exception e) {
        Log.e(
            "KnoxVpnEngineService",
            "updateNotification : Exception : " + Log.getStackTraceString(e));
      }
      if (profileEntry == null) {
        return;
      }
      if (profileEntry.getActivateState() == 1) {
        getVpnManagerService().updateEnterpriseVpn(str, i, z);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public int setNotificationDismissibleFlag(
      KnoxVpnContext knoxVpnContext, String str, int i, int i2) {
    checkCallingUidPermission(knoxVpnContext);
    int callingUid = Binder.getCallingUid();
    if (!hasKnoxInternalExceptionPermission(
        this.mContext.getPackageManager().getNameForUid(callingUid),
        UserHandle.getUserId(callingUid))) {
      Log.d("KnoxVpnEngineService", "Only Knox Internal package can set notification flag");
      return -1;
    }
    if (DBG) {
      Log.d(
          "KnoxVpnEngineService",
          "setNotificationDismissibleFlag profile : "
              + str
              + ", userId : "
              + i
              + " , flag : "
              + i2);
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry == null) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return 108;
      }
      if (profileEntry.getActivateState() != 1) {
        Log.e("KnoxVpnEngineService", "setNotificationDismissibleFlag : Profile is not activated");
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return 0;
      }
      if (setDismissibleFlag(knoxVpnContext.adminId, i, i2)) {
        this.notificationFlagState.put(Integer.valueOf(i), Integer.valueOf(i2));
        getVpnManagerService().setNotificationDismissibleFlag(str, i, i2);
        return 1;
      }
      Log.e("KnoxVpnEngineService", "setNotificationDismissibleFlag : Notification DB failure");
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return -2;
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "setNotificationDismissibleFlag : Exception : " + Log.getStackTraceString(e));
      return -1;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final boolean setDismissibleFlag(int i, int i2, int i3) {
    boolean putDataByFields;
    try {
      String[] strArr = {"notificationUserId"};
      String[] strArr2 = {String.valueOf(i2)};
      ArrayList dataByFields =
          this.mVpnStorageProvider.getDataByFields("VpnNotificationTable", strArr, strArr2, null);
      if (dataByFields != null && !dataByFields.isEmpty()) {
        ContentValues contentValues = (ContentValues) dataByFields.get(0);
        contentValues.put("dismissFlag", Integer.valueOf(i3));
        putDataByFields =
            this.mVpnStorageProvider.putDataByFields(
                "VpnNotificationTable", strArr, strArr2, contentValues);
        return putDataByFields;
      }
      ContentValues contentValues2 = new ContentValues();
      contentValues2.put("dismissFlag", Integer.valueOf(i3));
      contentValues2.put("notificationUserId", Integer.valueOf(i2));
      contentValues2.put("adminUid", Integer.valueOf(i));
      putDataByFields =
          this.mVpnStorageProvider.putDataByFields(
              "VpnNotificationTable", null, null, contentValues2);
      return putDataByFields;
    } catch (Exception e) {
      if (!DBG) {
        return false;
      }
      Log.e(
          "KnoxVpnEngineService",
          "Exception occured while storing notificationFlagState info in db "
              + Log.getStackTraceString(e));
      return false;
    }
  }

  public int getNotificationDismissibleFlag(KnoxVpnContext knoxVpnContext, int i) {
    checkCallingUidPermission(knoxVpnContext);
    int callingUid = Binder.getCallingUid();
    if (!hasKnoxInternalExceptionPermission(
        this.mContext.getPackageManager().getNameForUid(callingUid),
        UserHandle.getUserId(callingUid))) {
      Log.d("KnoxVpnEngineService", "Only Knox Internal package can get notification flag");
      return -1;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return getNotificationDismissibleFlagInternal(i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public int getNotificationDismissibleFlagInternal(int i) {
    ConcurrentHashMap concurrentHashMap;
    if (Binder.getCallingUid() == 1000
        && (concurrentHashMap = this.notificationFlagState) != null
        && concurrentHashMap.containsKey(Integer.valueOf(i))) {
      return ((Integer) this.notificationFlagState.get(Integer.valueOf(i))).intValue();
    }
    return 1;
  }

  public final boolean hasKnoxInternalExceptionPermission(String str, int i) {
    try {
      return AppGlobals.getPackageManager()
              .checkPermission(
                  "com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION", str, i)
          == 0;
    } catch (RemoteException e) {
      e.printStackTrace();
      return false;
    }
  }

  public final void syncVpnProfile(IKnoxVpnService iKnoxVpnService, String str) {
    Log.d("KnoxVpnEngineService", "Start syncProfile between VPNDB and Vendor's");
    try {
      String connection = iKnoxVpnService.getConnection(str);
      if (connection != null) {
        Log.d(
            "KnoxVpnEngineService",
            "Profile in VendorDB is removed successfullyStatus of remove: "
                + iKnoxVpnService.removeConnection(
                    this.mKnoxVpnHelper.getProfileNameFromJsonString(connection)));
      }
    } catch (Exception e) {
      Log.e("KnoxVpnEngineService", "Sync the profile : Failure at " + Log.getStackTraceString(e));
    }
  }

  public int checkChainingStatus(String str) {
    IKnoxVpnService binderInterfaceForProfile;
    int i = 0;
    try {
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry != null) {
        String vendorPkgName = profileEntry.getVendorPkgName();
        Log.d("KnoxVpnEngineService", "checkChainingStatus: vendorName value is " + vendorPkgName);
        String profileOwningThePackage =
            this.mKnoxVpnHelper.getProfileOwningThePackage(vendorPkgName);
        Log.d(
            "KnoxVpnEngineService",
            "checkChainingStatus: profile_added_vendor_name value is " + profileOwningThePackage);
        if (profileOwningThePackage != null
            && (binderInterfaceForProfile = getBinderInterfaceForProfile(profileOwningThePackage))
                != null) {
          int state = binderInterfaceForProfile.getState(profileOwningThePackage);
          Log.d("KnoxVpnEngineService", "checkChainingStatus: state value is " + state);
          if (state == 4
              || ((Boolean)
                      this.mIgnoreConnectCheckForChaining.get(
                          Integer.valueOf(profileEntry.getPersonaId())))
                  .booleanValue()) {
            i = 1;
            this.mIgnoreConnectCheckForChaining.put(
                Integer.valueOf(profileEntry.getPersonaId()), Boolean.FALSE);
          }
        }
      }
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception at checkChainingStatus API " + Log.getStackTraceString(e));
    }
    Log.d("KnoxVpnEngineService", "checkChainingStatus: chaining_status value is " + i);
    return i;
  }

  /* JADX WARN: Code restructure failed: missing block: B:20:0x0061, code lost:

     if (r3 == false) goto L20;
  */
  /* JADX WARN: Code restructure failed: missing block: B:21:0x0063, code lost:

     android.util.Log.d("KnoxVpnEngineService", "getChainingEnabledForProfile:chaining enabled for profile");
  */
  /* JADX WARN: Code restructure failed: missing block: B:22:0x0068, code lost:

     r0 = r2.getChainingEnabled();
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public int getChainingEnabledForProfile(int i) {
    if (DBG) {
      Log.d("KnoxVpnEngineService", "getChainingEnabledForProfile: uid value is " + i);
    }
    int i2 = -1;
    try {
      Iterator it = this.vpnConfig.getProfileEntries().iterator();
      while (true) {
        if (!it.hasNext()) {
          break;
        }
        VpnProfileInfo vpnProfileInfo = (VpnProfileInfo) it.next();
        boolean z = DBG;
        if (z) {
          Log.d(
              "KnoxVpnEngineService",
              "getChainingEnabledForProfile: uidOfVendor value is "
                  + vpnProfileInfo.getVendorUid());
        }
        if (vpnProfileInfo.getVendorUid() == i) {
          if (z) {
            Log.d("KnoxVpnEngineService", "getChainingEnabledForProfile: vendorName is same");
          }
          if (vpnProfileInfo.getChainingEnabled() == 1) {
            break;
          }
        }
      }
    } catch (Exception e) {
      Log.e("KnoxVpnEngineService", "Exception = " + Log.getStackTraceString(e));
    }
    if (DBG) {
      Log.d("KnoxVpnEngineService", "getChainingEnabledForProfile: chainingEnabled value is " + i2);
    }
    return i2;
  }

  public final void handleChainingScenarioForStartConnection(String str) {
    try {
      int checkIfProfileHasChainingFeature =
          this.mKnoxVpnHelper.checkIfProfileHasChainingFeature(str);
      Log.d(
          "KnoxVpnEngineService",
          "handleChainingScenarioForStartConnection: chaining_enabled value is "
              + checkIfProfileHasChainingFeature);
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry == null) {
        Log.d(
            "KnoxVpnEngineService",
            "handleChainingScenarioForStartConnection: profile in outerProfile is " + profileEntry);
        return;
      }
      int personaId = profileEntry.getPersonaId();
      Log.d(
          "KnoxVpnEngineService",
          "handleChainingScenarioForStartConnection: userIdOfOuterProfile value is " + personaId);
      if (checkIfProfileHasChainingFeature == 0) {
        for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
          if (vpnProfileInfo.getChainingEnabled() == 1
              && vpnProfileInfo.getPersonaId() == personaId) {
            Log.d(
                "KnoxVpnEngineService",
                "handleChainingScenarioForStartConnection: chained profile is going to be started");
            startChainedProfile(vpnProfileInfo.getProfileName());
          }
        }
      }
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception at handleChainingScenarioForStartConnection API "
              + Log.getStackTraceString(e));
    }
  }

  public final void startChainedProfile(String str) {
    try {
      ChainingStateMachine chainingStateMachine = this.mChainingStateMachine;
      if (chainingStateMachine != null) {
        chainingStateMachine.exit();
        this.mChainingStateMachine = null;
      }
      ChainingStateMachine chainingStateMachine2 = new ChainingStateMachine(str, 4);
      this.mChainingStateMachine = chainingStateMachine2;
      chainingStateMachine2.start();
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception at startChainedProfile API " + Log.getStackTraceString(e));
    }
  }

  public final void removeProfileFromKeyStore(String str, int i, String str2) {
    int userId = UserHandle.getUserId(i);
    int appId = UserHandle.getAppId(i);
    String regularPackageName = this.mKnoxVpnHelper.getRegularPackageName(str2);
    if (appId == 1000) {
      try {
        if (regularPackageName.equalsIgnoreCase("com.samsung.sVpn") && userId == 0) {
          for (VpnProfile vpnProfile : retrieveVpnListFromStorage()) {
            if (vpnProfile.name.equals(str)) {
              if (DBG) {
                Log.d(
                    "KnoxVpnEngineService",
                    "The profileInfo present in the keystore belongs to knox, so going to delete"
                        + " it");
              }
              LegacyVpnProfileStore.remove("VPN_" + vpnProfile.key);
              return;
            }
          }
        }
      } catch (Exception e) {
        Log.e(
            "KnoxVpnEngineService",
            "Exception occured at removeProfileFromKeyStore API " + Log.getStackTraceString(e));
      }
    }
  }

  public final ArrayList retrieveVpnListFromStorage() {
    VpnProfile decode;
    ArrayList arrayList = new ArrayList();
    String[] list = LegacyVpnProfileStore.list("VPN_");
    if (list != null && list.length > 0) {
      for (String str : list) {
        if (!TextUtils.isEmpty(str)) {
          byte[] bArr = LegacyVpnProfileStore.get("VPN_" + str);
          if (bArr != null && (decode = VpnProfile.decode(str, bArr)) != null) {
            arrayList.add(decode);
          }
        }
      }
    }
    return arrayList;
  }

  public final void stopStrongwanProxyConnection(String str, String str2, int i) {
    try {
      if (str.equalsIgnoreCase("com.samsung.sVpn")
          && getActiveProfilesForVendor(str2) != null
          && getActiveProfilesForVendor(str2).size() > 0) {
        if (i == 0) {
          getVpnManagerService().prepareVpn("[Legacy VPN]", "[Legacy VPN]", i);
        } else if (i > 0) {
          getVpnManagerService().stopLegacyKnoxVpn(i, "[Legacy VPN]", "[Legacy VPN]");
        }
      }
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception occured at stopStrongwanProxyConnection API " + Log.getStackTraceString(e));
    }
  }

  public class ChainingStateMachine extends Thread {
    public String profileName;
    public int requiredState;
    public long threadStartTime = -1;
    public long idleStateSleepTime = 5000;
    public long connectingStateSleepTime = 5000;

    public ChainingStateMachine(String str, int i) {
      this.profileName = str;
      this.requiredState = i;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      try {
        this.threadStartTime = SystemClock.elapsedRealtime();
        if (KnoxVpnEngineService.DBG) {
          Log.d(
              "KnoxVpnEngineService",
              "ChainingStateMachine: thread start time is " + this.threadStartTime);
        }
        IKnoxVpnService binderInterfaceForProfile =
            KnoxVpnEngineService.this.getBinderInterfaceForProfile(this.profileName);
        if (binderInterfaceForProfile == null) {
          if (KnoxVpnEngineService.DBG) {
            Log.d(
                "KnoxVpnEngineService",
                "Error occured while ChainingStateMachine: The error code is 110");
            return;
          }
          return;
        }
        int state = binderInterfaceForProfile.getState(this.profileName);
        if (KnoxVpnEngineService.DBG) {
          Log.d(
              "KnoxVpnEngineService",
              "ChainingStateMachine: currentState of the profile is " + state);
        }
        if (state == -1 || state == 5 || state == 1) {
          startChainedConnection(this.profileName, this.idleStateSleepTime);
        } else if (state == 2 || state == 3) {
          startChainedConnection(this.profileName, this.connectingStateSleepTime);
        }
        if (KnoxVpnEngineService.DBG) {
          Log.d(
              "KnoxVpnEngineService",
              "ChainingStateMachine: thread stop time is " + SystemClock.elapsedRealtime());
        }
      } catch (Exception e) {
        if (KnoxVpnEngineService.DBG) {
          Log.e(
              "KnoxVpnEngineService",
              "Exception at ChainingStateMachine API " + Log.getStackTraceString(e));
        }
      }
    }

    public void exit() {
      interrupt();
    }

    public final void startChainedConnection(String str, long j) {
      if (KnoxVpnEngineService.DBG) {
        Log.d(
            "KnoxVpnEngineService",
            "ChainingStateMachine: startChainedConnection is being called for profile " + str);
      }
      try {
        Thread.sleep(j);
        if (KnoxVpnEngineService.DBG) {
          Log.d(
              "KnoxVpnEngineService",
              "ChainingStateMachine: The thread running time after delay is  "
                  + SystemClock.elapsedRealtime());
        }
        IKnoxVpnService binderInterfaceForProfile =
            KnoxVpnEngineService.this.getBinderInterfaceForProfile(str);
        if (binderInterfaceForProfile != null) {
          if (KnoxVpnEngineService.DBG) {
            Log.d(
                "KnoxVpnEngineService",
                "ChainingStateMachine: state of the profile after delay is "
                    + binderInterfaceForProfile.getState(str));
          }
          int state = binderInterfaceForProfile.getState(str);
          if (state == -1 || state == 5 || state == 1) {
            VpnProfileInfo profileEntry = KnoxVpnEngineService.this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
              KnoxVpnEngineService.this.mIgnoreConnectCheckForChaining.put(
                  Integer.valueOf(profileEntry.getPersonaId()), Boolean.TRUE);
              int startVpnProfile = KnoxVpnEngineService.this.startVpnProfile(str);
              if (KnoxVpnEngineService.DBG) {
                Log.d(
                    "KnoxVpnEngineService",
                    "ChainingStateMachine: the profile is going to be started after the delay and"
                        + " the result is "
                        + startVpnProfile);
                return;
              }
              return;
            }
            return;
          }
          if (state == 2 || state == 3) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (KnoxVpnEngineService.DBG) {
              Log.d(
                  "KnoxVpnEngineService",
                  "ChainingStateMachine: currentTime after the thread has started is  "
                      + elapsedRealtime);
            }
            if (elapsedRealtime - this.threadStartTime <= 90000) {
              if (KnoxVpnEngineService.DBG) {
                Log.d(
                    "KnoxVpnEngineService",
                    "ChainingStateMachine: the profile is going to be delayed again " + str);
              }
              startChainedConnection(str, this.connectingStateSleepTime);
              return;
            }
            if (KnoxVpnEngineService.DBG) {
              Log.d(
                  "KnoxVpnEngineService",
                  "ChainingStateMachine: time out has happened and going to exit " + str);
            }
          }
        }
      } catch (Exception e) {
        if (KnoxVpnEngineService.DBG) {
          Log.e(
              "KnoxVpnEngineService",
              "Exception at startChainedConnection API " + Log.getStackTraceString(e));
        }
      }
    }
  }

  public final void checkExistsEmailPackageInProfiles(int i, HashMap hashMap, HashSet hashSet) {
    ArrayList arrayList = new ArrayList();
    HashMap hashMap2 = new HashMap();
    int i2 = 1;
    boolean z = i % 2 != 0;
    for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
      if (vpnProfileInfo != null) {
        for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.getPackageList()) {
          if (vpnPackageInfo.getUid() == -2) {
            int containerIdFromPackageName =
                getKnoxVpnHelperInstance()
                    .getContainerIdFromPackageName(vpnPackageInfo.getPackageName());
            if (vpnProfileInfo.getActivateState() == i2) {
              Iterator it = hashSet.iterator();
              boolean z2 = false;
              while (it.hasNext()) {
                String[] packagesForUid =
                    this.mContext
                        .getPackageManager()
                        .getPackagesForUid(((Integer) it.next()).intValue());
                if (packagesForUid != null) {
                  int length = packagesForUid.length;
                  int i3 = 0;
                  while (true) {
                    if (i3 >= length) {
                      break;
                    }
                    String str = packagesForUid[i3];
                    if (Arrays.asList(KnoxVpnConstants.EMAIL_PACKAGE_LIST).contains(str)) {
                      Log.d(
                          "KnoxVpnEngineService",
                          "checkExistsEmailPackageInProfiles :: Email package is matched but"
                              + " exempted: "
                              + str);
                      z2 = true;
                      break;
                    }
                    i3++;
                  }
                  if (z2) {
                    break;
                  }
                }
              }
              if (!z2) {
                arrayList.add(Integer.valueOf(containerIdFromPackageName));
              }
            }
            getKnoxVpnHelperInstance().setProxyFlagForEmail(containerIdFromPackageName, false);
          } else {
            if (vpnProfileInfo.getActivateState() == 1) {
              hashMap2.put(
                  Integer.valueOf(vpnPackageInfo.getUid()), vpnPackageInfo.getPackageName());
            }
            getKnoxVpnHelperInstance()
                .setProxyFlagForEmail(UserHandle.getUserId(vpnPackageInfo.getUid()), false);
          }
          i2 = 1;
        }
      }
      i2 = 1;
    }
    if (!z) {
      Iterator it2 = hashMap.keySet().iterator();
      while (it2.hasNext()) {
        int intValue = ((Integer) it2.next()).intValue();
        hashMap2.remove(Integer.valueOf(intValue));
        if (((Boolean) hashMap.get(Integer.valueOf(intValue))).booleanValue()) {
          for (int i4 = 0; i4 < arrayList.size(); i4++) {
            if (((Integer) arrayList.get(i4)).intValue() == intValue) {
              arrayList.remove(i4);
            }
          }
        }
      }
    }
    for (int i5 = 0; i5 < arrayList.size(); i5++) {
      getKnoxVpnHelperInstance()
          .setProxyFlagForEmail(((Integer) arrayList.get(i5)).intValue(), true);
    }
    Iterator it3 = hashMap2.keySet().iterator();
    while (it3.hasNext()) {
      int intValue2 = ((Integer) it3.next()).intValue();
      if (Arrays.asList(KnoxVpnConstants.EMAIL_PACKAGE_LIST)
          .contains(
              getKnoxVpnHelperInstance()
                  .getRegularPackageName((String) hashMap2.get(Integer.valueOf(intValue2))))) {
        Log.d(
            "KnoxVpnEngineService",
            "checkExistsEmailPackageInProfiles :: Email package is matched : "
                + ((String) hashMap2.get(Integer.valueOf(intValue2))));
        getKnoxVpnHelperInstance().setProxyFlagForEmail(UserHandle.getUserId(intValue2), true);
      }
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:105:0x0193  */
  /* JADX WARN: Removed duplicated region for block: B:107:? A[RETURN, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void updateProxyRules(final int i, final String str, final HashMap hashMap) {
    VpnProfileInfo profileEntry;
    long clearCallingIdentity;
    try {
      profileEntry = this.vpnConfig.getProfileEntry(str);
    } catch (Exception e) {
      if (DBG) {}
    }
    if (profileEntry != null) {
      String proxyServer = profileEntry.getProxyServer();
      profileEntry.getProxyUsername();
      profileEntry.getProxyPassword();
      Uri pacurl = profileEntry.getPacurl();
      final int vendorUid = profileEntry.getVendorUid();
      profileEntry.getProxyPort();
      String regularPackageName =
          this.mKnoxVpnHelper.getRegularPackageName(profileEntry.getVendorPkgName());
      final HashSet exemptPackageList = profileEntry.getExemptPackageList();
      if (pacurl == Uri.EMPTY && proxyServer == KnoxVpnConstants.DEFAULT_PROXY_SERVER) {
        return;
      }
      if (i == 0) {
        getKnoxVpnPacProcessor()
            .updatePermissionForAppsToaccessLocalHost(
                str,
                1,
                profileEntry.getProxyInfo().getPort(),
                getKnoxVpnHelperInstance().getListOfUid(str));
        profileEntry.setProxyInfo(null);
        clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          getVpnManagerService()
              .updateLocalProxyInfo(
                  str, UserHandle.getUserId(vendorUid), regularPackageName, (ProxyInfo) null);
          if (hashMap == null || hashMap.size() <= 0) {
            return;
          }
          final ProxyInfo buildPacProxy = ProxyInfo.buildPacProxy(Uri.EMPTY);
          checkExistsEmailPackageInProfiles(i, hashMap, exemptPackageList);
          Runnable runnable = new Runnable() { // from class:
                // com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.4
                @Override // java.lang.Runnable
                public void run() {
                  if (KnoxVpnEngineService.DBG) {
                    Log.d(
                        "KnoxVpnEngineService",
                        "The pac configuration is being removed for the profile, update AMS" + str);
                  }
                  KnoxVpnEngineService.this
                      .getAMSInstance()
                      .updateProxyPacConfigurationForKnoxVpn(
                          hashMap, vendorUid, buildPacProxy, exemptPackageList);
                  if (KnoxVpnEngineService.DBG) {
                    Log.d(
                        "KnoxVpnEngineService",
                        "The pac configuration is being removed for the profile, send default"
                            + " broadcast "
                            + str);
                  }
                  KnoxVpnEngineService.this
                      .getKnoxVpnHelperInstance()
                      .sendProxyConfigBroadcast(hashMap, vendorUid, exemptPackageList);
                  KnoxVpnEngineService.this.killRunningProcessToApplyProxy(
                      str, hashMap, exemptPackageList);
                }
              };
          KnoxVpnHandler knoxVpnHandler = this.mHandler;
          if (knoxVpnHandler != null) {
            knoxVpnHandler.post(runnable);
            return;
          }
          return;
        } finally {
        }
      }
      if (i == 1) {
        if (profileEntry.getProxyInfo() == null) {
          ProxyInfo buildPacProxy2 = ProxyInfo.buildPacProxy(pacurl, getPortForProfileName(str));
          profileEntry.setProxyInfo(buildPacProxy2);
          clearCallingIdentity = Binder.clearCallingIdentity();
          try {
            getVpnManagerService()
                .updateLocalProxyInfo(
                    str, UserHandle.getUserId(vendorUid), regularPackageName, buildPacProxy2);
            Binder.restoreCallingIdentity(clearCallingIdentity);
          } finally {
          }
        }
        if (hashMap == null || hashMap.size() <= 0) {
          return;
        }
        final ProxyInfo proxyInfo = profileEntry.getProxyInfo();
        Runnable runnable2 =
            new Runnable() { // from class:
                             // com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.2
              @Override // java.lang.Runnable
              public void run() {
                if (KnoxVpnEngineService.DBG) {
                  Log.d(
                      "KnoxVpnEngineService",
                      "The pac configuration is being added for the profile, update AMS" + str);
                }
                KnoxVpnEngineService.this
                    .getAMSInstance()
                    .updateProxyPacConfigurationForKnoxVpn(
                        hashMap, vendorUid, proxyInfo, exemptPackageList);
                if (KnoxVpnEngineService.DBG) {
                  Log.d(
                      "KnoxVpnEngineService",
                      "The pac configuration is being added for the profile, send pac broadcast "
                          + str);
                }
                KnoxVpnEngineService.this
                    .getKnoxVpnHelperInstance()
                    .sendProxyConfigBroadcast(hashMap, vendorUid, exemptPackageList);
                KnoxVpnEngineService.this.checkExistsEmailPackageInProfiles(
                    i, hashMap, exemptPackageList);
                KnoxVpnEngineService.this.killRunningProcessToApplyProxy(
                    str, hashMap, exemptPackageList);
              }
            };
        KnoxVpnHandler knoxVpnHandler2 = this.mHandler;
        if (knoxVpnHandler2 != null) {
          knoxVpnHandler2.post(runnable2);
          return;
        }
        return;
      }
      if (i != 2) {
        if (i != 3) {
          return;
        }
        if (profileEntry.getProxyInfo() == null) {
          ProxyInfo buildPacProxy3 = ProxyInfo.buildPacProxy(pacurl, getPortForProfileName(str));
          profileEntry.setProxyInfo(buildPacProxy3);
          clearCallingIdentity = Binder.clearCallingIdentity();
          try {
            getVpnManagerService()
                .updateLocalProxyInfo(
                    str, UserHandle.getUserId(vendorUid), regularPackageName, buildPacProxy3);
            Binder.restoreCallingIdentity(clearCallingIdentity);
          } finally {
          }
        }
        if (hashMap == null || hashMap.size() <= 0) {
          return;
        }
        final ProxyInfo proxyInfo2 = profileEntry.getProxyInfo();
        Runnable runnable3 = new Runnable() { // from class:
              // com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.3
              @Override // java.lang.Runnable
              public void run() {
                Log.d(
                    "KnoxVpnEngineService",
                    "The pac configuration is being added for the application added to vpn, update"
                        + " AMS "
                        + str);
                KnoxVpnEngineService.this
                    .getAMSInstance()
                    .updateProxyPacConfigurationForKnoxVpn(
                        hashMap, vendorUid, proxyInfo2, exemptPackageList);
                Log.d(
                    "KnoxVpnEngineService",
                    "The pac configuration is being added for the application added to vpn, send"
                        + " pac broadcast "
                        + str);
                KnoxVpnEngineService.this
                    .getKnoxVpnHelperInstance()
                    .sendProxyConfigBroadcast(hashMap, vendorUid, exemptPackageList);
                KnoxVpnEngineService.this.checkExistsEmailPackageInProfiles(
                    i, hashMap, exemptPackageList);
                KnoxVpnEngineService.this.killRunningProcessToApplyProxy(
                    str, hashMap, exemptPackageList);
              }
            };
        KnoxVpnHandler knoxVpnHandler3 = this.mHandler;
        if (knoxVpnHandler3 != null) {
          knoxVpnHandler3.post(runnable3);
          return;
        }
        return;
      }
      if (hashMap == null || hashMap.size() <= 0) {
        return;
      }
      if (profileEntry.getPackageCount() == hashMap.size()) {
        if (DBG) {
          Log.d(
              "KnoxVpnEngineService",
              "The admin has removed all the apps from the profile, setting the default PAC proxy"
                  + " configuration");
        }
        getKnoxVpnPacProcessor()
            .updatePermissionForAppsToaccessLocalHost(
                str,
                1,
                profileEntry.getProxyInfo().getPort(),
                getKnoxVpnHelperInstance().getListOfUid(str));
        profileEntry.setProxyInfo(null);
        clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          getVpnManagerService()
              .updateLocalProxyInfo(
                  str, UserHandle.getUserId(vendorUid), regularPackageName, (ProxyInfo) null);
          Binder.restoreCallingIdentity(clearCallingIdentity);
        } finally {
        }
      }
      final ProxyInfo buildPacProxy4 = ProxyInfo.buildPacProxy(Uri.EMPTY);
      checkExistsEmailPackageInProfiles(i, hashMap, exemptPackageList);
      Runnable runnable4 = new Runnable() { // from class:
            // com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService.5
            @Override // java.lang.Runnable
            public void run() {
              Log.d(
                  "KnoxVpnEngineService",
                  "The pac configuration is being removed for the application added to vpn, update"
                      + " AMS "
                      + str);
              KnoxVpnEngineService.this
                  .getAMSInstance()
                  .updateProxyPacConfigurationForKnoxVpn(
                      hashMap, vendorUid, buildPacProxy4, exemptPackageList);
              Log.d(
                  "KnoxVpnEngineService",
                  "The pac configuration is being removed for the application added to vpn, send"
                      + " default broadcast "
                      + str);
              KnoxVpnEngineService.this
                  .getKnoxVpnHelperInstance()
                  .sendProxyConfigBroadcast(hashMap, vendorUid, exemptPackageList);
              KnoxVpnEngineService.this.killRunningProcessToApplyProxy(
                  str, hashMap, exemptPackageList);
            }
          };
      KnoxVpnHandler knoxVpnHandler4 = this.mHandler;
      if (knoxVpnHandler4 != null) {
        knoxVpnHandler4.post(runnable4);
        return;
      }
      return;
      if (DBG) {
        return;
      }
      Log.e(
          "KnoxVpnEngineService",
          "Exception occured while applying the proxy rules at KnoxVpnEngineService "
              + Log.getStackTraceString(e));
    }
  }

  public boolean isProxyConfiguredForKnoxVpn(int i) {
    try {
      for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
        if (vpnProfileInfo.getProxyServer() != KnoxVpnConstants.DEFAULT_PROXY_SERVER
            || vpnProfileInfo.getPacurl() != Uri.EMPTY) {
          int activateState = vpnProfileInfo.getActivateState();
          if (activateState != 0) {
            if (activateState == 1) {
              for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.getPackageList()) {
                if (vpnPackageInfo.getUid() == i) {
                  return true;
                }
                if (vpnPackageInfo.getUid() == -2) {
                  if (i == vpnProfileInfo.getVendorUid()) {
                    Log.d(
                        "KnoxVpnEngineService",
                        "knox vpn proxy setting skipping for vendor entry " + i);
                    return false;
                  }
                  if (UserHandle.getUserId(i)
                      == getKnoxVpnHelperInstance()
                          .getContainerIdFromPackageName(vpnPackageInfo.getPackageName())) {
                    Iterator it = vpnProfileInfo.getExemptPackageList().iterator();
                    while (it.hasNext()) {
                      if (i == ((Integer) it.next()).intValue()) {
                        Log.d(
                            "KnoxVpnEngineService",
                            "knox vpn proxy setting skipping for exempted entry " + i);
                        return false;
                      }
                    }
                    return true;
                  }
                }
              }
            } else if (DBG) {
              Log.d(
                  "KnoxVpnEngineService",
                  "knox vpn proxy settings is being queried by CS for unknown state vpn profile "
                      + vpnProfileInfo.getProfileName());
            }
          } else if (DBG) {
            Log.d(
                "KnoxVpnEngineService",
                "knox vpn proxy settings is being queried by CS for the de-activated vpn profile "
                    + vpnProfileInfo.getProfileName());
          }
        }
      }
    } catch (Exception unused) {
      if (DBG) {
        Log.e(
            "KnoxVpnEngineService",
            "Exception occured while retrieving the profile info object, might be that the proxy"
                + " info has not been configured yet ");
      }
    }
    return false;
  }

  public final void killRunningProcessToApplyProxy(String str, HashMap hashMap, HashSet hashSet) {
    Iterator it;
    Iterator it2;
    HashMap hashMap2 = hashMap;
    if (hashMap2 == null) {
      if (DBG) {
        Log.d("KnoxVpnEngineService", "Proxy config has been applied : null");
        return;
      }
      return;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        Iterator it3 = hashMap.keySet().iterator();
        boolean z = false;
        while (it3.hasNext()) {
          Integer num = (Integer) it3.next();
          if (((Boolean) hashMap2.get(num)).booleanValue()) {
            String[] strArr = KnoxVpnConstants.APPS_TO_RESTART_PROXY;
            int length = strArr.length;
            int i = 0;
            while (i < length) {
              String str2 = strArr[i];
              int uIDForPackage = getKnoxVpnHelperInstance().getUIDForPackage(num.intValue(), str2);
              if (hashSet.contains(Integer.valueOf(uIDForPackage))) {
                Log.d(
                    "KnoxVpnEngineService",
                    "The following app uid "
                        + uIDForPackage
                        + "is not going to be restarted since it is added to exempt list");
                it2 = it3;
              } else {
                ApplicationInfo applicationInfo =
                    this.mContext.getPackageManager().getApplicationInfo(str2, 0);
                it2 = it3;
                if (getAMSInstance()
                    .checkIfProcessIsRunning(applicationInfo.processName, uIDForPackage)) {
                  Log.d(
                      "KnoxVpnEngineService",
                      "Proxy config has been applied for the entire user, going to restart the app "
                          + str2
                          + "whose uid is "
                          + uIDForPackage);
                  getAMSInstance()
                      .killApplicationProcess(applicationInfo.processName, uIDForPackage);
                  z = true;
                }
                getAMSInstance().killBackgroundProcesses(str2, num.intValue());
              }
              i++;
              it3 = it2;
            }
            it = it3;
          } else {
            it = it3;
            String packageNameForUid =
                getKnoxVpnHelperInstance().getPackageNameForUid(num.intValue());
            if (Arrays.asList(KnoxVpnConstants.APPS_TO_RESTART_PROXY).contains(packageNameForUid)) {
              ApplicationInfo applicationInfo2 =
                  this.mContext.getPackageManager().getApplicationInfo(packageNameForUid, 0);
              if (getAMSInstance()
                  .checkIfProcessIsRunning(applicationInfo2.processName, num.intValue())) {
                Log.d(
                    "KnoxVpnEngineService",
                    "Proxy config has been applied, going to restart the app "
                        + packageNameForUid
                        + "whose uid is "
                        + num);
                getAMSInstance()
                    .killApplicationProcess(applicationInfo2.processName, num.intValue());
                z = true;
              }
              getAMSInstance()
                  .killBackgroundProcesses(packageNameForUid, UserHandle.getUserId(num.intValue()));
              hashMap2 = hashMap;
              it3 = it;
            }
          }
          hashMap2 = hashMap;
          it3 = it;
        }
        if (z) {
          createProcessKillNotification(str);
        }
      } catch (Exception e) {
        Log.e(
            "KnoxVpnEngineService",
            "Exception occured while trying to kill application process to enable proxy "
                + Log.getStackTraceString(e));
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void createProcessKillNotification(String str) {
    try {
      String string = this.mContext.getString(R.string.old_app_action);
      String string2 = this.mContext.getString(R.string.number_picker_increment_scroll_mode);
      VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
      if (profileEntry != null) {
        int userId = UserHandle.getUserId(profileEntry.getVendorUid());
        getNotificationManager()
            .notifyAsUser(
                null,
                str.hashCode(),
                new Notification.Builder(this.mContext, SystemNotificationChannels.VPN)
                    .setSmallIcon(R.drawable.ic_dialog_alert)
                    .setContentTitle(string)
                    .setContentText(string2)
                    .setDefaults(0)
                    .setPriority(2)
                    .setAutoCancel(true)
                    .setStyle(new Notification.BigTextStyle().bigText(string2))
                    .build(),
                new UserHandle(userId));
      }
    } catch (Exception unused) {
      if (DBG) {
        Log.e(
            "KnoxVpnEngineService",
            "Exception occured while trying to create a notification to inform user about process"
                + " restart ");
      }
    }
  }

  public final void removeProcessKillNotification(String str) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry != null) {
          getNotificationManager()
              .cancelAsUser(
                  null,
                  str.hashCode(),
                  new UserHandle(UserHandle.getUserId(profileEntry.getVendorUid())));
        }
      } catch (Exception e) {
        if (DBG) {
          Log.e(
              "KnoxVpnEngineService",
              "Exception occured while trying to remove a notification which informed user about"
                  + " process restart "
                  + Log.getStackTraceString(e));
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final int getPortForProfileName(String str) {
    int proxyPortForProfile = getKnoxVpnPacProcessor().getProxyPortForProfile(str);
    if (proxyPortForProfile == -1) {
      throw new IllegalArgumentException();
    }
    getKnoxVpnPacProcessor()
        .updatePermissionForAppsToaccessLocalHost(
            str, 0, proxyPortForProfile, getKnoxVpnHelperInstance().getListOfUid(str));
    return proxyPortForProfile;
  }

  public final String getProfileNameForUid(int i) {
    try {
      for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
        if (vpnProfileInfo.getActivateState() == 1 && vpnProfileInfo.getVendorUid() == i) {
          return vpnProfileInfo.getProfileName();
        }
      }
      return null;
    } catch (Exception unused) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception occured while trying to fetch profile name for the uid " + i);
      return null;
    }
  }

  public String getInterfaceNameForUid(int i) {
    try {
    } catch (Exception unused) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception occured while trying to fetch interfacename for the uid " + i);
    }
    if (Binder.getCallingUid() != 1000) {
      return null;
    }
    for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
      if (vpnProfileInfo.getActivateState() == 1) {
        if (vpnProfileInfo.getVendorUid() == i && vpnProfileInfo.getChainingEnabled() != 1) {
          return null;
        }
        for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.getPackageList()) {
          if (vpnPackageInfo.getUid() == i) {
            return vpnProfileInfo.getInterfaceName();
          }
          if (vpnPackageInfo.getUid() == -2
              && UserHandle.getUserId(i)
                  == getKnoxVpnHelperInstance()
                      .getContainerIdFromPackageName(vpnPackageInfo.getPackageName())
              && getProfileNameForUid(i) == null) {
            Iterator it = vpnProfileInfo.getExemptPackageList().iterator();
            while (it.hasNext()) {
              if (i == ((Integer) it.next()).intValue()) {
                return null;
              }
            }
            if (i == 0) {
              return null;
            }
            return vpnProfileInfo.getInterfaceName();
          }
        }
      }
    }
    return null;
  }

  public boolean checkIfCallerIsVpnVendor(int i) {
    String str;
    try {
      String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
      if (packagesForUid != null) {
        str = packagesForUid[0];
        if (DBG) {
          Log.d("KnoxVpnEngineService", "Packagename of uid " + i + " is " + str);
        }
      } else {
        str = null;
      }
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception at checkIfCallerIsVpnVendor " + Log.getStackTraceString(e));
    }
    return implementsVpnService(str, UserHandle.getUserId(i));
  }

  public final boolean implementsVpnService(String str, int i) {
    boolean z = false;
    try {
      List queryIntentServicesAsUser =
          this.mContext
              .getPackageManager()
              .queryIntentServicesAsUser(new Intent(str + ".BIND_SERVICE_NEW"), 0, i);
      if (queryIntentServicesAsUser.size() > 0) {
        Iterator it = queryIntentServicesAsUser.iterator();
        while (true) {
          if (!it.hasNext()) {
            break;
          }
          if (((ResolveInfo) it.next()).serviceInfo.packageName.equalsIgnoreCase(str)) {
            z = true;
            break;
          }
        }
      }
    } catch (Exception e) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception at implementsVpnService " + Log.getStackTraceString(e));
    }
    if (DBG) {
      Log.d("KnoxVpnEngineService", "implementsVpnService : supportsKnox value is " + z);
    }
    return z;
  }

  public String[] getProxyInfoForUid(int i) {
    String[] strArr = new String[2];
    try {
      for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
        if (vpnProfileInfo.getActivateState() == 1) {
          if (vpnProfileInfo.getProxyInfo() == null) {
            return strArr;
          }
          for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.getPackageList()) {
            if (vpnPackageInfo.getUid() == i) {
              strArr[0] = vpnProfileInfo.getProxyInfo().getHost();
              strArr[1] = Integer.toString(vpnProfileInfo.getProxyInfo().getPort());
              Log.d(
                  "KnoxVpnEngineService",
                  "proxy information is requested by the uid belonging to per-app domain " + i);
              return strArr;
            }
            if (vpnPackageInfo.getUid() == -2
                && UserHandle.getUserId(i)
                    == getKnoxVpnHelperInstance()
                        .getContainerIdFromPackageName(vpnPackageInfo.getPackageName())) {
              Iterator it = vpnProfileInfo.getExemptPackageList().iterator();
              while (it.hasNext()) {
                if (i == ((Integer) it.next()).intValue()) {
                  Log.d(
                      "KnoxVpnEngineService",
                      "proxy information is requested by the uid belonging to exempt list in user"
                          + " domain "
                          + i);
                  return strArr;
                }
              }
              if (i == vpnProfileInfo.getVendorUid()) {
                Log.d(
                    "KnoxVpnEngineService",
                    "proxy information is requested by the uid belonging to vpn client " + i);
                return strArr;
              }
              strArr[0] = vpnProfileInfo.getProxyInfo().getHost();
              strArr[1] = Integer.toString(vpnProfileInfo.getProxyInfo().getPort());
              Log.d(
                  "KnoxVpnEngineService",
                  "proxy information is requested by the uid belonging to user domain " + i);
              return strArr;
            }
          }
        }
      }
    } catch (Exception unused) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception occured while trying to fetch the proxy information for the uid " + i);
    }
    return strArr;
  }

  public boolean checkIfUidIsExempted(int i) {
    for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
      if (vpnProfileInfo.getActivateState() == 1) {
        for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.getPackageList()) {
          if (vpnPackageInfo.getUid() == -2
              && UserHandle.getUserId(i)
                  == getKnoxVpnHelperInstance()
                      .getContainerIdFromPackageName(vpnPackageInfo.getPackageName())) {
            Iterator it = vpnProfileInfo.getExemptPackageList().iterator();
            while (it.hasNext()) {
              if (i == ((Integer) it.next()).intValue()) {
                return true;
              }
            }
          }
        }
      }
    }
    Log.d("KnoxVpnEngineService", "checkIfUidIsExempted returned false for uid " + i);
    return false;
  }

  public boolean checkIfLocalProxyPortExists(int i) {
    try {
      for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
        if (vpnProfileInfo.getProxyInfo().getPort() == i) {
          Log.d(
              "KnoxVpnEngineService",
              "The local proxy port is currently owned by the vpn profile "
                  + vpnProfileInfo.getProfileName());
          return true;
        }
      }
      return false;
    } catch (Exception unused) {
      Log.e(
          "KnoxVpnEngineService",
          "Exception occured while trying to check if local proxy port exists for vpn profile");
      return false;
    }
  }

  public final int getVirtualInterfaceType(String str) {
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry != null) {
      return profileEntry.getInterfaceType();
    }
    return 0;
  }

  public final void blockIncomingICMPPackets(boolean z) {
    String activeNetworkInterface = this.mKnoxVpnHelper.getActiveNetworkInterface();
    if (activeNetworkInterface != null) {
      this.mFirewallHelper.blockIncomingICMPPackets(z, activeNetworkInterface);
    }
  }

  public final void updateRulesToExemptUid(
      String str, int i, int i2, int i3, int i4, String str2, String str3) {
    List defaultRouteAppUidList = this.mFirewallHelper.getDefaultRouteAppUidList();
    try {
      if (i != 0) {
        if (i != 1) {
          return;
        }
        if (i2 == 1) {
          this.mFirewallHelper.addExemptRulesForUid(i3);
        } else if (i2 == 2) {
          if (defaultRouteAppUidList.contains(Integer.valueOf(i3))) {
            Log.d("KnoxVpnEngineService", "Ignore applying exempt rule for uid " + i3);
          } else {
            this.mFirewallHelper.addIpRulesForExemptedUid(i3, str3, i4);
          }
        } else if (i2 != 3) {
        } else {
          addUidToExemptList(str, str2, i3, i3);
        }
      } else if (i2 == 1) {
        this.mFirewallHelper.removeExemptRulesForUid(i3);
      } else if (i2 == 2) {
        if (defaultRouteAppUidList.contains(Integer.valueOf(i3))) {
          Log.d("KnoxVpnEngineService", "Ignore removing exempt rule for uid " + i3);
        } else {
          this.mFirewallHelper.removeIpRulesForExemptedUid(i3, i4);
        }
      } else if (i2 != 3) {
      } else {
        removeUidFromExemptList(str, str2, i3, i3);
      }
    } catch (Exception e) {
      Log.e("KnoxVpnEngineService", "Error at updateRulesToExemtUid " + e);
    }
  }

  public final void setCommonProperties(
      KnoxAnalyticsData knoxAnalyticsData, KnoxVpnContext knoxVpnContext, String str, int i) {
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (knoxVpnContext != null) {
      knoxAnalyticsData.setProperty("admUid", knoxVpnContext.adminId);
      knoxAnalyticsData.setProperty("vndrCid", knoxVpnContext.personaId);
      knoxAnalyticsData.setProperty("vndrPkgN", knoxVpnContext.vendorName);
    }
    if (profileEntry != null) {
      i = profileEntry.getProfileId();
    }
    knoxAnalyticsData.setProperty("prfid", i);
  }

  public final void setPropertiesWithLocalEntry(KnoxAnalyticsData knoxAnalyticsData, String str) {
    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
    if (profileEntry != null) {
      String protocolType = profileEntry.getProtocolType();
      if (protocolType != null) {
        knoxAnalyticsData.setProperty("prtTp", protocolType);
      }
      int routeType = profileEntry.getRouteType();
      if (routeType != -1) {
        knoxAnalyticsData.setProperty("rtTp", routeType);
      }
      knoxAnalyticsData.setProperty("connTp", profileEntry.getVpnConnectionType());
      knoxAnalyticsData.setProperty("prxAuth", profileEntry.isProxyAuthRequired());
      knoxAnalyticsData.setProperty("chn", profileEntry.getChainingEnabled());
    }
  }

  public synchronized void dump(
      FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    try {
    } catch (Exception unused) {
      Log.e(
          "KnoxVpnEngineService",
          "knoxvpnprofileinfo: error occured while trying to print the profile state");
    }
    if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
      printWriter.println(
          "knoxvpnprofileinfo: Permission Denial: can't dump PersonaManager from from pid="
              + Binder.getCallingPid()
              + ", uid="
              + Binder.getCallingUid()
              + " without permission android.permission.DUMP");
      return;
    }
    printWriter.print(
        "knoxvpnprofileinfo: The profile info being printed at time "
            + System.currentTimeMillis()
            + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    Iterator it = this.mVpnClientStatus.iterator();
    while (it.hasNext()) {
      printWriter.print(
          "knoxvpnprofileinfo:"
              + ((String) it.next())
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    }
    Iterator it2 = this.mKnoxVpnPacProcessor.getKnoxVpnProxyClientStatus().iterator();
    while (it2.hasNext()) {
      printWriter.print(
          "knoxvpnprofileinfo:"
              + ((String) it2.next())
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    }
    KnoxVpnTetherAuthentication knoxVpnTetherAuthentication = this.mKnoxVpnTetherAuthentication;
    if (knoxVpnTetherAuthentication != null) {
      Iterator it3 = knoxVpnTetherAuthentication.getKnoxVpnTetherAuthClientStatus().iterator();
      while (it3.hasNext()) {
        printWriter.print(
            "knoxvpnprofileinfo:"
                + ((String) it3.next())
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      }
    }
    List<ActivityManager.ProcessErrorStateInfo> processesInErrorState =
        getAMSInstance().getProcessesInErrorState();
    if (processesInErrorState != null) {
      for (ActivityManager.ProcessErrorStateInfo processErrorStateInfo : processesInErrorState) {
        String str = processErrorStateInfo.processName;
        if (str != null && str.equalsIgnoreCase("com.knox.vpn.proxyhandler")) {
          printWriter.print(
              "knoxvpnprofileinfo: knox vpn proxy apk error condition is "
                  + processErrorStateInfo.condition
                  + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
          printWriter.print(
              "knoxvpnprofileinfo: knox vpn proxy apk error longMsg is "
                  + processErrorStateInfo.longMsg
                  + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
          printWriter.print(
              "knoxvpnprofileinfo: knox vpn proxy apk error shortMsg is "
                  + processErrorStateInfo.shortMsg
                  + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
          printWriter.print(
              "knoxvpnprofileinfo: knox vpn proxy apk error stackTrace is "
                  + processErrorStateInfo.stackTrace
                  + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
          printWriter.print(
              "knoxvpnprofileinfo: knox vpn proxy apk error pid is "
                  + processErrorStateInfo.pid
                  + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
          printWriter.print(
              "knoxvpnprofileinfo: knox vpn proxy apk error uid is "
                  + processErrorStateInfo.uid
                  + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
      }
    }
    for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
      printWriter.print(
          "knoxvpnprofileinfo: profile name is "
              + vpnProfileInfo.getProfileName()
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      printWriter.print(
          "knoxvpnprofileinfo: The default interface to which the profile is connected to is "
              + vpnProfileInfo.getDefaultInterface()
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      printWriter.print(
          "knoxvpnprofileinfo: The vitual interface to which the profile is connected to is "
              + vpnProfileInfo.getInterfaceName()
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      printWriter.print(
          "knoxvpnprofileinfo: The vitual interface v4 address to which the profile is connected to"
              + " is "
              + vpnProfileInfo.getInterfaceAddress()
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      printWriter.print(
          "knoxvpnprofileinfo: The vitual interface v6 address to which the profile is connected to"
              + " is "
              + vpnProfileInfo.getV6InterfaceAddress()
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      printWriter.print(
          "knoxvpnprofileinfo: The profile is triggered by the vpn client "
              + vpnProfileInfo.getVendorPkgName()
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      printWriter.print(
          "knoxvpnprofileinfo: The admin id of the profile is "
              + vpnProfileInfo.getAdminId()
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      printWriter.print(
          "knoxvpnprofileinfo: The profile has been created under the user "
              + vpnProfileInfo.getPersonaId()
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      printWriter.print(
          "knoxvpnprofileinfo: The activation state of the vpn profile "
              + vpnProfileInfo.getActivateState()
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      printWriter.print(
          "knoxvpnprofileinfo: is chaining enabled for the profile ? "
              + vpnProfileInfo.getChainingEnabled()
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      printWriter.print(
          "knoxvpnprofileinfo: is uid tracking enabled for the profile ? "
              + vpnProfileInfo.getUidPidSearchEnabled()
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      printWriter.print(
          "knoxvpnprofileinfo: The profile is triggered by the vpn client whose uid is "
              + vpnProfileInfo.getVendorUid()
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      printWriter.print(
          "knoxvpnprofileinfo: The ipChainValue for the profile configured is "
              + vpnProfileInfo.getIpChainName()
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      printWriter.print(
          "knoxvpnprofileinfo: The usb tethering for the profile configured is "
              + vpnProfileInfo.getUsbTethering()
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      printWriter.print(
          "knoxvpnprofileinfo: usb interface name, if active is "
              + this.mKnoxVpnHelper.getInterfaceNameForUsbtethering()
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      int usbtetherAuth = vpnProfileInfo.getUsbtetherAuth();
      printWriter.print(
          "knoxvpnprofileinfo: is usb tethering configured with auth "
              + usbtetherAuth
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      if (usbtetherAuth == 1) {
        printWriter.print(
            "knoxvpnprofileinfo: usbTetherAuthConfig login page configured ? "
                + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(
                    vpnProfileInfo.getProfileName(), "tetherLoginpage", 0)
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.print(
            "knoxvpnprofileinfo: usbTetherAuthConfig response page configured ? "
                + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(
                    vpnProfileInfo.getProfileName(), "tetherResponsePage", 0)
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.print(
            "knoxvpnprofileinfo: usbTetherAuthConfig client cert issuer CN configured ? "
                + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(
                    vpnProfileInfo.getProfileName(), "tetherClientCertIssuerCN", 0)
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.print(
            "knoxvpnprofileinfo: usbTetherAuthConfig client cert issued CN configured ? "
                + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(
                    vpnProfileInfo.getProfileName(), "tetherClientCertIssuedCN", 0)
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.print(
            "knoxvpnprofileinfo: usbTetherAuthConfig captive cert configured ? "
                + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(
                    vpnProfileInfo.getProfileName(), "tetherCaptivePortalCert", 1)
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.print(
            "knoxvpnprofileinfo: usbTetherAuthConfig captive alias configured ? "
                + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(
                    vpnProfileInfo.getProfileName(), "tetherCaptivePortalAlias", 0)
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.print(
            "knoxvpnprofileinfo: usbTetherAuthConfig ca cert configured ? "
                + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(
                    vpnProfileInfo.getProfileName(), "tetherCACert", 1)
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.print(
            "knoxvpnprofileinfo: usbTetherAuthConfig ca alias configured ? "
                + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(
                    vpnProfileInfo.getProfileName(), "tetherCAlias", 0)
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.print(
            "knoxvpnprofileinfo: usbTetherAuthConfig user cert configured ? "
                + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(
                    vpnProfileInfo.getProfileName(), "tetherServerCert", 1)
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.print(
            "knoxvpnprofileinfo: usbTetherAuthConfig user alias configured ? "
                + this.mKnoxVpnHelper.getUsbTetheringAuthConfig(
                    vpnProfileInfo.getProfileName(), "tetherServerAlias", 0)
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      }
      printWriter.print(
          "knoxvpnprofileinfo: connection type is "
              + vpnProfileInfo.getVpnConnectionType()
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.getPackageList()) {
        if (vpnPackageInfo.getUid() == -2) {
          printWriter.print(
              "knoxvpnprofileinfo: The following user has been added to the profile "
                  + getKnoxVpnHelperInstance()
                      .getContainerIdFromPackageName(vpnPackageInfo.getPackageName())
                  + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        } else {
          printWriter.print(
              "knoxvpnprofileinfo: The following application with uid has been added to the profile"
                  + " "
                  + vpnPackageInfo.getUid()
                  + " whose package name is "
                  + vpnPackageInfo.getPackageName()
                  + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
      }
      Iterator it4 = vpnProfileInfo.getExemptPackageList().iterator();
      while (it4.hasNext()) {
        printWriter.print(
            "knoxvpnprofileinfo: The following application with uid has been exempted from vpn"
                + " connection "
                + ((Integer) it4.next()).intValue()
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      }
      Iterator it5 =
          this.mKnoxVpnHelper
              .getUninsalledAppsFromExemptedList(vpnProfileInfo.getProfileName())
              .iterator();
      while (it5.hasNext()) {
        printWriter.print(
            "knoxvpnprofileinfo:Exempted app is either uninstalled by end-user or not yet installed"
                + " "
                + ((String) it5.next())
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      }
      if (vpnProfileInfo.getProxyServer() != KnoxVpnConstants.DEFAULT_PROXY_SERVER
          || vpnProfileInfo.getPacurl() != Uri.EMPTY) {
        String profileName = vpnProfileInfo.getProfileName();
        if (vpnProfileInfo.getProxyInfo() != null) {
          int port = vpnProfileInfo.getProxyInfo().getPort();
          String host = vpnProfileInfo.getProxyInfo().getHost();
          printWriter.print(
              "knoxvpnprofileinfo: The profile has been configured with proxy configuration whose"
                  + " local port as set in f/w is "
                  + port
                  + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
          printWriter.print(
              "knoxvpnprofileinfo: The profile has been configured with proxy configuration whose"
                  + " local host as set in f/w is "
                  + host
                  + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
        printWriter.print(
            "knoxvpnprofileinfo: is the proxy credentials predefined for the profile? "
                + vpnProfileInfo.isproxyCredentialsPreDefined()
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.print(
            "knoxvpnprofileinfo: is proxy auth required for the profile "
                + vpnProfileInfo.isProxyAuthRequired()
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.print(
            "knoxvpnprofileinfo: The profile has been configured with the pac url "
                + vpnProfileInfo.getPacurl()
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.print(
            "knoxvpnprofileinfo: The profile has been configured with the Static Proxy Server "
                + vpnProfileInfo.getProxyServer()
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.print(
            "knoxvpnprofileinfo: The profile has been configured with the Static Proxy port "
                + vpnProfileInfo.getProxyPort()
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.print(
            "knoxvpnprofileinfo: The proxy port retrieved from the apk is "
                + this.mKnoxVpnPacProcessor.getProxyPortForProfile(profileName)
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.print(
            "knoxvpnprofileinfo: check to see if proxy thread is running or not in the apk is "
                + this.mKnoxVpnPacProcessor.isProxyThreadRunning(profileName)
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.print(
            "knoxvpnprofileinfo: current state of the proxy thread in the apk is "
                + this.mKnoxVpnPacProcessor.getProxythreadStatus(profileName)
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.print(
            "knoxvpnprofileinfo: check to see if proxy thread is alive or not in the apk is "
                + this.mKnoxVpnPacProcessor.isProxyThreadAlive(profileName)
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      }
      IKnoxVpnService binderInterfaceForProfile =
          getBinderInterfaceForProfile(vpnProfileInfo.getProfileName());
      if (binderInterfaceForProfile != null) {
        printWriter.print(
            "knoxvpnprofileinfo: The state of the profile in client is "
                + binderInterfaceForProfile.getState(vpnProfileInfo.getProfileName())
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      }
    }
  }

  public void showToastVpnEULA() {
    Log.d("KnoxVpnEngineService", "Show EULA Toast for ONS- START");
    String string = this.mContext.getString(R.string.number_picker_increment_scroll_action);
    Message message = new Message();
    Bundle bundle = new Bundle();
    bundle.putString("message", string);
    message.setData(bundle);
    message.what = 32;
    this.mHandler.sendMessage(message);
    Log.d("KnoxVpnEngineService", "Show EULA Toast for ONS - END");
  }

  /* JADX WARN: Removed duplicated region for block: B:33:0x00a3 A[Catch: Exception -> 0x00c9, TryCatch #1 {Exception -> 0x00c9, blocks: (B:6:0x000b, B:8:0x0019, B:12:0x0021, B:14:0x0025, B:15:0x0041, B:18:0x004d, B:21:0x0062, B:24:0x0069, B:26:0x006f, B:30:0x007f, B:33:0x00a3, B:36:0x00bb, B:42:0x008c), top: B:5:0x000b }] */
  /* JADX WARN: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void logVpnVendor(ComponentName componentName) {
    int i;
    if (componentName == null) {
      return;
    }
    try {
      String packageName = componentName.getPackageName();
      String className = componentName.getClassName();
      if (!TextUtils.isEmpty(packageName) && !TextUtils.isEmpty(className)) {
        if (DBG) {
          Log.d(
              "KnoxVpnEngineService", "VPN service name " + className + " pkgname " + packageName);
        }
        int i2 = "com.samsung.android.knox.net.vpn.KnoxONSVpnService".equals(className) ? 2 : 1;
        boolean z = false;
        String[] strArr = {"packageName"};
        String[] strArr2 = {packageName};
        ArrayList dataByFields =
            this.mVpnStorageProvider.getDataByFields("vpnVendor", strArr, strArr2, null);
        if (dataByFields != null && !dataByFields.isEmpty()) {
          ContentValues contentValues = (ContentValues) dataByFields.get(0);
          try {
            i = contentValues.getAsInteger("namespace").intValue();
          } catch (NumberFormatException unused) {
            i = 0;
          }
          if ((i & i2) == 0) {
            contentValues.put("namespace", Integer.valueOf(i | i2));
            this.mVpnStorageProvider.putDataByFields("vpnVendor", strArr, strArr2, contentValues);
            z = true;
          }
          if (z) {
            return;
          }
          KnoxAnalyticsData knoxAnalyticsData =
              new KnoxAnalyticsData("KNOX_VPN", 1, "API:logVpnVendor");
          this.mData = knoxAnalyticsData;
          knoxAnalyticsData.setProperty("vndrPkgN", packageName);
          this.mData.setProperty("NS", (i2 & 1) != 0 ? "Old" : "New");
          KnoxAnalytics.log(this.mData);
          return;
        }
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("packageName", packageName);
        contentValues2.put("namespace", Integer.valueOf(i2));
        this.mVpnStorageProvider.putDataByFields("vpnVendor", null, null, contentValues2);
        z = true;
        if (z) {}
      }
    } catch (Exception e) {
      Log.e("KnoxVpnEngineService", "failed to log vendor namespace " + e.getMessage());
    }
  }
}
