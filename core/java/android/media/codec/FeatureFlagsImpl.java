package android.media.codec;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.media.codec.FeatureFlags
    public boolean aidlHalInputSurface() {
        return false;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean dynamicColorAspects() {
        return true;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean hlgEditing() {
        return true;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean inProcessSwAudioCodec() {
        return false;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean inProcessSwAudioCodecSupport() {
        return false;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean inputSurfaceThrottle() {
        return true;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean largeAudioFrameFinish() {
        return true;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean nativeCapabilites() {
        return false;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean nullOutputSurface() {
        return true;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean nullOutputSurfaceSupport() {
        return true;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean regionOfInterest() {
        return true;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean regionOfInterestSupport() {
        return true;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean setCallbackStall() {
        return false;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean setStateEarly() {
        return true;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean stopHalBeforeSurface() {
        return true;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean teamfood() {
        return false;
    }
}
