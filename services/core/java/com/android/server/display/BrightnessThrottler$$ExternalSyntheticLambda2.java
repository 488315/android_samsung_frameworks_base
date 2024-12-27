package com.android.server.display;

import android.hardware.display.BrightnessInfo;
import android.os.Temperature;
import android.util.Slog;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

public final /* synthetic */ class BrightnessThrottler$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BrightnessThrottler$$ExternalSyntheticLambda2(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        float f;
        switch (this.$r8$classId) {
            case 0:
                BrightnessThrottler brightnessThrottler = (BrightnessThrottler) this.f$0;
                PrintWriter printWriter = (PrintWriter) this.f$1;
                brightnessThrottler.getClass();
                printWriter.println("BrightnessThrottler:");
                StringBuilder m =
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                printWriter,
                                brightnessThrottler.mThermalBrightnessThrottlingDataId,
                                "  mThermalThrottlingData=",
                                new StringBuilder("  mThermalBrightnessThrottlingDataId="));
                m.append(brightnessThrottler.mThermalThrottlingData);
                printWriter.println(m.toString());
                StringBuilder m2 =
                        KillPolicyManager$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                printWriter,
                                                brightnessThrottler.mUniqueDisplayId,
                                                "  mThrottlingStatus=",
                                                new StringBuilder("  mUniqueDisplayId=")),
                                        brightnessThrottler.mThrottlingStatus,
                                        printWriter,
                                        "  mBrightnessCap="),
                                brightnessThrottler.mBrightnessCap,
                                printWriter,
                                "  mBrightnessMaxReason=");
                m2.append(
                        BrightnessInfo.briMaxReasonToString(
                                brightnessThrottler.mBrightnessMaxReason));
                printWriter.println(m2.toString());
                printWriter.println(
                        "  mDdcThermalThrottlingDataMap="
                                + brightnessThrottler.mDdcThermalThrottlingDataMap);
                printWriter.println(
                        "  mThermalBrightnessThrottlingDataOverride="
                                + brightnessThrottler.mThermalBrightnessThrottlingDataOverride);
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("  mThermalBrightnessThrottlingDataString="),
                        brightnessThrottler.mThermalBrightnessThrottlingDataString,
                        printWriter);
                BrightnessThrottler.SkinThermalStatusObserver skinThermalStatusObserver =
                        brightnessThrottler.mSkinThermalStatusObserver;
                skinThermalStatusObserver.getClass();
                printWriter.println("  SkinThermalStatusObserver:");
                StringBuilder m3 =
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("    mStarted: "),
                                skinThermalStatusObserver.mStarted,
                                printWriter,
                                "    mObserverTempSensor: ");
                m3.append(skinThermalStatusObserver.mObserverTempSensor);
                printWriter.println(m3.toString());
                if (skinThermalStatusObserver.mThermalService == null) {
                    printWriter.println("    ThermalService not available");
                    break;
                } else {
                    printWriter.println("    ThermalService available");
                    break;
                }
            default:
                BrightnessThrottler.SkinThermalStatusObserver skinThermalStatusObserver2 =
                        (BrightnessThrottler.SkinThermalStatusObserver) this.f$0;
                Temperature temperature = (Temperature) this.f$1;
                skinThermalStatusObserver2.getClass();
                int status = temperature.getStatus();
                BrightnessThrottler brightnessThrottler2 = BrightnessThrottler.this;
                if (brightnessThrottler2.mThrottlingStatus != status) {
                    brightnessThrottler2.mThrottlingStatus = status;
                    DisplayDeviceConfig.ThermalBrightnessThrottlingData
                            thermalBrightnessThrottlingData =
                                    brightnessThrottler2.mThermalThrottlingData;
                    if (thermalBrightnessThrottlingData != null) {
                        float f2 = 1.0f;
                        int i = 0;
                        if (status == -1 || thermalBrightnessThrottlingData == null) {
                            f = 1.0f;
                        } else {
                            Iterator it =
                                    ((ArrayList) thermalBrightnessThrottlingData.throttlingLevels)
                                            .iterator();
                            f = 1.0f;
                            while (it.hasNext()) {
                                DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel
                                        throttlingLevel =
                                                (DisplayDeviceConfig.ThermalBrightnessThrottlingData
                                                                .ThrottlingLevel)
                                                        it.next();
                                if (throttlingLevel.thermalStatus
                                        <= brightnessThrottler2.mThrottlingStatus) {
                                    f = throttlingLevel.brightness;
                                    i = 1;
                                }
                            }
                        }
                        if (brightnessThrottler2.mBrightnessCap != f
                                || brightnessThrottler2.mBrightnessMaxReason != i) {
                            if (f < FullScreenMagnificationGestureHandler.MAX_SCALE) {
                                Slog.e(
                                        "BrightnessThrottler",
                                        "brightness "
                                                + f
                                                + " is lower than the minimum possible brightness"
                                                + " 0.0");
                                f = 0.0f;
                            }
                            if (f > 1.0f) {
                                Slog.e(
                                        "BrightnessThrottler",
                                        "brightness "
                                                + f
                                                + " is higher than the maximum possible brightness"
                                                + " 1.0");
                            } else {
                                f2 = f;
                            }
                            brightnessThrottler2.mBrightnessCap = f2;
                            brightnessThrottler2.mBrightnessMaxReason = i;
                            if (BrightnessThrottler.DEBUG) {
                                Slog.d(
                                        "BrightnessThrottler",
                                        "State changed: mBrightnessCap = "
                                                + brightnessThrottler2.mBrightnessCap
                                                + ", mBrightnessMaxReason = "
                                                + BrightnessInfo.briMaxReasonToString(
                                                        brightnessThrottler2.mBrightnessMaxReason));
                            }
                            Runnable runnable = brightnessThrottler2.mThrottlingChangeCallback;
                            if (runnable != null) {
                                runnable.run();
                                break;
                            }
                        }
                    }
                }
                break;
        }
    }
}
