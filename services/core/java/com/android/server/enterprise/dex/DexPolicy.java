package com.android.server.enterprise.dex;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.application.ApplicationPolicy;
import com.android.server.enterprise.restriction.RestrictionPolicy;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.Utils;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.dex.IDexPolicy;
import com.samsung.android.knox.net.wifi.IWifiPolicy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class DexPolicy extends IDexPolicy.Stub implements EnterpriseServiceCallback {
  public static boolean isBlockerRegistered = false;
  public final SemDesktopModeManager.DesktopModeBlocker blocker;
  public ApplicationPolicy mApplicationPolicy;
  public final Context mContext;
  public ContextInfo mContext_temp;
  public EnterpriseDeviceManager mEDM;
  public EdmStorageProvider mEdmStorageProvider;
  public EnterpriseDumpHelper mEnterpriseDumpHelper;
  public IWifiPolicy mIWifipolicy;
  public final Injector mInjector;
  public RestrictionPolicy restrictionPolicy;

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void notifyToAddSystemService(String str, IBinder iBinder) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminAdded(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onPreAdminRemoval(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void systemReady() {}

  public class Injector {
    public final Context mContext;

    public Injector(Context context) {
      this.mContext = context;
    }

    public EdmStorageProvider getEdmStorageProvider() {
      return new EdmStorageProvider(this.mContext);
    }

    public RestrictionPolicy getRestrictionPolicy() {
      return (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
    }

    public EnterpriseDumpHelper getEnterpriseDumpHelper() {
      return new EnterpriseDumpHelper(this.mContext);
    }

    public IWifiPolicy getIWifiPolicy() {
      return IWifiPolicy.Stub.asInterface(ServiceManager.getService("wifi_policy"));
    }

    public ApplicationPolicy getApplicationPolicy() {
      return (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
    }

    public EnterpriseDeviceManager getEnterpriseDeviceManager() {
      return EnterpriseDeviceManager.getInstance(this.mContext);
    }
  }

  public DexPolicy(Context context) {
    this(new Injector(context));
  }

  public DexPolicy(Injector injector) {
    this.mEDM = null;
    this.mEdmStorageProvider = null;
    this.restrictionPolicy = null;
    this.mIWifipolicy = null;
    this.mApplicationPolicy = null;
    this.blocker =
        new SemDesktopModeManager
            .DesktopModeBlocker() { // from class: com.android.server.enterprise.dex.DexPolicy.1
          public String onBlocked() {
            return DexPolicy.this.mContext.getString(R.string.gadget_host_error_inflating);
          }
        };
    this.mInjector = injector;
    Context context = injector.mContext;
    this.mContext = context;
    this.mEdmStorageProvider = injector.getEdmStorageProvider();
    this.restrictionPolicy = injector.getRestrictionPolicy();
    this.mEnterpriseDumpHelper = injector.getEnterpriseDumpHelper();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("com.samsung.android.desktopmode.action.ENTER_DESKTOP_MODE");
    intentFilter.addAction("com.samsung.android.desktopmode.action.EXIT_DESKTOP_MODE");
    intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
    context.registerReceiver(
        new BroadcastReceiver() { // from class: com.android.server.enterprise.dex.DexPolicy.2
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
              Log.d("DexPolicyService", "action is null!");
            }
            switch (action) {
              case "android.intent.action.LOCKED_BOOT_COMPLETED":
                Log.d("DexPolicyService", "ACTION_LOCKED_BOOT_COMPLETED");
                if (Utils.isEthernetOnlyApplied(DexPolicy.this.mEdmStorageProvider)
                    && !Utils.isDexActivated(DexPolicy.this.mContext)) {
                  DexPolicy.this.mContext_temp =
                      new ContextInfo(
                          Utils.getAdminUidForEthernetOnly(DexPolicy.this.mEdmStorageProvider));
                  DexPolicy dexPolicy = DexPolicy.this;
                  dexPolicy.applyEthernetOnly(dexPolicy.mContext_temp, false);
                }
                if (!Utils.isDexActivated(DexPolicy.this.mContext)) {
                  DexPolicy.this.exitDexModeSetPackageState();
                }
                if (DexPolicy.this.isDexDisabled() && !DexPolicy.isBlockerRegistered) {
                  DexPolicy.this.registerDexBlocker();
                  break;
                }
                break;
              case "com.samsung.android.desktopmode.action.ENTER_DESKTOP_MODE":
                Log.d("DexPolicyService", "dex enter ");
                break;
              case "com.samsung.android.desktopmode.action.EXIT_DESKTOP_MODE":
                Log.d("DexPolicyService", "dex exit ");
                break;
            }
          }
        },
        intentFilter);
    Log.d("DexPolicyService", "SEC_PRODUCT_FEATURE_COMMON_SUPPORT_KNOX_DESKTOP is true");
    SemDesktopModeManager semDesktopModeManager =
        (SemDesktopModeManager) context.getSystemService("desktopmode");
    if (semDesktopModeManager != null) {
      semDesktopModeManager.registerListener(
          new SemDesktopModeManager
              .DesktopModeListener() { // from class: com.android.server.enterprise.dex.DexPolicy.3
            public void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
              if (semDesktopModeState.state == 20) {
                if (semDesktopModeState.enabled == 3) {
                  Log.d("DexPolicyService", "listener - Dex Enabling");
                  if (DexPolicy.this.isDexDisabled()) {
                    DexPolicy.this.registerDexBlocker();
                  }
                  if (DexPolicy.this.isEthernetOnlyEnforced()) {
                    if (!Utils.isEthernetOnlyApplied(DexPolicy.this.mEdmStorageProvider)) {
                      DexPolicy.this.mContext_temp =
                          new ContextInfo(
                              Utils.getAdminUidForEthernetOnly(DexPolicy.this.mEdmStorageProvider));
                      DexPolicy dexPolicy = DexPolicy.this;
                      dexPolicy.applyEthernetOnly(dexPolicy.mContext_temp, true);
                    } else {
                      DexPolicy.this.showEthernetOnlyNotification(true);
                    }
                  }
                  Log.d(
                      "DexPolicyService",
                      "is Dex Activated : " + Utils.isDexActivated(DexPolicy.this.mContext));
                  DexPolicy.this.enterDexModeSetPackageState();
                }
                if (semDesktopModeState.enabled == 1) {
                  Log.d("DexPolicyService", "listener - Dex Disabling");
                  if (DexPolicy.this.isEthernetOnlyEnforced()) {
                    DexPolicy.this.mContext_temp =
                        new ContextInfo(
                            Utils.getAdminUidForEthernetOnly(DexPolicy.this.mEdmStorageProvider));
                    DexPolicy dexPolicy2 = DexPolicy.this;
                    dexPolicy2.applyEthernetOnly(dexPolicy2.mContext_temp, false);
                  }
                  Log.d(
                      "DexPolicyService",
                      "is Dex Activated : " + Utils.isDexActivated(DexPolicy.this.mContext));
                  DexPolicy.this.exitDexModeSetPackageState();
                }
              }
            }
          });
    }
  }

  public final void enforceDexPermission() {
    getEDM()
        .enforceActiveAdminPermission(
            new ArrayList(Arrays.asList(KnoxCustomManagerService.KNOX_DEX_PERMISSION)));
  }

  public final ContextInfo enforceDexPermission(ContextInfo contextInfo) {
    return getEDM()
        .enforceActiveAdminPermissionByContext(
            contextInfo,
            new ArrayList(Arrays.asList(KnoxCustomManagerService.KNOX_DEX_PERMISSION)));
  }

  public final ContextInfo enforceOwnerOnlyAndDexPermission(ContextInfo contextInfo) {
    return getEDM()
        .enforceOwnerOnlyAndActiveAdminPermission(
            contextInfo,
            new ArrayList(Arrays.asList(KnoxCustomManagerService.KNOX_DEX_PERMISSION)));
  }

  public final EnterpriseDeviceManager getEDM() {
    if (this.mEDM == null) {
      this.mEDM = this.mInjector.getEnterpriseDeviceManager();
    }
    return this.mEDM;
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminRemoved(int i) {
    if (isBlockerRegistered && !isDexDisabled()) {
      unRegisterDexBlocker();
    }
    if (Utils.isEthernetOnlyApplied(this.mEdmStorageProvider)) {
      return;
    }
    showEthernetOnlyNotification(false);
  }

  public final void getRestrictionPolicy() {
    this.restrictionPolicy =
        (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
  }

  public final ApplicationPolicy getApplicationPolicy() {
    if (this.mApplicationPolicy == null) {
      this.mApplicationPolicy = this.mInjector.getApplicationPolicy();
    }
    return this.mApplicationPolicy;
  }

  public boolean setDexDisabled(ContextInfo contextInfo, boolean z) {
    boolean putBoolean =
        this.mEdmStorageProvider.putBoolean(
            enforceOwnerOnlyAndDexPermission(contextInfo).mCallerUid,
            "DEX_POLICY",
            "dexDisabled",
            z);
    if (!isBlockerRegistered && z && putBoolean) {
      registerDexBlocker();
    }
    if (isBlockerRegistered && putBoolean && !isDexDisabled()) {
      unRegisterDexBlocker();
    }
    return putBoolean;
  }

  public final void registerDexBlocker() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      ((SemDesktopModeManager)
              this.mContext.getApplicationContext().getSystemService("desktopmode"))
          .registerBlocker(this.blocker);
      isBlockerRegistered = true;
      Log.d("DexPolicyService", "registerDexBlocker was registered");
    } catch (Exception unused) {
      Log.d("DexPolicyService", "registerDexBlocker was failed");
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public final void unRegisterDexBlocker() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      ((SemDesktopModeManager)
              this.mContext.getApplicationContext().getSystemService("desktopmode"))
          .unregisterBlocker(this.blocker);
      isBlockerRegistered = false;
      Log.d("DexPolicyService", "registerDexBlocker was unregistered");
    } catch (Exception unused) {
      Log.d("DexPolicyService", "unRegisterDexBlocker was failed");
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public boolean isDexDisabled() {
    try {
      Iterator it = this.mEdmStorageProvider.getBooleanList("DEX_POLICY", "dexDisabled").iterator();
      while (it.hasNext()) {
        if (((Boolean) it.next()).booleanValue()) {
          return true;
        }
      }
      return false;
    } catch (Exception e) {
      Log.e("DexPolicyService", "isDexDisabled : failed " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean isDexActivated() {
    return Utils.isDexActivated(this.mContext);
  }

  public boolean enforceEthernetOnly(ContextInfo contextInfo, boolean z) {
    ContextInfo enforceOwnerOnlyAndDexPermission = enforceOwnerOnlyAndDexPermission(contextInfo);
    boolean putBoolean =
        this.mEdmStorageProvider.putBoolean(
            enforceOwnerOnlyAndDexPermission.mCallerUid, "DEX_POLICY", "ethernetOnlyEnabled", z);
    if (Utils.isDexActivated(this.mContext)) {
      if (z) {
        applyEthernetOnly(enforceOwnerOnlyAndDexPermission, true);
      } else {
        applyEthernetOnly(enforceOwnerOnlyAndDexPermission, false);
      }
    }
    return putBoolean;
  }

  public boolean isEthernetOnlyEnforced() {
    try {
      Iterator it =
          this.mEdmStorageProvider.getBooleanList("DEX_POLICY", "ethernetOnlyEnabled").iterator();
      while (it.hasNext()) {
        if (((Boolean) it.next()).booleanValue()) {
          return true;
        }
      }
      return false;
    } catch (Exception e) {
      Log.e("DexPolicyService", "isEthernetOnlyEnforced : failed " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public final void storeCurrentPolicy(ContextInfo contextInfo) {
    boolean z;
    boolean z2;
    boolean z3;
    boolean z4;
    boolean z5;
    boolean z6 = true;
    try {
      z5 =
          this.mEdmStorageProvider.getBoolean(
              contextInfo.mCallerUid, "RESTRICTION", "cellularDataEnabled");
      try {
        z2 = this.mEdmStorageProvider.getBoolean(contextInfo.mCallerUid, "WIFI", "allowWifi");
      } catch (Exception e) {
        e = e;
        z2 = true;
        z3 = true;
      }
      try {
        z3 =
            this.mEdmStorageProvider.getBoolean(
                contextInfo.mCallerUid, "RESTRICTION", "usbTetheringEnabled");
      } catch (Exception e2) {
        e = e2;
        z3 = true;
        z4 = z3;
        z6 = z5;
        z = z4;
        Log.w("DexPolicyService", "storeCurrentPolicy() failed " + e);
        boolean z7 = z;
        z5 = z6;
        z6 = z7;
        this.mEdmStorageProvider.putBoolean(
            contextInfo.mCallerUid, "DEX_POLICY", "prevCellularData", z5);
        this.mEdmStorageProvider.putBoolean(contextInfo.mCallerUid, "DEX_POLICY", "prevWifi", z2);
        this.mEdmStorageProvider.putBoolean(
            contextInfo.mCallerUid, "DEX_POLICY", "prevUsbTethering", z3);
        this.mEdmStorageProvider.putBoolean(
            contextInfo.mCallerUid, "DEX_POLICY", "prevWifiTethering", z4);
        this.mEdmStorageProvider.putBoolean(
            contextInfo.mCallerUid, "DEX_POLICY", "prevBtTethering", z6);
      }
      try {
        z4 =
            this.mEdmStorageProvider.getBoolean(
                contextInfo.mCallerUid, "RESTRICTION", "wifiTetheringEnabled");
      } catch (Exception e3) {
        e = e3;
        z4 = true;
        z6 = z5;
        z = z4;
        Log.w("DexPolicyService", "storeCurrentPolicy() failed " + e);
        boolean z72 = z;
        z5 = z6;
        z6 = z72;
        this.mEdmStorageProvider.putBoolean(
            contextInfo.mCallerUid, "DEX_POLICY", "prevCellularData", z5);
        this.mEdmStorageProvider.putBoolean(contextInfo.mCallerUid, "DEX_POLICY", "prevWifi", z2);
        this.mEdmStorageProvider.putBoolean(
            contextInfo.mCallerUid, "DEX_POLICY", "prevUsbTethering", z3);
        this.mEdmStorageProvider.putBoolean(
            contextInfo.mCallerUid, "DEX_POLICY", "prevWifiTethering", z4);
        this.mEdmStorageProvider.putBoolean(
            contextInfo.mCallerUid, "DEX_POLICY", "prevBtTethering", z6);
      }
    } catch (Exception e4) {
      e = e4;
      z = true;
      z2 = true;
      z3 = true;
      z4 = true;
    }
    try {
      z6 =
          this.mEdmStorageProvider.getBoolean(
              contextInfo.mCallerUid, "RESTRICTION", "bluetoothTetheringEnabled");
      Log.d(
          "DexPolicyService",
          "store current status - cell - "
              + z5
              + "  wifi - "
              + z2
              + "  usbTether - "
              + z3
              + "  WifiTether - "
              + z4
              + "  BtTether - "
              + z6);
    } catch (Exception e5) {
      e = e5;
      z = z6;
      z6 = z5;
      Log.w("DexPolicyService", "storeCurrentPolicy() failed " + e);
      boolean z722 = z;
      z5 = z6;
      z6 = z722;
      this.mEdmStorageProvider.putBoolean(
          contextInfo.mCallerUid, "DEX_POLICY", "prevCellularData", z5);
      this.mEdmStorageProvider.putBoolean(contextInfo.mCallerUid, "DEX_POLICY", "prevWifi", z2);
      this.mEdmStorageProvider.putBoolean(
          contextInfo.mCallerUid, "DEX_POLICY", "prevUsbTethering", z3);
      this.mEdmStorageProvider.putBoolean(
          contextInfo.mCallerUid, "DEX_POLICY", "prevWifiTethering", z4);
      this.mEdmStorageProvider.putBoolean(
          contextInfo.mCallerUid, "DEX_POLICY", "prevBtTethering", z6);
    }
    this.mEdmStorageProvider.putBoolean(
        contextInfo.mCallerUid, "DEX_POLICY", "prevCellularData", z5);
    this.mEdmStorageProvider.putBoolean(contextInfo.mCallerUid, "DEX_POLICY", "prevWifi", z2);
    this.mEdmStorageProvider.putBoolean(
        contextInfo.mCallerUid, "DEX_POLICY", "prevUsbTethering", z3);
    this.mEdmStorageProvider.putBoolean(
        contextInfo.mCallerUid, "DEX_POLICY", "prevWifiTethering", z4);
    this.mEdmStorageProvider.putBoolean(
        contextInfo.mCallerUid, "DEX_POLICY", "prevBtTethering", z6);
  }

  public final void storeNetworkStatus(ContextInfo contextInfo) {
    TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
    WifiManager wifiManager = (WifiManager) this.mContext.getSystemService("wifi");
    boolean isDataEnabled = telephonyManager.isDataEnabled();
    boolean isWifiEnabled = wifiManager.isWifiEnabled();
    this.mEdmStorageProvider.putBoolean(
        contextInfo.mCallerUid, "DEX_POLICY", "prevDataStatus", isDataEnabled);
    this.mEdmStorageProvider.putBoolean(
        contextInfo.mCallerUid, "DEX_POLICY", "prevWifiStatus", isWifiEnabled);
  }

  /* JADX WARN: Removed duplicated region for block: B:11:0x005c A[Catch: all -> 0x0056, Exception -> 0x0058, TRY_LEAVE, TryCatch #1 {Exception -> 0x0058, blocks: (B:20:0x0052, B:11:0x005c), top: B:19:0x0052, outer: #0 }] */
  /* JADX WARN: Removed duplicated region for block: B:19:0x0052 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void restoreNetworkStatus(ContextInfo contextInfo) {
    boolean z;
    long clearCallingIdentity;
    TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
    WifiManager wifiManager = (WifiManager) this.mContext.getSystemService("wifi");
    boolean z2 = false;
    try {
      z =
          this.mEdmStorageProvider.getBoolean(
              contextInfo.mCallerUid, "DEX_POLICY", "prevDataStatus");
    } catch (Exception e) {
      e = e;
      z = false;
    }
    try {
      try {
        z2 =
            this.mEdmStorageProvider.getBoolean(
                contextInfo.mCallerUid, "DEX_POLICY", "prevWifiStatus");
      } catch (Exception e2) {
        e = e2;
        Log.w("DexPolicyService", "restoreNetworkStatus() failed " + e);
        clearCallingIdentity = Binder.clearCallingIdentity();
        if (z) {}
        if (z2) {}
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return;
      }
      if (z) {
        try {
          telephonyManager.setDataEnabled(true);
        } catch (Exception e3) {
          Log.e("DexPolicyService", "restoreNetworkStatus : failed " + e3.getMessage());
          e3.printStackTrace();
        }
      }
      if (z2) {
        wifiManager.setWifiEnabled(true);
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return;
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
    clearCallingIdentity = Binder.clearCallingIdentity();
  }

  public final void applyEthernetOnly(ContextInfo contextInfo, boolean z) {
    if (this.restrictionPolicy == null) {
      getRestrictionPolicy();
    }
    if (this.mIWifipolicy == null) {
      this.mIWifipolicy = this.mInjector.getIWifiPolicy();
    }
    if (this.mIWifipolicy == null) {
      Log.w("DexPolicyService", "failed to set applyEthernetOnly");
      return;
    }
    Log.d("DexPolicyService", "applyEthernetOnly - " + z);
    if (z) {
      storeCurrentPolicy(contextInfo);
      storeNetworkStatus(contextInfo);
      this.restrictionPolicy.setTethering(contextInfo, false);
      this.restrictionPolicy.setCellularData(contextInfo, false);
      try {
        this.mIWifipolicy.setWifiAllowed(contextInfo, false);
      } catch (RemoteException e) {
        Log.w("DexPolicyService", "failed to set applyEthernetOnly", e);
      }
      setEthernetOnlyApplied(contextInfo, true);
      showEthernetOnlyNotification(true);
      return;
    }
    setEthernetOnlyApplied(contextInfo, false);
    showEthernetOnlyNotification(false);
    if (isPrevCellDataEnabled()) {
      this.restrictionPolicy.setCellularData(contextInfo, true);
    }
    if (isPrevWifiEnabled()) {
      try {
        this.mIWifipolicy.setWifiAllowed(contextInfo, true);
      } catch (RemoteException e2) {
        Log.w("DexPolicyService", "failed to set applyEthernetOnly", e2);
      }
    }
    if (isPrevWifiTetheringEnabled()) {
      this.restrictionPolicy.setWifiTethering(contextInfo, true);
    }
    if (isPrevUsbTetheringEnabled()) {
      this.restrictionPolicy.setUsbTethering(contextInfo, true);
    }
    if (isPrevBtTetheringEnabled()) {
      this.restrictionPolicy.setBluetoothTethering(contextInfo, true);
    }
    restoreNetworkStatus(contextInfo);
  }

  public final void showEthernetOnlyNotification(boolean z) {
    String string = this.mContext.getString(R.string.geofencing_service);
    NotificationManager notificationManager =
        (NotificationManager) this.mContext.getSystemService("notification");
    if (notificationManager == null) {
      Log.d("DexPolicyService", "Failed to get NotificationManager");
      return;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      if (z) {
        Notification.Builder builder = new Notification.Builder(this.mContext, "MDM_DEXPOLICY");
        builder.setWhen(0L);
        builder.setSmallIcon(R.drawable.perm_group_aural);
        builder.setContentTitle("Dex Ethernet only mode");
        builder.setContentText(string);
        builder.setStyle(new Notification.BigTextStyle().bigText(string));
        builder.setPriority(2);
        builder.setOngoing(true);
        notificationManager.notify(4557, builder.build());
      } else {
        notificationManager.cancel(4557);
      }
    } catch (Exception unused) {
      Log.d("DexPolicyService", "showEthernetOnlyNotification was failed");
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public final boolean isPrevCellDataEnabled() {
    try {
      Iterator it =
          this.mEdmStorageProvider.getBooleanList("DEX_POLICY", "prevCellularData").iterator();
      while (it.hasNext()) {
        if (!((Boolean) it.next()).booleanValue()) {
          return false;
        }
      }
      return true;
    } catch (Exception e) {
      Log.e("DexPolicyService", "isPrevCellDataEnabled : failed " + e.getMessage());
      e.printStackTrace();
      return true;
    }
  }

  public final boolean isPrevWifiEnabled() {
    try {
      Iterator it = this.mEdmStorageProvider.getBooleanList("DEX_POLICY", "prevWifi").iterator();
      while (it.hasNext()) {
        if (!((Boolean) it.next()).booleanValue()) {
          return false;
        }
      }
      return true;
    } catch (Exception e) {
      Log.e("DexPolicyService", "isPrevWifiEnabled : failed " + e.getMessage());
      e.printStackTrace();
      return true;
    }
  }

  public final boolean isPrevUsbTetheringEnabled() {
    try {
      Iterator it =
          this.mEdmStorageProvider.getBooleanList("DEX_POLICY", "prevUsbTethering").iterator();
      while (it.hasNext()) {
        if (!((Boolean) it.next()).booleanValue()) {
          return false;
        }
      }
      return true;
    } catch (Exception e) {
      Log.e("DexPolicyService", "isPrevUsbTetheringEnabled : failed " + e.getMessage());
      e.printStackTrace();
      return true;
    }
  }

  public final boolean isPrevWifiTetheringEnabled() {
    try {
      Iterator it =
          this.mEdmStorageProvider.getBooleanList("DEX_POLICY", "prevWifiTethering").iterator();
      while (it.hasNext()) {
        if (!((Boolean) it.next()).booleanValue()) {
          return false;
        }
      }
      return true;
    } catch (Exception e) {
      Log.e("DexPolicyService", "isPrevWifiTetheringEnabled : failed " + e.getMessage());
      e.printStackTrace();
      return true;
    }
  }

  public final boolean isPrevBtTetheringEnabled() {
    try {
      Iterator it =
          this.mEdmStorageProvider.getBooleanList("DEX_POLICY", "prevBtTethering").iterator();
      while (it.hasNext()) {
        if (!((Boolean) it.next()).booleanValue()) {
          return false;
        }
      }
      return true;
    } catch (Exception e) {
      Log.e("DexPolicyService", "isPrevBtTetheringEnabled : failed " + e.getMessage());
      e.printStackTrace();
      return true;
    }
  }

  public final void setEthernetOnlyApplied(ContextInfo contextInfo, boolean z) {
    Log.d("DexPolicyService", "setEthernetOnlyApplied - " + z);
    this.mEdmStorageProvider.putBoolean(
        contextInfo.mCallerUid, "DEX_POLICY", "ethernetOnlyApplied", z);
  }

  public final void enterDexModeSetPackageState() {
    Log.d("DexPolicyService", "enterDexModeSetPackageState");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        List valuesList =
            this.mEdmStorageProvider.getValuesList("ADMIN", new String[] {"adminUid"});
        if (valuesList != null && !valuesList.isEmpty()) {
          Iterator it = valuesList.iterator();
          while (it.hasNext()) {
            Integer asInteger = ((ContentValues) it.next()).getAsInteger("adminUid");
            if (asInteger != null) {
              ContextInfo contextInfo = new ContextInfo(asInteger.intValue());
              Iterator it2 = getPackagesFromDisableList(contextInfo).iterator();
              while (it2.hasNext()) {
                disablePackage(contextInfo, (String) it2.next());
              }
            }
          }
        }
      } catch (Exception e) {
        Log.e("DexPolicyService", "enterDexModeSetPackageState : failed " + e.getMessage());
        e.printStackTrace();
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void exitDexModeSetPackageState() {
    Log.d("DexPolicyService", "exitDexModeSetPackageState");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        List valuesList =
            this.mEdmStorageProvider.getValuesList("ADMIN", new String[] {"adminUid"});
        Log.d(
            "DexPolicyService",
            "exitDexModeSetPackageState : Admin list size() : " + valuesList.size());
        if (!valuesList.isEmpty()) {
          Iterator it = valuesList.iterator();
          while (it.hasNext()) {
            Integer asInteger = ((ContentValues) it.next()).getAsInteger("adminUid");
            if (asInteger != null) {
              ContextInfo contextInfo = new ContextInfo(asInteger.intValue());
              List packagesFromDisableList = getPackagesFromDisableList(contextInfo);
              writePackageDisableList(contextInfo, null);
              Iterator it2 = packagesFromDisableList.iterator();
              while (it2.hasNext()) {
                enablePackage(contextInfo, (String) it2.next());
              }
              writePackageDisableList(contextInfo, packagesFromDisableList);
            }
          }
        }
      } catch (Exception e) {
        Log.e("DexPolicyService", "exitDexModeSetPackageState : failed " + e.getMessage());
        e.printStackTrace();
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final boolean writePackageDisableList(ContextInfo contextInfo, List list) {
    int i = contextInfo.mCallerUid;
    boolean z = false;
    try {
      ContentValues contentValues = new ContentValues();
      contentValues.put("adminUid", Integer.valueOf(i));
      ContentValues contentValues2 = new ContentValues();
      contentValues2.put("dexApplicationDisableList", Utils.serializeObject(list));
      if (this.mEdmStorageProvider.getCount("DEX_POLICY", contentValues) > 0) {
        z = this.mEdmStorageProvider.putValues("DEX_POLICY", contentValues2, contentValues);
        Log.d(
            "DexPolicyService",
            "writePackageList(dexApplicationDisableList) : update : ret : " + z);
      } else {
        contentValues2.put("adminUid", Integer.valueOf(i));
        z = this.mEdmStorageProvider.putValuesNoUpdate("DEX_POLICY", contentValues2);
        Log.d(
            "DexPolicyService",
            "writePackageList(dexApplicationDisableList) : insert : ret : " + z);
      }
    } catch (Exception e) {
      Log.e("DexPolicyService", "writePackageList : failed " + e.getMessage());
      e.printStackTrace();
    }
    return z;
  }

  public final void updatePackageControlState(String str, int i, boolean z) {
    if (getApplicationPolicy() != null) {
      getApplicationPolicy().updatePackageControlStateForDex(str, i, z);
    }
  }

  public final int getControlState(String str, int i) {
    if (getApplicationPolicy() != null) {
      return getApplicationPolicy().getDexControlState(str, i);
    }
    return 0;
  }

  public final void disablePackage(ContextInfo contextInfo, String str) {
    try {
      int i = contextInfo.mCallerUid;
      if (getApplicationPolicy().isPackageDisabled(i, str)) {
        return;
      }
      getApplicationPolicy().setApplicationState(contextInfo, str, false);
      updatePackageControlState(str, i, true);
    } catch (Exception e) {
      Log.e("DexPolicyService", "setControlStateMask : failed " + e.getMessage());
      e.printStackTrace();
    }
  }

  public final void enablePackage(ContextInfo contextInfo, String str) {
    try {
      int i = contextInfo.mCallerUid;
      if (isPackageDisabledInControlState(i, str)) {
        getApplicationPolicy().setApplicationState(contextInfo, str, true);
      }
      updatePackageControlState(str, i, false);
    } catch (Exception e) {
      Log.e("DexPolicyService", "setControlStateMask : failed " + e.getMessage());
      e.printStackTrace();
    }
  }

  public final boolean isPackageDisabledInControlState(int i, String str) {
    return (getControlState(str, i) & 2) == 2;
  }

  public int addPackageToDisableList(ContextInfo contextInfo, String str) {
    Log.d("DexPolicyService", "addPackageToDisableList");
    ContextInfo enforceOwnerOnlyAndDexPermission = enforceOwnerOnlyAndDexPermission(contextInfo);
    List packagesFromDisableList = getPackagesFromDisableList(enforceOwnerOnlyAndDexPermission);
    if (packagesFromDisableList.contains(str)) {
      Log.d("DexPolicyService", "addPackageToDisableList already blocked package");
      return 3;
    }
    if (!getApplicationPolicy().isApplicationInstalled(enforceOwnerOnlyAndDexPermission, str)) {
      Log.d("DexPolicyService", "addPackageToDisableList : " + str + " is not installed");
      return 2;
    }
    try {
      packagesFromDisableList.add(str);
      if (Utils.isDexActivated(this.mContext)) {
        disablePackage(enforceOwnerOnlyAndDexPermission, str);
      }
      return !writePackageDisableList(enforceOwnerOnlyAndDexPermission, packagesFromDisableList)
          ? 1
          : 0;
    } catch (Exception e) {
      Log.e("DexPolicyService", "addPackageToDisableList : failed " + e.getMessage());
      e.printStackTrace();
      return 1;
    }
  }

  public int removePackageFromDisableList(ContextInfo contextInfo, String str) {
    Log.d("DexPolicyService", "removePackageFromDisableList");
    ContextInfo enforceOwnerOnlyAndDexPermission = enforceOwnerOnlyAndDexPermission(contextInfo);
    List packagesFromDisableList = getPackagesFromDisableList(enforceOwnerOnlyAndDexPermission);
    if (!packagesFromDisableList.contains(str)) {
      Log.d("DexPolicyService", "removePackageFromDisableList not find blocked package name");
      return 4;
    }
    try {
      packagesFromDisableList.remove(str);
      boolean writePackageDisableList =
          writePackageDisableList(enforceOwnerOnlyAndDexPermission, packagesFromDisableList);
      if (Utils.isDexActivated(this.mContext)) {
        enablePackage(enforceOwnerOnlyAndDexPermission, str);
      }
      return !writePackageDisableList ? 1 : 0;
    } catch (Exception e) {
      Log.e("DexPolicyService", "removePackageFromDisableList : failed " + e.getMessage());
      e.printStackTrace();
      return 1;
    }
  }

  public List getPackagesFromDisableList(ContextInfo contextInfo) {
    Log.d("DexPolicyService", "getPackagesFromDisableList");
    int i = enforceDexPermission(contextInfo).mCallerUid;
    if (getApplicationPolicy() != null) {
      return getApplicationPolicy().getPackagesFromDisableListForDex(i);
    }
    return new ArrayList();
  }

  public boolean allowScreenTimeoutChange(ContextInfo contextInfo, boolean z) {
    return this.mEdmStorageProvider.putBoolean(
        enforceOwnerOnlyAndDexPermission(contextInfo).mCallerUid,
        "DEX_POLICY",
        "screenTimeoutChangeAllowed",
        z);
  }

  public boolean isScreenTimeoutChangeAllowed() {
    try {
      Iterator it =
          this.mEdmStorageProvider
              .getBooleanList("DEX_POLICY", "screenTimeoutChangeAllowed")
              .iterator();
      while (it.hasNext()) {
        if (!((Boolean) it.next()).booleanValue()) {
          return false;
        }
      }
      return true;
    } catch (Exception e) {
      Log.e("DexPolicyService", "isScreenTimeoutChangeAllowed : failed " + e.getMessage());
      e.printStackTrace();
      return true;
    }
  }

  public boolean enforceVirtualMacAddress(ContextInfo contextInfo, boolean z) {
    return this.mEdmStorageProvider.putBoolean(
        enforceOwnerOnlyAndDexPermission(contextInfo).mCallerUid,
        "DEX_POLICY",
        "useDexStationMacAddress",
        z);
  }

  public boolean isVirtualMacAddressEnforced() {
    try {
      Iterator it =
          this.mEdmStorageProvider
              .getBooleanList("DEX_POLICY", "useDexStationMacAddress")
              .iterator();
      while (it.hasNext()) {
        if (((Boolean) it.next()).booleanValue()) {
          return true;
        }
      }
      return false;
    } catch (Exception e) {
      Log.e("DexPolicyService", "isVirtualMacAddressEnforced : failed " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public String getVirtualMacAddress() {
    enforceDexPermission();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    String str = "";
    try {
      try {
        str =
            INetworkManagementService.Stub.asInterface(
                    ServiceManager.getService("network_management"))
                .getInterfaceConfig("eth0")
                .getHardwareAddress();
        Log.d("DexPolicyService", "getVirtualMacAddress : " + str);
      } catch (Exception e) {
        Log.e("DexPolicyService", "getVirtualMacAddress : failed " + e.getMessage());
        e.printStackTrace();
      }
      return str;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
      printWriter.println("Permission Denial: can't dump DexPolicy");
    } else {
      this.mEnterpriseDumpHelper.dumpTable(
          printWriter,
          "DEX_POLICY",
          new String[] {
            "adminUid",
            "dexDisabled",
            "ethernetOnlyEnabled",
            "prevCellularData",
            "prevWifi",
            "prevWifiTethering",
            "prevUsbTethering",
            "prevBtTethering",
            "prevDataStatus",
            "prevWifiStatus",
            "ethernetOnlyApplied",
            "screenTimeoutChangeAllowed",
            "useDexStationMacAddress"
          });
    }
  }
}
