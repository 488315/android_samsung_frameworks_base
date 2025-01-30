package com.android.server.am;

import android.app.BackgroundStartPrivileges;
import android.app.IApplicationThread;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ApplicationInfo;
import android.content.pm.ProcessInfo;
import android.content.res.CompatibilityInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.system.OsConstants;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.util.EventLog;
import android.util.Slog;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.app.procstats.ProcessState;
import com.android.internal.app.procstats.ProcessStats;
import com.android.internal.os.Zygote;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.FgThread;
import com.android.server.chimera.umr.KernelMemoryProxy$ReclaimerLog;
import com.android.server.wm.WindowProcessController;
import com.android.server.wm.WindowProcessListener;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class ProcessRecord implements WindowProcessListener {
  public int AMSExceptionFlag;
  public long activeLaunchTime;
  public long appKeepingTime;
  public final boolean appZygote;
  public String callStack;
  public int dhaKeepEmptyFlag;
  public long freezeUnfreezeTimeMARs;
  public boolean frozenMARs;
  public volatile ApplicationInfo info;
  public boolean isAMSException;
  public boolean isActiveLaunch;
  public boolean isCheckForPreLNeeded;
  public boolean isForKeeping;
  public boolean isMLException;
  public boolean isNeverKillException;
  public boolean isSDException;
  public boolean isSDListout;
  public boolean isSDMaxAdj;
  public final boolean isSdkSandbox;
  public final boolean isolated;
  public final ArrayMap mBackgroundStartPrivileges;
  public BackgroundStartPrivileges mBackgroundStartPrivilegesMerged;
  public volatile long mBindApplicationTime;
  public volatile boolean mBindMountPending;
  public volatile boolean mClearedWaitingToKill;
  public CompatibilityInfo mCompat;
  public IBinder.DeathRecipient mDeathRecipient;
  public boolean mDebugging;
  public boolean mDedicated;
  public long[] mDisabledCompatChanges;
  public int mDyingPid;
  public final ProcessErrorStateRecord mErrorState;
  public boolean mExcessiveResourceUsage = false;
  public boolean mFixedAppContextDisplay;
  public int[] mGids;
  public volatile HostingRecord mHostingRecord;
  public boolean mInFullBackup;
  public boolean mInfant;
  public ActiveInstrumentation mInstr;
  public String mInstructionSet;
  public int mIpmLaunchType;
  public volatile boolean mIsCancelFromSeq;
  public boolean mIsRemovedName;
  public String mIsolatedEntryPoint;
  public String[] mIsolatedEntryPointArgs;
  public boolean mKeepProcessAlive;
  public boolean mKeepSEMPrcp;
  public int mKillProcessTimeout;
  public long mKillTime;
  public boolean mKilled;
  public boolean mKilledByAm;
  public long mLastActivityTime;
  public int mLruSeq;
  public volatile int mMountMode;
  public IApplicationThread mOnewayThread;
  public final ProcessCachedOptimizerRecord mOptRecord;
  public int mOverrideDisplayId;
  public boolean mPendingFinishAttach;
  public boolean mPendingStart;
  public volatile boolean mPersistent;
  public int mPid;
  public ArraySet mPkgDeps;
  public final PackageList mPkgList;
  public volatile ProcessRecord mPredecessor;
  public final ActivityManagerGlobalLock mProcLock;
  public volatile boolean mProcessGroupCreated;
  public final ProcessProfileRecord mProfile;
  public final ProcessProviderRecord mProviders;
  public long mRSSresiduePostFCA;
  public final ProcessReceiverRecord mReceivers;
  public volatile boolean mRemoved;
  public int mRenderThreadTid;
  public String mRequiredAbi;
  public volatile String mSeInfo;
  public final ActivityManagerService mService;
  public final ProcessServiceRecord mServices;
  public String mShortStringName;
  public volatile boolean mSkipProcessGroupCreation;
  public boolean mSkipToFinishActivities;
  public volatile long mStartElapsedTime;
  public long mStartSeq;
  public volatile int mStartUid;
  public volatile long mStartUptime;
  public final ProcessStateRecord mState;
  public String mStringName;
  public volatile ProcessRecord mSuccessor;
  public Runnable mSuccessorStartRunnable;
  public boolean mTGLCallbackPosted;
  public IApplicationThread mThread;
  public UidRecord mUidRecord;
  public boolean mUnlocked;
  public boolean mUsingWrapper;
  public boolean mWaitedForDebugger;
  public String mWaitingToKill;
  public final WindowProcessController mWindowProcessController;
  public long mlLaunchTime;
  public final ProcessInfo processInfo;
  public final String processName;
  public final String sdkSandboxClientAppPackage;
  public final String sdkSandboxClientAppVolumeUuid;
  public final int uid;
  public final int userId;

  public void setStartParams(int i, HostingRecord hostingRecord, String str, long j, long j2) {
    this.mStartUid = i;
    this.mHostingRecord = hostingRecord;
    this.mSeInfo = str;
    this.mStartUptime = j;
    this.mStartElapsedTime = j2;
  }

  public void dump(PrintWriter printWriter, String str) {
    long uptimeMillis = SystemClock.uptimeMillis();
    long elapsedRealtime = SystemClock.elapsedRealtime();
    printWriter.print(str);
    printWriter.print("user #");
    printWriter.print(this.userId);
    printWriter.print(" uid=");
    printWriter.print(this.info.uid);
    if (this.uid != this.info.uid) {
      printWriter.print(" ISOLATED uid=");
      printWriter.print(this.uid);
    }
    printWriter.print(" gids={");
    if (this.mGids != null) {
      for (int i = 0; i < this.mGids.length; i++) {
        if (i != 0) {
          printWriter.print(", ");
        }
        printWriter.print(this.mGids[i]);
      }
    }
    printWriter.println("}");
    if (this.processInfo != null) {
      printWriter.print(str);
      printWriter.println("processInfo:");
      if (this.processInfo.deniedPermissions != null) {
        for (int i2 = 0; i2 < this.processInfo.deniedPermissions.size(); i2++) {
          printWriter.print(str);
          printWriter.print("  deny: ");
          printWriter.println((String) this.processInfo.deniedPermissions.valueAt(i2));
        }
      }
      if (this.processInfo.gwpAsanMode != -1) {
        printWriter.print(str);
        printWriter.println("  gwpAsanMode=" + this.processInfo.gwpAsanMode);
      }
      if (this.processInfo.memtagMode != -1) {
        printWriter.print(str);
        printWriter.println("  memtagMode=" + this.processInfo.memtagMode);
      }
    }
    printWriter.print(str);
    printWriter.print("mRequiredAbi=");
    printWriter.print(this.mRequiredAbi);
    printWriter.print(" instructionSet=");
    printWriter.println(this.mInstructionSet);
    if (this.info.className != null) {
      printWriter.print(str);
      printWriter.print("class=");
      printWriter.println(this.info.className);
    }
    if (this.info.manageSpaceActivityName != null) {
      printWriter.print(str);
      printWriter.print("manageSpaceActivityName=");
      printWriter.println(this.info.manageSpaceActivityName);
    }
    printWriter.print(str);
    printWriter.print("dir=");
    printWriter.print(this.info.sourceDir);
    printWriter.print(" publicDir=");
    printWriter.print(this.info.publicSourceDir);
    printWriter.print(" data=");
    printWriter.println(this.info.dataDir);
    this.mPkgList.dump(printWriter, str);
    if (this.mPkgDeps != null) {
      printWriter.print(str);
      printWriter.print("packageDependencies={");
      for (int i3 = 0; i3 < this.mPkgDeps.size(); i3++) {
        if (i3 > 0) {
          printWriter.print(", ");
        }
        printWriter.print((String) this.mPkgDeps.valueAt(i3));
      }
      printWriter.println("}");
    }
    printWriter.print(str);
    printWriter.print("compat=");
    printWriter.println(this.mCompat);
    if (this.mInstr != null) {
      printWriter.print(str);
      printWriter.print("mInstr=");
      printWriter.println(this.mInstr);
    }
    printWriter.print(str);
    printWriter.print("thread=");
    printWriter.println(this.mThread);
    printWriter.print(str);
    printWriter.print("pid=");
    printWriter.println(this.mPid);
    printWriter.print(str);
    printWriter.print("lastActivityTime=");
    TimeUtils.formatDuration(this.mLastActivityTime, uptimeMillis, printWriter);
    printWriter.print(str);
    printWriter.print("startUptimeTime=");
    TimeUtils.formatDuration(this.mStartElapsedTime, uptimeMillis, printWriter);
    printWriter.print(str);
    printWriter.print("startElapsedTime=");
    TimeUtils.formatDuration(this.mStartElapsedTime, elapsedRealtime, printWriter);
    printWriter.println();
    if (this.mPersistent || this.mRemoved) {
      printWriter.print(str);
      printWriter.print("persistent=");
      printWriter.print(this.mPersistent);
      printWriter.print(" removed=");
      printWriter.println(this.mRemoved);
    }
    if (this.mDebugging) {
      printWriter.print(str);
      printWriter.print("mDebugging=");
      printWriter.println(this.mDebugging);
    }
    if (this.mPendingStart) {
      printWriter.print(str);
      printWriter.print("pendingStart=");
      printWriter.println(this.mPendingStart);
    }
    printWriter.print(str);
    printWriter.print("startSeq=");
    printWriter.println(this.mStartSeq);
    printWriter.print(str);
    printWriter.print("mountMode=");
    printWriter.println(DebugUtils.valueToString(Zygote.class, "MOUNT_EXTERNAL_", this.mMountMode));
    if (this.mKilled || this.mKilledByAm || this.mWaitingToKill != null) {
      printWriter.print(str);
      printWriter.print("killed=");
      printWriter.print(this.mKilled);
      printWriter.print(" killedByAm=");
      printWriter.print(this.mKilledByAm);
      printWriter.print(" waitingToKill=");
      printWriter.println(this.mWaitingToKill);
    }
    if (this.mIsolatedEntryPoint != null || this.mIsolatedEntryPointArgs != null) {
      printWriter.print(str);
      printWriter.print("isolatedEntryPoint=");
      printWriter.println(this.mIsolatedEntryPoint);
      printWriter.print(str);
      printWriter.print("isolatedEntryPointArgs=");
      printWriter.println(Arrays.toString(this.mIsolatedEntryPointArgs));
    }
    if (this.mState.getSetProcState() > 10) {
      this.mProfile.dumpCputime(printWriter, str);
    }
    this.mProfile.dumpPss(printWriter, str, uptimeMillis);
    this.mState.dump(printWriter, str, uptimeMillis);
    this.mErrorState.dump(printWriter, str, uptimeMillis);
    this.mServices.dump(printWriter, str, uptimeMillis);
    this.mProviders.dump(printWriter, str, uptimeMillis);
    this.mReceivers.dump(printWriter, str, uptimeMillis);
    this.mOptRecord.dump(printWriter, str, uptimeMillis);
    this.mWindowProcessController.dump(printWriter, str);
    if (!"<empty>".equals(this.callStack)) {
      printWriter.print(str);
      printWriter.print("removed from: ");
      printWriter.println(this.callStack);
    }
    printWriter.print(str);
    printWriter.print("MultiDisplayInfo {");
    if (this.mKeepProcessAlive) {
      printWriter.print("  keepProcessAlive=true");
    }
    printWriter.print("}");
    printWriter.println();
    if (DynamicHiddenApp.PICKED_ADJ_ENABLE) {
      printWriter.print(str);
      printWriter.println("isMLException=");
      printWriter.println(this.isMLException);
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:15:0x0096, code lost:

     if (r1.nativeHeapZeroInitialized == (-1)) goto L21;
  */
  /* JADX WARN: Removed duplicated region for block: B:23:0x00ce  */
  /* JADX WARN: Removed duplicated region for block: B:30:0x00d9  */
  /* JADX WARN: Removed duplicated region for block: B:8:0x0088  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public ProcessRecord(
      ActivityManagerService activityManagerService,
      ApplicationInfo applicationInfo,
      String str,
      int i,
      String str2,
      int i2,
      String str3) {
    ProcessInfo processInfo;
    boolean isSdkSandboxUid;
    PackageList packageList = new PackageList(this);
    this.mPkgList = packageList;
    this.dhaKeepEmptyFlag = 0;
    this.isAMSException = false;
    this.isMLException = false;
    this.isNeverKillException = false;
    this.isSDException = false;
    this.isSDListout = false;
    this.isSDMaxAdj = false;
    this.AMSExceptionFlag = -1;
    this.mBackgroundStartPrivileges = new ArrayMap();
    this.mBackgroundStartPrivilegesMerged = BackgroundStartPrivileges.NONE;
    this.isCheckForPreLNeeded = CoreRune.SYSFW_APP_PREL;
    this.mIsCancelFromSeq = false;
    this.mDedicated = false;
    this.mIsRemovedName = false;
    this.mInfant = false;
    this.mKeepSEMPrcp = false;
    this.callStack = "<empty>";
    this.mIpmLaunchType = -1;
    this.mlLaunchTime = -1L;
    this.mOverrideDisplayId = -1;
    this.mRSSresiduePostFCA = 0L;
    this.mTGLCallbackPosted = false;
    this.mClearedWaitingToKill = false;
    this.mKeepProcessAlive = false;
    this.mKillProcessTimeout = -1;
    this.mFixedAppContextDisplay = false;
    this.mService = activityManagerService;
    this.mProcLock = activityManagerService.mProcLock;
    this.info = applicationInfo;
    PackageManagerInternal packageManagerInternal = activityManagerService.mPackageManagerInt;
    if (packageManagerInternal != null) {
      if (i2 > 0) {
        ArrayMap processesForUid = packageManagerInternal.getProcessesForUid(i2);
        if (processesForUid != null) {
          processInfo = (ProcessInfo) processesForUid.get(str3);
          if (processInfo != null) {
            if (processInfo.deniedPermissions == null) {
              if (processInfo.gwpAsanMode == -1) {
                if (processInfo.memtagMode == -1) {}
              }
            }
          }
        }
        processInfo = null;
        if (processInfo != null) {}
      } else {
        ArrayMap processesForUid2 = packageManagerInternal.getProcessesForUid(i);
        if (processesForUid2 != null) {
          processInfo = (ProcessInfo) processesForUid2.get(str);
          if (processInfo != null) {}
        }
        processInfo = null;
        if (processInfo != null) {}
      }
      this.processInfo = processInfo;
      this.isolated = Process.isIsolated(i);
      isSdkSandboxUid = Process.isSdkSandboxUid(i);
      this.isSdkSandbox = isSdkSandboxUid;
      this.appZygote = UserHandle.getAppId(i) < 90000 && UserHandle.getAppId(i) <= 98999;
      this.uid = i;
      int userId = UserHandle.getUserId(i);
      this.userId = userId;
      this.processName = str;
      this.sdkSandboxClientAppPackage = str2;
      if (!isSdkSandboxUid) {
        ApplicationInfo clientInfoForSdkSandbox = getClientInfoForSdkSandbox();
        this.sdkSandboxClientAppVolumeUuid =
            clientInfoForSdkSandbox != null ? clientInfoForSdkSandbox.volumeUuid : null;
      } else {
        this.sdkSandboxClientAppVolumeUuid = null;
      }
      this.mPersistent = false;
      this.mRemoved = false;
      ProcessProfileRecord processProfileRecord = new ProcessProfileRecord(this);
      this.mProfile = processProfileRecord;
      this.mServices = new ProcessServiceRecord(this);
      this.mProviders = new ProcessProviderRecord(this);
      this.mReceivers = new ProcessReceiverRecord(this);
      this.mErrorState = new ProcessErrorStateRecord(this);
      ProcessStateRecord processStateRecord = new ProcessStateRecord(this);
      this.mState = processStateRecord;
      ProcessCachedOptimizerRecord processCachedOptimizerRecord =
          new ProcessCachedOptimizerRecord(this);
      this.mOptRecord = processCachedOptimizerRecord;
      long uptimeMillis = SystemClock.uptimeMillis();
      processProfileRecord.init(uptimeMillis);
      processCachedOptimizerRecord.init(uptimeMillis);
      processStateRecord.init(uptimeMillis);
      this.mWindowProcessController =
          new WindowProcessController(
              activityManagerService.mActivityTaskManager, this.info, str, i, userId, this, this);
      packageList.put(
          applicationInfo.packageName,
          new ProcessStats.ProcessStateHolder(applicationInfo.longVersionCode));
    }
    processInfo = null;
    this.processInfo = processInfo;
    this.isolated = Process.isIsolated(i);
    isSdkSandboxUid = Process.isSdkSandboxUid(i);
    this.isSdkSandbox = isSdkSandboxUid;
    this.appZygote = UserHandle.getAppId(i) < 90000 && UserHandle.getAppId(i) <= 98999;
    this.uid = i;
    int userId2 = UserHandle.getUserId(i);
    this.userId = userId2;
    this.processName = str;
    this.sdkSandboxClientAppPackage = str2;
    if (!isSdkSandboxUid) {}
    this.mPersistent = false;
    this.mRemoved = false;
    ProcessProfileRecord processProfileRecord2 = new ProcessProfileRecord(this);
    this.mProfile = processProfileRecord2;
    this.mServices = new ProcessServiceRecord(this);
    this.mProviders = new ProcessProviderRecord(this);
    this.mReceivers = new ProcessReceiverRecord(this);
    this.mErrorState = new ProcessErrorStateRecord(this);
    ProcessStateRecord processStateRecord2 = new ProcessStateRecord(this);
    this.mState = processStateRecord2;
    ProcessCachedOptimizerRecord processCachedOptimizerRecord2 =
        new ProcessCachedOptimizerRecord(this);
    this.mOptRecord = processCachedOptimizerRecord2;
    long uptimeMillis2 = SystemClock.uptimeMillis();
    processProfileRecord2.init(uptimeMillis2);
    processCachedOptimizerRecord2.init(uptimeMillis2);
    processStateRecord2.init(uptimeMillis2);
    this.mWindowProcessController =
        new WindowProcessController(
            activityManagerService.mActivityTaskManager, this.info, str, i, userId2, this, this);
    packageList.put(
        applicationInfo.packageName,
        new ProcessStats.ProcessStateHolder(applicationInfo.longVersionCode));
  }

  public UidRecord getUidRecord() {
    return this.mUidRecord;
  }

  public void setUidRecord(UidRecord uidRecord) {
    this.mUidRecord = uidRecord;
  }

  public PackageList getPkgList() {
    return this.mPkgList;
  }

  public ArraySet getPkgDeps() {
    return this.mPkgDeps;
  }

  public void setPkgDeps(ArraySet arraySet) {
    this.mPkgDeps = arraySet;
  }

  public int getPid() {
    return this.mPid;
  }

  public void setPid(int i) {
    this.mPid = i;
    this.mWindowProcessController.setPid(i);
    this.mShortStringName = null;
    this.mStringName = null;
    synchronized (this.mProfile.mProfilerLock) {
      this.mProfile.setPid(i);
    }
  }

  public int getSetAdj() {
    return this.mState.getSetAdj();
  }

  public IApplicationThread getThread() {
    return this.mThread;
  }

  public void setIsforKeeping(boolean z) {
    this.isForKeeping = z;
  }

  public boolean isForKeeping() {
    return this.isForKeeping;
  }

  public void setAppKeepingTime(long j) {
    this.appKeepingTime = j;
  }

  public IApplicationThread getOnewayThread() {
    return this.mOnewayThread;
  }

  public int getSetProcState() {
    return this.mState.getSetProcState();
  }

  public void makeActive(
      IApplicationThread iApplicationThread, ProcessStatsService processStatsService) {
    this.mProfile.onProcessActive(iApplicationThread, processStatsService);
    this.mThread = iApplicationThread;
    if (this.mPid == Process.myPid()) {
      this.mOnewayThread =
          new SameProcessApplicationThread(iApplicationThread, FgThread.getHandler());
    } else {
      this.mOnewayThread = iApplicationThread;
    }
    this.mWindowProcessController.setThread(iApplicationThread);
  }

  public void makeInactive(ProcessStatsService processStatsService) {
    this.mThread = null;
    this.mOnewayThread = null;
    this.mWindowProcessController.setThread(null);
    this.mProfile.onProcessInactive(processStatsService);
  }

  public int getDyingPid() {
    return this.mDyingPid;
  }

  public void setDyingPid(int i) {
    this.mDyingPid = i;
  }

  public void setGids(int[] iArr) {
    this.mGids = iArr;
  }

  public void setRequiredAbi(String str) {
    this.mRequiredAbi = str;
    this.mWindowProcessController.setRequiredAbi(str);
  }

  public void setInstructionSet(String str) {
    this.mInstructionSet = str;
  }

  public void setPersistent(boolean z) {
    this.mPersistent = z;
    this.mWindowProcessController.setPersistent(z);
  }

  public boolean isPersistent() {
    return this.mPersistent;
  }

  public boolean isPendingStart() {
    return this.mPendingStart;
  }

  public void setPendingStart(boolean z) {
    this.mPendingStart = z;
  }

  public void setPendingFinishAttach(boolean z) {
    this.mPendingFinishAttach = z;
  }

  public boolean isPendingFinishAttach() {
    return this.mPendingFinishAttach;
  }

  public long getStartSeq() {
    return this.mStartSeq;
  }

  public void setStartSeq(long j) {
    this.mStartSeq = j;
  }

  public HostingRecord getHostingRecord() {
    return this.mHostingRecord;
  }

  public String getSeInfo() {
    return this.mSeInfo;
  }

  public long getStartUptime() {
    return this.mStartUptime;
  }

  public long getStartTime() {
    return this.mStartUptime;
  }

  public long getStartElapsedTime() {
    return this.mStartElapsedTime;
  }

  public long getBindApplicationTime() {
    return this.mBindApplicationTime;
  }

  public void setBindApplicationTime(long j) {
    this.mBindApplicationTime = j;
  }

  public int getStartUid() {
    return this.mStartUid;
  }

  public int getMountMode() {
    return this.mMountMode;
  }

  public void setMountMode(int i) {
    this.mMountMode = i;
  }

  public boolean isBindMountPending() {
    return this.mBindMountPending;
  }

  public void setBindMountPending(boolean z) {
    this.mBindMountPending = z;
  }

  public boolean isUnlocked() {
    return this.mUnlocked;
  }

  public void setUnlocked(boolean z) {
    this.mUnlocked = z;
  }

  public int getRenderThreadTid() {
    return this.mRenderThreadTid;
  }

  public void setRenderThreadTid(int i) {
    this.mRenderThreadTid = i;
  }

  public CompatibilityInfo getCompat() {
    return this.mCompat;
  }

  public void setCompat(CompatibilityInfo compatibilityInfo) {
    this.mCompat = compatibilityInfo;
  }

  public long[] getDisabledCompatChanges() {
    return this.mDisabledCompatChanges;
  }

  public void setDisabledCompatChanges(long[] jArr) {
    this.mDisabledCompatChanges = jArr;
  }

  public void unlinkDeathRecipient() {
    IApplicationThread iApplicationThread;
    if (this.mDeathRecipient != null && (iApplicationThread = this.mThread) != null) {
      iApplicationThread.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
    }
    this.mDeathRecipient = null;
  }

  public void setDeathRecipient(IBinder.DeathRecipient deathRecipient) {
    this.mDeathRecipient = deathRecipient;
  }

  public IBinder.DeathRecipient getDeathRecipient() {
    return this.mDeathRecipient;
  }

  public void setActiveInstrumentation(ActiveInstrumentation activeInstrumentation) {
    this.mInstr = activeInstrumentation;
    boolean z = activeInstrumentation != null;
    this.mWindowProcessController.setInstrumenting(
        z,
        z ? activeInstrumentation.mSourceUid : -1,
        z && activeInstrumentation.mHasBackgroundActivityStartsPermission);
  }

  public ActiveInstrumentation getActiveInstrumentation() {
    return this.mInstr;
  }

  public boolean isKilledByAm() {
    return this.mKilledByAm;
  }

  public boolean isSkipTrimMemory() {
    return this.mKilledByAm || this.isForKeeping;
  }

  public void setKilledByAm(boolean z) {
    this.mKilledByAm = z;
  }

  public boolean isKilled() {
    return this.mKilled;
  }

  public void setKilled(boolean z) {
    this.mKilled = z;
  }

  public long getKillTime() {
    return this.mKillTime;
  }

  public String getWaitingToKill() {
    return this.mWaitingToKill;
  }

  public void setWaitingToKill(String str) {
    this.mWaitingToKill = str;
    this.mClearedWaitingToKill = false;
  }

  @Override // com.android.server.wm.WindowProcessListener
  public boolean isRemoved() {
    return this.mRemoved;
  }

  public void setRemoved(boolean z) {
    this.mRemoved = z;
  }

  public boolean isDebugging() {
    return this.mDebugging;
  }

  public ApplicationInfo getClientInfoForSdkSandbox() {
    if (!this.isSdkSandbox || this.sdkSandboxClientAppPackage == null) {
      throw new IllegalStateException("getClientInfoForSdkSandbox called for non-sandbox process");
    }
    return this.mService
        .getPackageManagerInternal()
        .getApplicationInfo(this.sdkSandboxClientAppPackage, 0L, 1000, this.userId);
  }

  public boolean isDebuggable() {
    if ((this.info.flags & 2) != 0) {
      return true;
    }
    if (!this.isSdkSandbox) {
      return false;
    }
    ApplicationInfo clientInfoForSdkSandbox = getClientInfoForSdkSandbox();
    return (clientInfoForSdkSandbox == null || (clientInfoForSdkSandbox.flags & 2) == 0)
        ? false
        : true;
  }

  public void setDebugging(boolean z) {
    this.mDebugging = z;
    this.mWindowProcessController.setDebugging(z);
  }

  public boolean hasWaitedForDebugger() {
    return this.mWaitedForDebugger;
  }

  public void setWaitedForDebugger(boolean z) {
    this.mWaitedForDebugger = z;
  }

  public long getLastActivityTime() {
    return this.mLastActivityTime;
  }

  public void setLastActivityTime(long j) {
    this.mLastActivityTime = j;
  }

  public boolean isUsingWrapper() {
    return this.mUsingWrapper;
  }

  public void setUsingWrapper(boolean z) {
    this.mUsingWrapper = z;
    this.mWindowProcessController.setUsingWrapper(z);
  }

  public int getLruSeq() {
    return this.mLruSeq;
  }

  public void setLruSeq(int i) {
    this.mLruSeq = i;
  }

  public String getIsolatedEntryPoint() {
    return this.mIsolatedEntryPoint;
  }

  public void setIsolatedEntryPoint(String str) {
    this.mIsolatedEntryPoint = str;
  }

  public String[] getIsolatedEntryPointArgs() {
    return this.mIsolatedEntryPointArgs;
  }

  public void setIsolatedEntryPointArgs(String[] strArr) {
    this.mIsolatedEntryPointArgs = strArr;
  }

  public boolean isInFullBackup() {
    return this.mInFullBackup;
  }

  public void setInFullBackup(boolean z) {
    this.mInFullBackup = z;
  }

  public boolean isCached() {
    return this.mState.isCached();
  }

  public boolean hasActivities() {
    return this.mWindowProcessController.hasActivities();
  }

  public boolean hasActivitiesOrRecentTasks() {
    return this.mWindowProcessController.hasActivitiesOrRecentTasks();
  }

  public boolean hasRecentTasks() {
    return this.mWindowProcessController.hasRecentTasks();
  }

  public boolean hasRestartService() {
    int numberOfRunningServices = this.mServices.numberOfRunningServices();
    for (int i = 0; i < numberOfRunningServices; i++) {
      if (!this.mServices.getRunningServiceAt(i).canStopIfKilled(false)) {
        return true;
      }
    }
    return false;
  }

  public ApplicationInfo getApplicationInfo() {
    return this.info;
  }

  public boolean onCleanupApplicationRecordLSP(
      ProcessStatsService processStatsService, boolean z, boolean z2) {
    this.mErrorState.onCleanupApplicationRecordLSP();
    resetPackageList(processStatsService);
    if (z2) {
      unlinkDeathRecipient();
    }
    makeInactive(processStatsService);
    setWaitingToKill(null);
    this.mState.onCleanupApplicationRecordLSP();
    this.mServices.onCleanupApplicationRecordLocked();
    this.mReceivers.onCleanupApplicationRecordLocked();
    return this.mProviders.onCleanupApplicationRecordLocked(z);
  }

  public boolean isInterestingToUserLocked() {
    if (this.mWindowProcessController.isInterestingToUser()) {
      return true;
    }
    return this.mServices.hasForegroundServices();
  }

  public void scheduleCrashLocked(String str, int i, Bundle bundle) {
    if (this.mKilledByAm || this.mThread == null) {
      return;
    }
    if (this.mPid == Process.myPid()) {
      Slog.w("ActivityManager", "scheduleCrash: trying to crash system process!");
      return;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        this.mThread.scheduleCrash(str, i, bundle);
      } catch (RemoteException unused) {
        killLocked("scheduleCrash for '" + str + "' failed", 4, true);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void killLocked(String str, int i, boolean z) {
    killLocked(str, i, 0, z, true);
  }

  public void killLocked(String str, int i, int i2, boolean z) {
    killLocked(str, str, i, i2, z, true);
  }

  public void killLocked(String str, String str2, int i, int i2, boolean z) {
    killLocked(str, str2, i, i2, z, true);
  }

  public void killLocked(String str, int i, int i2, boolean z, boolean z2) {
    killLocked(str, str, i, i2, z, z2);
  }

  public void killLocked(String str, String str2, int i, int i2, boolean z, boolean z2) {
    int i3 = this.mPid;
    if (i3 > 0 && Process.getThreadGroupLeader(i3) != this.mPid) {
      Slog.w(
          "ActivityManager",
          "Not TGL " + this.mPid + XmlUtils.STRING_ARRAY_SEPARATOR + Debug.getCallers(2));
      if (this.mTGLCallbackPosted) {
        return;
      }
      this.mTGLCallbackPosted = true;
      this.mService.mHandler.postDelayed(
          new Runnable() { // from class:
                           // com.android.server.am.ProcessRecord$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              ProcessRecord.this.lambda$killLocked$0();
            }
          },
          1000L);
      return;
    }
    if (this.mKilledByAm) {
      return;
    }
    Trace.traceBegin(64L, "kill");
    if (i == 6 && this.mErrorState.getAnrAnnotation() != null) {
      str2 = str2 + ": " + this.mErrorState.getAnrAnnotation();
    }
    if (this.mService != null && (z || this.info.uid == this.mService.mCurOomAdjUid)) {
      this.mService.reportUidInfoMessageLocked(
          "ActivityManager",
          "Killing " + toShortString() + " (adj " + this.mState.getSetAdj() + "): " + str,
          this.info.uid);
    }
    this.mOptRecord.setPendingFreeze(false);
    this.mOptRecord.setFrozen(false);
    if (this.mPid > 0) {
      this.mService.mProcessList.noteAppKill(this, i, i2, str2);
      EventLog.writeEvent(
          30023,
          Integer.valueOf(this.userId),
          Integer.valueOf(this.mPid),
          this.processName,
          Integer.valueOf(this.mState.getSetAdj()),
          str);
      KernelMemoryProxy$ReclaimerLog.write(
          "B|killProcessQuiet comm=" + this.processName + "(" + this.mPid + ")", false);
      long lastPss = this.mProfile.getLastPss();
      Process.killProcessQuiet(this.mPid);
      killProcessGroupIfNecessaryLocked(z2);
      KernelMemoryProxy$ReclaimerLog.write("E|killProcessQuiet pss=" + lastPss, false);
    } else {
      this.mPendingStart = false;
    }
    if (!this.mPersistent) {
      ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
      ActivityManagerService.boostPriorityForProcLockedSection();
      synchronized (activityManagerGlobalLock) {
        try {
          this.mKilled = true;
          this.mKilledByAm = true;
          this.mKillTime = SystemClock.uptimeMillis();
        } catch (Throwable th) {
          ActivityManagerService.resetPriorityAfterProcLockedSection();
          throw th;
        }
      }
      ActivityManagerService.resetPriorityAfterProcLockedSection();
    }
    Trace.traceEnd(64L);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$killLocked$0() {
    ActivityManagerService activityManagerService = this.mService;
    ActivityManagerService.boostPriorityForLockedSection();
    synchronized (activityManagerService) {
      try {
        if (!this.mKilled) {
          this.mService.appDiedLocked(this, "TGL@");
        }
      } catch (Throwable th) {
        ActivityManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    ActivityManagerService.resetPriorityAfterLockedSection();
  }

  public void killProcessGroupIfNecessaryLocked(boolean z) {
    boolean z2;
    boolean z3 = true;
    if (this.mHostingRecord != null
        && (this.mHostingRecord.usesWebviewZygote() || this.mHostingRecord.usesAppZygote())) {
      synchronized (this) {
        z2 = this.mProcessGroupCreated;
        if (!z2) {
          this.mSkipProcessGroupCreation = true;
        }
      }
      z3 = z2;
    }
    if (z3) {
      if (z) {
        ProcessList.killProcessGroup(this.uid, this.mPid);
      } else {
        Process.sendSignalToProcessGroup(this.uid, this.mPid, OsConstants.SIGKILL);
        ProcessList.CleanUpCgroup(this.uid, this.mPid);
      }
    }
  }

  @Override // com.android.server.wm.WindowProcessListener
  public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
    dumpDebug(protoOutputStream, j, -1);
  }

  public void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
    long start = protoOutputStream.start(j);
    protoOutputStream.write(1120986464257L, this.mPid);
    protoOutputStream.write(1138166333442L, this.processName);
    protoOutputStream.write(1120986464259L, this.info.uid);
    if (UserHandle.getAppId(this.info.uid) >= 10000) {
      protoOutputStream.write(1120986464260L, this.userId);
      protoOutputStream.write(1120986464261L, UserHandle.getAppId(this.info.uid));
    }
    if (this.uid != this.info.uid) {
      protoOutputStream.write(1120986464262L, UserHandle.getAppId(this.uid));
    }
    protoOutputStream.write(1133871366151L, this.mPersistent);
    if (i >= 0) {
      protoOutputStream.write(1120986464264L, i);
    }
    protoOutputStream.end(start);
  }

  public String toShortString() {
    String str = this.mShortStringName;
    if (str != null) {
      return str;
    }
    StringBuilder sb = new StringBuilder(128);
    toShortString(sb);
    String sb2 = sb.toString();
    this.mShortStringName = sb2;
    return sb2;
  }

  public void toShortString(StringBuilder sb) {
    sb.append(this.mPid);
    sb.append(':');
    sb.append(this.processName);
    sb.append('/');
    if (this.info.uid < 10000) {
      sb.append(this.uid);
      return;
    }
    sb.append('u');
    sb.append(this.userId);
    int appId = UserHandle.getAppId(this.info.uid);
    if (appId >= 10000) {
      sb.append('a');
      sb.append(appId - 10000);
    } else {
      sb.append('s');
      sb.append(appId);
    }
    if (this.uid != this.info.uid) {
      sb.append('i');
      sb.append(UserHandle.getAppId(this.uid) - 99000);
    }
  }

  public String toString() {
    String str = this.mStringName;
    if (str != null) {
      return str;
    }
    StringBuilder sb = new StringBuilder(128);
    sb.append("ProcessRecord{");
    sb.append(Integer.toHexString(System.identityHashCode(this)));
    sb.append(' ');
    toShortString(sb);
    sb.append('}');
    String sb2 = sb.toString();
    this.mStringName = sb2;
    return sb2;
  }

  public boolean addPackage(String str, long j, ProcessStatsService processStatsService) {
    synchronized (processStatsService.mLock) {
      synchronized (this.mPkgList) {
        if (this.mPkgList.containsKey(str)) {
          return false;
        }
        ProcessStats.ProcessStateHolder processStateHolder = new ProcessStats.ProcessStateHolder(j);
        ProcessState baseProcessTracker = this.mProfile.getBaseProcessTracker();
        if (baseProcessTracker != null) {
          processStatsService.updateProcessStateHolderLocked(
              processStateHolder, str, this.info.uid, j, this.processName);
          this.mPkgList.put(str, processStateHolder);
          ProcessState processState = processStateHolder.state;
          if (processState != baseProcessTracker) {
            processState.makeActive();
          }
        } else {
          this.mPkgList.put(str, processStateHolder);
        }
        return true;
      }
    }
  }

  public void resetPackageList(ProcessStatsService processStatsService) {
    PackageList packageList;
    synchronized (processStatsService.mLock) {
      final ProcessState baseProcessTracker = this.mProfile.getBaseProcessTracker();
      PackageList packageList2 = this.mPkgList;
      try {
        synchronized (packageList2) {
          try {
            int size = this.mPkgList.size();
            if (baseProcessTracker != null) {
              baseProcessTracker.setState(
                  -1,
                  processStatsService.getMemFactorLocked(),
                  SystemClock.uptimeMillis(),
                  this.mPkgList.getPackageListLocked());
              if (size != 1) {
                this.mPkgList.forEachPackageProcessStats(
                    new Consumer() { // from class:
                                     // com.android.server.am.ProcessRecord$$ExternalSyntheticLambda0
                      @Override // java.util.function.Consumer
                      public final void accept(Object obj) {
                        ProcessRecord.lambda$resetPackageList$1(
                            baseProcessTracker, (ProcessStats.ProcessStateHolder) obj);
                      }
                    });
                this.mPkgList.clear();
                ProcessStats.ProcessStateHolder processStateHolder =
                    new ProcessStats.ProcessStateHolder(this.info.longVersionCode);
                packageList = packageList2;
                processStatsService.updateProcessStateHolderLocked(
                    processStateHolder,
                    this.info.packageName,
                    this.info.uid,
                    this.info.longVersionCode,
                    this.processName);
                this.mPkgList.put(this.info.packageName, processStateHolder);
                ProcessState processState = processStateHolder.state;
                if (processState != baseProcessTracker) {
                  processState.makeActive();
                }
              } else {
                packageList = packageList2;
              }
            } else {
              packageList = packageList2;
              if (size != 1) {
                this.mPkgList.clear();
                this.mPkgList.put(
                    this.info.packageName,
                    new ProcessStats.ProcessStateHolder(this.info.longVersionCode));
              }
            }
          } catch (Throwable th) {
            th = th;
            throw th;
          }
        }
      } catch (Throwable th2) {
        th = th2;
      }
      throw th;
    }
  }

  public static /* synthetic */ void lambda$resetPackageList$1(
      ProcessState processState, ProcessStats.ProcessStateHolder processStateHolder) {
    ProcessState processState2 = processStateHolder.state;
    if (processState2 == null || processState2 == processState) {
      return;
    }
    processState2.makeInactive();
  }

  public String[] getPackageList() {
    return this.mPkgList.getPackageList();
  }

  public List getPackageListWithVersionCode() {
    return this.mPkgList.getPackageListWithVersionCode();
  }

  public WindowProcessController getWindowProcessController() {
    return this.mWindowProcessController;
  }

  public void addOrUpdateBackgroundStartPrivileges(
      Binder binder, BackgroundStartPrivileges backgroundStartPrivileges) {
    Objects.requireNonNull(binder, "entity");
    Objects.requireNonNull(backgroundStartPrivileges, "backgroundStartPrivileges");
    Preconditions.checkArgument(
        backgroundStartPrivileges.allowsAny(), "backgroundStartPrivileges does not allow anything");
    this.mWindowProcessController.addOrUpdateBackgroundStartPrivileges(
        binder, backgroundStartPrivileges);
    setBackgroundStartPrivileges(binder, backgroundStartPrivileges);
  }

  public void removeBackgroundStartPrivileges(Binder binder) {
    Objects.requireNonNull(binder, "entity");
    this.mWindowProcessController.removeBackgroundStartPrivileges(binder);
    setBackgroundStartPrivileges(binder, null);
  }

  public BackgroundStartPrivileges getBackgroundStartPrivileges() {
    BackgroundStartPrivileges backgroundStartPrivileges;
    synchronized (this.mBackgroundStartPrivileges) {
      if (this.mBackgroundStartPrivilegesMerged == null) {
        this.mBackgroundStartPrivilegesMerged = BackgroundStartPrivileges.NONE;
        for (int size = this.mBackgroundStartPrivileges.size() - 1; size >= 0; size--) {
          this.mBackgroundStartPrivilegesMerged =
              this.mBackgroundStartPrivilegesMerged.merge(
                  (BackgroundStartPrivileges) this.mBackgroundStartPrivileges.valueAt(size));
        }
      }
      backgroundStartPrivileges = this.mBackgroundStartPrivilegesMerged;
    }
    return backgroundStartPrivileges;
  }

  /* JADX WARN: Code restructure failed: missing block: B:17:0x001a, code lost:

     if (r6 != ((android.app.BackgroundStartPrivileges) r4.mBackgroundStartPrivileges.put(r5, r6))) goto L12;
  */
  /* JADX WARN: Removed duplicated region for block: B:10:0x001e A[Catch: all -> 0x0023, TryCatch #0 {, blocks: (B:6:0x0007, B:10:0x001e, B:11:0x0021, B:16:0x0012), top: B:4:0x0005 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void setBackgroundStartPrivileges(
      Binder binder, BackgroundStartPrivileges backgroundStartPrivileges) {
    synchronized (this.mBackgroundStartPrivileges) {
      boolean z = true;
      if (backgroundStartPrivileges == null) {
        if (this.mBackgroundStartPrivileges.remove(binder) != null) {
          if (z) {
            this.mBackgroundStartPrivilegesMerged = null;
          }
        }
        z = false;
        if (z) {}
      }
    }
  }

  @Override // com.android.server.wm.WindowProcessListener
  public void clearProfilerIfNeeded() {
    synchronized (this.mService.mAppProfiler.mProfilerLock) {
      this.mService.mAppProfiler.clearProfilerLPf();
    }
  }

  @Override // com.android.server.wm.WindowProcessListener
  public void updateServiceConnectionActivities() {
    ActivityManagerService activityManagerService = this.mService;
    ActivityManagerService.boostPriorityForLockedSection();
    synchronized (activityManagerService) {
      try {
        this.mService.mServices.updateServiceConnectionActivitiesLocked(this.mServices);
      } catch (Throwable th) {
        ActivityManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    ActivityManagerService.resetPriorityAfterLockedSection();
  }

  @Override // com.android.server.wm.WindowProcessListener
  public void setPendingUiClean(boolean z) {
    ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
    ActivityManagerService.boostPriorityForProcLockedSection();
    synchronized (activityManagerGlobalLock) {
      try {
        this.mProfile.setPendingUiClean(z);
      } catch (Throwable th) {
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        throw th;
      }
    }
    ActivityManagerService.resetPriorityAfterProcLockedSection();
  }

  @Override // com.android.server.wm.WindowProcessListener
  public void setPendingUiCleanAndForceProcessStateUpTo(int i) {
    ActivityManagerService activityManagerService = this.mService;
    ActivityManagerService.boostPriorityForLockedSection();
    synchronized (activityManagerService) {
      try {
        setPendingUiClean(true);
        this.mState.forceProcessStateUpTo(i);
      } catch (Throwable th) {
        ActivityManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    ActivityManagerService.resetPriorityAfterLockedSection();
  }

  @Override // com.android.server.wm.WindowProcessListener
  public void updateProcessInfo(boolean z, boolean z2, boolean z3) {
    ActivityManagerService activityManagerService = this.mService;
    ActivityManagerService.boostPriorityForLockedSection();
    synchronized (activityManagerService) {
      if (z) {
        try {
          this.mService.mServices.updateServiceConnectionActivitiesLocked(this.mServices);
        } catch (Throwable th) {
          ActivityManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      if (this.mThread == null) {
        ActivityManagerService.resetPriorityAfterLockedSection();
        return;
      }
      this.mService.updateLruProcessLocked(this, z2, null);
      if (z3) {
        this.mService.updateOomAdjLocked(this, 1);
      }
      ActivityManagerService.resetPriorityAfterLockedSection();
    }
  }

  @Override // com.android.server.wm.WindowProcessListener
  public long getCpuTime() {
    return this.mService.mAppProfiler.getCpuTimeForPid(this.mPid);
  }

  public long getCpuDelayTime() {
    return this.mService.mAppProfiler.getCpuDelayTimeForPid(this.mPid);
  }

  @Override // com.android.server.wm.WindowProcessListener
  public void onStartActivity(int i, boolean z, String str, long j) {
    ActivityManagerService activityManagerService = this.mService;
    ActivityManagerService.boostPriorityForLockedSection();
    synchronized (activityManagerService) {
      try {
        this.mWaitingToKill = null;
        this.mClearedWaitingToKill = false;
        if (z) {
          synchronized (this.mService.mAppProfiler.mProfilerLock) {
            this.mService.mAppProfiler.setProfileProcLPf(this);
          }
        }
        if (str != null) {
          addPackage(str, j, this.mService.mProcessStats);
        }
        updateProcessInfo(false, true, true);
        setPendingUiClean(true);
        this.mState.setHasShownUi(true);
        this.mState.forceProcessStateUpTo(i);
      } catch (Throwable th) {
        ActivityManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    ActivityManagerService.resetPriorityAfterLockedSection();
  }

  @Override // com.android.server.wm.WindowProcessListener
  public void appDied(String str) {
    ActivityManagerService activityManagerService = this.mService;
    ActivityManagerService.boostPriorityForLockedSection();
    synchronized (activityManagerService) {
      try {
        this.mService.appDiedLocked(this, str);
      } catch (Throwable th) {
        ActivityManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    ActivityManagerService.resetPriorityAfterLockedSection();
  }

  @Override // com.android.server.wm.WindowProcessListener
  public void setRunningRemoteAnimation(boolean z) {
    if (this.mPid == Process.myPid()) {
      Slog.wtf("ActivityManager", "system can't run remote animation");
      return;
    }
    ActivityManagerService activityManagerService = this.mService;
    ActivityManagerService.boostPriorityForLockedSection();
    synchronized (activityManagerService) {
      try {
        this.mState.setRunningRemoteAnimation(z);
      } catch (Throwable th) {
        ActivityManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    ActivityManagerService.resetPriorityAfterLockedSection();
  }

  public long getInputDispatchingTimeoutMillis() {
    return this.mWindowProcessController.getInputDispatchingTimeoutMillis();
  }

  public int getProcessClassEnum() {
    if (this.mPid == ActivityManagerService.MY_PID) {
      return 3;
    }
    if (this.info == null) {
      return 0;
    }
    return (this.info.flags & 1) != 0 ? 2 : 1;
  }

  public List getLruProcessList() {
    return this.mService.mProcessList.getLruProcessesLOSP();
  }

  @Override // com.android.server.wm.WindowProcessListener
  public void setClearWaitingToKill() {
    this.mClearedWaitingToKill = true;
  }

  public void setActiveLaunch(boolean z) {
    this.isActiveLaunch = z;
  }

  public boolean isActiveLaunch() {
    return this.isActiveLaunch;
  }

  public void setActiveLaunchTime(long j) {
    this.activeLaunchTime = j;
  }

  public void setIpmLaunchType(int i) {
    this.mIpmLaunchType = i;
  }

  public int getIpmLaunchtype() {
    return this.mIpmLaunchType;
  }

  public long getMlLaunchTime() {
    return this.mlLaunchTime;
  }

  public void setMlLaunchTime(long j) {
    this.mlLaunchTime = j;
  }

  public void setOverrideDisplayId(int i) {
    this.mOverrideDisplayId = i;
    this.mWindowProcessController.setOverrideDisplayId(i);
  }

  public int getDisplayId() {
    return this.mOverrideDisplayId;
  }

  @Override // com.android.server.wm.WindowProcessListener
  public boolean skipToFinishActivities() {
    return this.mSkipToFinishActivities;
  }

  public void setSkipToFinishActivities(boolean z) {
    String str;
    if (this.mSkipToFinishActivities != z) {
      this.mSkipToFinishActivities = z;
      StringBuilder sb = new StringBuilder();
      sb.append("setSkipToFinishActivities: ");
      sb.append(z);
      sb.append(", ");
      sb.append(this);
      if (CoreRune.SAFE_DEBUG) {
        str = ", Callers=" + Debug.getCallers(5);
      } else {
        str = "";
      }
      sb.append(str);
      Slog.d("ActivityManager", sb.toString());
    }
  }

  public void setKeepProcessAlive(boolean z) {
    this.mKeepProcessAlive = z;
    this.mWindowProcessController.setKeepProcessAlive(z);
  }

  public void setKillProcessTimeout(int i) {
    this.mKillProcessTimeout = i;
  }

  public int getKillProcessTimeout() {
    return this.mKillProcessTimeout;
  }

  public void setFixedAppContextDisplay(boolean z) {
    this.mFixedAppContextDisplay = z;
  }

  public boolean isFixedAppContextDisplay() {
    return this.mFixedAppContextDisplay;
  }

  public boolean isExcessiveResourceUsage() {
    return this.mExcessiveResourceUsage;
  }

  public void setExcessiveResourceUsage(boolean z) {
    this.mExcessiveResourceUsage = z;
  }
}
