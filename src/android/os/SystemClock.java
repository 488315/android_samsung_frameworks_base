package android.os;

import android.app.IAlarmManager;
import android.app.time.UnixEpochTime;
import android.app.timedetector.ITimeDetectorService;
import android.location.ILocationManager;
import android.location.LocationTime;
import android.util.Slog;
import com.android.internal.os.ApplicationSharedMemory;
import com.android.internal.os.Flags;
import dalvik.annotation.optimization.CriticalNative;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.ZoneOffset;

/* loaded from: classes3.dex */
public final class SystemClock {
    private static final String TAG = "SystemClock";
    private static final long sAnchorNanoTime$ravenwood = System.nanoTime();
    private static volatile IAlarmManager sIAlarmManager;
    private static volatile ITimeDetectorService sITimeDetectorService;

    @CriticalNative
    public static native long currentThreadTimeMicro();

    @CriticalNative
    public static native long currentThreadTimeMillis();

    @CriticalNative
    public static native long currentTimeMicro();

    @CriticalNative
    public static native long elapsedRealtime();

    @CriticalNative
    public static native long elapsedRealtimeNanos();

    @CriticalNative
    public static native long uptimeMillis();

    @CriticalNative
    public static native long uptimeNanos();

    private SystemClock() {
    }

    public static void sleep(long j) {
        long uptimeMillis = uptimeMillis();
        boolean z = false;
        long j2 = j;
        do {
            try {
                Thread.sleep(j2);
            } catch (InterruptedException unused) {
                z = true;
            }
            j2 = (uptimeMillis + j) - uptimeMillis();
        } while (j2 > 0);
        if (z) {
            Thread.currentThread().interrupt();
        }
    }

    public static boolean setCurrentTimeMillis(long j) {
        IAlarmManager iAlarmManager = getIAlarmManager();
        if (iAlarmManager == null) {
            Slog.e(TAG, "Unable to set RTC: mgr == null");
            return false;
        }
        try {
            return iAlarmManager.setTime(j);
        } catch (RemoteException e) {
            Slog.e(TAG, "Unable to set RTC", e);
            return false;
        } catch (SecurityException e2) {
            Slog.e(TAG, "Unable to set RTC", e2);
            return false;
        }
    }

    private static IAlarmManager getIAlarmManager() {
        if (sIAlarmManager == null) {
            sIAlarmManager = IAlarmManager.Stub.asInterface(ServiceManager.getService("alarm"));
        }
        return sIAlarmManager;
    }

    private static ITimeDetectorService getITimeDetectorService() {
        if (sITimeDetectorService == null) {
            sITimeDetectorService = ITimeDetectorService.Stub.asInterface(ServiceManager.getService("time_detector"));
        }
        return sITimeDetectorService;
    }

    public static long uptimeMillis$ravenwood() {
        return uptimeNanos() / 1000000;
    }

    public static long uptimeNanos$ravenwood() {
        return System.nanoTime() - sAnchorNanoTime$ravenwood;
    }

    public static Clock uptimeClock() {
        return new SimpleClock(ZoneOffset.UTC) { // from class: android.os.SystemClock.1
            @Override // android.os.SimpleClock, java.time.Clock, java.time.InstantSource
            public long millis() {
                return SystemClock.uptimeMillis();
            }
        };
    }

    public static long elapsedRealtime$ravenwood() {
        return elapsedRealtimeNanos() / 1000000;
    }

    public static Clock elapsedRealtimeClock() {
        return new SimpleClock(ZoneOffset.UTC) { // from class: android.os.SystemClock.2
            @Override // android.os.SimpleClock, java.time.Clock, java.time.InstantSource
            public long millis() {
                return SystemClock.elapsedRealtime();
            }
        };
    }

    public static long elapsedRealtimeNanos$ravenwood() {
        return uptimeNanos() + 3600000000000L;
    }

    public static long currentTimeMicro$ravenwood() {
        return System.nanoTime() / 1000;
    }

    public static long currentNetworkTimeMillis() {
        if (Flags.applicationSharedMemoryEnabled() && com.android.internal.hidden_from_bootclasspath.android.os.Flags.networkTimeUsesSharedMemory()) {
            return ApplicationSharedMemory.getInstance().getLatestNetworkTimeUnixEpochMillisAtZeroElapsedRealtimeMillis() + elapsedRealtime();
        }
        ITimeDetectorService iTimeDetectorService = getITimeDetectorService();
        if (iTimeDetectorService == null) {
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            UnixEpochTime latestNetworkTime = iTimeDetectorService.latestNetworkTime();
            if (latestNetworkTime == null) {
                throw new DateTimeException("Network based time is not available.");
            }
            return latestNetworkTime.getUnixEpochTimeMillis() + (elapsedRealtime() - latestNetworkTime.getElapsedRealtimeMillis());
        } catch (ParcelableException e) {
            e.maybeRethrow(DateTimeException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public static Clock currentNetworkTimeClock() {
        return new SimpleClock(ZoneOffset.UTC) { // from class: android.os.SystemClock.3
            @Override // android.os.SimpleClock, java.time.Clock, java.time.InstantSource
            public long millis() {
                return SystemClock.currentNetworkTimeMillis();
            }
        };
    }

    public static Clock currentGnssTimeClock() {
        return new SimpleClock(ZoneOffset.UTC) { // from class: android.os.SystemClock.4
            private final ILocationManager mMgr = ILocationManager.Stub.asInterface(ServiceManager.getService("location"));

            @Override // android.os.SimpleClock, java.time.Clock, java.time.InstantSource
            public long millis() {
                try {
                    LocationTime gnssTimeMillis = this.mMgr.getGnssTimeMillis();
                    if (gnssTimeMillis == null) {
                        throw new DateTimeException("Gnss based time is not available.");
                    }
                    return gnssTimeMillis.getUnixEpochTimeMillis() + ((SystemClock.elapsedRealtimeNanos() - gnssTimeMillis.getElapsedRealtimeNanos()) / 1000000);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        };
    }
}
