package com.android.keyguard;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Debug;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.AdminSecondaryLockScreenController;
import com.android.keyguard.DualDarInnerLockScreenController;
import com.android.keyguard.KeyguardArrowViewController;
import com.android.keyguard.KeyguardInputViewController;
import com.android.keyguard.KeyguardPluginControllerImpl;
import com.android.keyguard.KeyguardSecSecurityContainerController.C07354;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSecurityViewFlipperController;
import com.android.keyguard.biometrics.KeyguardBiometricViewController;
import com.android.keyguard.biometrics.KeyguardUCMBiometricViewController;
import com.android.keyguard.punchhole.KeyguardPunchHoleVIView;
import com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.SideFpsController;
import com.android.systemui.classifier.FalsingA11yDelegate;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.Log;
import com.android.systemui.keyguard.SecurityLog;
import com.android.systemui.keyguard.domain.interactor.KeyguardFaceAuthInteractor;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.GlobalSettings;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.dar.VirtualLockUtils;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.security.mdf.MdfUtils;
import com.samsung.android.service.reactive.ReactiveServiceManager;
import com.sec.ims.configuration.DATA;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardSecSecurityContainerController extends KeyguardSecurityContainerController {
    public final AlarmManager mAlarmManager;
    public final KeyguardBiometricViewController mBiometricViewController;
    public final C07321 mConfigurationListener;
    public final DevicePolicyManager mDpm;
    public final DualDarInnerLockScreenController mDualDarInnerLockScreenController;
    public int mFactoryResetProtectionType;
    public int mImeBottom;
    public final InputMethodManager mImm;
    public boolean mIsDisappearAnimation;
    public boolean mIsImeShown;
    public boolean mIsPassword;
    public boolean mIsResetCredentialShowing;
    public boolean mIsSwipeBouncer;
    public final C07332 mKeyguardArrowViewCallback;
    public final KeyguardArrowViewController mKeyguardArrowViewController;
    public final KeyguardCarrierTextViewController mKeyguardCarrierTextViewController;
    public final KeyguardPluginControllerImpl mKeyguardPluginController;
    public final KeyguardPunchHoleVIViewController mKeyguardPunchHoleVIViewController;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public int mNavigationBarHeight;
    public boolean mNeedsInput;
    public final KeyguardSecSecurityContainerController$$ExternalSyntheticLambda2 mOnChangedCallback;
    public LockscreenCredential mPrevCredential;
    public int mRemainingBeforeWipe;
    public final KeyguardSecSecurityContainerController$$ExternalSyntheticLambda1 mRotationConsumer;
    public final SecRotationWatcher mRotationWatcher;
    public final SettingsHelper mSettingsHelper;
    public final KeyguardUCMBiometricViewController mUCMBiometricViewController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardSecSecurityContainerController$2 */
    public final class C07332 implements KeyguardArrowViewCallback {
        public C07332() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardSecSecurityContainerController$4 */
    public final class C07354 implements KeyguardSecurityCallback {
        public C07354() {
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void dismiss(int i, KeyguardSecurityModel.SecurityMode securityMode, boolean z) {
            dismiss(z, i, false, securityMode);
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void finish(int i, boolean z) {
            Object[] objArr = new Object[3];
            Map map = LogUtil.beginTimes;
            boolean z2 = false;
            objArr[0] = Integer.valueOf(z ? 1 : 0);
            objArr[1] = Integer.valueOf(i);
            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = KeyguardSecSecurityContainerController.this;
            objArr[2] = Integer.valueOf(keyguardSecSecurityContainerController.mDismissAction == null ? 0 : 1);
            Log.m139d("KeyguardUnlockInfo", "finish fromPrimaryAuth=%d, userId=%d, hasDismissAction=%d", objArr);
            ActivityStarter.OnDismissAction onDismissAction = keyguardSecSecurityContainerController.mDismissAction;
            if (onDismissAction != null) {
                z2 = onDismissAction.onDismiss();
                keyguardSecSecurityContainerController.mDismissAction = null;
                keyguardSecSecurityContainerController.mCancelAction = null;
            }
            ViewMediatorCallback viewMediatorCallback = keyguardSecSecurityContainerController.mViewMediatorCallback;
            if (viewMediatorCallback != null) {
                if (z2) {
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
                }
                LockPatternUtils lockPatternUtils = keyguardSecSecurityContainerController.mLockPatternUtils;
                if (lockPatternUtils.getCurrentFailedPasswordAttempts(-9998) + 1 >= 3) {
                    lockPatternUtils.expirePreviousData();
                    keyguardSecSecurityContainerController.mViewMediatorCallback.resetKeyguard();
                    return;
                } else {
                    if (DeviceType.isWeaverDevice()) {
                        i = -9998;
                    }
                    lockPatternUtils.reportFailedPasswordAttempt(i);
                    return;
                }
            }
            int failedUnlockAttempts = keyguardSecSecurityContainerController.mUpdateMonitor.getFailedUnlockAttempts(i);
            int i3 = AbstractC07365.f207xdc0e830a[keyguardSecSecurityContainerController.mSecurityModel.getSecurityMode(i).ordinal()];
            String str = i3 != 1 ? i3 != 2 ? i3 != 3 ? null : "2" : DATA.DM_FIELD_INDEX.PUBLIC_USER_ID : "1";
            if (failedUnlockAttempts > 0 && str != null) {
                SystemUIAnalytics.sendEventCDLog(DATA.DM_FIELD_INDEX.VOLTE_DOMAIN_UI_SHOW, "1201", str, String.valueOf(failedUnlockAttempts + 1));
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
                Settings.Secure.putInt(keyguardSecSecurityContainerController.getContext().getContentResolver(), "reset_credential_from_previous", 0);
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
            if ((z3 || ((KeyguardViewMediator) ((KeyguardUnlockAnimationController) ((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.get(KeyguardStateController.class))).mUnlockAnimationControllerLazy.get()).keyguardViewMediator.get()).isAnimatingBetweenKeyguardAndSurfaceBehind()) && keyguardUpdateMonitor.isFaceOptionEnabled()) {
                android.util.Log.d("KeyguardSecSecurityContainer", "keyguard is already goingAway and face enabled. cancel dismiss");
                return false;
            }
            if (LsRune.SECURITY_SIM_PERM_DISABLED && keyguardUpdateMonitor.isIccBlockedPermanently()) {
                android.util.Log.d("KeyguardSecSecurityContainer", "dismiss failed. Permanent state.");
                return false;
            }
            if (z) {
                SecurityLog.m143d("KeyguardSecSecurityContainer", "dismiss caller\n" + Debug.getCallers(10, "  "));
            }
            if (!z) {
                if ((KeyguardUnlockInfo.unlockTrigger == KeyguardUnlockInfo.UnlockTrigger.TRIGGER_UNKNOWN) && DeviceType.getDebugLevel() != 0) {
                    android.util.Log.d("KeyguardUnlockInfo", "unknown trigger caller\n" + Debug.getCallers(15, "  "));
                }
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardSecSecurityContainerController$5 */
    public abstract /* synthetic */ class AbstractC07365 {

        /* renamed from: $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode */
        public static final /* synthetic */ int[] f207xdc0e830a;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            f207xdc0e830a = iArr;
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f207xdc0e830a[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f207xdc0e830a[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f207xdc0e830a[KeyguardSecurityModel.SecurityMode.FMM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f207xdc0e830a[KeyguardSecurityModel.SecurityMode.SmartcardPIN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f207xdc0e830a[KeyguardSecurityModel.SecurityMode.Permanent.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f207xdc0e830a[KeyguardSecurityModel.SecurityMode.AdminLock.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f207xdc0e830a[KeyguardSecurityModel.SecurityMode.SKTCarrierLock.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f207xdc0e830a[KeyguardSecurityModel.SecurityMode.SKTCarrierPassword.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f207xdc0e830a[KeyguardSecurityModel.SecurityMode.RMM.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f207xdc0e830a[KeyguardSecurityModel.SecurityMode.KNOXGUARD.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f207xdc0e830a[KeyguardSecurityModel.SecurityMode.SimPin.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f207xdc0e830a[KeyguardSecurityModel.SecurityMode.SimPuk.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f207xdc0e830a[KeyguardSecurityModel.SecurityMode.SimPerso.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OnApplyWindowInsetsListener implements View.OnApplyWindowInsetsListener {
        public /* synthetic */ OnApplyWindowInsetsListener(KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, int i) {
            this();
        }

        @Override // android.view.View.OnApplyWindowInsetsListener
        public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            int i;
            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = KeyguardSecSecurityContainerController.this;
            r0 = 1;
            char c = 1;
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
                    int rotation = DeviceState.getRotation(context.getResources().getConfiguration().windowConfiguration.getRotation());
                    if (rotation != 1 && rotation != 3) {
                        c = 0;
                    }
                    int[] iArr = SecurityUtils.sImeHeight;
                    int i5 = iArr[c];
                    if (i5 == 0 || i5 != i4) {
                        iArr[c] = i4;
                    }
                    if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                        KeyguardSecSecurityContainerController keyguardSecSecurityContainerController3 = KeyguardSecSecurityContainerController.this;
                        keyguardSecSecurityContainerController3.mUpdateMonitor.updateSIPShownState(keyguardSecSecurityContainerController3.mIsImeShown);
                    }
                }
                i = Integer.max(i2, i3);
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
                i = 0;
            }
            View view2 = KeyguardSecSecurityContainerController.this.mView;
            ((KeyguardSecSecurityContainer) view2).setPadding(((KeyguardSecSecurityContainer) view2).getPaddingLeft(), ((KeyguardSecSecurityContainer) KeyguardSecSecurityContainerController.this.mView).getPaddingTop(), ((KeyguardSecSecurityContainer) KeyguardSecSecurityContainerController.this.mView).getPaddingRight(), i);
            return windowInsets.inset(0, 0, 0, i);
        }

        private OnApplyWindowInsetsListener() {
        }
    }

    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.keyguard.KeyguardSecSecurityContainerController$1] */
    public KeyguardSecSecurityContainerController(KeyguardSecSecurityContainer keyguardSecSecurityContainer, AdminSecondaryLockScreenController.Factory factory, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel keyguardSecurityModel, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, KeyguardStateController keyguardStateController, KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController, ConfigurationController configurationController, FalsingCollector falsingCollector, FalsingManager falsingManager, UserSwitcherController userSwitcherController, DeviceProvisionedController deviceProvisionedController, FeatureFlags featureFlags, GlobalSettings globalSettings, SessionTracker sessionTracker, Optional<SideFpsController> optional, FalsingA11yDelegate falsingA11yDelegate, TelephonyManager telephonyManager, ViewMediatorCallback viewMediatorCallback, AudioManager audioManager, KeyguardFaceAuthInteractor keyguardFaceAuthInteractor, DevicePolicyManager devicePolicyManager, InputMethodManager inputMethodManager, AlarmManager alarmManager, SecRotationWatcher secRotationWatcher, SettingsHelper settingsHelper, KeyguardCarrierTextViewController keyguardCarrierTextViewController, KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController, KeyguardArrowViewController.Factory factory2, KeyguardBiometricViewController keyguardBiometricViewController, KeyguardPluginControllerImpl.Factory factory3, DualDarInnerLockScreenController.Factory factory4) {
        super(keyguardSecSecurityContainer, factory, lockPatternUtils, keyguardUpdateMonitor, keyguardSecurityModel, metricsLogger, uiEventLogger, keyguardStateController, keyguardSecurityViewFlipperController, configurationController, falsingCollector, falsingManager, userSwitcherController, deviceProvisionedController, featureFlags, globalSettings, sessionTracker, optional, falsingA11yDelegate, telephonyManager, viewMediatorCallback, audioManager, keyguardFaceAuthInteractor);
        int i = 0;
        this.mImeBottom = 0;
        this.mRotationConsumer = new IntConsumer() { // from class: com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticLambda1
            @Override // java.util.function.IntConsumer
            public final void accept(int i2) {
                KeyguardSecSecurityContainerController.this.updateLayoutMargins(i2);
            }
        };
        this.mRemainingBeforeWipe = 20;
        this.mFactoryResetProtectionType = 0;
        this.mIsDisappearAnimation = false;
        this.mOnChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticLambda2
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                AlarmManager alarmManager2;
                StringBuilder sb = new StringBuilder("OnChangedCallback() ");
                KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = KeyguardSecSecurityContainerController.this;
                ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, keyguardSecSecurityContainerController.mIsResetCredentialShowing, "KeyguardSecSecurityContainer");
                SettingsHelper settingsHelper2 = keyguardSecSecurityContainerController.mSettingsHelper;
                if (settingsHelper2 != null) {
                    if ((settingsHelper2.mItemLists.get("reset_credential_from_previous").getIntValue() == 1) && keyguardSecSecurityContainerController.mIsResetCredentialShowing) {
                        keyguardSecSecurityContainerController.mIsResetCredentialShowing = false;
                        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                        keyguardSecSecurityContainerController.mUpdateMonitor.clearFailedUnlockAttempts(true);
                        keyguardSecSecurityContainerController.mLockPatternUtils.reportSuccessfulPasswordAttempt(currentUser);
                        PendingIntent broadcast = PendingIntent.getBroadcast(keyguardSecSecurityContainerController.getContext(), 0, new Intent("com.samsung.keyguard.BIOMETRIC_LOCKOUT_RESET"), 603979776);
                        if (broadcast != null && (alarmManager2 = keyguardSecSecurityContainerController.mAlarmManager) != null) {
                            android.util.Log.d("KeyguardSecSecurityContainer", "Alarm manager have ACTION_BIOMETRIC_LOCKOUT_RESET then will be canceled");
                            alarmManager2.cancel(broadcast);
                            broadcast.cancel();
                        }
                        Settings.Secure.putInt(keyguardSecSecurityContainerController.getContext().getContentResolver(), "reset_credential_from_previous", 0);
                        keyguardSecSecurityContainerController.new C07354().dismiss(true, KeyguardUpdateMonitor.getCurrentUser(), false, keyguardSecSecurityContainerController.mCurrentSecurityMode);
                    }
                }
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
        C07332 c07332 = new C07332();
        this.mKeyguardArrowViewCallback = c07332;
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardSecSecurityContainerController.3
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDualDarInnerLockScreenStateChanged(boolean z) {
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
        this.mRotationWatcher = secRotationWatcher;
        this.mSettingsHelper = settingsHelper;
        this.mKeyguardCarrierTextViewController = keyguardCarrierTextViewController;
        this.mKeyguardPunchHoleVIViewController = LsRune.SECURITY_PUNCH_HOLE_FACE_VI ? keyguardPunchHoleVIViewController : null;
        this.mKeyguardArrowViewController = LsRune.SECURITY_ARROW_VIEW ? new KeyguardArrowViewController(factory2.mView, c07332, factory2.mConfigurationController, factory2.mKeyguardUpdateMonitor, factory2.mViewMediatorCallback) : null;
        this.mBiometricViewController = keyguardBiometricViewController;
        this.mUCMBiometricViewController = new KeyguardUCMBiometricViewController((KeyguardSecSecurityContainer) this.mView);
        this.mKeyguardPluginController = new KeyguardPluginControllerImpl(factory3.mContext, factory3.mViewMediatorCallback, factory3.mDesktopManager, factory3.mSubScreenManager, this.mKeyguardSecurityCallback, factory3.mLatencyTracker, factory3.mLockPatternUtils, factory3.mKeyguardUpdateMonitor, 0);
        ((KeyguardSecSecurityContainer) this.mView).setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener(this, i));
        this.mKnoxStateMonitor = (KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class);
        if (factory4 != null) {
            this.mDualDarInnerLockScreenController = new DualDarInnerLockScreenController(factory4.mContext, factory4.mParent, this, factory4.mUpdateMonitor, this.mKeyguardSecurityCallback, new KeyguardSecSecurityContainerController$$ExternalSyntheticLambda3(this), factory4.mHandler, factory4.mLayoutInflater, factory4.mKeyguardSecurityViewControllerFactory);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0026, code lost:
    
        if ((getContext().getResources().getConfiguration().semDisplayDeviceType == 0) != false) goto L13;
     */
    @Override // com.android.keyguard.KeyguardSecurityContainerController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void configureMode() {
        int i = 0;
        if (SecurityUtils.isArrowViewSupported(this.mCurrentSecurityMode)) {
            if (!DeviceType.isTablet()) {
                if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
                }
            }
            i = 3;
        }
        ((KeyguardSecSecurityContainer) this.mView).initMode(i, this.mGlobalSettings, this.mFalsingManager, this.mUserSwitcherController, null, this.mFalsingA11yDelegate);
    }

    @Override // com.android.keyguard.KeyguardSecurityContainerController
    public final KeyguardSecurityCallback getSecurityCallback() {
        return new C07354();
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
            keyguardPunchHoleVIViewController.mIsBouncerVI = true;
            ((KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController.mView).TAG = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder(), ((KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController.mView).TAG, "_Bouncer");
            keyguardPunchHoleVIViewController.init();
        }
        if (LsRune.SECURITY_ARROW_VIEW) {
            this.mKeyguardArrowViewController.init();
        }
        this.mBiometricViewController.init();
        if (LsRune.SECURITY_WARNING_WIPE_OUT_MESSAGE) {
            ReactiveServiceManager reactiveServiceManager = new ReactiveServiceManager(getContext());
            if (reactiveServiceManager.isConnected()) {
                this.mFactoryResetProtectionType = reactiveServiceManager.getServiceSupport();
            }
            android.util.Log.d("KeyguardSecSecurityContainer", "updateFactoryResetProtectionType( " + this.mFactoryResetProtectionType + " )");
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
            int failedUnlockAttempts = keyguardUpdateMonitor.getFailedUnlockAttempts(currentUser);
            boolean isAutoWipe = keyguardUpdateMonitor.isAutoWipe();
            int maximumFailedPasswordsForWipe = this.mDpm.getMaximumFailedPasswordsForWipe(null, currentUser);
            if (maximumFailedPasswordsForWipe <= 0) {
                maximumFailedPasswordsForWipe = isAutoWipe ? 20 : 0;
            }
            android.util.Log.d("KeyguardSecSecurityContainer", SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("doWipeOutIfMaxFailedAttemptsSinceBoot( failedAttemptsBeforeWipe = ", maximumFailedPasswordsForWipe, " , failedAttempts = ", failedUnlockAttempts, " )"));
            if (maximumFailedPasswordsForWipe <= 0 || failedUnlockAttempts < maximumFailedPasswordsForWipe) {
                return;
            }
            Slog.e("KeyguardSecSecurityContainer", "doWipeOutIfMaxFailedAttemptsSinceBoot( Too many unlock attempts; device will be wiped! )");
            Context context = getContext();
            if (ResetDeviceUtils.sResetDeviceUtils == null) {
                ResetDeviceUtils.sResetDeviceUtils = new ResetDeviceUtils(context);
            }
            ResetDeviceUtils.sResetDeviceUtils.wipeOut(failedUnlockAttempts, 1);
        }
    }

    public final void onPause() {
        android.util.Log.d("KeyguardSecurityContainer", String.format("screen off, instance %s at %s", Integer.toHexString(hashCode()), Long.valueOf(SystemClock.uptimeMillis())));
        showPrimarySecurityScreen();
        this.mAdminSecondaryLockScreenController.hide();
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            getCurrentSecurityController(new KeyguardSecurityContainerController$$ExternalSyntheticLambda4(3));
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
            keyguardUpdateMonitor.updatePermanentLock(KeyguardUpdateMonitor.getCurrentUser());
        }
    }

    public final void onResume(int i) {
        int i2;
        android.util.Log.d("KeyguardSecurityContainer", "screen on, instance " + Integer.toHexString(hashCode()));
        ((KeyguardSecurityContainer) this.mView).requestFocus();
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            KeyguardSecurityContainer.ViewMode viewMode = ((KeyguardSecurityContainer) this.mView).mViewMode;
            if (viewMode instanceof KeyguardSecurityContainer.SidedSecurityMode) {
                i2 = (viewMode instanceof KeyguardSecurityContainer.SidedSecurityMode) && ((KeyguardSecurityContainer.SidedSecurityMode) viewMode).isLeftAligned() ? 3 : 4;
            } else {
                i2 = 2;
            }
            SysUiStatsLog.write(63, i2);
            getCurrentSecurityController(new KeyguardSecurityContainerController$$ExternalSyntheticLambda6(1, 0));
        }
        KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) this.mView;
        this.mSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser());
        boolean z = ((KeyguardStateControllerImpl) this.mKeyguardStateController).mFaceAuthEnabled;
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
        if (i <= 60 || (keyguardSecurityViewFlipper = ((KeyguardSecSecurityContainer) this.mView).mSecurityViewFlipper) == null) {
            return;
        }
        keyguardSecurityViewFlipper.removeAllViews();
    }

    @Override // com.android.keyguard.KeyguardSecurityContainerController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        this.mUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        this.mRotationWatcher.addCallback(this.mRotationConsumer);
        this.mSettingsHelper.registerCallback(this.mOnChangedCallback, Settings.Secure.getUriFor("reset_credential_from_previous"));
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityContainerController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        this.mUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        this.mRotationWatcher.removeCallback(this.mRotationConsumer);
        this.mSettingsHelper.unregisterCallback(this.mOnChangedCallback);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        }
    }

    public final void reinflateViewFlipper(KeyguardSecurityViewFlipperController.OnViewInflatedCallback onViewInflatedCallback) {
        if (onViewInflatedCallback != null) {
            getCurrentSecurityController(onViewInflatedCallback);
            return;
        }
        KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController = this.mSecurityViewFlipperController;
        ((KeyguardSecurityViewFlipper) keyguardSecurityViewFlipperController.mView).removeAllViews();
        ((ArrayList) keyguardSecurityViewFlipperController.mChildren).clear();
        keyguardSecurityViewFlipperController.asynchronouslyInflateView(this.mCurrentSecurityMode, this.mKeyguardSecurityCallback, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x0098, code lost:
    
        if (r5 != (-10000)) goto L47;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00b3  */
    @Override // com.android.keyguard.KeyguardSecurityContainerController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void reportFailedUnlockAttempt(int i, int i2) {
        int i3;
        String str;
        String string;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        int failedUnlockAttempts = keyguardUpdateMonitor.getFailedUnlockAttempts(i) + 1;
        boolean isAutoWipe = keyguardUpdateMonitor.isAutoWipe();
        KeyguardSecurityModel keyguardSecurityModel = this.mSecurityModel;
        int i4 = AbstractC07365.f207xdc0e830a[keyguardSecurityModel.getSecurityMode(i).ordinal()];
        int i5 = 2;
        String str2 = i4 != 1 ? i4 != 2 ? i4 != 3 ? null : "2" : DATA.DM_FIELD_INDEX.PUBLIC_USER_ID : "1";
        if (str2 != null) {
            SystemUIAnalytics.sendEventCDLog(DATA.DM_FIELD_INDEX.VOLTE_DOMAIN_UI_SHOW, "1200", str2, String.valueOf(failedUnlockAttempts));
        }
        DevicePolicyManager devicePolicyManager = this.mDpm;
        int maximumFailedPasswordsForWipe = devicePolicyManager.getMaximumFailedPasswordsForWipe(null, i);
        if (maximumFailedPasswordsForWipe <= 0) {
            maximumFailedPasswordsForWipe = isAutoWipe ? 20 : 0;
        }
        this.mRemainingBeforeWipe = maximumFailedPasswordsForWipe > 0 ? maximumFailedPasswordsForWipe - failedUnlockAttempts : Integer.MAX_VALUE;
        boolean isFingerprintOptionEnabled = keyguardUpdateMonitor.isFingerprintOptionEnabled();
        boolean isFaceOptionEnabled = keyguardUpdateMonitor.isFaceOptionEnabled();
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        if ((isFingerprintOptionEnabled || isFaceOptionEnabled) && maximumFailedPasswordsForWipe > 0) {
            if (maximumFailedPasswordsForWipe >= 10) {
                if (this.mRemainingBeforeWipe <= 5) {
                    lockPatternUtils.requireStrongAuth(2, i);
                    if (isFaceOptionEnabled) {
                        keyguardUpdateMonitor.stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_STOPPED_USER_INPUT_ON_BOUNCER);
                    }
                }
            } else if (this.mRemainingBeforeWipe <= 2) {
                lockPatternUtils.requireStrongAuth(2, i);
                if (isFaceOptionEnabled) {
                    keyguardUpdateMonitor.stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_STOPPED_USER_INPUT_ON_BOUNCER);
                }
            }
        }
        int i6 = this.mRemainingBeforeWipe;
        InputMethodManager inputMethodManager = this.mImm;
        if (i6 < 5) {
            int profileWithMinimumFailedPasswordsForWipe = devicePolicyManager.getProfileWithMinimumFailedPasswordsForWipe(i);
            if (profileWithMinimumFailedPasswordsForWipe == i) {
                if (profileWithMinimumFailedPasswordsForWipe != 0) {
                    i5 = 3;
                    if (this.mRemainingBeforeWipe > 0) {
                        Slog.i("KeyguardSecSecurityContainer", "Too many unlock attempts; user " + profileWithMinimumFailedPasswordsForWipe + " will be wiped!");
                        Context context = getContext();
                        if (ResetDeviceUtils.sResetDeviceUtils == null) {
                            ResetDeviceUtils.sResetDeviceUtils = new ResetDeviceUtils(context);
                        }
                        ResetDeviceUtils.sResetDeviceUtils.wipeOut(failedUnlockAttempts, i5);
                    } else if (!isAutoWipe && !LsRune.SECURITY_WARNING_WIPE_OUT_MESSAGE) {
                        inputMethodManager.semForceHideSoftInput();
                        ((KeyguardSecSecurityContainer) this.mView).showAlmostAtWipeDialog(failedUnlockAttempts, this.mRemainingBeforeWipe, i5);
                    }
                }
                i5 = 1;
                if (this.mRemainingBeforeWipe > 0) {
                }
            }
        }
        lockPatternUtils.reportFailedPasswordAttempt(i);
        KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) this.mKnoxStateMonitor;
        EdmMonitor edmMonitor = knoxStateMonitorImpl.mEdmMonitor;
        if (edmMonitor != null) {
            edmMonitor.updateFailedUnlockAttemptForDeviceDisabled();
        }
        EdmMonitor edmMonitor2 = knoxStateMonitorImpl.mEdmMonitor;
        if (edmMonitor2 != null) {
            edmMonitor2.updateFailedUnlockAttemptForProfileDisabled();
        }
        boolean z = LsRune.SECURITY_WARNING_WIPE_OUT_MESSAGE;
        KeyguardPluginControllerImpl keyguardPluginControllerImpl = this.mKeyguardPluginController;
        if (z && ((i3 = this.mRemainingBeforeWipe) == 1 || i3 == 5)) {
            UserInfo userInfo = UserManager.get(getContext()).getUserInfo(i);
            if (userInfo != null && userInfo.isPrimary()) {
                inputMethodManager.semForceHideSoftInput();
                int i7 = this.mRemainingBeforeWipe;
                KeyguardTextBuilder keyguardTextBuilder = KeyguardTextBuilder.getInstance(getContext());
                if (this.mFactoryResetProtectionType == 1) {
                    KeyguardSecurityModel.SecurityMode securityMode = keyguardSecurityModel.getSecurityMode(i);
                    keyguardTextBuilder.getClass();
                    str = i7 != 1 ? "none" : "1";
                    keyguardTextBuilder.updateSecurityMode(securityMode);
                    Context context2 = keyguardTextBuilder.mContext;
                    int identifier = context2.getResources().getIdentifier(String.format(context2.getResources().getString(R.string.kg_device_security_remaining_reactivation), keyguardTextBuilder.mDeviceType, keyguardTextBuilder.mSecurityType, str), "string", context2.getPackageName());
                    if (identifier != 0) {
                        string = context2.getString(identifier, Integer.valueOf(i7));
                        ((KeyguardSecSecurityContainer) this.mView).showDialog(string);
                        keyguardPluginControllerImpl.showWipeWarningDialog(string);
                    } else {
                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("Can't find warning reactivation string id=", identifier, "KeyguardTextBuilder");
                        string = "";
                        ((KeyguardSecSecurityContainer) this.mView).showDialog(string);
                        keyguardPluginControllerImpl.showWipeWarningDialog(string);
                    }
                } else {
                    KeyguardSecurityModel.SecurityMode securityMode2 = keyguardSecurityModel.getSecurityMode(i);
                    keyguardTextBuilder.getClass();
                    str = i7 != 1 ? "none" : "1";
                    keyguardTextBuilder.updateSecurityMode(securityMode2);
                    Context context3 = keyguardTextBuilder.mContext;
                    int identifier2 = context3.getResources().getIdentifier(String.format(context3.getResources().getString(R.string.kg_device_security_remaining_frp), keyguardTextBuilder.mDeviceType, keyguardTextBuilder.mSecurityType, str), "string", context3.getPackageName());
                    if (identifier2 != 0) {
                        string = context3.getString(identifier2, Integer.valueOf(i7));
                        ((KeyguardSecSecurityContainer) this.mView).showDialog(string);
                        keyguardPluginControllerImpl.showWipeWarningDialog(string);
                    } else {
                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("Can't find warning frp string id=", identifier2, "KeyguardTextBuilder");
                        string = "";
                        ((KeyguardSecSecurityContainer) this.mView).showDialog(string);
                        keyguardPluginControllerImpl.showWipeWarningDialog(string);
                    }
                }
            }
        } else if (isAutoWipe && this.mRemainingBeforeWipe == 1) {
            inputMethodManager.semForceHideSoftInput();
            String warningAutoWipeMessage = KeyguardTextBuilder.getInstance(getContext()).getWarningAutoWipeMessage(failedUnlockAttempts, this.mRemainingBeforeWipe);
            if (warningAutoWipeMessage != null) {
                ((KeyguardSecSecurityContainer) this.mView).showDialog(warningAutoWipeMessage);
                keyguardPluginControllerImpl.showWipeWarningDialog(warningAutoWipeMessage);
            }
        }
        if (i2 > 0) {
            lockPatternUtils.reportPasswordLockout(i2, i);
        }
        if (failedUnlockAttempts >= keyguardUpdateMonitor.getMaxFailedUnlockAttempts()) {
            keyguardUpdateMonitor.updatePermanentLock(i);
        }
    }

    public final void setOnDismissAction(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable) {
        Runnable runnable2 = this.mCancelAction;
        if (runnable2 != null) {
            runnable2.run();
            this.mCancelAction = null;
        }
        this.mDismissAction = onDismissAction;
        this.mCancelAction = runnable;
        this.mUpdateMonitor.setDismissActionExist(onDismissAction != null);
    }

    @Override // com.android.keyguard.KeyguardSecurityContainerController
    public final void showMessage(CharSequence charSequence, ColorStateList colorStateList, boolean z) {
        super.showMessage(charSequence, colorStateList, z);
        if (this.mCurrentSecurityMode == KeyguardSecurityModel.SecurityMode.None || !AccessibilityManager.getInstance(getContext()).isTouchExplorationEnabled()) {
            return;
        }
        ((KeyguardSecSecurityContainer) this.mView).announceForAccessibility(charSequence);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x017c  */
    @Override // com.android.keyguard.KeyguardSecurityContainerController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean showNextSecurityScreenOrFinish(boolean z, int i, boolean z2, KeyguardSecurityModel.SecurityMode securityMode) {
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        android.util.Log.d("KeyguardSecSecurityContainer", "showNextSecurityScreenOrFinish(" + z + ")");
        if (securityMode != KeyguardSecurityModel.SecurityMode.Invalid && securityMode != this.mCurrentSecurityMode) {
            android.util.Log.w("KeyguardSecSecurityContainer", "Attempted to invoke showNextSecurityScreenOrFinish with securityMode " + securityMode + ", but current mode is " + this.mCurrentSecurityMode);
            return false;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        boolean isDualDarInnerAuthRequired = keyguardUpdateMonitor.isDualDarInnerAuthRequired(i);
        KnoxStateMonitor knoxStateMonitor = this.mKnoxStateMonitor;
        boolean z8 = true;
        if (isDualDarInnerAuthRequired && !z2) {
            KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) knoxStateMonitor;
            if (knoxStateMonitorImpl.mDualDarMonitor != null) {
                boolean isVirtualUserId = VirtualLockUtils.isVirtualUserId(i);
                AbstractC0731x5bb8a836.m72m("isVirtualUserId - userId : ", i, ", ret : ", isVirtualUserId, "DualDarMonitor");
                if (isVirtualUserId) {
                    z7 = true;
                    if (z7) {
                        z3 = false;
                        if (!MdfUtils.isMdfDisabled()) {
                            Toast.makeText(getContext(), "User authentication is blocked by CC mode since it detects the device has been tampered", 1).show();
                            return false;
                        }
                        boolean z9 = LsRune.SECURITY_SWIPE_BOUNCER;
                        if (!z9 || KeyguardSecurityModel.SecurityMode.Swipe != this.mCurrentSecurityMode) {
                            if (!keyguardUpdateMonitor.getUserCanSkipBouncer(i)) {
                                KeyguardSecurityModel.SecurityMode securityMode2 = KeyguardSecurityModel.SecurityMode.None;
                                KeyguardSecurityModel.SecurityMode securityMode3 = this.mCurrentSecurityMode;
                                KeyguardSecurityModel keyguardSecurityModel = this.mSecurityModel;
                                if (securityMode2 == securityMode3) {
                                    KeyguardSecurityModel.SecurityMode securityMode4 = keyguardSecurityModel.getSecurityMode(i);
                                    if (z9 && securityMode2 == securityMode4 && this.mIsSwipeBouncer) {
                                        showSecurityScreen(KeyguardSecurityModel.SecurityMode.Swipe);
                                    } else if (securityMode2 == securityMode4) {
                                        SystemUIAnalytics.sendEventLog(DATA.DM_FIELD_INDEX.UT_APN_NAME, "1001", "2");
                                        z6 = true;
                                        z5 = false;
                                        z8 = z6;
                                        z4 = true;
                                    } else {
                                        showSecurityScreen(securityMode4);
                                    }
                                    z6 = false;
                                    z5 = false;
                                    z8 = z6;
                                    z4 = true;
                                } else {
                                    if (z) {
                                        switch (AbstractC07365.f207xdc0e830a[securityMode3.ordinal()]) {
                                            case 1:
                                            case 2:
                                            case 3:
                                            case 4:
                                            case 5:
                                                z4 = true;
                                                z5 = true;
                                                break;
                                            case 6:
                                            case 7:
                                            case 8:
                                            case 9:
                                            case 10:
                                            case 11:
                                            case 12:
                                            case 13:
                                            case 14:
                                                KeyguardSecurityModel.SecurityMode securityMode5 = keyguardSecurityModel.getSecurityMode(i);
                                                boolean isLockScreenDisabled = this.mLockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser());
                                                if (!((KnoxStateMonitorImpl) knoxStateMonitor).isLockScreenDisabledbyKNOX() && ((securityMode5 != securityMode2 && !isLockScreenDisabled) || (keyguardUpdateMonitor.isSimPinSecure() && isLockScreenDisabled))) {
                                                    showSecurityScreen(securityMode5);
                                                    break;
                                                }
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
                                    z5 = false;
                                    z4 = true;
                                    z8 = false;
                                }
                            } else if (z9 && this.mIsSwipeBouncer) {
                                showSecurityScreen(KeyguardSecurityModel.SecurityMode.Swipe);
                                z5 = false;
                                z4 = true;
                                z8 = false;
                            } else {
                                boolean isBiometricsAuthenticatedOnLock = keyguardUpdateMonitor.isBiometricsAuthenticatedOnLock();
                                if (!isBiometricsAuthenticatedOnLock && keyguardUpdateMonitor.getUserHasTrust(i)) {
                                    KeyguardUnlockInfo.setAuthDetailSkipBouncer(KeyguardUnlockInfo.SkipBouncerReason.EXTEND_LOCK);
                                    SystemUIAnalytics.sendEventLog(DATA.DM_FIELD_INDEX.UT_APN_NAME, "1001", DATA.DM_FIELD_INDEX.PUBLIC_USER_ID);
                                } else if (isBiometricsAuthenticatedOnLock) {
                                    KeyguardUnlockInfo.setAuthDetailSkipBouncer(KeyguardUnlockInfo.SkipBouncerReason.FACE_UNLOCK_LOCK_STAY);
                                } else {
                                    z4 = true;
                                    z5 = false;
                                }
                                z4 = false;
                                z5 = false;
                            }
                            if (!z8 && !z3 && keyguardUpdateMonitor.isDualDarInnerAuthRequired(i)) {
                                startDisappearAnimation(new Runnable() { // from class: com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticLambda4
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        final DualDarInnerLockScreenController dualDarInnerLockScreenController = KeyguardSecSecurityContainerController.this.mDualDarInnerLockScreenController;
                                        dualDarInnerLockScreenController.getClass();
                                        int innerAuthUserId = ((KnoxStateMonitorImpl) dualDarInnerLockScreenController.mKnoxStateMonitor).getInnerAuthUserId(UserHandle.getCallingUserId());
                                        int credentialTypeForUser = dualDarInnerLockScreenController.mLockPatternUtils.getCredentialTypeForUser(innerAuthUserId);
                                        DualDarInnerLockScreenController.C06504 c06504 = dualDarInnerLockScreenController.mCallback;
                                        KeyguardSecurityContainer keyguardSecurityContainer = dualDarInnerLockScreenController.mParent;
                                        DualDarKeyguardSecurityCallback dualDarKeyguardSecurityCallback = dualDarInnerLockScreenController.mDualDarKeyguardSecurityCallback;
                                        KeyguardInputViewController.Factory factory = dualDarInnerLockScreenController.mKeyguardSecurityViewControllerFactory;
                                        LayoutInflater layoutInflater = dualDarInnerLockScreenController.mLayoutInflater;
                                        if (credentialTypeForUser == 3) {
                                            KeyguardInputView keyguardInputView = DeviceType.isTablet() ? (KeyguardInputView) layoutInflater.inflate(R.layout.keyguard_knox_dual_dar_inner_pin_view_tablet, (ViewGroup) keyguardSecurityContainer, false) : (KeyguardInputView) layoutInflater.inflate(R.layout.keyguard_knox_dual_dar_inner_pin_view, (ViewGroup) keyguardSecurityContainer, false);
                                            dualDarInnerLockScreenController.mBaseView = keyguardInputView;
                                            keyguardInputView.setId(View.generateViewId());
                                            dualDarInnerLockScreenController.mBaseViewController = factory.create(dualDarInnerLockScreenController.mBaseView, KeyguardSecurityModel.SecurityMode.PIN, c06504);
                                            ((KeyguardSecSecurityContainerController$$ExternalSyntheticLambda3) dualDarKeyguardSecurityCallback).onSecurityModeChanged(false);
                                            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) dualDarInnerLockScreenController.mBaseView.getLayoutParams();
                                            layoutParams.bottomToBottom = 0;
                                            dualDarInnerLockScreenController.mBaseView.setLayoutParams(layoutParams);
                                        } else if (credentialTypeForUser != 4) {
                                            android.util.Log.d("DualDarInnerLockScreenController", "Something went wrong");
                                        } else {
                                            KeyguardInputView keyguardInputView2 = DeviceType.isTablet() ? (KeyguardInputView) layoutInflater.inflate(R.layout.keyguard_knox_dual_dar_inner_password_view_tablet, (ViewGroup) keyguardSecurityContainer, false) : (KeyguardInputView) layoutInflater.inflate(R.layout.keyguard_knox_dual_dar_inner_password_view, (ViewGroup) keyguardSecurityContainer, false);
                                            dualDarInnerLockScreenController.mBaseView = keyguardInputView2;
                                            keyguardInputView2.setId(View.generateViewId());
                                            dualDarInnerLockScreenController.mBaseViewController = factory.create(dualDarInnerLockScreenController.mBaseView, KeyguardSecurityModel.SecurityMode.Password, c06504);
                                            ((KeyguardSecSecurityContainerController$$ExternalSyntheticLambda3) dualDarKeyguardSecurityCallback).onSecurityModeChanged(true);
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
                                                    int i2;
                                                    DualDarInnerLockScreenController dualDarInnerLockScreenController2 = DualDarInnerLockScreenController.this;
                                                    dualDarInnerLockScreenController2.getClass();
                                                    if (dualDarInnerLockScreenController2.mLockPatternUtils.getCredentialTypeForUser(((KnoxStateMonitorImpl) dualDarInnerLockScreenController2.mKnoxStateMonitor).getInnerAuthUserId(UserHandle.getCallingUserId())) == 4) {
                                                        int i3 = LsRune.SECURITY_NAVBAR_ENABLED ? 0 : windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars()).bottom;
                                                        int i4 = windowInsets.getInsets(WindowInsets.Type.ime()).bottom;
                                                        boolean isVisible = windowInsets.isVisible(WindowInsets.Type.ime());
                                                        if (dualDarInnerLockScreenController2.mIsImeShown != isVisible) {
                                                            dualDarInnerLockScreenController2.mIsImeShown = isVisible;
                                                            dualDarInnerLockScreenController2.updateLayoutMargins(dualDarInnerLockScreenController2.mParent, dualDarInnerLockScreenController2.mBaseView);
                                                        }
                                                        i2 = Integer.max(i3, i4);
                                                    } else {
                                                        i2 = 0;
                                                    }
                                                    view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), i2);
                                                    return windowInsets.inset(0, 0, 0, i2);
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
                                        dualDarInnerLockScreenController.mBaseViewController.reset();
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
                            if (z8 && !z2) {
                                if (z4) {
                                    KeyguardUnlockInfo.setAuthDetail(this.mCurrentSecurityMode);
                                }
                                this.mKeyguardSecurityCallback.finish(i, z5);
                                if (z) {
                                    SystemUIAnalytics.sendEventLog(DATA.DM_FIELD_INDEX.UT_APN_NAME, "1032", "1");
                                }
                            }
                            return z8;
                        }
                        z5 = false;
                        z4 = true;
                        if (!z8) {
                        }
                        if (z8) {
                            if (z4) {
                            }
                            this.mKeyguardSecurityCallback.finish(i, z5);
                            if (z) {
                            }
                        }
                        return z8;
                    }
                    StringBuilder m1m = AbstractC0000x2c234b15.m1m("Switch targetUserId ", i, " to ");
                    m1m.append(knoxStateMonitorImpl.getMainUserId(i));
                    android.util.Log.d("KeyguardSecSecurityContainer", m1m.toString());
                    i = knoxStateMonitorImpl.getMainUserId(i);
                }
            }
            z7 = false;
            if (z7) {
            }
        }
        z3 = true;
        if (!MdfUtils.isMdfDisabled()) {
        }
    }

    public final void showPromptReason(int i) {
        if (i == 5) {
            android.util.Log.i("KeyguardSecSecurityContainer", "return, biometric lockout");
        } else if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            if (i != 0) {
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Strong auth required, reason: ", i, "KeyguardSecurityContainer");
            }
            getCurrentSecurityController(new KeyguardSecurityContainerController$$ExternalSyntheticLambda6(i, 1));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00af  */
    @Override // com.android.keyguard.KeyguardSecurityContainerController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void showSecurityScreen(KeyguardSecurityModel.SecurityMode securityMode) {
        boolean z;
        android.util.Log.d("KeyguardSecSecurityContainer", "showSecurityScreen(" + securityMode + ") current = " + this.mCurrentSecurityMode);
        KeyguardSecurityModel.SecurityMode securityMode2 = this.mCurrentSecurityMode;
        if (securityMode == securityMode2) {
            return;
        }
        if (AbstractC07365.f207xdc0e830a[securityMode2.ordinal()] == 12 && securityMode == KeyguardSecurityModel.SecurityMode.None) {
            int nextSubIdForState = ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).getNextSubIdForState(2);
            EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mEdmMonitor;
            boolean z2 = false;
            if (edmMonitor != null) {
                if (edmMonitor.mLockedIccIdList != null && SubscriptionManager.isValidSubscriptionId(nextSubIdForState)) {
                    SubscriptionInfo activeSubscriptionInfo = ((SubscriptionManager) edmMonitor.knoxStateMonitor.mContext.getSystemService("telephony_subscription_service")).getActiveSubscriptionInfo(nextSubIdForState);
                    String iccId = activeSubscriptionInfo != null ? activeSubscriptionInfo.getIccId() : null;
                    Object[] objArr = new Object[3];
                    objArr[0] = Integer.valueOf(nextSubIdForState);
                    Object obj = activeSubscriptionInfo;
                    if (activeSubscriptionInfo == null) {
                        obj = "";
                    }
                    objArr[1] = obj;
                    objArr[2] = iccId != null ? iccId : "";
                    android.util.Log.d("EdmMonitor", String.format("isSubIdLockedByAdmin subId=%d, subInfo=%s, iccId=%s", objArr));
                    if (iccId != null) {
                        for (String str : edmMonitor.mLockedIccIdList) {
                            if (!str.equals(iccId)) {
                            }
                        }
                    }
                    z = true;
                    if (z) {
                        z2 = true;
                    }
                }
                z = false;
                if (z) {
                }
            }
            Log.m139d("KeyguardSecSecurityContainer", "reportSecurityMode SimPin -> None simPinSubId = %d, isLockedByMDM=%b", Integer.valueOf(nextSubIdForState), Boolean.valueOf(z2));
        }
        this.mUpdateMonitor.dispatchSecurityModeChanged(securityMode);
        super.showSecurityScreen(securityMode);
        updateLayoutMargins();
    }

    @Override // com.android.keyguard.KeyguardSecurityContainerController
    public final void startAppearAnimation() {
        String str;
        super.startAppearAnimation();
        KeyguardBiometricViewController keyguardBiometricViewController = this.mBiometricViewController;
        keyguardBiometricViewController.updateBiometricViewLayout();
        keyguardBiometricViewController.startLockIconAnimation(true);
        if (LsRune.SECURITY_ARROW_VIEW) {
            KeyguardArrowViewController keyguardArrowViewController = this.mKeyguardArrowViewController;
            KeyguardArrowViewController.startArrowViewAnimation(keyguardArrowViewController.mLeftArrow);
            KeyguardArrowViewController.startArrowViewAnimation(keyguardArrowViewController.mRightArrow);
            if (!keyguardArrowViewController.isInvalidArrowView()) {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - keyguardArrowViewController.mLastUpdateTime > 604800000) {
                    keyguardArrowViewController.mLastUpdateTime = currentTimeMillis;
                    new KeyguardArrowViewController.StatusLoggingTask().execute(new Object[0]);
                }
            }
        }
        if (this.mCurrentSecurityMode == KeyguardSecurityModel.SecurityMode.SmartcardPIN) {
            Context context = getContext();
            KeyguardUCMBiometricViewController keyguardUCMBiometricViewController = this.mUCMBiometricViewController;
            keyguardUCMBiometricViewController.getClass();
            IUcmService asInterface = IUcmService.Stub.asInterface(ServiceManager.getService("com.samsung.ucs.ucsservice"));
            if (asInterface == null) {
                android.util.Log.d("KeyguardUCMBiometricViewController", "failed to get UCM service");
            }
            String str2 = null;
            if (asInterface == null) {
                android.util.Log.d("KeyguardUCMBiometricViewController", "failed to get UCM service");
            } else {
                try {
                    str = asInterface.getKeyguardStorageForCurrentUser(KeyguardUpdateMonitor.getCurrentUser());
                } catch (RemoteException e) {
                    e.printStackTrace();
                    str = null;
                }
                if (str != null && !str.equals("") && !str.equals("none")) {
                    str2 = str;
                }
            }
            keyguardUCMBiometricViewController.mKeyguardSecSecurityContainer.findViewById(R.id.biometric_timeout_message).setPadding(0, str2 == null ? 0 : context.getResources().getDimensionPixelSize(R.dimen.kg_ucm_message_area_padding_top), 0, 0);
        }
    }

    public final boolean startDisappearAnimation(final Runnable runnable) {
        KeyguardSecurityModel.SecurityMode securityMode = this.mCurrentSecurityMode;
        if (securityMode != KeyguardSecurityModel.SecurityMode.None) {
            KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) this.mView;
            keyguardSecurityContainer.mDisappearAnimRunning = true;
            if (securityMode == KeyguardSecurityModel.SecurityMode.Password && (keyguardSecurityContainer.mSecurityViewFlipper.getSecurityView() instanceof KeyguardPasswordView)) {
                ((KeyguardPasswordView) keyguardSecurityContainer.mSecurityViewFlipper.getSecurityView()).mDisappearAnimationListener = new KeyguardSecurityContainer$$ExternalSyntheticLambda0(keyguardSecurityContainer);
            } else {
                keyguardSecurityContainer.mViewMode.startDisappearAnimation(securityMode);
            }
            getCurrentSecurityController(new KeyguardSecurityViewFlipperController.OnViewInflatedCallback() { // from class: com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda8
                @Override // com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback
                public final void onViewInflated(KeyguardInputViewController keyguardInputViewController) {
                    Runnable runnable2 = runnable;
                    if (keyguardInputViewController.startDisappearAnimation(runnable2) || runnable2 == null) {
                        return;
                    }
                    runnable2.run();
                }
            });
        }
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
            constraintSet.constrainWidth(keyguardSecurityViewFlipper.getId(), (!(getContext().getResources().getConfiguration().semDisplayDeviceType == 0) || (this.mCurrentSecurityMode == KeyguardSecurityModel.SecurityMode.KNOXGUARD)) ? 0 : SecurityUtils.getMainSecurityViewFlipperSize(getContext(), this.mIsPassword));
        }
        boolean z = getResources().getConfiguration().getLayoutDirection() == 1;
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
        int i2;
        int i3;
        int i4;
        int i5;
        if (((KeyguardSecSecurityContainer) this.mView).mSecurityViewFlipper == null) {
            return;
        }
        Resources resources = getResources();
        KeyguardSecurityModel.SecurityMode securityMode = this.mCurrentSecurityMode;
        this.mIsPassword = isPassword(securityMode);
        int i6 = 0;
        this.mNavigationBarHeight = LsRune.SECURITY_NAVBAR_ENABLED ? resources.getDimensionPixelSize(android.R.dimen.notification_content_margin_end) : 0;
        boolean z = LsRune.SECURITY_SUB_DISPLAY_LOCK;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        if (z && !DeviceState.isSmartViewFitToActiveDisplay()) {
            boolean z2 = getContext().getResources().getConfiguration().semDisplayDeviceType == 0;
            if (DeviceState.shouldEnableKeyguardScreenRotation(getContext()) || !this.mNeedsInput || this.mCurrentSecurityMode == KeyguardSecurityModel.SecurityMode.KNOXGUARD) {
                i4 = keyguardUpdateMonitor.isHiddenInputContainer() ? 0 : this.mNavigationBarHeight;
                if (i != 1 && i != 3) {
                    i5 = (this.mIsPassword && this.mIsImeShown) ? 0 : this.mNavigationBarHeight;
                    i4 = 0;
                } else {
                    int i7 = z2 ? 0 : i4;
                    if (z2) {
                        i4 = 0;
                    }
                    if (z2 && (!this.mIsPassword || !this.mIsImeShown)) {
                        i6 = this.mNavigationBarHeight;
                    }
                    int i8 = i6;
                    i6 = i7;
                    i5 = i8;
                }
            } else {
                i5 = 0;
                i4 = 0;
            }
            updateLayoutParams(i6, i4, i5);
            return;
        }
        if (DeviceType.isTablet()) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_emergency_button_margin_bottom_for_tablet_fingerprint) + DeviceState.sInDisplayFingerprintHeight;
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
            i2 = DeviceState.sInDisplayFingerprintHeight;
            boolean z4 = keyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted() && !keyguardUpdateMonitor.isHiddenInputContainer();
            int i9 = keyguardUpdateMonitor.isHiddenInputContainer() ? 0 : this.mNavigationBarHeight;
            if (i != 1) {
                if (i != 3) {
                    boolean z5 = (!DeviceState.isInDisplayFpSensorPositionHigh()) & z4;
                    if (this.mIsPassword && this.mIsImeShown) {
                        i2 = 0;
                    } else if (!z5) {
                        i2 = this.mNavigationBarHeight;
                    }
                    i3 = i2;
                    i2 = 0;
                } else {
                    if (!z4) {
                        i2 = i9;
                    }
                    int i10 = i9;
                    i9 = i2;
                    i2 = i10;
                }
            } else if (!z4) {
                i2 = i9;
            }
            i3 = 0;
            i6 = i9;
        } else {
            i3 = 0;
            i2 = 0;
        }
        updateLayoutParams(i6, i2, i3);
    }
}
