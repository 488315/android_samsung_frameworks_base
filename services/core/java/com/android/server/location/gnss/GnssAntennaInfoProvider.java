package com.android.server.location.gnss;

import android.location.IGnssAntennaInfoListener;
import android.location.LocationConstants;
import android.location.util.identity.CallerIdentity;
import android.os.IBinder;

import com.android.internal.util.ConcurrentUtils;
import com.android.server.location.LocationServiceThread;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.location.listeners.BinderListenerRegistration;
import com.android.server.location.listeners.ListenerMultiplexer;
import com.android.server.location.listeners.RemovableListenerRegistration;
import com.android.server.location.nsflp.NSLocationProviderHelper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class GnssAntennaInfoProvider extends ListenerMultiplexer
        implements GnssNative.BaseCallbacks, GnssNative.AntennaInfoCallbacks {
    public volatile List mAntennaInfos;
    public final GnssNative mGnssNative;
    public NSLocationProviderHelper mNSLocationProviderHelper = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AntennaInfoListenerRegistration extends BinderListenerRegistration {
        public final CallerIdentity mIdentity;

        public AntennaInfoListenerRegistration(
                CallerIdentity callerIdentity, IGnssAntennaInfoListener iGnssAntennaInfoListener) {
            super(
                    callerIdentity.isMyProcess()
                            ? LocationServiceThread.getExecutor()
                            : ConcurrentUtils.DIRECT_EXECUTOR,
                    iGnssAntennaInfoListener);
            this.mIdentity = callerIdentity;
        }

        @Override // com.android.server.location.listeners.BinderListenerRegistration
        public final IBinder getBinderFromKey(Object obj) {
            return (IBinder) obj;
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final ListenerMultiplexer getOwner() {
            return GnssAntennaInfoProvider.this;
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final String getTag() {
            return "GnssManager";
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final void onActive() {
            updateAntennaListener();
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final void onInactive() {
            updateAntennaListener();
        }

        public final String toString() {
            return this.mIdentity.toString();
        }

        public final void updateAntennaListener() {
            IBinder iBinder;
            GnssAntennaInfoProvider gnssAntennaInfoProvider = GnssAntennaInfoProvider.this;
            try {
                Object obj = this.mKey;
                Objects.requireNonNull(obj);
                iBinder = getBinderFromKey(obj);
            } catch (IllegalArgumentException | NullPointerException unused) {
                iBinder = null;
            }
            IBinder iBinder2 = iBinder;
            boolean z = this.mActive;
            String packageName = this.mIdentity.getPackageName();
            int uid = this.mIdentity.getUid();
            int pid = this.mIdentity.getPid();
            NSLocationProviderHelper nSLocationProviderHelper =
                    gnssAntennaInfoProvider.mNSLocationProviderHelper;
            if (nSLocationProviderHelper == null) {
                return;
            }
            nSLocationProviderHelper.updateGnssDataListener(
                    iBinder2,
                    z,
                    packageName,
                    LocationConstants.LISTENER_TYPE.GNSS_ANTENNA_INFO,
                    uid,
                    pid);
        }
    }

    public GnssAntennaInfoProvider(GnssNative gnssNative) {
        this.mGnssNative = gnssNative;
        gnssNative.addBaseCallbacks(this);
        gnssNative.addAntennaInfoCallbacks(this);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final boolean isActive(RemovableListenerRegistration removableListenerRegistration) {
        return true;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final /* bridge */ /* synthetic */ Object mergeRegistrations(Collection collection) {
        return null;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer,
              // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public final void onHalRestarted() {
        this.mGnssNative.startAntennaInfoListening();
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public final void onHalStarted() {
        this.mGnssNative.startAntennaInfoListening();
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void onRegistrationAdded(
            Object obj, RemovableListenerRegistration removableListenerRegistration) {
        IBinder iBinder = (IBinder) obj;
        NSLocationProviderHelper nSLocationProviderHelper = this.mNSLocationProviderHelper;
        if (nSLocationProviderHelper == null
                || !(removableListenerRegistration instanceof AntennaInfoListenerRegistration)) {
            return;
        }
        CallerIdentity callerIdentity =
                ((AntennaInfoListenerRegistration) removableListenerRegistration).mIdentity;
        nSLocationProviderHelper.addGnssDataListener(
                iBinder,
                callerIdentity.getPackageName(),
                LocationConstants.LISTENER_TYPE.GNSS_ANTENNA_INFO,
                callerIdentity.getUid(),
                callerIdentity.getPid(),
                true,
                true);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void onRegistrationRemoved(
            Object obj, RemovableListenerRegistration removableListenerRegistration) {
        IBinder iBinder = (IBinder) obj;
        NSLocationProviderHelper nSLocationProviderHelper = this.mNSLocationProviderHelper;
        if (nSLocationProviderHelper == null
                || !(removableListenerRegistration instanceof AntennaInfoListenerRegistration)) {
            return;
        }
        CallerIdentity callerIdentity =
                ((AntennaInfoListenerRegistration) removableListenerRegistration).mIdentity;
        nSLocationProviderHelper.removeGnssDataListener(
                iBinder,
                LocationConstants.LISTENER_TYPE.GNSS_ANTENNA_INFO,
                callerIdentity.getUid(),
                callerIdentity.getPid());
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final /* bridge */ /* synthetic */ boolean registerWithService(
            Collection collection, Object obj) {
        return true;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void unregisterWithService() {}
}
