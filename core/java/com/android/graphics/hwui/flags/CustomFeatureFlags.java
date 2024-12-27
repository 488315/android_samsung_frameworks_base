package com.android.graphics.hwui.flags;

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
                            Flags.FLAG_ANIMATE_HDR_TRANSITIONS,
                            Flags.FLAG_CLIP_SHADER,
                            Flags.FLAG_CLIP_SURFACEVIEWS,
                            Flags.FLAG_DRAW_REGION,
                            Flags.FLAG_GAINMAP_ANIMATIONS,
                            Flags.FLAG_GAINMAP_CONSTRUCTOR_WITH_METADATA,
                            Flags.FLAG_HDR_10BIT_PLUS,
                            Flags.FLAG_HIGH_CONTRAST_TEXT_LUMINANCE,
                            Flags.FLAG_HIGH_CONTRAST_TEXT_SMALL_TEXT_RECT,
                            Flags.FLAG_INITIALIZE_GL_ALWAYS,
                            Flags.FLAG_LIMITED_HDR,
                            Flags.FLAG_MATRIX_44,
                            Flags.FLAG_REQUESTED_FORMATS_V,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean animateHdrTransitions() {
        return getValue(
                Flags.FLAG_ANIMATE_HDR_TRANSITIONS,
                new Predicate() { // from class:
                                  // com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).animateHdrTransitions();
                    }
                });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean clipShader() {
        return getValue(
                Flags.FLAG_CLIP_SHADER,
                new Predicate() { // from class:
                                  // com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).clipShader();
                    }
                });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean clipSurfaceviews() {
        return getValue(
                Flags.FLAG_CLIP_SURFACEVIEWS,
                new Predicate() { // from class:
                                  // com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).clipSurfaceviews();
                    }
                });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean drawRegion() {
        return getValue(
                Flags.FLAG_DRAW_REGION,
                new Predicate() { // from class:
                                  // com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).drawRegion();
                    }
                });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean gainmapAnimations() {
        return getValue(
                Flags.FLAG_GAINMAP_ANIMATIONS,
                new Predicate() { // from class:
                                  // com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).gainmapAnimations();
                    }
                });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean gainmapConstructorWithMetadata() {
        return getValue(
                Flags.FLAG_GAINMAP_CONSTRUCTOR_WITH_METADATA,
                new Predicate() { // from class:
                                  // com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).gainmapConstructorWithMetadata();
                    }
                });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean hdr10bitPlus() {
        return getValue(
                Flags.FLAG_HDR_10BIT_PLUS,
                new Predicate() { // from class:
                                  // com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).hdr10bitPlus();
                    }
                });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean highContrastTextLuminance() {
        return getValue(
                Flags.FLAG_HIGH_CONTRAST_TEXT_LUMINANCE,
                new Predicate() { // from class:
                                  // com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).highContrastTextLuminance();
                    }
                });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean highContrastTextSmallTextRect() {
        return getValue(
                Flags.FLAG_HIGH_CONTRAST_TEXT_SMALL_TEXT_RECT,
                new Predicate() { // from class:
                                  // com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).highContrastTextSmallTextRect();
                    }
                });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean initializeGlAlways() {
        return getValue(
                Flags.FLAG_INITIALIZE_GL_ALWAYS,
                new Predicate() { // from class:
                                  // com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).initializeGlAlways();
                    }
                });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean limitedHdr() {
        return getValue(
                Flags.FLAG_LIMITED_HDR,
                new Predicate() { // from class:
                                  // com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).limitedHdr();
                    }
                });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean matrix44() {
        return getValue(
                Flags.FLAG_MATRIX_44,
                new Predicate() { // from class:
                                  // com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).matrix44();
                    }
                });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean requestedFormatsV() {
        return getValue(
                Flags.FLAG_REQUESTED_FORMATS_V,
                new Predicate() { // from class:
                                  // com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).requestedFormatsV();
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
                Flags.FLAG_ANIMATE_HDR_TRANSITIONS,
                Flags.FLAG_CLIP_SHADER,
                Flags.FLAG_CLIP_SURFACEVIEWS,
                Flags.FLAG_DRAW_REGION,
                Flags.FLAG_GAINMAP_ANIMATIONS,
                Flags.FLAG_GAINMAP_CONSTRUCTOR_WITH_METADATA,
                Flags.FLAG_HDR_10BIT_PLUS,
                Flags.FLAG_HIGH_CONTRAST_TEXT_LUMINANCE,
                Flags.FLAG_HIGH_CONTRAST_TEXT_SMALL_TEXT_RECT,
                Flags.FLAG_INITIALIZE_GL_ALWAYS,
                Flags.FLAG_LIMITED_HDR,
                Flags.FLAG_MATRIX_44,
                Flags.FLAG_REQUESTED_FORMATS_V);
    }
}
