package com.android.systemui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Build;
import android.provider.DeviceConfig;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.PrintWriter;

public final class LatencyTester implements CoreStartable {
    public static final boolean DEFAULT_ENABLED = Build.IS_ENG;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final AnonymousClass1 mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.LatencyTester.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.android.systemui.latency.ACTION_FINGERPRINT_WAKE".equals(action)) {
                LatencyTester.m877$$Nest$mfakeWakeAndUnlock(LatencyTester.this, BiometricSourceType.FINGERPRINT);
            } else if ("com.android.systemui.latency.ACTION_FACE_WAKE".equals(action)) {
                LatencyTester.m877$$Nest$mfakeWakeAndUnlock(LatencyTester.this, BiometricSourceType.FACE);
            }
        }
    };
    public final DeviceConfigProxy mDeviceConfigProxy;
    public boolean mEnabled;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final SelectedUserInteractor mSelectedUserInteractor;

    /* renamed from: -$$Nest$mfakeWakeAndUnlock, reason: not valid java name */
    public static void m877$$Nest$mfakeWakeAndUnlock(LatencyTester latencyTester, BiometricSourceType biometricSourceType) {
        if (latencyTester.mEnabled) {
            BiometricSourceType biometricSourceType2 = BiometricSourceType.FACE;
            SelectedUserInteractor selectedUserInteractor = latencyTester.mSelectedUserInteractor;
            KeyguardUpdateMonitor keyguardUpdateMonitor = latencyTester.mKeyguardUpdateMonitor;
            if (biometricSourceType == biometricSourceType2) {
                keyguardUpdateMonitor.onFaceAuthenticated(selectedUserInteractor.getSelectedUserId(), true);
            } else if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                keyguardUpdateMonitor.onFingerprintAuthenticated(selectedUserInteractor.getSelectedUserId(), true);
            }
        }
    }

    public LatencyTester(BroadcastDispatcher broadcastDispatcher, DeviceConfigProxy deviceConfigProxy, DelayableExecutor delayableExecutor, KeyguardUpdateMonitor keyguardUpdateMonitor, SelectedUserInteractor selectedUserInteractor) {
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mDeviceConfigProxy = deviceConfigProxy;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mSelectedUserInteractor = selectedUserInteractor;
        updateEnabled();
        deviceConfigProxy.addOnPropertiesChangedListener("latency_tracker", delayableExecutor, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.LatencyTester$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                LatencyTester.this.updateEnabled();
            }
        });
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("mEnabled="), this.mEnabled, printWriter);
    }

    public final void updateEnabled() {
        boolean z = this.mEnabled;
        boolean z2 = Build.IS_DEBUGGABLE && this.mDeviceConfigProxy.getBoolean("latency_tracker", "enabled", DEFAULT_ENABLED);
        this.mEnabled = z2;
        if (z2 != z) {
            BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
            if (z2) {
                broadcastDispatcher.registerReceiver(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("com.android.systemui.latency.ACTION_FINGERPRINT_WAKE", "com.android.systemui.latency.ACTION_FACE_WAKE"), this.mBroadcastReceiver);
            } else {
                broadcastDispatcher.unregisterReceiver(this.mBroadcastReceiver);
            }
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
