package com.android.internal.hidden_from_bootclasspath.com.android.hardware.input;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_EMOJI_AND_SCREENSHOT_KEYCODES_AVAILABLE, Flags.FLAG_KEYBOARD_A11Y_BOUNCE_KEYS_FLAG, Flags.FLAG_KEYBOARD_A11Y_SLOW_KEYS_FLAG, Flags.FLAG_KEYBOARD_A11Y_STICKY_KEYS_FLAG, Flags.FLAG_KEYBOARD_LAYOUT_PREVIEW_FLAG, Flags.FLAG_POINTER_COORDS_IS_RESAMPLED_API, Flags.FLAG_TOUCHPAD_TAP_DRAGGING, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean emojiAndScreenshotKeycodesAvailable() {
        return getValue(Flags.FLAG_EMOJI_AND_SCREENSHOT_KEYCODES_AVAILABLE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).emojiAndScreenshotKeycodesAvailable();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean keyboardA11yBounceKeysFlag() {
        return getValue(Flags.FLAG_KEYBOARD_A11Y_BOUNCE_KEYS_FLAG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keyboardA11yBounceKeysFlag();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean keyboardA11ySlowKeysFlag() {
        return getValue(Flags.FLAG_KEYBOARD_A11Y_SLOW_KEYS_FLAG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keyboardA11ySlowKeysFlag();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean keyboardA11yStickyKeysFlag() {
        return getValue(Flags.FLAG_KEYBOARD_A11Y_STICKY_KEYS_FLAG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keyboardA11yStickyKeysFlag();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean keyboardLayoutPreviewFlag() {
        return getValue(Flags.FLAG_KEYBOARD_LAYOUT_PREVIEW_FLAG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keyboardLayoutPreviewFlag();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean pointerCoordsIsResampledApi() {
        return getValue(Flags.FLAG_POINTER_COORDS_IS_RESAMPLED_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).pointerCoordsIsResampledApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean touchpadTapDragging() {
        return getValue(Flags.FLAG_TOUCHPAD_TAP_DRAGGING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).touchpadTapDragging();
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
        return Arrays.asList(Flags.FLAG_EMOJI_AND_SCREENSHOT_KEYCODES_AVAILABLE, Flags.FLAG_KEYBOARD_A11Y_BOUNCE_KEYS_FLAG, Flags.FLAG_KEYBOARD_A11Y_SLOW_KEYS_FLAG, Flags.FLAG_KEYBOARD_A11Y_STICKY_KEYS_FLAG, Flags.FLAG_KEYBOARD_LAYOUT_PREVIEW_FLAG, Flags.FLAG_POINTER_COORDS_IS_RESAMPLED_API, Flags.FLAG_TOUCHPAD_TAP_DRAGGING);
    }
}
