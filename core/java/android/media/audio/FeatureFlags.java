package android.media.audio;

public interface FeatureFlags {
    boolean autoPublicVolumeApiHardening();

    boolean automaticBtDeviceType();

    boolean featureSpatialAudioHeadtrackingLowLatency();

    boolean focusExclusiveWithRecording();

    boolean focusFreezeTestApi();

    boolean foregroundAudioControl();

    boolean loudnessConfiguratorApi();

    boolean muteBackgroundAudio();

    boolean roForegroundAudioControl();

    boolean roVolumeRingerApiHardening();

    boolean scoManagedByAudio();

    boolean supportedDeviceTypesApi();

    boolean volumeRingerApiHardening();
}
