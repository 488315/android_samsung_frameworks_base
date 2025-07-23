package com.android.graphics.libgui.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ALLOCATE_BUFFER_PRIORITY = "com.android.graphics.libgui.flags.allocate_buffer_priority";
    public static final String FLAG_APPLY_PICTURE_PROFILES = "com.android.graphics.libgui.flags.apply_picture_profiles";
    public static final String FLAG_BQ_ALWAYS_USE_MAX_DEQUEUED_BUFFER_COUNT = "com.android.graphics.libgui.flags.bq_always_use_max_dequeued_buffer_count";
    public static final String FLAG_BQ_CONSUMER_ATTACH_CALLBACK = "com.android.graphics.libgui.flags.bq_consumer_attach_callback";
    public static final String FLAG_BQ_EXTENDEDALLOCATE = "com.android.graphics.libgui.flags.bq_extendedallocate";
    public static final String FLAG_BQ_GL_FENCE_CLEANUP = "com.android.graphics.libgui.flags.bq_gl_fence_cleanup";
    public static final String FLAG_BQ_PRODUCER_THROTTLES_ONLY_ASYNC_MODE = "com.android.graphics.libgui.flags.bq_producer_throttles_only_async_mode";
    public static final String FLAG_BQ_SETFRAMERATE = "com.android.graphics.libgui.flags.bq_setframerate";
    public static final String FLAG_BUFFER_RELEASE_CHANNEL = "com.android.graphics.libgui.flags.buffer_release_channel";
    public static final String FLAG_FRAMETIMESTAMPS_PREVIOUSRELEASE = "com.android.graphics.libgui.flags.frametimestamps_previousrelease";
    public static final String FLAG_TRACE_FRAME_RATE_OVERRIDE = "com.android.graphics.libgui.flags.trace_frame_rate_override";
    public static final String FLAG_WB_CAMERA3_AND_PROCESSORS = "com.android.graphics.libgui.flags.wb_camera3_and_processors";
    public static final String FLAG_WB_CONSUMER_BASE_OWNS_BQ = "com.android.graphics.libgui.flags.wb_consumer_base_owns_bq";
    public static final String FLAG_WB_LIBCAMERASERVICE = "com.android.graphics.libgui.flags.wb_libcameraservice";
    public static final String FLAG_WB_MEDIA_MIGRATION = "com.android.graphics.libgui.flags.wb_media_migration";
    public static final String FLAG_WB_PLATFORM_API_IMPROVEMENTS = "com.android.graphics.libgui.flags.wb_platform_api_improvements";
    public static final String FLAG_WB_RING_BUFFER = "com.android.graphics.libgui.flags.wb_ring_buffer";
    public static final String FLAG_WB_STREAM_SPLITTER = "com.android.graphics.libgui.flags.wb_stream_splitter";
    public static final String FLAG_WB_UNLIMITED_SLOTS = "com.android.graphics.libgui.flags.wb_unlimited_slots";

    public static boolean allocateBufferPriority() {
        return FEATURE_FLAGS.allocateBufferPriority();
    }

    public static boolean applyPictureProfiles() {
        return FEATURE_FLAGS.applyPictureProfiles();
    }

    public static boolean bqAlwaysUseMaxDequeuedBufferCount() {
        return FEATURE_FLAGS.bqAlwaysUseMaxDequeuedBufferCount();
    }

    public static boolean bqConsumerAttachCallback() {
        return FEATURE_FLAGS.bqConsumerAttachCallback();
    }

    public static boolean bqExtendedallocate() {
        return FEATURE_FLAGS.bqExtendedallocate();
    }

    public static boolean bqGlFenceCleanup() {
        return FEATURE_FLAGS.bqGlFenceCleanup();
    }

    public static boolean bqProducerThrottlesOnlyAsyncMode() {
        return FEATURE_FLAGS.bqProducerThrottlesOnlyAsyncMode();
    }

    public static boolean bqSetframerate() {
        return FEATURE_FLAGS.bqSetframerate();
    }

    public static boolean bufferReleaseChannel() {
        return FEATURE_FLAGS.bufferReleaseChannel();
    }

    public static boolean frametimestampsPreviousrelease() {
        return FEATURE_FLAGS.frametimestampsPreviousrelease();
    }

    public static boolean traceFrameRateOverride() {
        return FEATURE_FLAGS.traceFrameRateOverride();
    }

    public static boolean wbCamera3AndProcessors() {
        return FEATURE_FLAGS.wbCamera3AndProcessors();
    }

    public static boolean wbConsumerBaseOwnsBq() {
        return FEATURE_FLAGS.wbConsumerBaseOwnsBq();
    }

    public static boolean wbLibcameraservice() {
        return FEATURE_FLAGS.wbLibcameraservice();
    }

    public static boolean wbMediaMigration() {
        return FEATURE_FLAGS.wbMediaMigration();
    }

    public static boolean wbPlatformApiImprovements() {
        return FEATURE_FLAGS.wbPlatformApiImprovements();
    }

    public static boolean wbRingBuffer() {
        return FEATURE_FLAGS.wbRingBuffer();
    }

    public static boolean wbStreamSplitter() {
        return FEATURE_FLAGS.wbStreamSplitter();
    }

    public static boolean wbUnlimitedSlots() {
        return FEATURE_FLAGS.wbUnlimitedSlots();
    }
}
