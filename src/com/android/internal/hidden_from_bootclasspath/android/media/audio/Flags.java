package com.android.internal.hidden_from_bootclasspath.android.media.audio;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_AUTOMATIC_BT_DEVICE_TYPE = "android.media.audio.automatic_bt_device_type";
    public static final String FLAG_AUTO_PUBLIC_VOLUME_API_HARDENING = "android.media.audio.auto_public_volume_api_hardening";
    public static final String FLAG_CACHE_GET_STREAM_MIN_MAX_VOLUME = "android.media.audio.cache_get_stream_min_max_volume";
    public static final String FLAG_CACHE_GET_STREAM_VOLUME = "android.media.audio.cache_get_stream_volume";
    public static final String FLAG_CONCURRENT_AUDIO_RECORD_BYPASS_PERMISSION = "android.media.audio.concurrent_audio_record_bypass_permission";
    public static final String FLAG_DEPRECATE_STREAM_BT_SCO = "android.media.audio.deprecate_stream_bt_sco";
    public static final String FLAG_DOLBY_AC4_LEVEL4_ENCODING_API = "android.media.audio.dolby_ac4_level4_encoding_api";
    public static final String FLAG_ENABLE_MULTICHANNEL_GROUP_DEVICE = "android.media.audio.enable_multichannel_group_device";
    public static final String FLAG_ENABLE_RINGTONE_HAPTICS_CUSTOMIZATION = "android.media.audio.enable_ringtone_haptics_customization";
    public static final String FLAG_FEATURE_SPATIAL_AUDIO_HEADTRACKING_LOW_LATENCY = "android.media.audio.feature_spatial_audio_headtracking_low_latency";
    public static final String FLAG_FOCUS_EXCLUSIVE_WITH_RECORDING = "android.media.audio.focus_exclusive_with_recording";
    public static final String FLAG_FOCUS_FREEZE_TEST_API = "android.media.audio.focus_freeze_test_api";
    public static final String FLAG_FOREGROUND_AUDIO_CONTROL = "android.media.audio.foreground_audio_control";
    public static final String FLAG_HARDENING_PERMISSION_API = "android.media.audio.hardening_permission_api";
    public static final String FLAG_HARDENING_PERMISSION_SPA = "android.media.audio.hardening_permission_spa";
    public static final String FLAG_IAMF_DEFINITIONS_API = "android.media.audio.iamf_definitions_api";
    public static final String FLAG_LOUDNESS_CONFIGURATOR_API = "android.media.audio.loudness_configurator_api";
    public static final String FLAG_MUTED_BY_PORT_VOLUME_API = "android.media.audio.muted_by_port_volume_api";
    public static final String FLAG_MUTE_BACKGROUND_AUDIO = "android.media.audio.mute_background_audio";
    public static final String FLAG_REGISTER_VOLUME_CALLBACK_API_HARDENING = "android.media.audio.register_volume_callback_api_hardening";
    public static final String FLAG_RINGTONE_USER_URI_CHECK = "android.media.audio.ringtone_user_uri_check";
    public static final String FLAG_ROUTED_DEVICE_IDS = "android.media.audio.routed_device_ids";
    public static final String FLAG_RO_FOREGROUND_AUDIO_CONTROL = "android.media.audio.ro_foreground_audio_control";
    public static final String FLAG_RO_VOLUME_RINGER_API_HARDENING = "android.media.audio.ro_volume_ringer_api_hardening";
    public static final String FLAG_SCO_MANAGED_BY_AUDIO = "android.media.audio.sco_managed_by_audio";
    public static final String FLAG_SONY_360RA_MPEGH_3D_FORMAT = "android.media.audio.sony_360ra_mpegh_3d_format";
    public static final String FLAG_SPATIALIZER_CAPABILITIES = "android.media.audio.spatializer_capabilities";
    public static final String FLAG_SPATIAL_AUDIO_SETTINGS_VERSIONING = "android.media.audio.spatial_audio_settings_versioning";
    public static final String FLAG_SPEAKER_CLEANUP_USAGE = "android.media.audio.speaker_cleanup_usage";
    public static final String FLAG_SPEAKER_LAYOUT_API = "android.media.audio.speaker_layout_api";
    public static final String FLAG_SUPPORTED_DEVICE_TYPES_API = "android.media.audio.supported_device_types_api";
    public static final String FLAG_UNIFY_ABSOLUTE_VOLUME_MANAGEMENT = "android.media.audio.unify_absolute_volume_management";
    public static final String FLAG_VOLUME_RINGER_API_HARDENING = "android.media.audio.volume_ringer_api_hardening";

    public static boolean autoPublicVolumeApiHardening() {
        return FEATURE_FLAGS.autoPublicVolumeApiHardening();
    }

    public static boolean automaticBtDeviceType() {
        return FEATURE_FLAGS.automaticBtDeviceType();
    }

    public static boolean cacheGetStreamMinMaxVolume() {
        return FEATURE_FLAGS.cacheGetStreamMinMaxVolume();
    }

    public static boolean cacheGetStreamVolume() {
        return FEATURE_FLAGS.cacheGetStreamVolume();
    }

    public static boolean concurrentAudioRecordBypassPermission() {
        return FEATURE_FLAGS.concurrentAudioRecordBypassPermission();
    }

    public static boolean deprecateStreamBtSco() {
        return FEATURE_FLAGS.deprecateStreamBtSco();
    }

    public static boolean dolbyAc4Level4EncodingApi() {
        return FEATURE_FLAGS.dolbyAc4Level4EncodingApi();
    }

    public static boolean enableMultichannelGroupDevice() {
        return FEATURE_FLAGS.enableMultichannelGroupDevice();
    }

    public static boolean enableRingtoneHapticsCustomization() {
        return FEATURE_FLAGS.enableRingtoneHapticsCustomization();
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

    public static boolean hardeningPermissionApi() {
        return FEATURE_FLAGS.hardeningPermissionApi();
    }

    public static boolean hardeningPermissionSpa() {
        return FEATURE_FLAGS.hardeningPermissionSpa();
    }

    public static boolean iamfDefinitionsApi() {
        return FEATURE_FLAGS.iamfDefinitionsApi();
    }

    public static boolean loudnessConfiguratorApi() {
        return FEATURE_FLAGS.loudnessConfiguratorApi();
    }

    public static boolean muteBackgroundAudio() {
        return FEATURE_FLAGS.muteBackgroundAudio();
    }

    public static boolean mutedByPortVolumeApi() {
        return FEATURE_FLAGS.mutedByPortVolumeApi();
    }

    public static boolean registerVolumeCallbackApiHardening() {
        return FEATURE_FLAGS.registerVolumeCallbackApiHardening();
    }

    public static boolean ringtoneUserUriCheck() {
        return FEATURE_FLAGS.ringtoneUserUriCheck();
    }

    public static boolean roForegroundAudioControl() {
        return FEATURE_FLAGS.roForegroundAudioControl();
    }

    public static boolean roVolumeRingerApiHardening() {
        return FEATURE_FLAGS.roVolumeRingerApiHardening();
    }

    public static boolean routedDeviceIds() {
        return FEATURE_FLAGS.routedDeviceIds();
    }

    public static boolean scoManagedByAudio() {
        return FEATURE_FLAGS.scoManagedByAudio();
    }

    public static boolean sony360raMpegh3dFormat() {
        return FEATURE_FLAGS.sony360raMpegh3dFormat();
    }

    public static boolean spatialAudioSettingsVersioning() {
        return FEATURE_FLAGS.spatialAudioSettingsVersioning();
    }

    public static boolean spatializerCapabilities() {
        return FEATURE_FLAGS.spatializerCapabilities();
    }

    public static boolean speakerCleanupUsage() {
        return FEATURE_FLAGS.speakerCleanupUsage();
    }

    public static boolean speakerLayoutApi() {
        return FEATURE_FLAGS.speakerLayoutApi();
    }

    public static boolean supportedDeviceTypesApi() {
        return FEATURE_FLAGS.supportedDeviceTypesApi();
    }

    public static boolean unifyAbsoluteVolumeManagement() {
        return FEATURE_FLAGS.unifyAbsoluteVolumeManagement();
    }

    public static boolean volumeRingerApiHardening() {
        return FEATURE_FLAGS.volumeRingerApiHardening();
    }
}
