package android.media.audiopolicy;

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
                            Flags.FLAG_AUDIO_MIX_OWNERSHIP,
                            Flags.FLAG_AUDIO_MIX_POLICY_ORDERING,
                            Flags.FLAG_AUDIO_MIX_TEST_API,
                            Flags.FLAG_AUDIO_POLICY_UPDATE_MIXING_RULES_API,
                            Flags.FLAG_ENABLE_FADE_MANAGER_CONFIGURATION,
                            Flags.FLAG_MULTI_ZONE_AUDIO,
                            Flags.FLAG_RECORD_AUDIO_DEVICE_AWARE_PERMISSION,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean audioMixOwnership() {
        return getValue(
                Flags.FLAG_AUDIO_MIX_OWNERSHIP,
                new Predicate() { // from class:
                    // android.media.audiopolicy.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).audioMixOwnership();
                    }
                });
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean audioMixPolicyOrdering() {
        return getValue(
                Flags.FLAG_AUDIO_MIX_POLICY_ORDERING,
                new Predicate() { // from class:
                    // android.media.audiopolicy.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).audioMixPolicyOrdering();
                    }
                });
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean audioMixTestApi() {
        return getValue(
                Flags.FLAG_AUDIO_MIX_TEST_API,
                new Predicate() { // from class:
                    // android.media.audiopolicy.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).audioMixTestApi();
                    }
                });
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean audioPolicyUpdateMixingRulesApi() {
        return getValue(
                Flags.FLAG_AUDIO_POLICY_UPDATE_MIXING_RULES_API,
                new Predicate() { // from class:
                    // android.media.audiopolicy.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).audioPolicyUpdateMixingRulesApi();
                    }
                });
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean enableFadeManagerConfiguration() {
        return getValue(
                Flags.FLAG_ENABLE_FADE_MANAGER_CONFIGURATION,
                new Predicate() { // from class:
                    // android.media.audiopolicy.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableFadeManagerConfiguration();
                    }
                });
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean multiZoneAudio() {
        return getValue(
                Flags.FLAG_MULTI_ZONE_AUDIO,
                new Predicate() { // from class:
                    // android.media.audiopolicy.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).multiZoneAudio();
                    }
                });
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean recordAudioDeviceAwarePermission() {
        return getValue(
                Flags.FLAG_RECORD_AUDIO_DEVICE_AWARE_PERMISSION,
                new Predicate() { // from class:
                    // android.media.audiopolicy.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).recordAudioDeviceAwarePermission();
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
                Flags.FLAG_AUDIO_MIX_OWNERSHIP,
                Flags.FLAG_AUDIO_MIX_POLICY_ORDERING,
                Flags.FLAG_AUDIO_MIX_TEST_API,
                Flags.FLAG_AUDIO_POLICY_UPDATE_MIXING_RULES_API,
                Flags.FLAG_ENABLE_FADE_MANAGER_CONFIGURATION,
                Flags.FLAG_MULTI_ZONE_AUDIO,
                Flags.FLAG_RECORD_AUDIO_DEVICE_AWARE_PERMISSION);
    }
}
