package android.media.audiopolicy;

/* loaded from: classes2.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.media.audiopolicy.FeatureFlags
    public boolean audioMixOwnership() {
        return true;
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean audioMixPolicyOrdering() {
        return false;
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean audioMixTestApi() {
        return true;
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean audioPolicyUpdateMixingRulesApi() {
        return true;
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean enableFadeManagerConfiguration() {
        return true;
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean multiZoneAudio() {
        return false;
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean recordAudioDeviceAwarePermission() {
        return true;
    }
}
