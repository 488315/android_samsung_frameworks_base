package com.android.keyguard;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Debug;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Slog;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.AdminSecondaryLockScreenController;
import com.android.keyguard.DualDarInnerLockScreenController;
import com.android.keyguard.KeyguardArrowViewController;
import com.android.keyguard.KeyguardPluginControllerImpl;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSecurityViewFlipperController;
import com.android.keyguard.biometrics.KeyguardBiometricViewController;
import com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.Flags;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.FaceAuthAccessibilityDelegate;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor;
import com.android.systemui.classifier.FalsingA11yDelegate;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.Log;
import com.android.systemui.keyguard.SecurityLog;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.settings.GlobalSettings;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import dagger.Lazy;
import java.io.File;
import java.util.HashMap;
import javax.inject.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class KeyguardSecSecurityContainerController extends KeyguardSecurityContainerController {
    public final AlarmManager mAlarmManager;
    public final KeyguardBiometricViewController mBiometricViewController;
    public final AnonymousClass1 mConfigurationListener;
    public int mCurrentRotation;
    public final AnonymousClass2 mDisplayLifeCycleObserver;
    public final DisplayLifecycle mDisplayLifecycle;
    public final DevicePolicyManager mDpm;
    public final DualDarInnerLockScreenController mDualDarInnerLockScreenController;
    public int mImeBottom;
    public final InputMethodManager mImm;
    public boolean mIsDisappearAnimation;
    public boolean mIsImeShown;
    public boolean mIsPassword;
    public boolean mIsResetCredentialShowing;
    public boolean mIsSwipeBouncer;
    public final AnonymousClass3 mKeyguardArrowViewCallback;
    public final KeyguardArrowViewController mKeyguardArrowViewController;
    public final KeyguardCarrierTextViewController mKeyguardCarrierTextViewController;
    public final KeyguardPluginControllerImpl mKeyguardPluginController;
    public final KeyguardPunchHoleVIViewController mKeyguardPunchHoleVIViewController;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public int mNavigationBarHeight;
    public boolean mNeedsInput;
    private final SettingsHelper.OnChangedCallback mOnChangedCallback;
    public LockscreenCredential mPrevCredential;
    public int mRemainingBeforeWipe;
    public final SelectedUserInteractor mSelectedUserInteractor;
    private SettingsHelper mSettingsHelper;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.keyguard.KeyguardSecSecurityContainerController$3, reason: invalid class name */
    public final class AnonymousClass3 implements KeyguardArrowViewCallback {
        public AnonymousClass3() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.keyguard.KeyguardSecSecurityContainerController$5, reason: invalid class name */
    public final class AnonymousClass5 implements KeyguardSecurityCallback {
        public AnonymousClass5() {
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void dismiss(boolean z, int i, KeyguardSecurityModel.SecurityMode securityMode) {
            dismiss(z, i, false, securityMode);
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void finish(int i) {
            boolean z;
            Integer valueOf = Integer.valueOf(i);
            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = KeyguardSecSecurityContainerController.this;
            Log.d("KeyguardUnlockInfo", "finish userId=%d, hasDismissAction=%d", valueOf, Integer.valueOf(LogUtil.getInt(keyguardSecSecurityContainerController.mDismissAction)));
            ActivityStarter.OnDismissAction onDismissAction = keyguardSecSecurityContainerController.mDismissAction;
            if (onDismissAction != null) {
                z = onDismissAction.onDismiss();
                keyguardSecSecurityContainerController.mDismissAction = null;
                keyguardSecSecurityContainerController.mCancelAction = null;
            } else {
                z = false;
            }
            ViewMediatorCallback viewMediatorCallback = keyguardSecSecurityContainerController.mViewMediatorCallback;
            if (viewMediatorCallback != null) {
                if (z) {
                    viewMediatorCallback.keyguardDonePending(i);
                } else {
                    viewMediatorCallback.keyguardDone(i);
                }
            }
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void onCancelClicked() {
            KeyguardSecSecurityContainerController.this.mViewMediatorCallback.onCancelClicked();
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void onSecurityModeChanged(boolean z) {
            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = KeyguardSecSecurityContainerController.this;
            keyguardSecSecurityContainerController.mNeedsInput = z;
            keyguardSecSecurityContainerController.mViewMediatorCallback.setNeedsInput(z);
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void reportUnlockAttempt(int i, int i2, boolean z) {
            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = KeyguardSecSecurityContainerController.this;
            if (!z) {
                if (!keyguardSecSecurityContainerController.mUpdateMonitor.isForgotPasswordView()) {
                    keyguardSecSecurityContainerController.reportFailedUnlockAttempt(i, i2);
                    keyguardSecSecurityContainerController.mUpdateMonitor.notifyFailedUnlockAttemptChanged();
                    return;
                } else if (keyguardSecSecurityContainerController.mLockPatternUtils.getCurrentFailedPasswordAttempts(-9899) + 1 >= 3) {
                    keyguardSecSecurityContainerController.mLockPatternUtils.expirePreviousData();
                    keyguardSecSecurityContainerController.mViewMediatorCallback.resetKeyguard();
                    return;
                } else {
                    LockPatternUtils lockPatternUtils = keyguardSecSecurityContainerController.mLockPatternUtils;
                    if (DeviceType.isWeaverDevice()) {
                        i = -9899;
                    }
                    lockPatternUtils.reportFailedPasswordAttempt(i);
                    return;
                }
            }
            int failedUnlockAttempts = keyguardSecSecurityContainerController.mUpdateMonitor.getFailedUnlockAttempts(i);
            int i3 = AnonymousClass6.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[keyguardSecSecurityContainerController.mSecurityModel.getSecurityMode(i).ordinal()];
            String str = i3 != 1 ? i3 != 2 ? i3 != 3 ? null : "2" : "3" : "1";
            if (str != null) {
                HashMap hashMap = new HashMap();
                hashMap.put("det", "1");
                hashMap.put(str, String.valueOf(failedUnlockAttempts + 1));
                SystemUIAnalytics.sendEventCDLog("102", SystemUIAnalytics.EID_UNLOCK_BOUNCER, hashMap);
            }
            KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardSecSecurityContainerController.mUpdateMonitor;
            keyguardUpdateMonitor.clearFailedUnlockAttempts(true);
            keyguardSecSecurityContainerController.mLockPatternUtils.reportSuccessfulPasswordAttempt(i);
            PendingIntent broadcast = PendingIntent.getBroadcast(keyguardSecSecurityContainerController.getContext(), 0, new Intent("com.samsung.keyguard.BIOMETRIC_LOCKOUT_RESET"), 603979776);
            if (broadcast != null) {
                android.util.Log.d("KeyguardSecSecurityContainer", "Alarm manager have ACTION_BIOMETRIC_LOCKOUT_RESET then will be canceled");
                keyguardSecSecurityContainerController.mAlarmManager.cancel(broadcast);
                broadcast.cancel();
            }
            if (((KnoxStateMonitorImpl) keyguardSecSecurityContainerController.mKnoxStateMonitor).mEdmMonitor.mPwdChangeRequest > 0) {
                Intent intent = new Intent();
                intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.password.ChooseLockGeneric$InternalActivity");
                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                intent.addFlags(QuickStepContract.SYSUI_STATE_BACK_DISABLED);
                intent.addFlags(QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED);
                keyguardSecSecurityContainerController.getContext().startActivityAsUser(intent, UserHandle.CURRENT);
            }
            if (keyguardUpdateMonitor.isForgotPasswordView()) {
                Intent intent2 = new Intent();
                intent2.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.password.ChooseLockGeneric$RecoveryActivity");
                intent2.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                intent2.addFlags(QuickStepContract.SYSUI_STATE_BACK_DISABLED);
                intent2.addFlags(QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED);
                intent2.putExtra("hide_insecure_options", true);
                intent2.putExtra("recover_password", true);
                LockscreenCredential lockscreenCredential = keyguardSecSecurityContainerController.mPrevCredential;
                if (lockscreenCredential != null) {
                    intent2.putExtra(HostAuth.PASSWORD, (Parcelable) lockscreenCredential);
                }
                keyguardSecSecurityContainerController.mPrevCredential = null;
                keyguardSecSecurityContainerController.mIsResetCredentialShowing = true;
                Settings.Secure.putInt(keyguardSecSecurityContainerController.getContext().getContentResolver(), SettingsHelper.INDEX_RESET_CREDENTIAL, 0);
                keyguardSecSecurityContainerController.getContext().startActivityAsUser(intent2, UserHandle.CURRENT);
            }
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void reset() {
            KeyguardSecSecurityContainerController.this.mViewMediatorCallback.resetKeyguard();
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void setPrevCredential(LockscreenCredential lockscreenCredential) {
            KeyguardSecSecurityContainerController.this.mPrevCredential = lockscreenCredential;
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void showBackupSecurity(KeyguardSecurityModel.SecurityMode securityMode) {
            userActivity();
            KeyguardSecSecurityContainerController.this.showSecurityScreen(securityMode);
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void userActivity() {
            KeyguardSecSecurityContainerController.this.mViewMediatorCallback.userActivity();
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final boolean dismiss(boolean z, int i, boolean z2, KeyguardSecurityModel.SecurityMode securityMode) {
            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = KeyguardSecSecurityContainerController.this;
            boolean z3 = keyguardSecSecurityContainerController.mUpdateMonitor.mKeyguardGoingAway;
            KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardSecSecurityContainerController.mUpdateMonitor;
            if ((z3 || ((KeyguardViewMediator) ((KeyguardUnlockAnimationController) ((KeyguardStateControllerImpl) keyguardSecSecurityContainerController.mKeyguardStateController).mUnlockAnimationControllerLazy.get()).keyguardViewMediator.get()).isAnimatingBetweenKeyguardAndSurfaceBehind()) && keyguardUpdateMonitor.isFaceOptionEnabled()) {
                android.util.Log.d("KeyguardSecSecurityContainer", "keyguard is already goingAway and face enabled. cancel dismiss");
                return false;
            }
            if (CscRune.SECURITY_SIM_PERM_DISABLED && keyguardUpdateMonitor.isIccBlockedPermanently()) {
                android.util.Log.d("KeyguardSecSecurityContainer", "dismiss failed. Permanent state.");
                return false;
            }
            if (z) {
                SecurityLog.d("KeyguardSecSecurityContainer", "dismiss caller\n" + Debug.getCallers(10, "  "));
            }
            if (!z && KeyguardUnlockInfo.unlockTrigger == KeyguardUnlockInfo.UnlockTrigger.TRIGGER_UNKNOWN && DeviceType.getDebugLevel() != DeviceType.DEBUG_LEVEL_LOW) {
                android.util.Log.d("KeyguardUnlockInfo", "unknown trigger caller\n" + Debug.getCallers(15, "  "));
            }
            boolean showNextSecurityScreenOrFinish = keyguardSecSecurityContainerController.showNextSecurityScreenOrFinish(z, i, z2, securityMode);
            if (showNextSecurityScreenOrFinish && z) {
                keyguardUpdateMonitor.setUnlockingKeyguard(true);
            }
            return showNextSecurityScreenOrFinish;
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void onUserInput() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.keyguard.KeyguardSecSecurityContainerController$6, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass6 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode = iArr;
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.FMM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SmartcardPIN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Permanent.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.AdminLock.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SKTCarrierLock.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SKTCarrierPassword.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.RMM.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.KNOXGUARD.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SimPin.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SimPuk.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SimPerso.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class OnApplyWindowInsetsListener implements View.OnApplyWindowInsetsListener {
        public /* synthetic */ OnApplyWindowInsetsListener(KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, int i) {
            this();
        }

        @Override // android.view.View.OnApplyWindowInsetsListener
        public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            int i;
            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = KeyguardSecSecurityContainerController.this;
            r0 = 1;
            byte b = 1;
            if (keyguardSecSecurityContainerController.isPassword(keyguardSecSecurityContainerController.mCurrentSecurityMode)) {
                int i2 = LsRune.SECURITY_NAVBAR_ENABLED ? 0 : windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars()).bottom;
                int i3 = windowInsets.getInsets(WindowInsets.Type.ime()).bottom;
                KeyguardSecSecurityContainerController keyguardSecSecurityContainerController2 = KeyguardSecSecurityContainerController.this;
                if (keyguardSecSecurityContainerController2.mImeBottom != i3) {
                    keyguardSecSecurityContainerController2.mImeBottom = i3;
                    keyguardSecSecurityContainerController2.mIsImeShown = i3 != 0;
                    keyguardSecSecurityContainerController2.updateLayoutMargins();
                    Context context = KeyguardSecSecurityContainerController.this.getContext();
                    int i4 = KeyguardSecSecurityContainerController.this.mImeBottom;
                    int i5 = SecurityUtils.sPINContainerBottomMargin;
                    int rotation = DeviceState.getRotation(context.getResources().getConfiguration().windowConfiguration.getRotation());
                    if (rotation != 1 && rotation != 3) {
                        b = 0;
                    }
                    int[] iArr = SecurityUtils.sImeHeight;
                    int i6 = iArr[b];
                    if (i6 == 0 || i6 != i4) {
                        iArr[b] = i4;
                    }
                    if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                        KeyguardSecSecurityContainerController keyguardSecSecurityContainerController3 = KeyguardSecSecurityContainerController.this;
                        keyguardSecSecurityContainerController3.mUpdateMonitor.updateSIPShownState(keyguardSecSecurityContainerController3.mIsImeShown);
                    }
                }
                i = Integer.max(i2, i3);
            } else {
                if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                    int i7 = windowInsets.getInsets(WindowInsets.Type.ime()).bottom;
                    KeyguardSecSecurityContainerController keyguardSecSecurityContainerController4 = KeyguardSecSecurityContainerController.this;
                    if (keyguardSecSecurityContainerController4.mImeBottom != i7) {
                        keyguardSecSecurityContainerController4.mImeBottom = i7;
                        boolean z = i7 != 0;
                        keyguardSecSecurityContainerController4.mIsImeShown = z;
                        keyguardSecSecurityContainerController4.mUpdateMonitor.updateSIPShownState(z);
                    }
                }
                i = 0;
            }
            ((KeyguardSecSecurityContainer) ((ViewController) KeyguardSecSecurityContainerController.this).mView).setPadding(((KeyguardSecSecurityContainer) ((ViewController) KeyguardSecSecurityContainerController.this).mView).getPaddingLeft(), ((KeyguardSecSecurityContainer) ((ViewController) KeyguardSecSecurityContainerController.this).mView).getPaddingTop(), ((KeyguardSecSecurityContainer) ((ViewController) KeyguardSecSecurityContainerController.this).mView).getPaddingRight(), i);
            return windowInsets.inset(0, 0, 0, i);
        }

        private OnApplyWindowInsetsListener() {
        }
    }

    /* renamed from: $r8$lambda$5mPUvyQ1u7jyxIoop9We5V-6bYo, reason: not valid java name */
    public static void m837$r8$lambda$5mPUvyQ1u7jyxIoop9We5V6bYo(KeyguardSecSecurityContainerController keyguardSecSecurityContainerController) {
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("OnChangedCallback() "), keyguardSecSecurityContainerController.mIsResetCredentialShowing, "KeyguardSecSecurityContainer");
        SettingsHelper settingsHelper = keyguardSecSecurityContainerController.mSettingsHelper;
        if (settingsHelper != null && settingsHelper.isResetCredential() && keyguardSecSecurityContainerController.mIsResetCredentialShowing) {
            keyguardSecSecurityContainerController.mIsResetCredentialShowing = false;
            int selectedUserId = keyguardSecSecurityContainerController.mSelectedUserInteractor.getSelectedUserId(false);
            keyguardSecSecurityContainerController.mUpdateMonitor.clearFailedUnlockAttempts(true);
            keyguardSecSecurityContainerController.mLockPatternUtils.reportSuccessfulPasswordAttempt(selectedUserId);
            PendingIntent broadcast = PendingIntent.getBroadcast(keyguardSecSecurityContainerController.getContext(), 0, new Intent("com.samsung.keyguard.BIOMETRIC_LOCKOUT_RESET"), 603979776);
            if (broadcast != null && keyguardSecSecurityContainerController.mAlarmManager != null) {
                android.util.Log.d("KeyguardSecSecurityContainer", "Alarm manager have ACTION_BIOMETRIC_LOCKOUT_RESET then will be canceled");
                keyguardSecSecurityContainerController.mAlarmManager.cancel(broadcast);
                broadcast.cancel();
            }
            Settings.Secure.putInt(keyguardSecSecurityContainerController.getContext().getContentResolver(), SettingsHelper.INDEX_RESET_CREDENTIAL, 0);
            keyguardSecSecurityContainerController.new AnonymousClass5().dismiss(true, selectedUserId, false, keyguardSecSecurityContainerController.mCurrentSecurityMode);
        }
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.keyguard.KeyguardSecSecurityContainerController$1] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.keyguard.KeyguardSecSecurityContainerController$2] */
    public KeyguardSecSecurityContainerController(KeyguardSecSecurityContainer keyguardSecSecurityContainer, AlarmManager alarmManager, DevicePolicyManager devicePolicyManager, DisplayLifecycle displayLifecycle, DualDarInnerLockScreenController.Factory factory, InputMethodManager inputMethodManager, KeyguardCarrierTextViewController keyguardCarrierTextViewController, KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController, KeyguardArrowViewController.Factory factory2, KeyguardBiometricViewController keyguardBiometricViewController, KeyguardPluginControllerImpl.Factory factory3, SettingsHelper settingsHelper, AdminSecondaryLockScreenController.Factory factory4, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel keyguardSecurityModel, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, KeyguardStateController keyguardStateController, KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController, ConfigurationController configurationController, FalsingCollector falsingCollector, FalsingManager falsingManager, UserSwitcherController userSwitcherController, FeatureFlags featureFlags, GlobalSettings globalSettings, SessionTracker sessionTracker, FalsingA11yDelegate falsingA11yDelegate, TelephonyManager telephonyManager, ViewMediatorCallback viewMediatorCallback, AudioManager audioManager, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, BouncerMessageInteractor bouncerMessageInteractor, Provider provider, SelectedUserInteractor selectedUserInteractor, DeviceProvisionedController deviceProvisionedController, FaceAuthAccessibilityDelegate faceAuthAccessibilityDelegate, KeyguardTransitionInteractor keyguardTransitionInteractor, Lazy lazy, Provider provider2) {
        super(keyguardSecSecurityContainer, factory4, lockPatternUtils, keyguardUpdateMonitor, keyguardSecurityModel, metricsLogger, uiEventLogger, keyguardStateController, keyguardSecurityViewFlipperController, configurationController, falsingCollector, falsingManager, userSwitcherController, featureFlags, globalSettings, sessionTracker, falsingA11yDelegate, telephonyManager, viewMediatorCallback, audioManager, deviceEntryFaceAuthInteractor, bouncerMessageInteractor, provider, selectedUserInteractor, deviceProvisionedController, faceAuthAccessibilityDelegate, devicePolicyManager, keyguardTransitionInteractor, lazy, provider2);
        int i = 0;
        this.mImeBottom = 0;
        this.mRemainingBeforeWipe = 20;
        this.mIsDisappearAnimation = false;
        this.mOnChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticLambda1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                KeyguardSecSecurityContainerController.m837$r8$lambda$5mPUvyQ1u7jyxIoop9We5V6bYo(KeyguardSecSecurityContainerController.this);
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardSecSecurityContainerController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDisplayDeviceTypeChanged() {
                KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = KeyguardSecSecurityContainerController.this;
                keyguardSecSecurityContainerController.configureMode();
                keyguardSecSecurityContainerController.updateLayoutMargins();
            }
        };
        this.mCurrentRotation = 0;
        this.mDisplayLifeCycleObserver = new DisplayLifecycle.Observer() { // from class: com.android.keyguard.KeyguardSecSecurityContainerController.2
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onDisplayChanged(int i2) {
                KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = KeyguardSecSecurityContainerController.this;
                int rotation = DeviceState.getRotation(keyguardSecSecurityContainerController.getResources().getConfiguration().windowConfiguration.getRotation());
                if (keyguardSecSecurityContainerController.mCurrentRotation != rotation) {
                    keyguardSecSecurityContainerController.mCurrentRotation = rotation;
                    keyguardSecSecurityContainerController.updateLayoutMargins(rotation);
                }
            }
        };
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardSecSecurityContainerController.4
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDualDarInnerLockScreenStateChanged(int i2, boolean z) {
                KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = KeyguardSecSecurityContainerController.this;
                keyguardSecSecurityContainerController.configureMode();
                keyguardSecSecurityContainerController.updateLayoutMargins();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onLockModeChanged() {
                KeyguardSecSecurityContainerController.this.updateLayoutMargins();
            }
        };
        this.mDpm = devicePolicyManager;
        this.mImm = inputMethodManager;
        this.mAlarmManager = alarmManager;
        this.mDisplayLifecycle = displayLifecycle;
        this.mSettingsHelper = settingsHelper;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mKeyguardCarrierTextViewController = keyguardCarrierTextViewController;
        KeyguardArrowViewController keyguardArrowViewController = null;
        this.mKeyguardPunchHoleVIViewController = LsRune.SECURITY_PUNCH_HOLE_FACE_VI ? keyguardPunchHoleVIViewController : null;
        if (LsRune.SECURITY_ARROW_VIEW) {
            keyguardArrowViewController = new KeyguardArrowViewController(factory2.mView, anonymousClass3, factory2.mConfigurationController, factory2.mKeyguardUpdateMonitor, factory2.mViewMediatorCallback);
        }
        this.mKeyguardArrowViewController = keyguardArrowViewController;
        this.mBiometricViewController = keyguardBiometricViewController;
        this.mKeyguardPluginController = new KeyguardPluginControllerImpl(factory3.mContext, factory3.mViewMediatorCallback, factory3.mDesktopManager, factory3.mSubScreenManager, this.mKeyguardSecurityCallback, factory3.mLatencyTracker, factory3.mLockPatternUtils, factory3.mKeyguardUpdateMonitor, factory3.mSelectedUserInteractor, 0);
        ((KeyguardSecSecurityContainer) this.mView).setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener(this, i));
        this.mKnoxStateMonitor = (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
        if (factory != null) {
            KeyguardSecSecurityContainerController$$ExternalSyntheticLambda2 keyguardSecSecurityContainerController$$ExternalSyntheticLambda2 = new KeyguardSecSecurityContainerController$$ExternalSyntheticLambda2(this);
            this.mDualDarInnerLockScreenController = new DualDarInnerLockScreenController(factory.mContext, factory.mParent, this, factory.mUpdateMonitor, this.mKeyguardSecurityCallback, keyguardSecSecurityContainerController$$ExternalSyntheticLambda2, factory.mHandler, factory.mLayoutInflater, factory.mKeyguardSecurityViewControllerFactory, factory.mSelectedUserInteractor);
        }
        this.mKeyguardStateController = keyguardStateController;
    }

    @Override // com.android.keyguard.KeyguardSecurityContainerController
    public final void configureMode() {
        ((KeyguardSecSecurityContainer) this.mView).initMode((SecurityUtils.isArrowViewSupported(this.mCurrentSecurityMode) && (DeviceType.isTablet() || (LsRune.SECURITY_SUB_DISPLAY_LOCK && getContext().getResources().getConfiguration().semDisplayDeviceType == 0))) ? 3 : 0, this.mGlobalSettings, this.mFalsingManager, this.mUserSwitcherController, null, this.mFalsingA11yDelegate);
    }

    @Override // com.android.keyguard.KeyguardSecurityContainerController
    public final KeyguardSecurityCallback getSecurityCallback() {
        return new AnonymousClass5();
    }

    public final boolean interceptRestKey(KeyEvent keyEvent) {
        if (interceptMediaKey(keyEvent)) {
            return true;
        }
        if (!((KeyguardSecSecurityContainer) this.mView).hasFocus()) {
            ((KeyguardSecSecurityContainer) this.mView).requestFocus();
        }
        return ((KeyguardSecSecurityContainer) this.mView).dispatchKeyEvent(keyEvent);
    }

    public final boolean isPassword(KeyguardSecurityModel.SecurityMode securityMode) {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        if (keyguardUpdateMonitor.isForgotPasswordView()) {
            return keyguardUpdateMonitor.getPrevCredentialType() == 4;
        }
        if (securityMode != null) {
            return securityMode == KeyguardSecurityModel.SecurityMode.Password || securityMode == KeyguardSecurityModel.SecurityMode.SKTCarrierPassword;
        }
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecurityContainerController, com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).mEdmMonitor;
        if (edmMonitor != null) {
            edmMonitor.updateFailedUnlockAttemptForDeviceDisabled();
        }
        this.mKeyguardCarrierTextViewController.init();
        if (LsRune.SECURITY_PUNCH_HOLE_FACE_VI) {
            KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = this.mKeyguardPunchHoleVIViewController;
            keyguardPunchHoleVIViewController.setBouncer();
            keyguardPunchHoleVIViewController.init();
        }
        if (LsRune.SECURITY_ARROW_VIEW) {
            this.mKeyguardArrowViewController.init();
        }
        this.mBiometricViewController.init();
        if (CscRune.SECURITY_WARNING_WIPE_OUT_MESSAGE) {
            int i = 0;
            int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId(false);
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
            int failedUnlockAttempts = keyguardUpdateMonitor.getFailedUnlockAttempts(selectedUserId);
            boolean isAutoWipe = keyguardUpdateMonitor.isAutoWipe();
            int maximumFailedPasswordsForWipe = this.mDpm.getMaximumFailedPasswordsForWipe(null, selectedUserId);
            if (maximumFailedPasswordsForWipe > 0) {
                i = maximumFailedPasswordsForWipe;
            } else if (isAutoWipe) {
                i = 20;
            }
            android.util.Log.d("KeyguardSecSecurityContainer", HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(i, failedUnlockAttempts, "doWipeOutIfMaxFailedAttemptsSinceBoot( failedAttemptsBeforeWipe = ", " , failedAttempts = ", " )"));
            if (i <= 0 || failedUnlockAttempts < i) {
                return;
            }
            Slog.e("KeyguardSecSecurityContainer", "doWipeOutIfMaxFailedAttemptsSinceBoot( Too many unlock attempts; device will be wiped! )");
            Context context = getContext();
            if (ResetDeviceUtils.sResetDeviceUtils == null) {
                ResetDeviceUtils.sResetDeviceUtils = new ResetDeviceUtils(context);
            }
            ResetDeviceUtils.sResetDeviceUtils.wipeOut(failedUnlockAttempts, selectedUserId, 1);
        }
    }

    public final void onPause() {
        android.util.Log.d("KeyguardSecurityContainer", "screen off, instance " + Integer.toHexString(hashCode()) + " at " + SystemClock.uptimeMillis());
        showPrimarySecurityScreen();
        this.mAdminSecondaryLockScreenController.hide();
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            getCurrentSecurityController(new KeyguardSecurityContainerController$$ExternalSyntheticLambda1(3));
        }
        KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) this.mView;
        AlertDialog alertDialog = keyguardSecurityContainer.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            keyguardSecurityContainer.mAlertDialog = null;
        }
        keyguardSecurityContainer.mViewMode.reset();
        ((KeyguardSecurityContainer) this.mView).clearFocus();
        this.mDualDarInnerLockScreenController.hide();
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        if (keyguardUpdateMonitor.getMaxFailedUnlockAttempts() != 50) {
            keyguardUpdateMonitor.updatePermanentLock(this.mSelectedUserInteractor.getSelectedUserId(false));
        }
    }

    public final void onResume(int i) {
        android.util.Log.d("KeyguardSecurityContainer", "screen on, instance " + Integer.toHexString(hashCode()));
        ((KeyguardSecurityContainer) this.mView).requestFocus();
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            KeyguardSecurityContainer.ViewMode viewMode = ((KeyguardSecurityContainer) this.mView).mViewMode;
            SysUiStatsLog.write(63, viewMode instanceof KeyguardSecurityContainer.SidedSecurityMode ? ((viewMode instanceof KeyguardSecurityContainer.SidedSecurityMode) && ((KeyguardSecurityContainer.SidedSecurityMode) viewMode).isLeftAligned()) ? 3 : 4 : 2);
            getCurrentSecurityController(new KeyguardSecurityContainerController$$ExternalSyntheticLambda4());
        }
        KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) this.mView;
        this.mSecurityModel.getSecurityMode(super.mSelectedUserInteractor.getSelectedUserId(false));
        boolean z = ((KeyguardStateControllerImpl) super.mKeyguardStateController).mFaceEnrolledAndEnabled;
        keyguardSecurityContainer.getClass();
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            this.mIsDisappearAnimation = false;
        }
        if (LsRune.SECURITY_ARROW_VIEW) {
            configureMode();
        }
        updateLayoutMargins();
    }

    public final void onTrimMemory(int i) {
        KeyguardSecurityViewFlipper keyguardSecurityViewFlipper;
        if (i < 40 || (keyguardSecurityViewFlipper = ((KeyguardSecSecurityContainer) this.mView).mSecurityViewFlipper) == null) {
            return;
        }
        keyguardSecurityViewFlipper.removeAllViews();
    }

    @Override // com.android.keyguard.KeyguardSecurityContainerController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        this.mCurrentRotation = DeviceState.getRotation(getResources().getConfiguration().windowConfiguration.getRotation());
        this.mDisplayLifecycle.addObserver(this.mDisplayLifeCycleObserver);
        this.mUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        this.mSettingsHelper.registerCallback(this.mOnChangedCallback, Settings.Secure.getUriFor(SettingsHelper.INDEX_RESET_CREDENTIAL));
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityContainerController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        this.mUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        this.mDisplayLifecycle.removeObserver(this.mDisplayLifeCycleObserver);
        this.mSettingsHelper.unregisterCallback(this.mOnChangedCallback);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityContainerController
    public final void reinflateViewFlipper(KeyguardSecurityViewFlipperController.OnViewInflatedCallback onViewInflatedCallback) {
        if (onViewInflatedCallback == null) {
            super.reinflateViewFlipper(null);
        } else {
            getCurrentSecurityController(onViewInflatedCallback);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00be  */
    @Override // com.android.keyguard.KeyguardSecurityContainerController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void reportFailedUnlockAttempt(int r17, int r18) {
        /*
            Method dump skipped, instructions count: 485
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardSecSecurityContainerController.reportFailedUnlockAttempt(int, int):void");
    }

    public final void setOnDismissAction(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable) {
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        Runnable runnable2 = this.mCancelAction;
        if (runnable2 != null) {
            runnable2.run();
        }
        this.mDismissAction = onDismissAction;
        this.mCancelAction = runnable;
        this.mUpdateMonitor.setDismissActionExist(onDismissAction != null);
    }

    public final void setSecurityContainerVisibility(int i) {
        ((KeyguardSecSecurityContainer) this.mView).setVisibility(i);
    }

    public final boolean shouldEnableMenuKey() {
        if (CscRune.SECURITY_SIM_PERM_DISABLED && this.mUpdateMonitor.isSimDisabledPermanently()) {
            return false;
        }
        return !((KeyguardSecurityContainer) this.mView).getResources().getBoolean(R.bool.config_disableMenuKeyInLockScreen) || ActivityManager.isRunningInTestHarness() || new File("/data/local/enable_menu_key").exists();
    }

    @Override // com.android.keyguard.KeyguardSecurityContainerController
    public final void showMessage(CharSequence charSequence, ColorStateList colorStateList, boolean z) {
        super.showMessage(charSequence, colorStateList, z);
        if (this.mCurrentSecurityMode == KeyguardSecurityModel.SecurityMode.None || !AccessibilityManager.getInstance(getContext()).isTouchExplorationEnabled()) {
            return;
        }
        ((KeyguardSecSecurityContainer) this.mView).announceForAccessibility(charSequence);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0195  */
    @Override // com.android.keyguard.KeyguardSecurityContainerController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean showNextSecurityScreenOrFinish(boolean r17, int r18, boolean r19, com.android.keyguard.KeyguardSecurityModel.SecurityMode r20) {
        /*
            Method dump skipped, instructions count: 448
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardSecSecurityContainerController.showNextSecurityScreenOrFinish(boolean, int, boolean, com.android.keyguard.KeyguardSecurityModel$SecurityMode):boolean");
    }

    @Override // com.android.keyguard.KeyguardSecurityContainerController
    public void showSecurityScreen(KeyguardSecurityModel.SecurityMode securityMode) {
        android.util.Log.d("KeyguardSecSecurityContainer", "showSecurityScreen(" + securityMode + ") current = " + this.mCurrentSecurityMode);
        KeyguardSecurityModel.SecurityMode securityMode2 = this.mCurrentSecurityMode;
        if (securityMode == securityMode2) {
            return;
        }
        if (AnonymousClass6.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[securityMode2.ordinal()] == 12 && securityMode == KeyguardSecurityModel.SecurityMode.None) {
            int nextSubIdForState = ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).getNextSubIdForState(2);
            EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor;
            boolean z = false;
            if (edmMonitor != null && edmMonitor.mLockedIccIdList != null && SubscriptionManager.isValidSubscriptionId(nextSubIdForState)) {
                SubscriptionInfo activeSubscriptionInfo = ((SubscriptionManager) edmMonitor.knoxStateMonitor.mContext.getSystemService("telephony_subscription_service")).getActiveSubscriptionInfo(nextSubIdForState);
                String iccId = activeSubscriptionInfo != null ? activeSubscriptionInfo.getIccId() : null;
                Integer valueOf = Integer.valueOf(nextSubIdForState);
                Object obj = activeSubscriptionInfo;
                if (activeSubscriptionInfo == null) {
                    obj = "";
                }
                android.util.Log.d("EdmMonitor", String.format("isSubIdLockedByAdmin subId=%d, subInfo=%s, iccId=%s", valueOf, obj, iccId != null ? iccId : ""));
                if (iccId != null) {
                    for (String str : edmMonitor.mLockedIccIdList) {
                        if (!str.equals(iccId)) {
                        }
                    }
                }
                z = true;
                break;
            }
            Log.d("KeyguardSecSecurityContainer", "reportSecurityMode SimPin -> None simPinSubId = %d, isLockedByMDM=%b", Integer.valueOf(nextSubIdForState), Boolean.valueOf(z));
        }
        this.mUpdateMonitor.dispatchSecurityModeChanged(securityMode);
        super.showSecurityScreen(securityMode);
        updateLayoutMargins();
    }

    @Override // com.android.keyguard.KeyguardSecurityContainerController
    public final void startAppearAnimation() {
        super.startAppearAnimation();
        KeyguardBiometricViewController keyguardBiometricViewController = this.mBiometricViewController;
        keyguardBiometricViewController.updateBiometricViewLayout();
        keyguardBiometricViewController.startLockIconAnimation(true);
        if (LsRune.SECURITY_ARROW_VIEW) {
            KeyguardArrowViewController keyguardArrowViewController = this.mKeyguardArrowViewController;
            keyguardArrowViewController.startArrowViewAnimation(keyguardArrowViewController.mLeftArrow);
            keyguardArrowViewController.startArrowViewAnimation(keyguardArrowViewController.mRightArrow);
            if (keyguardArrowViewController.isInvalidArrowView()) {
                return;
            }
            SharedPreferences.Editor edit = keyguardArrowViewController.getContext().getSharedPreferences(SystemUIAnalytics.LOCK_PREF_NAME, 0).edit();
            int bouncerOneHandPosition = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getBouncerOneHandPosition();
            edit.putString(SystemUIAnalytics.STID_LOCK_BOUNCER_POSITION, bouncerOneHandPosition != 0 ? bouncerOneHandPosition != 2 ? SystemUIAnalytics.DT_BOUNCER_POSITION_CENTER : SystemUIAnalytics.DT_BOUNCER_POSITION_RIGHT : SystemUIAnalytics.DT_BOUNCER_POSITION_LEFT).apply();
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityContainerController
    public final boolean startDisappearAnimation(Runnable runnable) {
        super.startDisappearAnimation(runnable);
        this.mBiometricViewController.startLockIconAnimation(false);
        if (LsRune.SECURITY_ARROW_VIEW) {
            this.mKeyguardArrowViewController.updateArrowVisibility(false);
        }
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            this.mIsDisappearAnimation = true;
        }
        return true;
    }

    public final void updateLayoutMargins() {
        updateLayoutMargins(DeviceState.getRotation(getResources().getConfiguration().windowConfiguration.getRotation()));
    }

    public final void updateLayoutParams(int i, int i2, int i3) {
        KeyguardSecurityViewFlipper keyguardSecurityViewFlipper = ((KeyguardSecSecurityContainer) this.mView).mSecurityViewFlipper;
        if (keyguardSecurityViewFlipper == null) {
            android.util.Log.d("KeyguardSecSecurityContainer", "updateLayoutParams securityViewFlipper is null");
            return;
        }
        Resources resources = getResources();
        ConstraintSet constraintSet = new ConstraintSet();
        if (DeviceType.isTablet()) {
            constraintSet.constrainWidth(keyguardSecurityViewFlipper.getId(), resources.getDimensionPixelSize(R.dimen.kg_message_area_width_tablet));
        } else if (!LsRune.SECURITY_SUB_DISPLAY_LOCK || DeviceState.isSmartViewFitToActiveDisplay()) {
            constraintSet.constrainWidth(keyguardSecurityViewFlipper.getId(), 0);
        } else {
            Context context = getContext();
            int i4 = SecurityUtils.sPINContainerBottomMargin;
            constraintSet.constrainWidth(keyguardSecurityViewFlipper.getId(), (!(context.getResources().getConfiguration().semDisplayDeviceType == 0) || (this.mCurrentSecurityMode == KeyguardSecurityModel.SecurityMode.KNOXGUARD)) ? 0 : SecurityUtils.getMainSecurityViewFlipperSize(getContext(), this.mIsPassword));
        }
        Configuration configuration = getResources().getConfiguration();
        int i5 = SecurityUtils.sPINContainerBottomMargin;
        boolean z = configuration.getLayoutDirection() == 1;
        constraintSet.connect(keyguardSecurityViewFlipper.getId(), 6, 0, 6, z ? i2 : i);
        constraintSet.connect(keyguardSecurityViewFlipper.getId(), 7, 0, 7, z ? i : i2);
        constraintSet.connect(keyguardSecurityViewFlipper.getId(), 4, 0, 4, i3);
        constraintSet.connect(keyguardSecurityViewFlipper.getId(), 3, 0, 3, 0);
        constraintSet.constrainHeight(keyguardSecurityViewFlipper.getId(), 0);
        constraintSet.applyTo((ConstraintLayout) this.mView);
        if (LsRune.SECURITY_ARROW_VIEW) {
            this.mKeyguardArrowViewController.updateArrowView();
        }
    }

    public final void updateLayoutMargins(int i) {
        int inDisplayFingerprintHeight;
        int i2;
        int i3;
        int i4;
        if (((KeyguardSecSecurityContainer) this.mView).mSecurityViewFlipper == null) {
            return;
        }
        Resources resources = getResources();
        KeyguardSecurityModel.SecurityMode securityMode = this.mCurrentSecurityMode;
        this.mIsPassword = isPassword(securityMode);
        int i5 = 0;
        this.mNavigationBarHeight = LsRune.SECURITY_NAVBAR_ENABLED ? resources.getDimensionPixelSize(android.R.dimen.resolver_empty_state_container_padding_top) : 0;
        boolean z = LsRune.SECURITY_SUB_DISPLAY_LOCK;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        if (z && !DeviceState.isSmartViewFitToActiveDisplay()) {
            Context context = getContext();
            int i6 = SecurityUtils.sPINContainerBottomMargin;
            boolean z2 = context.getResources().getConfiguration().semDisplayDeviceType == 0;
            if (DeviceState.shouldEnableKeyguardScreenRotation(getContext()) || !this.mNeedsInput || this.mCurrentSecurityMode == KeyguardSecurityModel.SecurityMode.KNOXGUARD) {
                i3 = keyguardUpdateMonitor.isHiddenInputContainer() ? 0 : this.mNavigationBarHeight;
                if (i != 1 && i != 3) {
                    i4 = (this.mIsPassword && this.mIsImeShown) ? 0 : this.mNavigationBarHeight;
                    i3 = 0;
                } else {
                    int i7 = z2 ? 0 : i3;
                    if (z2) {
                        i3 = 0;
                    }
                    if (z2 && (!this.mIsPassword || !this.mIsImeShown)) {
                        i5 = this.mNavigationBarHeight;
                    }
                    int i8 = i5;
                    i5 = i7;
                    i4 = i8;
                }
            } else {
                i4 = 0;
                i3 = 0;
            }
            updateLayoutParams(i5, i3, i4);
            return;
        }
        if (DeviceType.isTablet()) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_emergency_button_margin_bottom_for_tablet_fingerprint) + DeviceState.getInDisplayFingerprintHeight();
            boolean z3 = keyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted() && !keyguardUpdateMonitor.isHiddenInputContainer();
            if (i != 1 && i != 2 && i != 3) {
                if (!this.mIsPassword || !this.mIsImeShown) {
                    if (!z3) {
                        dimensionPixelSize = this.mNavigationBarHeight;
                    }
                    updateLayoutParams(0, 0, dimensionPixelSize);
                    return;
                }
                dimensionPixelSize = 0;
                updateLayoutParams(0, 0, dimensionPixelSize);
                return;
            }
            if (!this.mIsPassword || !this.mIsImeShown) {
                dimensionPixelSize = this.mNavigationBarHeight;
                updateLayoutParams(0, 0, dimensionPixelSize);
                return;
            }
            dimensionPixelSize = 0;
            updateLayoutParams(0, 0, dimensionPixelSize);
            return;
        }
        if (DeviceState.shouldEnableKeyguardScreenRotation(getContext()) || LsRune.SECURITY_FINGERPRINT_IN_DISPLAY || !this.mNeedsInput || securityMode == KeyguardSecurityModel.SecurityMode.KNOXGUARD) {
            inDisplayFingerprintHeight = DeviceState.getInDisplayFingerprintHeight();
            boolean z4 = keyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted() && !keyguardUpdateMonitor.isHiddenInputContainer();
            int i9 = keyguardUpdateMonitor.isHiddenInputContainer() ? 0 : this.mNavigationBarHeight;
            if (i == 1) {
                if (!z4) {
                    inDisplayFingerprintHeight = i9;
                }
                i2 = 0;
                i5 = i9;
            } else if (i != 3) {
                boolean z5 = (!DeviceState.isInDisplayFpSensorPositionHigh()) & z4;
                if (this.mIsPassword && this.mIsImeShown) {
                    inDisplayFingerprintHeight = 0;
                } else if (!z5) {
                    inDisplayFingerprintHeight = this.mNavigationBarHeight;
                }
                i2 = inDisplayFingerprintHeight;
                inDisplayFingerprintHeight = 0;
            } else {
                if (!z4) {
                    inDisplayFingerprintHeight = i9;
                }
                i2 = 0;
                i5 = inDisplayFingerprintHeight;
                inDisplayFingerprintHeight = i9;
            }
        } else {
            i2 = 0;
            inDisplayFingerprintHeight = 0;
        }
        updateLayoutParams(i5, inDisplayFingerprintHeight, i2);
    }
}
