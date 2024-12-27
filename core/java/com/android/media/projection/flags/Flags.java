package com.android.media.projection.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_LIMIT_MANAGE_MEDIA_PROJECTION = "com.android.media.projection.flags.limit_manage_media_projection";

    public static boolean limitManageMediaProjection() {
        return FEATURE_FLAGS.limitManageMediaProjection();
    }
}
