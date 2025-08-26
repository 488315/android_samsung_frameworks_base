package com.android.internal.jank;

import android.animation.AnimationHandler;
import android.graphics.HardwareRendererObserver;
import android.os.Handler;
import android.os.Trace;
import android.text.TextUtils;
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
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.util.FrameworkStatsLog;
import com.samsung.android.rune.CoreRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public class FrameTracker implements HardwareRendererObserver.OnFrameMetricsAvailableListener, SurfaceControl.OnJankDataListener {
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
    private SurfaceControl.OnJankDataListenerRegistration mJankDataListenerRegistration;
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
        final long frameVsyncId;
        boolean hwuiCallbackFired = false;
        boolean surfaceControlCallbackFired = false;
        int jankType = 0;
        int refreshRate = 0;
        long totalDurationNanos = 0;
        boolean isFirstFrame = false;

        static JankInfo createFromHwuiCallback(long j, long j2, boolean z) {
            return new JankInfo(j).update(j2, z);
        }

        static JankInfo createFromSurfaceControlCallback(SurfaceControl.JankData jankData) {
            return new JankInfo(jankData.getVsyncId()).update(jankData);
        }

        private JankInfo(long j) {
            this.frameVsyncId = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public JankInfo update(SurfaceControl.JankData jankData) {
            this.surfaceControlCallbackFired = true;
            this.jankType = jankData.getJankType();
            this.refreshRate = DisplayRefreshRate.getRefreshRate(jankData.getFrameIntervalNanos());
            if (Flags.useSfFrameDuration()) {
                this.totalDurationNanos = jankData.getActualAppFrameTimeNanos();
            }
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public JankInfo update(long j, boolean z) {
            this.hwuiCallbackFired = true;
            if (!Flags.useSfFrameDuration()) {
                this.totalDurationNanos = j;
            }
            this.isFirstFrame = z;
            return this;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            int i = this.jankType;
            if (i == 0) {
                sb.append("JANK_NONE");
            } else if (i == 1) {
                sb.append("JANK_COMPOSER");
            } else if (i == 2) {
                sb.append("JANK_APPLICATION");
            } else {
                sb.append("UNKNOWN: ");
                sb.append(this.jankType);
            }
            sb.append(", ");
            sb.append(this.frameVsyncId);
            sb.append(", ");
            sb.append(this.totalDurationNanos);
            return sb.toString();
        }
    }

    public FrameTracker(InteractionJankMonitor.Configuration configuration, ThreadedRendererWrapper threadedRendererWrapper, ViewRootWrapper viewRootWrapper, SurfaceControlWrapper surfaceControlWrapper, ChoreographerWrapper choreographerWrapper, FrameMetricsWrapper frameMetricsWrapper, StatsLogWrapper statsLogWrapper, int i, int i2, FrameTrackerListener frameTrackerListener) {
        boolean zIsSurfaceOnly = configuration.isSurfaceOnly();
        this.mSurfaceOnly = zIsSurfaceOnly;
        this.mConfig = configuration;
        Handler handler = configuration.getHandler();
        this.mHandler = handler;
        this.mChoreographer = choreographerWrapper;
        this.mSurfaceControlWrapper = surfaceControlWrapper;
        this.mStatsLog = statsLogWrapper;
        this.mDeferMonitoring = configuration.shouldDeferMonitor();
        this.mRendererWrapper = zIsSurfaceOnly ? null : threadedRendererWrapper;
        frameMetricsWrapper = zIsSurfaceOnly ? null : frameMetricsWrapper;
        this.mMetricsWrapper = frameMetricsWrapper;
        viewRootWrapper = zIsSurfaceOnly ? null : viewRootWrapper;
        this.mViewRoot = viewRootWrapper;
        this.mObserver = (zIsSurfaceOnly || (Flags.useSfFrameDuration() && Flags.ignoreHwuiIsFirstFrame())) ? null : new HardwareRendererObserver(this, frameMetricsWrapper.getTiming(), handler, false);
        this.mTraceThresholdMissedFrames = i;
        this.mTraceThresholdFrameTimeMillis = i2;
        this.mListener = frameTrackerListener;
        this.mDisplayId = configuration.getDisplayId();
        if (zIsSurfaceOnly) {
            this.mSurfaceControl = configuration.getSurfaceControl();
            this.mSurfaceChangedCallback = null;
            return;
        }
        if (viewRootWrapper.getSurfaceControl().isValid()) {
            this.mSurfaceControl = viewRootWrapper.getSurfaceControl();
        }
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mSurfaceChangedCallback = anonymousClass1;
        viewRootWrapper.addSurfaceChangedCallback(anonymousClass1);
    }

    /* renamed from: com.android.internal.jank.FrameTracker$1, reason: invalid class name */
    class AnonymousClass1 implements ViewRootImpl.SurfaceChangedCallback {
        @Override // android.view.ViewRootImpl.SurfaceChangedCallback
        public void surfaceReplaced(SurfaceControl.Transaction transaction) {
        }

        AnonymousClass1() {
        }

        @Override // android.view.ViewRootImpl.SurfaceChangedCallback
        public void surfaceCreated(SurfaceControl.Transaction transaction) {
            Trace.beginSection("FrameTracker#surfaceCreated");
            FrameTracker.this.mHandler.runWithScissors(new Runnable() { // from class: com.android.internal.jank.FrameTracker$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$surfaceCreated$0();
                }
            }, 500L);
            Trace.endSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$surfaceCreated$0() {
            if (FrameTracker.this.mSurfaceControl == null) {
                FrameTracker frameTracker = FrameTracker.this;
                frameTracker.mSurfaceControl = frameTracker.mViewRoot.getSurfaceControl();
                if (FrameTracker.this.mBeginVsyncId != -1) {
                    FrameTracker.this.begin();
                }
            }
        }

        @Override // android.view.ViewRootImpl.SurfaceChangedCallback
        public void surfaceDestroyed() {
            FrameTracker.this.mHandler.post(new Runnable() { // from class: com.android.internal.jank.FrameTracker$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$surfaceDestroyed$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$surfaceDestroyed$1() {
            if (FrameTracker.this.mMetricsFinalized) {
                return;
            }
            FrameTracker.this.end(1);
        }
    }

    public void begin() {
        long vsyncId = this.mChoreographer.getVsyncId();
        if (this.mBeginVsyncId == -1) {
            this.mBeginVsyncId = this.mDeferMonitoring ? 1 + vsyncId : vsyncId;
        }
        if (this.mSurfaceControl != null) {
            if (this.mDeferMonitoring && vsyncId < this.mBeginVsyncId) {
                markEvent("FT#deferMonitoring", 0L);
                postTraceStartMarker(new Runnable() { // from class: com.android.internal.jank.FrameTracker$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.beginInternal();
                    }
                });
            } else {
                beginInternal();
            }
        }
    }

    public void postTraceStartMarker(Runnable runnable) {
        this.mChoreographer.mChoreographer.postCallback(0, runnable, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void beginInternal() {
        if (this.mCancelled || this.mEndVsyncId != -1) {
            return;
        }
        this.mTracingStarted = true;
        String sessionName = this.mConfig.getSessionName();
        Trace.asyncTraceForTrackBegin(4096L, sessionName, sessionName, (int) this.mBeginVsyncId);
        markEvent("FT#beginVsync", this.mBeginVsyncId);
        markEvent("FT#layerId", this.mSurfaceControl.getLayerId());
        markCujUiThread();
        this.mJankDataListenerRegistration = this.mSurfaceControlWrapper.addJankStatsListener(this, this.mSurfaceControl);
        if (this.mSurfaceOnly) {
            return;
        }
        this.mRendererWrapper.addObserver(this.mObserver);
    }

    public boolean end(int i) {
        if (this.mCancelled || this.mEndVsyncId != -1) {
            return false;
        }
        long lastAnimationFrameVsyncId = AnimationHandler.getInstance().getLastAnimationFrameVsyncId(this.mChoreographer.getVsyncId());
        this.mEndVsyncId = lastAnimationFrameVsyncId;
        long j = this.mBeginVsyncId;
        if (j == -1) {
            return cancel(17);
        }
        if (lastAnimationFrameVsyncId <= j) {
            return cancel(18);
        }
        String sessionName = this.mConfig.getSessionName();
        markEvent("FT#end", i);
        markEvent("FT#endVsync", this.mEndVsyncId);
        Trace.asyncTraceForTrackEnd(4096L, sessionName, (int) this.mBeginVsyncId);
        SurfaceControl.OnJankDataListenerRegistration onJankDataListenerRegistration = this.mJankDataListenerRegistration;
        if (onJankDataListenerRegistration != null) {
            onJankDataListenerRegistration.removeAfter(this.mEndVsyncId);
        }
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(sessionName);
        this.mWaitForFinishTimedOut = anonymousClass2;
        this.mHandler.postDelayed(anonymousClass2, 60L);
        notifyCujEvent(InteractionJankMonitor.ACTION_SESSION_END, i);
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
            long millis;
            if (FrameTracker.this.mWaitForFinishTimedOut == null || FrameTracker.this.mMetricsFinalized) {
                return;
            }
            if (FrameTracker.this.mSurfaceControl != null && FrameTracker.this.mSurfaceControl.isValid()) {
                SurfaceControl.Transaction.sendSurfaceFlushJankData(FrameTracker.this.mSurfaceControl);
            }
            if (FrameTracker.this.mJankDataListenerRegistration != null) {
                FrameTracker.this.mJankDataListenerRegistration.flush();
            }
            int i = this.mFlushAttempts;
            if (i < 3) {
                this.mFlushAttempts = i + 1;
                millis = 60;
            } else {
                FrameTracker frameTracker = FrameTracker.this;
                final String str = this.val$name;
                frameTracker.mWaitForFinishTimedOut = new Runnable() { // from class: com.android.internal.jank.FrameTracker$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$run$0(str);
                    }
                };
                millis = TimeUnit.SECONDS.toMillis(10L);
            }
            FrameTracker.this.mHandler.postDelayed(FrameTracker.this.mWaitForFinishTimedOut, millis);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(String str) {
            Log.e(FrameTracker.TAG, "force finish cuj, time out: " + str);
            FrameTracker.this.finish();
        }
    }

    public boolean cancel(int i) {
        boolean z = i == 17 || i == 18;
        if (this.mCancelled || !(this.mEndVsyncId == -1 || z)) {
            return false;
        }
        this.mCancelled = true;
        markEvent("FT#cancel", i);
        if (this.mTracingStarted) {
            Trace.asyncTraceForTrackEnd(4096L, this.mConfig.getSessionName(), (int) this.mBeginVsyncId);
        }
        removeObservers();
        notifyCujEvent(InteractionJankMonitor.ACTION_SESSION_CANCEL, i);
        return true;
    }

    private void markEvent(String str, long j) {
        if (Trace.isTagEnabled(4096L)) {
            String simple = TextUtils.formatSimple("%s#%s", str, Long.valueOf(j));
            if (simple.length() > 127) {
                throw new IllegalArgumentException(TextUtils.formatSimple("The length of the trace event description <%s> exceeds %d", simple, 127));
            }
            Trace.instantForTrack(4096L, this.mConfig.getSessionName(), simple);
        }
    }

    private void markCujUiThread() {
        if (Trace.isTagEnabled(4096L)) {
            Trace.instant(4096L, this.mConfig.getSessionName() + "#UIThread");
        }
    }

    private void notifyCujEvent(String str, int i) {
        FrameTrackerListener frameTrackerListener = this.mListener;
        if (frameTrackerListener == null) {
            return;
        }
        frameTrackerListener.onCujEvents(this, str, i);
    }

    @Override // android.view.SurfaceControl.OnJankDataListener
    public void onJankDataAvailable(final List<SurfaceControl.JankData> list) {
        postCallback(new Runnable() { // from class: com.android.internal.jank.FrameTracker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onJankDataAvailable$0(list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onJankDataAvailable$0(List list) {
        try {
            Trace.beginSection("FrameTracker#onJankDataAvailable");
            if (!this.mCancelled && !this.mMetricsFinalized) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    SurfaceControl.JankData jankData = (SurfaceControl.JankData) it.next();
                    if (isInRange(jankData.getVsyncId())) {
                        JankInfo jankInfoFindJankInfo = findJankInfo(jankData.getVsyncId());
                        if (jankInfoFindJankInfo != null) {
                            jankInfoFindJankInfo.update(jankData);
                        } else {
                            this.mJankInfos.put((int) jankData.getVsyncId(), JankInfo.createFromSurfaceControlCallback(jankData));
                        }
                    }
                }
                processJankInfos();
            }
        } finally {
            Trace.endSection();
        }
    }

    public void postCallback(Runnable runnable) {
        this.mHandler.post(runnable);
    }

    private JankInfo findJankInfo(long j) {
        return this.mJankInfos.get((int) j);
    }

    private boolean isInRange(long j) {
        return j >= this.mBeginVsyncId;
    }

    @Override // android.graphics.HardwareRendererObserver.OnFrameMetricsAvailableListener
    public void onFrameMetricsAvailable(int i) {
        postCallback(new Runnable() { // from class: com.android.internal.jank.FrameTracker$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onFrameMetricsAvailable$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFrameMetricsAvailable$1() {
        try {
            Trace.beginSection("FrameTracker#onFrameMetricsAvailable");
            if (!this.mCancelled && !this.mMetricsFinalized) {
                long metric = this.mMetricsWrapper.getMetric(8);
                boolean z = this.mMetricsWrapper.getMetric(9) == 1;
                long j = this.mMetricsWrapper.getTiming()[1];
                if (isInRange(j)) {
                    JankInfo jankInfoFindJankInfo = findJankInfo(j);
                    if (jankInfoFindJankInfo != null) {
                        jankInfoFindJankInfo.update(metric, z);
                    } else {
                        this.mJankInfos.put((int) j, JankInfo.createFromHwuiCallback(j, metric, z));
                    }
                    processJankInfos();
                }
            }
        } finally {
            Trace.endSection();
        }
    }

    private boolean hasReceivedCallbacksAfterEnd() {
        JankInfo jankInfoValueAt;
        if (this.mEndVsyncId == -1) {
            return false;
        }
        if (this.mJankInfos.size() == 0) {
            jankInfoValueAt = null;
        } else {
            SparseArray<JankInfo> sparseArray = this.mJankInfos;
            jankInfoValueAt = sparseArray.valueAt(sparseArray.size() - 1);
        }
        if (jankInfoValueAt == null || jankInfoValueAt.frameVsyncId < this.mEndVsyncId) {
            return false;
        }
        for (int size = this.mJankInfos.size() - 1; size >= 0; size--) {
            JankInfo jankInfoValueAt2 = this.mJankInfos.valueAt(size);
            if (jankInfoValueAt2.frameVsyncId >= this.mEndVsyncId && callbacksReceived(jankInfoValueAt2)) {
                return true;
            }
        }
        return false;
    }

    private void processJankInfos() {
        if (!this.mMetricsFinalized && hasReceivedCallbacksAfterEnd()) {
            finish();
        }
    }

    private boolean callbacksReceived(JankInfo jankInfo) {
        if (this.mObserver == null) {
            return jankInfo.surfaceControlCallbackFired;
        }
        return jankInfo.hwuiCallbackFired && jankInfo.surfaceControlCallbackFired;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finish() {
        Trace.beginSection("FrameTracker#finish");
        finishTraced();
        Trace.endSection();
    }

    private void finishTraced() {
        String str;
        long j;
        String str2;
        long j2;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        String str3;
        int i6;
        long jMax;
        long j3;
        int i7;
        int i8;
        String str4 = "/";
        if (this.mMetricsFinalized || this.mCancelled) {
            return;
        }
        int i9 = 1;
        this.mMetricsFinalized = true;
        this.mHandler.removeCallbacks(this.mWaitForFinishTimedOut);
        this.mWaitForFinishTimedOut = null;
        markEvent("FT#finish", this.mJankInfos.size());
        removeObservers();
        String sessionName = this.mConfig.getSessionName();
        long j4 = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        int iMax = 0;
        int i15 = 0;
        int i16 = 0;
        while (true) {
            if (i10 >= this.mJankInfos.size()) {
                str = str4;
                j = j4;
                str2 = sessionName;
                break;
            }
            JankInfo jankInfoValueAt = this.mJankInfos.valueAt(i10);
            int i17 = i9;
            if (this.mSurfaceOnly || !jankInfoValueAt.isFirstFrame || Flags.ignoreHwuiIsFirstFrame()) {
                long j5 = j4;
                str2 = sessionName;
                if (jankInfoValueAt.frameVsyncId > this.mEndVsyncId) {
                    str = str4;
                    j = j5;
                    break;
                }
                if (jankInfoValueAt.surfaceControlCallbackFired) {
                    int i18 = i11 + 1;
                    if ((jankInfoValueAt.jankType & 2) != 0) {
                        Log.w(TAG, "Missed App frame:" + jankInfoValueAt + ", CUJ=" + str2);
                        i13++;
                        i7 = i17;
                    } else {
                        i7 = 0;
                    }
                    if ((jankInfoValueAt.jankType & 1) != 0) {
                        Log.w(TAG, "Missed SF frame:" + jankInfoValueAt + ", CUJ=" + str2);
                        i14++;
                        i7 = i17;
                    }
                    if (i7 != 0) {
                        i12++;
                        i15++;
                    } else {
                        iMax = Math.max(iMax, i15);
                        i15 = 0;
                    }
                    if (jankInfoValueAt.refreshRate != 0 && jankInfoValueAt.refreshRate != i16) {
                        i16 = i16 == 0 ? jankInfoValueAt.refreshRate : i17;
                    }
                    if (this.mObserver == null || jankInfoValueAt.hwuiCallbackFired) {
                        i5 = i10;
                        i8 = i18;
                    } else {
                        i5 = i10;
                        i8 = i18;
                        markEvent("FT#MissedHWUICallback", jankInfoValueAt.frameVsyncId);
                        Log.w(TAG, "Missing HWUI jank callback for vsyncId: " + jankInfoValueAt.frameVsyncId + ", CUJ=" + str2);
                    }
                    i11 = i8;
                } else {
                    i5 = i10;
                }
                if (!this.mSurfaceOnly && jankInfoValueAt.hwuiCallbackFired) {
                    str3 = str4;
                    i6 = i11;
                    long jMax2 = Math.max(jankInfoValueAt.totalDurationNanos, j5);
                    if (jankInfoValueAt.surfaceControlCallbackFired) {
                        j3 = jMax2;
                    } else {
                        j3 = jMax2;
                        markEvent("FT#MissedSFCallback", jankInfoValueAt.frameVsyncId);
                        Log.w(TAG, "Missing SF jank callback for vsyncId: " + jankInfoValueAt.frameVsyncId + ", CUJ=" + str2);
                    }
                    jMax = j3;
                } else {
                    str3 = str4;
                    i6 = i11;
                    jMax = j5;
                    if (Flags.useSfFrameDuration() && jankInfoValueAt.surfaceControlCallbackFired) {
                        jMax = Math.max(jankInfoValueAt.totalDurationNanos, jMax);
                    }
                }
                i11 = i6;
            } else {
                str3 = str4;
                jMax = j4;
                i5 = i10;
                str2 = sessionName;
            }
            i10 = i5 + 1;
            sessionName = str2;
            j4 = jMax;
            i9 = i17;
            str4 = str3;
        }
        int iMax2 = Math.max(iMax, i15);
        Trace.traceCounter(4096L, str2 + "#missedFrames", i12);
        Trace.traceCounter(4096L, str2 + "#missedAppFrames", i13);
        Trace.traceCounter(4096L, str2 + "#missedSfFrames", i14);
        Trace.traceCounter(4096L, str2 + "#totalFrames", i11);
        int i19 = i16;
        Trace.traceCounter(4096L, str2 + "#maxFrameTimeMillis", (int) (j / 1000000));
        Trace.traceCounter(4096L, str2 + "#maxSuccessiveMissedFrames", iMax2);
        if (this.mListener != null && shouldTriggerPerfetto(i12, (int) j)) {
            this.mListener.triggerPerfetto(this.mConfig);
        }
        if (this.mConfig.logToStatsd()) {
            long j6 = j;
            i = i11;
            i2 = i12;
            i3 = i13;
            i4 = i14;
            this.mStatsLog.write(305, this.mDisplayId, i19, this.mConfig.getStatsdInteractionType(), i11, i12, j6, i14, i13, iMax2);
            j2 = j6;
        } else {
            j2 = j;
            i = i11;
            i2 = i12;
            i3 = i13;
            i4 = i14;
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("CUJ=");
            sb.append(str2);
            String str5 = str;
            sb.append(str5);
            sb.append(i);
            sb.append(str5);
            sb.append(i3);
            sb.append(str5);
            sb.append(i4);
            sb.append(str5);
            sb.append(i2);
            sb.append(str5);
            sb.append(j2 / 1000000);
            sb.append(str5);
            sb.append(iMax2);
            PerfLog.d(27, sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean shouldTriggerPerfetto(int i, int i2) {
        int i3;
        int i4 = this.mTraceThresholdMissedFrames;
        return (i4 != -1 && i >= i4) || (!this.mSurfaceOnly && (i3 = this.mTraceThresholdFrameTimeMillis) != -1 && i2 >= i3 * 1000000);
    }

    public void removeObservers() {
        SurfaceControl.OnJankDataListenerRegistration onJankDataListenerRegistration = this.mJankDataListenerRegistration;
        if (onJankDataListenerRegistration != null) {
            onJankDataListenerRegistration.release();
            this.mJankDataListenerRegistration = null;
        }
        if (this.mSurfaceOnly) {
            return;
        }
        this.mRendererWrapper.removeObserver(this.mObserver);
        ViewRootImpl.SurfaceChangedCallback surfaceChangedCallback = this.mSurfaceChangedCallback;
        if (surfaceChangedCallback != null) {
            this.mViewRoot.removeSurfaceChangedCallback(surfaceChangedCallback);
        }
    }

    public static class FrameMetricsWrapper {
        private final FrameMetrics mFrameMetrics = new FrameMetrics();

        public long[] getTiming() {
            return this.mFrameMetrics.mTimingData;
        }

        public long getMetric(int i) {
            return this.mFrameMetrics.getMetric(i);
        }
    }

    public static class ThreadedRendererWrapper {
        private final ThreadedRenderer mRenderer;

        public ThreadedRendererWrapper(ThreadedRenderer threadedRenderer) {
            this.mRenderer = threadedRenderer;
        }

        public void addObserver(HardwareRendererObserver hardwareRendererObserver) {
            if (CoreRune.GFW_DEBUG_DISABLE_HWRENDERING || hardwareRendererObserver == null) {
                return;
            }
            this.mRenderer.addObserver(hardwareRendererObserver);
        }

        public void removeObserver(HardwareRendererObserver hardwareRendererObserver) {
            if (CoreRune.GFW_DEBUG_DISABLE_HWRENDERING || hardwareRendererObserver == null) {
                return;
            }
            this.mRenderer.addObserver(hardwareRendererObserver);
        }
    }

    public static class ViewRootWrapper {
        private final ViewRootImpl mViewRoot;

        public ViewRootWrapper(ViewRootImpl viewRootImpl) {
            this.mViewRoot = viewRootImpl;
        }

        public void addSurfaceChangedCallback(ViewRootImpl.SurfaceChangedCallback surfaceChangedCallback) {
            this.mViewRoot.addSurfaceChangedCallback(surfaceChangedCallback);
        }

        public void removeSurfaceChangedCallback(ViewRootImpl.SurfaceChangedCallback surfaceChangedCallback) {
            this.mViewRoot.removeSurfaceChangedCallback(surfaceChangedCallback);
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

        int dipToPx(int i) {
            return (int) ((this.mViewRoot.mContext.getResources().getDisplayMetrics().density * i) + 0.5f);
        }
    }

    public static class SurfaceControlWrapper {
        public SurfaceControl.OnJankDataListenerRegistration addJankStatsListener(SurfaceControl.OnJankDataListener onJankDataListener, SurfaceControl surfaceControl) {
            return surfaceControl.addOnJankDataListener(onJankDataListener);
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

        public void write(int i, int i2, int i3, int i4, long j, long j2, long j3, long j4, long j5, long j6) {
            FrameworkStatsLog.write(i, i4, j, j2, j3, j4, j5, j6, this.mDisplayResolutionTracker.getResolution(i2), i3);
        }
    }
}
