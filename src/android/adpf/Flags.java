package android.adpf;

/* loaded from: classes.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADPF_VIEWROOTIMPL_ACTION_DOWN_BOOST = "android.adpf.adpf_viewrootimpl_action_down_boost";

    public static boolean adpfViewrootimplActionDownBoost() {
        return FEATURE_FLAGS.adpfViewrootimplActionDownBoost();
    }
}
