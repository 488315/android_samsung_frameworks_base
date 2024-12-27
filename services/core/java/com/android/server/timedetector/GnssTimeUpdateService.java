package com.android.server.timedetector;

import android.app.AlarmManager;
import android.app.time.UnixEpochTime;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationManagerInternal;
import android.location.LocationRequest;
import android.location.LocationTime;
import android.os.Binder;
import android.os.Handler;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.util.LocalLog;
import android.util.Log;
import com.android.internal.util.DumpUtils;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Executor;

public final class GnssTimeUpdateService extends Binder {
    public static final boolean D = Log.isLoggable("GnssTimeUpdateService", 3);
    public static final Duration GNSS_TIME_UPDATE_ALARM_INTERVAL = Duration.ofHours(4);
    public GnssTimeUpdateService$$ExternalSyntheticLambda1 mAlarmListener;
    public final AlarmManager mAlarmManager;
    public final Context mContext;
    public volatile UnixEpochTime mLastSuggestedGnssTime;
    public GnssTimeUpdateService$$ExternalSyntheticLambda0 mLocationListener;
    public final LocationManager mLocationManager;
    public final LocationManagerInternal mLocationManagerInternal;
    public final TimeDetectorInternal mTimeDetectorInternal;
    public final LocalLog mLocalLog = new LocalLog(10, false);
    public final Executor mExecutor = FgThread.getExecutor();
    public final Handler mHandler = FgThread.getHandler();
    public final Object mLock = new Object();

    public final class Lifecycle extends SystemService {
        public GnssTimeUpdateService mService;

        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            if (i == 600) {
                this.mService.startGnssListeningInternal();
            }
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            Context createAttributionContext = getContext().createAttributionContext("GnssTimeUpdateService");
            GnssTimeUpdateService gnssTimeUpdateService = new GnssTimeUpdateService(createAttributionContext, (AlarmManager) createAttributionContext.getSystemService(AlarmManager.class), (LocationManager) createAttributionContext.getSystemService(LocationManager.class), (LocationManagerInternal) LocalServices.getService(LocationManagerInternal.class), (TimeDetectorInternal) LocalServices.getService(TimeDetectorInternal.class));
            this.mService = gnssTimeUpdateService;
            publishBinderService("gnss_time_update_service", gnssTimeUpdateService);
        }
    }

    public GnssTimeUpdateService(Context context, AlarmManager alarmManager, LocationManager locationManager, LocationManagerInternal locationManagerInternal, TimeDetectorInternal timeDetectorInternal) {
        Objects.requireNonNull(context);
        this.mContext = context;
        Objects.requireNonNull(alarmManager);
        this.mAlarmManager = alarmManager;
        Objects.requireNonNull(locationManager);
        this.mLocationManager = locationManager;
        Objects.requireNonNull(locationManagerInternal);
        this.mLocationManagerInternal = locationManagerInternal;
        Objects.requireNonNull(timeDetectorInternal);
        this.mTimeDetectorInternal = timeDetectorInternal;
    }

    public static void logDebug(String str) {
        if (D) {
            Log.d("GnssTimeUpdateService", str);
        }
    }

    @Override // android.os.Binder
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "GnssTimeUpdateService", printWriter)) {
            printWriter.println("mLastSuggestedGnssTime: " + this.mLastSuggestedGnssTime);
            synchronized (this.mLock) {
                try {
                    printWriter.print("state: ");
                    if (this.mLocationListener != null) {
                        printWriter.println("time updates enabled");
                    } else {
                        printWriter.println("alarm enabled");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            printWriter.println("Log:");
            this.mLocalLog.dump(printWriter);
        }
    }

    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new GnssTimeUpdateServiceShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public boolean startGnssListeningInternal() {
        if (!this.mLocationManager.hasProvider("gps")) {
            Log.e("GnssTimeUpdateService", "GPS provider does not exist on this device");
            this.mLocalLog.log("GPS provider does not exist on this device");
            return false;
        }
        synchronized (this.mLock) {
            try {
                if (this.mLocationListener != null) {
                    logDebug("Already listening for GNSS updates");
                    return true;
                }
                GnssTimeUpdateService$$ExternalSyntheticLambda1 gnssTimeUpdateService$$ExternalSyntheticLambda1 = this.mAlarmListener;
                if (gnssTimeUpdateService$$ExternalSyntheticLambda1 != null) {
                    this.mAlarmManager.cancel(gnssTimeUpdateService$$ExternalSyntheticLambda1);
                    this.mAlarmListener = null;
                }
                startGnssListeningLocked();
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startGnssListeningLocked() {
        logDebug("startGnssListeningLocked()");
        this.mLocationListener = new LocationListener() { // from class: com.android.server.timedetector.GnssTimeUpdateService$$ExternalSyntheticLambda0
            @Override // android.location.LocationListener
            public final void onLocationChanged(Location location) {
                final GnssTimeUpdateService gnssTimeUpdateService = GnssTimeUpdateService.this;
                gnssTimeUpdateService.getClass();
                GnssTimeUpdateService.logDebug("handleLocationAvailable()");
                LocationTime gnssTimeMillis = gnssTimeUpdateService.mLocationManagerInternal.getGnssTimeMillis();
                if (gnssTimeMillis != null) {
                    String str = "Passive location time received: " + gnssTimeMillis;
                    GnssTimeUpdateService.logDebug(str);
                    gnssTimeUpdateService.mLocalLog.log(str);
                    GnssTimeUpdateService.logDebug("suggestGnssTime()");
                    UnixEpochTime unixEpochTime = new UnixEpochTime(gnssTimeMillis.getElapsedRealtimeNanos() / 1000000, gnssTimeMillis.getUnixEpochTimeMillis());
                    gnssTimeUpdateService.mLastSuggestedGnssTime = unixEpochTime;
                    GnssTimeSuggestion gnssTimeSuggestion = new GnssTimeSuggestion(unixEpochTime);
                    TimeDetectorInternalImpl timeDetectorInternalImpl = (TimeDetectorInternalImpl) gnssTimeUpdateService.mTimeDetectorInternal;
                    timeDetectorInternalImpl.getClass();
                    timeDetectorInternalImpl.mHandler.post(new TimeDetectorInternalImpl$$ExternalSyntheticLambda0(timeDetectorInternalImpl, gnssTimeSuggestion, 1));
                } else {
                    GnssTimeUpdateService.logDebug("getGnssTimeMillis() returned null");
                }
                synchronized (gnssTimeUpdateService.mLock) {
                    try {
                        GnssTimeUpdateService$$ExternalSyntheticLambda0 gnssTimeUpdateService$$ExternalSyntheticLambda0 = gnssTimeUpdateService.mLocationListener;
                        if (gnssTimeUpdateService$$ExternalSyntheticLambda0 == null) {
                            Log.w("GnssTimeUpdateService", "mLocationListener unexpectedly null");
                            gnssTimeUpdateService.mLocalLog.log("mLocationListener unexpectedly null");
                        } else {
                            gnssTimeUpdateService.mLocationManager.removeUpdates(gnssTimeUpdateService$$ExternalSyntheticLambda0);
                            gnssTimeUpdateService.mLocationListener = null;
                        }
                        if (gnssTimeUpdateService.mAlarmListener != null) {
                            Log.w("GnssTimeUpdateService", "mAlarmListener was unexpectedly non-null");
                            gnssTimeUpdateService.mLocalLog.log("mAlarmListener was unexpectedly non-null");
                            gnssTimeUpdateService.mAlarmManager.cancel(gnssTimeUpdateService.mAlarmListener);
                        }
                        long elapsedRealtime = SystemClock.elapsedRealtime() + GnssTimeUpdateService.GNSS_TIME_UPDATE_ALARM_INTERVAL.toMillis();
                        ?? r9 = new AlarmManager.OnAlarmListener() { // from class: com.android.server.timedetector.GnssTimeUpdateService$$ExternalSyntheticLambda1
                            @Override // android.app.AlarmManager.OnAlarmListener
                            public final void onAlarm() {
                                GnssTimeUpdateService gnssTimeUpdateService2 = GnssTimeUpdateService.this;
                                gnssTimeUpdateService2.getClass();
                                GnssTimeUpdateService.logDebug("handleAlarmFired()");
                                synchronized (gnssTimeUpdateService2.mLock) {
                                    gnssTimeUpdateService2.mAlarmListener = null;
                                    gnssTimeUpdateService2.startGnssListeningLocked();
                                }
                            }
                        };
                        gnssTimeUpdateService.mAlarmListener = r9;
                        gnssTimeUpdateService.mAlarmManager.set(2, elapsedRealtime, "GnssTimeUpdateService", r9, gnssTimeUpdateService.mHandler);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mLocationManager.requestLocationUpdates("gps", new LocationRequest.Builder(Long.MAX_VALUE).setMinUpdateIntervalMillis(0L).build(), this.mExecutor, this.mLocationListener);
    }
}
