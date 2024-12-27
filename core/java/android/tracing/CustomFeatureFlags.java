package android.tracing;

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
                            Flags.FLAG_PERFETTO_IME,
                            Flags.FLAG_PERFETTO_IME_TRACING,
                            Flags.FLAG_PERFETTO_PROTOLOG_TRACING,
                            Flags.FLAG_PERFETTO_TRANSITION_TRACING,
                            Flags.FLAG_PERFETTO_VIEW_CAPTURE_TRACING,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoIme() {
        return getValue(
                Flags.FLAG_PERFETTO_IME,
                new Predicate() { // from class:
                                  // android.tracing.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).perfettoIme();
                    }
                });
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoImeTracing() {
        return getValue(
                Flags.FLAG_PERFETTO_IME_TRACING,
                new Predicate() { // from class:
                                  // android.tracing.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).perfettoImeTracing();
                    }
                });
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoProtologTracing() {
        return getValue(
                Flags.FLAG_PERFETTO_PROTOLOG_TRACING,
                new Predicate() { // from class:
                                  // android.tracing.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).perfettoProtologTracing();
                    }
                });
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoTransitionTracing() {
        return getValue(
                Flags.FLAG_PERFETTO_TRANSITION_TRACING,
                new Predicate() { // from class:
                                  // android.tracing.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).perfettoTransitionTracing();
                    }
                });
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoViewCaptureTracing() {
        return getValue(
                Flags.FLAG_PERFETTO_VIEW_CAPTURE_TRACING,
                new Predicate() { // from class:
                                  // android.tracing.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).perfettoViewCaptureTracing();
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
                Flags.FLAG_PERFETTO_IME,
                Flags.FLAG_PERFETTO_IME_TRACING,
                Flags.FLAG_PERFETTO_PROTOLOG_TRACING,
                Flags.FLAG_PERFETTO_TRANSITION_TRACING,
                Flags.FLAG_PERFETTO_VIEW_CAPTURE_TRACING);
    }
}
