package com.android.systemui.util.wakelock;

import android.os.PowerManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.ToIntFunction;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
public final class ClientTrackingWakeLock implements WakeLock {
    public static final int $stable = 8;
    private final ConcurrentHashMap<String, AtomicInteger> activeClients = new ConcurrentHashMap<>();
    private final WakeLockLogger logger;
    private final long maxTimeout;
    private final PowerManager.WakeLock pmWakeLock;

    /* renamed from: com.android.systemui.util.wakelock.ClientTrackingWakeLock$activeClients$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(1, AtomicInteger.class, "get", "get()I", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke, reason: merged with bridge method [inline-methods] */
        public final Integer mo781invoke(AtomicInteger atomicInteger) {
            return Integer.valueOf(atomicInteger.get());
        }
    }

    public ClientTrackingWakeLock(PowerManager.WakeLock wakeLock, WakeLockLogger wakeLockLogger, long j) {
        this.pmWakeLock = wakeLock;
        this.logger = wakeLockLogger;
        this.maxTimeout = j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final AtomicInteger acquire$lambda$0(String str) {
        return new AtomicInteger(0);
    }

    @Override // com.android.systemui.util.wakelock.WakeLock
    public void acquire(String str) {
        ConcurrentHashMap<String, AtomicInteger> concurrentHashMap = this.activeClients;
        final ClientTrackingWakeLock$$ExternalSyntheticLambda0 clientTrackingWakeLock$$ExternalSyntheticLambda0 = new ClientTrackingWakeLock$$ExternalSyntheticLambda0();
        int iIncrementAndGet = concurrentHashMap.computeIfAbsent(str, new Function() { // from class: com.android.systemui.util.wakelock.ClientTrackingWakeLock$sam$java_util_function_Function$0
            @Override // java.util.function.Function
            public final /* synthetic */ Object apply(Object obj) {
                return clientTrackingWakeLock$$ExternalSyntheticLambda0.mo781invoke(obj);
            }
        }).incrementAndGet();
        WakeLockLogger wakeLockLogger = this.logger;
        if (wakeLockLogger != null) {
            wakeLockLogger.logAcquire(this.pmWakeLock, str, iIncrementAndGet);
        }
        long j = this.maxTimeout;
        if (j == -1) {
            this.pmWakeLock.acquire();
        } else {
            this.pmWakeLock.acquire(j);
        }
    }

    public final int activeClients() {
        ConcurrentHashMap<String, AtomicInteger> concurrentHashMap = this.activeClients;
        final AnonymousClass1 anonymousClass1 = AnonymousClass1.INSTANCE;
        return concurrentHashMap.reduceValuesToInt(Long.MAX_VALUE, new ToIntFunction() { // from class: com.android.systemui.util.wakelock.ClientTrackingWakeLock$sam$java_util_function_ToIntFunction$0
            @Override // java.util.function.ToIntFunction
            public final /* synthetic */ int applyAsInt(Object obj) {
                return ((Number) anonymousClass1.mo781invoke(obj)).intValue();
            }
        }, 0, new IntBinaryOperator() { // from class: com.android.systemui.util.wakelock.ClientTrackingWakeLock.activeClients.2
            @Override // java.util.function.IntBinaryOperator
            public final int applyAsInt(int i, int i2) {
                return Integer.sum(i, i2);
            }
        });
    }

    @Override // com.android.systemui.util.wakelock.WakeLock
    public void release(String str) {
        AtomicInteger atomicInteger = this.activeClients.get(str);
        int iDecrementAndGet = atomicInteger != null ? atomicInteger.decrementAndGet() : -1;
        if (iDecrementAndGet >= 0) {
            WakeLockLogger wakeLockLogger = this.logger;
            if (wakeLockLogger != null) {
                wakeLockLogger.logRelease(this.pmWakeLock, str, iDecrementAndGet);
            }
            this.pmWakeLock.release();
            return;
        }
        Log.wtf(WakeLock.TAG, "Releasing WakeLock with invalid reason: " + str);
        AtomicInteger atomicInteger2 = this.activeClients.get(str);
        if (atomicInteger2 != null) {
            atomicInteger2.incrementAndGet();
        }
    }

    public String toString() {
        return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(activeClients(), "active clients=");
    }

    @Override // com.android.systemui.util.wakelock.WakeLock
    public Runnable wrap(Runnable runnable) {
        return WakeLock.wrapImpl(this, runnable);
    }
}
