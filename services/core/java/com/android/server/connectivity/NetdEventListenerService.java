package com.android.server.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.INetdEventCallback;
import android.net.MacAddress;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkPolicyManager;
import android.net.NetworkRequest;
import android.net.metrics.ConnectStats;
import android.net.metrics.DnsEvent;
import android.net.metrics.INetdEventListener;
import android.net.metrics.NetworkMetrics;
import android.net.metrics.WakeupEvent;
import android.net.metrics.WakeupStats;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.p005os.BatteryStatsInternal;
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

/* loaded from: classes.dex */
public class NetdEventListenerService extends BaseNetdEventListener {
    static final int WAKEUP_EVENT_BUFFER_LENGTH = 1024;
    static final String WAKEUP_EVENT_PREFIX_DELIM = ":";
    public final int MSG_CHECK_NETWORKERROR;
    public final IntentFilter filter;
    public final TransportForNetIdNetworkCallback mCallback;
    public int mCheckedNetId;
    public final ConnectivityManager mCm;
    public final TokenBucket mConnectTb;
    public Context mContext;
    public DnsLocalLog mDnsErrorInfoLog;
    public Handler mDnsHandler;
    public int mFailCount;
    public long mLastSnapshot;
    public INetdEventCallback[] mNetdEventCallbackList;
    public final SparseArray mNetworkMetrics;
    public final RingBuffer mNetworkMetricsSnapshots;
    public NetworkPolicyManager mPolicyManager;
    public final RingBuffer mWakeupEvents;
    public final ArrayMap mWakeupStats;
    public final BroadcastReceiver receiver;
    public static final String TAG = NetdEventListenerService.class.getSimpleName();
    public static final int[] ALLOWED_CALLBACK_TYPES = {0, 1, 2, 3, 4};

    @Override // com.android.net.module.util.BaseNetdEventListener, android.net.metrics.INetdEventListener
    public String getInterfaceHash() {
        return INetdEventListener.HASH;
    }

    @Override // com.android.net.module.util.BaseNetdEventListener, android.net.metrics.INetdEventListener
    public int getInterfaceVersion() {
        return 1;
    }

    public synchronized boolean addNetdEventCallback(int i, INetdEventCallback iNetdEventCallback) {
        if (!isValidCallerType(i)) {
            Log.e(TAG, "Invalid caller type: " + i);
            return false;
        }
        this.mNetdEventCallbackList[i] = iNetdEventCallback;
        return true;
    }

    public synchronized boolean removeNetdEventCallback(int i) {
        if (!isValidCallerType(i)) {
            Log.e(TAG, "Invalid caller type: " + i);
            return false;
        }
        this.mNetdEventCallbackList[i] = null;
        return true;
    }

    public static boolean isValidCallerType(int i) {
        int i2 = 0;
        while (true) {
            int[] iArr = ALLOWED_CALLBACK_TYPES;
            if (i2 >= iArr.length) {
                return false;
            }
            if (i == iArr[i2]) {
                return true;
            }
            i2++;
        }
    }

    public NetdEventListenerService(Context context) {
        this((ConnectivityManager) context.getSystemService(ConnectivityManager.class));
        this.mContext = context;
        this.filter.addAction("com.samsung.android.mobiledoctor.GETMOBILEDATAFILES");
        this.filter.addAction("com.samsung.android.mobiledoctor.DELETEMOBILEDATAFILES");
        this.filter.addAction("com.samsung.android.action.ACTION_REQUEST_INTERNET_LOG");
        this.mContext.registerReceiver(this.receiver, this.filter);
        String str = TAG;
        HandlerThread handlerThread = new HandlerThread(str);
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        if (looper != null) {
            this.mDnsHandler = new DnsEventHandler(looper);
        } else {
            Log.e(str, "handlerThread.getLooper() returned null");
        }
        this.mPolicyManager = (NetworkPolicyManager) this.mContext.getSystemService(NetworkPolicyManager.class);
    }

    public NetdEventListenerService(ConnectivityManager connectivityManager) {
        this.mNetworkMetrics = new SparseArray();
        this.mNetworkMetricsSnapshots = new RingBuffer(NetworkMetricsSnapshot.class, 144);
        this.mLastSnapshot = 0L;
        this.mWakeupStats = new ArrayMap();
        this.mWakeupEvents = new RingBuffer(WakeupEvent.class, 1024);
        this.mConnectTb = new TokenBucket(15000, 5000);
        TransportForNetIdNetworkCallback transportForNetIdNetworkCallback = new TransportForNetIdNetworkCallback();
        this.mCallback = transportForNetIdNetworkCallback;
        this.filter = new IntentFilter();
        this.mDnsErrorInfoLog = new DnsLocalLog(1000);
        this.mDnsHandler = null;
        this.MSG_CHECK_NETWORKERROR = 10001;
        this.mNetdEventCallbackList = new INetdEventCallback[ALLOWED_CALLBACK_TYPES.length];
        this.receiver = new BroadcastReceiver() { // from class: com.android.server.connectivity.NetdEventListenerService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent == null || intent.getAction() == null) {
                    return;
                }
                String action = intent.getAction();
                if (action.isEmpty()) {
                    return;
                }
                if (action.equals("com.samsung.android.mobiledoctor.GETMOBILEDATAFILES") || action.equals("com.samsung.android.action.ACTION_REQUEST_INTERNET_LOG")) {
                    NetdEventListenerService.this.writeMobileDataDnsFile();
                } else if (action.equals("com.samsung.android.mobiledoctor.DELETEMOBILEDATAFILES")) {
                    NetdEventListenerService.this.deleteMobileDataLogFile();
                }
            }
        };
        this.mCm = connectivityManager;
        connectivityManager.registerNetworkCallback(new NetworkRequest.Builder().clearCapabilities().build(), transportForNetIdNetworkCallback);
    }

    public static long projectSnapshotTime(long j) {
        return (j / 60000) * 60000;
    }

    public final NetworkMetrics getMetricsForNetwork(long j, int i) {
        NetworkMetrics networkMetrics = (NetworkMetrics) this.mNetworkMetrics.get(i);
        NetworkCapabilities networkCapabilities = this.mCallback.getNetworkCapabilities(i);
        long packBits = networkCapabilities != null ? BitUtils.packBits(networkCapabilities.getTransportTypes()) : 0L;
        boolean z = (networkMetrics == null || networkCapabilities == null || networkMetrics.transports == packBits) ? false : true;
        collectPendingMetricsSnapshot(j, z);
        if (networkMetrics != null && !z) {
            return networkMetrics;
        }
        NetworkMetrics networkMetrics2 = new NetworkMetrics(i, packBits, this.mConnectTb);
        this.mNetworkMetrics.put(i, networkMetrics2);
        return networkMetrics2;
    }

    public final NetworkMetricsSnapshot[] getNetworkMetricsSnapshots() {
        collectPendingMetricsSnapshot(System.currentTimeMillis(), false);
        return (NetworkMetricsSnapshot[]) this.mNetworkMetricsSnapshots.toArray();
    }

    public final void collectPendingMetricsSnapshot(long j, boolean z) {
        if (z || Math.abs(j - this.mLastSnapshot) > 60000) {
            long projectSnapshotTime = projectSnapshotTime(j);
            this.mLastSnapshot = projectSnapshotTime;
            NetworkMetricsSnapshot collect = NetworkMetricsSnapshot.collect(projectSnapshotTime, this.mNetworkMetrics);
            if (collect.stats.isEmpty()) {
                return;
            }
            this.mNetworkMetricsSnapshots.append(collect);
        }
    }

    public class DnsResultParams {
        public final String mHostname;
        public final int mLatencyMs;
        public final int mNetId;
        public final int mReturnCode;
        public final int mUid;

        public DnsResultParams(int i, int i2, String str, int i3, int i4) {
            this.mNetId = i;
            this.mReturnCode = i2;
            this.mHostname = str;
            this.mUid = i3;
            this.mLatencyMs = i4;
        }
    }

    @Override // com.android.net.module.util.BaseNetdEventListener, android.net.metrics.INetdEventListener
    public synchronized void onDnsEvent(int i, int i2, int i3, int i4, String str, String[] strArr, int i5, int i6) {
        int i7;
        INetdEventCallback[] iNetdEventCallbackArr;
        int i8;
        long currentTimeMillis = System.currentTimeMillis();
        getMetricsForNetwork(currentTimeMillis, i).addDnsResult(i2, i3, i4);
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
                    iNetdEventCallback.onDnsEvent(i, i2, i3, str, strArr, i5, ALLOWED_CALLBACK_TYPES[i10] == 4 ? i4 : currentTimeMillis, i6);
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
        Handler handler = this.mDnsHandler;
        if (handler != null) {
            Message obtainMessage = handler.obtainMessage(10001);
            obtainMessage.obj = new DnsResultParams(i, i3, str, i6, i4);
            this.mDnsHandler.sendMessage(obtainMessage);
        }
    }

    public void dnsHandleEvent(DnsResultParams dnsResultParams) {
        int i;
        int i2 = dnsResultParams.mReturnCode;
        int i3 = dnsResultParams.mUid;
        int i4 = dnsResultParams.mNetId;
        int i5 = dnsResultParams.mLatencyMs;
        if (i2 != 0 && this.mContext != null) {
            if (this.mCallback.getNetworkCapabilities(i4) == null || this.mCm == null) {
                return;
            }
            boolean isUidNetworkingBlocked = this.mPolicyManager.isUidNetworkingBlocked(i3, !r4.hasCapability(11));
            String str = TAG;
            Log.d(str, "DNS Requested by " + dnsResultParams.mNetId + ", " + i3 + ", " + i2 + ", " + isUidNetworkingBlocked + ", " + i5);
            NetworkInfo activeNetworkInfo = this.mCm.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return;
            }
            int type = activeNetworkInfo.getType();
            int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
            TelephonyManager telephonyManager = new TelephonyManager(this.mContext, defaultDataSubscriptionId);
            SignalStrength signalStrength = telephonyManager.getSignalStrength();
            String networkOperatorName = telephonyManager.getNetworkOperatorName();
            boolean isNetworkRoaming = telephonyManager.isNetworkRoaming(defaultDataSubscriptionId);
            int i6 = dnsResultParams.mNetId;
            String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i3);
            String str2 = packagesForUid != null ? packagesForUid[0] : "";
            int level = signalStrength != null ? signalStrength.getLevel() : 0;
            if (this.mFailCount == 20 && type == 0 && i6 != this.mCheckedNetId) {
                this.mCheckedNetId = i6;
                try {
                    DatagramSocket datagramSocket = new DatagramSocket();
                    try {
                        Log.e(str, "sending DNS");
                        datagramSocket.setSoTimeout(1000);
                        String[] split = dnsResultParams.mHostname.split("\\.");
                        Random random = new Random();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] bArr = new byte[2];
                        random.nextBytes(bArr);
                        byteArrayOutputStream.write(bArr);
                        byteArrayOutputStream.write(new byte[]{1, 0});
                        byteArrayOutputStream.write(new byte[]{0, 1});
                        byteArrayOutputStream.write(new byte[]{0, 0});
                        byteArrayOutputStream.write(new byte[]{0, 0});
                        byteArrayOutputStream.write(new byte[]{0, 0});
                        for (String str3 : split) {
                            byte[] bytes = str3.getBytes("US-ASCII");
                            byteArrayOutputStream.write(bytes.length);
                            byteArrayOutputStream.write(bytes);
                        }
                        byteArrayOutputStream.write(0);
                        byteArrayOutputStream.write(new byte[]{0, 1});
                        byteArrayOutputStream.write(new byte[]{0, 1});
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        datagramSocket.send(new DatagramPacket(byteArray, byteArray.length, InetAddress.parseNumericAddress("8.8.8.8"), 53));
                        int length = byteArray.length;
                        datagramSocket.receive(new DatagramPacket(new byte[length], length));
                        datagramSocket.close();
                        i = 0;
                    } catch (Throwable th) {
                        datagramSocket.close();
                        throw th;
                    }
                } catch (IOException e) {
                    Log.e(TAG, "IOException error: " + e);
                    i = 1;
                }
            } else {
                i = -1;
            }
            this.mDnsErrorInfoLog.log(String.format("%02d %02d %d %d %d %d %d %s %s", Integer.valueOf(type), Integer.valueOf(i2), Integer.valueOf(i6), Integer.valueOf(this.mFailCount), Integer.valueOf(i), Integer.valueOf(isNetworkRoaming ? 1 : 0), Integer.valueOf(level), str2, networkOperatorName));
            int i7 = this.mFailCount;
            if (i7 == 20) {
                this.mFailCount = 0;
                return;
            } else {
                this.mFailCount = i7 + 1;
                return;
            }
        }
        Log.d(TAG, "DNS Requested by " + dnsResultParams.mNetId + ", " + i3 + ", " + i5);
        this.mCheckedNetId = 0;
        this.mFailCount = 0;
    }

    public class DnsEventHandler extends Handler {
        public DnsEventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 10001) {
                return;
            }
            NetdEventListenerService.this.dnsHandleEvent((DnsResultParams) message.obj);
        }
    }

    @Override // com.android.net.module.util.BaseNetdEventListener, android.net.metrics.INetdEventListener
    public synchronized void onNat64PrefixEvent(int i, boolean z, String str, int i2) {
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

    @Override // com.android.net.module.util.BaseNetdEventListener, android.net.metrics.INetdEventListener
    public synchronized void onPrivateDnsValidationEvent(int i, String str, String str2, boolean z) {
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

    @Override // com.android.net.module.util.BaseNetdEventListener, android.net.metrics.INetdEventListener
    public synchronized void onConnectEvent(int i, int i2, int i3, String str, int i4, int i5) {
        long currentTimeMillis = System.currentTimeMillis();
        getMetricsForNetwork(currentTimeMillis, i).addConnectResult(i2, i3, str);
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

    public final boolean hasWifiTransport(Network network) {
        return this.mCm.getNetworkCapabilities(network).hasTransport(1);
    }

    @Override // com.android.net.module.util.BaseNetdEventListener, android.net.metrics.INetdEventListener
    public synchronized void onWakeupEvent(String str, int i, int i2, int i3, byte[] bArr, String str2, String str3, int i4, int i5, long j) {
        String[] split = str.split(":");
        if (split.length != 2) {
            throw new IllegalArgumentException("Prefix " + str + " required in format <nethandle>:<interface>");
        }
        Network fromNetworkHandle = Network.fromNetworkHandle(Long.parseLong(split[0]));
        WakeupEvent wakeupEvent = new WakeupEvent();
        wakeupEvent.iface = split[1];
        wakeupEvent.uid = i;
        wakeupEvent.ethertype = i2;
        if (ArrayUtils.isEmpty(bArr)) {
            if (hasWifiTransport(fromNetworkHandle)) {
                Log.e(TAG, "Empty mac address on WiFi transport, network: " + fromNetworkHandle);
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
        addWakeupEvent(wakeupEvent);
        BatteryStatsInternal batteryStatsInternal = (BatteryStatsInternal) LocalServices.getService(BatteryStatsInternal.class);
        if (batteryStatsInternal != null) {
            batteryStatsInternal.noteCpuWakingNetworkPacket(fromNetworkHandle, (SystemClock.elapsedRealtime() + wakeupEvent.timestampMs) - System.currentTimeMillis(), wakeupEvent.uid);
        }
        FrameworkStatsLog.write(44, i, wakeupEvent.iface, i2, String.valueOf(wakeupEvent.dstHwAddr), str2, str3, i3, i4, i5);
    }

    @Override // com.android.net.module.util.BaseNetdEventListener, android.net.metrics.INetdEventListener
    public synchronized void onTcpSocketStatsEvent(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5) {
        if (iArr.length == iArr2.length && iArr.length == iArr3.length && iArr.length == iArr4.length && iArr.length == iArr5.length) {
            long currentTimeMillis = System.currentTimeMillis();
            for (int i = 0; i < iArr.length; i++) {
                int i2 = iArr[i];
                getMetricsForNetwork(currentTimeMillis, i2).addTcpStatsResult(iArr2[i], iArr3[i], iArr4[i], iArr5[i]);
            }
            return;
        }
        Log.e(TAG, "Mismatched lengths of TCP socket stats data arrays");
    }

    public final void addWakeupEvent(WakeupEvent wakeupEvent) {
        String str = wakeupEvent.iface;
        this.mWakeupEvents.append(wakeupEvent);
        WakeupStats wakeupStats = (WakeupStats) this.mWakeupStats.get(str);
        if (wakeupStats == null) {
            wakeupStats = new WakeupStats(str);
            this.mWakeupStats.put(str, wakeupStats);
        }
        wakeupStats.countEvent(wakeupEvent);
    }

    public synchronized void flushStatistics(List list) {
        for (int i = 0; i < this.mNetworkMetrics.size(); i++) {
            ConnectStats connectStats = ((NetworkMetrics) this.mNetworkMetrics.valueAt(i)).connectMetrics;
            if (connectStats.eventCount != 0) {
                list.add(IpConnectivityEventBuilder.toProto(connectStats));
            }
        }
        for (int i2 = 0; i2 < this.mNetworkMetrics.size(); i2++) {
            DnsEvent dnsEvent = ((NetworkMetrics) this.mNetworkMetrics.valueAt(i2)).dnsMetrics;
            if (dnsEvent.eventCount != 0) {
                list.add(IpConnectivityEventBuilder.toProto(dnsEvent));
            }
        }
        for (int i3 = 0; i3 < this.mWakeupStats.size(); i3++) {
            list.add(IpConnectivityEventBuilder.toProto((WakeupStats) this.mWakeupStats.valueAt(i3)));
        }
        this.mNetworkMetrics.clear();
        this.mWakeupStats.clear();
    }

    public synchronized void list(PrintWriter printWriter) {
        NetworkMetricsSnapshot collect = NetworkMetricsSnapshot.collect(System.currentTimeMillis(), this.mNetworkMetrics);
        if (!collect.stats.isEmpty()) {
            this.mNetworkMetricsSnapshots.append(collect);
        }
        printWriter.println("dns/connect events:");
        for (int i = 0; i < this.mNetworkMetrics.size(); i++) {
            printWriter.println(((NetworkMetrics) this.mNetworkMetrics.valueAt(i)).connectMetrics);
        }
        for (int i2 = 0; i2 < this.mNetworkMetrics.size(); i2++) {
            printWriter.println(((NetworkMetrics) this.mNetworkMetrics.valueAt(i2)).dnsMetrics);
        }
        printWriter.println("");
        printWriter.println("network statistics:");
        for (NetworkMetricsSnapshot networkMetricsSnapshot : getNetworkMetricsSnapshots()) {
            printWriter.println(networkMetricsSnapshot);
        }
        printWriter.println("");
        printWriter.println("packet wakeup events:");
        for (int i3 = 0; i3 < this.mWakeupStats.size(); i3++) {
            printWriter.println(this.mWakeupStats.valueAt(i3));
        }
        for (WakeupEvent wakeupEvent : (WakeupEvent[]) this.mWakeupEvents.toArray()) {
            printWriter.println(wakeupEvent);
        }
    }

    public synchronized List listAsProtos() {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (int i = 0; i < this.mNetworkMetrics.size(); i++) {
            arrayList.add(IpConnectivityEventBuilder.toProto(((NetworkMetrics) this.mNetworkMetrics.valueAt(i)).connectMetrics));
        }
        for (int i2 = 0; i2 < this.mNetworkMetrics.size(); i2++) {
            arrayList.add(IpConnectivityEventBuilder.toProto(((NetworkMetrics) this.mNetworkMetrics.valueAt(i2)).dnsMetrics));
        }
        for (int i3 = 0; i3 < this.mWakeupStats.size(); i3++) {
            arrayList.add(IpConnectivityEventBuilder.toProto((WakeupStats) this.mWakeupStats.valueAt(i3)));
        }
        return arrayList;
    }

    public class NetworkMetricsSnapshot {
        public List stats = new ArrayList();
        public long timeMs;

        public static NetworkMetricsSnapshot collect(long j, SparseArray sparseArray) {
            NetworkMetricsSnapshot networkMetricsSnapshot = new NetworkMetricsSnapshot();
            networkMetricsSnapshot.timeMs = j;
            for (int i = 0; i < sparseArray.size(); i++) {
                NetworkMetrics.Summary pendingStats = ((NetworkMetrics) sparseArray.valueAt(i)).getPendingStats();
                if (pendingStats != null) {
                    networkMetricsSnapshot.stats.add(pendingStats);
                }
            }
            return networkMetricsSnapshot;
        }

        public String toString() {
            StringJoiner stringJoiner = new StringJoiner(", ");
            Iterator it = this.stats.iterator();
            while (it.hasNext()) {
                stringJoiner.add(((NetworkMetrics.Summary) it.next()).toString());
            }
            return String.format("%tT.%tL: %s", Long.valueOf(this.timeMs), Long.valueOf(this.timeMs), stringJoiner.toString());
        }
    }

    public class TransportForNetIdNetworkCallback extends ConnectivityManager.NetworkCallback {
        public final SparseArray mCapabilities;

        public TransportForNetIdNetworkCallback() {
            this.mCapabilities = new SparseArray();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            synchronized (this.mCapabilities) {
                this.mCapabilities.put(network.getNetId(), networkCapabilities);
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            synchronized (this.mCapabilities) {
                this.mCapabilities.remove(network.getNetId());
            }
        }

        public NetworkCapabilities getNetworkCapabilities(int i) {
            NetworkCapabilities networkCapabilities;
            synchronized (this.mCapabilities) {
                networkCapabilities = (NetworkCapabilities) this.mCapabilities.get(i);
            }
            return networkCapabilities;
        }
    }

    public class DnsLocalLog {
        public final Deque mLog;
        public final int mMaxLines;

        public DnsLocalLog(int i) {
            int max = Math.max(0, i);
            this.mMaxLines = max;
            this.mLog = new ArrayDeque(max);
        }

        public final synchronized void append(String str) {
            while (this.mLog.size() >= this.mMaxLines) {
                this.mLog.remove();
            }
            this.mLog.add(str);
        }

        public final String encrypt(String str, int i) {
            String str2 = "";
            for (int i2 = 0; i2 < str.length(); i2++) {
                int charAt = (str.charAt(i2) + i) % 127;
                if (charAt < 96) {
                    charAt += 32;
                }
                str2 = str2 + ((char) charAt);
            }
            return str2;
        }

        public void log(String str) {
            if (this.mMaxLines <= 0) {
                return;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            append(String.format("%ty-%tm-%td_%tH:%tM:%tS %s", calendar, calendar, calendar, calendar, calendar, calendar, str));
        }

        public synchronized void dump(PrintWriter printWriter) {
            Iterator it = this.mLog.iterator();
            while (it.hasNext()) {
                printWriter.println(encrypt((String) it.next(), 54));
            }
        }
    }

    public final void deleteMobileDataLogFile() {
        File file = new File("/data/log/err/mobiledata_dns.dat");
        if (file.exists()) {
            file.delete();
        }
    }

    public void writeMobileDataDnsFile() {
        PrintWriter printWriter;
        File file = new File("/data/log/err");
        String str = TAG;
        Log.d(str, "writeMobileDataDnsFile");
        if (!file.exists()) {
            Log.d(str, "!dir.exists");
            if (!file.mkdir()) {
                Log.d(str, "dir not created");
                return;
            }
        }
        Log.d(str, "end of dir");
        File file2 = new File("/data/log/err/mobiledata_dns.dat");
        PrintWriter printWriter2 = null;
        try {
            try {
                file2.createNewFile();
                file2.setReadable(true, false);
                printWriter = new PrintWriter(file2);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            this.mDnsErrorInfoLog.dump(printWriter);
            printWriter.flush();
            printWriter.close();
        } catch (Exception e2) {
            e = e2;
            printWriter2 = printWriter;
            e.printStackTrace();
            if (printWriter2 != null) {
                printWriter2.close();
            }
        } catch (Throwable th2) {
            th = th2;
            printWriter2 = printWriter;
            if (printWriter2 != null) {
                printWriter2.close();
            }
            throw th;
        }
    }
}
