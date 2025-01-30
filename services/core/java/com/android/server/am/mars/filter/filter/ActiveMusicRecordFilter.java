package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.media.AudioManager;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.server.LocalServices;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.filter.IFilter;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.samsung.android.server.audio.SemAudioServiceInternal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class ActiveMusicRecordFilter implements IFilter {
  public static String TAG = "ActiveMusicRecordFilter";
  public String FM_RADIO_PACKAGE_NAME;
  public ArrayMap mActiveMusicRecordPkgs;
  public AudioManager mAudioManager;
  public Context mContext;
  public boolean mIsUsingAudioList;
  public SemAudioServiceInternal mLocalAudioService;
  public ArrayMap mTTSPkgs;
  public ArrayList mTTSPkgsUid;
  public ExecutorService threadPool;

  public abstract class ActiveMusicRecordFilterHolder {
    public static final ActiveMusicRecordFilter INSTANCE = new ActiveMusicRecordFilter();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void deInit() {}

  public ActiveMusicRecordFilter() {
    this.mContext = null;
    this.mAudioManager = null;
    this.mLocalAudioService = null;
    this.mActiveMusicRecordPkgs = new ArrayMap();
    this.mTTSPkgs = new ArrayMap();
    this.mTTSPkgsUid = new ArrayList();
    this.mIsUsingAudioList = false;
    this.FM_RADIO_PACKAGE_NAME = "com.sec.android.app.fm";
    this.threadPool = null;
  }

  public static ActiveMusicRecordFilter getInstance() {
    return ActiveMusicRecordFilterHolder.INSTANCE;
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void init(Context context) {
    this.mContext = context;
    this.threadPool =
        Executors.newFixedThreadPool(
            4,
            new ThreadFactory() { // from class:
                                  // com.android.server.am.mars.filter.filter.ActiveMusicRecordFilter.1
              public final AtomicInteger counter = new AtomicInteger();

              @Override // java.util.concurrent.ThreadFactory
              public Thread newThread(Runnable runnable) {
                return new Thread(
                    runnable,
                    String.format("freecess-%d", Integer.valueOf(this.counter.incrementAndGet())));
              }
            });
    if (this.mLocalAudioService == null) {
      this.mLocalAudioService =
          (SemAudioServiceInternal) LocalServices.getService(SemAudioServiceInternal.class);
    }
  }

  public final boolean isUsingFmRadioActive() {
    if (this.mAudioManager == null) {
      Slog.w(TAG, "audio Manager is null");
      return true;
    }
    FutureTask futureTask =
        new FutureTask(
            new Callable() { // from class:
                             // com.android.server.am.mars.filter.filter.ActiveMusicRecordFilter$$ExternalSyntheticLambda1
              @Override // java.util.concurrent.Callable
              public final Object call() {
                Boolean lambda$isUsingFmRadioActive$0;
                lambda$isUsingFmRadioActive$0 =
                    ActiveMusicRecordFilter.this.lambda$isUsingFmRadioActive$0();
                return lambda$isUsingFmRadioActive$0;
              }
            });
    this.threadPool.execute(futureTask);
    try {
      return ((Boolean) futureTask.get(1L, TimeUnit.SECONDS)).booleanValue();
    } catch (Exception e) {
      if (e instanceof TimeoutException) {
        Slog.w(TAG, "1 second timeout executing semIsFmRadioActive");
      }
      futureTask.cancel(false);
      e.printStackTrace();
      return true;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ Boolean lambda$isUsingFmRadioActive$0() {
    return Boolean.valueOf(this.mAudioManager.semIsFmRadioActive());
  }

  public final boolean isUsingAudio(final String str, final int i) {
    if (this.mAudioManager == null) {
      Slog.w(TAG, "audio Manager is null");
      return true;
    }
    FutureTask futureTask =
        new FutureTask(
            new Callable() { // from class:
                             // com.android.server.am.mars.filter.filter.ActiveMusicRecordFilter$$ExternalSyntheticLambda0
              @Override // java.util.concurrent.Callable
              public final Object call() {
                Boolean lambda$isUsingAudio$1;
                lambda$isUsingAudio$1 = ActiveMusicRecordFilter.this.lambda$isUsingAudio$1(str, i);
                return lambda$isUsingAudio$1;
              }
            });
    this.threadPool.execute(futureTask);
    try {
      return ((Boolean) futureTask.get(1L, TimeUnit.SECONDS)).booleanValue();
    } catch (Exception e) {
      if (e instanceof TimeoutException) {
        Slog.w(TAG, "1 second timeout executing isUsingAudio");
      }
      futureTask.cancel(false);
      e.printStackTrace();
      return true;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ Boolean lambda$isUsingAudio$1(String str, int i) {
    return Boolean.valueOf(this.mAudioManager.isUsingAudio(str, i));
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public int filter(String str, int i, int i2, int i3) {
    Context context;
    if (this.mAudioManager == null && (context = this.mContext) != null) {
      this.mAudioManager = (AudioManager) context.getSystemService("audio");
    }
    if (this.FM_RADIO_PACKAGE_NAME.equals(str)) {
      return isUsingFmRadioActive() ? 7 : 0;
    }
    long elapsedRealtime = SystemClock.elapsedRealtime();
    if (((MARsPolicyManager.getInstance().getScreenOnState() && (i3 == 4 || i3 == 15))
            || (i3 == 18 && !this.mIsUsingAudioList))
        && isUsingAudio(str, i2)) {
      synchronized (this.mActiveMusicRecordPkgs) {
        this.mActiveMusicRecordPkgs.put(Integer.valueOf(i2), Long.valueOf(elapsedRealtime));
      }
      return 7;
    }
    synchronized (this.mActiveMusicRecordPkgs) {
      Long l = (Long) this.mActiveMusicRecordPkgs.get(Integer.valueOf(i2));
      long j =
          !MARsPolicyManager.getInstance().isChinaPolicyEnabled()
              ? BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS
              : 2000L;
      if (l != null && elapsedRealtime - l.longValue() <= j) {
        return 7;
      }
      this.mActiveMusicRecordPkgs.remove(Integer.valueOf(i2));
      if (!MARsPolicyManager.getInstance().isChinaPolicyEnabled()
          || MARsPolicyManager.getInstance().isAutoRunOn(str, i)) {
        synchronized (this.mTTSPkgs) {
          ArrayList arrayList = (ArrayList) this.mTTSPkgs.get(Integer.valueOf(i));
          if (arrayList != null && arrayList.contains(str)) {
            return 7;
          }
          synchronized (this.mTTSPkgsUid) {
            if (this.mTTSPkgsUid.contains(Integer.valueOf(i2))) {
              return 7;
            }
          }
        }
      }
      return 0;
    }
  }

  public void getUidListUsingAudio() {
    if (this.mLocalAudioService == null) {
      this.mLocalAudioService =
          (SemAudioServiceInternal) LocalServices.getService(SemAudioServiceInternal.class);
    }
    SemAudioServiceInternal semAudioServiceInternal = this.mLocalAudioService;
    if (semAudioServiceInternal != null) {
      List<Integer> uidListUsingAudio = semAudioServiceInternal.getUidListUsingAudio();
      long elapsedRealtime = SystemClock.elapsedRealtime();
      for (Integer num : uidListUsingAudio) {
        synchronized (this.mActiveMusicRecordPkgs) {
          this.mActiveMusicRecordPkgs.put(num, Long.valueOf(elapsedRealtime));
        }
      }
    }
  }

  public void onTTSPkgBinded(String str, Integer num) {
    synchronized (this.mTTSPkgs) {
      ArrayList arrayList = (ArrayList) this.mTTSPkgs.get(num);
      if (arrayList == null) {
        arrayList = new ArrayList();
      }
      if (!arrayList.contains(str)) {
        arrayList.add(str);
        this.mTTSPkgs.put(num, arrayList);
      }
    }
  }

  public void onTTSPkgUnBinded(String str, Integer num) {
    synchronized (this.mTTSPkgs) {
      ArrayList arrayList = (ArrayList) this.mTTSPkgs.get(num);
      if (arrayList != null && arrayList.contains(str)) {
        arrayList.remove(str);
        this.mTTSPkgs.put(num, arrayList);
      }
    }
  }

  public void onTTSPkgBind(Integer num) {
    synchronized (this.mTTSPkgsUid) {
      if (!this.mTTSPkgsUid.contains(num)) {
        this.mTTSPkgsUid.add(num);
      }
    }
  }

  public void onTTSPkgUnBindAll() {
    synchronized (this.mTTSPkgsUid) {
      this.mTTSPkgsUid.clear();
    }
  }

  public void setUsingAudioList(boolean z) {
    this.mIsUsingAudioList = z;
  }
}
