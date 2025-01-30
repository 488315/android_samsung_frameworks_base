package com.android.server.biometrics.sensors.fingerprint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.BatteryManagerInternal;
import android.os.Handler;
import android.os.UserHandle;
import android.widget.Toast;
import com.android.server.LocalServices;
import com.android.server.biometrics.Utils;

/* loaded from: classes.dex */
public class SemFpWirelessPowerMonitor implements SemFpEnrollmentListener, SemFpAuthenticationListener {
    static final String ACTION_WIRELESS_POWER_SHARING = "com.samsung.android.sm.ACTION_WIRELESS_POWER_SHARING";
    static final int AUTH_REJECT_COUNT_THRESHOLDS = 3;
    static final String KEY_WIRELESS_POWER_SHARING_ENABLED = "enable";
    public int mAuthRejectCountWhileWirelessPower;
    BroadcastReceiver mBrForWirelessPower;
    public final Context mContext;

    /* renamed from: mH */
    public final Handler f1660mH;
    public final Injector mInjector;
    public boolean mIsWirelessPowerRunning;
    public boolean mIsWirelessPowerSharingRunning;
    public final ServiceProvider mServiceProvider;

    public class Injector {
        public void showWirelessChargerErrorToastMessage(Context context) {
            Toast.makeText(context, 17042715, 0).show();
        }
    }

    public SemFpWirelessPowerMonitor(Context context, ServiceProvider serviceProvider) {
        this(context, serviceProvider, new Injector());
    }

    public SemFpWirelessPowerMonitor(Context context, ServiceProvider serviceProvider, Injector injector) {
        this.mContext = context;
        this.f1660mH = SemFpMainThread.get().getHandler();
        this.mServiceProvider = serviceProvider;
        this.mInjector = injector;
    }

    public void start() {
        registerBroadcast();
        this.mServiceProvider.semAddAuthenticationListener(this);
        this.mServiceProvider.semAddEnrollmentListener(this);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener
    public void onAuthenticationResult(int i, int i2, int i3) {
        if (i3 == 0 && this.mIsWirelessPowerRunning) {
            int i4 = this.mAuthRejectCountWhileWirelessPower + 1;
            this.mAuthRejectCountWhileWirelessPower = i4;
            if (i4 == 3) {
                this.mInjector.showWirelessChargerErrorToastMessage(this.mContext);
                this.mAuthRejectCountWhileWirelessPower = 0;
                return;
            }
            return;
        }
        this.mAuthRejectCountWhileWirelessPower = 0;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpEnrollmentListener
    public void onEnrollStarted(int i, int i2) {
        if (this.mIsWirelessPowerRunning) {
            this.mServiceProvider.onWirelessPowerEnabled();
        }
    }

    public final void registerBroadcast() {
        if (this.mBrForWirelessPower != null) {
            return;
        }
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpWirelessPowerMonitor.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if ("android.intent.action.BATTERY_CHANGED".contentEquals(action)) {
                    SemFpWirelessPowerMonitor.this.handleActionOfBatteryChanged();
                } else if (SemFpWirelessPowerMonitor.ACTION_WIRELESS_POWER_SHARING.contentEquals(action)) {
                    SemFpWirelessPowerMonitor.this.handleActionOfWirelessPowerSharing(intent);
                }
            }
        };
        this.mBrForWirelessPower = broadcastReceiver;
        Utils.registerBroadcastAsUser(this.mContext, broadcastReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"), UserHandle.ALL, this.f1660mH);
        Utils.registerBroadcastAsUser(this.mContext, this.mBrForWirelessPower, new IntentFilter(ACTION_WIRELESS_POWER_SHARING), UserHandle.ALL, "com.samsung.android.permission.wirelesspowersharing", this.f1660mH);
    }

    public final void handleActionOfBatteryChanged() {
        if (((BatteryManagerInternal) LocalServices.getService(BatteryManagerInternal.class)).getPlugType() == 4) {
            dispatchWirelessPowerStatus(true);
        } else {
            if (this.mIsWirelessPowerSharingRunning) {
                return;
            }
            dispatchWirelessPowerStatus(false);
        }
    }

    public final void handleActionOfWirelessPowerSharing(Intent intent) {
        boolean booleanExtra = intent.getBooleanExtra(KEY_WIRELESS_POWER_SHARING_ENABLED, false);
        this.mIsWirelessPowerSharingRunning = booleanExtra;
        dispatchWirelessPowerStatus(booleanExtra);
    }

    public final void dispatchWirelessPowerStatus(boolean z) {
        if (this.mIsWirelessPowerRunning == z) {
            return;
        }
        this.mIsWirelessPowerRunning = z;
        if (z) {
            this.mServiceProvider.onWirelessPowerEnabled();
        } else {
            this.mAuthRejectCountWhileWirelessPower = 0;
        }
        ServiceProvider serviceProvider = this.mServiceProvider;
        serviceProvider.semRequest(((FingerprintSensorPropertiesInternal) serviceProvider.getSensorProperties().get(0)).sensorId, 29, this.mIsWirelessPowerRunning ? 1 : 0, null, null);
    }

    public void setWirelessPowerStatusForTesting(boolean z) {
        this.mIsWirelessPowerRunning = z;
    }

    public void setWirelessPowerSharingStatusForTesting(boolean z) {
        this.mIsWirelessPowerSharingRunning = z;
    }

    public int getAuthRejectCountWhileWirelessPowerForTesting() {
        return this.mAuthRejectCountWhileWirelessPower;
    }
}
