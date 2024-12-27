package com.android.keyguard;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.hardware.biometrics.BiometricSourceType;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;
import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class KeyguardMessageAreaController extends ViewController {
    public final AnnounceRunnable mAnnounceRunnable;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass3 mConfigurationListener;
    public final KeyguardUpdateMonitorCallback mInfoCallback;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public Pair mMessageBiometricSource;
    public final AnonymousClass1 mTextWatcher;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class AnnounceRunnable implements Runnable {
        public final WeakReference mHost;
        public CharSequence mTextToAnnounce;

        public AnnounceRunnable(View view) {
            this.mHost = new WeakReference(view);
        }

        @Override // java.lang.Runnable
        public final void run() {
            View view = (View) this.mHost.get();
            if (view == null || !view.isVisibleToUser()) {
                return;
            }
            view.announceForAccessibility(this.mTextToAnnounce);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Factory {
        public final ConfigurationController mConfigurationController;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;

        public Factory(KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController) {
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mConfigurationController = configurationController;
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.keyguard.KeyguardMessageAreaController$3] */
    public KeyguardMessageAreaController(KeyguardMessageArea keyguardMessageArea, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController) {
        super(keyguardMessageArea);
        this.mMessageBiometricSource = null;
        this.mTextWatcher = new AnonymousClass1();
        this.mInfoCallback = new KeyguardUpdateMonitorCallback(this) { // from class: com.android.keyguard.KeyguardMessageAreaController.2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStartedWakingUp() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onFinishedGoingToSleep(int i) {
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardMessageAreaController.3
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
        this.mAnnounceRunnable = new AnnounceRunnable(this.mView);
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
        ((KeyguardMessageArea) this.mView).addTextChangedListener(this.mTextWatcher);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        this.mKeyguardUpdateMonitor.removeCallback(this.mInfoCallback);
        ((KeyguardMessageArea) this.mView).removeTextChangedListener(this.mTextWatcher);
    }

    public final void setIsVisible(boolean z) {
        KeyguardMessageArea keyguardMessageArea = (KeyguardMessageArea) this.mView;
        if (keyguardMessageArea.mIsVisible != z) {
            keyguardMessageArea.mIsVisible = z;
            keyguardMessageArea.update$6();
        }
    }

    public void setMessage(CharSequence charSequence) {
        setMessage$1(charSequence, true);
    }

    public final void setMessage$1(CharSequence charSequence, boolean z) {
        long uptimeMillis = SystemClock.uptimeMillis();
        Pair pair = this.mMessageBiometricSource;
        if (pair == null || BiometricSourceType.FACE != null || pair.first != BiometricSourceType.FINGERPRINT || uptimeMillis - ((Long) pair.second).longValue() >= 3500) {
            this.mMessageBiometricSource = new Pair(null, Long.valueOf(uptimeMillis));
            ((KeyguardMessageArea) this.mView).getClass();
            ((KeyguardMessageArea) this.mView).setMessage(charSequence, z);
        } else {
            Log.d("KeyguardMessageAreaController", "Skip showing face message \"" + ((Object) charSequence) + "\"");
        }
    }

    public final void setNextMessageColor(ColorStateList colorStateList) {
        ((KeyguardMessageArea) this.mView).getClass();
    }

    public final void setMessage(int i) {
        setMessage(i != 0 ? ((KeyguardMessageArea) this.mView).getResources().getString(i) : null);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.keyguard.KeyguardMessageAreaController$1, reason: invalid class name */
    public final class AnonymousClass1 implements TextWatcher {
        public AnonymousClass1() {
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(final Editable editable) {
            if (TextUtils.isEmpty(editable)) {
                return;
            }
            ((KeyguardMessageArea) ((ViewController) KeyguardMessageAreaController.this).mView).removeCallbacks(KeyguardMessageAreaController.this.mAnnounceRunnable);
            KeyguardMessageAreaController keyguardMessageAreaController = KeyguardMessageAreaController.this;
            keyguardMessageAreaController.mAnnounceRunnable.mTextToAnnounce = editable;
            ((KeyguardMessageArea) ((ViewController) keyguardMessageAreaController).mView).postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardMessageAreaController$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    View view;
                    KeyguardMessageAreaController.AnonymousClass1 anonymousClass1 = KeyguardMessageAreaController.AnonymousClass1.this;
                    CharSequence charSequence = editable;
                    view = ((ViewController) KeyguardMessageAreaController.this).mView;
                    if (charSequence == ((KeyguardMessageArea) view).getText()) {
                        KeyguardMessageAreaController.this.mAnnounceRunnable.run();
                    }
                }
            }, 250L);
        }

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }
}
