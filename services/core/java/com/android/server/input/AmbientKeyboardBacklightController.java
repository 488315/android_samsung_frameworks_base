package com.android.server.input;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Slog;
import android.util.TypedValue;
import android.view.DisplayInfo;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.display.utils.SensorUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class AmbientKeyboardBacklightController
        implements DisplayManager.DisplayListener, SensorEventListener {
    public static final int HYSTERESIS_THRESHOLD = 2;
    public final BrightnessStep[] mBrightnessSteps;
    public final Context mContext;
    public int mCurrentBrightnessStepIndex;
    public String mCurrentDefaultDisplayUniqueId;
    public final Handler mHandler;
    public HysteresisState mHysteresisState;
    public Sensor mLightSensor;
    public int mSmoothedLux;
    public int mSmoothedLuxAtLastAdjustment;
    public final float mSmoothingConstant;
    public static final boolean DEBUG = Log.isLoggable("KbdBacklightController", 3);
    public static final Object sAmbientControllerLock = new Object();
    public final List mAmbientKeyboardBacklightListeners = new ArrayList();
    public int mHysteresisCount = 0;

    public final class BrightnessStep {
        public final int mBrightnessValue;
        public final int mDecreaseLuxThreshold;
        public final int mIncreaseLuxThreshold;

        public BrightnessStep(int i, int i2, int i3) {
            this.mBrightnessValue = i;
            this.mIncreaseLuxThreshold = i2;
            this.mDecreaseLuxThreshold = i3;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("BrightnessStep{mBrightnessValue=");
            sb.append(this.mBrightnessValue);
            sb.append(", mIncreaseThreshold=");
            sb.append(this.mIncreaseLuxThreshold);
            sb.append(", mDecreaseThreshold=");
            return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                    sb, this.mDecreaseLuxThreshold, '}');
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class HysteresisState {
        public static final /* synthetic */ HysteresisState[] $VALUES;
        public static final HysteresisState DECREASING;
        public static final HysteresisState IMMEDIATE;
        public static final HysteresisState INCREASING;
        public static final HysteresisState STABLE;

        static {
            HysteresisState hysteresisState = new HysteresisState("STABLE", 0);
            STABLE = hysteresisState;
            HysteresisState hysteresisState2 = new HysteresisState("DECREASING", 1);
            DECREASING = hysteresisState2;
            HysteresisState hysteresisState3 = new HysteresisState("INCREASING", 2);
            INCREASING = hysteresisState3;
            HysteresisState hysteresisState4 = new HysteresisState("IMMEDIATE", 3);
            IMMEDIATE = hysteresisState4;
            $VALUES =
                    new HysteresisState[] {
                        hysteresisState, hysteresisState2, hysteresisState3, hysteresisState4
                    };
        }

        public static HysteresisState valueOf(String str) {
            return (HysteresisState) Enum.valueOf(HysteresisState.class, str);
        }

        public static HysteresisState[] values() {
            return (HysteresisState[]) $VALUES.clone();
        }
    }

    public AmbientKeyboardBacklightController(Context context, Looper looper) {
        this.mContext = context;
        this.mHandler =
                new Handler(
                        looper,
                        new Handler
                                .Callback() { // from class:
                                              // com.android.server.input.AmbientKeyboardBacklightController$$ExternalSyntheticLambda0
                            @Override // android.os.Handler.Callback
                            public final boolean handleMessage(Message message) {
                                AmbientKeyboardBacklightController
                                        ambientKeyboardBacklightController =
                                                AmbientKeyboardBacklightController.this;
                                ambientKeyboardBacklightController.getClass();
                                int i = message.what;
                                if (i != 0) {
                                    if (i != 1) {
                                        return false;
                                    }
                                    ambientKeyboardBacklightController.handleDisplayChange();
                                    return true;
                                }
                                int intValue = ((Integer) message.obj).intValue();
                                synchronized (
                                        AmbientKeyboardBacklightController.sAmbientControllerLock) {
                                    try {
                                        Iterator it =
                                                ((ArrayList)
                                                                ambientKeyboardBacklightController
                                                                        .mAmbientKeyboardBacklightListeners)
                                                        .iterator();
                                        while (it.hasNext()) {
                                            ((KeyboardBacklightController$$ExternalSyntheticLambda1)
                                                            it.next())
                                                    .f$0.handleAmbientLightValueChanged(intValue);
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                                return true;
                            }
                        });
        Resources resources = context.getResources();
        int[] intArray =
                resources.getIntArray(
                        R.array.config_nightDisplayColorTemperatureCoefficientsNative);
        int[] intArray2 = resources.getIntArray(R.array.config_nonPreemptibleInputMethods);
        int[] intArray3 = resources.getIntArray(R.array.config_notificationFallbackVibePattern);
        if (intArray.length != intArray2.length || intArray2.length != intArray3.length) {
            throw new IllegalArgumentException(
                    "The config files for auto keyboard backlight brightness must contain arrays of"
                        + " equal lengths");
        }
        int length = intArray.length;
        this.mBrightnessSteps = new BrightnessStep[length];
        int i = 0;
        while (true) {
            int i2 = Integer.MIN_VALUE;
            if (i >= length) {
                break;
            }
            int i3 = intArray3[i];
            int i4 = i3 >= 0 ? i3 : Integer.MAX_VALUE;
            int i5 = intArray2[i];
            if (i5 >= 0) {
                i2 = i5;
            }
            this.mBrightnessSteps[i] = new BrightnessStep(intArray[i], i4, i2);
            i++;
        }
        BrightnessStep[] brightnessStepArr = this.mBrightnessSteps;
        int length2 = brightnessStepArr.length;
        if (length2 == 0
                || brightnessStepArr[0].mDecreaseLuxThreshold != Integer.MIN_VALUE
                || brightnessStepArr[length2 - 1].mIncreaseLuxThreshold != Integer.MAX_VALUE) {
            throw new IllegalArgumentException(
                    "The config files for auto keyboard backlight brightness must contain arrays of"
                        + " length > 0 and have -1 or Integer.MIN_VALUE as lower bound for decrease"
                        + " thresholds and -1 or Integer.MAX_VALUE as upper bound for increase"
                        + " thresholds");
        }
        TypedValue typedValue = new TypedValue();
        resources.getValue(R.dimen.config_letterboxHorizontalPositionMultiplier, typedValue, true);
        float f = typedValue.getFloat();
        this.mSmoothingConstant = f;
        double d = f;
        if (d <= 0.0d || d > 1.0d) {
            throw new IllegalArgumentException(
                    "The config files for auto keyboard backlight brightness must contain smoothing"
                        + " constant in range (0.0, 1.0].");
        }
        if (DEBUG) {
            Log.d(
                    "KbdBacklightController",
                    "Brightness steps: "
                            + Arrays.toString(this.mBrightnessSteps)
                            + " Smoothing constant = "
                            + this.mSmoothingConstant);
        }
    }

    public void addSensorListener(Sensor sensor) {
        SensorManager sensorManager =
                (SensorManager) this.mContext.getSystemService(SensorManager.class);
        if (sensorManager == null || sensor == null) {
            return;
        }
        this.mHysteresisState = HysteresisState.IMMEDIATE;
        this.mSmoothedLux = 0;
        this.mSmoothedLuxAtLastAdjustment = 0;
        this.mCurrentBrightnessStepIndex = -1;
        sensorManager.registerListener(this, sensor, 3, this.mHandler);
        if (DEBUG) {
            Slog.d("KbdBacklightController", "Registering ALS listener");
        }
    }

    public final Sensor getAmbientLightSensor(
            DisplayManagerInternal.AmbientLightSensorData ambientLightSensorData) {
        SensorManager sensorManager =
                (SensorManager) this.mContext.getSystemService(SensorManager.class);
        Objects.requireNonNull(sensorManager);
        if (DEBUG) {
            Slog.d(
                    "KbdBacklightController",
                    "Ambient Light sensor data: " + ambientLightSensorData);
        }
        return SensorUtils.findSensor(
                sensorManager,
                ambientLightSensorData.sensorType,
                ambientLightSensorData.sensorName,
                5);
    }

    public final void handleDisplayChange() {
        DisplayManagerInternal displayManagerInternal =
                (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
        DisplayInfo displayInfo = displayManagerInternal.getDisplayInfo(0);
        if (displayInfo == null) {
            return;
        }
        synchronized (sAmbientControllerLock) {
            try {
                if (Objects.equals(this.mCurrentDefaultDisplayUniqueId, displayInfo.uniqueId)) {
                    return;
                }
                if (DEBUG) {
                    Slog.d(
                            "KbdBacklightController",
                            "Default display changed: resetting the light sensor");
                }
                this.mCurrentDefaultDisplayUniqueId = displayInfo.uniqueId;
                if (!((ArrayList) this.mAmbientKeyboardBacklightListeners).isEmpty()) {
                    removeSensorListener(this.mLightSensor);
                }
                this.mLightSensor =
                        getAmbientLightSensor(displayManagerInternal.getAmbientLightSensorData(0));
                if (!((ArrayList) this.mAmbientKeyboardBacklightListeners).isEmpty()) {
                    addSensorListener(this.mLightSensor);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.hardware.SensorEventListener
    public final void onAccuracyChanged(Sensor sensor, int i) {}

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayAdded(int i) {
        handleDisplayChange();
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayChanged(int i) {
        handleDisplayChange();
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayRemoved(int i) {
        handleDisplayChange();
    }

    @Override // android.hardware.SensorEventListener
    public final void onSensorChanged(SensorEvent sensorEvent) {
        HysteresisState hysteresisState;
        HysteresisState hysteresisState2;
        float f = sensorEvent.values[0];
        if (f < FullScreenMagnificationGestureHandler.MAX_SCALE) {
            Slog.w("KbdBacklightController", "Light sensor doesn't have valid value");
            return;
        }
        HysteresisState hysteresisState3 = this.mHysteresisState;
        HysteresisState hysteresisState4 = HysteresisState.IMMEDIATE;
        if (hysteresisState3 == hysteresisState4) {
            this.mSmoothedLux = (int) f;
        } else {
            float f2 = this.mSmoothingConstant;
            this.mSmoothedLux = (int) (((1.0f - f2) * this.mSmoothedLux) + (f * f2));
        }
        boolean z = DEBUG;
        if (z) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Current smoothed lux from ALS = "),
                    this.mSmoothedLux,
                    "KbdBacklightController");
        }
        HysteresisState hysteresisState5 = this.mHysteresisState;
        HysteresisState hysteresisState6 = HysteresisState.STABLE;
        if (hysteresisState5 != hysteresisState4
                && this.mSmoothedLux == this.mSmoothedLuxAtLastAdjustment) {
            this.mHysteresisState = hysteresisState6;
            return;
        }
        int max = Math.max(0, this.mCurrentBrightnessStepIndex);
        int length = this.mBrightnessSteps.length;
        int i = this.mSmoothedLux;
        int i2 = this.mSmoothedLuxAtLastAdjustment;
        if (i > i2) {
            HysteresisState hysteresisState7 = this.mHysteresisState;
            if (hysteresisState7 != hysteresisState4
                    && hysteresisState7 != (hysteresisState2 = HysteresisState.INCREASING)) {
                if (z) {
                    Slog.d(
                            "KbdBacklightController",
                            "ALS transitioned to brightness increasing state");
                }
                this.mHysteresisState = hysteresisState2;
                this.mHysteresisCount = 0;
            }
            while (max < length
                    && this.mSmoothedLux >= this.mBrightnessSteps[max].mIncreaseLuxThreshold) {
                max++;
            }
        } else if (i < i2) {
            HysteresisState hysteresisState8 = this.mHysteresisState;
            if (hysteresisState8 != hysteresisState4
                    && hysteresisState8 != (hysteresisState = HysteresisState.DECREASING)) {
                if (z) {
                    Slog.d(
                            "KbdBacklightController",
                            "ALS transitioned to brightness decreasing state");
                }
                this.mHysteresisState = hysteresisState;
                this.mHysteresisCount = 0;
            }
            while (max >= 0
                    && this.mSmoothedLux <= this.mBrightnessSteps[max].mDecreaseLuxThreshold) {
                max--;
            }
        }
        if (this.mHysteresisState == hysteresisState4) {
            this.mCurrentBrightnessStepIndex = max;
            this.mSmoothedLuxAtLastAdjustment = this.mSmoothedLux;
            this.mHysteresisState = hysteresisState6;
            this.mHysteresisCount = 0;
            this.mHandler.sendMessage(
                    Message.obtain(
                            this.mHandler,
                            0,
                            Integer.valueOf(this.mBrightnessSteps[max].mBrightnessValue)));
            return;
        }
        if (max == this.mCurrentBrightnessStepIndex) {
            return;
        }
        this.mHysteresisCount++;
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("Incremented hysteresis count to ");
            sb.append(this.mHysteresisCount);
            sb.append(" (lux went from ");
            sb.append(this.mSmoothedLuxAtLastAdjustment);
            sb.append(" to ");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    sb, this.mSmoothedLux, ")", "KbdBacklightController");
        }
        if (this.mHysteresisCount >= 2) {
            this.mCurrentBrightnessStepIndex = max;
            this.mSmoothedLuxAtLastAdjustment = this.mSmoothedLux;
            this.mHysteresisCount = 1;
            this.mHandler.sendMessage(
                    Message.obtain(
                            this.mHandler,
                            0,
                            Integer.valueOf(this.mBrightnessSteps[max].mBrightnessValue)));
        }
    }

    public final void removeSensorListener(Sensor sensor) {
        SensorManager sensorManager =
                (SensorManager) this.mContext.getSystemService(SensorManager.class);
        if (sensorManager == null || sensor == null) {
            return;
        }
        sensorManager.unregisterListener(this, sensor);
        if (DEBUG) {
            Slog.d("KbdBacklightController", "Unregistering ALS listener");
        }
    }
}
