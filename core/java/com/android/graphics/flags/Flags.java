package com.android.graphics.flags;

/* loaded from: classes3.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_EXACT_COMPUTE_BOUNDS =
            "com.android.graphics.flags.exact_compute_bounds";
    public static final String FLAG_ICON_LOAD_DRAWABLE_RETURN_NULL_WHEN_URI_DECODE_FAILS =
            "com.android.graphics.flags.icon_load_drawable_return_null_when_uri_decode_fails";
    public static final String FLAG_OK_LAB_COLORSPACE =
            "com.android.graphics.flags.ok_lab_colorspace";
    public static final String FLAG_YUV_IMAGE_COMPRESS_TO_ULTRA_HDR =
            "com.android.graphics.flags.yuv_image_compress_to_ultra_hdr";

    public static boolean exactComputeBounds() {
        return FEATURE_FLAGS.exactComputeBounds();
    }

    public static boolean iconLoadDrawableReturnNullWhenUriDecodeFails() {
        return FEATURE_FLAGS.iconLoadDrawableReturnNullWhenUriDecodeFails();
    }

    public static boolean okLabColorspace() {
        return FEATURE_FLAGS.okLabColorspace();
    }

    public static boolean yuvImageCompressToUltraHdr() {
        return FEATURE_FLAGS.yuvImageCompressToUltraHdr();
    }
}
