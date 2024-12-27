package com.android.server.biometrics.sensors.fingerprint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.PowerManager;
import android.os.UserHandle;
import android.util.Slog;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemFpScreenStatusNotifier implements SemFpAuthenticationListener {
    static final int SCREEN_OFF = 1;
    static final int SCREEN_ON = 2;
    public final Context mContext;
    public final Injector mInjector;
    BroadcastReceiver mScreenOnOffReceiver;
    public int mScreenStatus = 0;
    public final ServiceProvider mServiceProvider;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Injector {}

    public SemFpScreenStatusNotifier(
            Context context, ServiceProvider serviceProvider, Injector injector) {
        this.mContext = context;
        this.mServiceProvider = serviceProvider;
        this.mInjector = injector;
    }

    public final void notifyScreenStatus() {
        Context context = this.mContext;
        this.mInjector.getClass();
        int i =
                ((PowerManager) context.getSystemService(PowerManager.class)).isInteractive()
                        ? 2
                        : 1;
        Slog.i("SemFpScreenStatusNotifier", "notifyScreenStatus: " + this.mScreenStatus + ", " + i);
        if (this.mScreenStatus == i) {
            return;
        }
        this.mScreenStatus = i;
        ServiceProvider serviceProvider = this.mServiceProvider;
        ((FingerprintProvider) serviceProvider)
                .semRequest(
                        ((FingerprintSensorPropertiesInternal)
                                        ((ArrayList)
                                                        ((FingerprintProvider) serviceProvider)
                                                                .getSensorProperties())
                                                .get(0))
                                .sensorId,
                        36,
                        this.mScreenStatus,
                        null,
                        null);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener
    public final void onAuthenticationFinished(int i, int i2) {
        BroadcastReceiver broadcastReceiver = this.mScreenOnOffReceiver;
        if (broadcastReceiver == null) {
            return;
        }
        Utils.unregisterBroadcast(this.mContext, broadcastReceiver);
        this.mScreenOnOffReceiver = null;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener
    public final void onAuthenticationStarted(int i, int i2) {
        if (this.mScreenOnOffReceiver == null) {
            this.mScreenOnOffReceiver =
                    new BroadcastReceiver() { // from class:
                                              // com.android.server.biometrics.sensors.fingerprint.SemFpScreenStatusNotifier.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            SemFpScreenStatusNotifier.this.notifyScreenStatus();
                        }
                    };
            Utils.registerBroadcastAsUser(
                    this.mContext,
                    this.mScreenOnOffReceiver,
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                            "android.intent.action.SCREEN_ON", "android.intent.action.SCREEN_OFF"),
                    UserHandle.CURRENT,
                    BiometricHandlerProvider.sBiometricHandlerProvider
                            .getBiometricCallbackHandler());
        }
        notifyScreenStatus();
    }
}
