package com.android.internal.jank;

import android.graphics.HardwareRendererObserver;
import android.os.Handler;
import android.os.Trace;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.PerfLog;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.FrameMetrics;
import android.view.SurfaceControl;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowCallbacks;
import com.android.internal.jank.FrameTracker;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.util.FrameworkStatsLog;
import com.samsung.android.rune.CoreRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public class FrameTracker extends SurfaceControl.OnJankDataListener implements HardwareRendererObserver.OnFrameMetricsAvailableListener {
    private static final int FLUSH_DELAY_MILLISECOND = 60;
    private static final long INVALID_ID = -1;
    private static final int MAX_FLUSH_ATTEMPTS = 3;
    private static final int MAX_LENGTH_EVENT_DESC = 127;
    public static final int NANOS_IN_MILLISECOND = 1000000;
    static final int REASON_CANCEL_NORMAL = 16;
    static final int REASON_CANCEL_NOT_BEGUN = 17;
    static final int REASON_CANCEL_SAME_VSYNC = 18;
    static final int REASON_CANCEL_TIMEOUT = 19;
    static final int REASON_END_NORMAL = 0;
    static final int REASON_END_SURFACE_DESTROYED = 1;
    static final int REASON_END_UNKNOWN = -1;
    private static final String TAG = "FrameTracker";
    private final ChoreographerWrapper mChoreographer;
    private final InteractionJankMonitor.Configuration mConfig;
    private final boolean mDeferMonitoring;
    private final int mDisplayId;
    private final Handler mHandler;
    private final FrameTrackerListener mListener;
    private boolean mMetricsFinalized;
    private final FrameMetricsWrapper mMetricsWrapper;
    private final HardwareRendererObserver mObserver;
    private final ThreadedRendererWrapper mRendererWrapper;
    private final StatsLogWrapper mStatsLog;
    private final ViewRootImpl.SurfaceChangedCallback mSurfaceChangedCallback;
    private SurfaceControl mSurfaceControl;
    private final SurfaceControlWrapper mSurfaceControlWrapper;
    public final boolean mSurfaceOnly;
    private final int mTraceThresholdFrameTimeMillis;
    private final int mTraceThresholdMissedFrames;
    private final ViewRootWrapper mViewRoot;
    private Runnable mWaitForFinishTimedOut;
    private final SparseArray<JankInfo> mJankInfos = new SparseArray<>();
    private long mBeginVsyncId = -1;
    private long mEndVsyncId = -1;
    private boolean mCancelled = false;
    private boolean mTracingStarted = false;

    public interface FrameTrackerListener {
        void onCujEvents(FrameTracker frameTracker, String str, int i);

        void triggerPerfetto(InteractionJankMonitor.Configuration configuration);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Reasons {
    }

    private static class JankInfo {
        long frameVsyncId;
        boolean hwuiCallbackFired;
        boolean isFirstFrame;
        int jankType;
        int refreshRate;
        boolean surfaceControlCallbackFired;
        long totalDurationNanos;

        static JankInfo createFromHwuiCallback(long frameVsyncId, long totalDurationNanos, boolean isFirstFrame) {
            return new JankInfo(frameVsyncId, true, false, 0, 0, totalDurationNanos, isFirstFrame);
        }

        static JankInfo createFromSurfaceControlCallback(long frameVsyncId, int jankType, int refreshRate) {
            return new JankInfo(frameVsyncId, false, true, jankType, refreshRate, 0L, false);
        }

        private JankInfo(long frameVsyncId, boolean hwuiCallbackFired, boolean surfaceControlCallbackFired, int jankType, int refreshRate, long totalDurationNanos, boolean isFirstFrame) {
            this.frameVsyncId = frameVsyncId;
            this.hwuiCallbackFired = hwuiCallbackFired;
            this.surfaceControlCallbackFired = surfaceControlCallbackFired;
            this.jankType = jankType;
            this.refreshRate = refreshRate;
            this.totalDurationNanos = totalDurationNanos;
            this.isFirstFrame = isFirstFrame;
        }

        public String toString() {
            StringBuilder str = new StringBuilder();
            switch (this.jankType) {
                case 0:
                    str.append("JANK_NONE");
                    break;
                case 1:
                    str.append("DISPLAY_HAL");
                    break;
                case 2:
                    str.append("JANK_SURFACEFLINGER_DEADLINE_MISSED");
                    break;
                case 4:
                    str.append("JANK_SURFACEFLINGER_GPU_DEADLINE_MISSED");
                    break;
                case 8:
                    str.append("JANK_APP_DEADLINE_MISSED");
                    break;
                case 16:
                    str.append("PREDICTION_ERROR");
                    break;
                case 32:
                    str.append("SURFACE_FLINGER_SCHEDULING");
                    break;
                default:
                    str.append("UNKNOWN: ").append(this.jankType);
                    break;
            }
            str.append(", ").append(this.frameVsyncId);
            str.append(", ").append(this.totalDurationNanos);
            return str.toString();
        }
    }

    public FrameTracker(InteractionJankMonitor.Configuration config, ThreadedRendererWrapper renderer, ViewRootWrapper viewRootWrapper, SurfaceControlWrapper surfaceControlWrapper, ChoreographerWrapper choreographer, FrameMetricsWrapper metrics, StatsLogWrapper statsLog, int traceThresholdMissedFrames, int traceThresholdFrameTimeMillis, FrameTrackerListener listener) {
        HardwareRendererObserver hardwareRendererObserver;
        this.mSurfaceOnly = config.isSurfaceOnly();
        this.mConfig = config;
        this.mHandler = config.getHandler();
        this.mChoreographer = choreographer;
        this.mSurfaceControlWrapper = surfaceControlWrapper;
        this.mStatsLog = statsLog;
        this.mDeferMonitoring = config.shouldDeferMonitor();
        this.mRendererWrapper = this.mSurfaceOnly ? null : renderer;
        this.mMetricsWrapper = this.mSurfaceOnly ? null : metrics;
        this.mViewRoot = this.mSurfaceOnly ? null : viewRootWrapper;
        if (this.mSurfaceOnly) {
            hardwareRendererObserver = null;
        } else {
            hardwareRendererObserver = new HardwareRendererObserver(this, this.mMetricsWrapper.getTiming(), this.mHandler, false);
        }
        this.mObserver = hardwareRendererObserver;
        this.mTraceThresholdMissedFrames = traceThresholdMissedFrames;
        this.mTraceThresholdFrameTimeMillis = traceThresholdFrameTimeMillis;
        this.mListener = listener;
        this.mDisplayId = config.getDisplayId();
        if (this.mSurfaceOnly) {
            this.mSurfaceControl = config.getSurfaceControl();
            this.mSurfaceChangedCallback = null;
        } else {
            if (this.mViewRoot.getSurfaceControl().isValid()) {
                this.mSurfaceControl = this.mViewRoot.getSurfaceControl();
            }
            this.mSurfaceChangedCallback = new AnonymousClass1();
            this.mViewRoot.addSurfaceChangedCallback(this.mSurfaceChangedCallback);
        }
    }

    /* renamed from: com.android.internal.jank.FrameTracker$1, reason: invalid class name */
    class AnonymousClass1 implements ViewRootImpl.SurfaceChangedCallback {
        AnonymousClass1() {
        }

        @Override // android.view.ViewRootImpl.SurfaceChangedCallback
        public void surfaceCreated(SurfaceControl.Transaction t) {
            FrameTracker.this.mHandler.runWithScissors(new Runnable() { // from class: com.android.internal.jank.FrameTracker$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FrameTracker.AnonymousClass1.this.lambda$surfaceCreated$0();
                }
            }, 500L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$surfaceCreated$0() {
            if (FrameTracker.this.mSurfaceControl == null) {
                FrameTracker.this.mSurfaceControl = FrameTracker.this.mViewRoot.getSurfaceControl();
                if (FrameTracker.this.mBeginVsyncId != -1) {
                    FrameTracker.this.begin();
                }
            }
        }

        @Override // android.view.ViewRootImpl.SurfaceChangedCallback
        public void surfaceReplaced(SurfaceControl.Transaction t) {
        }

        @Override // android.view.ViewRootImpl.SurfaceChangedCallback
        public void surfaceDestroyed() {
            FrameTracker.this.mHandler.postDelayed(new Runnable() { // from class: com.android.internal.jank.FrameTracker$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    FrameTracker.AnonymousClass1.this.lambda$surfaceDestroyed$1();
                }
            }, 50L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$surfaceDestroyed$1() {
            if (!FrameTracker.this.mMetricsFinalized) {
                FrameTracker.this.end(1);
                FrameTracker.this.finish();
            }
        }
    }

    public void begin() {
        long currentVsync = this.mChoreographer.getVsyncId();
        if (this.mBeginVsyncId == -1) {
            this.mBeginVsyncId = this.mDeferMonitoring ? 1 + currentVsync : currentVsync;
        }
        if (this.mSurfaceControl != null) {
            if (this.mDeferMonitoring && currentVsync < this.mBeginVsyncId) {
                markEvent("FT#deferMonitoring", 0L);
                postTraceStartMarker(new Runnable() { // from class: com.android.internal.jank.FrameTracker$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        FrameTracker.this.beginInternal();
                    }
                });
            } else {
                beginInternal();
            }
        }
    }

    public void postTraceStartMarker(Runnable action) {
        this.mChoreographer.mChoreographer.postCallback(0, action, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void beginInternal() {
        if (this.mCancelled || this.mEndVsyncId != -1) {
            return;
        }
        this.mTracingStarted = true;
        String name = this.mConfig.getSessionName();
        Trace.asyncTraceForTrackBegin(4096L, name, name, (int) this.mBeginVsyncId);
        markEvent("FT#beginVsync", this.mBeginVsyncId);
        markEvent("FT#layerId", this.mSurfaceControl.getLayerId());
        this.mSurfaceControlWrapper.addJankStatsListener(this, this.mSurfaceControl);
        if (!this.mSurfaceOnly) {
            this.mRendererWrapper.addObserver(this.mObserver);
        }
    }

    public boolean end(int reason) {
        if (this.mCancelled || this.mEndVsyncId != -1) {
            return false;
        }
        this.mEndVsyncId = this.mChoreographer.getVsyncId();
        if (this.mBeginVsyncId == -1) {
            return cancel(17);
        }
        if (this.mEndVsyncId <= this.mBeginVsyncId) {
            return cancel(18);
        }
        String name = this.mConfig.getSessionName();
        markEvent("FT#end", reason);
        markEvent("FT#endVsync", this.mEndVsyncId);
        Trace.asyncTraceForTrackEnd(4096L, name, (int) this.mBeginVsyncId);
        this.mWaitForFinishTimedOut = new AnonymousClass2(name);
        this.mHandler.postDelayed(this.mWaitForFinishTimedOut, 60L);
        notifyCujEvent(InteractionJankMonitor.ACTION_SESSION_END, reason);
        return true;
    }

    /* renamed from: com.android.internal.jank.FrameTracker$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {
        private int mFlushAttempts = 0;
        final /* synthetic */ String val$name;

        AnonymousClass2(String str) {
            this.val$name = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            long delay;
            if (FrameTracker.this.mWaitForFinishTimedOut == null || FrameTracker.this.mMetricsFinalized) {
                return;
            }
            if (FrameTracker.this.mSurfaceControl != null && FrameTracker.this.mSurfaceControl.isValid()) {
                SurfaceControl.Transaction.sendSurfaceFlushJankData(FrameTracker.this.mSurfaceControl);
            }
            if (this.mFlushAttempts < 3) {
                delay = 60;
                this.mFlushAttempts++;
            } else {
                FrameTracker frameTracker = FrameTracker.this;
                final String str = this.val$name;
                frameTracker.mWaitForFinishTimedOut = new Runnable() { // from class: com.android.internal.jank.FrameTracker$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        FrameTracker.AnonymousClass2.this.lambda$run$0(str);
                    }
                };
                delay = TimeUnit.SECONDS.toMillis(10L);
            }
            FrameTracker.this.mHandler.postDelayed(FrameTracker.this.mWaitForFinishTimedOut, delay);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(String name) {
            Log.e(FrameTracker.TAG, "force finish cuj, time out: " + name);
            FrameTracker.this.finish();
        }
    }

    public boolean cancel(int reason) {
        boolean cancelFromEnd = reason == 17 || reason == 18;
        if (this.mCancelled || !(this.mEndVsyncId == -1 || cancelFromEnd)) {
            return false;
        }
        this.mCancelled = true;
        markEvent("FT#cancel", reason);
        if (this.mTracingStarted) {
            Trace.asyncTraceForTrackEnd(4096L, this.mConfig.getSessionName(), (int) this.mBeginVsyncId);
        }
        removeObservers();
        notifyCujEvent(InteractionJankMonitor.ACTION_SESSION_CANCEL, reason);
        return true;
    }

    private void markEvent(String eventName, long eventValue) {
        if (Trace.isTagEnabled(4096L)) {
            String event = TextUtils.formatSimple("%s#%s", eventName, Long.valueOf(eventValue));
            if (event.length() <= 127) {
                Trace.instantForTrack(4096L, this.mConfig.getSessionName(), event);
                return;
            }
            throw new IllegalArgumentException(TextUtils.formatSimple("The length of the trace event description <%s> exceeds %d", event, 127));
        }
    }

    private void notifyCujEvent(String action, int reason) {
        if (this.mListener == null) {
            return;
        }
        this.mListener.onCujEvents(this, action, reason);
    }

    @Override // android.view.SurfaceControl.OnJankDataListener
    public void onJankDataAvailable(final SurfaceControl.JankData[] jankData) {
        postCallback(new Runnable() { // from class: com.android.internal.jank.FrameTracker$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                FrameTracker.this.lambda$onJankDataAvailable$0(jankData);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onJankDataAvailable$0(SurfaceControl.JankData[] jankData) {
        if (this.mCancelled || this.mMetricsFinalized) {
            return;
        }
        for (SurfaceControl.JankData jankStat : jankData) {
            if (isInRange(jankStat.frameVsyncId)) {
                int refreshRate = DisplayRefreshRate.getRefreshRate(jankStat.frameIntervalNs);
                JankInfo info = findJankInfo(jankStat.frameVsyncId);
                if (info != null) {
                    info.surfaceControlCallbackFired = true;
                    info.jankType = jankStat.jankType;
                    info.refreshRate = refreshRate;
                } else {
                    this.mJankInfos.put((int) jankStat.frameVsyncId, JankInfo.createFromSurfaceControlCallback(jankStat.frameVsyncId, jankStat.jankType, refreshRate));
                }
            }
        }
        processJankInfos();
    }

    public void postCallback(Runnable callback) {
        this.mHandler.post(callback);
    }

    private JankInfo findJankInfo(long frameVsyncId) {
        return this.mJankInfos.get((int) frameVsyncId);
    }

    private boolean isInRange(long vsyncId) {
        return vsyncId >= this.mBeginVsyncId;
    }

    @Override // android.graphics.HardwareRendererObserver.OnFrameMetricsAvailableListener
    public void onFrameMetricsAvailable(int dropCountSinceLastInvocation) {
        postCallback(new Runnable() { // from class: com.android.internal.jank.FrameTracker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FrameTracker.this.lambda$onFrameMetricsAvailable$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFrameMetricsAvailable$1() {
        if (this.mCancelled || this.mMetricsFinalized) {
            return;
        }
        long totalDurationNanos = this.mMetricsWrapper.getMetric(8);
        boolean isFirstFrame = this.mMetricsWrapper.getMetric(9) == 1;
        long frameVsyncId = this.mMetricsWrapper.getTiming()[1];
        if (!isInRange(frameVsyncId)) {
            return;
        }
        JankInfo info = findJankInfo(frameVsyncId);
        if (info != null) {
            info.hwuiCallbackFired = true;
            info.totalDurationNanos = totalDurationNanos;
            info.isFirstFrame = isFirstFrame;
        } else {
            this.mJankInfos.put((int) frameVsyncId, JankInfo.createFromHwuiCallback(frameVsyncId, totalDurationNanos, isFirstFrame));
        }
        processJankInfos();
    }

    private boolean hasReceivedCallbacksAfterEnd() {
        if (this.mEndVsyncId == -1) {
            return false;
        }
        JankInfo last = this.mJankInfos.size() == 0 ? null : this.mJankInfos.valueAt(this.mJankInfos.size() - 1);
        if (last == null || last.frameVsyncId < this.mEndVsyncId) {
            return false;
        }
        for (int i = this.mJankInfos.size() - 1; i >= 0; i--) {
            JankInfo info = this.mJankInfos.valueAt(i);
            if (info.frameVsyncId >= this.mEndVsyncId && callbacksReceived(info)) {
                return true;
            }
        }
        return false;
    }

    private void processJankInfos() {
        if (this.mMetricsFinalized || !hasReceivedCallbacksAfterEnd()) {
            return;
        }
        finish();
    }

    private boolean callbacksReceived(JankInfo info) {
        if (this.mSurfaceOnly) {
            return info.surfaceControlCallbackFired;
        }
        return info.hwuiCallbackFired && info.surfaceControlCallbackFired;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finish() {
        int missedSfFramesCount;
        String name;
        int refreshRate;
        long maxFrameTimeNanos;
        int missedAppFramesCount;
        int missedFramesCount;
        long maxFrameTimeNanos2;
        int maxSuccessiveMissedFramesCount;
        String name2;
        int missedSfFramesCount2;
        String name3;
        boolean z;
        long maxFrameTimeNanos3;
        int totalFramesCount;
        boolean missedFrame;
        int missedFramesCount2;
        if (this.mMetricsFinalized || this.mCancelled) {
            return;
        }
        boolean z2 = true;
        this.mMetricsFinalized = true;
        this.mHandler.removeCallbacks(this.mWaitForFinishTimedOut);
        this.mWaitForFinishTimedOut = null;
        markEvent("FT#finish", this.mJankInfos.size());
        removeObservers();
        String name4 = this.mConfig.getSessionName();
        int totalFramesCount2 = 0;
        int maxSuccessiveMissedFramesCount2 = 0;
        long maxFrameTimeNanos4 = 0;
        int missedAppFramesCount2 = 0;
        int i = 0;
        int missedAppFramesCount3 = 0;
        int successiveMissedFramesCount = 0;
        int successiveMissedFramesCount2 = 0;
        int refreshRate2 = 0;
        while (true) {
            if (i >= this.mJankInfos.size()) {
                missedSfFramesCount = refreshRate2;
                name = name4;
                refreshRate = successiveMissedFramesCount2;
                maxFrameTimeNanos = maxFrameTimeNanos4;
                break;
            }
            JankInfo info = this.mJankInfos.valueAt(i);
            boolean isFirstDrawn = (this.mSurfaceOnly || !info.isFirstFrame) ? false : z2;
            if (isFirstDrawn) {
                z = z2;
                maxFrameTimeNanos3 = maxFrameTimeNanos4;
                name3 = name4;
            } else {
                String name5 = name4;
                long j = info.frameVsyncId;
                long maxFrameTimeNanos5 = maxFrameTimeNanos4;
                long maxFrameTimeNanos6 = this.mEndVsyncId;
                if (j > maxFrameTimeNanos6) {
                    refreshRate = successiveMissedFramesCount2;
                    missedSfFramesCount = refreshRate2;
                    name = name5;
                    maxFrameTimeNanos = maxFrameTimeNanos5;
                    break;
                }
                if (info.surfaceControlCallbackFired) {
                    int totalFramesCount3 = totalFramesCount2 + 1;
                    boolean missedFrame2 = false;
                    if ((info.jankType & 8) != 0) {
                        name3 = name5;
                        Log.w(TAG, "Missed App frame:" + info + ", CUJ=" + name3);
                        missedAppFramesCount2++;
                        missedFrame2 = true;
                    } else {
                        name3 = name5;
                    }
                    boolean missedFrame3 = missedFrame2;
                    z = true;
                    if ((info.jankType & 1) == 0 && (info.jankType & 2) == 0 && (info.jankType & 4) == 0 && (info.jankType & 32) == 0 && (info.jankType & 16) == 0) {
                        totalFramesCount = totalFramesCount3;
                        missedFrame = missedFrame3;
                    } else {
                        totalFramesCount = totalFramesCount3;
                        Log.w(TAG, "Missed SF frame:" + info + ", CUJ=" + name3);
                        refreshRate2++;
                        missedFrame = true;
                    }
                    if (missedFrame) {
                        missedAppFramesCount3++;
                        successiveMissedFramesCount++;
                    } else {
                        int maxSuccessiveMissedFramesCount3 = Math.max(maxSuccessiveMissedFramesCount2, successiveMissedFramesCount);
                        successiveMissedFramesCount = 0;
                        maxSuccessiveMissedFramesCount2 = maxSuccessiveMissedFramesCount3;
                    }
                    int maxSuccessiveMissedFramesCount4 = info.refreshRate;
                    if (maxSuccessiveMissedFramesCount4 != 0 && info.refreshRate != successiveMissedFramesCount2) {
                        successiveMissedFramesCount2 = successiveMissedFramesCount2 == 0 ? info.refreshRate : 1;
                    }
                    if (!this.mSurfaceOnly && !info.hwuiCallbackFired) {
                        missedFramesCount2 = missedAppFramesCount3;
                        markEvent("FT#MissedHWUICallback", info.frameVsyncId);
                        Log.w(TAG, "Missing HWUI jank callback for vsyncId: " + info.frameVsyncId + ", CUJ=" + name3);
                    } else {
                        missedFramesCount2 = missedAppFramesCount3;
                    }
                    totalFramesCount2 = totalFramesCount;
                    missedAppFramesCount3 = missedFramesCount2;
                } else {
                    name3 = name5;
                    z = true;
                }
                if (this.mSurfaceOnly || !info.hwuiCallbackFired) {
                    int missedFramesCount3 = missedAppFramesCount3;
                    int refreshRate3 = successiveMissedFramesCount2;
                    int missedSfFramesCount3 = refreshRate2;
                    missedAppFramesCount3 = missedFramesCount3;
                    maxFrameTimeNanos3 = maxFrameTimeNanos5;
                    successiveMissedFramesCount2 = refreshRate3;
                    refreshRate2 = missedSfFramesCount3;
                } else {
                    int missedFramesCount4 = missedAppFramesCount3;
                    int refreshRate4 = successiveMissedFramesCount2;
                    int missedSfFramesCount4 = refreshRate2;
                    long maxFrameTimeNanos7 = Math.max(info.totalDurationNanos, maxFrameTimeNanos5);
                    if (!info.surfaceControlCallbackFired) {
                        maxFrameTimeNanos3 = maxFrameTimeNanos7;
                        long maxFrameTimeNanos8 = info.frameVsyncId;
                        markEvent("FT#MissedSFCallback", maxFrameTimeNanos8);
                        Log.w(TAG, "Missing SF jank callback for vsyncId: " + info.frameVsyncId + ", CUJ=" + name3);
                    } else {
                        maxFrameTimeNanos3 = maxFrameTimeNanos7;
                    }
                    missedAppFramesCount3 = missedFramesCount4;
                    successiveMissedFramesCount2 = refreshRate4;
                    refreshRate2 = missedSfFramesCount4;
                }
            }
            i++;
            name4 = name3;
            z2 = z;
            maxFrameTimeNanos4 = maxFrameTimeNanos3;
        }
        int maxSuccessiveMissedFramesCount5 = Math.max(maxSuccessiveMissedFramesCount2, successiveMissedFramesCount);
        Trace.traceCounter(4096L, name + "#missedFrames", missedAppFramesCount3);
        Trace.traceCounter(4096L, name + "#missedAppFrames", missedAppFramesCount2);
        Trace.traceCounter(4096L, name + "#missedSfFrames", missedSfFramesCount);
        Trace.traceCounter(4096L, name + "#totalFrames", totalFramesCount2);
        int missedSfFramesCount5 = missedSfFramesCount;
        int refreshRate5 = refreshRate;
        Trace.traceCounter(4096L, name + "#maxFrameTimeMillis", (int) (maxFrameTimeNanos / 1000000));
        Trace.traceCounter(4096L, name + "#maxSuccessiveMissedFrames", maxSuccessiveMissedFramesCount5);
        if (this.mListener != null && shouldTriggerPerfetto(missedAppFramesCount3, (int) maxFrameTimeNanos)) {
            this.mListener.triggerPerfetto(this.mConfig);
        }
        if (!this.mConfig.logToStatsd()) {
            missedAppFramesCount = missedAppFramesCount2;
            missedFramesCount = missedAppFramesCount3;
            maxFrameTimeNanos2 = maxFrameTimeNanos;
            maxSuccessiveMissedFramesCount = maxSuccessiveMissedFramesCount5;
            name2 = name;
            missedSfFramesCount2 = missedSfFramesCount5;
        } else {
            maxFrameTimeNanos2 = maxFrameTimeNanos;
            missedSfFramesCount2 = missedSfFramesCount5;
            missedAppFramesCount = missedAppFramesCount2;
            int missedFramesCount5 = missedAppFramesCount3;
            missedFramesCount = missedFramesCount5;
            maxSuccessiveMissedFramesCount = maxSuccessiveMissedFramesCount5;
            name2 = name;
            this.mStatsLog.write(305, this.mDisplayId, refreshRate5, this.mConfig.getStatsdInteractionType(), totalFramesCount2, missedAppFramesCount3, maxFrameTimeNanos2, missedSfFramesCount2, missedAppFramesCount2, maxSuccessiveMissedFramesCount5);
        }
        try {
            StringBuilder LogStr = new StringBuilder();
            try {
                try {
                    LogStr.append("CUJ=").append(name2).append("/").append(totalFramesCount2).append("/").append(missedAppFramesCount).append("/").append(missedSfFramesCount2).append("/").append(missedFramesCount).append("/").append(maxFrameTimeNanos2 / 1000000).append("/").append(maxSuccessiveMissedFramesCount);
                    PerfLog.d(27, LogStr.toString());
                } catch (Exception e) {
                    e = e;
                    e.printStackTrace();
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    ThreadedRendererWrapper getThreadedRenderer() {
        return this.mRendererWrapper;
    }

    ViewRootWrapper getViewRoot() {
        return this.mViewRoot;
    }

    private boolean shouldTriggerPerfetto(int missedFramesCount, int maxFrameTimeNanos) {
        boolean overMissedFramesThreshold = this.mTraceThresholdMissedFrames != -1 && missedFramesCount >= this.mTraceThresholdMissedFrames;
        boolean overFrameTimeThreshold = (this.mSurfaceOnly || this.mTraceThresholdFrameTimeMillis == -1 || maxFrameTimeNanos < this.mTraceThresholdFrameTimeMillis * 1000000) ? false : true;
        return overMissedFramesThreshold || overFrameTimeThreshold;
    }

    public void removeObservers() {
        this.mSurfaceControlWrapper.removeJankStatsListener(this);
        if (!this.mSurfaceOnly) {
            this.mRendererWrapper.removeObserver(this.mObserver);
            if (this.mSurfaceChangedCallback != null) {
                this.mViewRoot.removeSurfaceChangedCallback(this.mSurfaceChangedCallback);
            }
        }
    }

    public static class FrameMetricsWrapper {
        private final FrameMetrics mFrameMetrics = new FrameMetrics();

        public long[] getTiming() {
            return this.mFrameMetrics.mTimingData;
        }

        public long getMetric(int index) {
            return this.mFrameMetrics.getMetric(index);
        }
    }

    public static class ThreadedRendererWrapper {
        private final ThreadedRenderer mRenderer;

        public ThreadedRendererWrapper(ThreadedRenderer renderer) {
            this.mRenderer = renderer;
        }

        public void addObserver(HardwareRendererObserver observer) {
            if (CoreRune.GFW_DEBUG_DISABLE_HWRENDERING) {
                return;
            }
            this.mRenderer.addObserver(observer);
        }

        public void removeObserver(HardwareRendererObserver observer) {
            if (CoreRune.GFW_DEBUG_DISABLE_HWRENDERING) {
                return;
            }
            this.mRenderer.removeObserver(observer);
        }
    }

    public static class ViewRootWrapper {
        private final ViewRootImpl mViewRoot;

        public ViewRootWrapper(ViewRootImpl viewRoot) {
            this.mViewRoot = viewRoot;
        }

        public void addSurfaceChangedCallback(ViewRootImpl.SurfaceChangedCallback callback) {
            this.mViewRoot.addSurfaceChangedCallback(callback);
        }

        public void removeSurfaceChangedCallback(ViewRootImpl.SurfaceChangedCallback callback) {
            this.mViewRoot.removeSurfaceChangedCallback(callback);
        }

        public SurfaceControl getSurfaceControl() {
            return this.mViewRoot.getSurfaceControl();
        }

        void requestInvalidateRootRenderNode() {
            this.mViewRoot.requestInvalidateRootRenderNode();
        }

        void addWindowCallbacks(WindowCallbacks windowCallbacks) {
            this.mViewRoot.addWindowCallbacks(windowCallbacks);
        }

        void removeWindowCallbacks(WindowCallbacks windowCallbacks) {
            this.mViewRoot.removeWindowCallbacks(windowCallbacks);
        }

        View getView() {
            return this.mViewRoot.getView();
        }

        int dipToPx(int dip) {
            DisplayMetrics displayMetrics = this.mViewRoot.mContext.getResources().getDisplayMetrics();
            return (int) ((displayMetrics.density * dip) + 0.5f);
        }
    }

    public static class SurfaceControlWrapper {
        public void addJankStatsListener(SurfaceControl.OnJankDataListener listener, SurfaceControl surfaceControl) {
            SurfaceControl.addJankDataListener(listener, surfaceControl);
        }

        public void removeJankStatsListener(SurfaceControl.OnJankDataListener listener) {
            SurfaceControl.removeJankDataListener(listener);
        }
    }

    public static class ChoreographerWrapper {
        private final Choreographer mChoreographer;

        public ChoreographerWrapper(Choreographer choreographer) {
            this.mChoreographer = choreographer;
        }

        public long getVsyncId() {
            return this.mChoreographer.getVsyncId();
        }
    }

    public static class StatsLogWrapper {
        private final DisplayResolutionTracker mDisplayResolutionTracker;

        public StatsLogWrapper(DisplayResolutionTracker displayResolutionTracker) {
            this.mDisplayResolutionTracker = displayResolutionTracker;
        }

        public void write(int code, int displayId, int refreshRate, int arg1, long arg2, long arg3, long arg4, long arg5, long arg6, long arg7) {
            FrameworkStatsLog.write(code, arg1, arg2, arg3, arg4, arg5, arg6, arg7, this.mDisplayResolutionTracker.getResolution(displayId), refreshRate);
        }
    }
}
