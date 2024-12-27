package com.android.media.audio;

public abstract class Flags {
    public static final FeatureFlagsImpl FEATURE_FLAGS = null;

    public static boolean absVolumeIndexFix() {
        return false;
    }

    public static boolean audioserverPermissions() {
        return false;
    }

    public static boolean disablePrescaleAbsoluteVolume() {
        return true;
    }

    public static boolean dsaOverBtLeAudio() {
        return true;
    }

    public static boolean portToPiidSimplification() {
        return false;
    }

    public static boolean ringerModeAffectsAlarm() {
        return true;
    }

    public static boolean setStreamVolumeOrder() {
        return true;
    }

    public static boolean vgsVssSyncMuteOrder() {
        return true;
    }
}
