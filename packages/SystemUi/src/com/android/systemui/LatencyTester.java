package com.android.systemui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Build;
import android.provider.DeviceConfig;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LatencyTester implements CoreStartable {
    public static final boolean DEFAULT_ENABLED = Build.IS_ENG;
    public final BiometricUnlockController mBiometricUnlockController;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final C09691 mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.LatencyTester.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.android.systemui.latency.ACTION_FINGERPRINT_WAKE".equals(action)) {
                LatencyTester.m370$$Nest$mfakeWakeAndUnlock(LatencyTester.this, BiometricSourceType.FINGERPRINT);
            } else if ("com.android.systemui.latency.ACTION_FACE_WAKE".equals(action)) {
                LatencyTester.m370$$Nest$mfakeWakeAndUnlock(LatencyTester.this, BiometricSourceType.FACE);
            }
        }
    };
    public final DeviceConfigProxy mDeviceConfigProxy;
    public boolean mEnabled;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;

    /* renamed from: -$$Nest$mfakeWakeAndUnlock, reason: not valid java name */
    public static void m370$$Nest$mfakeWakeAndUnlock(LatencyTester latencyTester, BiometricSourceType biometricSourceType) {
        if (latencyTester.mEnabled) {
            BiometricSourceType biometricSourceType2 = BiometricSourceType.FACE;
            KeyguardUpdateMonitor keyguardUpdateMonitor = latencyTester.mKeyguardUpdateMonitor;
            if (biometricSourceType == biometricSourceType2) {
                keyguardUpdateMonitor.onFaceAuthenticated(KeyguardUpdateMonitor.getCurrentUser(), true);
            } else if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                keyguardUpdateMonitor.onFingerprintAuthenticated(KeyguardUpdateMonitor.getCurrentUser(), true);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.LatencyTester$1] */
    public LatencyTester(BiometricUnlockController biometricUnlockController, BroadcastDispatcher broadcastDispatcher, DeviceConfigProxy deviceConfigProxy, DelayableExecutor delayableExecutor, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mBiometricUnlockController = biometricUnlockController;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mDeviceConfigProxy = deviceConfigProxy;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        updateEnabled();
        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.LatencyTester$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                LatencyTester.this.updateEnabled();
            }
        };
        deviceConfigProxy.getClass();
        DeviceConfig.addOnPropertiesChangedListener("latency_tracker", delayableExecutor, onPropertiesChangedListener);
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(new StringBuilder("mEnabled="), this.mEnabled, printWriter);
    }

    public final void registerForBroadcasts(boolean z) {
        C09691 c09691 = this.mBroadcastReceiver;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        if (!z) {
            broadcastDispatcher.unregisterReceiver(c09691);
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.systemui.latency.ACTION_FINGERPRINT_WAKE");
        intentFilter.addAction("com.android.systemui.latency.ACTION_FACE_WAKE");
        broadcastDispatcher.registerReceiver(intentFilter, c09691);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        registerForBroadcasts(this.mEnabled);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateEnabled() {
        boolean z;
        boolean z2 = this.mEnabled;
        if (Build.IS_DEBUGGABLE) {
            this.mDeviceConfigProxy.getClass();
            if (DeviceConfig.getBoolean("latency_tracker", "enabled", DEFAULT_ENABLED)) {
                z = true;
                this.mEnabled = z;
                if (z == z2) {
                    registerForBroadcasts(z);
                    return;
                }
                return;
            }
        }
        z = false;
        this.mEnabled = z;
        if (z == z2) {
        }
    }
}
