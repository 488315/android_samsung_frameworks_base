package android.tracing;

/* loaded from: classes4.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_PERFETTO_IME = "android.tracing.perfetto_ime";
    public static final String FLAG_PERFETTO_IME_TRACING = "android.tracing.perfetto_ime_tracing";
    public static final String FLAG_PERFETTO_PROTOLOG_TRACING = "android.tracing.perfetto_protolog_tracing";
    public static final String FLAG_PERFETTO_TRANSITION_TRACING = "android.tracing.perfetto_transition_tracing";
    public static final String FLAG_PERFETTO_VIEW_CAPTURE_TRACING = "android.tracing.perfetto_view_capture_tracing";

    public static boolean perfettoIme() {
        return FEATURE_FLAGS.perfettoIme();
    }

    public static boolean perfettoImeTracing() {
        return FEATURE_FLAGS.perfettoImeTracing();
    }

    public static boolean perfettoProtologTracing() {
        return FEATURE_FLAGS.perfettoProtologTracing();
    }

    public static boolean perfettoTransitionTracing() {
        return FEATURE_FLAGS.perfettoTransitionTracing();
    }

    public static boolean perfettoViewCaptureTracing() {
        return FEATURE_FLAGS.perfettoViewCaptureTracing();
    }
}
