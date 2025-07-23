package com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ACTIVITY_CONTROL_API = "android.companion.virtualdevice.flags.activity_control_api";
    public static final String FLAG_CAMERA_MULTIPLE_INPUT_STREAMS = "android.companion.virtualdevice.flags.camera_multiple_input_streams";
    public static final String FLAG_CAMERA_TIMESTAMP_FROM_SURFACE = "android.companion.virtualdevice.flags.camera_timestamp_from_surface";
    public static final String FLAG_CORRECT_VIRTUAL_DISPLAY_POWER_STATE = "android.companion.virtualdevice.flags.correct_virtual_display_power_state";
    public static final String FLAG_DEFAULT_DEVICE_CAMERA_ACCESS_POLICY = "android.companion.virtualdevice.flags.default_device_camera_access_policy";
    public static final String FLAG_DEVICE_AWARE_DISPLAY_POWER = "android.companion.virtualdevice.flags.device_aware_display_power";
    public static final String FLAG_DEVICE_AWARE_SETTINGS_OVERRIDE = "android.companion.virtualdevice.flags.device_aware_settings_override";
    public static final String FLAG_DISPLAY_POWER_MANAGER_APIS = "android.companion.virtualdevice.flags.display_power_manager_apis";
    public static final String FLAG_ENABLE_LIMITED_VDM_ROLE = "android.companion.virtualdevice.flags.enable_limited_vdm_role";
    public static final String FLAG_ENFORCE_REMOTE_DEVICE_OPT_OUT_ON_ALL_VIRTUAL_DISPLAYS = "android.companion.virtualdevice.flags.enforce_remote_device_opt_out_on_all_virtual_displays";
    public static final String FLAG_EXTERNAL_VIRTUAL_CAMERAS = "android.companion.virtualdevice.flags.external_virtual_cameras";
    public static final String FLAG_HIGH_RESOLUTION_SCROLL = "android.companion.virtualdevice.flags.high_resolution_scroll";
    public static final String FLAG_MIGRATE_VIEWCONFIGURATION_CONSTANTS_TO_RESOURCES = "android.companion.virtualdevice.flags.migrate_viewconfiguration_constants_to_resources";
    public static final String FLAG_NOTIFICATIONS_FOR_DEVICE_STREAMING = "android.companion.virtualdevice.flags.notifications_for_device_streaming";
    public static final String FLAG_STATUS_BAR_AND_INSETS = "android.companion.virtualdevice.flags.status_bar_and_insets";
    public static final String FLAG_VDM_SETTINGS = "android.companion.virtualdevice.flags.vdm_settings";
    public static final String FLAG_VIEWCONFIGURATION_APIS = "android.companion.virtualdevice.flags.viewconfiguration_apis";
    public static final String FLAG_VIRTUAL_DISPLAY_INSETS = "android.companion.virtualdevice.flags.virtual_display_insets";
    public static final String FLAG_VIRTUAL_DISPLAY_ROTATION_API = "android.companion.virtualdevice.flags.virtual_display_rotation_api";
    public static final String FLAG_VIRTUAL_ROTARY = "android.companion.virtualdevice.flags.virtual_rotary";
    public static final String FLAG_VIRTUAL_SENSOR_ADDITIONAL_INFO = "android.companion.virtualdevice.flags.virtual_sensor_additional_info";

    public static boolean activityControlApi() {
        return FEATURE_FLAGS.activityControlApi();
    }

    public static boolean cameraMultipleInputStreams() {
        return FEATURE_FLAGS.cameraMultipleInputStreams();
    }

    public static boolean cameraTimestampFromSurface() {
        return FEATURE_FLAGS.cameraTimestampFromSurface();
    }

    public static boolean correctVirtualDisplayPowerState() {
        return FEATURE_FLAGS.correctVirtualDisplayPowerState();
    }

    public static boolean defaultDeviceCameraAccessPolicy() {
        return FEATURE_FLAGS.defaultDeviceCameraAccessPolicy();
    }

    public static boolean deviceAwareDisplayPower() {
        return FEATURE_FLAGS.deviceAwareDisplayPower();
    }

    public static boolean deviceAwareSettingsOverride() {
        return FEATURE_FLAGS.deviceAwareSettingsOverride();
    }

    public static boolean displayPowerManagerApis() {
        return FEATURE_FLAGS.displayPowerManagerApis();
    }

    public static boolean enableLimitedVdmRole() {
        return FEATURE_FLAGS.enableLimitedVdmRole();
    }

    public static boolean enforceRemoteDeviceOptOutOnAllVirtualDisplays() {
        return FEATURE_FLAGS.enforceRemoteDeviceOptOutOnAllVirtualDisplays();
    }

    public static boolean externalVirtualCameras() {
        return FEATURE_FLAGS.externalVirtualCameras();
    }

    public static boolean highResolutionScroll() {
        return FEATURE_FLAGS.highResolutionScroll();
    }

    public static boolean migrateViewconfigurationConstantsToResources() {
        return FEATURE_FLAGS.migrateViewconfigurationConstantsToResources();
    }

    public static boolean notificationsForDeviceStreaming() {
        return FEATURE_FLAGS.notificationsForDeviceStreaming();
    }

    public static boolean statusBarAndInsets() {
        return FEATURE_FLAGS.statusBarAndInsets();
    }

    public static boolean vdmSettings() {
        return FEATURE_FLAGS.vdmSettings();
    }

    public static boolean viewconfigurationApis() {
        return FEATURE_FLAGS.viewconfigurationApis();
    }

    public static boolean virtualDisplayInsets() {
        return FEATURE_FLAGS.virtualDisplayInsets();
    }

    public static boolean virtualDisplayRotationApi() {
        return FEATURE_FLAGS.virtualDisplayRotationApi();
    }

    public static boolean virtualRotary() {
        return FEATURE_FLAGS.virtualRotary();
    }

    public static boolean virtualSensorAdditionalInfo() {
        return FEATURE_FLAGS.virtualSensorAdditionalInfo();
    }
}
