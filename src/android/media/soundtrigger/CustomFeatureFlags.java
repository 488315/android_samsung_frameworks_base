package android.media.soundtrigger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_DETECTION_SERVICE_PAUSED_RESUMED_API, Flags.FLAG_GENERIC_MODEL_API, Flags.FLAG_MANAGER_API, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.media.soundtrigger.FeatureFlags
    public boolean detectionServicePausedResumedApi() {
        return getValue(Flags.FLAG_DETECTION_SERVICE_PAUSED_RESUMED_API, new Predicate() { // from class: android.media.soundtrigger.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).detectionServicePausedResumedApi();
            }
        });
    }

    @Override // android.media.soundtrigger.FeatureFlags
    public boolean genericModelApi() {
        return getValue(Flags.FLAG_GENERIC_MODEL_API, new Predicate() { // from class: android.media.soundtrigger.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).genericModelApi();
            }
        });
    }

    @Override // android.media.soundtrigger.FeatureFlags
    public boolean managerApi() {
        return getValue(Flags.FLAG_MANAGER_API, new Predicate() { // from class: android.media.soundtrigger.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).managerApi();
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
        return Arrays.asList(Flags.FLAG_DETECTION_SERVICE_PAUSED_RESUMED_API, Flags.FLAG_GENERIC_MODEL_API, Flags.FLAG_MANAGER_API);
    }
}
