package com.android.keyguard;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.hardware.biometrics.BiometricSourceType;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;

/* loaded from: classes.dex */
public class KeyguardMessageAreaController extends ViewController {
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass2 mConfigurationListener;
    public KeyguardUpdateMonitorCallback mInfoCallback;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public Pair mMessageBiometricSource;

    public class Factory {
        public final ConfigurationController mConfigurationController;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;

        public Factory(KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController) {
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mConfigurationController = configurationController;
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.keyguard.KeyguardMessageAreaController$2] */
    public KeyguardMessageAreaController(KeyguardMessageArea keyguardMessageArea, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController) {
        super(keyguardMessageArea);
        this.mMessageBiometricSource = null;
        this.mInfoCallback = new KeyguardUpdateMonitorCallback(this) { // from class: com.android.keyguard.KeyguardMessageAreaController.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStartedWakingUp() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onFinishedGoingToSleep(int i) {
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardMessageAreaController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                ((KeyguardMessageArea) ((ViewController) KeyguardMessageAreaController.this).mView).getClass();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                ((KeyguardMessageArea) ((ViewController) KeyguardMessageAreaController.this).mView).getClass();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                ((KeyguardMessageArea) ((ViewController) KeyguardMessageAreaController.this).mView).onThemeChanged();
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mConfigurationController = configurationController;
    }

    public final CharSequence getMessage() {
        return ((KeyguardMessageArea) this.mView).getText();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mInfoCallback;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
        ((KeyguardMessageArea) this.mView).setSelected(keyguardUpdateMonitor.mDeviceInteractive);
        ((KeyguardMessageArea) this.mView).onThemeChanged();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        this.mKeyguardUpdateMonitor.removeCallback(this.mInfoCallback);
    }

    public final void setIsVisible(boolean z) {
        KeyguardMessageArea keyguardMessageArea = (KeyguardMessageArea) this.mView;
        if (keyguardMessageArea.mIsVisible != z) {
            keyguardMessageArea.mIsVisible = z;
            keyguardMessageArea.update$1$1();
        }
    }

    public void setMessage(CharSequence charSequence) {
        setMessage(charSequence, true);
    }

    public final void setNextMessageColor(ColorStateList colorStateList) {
        ((KeyguardMessageArea) this.mView).getClass();
    }

    public final void setMessage(CharSequence charSequence, boolean z) {
        long jUptimeMillis = SystemClock.uptimeMillis();
        Pair pair = this.mMessageBiometricSource;
        if (pair == null || BiometricSourceType.FACE != null || pair.first != BiometricSourceType.FINGERPRINT || jUptimeMillis - ((Long) pair.second).longValue() >= 3500) {
            this.mMessageBiometricSource = new Pair(null, Long.valueOf(jUptimeMillis));
            ((KeyguardMessageArea) this.mView).getClass();
            ((KeyguardMessageArea) this.mView).setMessage(charSequence, z);
        } else {
            Log.d("KeyguardMessageAreaController", "Skip showing face message \"" + ((Object) charSequence) + "\"");
        }
    }

    public final void setMessage(int i) {
        setMessage(i != 0 ? ((KeyguardMessageArea) this.mView).getResources().getString(i) : null);
    }
}
