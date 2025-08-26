package android.app.jank;

import android.app.jank.JankDataProcessor;
import android.app.jank.StateTracker;
import android.util.Log;
import android.util.Pools;
import android.view.SurfaceControl;
import com.android.internal.util.FrameworkStatsLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class JankDataProcessor {
    private static final boolean DEBUG_LOGGING = false;
    private static final int LOG_BATCH_FREQUENCY = 50;
    private static final int MAX_IN_MEMORY_STATS = 25;
    private static final String TAG = "JankDataProcessor";
    private StateTracker mStateTracker;
    private int mCurrentBatchCount = 0;
    private ArrayList<StateTracker.StateData> mPendingStates = new ArrayList<>();
    private Pools.SimplePool<PendingJankStat> mPendingJankStatsPool = new Pools.SimplePool<>(25);
    private HashMap<String, PendingJankStat> mPendingJankStats = new HashMap<>();

    public JankDataProcessor(StateTracker stateTracker) {
        this.mStateTracker = null;
        this.mStateTracker = stateTracker;
    }

    public void processJankData(List<SurfaceControl.JankData> list, String str, int i) {
        this.mStateTracker.retrieveAllStates(this.mPendingStates);
        for (int i2 = 0; i2 < list.size(); i2++) {
            SurfaceControl.JankData jankData = list.get(i2);
            for (int i3 = 0; i3 < this.mPendingStates.size(); i3++) {
                StateTracker.StateData stateData = this.mPendingStates.get(i3);
                if (jankData.getVsyncId() >= stateData.mVsyncIdStart && jankData.getVsyncId() <= stateData.mVsyncIdEnd) {
                    recordFrameCount(jankData, stateData, str, i);
                    stateData.mProcessed = true;
                }
            }
        }
        incrementBatchCountAndMaybeLogStats();
        jankDataProcessingComplete();
    }

    public void mergeJankStats(AppJankStats appJankStats, String str) {
        String stateKey = this.mStateTracker.getStateKey(appJankStats.getWidgetCategory(), appJankStats.getWidgetId(), appJankStats.getWidgetState());
        if (this.mPendingJankStats.containsKey(stateKey)) {
            mergeExistingStat(stateKey, appJankStats);
        } else {
            mergeNewStat(stateKey, str, appJankStats);
        }
        incrementBatchCountAndMaybeLogStats();
    }

    private void mergeExistingStat(String str, AppJankStats appJankStats) {
        PendingJankStat pendingJankStat = this.mPendingJankStats.get(str);
        pendingJankStat.mJankyFrames += appJankStats.getJankyFrameCount();
        pendingJankStat.mTotalFrames += appJankStats.getTotalFrameCount();
        mergeOverrunHistograms(pendingJankStat.mFrameOverrunBuckets, appJankStats.getRelativeFrameTimeHistogram().getBucketCounters());
    }

    private void mergeNewStat(String str, String str2, AppJankStats appJankStats) {
        if (this.mPendingJankStats.size() > 25) {
            return;
        }
        PendingJankStat pendingJankStatAcquire = this.mPendingJankStatsPool.acquire();
        if (pendingJankStatAcquire == null) {
            pendingJankStatAcquire = new PendingJankStat();
        }
        pendingJankStatAcquire.clearStats();
        pendingJankStatAcquire.mActivityName = str2;
        pendingJankStatAcquire.mUid = appJankStats.getUid();
        pendingJankStatAcquire.mWidgetId = appJankStats.getWidgetId();
        pendingJankStatAcquire.mWidgetCategory = appJankStats.getWidgetCategory();
        pendingJankStatAcquire.mWidgetState = appJankStats.getWidgetState();
        pendingJankStatAcquire.mTotalFrames = appJankStats.getTotalFrameCount();
        pendingJankStatAcquire.mJankyFrames = appJankStats.getJankyFrameCount();
        mergeOverrunHistograms(pendingJankStatAcquire.mFrameOverrunBuckets, appJankStats.getRelativeFrameTimeHistogram().getBucketCounters());
        this.mPendingJankStats.put(str, pendingJankStatAcquire);
    }

    private void mergeOverrunHistograms(int[] iArr, int[] iArr2) {
        if (iArr.length != iArr2.length) {
            return;
        }
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = iArr[i] + iArr2[i];
        }
    }

    private void incrementBatchCountAndMaybeLogStats() {
        int i = this.mCurrentBatchCount + 1;
        this.mCurrentBatchCount = i;
        if (i >= 50) {
            logMetricCounts();
        }
    }

    public HashMap<String, PendingJankStat> getPendingJankStats() {
        return this.mPendingJankStats;
    }

    private void jankDataProcessingComplete() {
        this.mStateTracker.stateProcessingComplete();
        this.mPendingStates.clear();
    }

    private void recordFrameCount(SurfaceControl.JankData jankData, StateTracker.StateData stateData, String str, int i) {
        PendingJankStat pendingJankStatAcquire = this.mPendingJankStats.get(stateData.mStateDataKey);
        if (pendingJankStatAcquire == null) {
            if (this.mPendingJankStats.size() > 25) {
                return;
            }
            pendingJankStatAcquire = this.mPendingJankStatsPool.acquire();
            if (pendingJankStatAcquire == null) {
                pendingJankStatAcquire = new PendingJankStat();
            }
            pendingJankStatAcquire.clearStats();
            pendingJankStatAcquire.mActivityName = str;
            pendingJankStatAcquire.mUid = i;
            this.mPendingJankStats.put(stateData.mStateDataKey, pendingJankStatAcquire);
        }
        if (pendingJankStatAcquire.processedVsyncId == jankData.getVsyncId()) {
            return;
        }
        pendingJankStatAcquire.mTotalFrames++;
        if ((jankData.getJankType() & 2) != 0) {
            pendingJankStatAcquire.mJankyFrames++;
        }
        pendingJankStatAcquire.recordFrameOverrun(jankData.getActualAppFrameTimeNanos());
        pendingJankStatAcquire.processedVsyncId = jankData.getVsyncId();
    }

    public void logMetricCounts() {
        try {
            this.mPendingJankStats.values().forEach(new Consumer() { // from class: android.app.jank.JankDataProcessor$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$logMetricCounts$0((JankDataProcessor.PendingJankStat) obj);
                }
            });
            this.mPendingJankStats.clear();
            this.mCurrentBatchCount = 0;
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$logMetricCounts$0(PendingJankStat pendingJankStat) {
        FrameworkStatsLog.write(950, pendingJankStat.getUid(), pendingJankStat.getActivityName(), pendingJankStat.getWidgetId(), pendingJankStat.getRefreshRate(), widgetCategoryToInt(pendingJankStat.getWidgetCategory()), widgetStateToInt(pendingJankStat.getWidgetState()), pendingJankStat.getTotalFrames(), pendingJankStat.getJankyFrames(), pendingJankStat.getFrameOverrunBuckets());
        Log.d(pendingJankStat.mActivityName, pendingJankStat.toString());
        this.mPendingJankStatsPool.release(pendingJankStat);
    }

    private int widgetCategoryToInt(String str) {
        str.hashCode();
        switch (str) {
            case "scroll":
                return 2;
            case "media":
                return 3;
            case "other":
                return 6;
            case "keyboard":
                return 5;
            case "animation":
                return 2;
            case "navigation":
                return 4;
            default:
                return 0;
        }
    }

    private int widgetStateToInt(String str) {
        str.hashCode();
        switch (str) {
            case "swiping":
                return 4;
            case "tapping":
                return 9;
            case "dragging":
                return 5;
            case "zooming":
                return 6;
            case "none":
                return 1;
            case "scrolling":
                return 2;
            case "animating":
                return 7;
            case "predictive_back":
                return 10;
            case "flinging":
                return 3;
            case "playback":
                return 8;
            default:
                return 0;
        }
    }

    public static final class PendingJankStat {
        private static final int NANOS_PER_MS = 1000000;
        private static final int[] sFrameOverrunHistogramBounds = {Integer.MIN_VALUE, -200, -150, -100, -90, -80, -70, -60, -50, -40, -30, -25, -20, -18, -16, -14, -12, -10, -8, -6, -4, -2, 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 25, 30, 40, 50, 60, 70, 80, 90, 100, 150, 200, 300, 400, 500, 600, 700, 800, 900, 1000, Integer.MAX_VALUE};
        private String mActivityName;
        private long mJankyFrames;
        private int mRefreshRate;
        private long mTotalFrames;
        private int mUid;
        private String mWidgetCategory;
        private String mWidgetId;
        private String mWidgetState;
        public long processedVsyncId = -1;
        private final int[] mFrameOverrunBuckets = new int[sFrameOverrunHistogramBounds.length - 1];

        public long getProcessedVsyncId() {
            return this.processedVsyncId;
        }

        public void setProcessedVsyncId(long j) {
            this.processedVsyncId = j;
        }

        public int getUid() {
            return this.mUid;
        }

        public void setUid(int i) {
            this.mUid = i;
        }

        public String getActivityName() {
            return this.mActivityName;
        }

        public void setActivityName(String str) {
            this.mActivityName = str;
        }

        public String getWidgetId() {
            return this.mWidgetId;
        }

        public void setWidgetId(String str) {
            this.mWidgetId = str;
        }

        public String getWidgetCategory() {
            return this.mWidgetCategory;
        }

        public void setWidgetCategory(String str) {
            this.mWidgetCategory = str;
        }

        public String getWidgetState() {
            return this.mWidgetState;
        }

        public void setWidgetState(String str) {
            this.mWidgetState = str;
        }

        public long getTotalFrames() {
            return this.mTotalFrames;
        }

        public void setTotalFrames(long j) {
            this.mTotalFrames = j;
        }

        public long getJankyFrames() {
            return this.mJankyFrames;
        }

        public void setJankyFrames(long j) {
            this.mJankyFrames = j;
        }

        public int[] getFrameOverrunBuckets() {
            return this.mFrameOverrunBuckets;
        }

        public int getRefreshRate() {
            return this.mRefreshRate;
        }

        public void setRefreshRate(int i) {
            this.mRefreshRate = i;
        }

        public void recordFrameOverrun(long j) {
            try {
                int[] iArr = this.mFrameOverrunBuckets;
                int iIndexForFrameOverrun = indexForFrameOverrun(((int) j) / 1000000);
                iArr[iIndexForFrameOverrun] = iArr[iIndexForFrameOverrun] + 1;
            } catch (IndexOutOfBoundsException unused) {
            }
        }

        public void clearStats() {
            this.mUid = -1;
            this.mActivityName = "";
            this.processedVsyncId = -1L;
            this.mJankyFrames = 0L;
            this.mTotalFrames = 0L;
            this.mWidgetCategory = "";
            this.mWidgetState = "";
            this.mRefreshRate = 0;
            clearHistogram();
        }

        private void clearHistogram() {
            int i = 0;
            while (true) {
                int[] iArr = this.mFrameOverrunBuckets;
                if (i >= iArr.length) {
                    return;
                }
                iArr[i] = 0;
                i++;
            }
        }

        private int indexForFrameOverrun(int i) {
            if (i < 20) {
                if (i >= -20) {
                    return ((i + 20) / 2) + 12;
                }
                if (i >= -30) {
                    return ((i + 30) / 5) + 10;
                }
                if (i >= -100) {
                    return ((i + 100) / 10) + 3;
                }
                if (i >= -200) {
                    return ((i + 200) / 50) + 1;
                }
                return 0;
            }
            if (i < 30) {
                return ((i - 20) / 5) + 32;
            }
            if (i < 100) {
                return ((i - 30) / 10) + 34;
            }
            if (i < 200) {
                return ((i - 50) / 100) + 41;
            }
            if (i <= 1000) {
                return ((i - 200) / 100) + 43;
            }
            return this.mFrameOverrunBuckets.length - 1;
        }
    }
}
