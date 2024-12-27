package com.android.server.display;

import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManager;
import android.os.SystemClock;
import android.util.Slog;
import android.util.TimeUtils;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.Iterator;

public final /* synthetic */ class HighBrightnessModeController$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HighBrightnessModeController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ HighBrightnessModeController$$ExternalSyntheticLambda1(
            HighBrightnessModeController highBrightnessModeController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = highBrightnessModeController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int[] supportedHdrTypes;
        switch (this.$r8$classId) {
            case 0:
                HighBrightnessModeController highBrightnessModeController = this.f$0;
                String str = (String) this.f$1;
                highBrightnessModeController.getClass();
                String upperCase = str.toUpperCase();
                upperCase.getClass();
                if (upperCase.equals("ON")) {
                    highBrightnessModeController.mSupportHdrSolution = true;
                    Slog.d("HighBrightnessModeController", "Enforce HighBrightnessMode @HDR");
                } else if (upperCase.equals("OFF")) {
                    highBrightnessModeController.mSupportHdrSolution = false;
                    Slog.d("HighBrightnessModeController", "Prevent HighBrightnessMode @HDR");
                } else {
                    boolean z = HighBrightnessModeController.FEATURE_SUPPORT_HDR_SOLUTION;
                    highBrightnessModeController.mSupportHdrSolution = z;
                    if (!z
                            && (supportedHdrTypes =
                                            ((DisplayManager)
                                                            highBrightnessModeController.mContext
                                                                    .getSystemService(
                                                                            DisplayManager.class))
                                                    .getDisplay(0)
                                                    .getHdrCapabilities()
                                                    .getSupportedHdrTypes())
                                    != null) {
                        for (int i : supportedHdrTypes) {
                            if (i == 2 || i == 4 || i == 3) {
                                highBrightnessModeController.mSupportHdrSolution = true;
                            }
                        }
                    }
                }
                HeimdAllFsService$$ExternalSyntheticOutline0.m(
                        "HighBrightnessModeController",
                        new StringBuilder("mSupportHdrSolution: "),
                        highBrightnessModeController.mSupportHdrSolution);
                break;
            default:
                HighBrightnessModeController highBrightnessModeController2 = this.f$0;
                PrintWriter printWriter = (PrintWriter) this.f$1;
                highBrightnessModeController2.getClass();
                printWriter.println("HighBrightnessModeController:");
                StringBuilder m =
                        KillPolicyManager$$ExternalSyntheticOutline0.m(
                                KillPolicyManager$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("  mBrightness="),
                                        highBrightnessModeController2.mBrightness,
                                        printWriter,
                                        "  mUnthrottledBrightness="),
                                highBrightnessModeController2.mUnthrottledBrightness,
                                printWriter,
                                "  mThrottlingReason=");
                m.append(
                        BrightnessInfo.briMaxReasonToString(
                                highBrightnessModeController2.mThrottlingReason));
                printWriter.println(m.toString());
                StringBuilder sb = new StringBuilder("  mCurrentMin=");
                float f = highBrightnessModeController2.mBrightnessMin;
                StringBuilder m2 =
                        KillPolicyManager$$ExternalSyntheticOutline0.m(
                                sb, f, printWriter, "  mCurrentMax=");
                m2.append(highBrightnessModeController2.getCurrentBrightnessMax());
                printWriter.println(m2.toString());
                StringBuilder sb2 = new StringBuilder("  mHbmMode=");
                sb2.append(BrightnessInfo.hbmToString(highBrightnessModeController2.mHbmMode));
                StringBuilder m3 =
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                printWriter,
                                highBrightnessModeController2.mHbmMode == 2
                                        ? "("
                                                + highBrightnessModeController2
                                                        .getHdrBrightnessValue()
                                                + ")"
                                        : "",
                                "  mHbmStatsState=",
                                sb2);
                int i2 = highBrightnessModeController2.mHbmStatsState;
                StringBuilder m4 =
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                printWriter,
                                i2 != 1
                                        ? i2 != 2
                                                ? i2 != 3 ? String.valueOf(i2) : "HBM_ON_SUNLIGHT"
                                                : "HBM_ON_HDR"
                                        : "HBM_OFF",
                                "  mHbmData=",
                                m3);
                m4.append(highBrightnessModeController2.mHbmData);
                printWriter.println(m4.toString());
                StringBuilder sb3 = new StringBuilder("  mAmbientLux=");
                sb3.append(highBrightnessModeController2.mAmbientLux);
                StringBuilder m5 =
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                        .m(
                                                                printWriter,
                                                                highBrightnessModeController2
                                                                                .mIsAutoBrightnessEnabled
                                                                        ? ""
                                                                        : " (old/invalid)",
                                                                "  mIsInAllowedAmbientRange=",
                                                                sb3),
                                                highBrightnessModeController2
                                                        .mIsInAllowedAmbientRange,
                                                printWriter,
                                                "  mIsAutoBrightnessEnabled="),
                                        highBrightnessModeController2.mIsAutoBrightnessEnabled,
                                        printWriter,
                                        "  mIsAutoBrightnessOffByState="),
                                highBrightnessModeController2.mIsAutoBrightnessOffByState,
                                printWriter,
                                "  mIsHdrLayerPresent=");
                m5.append(highBrightnessModeController2.mIsHdrLayerPresent);
                printWriter.println(m5.toString());
                printWriter.println("  mBrightnessMin=" + f);
                StringBuilder m6 =
                        KillPolicyManager$$ExternalSyntheticOutline0.m(
                                new StringBuilder("  mBrightnessMax="),
                                highBrightnessModeController2.mBrightnessMax,
                                printWriter,
                                "  remainingTime=");
                highBrightnessModeController2.mClock.getClass();
                m6.append(
                        highBrightnessModeController2.calculateRemainingTime(
                                SystemClock.uptimeMillis()));
                printWriter.println(m6.toString());
                StringBuilder m7 =
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("  mIsTimeAvailable= "),
                                        highBrightnessModeController2.mIsTimeAvailable,
                                        printWriter,
                                        "  mIsBlockedByLowPowerMode="),
                                highBrightnessModeController2.mIsBlockedByLowPowerMode,
                                printWriter,
                                "  width*height=");
                m7.append(highBrightnessModeController2.mWidth);
                m7.append("*");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        m7, highBrightnessModeController2.mHeight, printWriter);
                if (highBrightnessModeController2.mHighBrightnessModeMetadata != null) {
                    printWriter.println(
                            "  mRunningStartTimeMillis="
                                    + TimeUtils.formatUptime(
                                            highBrightnessModeController2
                                                    .mHighBrightnessModeMetadata
                                                    .mRunningStartTimeMillis));
                    printWriter.println("  mEvents=");
                    long uptimeMillis = SystemClock.uptimeMillis();
                    long j =
                            highBrightnessModeController2
                                    .mHighBrightnessModeMetadata
                                    .mRunningStartTimeMillis;
                    if (j != -1) {
                        printWriter.println(
                                "    event: ["
                                        + TimeUtils.formatUptime(j)
                                        + ", "
                                        + TimeUtils.formatUptime(uptimeMillis)
                                        + "] ("
                                        + TimeUtils.formatDuration(uptimeMillis - j)
                                        + ")");
                        uptimeMillis = j;
                    }
                    Iterator it =
                            highBrightnessModeController2.mHighBrightnessModeMetadata.mEvents
                                    .iterator();
                    while (it.hasNext()) {
                        HbmEvent hbmEvent = (HbmEvent) it.next();
                        if (uptimeMillis > hbmEvent.mEndTimeMillis) {
                            printWriter.println(
                                    "    event: [normal brightness]: "
                                            + TimeUtils.formatDuration(
                                                    uptimeMillis - hbmEvent.mEndTimeMillis));
                        }
                        long j2 = hbmEvent.mEndTimeMillis;
                        long j3 = hbmEvent.mStartTimeMillis;
                        printWriter.println(
                                "    event: ["
                                        + TimeUtils.formatUptime(j3)
                                        + ", "
                                        + TimeUtils.formatUptime(hbmEvent.mEndTimeMillis)
                                        + "] ("
                                        + TimeUtils.formatDuration(j2 - j3)
                                        + ")");
                        uptimeMillis = j3;
                    }
                    break;
                } else {
                    printWriter.println("  mHighBrightnessModeMetadata=null");
                    break;
                }
        }
    }
}
