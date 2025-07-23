package android.media.codec;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.media.codec.FeatureFlags
    public boolean aidlHalInputSurface() {
        return false;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean apvSupport() {
        return true;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean codecAvailability() {
        return true;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean codecAvailabilityMetrics() {
        return false;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean codecAvailabilitySupport() {
        return true;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean codecBufferStateCleanup() {
        return true;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean dataspaceV0Partial() {
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
        return true;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean inProcessSwAudioCodecSupport() {
        return true;
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
    public boolean numInputSlots() {
        return false;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean p210FormatSupport() {
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
    public boolean renderingDepthRemoval() {
        return true;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean secureCodecsRequireCrypto() {
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
    public boolean subsessionMetrics() {
        return true;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean teamfood() {
        return false;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean thumbnailBlockModel() {
        return true;
    }
}
