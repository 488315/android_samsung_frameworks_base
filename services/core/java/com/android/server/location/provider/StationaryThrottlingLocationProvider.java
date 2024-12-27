package com.android.server.location.provider;

import android.location.Location;
import android.location.LocationResult;
import android.location.provider.ProviderRequest;
import android.os.Binder;
import android.os.Bundle;
import android.os.SystemClock;

import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.Preconditions;
import com.android.server.DeviceIdleInternal;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.location.LocationManagerService;
import com.android.server.location.LocationServiceThread;
import com.android.server.location.eventlog.LocationEventLog;
import com.android.server.location.injector.DeviceIdleHelper$DeviceIdleListener;
import com.android.server.location.injector.SystemDeviceIdleHelper;
import com.android.server.location.injector.SystemDeviceStationaryHelper;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;

public final class StationaryThrottlingLocationProvider extends AbstractLocationProvider
        implements DeviceIdleHelper$DeviceIdleListener,
                DeviceIdleInternal.StationaryListener,
                AbstractLocationProvider.Listener {
    public final AbstractLocationProvider mDelegate;
    public DeliverLastLocationRunnable mDeliverLastLocationCallback;
    public boolean mDeviceIdle;
    public final SystemDeviceIdleHelper mDeviceIdleHelper;
    public boolean mDeviceStationary;
    public final SystemDeviceStationaryHelper mDeviceStationaryHelper;
    public long mDeviceStationaryRealtimeMs;
    public ProviderRequest mIncomingRequest;
    public final Object mInitializationLock;
    public final boolean mInitialized;
    public Location mLastLocation;
    public final Object mLock;
    public final String mName;
    public ProviderRequest mOutgoingRequest;
    public long mThrottlingIntervalMs;

    public final class DeliverLastLocationRunnable implements Runnable {
        public DeliverLastLocationRunnable() {}

        @Override // java.lang.Runnable
        public final void run() {
            synchronized (StationaryThrottlingLocationProvider.this.mLock) {
                try {
                    StationaryThrottlingLocationProvider stationaryThrottlingLocationProvider =
                            StationaryThrottlingLocationProvider.this;
                    if (stationaryThrottlingLocationProvider.mDeliverLastLocationCallback != this) {
                        return;
                    }
                    if (stationaryThrottlingLocationProvider.mLastLocation == null) {
                        return;
                    }
                    Location location =
                            new Location(StationaryThrottlingLocationProvider.this.mLastLocation);
                    location.setTime(System.currentTimeMillis());
                    location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                    if (location.hasSpeed()) {
                        location.removeSpeed();
                        if (location.hasSpeedAccuracy()) {
                            location.removeSpeedAccuracy();
                        }
                    }
                    if (location.hasBearing()) {
                        location.removeBearing();
                        if (location.hasBearingAccuracy()) {
                            location.removeBearingAccuracy();
                        }
                    }
                    Bundle extras = location.getExtras();
                    if (extras == null) {
                        extras = new Bundle();
                    }
                    extras.putBoolean("isThrottling", true);
                    location.setExtras(extras);
                    StationaryThrottlingLocationProvider.this.mLastLocation = location;
                    LocationServiceThread.getHandler()
                            .postDelayed(
                                    this,
                                    StationaryThrottlingLocationProvider.this
                                            .mThrottlingIntervalMs);
                    StationaryThrottlingLocationProvider.this.reportLocation(
                            LocationResult.wrap(new Location[] {location}));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public StationaryThrottlingLocationProvider(
            String str,
            LocationManagerService.SystemInjector systemInjector,
            AbstractLocationProvider abstractLocationProvider) {
        super(ConcurrentUtils.DIRECT_EXECUTOR, null, null, Collections.emptySet());
        Object obj = new Object();
        this.mInitializationLock = obj;
        this.mInitialized = false;
        this.mDelegate = abstractLocationProvider;
        this.mLock = new Object();
        this.mDeviceIdle = false;
        this.mDeviceStationary = false;
        this.mDeviceStationaryRealtimeMs = Long.MIN_VALUE;
        ProviderRequest providerRequest = ProviderRequest.EMPTY_REQUEST;
        this.mIncomingRequest = providerRequest;
        this.mOutgoingRequest = providerRequest;
        this.mThrottlingIntervalMs = Long.MAX_VALUE;
        this.mDeliverLastLocationCallback = null;
        this.mName = str;
        this.mDeviceIdleHelper = systemInjector.mDeviceIdleHelper;
        this.mDeviceStationaryHelper = systemInjector.mDeviceStationaryHelper;
        synchronized (obj) {
            Preconditions.checkState(!this.mInitialized);
            setState(new DelegateLocationProvider$$ExternalSyntheticLambda0(0, this));
            this.mInitialized = true;
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mThrottlingIntervalMs != Long.MAX_VALUE) {
            printWriter.println("stationary throttled=" + this.mLastLocation);
        } else {
            printWriter.print("stationary throttled=false");
            if (!this.mDeviceIdle) {
                printWriter.print(" (not idle)");
            }
            if (!this.mDeviceStationary) {
                printWriter.print(" (not stationary)");
            }
            printWriter.println();
        }
        this.mDelegate.dump(fileDescriptor, printWriter, strArr);
    }

    @Override // com.android.server.location.injector.DeviceIdleHelper$DeviceIdleListener
    public final void onDeviceIdleChanged(boolean z) {
        synchronized (this.mLock) {
            try {
                if (z == this.mDeviceIdle) {
                    return;
                }
                this.mDeviceIdle = z;
                if (z) {
                    this.mDeviceStationaryHelper.addListener(this);
                } else {
                    SystemDeviceStationaryHelper systemDeviceStationaryHelper =
                            this.mDeviceStationaryHelper;
                    Preconditions.checkState(systemDeviceStationaryHelper.mDeviceIdle != null);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        systemDeviceStationaryHelper.mDeviceIdle.unregisterStationaryListener(this);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        this.mDeviceStationary = false;
                        this.mDeviceStationaryRealtimeMs = Long.MIN_VALUE;
                        onThrottlingChangedLocked(false);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void onDeviceStationaryChanged(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mDeviceIdle) {
                    if (this.mDeviceStationary == z) {
                        return;
                    }
                    this.mDeviceStationary = z;
                    if (z) {
                        this.mDeviceStationaryRealtimeMs = SystemClock.elapsedRealtime();
                    } else {
                        this.mDeviceStationaryRealtimeMs = Long.MIN_VALUE;
                    }
                    onThrottlingChangedLocked(false);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onExtraCommand(int i, String str, Bundle bundle, int i2) {
        Preconditions.checkState(this.mInitialized);
        this.mDelegate.mController.sendExtraCommand(i, str, bundle, i2);
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onFlush(
            LocationProviderManager$Registration$$ExternalSyntheticLambda0
                    locationProviderManager$Registration$$ExternalSyntheticLambda0) {
        Preconditions.checkState(this.mInitialized);
        this.mDelegate.mController.flush(
                locationProviderManager$Registration$$ExternalSyntheticLambda0);
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider.Listener
    public final void onReportLocation(LocationResult locationResult) {
        synchronized (this.mInitializationLock) {
            Preconditions.checkState(this.mInitialized);
        }
        reportLocation(locationResult);
        synchronized (this.mLock) {
            this.mLastLocation = locationResult.getLastLocation();
            onThrottlingChangedLocked(false);
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onSetRequest(ProviderRequest providerRequest) {
        synchronized (this.mLock) {
            this.mIncomingRequest = providerRequest;
            onThrottlingChangedLocked(true);
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onStart() {
        this.mDelegate.mController.start();
        synchronized (this.mLock) {
            this.mDeviceIdleHelper.addListener(this);
            SystemDeviceIdleHelper systemDeviceIdleHelper = this.mDeviceIdleHelper;
            Preconditions.checkState(systemDeviceIdleHelper.mPowerManager != null);
            onDeviceIdleChanged(systemDeviceIdleHelper.mPowerManager.isDeviceIdleMode());
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider.Listener
    public final void onStateChanged(
            AbstractLocationProvider.State state, AbstractLocationProvider.State state2) {
        synchronized (this.mInitializationLock) {
            Preconditions.checkState(this.mInitialized);
        }
        setState(new DelegateLocationProvider$$ExternalSyntheticLambda0(1, state2));
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onStop() {
        synchronized (this.mLock) {
            try {
                SystemDeviceIdleHelper systemDeviceIdleHelper = this.mDeviceIdleHelper;
                synchronized (systemDeviceIdleHelper) {
                    if (systemDeviceIdleHelper.mListeners.remove(this)
                            && systemDeviceIdleHelper.mListeners.isEmpty()) {
                        synchronized (systemDeviceIdleHelper) {
                            systemDeviceIdleHelper.mRegistrationRequired = false;
                            systemDeviceIdleHelper.onRegistrationStateChanged();
                        }
                    }
                }
                onDeviceIdleChanged(false);
                ProviderRequest providerRequest = ProviderRequest.EMPTY_REQUEST;
                this.mIncomingRequest = providerRequest;
                this.mOutgoingRequest = providerRequest;
                this.mThrottlingIntervalMs = Long.MAX_VALUE;
                if (this.mDeliverLastLocationCallback != null) {
                    LocationServiceThread.getHandler()
                            .removeCallbacks(this.mDeliverLastLocationCallback);
                    this.mDeliverLastLocationCallback = null;
                }
                this.mLastLocation = null;
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mDelegate.mController.stop();
    }

    public final void onThrottlingChangedLocked(boolean z) {
        Location location;
        long max =
                (!this.mDeviceStationary
                                || !this.mDeviceIdle
                                || this.mIncomingRequest.isLocationSettingsIgnored()
                                || this.mIncomingRequest.getQuality() == 100
                                || (location = this.mLastLocation) == null
                                || location.getElapsedRealtimeAgeMillis(
                                                this.mDeviceStationaryRealtimeMs)
                                        > 30000)
                        ? Long.MAX_VALUE
                        : Math.max(this.mIncomingRequest.getIntervalMillis(), 1000L);
        ProviderRequest providerRequest =
                max != Long.MAX_VALUE ? ProviderRequest.EMPTY_REQUEST : this.mIncomingRequest;
        if (!providerRequest.equals(this.mOutgoingRequest)) {
            this.mOutgoingRequest = providerRequest;
            this.mDelegate.mController.setRequest(providerRequest);
        }
        long j = this.mThrottlingIntervalMs;
        if (max == j) {
            return;
        }
        this.mThrottlingIntervalMs = max;
        if (max == Long.MAX_VALUE) {
            if (j != Long.MAX_VALUE) {
                LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
                String str = this.mName;
                ProviderRequest providerRequest2 = this.mOutgoingRequest;
                locationEventLog.getClass();
                locationEventLog.addLog$1(
                        new LocationEventLog.ProviderClientPermittedEvent(
                                str, false, providerRequest2, 2));
                RCPManagerService$$ExternalSyntheticOutline0.m(
                        new StringBuilder(),
                        this.mName,
                        " provider stationary unthrottled",
                        "LocationManagerService");
            }
            LocationServiceThread.getHandler().removeCallbacks(this.mDeliverLastLocationCallback);
            this.mDeliverLastLocationCallback = null;
            return;
        }
        if (j == Long.MAX_VALUE) {
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    new StringBuilder(),
                    this.mName,
                    " provider stationary throttled",
                    "LocationManagerService");
            LocationEventLog locationEventLog2 = LocationEventLog.EVENT_LOG;
            String str2 = this.mName;
            ProviderRequest providerRequest3 = this.mOutgoingRequest;
            locationEventLog2.getClass();
            locationEventLog2.addLog$1(
                    new LocationEventLog.ProviderClientPermittedEvent(
                            str2, true, providerRequest3, 2));
        }
        if (this.mDeliverLastLocationCallback != null) {
            LocationServiceThread.getHandler().removeCallbacks(this.mDeliverLastLocationCallback);
        }
        this.mDeliverLastLocationCallback = new DeliverLastLocationRunnable();
        Preconditions.checkState(this.mLastLocation != null);
        if (z) {
            LocationServiceThread.getHandler().post(this.mDeliverLastLocationCallback);
        } else {
            LocationServiceThread.getHandler()
                    .postDelayed(
                            this.mDeliverLastLocationCallback,
                            this.mThrottlingIntervalMs
                                    - this.mLastLocation.getElapsedRealtimeAgeMillis());
        }
    }
}
