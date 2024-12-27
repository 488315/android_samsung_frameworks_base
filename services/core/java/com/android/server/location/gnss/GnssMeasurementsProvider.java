package com.android.server.location.gnss;

import android.location.GnssMeasurement;
import android.location.GnssMeasurementRequest;
import android.location.GnssMeasurementsEvent;
import android.location.IGnssMeasurementsListener;
import android.location.LocationConstants;
import android.location.util.identity.CallerIdentity;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

import com.android.internal.util.Preconditions;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.location.LocationManagerService;
import com.android.server.location.eventlog.LocationEventLog;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.location.injector.Injector;
import com.android.server.location.injector.LocationUsageLogger;
import com.android.server.location.injector.SettingsHelper$GlobalSettingChangedListener;
import com.android.server.location.injector.SystemAppOpsHelper;
import com.android.server.location.injector.SystemSettingsHelper;
import com.android.server.location.listeners.RemovableListenerRegistration;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public final class GnssMeasurementsProvider extends GnssListenerMultiplexer
        implements SettingsHelper$GlobalSettingChangedListener,
                GnssNative.BaseCallbacks,
                GnssNative.MeasurementCallbacks {
    public final SystemAppOpsHelper mAppOpsHelper;
    public final GnssNative mGnssNative;
    public GnssMeasurementsEvent mLastGnssMeasurementsEvent;
    public final LocationUsageLogger mLogger;

    public final class GnssMeasurementListenerRegistration
            extends GnssListenerMultiplexer.GnssListenerRegistration {
        public GnssMeasurementListenerRegistration(
                GnssMeasurementRequest gnssMeasurementRequest,
                CallerIdentity callerIdentity,
                IGnssMeasurementsListener iGnssMeasurementsListener) {
            super(
                    callerIdentity,
                    iGnssMeasurementsListener,
                    GnssMeasurementsProvider.this,
                    gnssMeasurementRequest);
        }

        @Override // com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration, com.android.server.location.listeners.RemovableListenerRegistration
        public final void onActive() {
            GnssMeasurementsProvider.this.mAppOpsHelper.startOpNoThrow(42, this.mIdentity);
            updateGnssListener();
        }

        @Override // com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration, com.android.server.location.listeners.RemovableListenerRegistration
        public final void onInactive() {
            GnssMeasurementsProvider.this.mAppOpsHelper.finishOp(42, this.mIdentity);
            updateGnssListener();
        }

        @Override // com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration, com.android.server.location.listeners.BinderListenerRegistration, com.android.server.location.listeners.RemovableListenerRegistration
        public final void onRegister() {
            super.onRegister();
            LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
            CallerIdentity callerIdentity = this.mIdentity;
            GnssMeasurementRequest gnssMeasurementRequest = (GnssMeasurementRequest) this.mRequest;
            locationEventLog.getClass();
            locationEventLog.addLog$1(
                    new LocationEventLog.GnssMeasurementClientRegisterEvent(
                            true, callerIdentity, gnssMeasurementRequest));
            LocationEventLog.GnssMeasurementAggregateStats gnssMeasurementAggregateStats =
                    locationEventLog.getGnssMeasurementAggregateStats(callerIdentity);
            long intervalMillis = gnssMeasurementRequest.getIntervalMillis();
            boolean isFullTracking = gnssMeasurementRequest.isFullTracking();
            synchronized (gnssMeasurementAggregateStats) {
                try {
                    int i = gnssMeasurementAggregateStats.mAddedRequestCount;
                    gnssMeasurementAggregateStats.mAddedRequestCount = i + 1;
                    if (i == 0) {
                        gnssMeasurementAggregateStats.mAddedTimeLastUpdateRealtimeMs =
                                SystemClock.elapsedRealtime();
                    }
                    if (isFullTracking) {
                        gnssMeasurementAggregateStats.mHasFullTracking = true;
                    } else {
                        gnssMeasurementAggregateStats.mHasDutyCycling = true;
                    }
                    gnssMeasurementAggregateStats.mFastestIntervalMs =
                            Math.min(
                                    intervalMillis,
                                    gnssMeasurementAggregateStats.mFastestIntervalMs);
                    gnssMeasurementAggregateStats.mSlowestIntervalMs =
                            Math.max(
                                    intervalMillis,
                                    gnssMeasurementAggregateStats.mSlowestIntervalMs);
                } catch (Throwable th) {
                    throw th;
                }
            }
            executeOperation(
                    new GnssMeasurementsProvider$GnssMeasurementListenerRegistration$$ExternalSyntheticLambda0());
        }

        @Override // com.android.server.location.listeners.BinderListenerRegistration,
                  // com.android.server.location.listeners.RemovableListenerRegistration
        public final void onUnregister() {
            LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
            CallerIdentity callerIdentity = this.mIdentity;
            locationEventLog.getClass();
            locationEventLog.addLog$1(
                    new LocationEventLog.GnssMeasurementClientRegisterEvent(
                            false, callerIdentity, null));
            LocationEventLog.GnssMeasurementAggregateStats gnssMeasurementAggregateStats =
                    locationEventLog.getGnssMeasurementAggregateStats(callerIdentity);
            synchronized (gnssMeasurementAggregateStats) {
                gnssMeasurementAggregateStats.updateTotals();
                int i = gnssMeasurementAggregateStats.mAddedRequestCount - 1;
                gnssMeasurementAggregateStats.mAddedRequestCount = i;
                Preconditions.checkState(i >= 0);
            }
            super.onUnregister();
        }
    }

    public GnssMeasurementsProvider(Injector injector, GnssNative gnssNative) {
        super(injector);
        LocationManagerService.SystemInjector systemInjector =
                (LocationManagerService.SystemInjector) injector;
        this.mAppOpsHelper = systemInjector.mAppOpsHelper;
        this.mLogger = systemInjector.mLocationUsageLogger;
        this.mGnssNative = gnssNative;
        gnssNative.addBaseCallbacks(this);
        gnssNative.addMeasurementCallbacks(this);
    }

    public final void addListener(
            GnssMeasurementRequest gnssMeasurementRequest,
            CallerIdentity callerIdentity,
            IGnssMeasurementsListener iGnssMeasurementsListener) {
        super.addListener(
                (Object) gnssMeasurementRequest,
                callerIdentity,
                (IInterface) iGnssMeasurementsListener);
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public final void addListener(
            Object obj, CallerIdentity callerIdentity, IInterface iInterface) {
        throw null;
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public final GnssListenerMultiplexer.GnssListenerRegistration createRegistration(
            Object obj, CallerIdentity callerIdentity, IInterface iInterface) {
        return new GnssMeasurementListenerRegistration(
                (GnssMeasurementRequest) obj,
                callerIdentity,
                (IGnssMeasurementsListener) iInterface);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String sb;
        super.dump(fileDescriptor, printWriter, strArr);
        printWriter.print("last measurements=");
        synchronized (this.mMultiplexerLock) {
            try {
                if (this.mLastGnssMeasurementsEvent == null) {
                    sb = null;
                } else {
                    StringBuilder sb2 = new StringBuilder("[");
                    sb2.append("elapsedRealtimeNs=");
                    sb2.append(
                            this.mLastGnssMeasurementsEvent.getClock().getElapsedRealtimeNanos());
                    sb2.append(" measurementCount=");
                    sb2.append(this.mLastGnssMeasurementsEvent.getMeasurements().size());
                    float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
                    int i = 0;
                    for (GnssMeasurement gnssMeasurement :
                            this.mLastGnssMeasurementsEvent.getMeasurements()) {
                        if (gnssMeasurement.hasBasebandCn0DbHz()) {
                            f = (float) (f + gnssMeasurement.getBasebandCn0DbHz());
                            i++;
                        }
                    }
                    if (i > 0) {
                        sb2.append(" avgBasebandCn0=");
                        sb2.append(f / i);
                    }
                    sb2.append("]");
                    sb = sb2.toString();
                }
            } finally {
            }
        }
        printWriter.println(sb);
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public final LocationConstants.LISTENER_TYPE getListenerType() {
        return LocationConstants.LISTENER_TYPE.GNSS_MEASUREMENT;
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public final boolean isSupported() {
        return this.mGnssNative.isMeasurementSupported();
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer,
              // com.android.server.location.listeners.ListenerMultiplexer
    public final Object mergeRegistrations(Collection collection) {
        SystemSettingsHelper.LongGlobalSetting longGlobalSetting =
                this.mSettingsHelper.mGnssMeasurementFullTracking;
        longGlobalSetting.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            boolean z = false;
            boolean z2 =
                    Settings.Global.getInt(
                                    longGlobalSetting.mContext.getContentResolver(),
                                    longGlobalSetting.mSettingName,
                                    0)
                            != 0;
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Iterator it = ((ArrayList) collection).iterator();
            int i = Integer.MAX_VALUE;
            while (it.hasNext()) {
                GnssMeasurementRequest gnssMeasurementRequest =
                        (GnssMeasurementRequest)
                                ((GnssListenerMultiplexer.GnssListenerRegistration) it.next())
                                        .mRequest;
                if (gnssMeasurementRequest.getIntervalMillis() != Integer.MAX_VALUE) {
                    if (gnssMeasurementRequest.isFullTracking()) {
                        z2 = true;
                    }
                    if (gnssMeasurementRequest.isCorrelationVectorOutputsEnabled()) {
                        z = true;
                    }
                    i = Math.min(i, gnssMeasurementRequest.getIntervalMillis());
                }
            }
            return new GnssMeasurementRequest.Builder()
                    .setFullTracking(z2)
                    .setCorrelationVectorOutputsEnabled(z)
                    .setIntervalMillis(i)
                    .build();
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void onActive() {
        this.mSettingsHelper.mGnssMeasurementFullTracking.addListener(this);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void onInactive() {
        this.mSettingsHelper.mGnssMeasurementFullTracking.removeListener(this);
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer,
              // com.android.server.location.listeners.ListenerMultiplexer
    public final void onRegistrationAdded(
            Object obj, RemovableListenerRegistration removableListenerRegistration) {
        GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration =
                (GnssListenerMultiplexer.GnssListenerRegistration) removableListenerRegistration;
        this.mLogger.logLocationApiUsage(
                0,
                2,
                gnssListenerRegistration.mIdentity.getPackageName(),
                gnssListenerRegistration.mIdentity.getAttributionTag(),
                null,
                null,
                true,
                false,
                null,
                gnssListenerRegistration.mForeground);
        addGnssDataListener((IBinder) obj, gnssListenerRegistration);
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer,
              // com.android.server.location.listeners.ListenerMultiplexer
    public final void onRegistrationRemoved(
            Object obj, RemovableListenerRegistration removableListenerRegistration) {
        GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration =
                (GnssListenerMultiplexer.GnssListenerRegistration) removableListenerRegistration;
        this.mLogger.logLocationApiUsage(
                1,
                2,
                gnssListenerRegistration.mIdentity.getPackageName(),
                gnssListenerRegistration.mIdentity.getAttributionTag(),
                null,
                null,
                true,
                false,
                null,
                gnssListenerRegistration.mForeground);
        removeGnssDataListener((IBinder) obj, gnssListenerRegistration);
    }

    public final boolean registerWithService(GnssMeasurementRequest gnssMeasurementRequest) {
        if (gnssMeasurementRequest.getIntervalMillis() == Integer.MAX_VALUE) {
            return true;
        }
        if (!this.mGnssNative.startMeasurementCollection(
                gnssMeasurementRequest.isFullTracking(),
                gnssMeasurementRequest.isCorrelationVectorOutputsEnabled(),
                gnssMeasurementRequest.getIntervalMillis())) {
            Log.e("GnssManager", "error starting gnss measurements");
            return false;
        }
        Log.d("GnssManager", "starting gnss measurements (" + gnssMeasurementRequest + ")");
        return true;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final /* bridge */ /* synthetic */ boolean registerWithService(
            Collection collection, Object obj) {
        return registerWithService((GnssMeasurementRequest) obj);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final boolean reregisterWithService(Object obj, Object obj2, Collection collection) {
        GnssMeasurementRequest gnssMeasurementRequest = (GnssMeasurementRequest) obj2;
        if (gnssMeasurementRequest.getIntervalMillis() == Integer.MAX_VALUE) {
            unregisterWithService();
            return true;
        }
        this.mGnssNative.mConfiguration.getClass();
        GnssConfiguration.HalInterfaceVersion halInterfaceVersion =
                GnssConfiguration.getHalInterfaceVersion();
        if (halInterfaceVersion.mMajor != 3 || halInterfaceVersion.mMinor < 3) {
            unregisterWithService();
        }
        return registerWithService(gnssMeasurementRequest);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void unregisterWithService() {
        if (this.mGnssNative.stopMeasurementCollection()) {
            Log.d("GnssManager", "stopping gnss measurements");
        } else {
            Log.e("GnssManager", "error stopping gnss measurements");
        }
    }
}
