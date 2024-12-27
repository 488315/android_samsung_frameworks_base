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
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_ENABLE_INPUT_POWER_LIMITED_WARNING,
                            Flags.FLAG_ENABLE_INTERFACE_NAME_DEVICE_FILTER,
                            Flags.FLAG_ENABLE_IS_MODE_CHANGE_SUPPORTED_API,
                            Flags.FLAG_ENABLE_IS_PD_COMPLIANT_API,
                            Flags.FLAG_ENABLE_REPORT_USB_DATA_COMPLIANCE_WARNING,
                            Flags.FLAG_ENABLE_USB_DATA_COMPLIANCE_WARNING,
                            Flags.FLAG_ENABLE_USB_DATA_SIGNAL_STAKING,
                            Flags.FLAG_ENABLE_USB_SYSFS_MIDI_IDENTIFICATION,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableInputPowerLimitedWarning() {
        return getValue(
                Flags.FLAG_ENABLE_INPUT_POWER_LIMITED_WARNING,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableInputPowerLimitedWarning();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableInterfaceNameDeviceFilter() {
        return getValue(
                Flags.FLAG_ENABLE_INTERFACE_NAME_DEVICE_FILTER,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableInterfaceNameDeviceFilter();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableIsModeChangeSupportedApi() {
        return getValue(
                Flags.FLAG_ENABLE_IS_MODE_CHANGE_SUPPORTED_API,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableIsModeChangeSupportedApi();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableIsPdCompliantApi() {
        return getValue(
                Flags.FLAG_ENABLE_IS_PD_COMPLIANT_API,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableIsPdCompliantApi();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableReportUsbDataComplianceWarning() {
        return getValue(
                Flags.FLAG_ENABLE_REPORT_USB_DATA_COMPLIANCE_WARNING,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableReportUsbDataComplianceWarning();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableUsbDataComplianceWarning() {
        return getValue(
                Flags.FLAG_ENABLE_USB_DATA_COMPLIANCE_WARNING,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableUsbDataComplianceWarning();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableUsbDataSignalStaking() {
        return getValue(
                Flags.FLAG_ENABLE_USB_DATA_SIGNAL_STAKING,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableUsbDataSignalStaking();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.FeatureFlags
    public boolean enableUsbSysfsMidiIdentification() {
        return getValue(
                Flags.FLAG_ENABLE_USB_SYSFS_MIDI_IDENTIFICATION,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableUsbSysfsMidiIdentification();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_ENABLE_INPUT_POWER_LIMITED_WARNING,
                Flags.FLAG_ENABLE_INTERFACE_NAME_DEVICE_FILTER,
                Flags.FLAG_ENABLE_IS_MODE_CHANGE_SUPPORTED_API,
                Flags.FLAG_ENABLE_IS_PD_COMPLIANT_API,
                Flags.FLAG_ENABLE_REPORT_USB_DATA_COMPLIANCE_WARNING,
                Flags.FLAG_ENABLE_USB_DATA_COMPLIANCE_WARNING,
                Flags.FLAG_ENABLE_USB_DATA_SIGNAL_STAKING,
                Flags.FLAG_ENABLE_USB_SYSFS_MIDI_IDENTIFICATION);
    }
}
