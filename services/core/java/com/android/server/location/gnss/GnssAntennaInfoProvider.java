package com.android.server.location.gnss;

import android.location.IGnssAntennaInfoListener;
import android.location.LocationConstants;
import android.location.util.identity.CallerIdentity;
import android.os.Binder;
import android.os.IBinder;
import com.android.internal.listeners.ListenerExecutor;
import com.android.internal.util.ConcurrentUtils;
import com.android.server.FgThread;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.location.listeners.BinderListenerRegistration;
import com.android.server.location.listeners.ListenerMultiplexer;
import com.android.server.location.listeners.ListenerRegistration;
import com.android.server.location.nsflp.NSLocationProviderHelper;
import java.util.Collection;
import java.util.List;

/* loaded from: classes2.dex */
public class GnssAntennaInfoProvider extends ListenerMultiplexer implements GnssNative.BaseCallbacks, GnssNative.AntennaInfoCallbacks {
    public volatile List mAntennaInfos;
    public final GnssNative mGnssNative;
    public NSLocationProviderHelper mNSLocationProviderHelper = null;

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public boolean isActive(ListenerRegistration listenerRegistration) {
        return true;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public Void mergeRegistrations(Collection collection) {
        return null;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public boolean registerWithService(Void r1, Collection collection) {
        return true;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void unregisterWithService() {
    }

    public class AntennaInfoListenerRegistration extends BinderListenerRegistration {
        public final CallerIdentity mIdentity;

        @Override // com.android.server.location.listeners.BinderListenerRegistration
        public IBinder getBinderFromKey(IBinder iBinder) {
            return iBinder;
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        public String getTag() {
            return "GnssManager";
        }

        public AntennaInfoListenerRegistration(CallerIdentity callerIdentity, IGnssAntennaInfoListener iGnssAntennaInfoListener) {
            super(callerIdentity.isMyProcess() ? FgThread.getExecutor() : ConcurrentUtils.DIRECT_EXECUTOR, iGnssAntennaInfoListener);
            this.mIdentity = callerIdentity;
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public GnssAntennaInfoProvider getOwner() {
            return GnssAntennaInfoProvider.this;
        }

        public String toString() {
            return this.mIdentity.toString();
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        public void onActive() {
            super.onActive();
            updateAntennaListener();
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        public void onInactive() {
            super.onInactive();
            updateAntennaListener();
        }

        public final void updateAntennaListener() {
            GnssAntennaInfoProvider.this.updateGnssDataListener(getBinder(), isActive(), this.mIdentity.getPackageName(), this.mIdentity.getUid(), this.mIdentity.getPid());
        }

        public final CallerIdentity getIdentity() {
            return this.mIdentity;
        }
    }

    public GnssAntennaInfoProvider(GnssNative gnssNative) {
        this.mGnssNative = gnssNative;
        gnssNative.addBaseCallbacks(this);
        gnssNative.addAntennaInfoCallbacks(this);
    }

    public void setNSLocationProviderHelper(NSLocationProviderHelper nSLocationProviderHelper) {
        this.mNSLocationProviderHelper = nSLocationProviderHelper;
    }

    public List getAntennaInfos() {
        return this.mAntennaInfos;
    }

    public boolean isSupported() {
        return this.mGnssNative.isAntennaInfoSupported();
    }

    public void addListener(CallerIdentity callerIdentity, IGnssAntennaInfoListener iGnssAntennaInfoListener) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            putRegistration(iGnssAntennaInfoListener.asBinder(), new AntennaInfoListenerRegistration(callerIdentity, iGnssAntennaInfoListener));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void removeListener(IGnssAntennaInfoListener iGnssAntennaInfoListener) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            removeRegistration(iGnssAntennaInfoListener.asBinder());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public void onHalStarted() {
        this.mGnssNative.startAntennaInfoListening();
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public void onHalRestarted() {
        this.mGnssNative.startAntennaInfoListening();
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.AntennaInfoCallbacks
    public void onReportAntennaInfo(final List list) {
        if (list.equals(this.mAntennaInfos)) {
            return;
        }
        this.mAntennaInfos = list;
        deliverToListeners(new ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssAntennaInfoProvider$$ExternalSyntheticLambda0
            public final void operate(Object obj) {
                ((IGnssAntennaInfoListener) obj).onGnssAntennaInfoChanged(list);
            }
        });
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onRegistrationAdded(IBinder iBinder, ListenerRegistration listenerRegistration) {
        super.onRegistrationAdded((Object) iBinder, listenerRegistration);
        if (this.mNSLocationProviderHelper == null || !(listenerRegistration instanceof AntennaInfoListenerRegistration)) {
            return;
        }
        CallerIdentity identity = ((AntennaInfoListenerRegistration) listenerRegistration).getIdentity();
        this.mNSLocationProviderHelper.addGnssDataListener(iBinder, identity.getPackageName(), LocationConstants.LISTENER_TYPE.GNSS_ANTENNA_INFO, identity.getUid(), identity.getPid(), true, true);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onRegistrationRemoved(IBinder iBinder, ListenerRegistration listenerRegistration) {
        super.onRegistrationRemoved((Object) iBinder, listenerRegistration);
        if (this.mNSLocationProviderHelper == null || !(listenerRegistration instanceof AntennaInfoListenerRegistration)) {
            return;
        }
        CallerIdentity identity = ((AntennaInfoListenerRegistration) listenerRegistration).getIdentity();
        this.mNSLocationProviderHelper.removeGnssDataListener(iBinder, LocationConstants.LISTENER_TYPE.GNSS_ANTENNA_INFO, identity.getUid(), identity.getPid());
    }

    public final void updateGnssDataListener(IBinder iBinder, boolean z, String str, int i, int i2) {
        NSLocationProviderHelper nSLocationProviderHelper = this.mNSLocationProviderHelper;
        if (nSLocationProviderHelper == null) {
            return;
        }
        nSLocationProviderHelper.updateGnssDataListener(iBinder, z, str, LocationConstants.LISTENER_TYPE.GNSS_ANTENNA_INFO, i, i2);
    }
}
