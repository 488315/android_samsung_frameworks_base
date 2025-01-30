package com.android.server.am;

import android.os.Process;
import android.os.SystemClock;
import android.provider.DeviceConfig;
import android.util.Slog;
import com.android.server.display.DisplayPowerController2;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class CacheOomRanker {
  static final float DEFAULT_OOM_RE_RANKING_LRU_WEIGHT = 0.35f;
  static final int DEFAULT_OOM_RE_RANKING_NUMBER_TO_RE_RANK = 8;
  static final float DEFAULT_OOM_RE_RANKING_RSS_WEIGHT = 0.15f;
  static final float DEFAULT_OOM_RE_RANKING_USES_WEIGHT = 0.5f;
  static final int DEFAULT_PRESERVE_TOP_N_APPS = 3;
  static final long DEFAULT_RSS_UPDATE_RATE_MS = 10000;
  static final boolean DEFAULT_USE_FREQUENT_RSS = true;
  static final String KEY_OOM_RE_RANKING_LRU_WEIGHT = "oom_re_ranking_lru_weight";
  static final String KEY_OOM_RE_RANKING_NUMBER_TO_RE_RANK = "oom_re_ranking_number_to_re_rank";
  static final String KEY_OOM_RE_RANKING_PRESERVE_TOP_N_APPS = "oom_re_ranking_preserve_top_n_apps";
  static final String KEY_OOM_RE_RANKING_RSS_UPDATE_RATE_MS = "oom_re_ranking_rss_update_rate_ms";
  static final String KEY_OOM_RE_RANKING_RSS_WEIGHT = "oom_re_ranking_rss_weight";
  static final String KEY_OOM_RE_RANKING_USES_WEIGHT = "oom_re_ranking_uses_weight";
  static final String KEY_OOM_RE_RANKING_USE_FREQUENT_RSS = "oom_re_ranking_rss_use_frequent_rss";
  static final String KEY_USE_OOM_RE_RANKING = "use_oom_re_ranking";
  public int[] mLruPositions;
  float mLruWeight;
  public final DeviceConfig.OnPropertiesChangedListener mOnFlagsChangedListener;
  public final Object mPhenotypeFlagLock;
  int mPreserveTopNApps;
  public final ActivityManagerGlobalLock mProcLock;
  public final ProcessDependencies mProcessDependencies;
  public final Object mProfilerLock;
  long mRssUpdateRateMs;
  float mRssWeight;
  public RankedProcessRecord[] mScoredProcessRecords;
  public final ActivityManagerService mService;
  boolean mUseFrequentRss;
  public boolean mUseOomReRanking;
  float mUsesWeight;
  public static final Comparator SCORED_PROCESS_RECORD_COMPARATOR = new ScoreComparator();
  public static final Comparator CACHE_USE_COMPARATOR = new CacheUseComparator();
  public static final Comparator RSS_COMPARATOR = new RssComparator();
  public static final Comparator LAST_RSS_COMPARATOR = new LastRssComparator();
  public static final Comparator LAST_ACTIVITY_TIME_COMPARATOR = new LastActivityTimeComparator();

  public interface ProcessDependencies {
    long[] getRss(int i);
  }

  public class RankedProcessRecord {
    public ProcessRecord proc;
    public float score;

    public RankedProcessRecord() {}
  }

  public CacheOomRanker(ActivityManagerService activityManagerService) {
    this(activityManagerService, new ProcessDependenciesImpl());
  }

  public CacheOomRanker(
      ActivityManagerService activityManagerService, ProcessDependencies processDependencies) {
    this.mPhenotypeFlagLock = new Object();
    this.mUseOomReRanking = false;
    this.mPreserveTopNApps = 3;
    this.mUseFrequentRss = true;
    this.mRssUpdateRateMs = DEFAULT_RSS_UPDATE_RATE_MS;
    this.mLruWeight = DEFAULT_OOM_RE_RANKING_LRU_WEIGHT;
    this.mUsesWeight = DEFAULT_OOM_RE_RANKING_USES_WEIGHT;
    this.mRssWeight = DEFAULT_OOM_RE_RANKING_RSS_WEIGHT;
    this.mOnFlagsChangedListener =
        new DeviceConfig
            .OnPropertiesChangedListener() { // from class: com.android.server.am.CacheOomRanker.1
          public void onPropertiesChanged(DeviceConfig.Properties properties) {
            synchronized (CacheOomRanker.this.mPhenotypeFlagLock) {
              for (String str : properties.getKeyset()) {
                if (CacheOomRanker.KEY_USE_OOM_RE_RANKING.equals(str)) {
                  CacheOomRanker.this.updateUseOomReranking();
                } else if (CacheOomRanker.KEY_OOM_RE_RANKING_NUMBER_TO_RE_RANK.equals(str)) {
                  CacheOomRanker.this.updateNumberToReRank();
                } else if (CacheOomRanker.KEY_OOM_RE_RANKING_PRESERVE_TOP_N_APPS.equals(str)) {
                  CacheOomRanker.this.updatePreserveTopNApps();
                } else if (CacheOomRanker.KEY_OOM_RE_RANKING_USE_FREQUENT_RSS.equals(str)) {
                  CacheOomRanker.this.updateUseFrequentRss();
                } else if (CacheOomRanker.KEY_OOM_RE_RANKING_RSS_UPDATE_RATE_MS.equals(str)) {
                  CacheOomRanker.this.updateRssUpdateRateMs();
                } else if (CacheOomRanker.KEY_OOM_RE_RANKING_LRU_WEIGHT.equals(str)) {
                  CacheOomRanker.this.updateLruWeight();
                } else if (CacheOomRanker.KEY_OOM_RE_RANKING_USES_WEIGHT.equals(str)) {
                  CacheOomRanker.this.updateUsesWeight();
                } else if (CacheOomRanker.KEY_OOM_RE_RANKING_RSS_WEIGHT.equals(str)) {
                  CacheOomRanker.this.updateRssWeight();
                }
              }
            }
          }
        };
    this.mService = activityManagerService;
    this.mProcLock = activityManagerService.mProcLock;
    this.mProfilerLock = activityManagerService.mAppProfiler.mProfilerLock;
    this.mProcessDependencies = processDependencies;
  }

  public void init(Executor executor) {
    DeviceConfig.addOnPropertiesChangedListener(
        "activity_manager", executor, this.mOnFlagsChangedListener);
    synchronized (this.mPhenotypeFlagLock) {
      updateUseOomReranking();
      updateNumberToReRank();
      updateLruWeight();
      updateUsesWeight();
      updateRssWeight();
    }
  }

  public boolean useOomReranking() {
    boolean z;
    synchronized (this.mPhenotypeFlagLock) {
      z = this.mUseOomReRanking;
    }
    return z;
  }

  public final void updateUseOomReranking() {
    this.mUseOomReRanking =
        DeviceConfig.getBoolean("activity_manager", KEY_USE_OOM_RE_RANKING, false);
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
      if (i2 < rankedProcessRecordArr.length) {
        rankedProcessRecordArr[i2] = new RankedProcessRecord();
        i2++;
      } else {
        this.mLruPositions = new int[i];
        return;
      }
    }
  }

  public int getNumberToReRank() {
    RankedProcessRecord[] rankedProcessRecordArr = this.mScoredProcessRecords;
    if (rankedProcessRecordArr == null) {
      return 0;
    }
    return rankedProcessRecordArr.length;
  }

  public final void updatePreserveTopNApps() {
    int i = 3;
    int i2 = DeviceConfig.getInt("activity_manager", KEY_OOM_RE_RANKING_PRESERVE_TOP_N_APPS, 3);
    if (i2 < 0) {
      Slog.w("OomAdjuster", "Found negative value for preserveTopNApps, setting to default: " + i2);
    } else {
      i = i2;
    }
    this.mPreserveTopNApps = i;
  }

  public final void updateRssUpdateRateMs() {
    this.mRssUpdateRateMs =
        DeviceConfig.getLong(
            "activity_manager", KEY_OOM_RE_RANKING_RSS_UPDATE_RATE_MS, DEFAULT_RSS_UPDATE_RATE_MS);
  }

  public final void updateUseFrequentRss() {
    this.mUseFrequentRss =
        DeviceConfig.getBoolean("activity_manager", KEY_OOM_RE_RANKING_USE_FREQUENT_RSS, true);
  }

  public final void updateLruWeight() {
    this.mLruWeight =
        DeviceConfig.getFloat(
            "activity_manager", KEY_OOM_RE_RANKING_LRU_WEIGHT, DEFAULT_OOM_RE_RANKING_LRU_WEIGHT);
  }

  public final void updateUsesWeight() {
    this.mUsesWeight =
        DeviceConfig.getFloat(
            "activity_manager", KEY_OOM_RE_RANKING_USES_WEIGHT, DEFAULT_OOM_RE_RANKING_USES_WEIGHT);
  }

  public final void updateRssWeight() {
    this.mRssWeight =
        DeviceConfig.getFloat(
            "activity_manager", KEY_OOM_RE_RANKING_RSS_WEIGHT, DEFAULT_OOM_RE_RANKING_RSS_WEIGHT);
  }

  public void reRankLruCachedAppsLSP(ArrayList arrayList, int i) {
    float f;
    float f2;
    float f3;
    int i2;
    boolean z;
    long j;
    int[] iArr;
    RankedProcessRecord[] rankedProcessRecordArr;
    float f4;
    int i3;
    int i4;
    long j2;
    float f5;
    synchronized (this.mPhenotypeFlagLock) {
      f = this.mLruWeight;
      f2 = this.mUsesWeight;
      f3 = this.mRssWeight;
      i2 = this.mPreserveTopNApps;
      z = this.mUseFrequentRss;
      j = this.mRssUpdateRateMs;
      iArr = this.mLruPositions;
      rankedProcessRecordArr = this.mScoredProcessRecords;
    }
    if (iArr == null || rankedProcessRecordArr == null) {
      return;
    }
    int i5 = 0;
    int i6 = 0;
    while (true) {
      f4 = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      if (i5 >= i || i6 >= rankedProcessRecordArr.length) {
        break;
      }
      ProcessRecord processRecord = (ProcessRecord) arrayList.get(i5);
      if (appCanBeReRanked(processRecord)) {
        f5 = f2;
        RankedProcessRecord rankedProcessRecord = rankedProcessRecordArr[i6];
        rankedProcessRecord.proc = processRecord;
        rankedProcessRecord.score = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        iArr[i6] = i5;
        i6++;
      } else {
        f5 = f2;
      }
      i5++;
      f2 = f5;
    }
    float f6 = f2;
    int i7 = 0;
    while (i5 < i && i7 < i2) {
      if (appCanBeReRanked((ProcessRecord) arrayList.get(i5))) {
        i7++;
      }
      i5++;
    }
    if (i7 < i2 && (i6 = i6 - (i2 - i7)) < 0) {
      i6 = 0;
    }
    if (z) {
      long elapsedRealtime = SystemClock.elapsedRealtime();
      int i8 = 0;
      while (i8 < i6) {
        RankedProcessRecord rankedProcessRecord2 = rankedProcessRecordArr[i8];
        long cacheOomRankerRssTimeMs =
            elapsedRealtime - rankedProcessRecord2.proc.mState.getCacheOomRankerRssTimeMs();
        if (rankedProcessRecord2.proc.mState.getCacheOomRankerRss() == 0
            || cacheOomRankerRssTimeMs >= j) {
          long[] rss = this.mProcessDependencies.getRss(rankedProcessRecord2.proc.getPid());
          if (rss == null || rss.length == 0) {
            Slog.e(
                "OomAdjuster",
                "Process.getRss returned bad value, not re-ranking: " + Arrays.toString(rss));
            return;
          }
          j2 = j;
          rankedProcessRecord2.proc.mState.setCacheOomRankerRss(rss[0], elapsedRealtime);
          rankedProcessRecord2.proc.mProfile.setLastRss(rss[0]);
        } else {
          j2 = j;
        }
        i8++;
        j = j2;
        f4 = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      }
    }
    float f7 = f4;
    if (f > f7) {
      i3 = 0;
      Arrays.sort(rankedProcessRecordArr, 0, i6, LAST_ACTIVITY_TIME_COMPARATOR);
      addToScore(rankedProcessRecordArr, f);
    } else {
      i3 = 0;
    }
    if (f3 > f7) {
      if (z) {
        Arrays.sort(rankedProcessRecordArr, i3, i6, RSS_COMPARATOR);
      } else {
        synchronized (this.mService.mAppProfiler.mProfilerLock) {
          Arrays.sort(rankedProcessRecordArr, i3, i6, LAST_RSS_COMPARATOR);
        }
      }
      addToScore(rankedProcessRecordArr, f3);
    }
    if (f6 > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      i4 = 0;
      Arrays.sort(rankedProcessRecordArr, 0, i6, CACHE_USE_COMPARATOR);
      addToScore(rankedProcessRecordArr, f6);
    } else {
      i4 = 0;
    }
    Arrays.sort(rankedProcessRecordArr, i4, i6, SCORED_PROCESS_RECORD_COMPARATOR);
    for (int i9 = i4; i9 < i6; i9++) {
      arrayList.set(iArr[i9], rankedProcessRecordArr[i9].proc);
      rankedProcessRecordArr[i9].proc = null;
    }
  }

  public static boolean appCanBeReRanked(ProcessRecord processRecord) {
    return (processRecord.isKilledByAm()
            || processRecord.getThread() == null
            || processRecord.mState.getCurAdj() < 1001)
        ? false
        : true;
  }

  public static void addToScore(RankedProcessRecord[] rankedProcessRecordArr, float f) {
    for (int i = 1; i < rankedProcessRecordArr.length; i++) {
      rankedProcessRecordArr[i].score += i * f;
    }
  }

  public void dump(PrintWriter printWriter) {
    printWriter.println("CacheOomRanker settings");
    synchronized (this.mPhenotypeFlagLock) {
      printWriter.println("  use_oom_re_ranking=" + this.mUseOomReRanking);
      printWriter.println("  oom_re_ranking_number_to_re_rank=" + getNumberToReRank());
      printWriter.println("  oom_re_ranking_lru_weight=" + this.mLruWeight);
      printWriter.println("  oom_re_ranking_uses_weight=" + this.mUsesWeight);
      printWriter.println("  oom_re_ranking_rss_weight=" + this.mRssWeight);
    }
  }

  public class ScoreComparator implements Comparator {
    public ScoreComparator() {}

    @Override // java.util.Comparator
    public int compare(
        RankedProcessRecord rankedProcessRecord, RankedProcessRecord rankedProcessRecord2) {
      return Float.compare(rankedProcessRecord.score, rankedProcessRecord2.score);
    }
  }

  public class LastActivityTimeComparator implements Comparator {
    public LastActivityTimeComparator() {}

    @Override // java.util.Comparator
    public int compare(
        RankedProcessRecord rankedProcessRecord, RankedProcessRecord rankedProcessRecord2) {
      return Long.compare(
          rankedProcessRecord.proc.getLastActivityTime(),
          rankedProcessRecord2.proc.getLastActivityTime());
    }
  }

  public class CacheUseComparator implements Comparator {
    public CacheUseComparator() {}

    @Override // java.util.Comparator
    public int compare(
        RankedProcessRecord rankedProcessRecord, RankedProcessRecord rankedProcessRecord2) {
      return Long.compare(
          rankedProcessRecord.proc.mState.getCacheOomRankerUseCount(),
          rankedProcessRecord2.proc.mState.getCacheOomRankerUseCount());
    }
  }

  public class RssComparator implements Comparator {
    public RssComparator() {}

    @Override // java.util.Comparator
    public int compare(
        RankedProcessRecord rankedProcessRecord, RankedProcessRecord rankedProcessRecord2) {
      return Long.compare(
          rankedProcessRecord2.proc.mState.getCacheOomRankerRss(),
          rankedProcessRecord.proc.mState.getCacheOomRankerRss());
    }
  }

  public class LastRssComparator implements Comparator {
    public LastRssComparator() {}

    @Override // java.util.Comparator
    public int compare(
        RankedProcessRecord rankedProcessRecord, RankedProcessRecord rankedProcessRecord2) {
      return Long.compare(
          rankedProcessRecord2.proc.mProfile.getLastRss(),
          rankedProcessRecord.proc.mProfile.getLastRss());
    }
  }

  public class ProcessDependenciesImpl implements ProcessDependencies {
    public ProcessDependenciesImpl() {}

    @Override // com.android.server.am.CacheOomRanker.ProcessDependencies
    public long[] getRss(int i) {
      return Process.getRss(i);
    }
  }
}
