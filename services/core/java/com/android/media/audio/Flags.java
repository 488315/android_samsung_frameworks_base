package com.android.media.audio;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
