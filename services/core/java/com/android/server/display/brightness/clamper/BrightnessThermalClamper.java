package com.android.server.display.brightness.clamper;

import android.os.Handler;
import android.os.IThermalEventListener;
import android.os.IThermalService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.Temperature;
import android.provider.DeviceConfigInterface;
import android.util.Slog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.display.BrightnessThrottler$$ExternalSyntheticLambda1;
import com.android.server.display.DisplayDeviceConfig;
import com.android.server.display.brightness.clamper.BrightnessClamper;
import com.android.server.display.brightness.clamper.BrightnessClamperController;
import com.android.server.display.brightness.clamper.BrightnessThermalClamper;
import com.android.server.display.config.SensorData;
import com.android.server.display.feature.DeviceConfigParameterProvider;
import com.android.server.display.utils.DeviceConfigParsingUtils;
import com.android.server.display.utils.SensorUtils;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BrightnessThermalClamper extends BrightnessClamper {
    public final DeviceConfigParameterProvider mConfigParameterProvider;
    public String mDataId;
    public final BrightnessThermalClamper$$ExternalSyntheticLambda0 mDataPointMapper;
    public final BrightnessThrottler$$ExternalSyntheticLambda1 mDataSetMapper;
    public final ThermalStatusObserver mThermalStatusObserver;
    public DisplayDeviceConfig.ThermalBrightnessThrottlingData mThermalThrottlingDataActive;
    public DisplayDeviceConfig.ThermalBrightnessThrottlingData mThermalThrottlingDataFromDeviceConfig;
    public Map mThermalThrottlingDataOverride;
    public int mThrottlingStatus;
    public String mUniqueDisplayId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ThermalData {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ThermalStatusObserver extends IThermalEventListener.Stub {
        public final Handler mHandler;
        public final Injector mInjector;
        public SensorData mObserverTempSensor;
        public boolean mStarted = false;
        public IThermalService mThermalService;

        public ThermalStatusObserver(Injector injector, Handler handler) {
            this.mInjector = injector;
            this.mHandler = handler;
        }

        public final void notifyThrottling(Temperature temperature) {
            Slog.d("BrightnessThermalClamper", "New thermal throttling status = " + temperature.getStatus());
            String str = this.mObserverTempSensor.name;
            if (str == null || str.equals(temperature.getName())) {
                final int status = temperature.getStatus();
                this.mHandler.post(new Runnable() { // from class: com.android.server.display.brightness.clamper.BrightnessThermalClamper$ThermalStatusObserver$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BrightnessThermalClamper.ThermalStatusObserver thermalStatusObserver = BrightnessThermalClamper.ThermalStatusObserver.this;
                        int i = status;
                        BrightnessThermalClamper brightnessThermalClamper = BrightnessThermalClamper.this;
                        if (brightnessThermalClamper.mThrottlingStatus != i) {
                            brightnessThermalClamper.mThrottlingStatus = i;
                            brightnessThermalClamper.recalculateBrightnessCap$1();
                        }
                    }
                });
                return;
            }
            Slog.i("BrightnessThermalClamper", "Skipping thermal throttling notification as monitored sensor: " + this.mObserverTempSensor.name + " != notified sensor: " + temperature.getName());
        }

        public final void registerThermalListener() {
            this.mInjector.getClass();
            IThermalService asInterface = IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
            this.mThermalService = asInterface;
            if (asInterface == null) {
                Slog.e("BrightnessThermalClamper", "Could not observe thermal status. Service not available");
                return;
            }
            try {
                this.mThermalService.registerThermalEventListenerWithType(this, SensorUtils.getSensorTemperatureType(this.mObserverTempSensor));
                this.mStarted = true;
            } catch (RemoteException e) {
                Slog.e("BrightnessThermalClamper", "Failed to register thermal status listener", e);
            }
        }

        public final void stopObserving() {
            if (this.mStarted) {
                try {
                    this.mThermalService.unregisterThermalEventListener(this);
                    this.mStarted = false;
                } catch (RemoteException e) {
                    Slog.e("BrightnessThermalClamper", "Failed to unregister thermal status listener", e);
                }
                this.mThermalService = null;
            }
        }
    }

    public BrightnessThermalClamper(Injector injector, Handler handler, BrightnessClamperController.ClamperChangeListener clamperChangeListener, ThermalData thermalData) {
        super(handler, clamperChangeListener);
        this.mThermalThrottlingDataOverride = Map.of();
        this.mThermalThrottlingDataFromDeviceConfig = null;
        this.mThermalThrottlingDataActive = null;
        this.mUniqueDisplayId = null;
        this.mDataId = null;
        this.mThrottlingStatus = 0;
        this.mDataPointMapper = new BrightnessThermalClamper$$ExternalSyntheticLambda0();
        this.mDataSetMapper = new BrightnessThrottler$$ExternalSyntheticLambda1();
        injector.getClass();
        this.mConfigParameterProvider = new DeviceConfigParameterProvider(DeviceConfigInterface.REAL);
        this.mThermalStatusObserver = new ThermalStatusObserver(injector, handler);
        handler.post(new BrightnessThermalClamper$$ExternalSyntheticLambda1(this, thermalData, 0));
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public final void dump(PrintWriter printWriter) {
        StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, this.mDataId, "  mDataOverride: ", BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, this.mUniqueDisplayId, "  mDataId: ", BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "BrightnessThermalClamper:", "  mThrottlingStatus: "), this.mThrottlingStatus, printWriter, "  mUniqueDisplayId: ")));
        m.append(this.mThermalThrottlingDataOverride);
        printWriter.println(m.toString());
        printWriter.println("  mDataFromDeviceConfig: " + this.mThermalThrottlingDataFromDeviceConfig);
        printWriter.println("  mDataActive: " + this.mThermalThrottlingDataActive);
        ThermalStatusObserver thermalStatusObserver = this.mThermalStatusObserver;
        thermalStatusObserver.getClass();
        printWriter.println("  ThermalStatusObserver:");
        StringBuilder m2 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("    mStarted: "), thermalStatusObserver.mStarted, printWriter, "    mObserverTempSensor: ");
        m2.append(thermalStatusObserver.mObserverTempSensor);
        printWriter.println(m2.toString());
        if (thermalStatusObserver.mThermalService != null) {
            printWriter.println("    ThermalService available");
        } else {
            printWriter.println("    ThermalService not available");
        }
        super.dump(printWriter);
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public final BrightnessClamper.Type getType() {
        return BrightnessClamper.Type.THERMAL;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public final void onDeviceConfigChanged() {
        this.mHandler.post(new Runnable() { // from class: com.android.server.display.brightness.clamper.BrightnessThermalClamper$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                BrightnessThermalClamper brightnessThermalClamper = BrightnessThermalClamper.this;
                brightnessThermalClamper.mThermalThrottlingDataOverride = DeviceConfigParsingUtils.parseDeviceConfigMap(brightnessThermalClamper.mConfigParameterProvider.mDeviceConfig.getString("display_manager", "brightness_throttling_data", (String) null), brightnessThermalClamper.mDataPointMapper, brightnessThermalClamper.mDataSetMapper);
                brightnessThermalClamper.recalculateActiveData$1();
            }
        });
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public final void onDisplayChanged(Object obj) {
        this.mHandler.post(new BrightnessThermalClamper$$ExternalSyntheticLambda1(this, (ThermalData) obj, 1));
    }

    public final void recalculateActiveData$1() {
        String str = this.mUniqueDisplayId;
        if (str == null || this.mDataId == null) {
            return;
        }
        this.mThermalThrottlingDataActive = (DisplayDeviceConfig.ThermalBrightnessThrottlingData) ((Map) this.mThermalThrottlingDataOverride.getOrDefault(str, Map.of())).getOrDefault(this.mDataId, this.mThermalThrottlingDataFromDeviceConfig);
        recalculateBrightnessCap$1();
    }

    public final void recalculateBrightnessCap$1() {
        DisplayDeviceConfig.ThermalBrightnessThrottlingData thermalBrightnessThrottlingData = this.mThermalThrottlingDataActive;
        float f = 1.0f;
        boolean z = false;
        if (thermalBrightnessThrottlingData != null) {
            Iterator it = ((ArrayList) thermalBrightnessThrottlingData.throttlingLevels).iterator();
            while (it.hasNext()) {
                DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel throttlingLevel = (DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel) it.next();
                if (throttlingLevel.thermalStatus > this.mThrottlingStatus) {
                    break;
                }
                f = throttlingLevel.brightness;
                z = true;
            }
        }
        if (f == this.mBrightnessCap && this.mIsActive == z) {
            return;
        }
        this.mBrightnessCap = f;
        this.mIsActive = z;
        this.mChangeListener.onChanged();
    }

    public final void setDisplayData(ThermalData thermalData) {
        SensorData sensorData;
        this.mUniqueDisplayId = ((BrightnessClamperController.DisplayDeviceData) thermalData).mUniqueDisplayId;
        BrightnessClamperController.DisplayDeviceData displayDeviceData = (BrightnessClamperController.DisplayDeviceData) thermalData;
        String str = displayDeviceData.mThermalThrottlingDataId;
        this.mDataId = str;
        DisplayDeviceConfig displayDeviceConfig = displayDeviceData.mDisplayDeviceConfig;
        DisplayDeviceConfig.ThermalBrightnessThrottlingData thermalBrightnessThrottlingData = (DisplayDeviceConfig.ThermalBrightnessThrottlingData) ((HashMap) displayDeviceConfig.mThermalBrightnessThrottlingDataMapByThrottlingId).get(str);
        this.mThermalThrottlingDataFromDeviceConfig = thermalBrightnessThrottlingData;
        if (thermalBrightnessThrottlingData == null && !"default".equals(this.mDataId)) {
            Slog.wtf("BrightnessThermalClamper", "Thermal throttling data is missing for thermalThrottlingDataId=" + this.mDataId);
        }
        SensorData sensorData2 = displayDeviceConfig.mTempSensor;
        ThermalStatusObserver thermalStatusObserver = this.mThermalStatusObserver;
        if (!thermalStatusObserver.mStarted || (sensorData = thermalStatusObserver.mObserverTempSensor) == null) {
            thermalStatusObserver.mObserverTempSensor = sensorData2;
            thermalStatusObserver.registerThermalListener();
            return;
        }
        thermalStatusObserver.mObserverTempSensor = sensorData2;
        if (sensorData.type.equals(sensorData2.type)) {
            Slog.d("BrightnessThermalClamper", "Thermal status observer already started");
        } else {
            thermalStatusObserver.stopObserving();
            thermalStatusObserver.registerThermalListener();
        }
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public final void stop() {
        this.mThermalStatusObserver.stopObserving();
    }
}
