package android.tracing;

/* loaded from: classes4.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_CLIENT_SIDE_PROTO_LOGGING = "android.tracing.client_side_proto_logging";
    public static final String FLAG_PERFETTO_IME = "android.tracing.perfetto_ime";
    public static final String FLAG_PERFETTO_IME_TRACING = "android.tracing.perfetto_ime_tracing";
    public static final String FLAG_PERFETTO_PROTOLOG_TRACING = "android.tracing.perfetto_protolog_tracing";
    public static final String FLAG_PERFETTO_TRANSITION_TRACING = "android.tracing.perfetto_transition_tracing";
    public static final String FLAG_PERFETTO_VIEW_CAPTURE_TRACING = "android.tracing.perfetto_view_capture_tracing";
    public static final String FLAG_PERFETTO_WM_DUMP = "android.tracing.perfetto_wm_dump";
    public static final String FLAG_PERFETTO_WM_DUMP_CTS = "android.tracing.perfetto_wm_dump_cts";
    public static final String FLAG_PERFETTO_WM_TRACING = "android.tracing.perfetto_wm_tracing";
    public static final String FLAG_SYSTEM_SERVER_LARGE_PERFETTO_SHMEM_BUFFER = "android.tracing.system_server_large_perfetto_shmem_buffer";

    public static boolean clientSideProtoLogging() {
        return FEATURE_FLAGS.clientSideProtoLogging();
    }

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

    public static boolean perfettoWmDump() {
        return FEATURE_FLAGS.perfettoWmDump();
    }

    public static boolean perfettoWmDumpCts() {
        return FEATURE_FLAGS.perfettoWmDumpCts();
    }

    public static boolean perfettoWmTracing() {
        return FEATURE_FLAGS.perfettoWmTracing();
    }

    public static boolean systemServerLargePerfettoShmemBuffer() {
        return FEATURE_FLAGS.systemServerLargePerfettoShmemBuffer();
    }
}
