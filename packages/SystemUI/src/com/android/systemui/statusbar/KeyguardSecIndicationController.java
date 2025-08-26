package com.android.systemui.statusbar;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.AlarmManager;
import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.app.SemWallpaperColors;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricSourceType;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.app.IBatteryStats;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.ViewClippingUtil;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.SecCountDownTimer;
import com.android.keyguard.SecRotationWatcher;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.settingslib.Utils;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.PowerUiRune;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.FaceHelpMessageDeferralFactory;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFingerprintAuthInteractor;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.dock.DockManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.util.IndicationHelper;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.pluginlock.PluginLockData;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda43;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.phone.BounceInterpolator;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.KeyguardIndicationTextView;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.user.domain.interactor.UserLogoutInteractor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIImageView;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.LambdaObserver;
import io.reactivex.internal.operators.observable.ObservableDelay;
import io.reactivex.internal.operators.observable.ObservableJust;
import io.reactivex.internal.operators.observable.ObservableObserveOn;
import io.reactivex.schedulers.Schedulers;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class KeyguardSecIndicationController extends KeyguardIndicationController implements SystemUIWidgetCallback, PluginLockListener.State {
    public final AccessibilityManager mAccessibilityManager;
    public final Drawable mBatteryLightingBoltDrawable;
    public final Drawable mBatteryProtectionDrawable;
    public CountDownTimer mBiometricsCountdownTimer;
    public final BounceInterpolator mBounceInterpolator;
    public final AnonymousClass2 mClippingParams;
    public AnonymousClass7 mCountDownTimer;
    public final AnonymousClass1 mEditModeListener;
    public final ColorStateList mErrorColor;
    public View mIndicationArea;
    public final KeyguardSecIndicationPolicy mIndicationPolicy;
    public boolean mIsDefaultLockViewMode;
    public boolean mIsFpGuidePos;
    public boolean mIsScreenOn;
    public boolean mIsUsbRestricted;
    public final KeyguardEditModeController mKeyguardEditModeController;
    public final KeyguardStateController mKeyguardStateController;
    public final AnonymousClass3 mKeyguardStateControllerCallback;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public LinearLayout mLifeStyleContainer;
    public SystemUIImageView mLifeStyleImageView;
    public boolean mLockHelpTextVisible;
    public final PluginLockData mPluginLockData;
    public final PluginLockMediator mPluginLockMediator;
    public final PluginLockStarManager mPluginLockStarManager;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public SecKeyguardCallback mUpdateMonitorCallback;
    public KeyguardIndicationTextView mUpperTextView;
    public final AnonymousClass5 mWakefulnessObserver;

    /* renamed from: com.android.systemui.statusbar.KeyguardSecIndicationController$9, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass9 {
        public static final /* synthetic */ int[] $SwitchMap$android$hardware$biometrics$BiometricSourceType;
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$statusbar$IndicationPosition;

        static {
            int[] iArr = new int[BiometricSourceType.values().length];
            $SwitchMap$android$hardware$biometrics$BiometricSourceType = iArr;
            try {
                iArr[BiometricSourceType.FINGERPRINT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$hardware$biometrics$BiometricSourceType[BiometricSourceType.FACE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[IndicationPosition.values().length];
            $SwitchMap$com$android$systemui$statusbar$IndicationPosition = iArr2;
            try {
                iArr2[IndicationPosition.DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$systemui$statusbar$IndicationPosition[IndicationPosition.UPPER.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public class SecKeyguardCallback extends KeyguardIndicationController.BaseKeyguardCallback {
        public int mLastSuccessiveErrorMessage;

        public SecKeyguardCallback() {
            super();
        }

        @Override // com.android.systemui.statusbar.KeyguardIndicationController.BaseKeyguardCallback, com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) throws Resources.NotFoundException {
            BiometricSourceType biometricSourceType2 = BiometricSourceType.FACE;
            KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
            if (biometricSourceType == biometricSourceType2 && ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnabledFaceStayOnLock() && keyguardSecIndicationController.mVisible && !keyguardSecIndicationController.mDozing && keyguardSecIndicationController.mIsScreenOn) {
                KeyguardSecIndicationController.m2961$$Nest$mupdateDefaultIndications(keyguardSecIndicationController);
                keyguardSecIndicationController.showBounceAnimation(keyguardSecIndicationController.mUpperTextView);
            } else {
                KeyguardSecIndicationPolicy keyguardSecIndicationPolicy = keyguardSecIndicationController.mIndicationPolicy;
                if (keyguardSecIndicationPolicy != null) {
                    keyguardSecIndicationPolicy.removeAllIndications();
                }
            }
            keyguardSecIndicationController.removeIndication(IndicationEventType.BIOMETRICS_HELP);
        }

        @Override // com.android.systemui.statusbar.KeyguardIndicationController.BaseKeyguardCallback, com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
            KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
            if (keyguardSecIndicationController.mIndicationHelper.shouldSuppressErrorMsg(biometricSourceType, i)) {
                return;
            }
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = keyguardSecIndicationController.mStatusBarKeyguardViewManager;
            if (statusBarKeyguardViewManager != null ? statusBarKeyguardViewManager.isBouncerShowing() : false) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT && this.mLastSuccessiveErrorMessage != i) {
                    keyguardSecIndicationController.mStatusBarKeyguardViewManager.setKeyguardMessage(str, keyguardSecIndicationController.mInitialTextColorState);
                }
            } else if (keyguardUpdateMonitor.mDeviceInteractive) {
                ColorStateList colorAttr = Utils.getColorAttr(R.attr.colorError, keyguardSecIndicationController.mContext);
                if (biometricSourceType != BiometricSourceType.FACE) {
                    int rotation = keyguardSecIndicationController.mContext.getResources().getConfiguration().windowConfiguration.getRotation();
                    BiometricSourceType biometricSourceType2 = BiometricSourceType.FINGERPRINT;
                    if (biometricSourceType == biometricSourceType2 && DeviceState.isInDisplayFpSensorPositionHigh() && rotation == 0) {
                        IndicationPosition indicationPosition = IndicationPosition.UPPER;
                        IndicationEventType indicationEventType = IndicationEventType.BIOMETRICS_HELP;
                        KeyguardSecIndicationController.this.addIndicationTimeout(indicationPosition, indicationEventType, str, colorAttr, false);
                        keyguardSecIndicationController.removeIndication(indicationEventType);
                    } else if (biometricSourceType != biometricSourceType2 || DeviceType.isInDisplayFingerprintSupported()) {
                        keyguardSecIndicationController.addIndicationTimeout(IndicationEventType.BIOMETRICS_HELP, AnonymousClass9.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()] != 2 ? str : "", colorAttr, false);
                    }
                    keyguardSecIndicationController.showBounceAnimation(keyguardSecIndicationController.mUpperTextView);
                }
            }
            this.mLastSuccessiveErrorMessage = i;
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x0052  */
        @Override // com.android.systemui.statusbar.KeyguardIndicationController.BaseKeyguardCallback, com.android.keyguard.KeyguardUpdateMonitorCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onBiometricHelp(int i, String str, BiometricSourceType biometricSourceType) {
            if (biometricSourceType != BiometricSourceType.FINGERPRINT) {
                return;
            }
            KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
            if (keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true)) {
                KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
                StatusBarKeyguardViewManager statusBarKeyguardViewManager = keyguardSecIndicationController.mStatusBarKeyguardViewManager;
                boolean zIsBouncerShowing = statusBarKeyguardViewManager != null ? statusBarKeyguardViewManager.isBouncerShowing() : false;
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = keyguardSecIndicationController.mKeyguardUpdateMonitor;
                if (zIsBouncerShowing && keyguardUpdateMonitor2.getLockoutAttemptDeadline() <= 0) {
                    AnonymousClass7 anonymousClass7 = keyguardSecIndicationController.mCountDownTimer;
                    if (anonymousClass7 != null) {
                        anonymousClass7.cancel();
                        keyguardSecIndicationController.mCountDownTimer = null;
                        keyguardSecIndicationController.removeIndication(IndicationEventType.PPP_COOLDOWN);
                    }
                    if (!TextUtils.isEmpty(str)) {
                        keyguardSecIndicationController.mStatusBarKeyguardViewManager.setKeyguardMessage(str, keyguardSecIndicationController.mInitialTextColorState);
                    }
                } else if (keyguardSecIndicationController.mScreenLifecycle.mScreenState == 2 && !keyguardUpdateMonitor.mGoingToSleep) {
                    if (i == 1) {
                        str = keyguardSecIndicationController.mContext.getString(com.android.systemui.R.string.kg_fingerprint_acquired_partial);
                    } else if (i == 2) {
                        str = keyguardSecIndicationController.mContext.getString(com.android.systemui.R.string.kg_fingerprint_acquired_insufficient);
                    } else if (i == 3) {
                        str = keyguardSecIndicationController.mContext.getString(com.android.systemui.R.string.kg_fingerprint_acquired_image_dirty);
                    } else if (i == 5) {
                        str = keyguardSecIndicationController.mContext.getString(com.android.systemui.R.string.kg_fingerprint_acquired_too_fast);
                    } else if (i == 1001) {
                        str = keyguardSecIndicationController.mContext.getString(com.android.systemui.R.string.kg_fingerprint_acquired_too_wet);
                    } else if (i == 1003) {
                        str = keyguardSecIndicationController.mContext.getString(com.android.systemui.R.string.kg_fingerprint_acquired_light);
                    } else if (i == 1004) {
                        str = keyguardSecIndicationController.mContext.getString(com.android.systemui.R.string.kg_fingerprint_acquired_tsp_block);
                    }
                    String str2 = str;
                    int rotation = keyguardSecIndicationController.mContext.getResources().getConfiguration().windowConfiguration.getRotation();
                    if (!DeviceState.isTablet() || !LsRune.SECURITY_FINGERPRINT_IN_DISPLAY || rotation != 2) {
                        if (DeviceState.isInDisplayFpSensorPositionHigh() && rotation == 0) {
                            keyguardSecIndicationController.addIndicationTimeout(IndicationPosition.UPPER, IndicationEventType.BIOMETRICS_HELP, str2, keyguardSecIndicationController.mErrorColor, false);
                            keyguardSecIndicationController.showBounceAnimation(keyguardSecIndicationController.mUpperTextView);
                        } else if (DeviceType.isInDisplayFingerprintSupported()) {
                            keyguardSecIndicationController.addIndicationTimeout(IndicationEventType.BIOMETRICS_HELP, str2, keyguardSecIndicationController.mErrorColor, false);
                            keyguardSecIndicationController.showBounceAnimation(keyguardSecIndicationController.mTopIndicationView);
                        }
                    }
                    if (i == -1 && keyguardSecIndicationController.mAccessibilityManager.isTouchExplorationEnabled() && !keyguardUpdateMonitor2.isMaxFailedBiometricUnlockAttemptsShort()) {
                        keyguardSecIndicationController.removeIndication(IndicationEventType.UNLOCK_GUIDE);
                        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                        Completable.timer(1000L, AndroidSchedulers.mainThread()).subscribe(new KeyguardSecIndicationController$SecKeyguardCallback$$ExternalSyntheticLambda0(this, 2));
                    }
                }
                this.mLastSuccessiveErrorMessage = -1;
            }
        }

        @Override // com.android.systemui.statusbar.KeyguardIndicationController.BaseKeyguardCallback, com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) throws Resources.NotFoundException {
            KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
            if (keyguardSecIndicationController.mVisible && !keyguardSecIndicationController.mDozing && keyguardSecIndicationController.mIsScreenOn) {
                if (z) {
                    KeyguardSecIndicationController.m2961$$Nest$mupdateDefaultIndications(keyguardSecIndicationController);
                    keyguardSecIndicationController.addIndicationTimeout(IndicationEventType.BIOMETRICS_STOP, "", keyguardSecIndicationController.mInitialTextColorState, false);
                    return;
                }
                keyguardSecIndicationController.removeIndication(IndicationEventType.BIOMETRICS_STOP);
                if (keyguardSecIndicationController.isAuthenticatedWithBiometric()) {
                    return;
                }
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                Completable.timer(100L, AndroidSchedulers.mainThread()).subscribe(new KeyguardSecIndicationController$SecKeyguardCallback$$ExternalSyntheticLambda0(this, 0));
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardVisibilityChanged(boolean z) {
            LinearLayout linearLayout;
            if (z || (linearLayout = KeyguardSecIndicationController.this.mLifeStyleContainer) == null) {
                return;
            }
            linearLayout.setVisibility(8);
        }

        /* JADX WARN: Type inference failed for: r0v26, types: [android.os.CountDownTimer, com.android.systemui.statusbar.KeyguardSecIndicationController$7] */
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onLockModeChanged() {
            long j;
            CountDownTimer countDownTimer;
            final KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
            long lockoutAttemptDeadline = keyguardSecIndicationController.mKeyguardUpdateMonitor.getLockoutAttemptDeadline();
            KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardSecIndicationController.mKeyguardUpdateMonitor;
            long lockoutBiometricAttemptDeadline = keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline();
            StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("onLockModeChanged - ", lockoutAttemptDeadline, " | ");
            sbM.append(lockoutBiometricAttemptDeadline);
            Log.d("KeyguardSecIndicationController", sbM.toString());
            if (lockoutAttemptDeadline > 0) {
                Log.d("KeyguardSecIndicationController", "startCountdownTimer - " + lockoutAttemptDeadline);
                if (keyguardUpdateMonitor.isPerformingWipeOut()) {
                    j = 0;
                    countDownTimer = null;
                } else {
                    AnonymousClass7 anonymousClass7 = keyguardSecIndicationController.mCountDownTimer;
                    if (anonymousClass7 != null) {
                        anonymousClass7.cancel();
                        keyguardSecIndicationController.mCountDownTimer = null;
                    }
                    j = 0;
                    countDownTimer = null;
                    ?? r0 = new SecCountDownTimer(lockoutAttemptDeadline - SystemClock.elapsedRealtime(), 1000L, keyguardSecIndicationController.mContext, keyguardSecIndicationController.mSelectedUserInteractor, keyguardSecIndicationController.mKeyguardUpdateMonitor, null, false) { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.7
                        @Override // com.android.keyguard.SecCountDownTimer, android.os.CountDownTimer
                        public final void onFinish() {
                            Log.d("KeyguardSecIndicationController", "CountdownTimer - onFinish()");
                            KeyguardSecIndicationController.this.mKeyguardUpdateMonitor.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_FACE_LOCKOUT_RESET);
                            KeyguardSecIndicationController keyguardSecIndicationController2 = KeyguardSecIndicationController.this;
                            keyguardSecIndicationController2.mCountDownTimer = null;
                            keyguardSecIndicationController2.removeIndication(IndicationEventType.PPP_COOLDOWN);
                        }

                        @Override // com.android.keyguard.SecCountDownTimer, android.os.CountDownTimer
                        public final void onTick(long j2) {
                            super.onTick(j2);
                            String str = this.mTimerText;
                            if (str.isEmpty()) {
                                return;
                            }
                            Log.d("KeyguardSecIndicationController", "CountdownTimer - ".concat(str));
                            KeyguardSecIndicationController.this.addIndication(IndicationEventType.PPP_COOLDOWN, str);
                        }
                    };
                    keyguardSecIndicationController.mCountDownTimer = r0;
                    r0.start();
                }
            } else {
                j = 0;
                countDownTimer = null;
                AnonymousClass7 anonymousClass72 = keyguardSecIndicationController.mCountDownTimer;
                if (anonymousClass72 != null) {
                    anonymousClass72.cancel();
                    keyguardSecIndicationController.mCountDownTimer = null;
                    keyguardSecIndicationController.removeIndication(IndicationEventType.PPP_COOLDOWN);
                }
            }
            if (lockoutBiometricAttemptDeadline > j) {
                Log.d("KeyguardSecIndicationController", "startBiometricCountdownTimer - " + lockoutBiometricAttemptDeadline);
                if (!keyguardUpdateMonitor.isPerformingWipeOut()) {
                    CountDownTimer countDownTimer2 = keyguardSecIndicationController.mBiometricsCountdownTimer;
                    if (countDownTimer2 != null) {
                        countDownTimer2.cancel();
                        keyguardSecIndicationController.mBiometricsCountdownTimer = countDownTimer;
                    }
                    KeyguardIndicationTextView keyguardIndicationTextView = keyguardSecIndicationController.mUpperTextView;
                    if (keyguardIndicationTextView != null && !TextUtils.isEmpty(keyguardIndicationTextView.getText())) {
                        keyguardSecIndicationController.mUpperTextView.setText("");
                    }
                    keyguardSecIndicationController.mBiometricsCountdownTimer = new CountDownTimer(lockoutBiometricAttemptDeadline - SystemClock.elapsedRealtime(), 1000L) { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.6
                        public final int attemptRemainingBeforeWipe;
                        public final int biometricType;

                        {
                            super(j, j);
                            this.attemptRemainingBeforeWipe = KeyguardSecIndicationController.this.mKeyguardUpdateMonitor.getRemainingAttempt(1);
                            this.biometricType = KeyguardSecIndicationController.this.mKeyguardUpdateMonitor.getBiometricType();
                        }

                        @Override // android.os.CountDownTimer
                        public final void onFinish() {
                            Log.d("KeyguardSecIndicationController", "BiometricsCountdownTimer - onFinish");
                            KeyguardSecIndicationController.this.mKeyguardUpdateMonitor.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_FACE_LOCKOUT_RESET);
                            KeyguardSecIndicationController keyguardSecIndicationController2 = KeyguardSecIndicationController.this;
                            keyguardSecIndicationController2.mBiometricsCountdownTimer = null;
                            keyguardSecIndicationController2.removeIndication(IndicationEventType.BIOMETRICS_COOLDOWN);
                        }

                        @Override // android.os.CountDownTimer
                        public final void onTick(long j2) {
                            String string;
                            int iRound = (int) Math.round(j2 / 1000.0d);
                            int iCeil = (int) Math.ceil(iRound / 60.0d);
                            if (this.attemptRemainingBeforeWipe > 0) {
                                StringBuilder sb = new StringBuilder();
                                Resources resources = KeyguardSecIndicationController.this.mContext.getResources();
                                int i = this.attemptRemainingBeforeWipe;
                                string = TransitionKt$$ExternalSyntheticOutline0.m(sb, resources.getQuantityString(com.android.systemui.R.plurals.kg_attempt_left, i, Integer.valueOf(i)), "\n");
                            } else {
                                string = "";
                            }
                            if (iRound > 60) {
                                StringBuilder sbM2 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(string);
                                sbM2.append(KeyguardSecIndicationController.this.mContext.getResources().getQuantityString(com.android.systemui.R.plurals.kg_too_many_failed_attempts_countdown_min, iCeil, Integer.valueOf(iCeil)));
                                string = sbM2.toString();
                            } else if (iRound <= 60 && iRound > 0) {
                                int i2 = this.biometricType;
                                if (i2 == 1) {
                                    StringBuilder sbM3 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(string);
                                    sbM3.append(KeyguardSecIndicationController.this.mContext.getResources().getQuantityString(com.android.systemui.R.plurals.kg_too_many_failed_attempts_countdown_fingerprint, iRound, Integer.valueOf(iRound)));
                                    string = sbM3.toString();
                                } else if (i2 != 256) {
                                    StringBuilder sbM4 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(string);
                                    sbM4.append(KeyguardSecIndicationController.this.mContext.getResources().getQuantityString(com.android.systemui.R.plurals.kg_too_many_failed_attempts_countdown_biometric, iRound, Integer.valueOf(iRound)));
                                    string = sbM4.toString();
                                } else {
                                    StringBuilder sbM5 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(string);
                                    sbM5.append(KeyguardSecIndicationController.this.mContext.getResources().getQuantityString(com.android.systemui.R.plurals.kg_too_many_failed_attempts_countdown_face, iRound, Integer.valueOf(iRound)));
                                    string = sbM5.toString();
                                }
                            }
                            if (string.isEmpty()) {
                                return;
                            }
                            KeyguardSecIndicationController keyguardSecIndicationController2 = KeyguardSecIndicationController.this;
                            if (keyguardSecIndicationController2.mKeyguardUpdateMonitor.getUserCanSkipBouncer(keyguardSecIndicationController2.mSelectedUserInteractor.getSelectedUserId())) {
                                return;
                            }
                            RecyclerView$$ExternalSyntheticOutline0.m(this.biometricType, "KeyguardSecIndicationController", ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("BiometricsCountdownTimer - ", string, " biometricType : "));
                            KeyguardSecIndicationController.this.addIndication(IndicationEventType.BIOMETRICS_COOLDOWN, string);
                        }
                    }.start();
                }
            } else {
                CountDownTimer countDownTimer3 = keyguardSecIndicationController.mBiometricsCountdownTimer;
                if (countDownTimer3 != null) {
                    countDownTimer3.cancel();
                    keyguardSecIndicationController.mBiometricsCountdownTimer = countDownTimer;
                    keyguardSecIndicationController.removeIndication(IndicationEventType.BIOMETRICS_COOLDOWN);
                }
            }
            keyguardSecIndicationController.addIndication(IndicationEventType.UNLOCK_GUIDE, keyguardSecIndicationController.getUnlockGuideText());
        }

        @Override // com.android.systemui.statusbar.KeyguardIndicationController.BaseKeyguardCallback, com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onRefreshBatteryInfo(KeyguardBatteryStatus keyguardBatteryStatus) throws Resources.NotFoundException {
            super.onRefreshBatteryInfo(keyguardBatteryStatus);
            KeyguardSecIndicationController.this.addInitialIndication();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onSimStateChanged(int i, int i2, int i3) {
            KeyguardSecIndicationController.this.mHandler.postDelayed(new KeyguardSecIndicationController$$ExternalSyntheticLambda3(this, 1), 500L);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onStrongAuthStateChanged(int i) {
            KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
            int strongAuthForUser = keyguardSecIndicationController.mKeyguardUpdateMonitor.mStrongAuthTracker.getStrongAuthForUser(keyguardSecIndicationController.mSelectedUserInteractor.getSelectedUserId());
            if ((strongAuthForUser & 1) == 0 && (strongAuthForUser & 2) == 0 && (strongAuthForUser & 4) == 0 && (strongAuthForUser & 8) == 0 && (strongAuthForUser & 16) == 0 && (strongAuthForUser & 32) == 0) {
                return;
            }
            CountDownTimer countDownTimer = keyguardSecIndicationController.mBiometricsCountdownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
                keyguardSecIndicationController.mBiometricsCountdownTimer = null;
                keyguardSecIndicationController.removeIndication(IndicationEventType.BIOMETRICS_COOLDOWN);
            }
            keyguardSecIndicationController.addIndication(IndicationEventType.UNLOCK_GUIDE, keyguardSecIndicationController.getUnlockGuideText());
        }

        @Override // com.android.systemui.statusbar.KeyguardIndicationController.BaseKeyguardCallback, com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTrustAgentErrorMessage(CharSequence charSequence) {
            IndicationEventType indicationEventType = IndicationEventType.TRUST_AGENT_ERROR;
            KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
            keyguardSecIndicationController.addIndication(IndicationPosition.DEFAULT, indicationEventType, charSequence, keyguardSecIndicationController.mErrorColor);
        }

        @Override // com.android.systemui.statusbar.KeyguardIndicationController.BaseKeyguardCallback, com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTrustChanged(int i) {
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            Completable.timer(100L, AndroidSchedulers.mainThread()).subscribe(new KeyguardSecIndicationController$SecKeyguardCallback$$ExternalSyntheticLambda0(this, 1));
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUSBRestrictionChanged(boolean z) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("onUSBRestrictionChanged ", "KeyguardSecIndicationController", z);
            KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
            keyguardSecIndicationController.mIsUsbRestricted = z;
            keyguardSecIndicationController.addUsbRestriction();
        }

        @Override // com.android.systemui.statusbar.KeyguardIndicationController.BaseKeyguardCallback, com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserUnlocked() {
            KeyguardSecIndicationController.this.removeIndication(IndicationEventType.UNLOCK_GUIDE);
        }
    }

    /* renamed from: -$$Nest$mupdateDefaultIndications, reason: not valid java name */
    public static void m2961$$Nest$mupdateDefaultIndications(KeyguardSecIndicationController keyguardSecIndicationController) throws Resources.NotFoundException {
        keyguardSecIndicationController.addInitialIndication();
        keyguardSecIndicationController.addIndication(keyguardSecIndicationController.isAuthenticatedWithBiometric() ? IndicationEventType.BIOMETRICS_HELP : IndicationEventType.UNLOCK_GUIDE, keyguardSecIndicationController.getUnlockGuideText());
        if (keyguardSecIndicationController.mIsFpGuidePos) {
            KeyguardSecIndicationController$$ExternalSyntheticLambda3 keyguardSecIndicationController$$ExternalSyntheticLambda3 = new KeyguardSecIndicationController$$ExternalSyntheticLambda3(keyguardSecIndicationController, 0);
            KeyguardIndicationController.AnonymousClass2 anonymousClass2 = keyguardSecIndicationController.mHandler;
            if (anonymousClass2.hasCallbacks(keyguardSecIndicationController$$ExternalSyntheticLambda3)) {
                anonymousClass2.removeCallbacks(keyguardSecIndicationController$$ExternalSyntheticLambda3);
            }
            if (!keyguardSecIndicationController.mVisible || keyguardSecIndicationController.mDozing) {
                return;
            }
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = keyguardSecIndicationController.mStatusBarKeyguardViewManager;
            if (statusBarKeyguardViewManager != null ? statusBarKeyguardViewManager.isBouncerShowing() : false) {
                return;
            }
            anonymousClass2.postDelayed(keyguardSecIndicationController$$ExternalSyntheticLambda3, 3000L);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.systemui.statusbar.KeyguardSecIndicationController$3, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.systemui.statusbar.KeyguardSecIndicationController$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.statusbar.KeyguardSecIndicationController$2] */
    /* JADX WARN: Type inference failed for: r6v2, types: [com.android.systemui.statusbar.KeyguardSecIndicationController$5, java.lang.Object] */
    public KeyguardSecIndicationController(Context context, Looper looper, WakeLock.Builder builder, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, DockManager dockManager, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager, IBatteryStats iBatteryStats, UserManager userManager, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, FalsingManager falsingManager, AuthController authController, LockPatternUtils lockPatternUtils, ScreenLifecycle screenLifecycle, KeyguardBypassController keyguardBypassController, AccessibilityManager accessibilityManager, FaceHelpMessageDeferralFactory faceHelpMessageDeferralFactory, KeyguardLogger keyguardLogger, AlternateBouncerInteractor alternateBouncerInteractor, AlarmManager alarmManager, UserTracker userTracker, BouncerMessageInteractor bouncerMessageInteractor, FeatureFlags featureFlags, IndicationHelper indicationHelper, KeyguardInteractor keyguardInteractor, BiometricMessageInteractor biometricMessageInteractor, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, UserLogoutInteractor userLogoutInteractor, SelectedUserInteractor selectedUserInteractor, SecRotationWatcher secRotationWatcher, PluginLockMediator pluginLockMediator, PluginLockData pluginLockData, PluginLockStarManager pluginLockStarManager, KeyguardEditModeController keyguardEditModeController) {
        super(context, looper, builder, keyguardStateController, statusBarStateController, keyguardUpdateMonitor, dockManager, broadcastDispatcher, devicePolicyManager, iBatteryStats, userManager, delayableExecutor, delayableExecutor2, falsingManager, authController, lockPatternUtils, screenLifecycle, keyguardBypassController, accessibilityManager, faceHelpMessageDeferralFactory, keyguardLogger, alternateBouncerInteractor, alarmManager, userTracker, bouncerMessageInteractor, featureFlags, indicationHelper, keyguardInteractor, biometricMessageInteractor, deviceEntryFingerprintAuthInteractor, deviceEntryFaceAuthInteractor, userLogoutInteractor);
        this.mIsScreenOn = true;
        this.mIsUsbRestricted = false;
        ?? r5 = new KeyguardEditModeController.Listener() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.1
            @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
            public final void onAnimationEnded() throws Resources.NotFoundException {
                KeyguardSecIndicationController.this.setVisible(true);
            }

            @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
            public final void onAnimationStarted(boolean z) throws Resources.NotFoundException {
                KeyguardSecIndicationController.this.setVisible(false);
            }
        };
        this.mEditModeListener = r5;
        this.mClippingParams = new ViewClippingUtil.ClippingParameters() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.2
            public final boolean shouldFinish(View view) {
                return view == KeyguardSecIndicationController.this.mIndicationArea;
            }
        };
        this.mLockHelpTextVisible = true;
        this.mIsDefaultLockViewMode = true;
        ?? r4 = new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.3
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onPrimaryBouncerShowingChanged() throws Resources.NotFoundException {
                KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
                if (((KeyguardStateControllerImpl) keyguardSecIndicationController.mKeyguardStateController).mPrimaryBouncerShowing) {
                    keyguardSecIndicationController.mIndicationPolicy.removeAllIndications();
                } else {
                    keyguardSecIndicationController.setVisible(true);
                }
            }
        };
        this.mKeyguardStateControllerCallback = r4;
        ?? r6 = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.5
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                KeyguardSecIndicationPolicy keyguardSecIndicationPolicy = KeyguardSecIndicationController.this.mIndicationPolicy;
                if (keyguardSecIndicationPolicy != null) {
                    keyguardSecIndicationPolicy.removeAllIndications();
                }
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                KeyguardSecIndicationController.this.mIsScreenOn = false;
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() throws Resources.NotFoundException {
                KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
                keyguardSecIndicationController.mIsScreenOn = true;
                keyguardSecIndicationController.addInitialIndication();
                keyguardSecIndicationController.addIndication(IndicationEventType.UNLOCK_GUIDE, keyguardSecIndicationController.getUnlockGuideText());
            }
        };
        this.mWakefulnessObserver = r6;
        this.mKeyguardStateController = keyguardStateController;
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(r4);
        this.mAccessibilityManager = accessibilityManager;
        KeyguardSecIndicationPolicy keyguardSecIndicationPolicy = new KeyguardSecIndicationPolicy();
        this.mIndicationPolicy = keyguardSecIndicationPolicy;
        keyguardSecIndicationPolicy.mListenerList.add(this);
        keyguardSecIndicationPolicy.mTopItemMap.keySet().stream().forEach(new KeyguardSecIndicationPolicy$$ExternalSyntheticLambda3(0, keyguardSecIndicationPolicy, this));
        this.mBounceInterpolator = new BounceInterpolator();
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mBatteryLightingBoltDrawable = this.mContext.getResources().getDrawable(com.android.systemui.R.drawable.keyguard_battery_bolt, null);
        this.mBatteryProtectionDrawable = this.mContext.getResources().getDrawable(com.android.systemui.R.drawable.keyguard_battery_protection, null);
        this.mKnoxStateMonitor = (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
        this.mKeyguardEditModeController = keyguardEditModeController;
        ((ArrayList) ((KeyguardEditModeControllerImpl) keyguardEditModeController).listeners).add(r5);
        this.mPluginLockStarManager = pluginLockStarManager;
        pluginLockStarManager.registerCallback(PluginLockStar.INDICATOR_TYPE, new PluginLockStarManager.LockStarCallback() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.4
            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final void onChangedLockStarData(boolean z) throws Resources.NotFoundException {
                if (z) {
                    return;
                }
                KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
                keyguardSecIndicationController.mTopIndicationView.updateFontSizeInKeyguardBoundary(true, keyguardSecIndicationController.mContext.getResources().getConfiguration());
                keyguardSecIndicationController.mTopIndicationView.updateTextView();
            }
        });
        WallpaperUtils.registerSystemUIWidgetCallback(this, 128L);
        ((WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class)).addObserver(r6);
        this.mPluginLockMediator = pluginLockMediator;
        pluginLockMediator.registerStateCallback(this);
        this.mPluginLockData = pluginLockData;
        this.mErrorColor = ColorStateList.valueOf(-1);
        IndicationEventType indicationEventType = IndicationEventType.EMPTY_LOW;
        addIndication(indicationEventType, "");
        IndicationPosition indicationPosition = IndicationPosition.UPPER;
        addIndication(indicationPosition, indicationEventType, "", this.mInitialTextColorState);
        if (CscRune.SECURITY_SIM_PERM_DISABLED && this.mKeyguardUpdateMonitor.isIccBlockedPermanently()) {
            IndicationEventType indicationEventType2 = IndicationEventType.EMPTY_HIGH;
            addIndication(indicationEventType2, "");
            addIndication(indicationPosition, indicationEventType2, "", this.mInitialTextColorState);
        }
    }

    public final SpannableStringBuilder AddBatteryIcon(String str, boolean z) throws Resources.NotFoundException {
        int dimensionPixelSize;
        int i = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.mContext) == 1 ? 1 : 0;
        String strReplaceFirst = i != 0 ? str.replaceFirst("\n", "  \n") : AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(" ", str);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(strReplaceFirst);
        int iIndexOf = strReplaceFirst.indexOf(" ");
        if (iIndexOf != -1) {
            Drawable drawable = z ? this.mBatteryProtectionDrawable : this.mBatteryLightingBoltDrawable;
            KeyguardIndicationTextView keyguardIndicationTextView = this.mTopIndicationView;
            if (keyguardIndicationTextView != null) {
                drawable.setColorFilter(keyguardIndicationTextView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN);
                dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.keyguard_battery_bolt_size);
            } else {
                dimensionPixelSize = 0;
            }
            int i2 = dimensionPixelSize;
            if (dimensionPixelSize <= 0) {
                dimensionPixelSize = 1;
            }
            if (i2 <= 0) {
                i2 = 1;
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(dimensionPixelSize, i2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), bitmapCreateBitmap);
            bitmapDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            spannableStringBuilder.setSpan(new ImageSpan(bitmapDrawable, 0), iIndexOf + i, iIndexOf + (i != 0 ? 2 : 1), 33);
        }
        return spannableStringBuilder;
    }

    public final void addIndication(IndicationEventType indicationEventType, CharSequence charSequence) {
        addIndication(IndicationPosition.DEFAULT, indicationEventType, charSequence, this.mInitialTextColorState);
    }

    public final void addIndicationTimeout(IndicationEventType indicationEventType, CharSequence charSequence, ColorStateList colorStateList, boolean z) {
        addIndicationTimeout(IndicationPosition.DEFAULT, indicationEventType, charSequence, colorStateList, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:154:0x03d5  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x03ea  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x03f0  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00e5 A[PHI: r0
      0x00e5: PHI (r0v33 java.lang.String) = (r0v12 java.lang.String), (r0v12 java.lang.String), (r0v34 java.lang.String) binds: [B:153:0x03d3, B:155:0x03d9, B:50:0x00e3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0247  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addInitialIndication() throws Resources.NotFoundException {
        String string;
        String strAddBatteryIcon;
        boolean z;
        int i;
        int i2;
        String string2;
        String str;
        String str2;
        String string3;
        CustomSdkMonitor customSdkMonitor;
        Log.d("KeyguardSecIndicationController", "addTrustAgentHelp " + hasTrust());
        if (hasTrust()) {
            addIndication(IndicationEventType.TRUST_AGENT_HELP, this.mContext.getText(com.android.systemui.R.string.kg_extend_lock_content_description));
            return;
        }
        removeIndication(IndicationEventType.TRUST_AGENT_HELP);
        if (addUsbRestriction()) {
            return;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        KeyguardBatteryStatus keyguardBatteryStatus = keyguardUpdateMonitor.getKeyguardBatteryStatus();
        if (keyguardBatteryStatus == null) {
            Log.w("KeyguardSecIndicationController", "addBatteryAndOwnerInfoIndication() no status");
        } else {
            Log.d("KeyguardSecIndicationController", "addBatteryAndOwnerInfoIndication() battery status = " + keyguardBatteryStatus);
            boolean z2 = this.mPowerPluggedIn;
            boolean z3 = keyguardBatteryStatus.level < 20;
            if (PowerUiRune.BATTERY_SWELLING_NOTICE) {
                int i3 = keyguardBatteryStatus.swellingMode;
                char c = (i3 & 16) != 0 ? (char) 16 : (i3 & 32) != 0 ? ' ' : (char) 0;
                long j = keyguardBatteryStatus.remaining;
                CustomSdkMonitor customSdkMonitor2 = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mCustomSdkMonitor;
                boolean z4 = customSdkMonitor2 != null && (customSdkMonitor2.mKnoxCustomLockScreenHiddenItems & 2) == 0;
                if (z4 && (this.mPowerPluggedIn || ((this.mPowerCharged && keyguardBatteryStatus.isPluggedIn()) || (this.mProtectedFullyCharged && keyguardBatteryStatus.isPluggedIn())))) {
                    int i4 = this.mChangingType;
                    int protectBatterySetValue = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getProtectBatterySetValue();
                    String str3 = "";
                    if (this.mPowerCharged) {
                        Context context = this.mContext;
                        if (context == null) {
                            Log.e("KeyguardSecIndicationController", "Fail to getChargingText");
                            string3 = str3;
                        } else {
                            int i5 = this.mBatteryLevel;
                            if (i5 == 100) {
                                string3 = this.mContext.getString(com.android.systemui.R.string.battery_meter_format, Integer.valueOf(this.mBatteryLevel)) + '\n' + ((Object) this.mContext.getText(com.android.systemui.R.string.kg_power_fully_charged));
                            } else if (LsRune.LOCKUI_ECO_BATTERY && (protectBatterySetValue == 3 || protectBatterySetValue == 4)) {
                                string3 = AddBatteryIcon(context.getString(com.android.systemui.R.string.battery_meter_format, Integer.valueOf(i5)) + '\n' + this.mContext.getString(com.android.systemui.R.string.kg_power_charging_paused_to_protect_your_battery), true);
                            } else {
                                string3 = context.getText(com.android.systemui.R.string.kg_power_fully_charged).toString();
                            }
                        }
                    } else if (!this.mProtectedFullyCharged) {
                        if (this.mIsNeededShowChargingType) {
                            string = str3;
                            if (i4 != 2) {
                                switch (i4) {
                                    case 10:
                                        string = this.mContext.getString(com.android.systemui.R.string.kg_power_charging, Integer.valueOf(this.mBatteryLevel));
                                        break;
                                    case 11:
                                        string2 = CscRune.LOCKUI_HELP_TEXT_FOR_CHN ? this.mContext.getString(com.android.systemui.R.string.kg_power_fast_charging_chn, Integer.valueOf(this.mBatteryLevel)) : this.mContext.getString(com.android.systemui.R.string.kg_power_fast_charging, Integer.valueOf(this.mBatteryLevel));
                                        string = string2;
                                        break;
                                    case 12:
                                        string = this.mContext.getString(com.android.systemui.R.string.kg_power_charging_wirelessly, Integer.valueOf(this.mBatteryLevel));
                                        break;
                                    case 13:
                                        string2 = CscRune.LOCKUI_HELP_TEXT_FOR_CHN ? this.mContext.getString(com.android.systemui.R.string.kg_power_fast_charging_wirelessly_chn, Integer.valueOf(this.mBatteryLevel)) : this.mContext.getString(com.android.systemui.R.string.kg_power_fast_charging_wirelessly, Integer.valueOf(this.mBatteryLevel));
                                        string = string2;
                                        break;
                                    case 14:
                                        string = this.mContext.getString(com.android.systemui.R.string.kg_power_super_fast_charging, Integer.valueOf(this.mBatteryLevel));
                                        break;
                                    case 15:
                                        string = this.mContext.getString(com.android.systemui.R.string.kg_power_super_fast_charging_20, Integer.valueOf(this.mBatteryLevel));
                                        break;
                                }
                            } else {
                                string = this.mContext.getString(com.android.systemui.R.string.common_battery_slow_charging, Integer.valueOf(this.mBatteryLevel));
                            }
                        } else {
                            string = this.mContext.getString(com.android.systemui.R.string.battery_meter_format, Integer.valueOf(this.mBatteryLevel));
                        }
                        String str4 = string;
                        if (!PowerUiRune.BATTERY_CHARGING_ESTIMATE_TIME || i4 == 2) {
                            strAddBatteryIcon = str4;
                            if (this.mIsNeededShowChargingType || TextUtils.isEmpty(strAddBatteryIcon)) {
                                z = false;
                            } else {
                                z = false;
                                strAddBatteryIcon = AddBatteryIcon(strAddBatteryIcon, false);
                            }
                            addIndicationTimeout(IndicationEventType.BATTERY, strAddBatteryIcon, this.mInitialTextColorState, (z2 || !this.mPowerPluggedInWired) ? z : true);
                            addIndication(IndicationEventType.BATTERY_RESTING, strAddBatteryIcon);
                        } else if (j <= 0 || c != 0) {
                            if (c == 16) {
                                string3 = this.mContext.getString(com.android.systemui.R.string.common_battery_slow_charging, Integer.valueOf(this.mBatteryLevel));
                            }
                            strAddBatteryIcon = str4;
                            if (this.mIsNeededShowChargingType) {
                                z = false;
                                if (z2) {
                                    addIndicationTimeout(IndicationEventType.BATTERY, strAddBatteryIcon, this.mInitialTextColorState, (z2 || !this.mPowerPluggedInWired) ? z : true);
                                    addIndication(IndicationEventType.BATTERY_RESTING, strAddBatteryIcon);
                                }
                            }
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append(str4);
                            sb.append('\n');
                            long j2 = j / 1000;
                            if (j2 >= 3600) {
                                i = (int) (j2 / 3600);
                                j2 -= i * 3600;
                            } else {
                                i = 0;
                            }
                            if (j2 >= 60) {
                                i2 = (int) (j2 / 60);
                                j2 -= i2 * 60;
                            } else {
                                i2 = 0;
                            }
                            int i6 = (int) j2;
                            if (i == 0 && i2 >= 2 && i6 >= 30) {
                                i2++;
                                if (i2 == 60) {
                                    i = 1;
                                    i2 = 0;
                                }
                            }
                            sb.append((i <= 0 || i2 <= 0) ? i > 0 ? this.mContext.getString(com.android.systemui.R.string.kg_power_time_format_hour, Integer.valueOf(i)) : this.mContext.getString(com.android.systemui.R.string.kg_power_time_format_minute, Integer.valueOf(i2)) : this.mContext.getString(com.android.systemui.R.string.kg_power_time_format_hour_minute, Integer.valueOf(i), Integer.valueOf(i2)));
                            strAddBatteryIcon = sb.toString();
                            if (this.mIsNeededShowChargingType) {
                            }
                        }
                    } else if (this.mContext == null) {
                        Log.e("KeyguardSecIndicationController", "Fail to getChargingText");
                        string3 = str3;
                    } else if (!LsRune.LOCKUI_ECO_BATTERY) {
                        string3 = this.mContext.getText(com.android.systemui.R.string.kg_power_charging_paused).toString() + "\n" + this.mContext.getText(com.android.systemui.R.string.kg_power_charging_limit_85).toString();
                    } else if (protectBatterySetValue == 1 || protectBatterySetValue == 2) {
                        string3 = AddBatteryIcon(this.mContext.getString(com.android.systemui.R.string.battery_meter_format, Integer.valueOf(this.mBatteryLevel)) + '\n' + ((Object) this.mContext.getText(com.android.systemui.R.string.kg_power_charging_maximum_protection)), true);
                    } else if (protectBatterySetValue == 4 && (str = this.mSleepChargingEvent) != null && !"off".equals(str) && this.mSleepChargingEventFinishTime != null) {
                        String string4 = this.mContext.getString(com.android.systemui.R.string.battery_meter_format, Integer.valueOf(this.mBatteryLevel));
                        if (this.mBatteryLevel != 100 && this.mSleepChargingEventFinishTime != null) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(string4);
                            sb2.append('\n');
                            Context context2 = this.mContext;
                            try {
                                Date date = new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(this.mSleepChargingEventFinishTime);
                                str2 = str3;
                                if (date != null) {
                                    str2 = DateFormat.getTimeFormat(context2).format(Long.valueOf(date.getTime()));
                                }
                            } catch (ParseException e) {
                                Log.w("KeyguardSecIndicationController", "ParseException", e);
                                str2 = str3;
                            }
                            sb2.append(context2.getString(com.android.systemui.R.string.kg_power_charging_adaptive_protection, str2));
                            string4 = sb2.toString();
                        }
                        string3 = AddBatteryIcon(string4, true);
                    }
                    strAddBatteryIcon = string3;
                    z = false;
                } else if (z4 && z3) {
                    CharSequence text = this.mContext.getText(com.android.systemui.R.string.kg_power_connect_charger);
                    addIndicationTimeout(IndicationEventType.BATTERY, text, this.mInitialTextColorState, !z2 && this.mPowerPluggedInWired);
                    addIndication(IndicationEventType.BATTERY_RESTING, text);
                } else {
                    removeIndication(IndicationEventType.BATTERY);
                    removeIndication(IndicationEventType.BATTERY_RESTING);
                }
            }
        }
        KnoxStateMonitor knoxStateMonitor = this.mKnoxStateMonitor;
        if ((knoxStateMonitor == null || ((customSdkMonitor = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor) != null && (customSdkMonitor.mKnoxCustomLockScreenHiddenItems & 32) == 0)) && this.mTopIndicationView != null) {
            if (keyguardUpdateMonitor.isDeviceOwnerInfoEnabled() && keyguardUpdateMonitor.getDeviceOwnerInfo() != null) {
                if (TextUtils.equals(this.mTopIndicationView.getText(), keyguardUpdateMonitor.getDeviceOwnerInfo())) {
                    return;
                }
                addIndication(IndicationEventType.OWNER_INFO, keyguardUpdateMonitor.getDeviceOwnerInfo());
            } else if (!keyguardUpdateMonitor.isOwnerInfoEnabled() || keyguardUpdateMonitor.getOwnerInfo() == null) {
                removeIndication(IndicationEventType.OWNER_INFO);
            } else {
                if (TextUtils.equals(this.mTopIndicationView.getText(), keyguardUpdateMonitor.getOwnerInfo())) {
                    return;
                }
                addIndication(IndicationEventType.OWNER_INFO, keyguardUpdateMonitor.getOwnerInfo());
            }
        }
    }

    public final boolean addUsbRestriction() {
        StringBuilder sb = new StringBuilder("addUsbRestriction ");
        sb.append(this.mIsUsbRestricted);
        sb.append("/isSecure=");
        SelectedUserInteractor selectedUserInteractor = this.mSelectedUserInteractor;
        int selectedUserId = selectedUserInteractor.getSelectedUserId();
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        sb.append(keyguardUpdateMonitor.isSecure(selectedUserId) && !keyguardUpdateMonitor.getUserCanSkipBouncer(selectedUserInteractor.getSelectedUserId()));
        Log.d("KeyguardSecIndicationController", sb.toString());
        if (!keyguardUpdateMonitor.isSecure(selectedUserInteractor.getSelectedUserId()) || keyguardUpdateMonitor.getUserCanSkipBouncer(selectedUserInteractor.getSelectedUserId()) || !this.mIsUsbRestricted) {
            removeIndication(IndicationEventType.USB_RESTRICTION);
            return false;
        }
        if (DeviceType.isTablet()) {
            addIndication(IndicationEventType.USB_RESTRICTION, this.mContext.getString(com.android.systemui.R.string.lockscreen_usb_restriction_tablet_help_text));
        } else {
            addIndication(IndicationEventType.USB_RESTRICTION, this.mContext.getString(com.android.systemui.R.string.lockscreen_usb_restriction_phone_help_text));
        }
        return true;
    }

    public final void changeIndication(CharSequence charSequence, boolean z, boolean z2) throws Resources.NotFoundException {
        NotificationPanelViewController$$ExternalSyntheticLambda43 notificationPanelViewController$$ExternalSyntheticLambda43;
        if (this.mTopIndicationView != null) {
            if (this.mLockHelpTextVisible || TextUtils.isEmpty(charSequence)) {
                TextPaint paint = this.mTopIndicationView.getPaint();
                int width = this.mTopIndicationView.getWidth();
                CharSequence text = this.mTopIndicationView.getText();
                Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;
                int lineCount = new StaticLayout(text, paint, width, alignment, 1.0f, 0.0f, false).getLineCount();
                TextUtils.TruncateAt ellipsize = this.mTopIndicationView.getEllipsize();
                TextUtils.TruncateAt truncateAt = TextUtils.TruncateAt.MARQUEE;
                boolean z3 = ellipsize == truncateAt || lineCount == 1;
                KeyguardIndicationTextView keyguardIndicationTextView = this.mTopIndicationView;
                if (keyguardIndicationTextView != null) {
                    keyguardIndicationTextView.setSingleLine(z2);
                    this.mTopIndicationView.setEllipsize(z2 ? truncateAt : TextUtils.TruncateAt.END);
                    this.mTopIndicationView.setSelected(z2);
                }
                if (z) {
                    final KeyguardIndicationTextView keyguardIndicationTextView2 = this.mTopIndicationView;
                    final String string = charSequence.toString();
                    int integer = this.mContext.getResources().getInteger(com.android.systemui.R.integer.wired_charging_keyguard_text_animation_distance);
                    int integer2 = this.mContext.getResources().getInteger(com.android.systemui.R.integer.wired_charging_keyguard_text_animation_duration_up);
                    final int integer3 = this.mContext.getResources().getInteger(com.android.systemui.R.integer.wired_charging_keyguard_text_animation_duration_down);
                    keyguardIndicationTextView2.animate().cancel();
                    ViewClippingUtil.setClippingDeactivated(keyguardIndicationTextView2, true, this.mClippingParams);
                    keyguardIndicationTextView2.animate().translationYBy(integer).setInterpolator(new LinearInterpolator()).setDuration(integer2).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.8
                        public boolean mCancelled;

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animator) {
                            keyguardIndicationTextView2.setTranslationY(0.0f);
                            this.mCancelled = true;
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            if (this.mCancelled) {
                                ViewClippingUtil.setClippingDeactivated(keyguardIndicationTextView2, false, KeyguardSecIndicationController.this.mClippingParams);
                            } else {
                                keyguardIndicationTextView2.animate().setDuration(integer3).setInterpolator(KeyguardSecIndicationController.this.mBounceInterpolator).translationY(0.0f).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.8.1
                                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                    public final void onAnimationEnd(Animator animator2) {
                                        keyguardIndicationTextView2.setTranslationY(0.0f);
                                        AnonymousClass8 anonymousClass8 = AnonymousClass8.this;
                                        ViewClippingUtil.setClippingDeactivated(keyguardIndicationTextView2, false, KeyguardSecIndicationController.this.mClippingParams);
                                    }
                                });
                            }
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            keyguardIndicationTextView2.switchIndication(string, null);
                        }
                    });
                    return;
                }
                this.mTopIndicationView.switchIndication(charSequence, null);
                PluginLockStarManager pluginLockStarManager = this.mPluginLockStarManager;
                if (pluginLockStarManager.isLockStarEnabled()) {
                    PluginLockStar pluginLockStar = pluginLockStarManager.mPluginLockStar;
                    PluginLockStar.Modifier modifier = pluginLockStar == null ? null : pluginLockStar.getModifier("UpdatehelptextScale");
                    if (modifier != null) {
                        modifier.accept(this.mTopIndicationView);
                    }
                    PluginLockStar pluginLockStar2 = pluginLockStarManager.mPluginLockStar;
                    PluginLockStar.Modifier modifier2 = pluginLockStar2 != null ? pluginLockStar2.getModifier("UpdatehelptextColor") : null;
                    if (modifier2 != null) {
                        modifier2.accept(this.mTopIndicationView);
                    }
                }
                if (z3 == (this.mTopIndicationView.getEllipsize() == truncateAt || new StaticLayout(this.mTopIndicationView.getText(), paint, width, alignment, 1.0f, 0.0f, false).getLineCount() == 1) || (notificationPanelViewController$$ExternalSyntheticLambda43 = this.mUpdatePosition) == null) {
                    return;
                }
                notificationPanelViewController$$ExternalSyntheticLambda43.accept(Boolean.TRUE);
            }
        }
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final void dump(PrintWriter printWriter, String[] strArr) {
        super.dump(printWriter, strArr);
        KeyguardSecIndicationPolicy keyguardSecIndicationPolicy = this.mIndicationPolicy;
        if (keyguardSecIndicationPolicy != null) {
            keyguardSecIndicationPolicy.dump(printWriter, strArr);
        }
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final KeyguardUpdateMonitorCallback getKeyguardCallback() {
        if (this.mUpdateMonitorCallback == null) {
            this.mUpdateMonitorCallback = new SecKeyguardCallback();
        }
        return this.mUpdateMonitorCallback;
    }

    public final CharSequence getUnlockGuideText() {
        boolean z;
        int i;
        boolean z2 = CscRune.SECURITY_SIM_PERM_DISABLED;
        int i2 = 0;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (z2 && keyguardUpdateMonitor.isIccBlockedPermanently()) {
            z = true;
        } else {
            CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mCustomSdkMonitor;
            z = !(customSdkMonitor != null && (customSdkMonitor.mKnoxCustomLockScreenHiddenItems & 256) == 0);
        }
        if (z) {
            return "";
        }
        if (LsRune.SECURITY_CONTINUITY_LOCKSCREEN_VI && this.mForceIsDismissible) {
            return "";
        }
        boolean zHasTrust = hasTrust();
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
        boolean zIsUnlockingWithBiometricAllowed = keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true);
        boolean z3 = zIsUnlockingWithBiometricAllowed && keyguardUpdateMonitor.isFingerprintOptionEnabled() && !isAuthenticatedWithBiometric() && !zHasTrust;
        boolean z4 = zIsUnlockingWithBiometricAllowed && keyguardUpdateMonitor.isFaceOptionEnabled() && !keyguardUpdateMonitor.isCameraDisabledByPolicy() && !keyguardUpdateMonitor.isFaceDisabled(selectedUserId) && keyguardUpdateMonitor.isFaceDetectionRunning();
        boolean zIsTouchExplorationEnabled = this.mAccessibilityManager.isTouchExplorationEnabled();
        this.mIsFpGuidePos = false;
        if (z3 || z4) {
            if (!(z3 && z4) && z3) {
                i = zIsTouchExplorationEnabled ? com.android.systemui.R.string.kg_fingerprint_or_swipe_unlock_voice_assistant_instructions : 0;
                this.mIsFpGuidePos = zIsTouchExplorationEnabled;
            } else {
                i = 0;
            }
            if (i != 0) {
                return this.mContext.getString(i);
            }
        } else {
            i = hasTrust() ? com.android.systemui.R.string.kg_extend_lock_content_description : (!keyguardUpdateMonitor.isSecure() || keyguardUpdateMonitor.isBiometricsAuthenticatedOnLock()) ? com.android.systemui.R.string.kg_swipe_active_instructions : com.android.systemui.R.string.kg_swipe_unlock_instructions;
        }
        if (zIsTouchExplorationEnabled) {
            if (keyguardUpdateMonitor.isUnlockCompleted() || !(z3 || z4)) {
                if (!zHasTrust && !z3 && !z4) {
                    i2 = com.android.systemui.R.string.kg_voice_assistant_unlock_instructions;
                }
                i = (isAuthenticatedWithBiometric() || !keyguardUpdateMonitor.isSecure()) ? com.android.systemui.R.string.kg_voice_assistant_active_instructions : i2;
            } else {
                i = com.android.systemui.R.string.kg_voice_assistant_unlock_strong_auth_instructions;
            }
        }
        return i != 0 ? this.mContext.getString(i) : "";
    }

    public final boolean hasTrust() {
        return this.mKeyguardUpdateMonitor.getUserHasTrust(this.mSelectedUserInteractor.getSelectedUserId());
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final void hideTransientIndication() {
        removeIndication(IndicationEventType.LEGACY_TRANSIENT);
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final void hideTransientIndicationDelayed(long j) {
        IndicationEventType indicationEventType = IndicationEventType.LEGACY_TRANSIENT;
        ObjectHelper.requireNonNull(indicationEventType, "item is null");
        ObservableJust observableJust = new ObservableJust(indicationEventType);
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        Scheduler scheduler = Schedulers.COMPUTATION;
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        ObservableDelay observableDelay = new ObservableDelay(observableJust, j, timeUnit, scheduler, false);
        Scheduler schedulerMainThread = AndroidSchedulers.mainThread();
        int i = Flowable.BUFFER_SIZE;
        if (i <= 0) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "bufferSize > 0 required but it was "));
        }
        ObservableObserveOn observableObserveOn = new ObservableObserveOn(observableDelay, schedulerMainThread, false, i);
        KeyguardSecIndicationController$$ExternalSyntheticLambda0 keyguardSecIndicationController$$ExternalSyntheticLambda0 = new KeyguardSecIndicationController$$ExternalSyntheticLambda0(this);
        Functions.OnErrorMissingConsumer onErrorMissingConsumer = Functions.ON_ERROR_MISSING;
        Functions.EmptyAction emptyAction = Functions.EMPTY_ACTION;
        Functions.EmptyConsumer emptyConsumer = Functions.EMPTY_CONSUMER;
        ObjectHelper.requireNonNull(onErrorMissingConsumer, "onError is null");
        ObjectHelper.requireNonNull(emptyAction, "onComplete is null");
        ObjectHelper.requireNonNull(emptyConsumer, "onSubscribe is null");
        observableObserveOn.subscribe(new LambdaObserver(keyguardSecIndicationController$$ExternalSyntheticLambda0, onErrorMissingConsumer, emptyAction, emptyConsumer));
    }

    public final boolean isAuthenticatedWithBiometric() {
        return ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isAuthenticatedWithBiometric(this.mSelectedUserInteractor.getSelectedUserId());
    }

    public final void onIndicationChanged(IndicationPosition indicationPosition, IndicationItem indicationItem) {
        KeyguardIndicationTextView keyguardIndicationTextView;
        KeyguardIndicationTextView keyguardIndicationTextView2;
        if (indicationPosition == null || indicationItem == null) {
            return;
        }
        if (!TextUtils.isEmpty(indicationItem.mText) && (!this.mVisible || this.mDozing)) {
            String str = String.format("onIndicationChanged() return - keyguard is not visible, pos = %7s, item = %s", indicationPosition, indicationItem);
            if (str.contains("OWNER_INFO")) {
                str = "onIndicationChanged() return - keyguard is not visible, skip ownerInfo";
            }
            Log.d("KeyguardSecIndicationController", str);
            return;
        }
        if (this.mKeyguardUpdateMonitor.isKeyguardUnlocking()) {
            Log.d("KeyguardSecIndicationController", "onIndicationChanged() returned - unlocking");
            changeIndication("", false, false);
            if ((this.mLockHelpTextVisible || TextUtils.isEmpty("")) && (keyguardIndicationTextView2 = this.mUpperTextView) != null) {
                keyguardIndicationTextView2.switchIndication("", null);
                return;
            }
            return;
        }
        if (((KeyguardEditModeControllerImpl) this.mKeyguardEditModeController).getVIRunning()) {
            Log.d("KeyguardSecIndicationController", "onIndicationChanged() returned - EditMode VIrunning");
            return;
        }
        boolean z = indicationItem.mPriority >= IndicationEventType.EMPTY_HIGH.getPriority() && !TextUtils.isEmpty(indicationItem.mText);
        SettableWakeLock settableWakeLock = this.mWakeLock;
        if (settableWakeLock != null) {
            settableWakeLock.setAcquired(z);
        }
        if (AnonymousClass9.$SwitchMap$com$android$systemui$statusbar$IndicationPosition[indicationPosition.ordinal()] != 2) {
            changeIndication(indicationItem.mText, indicationItem.mIsAnimation, indicationItem.mEventType == IndicationEventType.OWNER_INFO);
            return;
        }
        CharSequence charSequence = indicationItem.mText;
        if ((this.mLockHelpTextVisible || TextUtils.isEmpty(charSequence)) && (keyguardIndicationTextView = this.mUpperTextView) != null) {
            keyguardIndicationTextView.switchIndication(charSequence, null);
        }
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void onViewModeChanged(int i) throws Resources.NotFoundException {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onViewModeChanged mode: ", "KeyguardSecIndicationController");
        boolean z = i == 0;
        if (this.mIsDefaultLockViewMode != z) {
            this.mIsDefaultLockViewMode = z;
            if (this.mLockHelpTextVisible != z) {
                this.mLockHelpTextVisible = z;
                setVisible(z);
                KeyguardIndicationTextView keyguardIndicationTextView = this.mUpperTextView;
                if (keyguardIndicationTextView != null) {
                    keyguardIndicationTextView.setVisibility(this.mLockHelpTextVisible ? 0 : 8);
                    if (this.mLockHelpTextVisible) {
                        return;
                    }
                    this.mUpperTextView.setText("");
                }
            }
        }
    }

    public final void removeIndication(IndicationEventType indicationEventType) {
        IndicationPosition indicationPosition = IndicationPosition.DEFAULT;
        KeyguardSecIndicationPolicy keyguardSecIndicationPolicy = this.mIndicationPolicy;
        if (keyguardSecIndicationPolicy != null) {
            keyguardSecIndicationPolicy.removeIndicationEvent(indicationPosition, indicationEventType);
        }
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final void setDozing(boolean z) {
        KeyguardSecIndicationPolicy keyguardSecIndicationPolicy;
        if (this.mIsScreenOn && z && (keyguardSecIndicationPolicy = this.mIndicationPolicy) != null) {
            keyguardSecIndicationPolicy.removeAllIndications();
        }
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final void setIndicationArea(ViewGroup viewGroup) {
        super.setIndicationArea(viewGroup);
        this.mIndicationArea = viewGroup;
        this.mLifeStyleContainer = (LinearLayout) viewGroup.findViewById(com.android.systemui.R.id.life_style_view_area);
        this.mLifeStyleImageView = (SystemUIImageView) viewGroup.findViewById(com.android.systemui.R.id.keyguard_life_style_image);
        if (this.mLifeStyleContainer != null) {
            this.mLifeStyleContainer.setBackgroundResource(WallpaperUtils.isWhiteKeyguardWallpaper("bottom") ? com.android.systemui.R.drawable.rounded_bg_routine_mode_radius_whitebg : com.android.systemui.R.drawable.rounded_bg_routine_mode_radius);
            this.mLifeStyleContainer.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    KeyguardSecIndicationController keyguardSecIndicationController = this.f$0;
                    KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardSecIndicationController.mKeyguardUpdateMonitor;
                    boolean z = keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(keyguardSecIndicationController.mSelectedUserInteractor.getSelectedUserId());
                    Intent action = new Intent().setAction("com.samsung.android.app.routines.action.LAUNCH_MODE_LIST_DIALOG");
                    action.putExtra("lock_bouncer_enabled", z).setPackage("com.samsung.android.app.routines").addFlags(335544352);
                    try {
                        ActivityTaskManager.getService().startActivityAsUser((IApplicationThread) null, keyguardSecIndicationController.mContext.getBasePackageName(), keyguardSecIndicationController.mContext.getAttributionTag(), action, action.resolveTypeIfNeeded(keyguardSecIndicationController.mContext.getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, ActivityOptions.makeBasic().toBundle(), UserHandle.CURRENT.getIdentifier());
                    } catch (RemoteException unused) {
                        Log.e("KeyguardSecIndicationController", "Unexpected intent: " + action + " when Routine Mode clicked");
                    }
                }
            });
        }
        LinearLayout linearLayout = this.mLifeStyleContainer;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        this.mUpperTextView = (KeyguardIndicationTextView) viewGroup.findViewById(com.android.systemui.R.id.keyguard_upper_fingerprint_indication);
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final void setNowBarExpandMode(boolean z) throws Resources.NotFoundException {
        if (z) {
            if (this.mIndicationArea.getVisibility() == 0) {
                setVisible(false);
            }
        } else if (this.mIndicationArea.getVisibility() != 0) {
            setVisible(true);
        }
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final void setUpperTextView(KeyguardIndicationTextView keyguardIndicationTextView) {
        this.mUpperTextView = keyguardIndicationTextView;
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final void setVisible(boolean z) throws Resources.NotFoundException {
        boolean z2 = z & this.mLockHelpTextVisible & (!this.mKeyguardUpdateMonitor.isNowBarExpandMode());
        PluginLockData pluginLockData = this.mPluginLockData;
        if (pluginLockData != null && pluginLockData.isAvailable() && this.mIsDefaultLockViewMode) {
            z2 = pluginLockData.getVisibility(5) == 0;
        }
        super.setVisible(z2);
        if (z2) {
            addInitialIndication();
            if (this.mIsScreenOn) {
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                Completable.timer(100L, AndroidSchedulers.mainThread()).subscribe(new KeyguardSecIndicationController$$ExternalSyntheticLambda0(this));
                return;
            }
            return;
        }
        KeyguardIndicationTextView keyguardIndicationTextView = this.mUpperTextView;
        if (keyguardIndicationTextView != null) {
            keyguardIndicationTextView.setText("");
        }
        KeyguardIndicationTextView keyguardIndicationTextView2 = this.mTopIndicationView;
        if (keyguardIndicationTextView2 != null) {
            keyguardIndicationTextView2.setText("");
        }
        LinearLayout linearLayout = this.mLifeStyleContainer;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
    }

    public final void showBounceAnimation(KeyguardIndicationTextView keyguardIndicationTextView) {
        if (keyguardIndicationTextView != null) {
            keyguardIndicationTextView.setTranslationY(0.0f);
            keyguardIndicationTextView.startAnimation(AnimationUtils.loadAnimation(this.mContext, com.android.systemui.R.anim.giggle));
        }
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final void showTransientIndication(int i) {
        addIndication(IndicationEventType.LEGACY_TRANSIENT, this.mContext.getResources().getText(i));
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void updateDynamicLockData(String str) throws Resources.NotFoundException {
        setVisible(true);
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final void updateLifeStyleInfo(Intent intent) {
        intent.getBooleanExtra("mode_is_running", false);
        intent.getStringExtra("mode_display_name");
        byte[] byteArrayExtra = intent.getByteArrayExtra("mode_icon_byte_array");
        if (byteArrayExtra != null) {
            new BitmapDrawable(BitmapFactory.decodeByteArray(byteArrayExtra, 0, byteArrayExtra.length));
        }
    }

    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) throws Resources.NotFoundException {
        if (this.mLifeStyleContainer != null) {
            this.mLifeStyleContainer.setBackgroundResource(WallpaperUtils.isWhiteKeyguardWallpaper("bottom") ? com.android.systemui.R.drawable.rounded_bg_routine_mode_radius_whitebg : com.android.systemui.R.drawable.rounded_bg_routine_mode_radius);
            KeyguardIndicationTextView keyguardIndicationTextView = this.mLifeStyleIndicationView;
            if (keyguardIndicationTextView != null) {
                keyguardIndicationTextView.updateTextView();
                SystemUIImageView systemUIImageView = this.mLifeStyleImageView;
                if (systemUIImageView != null) {
                    systemUIImageView.setImageTintList(this.mLifeStyleIndicationView.getTextColors());
                }
            }
        }
    }

    public final void addIndicationTimeout(IndicationPosition indicationPosition, IndicationEventType indicationEventType, CharSequence charSequence, ColorStateList colorStateList, boolean z) {
        if (this.mKeyguardUpdateMonitor.isKeyguardUnlocking()) {
            Log.d("KeyguardSecIndicationController", "addIndicationTimeout() returned - unlocking");
            return;
        }
        KeyguardSecIndicationPolicy keyguardSecIndicationPolicy = this.mIndicationPolicy;
        if (keyguardSecIndicationPolicy != null) {
            keyguardSecIndicationPolicy.addIndicationEvent(indicationPosition, indicationEventType, charSequence, colorStateList, 3000L, z);
        }
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final void showTransientIndication(CharSequence charSequence) {
        addIndication(IndicationEventType.LEGACY_TRANSIENT, charSequence);
    }

    public final void addIndication(IndicationPosition indicationPosition, IndicationEventType indicationEventType, CharSequence charSequence, ColorStateList colorStateList) {
        if (this.mKeyguardUpdateMonitor.isKeyguardUnlocking()) {
            Log.d("KeyguardSecIndicationController", "addIndication() returned - unlocking");
            return;
        }
        KeyguardSecIndicationPolicy keyguardSecIndicationPolicy = this.mIndicationPolicy;
        if (keyguardSecIndicationPolicy != null) {
            keyguardSecIndicationPolicy.addIndicationEvent(indicationPosition, indicationEventType, charSequence, colorStateList, -1L, false);
        }
    }
}
