package android.app.assist.flags;

/* loaded from: classes.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADD_PLACEHOLDER_VIEW_FOR_NULL_CHILD = "android.app.assist.flags.add_placeholder_view_for_null_child";

    public static boolean addPlaceholderViewForNullChild() {
        return FEATURE_FLAGS.addPlaceholderViewForNullChild();
    }
}
