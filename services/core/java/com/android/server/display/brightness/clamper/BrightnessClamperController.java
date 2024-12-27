package com.android.server.display.brightness.clamper;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.display.DisplayManagerInternal;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.SystemClock;
import android.provider.DeviceConfig;
import android.provider.DeviceConfigInterface;
import android.util.Slog;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.DisplayBrightnessState;
import com.android.server.display.DisplayDeviceConfig;
import com.android.server.display.brightness.BrightnessUtils;
import com.android.server.display.brightness.clamper.BrightnessClamper;
import com.android.server.display.brightness.clamper.BrightnessPowerClamper;
import com.android.server.display.brightness.clamper.BrightnessThermalClamper;
import com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper;
import com.android.server.display.config.SensorData;
import com.android.server.display.feature.DeviceConfigParameterProvider;
import com.android.server.display.feature.DisplayManagerFlags;
import com.android.server.display.utils.AmbientFilter$WeightedMovingAverageAmbientFilter;
import com.android.server.display.utils.AmbientFilterFactory;
import com.android.server.display.utils.DebugUtils;
import com.android.server.display.utils.SensorUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BrightnessClamperController {
    public static final boolean DEBUG = DebugUtils.isDebuggable("BrightnessClamperController");
    public AmbientFilter$WeightedMovingAverageAmbientFilter mAmbientFilter;
    public final ClamperChangeListener mClamperChangeListenerExternal;
    public final List mClampers;
    public final DeviceConfigParameterProvider mDeviceConfigParameterProvider;
    public final Executor mExecutor;
    public final Handler mHandler;
    public final Sensor mLightSensor;
    public final AnonymousClass1 mLightSensorListener;
    public final String mLightSensorName;
    public final int mLightSensorRate;
    public final String mLightSensorType;
    public final List mModifiers;
    public final BrightnessClamperController$$ExternalSyntheticLambda7 mOnPropertiesChangedListener;
    public final Resources mResources;
    public final SensorManager mSensorManager;
    public float mBrightnessCap = BrightnessUtils.sScreenExtendedBrightnessRangeMaximum;
    public float mCustomAnimationRate = -1.0f;
    public BrightnessClamper.Type mClamperType = null;
    public Sensor mRegisteredLightSensor = null;
    public boolean mClamperApplied = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ClamperChangeListener {
        void onChanged();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisplayDeviceData implements BrightnessThermalClamper.ThermalData, BrightnessPowerClamper.PowerData, BrightnessWearBedtimeModeClamper.WearBedtimeModeData {
        public final DisplayDeviceConfig mDisplayDeviceConfig;
        public final String mPowerThrottlingDataId;
        public final String mThermalThrottlingDataId;
        public final String mUniqueDisplayId;

        public DisplayDeviceData(String str, String str2, String str3, DisplayDeviceConfig displayDeviceConfig) {
            this.mUniqueDisplayId = str;
            this.mThermalThrottlingDataId = str2;
            this.mPowerThrottlingDataId = str3;
            this.mDisplayDeviceConfig = displayDeviceConfig;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Injector {
    }

    /* JADX WARN: Type inference failed for: r1v15, types: [android.provider.DeviceConfig$OnPropertiesChangedListener, com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda7] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.server.display.brightness.clamper.BrightnessClamperController$1] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda5] */
    public BrightnessClamperController(Injector injector, Handler handler, ClamperChangeListener clamperChangeListener, DisplayDeviceData displayDeviceData, Context context, DisplayManagerFlags displayManagerFlags, SensorManager sensorManager) {
        Resources resources;
        DisplayDeviceConfig displayDeviceConfig;
        ArrayList arrayList;
        SensorData sensorData;
        DisplayDeviceConfig displayDeviceConfig2;
        if (injector == null) {
            new Injector();
        }
        DeviceConfigInterface deviceConfigInterface = DeviceConfigInterface.REAL;
        this.mDeviceConfigParameterProvider = new DeviceConfigParameterProvider(deviceConfigInterface);
        this.mHandler = handler;
        this.mSensorManager = sensorManager;
        DisplayDeviceConfig displayDeviceConfig3 = displayDeviceData.mDisplayDeviceConfig;
        this.mLightSensorListener = new SensorEventListener() { // from class: com.android.server.display.brightness.clamper.BrightnessClamperController.1
            @Override // android.hardware.SensorEventListener
            public final void onAccuracyChanged(Sensor sensor, int i) {
            }

            @Override // android.hardware.SensorEventListener
            public final void onSensorChanged(SensorEvent sensorEvent) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                BrightnessClamperController.this.mAmbientFilter.addValue(TimeUnit.NANOSECONDS.toMillis(sensorEvent.timestamp), sensorEvent.values[0]);
                final float estimate = BrightnessClamperController.this.mAmbientFilter.getEstimate(elapsedRealtime);
                ((ArrayList) BrightnessClamperController.this.mModifiers).forEach(new Consumer() { // from class: com.android.server.display.brightness.clamper.BrightnessClamperController$1$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((BrightnessModifier) obj).setAmbientLux(estimate);
                    }
                });
            }
        };
        this.mClamperChangeListenerExternal = clamperChangeListener;
        HandlerExecutor handlerExecutor = new HandlerExecutor(handler);
        Resources resources2 = context.getResources();
        this.mResources = resources2;
        this.mLightSensorRate = context.getResources().getInteger(R.integer.config_cameraLaunchGestureSensorType);
        final ?? r1 = new Runnable() { // from class: com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                BrightnessClamperController brightnessClamperController = BrightnessClamperController.this;
                brightnessClamperController.getClass();
                float f = BrightnessUtils.sScreenExtendedBrightnessRangeMaximum;
                BrightnessClamper.Type type = null;
                BrightnessClamper brightnessClamper = (BrightnessClamper) brightnessClamperController.mClampers.stream().filter(new BrightnessClamperController$$ExternalSyntheticLambda2(1)).min(new BrightnessClamperController$$ExternalSyntheticLambda12()).orElse(null);
                if (brightnessClamper != null) {
                    f = brightnessClamper.mBrightnessCap;
                    type = brightnessClamper.getType();
                }
                if (brightnessClamperController.mBrightnessCap == f && brightnessClamperController.mClamperType == type && brightnessClamperController.mCustomAnimationRate == -1.0f) {
                    return;
                }
                brightnessClamperController.mBrightnessCap = f;
                brightnessClamperController.mClamperType = type;
                brightnessClamperController.mCustomAnimationRate = -1.0f;
                brightnessClamperController.mClamperChangeListenerExternal.onChanged();
            }
        };
        ClamperChangeListener clamperChangeListener2 = new ClamperChangeListener() { // from class: com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda6
            @Override // com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener
            public final void onChanged() {
                Runnable runnable = r1;
                Handler handler2 = BrightnessClamperController.this.mHandler;
                if (handler2.hasCallbacks(runnable)) {
                    return;
                }
                handler2.post(runnable);
            }
        };
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new BrightnessThermalClamper(new BrightnessThermalClamper.Injector(), handler, clamperChangeListener2, displayDeviceData));
        if (displayManagerFlags.mPowerThrottlingClamperFlagState.isEnabled()) {
            arrayList2.add(new BrightnessPowerClamper(new BrightnessPowerClamper.Injector(), handler, clamperChangeListener2, displayDeviceData));
        }
        if (displayManagerFlags.mBrightnessWearBedtimeModeClamperFlagState.isEnabled()) {
            displayDeviceConfig = displayDeviceConfig3;
            arrayList = arrayList2;
            resources = resources2;
            arrayList.add(new BrightnessWearBedtimeModeClamper(new BrightnessWearBedtimeModeClamper.Injector(), handler, context, clamperChangeListener2, displayDeviceData));
        } else {
            resources = resources2;
            displayDeviceConfig = displayDeviceConfig3;
            arrayList = arrayList2;
        }
        this.mClampers = arrayList;
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(new DisplayDimModifier(context));
        arrayList3.add(new BrightnessLowPowerModeModifier());
        if (displayManagerFlags.mEvenDimmerFlagState.isEnabled() && (displayDeviceConfig2 = displayDeviceData.mDisplayDeviceConfig) != null && displayDeviceConfig2.mEvenDimmerBrightnessData != null) {
            arrayList3.add(new BrightnessLowLuxModifier(handler, clamperChangeListener, context, displayDeviceConfig2));
        }
        this.mModifiers = arrayList3;
        ?? r12 = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda7
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                ((ArrayList) BrightnessClamperController.this.mClampers).forEach(new BrightnessClamperController$$ExternalSyntheticLambda3(1));
            }
        };
        this.mOnPropertiesChangedListener = r12;
        if (arrayList.isEmpty()) {
            return;
        }
        deviceConfigInterface.addOnPropertiesChangedListener("display_manager", handlerExecutor, (DeviceConfig.OnPropertiesChangedListener) r12);
        if (displayDeviceConfig != null && (sensorData = displayDeviceConfig.mAmbientLightSensor) != null) {
            this.mLightSensorType = sensorData.type;
            this.mLightSensorName = sensorData.name;
        } else if (this.mLightSensorName == null && this.mLightSensorType == null) {
            this.mLightSensorType = resources.getString(R.string.display_rotation_camera_compat_toast_in_multi_window);
            this.mLightSensorName = "";
        }
        this.mLightSensor = SensorUtils.findSensor(sensorManager, this.mLightSensorType, this.mLightSensorName, 5);
        maybeRegisterLightSensor();
    }

    public final DisplayBrightnessState clamp(DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, float f, boolean z, int i, BrightnessModifierRequest brightnessModifierRequest) {
        float min = Math.min(f, this.mBrightnessCap);
        DisplayBrightnessState.Builder builder = new DisplayBrightnessState.Builder();
        builder.mIsSlowChange = z;
        builder.mBrightness = min;
        builder.mMaxBrightness = this.mBrightnessCap;
        builder.mCustomAnimationRate = this.mCustomAnimationRate;
        if (this.mClamperType != null) {
            builder.mBrightnessReason.addModifier(min, 8);
            if (!this.mClamperApplied) {
                builder.mIsSlowChange = false;
            }
            this.mClamperApplied = true;
        } else {
            this.mClamperApplied = false;
        }
        if (i != 2) {
            unregisterSensorListener();
        } else {
            maybeRegisterLightSensor();
        }
        for (int i2 = 0; i2 < ((ArrayList) this.mModifiers).size(); i2++) {
            BrightnessModifier brightnessModifier = (BrightnessModifier) ((ArrayList) this.mModifiers).get(i2);
            if (brightnessModifier.shouldApply(displayPowerRequest, brightnessModifierRequest)) {
                float f2 = builder.mBrightness;
                if (f2 > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    builder.mBrightness = brightnessModifier.getBrightnessAdjusted(f2, displayPowerRequest);
                    builder.mBrightnessReason.addModifier(builder.mBrightness, brightnessModifier.getModifier());
                }
                if (!brightnessModifier.mApplied) {
                    builder.mIsSlowChange = false;
                }
                brightnessModifier.mApplied = true;
            } else if (brightnessModifier.mApplied) {
                builder.mIsSlowChange = false;
                brightnessModifier.mApplied = false;
            }
        }
        return new DisplayBrightnessState(builder);
    }

    public final int getBrightnessMaxReason() {
        BrightnessClamper.Type type = this.mClamperType;
        if (type == null) {
            return 0;
        }
        if (type == BrightnessClamper.Type.THERMAL) {
            return 1;
        }
        if (type == BrightnessClamper.Type.POWER) {
            return 2;
        }
        if (type == BrightnessClamper.Type.WEAR_BEDTIME_MODE) {
            return 3;
        }
        Slog.wtf("BrightnessClamperController", "BrightnessMaxReason not mapped for type=" + this.mClamperType);
        return 0;
    }

    public final void maybeRegisterLightSensor() {
        Sensor sensor;
        if (this.mModifiers.stream().noneMatch(new BrightnessClamperController$$ExternalSyntheticLambda2(0)) || (sensor = this.mRegisteredLightSensor) == this.mLightSensor) {
            return;
        }
        if (sensor != null) {
            unregisterSensorListener();
        }
        this.mAmbientFilter = AmbientFilterFactory.createBrightnessFilter(this.mResources, "BrightnessClamperController");
        this.mSensorManager.registerListener(this.mLightSensorListener, this.mLightSensor, this.mLightSensorRate * 1000, this.mHandler);
        this.mRegisteredLightSensor = this.mLightSensor;
        if (DEBUG) {
            Slog.d("BrightnessClamperController", "maybeRegisterLightSensor");
        }
    }

    public final void unregisterSensorListener() {
        this.mSensorManager.unregisterListener(this.mLightSensorListener);
        this.mRegisteredLightSensor = null;
        ((ArrayList) this.mModifiers).forEach(new BrightnessClamperController$$ExternalSyntheticLambda3(0));
        if (DEBUG) {
            Slog.d("BrightnessClamperController", "unregisterSensorListener");
        }
    }
}
