package com.android.graphics.hwui.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ANIMATE_HDR_TRANSITIONS, Flags.FLAG_ANIMATED_IMAGE_DRAWABLE_FILTER_BITMAP, Flags.FLAG_BITMAP_ASHMEM_LONG_NAME, Flags.FLAG_BITMAP_PARCEL_ASHMEM_AS_IMMUTABLE, Flags.FLAG_CALC_WORKLOAD_ORIG_DEADLINE, Flags.FLAG_CLIP_SHADER, Flags.FLAG_CLIP_SURFACEVIEWS, Flags.FLAG_DRAW_REGION, Flags.FLAG_EARLY_PREINIT_BUFFER_ALLOCATOR, Flags.FLAG_EARLY_PRELOAD_GL_CONTEXT, Flags.FLAG_GAINMAP_ANIMATIONS, Flags.FLAG_GAINMAP_CONSTRUCTOR_WITH_METADATA, Flags.FLAG_HDR_10BIT_PLUS, Flags.FLAG_HIGH_CONTRAST_TEXT_INNER_TEXT_COLOR, Flags.FLAG_HIGH_CONTRAST_TEXT_SMALL_TEXT_RECT, Flags.FLAG_INITIALIZE_GL_ALWAYS, Flags.FLAG_ISO_GAINMAP_APIS, Flags.FLAG_LIMITED_HDR, Flags.FLAG_MATRIX_44, Flags.FLAG_QUERY_GLOBAL_PRIORITY, Flags.FLAG_REMOVE_VRI_SKETCHY_DESTROY, Flags.FLAG_REQUESTED_FORMATS_V, Flags.FLAG_RESAMPLE_GAINMAP_REGIONS, Flags.FLAG_RUNTIME_COLOR_FILTERS_BLENDERS, Flags.FLAG_SHADER_COLOR_SPACE, Flags.FLAG_SKIP_EGLMANAGER_TELEMETRY, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean animateHdrTransitions() {
        return getValue(Flags.FLAG_ANIMATE_HDR_TRANSITIONS, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).animateHdrTransitions();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean animatedImageDrawableFilterBitmap() {
        return getValue(Flags.FLAG_ANIMATED_IMAGE_DRAWABLE_FILTER_BITMAP, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).animatedImageDrawableFilterBitmap();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean bitmapAshmemLongName() {
        return getValue(Flags.FLAG_BITMAP_ASHMEM_LONG_NAME, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).bitmapAshmemLongName();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean bitmapParcelAshmemAsImmutable() {
        return getValue(Flags.FLAG_BITMAP_PARCEL_ASHMEM_AS_IMMUTABLE, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).bitmapParcelAshmemAsImmutable();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean calcWorkloadOrigDeadline() {
        return getValue(Flags.FLAG_CALC_WORKLOAD_ORIG_DEADLINE, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).calcWorkloadOrigDeadline();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean clipShader() {
        return getValue(Flags.FLAG_CLIP_SHADER, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).clipShader();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean clipSurfaceviews() {
        return getValue(Flags.FLAG_CLIP_SURFACEVIEWS, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).clipSurfaceviews();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean drawRegion() {
        return getValue(Flags.FLAG_DRAW_REGION, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).drawRegion();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean earlyPreinitBufferAllocator() {
        return getValue(Flags.FLAG_EARLY_PREINIT_BUFFER_ALLOCATOR, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).earlyPreinitBufferAllocator();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean earlyPreloadGlContext() {
        return getValue(Flags.FLAG_EARLY_PRELOAD_GL_CONTEXT, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).earlyPreloadGlContext();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean gainmapAnimations() {
        return getValue(Flags.FLAG_GAINMAP_ANIMATIONS, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).gainmapAnimations();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean gainmapConstructorWithMetadata() {
        return getValue(Flags.FLAG_GAINMAP_CONSTRUCTOR_WITH_METADATA, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).gainmapConstructorWithMetadata();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean hdr10bitPlus() {
        return getValue(Flags.FLAG_HDR_10BIT_PLUS, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hdr10bitPlus();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean highContrastTextInnerTextColor() {
        return getValue(Flags.FLAG_HIGH_CONTRAST_TEXT_INNER_TEXT_COLOR, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).highContrastTextInnerTextColor();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean highContrastTextSmallTextRect() {
        return getValue(Flags.FLAG_HIGH_CONTRAST_TEXT_SMALL_TEXT_RECT, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).highContrastTextSmallTextRect();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean initializeGlAlways() {
        return getValue(Flags.FLAG_INITIALIZE_GL_ALWAYS, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).initializeGlAlways();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean isoGainmapApis() {
        return getValue(Flags.FLAG_ISO_GAINMAP_APIS, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).isoGainmapApis();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean limitedHdr() {
        return getValue(Flags.FLAG_LIMITED_HDR, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).limitedHdr();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean matrix44() {
        return getValue(Flags.FLAG_MATRIX_44, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).matrix44();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean queryGlobalPriority() {
        return getValue(Flags.FLAG_QUERY_GLOBAL_PRIORITY, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).queryGlobalPriority();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean removeVriSketchyDestroy() {
        return getValue(Flags.FLAG_REMOVE_VRI_SKETCHY_DESTROY, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeVriSketchyDestroy();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean requestedFormatsV() {
        return getValue(Flags.FLAG_REQUESTED_FORMATS_V, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).requestedFormatsV();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean resampleGainmapRegions() {
        return getValue(Flags.FLAG_RESAMPLE_GAINMAP_REGIONS, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).resampleGainmapRegions();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean runtimeColorFiltersBlenders() {
        return getValue(Flags.FLAG_RUNTIME_COLOR_FILTERS_BLENDERS, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).runtimeColorFiltersBlenders();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean shaderColorSpace() {
        return getValue(Flags.FLAG_SHADER_COLOR_SPACE, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).shaderColorSpace();
            }
        });
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean skipEglmanagerTelemetry() {
        return getValue(Flags.FLAG_SKIP_EGLMANAGER_TELEMETRY, new Predicate() { // from class: com.android.graphics.hwui.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).skipEglmanagerTelemetry();
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
        return Arrays.asList(Flags.FLAG_ANIMATE_HDR_TRANSITIONS, Flags.FLAG_ANIMATED_IMAGE_DRAWABLE_FILTER_BITMAP, Flags.FLAG_BITMAP_ASHMEM_LONG_NAME, Flags.FLAG_BITMAP_PARCEL_ASHMEM_AS_IMMUTABLE, Flags.FLAG_CALC_WORKLOAD_ORIG_DEADLINE, Flags.FLAG_CLIP_SHADER, Flags.FLAG_CLIP_SURFACEVIEWS, Flags.FLAG_DRAW_REGION, Flags.FLAG_EARLY_PREINIT_BUFFER_ALLOCATOR, Flags.FLAG_EARLY_PRELOAD_GL_CONTEXT, Flags.FLAG_GAINMAP_ANIMATIONS, Flags.FLAG_GAINMAP_CONSTRUCTOR_WITH_METADATA, Flags.FLAG_HDR_10BIT_PLUS, Flags.FLAG_HIGH_CONTRAST_TEXT_INNER_TEXT_COLOR, Flags.FLAG_HIGH_CONTRAST_TEXT_SMALL_TEXT_RECT, Flags.FLAG_INITIALIZE_GL_ALWAYS, Flags.FLAG_ISO_GAINMAP_APIS, Flags.FLAG_LIMITED_HDR, Flags.FLAG_MATRIX_44, Flags.FLAG_QUERY_GLOBAL_PRIORITY, Flags.FLAG_REMOVE_VRI_SKETCHY_DESTROY, Flags.FLAG_REQUESTED_FORMATS_V, Flags.FLAG_RESAMPLE_GAINMAP_REGIONS, Flags.FLAG_RUNTIME_COLOR_FILTERS_BLENDERS, Flags.FLAG_SHADER_COLOR_SPACE, Flags.FLAG_SKIP_EGLMANAGER_TELEMETRY);
    }
}
