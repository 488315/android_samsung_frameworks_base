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
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.util.LatencyTracker;
import com.android.internal.util.Preconditions;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
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
import com.android.systemui.deviceentry.shared.DeviceEntryUdfpsRefactor;
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
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
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
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

public final class UdfpsController implements DozeReceiver, Dumpable {
    public final AccessibilityManager mAccessibilityManager;
    public final ActivityTransitionAnimator mActivityTransitionAnimator;
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public Runnable mAodInterruptRunnable;
    public boolean mAttemptedToDismissKeyguard;
    public Runnable mAuthControllerUpdateUdfpsLocation;
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
    public final LockscreenShadeTransitionController mLockscreenShadeTransitionController;
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
    public UdfpsDisplayModeProvider mUdfpsDisplayMode;
    public final UdfpsKeyguardAccessibilityDelegate mUdfpsKeyguardAccessibilityDelegate;
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
            try {
                $SwitchMap$com$android$systemui$biometrics$udfps$InteractionEvent[InteractionEvent.UNCHANGED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public interface Callback {
        void onFingerDown();

        void onFingerUp();
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x0510  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x052f  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0531 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x03f1  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0454  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x03f6  */
    /* renamed from: -$$Nest$monTouch, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean m891$$Nest$monTouch(com.android.systemui.biometrics.UdfpsController r32, long r33, android.view.MotionEvent r35, boolean r36) {
        /*
            Method dump skipped, instructions count: 1358
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.UdfpsController.m891$$Nest$monTouch(com.android.systemui.biometrics.UdfpsController, long, android.view.MotionEvent, boolean):boolean");
    }

    static {
        VibrationEffect.get(0);
    }

    public UdfpsController(Context context, Execution execution, LayoutInflater layoutInflater, FingerprintManager fingerprintManager, WindowManager windowManager, StatusBarStateController statusBarStateController, DelayableExecutor delayableExecutor, StatusBarKeyguardViewManager statusBarKeyguardViewManager, DumpManager dumpManager, KeyguardUpdateMonitor keyguardUpdateMonitor, FalsingManager falsingManager, PowerManager powerManager, AccessibilityManager accessibilityManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ScreenLifecycle screenLifecycle, VibratorHelper vibratorHelper, UdfpsHapticsSimulator udfpsHapticsSimulator, UdfpsShell udfpsShell, KeyguardStateController keyguardStateController, DisplayManager displayManager, Handler handler, ConfigurationController configurationController, SystemClock systemClock, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, SystemUIDialogManager systemUIDialogManager, LatencyTracker latencyTracker, ActivityTransitionAnimator activityTransitionAnimator, Executor executor, PrimaryBouncerInteractor primaryBouncerInteractor, ShadeInteractor shadeInteractor, SinglePointerTouchProcessor singlePointerTouchProcessor, SessionTracker sessionTracker, AlternateBouncerInteractor alternateBouncerInteractor, InputManager inputManager, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, UdfpsKeyguardAccessibilityDelegate udfpsKeyguardAccessibilityDelegate, SelectedUserInteractor selectedUserInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, Lazy lazy, Lazy lazy2, UdfpsOverlayInteractor udfpsOverlayInteractor, PowerInteractor powerInteractor, CoroutineScope coroutineScope) {
        ScreenLifecycle.Observer observer = new ScreenLifecycle.Observer() { // from class: com.android.systemui.biometrics.UdfpsController.1
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
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.biometrics.UdfpsController.2
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
                RecyclerView$$ExternalSyntheticOutline0.m(UdfpsController.this.mOverlay.requestReason, "UdfpsController", ActivityResultRegistry$$ExternalSyntheticOutline0.m("ACTION_CLOSE_SYSTEM_DIALOGS received, reason: ", stringExtra, ", mRequestReason: "));
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
        this.mContext = context;
        this.mExecution = execution;
        this.mVibrator = vibratorHelper;
        this.mInflater = layoutInflater;
        this.mIgnoreRefreshRate = context.getResources().getBoolean(R.bool.config_keepDreamingWhenUnplugging);
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
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        screenLifecycle.addObserver(observer);
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
        this.mUdfpsKeyguardAccessibilityDelegate = udfpsKeyguardAccessibilityDelegate;
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
                Runnable runnable = UdfpsController.this.mAuthControllerUpdateUdfpsLocation;
                if (runnable != null) {
                    runnable.run();
                }
                return Unit.INSTANCE;
            }
        });
        this.mDeviceEntryFaceAuthInteractor = deviceEntryFaceAuthInteractor;
        UdfpsOverlayController udfpsOverlayController = new UdfpsOverlayController();
        fingerprintManager2.setUdfpsOverlayController(udfpsOverlayController);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.registerReceiver(broadcastReceiver, intentFilter, 2);
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

    @Override // com.android.systemui.doze.DozeReceiver
    public final void dozeTimeTick() {
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay != null) {
            Flags.deviceEntryUdfpsRefactor();
            if (udfpsControllerOverlay.overlayTouchView instanceof UdfpsView) {
                DeviceEntryUdfpsRefactor.assertInLegacyMode();
                throw null;
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        int integer = this.mContext.getResources().getInteger(R.integer.default_data_warning_level_mb);
        printWriter.println("mSensorProps=(" + this.mSensorProps + ")");
        StringBuilder sb = new StringBuilder("touchConfigId: ");
        sb.append(integer);
        printWriter.println(sb.toString());
    }

    public final void hideUdfpsOverlay() {
        this.mExecution.assertIsMainThread();
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay != null) {
            Flags.deviceEntryUdfpsRefactor();
            if (udfpsControllerOverlay.overlayTouchView != null) {
                onFingerUp(this.mOverlay.requestId, -1, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, false);
            }
            UdfpsControllerOverlay udfpsControllerOverlay2 = this.mOverlay;
            udfpsControllerOverlay2.getClass();
            Flags.deviceEntryUdfpsRefactor();
            Flags.deviceEntryUdfpsRefactor();
            ((UdfpsDisplayMode) udfpsControllerOverlay2.udfpsDisplayModeProvider).disable();
            Flags.deviceEntryUdfpsRefactor();
            UdfpsTouchOverlay udfpsTouchOverlay = udfpsControllerOverlay2.overlayTouchView;
            if (udfpsTouchOverlay != null) {
                Flags.FEATURE_FLAGS.getClass();
                if (udfpsTouchOverlay.getParent() != null) {
                    udfpsControllerOverlay2.windowManager.removeView(udfpsTouchOverlay);
                }
                Trace.setCounter("UdfpsAddView", 0L);
                udfpsTouchOverlay.setOnTouchListener(null);
                udfpsTouchOverlay.setOnHoverListener(null);
                UdfpsControllerOverlay$show$3$1 udfpsControllerOverlay$show$3$1 = udfpsControllerOverlay2.overlayTouchListener;
                if (udfpsControllerOverlay$show$3$1 != null) {
                    udfpsControllerOverlay2.accessibilityManager.removeTouchExplorationStateChangeListener(udfpsControllerOverlay$show$3$1);
                }
            }
            udfpsControllerOverlay2.overlayTouchView = null;
            udfpsControllerOverlay2.overlayTouchListener = null;
            Job job = udfpsControllerOverlay2.listenForCurrentKeyguardState;
            if (job != null) {
                job.cancel(null);
            }
            this.mKeyguardViewManager.hideAlternateBouncer(true);
        }
        this.mOverlay = null;
        BiometricDisplayListener biometricDisplayListener = this.mOrientationListener;
        biometricDisplayListener.displayManager.unregisterDisplayListener(biometricDisplayListener);
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
        if (this.mSensorProps.sensorType == 3) {
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
        UdfpsControllerOverlay udfpsControllerOverlay2 = this.mOverlay;
        udfpsControllerOverlay2.getClass();
        Flags.deviceEntryUdfpsRefactor();
        if (udfpsControllerOverlay2.overlayTouchView != null && this.mSensorProps.sensorType == 3) {
            if (this.mIgnoreRefreshRate) {
                dispatchOnUiReady(j);
            } else {
                DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
                Flags.deviceEntryUdfpsRefactor();
                UdfpsDisplayMode udfpsDisplayMode = (UdfpsDisplayMode) this.mUdfpsDisplayMode;
                udfpsDisplayMode.execution.isMainThread();
                UdfpsLogger udfpsLogger = udfpsDisplayMode.logger;
                udfpsLogger.getClass();
                LogLevel logLevel = LogLevel.VERBOSE;
                LogBuffer logBuffer = udfpsLogger.logBuffer;
                logBuffer.log("UdfpsDisplayMode", logLevel, "enable", null);
                if (udfpsDisplayMode.currentRequest != null) {
                    logBuffer.log("UdfpsDisplayMode", LogLevel.ERROR, "enable | already requested", null);
                } else {
                    AuthController authController = udfpsDisplayMode.authController;
                    if (authController.mUdfpsRefreshRateRequestCallback == null) {
                        logBuffer.log("UdfpsDisplayMode", LogLevel.ERROR, "enable | mDisplayManagerCallback is null", null);
                    } else {
                        Trace.beginSection("UdfpsDisplayMode.enable");
                        Request request = new Request(udfpsDisplayMode.context.getDisplayId());
                        udfpsDisplayMode.currentRequest = request;
                        try {
                            IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback = authController.mUdfpsRefreshRateRequestCallback;
                            Intrinsics.checkNotNull(iUdfpsRefreshRateRequestCallback);
                            iUdfpsRefreshRateRequestCallback.onRequestEnabled(request.displayId);
                            logBuffer.log("UdfpsDisplayMode", logLevel, "enable | requested optimal refresh rate for UDFPS", null);
                        } catch (RemoteException e) {
                            logBuffer.commit(logBuffer.obtain("UdfpsDisplayMode", LogLevel.ERROR, new UdfpsLogger$e$2("enable"), e));
                        }
                        dispatchOnUiReady(j);
                        if (Unit.INSTANCE == null) {
                            logBuffer.log("UdfpsDisplayMode", LogLevel.WARNING, "enable | onEnabled is null", null);
                        }
                        Trace.endSection();
                    }
                }
            }
        }
        Iterator it = ((HashSet) this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onFingerDown();
        }
    }

    public final void onFingerUp(long j, int i, float f, float f2, float f3, float f4, float f5, long j2, long j3, boolean z) {
        this.mExecution.assertIsMainThread();
        this.mActivePointerId = -1;
        if (this.mOnFingerDown) {
            this.mFingerprintManager.onPointerUp(j, this.mSensorProps.sensorId, i, f, f2, f3, f4, f5, j2, j3, z);
            Iterator it = ((HashSet) this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onFingerUp();
            }
        }
        this.mOnFingerDown = false;
        if (this.mSensorProps.sensorType == 3) {
            DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
            Flags.deviceEntryUdfpsRefactor();
            UdfpsDisplayModeProvider udfpsDisplayModeProvider = this.mUdfpsDisplayMode;
            if (udfpsDisplayModeProvider != null) {
                ((UdfpsDisplayMode) udfpsDisplayModeProvider).disable();
            }
        }
        cancelAodSendFingerUpAction();
    }

    public void playStartHaptic() {
        if (this.mAccessibilityManager.isTouchExplorationEnabled()) {
            UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
            if (udfpsControllerOverlay != null) {
                Flags.deviceEntryUdfpsRefactor();
                if (udfpsControllerOverlay.overlayTouchView != null) {
                    UdfpsControllerOverlay udfpsControllerOverlay2 = this.mOverlay;
                    udfpsControllerOverlay2.getClass();
                    Flags.deviceEntryUdfpsRefactor();
                    UdfpsTouchOverlay udfpsTouchOverlay = udfpsControllerOverlay2.overlayTouchView;
                    this.mVibrator.getClass();
                    udfpsTouchOverlay.performHapticFeedback(6);
                    return;
                }
            }
            Log.e("UdfpsController", "No haptics played. Could not obtain overlay view to performvibration. Either the controller overlay is null or has no view");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v7, types: [android.view.accessibility.AccessibilityManager$TouchExplorationStateChangeListener, com.android.systemui.biometrics.UdfpsControllerOverlay$show$3$1] */
    public final void showUdfpsOverlay(final UdfpsControllerOverlay udfpsControllerOverlay) {
        this.mExecution.assertIsMainThread();
        this.mOverlay = udfpsControllerOverlay;
        if (udfpsControllerOverlay.requestReason == 4 && !this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning()) {
            Log.d("UdfpsController", "Attempting to showUdfpsOverlay when fingerprint detection isn't running on keyguard. Skip show.");
            return;
        }
        UdfpsOverlayParams udfpsOverlayParams = this.mOverlayParams;
        Flags.deviceEntryUdfpsRefactor();
        if (udfpsControllerOverlay.overlayTouchView == null) {
            udfpsControllerOverlay.overlayParams = udfpsOverlayParams;
            udfpsControllerOverlay.sensorBounds = new Rect(udfpsOverlayParams.sensorBounds);
            try {
                Flags.deviceEntryUdfpsRefactor();
                UdfpsTouchOverlay udfpsTouchOverlay = (UdfpsTouchOverlay) udfpsControllerOverlay.inflater.inflate(com.android.systemui.R.layout.udfps_touch_overlay, (ViewGroup) null, false);
                int i = udfpsControllerOverlay.requestReason;
                if (i == 1 || i == 2 || i == 3) {
                    udfpsTouchOverlay.setImportantForAccessibility(2);
                }
                Flags.FEATURE_FLAGS.getClass();
                udfpsControllerOverlay.addViewRunnable = new UdfpsControllerOverlay$addViewNowOrLater$$inlined$Runnable$1(udfpsControllerOverlay, udfpsTouchOverlay, null);
                if (((WakefulnessModel) udfpsControllerOverlay.powerInteractor.detailedWakefulness.$$delegate_0.getValue()).isAwake()) {
                    UdfpsControllerOverlay$addViewNowOrLater$$inlined$Runnable$1 udfpsControllerOverlay$addViewNowOrLater$$inlined$Runnable$1 = udfpsControllerOverlay.addViewRunnable;
                    if (udfpsControllerOverlay$addViewNowOrLater$$inlined$Runnable$1 != null) {
                        Job job = udfpsControllerOverlay.listenForCurrentKeyguardState;
                        if (job != null) {
                            job.cancel(null);
                        }
                        udfpsControllerOverlay$addViewNowOrLater$$inlined$Runnable$1.run();
                    }
                    udfpsControllerOverlay.addViewRunnable = null;
                } else {
                    Job job2 = udfpsControllerOverlay.listenForCurrentKeyguardState;
                    if (job2 != null) {
                        job2.cancel(null);
                    }
                    udfpsControllerOverlay.listenForCurrentKeyguardState = BuildersKt.launch$default(udfpsControllerOverlay.scope, null, null, new UdfpsControllerOverlay$addViewNowOrLater$2(udfpsControllerOverlay, null), 3);
                }
                UdfpsOverlayInteractor udfpsOverlayInteractor = udfpsControllerOverlay.udfpsOverlayInteractor;
                if (i == 4) {
                    UdfpsTouchOverlayBinder.bind(udfpsTouchOverlay, (UdfpsTouchOverlayViewModel) udfpsControllerOverlay.deviceEntryUdfpsTouchOverlayViewModel.get(), udfpsOverlayInteractor);
                } else {
                    UdfpsTouchOverlayBinder.bind(udfpsTouchOverlay, (UdfpsTouchOverlayViewModel) udfpsControllerOverlay.defaultUdfpsTouchOverlayViewModel.get(), udfpsOverlayInteractor);
                }
                udfpsControllerOverlay.overlayTouchView = udfpsTouchOverlay;
                Flags.deviceEntryUdfpsRefactor();
                final UdfpsTouchOverlay udfpsTouchOverlay2 = udfpsControllerOverlay.overlayTouchView;
                if (udfpsTouchOverlay2 != null) {
                    udfpsControllerOverlay.accessibilityManager.isTouchExplorationEnabled();
                    ?? r3 = new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$3$1
                        @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
                        public final void onTouchExplorationStateChanged(boolean z) {
                            if (UdfpsControllerOverlay.this.accessibilityManager.isTouchExplorationEnabled()) {
                                View view = udfpsTouchOverlay2;
                                final UdfpsControllerOverlay udfpsControllerOverlay2 = UdfpsControllerOverlay.this;
                                view.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$3$1.1
                                    @Override // android.view.View.OnHoverListener
                                    public final boolean onHover(View view2, MotionEvent motionEvent) {
                                        Function3 function3 = UdfpsControllerOverlay.this.onTouch;
                                        Intrinsics.checkNotNull(view2);
                                        Intrinsics.checkNotNull(motionEvent);
                                        return ((Boolean) function3.invoke(view2, motionEvent, Boolean.TRUE)).booleanValue();
                                    }
                                });
                                udfpsTouchOverlay2.setOnTouchListener(null);
                                UdfpsControllerOverlay.this.getClass();
                                return;
                            }
                            udfpsTouchOverlay2.setOnHoverListener(null);
                            View view2 = udfpsTouchOverlay2;
                            final UdfpsControllerOverlay udfpsControllerOverlay3 = UdfpsControllerOverlay.this;
                            view2.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$3$1.2
                                @Override // android.view.View.OnTouchListener
                                public final boolean onTouch(View view3, MotionEvent motionEvent) {
                                    Function3 function3 = UdfpsControllerOverlay.this.onTouch;
                                    Intrinsics.checkNotNull(view3);
                                    Intrinsics.checkNotNull(motionEvent);
                                    return ((Boolean) function3.invoke(view3, motionEvent, Boolean.TRUE)).booleanValue();
                                }
                            });
                            UdfpsControllerOverlay.this.getClass();
                        }
                    };
                    udfpsControllerOverlay.overlayTouchListener = r3;
                    udfpsControllerOverlay.accessibilityManager.addTouchExplorationStateChangeListener(r3);
                    UdfpsControllerOverlay$show$3$1 udfpsControllerOverlay$show$3$1 = udfpsControllerOverlay.overlayTouchListener;
                    if (udfpsControllerOverlay$show$3$1 != null) {
                        udfpsControllerOverlay$show$3$1.onTouchExplorationStateChanged(true);
                    }
                }
            } catch (RuntimeException e) {
                Log.e("UdfpsControllerOverlay", "showUdfpsOverlay | failed to add window", e);
            }
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
    }

    public void tryAodSendFingerUp() {
        if (this.mIsAodInterruptActive) {
            cancelAodSendFingerUpAction();
            UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
            if (udfpsControllerOverlay != null) {
                Flags.deviceEntryUdfpsRefactor();
                if (udfpsControllerOverlay.overlayTouchView != null) {
                    long j = this.mOverlay.requestId;
                    Flags.deviceEntryUdfpsRefactor();
                    onFingerUp(j, -1, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, false);
                }
            }
        }
    }

    public final class UdfpsOverlayController extends IUdfpsOverlayController.Stub {
        public UdfpsOverlayController() {
        }

        public final void hideUdfpsOverlay(int i) {
            UdfpsController.this.mFgExecutor.execute(new UdfpsController$$ExternalSyntheticLambda2(this, 1));
        }

        public final void onAcquired(final int i, final int i2) {
            if (BiometricFingerprintConstants.shouldDisableUdfpsDisplayMode(i2)) {
                UdfpsController.this.mFgExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        UdfpsController.UdfpsOverlayController udfpsOverlayController = UdfpsController.UdfpsOverlayController.this;
                        int i3 = i;
                        int i4 = i2;
                        if (UdfpsController.this.mOverlay == null) {
                            Log.e("UdfpsController", "Null request when onAcquired for sensorId: " + i3 + " acquiredInfo=" + i4);
                            return;
                        }
                        Flags.deviceEntryUdfpsRefactor();
                        UdfpsController udfpsController = UdfpsController.this;
                        if (udfpsController.mSensorProps.sensorType == 3) {
                            DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
                            Flags.deviceEntryUdfpsRefactor();
                            UdfpsDisplayModeProvider udfpsDisplayModeProvider = udfpsController.mUdfpsDisplayMode;
                            if (udfpsDisplayModeProvider != null) {
                                ((UdfpsDisplayMode) udfpsDisplayModeProvider).disable();
                            }
                        }
                        UdfpsController.this.tryAodSendFingerUp();
                    }
                });
            }
        }

        public final void setDebugMessage(int i, String str) {
            UdfpsController.this.mFgExecutor.execute(new UdfpsController$$ExternalSyntheticLambda2(this, str));
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
                    udfpsController.showUdfpsOverlay(new UdfpsControllerOverlay(udfpsController.mContext, udfpsController.mInflater, udfpsController.mWindowManager, udfpsController.mAccessibilityManager, udfpsController.mStatusBarStateController, udfpsController.mKeyguardViewManager, udfpsController.mKeyguardUpdateMonitor, udfpsController.mDialogManager, udfpsController.mDumpManager, udfpsController.mLockscreenShadeTransitionController, udfpsController.mConfigurationController, udfpsController.mKeyguardStateController, udfpsController.mUnlockedScreenOffAnimationController, udfpsController.mUdfpsDisplayMode, j2, i3, iUdfpsOverlayControllerCallback2, new Function3() { // from class: com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda4
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            UdfpsController udfpsController2 = UdfpsController.this;
                            boolean booleanValue = ((Boolean) obj3).booleanValue();
                            return Boolean.valueOf(UdfpsController.m891$$Nest$monTouch(udfpsController2, j2, (MotionEvent) obj2, booleanValue));
                        }
                    }, udfpsController.mActivityTransitionAnimator, udfpsController.mPrimaryBouncerInteractor, udfpsController.mAlternateBouncerInteractor, udfpsController.mUdfpsKeyguardAccessibilityDelegate, udfpsController.mKeyguardTransitionInteractor, udfpsController.mSelectedUserInteractor, udfpsController.mDeviceEntryUdfpsTouchOverlayViewModel, udfpsController.mDefaultUdfpsTouchOverlayViewModel, udfpsController.mShadeInteractor, udfpsController.mUdfpsOverlayInteractor, udfpsController.mPowerInteractor, udfpsController.mScope));
                }
            });
        }

        public final void onEnrollmentHelp(int i) {
        }

        public final void onEnrollmentProgress(int i, int i2) {
        }
    }
}
