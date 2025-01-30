package com.android.systemui.doze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.format.Formatter;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.Preconditions;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dock.DockManager;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.DozeSensors;
import com.android.systemui.doze.DozeTriggers;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.DozeServiceHost$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.util.sensors.ProximityCheck;
import com.android.systemui.util.sensors.ProximitySensor;
import com.android.systemui.util.sensors.ProximitySensorImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.wakelock.WakeLock;
import com.samsung.android.knox.custom.CustomDeviceManager;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DozeTriggers implements DozeMachine.Part {
    public static boolean sWakeDisplaySensorState = true;
    public final boolean mAllowPulseTriggers;
    public DozeTriggers$$ExternalSyntheticLambda6 mAodInterruptRunnable;
    public final AuthController mAuthController;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final TriggerReceiver mBroadcastReceiver;
    public final AmbientDisplayConfiguration mConfig;
    public final Context mContext;
    public final DockManager mDockManager;
    public final DozeHost mDozeHost;
    public final DozeLog mDozeLog;
    public final DozeParameters mDozeParameters;
    public final DozeSensors mDozeSensors;
    public final C12502 mHostCallback;
    public boolean mInAod;
    public final KeyguardStateController mKeyguardStateController;
    public DozeMachine mMachine;
    public long mNotificationPulseTime;
    public final ProximityCheck mProxCheck;
    public final SessionTracker mSessionTracker;
    public final UiEventLogger mUiEventLogger;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;
    public final WakeLock mWakeLock;
    public boolean mWantProxSensor;
    public boolean mWantSensors;
    public boolean mWantTouchScreenSensors;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.doze.DozeTriggers$3 */
    public abstract /* synthetic */ class AbstractC12513 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSING.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSING_BRIGHT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_DOCKED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSE_DONE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_SUSPEND_TRIGGERS.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DockEventListener implements DockManager.DockEventListener {
        public /* synthetic */ DockEventListener(DozeTriggers dozeTriggers, int i) {
            this(dozeTriggers);
        }

        private DockEventListener(DozeTriggers dozeTriggers) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum DozingUpdateUiEvent implements UiEventLogger.UiEventEnum {
        DOZING_UPDATE_NOTIFICATION(433),
        DOZING_UPDATE_SIGMOTION(434),
        DOZING_UPDATE_SENSOR_PICKUP(435),
        DOZING_UPDATE_SENSOR_DOUBLE_TAP(436),
        DOZING_UPDATE_SENSOR_LONG_SQUEEZE(437),
        DOZING_UPDATE_DOCKING(438),
        DOZING_UPDATE_SENSOR_WAKEUP(439),
        DOZING_UPDATE_SENSOR_WAKE_LOCKSCREEN(440),
        DOZING_UPDATE_SENSOR_TAP(CustomDeviceManager.MULTI_WINDOW_FIXED_STATE),
        DOZING_UPDATE_AUTH_TRIGGERED(657),
        DOZING_UPDATE_QUICK_PICKUP(708),
        DOZING_UPDATE_WAKE_TIMEOUT(794);

        private final int mId;

        DozingUpdateUiEvent(int i) {
            this.mId = i;
        }

        public static DozingUpdateUiEvent fromReason(int i) {
            switch (i) {
                case 1:
                    return DOZING_UPDATE_NOTIFICATION;
                case 2:
                    return DOZING_UPDATE_SIGMOTION;
                case 3:
                    return DOZING_UPDATE_SENSOR_PICKUP;
                case 4:
                    return DOZING_UPDATE_SENSOR_DOUBLE_TAP;
                case 5:
                    return DOZING_UPDATE_SENSOR_LONG_SQUEEZE;
                case 6:
                    return DOZING_UPDATE_DOCKING;
                case 7:
                    return DOZING_UPDATE_SENSOR_WAKEUP;
                case 8:
                    return DOZING_UPDATE_SENSOR_WAKE_LOCKSCREEN;
                case 9:
                    return DOZING_UPDATE_SENSOR_TAP;
                case 10:
                    return DOZING_UPDATE_AUTH_TRIGGERED;
                case 11:
                    return DOZING_UPDATE_QUICK_PICKUP;
                default:
                    return null;
            }
        }

        public final int getId() {
            return this.mId;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TriggerReceiver extends BroadcastReceiver {
        public boolean mRegistered;

        public /* synthetic */ TriggerReceiver(DozeTriggers dozeTriggers, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("com.android.systemui.doze.pulse".equals(intent.getAction())) {
                if (DozeMachine.DEBUG) {
                    Log.d("DozeTriggers", "Received pulse intent");
                }
                DozeTriggers dozeTriggers = DozeTriggers.this;
                boolean z = DozeTriggers.sWakeDisplaySensorState;
                dozeTriggers.requestPulse(0, false, null);
            }
        }

        private TriggerReceiver() {
        }
    }

    static {
        boolean z = DozeService.DEBUG;
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.doze.DozeTriggers$2] */
    public DozeTriggers(Context context, DozeHost dozeHost, AmbientDisplayConfiguration ambientDisplayConfiguration, DozeParameters dozeParameters, AsyncSensorManager asyncSensorManager, WakeLock wakeLock, DockManager dockManager, ProximitySensor proximitySensor, ProximityCheck proximityCheck, DozeLog dozeLog, BroadcastDispatcher broadcastDispatcher, SecureSettings secureSettings, AuthController authController, UiEventLogger uiEventLogger, SessionTracker sessionTracker, KeyguardStateController keyguardStateController, DevicePostureController devicePostureController, UserTracker userTracker) {
        int i = 0;
        this.mBroadcastReceiver = new TriggerReceiver(this, i);
        new DockEventListener(this, i);
        this.mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.doze.DozeTriggers.1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i2, Context context2) {
                for (DozeSensors.TriggerSensor triggerSensor : DozeTriggers.this.mDozeSensors.mTriggerSensors) {
                    triggerSensor.updateListening();
                }
            }
        };
        this.mHostCallback = new DozeHost.Callback() { // from class: com.android.systemui.doze.DozeTriggers.2
            @Override // com.android.systemui.doze.DozeHost.Callback
            public final void onNotificationAlerted(DozeServiceHost$$ExternalSyntheticLambda1 dozeServiceHost$$ExternalSyntheticLambda1) {
                boolean z = DozeTriggers.sWakeDisplaySensorState;
                DozeTriggers dozeTriggers = DozeTriggers.this;
                dozeTriggers.getClass();
                if (DozeMachine.DEBUG) {
                    Log.d("DozeTriggers", "requestNotificationPulse");
                }
                boolean z2 = DozeTriggers.sWakeDisplaySensorState;
                DozeLog dozeLog2 = dozeTriggers.mDozeLog;
                if (!z2) {
                    Log.d("DozeTriggers", "Wake display false. Pulse denied.");
                    DozeTriggers.runIfNotNull(dozeServiceHost$$ExternalSyntheticLambda1);
                    dozeLog2.tracePulseDropped("wakeDisplaySensor");
                    return;
                }
                dozeTriggers.mNotificationPulseTime = SystemClock.elapsedRealtime();
                if (!dozeTriggers.mConfig.pulseOnNotificationEnabled(((UserTrackerImpl) dozeTriggers.mUserTracker).getUserId())) {
                    DozeTriggers.runIfNotNull(dozeServiceHost$$ExternalSyntheticLambda1);
                    dozeLog2.tracePulseDropped("pulseOnNotificationsDisabled");
                    return;
                }
                if (((DozeServiceHost) dozeTriggers.mDozeHost).mAlwaysOnSuppressed) {
                    DozeTriggers.runIfNotNull(dozeServiceHost$$ExternalSyntheticLambda1);
                    dozeLog2.tracePulseDropped("dozeSuppressed");
                    return;
                }
                dozeTriggers.requestPulse(1, false, dozeServiceHost$$ExternalSyntheticLambda1);
                DozeLogger dozeLogger = dozeLog2.mLogger;
                dozeLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                DozeLogger$logNotificationPulse$2 dozeLogger$logNotificationPulse$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logNotificationPulse$2
                    @Override // kotlin.jvm.functions.Function1
                    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        return "Notification pulse";
                    }
                };
                LogBuffer logBuffer = dozeLogger.buffer;
                logBuffer.commit(logBuffer.obtain("DozeLog", logLevel, dozeLogger$logNotificationPulse$2, null));
                dozeLog2.mNotificationPulseStats.mCount++;
            }
        };
        this.mContext = context;
        this.mDozeHost = dozeHost;
        this.mConfig = ambientDisplayConfiguration;
        this.mDozeParameters = dozeParameters;
        this.mWakeLock = wakeLock;
        this.mAllowPulseTriggers = true;
        this.mSessionTracker = sessionTracker;
        this.mDozeSensors = new DozeSensors(context.getResources(), asyncSensorManager, dozeParameters, ambientDisplayConfiguration, wakeLock, new DozeTriggers$$ExternalSyntheticLambda2(this), new DozeTriggers$$ExternalSyntheticLambda3(this, 0), dozeLog, proximitySensor, secureSettings, authController, devicePostureController, userTracker);
        this.mDockManager = dockManager;
        this.mProxCheck = proximityCheck;
        this.mDozeLog = dozeLog;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mAuthController = authController;
        this.mUiEventLogger = uiEventLogger;
        this.mKeyguardStateController = keyguardStateController;
        this.mUserTracker = userTracker;
    }

    public static boolean canPulse(DozeMachine.State state, boolean z) {
        return state == DozeMachine.State.DOZE || state == DozeMachine.State.DOZE_AOD || state == DozeMachine.State.DOZE_AOD_DOCKED || ((state == DozeMachine.State.DOZE_AOD_PAUSED || state == DozeMachine.State.DOZE_AOD_PAUSING) && z);
    }

    public static void runIfNotNull(Runnable runnable) {
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void destroy() {
        DozeSensors dozeSensors = this.mDozeSensors;
        for (DozeSensors.TriggerSensor triggerSensor : dozeSensors.mTriggerSensors) {
            if (triggerSensor.mRequested) {
                triggerSensor.mRequested = false;
                triggerSensor.updateListening();
            }
        }
        dozeSensors.mProximitySensor.destroy();
        ((DevicePostureControllerImpl) dozeSensors.mDevicePostureController).removeCallback(dozeSensors.mDevicePostureCallback);
        dozeSensors.mAuthController.removeCallback(dozeSensors.mAuthControllerCallback);
        this.mProxCheck.mSensor.destroy();
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void dump(PrintWriter printWriter) {
        printWriter.println(" mAodInterruptRunnable=" + this.mAodInterruptRunnable);
        printWriter.print(" notificationPulseTime=");
        printWriter.println(Formatter.formatShortElapsedTime(this.mContext, this.mNotificationPulseTime));
        printWriter.println(" DozeHost#isPulsePending=" + ((DozeServiceHost) this.mDozeHost).mPulsePending);
        printWriter.println("DozeSensors:");
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.increaseIndent();
        StringBuilder sb = new StringBuilder("mListening=");
        DozeSensors dozeSensors = this.mDozeSensors;
        sb.append(dozeSensors.mListening);
        indentingPrintWriter.println(sb.toString());
        indentingPrintWriter.println("mDevicePosture=" + DevicePostureController.devicePostureToString(dozeSensors.mDevicePosture));
        indentingPrintWriter.println("mListeningTouchScreenSensors=" + dozeSensors.mListeningTouchScreenSensors);
        indentingPrintWriter.println("mSelectivelyRegisterProxSensors=" + dozeSensors.mSelectivelyRegisterProxSensors);
        indentingPrintWriter.println("mListeningProxSensors=" + dozeSensors.mListeningProxSensors);
        indentingPrintWriter.println("mScreenOffUdfpsEnabled=" + dozeSensors.mScreenOffUdfpsEnabled);
        indentingPrintWriter.println("mUdfpsEnrolled=" + dozeSensors.mUdfpsEnrolled);
        IndentingPrintWriter indentingPrintWriter2 = new IndentingPrintWriter(indentingPrintWriter);
        indentingPrintWriter2.increaseIndent();
        for (DozeSensors.TriggerSensor triggerSensor : dozeSensors.mTriggerSensors) {
            indentingPrintWriter2.println("Sensor: " + triggerSensor.toString());
        }
        indentingPrintWriter2.println("ProxSensor: " + dozeSensors.mProximitySensor.toString());
    }

    public final void gentleWakeUp(int i) {
        Optional.ofNullable(DozingUpdateUiEvent.fromReason(i)).ifPresent(new DozeTriggers$$ExternalSyntheticLambda3(this, 3));
        if (this.mDozeParameters.getDisplayNeedsBlanking()) {
            ((DozeServiceHost) this.mDozeHost).setAodDimmingScrim(1.0f);
        }
        this.mMachine.mDozeService.requestWakeUp(i);
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void onScreenState(int i) {
        DozeSensors dozeSensors = this.mDozeSensors;
        dozeSensors.getClass();
        boolean z = i == 3 || i == 4 || i == 1;
        ProximitySensorImpl proximitySensorImpl = (ProximitySensorImpl) dozeSensors.mProximitySensor;
        proximitySensorImpl.mSecondarySafe = proximitySensorImpl.mSecondaryThresholdSensor.isLoaded() && z;
        proximitySensorImpl.chooseSensor();
        boolean z2 = i == 3 || i == 4 || i == 1;
        dozeSensors.setProxListening(this.mWantProxSensor && z2);
        boolean z3 = this.mWantSensors;
        boolean z4 = this.mWantTouchScreenSensors;
        boolean z5 = this.mInAod;
        boolean z6 = !dozeSensors.mSelectivelyRegisterProxSensors || z2;
        if (dozeSensors.mListening != z3 || dozeSensors.mListeningTouchScreenSensors != z4 || dozeSensors.mListeningProxSensors != z6 || dozeSensors.mListeningAodOnlySensors != z5) {
            dozeSensors.mListening = z3;
            dozeSensors.mListeningTouchScreenSensors = z4;
            dozeSensors.mListeningProxSensors = z6;
            dozeSensors.mListeningAodOnlySensors = z5;
            dozeSensors.updateListening();
        }
        DozeTriggers$$ExternalSyntheticLambda6 dozeTriggers$$ExternalSyntheticLambda6 = this.mAodInterruptRunnable;
        if (dozeTriggers$$ExternalSyntheticLambda6 == null || i != 2) {
            return;
        }
        dozeTriggers$$ExternalSyntheticLambda6.run();
        this.mAodInterruptRunnable = null;
    }

    public void onSensor(final int i, final float f, final float f2, final float[] fArr) {
        final boolean z = i == 4;
        final boolean z2 = i == 9;
        boolean z3 = i == 3;
        boolean z4 = i == 5;
        boolean z5 = i == 7;
        boolean z6 = i == 8;
        final boolean z7 = i == 10;
        boolean z8 = i == 11;
        boolean z9 = z8 || ((z5 || z6) && fArr != null && fArr.length > 0 && fArr[0] != 0.0f);
        if (z5) {
            onWakeScreen(z9, this.mMachine.isExecutingTransition() ? null : this.mMachine.getState(), i);
        } else if (z4) {
            requestPulse(i, true, null);
        } else if (!z6 && !z8) {
            final boolean z10 = z3;
            proximityCheckThenCall(i, new Consumer() { // from class: com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DozeTriggers dozeTriggers = DozeTriggers.this;
                    int i2 = i;
                    boolean z11 = z;
                    boolean z12 = z2;
                    float f3 = f;
                    float f4 = f2;
                    boolean z13 = z10;
                    boolean z14 = z7;
                    float[] fArr2 = fArr;
                    Boolean bool = (Boolean) obj;
                    DozeLog dozeLog = dozeTriggers.mDozeLog;
                    if (bool != null && bool.booleanValue()) {
                        dozeLog.traceSensorEventDropped(i2, "prox reporting near");
                        return;
                    }
                    DozeHost dozeHost = dozeTriggers.mDozeHost;
                    if (!z11 && !z12) {
                        if (z13) {
                            if (((KeyguardStateControllerImpl) dozeTriggers.mKeyguardStateController).mOccluded) {
                                dozeLog.traceSensorEventDropped(i2, "keyguard occluded");
                                return;
                            } else {
                                dozeTriggers.gentleWakeUp(i2);
                                return;
                            }
                        }
                        if (!z14) {
                            ((DozeServiceHost) dozeHost).extendPulse(i2);
                            return;
                        }
                        if (DozeTriggers.canPulse(dozeTriggers.mMachine.getState(), true)) {
                            LogBuffer.log$default(dozeLog.mLogger.buffer, "DozeLog", LogLevel.DEBUG, "updfsLongPress - setting aodInterruptRunnable to run when the display is on", null, 8, null);
                            dozeTriggers.mAodInterruptRunnable = new DozeTriggers$$ExternalSyntheticLambda6(dozeTriggers, f3, f4, fArr2);
                        } else {
                            LogBuffer.log$default(dozeLog.mLogger.buffer, "DozeLog", LogLevel.DEBUG, "udfpsLongPress - Not sending aodInterrupt. Unsupported doze state.", null, 8, null);
                        }
                        dozeTriggers.requestPulse(10, true, null);
                        return;
                    }
                    DozeServiceHost dozeServiceHost = (DozeServiceHost) dozeHost;
                    dozeServiceHost.getClass();
                    if (f3 >= 0.0f && f4 >= 0.0f) {
                        View view = dozeServiceHost.mAmbientIndicationContainer;
                        if (view != null && view.getVisibility() == 0) {
                            dozeServiceHost.mAmbientIndicationContainer.getLocationOnScreen(new int[2]);
                            float f5 = f3 - r4[0];
                            float f6 = f4 - r4[1];
                            if (0.0f <= f5 && f5 <= dozeServiceHost.mAmbientIndicationContainer.getWidth() && 0.0f <= f6 && f6 <= dozeServiceHost.mAmbientIndicationContainer.getHeight()) {
                                long elapsedRealtime = SystemClock.elapsedRealtime();
                                MotionEvent obtain = MotionEvent.obtain(elapsedRealtime, elapsedRealtime, 0, f3, f4, 0);
                                dozeServiceHost.mAmbientIndicationContainer.dispatchTouchEvent(obtain);
                                obtain.recycle();
                                MotionEvent obtain2 = MotionEvent.obtain(elapsedRealtime, elapsedRealtime, 1, f3, f4, 0);
                                dozeServiceHost.mAmbientIndicationContainer.dispatchTouchEvent(obtain2);
                                obtain2.recycle();
                            }
                        }
                        ((KeyguardRepositoryImpl) dozeServiceHost.mDozeInteractor.keyguardRepository)._lastDozeTapToWakePosition.setValue(new Point((int) f3, (int) f4));
                    }
                    dozeTriggers.gentleWakeUp(i2);
                }
            }, true);
        } else if (z9) {
            requestPulse(i, true, null);
        }
        if (!z3 || ((KeyguardStateControllerImpl) this.mKeyguardStateController).mOccluded) {
            return;
        }
        boolean z11 = SystemClock.elapsedRealtime() - this.mNotificationPulseTime < ((long) this.mDozeParameters.getInt("doze.pickup.vibration.threshold", R.integer.doze_pickup_vibration_threshold));
        DozeLog dozeLog = this.mDozeLog;
        DozeLogger dozeLogger = dozeLog.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        DozeLogger$logPickupWakeup$2 dozeLogger$logPickupWakeup$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logPickupWakeup$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AbstractC0866xb1ce8deb.m86m("PickupWakeup withinVibrationThreshold=", ((LogMessage) obj).getBool1());
            }
        };
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logPickupWakeup$2, null);
        obtain.setBool1(z11);
        logBuffer.commit(obtain);
        (z11 ? dozeLog.mPickupPulseNearVibrationStats : dozeLog.mPickupPulseNotNearVibrationStats).mCount++;
    }

    public final void onWakeScreen(boolean z, final DozeMachine.State state, final int i) {
        DozeLogger dozeLogger = this.mDozeLog.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        DozeLogger$logWakeDisplay$2 dozeLogger$logWakeDisplay$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logWakeDisplay$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Display wakefulness changed, isAwake=" + logMessage.getBool1() + ", reason=" + DozeLog.reasonToString(logMessage.getInt1());
            }
        };
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logWakeDisplay$2, null);
        obtain.setBool1(z);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
        sWakeDisplaySensorState = z;
        if (z) {
            proximityCheckThenCall(i, new Consumer() { // from class: com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DozeTriggers dozeTriggers = DozeTriggers.this;
                    DozeMachine.State state2 = state;
                    int i2 = i;
                    Boolean bool = (Boolean) obj;
                    dozeTriggers.getClass();
                    if ((bool == null || !bool.booleanValue()) && state2 == DozeMachine.State.DOZE) {
                        dozeTriggers.mMachine.requestState(DozeMachine.State.DOZE_AOD);
                        Optional.ofNullable(DozeTriggers.DozingUpdateUiEvent.fromReason(i2)).ifPresent(new DozeTriggers$$ExternalSyntheticLambda3(dozeTriggers, 2));
                    }
                }
            }, false);
            return;
        }
        boolean z2 = state == DozeMachine.State.DOZE_AOD_PAUSED;
        if ((state == DozeMachine.State.DOZE_AOD_PAUSING) || z2) {
            return;
        }
        this.mMachine.requestState(DozeMachine.State.DOZE);
        this.mUiEventLogger.log(DozingUpdateUiEvent.DOZING_UPDATE_WAKE_TIMEOUT);
    }

    public final void proximityCheckThenCall(final int i, final Consumer consumer, boolean z) {
        Boolean isNear = ((ProximitySensorImpl) this.mDozeSensors.mProximitySensor).isNear();
        if (z) {
            consumer.accept(null);
            return;
        }
        if (isNear != null) {
            consumer.accept(isNear);
            return;
        }
        final long uptimeMillis = SystemClock.uptimeMillis();
        Consumer consumer2 = new Consumer() { // from class: com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda5
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DozeTriggers dozeTriggers = DozeTriggers.this;
                long j = uptimeMillis;
                int i2 = i;
                Consumer consumer3 = consumer;
                Boolean bool = (Boolean) obj;
                dozeTriggers.getClass();
                long uptimeMillis2 = SystemClock.uptimeMillis();
                boolean z2 = (bool == null || !bool.booleanValue()) ? 0 : 1;
                long j2 = uptimeMillis2 - j;
                DozeLog dozeLog = dozeTriggers.mDozeLog;
                DozeLogger dozeLogger = dozeLog.mLogger;
                dozeLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                Function1 function1 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logProximityResult$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        LogMessage logMessage = (LogMessage) obj2;
                        return "Proximity result reason=" + DozeLog.reasonToString(logMessage.getInt1()) + " near=" + logMessage.getBool1() + " millis=" + logMessage.getLong1();
                    }
                };
                LogBuffer logBuffer = dozeLogger.buffer;
                LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, function1, null);
                obtain.setBool1(z2);
                obtain.setLong1(j2);
                obtain.setInt1(i2);
                logBuffer.commit(obtain);
                dozeLog.mProxStats[i2][!z2].mCount++;
                consumer3.accept(bool);
                dozeTriggers.mWakeLock.release("DozeTriggers");
            }
        };
        ProximityCheck proximityCheck = this.mProxCheck;
        if (((ProximitySensorImpl) proximityCheck.mSensor).isLoaded()) {
            ((ArrayList) proximityCheck.mCallbacks).add(consumer2);
            if (!proximityCheck.mRegistered.getAndSet(true)) {
                proximityCheck.mSensor.register(proximityCheck.mListener);
                proximityCheck.mDelayableExecutor.executeDelayed(500L, proximityCheck);
            }
        } else {
            consumer2.accept(null);
        }
        this.mWakeLock.acquire("DozeTriggers");
    }

    public final void registerCallbacks() {
        TriggerReceiver triggerReceiver = this.mBroadcastReceiver;
        if (!triggerReceiver.mRegistered) {
            this.mBroadcastDispatcher.registerReceiver(new IntentFilter("com.android.systemui.doze.pulse"), triggerReceiver);
            triggerReceiver.mRegistered = true;
        }
        this.mDockManager.getClass();
        DozeServiceHost dozeServiceHost = (DozeServiceHost) this.mDozeHost;
        dozeServiceHost.getClass();
        Assert.isMainThread();
        dozeServiceHost.mCallbacks.add(this.mHostCallback);
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, this.mContext.getMainExecutor());
    }

    public final void requestPulse(final int i, final boolean z, final DozeServiceHost$$ExternalSyntheticLambda1 dozeServiceHost$$ExternalSyntheticLambda1) {
        Assert.isMainThread();
        DozeServiceHost dozeServiceHost = (DozeServiceHost) this.mDozeHost;
        dozeServiceHost.extendPulse(i);
        final DozeMachine.State state = this.mMachine.isExecutingTransition() ? null : this.mMachine.getState();
        if (state == DozeMachine.State.DOZE_PULSING && i == 8) {
            this.mMachine.requestState(DozeMachine.State.DOZE_PULSING_BRIGHT);
            return;
        }
        boolean z2 = this.mAllowPulseTriggers;
        if (z2 && !dozeServiceHost.mPulsePending && canPulse(state, z)) {
            dozeServiceHost.mPulsePending = true;
            proximityCheckThenCall(i, new Consumer() { // from class: com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DozeTriggers dozeTriggers = DozeTriggers.this;
                    Runnable runnable = dozeServiceHost$$ExternalSyntheticLambda1;
                    DozeMachine.State state2 = state;
                    boolean z3 = z;
                    int i2 = i;
                    Boolean bool = (Boolean) obj;
                    DozeHost dozeHost = dozeTriggers.mDozeHost;
                    DozeLog dozeLog = dozeTriggers.mDozeLog;
                    if (bool != null && bool.booleanValue()) {
                        dozeLog.tracePulseDropped("requestPulse - inPocket");
                        ((DozeServiceHost) dozeHost).mPulsePending = false;
                        DozeTriggers.runIfNotNull(runnable);
                        return;
                    }
                    DozeServiceHost dozeServiceHost2 = (DozeServiceHost) dozeHost;
                    boolean z4 = dozeServiceHost2.mPulsePending;
                    dozeServiceHost2.mPulsePending = false;
                    Lazy lazy = dozeServiceHost2.mBiometricUnlockControllerLazy;
                    if (z4) {
                        if (!(((BiometricUnlockController) lazy.get()).mMode == 1) && DozeTriggers.canPulse(state2, z3)) {
                            DozeMachine dozeMachine = dozeTriggers.mMachine;
                            Preconditions.checkState(!dozeMachine.isExecutingTransition());
                            dozeMachine.requestState(DozeMachine.State.DOZE_REQUEST_PULSE, i2);
                            return;
                        }
                    }
                    if (z4) {
                        if (((BiometricUnlockController) lazy.get()).mMode == 1) {
                            dozeLog.tracePulseDropped("continuePulseRequest - pulsingBlocked");
                        } else if (!DozeTriggers.canPulse(state2, z3)) {
                            dozeLog.tracePulseDropped(state2, "continuePulseRequest - doze state cannot pulse");
                        }
                    } else {
                        dozeLog.tracePulseDropped("continuePulseRequest - pulse no longer pending, pulse was cancelled before it could start transitioning to pulsing state.");
                    }
                    DozeTriggers.runIfNotNull(runnable);
                }
            }, !SystemProperties.getBoolean("doze.pulse.proxcheck", this.mDozeParameters.mResources.getBoolean(R.bool.doze_proximity_check_before_pulse)) || z);
            Optional.ofNullable(DozingUpdateUiEvent.fromReason(i)).ifPresent(new DozeTriggers$$ExternalSyntheticLambda3(this, 1));
            return;
        }
        DozeLog dozeLog = this.mDozeLog;
        if (!z2) {
            dozeLog.tracePulseDropped("requestPulse - !mAllowPulseTriggers");
        } else if (dozeServiceHost.mPulsePending) {
            dozeLog.tracePulseDropped("requestPulse - pulsePending");
        } else if (!canPulse(state, z)) {
            dozeLog.tracePulseDropped(state, "requestPulse - dozeState cannot pulse");
        }
        runIfNotNull(dozeServiceHost$$ExternalSyntheticLambda1);
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void setDozeMachine(DozeMachine dozeMachine) {
        this.mMachine = dozeMachine;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        if (state == DozeMachine.State.DOZE_SUSPEND_TRIGGERS && state2 != DozeMachine.State.FINISH && state2 != DozeMachine.State.UNINITIALIZED) {
            registerCallbacks();
        }
        int i = AbstractC12513.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()];
        DozeSensors dozeSensors = this.mDozeSensors;
        switch (i) {
            case 1:
                this.mAodInterruptRunnable = null;
                sWakeDisplaySensorState = true;
                registerCallbacks();
                dozeSensors.getClass();
                dozeSensors.mDebounceFrom = SystemClock.uptimeMillis();
                break;
            case 2:
                this.mAodInterruptRunnable = null;
                this.mWantProxSensor = false;
                this.mWantSensors = true;
                this.mWantTouchScreenSensors = true;
                this.mInAod = false;
                break;
            case 3:
                this.mAodInterruptRunnable = null;
                this.mWantProxSensor = true;
                this.mWantSensors = true;
                this.mWantTouchScreenSensors = true;
                this.mInAod = true;
                if (!sWakeDisplaySensorState) {
                    onWakeScreen(false, state2, 7);
                    break;
                }
                break;
            case 4:
            case 5:
                this.mWantProxSensor = true;
                break;
            case 6:
            case 7:
                this.mWantProxSensor = true;
                this.mWantTouchScreenSensors = false;
                break;
            case 8:
                this.mWantProxSensor = false;
                this.mWantTouchScreenSensors = false;
                break;
            case 9:
                dozeSensors.getClass();
                dozeSensors.mDebounceFrom = SystemClock.uptimeMillis();
                break;
            case 10:
            case 11:
                TriggerReceiver triggerReceiver = this.mBroadcastReceiver;
                if (triggerReceiver.mRegistered) {
                    this.mBroadcastDispatcher.unregisterReceiver(triggerReceiver);
                    triggerReceiver.mRegistered = false;
                }
                DozeServiceHost dozeServiceHost = (DozeServiceHost) this.mDozeHost;
                dozeServiceHost.getClass();
                Assert.isMainThread();
                dozeServiceHost.mCallbacks.remove(this.mHostCallback);
                this.mDockManager.getClass();
                ((UserTrackerImpl) this.mUserTracker).removeCallback(this.mUserChangedCallback);
                if (dozeSensors.mListening || dozeSensors.mListeningTouchScreenSensors || dozeSensors.mListeningAodOnlySensors) {
                    dozeSensors.mListening = false;
                    dozeSensors.mListeningTouchScreenSensors = false;
                    dozeSensors.mListeningAodOnlySensors = false;
                    dozeSensors.updateListening();
                }
                dozeSensors.setProxListening(false);
                this.mWantSensors = false;
                this.mWantProxSensor = false;
                this.mWantTouchScreenSensors = false;
                this.mInAod = false;
                break;
        }
        boolean z = this.mWantSensors;
        boolean z2 = this.mWantTouchScreenSensors;
        boolean z3 = this.mInAod;
        if (dozeSensors.mListening == z && dozeSensors.mListeningTouchScreenSensors == z2 && dozeSensors.mListeningAodOnlySensors == z3) {
            return;
        }
        dozeSensors.mListening = z;
        dozeSensors.mListeningTouchScreenSensors = z2;
        dozeSensors.mListeningAodOnlySensors = z3;
        dozeSensors.updateListening();
    }
}
