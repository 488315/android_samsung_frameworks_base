package com.android.keyguard;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.UserInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Debug;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Slog;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.AdminSecondaryLockScreenController;
import com.android.keyguard.DualDarInnerLockScreenController;
import com.android.keyguard.KeyguardArrowViewController;
import com.android.keyguard.KeyguardInputViewController;
import com.android.keyguard.KeyguardPluginControllerImpl;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.biometrics.KeyguardBiometricViewController;
import com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.biometrics.FaceAuthAccessibilityDelegate;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.classifier.FalsingA11yDelegate;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.Log;
import com.android.systemui.keyguard.SecurityLog;
import com.android.systemui.keyguard.domain.interactor.KeyguardDismissTransitionInteractor;
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
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
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
import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.dar.VirtualLockUtils;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.security.mdf.MdfUtils;
import dagger.Lazy;
import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: classes.dex */
public class KeyguardSecSecurityContainerController extends KeyguardSecurityContainerController {
    public final AlarmManager mAlarmManager;
    public final AuthenticationInteractor mAuthenticationInteractor;
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
    public final ResetDeviceUtils mResetDeviceUtils;
    public final SelectedUserInteractor mSelectedUserInteractor;
    private SettingsHelper mSettingsHelper;

    /* renamed from: com.android.keyguard.KeyguardSecSecurityContainerController$3, reason: invalid class name */
    public class AnonymousClass3 implements KeyguardArrowViewCallback {
        public AnonymousClass3() {
        }
    }

    /* renamed from: com.android.keyguard.KeyguardSecSecurityContainerController$5, reason: invalid class name */
    public class AnonymousClass5 implements KeyguardSecurityCallback {
        public AnonymousClass5() {
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void dismiss(boolean z, int i, KeyguardSecurityModel.SecurityMode securityMode) {
            dismiss(z, i, false, securityMode);
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void finish(int i) {
            boolean zOnDismiss;
            Integer numValueOf = Integer.valueOf(i);
            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = KeyguardSecSecurityContainerController.this;
            Log.d("KeyguardUnlockInfo", "finish userId=%d, hasDismissAction=%d", numValueOf, Integer.valueOf(LogUtil.getInt(keyguardSecSecurityContainerController.mDismissAction)));
            ActivityStarter.OnDismissAction onDismissAction = keyguardSecSecurityContainerController.mDismissAction;
            if (onDismissAction != null) {
                zOnDismiss = onDismissAction.onDismiss();
                keyguardSecSecurityContainerController.mDismissAction = null;
                keyguardSecSecurityContainerController.mCancelAction = null;
            } else {
                zOnDismiss = false;
            }
            ViewMediatorCallback viewMediatorCallback = keyguardSecSecurityContainerController.mViewMediatorCallback;
            if (viewMediatorCallback != null) {
                if (zOnDismiss) {
                    viewMediatorCallback.keyguardDonePending(i);
                } else {
                    viewMediatorCallback.keyguardDone(i);
                }
            }
            if (keyguardSecSecurityContainerController.mUpdateMonitor.isFullscreenBouncer()) {
                KeyguardSecurityModel.SecurityMode securityMode = keyguardSecSecurityContainerController.mSecurityModel.getSecurityMode(keyguardSecSecurityContainerController.mSelectedUserInteractor.getSelectedUserId());
                if (securityMode == KeyguardSecurityModel.SecurityMode.None) {
                    ((KeyguardBouncerRepositoryImpl) ((PrimaryBouncerInteractor) keyguardSecSecurityContainerController.mPrimaryBouncerInteractor.get()).repository)._lastShownSecurityMode.setValue(securityMode);
                    keyguardSecSecurityContainerController.showSecurityScreen(securityMode);
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
            if (z) {
                int failedUnlockAttempts = keyguardSecSecurityContainerController.mUpdateMonitor.getFailedUnlockAttempts(i);
                int i3 = AnonymousClass6.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[keyguardSecSecurityContainerController.mSecurityModel.getSecurityMode(i).ordinal()];
                String str = i3 != 1 ? i3 != 2 ? i3 != 3 ? null : "2" : "3" : "1";
                if (str != null) {
                    HashMap map = new HashMap();
                    map.put("det", "1");
                    map.put(str, String.valueOf(failedUnlockAttempts + 1));
                    SystemUIAnalytics.sendEventCDLog("102", SystemUIAnalytics.EID_UNLOCK_BOUNCER, map);
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
                    boolean z2 = Settings.Secure.getIntForUser(keyguardSecSecurityContainerController.getContext().getContentResolver(), "ucm_keyguard_enforce_case", 0, i) == 1;
                    intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.password.ChooseLockGeneric$InternalActivity");
                    intent.addFlags(268435456);
                    intent.addFlags(4194304);
                    intent.addFlags(8388608);
                    if (z2) {
                        intent.addFlags(603979776);
                    }
                    keyguardSecSecurityContainerController.getContext().startActivityAsUser(intent, UserHandle.CURRENT);
                }
                if (keyguardUpdateMonitor.isForgotPasswordView()) {
                    Intent intent2 = new Intent();
                    intent2.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.password.ChooseLockGeneric$RecoveryActivity");
                    intent2.addFlags(268435456);
                    intent2.addFlags(4194304);
                    intent2.addFlags(8388608);
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
            } else if (!keyguardSecSecurityContainerController.mUpdateMonitor.isForgotPasswordView()) {
                keyguardSecSecurityContainerController.reportFailedUnlockAttempt(i, i2);
                keyguardSecSecurityContainerController.mUpdateMonitor.notifyFailedUnlockAttemptChanged();
            } else if (keyguardSecSecurityContainerController.mLockPatternUtils.getCurrentFailedPasswordAttempts(-9899) + 1 < 3) {
                LockPatternUtils lockPatternUtils = keyguardSecSecurityContainerController.mLockPatternUtils;
                if (DeviceType.isWeaverDevice()) {
                    i = -9899;
                }
                lockPatternUtils.reportFailedPasswordAttempt(i);
            } else {
                keyguardSecSecurityContainerController.mLockPatternUtils.expirePreviousData();
                keyguardSecSecurityContainerController.mViewMediatorCallback.resetKeyguard();
            }
            if (keyguardSecSecurityContainerController.mUpdateMonitor.isForgotPasswordView()) {
                return;
            }
            keyguardSecSecurityContainerController.mAuthenticationInteractor.reportUnlockAttempt(z);
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
            boolean zShowNextSecurityScreenOrFinish = keyguardSecSecurityContainerController.showNextSecurityScreenOrFinish(z, i, z2, securityMode);
            if (zShowNextSecurityScreenOrFinish && z) {
                keyguardUpdateMonitor.setUnlockingKeyguard(true);
            }
            return zShowNextSecurityScreenOrFinish;
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void onUserInput() {
        }
    }

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

    public class OnApplyWindowInsetsListener implements View.OnApplyWindowInsetsListener {
        public /* synthetic */ OnApplyWindowInsetsListener(KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, int i) {
            this();
        }

        @Override // android.view.View.OnApplyWindowInsetsListener
        public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            int iMax;
            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = KeyguardSecSecurityContainerController.this;
            c = 1;
            char c = 1;
            if (keyguardSecSecurityContainerController.isPassword(keyguardSecSecurityContainerController.mCurrentSecurityMode)) {
                int i = LsRune.SECURITY_NAVBAR_ENABLED ? 0 : windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars()).bottom;
                int i2 = windowInsets.getInsets(WindowInsets.Type.ime()).bottom;
                KeyguardSecSecurityContainerController keyguardSecSecurityContainerController2 = KeyguardSecSecurityContainerController.this;
                if (keyguardSecSecurityContainerController2.mImeBottom != i2) {
                    keyguardSecSecurityContainerController2.mImeBottom = i2;
                    keyguardSecSecurityContainerController2.mIsImeShown = i2 != 0;
                    keyguardSecSecurityContainerController2.updateLayoutMargins();
                    Context context = KeyguardSecSecurityContainerController.this.getContext();
                    int i3 = KeyguardSecSecurityContainerController.this.mImeBottom;
                    int i4 = SecurityUtils.sPINContainerBottomMargin;
                    int rotation = DeviceState.getRotation(context.getResources().getConfiguration().windowConfiguration.getRotation());
                    if (rotation != 1 && rotation != 3) {
                        c = 0;
                    }
                    int[] iArr = SecurityUtils.sImeHeight;
                    int i5 = iArr[c];
                    if (i5 == 0 || i5 != i3) {
                        iArr[c] = i3;
                    }
                    if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                        KeyguardSecSecurityContainerController keyguardSecSecurityContainerController3 = KeyguardSecSecurityContainerController.this;
                        keyguardSecSecurityContainerController3.mUpdateMonitor.updateSIPShownState(keyguardSecSecurityContainerController3.mIsImeShown);
                    }
                }
                iMax = Integer.max(i, i2);
            } else {
                if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                    int i6 = windowInsets.getInsets(WindowInsets.Type.ime()).bottom;
                    KeyguardSecSecurityContainerController keyguardSecSecurityContainerController4 = KeyguardSecSecurityContainerController.this;
                    if (keyguardSecSecurityContainerController4.mImeBottom != i6) {
                        keyguardSecSecurityContainerController4.mImeBottom = i6;
                        boolean z = i6 != 0;
                        keyguardSecSecurityContainerController4.mIsImeShown = z;
                        keyguardSecSecurityContainerController4.mUpdateMonitor.updateSIPShownState(z);
                    }
                }
                iMax = 0;
            }
            ((KeyguardSecSecurityContainer) ((ViewController) KeyguardSecSecurityContainerController.this).mView).setPadding(((KeyguardSecSecurityContainer) ((ViewController) KeyguardSecSecurityContainerController.this).mView).getPaddingLeft(), ((KeyguardSecSecurityContainer) ((ViewController) KeyguardSecSecurityContainerController.this).mView).getPaddingTop(), ((KeyguardSecSecurityContainer) ((ViewController) KeyguardSecSecurityContainerController.this).mView).getPaddingRight(), iMax);
            return windowInsets.inset(0, 0, 0, iMax);
        }

        private OnApplyWindowInsetsListener() {
        }
    }

    /* renamed from: $r8$lambda$5mPUvyQ1u7jyxIoop9We5V-6bYo, reason: not valid java name */
    public static void m959$r8$lambda$5mPUvyQ1u7jyxIoop9We5V6bYo(KeyguardSecSecurityContainerController keyguardSecSecurityContainerController) {
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("OnChangedCallback() "), keyguardSecSecurityContainerController.mIsResetCredentialShowing, "KeyguardSecSecurityContainer");
        SettingsHelper settingsHelper = keyguardSecSecurityContainerController.mSettingsHelper;
        if (settingsHelper != null && settingsHelper.isResetCredential() && keyguardSecSecurityContainerController.mIsResetCredentialShowing) {
            keyguardSecSecurityContainerController.mIsResetCredentialShowing = false;
            int selectedUserId = keyguardSecSecurityContainerController.mSelectedUserInteractor.getSelectedUserId();
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
    public KeyguardSecSecurityContainerController(KeyguardSecSecurityContainer keyguardSecSecurityContainer, AlarmManager alarmManager, AuthenticationInteractor authenticationInteractor, DevicePolicyManager devicePolicyManager, DisplayLifecycle displayLifecycle, DualDarInnerLockScreenController.Factory factory, InputMethodManager inputMethodManager, KeyguardCarrierTextViewController keyguardCarrierTextViewController, KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController, KeyguardArrowViewController.Factory factory2, KeyguardBiometricViewController keyguardBiometricViewController, KeyguardPluginControllerImpl.Factory factory3, ResetDeviceUtils resetDeviceUtils, SettingsHelper settingsHelper, AdminSecondaryLockScreenController.Factory factory4, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel keyguardSecurityModel, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, KeyguardStateController keyguardStateController, KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController, ConfigurationController configurationController, FalsingCollector falsingCollector, FalsingManager falsingManager, UserSwitcherController userSwitcherController, FeatureFlags featureFlags, GlobalSettings globalSettings, SessionTracker sessionTracker, FalsingA11yDelegate falsingA11yDelegate, TelephonyManager telephonyManager, ViewMediatorCallback viewMediatorCallback, AudioManager audioManager, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, BouncerMessageInteractor bouncerMessageInteractor, Provider provider, SelectedUserInteractor selectedUserInteractor, DeviceProvisionedController deviceProvisionedController, FaceAuthAccessibilityDelegate faceAuthAccessibilityDelegate, KeyguardDismissTransitionInteractor keyguardDismissTransitionInteractor, Lazy lazy, Executor executor, Provider provider2, Lazy lazy2) {
        super(keyguardSecSecurityContainer, factory4, lockPatternUtils, keyguardUpdateMonitor, keyguardSecurityModel, metricsLogger, uiEventLogger, keyguardStateController, keyguardSecurityViewFlipperController, configurationController, falsingCollector, falsingManager, userSwitcherController, featureFlags, globalSettings, sessionTracker, falsingA11yDelegate, telephonyManager, viewMediatorCallback, audioManager, deviceEntryFaceAuthInteractor, bouncerMessageInteractor, provider, selectedUserInteractor, deviceProvisionedController, faceAuthAccessibilityDelegate, devicePolicyManager, keyguardDismissTransitionInteractor, lazy, executor, provider2, lazy2);
        int i = 0;
        this.mImeBottom = 0;
        this.mRemainingBeforeWipe = 20;
        this.mIsDisappearAnimation = false;
        this.mOnChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticLambda1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                KeyguardSecSecurityContainerController.m959$r8$lambda$5mPUvyQ1u7jyxIoop9We5V6bYo(this.f$0);
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
        this.mKeyguardArrowViewCallback = anonymousClass3;
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
        this.mResetDeviceUtils = resetDeviceUtils;
        this.mAuthenticationInteractor = authenticationInteractor;
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
            int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
            int failedUnlockAttempts = keyguardUpdateMonitor.getFailedUnlockAttempts(selectedUserId);
            boolean zIsAutoWipe = keyguardUpdateMonitor.isAutoWipe();
            int maximumFailedPasswordsForWipe = this.mDpm.getMaximumFailedPasswordsForWipe(null, selectedUserId);
            if (maximumFailedPasswordsForWipe <= 0) {
                maximumFailedPasswordsForWipe = zIsAutoWipe ? 20 : 0;
            }
            android.util.Log.d("KeyguardSecSecurityContainer", MutableVectorKt$$ExternalSyntheticOutline0.m(maximumFailedPasswordsForWipe, failedUnlockAttempts, "doWipeOutIfMaxFailedAttemptsSinceBoot( failedAttemptsBeforeWipe = ", " , failedAttempts = ", " )"));
            if (maximumFailedPasswordsForWipe <= 0 || failedUnlockAttempts < maximumFailedPasswordsForWipe) {
                return;
            }
            Slog.e("KeyguardSecSecurityContainer", "doWipeOutIfMaxFailedAttemptsSinceBoot( Too many unlock attempts; device will be wiped! )");
            this.mResetDeviceUtils.wipeOut(failedUnlockAttempts, selectedUserId, 1);
        }
    }

    public final void onPause() {
        android.util.Log.d("KeyguardSecurityContainer", "screen off, instance " + Integer.toHexString(hashCode()) + " at " + SystemClock.uptimeMillis());
        showPrimarySecurityScreen();
        this.mAdminSecondaryLockScreenController.hide();
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            getCurrentSecurityController(new KeyguardSecurityContainerController$$ExternalSyntheticLambda0(4));
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
            keyguardUpdateMonitor.updatePermanentLock(this.mSelectedUserInteractor.getSelectedUserId());
        }
    }

    public final void onResume(int i) {
        android.util.Log.d("KeyguardSecurityContainer", "screen on, instance " + Integer.toHexString(hashCode()));
        ((KeyguardSecurityContainer) this.mView).clearFocus();
        ((KeyguardSecurityContainer) this.mView).clearAccessibilityFocus();
        ((KeyguardSecurityContainer) this.mView).requestFocus();
        ((KeyguardSecurityContainer) this.mView).requestAccessibilityFocus();
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            KeyguardSecurityContainer.ViewMode viewMode = ((KeyguardSecurityContainer) this.mView).mViewMode;
            SysUiStatsLog.write(63, viewMode instanceof KeyguardSecurityContainer.SidedSecurityMode ? ((viewMode instanceof KeyguardSecurityContainer.SidedSecurityMode) && ((KeyguardSecurityContainer.SidedSecurityMode) viewMode).isLeftAligned()) ? 3 : 4 : 2);
            getCurrentSecurityController(new KeyguardSecurityContainerController$$ExternalSyntheticLambda0(1));
        }
        KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) this.mView;
        this.mSecurityModel.getSecurityMode(super.mSelectedUserInteractor.getSelectedUserId());
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

    /* JADX WARN: Removed duplicated region for block: B:45:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x010a  */
    @Override // com.android.keyguard.KeyguardSecurityContainerController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void reportFailedUnlockAttempt(int i, int i2) {
        int i3;
        String string;
        int i4;
        int i5;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        int failedUnlockAttempts = keyguardUpdateMonitor.getFailedUnlockAttempts(i) + 1;
        boolean zIsAutoWipe = keyguardUpdateMonitor.isAutoWipe();
        KeyguardSecurityModel keyguardSecurityModel = this.mSecurityModel;
        int i6 = AnonymousClass6.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[keyguardSecurityModel.getSecurityMode(i).ordinal()];
        String str = i6 != 1 ? i6 != 2 ? i6 != 3 ? null : "2" : "3" : "1";
        if (str != null) {
            SystemUIAnalytics.sendEventCDLog("102", SystemUIAnalytics.EID_FAIL_ATTEMPT_FOR_THROTTLE_TIME, str, String.valueOf(failedUnlockAttempts));
        }
        int maximumFailedPasswordsForWipe = this.mDpm.getMaximumFailedPasswordsForWipe(null, i);
        if (maximumFailedPasswordsForWipe <= 0) {
            maximumFailedPasswordsForWipe = zIsAutoWipe ? 20 : 0;
        }
        this.mRemainingBeforeWipe = maximumFailedPasswordsForWipe > 0 ? maximumFailedPasswordsForWipe - failedUnlockAttempts : Integer.MAX_VALUE;
        boolean zIsFingerprintOptionEnabled = keyguardUpdateMonitor.isFingerprintOptionEnabled();
        boolean zIsFaceOptionEnabled = keyguardUpdateMonitor.isFaceOptionEnabled();
        if ((zIsFingerprintOptionEnabled || zIsFaceOptionEnabled) && maximumFailedPasswordsForWipe > 0) {
            if (maximumFailedPasswordsForWipe >= 10) {
                if (this.mRemainingBeforeWipe <= 5) {
                    this.mLockPatternUtils.requireStrongAuth(2, i);
                    if (zIsFaceOptionEnabled) {
                        keyguardUpdateMonitor.stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_STOPPED_USER_INPUT_ON_BOUNCER);
                    }
                }
            } else if (this.mRemainingBeforeWipe <= 2) {
                this.mLockPatternUtils.requireStrongAuth(2, i);
                if (zIsFaceOptionEnabled) {
                    keyguardUpdateMonitor.stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_STOPPED_USER_INPUT_ON_BOUNCER);
                }
            }
        }
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(maximumFailedPasswordsForWipe, "reportFailedUnlockAttempt   \n failedAttemptsBeforeWipe: ", "\n mRemainingBeforeWipe  ");
        sbM.append(this.mRemainingBeforeWipe);
        sbM.append("\n failedAttempts: ");
        sbM.append(failedUnlockAttempts);
        android.util.Log.w("KeyguardSecSecurityContainer", sbM.toString());
        if (this.mRemainingBeforeWipe < 5) {
            int profileWithMinimumFailedPasswordsForWipe = this.mDpm.getProfileWithMinimumFailedPasswordsForWipe(i);
            if (profileWithMinimumFailedPasswordsForWipe == i) {
                i4 = profileWithMinimumFailedPasswordsForWipe != 0 ? 3 : 1;
                if (this.mRemainingBeforeWipe > 0) {
                    Slog.i("KeyguardSecSecurityContainer", "Too many unlock attempts; user " + profileWithMinimumFailedPasswordsForWipe + " will be wiped!");
                    if (zIsAutoWipe) {
                        ResetDeviceUtils resetDeviceUtils = this.mResetDeviceUtils;
                        int profileWithMinimumFailedPasswordsForWipe2 = resetDeviceUtils.mLockPatternUtils.getDevicePolicyManager().getProfileWithMinimumFailedPasswordsForWipe(i4);
                        if (profileWithMinimumFailedPasswordsForWipe2 == i4) {
                            i5 = profileWithMinimumFailedPasswordsForWipe2 != 0 ? 3 : 1;
                            resetDeviceUtils.wipeOut(failedUnlockAttempts, i4, i5);
                        } else {
                            if (profileWithMinimumFailedPasswordsForWipe2 != -10000) {
                                i5 = 2;
                            }
                            resetDeviceUtils.wipeOut(failedUnlockAttempts, i4, i5);
                        }
                    } else {
                        ((KeyguardSecSecurityContainer) this.mView).showWipeDialog(failedUnlockAttempts, i4);
                    }
                } else if (!zIsAutoWipe && !CscRune.SECURITY_WARNING_WIPE_OUT_MESSAGE) {
                    this.mImm.semForceHideSoftInput();
                    ((KeyguardSecSecurityContainer) this.mView).showAlmostAtWipeDialog(failedUnlockAttempts, this.mRemainingBeforeWipe, i4);
                }
            } else {
                if (profileWithMinimumFailedPasswordsForWipe != -10000) {
                    i4 = 2;
                }
                if (this.mRemainingBeforeWipe > 0) {
                }
            }
        }
        this.mLockPatternUtils.reportFailedPasswordAttempt(i);
        KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) this.mKnoxStateMonitor;
        EdmMonitor edmMonitor = knoxStateMonitorImpl.mEdmMonitor;
        if (edmMonitor != null) {
            edmMonitor.updateFailedUnlockAttemptForDeviceDisabled();
        }
        EdmMonitor edmMonitor2 = knoxStateMonitorImpl.mEdmMonitor;
        if (edmMonitor2 != null) {
            edmMonitor2.updateFailedUnlockAttemptForProfileDisabled();
        }
        boolean z = CscRune.SECURITY_WARNING_WIPE_OUT_MESSAGE;
        KeyguardPluginControllerImpl keyguardPluginControllerImpl = this.mKeyguardPluginController;
        if (z && ((i3 = this.mRemainingBeforeWipe) == 1 || i3 == 5)) {
            UserInfo userInfo = UserManager.get(getContext()).getUserInfo(i);
            if (userInfo != null && userInfo.isPrimary()) {
                this.mImm.semForceHideSoftInput();
                int i7 = this.mRemainingBeforeWipe;
                KeyguardTextBuilder keyguardTextBuilder = KeyguardTextBuilder.getInstance(getContext());
                KeyguardSecurityModel.SecurityMode securityMode = keyguardSecurityModel.getSecurityMode(i);
                keyguardTextBuilder.getClass();
                String str2 = i7 != 1 ? SignalSeverity.NONE : "1";
                keyguardTextBuilder.updateSecurityMode(securityMode);
                int identifier = keyguardTextBuilder.mContext.getResources().getIdentifier(String.format(keyguardTextBuilder.mContext.getResources().getString(R.string.kg_device_security_remaining_frp), keyguardTextBuilder.mDeviceType, keyguardTextBuilder.mSecurityType, str2), "string", keyguardTextBuilder.mContext.getPackageName());
                if (identifier != 0) {
                    string = keyguardTextBuilder.mContext.getString(identifier, Integer.valueOf(i7));
                } else {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(identifier, "Can't find warning frp string id=", "KeyguardTextBuilder");
                    string = "";
                }
                ((KeyguardSecSecurityContainer) this.mView).showDialog(string);
                keyguardPluginControllerImpl.showWipeWarningDialog(string);
            }
        } else if (zIsAutoWipe && this.mRemainingBeforeWipe == 1) {
            this.mImm.semForceHideSoftInput();
            String warningAutoWipeMessage = KeyguardTextBuilder.getInstance(getContext()).getWarningAutoWipeMessage(failedUnlockAttempts, this.mRemainingBeforeWipe);
            if (warningAutoWipeMessage != null) {
                ((KeyguardSecSecurityContainer) this.mView).showDialog(warningAutoWipeMessage);
                keyguardPluginControllerImpl.showWipeWarningDialog(warningAutoWipeMessage);
            }
        }
        if (i2 > 0) {
            this.mLockPatternUtils.reportPasswordLockout(i2, i);
        }
        if (failedUnlockAttempts >= keyguardUpdateMonitor.getMaxFailedUnlockAttempts()) {
            keyguardUpdateMonitor.updatePermanentLock(i);
        }
    }

    public final void setOnDismissAction(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable) {
        int i = SceneContainerFlag.$r8$clinit;
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

    /* JADX WARN: Removed duplicated region for block: B:26:0x00a4 A[FALL_THROUGH] */
    @Override // com.android.keyguard.KeyguardSecurityContainerController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean showNextSecurityScreenOrFinish(boolean z, int i, boolean z2, KeyguardSecurityModel.SecurityMode securityMode) {
        boolean z3;
        boolean z4;
        boolean z5;
        int mainUserId = i;
        android.util.Log.d("KeyguardSecSecurityContainer", "showNextSecurityScreenOrFinish(" + z + ")");
        if (securityMode != KeyguardSecurityModel.SecurityMode.Invalid && securityMode != this.mCurrentSecurityMode) {
            android.util.Log.w("KeyguardSecSecurityContainer", "Attempted to invoke showNextSecurityScreenOrFinish with securityMode " + securityMode + ", but current mode is " + this.mCurrentSecurityMode);
            return false;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        boolean zIsDualDarInnerAuthRequired = keyguardUpdateMonitor.isDualDarInnerAuthRequired(mainUserId);
        KnoxStateMonitor knoxStateMonitor = this.mKnoxStateMonitor;
        boolean z6 = true;
        if (!zIsDualDarInnerAuthRequired || z2) {
            z3 = true;
        } else {
            KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) knoxStateMonitor;
            if (knoxStateMonitorImpl.mDualDarMonitor != null) {
                boolean zIsVirtualUserId = VirtualLockUtils.isVirtualUserId(mainUserId);
                KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("isVirtualUserId - userId : ", mainUserId, ", ret : ", zIsVirtualUserId, "DualDarMonitor");
                if (zIsVirtualUserId) {
                    StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(mainUserId, "Switch targetUserId ", " to ");
                    sbM.append(knoxStateMonitorImpl.getMainUserId(mainUserId));
                    android.util.Log.d("KeyguardSecSecurityContainer", sbM.toString());
                    mainUserId = knoxStateMonitorImpl.getMainUserId(mainUserId);
                    z3 = true;
                }
            }
            z3 = false;
        }
        if (MdfUtils.isMdfDisabled()) {
            Toast.makeText(getContext(), "User authentication is blocked by CC mode since it detects the device has been tampered", 1).show();
            return false;
        }
        boolean z7 = LsRune.SECURITY_SWIPE_BOUNCER;
        if (!z7 || KeyguardSecurityModel.SecurityMode.Swipe != this.mCurrentSecurityMode) {
            if (!keyguardUpdateMonitor.getUserCanSkipBouncer(mainUserId)) {
                KeyguardSecurityModel.SecurityMode securityMode2 = KeyguardSecurityModel.SecurityMode.None;
                KeyguardSecurityModel.SecurityMode securityMode3 = this.mCurrentSecurityMode;
                KeyguardSecurityModel keyguardSecurityModel = this.mSecurityModel;
                if (securityMode2 == securityMode3) {
                    KeyguardSecurityModel.SecurityMode securityMode4 = keyguardSecurityModel.getSecurityMode(mainUserId);
                    if (z7 && securityMode2 == securityMode4 && this.mIsSwipeBouncer) {
                        showSecurityScreen(KeyguardSecurityModel.SecurityMode.Swipe);
                    } else if (securityMode2 == securityMode4) {
                        SystemUIAnalytics.sendEventLog("101", SystemUIAnalytics.EID_GO_TO_SECOND_SCREEN, "2");
                        z4 = true;
                        z6 = z4;
                        z5 = true;
                    } else {
                        showSecurityScreen(securityMode4);
                    }
                    z4 = false;
                    z6 = z4;
                    z5 = true;
                } else {
                    if (z) {
                        switch (AnonymousClass6.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[securityMode3.ordinal()]) {
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                            case 11:
                            case 12:
                            case 13:
                            case 14:
                                KeyguardSecurityModel.SecurityMode securityMode5 = keyguardSecurityModel.getSecurityMode(mainUserId);
                                boolean z8 = this.mLockPatternUtils.isLockScreenDisabled(this.mSelectedUserInteractor.getSelectedUserId()) || !((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).isUserSetup(mainUserId);
                                KnoxStateMonitorImpl knoxStateMonitorImpl2 = (KnoxStateMonitorImpl) knoxStateMonitor;
                                knoxStateMonitorImpl2.getClass();
                                if (((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isForcedLock() || (knoxStateMonitorImpl2.mCustomSdkMonitor.mKnoxCustomLockScreenOverrideMode & 2) == 0) {
                                    android.util.Log.d("KeyguardSecSecurityContainer", "showNextSecurityScreenOrFinish mCurrentSecurityMode : " + this.mCurrentSecurityMode + " -> securityMode : " + securityMode5);
                                    if (securityMode5 == securityMode2) {
                                        if ((keyguardUpdateMonitor.isSimPinSecure() && z8) || (keyguardUpdateMonitor.isRemoteLockEnabled() && z8)) {
                                            showSecurityScreen(securityMode5);
                                            break;
                                        }
                                    } else {
                                        showSecurityScreen(securityMode5);
                                        break;
                                    }
                                }
                                break;
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                                z5 = true;
                                break;
                            default:
                                Objects.toString(this.mCurrentSecurityMode);
                                if (!keyguardUpdateMonitor.isForgotPasswordView()) {
                                    showPrimarySecurityScreen();
                                    break;
                                }
                                break;
                        }
                    }
                    z5 = true;
                    z6 = false;
                }
            } else if (z7 && this.mIsSwipeBouncer) {
                showSecurityScreen(KeyguardSecurityModel.SecurityMode.Swipe);
                z5 = true;
                z6 = false;
            } else {
                boolean zIsBiometricsAuthenticatedOnLock = keyguardUpdateMonitor.isBiometricsAuthenticatedOnLock();
                if (zIsBiometricsAuthenticatedOnLock || !keyguardUpdateMonitor.getUserHasTrust(mainUserId)) {
                    if (zIsBiometricsAuthenticatedOnLock) {
                        KeyguardUnlockInfo.setAuthDetailSkipBouncer(KeyguardUnlockInfo.SkipBouncerReason.BIOMETRICS_UNLOCK_LOCK_STAY);
                        SystemUIAnalytics.sendEventLog("101", SystemUIAnalytics.EID_GO_TO_SECOND_SCREEN, "4");
                    }
                    z5 = true;
                } else {
                    KeyguardUnlockInfo.setAuthDetailSkipBouncer(KeyguardUnlockInfo.SkipBouncerReason.EXTEND_LOCK);
                    SystemUIAnalytics.sendEventLog("101", SystemUIAnalytics.EID_GO_TO_SECOND_SCREEN, "3");
                }
                z5 = false;
            }
        }
        if (z6 && !z3 && keyguardUpdateMonitor.isDualDarInnerAuthRequired(mainUserId)) {
            startDisappearAnimation(new Runnable() { // from class: com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    final DualDarInnerLockScreenController dualDarInnerLockScreenController = this.f$0.mDualDarInnerLockScreenController;
                    dualDarInnerLockScreenController.getClass();
                    int innerAuthUserId = ((KnoxStateMonitorImpl) dualDarInnerLockScreenController.mKnoxStateMonitor).getInnerAuthUserId(UserHandle.getCallingUserId());
                    int credentialTypeForUser = dualDarInnerLockScreenController.mLockPatternUtils.getCredentialTypeForUser(innerAuthUserId);
                    DualDarInnerLockScreenController.AnonymousClass4 anonymousClass4 = dualDarInnerLockScreenController.mCallback;
                    KeyguardSecurityContainer keyguardSecurityContainer = dualDarInnerLockScreenController.mParent;
                    DualDarKeyguardSecurityCallback dualDarKeyguardSecurityCallback = dualDarInnerLockScreenController.mDualDarKeyguardSecurityCallback;
                    KeyguardInputViewController.Factory factory = dualDarInnerLockScreenController.mKeyguardSecurityViewControllerFactory;
                    if (credentialTypeForUser == 3) {
                        KeyguardInputView keyguardInputView = DeviceType.isTablet() ? (KeyguardInputView) dualDarInnerLockScreenController.mLayoutInflater.inflate(R.layout.keyguard_knox_dual_dar_inner_pin_view_tablet, (ViewGroup) keyguardSecurityContainer, false) : (KeyguardInputView) dualDarInnerLockScreenController.mLayoutInflater.inflate(R.layout.keyguard_knox_dual_dar_inner_pin_view, (ViewGroup) keyguardSecurityContainer, false);
                        dualDarInnerLockScreenController.mBaseView = keyguardInputView;
                        keyguardInputView.setId(View.generateViewId());
                        dualDarInnerLockScreenController.mBaseViewController = factory.create(dualDarInnerLockScreenController.mBaseView, KeyguardSecurityModel.SecurityMode.PIN, anonymousClass4);
                        ((KeyguardSecSecurityContainerController$$ExternalSyntheticLambda2) dualDarKeyguardSecurityCallback).onSecurityModeChanged(false);
                        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) dualDarInnerLockScreenController.mBaseView.getLayoutParams();
                        layoutParams.bottomToBottom = 0;
                        dualDarInnerLockScreenController.mBaseView.setLayoutParams(layoutParams);
                    } else if (credentialTypeForUser != 4) {
                        android.util.Log.d("DualDarInnerLockScreenController", "Something went wrong");
                    } else {
                        KeyguardInputView keyguardInputView2 = DeviceType.isTablet() ? (KeyguardInputView) dualDarInnerLockScreenController.mLayoutInflater.inflate(R.layout.keyguard_knox_dual_dar_inner_password_view_tablet, (ViewGroup) keyguardSecurityContainer, false) : (KeyguardInputView) dualDarInnerLockScreenController.mLayoutInflater.inflate(R.layout.keyguard_knox_dual_dar_inner_password_view, (ViewGroup) keyguardSecurityContainer, false);
                        dualDarInnerLockScreenController.mBaseView = keyguardInputView2;
                        keyguardInputView2.setId(View.generateViewId());
                        dualDarInnerLockScreenController.mBaseViewController = factory.create(dualDarInnerLockScreenController.mBaseView, KeyguardSecurityModel.SecurityMode.Password, anonymousClass4);
                        ((KeyguardSecSecurityContainerController$$ExternalSyntheticLambda2) dualDarKeyguardSecurityCallback).onSecurityModeChanged(true);
                        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) dualDarInnerLockScreenController.mBaseView.getLayoutParams();
                        layoutParams2.bottomToBottom = 0;
                        layoutParams2.startToStart = 0;
                        layoutParams2.endToEnd = 0;
                        dualDarInnerLockScreenController.mBaseView.setLayoutParams(layoutParams2);
                    }
                    KeyguardInputView keyguardInputView3 = dualDarInnerLockScreenController.mBaseView;
                    if (keyguardInputView3 != null) {
                        keyguardInputView3.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.keyguard.DualDarInnerLockScreenController.2
                            @Override // android.view.View.OnAttachStateChangeListener
                            public final void onViewAttachedToWindow(View view) {
                                DualDarInnerLockScreenController dualDarInnerLockScreenController2 = DualDarInnerLockScreenController.this;
                                dualDarInnerLockScreenController2.mUpdateMonitor.registerCallback(dualDarInnerLockScreenController2.mUpdateCallback);
                            }

                            @Override // android.view.View.OnAttachStateChangeListener
                            public final void onViewDetachedFromWindow(View view) {
                                DualDarInnerLockScreenController dualDarInnerLockScreenController2 = DualDarInnerLockScreenController.this;
                                dualDarInnerLockScreenController2.mUpdateMonitor.removeCallback(dualDarInnerLockScreenController2.mUpdateCallback);
                            }
                        });
                        dualDarInnerLockScreenController.mBaseView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.keyguard.DualDarInnerLockScreenController$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnApplyWindowInsetsListener
                            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                                int iMax;
                                DualDarInnerLockScreenController dualDarInnerLockScreenController2 = dualDarInnerLockScreenController;
                                dualDarInnerLockScreenController2.getClass();
                                if (dualDarInnerLockScreenController2.mLockPatternUtils.getCredentialTypeForUser(((KnoxStateMonitorImpl) dualDarInnerLockScreenController2.mKnoxStateMonitor).getInnerAuthUserId(UserHandle.getCallingUserId())) == 4) {
                                    int i2 = LsRune.SECURITY_NAVBAR_ENABLED ? 0 : windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars()).bottom;
                                    int i3 = windowInsets.getInsets(WindowInsets.Type.ime()).bottom;
                                    boolean zIsVisible = windowInsets.isVisible(WindowInsets.Type.ime());
                                    if (dualDarInnerLockScreenController2.mIsImeShown != zIsVisible) {
                                        dualDarInnerLockScreenController2.mIsImeShown = zIsVisible;
                                        dualDarInnerLockScreenController2.updateLayoutMargins(dualDarInnerLockScreenController2.mParent, dualDarInnerLockScreenController2.mBaseView);
                                    }
                                    iMax = Integer.max(i2, i3);
                                } else {
                                    iMax = 0;
                                }
                                view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), iMax);
                                return windowInsets.inset(0, 0, 0, iMax);
                            }
                        });
                    }
                    KeyguardInputView keyguardInputView4 = dualDarInnerLockScreenController.mBaseView;
                    if (keyguardInputView4 == null || keyguardInputView4.isAttachedToWindow()) {
                        return;
                    }
                    keyguardSecurityContainer.addView(dualDarInnerLockScreenController.mBaseView);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(keyguardSecurityContainer);
                    constraintSet.connect(dualDarInnerLockScreenController.mBaseView.getId(), 3, 0, 3);
                    constraintSet.connect(dualDarInnerLockScreenController.mBaseView.getId(), 6, 0, 6);
                    constraintSet.connect(dualDarInnerLockScreenController.mBaseView.getId(), 7, 0, 7);
                    constraintSet.connect(dualDarInnerLockScreenController.mBaseView.getId(), 4, 0, 4);
                    constraintSet.constrainHeight(dualDarInnerLockScreenController.mBaseView.getId(), 0);
                    constraintSet.constrainWidth(dualDarInnerLockScreenController.mBaseView.getId(), 0);
                    constraintSet.applyTo(keyguardSecurityContainer);
                    dualDarInnerLockScreenController.mBaseViewController.init();
                    dualDarInnerLockScreenController.mBaseViewController.reset$1();
                    dualDarInnerLockScreenController.mBaseViewController.onResume(2);
                    dualDarInnerLockScreenController.mBaseViewController.startAppearAnimation();
                    dualDarInnerLockScreenController.updateLayoutMargins(keyguardSecurityContainer, dualDarInnerLockScreenController.mBaseView);
                    dualDarInnerLockScreenController.mBaseView.setFocusable(true);
                    dualDarInnerLockScreenController.mBaseView.setFocusableInTouchMode(true);
                    dualDarInnerLockScreenController.mBaseView.requestFocus();
                    dualDarInnerLockScreenController.mUpdateMonitor.dispatchDualDarInnerLockScreenState(innerAuthUserId, true);
                }
            });
            return false;
        }
        if (z6 && !z2) {
            if (z5) {
                KeyguardUnlockInfo.setAuthDetail(this.mCurrentSecurityMode);
            }
            this.mKeyguardSecurityCallback.finish(mainUserId);
        }
        return z6;
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
                Integer numValueOf = Integer.valueOf(nextSubIdForState);
                Object obj = activeSubscriptionInfo;
                if (activeSubscriptionInfo == null) {
                    obj = "";
                }
                android.util.Log.d("EdmMonitor", String.format("isSubIdLockedByAdmin subId=%d, subInfo=%s, iccId=%s", numValueOf, obj, iccId != null ? iccId : ""));
                if (iccId == null) {
                    z = true;
                    break;
                }
                for (String str : edmMonitor.mLockedIccIdList) {
                    if (str.equals(iccId)) {
                        z = true;
                        break;
                    }
                }
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
            SharedPreferences.Editor editorEdit = keyguardArrowViewController.getContext().getSharedPreferences(SystemUIAnalytics.LOCK_PREF_NAME, 0).edit();
            int bouncerOneHandPosition = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getBouncerOneHandPosition();
            editorEdit.putString(SystemUIAnalytics.STID_LOCK_BOUNCER_POSITION, bouncerOneHandPosition != 0 ? bouncerOneHandPosition != 2 ? SystemUIAnalytics.DT_BOUNCER_POSITION_CENTER : SystemUIAnalytics.DT_BOUNCER_POSITION_RIGHT : SystemUIAnalytics.DT_BOUNCER_POSITION_LEFT).apply();
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

    /* JADX WARN: Removed duplicated region for block: B:68:0x00cb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
        this.mNavigationBarHeight = LsRune.SECURITY_NAVBAR_ENABLED ? resources.getDimensionPixelSize(android.R.dimen.secondary_waterfall_display_right_edge_size) : 0;
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
                }
            } else {
                dimensionPixelSize = (this.mIsPassword && this.mIsImeShown) ? 0 : this.mNavigationBarHeight;
            }
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
