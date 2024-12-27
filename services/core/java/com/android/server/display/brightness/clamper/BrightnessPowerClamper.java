package com.android.server.display.brightness.clamper;

import android.os.Handler;
import android.provider.DeviceConfigInterface;
import android.util.Slog;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;
import com.android.server.display.DisplayDeviceConfig;
import com.android.server.display.feature.DeviceConfigParameterProvider;
import com.android.server.display.utils.DeviceConfigParsingUtils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public final class BrightnessPowerClamper extends BrightnessClamper {
    public final DeviceConfigParameterProvider mConfigParameterProvider;
    public float mCurrentAvgPowerConsumed;
    public int mCurrentThermalLevel;
    public String mDataId;
    public final BrightnessPowerClamper$$ExternalSyntheticLambda1 mDataPointMapper;
    public final BrightnessPowerClamper$$ExternalSyntheticLambda2 mDataSetMapper;
    public final Injector mInjector;
    public PmicMonitor mPmicMonitor;
    public DisplayDeviceConfig.PowerThrottlingConfigData mPowerThrottlingConfigData;
    public DisplayDeviceConfig.PowerThrottlingData mPowerThrottlingDataActive;
    public DisplayDeviceConfig.PowerThrottlingData mPowerThrottlingDataFromDDC;
    public Map mPowerThrottlingDataOverride;
    public String mUniqueDisplayId;

    class Injector {}

    public interface PowerData {}

    public BrightnessPowerClamper(
            Injector injector,
            Handler handler,
            BrightnessClamperController.ClamperChangeListener clamperChangeListener,
            PowerData powerData) {
        super(handler, clamperChangeListener);
        this.mPowerThrottlingDataOverride = Map.of();
        this.mPowerThrottlingDataFromDDC = null;
        this.mPowerThrottlingDataActive = null;
        this.mPowerThrottlingConfigData = null;
        this.mCurrentThermalLevel = 0;
        this.mCurrentAvgPowerConsumed = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mUniqueDisplayId = null;
        this.mDataId = null;
        this.mDataPointMapper = new BrightnessPowerClamper$$ExternalSyntheticLambda1();
        this.mDataSetMapper = new BrightnessPowerClamper$$ExternalSyntheticLambda2();
        this.mInjector = injector;
        injector.getClass();
        this.mConfigParameterProvider =
                new DeviceConfigParameterProvider(DeviceConfigInterface.REAL);
        handler.post(new BrightnessPowerClamper$$ExternalSyntheticLambda3(this, powerData, 0));
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public final void dump(PrintWriter printWriter) {
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                printWriter,
                                this.mUniqueDisplayId,
                                "  mCurrentThermalLevel=",
                                KillPolicyManager$$ExternalSyntheticOutline0.m(
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                                printWriter,
                                                "BrightnessPowerClamper:",
                                                "  mCurrentAvgPowerConsumed="),
                                        this.mCurrentAvgPowerConsumed,
                                        printWriter,
                                        "  mUniqueDisplayId=")),
                        this.mCurrentThermalLevel,
                        printWriter,
                        "  mPowerThrottlingDataFromDDC=");
        DisplayDeviceConfig.PowerThrottlingData powerThrottlingData =
                this.mPowerThrottlingDataFromDDC;
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                m,
                powerThrottlingData == null ? "null" : powerThrottlingData.toString(),
                printWriter);
        super.dump(printWriter);
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public final BrightnessClamper.Type getType() {
        return BrightnessClamper.Type.POWER;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public final void onDeviceConfigChanged() {
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.display.brightness.clamper.BrightnessPowerClamper$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        BrightnessPowerClamper brightnessPowerClamper = BrightnessPowerClamper.this;
                        brightnessPowerClamper.mPowerThrottlingDataOverride =
                                DeviceConfigParsingUtils.parseDeviceConfigMap(
                                        brightnessPowerClamper.mConfigParameterProvider
                                                .mDeviceConfig.getString(
                                                "display_manager",
                                                "power_throttling_data",
                                                (String) null),
                                        brightnessPowerClamper.mDataPointMapper,
                                        brightnessPowerClamper.mDataSetMapper);
                        brightnessPowerClamper.recalculateActiveData();
                    }
                });
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public final void onDisplayChanged(Object obj) {
        this.mHandler.post(
                new BrightnessPowerClamper$$ExternalSyntheticLambda3(this, (PowerData) obj, 1));
    }

    public final void recalculateActiveData() {
        ScheduledFuture scheduledFuture;
        String str = this.mUniqueDisplayId;
        if (str == null || this.mDataId == null) {
            return;
        }
        DisplayDeviceConfig.PowerThrottlingData powerThrottlingData =
                (DisplayDeviceConfig.PowerThrottlingData)
                        ((Map) this.mPowerThrottlingDataOverride.getOrDefault(str, Map.of()))
                                .getOrDefault(this.mDataId, this.mPowerThrottlingDataFromDDC);
        this.mPowerThrottlingDataActive = powerThrottlingData;
        if (powerThrottlingData != null) {
            PmicMonitor pmicMonitor = this.mPmicMonitor;
            if (pmicMonitor != null) {
                ScheduledFuture scheduledFuture2 = pmicMonitor.mPmicMonitorFuture;
                if (scheduledFuture2 != null) {
                    scheduledFuture2.cancel(true);
                    pmicMonitor.mPmicMonitorFuture = null;
                }
                this.mPmicMonitor.start();
            }
        } else {
            PmicMonitor pmicMonitor2 = this.mPmicMonitor;
            if (pmicMonitor2 != null
                    && (scheduledFuture = pmicMonitor2.mPmicMonitorFuture) != null) {
                scheduledFuture.cancel(true);
                pmicMonitor2.mPmicMonitorFuture = null;
            }
        }
        recalculateBrightnessCap();
    }

    public final void recalculateBrightnessCap() {
        float f;
        boolean z;
        int i = this.mCurrentThermalLevel;
        DisplayDeviceConfig.PowerThrottlingData powerThrottlingData =
                this.mPowerThrottlingDataActive;
        if (powerThrottlingData != null) {
            Iterator it = ((ArrayList) powerThrottlingData.throttlingLevels).iterator();
            f = 0.0f;
            while (it.hasNext()) {
                DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel throttlingLevel =
                        (DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel) it.next();
                if (throttlingLevel.thermalStatus > i) {
                    break;
                } else {
                    f = throttlingLevel.powerQuotaMilliWatts;
                }
            }
        } else {
            f = 0.0f;
        }
        if (this.mPowerThrottlingDataActive == null) {
            return;
        }
        float f2 = 1.0f;
        if (f > FullScreenMagnificationGestureHandler.MAX_SCALE) {
            float f3 = this.mCurrentAvgPowerConsumed;
            if (f3 > f) {
                f2 =
                        Math.max(
                                (f / f3) * 1.0f,
                                this.mPowerThrottlingConfigData.brightnessLowestCapAllowed);
                z = true;
                if (this.mBrightnessCap == f2 || this.mIsActive != z) {
                    this.mIsActive = z;
                    this.mBrightnessCap = f2;
                    this.mChangeListener.onChanged();
                }
                return;
            }
        }
        z = false;
        if (this.mBrightnessCap == f2) {}
        this.mIsActive = z;
        this.mBrightnessCap = f2;
        this.mChangeListener.onChanged();
    }

    public final void setDisplayData(PowerData powerData) {
        this.mUniqueDisplayId =
                ((BrightnessClamperController.DisplayDeviceData) powerData).mUniqueDisplayId;
        BrightnessClamperController.DisplayDeviceData displayDeviceData =
                (BrightnessClamperController.DisplayDeviceData) powerData;
        String str = displayDeviceData.mPowerThrottlingDataId;
        this.mDataId = str;
        DisplayDeviceConfig displayDeviceConfig = displayDeviceData.mDisplayDeviceConfig;
        DisplayDeviceConfig.PowerThrottlingData powerThrottlingData =
                (DisplayDeviceConfig.PowerThrottlingData)
                        ((HashMap) displayDeviceConfig.mPowerThrottlingDataMapByThrottlingId)
                                .get(str);
        this.mPowerThrottlingDataFromDDC = powerThrottlingData;
        if (powerThrottlingData == null && !"default".equals(this.mDataId)) {
            Slog.wtf(
                    "BrightnessPowerClamper",
                    "Power throttling data is missing for powerThrottlingDataId=" + this.mDataId);
        }
        DisplayDeviceConfig.PowerThrottlingConfigData powerThrottlingConfigData =
                displayDeviceConfig.mPowerThrottlingConfigData;
        this.mPowerThrottlingConfigData = powerThrottlingConfigData;
        if (powerThrottlingConfigData == null) {
            Slog.d(
                    "BrightnessPowerClamper",
                    "Power throttling data is missing for configuration data.");
        }
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public final void stop() {
        PmicMonitor pmicMonitor = this.mPmicMonitor;
        if (pmicMonitor != null) {
            pmicMonitor.mExecutor.shutdownNow();
        }
    }
}
