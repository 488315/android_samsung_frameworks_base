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

    /* JADX WARN: Removed duplicated region for block: B:38:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateBrightnessAndReady(boolean z) {
        boolean z2;
        int i = -1;
        if (z || this.mRegistered || this.mDebugBrightnessBucket != -1) {
            int i2 = this.mDebugBrightnessBucket;
            if (i2 == -1) {
                i2 = this.mLastSensorValue;
            }
            float[] fArr = this.mSensorToBrightnessFloat;
            if (fArr != null) {
                float fMin = (i2 < 0 || i2 >= fArr.length) ? -1.0f : fArr[i2];
                z2 = fMin >= 0.0f;
                if (z2) {
                    DozeMachine.Service service = this.mDozeService;
                    if (this.mSystemSettings.getIntForUser("screen_brightness_mode", 0, -2) != 1) {
                        fMin = Math.min(fMin, this.mDisplayManager.getBrightness(0));
                    }
                    service.setDozeScreenBrightnessFloat(clampToDimBrightnessForScreenOffFloat(fMin));
                }
            } else if (i2 >= 0) {
                int[] iArr = this.mSensorToBrightness;
                int iMin = i2 >= iArr.length ? -1 : iArr[i2];
                z2 = iMin > 0;
                if (z2) {
                    DozeMachine.Service service2 = this.mDozeService;
                    if (this.mSystemSettings.getIntForUser("screen_brightness_mode", 0, -2) != 1) {
                        iMin = Math.min(iMin, this.mSystemSettings.getIntForUser("screen_brightness", Integer.MAX_VALUE, -2));
                    }
                    service2.setDozeScreenBrightness(clampToDimBrightnessForScreenOff(iMin));
                }
            }
            if (!isLightSensorPresent()) {
                i = 0;
            } else if (z2 && i2 >= 0) {
                int[] iArr2 = this.mSensorToScrimOpacity;
                if (i2 < iArr2.length) {
                    i = iArr2[i2];
                }
            }
            if (i >= 0) {
                ((DozeServiceHost) this.mDozeHost).setAodDimmingScrim(i / 255.0f);
            }
        }
    }

    @Override // android.hardware.SensorEventListener
    public final void onAccuracyChanged(Sensor sensor, int i) {
    }
}
