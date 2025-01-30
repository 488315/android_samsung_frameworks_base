package com.android.keyguard;

import android.content.res.Configuration;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;
import java.lang.ref.WeakReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardMessageAreaController extends ViewController {
    public final AnnounceRunnable mAnnounceRunnable;
    public final ConfigurationController mConfigurationController;
    public final C07023 mConfigurationListener;
    public KeyguardUpdateMonitorCallback mInfoCallback;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final C07001 mTextWatcher;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Factory {
        public final ConfigurationController mConfigurationController;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;

        public Factory(KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController) {
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mConfigurationController = configurationController;
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.keyguard.KeyguardMessageAreaController$3] */
    public KeyguardMessageAreaController(KeyguardMessageArea keyguardMessageArea, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController) {
        super(keyguardMessageArea);
        this.mTextWatcher = new C07001();
        this.mInfoCallback = new KeyguardUpdateMonitorCallback(this) { // from class: com.android.keyguard.KeyguardMessageAreaController.2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onFinishedGoingToSleep(int i) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStartedWakingUp() {
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardMessageAreaController.3
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                ((KeyguardMessageArea) KeyguardMessageAreaController.this.mView).getClass();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                ((KeyguardMessageArea) KeyguardMessageAreaController.this.mView).getClass();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                ((KeyguardMessageArea) KeyguardMessageAreaController.this.mView).onThemeChanged();
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mConfigurationController = configurationController;
        this.mAnnounceRunnable = new AnnounceRunnable(this.mView);
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
            keyguardMessageArea.update();
        }
    }

    public void setMessage(CharSequence charSequence) {
        setMessage(charSequence, true);
    }

    public final void setMessage(CharSequence charSequence, boolean z) {
        View view = this.mView;
        if (((KeyguardMessageArea) view).mIsDisabled) {
            return;
        }
        ((KeyguardMessageArea) view).setMessage(charSequence, z);
    }

    public final void setMessage(int i) {
        setMessage(i != 0 ? ((KeyguardMessageArea) this.mView).getResources().getString(i) : null);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardMessageAreaController$1 */
    public final class C07001 implements TextWatcher {
        public C07001() {
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(final Editable editable) {
            if (TextUtils.isEmpty(editable)) {
                return;
            }
            KeyguardMessageAreaController keyguardMessageAreaController = KeyguardMessageAreaController.this;
            ((KeyguardMessageArea) keyguardMessageAreaController.mView).removeCallbacks(keyguardMessageAreaController.mAnnounceRunnable);
            KeyguardMessageAreaController keyguardMessageAreaController2 = KeyguardMessageAreaController.this;
            keyguardMessageAreaController2.mAnnounceRunnable.mTextToAnnounce = editable;
            ((KeyguardMessageArea) keyguardMessageAreaController2.mView).postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardMessageAreaController$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardMessageAreaController.C07001 c07001 = KeyguardMessageAreaController.C07001.this;
                    if (editable == ((KeyguardMessageArea) KeyguardMessageAreaController.this.mView).getText()) {
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
