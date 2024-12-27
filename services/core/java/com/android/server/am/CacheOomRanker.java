package com.android.server.am;

import android.provider.DeviceConfig;
import android.util.Slog;

import java.util.Comparator;

public final class CacheOomRanker {
    static final float DEFAULT_OOM_RE_RANKING_LRU_WEIGHT = 0.35f;
    static final int DEFAULT_OOM_RE_RANKING_NUMBER_TO_RE_RANK = 8;
    static final float DEFAULT_OOM_RE_RANKING_RSS_WEIGHT = 0.15f;
    static final float DEFAULT_OOM_RE_RANKING_USES_WEIGHT = 0.5f;
    static final int DEFAULT_PRESERVE_TOP_N_APPS = 3;
    static final long DEFAULT_RSS_UPDATE_RATE_MS = 10000;
    static final boolean DEFAULT_USE_FREQUENT_RSS = true;
    static final String KEY_OOM_RE_RANKING_LRU_WEIGHT = "oom_re_ranking_lru_weight";
    static final String KEY_OOM_RE_RANKING_NUMBER_TO_RE_RANK = "oom_re_ranking_number_to_re_rank";
    static final String KEY_OOM_RE_RANKING_PRESERVE_TOP_N_APPS =
            "oom_re_ranking_preserve_top_n_apps";
    static final String KEY_OOM_RE_RANKING_RSS_UPDATE_RATE_MS = "oom_re_ranking_rss_update_rate_ms";
    static final String KEY_OOM_RE_RANKING_RSS_WEIGHT = "oom_re_ranking_rss_weight";
    static final String KEY_OOM_RE_RANKING_USES_WEIGHT = "oom_re_ranking_uses_weight";
    static final String KEY_OOM_RE_RANKING_USE_FREQUENT_RSS = "oom_re_ranking_rss_use_frequent_rss";
    static final String KEY_USE_OOM_RE_RANKING = "use_oom_re_ranking";
    public int[] mLruPositions;
    public final ProcessDependencies mProcessDependencies;
    public final Object mProfilerLock;
    public RankedProcessRecord[] mScoredProcessRecords;
    public final ActivityManagerService mService;
    public static final RssComparator SCORED_PROCESS_RECORD_COMPARATOR = new RssComparator(4);
    public static final RssComparator CACHE_USE_COMPARATOR = new RssComparator(1);
    public static final RssComparator RSS_COMPARATOR = new RssComparator(0);
    public static final RssComparator LAST_RSS_COMPARATOR = new RssComparator(3);
    public static final RssComparator LAST_ACTIVITY_TIME_COMPARATOR = new RssComparator(2);
    public final Object mPhenotypeFlagLock = new Object();
    public boolean mUseOomReRanking = false;
    int mPreserveTopNApps = 3;
    boolean mUseFrequentRss = true;
    long mRssUpdateRateMs = DEFAULT_RSS_UPDATE_RATE_MS;
    float mLruWeight = DEFAULT_OOM_RE_RANKING_LRU_WEIGHT;
    float mUsesWeight = DEFAULT_OOM_RE_RANKING_USES_WEIGHT;
    float mRssWeight = DEFAULT_OOM_RE_RANKING_RSS_WEIGHT;
    public final AnonymousClass1 mOnFlagsChangedListener =
            new DeviceConfig.OnPropertiesChangedListener() { // from class:
                // com.android.server.am.CacheOomRanker.1
                public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                    synchronized (CacheOomRanker.this.mPhenotypeFlagLock) {
                        try {
                            for (String str : properties.getKeyset()) {
                                if (CacheOomRanker.KEY_USE_OOM_RE_RANKING.equals(str)) {
                                    CacheOomRanker cacheOomRanker = CacheOomRanker.this;
                                    cacheOomRanker.getClass();
                                    cacheOomRanker.mUseOomReRanking =
                                            DeviceConfig.getBoolean(
                                                    "activity_manager",
                                                    CacheOomRanker.KEY_USE_OOM_RE_RANKING,
                                                    false);
                                } else if (CacheOomRanker.KEY_OOM_RE_RANKING_NUMBER_TO_RE_RANK
                                        .equals(str)) {
                                    CacheOomRanker.this.updateNumberToReRank();
                                } else if (CacheOomRanker.KEY_OOM_RE_RANKING_PRESERVE_TOP_N_APPS
                                        .equals(str)) {
                                    CacheOomRanker cacheOomRanker2 = CacheOomRanker.this;
                                    cacheOomRanker2.getClass();
                                    int i = 3;
                                    int i2 =
                                            DeviceConfig.getInt(
                                                    "activity_manager",
                                                    CacheOomRanker
                                                            .KEY_OOM_RE_RANKING_PRESERVE_TOP_N_APPS,
                                                    3);
                                    if (i2 < 0) {
                                        Slog.w(
                                                "OomAdjuster",
                                                "Found negative value for preserveTopNApps, setting"
                                                    + " to default: "
                                                        + i2);
                                    } else {
                                        i = i2;
                                    }
                                    cacheOomRanker2.mPreserveTopNApps = i;
                                } else if (CacheOomRanker.KEY_OOM_RE_RANKING_USE_FREQUENT_RSS
                                        .equals(str)) {
                                    CacheOomRanker cacheOomRanker3 = CacheOomRanker.this;
                                    cacheOomRanker3.getClass();
                                    cacheOomRanker3.mUseFrequentRss =
                                            DeviceConfig.getBoolean(
                                                    "activity_manager",
                                                    CacheOomRanker
                                                            .KEY_OOM_RE_RANKING_USE_FREQUENT_RSS,
                                                    true);
                                } else if (CacheOomRanker.KEY_OOM_RE_RANKING_RSS_UPDATE_RATE_MS
                                        .equals(str)) {
                                    CacheOomRanker cacheOomRanker4 = CacheOomRanker.this;
                                    cacheOomRanker4.getClass();
                                    cacheOomRanker4.mRssUpdateRateMs =
                                            DeviceConfig.getLong(
                                                    "activity_manager",
                                                    CacheOomRanker
                                                            .KEY_OOM_RE_RANKING_RSS_UPDATE_RATE_MS,
                                                    CacheOomRanker.DEFAULT_RSS_UPDATE_RATE_MS);
                                } else if (CacheOomRanker.KEY_OOM_RE_RANKING_LRU_WEIGHT.equals(
                                        str)) {
                                    CacheOomRanker cacheOomRanker5 = CacheOomRanker.this;
                                    cacheOomRanker5.getClass();
                                    cacheOomRanker5.mLruWeight =
                                            DeviceConfig.getFloat(
                                                    "activity_manager",
                                                    CacheOomRanker.KEY_OOM_RE_RANKING_LRU_WEIGHT,
                                                    CacheOomRanker
                                                            .DEFAULT_OOM_RE_RANKING_LRU_WEIGHT);
                                } else if (CacheOomRanker.KEY_OOM_RE_RANKING_USES_WEIGHT.equals(
                                        str)) {
                                    CacheOomRanker cacheOomRanker6 = CacheOomRanker.this;
                                    cacheOomRanker6.getClass();
                                    cacheOomRanker6.mUsesWeight =
                                            DeviceConfig.getFloat(
                                                    "activity_manager",
                                                    CacheOomRanker.KEY_OOM_RE_RANKING_USES_WEIGHT,
                                                    CacheOomRanker
                                                            .DEFAULT_OOM_RE_RANKING_USES_WEIGHT);
                                } else if (CacheOomRanker.KEY_OOM_RE_RANKING_RSS_WEIGHT.equals(
                                        str)) {
                                    CacheOomRanker cacheOomRanker7 = CacheOomRanker.this;
                                    cacheOomRanker7.getClass();
                                    cacheOomRanker7.mRssWeight =
                                            DeviceConfig.getFloat(
                                                    "activity_manager",
                                                    CacheOomRanker.KEY_OOM_RE_RANKING_RSS_WEIGHT,
                                                    CacheOomRanker
                                                            .DEFAULT_OOM_RE_RANKING_RSS_WEIGHT);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            };

    public interface ProcessDependencies {}

    public final class ProcessDependenciesImpl implements ProcessDependencies {}

    public final class RankedProcessRecord {
        public ProcessRecord proc;
        public float score;
    }

    public final class RssComparator implements Comparator {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ RssComparator(int i) {
            this.$r8$classId = i;
        }

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            switch (this.$r8$classId) {
                case 0:
                    return Long.compare(
                            ((RankedProcessRecord) obj2).proc.mState.mCacheOomRankerRss,
                            ((RankedProcessRecord) obj).proc.mState.mCacheOomRankerRss);
                case 1:
                    return Long.compare(
                            ((RankedProcessRecord) obj).proc.mState.mCacheOomRankerUseCount,
                            ((RankedProcessRecord) obj2).proc.mState.mCacheOomRankerUseCount);
                case 2:
                    return Long.compare(
                            ((RankedProcessRecord) obj).proc.mLastActivityTime,
                            ((RankedProcessRecord) obj2).proc.mLastActivityTime);
                case 3:
                    return Long.compare(
                            ((RankedProcessRecord) obj2).proc.mProfile.mLastRss,
                            ((RankedProcessRecord) obj).proc.mProfile.mLastRss);
                default:
                    return Float.compare(
                            ((RankedProcessRecord) obj).score, ((RankedProcessRecord) obj2).score);
            }
        }
    }

    public CacheOomRanker(
            ActivityManagerService activityManagerService,
            ProcessDependencies processDependencies) {
        this.mService = activityManagerService;
        ActivityManagerProcLock activityManagerProcLock = activityManagerService.mProcLock;
        this.mProfilerLock = activityManagerService.mAppProfiler.mProfilerLock;
        this.mProcessDependencies = processDependencies;
    }

    public static void addToScore(RankedProcessRecord[] rankedProcessRecordArr, float f) {
        for (int i = 1; i < rankedProcessRecordArr.length; i++) {
            RankedProcessRecord rankedProcessRecord = rankedProcessRecordArr[i];
            rankedProcessRecord.score = (i * f) + rankedProcessRecord.score;
        }
    }

    public int getNumberToReRank() {
        RankedProcessRecord[] rankedProcessRecordArr = this.mScoredProcessRecords;
        if (rankedProcessRecordArr == null) {
            return 0;
        }
        return rankedProcessRecordArr.length;
    }

    public final void updateNumberToReRank() {
        int numberToReRank = getNumberToReRank();
        int i = DeviceConfig.getInt("activity_manager", KEY_OOM_RE_RANKING_NUMBER_TO_RE_RANK, 8);
        if (numberToReRank == i) {
            return;
        }
        this.mScoredProcessRecords = new RankedProcessRecord[i];
        int i2 = 0;
        while (true) {
            RankedProcessRecord[] rankedProcessRecordArr = this.mScoredProcessRecords;
            if (i2 >= rankedProcessRecordArr.length) {
                this.mLruPositions = new int[i];
                return;
            } else {
                rankedProcessRecordArr[i2] = new RankedProcessRecord();
                i2++;
            }
        }
    }
}
