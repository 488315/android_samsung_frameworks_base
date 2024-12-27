package com.android.keyguard;

import android.app.ActivityTaskManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.CscRune;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class EmergencyButtonController extends ViewController {
    public final ActivityTaskManager mActivityTaskManager;
    public final Executor mBackgroundExecutor;
    public boolean mBouncerShowing;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass2 mConfigurationListener;
    public int mCurrentSimState;
    public EmergencyButtonCallback mEmergencyButtonCallback;
    public final InputMethodManager mImm;
    public final KeyguardUpdateMonitorCallback mInfoCallback;
    public boolean mKeyguardShowing;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final Executor mMainExecutor;
    public final MetricsLogger mMetricsLogger;
    public View mPasswordEntry;
    public final PowerManager mPowerManager;
    public final SelectedUserInteractor mSelectedUserInteractor;
    private final SettingsHelper.OnChangedCallback mSettingsListener;
    public final Uri[] mSettingsValueList;
    public final ShadeController mShadeController;
    public final TelecomManager mTelecomManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface EmergencyButtonCallback {
        void onEmergencyButtonClickedWhenInCall();
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Factory {
        public final ActivityTaskManager mActivityTaskManager;
        public final Executor mBackgroundExecutor;
        public final ConfigurationController mConfigurationController;
        public final InputMethodManager mInputMethodManager;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final LockPatternUtils mLockPatternUtils;
        public final Executor mMainExecutor;
        public final MetricsLogger mMetricsLogger;
        public final PowerManager mPowerManager;
        public final SelectedUserInteractor mSelectedUserInteractor;
        public final ShadeController mShadeController;
        public final TelecomManager mTelecomManager;

        public Factory(ConfigurationController configurationController, InputMethodManager inputMethodManager, KeyguardUpdateMonitor keyguardUpdateMonitor, PowerManager powerManager, ActivityTaskManager activityTaskManager, ShadeController shadeController, TelecomManager telecomManager, MetricsLogger metricsLogger, LockPatternUtils lockPatternUtils, Executor executor, Executor executor2, SelectedUserInteractor selectedUserInteractor) {
            this.mConfigurationController = configurationController;
            this.mInputMethodManager = inputMethodManager;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mPowerManager = powerManager;
            this.mActivityTaskManager = activityTaskManager;
            this.mShadeController = shadeController;
            this.mTelecomManager = telecomManager;
            this.mMetricsLogger = metricsLogger;
            this.mLockPatternUtils = lockPatternUtils;
            this.mMainExecutor = executor;
            this.mBackgroundExecutor = executor2;
            this.mSelectedUserInteractor = selectedUserInteractor;
        }

        public final EmergencyButtonController create(EmergencyButton emergencyButton) {
            return new EmergencyButtonController(emergencyButton, this.mConfigurationController, this.mInputMethodManager, this.mKeyguardUpdateMonitor, this.mPowerManager, this.mActivityTaskManager, this.mShadeController, this.mTelecomManager, this.mMetricsLogger, this.mLockPatternUtils, this.mMainExecutor, this.mBackgroundExecutor, this.mSelectedUserInteractor);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x008a, code lost:
    
        r6 = "";
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void $r8$lambda$05evS2cO47UZK0SrphgxT6aEUMU(com.android.keyguard.EmergencyButtonController r7, boolean r8) {
        /*
            Method dump skipped, instructions count: 340
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.EmergencyButtonController.$r8$lambda$05evS2cO47UZK0SrphgxT6aEUMU(com.android.keyguard.EmergencyButtonController, boolean):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x00de, code lost:
    
        if (r0.getVisibility() == 0) goto L46;
     */
    /* renamed from: $r8$lambda$x-Q0QlP59a82QvETJ9i9UaMk1vM, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m829$r8$lambda$xQ0QlP59a82QvETJ9i9UaMk1vM(com.android.keyguard.EmergencyButtonController r9, boolean r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 246
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.EmergencyButtonController.m829$r8$lambda$xQ0QlP59a82QvETJ9i9UaMk1vM(com.android.keyguard.EmergencyButtonController, boolean, boolean):void");
    }

    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.keyguard.EmergencyButtonController$2] */
    public EmergencyButtonController(EmergencyButton emergencyButton, ConfigurationController configurationController, InputMethodManager inputMethodManager, KeyguardUpdateMonitor keyguardUpdateMonitor, PowerManager powerManager, ActivityTaskManager activityTaskManager, ShadeController shadeController, TelecomManager telecomManager, MetricsLogger metricsLogger, LockPatternUtils lockPatternUtils, Executor executor, Executor executor2, SelectedUserInteractor selectedUserInteractor) {
        super(emergencyButton);
        this.mKeyguardShowing = true;
        this.mBouncerShowing = false;
        this.mCurrentSimState = 1;
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.keyguard.EmergencyButtonController$$ExternalSyntheticLambda2
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }
        };
        this.mSettingsValueList = new Uri[]{Settings.System.getUriFor(SettingsHelper.INDEX_AIRPLANE_MODE_ON)};
        this.mPasswordEntry = null;
        this.mInfoCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.EmergencyButtonController.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
                EmergencyButtonController emergencyButtonController = EmergencyButtonController.this;
                emergencyButtonController.mBouncerShowing = z;
                emergencyButtonController.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) {
                EmergencyButtonController emergencyButtonController = EmergencyButtonController.this;
                emergencyButtonController.mKeyguardShowing = z;
                emergencyButtonController.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onOfflineStateChanged() {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onPhoneStateChanged(int i) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onRefreshCarrierInfo(Intent intent) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimStateChanged(int i, int i2, int i3) {
                EmergencyButtonController emergencyButtonController = EmergencyButtonController.this;
                emergencyButtonController.getClass();
                emergencyButtonController.mCurrentSimState = i3;
                emergencyButtonController.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitchComplete(int i) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.EmergencyButtonController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }
        };
        this.mConfigurationController = configurationController;
        this.mImm = inputMethodManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mPowerManager = powerManager;
        this.mActivityTaskManager = activityTaskManager;
        this.mShadeController = shadeController;
        this.mTelecomManager = telecomManager;
        this.mMetricsLogger = metricsLogger;
        this.mMainExecutor = executor;
        this.mBackgroundExecutor = executor2;
        this.mSelectedUserInteractor = selectedUserInteractor;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        DejankUtils.whitelistIpcs(new EmergencyButtonController$$ExternalSyntheticLambda1(this, 0));
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mKeyguardUpdateMonitor.registerCallback(this.mInfoCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        setEmergencyView(this.mView);
        if (CscRune.SECURITY_EMERGENCY_BUTTON_KOR) {
            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.mSettingsListener, this.mSettingsValueList);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mInfoCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        if (CscRune.SECURITY_EMERGENCY_BUTTON_KOR) {
            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).unregisterCallback(this.mSettingsListener);
        }
    }

    public final void setEmergencyView(View view) {
        EmergencyButton emergencyButton = (EmergencyButton) view;
        this.mView = emergencyButton;
        emergencyButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.EmergencyButtonController$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                EmergencyButtonController emergencyButtonController = EmergencyButtonController.this;
                emergencyButtonController.mMetricsLogger.action(200);
                PowerManager powerManager = emergencyButtonController.mPowerManager;
                if (powerManager != null) {
                    powerManager.userActivity(SystemClock.uptimeMillis(), true);
                }
                emergencyButtonController.mActivityTaskManager.stopSystemLockTaskMode();
                if (!SafeUIState.isSysUiSafeModeEnabled()) {
                    emergencyButtonController.mShadeController.collapseShade(false);
                }
                emergencyButtonController.mBackgroundExecutor.execute(new EmergencyButtonController$$ExternalSyntheticLambda1(emergencyButtonController, 1));
                SystemUIAnalytics.sendEventLog("102", SystemUIAnalytics.EID_EMERGENCY_CALL);
            }
        });
    }

    public void updateEmergencyCallButton() {
        if (this.mView != 0) {
            TelecomManager telecomManager = this.mTelecomManager;
            boolean z = telecomManager != null && telecomManager.isInCall();
            boolean isSecure = this.mKeyguardUpdateMonitor.isSecure();
            if (this.mKeyguardShowing || this.mBouncerShowing) {
                this.mBackgroundExecutor.execute(new EmergencyButtonController$$ExternalSyntheticLambda4(this, z, isSecure, 0));
            }
        }
    }
}
