package com.android.server.location.gnss;

import android.location.GnssNavigationMessage;
import android.location.IGnssNavigationMessageListener;
import android.location.LocationConstants;
import android.location.util.identity.CallerIdentity;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;
import com.android.internal.listeners.ListenerExecutor;
import com.android.server.location.gnss.GnssListenerMultiplexer;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.location.injector.AppOpsHelper;
import com.android.server.location.injector.Injector;
import com.android.server.location.listeners.ListenerRegistration;
import java.util.Collection;
import java.util.function.Function;

/* loaded from: classes2.dex */
public class GnssNavigationMessageProvider extends GnssListenerMultiplexer implements GnssNative.BaseCallbacks, GnssNative.NavigationMessageCallbacks {
    public final AppOpsHelper mAppOpsHelper;
    public final GnssNative mGnssNative;

    public class GnssNavigationMessageListenerRegistration extends GnssListenerMultiplexer.GnssListenerRegistration {
        public GnssNavigationMessageListenerRegistration(CallerIdentity callerIdentity, IGnssNavigationMessageListener iGnssNavigationMessageListener) {
            super(null, callerIdentity, iGnssNavigationMessageListener);
        }

        @Override // com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration, com.android.server.location.listeners.BinderListenerRegistration, com.android.server.location.listeners.RemovableListenerRegistration
        public void onRegister() {
            super.onRegister();
            executeOperation(new ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssNavigationMessageProvider$GnssNavigationMessageListenerRegistration$$ExternalSyntheticLambda0
                public final void operate(Object obj) {
                    ((IGnssNavigationMessageListener) obj).onStatusChanged(1);
                }
            });
        }
    }

    public GnssNavigationMessageProvider(Injector injector, GnssNative gnssNative) {
        super(injector);
        this.mAppOpsHelper = injector.getAppOpsHelper();
        this.mGnssNative = gnssNative;
        gnssNative.addBaseCallbacks(this);
        gnssNative.addNavigationMessageCallbacks(this);
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public boolean isSupported() {
        return this.mGnssNative.isNavigationMessageCollectionSupported();
    }

    public void addListener(CallerIdentity callerIdentity, IGnssNavigationMessageListener iGnssNavigationMessageListener) {
        super.addListener(callerIdentity, (IInterface) iGnssNavigationMessageListener);
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public GnssListenerMultiplexer.GnssListenerRegistration createRegistration(Void r1, CallerIdentity callerIdentity, IGnssNavigationMessageListener iGnssNavigationMessageListener) {
        return new GnssNavigationMessageListenerRegistration(callerIdentity, iGnssNavigationMessageListener);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public boolean registerWithService(Void r1, Collection collection) {
        if (this.mGnssNative.startNavigationMessageCollection()) {
            Log.d("GnssManager", "starting gnss navigation messages");
            return true;
        }
        Log.e("GnssManager", "error starting gnss navigation messages");
        return false;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void unregisterWithService() {
        if (this.mGnssNative.stopNavigationMessageCollection()) {
            Log.d("GnssManager", "stopping gnss navigation messages");
        } else {
            Log.e("GnssManager", "error stopping gnss navigation messages");
        }
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public void onHalRestarted() {
        resetService();
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.NavigationMessageCallbacks
    public void onReportNavigationMessage(final GnssNavigationMessage gnssNavigationMessage) {
        deliverToListeners(new Function() { // from class: com.android.server.location.gnss.GnssNavigationMessageProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ListenerExecutor.ListenerOperation lambda$onReportNavigationMessage$1;
                lambda$onReportNavigationMessage$1 = GnssNavigationMessageProvider.this.lambda$onReportNavigationMessage$1(gnssNavigationMessage, (GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onReportNavigationMessage$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ListenerExecutor.ListenerOperation lambda$onReportNavigationMessage$1(final GnssNavigationMessage gnssNavigationMessage, GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        if (this.mAppOpsHelper.noteOpNoThrow(1, gnssListenerRegistration.getIdentity())) {
            return new ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssNavigationMessageProvider$$ExternalSyntheticLambda1
                public final void operate(Object obj) {
                    ((IGnssNavigationMessageListener) obj).onGnssNavigationMessageReceived(gnssNavigationMessage);
                }
            };
        }
        return null;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onRegistrationAdded(IBinder iBinder, GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        super.onRegistrationAdded((Object) iBinder, (ListenerRegistration) gnssListenerRegistration);
        addGnssDataListener(iBinder, gnssListenerRegistration);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onRegistrationRemoved(IBinder iBinder, GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        super.onRegistrationRemoved((Object) iBinder, (ListenerRegistration) gnssListenerRegistration);
        removeGnssDataListener(iBinder, gnssListenerRegistration);
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public LocationConstants.LISTENER_TYPE getListenerType() {
        return LocationConstants.LISTENER_TYPE.GNSS_NAVIGATION_MESSAGE;
    }
}
