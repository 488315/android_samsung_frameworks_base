package com.android.server.net;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.ConnectivityManager;
import android.net.ICloEventObserver;
import android.net.INetd;
import android.net.INetdUnsolicitedEventListener;
import android.net.INetworkManagementEventObserver;
import android.net.InetAddresses;
import android.net.InterfaceConfiguration;
import android.net.InterfaceConfigurationParcel;
import android.net.IpPrefix;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkStack;
import android.net.RouteInfo;
import android.net.RouteInfoParcel;
import android.net.TetherStatsParcel;
import android.net.UidRangeParcel;
import android.net.util.NetdService;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.Parcel;
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
import com.android.modules.utils.build.SdkLevel;
import com.android.net.module.util.NetdUtils;
import com.android.net.module.util.PermissionUtils;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;

import com.google.android.collect.Maps;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class NetworkManagementService extends INetworkManagementService.Stub {
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
    public SparseBooleanArray mUidAllowOnMetered;
    public SparseIntArray mUidCleartextPolicy;
    public final SparseIntArray mUidFirewallBackgroundRules;
    public final SparseIntArray mUidFirewallDozableRules;
    public final SparseIntArray mUidFirewallLowPowerStandbyRules;
    public final SparseIntArray mUidFirewallOemDenyRules;
    public final SparseIntArray mUidFirewallPowerSaveRules;
    public final SparseIntArray mUidFirewallRestrictedRules;
    public final SparseIntArray mUidFirewallRules;
    public final SparseIntArray mUidFirewallStandbyRules;
    public final SparseIntArray mUidMeteredFirewallAllowRules;
    public final SparseIntArray mUidMeteredFirewallDenyAdminRules;
    public final SparseIntArray mUidMeteredFirewallDenyUserRules;
    public SparseBooleanArray mUidRejectOnMetered;
    public final boolean mUseMeteredFirewallChains;

    public final class Dependencies {}

    public final class LocalService {
        public LocalService() {}
    }

    public final class NetdTetherEventListener extends Binder implements INetdTetherEventListener {
        public NetdTetherEventListener() {
            attachInterface(this, "com.android.internal.net.INetdTetherEventListener");
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.internal.net.INetdTetherEventListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.internal.net.INetdTetherEventListener");
                return true;
            }
            if (i == 1) {
                Log.d("NetworkManagement", "send intent to KVES to inform tether has started");
                NetworkManagementService networkManagementService = NetworkManagementService.this;
                networkManagementService.getClass();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Intent intent = new Intent();
                    intent.setAction(
                            "com.samsung.android.knox.intent.action.INTERFACE_STATUS_INTERNAL");
                    intent.putExtra(
                            "com.samsung.android.knox.intent.extra.ACTION_INTERNAL",
                            "tethering_info");
                    networkManagementService.mContext.sendBroadcast(
                            intent, "com.samsung.android.knox.permission.KNOX_VPN_INTERNAL");
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } else if (i != 2) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }
    }

    public final class NetdUnsolicitedEventListener extends INetdUnsolicitedEventListener.Stub {
        public NetdUnsolicitedEventListener() {}

        @Override // android.net.INetdUnsolicitedEventListener
        public final String getInterfaceHash() {
            return "2be6ff6fb01645cdddb3bb60f6de5727e5733267";
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public final int getInterfaceVersion() {
            return 15;
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public final void onInterfaceAdded(String str) {
            NetworkManagementService.this.mDaemonHandler.post(
                    new NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda1(
                            this, str, 1));
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public final void onInterfaceAddressRemoved(String str, String str2, int i, int i2) {
            NetworkManagementService.this.mDaemonHandler.post(
                    new NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda6(
                            this, str2, new LinkAddress(str, i, i2), 0));
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public final void onInterfaceAddressUpdated(String str, String str2, int i, int i2) {
            NetworkManagementService.this.mDaemonHandler.post(
                    new NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda6(
                            this, str2, new LinkAddress(str, i, i2), 1));
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public final void onInterfaceChanged(String str, boolean z) {
            NetworkManagementService.this.mDaemonHandler.post(
                    new NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda2(
                            this, str, z, 1));
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public final void onInterfaceClassActivityChanged(
                final boolean z, final int i, long j, final int i2) {
            if (j <= 0) {
                j = SystemClock.elapsedRealtimeNanos();
            }
            final long j2 = j;
            NetworkManagementService.this.mDaemonHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            NetworkManagementService.NetdUnsolicitedEventListener
                                    netdUnsolicitedEventListener =
                                            NetworkManagementService.NetdUnsolicitedEventListener
                                                    .this;
                            final int i3 = i;
                            final boolean z2 = z;
                            final long j3 = j2;
                            final int i4 = i2;
                            NetworkManagementService networkManagementService =
                                    NetworkManagementService.this;
                            networkManagementService.getClass();
                            networkManagementService.invokeForAllObservers(
                                    new NetworkManagementService
                                            .NetworkManagementEventCallback() { // from class:
                                                                                // com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda0
                                        @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
                                        public final void sendCallback(
                                                INetworkManagementEventObserver
                                                        iNetworkManagementEventObserver) {
                                            iNetworkManagementEventObserver
                                                    .interfaceClassDataActivityChanged(
                                                            i3, z2, j3, i4);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public final void onInterfaceDnsServerInfo(
                final String str, final long j, final String[] strArr) {
            NetworkManagementService.this.mDaemonHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            NetworkManagementService.NetdUnsolicitedEventListener
                                    netdUnsolicitedEventListener =
                                            NetworkManagementService.NetdUnsolicitedEventListener
                                                    .this;
                            final String str2 = str;
                            final long j2 = j;
                            final String[] strArr2 = strArr;
                            NetworkManagementService networkManagementService =
                                    NetworkManagementService.this;
                            networkManagementService.getClass();
                            networkManagementService.invokeForAllObservers(
                                    new NetworkManagementService
                                            .NetworkManagementEventCallback() { // from class:
                                                                                // com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda4
                                        @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
                                        public final void sendCallback(
                                                INetworkManagementEventObserver
                                                        iNetworkManagementEventObserver) {
                                            iNetworkManagementEventObserver.interfaceDnsServerInfo(
                                                    str2, j2, strArr2);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public final void onInterfaceLinkStateChanged(String str, boolean z) {
            NetworkManagementService.this.mDaemonHandler.post(
                    new NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda2(
                            this, str, z, 0));
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public final void onInterfaceRemoved(String str) {
            NetworkManagementService.this.mDaemonHandler.post(
                    new NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda1(
                            this, str, 0));
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public final void onQuotaLimitReached(String str, String str2) {
            NetworkManagementService.this.mDaemonHandler.post(
                    new NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda6(
                            this, str, str2, 2));
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public final void onRouteChanged(boolean z, String str, String str2, String str3) {
            NetworkManagementService.this.mDaemonHandler.post(
                    new NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda2(
                            this,
                            z,
                            new RouteInfo(
                                    new IpPrefix(str),
                                    "".equals(str2)
                                            ? null
                                            : InetAddresses.parseNumericAddress(str2),
                                    str3,
                                    1)));
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public final void onStrictCleartextDetected(int i, String str) {
            ActivityManager.getService()
                    .notifyCleartextNetwork(i, HexDump.hexStringToByteArray(str));
        }
    }

    public interface NetworkManagementEventCallback {
        void sendCallback(INetworkManagementEventObserver iNetworkManagementEventObserver);
    }

    public NetworkManagementService(Context context, Dependencies dependencies) {
        super(PermissionEnforcer.fromContext(context));
        this.mObservers = new RemoteCallbackList();
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
        this.mUidFirewallBackgroundRules = new SparseIntArray();
        this.mUidMeteredFirewallAllowRules = new SparseIntArray();
        this.mUidMeteredFirewallDenyUserRules = new SparseIntArray();
        this.mUidMeteredFirewallDenyAdminRules = new SparseIntArray();
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        this.mFirewallChainStates = sparseBooleanArray;
        this.mNetdTetherEventListener = null;
        this.mContext = context;
        this.mDeps = dependencies;
        Flags.useMeteredFirewallChains();
        this.mUseMeteredFirewallChains = true;
        sparseBooleanArray.put(11, true);
        sparseBooleanArray.put(12, true);
        this.mDaemonHandler = new Handler(FgThread.get().getLooper());
        this.mNetdUnsolicitedEventListener = new NetdUnsolicitedEventListener();
        LocalServices.addService(LocalService.class, new LocalService());
    }

    public static RouteInfoParcel convertRouteInfo(RouteInfo routeInfo) {
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

    public static NetworkManagementService create(Context context) {
        NetworkManagementService networkManagementService =
                new NetworkManagementService(context, new Dependencies());
        boolean z = DBG;
        if (z) {
            Slog.d("NetworkManagement", "Creating NetworkManagementService");
        }
        if (z) {
            Slog.d("NetworkManagement", "Connecting native netd service");
        }
        networkManagementService.mDeps.getClass();
        INetd iNetd = NetdService.get();
        networkManagementService.mNetdService = iNetd;
        try {
            iNetd.registerUnsolicitedEventListener(
                    networkManagementService.mNetdUnsolicitedEventListener);
            if (z) {
                Slog.d("NetworkManagement", "Register unsolicited event listener");
            }
        } catch (RemoteException | ServiceSpecificException e) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    e, "Failed to set Netd unsolicited event listener ", "NetworkManagement");
        }
        try {
            networkManagementService.mOemNetd =
                    IOemNetd.Stub.asInterface(networkManagementService.mNetdService.getOemNetd());
            if (DBG) {
                Slog.d("NetworkManagement", "Get OemNet listener");
            }
        } catch (RemoteException | ServiceSpecificException e2) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    e2, "Failed to get OemNetd listener ", "NetworkManagement");
        }
        if (DBG) {
            Slog.d("NetworkManagement", "Connected");
        }
        return networkManagementService;
    }

    public static void dumpUidFirewallRule(
            PrintWriter printWriter, String str, SparseIntArray sparseIntArray) {
        printWriter.print("UID firewall ");
        printWriter.print(str);
        printWriter.print(" rule: [");
        int size = sparseIntArray.size();
        for (int i = 0; i < size; i++) {
            printWriter.print(sparseIntArray.keyAt(i));
            printWriter.print(":");
            printWriter.print(sparseIntArray.valueAt(i));
            if (i < size - 1) {
                printWriter.print(",");
            }
        }
        printWriter.println("]");
    }

    public static void dumpUidRuleOnQuotaLocked(
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

    public final void activateClo(String str) {
        Log.v("NetworkManagement", "activate Clo native is called: " + str);
        enforceSystemUid();
        try {
            this.mOemNetd.activateClo(str);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void activateCloGro() {
        Log.v("NetworkManagement", "activate Clo Gro native is called");
        enforceSystemUid();
        try {
            this.mOemNetd.activateCloGro();
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final int addApeRule(boolean z, String str, int i) {
        enforceSystemUid();
        StringBuilder sb = new StringBuilder("addRule is called  add ");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                sb, z ? " true" : "false", ", interface name ", str, ", bandwidthMbps ");
        sb.append(i);
        Log.v("NetworkManagement", sb.toString());
        try {
            return this.mOemNetd.addApeRule(z, str, i);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void addChain(String str, String str2) {
        Slog.d("NetworkManagement", "addchain chain : " + str);
        Slog.d("NetworkManagement", "iptype : " + str2);
        enforceSystemUid();
        try {
            this.mOemNetd.addMptcpChain(str, str2);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void addIpAcceptRule(String str, String str2, String str3) {
        Slog.d("NetworkManagement", "addIpAcceptRule");
        enforceSystemUid();
        try {
            this.mOemNetd.addMptcpIpAcceptRule(str, str2, str3);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void addLegacyRoute(int i, String str, String str2, String str3, int i2) {
        Slog.d("NetworkManagement", "addLegacyRoute" + str);
        enforceSystemUid();
        try {
            this.mOemNetd.networkAddLegacyRoute(i, str, str2, str3, i2);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final int addMnxbRule(boolean z, String str, int i) {
        enforceSystemUid();
        StringBuilder sb = new StringBuilder("addRule is called  add ");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                sb, z ? " true" : "false", ", interface name ", str, ", bandwidthMbps ");
        sb.append(i);
        Log.v("NetworkManagement", sb.toString());
        try {
            return this.mOemNetd.addMnxbRule(z, str, i);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void addMptcpLink(String str) {
        Slog.d("NetworkManagement", "addmptcplink" + str);
        enforceSystemUid();
        try {
            this.mOemNetd.addMptcpLinkIface(str);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void addOrRemoveSystemAppFromDataSaverWhitelist(boolean z, int i) {
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

    public final void addPortFwdRules(String str, String str2, String str3, String str4, int i) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.addPortFwdRules(str, str2, str3, str4, i);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void addSocksRule(String str, String str2, String str3, int i, String str4) {
        Slog.d("NetworkManagement", "addSocksRule");
        enforceSystemUid();
        try {
            this.mOemNetd.addMptcpSocksRule(str, str2, str3, i, str4);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void addSocksSkipRule(String str, String str2, String str3) {
        Slog.d("NetworkManagement", "addSocksSkipRule");
        enforceSystemUid();
        try {
            this.mOemNetd.addMptcpSocksSkipRule(str, str2, str3);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void addSocksSkipRuleProto(
            String str, String str2, String str3, int i, String str4) {
        Slog.d("NetworkManagement", "addSocksSkipRuleProto");
        Slog.d("NetworkManagement", " iptype : " + str4);
        enforceSystemUid();
        try {
            this.mOemNetd.addMptcpSocksSkipRuleProto(str, str2, str3, i, str4);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void addSourcePortAcceptRule(String str, String str2, int i) {
        Slog.d("NetworkManagement", "addSourcePortAcceptRule");
        enforceSystemUid();
        try {
            this.mOemNetd.addMptcpSourcePortAcceptRule(str, str2, i);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void addSourceRoute(String str, String str2, String str3) {
        Slog.d("NetworkManagement", "addSourceRoute");
        enforceSystemUid();
        try {
            this.mOemNetd.addMptcpSourceRoute(str, str2, str3);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void addTosPolicy(int i, int i2) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.addTosPolicy(i, i2);
        } catch (RemoteException e) {
            Slog.e("NetworkManagement", "Error addTosPolicy > " + e);
        }
    }

    public final void addUidSocksRule(
            String str, String str2, String str3, int i, int i2, String str4) {
        Slog.d("NetworkManagement", "addSocksRule");
        enforceSystemUid();
        try {
            this.mOemNetd.addMptcpUidSocksRule(str, str2, str3, i, i2, str4);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void addUidToChain(String str, String str2, int i) {
        Slog.d("NetworkManagement", "addUidToChain");
        enforceSystemUid();
        try {
            this.mOemNetd.addUidToMptcpChain(str, i, str2);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void allowProtect(int i) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        try {
            this.mNetdService.networkSetProtectAllow(i);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void applyUidCleartextNetworkPolicy(int i, int i2) {
        int i3 = 1;
        if (i2 != 0) {
            if (i2 == 1) {
                i3 = 2;
            } else {
                if (i2 != 2) {
                    throw new IllegalArgumentException(
                            VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "Unknown policy "));
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

    public final void buildFirewall() {
        enforceSystemUidexceptUserId();
        try {
            this.mOemNetd.firewallBuild();
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void cleanAllBlock() {
        enforceSystemUid();
        Log.e("NetworkManagement", "cleanAllBlock");
        try {
            this.mOemNetd.cleanAllBlock();
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void cleanBlockPorts() {
        enforceSystemUid();
        Log.e("NetworkManagement", "cleanBlockPorts");
        try {
            this.mOemNetd.cleanBlockPorts();
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void cleanOnlyAllowIPs() {
        enforceSystemUid();
        Log.e("NetworkManagement", "cleanOnlyAllowIPs");
        try {
            this.mOemNetd.cleanOnlyAllowIPs();
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void clearEbpfMap(int i) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.clearEbpfMap(i);
        } catch (Exception unused) {
            Log.e("NetworkManagement", "clearEbpfMap failed");
        }
    }

    public final void clearInterfaceAddresses(String str) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        try {
            this.mNetdService.interfaceClearAddrs(str);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void clearTosMap() {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.clearTosMap();
        } catch (RemoteException e) {
            Slog.e("NetworkManagement", "Error clearTosMap > " + e);
        }
    }

    public final void closeSocketsForFreecess(int i, String str) {
        enforceSystemUid();
    }

    public final void closeSocketsForUid(int i) {
        try {
            this.mNetdService.socketDestroy(
                    new UidRangeParcel[] {new UidRangeParcel(i, i)}, new int[0]);
        } catch (RemoteException | ServiceSpecificException e) {
            Slog.e("NetworkManagement", "Error closing sockets for uid=" + i + ": " + e);
        }
    }

    public final void closeSocketsForUids(int[] iArr) {
        for (int i : iArr) {
            closeSocketsForUid(i);
        }
    }

    public final void deactivateClo(String str) {
        Log.v("NetworkManagement", "deactivate Clo native is called: " + str);
        enforceSystemUid();
        try {
            this.mOemNetd.deactivateClo(str);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void deactivateCloGro() {
        Log.v("NetworkManagement", "deactivate Clo Gro native is called");
        enforceSystemUid();
        try {
            this.mOemNetd.deactivateCloGro();
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void delIpAcceptRule(String str, String str2, String str3) {
        Slog.d("NetworkManagement", "delIpAcceptRule");
        enforceSystemUid();
        try {
            this.mOemNetd.delMptcpIpAcceptRule(str, str2, str3);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void delSourcePortAcceptRule(String str, String str2, int i) {
        Slog.d("NetworkManagement", "delSourcePortAcceptRule");
        enforceSystemUid();
        try {
            this.mOemNetd.delMptcpSourcePortAcceptRule(str, str2, i);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void delSourceRoute(String str, String str2, String str3) {
        Slog.d("NetworkManagement", "delSourceRoute");
        enforceSystemUid();
        try {
            this.mOemNetd.delMptcpSourceRoute(str, str2, str3);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void denyProtect(int i) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        try {
            this.mNetdService.networkSetProtectDeny(i);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void disableDAD(String str) {
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                "disableDAD ifName ", str, "NetworkManagement");
        NetworkStack.checkNetworkStackPermission(this.mContext);
        try {
            this.mNetdService.setProcSysNet(6, 1, str, "accept_dad", "0");
            this.mNetdService.setProcSysNet(6, 1, str, "dad_transmits", "0");
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    public final void disableEpdg(String str, String str2) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            modifyEpdg(false, str, str2, false);
        } catch (SocketException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void disableIpv6(String str) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        try {
            this.mNetdService.interfaceSetEnableIPv6(str, false);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void disableMptcp() {
        Slog.d("NetworkManagement", "disableMptcp");
        enforceSystemUid();
        try {
            this.mOemNetd.disableMptcpMode();
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void disableNat(String str, String str2) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (SdkLevel.isAtLeastV()) {
            throw new UnsupportedOperationException("NMS#disableNat not supported in V+");
        }
        try {
            this.mNetdService.tetherRemoveForward(str, str2);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "NetworkManagement", printWriter)) {
            StringBuilder m$1 =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                            printWriter,
                            "Flags:",
                            "com.android.server.net.use_metered_firewall_chains: ");
            m$1.append(this.mUseMeteredFirewallChains);
            printWriter.println(m$1.toString());
            printWriter.println();
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
                printWriter.print("UID firewall background chain enabled: ");
                printWriter.println(getFirewallChainState(6));
                dumpUidFirewallRule(printWriter, "background", this.mUidFirewallBackgroundRules);
                printWriter.print("UID firewall metered allow chain enabled (Data saver mode): ");
                printWriter.println(getFirewallChainState(10));
                dumpUidFirewallRule(
                        printWriter, "metered_allow", this.mUidMeteredFirewallAllowRules);
                printWriter.print("UID firewall metered deny_user chain enabled (always-on): ");
                printWriter.println(getFirewallChainState(11));
                dumpUidFirewallRule(
                        printWriter, "metered_deny_user", this.mUidMeteredFirewallDenyUserRules);
                printWriter.print("UID firewall metered deny_admin chain enabled (always-on): ");
                printWriter.println(getFirewallChainState(12));
                dumpUidFirewallRule(
                        printWriter, "metered_deny_admin", this.mUidMeteredFirewallDenyAdminRules);
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

    public final void enableEpdg(String str, String str2, boolean z) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            modifyEpdg(true, str, str2, z);
        } catch (SocketException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void enableIpv6(String str) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        try {
            this.mNetdService.interfaceSetEnableIPv6(str, true);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void enableKnoxVpnFlagForTether(boolean z) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.enableKnoxVpnFlagForTether(z);
        } catch (Exception unused) {
            Log.e("NetworkManagement", "enableKnoxVpnFlagForTether failed for chained vpn profile");
        }
    }

    public final void enableMptcp(String str) {
        Slog.d("NetworkManagement", "enableMptcp");
        enforceSystemUid();
        try {
            this.mOemNetd.enableMptcpModes(str);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void enableNat(String str, String str2) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (SdkLevel.isAtLeastV()) {
            throw new UnsupportedOperationException("NMS#enableNat not supported in V+");
        }
        try {
            this.mNetdService.tetherAddForward(str, str2);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void enforceSystemUid() {
        this.mDeps.getClass();
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Only available to AID_SYSTEM");
        }
    }

    public final void enforceSystemUidexceptUserId() {
        this.mDeps.getClass();
        if (UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
            throw new SecurityException("Only available to AID_SYSTEM");
        }
    }

    public final boolean getFirewallChainState(int i) {
        boolean z;
        synchronized (this.mRulesLock) {
            z = this.mFirewallChainStates.get(i);
        }
        return z;
    }

    public final String getFirewallRuleName(int i, int i2) {
        boolean z;
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 10:
                z = false;
                break;
            case 2:
            case 7:
            case 11:
            case 12:
                z = true;
                break;
            case 8:
            case 9:
            default:
                enforceSystemUid();
                z = !this.mFirewallEnabled;
                break;
        }
        if (z) {
            if (i2 == 2) {
                return "deny";
            }
        } else if (i2 != 1) {
            return "deny";
        }
        return "allow";
    }

    public final InterfaceConfiguration getInterfaceConfig(String str) {
        PermissionUtils.enforceNetworkStackPermissionOr(
                this.mContext, new String[] {"android.permission.CONNECTIVITY_INTERNAL"});
        try {
            InterfaceConfigurationParcel interfaceGetCfg = this.mNetdService.interfaceGetCfg(str);
            try {
                InterfaceConfiguration interfaceConfiguration = new InterfaceConfiguration();
                interfaceConfiguration.setHardwareAddress(interfaceGetCfg.hwAddr);
                interfaceConfiguration.setLinkAddress(
                        new LinkAddress(
                                InetAddresses.parseNumericAddress(interfaceGetCfg.ipv4Addr),
                                interfaceGetCfg.prefixLength));
                for (String str2 : interfaceGetCfg.flags) {
                    interfaceConfiguration.setFlag(str2);
                }
                return interfaceConfiguration;
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException("Invalid InterfaceConfigurationParcel", e);
            }
        } catch (RemoteException | ServiceSpecificException e2) {
            throw new IllegalStateException(e2);
        }
    }

    public final boolean getIpForwardingEnabled() {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (SdkLevel.isAtLeastV()) {
            throw new UnsupportedOperationException(
                    "NMS#getIpForwardingEnabled not supported in V+");
        }
        try {
            return this.mNetdService.ipfwdEnabled();
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final int getL4sConnCount() {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            return this.mOemNetd.getL4sConnCount();
        } catch (RemoteException e) {
            Slog.e("NetworkManagement", "Error getL4sConnCount > " + e);
            return 0;
        }
    }

    public final long getNetworkStatsVideoCall(String str, int i, int i2) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
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
                    throw new IllegalStateException(
                            "problem parsing video call stats for " + str + ": " + e);
                }
            }
            return j + j2;
        } catch (RemoteException | ServiceSpecificException e2) {
            throw new IllegalStateException("problem parsing videocall stats: ", e2);
        }
    }

    public final int[] getTcpLocalPorts(int[] iArr) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        int[] iArr2 = new int[0];
        try {
            return this.mOemNetd.getTcpLocalPorts(iArr);
        } catch (RemoteException e) {
            Slog.e("NetworkManagement", "Error getTcpLocalPorts > " + e);
            return iArr2;
        }
    }

    public final SparseIntArray getUidFirewallRulesLR(int i) {
        switch (i) {
            case 0:
                return this.mUidFirewallRules;
            case 1:
                return this.mUidFirewallDozableRules;
            case 2:
                return this.mUidFirewallStandbyRules;
            case 3:
                return this.mUidFirewallPowerSaveRules;
            case 4:
                return this.mUidFirewallRestrictedRules;
            case 5:
                return this.mUidFirewallLowPowerStandbyRules;
            case 6:
                return this.mUidFirewallBackgroundRules;
            case 7:
                return this.mUidFirewallOemDenyRules;
            case 8:
            case 9:
            default:
                throw new IllegalArgumentException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown chain:"));
            case 10:
                return this.mUidMeteredFirewallAllowRules;
            case 11:
                return this.mUidMeteredFirewallDenyUserRules;
            case 12:
                return this.mUidMeteredFirewallDenyAdminRules;
        }
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

    public final boolean isBandwidthControlEnabled() {
        return true;
    }

    public final boolean isFirewallEnabled() {
        enforceSystemUid();
        return this.mFirewallEnabled;
    }

    public final boolean isNetworkRestricted(int i) {
        isNetworkRestricted_enforcePermission();
        return isNetworkRestrictedInternal(i);
    }

    public final boolean isNetworkRestrictedInternal(int i) {
        String str;
        synchronized (this.mRulesLock) {
            try {
                if (getFirewallChainState(2)) {
                    str = "Uid ";
                    if (this.mUidFirewallStandbyRules.get(i) == 2) {
                        if (DBG) {
                            Slog.d(
                                    "NetworkManagement",
                                    "Uid " + i + " restricted because of app standby mode");
                        }
                        return true;
                    }
                } else {
                    str = "Uid ";
                }
                if (getFirewallChainState(1) && this.mUidFirewallDozableRules.get(i) != 1) {
                    if (DBG) {
                        Slog.d(
                                "NetworkManagement",
                                "Uid " + i + " restricted because of device idle mode");
                    }
                    return true;
                }
                if (getFirewallChainState(3) && this.mUidFirewallPowerSaveRules.get(i) != 1) {
                    if (DBG) {
                        Slog.d(
                                "NetworkManagement",
                                "Uid " + i + " restricted because of power saver mode");
                    }
                    return true;
                }
                if (getFirewallChainState(4) && this.mUidFirewallRestrictedRules.get(i) != 1) {
                    if (DBG) {
                        Slog.d(
                                "NetworkManagement",
                                "Uid " + i + " restricted because of restricted mode");
                    }
                    return true;
                }
                if (getFirewallChainState(5) && this.mUidFirewallLowPowerStandbyRules.get(i) != 1) {
                    if (DBG) {
                        Slog.d(
                                "NetworkManagement",
                                "Uid " + i + " restricted because of low power standby");
                    }
                    return true;
                }
                if (getFirewallChainState(6) && this.mUidFirewallBackgroundRules.get(i) != 1) {
                    if (DBG) {
                        Slog.d(
                                "NetworkManagement",
                                "Uid " + i + " restricted because it is in background");
                    }
                    return true;
                }
                if (getFirewallChainState(7) && this.mUidFirewallOemDenyRules.get(i) == 2) {
                    if (DBG) {
                        Slog.d("NetworkManagement", "Uid " + i + " restricted because of freecess");
                    }
                    return true;
                }
                if (this.mUseMeteredFirewallChains) {
                    if (getFirewallChainState(11)
                            && this.mUidMeteredFirewallDenyUserRules.get(i) == 2) {
                        if (DBG) {
                            Slog.d(
                                    "NetworkManagement",
                                    "Uid "
                                            + i
                                            + " restricted because of user-restricted metered data"
                                            + " in the background");
                        }
                        return true;
                    }
                    if (getFirewallChainState(12)
                            && this.mUidMeteredFirewallDenyAdminRules.get(i) == 2) {
                        if (DBG) {
                            Slog.d(
                                    "NetworkManagement",
                                    "Uid "
                                            + i
                                            + " restricted because of admin-restricted metered data"
                                            + " in the background");
                        }
                        return true;
                    }
                    if (getFirewallChainState(10)
                            && this.mUidMeteredFirewallAllowRules.get(i) != 1) {
                        if (DBG) {
                            Slog.d(
                                    "NetworkManagement",
                                    "Uid " + i + " restricted because of data saver mode");
                        }
                        return true;
                    }
                } else {
                    if (this.mUidRejectOnMetered.get(i)) {
                        if (DBG) {
                            Slog.d(
                                    "NetworkManagement",
                                    "Uid "
                                            + i
                                            + " restricted because of no metered data in the"
                                            + " background");
                        }
                        return true;
                    }
                    if (this.mDataSaverMode && !this.mUidAllowOnMetered.get(i)) {
                        if (DBG) {
                            Slog.d(
                                    "NetworkManagement",
                                    str + i + " restricted because of data saver mode");
                        }
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isTetheringStarted() {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (SdkLevel.isAtLeastV()) {
            throw new UnsupportedOperationException("NMS#isTetheringStarted not supported in V+");
        }
        try {
            return this.mNetdService.tetherIsEnabled();
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final long[] l4StatsGet() {
        enforceSystemUid();
        try {
            return this.mOemNetd.l4StatsGet();
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final String[] listInterfaces() {
        PermissionUtils.enforceNetworkStackPermissionOr(
                this.mContext, new String[] {"android.permission.CONNECTIVITY_INTERNAL"});
        try {
            return this.mNetdService.interfaceGetList();
        } catch (RemoteException | ServiceSpecificException e) {
            AuditLog.log(
                    3,
                    4,
                    false,
                    Process.myPid(),
                    "NetworkManagementService",
                    String.format("Error listing interfaces: %s", e));
            throw new IllegalStateException(e);
        }
    }

    public final String[] listTetheredInterfaces() {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (SdkLevel.isAtLeastV()) {
            throw new UnsupportedOperationException(
                    "NMS#listTetheredInterfaces not supported in V+");
        }
        try {
            return this.mNetdService.tetherInterfaceList();
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void modifyEpdg(boolean z, String str, String str2, boolean z2) {
        StringBuilder sb = new StringBuilder("modifyEpdg epdg ");
        sb.append(z);
        sb.append(" ");
        sb.append(str);
        sb.append(" ");
        BootReceiver$$ExternalSyntheticOutline0.m(sb, str2, "NetworkManagement");
        try {
            this.mOemNetd.modifyEpdg(z, str, str2, z2);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void prepareNativeDaemon() {
        IBatteryStats iBatteryStats;
        SparseBooleanArray sparseBooleanArray;
        SparseBooleanArray sparseBooleanArray2;
        synchronized (this.mQuotaLock) {
            try {
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
                        setInterfaceQuota(
                                (String) entry.getKey(), ((Long) entry.getValue()).longValue());
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
                        setInterfaceAlert(
                                (String) entry2.getKey(), ((Long) entry2.getValue()).longValue());
                    }
                }
                if (!this.mUseMeteredFirewallChains) {
                    synchronized (this.mRulesLock) {
                        try {
                            int size3 = this.mUidRejectOnMetered.size();
                            sparseBooleanArray = null;
                            if (size3 > 0) {
                                if (DBG) {
                                    Slog.d(
                                            "NetworkManagement",
                                            "Pushing " + size3 + " UIDs to metered denylist rules");
                                }
                                sparseBooleanArray2 = this.mUidRejectOnMetered;
                                this.mUidRejectOnMetered = new SparseBooleanArray();
                            } else {
                                sparseBooleanArray2 = null;
                            }
                            int size4 = this.mUidAllowOnMetered.size();
                            if (size4 > 0) {
                                if (DBG) {
                                    Slog.d(
                                            "NetworkManagement",
                                            "Pushing "
                                                    + size4
                                                    + " UIDs to metered allowlist rules");
                                }
                                sparseBooleanArray = this.mUidAllowOnMetered;
                                this.mUidAllowOnMetered = new SparseBooleanArray();
                            }
                        } finally {
                        }
                    }
                    if (sparseBooleanArray2 != null) {
                        for (int i = 0; i < sparseBooleanArray2.size(); i++) {
                            setUidOnMeteredNetworkList(
                                    sparseBooleanArray2.keyAt(i),
                                    false,
                                    sparseBooleanArray2.valueAt(i));
                        }
                    }
                    if (sparseBooleanArray != null) {
                        for (int i2 = 0; i2 < sparseBooleanArray.size(); i2++) {
                            setUidOnMeteredNetworkList(
                                    sparseBooleanArray.keyAt(i2),
                                    true,
                                    sparseBooleanArray.valueAt(i2));
                        }
                    }
                }
                int size5 = this.mUidCleartextPolicy.size();
                if (size5 > 0) {
                    if (DBG) {
                        Slog.d(
                                "NetworkManagement",
                                "Pushing " + size5 + " active UID cleartext policies");
                    }
                    SparseIntArray sparseIntArray = this.mUidCleartextPolicy;
                    this.mUidCleartextPolicy = new SparseIntArray();
                    for (int i3 = 0; i3 < sparseIntArray.size(); i3++) {
                        setUidCleartextNetworkPolicy(
                                sparseIntArray.keyAt(i3), sparseIntArray.valueAt(i3));
                    }
                }
                setFirewallEnabled(this.mFirewallEnabled);
                syncFirewallChainLocked(0, "");
                syncFirewallChainLocked(2, "standby ");
                syncFirewallChainLocked(1, "dozable ");
                syncFirewallChainLocked(3, "powersave ");
                syncFirewallChainLocked(4, "restricted ");
                syncFirewallChainLocked(5, "low power standby ");
                syncFirewallChainLocked(6, "background");
                if (this.mUseMeteredFirewallChains) {
                    syncFirewallChainLocked(10, "metered_allow");
                    syncFirewallChainLocked(11, "metered_deny_user");
                    syncFirewallChainLocked(12, "metered_deny_admin");
                }
                syncFirewallChainLocked(7, "fw_oem deny_1 ");
                int[] iArr = {2, 1, 3, 4, 5, 6, 7};
                for (int i4 = 0; i4 < 7; i4++) {
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
            } catch (Throwable th) {
                throw th;
            }
        }
        try {
            synchronized (this) {
                try {
                    iBatteryStats = this.mBatteryStats;
                    if (iBatteryStats == null) {
                        this.mDeps.getClass();
                        iBatteryStats =
                                IBatteryStats.Stub.asInterface(
                                        ServiceManager.getService("batterystats"));
                        this.mBatteryStats = iBatteryStats;
                    }
                } finally {
                }
            }
            iBatteryStats.noteNetworkStatsEnabled();
        } catch (RemoteException unused) {
        }
    }

    public final int prioritizeApp(boolean z, int i) {
        enforceSystemUid();
        StringBuilder sb = new StringBuilder("prioritizeApp is called for uid ");
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

    public final int prioritizeMnxbApp(boolean z, int i) {
        enforceSystemUid();
        StringBuilder sb = new StringBuilder("prioritizeApp is called for uid ");
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

    public final void registerCloEventObserver(ICloEventObserver iCloEventObserver) {
        Log.v("NetworkManagement", "registerCloEventObserver");
        enforceSystemUid();
        try {
            this.mOemNetd.registerCloEventListener(iCloEventObserver);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void registerNetdTetherEventListener() {
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

    public final void registerObserver(
            INetworkManagementEventObserver iNetworkManagementEventObserver) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        this.mObservers.register(iNetworkManagementEventObserver);
    }

    public final void removeChain(String str, String str2) {
        Slog.d("NetworkManagement", "removechain" + str);
        Slog.d("NetworkManagement", "iptype : " + str2);
        enforceSystemUid();
        try {
            this.mOemNetd.removeMptcpChain(str, str2);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void removeInterfaceAlert(String str) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
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

    public final void removeInterfaceQuota(String str) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        synchronized (this.mQuotaLock) {
            try {
                if (this.mActiveQuotas.containsKey(str)) {
                    this.mActiveQuotas.remove(str);
                    this.mActiveAlerts.remove(str);
                    try {
                        this.mNetdService.bandwidthRemoveInterfaceQuota(str);
                    } catch (RemoteException | ServiceSpecificException e) {
                        throw new IllegalStateException(e);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeLegacyRoute(int i, String str, String str2, String str3, int i2) {
        Slog.d("NetworkManagement", "removeLegacyRoute" + str);
        enforceSystemUid();
        try {
            this.mOemNetd.networkRemoveLegacyRoute(i, str, str2, str3, i2);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void removeMptcpLink(String str) {
        Slog.d("NetworkManagement", "removemptcplink" + str);
        enforceSystemUid();
        try {
            this.mOemNetd.removeMptcpLinkIface(str);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void removeSocksRule(String str, String str2, String str3, int i, String str4) {
        Slog.d("NetworkManagement", "removeSocksRule");
        enforceSystemUid();
        try {
            this.mOemNetd.removeMptcpSocksRule(str, str2, str3, i, str4);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void removeSocksSkipRule(String str, String str2, String str3) {
        Slog.d("NetworkManagement", "removeSocksSkipRule");
        enforceSystemUid();
        try {
            this.mOemNetd.removeMptcpSocksSkipRule(str, str2, str3);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void removeSocksSkipRuleProto(
            String str, String str2, String str3, int i, String str4) {
        Slog.d("NetworkManagement", "removeSocksSkipRule");
        enforceSystemUid();
        try {
            this.mOemNetd.removeMptcpSocksSkipRuleProto(str, str2, str3, i, str4);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void removeTosPolicy(int i) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.removeTosPolicy(i);
        } catch (RemoteException e) {
            Slog.e("NetworkManagement", "Error removeTosPolicy > " + e);
        }
    }

    public final void removeUidFromChain(String str, String str2, int i) {
        Slog.d("NetworkManagement", "removeUidFromChain");
        enforceSystemUid();
        try {
            this.mOemNetd.removeUidFromMptcpChain(str, i, str2);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void removeUidSocksRule(
            String str, String str2, String str3, int i, int i2, String str4) {
        Slog.d("NetworkManagement", "removeSocksRule");
        enforceSystemUid();
        try {
            this.mOemNetd.removeMptcpUidSocksRule(str, str2, str3, i, i2, str4);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final int replaceApeRule(String str, int i, int i2) {
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

    public final int replaceMnxbRule(String str, int i, int i2) {
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

    public final String runKnoxFirewallRulesCommand(int i, String str) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            return this.mOemNetd.runKnoxFirewallRulesCommand(i, str);
        } catch (Exception unused) {
            Log.e("NetworkManagement", "runKnoxFirewallRulesCommand failed for vpn profile");
            return null;
        }
    }

    public final void runKnoxRulesCommand(int i, String[] strArr) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.runKnoxRulesCommand(i, strArr);
        } catch (Exception unused) {
            Log.e("NetworkManagement", "runKnoxRulesCommand failed for vpn profile");
        }
    }

    public final void setAdvertiseWindowSize(int i) {
        Log.v(
                "NetworkManagement",
                "setAdvertiseWindowSize is called, to set advertise window as " + i);
        enforceSystemUid();
        try {
            this.mOemNetd.setAdvertiseWindowSize(i);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setAllowHostAlone(String str) {
        enforceSystemUid();
        Log.e("NetworkManagement", "setAllowHostAlone");
        try {
            this.mOemNetd.setAllowHostAlone(str);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setAllowListIPs(String str) {
        enforceSystemUid();
        Log.e("NetworkManagement", "setAllowListIPs");
        try {
            this.mOemNetd.setAllowListIPs(str);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setAutoConf(String str, boolean z) {
        NetworkStack.checkNetworkStackPermission(this.mContext);
        try {
            this.mOemNetd.interfaceSetAutoConf(str, z);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setBlockAllDNSPackets(boolean z) {
        enforceSystemUid();
        Log.e("NetworkManagement", "setBlockAllDNSPackets");
        try {
            this.mOemNetd.setBlockAllDNSPackets(z);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setBlockAllPackets() {
        enforceSystemUid();
        Log.e("NetworkManagement", "setBlockAllPackets");
        try {
            this.mOemNetd.setBlockAllPackets();
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setBlockHostAlone(String str) {
        enforceSystemUid();
        Log.e("NetworkManagement", "setBlockHostAlone");
        try {
            this.mOemNetd.setBlockHostAlone(str);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setBlockListIPs(String str) {
        enforceSystemUid();
        Log.e("NetworkManagement", "setBlockListIPs");
        try {
            this.mOemNetd.setBlockListIPs(str);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setBlockPorts(String str, int i, String str2) {
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

    /* JADX WARN: Not initialized variable reg: 5, insn: 0x00b7: INVOKE (r5 I:long) STATIC call: android.os.Trace.traceEnd(long):void A[Catch: all -> 0x0032, MD:(long):void (s)], block:B:46:0x00b7 */
    public final boolean setDataSaverModeEnabled(boolean z) {
        long traceEnd;
        setDataSaverModeEnabled_enforcePermission();
        if (DBG) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    "setDataSaverMode: ", "NetworkManagement", z);
        }
        synchronized (this.mQuotaLock) {
            try {
                if (this.mDataSaverMode == z) {
                    Log.w(
                            "NetworkManagement",
                            "setDataSaverMode(): already " + this.mDataSaverMode);
                    return true;
                }
                try {
                    Trace.traceBegin(2097152L, "setDataSaverModeEnabled");
                    try {
                        if (SdkLevel.isAtLeastV()) {
                            ((ConnectivityManager)
                                            this.mContext.getSystemService(
                                                    ConnectivityManager.class))
                                    .setDataSaverEnabled(z);
                            this.mDataSaverMode = z;
                            if (this.mUseMeteredFirewallChains) {
                                synchronized (this.mRulesLock) {
                                    this.mFirewallChainStates.put(10, z);
                                }
                            }
                            Trace.traceEnd(2097152L);
                            return true;
                        }
                        boolean bandwidthEnableDataSaver =
                                this.mNetdService.bandwidthEnableDataSaver(z);
                        if (bandwidthEnableDataSaver) {
                            this.mDataSaverMode = z;
                        } else {
                            Log.e(
                                    "NetworkManagement",
                                    "setDataSaverMode(" + z + "): failed to set iptables");
                        }
                        Trace.traceEnd(2097152L);
                        return bandwidthEnableDataSaver;
                    } catch (RemoteException | IllegalStateException e) {
                        Log.e(
                                "NetworkManagement",
                                "setDataSaverMode(" + z + "): failed with exception",
                                e);
                        Trace.traceEnd(2097152L);
                        return false;
                    }
                } catch (Throwable th) {
                    Trace.traceEnd(traceEnd);
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void setDestinationBasedMarkRule(
            boolean z, String str, String str2, int i, int i2) {
        Slog.d("NetworkManagement", "setDestinationBasedMarkRule");
        enforceSystemUid();
        try {
            this.mOemNetd.setMptcpDestBaseMarkRule(z, str, str2, i, i2);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setDnsForwardersForKnoxVpn(int i, String[] strArr) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mNetdService.tetherDnsSet(i, strArr);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setEpdgInterfaceDropRule(String str, String str2, boolean z) {
        Slog.d("NetworkManagement", "setEpdgInterfaceDropRule");
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.setEpdgInterfaceDropRule(str, str2, z);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setFirewallChainEnabled(int i, boolean z) {
        enforceSystemUid();
        synchronized (this.mQuotaLock) {
            synchronized (this.mRulesLock) {
                if (getFirewallChainState(i) == z) {
                    return;
                }
                synchronized (this.mRulesLock) {
                    this.mFirewallChainStates.put(i, z);
                }
                switch (i) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        try {
                            ((ConnectivityManager)
                                            this.mContext.getSystemService(
                                                    ConnectivityManager.class))
                                    .setFirewallChainEnabled(i, z);
                            return;
                        } catch (RuntimeException e) {
                            throw new IllegalStateException(e);
                        }
                    default:
                        throw new IllegalArgumentException(
                                "Invalid chain for setFirewallChainEnabled: "
                                        + NetworkPolicyLogger.getFirewallChainName(i));
                }
            }
        }
    }

    public final void setFirewallEnabled(boolean z) {
        enforceSystemUid();
        try {
            this.mNetdService.firewallSetFirewallType(!z ? 1 : 0);
            this.mFirewallEnabled = z;
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setFirewallRuleMobileData(int i, boolean z) {
        enforceSystemUidexceptUserId();
        try {
            this.mOemNetd.firewallSetRuleMobileData(i, z);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setFirewallRuleWifi(int i, boolean z) {
        enforceSystemUidexceptUserId();
        try {
            this.mOemNetd.firewallSetRuleWifi(i, z);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setFirewallUidRule(int i, int i2, int i3) {
        enforceSystemUid();
        synchronized (this.mQuotaLock) {
            if (updateFirewallUidRuleLocked(i, i2, i3)) {
                try {
                    ((ConnectivityManager)
                                    this.mContext.getSystemService(ConnectivityManager.class))
                            .setUidFirewallRule(i, i2, i3);
                } catch (RuntimeException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    public final void setFirewallUidRules(int i, int[] iArr, int[] iArr2) {
        enforceSystemUid();
        synchronized (this.mQuotaLock) {
            synchronized (this.mRulesLock) {
                try {
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
                } catch (Throwable th) {
                    throw th;
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

    public final void setIPv6AddrGenMode(String str, int i) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        try {
            this.mNetdService.setIPv6AddrGenMode(str, i);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public final void setInterfaceAlert(String str, long j) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (!this.mActiveQuotas.containsKey(str)) {
            throw new IllegalStateException("setting alert requires existing quota on iface");
        }
        synchronized (this.mQuotaLock) {
            try {
                if (this.mActiveAlerts.containsKey(str)) {
                    throw new IllegalStateException("iface " + str + " already has alert");
                }
                try {
                    this.mNetdService.bandwidthSetInterfaceAlert(str, j);
                    this.mActiveAlerts.put(str, Long.valueOf(j));
                } catch (RemoteException | ServiceSpecificException e) {
                    throw new IllegalStateException(e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setInterfaceConfig(
            String str, InterfaceConfiguration interfaceConfiguration) {
        PermissionUtils.enforceNetworkStackPermissionOr(
                this.mContext, new String[] {"android.permission.CONNECTIVITY_INTERNAL"});
        LinkAddress linkAddress = interfaceConfiguration.getLinkAddress();
        if (linkAddress == null || linkAddress.getAddress() == null) {
            throw new IllegalStateException("Null LinkAddress given");
        }
        InterfaceConfigurationParcel interfaceConfigurationParcel =
                new InterfaceConfigurationParcel();
        interfaceConfigurationParcel.ifName = str;
        String hardwareAddress = interfaceConfiguration.getHardwareAddress();
        if (TextUtils.isEmpty(hardwareAddress)) {
            interfaceConfigurationParcel.hwAddr = "";
        } else {
            interfaceConfigurationParcel.hwAddr = hardwareAddress;
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
        try {
            this.mNetdService.interfaceSetCfg(interfaceConfigurationParcel);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setInterfaceDown(String str) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        InterfaceConfiguration interfaceConfig = getInterfaceConfig(str);
        interfaceConfig.setInterfaceDown();
        setInterfaceConfig(str, interfaceConfig);
    }

    public final void setInterfaceIpv6PrivacyExtensions(String str, boolean z) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        try {
            this.mNetdService.interfaceSetIPv6PrivacyExtensions(str, z);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setInterfaceQuota(String str, long j) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        synchronized (this.mQuotaLock) {
            try {
                if (this.mActiveQuotas.containsKey(str)) {
                    throw new IllegalStateException("iface " + str + " already has quota");
                }
                try {
                    this.mNetdService.bandwidthSetInterfaceQuota(str, j);
                    this.mActiveQuotas.put(str, Long.valueOf(j));
                } catch (RemoteException | ServiceSpecificException e) {
                    throw new IllegalStateException(e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setInterfaceUp(String str) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        InterfaceConfiguration interfaceConfig = getInterfaceConfig(str);
        interfaceConfig.setInterfaceUp();
        setInterfaceConfig(str, interfaceConfig);
    }

    public final void setIpForwardingEnabled(boolean z) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (SdkLevel.isAtLeastV()) {
            throw new UnsupportedOperationException(
                    "NMS#setIpForwardingEnabled not supported in V+");
        }
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

    public final void setKnoxVpn(int i, boolean z) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.setKnoxVpn(i, z);
        } catch (Exception unused) {
            Log.e("NetworkManagement", "setKnoxVpn failed for vpn profile");
        }
    }

    public final void setMptcpMtuValue(String str, int i) {
        Slog.d("NetworkManagement", "setMptcpMtuValues");
        enforceSystemUid();
        try {
            this.mOemNetd.setMtuValueMptcp(str, i);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setNetworkInfo(int i, boolean z, int i2) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.setNetworkInfo(i, z, i2);
        } catch (Exception unused) {
            Log.e("NetworkManagement", "setNetworkInfo failed for chained vpn profile");
        }
    }

    public final void setOnlyAllowIPs(String str) {
        enforceSystemUid();
        Log.e("NetworkManagement", "setOnlyAllowIPs");
        try {
            this.mOemNetd.setOnlyAllowIPs(str);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setPrivateIpRoute(boolean z, String str, int i) {
        Slog.d("NetworkManagement", "setPrivateIpRoute");
        enforceSystemUid();
        try {
            this.mOemNetd.setMptcpPrivateIpRoute(z, str, i);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setQboxUid(int i, boolean z) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.setQboxUid(i, z);
        } catch (RemoteException e) {
            Slog.e("NetworkManagement", "Error setQboxUid > " + e);
        }
    }

    public final void setTcpBufferSize(String str, String str2) {
        Slog.d("NetworkManagement", "setTcpBufferSize");
        enforceSystemUid();
        try {
            this.mOemNetd.setMptcpTcpBufferSize(str, str2);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setUIDRoute(boolean z, String str, int i, String str2, String str3) {
        Slog.d("NetworkManagement", "setUIDRoute");
        enforceSystemUid();
        try {
            this.mOemNetd.setMptcpUIDRoute(z, str, i, str2, str3);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setUidCleartextNetworkPolicy(int i, int i2) {
        this.mDeps.getClass();
        if (Binder.getCallingUid() != i) {
            PermissionUtils.enforceNetworkStackPermission(this.mContext);
        }
        synchronized (this.mQuotaLock) {
            try {
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setUidOnMeteredNetworkAllowlist(int i, boolean z) {
        setUidOnMeteredNetworkList(i, true, z);
    }

    public final void setUidOnMeteredNetworkDenylist(int i, boolean z) {
        setUidOnMeteredNetworkList(i, false, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x001e, code lost:

       r6 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x006f, code lost:

       throw r6;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setUidOnMeteredNetworkList(int r7, boolean r8, boolean r9) {
        /*
            r6 = this;
            android.content.Context r0 = r6.mContext
            com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(r0)
            java.lang.Object r0 = r6.mQuotaLock
            monitor-enter(r0)
            java.lang.Object r1 = r6.mRulesLock     // Catch: java.lang.Throwable -> L1e
            monitor-enter(r1)     // Catch: java.lang.Throwable -> L1e
            if (r8 == 0) goto L12
            android.util.SparseBooleanArray r2 = r6.mUidAllowOnMetered     // Catch: java.lang.Throwable -> L10
            goto L14
        L10:
            r6 = move-exception
            goto L6c
        L12:
            android.util.SparseBooleanArray r2 = r6.mUidRejectOnMetered     // Catch: java.lang.Throwable -> L10
        L14:
            r3 = 0
            boolean r3 = r2.get(r7, r3)     // Catch: java.lang.Throwable -> L10
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L10
            if (r3 != r9) goto L20
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1e
            return
        L1e:
            r6 = move-exception
            goto L6e
        L20:
            java.lang.String r1 = "inetd bandwidth"
            r3 = 2097152(0x200000, double:1.0361308E-317)
            android.os.Trace.traceBegin(r3, r1)     // Catch: java.lang.Throwable -> L1e
            android.content.Context r1 = r6.mContext     // Catch: java.lang.Throwable -> L1e
            java.lang.Class<android.net.ConnectivityManager> r5 = android.net.ConnectivityManager.class
            java.lang.Object r1 = r1.getSystemService(r5)     // Catch: java.lang.Throwable -> L1e
            android.net.ConnectivityManager r1 = (android.net.ConnectivityManager) r1     // Catch: java.lang.Throwable -> L1e
            if (r8 == 0) goto L42
            if (r9 == 0) goto L3e
            r1.addUidToMeteredNetworkAllowList(r7)     // Catch: java.lang.Throwable -> L3a java.lang.RuntimeException -> L3c
            goto L4b
        L3a:
            r6 = move-exception
            goto L68
        L3c:
            r6 = move-exception
            goto L62
        L3e:
            r1.removeUidFromMeteredNetworkAllowList(r7)     // Catch: java.lang.Throwable -> L3a java.lang.RuntimeException -> L3c
            goto L4b
        L42:
            if (r9 == 0) goto L48
            r1.addUidToMeteredNetworkDenyList(r7)     // Catch: java.lang.Throwable -> L3a java.lang.RuntimeException -> L3c
            goto L4b
        L48:
            r1.removeUidFromMeteredNetworkDenyList(r7)     // Catch: java.lang.Throwable -> L3a java.lang.RuntimeException -> L3c
        L4b:
            java.lang.Object r6 = r6.mRulesLock     // Catch: java.lang.Throwable -> L3a java.lang.RuntimeException -> L3c
            monitor-enter(r6)     // Catch: java.lang.Throwable -> L3a java.lang.RuntimeException -> L3c
            if (r9 == 0) goto L57
            r8 = 1
            r2.put(r7, r8)     // Catch: java.lang.Throwable -> L55
            goto L5a
        L55:
            r7 = move-exception
            goto L60
        L57:
            r2.delete(r7)     // Catch: java.lang.Throwable -> L55
        L5a:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L55
            android.os.Trace.traceEnd(r3)     // Catch: java.lang.Throwable -> L1e
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1e
            return
        L60:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L55
            throw r7     // Catch: java.lang.Throwable -> L3a java.lang.RuntimeException -> L3c
        L62:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L3a
            r7.<init>(r6)     // Catch: java.lang.Throwable -> L3a
            throw r7     // Catch: java.lang.Throwable -> L3a
        L68:
            android.os.Trace.traceEnd(r3)     // Catch: java.lang.Throwable -> L1e
            throw r6     // Catch: java.lang.Throwable -> L1e
        L6c:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L10
            throw r6     // Catch: java.lang.Throwable -> L1e
        L6e:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1e
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkManagementService.setUidOnMeteredNetworkList(int,"
                    + " boolean, boolean):void");
    }

    public final void setUrlFirewallRuleMobileData(int i, String str, boolean z) {
        enforceSystemUidexceptUserId();
        Preconditions.checkState(this.mFirewallEnabled);
        try {
            this.mOemNetd.gmsCoreSetUidUrlMobileDataRule(i, str, z ? 1 : 2);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void setUrlFirewallRuleWifi(int i, String str, boolean z) {
        enforceSystemUidexceptUserId();
        Preconditions.checkState(this.mFirewallEnabled);
        try {
            this.mOemNetd.gmsCoreSetUidUrlWifiRule(i, str, z ? 1 : 2);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void shutdown() {
        shutdown_enforcePermission();
        Slog.i("NetworkManagement", "Shutting down");
    }

    public final void spegRestrictNetworkConnection(int i, boolean z) {
        enforceSystemUid();
        try {
            this.mOemNetd.spegRestrictNetworkConnection(i, z);
        } catch (RemoteException | ServiceSpecificException e) {
            Log.e("SPEG", "spegRestrictNetworkConnection Error", e);
        }
    }

    public final int startL4s(String str) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            return this.mOemNetd.startL4s(str);
        } catch (RemoteException e) {
            Slog.e("NetworkManagement", "Error startL4s > " + e);
            return -1;
        }
    }

    public final void startNetworkStatsOnPorts(String str, int i, int i2) {
        GestureWakeup$$ExternalSyntheticOutline0.m(
                StorageManagerService$$ExternalSyntheticOutline0.m(
                        i, "startNetworkStatsOnPorts iface:", str, " in:", " out:"),
                i2,
                "NetworkManagement");
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        try {
            this.mOemNetd.startVideoStats(str, i, i2);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void startQbox(String str) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.startQbox(str);
        } catch (RemoteException e) {
            Slog.e("NetworkManagement", "Error startQbox > " + e);
        }
    }

    public final void startTethering(String[] strArr) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (SdkLevel.isAtLeastV()) {
            throw new UnsupportedOperationException("NMS#startTethering not supported in V+");
        }
        try {
            NetdUtils.tetherStart(this.mNetdService, true, strArr);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void startTosMarker(String str) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.startTosMarker(str);
        } catch (RemoteException e) {
            Slog.e("NetworkManagement", "Error startTosMarker > " + e);
        }
    }

    public final int stopL4s(String str) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            return this.mOemNetd.stopL4s(str);
        } catch (RemoteException e) {
            Slog.e("NetworkManagement", "Error stopL4s > " + e);
            return -1;
        }
    }

    public final void stopNetworkStatsOnPorts(String str, int i, int i2) {
        GestureWakeup$$ExternalSyntheticOutline0.m(
                StorageManagerService$$ExternalSyntheticOutline0.m(
                        i, "stopNetworkStatsOnPorts iface:", str, " in:", " out:"),
                i2,
                "NetworkManagement");
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        try {
            this.mOemNetd.stopVideoStats(str, i, i2);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void stopQbox() {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.stopQbox();
        } catch (RemoteException e) {
            Slog.e("NetworkManagement", "Error stopQbox > " + e);
        }
    }

    public final void stopTethering() {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (SdkLevel.isAtLeastV()) {
            throw new UnsupportedOperationException("NMS#stopTethering not supported in V+");
        }
        try {
            this.mNetdService.tetherStop();
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void stopTosMarker(String str) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.stopTosMarker(str);
        } catch (RemoteException e) {
            Slog.e("NetworkManagement", "Error stopTosMarker > " + e);
        }
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
                int keyAt = clone.keyAt(i2);
                int valueAt = clone.valueAt(i2);
                if (updateFirewallUidRuleLocked(i, keyAt, valueAt)) {
                    try {
                        ((ConnectivityManager)
                                        this.mContext.getSystemService(ConnectivityManager.class))
                                .setUidFirewallRule(i, keyAt, valueAt);
                    } catch (RuntimeException e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
        }
    }

    public final void tetherInterface(String str) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (SdkLevel.isAtLeastV()) {
            throw new UnsupportedOperationException("NMS#tetherInterface not supported in V+");
        }
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

    public final void unregisterCloEventObserver() {
        Log.v("NetworkManagement", "unregisterCloEventObserver");
        enforceSystemUid();
        try {
            this.mOemNetd.unregisterCloEventListener();
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void unregisterNetdTetherEventListener() {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.unregisterNetdTetherEventListener();
        } catch (Exception unused) {
            Log.e("NetworkManagement", "unregisterNetdTetherEventListener failed ");
        }
    }

    public final void unregisterObserver(
            INetworkManagementEventObserver iNetworkManagementEventObserver) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        this.mObservers.unregister(iNetworkManagementEventObserver);
    }

    public final void untetherInterface(String str) {
        PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (SdkLevel.isAtLeastV()) {
            throw new UnsupportedOperationException("NMS#untetherInterface not supported in V+");
        }
        try {
            NetdUtils.untetherInterface(this.mNetdService, str);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void updateDefaultGatewayForEpdg(Network network) {
        LinkProperties linkProperties;
        Slog.d("NetworkManagement", "updateDefaultGatewayForEpdg");
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        int netId = network.getNetId();
        try {
            linkProperties =
                    ((ConnectivityManager)
                                    this.mContext.getSystemService(ConnectivityManager.class))
                            .getLinkProperties(network);
        } catch (Exception e) {
            KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                    e, "Error getLinkProperties: ", "NetworkManagement");
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
                Slog.d(
                        "NetworkManagement",
                        "Updating Route [" + routeInfo + "] from network " + netId);
                try {
                    this.mNetdService.networkUpdateRouteParcel(netId, convertRouteInfo(routeInfo));
                } catch (Exception e2) {
                    KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                            e2, "Exception in networkUpdateRouteParcel: ", "NetworkManagement");
                }
            }
        }
    }

    public final boolean updateFirewallUidRuleLocked(int i, int i2, int i3) {
        synchronized (this.mRulesLock) {
            try {
                SparseIntArray uidFirewallRulesLR = getUidFirewallRulesLR(i);
                int i4 = uidFirewallRulesLR.get(i2, 0);
                boolean z = DBG;
                if (z) {
                    Slog.d(
                            "NetworkManagement",
                            "oldRule = "
                                    + i4
                                    + ", newRule="
                                    + i3
                                    + " for uid="
                                    + i2
                                    + " on chain "
                                    + i);
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
                return !firewallRuleName.equals(firewallRuleName2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateGroFlushTime(long j) {
        Log.v("NetworkManagement", "updateGroFlushTime: " + j);
        enforceSystemUid();
        try {
            this.mOemNetd.updateGroFlushTime(j);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void updateGroPshOption(int i) {
        Log.v("NetworkManagement", "updateGroPshOption: " + i);
        enforceSystemUid();
        try {
            this.mOemNetd.updateGroPshOption(i);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void updateInputFilterAppWideRules(int[] iArr, int i, int i2) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.updateInputFilterAppWideRules(iArr, i, i2);
        } catch (Exception unused) {
            Log.e("NetworkManagement", "updateInputFilterAppWideRules failed for vpn profile");
        }
    }

    public final void updateInputFilterExemptRules(int i, int i2) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.updateInputFilterExemptRules(i, i2);
        } catch (Exception unused) {
            Log.e("NetworkManagement", "updateInputFilterExemptRules failed for vpn profile");
        }
    }

    public final void updateInputFilterUserWideRules(int[] iArr, int i, int i2) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CONNECTIVITY_INTERNAL", "NetworkManagement");
        try {
            this.mOemNetd.updateInputFilterUserWideRules(iArr, i, i2);
        } catch (Exception unused) {
            Log.e("NetworkManagement", "updateInputFilterUserWideRules failed for vpn profile");
        }
    }

    public final void updateSourceRule(boolean z, String str, String str2) {
        Slog.d("NetworkManagement", "updateSourceRule");
        enforceSystemUid();
        try {
            this.mOemNetd.updateMptcpSourceRule(z, str, str2);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }
}
