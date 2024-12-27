package android.media.audio;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_AUTO_PUBLIC_VOLUME_API_HARDENING,
                            Flags.FLAG_AUTOMATIC_BT_DEVICE_TYPE,
                            Flags.FLAG_FEATURE_SPATIAL_AUDIO_HEADTRACKING_LOW_LATENCY,
                            Flags.FLAG_FOCUS_EXCLUSIVE_WITH_RECORDING,
                            Flags.FLAG_FOCUS_FREEZE_TEST_API,
                            Flags.FLAG_FOREGROUND_AUDIO_CONTROL,
                            Flags.FLAG_LOUDNESS_CONFIGURATOR_API,
                            Flags.FLAG_MUTE_BACKGROUND_AUDIO,
                            Flags.FLAG_RO_FOREGROUND_AUDIO_CONTROL,
                            Flags.FLAG_RO_VOLUME_RINGER_API_HARDENING,
                            Flags.FLAG_SCO_MANAGED_BY_AUDIO,
                            Flags.FLAG_SUPPORTED_DEVICE_TYPES_API,
                            Flags.FLAG_VOLUME_RINGER_API_HARDENING,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.media.audio.FeatureFlags
    public boolean autoPublicVolumeApiHardening() {
        return getValue(
                Flags.FLAG_AUTO_PUBLIC_VOLUME_API_HARDENING,
                new Predicate() { // from class:
                    // android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).autoPublicVolumeApiHardening();
                    }
                });
    }

    @Override // android.media.audio.FeatureFlags
    public boolean automaticBtDeviceType() {
        return getValue(
                Flags.FLAG_AUTOMATIC_BT_DEVICE_TYPE,
                new Predicate() { // from class:
                    // android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).automaticBtDeviceType();
                    }
                });
    }

    @Override // android.media.audio.FeatureFlags
    public boolean featureSpatialAudioHeadtrackingLowLatency() {
        return getValue(
                Flags.FLAG_FEATURE_SPATIAL_AUDIO_HEADTRACKING_LOW_LATENCY,
                new Predicate() { // from class:
                    // android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).featureSpatialAudioHeadtrackingLowLatency();
                    }
                });
    }

    @Override // android.media.audio.FeatureFlags
    public boolean focusExclusiveWithRecording() {
        return getValue(
                Flags.FLAG_FOCUS_EXCLUSIVE_WITH_RECORDING,
                new Predicate() { // from class:
                    // android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).focusExclusiveWithRecording();
                    }
                });
    }

    @Override // android.media.audio.FeatureFlags
    public boolean focusFreezeTestApi() {
        return getValue(
                Flags.FLAG_FOCUS_FREEZE_TEST_API,
                new Predicate() { // from class:
                    // android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).focusFreezeTestApi();
                    }
                });
    }

    @Override // android.media.audio.FeatureFlags
    public boolean foregroundAudioControl() {
        return getValue(
                Flags.FLAG_FOREGROUND_AUDIO_CONTROL,
                new Predicate() { // from class:
                    // android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).foregroundAudioControl();
                    }
                });
    }

    @Override // android.media.audio.FeatureFlags
    public boolean loudnessConfiguratorApi() {
        return getValue(
                Flags.FLAG_LOUDNESS_CONFIGURATOR_API,
                new Predicate() { // from class:
                    // android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).loudnessConfiguratorApi();
                    }
                });
    }

    @Override // android.media.audio.FeatureFlags
    public boolean muteBackgroundAudio() {
        return getValue(
                Flags.FLAG_MUTE_BACKGROUND_AUDIO,
                new Predicate() { // from class:
                    // android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).muteBackgroundAudio();
                    }
                });
    }

    @Override // android.media.audio.FeatureFlags
    public boolean roForegroundAudioControl() {
        return getValue(
                Flags.FLAG_RO_FOREGROUND_AUDIO_CONTROL,
                new Predicate() { // from class:
                    // android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).roForegroundAudioControl();
                    }
                });
    }

    @Override // android.media.audio.FeatureFlags
    public boolean roVolumeRingerApiHardening() {
        return getValue(
                Flags.FLAG_RO_VOLUME_RINGER_API_HARDENING,
                new Predicate() { // from class:
                    // android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).roVolumeRingerApiHardening();
                    }
                });
    }

    @Override // android.media.audio.FeatureFlags
    public boolean scoManagedByAudio() {
        return getValue(
                Flags.FLAG_SCO_MANAGED_BY_AUDIO,
                new Predicate() { // from class:
                    // android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).scoManagedByAudio();
                    }
                });
    }

    @Override // android.media.audio.FeatureFlags
    public boolean supportedDeviceTypesApi() {
        return getValue(
                Flags.FLAG_SUPPORTED_DEVICE_TYPES_API,
                new Predicate() { // from class:
                    // android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).supportedDeviceTypesApi();
                    }
                });
    }

    @Override // android.media.audio.FeatureFlags
    public boolean volumeRingerApiHardening() {
        return getValue(
                Flags.FLAG_VOLUME_RINGER_API_HARDENING,
                new Predicate() { // from class:
                    // android.media.audio.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).volumeRingerApiHardening();
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
                Flags.FLAG_AUTO_PUBLIC_VOLUME_API_HARDENING,
                Flags.FLAG_AUTOMATIC_BT_DEVICE_TYPE,
                Flags.FLAG_FEATURE_SPATIAL_AUDIO_HEADTRACKING_LOW_LATENCY,
                Flags.FLAG_FOCUS_EXCLUSIVE_WITH_RECORDING,
                Flags.FLAG_FOCUS_FREEZE_TEST_API,
                Flags.FLAG_FOREGROUND_AUDIO_CONTROL,
                Flags.FLAG_LOUDNESS_CONFIGURATOR_API,
                Flags.FLAG_MUTE_BACKGROUND_AUDIO,
                Flags.FLAG_RO_FOREGROUND_AUDIO_CONTROL,
                Flags.FLAG_RO_VOLUME_RINGER_API_HARDENING,
                Flags.FLAG_SCO_MANAGED_BY_AUDIO,
                Flags.FLAG_SUPPORTED_DEVICE_TYPES_API,
                Flags.FLAG_VOLUME_RINGER_API_HARDENING);
    }
}
