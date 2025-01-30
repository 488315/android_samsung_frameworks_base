package com.android.server.net;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.INetd;
import android.net.INetdUnsolicitedEventListener;
import android.net.INetworkManagementEventObserver;
import android.net.ITetheringStatsProvider;
import android.net.InetAddresses;
import android.net.InterfaceConfiguration;
import android.net.InterfaceConfigurationParcel;
import android.net.IpPrefix;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkStack;
import android.net.NetworkStats;
import android.net.RouteInfo;
import android.net.RouteInfoParcel;
import android.net.TetherStatsParcel;
import android.net.UidRangeParcel;
import android.net.util.NetdService;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.PermissionEnforcer;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import com.android.internal.app.IBatteryStats;
import com.android.internal.net.INetdTetherEventListener;
import com.android.internal.net.IOemNetd;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.HexDump;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.XmlUtils;
import com.android.net.module.util.NetdUtils;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.google.android.collect.Maps;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes2.dex */
public class NetworkManagementService extends INetworkManagementService.Stub {
  public static final boolean DBG = Log.isLoggable("NetworkManagement", 3);
  public HashMap mActiveAlerts;
  public HashMap mActiveQuotas;
  public IBatteryStats mBatteryStats;
  public final Context mContext;
  public final Handler mDaemonHandler;
  public volatile boolean mDataSaverMode;
  public final Dependencies mDeps;
  public final SparseBooleanArray mFirewallChainStates;
  public volatile boolean mFirewallEnabled;
  public INetd mNetdService;
  public NetdTetherEventListener mNetdTetherEventListener;
  public final NetdUnsolicitedEventListener mNetdUnsolicitedEventListener;
  public final RemoteCallbackList mObservers;
  public IOemNetd mOemNetd;
  public final Object mQuotaLock;
  public final Object mRulesLock;
  public volatile boolean mStrictEnabled;
  public final HashMap mTetheringStatsProviders;
  public SparseBooleanArray mUidAllowOnMetered;
  public SparseIntArray mUidCleartextPolicy;
  public SparseIntArray mUidFirewallDozableRules;
  public SparseIntArray mUidFirewallLowPowerStandbyRules;
  public SparseIntArray mUidFirewallOemDenyRules;
  public SparseIntArray mUidFirewallPowerSaveRules;
  public SparseIntArray mUidFirewallRestrictedRules;
  public SparseIntArray mUidFirewallRules;
  public SparseIntArray mUidFirewallStandbyRules;
  public SparseBooleanArray mUidRejectOnMetered;

  public interface NetworkManagementEventCallback {
    void sendCallback(INetworkManagementEventObserver iNetworkManagementEventObserver);
  }

  public boolean isBandwidthControlEnabled() {
    return true;
  }

  public class Dependencies {
    public IBinder getService(String str) {
      return ServiceManager.getService(str);
    }

    public void registerLocalService(NetworkManagementInternal networkManagementInternal) {
      LocalServices.addService(NetworkManagementInternal.class, networkManagementInternal);
    }

    public INetd getNetd() {
      return NetdService.get();
    }

    public int getCallingUid() {
      return Binder.getCallingUid();
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public NetworkManagementService(Context context, Dependencies dependencies) {
    super(PermissionEnforcer.fromContext(context));
    this.mObservers = new RemoteCallbackList();
    HashMap newHashMap = Maps.newHashMap();
    this.mTetheringStatsProviders = newHashMap;
    this.mQuotaLock = new Object();
    this.mRulesLock = new Object();
    this.mActiveQuotas = Maps.newHashMap();
    this.mActiveAlerts = Maps.newHashMap();
    this.mUidRejectOnMetered = new SparseBooleanArray();
    this.mUidAllowOnMetered = new SparseBooleanArray();
    this.mUidCleartextPolicy = new SparseIntArray();
    this.mUidFirewallRules = new SparseIntArray();
    this.mUidFirewallStandbyRules = new SparseIntArray();
    this.mUidFirewallDozableRules = new SparseIntArray();
    this.mUidFirewallPowerSaveRules = new SparseIntArray();
    this.mUidFirewallOemDenyRules = new SparseIntArray();
    this.mUidFirewallRestrictedRules = new SparseIntArray();
    this.mUidFirewallLowPowerStandbyRules = new SparseIntArray();
    this.mFirewallChainStates = new SparseBooleanArray();
    Object[] objArr = 0;
    this.mNetdTetherEventListener = null;
    this.mContext = context;
    this.mDeps = dependencies;
    this.mDaemonHandler = new Handler(FgThread.get().getLooper());
    this.mNetdUnsolicitedEventListener = new NetdUnsolicitedEventListener();
    dependencies.registerLocalService(new LocalService());
    synchronized (newHashMap) {
      newHashMap.put(new NetdTetheringStatsProvider(), KnoxVpnFirewallHelper.NETD_SERVICE_NAME);
    }
  }

  public NetworkManagementService() {
    this.mObservers = new RemoteCallbackList();
    this.mTetheringStatsProviders = Maps.newHashMap();
    this.mQuotaLock = new Object();
    this.mRulesLock = new Object();
    this.mActiveQuotas = Maps.newHashMap();
    this.mActiveAlerts = Maps.newHashMap();
    this.mUidRejectOnMetered = new SparseBooleanArray();
    this.mUidAllowOnMetered = new SparseBooleanArray();
    this.mUidCleartextPolicy = new SparseIntArray();
    this.mUidFirewallRules = new SparseIntArray();
    this.mUidFirewallStandbyRules = new SparseIntArray();
    this.mUidFirewallDozableRules = new SparseIntArray();
    this.mUidFirewallPowerSaveRules = new SparseIntArray();
    this.mUidFirewallOemDenyRules = new SparseIntArray();
    this.mUidFirewallRestrictedRules = new SparseIntArray();
    this.mUidFirewallLowPowerStandbyRules = new SparseIntArray();
    this.mFirewallChainStates = new SparseBooleanArray();
    this.mNetdTetherEventListener = null;
    this.mContext = null;
    this.mDaemonHandler = null;
    this.mDeps = null;
    this.mNetdUnsolicitedEventListener = null;
  }

  public static NetworkManagementService create(Context context, Dependencies dependencies) {
    NetworkManagementService networkManagementService =
        new NetworkManagementService(context, dependencies);
    boolean z = DBG;
    if (z) {
      Slog.d("NetworkManagement", "Creating NetworkManagementService");
    }
    if (z) {
      Slog.d("NetworkManagement", "Connecting native netd service");
    }
    networkManagementService.connectNativeNetdService();
    if (z) {
      Slog.d("NetworkManagement", "Connected");
    }
    return networkManagementService;
  }

  public static NetworkManagementService create(Context context) {
    return create(context, new Dependencies());
  }

  public void systemReady() {
    if (DBG) {
      long currentTimeMillis = System.currentTimeMillis();
      prepareNativeDaemon();
      Slog.d(
          "NetworkManagement",
          "Prepared in " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
      return;
    }
    prepareNativeDaemon();
  }

  public final IBatteryStats getBatteryStats() {
    synchronized (this) {
      IBatteryStats iBatteryStats = this.mBatteryStats;
      if (iBatteryStats != null) {
        return iBatteryStats;
      }
      IBatteryStats asInterface =
          IBatteryStats.Stub.asInterface(this.mDeps.getService("batterystats"));
      this.mBatteryStats = asInterface;
      return asInterface;
    }
  }

  public void registerObserver(INetworkManagementEventObserver iNetworkManagementEventObserver) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    this.mObservers.register(iNetworkManagementEventObserver);
  }

  public void unregisterObserver(INetworkManagementEventObserver iNetworkManagementEventObserver) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    this.mObservers.unregister(iNetworkManagementEventObserver);
  }

  public final void invokeForAllObservers(
      NetworkManagementEventCallback networkManagementEventCallback) {
    int beginBroadcast = this.mObservers.beginBroadcast();
    for (int i = 0; i < beginBroadcast; i++) {
      try {
        networkManagementEventCallback.sendCallback(
            (INetworkManagementEventObserver) this.mObservers.getBroadcastItem(i));
      } catch (RemoteException | RuntimeException unused) {
      } catch (Throwable th) {
        this.mObservers.finishBroadcast();
        throw th;
      }
    }
    this.mObservers.finishBroadcast();
  }

  public final void notifyInterfaceStatusChanged(final String str, final boolean z) {
    invokeForAllObservers(
        new NetworkManagementEventCallback() { // from class:
                                               // com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda9
          @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
          public final void sendCallback(
              INetworkManagementEventObserver iNetworkManagementEventObserver) {
            iNetworkManagementEventObserver.interfaceStatusChanged(str, z);
          }
        });
  }

  public final void notifyInterfaceLinkStateChanged(final String str, final boolean z) {
    invokeForAllObservers(
        new NetworkManagementEventCallback() { // from class:
                                               // com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda11
          @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
          public final void sendCallback(
              INetworkManagementEventObserver iNetworkManagementEventObserver) {
            iNetworkManagementEventObserver.interfaceLinkStateChanged(str, z);
          }
        });
  }

  public final void notifyInterfaceAdded(final String str) {
    AuditLog.log(
        5,
        4,
        true,
        Process.myPid(),
        NetworkManagementService.class.getSimpleName(),
        String.format("Interface %s is tethered", str));
    invokeForAllObservers(
        new NetworkManagementEventCallback() { // from class:
                                               // com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda4
          @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
          public final void sendCallback(
              INetworkManagementEventObserver iNetworkManagementEventObserver) {
            iNetworkManagementEventObserver.interfaceAdded(str);
          }
        });
  }

  public final void notifyInterfaceRemoved(final String str) {
    this.mActiveAlerts.remove(str);
    this.mActiveQuotas.remove(str);
    AuditLog.log(
        5,
        4,
        true,
        Process.myPid(),
        NetworkManagementService.class.getSimpleName(),
        String.format("Interface %s is untethered", str));
    invokeForAllObservers(
        new NetworkManagementEventCallback() { // from class:
                                               // com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda10
          @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
          public final void sendCallback(
              INetworkManagementEventObserver iNetworkManagementEventObserver) {
            iNetworkManagementEventObserver.interfaceRemoved(str);
          }
        });
  }

  public final void notifyLimitReached(final String str, final String str2) {
    invokeForAllObservers(
        new NetworkManagementEventCallback() { // from class:
                                               // com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda1
          @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
          public final void sendCallback(
              INetworkManagementEventObserver iNetworkManagementEventObserver) {
            iNetworkManagementEventObserver.limitReached(str, str2);
          }
        });
  }

  public final void notifyInterfaceClassActivity(
      final int i, final boolean z, final long j, final int i2) {
    invokeForAllObservers(
        new NetworkManagementEventCallback() { // from class:
                                               // com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda5
          @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
          public final void sendCallback(
              INetworkManagementEventObserver iNetworkManagementEventObserver) {
            iNetworkManagementEventObserver.interfaceClassDataActivityChanged(i, z, j, i2);
          }
        });
  }

  public void registerTetheringStatsProvider(
      ITetheringStatsProvider iTetheringStatsProvider, String str) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    Objects.requireNonNull(iTetheringStatsProvider);
    synchronized (this.mTetheringStatsProviders) {
      this.mTetheringStatsProviders.put(iTetheringStatsProvider, str);
    }
  }

  public void unregisterTetheringStatsProvider(ITetheringStatsProvider iTetheringStatsProvider) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    synchronized (this.mTetheringStatsProviders) {
      this.mTetheringStatsProviders.remove(iTetheringStatsProvider);
    }
  }

  public void tetherLimitReached(ITetheringStatsProvider iTetheringStatsProvider) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    synchronized (this.mTetheringStatsProviders) {
      if (this.mTetheringStatsProviders.containsKey(iTetheringStatsProvider)) {
        this.mDaemonHandler.post(
            new Runnable() { // from class:
                             // com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                NetworkManagementService.this.lambda$tetherLimitReached$6();
              }
            });
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$tetherLimitReached$6() {
    notifyLimitReached("globalAlert", null);
  }

  public final void syncFirewallChainLocked(int i, String str) {
    SparseIntArray clone;
    synchronized (this.mRulesLock) {
      SparseIntArray uidFirewallRulesLR = getUidFirewallRulesLR(i);
      clone = uidFirewallRulesLR.clone();
      uidFirewallRulesLR.clear();
    }
    if (clone.size() > 0) {
      if (DBG) {
        Slog.d(
            "NetworkManagement",
            "Pushing " + clone.size() + " active firewall " + str + "UID rules");
      }
      for (int i2 = 0; i2 < clone.size(); i2++) {
        setFirewallUidRuleLocked(i, clone.keyAt(i2), clone.valueAt(i2));
      }
    }
  }

  public final void connectNativeNetdService() {
    INetd netd = this.mDeps.getNetd();
    this.mNetdService = netd;
    try {
      netd.registerUnsolicitedEventListener(this.mNetdUnsolicitedEventListener);
      if (DBG) {
        Slog.d("NetworkManagement", "Register unsolicited event listener");
      }
    } catch (RemoteException | ServiceSpecificException e) {
      Slog.e("NetworkManagement", "Failed to set Netd unsolicited event listener " + e);
    }
    try {
      this.mOemNetd = IOemNetd.Stub.asInterface(this.mNetdService.getOemNetd());
      if (DBG) {
        Slog.d("NetworkManagement", "Get OemNet listener");
      }
    } catch (RemoteException | ServiceSpecificException e2) {
      Slog.e("NetworkManagement", "Failed to get OemNetd listener " + e2);
    }
  }

  public final void prepareNativeDaemon() {
    SparseBooleanArray sparseBooleanArray;
    SparseBooleanArray sparseBooleanArray2;
    synchronized (this.mQuotaLock) {
      this.mStrictEnabled = true;
      setDataSaverModeEnabled(this.mDataSaverMode);
      int size = this.mActiveQuotas.size();
      if (size > 0) {
        if (DBG) {
          Slog.d("NetworkManagement", "Pushing " + size + " active quota rules");
        }
        HashMap hashMap = this.mActiveQuotas;
        this.mActiveQuotas = Maps.newHashMap();
        for (Map.Entry entry : hashMap.entrySet()) {
          setInterfaceQuota((String) entry.getKey(), ((Long) entry.getValue()).longValue());
        }
      }
      int size2 = this.mActiveAlerts.size();
      if (size2 > 0) {
        if (DBG) {
          Slog.d("NetworkManagement", "Pushing " + size2 + " active alert rules");
        }
        HashMap hashMap2 = this.mActiveAlerts;
        this.mActiveAlerts = Maps.newHashMap();
        for (Map.Entry entry2 : hashMap2.entrySet()) {
          setInterfaceAlert((String) entry2.getKey(), ((Long) entry2.getValue()).longValue());
        }
      }
      synchronized (this.mRulesLock) {
        int size3 = this.mUidRejectOnMetered.size();
        sparseBooleanArray = null;
        if (size3 > 0) {
          if (DBG) {
            Slog.d("NetworkManagement", "Pushing " + size3 + " UIDs to metered denylist rules");
          }
          sparseBooleanArray2 = this.mUidRejectOnMetered;
          this.mUidRejectOnMetered = new SparseBooleanArray();
        } else {
          sparseBooleanArray2 = null;
        }
        int size4 = this.mUidAllowOnMetered.size();
        if (size4 > 0) {
          if (DBG) {
            Slog.d("NetworkManagement", "Pushing " + size4 + " UIDs to metered allowlist rules");
          }
          sparseBooleanArray = this.mUidAllowOnMetered;
          this.mUidAllowOnMetered = new SparseBooleanArray();
        }
      }
      if (sparseBooleanArray2 != null) {
        for (int i = 0; i < sparseBooleanArray2.size(); i++) {
          setUidOnMeteredNetworkDenylist(
              sparseBooleanArray2.keyAt(i), sparseBooleanArray2.valueAt(i));
        }
      }
      if (sparseBooleanArray != null) {
        for (int i2 = 0; i2 < sparseBooleanArray.size(); i2++) {
          setUidOnMeteredNetworkAllowlist(
              sparseBooleanArray.keyAt(i2), sparseBooleanArray.valueAt(i2));
        }
      }
      int size5 = this.mUidCleartextPolicy.size();
      if (size5 > 0) {
        if (DBG) {
          Slog.d("NetworkManagement", "Pushing " + size5 + " active UID cleartext policies");
        }
        SparseIntArray sparseIntArray = this.mUidCleartextPolicy;
        this.mUidCleartextPolicy = new SparseIntArray();
        for (int i3 = 0; i3 < sparseIntArray.size(); i3++) {
          setUidCleartextNetworkPolicy(sparseIntArray.keyAt(i3), sparseIntArray.valueAt(i3));
        }
      }
      setFirewallEnabled(this.mFirewallEnabled);
      syncFirewallChainLocked(0, "");
      syncFirewallChainLocked(2, "standby ");
      syncFirewallChainLocked(1, "dozable ");
      syncFirewallChainLocked(3, "powersave ");
      syncFirewallChainLocked(4, "restricted ");
      syncFirewallChainLocked(5, "low power standby ");
      syncFirewallChainLocked(7, "fw_oem deny_1 ");
      int[] iArr = {2, 1, 3, 4, 5, 7};
      for (int i4 = 0; i4 < 6; i4++) {
        int i5 = iArr[i4];
        if (getFirewallChainState(i5)) {
          setFirewallChainEnabled(i5, true);
        }
      }
      try {
        Log.d("NetworkManagement", "makeBlockChildChain");
        this.mOemNetd.makeBlockChildChain();
        try {
          Log.d("NetworkManagement", "makeVideoCallChain");
          this.mOemNetd.makeVideoCallChain();
        } catch (RemoteException e) {
          throw e.rethrowAsRuntimeException();
        }
      } catch (RemoteException e2) {
        throw e2.rethrowAsRuntimeException();
      }
    }
    try {
      getBatteryStats().noteNetworkStatsEnabled();
    } catch (RemoteException unused) {
    }
  }

  public final void notifyAddressUpdated(final String str, final LinkAddress linkAddress) {
    invokeForAllObservers(
        new NetworkManagementEventCallback() { // from class:
                                               // com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda3
          @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
          public final void sendCallback(
              INetworkManagementEventObserver iNetworkManagementEventObserver) {
            iNetworkManagementEventObserver.addressUpdated(str, linkAddress);
          }
        });
  }

  public final void notifyAddressRemoved(final String str, final LinkAddress linkAddress) {
    invokeForAllObservers(
        new NetworkManagementEventCallback() { // from class:
                                               // com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda8
          @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
          public final void sendCallback(
              INetworkManagementEventObserver iNetworkManagementEventObserver) {
            iNetworkManagementEventObserver.addressRemoved(str, linkAddress);
          }
        });
  }

  public final void notifyInterfaceDnsServerInfo(
      final String str, final long j, final String[] strArr) {
    invokeForAllObservers(
        new NetworkManagementEventCallback() { // from class:
                                               // com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda2
          @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
          public final void sendCallback(
              INetworkManagementEventObserver iNetworkManagementEventObserver) {
            iNetworkManagementEventObserver.interfaceDnsServerInfo(str, j, strArr);
          }
        });
  }

  public final void notifyRouteChange(boolean z, final RouteInfo routeInfo) {
    if (z) {
      invokeForAllObservers(
          new NetworkManagementEventCallback() { // from class:
                                                 // com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda6
            @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
            public final void sendCallback(
                INetworkManagementEventObserver iNetworkManagementEventObserver) {
              iNetworkManagementEventObserver.routeUpdated(routeInfo);
            }
          });
    } else {
      invokeForAllObservers(
          new NetworkManagementEventCallback() { // from class:
                                                 // com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda7
            @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
            public final void sendCallback(
                INetworkManagementEventObserver iNetworkManagementEventObserver) {
              iNetworkManagementEventObserver.routeRemoved(routeInfo);
            }
          });
    }
  }

  public class NetdUnsolicitedEventListener extends INetdUnsolicitedEventListener.Stub {
    @Override // android.net.INetdUnsolicitedEventListener
    public String getInterfaceHash() {
      return "38614f80a23b92603d4851177e57c460aec1b606";
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public int getInterfaceVersion() {
      return 13;
    }

    public NetdUnsolicitedEventListener() {}

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceClassActivityChanged(
        final boolean z, final int i, long j, final int i2) {
      if (j <= 0) {
        j = SystemClock.elapsedRealtimeNanos();
      }
      final long j2 = j;
      NetworkManagementService.this.mDaemonHandler.post(
          new Runnable() { // from class:
                           // com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              NetworkManagementService.NetdUnsolicitedEventListener.this
                  .lambda$onInterfaceClassActivityChanged$0(i, z, j2, i2);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInterfaceClassActivityChanged$0(
        int i, boolean z, long j, int i2) {
      NetworkManagementService.this.notifyInterfaceClassActivity(i, z, j, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onQuotaLimitReached$1(String str, String str2) {
      NetworkManagementService.this.notifyLimitReached(str, str2);
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onQuotaLimitReached(final String str, final String str2) {
      NetworkManagementService.this.mDaemonHandler.post(
          new Runnable() { // from class:
                           // com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              NetworkManagementService.NetdUnsolicitedEventListener.this
                  .lambda$onQuotaLimitReached$1(str, str2);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInterfaceDnsServerInfo$2(
        String str, long j, String[] strArr) {
      NetworkManagementService.this.notifyInterfaceDnsServerInfo(str, j, strArr);
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceDnsServerInfo(final String str, final long j, final String[] strArr) {
      NetworkManagementService.this.mDaemonHandler.post(
          new Runnable() { // from class:
                           // com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
              NetworkManagementService.NetdUnsolicitedEventListener.this
                  .lambda$onInterfaceDnsServerInfo$2(str, j, strArr);
            }
          });
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceAddressUpdated(String str, final String str2, int i, int i2) {
      final LinkAddress linkAddress = new LinkAddress(str, i, i2);
      NetworkManagementService.this.mDaemonHandler.post(
          new Runnable() { // from class:
                           // com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              NetworkManagementService.NetdUnsolicitedEventListener.this
                  .lambda$onInterfaceAddressUpdated$3(str2, linkAddress);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInterfaceAddressUpdated$3(
        String str, LinkAddress linkAddress) {
      NetworkManagementService.this.notifyAddressUpdated(str, linkAddress);
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceAddressRemoved(String str, final String str2, int i, int i2) {
      final LinkAddress linkAddress = new LinkAddress(str, i, i2);
      NetworkManagementService.this.mDaemonHandler.post(
          new Runnable() { // from class:
                           // com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              NetworkManagementService.NetdUnsolicitedEventListener.this
                  .lambda$onInterfaceAddressRemoved$4(str2, linkAddress);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInterfaceAddressRemoved$4(
        String str, LinkAddress linkAddress) {
      NetworkManagementService.this.notifyAddressRemoved(str, linkAddress);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInterfaceAdded$5(String str) {
      NetworkManagementService.this.notifyInterfaceAdded(str);
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceAdded(final String str) {
      NetworkManagementService.this.mDaemonHandler.post(
          new Runnable() { // from class:
                           // com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
              NetworkManagementService.NetdUnsolicitedEventListener.this.lambda$onInterfaceAdded$5(
                  str);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInterfaceRemoved$6(String str) {
      NetworkManagementService.this.notifyInterfaceRemoved(str);
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceRemoved(final String str) {
      NetworkManagementService.this.mDaemonHandler.post(
          new Runnable() { // from class:
                           // com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              NetworkManagementService.NetdUnsolicitedEventListener.this
                  .lambda$onInterfaceRemoved$6(str);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInterfaceChanged$7(String str, boolean z) {
      NetworkManagementService.this.notifyInterfaceStatusChanged(str, z);
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceChanged(final String str, final boolean z) {
      NetworkManagementService.this.mDaemonHandler.post(
          new Runnable() { // from class:
                           // com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
              NetworkManagementService.NetdUnsolicitedEventListener.this
                  .lambda$onInterfaceChanged$7(str, z);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInterfaceLinkStateChanged$8(String str, boolean z) {
      NetworkManagementService.this.notifyInterfaceLinkStateChanged(str, z);
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceLinkStateChanged(final String str, final boolean z) {
      NetworkManagementService.this.mDaemonHandler.post(
          new Runnable() { // from class:
                           // com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
              NetworkManagementService.NetdUnsolicitedEventListener.this
                  .lambda$onInterfaceLinkStateChanged$8(str, z);
            }
          });
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onRouteChanged(final boolean z, String str, String str2, String str3) {
      final RouteInfo routeInfo =
          new RouteInfo(
              new IpPrefix(str),
              "".equals(str2) ? null : InetAddresses.parseNumericAddress(str2),
              str3,
              1);
      NetworkManagementService.this.mDaemonHandler.post(
          new Runnable() { // from class:
                           // com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
              NetworkManagementService.NetdUnsolicitedEventListener.this.lambda$onRouteChanged$9(
                  z, routeInfo);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRouteChanged$9(boolean z, RouteInfo routeInfo) {
      NetworkManagementService.this.notifyRouteChange(z, routeInfo);
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onStrictCleartextDetected(int i, String str) {
      ActivityManager.getService().notifyCleartextNetwork(i, HexDump.hexStringToByteArray(str));
    }
  }

  public String[] listInterfaces() {
    NetworkStack.checkNetworkStackPermissionOr(
        this.mContext, new String[] {"android.permission.CONNECTIVITY_INTERNAL"});
    try {
      return this.mNetdService.interfaceGetList();
    } catch (RemoteException | ServiceSpecificException e) {
      AuditLog.log(
          3,
          4,
          false,
          Process.myPid(),
          NetworkManagementService.class.getSimpleName(),
          String.format("Error listing interfaces: %s", e));
      throw new IllegalStateException(e);
    }
  }

  public void setDnsForwardersForKnoxVpn(int i, String[] strArr) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      this.mNetdService.tetherDnsSet(i, strArr);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public final void sendusbTetheringUpdate() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      Intent intent = new Intent();
      intent.setAction("com.samsung.android.knox.intent.action.INTERFACE_STATUS_INTERNAL");
      intent.putExtra("com.samsung.android.knox.intent.extra.ACTION_INTERNAL", "tethering_info");
      this.mContext.sendBroadcast(intent, "com.samsung.android.knox.permission.KNOX_VPN_INTERNAL");
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setNetworkInfo(int i, boolean z, int i2) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      this.mOemNetd.setNetworkInfo(i, z, i2);
    } catch (Exception unused) {
      Log.e("NetworkManagement", "setNetworkInfo failed for chained vpn profile");
    }
  }

  public void addOrRemoveSystemAppFromDataSaverWhitelist(boolean z, int i) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      if (z) {
        this.mNetdService.bandwidthAddNiceApp(i);
      } else {
        this.mNetdService.bandwidthRemoveNiceApp(i);
      }
    } catch (RemoteException | ServiceSpecificException unused) {
      Log.e("NetworkManagement", "addOrRemoveSystemAppFromDataSaverWhitelist Error");
    }
  }

  public void updateInputFilterExemptRules(int i, int i2) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      this.mOemNetd.updateInputFilterExemptRules(i, i2);
    } catch (Exception unused) {
      Log.e("NetworkManagement", "updateInputFilterExemptRules failed for vpn profile");
    }
  }

  public void updateInputFilterUserWideRules(int[] iArr, int i, int i2) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      this.mOemNetd.updateInputFilterUserWideRules(iArr, i, i2);
    } catch (Exception unused) {
      Log.e("NetworkManagement", "updateInputFilterUserWideRules failed for vpn profile");
    }
  }

  public void updateInputFilterAppWideRules(int[] iArr, int i, int i2) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      this.mOemNetd.updateInputFilterAppWideRules(iArr, i, i2);
    } catch (Exception unused) {
      Log.e("NetworkManagement", "updateInputFilterAppWideRules failed for vpn profile");
    }
  }

  public void clearEbpfMap(int i) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      this.mOemNetd.clearEbpfMap(i);
    } catch (Exception unused) {
      Log.e("NetworkManagement", "clearEbpfMap failed");
    }
  }

  public String runKnoxFirewallRulesCommand(int i, String str) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      return this.mOemNetd.runKnoxFirewallRulesCommand(i, str);
    } catch (Exception unused) {
      Log.e("NetworkManagement", "runKnoxFirewallRulesCommand failed for vpn profile");
      return null;
    }
  }

  public void runKnoxRulesCommand(int i, String[] strArr) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      this.mOemNetd.runKnoxRulesCommand(i, strArr);
    } catch (Exception unused) {
      Log.e("NetworkManagement", "runKnoxRulesCommand failed for vpn profile");
    }
  }

  public void enableKnoxVpnFlagForTether(boolean z) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      this.mOemNetd.enableKnoxVpnFlagForTether(z);
    } catch (Exception unused) {
      Log.e("NetworkManagement", "enableKnoxVpnFlagForTether failed for chained vpn profile");
    }
  }

  public void registerNetdTetherEventListener() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    if (this.mNetdTetherEventListener == null) {
      Log.d("NetworkManagement", "Initializing NetdTetherEventListener");
      this.mNetdTetherEventListener = new NetdTetherEventListener();
    }
    try {
      this.mOemNetd.registerNetdTetherEventListener(this.mNetdTetherEventListener);
    } catch (Exception unused) {
      Log.e("NetworkManagement", "registerNetdTetherEventListener failed ");
    }
  }

  public void unregisterNetdTetherEventListener() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      this.mOemNetd.unregisterNetdTetherEventListener();
    } catch (Exception unused) {
      Log.e("NetworkManagement", "unregisterNetdTetherEventListener failed ");
    }
  }

  public void setKnoxVpn(int i, boolean z) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      this.mOemNetd.setKnoxVpn(i, z);
    } catch (Exception unused) {
      Log.e("NetworkManagement", "setKnoxVpn failed for vpn profile");
    }
  }

  public class NetdTetherEventListener extends INetdTetherEventListener.Stub {
    @Override // com.android.internal.net.INetdTetherEventListener
    public void onTetherStop() {}

    public NetdTetherEventListener() {}

    @Override // com.android.internal.net.INetdTetherEventListener
    public void onTetherStart() {
      Log.d("NetworkManagement", "send intent to KVES to inform tether has started");
      NetworkManagementService.this.sendusbTetheringUpdate();
    }
  }

  public void spegRestrictNetworkConnection(int i, boolean z) {
    enforceSystemUid();
    try {
      this.mOemNetd.spegRestrictNetworkConnection(i, z);
    } catch (RemoteException | ServiceSpecificException e) {
      Log.e("SPEG", "spegRestrictNetworkConnection Error", e);
    }
  }

  public static InterfaceConfigurationParcel toStableParcel(
      InterfaceConfiguration interfaceConfiguration, String str) {
    InterfaceConfigurationParcel interfaceConfigurationParcel = new InterfaceConfigurationParcel();
    interfaceConfigurationParcel.ifName = str;
    String hardwareAddress = interfaceConfiguration.getHardwareAddress();
    if (!TextUtils.isEmpty(hardwareAddress)) {
      interfaceConfigurationParcel.hwAddr = hardwareAddress;
    } else {
      interfaceConfigurationParcel.hwAddr = "";
    }
    interfaceConfigurationParcel.ipv4Addr =
        interfaceConfiguration.getLinkAddress().getAddress().getHostAddress();
    interfaceConfigurationParcel.prefixLength =
        interfaceConfiguration.getLinkAddress().getPrefixLength();
    ArrayList arrayList = new ArrayList();
    Iterator it = interfaceConfiguration.getFlags().iterator();
    while (it.hasNext()) {
      arrayList.add((String) it.next());
    }
    interfaceConfigurationParcel.flags = (String[]) arrayList.toArray(new String[0]);
    return interfaceConfigurationParcel;
  }

  public static InterfaceConfiguration fromStableParcel(
      InterfaceConfigurationParcel interfaceConfigurationParcel) {
    InterfaceConfiguration interfaceConfiguration = new InterfaceConfiguration();
    interfaceConfiguration.setHardwareAddress(interfaceConfigurationParcel.hwAddr);
    interfaceConfiguration.setLinkAddress(
        new LinkAddress(
            InetAddresses.parseNumericAddress(interfaceConfigurationParcel.ipv4Addr),
            interfaceConfigurationParcel.prefixLength));
    for (String str : interfaceConfigurationParcel.flags) {
      interfaceConfiguration.setFlag(str);
    }
    return interfaceConfiguration;
  }

  public InterfaceConfiguration getInterfaceConfig(String str) {
    NetworkStack.checkNetworkStackPermissionOr(
        this.mContext, new String[] {"android.permission.CONNECTIVITY_INTERNAL"});
    try {
      try {
        return fromStableParcel(this.mNetdService.interfaceGetCfg(str));
      } catch (IllegalArgumentException e) {
        throw new IllegalStateException("Invalid InterfaceConfigurationParcel", e);
      }
    } catch (RemoteException | ServiceSpecificException e2) {
      throw new IllegalStateException(e2);
    }
  }

  public void setInterfaceConfig(String str, InterfaceConfiguration interfaceConfiguration) {
    NetworkStack.checkNetworkStackPermissionOr(
        this.mContext, new String[] {"android.permission.CONNECTIVITY_INTERNAL"});
    LinkAddress linkAddress = interfaceConfiguration.getLinkAddress();
    if (linkAddress == null || linkAddress.getAddress() == null) {
      throw new IllegalStateException("Null LinkAddress given");
    }
    try {
      this.mNetdService.interfaceSetCfg(toStableParcel(interfaceConfiguration, str));
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setInterfaceDown(String str) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    InterfaceConfiguration interfaceConfig = getInterfaceConfig(str);
    interfaceConfig.setInterfaceDown();
    setInterfaceConfig(str, interfaceConfig);
  }

  public void setInterfaceUp(String str) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    InterfaceConfiguration interfaceConfig = getInterfaceConfig(str);
    interfaceConfig.setInterfaceUp();
    setInterfaceConfig(str, interfaceConfig);
  }

  public void setInterfaceIpv6PrivacyExtensions(String str, boolean z) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      this.mNetdService.interfaceSetIPv6PrivacyExtensions(str, z);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void clearInterfaceAddresses(String str) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      this.mNetdService.interfaceClearAddrs(str);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void enableIpv6(String str) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      this.mNetdService.interfaceSetEnableIPv6(str, true);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setAutoConf(String str, boolean z) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      this.mOemNetd.interfaceSetAutoConf(str, z);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void disableDAD(String str) {
    Slog.d("NetworkManagement", "disableDAD ifName " + str);
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      this.mNetdService.setProcSysNet(6, 1, str, "accept_dad", "0");
      this.mNetdService.setProcSysNet(6, 1, str, "dad_transmits", "0");
    } catch (Exception e) {
      throw new RemoteException(e.getMessage());
    }
  }

  public void setIPv6AddrGenMode(String str, int i) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      this.mNetdService.setIPv6AddrGenMode(str, i);
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  public void disableIpv6(String str) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      this.mNetdService.interfaceSetEnableIPv6(str, false);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void addRoute(int i, RouteInfo routeInfo) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    NetdUtils.modifyRoute(this.mNetdService, NetdUtils.ModifyOperation.ADD, i, routeInfo);
  }

  public void removeRoute(int i, RouteInfo routeInfo) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    NetdUtils.modifyRoute(this.mNetdService, NetdUtils.ModifyOperation.REMOVE, i, routeInfo);
  }

  public void shutdown() {
    super.shutdown_enforcePermission();
    Slog.i("NetworkManagement", "Shutting down");
  }

  public boolean getIpForwardingEnabled() {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      return this.mNetdService.ipfwdEnabled();
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setIpForwardingEnabled(boolean z) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      if (z) {
        this.mNetdService.ipfwdEnableForwarding("tethering");
      } else {
        this.mNetdService.ipfwdDisableForwarding("tethering");
      }
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void startTethering(String[] strArr) {
    startTetheringWithConfiguration(true, strArr);
  }

  public void startTetheringWithConfiguration(boolean z, String[] strArr) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      NetdUtils.tetherStart(this.mNetdService, z, strArr);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void stopTethering() {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      this.mNetdService.tetherStop();
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public boolean isTetheringStarted() {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      return this.mNetdService.tetherIsEnabled();
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void tetherInterface(String str) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      LinkAddress linkAddress = getInterfaceConfig(str).getLinkAddress();
      NetdUtils.tetherInterface(
          this.mNetdService,
          str,
          new IpPrefix(linkAddress.getAddress(), linkAddress.getPrefixLength()));
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void untetherInterface(String str) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      NetdUtils.untetherInterface(this.mNetdService, str);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public String[] listTetheredInterfaces() {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      return this.mNetdService.tetherInterfaceList();
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public String[] getDnsForwarders() {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      return this.mNetdService.tetherDnsList();
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public final void modifyInterfaceForward(boolean z, String str, String str2) {
    try {
      if (z) {
        this.mNetdService.ipfwdAddInterfaceForward(str, str2);
      } else {
        this.mNetdService.ipfwdRemoveInterfaceForward(str, str2);
      }
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void startInterfaceForwarding(String str, String str2) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    modifyInterfaceForward(true, str, str2);
  }

  public void stopInterfaceForwarding(String str, String str2) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    modifyInterfaceForward(false, str, str2);
  }

  public void enableNat(String str, String str2) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      this.mNetdService.tetherAddForward(str, str2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void disableNat(String str, String str2) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      this.mNetdService.tetherRemoveForward(str, str2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setInterfaceQuota(String str, long j) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    synchronized (this.mQuotaLock) {
      if (this.mActiveQuotas.containsKey(str)) {
        throw new IllegalStateException("iface " + str + " already has quota");
      }
      try {
        this.mNetdService.bandwidthSetInterfaceQuota(str, j);
        this.mActiveQuotas.put(str, Long.valueOf(j));
        synchronized (this.mTetheringStatsProviders) {
          for (ITetheringStatsProvider iTetheringStatsProvider :
              this.mTetheringStatsProviders.keySet()) {
            try {
              iTetheringStatsProvider.setInterfaceQuota(str, j);
            } catch (RemoteException e) {
              Log.e(
                  "NetworkManagement",
                  "Problem setting tethering data limit on provider "
                      + ((String) this.mTetheringStatsProviders.get(iTetheringStatsProvider))
                      + ": "
                      + e);
            }
          }
        }
      } catch (RemoteException | ServiceSpecificException e2) {
        throw new IllegalStateException(e2);
      }
    }
  }

  public void removeInterfaceQuota(String str) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    synchronized (this.mQuotaLock) {
      if (this.mActiveQuotas.containsKey(str)) {
        this.mActiveQuotas.remove(str);
        this.mActiveAlerts.remove(str);
        try {
          this.mNetdService.bandwidthRemoveInterfaceQuota(str);
          synchronized (this.mTetheringStatsProviders) {
            for (ITetheringStatsProvider iTetheringStatsProvider :
                this.mTetheringStatsProviders.keySet()) {
              try {
                iTetheringStatsProvider.setInterfaceQuota(str, -1L);
              } catch (RemoteException e) {
                Log.e(
                    "NetworkManagement",
                    "Problem removing tethering data limit on provider "
                        + ((String) this.mTetheringStatsProviders.get(iTetheringStatsProvider))
                        + ": "
                        + e);
              }
            }
          }
        } catch (RemoteException | ServiceSpecificException e2) {
          throw new IllegalStateException(e2);
        }
      }
    }
  }

  public void setInterfaceAlert(String str, long j) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    if (!this.mActiveQuotas.containsKey(str)) {
      throw new IllegalStateException("setting alert requires existing quota on iface");
    }
    synchronized (this.mQuotaLock) {
      if (this.mActiveAlerts.containsKey(str)) {
        throw new IllegalStateException("iface " + str + " already has alert");
      }
      try {
        this.mNetdService.bandwidthSetInterfaceAlert(str, j);
        this.mActiveAlerts.put(str, Long.valueOf(j));
      } catch (RemoteException | ServiceSpecificException e) {
        throw new IllegalStateException(e);
      }
    }
  }

  public void removeInterfaceAlert(String str) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    synchronized (this.mQuotaLock) {
      if (this.mActiveAlerts.containsKey(str)) {
        try {
          this.mNetdService.bandwidthRemoveInterfaceAlert(str);
          this.mActiveAlerts.remove(str);
        } catch (RemoteException | ServiceSpecificException e) {
          throw new IllegalStateException(e);
        }
      }
    }
  }

  public void setGlobalAlert(long j) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      this.mNetdService.bandwidthSetGlobalAlert(j);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public final void setUidOnMeteredNetworkList(int i, boolean z, boolean z2) {
    SparseBooleanArray sparseBooleanArray;
    boolean z3;
    NetworkStack.checkNetworkStackPermission(this.mContext);
    synchronized (this.mQuotaLock) {
      synchronized (this.mRulesLock) {
        sparseBooleanArray = z ? this.mUidAllowOnMetered : this.mUidRejectOnMetered;
        z3 = sparseBooleanArray.get(i, false);
      }
      if (z3 == z2) {
        return;
      }
      Trace.traceBegin(2097152L, "inetd bandwidth");
      ConnectivityManager connectivityManager =
          (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
      try {
        try {
          if (z) {
            if (z2) {
              connectivityManager.addUidToMeteredNetworkAllowList(i);
            } else {
              connectivityManager.removeUidFromMeteredNetworkAllowList(i);
            }
          } else if (z2) {
            connectivityManager.addUidToMeteredNetworkDenyList(i);
          } else {
            connectivityManager.removeUidFromMeteredNetworkDenyList(i);
          }
          synchronized (this.mRulesLock) {
            if (z2) {
              sparseBooleanArray.put(i, true);
            } else {
              sparseBooleanArray.delete(i);
            }
          }
        } catch (RuntimeException e) {
          throw new IllegalStateException(e);
        }
      } finally {
        Trace.traceEnd(2097152L);
      }
    }
  }

  public void setUidOnMeteredNetworkDenylist(int i, boolean z) {
    setUidOnMeteredNetworkList(i, false, z);
  }

  public void setUidOnMeteredNetworkAllowlist(int i, boolean z) {
    setUidOnMeteredNetworkList(i, true, z);
  }

  public boolean setDataSaverModeEnabled(boolean z) {
    super.setDataSaverModeEnabled_enforcePermission();
    if (DBG) {
      Log.d("NetworkManagement", "setDataSaverMode: " + z);
    }
    synchronized (this.mQuotaLock) {
      if (this.mDataSaverMode == z) {
        Log.w("NetworkManagement", "setDataSaverMode(): already " + this.mDataSaverMode);
        return true;
      }
      Trace.traceBegin(2097152L, "bandwidthEnableDataSaver");
      try {
        boolean bandwidthEnableDataSaver = this.mNetdService.bandwidthEnableDataSaver(z);
        if (bandwidthEnableDataSaver) {
          this.mDataSaverMode = z;
        } else {
          Log.w("NetworkManagement", "setDataSaverMode(" + z + "): netd command silently failed");
        }
        return bandwidthEnableDataSaver;
      } catch (RemoteException e) {
        Log.w("NetworkManagement", "setDataSaverMode(" + z + "): netd command failed", e);
        return false;
      } finally {
        Trace.traceEnd(2097152L);
      }
    }
  }

  public final void applyUidCleartextNetworkPolicy(int i, int i2) {
    int i3 = 1;
    if (i2 != 0) {
      if (i2 == 1) {
        i3 = 2;
      } else {
        if (i2 != 2) {
          throw new IllegalArgumentException("Unknown policy " + i2);
        }
        i3 = 3;
      }
    }
    try {
      this.mNetdService.strictUidCleartextPenalty(i, i3);
      this.mUidCleartextPolicy.put(i, i2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setUidCleartextNetworkPolicy(int i, int i2) {
    if (this.mDeps.getCallingUid() != i) {
      NetworkStack.checkNetworkStackPermission(this.mContext);
    }
    synchronized (this.mQuotaLock) {
      int i3 = this.mUidCleartextPolicy.get(i, 0);
      if (i3 == i2) {
        return;
      }
      if (!this.mStrictEnabled) {
        this.mUidCleartextPolicy.put(i, i2);
        return;
      }
      if (i3 != 0 && i2 != 0) {
        applyUidCleartextNetworkPolicy(i, 0);
      }
      applyUidCleartextNetworkPolicy(i, i2);
    }
  }

  public class NetdTetheringStatsProvider extends ITetheringStatsProvider.Stub {
    public void setInterfaceQuota(String str, long j) {}

    public NetdTetheringStatsProvider() {}

    public NetworkStats getTetherStats(int i) {
      throw new UnsupportedOperationException();
    }
  }

  public NetworkStats getNetworkStatsTethering(int i) {
    throw new UnsupportedOperationException();
  }

  public void setFirewallEnabled(boolean z) {
    enforceSystemUid();
    try {
      this.mNetdService.firewallSetFirewallType(z ? 0 : 1);
      this.mFirewallEnabled = z;
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public boolean isFirewallEnabled() {
    enforceSystemUid();
    return this.mFirewallEnabled;
  }

  public void setFirewallInterfaceRule(String str, boolean z) {
    enforceSystemUid();
    Preconditions.checkState(this.mFirewallEnabled);
    try {
      this.mNetdService.firewallSetInterfaceRule(str, z ? 1 : 2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void closeSocketsForFreecess(int i, String str) {
    enforceSystemUid();
  }

  public void closeSocketsForUids(int[] iArr) {
    for (int i : iArr) {
      closeSocketsForUid(i);
    }
  }

  public void closeSocketsForUid(int i) {
    try {
      this.mNetdService.socketDestroy(new UidRangeParcel[] {new UidRangeParcel(i, i)}, new int[0]);
    } catch (RemoteException | ServiceSpecificException e) {
      Slog.e("NetworkManagement", "Error closing sockets for uid=" + i + ": " + e);
    }
  }

  public void setFirewallChainEnabled(int i, boolean z) {
    enforceSystemUid();
    synchronized (this.mQuotaLock) {
      synchronized (this.mRulesLock) {
        if (getFirewallChainState(i) == z) {
          return;
        }
        setFirewallChainState(i, z);
        String firewallChainName = getFirewallChainName(i);
        if (i == 0) {
          throw new IllegalArgumentException("Bad child chain: " + firewallChainName);
        }
        try {
          ((ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class))
              .setFirewallChainEnabled(i, z);
        } catch (RuntimeException e) {
          throw new IllegalStateException(e);
        }
      }
    }
  }

  public final String getFirewallChainName(int i) {
    if (i == 1) {
      return "dozable";
    }
    if (i == 2) {
      return "standby";
    }
    if (i == 3) {
      return "powersave";
    }
    if (i == 4) {
      return "restricted";
    }
    if (i == 5) {
      return "low_power_standby";
    }
    if (i == 7) {
      return "fw_oem_deny_1";
    }
    throw new IllegalArgumentException("Bad child chain: " + i);
  }

  public final int getFirewallType(int i) {
    if (i == 1) {
      return 0;
    }
    if (i == 2) {
      return 1;
    }
    if (i == 3 || i == 4 || i == 5) {
      return 0;
    }
    if (i != 7) {
      return !isFirewallEnabled() ? 1 : 0;
    }
    return 1;
  }

  public void setFirewallUidRules(int i, int[] iArr, int[] iArr2) {
    enforceSystemUid();
    synchronized (this.mQuotaLock) {
      synchronized (this.mRulesLock) {
        SparseIntArray uidFirewallRulesLR = getUidFirewallRulesLR(i);
        SparseIntArray sparseIntArray = new SparseIntArray();
        for (int length = iArr.length - 1; length >= 0; length--) {
          int i2 = iArr[length];
          int i3 = iArr2[length];
          updateFirewallUidRuleLocked(i, i2, i3);
          sparseIntArray.put(i2, i3);
        }
        SparseIntArray sparseIntArray2 = new SparseIntArray();
        for (int size = uidFirewallRulesLR.size() - 1; size >= 0; size--) {
          int keyAt = uidFirewallRulesLR.keyAt(size);
          if (sparseIntArray.indexOfKey(keyAt) < 0) {
            sparseIntArray2.put(keyAt, 0);
          }
        }
        for (int size2 = sparseIntArray2.size() - 1; size2 >= 0; size2--) {
          updateFirewallUidRuleLocked(i, sparseIntArray2.keyAt(size2), 0);
        }
      }
      try {
        ((ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class))
            .replaceFirewallChain(i, iArr);
      } catch (RuntimeException e) {
        Slog.w("NetworkManagement", "Error flushing firewall chain " + i, e);
      }
    }
  }

  public void setFirewallUidRule(int i, int i2, int i3) {
    enforceSystemUid();
    synchronized (this.mQuotaLock) {
      setFirewallUidRuleLocked(i, i2, i3);
    }
  }

  public final void setFirewallUidRuleLocked(int i, int i2, int i3) {
    if (updateFirewallUidRuleLocked(i, i2, i3)) {
      try {
        ((ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class))
            .setUidFirewallRule(i, i2, i3);
      } catch (RuntimeException e) {
        throw new IllegalStateException(e);
      }
    }
  }

  public final boolean updateFirewallUidRuleLocked(int i, int i2, int i3) {
    synchronized (this.mRulesLock) {
      SparseIntArray uidFirewallRulesLR = getUidFirewallRulesLR(i);
      int i4 = uidFirewallRulesLR.get(i2, 0);
      boolean z = DBG;
      if (z) {
        Slog.d(
            "NetworkManagement",
            "oldRule = " + i4 + ", newRule=" + i3 + " for uid=" + i2 + " on chain " + i);
      }
      if (i4 == i3) {
        if (z) {
          Slog.d("NetworkManagement", "!!!!! Skipping change");
        }
        return false;
      }
      String firewallRuleName = getFirewallRuleName(i, i3);
      String firewallRuleName2 = getFirewallRuleName(i, i4);
      if (i3 == 0) {
        uidFirewallRulesLR.delete(i2);
      } else {
        uidFirewallRulesLR.put(i2, i3);
      }
      return firewallRuleName.equals(firewallRuleName2) ? false : true;
    }
  }

  public final String getFirewallRuleName(int i, int i2) {
    if (getFirewallType(i) == 0) {
      if (i2 != 1) {
        return "deny";
      }
    } else if (i2 == 2) {
      return "deny";
    }
    return "allow";
  }

  public final SparseIntArray getUidFirewallRulesLR(int i) {
    if (i == 0) {
      return this.mUidFirewallRules;
    }
    if (i == 1) {
      return this.mUidFirewallDozableRules;
    }
    if (i == 2) {
      return this.mUidFirewallStandbyRules;
    }
    if (i == 3) {
      return this.mUidFirewallPowerSaveRules;
    }
    if (i == 4) {
      return this.mUidFirewallRestrictedRules;
    }
    if (i == 5) {
      return this.mUidFirewallLowPowerStandbyRules;
    }
    if (i == 7) {
      return this.mUidFirewallOemDenyRules;
    }
    throw new IllegalArgumentException("Unknown chain:" + i);
  }

  public final void enforceSystemUid() {
    if (this.mDeps.getCallingUid() != 1000) {
      throw new SecurityException("Only available to AID_SYSTEM");
    }
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (DumpUtils.checkDumpPermission(this.mContext, "NetworkManagement", printWriter)) {
      synchronized (this.mQuotaLock) {
        printWriter.print("Active quota ifaces: ");
        printWriter.println(this.mActiveQuotas.toString());
        printWriter.print("Active alert ifaces: ");
        printWriter.println(this.mActiveAlerts.toString());
        printWriter.print("Data saver mode: ");
        printWriter.println(this.mDataSaverMode);
        synchronized (this.mRulesLock) {
          dumpUidRuleOnQuotaLocked(printWriter, "denied UIDs", this.mUidRejectOnMetered);
          dumpUidRuleOnQuotaLocked(printWriter, "allowed UIDs", this.mUidAllowOnMetered);
        }
      }
      synchronized (this.mRulesLock) {
        dumpUidFirewallRule(printWriter, "", this.mUidFirewallRules);
        printWriter.print("UID firewall standby chain enabled: ");
        printWriter.println(getFirewallChainState(2));
        dumpUidFirewallRule(printWriter, "standby", this.mUidFirewallStandbyRules);
        printWriter.print("UID firewall dozable chain enabled: ");
        printWriter.println(getFirewallChainState(1));
        dumpUidFirewallRule(printWriter, "dozable", this.mUidFirewallDozableRules);
        printWriter.print("UID firewall powersave chain enabled: ");
        printWriter.println(getFirewallChainState(3));
        dumpUidFirewallRule(printWriter, "powersave", this.mUidFirewallPowerSaveRules);
        printWriter.print("UID firewall restricted mode chain enabled: ");
        printWriter.println(getFirewallChainState(4));
        dumpUidFirewallRule(printWriter, "restricted", this.mUidFirewallRestrictedRules);
        printWriter.print("UID firewall low power standby chain enabled: ");
        printWriter.println(getFirewallChainState(5));
        dumpUidFirewallRule(
            printWriter, "low_power_standby", this.mUidFirewallLowPowerStandbyRules);
        printWriter.print("UID firewall oem deny chain enabled: ");
        printWriter.println(getFirewallChainState(7));
        dumpUidFirewallRule(printWriter, "fw_oem_deny_1", this.mUidFirewallOemDenyRules);
      }
      printWriter.print("Firewall enabled: ");
      printWriter.println(this.mFirewallEnabled);
      printWriter.print("Netd service status: ");
      INetd iNetd = this.mNetdService;
      if (iNetd == null) {
        printWriter.println("disconnected");
        return;
      }
      try {
        printWriter.println(iNetd.isAlive() ? "alive" : "dead");
      } catch (RemoteException unused) {
        printWriter.println(INetd.NEXTHOP_UNREACHABLE);
      }
    }
  }

  public final void dumpUidRuleOnQuotaLocked(
      PrintWriter printWriter, String str, SparseBooleanArray sparseBooleanArray) {
    printWriter.print("UID bandwith control ");
    printWriter.print(str);
    printWriter.print(": [");
    int size = sparseBooleanArray.size();
    for (int i = 0; i < size; i++) {
      printWriter.print(sparseBooleanArray.keyAt(i));
      if (i < size - 1) {
        printWriter.print(",");
      }
    }
    printWriter.println("]");
  }

  public final void dumpUidFirewallRule(
      PrintWriter printWriter, String str, SparseIntArray sparseIntArray) {
    printWriter.print("UID firewall ");
    printWriter.print(str);
    printWriter.print(" rule: [");
    int size = sparseIntArray.size();
    for (int i = 0; i < size; i++) {
      printWriter.print(sparseIntArray.keyAt(i));
      printWriter.print(XmlUtils.STRING_ARRAY_SEPARATOR);
      printWriter.print(sparseIntArray.valueAt(i));
      if (i < size - 1) {
        printWriter.print(",");
      }
    }
    printWriter.println("]");
  }

  public final void modifyInterfaceInNetwork(boolean z, int i, String str) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      if (z) {
        this.mNetdService.networkAddInterface(i, str);
      } else {
        this.mNetdService.networkRemoveInterface(i, str);
      }
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void allowProtect(int i) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      this.mNetdService.networkSetProtectAllow(i);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void denyProtect(int i) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    try {
      this.mNetdService.networkSetProtectDeny(i);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void addInterfaceToLocalNetwork(String str, List list) {
    modifyInterfaceInNetwork(true, 99, str);
    NetdUtils.addRoutesToLocalNetwork(this.mNetdService, str, list);
  }

  public void removeInterfaceFromLocalNetwork(String str) {
    modifyInterfaceInNetwork(false, 99, str);
  }

  public int removeRoutesFromLocalNetwork(List list) {
    NetworkStack.checkNetworkStackPermission(this.mContext);
    return NetdUtils.removeRoutesFromLocalNetwork(this.mNetdService, list);
  }

  public boolean isNetworkRestricted(int i) {
    super.isNetworkRestricted_enforcePermission();
    return isNetworkRestrictedInternal(i);
  }

  public final boolean isNetworkRestrictedInternal(int i) {
    synchronized (this.mRulesLock) {
      if (getFirewallChainState(2) && this.mUidFirewallStandbyRules.get(i) == 2) {
        if (DBG) {
          Slog.d("NetworkManagement", "Uid " + i + " restricted because of app standby mode");
        }
        return true;
      }
      if (getFirewallChainState(1) && this.mUidFirewallDozableRules.get(i) != 1) {
        if (DBG) {
          Slog.d("NetworkManagement", "Uid " + i + " restricted because of device idle mode");
        }
        return true;
      }
      if (getFirewallChainState(3) && this.mUidFirewallPowerSaveRules.get(i) != 1) {
        if (DBG) {
          Slog.d("NetworkManagement", "Uid " + i + " restricted because of power saver mode");
        }
        return true;
      }
      if (getFirewallChainState(4) && this.mUidFirewallRestrictedRules.get(i) != 1) {
        if (DBG) {
          Slog.d("NetworkManagement", "Uid " + i + " restricted because of restricted mode");
        }
        return true;
      }
      if (getFirewallChainState(5) && this.mUidFirewallLowPowerStandbyRules.get(i) != 1) {
        if (DBG) {
          Slog.d("NetworkManagement", "Uid " + i + " restricted because of low power standby");
        }
        return true;
      }
      if (getFirewallChainState(7) && this.mUidFirewallOemDenyRules.get(i) == 2) {
        if (DBG) {
          Slog.d("NetworkManagement", "Uid " + i + " restricted because of freecess");
        }
        return true;
      }
      if (this.mUidRejectOnMetered.get(i)) {
        if (DBG) {
          Slog.d(
              "NetworkManagement",
              "Uid " + i + " restricted because of no metered data in the background");
        }
        return true;
      }
      if (!this.mDataSaverMode || this.mUidAllowOnMetered.get(i)) {
        return false;
      }
      if (DBG) {
        Slog.d("NetworkManagement", "Uid " + i + " restricted because of data saver mode");
      }
      return true;
    }
  }

  public final void setFirewallChainState(int i, boolean z) {
    synchronized (this.mRulesLock) {
      this.mFirewallChainStates.put(i, z);
    }
  }

  public final boolean getFirewallChainState(int i) {
    boolean z;
    synchronized (this.mRulesLock) {
      z = this.mFirewallChainStates.get(i);
    }
    return z;
  }

  public class LocalService extends NetworkManagementInternal {
    public LocalService() {}

    @Override // com.android.server.net.NetworkManagementInternal
    public boolean isNetworkRestrictedForUid(int i) {
      return NetworkManagementService.this.isNetworkRestrictedInternal(i);
    }
  }

  public void addPortFwdRules(String str, String str2, String str3, String str4, int i) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      this.mOemNetd.addPortFwdRules(str, str2, str3, str4, i);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public long getNetworkStatsVideoCall(String str, int i, int i2) {
    NetworkStack.checkNetworkStackPermissionOr(
        this.mContext, new String[] {"android.permission.CONNECTIVITY_INTERNAL"});
    try {
      TetherStatsParcel[] videoStats = this.mOemNetd.getVideoStats(str, i, i2);
      long j = 0;
      long j2 = 0;
      for (TetherStatsParcel tetherStatsParcel : videoStats) {
        try {
          String str2 = tetherStatsParcel.iface;
          j += tetherStatsParcel.rxBytes;
          j2 += tetherStatsParcel.txBytes;
        } catch (NumberFormatException e) {
          throw new IllegalStateException("problem parsing video call stats for " + str + ": " + e);
        }
      }
      return j + j2;
    } catch (RemoteException | ServiceSpecificException e2) {
      throw new IllegalStateException("problem parsing videocall stats: ", e2);
    }
  }

  public void startNetworkStatsOnPorts(String str, int i, int i2) {
    Log.d("NetworkManagement", "startNetworkStatsOnPorts iface:" + str + " in:" + i + " out:" + i2);
    NetworkStack.checkNetworkStackPermissionOr(
        this.mContext, new String[] {"android.permission.CONNECTIVITY_INTERNAL"});
    try {
      this.mOemNetd.startVideoStats(str, i, i2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void stopNetworkStatsOnPorts(String str, int i, int i2) {
    Log.d("NetworkManagement", "stopNetworkStatsOnPorts iface:" + str + " in:" + i + " out:" + i2);
    NetworkStack.checkNetworkStackPermissionOr(
        this.mContext, new String[] {"android.permission.CONNECTIVITY_INTERNAL"});
    try {
      this.mOemNetd.stopVideoStats(str, i, i2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void createNetworkGuardChain() {
    NetworkStack.checkNetworkStackPermissionOr(
        this.mContext, new String[] {"android.permission.CONNECTIVITY_INTERNAL"});
    try {
      this.mOemNetd.networkGuardCreateChain();
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void deleteNetworkGuardChain() {
    NetworkStack.checkNetworkStackPermissionOr(
        this.mContext, new String[] {"android.permission.CONNECTIVITY_INTERNAL"});
    try {
      this.mOemNetd.networkGuardDeleteChain();
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void enableNetworkGuard(boolean z) {
    NetworkStack.checkNetworkStackPermissionOr(
        this.mContext, new String[] {"android.permission.CONNECTIVITY_INTERNAL"});
    try {
      this.mOemNetd.networkGuardEnable(z);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void disableNetworkGuard() {
    NetworkStack.checkNetworkStackPermissionOr(
        this.mContext, new String[] {"android.permission.CONNECTIVITY_INTERNAL"});
    try {
      this.mOemNetd.networkGuardDisable();
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void deleteNetworkGuardWhiteListRule() {
    NetworkStack.checkNetworkStackPermissionOr(
        this.mContext, new String[] {"android.permission.CONNECTIVITY_INTERNAL"});
    try {
      this.mOemNetd.networkGuardDeleteWhiteListRule();
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setNetworkGuardUidRangeAcceptRule(int i, int i2) {
    NetworkStack.checkNetworkStackPermissionOr(
        this.mContext, new String[] {"android.permission.CONNECTIVITY_INTERNAL"});
    try {
      this.mOemNetd.networkGuardSetUidRangeAcceptRule(i, i2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setNetworkGuardUidRule(int i, boolean z, boolean z2) {
    NetworkStack.checkNetworkStackPermissionOr(
        this.mContext, new String[] {"android.permission.CONNECTIVITY_INTERNAL"});
    try {
      this.mOemNetd.networkGuardSetUidRule(i, z, z2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setNetworkGuardProtocolAcceptRule(int i) {
    NetworkStack.checkNetworkStackPermissionOr(
        this.mContext, new String[] {"android.permission.CONNECTIVITY_INTERNAL"});
    try {
      this.mOemNetd.networkGuardSetProtocolAcceptRule(i);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public int prioritizeApp(boolean z, int i) {
    enforceSystemUid();
    StringBuilder sb = new StringBuilder();
    sb.append("prioritizeApp is called for uid ");
    sb.append(i);
    sb.append(", add ");
    sb.append(z ? " true" : "false");
    Log.v("NetworkManagement", sb.toString());
    try {
      return this.mOemNetd.prioritizeApp(z, i);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public int addApeRule(boolean z, String str, int i) {
    enforceSystemUid();
    StringBuilder sb = new StringBuilder();
    sb.append("addRule is called  add ");
    sb.append(z ? " true" : "false");
    sb.append(", interface name ");
    sb.append(str);
    sb.append(", bandwidthMbps ");
    sb.append(i);
    Log.v("NetworkManagement", sb.toString());
    try {
      return this.mOemNetd.addApeRule(z, str, i);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public int replaceApeRule(String str, int i, int i2) {
    enforceSystemUid();
    Log.v(
        "NetworkManagement",
        "replaceApeRule is called, interface name "
            + str
            + ", from old bandwidthMbps "
            + i
            + ", to new bandwidth "
            + i2);
    try {
      return this.mOemNetd.replaceApeRule(str, i, i2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void startQbox(String str) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      this.mOemNetd.startQbox(str);
    } catch (RemoteException e) {
      Slog.e("NetworkManagement", "Error startQbox > " + e);
    }
  }

  public void stopQbox() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      this.mOemNetd.stopQbox();
    } catch (RemoteException e) {
      Slog.e("NetworkManagement", "Error stopQbox > " + e);
    }
  }

  public void setQboxUid(int i, boolean z) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      this.mOemNetd.setQboxUid(i, z);
    } catch (RemoteException e) {
      Slog.e("NetworkManagement", "Error setQboxUid > " + e);
    }
  }

  public final void modifyEpdg(boolean z, String str, String str2, boolean z2) {
    Slog.d("NetworkManagement", "modifyEpdg epdg " + z + " " + str + " " + str2);
    try {
      this.mOemNetd.modifyEpdg(z, str, str2, z2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void enableEpdg(String str, String str2, boolean z) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      modifyEpdg(true, str, str2, z);
    } catch (SocketException e) {
      throw new IllegalStateException(e);
    }
  }

  public void disableEpdg(String str, String str2) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      modifyEpdg(false, str, str2, false);
    } catch (SocketException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setEpdgInterfaceDropRule(String str, String str2, boolean z) {
    Slog.d("NetworkManagement", "setEpdgInterfaceDropRule");
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      this.mOemNetd.setEpdgInterfaceDropRule(str, str2, z);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public final RouteInfoParcel convertRouteInfo(RouteInfo routeInfo) {
    int type = routeInfo.getType();
    String str = "";
    if (type != 1) {
      if (type == 7) {
        str = INetd.NEXTHOP_UNREACHABLE;
      } else if (type == 9) {
        str = INetd.NEXTHOP_THROW;
      }
    } else if (routeInfo.hasGateway()) {
      str = routeInfo.getGateway().getHostAddress();
    }
    RouteInfoParcel routeInfoParcel = new RouteInfoParcel();
    routeInfoParcel.ifName = routeInfo.getInterface();
    routeInfoParcel.destination = routeInfo.getDestination().toString();
    routeInfoParcel.nextHop = str;
    routeInfoParcel.mtu = routeInfo.getMtu();
    return routeInfoParcel;
  }

  public void updateDefaultGatewayForEpdg(Network network) {
    LinkProperties linkProperties;
    Slog.d("NetworkManagement", "updateDefaultGatewayForEpdg");
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    int netId = network.getNetId();
    try {
      linkProperties =
          ((ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class))
              .getLinkProperties(network);
    } catch (Exception e) {
      Slog.d("NetworkManagement", "Error getLinkProperties: " + e);
      linkProperties = null;
    }
    if (linkProperties == null) {
      Slog.d("NetworkManagement", "linkProperties is empty");
      return;
    }
    for (RouteInfo routeInfo : linkProperties.getRoutes()) {
      if (routeInfo.getType() == 1
          && routeInfo.getGateway() != null
          && routeInfo.getGateway().getHostAddress().startsWith("fe80")) {
        Slog.d("NetworkManagement", "Updating Route [" + routeInfo + "] from network " + netId);
        try {
          this.mNetdService.networkUpdateRouteParcel(netId, convertRouteInfo(routeInfo));
        } catch (Exception e2) {
          Slog.d("NetworkManagement", "Exception in networkUpdateRouteParcel: " + e2);
        }
      }
    }
  }

  public void setBlockAllDNSPackets(boolean z) {
    enforceSystemUid();
    Log.e("NetworkManagement", "setBlockAllDNSPackets");
    try {
      this.mOemNetd.setBlockAllDNSPackets(z);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setBlockHostAlone(String str) {
    enforceSystemUid();
    Log.e("NetworkManagement", "setBlockHostAlone");
    try {
      this.mOemNetd.setBlockHostAlone(str);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setAllowHostAlone(String str) {
    enforceSystemUid();
    Log.e("NetworkManagement", "setAllowHostAlone");
    try {
      this.mOemNetd.setAllowHostAlone(str);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setBlockListIPs(String str) {
    enforceSystemUid();
    Log.e("NetworkManagement", "setBlockListIPs");
    try {
      this.mOemNetd.setBlockListIPs(str);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setAllowListIPs(String str) {
    enforceSystemUid();
    Log.e("NetworkManagement", "setAllowListIPs");
    try {
      this.mOemNetd.setAllowListIPs(str);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void cleanAllBlock() {
    enforceSystemUid();
    Log.e("NetworkManagement", "cleanAllBlock");
    try {
      this.mOemNetd.cleanAllBlock();
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setBlockAllPackets() {
    enforceSystemUid();
    Log.e("NetworkManagement", "setBlockAllPackets");
    try {
      this.mOemNetd.setBlockAllPackets();
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setBlockPorts(String str, int i, String str2) {
    enforceSystemUid();
    Log.e(
        "NetworkManagement",
        "setBlockPorts, protocol: "
            + str
            + " directionBitMask: "
            + i
            + " ports: "
            + str2
            + " callingUid: "
            + Binder.getCallingUid()
            + " callingPid: "
            + Binder.getCallingPid());
    try {
      this.mOemNetd.setBlockPorts(str, i, str2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void cleanBlockPorts() {
    enforceSystemUid();
    Log.e("NetworkManagement", "cleanBlockPorts");
    try {
      this.mOemNetd.cleanBlockPorts();
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public final void enforceSystemUidexceptUserId() {
    if (UserHandle.getAppId(this.mDeps.getCallingUid()) != 1000) {
      throw new SecurityException("Only available to AID_SYSTEM");
    }
  }

  public void setUrlFirewallRuleMobileData(int i, String str, boolean z) {
    enforceSystemUidexceptUserId();
    Preconditions.checkState(this.mFirewallEnabled);
    try {
      this.mOemNetd.gmsCoreSetUidUrlMobileDataRule(i, str, z ? 1 : 2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setUrlFirewallRuleWifi(int i, String str, boolean z) {
    enforceSystemUidexceptUserId();
    Preconditions.checkState(this.mFirewallEnabled);
    try {
      this.mOemNetd.gmsCoreSetUidUrlWifiRule(i, str, z ? 1 : 2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void buildFirewall() {
    enforceSystemUidexceptUserId();
    try {
      this.mOemNetd.firewallBuild();
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setFirewallRuleWifi(int i, boolean z) {
    enforceSystemUidexceptUserId();
    try {
      this.mOemNetd.firewallSetRuleWifi(i, z);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setFirewallRuleMobileData(int i, boolean z) {
    enforceSystemUidexceptUserId();
    try {
      this.mOemNetd.firewallSetRuleMobileData(i, z);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setKnoxGuardExemptRule(boolean z, String str, int i) {
    Slog.d("NetworkManagement", "setKnoxGuardExemptRule");
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
    try {
      this.mOemNetd.setKnoxGuardExemptRule(z, str, i);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void addMptcpLink(String str) {
    Slog.d("NetworkManagement", "addmptcplink" + str);
    enforceSystemUid();
    try {
      this.mOemNetd.addMptcpLinkIface(str);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void removeMptcpLink(String str) {
    Slog.d("NetworkManagement", "removemptcplink" + str);
    enforceSystemUid();
    try {
      this.mOemNetd.removeMptcpLinkIface(str);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void addChain(String str, String str2) {
    Slog.d("NetworkManagement", "addchain chain : " + str);
    Slog.d("NetworkManagement", "iptype : " + str2);
    enforceSystemUid();
    try {
      this.mOemNetd.addMptcpChain(str, str2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void removeChain(String str, String str2) {
    Slog.d("NetworkManagement", "removechain" + str);
    Slog.d("NetworkManagement", "iptype : " + str2);
    enforceSystemUid();
    try {
      this.mOemNetd.removeMptcpChain(str, str2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void addSocksRule(String str, String str2, String str3, int i, String str4) {
    Slog.d("NetworkManagement", "addSocksRule");
    enforceSystemUid();
    try {
      this.mOemNetd.addMptcpSocksRule(str, str2, str3, i, str4);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void removeSocksRule(String str, String str2, String str3, int i, String str4) {
    Slog.d("NetworkManagement", "removeSocksRule");
    enforceSystemUid();
    try {
      this.mOemNetd.removeMptcpSocksRule(str, str2, str3, i, str4);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void addUidSocksRule(String str, String str2, String str3, int i, int i2, String str4) {
    Slog.d("NetworkManagement", "addSocksRule");
    enforceSystemUid();
    try {
      this.mOemNetd.addMptcpUidSocksRule(str, str2, str3, i, i2, str4);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void removeUidSocksRule(String str, String str2, String str3, int i, int i2, String str4) {
    Slog.d("NetworkManagement", "removeSocksRule");
    enforceSystemUid();
    try {
      this.mOemNetd.removeMptcpUidSocksRule(str, str2, str3, i, i2, str4);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void addSocksSkipRule(String str, String str2, String str3) {
    Slog.d("NetworkManagement", "addSocksSkipRule");
    enforceSystemUid();
    try {
      this.mOemNetd.addMptcpSocksSkipRule(str, str2, str3);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void removeSocksSkipRule(String str, String str2, String str3) {
    Slog.d("NetworkManagement", "removeSocksSkipRule");
    enforceSystemUid();
    try {
      this.mOemNetd.removeMptcpSocksSkipRule(str, str2, str3);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void addSocksSkipRuleProto(String str, String str2, String str3, int i, String str4) {
    Slog.d("NetworkManagement", "addSocksSkipRuleProto");
    Slog.d("NetworkManagement", " iptype : " + str4);
    enforceSystemUid();
    try {
      this.mOemNetd.addMptcpSocksSkipRuleProto(str, str2, str3, i, str4);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void removeSocksSkipRuleProto(String str, String str2, String str3, int i, String str4) {
    Slog.d("NetworkManagement", "removeSocksSkipRule");
    enforceSystemUid();
    try {
      this.mOemNetd.removeMptcpSocksSkipRuleProto(str, str2, str3, i, str4);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void addUidToChain(String str, String str2, int i) {
    Slog.d("NetworkManagement", "addUidToChain");
    enforceSystemUid();
    try {
      this.mOemNetd.addUidToMptcpChain(str, i, str2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void removeUidFromChain(String str, String str2, int i) {
    Slog.d("NetworkManagement", "removeUidFromChain");
    enforceSystemUid();
    try {
      this.mOemNetd.removeUidFromMptcpChain(str, i, str2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void addIpAcceptRule(String str, String str2, String str3) {
    Slog.d("NetworkManagement", "addIpAcceptRule");
    enforceSystemUid();
    try {
      this.mOemNetd.addMptcpIpAcceptRule(str, str2, str3);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void delIpAcceptRule(String str, String str2, String str3) {
    Slog.d("NetworkManagement", "delIpAcceptRule");
    enforceSystemUid();
    try {
      this.mOemNetd.delMptcpIpAcceptRule(str, str2, str3);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setTcpBufferSize(String str, String str2) {
    Slog.d("NetworkManagement", "setTcpBufferSize");
    enforceSystemUid();
    try {
      this.mOemNetd.setMptcpTcpBufferSize(str, str2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setMptcpMtuValue(String str, int i) {
    Slog.d("NetworkManagement", "setMptcpMtuValues");
    enforceSystemUid();
    try {
      this.mOemNetd.setMtuValueMptcp(str, i);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void enableMptcp(String str) {
    Slog.d("NetworkManagement", "enableMptcp");
    enforceSystemUid();
    try {
      this.mOemNetd.enableMptcpModes(str);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void disableMptcp() {
    Slog.d("NetworkManagement", "disableMptcp");
    enforceSystemUid();
    try {
      this.mOemNetd.disableMptcpMode();
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void addSourceRoute(String str, String str2, String str3) {
    Slog.d("NetworkManagement", "addSourceRoute");
    enforceSystemUid();
    try {
      this.mOemNetd.addMptcpSourceRoute(str, str2, str3);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void delSourceRoute(String str, String str2, String str3) {
    Slog.d("NetworkManagement", "delSourceRoute");
    enforceSystemUid();
    try {
      this.mOemNetd.delMptcpSourceRoute(str, str2, str3);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void addSourcePortAcceptRule(String str, String str2, int i) {
    Slog.d("NetworkManagement", "addSourcePortAcceptRule");
    enforceSystemUid();
    try {
      this.mOemNetd.addMptcpSourcePortAcceptRule(str, str2, i);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void delSourcePortAcceptRule(String str, String str2, int i) {
    Slog.d("NetworkManagement", "delSourcePortAcceptRule");
    enforceSystemUid();
    try {
      this.mOemNetd.delMptcpSourcePortAcceptRule(str, str2, i);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void updateSourceRule(boolean z, String str, String str2) {
    Slog.d("NetworkManagement", "updateSourceRule");
    enforceSystemUid();
    try {
      this.mOemNetd.updateMptcpSourceRule(z, str, str2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setPrivateIpRoute(boolean z, String str, int i) {
    Slog.d("NetworkManagement", "setPrivateIpRoute");
    enforceSystemUid();
    try {
      this.mOemNetd.setMptcpPrivateIpRoute(z, str, i);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setDestinationBasedMarkRule(boolean z, String str, String str2, int i, int i2) {
    Slog.d("NetworkManagement", "setDestinationBasedMarkRule");
    enforceSystemUid();
    try {
      this.mOemNetd.setMptcpDestBaseMarkRule(z, str, str2, i, i2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setUIDRoute(boolean z, String str, int i, String str2, String str3) {
    Slog.d("NetworkManagement", "setUIDRoute");
    enforceSystemUid();
    try {
      this.mOemNetd.setMptcpUIDRoute(z, str, i, str2, str3);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public int prioritizeMnxbApp(boolean z, int i) {
    enforceSystemUid();
    StringBuilder sb = new StringBuilder();
    sb.append("prioritizeApp is called for uid ");
    sb.append(i);
    sb.append(", add ");
    sb.append(z ? " true" : "false");
    Log.v("NetworkManagement", sb.toString());
    try {
      return this.mOemNetd.prioritizeMnxbApp(z, i);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public int addMnxbRule(boolean z, String str, int i) {
    enforceSystemUid();
    StringBuilder sb = new StringBuilder();
    sb.append("addRule is called  add ");
    sb.append(z ? " true" : "false");
    sb.append(", interface name ");
    sb.append(str);
    sb.append(", bandwidthMbps ");
    sb.append(i);
    Log.v("NetworkManagement", sb.toString());
    try {
      return this.mOemNetd.addMnxbRule(z, str, i);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public int replaceMnxbRule(String str, int i, int i2) {
    enforceSystemUid();
    Log.v(
        "NetworkManagement",
        "replaceMnxbRule is called, interface name "
            + str
            + ", from old bandwidthMbps "
            + i
            + ", to new bandwidth "
            + i2);
    try {
      return this.mOemNetd.replaceMnxbRule(str, i, i2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void setAdvertiseWindowSize(int i) {
    Log.v("NetworkManagement", "setAdvertiseWindowSize is called, to set advertise window as " + i);
    enforceSystemUid();
    try {
      this.mOemNetd.setAdvertiseWindowSize(i);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public long[] l4StatsGet() {
    enforceSystemUid();
    try {
      return this.mOemNetd.l4StatsGet();
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void addLegacyRoute(int i, String str, String str2, String str3, int i2) {
    Slog.d("NetworkManagement", "addLegacyRoute" + str);
    enforceSystemUid();
    try {
      this.mOemNetd.networkAddLegacyRoute(i, str, str2, str3, i2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }

  public void removeLegacyRoute(int i, String str, String str2, String str3, int i2) {
    Slog.d("NetworkManagement", "removeLegacyRoute" + str);
    enforceSystemUid();
    try {
      this.mOemNetd.networkRemoveLegacyRoute(i, str, str2, str3, i2);
    } catch (RemoteException | ServiceSpecificException e) {
      throw new IllegalStateException(e);
    }
  }
}
