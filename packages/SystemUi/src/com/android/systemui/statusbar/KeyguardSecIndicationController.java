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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
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
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import com.android.internal.app.IBatteryStats;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.ViewClippingUtil;
import com.android.keyguard.FaceAuthUiEvent;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.SecCountDownTimer;
import com.android.keyguard.SecRotationWatcher;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.PowerUiRune;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.FaceHelpMessageDeferral;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dock.DockManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$1;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.pluginlock.PluginLockData;
import com.android.systemui.pluginlock.PluginLockDataImpl;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.pluginlock.listener.PluginLockListener$State;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.KeyguardSecIndicationController;
import com.android.systemui.statusbar.phone.BounceInterpolator;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.KeyguardIndicationTextView;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView;
import com.android.systemui.statusbar.phone.KeyguardUsimTextView;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIImageView;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.android.systemui.widget.SystemUIWidgetUtil;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardSecIndicationController extends KeyguardIndicationController implements IndicationChangeListener, SystemUIWidgetCallback, PluginLockListener$State {
    public final AccessibilityManager mAccessibilityManager;
    public final KeyguardBatteryMeterDrawable mBatteryDrawable;
    public CountDownTimer mBiometricsCountdownTimer;
    public final BounceInterpolator mBounceInterpolator;
    public final C25402 mClippingParams;
    public SecCountDownTimer mCountDownTimer;
    public final C25457 mDisplayListener;
    public final C25371 mEditModeListener;
    public final ColorStateList mErrorColor;
    public View mIndicationArea;
    public final KeyguardSecIndicationPolicy mIndicationPolicy;
    public boolean mIsDefaultLockViewMode;
    public boolean mIsFpGuidePos;
    public boolean mIsScreenOn;
    public boolean mIsUsbRestricted;
    public final KeyguardEditModeController mKeyguardEditModeController;
    public final KeyguardStateController mKeyguardStateController;
    public final C25413 mKeyguardStateControllerCallback;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public LinearLayout mLifeStyleContainer;
    public boolean mLifeStyleEnable;
    public Drawable mLifeStyleIcon;
    public SystemUIImageView mLifeStyleImageView;
    public String mLifeStyleName;
    public boolean mLockHelpTextVisible;
    public final PluginLockData mPluginLockData;
    public final PluginLockStarManager mPluginLockStarManager;
    public SecKeyguardCallback mUpdateMonitorCallback;
    public KeyguardIndicationTextView mUpperTextView;
    public LinearLayout mUsimTextArea;
    public KeyguardUsimTextView mUsimTextView;
    public final C25446 mWakefulnessObserver;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.KeyguardSecIndicationController$11 */
    public abstract /* synthetic */ class AbstractC253911 {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SecKeyguardCallback extends KeyguardIndicationController.BaseKeyguardCallback {
        public int mLastSuccessiveErrorMessage;

        public SecKeyguardCallback() {
            super();
        }

        @Override // com.android.systemui.statusbar.KeyguardIndicationController.BaseKeyguardCallback, com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
            BiometricSourceType biometricSourceType2 = BiometricSourceType.FACE;
            KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
            if (biometricSourceType == biometricSourceType2 && ((SettingsHelper) Dependency.get(SettingsHelper.class)).isEnabledFaceStayOnLock()) {
                if ((keyguardSecIndicationController.mVisible && !keyguardSecIndicationController.mDozing) && keyguardSecIndicationController.mIsScreenOn) {
                    KeyguardSecIndicationController.m1703$$Nest$mupdateDefaultIndications(keyguardSecIndicationController);
                    keyguardSecIndicationController.showBounceAnimation(keyguardSecIndicationController.mUpperTextView);
                    keyguardSecIndicationController.removeIndication(IndicationEventType.BIOMETRICS_HELP);
                }
            }
            KeyguardSecIndicationPolicy keyguardSecIndicationPolicy = keyguardSecIndicationController.mIndicationPolicy;
            if (keyguardSecIndicationPolicy != null) {
                keyguardSecIndicationPolicy.removeAllIndications();
            }
            keyguardSecIndicationController.removeIndication(IndicationEventType.BIOMETRICS_HELP);
        }

        /* JADX WARN: Code restructure failed: missing block: B:37:0x009f, code lost:
        
            if ((com.android.systemui.util.DeviceType.supportInDisplayFingerprint == 1) == false) goto L54;
         */
        @Override // com.android.systemui.statusbar.KeyguardIndicationController.BaseKeyguardCallback, com.android.keyguard.KeyguardUpdateMonitorCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
            if (biometricSourceType == BiometricSourceType.FINGERPRINT ? shouldSuppressFingerprintError(i) : biometricSourceType == BiometricSourceType.FACE && (((KeyguardIndicationController.this.mKeyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true) ^ true) && i != 9) || i == 5 || i == 2)) {
                return;
            }
            KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = keyguardSecIndicationController.mStatusBarKeyguardViewManager;
            if (statusBarKeyguardViewManager != null ? statusBarKeyguardViewManager.isBouncerShowing() : false) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT && this.mLastSuccessiveErrorMessage != i) {
                    keyguardSecIndicationController.mStatusBarKeyguardViewManager.setKeyguardMessage(str, keyguardSecIndicationController.mInitialTextColorState);
                }
            } else if (keyguardUpdateMonitor.mDeviceInteractive) {
                String str2 = LsRune.VALUE_CONFIG_CARRIER_TEXT_POLICY;
                Context context = keyguardSecIndicationController.mContext;
                ColorStateList colorAttr = Utils.getColorAttr(R.attr.colorError, context);
                if (biometricSourceType != BiometricSourceType.FACE) {
                    int rotation = context.getResources().getConfiguration().windowConfiguration.getRotation();
                    if (biometricSourceType == BiometricSourceType.FINGERPRINT && DeviceState.isInDisplayFpSensorPositionHigh() && rotation == 0) {
                        KeyguardSecIndicationController keyguardSecIndicationController2 = KeyguardSecIndicationController.this;
                        IndicationPosition indicationPosition = IndicationPosition.UPPER;
                        IndicationEventType indicationEventType = IndicationEventType.BIOMETRICS_HELP;
                        keyguardSecIndicationController2.addIndicationTimeout(indicationPosition, indicationEventType, str, colorAttr, false);
                        keyguardSecIndicationController.removeIndication(indicationEventType);
                    } else {
                        if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                            if (DeviceType.supportInDisplayFingerprint == -1) {
                                DeviceType.supportInDisplayFingerprint = 1;
                            }
                        }
                        IndicationEventType indicationEventType2 = IndicationEventType.BIOMETRICS_HELP;
                        if (AbstractC253911.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()] == 2) {
                            str = "";
                        }
                        keyguardSecIndicationController.addIndicationTimeout(indicationEventType2, str, colorAttr, false);
                    }
                    keyguardSecIndicationController.showBounceAnimation(keyguardSecIndicationController.mUpperTextView);
                }
            }
            this.mLastSuccessiveErrorMessage = i;
        }

        @Override // com.android.systemui.statusbar.KeyguardIndicationController.BaseKeyguardCallback, com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricHelp(int i, String str, BiometricSourceType biometricSourceType) {
            boolean z;
            if (biometricSourceType != BiometricSourceType.FINGERPRINT) {
                return;
            }
            KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
            if (keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true)) {
                KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
                StatusBarKeyguardViewManager statusBarKeyguardViewManager = keyguardSecIndicationController.mStatusBarKeyguardViewManager;
                boolean isBouncerShowing = statusBarKeyguardViewManager != null ? statusBarKeyguardViewManager.isBouncerShowing() : false;
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = keyguardSecIndicationController.mKeyguardUpdateMonitor;
                if (isBouncerShowing) {
                    if (keyguardUpdateMonitor2.getLockoutAttemptDeadline() > 0) {
                        z = true;
                    } else {
                        SecCountDownTimer secCountDownTimer = keyguardSecIndicationController.mCountDownTimer;
                        if (secCountDownTimer != null) {
                            secCountDownTimer.cancel();
                            keyguardSecIndicationController.mCountDownTimer = null;
                            keyguardSecIndicationController.removeIndication(IndicationEventType.PPP_COOLDOWN);
                        }
                        z = false;
                    }
                    if (!z && !TextUtils.isEmpty(str)) {
                        keyguardSecIndicationController.mStatusBarKeyguardViewManager.setKeyguardMessage(str, keyguardSecIndicationController.mInitialTextColorState);
                        this.mLastSuccessiveErrorMessage = -1;
                    }
                }
                if (keyguardSecIndicationController.mScreenLifecycle.mScreenState == 2 && !keyguardUpdateMonitor.mGoingToSleep) {
                    Context context = keyguardSecIndicationController.mContext;
                    if (i == 1) {
                        str = context.getString(com.android.systemui.R.string.kg_fingerprint_acquired_partial);
                    } else if (i == 2) {
                        str = context.getString(com.android.systemui.R.string.kg_fingerprint_acquired_insufficient);
                    } else if (i == 3) {
                        str = context.getString(com.android.systemui.R.string.kg_fingerprint_acquired_image_dirty);
                    } else if (i == 5) {
                        str = context.getString(com.android.systemui.R.string.kg_fingerprint_acquired_too_fast);
                    } else if (i == 1001) {
                        str = context.getString(com.android.systemui.R.string.kg_fingerprint_acquired_too_wet);
                    } else if (i == 1003) {
                        str = context.getString(com.android.systemui.R.string.kg_fingerprint_acquired_light);
                    } else if (i == 1004) {
                        str = context.getString(com.android.systemui.R.string.kg_fingerprint_acquired_tsp_block);
                    }
                    String str2 = str;
                    int rotation = context.getResources().getConfiguration().windowConfiguration.getRotation();
                    Point point = DeviceState.sDisplaySize;
                    if (!DeviceType.isTablet() || !LsRune.SECURITY_FINGERPRINT_IN_DISPLAY || rotation != 2) {
                        if (DeviceState.isInDisplayFpSensorPositionHigh() && rotation == 0) {
                            keyguardSecIndicationController.addIndicationTimeout(IndicationPosition.UPPER, IndicationEventType.BIOMETRICS_HELP, str2, keyguardSecIndicationController.mErrorColor, false);
                            keyguardSecIndicationController.showBounceAnimation(keyguardSecIndicationController.mUpperTextView);
                        } else {
                            if (DeviceType.supportInDisplayFingerprint == -1) {
                                DeviceType.supportInDisplayFingerprint = 1;
                            }
                            if (DeviceType.supportInDisplayFingerprint == 1) {
                                keyguardSecIndicationController.addIndicationTimeout(IndicationEventType.BIOMETRICS_HELP, str2, keyguardSecIndicationController.mErrorColor, false);
                                keyguardSecIndicationController.showBounceAnimation(keyguardSecIndicationController.mTopIndicationView);
                            }
                        }
                    }
                    if (i == -1 && keyguardSecIndicationController.mAccessibilityManager.isTouchExplorationEnabled() && !keyguardUpdateMonitor2.isMaxFailedBiometricUnlockAttemptsShort()) {
                        keyguardSecIndicationController.removeIndication(IndicationEventType.UNLOCK_GUIDE);
                        Completable.timer(1000L, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController$SecKeyguardCallback$$ExternalSyntheticLambda0
                            @Override // io.reactivex.functions.Action
                            public final void run() {
                                KeyguardSecIndicationController.SecKeyguardCallback secKeyguardCallback = KeyguardSecIndicationController.SecKeyguardCallback.this;
                                KeyguardSecIndicationController keyguardSecIndicationController2 = KeyguardSecIndicationController.this;
                                String string = keyguardSecIndicationController2.mContext.getString(com.android.systemui.R.string.kg_fingerprint_retry_notification);
                                if (keyguardSecIndicationController2.mKeyguardUpdateMonitor.isFingerprintLeave()) {
                                    KeyguardSecIndicationController.m1703$$Nest$mupdateDefaultIndications(keyguardSecIndicationController2);
                                    return;
                                }
                                IndicationEventType indicationEventType = IndicationEventType.BIOMETRICS_HELP;
                                KeyguardSecIndicationController keyguardSecIndicationController3 = KeyguardSecIndicationController.this;
                                keyguardSecIndicationController3.addIndicationTimeout(indicationEventType, string, keyguardSecIndicationController3.mErrorColor, false);
                            }
                        });
                    }
                }
                this.mLastSuccessiveErrorMessage = -1;
            }
        }

        @Override // com.android.systemui.statusbar.KeyguardIndicationController.BaseKeyguardCallback, com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricRunningStateChanged(BiometricSourceType biometricSourceType, boolean z) {
            KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
            if ((keyguardSecIndicationController.mVisible && !keyguardSecIndicationController.mDozing) && keyguardSecIndicationController.mIsScreenOn) {
                if (z) {
                    KeyguardSecIndicationController.m1703$$Nest$mupdateDefaultIndications(keyguardSecIndicationController);
                    keyguardSecIndicationController.addIndicationTimeout(IndicationEventType.BIOMETRICS_STOP, "", keyguardSecIndicationController.mInitialTextColorState, false);
                } else {
                    keyguardSecIndicationController.removeIndication(IndicationEventType.BIOMETRICS_STOP);
                    if (KeyguardSecIndicationController.isAuthenticatedWithBiometric()) {
                        return;
                    }
                    Completable.timer(100L, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController$SecKeyguardCallback$$ExternalSyntheticLambda1
                        @Override // io.reactivex.functions.Action
                        public final void run() {
                            KeyguardSecIndicationController.m1703$$Nest$mupdateDefaultIndications(KeyguardSecIndicationController.this);
                        }
                    });
                }
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

        /* JADX WARN: Removed duplicated region for block: B:12:0x0081  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x00cf  */
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onLockModeChanged() {
            final KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
            long lockoutAttemptDeadline = keyguardSecIndicationController.mKeyguardUpdateMonitor.getLockoutAttemptDeadline();
            KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardSecIndicationController.mKeyguardUpdateMonitor;
            long lockoutBiometricAttemptDeadline = keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline();
            StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("onLockModeChanged - ", lockoutAttemptDeadline, " | ");
            m17m.append(lockoutBiometricAttemptDeadline);
            Log.d("KeyguardSecIndicationController", m17m.toString());
            long j = 0;
            if (lockoutAttemptDeadline > 0) {
                Log.d("KeyguardSecIndicationController", "startCountdownTimer - " + lockoutAttemptDeadline);
                if (!keyguardUpdateMonitor.isPerformingWipeOut()) {
                    SecCountDownTimer secCountDownTimer = keyguardSecIndicationController.mCountDownTimer;
                    if (secCountDownTimer != null) {
                        secCountDownTimer.cancel();
                        keyguardSecIndicationController.mCountDownTimer = null;
                    }
                    SecCountDownTimer secCountDownTimer2 = new SecCountDownTimer(lockoutAttemptDeadline - SystemClock.elapsedRealtime(), 1000L, keyguardSecIndicationController.mContext, keyguardSecIndicationController.mKeyguardUpdateMonitor, null, false) { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.9
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
                    keyguardSecIndicationController.mCountDownTimer = secCountDownTimer2;
                    secCountDownTimer2.start();
                }
                if (lockoutBiometricAttemptDeadline <= j) {
                    Log.d("KeyguardSecIndicationController", "startBiometricCountdownTimer - " + lockoutBiometricAttemptDeadline);
                    if (!keyguardUpdateMonitor.isPerformingWipeOut()) {
                        CountDownTimer countDownTimer = keyguardSecIndicationController.mBiometricsCountdownTimer;
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                            keyguardSecIndicationController.mBiometricsCountdownTimer = null;
                        }
                        KeyguardIndicationTextView keyguardIndicationTextView = keyguardSecIndicationController.mUpperTextView;
                        if (keyguardIndicationTextView != null && !TextUtils.isEmpty(keyguardIndicationTextView.getText())) {
                            keyguardSecIndicationController.mUpperTextView.setText("");
                        }
                        keyguardSecIndicationController.mBiometricsCountdownTimer = new CountDownTimer(lockoutBiometricAttemptDeadline - SystemClock.elapsedRealtime(), 1000L) { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.8
                            public final int attemptRemainingBeforeWipe;
                            public final int biometricType;

                            {
                                super(r2, r4);
                                this.attemptRemainingBeforeWipe = KeyguardSecIndicationController.this.mKeyguardUpdateMonitor.getRemainingAttempt(1);
                                this.biometricType = KeyguardSecIndicationController.this.mKeyguardUpdateMonitor.getBiometricType(KeyguardUpdateMonitor.getCurrentUser());
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
                                String str;
                                int round = (int) Math.round(j2 / 1000.0d);
                                int ceil = (int) Math.ceil(round / 60.0d);
                                if (this.attemptRemainingBeforeWipe > 0) {
                                    StringBuilder sb = new StringBuilder();
                                    Resources resources = KeyguardSecIndicationController.this.mContext.getResources();
                                    int i = this.attemptRemainingBeforeWipe;
                                    str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, resources.getQuantityString(com.android.systemui.R.plurals.kg_attempt_left, i, Integer.valueOf(i)), "\n");
                                } else {
                                    str = "";
                                }
                                if (round > 60) {
                                    StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str);
                                    m18m.append(KeyguardSecIndicationController.this.mContext.getResources().getQuantityString(com.android.systemui.R.plurals.kg_too_many_failed_attempts_countdown_min, ceil, Integer.valueOf(ceil)));
                                    str = m18m.toString();
                                } else if (round <= 60 && round > 0) {
                                    int i2 = this.biometricType;
                                    if (i2 == 1) {
                                        StringBuilder m18m2 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str);
                                        m18m2.append(KeyguardSecIndicationController.this.mContext.getResources().getQuantityString(com.android.systemui.R.plurals.kg_too_many_failed_attempts_countdown_fingerprint, round, Integer.valueOf(round)));
                                        str = m18m2.toString();
                                    } else if (i2 != 256) {
                                        StringBuilder m18m3 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str);
                                        m18m3.append(KeyguardSecIndicationController.this.mContext.getResources().getQuantityString(com.android.systemui.R.plurals.kg_too_many_failed_attempts_countdown_biometric, round, Integer.valueOf(round)));
                                        str = m18m3.toString();
                                    } else {
                                        StringBuilder m18m4 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str);
                                        m18m4.append(KeyguardSecIndicationController.this.mContext.getResources().getQuantityString(com.android.systemui.R.plurals.kg_too_many_failed_attempts_countdown_face, round, Integer.valueOf(round)));
                                        str = m18m4.toString();
                                    }
                                }
                                if (str.isEmpty() || KeyguardSecIndicationController.this.mKeyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser())) {
                                    return;
                                }
                                RecyclerView$$ExternalSyntheticOutline0.m46m(ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("BiometricsCountdownTimer - ", str, " biometricType : "), this.biometricType, "KeyguardSecIndicationController");
                                KeyguardSecIndicationController.this.addIndication(IndicationEventType.BIOMETRICS_COOLDOWN, str);
                            }
                        }.start();
                    }
                } else {
                    CountDownTimer countDownTimer2 = keyguardSecIndicationController.mBiometricsCountdownTimer;
                    if (countDownTimer2 != null) {
                        countDownTimer2.cancel();
                        keyguardSecIndicationController.mBiometricsCountdownTimer = null;
                        keyguardSecIndicationController.removeIndication(IndicationEventType.BIOMETRICS_COOLDOWN);
                    }
                }
                keyguardSecIndicationController.addIndication(IndicationEventType.UNLOCK_GUIDE, keyguardSecIndicationController.getUnlockGuideText());
            }
            SecCountDownTimer secCountDownTimer3 = keyguardSecIndicationController.mCountDownTimer;
            if (secCountDownTimer3 != null) {
                secCountDownTimer3.cancel();
                keyguardSecIndicationController.mCountDownTimer = null;
                keyguardSecIndicationController.removeIndication(IndicationEventType.PPP_COOLDOWN);
            }
            j = 0;
            if (lockoutBiometricAttemptDeadline <= j) {
            }
            keyguardSecIndicationController.addIndication(IndicationEventType.UNLOCK_GUIDE, keyguardSecIndicationController.getUnlockGuideText());
        }

        @Override // com.android.systemui.statusbar.KeyguardIndicationController.BaseKeyguardCallback, com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onRefreshBatteryInfo(KeyguardBatteryStatus keyguardBatteryStatus) {
            super.onRefreshBatteryInfo(keyguardBatteryStatus);
            KeyguardSecIndicationController.this.addInitialIndication();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onSimStateChanged(int i, int i2, int i3) {
            postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController$SecKeyguardCallback$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardSecIndicationController.this.addLifeStyleIndication();
                }
            }, 500L);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onStrongAuthStateChanged(int i) {
            KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
            keyguardSecIndicationController.getClass();
            int strongAuthForUser = keyguardSecIndicationController.mKeyguardUpdateMonitor.mStrongAuthTracker.getStrongAuthForUser(KeyguardUpdateMonitor.getCurrentUser());
            if (((strongAuthForUser & 1) == 0 && (strongAuthForUser & 2) == 0 && (strongAuthForUser & 4) == 0 && (strongAuthForUser & 8) == 0 && (strongAuthForUser & 16) == 0 && (strongAuthForUser & 32) == 0) ? false : true) {
                CountDownTimer countDownTimer = keyguardSecIndicationController.mBiometricsCountdownTimer;
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    keyguardSecIndicationController.mBiometricsCountdownTimer = null;
                    keyguardSecIndicationController.removeIndication(IndicationEventType.BIOMETRICS_COOLDOWN);
                }
                keyguardSecIndicationController.addIndication(IndicationEventType.UNLOCK_GUIDE, keyguardSecIndicationController.getUnlockGuideText());
            }
        }

        @Override // com.android.systemui.statusbar.KeyguardIndicationController.BaseKeyguardCallback, com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTrustAgentErrorMessage(CharSequence charSequence) {
            IndicationEventType indicationEventType = IndicationEventType.TRUST_AGENT_ERROR;
            KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
            keyguardSecIndicationController.addIndication(IndicationPosition.DEFAULT, indicationEventType, charSequence, keyguardSecIndicationController.mErrorColor);
        }

        @Override // com.android.systemui.statusbar.KeyguardIndicationController.BaseKeyguardCallback, com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTrustChanged(int i) {
            Completable.timer(100L, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController$SecKeyguardCallback$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Action
                public final void run() {
                    KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
                    if (keyguardSecIndicationController.mKeyguardUpdateMonitor.isKeyguardUnlocking()) {
                        return;
                    }
                    IndicationEventType indicationEventType = IndicationEventType.TRUST_AGENT_HELP;
                    keyguardSecIndicationController.removeIndication(indicationEventType);
                    if (!keyguardSecIndicationController.mKeyguardUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser())) {
                        indicationEventType = IndicationEventType.UNLOCK_GUIDE;
                    }
                    keyguardSecIndicationController.addIndication(indicationEventType, keyguardSecIndicationController.getUnlockGuideText());
                }
            });
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUSBRestrictionChanged(boolean z) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onUSBRestrictionChanged ", z, "KeyguardSecIndicationController");
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
    public static void m1703$$Nest$mupdateDefaultIndications(final KeyguardSecIndicationController keyguardSecIndicationController) {
        keyguardSecIndicationController.addInitialIndication();
        keyguardSecIndicationController.addLifeStyleIndication();
        keyguardSecIndicationController.addIndication(isAuthenticatedWithBiometric() ? IndicationEventType.BIOMETRICS_HELP : IndicationEventType.UNLOCK_GUIDE, keyguardSecIndicationController.getUnlockGuideText());
        if (keyguardSecIndicationController.mIsFpGuidePos) {
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    int i;
                    KeyguardSecIndicationController keyguardSecIndicationController2 = KeyguardSecIndicationController.this;
                    keyguardSecIndicationController2.getClass();
                    boolean z = LsRune.SECURITY_FINGERPRINT_IN_DISPLAY;
                    Context context = keyguardSecIndicationController2.mContext;
                    if (z) {
                        int rotation = context.getResources().getConfiguration().windowConfiguration.getRotation();
                        i = rotation != 1 ? rotation != 2 ? rotation != 3 ? com.android.systemui.R.string.kg_in_display_fingerprint_sensor_0_help_instructions : com.android.systemui.R.string.kg_in_display_fingerprint_sensor_270_help_instructions : com.android.systemui.R.string.kg_in_display_fingerprint_sensor_180_help_instructions : com.android.systemui.R.string.kg_in_display_fingerprint_sensor_90_help_instructions;
                    } else {
                        i = 0;
                    }
                    if (i != 0) {
                        keyguardSecIndicationController2.mTopIndicationView.announceForAccessibility(context.getString(i));
                    }
                }
            };
            KeyguardIndicationController.HandlerC25152 handlerC25152 = keyguardSecIndicationController.mHandler;
            if (handlerC25152.hasCallbacks(runnable)) {
                handlerC25152.removeCallbacks(runnable);
            }
            if (keyguardSecIndicationController.mVisible && !keyguardSecIndicationController.mDozing) {
                StatusBarKeyguardViewManager statusBarKeyguardViewManager = keyguardSecIndicationController.mStatusBarKeyguardViewManager;
                if (statusBarKeyguardViewManager != null ? statusBarKeyguardViewManager.isBouncerShowing() : false) {
                    return;
                }
                handlerC25152.postDelayed(runnable, 3000L);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.android.systemui.statusbar.KeyguardSecIndicationController$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.android.systemui.statusbar.KeyguardSecIndicationController$2] */
    /* JADX WARN: Type inference failed for: r7v1, types: [com.android.systemui.statusbar.KeyguardSecIndicationController$3, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.android.systemui.statusbar.KeyguardSecIndicationController$6, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r9v0, types: [com.android.systemui.statusbar.KeyguardSecIndicationController$7] */
    public KeyguardSecIndicationController(Context context, Looper looper, WakeLock.Builder builder, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, DockManager dockManager, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager, IBatteryStats iBatteryStats, UserManager userManager, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, FalsingManager falsingManager, AuthController authController, LockPatternUtils lockPatternUtils, ScreenLifecycle screenLifecycle, KeyguardBypassController keyguardBypassController, AccessibilityManager accessibilityManager, FaceHelpMessageDeferral faceHelpMessageDeferral, KeyguardLogger keyguardLogger, AlternateBouncerInteractor alternateBouncerInteractor, AlarmManager alarmManager, UserTracker userTracker, FeatureFlags featureFlags, SecRotationWatcher secRotationWatcher, PluginLockMediator pluginLockMediator, PluginLockData pluginLockData, PluginLockStarManager pluginLockStarManager, KeyguardEditModeController keyguardEditModeController) {
        super(context, looper, builder, keyguardStateController, statusBarStateController, keyguardUpdateMonitor, dockManager, broadcastDispatcher, devicePolicyManager, iBatteryStats, userManager, delayableExecutor, delayableExecutor2, falsingManager, authController, lockPatternUtils, screenLifecycle, keyguardBypassController, accessibilityManager, faceHelpMessageDeferral, keyguardLogger, alternateBouncerInteractor, alarmManager, userTracker, featureFlags);
        this.mIsScreenOn = true;
        this.mIsUsbRestricted = false;
        ?? r6 = new KeyguardEditModeController.Listener() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.1
            @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
            public final void onAnimationEnded() {
                KeyguardSecIndicationController.this.setVisible(true);
            }

            @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
            public final void onAnimationStarted(boolean z) {
                KeyguardSecIndicationController.this.setVisible(false);
            }
        };
        this.mEditModeListener = r6;
        this.mClippingParams = new ViewClippingUtil.ClippingParameters() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.2
            public final boolean shouldFinish(View view) {
                return view == KeyguardSecIndicationController.this.mIndicationArea;
            }
        };
        this.mLockHelpTextVisible = true;
        this.mIsDefaultLockViewMode = true;
        ?? r7 = new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.3
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onPrimaryBouncerShowingChanged() {
                KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
                if (((KeyguardStateControllerImpl) keyguardSecIndicationController.mKeyguardStateController).mPrimaryBouncerShowing) {
                    keyguardSecIndicationController.mIndicationPolicy.removeAllIndications();
                } else {
                    keyguardSecIndicationController.setVisible(true);
                }
            }
        };
        this.mKeyguardStateControllerCallback = r7;
        ?? r8 = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.6
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
            public final void onStartedWakingUp() {
                KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
                keyguardSecIndicationController.mIsScreenOn = true;
                keyguardSecIndicationController.addInitialIndication();
                keyguardSecIndicationController.addLifeStyleIndication();
                keyguardSecIndicationController.addIndication(IndicationEventType.UNLOCK_GUIDE, keyguardSecIndicationController.getUnlockGuideText());
            }
        };
        this.mWakefulnessObserver = r8;
        this.mDisplayListener = new DisplayLifecycle.Observer(this) { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.7
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                String str = LsRune.VALUE_CONFIG_CARRIER_TEXT_POLICY;
            }
        };
        this.mKeyguardStateController = keyguardStateController;
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(r7);
        this.mAccessibilityManager = accessibilityManager;
        KeyguardSecIndicationPolicy keyguardSecIndicationPolicy = new KeyguardSecIndicationPolicy();
        this.mIndicationPolicy = keyguardSecIndicationPolicy;
        keyguardSecIndicationPolicy.mListenerList.add(this);
        keyguardSecIndicationPolicy.mTopItemMap.keySet().stream().forEach(new KeyguardSecIndicationPolicy$$ExternalSyntheticLambda1(0, keyguardSecIndicationPolicy, this));
        this.mBatteryDrawable = new KeyguardBatteryMeterDrawable(context);
        this.mBounceInterpolator = new BounceInterpolator();
        this.mKnoxStateMonitor = (KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class);
        this.mKeyguardEditModeController = keyguardEditModeController;
        ((ArrayList) ((KeyguardEditModeControllerImpl) keyguardEditModeController).listeners).add(r6);
        this.mPluginLockStarManager = pluginLockStarManager;
        pluginLockStarManager.registerCallback("KeyguardSecIndicationController", new PluginLockStarManager.LockStarCallback() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.5
            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final void onChangedLockStarEnabled(boolean z) {
                if (z) {
                    return;
                }
                KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
                keyguardSecIndicationController.mTopIndicationView.updateFontSizeInKeyguardBoundary(true, keyguardSecIndicationController.mContext.getResources().getConfiguration());
                keyguardSecIndicationController.mTopIndicationView.updateTextView();
            }
        });
        WallpaperUtils.registerSystemUIWidgetCallback(this, SystemUIWidgetUtil.convertFlag("bottom"));
        ((WakefulnessLifecycle) Dependency.get(WakefulnessLifecycle.class)).addObserver(r8);
        String str = LsRune.VALUE_CONFIG_CARRIER_TEXT_POLICY;
        ((PluginLockMediatorImpl) pluginLockMediator).registerStateCallback(this);
        this.mPluginLockData = pluginLockData;
        this.mErrorColor = ColorStateList.valueOf(-1);
        IndicationEventType indicationEventType = IndicationEventType.EMPTY_LOW;
        addIndication(indicationEventType, "");
        IndicationPosition indicationPosition = IndicationPosition.UPPER;
        addIndication(indicationPosition, indicationEventType, "", this.mInitialTextColorState);
        if (LsRune.SECURITY_SIM_PERM_DISABLED && this.mKeyguardUpdateMonitor.isIccBlockedPermanently()) {
            IndicationEventType indicationEventType2 = IndicationEventType.EMPTY_HIGH;
            addIndication(indicationEventType2, "");
            addIndication(indicationPosition, indicationEventType2, "", this.mInitialTextColorState);
        }
    }

    public static boolean isAuthenticatedWithBiometric() {
        return ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).isAuthenticatedWithBiometric(KeyguardUpdateMonitor.getCurrentUser());
    }

    public final SpannableStringBuilder AddBatteryIcon(String str) {
        KeyguardIndicationTextView keyguardIndicationTextView = this.mTopIndicationView;
        KeyguardBatteryMeterDrawable keyguardBatteryMeterDrawable = this.mBatteryDrawable;
        if (keyguardIndicationTextView != null) {
            float textSize = keyguardIndicationTextView.getTextSize();
            keyguardBatteryMeterDrawable.batteryLevel = this.mBatteryLevel;
            int currentTextColor = this.mTopIndicationView.getCurrentTextColor();
            keyguardBatteryMeterDrawable.darkIntensity = ((Color.red(currentTextColor) + Color.green(currentTextColor)) + Color.blue(currentTextColor)) / 3 <= 128 ? 1.0f : 0.0f;
            int i = (int) textSize;
            keyguardBatteryMeterDrawable.setBounds(0, 0, (i * 8) / 14, i);
            keyguardBatteryMeterDrawable.batteryLevelColor = this.mTopIndicationView.getCurrentTextColor();
            Paint paint = keyguardBatteryMeterDrawable.batteryLevelBackgroundPaint;
            float f = keyguardBatteryMeterDrawable.darkIntensity;
            int i2 = keyguardBatteryMeterDrawable.batteryLevelBackgroundLightColor;
            int i3 = keyguardBatteryMeterDrawable.batteryLevelBackgroundDarkColor;
            ArgbEvaluator argbEvaluator = ArgbEvaluator.sInstance;
            paint.setColor(((Integer) argbEvaluator.evaluate(f, Integer.valueOf(i2), Integer.valueOf(i3))).intValue());
            keyguardBatteryMeterDrawable.batteryLightningBoltLightPaint.setColor(keyguardBatteryMeterDrawable.batteryLevelColor);
            keyguardBatteryMeterDrawable.batteryLightningBoltDarkPaint.setColor(((Integer) argbEvaluator.evaluate(keyguardBatteryMeterDrawable.darkIntensity, Integer.valueOf(keyguardBatteryMeterDrawable.batteryLightningBoltDarkColor), Integer.valueOf(keyguardBatteryMeterDrawable.batteryLightningBoltLightColor))).intValue());
            keyguardBatteryMeterDrawable.invalidateSelf();
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("  ");
        spannableStringBuilder.append((CharSequence) str);
        int i4 = keyguardBatteryMeterDrawable.width;
        if (i4 <= 0) {
            i4 = 1;
        }
        int i5 = keyguardBatteryMeterDrawable.height;
        if (i5 <= 0) {
            i5 = 1;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i4, i5, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        keyguardBatteryMeterDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        keyguardBatteryMeterDrawable.draw(canvas);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), createBitmap);
        bitmapDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        spannableStringBuilder.setSpan(new ImageSpan(bitmapDrawable, 2), 0, 1, 33);
        return spannableStringBuilder;
    }

    public final void addIndication(IndicationEventType indicationEventType, CharSequence charSequence) {
        addIndication(IndicationPosition.DEFAULT, indicationEventType, charSequence, this.mInitialTextColorState);
    }

    public final void addIndicationTimeout(IndicationEventType indicationEventType, CharSequence charSequence, ColorStateList colorStateList, boolean z) {
        addIndicationTimeout(IndicationPosition.DEFAULT, indicationEventType, charSequence, colorStateList, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:208:0x002d, code lost:
    
        if ((r6 & 32) != 0) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x03ef, code lost:
    
        if (((r0.mKnoxCustomLockScreenHiddenItems & 32) != 0 ? r6 : r3) != false) goto L195;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x03f3, code lost:
    
        if (r3 == false) goto L214;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x0396 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0380  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x03ca  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x03e0  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x03fa  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x03d7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addInitialIndication() {
        CustomSdkMonitor customSdkMonitor;
        boolean z;
        KeyguardBatteryStatus keyguardBatteryStatus;
        boolean z2;
        boolean z3;
        KnoxStateMonitor knoxStateMonitor;
        String string;
        String string2;
        CharSequence charSequence;
        CharSequence AddBatteryIcon;
        boolean z4;
        int i;
        int i2;
        String str;
        String str2;
        boolean z5;
        CharSequence charSequence2;
        CharSequence charSequence3;
        CharSequence charSequence4;
        CharSequence charSequence5;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        KeyguardBatteryStatus keyguardBatteryStatus2 = keyguardUpdateMonitor.getKeyguardBatteryStatus();
        if (keyguardBatteryStatus2 == null) {
            Log.w("KeyguardSecIndicationController", "addBatteryAndOwnerInfoIndication() no status");
            return;
        }
        boolean z6 = this.mPowerPluggedIn;
        boolean z7 = keyguardBatteryStatus2.level < 20;
        char c = ' ';
        if (PowerUiRune.BATTERY_SWELLING_NOTICE) {
            int i3 = keyguardBatteryStatus2.swellingMode;
            if ((i3 & 16) != 0) {
                c = 16;
            }
            long j = keyguardBatteryStatus2.remaining;
            customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mCustomSdkMonitor;
            if (customSdkMonitor != null) {
                if ((customSdkMonitor.mKnoxCustomLockScreenHiddenItems & 2) == 0) {
                    z = true;
                    Context context = this.mContext;
                    if (z || !(this.mPowerPluggedIn || ((this.mPowerCharged && keyguardBatteryStatus2.isPluggedIn()) || (this.mProtectedFullyCharged && keyguardBatteryStatus2.isPluggedIn())))) {
                        keyguardBatteryStatus = keyguardBatteryStatus2;
                        z2 = true;
                        if (z || !z7) {
                            removeIndication(IndicationEventType.BATTERY);
                            removeIndication(IndicationEventType.BATTERY_RESTING);
                        } else {
                            CharSequence text = context.getText(com.android.systemui.R.string.kg_power_connect_charger);
                            addIndicationTimeout(IndicationEventType.BATTERY, text, this.mInitialTextColorState, !z6 && this.mPowerPluggedInWired);
                            addIndication(IndicationEventType.BATTERY_RESTING, text);
                        }
                        z3 = false;
                    } else {
                        int i4 = this.mChangingType;
                        int intValue = ((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("protect_battery").getIntValue();
                        boolean z8 = LsRune.LOCKUI_ECO_BATTERY;
                        boolean z9 = z8 && intValue == 4 && this.mResumedChargingAdaptiveProtection;
                        boolean z10 = this.mPowerCharged;
                        KeyguardBatteryMeterDrawable keyguardBatteryMeterDrawable = this.mBatteryDrawable;
                        String str3 = "";
                        if (z10) {
                            this.mResumedChargingAdaptiveProtection = false;
                            if (context == null) {
                                Log.e("KeyguardSecIndicationController", "Fail to getChargingText");
                                charSequence5 = str3;
                            } else if (!z8) {
                                charSequence5 = context.getText(com.android.systemui.R.string.kg_power_fully_charged).toString();
                            } else if (intValue == 0) {
                                charSequence5 = context.getText(com.android.systemui.R.string.kg_power_fully_charged).toString();
                            } else if (intValue == 3 || intValue == 4) {
                                keyguardBatteryMeterDrawable.isNeedBoltAndWarning = false;
                                charSequence5 = AddBatteryIcon(context.getString(com.android.systemui.R.string.battery_meter_format, Integer.valueOf(this.mBatteryLevel)));
                            }
                            keyguardBatteryStatus = keyguardBatteryStatus2;
                            charSequence4 = charSequence5;
                            z4 = false;
                            charSequence3 = charSequence4;
                            z2 = true;
                            z3 = z4;
                            charSequence = charSequence3;
                            AddBatteryIcon = charSequence;
                            addIndicationTimeout(IndicationEventType.BATTERY, AddBatteryIcon, this.mInitialTextColorState, (z6 && this.mPowerPluggedInWired) ? z2 : z3);
                            addIndication(IndicationEventType.BATTERY_RESTING, AddBatteryIcon);
                        }
                        keyguardBatteryStatus = keyguardBatteryStatus2;
                        if (this.mProtectedFullyCharged) {
                            if (context == null) {
                                Log.e("KeyguardSecIndicationController", "Fail to getChargingText");
                                charSequence4 = str3;
                            } else {
                                if (!z8) {
                                    z4 = false;
                                    charSequence3 = context.getText(com.android.systemui.R.string.kg_power_charging_paused).toString() + "\n" + context.getText(com.android.systemui.R.string.kg_power_charging_limit_85).toString();
                                } else if (intValue == 1 || intValue == 2) {
                                    String str4 = context.getString(com.android.systemui.R.string.battery_meter_format, Integer.valueOf(this.mBatteryLevel)) + '\n' + ((Object) context.getText(com.android.systemui.R.string.kg_power_charging_maximum_protection));
                                    keyguardBatteryMeterDrawable.isNeedBoltAndWarning = false;
                                    CharSequence AddBatteryIcon2 = AddBatteryIcon(str4);
                                    z4 = false;
                                    charSequence3 = AddBatteryIcon2;
                                } else if (intValue == 4 && (str = this.mSleepChargingEvent) != null && !"off".equals(str) && this.mSleepChargingEventFinishTime != null) {
                                    String string3 = context.getString(com.android.systemui.R.string.battery_meter_format, Integer.valueOf(this.mBatteryLevel));
                                    if (this.mBatteryLevel == 100 || this.mSleepChargingEventFinishTime == null) {
                                        str2 = string3;
                                        z5 = false;
                                    } else {
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(string3);
                                        sb.append('\n');
                                        Object[] objArr = new Object[1];
                                        try {
                                            Date parse = new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(this.mSleepChargingEventFinishTime);
                                            charSequence2 = str3;
                                            if (parse != null) {
                                                charSequence2 = DateFormat.getTimeFormat(context).format(Long.valueOf(parse.getTime()));
                                            }
                                        } catch (ParseException e) {
                                            Log.w("KeyguardSecIndicationController", "ParseException", e);
                                            charSequence2 = str3;
                                        }
                                        z5 = false;
                                        objArr[0] = charSequence2;
                                        sb.append(context.getString(com.android.systemui.R.string.kg_power_charging_adaptive_protection, objArr));
                                        str2 = sb.toString();
                                    }
                                    keyguardBatteryMeterDrawable.isNeedBoltAndWarning = z5;
                                    charSequence4 = AddBatteryIcon(str2);
                                }
                                z2 = true;
                                z3 = z4;
                                charSequence = charSequence3;
                                AddBatteryIcon = charSequence;
                                addIndicationTimeout(IndicationEventType.BATTERY, AddBatteryIcon, this.mInitialTextColorState, (z6 && this.mPowerPluggedInWired) ? z2 : z3);
                                addIndication(IndicationEventType.BATTERY_RESTING, AddBatteryIcon);
                            }
                            z4 = false;
                            charSequence3 = charSequence4;
                            z2 = true;
                            z3 = z4;
                            charSequence = charSequence3;
                            AddBatteryIcon = charSequence;
                            addIndicationTimeout(IndicationEventType.BATTERY, AddBatteryIcon, this.mInitialTextColorState, (z6 && this.mPowerPluggedInWired) ? z2 : z3);
                            addIndication(IndicationEventType.BATTERY_RESTING, AddBatteryIcon);
                        }
                        z3 = false;
                        if (z9) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(context.getString(com.android.systemui.R.string.battery_meter_format, Integer.valueOf(this.mBatteryLevel)));
                            sb2.append('\n');
                            string = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m73m(context, com.android.systemui.R.string.kg_power_charging_adaptive_protection_resumed, sb2);
                        } else {
                            String str5 = str3;
                            if (this.mIsNeededShowChargingType) {
                                switch (i4) {
                                    case 10:
                                        str5 = context.getString(com.android.systemui.R.string.kg_power_charging, Integer.valueOf(this.mBatteryLevel));
                                        break;
                                    case 11:
                                        string2 = LsRune.LOCKUI_HELP_TEXT_FOR_CHN ? context.getString(com.android.systemui.R.string.kg_power_fast_charging_chn, Integer.valueOf(this.mBatteryLevel)) : context.getString(com.android.systemui.R.string.kg_power_fast_charging, Integer.valueOf(this.mBatteryLevel));
                                        str5 = string2;
                                        break;
                                    case 12:
                                        str5 = context.getString(com.android.systemui.R.string.kg_power_charging_wirelessly, Integer.valueOf(this.mBatteryLevel));
                                        break;
                                    case 13:
                                        string2 = LsRune.LOCKUI_HELP_TEXT_FOR_CHN ? context.getString(com.android.systemui.R.string.kg_power_fast_charging_wirelessly_chn, Integer.valueOf(this.mBatteryLevel)) : context.getString(com.android.systemui.R.string.kg_power_fast_charging_wirelessly, Integer.valueOf(this.mBatteryLevel));
                                        str5 = string2;
                                        break;
                                    case 14:
                                        str5 = context.getString(com.android.systemui.R.string.kg_power_super_fast_charging, Integer.valueOf(this.mBatteryLevel));
                                        break;
                                    case 15:
                                        str5 = context.getString(com.android.systemui.R.string.kg_power_super_fast_charging_20, Integer.valueOf(this.mBatteryLevel));
                                        break;
                                }
                                string = str5;
                            } else {
                                string = context.getString(com.android.systemui.R.string.battery_meter_format, Integer.valueOf(this.mBatteryLevel));
                            }
                        }
                        if (PowerUiRune.BATTERY_CHARGING_ESTIMATE_TIME) {
                            if (j > 0 && c == 0) {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(string);
                                sb3.append('\n');
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
                                int i5 = (int) j2;
                                if (i == 0 && i2 >= 2 && i5 >= 30) {
                                    i2++;
                                    if (i2 == 60) {
                                        i = 1;
                                        i2 = 0;
                                    }
                                }
                                sb3.append((i <= 0 || i2 <= 0) ? i > 0 ? context.getString(com.android.systemui.R.string.kg_power_time_format_hour, Integer.valueOf(i)) : context.getString(com.android.systemui.R.string.kg_power_time_format_minute, Integer.valueOf(i2)) : context.getString(com.android.systemui.R.string.kg_power_time_format_hour_minute, Integer.valueOf(i), Integer.valueOf(i2)));
                                string = sb3.toString();
                            } else if (c == 16) {
                                z4 = false;
                                charSequence3 = context.getString(com.android.systemui.R.string.common_battery_slow_charging, Integer.valueOf(this.mBatteryLevel));
                                z2 = true;
                                z3 = z4;
                                charSequence = charSequence3;
                                AddBatteryIcon = charSequence;
                                addIndicationTimeout(IndicationEventType.BATTERY, AddBatteryIcon, this.mInitialTextColorState, (z6 && this.mPowerPluggedInWired) ? z2 : z3);
                                addIndication(IndicationEventType.BATTERY_RESTING, AddBatteryIcon);
                            }
                        }
                        if (this.mIsNeededShowChargingType || TextUtils.isEmpty(string)) {
                            z2 = true;
                            charSequence = string;
                            AddBatteryIcon = charSequence;
                            addIndicationTimeout(IndicationEventType.BATTERY, AddBatteryIcon, this.mInitialTextColorState, (z6 && this.mPowerPluggedInWired) ? z2 : z3);
                            addIndication(IndicationEventType.BATTERY_RESTING, AddBatteryIcon);
                        } else {
                            z2 = true;
                            keyguardBatteryMeterDrawable.isNeedBoltAndWarning = true;
                            AddBatteryIcon = AddBatteryIcon(string);
                            addIndicationTimeout(IndicationEventType.BATTERY, AddBatteryIcon, this.mInitialTextColorState, (z6 && this.mPowerPluggedInWired) ? z2 : z3);
                            addIndication(IndicationEventType.BATTERY_RESTING, AddBatteryIcon);
                        }
                    }
                    if (keyguardUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser())) {
                        addIndication(IndicationEventType.TRUST_AGENT_HELP, context.getText(com.android.systemui.R.string.kg_extend_lock_content_description));
                    } else {
                        removeIndication(IndicationEventType.TRUST_AGENT_HELP);
                    }
                    knoxStateMonitor = this.mKnoxStateMonitor;
                    if (knoxStateMonitor != null) {
                        CustomSdkMonitor customSdkMonitor2 = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
                        if (customSdkMonitor2 != null) {
                        }
                        z2 = z3;
                    }
                    if (this.mTopIndicationView != null) {
                        if (!keyguardUpdateMonitor.isDeviceOwnerInfoEnabled() || keyguardUpdateMonitor.getDeviceOwnerInfo() == null) {
                            if (!keyguardUpdateMonitor.isOwnerInfoEnabled() || keyguardUpdateMonitor.getOwnerInfo() == null) {
                                removeIndication(IndicationEventType.OWNER_INFO);
                            } else if (!TextUtils.equals(this.mTopIndicationView.getText(), keyguardUpdateMonitor.getOwnerInfo())) {
                                addIndication(IndicationEventType.OWNER_INFO, keyguardUpdateMonitor.getOwnerInfo());
                            }
                        } else if (!TextUtils.equals(this.mTopIndicationView.getText(), keyguardUpdateMonitor.getDeviceOwnerInfo())) {
                            addIndication(IndicationEventType.OWNER_INFO, keyguardUpdateMonitor.getDeviceOwnerInfo());
                        }
                    }
                    addUsbRestriction();
                    Log.d("KeyguardSecIndicationController", "addBatteryAndOwnerInfoIndication() battery status = " + keyguardBatteryStatus);
                }
            }
            z = false;
            Context context2 = this.mContext;
            if (z) {
            }
            keyguardBatteryStatus = keyguardBatteryStatus2;
            z2 = true;
            if (z) {
            }
            removeIndication(IndicationEventType.BATTERY);
            removeIndication(IndicationEventType.BATTERY_RESTING);
            z3 = false;
            if (keyguardUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser())) {
            }
            knoxStateMonitor = this.mKnoxStateMonitor;
            if (knoxStateMonitor != null) {
            }
            if (this.mTopIndicationView != null) {
            }
            addUsbRestriction();
            Log.d("KeyguardSecIndicationController", "addBatteryAndOwnerInfoIndication() battery status = " + keyguardBatteryStatus);
        }
        c = 0;
        long j3 = keyguardBatteryStatus2.remaining;
        customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mCustomSdkMonitor;
        if (customSdkMonitor != null) {
        }
        z = false;
        Context context22 = this.mContext;
        if (z) {
        }
        keyguardBatteryStatus = keyguardBatteryStatus2;
        z2 = true;
        if (z) {
        }
        removeIndication(IndicationEventType.BATTERY);
        removeIndication(IndicationEventType.BATTERY_RESTING);
        z3 = false;
        if (keyguardUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser())) {
        }
        knoxStateMonitor = this.mKnoxStateMonitor;
        if (knoxStateMonitor != null) {
        }
        if (this.mTopIndicationView != null) {
        }
        addUsbRestriction();
        Log.d("KeyguardSecIndicationController", "addBatteryAndOwnerInfoIndication() battery status = " + keyguardBatteryStatus);
    }

    public final void addLifeStyleIndication() {
        LinearLayout linearLayout;
        KeyguardUsimTextView keyguardUsimTextView;
        if (this.mLifeStyleIndicationView == null || this.mLifeStyleImageView == null) {
            return;
        }
        if (LsRune.LOCKUI_BOTTOM_USIM_TEXT && (linearLayout = this.mUsimTextArea) != null && linearLayout.getVisibility() == 0 && (keyguardUsimTextView = this.mUsimTextView) != null && keyguardUsimTextView.getVisibility() == 0 && this.mUsimTextView.getText() != null) {
            this.mLifeStyleContainer.setVisibility(8);
            return;
        }
        this.mLifeStyleContainer.setVisibility(0);
        this.mLifeStyleContainer.setLayoutDirection(MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(this.mContext) == 1 ? 1 : 0);
        this.mLifeStyleContainer.setClickable(this.mLifeStyleEnable);
        if (!this.mLifeStyleEnable) {
            this.mLifeStyleContainer.setVisibility(8);
            if (!TextUtils.isEmpty(this.mLifeStyleIndicationView.getText())) {
                this.mLifeStyleIndicationView.clearMessages();
            }
            this.mLifeStyleIndicationView.setSelected(false);
            this.mLifeStyleImageView.setImageDrawable(null);
            return;
        }
        this.mLifeStyleIndicationView.switchIndication(this.mLifeStyleName, null);
        this.mLifeStyleImageView.setImageDrawable(this.mLifeStyleIcon);
        this.mLifeStyleIndicationView.updateTextView();
        this.mLifeStyleImageView.setImageTintList(this.mLifeStyleIndicationView.getTextColors());
        this.mLifeStyleIndicationView.setSelected(true);
        PluginLockStarManager pluginLockStarManager = this.mPluginLockStarManager;
        PluginLockStar pluginLockStar = pluginLockStarManager.mPluginLockStar;
        if (pluginLockStar != null ? pluginLockStar.isLockStarEnabled() : false) {
            PluginLockStar.Modifier modifier = pluginLockStarManager.getModifier("UpdatelifestyleScale");
            if (modifier != null) {
                modifier.accept(this.mLifeStyleIndicationView);
                modifier.accept(this.mLifeStyleImageView);
            }
            PluginLockStar.Modifier modifier2 = pluginLockStarManager.getModifier("UpdatelifestyleColor");
            if (modifier2 != null) {
                this.mLifeStyleImageView.setImageTintList(null);
                modifier2.accept(this.mLifeStyleIndicationView);
                modifier2.accept(this.mLifeStyleImageView);
            }
        }
    }

    public final void addUsbRestriction() {
        StringBuilder sb = new StringBuilder("addUsbRestriction ");
        sb.append(this.mIsUsbRestricted);
        sb.append("/isSecure=");
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        sb.append(keyguardUpdateMonitor.isSecure(currentUser) && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()));
        Log.d("KeyguardSecIndicationController", sb.toString());
        if (!(keyguardUpdateMonitor.isSecure(KeyguardUpdateMonitor.getCurrentUser()) && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser())) || !this.mIsUsbRestricted) {
            removeIndication(IndicationEventType.USB_RESTRICTION);
            return;
        }
        boolean isTablet = DeviceType.isTablet();
        Context context = this.mContext;
        if (isTablet) {
            addIndication(IndicationEventType.USB_RESTRICTION, context.getString(com.android.systemui.R.string.lockscreen_usb_restriction_tablet_help_text));
        } else {
            addIndication(IndicationEventType.USB_RESTRICTION, context.getString(com.android.systemui.R.string.lockscreen_usb_restriction_phone_help_text));
        }
    }

    public final void changeIndication(CharSequence charSequence, boolean z, boolean z2) {
        if (this.mTopIndicationView != null) {
            if (this.mLockHelpTextVisible || TextUtils.isEmpty(charSequence)) {
                TextPaint paint = this.mTopIndicationView.getPaint();
                int width = this.mTopIndicationView.getWidth();
                boolean z3 = this.mTopIndicationView.getEllipsize() == TextUtils.TruncateAt.MARQUEE || new StaticLayout(this.mTopIndicationView.getText(), paint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false).getLineCount() == 1;
                KeyguardIndicationTextView keyguardIndicationTextView = this.mTopIndicationView;
                if (keyguardIndicationTextView != null) {
                    keyguardIndicationTextView.setSingleLine(z2);
                    this.mTopIndicationView.setEllipsize(z2 ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                    this.mTopIndicationView.setSelected(z2);
                }
                if (z) {
                    final KeyguardIndicationTextView keyguardIndicationTextView2 = this.mTopIndicationView;
                    final String charSequence2 = charSequence.toString();
                    Context context = this.mContext;
                    int integer = context.getResources().getInteger(com.android.systemui.R.integer.wired_charging_keyguard_text_animation_distance);
                    int integer2 = context.getResources().getInteger(com.android.systemui.R.integer.wired_charging_keyguard_text_animation_duration_up);
                    final int integer3 = context.getResources().getInteger(com.android.systemui.R.integer.wired_charging_keyguard_text_animation_duration_down);
                    keyguardIndicationTextView2.animate().cancel();
                    ViewClippingUtil.setClippingDeactivated(keyguardIndicationTextView2, true, this.mClippingParams);
                    keyguardIndicationTextView2.animate().translationYBy(integer).setInterpolator(new LinearInterpolator()).setDuration(integer2).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.10
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
                                keyguardIndicationTextView2.animate().setDuration(integer3).setInterpolator(KeyguardSecIndicationController.this.mBounceInterpolator).translationY(0.0f).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.10.1
                                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                    public final void onAnimationEnd(Animator animator2) {
                                        keyguardIndicationTextView2.setTranslationY(0.0f);
                                        C253810 c253810 = C253810.this;
                                        ViewClippingUtil.setClippingDeactivated(keyguardIndicationTextView2, false, KeyguardSecIndicationController.this.mClippingParams);
                                    }
                                });
                            }
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            keyguardIndicationTextView2.switchIndication(charSequence2, null);
                        }
                    });
                    return;
                }
                this.mTopIndicationView.switchIndication(charSequence, null);
                PluginLockStarManager pluginLockStarManager = this.mPluginLockStarManager;
                PluginLockStar pluginLockStar = pluginLockStarManager.mPluginLockStar;
                if (pluginLockStar == null ? false : pluginLockStar.isLockStarEnabled()) {
                    PluginLockStar.Modifier modifier = pluginLockStarManager.getModifier("UpdatehelptextScale");
                    if (modifier != null) {
                        modifier.accept(this.mTopIndicationView);
                    }
                    PluginLockStar.Modifier modifier2 = pluginLockStarManager.getModifier("UpdatehelptextColor");
                    if (modifier2 != null) {
                        modifier2.accept(this.mTopIndicationView);
                    }
                }
                if (z3 != (this.mTopIndicationView.getEllipsize() == TextUtils.TruncateAt.MARQUEE || new StaticLayout(this.mTopIndicationView.getText(), paint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false).getLineCount() == 1)) {
                    ((KeyguardSecBottomAreaViewBinder$bind$1) ((KeyguardSecBottomAreaView) this.mTopIndicationView.getParent().getParent()).binding).updateIndicationPosition();
                }
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
        boolean z2;
        int i;
        boolean z3 = LsRune.SECURITY_SIM_PERM_DISABLED;
        int i2 = 0;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (z3 && keyguardUpdateMonitor.isIccBlockedPermanently()) {
            z2 = true;
        } else {
            CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mCustomSdkMonitor;
            if (customSdkMonitor != null) {
                if ((customSdkMonitor.mKnoxCustomLockScreenHiddenItems & 256) == 0) {
                    z = true;
                    z2 = !z;
                }
            }
            z = false;
            z2 = !z;
        }
        if (z2) {
            return "";
        }
        boolean userHasTrust = keyguardUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser());
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        boolean isUnlockingWithBiometricAllowed = keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true);
        boolean z4 = isUnlockingWithBiometricAllowed && keyguardUpdateMonitor.isFingerprintOptionEnabled() && !isAuthenticatedWithBiometric() && !userHasTrust;
        boolean z5 = isUnlockingWithBiometricAllowed && keyguardUpdateMonitor.isFaceOptionEnabled() && !keyguardUpdateMonitor.isCameraDisabledByPolicy() && !keyguardUpdateMonitor.isFaceDisabled(currentUser) && keyguardUpdateMonitor.isFaceDetectionRunning();
        boolean isTouchExplorationEnabled = this.mAccessibilityManager.isTouchExplorationEnabled();
        this.mIsFpGuidePos = false;
        Context context = this.mContext;
        if (z4 || z5) {
            if (!(z4 && z5) && z4) {
                int i3 = isTouchExplorationEnabled ? com.android.systemui.R.string.kg_fingerprint_or_swipe_unlock_voice_assistant_instructions : 0;
                this.mIsFpGuidePos = isTouchExplorationEnabled;
                i = i3;
            } else {
                i = 0;
            }
            if (i != 0) {
                return context.getString(i);
            }
        } else {
            i = keyguardUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser()) ? com.android.systemui.R.string.kg_extend_lock_content_description : (!keyguardUpdateMonitor.isSecure() || keyguardUpdateMonitor.isBiometricsAuthenticatedOnLock()) ? com.android.systemui.R.string.kg_swipe_active_instructions : com.android.systemui.R.string.kg_swipe_unlock_instructions;
        }
        if (isTouchExplorationEnabled) {
            if (keyguardUpdateMonitor.isUnlockCompleted() || !(z4 || z5)) {
                if (!userHasTrust && !z4 && !z5) {
                    i2 = com.android.systemui.R.string.kg_voice_assistant_unlock_instructions;
                }
                i = (isAuthenticatedWithBiometric() || !keyguardUpdateMonitor.isSecure()) ? com.android.systemui.R.string.kg_voice_assistant_active_instructions : i2;
            } else {
                i = com.android.systemui.R.string.kg_voice_assistant_unlock_strong_auth_instructions;
            }
        }
        return i != 0 ? context.getString(i) : "";
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final void hideTransientIndication() {
        removeIndication(IndicationEventType.LEGACY_TRANSIENT);
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final void hideTransientIndicationDelayed(long j) {
        IndicationEventType indicationEventType = IndicationEventType.LEGACY_TRANSIENT;
        int i = ObjectHelper.$r8$clinit;
        if (indicationEventType == null) {
            throw new NullPointerException("item is null");
        }
        ObservableJust observableJust = new ObservableJust(indicationEventType);
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        Scheduler scheduler = Schedulers.COMPUTATION;
        if (timeUnit == null) {
            throw new NullPointerException("unit is null");
        }
        if (scheduler == null) {
            throw new NullPointerException("scheduler is null");
        }
        ObservableDelay observableDelay = new ObservableDelay(observableJust, j, timeUnit, scheduler, false);
        Scheduler mainThread = AndroidSchedulers.mainThread();
        int i2 = Flowable.BUFFER_SIZE;
        if (i2 <= 0) {
            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("bufferSize > 0 required but it was ", i2));
        }
        ObservableObserveOn observableObserveOn = new ObservableObserveOn(observableDelay, mainThread, false, i2);
        KeyguardSecIndicationController$$ExternalSyntheticLambda0 keyguardSecIndicationController$$ExternalSyntheticLambda0 = new KeyguardSecIndicationController$$ExternalSyntheticLambda0(this, 0);
        Functions.OnErrorMissingConsumer onErrorMissingConsumer = Functions.ON_ERROR_MISSING;
        Functions.EmptyAction emptyAction = Functions.EMPTY_ACTION;
        Functions.EmptyConsumer emptyConsumer = Functions.EMPTY_CONSUMER;
        if (onErrorMissingConsumer == null) {
            throw new NullPointerException("onError is null");
        }
        if (emptyAction == null) {
            throw new NullPointerException("onComplete is null");
        }
        if (emptyConsumer == null) {
            throw new NullPointerException("onSubscribe is null");
        }
        observableObserveOn.subscribe(new LambdaObserver(keyguardSecIndicationController$$ExternalSyntheticLambda0, onErrorMissingConsumer, emptyAction, emptyConsumer));
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final boolean isInLifeStyleArea(MotionEvent motionEvent) {
        LinearLayout linearLayout = this.mLifeStyleContainer;
        if (linearLayout != null && linearLayout.getVisibility() == 0) {
            Rect rect = new Rect();
            this.mLifeStyleContainer.getGlobalVisibleRect(rect);
            if (rect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final void onConfigChanged() {
        LinearLayout linearLayout = this.mLifeStyleContainer;
        Context context = this.mContext;
        if (linearLayout != null) {
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.keyguard_indication_life_style_container_padding_horizontal);
            int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.keyguard_indication_life_style_container_padding_vertical);
            this.mLifeStyleContainer.setPadding(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize2);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mLifeStyleContainer.getLayoutParams();
            marginLayoutParams.bottomMargin = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.keyguard_indication_life_style_margin_bottom);
            this.mLifeStyleContainer.setLayoutParams(marginLayoutParams);
        }
        SystemUIImageView systemUIImageView = this.mLifeStyleImageView;
        if (systemUIImageView != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) systemUIImageView.getLayoutParams();
            int dimensionPixelSize3 = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.keyguard_indication_life_style_height);
            marginLayoutParams2.width = dimensionPixelSize3;
            marginLayoutParams2.height = dimensionPixelSize3;
            marginLayoutParams2.rightMargin = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.keyguard_indication_life_style_margin);
            this.mLifeStyleImageView.setLayoutParams(marginLayoutParams2);
        }
        this.mTopIndicationView.updateFontSizeInKeyguardBoundary(true, context.getResources().getConfiguration());
        setVisible(false);
        Completable.timer(100L, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).subscribe(new KeyguardSecIndicationController$$ExternalSyntheticLambda0(this, 2));
        PluginLockStarManager pluginLockStarManager = this.mPluginLockStarManager;
        if (pluginLockStarManager != null) {
            PluginLockStar pluginLockStar = pluginLockStarManager.mPluginLockStar;
            if (pluginLockStar != null ? pluginLockStar.isLockStarEnabled() : false) {
                PluginLockStar.Modifier modifier = pluginLockStarManager.getModifier("UpdatelifestyleScale");
                if (modifier != null) {
                    modifier.accept(this.mLifeStyleImageView);
                    modifier.accept(this.mLifeStyleIndicationView);
                }
                PluginLockStar.Modifier modifier2 = pluginLockStarManager.getModifier("UpdatelifestyleColor");
                if (modifier2 != null) {
                    SystemUIImageView systemUIImageView2 = this.mLifeStyleImageView;
                    if (systemUIImageView2 != null) {
                        systemUIImageView2.setImageTintList(null);
                    }
                    modifier2.accept(this.mLifeStyleImageView);
                    modifier2.accept(this.mLifeStyleIndicationView);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onIndicationChanged(IndicationPosition indicationPosition, IndicationItem indicationItem) {
        boolean z;
        KeyguardIndicationTextView keyguardIndicationTextView;
        KeyguardIndicationTextView keyguardIndicationTextView2;
        if (indicationPosition == null || indicationItem == null) {
            Log.d("KeyguardSecIndicationController", "onIndicationChanged() return - pos or item is null, pos = " + indicationPosition + ", item = " + indicationItem);
            return;
        }
        if (!TextUtils.isEmpty(indicationItem.mText)) {
            if (!(this.mVisible && !this.mDozing)) {
                z = false;
                if (z) {
                    String format = String.format("onIndicationChanged() return - keyguard is not visible, pos = %7s, item = %s", indicationPosition, indicationItem);
                    if (format.contains("OWNER_INFO")) {
                        format = "onIndicationChanged() return - keyguard is not visible, skip ownerInfo";
                    }
                    Log.d("KeyguardSecIndicationController", format);
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
                boolean z2 = indicationItem.mPriority >= IndicationEventType.EMPTY_HIGH.getPriority() && !TextUtils.isEmpty(indicationItem.mText);
                SettableWakeLock settableWakeLock = this.mWakeLock;
                if (settableWakeLock != null) {
                    settableWakeLock.setAcquired(z2);
                }
                if (AbstractC253911.$SwitchMap$com$android$systemui$statusbar$IndicationPosition[indicationPosition.ordinal()] != 2) {
                    changeIndication(indicationItem.mText, indicationItem.mIsAnimation, indicationItem.mEventType == IndicationEventType.OWNER_INFO);
                    return;
                }
                CharSequence charSequence = indicationItem.mText;
                if ((this.mLockHelpTextVisible || TextUtils.isEmpty(charSequence)) && (keyguardIndicationTextView = this.mUpperTextView) != null) {
                    keyguardIndicationTextView.switchIndication(charSequence, null);
                    return;
                }
                return;
            }
        }
        z = true;
        if (z) {
        }
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final void onViewModeChanged(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("onViewModeChanged mode: ", i, "KeyguardSecIndicationController");
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
        this.mUsimTextArea = (LinearLayout) viewGroup.findViewById(com.android.systemui.R.id.usim_text_area);
        this.mUsimTextView = (KeyguardUsimTextView) viewGroup.findViewById(com.android.systemui.R.id.keyguard_usim_carrier_text);
        if (this.mLifeStyleContainer != null) {
            this.mLifeStyleContainer.setBackgroundResource(WallpaperUtils.isWhiteKeyguardWallpaper("bottom") ? com.android.systemui.R.drawable.rounded_bg_routine_mode_radius_whitebg : com.android.systemui.R.drawable.rounded_bg_routine_mode_radius);
            this.mLifeStyleContainer.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationController.4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
                    Context context = keyguardSecIndicationController.mContext;
                    KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardSecIndicationController.mKeyguardUpdateMonitor;
                    boolean z = keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser());
                    Intent action = new Intent().setAction("com.samsung.android.app.routines.action.LAUNCH_MODE_LIST_DIALOG");
                    action.putExtra("lock_bouncer_enabled", z).setPackage("com.samsung.android.app.routines").addFlags(335544352);
                    try {
                        ActivityTaskManager.getService().startActivityAsUser((IApplicationThread) null, context.getBasePackageName(), context.getAttributionTag(), action, action.resolveTypeIfNeeded(context.getContentResolver()), (IBinder) null, (String) null, 0, QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, (ProfilerInfo) null, ActivityOptions.makeBasic().toBundle(), UserHandle.CURRENT.getIdentifier());
                    } catch (RemoteException unused) {
                        Log.e("KeyguardSecIndicationController", "Unexpected intent: " + action + " when Routine Mode clicked");
                    }
                }
            });
        }
        this.mUpperTextView = (KeyguardIndicationTextView) viewGroup.findViewById(com.android.systemui.R.id.keyguard_upper_fingerprint_indication);
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final void setUpperTextView(KeyguardIndicationTextView keyguardIndicationTextView) {
        this.mUpperTextView = keyguardIndicationTextView;
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final void setVisible(boolean z) {
        boolean z2 = z & this.mLockHelpTextVisible;
        PluginLockData pluginLockData = this.mPluginLockData;
        if (pluginLockData != null) {
            PluginLockDataImpl pluginLockDataImpl = (PluginLockDataImpl) pluginLockData;
            if (pluginLockDataImpl.isAvailable() && this.mIsDefaultLockViewMode) {
                z2 = pluginLockDataImpl.getVisibility(5) == 0;
            }
        }
        super.setVisible(z2);
        if (z2) {
            addInitialIndication();
            addLifeStyleIndication();
            if (this.mIsScreenOn) {
                Completable.timer(100L, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).subscribe(new KeyguardSecIndicationController$$ExternalSyntheticLambda0(this, 1));
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

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final void updateDynamicLockData(String str) {
        setVisible(true);
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public final void updateLifeStyleInfo(Intent intent) {
        this.mLifeStyleEnable = intent.getBooleanExtra("mode_is_running", false);
        this.mLifeStyleName = intent.getStringExtra("mode_display_name");
        byte[] byteArrayExtra = intent.getByteArrayExtra("mode_icon_byte_array");
        if (byteArrayExtra != null) {
            this.mLifeStyleIcon = new BitmapDrawable(BitmapFactory.decodeByteArray(byteArrayExtra, 0, byteArrayExtra.length));
        }
        addLifeStyleIndication();
    }

    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
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
