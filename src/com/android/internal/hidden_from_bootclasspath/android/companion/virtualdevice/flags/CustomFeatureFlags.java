package com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ACTIVITY_CONTROL_API, Flags.FLAG_CAMERA_MULTIPLE_INPUT_STREAMS, Flags.FLAG_CAMERA_TIMESTAMP_FROM_SURFACE, Flags.FLAG_CORRECT_VIRTUAL_DISPLAY_POWER_STATE, Flags.FLAG_DEFAULT_DEVICE_CAMERA_ACCESS_POLICY, Flags.FLAG_DEVICE_AWARE_DISPLAY_POWER, Flags.FLAG_DEVICE_AWARE_SETTINGS_OVERRIDE, Flags.FLAG_DISPLAY_POWER_MANAGER_APIS, Flags.FLAG_ENABLE_LIMITED_VDM_ROLE, Flags.FLAG_ENFORCE_REMOTE_DEVICE_OPT_OUT_ON_ALL_VIRTUAL_DISPLAYS, Flags.FLAG_EXTERNAL_VIRTUAL_CAMERAS, Flags.FLAG_HIGH_RESOLUTION_SCROLL, Flags.FLAG_MIGRATE_VIEWCONFIGURATION_CONSTANTS_TO_RESOURCES, Flags.FLAG_NOTIFICATIONS_FOR_DEVICE_STREAMING, Flags.FLAG_STATUS_BAR_AND_INSETS, Flags.FLAG_VDM_SETTINGS, Flags.FLAG_VIEWCONFIGURATION_APIS, Flags.FLAG_VIRTUAL_DISPLAY_INSETS, Flags.FLAG_VIRTUAL_DISPLAY_ROTATION_API, Flags.FLAG_VIRTUAL_ROTARY, Flags.FLAG_VIRTUAL_SENSOR_ADDITIONAL_INFO, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean activityControlApi() {
        return getValue(Flags.FLAG_ACTIVITY_CONTROL_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).activityControlApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean cameraMultipleInputStreams() {
        return getValue(Flags.FLAG_CAMERA_MULTIPLE_INPUT_STREAMS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraMultipleInputStreams();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean cameraTimestampFromSurface() {
        return getValue(Flags.FLAG_CAMERA_TIMESTAMP_FROM_SURFACE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraTimestampFromSurface();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean correctVirtualDisplayPowerState() {
        return getValue(Flags.FLAG_CORRECT_VIRTUAL_DISPLAY_POWER_STATE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).correctVirtualDisplayPowerState();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean defaultDeviceCameraAccessPolicy() {
        return getValue(Flags.FLAG_DEFAULT_DEVICE_CAMERA_ACCESS_POLICY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).defaultDeviceCameraAccessPolicy();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean deviceAwareDisplayPower() {
        return getValue(Flags.FLAG_DEVICE_AWARE_DISPLAY_POWER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceAwareDisplayPower();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean deviceAwareSettingsOverride() {
        return getValue(Flags.FLAG_DEVICE_AWARE_SETTINGS_OVERRIDE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceAwareSettingsOverride();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean displayPowerManagerApis() {
        return getValue(Flags.FLAG_DISPLAY_POWER_MANAGER_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).displayPowerManagerApis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean enableLimitedVdmRole() {
        return getValue(Flags.FLAG_ENABLE_LIMITED_VDM_ROLE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableLimitedVdmRole();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean enforceRemoteDeviceOptOutOnAllVirtualDisplays() {
        return getValue(Flags.FLAG_ENFORCE_REMOTE_DEVICE_OPT_OUT_ON_ALL_VIRTUAL_DISPLAYS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enforceRemoteDeviceOptOutOnAllVirtualDisplays();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean externalVirtualCameras() {
        return getValue(Flags.FLAG_EXTERNAL_VIRTUAL_CAMERAS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).externalVirtualCameras();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean highResolutionScroll() {
        return getValue(Flags.FLAG_HIGH_RESOLUTION_SCROLL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).highResolutionScroll();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean migrateViewconfigurationConstantsToResources() {
        return getValue(Flags.FLAG_MIGRATE_VIEWCONFIGURATION_CONSTANTS_TO_RESOURCES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).migrateViewconfigurationConstantsToResources();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean notificationsForDeviceStreaming() {
        return getValue(Flags.FLAG_NOTIFICATIONS_FOR_DEVICE_STREAMING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notificationsForDeviceStreaming();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean statusBarAndInsets() {
        return getValue(Flags.FLAG_STATUS_BAR_AND_INSETS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).statusBarAndInsets();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean vdmSettings() {
        return getValue(Flags.FLAG_VDM_SETTINGS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vdmSettings();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean viewconfigurationApis() {
        return getValue(Flags.FLAG_VIEWCONFIGURATION_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).viewconfigurationApis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean virtualDisplayInsets() {
        return getValue(Flags.FLAG_VIRTUAL_DISPLAY_INSETS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).virtualDisplayInsets();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean virtualDisplayRotationApi() {
        return getValue(Flags.FLAG_VIRTUAL_DISPLAY_ROTATION_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).virtualDisplayRotationApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean virtualRotary() {
        return getValue(Flags.FLAG_VIRTUAL_ROTARY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).virtualRotary();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.FeatureFlags
    public boolean virtualSensorAdditionalInfo() {
        return getValue(Flags.FLAG_VIRTUAL_SENSOR_ADDITIONAL_INFO, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).virtualSensorAdditionalInfo();
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
        return Arrays.asList(Flags.FLAG_ACTIVITY_CONTROL_API, Flags.FLAG_CAMERA_MULTIPLE_INPUT_STREAMS, Flags.FLAG_CAMERA_TIMESTAMP_FROM_SURFACE, Flags.FLAG_CORRECT_VIRTUAL_DISPLAY_POWER_STATE, Flags.FLAG_DEFAULT_DEVICE_CAMERA_ACCESS_POLICY, Flags.FLAG_DEVICE_AWARE_DISPLAY_POWER, Flags.FLAG_DEVICE_AWARE_SETTINGS_OVERRIDE, Flags.FLAG_DISPLAY_POWER_MANAGER_APIS, Flags.FLAG_ENABLE_LIMITED_VDM_ROLE, Flags.FLAG_ENFORCE_REMOTE_DEVICE_OPT_OUT_ON_ALL_VIRTUAL_DISPLAYS, Flags.FLAG_EXTERNAL_VIRTUAL_CAMERAS, Flags.FLAG_HIGH_RESOLUTION_SCROLL, Flags.FLAG_MIGRATE_VIEWCONFIGURATION_CONSTANTS_TO_RESOURCES, Flags.FLAG_NOTIFICATIONS_FOR_DEVICE_STREAMING, Flags.FLAG_STATUS_BAR_AND_INSETS, Flags.FLAG_VDM_SETTINGS, Flags.FLAG_VIEWCONFIGURATION_APIS, Flags.FLAG_VIRTUAL_DISPLAY_INSETS, Flags.FLAG_VIRTUAL_DISPLAY_ROTATION_API, Flags.FLAG_VIRTUAL_ROTARY, Flags.FLAG_VIRTUAL_SENSOR_ADDITIONAL_INFO);
    }
}
