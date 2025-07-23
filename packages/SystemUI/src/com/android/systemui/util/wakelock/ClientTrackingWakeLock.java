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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ClientTrackingWakeLock implements WakeLock {
    public static final int $stable = 8;
    private final ConcurrentHashMap<String, AtomicInteger> activeClients = new ConcurrentHashMap<>();
    private final WakeLockLogger logger;
    private final long maxTimeout;
    private final PowerManager.WakeLock pmWakeLock;

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
        int incrementAndGet = concurrentHashMap.computeIfAbsent(str, new Function() { // from class: com.android.systemui.util.wakelock.ClientTrackingWakeLock$sam$java_util_function_Function$0
            @Override // java.util.function.Function
            public final /* synthetic */ Object apply(Object obj) {
                return Function1.this.mo779invoke(obj);
            }
        }).incrementAndGet();
        WakeLockLogger wakeLockLogger = this.logger;
        if (wakeLockLogger != null) {
            wakeLockLogger.logAcquire(this.pmWakeLock, str, incrementAndGet);
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
        final ClientTrackingWakeLock$activeClients$1 clientTrackingWakeLock$activeClients$1 = ClientTrackingWakeLock$activeClients$1.INSTANCE;
        return concurrentHashMap.reduceValuesToInt(Long.MAX_VALUE, new ToIntFunction() { // from class: com.android.systemui.util.wakelock.ClientTrackingWakeLock$sam$java_util_function_ToIntFunction$0
            @Override // java.util.function.ToIntFunction
            public final /* synthetic */ int applyAsInt(Object obj) {
                return ((Number) Function1.this.mo779invoke(obj)).intValue();
            }
        }, 0, new IntBinaryOperator() { // from class: com.android.systemui.util.wakelock.ClientTrackingWakeLock$activeClients$2
            @Override // java.util.function.IntBinaryOperator
            public final int applyAsInt(int i, int i2) {
                return Integer.sum(i, i2);
            }
        });
    }

    @Override // com.android.systemui.util.wakelock.WakeLock
    public void release(String str) {
        AtomicInteger atomicInteger = this.activeClients.get(str);
        int decrementAndGet = atomicInteger != null ? atomicInteger.decrementAndGet() : -1;
        if (decrementAndGet >= 0) {
            WakeLockLogger wakeLockLogger = this.logger;
            if (wakeLockLogger != null) {
                wakeLockLogger.logRelease(this.pmWakeLock, str, decrementAndGet);
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
