package com.android.server.location.gnss;

import android.location.GnssStatus;
import android.location.IGnssStatusListener;
import android.location.LocationConstants;
import android.os.IBinder;
import android.util.Log;

import com.android.internal.listeners.ListenerExecutor;
import com.android.server.location.LocationManagerService;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.location.injector.Injector;
import com.android.server.location.injector.LocationUsageLogger;
import com.android.server.location.injector.SystemAppOpsHelper;
import com.android.server.location.listeners.RemovableListenerRegistration;
import com.android.server.location.nsflp.NSConnectionHelper;

import java.util.Collection;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GnssStatusProvider extends GnssListenerMultiplexer
        implements GnssNative.BaseCallbacks,
                GnssNative.StatusCallbacks,
                GnssNative.SvStatusCallbacks {
    public final SystemAppOpsHelper mAppOpsHelper;
    public final GnssNative mGnssNative;
    public boolean mIsNavigating;
    public final LocationUsageLogger mLogger;
    public final NSConnectionHelper mNSConnectionHelper;
    public GnssManagerService$$ExternalSyntheticLambda0 mOnStatusChanged;

    public GnssStatusProvider(Injector injector, GnssNative gnssNative) {
        super(injector);
        this.mIsNavigating = false;
        LocationManagerService.SystemInjector systemInjector =
                (LocationManagerService.SystemInjector) injector;
        this.mAppOpsHelper = systemInjector.mAppOpsHelper;
        this.mLogger = systemInjector.mLocationUsageLogger;
        this.mGnssNative = gnssNative;
        this.mNSConnectionHelper = systemInjector.mNSConnectionHelper;
        gnssNative.addBaseCallbacks(this);
        gnssNative.addStatusCallbacks(this);
        gnssNative.addSvStatusCallbacks(this);
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public final LocationConstants.LISTENER_TYPE getListenerType() {
        return LocationConstants.LISTENER_TYPE.GNSS_STATUS;
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer,
              // com.android.server.location.listeners.ListenerMultiplexer
    public final void onRegistrationAdded(
            Object obj, RemovableListenerRegistration removableListenerRegistration) {
        GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration =
                (GnssListenerMultiplexer.GnssListenerRegistration) removableListenerRegistration;
        this.mLogger.logLocationApiUsage(
                0,
                3,
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
                3,
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

    @Override // com.android.server.location.gnss.hal.GnssNative.SvStatusCallbacks
    public final void onReportSvStatus(final GnssStatus gnssStatus) {
        deliverToListeners(
                new Function() { // from class:
                                 // com.android.server.location.gnss.GnssStatusProvider$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        GnssStatusProvider gnssStatusProvider = GnssStatusProvider.this;
                        final GnssStatus gnssStatus2 = gnssStatus;
                        gnssStatusProvider.getClass();
                        if (gnssStatusProvider.mAppOpsHelper.noteOpNoThrow(
                                1,
                                ((GnssListenerMultiplexer.GnssListenerRegistration) obj)
                                        .mIdentity)) {
                            return new ListenerExecutor
                                    .ListenerOperation() { // from class:
                                                           // com.android.server.location.gnss.GnssStatusProvider$$ExternalSyntheticLambda4
                                public final void operate(Object obj2) {
                                    ((IGnssStatusListener) obj2).onSvStatusChanged(gnssStatus2);
                                }
                            };
                        }
                        return null;
                    }
                });
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final boolean registerWithService(Collection collection, Object obj) {
        if (this.mGnssNative.startSvStatusCollection()) {
            Log.d("GnssManager", "starting gnss sv status");
            return true;
        }
        Log.e("GnssManager", "error starting gnss sv status");
        return false;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void unregisterWithService() {
        if (this.mGnssNative.stopSvStatusCollection()) {
            Log.d("GnssManager", "stopping gnss sv status");
        } else {
            Log.e("GnssManager", "error stopping gnss sv status");
        }
    }
}
