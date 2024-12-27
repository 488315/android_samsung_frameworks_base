package android.media.audio;

/* loaded from: classes2.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_AUTOMATIC_BT_DEVICE_TYPE =
            "android.media.audio.automatic_bt_device_type";
    public static final String FLAG_AUTO_PUBLIC_VOLUME_API_HARDENING =
            "android.media.audio.auto_public_volume_api_hardening";
    public static final String FLAG_FEATURE_SPATIAL_AUDIO_HEADTRACKING_LOW_LATENCY =
            "android.media.audio.feature_spatial_audio_headtracking_low_latency";
    public static final String FLAG_FOCUS_EXCLUSIVE_WITH_RECORDING =
            "android.media.audio.focus_exclusive_with_recording";
    public static final String FLAG_FOCUS_FREEZE_TEST_API =
            "android.media.audio.focus_freeze_test_api";
    public static final String FLAG_FOREGROUND_AUDIO_CONTROL =
            "android.media.audio.foreground_audio_control";
    public static final String FLAG_LOUDNESS_CONFIGURATOR_API =
            "android.media.audio.loudness_configurator_api";
    public static final String FLAG_MUTE_BACKGROUND_AUDIO =
            "android.media.audio.mute_background_audio";
    public static final String FLAG_RO_FOREGROUND_AUDIO_CONTROL =
            "android.media.audio.ro_foreground_audio_control";
    public static final String FLAG_RO_VOLUME_RINGER_API_HARDENING =
            "android.media.audio.ro_volume_ringer_api_hardening";
    public static final String FLAG_SCO_MANAGED_BY_AUDIO =
            "android.media.audio.sco_managed_by_audio";
    public static final String FLAG_SUPPORTED_DEVICE_TYPES_API =
            "android.media.audio.supported_device_types_api";
    public static final String FLAG_VOLUME_RINGER_API_HARDENING =
            "android.media.audio.volume_ringer_api_hardening";

    public static boolean autoPublicVolumeApiHardening() {
        return FEATURE_FLAGS.autoPublicVolumeApiHardening();
    }

    public static boolean automaticBtDeviceType() {
        return FEATURE_FLAGS.automaticBtDeviceType();
    }

    public static boolean featureSpatialAudioHeadtrackingLowLatency() {
        return FEATURE_FLAGS.featureSpatialAudioHeadtrackingLowLatency();
    }

    public static boolean focusExclusiveWithRecording() {
        return FEATURE_FLAGS.focusExclusiveWithRecording();
    }

    public static boolean focusFreezeTestApi() {
        return FEATURE_FLAGS.focusFreezeTestApi();
    }

    public static boolean foregroundAudioControl() {
        return FEATURE_FLAGS.foregroundAudioControl();
    }

    public static boolean loudnessConfiguratorApi() {
        return FEATURE_FLAGS.loudnessConfiguratorApi();
    }

    public static boolean muteBackgroundAudio() {
        return FEATURE_FLAGS.muteBackgroundAudio();
    }

    public static boolean roForegroundAudioControl() {
        return FEATURE_FLAGS.roForegroundAudioControl();
    }

    public static boolean roVolumeRingerApiHardening() {
        return FEATURE_FLAGS.roVolumeRingerApiHardening();
    }

    public static boolean scoManagedByAudio() {
        return FEATURE_FLAGS.scoManagedByAudio();
    }

    public static boolean supportedDeviceTypesApi() {
        return FEATURE_FLAGS.supportedDeviceTypesApi();
    }

    public static boolean volumeRingerApiHardening() {
        return FEATURE_FLAGS.volumeRingerApiHardening();
    }
}
