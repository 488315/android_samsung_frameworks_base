package android.media.audiopolicy;

public interface FeatureFlags {
    boolean audioMixOwnership();

    boolean audioMixPolicyOrdering();

    boolean audioMixTestApi();

    boolean audioPolicyUpdateMixingRulesApi();

    boolean enableFadeManagerConfiguration();

    boolean multiZoneAudio();

    boolean recordAudioDeviceAwarePermission();
}
