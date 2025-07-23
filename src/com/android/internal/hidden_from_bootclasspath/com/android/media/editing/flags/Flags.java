package com.android.internal.hidden_from_bootclasspath.com.android.media.editing.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADD_MEDIA_METRICS_EDITING = "com.android.media.editing.flags.add_media_metrics_editing";
    public static final String FLAG_MUXER_MP4_ENABLE_APV = "com.android.media.editing.flags.muxer_mp4_enable_apv";
    public static final String FLAG_STAGEFRIGHTRECORDER_ENABLE_B_FRAMES = "com.android.media.editing.flags.stagefrightrecorder_enable_b_frames";

    public static boolean addMediaMetricsEditing() {
        return FEATURE_FLAGS.addMediaMetricsEditing();
    }

    public static boolean muxerMp4EnableApv() {
        return FEATURE_FLAGS.muxerMp4EnableApv();
    }

    public static boolean stagefrightrecorderEnableBFrames() {
        return FEATURE_FLAGS.stagefrightrecorderEnableBFrames();
    }
}
