package com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_INPUT_POWER_LIMITED_WARNING =
            "android.hardware.usb.flags.enable_input_power_limited_warning";
    public static final String FLAG_ENABLE_INTERFACE_NAME_DEVICE_FILTER =
            "android.hardware.usb.flags.enable_interface_name_device_filter";
    public static final String FLAG_ENABLE_IS_MODE_CHANGE_SUPPORTED_API =
            "android.hardware.usb.flags.enable_is_mode_change_supported_api";
    public static final String FLAG_ENABLE_IS_PD_COMPLIANT_API =
            "android.hardware.usb.flags.enable_is_pd_compliant_api";
    public static final String FLAG_ENABLE_REPORT_USB_DATA_COMPLIANCE_WARNING =
            "android.hardware.usb.flags.enable_report_usb_data_compliance_warning";
    public static final String FLAG_ENABLE_USB_DATA_COMPLIANCE_WARNING =
            "android.hardware.usb.flags.enable_usb_data_compliance_warning";
    public static final String FLAG_ENABLE_USB_DATA_SIGNAL_STAKING =
            "android.hardware.usb.flags.enable_usb_data_signal_staking";
    public static final String FLAG_ENABLE_USB_SYSFS_MIDI_IDENTIFICATION =
            "android.hardware.usb.flags.enable_usb_sysfs_midi_identification";

    public static boolean enableInputPowerLimitedWarning() {
        return FEATURE_FLAGS.enableInputPowerLimitedWarning();
    }

    public static boolean enableInterfaceNameDeviceFilter() {
        return FEATURE_FLAGS.enableInterfaceNameDeviceFilter();
    }

    public static boolean enableIsModeChangeSupportedApi() {
        return FEATURE_FLAGS.enableIsModeChangeSupportedApi();
    }

    public static boolean enableIsPdCompliantApi() {
        return FEATURE_FLAGS.enableIsPdCompliantApi();
    }

    public static boolean enableReportUsbDataComplianceWarning() {
        return FEATURE_FLAGS.enableReportUsbDataComplianceWarning();
    }

    public static boolean enableUsbDataComplianceWarning() {
        return FEATURE_FLAGS.enableUsbDataComplianceWarning();
    }

    public static boolean enableUsbDataSignalStaking() {
        return FEATURE_FLAGS.enableUsbDataSignalStaking();
    }

    public static boolean enableUsbSysfsMidiIdentification() {
        return FEATURE_FLAGS.enableUsbSysfsMidiIdentification();
    }
}
