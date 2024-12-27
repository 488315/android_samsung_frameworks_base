package com.android.server.location.gnss;

import android.location.IGnssNavigationMessageListener;
import android.location.LocationConstants;
import android.location.util.identity.CallerIdentity;
import android.os.IInterface;
import android.util.Log;

import com.android.server.location.LocationManagerService;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.location.injector.Injector;
import com.android.server.location.injector.SystemAppOpsHelper;

import java.util.Collection;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class GnssNavigationMessageProvider extends GnssListenerMultiplexer
        implements GnssNative.BaseCallbacks, GnssNative.NavigationMessageCallbacks {
    public final SystemAppOpsHelper mAppOpsHelper;
    public final GnssNative mGnssNative;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GnssNavigationMessageListenerRegistration
            extends GnssListenerMultiplexer.GnssListenerRegistration {
        @Override // com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration, com.android.server.location.listeners.BinderListenerRegistration, com.android.server.location.listeners.RemovableListenerRegistration
        public final void onRegister() {
            super.onRegister();
            executeOperation(
                    new GnssNavigationMessageProvider$GnssNavigationMessageListenerRegistration$$ExternalSyntheticLambda0());
        }
    }

    public GnssNavigationMessageProvider(Injector injector, GnssNative gnssNative) {
        super(injector);
        this.mAppOpsHelper = ((LocationManagerService.SystemInjector) injector).mAppOpsHelper;
        this.mGnssNative = gnssNative;
        gnssNative.addBaseCallbacks(this);
        gnssNative.addNavigationMessageCallbacks(this);
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public final GnssListenerMultiplexer.GnssListenerRegistration createRegistration(
            Object obj, CallerIdentity callerIdentity, IInterface iInterface) {
        return new GnssNavigationMessageListenerRegistration(
                callerIdentity, (IGnssNavigationMessageListener) iInterface, this, null);
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public final LocationConstants.LISTENER_TYPE getListenerType() {
        return LocationConstants.LISTENER_TYPE.GNSS_NAVIGATION_MESSAGE;
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public final boolean isSupported() {
        return this.mGnssNative.isNavigationMessageCollectionSupported();
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final boolean registerWithService(Collection collection, Object obj) {
        if (this.mGnssNative.startNavigationMessageCollection()) {
            Log.d("GnssManager", "starting gnss navigation messages");
            return true;
        }
        Log.e("GnssManager", "error starting gnss navigation messages");
        return false;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void unregisterWithService() {
        if (this.mGnssNative.stopNavigationMessageCollection()) {
            Log.d("GnssManager", "stopping gnss navigation messages");
        } else {
            Log.e("GnssManager", "error stopping gnss navigation messages");
        }
    }
}
