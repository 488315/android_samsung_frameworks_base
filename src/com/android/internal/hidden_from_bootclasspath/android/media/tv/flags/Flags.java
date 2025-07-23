package com.android.internal.hidden_from_bootclasspath.android.media.tv.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_APPLY_PICTURE_PROFILES = "android.media.tv.flags.apply_picture_profiles";
    public static final String FLAG_BROADCAST_VISIBILITY_TYPES = "android.media.tv.flags.broadcast_visibility_types";
    public static final String FLAG_ENABLE_AD_SERVICE_FW = "android.media.tv.flags.enable_ad_service_fw";
    public static final String FLAG_ENABLE_LE_AUDIO_BROADCAST_UI = "android.media.tv.flags.enable_le_audio_broadcast_ui";
    public static final String FLAG_ENABLE_LE_AUDIO_UNICAST_UI = "android.media.tv.flags.enable_le_audio_unicast_ui";
    public static final String FLAG_HDMI_CONTROL_COLLECT_PHYSICAL_ADDRESS = "android.media.tv.flags.hdmi_control_collect_physical_address";
    public static final String FLAG_HDMI_CONTROL_ENHANCED_BEHAVIOR = "android.media.tv.flags.hdmi_control_enhanced_behavior";
    public static final String FLAG_KIDS_MODE_TVDB_SHARING = "android.media.tv.flags.kids_mode_tvdb_sharing";
    public static final String FLAG_MEDIACAS_UPDATE_CLIENT_PROFILE_PRIORITY = "android.media.tv.flags.mediacas_update_client_profile_priority";
    public static final String FLAG_MEDIA_QUALITY_FW = "android.media.tv.flags.media_quality_fw";
    public static final String FLAG_MEDIA_QUALITY_FW_BUGFIX = "android.media.tv.flags.media_quality_fw_bugfix";
    public static final String FLAG_SET_RESOURCE_HOLDER_RETAIN = "android.media.tv.flags.set_resource_holder_retain";
    public static final String FLAG_TIAF_V_APIS = "android.media.tv.flags.tiaf_v_apis";
    public static final String FLAG_TIF_EXTENSION_STANDARDIZATION = "android.media.tv.flags.tif_extension_standardization";
    public static final String FLAG_TIF_EXTENSION_STANDARDIZATION_BUGFIX = "android.media.tv.flags.tif_extension_standardization_bugfix";
    public static final String FLAG_TIF_UNBIND_INACTIVE_TIS = "android.media.tv.flags.tif_unbind_inactive_tis";
    public static final String FLAG_TUNER_W_APIS = "android.media.tv.flags.tuner_w_apis";

    public static boolean applyPictureProfiles() {
        return FEATURE_FLAGS.applyPictureProfiles();
    }

    public static boolean broadcastVisibilityTypes() {
        return FEATURE_FLAGS.broadcastVisibilityTypes();
    }

    public static boolean enableAdServiceFw() {
        return FEATURE_FLAGS.enableAdServiceFw();
    }

    public static boolean enableLeAudioBroadcastUi() {
        return FEATURE_FLAGS.enableLeAudioBroadcastUi();
    }

    public static boolean enableLeAudioUnicastUi() {
        return FEATURE_FLAGS.enableLeAudioUnicastUi();
    }

    public static boolean hdmiControlCollectPhysicalAddress() {
        return FEATURE_FLAGS.hdmiControlCollectPhysicalAddress();
    }

    public static boolean hdmiControlEnhancedBehavior() {
        return FEATURE_FLAGS.hdmiControlEnhancedBehavior();
    }

    public static boolean kidsModeTvdbSharing() {
        return FEATURE_FLAGS.kidsModeTvdbSharing();
    }

    public static boolean mediaQualityFw() {
        return FEATURE_FLAGS.mediaQualityFw();
    }

    public static boolean mediaQualityFwBugfix() {
        return FEATURE_FLAGS.mediaQualityFwBugfix();
    }

    public static boolean mediacasUpdateClientProfilePriority() {
        return FEATURE_FLAGS.mediacasUpdateClientProfilePriority();
    }

    public static boolean setResourceHolderRetain() {
        return FEATURE_FLAGS.setResourceHolderRetain();
    }

    public static boolean tiafVApis() {
        return FEATURE_FLAGS.tiafVApis();
    }

    public static boolean tifExtensionStandardization() {
        return FEATURE_FLAGS.tifExtensionStandardization();
    }

    public static boolean tifExtensionStandardizationBugfix() {
        return FEATURE_FLAGS.tifExtensionStandardizationBugfix();
    }

    public static boolean tifUnbindInactiveTis() {
        return FEATURE_FLAGS.tifUnbindInactiveTis();
    }

    public static boolean tunerWApis() {
        return FEATURE_FLAGS.tunerWApis();
    }
}
