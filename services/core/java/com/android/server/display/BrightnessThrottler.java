package com.android.server.display;

import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IThermalEventListener;
import android.os.IThermalService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.Temperature;
import android.provider.DeviceConfig;
import android.provider.DeviceConfigInterface;
import android.util.Slog;

import com.android.server.display.config.SensorData;
import com.android.server.display.feature.DeviceConfigParameterProvider;
import com.android.server.display.utils.DebugUtils;
import com.android.server.display.utils.DeviceConfigParsingUtils;
import com.android.server.display.utils.SensorUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BrightnessThrottler {
    public static final boolean DEBUG = DebugUtils.isDebuggable("BrightnessThrottler");
    public final DeviceConfigParameterProvider mConfigParameterProvider;
    public Map mDdcThermalThrottlingDataMap;
    public final Handler mDeviceConfigHandler;
    public final DeviceConfigListener mDeviceConfigListener;
    public final Handler mHandler;
    public final SkinThermalStatusObserver mSkinThermalStatusObserver;
    public SensorData mTempSensor;
    public String mThermalBrightnessThrottlingDataId;
    public String mThermalBrightnessThrottlingDataString;
    public DisplayDeviceConfig.ThermalBrightnessThrottlingData mThermalThrottlingData;
    public final Runnable mThrottlingChangeCallback;
    public int mThrottlingStatus;
    public String mUniqueDisplayId;
    public float mBrightnessCap = 1.0f;
    public int mBrightnessMaxReason = 0;
    public final Map mThermalBrightnessThrottlingDataOverride = new HashMap();
    public final BrightnessThrottler$$ExternalSyntheticLambda0 mDataPointMapper =
            new BrightnessThrottler$$ExternalSyntheticLambda0();
    public final BrightnessThrottler$$ExternalSyntheticLambda1 mDataSetMapper =
            new BrightnessThrottler$$ExternalSyntheticLambda1();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeviceConfigListener implements DeviceConfig.OnPropertiesChangedListener {
        public final Executor mExecutor;

        public DeviceConfigListener() {
            this.mExecutor = new HandlerExecutor(BrightnessThrottler.this.mDeviceConfigHandler);
        }

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            BrightnessThrottler.this.loadThermalBrightnessThrottlingDataFromDeviceConfig();
            BrightnessThrottler.this.resetThermalThrottlingData();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SkinThermalStatusObserver extends IThermalEventListener.Stub {
        public final Handler mHandler;
        public final Injector mInjector;
        public SensorData mObserverTempSensor;
        public boolean mStarted;
        public IThermalService mThermalService;

        public SkinThermalStatusObserver(Injector injector, Handler handler) {
            this.mInjector = injector;
            this.mHandler = handler;
        }

        public final void notifyThrottling(Temperature temperature) {
            if (BrightnessThrottler.DEBUG) {
                Slog.d(
                        "BrightnessThrottler",
                        "New thermal throttling status = " + temperature.getStatus());
            }
            String str = this.mObserverTempSensor.name;
            if (str == null || str.equals(temperature.getName())) {
                this.mHandler.post(
                        new BrightnessThrottler$$ExternalSyntheticLambda2(1, this, temperature));
                return;
            }
            Slog.i(
                    "BrightnessThrottler",
                    "Skipping thermal throttling notification as monitored sensor: "
                            + this.mObserverTempSensor.name
                            + " != notified sensor: "
                            + temperature.getName());
        }

        public final void registerThermalListener() {
            this.mInjector.getClass();
            IThermalService asInterface =
                    IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
            this.mThermalService = asInterface;
            if (asInterface == null) {
                Slog.e(
                        "BrightnessThrottler",
                        "Could not observe thermal status. Service not available");
                return;
            }
            try {
                this.mThermalService.registerThermalEventListenerWithType(
                        this, SensorUtils.getSensorTemperatureType(this.mObserverTempSensor));
                this.mStarted = true;
            } catch (RemoteException e) {
                Slog.e("BrightnessThrottler", "Failed to register thermal status listener", e);
            }
        }

        public final void stopObserving() {
            if (!this.mStarted) {
                if (BrightnessThrottler.DEBUG) {
                    Slog.d(
                            "BrightnessThrottler",
                            "Stop skipped because thermal status observer not started");
                }
            } else {
                try {
                    this.mThermalService.unregisterThermalEventListener(this);
                    this.mStarted = false;
                } catch (RemoteException e) {
                    Slog.e(
                            "BrightnessThrottler",
                            "Failed to unregister thermal status listener",
                            e);
                }
                this.mThermalService = null;
            }
        }
    }

    public BrightnessThrottler(
            Injector injector,
            Handler handler,
            Handler handler2,
            Runnable runnable,
            String str,
            String str2,
            Map map,
            SensorData sensorData) {
        this.mHandler = handler;
        this.mDeviceConfigHandler = handler2;
        this.mDdcThermalThrottlingDataMap = map;
        this.mThrottlingChangeCallback = runnable;
        this.mSkinThermalStatusObserver = new SkinThermalStatusObserver(injector, handler);
        this.mUniqueDisplayId = str;
        injector.getClass();
        this.mConfigParameterProvider =
                new DeviceConfigParameterProvider(DeviceConfigInterface.REAL);
        this.mDeviceConfigListener = new DeviceConfigListener();
        this.mThermalBrightnessThrottlingDataId = str2;
        this.mDdcThermalThrottlingDataMap = map;
        loadThermalBrightnessThrottlingDataFromDeviceConfig();
        Map map2 = this.mDdcThermalThrottlingDataMap;
        String str3 = this.mThermalBrightnessThrottlingDataId;
        String str4 = this.mUniqueDisplayId;
        this.mDdcThermalThrottlingDataMap = map2;
        this.mThermalBrightnessThrottlingDataId = str3;
        this.mUniqueDisplayId = str4;
        this.mTempSensor = sensorData;
        resetThermalThrottlingData();
    }

    public final DisplayDeviceConfig.ThermalBrightnessThrottlingData getConfigFromId(String str) {
        DisplayDeviceConfig.ThermalBrightnessThrottlingData thermalBrightnessThrottlingData =
                this.mThermalBrightnessThrottlingDataOverride.get(this.mUniqueDisplayId) == null
                        ? null
                        : (DisplayDeviceConfig.ThermalBrightnessThrottlingData)
                                ((Map)
                                                this.mThermalBrightnessThrottlingDataOverride.get(
                                                        this.mUniqueDisplayId))
                                        .get(str);
        return thermalBrightnessThrottlingData == null
                ? (DisplayDeviceConfig.ThermalBrightnessThrottlingData)
                        this.mDdcThermalThrottlingDataMap.get(str)
                : thermalBrightnessThrottlingData;
    }

    public final void loadThermalBrightnessThrottlingDataFromDeviceConfig() {
        this.mThermalBrightnessThrottlingDataString =
                this.mConfigParameterProvider.mDeviceConfig.getString(
                        "display_manager", "brightness_throttling_data", (String) null);
        ((HashMap) this.mThermalBrightnessThrottlingDataOverride).clear();
        String str = this.mThermalBrightnessThrottlingDataString;
        if (str == null) {
            Slog.w("BrightnessThrottler", "DeviceConfig ThermalBrightnessThrottlingData is null");
        } else {
            ((HashMap) this.mThermalBrightnessThrottlingDataOverride)
                    .putAll(
                            DeviceConfigParsingUtils.parseDeviceConfigMap(
                                    str, this.mDataPointMapper, this.mDataSetMapper));
        }
    }

    public final void resetThermalThrottlingData() {
        SensorData sensorData;
        SkinThermalStatusObserver skinThermalStatusObserver = this.mSkinThermalStatusObserver;
        skinThermalStatusObserver.stopObserving();
        DeviceConfigInterface deviceConfigInterface = this.mConfigParameterProvider.mDeviceConfig;
        DeviceConfigListener deviceConfigListener = this.mDeviceConfigListener;
        deviceConfigInterface.removeOnPropertiesChangedListener(deviceConfigListener);
        this.mBrightnessCap = 1.0f;
        this.mBrightnessMaxReason = 0;
        this.mThrottlingStatus = -1;
        BrightnessThrottler.this.mConfigParameterProvider.mDeviceConfig
                .addOnPropertiesChangedListener(
                        "display_manager", deviceConfigListener.mExecutor, deviceConfigListener);
        this.mThermalThrottlingData = getConfigFromId(this.mThermalBrightnessThrottlingDataId);
        if (!"default".equals(this.mThermalBrightnessThrottlingDataId)
                && this.mThermalThrottlingData == null) {
            this.mThermalThrottlingData = getConfigFromId("default");
            Slog.d("BrightnessThrottler", "Falling back to default throttling Id");
        }
        if (this.mThermalThrottlingData != null) {
            SensorData sensorData2 = this.mTempSensor;
            if (!skinThermalStatusObserver.mStarted
                    || (sensorData = skinThermalStatusObserver.mObserverTempSensor) == null) {
                skinThermalStatusObserver.mObserverTempSensor = sensorData2;
                skinThermalStatusObserver.registerThermalListener();
                return;
            }
            skinThermalStatusObserver.mObserverTempSensor = sensorData2;
            if (!sensorData.type.equals(sensorData2.type)) {
                skinThermalStatusObserver.stopObserving();
                skinThermalStatusObserver.registerThermalListener();
            } else if (DEBUG) {
                Slog.d("BrightnessThrottler", "Thermal status observer already started");
            }
        }
    }
}
