package com.android.server.enterprise.filter;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.IActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.ServiceInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.ConnectivityManager;
import android.net.INetd;
import android.net.LinkProperties;
import android.net.Network;
import android.net.UidRangeParcel;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Log;

import com.android.internal.net.IOemNetd;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapterlayer.DnsResolverAdapter;
import com.android.server.enterprise.adapterlayer.DnsResolverAdapter$$ExternalSyntheticLambda2;
import com.android.server.enterprise.firewall.EnforceDnsManager;
import com.android.server.enterprise.firewall.KnoxNetIdManager;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.app.networkfilter.INetworkFilterProxy;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KnoxNetworkFilterService extends IKnoxNetworkFilterService.Stub
        implements EnterpriseServiceCallback {
    public static final boolean DBG = Debug.semIsProductDev();
    public final ConnectivityManager mCm;
    public final Context mContext;
    public final HashMap mDefaultDnsConnectionList;
    public final HashMap mDefaultDnsProxyInterfaceList;
    public final HashMap mDefaultTcpConnectionList;
    public final HashMap mDefaultTcpProxyInterfaceList;
    public final HashMap mDefaultUdpConnectionList;
    public final HashMap mDefaultUdpProxyInterfaceList;
    public EnforceDnsManager mDnsManager;
    public final DnsResolverAdapter mDnsResolverAdapter;
    public final EnterpriseDeviceManager mEdm;
    public final KnoxNwFilterHandler mHandler;
    public final HandlerThread mHandlerThread;
    public final KnoxNetworkFilterFirewall mKnoxNwFilterFw;
    public final KnoxNetworkFilterHelper mKnoxNwFilterHelper;
    public final KnoxNetworkFilterValidation mKnoxNwFilterValidation;
    public final List mLocalProxyStatus;
    public final KnoxNetIdManager mNetIdManager;
    public INetd mNetdService;
    public final HashMap mNetworkCallbackList;
    public int mNwFilterProxyAppId;
    public IOemNetd mOemNetdService;
    public NwFilterReceiver mReceiver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DefaultDnsConnection implements ServiceConnection {
        public final /* synthetic */ int $r8$classId;
        public CountDownLatch countDownLatch = null;
        public final /* synthetic */ KnoxNetworkFilterService this$0;
        public final int userId;

        public /* synthetic */ DefaultDnsConnection(
                KnoxNetworkFilterService knoxNetworkFilterService, int i, int i2) {
            this.$r8$classId = i2;
            this.this$0 = knoxNetworkFilterService;
            this.userId = i;
        }

        private final synchronized void
                onServiceConnected$com$android$server$enterprise$filter$KnoxNetworkFilterService$DefaultTcpConnection(
                        ComponentName componentName, IBinder iBinder) {
            try {
                Log.d(
                        "knoxNwFilter-KnoxNetworkFilterService",
                        "DefaultTcpConnection:onServiceConnected is reached ");
                ((ArrayList) this.this$0.mLocalProxyStatus)
                        .add(
                                "DefaultTcpConnection:onServiceConnected  for user "
                                        + this.userId
                                        + " "
                                        + new Date(System.currentTimeMillis()));
                INetworkFilterProxy asInterface = INetworkFilterProxy.Stub.asInterface(iBinder);
                this.this$0.mDefaultTcpProxyInterfaceList.put(
                        Integer.valueOf(this.userId), asInterface);
                CountDownLatch countDownLatch = this.countDownLatch;
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
                KnoxNetworkFilterHelper knoxNetworkFilterHelper = this.this$0.mKnoxNwFilterHelper;
                int i = this.userId;
                knoxNetworkFilterHelper.getClass();
                String profilebyUserId = KnoxNetworkFilterHelper.getProfilebyUserId(i);
                if (profilebyUserId == null) {
                    return;
                }
                this.this$0.mKnoxNwFilterHelper.getClass();
                KnoxNetworkFilterHelper.getConfiguredProtocols(profilebyUserId);
                KnoxNetworkFilterProfileInfo profileEntry =
                        KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId);
                if (profileEntry == null) {
                    return;
                }
                this.this$0.mKnoxNwFilterHelper.getClass();
                String configuredDestIpRange =
                        KnoxNetworkFilterHelper.getConfiguredDestIpRange(2, profilebyUserId);
                this.this$0.mKnoxNwFilterHelper.getClass();
                String configuredDestIpRange2 =
                        KnoxNetworkFilterHelper.getConfiguredDestIpRange(10, profilebyUserId);
                int uid = UserHandle.getUid(this.userId, this.this$0.mNwFilterProxyAppId);
                try {
                    this.this$0.mKnoxNwFilterHelper.getClass();
                    asInterface.setConfig(
                            profilebyUserId,
                            KnoxNetworkFilterHelper.getRulesConfig(profilebyUserId));
                    this.this$0.mKnoxNwFilterHelper.getClass();
                    asInterface.registerRemoteProxyAddr(
                            profilebyUserId,
                            KnoxNetworkFilterHelper.retrieveListenersFromCache(profilebyUserId));
                    List listener = asInterface.getListener(profilebyUserId);
                    this.this$0.mKnoxNwFilterFw.getClass();
                    KnoxNetworkFilterFirewall.denyUnAuthorizedPackets(uid, "tcp", listener);
                    this.this$0.mKnoxNwFilterHelper.getClass();
                    if (KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId).mState == 3) {
                        if (profileEntry.mV4TcpConfigured) {
                            int startProxyServer = asInterface.startProxyServer();
                            KnoxNetworkFilterFirewall knoxNetworkFilterFirewall =
                                    this.this$0.mKnoxNwFilterFw;
                            int i2 = this.userId;
                            knoxNetworkFilterFirewall.getClass();
                            KnoxNetworkFilterFirewall.redirectAppGeneratedTcpConn(
                                    i2, startProxyServer, configuredDestIpRange);
                            this.this$0
                                    .getOemNetdService()
                                    .enablePortInfoEntries(this.userId, 2, 6, true);
                        }
                        if (profileEntry.mV6TcpConfigured) {
                            int startV6ProxyServer = asInterface.startV6ProxyServer();
                            KnoxNetworkFilterFirewall knoxNetworkFilterFirewall2 =
                                    this.this$0.mKnoxNwFilterFw;
                            int i3 = this.userId;
                            knoxNetworkFilterFirewall2.getClass();
                            KnoxNetworkFilterFirewall.redirectAppGeneratedV6TcpConn(
                                    i3, startV6ProxyServer, configuredDestIpRange2);
                            this.this$0
                                    .getOemNetdService()
                                    .enablePortInfoEntries(this.userId, 10, 6, true);
                        }
                        int startHttpProxyServer = asInterface.startHttpProxyServer();
                        String[] browserAppList = asInterface.getBrowserAppList();
                        this.this$0
                                .getOemNetdService()
                                .setHttpProxyPort(this.userId, startHttpProxyServer);
                        this.this$0.mKnoxNwFilterHelper.applyHttpProxyConfiguration(
                                profilebyUserId, browserAppList, true, this.userId);
                        for (String str : browserAppList) {
                            KnoxNetworkFilterHelper knoxNetworkFilterHelper2 =
                                    this.this$0.mKnoxNwFilterHelper;
                            int i4 = this.userId;
                            knoxNetworkFilterHelper2.getClass();
                            int uIDForPackage = KnoxNetworkFilterHelper.getUIDForPackage(i4, str);
                            if (uIDForPackage > 0) {
                                this.this$0.mKnoxNwFilterFw.getClass();
                                KnoxNetworkFilterFirewall.applyTcpRedirectRulesForCaptivePortal(
                                        uIDForPackage, startHttpProxyServer);
                            }
                        }
                    }
                } catch (RemoteException | NullPointerException unused) {
                }
            } catch (Throwable th) {
                throw th;
            }
        }

        @Override // android.content.ServiceConnection
        public final synchronized void onBindingDied(ComponentName componentName) {
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this) {
                        try {
                            Log.d(
                                    "knoxNwFilter-KnoxNetworkFilterService",
                                    "DefaultDnsConnection:onBindingDied is reached ");
                            ((ArrayList) this.this$0.mLocalProxyStatus)
                                    .add(
                                            "DefaultDnsConnection:onBindingDied  for user "
                                                    + this.userId
                                                    + " "
                                                    + new Date(System.currentTimeMillis()));
                            if (this.this$0.mDefaultDnsProxyInterfaceList.containsKey(
                                    Integer.valueOf(this.userId))) {
                                this.this$0.mDefaultDnsProxyInterfaceList.put(
                                        Integer.valueOf(this.userId), null);
                            }
                            if (this.this$0.mDefaultDnsConnectionList.containsKey(
                                    Integer.valueOf(this.userId))) {
                                KnoxNetworkFilterService knoxNetworkFilterService = this.this$0;
                                knoxNetworkFilterService.mContext.unbindService(
                                        (ServiceConnection)
                                                knoxNetworkFilterService.mDefaultDnsConnectionList
                                                        .get(Integer.valueOf(this.userId)));
                                this.this$0.mDefaultDnsConnectionList.remove(
                                        Integer.valueOf(this.userId));
                            }
                            DefaultDnsConnection defaultDnsConnection =
                                    new DefaultDnsConnection(this.this$0, this.userId, 0);
                            Intent intent = new Intent();
                            intent.setClassName(
                                    "com.samsung.android.knox.app.networkfilter",
                                    "com.samsung.android.knox.app.networkfilter.dns.DefaultDnsProxyService");
                            if (this.this$0.mContext.bindServiceAsUser(
                                    intent,
                                    defaultDnsConnection,
                                    1073741829,
                                    new UserHandle(this.userId))) {
                                this.this$0.mDefaultDnsConnectionList.put(
                                        Integer.valueOf(this.userId), defaultDnsConnection);
                            } else {
                                Log.e(
                                        "knoxNwFilter-KnoxNetworkFilterService",
                                        "failed to bind dns process after onBinding died event,"
                                            + " clearing up the rules applied");
                                KnoxNetworkFilterService.m511$$Nest$mhandleBindingDiedEvent(
                                        this.this$0, this.userId);
                            }
                        } finally {
                        }
                    }
                    return;
                case 1:
                    synchronized (this) {
                        try {
                            Log.d(
                                    "knoxNwFilter-KnoxNetworkFilterService",
                                    "DefaultTcpConnection:onBindingDied is reached ");
                            ((ArrayList) this.this$0.mLocalProxyStatus)
                                    .add(
                                            "DefaultTcpConnection:onBindingDied  for user "
                                                    + this.userId
                                                    + " "
                                                    + new Date(System.currentTimeMillis()));
                            if (this.this$0.mDefaultTcpProxyInterfaceList.containsKey(
                                    Integer.valueOf(this.userId))) {
                                this.this$0.mDefaultTcpProxyInterfaceList.put(
                                        Integer.valueOf(this.userId), null);
                            }
                            if (this.this$0.mDefaultTcpConnectionList.containsKey(
                                    Integer.valueOf(this.userId))) {
                                KnoxNetworkFilterService knoxNetworkFilterService2 = this.this$0;
                                knoxNetworkFilterService2.mContext.unbindService(
                                        (ServiceConnection)
                                                knoxNetworkFilterService2.mDefaultTcpConnectionList
                                                        .get(Integer.valueOf(this.userId)));
                                this.this$0.mDefaultTcpConnectionList.remove(
                                        Integer.valueOf(this.userId));
                            }
                            DefaultDnsConnection defaultDnsConnection2 =
                                    new DefaultDnsConnection(this.this$0, this.userId, 1);
                            Intent intent2 = new Intent();
                            intent2.setClassName(
                                    "com.samsung.android.knox.app.networkfilter",
                                    "com.samsung.android.knox.app.networkfilter.tcp.DefaultTcpProxyService");
                            if (this.this$0.mContext.bindServiceAsUser(
                                    intent2,
                                    defaultDnsConnection2,
                                    1073741829,
                                    new UserHandle(this.userId))) {
                                this.this$0.mDefaultTcpConnectionList.put(
                                        Integer.valueOf(this.userId), defaultDnsConnection2);
                            } else {
                                Log.e(
                                        "knoxNwFilter-KnoxNetworkFilterService",
                                        "failed to bind tcp process after onBinding died event");
                            }
                        } finally {
                        }
                    }
                    return;
                default:
                    synchronized (this) {
                        try {
                            Log.d(
                                    "knoxNwFilter-KnoxNetworkFilterService",
                                    "DefaultUdpConnection:onBindingDied is reached ");
                            ((ArrayList) this.this$0.mLocalProxyStatus)
                                    .add(
                                            "DefaultUdpConnection:onBindingDied  for user "
                                                    + this.userId
                                                    + " "
                                                    + new Date(System.currentTimeMillis()));
                            if (this.this$0.mDefaultUdpProxyInterfaceList.containsKey(
                                    Integer.valueOf(this.userId))) {
                                this.this$0.mDefaultUdpProxyInterfaceList.put(
                                        Integer.valueOf(this.userId), null);
                            }
                            if (this.this$0.mDefaultUdpConnectionList.containsKey(
                                    Integer.valueOf(this.userId))) {
                                KnoxNetworkFilterService knoxNetworkFilterService3 = this.this$0;
                                knoxNetworkFilterService3.mContext.unbindService(
                                        (ServiceConnection)
                                                knoxNetworkFilterService3.mDefaultUdpConnectionList
                                                        .get(Integer.valueOf(this.userId)));
                                this.this$0.mDefaultUdpConnectionList.remove(
                                        Integer.valueOf(this.userId));
                            }
                            DefaultDnsConnection defaultDnsConnection3 =
                                    new DefaultDnsConnection(this.this$0, this.userId, 2);
                            Intent intent3 = new Intent();
                            intent3.setClassName(
                                    "com.samsung.android.knox.app.networkfilter",
                                    "com.samsung.android.knox.app.networkfilter.udp.DefaultUdpProxyService");
                            if (this.this$0.mContext.bindServiceAsUser(
                                    intent3,
                                    defaultDnsConnection3,
                                    1073741829,
                                    new UserHandle(this.userId))) {
                                this.this$0.mDefaultUdpConnectionList.put(
                                        Integer.valueOf(this.userId), defaultDnsConnection3);
                            } else {
                                Log.e(
                                        "knoxNwFilter-KnoxNetworkFilterService",
                                        "failed to bind udp process after onBinding died event");
                            }
                        } finally {
                        }
                    }
                    return;
            }
        }

        @Override // android.content.ServiceConnection
        public final synchronized void onServiceConnected(
                ComponentName componentName, IBinder iBinder) {
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this) {
                        try {
                            Log.d(
                                    "knoxNwFilter-KnoxNetworkFilterService",
                                    "DefaultDnsConnection:onServiceConnected is reached ");
                            ((ArrayList) this.this$0.mLocalProxyStatus)
                                    .add(
                                            "DefaultDnsConnection:onServiceConnected  for user "
                                                    + this.userId
                                                    + " "
                                                    + new Date(System.currentTimeMillis()));
                            INetworkFilterProxy asInterface =
                                    INetworkFilterProxy.Stub.asInterface(iBinder);
                            this.this$0.mDefaultDnsProxyInterfaceList.put(
                                    Integer.valueOf(this.userId), asInterface);
                            CountDownLatch countDownLatch = this.countDownLatch;
                            if (countDownLatch != null) {
                                countDownLatch.countDown();
                            }
                            KnoxNetworkFilterHelper knoxNetworkFilterHelper =
                                    this.this$0.mKnoxNwFilterHelper;
                            int i = this.userId;
                            knoxNetworkFilterHelper.getClass();
                            String profilebyUserId = KnoxNetworkFilterHelper.getProfilebyUserId(i);
                            if (profilebyUserId == null) {
                                return;
                            }
                            this.this$0.mKnoxNwFilterHelper.getClass();
                            KnoxNetworkFilterHelper.getConfiguredProtocols(profilebyUserId);
                            KnoxNetworkFilterProfileInfo profileEntry =
                                    KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId);
                            if (profileEntry == null) {
                                return;
                            }
                            try {
                                this.this$0.mKnoxNwFilterHelper.getClass();
                                asInterface.setConfig(
                                        profilebyUserId,
                                        KnoxNetworkFilterHelper.getRulesConfig(profilebyUserId));
                                this.this$0.mKnoxNwFilterHelper.getClass();
                                asInterface.registerRemoteProxyAddr(
                                        profilebyUserId,
                                        KnoxNetworkFilterHelper.retrieveListenersFromCache(
                                                profilebyUserId));
                                List listener = asInterface.getListener(profilebyUserId);
                                int uid =
                                        UserHandle.getUid(
                                                this.userId, this.this$0.mNwFilterProxyAppId);
                                this.this$0.mKnoxNwFilterFw.getClass();
                                KnoxNetworkFilterFirewall.denyUnAuthorizedPackets(
                                        uid, "dns", listener);
                                this.this$0.mKnoxNwFilterHelper.getClass();
                                if (KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId)
                                                        .mState
                                                == 3
                                        && profileEntry.mV4DnsConfigured) {
                                    int startProxyServer = asInterface.startProxyServer();
                                    KnoxNetworkFilterFirewall knoxNetworkFilterFirewall =
                                            this.this$0.mKnoxNwFilterFw;
                                    int i2 = this.userId;
                                    knoxNetworkFilterFirewall.getClass();
                                    KnoxNetworkFilterFirewall.redirectDnsQuery(
                                            i2, startProxyServer);
                                }
                            } catch (RemoteException | NullPointerException unused) {
                            }
                            return;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                case 1:
                    onServiceConnected$com$android$server$enterprise$filter$KnoxNetworkFilterService$DefaultTcpConnection(
                            componentName, iBinder);
                    return;
                default:
                    synchronized (this) {
                        try {
                            Log.d(
                                    "knoxNwFilter-KnoxNetworkFilterService",
                                    "DefaultUdpConnection:onServiceConnected is reached ");
                            ((ArrayList) this.this$0.mLocalProxyStatus)
                                    .add(
                                            "DefaultUdpConnection:onServiceConnected  for user "
                                                    + this.userId
                                                    + " "
                                                    + new Date(System.currentTimeMillis()));
                            INetworkFilterProxy asInterface2 =
                                    INetworkFilterProxy.Stub.asInterface(iBinder);
                            this.this$0.mDefaultUdpProxyInterfaceList.put(
                                    Integer.valueOf(this.userId), asInterface2);
                            CountDownLatch countDownLatch2 = this.countDownLatch;
                            if (countDownLatch2 != null) {
                                countDownLatch2.countDown();
                            }
                            KnoxNetworkFilterHelper knoxNetworkFilterHelper2 =
                                    this.this$0.mKnoxNwFilterHelper;
                            int i3 = this.userId;
                            knoxNetworkFilterHelper2.getClass();
                            String profilebyUserId2 =
                                    KnoxNetworkFilterHelper.getProfilebyUserId(i3);
                            if (profilebyUserId2 == null) {
                                return;
                            }
                            this.this$0.mKnoxNwFilterHelper.getClass();
                            KnoxNetworkFilterHelper.getConfiguredProtocols(profilebyUserId2);
                            KnoxNetworkFilterProfileInfo profileEntry2 =
                                    KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId2);
                            if (profileEntry2 == null) {
                                return;
                            }
                            this.this$0.mKnoxNwFilterHelper.getClass();
                            String configuredDestIpRange =
                                    KnoxNetworkFilterHelper.getConfiguredDestIpRange(
                                            2, profilebyUserId2);
                            this.this$0.mKnoxNwFilterHelper.getClass();
                            String configuredDestIpRange2 =
                                    KnoxNetworkFilterHelper.getConfiguredDestIpRange(
                                            10, profilebyUserId2);
                            int uid2 =
                                    UserHandle.getUid(this.userId, this.this$0.mNwFilterProxyAppId);
                            try {
                                this.this$0.mKnoxNwFilterHelper.getClass();
                                asInterface2.setConfig(
                                        profilebyUserId2,
                                        KnoxNetworkFilterHelper.getRulesConfig(profilebyUserId2));
                                this.this$0.mKnoxNwFilterHelper.getClass();
                                asInterface2.registerRemoteProxyAddr(
                                        profilebyUserId2,
                                        KnoxNetworkFilterHelper.retrieveListenersFromCache(
                                                profilebyUserId2));
                                List listener2 = asInterface2.getListener(profilebyUserId2);
                                this.this$0.mKnoxNwFilterFw.getClass();
                                KnoxNetworkFilterFirewall.denyUnAuthorizedPackets(
                                        uid2, "udp", listener2);
                                this.this$0.mKnoxNwFilterHelper.getClass();
                                if (KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId2)
                                                .mState
                                        == 3) {
                                    if (profileEntry2.mV4UdpConfigured) {
                                        int startProxyServer2 = asInterface2.startProxyServer();
                                        KnoxNetworkFilterFirewall knoxNetworkFilterFirewall2 =
                                                this.this$0.mKnoxNwFilterFw;
                                        int i4 = this.userId;
                                        knoxNetworkFilterFirewall2.getClass();
                                        KnoxNetworkFilterFirewall.redirectAppGeneratedUdpConn(
                                                i4, startProxyServer2, configuredDestIpRange);
                                    }
                                    if (profileEntry2.mV6UdpConfigured) {
                                        int startV6ProxyServer = asInterface2.startV6ProxyServer();
                                        KnoxNetworkFilterFirewall knoxNetworkFilterFirewall3 =
                                                this.this$0.mKnoxNwFilterFw;
                                        int i5 = this.userId;
                                        knoxNetworkFilterFirewall3.getClass();
                                        KnoxNetworkFilterFirewall.redirectAppGeneratedV6UdpConn(
                                                i5, startV6ProxyServer, configuredDestIpRange2);
                                        this.this$0
                                                .getOemNetdService()
                                                .enablePortInfoEntries(this.userId, 10, 17, true);
                                    }
                                }
                            } catch (RemoteException | NullPointerException unused2) {
                            }
                            return;
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
            }
        }

        @Override // android.content.ServiceConnection
        public final synchronized void onServiceDisconnected(ComponentName componentName) {
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this) {
                        try {
                            Log.d(
                                    "knoxNwFilter-KnoxNetworkFilterService",
                                    "DefaultDnsConnection:onServicedisconnected is reached ");
                            ((ArrayList) this.this$0.mLocalProxyStatus)
                                    .add(
                                            "DefaultDnsConnection:onServiceDisconnected  for user "
                                                    + this.userId
                                                    + " "
                                                    + new Date(System.currentTimeMillis()));
                            if (this.this$0.mDefaultDnsProxyInterfaceList.containsKey(
                                    Integer.valueOf(this.userId))) {
                                this.this$0.mDefaultDnsProxyInterfaceList.put(
                                        Integer.valueOf(this.userId), null);
                            }
                            KnoxNetworkFilterFirewall knoxNetworkFilterFirewall =
                                    this.this$0.mKnoxNwFilterFw;
                            int i = this.userId;
                            knoxNetworkFilterFirewall.getClass();
                            KnoxNetworkFilterFirewall.flushRedirectDnsQueryRules(i);
                            this.this$0.mKnoxNwFilterFw.getClass();
                            KnoxNetworkFilterFirewall knoxNetworkFilterFirewall2 =
                                    this.this$0.mKnoxNwFilterFw;
                            int i2 = this.userId;
                            knoxNetworkFilterFirewall2.getClass();
                            KnoxNetworkFilterFirewall.flushUnAuthorizedPackets(i2);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return;
                case 1:
                    synchronized (this) {
                        try {
                            Log.d(
                                    "knoxNwFilter-KnoxNetworkFilterService",
                                    "DefaultTcpConnection:onServicedisconnected is reached ");
                            ((ArrayList) this.this$0.mLocalProxyStatus)
                                    .add(
                                            "DefaultTcpConnection:onServiceDisconnected  for user "
                                                    + this.userId
                                                    + " "
                                                    + new Date(System.currentTimeMillis()));
                            if (this.this$0.mDefaultTcpProxyInterfaceList.containsKey(
                                    Integer.valueOf(this.userId))) {
                                this.this$0.mDefaultTcpProxyInterfaceList.put(
                                        Integer.valueOf(this.userId), null);
                            }
                            KnoxNetworkFilterFirewall knoxNetworkFilterFirewall3 =
                                    this.this$0.mKnoxNwFilterFw;
                            int i3 = this.userId;
                            knoxNetworkFilterFirewall3.getClass();
                            KnoxNetworkFilterFirewall.flushAppGeneratedRedirectTcpConnRules(i3);
                            KnoxNetworkFilterFirewall knoxNetworkFilterFirewall4 =
                                    this.this$0.mKnoxNwFilterFw;
                            int i4 = this.userId;
                            knoxNetworkFilterFirewall4.getClass();
                            KnoxNetworkFilterFirewall.flushAppGeneratedRedirectV6TcpConnRules(i4);
                            KnoxNetworkFilterFirewall knoxNetworkFilterFirewall5 =
                                    this.this$0.mKnoxNwFilterFw;
                            int i5 = this.userId;
                            knoxNetworkFilterFirewall5.getClass();
                            KnoxNetworkFilterFirewall.flushTcpRedirectRulesForCaptivePortal(i5);
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                    return;
                default:
                    synchronized (this) {
                        try {
                            Log.d(
                                    "knoxNwFilter-KnoxNetworkFilterService",
                                    "DefaultUdpConnection:onServicedisconnected is reached ");
                            ((ArrayList) this.this$0.mLocalProxyStatus)
                                    .add(
                                            "DefaultUdpConnection:onServiceDisconnected  for user "
                                                    + this.userId
                                                    + " "
                                                    + new Date(System.currentTimeMillis()));
                            if (this.this$0.mDefaultUdpProxyInterfaceList.containsKey(
                                    Integer.valueOf(this.userId))) {
                                this.this$0.mDefaultUdpProxyInterfaceList.put(
                                        Integer.valueOf(this.userId), null);
                            }
                            KnoxNetworkFilterFirewall knoxNetworkFilterFirewall6 =
                                    this.this$0.mKnoxNwFilterFw;
                            int i6 = this.userId;
                            knoxNetworkFilterFirewall6.getClass();
                            KnoxNetworkFilterFirewall.flushAppGeneratedRedirectUdpConnRules(i6);
                            KnoxNetworkFilterFirewall knoxNetworkFilterFirewall7 =
                                    this.this$0.mKnoxNwFilterFw;
                            int i7 = this.userId;
                            knoxNetworkFilterFirewall7.getClass();
                            KnoxNetworkFilterFirewall.flushAppGeneratedRedirectV6UdpConnRules(i7);
                        } catch (Throwable th3) {
                            throw th3;
                        }
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KnoxNwFilterHandler extends Handler {
        public KnoxNwFilterHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Can't wrap try/catch for region: R(34:107|108|109|110|111|(2:112|113)|114|(1:116)|117|(1:119)|120|(3:122|(2:125|123)|126)|127|128|129|130|(2:131|132)|133|(2:134|135)|136|(2:137|138)|139|(2:140|141)|142|(2:143|144)|145|(2:146|147)|(2:148|149)|150|151|152|153|(1:155)|156) */
        /* JADX WARN: Code restructure failed: missing block: B:158:0x0486, code lost:

           android.util.Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:setConfig:error occured while communicating to remote service");
        */
        /* JADX WARN: Code restructure failed: missing block: B:159:0x047c, code lost:

           android.util.Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:setConfig:error occured remote service null");
        */
        /* JADX WARN: Removed duplicated region for block: B:155:0x04b9 A[Catch: all -> 0x02da, TryCatch #29 {all -> 0x02da, blocks: (B:105:0x0288, B:107:0x02b6, B:110:0x02d6, B:111:0x02e2, B:113:0x02e5, B:114:0x0305, B:116:0x0314, B:117:0x0317, B:119:0x031c, B:120:0x0321, B:122:0x0327, B:123:0x032d, B:125:0x0333, B:127:0x0341, B:129:0x0375, B:130:0x038c, B:132:0x0394, B:133:0x03ab, B:135:0x03b3, B:136:0x03ca, B:138:0x03da, B:139:0x03f1, B:141:0x03f9, B:142:0x0410, B:144:0x0418, B:145:0x042f, B:147:0x043f, B:149:0x0446, B:150:0x0465, B:152:0x0472, B:153:0x048f, B:155:0x04b9, B:156:0x04bc, B:159:0x047c, B:158:0x0486, B:164:0x0452, B:162:0x045c, B:170:0x041c, B:169:0x0426, B:173:0x03fd, B:172:0x0407, B:176:0x03de, B:175:0x03e8, B:179:0x03b7, B:178:0x03c1, B:182:0x0398, B:181:0x03a2, B:185:0x0379, B:184:0x0383, B:191:0x02de, B:192:0x02e1, B:193:0x04c1, B:109:0x02ba), top: B:104:0x0288, inners: #31, #35, #34, #33, #29, #28, #27, #26, #25 }] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r18) {
            /*
                Method dump skipped, instructions count: 1442
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.enterprise.filter.KnoxNetworkFilterService.KnoxNwFilterHandler.handleMessage(android.os.Message):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NetworkCallback extends ConnectivityManager.NetworkCallback {
        public final int userId;

        public NetworkCallback(int i) {
            this.userId = i;
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onAvailable(Network network) {
            Log.d(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "onAvailable being called for netId " + network.getNetId());
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            int i;
            int i2;
            Iterator<InetAddress> it;
            String str;
            ArrayList arrayList;
            ArrayList arrayList2;
            ArrayList arrayList3;
            StringBuilder sb = new StringBuilder("onLinkPropertiesChanged being called for netId ");
            sb.append(network.getNetId());
            sb.append(" for interface ");
            sb.append(linkProperties.getInterfaceName());
            sb.append(" for user ");
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    sb, this.userId, "knoxNwFilter-KnoxNetworkFilterService");
            KnoxNetworkFilterFirewall knoxNetworkFilterFirewall =
                    KnoxNetworkFilterService.this.mKnoxNwFilterFw;
            int i3 = this.userId;
            List<InetAddress> dnsServers = linkProperties.getDnsServers();
            knoxNetworkFilterFirewall.getClass();
            String str2 = i3 + "_knox_nwfilter_dns_exempt";
            String m = NandswapManager$$ExternalSyntheticOutline0.m(i3, "_knox_nwfilter_dns_drop");
            ArrayList arrayList4 = new ArrayList();
            ArrayList arrayList5 = new ArrayList();
            ArrayList arrayList6 = new ArrayList();
            ArrayList arrayList7 = new ArrayList();
            if (i3 == 0) {
                i2 = 10000;
                i = 19999;
            } else {
                int i4 = i3 * 100000;
                i = i4 + 99999;
                i2 = i4 + 1;
            }
            String str3 = i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i;
            String m2 =
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            " -m owner --uid-owner ", str3, " -p udp --dport 53 ");
            KnoxNetworkFilterFirewall.IpRestoreActionType ipRestoreActionType =
                    KnoxNetworkFilterFirewall.IpRestoreActionType.INSERT;
            ArrayList arrayList8 = arrayList5;
            arrayList6.add(
                    new KnoxNetworkFilterFirewall.IpRestoreParam(
                            m, m2, "DROP", "", ipRestoreActionType));
            arrayList7.add(
                    new KnoxNetworkFilterFirewall.IpRestoreParam(
                            m,
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    " -m owner --uid-owner ", str3, " -p udp --dport 53 "),
                            "DROP",
                            "",
                            ipRestoreActionType));
            Iterator<InetAddress> it2 = dnsServers.iterator();
            while (it2.hasNext()) {
                InetAddress next = it2.next();
                if (next instanceof Inet4Address) {
                    StringBuilder m3 =
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    " -m owner --uid-owner ", str3, " -p udp --dport 443 -d ");
                    m3.append(next.getHostAddress());
                    it = it2;
                    arrayList = arrayList6;
                    str = m;
                    arrayList4.add(
                            new KnoxNetworkFilterFirewall.IpRestoreParam(
                                    str2, m3.toString(), "ACCEPT", "", ipRestoreActionType));
                    StringBuilder m4 =
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    " -m owner --uid-owner ", str3, " -p tcp --dport 443 -d ");
                    m4.append(next.getHostAddress());
                    arrayList4.add(
                            new KnoxNetworkFilterFirewall.IpRestoreParam(
                                    str2, m4.toString(), "ACCEPT", "", ipRestoreActionType));
                    StringBuilder m5 =
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    " -m owner --uid-owner ", str3, " -p tcp --dport 53 -d ");
                    m5.append(next.getHostAddress());
                    arrayList4.add(
                            new KnoxNetworkFilterFirewall.IpRestoreParam(
                                    str2, m5.toString(), "ACCEPT", "", ipRestoreActionType));
                    arrayList4.add(
                            new KnoxNetworkFilterFirewall.IpRestoreParam(
                                    str2,
                                    XmlUtils$$ExternalSyntheticOutline0.m(
                                            " -m owner --uid-owner ", str3, " -p udp --dport 53 "),
                                    "ACCEPT",
                                    "",
                                    ipRestoreActionType));
                    StringBuilder m6 =
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    " -m owner --uid-owner ", str3, " -p udp --dport 443 -d ");
                    m6.append(next.getHostAddress());
                    arrayList.add(
                            new KnoxNetworkFilterFirewall.IpRestoreParam(
                                    str, m6.toString(), "DROP", "", ipRestoreActionType));
                    StringBuilder m7 =
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    " -m owner --uid-owner ", str3, " -p tcp --dport 443 -d ");
                    m7.append(next.getHostAddress());
                    arrayList.add(
                            new KnoxNetworkFilterFirewall.IpRestoreParam(
                                    str, m7.toString(), "DROP", "", ipRestoreActionType));
                    StringBuilder m8 =
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    " -m owner --uid-owner ", str3, " -p tcp --dport 53 -d ");
                    m8.append(next.getHostAddress());
                    arrayList.add(
                            new KnoxNetworkFilterFirewall.IpRestoreParam(
                                    str, m8.toString(), "DROP", "", ipRestoreActionType));
                    arrayList7 = arrayList7;
                } else {
                    it = it2;
                    str = m;
                    ArrayList arrayList9 = arrayList6;
                    ArrayList arrayList10 = arrayList7;
                    if (next instanceof Inet6Address) {
                        StringBuilder m9 =
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        " -m owner --uid-owner ", str3, " -p udp --dport 443 -d ");
                        m9.append(next.getHostAddress());
                        arrayList = arrayList9;
                        ArrayList arrayList11 = arrayList8;
                        arrayList11.add(
                                new KnoxNetworkFilterFirewall.IpRestoreParam(
                                        str2, m9.toString(), "ACCEPT", "", ipRestoreActionType));
                        StringBuilder m10 =
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        " -m owner --uid-owner ", str3, " -p tcp --dport 443 -d ");
                        m10.append(next.getHostAddress());
                        arrayList2 = arrayList4;
                        arrayList3 = arrayList11;
                        arrayList3.add(
                                new KnoxNetworkFilterFirewall.IpRestoreParam(
                                        str2, m10.toString(), "ACCEPT", "", ipRestoreActionType));
                        StringBuilder m11 =
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        " -m owner --uid-owner ", str3, " -p tcp --dport 53 -d ");
                        m11.append(next.getHostAddress());
                        arrayList3.add(
                                new KnoxNetworkFilterFirewall.IpRestoreParam(
                                        str2, m11.toString(), "ACCEPT", "", ipRestoreActionType));
                        arrayList3.add(
                                new KnoxNetworkFilterFirewall.IpRestoreParam(
                                        str2,
                                        XmlUtils$$ExternalSyntheticOutline0.m(
                                                " -m owner --uid-owner ",
                                                str3,
                                                " -p udp --dport 53 "),
                                        "ACCEPT",
                                        "",
                                        ipRestoreActionType));
                        StringBuilder m12 =
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        " -m owner --uid-owner ", str3, " -p udp --dport 443 -d ");
                        m12.append(next.getHostAddress());
                        arrayList7 = arrayList10;
                        arrayList7.add(
                                new KnoxNetworkFilterFirewall.IpRestoreParam(
                                        str, m12.toString(), "DROP", "", ipRestoreActionType));
                        StringBuilder m13 =
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        " -m owner --uid-owner ", str3, " -p tcp --dport 443 -d ");
                        m13.append(next.getHostAddress());
                        arrayList7.add(
                                new KnoxNetworkFilterFirewall.IpRestoreParam(
                                        str, m13.toString(), "DROP", "", ipRestoreActionType));
                        StringBuilder m14 =
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        " -m owner --uid-owner ", str3, " -p tcp --dport 53 -d ");
                        m14.append(next.getHostAddress());
                        arrayList7.add(
                                new KnoxNetworkFilterFirewall.IpRestoreParam(
                                        str, m14.toString(), "DROP", "", ipRestoreActionType));
                        it2 = it;
                        arrayList6 = arrayList;
                        m = str;
                        ArrayList arrayList12 = arrayList3;
                        arrayList4 = arrayList2;
                        arrayList8 = arrayList12;
                    } else {
                        arrayList7 = arrayList10;
                        arrayList = arrayList9;
                    }
                }
                ArrayList arrayList13 = arrayList4;
                arrayList3 = arrayList8;
                arrayList2 = arrayList13;
                it2 = it;
                arrayList6 = arrayList;
                m = str;
                ArrayList arrayList122 = arrayList3;
                arrayList4 = arrayList2;
                arrayList8 = arrayList122;
            }
            KnoxNetworkFilterFirewall.insertRules(4, "*nat", null, arrayList4, true);
            KnoxNetworkFilterFirewall.insertRules(4, "*filter", null, arrayList6, true);
            KnoxNetworkFilterFirewall.insertRules(6, "*filter", null, arrayList7, true);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            StringBuilder sb = new StringBuilder("onLost being called for netId ");
            sb.append(network.getNetId());
            sb.append(" for user ");
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    sb, this.userId, "knoxNwFilter-KnoxNetworkFilterService");
            KnoxNetworkFilterFirewall knoxNetworkFilterFirewall =
                    KnoxNetworkFilterService.this.mKnoxNwFilterFw;
            int i = this.userId;
            knoxNetworkFilterFirewall.getClass();
            KnoxNetworkFilterFirewall.flushExemptDnsRulesFromNat(i);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                KnoxNetworkFilterService.this
                        .getOemNetdService()
                        .clearNetworkFilterEntries(this.userId);
            } catch (RemoteException unused) {
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NwFilterReceiver extends BroadcastReceiver {
        public NwFilterReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String schemeSpecificPart;
            String action = intent.getAction();
            if (isInitialStickyBroadcast()) {
                return;
            }
            Bundle bundle = new Bundle();
            if (action.equalsIgnoreCase("android.intent.action.PACKAGE_ADDED")) {
                Uri data = intent.getData();
                schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                boolean booleanExtra =
                        intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                if (intExtra > -1) {
                    bundle.putInt("uid", intExtra);
                    bundle.putString("package", schemeSpecificPart);
                    bundle.putBoolean("new_install_or_update", booleanExtra);
                    KnoxNetworkFilterService.this.sendMessageToHandler(1, bundle);
                    return;
                }
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.PACKAGE_REMOVED")) {
                Uri data2 = intent.getData();
                schemeSpecificPart = data2 != null ? data2.getSchemeSpecificPart() : null;
                int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
                boolean booleanExtra2 =
                        intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                bundle.putInt("uid", intExtra2);
                bundle.putString("package", schemeSpecificPart);
                bundle.putBoolean("new_install_or_update", booleanExtra2);
                KnoxNetworkFilterService.this.sendMessageToHandler(2, bundle);
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.PACKAGE_DATA_CLEARED")) {
                KnoxNetworkFilterService.this.sendMessageToHandler(3, intent.getExtras());
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")) {
                KnoxNetworkFilterService.this.sendMessageToHandler(4, intent.getExtras());
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                KnoxNetworkFilterService.this.sendMessageToHandler(5, intent.getExtras());
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.AIRPLANE_MODE")) {
                if (intent.getBooleanExtra(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN, false)) {
                    KnoxNetworkFilterService.this.sendMessageToHandler(6, null);
                }
            } else if (action.equals("com.samsung.intent.action.EMERGENCY_STATE_CHANGED")) {
                KnoxNetworkFilterService.this.sendMessageToHandler(7, intent.getExtras());
            } else if (action.equals("android.intent.action.USER_REMOVED")) {
                KnoxNetworkFilterService.this.sendMessageToHandler(12, intent.getExtras());
            }
        }
    }

    /* renamed from: -$$Nest$mhandleActionBootComplete, reason: not valid java name */
    public static void m506$$Nest$mhandleActionBootComplete(
            KnoxNetworkFilterService knoxNetworkFilterService, int i) {
        synchronized (knoxNetworkFilterService) {
            if (knoxNetworkFilterService.mKnoxNwFilterHelper.isUserIdRegistered(i)) {
                Log.i(
                        "knoxNwFilter-KnoxNetworkFilterService",
                        "boot complete event is triggered for registered user " + i);
                knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                knoxNetworkFilterService.mNwFilterProxyAppId =
                        UserHandle.getAppId(
                                KnoxNetworkFilterHelper.getUIDForPackage(
                                        i, "com.samsung.android.knox.app.networkfilter"));
                String registeredAppPackage =
                        knoxNetworkFilterService.mKnoxNwFilterHelper.getRegisteredAppPackage(i);
                try {
                    knoxNetworkFilterService.bindInternalProxyServices(i);
                } catch (InterruptedException unused) {
                    if (registeredAppPackage != null) {
                        Log.i(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "sending register broadcast to registered package "
                                        + registeredAppPackage
                                        + " userId "
                                        + i
                                        + " failed after boot complete");
                        knoxNetworkFilterService.sendBootStatusIntent(
                                i, 0, -8, 2, registeredAppPackage);
                        return;
                    }
                }
                if (registeredAppPackage != null) {
                    Log.i(
                            "knoxNwFilter-KnoxNetworkFilterService",
                            "sending register broadcast to registered package "
                                    + registeredAppPackage
                                    + " userId "
                                    + i
                                    + " success after boot complete");
                    knoxNetworkFilterService.sendBootStatusIntent(i, 0, 0, 2, registeredAppPackage);
                }
            }
            if (knoxNetworkFilterService.mKnoxNwFilterHelper.isUserIdAuthorized(i)) {
                Log.i(
                        "knoxNwFilter-KnoxNetworkFilterService",
                        "boot complete event is triggered for authorized user " + i);
                knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                knoxNetworkFilterService.mNwFilterProxyAppId =
                        UserHandle.getAppId(
                                KnoxNetworkFilterHelper.getUIDForPackage(
                                        i, "com.samsung.android.knox.app.networkfilter"));
                String authorizedAppPackage =
                        knoxNetworkFilterService.mKnoxNwFilterHelper.getAuthorizedAppPackage(i);
                try {
                    knoxNetworkFilterService.bindInternalProxyServices(i);
                    if (authorizedAppPackage != null) {
                        Log.i(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "sending broadcast to authorized package "
                                        + authorizedAppPackage
                                        + " userId "
                                        + i
                                        + " success after boot complete");
                        knoxNetworkFilterService.sendBootStatusIntent(
                                i, 6, 0, 2, authorizedAppPackage);
                    }
                } catch (InterruptedException unused2) {
                    Log.i(
                            "knoxNwFilter-KnoxNetworkFilterService",
                            "sending broadcast to authorized package "
                                    + authorizedAppPackage
                                    + " userId "
                                    + i
                                    + " failed after boot complete");
                    knoxNetworkFilterService.sendBootStatusIntent(
                            i, 6, -8, 2, authorizedAppPackage);
                }
            }
        }
    }

    /* renamed from: -$$Nest$mhandleActionClearData, reason: not valid java name */
    public static void m507$$Nest$mhandleActionClearData(
            KnoxNetworkFilterService knoxNetworkFilterService, Bundle bundle) {
        synchronized (knoxNetworkFilterService) {
            try {
                int i = bundle.getInt("android.intent.extra.UID");
                int userId = UserHandle.getUserId(i);
                if (knoxNetworkFilterService.mKnoxNwFilterHelper.isUserIdRegistered(userId)) {
                    knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                    String packageNameForUid = KnoxNetworkFilterHelper.getPackageNameForUid(i);
                    String registeredAppPackage =
                            knoxNetworkFilterService.mKnoxNwFilterHelper.getRegisteredAppPackage(
                                    userId);
                    if (registeredAppPackage != null
                            && registeredAppPackage.equalsIgnoreCase(packageNameForUid)) {
                        Log.i(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "clear data action is triggered for registered package "
                                        + packageNameForUid
                                        + " with uid "
                                        + i);
                        knoxNetworkFilterService.handleVendorPackageUninstall(
                                i, packageNameForUid, false);
                        Log.i(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "sending register broadcast to registered package "
                                        + packageNameForUid
                                        + " userId "
                                        + userId
                                        + " success after clear data event");
                        knoxNetworkFilterService.sendStatusIntent(userId, 0, 0, packageNameForUid);
                    }
                }
                if (knoxNetworkFilterService.mKnoxNwFilterHelper.isUserIdAuthorized(userId)) {
                    knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                    String packageNameForUid2 = KnoxNetworkFilterHelper.getPackageNameForUid(i);
                    String authorizedAppPackage =
                            knoxNetworkFilterService.mKnoxNwFilterHelper.getAuthorizedAppPackage(
                                    userId);
                    if (authorizedAppPackage != null
                            && authorizedAppPackage.equalsIgnoreCase(packageNameForUid2)) {
                        Log.i(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "clear data action is triggered for authorized package "
                                        + packageNameForUid2
                                        + " with uid "
                                        + i);
                        KnoxAnalyticsData knoxAnalyticsData =
                                new KnoxAnalyticsData("KNOX_ZT", 1, "ZTNA_USAGE");
                        knoxAnalyticsData.setProperty("apiN", "unregisterNetworkFilter");
                        knoxAnalyticsData.setProperty("pkgN", authorizedAppPackage);
                        knoxAnalyticsData.setProperty("eInfo", "202");
                        knoxAnalyticsData.setUserTypeProperty(userId);
                        Log.i(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "KnoxAnalyticsData:unregisterNetworkFilter API getting logged due"
                                    + " to clear-data for caller "
                                        + authorizedAppPackage
                                        + " "
                                        + userId);
                        KnoxAnalytics.log(knoxAnalyticsData);
                        knoxNetworkFilterService.handleVendorPackageUninstall(
                                i, packageNameForUid2, true);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mhandleActionLockBootCompleted, reason: not valid java name */
    public static void m508$$Nest$mhandleActionLockBootCompleted(
            KnoxNetworkFilterService knoxNetworkFilterService, int i) {
        synchronized (knoxNetworkFilterService) {
            boolean isUserIdRegistered =
                    knoxNetworkFilterService.mKnoxNwFilterHelper.isUserIdRegistered(i);
            if (isUserIdRegistered) {
                Log.i(
                        "knoxNwFilter-KnoxNetworkFilterService",
                        "lock boot complete event is triggered for registered user " + i);
                knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                knoxNetworkFilterService.mNwFilterProxyAppId =
                        UserHandle.getAppId(
                                KnoxNetworkFilterHelper.getUIDForPackage(
                                        i, "com.samsung.android.knox.app.networkfilter"));
                String registeredAppPackage =
                        knoxNetworkFilterService.mKnoxNwFilterHelper.getRegisteredAppPackage(i);
                try {
                    knoxNetworkFilterService.bindInternalProxyServices(i);
                } catch (InterruptedException unused) {
                    if (registeredAppPackage != null) {
                        Log.i(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "sending register broadcast to registered package "
                                        + registeredAppPackage
                                        + " userId "
                                        + i
                                        + " failed after lock boot complete");
                        knoxNetworkFilterService.sendBootStatusIntent(
                                i, 0, -8, 1, registeredAppPackage);
                        return;
                    }
                }
                if (registeredAppPackage != null) {
                    Log.i(
                            "knoxNwFilter-KnoxNetworkFilterService",
                            "sending register broadcast to registered package "
                                    + registeredAppPackage
                                    + " userId "
                                    + i
                                    + " success after lock boot complete");
                    knoxNetworkFilterService.sendBootStatusIntent(i, 0, 0, 1, registeredAppPackage);
                }
            }
            boolean isUserIdAuthorized =
                    knoxNetworkFilterService.mKnoxNwFilterHelper.isUserIdAuthorized(i);
            if (isUserIdAuthorized) {
                Log.i(
                        "knoxNwFilter-KnoxNetworkFilterService",
                        "lock boot complete event is triggered for authorized user " + i);
                knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                knoxNetworkFilterService.mNwFilterProxyAppId =
                        UserHandle.getAppId(
                                KnoxNetworkFilterHelper.getUIDForPackage(
                                        i, "com.samsung.android.knox.app.networkfilter"));
                String authorizedAppPackage =
                        knoxNetworkFilterService.mKnoxNwFilterHelper.getAuthorizedAppPackage(i);
                try {
                    knoxNetworkFilterService.bindInternalProxyServices(i);
                } catch (InterruptedException unused2) {
                    if (authorizedAppPackage != null) {
                        Log.i(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "sending broadcast to authorized package "
                                        + authorizedAppPackage
                                        + " userId "
                                        + i
                                        + " failed after lock boot complete");
                        knoxNetworkFilterService.sendBootStatusIntent(
                                i, 6, -8, 1, authorizedAppPackage);
                        return;
                    }
                }
                if (authorizedAppPackage != null) {
                    Log.i(
                            "knoxNwFilter-KnoxNetworkFilterService",
                            "sending broadcast to authorized package "
                                    + authorizedAppPackage
                                    + " userId "
                                    + i
                                    + " success after lock boot complete");
                    knoxNetworkFilterService.sendBootStatusIntent(i, 6, 0, 1, authorizedAppPackage);
                }
            }
            if (isUserIdRegistered || isUserIdAuthorized) {
                knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
                KnoxNetworkFilterFirewall.createIpTableChains(i);
            }
        }
    }

    /* renamed from: -$$Nest$mhandleActionPackageAdded, reason: not valid java name */
    public static void m509$$Nest$mhandleActionPackageAdded(
            KnoxNetworkFilterService knoxNetworkFilterService, Bundle bundle) {
        boolean z;
        int i;
        KnoxNetworkFilterProfileInfo profileEntry;
        ServiceInfo[] serviceInfoArr;
        synchronized (knoxNetworkFilterService) {
            String string = bundle.getString("package");
            int i2 = bundle.getInt("uid");
            int userId = UserHandle.getUserId(i2);
            boolean z2 = bundle.getBoolean("new_install_or_update");
            boolean isUserIdRegistered =
                    knoxNetworkFilterService.mKnoxNwFilterHelper.isUserIdRegistered(userId);
            boolean isUserIdAuthorized =
                    knoxNetworkFilterService.mKnoxNwFilterHelper.isUserIdAuthorized(userId);
            if (isUserIdRegistered || isUserIdAuthorized) {
                String versionName =
                        knoxNetworkFilterService.mKnoxNwFilterHelper.getVersionName(userId, string);
                String signature =
                        knoxNetworkFilterService.mKnoxNwFilterHelper.getSignature(userId, string);
                int versionCode =
                        knoxNetworkFilterService.mKnoxNwFilterHelper.getVersionCode(userId, string);
                String[] strArr = null;
                INetworkFilterProxy iNetworkFilterProxy =
                        (INetworkFilterProxy)
                                knoxNetworkFilterService.mDefaultDnsProxyInterfaceList.getOrDefault(
                                        Integer.valueOf(userId), null);
                INetworkFilterProxy iNetworkFilterProxy2 =
                        (INetworkFilterProxy)
                                knoxNetworkFilterService.mDefaultTcpProxyInterfaceList.getOrDefault(
                                        Integer.valueOf(userId), null);
                INetworkFilterProxy iNetworkFilterProxy3 =
                        (INetworkFilterProxy)
                                knoxNetworkFilterService.mDefaultUdpProxyInterfaceList.getOrDefault(
                                        Integer.valueOf(userId), null);
                if (iNetworkFilterProxy != null) {
                    try {
                        iNetworkFilterProxy.updateApplicationInfo(
                                string, i2, signature, versionName, versionCode);
                    } catch (RemoteException | NullPointerException unused) {
                    }
                }
                if (iNetworkFilterProxy2 != null) {
                    try {
                        iNetworkFilterProxy2.updateApplicationInfo(
                                string, i2, signature, versionName, versionCode);
                        strArr = iNetworkFilterProxy2.getBrowserAppList();
                    } catch (RemoteException | NullPointerException unused2) {
                    }
                }
                if (iNetworkFilterProxy3 != null) {
                    try {
                        iNetworkFilterProxy3.updateApplicationInfo(
                                string, i2, signature, versionName, versionCode);
                    } catch (RemoteException | NullPointerException unused3) {
                    }
                }
                knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    PackageInfo packageInfo =
                            AppGlobals.getPackageManager().getPackageInfo(string, 4L, userId);
                    if (packageInfo != null && (serviceInfoArr = packageInfo.services) != null) {
                        for (ServiceInfo serviceInfo : serviceInfoArr) {
                            String str = serviceInfo.permission;
                            if (str != null
                                    && str.equalsIgnoreCase(
                                            "android.permission.BIND_VPN_SERVICE")) {
                                z = true;
                                break;
                            }
                        }
                    }
                    z = false;
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (RemoteException unused4) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    z = false;
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
                if (z) {
                    Log.d(
                            "knoxNwFilter-KnoxNetworkFilterService",
                            "vpn client with package name "
                                    + string
                                    + " and uid "
                                    + i2
                                    + " is installed");
                    knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                    String profilebyUserId = KnoxNetworkFilterHelper.getProfilebyUserId(userId);
                    if (profilebyUserId != null
                            && (profileEntry =
                                            KnoxNetworkFilterProfileInfo.getProfileEntry(
                                                    profilebyUserId))
                                    != null
                            && strArr != null) {
                        if (Arrays.asList(strArr).contains(string)) {
                            Log.i(
                                    "knoxNwFilter-KnoxNetworkFilterService",
                                    "ignore adding the browser package " + string + " to vpn list");
                        } else {
                            profileEntry.mVpnClientUidList.add(Integer.valueOf(i2));
                            knoxNetworkFilterService.exemptUidFromNwFilterRange(i2, i2);
                        }
                    }
                }
                if (!z2 && strArr != null && Arrays.asList(strArr).contains(string)) {
                    try {
                        i = iNetworkFilterProxy2.getHttpLocalProxyPort();
                    } catch (RemoteException | NullPointerException unused5) {
                        i = -1;
                    }
                    knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
                    KnoxNetworkFilterFirewall.applyTcpRedirectRulesForCaptivePortal(i2, i);
                }
                String registeredAppPackage =
                        knoxNetworkFilterService.mKnoxNwFilterHelper.getRegisteredAppPackage(
                                userId);
                if (registeredAppPackage != null) {
                    if (!registeredAppPackage.equalsIgnoreCase(string)) {
                        return;
                    }
                    if (knoxNetworkFilterService.mKnoxNwFilterHelper.isAppRegistered(
                            userId,
                            string,
                            knoxNetworkFilterService.mKnoxNwFilterHelper.getSignature(
                                    userId, string))) {
                        try {
                            Thread.currentThread();
                            Thread.sleep(3000L);
                        } catch (Exception unused6) {
                        }
                        knoxNetworkFilterService.mKnoxNwFilterHelper
                                .addOrRemoveAppsFromBatteryOptimization(i2, string, true);
                        knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                        Log.d(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "sending register broadcast to package "
                                        + string
                                        + " userId "
                                        + userId
                                        + " success after install");
                        knoxNetworkFilterService.sendStatusIntent(userId, 0, 0, string);
                    }
                }
                String authorizedAppPackage =
                        knoxNetworkFilterService.mKnoxNwFilterHelper.getAuthorizedAppPackage(
                                userId);
                if (authorizedAppPackage != null) {
                    if (!authorizedAppPackage.equalsIgnoreCase(string)) {
                        return;
                    }
                    knoxNetworkFilterService.mKnoxNwFilterHelper
                            .addOrRemoveAppsFromBatteryOptimization(i2, string, true);
                    knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                }
            }
        }
    }

    /* renamed from: -$$Nest$mhandleActionPackageRemoved, reason: not valid java name */
    public static void m510$$Nest$mhandleActionPackageRemoved(
            KnoxNetworkFilterService knoxNetworkFilterService, Bundle bundle) {
        int i;
        KnoxNetworkFilterProfileInfo profileEntry;
        synchronized (knoxNetworkFilterService) {
            String string = bundle.getString("package");
            int i2 = bundle.getInt("uid");
            int userId = UserHandle.getUserId(i2);
            boolean z = bundle.getBoolean("new_install_or_update");
            boolean isUserIdRegistered =
                    knoxNetworkFilterService.mKnoxNwFilterHelper.isUserIdRegistered(userId);
            boolean isUserIdAuthorized =
                    knoxNetworkFilterService.mKnoxNwFilterHelper.isUserIdAuthorized(userId);
            if (isUserIdRegistered || isUserIdAuthorized) {
                String[] strArr = null;
                INetworkFilterProxy iNetworkFilterProxy =
                        (INetworkFilterProxy)
                                knoxNetworkFilterService.mDefaultDnsProxyInterfaceList.getOrDefault(
                                        Integer.valueOf(userId), null);
                INetworkFilterProxy iNetworkFilterProxy2 =
                        (INetworkFilterProxy)
                                knoxNetworkFilterService.mDefaultTcpProxyInterfaceList.getOrDefault(
                                        Integer.valueOf(userId), null);
                INetworkFilterProxy iNetworkFilterProxy3 =
                        (INetworkFilterProxy)
                                knoxNetworkFilterService.mDefaultUdpProxyInterfaceList.getOrDefault(
                                        Integer.valueOf(userId), null);
                if (iNetworkFilterProxy != null) {
                    try {
                        iNetworkFilterProxy.updateApplicationInfo(
                                string, -1, (String) null, (String) null, 0);
                    } catch (RemoteException | NullPointerException unused) {
                    }
                }
                if (iNetworkFilterProxy2 != null) {
                    try {
                        iNetworkFilterProxy2.updateApplicationInfo(
                                string, -1, (String) null, (String) null, 0);
                    } catch (RemoteException | NullPointerException unused2) {
                    }
                }
                if (iNetworkFilterProxy3 != null) {
                    try {
                        iNetworkFilterProxy3.updateApplicationInfo(
                                string, -1, (String) null, (String) null, 0);
                    } catch (RemoteException | NullPointerException unused3) {
                    }
                }
                knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                String profilebyUserId = KnoxNetworkFilterHelper.getProfilebyUserId(userId);
                if (profilebyUserId != null
                        && (profileEntry =
                                        KnoxNetworkFilterProfileInfo.getProfileEntry(
                                                profilebyUserId))
                                != null
                        && profileEntry.mVpnClientUidList.contains(Integer.valueOf(i2))) {
                    profileEntry.mVpnClientUidList.remove(Integer.valueOf(i2));
                    Log.d(
                            "knoxNwFilter-KnoxNetworkFilterService",
                            "vpn client with package name "
                                    + string
                                    + " and uid "
                                    + i2
                                    + " is uninstalled");
                    knoxNetworkFilterService.removeExemptUidFromNwFilterRange(i2, i2);
                }
                if (!z) {
                    try {
                        strArr = iNetworkFilterProxy2.getBrowserAppList();
                    } catch (RemoteException | NullPointerException unused4) {
                    }
                    if (strArr != null && Arrays.asList(strArr).contains(string)) {
                        try {
                            i = iNetworkFilterProxy2.getHttpLocalProxyPort();
                        } catch (RemoteException | NullPointerException unused5) {
                            i = -1;
                        }
                        knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
                        KnoxNetworkFilterFirewall.removeTcpRedirectRulesForCaptivePortal(i2, i);
                    }
                }
                String registeredAppPackage =
                        knoxNetworkFilterService.mKnoxNwFilterHelper.getRegisteredAppPackage(
                                userId);
                boolean z2 =
                        registeredAppPackage != null
                                && registeredAppPackage.equalsIgnoreCase(string);
                if ((knoxNetworkFilterService.mKnoxNwFilterHelper.isAppAuthorized(i2, string) || z2)
                        && !z) {
                    if (z2) {
                        Log.i(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "Registered package getting uninstalled is " + string + " " + i2);
                        knoxNetworkFilterService.handleVendorPackageUninstall(i2, string, false);
                    } else {
                        Log.i(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "Authorized package getting uninstalled is " + string + " " + i2);
                        KnoxAnalyticsData knoxAnalyticsData =
                                new KnoxAnalyticsData("KNOX_ZT", 1, "ZTNA_USAGE");
                        knoxAnalyticsData.setProperty("apiN", "unregisterNetworkFilter");
                        knoxAnalyticsData.setProperty("pkgN", string);
                        knoxAnalyticsData.setProperty("eInfo", "201");
                        knoxAnalyticsData.setUserTypeProperty(userId);
                        Log.i(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "KnoxAnalyticsData:unregisterNetworkFilter API getting logged due"
                                    + " to uninstall for caller "
                                        + string
                                        + " "
                                        + userId);
                        KnoxAnalytics.log(knoxAnalyticsData);
                        knoxNetworkFilterService.handleVendorPackageUninstall(i2, string, true);
                    }
                }
            }
        }
    }

    /* renamed from: -$$Nest$mhandleBindingDiedEvent, reason: not valid java name */
    public static void m511$$Nest$mhandleBindingDiedEvent(
            KnoxNetworkFilterService knoxNetworkFilterService, int i) {
        knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
        String profilebyUserId = KnoxNetworkFilterHelper.getProfilebyUserId(i);
        knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
        int vendorUidByProfile = KnoxNetworkFilterHelper.getVendorUidByProfile(profilebyUserId);
        knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
        String packageNameForUid = KnoxNetworkFilterHelper.getPackageNameForUid(vendorUidByProfile);
        knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
        int uIDForPackage =
                KnoxNetworkFilterHelper.getUIDForPackage(
                        i, "com.samsung.android.knox.app.networkfilter");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            knoxNetworkFilterService.getOemNetdService().enablePortInfoEntries(i, 2, 6, false);
            knoxNetworkFilterService.getOemNetdService().enablePortInfoEntries(i, 10, 6, false);
            knoxNetworkFilterService.getOemNetdService().enablePortInfoEntries(i, 10, 17, false);
            knoxNetworkFilterService.getOemNetdService().clearNetworkFilterEntries(i);
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        knoxNetworkFilterService.unregisterSystemDefaultNetworkCallback(i);
        try {
            knoxNetworkFilterService.releaseNwFilterNetId(
                    knoxNetworkFilterService.getOemNetdService().getNwFilterNetId(i));
            knoxNetworkFilterService.getOemNetdService().removeUserFromNwFilterRange(i);
            knoxNetworkFilterService.getOemNetdService().setNwFilterNetId(i, -1);
            knoxNetworkFilterService.getOemNetdService().removeKnoxNwFilterProxyApp(uIDForPackage);
        } catch (RemoteException unused2) {
            Log.e(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "handleBindingDiedEvent:error occured while communicating to remote oemnetd"
                        + " service");
        }
        knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
        KnoxNetworkFilterFirewall.flushUnAuthorizedPackets(i);
        String str = KnoxNetworkFilterConstants.NW_FILTER_EXEMPT_LIST[0];
        knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
        int uIDForPackage2 = KnoxNetworkFilterHelper.getUIDForPackage(i, str);
        if (uIDForPackage2 != -1) {
            knoxNetworkFilterService.removeExemptUidFromNwFilterRange(
                    uIDForPackage2, uIDForPackage2);
        }
        knoxNetworkFilterService.removeExemptUidFromNwFilterRange(
                vendorUidByProfile, vendorUidByProfile);
        if (i == 0) {
            knoxNetworkFilterService.removeExemptUidFromNwFilterRange(1, 9999);
        }
        KnoxNetworkFilterProfileInfo profileEntry =
                KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId);
        if (profileEntry != null) {
            Iterator it = profileEntry.mVpnClientUidList.iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                knoxNetworkFilterService.removeExemptUidFromNwFilterRange(intValue, intValue);
            }
        }
        knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
        KnoxNetworkFilterFirewall.flushAppGeneratedDOTBlockRules(i);
        knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
        KnoxNetworkFilterFirewall.flushExemptDnsRulesFromNat(i);
        knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
        KnoxNetworkFilterFirewall.flushRedirectDnsQueryRules(i);
        knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
        KnoxNetworkFilterFirewall.flushAppGeneratedRedirectTcpConnRules(i);
        knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
        KnoxNetworkFilterFirewall.flushUdpPacketExemptRules(i, 2);
        knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
        KnoxNetworkFilterFirewall.flushAppGeneratedRedirectUdpConnRules(i);
        knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
        KnoxNetworkFilterFirewall.flushRedirectV6DnsQueryRules(i);
        knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
        KnoxNetworkFilterFirewall.flushAppGeneratedRedirectV6TcpConnRules(i);
        knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
        KnoxNetworkFilterFirewall.flushUdpPacketExemptRules(i, 10);
        knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
        KnoxNetworkFilterFirewall.flushAppGeneratedRedirectV6UdpConnRules(i);
        String[] strArr = null;
        if (profileEntry != null) {
            try {
                strArr =
                        (String[])
                                profileEntry.mBrowserAppList.toArray(
                                        new String[profileEntry.mBrowserAppList.size()]);
            } catch (RemoteException unused3) {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterService",
                        "handleBindingDiedEvent:tcp:http:error occured while communicating to"
                            + " remote service");
            } catch (NullPointerException unused4) {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterService",
                        "handleBindingDiedEvent:tcp:http:error occured remote service null");
            }
        }
        knoxNetworkFilterService.getOemNetdService().setHttpProxyPort(i, -1);
        knoxNetworkFilterService.mKnoxNwFilterHelper.applyHttpProxyConfiguration(
                profilebyUserId, strArr, false, i);
        knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
        KnoxNetworkFilterFirewall.flushTcpRedirectRulesForCaptivePortal(i);
        knoxNetworkFilterService.mKnoxNwFilterHelper.addOrRemoveAppsFromBatteryOptimization(
                uIDForPackage, "com.samsung.android.knox.app.networkfilter", false);
        knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
        knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
        KnoxNetworkFilterProfileInfo profileEntry2 =
                KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId);
        if (profileEntry2 != null) {
            profileEntry2.mState = 5;
        }
        knoxNetworkFilterService.sendStatusIntent(i, 2, -10, packageNameForUid);
        knoxNetworkFilterService.disableNetdFlags();
        knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
        int adminIdForUser = knoxNetworkFilterService.mKnoxNwFilterHelper.getAdminIdForUser(i);
        knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
        knoxNetworkFilterService.sendEMMStatusIntent(
                i, 2, KnoxNetworkFilterHelper.getPackageNameForUid(adminIdForUser));
    }

    /* renamed from: -$$Nest$mhandleRegisterOperation, reason: not valid java name */
    public static void m512$$Nest$mhandleRegisterOperation(
            KnoxNetworkFilterService knoxNetworkFilterService, Bundle bundle) {
        synchronized (knoxNetworkFilterService) {
            try {
                int i = bundle.getInt("userId");
                String string = bundle.getString("package");
                knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                int uIDForPackage = KnoxNetworkFilterHelper.getUIDForPackage(i, string);
                try {
                    knoxNetworkFilterService.bindInternalProxyServices(i);
                    knoxNetworkFilterService.initializeModules();
                    knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
                    KnoxNetworkFilterFirewall.createIpTableChains(i);
                    if (uIDForPackage != -1) {
                        knoxNetworkFilterService.mKnoxNwFilterHelper
                                .addOrRemoveAppsFromBatteryOptimization(
                                        uIDForPackage, string, true);
                        knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                    }
                    Log.d(
                            "knoxNwFilter-KnoxNetworkFilterService",
                            "sending register broadcast to package "
                                    + string
                                    + " userId "
                                    + i
                                    + " success during register process");
                    knoxNetworkFilterService.sendStatusIntent(i, 0, 0, string);
                } catch (InterruptedException unused) {
                    Log.d(
                            "knoxNwFilter-KnoxNetworkFilterService",
                            "sending register broadcast to package "
                                    + string
                                    + " userId "
                                    + i
                                    + " failed during register process");
                    knoxNetworkFilterService.sendStatusIntent(i, 0, -8, string);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x015a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x023c A[Catch: all -> 0x0070, TryCatch #8 {, blocks: (B:4:0x000b, B:9:0x0058, B:11:0x0061, B:15:0x0073, B:17:0x0083, B:19:0x0091, B:21:0x0098, B:23:0x00a8, B:24:0x00ab, B:26:0x00b0, B:27:0x00b5, B:128:0x00e6, B:129:0x00ea, B:29:0x0115, B:111:0x011a, B:112:0x011e, B:114:0x0126, B:117:0x012e, B:33:0x0156, B:103:0x015a, B:104:0x015e, B:35:0x0192, B:91:0x0196, B:92:0x019a, B:94:0x01a2, B:37:0x01cf, B:79:0x01d3, B:80:0x01d7, B:82:0x01e9, B:40:0x0216, B:41:0x0225, B:43:0x023c, B:44:0x0240, B:46:0x0246, B:48:0x0259, B:50:0x025d, B:52:0x026a, B:54:0x027c, B:56:0x02a7, B:57:0x02b0, B:59:0x02b6, B:63:0x02bb, B:65:0x02de, B:66:0x02e1, B:68:0x0301, B:76:0x030c, B:73:0x0329, B:88:0x01f4, B:86:0x0205, B:100:0x01ad, B:98:0x01be, B:108:0x0170, B:106:0x0181, B:125:0x0132, B:123:0x0143, B:133:0x00f3, B:131:0x0104, B:135:0x0348), top: B:3:0x000b, inners: #3, #18, #17, #16, #15, #14, #13 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x02de A[Catch: all -> 0x0070, TryCatch #8 {, blocks: (B:4:0x000b, B:9:0x0058, B:11:0x0061, B:15:0x0073, B:17:0x0083, B:19:0x0091, B:21:0x0098, B:23:0x00a8, B:24:0x00ab, B:26:0x00b0, B:27:0x00b5, B:128:0x00e6, B:129:0x00ea, B:29:0x0115, B:111:0x011a, B:112:0x011e, B:114:0x0126, B:117:0x012e, B:33:0x0156, B:103:0x015a, B:104:0x015e, B:35:0x0192, B:91:0x0196, B:92:0x019a, B:94:0x01a2, B:37:0x01cf, B:79:0x01d3, B:80:0x01d7, B:82:0x01e9, B:40:0x0216, B:41:0x0225, B:43:0x023c, B:44:0x0240, B:46:0x0246, B:48:0x0259, B:50:0x025d, B:52:0x026a, B:54:0x027c, B:56:0x02a7, B:57:0x02b0, B:59:0x02b6, B:63:0x02bb, B:65:0x02de, B:66:0x02e1, B:68:0x0301, B:76:0x030c, B:73:0x0329, B:88:0x01f4, B:86:0x0205, B:100:0x01ad, B:98:0x01be, B:108:0x0170, B:106:0x0181, B:125:0x0132, B:123:0x0143, B:133:0x00f3, B:131:0x0104, B:135:0x0348), top: B:3:0x000b, inners: #3, #18, #17, #16, #15, #14, #13 }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0301 A[Catch: all -> 0x0070, TRY_LEAVE, TryCatch #8 {, blocks: (B:4:0x000b, B:9:0x0058, B:11:0x0061, B:15:0x0073, B:17:0x0083, B:19:0x0091, B:21:0x0098, B:23:0x00a8, B:24:0x00ab, B:26:0x00b0, B:27:0x00b5, B:128:0x00e6, B:129:0x00ea, B:29:0x0115, B:111:0x011a, B:112:0x011e, B:114:0x0126, B:117:0x012e, B:33:0x0156, B:103:0x015a, B:104:0x015e, B:35:0x0192, B:91:0x0196, B:92:0x019a, B:94:0x01a2, B:37:0x01cf, B:79:0x01d3, B:80:0x01d7, B:82:0x01e9, B:40:0x0216, B:41:0x0225, B:43:0x023c, B:44:0x0240, B:46:0x0246, B:48:0x0259, B:50:0x025d, B:52:0x026a, B:54:0x027c, B:56:0x02a7, B:57:0x02b0, B:59:0x02b6, B:63:0x02bb, B:65:0x02de, B:66:0x02e1, B:68:0x0301, B:76:0x030c, B:73:0x0329, B:88:0x01f4, B:86:0x0205, B:100:0x01ad, B:98:0x01be, B:108:0x0170, B:106:0x0181, B:125:0x0132, B:123:0x0143, B:133:0x00f3, B:131:0x0104, B:135:0x0348), top: B:3:0x000b, inners: #3, #18, #17, #16, #15, #14, #13 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01d3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0196 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: -$$Nest$mhandleStartFilteringOperation, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m513$$Nest$mhandleStartFilteringOperation(
            com.android.server.enterprise.filter.KnoxNetworkFilterService r17,
            android.os.Bundle r18) {
        /*
            Method dump skipped, instructions count: 858
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.filter.KnoxNetworkFilterService.m513$$Nest$mhandleStartFilteringOperation(com.android.server.enterprise.filter.KnoxNetworkFilterService,"
                    + " android.os.Bundle):void");
    }

    /* renamed from: -$$Nest$mhandleStopFilteringOperation, reason: not valid java name */
    public static void m514$$Nest$mhandleStopFilteringOperation(
            KnoxNetworkFilterService knoxNetworkFilterService, Bundle bundle) {
        synchronized (knoxNetworkFilterService) {
            try {
                int i = bundle.getInt("uid");
                int userId = UserHandle.getUserId(i);
                String string = bundle.getString("profileName");
                knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                String packageNameForUid = KnoxNetworkFilterHelper.getPackageNameForUid(i);
                knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                int uIDForPackage =
                        KnoxNetworkFilterHelper.getUIDForPackage(
                                userId, "com.samsung.android.knox.app.networkfilter");
                try {
                    knoxNetworkFilterService.releaseNwFilterNetId(
                            knoxNetworkFilterService.getOemNetdService().getNwFilterNetId(userId));
                    knoxNetworkFilterService
                            .getOemNetdService()
                            .removeUserFromNwFilterRange(userId);
                    knoxNetworkFilterService.getOemNetdService().setNwFilterNetId(userId, -1);
                    knoxNetworkFilterService
                            .getOemNetdService()
                            .removeKnoxNwFilterProxyApp(uIDForPackage);
                    knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
                    KnoxNetworkFilterFirewall.flushUnAuthorizedPackets(userId);
                    String str = KnoxNetworkFilterConstants.NW_FILTER_EXEMPT_LIST[0];
                    knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                    int uIDForPackage2 = KnoxNetworkFilterHelper.getUIDForPackage(userId, str);
                    if (uIDForPackage2 != -1) {
                        knoxNetworkFilterService.removeExemptUidFromNwFilterRange(
                                uIDForPackage2, uIDForPackage2);
                    }
                    knoxNetworkFilterService.removeExemptUidFromNwFilterRange(i, i);
                    if (userId == 0) {
                        knoxNetworkFilterService.removeExemptUidFromNwFilterRange(1, 9999);
                    }
                    KnoxNetworkFilterProfileInfo profileEntry =
                            KnoxNetworkFilterProfileInfo.getProfileEntry(string);
                    if (profileEntry != null) {
                        Iterator it = profileEntry.mVpnClientUidList.iterator();
                        while (it.hasNext()) {
                            int intValue = ((Integer) it.next()).intValue();
                            knoxNetworkFilterService.removeExemptUidFromNwFilterRange(
                                    intValue, intValue);
                        }
                    }
                    knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
                    KnoxNetworkFilterFirewall.flushAppGeneratedDOTBlockRules(userId);
                    knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
                    KnoxNetworkFilterFirewall.flushExemptDnsRulesFromNat(userId);
                    String[] strArr = null;
                    INetworkFilterProxy iNetworkFilterProxy =
                            (INetworkFilterProxy)
                                    knoxNetworkFilterService.mDefaultDnsProxyInterfaceList
                                            .getOrDefault(Integer.valueOf(userId), null);
                    INetworkFilterProxy iNetworkFilterProxy2 =
                            (INetworkFilterProxy)
                                    knoxNetworkFilterService.mDefaultTcpProxyInterfaceList
                                            .getOrDefault(Integer.valueOf(userId), null);
                    INetworkFilterProxy iNetworkFilterProxy3 =
                            (INetworkFilterProxy)
                                    knoxNetworkFilterService.mDefaultUdpProxyInterfaceList
                                            .getOrDefault(Integer.valueOf(userId), null);
                    try {
                        try {
                            iNetworkFilterProxy.stopProxyServer();
                        } catch (RemoteException unused) {
                            Log.e(
                                    "knoxNwFilter-KnoxNetworkFilterService",
                                    "handleStopFilteringOperation:dns:error occured while"
                                        + " communicating to remote service");
                        }
                    } catch (NullPointerException unused2) {
                        Log.e(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "handleStopFilteringOperation:dns:error occured remote service"
                                    + " null");
                    }
                    knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
                    KnoxNetworkFilterFirewall.flushRedirectDnsQueryRules(userId);
                    try {
                        try {
                            iNetworkFilterProxy2.stopProxyServer();
                        } catch (RemoteException unused3) {
                            Log.e(
                                    "knoxNwFilter-KnoxNetworkFilterService",
                                    "handleStopFilteringOperation:tcp:error occured while"
                                        + " communicating to remote service");
                        }
                    } catch (NullPointerException unused4) {
                        Log.e(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "handleStopFilteringOperation:tcp:error occured remote service"
                                    + " null");
                    }
                    knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
                    KnoxNetworkFilterFirewall.flushAppGeneratedRedirectTcpConnRules(userId);
                    try {
                        try {
                            iNetworkFilterProxy3.stopProxyServer();
                        } catch (RemoteException unused5) {
                            Log.e(
                                    "knoxNwFilter-KnoxNetworkFilterService",
                                    "handleStopFilteringOperation:udp:error occured while"
                                        + " communicating to remote service");
                        }
                    } catch (NullPointerException unused6) {
                        Log.e(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "handleStopFilteringOperation:udp:error occured remote service"
                                    + " null");
                    }
                    knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
                    KnoxNetworkFilterFirewall.flushUdpPacketExemptRules(userId, 2);
                    knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
                    KnoxNetworkFilterFirewall.flushAppGeneratedRedirectUdpConnRules(userId);
                    try {
                        iNetworkFilterProxy.stopV6ProxyServer();
                    } catch (RemoteException unused7) {
                        Log.e(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "handleStopFilteringOperation:dns:v6:error occured while"
                                    + " communicating to remote service");
                    } catch (NullPointerException unused8) {
                        Log.e(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "handleStopFilteringOperation:dns:v6:error occured remote service"
                                    + " null");
                    }
                    knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
                    KnoxNetworkFilterFirewall.flushRedirectV6DnsQueryRules(userId);
                    try {
                        try {
                            iNetworkFilterProxy2.stopV6ProxyServer();
                        } catch (RemoteException unused9) {
                            Log.e(
                                    "knoxNwFilter-KnoxNetworkFilterService",
                                    "handleStopFilteringOperation:tcp:V6:error occured while"
                                        + " communicating to remote service");
                        }
                    } catch (NullPointerException unused10) {
                        Log.e(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "handleStopFilteringOperation:tcp:V6:error occured remote service"
                                    + " null");
                    }
                    knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
                    KnoxNetworkFilterFirewall.flushAppGeneratedRedirectV6TcpConnRules(userId);
                    try {
                        try {
                            iNetworkFilterProxy3.stopV6ProxyServer();
                        } catch (RemoteException unused11) {
                            Log.e(
                                    "knoxNwFilter-KnoxNetworkFilterService",
                                    "handleStopFilteringOperation:udp:V6:error occured while"
                                        + " communicating to remote service");
                        }
                    } catch (NullPointerException unused12) {
                        Log.e(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "handleStopFilteringOperation:udp:V6:error occured remote service"
                                    + " null");
                    }
                    knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
                    KnoxNetworkFilterFirewall.flushUdpPacketExemptRules(userId, 10);
                    knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
                    KnoxNetworkFilterFirewall.flushAppGeneratedRedirectV6UdpConnRules(userId);
                    try {
                        try {
                            iNetworkFilterProxy2.stopHttpProxyServer();
                            strArr = iNetworkFilterProxy2.getBrowserAppList();
                            knoxNetworkFilterService
                                    .getOemNetdService()
                                    .setHttpProxyPort(userId, -1);
                        } catch (RemoteException unused13) {
                            Log.e(
                                    "knoxNwFilter-KnoxNetworkFilterService",
                                    "handleStopFilteringOperation:tcp:http:error occured while"
                                        + " communicating to remote service");
                        }
                    } catch (NullPointerException unused14) {
                        Log.e(
                                "knoxNwFilter-KnoxNetworkFilterService",
                                "handleStopFilteringOperation:tcp:http:error occured remote service"
                                    + " null");
                    }
                    knoxNetworkFilterService.mKnoxNwFilterHelper.applyHttpProxyConfiguration(
                            string, strArr, false, userId);
                    knoxNetworkFilterService.mKnoxNwFilterFw.getClass();
                    KnoxNetworkFilterFirewall.flushTcpRedirectRulesForCaptivePortal(userId);
                    knoxNetworkFilterService.mKnoxNwFilterHelper
                            .addOrRemoveAppsFromBatteryOptimization(
                                    uIDForPackage,
                                    "com.samsung.android.knox.app.networkfilter",
                                    false);
                    knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                    knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                    KnoxNetworkFilterProfileInfo profileEntry2 =
                            KnoxNetworkFilterProfileInfo.getProfileEntry(string);
                    if (profileEntry2 != null) {
                        profileEntry2.mState = 5;
                    }
                    knoxNetworkFilterService.sendStatusIntent(userId, 2, 0, packageNameForUid);
                    knoxNetworkFilterService.disableNetdFlags();
                    knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                    int adminIdForUser =
                            knoxNetworkFilterService.mKnoxNwFilterHelper.getAdminIdForUser(userId);
                    knoxNetworkFilterService.mKnoxNwFilterHelper.getClass();
                    knoxNetworkFilterService.sendEMMStatusIntent(
                            userId,
                            2,
                            KnoxNetworkFilterHelper.getPackageNameForUid(adminIdForUser));
                } catch (RemoteException unused15) {
                    Log.e(
                            "knoxNwFilter-KnoxNetworkFilterService",
                            "handleStopFilteringOperation:error occured while communicating to"
                                + " remote oemnetd service");
                    knoxNetworkFilterService.sendStatusIntent(userId, 2, -8, packageNameForUid);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public KnoxNetworkFilterService(Context context) {
        KnoxNetworkFilterValidation knoxNetworkFilterValidation;
        KnoxNetworkFilterFirewall knoxNetworkFilterFirewall;
        new ArraySet();
        this.mNwFilterProxyAppId = -1;
        this.mContext = context;
        KnoxNetworkFilterValidation knoxNetworkFilterValidation2 =
                KnoxNetworkFilterValidation.mKnoxNwFilterApiValidation;
        synchronized (KnoxNetworkFilterValidation.class) {
            try {
                if (KnoxNetworkFilterValidation.mKnoxNwFilterApiValidation == null) {
                    KnoxNetworkFilterValidation knoxNetworkFilterValidation3 =
                            new KnoxNetworkFilterValidation();
                    knoxNetworkFilterValidation3.mKnoxNwFilterHelper =
                            KnoxNetworkFilterHelper.getInstance(context);
                    KnoxNetworkFilterValidation.mKnoxNwFilterApiValidation =
                            knoxNetworkFilterValidation3;
                }
                knoxNetworkFilterValidation =
                        KnoxNetworkFilterValidation.mKnoxNwFilterApiValidation;
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mKnoxNwFilterValidation = knoxNetworkFilterValidation;
        KnoxNetworkFilterHelper knoxNetworkFilterHelper =
                KnoxNetworkFilterHelper.getInstance(context);
        this.mKnoxNwFilterHelper = knoxNetworkFilterHelper;
        this.mEdm = EnterpriseDeviceManager.getInstance(context);
        this.mCm = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        this.mNetIdManager = new KnoxNetIdManager();
        this.mDnsResolverAdapter = DnsResolverAdapter.getInstance();
        synchronized (KnoxNetworkFilterFirewall.class) {
            try {
                if (KnoxNetworkFilterFirewall.mKnoxNwFilterFw == null) {
                    KnoxNetworkFilterFirewall.mKnoxNwFilterFw = new KnoxNetworkFilterFirewall();
                }
                knoxNetworkFilterFirewall = KnoxNetworkFilterFirewall.mKnoxNwFilterFw;
            } catch (Throwable th2) {
                throw th2;
            }
        }
        this.mKnoxNwFilterFw = knoxNetworkFilterFirewall;
        this.mDefaultDnsConnectionList = new HashMap();
        this.mDefaultTcpConnectionList = new HashMap();
        this.mDefaultUdpConnectionList = new HashMap();
        this.mDefaultDnsProxyInterfaceList = new HashMap();
        this.mDefaultTcpProxyInterfaceList = new HashMap();
        this.mDefaultUdpProxyInterfaceList = new HashMap();
        this.mNetworkCallbackList = new HashMap();
        this.mLocalProxyStatus = new ArrayList();
        HandlerThread handlerThread = new HandlerThread("KnoxNwFilterHandler", 10);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new KnoxNwFilterHandler(this.mHandlerThread.getLooper());
        ArrayList dataByFields =
                knoxNetworkFilterHelper.mEDM.getDataByFields("NwFilterService", null, null, null);
        if (dataByFields.size() > 0) {
            Iterator it = dataByFields.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                String asString = contentValues.getAsString("profileName");
                String asString2 = contentValues.getAsString("profileConfig");
                String asString3 = contentValues.getAsString("pkgName");
                int intValue = contentValues.getAsInteger("pkgUid").intValue();
                if (!KnoxNetworkFilterProfileInfo.containsProfileEntry(asString)) {
                    KnoxNetworkFilterProfileInfo knoxNetworkFilterProfileInfo =
                            new KnoxNetworkFilterProfileInfo();
                    knoxNetworkFilterProfileInfo.mProfileName = asString;
                    knoxNetworkFilterProfileInfo.mRulesConfig = asString2;
                    knoxNetworkFilterProfileInfo.mPackageName = asString3;
                    knoxNetworkFilterProfileInfo.mPackageUid = intValue;
                    synchronized (KnoxNetworkFilterProfileInfo.class) {
                        if (asString != null) {}
                    }
                }
            }
        }
        ArrayList dataByFields2 =
                knoxNetworkFilterHelper.mEDM.getDataByFields(
                        "UnManagedNwFilterMgr", null, null, null);
        try {
            if (dataByFields2.size() > 0) {
                Iterator it2 = dataByFields2.iterator();
                while (it2.hasNext()) {
                    ContentValues contentValues2 = (ContentValues) it2.next();
                    int userId =
                            UserHandle.getUserId(contentValues2.getAsInteger("pkgUid").intValue());
                    byte[] asByteArray = contentValues2.getAsByteArray("bundleInfo");
                    Parcel obtain = Parcel.obtain();
                    obtain.unmarshall(asByteArray, 0, asByteArray.length);
                    obtain.setDataPosition(0);
                    Bundle readBundle = obtain.readBundle();
                    obtain.recycle();
                    Log.d(
                            "knoxNwFilter-KnoxNetworkFilterHelper",
                            "updateBundleInfoToLocalCache: configViewActivity: "
                                    + readBundle.getString("ConfigViewer", null)
                                    + " unEnrollViewActivity: "
                                    + readBundle.getString("UnEnrollViewer", null));
                    if (knoxNetworkFilterHelper.mBundleInfo.containsKey(Integer.valueOf(userId))) {
                        knoxNetworkFilterHelper.mBundleInfo.remove(Integer.valueOf(userId));
                    }
                    knoxNetworkFilterHelper.mBundleInfo.put(Integer.valueOf(userId), readBundle);
                }
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(" updateBundleInfoToLocalCache error: "),
                    "knoxNwFilter-KnoxNetworkFilterHelper");
        }
        initializeModules();
    }

    public static IActivityManager getAMSInstance() {
        return IActivityManager.Stub.asInterface(ServiceManager.getService("activity"));
    }

    public final void bindInternalProxyServices(int i) {
        if (!this.mDefaultDnsConnectionList.containsKey(Integer.valueOf(i))) {
            DefaultDnsConnection defaultDnsConnection = new DefaultDnsConnection(this, i, 0);
            defaultDnsConnection.countDownLatch = new CountDownLatch(1);
            Intent intent = new Intent();
            intent.setClassName(
                    "com.samsung.android.knox.app.networkfilter",
                    "com.samsung.android.knox.app.networkfilter.dns.DefaultDnsProxyService");
            if (this.mContext.bindServiceAsUser(
                            intent, defaultDnsConnection, 1073741829, new UserHandle(i))
                    && defaultDnsConnection.countDownLatch.await(30000L, TimeUnit.MILLISECONDS)) {
                Log.d(
                        "knoxNwFilter-KnoxNetworkFilterService",
                        "binding to default dns proxy service is success");
                this.mDefaultDnsConnectionList.put(Integer.valueOf(i), defaultDnsConnection);
            }
            defaultDnsConnection.countDownLatch = null;
        }
        if (!this.mDefaultTcpConnectionList.containsKey(Integer.valueOf(i))) {
            DefaultDnsConnection defaultDnsConnection2 = new DefaultDnsConnection(this, i, 1);
            defaultDnsConnection2.countDownLatch = new CountDownLatch(1);
            Intent intent2 = new Intent();
            intent2.setClassName(
                    "com.samsung.android.knox.app.networkfilter",
                    "com.samsung.android.knox.app.networkfilter.tcp.DefaultTcpProxyService");
            if (this.mContext.bindServiceAsUser(
                            intent2, defaultDnsConnection2, 1073741829, new UserHandle(i))
                    && defaultDnsConnection2.countDownLatch.await(30000L, TimeUnit.MILLISECONDS)) {
                Log.d(
                        "knoxNwFilter-KnoxNetworkFilterService",
                        "binding to default tcp proxy service is success");
                this.mDefaultTcpConnectionList.put(Integer.valueOf(i), defaultDnsConnection2);
            }
            defaultDnsConnection2.countDownLatch = null;
        }
        if (this.mDefaultUdpConnectionList.containsKey(Integer.valueOf(i))) {
            return;
        }
        DefaultDnsConnection defaultDnsConnection3 = new DefaultDnsConnection(this, i, 2);
        defaultDnsConnection3.countDownLatch = new CountDownLatch(1);
        Intent intent3 = new Intent();
        intent3.setClassName(
                "com.samsung.android.knox.app.networkfilter",
                "com.samsung.android.knox.app.networkfilter.udp.DefaultUdpProxyService");
        if (this.mContext.bindServiceAsUser(
                        intent3, defaultDnsConnection3, 1073741829, new UserHandle(i))
                && defaultDnsConnection3.countDownLatch.await(30000L, TimeUnit.MILLISECONDS)) {
            Log.d(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "binding to default udp proxy service is success");
            this.mDefaultUdpConnectionList.put(Integer.valueOf(i), defaultDnsConnection3);
        }
        defaultDnsConnection3.countDownLatch = null;
    }

    public final void checkCallingUidPermission() {
        this.mContext.enforceCallingOrSelfPermission(
                "com.samsung.android.knox.permission.KNOX_NETWORK_FILTER_SERVICE_PROVIDER",
                " Permission not granted");
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        this.mKnoxNwFilterHelper.getClass();
        String packageNameForUid = KnoxNetworkFilterHelper.getPackageNameForUid(callingUid);
        boolean isAppRegistered =
                this.mKnoxNwFilterHelper.isAppRegistered(
                        userId,
                        packageNameForUid,
                        this.mKnoxNwFilterHelper.getSignature(userId, packageNameForUid));
        if (this.mKnoxNwFilterHelper.isAppAuthorized(callingUid, packageNameForUid)
                || isAppRegistered) {
            return;
        }
        Log.e(
                "knoxNwFilter-KnoxNetworkFilterService",
                "failed due to caller not registered or authorized");
        throw new SecurityException("failed due to caller not registered");
    }

    public final void checkCallingUidPermissionEMM(ContextInfo contextInfo) {
        this.mEdm.enforceDoPoOnlyPermissionByContext(
                contextInfo,
                new ArrayList(
                        Arrays.asList(
                                "com.samsung.android.knox.permission.KNOX_NETWORK_FILTER_MGMT")));
    }

    public final void connectNativeNetdService() {
        boolean z;
        INetd asInterface;
        try {
            asInterface = INetd.Stub.asInterface(ServiceManager.getService("netd"));
            this.mNetdService = asInterface;
        } catch (RemoteException unused) {
            this.mNetdService = null;
            z = false;
        }
        if (asInterface == null) {
            return;
        }
        z = asInterface.isAlive();
        if (z) {
            return;
        }
        Log.e("knoxNwFilter-KnoxNetworkFilterService", "Can't connect to NativeNetdService netd");
    }

    public final void disableNetdFlags() {
        this.mKnoxNwFilterHelper.getClass();
        Iterator it = ((ArrayList) KnoxNetworkFilterHelper.getProfileList()).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            KnoxNetworkFilterProfileInfo profileEntry =
                    KnoxNetworkFilterProfileInfo.getProfileEntry(str);
            if (profileEntry != null && profileEntry.mState == 3) {
                AudioDeviceInventory$$ExternalSyntheticOutline0.m(
                        "profile ",
                        str,
                        " is running, so skipping disabling the netd flags",
                        "knoxNwFilter-KnoxNetworkFilterService");
                return;
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Log.i(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "Disabling all netd flags, since the profile list is empty");
            SystemProperties.set("net.redirect_socket_calls.hooked", "false");
            getOemNetdService().enableIpOptionModification(false);
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final synchronized void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println(
                    "KnoxNetworkFilterProfileInfo: Permission Denial: can't dump PersonaManager"
                        + " from from pid="
                            + Binder.getCallingPid()
                            + ", uid="
                            + Binder.getCallingUid()
                            + " without permission android.permission.DUMP");
            return;
        }
        printWriter.println(
                "KnoxNetworkFilterProfileInfo: The profile info being printed at time "
                        + new Date(System.currentTimeMillis())
                        + "\n");
        Collection<KnoxNetworkFilterProfileInfo> values =
                KnoxNetworkFilterProfileInfo.mProfileInfomap.values();
        if (values != null) {
            for (KnoxNetworkFilterProfileInfo knoxNetworkFilterProfileInfo : values) {
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: profileName: "
                                + knoxNetworkFilterProfileInfo.mProfileName
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: RulesConfig: "
                                + knoxNetworkFilterProfileInfo.mRulesConfig
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: socketConfig "
                                + knoxNetworkFilterProfileInfo.mSocketConfig
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: packageName "
                                + knoxNetworkFilterProfileInfo.mPackageName
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: packageUid "
                                + knoxNetworkFilterProfileInfo.mPackageUid
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: state: "
                                + knoxNetworkFilterProfileInfo.mState
                                + "\n");
                Iterator it = knoxNetworkFilterProfileInfo.mVpnClientUidList.iterator();
                while (it.hasNext()) {
                    printWriter.print(
                            "KnoxNetworkFilterProfileInfo: vpn client uid: "
                                    + ((Integer) it.next()).intValue()
                                    + "\n");
                }
                printWriter.print("\n");
            }
        }
        Iterator it2 =
                ((ArrayList) this.mKnoxNwFilterHelper.getRegisterInfoFromDatabase()).iterator();
        while (it2.hasNext()) {
            printWriter.print(
                    "KnoxNetworkFilterProfileInfo: register app info: "
                            + ((String) it2.next())
                            + "\n");
        }
        printWriter.print("\n");
        Iterator it3 =
                ((ArrayList) this.mKnoxNwFilterHelper.getAuthorizedInfoFromDatabase()).iterator();
        while (it3.hasNext()) {
            printWriter.print(
                    "KnoxNetworkFilterProfileInfo: unManaged app info: "
                            + ((String) it3.next())
                            + "\n");
        }
        printWriter.print("\n");
        try {
            this.mKnoxNwFilterHelper.getClass();
            Iterator it4 = ((ArrayList) KnoxNetworkFilterHelper.getUserIdList()).iterator();
            while (it4.hasNext()) {
                int intValue = ((Integer) it4.next()).intValue();
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: netId value is "
                                + getOemNetdService().getNwFilterNetId(intValue)
                                + " for userId "
                                + intValue
                                + "\n");
            }
        } catch (RemoteException | NullPointerException unused) {
        }
        printWriter.print("\n");
        List<ActivityManager.ProcessErrorStateInfo> processesInErrorState =
                getAMSInstance().getProcessesInErrorState();
        if (processesInErrorState != null) {
            for (ActivityManager.ProcessErrorStateInfo processErrorStateInfo :
                    processesInErrorState) {
                String str = processErrorStateInfo.processName;
                if (str != null
                        && str.equalsIgnoreCase("com.samsung.android.knox.app.networkfilter")) {
                    printWriter.print(
                            "KnoxNetworkFilterProfileInfo: local nwfilter proxy apk error condition"
                                + " is "
                                    + processErrorStateInfo.condition
                                    + "\n");
                    printWriter.print(
                            "KnoxNetworkFilterProfileInfo: local nwfilter proxy apk error longMsg"
                                + " is "
                                    + processErrorStateInfo.longMsg
                                    + "\n");
                    printWriter.print(
                            "KnoxNetworkFilterProfileInfo: local nwfilter proxy apk error shortMsg"
                                + " is "
                                    + processErrorStateInfo.shortMsg
                                    + "\n");
                    printWriter.print(
                            "KnoxNetworkFilterProfileInfo: local nwfilter proxy apk error"
                                + " stackTrace is "
                                    + processErrorStateInfo.stackTrace
                                    + "\n");
                    printWriter.print(
                            "KnoxNetworkFilterProfileInfo: local nwfilter proxy apk error pid is "
                                    + processErrorStateInfo.pid
                                    + "\n");
                    printWriter.print(
                            "KnoxNetworkFilterProfileInfo: local nwfilter proxy apk apk error uid"
                                + " is "
                                    + processErrorStateInfo.uid
                                    + "\n");
                }
            }
        }
        printWriter.print("\n");
        for (Integer num : this.mDefaultDnsProxyInterfaceList.keySet()) {
            int intValue2 = num.intValue();
            INetworkFilterProxy iNetworkFilterProxy =
                    (INetworkFilterProxy)
                            this.mDefaultDnsProxyInterfaceList.getOrDefault(num, null);
            if (iNetworkFilterProxy != null) {
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default DNS proxy server configured"
                            + " under user "
                                + intValue2
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default DNS proxy server address "
                                + iNetworkFilterProxy.getLocalProxyAddress()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default DNS proxy server port "
                                + iNetworkFilterProxy.getLocalProxyPort()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default DNS proxy server thread is"
                            + " active "
                                + iNetworkFilterProxy.isProxyThreadRunning()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default DNS proxy server thread state "
                                + iNetworkFilterProxy.getProxythreadStatus()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default DNS proxy server alive "
                                + iNetworkFilterProxy.isProxyThreadAlive()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: V6:local default DNS proxy server address "
                                + iNetworkFilterProxy.getV6LocalProxyAddress()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: V6:local default DNS proxy server port "
                                + iNetworkFilterProxy.getV6LocalProxyPort()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: V6:local default DNS proxy server thread is"
                            + " active "
                                + iNetworkFilterProxy.isV6ProxyThreadRunning()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: V6:local default DNS proxy server thread"
                            + " state "
                                + iNetworkFilterProxy.getV6ProxythreadStatus()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: V6:local default DNS proxy server alive "
                                + iNetworkFilterProxy.isV6ProxyThreadAlive()
                                + "\n");
            }
            printWriter.print("\n");
        }
        for (Integer num2 : this.mDefaultTcpProxyInterfaceList.keySet()) {
            int intValue3 = num2.intValue();
            INetworkFilterProxy iNetworkFilterProxy2 =
                    (INetworkFilterProxy)
                            this.mDefaultTcpProxyInterfaceList.getOrDefault(num2, null);
            if (iNetworkFilterProxy2 != null) {
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default tcp proxy server configured"
                            + " under user "
                                + intValue3
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default tcp proxy server address "
                                + iNetworkFilterProxy2.getLocalProxyAddress()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default tcp proxy server port "
                                + iNetworkFilterProxy2.getLocalProxyPort()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default tcp proxy server thread is"
                            + " active "
                                + iNetworkFilterProxy2.isProxyThreadRunning()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default tcp proxy server thread state "
                                + iNetworkFilterProxy2.getProxythreadStatus()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default tcp proxy server alive "
                                + iNetworkFilterProxy2.isProxyThreadAlive()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: V6:local default tcp proxy server address "
                                + iNetworkFilterProxy2.getV6LocalProxyAddress()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: V6:local default tcp proxy server port "
                                + iNetworkFilterProxy2.getV6LocalProxyPort()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: V6:local default tcp proxy server thread is"
                            + " active "
                                + iNetworkFilterProxy2.isV6ProxyThreadRunning()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: V6:local default tcp proxy server thread"
                            + " state "
                                + iNetworkFilterProxy2.getV6ProxythreadStatus()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: V6:local default tcp proxy server alive "
                                + iNetworkFilterProxy2.isV6ProxyThreadAlive()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: http:local default tcp proxy server address "
                                + iNetworkFilterProxy2.getHttpLocalProxyAddress()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: http:local default tcp proxy server port "
                                + iNetworkFilterProxy2.getHttpLocalProxyPort()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: http:local default tcp proxy server thread"
                            + " is active "
                                + iNetworkFilterProxy2.isHttpProxyThreadRunning()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: http:local default tcp proxy server thread"
                            + " state "
                                + iNetworkFilterProxy2.getHttpProxythreadStatus()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: http:local default tcp proxy server alive "
                                + iNetworkFilterProxy2.isHttpProxyThreadAlive()
                                + "\n");
            }
            printWriter.print("\n");
        }
        for (Integer num3 : this.mDefaultUdpProxyInterfaceList.keySet()) {
            int intValue4 = num3.intValue();
            INetworkFilterProxy iNetworkFilterProxy3 =
                    (INetworkFilterProxy)
                            this.mDefaultUdpProxyInterfaceList.getOrDefault(num3, null);
            if (iNetworkFilterProxy3 != null) {
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default udp proxy server configured"
                            + " under user "
                                + intValue4
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default udp proxy server address "
                                + iNetworkFilterProxy3.getLocalProxyAddress()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default udp proxy server port "
                                + iNetworkFilterProxy3.getLocalProxyPort()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default udp proxy server thread is"
                            + " active "
                                + iNetworkFilterProxy3.isProxyThreadRunning()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default udp proxy server thread state "
                                + iNetworkFilterProxy3.getProxythreadStatus()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: local default udp proxy server alive "
                                + iNetworkFilterProxy3.isProxyThreadAlive()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: V6:local default udp proxy server address "
                                + iNetworkFilterProxy3.getV6LocalProxyAddress()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: V6:local default udp proxy server port "
                                + iNetworkFilterProxy3.getV6LocalProxyPort()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: V6:local default udp proxy server thread is"
                            + " active "
                                + iNetworkFilterProxy3.isV6ProxyThreadRunning()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: V6:local default udp proxy server thread"
                            + " state "
                                + iNetworkFilterProxy3.getV6ProxythreadStatus()
                                + "\n");
                printWriter.print(
                        "KnoxNetworkFilterProfileInfo: V6:local default udp proxy server alive "
                                + iNetworkFilterProxy3.isV6ProxyThreadAlive()
                                + "\n");
            }
            printWriter.print("\n");
        }
        Iterator it5 = ((ArrayList) this.mLocalProxyStatus).iterator();
        while (it5.hasNext()) {
            printWriter.print("KnoxNetworkFilterProfileInfo: " + ((String) it5.next()) + "\n");
        }
        printWriter.print("\n");
        printWriter.print(
                "KnoxNetworkFilterProfileInfo: version "
                        + this.mKnoxNwFilterHelper.getVersionCode(
                                0, "com.samsung.android.knox.app.networkfilter")
                        + "\n");
    }

    public final void exemptUidFromNwFilterRange(int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new UidRangeParcel(i, i2));
            getOemNetdService()
                    .exemptUidFromNwFilterRange(
                            (UidRangeParcel[])
                                    arrayList.toArray(new UidRangeParcel[arrayList.size()]));
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (i == i2) {
            this.mKnoxNwFilterFw.getClass();
            String str = UserHandle.getUserId(i) + "_knox_nwfilter_app_act";
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            String m = VibrationParam$1$$ExternalSyntheticOutline0.m(i, " -m owner --uid-owner ");
            KnoxNetworkFilterFirewall.IpRestoreActionType ipRestoreActionType =
                    KnoxNetworkFilterFirewall.IpRestoreActionType.INSERT;
            arrayList2.add(
                    new KnoxNetworkFilterFirewall.IpRestoreParam(
                            str, m, "ACCEPT", "", ipRestoreActionType));
            arrayList3.add(
                    new KnoxNetworkFilterFirewall.IpRestoreParam(
                            str,
                            VibrationParam$1$$ExternalSyntheticOutline0.m(
                                    i, " -m owner --uid-owner "),
                            "ACCEPT",
                            "",
                            ipRestoreActionType));
            arrayList4.add(
                    new KnoxNetworkFilterFirewall.IpRestoreParam(
                            str,
                            VibrationParam$1$$ExternalSyntheticOutline0.m(
                                    i, " -m owner --uid-owner "),
                            "ACCEPT",
                            "",
                            ipRestoreActionType));
            KnoxNetworkFilterFirewall.insertRules(4, "*nat", null, arrayList2, true);
            KnoxNetworkFilterFirewall.insertRules(46, "*filter", null, arrayList3, true);
            KnoxNetworkFilterFirewall.insertRules(6, "*mangle", null, arrayList4, true);
        }
    }

    public final synchronized List getAllProfiles() {
        int callingUid;
        checkCallingUidPermission();
        KnoxNetworkFilterHelper knoxNetworkFilterHelper = this.mKnoxNwFilterHelper;
        callingUid = Binder.getCallingUid();
        knoxNetworkFilterHelper.getClass();
        return KnoxNetworkFilterHelper.getProfileListByVendor(callingUid);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0037 A[Catch: all -> 0x004c, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0004, B:7:0x0018, B:10:0x001f, B:12:0x002b, B:16:0x0037, B:20:0x004e, B:24:0x0059, B:25:0x005e), top: B:3:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004e A[Catch: all -> 0x004c, TRY_ENTER, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0004, B:7:0x0018, B:10:0x001f, B:12:0x002b, B:16:0x0037, B:20:0x004e, B:24:0x0059, B:25:0x005e), top: B:3:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized java.lang.String getConfig(java.lang.String r5) {
        /*
            r4 = this;
            java.lang.String r0 = "getConfig validation failed with error code "
            monitor-enter(r4)
            r4.checkCallingUidPermission()     // Catch: java.lang.Throwable -> L4c
            com.android.server.enterprise.filter.KnoxNetworkFilterValidation r1 = r4.mKnoxNwFilterValidation     // Catch: java.lang.Throwable -> L4c
            r1.getClass()     // Catch: java.lang.Throwable -> L4c
            int r2 = android.os.Binder.getCallingUid()     // Catch: java.lang.Throwable -> L4c
            int r2 = r1.getInstanceValidation(r2)     // Catch: java.lang.Throwable -> L4c
            if (r2 != 0) goto L59
            if (r5 == 0) goto L34
            boolean r3 = r5.isEmpty()     // Catch: java.lang.Throwable -> L4c
            if (r3 == 0) goto L1f
            goto L34
        L1f:
            com.android.server.enterprise.filter.KnoxNetworkFilterHelper r1 = r1.mKnoxNwFilterHelper     // Catch: java.lang.Throwable -> L4c
            r1.getClass()     // Catch: java.lang.Throwable -> L4c
            int r1 = com.android.server.enterprise.filter.KnoxNetworkFilterHelper.getVendorUidByProfile(r5)     // Catch: java.lang.Throwable -> L4c
            r3 = -1
            if (r1 == r3) goto L35
            int r3 = android.os.Binder.getCallingUid()     // Catch: java.lang.Throwable -> L4c
            if (r1 == r3) goto L35
            r2 = -12
            goto L35
        L34:
            r2 = -7
        L35:
            if (r2 == 0) goto L4e
            java.lang.String r5 = "knoxNwFilter-KnoxNetworkFilterService"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4c
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L4c
            r1.append(r2)     // Catch: java.lang.Throwable -> L4c
            java.lang.String r0 = r1.toString()     // Catch: java.lang.Throwable -> L4c
            android.util.Log.e(r5, r0)     // Catch: java.lang.Throwable -> L4c
            monitor-exit(r4)
            r4 = 0
            return r4
        L4c:
            r5 = move-exception
            goto L5f
        L4e:
            com.android.server.enterprise.filter.KnoxNetworkFilterHelper r0 = r4.mKnoxNwFilterHelper     // Catch: java.lang.Throwable -> L4c
            r0.getClass()     // Catch: java.lang.Throwable -> L4c
            java.lang.String r5 = com.android.server.enterprise.filter.KnoxNetworkFilterHelper.getRulesConfig(r5)     // Catch: java.lang.Throwable -> L4c
            monitor-exit(r4)
            return r5
        L59:
            java.lang.SecurityException r5 = new java.lang.SecurityException     // Catch: java.lang.Throwable -> L4c
            r5.<init>()     // Catch: java.lang.Throwable -> L4c
            throw r5     // Catch: java.lang.Throwable -> L4c
        L5f:
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.filter.KnoxNetworkFilterService.getConfig(java.lang.String):java.lang.String");
    }

    public final synchronized String getConfigByUserId(int i) {
        String str;
        try {
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(callingUid);
            this.mKnoxNwFilterHelper.getClass();
            String packageNameForUid = KnoxNetworkFilterHelper.getPackageNameForUid(callingUid);
            if (!packageNameForUid.equalsIgnoreCase("com.android.vpndialogs")) {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterService",
                        "Error during getConfigByUserId API validation as the caller package name"
                            + " validation failed");
                throw new SecurityException();
            }
            this.mKnoxNwFilterHelper.getClass();
            if (!KnoxNetworkFilterHelper.checkIfPlatformSigned(userId, packageNameForUid)) {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterService",
                        "Error during getConfigByUserId API validation as the caller is not"
                            + " platform signed");
                throw new SecurityException();
            }
            this.mKnoxNwFilterHelper.getClass();
            String profilebyUserId = KnoxNetworkFilterHelper.getProfilebyUserId(userId);
            if (profilebyUserId != null) {
                this.mKnoxNwFilterHelper.getClass();
                str = KnoxNetworkFilterHelper.getRulesConfig(profilebyUserId);
            } else {
                str = null;
            }
            if (str == null) {
                str = "";
            }
        } catch (Throwable th) {
            throw th;
        }
        return str;
    }

    public final synchronized int getInstanceValidation() {
        return this.mKnoxNwFilterValidation.getInstanceValidation(Binder.getCallingUid());
    }

    public final int getKnoxNwFilterHttpProxyPort(int i, String str) {
        KnoxNetworkFilterProfileInfo profileEntry;
        this.mKnoxNwFilterHelper.getClass();
        if (!((ArrayList) KnoxNetworkFilterHelper.getProfileList()).isEmpty()) {
            this.mKnoxNwFilterHelper.getClass();
            String profilebyUserId = KnoxNetworkFilterHelper.getProfilebyUserId(i);
            if (profilebyUserId != null
                    && (profileEntry =
                                    KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId))
                            != null
                    && profileEntry.mDNSCacheStatus == 1
                    && profileEntry.mBrowserAppList.contains(str)) {
                try {
                    return ((INetworkFilterProxy)
                                    this.mDefaultTcpProxyInterfaceList.getOrDefault(
                                            Integer.valueOf(i), null))
                            .getHttpLocalProxyPort();
                } catch (RemoteException | NullPointerException unused) {
                }
            }
        }
        return -1;
    }

    public final IOemNetd getOemNetdService() {
        IOemNetd iOemNetd = this.mOemNetdService;
        if (iOemNetd != null) {
            return iOemNetd;
        }
        if (this.mNetdService == null) {
            connectNativeNetdService();
        }
        INetd iNetd = this.mNetdService;
        if (iNetd != null) {
            try {
                this.mOemNetdService = IOemNetd.Stub.asInterface(iNetd.getOemNetd());
            } catch (RemoteException e) {
                ActivityManagerService$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("Failed to get OemNetd listener "),
                        "knoxNwFilter-KnoxNetworkFilterService");
            }
        }
        return this.mOemNetdService;
    }

    public final String getPkgNameForTcpV4Port(int i) {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        String str = "";
        if (UserHandle.getAppId(callingUid) != this.mNwFilterProxyAppId) {
            Log.e(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "unauthorized caller for getPkgNameForTcpV4Port");
            return "";
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i2 = -1;
        try {
            try {
                try {
                    String networkFilterTcpV4Entry =
                            getOemNetdService().getNetworkFilterTcpV4Entry(userId, i);
                    if (networkFilterTcpV4Entry != null && !networkFilterTcpV4Entry.isEmpty()) {
                        i2 = Integer.parseInt(networkFilterTcpV4Entry.split("_")[0]);
                        str = AppGlobals.getPackageManager().getNameForUid(i2);
                    }
                } catch (RemoteException unused) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "RemoteException");
                } catch (SecurityException unused2) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "SecurityException");
                }
            } catch (IllegalArgumentException unused3) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "IllegalArgumentException");
            }
            if (DBG) {
                GestureWakeup$$ExternalSyntheticOutline0.m(
                        StorageManagerService$$ExternalSyntheticOutline0.m(
                                i2,
                                "getPkgNameForTcpV4Port value is ",
                                str,
                                " for uid ",
                                " port value is "),
                        i,
                        "knoxNwFilter-KnoxNetworkFilterService");
            }
            return str;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String getPkgNameForTcpV6Port(int i) {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        String str = "";
        if (UserHandle.getAppId(callingUid) != this.mNwFilterProxyAppId) {
            Log.e(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "unauthorized caller for getPkgNameForTcpV6Port");
            return "";
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i2 = -1;
        try {
            try {
                try {
                    String networkFilterTcpV6Entry =
                            getOemNetdService().getNetworkFilterTcpV6Entry(userId, i);
                    if (networkFilterTcpV6Entry != null && !networkFilterTcpV6Entry.isEmpty()) {
                        i2 = Integer.parseInt(networkFilterTcpV6Entry.split("_")[0]);
                        str = AppGlobals.getPackageManager().getNameForUid(i2);
                    }
                } catch (RemoteException unused) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "RemoteException");
                } catch (SecurityException unused2) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "SecurityException");
                }
            } catch (IllegalArgumentException unused3) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "IllegalArgumentException");
            }
            if (DBG) {
                GestureWakeup$$ExternalSyntheticOutline0.m(
                        StorageManagerService$$ExternalSyntheticOutline0.m(
                                i2,
                                "getPkgNameForTcpV6Port value is ",
                                str,
                                " for uid ",
                                " port value is "),
                        i,
                        "knoxNwFilter-KnoxNetworkFilterService");
            }
            return str;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final synchronized String getProfileForUser(int i) {
        String profilebyUserId;
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        this.mKnoxNwFilterHelper.getClass();
        String packageNameForUid = KnoxNetworkFilterHelper.getPackageNameForUid(callingUid);
        if (!packageNameForUid.equalsIgnoreCase("com.android.vpndialogs")) {
            Log.e(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "Error during getProfileForUser API validation as the caller package name"
                        + " validation failed");
            throw new SecurityException();
        }
        this.mKnoxNwFilterHelper.getClass();
        if (!KnoxNetworkFilterHelper.checkIfPlatformSigned(userId, packageNameForUid)) {
            Log.e(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "Error during getProfileForUser API validation as the caller is not platform"
                        + " signed");
            throw new SecurityException();
        }
        this.mKnoxNwFilterHelper.getClass();
        profilebyUserId = KnoxNetworkFilterHelper.getProfilebyUserId(userId);
        if (profilebyUserId == null) {
            profilebyUserId = "";
        }
        return profilebyUserId;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0037 A[Catch: all -> 0x004b, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0004, B:7:0x0018, B:10:0x001f, B:12:0x002b, B:16:0x0037, B:21:0x004d, B:27:0x005c, B:28:0x0061), top: B:3:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized int getProfileStatus(java.lang.String r5) {
        /*
            r4 = this;
            java.lang.String r0 = "getProfileStatus validation failed with error code "
            monitor-enter(r4)
            r4.checkCallingUidPermission()     // Catch: java.lang.Throwable -> L4b
            com.android.server.enterprise.filter.KnoxNetworkFilterValidation r1 = r4.mKnoxNwFilterValidation     // Catch: java.lang.Throwable -> L4b
            r1.getClass()     // Catch: java.lang.Throwable -> L4b
            int r2 = android.os.Binder.getCallingUid()     // Catch: java.lang.Throwable -> L4b
            int r2 = r1.getInstanceValidation(r2)     // Catch: java.lang.Throwable -> L4b
            if (r2 != 0) goto L5c
            if (r5 == 0) goto L34
            boolean r3 = r5.isEmpty()     // Catch: java.lang.Throwable -> L4b
            if (r3 == 0) goto L1f
            goto L34
        L1f:
            com.android.server.enterprise.filter.KnoxNetworkFilterHelper r1 = r1.mKnoxNwFilterHelper     // Catch: java.lang.Throwable -> L4b
            r1.getClass()     // Catch: java.lang.Throwable -> L4b
            int r1 = com.android.server.enterprise.filter.KnoxNetworkFilterHelper.getVendorUidByProfile(r5)     // Catch: java.lang.Throwable -> L4b
            r3 = -1
            if (r1 == r3) goto L35
            int r3 = android.os.Binder.getCallingUid()     // Catch: java.lang.Throwable -> L4b
            if (r1 == r3) goto L35
            r2 = -12
            goto L35
        L34:
            r2 = -7
        L35:
            if (r2 == 0) goto L4d
            java.lang.String r5 = "knoxNwFilter-KnoxNetworkFilterService"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4b
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L4b
            r1.append(r2)     // Catch: java.lang.Throwable -> L4b
            java.lang.String r0 = r1.toString()     // Catch: java.lang.Throwable -> L4b
            android.util.Log.e(r5, r0)     // Catch: java.lang.Throwable -> L4b
            monitor-exit(r4)
            return r2
        L4b:
            r5 = move-exception
            goto L62
        L4d:
            com.android.server.enterprise.filter.KnoxNetworkFilterHelper r0 = r4.mKnoxNwFilterHelper     // Catch: java.lang.Throwable -> L4b java.lang.NullPointerException -> L59
            r0.getClass()     // Catch: java.lang.Throwable -> L4b java.lang.NullPointerException -> L59
            com.android.server.enterprise.filter.KnoxNetworkFilterProfileInfo r5 = com.android.server.enterprise.filter.KnoxNetworkFilterProfileInfo.getProfileEntry(r5)     // Catch: java.lang.Throwable -> L4b java.lang.NullPointerException -> L59
            int r5 = r5.mState     // Catch: java.lang.Throwable -> L4b java.lang.NullPointerException -> L59
            goto L5a
        L59:
            r5 = -2
        L5a:
            monitor-exit(r4)
            return r5
        L5c:
            java.lang.SecurityException r5 = new java.lang.SecurityException     // Catch: java.lang.Throwable -> L4b
            r5.<init>()     // Catch: java.lang.Throwable -> L4b
            throw r5     // Catch: java.lang.Throwable -> L4b
        L62:
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.filter.KnoxNetworkFilterService.getProfileStatus(java.lang.String):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0037 A[Catch: all -> 0x004c, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0004, B:7:0x0018, B:10:0x001f, B:12:0x002b, B:16:0x0037, B:20:0x004e, B:24:0x0059, B:25:0x005e), top: B:3:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004e A[Catch: all -> 0x004c, TRY_ENTER, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0004, B:7:0x0018, B:10:0x001f, B:12:0x002b, B:16:0x0037, B:20:0x004e, B:24:0x0059, B:25:0x005e), top: B:3:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized java.lang.String getRegisteredListeners(java.lang.String r5) {
        /*
            r4 = this;
            java.lang.String r0 = "getRegisteredListeners validation failed with error code "
            monitor-enter(r4)
            r4.checkCallingUidPermission()     // Catch: java.lang.Throwable -> L4c
            com.android.server.enterprise.filter.KnoxNetworkFilterValidation r1 = r4.mKnoxNwFilterValidation     // Catch: java.lang.Throwable -> L4c
            r1.getClass()     // Catch: java.lang.Throwable -> L4c
            int r2 = android.os.Binder.getCallingUid()     // Catch: java.lang.Throwable -> L4c
            int r2 = r1.getInstanceValidation(r2)     // Catch: java.lang.Throwable -> L4c
            if (r2 != 0) goto L59
            if (r5 == 0) goto L34
            boolean r3 = r5.isEmpty()     // Catch: java.lang.Throwable -> L4c
            if (r3 == 0) goto L1f
            goto L34
        L1f:
            com.android.server.enterprise.filter.KnoxNetworkFilterHelper r1 = r1.mKnoxNwFilterHelper     // Catch: java.lang.Throwable -> L4c
            r1.getClass()     // Catch: java.lang.Throwable -> L4c
            int r1 = com.android.server.enterprise.filter.KnoxNetworkFilterHelper.getVendorUidByProfile(r5)     // Catch: java.lang.Throwable -> L4c
            r3 = -1
            if (r1 == r3) goto L35
            int r3 = android.os.Binder.getCallingUid()     // Catch: java.lang.Throwable -> L4c
            if (r1 == r3) goto L35
            r2 = -12
            goto L35
        L34:
            r2 = -7
        L35:
            if (r2 == 0) goto L4e
            java.lang.String r5 = "knoxNwFilter-KnoxNetworkFilterService"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4c
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L4c
            r1.append(r2)     // Catch: java.lang.Throwable -> L4c
            java.lang.String r0 = r1.toString()     // Catch: java.lang.Throwable -> L4c
            android.util.Log.e(r5, r0)     // Catch: java.lang.Throwable -> L4c
            monitor-exit(r4)
            r4 = 0
            return r4
        L4c:
            r5 = move-exception
            goto L5f
        L4e:
            com.android.server.enterprise.filter.KnoxNetworkFilterHelper r0 = r4.mKnoxNwFilterHelper     // Catch: java.lang.Throwable -> L4c
            r0.getClass()     // Catch: java.lang.Throwable -> L4c
            java.lang.String r5 = com.android.server.enterprise.filter.KnoxNetworkFilterHelper.retrieveListenersFromCache(r5)     // Catch: java.lang.Throwable -> L4c
            monitor-exit(r4)
            return r5
        L59:
            java.lang.SecurityException r5 = new java.lang.SecurityException     // Catch: java.lang.Throwable -> L4c
            r5.<init>()     // Catch: java.lang.Throwable -> L4c
            throw r5     // Catch: java.lang.Throwable -> L4c
        L5f:
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.filter.KnoxNetworkFilterService.getRegisteredListeners(java.lang.String):java.lang.String");
    }

    public final synchronized List getRegisteredPackageList(ContextInfo contextInfo) {
        checkCallingUidPermissionEMM(contextInfo);
        return this.mKnoxNwFilterHelper.getRegisteredAppsByAdminId(contextInfo.mCallerUid);
    }

    public final String getTcpV4PortInfo(int i) {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        String str = "";
        if (UserHandle.getAppId(callingUid) != this.mNwFilterProxyAppId) {
            Log.e(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "unauthorized caller for getTcpV4PortInfo");
            return "";
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = getOemNetdService().getNetworkFilterTcpV4Entry(userId, i);
            } catch (RemoteException unused) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "RemoteException");
            } catch (IllegalArgumentException unused2) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "IllegalArgumentException");
            } catch (SecurityException unused3) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "SecurityException");
            }
            if (DBG) {
                NetworkScoreService$$ExternalSyntheticOutline0.m(
                        i,
                        "portInfo value is ",
                        str,
                        " port value is ",
                        "knoxNwFilter-KnoxNetworkFilterService");
            }
            return str;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String getTcpV6PortInfo(int i) {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        String str = "";
        if (UserHandle.getAppId(callingUid) != this.mNwFilterProxyAppId) {
            Log.e(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "unauthorized caller for getTcpV6PortInfo ");
            return "";
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = getOemNetdService().getNetworkFilterTcpV6Entry(userId, i);
            } catch (RemoteException unused) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "RemoteException");
            } catch (IllegalArgumentException unused2) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "IllegalArgumentException");
            } catch (SecurityException unused3) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "SecurityException");
            }
            if (DBG) {
                NetworkScoreService$$ExternalSyntheticOutline0.m(
                        i,
                        "getTcpV6PortInfo value is ",
                        str,
                        " port value is ",
                        "knoxNwFilter-KnoxNetworkFilterService");
            }
            return str;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String getUdpV6PortInfo(int i) {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        String str = "";
        if (UserHandle.getAppId(callingUid) != this.mNwFilterProxyAppId) {
            Log.e(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "unauthorized caller for getUdpV6PortInfo");
            return "";
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = getOemNetdService().getNetworkFilterUdpV6Entry(userId, i);
            } catch (RemoteException unused) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "RemoteException");
            } catch (IllegalArgumentException unused2) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "IllegalArgumentException");
            } catch (SecurityException unused3) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "SecurityException");
            }
            if (DBG) {
                NetworkScoreService$$ExternalSyntheticOutline0.m(
                        i,
                        "getUdpV6PortInfo value is ",
                        str,
                        " port value is ",
                        "knoxNwFilter-KnoxNetworkFilterService");
            }
            return str;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final synchronized void handleVendorPackageUninstall(int i, String str, boolean z) {
        String[] strArr;
        try {
            int userId = UserHandle.getUserId(i);
            this.mKnoxNwFilterHelper.getClass();
            int uIDForPackage =
                    KnoxNetworkFilterHelper.getUIDForPackage(
                            userId, "com.samsung.android.knox.app.networkfilter");
            this.mKnoxNwFilterHelper.getClass();
            String profilebyUserId = KnoxNetworkFilterHelper.getProfilebyUserId(userId);
            if (profilebyUserId != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    getOemNetdService().enablePortInfoEntries(userId, 2, 6, false);
                    getOemNetdService().enablePortInfoEntries(userId, 10, 6, false);
                    getOemNetdService().enablePortInfoEntries(userId, 10, 17, false);
                    getOemNetdService().clearNetworkFilterEntries(userId);
                } catch (RemoteException unused) {
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                unregisterSystemDefaultNetworkCallback(userId);
                INetworkFilterProxy iNetworkFilterProxy =
                        (INetworkFilterProxy)
                                this.mDefaultDnsProxyInterfaceList.getOrDefault(
                                        Integer.valueOf(userId), null);
                INetworkFilterProxy iNetworkFilterProxy2 =
                        (INetworkFilterProxy)
                                this.mDefaultTcpProxyInterfaceList.getOrDefault(
                                        Integer.valueOf(userId), null);
                INetworkFilterProxy iNetworkFilterProxy3 =
                        (INetworkFilterProxy)
                                this.mDefaultUdpProxyInterfaceList.getOrDefault(
                                        Integer.valueOf(userId), null);
                try {
                    iNetworkFilterProxy.stopProxyServer();
                } catch (RemoteException | NullPointerException unused2) {
                }
                try {
                    iNetworkFilterProxy2.stopProxyServer();
                } catch (RemoteException | NullPointerException unused3) {
                }
                try {
                    iNetworkFilterProxy3.stopProxyServer();
                } catch (RemoteException | NullPointerException unused4) {
                }
                try {
                    iNetworkFilterProxy.stopV6ProxyServer();
                } catch (RemoteException | NullPointerException unused5) {
                }
                try {
                    iNetworkFilterProxy2.stopV6ProxyServer();
                } catch (RemoteException | NullPointerException unused6) {
                }
                try {
                    iNetworkFilterProxy3.stopV6ProxyServer();
                } catch (RemoteException | NullPointerException unused7) {
                }
                try {
                    iNetworkFilterProxy2.stopHttpProxyServer();
                    strArr = iNetworkFilterProxy2.getBrowserAppList();
                    try {
                        getOemNetdService().setHttpProxyPort(userId, -1);
                    } catch (RemoteException | NullPointerException unused8) {
                    }
                } catch (RemoteException | NullPointerException unused9) {
                    strArr = null;
                }
                this.mKnoxNwFilterHelper.applyHttpProxyConfiguration(
                        profilebyUserId, strArr, false, userId);
                this.mKnoxNwFilterFw.getClass();
                KnoxNetworkFilterFirewall.flushTcpRedirectRulesForCaptivePortal(userId);
                try {
                    releaseNwFilterNetId(getOemNetdService().getNwFilterNetId(userId));
                    getOemNetdService().removeUserFromNwFilterRange(userId);
                    getOemNetdService().setNwFilterNetId(userId, -1);
                    getOemNetdService().removeKnoxNwFilterProxyApp(uIDForPackage);
                } catch (RemoteException unused10) {
                }
                String str2 = KnoxNetworkFilterConstants.NW_FILTER_EXEMPT_LIST[0];
                this.mKnoxNwFilterHelper.getClass();
                int uIDForPackage2 = KnoxNetworkFilterHelper.getUIDForPackage(userId, str2);
                if (uIDForPackage2 != -1) {
                    removeExemptUidFromNwFilterRange(uIDForPackage2, uIDForPackage2);
                }
                removeExemptUidFromNwFilterRange(i, i);
                if (userId == 0) {
                    removeExemptUidFromNwFilterRange(1, 9999);
                }
                KnoxNetworkFilterProfileInfo profileEntry =
                        KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId);
                if (profileEntry != null) {
                    Iterator it = profileEntry.mVpnClientUidList.iterator();
                    while (it.hasNext()) {
                        int intValue = ((Integer) it.next()).intValue();
                        removeExemptUidFromNwFilterRange(intValue, intValue);
                    }
                }
                this.mKnoxNwFilterFw.getClass();
                KnoxNetworkFilterFirewall.flushAppGeneratedDOTBlockRules(userId);
                this.mKnoxNwFilterFw.getClass();
                this.mKnoxNwFilterFw.getClass();
                KnoxNetworkFilterFirewall.flushRedirectDnsQueryRules(userId);
                this.mKnoxNwFilterFw.getClass();
                KnoxNetworkFilterFirewall.flushExemptDnsRulesFromNat(userId);
                this.mKnoxNwFilterFw.getClass();
                KnoxNetworkFilterFirewall.flushAppGeneratedRedirectTcpConnRules(userId);
                this.mKnoxNwFilterFw.getClass();
                KnoxNetworkFilterFirewall.flushAppGeneratedRedirectV6TcpConnRules(userId);
                this.mKnoxNwFilterFw.getClass();
                KnoxNetworkFilterFirewall.flushUdpPacketExemptRules(userId, 2);
                this.mKnoxNwFilterFw.getClass();
                KnoxNetworkFilterFirewall.flushAppGeneratedRedirectUdpConnRules(userId);
                this.mKnoxNwFilterFw.getClass();
                KnoxNetworkFilterFirewall.flushUdpPacketExemptRules(userId, 10);
                this.mKnoxNwFilterFw.getClass();
                KnoxNetworkFilterFirewall.flushAppGeneratedRedirectV6UdpConnRules(userId);
                try {
                    iNetworkFilterProxy.setConfig(profilebyUserId, (String) null);
                    iNetworkFilterProxy2.setConfig(profilebyUserId, (String) null);
                    iNetworkFilterProxy3.setConfig(profilebyUserId, (String) null);
                } catch (RemoteException | NullPointerException unused11) {
                }
                try {
                    this.mKnoxNwFilterFw.getClass();
                    KnoxNetworkFilterFirewall.flushUnAuthorizedPackets(userId);
                } catch (NullPointerException unused12) {
                }
                this.mKnoxNwFilterHelper.removeVendorInfoFromStorage(profilebyUserId);
                disableNetdFlags();
            }
            if (z) {
                Log.i(
                        "knoxNwFilter-KnoxNetworkFilterService",
                        "removing the configs set for authorized user after the vendor package"
                            + " uninstall");
                this.mKnoxNwFilterHelper.removeAuthorizedInfoFromDatabase(i, str);
                unRegisterFilterList();
                unbindInternalProxyServices(userId);
                this.mKnoxNwFilterFw.getClass();
                KnoxNetworkFilterFirewall.removeIpTableChains(userId);
                disableNetdFlags();
                this.mKnoxNwFilterHelper.getClass();
            }
        } catch (Throwable th2) {
            throw th2;
        }
    }

    public final void initializeModules() {
        try {
            boolean isRegisterDbEmpty = this.mKnoxNwFilterHelper.isRegisterDbEmpty();
            boolean z =
                    this.mKnoxNwFilterHelper
                                    .mEDM
                                    .getDataByFields("UnManagedNwFilterMgr", null, null, null)
                                    .size()
                            <= 0;
            if (isRegisterDbEmpty && z) {
                return;
            }
            registerFilterList();
        } catch (Exception unused) {
        }
    }

    public final synchronized boolean isAuthorized() {
        int callingUid;
        String packageNameForUid;
        callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        this.mKnoxNwFilterHelper.getClass();
        packageNameForUid = KnoxNetworkFilterHelper.getPackageNameForUid(callingUid);
        this.mKnoxNwFilterValidation.prepareFilteringValidation(userId, packageNameForUid);
        this.mContext.enforceCallingOrSelfPermission(
                "com.samsung.android.knox.permission.KNOX_NETWORK_FILTER_SERVICE_PROVIDER",
                " Permission not granted");
        return this.mKnoxNwFilterHelper.isAppAuthorized(callingUid, packageNameForUid);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {}

    public final synchronized int pause(String str) {
        checkCallingUidPermission();
        return -1;
    }

    public final synchronized int prepareFiltering(String str, Bundle bundle) {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        this.mKnoxNwFilterHelper.getClass();
        String packageNameForUid = KnoxNetworkFilterHelper.getPackageNameForUid(callingUid);
        if (!packageNameForUid.equalsIgnoreCase("com.android.vpndialogs")) {
            Log.e(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "Error during prepareFiltering API validation as the caller package name"
                        + " validation failed");
            throw new SecurityException();
        }
        this.mKnoxNwFilterHelper.getClass();
        if (!KnoxNetworkFilterHelper.checkIfPlatformSigned(userId, packageNameForUid)) {
            Log.e(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "Error during prepareFiltering API validation as the caller is not platform"
                        + " signed");
            throw new SecurityException();
        }
        this.mKnoxNwFilterHelper.getClass();
        int uIDForPackage = KnoxNetworkFilterHelper.getUIDForPackage(userId, str);
        this.mContext.enforcePermission(
                "com.samsung.android.knox.permission.KNOX_NETWORK_FILTER_SERVICE_PROVIDER",
                0,
                uIDForPackage,
                " Permission not granted");
        this.mKnoxNwFilterValidation.prepareFilteringValidation(userId, str);
        if (!this.mKnoxNwFilterHelper.addAuthorizedInfoToDatabase(uIDForPackage, str, bundle)) {
            if (!this.mKnoxNwFilterHelper.removeAuthorizedInfoFromDatabase(uIDForPackage, str)) {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterService",
                        "prepareFiltering: inserting info into db failed");
                return -8;
            }
            if (!this.mKnoxNwFilterHelper.addAuthorizedInfoToDatabase(uIDForPackage, str, bundle)) {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterService",
                        "prepareFiltering: inserting info into db failed during second attempt");
                return -8;
            }
        }
        this.mKnoxNwFilterHelper.getClass();
        this.mNwFilterProxyAppId =
                UserHandle.getAppId(
                        KnoxNetworkFilterHelper.getUIDForPackage(
                                userId, "com.samsung.android.knox.app.networkfilter"));
        try {
            bindInternalProxyServices(userId);
            initializeModules();
            this.mKnoxNwFilterFw.getClass();
            KnoxNetworkFilterFirewall.createIpTableChains(userId);
            if (uIDForPackage != -1) {
                this.mKnoxNwFilterHelper.addOrRemoveAppsFromBatteryOptimization(
                        uIDForPackage, str, true);
                this.mKnoxNwFilterHelper.getClass();
            }
            KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_ZT", 1, "ZTNA_USAGE");
            knoxAnalyticsData.setProperty("apiN", "registerNetworkFilter");
            knoxAnalyticsData.setProperty("pkgN", str);
            knoxAnalyticsData.setProperty("eInfo", "100");
            knoxAnalyticsData.setUserTypeProperty(userId);
            Log.i(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "KnoxAnalyticsData:registerNetworkFilter API getting logged due to end-user"
                        + " authorization for caller "
                            + str
                            + " "
                            + userId);
            KnoxAnalytics.log(knoxAnalyticsData);
            return 0;
        } catch (InterruptedException unused) {
            Log.e(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "removing the inserted info from db since internal binding failed");
            this.mKnoxNwFilterHelper.removeAuthorizedInfoFromDatabase(uIDForPackage, str);
            return -8;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0058 A[Catch: all -> 0x0064, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x000e, B:9:0x0015, B:17:0x004e, B:19:0x0058, B:22:0x0066, B:25:0x0026, B:28:0x002e, B:30:0x0034, B:32:0x003a), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0066 A[Catch: all -> 0x0064, TRY_ENTER, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x000e, B:9:0x0015, B:17:0x004e, B:19:0x0058, B:22:0x0066, B:25:0x0026, B:28:0x002e, B:30:0x0034, B:32:0x003a), top: B:3:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized int registerApplication(
            com.samsung.android.knox.ContextInfo r6,
            java.lang.String r7,
            java.lang.String r8,
            android.os.Bundle r9) {
        /*
            r5 = this;
            java.lang.String r0 = "KnoxAnalyticsData:registerNetworkFilter API getting logged for caller "
            monitor-enter(r5)
            r5.checkCallingUidPermissionEMM(r6)     // Catch: java.lang.Throwable -> L64
            com.android.server.enterprise.filter.KnoxNetworkFilterValidation r1 = r5.mKnoxNwFilterValidation     // Catch: java.lang.Throwable -> L64
            r1.getClass()     // Catch: java.lang.Throwable -> L64
            r2 = -6
            if (r7 == 0) goto L46
            boolean r3 = r7.isEmpty()     // Catch: java.lang.Throwable -> L64
            if (r3 == 0) goto L15
            goto L46
        L15:
            int r3 = r6.mCallerUid     // Catch: java.lang.Throwable -> L64
            int r3 = android.os.UserHandle.getUserId(r3)     // Catch: java.lang.Throwable -> L64
            com.android.server.enterprise.filter.KnoxNetworkFilterHelper r1 = r1.mKnoxNwFilterHelper     // Catch: java.lang.Throwable -> L64
            boolean r4 = r1.isRegisterDbEmpty()     // Catch: java.lang.Throwable -> L64
            if (r4 != 0) goto L26
            r1 = -9
            goto L48
        L26:
            boolean r4 = com.android.server.enterprise.filter.KnoxNetworkFilterHelper.isPackageInstalled(r3, r7)     // Catch: java.lang.Throwable -> L64
            if (r4 == 0) goto L44
            if (r8 == 0) goto L42
            boolean r4 = r8.isEmpty()     // Catch: java.lang.Throwable -> L64
            if (r4 != 0) goto L42
            java.lang.String r1 = r1.getSignature(r3, r7)     // Catch: java.lang.Throwable -> L64
            if (r1 == 0) goto L42
            boolean r1 = r1.equalsIgnoreCase(r8)     // Catch: java.lang.Throwable -> L64
            if (r1 != 0) goto L42
            r1 = -3
            goto L48
        L42:
            r1 = 0
            goto L48
        L44:
            r1 = r2
            goto L48
        L46:
            r1 = -10
        L48:
            if (r1 == 0) goto L4e
            if (r1 == r2) goto L4e
            monitor-exit(r5)
            return r1
        L4e:
            com.android.server.enterprise.filter.KnoxNetworkFilterHelper r2 = r5.mKnoxNwFilterHelper     // Catch: java.lang.Throwable -> L64
            int r3 = r6.mCallerUid     // Catch: java.lang.Throwable -> L64
            boolean r8 = r2.addRegisterInfoToDatabase(r3, r7, r8, r9)     // Catch: java.lang.Throwable -> L64
            if (r8 != 0) goto L66
            java.lang.String r6 = "knoxNwFilter-KnoxNetworkFilterService"
            java.lang.String r7 = "registerApplication: inserting info into db failed"
            android.util.Log.e(r6, r7)     // Catch: java.lang.Throwable -> L64
            monitor-exit(r5)
            r5 = -8
            return r5
        L64:
            r6 = move-exception
            goto Ldc
        L66:
            com.android.server.enterprise.filter.KnoxNetworkFilterHelper r8 = r5.mKnoxNwFilterHelper     // Catch: java.lang.Throwable -> L64
            int r9 = r6.mContainerId     // Catch: java.lang.Throwable -> L64
            java.lang.String r2 = "com.samsung.android.knox.app.networkfilter"
            r8.getClass()     // Catch: java.lang.Throwable -> L64
            int r8 = com.android.server.enterprise.filter.KnoxNetworkFilterHelper.getUIDForPackage(r9, r2)     // Catch: java.lang.Throwable -> L64
            int r8 = android.os.UserHandle.getAppId(r8)     // Catch: java.lang.Throwable -> L64
            r5.mNwFilterProxyAppId = r8     // Catch: java.lang.Throwable -> L64
            com.samsung.android.knox.analytics.KnoxAnalyticsData r8 = new com.samsung.android.knox.analytics.KnoxAnalyticsData     // Catch: java.lang.Throwable -> L64
            java.lang.String r9 = "KNOX_ZT"
            java.lang.String r2 = "ZTNA_USAGE"
            r3 = 1
            r8.<init>(r9, r3, r2)     // Catch: java.lang.Throwable -> L64
            com.android.server.enterprise.filter.KnoxNetworkFilterHelper r9 = r5.mKnoxNwFilterHelper     // Catch: java.lang.Throwable -> L64
            int r2 = r6.mCallerUid     // Catch: java.lang.Throwable -> L64
            r9.getClass()     // Catch: java.lang.Throwable -> L64
            java.lang.String r9 = com.android.server.enterprise.filter.KnoxNetworkFilterHelper.getPackageNameForUid(r2)     // Catch: java.lang.Throwable -> L64
            java.lang.String r2 = "apiN"
            java.lang.String r3 = "registerNetworkFilter"
            r8.setProperty(r2, r3)     // Catch: java.lang.Throwable -> L64
            java.lang.String r2 = "pkgN"
            r8.setProperty(r2, r9)     // Catch: java.lang.Throwable -> L64
            int r2 = r6.mContainerId     // Catch: java.lang.Throwable -> L64
            r8.setUserTypeProperty(r2)     // Catch: java.lang.Throwable -> L64
            java.lang.String r2 = "knoxNwFilter-KnoxNetworkFilterService"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L64
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L64
            r3.append(r9)     // Catch: java.lang.Throwable -> L64
            java.lang.String r9 = " "
            r3.append(r9)     // Catch: java.lang.Throwable -> L64
            int r9 = r6.mContainerId     // Catch: java.lang.Throwable -> L64
            r3.append(r9)     // Catch: java.lang.Throwable -> L64
            java.lang.String r9 = r3.toString()     // Catch: java.lang.Throwable -> L64
            android.util.Log.i(r2, r9)     // Catch: java.lang.Throwable -> L64
            com.samsung.android.knox.analytics.KnoxAnalytics.log(r8)     // Catch: java.lang.Throwable -> L64
            android.os.Bundle r8 = new android.os.Bundle     // Catch: java.lang.Throwable -> L64
            r8.<init>()     // Catch: java.lang.Throwable -> L64
            java.lang.String r9 = "userId"
            int r6 = r6.mContainerId     // Catch: java.lang.Throwable -> L64
            r8.putInt(r9, r6)     // Catch: java.lang.Throwable -> L64
            java.lang.String r6 = "package"
            r8.putString(r6, r7)     // Catch: java.lang.Throwable -> L64
            r6 = 10
            r5.sendMessageToHandler(r6, r8)     // Catch: java.lang.Throwable -> L64
            monitor-exit(r5)
            return r1
        Ldc:
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.filter.KnoxNetworkFilterService.registerApplication(com.samsung.android.knox.ContextInfo,"
                    + " java.lang.String, java.lang.String, android.os.Bundle):int");
    }

    public final void registerFilterList() {
        if (this.mReceiver != null) {
            return;
        }
        this.mReceiver = new NwFilterReceiver();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
            intentFilter.addCategory("android.intent.category.DEFAULT");
            intentFilter.addDataScheme("package");
            Context context = this.mContext;
            NwFilterReceiver nwFilterReceiver = this.mReceiver;
            UserHandle userHandle = UserHandle.ALL;
            context.registerReceiverAsUser(nwFilterReceiver, userHandle, intentFilter, null, null);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.BOOT_COMPLETED");
            intentFilter2.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
            intentFilter2.addAction("android.intent.action.AIRPLANE_MODE");
            intentFilter2.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
            intentFilter2.addAction("android.intent.action.USER_REMOVED");
            this.mContext.registerReceiverAsUser(
                    this.mReceiver, userHandle, intentFilter2, null, null, 2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:14|(2:15|16)|17|18|(3:19|20|(1:22))|24|(1:26)|(2:27|28)|(2:29|30)|32|33|34|35|36) */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0127, code lost:

       android.util.Log.e("knoxNwFilter-KnoxNetworkFilterService", "registerListeners:udp:error occurred since the remote service is null");
    */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x013b, code lost:

       android.util.Log.e("knoxNwFilter-KnoxNetworkFilterService", "registerListeners:udp:error occured while communicating to remote service");
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0131, code lost:

       android.util.Log.e("knoxNwFilter-KnoxNetworkFilterService", "registerListeners:udp:IllegalArgumentException");
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized int registerListeners(java.lang.String r11, java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 330
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.filter.KnoxNetworkFilterService.registerListeners(java.lang.String,"
                    + " java.lang.String):int");
    }

    public final void registerSystemDefaultNetworkCallback(int i) {
        if (this.mNetworkCallbackList.containsKey(Integer.valueOf(i))) {
            Log.i(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "Default network callback is already registered, skipping registering");
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                NetworkCallback networkCallback = new NetworkCallback(i);
                this.mCm.registerSystemDefaultNetworkCallback(networkCallback, this.mHandler);
                this.mNetworkCallbackList.put(Integer.valueOf(i), networkCallback);
            } catch (RuntimeException e) {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterService",
                        "Failed to register system default network callback "
                                + Log.getStackTraceString(e));
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void releaseNwFilterNetId(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            INetd iNetd = this.mNetdService;
            if (iNetd != null) {
                iNetd.networkDestroy(i);
            }
        } catch (RemoteException | ServiceSpecificException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        this.mNetIdManager.releaseNetId(i);
    }

    public final synchronized void removeConfigByEnduser() {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        this.mKnoxNwFilterHelper.getClass();
        String packageNameForUid = KnoxNetworkFilterHelper.getPackageNameForUid(callingUid);
        if (!packageNameForUid.equalsIgnoreCase("com.android.vpndialogs")) {
            Log.e(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "Error during removeConfigByEnduser API validation as the caller package name"
                        + " validation failed");
            throw new SecurityException();
        }
        this.mKnoxNwFilterHelper.getClass();
        if (!KnoxNetworkFilterHelper.checkIfPlatformSigned(userId, packageNameForUid)) {
            Log.e(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "Error during removeConfigByEnduser API validation as the caller is not"
                        + " platform signed");
            throw new SecurityException();
        }
        this.mKnoxNwFilterHelper.getClass();
        String profilebyUserId = KnoxNetworkFilterHelper.getProfilebyUserId(userId);
        if (profilebyUserId == null) {
            return;
        }
        this.mKnoxNwFilterHelper.getClass();
        int vendorUidByProfile = KnoxNetworkFilterHelper.getVendorUidByProfile(profilebyUserId);
        if (vendorUidByProfile == -1) {
            return;
        }
        KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_ZT", 1, "ZTNA_USAGE");
        this.mKnoxNwFilterHelper.getClass();
        String packageNameForUid2 =
                KnoxNetworkFilterHelper.getPackageNameForUid(vendorUidByProfile);
        knoxAnalyticsData.setProperty("apiN", "unregisterNetworkFilter");
        knoxAnalyticsData.setProperty("pkgN", packageNameForUid2);
        knoxAnalyticsData.setProperty("eInfo", "203");
        knoxAnalyticsData.setUserTypeProperty(userId);
        Log.i(
                "knoxNwFilter-KnoxNetworkFilterService",
                "KnoxAnalyticsData:unregisterNetworkFilter API getting logged due to end-user"
                    + " stopping manually for caller "
                        + packageNameForUid2
                        + " "
                        + userId);
        KnoxAnalytics.log(knoxAnalyticsData);
        Bundle bundle = new Bundle();
        bundle.putInt("uid", vendorUidByProfile);
        sendMessageToHandler(13, bundle);
    }

    public final void removeExemptUidFromNwFilterRange(int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new UidRangeParcel(i, i2));
            getOemNetdService()
                    .removeExemptUidFromNwFilterRange(
                            (UidRangeParcel[])
                                    arrayList.toArray(new UidRangeParcel[arrayList.size()]));
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (i == i2) {
            this.mKnoxNwFilterFw.getClass();
            String str = UserHandle.getUserId(i) + "_knox_nwfilter_app_act";
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            String m = VibrationParam$1$$ExternalSyntheticOutline0.m(i, " -m owner --uid-owner ");
            KnoxNetworkFilterFirewall.IpRestoreActionType ipRestoreActionType =
                    KnoxNetworkFilterFirewall.IpRestoreActionType.DELETE;
            arrayList2.add(
                    new KnoxNetworkFilterFirewall.IpRestoreParam(
                            str, m, "ACCEPT", "", ipRestoreActionType));
            arrayList3.add(
                    new KnoxNetworkFilterFirewall.IpRestoreParam(
                            str,
                            VibrationParam$1$$ExternalSyntheticOutline0.m(
                                    i, " -m owner --uid-owner "),
                            "ACCEPT",
                            "",
                            ipRestoreActionType));
            arrayList4.add(
                    new KnoxNetworkFilterFirewall.IpRestoreParam(
                            str,
                            VibrationParam$1$$ExternalSyntheticOutline0.m(
                                    i, " -m owner --uid-owner "),
                            "ACCEPT",
                            "",
                            ipRestoreActionType));
            KnoxNetworkFilterFirewall.insertRules(4, "*nat", null, arrayList2, true);
            KnoxNetworkFilterFirewall.insertRules(46, "*filter", null, arrayList3, true);
            KnoxNetworkFilterFirewall.insertRules(6, "*mangle", null, arrayList4, true);
        }
    }

    public final void sendBootStatusIntent(int i, int i2, int i3, int i4, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.knox.intent.action.NOTIFY_STATUS");
            intent.putExtra("com.samsung.android.knox.intent.extra.ERROR_CODE", i3);
            intent.addFlags(67108864);
            intent.addFlags(32);
            intent.addFlags(16777216);
            if (i2 == 0) {
                intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 1);
                intent.putExtra("com.samsung.android.knox.intent.extra.TYPE", i4);
            } else if (i2 == 6) {
                intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 7);
                intent.putExtra("com.samsung.android.knox.intent.extra.TYPE", i4);
            }
            intent.setPackage(str);
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(i));
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void sendEMMStatusIntent(int i, int i2, String str) {
        if (str == null) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.knox.intent.action.NOTIFY_NETWORK_FILTER_STATUS");
            intent.addFlags(67108864);
            intent.addFlags(32);
            intent.addFlags(16777216);
            if (i2 == 1) {
                intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 1);
            } else if (i2 == 2) {
                intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 3);
            } else if (i2 == 3) {
                intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 2);
            }
            intent.setPackage(str);
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(i));
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void sendMessageToHandler(int i, Bundle bundle) {
        KnoxNwFilterHandler knoxNwFilterHandler = this.mHandler;
        if (knoxNwFilterHandler != null) {
            this.mHandler.sendMessage(Message.obtain(knoxNwFilterHandler, i, 0, 0, bundle));
        }
    }

    public final void sendStatusIntent(int i, int i2, int i3, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.knox.intent.action.NOTIFY_STATUS");
            intent.putExtra("com.samsung.android.knox.intent.extra.ERROR_CODE", i3);
            intent.addFlags(67108864);
            intent.addFlags(32);
            intent.addFlags(16777216);
            switch (i2) {
                case 0:
                    intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 1);
                    break;
                case 1:
                    intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 100);
                    break;
                case 2:
                    intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 101);
                    break;
                case 3:
                    intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 102);
                    break;
                case 4:
                    intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 2);
                    break;
                case 5:
                    intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 103);
                    break;
                case 6:
                    intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 7);
                    break;
            }
            intent.setPackage(str);
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(i));
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x005e, code lost:

       return -9;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized int setConfig(java.lang.String r13, java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.filter.KnoxNetworkFilterService.setConfig(java.lang.String,"
                    + " java.lang.String):int");
    }

    public final int setupNetworkDns(int i) {
        boolean z;
        Exception e;
        INetd iNetd;
        connectNativeNetdService();
        int i2 = -1;
        boolean z2 = true;
        do {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    try {
                        i2 = this.mNetIdManager.reserveNetId();
                        iNetd = this.mNetdService;
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } catch (IllegalStateException e2) {
                    Log.e(
                            "knoxNwFilter-KnoxNetworkFilterService",
                            "setupNetworkDns() - Failed creating new network.",
                            e2);
                }
                if (iNetd == null) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return i2;
                }
                try {
                    iNetd.networkCreateVpn(i2, false);
                    DnsResolverAdapter dnsResolverAdapter = this.mDnsResolverAdapter;
                    dnsResolverAdapter.getClass();
                    Log.d("DnsResolverAdapter", "createNetworkCache - entered");
                    dnsResolverAdapter.runWithExceptionHandling(
                            new DnsResolverAdapter$$ExternalSyntheticLambda2(i2, 0));
                    Log.d("DnsResolverAdapter", "createNetworkCache - exited");
                    try {
                        LinkProperties linkProperties = new LinkProperties();
                        if (i == 0) {
                            linkProperties.addDnsServer(InetAddress.getByName("127.0.0.1"));
                        } else {
                            linkProperties.addDnsServer(InetAddress.getByName("127.0.0.2"));
                        }
                        EnforceDnsManager enforceDnsManager =
                                new EnforceDnsManager(this.mContext, this.mDnsResolverAdapter);
                        this.mDnsManager = enforceDnsManager;
                        ((HashMap) enforceDnsManager.mLinkPropertiesMap)
                                .put(Integer.valueOf(i2), linkProperties);
                        enforceDnsManager.sendDnsConfigurationForNetwork(i2);
                        EnforceDnsManager enforceDnsManager2 = this.mDnsManager;
                        enforceDnsManager2.mEnforceDnsUid = false;
                        enforceDnsManager2.sendDnsConfigurationForNetwork(i2);
                        EnforceDnsManager enforceDnsManager3 = this.mDnsManager;
                        ((HashMap) enforceDnsManager3.mTransportsMap)
                                .put(Integer.valueOf(i2), new int[] {0, 1, 3, 4});
                        enforceDnsManager3.sendDnsConfigurationForNetwork(i2);
                        this.mDnsManager.flushVmDnsCache();
                        try {
                            Log.d(
                                    "knoxNwFilter-KnoxNetworkFilterService",
                                    "setupNetworkDns() - Network created id = " + i2);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            z2 = false;
                        } catch (Exception e3) {
                            z = true;
                            e = e3;
                            z2 = false;
                            if (z) {
                                Log.e(
                                        "knoxNwFilter-KnoxNetworkFilterService",
                                        "setupNetworkDns() - Failed to set dns for network " + i2,
                                        e);
                                try {
                                    INetd iNetd2 = this.mNetdService;
                                    if (iNetd2 != null) {
                                        iNetd2.networkDestroy(i2);
                                    }
                                } catch (Exception e4) {
                                    Log.e(
                                            "knoxNwFilter-KnoxNetworkFilterService",
                                            "setupNetworkDns() - Failed to remove network",
                                            e4);
                                }
                            } else {
                                Log.e(
                                        "knoxNwFilter-KnoxNetworkFilterService",
                                        "setupNetworkDns() - Failed to create new network with id"
                                            + " provided.",
                                        e);
                            }
                            this.mNetIdManager.releaseNetId(i2);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    } catch (Exception e5) {
                        e = e5;
                        z = true;
                    }
                } catch (RemoteException | ServiceSpecificException e6) {
                    Log.e(
                            "knoxNwFilter-KnoxNetworkFilterService",
                            "Error creating private network: " + e6.getMessage());
                    this.mNetIdManager.releaseNetId(i2);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -1;
                }
            } catch (Exception e7) {
                z = false;
                e = e7;
            }
        } while (z2);
        return i2;
    }

    public final synchronized int start(String str) {
        checkCallingUidPermission();
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        int startValidation = this.mKnoxNwFilterValidation.startValidation(str);
        if (startValidation != 0) {
            return startValidation;
        }
        INetworkFilterProxy iNetworkFilterProxy =
                (INetworkFilterProxy)
                        this.mDefaultDnsProxyInterfaceList.getOrDefault(
                                Integer.valueOf(userId), null);
        INetworkFilterProxy iNetworkFilterProxy2 =
                (INetworkFilterProxy)
                        this.mDefaultUdpProxyInterfaceList.getOrDefault(
                                Integer.valueOf(userId), null);
        INetworkFilterProxy iNetworkFilterProxy3 =
                (INetworkFilterProxy)
                        this.mDefaultTcpProxyInterfaceList.getOrDefault(
                                Integer.valueOf(userId), null);
        if (iNetworkFilterProxy != null
                && iNetworkFilterProxy3 != null
                && iNetworkFilterProxy2 != null) {
            this.mKnoxNwFilterHelper.getClass();
            if (KnoxNetworkFilterProfileInfo.getProfileEntry(str).mState == 3) {
                Log.d(
                        "knoxNwFilter-KnoxNetworkFilterService",
                        "skipping start call since the profile is already in running state");
                return startValidation;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SystemProperties.set("net.redirect_socket_calls.hooked", "true");
                getOemNetdService().enableIpOptionModification(true);
            } catch (RemoteException unused) {
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            registerSystemDefaultNetworkCallback(userId);
            Bundle bundle = new Bundle();
            bundle.putInt("uid", callingUid);
            bundle.putString("profileName", str);
            sendMessageToHandler(8, bundle);
            return startValidation;
        }
        return -8;
    }

    public final synchronized int stop(String str, String str2) {
        try {
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(callingUid);
            this.mKnoxNwFilterHelper.getClass();
            String packageNameForUid = KnoxNetworkFilterHelper.getPackageNameForUid(callingUid);
            if (packageNameForUid.equalsIgnoreCase("com.android.vpndialogs")) {
                this.mKnoxNwFilterHelper.getClass();
                if (!KnoxNetworkFilterHelper.checkIfPlatformSigned(userId, packageNameForUid)) {
                    Log.e(
                            "knoxNwFilter-KnoxNetworkFilterService",
                            "Error during stop API validation as the caller is not platform"
                                + " signed");
                    throw new SecurityException();
                }
                this.mKnoxNwFilterHelper.getClass();
                callingUid = KnoxNetworkFilterHelper.getVendorUidByProfile(str);
                userId = UserHandle.getUserId(callingUid);
            } else {
                checkCallingUidPermission();
            }
            int stopValidation = this.mKnoxNwFilterValidation.stopValidation(callingUid, str);
            if (stopValidation != 0) {
                return stopValidation;
            }
            INetworkFilterProxy iNetworkFilterProxy =
                    (INetworkFilterProxy)
                            this.mDefaultDnsProxyInterfaceList.getOrDefault(
                                    Integer.valueOf(userId), null);
            INetworkFilterProxy iNetworkFilterProxy2 =
                    (INetworkFilterProxy)
                            this.mDefaultUdpProxyInterfaceList.getOrDefault(
                                    Integer.valueOf(userId), null);
            INetworkFilterProxy iNetworkFilterProxy3 =
                    (INetworkFilterProxy)
                            this.mDefaultTcpProxyInterfaceList.getOrDefault(
                                    Integer.valueOf(userId), null);
            if (iNetworkFilterProxy != null
                    && iNetworkFilterProxy3 != null
                    && iNetworkFilterProxy2 != null) {
                this.mKnoxNwFilterHelper.getClass();
                if (KnoxNetworkFilterProfileInfo.getProfileEntry(str).mState != 3) {
                    Log.d(
                            "knoxNwFilter-KnoxNetworkFilterService",
                            "skipping stop call since the profile is already in idle state");
                    return stopValidation;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    getOemNetdService().enablePortInfoEntries(userId, 2, 6, false);
                    getOemNetdService().enablePortInfoEntries(userId, 10, 6, false);
                    getOemNetdService().enablePortInfoEntries(userId, 10, 17, false);
                    getOemNetdService().clearNetworkFilterEntries(userId);
                } catch (RemoteException unused) {
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                unregisterSystemDefaultNetworkCallback(userId);
                Bundle bundle = new Bundle();
                bundle.putInt("uid", callingUid);
                bundle.putString("profileName", str);
                sendMessageToHandler(9, bundle);
                return stopValidation;
            }
            return -8;
        } catch (Throwable th2) {
            throw th2;
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {}

    public final void unRegisterFilterList() {
        boolean isRegisterDbEmpty = this.mKnoxNwFilterHelper.isRegisterDbEmpty();
        boolean z =
                this.mKnoxNwFilterHelper
                                .mEDM
                                .getDataByFields("UnManagedNwFilterMgr", null, null, null)
                                .size()
                        <= 0;
        if (isRegisterDbEmpty && z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    NwFilterReceiver nwFilterReceiver = this.mReceiver;
                    if (nwFilterReceiver != null) {
                        this.mContext.unregisterReceiver(nwFilterReceiver);
                    }
                    this.mReceiver = null;
                } catch (Exception unused) {
                    Log.e(
                            "knoxNwFilter-KnoxNetworkFilterService",
                            "Error occured while trying to unregister the reciever");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public final void unbindInternalProxyServices(int i) {
        if (this.mDefaultDnsConnectionList.containsKey(Integer.valueOf(i))) {
            this.mContext.unbindService(
                    (ServiceConnection) this.mDefaultDnsConnectionList.get(Integer.valueOf(i)));
            this.mDefaultDnsConnectionList.remove(Integer.valueOf(i));
        }
        if (this.mDefaultTcpConnectionList.containsKey(Integer.valueOf(i))) {
            this.mContext.unbindService(
                    (ServiceConnection) this.mDefaultTcpConnectionList.get(Integer.valueOf(i)));
            this.mDefaultTcpConnectionList.remove(Integer.valueOf(i));
        }
        if (this.mDefaultUdpConnectionList.containsKey(Integer.valueOf(i))) {
            this.mContext.unbindService(
                    (ServiceConnection) this.mDefaultUdpConnectionList.get(Integer.valueOf(i)));
            this.mDefaultUdpConnectionList.remove(Integer.valueOf(i));
        }
    }

    public final synchronized int unregisterApplication(ContextInfo contextInfo, String str) {
        checkCallingUidPermissionEMM(contextInfo);
        int unregisterApplicationValidation =
                this.mKnoxNwFilterValidation.unregisterApplicationValidation(contextInfo, str);
        if (unregisterApplicationValidation != 0 && unregisterApplicationValidation != -6) {
            return unregisterApplicationValidation;
        }
        if (!this.mKnoxNwFilterHelper.mEDM.deleteDataByFields(
                "NwFilterMgr", new String[] {"pkgName"}, new String[] {str})) {
            Log.e(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "unregisterApplication: deleting info from db failed");
            return -8;
        }
        KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_ZT", 1, "ZTNA_USAGE");
        KnoxNetworkFilterHelper knoxNetworkFilterHelper = this.mKnoxNwFilterHelper;
        int i = contextInfo.mCallerUid;
        knoxNetworkFilterHelper.getClass();
        String packageNameForUid = KnoxNetworkFilterHelper.getPackageNameForUid(i);
        knoxAnalyticsData.setProperty("apiN", "unregisterNetworkFilter");
        knoxAnalyticsData.setProperty("pkgN", packageNameForUid);
        knoxAnalyticsData.setUserTypeProperty(contextInfo.mContainerId);
        Log.i(
                "knoxNwFilter-KnoxNetworkFilterService",
                "KnoxAnalyticsData:unregisterNetworkFilter API getting logged for caller "
                        + packageNameForUid
                        + " "
                        + contextInfo.mContainerId);
        KnoxAnalytics.log(knoxAnalyticsData);
        Bundle bundle = new Bundle();
        bundle.putInt("userId", contextInfo.mContainerId);
        bundle.putString("package", str);
        sendMessageToHandler(11, bundle);
        return unregisterApplicationValidation;
    }

    public final void unregisterSystemDefaultNetworkCallback(int i) {
        if (!this.mNetworkCallbackList.containsKey(Integer.valueOf(i))) {
            Log.i(
                    "knoxNwFilter-KnoxNetworkFilterService",
                    "Default network callback is not registered, skipping unregistering");
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                NetworkCallback networkCallback =
                        (NetworkCallback) this.mNetworkCallbackList.get(Integer.valueOf(i));
                if (networkCallback != null) {
                    this.mCm.unregisterNetworkCallback(networkCallback);
                    this.mNetworkCallbackList.remove(Integer.valueOf(i));
                }
            } catch (RuntimeException e) {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterService",
                        "Failed to unregister system default network callback "
                                + Log.getStackTraceString(e));
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
