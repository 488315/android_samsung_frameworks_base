package com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ADOPT_PRIMARY_GROUP_MANAGEMENT_API, Flags.FLAG_ADOPT_PRIMARY_GROUP_MANAGEMENT_API_V2, Flags.FLAG_ASHA_PROFILE_ACCESS_PROFILE_ENABLED_TRUE, Flags.FLAG_AUDIO_SHARING_DEVELOPER_OPTION, Flags.FLAG_AUDIO_SHARING_HYSTERESIS_MODE_FIX, Flags.FLAG_AUDIO_SHARING_QS_DIALOG_IMPROVEMENT, Flags.FLAG_AUDIO_STREAM_MEDIA_SERVICE_BY_RECEIVE_STATE, Flags.FLAG_BLUETOOTH_QS_TILE_DIALOG_AUTO_ON_TOGGLE, Flags.FLAG_DISABLE_AUDIO_SHARING_AUTO_PICK_FALLBACK_IN_UI, Flags.FLAG_ENABLE_DETERMINING_ADVANCED_DETAILS_HEADER_WITH_METADATA, Flags.FLAG_ENABLE_DETERMINING_SPATIAL_AUDIO_ATTRIBUTES_BY_PROFILE, Flags.FLAG_ENABLE_LE_AUDIO_QR_CODE_PRIVATE_BROADCAST_SHARING, Flags.FLAG_ENABLE_LE_AUDIO_SHARING, Flags.FLAG_ENABLE_TEMPORARY_BOND_DEVICES_UI, Flags.FLAG_EXTREME_POWER_LOW_STATE_VULNERABILITY, Flags.FLAG_HEARING_DEVICE_SET_CONNECTION_STATUS_REPORT, Flags.FLAG_HEARING_DEVICES_AMBIENT_VOLUME_CONTROL, Flags.FLAG_HEARING_DEVICES_INPUT_ROUTING_CONTROL, Flags.FLAG_IGNORE_A2DP_DISCONNECTION_FOR_ANDROID_AUTO, Flags.FLAG_LEGACY_LE_AUDIO_SHARING, Flags.FLAG_MEMBER_DEVICE_LEA_ACTIVE_STATE_SYNC_FIX, Flags.FLAG_NEW_STATUS_BAR_ICONS, Flags.FLAG_PROMOTE_AUDIO_SHARING_FOR_SECOND_AUTO_CONNECTED_LEA_DEVICE, Flags.FLAG_SETTINGS_CATALYST, Flags.FLAG_SETTINGS_PREFERENCE_WRITE_CONSENT_ENABLED, Flags.FLAG_VOLUME_DIALOG_AUDIO_SHARING_FIX, Flags.FLAG_WRITE_SYSTEM_PREFERENCE_PERMISSION_ENABLED, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean adoptPrimaryGroupManagementApi() {
        return getValue(Flags.FLAG_ADOPT_PRIMARY_GROUP_MANAGEMENT_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adoptPrimaryGroupManagementApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean adoptPrimaryGroupManagementApiV2() {
        return getValue(Flags.FLAG_ADOPT_PRIMARY_GROUP_MANAGEMENT_API_V2, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adoptPrimaryGroupManagementApiV2();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean ashaProfileAccessProfileEnabledTrue() {
        return getValue(Flags.FLAG_ASHA_PROFILE_ACCESS_PROFILE_ENABLED_TRUE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ashaProfileAccessProfileEnabledTrue();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean audioSharingDeveloperOption() {
        return getValue(Flags.FLAG_AUDIO_SHARING_DEVELOPER_OPTION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).audioSharingDeveloperOption();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean audioSharingHysteresisModeFix() {
        return getValue(Flags.FLAG_AUDIO_SHARING_HYSTERESIS_MODE_FIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).audioSharingHysteresisModeFix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean audioSharingQsDialogImprovement() {
        return getValue(Flags.FLAG_AUDIO_SHARING_QS_DIALOG_IMPROVEMENT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).audioSharingQsDialogImprovement();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean audioStreamMediaServiceByReceiveState() {
        return getValue(Flags.FLAG_AUDIO_STREAM_MEDIA_SERVICE_BY_RECEIVE_STATE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).audioStreamMediaServiceByReceiveState();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean bluetoothQsTileDialogAutoOnToggle() {
        return getValue(Flags.FLAG_BLUETOOTH_QS_TILE_DIALOG_AUTO_ON_TOGGLE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).bluetoothQsTileDialogAutoOnToggle();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean disableAudioSharingAutoPickFallbackInUi() {
        return getValue(Flags.FLAG_DISABLE_AUDIO_SHARING_AUTO_PICK_FALLBACK_IN_UI, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableAudioSharingAutoPickFallbackInUi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean enableDeterminingAdvancedDetailsHeaderWithMetadata() {
        return getValue(Flags.FLAG_ENABLE_DETERMINING_ADVANCED_DETAILS_HEADER_WITH_METADATA, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDeterminingAdvancedDetailsHeaderWithMetadata();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean enableDeterminingSpatialAudioAttributesByProfile() {
        return getValue(Flags.FLAG_ENABLE_DETERMINING_SPATIAL_AUDIO_ATTRIBUTES_BY_PROFILE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDeterminingSpatialAudioAttributesByProfile();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean enableLeAudioQrCodePrivateBroadcastSharing() {
        return getValue(Flags.FLAG_ENABLE_LE_AUDIO_QR_CODE_PRIVATE_BROADCAST_SHARING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableLeAudioQrCodePrivateBroadcastSharing();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean enableLeAudioSharing() {
        return getValue(Flags.FLAG_ENABLE_LE_AUDIO_SHARING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableLeAudioSharing();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean enableTemporaryBondDevicesUi() {
        return getValue(Flags.FLAG_ENABLE_TEMPORARY_BOND_DEVICES_UI, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableTemporaryBondDevicesUi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean extremePowerLowStateVulnerability() {
        return getValue(Flags.FLAG_EXTREME_POWER_LOW_STATE_VULNERABILITY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).extremePowerLowStateVulnerability();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean hearingDeviceSetConnectionStatusReport() {
        return getValue(Flags.FLAG_HEARING_DEVICE_SET_CONNECTION_STATUS_REPORT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hearingDeviceSetConnectionStatusReport();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean hearingDevicesAmbientVolumeControl() {
        return getValue(Flags.FLAG_HEARING_DEVICES_AMBIENT_VOLUME_CONTROL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hearingDevicesAmbientVolumeControl();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean hearingDevicesInputRoutingControl() {
        return getValue(Flags.FLAG_HEARING_DEVICES_INPUT_ROUTING_CONTROL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hearingDevicesInputRoutingControl();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean ignoreA2dpDisconnectionForAndroidAuto() {
        return getValue(Flags.FLAG_IGNORE_A2DP_DISCONNECTION_FOR_ANDROID_AUTO, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ignoreA2dpDisconnectionForAndroidAuto();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean legacyLeAudioSharing() {
        return getValue(Flags.FLAG_LEGACY_LE_AUDIO_SHARING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).legacyLeAudioSharing();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean memberDeviceLeaActiveStateSyncFix() {
        return getValue(Flags.FLAG_MEMBER_DEVICE_LEA_ACTIVE_STATE_SYNC_FIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).memberDeviceLeaActiveStateSyncFix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean newStatusBarIcons() {
        return getValue(Flags.FLAG_NEW_STATUS_BAR_ICONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).newStatusBarIcons();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean promoteAudioSharingForSecondAutoConnectedLeaDevice() {
        return getValue(Flags.FLAG_PROMOTE_AUDIO_SHARING_FOR_SECOND_AUTO_CONNECTED_LEA_DEVICE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).promoteAudioSharingForSecondAutoConnectedLeaDevice();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean settingsCatalyst() {
        return getValue(Flags.FLAG_SETTINGS_CATALYST, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).settingsCatalyst();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean settingsPreferenceWriteConsentEnabled() {
        return getValue(Flags.FLAG_SETTINGS_PREFERENCE_WRITE_CONSENT_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).settingsPreferenceWriteConsentEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean volumeDialogAudioSharingFix() {
        return getValue(Flags.FLAG_VOLUME_DIALOG_AUDIO_SHARING_FIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).volumeDialogAudioSharingFix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.FeatureFlags
    public boolean writeSystemPreferencePermissionEnabled() {
        return getValue(Flags.FLAG_WRITE_SYSTEM_PREFERENCE_PERMISSION_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).writeSystemPreferencePermissionEnabled();
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
        return Arrays.asList(Flags.FLAG_ADOPT_PRIMARY_GROUP_MANAGEMENT_API, Flags.FLAG_ADOPT_PRIMARY_GROUP_MANAGEMENT_API_V2, Flags.FLAG_ASHA_PROFILE_ACCESS_PROFILE_ENABLED_TRUE, Flags.FLAG_AUDIO_SHARING_DEVELOPER_OPTION, Flags.FLAG_AUDIO_SHARING_HYSTERESIS_MODE_FIX, Flags.FLAG_AUDIO_SHARING_QS_DIALOG_IMPROVEMENT, Flags.FLAG_AUDIO_STREAM_MEDIA_SERVICE_BY_RECEIVE_STATE, Flags.FLAG_BLUETOOTH_QS_TILE_DIALOG_AUTO_ON_TOGGLE, Flags.FLAG_DISABLE_AUDIO_SHARING_AUTO_PICK_FALLBACK_IN_UI, Flags.FLAG_ENABLE_DETERMINING_ADVANCED_DETAILS_HEADER_WITH_METADATA, Flags.FLAG_ENABLE_DETERMINING_SPATIAL_AUDIO_ATTRIBUTES_BY_PROFILE, Flags.FLAG_ENABLE_LE_AUDIO_QR_CODE_PRIVATE_BROADCAST_SHARING, Flags.FLAG_ENABLE_LE_AUDIO_SHARING, Flags.FLAG_ENABLE_TEMPORARY_BOND_DEVICES_UI, Flags.FLAG_EXTREME_POWER_LOW_STATE_VULNERABILITY, Flags.FLAG_HEARING_DEVICE_SET_CONNECTION_STATUS_REPORT, Flags.FLAG_HEARING_DEVICES_AMBIENT_VOLUME_CONTROL, Flags.FLAG_HEARING_DEVICES_INPUT_ROUTING_CONTROL, Flags.FLAG_IGNORE_A2DP_DISCONNECTION_FOR_ANDROID_AUTO, Flags.FLAG_LEGACY_LE_AUDIO_SHARING, Flags.FLAG_MEMBER_DEVICE_LEA_ACTIVE_STATE_SYNC_FIX, Flags.FLAG_NEW_STATUS_BAR_ICONS, Flags.FLAG_PROMOTE_AUDIO_SHARING_FOR_SECOND_AUTO_CONNECTED_LEA_DEVICE, Flags.FLAG_SETTINGS_CATALYST, Flags.FLAG_SETTINGS_PREFERENCE_WRITE_CONSENT_ENABLED, Flags.FLAG_VOLUME_DIALOG_AUDIO_SHARING_FIX, Flags.FLAG_WRITE_SYSTEM_PREFERENCE_PERMISSION_ENABLED);
    }
}
