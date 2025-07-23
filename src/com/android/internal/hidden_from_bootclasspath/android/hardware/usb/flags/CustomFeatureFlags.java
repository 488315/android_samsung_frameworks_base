package com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ENABLE_ACCESSORY_STREAM_API, Flags.FLAG_ENABLE_INPUT_POWER_LIMITED_WARNING, Flags.FLAG_ENABLE_INTERFACE_NAME_DEVICE_FILTER, Flags.FLAG_ENABLE_IS_MODE_CHANGE_SUPPORTED_API, Flags.FLAG_ENABLE_IS_PD_COMPLIANT_API, Flags.FLAG_ENABLE_REPORT_USB_DATA_COMPLIANCE_WARNING, Flags.FLAG_ENABLE_UDC_SYSFS_USB_STATE_UPDATE, Flags.FLAG_ENABLE_USB_DATA_COMPLIANCE_WARNING, Flags.FLAG_ENABLE_USB_DATA_SIGNAL_STAKING, Flags.FLAG_ENABLE_USB_DATA_SIGNAL_STAKING_INTERNAL, Flags.FLAG_ENABLE_USB_SYSFS_MIDI_IDENTIFICATION, Flags.FLAG_EXPOSE_USB_SPEED_SYSTEM_API, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableAccessoryStreamApi() {
        return getValue(Flags.FLAG_ENABLE_ACCESSORY_STREAM_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableAccessoryStreamApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableInputPowerLimitedWarning() {
        return getValue(Flags.FLAG_ENABLE_INPUT_POWER_LIMITED_WARNING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableInputPowerLimitedWarning();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableInterfaceNameDeviceFilter() {
        return getValue(Flags.FLAG_ENABLE_INTERFACE_NAME_DEVICE_FILTER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableInterfaceNameDeviceFilter();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableIsModeChangeSupportedApi() {
        return getValue(Flags.FLAG_ENABLE_IS_MODE_CHANGE_SUPPORTED_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableIsModeChangeSupportedApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableIsPdCompliantApi() {
        return getValue(Flags.FLAG_ENABLE_IS_PD_COMPLIANT_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableIsPdCompliantApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableReportUsbDataComplianceWarning() {
        return getValue(Flags.FLAG_ENABLE_REPORT_USB_DATA_COMPLIANCE_WARNING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableReportUsbDataComplianceWarning();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableUdcSysfsUsbStateUpdate() {
        return getValue(Flags.FLAG_ENABLE_UDC_SYSFS_USB_STATE_UPDATE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableUdcSysfsUsbStateUpdate();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableUsbDataComplianceWarning() {
        return getValue(Flags.FLAG_ENABLE_USB_DATA_COMPLIANCE_WARNING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableUsbDataComplianceWarning();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableUsbDataSignalStaking() {
        return getValue(Flags.FLAG_ENABLE_USB_DATA_SIGNAL_STAKING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableUsbDataSignalStaking();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableUsbDataSignalStakingInternal() {
        return getValue(Flags.FLAG_ENABLE_USB_DATA_SIGNAL_STAKING_INTERNAL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableUsbDataSignalStakingInternal();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableUsbSysfsMidiIdentification() {
        return getValue(Flags.FLAG_ENABLE_USB_SYSFS_MIDI_IDENTIFICATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableUsbSysfsMidiIdentification();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean exposeUsbSpeedSystemApi() {
        return getValue(Flags.FLAG_EXPOSE_USB_SPEED_SYSTEM_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).exposeUsbSpeedSystemApi();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ENABLE_ACCESSORY_STREAM_API, Flags.FLAG_ENABLE_INPUT_POWER_LIMITED_WARNING, Flags.FLAG_ENABLE_INTERFACE_NAME_DEVICE_FILTER, Flags.FLAG_ENABLE_IS_MODE_CHANGE_SUPPORTED_API, Flags.FLAG_ENABLE_IS_PD_COMPLIANT_API, Flags.FLAG_ENABLE_REPORT_USB_DATA_COMPLIANCE_WARNING, Flags.FLAG_ENABLE_UDC_SYSFS_USB_STATE_UPDATE, Flags.FLAG_ENABLE_USB_DATA_COMPLIANCE_WARNING, Flags.FLAG_ENABLE_USB_DATA_SIGNAL_STAKING, Flags.FLAG_ENABLE_USB_DATA_SIGNAL_STAKING_INTERNAL, Flags.FLAG_ENABLE_USB_SYSFS_MIDI_IDENTIFICATION, Flags.FLAG_EXPOSE_USB_SPEED_SYSTEM_API);
    }
}
