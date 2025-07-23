package com.android.systemui.doze;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.IndentingPrintWriter;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.util.settings.SystemSettings;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class DozeScreenBrightness extends BroadcastReceiver implements DozeMachine.Part, SensorEventListener {
    public final int mDefaultDozeBrightness;
    public final float mDefaultDozeBrightnessFloat;
    public int mDevicePosture;
    public final DevicePostureController mDevicePostureController;
    public final DisplayManager mDisplayManager;
    public final DozeHost mDozeHost;
    public final DozeLog mDozeLog;
    public final DozeParameters mDozeParameters;
    public final DozeMachine.Service mDozeService;
    public final Handler mHandler;
    public final Optional[] mLightSensorOptional;
    public boolean mRegistered;
    public final int mScreenBrightnessDim;
    public final float mScreenBrightnessDimFloat;
    public final float mScreenBrightnessMinimumDimAmountFloat;
    public final AsyncSensorManager mSensorManager;
    public final int[] mSensorToBrightness;
    public final float[] mSensorToBrightnessFloat;
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
                Sensor sensor = (Sensor) optionalArr[i2].orElse(null);
                Sensor sensor2 = (Sensor) dozeScreenBrightness.mLightSensorOptional[i].orElse(null);
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
                dozeScreenBrightness.mDozeLog.tracePostureChanged(dozeScreenBrightness.mDevicePosture, "DozeScreenBrightness swap {" + sensor + "} => {" + sensor2 + "}, mRegistered=" + dozeScreenBrightness.mRegistered);
            }
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public DozeScreenBrightness(Context context, DozeMachine.Service service, AsyncSensorManager asyncSensorManager, Optional<Sensor>[] optionalArr, DozeHost dozeHost, Handler handler, AlwaysOnDisplayPolicy alwaysOnDisplayPolicy, WakefulnessLifecycle wakefulnessLifecycle, DozeParameters dozeParameters, DevicePostureController devicePostureController, DozeLog dozeLog, SystemSettings systemSettings, DisplayManager displayManager) {
        this.mDozeService = service;
        this.mSensorManager = asyncSensorManager;
        this.mDisplayManager = displayManager;
        this.mLightSensorOptional = optionalArr;
        this.mDevicePostureController = devicePostureController;
        this.mDevicePosture = ((DevicePostureControllerImpl) devicePostureController).getDevicePosture();
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mDozeParameters = dozeParameters;
        this.mDozeHost = dozeHost;
        this.mHandler = handler;
        this.mDozeLog = dozeLog;
        this.mSystemSettings = systemSettings;
        this.mScreenBrightnessMinimumDimAmountFloat = context.getResources().getFloat(R.dimen.date_picker_day_width);
        this.mDefaultDozeBrightness = alwaysOnDisplayPolicy.defaultDozeBrightness;
        this.mDefaultDozeBrightnessFloat = displayManager.getDefaultDozeBrightness(context.getDisplayId());
        this.mScreenBrightnessDim = alwaysOnDisplayPolicy.dimBrightness;
        this.mScreenBrightnessDimFloat = alwaysOnDisplayPolicy.dimBrightnessFloat;
        this.mSensorToBrightness = alwaysOnDisplayPolicy.screenBrightnessArray;
        this.mSensorToBrightnessFloat = displayManager.getDozeBrightnessSensorValueToBrightness(context.getDisplayId());
        this.mSensorToScrimOpacity = alwaysOnDisplayPolicy.dimmingScrimArray;
    }

    public final int clampToDimBrightnessForScreenOff(int i) {
        return ((this.mDozeParameters.shouldClampToDimBrightness() || this.mWakefulnessLifecycle.mWakefulness == 3) && this.mState == DozeMachine.State.INITIALIZED && this.mWakefulnessLifecycle.mLastSleepReason == 2) ? Math.max(0, Math.min(i - ((int) Math.floor(this.mScreenBrightnessMinimumDimAmountFloat * 255.0f)), this.mScreenBrightnessDim)) : i;
    }

    public final float clampToDimBrightnessForScreenOffFloat(float f) {
        return ((this.mDozeParameters.shouldClampToDimBrightness() || this.mWakefulnessLifecycle.mWakefulness == 3) && this.mState == DozeMachine.State.INITIALIZED && this.mWakefulnessLifecycle.mLastSleepReason == 2) ? Math.max(0.0f, Math.min(f - this.mScreenBrightnessMinimumDimAmountFloat, this.mScreenBrightnessDimFloat)) : f;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void dump(PrintWriter printWriter) {
        printWriter.println("DozeScreenBrightness:");
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("registered=" + this.mRegistered);
        indentingPrintWriter.println("posture=" + DevicePostureController.devicePostureToString(this.mDevicePosture));
        indentingPrintWriter.println("sensorToBrightness=" + Arrays.toString(this.mSensorToBrightness));
        indentingPrintWriter.println("sensorToBrightnessFloat=" + Arrays.toString(this.mSensorToBrightnessFloat));
        indentingPrintWriter.println("sensorToScrimOpacity=" + Arrays.toString(this.mSensorToScrimOpacity));
        indentingPrintWriter.println("screenBrightnessDim=" + this.mScreenBrightnessDim);
        indentingPrintWriter.println("screenBrightnessDimFloat=" + this.mScreenBrightnessDimFloat);
        indentingPrintWriter.println("mDefaultDozeBrightness=" + this.mDefaultDozeBrightness);
        indentingPrintWriter.println("mDefaultDozeBrightnessFloat=" + this.mDefaultDozeBrightnessFloat);
        indentingPrintWriter.println("mLastSensorValue=" + this.mLastSensorValue);
        StringBuilder sb = new StringBuilder("shouldUseFloatBrightness()=");
        sb.append(this.mSensorToBrightnessFloat != null);
        indentingPrintWriter.println(sb.toString());
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
        if (this.mSensorToBrightnessFloat != null) {
            this.mDozeService.setDozeScreenBrightnessFloat(clampToDimBrightnessForScreenOffFloat(Math.min(this.mDefaultDozeBrightnessFloat, this.mDisplayManager.getBrightness(0))));
        } else {
            this.mDozeService.setDozeScreenBrightness(clampToDimBrightnessForScreenOff(Math.min(this.mDefaultDozeBrightness, this.mSystemSettings.getIntForUser("screen_brightness", Integer.MAX_VALUE, -2))));
        }
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

    /* JADX WARN: Removed duplicated region for block: B:48:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0061  */
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
            if (r10 == r0) goto La6
        Lb:
            int r10 = r9.mDebugBrightnessBucket
            if (r10 != r0) goto L11
            int r10 = r9.mLastSensorValue
        L11:
            float[] r1 = r9.mSensorToBrightnessFloat
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L19
            r4 = r2
            goto L1a
        L19:
            r4 = r3
        L1a:
            r5 = -2
            java.lang.String r6 = "screen_brightness_mode"
            if (r4 == 0) goto L51
            if (r10 < 0) goto L28
            int r4 = r1.length
            if (r10 < r4) goto L25
            goto L28
        L25:
            r1 = r1[r10]
            goto L2a
        L28:
            r1 = -1082130432(0xffffffffbf800000, float:-1.0)
        L2a:
            r4 = 0
            int r4 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r4 < 0) goto L31
            r4 = r2
            goto L32
        L31:
            r4 = r3
        L32:
            if (r4 == 0) goto L85
            com.android.systemui.doze.DozeMachine$Service r7 = r9.mDozeService
            com.android.systemui.util.settings.SystemSettings r8 = r9.mSystemSettings
            int r5 = r8.getIntForUser(r6, r3, r5)
            if (r5 != r2) goto L3f
            goto L49
        L3f:
            android.hardware.display.DisplayManager r2 = r9.mDisplayManager
            float r2 = r2.getBrightness(r3)
            float r1 = java.lang.Math.min(r1, r2)
        L49:
            float r1 = r9.clampToDimBrightnessForScreenOffFloat(r1)
            r7.setDozeScreenBrightnessFloat(r1)
            goto L85
        L51:
            if (r10 < 0) goto L5c
            int[] r1 = r9.mSensorToBrightness
            int r4 = r1.length
            if (r10 < r4) goto L59
            goto L5c
        L59:
            r1 = r1[r10]
            goto L5d
        L5c:
            r1 = r0
        L5d:
            if (r1 <= 0) goto L61
            r4 = r2
            goto L62
        L61:
            r4 = r3
        L62:
            if (r4 == 0) goto L85
            com.android.systemui.doze.DozeMachine$Service r7 = r9.mDozeService
            com.android.systemui.util.settings.SystemSettings r8 = r9.mSystemSettings
            int r6 = r8.getIntForUser(r6, r3, r5)
            if (r6 != r2) goto L6f
            goto L7e
        L6f:
            com.android.systemui.util.settings.SystemSettings r2 = r9.mSystemSettings
            r6 = 2147483647(0x7fffffff, float:NaN)
            java.lang.String r8 = "screen_brightness"
            int r2 = r2.getIntForUser(r8, r6, r5)
            int r1 = java.lang.Math.min(r1, r2)
        L7e:
            int r1 = r9.clampToDimBrightnessForScreenOff(r1)
            r7.setDozeScreenBrightness(r1)
        L85:
            boolean r1 = r9.isLightSensorPresent()
            if (r1 != 0) goto L8d
            r0 = r3
            goto L99
        L8d:
            if (r4 == 0) goto L99
            if (r10 < 0) goto L99
            int[] r1 = r9.mSensorToScrimOpacity
            int r2 = r1.length
            if (r10 < r2) goto L97
            goto L99
        L97:
            r0 = r1[r10]
        L99:
            if (r0 < 0) goto La6
            com.android.systemui.doze.DozeHost r9 = r9.mDozeHost
            float r10 = (float) r0
            r0 = 1132396544(0x437f0000, float:255.0)
            float r10 = r10 / r0
            com.android.systemui.statusbar.phone.DozeServiceHost r9 = (com.android.systemui.statusbar.phone.DozeServiceHost) r9
            r9.setAodDimmingScrim(r10)
        La6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.doze.DozeScreenBrightness.updateBrightnessAndReady(boolean):void");
    }

    @Override // android.hardware.SensorEventListener
    public final void onAccuracyChanged(Sensor sensor, int i) {
    }
}
