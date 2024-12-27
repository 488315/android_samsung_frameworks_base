package com.android.graphics.hwui.flags;

/* loaded from: classes3.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ANIMATE_HDR_TRANSITIONS = "com.android.graphics.hwui.flags.animate_hdr_transitions";
    public static final String FLAG_CLIP_SHADER = "com.android.graphics.hwui.flags.clip_shader";
    public static final String FLAG_CLIP_SURFACEVIEWS = "com.android.graphics.hwui.flags.clip_surfaceviews";
    public static final String FLAG_DRAW_REGION = "com.android.graphics.hwui.flags.draw_region";
    public static final String FLAG_GAINMAP_ANIMATIONS = "com.android.graphics.hwui.flags.gainmap_animations";
    public static final String FLAG_GAINMAP_CONSTRUCTOR_WITH_METADATA = "com.android.graphics.hwui.flags.gainmap_constructor_with_metadata";
    public static final String FLAG_HDR_10BIT_PLUS = "com.android.graphics.hwui.flags.hdr_10bit_plus";
    public static final String FLAG_HIGH_CONTRAST_TEXT_LUMINANCE = "com.android.graphics.hwui.flags.high_contrast_text_luminance";
    public static final String FLAG_HIGH_CONTRAST_TEXT_SMALL_TEXT_RECT = "com.android.graphics.hwui.flags.high_contrast_text_small_text_rect";
    public static final String FLAG_INITIALIZE_GL_ALWAYS = "com.android.graphics.hwui.flags.initialize_gl_always";
    public static final String FLAG_LIMITED_HDR = "com.android.graphics.hwui.flags.limited_hdr";
    public static final String FLAG_MATRIX_44 = "com.android.graphics.hwui.flags.matrix_44";
    public static final String FLAG_REQUESTED_FORMATS_V = "com.android.graphics.hwui.flags.requested_formats_v";

    public static boolean animateHdrTransitions() {
        return FEATURE_FLAGS.animateHdrTransitions();
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

    public static boolean gainmapAnimations() {
        return FEATURE_FLAGS.gainmapAnimations();
    }

    public static boolean gainmapConstructorWithMetadata() {
        return FEATURE_FLAGS.gainmapConstructorWithMetadata();
    }

    public static boolean hdr10bitPlus() {
        return FEATURE_FLAGS.hdr10bitPlus();
    }

    public static boolean highContrastTextLuminance() {
        return FEATURE_FLAGS.highContrastTextLuminance();
    }

    public static boolean highContrastTextSmallTextRect() {
        return FEATURE_FLAGS.highContrastTextSmallTextRect();
    }

    public static boolean initializeGlAlways() {
        return FEATURE_FLAGS.initializeGlAlways();
    }

    public static boolean limitedHdr() {
        return FEATURE_FLAGS.limitedHdr();
    }

    public static boolean matrix44() {
        return FEATURE_FLAGS.matrix44();
    }

    public static boolean requestedFormatsV() {
        return FEATURE_FLAGS.requestedFormatsV();
    }
}
