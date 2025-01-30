package com.android.server.location.gnss;

import android.location.GnssMeasurementRequest;
import android.location.GnssMeasurementsEvent;
import android.location.IGnssMeasurementsListener;
import android.location.LocationConstants;
import android.location.util.identity.CallerIdentity;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;
import com.android.internal.listeners.ListenerExecutor;
import com.android.server.location.gnss.GnssConfiguration;
import com.android.server.location.gnss.GnssListenerMultiplexer;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.location.injector.AppOpsHelper;
import com.android.server.location.injector.Injector;
import com.android.server.location.injector.LocationUsageLogger;
import com.android.server.location.injector.SettingsHelper;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

/* loaded from: classes2.dex */
public final class GnssMeasurementsProvider extends GnssListenerMultiplexer implements SettingsHelper.GlobalSettingChangedListener, GnssNative.BaseCallbacks, GnssNative.MeasurementCallbacks {
    public final AppOpsHelper mAppOpsHelper;
    public final GnssNative mGnssNative;
    public final LocationUsageLogger mLogger;

    public class GnssMeasurementListenerRegistration extends GnssListenerMultiplexer.GnssListenerRegistration {
        public GnssMeasurementListenerRegistration(GnssMeasurementRequest gnssMeasurementRequest, CallerIdentity callerIdentity, IGnssMeasurementsListener iGnssMeasurementsListener) {
            super(gnssMeasurementRequest, callerIdentity, iGnssMeasurementsListener);
        }

        @Override // com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration, com.android.server.location.listeners.BinderListenerRegistration, com.android.server.location.listeners.RemovableListenerRegistration
        public void onRegister() {
            super.onRegister();
            executeOperation(new ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssMeasurementsProvider$GnssMeasurementListenerRegistration$$ExternalSyntheticLambda0
                public final void operate(Object obj) {
                    ((IGnssMeasurementsListener) obj).onStatusChanged(1);
                }
            });
        }

        @Override // com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration, com.android.server.location.listeners.ListenerRegistration
        public void onActive() {
            GnssMeasurementsProvider.this.mAppOpsHelper.startOpNoThrow(42, getIdentity());
            updateGnssListener();
        }

        @Override // com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration, com.android.server.location.listeners.ListenerRegistration
        public void onInactive() {
            GnssMeasurementsProvider.this.mAppOpsHelper.finishOp(42, getIdentity());
            updateGnssListener();
        }
    }

    public GnssMeasurementsProvider(Injector injector, GnssNative gnssNative) {
        super(injector);
        this.mAppOpsHelper = injector.getAppOpsHelper();
        this.mLogger = injector.getLocationUsageLogger();
        this.mGnssNative = gnssNative;
        gnssNative.addBaseCallbacks(this);
        gnssNative.addMeasurementCallbacks(this);
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public boolean isSupported() {
        return this.mGnssNative.isMeasurementSupported();
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public void addListener(GnssMeasurementRequest gnssMeasurementRequest, CallerIdentity callerIdentity, IGnssMeasurementsListener iGnssMeasurementsListener) {
        super.addListener((Object) gnssMeasurementRequest, callerIdentity, (IInterface) iGnssMeasurementsListener);
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public GnssListenerMultiplexer.GnssListenerRegistration createRegistration(GnssMeasurementRequest gnssMeasurementRequest, CallerIdentity callerIdentity, IGnssMeasurementsListener iGnssMeasurementsListener) {
        return new GnssMeasurementListenerRegistration(gnssMeasurementRequest, callerIdentity, iGnssMeasurementsListener);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public boolean registerWithService(GnssMeasurementRequest gnssMeasurementRequest, Collection collection) {
        if (gnssMeasurementRequest.getIntervalMillis() == Integer.MAX_VALUE) {
            return true;
        }
        if (this.mGnssNative.startMeasurementCollection(gnssMeasurementRequest.isFullTracking(), gnssMeasurementRequest.isCorrelationVectorOutputsEnabled(), gnssMeasurementRequest.getIntervalMillis())) {
            Log.d("GnssManager", "starting gnss measurements (" + gnssMeasurementRequest + ")");
            return true;
        }
        Log.e("GnssManager", "error starting gnss measurements");
        return false;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public boolean reregisterWithService(GnssMeasurementRequest gnssMeasurementRequest, GnssMeasurementRequest gnssMeasurementRequest2, Collection collection) {
        if (gnssMeasurementRequest2.getIntervalMillis() == Integer.MAX_VALUE) {
            unregisterWithService();
            return true;
        }
        GnssConfiguration.HalInterfaceVersion halInterfaceVersion = this.mGnssNative.getConfiguration().getHalInterfaceVersion();
        if (!(halInterfaceVersion.mMajor == 3 && halInterfaceVersion.mMinor >= 3)) {
            unregisterWithService();
        }
        return registerWithService(gnssMeasurementRequest2, collection);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void unregisterWithService() {
        if (this.mGnssNative.stopMeasurementCollection()) {
            Log.d("GnssManager", "stopping gnss measurements");
        } else {
            Log.e("GnssManager", "error stopping gnss measurements");
        }
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onActive() {
        this.mSettingsHelper.addOnGnssMeasurementsFullTrackingEnabledChangedListener(this);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onInactive() {
        this.mSettingsHelper.removeOnGnssMeasurementsFullTrackingEnabledChangedListener(this);
    }

    @Override // com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener
    public void onSettingChanged() {
        updateService();
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer, com.android.server.location.listeners.ListenerMultiplexer
    public GnssMeasurementRequest mergeRegistrations(Collection collection) {
        boolean isGnssMeasurementsFullTrackingEnabled = this.mSettingsHelper.isGnssMeasurementsFullTrackingEnabled();
        Iterator it = collection.iterator();
        boolean z = false;
        int i = Integer.MAX_VALUE;
        while (it.hasNext()) {
            GnssMeasurementRequest gnssMeasurementRequest = (GnssMeasurementRequest) ((GnssListenerMultiplexer.GnssListenerRegistration) it.next()).getRequest();
            if (gnssMeasurementRequest.getIntervalMillis() != Integer.MAX_VALUE) {
                if (gnssMeasurementRequest.isFullTracking()) {
                    isGnssMeasurementsFullTrackingEnabled = true;
                }
                if (gnssMeasurementRequest.isCorrelationVectorOutputsEnabled()) {
                    z = true;
                }
                i = Math.min(i, gnssMeasurementRequest.getIntervalMillis());
            }
        }
        return new GnssMeasurementRequest.Builder().setFullTracking(isGnssMeasurementsFullTrackingEnabled).setCorrelationVectorOutputsEnabled(z).setIntervalMillis(i).build();
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onRegistrationAdded(IBinder iBinder, GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        this.mLogger.logLocationApiUsage(0, 2, gnssListenerRegistration.getIdentity().getPackageName(), gnssListenerRegistration.getIdentity().getAttributionTag(), null, null, true, false, null, gnssListenerRegistration.isForeground());
        addGnssDataListener(iBinder, gnssListenerRegistration);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onRegistrationRemoved(IBinder iBinder, GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        this.mLogger.logLocationApiUsage(1, 2, gnssListenerRegistration.getIdentity().getPackageName(), gnssListenerRegistration.getIdentity().getAttributionTag(), null, null, true, false, null, gnssListenerRegistration.isForeground());
        removeGnssDataListener(iBinder, gnssListenerRegistration);
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public void onHalRestarted() {
        resetService();
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.MeasurementCallbacks
    public void onReportMeasurements(final GnssMeasurementsEvent gnssMeasurementsEvent) {
        deliverToListeners(new Function() { // from class: com.android.server.location.gnss.GnssMeasurementsProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ListenerExecutor.ListenerOperation lambda$onReportMeasurements$1;
                lambda$onReportMeasurements$1 = GnssMeasurementsProvider.this.lambda$onReportMeasurements$1(gnssMeasurementsEvent, (GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onReportMeasurements$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ListenerExecutor.ListenerOperation lambda$onReportMeasurements$1(final GnssMeasurementsEvent gnssMeasurementsEvent, GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        if (this.mAppOpsHelper.noteOpNoThrow(1, gnssListenerRegistration.getIdentity())) {
            return new ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssMeasurementsProvider$$ExternalSyntheticLambda1
                public final void operate(Object obj) {
                    ((IGnssMeasurementsListener) obj).onGnssMeasurementsReceived(gnssMeasurementsEvent);
                }
            };
        }
        return null;
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public LocationConstants.LISTENER_TYPE getListenerType() {
        return LocationConstants.LISTENER_TYPE.GNSS_MEASUREMENT;
    }
}
