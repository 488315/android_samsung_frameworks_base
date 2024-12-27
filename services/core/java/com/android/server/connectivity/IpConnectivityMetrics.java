package com.android.server.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.ConnectivityMetricsEvent;
import android.net.IIpConnectivityMetrics;
import android.net.INetdEventCallback;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkPolicyManager;
import android.net.NetworkStack;
import android.net.metrics.ApfProgramEvent;
import android.net.metrics.ConnectStats;
import android.net.metrics.DefaultNetworkEvent;
import android.net.metrics.DnsEvent;
import android.net.metrics.NetworkMetrics;
import android.net.metrics.WakeupEvent;
import android.net.metrics.WakeupStats;
import android.os.Binder;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;

import com.android.internal.util.RingBuffer;
import com.android.internal.util.TokenBucket;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.connectivity.NetdEventListenerService.DnsEventHandler;
import com.android.server.connectivity.metrics.nano.IpConnectivityLogClass;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

public final class IpConnectivityMetrics extends SystemService {
    public static final IpConnectivityMetrics$$ExternalSyntheticLambda0 READ_BUFFER_SIZE =
            new IpConnectivityMetrics$$ExternalSyntheticLambda0();
    public final Impl impl;
    public final ArrayMap mBuckets;
    public ArrayList mBuffer;
    public int mCapacity;
    public final ToIntFunction mCapacityGetter;
    final DefaultNetworkMetrics mDefaultNetworkMetrics;
    public int mDropped;
    public final RingBuffer mEventLog;
    public final Object mLock;
    NetdEventListenerService mNetdListener;

    public final class Impl extends IIpConnectivityMetrics.Stub {
        public Impl() {}

        public final boolean addNetdEventCallback(int i, INetdEventCallback iNetdEventCallback) {
            boolean z = true;
            int callingUid = Binder.getCallingUid();
            if (callingUid != 1000) {
                throw new SecurityException(
                        String.format(
                                "Uid %d has no permission to listen for netd events.",
                                Integer.valueOf(callingUid)));
            }
            NetdEventListenerService netdEventListenerService =
                    IpConnectivityMetrics.this.mNetdListener;
            if (netdEventListenerService == null) {
                return false;
            }
            synchronized (netdEventListenerService) {
                int i2 = 0;
                while (true) {
                    if (i2 >= 6) {
                        int[] iArr = NetdEventListenerService.ALLOWED_CALLBACK_TYPES;
                        Log.e("NetdEventListenerService", "Invalid caller type: " + i);
                        z = false;
                        break;
                    }
                    if (i == NetdEventListenerService.ALLOWED_CALLBACK_TYPES[i2]) {
                        netdEventListenerService.mNetdEventCallbackList[i] = iNetdEventCallback;
                        break;
                    }
                    i2++;
                }
            }
            return z;
        }

        public final void dump(
                FileDescriptor fileDescriptor, final PrintWriter printWriter, String[] strArr) {
            char c;
            int i;
            IpConnectivityMetrics.this
                    .getContext()
                    .enforceCallingOrSelfPermission(
                            "android.permission.DUMP", "IpConnectivityMetrics");
            IpConnectivityMetrics.this.mNetdListener.writeMobileDataDnsFile();
            String str = strArr.length > 0 ? strArr[0] : "";
            switch (str.hashCode()) {
                case -1616754616:
                    if (str.equals("--proto")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3322014:
                    if (str.equals("list")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 97532676:
                    if (str.equals("flush")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 106940904:
                    if (str.equals("proto")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                IpConnectivityMetrics.m367$$Nest$mcmdFlush(IpConnectivityMetrics.this, printWriter);
                return;
            }
            if (c == 1) {
                ((ArrayList) IpConnectivityMetrics.this.listEventsAsProtos())
                        .forEach(
                                new Consumer() { // from class:
                                                 // com.android.server.connectivity.IpConnectivityMetrics$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        printWriter.print(
                                                ((IpConnectivityLogClass.IpConnectivityEvent) obj)
                                                        .toString());
                                    }
                                });
                return;
            }
            if (c == 2) {
                IpConnectivityMetrics ipConnectivityMetrics = IpConnectivityMetrics.this;
                FileOutputStream fileOutputStream = new FileOutputStream(fileDescriptor);
                synchronized (ipConnectivityMetrics.mLock) {
                    i = ipConnectivityMetrics.mDropped;
                }
                try {
                    fileOutputStream.write(
                            IpConnectivityEventBuilder.serialize(
                                    i, ipConnectivityMetrics.listEventsAsProtos()));
                    fileOutputStream.flush();
                    return;
                } catch (IOException e) {
                    Log.e("IpConnectivityMetrics", "could not serialize events", e);
                    return;
                }
            }
            IpConnectivityMetrics ipConnectivityMetrics2 = IpConnectivityMetrics.this;
            ipConnectivityMetrics2.getClass();
            printWriter.println("metrics events:");
            Iterator it = ipConnectivityMetrics2.getEvents().iterator();
            while (it.hasNext()) {
                printWriter.println(((ConnectivityMetricsEvent) it.next()).toString());
            }
            printWriter.println("");
            NetdEventListenerService netdEventListenerService =
                    ipConnectivityMetrics2.mNetdListener;
            if (netdEventListenerService != null) {
                synchronized (netdEventListenerService) {
                    try {
                        NetdEventListenerService.NetworkMetricsSnapshot collect =
                                NetdEventListenerService.NetworkMetricsSnapshot.collect(
                                        System.currentTimeMillis(),
                                        netdEventListenerService.mNetworkMetrics);
                        if (!((ArrayList) collect.stats).isEmpty()) {
                            netdEventListenerService.mNetworkMetricsSnapshots.append(collect);
                        }
                        printWriter.println("dns/connect events:");
                        for (int i2 = 0;
                                i2 < netdEventListenerService.mNetworkMetrics.size();
                                i2++) {
                            printWriter.println(
                                    ((NetworkMetrics)
                                                    netdEventListenerService.mNetworkMetrics
                                                            .valueAt(i2))
                                            .connectMetrics);
                        }
                        for (int i3 = 0;
                                i3 < netdEventListenerService.mNetworkMetrics.size();
                                i3++) {
                            printWriter.println(
                                    ((NetworkMetrics)
                                                    netdEventListenerService.mNetworkMetrics
                                                            .valueAt(i3))
                                            .dnsMetrics);
                        }
                        printWriter.println("");
                        printWriter.println("network statistics:");
                        netdEventListenerService.collectPendingMetricsSnapshot(
                                System.currentTimeMillis(), false);
                        for (NetdEventListenerService.NetworkMetricsSnapshot
                                networkMetricsSnapshot :
                                        (NetdEventListenerService.NetworkMetricsSnapshot[])
                                                netdEventListenerService.mNetworkMetricsSnapshots
                                                        .toArray()) {
                            printWriter.println(networkMetricsSnapshot);
                        }
                        printWriter.println("");
                        printWriter.println("packet wakeup events:");
                        for (int i4 = 0; i4 < netdEventListenerService.mWakeupStats.size(); i4++) {
                            printWriter.println(netdEventListenerService.mWakeupStats.valueAt(i4));
                        }
                        for (WakeupEvent wakeupEvent :
                                (WakeupEvent[]) netdEventListenerService.mWakeupEvents.toArray()) {
                            printWriter.println(wakeupEvent);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            printWriter.println("");
            DefaultNetworkMetrics defaultNetworkMetrics =
                    ipConnectivityMetrics2.mDefaultNetworkMetrics;
            synchronized (defaultNetworkMetrics) {
                try {
                    printWriter.println("default network events:");
                    long currentTimeMillis = System.currentTimeMillis();
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    for (DefaultNetworkEvent defaultNetworkEvent :
                            (DefaultNetworkEvent[]) defaultNetworkMetrics.mEventsLog.toArray()) {
                        long j = currentTimeMillis - defaultNetworkEvent.durationMs;
                        printWriter.println(
                                String.format(
                                        "%tT.%tL: %s",
                                        Long.valueOf(j), Long.valueOf(j), defaultNetworkEvent));
                    }
                    defaultNetworkMetrics.mCurrentDefaultNetwork.updateDuration(elapsedRealtime);
                    if (defaultNetworkMetrics.mIsCurrentlyValid) {
                        DefaultNetworkEvent defaultNetworkEvent2 =
                                defaultNetworkMetrics.mCurrentDefaultNetwork;
                        defaultNetworkEvent2.validatedMs =
                                (elapsedRealtime - defaultNetworkMetrics.mLastValidationTimeMs)
                                        + defaultNetworkEvent2.validatedMs;
                        defaultNetworkMetrics.mLastValidationTimeMs = elapsedRealtime;
                    }
                    DefaultNetworkEvent defaultNetworkEvent3 =
                            defaultNetworkMetrics.mCurrentDefaultNetwork;
                    long j2 = currentTimeMillis - defaultNetworkEvent3.durationMs;
                    printWriter.println(
                            String.format(
                                    "%tT.%tL: %s",
                                    Long.valueOf(j2), Long.valueOf(j2), defaultNetworkEvent3));
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }

        public final void logDefaultNetworkEvent(
                Network network,
                int i,
                boolean z,
                LinkProperties linkProperties,
                NetworkCapabilities networkCapabilities,
                Network network2,
                int i2,
                LinkProperties linkProperties2,
                NetworkCapabilities networkCapabilities2) {
            NetworkStack.checkNetworkStackPermission(IpConnectivityMetrics.this.getContext());
            long elapsedRealtime = SystemClock.elapsedRealtime();
            DefaultNetworkMetrics defaultNetworkMetrics =
                    IpConnectivityMetrics.this.mDefaultNetworkMetrics;
            synchronized (defaultNetworkMetrics) {
                defaultNetworkMetrics.logCurrentDefaultNetwork(
                        elapsedRealtime, network2, i2, linkProperties2, networkCapabilities2);
                DefaultNetworkEvent defaultNetworkEvent = new DefaultNetworkEvent(elapsedRealtime);
                defaultNetworkEvent.durationMs = elapsedRealtime;
                if (network != null) {
                    DefaultNetworkMetrics.fillLinkInfo(
                            defaultNetworkEvent, network, linkProperties, networkCapabilities);
                    defaultNetworkEvent.initialScore = i;
                    if (z) {
                        defaultNetworkMetrics.mIsCurrentlyValid = true;
                        defaultNetworkMetrics.mLastValidationTimeMs = elapsedRealtime;
                    }
                } else {
                    defaultNetworkMetrics.mIsCurrentlyValid = false;
                }
                defaultNetworkMetrics.mCurrentDefaultNetwork = defaultNetworkEvent;
            }
        }

        public final void logDefaultNetworkValidity(boolean z) {
            NetworkStack.checkNetworkStackPermission(IpConnectivityMetrics.this.getContext());
            DefaultNetworkMetrics defaultNetworkMetrics =
                    IpConnectivityMetrics.this.mDefaultNetworkMetrics;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            synchronized (defaultNetworkMetrics) {
                if (!z) {
                    try {
                        if (defaultNetworkMetrics.mIsCurrentlyValid) {
                            defaultNetworkMetrics.mIsCurrentlyValid = false;
                            DefaultNetworkEvent defaultNetworkEvent =
                                    defaultNetworkMetrics.mCurrentDefaultNetwork;
                            defaultNetworkEvent.validatedMs =
                                    (elapsedRealtime - defaultNetworkMetrics.mLastValidationTimeMs)
                                            + defaultNetworkEvent.validatedMs;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (z && !defaultNetworkMetrics.mIsCurrentlyValid) {
                    defaultNetworkMetrics.mIsCurrentlyValid = true;
                    defaultNetworkMetrics.mLastValidationTimeMs = elapsedRealtime;
                }
            }
        }

        public final int logEvent(ConnectivityMetricsEvent connectivityMetricsEvent) {
            NetworkStack.checkNetworkStackPermission(IpConnectivityMetrics.this.getContext());
            IpConnectivityMetrics ipConnectivityMetrics = IpConnectivityMetrics.this;
            synchronized (ipConnectivityMetrics.mLock) {
                try {
                    ipConnectivityMetrics.mEventLog.append(connectivityMetricsEvent);
                    int size =
                            ipConnectivityMetrics.mCapacity - ipConnectivityMetrics.mBuffer.size();
                    if (connectivityMetricsEvent == null) {
                        return size;
                    }
                    TokenBucket tokenBucket =
                            (TokenBucket)
                                    ipConnectivityMetrics.mBuckets.get(
                                            connectivityMetricsEvent.data.getClass());
                    if (tokenBucket != null && !tokenBucket.get()) {
                        return -1;
                    }
                    if (size == 0) {
                        ipConnectivityMetrics.mDropped++;
                        return 0;
                    }
                    ipConnectivityMetrics.mBuffer.add(connectivityMetricsEvent);
                    return size - 1;
                } finally {
                }
            }
        }

        public final boolean removeNetdEventCallback(int i) {
            boolean z = true;
            int callingUid = Binder.getCallingUid();
            if (callingUid != 1000) {
                throw new SecurityException(
                        String.format(
                                "Uid %d has no permission to listen for netd events.",
                                Integer.valueOf(callingUid)));
            }
            NetdEventListenerService netdEventListenerService =
                    IpConnectivityMetrics.this.mNetdListener;
            if (netdEventListenerService == null) {
                return true;
            }
            synchronized (netdEventListenerService) {
                int i2 = 0;
                while (true) {
                    if (i2 >= 6) {
                        int[] iArr = NetdEventListenerService.ALLOWED_CALLBACK_TYPES;
                        Log.e("NetdEventListenerService", "Invalid caller type: " + i);
                        z = false;
                        break;
                    }
                    if (i == NetdEventListenerService.ALLOWED_CALLBACK_TYPES[i2]) {
                        netdEventListenerService.mNetdEventCallbackList[i] = null;
                        break;
                    }
                    i2++;
                }
            }
            return z;
        }
    }

    public final class LoggerImpl {}

    /* renamed from: -$$Nest$mcmdFlush, reason: not valid java name */
    public static void m367$$Nest$mcmdFlush(
            IpConnectivityMetrics ipConnectivityMetrics, PrintWriter printWriter) {
        ArrayList arrayList;
        int i;
        String str;
        synchronized (ipConnectivityMetrics.mLock) {
            arrayList = ipConnectivityMetrics.mBuffer;
            i = ipConnectivityMetrics.mDropped;
            ipConnectivityMetrics.initBuffer();
        }
        List proto = IpConnectivityEventBuilder.toProto(arrayList);
        DefaultNetworkMetrics defaultNetworkMetrics = ipConnectivityMetrics.mDefaultNetworkMetrics;
        synchronized (defaultNetworkMetrics) {
            try {
                Iterator it = ((ArrayList) defaultNetworkMetrics.mEvents).iterator();
                while (it.hasNext()) {
                    ((ArrayList) proto)
                            .add(
                                    IpConnectivityEventBuilder.toProto(
                                            (DefaultNetworkEvent) it.next()));
                }
                ((ArrayList) defaultNetworkMetrics.mEvents).clear();
            } catch (Throwable th) {
                throw th;
            }
        }
        NetdEventListenerService netdEventListenerService = ipConnectivityMetrics.mNetdListener;
        if (netdEventListenerService != null) {
            synchronized (netdEventListenerService) {
                for (int i2 = 0; i2 < netdEventListenerService.mNetworkMetrics.size(); i2++) {
                    try {
                        ConnectStats connectStats =
                                ((NetworkMetrics)
                                                netdEventListenerService.mNetworkMetrics.valueAt(
                                                        i2))
                                        .connectMetrics;
                        if (connectStats.eventCount != 0) {
                            ((ArrayList) proto)
                                    .add(IpConnectivityEventBuilder.toProto(connectStats));
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }
                for (int i3 = 0; i3 < netdEventListenerService.mNetworkMetrics.size(); i3++) {
                    DnsEvent dnsEvent =
                            ((NetworkMetrics) netdEventListenerService.mNetworkMetrics.valueAt(i3))
                                    .dnsMetrics;
                    if (dnsEvent.eventCount != 0) {
                        ((ArrayList) proto).add(IpConnectivityEventBuilder.toProto(dnsEvent));
                    }
                }
                for (int i4 = 0; i4 < netdEventListenerService.mWakeupStats.size(); i4++) {
                    ((ArrayList) proto)
                            .add(
                                    IpConnectivityEventBuilder.toProto(
                                            (WakeupStats)
                                                    netdEventListenerService.mWakeupStats.valueAt(
                                                            i4)));
                }
                netdEventListenerService.mNetworkMetrics.clear();
                netdEventListenerService.mWakeupStats.clear();
            }
        }
        try {
            str = Base64.encodeToString(IpConnectivityEventBuilder.serialize(i, proto), 0);
        } catch (IOException e) {
            Log.e("IpConnectivityMetrics", "could not serialize events", e);
            str = "";
        }
        printWriter.print(str);
    }

    public IpConnectivityMetrics(Context context) {
        super(context);
        IpConnectivityMetrics$$ExternalSyntheticLambda0
                ipConnectivityMetrics$$ExternalSyntheticLambda0 = READ_BUFFER_SIZE;
        this.mLock = new Object();
        this.impl = new Impl();
        this.mEventLog = new RingBuffer(ConnectivityMetricsEvent.class, 500);
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put(ApfProgramEvent.class, new TokenBucket(60000, 50));
        this.mBuckets = arrayMap;
        this.mDefaultNetworkMetrics = new DefaultNetworkMetrics();
        this.mCapacityGetter = ipConnectivityMetrics$$ExternalSyntheticLambda0;
        initBuffer();
    }

    public int bufferCapacity() {
        return this.mCapacityGetter.applyAsInt(getContext());
    }

    public final List getEvents() {
        List asList;
        synchronized (this.mLock) {
            asList = Arrays.asList((ConnectivityMetricsEvent[]) this.mEventLog.toArray());
        }
        return asList;
    }

    public final void initBuffer() {
        synchronized (this.mLock) {
            this.mDropped = 0;
            this.mCapacity = bufferCapacity();
            this.mBuffer = new ArrayList(this.mCapacity);
        }
    }

    public final List listEventsAsProtos() {
        ArrayList arrayList;
        ArrayList arrayList2;
        List proto = IpConnectivityEventBuilder.toProto(getEvents());
        NetdEventListenerService netdEventListenerService = this.mNetdListener;
        if (netdEventListenerService != null) {
            synchronized (netdEventListenerService) {
                try {
                    arrayList2 = new ArrayList();
                    for (int i = 0; i < netdEventListenerService.mNetworkMetrics.size(); i++) {
                        arrayList2.add(
                                IpConnectivityEventBuilder.toProto(
                                        ((NetworkMetrics)
                                                        netdEventListenerService.mNetworkMetrics
                                                                .valueAt(i))
                                                .connectMetrics));
                    }
                    for (int i2 = 0; i2 < netdEventListenerService.mNetworkMetrics.size(); i2++) {
                        arrayList2.add(
                                IpConnectivityEventBuilder.toProto(
                                        ((NetworkMetrics)
                                                        netdEventListenerService.mNetworkMetrics
                                                                .valueAt(i2))
                                                .dnsMetrics));
                    }
                    for (int i3 = 0; i3 < netdEventListenerService.mWakeupStats.size(); i3++) {
                        arrayList2.add(
                                IpConnectivityEventBuilder.toProto(
                                        (WakeupStats)
                                                netdEventListenerService.mWakeupStats.valueAt(i3)));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            ((ArrayList) proto).addAll(arrayList2);
        }
        DefaultNetworkMetrics defaultNetworkMetrics = this.mDefaultNetworkMetrics;
        synchronized (defaultNetworkMetrics) {
            arrayList = new ArrayList();
            for (DefaultNetworkEvent defaultNetworkEvent :
                    (DefaultNetworkEvent[]) defaultNetworkMetrics.mEventsLog.toArray()) {
                arrayList.add(IpConnectivityEventBuilder.toProto(defaultNetworkEvent));
            }
        }
        ((ArrayList) proto).addAll(arrayList);
        return proto;
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 500) {
            Context context = getContext();
            NetdEventListenerService netdEventListenerService =
                    new NetdEventListenerService(
                            (ConnectivityManager)
                                    context.getSystemService(ConnectivityManager.class));
            netdEventListenerService.mContext = context;
            netdEventListenerService.filter.addAction(
                    "com.samsung.android.mobiledoctor.GETMOBILEDATAFILES");
            netdEventListenerService.filter.addAction(
                    "com.samsung.android.mobiledoctor.DELETEMOBILEDATAFILES");
            netdEventListenerService.filter.addAction(
                    "com.samsung.android.action.ACTION_REQUEST_INTERNET_LOG");
            context.registerReceiver(
                    netdEventListenerService.receiver, netdEventListenerService.filter, 2);
            HandlerThread handlerThread = new HandlerThread("NetdEventListenerService");
            handlerThread.start();
            Looper looper = handlerThread.getLooper();
            if (looper != null) {
                netdEventListenerService.mDnsHandler =
                        netdEventListenerService.new DnsEventHandler(looper);
            } else {
                Log.e("NetdEventListenerService", "handlerThread.getLooper() returned null");
            }
            netdEventListenerService.mPolicyManager =
                    (NetworkPolicyManager) context.getSystemService(NetworkPolicyManager.class);
            this.mNetdListener = netdEventListenerService;
            publishBinderService("connmetrics", this.impl);
            publishBinderService("netd_listener", this.mNetdListener);
            LocalServices.addService(LoggerImpl.class, new LoggerImpl());
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {}
}
