package com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADOPT_PRIMARY_GROUP_MANAGEMENT_API = "com.android.settingslib.flags.adopt_primary_group_management_api";
    public static final String FLAG_ADOPT_PRIMARY_GROUP_MANAGEMENT_API_V2 = "com.android.settingslib.flags.adopt_primary_group_management_api_v2";
    public static final String FLAG_ASHA_PROFILE_ACCESS_PROFILE_ENABLED_TRUE = "com.android.settingslib.flags.asha_profile_access_profile_enabled_true";
    public static final String FLAG_AUDIO_SHARING_DEVELOPER_OPTION = "com.android.settingslib.flags.audio_sharing_developer_option";
    public static final String FLAG_AUDIO_SHARING_HYSTERESIS_MODE_FIX = "com.android.settingslib.flags.audio_sharing_hysteresis_mode_fix";
    public static final String FLAG_AUDIO_SHARING_QS_DIALOG_IMPROVEMENT = "com.android.settingslib.flags.audio_sharing_qs_dialog_improvement";
    public static final String FLAG_AUDIO_STREAM_MEDIA_SERVICE_BY_RECEIVE_STATE = "com.android.settingslib.flags.audio_stream_media_service_by_receive_state";
    public static final String FLAG_BLUETOOTH_QS_TILE_DIALOG_AUTO_ON_TOGGLE = "com.android.settingslib.flags.bluetooth_qs_tile_dialog_auto_on_toggle";
    public static final String FLAG_DISABLE_AUDIO_SHARING_AUTO_PICK_FALLBACK_IN_UI = "com.android.settingslib.flags.disable_audio_sharing_auto_pick_fallback_in_ui";
    public static final String FLAG_ENABLE_DETERMINING_ADVANCED_DETAILS_HEADER_WITH_METADATA = "com.android.settingslib.flags.enable_determining_advanced_details_header_with_metadata";
    public static final String FLAG_ENABLE_DETERMINING_SPATIAL_AUDIO_ATTRIBUTES_BY_PROFILE = "com.android.settingslib.flags.enable_determining_spatial_audio_attributes_by_profile";
    public static final String FLAG_ENABLE_LE_AUDIO_QR_CODE_PRIVATE_BROADCAST_SHARING = "com.android.settingslib.flags.enable_le_audio_qr_code_private_broadcast_sharing";
    public static final String FLAG_ENABLE_LE_AUDIO_SHARING = "com.android.settingslib.flags.enable_le_audio_sharing";
    public static final String FLAG_ENABLE_TEMPORARY_BOND_DEVICES_UI = "com.android.settingslib.flags.enable_temporary_bond_devices_ui";
    public static final String FLAG_EXTREME_POWER_LOW_STATE_VULNERABILITY = "com.android.settingslib.flags.extreme_power_low_state_vulnerability";
    public static final String FLAG_HEARING_DEVICES_AMBIENT_VOLUME_CONTROL = "com.android.settingslib.flags.hearing_devices_ambient_volume_control";
    public static final String FLAG_HEARING_DEVICES_INPUT_ROUTING_CONTROL = "com.android.settingslib.flags.hearing_devices_input_routing_control";
    public static final String FLAG_HEARING_DEVICE_SET_CONNECTION_STATUS_REPORT = "com.android.settingslib.flags.hearing_device_set_connection_status_report";
    public static final String FLAG_IGNORE_A2DP_DISCONNECTION_FOR_ANDROID_AUTO = "com.android.settingslib.flags.ignore_a2dp_disconnection_for_android_auto";
    public static final String FLAG_LEGACY_LE_AUDIO_SHARING = "com.android.settingslib.flags.legacy_le_audio_sharing";
    public static final String FLAG_MEMBER_DEVICE_LEA_ACTIVE_STATE_SYNC_FIX = "com.android.settingslib.flags.member_device_lea_active_state_sync_fix";
    public static final String FLAG_NEW_STATUS_BAR_ICONS = "com.android.settingslib.flags.new_status_bar_icons";
    public static final String FLAG_PROMOTE_AUDIO_SHARING_FOR_SECOND_AUTO_CONNECTED_LEA_DEVICE = "com.android.settingslib.flags.promote_audio_sharing_for_second_auto_connected_lea_device";
    public static final String FLAG_SETTINGS_CATALYST = "com.android.settingslib.flags.settings_catalyst";
    public static final String FLAG_SETTINGS_PREFERENCE_WRITE_CONSENT_ENABLED = "com.android.settingslib.flags.settings_preference_write_consent_enabled";
    public static final String FLAG_VOLUME_DIALOG_AUDIO_SHARING_FIX = "com.android.settingslib.flags.volume_dialog_audio_sharing_fix";
    public static final String FLAG_WRITE_SYSTEM_PREFERENCE_PERMISSION_ENABLED = "com.android.settingslib.flags.write_system_preference_permission_enabled";

    public static boolean adoptPrimaryGroupManagementApi() {
        return FEATURE_FLAGS.adoptPrimaryGroupManagementApi();
    }

    public static boolean adoptPrimaryGroupManagementApiV2() {
        return FEATURE_FLAGS.adoptPrimaryGroupManagementApiV2();
    }

    public static boolean ashaProfileAccessProfileEnabledTrue() {
        return FEATURE_FLAGS.ashaProfileAccessProfileEnabledTrue();
    }

    public static boolean audioSharingDeveloperOption() {
        return FEATURE_FLAGS.audioSharingDeveloperOption();
    }

    public static boolean audioSharingHysteresisModeFix() {
        return FEATURE_FLAGS.audioSharingHysteresisModeFix();
    }

    public static boolean audioSharingQsDialogImprovement() {
        return FEATURE_FLAGS.audioSharingQsDialogImprovement();
    }

    public static boolean audioStreamMediaServiceByReceiveState() {
        return FEATURE_FLAGS.audioStreamMediaServiceByReceiveState();
    }

    public static boolean bluetoothQsTileDialogAutoOnToggle() {
        return FEATURE_FLAGS.bluetoothQsTileDialogAutoOnToggle();
    }

    public static boolean disableAudioSharingAutoPickFallbackInUi() {
        return FEATURE_FLAGS.disableAudioSharingAutoPickFallbackInUi();
    }

    public static boolean enableDeterminingAdvancedDetailsHeaderWithMetadata() {
        return FEATURE_FLAGS.enableDeterminingAdvancedDetailsHeaderWithMetadata();
    }

    public static boolean enableDeterminingSpatialAudioAttributesByProfile() {
        return FEATURE_FLAGS.enableDeterminingSpatialAudioAttributesByProfile();
    }

    public static boolean enableLeAudioQrCodePrivateBroadcastSharing() {
        return FEATURE_FLAGS.enableLeAudioQrCodePrivateBroadcastSharing();
    }

    public static boolean enableLeAudioSharing() {
        return FEATURE_FLAGS.enableLeAudioSharing();
    }

    public static boolean enableTemporaryBondDevicesUi() {
        return FEATURE_FLAGS.enableTemporaryBondDevicesUi();
    }

    public static boolean extremePowerLowStateVulnerability() {
        return FEATURE_FLAGS.extremePowerLowStateVulnerability();
    }

    public static boolean hearingDeviceSetConnectionStatusReport() {
        return FEATURE_FLAGS.hearingDeviceSetConnectionStatusReport();
    }

    public static boolean hearingDevicesAmbientVolumeControl() {
        return FEATURE_FLAGS.hearingDevicesAmbientVolumeControl();
    }

    public static boolean hearingDevicesInputRoutingControl() {
        return FEATURE_FLAGS.hearingDevicesInputRoutingControl();
    }

    public static boolean ignoreA2dpDisconnectionForAndroidAuto() {
        return FEATURE_FLAGS.ignoreA2dpDisconnectionForAndroidAuto();
    }

    public static boolean legacyLeAudioSharing() {
        return FEATURE_FLAGS.legacyLeAudioSharing();
    }

    public static boolean memberDeviceLeaActiveStateSyncFix() {
        return FEATURE_FLAGS.memberDeviceLeaActiveStateSyncFix();
    }

    public static boolean newStatusBarIcons() {
        return FEATURE_FLAGS.newStatusBarIcons();
    }

    public static boolean promoteAudioSharingForSecondAutoConnectedLeaDevice() {
        return FEATURE_FLAGS.promoteAudioSharingForSecondAutoConnectedLeaDevice();
    }

    public static boolean settingsCatalyst() {
        return FEATURE_FLAGS.settingsCatalyst();
    }

    public static boolean settingsPreferenceWriteConsentEnabled() {
        return FEATURE_FLAGS.settingsPreferenceWriteConsentEnabled();
    }

    public static boolean volumeDialogAudioSharingFix() {
        return FEATURE_FLAGS.volumeDialogAudioSharingFix();
    }

    public static boolean writeSystemPreferencePermissionEnabled() {
        return FEATURE_FLAGS.writeSystemPreferencePermissionEnabled();
    }
}
