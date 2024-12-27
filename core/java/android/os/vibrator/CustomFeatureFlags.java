package android.os.vibrator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ADAPTIVE_HAPTICS_ENABLED, Flags.FLAG_HAPTIC_FEEDBACK_VIBRATION_OEM_CUSTOMIZATION_ENABLED, Flags.FLAG_KEYBOARD_CATEGORY_ENABLED, Flags.FLAG_USE_VIBRATOR_HAPTIC_FEEDBACK, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean adaptiveHapticsEnabled() {
        return getValue(Flags.FLAG_ADAPTIVE_HAPTICS_ENABLED, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adaptiveHapticsEnabled();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean hapticFeedbackVibrationOemCustomizationEnabled() {
        return getValue(Flags.FLAG_HAPTIC_FEEDBACK_VIBRATION_OEM_CUSTOMIZATION_ENABLED, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hapticFeedbackVibrationOemCustomizationEnabled();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean keyboardCategoryEnabled() {
        return getValue(Flags.FLAG_KEYBOARD_CATEGORY_ENABLED, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keyboardCategoryEnabled();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean useVibratorHapticFeedback() {
        return getValue(Flags.FLAG_USE_VIBRATOR_HAPTIC_FEEDBACK, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useVibratorHapticFeedback();
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
        return Arrays.asList(Flags.FLAG_ADAPTIVE_HAPTICS_ENABLED, Flags.FLAG_HAPTIC_FEEDBACK_VIBRATION_OEM_CUSTOMIZATION_ENABLED, Flags.FLAG_KEYBOARD_CATEGORY_ENABLED, Flags.FLAG_USE_VIBRATOR_HAPTIC_FEEDBACK);
    }
}
