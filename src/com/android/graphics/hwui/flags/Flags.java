package com.android.graphics.hwui.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ANIMATED_IMAGE_DRAWABLE_FILTER_BITMAP = "com.android.graphics.hwui.flags.animated_image_drawable_filter_bitmap";
    public static final String FLAG_ANIMATE_HDR_TRANSITIONS = "com.android.graphics.hwui.flags.animate_hdr_transitions";
    public static final String FLAG_BITMAP_ASHMEM_LONG_NAME = "com.android.graphics.hwui.flags.bitmap_ashmem_long_name";
    public static final String FLAG_BITMAP_PARCEL_ASHMEM_AS_IMMUTABLE = "com.android.graphics.hwui.flags.bitmap_parcel_ashmem_as_immutable";
    public static final String FLAG_CALC_WORKLOAD_ORIG_DEADLINE = "com.android.graphics.hwui.flags.calc_workload_orig_deadline";
    public static final String FLAG_CLIP_SHADER = "com.android.graphics.hwui.flags.clip_shader";
    public static final String FLAG_CLIP_SURFACEVIEWS = "com.android.graphics.hwui.flags.clip_surfaceviews";
    public static final String FLAG_DRAW_REGION = "com.android.graphics.hwui.flags.draw_region";
    public static final String FLAG_EARLY_PREINIT_BUFFER_ALLOCATOR = "com.android.graphics.hwui.flags.early_preinit_buffer_allocator";
    public static final String FLAG_EARLY_PRELOAD_GL_CONTEXT = "com.android.graphics.hwui.flags.early_preload_gl_context";
    public static final String FLAG_GAINMAP_ANIMATIONS = "com.android.graphics.hwui.flags.gainmap_animations";
    public static final String FLAG_GAINMAP_CONSTRUCTOR_WITH_METADATA = "com.android.graphics.hwui.flags.gainmap_constructor_with_metadata";
    public static final String FLAG_HDR_10BIT_PLUS = "com.android.graphics.hwui.flags.hdr_10bit_plus";
    public static final String FLAG_HIGH_CONTRAST_TEXT_INNER_TEXT_COLOR = "com.android.graphics.hwui.flags.high_contrast_text_inner_text_color";
    public static final String FLAG_HIGH_CONTRAST_TEXT_SMALL_TEXT_RECT = "com.android.graphics.hwui.flags.high_contrast_text_small_text_rect";
    public static final String FLAG_INITIALIZE_GL_ALWAYS = "com.android.graphics.hwui.flags.initialize_gl_always";
    public static final String FLAG_ISO_GAINMAP_APIS = "com.android.graphics.hwui.flags.iso_gainmap_apis";
    public static final String FLAG_LIMITED_HDR = "com.android.graphics.hwui.flags.limited_hdr";
    public static final String FLAG_MATRIX_44 = "com.android.graphics.hwui.flags.matrix_44";
    public static final String FLAG_QUERY_GLOBAL_PRIORITY = "com.android.graphics.hwui.flags.query_global_priority";
    public static final String FLAG_REMOVE_VRI_SKETCHY_DESTROY = "com.android.graphics.hwui.flags.remove_vri_sketchy_destroy";
    public static final String FLAG_REQUESTED_FORMATS_V = "com.android.graphics.hwui.flags.requested_formats_v";
    public static final String FLAG_RESAMPLE_GAINMAP_REGIONS = "com.android.graphics.hwui.flags.resample_gainmap_regions";
    public static final String FLAG_RUNTIME_COLOR_FILTERS_BLENDERS = "com.android.graphics.hwui.flags.runtime_color_filters_blenders";
    public static final String FLAG_SHADER_COLOR_SPACE = "com.android.graphics.hwui.flags.shader_color_space";
    public static final String FLAG_SKIP_EGLMANAGER_TELEMETRY = "com.android.graphics.hwui.flags.skip_eglmanager_telemetry";

    public static boolean animateHdrTransitions() {
        return FEATURE_FLAGS.animateHdrTransitions();
    }

    public static boolean animatedImageDrawableFilterBitmap() {
        return FEATURE_FLAGS.animatedImageDrawableFilterBitmap();
    }

    public static boolean bitmapAshmemLongName() {
        return FEATURE_FLAGS.bitmapAshmemLongName();
    }

    public static boolean bitmapParcelAshmemAsImmutable() {
        return FEATURE_FLAGS.bitmapParcelAshmemAsImmutable();
    }

    public static boolean calcWorkloadOrigDeadline() {
        return FEATURE_FLAGS.calcWorkloadOrigDeadline();
    }

    public static boolean clipShader() {
        return FEATURE_FLAGS.clipShader();
    }

    public static boolean clipSurfaceviews() {
        return FEATURE_FLAGS.clipSurfaceviews();
    }

    public static boolean drawRegion() {
        return FEATURE_FLAGS.drawRegion();
    }

    public static boolean earlyPreinitBufferAllocator() {
        return FEATURE_FLAGS.earlyPreinitBufferAllocator();
    }

    public static boolean earlyPreloadGlContext() {
        return FEATURE_FLAGS.earlyPreloadGlContext();
    }

    public static boolean gainmapAnimations() {
        return FEATURE_FLAGS.gainmapAnimations();
    }

    public static boolean gainmapConstructorWithMetadata() {
        return FEATURE_FLAGS.gainmapConstructorWithMetadata();
    }

    public static boolean hdr10bitPlus() {
        return FEATURE_FLAGS.hdr10bitPlus();
    }

    public static boolean highContrastTextInnerTextColor() {
        return FEATURE_FLAGS.highContrastTextInnerTextColor();
    }

    public static boolean highContrastTextSmallTextRect() {
        return FEATURE_FLAGS.highContrastTextSmallTextRect();
    }

    public static boolean initializeGlAlways() {
        return FEATURE_FLAGS.initializeGlAlways();
    }

    public static boolean isoGainmapApis() {
        return FEATURE_FLAGS.isoGainmapApis();
    }

    public static boolean limitedHdr() {
        return FEATURE_FLAGS.limitedHdr();
    }

    public static boolean matrix44() {
        return FEATURE_FLAGS.matrix44();
    }

    public static boolean queryGlobalPriority() {
        return FEATURE_FLAGS.queryGlobalPriority();
    }

    public static boolean removeVriSketchyDestroy() {
        return FEATURE_FLAGS.removeVriSketchyDestroy();
    }

    public static boolean requestedFormatsV() {
        return FEATURE_FLAGS.requestedFormatsV();
    }

    public static boolean resampleGainmapRegions() {
        return FEATURE_FLAGS.resampleGainmapRegions();
    }

    public static boolean runtimeColorFiltersBlenders() {
        return FEATURE_FLAGS.runtimeColorFiltersBlenders();
    }

    public static boolean shaderColorSpace() {
        return FEATURE_FLAGS.shaderColorSpace();
    }

    public static boolean skipEglmanagerTelemetry() {
        return FEATURE_FLAGS.skipEglmanagerTelemetry();
    }
}
