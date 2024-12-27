package com.android.server.display.brightness.strategy;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.IndentingPrintWriter;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.display.BrightnessMappingStrategy;
import com.android.server.display.DisplayBrightnessState;
import com.android.server.display.DisplayDeviceConfig;
import com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda11;
import com.android.server.display.ScreenOffBrightnessSensorController;
import com.android.server.display.brightness.BrightnessReason;
import com.android.server.display.brightness.StrategyExecutionRequest;
import com.android.server.display.config.SensorData;
import com.android.server.display.utils.SensorUtils;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AutoBrightnessFallbackStrategy implements DisplayBrightnessStrategy {
    public final Injector mInjector = new RealInjector();
    public boolean mIsEnabled;
    public int mLeadDisplayId;
    Sensor mScreenOffBrightnessSensor;
    public ScreenOffBrightnessSensorController mScreenOffBrightnessSensorController;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface Injector {
        Sensor getScreenOffBrightnessSensor(
                SensorManager sensorManager, DisplayDeviceConfig displayDeviceConfig);

        ScreenOffBrightnessSensorController getScreenOffBrightnessSensorController(
                SensorManager sensorManager,
                Sensor sensor,
                Handler handler,
                DisplayPowerController$$ExternalSyntheticLambda11
                        displayPowerController$$ExternalSyntheticLambda11,
                int[] iArr,
                BrightnessMappingStrategy brightnessMappingStrategy);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RealInjector implements Injector {
        @Override // com.android.server.display.brightness.strategy.AutoBrightnessFallbackStrategy.Injector
        public final Sensor getScreenOffBrightnessSensor(
                SensorManager sensorManager, DisplayDeviceConfig displayDeviceConfig) {
            SensorData sensorData = displayDeviceConfig.mScreenOffBrightnessSensor;
            if (sensorData == null) {
                return null;
            }
            return SensorUtils.findSensor(sensorManager, sensorData.type, sensorData.name, 0);
        }

        @Override // com.android.server.display.brightness.strategy.AutoBrightnessFallbackStrategy.Injector
        public final ScreenOffBrightnessSensorController getScreenOffBrightnessSensorController(
                SensorManager sensorManager,
                Sensor sensor,
                Handler handler,
                DisplayPowerController$$ExternalSyntheticLambda11
                        displayPowerController$$ExternalSyntheticLambda11,
                int[] iArr,
                BrightnessMappingStrategy brightnessMappingStrategy) {
            return new ScreenOffBrightnessSensorController(
                    sensorManager,
                    sensor,
                    handler,
                    displayPowerController$$ExternalSyntheticLambda11,
                    iArr,
                    brightnessMappingStrategy);
        }
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final void dump(PrintWriter printWriter) {
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                printWriter,
                                "AutoBrightnessFallbackStrategy:",
                                "  mLeadDisplayId="),
                        this.mLeadDisplayId,
                        printWriter,
                        "  mIsEnabled="),
                this.mIsEnabled,
                printWriter);
        if (this.mScreenOffBrightnessSensorController != null) {
            this.mScreenOffBrightnessSensorController.dump(
                    new IndentingPrintWriter(printWriter, " "));
        }
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final String getName() {
        return "AutoBrightnessFallbackStrategy";
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final int getReason() {
        return 9;
    }

    public final void setupAutoBrightnessFallbackSensor(
            SensorManager sensorManager,
            DisplayDeviceConfig displayDeviceConfig,
            Handler handler,
            BrightnessMappingStrategy brightnessMappingStrategy,
            boolean z,
            int i) {
        this.mIsEnabled = z;
        this.mLeadDisplayId = i;
        ScreenOffBrightnessSensorController screenOffBrightnessSensorController =
                this.mScreenOffBrightnessSensorController;
        if (screenOffBrightnessSensorController != null) {
            screenOffBrightnessSensorController.setLightSensorEnabled(false);
            this.mScreenOffBrightnessSensorController = null;
        }
        Sensor screenOffBrightnessSensor =
                this.mInjector.getScreenOffBrightnessSensor(sensorManager, displayDeviceConfig);
        this.mScreenOffBrightnessSensor = screenOffBrightnessSensor;
        int[] iArr = displayDeviceConfig.mScreenOffBrightnessSensorValueToLux;
        if (screenOffBrightnessSensor == null || iArr == null) {
            return;
        }
        this.mScreenOffBrightnessSensorController =
                this.mInjector.getScreenOffBrightnessSensorController(
                        sensorManager,
                        screenOffBrightnessSensor,
                        handler,
                        new DisplayPowerController$$ExternalSyntheticLambda11(0),
                        iArr,
                        brightnessMappingStrategy);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0016, code lost:

       if (r5.mAllowAutoBrightnessWhileDozingConfig != false) goto L17;
    */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x001b, code lost:

       if (r4.mLeadDisplayId != (-1)) goto L17;
    */
    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void strategySelectionPostProcessor(
            com.android.server.display.brightness.StrategySelectionNotifyRequest r5) {
        /*
            r4 = this;
            com.android.server.display.ScreenOffBrightnessSensorController r0 = r4.mScreenOffBrightnessSensorController
            if (r0 == 0) goto L22
            int r1 = r5.mTargetDisplayState
            boolean r2 = r5.mIsAutoBrightnessEnabled
            if (r2 == 0) goto L1e
            boolean r2 = r4.mIsEnabled
            if (r2 == 0) goto L1e
            r2 = 1
            if (r1 == r2) goto L18
            r3 = 3
            if (r1 != r3) goto L1e
            boolean r5 = r5.mAllowAutoBrightnessWhileDozingConfig
            if (r5 != 0) goto L1e
        L18:
            int r4 = r4.mLeadDisplayId
            r5 = -1
            if (r4 != r5) goto L1e
            goto L1f
        L1e:
            r2 = 0
        L1f:
            r0.setLightSensorEnabled(r2)
        L22:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.brightness.strategy.AutoBrightnessFallbackStrategy.strategySelectionPostProcessor(com.android.server.display.brightness.StrategySelectionNotifyRequest):void");
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final DisplayBrightnessState updateBrightness(
            StrategyExecutionRequest strategyExecutionRequest) {
        float automaticScreenBrightness =
                this.mScreenOffBrightnessSensorController.getAutomaticScreenBrightness();
        BrightnessReason brightnessReason = new BrightnessReason();
        brightnessReason.setReason(automaticScreenBrightness, 9);
        DisplayBrightnessState.Builder builder = new DisplayBrightnessState.Builder();
        builder.mBrightness = automaticScreenBrightness;
        builder.mSdrBrightness = automaticScreenBrightness;
        builder.mBrightnessReason = brightnessReason;
        builder.mDisplayBrightnessStrategyName = "AutoBrightnessFallbackStrategy";
        builder.mShouldUpdateScreenBrightnessSetting =
                automaticScreenBrightness != strategyExecutionRequest.mCurrentScreenBrightness;
        return new DisplayBrightnessState(builder);
    }
}
