package com.android.server.connectivity;

import android.R;
import android.app.AppGlobals;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.INetd;
import android.net.INetworkManagementEventObserver;
import android.net.IVpnManager;
import android.net.InetAddresses;
import android.net.IpPrefix;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkAgent;
import android.net.NetworkAgentConfig;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkProvider;
import android.net.NetworkScore;
import android.net.ProxyInfo;
import android.net.RouteInfo;
import android.net.VpnTransportInfo;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArraySet;
import android.util.Log;
import android.util.Range;

import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.FunctionalUtils;
import com.android.net.module.util.NetworkStackConstants;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.net.BaseNetworkObserver;

import libcore.io.IoUtils;

import com.samsung.android.knox.net.vpn.IKnoxVpnPolicy;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EnterpriseVpn {
    public static final boolean DBG = Debug.semIsProductDev();
    public String mAddress;
    public VpnConfig mConfig;
    public Connection mConnection;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public String mInterface;
    public final AnonymousClass3 mLocaleChangedReceiver;
    public final Looper mLooper;
    public final INetd mNetd;
    public AnonymousClass1 mNetworkAgent;
    public NetworkCapabilities mNetworkCapabilities;
    public final NetworkInfo mNetworkInfo;
    public final NetworkProvider mNetworkProvider;
    public final INetworkManagementService mNms;
    public final AnonymousClass2 mObserver;
    public final int mOwnerUID;
    public final String mPackage;
    public String mProfileName;
    public final int mUserId;
    public final UserManager mUserManager;
    public boolean isMetaDataEnabled = false;
    public ProxyInfo mHttpProxyInfo = null;
    public String mV6Address = null;
    public boolean isVpnObjectRemoved = false;
    public final Set knoxVpnUidRanges = new ArraySet();

    /* renamed from: com.android.server.connectivity.EnterpriseVpn$4, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass4 {
        public static final /* synthetic */ int[] $SwitchMap$android$net$NetworkInfo$DetailedState;

        static {
            int[] iArr = new int[NetworkInfo.DetailedState.values().length];
            $SwitchMap$android$net$NetworkInfo$DetailedState = iArr;
            try {
                iArr[NetworkInfo.DetailedState.CONNECTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[
                                NetworkInfo.DetailedState.DISCONNECTED.ordinal()] =
                        2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[
                                NetworkInfo.DetailedState.FAILED.ordinal()] =
                        3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[
                                NetworkInfo.DetailedState.CONNECTING.ordinal()] =
                        4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[
                                NetworkInfo.DetailedState.DISCONNECTING.ordinal()] =
                        5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public final class Connection implements ServiceConnection {
        public IBinder mService;

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.mService = iBinder;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {}
    }

    /* renamed from: -$$Nest$mcleanupVpnStateLocked, reason: not valid java name */
    public static void m364$$Nest$mcleanupVpnStateLocked(EnterpriseVpn enterpriseVpn) {
        enterpriseVpn.getClass();
        enterpriseVpn.mNetworkCapabilities =
                new NetworkCapabilities.Builder(enterpriseVpn.mNetworkCapabilities)
                        .setUids((Set) null)
                        .setTransportInfo(new VpnTransportInfo(-1, (String) null))
                        .build();
        enterpriseVpn.mConfig = null;
        enterpriseVpn.mInterface = null;
        enterpriseVpn.mConnection = null;
        enterpriseVpn.updateState(NetworkInfo.DetailedState.DISCONNECTED, "agentDisconnect");
    }

    /* renamed from: -$$Nest$mcloseInterface, reason: not valid java name */
    public static void m365$$Nest$mcloseInterface(EnterpriseVpn enterpriseVpn) {
        String str = enterpriseVpn.mInterface;
        if (str != null) {
            enterpriseVpn.jniReset(str);
        }
    }

    /* JADX WARN: Type inference failed for: r4v0, types: [com.android.server.connectivity.EnterpriseVpn$3] */
    public EnterpriseVpn(
            Looper looper,
            Context context,
            INetworkManagementService iNetworkManagementService,
            INetd iNetd,
            int i,
            String str,
            int i2) {
        int i3;
        INetworkManagementEventObserver iNetworkManagementEventObserver =
                new BaseNetworkObserver() { // from class:
                    // com.android.server.connectivity.EnterpriseVpn.2
                    public final void interfaceRemoved(String str2) {
                        synchronized (EnterpriseVpn.this) {
                            if (str2.equals(EnterpriseVpn.this.mInterface)
                                    && EnterpriseVpn.this.jniCheck(str2) == 0) {
                                Log.d("knoxEnterpriseVpn", "Interface removed: ".concat(str2));
                                try {
                                    EnterpriseVpn enterpriseVpn = EnterpriseVpn.this;
                                    Connection connection = enterpriseVpn.mConnection;
                                    if (connection != null) {
                                        enterpriseVpn.mContext.unbindService(connection);
                                        EnterpriseVpn.m365$$Nest$mcloseInterface(
                                                EnterpriseVpn.this);
                                        EnterpriseVpn.this.getClass();
                                        EnterpriseVpn.this.updateState(
                                                NetworkInfo.DetailedState.DISCONNECTING,
                                                "agentDisconnect");
                                        IVpnManager asInterface =
                                                IVpnManager.Stub.asInterface(
                                                        ServiceManager.getService(
                                                                "vpn_management"));
                                        EnterpriseVpn enterpriseVpn2 = EnterpriseVpn.this;
                                        asInterface.disconnectKnoxVpn(
                                                enterpriseVpn2.mProfileName,
                                                enterpriseVpn2.mOwnerUID);
                                        EnterpriseVpn.m364$$Nest$mcleanupVpnStateLocked(
                                                EnterpriseVpn.this);
                                        EnterpriseVpn.getKnoxVpnService()
                                                .removeVpnUidRanges(
                                                        EnterpriseVpn.this.mProfileName);
                                        if (EnterpriseVpn.this.isVpnObjectRemoved) {
                                            Log.d(
                                                    "knoxEnterpriseVpn",
                                                    "resetting the network capability and"
                                                        + " unregistering the observer since the"
                                                        + " interface is removed");
                                            EnterpriseVpn enterpriseVpn3 = EnterpriseVpn.this;
                                            enterpriseVpn3.hideNotification(enterpriseVpn3.mUserId);
                                            EnterpriseVpn.this.cleanupObjects();
                                        }
                                    }
                                } catch (Exception e) {
                                    Log.e(
                                            "knoxEnterpriseVpn",
                                            "Exception at  interfaceRemoved : "
                                                    + Log.getStackTraceString(e));
                                }
                            }
                        }
                    }
                };
        this.mObserver = iNetworkManagementEventObserver;
        this.mLocaleChangedReceiver =
                new BroadcastReceiver() { // from class:
                    // com.android.server.connectivity.EnterpriseVpn.3
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        String action = intent.getAction();
                        if (action != null
                                && action.equals("android.intent.action.LOCALE_CHANGED")) {
                            Log.d(
                                    "knoxEnterpriseVpn",
                                    "Locale changed. Updating Knox vpn notification and the number"
                                        + " of user present is ");
                            NetworkInfo networkInfo = EnterpriseVpn.this.mNetworkInfo;
                            if (networkInfo == null || !networkInfo.isConnected()) {
                                return;
                            }
                            EnterpriseVpn enterpriseVpn = EnterpriseVpn.this;
                            boolean z = EnterpriseVpn.DBG;
                            if (z) {
                                GestureWakeup$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("updateNotification > user : "),
                                        enterpriseVpn.mUserId,
                                        "knoxEnterpriseVpn");
                            } else {
                                enterpriseVpn.getClass();
                            }
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                try {
                                    NotificationManager notificationManager =
                                            (NotificationManager)
                                                    enterpriseVpn.mContext.getSystemService(
                                                            "notification");
                                    enterpriseVpn.mContext.getString(
                                            R.string.permdesc_postNotification);
                                    enterpriseVpn.mContext.getString(
                                            R.string.permdesc_persistentActivity);
                                    if (z) {
                                        Log.d(
                                                "knoxEnterpriseVpn",
                                                "updateNotification > vpn type is per-app");
                                    }
                                    List domainsByProfileName =
                                            EnterpriseVpn.getDomainsByProfileName(
                                                    enterpriseVpn.mConfig.session);
                                    if (domainsByProfileName != null) {
                                        Iterator it = domainsByProfileName.iterator();
                                        while (it.hasNext()) {
                                            int parseInt = Integer.parseInt((String) it.next());
                                            boolean z2 =
                                                    EnterpriseVpn.getKnoxVpnService()
                                                                    .getNotificationDismissibleFlagInternal(
                                                                            parseInt)
                                                            != 0;
                                            Notification createNotification =
                                                    enterpriseVpn.createNotification(
                                                            VpnConfig
                                                                    .getIntentForStatusPanelRefreshAsUser(
                                                                            enterpriseVpn.mContext,
                                                                            parseInt),
                                                            z2);
                                            if (z2) {
                                                notificationManager.notifyAsUser(
                                                        "Knox_Notification",
                                                        100000,
                                                        createNotification,
                                                        new UserHandle(parseInt));
                                            } else {
                                                notificationManager.notifyAsUser(
                                                        null,
                                                        100000 + parseInt,
                                                        createNotification,
                                                        new UserHandle(parseInt));
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    Log.d(
                                            "knoxEnterpriseVpn",
                                            "Exception: " + Log.getStackTraceString(e));
                                }
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            } catch (Throwable th) {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                throw th;
                            }
                        }
                    }
                };
        this.mContext = context;
        this.mConnectivityManager =
                (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        this.mNms = iNetworkManagementService;
        this.mNetd = iNetd;
        this.mUserId = i;
        this.mLooper = looper;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mPackage = str;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                iNetworkManagementService.registerObserver(iNetworkManagementEventObserver);
                try {
                    i3 = context.getPackageManager().getPackageUidAsUser(str, i);
                } catch (PackageManager.NameNotFoundException unused) {
                    i3 = -1;
                }
                this.mOwnerUID = i3;
                if (i3 != -1 && i2 != 1) {
                    this.mNms.allowProtect(i3);
                }
            } catch (Exception e) {
                Log.e(
                        "knoxEnterpriseVpn",
                        "Unable to register mObserver or protecting socket failed : "
                                + Log.getStackTraceString(e));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            NetworkProvider networkProvider =
                    new NetworkProvider(context, looper, "VpnNetworkProvider:" + this.mUserId);
            this.mNetworkProvider = networkProvider;
            this.mConnectivityManager.registerNetworkProvider(networkProvider);
            this.mNetworkInfo = new NetworkInfo(17, 0, "VPN", "");
            this.mNetworkCapabilities =
                    new NetworkCapabilities.Builder()
                            .addTransportType(4)
                            .removeCapability(15)
                            .addCapability(28)
                            .setTransportInfo(new VpnTransportInfo(-1, (String) null))
                            .build();
            clearCallingIdentity = Binder.clearCallingIdentity();
            this.mContext.registerReceiverAsUser(
                    this.mLocaleChangedReceiver,
                    UserHandle.ALL,
                    new IntentFilter("android.intent.action.LOCALE_CHANGED"),
                    null,
                    null);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static List getDomainsByProfileName(String str) {
        if (DBG) {
            Log.d("knoxEnterpriseVpn", "getDomainsByProfileName()");
        }
        try {
            return getKnoxVpnService().getDomainsByProfileName(str);
        } catch (Exception e) {
            EnterpriseVpn$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception: "), "knoxEnterpriseVpn");
            return null;
        }
    }

    public static IKnoxVpnPolicy getKnoxVpnService() {
        return IKnoxVpnPolicy.Stub.asInterface(ServiceManager.getService("knox_vpn_policy"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native int jniCheck(String str);

    private native int jniCreateknoxvpn(int i, boolean z);

    private native String jniGetName(int i);

    private native void jniReset(String str);

    private native int jniSetAddresses(String str, String str2);

    /* JADX WARN: Type inference failed for: r11v0, types: [com.android.server.connectivity.EnterpriseVpn$1] */
    public final void agentConnect() {
        LinkProperties makeLinkProperties = makeLinkProperties();
        NetworkCapabilities.Builder builder =
                new NetworkCapabilities.Builder(this.mNetworkCapabilities);
        builder.addCapability(12);
        updateState(NetworkInfo.DetailedState.CONNECTING, "agentConnect");
        NetworkAgentConfig build = new NetworkAgentConfig.Builder().setBypassableVpn(false).build();
        int i = this.mOwnerUID;
        builder.setOwnerUid(i);
        builder.setAdministratorUids(new int[] {i});
        builder.setUids(this.knoxVpnUidRanges);
        builder.setTransportInfo(new VpnTransportInfo(4, (String) null));
        this.mNetworkCapabilities = builder.build();
        this.mNetworkAgent =
                new NetworkAgent(
                        this.mContext,
                        this.mLooper,
                        this.mNetworkCapabilities,
                        makeLinkProperties,
                        new NetworkScore.Builder().setLegacyInt(101).build(),
                        build,
                        this
                                .mNetworkProvider) { // from class:
                                                     // com.android.server.connectivity.EnterpriseVpn.1
                    public final void onNetworkCreated() {
                        int i2;
                        Log.d("knoxEnterpriseVpn", "knox vpn network is created");
                        EnterpriseVpn enterpriseVpn = EnterpriseVpn.this;
                        boolean z = EnterpriseVpn.DBG;
                        enterpriseVpn.getClass();
                        try {
                            int chainingEnabledForProfile =
                                    EnterpriseVpn.getKnoxVpnService()
                                            .getChainingEnabledForProfile(enterpriseVpn.mOwnerUID);
                            synchronized (enterpriseVpn) {
                                AnonymousClass1 anonymousClass1 = enterpriseVpn.mNetworkAgent;
                                i2 = 0;
                                if (anonymousClass1 != null) {
                                    Network network = anonymousClass1.getNetwork();
                                    if (network != null) {
                                        i2 = network.getNetId();
                                    }
                                }
                            }
                            int i3 = i2;
                            if (chainingEnabledForProfile == 1) {
                                enterpriseVpn.mNms.setNetworkInfo(
                                        i3, true, enterpriseVpn.mOwnerUID);
                            }
                            EnterpriseVpn.getKnoxVpnService()
                                    .addVpnUidRanges(
                                            enterpriseVpn.mProfileName,
                                            i3,
                                            enterpriseVpn.mInterface,
                                            enterpriseVpn.mAddress,
                                            enterpriseVpn.mV6Address);
                            enterpriseVpn.showNotification(enterpriseVpn.mProfileName, true);
                        } catch (Exception e) {
                            VpnManagerService$$ExternalSyntheticOutline0.m(
                                    e, new StringBuilder("Error occured "), "knoxEnterpriseVpn");
                        }
                    }

                    public final void onNetworkDestroyed() {
                        Log.d("knoxEnterpriseVpn", "knox vpn network is destroyed");
                    }

                    public final void onNetworkUnwanted() {}
                };
        Binder.withCleanCallingIdentity(
                new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // com.android.server.connectivity.EnterpriseVpn$$ExternalSyntheticLambda1
                    public final void runOrThrow() {
                        EnterpriseVpn enterpriseVpn = EnterpriseVpn.this;
                        boolean z = EnterpriseVpn.DBG;
                        enterpriseVpn.getClass();
                        try {
                            enterpriseVpn.mNetworkAgent.register();
                        } catch (Exception e) {
                            enterpriseVpn.mNetworkAgent = null;
                            throw e;
                        }
                    }
                });
        setUnderlyingNetworks((List) null);
        updateState(NetworkInfo.DetailedState.CONNECTED, "agentConnect");
    }

    public final void applyBlockingRulesToUidRange(boolean z) {
        try {
            this.mConnectivityManager.setRequireVpnForUids(z, this.knoxVpnUidRanges);
        } catch (RuntimeException e) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "Updating blocked=", " for UIDs ", z);
            m.append(Arrays.toString(((ArraySet) this.knoxVpnUidRanges).toArray()));
            m.append(" failed");
            Log.e("knoxEnterpriseVpn", m.toString(), e);
        }
    }

    public final void cleanupObjects() {
        if (this.mInterface != null) {
            Log.e(
                    "knoxEnterpriseVpn",
                    "Delay resetting the network capability and unregistering the observer since"
                        + " the interface is not removed yet");
            this.isVpnObjectRemoved = true;
            return;
        }
        resetUidListInNetworkCapabilities();
        try {
            this.mNms.unregisterObserver(this.mObserver);
        } catch (RemoteException unused) {
        }
        AnonymousClass3 anonymousClass3 = this.mLocaleChangedReceiver;
        if (anonymousClass3 != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            this.mContext.unregisterReceiver(anonymousClass3);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        this.mConnectivityManager.unregisterNetworkProvider(this.mNetworkProvider);
    }

    public final Notification createNotification(PendingIntent pendingIntent, boolean z) {
        String string = this.mContext.getString(R.string.permdesc_postNotification);
        return new Notification.Builder(this.mContext, SystemNotificationChannels.VPN)
                .setSmallIcon(17304846)
                .setContentTitle(string)
                .setContentText(this.mContext.getString(R.string.permdesc_persistentActivity))
                .setContentIntent(pendingIntent)
                .setDefaults(0)
                .setOngoing(z)
                .setPriority(2)
                .getNotification();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [int] */
    /* JADX WARN: Type inference failed for: r5v4, types: [long] */
    public final synchronized ParcelFileDescriptor establish(VpnConfig vpnConfig) {
        long callingUid = Binder.getCallingUid();
        if (callingUid != this.mOwnerUID) {
            Log.e(
                    "knoxEnterpriseVpn",
                    "establish failed due to caller mismatch "
                            + this.mOwnerUID
                            + Binder.getCallingUid());
            return null;
        }
        try {
            Intent intent = new Intent("android.net.VpnService");
            intent.setClassName(this.mPackage, vpnConfig.user);
            callingUid = Binder.clearCallingIdentity();
            try {
                if (this.mUserManager.getUserInfo(this.mUserId).isRestricted()
                        || this.mUserManager.hasUserRestriction("no_config_vpn")) {
                    throw new SecurityException("Restricted users cannot establish VPNs");
                }
                ResolveInfo resolveService =
                        AppGlobals.getPackageManager()
                                .resolveService(intent, (String) null, 0L, this.mUserId);
                if (resolveService == null) {
                    throw new SecurityException("Cannot find " + vpnConfig.user);
                }
                if (!"android.permission.BIND_VPN_SERVICE"
                        .equals(resolveService.serviceInfo.permission)) {
                    throw new SecurityException(
                            vpnConfig.user
                                    + " does not require android.permission.BIND_VPN_SERVICE");
                }
                Binder.restoreCallingIdentity(callingUid);
                this.mProfileName = vpnConfig.session;
                ParcelFileDescriptor adoptFd =
                        ParcelFileDescriptor.adoptFd(
                                jniCreateknoxvpn(vpnConfig.mtu, this.isMetaDataEnabled));
                try {
                    try {
                        IoUtils.setBlocking(adoptFd.getFileDescriptor(), vpnConfig.blocking);
                        String jniGetName = jniGetName(adoptFd.getFd());
                        StringBuilder sb = new StringBuilder();
                        for (LinkAddress linkAddress : vpnConfig.addresses) {
                            sb.append(" ");
                            sb.append(linkAddress);
                        }
                        if (jniSetAddresses(jniGetName, sb.toString()) < 1) {
                            throw new IllegalArgumentException(
                                    "At least one address must be specified");
                        }
                        Connection connection = new Connection();
                        if (!this.mContext.bindServiceAsUser(
                                intent, connection, 67108865, new UserHandle(this.mUserId))) {
                            throw new IllegalStateException("Cannot bind " + vpnConfig.user);
                        }
                        Connection connection2 = this.mConnection;
                        if (connection2 != null) {
                            this.mContext.unbindService(connection2);
                        }
                        String str = this.mInterface;
                        if (str != null && !str.equals(jniGetName)) {
                            jniReset(this.mInterface);
                        }
                        this.mConnection = connection;
                        this.mInterface = jniGetName;
                        vpnConfig.user = this.mPackage;
                        vpnConfig.interfaze = jniGetName;
                        vpnConfig.startTime = SystemClock.elapsedRealtime();
                        this.mConfig = vpnConfig;
                        this.mNetworkAgent = null;
                        updateState(NetworkInfo.DetailedState.CONNECTING, "establish");
                        agentConnect();
                        Log.i(
                                "knoxEnterpriseVpn",
                                "Established by " + vpnConfig.user + " on " + this.mInterface);
                        return adoptFd;
                    } catch (RuntimeException e) {
                        Log.e(
                                "knoxEnterpriseVpn",
                                "Exception in creating tun interface" + Log.getStackTraceString(e));
                        IoUtils.closeQuietly(adoptFd);
                        updateState(NetworkInfo.DetailedState.DISCONNECTED, "agentDisconnect");
                        throw e;
                    }
                } catch (IOException e2) {
                    throw new IllegalStateException(
                            "Cannot set tunnel's fd as blocking=" + vpnConfig.blocking, e2);
                }
            } catch (RemoteException unused) {
                throw new SecurityException("Cannot find " + vpnConfig.user);
            }
        } finally {
            Binder.restoreCallingIdentity(callingUid);
        }
    }

    public final void hideNotification(int i) {
        if (DBG) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    i, "hideNotification > domain : ", "knoxEnterpriseVpn");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                NotificationManager notificationManager =
                        (NotificationManager) this.mContext.getSystemService("notification");
                if (getKnoxVpnService().getNotificationDismissibleFlagInternal(i) == 1) {
                    notificationManager.cancelAsUser(
                            "Knox_Notification", 100000, new UserHandle(i));
                } else {
                    notificationManager.cancelAsUser(null, 100000 + i, new UserHandle(i));
                }
            } catch (Exception e) {
                Log.d("knoxEnterpriseVpn", "Exception: " + Log.getStackTraceString(e));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean isCallerEstablishedOwnerLocked() {
        return (this.mNetworkAgent == null
                        || this.mInterface == null
                        || Binder.getCallingUid() != this.mOwnerUID)
                ? false
                : true;
    }

    public final LinkProperties makeLinkProperties() {
        LinkProperties linkProperties = new LinkProperties();
        linkProperties.setInterfaceName(this.mInterface);
        List<LinkAddress> list = this.mConfig.addresses;
        if (list != null) {
            for (LinkAddress linkAddress : list) {
                if (linkAddress.getAddress() instanceof Inet4Address) {
                    this.mAddress = linkAddress.getAddress().getHostAddress();
                    Log.d(
                            "knoxEnterpriseVpn",
                            "The ipv4 address added to the knox vpn interface is " + this.mAddress);
                    linkProperties.addRoute(
                            new RouteInfo(
                                    new IpPrefix(NetworkStackConstants.IPV4_ADDR_ANY, 0),
                                    null,
                                    null,
                                    1));
                }
                if (linkAddress.getAddress() instanceof Inet6Address) {
                    this.mV6Address = linkAddress.getAddress().getHostAddress();
                    Log.d(
                            "knoxEnterpriseVpn",
                            "The ipv6 address added to the knox vpn interface is "
                                    + this.mV6Address);
                    linkProperties.addRoute(
                            new RouteInfo(
                                    new IpPrefix(NetworkStackConstants.IPV6_ADDR_ANY, 0),
                                    null,
                                    null,
                                    1));
                }
                linkProperties.addLinkAddress(linkAddress);
            }
        }
        List list2 = this.mConfig.dnsServers;
        if (list2 != null) {
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                linkProperties.addDnsServer(InetAddresses.parseNumericAddress((String) it.next()));
            }
        }
        ProxyInfo proxyInfo = this.mHttpProxyInfo;
        if (proxyInfo != null) {
            linkProperties.setHttpProxy(proxyInfo);
        }
        StringBuilder sb = new StringBuilder();
        List list3 = this.mConfig.searchDomains;
        if (list3 != null) {
            Iterator it2 = list3.iterator();
            while (it2.hasNext()) {
                sb.append((String) it2.next());
                sb.append(' ');
            }
        }
        linkProperties.setDomains(sb.toString().trim());
        int i = this.mConfig.mtu;
        if (i > 0) {
            linkProperties.setMtu(i);
        }
        return linkProperties;
    }

    public final void refreshNotification(int i, boolean z) {
        if (DBG) {
            Log.d(
                    "knoxEnterpriseVpn",
                    "refreshNotification > domain : " + i + ", optionAdd : " + z);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                NotificationManager notificationManager =
                        (NotificationManager) this.mContext.getSystemService("notification");
                boolean z2 = getKnoxVpnService().getNotificationDismissibleFlagInternal(i) != 0;
                Notification createNotification =
                        createNotification(
                                VpnConfig.getIntentForStatusPanelEnterpriseVpnAsUser(
                                        this.mContext, this.mConfig, z, i),
                                z2);
                if (z2) {
                    notificationManager.notifyAsUser(
                            "Knox_Notification", 100000, createNotification, new UserHandle(i));
                } else {
                    notificationManager.notifyAsUser(
                            null, 100000 + i, createNotification, new UserHandle(i));
                }
            } catch (Exception e) {
                Log.d("knoxEnterpriseVpn", "Exception: " + Log.getStackTraceString(e));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void resetUidListInNetworkCapabilities() {
        applyBlockingRulesToUidRange(false);
        ((ArraySet) this.knoxVpnUidRanges).clear();
        NetworkCapabilities build =
                new NetworkCapabilities.Builder(this.mNetworkCapabilities)
                        .setUids((Set) null)
                        .build();
        this.mNetworkCapabilities = build;
        AnonymousClass1 anonymousClass1 = this.mNetworkAgent;
        if (anonymousClass1 != null) {
            anonymousClass1.sendNetworkCapabilities(build);
        }
    }

    public final void showNotification(String str, boolean z) {
        int i = this.mUserId;
        boolean z2 = DBG;
        if (z2) {
            Log.d(
                    "knoxEnterpriseVpn",
                    "showNotification > profileName : " + str + " , optionAdd : " + z);
        }
        NotificationManager notificationManager =
                (NotificationManager) this.mContext.getSystemService("notification");
        VpnConfig vpnConfig = this.mConfig;
        if (vpnConfig == null || notificationManager == null) {
            Log.d("knoxEnterpriseVpn", "Error : can not init");
            return;
        }
        vpnConfig.startTime = SystemClock.elapsedRealtime();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List domainsByProfileName = getDomainsByProfileName(vpnConfig.session);
            int i2 = 100000;
            if (domainsByProfileName == null || domainsByProfileName.size() <= 0) {
                boolean z3 = getKnoxVpnService().getNotificationDismissibleFlagInternal(i) != 0;
                Notification createNotification =
                        createNotification(
                                VpnConfig.getIntentForStatusPanelEnterpriseVpnAsUser(
                                        this.mContext, vpnConfig, false, i),
                                z3);
                if (z3) {
                    notificationManager.notifyAsUser(
                            "Knox_Notification", 100000, createNotification, new UserHandle(i));
                } else {
                    notificationManager.notifyAsUser(
                            null, i + 100000, createNotification, new UserHandle(i));
                }
                if (z2) {
                    Log.d(
                            "knoxEnterpriseVpn",
                            "showNotification > keyicon notified to user for non installed apps "
                                    + i);
                }
            } else {
                Iterator it = domainsByProfileName.iterator();
                while (it.hasNext()) {
                    int parseInt = Integer.parseInt((String) it.next());
                    boolean z4 =
                            getKnoxVpnService().getNotificationDismissibleFlagInternal(parseInt)
                                    != 0;
                    Notification createNotification2 =
                            createNotification(
                                    VpnConfig.getIntentForStatusPanelEnterpriseVpnAsUser(
                                            this.mContext, vpnConfig, z, parseInt),
                                    z4);
                    if (z4) {
                        notificationManager.notifyAsUser(
                                "Knox_Notification",
                                i2,
                                createNotification2,
                                new UserHandle(parseInt));
                    } else {
                        notificationManager.notifyAsUser(
                                null, parseInt + i2, createNotification2, new UserHandle(parseInt));
                    }
                    if (z2) {
                        Log.d(
                                "knoxEnterpriseVpn",
                                "showNotification > keyicon notified to user " + parseInt);
                    }
                    i2 = 100000;
                }
            }
        } catch (Exception e) {
            EnterpriseVpn$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception: "), "knoxEnterpriseVpn");
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void updateNotificationIcon(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                NotificationManager notificationManager =
                        (NotificationManager) this.mContext.getSystemService("notification");
                boolean z = getKnoxVpnService().getNotificationDismissibleFlagInternal(i) != 0;
                Notification createNotification =
                        createNotification(
                                VpnConfig.getIntentForStatusPanelRefreshAsUser(this.mContext, i),
                                z);
                if (z) {
                    notificationManager.cancelAsUser(null, i + 100000, new UserHandle(i));
                    notificationManager.notifyAsUser(
                            "Knox_Notification", 100000, createNotification, new UserHandle(i));
                } else {
                    notificationManager.cancelAsUser(
                            "Knox_Notification", 100000, new UserHandle(i));
                    notificationManager.notifyAsUser(
                            null, 100000 + i, createNotification, new UserHandle(i));
                }
            } catch (Exception e) {
                Log.d("knoxEnterpriseVpn", "Exception: " + Log.getStackTraceString(e));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void updateState(NetworkInfo.DetailedState detailedState, String str) {
        Log.d("knoxEnterpriseVpn", "setting state=" + detailedState + ", reason=" + str);
        LegacyVpnInfo.stateFromNetworkInfo(detailedState);
        this.mNetworkInfo.setDetailedState(detailedState, str, null);
        int i =
                AnonymousClass4.$SwitchMap$android$net$NetworkInfo$DetailedState[
                        detailedState.ordinal()];
        if (i == 1) {
            AnonymousClass1 anonymousClass1 = this.mNetworkAgent;
            if (anonymousClass1 != null) {
                anonymousClass1.markConnected();
                return;
            }
            return;
        }
        if (i == 2 || i == 3) {
            AnonymousClass1 anonymousClass12 = this.mNetworkAgent;
            if (anonymousClass12 != null) {
                anonymousClass12.unregister();
                this.mNetworkAgent = null;
                return;
            }
            return;
        }
        if (i == 4) {
            if (this.mNetworkAgent != null) {
                throw new IllegalStateException(
                        "VPN can only go to CONNECTING state when the agent is null.");
            }
        } else {
            if (i == 5) {
                return;
            }
            throw new IllegalArgumentException("Illegal state argument " + detailedState);
        }
    }

    public final void updateUidListInNetworkCapabilities() {
        Iterator it = ((ArraySet) this.knoxVpnUidRanges).iterator();
        while (it.hasNext()) {
            Range range = (Range) it.next();
            StringBuilder sb = new StringBuilder("uidRange going to be updated is ");
            sb.append(range.getLower());
            sb.append(" ");
            sb.append(range.getUpper());
            sb.append(" for profile ");
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    sb, this.mProfileName, "knoxEnterpriseVpn");
        }
        NetworkCapabilities build =
                new NetworkCapabilities.Builder(this.mNetworkCapabilities)
                        .setUids(this.knoxVpnUidRanges)
                        .build();
        this.mNetworkCapabilities = build;
        AnonymousClass1 anonymousClass1 = this.mNetworkAgent;
        if (anonymousClass1 != null) {
            anonymousClass1.sendNetworkCapabilities(build);
        }
    }

    public final void updateUidRangesToPerAppVpn(Set set, boolean z) {
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "updateUidRangesToPerAppVpn ", "knoxEnterpriseVpn", z);
        Iterator it = ((HashSet) set).iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            num.getClass();
            if (z) {
                ((ArraySet) this.knoxVpnUidRanges).add(new Range(num, num));
            } else {
                ((ArraySet) this.knoxVpnUidRanges).remove(new Range(num, num));
            }
        }
        updateUidListInNetworkCapabilities();
    }

    public final void updateUidRangesToUserVpn(int i, boolean z) {
        Log.d("knoxEnterpriseVpn", "updateUidRangesToUserVpn " + z + " " + i);
        int i2 = i * 100000;
        int i3 = i2 + 1;
        int i4 = i2 + 99999;
        if (z) {
            ((ArraySet) this.knoxVpnUidRanges)
                    .add(new Range(Integer.valueOf(i3), Integer.valueOf(i4)));
        } else {
            ((ArraySet) this.knoxVpnUidRanges)
                    .remove(new Range(Integer.valueOf(i3), Integer.valueOf(i4)));
        }
        updateUidListInNetworkCapabilities();
    }

    public final void updateUidRangesToUserVpnWithBlackList(int i, Set set) {
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                i, "updateUidRangesToUserVpnWithBlackList ", "knoxEnterpriseVpn");
        if (((HashSet) set).size() <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList(set);
        Collections.sort(arrayList);
        Range range =
                new Range(Integer.valueOf(i * 100000), Integer.valueOf(((i + 1) * 100000) - 1));
        int intValue = ((Integer) range.getLower()).intValue() + 1;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            int intValue2 = ((Integer) it.next()).intValue();
            if (intValue2 == intValue) {
                intValue++;
            } else {
                ((ArraySet) this.knoxVpnUidRanges)
                        .add(new Range(Integer.valueOf(intValue), Integer.valueOf(intValue2 - 1)));
                intValue = intValue2 + 1;
            }
        }
        if (intValue <= ((Integer) range.getUpper()).intValue()) {
            ((ArraySet) this.knoxVpnUidRanges)
                    .add(new Range(Integer.valueOf(intValue), (Integer) range.getUpper()));
        }
        updateUidListInNetworkCapabilities();
    }
}
