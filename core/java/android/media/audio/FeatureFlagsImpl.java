package android.media.audio;

/* loaded from: classes2.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.media.audio.FeatureFlags
    public boolean autoPublicVolumeApiHardening() {
        return false;
    }

    @Override // android.media.audio.FeatureFlags
    public boolean automaticBtDeviceType() {
        return true;
    }

    @Override // android.media.audio.FeatureFlags
    public boolean featureSpatialAudioHeadtrackingLowLatency() {
        return false;
    }

    @Override // android.media.audio.FeatureFlags
    public boolean focusExclusiveWithRecording() {
        return true;
    }

    @Override // android.media.audio.FeatureFlags
    public boolean focusFreezeTestApi() {
        return true;
    }

    @Override // android.media.audio.FeatureFlags
    public boolean foregroundAudioControl() {
        return false;
    }

    @Override // android.media.audio.FeatureFlags
    public boolean loudnessConfiguratorApi() {
        return true;
    }

    @Override // android.media.audio.FeatureFlags
    public boolean muteBackgroundAudio() {
        return false;
    }

    @Override // android.media.audio.FeatureFlags
    public boolean roForegroundAudioControl() {
        return true;
    }

    @Override // android.media.audio.FeatureFlags
    public boolean roVolumeRingerApiHardening() {
        return false;
    }

    @Override // android.media.audio.FeatureFlags
    public boolean scoManagedByAudio() {
        return false;
    }

    @Override // android.media.audio.FeatureFlags
    public boolean supportedDeviceTypesApi() {
        return true;
    }

    @Override // android.media.audio.FeatureFlags
    public boolean volumeRingerApiHardening() {
        return false;
    }
}
