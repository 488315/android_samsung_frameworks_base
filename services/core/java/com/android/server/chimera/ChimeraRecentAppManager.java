package com.android.server.chimera;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class ChimeraRecentAppManager {
  public RecentAppHandler mHandler;
  public final SettingRepository mSettingRepository;
  public final SystemRepository mSystemRepository;
  public final Object mRecentAppLock = new Object();
  public final Object mPsiListLock = new Object();
  List mRecentAppList = new ArrayList();
  Map mPsiSomeTotalList = new HashMap();
  Map mPsiFullTotalList = new HashMap();
  final SystemRepository.ChimeraProcessObserver mProcessObserver =
      new SystemRepository
          .ChimeraProcessObserver() { // from class:
                                      // com.android.server.chimera.ChimeraRecentAppManager.1
        public final int RESOURCE_USAGE_CHECK_INTERVAL = 1000;

        @Override // com.android.server.chimera.SystemRepository.ChimeraProcessObserver
        public void onForegroundActivitiesChanged(
            int i, int i2, boolean z, int i3, String[] strArr, boolean z2) {
          if (z
              && !z2
              && ChimeraRecentAppManager.this.mSettingRepository.isDynamicTargetFreeEnabled()
              && ChimeraRecentAppManager.this.mSystemRepository.hasPkgIcon(strArr[0], i3)) {
            ChimeraRecentAppManager.this.mSystemRepository.sendMessage(
                ChimeraRecentAppManager.this.mHandler, 1, Integer.valueOf(i2));
            if (ChimeraRecentAppManager.this.isRecentAppExist(i2)
                && ChimeraRecentAppManager.this.mSystemRepository.hasMessages(
                    ChimeraRecentAppManager.this.mHandler, 2, Integer.valueOf(i2))) {
              ChimeraRecentAppManager.this.mSystemRepository.removeMessages(
                  ChimeraRecentAppManager.this.mHandler, 2, Integer.valueOf(i2));
            }
            ChimeraRecentAppManager.this.mSystemRepository.sendMessageDelayed(
                ChimeraRecentAppManager.this.mHandler, 2, Integer.valueOf(i2), 1000L);
          }
        }
      };

  public ChimeraRecentAppManager(
      SystemRepository systemRepository, SettingRepository settingRepository, Looper looper) {
    this.mSystemRepository = systemRepository;
    this.mSettingRepository = settingRepository;
    this.mHandler = new RecentAppHandler(looper);
    registerProcessObserver();
  }

  public final boolean isRecentAppExist(int i) {
    return this.mPsiSomeTotalList.containsKey(Integer.valueOf(i));
  }

  public void checkAppUsageStart(int i) {
    updatePsiInfo(i);
  }

  public void checkAppUsageEnd(int i) {
    Set allRunningPackagePids = ChimeraCommonUtil.getAllRunningPackagePids(i);
    if (allRunningPackagePids == null || allRunningPackagePids.size() <= 0) {
      return;
    }
    Iterator it = allRunningPackagePids.iterator();
    long j = 0;
    while (it.hasNext()) {
      j += ChimeraCommonUtil.getProcPss(this.mSystemRepository, ((Integer) it.next()).intValue());
    }
    long availableMemoryKb = ChimeraCommonUtil.getAvailableMemoryKb(this.mSystemRepository);
    Pair psiInfo = getPsiInfo(i);
    updatePsiInfo(i);
    Pair psiInfo2 = getPsiInfo(i);
    if (psiInfo == null || psiInfo2 == null) {
      return;
    }
    long longValue = ((Long) psiInfo2.first).longValue() - ((Long) psiInfo.first).longValue();
    long longValue2 = ((Long) psiInfo2.second).longValue() - ((Long) psiInfo.second).longValue();
    synchronized (this.mPsiListLock) {
      this.mPsiSomeTotalList.remove(Integer.valueOf(i));
      this.mPsiFullTotalList.remove(Integer.valueOf(i));
    }
    synchronized (this.mRecentAppLock) {
      this.mRecentAppList.add(
          new ResourceUsageInfo(i, j, availableMemoryKb, longValue, longValue2));
      if (this.mRecentAppList.size() > 10) {
        this.mRecentAppList.remove(0);
      }
    }
  }

  public final Pair getPsiInfo(int i) {
    synchronized (this.mPsiListLock) {
      Long l = (Long) this.mPsiSomeTotalList.get(Integer.valueOf(i));
      Long l2 = (Long) this.mPsiFullTotalList.get(Integer.valueOf(i));
      if (l == null || l2 == null) {
        return null;
      }
      return Pair.create(l, l2);
    }
  }

  public ArrayList getRecentAppInfo() {
    int i;
    long j;
    long j2;
    long j3;
    long j4;
    long j5;
    ArrayList arrayList = new ArrayList();
    synchronized (this.mRecentAppLock) {
      if (this.mRecentAppList.size() > 0) {
        i = 0;
        long j6 = 0;
        long j7 = 0;
        long j8 = 0;
        long j9 = 0;
        j5 = 0;
        for (int i2 = 0; i2 < this.mRecentAppList.size(); i2++) {
          ResourceUsageInfo resourceUsageInfo = (ResourceUsageInfo) this.mRecentAppList.get(i2);
          long appPss = resourceUsageInfo.getAppPss();
          long availMem = resourceUsageInfo.getAvailMem();
          long psiSomeTotal = resourceUsageInfo.getPsiSomeTotal();
          long psiFullTotal = resourceUsageInfo.getPsiFullTotal();
          if (appPss > 0 && availMem > 0) {
            j6 += appPss;
            j7 += availMem;
            j8 += psiSomeTotal;
            j9 += psiFullTotal;
            i++;
          }
          if (i2 == this.mRecentAppList.size() - 1) {
            j5 = psiSomeTotal;
          }
        }
        long j10 = i;
        long j11 = j6 / j10;
        j3 = j7 / j10;
        j4 = j8 / j10;
        long j12 = j9 / j10;
        j = j11;
        j2 = j12;
      } else {
        i = 0;
        j = 0;
        j2 = 0;
        j3 = 0;
        j4 = 0;
        j5 = 0;
      }
    }
    this.mSystemRepository.logDebug(
        "ChimeraRecentAppManager",
        "getRecentAppInfo(): count:"
            + i
            + " pss:"
            + j
            + " avaiMem:"
            + j3
            + " avgPsiSomeTotal:"
            + j4
            + " avgPsiFullTotal:"
            + j2);
    arrayList.add(Long.valueOf(j3));
    arrayList.add(Long.valueOf(j));
    arrayList.add(Long.valueOf(j4));
    arrayList.add(Long.valueOf(j2));
    arrayList.add(Long.valueOf(j5));
    return arrayList;
  }

  public void updatePsiInfo(int i) {
    long psiFullTotal;
    long j;
    ChimeraCommonUtil.PsiFile psiFile =
        ChimeraCommonUtil.getPsiFile(
            ChimeraCommonUtil.PsiFileType.MEMORY, ChimeraCommonUtil.PsiDataType.TOTAL);
    if (psiFile.isEmpty()) {
      this.mSystemRepository.logDebug("ChimeraRecentAppManager", "No permission - psi memory");
      j = 0;
      psiFullTotal = 0;
    } else {
      long psiSomeTotal = psiFile.getPsiSomeTotal();
      psiFullTotal = psiFile.getPsiFullTotal();
      j = psiSomeTotal;
    }
    this.mSystemRepository.logDebug(
        "ChimeraRecentAppManager",
        "uid:" + i + " psiSomeTotal:" + j + " psiFullTotal:" + psiFullTotal);
    synchronized (this.mPsiListLock) {
      this.mPsiSomeTotalList.put(Integer.valueOf(i), Long.valueOf(j));
      this.mPsiFullTotalList.put(Integer.valueOf(i), Long.valueOf(psiFullTotal));
    }
  }

  public final void registerProcessObserver() {
    this.mSystemRepository.registerProcessObserver(this.mProcessObserver);
  }

  public class ResourceUsageInfo {
    public long mAppPss;
    public long mAvailMem;
    public long mPsiFullTotal;
    public long mPsiSomeTotal;
    public int mUid;

    public ResourceUsageInfo(int i, long j, long j2, long j3, long j4) {
      this.mUid = i;
      this.mAppPss = j;
      this.mAvailMem = j2;
      this.mPsiSomeTotal = j3;
      this.mPsiFullTotal = j4;
    }

    public long getAppPss() {
      return this.mAppPss;
    }

    public long getAvailMem() {
      return this.mAvailMem;
    }

    public long getPsiSomeTotal() {
      return this.mPsiSomeTotal;
    }

    public long getPsiFullTotal() {
      return this.mPsiFullTotal;
    }
  }

  public class RecentAppHandler extends Handler {
    public RecentAppHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int intValue = ((Integer) message.obj).intValue();
      int i = message.what;
      if (i == 1) {
        ChimeraRecentAppManager.this.checkAppUsageStart(intValue);
      } else {
        if (i != 2) {
          return;
        }
        ChimeraRecentAppManager.this.checkAppUsageEnd(intValue);
      }
    }
  }
}
