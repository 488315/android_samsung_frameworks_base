package android.media.codec;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_AIDL_HAL_INPUT_SURFACE, Flags.FLAG_APV_SUPPORT, Flags.FLAG_CODEC_AVAILABILITY, Flags.FLAG_CODEC_AVAILABILITY_METRICS, Flags.FLAG_CODEC_AVAILABILITY_SUPPORT, Flags.FLAG_CODEC_BUFFER_STATE_CLEANUP, Flags.FLAG_DATASPACE_V0_PARTIAL, Flags.FLAG_DYNAMIC_COLOR_ASPECTS, Flags.FLAG_HLG_EDITING, Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC, Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC_SUPPORT, Flags.FLAG_INPUT_SURFACE_THROTTLE, Flags.FLAG_LARGE_AUDIO_FRAME_FINISH, Flags.FLAG_NATIVE_CAPABILITES, Flags.FLAG_NULL_OUTPUT_SURFACE, Flags.FLAG_NULL_OUTPUT_SURFACE_SUPPORT, Flags.FLAG_NUM_INPUT_SLOTS, Flags.FLAG_P210_FORMAT_SUPPORT, Flags.FLAG_REGION_OF_INTEREST, Flags.FLAG_REGION_OF_INTEREST_SUPPORT, Flags.FLAG_RENDERING_DEPTH_REMOVAL, Flags.FLAG_SECURE_CODECS_REQUIRE_CRYPTO, Flags.FLAG_SET_CALLBACK_STALL, Flags.FLAG_SET_STATE_EARLY, Flags.FLAG_STOP_HAL_BEFORE_SURFACE, Flags.FLAG_SUBSESSION_METRICS, Flags.FLAG_TEAMFOOD, Flags.FLAG_THUMBNAIL_BLOCK_MODEL, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean aidlHalInputSurface() {
        return getValue(Flags.FLAG_AIDL_HAL_INPUT_SURFACE, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).aidlHalInputSurface();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean apvSupport() {
        return getValue(Flags.FLAG_APV_SUPPORT, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).apvSupport();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean codecAvailability() {
        return getValue(Flags.FLAG_CODEC_AVAILABILITY, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).codecAvailability();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean codecAvailabilityMetrics() {
        return getValue(Flags.FLAG_CODEC_AVAILABILITY_METRICS, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).codecAvailabilityMetrics();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean codecAvailabilitySupport() {
        return getValue(Flags.FLAG_CODEC_AVAILABILITY_SUPPORT, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).codecAvailabilitySupport();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean codecBufferStateCleanup() {
        return getValue(Flags.FLAG_CODEC_BUFFER_STATE_CLEANUP, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).codecBufferStateCleanup();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean dataspaceV0Partial() {
        return getValue(Flags.FLAG_DATASPACE_V0_PARTIAL, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dataspaceV0Partial();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean dynamicColorAspects() {
        return getValue(Flags.FLAG_DYNAMIC_COLOR_ASPECTS, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dynamicColorAspects();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean hlgEditing() {
        return getValue(Flags.FLAG_HLG_EDITING, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hlgEditing();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean inProcessSwAudioCodec() {
        return getValue(Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).inProcessSwAudioCodec();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean inProcessSwAudioCodecSupport() {
        return getValue(Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC_SUPPORT, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).inProcessSwAudioCodecSupport();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean inputSurfaceThrottle() {
        return getValue(Flags.FLAG_INPUT_SURFACE_THROTTLE, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).inputSurfaceThrottle();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean largeAudioFrameFinish() {
        return getValue(Flags.FLAG_LARGE_AUDIO_FRAME_FINISH, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).largeAudioFrameFinish();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean nativeCapabilites() {
        return getValue(Flags.FLAG_NATIVE_CAPABILITES, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nativeCapabilites();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean nullOutputSurface() {
        return getValue(Flags.FLAG_NULL_OUTPUT_SURFACE, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nullOutputSurface();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean nullOutputSurfaceSupport() {
        return getValue(Flags.FLAG_NULL_OUTPUT_SURFACE_SUPPORT, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nullOutputSurfaceSupport();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean numInputSlots() {
        return getValue(Flags.FLAG_NUM_INPUT_SLOTS, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).numInputSlots();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean p210FormatSupport() {
        return getValue(Flags.FLAG_P210_FORMAT_SUPPORT, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).p210FormatSupport();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean regionOfInterest() {
        return getValue(Flags.FLAG_REGION_OF_INTEREST, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).regionOfInterest();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean regionOfInterestSupport() {
        return getValue(Flags.FLAG_REGION_OF_INTEREST_SUPPORT, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).regionOfInterestSupport();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean renderingDepthRemoval() {
        return getValue(Flags.FLAG_RENDERING_DEPTH_REMOVAL, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).renderingDepthRemoval();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean secureCodecsRequireCrypto() {
        return getValue(Flags.FLAG_SECURE_CODECS_REQUIRE_CRYPTO, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).secureCodecsRequireCrypto();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean setCallbackStall() {
        return getValue(Flags.FLAG_SET_CALLBACK_STALL, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setCallbackStall();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean setStateEarly() {
        return getValue(Flags.FLAG_SET_STATE_EARLY, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setStateEarly();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean stopHalBeforeSurface() {
        return getValue(Flags.FLAG_STOP_HAL_BEFORE_SURFACE, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).stopHalBeforeSurface();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean subsessionMetrics() {
        return getValue(Flags.FLAG_SUBSESSION_METRICS, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).subsessionMetrics();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean teamfood() {
        return getValue(Flags.FLAG_TEAMFOOD, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).teamfood();
            }
        });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean thumbnailBlockModel() {
        return getValue(Flags.FLAG_THUMBNAIL_BLOCK_MODEL, new Predicate() { // from class: android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).thumbnailBlockModel();
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
        return Arrays.asList(Flags.FLAG_AIDL_HAL_INPUT_SURFACE, Flags.FLAG_APV_SUPPORT, Flags.FLAG_CODEC_AVAILABILITY, Flags.FLAG_CODEC_AVAILABILITY_METRICS, Flags.FLAG_CODEC_AVAILABILITY_SUPPORT, Flags.FLAG_CODEC_BUFFER_STATE_CLEANUP, Flags.FLAG_DATASPACE_V0_PARTIAL, Flags.FLAG_DYNAMIC_COLOR_ASPECTS, Flags.FLAG_HLG_EDITING, Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC, Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC_SUPPORT, Flags.FLAG_INPUT_SURFACE_THROTTLE, Flags.FLAG_LARGE_AUDIO_FRAME_FINISH, Flags.FLAG_NATIVE_CAPABILITES, Flags.FLAG_NULL_OUTPUT_SURFACE, Flags.FLAG_NULL_OUTPUT_SURFACE_SUPPORT, Flags.FLAG_NUM_INPUT_SLOTS, Flags.FLAG_P210_FORMAT_SUPPORT, Flags.FLAG_REGION_OF_INTEREST, Flags.FLAG_REGION_OF_INTEREST_SUPPORT, Flags.FLAG_RENDERING_DEPTH_REMOVAL, Flags.FLAG_SECURE_CODECS_REQUIRE_CRYPTO, Flags.FLAG_SET_CALLBACK_STALL, Flags.FLAG_SET_STATE_EARLY, Flags.FLAG_STOP_HAL_BEFORE_SURFACE, Flags.FLAG_SUBSESSION_METRICS, Flags.FLAG_TEAMFOOD, Flags.FLAG_THUMBNAIL_BLOCK_MODEL);
    }
}
