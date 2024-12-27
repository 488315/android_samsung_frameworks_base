package com.android.server.location.gnss;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.location.IGnssNmeaListener;
import android.location.LocationConstants;
import android.util.Log;

import com.android.internal.listeners.ListenerExecutor;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.location.LocationManagerService;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.location.injector.Injector;
import com.android.server.location.injector.SystemAppOpsHelper;

import java.util.Collection;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GnssNmeaProvider extends GnssListenerMultiplexer
        implements GnssNative.BaseCallbacks, GnssNative.NmeaCallbacks {
    public final SystemAppOpsHelper mAppOpsHelper;
    public final GnssNative mGnssNative;
    public int mNmeaAllowed;
    public final byte[] mNmeaBuffer;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.location.gnss.GnssNmeaProvider$1, reason: invalid class name */
    public final class AnonymousClass1 implements Function {
        public String mNmea;
        public final /* synthetic */ long val$timestamp;

        public AnonymousClass1(long j) {
            this.val$timestamp = j;
        }

        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            if (!GnssNmeaProvider.this.mAppOpsHelper.noteOpNoThrow(
                    1, ((GnssListenerMultiplexer.GnssListenerRegistration) obj).mIdentity)) {
                return null;
            }
            if (this.mNmea == null) {
                GnssNmeaProvider gnssNmeaProvider = GnssNmeaProvider.this;
                GnssNative gnssNative = gnssNmeaProvider.mGnssNative;
                byte[] bArr = gnssNmeaProvider.mNmeaBuffer;
                this.mNmea =
                        new String(
                                GnssNmeaProvider.this.mNmeaBuffer,
                                0,
                                gnssNative.readNmea(bArr, bArr.length));
            }
            final long j = this.val$timestamp;
            return new ListenerExecutor
                    .ListenerOperation() { // from class:
                                           // com.android.server.location.gnss.GnssNmeaProvider$1$$ExternalSyntheticLambda0
                public final void operate(Object obj2) {
                    IGnssNmeaListener iGnssNmeaListener = (IGnssNmeaListener) obj2;
                    iGnssNmeaListener.onNmeaReceived(
                            j, GnssNmeaProvider.AnonymousClass1.this.mNmea);
                }
            };
        }
    }

    public GnssNmeaProvider(Injector injector, GnssNative gnssNative) {
        super(injector);
        this.mNmeaBuffer = new byte[120];
        this.mAppOpsHelper = ((LocationManagerService.SystemInjector) injector).mAppOpsHelper;
        this.mGnssNative = gnssNative;
        gnssNative.addBaseCallbacks(this);
        gnssNative.addNmeaCallbacks(this);
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public final LocationConstants.LISTENER_TYPE getListenerType() {
        return LocationConstants.LISTENER_TYPE.NMEA;
    }

    public final void onGnssNmeaListenerStatusChanged(int i) {
        DirEncryptService$$ExternalSyntheticOutline0.m(
                i, "onGnssNmeaListenerStatusChanged: Injecting NMEA_ALLOWED=", "GnssNmeaProvider");
        this.mNmeaAllowed = i;
        this.mGnssNative.gnssConfigurationUpdateSec(
                VibrationParam$1$$ExternalSyntheticOutline0.m(i, "NMEA_ALLOWED="));
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer,
              // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public final void onHalRestarted() {
        resetService();
        Log.i("GnssNmeaProvider", "onHalRestarted()");
        this.mGnssNative.gnssConfigurationUpdateSec("NMEA_ALLOWED=" + this.mNmeaAllowed);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final boolean registerWithService(Collection collection, Object obj) {
        if (this.mGnssNative.startNmeaMessageCollection()) {
            Log.d("GnssNmeaProvider", "starting gnss nmea messages collection");
            return true;
        }
        Log.e("GnssNmeaProvider", "error starting gnss nmea messages collection");
        return false;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void unregisterWithService() {
        if (this.mGnssNative.stopNmeaMessageCollection()) {
            Log.d("GnssNmeaProvider", "stopping gnss nmea messages collection");
        } else {
            Log.e("GnssNmeaProvider", "error stopping gnss nmea messages collection");
        }
    }
}
