package com.android.server.accessibility;

import android.provider.DeviceConfig;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class Flags {
    public static final FeatureFlagsImpl FEATURE_FLAGS = null;

    public static boolean clearDefaultFromA11yShortcutTargetServiceRestore() {
        return true;
    }

    public static boolean computeWindowChangesOnA11yV2() {
        return true;
    }

    public static boolean doNotResetKeyEventState() {
        return true;
    }

    public static boolean enableColorCorrectionSaturation() {
        if (!FeatureFlagsImpl.accessibility_is_cached) {
            try {
                FeatureFlagsImpl.enableColorCorrectionSaturation =
                        DeviceConfig.getProperties("accessibility", new String[0])
                                .getBoolean(
                                        "com.android.server.accessibility.enable_color_correction_saturation",
                                        true);
                FeatureFlagsImpl.accessibility_is_cached = true;
            } catch (NullPointerException e) {
                throw new RuntimeException(
                        "Cannot read value from namespace accessibility from DeviceConfig. It could"
                            + " be that the code using flag executed before SettingsProvider"
                            + " initialization. Please use fixed read-only flag by adding"
                            + " is_fixed_read_only: true in flag declaration.",
                        e);
            }
        }
        return FeatureFlagsImpl.enableColorCorrectionSaturation;
    }

    public static boolean enableHardwareShortcutDisablesWarning() {
        return false;
    }

    public static boolean enableMagnificationMultipleFingerMultipleTapGesture() {
        return false;
    }

    public static boolean enableMagnificationOneFingerPanningGesture() {
        return false;
    }

    public static boolean fullscreenFlingGesture() {
        return false;
    }

    public static boolean handleMultiDeviceInput() {
        return false;
    }

    public static boolean managerAvoidReceiverTimeout() {
        return true;
    }

    public static boolean managerPackageMonitorLogicFix() {
        return true;
    }

    public static boolean pinchZoomZeroMinSpan() {
        return true;
    }

    public static boolean proxyUseAppsOnVirtualDeviceListener() {
        return true;
    }

    public static boolean removeOnWindowInfosChangedHandler() {
        return false;
    }

    public static boolean sendA11yEventsBasedOnState() {
        return false;
    }

    public static boolean sendHoverEventsBasedOnEventStream() {
        return true;
    }

    public static boolean skipPackageChangeBeforeUserSwitch() {
        return false;
    }
}
