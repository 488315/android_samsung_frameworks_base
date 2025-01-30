package com.android.server.location.gnss;

import android.location.IGnssNmeaListener;
import android.location.LocationConstants;
import android.location.util.identity.CallerIdentity;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;
import com.android.internal.listeners.ListenerExecutor;
import com.android.server.location.gnss.GnssListenerMultiplexer;
import com.android.server.location.gnss.GnssNmeaProvider;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.location.injector.AppOpsHelper;
import com.android.server.location.injector.Injector;
import com.android.server.location.listeners.ListenerRegistration;
import java.util.Collection;
import java.util.function.Function;

/* loaded from: classes2.dex */
public class GnssNmeaProvider extends GnssListenerMultiplexer implements GnssNative.BaseCallbacks, GnssNative.NmeaCallbacks {
    public final AppOpsHelper mAppOpsHelper;
    public final GnssNative mGnssNative;
    public int mNmeaAllowed;
    public final byte[] mNmeaBuffer;

    public GnssNmeaProvider(Injector injector, GnssNative gnssNative) {
        super(injector);
        this.mNmeaBuffer = new byte[120];
        this.mAppOpsHelper = injector.getAppOpsHelper();
        this.mGnssNative = gnssNative;
        gnssNative.addBaseCallbacks(this);
        gnssNative.addNmeaCallbacks(this);
    }

    public void onGnssNmeaListenerStatusChanged(int i) {
        Log.i("GnssNmeaProvider", "onGnssNmeaListenerStatusChanged: Injecting NMEA_ALLOWED=" + i);
        this.mNmeaAllowed = i;
        this.mGnssNative.gnssConfigurationUpdateSec("NMEA_ALLOWED=" + i);
    }

    public void addListener(CallerIdentity callerIdentity, IGnssNmeaListener iGnssNmeaListener) {
        super.addListener(callerIdentity, (IInterface) iGnssNmeaListener);
        Log.i("GnssNmeaProvider", "addListener: adding NMEA listener(" + Integer.toHexString(System.identityHashCode(iGnssNmeaListener)) + ") from " + callerIdentity.getPackageName());
        if (this.mNmeaAllowed == 0) {
            this.mNmeaAllowed = 1;
            onGnssNmeaListenerStatusChanged(1);
        }
    }

    public void removeListener(IGnssNmeaListener iGnssNmeaListener) {
        super.removeListener((IInterface) iGnssNmeaListener);
        Log.i("GnssNmeaProvider", "removeListener: removing NMEA listener(" + Integer.toHexString(System.identityHashCode(iGnssNmeaListener)) + ")");
        if (isRegistrationEmpty() && this.mNmeaAllowed == 1) {
            this.mNmeaAllowed = 0;
            onGnssNmeaListenerStatusChanged(0);
        }
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public boolean registerWithService(Void r1, Collection collection) {
        if (this.mGnssNative.startNmeaMessageCollection()) {
            Log.d("GnssNmeaProvider", "starting gnss nmea messages collection");
            return true;
        }
        Log.e("GnssNmeaProvider", "error starting gnss nmea messages collection");
        return false;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void unregisterWithService() {
        if (this.mGnssNative.stopNmeaMessageCollection()) {
            Log.d("GnssNmeaProvider", "stopping gnss nmea messages collection");
        } else {
            Log.e("GnssNmeaProvider", "error stopping gnss nmea messages collection");
        }
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public void onHalRestarted() {
        resetService();
        Log.i("GnssNmeaProvider", "onHalRestarted()");
        this.mGnssNative.gnssConfigurationUpdateSec("NMEA_ALLOWED=" + this.mNmeaAllowed);
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.NmeaCallbacks
    public void onReportNmea(long j) {
        deliverToListeners(new C18121(j));
    }

    /* renamed from: com.android.server.location.gnss.GnssNmeaProvider$1 */
    public class C18121 implements Function {
        public String mNmea;
        public final /* synthetic */ long val$timestamp;

        public C18121(long j) {
            this.val$timestamp = j;
        }

        @Override // java.util.function.Function
        public ListenerExecutor.ListenerOperation apply(GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
            if (!GnssNmeaProvider.this.mAppOpsHelper.noteOpNoThrow(1, gnssListenerRegistration.getIdentity())) {
                return null;
            }
            if (this.mNmea == null) {
                this.mNmea = new String(GnssNmeaProvider.this.mNmeaBuffer, 0, GnssNmeaProvider.this.mGnssNative.readNmea(GnssNmeaProvider.this.mNmeaBuffer, GnssNmeaProvider.this.mNmeaBuffer.length));
            }
            final long j = this.val$timestamp;
            return new ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssNmeaProvider$1$$ExternalSyntheticLambda0
                public final void operate(Object obj) {
                    GnssNmeaProvider.C18121.this.lambda$apply$0(j, (IGnssNmeaListener) obj);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$apply$0(long j, IGnssNmeaListener iGnssNmeaListener) {
            iGnssNmeaListener.onNmeaReceived(j, this.mNmea);
        }
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
        return LocationConstants.LISTENER_TYPE.NMEA;
    }
}
