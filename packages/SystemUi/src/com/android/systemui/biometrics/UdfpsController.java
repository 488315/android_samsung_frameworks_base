package com.android.systemui.biometrics;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.biometrics.BiometricFingerprintConstants;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IUdfpsOverlayController;
import android.hardware.fingerprint.IUdfpsOverlayControllerCallback;
import android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.Trace;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.util.Log;
import android.util.RotationUtils;
import android.util.StatsEvent;
import android.util.StatsLog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.internal.util.LatencyTracker;
import com.android.internal.util.Preconditions;
import com.android.keyguard.FaceAuthUiEvent;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.udfps.UdfpsOverlayParams;
import com.android.settingslib.udfps.UdfpsUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.biometrics.BiometricDisplayListener;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.biometrics.udfps.InteractionEvent;
import com.android.systemui.biometrics.udfps.NormalizedTouchData;
import com.android.systemui.biometrics.udfps.PreprocessedTouch;
import com.android.systemui.biometrics.udfps.SinglePointerTouchProcessor;
import com.android.systemui.biometrics.udfps.SinglePointerTouchProcessorKt;
import com.android.systemui.biometrics.udfps.TouchProcessorResult;
import com.android.systemui.doze.DozeReceiver;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.ReleasedFlag;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardFaceAuthInteractor;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.keyguard.domain.interactor.SystemUIKeyguardFaceAuthInteractor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.concurrency.ExecutionImpl;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class UdfpsController implements DozeReceiver, Dumpable {
    public final AccessibilityManager mAccessibilityManager;
    public boolean mAcquiredReceived;
    public final ActivityLaunchAnimator mActivityLaunchAnimator;
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public Runnable mAodInterruptRunnable;
    public boolean mAttemptedToDismissKeyguard;
    public Runnable mAuthControllerUpdateUdfpsLocation;
    public final Executor mBiometricExecutor;
    public final C10692 mBroadcastReceiver;
    public ExecutorImpl.ExecutionToken mCancelAodFingerUpAction;
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public final SystemUIDialogManager mDialogManager;
    public final DumpManager mDumpManager;
    public final Execution mExecution;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public final DelayableExecutor mFgExecutor;
    public final FingerprintManager mFingerprintManager;
    public final boolean mIgnoreRefreshRate;
    public final LayoutInflater mInflater;
    public final InputManager mInputManager;
    public boolean mIsAodInterruptActive;
    public final KeyguardFaceAuthInteractor mKeyguardFaceAuthInteractor;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final StatusBarKeyguardViewManager mKeyguardViewManager;
    public long mLastTouchInteractionTime;
    public final LatencyTracker mLatencyTracker;
    public final LockscreenShadeTransitionController mLockscreenShadeTransitionController;
    public boolean mOnFingerDown;
    final BiometricDisplayListener mOrientationListener;
    public UdfpsControllerOverlay mOverlay;
    public final PowerManager mPowerManager;
    public final PrimaryBouncerInteractor mPrimaryBouncerInteractor;
    public final C10681 mScreenObserver;
    public boolean mScreenOn;
    public final SecureSettings mSecureSettings;
    FingerprintSensorPropertiesInternal mSensorProps;
    public final SessionTracker mSessionTracker;
    public final ShadeExpansionStateManager mShadeExpansionStateManager;
    public final StatusBarStateController mStatusBarStateController;
    public final SystemClock mSystemClock;
    public long mTouchLogTime;
    public final SinglePointerTouchProcessor mTouchProcessor;
    public UdfpsDisplayModeProvider mUdfpsDisplayMode;
    public final UdfpsUtils mUdfpsUtils;
    public final UnlockedScreenOffAnimationController mUnlockedScreenOffAnimationController;
    public VelocityTracker mVelocityTracker;
    public final VibratorHelper mVibrator;
    public final WindowManager mWindowManager;
    public static final VibrationAttributes UDFPS_VIBRATION_ATTRIBUTES = new VibrationAttributes.Builder().setUsage(65).build();
    public static final VibrationAttributes LOCK_ICON_VIBRATION_ATTRIBUTES = new VibrationAttributes.Builder().setUsage(18).build();
    public static final VibrationEffect EFFECT_CLICK = VibrationEffect.get(0);
    UdfpsOverlayParams mOverlayParams = new UdfpsOverlayParams();
    public int mActivePointerId = -1;
    public final Set mCallbacks = new HashSet();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.biometrics.UdfpsController$3 */
    public abstract /* synthetic */ class AbstractC10703 {

        /* renamed from: $SwitchMap$com$android$systemui$biometrics$udfps$InteractionEvent */
        public static final /* synthetic */ int[] f232x56fbb812;

        static {
            int[] iArr = new int[InteractionEvent.values().length];
            f232x56fbb812 = iArr;
            try {
                iArr[InteractionEvent.DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f232x56fbb812[InteractionEvent.UP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f232x56fbb812[InteractionEvent.CANCEL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f232x56fbb812[InteractionEvent.UNCHANGED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        void onFingerDown();

        void onFingerUp();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.biometrics.UdfpsController$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v0, types: [android.content.BroadcastReceiver, com.android.systemui.biometrics.UdfpsController$2] */
    public UdfpsController(Context context, Execution execution, LayoutInflater layoutInflater, FingerprintManager fingerprintManager, WindowManager windowManager, StatusBarStateController statusBarStateController, DelayableExecutor delayableExecutor, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, DumpManager dumpManager, KeyguardUpdateMonitor keyguardUpdateMonitor, FeatureFlags featureFlags, FalsingManager falsingManager, PowerManager powerManager, AccessibilityManager accessibilityManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ScreenLifecycle screenLifecycle, VibratorHelper vibratorHelper, UdfpsHapticsSimulator udfpsHapticsSimulator, UdfpsShell udfpsShell, KeyguardStateController keyguardStateController, DisplayManager displayManager, Handler handler, ConfigurationController configurationController, SystemClock systemClock, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, SystemUIDialogManager systemUIDialogManager, LatencyTracker latencyTracker, ActivityLaunchAnimator activityLaunchAnimator, Optional<Provider> optional, Executor executor, PrimaryBouncerInteractor primaryBouncerInteractor, SinglePointerTouchProcessor singlePointerTouchProcessor, SessionTracker sessionTracker, AlternateBouncerInteractor alternateBouncerInteractor, SecureSettings secureSettings, InputManager inputManager, UdfpsUtils udfpsUtils, KeyguardFaceAuthInteractor keyguardFaceAuthInteractor) {
        ?? r5 = new ScreenLifecycle.Observer() { // from class: com.android.systemui.biometrics.UdfpsController.1
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurnedOff() {
                UdfpsController.this.mScreenOn = false;
            }

            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurnedOn() {
                UdfpsController udfpsController = UdfpsController.this;
                udfpsController.mScreenOn = true;
                Runnable runnable = udfpsController.mAodInterruptRunnable;
                if (runnable != null) {
                    runnable.run();
                    udfpsController.mAodInterruptRunnable = null;
                }
            }
        };
        this.mScreenObserver = r5;
        ?? r6 = new BroadcastReceiver() { // from class: com.android.systemui.biometrics.UdfpsController.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                UdfpsControllerOverlay udfpsControllerOverlay = UdfpsController.this.mOverlay;
                if (udfpsControllerOverlay == null || udfpsControllerOverlay.requestReason == 4 || !"android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                    return;
                }
                String stringExtra = intent.getStringExtra("reason");
                if (stringExtra == null) {
                    stringExtra = "unknown";
                }
                RecyclerView$$ExternalSyntheticOutline0.m46m(ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("ACTION_CLOSE_SYSTEM_DIALOGS received, reason: ", stringExtra, ", mRequestReason: "), UdfpsController.this.mOverlay.requestReason, "UdfpsController");
                UdfpsControllerOverlay udfpsControllerOverlay2 = UdfpsController.this.mOverlay;
                udfpsControllerOverlay2.getClass();
                try {
                    udfpsControllerOverlay2.controllerCallback.onUserCanceled();
                } catch (RemoteException e) {
                    Log.e("UdfpsControllerOverlay", "Remote exception", e);
                }
                UdfpsController.this.hideUdfpsOverlay();
            }
        };
        this.mBroadcastReceiver = r6;
        this.mContext = context;
        this.mExecution = execution;
        this.mVibrator = vibratorHelper;
        this.mInflater = layoutInflater;
        this.mIgnoreRefreshRate = context.getResources().getBoolean(R.bool.config_faceAuthSupportsSelfIllumination);
        FingerprintManager fingerprintManager2 = (FingerprintManager) Preconditions.checkNotNull(fingerprintManager);
        this.mFingerprintManager = fingerprintManager2;
        this.mWindowManager = windowManager;
        this.mFgExecutor = delayableExecutor;
        this.mShadeExpansionStateManager = shadeExpansionStateManager;
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardStateController = keyguardStateController;
        this.mKeyguardViewManager = statusBarKeyguardViewManager;
        this.mDumpManager = dumpManager;
        this.mDialogManager = systemUIDialogManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mFeatureFlags = featureFlags;
        this.mFalsingManager = falsingManager;
        this.mPowerManager = powerManager;
        this.mAccessibilityManager = accessibilityManager;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        screenLifecycle.addObserver(r5);
        this.mScreenOn = screenLifecycle.mScreenState == 2;
        this.mConfigurationController = configurationController;
        this.mSystemClock = systemClock;
        this.mUnlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        this.mLatencyTracker = latencyTracker;
        this.mActivityLaunchAnimator = activityLaunchAnimator;
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(optional.map(new UdfpsController$$ExternalSyntheticLambda1()).orElse(null));
        this.mSensorProps = new FingerprintSensorPropertiesInternal(-1, 0, 0, new ArrayList(), 0, false);
        this.mBiometricExecutor = executor;
        this.mPrimaryBouncerInteractor = primaryBouncerInteractor;
        this.mAlternateBouncerInteractor = alternateBouncerInteractor;
        this.mSecureSettings = secureSettings;
        this.mUdfpsUtils = udfpsUtils;
        this.mInputManager = inputManager;
        this.mTouchProcessor = ((FeatureFlagsRelease) featureFlags).isEnabled(Flags.UDFPS_NEW_TOUCH_DETECTION) ? singlePointerTouchProcessor : null;
        this.mSessionTracker = sessionTracker;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "UdfpsController", this);
        this.mOrientationListener = new BiometricDisplayListener(context, displayManager, handler, BiometricDisplayListener.SensorType.UnderDisplayFingerprint.INSTANCE, new Function0() { // from class: com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Runnable runnable = UdfpsController.this.mAuthControllerUpdateUdfpsLocation;
                if (runnable != null) {
                    runnable.run();
                }
                return Unit.INSTANCE;
            }
        });
        this.mKeyguardFaceAuthInteractor = keyguardFaceAuthInteractor;
        UdfpsOverlayController udfpsOverlayController = new UdfpsOverlayController();
        fingerprintManager2.setUdfpsOverlayController(udfpsOverlayController);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        context.registerReceiver(r6, intentFilter, 2);
        udfpsHapticsSimulator.udfpsController = this;
        udfpsShell.udfpsOverlayController = udfpsOverlayController;
    }

    public void cancelAodSendFingerUpAction() {
        this.mIsAodInterruptActive = false;
        ExecutorImpl.ExecutionToken executionToken = this.mCancelAodFingerUpAction;
        if (executionToken != null) {
            executionToken.run();
            this.mCancelAodFingerUpAction = null;
        }
    }

    public final void dispatchOnUiReady(long j) {
        this.mFingerprintManager.onUiReady(j, this.mSensorProps.sensorId);
        this.mLatencyTracker.onActionEnd(14);
    }

    @Override // com.android.systemui.doze.DozeReceiver
    public final void dozeTimeTick() {
        UdfpsView udfpsView;
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay == null || (udfpsView = udfpsControllerOverlay.overlayView) == null) {
            return;
        }
        udfpsView.dozeTimeTick();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("mSensorProps=(" + this.mSensorProps + ")");
        StringBuilder sb = new StringBuilder("Using new touch detection framework: ");
        ReleasedFlag releasedFlag = Flags.UDFPS_NEW_TOUCH_DETECTION;
        FeatureFlags featureFlags = this.mFeatureFlags;
        sb.append(((FeatureFlagsRelease) featureFlags).isEnabled(releasedFlag));
        printWriter.println(sb.toString());
        printWriter.println("Using ellipse touch detection: " + ((FeatureFlagsRelease) featureFlags).isEnabled(Flags.UDFPS_ELLIPSE_DETECTION));
    }

    public final void hideUdfpsOverlay() {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay != null) {
            UdfpsView udfpsView = udfpsControllerOverlay.overlayView;
            if (udfpsView != null) {
                onFingerUp(udfpsControllerOverlay.requestId, udfpsView);
            }
            UdfpsControllerOverlay udfpsControllerOverlay2 = this.mOverlay;
            UdfpsView udfpsView2 = udfpsControllerOverlay2.overlayView;
            if (udfpsView2 != null) {
                if (udfpsView2.isDisplayConfigured) {
                    udfpsView2.unconfigureDisplay$1();
                }
                udfpsControllerOverlay2.windowManager.removeView(udfpsView2);
                udfpsView2.setOnTouchListener(null);
                udfpsView2.setOnHoverListener(null);
                udfpsView2.animationViewController = null;
                UdfpsControllerOverlay$show$1$1 udfpsControllerOverlay$show$1$1 = udfpsControllerOverlay2.overlayTouchListener;
                if (udfpsControllerOverlay$show$1$1 != null) {
                    udfpsControllerOverlay2.accessibilityManager.removeTouchExplorationStateChangeListener(udfpsControllerOverlay$show$1$1);
                }
            }
            udfpsControllerOverlay2.overlayView = null;
            udfpsControllerOverlay2.overlayTouchListener = null;
            this.mKeyguardViewManager.hideAlternateBouncer(true);
        }
        this.mOverlay = null;
        BiometricDisplayListener biometricDisplayListener = this.mOrientationListener;
        biometricDisplayListener.displayManager.unregisterDisplayListener(biometricDisplayListener);
    }

    public final boolean isWithinSensorArea(UdfpsView udfpsView, float f, float f2, boolean z) {
        PointF pointF;
        if (!z) {
            UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
            if (udfpsControllerOverlay != null) {
                UdfpsView udfpsView2 = udfpsControllerOverlay.overlayView;
                if ((udfpsView2 != null ? udfpsView2.animationViewController : null) != null) {
                    return !(udfpsView2 != null ? udfpsView2.animationViewController : null).shouldPauseAuth() && this.mOverlayParams.sensorBounds.contains((int) f, (int) f2);
                }
            }
            return false;
        }
        UdfpsAnimationViewController udfpsAnimationViewController = udfpsView.animationViewController;
        if (udfpsAnimationViewController == null || (pointF = udfpsAnimationViewController.touchTranslation) == null) {
            pointF = new PointF(0.0f, 0.0f);
        }
        float centerX = udfpsView.sensorRect.centerX() + pointF.x;
        float centerY = udfpsView.sensorRect.centerY() + pointF.y;
        Rect rect = udfpsView.sensorRect;
        float f3 = (rect.right - rect.left) / 2.0f;
        float f4 = (rect.bottom - rect.top) / 2.0f;
        float f5 = udfpsView.sensorTouchAreaCoefficient;
        float f6 = f3 * f5;
        if (f > centerX - f6 && f < f6 + centerX) {
            float f7 = f4 * f5;
            if (f2 > centerY - f7 && f2 < f7 + centerY) {
                UdfpsAnimationViewController udfpsAnimationViewController2 = udfpsView.animationViewController;
                if (!(udfpsAnimationViewController2 != null ? udfpsAnimationViewController2.shouldPauseAuth() : false)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void onFingerDown(long j, int i, float f, float f2, float f3, float f4, float f5, long j2, long j3, boolean z) {
        int i2;
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay == null) {
            Log.w("UdfpsController", "Null request in onFingerDown");
            return;
        }
        if (!udfpsControllerOverlay.matchesRequestId(j)) {
            StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("Mismatched fingerDown: ", j, " current: ");
            m17m.append(this.mOverlay.requestId);
            Log.w("UdfpsController", m17m.toString());
            return;
        }
        if (this.mSensorProps.sensorType == 3) {
            this.mLatencyTracker.onActionStart(14);
        }
        ((SystemClockImpl) this.mSystemClock).getClass();
        this.mPowerManager.userActivity(android.os.SystemClock.uptimeMillis(), 2, 0);
        if (!this.mOnFingerDown) {
            playStartHaptic();
            SystemUIKeyguardFaceAuthInteractor systemUIKeyguardFaceAuthInteractor = (SystemUIKeyguardFaceAuthInteractor) this.mKeyguardFaceAuthInteractor;
            systemUIKeyguardFaceAuthInteractor.getClass();
            systemUIKeyguardFaceAuthInteractor.runFaceAuth(FaceAuthUiEvent.FACE_AUTH_TRIGGERED_UDFPS_POINTER_DOWN);
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            if (!keyguardUpdateMonitor.isFaceDetectionRunning()) {
                keyguardUpdateMonitor.requestFaceAuth("Face auth triggered due to finger down on UDFPS");
            }
        }
        this.mOnFingerDown = true;
        if (((FeatureFlagsRelease) this.mFeatureFlags).isEnabled(Flags.UDFPS_NEW_TOUCH_DETECTION)) {
            i2 = 0;
            this.mFingerprintManager.onPointerDown(j, this.mSensorProps.sensorId, i, f, f2, f3, f4, f5, j2, j3, z);
        } else {
            i2 = 0;
            this.mFingerprintManager.onPointerDown(j, this.mSensorProps.sensorId, (int) f, (int) f2, f3, f4);
        }
        Trace.endAsyncSection("UdfpsController.e2e.onPointerDown", i2);
        UdfpsView udfpsView = this.mOverlay.overlayView;
        if (udfpsView != null) {
            if ((this.mSensorProps.sensorType == 3 ? 1 : i2) != 0) {
                if (this.mIgnoreRefreshRate) {
                    dispatchOnUiReady(j);
                } else {
                    udfpsView.isDisplayConfigured = true;
                    UdfpsAnimationViewController udfpsAnimationViewController = udfpsView.animationViewController;
                    if (udfpsAnimationViewController != null) {
                        udfpsAnimationViewController.getView().onDisplayConfiguring();
                        udfpsAnimationViewController.getView().postInvalidate();
                    }
                    UdfpsDisplayModeProvider udfpsDisplayModeProvider = udfpsView.mUdfpsDisplayMode;
                    if (udfpsDisplayModeProvider != null) {
                        UdfpsDisplayMode udfpsDisplayMode = (UdfpsDisplayMode) udfpsDisplayModeProvider;
                        ((ExecutionImpl) udfpsDisplayMode.execution).mainLooper.isCurrentThread();
                        UdfpsLogger udfpsLogger = udfpsDisplayMode.logger;
                        udfpsLogger.getClass();
                        LogLevel logLevel = LogLevel.VERBOSE;
                        LogBuffer.log$default(udfpsLogger.logBuffer, "UdfpsDisplayMode", logLevel, "enable", null, 8, null);
                        if (udfpsDisplayMode.currentRequest != null) {
                            LogBuffer.log$default(udfpsLogger.logBuffer, "UdfpsDisplayMode", LogLevel.ERROR, "enable | already requested", null, 8, null);
                        } else {
                            AuthController authController = udfpsDisplayMode.authController;
                            if (authController.mUdfpsRefreshRateRequestCallback == null) {
                                LogBuffer.log$default(udfpsLogger.logBuffer, "UdfpsDisplayMode", LogLevel.ERROR, "enable | mDisplayManagerCallback is null", null, 8, null);
                            } else {
                                Trace.beginSection("UdfpsDisplayMode.enable");
                                Request request = new Request(udfpsDisplayMode.context.getDisplayId());
                                udfpsDisplayMode.currentRequest = request;
                                try {
                                    IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback = authController.mUdfpsRefreshRateRequestCallback;
                                    Intrinsics.checkNotNull(iUdfpsRefreshRateRequestCallback);
                                    iUdfpsRefreshRateRequestCallback.onRequestEnabled(request.displayId);
                                    LogBuffer.log$default(udfpsLogger.logBuffer, "UdfpsDisplayMode", logLevel, "enable | requested optimal refresh rate for UDFPS", null, 8, null);
                                } catch (RemoteException e) {
                                    LogLevel logLevel2 = LogLevel.ERROR;
                                    UdfpsLogger$e$2 udfpsLogger$e$2 = new UdfpsLogger$e$2("enable");
                                    LogBuffer logBuffer = udfpsLogger.logBuffer;
                                    logBuffer.commit(logBuffer.obtain("UdfpsDisplayMode", logLevel2, udfpsLogger$e$2, e));
                                }
                                dispatchOnUiReady(j);
                                if (Unit.INSTANCE == null) {
                                    LogBuffer.log$default(udfpsLogger.logBuffer, "UdfpsDisplayMode", LogLevel.WARNING, "enable | onEnabled is null", null, 8, null);
                                }
                                Trace.endSection();
                            }
                        }
                    }
                }
            }
        }
        Iterator it = ((HashSet) this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onFingerDown();
        }
    }

    public final void onFingerUp(long j, UdfpsView udfpsView) {
        onFingerUp(j, udfpsView, -1, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:238:0x053a, code lost:
    
        if (r6 != 10) goto L210;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x03cc, code lost:
    
        if (r6 != 4) goto L172;
     */
    /* JADX WARN: Removed duplicated region for block: B:46:0x04d2  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x04db  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x03db  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x043c  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x03e0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouch(long j, MotionEvent motionEvent, boolean z) {
        boolean z2;
        MotionEvent motionEvent2;
        int findPointerIndex;
        boolean z3;
        TouchProcessorResult.ProcessedTouch processedTouch;
        Object obj;
        Object obj2;
        StatusBarStateController statusBarStateController;
        AlternateBouncerInteractor alternateBouncerInteractor;
        InteractionEvent interactionEvent;
        String str;
        SystemClock systemClock;
        NormalizedTouchData normalizedTouchData;
        boolean z4;
        UdfpsController udfpsController;
        NormalizedTouchData normalizedTouchData2;
        boolean isEnabled = ((FeatureFlagsRelease) this.mFeatureFlags).isEnabled(Flags.UDFPS_NEW_TOUCH_DETECTION);
        Object obj3 = null;
        SystemClock systemClock2 = this.mSystemClock;
        FalsingManager falsingManager = this.mFalsingManager;
        if (!isEnabled) {
            UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
            if (udfpsControllerOverlay == null) {
                Log.w("UdfpsController", "ignoring onTouch with null overlay");
            } else {
                if (udfpsControllerOverlay.matchesRequestId(j)) {
                    UdfpsView udfpsView = this.mOverlay.overlayView;
                    int actionMasked = motionEvent.getActionMasked();
                    if (actionMasked != 0) {
                        if (actionMasked != 1) {
                            if (actionMasked != 2) {
                                if (actionMasked != 3) {
                                    if (actionMasked != 7) {
                                        if (actionMasked != 9) {
                                        }
                                    }
                                }
                            }
                            Trace.beginSection("UdfpsController.onTouch.ACTION_MOVE");
                            int i = this.mActivePointerId;
                            if (i == -1) {
                                z3 = false;
                                motionEvent2 = motionEvent;
                                findPointerIndex = motionEvent2.getPointerId(0);
                            } else {
                                motionEvent2 = motionEvent;
                                findPointerIndex = motionEvent2.findPointerIndex(i);
                                z3 = false;
                            }
                            if (findPointerIndex == motionEvent.getActionIndex()) {
                                boolean isWithinSensorArea = isWithinSensorArea(udfpsView, motionEvent2.getX(findPointerIndex), motionEvent2.getY(findPointerIndex), z);
                                if ((z || isWithinSensorArea) && shouldTryToDismissKeyguard()) {
                                    tryDismissingKeyguard();
                                    z2 = z3 ? 1 : 0;
                                    return z2;
                                }
                                UdfpsOverlayParams udfpsOverlayParams = this.mOverlayParams;
                                this.mUdfpsUtils.getClass();
                                Point point = new Point((int) motionEvent2.getRawX(findPointerIndex), (int) motionEvent2.getRawY(findPointerIndex));
                                int i2 = udfpsOverlayParams.rotation;
                                if (i2 == 1 || i2 == 3) {
                                    RotationUtils.rotatePoint(point, RotationUtils.deltaRotation(i2, z3 ? 1 : 0), udfpsOverlayParams.logicalDisplayWidth, udfpsOverlayParams.logicalDisplayHeight);
                                }
                                float f = point.x;
                                float f2 = udfpsOverlayParams.scaleFactor;
                                point.x = (int) (f / f2);
                                point.y = (int) (point.y / f2);
                                if (isWithinSensorArea) {
                                    if (this.mVelocityTracker == null) {
                                        this.mVelocityTracker = VelocityTracker.obtain();
                                    }
                                    this.mVelocityTracker.addMovement(motionEvent2);
                                    this.mVelocityTracker.computeCurrentVelocity(1000);
                                    VelocityTracker velocityTracker = this.mVelocityTracker;
                                    int i3 = this.mActivePointerId;
                                    float sqrt = (float) Math.sqrt(Math.pow(velocityTracker.getYVelocity(i3), 2.0d) + Math.pow(velocityTracker.getXVelocity(i3), 2.0d));
                                    float touchMinor = motionEvent2.getTouchMinor(findPointerIndex);
                                    float touchMajor = motionEvent2.getTouchMajor(findPointerIndex);
                                    boolean z5 = sqrt > 750.0f ? true : z3 ? 1 : 0;
                                    String.format("minor: %.1f, major: %.1f, v: %.1f, exceedsVelocityThreshold: %b", Float.valueOf(touchMinor), Float.valueOf(touchMajor), Float.valueOf(sqrt), Boolean.valueOf(z5));
                                    ((SystemClockImpl) systemClock2).getClass();
                                    long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - this.mTouchLogTime;
                                    if (!this.mOnFingerDown && !this.mAcquiredReceived && !z5) {
                                        float f3 = this.mOverlayParams.scaleFactor;
                                        onFingerDown(j, -1, point.x, point.y, touchMinor / f3, touchMajor / f3, 0.0f, 0L, 0L, false);
                                        this.mTouchLogTime = android.os.SystemClock.elapsedRealtime();
                                        z3 = true;
                                    } else if (elapsedRealtime >= 50) {
                                        this.mTouchLogTime = android.os.SystemClock.elapsedRealtime();
                                    }
                                } else {
                                    onFingerUp(j, udfpsView);
                                    ((ExecutorImpl) this.mFgExecutor).execute(new UdfpsController$$ExternalSyntheticLambda0(z3 ? 1 : 0, this, point));
                                }
                            }
                            Trace.endSection();
                            return z3;
                        }
                        Trace.beginSection("UdfpsController.onTouch.ACTION_UP");
                        this.mActivePointerId = -1;
                        VelocityTracker velocityTracker2 = this.mVelocityTracker;
                        if (velocityTracker2 != null) {
                            velocityTracker2.recycle();
                            this.mVelocityTracker = null;
                        }
                        this.mAttemptedToDismissKeyguard = false;
                        onFingerUp(j, udfpsView);
                        falsingManager.isFalseTouch(13);
                        Trace.endSection();
                        z2 = false;
                        return z2;
                    }
                    boolean z6 = false;
                    Trace.beginSection("UdfpsController.onTouch.ACTION_DOWN");
                    VelocityTracker velocityTracker3 = this.mVelocityTracker;
                    if (velocityTracker3 == null) {
                        this.mVelocityTracker = VelocityTracker.obtain();
                    } else {
                        velocityTracker3.clear();
                    }
                    boolean isWithinSensorArea2 = isWithinSensorArea(udfpsView, motionEvent.getX(), motionEvent.getY(), z);
                    if (isWithinSensorArea2) {
                        Trace.beginAsyncSection("UdfpsController.e2e.onPointerDown", 0);
                        this.mActivePointerId = motionEvent.getPointerId(0);
                        this.mVelocityTracker.addMovement(motionEvent);
                        this.mAcquiredReceived = false;
                        z6 = true;
                    }
                    if ((isWithinSensorArea2 || z) && shouldTryToDismissKeyguard()) {
                        tryDismissingKeyguard();
                    }
                    Trace.endSection();
                    return z6;
                }
                StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("ignoring stale touch event: ", j, " current: ");
                m17m.append(this.mOverlay.requestId);
                Log.w("UdfpsController", m17m.toString());
            }
            z2 = false;
            return z2;
        }
        if (z) {
            UdfpsControllerOverlay udfpsControllerOverlay2 = this.mOverlay;
            if (udfpsControllerOverlay2 == null) {
                Log.w("UdfpsController", "ignoring onTouch with null overlay");
            } else if (!udfpsControllerOverlay2.matchesRequestId(j)) {
                StringBuilder m17m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("ignoring stale touch event: ", j, " current: ");
                m17m2.append(this.mOverlay.requestId);
                Log.w("UdfpsController", m17m2.toString());
            } else if (this.mLockscreenShadeTransitionController.qsTransitionController.qsTransitionFraction == 0.0f && !this.mPrimaryBouncerInteractor.isInTransit()) {
                int i4 = this.mActivePointerId;
                UdfpsOverlayParams udfpsOverlayParams2 = this.mOverlayParams;
                SinglePointerTouchProcessor singlePointerTouchProcessor = this.mTouchProcessor;
                singlePointerTouchProcessor.getClass();
                switch (motionEvent.getActionMasked()) {
                    case 0:
                    case 2:
                    case 5:
                    case 7:
                    case 9:
                        PreprocessedTouch processTouch$preprocess = SinglePointerTouchProcessor.processTouch$preprocess(motionEvent, i4, udfpsOverlayParams2, singlePointerTouchProcessor);
                        Set set = SinglePointerTouchProcessorKt.SUPPORTED_ROTATIONS;
                        int i5 = processTouch$preprocess.previousPointerOnSensorId;
                        boolean z7 = i5 != -1;
                        List list = processTouch$preprocess.pointersOnSensor;
                        boolean z8 = !list.isEmpty();
                        Integer num = (Integer) CollectionsKt___CollectionsKt.firstOrNull(list);
                        int intValue = num != null ? num.intValue() : -1;
                        List list2 = processTouch$preprocess.data;
                        if (!z7 && z8) {
                            Iterator it = list2.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    Object next = it.next();
                                    if (((NormalizedTouchData) next).pointerId == intValue) {
                                        obj3 = next;
                                    }
                                }
                            }
                            NormalizedTouchData normalizedTouchData3 = (NormalizedTouchData) obj3;
                            if (normalizedTouchData3 == null) {
                                normalizedTouchData3 = new NormalizedTouchData(0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, 255, null);
                            }
                            processedTouch = new TouchProcessorResult.ProcessedTouch(InteractionEvent.DOWN, normalizedTouchData3.pointerId, normalizedTouchData3);
                        } else if (!z7 || z8) {
                            Iterator it2 = list2.iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    Object next2 = it2.next();
                                    if (((NormalizedTouchData) next2).pointerId == intValue) {
                                        obj3 = next2;
                                    }
                                }
                            }
                            NormalizedTouchData normalizedTouchData4 = (NormalizedTouchData) obj3;
                            if (normalizedTouchData4 == null && (normalizedTouchData4 = (NormalizedTouchData) CollectionsKt___CollectionsKt.firstOrNull(list2)) == null) {
                                normalizedTouchData4 = new NormalizedTouchData(0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, 255, null);
                            }
                            processedTouch = new TouchProcessorResult.ProcessedTouch(InteractionEvent.UNCHANGED, intValue, normalizedTouchData4);
                        } else {
                            Iterator it3 = list2.iterator();
                            while (true) {
                                if (it3.hasNext()) {
                                    Object next3 = it3.next();
                                    if (((NormalizedTouchData) next3).pointerId == i5) {
                                        obj3 = next3;
                                    }
                                }
                            }
                            NormalizedTouchData normalizedTouchData5 = (NormalizedTouchData) obj3;
                            if (normalizedTouchData5 == null) {
                                normalizedTouchData5 = new NormalizedTouchData(0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, 255, null);
                            }
                            processedTouch = new TouchProcessorResult.ProcessedTouch(InteractionEvent.UP, -1, normalizedTouchData5);
                        }
                        obj = processedTouch;
                        break;
                    case 1:
                    case 6:
                    case 10:
                        PreprocessedTouch processTouch$preprocess2 = SinglePointerTouchProcessor.processTouch$preprocess(motionEvent, i4, udfpsOverlayParams2, singlePointerTouchProcessor);
                        int pointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                        Set set2 = SinglePointerTouchProcessorKt.SUPPORTED_ROTATIONS;
                        List list3 = processTouch$preprocess2.pointersOnSensor;
                        int size = list3.size();
                        List list4 = processTouch$preprocess2.data;
                        if (size == 1 && list3.contains(Integer.valueOf(pointerId))) {
                            Iterator it4 = list4.iterator();
                            while (true) {
                                if (it4.hasNext()) {
                                    Object next4 = it4.next();
                                    if (((NormalizedTouchData) next4).pointerId == pointerId) {
                                        obj3 = next4;
                                    }
                                }
                            }
                            NormalizedTouchData normalizedTouchData6 = (NormalizedTouchData) obj3;
                            if (normalizedTouchData6 == null) {
                                normalizedTouchData6 = new NormalizedTouchData(0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, 255, null);
                            }
                            processedTouch = new TouchProcessorResult.ProcessedTouch(InteractionEvent.UP, -1, normalizedTouchData6);
                        } else {
                            Iterator it5 = list3.iterator();
                            while (true) {
                                if (it5.hasNext()) {
                                    obj2 = it5.next();
                                    if (((Number) obj2).intValue() != pointerId) {
                                    }
                                } else {
                                    obj2 = null;
                                }
                            }
                            Integer num2 = (Integer) obj2;
                            int intValue2 = num2 != null ? num2.intValue() : -1;
                            Iterator it6 = list4.iterator();
                            while (true) {
                                if (it6.hasNext()) {
                                    Object next5 = it6.next();
                                    if (((NormalizedTouchData) next5).pointerId == intValue2) {
                                        obj3 = next5;
                                    }
                                }
                            }
                            NormalizedTouchData normalizedTouchData7 = (NormalizedTouchData) obj3;
                            if (normalizedTouchData7 == null && (normalizedTouchData7 = (NormalizedTouchData) CollectionsKt___CollectionsKt.firstOrNull(list4)) == null) {
                                normalizedTouchData7 = new NormalizedTouchData(0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, 255, null);
                            }
                            processedTouch = new TouchProcessorResult.ProcessedTouch(InteractionEvent.UNCHANGED, intValue2, normalizedTouchData7);
                        }
                        obj = processedTouch;
                        break;
                    case 3:
                        NormalizedTouchData normalizedTouchData8 = new NormalizedTouchData(0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, 255, null);
                        Set set3 = SinglePointerTouchProcessorKt.SUPPORTED_ROTATIONS;
                        obj = new TouchProcessorResult.ProcessedTouch(InteractionEvent.CANCEL, -1, normalizedTouchData8);
                        break;
                    case 4:
                    case 8:
                    default:
                        obj = new TouchProcessorResult.Failure(KeyAttributes$$ExternalSyntheticOutline0.m21m("Unsupported MotionEvent.", MotionEvent.actionToString(motionEvent.getActionMasked())));
                        break;
                }
                if (!(obj instanceof TouchProcessorResult.Failure)) {
                    TouchProcessorResult.ProcessedTouch processedTouch2 = (TouchProcessorResult.ProcessedTouch) obj;
                    this.mActivePointerId = processedTouch2.pointerOnSensorId;
                    int[] iArr = AbstractC10703.f232x56fbb812;
                    InteractionEvent interactionEvent2 = processedTouch2.event;
                    int i6 = iArr[interactionEvent2.ordinal()];
                    NormalizedTouchData normalizedTouchData9 = processedTouch2.touchData;
                    AlternateBouncerInteractor alternateBouncerInteractor2 = this.mAlternateBouncerInteractor;
                    StatusBarStateController statusBarStateController2 = this.mStatusBarStateController;
                    if (i6 != 1) {
                        if (i6 == 2 || i6 == 3) {
                            if (InteractionEvent.CANCEL.equals(interactionEvent2)) {
                                Log.w("UdfpsController", "This is a CANCEL event that's reported as an UP event!");
                            }
                            this.mAttemptedToDismissKeyguard = false;
                            statusBarStateController = statusBarStateController2;
                            alternateBouncerInteractor = alternateBouncerInteractor2;
                            normalizedTouchData = normalizedTouchData9;
                            str = "UdfpsController";
                            systemClock = systemClock2;
                            interactionEvent = interactionEvent2;
                            onFingerUp(j, this.mOverlay.overlayView, normalizedTouchData9.pointerId, normalizedTouchData9.f234x, normalizedTouchData9.f235y, normalizedTouchData9.minor, normalizedTouchData9.major, normalizedTouchData9.orientation, normalizedTouchData9.time, normalizedTouchData9.gestureStart, statusBarStateController2.isDozing());
                        } else {
                            if (i6 == 4 && !isWithinSensorArea(this.mOverlay.overlayView, motionEvent.getRawX(), motionEvent.getRawY(), true) && this.mActivePointerId == -1 && alternateBouncerInteractor2.isVisibleState()) {
                                this.mKeyguardViewManager.onTouch(motionEvent);
                            }
                            statusBarStateController = statusBarStateController2;
                            alternateBouncerInteractor = alternateBouncerInteractor2;
                            normalizedTouchData = normalizedTouchData9;
                            interactionEvent = interactionEvent2;
                            str = "UdfpsController";
                            systemClock = systemClock2;
                        }
                        z4 = false;
                    } else {
                        statusBarStateController = statusBarStateController2;
                        alternateBouncerInteractor = alternateBouncerInteractor2;
                        interactionEvent = interactionEvent2;
                        str = "UdfpsController";
                        systemClock = systemClock2;
                        if (shouldTryToDismissKeyguard()) {
                            tryDismissingKeyguard();
                        }
                        normalizedTouchData = normalizedTouchData9;
                        onFingerDown(j, normalizedTouchData9.pointerId, normalizedTouchData9.f234x, normalizedTouchData9.f235y, normalizedTouchData9.minor, normalizedTouchData9.major, normalizedTouchData9.orientation, normalizedTouchData9.time, normalizedTouchData9.gestureStart, statusBarStateController.isDozing());
                        falsingManager.isFalseTouch(13);
                        z4 = true;
                    }
                    InteractionEvent interactionEvent3 = interactionEvent;
                    if (interactionEvent3 == InteractionEvent.UNCHANGED) {
                        ((SystemClockImpl) systemClock).getClass();
                        udfpsController = this;
                        if (android.os.SystemClock.elapsedRealtime() - udfpsController.mLastTouchInteractionTime < 50) {
                            normalizedTouchData2 = normalizedTouchData;
                            if (!udfpsController.isWithinSensorArea(udfpsController.mOverlay.overlayView, motionEvent.getRawX(), motionEvent.getRawY(), true) || alternateBouncerInteractor.isVisibleState()) {
                                z4 = true;
                            }
                            if (z4) {
                                udfpsController.mInputManager.pilferPointers(udfpsController.mOverlay.overlayView.getViewRootImpl().getInputToken());
                            }
                            return normalizedTouchData2.isWithinBounds(udfpsController.mOverlayParams.nativeSensorBounds);
                        }
                    } else {
                        udfpsController = this;
                    }
                    ((SystemClockImpl) systemClock).getClass();
                    udfpsController.mLastTouchInteractionTime = android.os.SystemClock.elapsedRealtime();
                    int i7 = iArr[interactionEvent3.ordinal()];
                    int i8 = 1;
                    int i9 = i7 != 1 ? i7 != 2 ? i7 != 3 ? 0 : 3 : 2 : 1;
                    UdfpsControllerOverlay udfpsControllerOverlay3 = udfpsController.mOverlay;
                    if (udfpsControllerOverlay3 != null) {
                        int i10 = udfpsControllerOverlay3.requestReason;
                        if (i10 == 1 || i10 == 2) {
                            i8 = 4;
                        } else if (i10 == 3) {
                            i8 = 2;
                        }
                        InstanceId sessionId = udfpsController.mSessionTracker.getSessionId(i8);
                        int id = sessionId == null ? sessionId.getId() : -1;
                        int integer = udfpsController.mContext.getResources().getInteger(R.integer.config_sideFpsToastTimeout);
                        normalizedTouchData2 = normalizedTouchData;
                        float f4 = normalizedTouchData2.f234x;
                        boolean isDozing = statusBarStateController.isDozing();
                        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
                        newBuilder.setAtomId(577);
                        newBuilder.writeInt(i9);
                        newBuilder.writeInt(integer);
                        newBuilder.writeInt(id);
                        newBuilder.writeFloat(f4);
                        float f5 = normalizedTouchData2.f235y;
                        newBuilder.writeFloat(f5);
                        float f6 = normalizedTouchData2.minor;
                        newBuilder.writeFloat(f6);
                        float f7 = normalizedTouchData2.major;
                        newBuilder.writeFloat(f7);
                        float f8 = normalizedTouchData2.orientation;
                        newBuilder.writeFloat(f8);
                        long j2 = normalizedTouchData2.time;
                        newBuilder.writeLong(j2);
                        long j3 = normalizedTouchData2.gestureStart;
                        newBuilder.writeLong(j3);
                        newBuilder.writeBoolean(isDozing);
                        newBuilder.usePooledBuffer();
                        StatsLog.write(newBuilder.build());
                        if (Build.isDebuggable()) {
                            StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("\n        |NormalizedTouchData [", interactionEvent3.toString(), "] {\n        |     pointerId: ");
                            m4m.append(normalizedTouchData2.pointerId);
                            m4m.append("\n        |             x: ");
                            m4m.append(normalizedTouchData2.f234x);
                            m4m.append("\n        |             y: ");
                            m4m.append(f5);
                            m4m.append("\n        |         minor: ");
                            m4m.append(f6);
                            m4m.append("\n        |         major: ");
                            m4m.append(f7);
                            m4m.append("\n        |   orientation: ");
                            m4m.append(f8);
                            m4m.append("\n        |          time: ");
                            m4m.append(j2);
                            m4m.append("\n        |  gestureStart: ");
                            m4m.append(j3);
                            m4m.append("\n        |}\n        ");
                            String str2 = str;
                            Log.d(str2, StringsKt__IndentKt.trimMargin$default(m4m.toString()));
                            Log.d(str2, "sessionId: " + id + ", isAod: " + statusBarStateController.isDozing() + ", touchConfigId: " + integer);
                        }
                        if (!udfpsController.isWithinSensorArea(udfpsController.mOverlay.overlayView, motionEvent.getRawX(), motionEvent.getRawY(), true)) {
                        }
                        z4 = true;
                        if (z4) {
                        }
                        return normalizedTouchData2.isWithinBounds(udfpsController.mOverlayParams.nativeSensorBounds);
                    }
                    i8 = -1;
                    InstanceId sessionId2 = udfpsController.mSessionTracker.getSessionId(i8);
                    if (sessionId2 == null) {
                    }
                    int integer2 = udfpsController.mContext.getResources().getInteger(R.integer.config_sideFpsToastTimeout);
                    normalizedTouchData2 = normalizedTouchData;
                    float f42 = normalizedTouchData2.f234x;
                    boolean isDozing2 = statusBarStateController.isDozing();
                    StatsEvent.Builder newBuilder2 = StatsEvent.newBuilder();
                    newBuilder2.setAtomId(577);
                    newBuilder2.writeInt(i9);
                    newBuilder2.writeInt(integer2);
                    newBuilder2.writeInt(id);
                    newBuilder2.writeFloat(f42);
                    float f52 = normalizedTouchData2.f235y;
                    newBuilder2.writeFloat(f52);
                    float f62 = normalizedTouchData2.minor;
                    newBuilder2.writeFloat(f62);
                    float f72 = normalizedTouchData2.major;
                    newBuilder2.writeFloat(f72);
                    float f82 = normalizedTouchData2.orientation;
                    newBuilder2.writeFloat(f82);
                    long j22 = normalizedTouchData2.time;
                    newBuilder2.writeLong(j22);
                    long j32 = normalizedTouchData2.gestureStart;
                    newBuilder2.writeLong(j32);
                    newBuilder2.writeBoolean(isDozing2);
                    newBuilder2.usePooledBuffer();
                    StatsLog.write(newBuilder2.build());
                    if (Build.isDebuggable()) {
                    }
                    if (!udfpsController.isWithinSensorArea(udfpsController.mOverlay.overlayView, motionEvent.getRawX(), motionEvent.getRawY(), true)) {
                    }
                    z4 = true;
                    if (z4) {
                    }
                    return normalizedTouchData2.isWithinBounds(udfpsController.mOverlayParams.nativeSensorBounds);
                }
                Log.w("UdfpsController", ((TouchProcessorResult.Failure) obj).reason);
            }
        } else {
            Log.e("UdfpsController", "ignoring the touch injected from outside of UdfpsView");
        }
        return false;
    }

    public void playStartHaptic() {
        if (this.mAccessibilityManager.isTouchExplorationEnabled()) {
            this.mVibrator.vibrate(Process.myUid(), this.mContext.getOpPackageName(), EFFECT_CLICK, "udfps-onStart-click", UDFPS_VIBRATION_ATTRIBUTES);
        }
    }

    public final boolean shouldTryToDismissKeyguard() {
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay != null) {
            UdfpsView udfpsView = udfpsControllerOverlay.overlayView;
            if (((udfpsView != null ? udfpsView.animationViewController : null) instanceof UdfpsKeyguardViewControllerLegacy) && ((KeyguardStateControllerImpl) this.mKeyguardStateController).mCanDismissLockScreen && !this.mAttemptedToDismissKeyguard) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:23:0x005e A[Catch: RuntimeException -> 0x0095, TryCatch #0 {RuntimeException -> 0x0095, blocks: (B:12:0x0033, B:14:0x004b, B:23:0x005e, B:24:0x0061, B:26:0x0083, B:27:0x0086), top: B:11:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0083 A[Catch: RuntimeException -> 0x0095, TryCatch #0 {RuntimeException -> 0x0095, blocks: (B:12:0x0033, B:14:0x004b, B:23:0x005e, B:24:0x0061, B:26:0x0083, B:27:0x0086), top: B:11:0x0033 }] */
    /* JADX WARN: Type inference failed for: r0v9, types: [android.view.accessibility.AccessibilityManager$TouchExplorationStateChangeListener, com.android.systemui.biometrics.UdfpsControllerOverlay$show$1$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void showUdfpsOverlay(final UdfpsControllerOverlay udfpsControllerOverlay) {
        boolean z;
        boolean z2;
        UdfpsControllerOverlay$show$1$1 udfpsControllerOverlay$show$1$1;
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        this.mOverlay = udfpsControllerOverlay;
        int i = udfpsControllerOverlay.requestReason;
        if (i == 4 && !this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning()) {
            Log.d("UdfpsController", "Attempting to showUdfpsOverlay when fingerprint detection isn't running on keyguard. Skip show.");
            return;
        }
        UdfpsOverlayParams udfpsOverlayParams = this.mOverlayParams;
        AccessibilityManager accessibilityManager = udfpsControllerOverlay.accessibilityManager;
        if (udfpsControllerOverlay.overlayView == null) {
            udfpsControllerOverlay.overlayParams = udfpsOverlayParams;
            udfpsControllerOverlay.sensorBounds = new Rect(udfpsOverlayParams.sensorBounds);
            z = true;
            try {
                final UdfpsView udfpsView = (UdfpsView) udfpsControllerOverlay.inflater.inflate(com.android.systemui.R.layout.udfps_view, (ViewGroup) null, false);
                udfpsView.overlayParams = udfpsOverlayParams;
                udfpsView.mUdfpsDisplayMode = udfpsControllerOverlay.udfpsDisplayModeProvider;
                UdfpsAnimationViewController inflateUdfpsAnimation = udfpsControllerOverlay.inflateUdfpsAnimation(this, udfpsView);
                if (inflateUdfpsAnimation != null) {
                    inflateUdfpsAnimation.init();
                    udfpsView.animationViewController = inflateUdfpsAnimation;
                }
                if (i != 1 && i != 2 && i != 3) {
                    z2 = false;
                    if (z2) {
                        udfpsView.setImportantForAccessibility(2);
                    }
                    WindowManager windowManager = udfpsControllerOverlay.windowManager;
                    WindowManager.LayoutParams layoutParams = udfpsControllerOverlay.coreLayoutParams;
                    udfpsControllerOverlay.updateDimensions(layoutParams, inflateUdfpsAnimation);
                    windowManager.addView(udfpsView, layoutParams);
                    udfpsView.sensorRect = udfpsControllerOverlay.sensorBounds;
                    udfpsControllerOverlay.touchExplorationEnabled = accessibilityManager.isTouchExplorationEnabled();
                    ?? r0 = new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$1$1
                        @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
                        public final void onTouchExplorationStateChanged(boolean z3) {
                            if (UdfpsControllerOverlay.this.accessibilityManager.isTouchExplorationEnabled()) {
                                UdfpsView udfpsView2 = udfpsView;
                                final UdfpsControllerOverlay udfpsControllerOverlay2 = UdfpsControllerOverlay.this;
                                udfpsView2.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$1$1.1
                                    @Override // android.view.View.OnHoverListener
                                    public final boolean onHover(View view, MotionEvent motionEvent) {
                                        return ((Boolean) UdfpsControllerOverlay.this.onTouch.invoke(view, motionEvent, Boolean.TRUE)).booleanValue();
                                    }
                                });
                                udfpsView.setOnTouchListener(null);
                                UdfpsControllerOverlay.this.touchExplorationEnabled = true;
                                return;
                            }
                            udfpsView.setOnHoverListener(null);
                            UdfpsView udfpsView3 = udfpsView;
                            final UdfpsControllerOverlay udfpsControllerOverlay3 = UdfpsControllerOverlay.this;
                            udfpsView3.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$1$1.2
                                @Override // android.view.View.OnTouchListener
                                public final boolean onTouch(View view, MotionEvent motionEvent) {
                                    return ((Boolean) UdfpsControllerOverlay.this.onTouch.invoke(view, motionEvent, Boolean.TRUE)).booleanValue();
                                }
                            });
                            UdfpsControllerOverlay.this.touchExplorationEnabled = false;
                        }
                    };
                    udfpsControllerOverlay.overlayTouchListener = r0;
                    accessibilityManager.addTouchExplorationStateChangeListener(r0);
                    udfpsControllerOverlay$show$1$1 = udfpsControllerOverlay.overlayTouchListener;
                    if (udfpsControllerOverlay$show$1$1 != null) {
                        udfpsControllerOverlay$show$1$1.onTouchExplorationStateChanged(true);
                    }
                    udfpsView.useExpandedOverlay = ((FeatureFlagsRelease) udfpsControllerOverlay.featureFlags).isEnabled(Flags.UDFPS_NEW_TOUCH_DETECTION);
                    udfpsControllerOverlay.overlayView = udfpsView;
                }
                z2 = true;
                if (z2) {
                }
                WindowManager windowManager2 = udfpsControllerOverlay.windowManager;
                WindowManager.LayoutParams layoutParams2 = udfpsControllerOverlay.coreLayoutParams;
                udfpsControllerOverlay.updateDimensions(layoutParams2, inflateUdfpsAnimation);
                windowManager2.addView(udfpsView, layoutParams2);
                udfpsView.sensorRect = udfpsControllerOverlay.sensorBounds;
                udfpsControllerOverlay.touchExplorationEnabled = accessibilityManager.isTouchExplorationEnabled();
                ?? r02 = new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$1$1
                    @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
                    public final void onTouchExplorationStateChanged(boolean z3) {
                        if (UdfpsControllerOverlay.this.accessibilityManager.isTouchExplorationEnabled()) {
                            UdfpsView udfpsView2 = udfpsView;
                            final UdfpsControllerOverlay udfpsControllerOverlay2 = UdfpsControllerOverlay.this;
                            udfpsView2.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$1$1.1
                                @Override // android.view.View.OnHoverListener
                                public final boolean onHover(View view, MotionEvent motionEvent) {
                                    return ((Boolean) UdfpsControllerOverlay.this.onTouch.invoke(view, motionEvent, Boolean.TRUE)).booleanValue();
                                }
                            });
                            udfpsView.setOnTouchListener(null);
                            UdfpsControllerOverlay.this.touchExplorationEnabled = true;
                            return;
                        }
                        udfpsView.setOnHoverListener(null);
                        UdfpsView udfpsView3 = udfpsView;
                        final UdfpsControllerOverlay udfpsControllerOverlay3 = UdfpsControllerOverlay.this;
                        udfpsView3.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$1$1.2
                            @Override // android.view.View.OnTouchListener
                            public final boolean onTouch(View view, MotionEvent motionEvent) {
                                return ((Boolean) UdfpsControllerOverlay.this.onTouch.invoke(view, motionEvent, Boolean.TRUE)).booleanValue();
                            }
                        });
                        UdfpsControllerOverlay.this.touchExplorationEnabled = false;
                    }
                };
                udfpsControllerOverlay.overlayTouchListener = r02;
                accessibilityManager.addTouchExplorationStateChangeListener(r02);
                udfpsControllerOverlay$show$1$1 = udfpsControllerOverlay.overlayTouchListener;
                if (udfpsControllerOverlay$show$1$1 != null) {
                }
                udfpsView.useExpandedOverlay = ((FeatureFlagsRelease) udfpsControllerOverlay.featureFlags).isEnabled(Flags.UDFPS_NEW_TOUCH_DETECTION);
                udfpsControllerOverlay.overlayView = udfpsView;
            } catch (RuntimeException e) {
                Log.e("UdfpsControllerOverlay", "showUdfpsOverlay | failed to add window", e);
            }
        } else {
            z = false;
        }
        if (z) {
            this.mOnFingerDown = false;
            this.mAttemptedToDismissKeyguard = false;
            this.mOrientationListener.enable();
        }
    }

    public void tryAodSendFingerUp() {
        UdfpsView udfpsView;
        if (this.mIsAodInterruptActive) {
            cancelAodSendFingerUpAction();
            UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
            if (udfpsControllerOverlay == null || (udfpsView = udfpsControllerOverlay.overlayView) == null) {
                return;
            }
            onFingerUp(udfpsControllerOverlay.requestId, udfpsView);
        }
    }

    public final void tryDismissingKeyguard() {
        if (!this.mOnFingerDown) {
            playStartHaptic();
        }
        this.mKeyguardViewManager.notifyKeyguardAuthenticated(false);
        this.mAttemptedToDismissKeyguard = true;
    }

    public final void onFingerUp(long j, UdfpsView udfpsView, int i, float f, float f2, float f3, float f4, float f5, long j2, long j3, boolean z) {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        this.mActivePointerId = -1;
        this.mAcquiredReceived = false;
        if (this.mOnFingerDown) {
            if (((FeatureFlagsRelease) this.mFeatureFlags).isEnabled(Flags.UDFPS_NEW_TOUCH_DETECTION)) {
                this.mFingerprintManager.onPointerUp(j, this.mSensorProps.sensorId, i, f, f2, f3, f4, f5, j2, j3, z);
            } else {
                this.mFingerprintManager.onPointerUp(j, this.mSensorProps.sensorId);
            }
            Iterator it = ((HashSet) this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onFingerUp();
            }
        }
        this.mOnFingerDown = false;
        if ((this.mSensorProps.sensorType == 3) && udfpsView.isDisplayConfigured) {
            udfpsView.unconfigureDisplay$1();
        }
        cancelAodSendFingerUpAction();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UdfpsOverlayController extends IUdfpsOverlayController.Stub {
        public UdfpsOverlayController() {
        }

        public final void hideUdfpsOverlay(int i) {
            FeatureFlags featureFlags = UdfpsController.this.mFeatureFlags;
            Flags flags = Flags.INSTANCE;
            featureFlags.getClass();
            ((ExecutorImpl) UdfpsController.this.mFgExecutor).execute(new UdfpsController$$ExternalSyntheticLambda3(this, 2));
        }

        public final void onAcquired(final int i, final int i2) {
            if (BiometricFingerprintConstants.shouldDisableUdfpsDisplayMode(i2)) {
                ((ExecutorImpl) UdfpsController.this.mFgExecutor).execute(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        UdfpsController.UdfpsOverlayController udfpsOverlayController = UdfpsController.UdfpsOverlayController.this;
                        int i3 = i;
                        int i4 = i2;
                        UdfpsController udfpsController = UdfpsController.this;
                        UdfpsControllerOverlay udfpsControllerOverlay = udfpsController.mOverlay;
                        if (udfpsControllerOverlay == null) {
                            Log.e("UdfpsController", "Null request when onAcquired for sensorId: " + i3 + " acquiredInfo=" + i4);
                            return;
                        }
                        udfpsController.mAcquiredReceived = true;
                        UdfpsView udfpsView = udfpsControllerOverlay.overlayView;
                        if (udfpsView != null) {
                            if (udfpsController.mSensorProps.sensorType == 3) {
                                udfpsController.getClass();
                                if (udfpsView.isDisplayConfigured) {
                                    udfpsView.unconfigureDisplay$1();
                                }
                            }
                        }
                        UdfpsController.this.tryAodSendFingerUp();
                    }
                });
            }
        }

        public final void setDebugMessage(int i, String str) {
            ((ExecutorImpl) UdfpsController.this.mFgExecutor).execute(new UdfpsController$$ExternalSyntheticLambda0(1, this, str));
        }

        public final void showUdfpsOverlay(final long j, int i, final int i2, final IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback) {
            FeatureFlags featureFlags = UdfpsController.this.mFeatureFlags;
            Flags flags = Flags.INSTANCE;
            featureFlags.getClass();
            ((ExecutorImpl) UdfpsController.this.mFgExecutor).execute(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    final UdfpsController.UdfpsOverlayController udfpsOverlayController = UdfpsController.UdfpsOverlayController.this;
                    final long j2 = j;
                    int i3 = i2;
                    IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback2 = iUdfpsOverlayControllerCallback;
                    UdfpsController udfpsController = UdfpsController.this;
                    udfpsController.showUdfpsOverlay(new UdfpsControllerOverlay(udfpsController.mContext, udfpsController.mFingerprintManager, udfpsController.mInflater, udfpsController.mWindowManager, udfpsController.mAccessibilityManager, udfpsController.mStatusBarStateController, udfpsController.mShadeExpansionStateManager, udfpsController.mKeyguardViewManager, udfpsController.mKeyguardUpdateMonitor, udfpsController.mDialogManager, udfpsController.mDumpManager, udfpsController.mLockscreenShadeTransitionController, udfpsController.mConfigurationController, udfpsController.mKeyguardStateController, udfpsController.mUnlockedScreenOffAnimationController, udfpsController.mUdfpsDisplayMode, udfpsController.mSecureSettings, j2, i3, iUdfpsOverlayControllerCallback2, new Function3() { // from class: com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            UdfpsController udfpsController2 = UdfpsController.this;
                            boolean booleanValue = ((Boolean) obj3).booleanValue();
                            return Boolean.valueOf(udfpsController2.onTouch(j2, (MotionEvent) obj2, booleanValue));
                        }
                    }, udfpsController.mActivityLaunchAnimator, udfpsController.mFeatureFlags, udfpsController.mPrimaryBouncerInteractor, udfpsController.mAlternateBouncerInteractor, udfpsController.mUdfpsUtils));
                }
            });
        }

        public final void onEnrollmentHelp(int i) {
        }

        public final void onEnrollmentProgress(int i, int i2) {
        }
    }
}
