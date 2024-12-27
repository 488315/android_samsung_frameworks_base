package com.android.server.knox.zt.networktrust;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import com.samsung.android.knox.SemPersonaManager;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class KnoxNetworkEventService {
    public static KnoxNetworkEventService mInstance;
    public final ConnectivityManager mCm;
    public final Context mContext;
    public NetworkCallback mDefaultNetworkCallback;
    public final Set mEnabledEvents;
    public final KnoxNwEventHandler mHandler;
    public final HandlerThread mHandlerThread;
    public final KnoxNetworkEventFirewall mKnoxNwEventFw;
    public UserActivityReceiver mReceiver;
    public final Set mUserIdList;
    public final UserManager mUserManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KnoxNwEventHandler extends Handler {
        public KnoxNwEventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("handleMessage called msg.what = "),
                    message.what,
                    "KnoxNetworkEventService");
            Bundle bundle = (Bundle) message.obj;
            int i = message.what;
            if (i == 1) {
                KnoxNetworkEventService knoxNetworkEventService = KnoxNetworkEventService.this;
                synchronized (knoxNetworkEventService) {
                    try {
                        int i2 = bundle.getInt("eventType");
                        ((HashSet) knoxNetworkEventService.mEnabledEvents).add(Integer.valueOf(i2));
                        switch (i2) {
                            case 14:
                                Log.d(
                                        "KnoxNetworkEventService",
                                        "handleStartMonitoring() insertRuleForInsecurePorts()");
                                Iterator it =
                                        ((HashSet) knoxNetworkEventService.mUserIdList).iterator();
                                while (it.hasNext()) {
                                    int intValue = ((Integer) it.next()).intValue();
                                    Log.d(
                                            "KnoxNetworkEventService",
                                            "handleStartMonitoring() insertRuleForInsecurePorts"
                                                + " userId = "
                                                    + intValue);
                                    knoxNetworkEventService.mKnoxNwEventFw.getClass();
                                    KnoxNetworkEventFirewall.insertRuleForInsecurePorts(intValue);
                                }
                                break;
                            case 15:
                            case 16:
                                Log.d(
                                        "KnoxNetworkEventService",
                                        "handleStartMonitoring()"
                                            + " registerSystemDefaultNetworkCallback()");
                                knoxNetworkEventService.registerSystemDefaultNetworkCallback();
                                break;
                            default:
                                Log.e("KnoxNetworkEventService", "Invalid event type - " + i2);
                                break;
                        }
                        knoxNetworkEventService.registerUserActivityReceiver();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            }
            if (i != 2) {
                if (i != 3) {
                    return;
                }
                KnoxNetworkEventService knoxNetworkEventService2 = KnoxNetworkEventService.this;
                synchronized (knoxNetworkEventService2) {
                    int i3 = bundle.getInt("android.intent.extra.user_handle", -10000);
                    Log.d("KnoxNetworkEventService", "handleActionUserRemoved for user: " + i3);
                    if (((HashSet) knoxNetworkEventService2.mUserIdList)
                            .contains(Integer.valueOf(i3))) {
                        KnoxNetworkEventFirewall knoxNetworkEventFirewall =
                                knoxNetworkEventService2.mKnoxNwEventFw;
                        KnoxNetworkEventFirewall.EventType eventType =
                                KnoxNetworkEventFirewall.EventType.INSECURE_PORTS;
                        knoxNetworkEventFirewall.getClass();
                        KnoxNetworkEventFirewall.flushRulesForEvent(eventType);
                        KnoxNetworkEventFirewall knoxNetworkEventFirewall2 =
                                knoxNetworkEventService2.mKnoxNwEventFw;
                        KnoxNetworkEventFirewall.EventType eventType2 =
                                KnoxNetworkEventFirewall.EventType.ABNORMAL_PACKETS;
                        knoxNetworkEventFirewall2.getClass();
                        KnoxNetworkEventFirewall.flushRulesForEvent(eventType2);
                        KnoxNetworkEventFirewall knoxNetworkEventFirewall3 =
                                knoxNetworkEventService2.mKnoxNwEventFw;
                        KnoxNetworkEventFirewall.EventType eventType3 =
                                KnoxNetworkEventFirewall.EventType.LOCAL_NW;
                        knoxNetworkEventFirewall3.getClass();
                        KnoxNetworkEventFirewall.flushRulesForEvent(eventType3);
                        return;
                    }
                    return;
                }
            }
            KnoxNetworkEventService knoxNetworkEventService3 = KnoxNetworkEventService.this;
            synchronized (knoxNetworkEventService3) {
                try {
                    int i4 = bundle.getInt("eventType");
                    ((HashSet) knoxNetworkEventService3.mEnabledEvents).remove(Integer.valueOf(i4));
                    if (!((HashSet) knoxNetworkEventService3.mEnabledEvents).contains(15)
                            && !((HashSet) knoxNetworkEventService3.mEnabledEvents).contains(16)) {
                        knoxNetworkEventService3.unregisterSystemDefaultNetworkCallback();
                    }
                    switch (i4) {
                        case 14:
                            KnoxNetworkEventFirewall knoxNetworkEventFirewall4 =
                                    knoxNetworkEventService3.mKnoxNwEventFw;
                            KnoxNetworkEventFirewall.EventType eventType4 =
                                    KnoxNetworkEventFirewall.EventType.INSECURE_PORTS;
                            knoxNetworkEventFirewall4.getClass();
                            KnoxNetworkEventFirewall.flushRulesForEvent(eventType4);
                            break;
                        case 15:
                            KnoxNetworkEventFirewall knoxNetworkEventFirewall5 =
                                    knoxNetworkEventService3.mKnoxNwEventFw;
                            KnoxNetworkEventFirewall.EventType eventType5 =
                                    KnoxNetworkEventFirewall.EventType.ABNORMAL_PACKETS;
                            knoxNetworkEventFirewall5.getClass();
                            KnoxNetworkEventFirewall.flushRulesForEvent(eventType5);
                            break;
                        case 16:
                            KnoxNetworkEventFirewall knoxNetworkEventFirewall6 =
                                    knoxNetworkEventService3.mKnoxNwEventFw;
                            KnoxNetworkEventFirewall.EventType eventType6 =
                                    KnoxNetworkEventFirewall.EventType.LOCAL_NW;
                            knoxNetworkEventFirewall6.getClass();
                            KnoxNetworkEventFirewall.flushRulesForEvent(eventType6);
                            break;
                    }
                    if (((HashSet) knoxNetworkEventService3.mEnabledEvents).isEmpty()) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            try {
                                UserActivityReceiver userActivityReceiver =
                                        knoxNetworkEventService3.mReceiver;
                                if (userActivityReceiver != null) {
                                    knoxNetworkEventService3.mContext.unregisterReceiver(
                                            userActivityReceiver);
                                }
                                knoxNetworkEventService3.mReceiver = null;
                            } catch (Exception unused) {
                                Log.e(
                                        "KnoxNetworkEventService",
                                        "Error while trying to unregister the receiver");
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NetworkCallback extends ConnectivityManager.NetworkCallback {
        public String mDefaultInterface;
        public boolean mIsWifiConfigured = false;

        public NetworkCallback() {}

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onAvailable(Network network) {
            Log.d(
                    "KnoxNetworkEventService",
                    "onAvailable being called for netId " + network.getNetId());
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onCapabilitiesChanged(
                Network network, NetworkCapabilities networkCapabilities) {
            if (!networkCapabilities.hasTransport(1)) {
                this.mIsWifiConfigured = false;
                return;
            }
            Log.d(
                    "KnoxNetworkEventService",
                    "onCapabilitiesChanged being called for wifi with netId " + network.getNetId());
            this.mIsWifiConfigured = true;
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            int i;
            int i2;
            List<InetAddress> list;
            String str;
            String str2;
            String str3;
            int i3;
            int i4;
            NetworkCallback networkCallback = this;
            networkCallback.mDefaultInterface = linkProperties.getInterfaceName();
            String str4 = "KnoxNetworkEventService";
            Log.d(
                    "KnoxNetworkEventService",
                    "onLinkPropertiesChanged being called for netId "
                            + network.getNetId()
                            + " for interface "
                            + networkCallback.mDefaultInterface);
            List<InetAddress> dnsServers = linkProperties.getDnsServers();
            boolean contains = ((HashSet) KnoxNetworkEventService.this.mEnabledEvents).contains(15);
            KnoxNetworkEventFirewall.IpRestoreActionType ipRestoreActionType =
                    KnoxNetworkEventFirewall.IpRestoreActionType.INSERT;
            KnoxNetworkEventFirewall.IpRestoreActionType ipRestoreActionType2 =
                    KnoxNetworkEventFirewall.IpRestoreActionType.APPEND;
            String str5 = PackageManagerShellCommandDataLoader.STDIN_PATH;
            if (contains) {
                Log.d("KnoxNetworkEventService", "NetworkCallback: insertRulesForAbnormalPackets");
                Iterator it = ((HashSet) KnoxNetworkEventService.this.mUserIdList).iterator();
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                            intValue,
                            "NetworkCallback: insertRulesForAbnormalPackets userId = ",
                            str4);
                    KnoxNetworkEventFirewall knoxNetworkEventFirewall =
                            KnoxNetworkEventService.this.mKnoxNwEventFw;
                    String str6 = networkCallback.mDefaultInterface;
                    knoxNetworkEventFirewall.getClass();
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    if (intValue == 0) {
                        i2 = 19999;
                        i = 10000;
                    } else {
                        int i5 = intValue * 100000;
                        i = i5 + 1;
                        i2 = i5 + 99999;
                    }
                    String str7 = i + str5 + i2;
                    Iterator it2 = it;
                    String str8 = str5;
                    String str9 = str4;
                    arrayList.add(
                            new KnoxNetworkEventFirewall.IpRestoreParam(
                                    "knox_nw_event_2",
                                    "",
                                    "knox_nw_event_2_mch",
                                    "",
                                    ipRestoreActionType2));
                    arrayList2.add(
                            new KnoxNetworkEventFirewall.IpRestoreParam(
                                    "knox_nw_event_2",
                                    "",
                                    "knox_nw_event_2_mch",
                                    "",
                                    ipRestoreActionType2));
                    boolean z = false;
                    boolean z2 = false;
                    for (InetAddress inetAddress : dnsServers) {
                        if (inetAddress instanceof Inet4Address) {
                            StringBuilder m =
                                    DumpUtils$$ExternalSyntheticOutline0.m(
                                            " -m owner --uid-owner ",
                                            str7,
                                            " -p udp --dport 53 -d ");
                            m.append(inetAddress.getHostAddress());
                            m.append(" -o ");
                            m.append(str6);
                            list = dnsServers;
                            arrayList.add(
                                    new KnoxNetworkEventFirewall.IpRestoreParam(
                                            "knox_nw_event_2_mch",
                                            m.toString(),
                                            "RETURN",
                                            "",
                                            ipRestoreActionType));
                            z = true;
                        } else {
                            list = dnsServers;
                            if (inetAddress instanceof Inet6Address) {
                                StringBuilder m2 =
                                        DumpUtils$$ExternalSyntheticOutline0.m(
                                                " -m owner --uid-owner ",
                                                str7,
                                                " -p udp --dport 53 -d ");
                                m2.append(inetAddress.getHostAddress());
                                m2.append(" -o ");
                                m2.append(str6);
                                arrayList2.add(
                                        new KnoxNetworkEventFirewall.IpRestoreParam(
                                                "knox_nw_event_2_mch",
                                                m2.toString(),
                                                "RETURN",
                                                "",
                                                ipRestoreActionType));
                                z2 = true;
                            }
                        }
                        dnsServers = list;
                    }
                    List<InetAddress> list2 = dnsServers;
                    if (z) {
                        arrayList.add(
                                new KnoxNetworkEventFirewall.IpRestoreParam(
                                        "knox_nw_event_2_mch",
                                        BootReceiver$$ExternalSyntheticOutline0.m(
                                                " -m owner --uid-owner ",
                                                str7,
                                                " -p udp --dport 53 -o ",
                                                str6),
                                        "knox_nw_event_2_act",
                                        "",
                                        ipRestoreActionType2));
                    }
                    if (z2) {
                        arrayList2.add(
                                new KnoxNetworkEventFirewall.IpRestoreParam(
                                        "knox_nw_event_2_mch",
                                        BootReceiver$$ExternalSyntheticOutline0.m(
                                                " -m owner --uid-owner ",
                                                str7,
                                                " -p udp --dport 53 -o ",
                                                str6),
                                        "knox_nw_event_2_act",
                                        "",
                                        ipRestoreActionType2));
                    }
                    KnoxNetworkEventFirewall.insertRules(true, null, arrayList, 4);
                    KnoxNetworkEventFirewall.insertRules(true, null, arrayList2, 6);
                    str5 = str8;
                    str4 = str9;
                    it = it2;
                    dnsServers = list2;
                }
            }
            String str10 = str4;
            String str11 = str5;
            String str12 = networkCallback.mDefaultInterface;
            if (str12 == null || !networkCallback.mIsWifiConfigured) {
                return;
            }
            KnoxNetworkEventService.this.getClass();
            try {
                NetworkInterface byName = NetworkInterface.getByName(str12);
                if (byName != null) {
                    Iterator it3 =
                            ((ArrayList)
                                            KnoxNetworkEventService.excludeLinkLocal(
                                                    byName.getInterfaceAddresses()))
                                    .iterator();
                    while (it3.hasNext()) {
                        InterfaceAddress interfaceAddress = (InterfaceAddress) it3.next();
                        InetAddress networkPart =
                                KnoxNetworkEventService.getNetworkPart(
                                        interfaceAddress.getAddress(),
                                        interfaceAddress.getNetworkPrefixLength());
                        if (!(networkPart instanceof Inet6Address)) {
                            str2 =
                                    networkPart.getHostAddress()
                                            + "/"
                                            + ((int) interfaceAddress.getNetworkPrefixLength());
                            break;
                        }
                    }
                }
                str2 = null;
                str = str10;
            } catch (Exception e) {
                str = str10;
                VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder(" "), str);
                str2 = null;
            }
            KnoxNetworkEventService knoxNetworkEventService = KnoxNetworkEventService.this;
            String str13 = networkCallback.mDefaultInterface;
            knoxNetworkEventService.getClass();
            try {
                NetworkInterface byName2 = NetworkInterface.getByName(str13);
                if (byName2 != null) {
                    Iterator it4 =
                            ((ArrayList)
                                            KnoxNetworkEventService.excludeLinkLocal(
                                                    byName2.getInterfaceAddresses()))
                                    .iterator();
                    while (it4.hasNext()) {
                        InterfaceAddress interfaceAddress2 = (InterfaceAddress) it4.next();
                        if (interfaceAddress2.getBroadcast() instanceof Inet4Address) {
                            str3 = interfaceAddress2.getBroadcast().getHostAddress();
                            break;
                        }
                    }
                }
            } catch (SocketException unused) {
            }
            str3 = null;
            StringBuilder sb = new StringBuilder("mDefaultInterface: ");
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    sb,
                    networkCallback.mDefaultInterface,
                    " destIpRange: ",
                    str2,
                    " broadcastIpAddress: ");
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, str3, str);
            if (((HashSet) KnoxNetworkEventService.this.mEnabledEvents).contains(16)) {
                Log.d(str, "NetworkCallback: insertRuleForLocalNetworkPackets");
                Iterator it5 = ((HashSet) KnoxNetworkEventService.this.mUserIdList).iterator();
                while (it5.hasNext()) {
                    int intValue2 = ((Integer) it5.next()).intValue();
                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                            intValue2,
                            "NetworkCallback: insertRuleForLocalNetworkPackets userId = ",
                            str);
                    KnoxNetworkEventService.this.mKnoxNwEventFw.getClass();
                    ArrayList arrayList3 = new ArrayList();
                    if (intValue2 == 0) {
                        i4 = 19999;
                        i3 = 10000;
                    } else {
                        int i6 = intValue2 * 100000;
                        i3 = i6 + 1;
                        i4 = i6 + 99999;
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(i3);
                    String str14 = str11;
                    sb2.append(str14);
                    sb2.append(i4);
                    String sb3 = sb2.toString();
                    arrayList3.add(
                            new KnoxNetworkEventFirewall.IpRestoreParam(
                                    "knox_nw_event_3",
                                    "",
                                    "knox_nw_event_3_mch",
                                    "",
                                    ipRestoreActionType2));
                    arrayList3.add(
                            new KnoxNetworkEventFirewall.IpRestoreParam(
                                    "knox_nw_event_3_mch",
                                    XmlUtils$$ExternalSyntheticOutline0.m(
                                            " -m owner --uid-owner ",
                                            sb3,
                                            " -p udp -m multiport --dports 1900,5353"),
                                    "RETURN",
                                    "",
                                    ipRestoreActionType));
                    arrayList3.add(
                            new KnoxNetworkEventFirewall.IpRestoreParam(
                                    "knox_nw_event_3_mch",
                                    XmlUtils$$ExternalSyntheticOutline0.m(
                                            " -m owner --uid-owner ",
                                            sb3,
                                            " -p udp -d 224.0.0.0/24"),
                                    "knox_nw_event_3_act",
                                    "",
                                    ipRestoreActionType2));
                    arrayList3.add(
                            new KnoxNetworkEventFirewall.IpRestoreParam(
                                    "knox_nw_event_3_mch",
                                    BootReceiver$$ExternalSyntheticOutline0.m(
                                            " -m owner --uid-owner ", sb3, " -p udp -d ", str3),
                                    "knox_nw_event_3_act",
                                    "",
                                    ipRestoreActionType2));
                    arrayList3.add(
                            new KnoxNetworkEventFirewall.IpRestoreParam(
                                    "knox_nw_event_3_mch",
                                    BootReceiver$$ExternalSyntheticOutline0.m(
                                            " -m owner --uid-owner ",
                                            sb3,
                                            " -p tcp -m state --state NEW -d ",
                                            str2),
                                    "knox_nw_event_3_act",
                                    "",
                                    ipRestoreActionType2));
                    KnoxNetworkEventFirewall.insertRules(true, null, arrayList3, 46);
                    networkCallback = this;
                    str11 = str14;
                    str = str;
                }
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            this.mDefaultInterface = null;
            this.mIsWifiConfigured = false;
            Log.d("KnoxNetworkEventService", "onLost being called for netId " + network.getNetId());
            KnoxNetworkEventFirewall knoxNetworkEventFirewall =
                    KnoxNetworkEventService.this.mKnoxNwEventFw;
            KnoxNetworkEventFirewall.EventType eventType =
                    KnoxNetworkEventFirewall.EventType.ABNORMAL_PACKETS;
            knoxNetworkEventFirewall.getClass();
            KnoxNetworkEventFirewall.flushRulesForEvent(eventType);
            KnoxNetworkEventFirewall knoxNetworkEventFirewall2 =
                    KnoxNetworkEventService.this.mKnoxNwEventFw;
            KnoxNetworkEventFirewall.EventType eventType2 =
                    KnoxNetworkEventFirewall.EventType.LOCAL_NW;
            knoxNetworkEventFirewall2.getClass();
            KnoxNetworkEventFirewall.flushRulesForEvent(eventType2);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserActivityReceiver extends BroadcastReceiver {
        public UserActivityReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (isInitialStickyBroadcast()
                    || !action.equals("android.intent.action.USER_REMOVED")) {
                return;
            }
            Bundle extras = intent.getExtras();
            KnoxNetworkEventService knoxNetworkEventService = KnoxNetworkEventService.this;
            KnoxNwEventHandler knoxNwEventHandler = knoxNetworkEventService.mHandler;
            if (knoxNwEventHandler != null) {
                knoxNetworkEventService.mHandler.sendMessage(
                        Message.obtain(knoxNwEventHandler, 3, 0, 0, extras));
            }
        }
    }

    public KnoxNetworkEventService(Context context) {
        KnoxNetworkEventFirewall knoxNetworkEventFirewall;
        this.mContext = context;
        this.mCm = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        KnoxNetworkEventFirewall knoxNetworkEventFirewall2 =
                KnoxNetworkEventFirewall.mKnoxNwEventFw;
        synchronized (KnoxNetworkEventFirewall.class) {
            try {
                if (KnoxNetworkEventFirewall.mKnoxNwEventFw == null) {
                    KnoxNetworkEventFirewall.mKnoxNwEventFw = new KnoxNetworkEventFirewall();
                }
                knoxNetworkEventFirewall = KnoxNetworkEventFirewall.mKnoxNwEventFw;
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mKnoxNwEventFw = knoxNetworkEventFirewall;
        this.mEnabledEvents = new HashSet();
        HashSet hashSet = new HashSet();
        this.mUserIdList = hashSet;
        UserManager userManager = (UserManager) context.getSystemService("user");
        this.mUserManager = userManager;
        if (!SemPersonaManager.isDoEnabled(0)) {
            String str = SystemProperties.get("ro.organization_owned");
            if (str != null && str.equals("true")) {
                hashSet.add(0);
                Iterator<UserHandle> it = userManager.getUserProfiles().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    int semGetIdentifier = it.next().semGetIdentifier();
                    if (this.mUserManager.getUserInfo(semGetIdentifier).isManagedProfile()) {
                        this.mUserIdList.add(Integer.valueOf(semGetIdentifier));
                        break;
                    }
                }
            } else {
                Iterator<UserHandle> it2 = userManager.getUserProfiles().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    int semGetIdentifier2 = it2.next().semGetIdentifier();
                    if (this.mUserManager.getUserInfo(semGetIdentifier2).isManagedProfile()) {
                        this.mUserIdList.add(Integer.valueOf(semGetIdentifier2));
                        break;
                    }
                }
            }
        } else {
            hashSet.add(0);
        }
        HandlerThread handlerThread = new HandlerThread("KnoxNwEventHandler", 10);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new KnoxNwEventHandler(this.mHandlerThread.getLooper());
        registerUserActivityReceiver();
    }

    public static List excludeLinkLocal(List list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            InterfaceAddress interfaceAddress = (InterfaceAddress) it.next();
            if (!interfaceAddress.getAddress().isLinkLocalAddress()) {
                arrayList.add(interfaceAddress);
            }
        }
        return arrayList;
    }

    public static InetAddress getNetworkPart(InetAddress inetAddress, int i) {
        byte[] address = inetAddress.getAddress();
        if (i < 0 || i > address.length * 8) {
            throw new RuntimeException(
                    "IP address with " + address.length + " bytes has invalid prefix length " + i);
        }
        int i2 = i / 8;
        byte b =
                (byte)
                        (IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT
                                << (8 - (i % 8)));
        if (i2 < address.length) {
            address[i2] = (byte) (b & address[i2]);
        }
        while (true) {
            i2++;
            if (i2 >= address.length) {
                try {
                    return InetAddress.getByAddress(address);
                } catch (UnknownHostException e) {
                    throw new RuntimeException("getNetworkPart error - " + e.toString());
                }
            }
            address[i2] = 0;
        }
    }

    public final void registerSystemDefaultNetworkCallback() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ConnectivityManager connectivityManager = this.mCm;
                if (this.mDefaultNetworkCallback == null) {
                    this.mDefaultNetworkCallback = new NetworkCallback();
                }
                connectivityManager.registerSystemDefaultNetworkCallback(
                        this.mDefaultNetworkCallback, this.mHandler);
            } catch (RuntimeException e) {
                Log.e(
                        "KnoxNetworkEventService",
                        "Failed to register system default network callback "
                                + Log.getStackTraceString(e));
                this.mDefaultNetworkCallback = null;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void registerUserActivityReceiver() {
        if (this.mReceiver != null) {
            return;
        }
        this.mReceiver = new UserActivityReceiver();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_REMOVED");
            this.mContext.registerReceiverAsUser(
                    this.mReceiver, UserHandle.ALL, intentFilter, null, null);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void unregisterSystemDefaultNetworkCallback() {
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
                        "KnoxNetworkEventService",
                        "Failed to unregister system default network callback "
                                + Log.getStackTraceString(e));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
