package android.tracing;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_CLIENT_SIDE_PROTO_LOGGING, Flags.FLAG_PERFETTO_IME, Flags.FLAG_PERFETTO_IME_TRACING, Flags.FLAG_PERFETTO_PROTOLOG_TRACING, Flags.FLAG_PERFETTO_TRANSITION_TRACING, Flags.FLAG_PERFETTO_VIEW_CAPTURE_TRACING, Flags.FLAG_PERFETTO_WM_DUMP, Flags.FLAG_PERFETTO_WM_DUMP_CTS, Flags.FLAG_PERFETTO_WM_TRACING, Flags.FLAG_SYSTEM_SERVER_LARGE_PERFETTO_SHMEM_BUFFER, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.tracing.FeatureFlags
    public boolean clientSideProtoLogging() {
        return getValue(Flags.FLAG_CLIENT_SIDE_PROTO_LOGGING, new Predicate() { // from class: android.tracing.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).clientSideProtoLogging();
            }
        });
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoIme() {
        return getValue(Flags.FLAG_PERFETTO_IME, new Predicate() { // from class: android.tracing.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).perfettoIme();
            }
        });
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoImeTracing() {
        return getValue(Flags.FLAG_PERFETTO_IME_TRACING, new Predicate() { // from class: android.tracing.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).perfettoImeTracing();
            }
        });
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoProtologTracing() {
        return getValue(Flags.FLAG_PERFETTO_PROTOLOG_TRACING, new Predicate() { // from class: android.tracing.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).perfettoProtologTracing();
            }
        });
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoTransitionTracing() {
        return getValue(Flags.FLAG_PERFETTO_TRANSITION_TRACING, new Predicate() { // from class: android.tracing.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).perfettoTransitionTracing();
            }
        });
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoViewCaptureTracing() {
        return getValue(Flags.FLAG_PERFETTO_VIEW_CAPTURE_TRACING, new Predicate() { // from class: android.tracing.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).perfettoViewCaptureTracing();
            }
        });
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoWmDump() {
        return getValue(Flags.FLAG_PERFETTO_WM_DUMP, new Predicate() { // from class: android.tracing.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).perfettoWmDump();
            }
        });
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoWmDumpCts() {
        return getValue(Flags.FLAG_PERFETTO_WM_DUMP_CTS, new Predicate() { // from class: android.tracing.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).perfettoWmDumpCts();
            }
        });
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoWmTracing() {
        return getValue(Flags.FLAG_PERFETTO_WM_TRACING, new Predicate() { // from class: android.tracing.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).perfettoWmTracing();
            }
        });
    }

    @Override // android.tracing.FeatureFlags
    public boolean systemServerLargePerfettoShmemBuffer() {
        return getValue(Flags.FLAG_SYSTEM_SERVER_LARGE_PERFETTO_SHMEM_BUFFER, new Predicate() { // from class: android.tracing.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).systemServerLargePerfettoShmemBuffer();
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
        return Arrays.asList(Flags.FLAG_CLIENT_SIDE_PROTO_LOGGING, Flags.FLAG_PERFETTO_IME, Flags.FLAG_PERFETTO_IME_TRACING, Flags.FLAG_PERFETTO_PROTOLOG_TRACING, Flags.FLAG_PERFETTO_TRANSITION_TRACING, Flags.FLAG_PERFETTO_VIEW_CAPTURE_TRACING, Flags.FLAG_PERFETTO_WM_DUMP, Flags.FLAG_PERFETTO_WM_DUMP_CTS, Flags.FLAG_PERFETTO_WM_TRACING, Flags.FLAG_SYSTEM_SERVER_LARGE_PERFETTO_SHMEM_BUFFER);
    }
}
