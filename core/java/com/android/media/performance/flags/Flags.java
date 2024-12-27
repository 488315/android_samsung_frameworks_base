package com.android.media.performance.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_MEDIA_DESCRIPTION_ASHMEM_BITMAP = "com.android.media.performance.flags.media_description_ashmem_bitmap";

    public static boolean mediaDescriptionAshmemBitmap() {
        return FEATURE_FLAGS.mediaDescriptionAshmemBitmap();
    }
}
