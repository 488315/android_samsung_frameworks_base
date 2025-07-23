package android.media.codec;

/* loaded from: classes3.dex */
public interface FeatureFlags {
    boolean aidlHalInputSurface();

    boolean apvSupport();

    boolean codecAvailability();

    boolean codecAvailabilityMetrics();

    boolean codecAvailabilitySupport();

    boolean codecBufferStateCleanup();

    boolean dataspaceV0Partial();

    boolean dynamicColorAspects();

    boolean hlgEditing();

    boolean inProcessSwAudioCodec();

    boolean inProcessSwAudioCodecSupport();

    boolean inputSurfaceThrottle();

    boolean largeAudioFrameFinish();

    boolean nativeCapabilites();

    boolean nullOutputSurface();

    boolean nullOutputSurfaceSupport();

    boolean numInputSlots();

    boolean p210FormatSupport();

    boolean regionOfInterest();

    boolean regionOfInterestSupport();

    boolean renderingDepthRemoval();

    boolean secureCodecsRequireCrypto();

    boolean setCallbackStall();

    boolean setStateEarly();

    boolean stopHalBeforeSurface();

    boolean subsessionMetrics();

    boolean teamfood();

    boolean thumbnailBlockModel();
}
