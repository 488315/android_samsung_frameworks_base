package com.android.server.chimera;

import android.os.SystemClock;
import android.os.IInstalld;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import com.android.internal.os.ProcessCpuTracker;
import com.android.server.am.MARsPolicyManager;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class ChimeraAppManager {
  public static final String CEM_PKG_PROTECTED_INTERVAL_DEFAULT = String.valueOf(60000);
  public ChimeraAppClassifier mAppClassifier;
  public int mCemPkgProtectedIntervalMs;
  public final SystemRepository mSystemRepository;
  public Map mStandbyInfoMap = new ArrayMap();
  public Map mReclaimApps = new ArrayMap();
  public Map mGcApps = new ArrayMap();
  public Set mForegroundG3ProcList = new HashSet();
  public List mDeviceIdleKillAllowList =
      new ArrayList() { // from class: com.android.server.chimera.ChimeraAppManager.1
        {
          add(KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME);
          add(KnoxCustomManagerService.LAUNCHER_PACKAGE);
        }
      };
  public List mDeviceIdleKillProtectedList =
      new ArrayList() { // from class: com.android.server.chimera.ChimeraAppManager.2
        {
          add("com.android.systemui");
          add("com.google.android.providers.media.module");
        }
      };

  public final boolean persistentApp(int i) {
    return i == -700 || i == -800;
  }

  public ChimeraAppManager(SystemRepository systemRepository) {
    this.mAppClassifier = null;
    this.mAppClassifier = new ChimeraAppClassifier(systemRepository);
    this.mSystemRepository = systemRepository;
    this.mCemPkgProtectedIntervalMs =
        Integer.parseInt(
            systemRepository.getSystemProperty(
                "persist.sys.chimera_cem_pkg_protected_interval_ms",
                CEM_PKG_PROTECTED_INTERVAL_DEFAULT));
    collectStandbyBucketList();
  }

  public void addToReclaimAppList(String str, int i, int i2) {
    ChimeraAppInfo chimeraAppInfo = (ChimeraAppInfo) this.mReclaimApps.get(str);
    if (chimeraAppInfo == null) {
      chimeraAppInfo = new ChimeraAppInfo(i, str);
      this.mReclaimApps.put(str, chimeraAppInfo);
    }
    chimeraAppInfo.addProcess(i2, null, 0L, 0L, 0L, 0L, 0);
  }

  public void addToGcAppList(String str, int i, int i2) {
    ChimeraAppInfo chimeraAppInfo = (ChimeraAppInfo) this.mGcApps.get(str);
    if (chimeraAppInfo == null) {
      chimeraAppInfo = new ChimeraAppInfo(i, str);
      this.mGcApps.put(str, chimeraAppInfo);
    }
    chimeraAppInfo.addProcess(i2, null, 0L, 0L, 0L, 0L, 0);
  }

  public void addToReclaimGcAppList(
      String str, SystemRepository.RunningAppProcessInfo runningAppProcessInfo) {
    if (RepositoryFactory.getInstance()
        .getSettingRepository(this.mSystemRepository)
        .isGcEnabled()) {
      addToGcAppList(str, runningAppProcessInfo.uid, runningAppProcessInfo.pid);
    }
    if (RepositoryFactory.getInstance()
        .getSettingRepository(this.mSystemRepository)
        .isReclaimPageCacheEnabled()) {
      addToReclaimAppList(str, runningAppProcessInfo.uid, runningAppProcessInfo.pid);
    }
  }

  public List getAppsToReclaim() {
    ArrayList arrayList = new ArrayList();
    for (ChimeraAppInfo chimeraAppInfo : this.mReclaimApps.values()) {
      arrayList.add(chimeraAppInfo);
      Log.i("ChimeraAppManager", "getAppsToReclaim(): " + chimeraAppInfo.packageName);
    }
    return arrayList;
  }

  public List getAppsToGc() {
    ArrayList arrayList = new ArrayList();
    for (ChimeraAppInfo chimeraAppInfo : this.mGcApps.values()) {
      arrayList.add(chimeraAppInfo);
      Log.i("ChimeraAppManager", "getAppsToGc(): " + chimeraAppInfo.packageName);
    }
    return arrayList;
  }

  public List getAppsToKill(
      SkipReasonLogger skipReasonLogger, int i, ChimeraCommonUtil.TriggerSource triggerSource) {
    ArrayList arrayList;
    boolean z;
    HashSet hashSet;
    int i2;
    String str;
    List list;
    boolean z2;
    String str2;
    ArrayList arrayList2;
    Iterator it;
    ArrayList arrayList3;
    ArrayList arrayList4;
    SystemRepository systemRepository = this.mSystemRepository;
    StringBuilder sb = new StringBuilder();
    sb.append("getAppsToKill() - protectedLruCount: ");
    int i3 = i;
    sb.append(i3);
    String str3 = "ChimeraAppManager";
    systemRepository.logDebug("ChimeraAppManager", sb.toString());
    ArrayMap arrayMap = new ArrayMap();
    HashSet hashSet2 = new HashSet();
    ArrayList arrayList5 = new ArrayList();
    List accessibilityServicePackages = this.mSystemRepository.getAccessibilityServicePackages();
    boolean isBubEnabled = ChimeraCommonUtil.isBubEnabled(this.mSystemRepository);
    String currentHomePackageName = this.mSystemRepository.getCurrentHomePackageName();
    ArrayList arrayList6 = new ArrayList();
    this.mReclaimApps.clear();
    this.mGcApps.clear();
    Iterator it2 = this.mSystemRepository.getRunningAppProcesses().iterator();
    int i4 = -1;
    while (it2.hasNext()) {
      SystemRepository.RunningAppProcessInfo runningAppProcessInfo =
          (SystemRepository.RunningAppProcessInfo) it2.next();
      i4++;
      arrayList6.add(Integer.valueOf(runningAppProcessInfo.pid));
      String[] strArr = runningAppProcessInfo.pkgList;
      if (strArr == null || strArr.length <= 0) {
        hashSet = hashSet2;
        i2 = i3;
        str = str3;
        list = accessibilityServicePackages;
        z2 = isBubEnabled;
        str2 = currentHomePackageName;
        arrayList2 = arrayList6;
        it = it2;
      } else {
        String str4 = strArr[0];
        it = it2;
        if (runningAppProcessInfo.processState == 17) {
          skipReasonLogger.mark(runningAppProcessInfo.pid, SkipReasonLogger.Reason.CACC);
        } else if (!TextUtils.equals(str4, currentHomePackageName)
            && !accessibilityServicePackages.contains(str4)) {
          String str5 = currentHomePackageName;
          ArrayList arrayList7 = arrayList6;
          if (SystemClock.uptimeMillis() - runningAppProcessInfo.lastActivityTime < 5000) {
            hashSet = hashSet2;
            i2 = i3;
            str = str3;
            arrayList3 = arrayList5;
            list = accessibilityServicePackages;
            z2 = isBubEnabled;
            str2 = str5;
            arrayList2 = arrayList7;
          } else {
            if (runningAppProcessInfo.processState == 19) {
              arrayList4 = arrayList5;
              list = accessibilityServicePackages;
              if (SystemClock.uptimeMillis() - runningAppProcessInfo.lastActivityTime
                  < this.mCemPkgProtectedIntervalMs) {
                hashSet2.add(str4);
                skipReasonLogger.mark(
                    runningAppProcessInfo.pid, SkipReasonLogger.Reason.CACHED_EMPTY);
                hashSet = hashSet2;
                i2 = i3;
                str = str3;
                str2 = str5;
                arrayList2 = arrayList7;
                arrayList3 = arrayList4;
                z2 = isBubEnabled;
              }
            } else {
              arrayList4 = arrayList5;
              list = accessibilityServicePackages;
            }
            if (runningAppProcessInfo.processState == 10
                && SystemClock.uptimeMillis() - runningAppProcessInfo.lastActivityTime
                    < this.mCemPkgProtectedIntervalMs) {
              hashSet2.add(str4);
              skipReasonLogger.mark(runningAppProcessInfo.pid, SkipReasonLogger.Reason.SERVICE);
            } else if (hashSet2.contains(str4)) {
              addToReclaimGcAppList(str4, runningAppProcessInfo);
            } else {
              if ((runningAppProcessInfo.flags & 4) > 0
                  || runningAppProcessInfo.processState == 18) {
                addToReclaimGcAppList(str4, runningAppProcessInfo);
                if (i3 > 0) {
                  this.mSystemRepository.logDebug(
                      str3, "getAppsToKill() - Protected by LRU : " + str4);
                  i3 += -1;
                  hashSet2.add(str4);
                  skipReasonLogger.mark(runningAppProcessInfo.pid, SkipReasonLogger.Reason.LRU);
                  currentHomePackageName = str5;
                  arrayList6 = arrayList7;
                  it2 = it;
                  arrayList5 = arrayList4;
                  accessibilityServicePackages = list;
                }
              }
              if (runningAppProcessInfo.processState <= 1) {
                hashSet2.add(str4);
                skipReasonLogger.mark(
                    runningAppProcessInfo.pid, SkipReasonLogger.Reason.PERSISTENT_OR_PROTECTED);
              } else if (!isBubEnabled && runningAppProcessInfo.isProtectedInPicked) {
                this.mSystemRepository.logDebug(
                    str3, "getAppsToKill() - Protected by Picked " + str4);
                hashSet2.add(str4);
                skipReasonLogger.mark(runningAppProcessInfo.pid, SkipReasonLogger.Reason.PICKED);
              } else {
                ChimeraAppInfo chimeraAppInfo = (ChimeraAppInfo) arrayMap.get(str4);
                if (chimeraAppInfo == null) {
                  chimeraAppInfo = new ChimeraAppInfo(runningAppProcessInfo.uid, str4);
                  chimeraAppInfo.lruIdx = i4;
                  getAppStandbyBucket(chimeraAppInfo);
                  chimeraAppInfo.curStandbyBucket =
                      getAppStandbyBucket(str4, runningAppProcessInfo.uid);
                  arrayMap.put(str4, chimeraAppInfo);
                  arrayList3 = arrayList4;
                  arrayList3.add(
                      str4
                          + "/"
                          + this.mSystemRepository.getUserId(runningAppProcessInfo.uid)
                          + "/"
                          + runningAppProcessInfo.uid);
                } else {
                  arrayList3 = arrayList4;
                }
                HashSet hashSet3 = hashSet2;
                i2 = i3;
                long j = runningAppProcessInfo.lastPss;
                if (j <= 0) {
                  this.mSystemRepository.logDebug(
                      str3, chimeraAppInfo.packageName + ": proc.lastPss <= 0, fetching right now");
                  j =
                      ChimeraCommonUtil.getProcPss(
                          this.mSystemRepository, runningAppProcessInfo.pid);
                }
                z2 = isBubEnabled;
                hashSet = hashSet3;
                str2 = str5;
                arrayList2 = arrayList7;
                str = str3;
                chimeraAppInfo.addProcess(
                    runningAppProcessInfo.pid,
                    runningAppProcessInfo.processName,
                    j,
                    runningAppProcessInfo.initialIdlePss,
                    runningAppProcessInfo.avgPss,
                    runningAppProcessInfo.lastSwapPss,
                    runningAppProcessInfo.importance);
                int i5 = runningAppProcessInfo.flags;
                if ((i5 & 8) > 0) {
                  chimeraAppInfo.appType |= IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
                }
                if ((i5 & 4) > 0) {
                  chimeraAppInfo.appType |= 16777216;
                }
              }
            }
            hashSet = hashSet2;
            i2 = i3;
            str = str3;
            str2 = str5;
            arrayList2 = arrayList7;
            arrayList3 = arrayList4;
            z2 = isBubEnabled;
          }
          i3 = i2;
          arrayList5 = arrayList3;
          it2 = it;
          isBubEnabled = z2;
          accessibilityServicePackages = list;
          currentHomePackageName = str2;
          hashSet2 = hashSet;
          arrayList6 = arrayList2;
          str3 = str;
        }
        hashSet = hashSet2;
        i2 = i3;
        str = str3;
        list = accessibilityServicePackages;
        z2 = isBubEnabled;
        str2 = currentHomePackageName;
        arrayList2 = arrayList6;
      }
      arrayList3 = arrayList5;
      i3 = i2;
      arrayList5 = arrayList3;
      it2 = it;
      isBubEnabled = z2;
      accessibilityServicePackages = list;
      currentHomePackageName = str2;
      hashSet2 = hashSet;
      arrayList6 = arrayList2;
      str3 = str;
    }
    ArrayList arrayList8 = arrayList5;
    ArrayList arrayList9 = arrayList6;
    if (arrayMap.size() > 0) {
      this.mAppClassifier.prepare(triggerSource);
      this.mAppClassifier.updatePackagesType(arrayList8);
      arrayList = new ArrayList();
      for (ChimeraAppInfo chimeraAppInfo2 : arrayMap.values()) {
        chimeraAppInfo2.reclaimGain = chimeraAppInfo2.pss;
        int packageType =
            this.mAppClassifier.getPackageType(
                    chimeraAppInfo2.packageName,
                    this.mSystemRepository.getUserId(chimeraAppInfo2.uid),
                    chimeraAppInfo2.getPidList(),
                    chimeraAppInfo2.uid)
                | chimeraAppInfo2.appType;
        chimeraAppInfo2.appType = packageType;
        int appType2group = ChimeraAppInfo.appType2group(packageType);
        chimeraAppInfo2.group = appType2group;
        if (appType2group == 3
            && MARsPolicyManager.getInstance().isForegroundServicePkg(chimeraAppInfo2.uid)) {
          for (ChimeraAppInfo.ProcessInfo processInfo : chimeraAppInfo2.procList) {
            skipReasonLogger.mark(processInfo.pid, SkipReasonLogger.Reason.PERSISTENT_OR_PROTECTED);
            this.mForegroundG3ProcList.add(Integer.valueOf(processInfo.pid));
          }
        } else if (chimeraAppInfo2.isInfoNotFeteched() || chimeraAppInfo2.isInProtectedGroup()) {
          Iterator it3 = chimeraAppInfo2.procList.iterator();
          while (it3.hasNext()) {
            skipReasonLogger.mark(
                ((ChimeraAppInfo.ProcessInfo) it3.next()).pid,
                SkipReasonLogger.Reason.PERSISTENT_OR_PROTECTED);
          }
        } else {
          Iterator it4 = chimeraAppInfo2.procList.iterator();
          while (true) {
            if (!it4.hasNext()) {
              z = false;
              break;
            }
            ChimeraAppInfo.ProcessInfo processInfo2 = (ChimeraAppInfo.ProcessInfo) it4.next();
            if (this.mForegroundG3ProcList.contains(Integer.valueOf(processInfo2.pid))) {
              skipReasonLogger.mark(
                  processInfo2.pid, SkipReasonLogger.Reason.PERSISTENT_OR_PROTECTED);
              z = true;
              break;
            }
          }
          if (!z) {
            arrayList.add(chimeraAppInfo2);
          }
        }
      }
    } else {
      arrayList = null;
    }
    ArrayList arrayList10 = new ArrayList();
    Iterator it5 = this.mForegroundG3ProcList.iterator();
    while (it5.hasNext()) {
      int intValue = ((Integer) it5.next()).intValue();
      ArrayList arrayList11 = arrayList9;
      if (!arrayList11.contains(Integer.valueOf(intValue))) {
        arrayList10.add(Integer.valueOf(intValue));
      }
      arrayList9 = arrayList11;
    }
    this.mForegroundG3ProcList.removeAll(arrayList10);
    return arrayList;
  }

  public Pair getAppsForCritical(List list) {
    boolean z;
    List list2;
    String str;
    long j;
    char c = 0;
    boolean z2 = list == null;
    List<SystemRepository.RunningAppProcessInfo> runningAppProcesses =
        z2 ? this.mSystemRepository.getRunningAppProcesses() : list;
    ArrayMap arrayMap = new ArrayMap();
    ArrayList arrayList = new ArrayList();
    List accessibilityServicePackages = this.mSystemRepository.getAccessibilityServicePackages();
    String currentHomePackageName = this.mSystemRepository.getCurrentHomePackageName();
    for (SystemRepository.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
      if (runningAppProcessInfo.processState != 17) {
        String[] strArr = runningAppProcessInfo.pkgList;
        if (strArr == null || strArr.length <= 0) {
          z = z2;
          list2 = accessibilityServicePackages;
          str = currentHomePackageName;
        } else {
          String str2 = strArr[c];
          if (!TextUtils.equals(str2, currentHomePackageName)) {
            if (!this.mSystemRepository.isApp(runningAppProcessInfo.uid)) {
              this.mSystemRepository.logDebug(
                  "ChimeraAppManager",
                  "handleCritical() - Skipped by uid: " + runningAppProcessInfo.processName);
            } else if (!accessibilityServicePackages.contains(str2)
                && !this.mSystemRepository.isOnScreenWindow(runningAppProcessInfo.uid)) {
              ChimeraAppInfo chimeraAppInfo = (ChimeraAppInfo) arrayMap.get(str2);
              if (chimeraAppInfo == null) {
                chimeraAppInfo = new ChimeraAppInfo(runningAppProcessInfo.uid, str2);
                arrayMap.put(str2, chimeraAppInfo);
                arrayList.add(
                    str2
                        + "/"
                        + this.mSystemRepository.getUserId(runningAppProcessInfo.uid)
                        + "/"
                        + runningAppProcessInfo.uid);
              }
              ChimeraAppInfo chimeraAppInfo2 = chimeraAppInfo;
              long j2 = runningAppProcessInfo.lastPss;
              if (!z2 || j2 > 0) {
                j = j2;
              } else {
                this.mSystemRepository.logDebug(
                    "ChimeraAppManager",
                    chimeraAppInfo2.packageName + ": proc.lastPss <= 0, fetching right now");
                j = ChimeraCommonUtil.getProcPss(this.mSystemRepository, runningAppProcessInfo.pid);
              }
              z = z2;
              list2 = accessibilityServicePackages;
              str = currentHomePackageName;
              chimeraAppInfo2.addProcess(
                          runningAppProcessInfo.pid,
                          runningAppProcessInfo.processName,
                          j,
                          runningAppProcessInfo.initialIdlePss,
                          runningAppProcessInfo.avgPss,
                          runningAppProcessInfo.lastSwapPss,
                          runningAppProcessInfo.importance)
                      .DRAMUsed =
                  runningAppProcessInfo.DRAMUsed;
            }
          }
        }
        accessibilityServicePackages = list2;
        z2 = z;
        currentHomePackageName = str;
        c = 0;
      }
    }
    if (arrayMap.size() <= 0) {
      return null;
    }
    this.mAppClassifier.prepare(ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_PMM_CRITICAL);
    this.mAppClassifier.updatePackagesType(arrayList);
    ArrayList arrayList2 = new ArrayList();
    ArrayList arrayList3 = new ArrayList();
    for (ChimeraAppInfo chimeraAppInfo3 : arrayMap.values()) {
      int packageType =
          this.mAppClassifier.getPackageType(
              chimeraAppInfo3.packageName,
              this.mSystemRepository.getUserId(chimeraAppInfo3.uid),
              chimeraAppInfo3.getPidList(),
              chimeraAppInfo3.uid);
      chimeraAppInfo3.appType = packageType;
      chimeraAppInfo3.group = ChimeraAppInfo.appType2group(packageType);
      if (chimeraAppInfo3.isInPMMCriticalProtectedGroup()) {
        this.mSystemRepository.logDebug(
            "ChimeraAppManager",
            "handleCritical() - Skipped by protected app: " + chimeraAppInfo3.packageName);
      } else if ((chimeraAppInfo3.appType & 8388608) != 0) {
        arrayList2.add(chimeraAppInfo3);
      } else {
        arrayList3.add(chimeraAppInfo3);
      }
    }
    return new Pair(arrayList2, arrayList3);
  }

  public List getAppsToDeviceIdle(int i, long j, long j2) {
    ChimeraAppInfo chimeraAppInfo;
    int i2;
    Iterator it;
    int i3;
    int i4;
    this.mSystemRepository.logDebug("ChimeraAppManager", "getAppsToDeviceIdle()");
    ArrayMap arrayMap = new ArrayMap();
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    int systemPid = this.mSystemRepository.getSystemPid();
    char c = 0;
    if ((i & 1) > 0) {
      Iterator it2 = this.mSystemRepository.getRunningAppProcesses().iterator();
      int i5 = -1;
      while (it2.hasNext()) {
        SystemRepository.RunningAppProcessInfo runningAppProcessInfo =
            (SystemRepository.RunningAppProcessInfo) it2.next();
        int i6 = i5 + 1;
        String[] strArr = runningAppProcessInfo.pkgList;
        if (strArr != null && strArr.length > 0 && systemPid != runningAppProcessInfo.pid) {
          String str = strArr[c];
          if (!this.mDeviceIdleKillProtectedList.contains(str)
              && runningAppProcessInfo.lastPss != 0
              && runningAppProcessInfo.initialIdlePss != 0) {
            Pair processStatesAndOomScoresForPIDs =
                this.mSystemRepository.getProcessStatesAndOomScoresForPIDs(
                    new int[] {runningAppProcessInfo.pid});
            if (processStatesAndOomScoresForPIDs != null
                && (i4 = ((int[]) processStatesAndOomScoresForPIDs.second)[c]) != 0
                && ((samsungApp(str) || persistentApp(i4)) && runningAppProcessInfo.lastPss > j)) {
              ChimeraAppInfo chimeraAppInfo2 = (ChimeraAppInfo) arrayMap.get(str);
              if (chimeraAppInfo2 == null) {
                chimeraAppInfo2 = new ChimeraAppInfo(runningAppProcessInfo.uid, str);
                chimeraAppInfo2.lruIdx = i6;
                arrayMap.put(str, chimeraAppInfo2);
                arrayList.add(
                    str
                        + "/"
                        + this.mSystemRepository.getUserId(runningAppProcessInfo.uid)
                        + "/"
                        + runningAppProcessInfo.uid);
              }
              long j3 = runningAppProcessInfo.lastPss;
              if (j3 <= 0) {
                j3 =
                    ChimeraCommonUtil.getProcPss(this.mSystemRepository, runningAppProcessInfo.pid);
              }
              i3 = i6;
              i2 = systemPid;
              it = it2;
              chimeraAppInfo2.addProcess(
                  runningAppProcessInfo.pid,
                  runningAppProcessInfo.processName,
                  j3,
                  runningAppProcessInfo.initialIdlePss,
                  runningAppProcessInfo.avgPss,
                  runningAppProcessInfo.lastSwapPss,
                  runningAppProcessInfo.importance);
              chimeraAppInfo2.idleKillAdj = i4;
              i5 = i3;
              systemPid = i2;
              it2 = it;
              c = 0;
            }
          }
        }
        i2 = systemPid;
        it = it2;
        i3 = i6;
        i5 = i3;
        systemPid = i2;
        it2 = it;
        c = 0;
      }
      if (arrayMap.size() > 0) {
        this.mAppClassifier.updatePackagesType(arrayList);
        for (ChimeraAppInfo chimeraAppInfo3 : arrayMap.values()) {
          chimeraAppInfo3.reclaimGain = chimeraAppInfo3.pss - chimeraAppInfo3.swapPss;
          int userId = this.mSystemRepository.getUserId(chimeraAppInfo3.uid);
          if (samsungApp(chimeraAppInfo3.packageName)) {
            int packageType =
                this.mAppClassifier.getPackageType(
                    chimeraAppInfo3.packageName,
                    userId,
                    chimeraAppInfo3.getPidList(),
                    chimeraAppInfo3.uid);
            chimeraAppInfo3.appType = packageType;
            chimeraAppInfo3.group = ChimeraAppInfo.appType2group(packageType);
            if (chimeraAppInfo3.isInDeviceIdleKillProtectedGroup()
                && !this.mDeviceIdleKillAllowList.contains(chimeraAppInfo3.packageName)) {
              this.mSystemRepository.logDebug(
                  "ChimeraAppManager",
                  "deviceIdleCritical() - Skipped by protected app: "
                      + chimeraAppInfo3.packageName);
            }
          }
          arrayList2.add(chimeraAppInfo3);
        }
      }
    }
    if ((i & 2) > 0) {
      for (ProcessCpuTracker.Stats stats : this.mSystemRepository.getNativeProcesses(null)) {
        if (isThirdPartyApp(stats.uid)) {
          long pss = this.mSystemRepository.getPss(stats.pid, null);
          if (pss > j2) {
            String[] packageNameFromUid = this.mSystemRepository.getPackageNameFromUid(stats.uid);
            if (packageNameFromUid.length > 0) {
              chimeraAppInfo = new ChimeraAppInfo(stats.uid, packageNameFromUid[0]);
            } else {
              chimeraAppInfo = new ChimeraAppInfo(stats.uid, stats.name);
            }
            chimeraAppInfo.addProcess(stats.pid, stats.name, pss, 0L, 0L, 0L, 0);
            chimeraAppInfo.idleKillAdj = -1000;
            arrayList2.add(chimeraAppInfo);
          }
        }
      }
    }
    return arrayList2;
  }

  public final boolean isThirdPartyApp(int i) {
    return this.mSystemRepository.isApp(i);
  }

  public final boolean samsungApp(String str) {
    return str.contains("com.sec") || str.contains("com.samsung");
  }

  public int getAppStandbyBucket(String str, int i) {
    return this.mSystemRepository.getAppStandbyBucket(
        str, this.mSystemRepository.getUserId(i), SystemClock.elapsedRealtime());
  }

  public void getAppStandbyBucket(ChimeraAppInfo chimeraAppInfo) {
    Integer num = (Integer) this.mStandbyInfoMap.get(chimeraAppInfo.packageName);
    if (num != null) {
      chimeraAppInfo.cacStandbyBucket = num.intValue();
      return;
    }
    int appStandbyBucket =
        this.mSystemRepository.getAppStandbyBucket(
            chimeraAppInfo.packageName,
            this.mSystemRepository.getUserId(chimeraAppInfo.uid),
            SystemClock.elapsedRealtime());
    chimeraAppInfo.cacStandbyBucket = appStandbyBucket;
    this.mStandbyInfoMap.put(chimeraAppInfo.packageName, Integer.valueOf(appStandbyBucket));
  }

  public void collectStandbyBucketList() {
    this.mStandbyInfoMap = this.mSystemRepository.getAppStandbyBuckets();
  }

  public String dumpStandbyBucket() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.mStandbyInfoMap.size() + " apps in mStandbyInfoMap: \n");
    for (String str : this.mStandbyInfoMap.keySet()) {
      sb.append(str + ": ");
      sb.append(this.mStandbyInfoMap.get(str));
      sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    }
    return sb.toString();
  }
}
