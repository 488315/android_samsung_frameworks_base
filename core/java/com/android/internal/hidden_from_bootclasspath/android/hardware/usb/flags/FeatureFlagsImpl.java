package com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableInputPowerLimitedWarning() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableInterfaceNameDeviceFilter() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableIsModeChangeSupportedApi() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableIsPdCompliantApi() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableReportUsbDataComplianceWarning() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableUsbDataComplianceWarning() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableUsbDataSignalStaking() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableUsbSysfsMidiIdentification() {
        return false;
    }
}
