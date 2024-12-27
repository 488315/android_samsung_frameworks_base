package com.android.server.display.feature;

import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.display.feature.flags.Flags;
import com.android.server.display.utils.DebugUtils;

import java.io.PrintWriter;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DisplayManagerFlags {
    public final FlagState mAdaptiveToneImprovements1;
    public final FlagState mAdaptiveToneImprovements2;
    public final FlagState mAlwaysRotateDisplayDevice;
    public final FlagState mAutoBrightnessModesFlagState;
    public final FlagState mBackUpSmoothDisplayAndForcePeakRefreshRateFlagState;
    public final FlagState mBrightnessIntRangeUserPerceptionFlagState;
    public final FlagState mBrightnessWearBedtimeModeClamperFlagState;
    public final FlagState mConnectedDisplayErrorHandlingFlagState;
    public final FlagState mConnectedDisplayManagementFlagState;
    public final FlagState mDisplayOffloadFlagState;
    public final FlagState mEvenDimmerFlagState;
    public final FlagState mExternalDisplayLimitModeState;
    public final FlagState mFastHdrTransitions;
    public final FlagState mHdrClamperFlagState;
    public final FlagState mIdleScreenRefreshRateTimeout;
    public final FlagState mIgnoreAppPreferredRefreshRate;
    public final FlagState mNbmControllerFlagState;
    public final FlagState mOffloadControlsDozeAutoBrightness;
    public final FlagState mOffloadDozeOverrideHoldsWakelock;
    public final FlagState mPeakRefreshRatePhysicalLimit;
    public final FlagState mPixelAnisotropyCorrectionEnabled;
    public final FlagState mPortInDisplayLayoutFlagState;
    public final FlagState mPowerThrottlingClamperFlagState;
    public final FlagState mRefactorDisplayPowerController;
    public final FlagState mRefreshRateVotingTelemetry;
    public final FlagState mResolutionBackupRestore;
    public final FlagState mRestrictDisplayModes;
    public final FlagState mSensorBasedBrightnessThrottling;
    public final FlagState mSmallAreaDetectionFlagState;
    public final FlagState mSynthetic60hzModes;
    public final FlagState mUseFusionProxSensor;
    public final FlagState mVsyncLowLightVote;
    public final FlagState mVsyncLowPowerVote;
    public static final String TAG = "DisplayManagerFlags";
    public static final boolean DEBUG = DebugUtils.isDebuggable(TAG);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FlagState {
        public boolean mEnabled;
        public boolean mEnabledSet;
        public final Supplier mFlagFunction;
        public final String mName;

        public FlagState(String str, Supplier supplier) {
            this.mName = str;
            this.mFlagFunction = supplier;
        }

        public final boolean isEnabled() {
            boolean z = this.mEnabledSet;
            String str = this.mName;
            if (z) {
                if (DisplayManagerFlags.DEBUG) {
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(
                            DisplayManagerFlags.TAG,
                            Preconditions$$ExternalSyntheticOutline0.m(
                                    str, ": mEnabled. Recall = "),
                            this.mEnabled);
                }
                return this.mEnabled;
            }
            boolean booleanValue = ((Boolean) this.mFlagFunction.get()).booleanValue();
            if (Build.IS_ENG || Build.IS_USERDEBUG) {
                booleanValue =
                        SystemProperties.getBoolean(
                                "persist.sys." + str + "-override", booleanValue);
            }
            this.mEnabled = booleanValue;
            if (DisplayManagerFlags.DEBUG) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        DisplayManagerFlags.TAG,
                        Preconditions$$ExternalSyntheticOutline0.m(
                                str, ": mEnabled. Flag value = "),
                        this.mEnabled);
            }
            this.mEnabledSet = true;
            return this.mEnabled;
        }

        public final String toString() {
            String str = this.mName;
            int length = str.length();
            return TextUtils.substring(str, 41, length)
                    + ": "
                    + TextUtils.formatSimple(
                            "%" + (93 - length) + "s%s",
                            new Object[] {" ", Boolean.valueOf(isEnabled())})
                    + " (def:"
                    + this.mFlagFunction.get()
                    + ")";
        }
    }

    public DisplayManagerFlags() {
        final int i = 0;
        this.mPortInDisplayLayoutFlagState =
                new FlagState(
                        "com.android.server.display.feature.flags.enable_port_in_display_layout",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i2 = 3;
        this.mConnectedDisplayManagementFlagState =
                new FlagState(
                        "com.android.server.display.feature.flags.enable_connected_display_management",
                        new Supplier() { // from class:
                            // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i2) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i3 = 14;
        this.mNbmControllerFlagState =
                new FlagState(
                        "com.android.server.display.feature.flags.enable_nbm_controller",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i3) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i4 = 18;
        this.mHdrClamperFlagState =
                new FlagState(
                        "com.android.server.display.feature.flags.enable_hdr_clamper",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i4) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i5 = 19;
        this.mAdaptiveToneImprovements1 =
                new FlagState(
                        "com.android.server.display.feature.flags.enable_adaptive_tone_improvements_1",
                        new Supplier() { // from class:
                            // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i5) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i6 = 20;
        this.mAdaptiveToneImprovements2 =
                new FlagState(
                        "com.android.server.display.feature.flags.enable_adaptive_tone_improvements_2",
                        new Supplier() { // from class:
                            // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i6) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i7 = 22;
        this.mDisplayOffloadFlagState =
                new FlagState(
                        "com.android.server.display.feature.flags.enable_display_offload",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i7) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i8 = 23;
        this.mExternalDisplayLimitModeState =
                new FlagState(
                        "com.android.server.display.feature.flags.enable_mode_limit_for_external_display",
                        new Supplier() { // from class:
                            // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i8) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i9 = 24;
        this.mConnectedDisplayErrorHandlingFlagState =
                new FlagState(
                        "com.android.server.display.feature.flags.enable_connected_display_error_handling",
                        new Supplier() { // from class:
                            // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i9) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i10 = 25;
        this.mBackUpSmoothDisplayAndForcePeakRefreshRateFlagState =
                new FlagState(
                        "com.android.server.display.feature.flags.back_up_smooth_display_and_force_peak_refresh_rate",
                        new Supplier() { // from class:
                            // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i10) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i11 = 21;
        this.mPowerThrottlingClamperFlagState =
                new FlagState(
                        "com.android.server.display.feature.flags.enable_power_throttling_clamper",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i11) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i12 = 26;
        this.mEvenDimmerFlagState =
                new FlagState(
                        "com.android.server.display.feature.flags.even_dimmer",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i12) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i13 = 27;
        this.mSmallAreaDetectionFlagState =
                new FlagState(
                        "com.android.graphics.surfaceflinger.flags.enable_small_area_detection",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i13) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i14 = 28;
        this.mBrightnessIntRangeUserPerceptionFlagState =
                new FlagState(
                        "com.android.server.display.feature.flags.brightness_int_range_user_perception",
                        new Supplier() { // from class:
                            // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i14) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i15 = 29;
        this.mRestrictDisplayModes =
                new FlagState(
                        "com.android.server.display.feature.flags.enable_restrict_display_modes",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i15) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i16 = 0;
        this.mResolutionBackupRestore =
                new FlagState(
                        "com.android.server.display.feature.flags.resolution_backup_restore",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda7
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i16) {
                                    case 0:
                                        return Boolean.valueOf(Flags.resolutionBackupRestore());
                                    case 1:
                                        return Boolean.valueOf(Flags.enableVsyncLowPowerVote());
                                    default:
                                        return Boolean.valueOf(Flags.enableVsyncLowLightVote());
                                }
                            }
                        });
        final int i17 = 1;
        this.mVsyncLowPowerVote =
                new FlagState(
                        "com.android.server.display.feature.flags.enable_vsync_low_power_vote",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda7
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i17) {
                                    case 0:
                                        return Boolean.valueOf(Flags.resolutionBackupRestore());
                                    case 1:
                                        return Boolean.valueOf(Flags.enableVsyncLowPowerVote());
                                    default:
                                        return Boolean.valueOf(Flags.enableVsyncLowLightVote());
                                }
                            }
                        });
        final int i18 = 2;
        this.mVsyncLowLightVote =
                new FlagState(
                        "com.android.server.display.feature.flags.enable_vsync_low_light_vote",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda7
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i18) {
                                    case 0:
                                        return Boolean.valueOf(Flags.resolutionBackupRestore());
                                    case 1:
                                        return Boolean.valueOf(Flags.enableVsyncLowPowerVote());
                                    default:
                                        return Boolean.valueOf(Flags.enableVsyncLowLightVote());
                                }
                            }
                        });
        final int i19 = 1;
        this.mBrightnessWearBedtimeModeClamperFlagState =
                new FlagState(
                        "com.android.server.display.feature.flags.brightness_wear_bedtime_mode_clamper",
                        new Supplier() { // from class:
                            // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i19) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i20 = 2;
        this.mAutoBrightnessModesFlagState =
                new FlagState(
                        "com.android.server.display.feature.flags.auto_brightness_modes",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i20) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i21 = 4;
        this.mFastHdrTransitions =
                new FlagState(
                        "com.android.server.display.feature.flags.fast_hdr_transitions",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i21) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i22 = 5;
        this.mAlwaysRotateDisplayDevice =
                new FlagState(
                        "com.android.server.display.feature.flags.always_rotate_display_device",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i22) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i23 = 6;
        this.mRefreshRateVotingTelemetry =
                new FlagState(
                        "com.android.server.display.feature.flags.refresh_rate_voting_telemetry",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i23) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i24 = 7;
        this.mPixelAnisotropyCorrectionEnabled =
                new FlagState(
                        "com.android.server.display.feature.flags.enable_pixel_anisotropy_correction",
                        new Supplier() { // from class:
                            // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i24) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i25 = 8;
        this.mSensorBasedBrightnessThrottling =
                new FlagState(
                        "com.android.server.display.feature.flags.sensor_based_brightness_throttling",
                        new Supplier() { // from class:
                            // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i25) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i26 = 9;
        this.mIdleScreenRefreshRateTimeout =
                new FlagState(
                        "com.android.server.display.feature.flags.idle_screen_refresh_rate_timeout",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i26) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i27 = 10;
        this.mRefactorDisplayPowerController =
                new FlagState(
                        "com.android.server.display.feature.flags.refactor_display_power_controller",
                        new Supplier() { // from class:
                            // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i27) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i28 = 11;
        this.mUseFusionProxSensor =
                new FlagState(
                        "com.android.server.display.feature.flags.use_fusion_prox_sensor",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i28) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i29 = 12;
        this.mOffloadControlsDozeAutoBrightness =
                new FlagState(
                        "com.android.server.display.feature.flags.offload_controls_doze_auto_brightness",
                        new Supplier() { // from class:
                            // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i29) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i30 = 13;
        this.mPeakRefreshRatePhysicalLimit =
                new FlagState(
                        "com.android.server.display.feature.flags.enable_peak_refresh_rate_physical_limit",
                        new Supplier() { // from class:
                            // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i30) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i31 = 15;
        this.mIgnoreAppPreferredRefreshRate =
                new FlagState(
                        "com.android.server.display.feature.flags.ignore_app_preferred_refresh_rate_request",
                        new Supplier() { // from class:
                            // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i31) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i32 = 16;
        this.mSynthetic60hzModes =
                new FlagState(
                        "com.android.server.display.feature.flags.enable_synthetic_60hz_modes",
                        new Supplier() { // from class:
                                         // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i32) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
        final int i33 = 17;
        this.mOffloadDozeOverrideHoldsWakelock =
                new FlagState(
                        "com.android.server.display.feature.flags.offload_doze_override_holds_wakelock",
                        new Supplier() { // from class:
                            // com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i33) {
                                    case 0:
                                        return Boolean.valueOf(Flags.enablePortInDisplayLayout());
                                    case 1:
                                        return Boolean.valueOf(
                                                Flags.brightnessWearBedtimeModeClamper());
                                    case 2:
                                        return Boolean.valueOf(Flags.autoBrightnessModes());
                                    case 3:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayManagement());
                                    case 4:
                                        return Boolean.valueOf(Flags.fastHdrTransitions());
                                    case 5:
                                        return Boolean.valueOf(Flags.alwaysRotateDisplayDevice());
                                    case 6:
                                        return Boolean.valueOf(Flags.refreshRateVotingTelemetry());
                                    case 7:
                                        return Boolean.valueOf(
                                                Flags.enablePixelAnisotropyCorrection());
                                    case 8:
                                        return Boolean.valueOf(
                                                Flags.sensorBasedBrightnessThrottling());
                                    case 9:
                                        return Boolean.valueOf(
                                                Flags.idleScreenRefreshRateTimeout());
                                    case 10:
                                        return Boolean.valueOf(
                                                Flags.refactorDisplayPowerController());
                                    case 11:
                                        return Boolean.valueOf(Flags.useFusionProxSensor());
                                    case 12:
                                        return Boolean.valueOf(
                                                Flags.offloadControlsDozeAutoBrightness());
                                    case 13:
                                        return Boolean.valueOf(
                                                Flags.enablePeakRefreshRatePhysicalLimit());
                                    case 14:
                                        return Boolean.valueOf(Flags.enableNbmController());
                                    case 15:
                                        return Boolean.valueOf(
                                                Flags.ignoreAppPreferredRefreshRateRequest());
                                    case 16:
                                        return Boolean.valueOf(Flags.enableSynthetic60hzModes());
                                    case 17:
                                        return Boolean.valueOf(
                                                Flags.offloadDozeOverrideHoldsWakelock());
                                    case 18:
                                        return Boolean.valueOf(Flags.enableHdrClamper());
                                    case 19:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements1());
                                    case 20:
                                        return Boolean.valueOf(
                                                Flags.enableAdaptiveToneImprovements2());
                                    case 21:
                                        return Boolean.valueOf(
                                                Flags.enablePowerThrottlingClamper());
                                    case 22:
                                        return Boolean.valueOf(Flags.enableDisplayOffload());
                                    case 23:
                                        return Boolean.valueOf(
                                                Flags.enableModeLimitForExternalDisplay());
                                    case 24:
                                        return Boolean.valueOf(
                                                Flags.enableConnectedDisplayErrorHandling());
                                    case 25:
                                        return Boolean.valueOf(
                                                Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
                                    case 26:
                                        return Boolean.valueOf(Flags.evenDimmer());
                                    case 27:
                                        return Boolean.valueOf(
                                                com.android.graphics.surfaceflinger.flags.Flags
                                                        .enableSmallAreaDetection());
                                    case 28:
                                        return Boolean.valueOf(
                                                Flags.brightnessIntRangeUserPerception());
                                    default:
                                        return Boolean.valueOf(Flags.enableRestrictDisplayModes());
                                }
                            }
                        });
    }

    public final boolean areAutoBrightnessModesEnabled() {
        return this.mAutoBrightnessModesFlagState.isEnabled();
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder m =
                DisplayManagerFlags$$ExternalSyntheticOutline0.m(
                        DisplayManagerFlags$$ExternalSyntheticOutline0.m(
                                DisplayManagerFlags$$ExternalSyntheticOutline0.m(
                                        DisplayManagerFlags$$ExternalSyntheticOutline0.m(
                                                DisplayManagerFlags$$ExternalSyntheticOutline0.m(
                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                .m(
                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                .m(
                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                .m(
                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                .m(
                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                .m(
                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                .m(
                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                .m(
                                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                                .m(
                                                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                                                .m(
                                                                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                                                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                                                                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                                                                                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                                                                                                                                                                                                                                        DisplayManagerFlags$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                                                                                                                                                                                                                                                        BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                                                                                                                                                                                                                .m$1(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        "DisplayManagerFlags:",
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        " "),
                                                                                                                                                                                                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                .mAdaptiveToneImprovements1,
                                                                                                                                                                                                                                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                                                                                                                                                                                                                                        " "),
                                                                                                                                                                                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                                                                                                                                                                                .mAdaptiveToneImprovements2,
                                                                                                                                                                                                                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                                                                                                                                                                                                                        " "),
                                                                                                                                                                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                                                                                                                                                                .mBackUpSmoothDisplayAndForcePeakRefreshRateFlagState,
                                                                                                                                                                                                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                                                                                                                                                                                                        " "),
                                                                                                                                                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                                                                                                                                                .mConnectedDisplayErrorHandlingFlagState,
                                                                                                                                                                                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                                                                                                                                                                                        " "),
                                                                                                                                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                                                                                                                                .mConnectedDisplayManagementFlagState,
                                                                                                                                                                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                                                                                                                                                                        " "),
                                                                                                                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                                                                                                                .mDisplayOffloadFlagState,
                                                                                                                                                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                                                                                                                                                        " "),
                                                                                                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                                                                                                .mExternalDisplayLimitModeState,
                                                                                                                                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                                                                                                                                        " "),
                                                                                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                                                                                .mHdrClamperFlagState,
                                                                                                                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                                                                                                                        " "),
                                                                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                                                                .mNbmControllerFlagState,
                                                                                                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                                                                                                        " "),
                                                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                                                .mPowerThrottlingClamperFlagState,
                                                                                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                                                                                        " "),
                                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                                .mEvenDimmerFlagState,
                                                                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                                                                        " "),
                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                .mSmallAreaDetectionFlagState,
                                                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                                                        " "),
                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                .mBrightnessIntRangeUserPerceptionFlagState,
                                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                                        " "),
                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                .mRestrictDisplayModes,
                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                        " "),
                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                .mBrightnessWearBedtimeModeClamperFlagState,
                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                        " "),
                                                                                                                                                                                                        this
                                                                                                                                                                                                                .mAutoBrightnessModesFlagState,
                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                        " "),
                                                                                                                                                                                        this
                                                                                                                                                                                                .mFastHdrTransitions,
                                                                                                                                                                                        printWriter,
                                                                                                                                                                                        " "),
                                                                                                                                                                        this
                                                                                                                                                                                .mAlwaysRotateDisplayDevice,
                                                                                                                                                                        printWriter,
                                                                                                                                                                        " "),
                                                                                                                                                        this
                                                                                                                                                                .mRefreshRateVotingTelemetry,
                                                                                                                                                        printWriter,
                                                                                                                                                        " "),
                                                                                                                                        this
                                                                                                                                                .mPixelAnisotropyCorrectionEnabled,
                                                                                                                                        printWriter,
                                                                                                                                        " "),
                                                                                                                        this
                                                                                                                                .mSensorBasedBrightnessThrottling,
                                                                                                                        printWriter,
                                                                                                                        " "),
                                                                                                        this
                                                                                                                .mIdleScreenRefreshRateTimeout,
                                                                                                        printWriter,
                                                                                                        " "),
                                                                                        this
                                                                                                .mRefactorDisplayPowerController,
                                                                                        printWriter,
                                                                                        " "),
                                                                        this
                                                                                .mResolutionBackupRestore,
                                                                        printWriter,
                                                                        " "),
                                                        this.mUseFusionProxSensor,
                                                        printWriter,
                                                        " "),
                                                this.mOffloadControlsDozeAutoBrightness,
                                                printWriter,
                                                " "),
                                        this.mPeakRefreshRatePhysicalLimit,
                                        printWriter,
                                        " "),
                                this.mIgnoreAppPreferredRefreshRate,
                                printWriter,
                                " "),
                        this.mSynthetic60hzModes,
                        printWriter,
                        " ");
        m.append(this.mOffloadDozeOverrideHoldsWakelock);
        printWriter.println(m.toString());
    }

    public final String getUseFusionProxSensorFlagName() {
        return this.mUseFusionProxSensor.mName;
    }

    public final boolean ignoreAppPreferredRefreshRateRequest() {
        return this.mIgnoreAppPreferredRefreshRate.isEnabled();
    }

    public final boolean isAdaptiveTone1Enabled() {
        return this.mAdaptiveToneImprovements1.isEnabled();
    }

    public final boolean isAdaptiveTone2Enabled() {
        return this.mAdaptiveToneImprovements2.isEnabled();
    }

    public final boolean isAlwaysRotateDisplayDeviceEnabled() {
        return this.mAlwaysRotateDisplayDevice.isEnabled();
    }

    public final boolean isBackUpSmoothDisplayAndForcePeakRefreshRateEnabled() {
        return this.mBackUpSmoothDisplayAndForcePeakRefreshRateFlagState.isEnabled();
    }

    public final boolean isBrightnessIntRangeUserPerceptionEnabled() {
        return this.mBrightnessIntRangeUserPerceptionFlagState.isEnabled();
    }

    public final boolean isBrightnessWearBedtimeModeClamperEnabled() {
        return this.mBrightnessWearBedtimeModeClamperFlagState.isEnabled();
    }

    public final boolean isConnectedDisplayErrorHandlingEnabled() {
        return this.mConnectedDisplayErrorHandlingFlagState.isEnabled();
    }

    public final boolean isConnectedDisplayManagementEnabled() {
        return this.mConnectedDisplayManagementFlagState.isEnabled();
    }

    public final boolean isDisplayOffloadEnabled() {
        return this.mDisplayOffloadFlagState.isEnabled();
    }

    public final boolean isDisplayResolutionRangeVotingEnabled() {
        return this.mExternalDisplayLimitModeState.isEnabled();
    }

    public final boolean isDisplaysRefreshRatesSynchronizationEnabled() {
        return this.mExternalDisplayLimitModeState.isEnabled();
    }

    public final boolean isEvenDimmerEnabled() {
        return this.mEvenDimmerFlagState.isEnabled();
    }

    public final boolean isExternalDisplayLimitModeEnabled() {
        return this.mExternalDisplayLimitModeState.isEnabled();
    }

    public final boolean isFastHdrTransitionsEnabled() {
        return this.mFastHdrTransitions.isEnabled();
    }

    public final boolean isHdrClamperEnabled() {
        return this.mHdrClamperFlagState.isEnabled();
    }

    public final boolean isIdleScreenRefreshRateTimeoutEnabled() {
        return this.mIdleScreenRefreshRateTimeout.isEnabled();
    }

    public final boolean isNbmControllerEnabled() {
        return this.mNbmControllerFlagState.isEnabled();
    }

    public final boolean isOffloadDozeOverrideHoldsWakelockEnabled() {
        return this.mOffloadDozeOverrideHoldsWakelock.isEnabled();
    }

    public final boolean isPeakRefreshRatePhysicalLimitEnabled() {
        return this.mPeakRefreshRatePhysicalLimit.isEnabled();
    }

    public final boolean isPixelAnisotropyCorrectionInLogicalDisplayEnabled() {
        return this.mPixelAnisotropyCorrectionEnabled.isEnabled();
    }

    public final boolean isPortInDisplayLayoutEnabled() {
        return this.mPortInDisplayLayoutFlagState.isEnabled();
    }

    public final boolean isPowerThrottlingClamperEnabled() {
        return this.mPowerThrottlingClamperFlagState.isEnabled();
    }

    public final boolean isRefactorDisplayPowerControllerEnabled() {
        return this.mRefactorDisplayPowerController.isEnabled();
    }

    public final boolean isRefreshRateVotingTelemetryEnabled() {
        return this.mRefreshRateVotingTelemetry.isEnabled();
    }

    public final boolean isResolutionBackupRestoreEnabled() {
        return this.mResolutionBackupRestore.isEnabled();
    }

    public final boolean isRestrictDisplayModesEnabled() {
        return this.mRestrictDisplayModes.isEnabled();
    }

    public final boolean isSensorBasedBrightnessThrottlingEnabled() {
        return this.mSensorBasedBrightnessThrottling.isEnabled();
    }

    public final boolean isSmallAreaDetectionEnabled() {
        return this.mSmallAreaDetectionFlagState.isEnabled();
    }

    public final boolean isSynthetic60HzModesEnabled() {
        return this.mSynthetic60hzModes.isEnabled();
    }

    public final boolean isUseFusionProxSensorEnabled() {
        return this.mUseFusionProxSensor.isEnabled();
    }

    public final boolean isUserPreferredModeVoteEnabled() {
        return this.mExternalDisplayLimitModeState.isEnabled();
    }

    public final boolean isVsyncLowLightVoteEnabled() {
        return this.mVsyncLowLightVote.isEnabled();
    }

    public final boolean isVsyncLowPowerVoteEnabled() {
        return this.mVsyncLowPowerVote.isEnabled();
    }

    public final boolean offloadControlsDozeAutoBrightness() {
        return this.mOffloadControlsDozeAutoBrightness.isEnabled();
    }
}
