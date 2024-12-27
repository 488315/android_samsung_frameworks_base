package com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags;

public interface FeatureFlags {
    boolean enableInputPowerLimitedWarning();

    boolean enableInterfaceNameDeviceFilter();

    boolean enableIsModeChangeSupportedApi();

    boolean enableIsPdCompliantApi();

    boolean enableReportUsbDataComplianceWarning();

    boolean enableUsbDataComplianceWarning();

    boolean enableUsbDataSignalStaking();

    boolean enableUsbSysfsMidiIdentification();
}
