package com.android.systemui.doze;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.IndentingPrintWriter;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.util.settings.SystemSettings;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class DozeScreenBrightness extends BroadcastReceiver implements DozeMachine.Part, SensorEventListener {
    public final int mDefaultDozeBrightness;
    public int mDevicePosture;
    public final DevicePostureController mDevicePostureController;
    public final DozeHost mDozeHost;
    public final DozeLog mDozeLog;
    public final DozeParameters mDozeParameters;
    public final DozeMachine.Service mDozeService;
    public final Handler mHandler;
    public final Optional[] mLightSensorOptional;
    public boolean mRegistered;
    public final int mScreenBrightnessDim;
    public final float mScreenBrightnessMinimumDimAmountFloat;
    public final AsyncSensorManager mSensorManager;
    public final int[] mSensorToBrightness;
    public final int[] mSensorToScrimOpacity;
    public final SystemSettings mSystemSettings;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public boolean mPaused = false;
    public boolean mScreenOff = false;
    public int mLastSensorValue = -1;
    public DozeMachine.State mState = DozeMachine.State.UNINITIALIZED;
    public int mDebugBrightnessBucket = -1;
    public final AnonymousClass1 mDevicePostureCallback = new DevicePostureController.Callback() { // from class: com.android.systemui.doze.DozeScreenBrightness.1
        @Override // com.android.systemui.statusbar.policy.DevicePostureController.Callback
        public final void onPostureChanged(int i) {
            DozeScreenBrightness dozeScreenBrightness = DozeScreenBrightness.this;
            int i2 = dozeScreenBrightness.mDevicePosture;
            if (i2 != i) {
                Optional[] optionalArr = dozeScreenBrightness.mLightSensorOptional;
                if (optionalArr.length < 2 || i >= optionalArr.length) {
                    return;
                }
                Sensor sensor = (Sensor) optionalArr[i2].get();
                Sensor sensor2 = (Sensor) dozeScreenBrightness.mLightSensorOptional[i].get();
                if (Objects.equals(sensor, sensor2)) {
                    dozeScreenBrightness.mDevicePosture = i;
                    return;
                }
                if (dozeScreenBrightness.mRegistered) {
                    dozeScreenBrightness.setLightSensorEnabled(false);
                    dozeScreenBrightness.mDevicePosture = i;
                    dozeScreenBrightness.setLightSensorEnabled(true);
                } else {
                    dozeScreenBrightness.mDevicePosture = i;
                }
                DozeLog dozeLog = dozeScreenBrightness.mDozeLog;
                int i3 = dozeScreenBrightness.mDevicePosture;
                String str = "DozeScreenBrightness swap {" + sensor + "} => {" + sensor2 + "}, mRegistered=" + dozeScreenBrightness.mRegistered;
                DozeLogger dozeLogger = dozeLog.mLogger;
                dozeLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                DozeLogger$logPostureChanged$2 dozeLogger$logPostureChanged$2 = DozeLogger$logPostureChanged$2.INSTANCE;
                LogBuffer logBuffer = dozeLogger.buffer;
                LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logPostureChanged$2, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.int1 = i3;
                logMessageImpl.str1 = str;
                logBuffer.commit(obtain);
            }
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.doze.DozeScreenBrightness$2, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_REQUEST_PULSE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_DOCKED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_SUSPEND_TRIGGERS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    static {
        SystemProperties.getBoolean("debug.aod_brightness", false);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.doze.DozeScreenBrightness$1] */
    public DozeScreenBrightness(Context context, DozeMachine.Service service, AsyncSensorManager asyncSensorManager, Optional<Sensor>[] optionalArr, DozeHost dozeHost, Handler handler, AlwaysOnDisplayPolicy alwaysOnDisplayPolicy, WakefulnessLifecycle wakefulnessLifecycle, DozeParameters dozeParameters, DevicePostureController devicePostureController, DozeLog dozeLog, SystemSettings systemSettings) {
        this.mDozeService = service;
        this.mSensorManager = asyncSensorManager;
        this.mLightSensorOptional = optionalArr;
        this.mDevicePostureController = devicePostureController;
        this.mDevicePosture = ((DevicePostureControllerImpl) devicePostureController).getDevicePosture();
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mDozeParameters = dozeParameters;
        this.mDozeHost = dozeHost;
        this.mHandler = handler;
        this.mDozeLog = dozeLog;
        this.mSystemSettings = systemSettings;
        this.mScreenBrightnessMinimumDimAmountFloat = context.getResources().getFloat(R.dimen.conversation_avatar_size_group_expanded);
        this.mDefaultDozeBrightness = alwaysOnDisplayPolicy.defaultDozeBrightness;
        this.mScreenBrightnessDim = alwaysOnDisplayPolicy.dimBrightness;
        this.mSensorToBrightness = alwaysOnDisplayPolicy.screenBrightnessArray;
        this.mSensorToScrimOpacity = alwaysOnDisplayPolicy.dimmingScrimArray;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x003f, code lost:
    
        if (r2.mWakefulnessLifecycle.mLastSleepReason != 2) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0058, code lost:
    
        return java.lang.Math.max(0, java.lang.Math.min(r3 - ((int) java.lang.Math.floor(r2.mScreenBrightnessMinimumDimAmountFloat * 255.0f)), r2.mScreenBrightnessDim));
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0059, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0032, code lost:
    
        if (r2.mWakefulnessLifecycle.mWakefulness == 3) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0038, code lost:
    
        if (r2.mState != com.android.systemui.doze.DozeMachine.State.INITIALIZED) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int clampToDimBrightnessForScreenOff(int r3) {
        /*
            r2 = this;
            com.android.systemui.statusbar.phone.DozeParameters r0 = r2.mDozeParameters
            com.android.systemui.statusbar.phone.ScreenOffAnimationController r0 = r0.mScreenOffAnimationController
            java.util.List r0 = r0.animations
            boolean r1 = r0 instanceof java.util.Collection
            if (r1 == 0) goto L14
            r1 = r0
            java.util.ArrayList r1 = (java.util.ArrayList) r1
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L14
            goto L2d
        L14:
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            java.util.Iterator r0 = r0.iterator()
        L1a:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L2d
            java.lang.Object r1 = r0.next()
            com.android.systemui.statusbar.phone.ScreenOffAnimation r1 = (com.android.systemui.statusbar.phone.ScreenOffAnimation) r1
            boolean r1 = r1.shouldPlayAnimation()
            if (r1 == 0) goto L1a
            goto L34
        L2d:
            com.android.systemui.keyguard.WakefulnessLifecycle r0 = r2.mWakefulnessLifecycle
            int r0 = r0.mWakefulness
            r1 = 3
            if (r0 != r1) goto L59
        L34:
            com.android.systemui.doze.DozeMachine$State r0 = r2.mState
            com.android.systemui.doze.DozeMachine$State r1 = com.android.systemui.doze.DozeMachine.State.INITIALIZED
            if (r0 != r1) goto L59
            com.android.systemui.keyguard.WakefulnessLifecycle r0 = r2.mWakefulnessLifecycle
            int r0 = r0.mLastSleepReason
            r1 = 2
            if (r0 != r1) goto L59
            float r0 = r2.mScreenBrightnessMinimumDimAmountFloat
            r1 = 1132396544(0x437f0000, float:255.0)
            float r0 = r0 * r1
            double r0 = (double) r0
            double r0 = java.lang.Math.floor(r0)
            int r0 = (int) r0
            int r3 = r3 - r0
            int r2 = r2.mScreenBrightnessDim
            int r2 = java.lang.Math.min(r3, r2)
            r3 = 0
            int r2 = java.lang.Math.max(r3, r2)
            return r2
        L59:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.doze.DozeScreenBrightness.clampToDimBrightnessForScreenOff(int):int");
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void dump(PrintWriter printWriter) {
        printWriter.println("DozeScreenBrightness:");
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("registered=" + this.mRegistered);
        indentingPrintWriter.println("posture=" + DevicePostureController.devicePostureToString(this.mDevicePosture));
    }

    public final boolean isLightSensorPresent() {
        int i;
        Optional[] optionalArr = this.mLightSensorOptional;
        return (optionalArr == null || (i = this.mDevicePosture) >= optionalArr.length) ? optionalArr != null && optionalArr[0].isPresent() : optionalArr[i].isPresent();
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        this.mDebugBrightnessBucket = intent.getIntExtra("brightness_bucket", -1);
        updateBrightnessAndReady(false);
    }

    @Override // android.hardware.SensorEventListener
    public final void onSensorChanged(SensorEvent sensorEvent) {
        if (Trace.isEnabled()) {
            Trace.traceBegin(4096L, "DozeScreenBrightness.onSensorChanged" + sensorEvent.values[0]);
        }
        try {
            if (this.mRegistered) {
                this.mLastSensorValue = (int) sensorEvent.values[0];
                updateBrightnessAndReady(false);
            }
        } finally {
            Trace.endSection();
        }
    }

    public void resetBrightnessToDefault() {
        DozeMachine.Service service = this.mDozeService;
        int i = this.mDefaultDozeBrightness;
        if (this.mSystemSettings.getIntForUser("screen_brightness_mode", 0, -2) != 1) {
            i = Math.min(i, this.mSystemSettings.getIntForUser("screen_brightness", Integer.MAX_VALUE, -2));
        }
        service.setDozeScreenBrightness(clampToDimBrightnessForScreenOff(i));
        ((DozeServiceHost) this.mDozeHost).setAodDimmingScrim(0.0f);
    }

    public final void setLightSensorEnabled(boolean z) {
        int i;
        if (z && !this.mRegistered && isLightSensorPresent()) {
            AsyncSensorManager asyncSensorManager = this.mSensorManager;
            Optional[] optionalArr = this.mLightSensorOptional;
            this.mRegistered = asyncSensorManager.registerListener(this, (optionalArr == null || (i = this.mDevicePosture) >= optionalArr.length) ? null : (Sensor) optionalArr[i].get(), 3, this.mHandler);
            this.mLastSensorValue = -1;
            return;
        }
        if (z || !this.mRegistered) {
            return;
        }
        this.mSensorManager.unregisterListener(this);
        this.mRegistered = false;
        this.mLastSensorValue = -1;
    }

    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        this.mState = state2;
        switch (AnonymousClass2.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()]) {
            case 1:
                resetBrightnessToDefault();
                break;
            case 2:
            case 3:
            case 4:
                setLightSensorEnabled(true);
                break;
            case 5:
            case 6:
                setLightSensorEnabled(false);
                resetBrightnessToDefault();
                break;
            case 7:
                setLightSensorEnabled(false);
                break;
            case 8:
                setLightSensorEnabled(false);
                ((DevicePostureControllerImpl) this.mDevicePostureController).removeCallback(this.mDevicePostureCallback);
                break;
        }
        if (state2 != DozeMachine.State.FINISH) {
            boolean z = state2 == DozeMachine.State.DOZE;
            if (this.mScreenOff != z) {
                this.mScreenOff = z;
                updateBrightnessAndReady(true);
            }
            boolean z2 = state2 == DozeMachine.State.DOZE_AOD_PAUSED;
            if (this.mPaused != z2) {
                this.mPaused = z2;
                updateBrightnessAndReady(false);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0021  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateBrightnessAndReady(boolean r10) {
        /*
            r9 = this;
            r0 = -1
            if (r10 != 0) goto Lb
            boolean r10 = r9.mRegistered
            if (r10 != 0) goto Lb
            int r10 = r9.mDebugBrightnessBucket
            if (r10 == r0) goto L6d
        Lb:
            int r10 = r9.mDebugBrightnessBucket
            if (r10 != r0) goto L11
            int r10 = r9.mLastSensorValue
        L11:
            if (r10 < 0) goto L1c
            int[] r1 = r9.mSensorToBrightness
            int r2 = r1.length
            if (r10 < r2) goto L19
            goto L1c
        L19:
            r1 = r1[r10]
            goto L1d
        L1c:
            r1 = r0
        L1d:
            r2 = 1
            r3 = 0
            if (r1 <= 0) goto L23
            r4 = r2
            goto L24
        L23:
            r4 = r3
        L24:
            if (r4 == 0) goto L4c
            com.android.systemui.doze.DozeMachine$Service r5 = r9.mDozeService
            com.android.systemui.util.settings.SystemSettings r6 = r9.mSystemSettings
            java.lang.String r7 = "screen_brightness_mode"
            r8 = -2
            int r6 = r6.getIntForUser(r7, r3, r8)
            if (r6 != r2) goto L35
            goto L45
        L35:
            com.android.systemui.util.settings.SystemSettings r2 = r9.mSystemSettings
            java.lang.String r6 = "screen_brightness"
            r7 = 2147483647(0x7fffffff, float:NaN)
            int r2 = r2.getIntForUser(r6, r7, r8)
            int r1 = java.lang.Math.min(r1, r2)
        L45:
            int r1 = r9.clampToDimBrightnessForScreenOff(r1)
            r5.setDozeScreenBrightness(r1)
        L4c:
            boolean r1 = r9.isLightSensorPresent()
            if (r1 != 0) goto L54
            r0 = r3
            goto L60
        L54:
            if (r4 == 0) goto L60
            if (r10 < 0) goto L60
            int[] r1 = r9.mSensorToScrimOpacity
            int r2 = r1.length
            if (r10 < r2) goto L5e
            goto L60
        L5e:
            r0 = r1[r10]
        L60:
            if (r0 < 0) goto L6d
            com.android.systemui.doze.DozeHost r9 = r9.mDozeHost
            float r10 = (float) r0
            r0 = 1132396544(0x437f0000, float:255.0)
            float r10 = r10 / r0
            com.android.systemui.statusbar.phone.DozeServiceHost r9 = (com.android.systemui.statusbar.phone.DozeServiceHost) r9
            r9.setAodDimmingScrim(r10)
        L6d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.doze.DozeScreenBrightness.updateBrightnessAndReady(boolean):void");
    }

    @Override // android.hardware.SensorEventListener
    public final void onAccuracyChanged(Sensor sensor, int i) {
    }
}
