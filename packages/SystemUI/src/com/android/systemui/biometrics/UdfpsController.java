package com.android.systemui.biometrics;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.hardware.biometrics.BiometricFingerprintConstants;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IUdfpsOverlayController;
import android.hardware.fingerprint.IUdfpsOverlayControllerCallback;
import android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.Trace;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.util.LatencyTracker;
import com.android.internal.util.Preconditions;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.UserActivityNotifier;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7;
import com.android.systemui.Dumpable;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.biometrics.BiometricDisplayListener;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.biometrics.udfps.InteractionEvent;
import com.android.systemui.biometrics.udfps.SinglePointerTouchProcessor;
import com.android.systemui.biometrics.ui.binder.UdfpsTouchOverlayBinder;
import com.android.systemui.biometrics.ui.view.UdfpsTouchOverlay;
import com.android.systemui.biometrics.ui.viewmodel.UdfpsTouchOverlayViewModel;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.doze.DozeReceiver;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.time.SystemClock;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class UdfpsController implements DozeReceiver, Dumpable {
    public final AccessibilityManager mAccessibilityManager;
    public final ActivityTransitionAnimator mActivityTransitionAnimator;
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public UdfpsController$$ExternalSyntheticLambda1 mAodInterruptRunnable;
    public boolean mAttemptedToDismissKeyguard;
    public AuthController$$ExternalSyntheticLambda3 mAuthControllerUpdateUdfpsLocation;
    public final Executor mBiometricExecutor;
    public final AnonymousClass2 mBroadcastReceiver;
    public Runnable mCancelAodFingerUpAction;
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public final Lazy mDefaultUdfpsTouchOverlayViewModel;
    public final DeviceEntryFaceAuthInteractor mDeviceEntryFaceAuthInteractor;
    public final Lazy mDeviceEntryUdfpsTouchOverlayViewModel;
    public final SystemUIDialogManager mDialogManager;
    public final DumpManager mDumpManager;
    public final Execution mExecution;
    public final FalsingManager mFalsingManager;
    public final DelayableExecutor mFgExecutor;
    public final FingerprintManager mFingerprintManager;
    public final boolean mIgnoreRefreshRate;
    public final LayoutInflater mInflater;
    public final InputManager mInputManager;
    public boolean mIsAodInterruptActive;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardTransitionInteractor mKeyguardTransitionInteractor;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final StatusBarKeyguardViewManager mKeyguardViewManager;
    public long mLastTouchInteractionTime;
    public final LatencyTracker mLatencyTracker;
    public boolean mOnFingerDown;
    final BiometricDisplayListener mOrientationListener;
    public UdfpsControllerOverlay mOverlay;
    public final PowerInteractor mPowerInteractor;
    public final PowerManager mPowerManager;
    public final PrimaryBouncerInteractor mPrimaryBouncerInteractor;
    public final CoroutineScope mScope;
    public final AnonymousClass1 mScreenObserver;
    public boolean mScreenOn;
    public final SelectedUserInteractor mSelectedUserInteractor;
    FingerprintSensorPropertiesInternal mSensorProps;
    public final SessionTracker mSessionTracker;
    public final ShadeInteractor mShadeInteractor;
    public final StatusBarStateController mStatusBarStateController;
    public final SystemClock mSystemClock;
    public final SinglePointerTouchProcessor mTouchProcessor;
    public UdfpsDisplayMode mUdfpsDisplayMode;
    public final UdfpsOverlayInteractor mUdfpsOverlayInteractor;
    public final UnlockedScreenOffAnimationController mUnlockedScreenOffAnimationController;
    public final VibratorHelper mVibrator;
    public final WindowManager mWindowManager;
    public static final VibrationAttributes UDFPS_VIBRATION_ATTRIBUTES = new VibrationAttributes.Builder().setUsage(65).build();
    public static final VibrationAttributes LOCK_ICON_VIBRATION_ATTRIBUTES = new VibrationAttributes.Builder().setUsage(18).build();
    UdfpsOverlayParams mOverlayParams = new UdfpsOverlayParams();
    public int mActivePointerId = -1;
    public boolean mPointerPilfered = false;
    public final Set mCallbacks = new HashSet();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.biometrics.UdfpsController$3, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass3 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$biometrics$udfps$InteractionEvent;

        static {
            int[] iArr = new int[InteractionEvent.values().length];
            $SwitchMap$com$android$systemui$biometrics$udfps$InteractionEvent = iArr;
            try {
                iArr[InteractionEvent.DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$biometrics$udfps$InteractionEvent[InteractionEvent.UP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$biometrics$udfps$InteractionEvent[InteractionEvent.CANCEL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        void onFingerDown();

        void onFingerUp();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:71:0x039e  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0403  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x03a3  */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v17, types: [int] */
    /* JADX WARN: Type inference failed for: r1v29 */
    /* JADX WARN: Type inference failed for: r1v30 */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r2v10, types: [int] */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r3v23, types: [com.android.systemui.log.SessionTracker] */
    /* JADX WARN: Type inference failed for: r4v16, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v28, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v6, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v8, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v15, types: [java.lang.Object] */
    /* renamed from: -$$Nest$monTouch, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean m1019$$Nest$monTouch(com.android.systemui.biometrics.UdfpsController r32, long r33, android.view.MotionEvent r35) {
        /*
            Method dump skipped, instructions count: 1250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.UdfpsController.m1019$$Nest$monTouch(com.android.systemui.biometrics.UdfpsController, long, android.view.MotionEvent):boolean");
    }

    static {
        VibrationEffect.get(0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.biometrics.UdfpsController$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.content.BroadcastReceiver, com.android.systemui.biometrics.UdfpsController$2] */
    public UdfpsController(Context context, Execution execution, LayoutInflater layoutInflater, FingerprintManager fingerprintManager, WindowManager windowManager, StatusBarStateController statusBarStateController, DelayableExecutor delayableExecutor, StatusBarKeyguardViewManager statusBarKeyguardViewManager, DumpManager dumpManager, KeyguardUpdateMonitor keyguardUpdateMonitor, FalsingManager falsingManager, PowerManager powerManager, AccessibilityManager accessibilityManager, ScreenLifecycle screenLifecycle, VibratorHelper vibratorHelper, UdfpsHapticsSimulator udfpsHapticsSimulator, UdfpsShell udfpsShell, KeyguardStateController keyguardStateController, DisplayManager displayManager, Handler handler, ConfigurationController configurationController, SystemClock systemClock, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, SystemUIDialogManager systemUIDialogManager, LatencyTracker latencyTracker, ActivityTransitionAnimator activityTransitionAnimator, Executor executor, PrimaryBouncerInteractor primaryBouncerInteractor, ShadeInteractor shadeInteractor, SinglePointerTouchProcessor singlePointerTouchProcessor, SessionTracker sessionTracker, AlternateBouncerInteractor alternateBouncerInteractor, InputManager inputManager, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, SelectedUserInteractor selectedUserInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, Lazy lazy, Lazy lazy2, UdfpsOverlayInteractor udfpsOverlayInteractor, PowerInteractor powerInteractor, CoroutineScope coroutineScope, UserActivityNotifier userActivityNotifier) {
        ?? r3 = new ScreenLifecycle.Observer() { // from class: com.android.systemui.biometrics.UdfpsController.1
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurnedOff() {
                UdfpsController.this.mScreenOn = false;
            }

            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurnedOn() {
                UdfpsController udfpsController = UdfpsController.this;
                udfpsController.mScreenOn = true;
                UdfpsController$$ExternalSyntheticLambda1 udfpsController$$ExternalSyntheticLambda1 = udfpsController.mAodInterruptRunnable;
                if (udfpsController$$ExternalSyntheticLambda1 != null) {
                    udfpsController$$ExternalSyntheticLambda1.run();
                    udfpsController.mAodInterruptRunnable = null;
                }
            }
        };
        this.mScreenObserver = r3;
        ?? r4 = new BroadcastReceiver() { // from class: com.android.systemui.biometrics.UdfpsController.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                UdfpsControllerOverlay udfpsControllerOverlay = UdfpsController.this.mOverlay;
                if (udfpsControllerOverlay == null || udfpsControllerOverlay.requestReason == 4 || !PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    return;
                }
                String stringExtra = intent.getStringExtra("reason");
                if (stringExtra == null) {
                    stringExtra = "unknown";
                }
                RecyclerView$$ExternalSyntheticOutline0.m(UdfpsController.this.mOverlay.requestReason, "UdfpsController", ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("ACTION_CLOSE_SYSTEM_DIALOGS received, reason: ", stringExtra, ", mRequestReason: "));
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
        this.mBroadcastReceiver = r4;
        this.mContext = context;
        this.mExecution = execution;
        this.mVibrator = vibratorHelper;
        this.mInflater = layoutInflater;
        this.mIgnoreRefreshRate = context.getResources().getBoolean(R.bool.config_magnification_area);
        FingerprintManager fingerprintManager2 = (FingerprintManager) Preconditions.checkNotNull(fingerprintManager);
        this.mFingerprintManager = fingerprintManager2;
        this.mWindowManager = windowManager;
        this.mFgExecutor = delayableExecutor;
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardStateController = keyguardStateController;
        this.mKeyguardViewManager = statusBarKeyguardViewManager;
        this.mDumpManager = dumpManager;
        this.mDialogManager = systemUIDialogManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mFalsingManager = falsingManager;
        this.mPowerManager = powerManager;
        this.mAccessibilityManager = accessibilityManager;
        screenLifecycle.addObserver(r3);
        this.mScreenOn = screenLifecycle.mScreenState == 2;
        this.mConfigurationController = configurationController;
        this.mSystemClock = systemClock;
        this.mUnlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        this.mLatencyTracker = latencyTracker;
        this.mActivityTransitionAnimator = activityTransitionAnimator;
        this.mSensorProps = new FingerprintSensorPropertiesInternal(-1, 0, 0, new ArrayList(), 0, false);
        this.mBiometricExecutor = executor;
        this.mPrimaryBouncerInteractor = primaryBouncerInteractor;
        this.mShadeInteractor = shadeInteractor;
        this.mAlternateBouncerInteractor = alternateBouncerInteractor;
        this.mUdfpsOverlayInteractor = udfpsOverlayInteractor;
        this.mPowerInteractor = powerInteractor;
        this.mScope = coroutineScope;
        this.mInputManager = inputManager;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mKeyguardTransitionInteractor = keyguardTransitionInteractor;
        this.mTouchProcessor = singlePointerTouchProcessor;
        this.mSessionTracker = sessionTracker;
        this.mDeviceEntryUdfpsTouchOverlayViewModel = lazy;
        this.mDefaultUdfpsTouchOverlayViewModel = lazy2;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "UdfpsController", this);
        this.mOrientationListener = new BiometricDisplayListener(context, displayManager, handler, BiometricDisplayListener.SensorType.UnderDisplayFingerprint.INSTANCE, new Function0() { // from class: com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AuthController$$ExternalSyntheticLambda3 authController$$ExternalSyntheticLambda3 = UdfpsController.this.mAuthControllerUpdateUdfpsLocation;
                if (authController$$ExternalSyntheticLambda3 != null) {
                    authController$$ExternalSyntheticLambda3.run();
                }
                return Unit.INSTANCE;
            }
        });
        this.mDeviceEntryFaceAuthInteractor = deviceEntryFaceAuthInteractor;
        UdfpsOverlayController udfpsOverlayController = new UdfpsOverlayController();
        fingerprintManager2.setUdfpsOverlayController(udfpsOverlayController);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.registerReceiver(r4, intentFilter, 2);
        udfpsHapticsSimulator.udfpsController = this;
        udfpsShell.udfpsOverlayController = udfpsOverlayController;
    }

    public void cancelAodSendFingerUpAction() {
        this.mIsAodInterruptActive = false;
        Runnable runnable = this.mCancelAodFingerUpAction;
        if (runnable != null) {
            runnable.run();
            this.mCancelAodFingerUpAction = null;
        }
    }

    public final void dispatchOnUiReady(long j) {
        this.mFingerprintManager.onUdfpsUiEvent(2, j, this.mSensorProps.sensorId);
        this.mLatencyTracker.onActionEnd(14);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        int integer = this.mContext.getResources().getInteger(R.integer.device_idle_sensing_to_ms);
        printWriter.println("mSensorProps=(" + this.mSensorProps + ")");
        MagnificationImpl$$ExternalSyntheticOutline0.m(new StringBuilder("touchConfigId: "), integer, printWriter);
    }

    public final void hideUdfpsOverlay() {
        this.mExecution.assertIsMainThread();
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay != null) {
            if (udfpsControllerOverlay.overlayTouchView != null) {
                onFingerUp(udfpsControllerOverlay.requestId, -1, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, false);
            }
            UdfpsControllerOverlay udfpsControllerOverlay2 = this.mOverlay;
            UdfpsTouchOverlay udfpsTouchOverlay = udfpsControllerOverlay2.overlayTouchView;
            ((UdfpsDisplayMode) udfpsControllerOverlay2.udfpsDisplayModeProvider).disable();
            UdfpsTouchOverlay udfpsTouchOverlay2 = udfpsControllerOverlay2.overlayTouchView;
            if (udfpsTouchOverlay2 != null) {
                if (udfpsTouchOverlay2.getParent() != null) {
                    udfpsControllerOverlay2.windowManager.removeView(udfpsTouchOverlay2);
                }
                Trace.setCounter("UdfpsAddView", 0L);
                udfpsTouchOverlay2.setOnTouchListener(null);
                udfpsTouchOverlay2.setOnHoverListener(null);
                UdfpsControllerOverlay$show$2$1 udfpsControllerOverlay$show$2$1 = udfpsControllerOverlay2.overlayTouchListener;
                if (udfpsControllerOverlay$show$2$1 != null) {
                    udfpsControllerOverlay2.accessibilityManager.removeTouchExplorationStateChangeListener(udfpsControllerOverlay$show$2$1);
                }
            }
            udfpsControllerOverlay2.overlayTouchView = null;
            udfpsControllerOverlay2.overlayTouchListener = null;
            StandaloneCoroutine standaloneCoroutine = udfpsControllerOverlay2.listenForCurrentKeyguardState;
            if (standaloneCoroutine != null) {
                standaloneCoroutine.cancel(null);
            }
            this.mKeyguardViewManager.hideAlternateBouncer(true);
        }
        this.mOverlay = null;
        BiometricDisplayListener biometricDisplayListener = this.mOrientationListener;
        biometricDisplayListener.displayManager.unregisterDisplayListener(biometricDisplayListener);
    }

    public final boolean isOptical() {
        return this.mSensorProps.sensorType == 3;
    }

    public final void onFingerDown(long j, int i, float f, float f2, float f3, float f4, float f5, long j2, long j3, boolean z) {
        this.mExecution.assertIsMainThread();
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay == null) {
            Log.w("UdfpsController", "Null request in onFingerDown");
            return;
        }
        long j4 = udfpsControllerOverlay.requestId;
        if (j4 != -1 && j4 != j) {
            StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("Mismatched fingerDown: ", j, " current: ");
            m.append(this.mOverlay.requestId);
            Log.w("UdfpsController", m.toString());
            return;
        }
        if (isOptical()) {
            this.mLatencyTracker.onActionStart(14);
        }
        this.mPowerManager.userActivity(this.mSystemClock.uptimeMillis(), 2, 0);
        if (!this.mOnFingerDown) {
            playStartHaptic();
            this.mDeviceEntryFaceAuthInteractor.onUdfpsSensorTouched();
        }
        this.mOnFingerDown = true;
        this.mFingerprintManager.onPointerDown(j, this.mSensorProps.sensorId, i, f, f2, f3, f4, f5, j2, j3, z);
        Trace.endAsyncSection("UdfpsController.e2e.onPointerDown", 0);
        if (this.mOverlay.overlayTouchView != null && isOptical()) {
            if (this.mIgnoreRefreshRate) {
                dispatchOnUiReady(j);
            } else {
                UdfpsDisplayMode udfpsDisplayMode = this.mUdfpsDisplayMode;
                udfpsDisplayMode.execution.isMainThread();
                UdfpsLogger udfpsLogger = udfpsDisplayMode.logger;
                udfpsLogger.getClass();
                LogLevel logLevel = LogLevel.VERBOSE;
                LogBuffer logBuffer = udfpsLogger.logBuffer;
                LogBuffer.log$default(logBuffer, "UdfpsDisplayMode", logLevel, "enable");
                if (udfpsDisplayMode.currentRequest != null) {
                    LogBuffer.log$default(logBuffer, "UdfpsDisplayMode", LogLevel.ERROR, "enable | already requested");
                } else {
                    AuthController authController = udfpsDisplayMode.authController;
                    if (authController.mUdfpsRefreshRateRequestCallback == null) {
                        LogBuffer.log$default(logBuffer, "UdfpsDisplayMode", LogLevel.ERROR, "enable | mDisplayManagerCallback is null");
                    } else {
                        Trace.beginSection("UdfpsDisplayMode.enable");
                        Request request = new Request(udfpsDisplayMode.context.getDisplayId());
                        udfpsDisplayMode.currentRequest = request;
                        try {
                            IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback = authController.mUdfpsRefreshRateRequestCallback;
                            iUdfpsRefreshRateRequestCallback.getClass();
                            iUdfpsRefreshRateRequestCallback.onRequestEnabled(request.displayId);
                            LogBuffer.log$default(logBuffer, "UdfpsDisplayMode", logLevel, "enable | requested optimal refresh rate for UDFPS");
                        } catch (RemoteException e) {
                            logBuffer.commit(logBuffer.obtain("UdfpsDisplayMode", LogLevel.ERROR, new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7("enable"), e));
                        }
                        dispatchOnUiReady(j);
                        Trace.endSection();
                    }
                }
            }
        }
        if (isOptical()) {
            Iterator it = ((HashSet) this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onFingerDown();
            }
        }
    }

    public final void onFingerUp(long j, int i, float f, float f2, float f3, float f4, float f5, long j2, long j3, boolean z) {
        UdfpsDisplayMode udfpsDisplayMode;
        this.mExecution.assertIsMainThread();
        this.mActivePointerId = -1;
        if (this.mOnFingerDown) {
            this.mFingerprintManager.onPointerUp(j, this.mSensorProps.sensorId, i, f, f2, f3, f4, f5, j2, j3, z);
            if (isOptical()) {
                Iterator it = ((HashSet) this.mCallbacks).iterator();
                while (it.hasNext()) {
                    ((Callback) it.next()).onFingerUp();
                }
            }
        }
        this.mOnFingerDown = false;
        if (isOptical() && (udfpsDisplayMode = this.mUdfpsDisplayMode) != null) {
            udfpsDisplayMode.disable();
        }
        cancelAodSendFingerUpAction();
    }

    public void playStartHaptic() {
        UdfpsTouchOverlay udfpsTouchOverlay;
        if (this.mAccessibilityManager.isTouchExplorationEnabled()) {
            UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
            if (udfpsControllerOverlay == null || (udfpsTouchOverlay = udfpsControllerOverlay.overlayTouchView) == null) {
                Log.e("UdfpsController", "No haptics played. Could not obtain overlay view to performvibration. Either the controller overlay is null or has no view");
            } else {
                this.mVibrator.getClass();
                udfpsTouchOverlay.performHapticFeedback(6);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v9, types: [android.view.accessibility.AccessibilityManager$TouchExplorationStateChangeListener, com.android.systemui.biometrics.UdfpsControllerOverlay$show$2$1] */
    public final void showUdfpsOverlay(final UdfpsControllerOverlay udfpsControllerOverlay) {
        this.mExecution.assertIsMainThread();
        if (this.mOverlay != null) {
            Log.d("UdfpsController", "showUdfpsOverlay | the overlay is already showing");
            return;
        }
        this.mOverlay = udfpsControllerOverlay;
        int i = udfpsControllerOverlay.requestReason;
        if (i == 4 && !this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning()) {
            Log.d("UdfpsController", "Attempting to showUdfpsOverlay when fingerprint detection isn't running on keyguard. Skip show.");
            return;
        }
        UdfpsOverlayParams udfpsOverlayParams = this.mOverlayParams;
        if (udfpsControllerOverlay.overlayTouchView != null) {
            Log.d("UdfpsControllerOverlay", "showUdfpsOverlay | the overlay is already showing");
            Log.d("UdfpsController", "showUdfpsOverlay | the overlay is already showing");
            return;
        }
        udfpsControllerOverlay.overlayParams = udfpsOverlayParams;
        udfpsControllerOverlay.sensorBounds = new Rect(udfpsOverlayParams.sensorBounds);
        try {
            final UdfpsTouchOverlay udfpsTouchOverlay = (UdfpsTouchOverlay) udfpsControllerOverlay.inflater.inflate(com.android.systemui.R.layout.udfps_touch_overlay, (ViewGroup) null, false);
            int i2 = udfpsControllerOverlay.requestReason;
            if (i2 == 1 || i2 == 2 || i2 == 3) {
                udfpsTouchOverlay.setImportantForAccessibility(2);
            }
            udfpsControllerOverlay.addViewNowOrLater(udfpsTouchOverlay);
            UdfpsOverlayInteractor udfpsOverlayInteractor = udfpsControllerOverlay.udfpsOverlayInteractor;
            if (i2 == 4) {
                UdfpsTouchOverlayBinder.bind(udfpsTouchOverlay, (UdfpsTouchOverlayViewModel) udfpsControllerOverlay.deviceEntryUdfpsTouchOverlayViewModel.get(), udfpsOverlayInteractor);
            } else {
                UdfpsTouchOverlayBinder.bind(udfpsTouchOverlay, (UdfpsTouchOverlayViewModel) udfpsControllerOverlay.defaultUdfpsTouchOverlayViewModel.get(), udfpsOverlayInteractor);
            }
            udfpsControllerOverlay.overlayTouchView = udfpsTouchOverlay;
            if (udfpsTouchOverlay != null) {
                udfpsControllerOverlay.accessibilityManager.isTouchExplorationEnabled();
                ?? r3 = new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$2$1
                    @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
                    public final void onTouchExplorationStateChanged(boolean z) {
                        if (UdfpsControllerOverlay.this.accessibilityManager.isTouchExplorationEnabled()) {
                            View view = udfpsTouchOverlay;
                            final UdfpsControllerOverlay udfpsControllerOverlay2 = UdfpsControllerOverlay.this;
                            view.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$2$1.1
                                @Override // android.view.View.OnHoverListener
                                public final boolean onHover(View view2, MotionEvent motionEvent) {
                                    Function2 function2 = UdfpsControllerOverlay.this.onTouch;
                                    view2.getClass();
                                    motionEvent.getClass();
                                    return ((Boolean) function2.invoke(view2, motionEvent)).booleanValue();
                                }
                            });
                            udfpsTouchOverlay.setOnTouchListener(null);
                            UdfpsControllerOverlay.this.getClass();
                            return;
                        }
                        udfpsTouchOverlay.setOnHoverListener(null);
                        View view2 = udfpsTouchOverlay;
                        final UdfpsControllerOverlay udfpsControllerOverlay3 = UdfpsControllerOverlay.this;
                        view2.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$2$1.2
                            @Override // android.view.View.OnTouchListener
                            public final boolean onTouch(View view3, MotionEvent motionEvent) {
                                Function2 function2 = UdfpsControllerOverlay.this.onTouch;
                                view3.getClass();
                                motionEvent.getClass();
                                return ((Boolean) function2.invoke(view3, motionEvent)).booleanValue();
                            }
                        });
                        UdfpsControllerOverlay.this.getClass();
                    }
                };
                udfpsControllerOverlay.overlayTouchListener = r3;
                udfpsControllerOverlay.accessibilityManager.addTouchExplorationStateChangeListener(r3);
                UdfpsControllerOverlay$show$2$1 udfpsControllerOverlay$show$2$1 = udfpsControllerOverlay.overlayTouchListener;
                if (udfpsControllerOverlay$show$2$1 != null) {
                    udfpsControllerOverlay$show$2$1.onTouchExplorationStateChanged(true);
                }
            }
        } catch (RuntimeException e) {
            Log.e("UdfpsControllerOverlay", "showUdfpsOverlay | failed to add window", e);
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "showUdfpsOverlay | adding window reason=", "UdfpsController");
        this.mOnFingerDown = false;
        this.mAttemptedToDismissKeyguard = false;
        BiometricDisplayListener biometricDisplayListener = this.mOrientationListener;
        Display display = biometricDisplayListener.context.getDisplay();
        if (display != null) {
            display.getDisplayInfo(biometricDisplayListener.cachedDisplayInfo);
        }
        biometricDisplayListener.displayManager.registerDisplayListener(biometricDisplayListener, biometricDisplayListener.handler, 4L);
        FingerprintManager fingerprintManager = this.mFingerprintManager;
        if (fingerprintManager != null) {
            fingerprintManager.onUdfpsUiEvent(1, udfpsControllerOverlay.requestId, this.mSensorProps.sensorId);
        }
    }

    public void tryAodSendFingerUp() {
        if (this.mIsAodInterruptActive) {
            cancelAodSendFingerUpAction();
            UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
            if (udfpsControllerOverlay == null || udfpsControllerOverlay.overlayTouchView == null) {
                return;
            }
            onFingerUp(udfpsControllerOverlay.requestId, -1, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, false);
        }
    }

    @Override // com.android.systemui.doze.DozeReceiver
    public final void dozeTimeTick() {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class UdfpsOverlayController extends IUdfpsOverlayController.Stub {
        public UdfpsOverlayController() {
        }

        public final void hideUdfpsOverlay(int i) {
            UdfpsController.this.mFgExecutor.execute(new UdfpsController$$ExternalSyntheticLambda2(this, 1));
        }

        public final void onAcquired(final int i, final int i2) {
            UdfpsController udfpsController = UdfpsController.this;
            if (udfpsController.mSensorProps.sensorType == 2) {
                DelayableExecutor delayableExecutor = udfpsController.mFgExecutor;
                if (i2 == 7) {
                    delayableExecutor.execute(new UdfpsController$$ExternalSyntheticLambda2(this, 2));
                } else {
                    delayableExecutor.execute(new UdfpsController$$ExternalSyntheticLambda2(this, 3));
                }
            }
            if (BiometricFingerprintConstants.shouldDisableUdfpsDisplayMode(i2)) {
                UdfpsController.this.mFgExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        UdfpsDisplayMode udfpsDisplayMode;
                        UdfpsController.UdfpsOverlayController udfpsOverlayController = UdfpsController.UdfpsOverlayController.this;
                        int i3 = i;
                        int i4 = i2;
                        UdfpsController udfpsController2 = UdfpsController.this;
                        if (udfpsController2.mOverlay != null) {
                            if (udfpsController2.isOptical() && (udfpsDisplayMode = udfpsController2.mUdfpsDisplayMode) != null) {
                                udfpsDisplayMode.disable();
                            }
                            UdfpsController.this.tryAodSendFingerUp();
                            return;
                        }
                        Log.e("UdfpsController", "Null request when onAcquired for sensorId: " + i3 + " acquiredInfo=" + i4);
                    }
                });
            }
        }

        public final void setDebugMessage(int i, String str) {
            UdfpsController.this.mFgExecutor.execute(new UdfpsController$$ExternalSyntheticLambda2(this, 4));
        }

        public final void showUdfpsOverlay(final long j, int i, final int i2, final IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback) {
            UdfpsController.this.mUdfpsOverlayInteractor._requestId.updateState(null, Long.valueOf(j));
            UdfpsController.this.mFgExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    final UdfpsController.UdfpsOverlayController udfpsOverlayController = UdfpsController.UdfpsOverlayController.this;
                    final long j2 = j;
                    int i3 = i2;
                    IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback2 = iUdfpsOverlayControllerCallback;
                    UdfpsController udfpsController = UdfpsController.this;
                    udfpsController.showUdfpsOverlay(new UdfpsControllerOverlay(udfpsController.mContext, udfpsController.mInflater, udfpsController.mWindowManager, udfpsController.mAccessibilityManager, udfpsController.mStatusBarStateController, udfpsController.mKeyguardViewManager, udfpsController.mKeyguardUpdateMonitor, udfpsController.mDialogManager, udfpsController.mDumpManager, udfpsController.mConfigurationController, udfpsController.mKeyguardStateController, udfpsController.mUnlockedScreenOffAnimationController, udfpsController.mUdfpsDisplayMode, j2, i3, iUdfpsOverlayControllerCallback2, new Function2() { // from class: com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda6
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return Boolean.valueOf(UdfpsController.m1019$$Nest$monTouch(UdfpsController.this, j2, (MotionEvent) obj2));
                        }
                    }, udfpsController.mActivityTransitionAnimator, udfpsController.mPrimaryBouncerInteractor, udfpsController.mAlternateBouncerInteractor, udfpsController.mKeyguardTransitionInteractor, udfpsController.mSelectedUserInteractor, udfpsController.mDeviceEntryUdfpsTouchOverlayViewModel, udfpsController.mDefaultUdfpsTouchOverlayViewModel, udfpsController.mShadeInteractor, udfpsController.mUdfpsOverlayInteractor, udfpsController.mPowerInteractor, udfpsController.mScope));
                }
            });
        }

        public final void onEnrollmentHelp(int i) {
        }

        public final void onEnrollmentProgress(int i, int i2) {
        }
    }
}
