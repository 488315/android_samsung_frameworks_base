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
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.ScreenOffAnimation;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.util.settings.SystemSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
    public final C12421 mDevicePostureCallback = new DevicePostureController.Callback() { // from class: com.android.systemui.doze.DozeScreenBrightness.1
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
                obtain.setInt1(i3);
                obtain.setStr1(str);
                logBuffer.commit(obtain);
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.doze.DozeScreenBrightness$2 */
    public abstract /* synthetic */ class AbstractC12432 {
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
        this.mDevicePosture = ((DevicePostureControllerImpl) devicePostureController).mCurrentDevicePosture;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mDozeParameters = dozeParameters;
        this.mDozeHost = dozeHost;
        this.mHandler = handler;
        this.mDozeLog = dozeLog;
        this.mSystemSettings = systemSettings;
        this.mScreenBrightnessMinimumDimAmountFloat = context.getResources().getFloat(R.dimen.config_minScrollbarTouchTarget);
        this.mDefaultDozeBrightness = alwaysOnDisplayPolicy.defaultDozeBrightness;
        this.mScreenBrightnessDim = alwaysOnDisplayPolicy.dimBrightness;
        this.mSensorToBrightness = alwaysOnDisplayPolicy.screenBrightnessArray;
        this.mSensorToScrimOpacity = alwaysOnDisplayPolicy.dimmingScrimArray;
    }

    public final int clampToDimBrightnessForScreenOff(int i) {
        boolean z;
        List list = this.mDozeParameters.mScreenOffAnimationController.animations;
        if (!(list instanceof Collection) || !((ArrayList) list).isEmpty()) {
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                if (((ScreenOffAnimation) it.next()).shouldPlayAnimation()) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        return (((z || this.mWakefulnessLifecycle.mWakefulness == 3) && this.mState == DozeMachine.State.INITIALIZED) && this.mWakefulnessLifecycle.mLastSleepReason == 2) ? Math.max(0, Math.min(i - ((int) Math.floor(this.mScreenBrightnessMinimumDimAmountFloat * 255.0f)), this.mScreenBrightnessDim)) : i;
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
        Optional[] optionalArr = this.mLightSensorOptional;
        return !(optionalArr != null && this.mDevicePosture < optionalArr.length) ? optionalArr != null && optionalArr[0].isPresent() : optionalArr[this.mDevicePosture].isPresent();
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
        this.mDozeService.setDozeScreenBrightness(clampToDimBrightnessForScreenOff(Math.min(this.mDefaultDozeBrightness, this.mSystemSettings.getIntForUser(Integer.MAX_VALUE, -2, "screen_brightness"))));
        ((DozeServiceHost) this.mDozeHost).setAodDimmingScrim(0.0f);
    }

    public final void setLightSensorEnabled(boolean z) {
        boolean z2 = false;
        if (!z || this.mRegistered || !isLightSensorPresent()) {
            if (z || !this.mRegistered) {
                return;
            }
            this.mSensorManager.unregisterListener(this);
            this.mRegistered = false;
            this.mLastSensorValue = -1;
            return;
        }
        AsyncSensorManager asyncSensorManager = this.mSensorManager;
        Optional[] optionalArr = this.mLightSensorOptional;
        if (optionalArr != null && this.mDevicePosture < optionalArr.length) {
            z2 = true;
        }
        this.mRegistered = asyncSensorManager.registerListener(this, !z2 ? null : (Sensor) optionalArr[this.mDevicePosture].get(), 3, this.mHandler);
        this.mLastSensorValue = -1;
    }

    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        this.mState = state2;
        switch (AbstractC12432.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()]) {
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

    /* JADX WARN: Removed duplicated region for block: B:18:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateBrightnessAndReady(boolean z) {
        int i;
        boolean z2;
        int i2 = -1;
        if (!z && !this.mRegistered && this.mDebugBrightnessBucket == -1) {
            return;
        }
        int i3 = this.mDebugBrightnessBucket;
        if (i3 == -1) {
            i3 = this.mLastSensorValue;
        }
        if (i3 >= 0) {
            int[] iArr = this.mSensorToBrightness;
            if (i3 < iArr.length) {
                i = iArr[i3];
                z2 = i <= 0;
                if (z2) {
                    this.mDozeService.setDozeScreenBrightness(clampToDimBrightnessForScreenOff(Math.min(i, this.mSystemSettings.getIntForUser(Integer.MAX_VALUE, -2, "screen_brightness"))));
                }
                if (isLightSensorPresent()) {
                    i2 = 0;
                } else if (z2 && i3 >= 0) {
                    int[] iArr2 = this.mSensorToScrimOpacity;
                    if (i3 < iArr2.length) {
                        i2 = iArr2[i3];
                    }
                }
                if (i2 < 0) {
                    ((DozeServiceHost) this.mDozeHost).setAodDimmingScrim(i2 / 255.0f);
                    return;
                }
                return;
            }
        }
        i = -1;
        if (i <= 0) {
        }
        if (z2) {
        }
        if (isLightSensorPresent()) {
        }
        if (i2 < 0) {
        }
    }

    @Override // android.hardware.SensorEventListener
    public final void onAccuracyChanged(Sensor sensor, int i) {
    }
}
