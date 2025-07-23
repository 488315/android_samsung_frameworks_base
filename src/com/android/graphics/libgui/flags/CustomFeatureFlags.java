package com.android.graphics.libgui.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ALLOCATE_BUFFER_PRIORITY, Flags.FLAG_APPLY_PICTURE_PROFILES, Flags.FLAG_BQ_ALWAYS_USE_MAX_DEQUEUED_BUFFER_COUNT, Flags.FLAG_BQ_CONSUMER_ATTACH_CALLBACK, Flags.FLAG_BQ_EXTENDEDALLOCATE, Flags.FLAG_BQ_GL_FENCE_CLEANUP, Flags.FLAG_BQ_PRODUCER_THROTTLES_ONLY_ASYNC_MODE, Flags.FLAG_BQ_SETFRAMERATE, Flags.FLAG_BUFFER_RELEASE_CHANNEL, Flags.FLAG_FRAMETIMESTAMPS_PREVIOUSRELEASE, Flags.FLAG_TRACE_FRAME_RATE_OVERRIDE, Flags.FLAG_WB_CAMERA3_AND_PROCESSORS, Flags.FLAG_WB_CONSUMER_BASE_OWNS_BQ, Flags.FLAG_WB_LIBCAMERASERVICE, Flags.FLAG_WB_MEDIA_MIGRATION, Flags.FLAG_WB_PLATFORM_API_IMPROVEMENTS, Flags.FLAG_WB_RING_BUFFER, Flags.FLAG_WB_STREAM_SPLITTER, Flags.FLAG_WB_UNLIMITED_SLOTS, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean allocateBufferPriority() {
        return getValue(Flags.FLAG_ALLOCATE_BUFFER_PRIORITY, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allocateBufferPriority();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean applyPictureProfiles() {
        return getValue(Flags.FLAG_APPLY_PICTURE_PROFILES, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).applyPictureProfiles();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean bqAlwaysUseMaxDequeuedBufferCount() {
        return getValue(Flags.FLAG_BQ_ALWAYS_USE_MAX_DEQUEUED_BUFFER_COUNT, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).bqAlwaysUseMaxDequeuedBufferCount();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean bqConsumerAttachCallback() {
        return getValue(Flags.FLAG_BQ_CONSUMER_ATTACH_CALLBACK, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).bqConsumerAttachCallback();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean bqExtendedallocate() {
        return getValue(Flags.FLAG_BQ_EXTENDEDALLOCATE, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).bqExtendedallocate();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean bqGlFenceCleanup() {
        return getValue(Flags.FLAG_BQ_GL_FENCE_CLEANUP, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).bqGlFenceCleanup();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean bqProducerThrottlesOnlyAsyncMode() {
        return getValue(Flags.FLAG_BQ_PRODUCER_THROTTLES_ONLY_ASYNC_MODE, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).bqProducerThrottlesOnlyAsyncMode();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean bqSetframerate() {
        return getValue(Flags.FLAG_BQ_SETFRAMERATE, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).bqSetframerate();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean bufferReleaseChannel() {
        return getValue(Flags.FLAG_BUFFER_RELEASE_CHANNEL, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).bufferReleaseChannel();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean frametimestampsPreviousrelease() {
        return getValue(Flags.FLAG_FRAMETIMESTAMPS_PREVIOUSRELEASE, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).frametimestampsPreviousrelease();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean traceFrameRateOverride() {
        return getValue(Flags.FLAG_TRACE_FRAME_RATE_OVERRIDE, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).traceFrameRateOverride();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean wbCamera3AndProcessors() {
        return getValue(Flags.FLAG_WB_CAMERA3_AND_PROCESSORS, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).wbCamera3AndProcessors();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean wbConsumerBaseOwnsBq() {
        return getValue(Flags.FLAG_WB_CONSUMER_BASE_OWNS_BQ, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).wbConsumerBaseOwnsBq();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean wbLibcameraservice() {
        return getValue(Flags.FLAG_WB_LIBCAMERASERVICE, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).wbLibcameraservice();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean wbMediaMigration() {
        return getValue(Flags.FLAG_WB_MEDIA_MIGRATION, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).wbMediaMigration();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean wbPlatformApiImprovements() {
        return getValue(Flags.FLAG_WB_PLATFORM_API_IMPROVEMENTS, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).wbPlatformApiImprovements();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean wbRingBuffer() {
        return getValue(Flags.FLAG_WB_RING_BUFFER, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).wbRingBuffer();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean wbStreamSplitter() {
        return getValue(Flags.FLAG_WB_STREAM_SPLITTER, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).wbStreamSplitter();
            }
        });
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean wbUnlimitedSlots() {
        return getValue(Flags.FLAG_WB_UNLIMITED_SLOTS, new Predicate() { // from class: com.android.graphics.libgui.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).wbUnlimitedSlots();
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
        return Arrays.asList(Flags.FLAG_ALLOCATE_BUFFER_PRIORITY, Flags.FLAG_APPLY_PICTURE_PROFILES, Flags.FLAG_BQ_ALWAYS_USE_MAX_DEQUEUED_BUFFER_COUNT, Flags.FLAG_BQ_CONSUMER_ATTACH_CALLBACK, Flags.FLAG_BQ_EXTENDEDALLOCATE, Flags.FLAG_BQ_GL_FENCE_CLEANUP, Flags.FLAG_BQ_PRODUCER_THROTTLES_ONLY_ASYNC_MODE, Flags.FLAG_BQ_SETFRAMERATE, Flags.FLAG_BUFFER_RELEASE_CHANNEL, Flags.FLAG_FRAMETIMESTAMPS_PREVIOUSRELEASE, Flags.FLAG_TRACE_FRAME_RATE_OVERRIDE, Flags.FLAG_WB_CAMERA3_AND_PROCESSORS, Flags.FLAG_WB_CONSUMER_BASE_OWNS_BQ, Flags.FLAG_WB_LIBCAMERASERVICE, Flags.FLAG_WB_MEDIA_MIGRATION, Flags.FLAG_WB_PLATFORM_API_IMPROVEMENTS, Flags.FLAG_WB_RING_BUFFER, Flags.FLAG_WB_STREAM_SPLITTER, Flags.FLAG_WB_UNLIMITED_SLOTS);
    }
}
