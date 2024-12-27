package android.media.midi;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_VIRTUAL_UMP = "android.media.midi.virtual_ump";

    public static boolean virtualUmp() {
        return FEATURE_FLAGS.virtualUmp();
    }
}
