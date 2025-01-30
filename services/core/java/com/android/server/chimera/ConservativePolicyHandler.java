package com.android.server.chimera;

import android.os.Looper;
import android.p005os.IInstalld;
import com.android.server.chimera.ChimeraAppInfo;
import com.android.server.chimera.ChimeraCommonUtil;
import com.android.server.chimera.PolicyHandler;
import com.android.server.chimera.SkipReasonLogger;
import com.android.server.am.FreecessController;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class ConservativePolicyHandler extends PolicyHandler {
    public boolean mKillAllOnHomeTrigger;
    public boolean mReclaimOnHomeTrigger;
    public ChimeraCommonUtil.TriggerSource mTriggerSource;

    public static /* synthetic */ boolean lambda$executePolicyInternal$1(int i) {
        return i >= 100 && i < 300;
    }

    public ConservativePolicyHandler(ChimeraAppManager chimeraAppManager, ChimeraStrategy chimeraStrategy, SystemRepository systemRepository, SettingRepository settingRepository, AbnormalFgsDetector abnormalFgsDetector, Looper looper) {
        super(chimeraAppManager, chimeraStrategy, systemRepository, settingRepository, abnormalFgsDetector, looper);
        this.mKillAllOnHomeTrigger = false;
        this.mReclaimOnHomeTrigger = false;
        this.mTriggerSource = ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_LMKD;
    }

    @Override // com.android.server.chimera.PolicyHandler
    public void dump(PrintWriter printWriter, String[] strArr) {
        if ("-a".equals(strArr[0])) {
            dumpCommonInfo(printWriter);
            printWriter.println();
            printWriter.println("************** adjinfo ****************");
            dumpAdjInfo(printWriter);
            printWriter.println();
            printWriter.println("************** appinfo ****************");
            dumpAppInfo(printWriter);
            printWriter.println();
            printWriter.println("************** kill history ****************");
            dumpHistoryBuffer(printWriter);
            return;
        }
        if (strArr.length > 0) {
            String str = strArr[0];
            if (str.equals("info")) {
                dumpCommonInfo(printWriter);
                return;
            }
            if (str.equals("appinfo")) {
                dumpAppInfo(printWriter);
                return;
            }
            if (str.equals("adjinfo")) {
                dumpAdjInfo(printWriter);
            } else if (str.equals("history")) {
                printWriter.println("Chimera Kill History:");
                dumpHistoryBuffer(printWriter);
            }
        }
    }

    @Override // com.android.server.chimera.PolicyHandler
    public int executePolicy(ChimeraCommonUtil.TriggerSource triggerSource, int i) {
        if (!prepareForTrigger(triggerSource)) {
            this.mSystemRepository.log("ConservativePolicyHandler", "executePolicy() - prepareForTrigger fails");
            return 0;
        }
        this.mTriggerSource = triggerSource;
        if (triggerSource == ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_LMKD) {
            if (i > 1) {
                this.mKillAllOnHomeTrigger = true;
            }
            executePolicyInternal(false, triggerSource);
        } else if (triggerSource == ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_HOME_IDLE) {
            if (this.mKillAllOnHomeTrigger) {
                executePolicyInternal(true, triggerSource);
                this.mKillAllOnHomeTrigger = false;
            } else if (isGcReclaimEnabled() && this.mReclaimOnHomeTrigger) {
                performGcAndReclaim();
                this.mReclaimOnHomeTrigger = false;
            }
        }
        return 0;
    }

    public void executePolicyInternal(boolean z, ChimeraCommonUtil.TriggerSource triggerSource) {
        this.mSystemRepository.log("ConservativePolicyHandler", "executePolicy() :" + this.mTriggerSource + ", killAll : " + z);
        this.mSkipReasonLogger.clear();
        List appsToKill = this.mAppManager.getAppsToKill(this.mSkipReasonLogger, this.mChimeraStrategy.getProtectedCountOnHomeTrigger(), triggerSource);
        if (appsToKill == null) {
            this.mSystemRepository.logDebug("ConservativePolicyHandler", "executePolicy() - getAppsToKill return null");
            return;
        }
        ArrayList arrayList = new ArrayList();
        this.mTriggerCnt++;
        int[] iArr = this.mTriggerCntSrc;
        int ordinal = this.mTriggerSource.ordinal();
        iArr[ordinal] = iArr[ordinal] + 1;
        Iterator it = appsToKill.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ChimeraAppInfo chimeraAppInfo = (ChimeraAppInfo) it.next();
            int i = chimeraAppInfo.group;
            if (i < 1) {
                this.mSystemRepository.logDebug("ConservativePolicyHandler", "Invalid app group id");
            } else {
                if (i >= 4) {
                    this.mSystemRepository.logDebug("ConservativePolicyHandler", "killing stopped to group 4");
                    break;
                }
                ProcessStatsAndOomScores create = ProcessStatsAndOomScores.create(chimeraAppInfo, this.mSystemRepository);
                chimeraAppInfo.statsAndOomScores = create;
                if (create != null && !create.hasCachedProcess()) {
                    if (this.mSystemRepository.isOnScreenWindow(chimeraAppInfo.uid)) {
                        markSkipReason(chimeraAppInfo, SkipReasonLogger.Reason.VISIBLE_SCREEN);
                    } else if (this.mWakeLockManager.contains(chimeraAppInfo.packageName)) {
                        markSkipReason(chimeraAppInfo, SkipReasonLogger.Reason.WAKELOCK);
                    } else {
                        if ((chimeraAppInfo.appType & IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES) != 0) {
                            PolicyHandler.ProtectedReason protectedReason = PolicyHandler.ProtectedReason.values()[hasProtectedServices(chimeraAppInfo)];
                            if (protectedReason != PolicyHandler.ProtectedReason.NONE && protectedReason != PolicyHandler.ProtectedReason.ACTIVITY_TIME) {
                                markSkipReason(chimeraAppInfo, SkipReasonLogger.Reason.SERVICE, " reason: " + protectedReason);
                            }
                        }
                        if (hasImportantAdjWithSystemUid(chimeraAppInfo)) {
                            markSkipReason(chimeraAppInfo, SkipReasonLogger.Reason.UID);
                        } else if (isReusedPid(chimeraAppInfo)) {
                            this.mSystemRepository.log("ConservativePolicyHandler", "Skipped by Thread Group Leader condition: " + chimeraAppInfo.packageName);
                        } else if (isSystemPid(chimeraAppInfo)) {
                            this.mSystemRepository.log("ConservativePolicyHandler", "Skipped by system server process id");
                        } else if (!hasProtectedAdjOrProcState(chimeraAppInfo)) {
                            if ((Arrays.stream(chimeraAppInfo.statsAndOomScores.mScores).noneMatch(new IntPredicate() { // from class: com.android.server.chimera.ConservativePolicyHandler$$ExternalSyntheticLambda1
                                @Override // java.util.function.IntPredicate
                                public final boolean test(int i2) {
                                    boolean lambda$executePolicyInternal$1;
                                    lambda$executePolicyInternal$1 = ConservativePolicyHandler.lambda$executePolicyInternal$1(i2);
                                    return lambda$executePolicyInternal$1;
                                }
                            }) ? false : chimeraAppInfo.procList.stream().anyMatch(new Predicate() { // from class: com.android.server.chimera.ConservativePolicyHandler$$ExternalSyntheticLambda0
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    boolean lambda$executePolicyInternal$0;
                                    lambda$executePolicyInternal$0 = ConservativePolicyHandler.this.lambda$executePolicyInternal$0((ChimeraAppInfo.ProcessInfo) obj);
                                    return lambda$executePolicyInternal$0;
                                }
                            })) || isExpiredKillInterval(chimeraAppInfo)) {
                                if (!this.mSystemRepository.isLockTaskPackage(chimeraAppInfo.packageName)) {
                                    calculateKillGain(chimeraAppInfo);
                                    if (chimeraAppInfo.reclaimGain > 0) {
                                        arrayList.add(chimeraAppInfo);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        this.mSkipReasonLogger.printLog("ConservativePolicyHandler");
        if (arrayList.size() > 0) {
            if (z) {
                killAll(arrayList);
            } else {
                killTopProfit(arrayList);
            }
            updateActionStatistics(this.mTriggerSource);
            return;
        }
        this.mNoActionCnt++;
        this.mSystemRepository.log("ConservativePolicyHandler", "killTargetList is empty");
        if (isGcReclaimEnabled()) {
            if (this.mTriggerSource == ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_LMKD) {
                this.mReclaimOnHomeTrigger = true;
            } else {
                performGcAndReclaim();
                this.mReclaimOnHomeTrigger = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$executePolicyInternal$0(ChimeraAppInfo.ProcessInfo processInfo) {
        return this.mAbnormalFgsDetector.isHeavyForegroundService(processInfo.pid, processInfo.pss, processInfo.importance);
    }

    @Override // com.android.server.chimera.PolicyHandler
    public long getQuickReclaimReleaseTarget(long j) {
        return this.mChimeraStrategy.getFreeMemTarget(j) - 307200;
    }

    @Override // com.android.server.chimera.PolicyHandler
    public int hasProtectedServices(ChimeraAppInfo chimeraAppInfo) {
        int hasChimeraProtectedProc;
        if (chimeraAppInfo != null && chimeraAppInfo.statsAndOomScores != null) {
            int i = 0;
            while (true) {
                ProcessStatsAndOomScores processStatsAndOomScores = chimeraAppInfo.statsAndOomScores;
                if (i >= processStatsAndOomScores.mPids.length) {
                    break;
                }
                int i2 = processStatsAndOomScores.mScores[i];
                if (i2 <= 850 && i2 >= -1000 && (hasChimeraProtectedProc = this.mSystemRepository.hasChimeraProtectedProc(((ChimeraAppInfo.ProcessInfo) chimeraAppInfo.procList.get(i)).processName, chimeraAppInfo.uid)) > 0) {
                    return hasChimeraProtectedProc;
                }
                i++;
            }
        }
        return 0;
    }

    public void calculateKillGain(ChimeraAppInfo chimeraAppInfo) {
        chimeraAppInfo.reclaimGain = chimeraAppInfo.pss;
        this.mSystemRepository.logDebug("ConservativePolicyHandler", "PSS gain: " + chimeraAppInfo.reclaimGain + ", hasRestartService: " + chimeraAppInfo.hasRestartService(this.mSystemRepository) + ", " + chimeraAppInfo);
    }

    public void killTopProfit(List list) {
        this.mSystemRepository.log("ConservativePolicyHandler", "killTopProfit");
        calcAppScores(list);
        list.sort(new Comparator() { // from class: com.android.server.chimera.ConservativePolicyHandler$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int lambda$killTopProfit$2;
                lambda$killTopProfit$2 = ConservativePolicyHandler.lambda$killTopProfit$2((ChimeraAppInfo) obj, (ChimeraAppInfo) obj2);
                return lambda$killTopProfit$2;
            }
        });
        if (!this.mSystemRepository.isUserShipBuild()) {
            this.mSystemRepository.log("ConservativePolicyHandler", "after sorting:");
            printAllAppInfo(list);
        }
        if (list.size() > 0) {
            killApp((ChimeraAppInfo) list.get(0), "LMKD MED");
        }
    }

    public static /* synthetic */ int lambda$killTopProfit$2(ChimeraAppInfo chimeraAppInfo, ChimeraAppInfo chimeraAppInfo2) {
        return Float.compare(chimeraAppInfo2.finalScore, chimeraAppInfo.finalScore);
    }

    public void killAll(List list) {
        this.mSystemRepository.log("ConservativePolicyHandler", "killAll");
        if (!this.mSystemRepository.isUserShipBuild()) {
            printAllAppInfo(list);
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            killApp((ChimeraAppInfo) it.next(), "LMKD CRI");
        }
    }

    public void killApp(ChimeraAppInfo chimeraAppInfo, String str) {
        String str2;
        ProcessStatsAndOomScores create = ProcessStatsAndOomScores.create(chimeraAppInfo, this.mSystemRepository);
        chimeraAppInfo.statsAndOomScores = create;
        if (create == null || hasProtectedAdjOrProcState(chimeraAppInfo)) {
            return;
        }
        if (!isService(chimeraAppInfo) || !chimeraAppInfo.hasRestartService(this.mSystemRepository)) {
            str2 = "Kill : ";
        } else if (FreecessController.getInstance().isFreezedPackage(chimeraAppInfo.packageName, this.mSystemRepository.getUserId(chimeraAppInfo.uid))) {
            addRescheduleExceptionPackage(chimeraAppInfo.packageName);
            str2 = "Kill freezedPackage : ";
        } else if (chimeraAppInfo.uid > 10000) {
            addRescheduleExceptionPackage(chimeraAppInfo.packageName);
            str2 = "Kill 3rd-party : ";
        } else {
            RestartImmediatePackages.getInstance().addPackage(chimeraAppInfo.packageName, Boolean.TRUE);
            str2 = "Kill and delay : ";
        }
        this.mSystemRepository.log("ConservativePolicyHandler", str2 + chimeraAppInfo.packageName + " Pss : " + chimeraAppInfo.pss);
        updateKillStatistics(chimeraAppInfo, this.mTriggerSource);
        this.mLastKilledTimeMap.put(chimeraAppInfo.packageName, Long.valueOf(this.mSystemRepository.currentTimeMillis()));
        for (ChimeraAppInfo.ProcessInfo processInfo : chimeraAppInfo.procList) {
            if (this.mAbnormalFgsDetector.isHeavyForegroundService(processInfo.pid, processInfo.pss, processInfo.importance)) {
                this.mAbnormalFgsDetector.putKillableHeavyApp(processInfo.processName, chimeraAppInfo.uid, processInfo.pss);
            }
            this.mSystemRepository.killProcessForChimera(processInfo.processName, chimeraAppInfo.uid, "Chimera " + str);
        }
    }

    @Override // com.android.server.chimera.PolicyHandler
    public boolean hasProtectedAdjOrProcState(ChimeraAppInfo chimeraAppInfo) {
        for (int i : chimeraAppInfo.statsAndOomScores.mScores) {
            if (i < 200) {
                return true;
            }
            if (i < 300) {
                if (i == 200 && (chimeraAppInfo.packageName.startsWith("com.samsung.android.bixby.ondevice") || chimeraAppInfo.packageName.equals("com.sds.emm.sers"))) {
                    return true;
                }
                if ((i == 200 && chimeraAppInfo.curStandbyBucket == 10) || chimeraAppInfo.curStandbyBucket == 5 || isPowerWhitelistedApp(chimeraAppInfo.packageName)) {
                    return true;
                }
            }
            if (i == 830 || i == 700 || i == 300) {
                return true;
            }
        }
        for (int i2 : chimeraAppInfo.statsAndOomScores.mStates) {
            if (i2 == 3 || i2 == 5) {
                return true;
            }
        }
        return false;
    }
}
