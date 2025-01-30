package com.android.server.wm;

import android.app.ActivityThread;
import android.app.BackgroundStartPrivileges;
import android.app.IActivityController;
import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.app.servertransaction.ConfigurationChangeItem;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Build;
import android.os.InputConstants;
import android.os.LocaleList;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;
import android.p005os.IInstalld;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.Watchdog;
import com.android.server.location.gnss.hal.GnssNative;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class WindowProcessController extends ConfigurationContainer
    implements ConfigurationContainerListener {
  public boolean mAdjustBindAppToDexConfig;
  public final ActivityTaskManagerService mAtm;
  public final BackgroundLaunchProcessController mBgLaunchController;
  public ActivityRecord mConfigActivityRecord;
  public Task mConfigTask;
  public volatile boolean mCrashing;
  public volatile int mCurSchedGroup;
  public volatile boolean mDebugging;
  public DisplayArea mDisplayArea;
  public volatile long mFgInteractionTime;
  public volatile boolean mHasActivities;
  public volatile boolean mHasCachedConfiguration;
  public volatile boolean mHasClientActivities;
  public volatile boolean mHasForegroundServices;
  public volatile boolean mHasImeService;
  public volatile boolean mHasOverlayUi;
  public boolean mHasPendingConfigurationChange;
  public volatile boolean mHasRecentTasks;
  public volatile boolean mHasTopUi;
  public ArrayList mInactiveActivities;
  public final ApplicationInfo mInfo;
  public volatile boolean mInstrumenting;
  public volatile boolean mInstrumentingWithBackgroundActivityStartPrivileges;
  public volatile long mInteractionEventTime;
  public volatile boolean mIsActivityConfigOverrideAllowed;
  public boolean mIsPrelScheduleGroupOverride;
  public volatile long mLastActivityFinishTime;
  public volatile long mLastActivityLaunchTime;
  public final WindowProcessListener mListener;
  public final String mName;
  public volatile boolean mNotResponding;
  public final Object mOwner;
  public int mPauseConfigurationDispatchCount;
  public volatile boolean mPendingUiClean;
  public volatile boolean mPerceptible;
  public volatile boolean mPersistent;
  public volatile int mPid;
  public int mRapidActivityLaunchCount;
  public ArrayMap mRemoteActivities;
  public volatile String mRequiredAbi;
  public boolean mRunningRecentsAnimation;
  public boolean mRunningRemoteAnimation;
  public IApplicationThread mThread;
  public final int mUid;
  public final int mUserId;
  public volatile boolean mUsingWrapper;
  public int mVrThreadTid;
  public volatile long mWhenUnimportant;
  public final ArrayList mPkgList = new ArrayList(1);
  public volatile int mCurProcState = 20;
  public volatile int mRepProcState = 20;
  public volatile int mCurAdj = -10000;
  public volatile int mInstrumentationSourceUid = -1;
  public final ArrayList mActivities = new ArrayList();
  public final ArrayList mRecentTasks = new ArrayList();
  public ActivityRecord mPreQTopResumedActivity = null;
  public final Configuration mLastReportedConfiguration = new Configuration();
  public int mLastTopActivityDeviceId = 0;
  public volatile int mActivityStateFlags = GnssNative.GNSS_AIDING_TYPE_ALL;
  public int mDisplayId = -1;
  public int mOverrideDisplayId = -1;
  public String mPrimaryDisplayName = null;
  public boolean mIsAppliedDexCompatConfiguration = false;
  public String mReason = null;
  public boolean mKeepProcessAlive = false;

  public interface ComputeOomAdjCallback {
    void onOtherActivity();

    void onPausedActivity();

    void onStoppingActivity(boolean z);

    void onVisibleActivity();
  }

  public static /* synthetic */ boolean lambda$updateTopResumingActivityInProcessIfNeeded$1(
      ActivityRecord activityRecord, ActivityRecord activityRecord2) {
    return activityRecord2 == activityRecord;
  }

  @Override // com.android.server.wm.ConfigurationContainer
  public ConfigurationContainer getChildAt(int i) {
    return null;
  }

  @Override // com.android.server.wm.ConfigurationContainer
  public int getChildCount() {
    return 0;
  }

  public WindowProcessController(
      final ActivityTaskManagerService activityTaskManagerService,
      ApplicationInfo applicationInfo,
      String str,
      int i,
      int i2,
      Object obj,
      WindowProcessListener windowProcessListener) {
    this.mIsActivityConfigOverrideAllowed = true;
    this.mInfo = applicationInfo;
    this.mName = str;
    this.mUid = i;
    this.mUserId = i2;
    this.mOwner = obj;
    this.mListener = windowProcessListener;
    this.mAtm = activityTaskManagerService;
    Objects.requireNonNull(activityTaskManagerService);
    this.mBgLaunchController =
        new BackgroundLaunchProcessController(
            new IntPredicate() { // from class:
                                 // com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda2
              @Override // java.util.function.IntPredicate
              public final boolean test(int i3) {
                return ActivityTaskManagerService.this.hasActiveVisibleWindow(i3);
              }
            },
            activityTaskManagerService.getBackgroundActivityStartCallback());
    if (applicationInfo.packageName.equals(
            activityTaskManagerService.getSysUiServiceComponentLocked().getPackageName())
        || UserHandle.getAppId(i) == 1000) {
      this.mIsActivityConfigOverrideAllowed = false;
      if (MultiTaskingController.ALLOW_OVERRIDE_DESKTOP_LAUNCHER
          && applicationInfo.packageName.equals("com.sec.android.app.desktoplauncher")) {
        this.mIsActivityConfigOverrideAllowed = true;
      }
    }
    onConfigurationChanged(activityTaskManagerService.getGlobalConfiguration());
    activityTaskManagerService.mPackageConfigPersister.updateConfigIfNeeded(
        this, i2, applicationInfo.packageName);
  }

  public void setPid(int i) {
    this.mPid = i;
  }

  public int getPid() {
    return this.mPid;
  }

  public void setThread(IApplicationThread iApplicationThread) {
    synchronized (this.mAtm.mGlobalLockWithoutBoost) {
      this.mThread = iApplicationThread;
      if (iApplicationThread != null) {
        setLastReportedConfiguration(getConfiguration());
      } else {
        this.mAtm.mVisibleActivityProcessTracker.removeProcess(this);
      }
    }
  }

  public IApplicationThread getThread() {
    return this.mThread;
  }

  public boolean hasThread() {
    return this.mThread != null;
  }

  public void setCurrentSchedulingGroup(int i) {
    this.mCurSchedGroup = i;
  }

  public int getCurrentSchedulingGroup() {
    return this.mCurSchedGroup;
  }

  public void setCurrentProcState(int i) {
    this.mCurProcState = i;
  }

  public int getCurrentProcState() {
    return this.mCurProcState;
  }

  public void setCurrentAdj(int i) {
    this.mCurAdj = i;
  }

  public int getCurrentAdj() {
    return this.mCurAdj;
  }

  public void setReportedProcState(int i) {
    Configuration configuration;
    int i2 = this.mRepProcState;
    this.mRepProcState = i;
    IApplicationThread iApplicationThread = this.mThread;
    if (i2 < 16 || i >= 16 || iApplicationThread == null || !this.mHasCachedConfiguration) {
      return;
    }
    synchronized (this.mLastReportedConfiguration) {
      configuration = new Configuration(this.mLastReportedConfiguration);
    }
    scheduleConfigurationChange(iApplicationThread, configuration);
  }

  public int getReportedProcState() {
    return this.mRepProcState;
  }

  public void setCrashing(boolean z) {
    this.mCrashing = z;
  }

  public void handleAppCrash() {
    ArrayList arrayList = new ArrayList(this.mActivities);
    for (int size = arrayList.size() - 1; size >= 0; size--) {
      ActivityRecord activityRecord = (ActivityRecord) arrayList.get(size);
      Slog.w(
          "ActivityTaskManager",
          "  Force finishing activity " + activityRecord.mActivityComponent.flattenToShortString());
      activityRecord.detachFromProcess();
      activityRecord.mDisplayContent.requestTransitionAndLegacyPrepare(2, 16);
      activityRecord.destroyIfPossible("handleAppCrashed");
    }
  }

  public boolean isCrashing() {
    return this.mCrashing;
  }

  public void setNotResponding(boolean z) {
    this.mNotResponding = z;
  }

  public boolean isNotResponding() {
    return this.mNotResponding;
  }

  public void setPersistent(boolean z) {
    this.mPersistent = z;
  }

  public boolean isPersistent() {
    return this.mPersistent;
  }

  public void setHasForegroundServices(boolean z) {
    this.mHasForegroundServices = z;
  }

  public boolean hasForegroundServices() {
    return this.mHasForegroundServices;
  }

  public boolean hasForegroundActivities() {
    return this.mAtm.mTopApp == this || (this.mActivityStateFlags & 458752) != 0;
  }

  public void setHasClientActivities(boolean z) {
    this.mHasClientActivities = z;
  }

  public boolean hasClientActivities() {
    return this.mHasClientActivities;
  }

  public void setHasTopUi(boolean z) {
    this.mHasTopUi = z;
  }

  public boolean hasTopUi() {
    return this.mHasTopUi;
  }

  public void setHasOverlayUi(boolean z) {
    this.mHasOverlayUi = z;
  }

  public boolean hasOverlayUi() {
    return this.mHasOverlayUi;
  }

  public void setPendingUiClean(boolean z) {
    this.mPendingUiClean = z;
  }

  public boolean hasPendingUiClean() {
    return this.mPendingUiClean;
  }

  public boolean registeredForDisplayAreaConfigChanges() {
    return this.mDisplayArea != null;
  }

  public boolean registeredForActivityConfigChanges() {
    return this.mConfigActivityRecord != null;
  }

  public void postPendingUiCleanMsg(boolean z) {
    this.mAtm.f1734mH.sendMessage(
        PooledLambda.obtainMessage(
            new BiConsumer() { // from class:
                               // com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda11
              @Override // java.util.function.BiConsumer
              public final void accept(Object obj, Object obj2) {
                ((WindowProcessListener) obj).setPendingUiClean(((Boolean) obj2).booleanValue());
              }
            },
            this.mListener,
            Boolean.valueOf(z)));
  }

  public void setInteractionEventTime(long j) {
    this.mInteractionEventTime = j;
  }

  public long getInteractionEventTime() {
    return this.mInteractionEventTime;
  }

  public void setFgInteractionTime(long j) {
    this.mFgInteractionTime = j;
  }

  public long getFgInteractionTime() {
    return this.mFgInteractionTime;
  }

  public void setWhenUnimportant(long j) {
    this.mWhenUnimportant = j;
  }

  public long getWhenUnimportant() {
    return this.mWhenUnimportant;
  }

  public void setRequiredAbi(String str) {
    this.mRequiredAbi = str;
  }

  public String getRequiredAbi() {
    return this.mRequiredAbi;
  }

  public DisplayArea getDisplayArea() {
    return this.mDisplayArea;
  }

  public void setDebugging(boolean z) {
    this.mDebugging = z;
  }

  public void setUsingWrapper(boolean z) {
    this.mUsingWrapper = z;
  }

  public boolean isUsingWrapper() {
    return this.mUsingWrapper;
  }

  public boolean hasEverLaunchedActivity() {
    return this.mLastActivityLaunchTime > 0;
  }

  public void setLastActivityLaunchTime(ActivityRecord activityRecord) {
    long j = activityRecord.lastLaunchTime;
    if (j > this.mLastActivityLaunchTime) {
      updateRapidActivityLaunch(activityRecord, j, this.mLastActivityLaunchTime);
      this.mLastActivityLaunchTime = j;
    } else if (j < this.mLastActivityLaunchTime) {
      Slog.w(
          "ActivityTaskManager",
          "Tried to set launchTime ("
              + j
              + ") < mLastActivityLaunchTime ("
              + this.mLastActivityLaunchTime
              + ")");
    }
  }

  public void updateRapidActivityLaunch(ActivityRecord activityRecord, long j, long j2) {
    if (this.mInstrumenting || this.mDebugging || j2 <= 0) {
      return;
    }
    long j3 = j - j2;
    if (j3 < 300) {
      this.mRapidActivityLaunchCount++;
    } else if (j3 >= 1500) {
      this.mRapidActivityLaunchCount = 0;
    }
    if (this.mRapidActivityLaunchCount > 500) {
      Slog.w("ActivityTaskManager", "Killing " + this.mPid + " because of rapid activity launch");
      activityRecord.getRootTask().moveTaskToBack(activityRecord.getTask());
      this.mAtm.f1734mH.post(
          new Runnable() { // from class:
                           // com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
              WindowProcessController.this.lambda$updateRapidActivityLaunch$0();
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateRapidActivityLaunch$0() {
    this.mAtm.mAmInternal.killProcess(this.mName, this.mUid, "rapidActivityLaunch");
  }

  public void setLastActivityFinishTimeIfNeeded(long j) {
    if (j <= this.mLastActivityFinishTime || !hasActivityInVisibleTask()) {
      return;
    }
    this.mLastActivityFinishTime = j;
  }

  public void addOrUpdateBackgroundStartPrivileges(
      Binder binder, BackgroundStartPrivileges backgroundStartPrivileges) {
    Objects.requireNonNull(binder, "entity");
    Objects.requireNonNull(backgroundStartPrivileges, "backgroundStartPrivileges");
    Preconditions.checkArgument(
        backgroundStartPrivileges.allowsAny(), "backgroundStartPrivileges does not allow anything");
    this.mBgLaunchController.addOrUpdateAllowBackgroundStartPrivileges(
        binder, backgroundStartPrivileges);
  }

  public void removeBackgroundStartPrivileges(Binder binder) {
    Objects.requireNonNull(binder, "entity");
    this.mBgLaunchController.removeAllowBackgroundStartPrivileges(binder);
  }

  public boolean areBackgroundFgsStartsAllowed() {
    return areBackgroundActivityStartsAllowed(this.mAtm.getBalAppSwitchesState(), true) != 0;
  }

  public int areBackgroundActivityStartsAllowed(int i) {
    return areBackgroundActivityStartsAllowed(i, false);
  }

  public final int areBackgroundActivityStartsAllowed(int i, boolean z) {
    return this.mBgLaunchController.areBackgroundActivityStartsAllowed(
        this.mPid,
        this.mUid,
        this.mInfo.packageName,
        i,
        z,
        hasActivityInVisibleTask(),
        this.mInstrumentingWithBackgroundActivityStartPrivileges,
        this.mAtm.getLastStopAppSwitchesTime(),
        this.mLastActivityLaunchTime,
        this.mLastActivityFinishTime);
  }

  public boolean canCloseSystemDialogsByToken() {
    return this.mBgLaunchController.canCloseSystemDialogsByToken(this.mUid);
  }

  public void clearBoundClientUids() {
    this.mBgLaunchController.clearBalOptInBoundClientUids();
  }

  public void addBoundClientUid(int i, String str, long j) {
    this.mBgLaunchController.addBoundClientUid(i, str, j);
  }

  public void setInstrumenting(boolean z, int i, boolean z2) {
    Preconditions.checkArgument(z || i == -1);
    this.mInstrumenting = z;
    this.mInstrumentationSourceUid = i;
    this.mInstrumentingWithBackgroundActivityStartPrivileges = z2;
  }

  public boolean isInstrumenting() {
    return this.mInstrumenting;
  }

  public int getInstrumentationSourceUid() {
    return this.mInstrumentationSourceUid;
  }

  public void setPerceptible(boolean z) {
    this.mPerceptible = z;
  }

  public boolean isPerceptible() {
    return this.mPerceptible;
  }

  @Override // com.android.server.wm.ConfigurationContainer
  public ConfigurationContainer getParent() {
    return this.mAtm.mRootWindowContainer;
  }

  public void addPackage(String str) {
    synchronized (this.mPkgList) {
      if (!this.mPkgList.contains(str)) {
        this.mPkgList.add(str);
      }
    }
  }

  public void clearPackageList() {
    synchronized (this.mPkgList) {
      this.mPkgList.clear();
    }
  }

  public boolean containsPackage(String str) {
    boolean contains;
    synchronized (this.mPkgList) {
      contains = this.mPkgList.contains(str);
    }
    return contains;
  }

  public List getPackageList() {
    ArrayList arrayList;
    synchronized (this.mPkgList) {
      arrayList = new ArrayList(this.mPkgList);
    }
    return arrayList;
  }

  public void addActivityIfNeeded(ActivityRecord activityRecord) {
    setLastActivityLaunchTime(activityRecord);
    if (this.mActivities.contains(activityRecord)) {
      return;
    }
    this.mActivities.add(activityRecord);
    this.mHasActivities = true;
    ArrayList arrayList = this.mInactiveActivities;
    if (arrayList != null) {
      arrayList.remove(activityRecord);
    }
    updateActivityConfigurationListener();
  }

  public void removeActivity(ActivityRecord activityRecord, boolean z) {
    if (z) {
      ArrayList arrayList = this.mInactiveActivities;
      if (arrayList == null) {
        ArrayList arrayList2 = new ArrayList();
        this.mInactiveActivities = arrayList2;
        arrayList2.add(activityRecord);
      } else if (!arrayList.contains(activityRecord)) {
        this.mInactiveActivities.add(activityRecord);
      }
    } else {
      ArrayList arrayList3 = this.mInactiveActivities;
      if (arrayList3 != null) {
        arrayList3.remove(activityRecord);
      }
    }
    this.mActivities.remove(activityRecord);
    this.mHasActivities = !this.mActivities.isEmpty();
    updateActivityConfigurationListener();
  }

  public void clearActivities() {
    this.mInactiveActivities = null;
    this.mActivities.clear();
    this.mHasActivities = false;
    updateActivityConfigurationListener();
  }

  public boolean hasActivities() {
    return this.mHasActivities;
  }

  public boolean hasVisibleActivities() {
    return (this.mActivityStateFlags & 65536) != 0;
  }

  public boolean hasActivityInVisibleTask() {
    return (this.mActivityStateFlags & 4194304) != 0;
  }

  public boolean hasActivitiesOrRecentTasks() {
    return this.mHasActivities || this.mHasRecentTasks;
  }

  public TaskDisplayArea getTopActivityDisplayArea() {
    if (this.mActivities.isEmpty()) {
      return null;
    }
    int size = this.mActivities.size() - 1;
    ActivityRecord activityRecord = (ActivityRecord) this.mActivities.get(size);
    TaskDisplayArea displayArea = activityRecord.getDisplayArea();
    for (int i = size - 1; i >= 0; i--) {
      ActivityRecord activityRecord2 = (ActivityRecord) this.mActivities.get(i);
      TaskDisplayArea displayArea2 = activityRecord2.getDisplayArea();
      if (activityRecord2.compareTo((WindowContainer) activityRecord) > 0 && displayArea2 != null) {
        activityRecord = activityRecord2;
        displayArea = displayArea2;
      }
    }
    return displayArea;
  }

  public boolean updateTopResumingActivityInProcessIfNeeded(final ActivityRecord activityRecord) {
    TaskFragment taskFragment;
    ActivityRecord activity;
    if (this.mInfo.targetSdkVersion < 29 && this.mPreQTopResumedActivity != activityRecord) {
      if (!activityRecord.isAttached()) {
        return false;
      }
      ActivityRecord activityRecord2 = this.mPreQTopResumedActivity;
      DisplayContent displayContent =
          (activityRecord2 == null || !activityRecord2.isAttached())
              ? null
              : this.mPreQTopResumedActivity.mDisplayContent;
      boolean z =
          (displayContent != null
                  && this.mPreQTopResumedActivity.isVisibleRequested()
                  && this.mPreQTopResumedActivity.isFocusable())
              ? false
              : true;
      DisplayContent displayContent2 = activityRecord.mDisplayContent;
      if (!z && displayContent.compareTo((WindowContainer) displayContent2) < 0) {
        z = true;
      }
      r2 =
          (z
                  || (activity =
                          displayContent.getActivity(
                              new Predicate() { // from class:
                                                // com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda10
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                  boolean lambda$updateTopResumingActivityInProcessIfNeeded$1;
                                  lambda$updateTopResumingActivityInProcessIfNeeded$1 =
                                      WindowProcessController
                                          .lambda$updateTopResumingActivityInProcessIfNeeded$1(
                                              ActivityRecord.this, (ActivityRecord) obj);
                                  return lambda$updateTopResumingActivityInProcessIfNeeded$1;
                                }
                              },
                              true,
                              this.mPreQTopResumedActivity))
                      == null
                  || activity == this.mPreQTopResumedActivity)
              ? z
              : true;
      if (r2) {
        ActivityRecord activityRecord3 = this.mPreQTopResumedActivity;
        if (activityRecord3 != null
            && activityRecord3.isState(ActivityRecord.State.RESUMED)
            && (taskFragment = this.mPreQTopResumedActivity.getTaskFragment()) != null) {
          taskFragment.startPausing(
              taskFragment.shouldBeVisible(null), false, activityRecord, "top-resumed-changed");
        }
        this.mPreQTopResumedActivity = activityRecord;
      }
    }
    return r2;
  }

  public void stopFreezingActivities() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        int size = this.mActivities.size();
        while (size > 0) {
          size--;
          ((ActivityRecord) this.mActivities.get(size)).stopFreezingScreenLocked(true);
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void finishActivities() {
    ArrayList arrayList = new ArrayList(this.mActivities);
    for (int i = 0; i < arrayList.size(); i++) {
      ActivityRecord activityRecord = (ActivityRecord) arrayList.get(i);
      if (!activityRecord.finishing && activityRecord.isInRootTaskLocked()) {
        activityRecord.finishIfPossible("finish-heavy", true);
      }
    }
  }

  public boolean isInterestingToUser() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        int size = this.mActivities.size();
        for (int i = 0; i < size; i++) {
          if (((ActivityRecord) this.mActivities.get(i)).isInterestingToUserLocked()) {
            WindowManagerService.resetPriorityAfterLockedSection();
            return true;
          }
        }
        if (hasEmbeddedWindow()) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return true;
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return false;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public final boolean hasEmbeddedWindow() {
    ArrayMap arrayMap = this.mRemoteActivities;
    if (arrayMap == null) {
      return false;
    }
    for (int size = arrayMap.size() - 1; size >= 0; size--) {
      if ((((int[]) this.mRemoteActivities.valueAt(size))[0] & 1) != 0
          && ((ActivityRecord) this.mRemoteActivities.keyAt(size)).isInterestingToUserLocked()) {
        return true;
      }
    }
    return false;
  }

  public boolean hasRunningActivity(String str) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        for (int size = this.mActivities.size() - 1; size >= 0; size--) {
          if (str.equals(((ActivityRecord) this.mActivities.get(size)).packageName)) {
            WindowManagerService.resetPriorityAfterLockedSection();
            return true;
          }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return false;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public void updateAppSpecificSettingsForAllActivitiesInPackage(
      String str, Integer num, LocaleList localeList, int i) {
    for (int size = this.mActivities.size() - 1; size >= 0; size--) {
      ActivityRecord activityRecord = (ActivityRecord) this.mActivities.get(size);
      if (str.equals(activityRecord.packageName)
          && activityRecord.applyAppSpecificConfig(num, localeList, Integer.valueOf(i))
          && activityRecord.isVisibleRequested()) {
        activityRecord.ensureActivityConfiguration(0, true);
      }
    }
  }

  public void clearPackagePreferredForHomeActivities() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        for (int size = this.mActivities.size() - 1; size >= 0; size--) {
          ActivityRecord activityRecord = (ActivityRecord) this.mActivities.get(size);
          if (activityRecord.isActivityTypeHome()) {
            Log.i(
                "ActivityTaskManager",
                "Clearing package preferred activities from " + activityRecord.packageName);
            try {
              ActivityThread.getPackageManager()
                  .clearPackagePreferredActivities(activityRecord.packageName);
            } catch (RemoteException unused) {
            }
          }
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public boolean hasStartedActivity(ActivityRecord activityRecord) {
    for (int size = this.mActivities.size() - 1; size >= 0; size--) {
      ActivityRecord activityRecord2 = (ActivityRecord) this.mActivities.get(size);
      if (activityRecord != activityRecord2 && !activityRecord2.mAppStopped) {
        return true;
      }
    }
    return false;
  }

  public boolean hasResumedActivity() {
    return (this.mActivityStateFlags & 2097152) != 0;
  }

  public void updateIntentForHeavyWeightActivity(Intent intent) {
    if (this.mActivities.isEmpty()) {
      return;
    }
    ActivityRecord activityRecord = (ActivityRecord) this.mActivities.get(0);
    intent.putExtra("cur_app", activityRecord.packageName);
    intent.putExtra("cur_task", activityRecord.getTask().mTaskId);
  }

  public boolean shouldKillProcessForRemovedTask(Task task) {
    for (int i = 0; i < this.mActivities.size(); i++) {
      ActivityRecord activityRecord = (ActivityRecord) this.mActivities.get(i);
      if (!activityRecord.mAppStopped) {
        return false;
      }
      Task task2 = activityRecord.getTask();
      if (task.mTaskId != task2.mTaskId && task2.inRecents) {
        return false;
      }
    }
    return true;
  }

  public void releaseSomeActivities(String str) {
    ArrayList arrayList = null;
    for (int i = 0; i < this.mActivities.size(); i++) {
      ActivityRecord activityRecord = (ActivityRecord) this.mActivities.get(i);
      if (activityRecord.finishing
          || activityRecord.isState(
              ActivityRecord.State.DESTROYING, ActivityRecord.State.DESTROYED)) {
        return;
      }
      if (!activityRecord.isVisibleRequested()
          && activityRecord.mAppStopped
          && activityRecord.hasSavedState()
          && activityRecord.isDestroyable()
          && !activityRecord.isState(
              ActivityRecord.State.STARTED,
              ActivityRecord.State.RESUMED,
              ActivityRecord.State.PAUSING,
              ActivityRecord.State.PAUSED,
              ActivityRecord.State.STOPPING)
          && activityRecord.getParent() != null) {
        if (arrayList == null) {
          arrayList = new ArrayList();
        }
        arrayList.add(activityRecord);
      }
    }
    if (arrayList != null) {
      arrayList.sort(
          new Comparator() { // from class:
                             // com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda3
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
              return ((ActivityRecord) obj).compareTo((WindowContainer) obj2);
            }
          });
      int max = Math.max(arrayList.size(), 1);
      do {
        ((ActivityRecord) arrayList.remove(0)).destroyImmediately(str);
        max--;
      } while (max > 0);
    }
  }

  public void getDisplayContextsWithErrorDialogs(List list) {
    if (list == null) {
      return;
    }
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        RootWindowContainer rootWindowContainer = this.mAtm.mWindowManager.mRoot;
        rootWindowContainer.getDisplayContextsWithNonToastVisibleWindows(this.mPid, list);
        for (int size = this.mActivities.size() - 1; size >= 0; size--) {
          ActivityRecord activityRecord = (ActivityRecord) this.mActivities.get(size);
          Context displayUiContext =
              rootWindowContainer.getDisplayUiContext(activityRecord.getDisplayId());
          if (displayUiContext != null
              && activityRecord.isVisibleRequested()
              && !list.contains(displayUiContext)) {
            list.add(displayUiContext);
          }
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void addHostActivity(ActivityRecord activityRecord) {
    int[] remoteActivityFlags = getRemoteActivityFlags(activityRecord);
    remoteActivityFlags[0] = remoteActivityFlags[0] | 1;
  }

  public void removeHostActivity(ActivityRecord activityRecord) {
    removeRemoteActivityFlags(activityRecord, 1);
  }

  public void addEmbeddedActivity(ActivityRecord activityRecord) {
    int[] remoteActivityFlags = getRemoteActivityFlags(activityRecord);
    remoteActivityFlags[0] = remoteActivityFlags[0] | 2;
  }

  public void removeEmbeddedActivity(ActivityRecord activityRecord) {
    removeRemoteActivityFlags(activityRecord, 2);
  }

  public final int[] getRemoteActivityFlags(ActivityRecord activityRecord) {
    if (this.mRemoteActivities == null) {
      this.mRemoteActivities = new ArrayMap();
    }
    int[] iArr = (int[]) this.mRemoteActivities.get(activityRecord);
    if (iArr != null) {
      return iArr;
    }
    int[] iArr2 = new int[1];
    this.mRemoteActivities.put(activityRecord, iArr2);
    return iArr2;
  }

  public final void removeRemoteActivityFlags(ActivityRecord activityRecord, int i) {
    int indexOfKey;
    ArrayMap arrayMap = this.mRemoteActivities;
    if (arrayMap != null && (indexOfKey = arrayMap.indexOfKey(activityRecord)) >= 0) {
      int[] iArr = (int[]) this.mRemoteActivities.valueAt(indexOfKey);
      int i2 = (~i) & iArr[0];
      iArr[0] = i2;
      if (i2 == 0) {
        this.mRemoteActivities.removeAt(indexOfKey);
      }
    }
  }

  public int computeOomAdjFromActivities(ComputeOomAdjCallback computeOomAdjCallback) {
    int i = this.mActivityStateFlags;
    if ((65536 & i) != 0) {
      computeOomAdjCallback.onVisibleActivity();
    } else if ((131072 & i) != 0) {
      computeOomAdjCallback.onPausedActivity();
    } else if ((262144 & i) != 0) {
      computeOomAdjCallback.onStoppingActivity((524288 & i) != 0);
    } else {
      computeOomAdjCallback.onOtherActivity();
    }
    return i & GnssNative.GNSS_AIDING_TYPE_ALL;
  }

  public void computeProcessActivityState() {
    int i;
    ActivityRecord.State state;
    int i2;
    ActivityRecord.State state2 = ActivityRecord.State.DESTROYED;
    boolean hasResumedActivity = hasResumedActivity();
    boolean z = (this.mActivityStateFlags & 1114112) != 0;
    int i3 = Integer.MAX_VALUE;
    boolean z2 = true;
    int i4 = 0;
    boolean z3 = false;
    for (int size = this.mActivities.size() - 1; size >= 0; size--) {
      ActivityRecord activityRecord = (ActivityRecord) this.mActivities.get(size);
      if (activityRecord.isVisible()) {
        i4 |= 1048576;
      }
      Task task = activityRecord.getTask();
      if (task != null && task.mLayerRank != -1) {
        i4 |= 4194304;
      }
      if (activityRecord.isVisibleRequested()) {
        if (activityRecord.isState(ActivityRecord.State.RESUMED)) {
          i4 |= 2097152;
        }
        if (task != null && i3 > 0 && (i2 = task.mLayerRank) >= 0 && i3 > i2) {
          i3 = i2;
        }
        z3 = true;
      } else if (!z3 && state2 != (state = ActivityRecord.State.PAUSING)) {
        if (!activityRecord.isState(state, ActivityRecord.State.PAUSED)) {
          state = ActivityRecord.State.STOPPING;
          if (activityRecord.isState(state)) {
            z2 &= activityRecord.finishing;
          }
        }
        state2 = state;
      }
    }
    ArrayMap arrayMap = this.mRemoteActivities;
    if (arrayMap != null) {
      for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
        if ((((int[]) this.mRemoteActivities.valueAt(size2))[0] & 2) != 0
            && ((ActivityRecord) this.mRemoteActivities.keyAt(size2)).isVisibleRequested()) {
          i4 |= 65536;
        }
      }
    }
    int i5 = (65535 & i3) | i4;
    if (z3) {
      i5 |= 65536;
    } else {
      if (state2 == ActivityRecord.State.PAUSING) {
        i = IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
      } else if (state2 == ActivityRecord.State.STOPPING) {
        i5 |= 262144;
        if (z2) {
          i = 524288;
        }
      }
      i5 |= i;
    }
    this.mActivityStateFlags = i5;
    boolean z4 = (i5 & 1114112) != 0;
    if (!z && z4) {
      this.mAtm.mVisibleActivityProcessTracker.onAnyActivityVisible(this);
      return;
    }
    if (z && !z4) {
      this.mAtm.mVisibleActivityProcessTracker.onAllActivitiesInvisible(this);
    } else if (z && !hasResumedActivity && hasResumedActivity()) {
      this.mAtm.mVisibleActivityProcessTracker.onActivityResumedWhileVisible(this);
    }
  }

  public final void prepareOomAdjustment() {
    this.mAtm.mRootWindowContainer.rankTaskLayers();
    this.mAtm.mTaskSupervisor.computeProcessActivityStateBatch();
  }

  public int computeRelaunchReason() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        for (int size = this.mActivities.size() - 1; size >= 0; size--) {
          int i = ((ActivityRecord) this.mActivities.get(size)).mRelaunchReason;
          if (i != 0) {
            WindowManagerService.resetPriorityAfterLockedSection();
            return i;
          }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return 0;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public long getInputDispatchingTimeoutMillis() {
    long j;
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        j =
            (isInstrumenting() || isUsingWrapper())
                ? 60000L
                : InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return j;
  }

  public void clearProfilerIfNeeded() {
    this.mAtm.f1734mH.sendMessage(
        PooledLambda.obtainMessage(
            new Consumer() { // from class:
                             // com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((WindowProcessListener) obj).clearProfilerIfNeeded();
              }
            },
            this.mListener));
  }

  public void updateProcessInfo(boolean z, boolean z2, boolean z3, boolean z4) {
    if (z4) {
      addToPendingTop();
    }
    if (z3) {
      prepareOomAdjustment();
    }
    this.mAtm.f1734mH.sendMessage(
        PooledLambda.obtainMessage(
            new WindowProcessController$$ExternalSyntheticLambda5(),
            this.mListener,
            Boolean.valueOf(z),
            Boolean.valueOf(z2),
            Boolean.valueOf(z3)));
  }

  public void scheduleUpdateOomAdj() {
    ActivityTaskManagerService.HandlerC2878H handlerC2878H = this.mAtm.f1734mH;
    WindowProcessController$$ExternalSyntheticLambda5
        windowProcessController$$ExternalSyntheticLambda5 =
            new WindowProcessController$$ExternalSyntheticLambda5();
    WindowProcessListener windowProcessListener = this.mListener;
    Boolean bool = Boolean.FALSE;
    handlerC2878H.sendMessage(
        PooledLambda.obtainMessage(
            windowProcessController$$ExternalSyntheticLambda5,
            windowProcessListener,
            bool,
            bool,
            Boolean.TRUE));
  }

  public void addToPendingTop() {
    this.mAtm.mAmInternal.addPendingTopUid(this.mUid, this.mPid, this.mThread);
  }

  public void updateServiceConnectionActivities() {
    this.mAtm.f1734mH.sendMessage(
        PooledLambda.obtainMessage(
            new Consumer() { // from class:
                             // com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda7
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((WindowProcessListener) obj).updateServiceConnectionActivities();
              }
            },
            this.mListener));
  }

  public void setPendingUiCleanAndForceProcessStateUpTo(int i) {
    this.mAtm.f1734mH.sendMessage(
        PooledLambda.obtainMessage(
            new BiConsumer() { // from class:
                               // com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda12
              @Override // java.util.function.BiConsumer
              public final void accept(Object obj, Object obj2) {
                ((WindowProcessListener) obj)
                    .setPendingUiCleanAndForceProcessStateUpTo(((Integer) obj2).intValue());
              }
            },
            this.mListener,
            Integer.valueOf(i)));
  }

  public boolean isRemoved() {
    return this.mListener.isRemoved();
  }

  public final boolean shouldSetProfileProc() {
    WindowProcessController windowProcessController;
    String str = this.mAtm.mProfileApp;
    return str != null
        && str.equals(this.mName)
        && ((windowProcessController = this.mAtm.mProfileProc) == null
            || windowProcessController == this);
  }

  public ProfilerInfo createProfilerInfoIfNeeded() {
    ProfilerInfo profilerInfo = this.mAtm.mProfilerInfo;
    if (profilerInfo == null || profilerInfo.profileFile == null || !shouldSetProfileProc()) {
      return null;
    }
    ParcelFileDescriptor parcelFileDescriptor = profilerInfo.profileFd;
    if (parcelFileDescriptor != null) {
      try {
        profilerInfo.profileFd = parcelFileDescriptor.dup();
      } catch (IOException unused) {
        profilerInfo.closeFd();
      }
    }
    return new ProfilerInfo(profilerInfo);
  }

  public void onStartActivity(int i, ActivityInfo activityInfo) {
    String str =
        ((activityInfo.flags & 1) == 0 || !"android".equals(activityInfo.packageName))
            ? activityInfo.packageName
            : null;
    if (i == 2) {
      this.mAtm.mAmInternal.addPendingTopUid(this.mUid, this.mPid, this.mThread);
    }
    this.mListener.setClearWaitingToKill();
    prepareOomAdjustment();
    this.mAtm.f1734mH.sendMessageAtFrontOfQueue(
        PooledLambda.obtainMessage(
            new QuintConsumer() { // from class:
                                  // com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda6
              public final void accept(
                  Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                ((WindowProcessListener) obj)
                    .onStartActivity(
                        ((Integer) obj2).intValue(),
                        ((Boolean) obj3).booleanValue(),
                        (String) obj4,
                        ((Long) obj5).longValue());
              }
            },
            this.mListener,
            Integer.valueOf(i),
            Boolean.valueOf(shouldSetProfileProc()),
            str,
            Long.valueOf(activityInfo.applicationInfo.longVersionCode)));
  }

  public void appDied(String str) {
    this.mAtm.f1734mH.sendMessage(
        PooledLambda.obtainMessage(
            new BiConsumer() { // from class:
                               // com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda8
              @Override // java.util.function.BiConsumer
              public final void accept(Object obj, Object obj2) {
                ((WindowProcessListener) obj).appDied((String) obj2);
              }
            },
            this.mListener,
            str));
  }

  public boolean handleAppDied() {
    this.mAtm.mTaskSupervisor.removeHistoryRecords(this);
    ArrayList arrayList = this.mInactiveActivities;
    boolean z = false;
    boolean z2 = (arrayList == null || arrayList.isEmpty()) ? false : true;
    ArrayList arrayList2 = (this.mHasActivities || z2) ? new ArrayList() : this.mActivities;
    if (this.mHasActivities) {
      arrayList2.addAll(this.mActivities);
    }
    if (z2) {
      arrayList2.addAll(this.mInactiveActivities);
    }
    if (isRemoved() && !skipToFinishActivities()) {
      for (int size = arrayList2.size() - 1; size >= 0; size--) {
        ((ActivityRecord) arrayList2.get(size)).makeFinishingLocked();
      }
    }
    for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
      ActivityRecord activityRecord = (ActivityRecord) arrayList2.get(size2);
      if (activityRecord.isVisibleRequested() || activityRecord.isVisible()) {
        z = true;
      }
      TaskFragment taskFragment = activityRecord.getTaskFragment();
      if (taskFragment != null) {
        z |= taskFragment.handleAppDied(this);
      }
      activityRecord.handleAppDied();
    }
    clearRecentTasks();
    clearActivities();
    return z;
  }

  public void registerDisplayAreaConfigurationListener(DisplayArea displayArea) {
    if (displayArea == null || displayArea.containsListener(this)) {
      return;
    }
    unregisterConfigurationListeners();
    this.mDisplayArea = displayArea;
    displayArea.registerConfigurationChangeListener(this);
  }

  public void unregisterDisplayAreaConfigurationListener() {
    DisplayArea displayArea = this.mDisplayArea;
    if (displayArea == null) {
      return;
    }
    displayArea.unregisterConfigurationChangeListener(this);
    this.mDisplayArea = null;
    onMergedOverrideConfigurationChanged(Configuration.EMPTY);
  }

  public void registerActivityConfigurationListener(ActivityRecord activityRecord) {
    if (activityRecord == null
        || activityRecord.containsListener(this)
        || !this.mIsActivityConfigOverrideAllowed) {
      return;
    }
    unregisterConfigurationListeners();
    this.mConfigActivityRecord = activityRecord;
    this.mConfigTask = activityRecord.getTask();
    activityRecord.registerConfigurationChangeListener(this);
  }

  public final void unregisterActivityConfigurationListener() {
    ActivityRecord activityRecord = this.mConfigActivityRecord;
    if (activityRecord == null) {
      return;
    }
    activityRecord.unregisterConfigurationChangeListener(this);
    this.mConfigTask = null;
    if (this.mConfigActivityRecord.getDisplayId() == 2 && this.mOverrideDisplayId != -1) {
      DisplayContent displayContent = this.mConfigActivityRecord.getDisplayContent();
      onMergedOverrideConfigurationChanged(displayContent.getMergedOverrideConfiguration());
      this.mConfigActivityRecord = null;
      registerDisplayConfigurationListener(displayContent);
      return;
    }
    this.mConfigActivityRecord = null;
    if (!this.mIsActivityConfigOverrideAllowed) {
      Configuration configuration = new Configuration();
      configuration.windowConfiguration.setPopOverState(2);
      onMergedOverrideConfigurationChanged(configuration);
      return;
    }
    onMergedOverrideConfigurationChanged(Configuration.EMPTY);
  }

  public final void unregisterConfigurationListeners() {
    unregisterActivityConfigurationListener();
    unregisterDisplayAreaConfigurationListener();
    unregisterDisplayConfigurationListener();
  }

  public void destroy() {
    unregisterConfigurationListeners();
  }

  public final void updateActivityConfigurationListener() {
    updateActivityConfigurationListener(null);
  }

  public final void updateActivityConfigurationListener(ActivityRecord activityRecord) {
    if (this.mIsActivityConfigOverrideAllowed) {
      if (activityRecord != null) {
        if (activityRecord.getTask().equals(this.mConfigTask)) {
          return;
        }
        registerActivityConfigurationListener(activityRecord);
        return;
      }
      for (int size = this.mActivities.size() - 1; size >= 0; size--) {
        ActivityRecord activityRecord2 = (ActivityRecord) this.mActivities.get(size);
        if (!activityRecord2.finishing) {
          registerActivityConfigurationListener(activityRecord2);
          return;
        }
      }
      unregisterActivityConfigurationListener();
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:10:0x0050  */
  /* JADX WARN: Removed duplicated region for block: B:22:0x0087  */
  /* JADX WARN: Removed duplicated region for block: B:7:0x003f  */
  @Override // com.android.server.wm.ConfigurationContainer
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void onConfigurationChanged(Configuration configuration) {
    Configuration configuration2;
    int topActivityDeviceId;
    Configuration configuration3;
    boolean z = false;
    if (this.mAtm.mDexCompatController.shouldBeApplyDexCompatConfigurationLocked(
        null, this.mInfo, this.mDisplayId)) {
      configuration2 = new Configuration(configuration);
      this.mAtm.mDexCompatController.applyDexCompatConfigurationLocked(
          null, this.mInfo, configuration2, "updateConfiguration");
      this.mIsAppliedDexCompatConfiguration = true;
    } else {
      if (this.mIsAppliedDexCompatConfiguration) {
        configuration2 = new Configuration(configuration);
        configuration2.dexCompatEnabled = 1;
        this.mIsAppliedDexCompatConfiguration = false;
      }
      super.onConfigurationChanged(configuration);
      topActivityDeviceId = getTopActivityDeviceId();
      if (topActivityDeviceId != this.mLastTopActivityDeviceId) {
        this.mLastTopActivityDeviceId = topActivityDeviceId;
        z = true;
      }
      configuration3 = getConfiguration();
      if (!(this.mLastReportedConfiguration.equals(configuration3) & (!z))) {
        if (configuration3.isOtherSeqNewer(this.mLastReportedConfiguration)) {
          configuration3.seq = this.mLastReportedConfiguration.seq;
        }
        if (Build.IS_DEBUGGABLE && this.mHasImeService) {
          Slog.w(
              "ActivityTaskManager",
              "Current config: " + configuration3 + " unchanged for IME proc " + this.mName);
          return;
        }
        return;
      }
      new Configuration(configuration3);
      if (this.mPauseConfigurationDispatchCount > 0) {
        this.mHasPendingConfigurationChange = true;
        return;
      } else {
        dispatchConfiguration(configuration3);
        return;
      }
    }
    configuration = configuration2;
    super.onConfigurationChanged(configuration);
    topActivityDeviceId = getTopActivityDeviceId();
    if (topActivityDeviceId != this.mLastTopActivityDeviceId) {}
    configuration3 = getConfiguration();
    if (!(this.mLastReportedConfiguration.equals(configuration3) & (!z))) {}
  }

  public final int getTopActivityDeviceId() {
    DisplayContent displayContent;
    ActivityRecord topNonFinishingActivity = getTopNonFinishingActivity();
    if (topNonFinishingActivity == null
        || (displayContent = topNonFinishingActivity.mDisplayContent) == null) {
      return 0;
    }
    return this.mAtm.mTaskSupervisor.getDeviceIdForDisplayId(displayContent.mDisplayId);
  }

  public final ActivityRecord getTopNonFinishingActivity() {
    if (this.mActivities.isEmpty()) {
      return null;
    }
    for (int size = this.mActivities.size() - 1; size >= 0; size--) {
      if (!((ActivityRecord) this.mActivities.get(size)).finishing) {
        return (ActivityRecord) this.mActivities.get(size);
      }
    }
    return null;
  }

  @Override // com.android.server.wm.ConfigurationContainerListener
  public void onMergedOverrideConfigurationChanged(Configuration configuration) {
    super.onRequestedOverrideConfigurationChanged(configuration);
  }

  @Override // com.android.server.wm.ConfigurationContainer
  public void resolveOverrideConfiguration(Configuration configuration) {
    Configuration requestedOverrideConfiguration = getRequestedOverrideConfiguration();
    int i = requestedOverrideConfiguration.assetsSeq;
    if (i != 0 && configuration.assetsSeq > i) {
      requestedOverrideConfiguration.assetsSeq = 0;
    }
    super.resolveOverrideConfiguration(configuration);
    Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
    resolvedOverrideConfiguration.windowConfiguration.setActivityType(0);
    resolvedOverrideConfiguration.seq = configuration.seq;
    if (this.mIsActivityConfigOverrideAllowed) {
      resolvedOverrideConfiguration.overrideUndefinedFrom(this.mLastReportedConfiguration);
    }
  }

  public void dispatchConfiguration(Configuration configuration) {
    this.mHasPendingConfigurationChange = false;
    if (this.mThread == null) {
      if (Build.IS_DEBUGGABLE && this.mHasImeService) {
        Slog.w(
            "ActivityTaskManager",
            "Unable to send config for IME proc " + this.mName + ": no app thread");
        return;
      }
      return;
    }
    configuration.seq = this.mAtm.increaseConfigurationSeqLocked();
    setLastReportedConfiguration(configuration);
    if (this.mRepProcState >= 16) {
      this.mHasCachedConfiguration = true;
      if (this.mRepProcState >= 16) {
        return;
      }
    }
    scheduleConfigurationChange(this.mThread, configuration);
  }

  public final void scheduleConfigurationChange(
      IApplicationThread iApplicationThread, Configuration configuration) {
    if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_CONFIGURATION,
          1049367566,
          0,
          (String) null,
          new Object[] {String.valueOf(this.mName), String.valueOf(configuration)});
    }
    if (Build.IS_DEBUGGABLE && this.mHasImeService) {
      Slog.v(
          "ActivityTaskManager",
          "Sending to IME proc " + this.mName + " new config " + configuration);
    }
    this.mHasCachedConfiguration = false;
    try {
      this.mAtm
          .getLifecycleManager()
          .scheduleTransaction(
              iApplicationThread,
              ConfigurationChangeItem.obtain(configuration, this.mLastTopActivityDeviceId));
    } catch (Exception e) {
      Slog.e("ActivityTaskManager", "Failed to schedule configuration change: " + this.mOwner, e);
    }
  }

  public void setLastReportedConfiguration(Configuration configuration) {
    synchronized (this.mLastReportedConfiguration) {
      this.mLastReportedConfiguration.setTo(configuration);
    }
  }

  public void pauseConfigurationDispatch() {
    this.mPauseConfigurationDispatchCount++;
  }

  public boolean resumeConfigurationDispatch() {
    int i = this.mPauseConfigurationDispatchCount;
    if (i == 0) {
      return false;
    }
    this.mPauseConfigurationDispatchCount = i - 1;
    return this.mHasPendingConfigurationChange;
  }

  public void updateAssetConfiguration(int i) {
    if (!this.mHasActivities || !this.mIsActivityConfigOverrideAllowed) {
      Configuration configuration = new Configuration(getRequestedOverrideConfiguration());
      configuration.assetsSeq = i;
      onRequestedOverrideConfigurationChanged(configuration);
      for (int size = this.mActivities.size() - 1; size >= 0; size--) {
        ActivityRecord activityRecord = (ActivityRecord) this.mActivities.get(size);
        if (!activityRecord.isDexMode()) {
          Configuration configuration2 =
              new Configuration(activityRecord.getRequestedOverrideConfiguration());
          configuration2.assetsSeq = i;
          activityRecord.onRequestedOverrideConfigurationChanged(configuration2);
          if (activityRecord.mVisibleRequested) {
            activityRecord.ensureActivityConfiguration(0, true);
          }
        }
      }
      return;
    }
    for (int size2 = this.mActivities.size() - 1; size2 >= 0; size2--) {
      ActivityRecord activityRecord2 = (ActivityRecord) this.mActivities.get(size2);
      Configuration configuration3 =
          new Configuration(activityRecord2.getRequestedOverrideConfiguration());
      configuration3.assetsSeq = i;
      activityRecord2.onRequestedOverrideConfigurationChanged(configuration3);
      if (activityRecord2.isVisibleRequested()) {
        activityRecord2.ensureActivityConfiguration(0, true);
      }
    }
  }

  public Configuration prepareConfigurationForLaunchingActivity() {
    Configuration configuration = getConfiguration();
    if (this.mHasPendingConfigurationChange) {
      this.mHasPendingConfigurationChange = false;
      configuration.seq = this.mAtm.increaseConfigurationSeqLocked();
    }
    this.mHasCachedConfiguration = false;
    return configuration;
  }

  public long getCpuTime() {
    return this.mListener.getCpuTime();
  }

  public void addRecentTask(Task task) {
    this.mRecentTasks.add(task);
    this.mHasRecentTasks = true;
  }

  public void removeRecentTask(Task task) {
    this.mRecentTasks.remove(task);
    this.mHasRecentTasks = !this.mRecentTasks.isEmpty();
  }

  public boolean hasRecentTasks() {
    return this.mHasRecentTasks;
  }

  public void clearRecentTasks() {
    for (int size = this.mRecentTasks.size() - 1; size >= 0; size--) {
      ((Task) this.mRecentTasks.get(size)).clearRootProcess();
    }
    this.mRecentTasks.clear();
    this.mHasRecentTasks = false;
  }

  /* JADX WARN: Code restructure failed: missing block: B:15:0x0022, code lost:

     if (r5.mPid != com.android.server.wm.WindowManagerService.MY_PID) goto L16;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void appEarlyNotResponding(String str, Runnable runnable) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        IActivityController iActivityController = this.mAtm.mController;
        if (iActivityController == null) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return;
        }
        Runnable runnable2 = null;
        try {
          if (iActivityController.appEarlyNotResponding(this.mName, this.mPid, str) < 0) {}
          runnable = null;
          runnable2 = runnable;
        } catch (RemoteException unused) {
          this.mAtm.mController = null;
          Watchdog.getInstance().setActivityController(null);
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (runnable2 != null) {
          runnable2.run();
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:16:0x0025, code lost:

     if (r6.mPid != com.android.server.wm.WindowManagerService.MY_PID) goto L18;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean appNotResponding(String str, Runnable runnable, Runnable runnable2) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        IActivityController iActivityController = this.mAtm.mController;
        if (iActivityController == null) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return false;
        }
        try {
          int appNotResponding = iActivityController.appNotResponding(this.mName, this.mPid, str);
          if (appNotResponding != 0) {
            if (appNotResponding < 0) {}
            runnable = runnable2;
          } else {
            runnable = null;
          }
          WindowManagerService.resetPriorityAfterLockedSection();
          if (runnable == null) {
            return false;
          }
          runnable.run();
          return true;
        } catch (RemoteException unused) {
          this.mAtm.mController = null;
          Watchdog.getInstance().setActivityController(null);
          WindowManagerService.resetPriorityAfterLockedSection();
          return false;
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public void onServiceStarted(ServiceInfo serviceInfo) {
    String str = serviceInfo.permission;
    if (str == null) {
      return;
    }
    switch (str) {
      case "android.permission.BIND_VOICE_INTERACTION":
      case "android.permission.BIND_ACCESSIBILITY_SERVICE":
        break;
      case "android.permission.BIND_INPUT_METHOD":
        this.mHasImeService = true;
        break;
      default:
        return;
    }
    this.mIsActivityConfigOverrideAllowed = false;
    unregisterActivityConfigurationListener();
  }

  public void onTopProcChanged() {
    if (this.mAtm.mVrController.isInterestingToSchedGroup()) {
      this.mAtm.f1734mH.post(
          new Runnable() { // from class:
                           // com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              WindowProcessController.this.lambda$onTopProcChanged$2();
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onTopProcChanged$2() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mAtm.mVrController.onTopProcChangedLocked(this);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public boolean isHomeProcess() {
    return this == this.mAtm.mHomeProcess;
  }

  public boolean isPreviousProcess() {
    return this == this.mAtm.mPreviousProcess;
  }

  public boolean isHeavyWeightProcess() {
    return this == this.mAtm.mHeavyWeightProcess;
  }

  public boolean isFactoryTestProcess() {
    ComponentName componentName;
    ActivityTaskManagerService activityTaskManagerService = this.mAtm;
    int i = activityTaskManagerService.mFactoryTest;
    if (i == 0) {
      return false;
    }
    if (i == 1
        && (componentName = activityTaskManagerService.mTopComponent) != null
        && this.mName.equals(componentName.getPackageName())) {
      return true;
    }
    return i == 2 && (this.mInfo.flags & 16) != 0;
  }

  public void setRunningRecentsAnimation(boolean z) {
    if (this.mRunningRecentsAnimation == z) {
      return;
    }
    this.mRunningRecentsAnimation = z;
    updateRunningRemoteOrRecentsAnimation();
  }

  public void setRunningRemoteAnimation(boolean z) {
    if (this.mRunningRemoteAnimation == z) {
      return;
    }
    this.mRunningRemoteAnimation = z;
    updateRunningRemoteOrRecentsAnimation();
  }

  public void updateRunningRemoteOrRecentsAnimation() {
    this.mAtm.f1734mH.sendMessage(
        PooledLambda.obtainMessage(
            new BiConsumer() { // from class:
                               // com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda1
              @Override // java.util.function.BiConsumer
              public final void accept(Object obj, Object obj2) {
                ((WindowProcessListener) obj)
                    .setRunningRemoteAnimation(((Boolean) obj2).booleanValue());
              }
            },
            this.mListener,
            Boolean.valueOf(isRunningRemoteTransition())));
  }

  public boolean isRunningRemoteTransition() {
    return this.mRunningRecentsAnimation || this.mRunningRemoteAnimation;
  }

  public void setRunningAnimationUnsafe() {
    this.mListener.setRunningRemoteAnimation(true);
  }

  public void setOverrideDisplayId(int i) {
    synchronized (this.mAtm.mGlobalLockWithoutBoost) {
      if (this.mDisplayArea != null) {
        Slog.w(
            "ActivityTaskManager",
            "setOverrideDisplayId. ignore override display id because this has displayArea.");
      } else {
        if (this.mOverrideDisplayId == i) {
          return;
        }
        this.mOverrideDisplayId = i;
        unregisterDisplayConfigurationListener();
        if (i == 2) {
          registerDisplayConfigurationListener(this.mAtm.mRootWindowContainer.getDisplayContent(i));
        }
      }
    }
  }

  public int getDisplayId() {
    int i = this.mOverrideDisplayId;
    return i != -1 ? i : this.mDisplayId;
  }

  public void registerDisplayConfigurationListener(DisplayContent displayContent) {
    if (displayContent == null) {
      return;
    }
    unregisterConfigurationListeners();
    this.mDisplayId = displayContent.mDisplayId;
    displayContent.registerConfigurationChangeListener(this);
  }

  public void unregisterDisplayConfigurationListener() {
    int i = this.mDisplayId;
    if (i == -1) {
      return;
    }
    DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(i);
    if (displayContent != null) {
      displayContent.unregisterConfigurationChangeListener(this);
    }
    this.mDisplayId = -1;
    onMergedOverrideConfigurationChanged(Configuration.EMPTY);
  }

  public boolean allActivitiesStoppedAndInvisibleLocked() {
    for (int size = this.mActivities.size() - 1; size >= 0; size--) {
      ActivityRecord activityRecord = (ActivityRecord) this.mActivities.get(size);
      if (!activityRecord.mAppStopped || activityRecord.isVisible()) {
        return false;
      }
    }
    return true;
  }

  public boolean skipToFinishActivities() {
    WindowProcessListener windowProcessListener = this.mListener;
    if (windowProcessListener != null) {
      return windowProcessListener.skipToFinishActivities();
    }
    return false;
  }

  public void setKeepProcessAlive(boolean z) {
    this.mKeepProcessAlive = z;
  }

  public boolean isKeepProcessAlive() {
    return this.mKeepProcessAlive;
  }

  public String getPrimaryDisplayName() {
    return this.mPrimaryDisplayName;
  }

  public void adjustBindAppToDexConfigIfNeeded() {
    if (this.mIsActivityConfigOverrideAllowed) {
      this.mAdjustBindAppToDexConfig = true;
    }
  }

  public void unsetBindAppToDexConfig() {
    this.mAdjustBindAppToDexConfig = false;
  }

  public boolean needBindAppToDexConfig() {
    return this.mAdjustBindAppToDexConfig;
  }

  public void updateActivityInfo(ApplicationInfo applicationInfo) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        for (int size = this.mActivities.size() - 1; size >= 0; size--) {
          ActivityRecord activityRecord = (ActivityRecord) this.mActivities.get(size);
          ActivityInfo activityInfo = activityRecord.info;
          if (activityInfo != null && activityInfo.applicationInfo != null) {
            if (applicationInfo.packageName.equals(activityRecord.packageName)) {
              ApplicationInfo applicationInfo2 = activityRecord.info.applicationInfo;
              applicationInfo2.resourceDirs = applicationInfo.resourceDirs;
              applicationInfo2.overlayPaths = applicationInfo.overlayPaths;
            }
          }
          WindowManagerService.resetPriorityAfterLockedSection();
          return;
        }
        WindowManagerService.resetPriorityAfterLockedSection();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public List getTaskIds() {
    ArrayList arrayList = new ArrayList();
    for (int size = this.mActivities.size() - 1; size >= 0; size--) {
      Task task = ((ActivityRecord) this.mActivities.get(size)).getTask();
      if (task != null && !arrayList.contains(Integer.valueOf(task.mTaskId))) {
        arrayList.add(Integer.valueOf(task.mTaskId));
      }
    }
    return arrayList;
  }

  public void updateTopActivityIfNeeded(ActivityRecord activityRecord) {
    updateActivityConfigurationListener(activityRecord);
  }

  public boolean isActivityConfigOverrideAllowed() {
    return this.mIsActivityConfigOverrideAllowed;
  }

  public String toString() {
    Object obj = this.mOwner;
    if (obj != null) {
      return obj.toString();
    }
    return null;
  }

  public void dump(PrintWriter printWriter, String str) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (this.mActivities.size() > 0) {
          printWriter.print(str);
          printWriter.println("Activities:");
          for (int i = 0; i < this.mActivities.size(); i++) {
            printWriter.print(str);
            printWriter.print("  - ");
            printWriter.println(this.mActivities.get(i));
          }
        }
        ArrayMap arrayMap = this.mRemoteActivities;
        if (arrayMap != null && !arrayMap.isEmpty()) {
          printWriter.print(str);
          printWriter.println("Remote Activities:");
          for (int size = this.mRemoteActivities.size() - 1; size >= 0; size--) {
            printWriter.print(str);
            printWriter.print("  - ");
            printWriter.print(this.mRemoteActivities.keyAt(size));
            printWriter.print(" flags=");
            int i2 = ((int[]) this.mRemoteActivities.valueAt(size))[0];
            if ((i2 & 1) != 0) {
              printWriter.print("host ");
            }
            if ((i2 & 2) != 0) {
              printWriter.print("embedded");
            }
            printWriter.println();
          }
        }
        if (this.mRecentTasks.size() > 0) {
          printWriter.println(str + "Recent Tasks:");
          for (int i3 = 0; i3 < this.mRecentTasks.size(); i3++) {
            printWriter.println(str + "  - " + this.mRecentTasks.get(i3));
          }
        }
        if (this.mVrThreadTid != 0) {
          printWriter.print(str);
          printWriter.print("mVrThreadTid=");
          printWriter.println(this.mVrThreadTid);
        }
        this.mBgLaunchController.dump(printWriter, str);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    printWriter.println(str + " Configuration=" + getConfiguration());
    printWriter.println(str + " OverrideConfiguration=" + getRequestedOverrideConfiguration());
    StringBuilder sb = new StringBuilder();
    sb.append(str);
    sb.append(" mLastReportedConfiguration=");
    sb.append(
        this.mHasCachedConfiguration
            ? "(cached) " + this.mLastReportedConfiguration
            : this.mLastReportedConfiguration);
    printWriter.println(sb.toString());
    int i4 = this.mActivityStateFlags;
    if (i4 != 65535) {
      printWriter.print(str + " mActivityStateFlags=");
      if ((1048576 & i4) != 0) {
        printWriter.print("W|");
      }
      if ((65536 & i4) != 0) {
        printWriter.print("V|");
        if ((2097152 & i4) != 0) {
          printWriter.print("R|");
        }
      } else if ((131072 & i4) != 0) {
        printWriter.print("P|");
      } else if ((262144 & i4) != 0) {
        printWriter.print("S|");
        if ((524288 & i4) != 0) {
          printWriter.print("F|");
        }
      }
      if ((4194304 & i4) != 0) {
        printWriter.print("VT|");
      }
      int i5 = i4 & GnssNative.GNSS_AIDING_TYPE_ALL;
      if (i5 != 65535) {
        printWriter.print("taskLayer=" + i5);
      }
      printWriter.println();
    }
    printWriter.println(str + " mDisplayId=" + this.mDisplayId);
  }

  public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
    this.mListener.dumpDebug(protoOutputStream, j);
  }

  public void updateGlobalConfiguration() {
    onConfigurationChanged(this.mAtm.getGlobalConfiguration());
  }
}
