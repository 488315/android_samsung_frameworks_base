package com.android.systemui.util.wakelock;

import android.os.PowerManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.ToIntFunction;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    @Override // com.android.systemui.util.wakelock.WakeLock
    public void acquire(String str) {
        int incrementAndGet = this.activeClients.computeIfAbsent(str, new Function() { // from class: com.android.systemui.util.wakelock.ClientTrackingWakeLock$acquire$count$1
            @Override // java.util.function.Function
            public final AtomicInteger apply(String str2) {
                return new AtomicInteger(0);
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
        return this.activeClients.reduceValuesToInt(Long.MAX_VALUE, new ToIntFunction() { // from class: com.android.systemui.util.wakelock.ClientTrackingWakeLock$activeClients$1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(AtomicInteger atomicInteger) {
                return atomicInteger.get();
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
        Log.wtf(WakeLock.TAG, "Releasing WakeLock with invalid reason: ".concat(str));
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
