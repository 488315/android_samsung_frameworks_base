package com.android.internal.hidden_from_bootclasspath.android.media.audio;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_AUTO_PUBLIC_VOLUME_API_HARDENING, Flags.FLAG_AUTOMATIC_BT_DEVICE_TYPE, Flags.FLAG_CACHE_GET_STREAM_MIN_MAX_VOLUME, Flags.FLAG_CACHE_GET_STREAM_VOLUME, Flags.FLAG_CONCURRENT_AUDIO_RECORD_BYPASS_PERMISSION, Flags.FLAG_DEPRECATE_STREAM_BT_SCO, Flags.FLAG_DOLBY_AC4_LEVEL4_ENCODING_API, Flags.FLAG_ENABLE_MULTICHANNEL_GROUP_DEVICE, Flags.FLAG_ENABLE_RINGTONE_HAPTICS_CUSTOMIZATION, Flags.FLAG_FEATURE_SPATIAL_AUDIO_HEADTRACKING_LOW_LATENCY, Flags.FLAG_FOCUS_EXCLUSIVE_WITH_RECORDING, Flags.FLAG_FOCUS_FREEZE_TEST_API, Flags.FLAG_FOREGROUND_AUDIO_CONTROL, Flags.FLAG_HARDENING_PERMISSION_API, Flags.FLAG_HARDENING_PERMISSION_SPA, Flags.FLAG_IAMF_DEFINITIONS_API, Flags.FLAG_LOUDNESS_CONFIGURATOR_API, Flags.FLAG_MUTE_BACKGROUND_AUDIO, Flags.FLAG_MUTED_BY_PORT_VOLUME_API, Flags.FLAG_REGISTER_VOLUME_CALLBACK_API_HARDENING, Flags.FLAG_RINGTONE_USER_URI_CHECK, Flags.FLAG_RO_FOREGROUND_AUDIO_CONTROL, Flags.FLAG_RO_VOLUME_RINGER_API_HARDENING, Flags.FLAG_ROUTED_DEVICE_IDS, Flags.FLAG_SCO_MANAGED_BY_AUDIO, Flags.FLAG_SONY_360RA_MPEGH_3D_FORMAT, Flags.FLAG_SPATIAL_AUDIO_SETTINGS_VERSIONING, Flags.FLAG_SPATIALIZER_CAPABILITIES, Flags.FLAG_SPEAKER_CLEANUP_USAGE, Flags.FLAG_SPEAKER_LAYOUT_API, Flags.FLAG_SUPPORTED_DEVICE_TYPES_API, Flags.FLAG_UNIFY_ABSOLUTE_VOLUME_MANAGEMENT, Flags.FLAG_VOLUME_RINGER_API_HARDENING, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean autoPublicVolumeApiHardening() {
        return getValue(Flags.FLAG_AUTO_PUBLIC_VOLUME_API_HARDENING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).autoPublicVolumeApiHardening();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean automaticBtDeviceType() {
        return getValue(Flags.FLAG_AUTOMATIC_BT_DEVICE_TYPE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).automaticBtDeviceType();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean cacheGetStreamMinMaxVolume() {
        return getValue(Flags.FLAG_CACHE_GET_STREAM_MIN_MAX_VOLUME, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheGetStreamMinMaxVolume();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean cacheGetStreamVolume() {
        return getValue(Flags.FLAG_CACHE_GET_STREAM_VOLUME, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheGetStreamVolume();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean concurrentAudioRecordBypassPermission() {
        return getValue(Flags.FLAG_CONCURRENT_AUDIO_RECORD_BYPASS_PERMISSION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).concurrentAudioRecordBypassPermission();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean deprecateStreamBtSco() {
        return getValue(Flags.FLAG_DEPRECATE_STREAM_BT_SCO, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deprecateStreamBtSco();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean dolbyAc4Level4EncodingApi() {
        return getValue(Flags.FLAG_DOLBY_AC4_LEVEL4_ENCODING_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dolbyAc4Level4EncodingApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean enableMultichannelGroupDevice() {
        return getValue(Flags.FLAG_ENABLE_MULTICHANNEL_GROUP_DEVICE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableMultichannelGroupDevice();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean enableRingtoneHapticsCustomization() {
        return getValue(Flags.FLAG_ENABLE_RINGTONE_HAPTICS_CUSTOMIZATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableRingtoneHapticsCustomization();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean featureSpatialAudioHeadtrackingLowLatency() {
        return getValue(Flags.FLAG_FEATURE_SPATIAL_AUDIO_HEADTRACKING_LOW_LATENCY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).featureSpatialAudioHeadtrackingLowLatency();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean focusExclusiveWithRecording() {
        return getValue(Flags.FLAG_FOCUS_EXCLUSIVE_WITH_RECORDING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).focusExclusiveWithRecording();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean focusFreezeTestApi() {
        return getValue(Flags.FLAG_FOCUS_FREEZE_TEST_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).focusFreezeTestApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean foregroundAudioControl() {
        return getValue(Flags.FLAG_FOREGROUND_AUDIO_CONTROL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).foregroundAudioControl();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean hardeningPermissionApi() {
        return getValue(Flags.FLAG_HARDENING_PERMISSION_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hardeningPermissionApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean hardeningPermissionSpa() {
        return getValue(Flags.FLAG_HARDENING_PERMISSION_SPA, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hardeningPermissionSpa();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean iamfDefinitionsApi() {
        return getValue(Flags.FLAG_IAMF_DEFINITIONS_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).iamfDefinitionsApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean loudnessConfiguratorApi() {
        return getValue(Flags.FLAG_LOUDNESS_CONFIGURATOR_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).loudnessConfiguratorApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean muteBackgroundAudio() {
        return getValue(Flags.FLAG_MUTE_BACKGROUND_AUDIO, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).muteBackgroundAudio();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean mutedByPortVolumeApi() {
        return getValue(Flags.FLAG_MUTED_BY_PORT_VOLUME_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mutedByPortVolumeApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean registerVolumeCallbackApiHardening() {
        return getValue(Flags.FLAG_REGISTER_VOLUME_CALLBACK_API_HARDENING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).registerVolumeCallbackApiHardening();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean ringtoneUserUriCheck() {
        return getValue(Flags.FLAG_RINGTONE_USER_URI_CHECK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ringtoneUserUriCheck();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean roForegroundAudioControl() {
        return getValue(Flags.FLAG_RO_FOREGROUND_AUDIO_CONTROL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).roForegroundAudioControl();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean roVolumeRingerApiHardening() {
        return getValue(Flags.FLAG_RO_VOLUME_RINGER_API_HARDENING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).roVolumeRingerApiHardening();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean routedDeviceIds() {
        return getValue(Flags.FLAG_ROUTED_DEVICE_IDS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).routedDeviceIds();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean scoManagedByAudio() {
        return getValue(Flags.FLAG_SCO_MANAGED_BY_AUDIO, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).scoManagedByAudio();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean sony360raMpegh3dFormat() {
        return getValue(Flags.FLAG_SONY_360RA_MPEGH_3D_FORMAT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sony360raMpegh3dFormat();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean spatialAudioSettingsVersioning() {
        return getValue(Flags.FLAG_SPATIAL_AUDIO_SETTINGS_VERSIONING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).spatialAudioSettingsVersioning();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean spatializerCapabilities() {
        return getValue(Flags.FLAG_SPATIALIZER_CAPABILITIES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).spatializerCapabilities();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean speakerCleanupUsage() {
        return getValue(Flags.FLAG_SPEAKER_CLEANUP_USAGE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).speakerCleanupUsage();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean speakerLayoutApi() {
        return getValue(Flags.FLAG_SPEAKER_LAYOUT_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).speakerLayoutApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean supportedDeviceTypesApi() {
        return getValue(Flags.FLAG_SUPPORTED_DEVICE_TYPES_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supportedDeviceTypesApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean unifyAbsoluteVolumeManagement() {
        return getValue(Flags.FLAG_UNIFY_ABSOLUTE_VOLUME_MANAGEMENT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).unifyAbsoluteVolumeManagement();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.audio.FeatureFlags
    public boolean volumeRingerApiHardening() {
        return getValue(Flags.FLAG_VOLUME_RINGER_API_HARDENING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).volumeRingerApiHardening();
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
        return Arrays.asList(Flags.FLAG_AUTO_PUBLIC_VOLUME_API_HARDENING, Flags.FLAG_AUTOMATIC_BT_DEVICE_TYPE, Flags.FLAG_CACHE_GET_STREAM_MIN_MAX_VOLUME, Flags.FLAG_CACHE_GET_STREAM_VOLUME, Flags.FLAG_CONCURRENT_AUDIO_RECORD_BYPASS_PERMISSION, Flags.FLAG_DEPRECATE_STREAM_BT_SCO, Flags.FLAG_DOLBY_AC4_LEVEL4_ENCODING_API, Flags.FLAG_ENABLE_MULTICHANNEL_GROUP_DEVICE, Flags.FLAG_ENABLE_RINGTONE_HAPTICS_CUSTOMIZATION, Flags.FLAG_FEATURE_SPATIAL_AUDIO_HEADTRACKING_LOW_LATENCY, Flags.FLAG_FOCUS_EXCLUSIVE_WITH_RECORDING, Flags.FLAG_FOCUS_FREEZE_TEST_API, Flags.FLAG_FOREGROUND_AUDIO_CONTROL, Flags.FLAG_HARDENING_PERMISSION_API, Flags.FLAG_HARDENING_PERMISSION_SPA, Flags.FLAG_IAMF_DEFINITIONS_API, Flags.FLAG_LOUDNESS_CONFIGURATOR_API, Flags.FLAG_MUTE_BACKGROUND_AUDIO, Flags.FLAG_MUTED_BY_PORT_VOLUME_API, Flags.FLAG_REGISTER_VOLUME_CALLBACK_API_HARDENING, Flags.FLAG_RINGTONE_USER_URI_CHECK, Flags.FLAG_RO_FOREGROUND_AUDIO_CONTROL, Flags.FLAG_RO_VOLUME_RINGER_API_HARDENING, Flags.FLAG_ROUTED_DEVICE_IDS, Flags.FLAG_SCO_MANAGED_BY_AUDIO, Flags.FLAG_SONY_360RA_MPEGH_3D_FORMAT, Flags.FLAG_SPATIAL_AUDIO_SETTINGS_VERSIONING, Flags.FLAG_SPATIALIZER_CAPABILITIES, Flags.FLAG_SPEAKER_CLEANUP_USAGE, Flags.FLAG_SPEAKER_LAYOUT_API, Flags.FLAG_SUPPORTED_DEVICE_TYPES_API, Flags.FLAG_UNIFY_ABSOLUTE_VOLUME_MANAGEMENT, Flags.FLAG_VOLUME_RINGER_API_HARDENING);
    }
}
