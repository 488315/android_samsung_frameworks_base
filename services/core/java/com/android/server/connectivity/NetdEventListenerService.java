package com.android.server.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.INetdEventCallback;
import android.net.MacAddress;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkPolicyManager;
import android.net.NetworkRequest;
import android.net.metrics.INetdEventListener;
import android.net.metrics.NetworkMetrics;
import android.net.metrics.WakeupEvent;
import android.net.metrics.WakeupStats;
import android.os.BatteryStatsInternal;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.BitUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.RingBuffer;
import com.android.internal.util.TokenBucket;
import com.android.net.module.util.BaseNetdEventListener;
import com.android.server.LocalServices;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class NetdEventListenerService extends BaseNetdEventListener {
    public static final int[] ALLOWED_CALLBACK_TYPES = {0, 1, 2, 3, 4, 5};
    static final int WAKEUP_EVENT_BUFFER_LENGTH = 1024;
    static final String WAKEUP_EVENT_PREFIX_DELIM = ":";
    public final IntentFilter filter;
    public final TransportForNetIdNetworkCallback mCallback;
    public int mCheckedNetId;
    public final ConnectivityManager mCm;
    public Context mContext;
    public final DnsLocalLog mDnsErrorInfoLog;
    public DnsEventHandler mDnsHandler;
    public int mFailCount;
    public final INetdEventCallback[] mNetdEventCallbackList;
    public NetworkPolicyManager mPolicyManager;
    public final AnonymousClass1 receiver;
    public final SparseArray mNetworkMetrics = new SparseArray();
    public final RingBuffer mNetworkMetricsSnapshots =
            new RingBuffer(NetworkMetricsSnapshot.class, 144);
    public long mLastSnapshot = 0;
    public final ArrayMap mWakeupStats = new ArrayMap();
    public final RingBuffer mWakeupEvents = new RingBuffer(WakeupEvent.class, 1024);
    public final TokenBucket mConnectTb = new TokenBucket(15000, 5000);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DnsEventHandler extends Handler {
        public DnsEventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            NetworkCapabilities networkCapabilities;
            int i;
            if (message.what != 10001) {
                return;
            }
            DnsResultParams dnsResultParams = (DnsResultParams) message.obj;
            NetdEventListenerService netdEventListenerService = NetdEventListenerService.this;
            netdEventListenerService.getClass();
            int i2 = dnsResultParams.mReturnCode;
            int i3 = dnsResultParams.mUid;
            int i4 = dnsResultParams.mNetId;
            int i5 = dnsResultParams.mLatencyMs;
            Context context = netdEventListenerService.mContext;
            if (context == null) {
                return;
            }
            String[] packagesForUid = context.getPackageManager().getPackagesForUid(i3);
            String str = "";
            if (packagesForUid != null) {
                str = packagesForUid[0];
                if (packagesForUid.length > 1) {
                    str = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, ",...");
                }
            }
            if (i2 == 0) {
                StringBuilder sb = new StringBuilder("DNS Requested by ");
                ServiceKeeper$$ExternalSyntheticOutline0.m(
                        dnsResultParams.mNetId, i3, ", ", "(", sb);
                sb.append(str);
                sb.append("), ");
                sb.append(NetdEventListenerService.getReturnCodeToString(i2));
                sb.append(", ");
                AudioService$$ExternalSyntheticOutline0.m(sb, i5, "ms", "NetdEventListenerService");
                netdEventListenerService.mCheckedNetId = 0;
                netdEventListenerService.mFailCount = 0;
                return;
            }
            TransportForNetIdNetworkCallback transportForNetIdNetworkCallback =
                    netdEventListenerService.mCallback;
            synchronized (transportForNetIdNetworkCallback.mCapabilities) {
                networkCapabilities =
                        (NetworkCapabilities)
                                transportForNetIdNetworkCallback.mCapabilities.get(i4);
            }
            if (networkCapabilities == null || netdEventListenerService.mCm == null) {
                return;
            }
            boolean isUidNetworkingBlocked =
                    netdEventListenerService.mPolicyManager.isUidNetworkingBlocked(
                            i3, !networkCapabilities.hasCapability(11));
            StringBuilder sb2 = new StringBuilder("DNS Requested by ");
            ServiceKeeper$$ExternalSyntheticOutline0.m(dnsResultParams.mNetId, i3, ", ", "(", sb2);
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(i2, str, "), ", "(", sb2);
            sb2.append(NetdEventListenerService.getReturnCodeToString(i2));
            sb2.append("), isBlocked=");
            sb2.append(isUidNetworkingBlocked);
            sb2.append(", ");
            AudioService$$ExternalSyntheticOutline0.m(sb2, i5, "ms", "NetdEventListenerService");
            NetworkInfo activeNetworkInfo = netdEventListenerService.mCm.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return;
            }
            int type = activeNetworkInfo.getType();
            int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
            TelephonyManager telephonyManager =
                    new TelephonyManager(
                            netdEventListenerService.mContext, defaultDataSubscriptionId);
            SignalStrength signalStrength = telephonyManager.getSignalStrength();
            String networkOperatorName = telephonyManager.getNetworkOperatorName();
            boolean isNetworkRoaming = telephonyManager.isNetworkRoaming(defaultDataSubscriptionId);
            int i6 = dnsResultParams.mNetId;
            int level = signalStrength != null ? signalStrength.getLevel() : 0;
            if (netdEventListenerService.mFailCount == 20
                    && type == 0
                    && i6 != netdEventListenerService.mCheckedNetId) {
                netdEventListenerService.mCheckedNetId = i6;
                try {
                    DatagramSocket datagramSocket = new DatagramSocket();
                    try {
                        Log.e("NetdEventListenerService", "sending DNS");
                        datagramSocket.setSoTimeout(1000);
                        String[] split = dnsResultParams.mHostname.split("\\.");
                        Random random = new Random();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] bArr = new byte[2];
                        random.nextBytes(bArr);
                        byteArrayOutputStream.write(bArr);
                        byteArrayOutputStream.write(new byte[] {1, 0});
                        byteArrayOutputStream.write(new byte[] {0, 1});
                        byteArrayOutputStream.write(new byte[] {0, 0});
                        byteArrayOutputStream.write(new byte[] {0, 0});
                        byteArrayOutputStream.write(new byte[] {0, 0});
                        for (String str2 : split) {
                            byte[] bytes = str2.getBytes("US-ASCII");
                            byteArrayOutputStream.write(bytes.length);
                            byteArrayOutputStream.write(bytes);
                        }
                        byteArrayOutputStream.write(0);
                        byteArrayOutputStream.write(new byte[] {0, 1});
                        byteArrayOutputStream.write(new byte[] {0, 1});
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        datagramSocket.send(
                                new DatagramPacket(
                                        byteArray,
                                        byteArray.length,
                                        InetAddress.parseNumericAddress("8.8.8.8"),
                                        53));
                        int length = byteArray.length;
                        datagramSocket.receive(new DatagramPacket(new byte[length], length));
                        datagramSocket.close();
                        i = 0;
                    } finally {
                        datagramSocket.close();
                    }
                } catch (IOException e) {
                    Log.e("NetdEventListenerService", "IOException error: " + e);
                    i = 1;
                }
            } else {
                i = -1;
            }
            DnsLocalLog dnsLocalLog = netdEventListenerService.mDnsErrorInfoLog;
            String format =
                    String.format(
                            "%02d %02d %d %d %d %d %d %s %s",
                            Integer.valueOf(type),
                            Integer.valueOf(i2),
                            Integer.valueOf(i6),
                            Integer.valueOf(netdEventListenerService.mFailCount),
                            Integer.valueOf(i),
                            Integer.valueOf(isNetworkRoaming ? 1 : 0),
                            Integer.valueOf(level),
                            str,
                            networkOperatorName);
            if (dnsLocalLog.mMaxLines > 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                String format2 =
                        String.format(
                                "%ty-%tm-%td_%tH:%tM:%tS %s",
                                calendar, calendar, calendar, calendar, calendar, calendar, format);
                synchronized (dnsLocalLog) {
                    while (((ArrayDeque) dnsLocalLog.mLog).size() >= dnsLocalLog.mMaxLines) {
                        try {
                            ((ArrayDeque) dnsLocalLog.mLog).remove();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    ((ArrayDeque) dnsLocalLog.mLog).add(format2);
                }
            }
            int i7 = netdEventListenerService.mFailCount;
            if (i7 == 20) {
                netdEventListenerService.mFailCount = 0;
            } else {
                netdEventListenerService.mFailCount = i7 + 1;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DnsLocalLog {
        public final Deque mLog;
        public final int mMaxLines;

        public DnsLocalLog() {
            int max = Math.max(0, 1000);
            this.mMaxLines = max;
            this.mLog = new ArrayDeque(max);
        }

        public final synchronized void dump(PrintWriter printWriter) {
            Iterator it = ((ArrayDeque) this.mLog).iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                String str2 = "";
                for (int i = 0; i < str.length(); i++) {
                    int charAt = (str.charAt(i) + '6') % 127;
                    if (charAt < 96) {
                        charAt += 32;
                    }
                    str2 = str2 + ((char) charAt);
                }
                printWriter.println(str2);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DnsResultParams {
        public final String mHostname;
        public final int mLatencyMs;
        public final int mNetId;
        public final int mReturnCode;
        public final int mUid;

        public DnsResultParams(int i, int i2, int i3, int i4, String str) {
            this.mNetId = i;
            this.mReturnCode = i2;
            this.mHostname = str;
            this.mUid = i3;
            this.mLatencyMs = i4;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NetworkMetricsSnapshot {
        public List stats;
        public long timeMs;

        public static NetworkMetricsSnapshot collect(long j, SparseArray sparseArray) {
            NetworkMetricsSnapshot networkMetricsSnapshot = new NetworkMetricsSnapshot();
            networkMetricsSnapshot.stats = new ArrayList();
            networkMetricsSnapshot.timeMs = j;
            for (int i = 0; i < sparseArray.size(); i++) {
                NetworkMetrics.Summary pendingStats =
                        ((NetworkMetrics) sparseArray.valueAt(i)).getPendingStats();
                if (pendingStats != null) {
                    ((ArrayList) networkMetricsSnapshot.stats).add(pendingStats);
                }
            }
            return networkMetricsSnapshot;
        }

        public final String toString() {
            StringJoiner stringJoiner = new StringJoiner(", ");
            Iterator it = ((ArrayList) this.stats).iterator();
            while (it.hasNext()) {
                stringJoiner.add(((NetworkMetrics.Summary) it.next()).toString());
            }
            return String.format(
                    "%tT.%tL: %s",
                    Long.valueOf(this.timeMs), Long.valueOf(this.timeMs), stringJoiner.toString());
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TransportForNetIdNetworkCallback
            extends ConnectivityManager.NetworkCallback {
        public final SparseArray mCapabilities = new SparseArray();

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onCapabilitiesChanged(
                Network network, NetworkCapabilities networkCapabilities) {
            synchronized (this.mCapabilities) {
                this.mCapabilities.put(network.getNetId(), networkCapabilities);
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            synchronized (this.mCapabilities) {
                this.mCapabilities.remove(network.getNetId());
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.server.connectivity.NetdEventListenerService$1] */
    public NetdEventListenerService(ConnectivityManager connectivityManager) {
        TransportForNetIdNetworkCallback transportForNetIdNetworkCallback =
                new TransportForNetIdNetworkCallback();
        this.mCallback = transportForNetIdNetworkCallback;
        this.filter = new IntentFilter();
        this.mDnsErrorInfoLog = new DnsLocalLog();
        this.mDnsHandler = null;
        this.mNetdEventCallbackList = new INetdEventCallback[6];
        this.receiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.connectivity.NetdEventListenerService.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        if (intent == null || intent.getAction() == null) {
                            return;
                        }
                        String action = intent.getAction();
                        if (action.isEmpty()) {
                            return;
                        }
                        if (action.equals("com.samsung.android.mobiledoctor.GETMOBILEDATAFILES")
                                || action.equals(
                                        "com.samsung.android.action.ACTION_REQUEST_INTERNET_LOG")) {
                            NetdEventListenerService.this.writeMobileDataDnsFile();
                            return;
                        }
                        if (action.equals(
                                "com.samsung.android.mobiledoctor.DELETEMOBILEDATAFILES")) {
                            NetdEventListenerService.this.getClass();
                            File file = new File("/data/log/err/mobiledata_dns.dat");
                            if (file.exists()) {
                                file.delete();
                            }
                        }
                    }
                };
        this.mCm = connectivityManager;
        connectivityManager.registerNetworkCallback(
                new NetworkRequest.Builder().clearCapabilities().build(),
                transportForNetIdNetworkCallback);
    }

    public static String getReturnCodeToString(int i) {
        if (i == 255) {
            return "TIMEOUT";
        }
        switch (i) {
            case 0:
                return "SUCCESS";
            case 1:
                return "ADDRFAMILY";
            case 2:
                return "AGAIN";
            case 3:
                return "BADFLAGS";
            case 4:
                return "FAIL";
            case 5:
                return "FAMILY";
            case 6:
                return "MEMORY";
            case 7:
                return "NODATA";
            case 8:
                return "NONAME";
            case 9:
                return "SERVICE";
            case 10:
                return "SOCKTYPE";
            case 11:
                return "SYSTEM";
            case 12:
                return "BADHINTS";
            case 13:
                return "PROTOCOL";
            case 14:
                return "OVERFLOW";
            default:
                return "UNKNOWN";
        }
    }

    public final void collectPendingMetricsSnapshot(long j, boolean z) {
        if (z || Math.abs(j - this.mLastSnapshot) > 60000) {
            long j2 = (j / 60000) * 60000;
            this.mLastSnapshot = j2;
            NetworkMetricsSnapshot collect =
                    NetworkMetricsSnapshot.collect(j2, this.mNetworkMetrics);
            if (((ArrayList) collect.stats).isEmpty()) {
                return;
            }
            this.mNetworkMetricsSnapshots.append(collect);
        }
    }

    @Override // com.android.net.module.util.BaseNetdEventListener,
              // android.net.metrics.INetdEventListener
    public final String getInterfaceHash() {
        return INetdEventListener.HASH;
    }

    @Override // com.android.net.module.util.BaseNetdEventListener,
              // android.net.metrics.INetdEventListener
    public final int getInterfaceVersion() {
        return 1;
    }

    public final NetworkMetrics getMetricsForNetwork(int i, long j) {
        NetworkCapabilities networkCapabilities;
        NetworkMetrics networkMetrics = (NetworkMetrics) this.mNetworkMetrics.get(i);
        TransportForNetIdNetworkCallback transportForNetIdNetworkCallback = this.mCallback;
        synchronized (transportForNetIdNetworkCallback.mCapabilities) {
            networkCapabilities =
                    (NetworkCapabilities) transportForNetIdNetworkCallback.mCapabilities.get(i);
        }
        long packBits =
                networkCapabilities != null
                        ? BitUtils.packBits(networkCapabilities.getTransportTypes())
                        : 0L;
        boolean z =
                (networkMetrics == null
                                || networkCapabilities == null
                                || networkMetrics.transports == packBits)
                        ? false
                        : true;
        collectPendingMetricsSnapshot(j, z);
        if (networkMetrics != null && !z) {
            return networkMetrics;
        }
        NetworkMetrics networkMetrics2 = new NetworkMetrics(i, packBits, this.mConnectTb);
        this.mNetworkMetrics.put(i, networkMetrics2);
        return networkMetrics2;
    }

    @Override // com.android.net.module.util.BaseNetdEventListener,
              // android.net.metrics.INetdEventListener
    public final synchronized void onConnectEvent(
            int i, int i2, int i3, String str, int i4, int i5) {
        long currentTimeMillis = System.currentTimeMillis();
        getMetricsForNetwork(i, currentTimeMillis).addConnectResult(i2, i3, str);
        for (INetdEventCallback iNetdEventCallback : this.mNetdEventCallbackList) {
            if (iNetdEventCallback != null) {
                try {
                    iNetdEventCallback.onConnectEvent(str, i4, currentTimeMillis, i5);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @Override // com.android.net.module.util.BaseNetdEventListener,
              // android.net.metrics.INetdEventListener
    public final synchronized void onDnsEvent(
            int i, int i2, int i3, int i4, String str, String[] strArr, int i5, int i6) {
        int i7;
        INetdEventCallback[] iNetdEventCallbackArr;
        int i8;
        long currentTimeMillis = System.currentTimeMillis();
        getMetricsForNetwork(i, currentTimeMillis).addDnsResult(i2, i3, i4);
        INetdEventCallback[] iNetdEventCallbackArr2 = this.mNetdEventCallbackList;
        int length = iNetdEventCallbackArr2.length;
        int i9 = 0;
        int i10 = 0;
        while (i9 < length) {
            INetdEventCallback iNetdEventCallback = iNetdEventCallbackArr2[i9];
            if (iNetdEventCallback != null) {
                try {
                    i7 = i9;
                    iNetdEventCallbackArr = iNetdEventCallbackArr2;
                    i8 = length;
                    iNetdEventCallback.onDnsEvent(
                            i,
                            i2,
                            i3,
                            str,
                            strArr,
                            i5,
                            ALLOWED_CALLBACK_TYPES[i10] == 4 ? i4 : currentTimeMillis,
                            i6);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } else {
                i7 = i9;
                iNetdEventCallbackArr = iNetdEventCallbackArr2;
                i8 = length;
            }
            i10++;
            i9 = i7 + 1;
            iNetdEventCallbackArr2 = iNetdEventCallbackArr;
            length = i8;
        }
        DnsEventHandler dnsEventHandler = this.mDnsHandler;
        if (dnsEventHandler != null) {
            Message obtainMessage = dnsEventHandler.obtainMessage(10001);
            obtainMessage.obj = new DnsResultParams(i, i3, i6, i4, str);
            this.mDnsHandler.sendMessage(obtainMessage);
        }
    }

    @Override // com.android.net.module.util.BaseNetdEventListener,
              // android.net.metrics.INetdEventListener
    public final synchronized void onNat64PrefixEvent(int i, boolean z, String str, int i2) {
        for (INetdEventCallback iNetdEventCallback : this.mNetdEventCallbackList) {
            if (iNetdEventCallback != null) {
                try {
                    iNetdEventCallback.onNat64PrefixEvent(i, z, str, i2);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @Override // com.android.net.module.util.BaseNetdEventListener,
              // android.net.metrics.INetdEventListener
    public final synchronized void onPrivateDnsValidationEvent(
            int i, String str, String str2, boolean z) {
        for (INetdEventCallback iNetdEventCallback : this.mNetdEventCallbackList) {
            if (iNetdEventCallback != null) {
                try {
                    iNetdEventCallback.onPrivateDnsValidationEvent(i, str, str2, z);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @Override // com.android.net.module.util.BaseNetdEventListener,
              // android.net.metrics.INetdEventListener
    public final synchronized void onTcpSocketStatsEvent(
            int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5) {
        if (iArr.length == iArr2.length
                && iArr.length == iArr3.length
                && iArr.length == iArr4.length
                && iArr.length == iArr5.length) {
            long currentTimeMillis = System.currentTimeMillis();
            for (int i = 0; i < iArr.length; i++) {
                int i2 = iArr[i];
                getMetricsForNetwork(i2, currentTimeMillis)
                        .addTcpStatsResult(iArr2[i], iArr3[i], iArr4[i], iArr5[i]);
            }
            return;
        }
        Log.e("NetdEventListenerService", "Mismatched lengths of TCP socket stats data arrays");
    }

    @Override // com.android.net.module.util.BaseNetdEventListener,
              // android.net.metrics.INetdEventListener
    public final synchronized void onWakeupEvent(
            String str,
            int i,
            int i2,
            int i3,
            byte[] bArr,
            String str2,
            String str3,
            int i4,
            int i5,
            long j) {
        try {
            String[] split = str.split(WAKEUP_EVENT_PREFIX_DELIM);
            if (split.length != 2) {
                throw new IllegalArgumentException(
                        "Prefix " + str + " required in format <nethandle>:<interface>");
            }
            Network fromNetworkHandle = Network.fromNetworkHandle(Long.parseLong(split[0]));
            WakeupEvent wakeupEvent = new WakeupEvent();
            wakeupEvent.iface = split[1];
            wakeupEvent.uid = i;
            wakeupEvent.ethertype = i2;
            if (ArrayUtils.isEmpty(bArr)) {
                if (this.mCm.getNetworkCapabilities(fromNetworkHandle).hasTransport(1)) {
                    Log.e(
                            "NetdEventListenerService",
                            "Empty mac address on WiFi transport, network: " + fromNetworkHandle);
                }
                wakeupEvent.dstHwAddr = null;
            } else {
                wakeupEvent.dstHwAddr = MacAddress.fromBytes(bArr);
            }
            wakeupEvent.srcIp = str2;
            wakeupEvent.dstIp = str3;
            wakeupEvent.ipNextHeader = i3;
            wakeupEvent.srcPort = i4;
            wakeupEvent.dstPort = i5;
            if (j > 0) {
                wakeupEvent.timestampMs = j / 1000000;
            } else {
                wakeupEvent.timestampMs = System.currentTimeMillis();
            }
            String str4 = wakeupEvent.iface;
            this.mWakeupEvents.append(wakeupEvent);
            WakeupStats wakeupStats = (WakeupStats) this.mWakeupStats.get(str4);
            if (wakeupStats == null) {
                wakeupStats = new WakeupStats(str4);
                this.mWakeupStats.put(str4, wakeupStats);
            }
            wakeupStats.countEvent(wakeupEvent);
            BatteryStatsInternal batteryStatsInternal =
                    (BatteryStatsInternal) LocalServices.getService(BatteryStatsInternal.class);
            if (batteryStatsInternal != null) {
                batteryStatsInternal.noteCpuWakingNetworkPacket(
                        fromNetworkHandle,
                        (SystemClock.elapsedRealtime() + wakeupEvent.timestampMs)
                                - System.currentTimeMillis(),
                        wakeupEvent.uid);
            }
            FrameworkStatsLog.write(
                    44,
                    i,
                    wakeupEvent.iface,
                    i2,
                    String.valueOf(wakeupEvent.dstHwAddr),
                    str2,
                    str3,
                    i3,
                    i4,
                    i5);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void writeMobileDataDnsFile() {
        File file = new File("/data/log/err");
        Log.d("NetdEventListenerService", "writeMobileDataDnsFile");
        if (!file.exists()) {
            Log.d("NetdEventListenerService", "!dir.exists");
            if (!file.mkdir()) {
                Log.d("NetdEventListenerService", "dir not created");
                return;
            }
        }
        Log.d("NetdEventListenerService", "end of dir");
        File file2 = new File("/data/log/err/mobiledata_dns.dat");
        PrintWriter printWriter = null;
        try {
            try {
                file2.createNewFile();
                file2.setReadable(true, false);
                PrintWriter printWriter2 = new PrintWriter(file2);
                try {
                    this.mDnsErrorInfoLog.dump(printWriter2);
                    printWriter2.flush();
                    printWriter2.close();
                } catch (Exception e) {
                    e = e;
                    printWriter = printWriter2;
                    e.printStackTrace();
                    if (printWriter != null) {
                        printWriter.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    printWriter = printWriter2;
                    if (printWriter != null) {
                        printWriter.close();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
