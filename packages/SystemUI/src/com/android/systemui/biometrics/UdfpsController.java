package com.android.systemui.biometrics;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
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
import android.os.RemoteException;
import android.os.Trace;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.util.Log;
import android.util.StatsEvent;
import android.util.StatsLog;
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
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
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
import com.android.systemui.biometrics.udfps.NormalizedTouchData;
import com.android.systemui.biometrics.udfps.PreprocessedTouch;
import com.android.systemui.biometrics.udfps.SinglePointerTouchProcessor;
import com.android.systemui.biometrics.udfps.SinglePointerTouchProcessorKt;
import com.android.systemui.biometrics.udfps.TouchProcessorResult;
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
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
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
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.text.StringsKt__IndentKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes.dex */
public class UdfpsController implements DozeReceiver, Dumpable {
    public final AccessibilityManager mAccessibilityManager;
    public UdfpsController$$ExternalSyntheticLambda1 mAodInterruptRunnable;
    public boolean mAttemptedToDismissKeyguard;
    public AuthController$$ExternalSyntheticLambda3 mAuthControllerUpdateUdfpsLocation;
    public final Executor mBiometricExecutor;
    public final AnonymousClass2 mBroadcastReceiver;
    public Runnable mCancelAodFingerUpAction;
    public final Context mContext;
    public final Lazy mDefaultUdfpsTouchOverlayViewModel;
    public final DeviceEntryFaceAuthInteractor mDeviceEntryFaceAuthInteractor;
    public final Lazy mDeviceEntryUdfpsTouchOverlayViewModel;
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
    public final Lazy mPromptUdfpsTouchOverlayViewModel;
    public final CoroutineScope mScope;
    public final AnonymousClass1 mScreenObserver;
    public boolean mScreenOn;
    FingerprintSensorPropertiesInternal mSensorProps;
    public final SessionTracker mSessionTracker;
    public final StatusBarStateController mStatusBarStateController;
    public final SystemClock mSystemClock;
    public final SinglePointerTouchProcessor mTouchProcessor;
    public UdfpsDisplayMode mUdfpsDisplayMode;
    public final UdfpsOverlayInteractor mUdfpsOverlayInteractor;
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
        }
    }

    public interface Callback {
        void onFingerDown();

        void onFingerUp();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0384  */
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
    */
    public static boolean m1021$$Nest$monTouch(UdfpsController udfpsController, long j, MotionEvent motionEvent) throws Resources.NotFoundException {
        TouchProcessorResult processedTouch;
        Object next;
        InteractionEvent interactionEvent;
        StatusBarStateController statusBarStateController;
        boolean z;
        boolean z2;
        NormalizedTouchData normalizedTouchData;
        boolean z3;
        boolean z4;
        int i;
        int i2;
        ?? r1;
        char c;
        boolean z5;
        NormalizedTouchData normalizedTouchData2;
        UdfpsControllerOverlay udfpsControllerOverlay = udfpsController.mOverlay;
        if (udfpsControllerOverlay == null) {
            Log.w("UdfpsController", "ignoring onTouch with null overlay");
            return false;
        }
        long j2 = udfpsControllerOverlay.requestId;
        if (j2 != -1 && j2 != j) {
            StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("ignoring stale touch event: ", j, " current: ");
            sbM.append(udfpsController.mOverlay.requestId);
            Log.w("UdfpsController", sbM.toString());
            return false;
        }
        if (motionEvent.getAction() == 0 || motionEvent.getAction() == 9) {
            udfpsController.mPointerPilfered = false;
            if (udfpsController.mActivePointerId != -1) {
                Log.w("UdfpsController", "onTouch down received without a preceding up");
            }
            udfpsController.mActivePointerId = -1;
            if (!udfpsController.mIsAodInterruptActive) {
                udfpsController.mOnFingerDown = false;
            }
        }
        int i3 = udfpsController.mActivePointerId;
        UdfpsOverlayParams udfpsOverlayParams = udfpsController.mOverlayParams;
        SinglePointerTouchProcessor singlePointerTouchProcessor = udfpsController.mTouchProcessor;
        singlePointerTouchProcessor.getClass();
        NormalizedTouchData normalizedTouchData3 = null;
        switch (motionEvent.getActionMasked()) {
            case 0:
            case 2:
            case 5:
            case 7:
            case 9:
                PreprocessedTouch preprocessedTouchProcessTouch$preprocess = SinglePointerTouchProcessor.processTouch$preprocess(motionEvent, i3, udfpsOverlayParams, singlePointerTouchProcessor);
                Set set = SinglePointerTouchProcessorKt.SUPPORTED_ROTATIONS;
                int i4 = preprocessedTouchProcessTouch$preprocess.previousPointerOnSensorId;
                boolean z6 = i4 != -1;
                boolean zIsEmpty = preprocessedTouchProcessTouch$preprocess.pointersOnSensor.isEmpty();
                Integer num = (Integer) CollectionsKt___CollectionsKt.firstOrNull(preprocessedTouchProcessTouch$preprocess.pointersOnSensor);
                int iIntValue = num != null ? num.intValue() : -1;
                if (!z6 && !zIsEmpty) {
                    Iterator it = preprocessedTouchProcessTouch$preprocess.data.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            ?? next2 = it.next();
                            if (((NormalizedTouchData) next2).pointerId == iIntValue) {
                                normalizedTouchData3 = next2;
                            }
                        }
                    }
                    NormalizedTouchData normalizedTouchData4 = normalizedTouchData3;
                    if (normalizedTouchData4 == null) {
                        normalizedTouchData4 = new NormalizedTouchData(0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, 255, null);
                    }
                    processedTouch = new TouchProcessorResult.ProcessedTouch(InteractionEvent.DOWN, normalizedTouchData4.pointerId, normalizedTouchData4);
                    break;
                } else if (!z6 || !zIsEmpty) {
                    Iterator it2 = preprocessedTouchProcessTouch$preprocess.data.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            ?? next3 = it2.next();
                            if (((NormalizedTouchData) next3).pointerId == iIntValue) {
                                normalizedTouchData3 = next3;
                            }
                        }
                    }
                    NormalizedTouchData normalizedTouchData5 = normalizedTouchData3;
                    if (normalizedTouchData5 == null && (normalizedTouchData5 = (NormalizedTouchData) CollectionsKt___CollectionsKt.firstOrNull(preprocessedTouchProcessTouch$preprocess.data)) == null) {
                        normalizedTouchData5 = new NormalizedTouchData(0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, 255, null);
                    }
                    processedTouch = new TouchProcessorResult.ProcessedTouch(InteractionEvent.UNCHANGED, iIntValue, normalizedTouchData5);
                    break;
                } else {
                    Iterator it3 = preprocessedTouchProcessTouch$preprocess.data.iterator();
                    while (true) {
                        if (it3.hasNext()) {
                            ?? next4 = it3.next();
                            if (((NormalizedTouchData) next4).pointerId == i4) {
                                normalizedTouchData3 = next4;
                            }
                        }
                    }
                    NormalizedTouchData normalizedTouchData6 = normalizedTouchData3;
                    if (normalizedTouchData6 == null) {
                        normalizedTouchData6 = new NormalizedTouchData(0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, 255, null);
                    }
                    processedTouch = new TouchProcessorResult.ProcessedTouch(InteractionEvent.UP, -1, normalizedTouchData6);
                    break;
                }
                break;
            case 1:
            case 6:
            case 10:
                PreprocessedTouch preprocessedTouchProcessTouch$preprocess2 = SinglePointerTouchProcessor.processTouch$preprocess(motionEvent, i3, udfpsOverlayParams, singlePointerTouchProcessor);
                int pointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                Set set2 = SinglePointerTouchProcessorKt.SUPPORTED_ROTATIONS;
                if (preprocessedTouchProcessTouch$preprocess2.pointersOnSensor.size() != 1 || !preprocessedTouchProcessTouch$preprocess2.pointersOnSensor.contains(Integer.valueOf(pointerId))) {
                    Iterator it4 = preprocessedTouchProcessTouch$preprocess2.pointersOnSensor.iterator();
                    while (true) {
                        if (it4.hasNext()) {
                            next = it4.next();
                            if (((Number) next).intValue() != pointerId) {
                            }
                        } else {
                            next = null;
                        }
                    }
                    Integer num2 = (Integer) next;
                    int iIntValue2 = num2 != null ? num2.intValue() : -1;
                    Iterator it5 = preprocessedTouchProcessTouch$preprocess2.data.iterator();
                    while (true) {
                        if (it5.hasNext()) {
                            ?? next5 = it5.next();
                            if (((NormalizedTouchData) next5).pointerId == iIntValue2) {
                                normalizedTouchData3 = next5;
                            }
                        }
                    }
                    NormalizedTouchData normalizedTouchData7 = normalizedTouchData3;
                    if (normalizedTouchData7 == null && (normalizedTouchData7 = (NormalizedTouchData) CollectionsKt___CollectionsKt.firstOrNull(preprocessedTouchProcessTouch$preprocess2.data)) == null) {
                        normalizedTouchData7 = new NormalizedTouchData(0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, 255, null);
                    }
                    processedTouch = new TouchProcessorResult.ProcessedTouch(InteractionEvent.UNCHANGED, iIntValue2, normalizedTouchData7);
                    break;
                } else {
                    Iterator it6 = preprocessedTouchProcessTouch$preprocess2.data.iterator();
                    while (true) {
                        if (it6.hasNext()) {
                            ?? next6 = it6.next();
                            if (((NormalizedTouchData) next6).pointerId == pointerId) {
                                normalizedTouchData3 = next6;
                            }
                        }
                    }
                    NormalizedTouchData normalizedTouchData8 = normalizedTouchData3;
                    if (normalizedTouchData8 == null) {
                        normalizedTouchData8 = new NormalizedTouchData(0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, 255, null);
                    }
                    processedTouch = new TouchProcessorResult.ProcessedTouch(InteractionEvent.UP, -1, normalizedTouchData8);
                    break;
                }
                break;
            case 3:
                NormalizedTouchData normalizedTouchData9 = new NormalizedTouchData(0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, 255, null);
                Set set3 = SinglePointerTouchProcessorKt.SUPPORTED_ROTATIONS;
                processedTouch = new TouchProcessorResult.ProcessedTouch(InteractionEvent.CANCEL, -1, normalizedTouchData9);
                break;
            case 4:
            case 8:
            default:
                processedTouch = new TouchProcessorResult.Failure(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Unsupported MotionEvent.", MotionEvent.actionToString(motionEvent.getActionMasked())));
                break;
        }
        if (processedTouch instanceof TouchProcessorResult.Failure) {
            Log.w("UdfpsController", ((TouchProcessorResult.Failure) processedTouch).reason);
            return false;
        }
        TouchProcessorResult.ProcessedTouch processedTouch2 = (TouchProcessorResult.ProcessedTouch) processedTouch;
        udfpsController.mActivePointerId = processedTouch2.pointerOnSensorId;
        int[] iArr = AnonymousClass3.$SwitchMap$com$android$systemui$biometrics$udfps$InteractionEvent;
        InteractionEvent interactionEvent2 = processedTouch2.event;
        int i5 = iArr[interactionEvent2.ordinal()];
        StatusBarStateController statusBarStateController2 = udfpsController.mStatusBarStateController;
        NormalizedTouchData normalizedTouchData10 = processedTouch2.touchData;
        if (i5 != 1) {
            if (i5 == 2 || i5 == 3) {
                if (InteractionEvent.CANCEL.equals(interactionEvent2)) {
                    Log.w("UdfpsController", "This is a CANCEL event that's reported as an UP event!");
                }
                udfpsController.mAttemptedToDismissKeyguard = false;
                UdfpsTouchOverlay udfpsTouchOverlay = udfpsController.mOverlay.overlayTouchView;
                statusBarStateController = statusBarStateController2;
                normalizedTouchData2 = normalizedTouchData10;
                interactionEvent = interactionEvent2;
                udfpsController.onFingerUp(j, normalizedTouchData10.pointerId, normalizedTouchData10.x, normalizedTouchData10.y, normalizedTouchData10.minor, normalizedTouchData10.major, normalizedTouchData10.orientation, normalizedTouchData10.time, normalizedTouchData10.gestureStart, statusBarStateController2.isDozing());
            } else {
                interactionEvent = interactionEvent2;
                normalizedTouchData2 = normalizedTouchData10;
                statusBarStateController = statusBarStateController2;
            }
            z4 = false;
            z2 = false;
            normalizedTouchData = normalizedTouchData2;
            z3 = true;
        } else {
            interactionEvent = interactionEvent2;
            statusBarStateController = statusBarStateController2;
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) udfpsController.mKeyguardStateController;
            if (keyguardStateControllerImpl.mShowing && keyguardStateControllerImpl.mCanDismissLockScreen && !udfpsController.mAttemptedToDismissKeyguard) {
                if (!udfpsController.mOnFingerDown) {
                    udfpsController.playStartHaptic();
                }
                udfpsController.mKeyguardViewManager.notifyKeyguardAuthenticated(false);
                z = true;
                udfpsController.mAttemptedToDismissKeyguard = true;
            } else {
                z = true;
            }
            if (udfpsController.mOnFingerDown) {
                z2 = false;
                normalizedTouchData = normalizedTouchData10;
                z3 = z;
            } else {
                normalizedTouchData = normalizedTouchData10;
                z2 = false;
                z3 = z;
                udfpsController.onFingerDown(j, normalizedTouchData10.pointerId, normalizedTouchData10.x, normalizedTouchData10.y, normalizedTouchData10.minor, normalizedTouchData10.major, normalizedTouchData10.orientation, normalizedTouchData10.time, normalizedTouchData10.gestureStart, statusBarStateController.isDozing());
            }
            udfpsController.mFalsingManager.isFalseTouch(13);
            z4 = z3;
        }
        InteractionEvent interactionEvent3 = InteractionEvent.UNCHANGED;
        SystemClock systemClock = udfpsController.mSystemClock;
        InteractionEvent interactionEvent4 = interactionEvent;
        if (interactionEvent4 != interactionEvent3 || systemClock.elapsedRealtime() - udfpsController.mLastTouchInteractionTime >= 50) {
            udfpsController.mLastTouchInteractionTime = systemClock.elapsedRealtime();
            int i6 = iArr[interactionEvent4.ordinal()];
            if (i6 != z3) {
                i = 2;
                if (i6 != 2) {
                    i2 = 3;
                    r1 = i6 != 3 ? z2 : 3;
                } else {
                    i2 = 3;
                    r1 = 2;
                }
            } else {
                i = 2;
                i2 = 3;
                r1 = z3;
            }
            UdfpsControllerOverlay udfpsControllerOverlay2 = udfpsController.mOverlay;
            if (udfpsControllerOverlay2 != null) {
                int i7 = udfpsControllerOverlay2.requestReason;
                ?? r2 = (i7 == z3 || i7 == i) ? 4 : i7 != i2 ? i7 != 4 ? -1 : z3 : i;
                InstanceId sessionId = udfpsController.mSessionTracker.getSessionId(r2);
                int id = sessionId != null ? sessionId.getId() : -1;
                int integer = udfpsController.mContext.getResources().getInteger(R.integer.device_idle_sensing_to_ms);
                NormalizedTouchData normalizedTouchData11 = normalizedTouchData;
                float f = normalizedTouchData11.x;
                boolean zIsDozing = statusBarStateController.isDozing();
                StatsEvent.Builder builderNewBuilder = StatsEvent.newBuilder();
                builderNewBuilder.setAtomId(577);
                builderNewBuilder.writeInt((int) r1);
                builderNewBuilder.writeInt(integer);
                builderNewBuilder.writeInt(id);
                builderNewBuilder.writeFloat(f);
                float f2 = normalizedTouchData11.y;
                builderNewBuilder.writeFloat(f2);
                float f3 = normalizedTouchData11.minor;
                builderNewBuilder.writeFloat(f3);
                float f4 = normalizedTouchData11.major;
                builderNewBuilder.writeFloat(f4);
                float f5 = normalizedTouchData11.orientation;
                builderNewBuilder.writeFloat(f5);
                long j3 = normalizedTouchData11.time;
                builderNewBuilder.writeLong(j3);
                int i8 = id;
                long j4 = normalizedTouchData11.gestureStart;
                builderNewBuilder.writeLong(j4);
                builderNewBuilder.writeBoolean(zIsDozing);
                builderNewBuilder.usePooledBuffer();
                StatsLog.write(builderNewBuilder.build());
                if (Build.isDebuggable()) {
                    StringBuilder sbM2 = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("\n        |NormalizedTouchData [", interactionEvent4.toString(), "] {\n        |     pointerId: ");
                    sbM2.append(normalizedTouchData11.pointerId);
                    sbM2.append("\n        |             x: ");
                    sbM2.append(normalizedTouchData11.x);
                    sbM2.append("\n        |             y: ");
                    sbM2.append(f2);
                    sbM2.append("\n        |         minor: ");
                    sbM2.append(f3);
                    sbM2.append("\n        |         major: ");
                    sbM2.append(f4);
                    sbM2.append("\n        |   orientation: ");
                    sbM2.append(f5);
                    sbM2.append("\n        |          time: ");
                    sbM2.append(j3);
                    sbM2.append("\n        |  gestureStart: ");
                    sbM2.append(j4);
                    sbM2.append("\n        |}\n        ");
                    Log.d("UdfpsController", StringsKt__IndentKt.trimMargin$default(sbM2.toString()));
                    Log.d("UdfpsController", "sessionId: " + i8 + ", isAod: " + statusBarStateController.isDozing() + ", touchConfigId: " + integer);
                }
            }
        }
        if (udfpsController.mActivePointerId != -1) {
            z4 = z3;
        }
        if (z4 && !udfpsController.mPointerPilfered) {
            UdfpsControllerOverlay udfpsControllerOverlay3 = udfpsController.mOverlay;
            if (udfpsControllerOverlay3 == null) {
                z5 = -1;
                c = 2;
            } else {
                int i9 = udfpsControllerOverlay3.requestReason;
                c = 2;
                z5 = (i9 == z3 || i9 == 2) ? 4 : i9 != 3 ? i9 != 4 ? -1 : z3 : 2;
            }
            if (z5 != c) {
                udfpsController.mInputManager.pilferPointers(udfpsControllerOverlay3.overlayTouchView.getViewRootImpl().getInputToken());
                udfpsController.mPointerPilfered = z3;
            }
        }
        return udfpsController.mActivePointerId != -1 ? z3 : z2;
    }

    static {
        VibrationEffect.get(0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.biometrics.UdfpsController$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.content.BroadcastReceiver, com.android.systemui.biometrics.UdfpsController$2] */
    public UdfpsController(Context context, Execution execution, LayoutInflater layoutInflater, FingerprintManager fingerprintManager, WindowManager windowManager, StatusBarStateController statusBarStateController, DelayableExecutor delayableExecutor, StatusBarKeyguardViewManager statusBarKeyguardViewManager, DumpManager dumpManager, KeyguardUpdateMonitor keyguardUpdateMonitor, FalsingManager falsingManager, PowerManager powerManager, AccessibilityManager accessibilityManager, ScreenLifecycle screenLifecycle, VibratorHelper vibratorHelper, UdfpsHapticsSimulator udfpsHapticsSimulator, UdfpsShell udfpsShell, KeyguardStateController keyguardStateController, DisplayManager displayManager, Handler handler, ConfigurationController configurationController, SystemClock systemClock, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, SystemUIDialogManager systemUIDialogManager, LatencyTracker latencyTracker, ActivityTransitionAnimator activityTransitionAnimator, Executor executor, PrimaryBouncerInteractor primaryBouncerInteractor, ShadeInteractor shadeInteractor, SinglePointerTouchProcessor singlePointerTouchProcessor, SessionTracker sessionTracker, AlternateBouncerInteractor alternateBouncerInteractor, InputManager inputManager, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, SelectedUserInteractor selectedUserInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, Lazy lazy, Lazy lazy2, Lazy lazy3, UdfpsOverlayInteractor udfpsOverlayInteractor, PowerInteractor powerInteractor, CoroutineScope coroutineScope, UserActivityNotifier userActivityNotifier) {
        ?? r2 = new ScreenLifecycle.Observer() { // from class: com.android.systemui.biometrics.UdfpsController.1
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
        this.mScreenObserver = r2;
        ?? r3 = new BroadcastReceiver() { // from class: com.android.systemui.biometrics.UdfpsController.2
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
        this.mBroadcastReceiver = r3;
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
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mFalsingManager = falsingManager;
        this.mPowerManager = powerManager;
        this.mAccessibilityManager = accessibilityManager;
        screenLifecycle.addObserver(r2);
        this.mScreenOn = screenLifecycle.mScreenState == 2;
        this.mSystemClock = systemClock;
        this.mLatencyTracker = latencyTracker;
        this.mSensorProps = new FingerprintSensorPropertiesInternal(-1, 0, 0, new ArrayList(), 0, false);
        this.mBiometricExecutor = executor;
        this.mUdfpsOverlayInteractor = udfpsOverlayInteractor;
        this.mPowerInteractor = powerInteractor;
        this.mScope = coroutineScope;
        this.mInputManager = inputManager;
        this.mKeyguardTransitionInteractor = keyguardTransitionInteractor;
        this.mTouchProcessor = singlePointerTouchProcessor;
        this.mSessionTracker = sessionTracker;
        this.mDeviceEntryUdfpsTouchOverlayViewModel = lazy;
        this.mDefaultUdfpsTouchOverlayViewModel = lazy2;
        this.mPromptUdfpsTouchOverlayViewModel = lazy3;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "UdfpsController", this);
        this.mOrientationListener = new BiometricDisplayListener(context, displayManager, handler, BiometricDisplayListener.SensorType.UnderDisplayFingerprint.INSTANCE, new Function0() { // from class: com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AuthController$$ExternalSyntheticLambda3 authController$$ExternalSyntheticLambda3 = this.f$0.mAuthControllerUpdateUdfpsLocation;
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
        context.registerReceiver(r3, intentFilter, 2);
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
    public final void dump(PrintWriter printWriter, String[] strArr) throws Resources.NotFoundException {
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
            StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("Mismatched fingerDown: ", j, " current: ");
            sbM.append(this.mOverlay.requestId);
            Log.w("UdfpsController", sbM.toString());
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
            if (i2 == 3) {
                UdfpsTouchOverlayBinder.bind(udfpsTouchOverlay, (UdfpsTouchOverlayViewModel) udfpsControllerOverlay.promptUdfpsTouchOverlayViewModel.get(), udfpsOverlayInteractor);
            } else if (i2 != 4) {
                UdfpsTouchOverlayBinder.bind(udfpsTouchOverlay, (UdfpsTouchOverlayViewModel) udfpsControllerOverlay.defaultUdfpsTouchOverlayViewModel.get(), udfpsOverlayInteractor);
            } else {
                UdfpsTouchOverlayBinder.bind(udfpsTouchOverlay, (UdfpsTouchOverlayViewModel) udfpsControllerOverlay.deviceEntryUdfpsTouchOverlayViewModel.get(), udfpsOverlayInteractor);
            }
            udfpsControllerOverlay.overlayTouchView = udfpsTouchOverlay;
            if (udfpsTouchOverlay != null) {
                udfpsControllerOverlay.accessibilityManager.isTouchExplorationEnabled();
                ?? r3 = new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$2$1
                    @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
                    public final void onTouchExplorationStateChanged(boolean z) {
                        if (udfpsControllerOverlay.accessibilityManager.isTouchExplorationEnabled()) {
                            View view = udfpsTouchOverlay;
                            final UdfpsControllerOverlay udfpsControllerOverlay2 = udfpsControllerOverlay;
                            view.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$2$1.1
                                @Override // android.view.View.OnHoverListener
                                public final boolean onHover(View view2, MotionEvent motionEvent) {
                                    Function2 function2 = udfpsControllerOverlay2.onTouch;
                                    view2.getClass();
                                    motionEvent.getClass();
                                    return ((Boolean) function2.invoke(view2, motionEvent)).booleanValue();
                                }
                            });
                            udfpsTouchOverlay.setOnTouchListener(null);
                            udfpsControllerOverlay.getClass();
                            return;
                        }
                        udfpsTouchOverlay.setOnHoverListener(null);
                        View view2 = udfpsTouchOverlay;
                        final UdfpsControllerOverlay udfpsControllerOverlay3 = udfpsControllerOverlay;
                        view2.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$2$1.2
                            @Override // android.view.View.OnTouchListener
                            public final boolean onTouch(View view3, MotionEvent motionEvent) {
                                Function2 function2 = udfpsControllerOverlay3.onTouch;
                                view3.getClass();
                                motionEvent.getClass();
                                return ((Boolean) function2.invoke(view3, motionEvent)).booleanValue();
                            }
                        });
                        udfpsControllerOverlay.getClass();
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
                        UdfpsController.UdfpsOverlayController udfpsOverlayController = this.f$0;
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
                    final UdfpsController.UdfpsOverlayController udfpsOverlayController = this.f$0;
                    final long j2 = j;
                    int i3 = i2;
                    IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback2 = iUdfpsOverlayControllerCallback;
                    UdfpsController udfpsController = UdfpsController.this;
                    udfpsController.showUdfpsOverlay(new UdfpsControllerOverlay(udfpsController.mInflater, udfpsController.mWindowManager, udfpsController.mAccessibilityManager, udfpsController.mKeyguardUpdateMonitor, udfpsController.mKeyguardStateController, udfpsController.mUdfpsDisplayMode, j2, i3, iUdfpsOverlayControllerCallback2, new Function2() { // from class: com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda6
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return Boolean.valueOf(UdfpsController.m1021$$Nest$monTouch(UdfpsController.this, j2, (MotionEvent) obj2));
                        }
                    }, udfpsController.mKeyguardTransitionInteractor, udfpsController.mDeviceEntryUdfpsTouchOverlayViewModel, udfpsController.mDefaultUdfpsTouchOverlayViewModel, udfpsController.mPromptUdfpsTouchOverlayViewModel, udfpsController.mUdfpsOverlayInteractor, udfpsController.mPowerInteractor, udfpsController.mScope));
                }
            });
        }

        public final void onEnrollmentHelp(int i) {
        }

        public final void onEnrollmentProgress(int i, int i2) {
        }
    }
}
