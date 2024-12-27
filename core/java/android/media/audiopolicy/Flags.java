package android.media.audiopolicy;

/* loaded from: classes2.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_AUDIO_MIX_OWNERSHIP =
            "android.media.audiopolicy.audio_mix_ownership";
    public static final String FLAG_AUDIO_MIX_POLICY_ORDERING =
            "android.media.audiopolicy.audio_mix_policy_ordering";
    public static final String FLAG_AUDIO_MIX_TEST_API =
            "android.media.audiopolicy.audio_mix_test_api";
    public static final String FLAG_AUDIO_POLICY_UPDATE_MIXING_RULES_API =
            "android.media.audiopolicy.audio_policy_update_mixing_rules_api";
    public static final String FLAG_ENABLE_FADE_MANAGER_CONFIGURATION =
            "android.media.audiopolicy.enable_fade_manager_configuration";
    public static final String FLAG_MULTI_ZONE_AUDIO = "android.media.audiopolicy.multi_zone_audio";
    public static final String FLAG_RECORD_AUDIO_DEVICE_AWARE_PERMISSION =
            "android.media.audiopolicy.record_audio_device_aware_permission";

    public static boolean audioMixOwnership() {
        return FEATURE_FLAGS.audioMixOwnership();
    }

    public static boolean audioMixPolicyOrdering() {
        return FEATURE_FLAGS.audioMixPolicyOrdering();
    }

    public static boolean audioMixTestApi() {
        return FEATURE_FLAGS.audioMixTestApi();
    }

    public static boolean audioPolicyUpdateMixingRulesApi() {
        return FEATURE_FLAGS.audioPolicyUpdateMixingRulesApi();
    }

    public static boolean enableFadeManagerConfiguration() {
        return FEATURE_FLAGS.enableFadeManagerConfiguration();
    }

    public static boolean multiZoneAudio() {
        return FEATURE_FLAGS.multiZoneAudio();
    }

    public static boolean recordAudioDeviceAwarePermission() {
        return FEATURE_FLAGS.recordAudioDeviceAwarePermission();
    }
}
