package android.view;

import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;
import dalvik.annotation.optimization.FastNative;
import java.lang.ref.WeakReference;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes4.dex */
public abstract class DisplayEventReceiver {
    public static final int EVENT_REGISTRATION_FRAME_RATE_OVERRIDE_FLAG = 2;
    public static final int EVENT_REGISTRATION_MODE_CHANGED_FLAG = 1;
    private static final String TAG = "DisplayEventReceiver";
    public static final int VSYNC_SOURCE_APP = 0;
    public static final int VSYNC_SOURCE_SURFACE_FLINGER = 1;
    private static final NativeAllocationRegistry sNativeAllocationRegistry = NativeAllocationRegistry.createMalloced(DisplayEventReceiver.class.getClassLoader(), nativeGetDisplayEventReceiverFinalizer());
    private Runnable mFreeNativeResources;
    private MessageQueue mMessageQueue;
    private long mReceiverPtr;
    private final VsyncEventData mVsyncEventData;

    private static native long nativeGetDisplayEventReceiverFinalizer();

    private static native VsyncEventData nativeGetLatestVsyncEventData(long j);

    private static native long nativeInit(WeakReference<DisplayEventReceiver> weakReference, WeakReference<VsyncEventData> weakReference2, MessageQueue messageQueue, int i, int i2, long j);

    @FastNative
    private static native void nativeScheduleVsync(long j);

    public void onFrameRateOverridesChanged(long j, long j2, FrameRateOverride[] frameRateOverrideArr) {
    }

    public void onHdcpLevelsChanged(long j, int i, int i2) {
    }

    public void onHotplug(long j, long j2, boolean z) {
    }

    public void onHotplugConnectionError(long j, int i) {
    }

    public void onModeChanged(long j, long j2, int i, long j3) {
    }

    public void onModeRejected(long j, int i) {
    }

    public void onVsync(long j, long j2, int i, VsyncEventData vsyncEventData) {
    }

    public DisplayEventReceiver(Looper looper) {
        this(looper, 0, 0, 0L);
    }

    public DisplayEventReceiver(Looper looper, int i, int i2) {
        this(looper, i, i2, 0L);
    }

    public DisplayEventReceiver(Looper looper, int i, int i2, long j) {
        VsyncEventData vsyncEventData = new VsyncEventData();
        this.mVsyncEventData = vsyncEventData;
        if (looper == null) {
            throw new IllegalArgumentException("looper must not be null");
        }
        this.mMessageQueue = looper.getQueue();
        long nativeInit = nativeInit(new WeakReference(this), new WeakReference(vsyncEventData), this.mMessageQueue, i, i2, j);
        this.mReceiverPtr = nativeInit;
        this.mFreeNativeResources = sNativeAllocationRegistry.registerNativeAllocation(this, nativeInit);
    }

    public void dispose() {
        if (this.mReceiverPtr != 0) {
            this.mFreeNativeResources.run();
            this.mReceiverPtr = 0L;
        }
        this.mMessageQueue = null;
    }

    public static final class VsyncEventData {
        static final int FRAME_TIMELINES_CAPACITY = 7;
        public long frameInterval;
        public final FrameTimeline[] frameTimelines;
        public int frameTimelinesLength;
        public int numberQueuedBuffers;
        public int preferredFrameTimelineIndex;

        public static class FrameTimeline {
            public long deadline;
            public long expectedPresentationTime;
            public long vsyncId;

            FrameTimeline() {
                this.vsyncId = -1L;
                long nanoTime = System.nanoTime();
                this.deadline = 10000000 + nanoTime;
                this.expectedPresentationTime = nanoTime + 20000000;
            }

            FrameTimeline(long j, long j2, long j3) {
                this.vsyncId = j;
                this.expectedPresentationTime = j2;
                this.deadline = j3;
            }

            void copyFrom(FrameTimeline frameTimeline) {
                this.vsyncId = frameTimeline.vsyncId;
                this.expectedPresentationTime = frameTimeline.expectedPresentationTime;
                this.deadline = frameTimeline.deadline;
            }
        }

        VsyncEventData() {
            this.frameInterval = -1L;
            int i = 0;
            this.preferredFrameTimelineIndex = 0;
            this.frameTimelinesLength = 1;
            this.numberQueuedBuffers = 0;
            this.frameTimelines = new FrameTimeline[7];
            while (true) {
                FrameTimeline[] frameTimelineArr = this.frameTimelines;
                if (i >= frameTimelineArr.length) {
                    return;
                }
                frameTimelineArr[i] = new FrameTimeline();
                i++;
            }
        }

        VsyncEventData(FrameTimeline[] frameTimelineArr, int i, int i2, long j, int i3) {
            this.frameTimelines = frameTimelineArr;
            this.preferredFrameTimelineIndex = i;
            this.frameTimelinesLength = i2;
            this.frameInterval = j;
            this.numberQueuedBuffers = i3;
        }

        void copyFrom(VsyncEventData vsyncEventData) {
            this.preferredFrameTimelineIndex = vsyncEventData.preferredFrameTimelineIndex;
            this.frameTimelinesLength = vsyncEventData.frameTimelinesLength;
            this.frameInterval = vsyncEventData.frameInterval;
            int i = 0;
            while (true) {
                FrameTimeline[] frameTimelineArr = this.frameTimelines;
                if (i < frameTimelineArr.length) {
                    frameTimelineArr[i].copyFrom(vsyncEventData.frameTimelines[i]);
                    i++;
                } else {
                    this.numberQueuedBuffers = vsyncEventData.numberQueuedBuffers;
                    return;
                }
            }
        }

        public FrameTimeline preferredFrameTimeline() {
            return this.frameTimelines[this.preferredFrameTimelineIndex];
        }
    }

    public static class FrameRateOverride {
        public final float frameRateHz;
        public final int uid;

        public FrameRateOverride(int i, float f) {
            this.uid = i;
            this.frameRateHz = f;
        }

        public String toString() {
            return "{uid=" + this.uid + " frameRateHz=" + this.frameRateHz + "}";
        }
    }

    public void scheduleVsync() {
        long j = this.mReceiverPtr;
        if (j == 0) {
            Log.w(TAG, "Attempted to schedule a vertical sync pulse but the display event receiver has already been disposed.");
        } else {
            nativeScheduleVsync(j);
        }
    }

    VsyncEventData getLatestVsyncEventData() {
        return nativeGetLatestVsyncEventData(this.mReceiverPtr);
    }

    private void dispatchVsync(long j, long j2, int i) {
        onVsync(j, j2, i, this.mVsyncEventData);
    }

    private void dispatchHotplug(long j, long j2, boolean z) {
        onHotplug(j, j2, z);
    }

    private void dispatchHotplugConnectionError(long j, int i) {
        onHotplugConnectionError(j, i);
    }

    private void dispatchModeChanged(long j, long j2, int i, long j3) {
        onModeChanged(j, j2, i, j3);
    }

    private void dispatchModeRejected(long j, int i) {
        onModeRejected(j, i);
    }

    private void dispatchFrameRateOverrides(long j, long j2, FrameRateOverride[] frameRateOverrideArr) {
        onFrameRateOverridesChanged(j, j2, frameRateOverrideArr);
    }

    private void dispatchHdcpLevelsChanged(long j, int i, int i2) {
        onHdcpLevelsChanged(j, i, i2);
    }
}
