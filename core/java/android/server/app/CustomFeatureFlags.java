package android.server.app;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_DISABLE_GAME_MODE_WHEN_APP_TOP,
                            Flags.FLAG_GAME_DEFAULT_FRAME_RATE,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.server.app.FeatureFlags
    public boolean disableGameModeWhenAppTop() {
        return getValue(
                Flags.FLAG_DISABLE_GAME_MODE_WHEN_APP_TOP,
                new Predicate() { // from class:
                                  // android.server.app.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).disableGameModeWhenAppTop();
                    }
                });
    }

    @Override // android.server.app.FeatureFlags
    public boolean gameDefaultFrameRate() {
        return getValue(
                Flags.FLAG_GAME_DEFAULT_FRAME_RATE,
                new Predicate() { // from class:
                                  // android.server.app.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).gameDefaultFrameRate();
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
                Flags.FLAG_DISABLE_GAME_MODE_WHEN_APP_TOP, Flags.FLAG_GAME_DEFAULT_FRAME_RATE);
    }
}
