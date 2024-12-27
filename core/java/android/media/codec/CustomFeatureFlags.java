package android.media.codec;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_AIDL_HAL_INPUT_SURFACE,
                            Flags.FLAG_DYNAMIC_COLOR_ASPECTS,
                            Flags.FLAG_HLG_EDITING,
                            Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC,
                            Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC_SUPPORT,
                            Flags.FLAG_INPUT_SURFACE_THROTTLE,
                            Flags.FLAG_LARGE_AUDIO_FRAME_FINISH,
                            Flags.FLAG_NATIVE_CAPABILITES,
                            Flags.FLAG_NULL_OUTPUT_SURFACE,
                            Flags.FLAG_NULL_OUTPUT_SURFACE_SUPPORT,
                            Flags.FLAG_REGION_OF_INTEREST,
                            Flags.FLAG_REGION_OF_INTEREST_SUPPORT,
                            Flags.FLAG_SET_CALLBACK_STALL,
                            Flags.FLAG_SET_STATE_EARLY,
                            Flags.FLAG_STOP_HAL_BEFORE_SURFACE,
                            Flags.FLAG_TEAMFOOD,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.media.codec.FeatureFlags
    public boolean aidlHalInputSurface() {
        return getValue(
                Flags.FLAG_AIDL_HAL_INPUT_SURFACE,
                new Predicate() { // from class:
                    // android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).aidlHalInputSurface();
                    }
                });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean dynamicColorAspects() {
        return getValue(
                Flags.FLAG_DYNAMIC_COLOR_ASPECTS,
                new Predicate() { // from class:
                    // android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).dynamicColorAspects();
                    }
                });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean hlgEditing() {
        return getValue(
                Flags.FLAG_HLG_EDITING,
                new Predicate() { // from class:
                    // android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).hlgEditing();
                    }
                });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean inProcessSwAudioCodec() {
        return getValue(
                Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC,
                new Predicate() { // from class:
                    // android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda15
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).inProcessSwAudioCodec();
                    }
                });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean inProcessSwAudioCodecSupport() {
        return getValue(
                Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC_SUPPORT,
                new Predicate() { // from class:
                    // android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).inProcessSwAudioCodecSupport();
                    }
                });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean inputSurfaceThrottle() {
        return getValue(
                Flags.FLAG_INPUT_SURFACE_THROTTLE,
                new Predicate() { // from class:
                    // android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).inputSurfaceThrottle();
                    }
                });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean largeAudioFrameFinish() {
        return getValue(
                Flags.FLAG_LARGE_AUDIO_FRAME_FINISH,
                new Predicate() { // from class:
                    // android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).largeAudioFrameFinish();
                    }
                });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean nativeCapabilites() {
        return getValue(
                Flags.FLAG_NATIVE_CAPABILITES,
                new Predicate() { // from class:
                    // android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).nativeCapabilites();
                    }
                });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean nullOutputSurface() {
        return getValue(
                Flags.FLAG_NULL_OUTPUT_SURFACE,
                new Predicate() { // from class:
                    // android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).nullOutputSurface();
                    }
                });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean nullOutputSurfaceSupport() {
        return getValue(
                Flags.FLAG_NULL_OUTPUT_SURFACE_SUPPORT,
                new Predicate() { // from class:
                    // android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).nullOutputSurfaceSupport();
                    }
                });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean regionOfInterest() {
        return getValue(
                Flags.FLAG_REGION_OF_INTEREST,
                new Predicate() { // from class:
                    // android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).regionOfInterest();
                    }
                });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean regionOfInterestSupport() {
        return getValue(
                Flags.FLAG_REGION_OF_INTEREST_SUPPORT,
                new Predicate() { // from class:
                    // android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).regionOfInterestSupport();
                    }
                });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean setCallbackStall() {
        return getValue(
                Flags.FLAG_SET_CALLBACK_STALL,
                new Predicate() { // from class:
                    // android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda14
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).setCallbackStall();
                    }
                });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean setStateEarly() {
        return getValue(
                Flags.FLAG_SET_STATE_EARLY,
                new Predicate() { // from class:
                    // android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda13
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).setStateEarly();
                    }
                });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean stopHalBeforeSurface() {
        return getValue(
                Flags.FLAG_STOP_HAL_BEFORE_SURFACE,
                new Predicate() { // from class:
                    // android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).stopHalBeforeSurface();
                    }
                });
    }

    @Override // android.media.codec.FeatureFlags
    public boolean teamfood() {
        return getValue(
                Flags.FLAG_TEAMFOOD,
                new Predicate() { // from class:
                    // android.media.codec.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).teamfood();
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
                Flags.FLAG_AIDL_HAL_INPUT_SURFACE,
                Flags.FLAG_DYNAMIC_COLOR_ASPECTS,
                Flags.FLAG_HLG_EDITING,
                Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC,
                Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC_SUPPORT,
                Flags.FLAG_INPUT_SURFACE_THROTTLE,
                Flags.FLAG_LARGE_AUDIO_FRAME_FINISH,
                Flags.FLAG_NATIVE_CAPABILITES,
                Flags.FLAG_NULL_OUTPUT_SURFACE,
                Flags.FLAG_NULL_OUTPUT_SURFACE_SUPPORT,
                Flags.FLAG_REGION_OF_INTEREST,
                Flags.FLAG_REGION_OF_INTEREST_SUPPORT,
                Flags.FLAG_SET_CALLBACK_STALL,
                Flags.FLAG_SET_STATE_EARLY,
                Flags.FLAG_STOP_HAL_BEFORE_SURFACE,
                Flags.FLAG_TEAMFOOD);
    }
}
