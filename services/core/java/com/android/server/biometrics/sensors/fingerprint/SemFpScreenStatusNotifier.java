package com.android.server.biometrics.sensors.fingerprint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.PowerManager;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.biometrics.SemBioFgThread;
import com.android.server.biometrics.Utils;

/* loaded from: classes.dex */
public class SemFpScreenStatusNotifier implements SemFpAuthenticationListener {
    static final int SCREEN_OFF = 1;
    static final int SCREEN_ON = 2;
    public final Context mContext;
    public final Injector mInjector;
    BroadcastReceiver mScreenOnOffReceiver;
    public int mScreenStatus;
    public final ServiceProvider mServiceProvider;

    public class Injector {
        public boolean isInteractive(Context context) {
            return ((PowerManager) context.getSystemService(PowerManager.class)).isInteractive();
        }
    }

    public SemFpScreenStatusNotifier(Context context, ServiceProvider serviceProvider) {
        this(context, serviceProvider, new Injector());
    }

    public SemFpScreenStatusNotifier(Context context, ServiceProvider serviceProvider, Injector injector) {
        this.mContext = context;
        this.mServiceProvider = serviceProvider;
        this.mInjector = injector;
        this.mScreenStatus = 0;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener
    public void onAuthenticationStarted(int i, int i2) {
        registerBroadcastReceiver();
        notifyScreenStatus();
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener
    public void onAuthenticationFinished(int i, int i2) {
        BroadcastReceiver broadcastReceiver = this.mScreenOnOffReceiver;
        if (broadcastReceiver == null) {
            return;
        }
        Utils.unregisterBroadcast(this.mContext, broadcastReceiver);
        this.mScreenOnOffReceiver = null;
    }

    public void start() {
        ServiceProvider serviceProvider = this.mServiceProvider;
        int semRequest = serviceProvider.semRequest(((FingerprintSensorPropertiesInternal) serviceProvider.getSensorProperties().get(0)).sensorId, 37, 0, null, null);
        Slog.i("SemFpScreenStatusNotifier", "TZ BUILD TYPE = " + semRequest);
        if (semRequest != 1) {
            return;
        }
        this.mServiceProvider.semAddAuthenticationListener(this);
    }

    public final void registerBroadcastReceiver() {
        if (this.mScreenOnOffReceiver != null) {
            return;
        }
        this.mScreenOnOffReceiver = new BroadcastReceiver() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpScreenStatusNotifier.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                SemFpScreenStatusNotifier.this.notifyScreenStatus();
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        Utils.registerBroadcastAsUser(this.mContext, this.mScreenOnOffReceiver, intentFilter, UserHandle.CURRENT, SemBioFgThread.get().getHandler());
    }

    public final void notifyScreenStatus() {
        int i = this.mInjector.isInteractive(this.mContext) ? 2 : 1;
        Slog.i("SemFpScreenStatusNotifier", "notifyScreenStatus: " + this.mScreenStatus + ", " + i);
        if (this.mScreenStatus == i) {
            return;
        }
        this.mScreenStatus = i;
        ServiceProvider serviceProvider = this.mServiceProvider;
        serviceProvider.semRequest(((FingerprintSensorPropertiesInternal) serviceProvider.getSensorProperties().get(0)).sensorId, 36, this.mScreenStatus, null, null);
    }
}
