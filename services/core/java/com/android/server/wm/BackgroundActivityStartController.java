package com.android.server.wm;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.BackgroundStartPrivileges;
import android.app.admin.DeviceStateCache;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmRegionConfig$$ExternalSyntheticOutline0;
import android.metrics.LogMaker;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.util.Slog;
import android.widget.Toast;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledPredicate;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.UiThread;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.PendingIntentRecord;
import com.android.server.pm.PackageManagerService;
import com.android.server.wm.BackgroundActivityStartController;
import com.android.window.flags.Flags;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public final class BackgroundActivityStartController {
    public static final ActivityOptions ACTIVITY_OPTIONS_SYSTEM_DEFINED = ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(0).setPendingIntentCreatorBackgroundActivityStartMode(0);
    public final ActivityTaskManagerService mService;
    public final ActivityTaskSupervisor mSupervisor;
    public final HashMap mTaskIdToFinishedActivity = new HashMap();
    public FinishedActivityEntry mTopFinishedActivity = null;

    class BalState {
        public final int mAppSwitchState;
        public final boolean mAutoOptInCaller;
        public final String mAutoOptInReason;
        public final BackgroundStartPrivileges mBalAllowedByPiCreator;
        public final BackgroundStartPrivileges mBalAllowedByPiCreatorWithHardening;
        public final BackgroundStartPrivileges mBalAllowedByPiSender;
        public final WindowProcessController mCallerApp;
        public final String mCallingPackage;
        public final int mCallingPid;
        public final int mCallingUid;
        public final boolean mCallingUidHasAnyVisibleWindow;
        public final int mCallingUidProcState;
        public final ActivityOptions mCheckedOptions;
        public final BackgroundStartPrivileges mForcedBalByPiSender;
        public final Intent mIntent;
        public final boolean mIsCallForResult;
        public final boolean mIsCallingUidPersistentSystemProcess;
        public final boolean mIsRealCallingUidPersistentSystemProcess;
        public final PendingIntentRecord mOriginatingPendingIntent;
        public final WindowProcessController mRealCallerApp;
        public final String mRealCallingPackage;
        public final int mRealCallingPid;
        public final int mRealCallingUid;
        public final boolean mRealCallingUidHasAnyVisibleWindow;
        public final int mRealCallingUidProcState;
        public BalVerdict mResultForCaller;
        public BalVerdict mResultForRealCaller;
        public final /* synthetic */ BackgroundActivityStartController this$0;

        /* JADX WARN: Removed duplicated region for block: B:104:0x0096  */
        /* JADX WARN: Removed duplicated region for block: B:115:0x007a  */
        /* JADX WARN: Removed duplicated region for block: B:11:0x0078  */
        /* JADX WARN: Removed duplicated region for block: B:14:0x0090  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x00cd  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x0145  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x017d A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:62:0x014f  */
        /* JADX WARN: Removed duplicated region for block: B:73:0x00da  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public BalState(com.android.server.wm.BackgroundActivityStartController r17, int r18, int r19, java.lang.String r20, int r21, int r22, com.android.server.wm.WindowProcessController r23, com.android.server.am.PendingIntentRecord r24, android.app.BackgroundStartPrivileges r25, com.android.server.wm.ActivityRecord r26, android.content.Intent r27, android.app.ActivityOptions r28) {
            /*
                Method dump skipped, instructions count: 484
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.BackgroundActivityStartController.BalState.<init>(com.android.server.wm.BackgroundActivityStartController, int, int, java.lang.String, int, int, com.android.server.wm.WindowProcessController, com.android.server.am.PendingIntentRecord, android.app.BackgroundStartPrivileges, com.android.server.wm.ActivityRecord, android.content.Intent, android.app.ActivityOptions):void");
        }

        public final String getDebugPackageName(int i, String str) {
            if (str != null) {
                return str;
            }
            if (i == 0) {
                return "root[debugOnly]";
            }
            String nameForUid = this.this$0.mService.getPackageManagerInternalLocked().getNameForUid(i);
            if (nameForUid == null) {
                nameForUid = VibrationParam$1$$ExternalSyntheticOutline0.m(i, "uid=");
            }
            return ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(nameForUid, "[debugOnly]");
        }

        public final String toString() {
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(2048, "[callingPackage: ");
            String str = this.mCallingPackage;
            int i = this.mCallingUid;
            m.append(getDebugPackageName(i, str));
            m.append("; callingPackageTargetSdk: ");
            ActivityOptions activityOptions = BackgroundActivityStartController.ACTIVITY_OPTIONS_SYSTEM_DEFINED;
            BackgroundActivityStartController backgroundActivityStartController = this.this$0;
            m.append(backgroundActivityStartController.getTargetSdk(str));
            m.append("; callingUid: ");
            m.append(i);
            m.append("; callingPid: ");
            m.append(this.mCallingPid);
            m.append("; appSwitchState: ");
            m.append(this.mAppSwitchState);
            m.append("; callingUidHasAnyVisibleWindow: ");
            m.append(this.mCallingUidHasAnyVisibleWindow);
            m.append("; callingUidProcState: ");
            m.append(DebugUtils.valueToString(ActivityManager.class, "PROCESS_STATE_", this.mCallingUidProcState));
            m.append("; isCallingUidPersistentSystemProcess: ");
            m.append(this.mIsCallingUidPersistentSystemProcess);
            m.append("; forcedBalByPiSender: ");
            m.append(this.mForcedBalByPiSender);
            m.append("; intent: ");
            m.append(this.mIntent);
            m.append("; callerApp: ");
            WindowProcessController windowProcessController = this.mCallerApp;
            m.append(windowProcessController);
            if (windowProcessController != null) {
                m.append("; inVisibleTask: ");
                m.append(windowProcessController.hasActivityInVisibleTask());
            }
            m.append("; balAllowedByPiCreator: ");
            m.append(this.mBalAllowedByPiCreator);
            m.append("; balAllowedByPiCreatorWithHardening: ");
            m.append(this.mBalAllowedByPiCreatorWithHardening);
            m.append("; resultIfPiCreatorAllowsBal: ");
            m.append(this.mResultForCaller);
            m.append("; hasRealCaller: ");
            boolean z = false;
            int i2 = this.mRealCallingUid;
            m.append(i2 != -1);
            m.append("; isCallForResult: ");
            m.append(this.mIsCallForResult);
            m.append("; isPendingIntent: ");
            PendingIntentRecord pendingIntentRecord = this.mOriginatingPendingIntent;
            if (pendingIntentRecord != null && i2 != -1) {
                z = true;
            }
            m.append(z);
            m.append("; autoOptInReason: ");
            m.append(this.mAutoOptInReason);
            if (i2 != -1) {
                m.append("; realCallingPackage: ");
                String str2 = this.mRealCallingPackage;
                m.append(getDebugPackageName(i2, str2));
                m.append("; realCallingPackageTargetSdk: ");
                m.append(backgroundActivityStartController.getTargetSdk(str2));
                m.append("; realCallingUid: ");
                m.append(i2);
                m.append("; realCallingPid: ");
                m.append(this.mRealCallingPid);
                m.append("; realCallingUidHasAnyVisibleWindow: ");
                m.append(this.mRealCallingUidHasAnyVisibleWindow);
                m.append("; realCallingUidProcState: ");
                m.append(DebugUtils.valueToString(ActivityManager.class, "PROCESS_STATE_", this.mRealCallingUidProcState));
                m.append("; isRealCallingUidPersistentSystemProcess: ");
                m.append(this.mIsRealCallingUidPersistentSystemProcess);
                m.append("; originatingPendingIntent: ");
                m.append(pendingIntentRecord);
                m.append("; realCallerApp: ");
                WindowProcessController windowProcessController2 = this.mRealCallerApp;
                m.append(windowProcessController2);
                if (windowProcessController2 != null) {
                    m.append("; realInVisibleTask: ");
                    m.append(windowProcessController2.hasActivityInVisibleTask());
                }
                m.append("; balAllowedByPiSender: ");
                m.append(this.mBalAllowedByPiSender);
                m.append("; resultIfPiSenderAllowsBal: ");
                m.append(this.mResultForRealCaller);
            }
            m.append("; balImproveRealCallerVisibilityCheck: ");
            m.append(Flags.balImproveRealCallerVisibilityCheck());
            m.append("; balRequireOptInByPendingIntentCreator: ");
            m.append(Flags.balRequireOptInByPendingIntentCreator());
            m.append("; balRequireOptInSameUid: ");
            m.append(Flags.balRequireOptInSameUid());
            m.append("; balRespectAppSwitchStateWhenCheckBoundByForegroundUid: ");
            m.append(Flags.balRespectAppSwitchStateWhenCheckBoundByForegroundUid());
            m.append("; balDontBringExistingBackgroundTaskStackToFg: ");
            m.append(Flags.balDontBringExistingBackgroundTaskStackToFg());
            m.append("]");
            return m.toString();
        }
    }

    public final class BalVerdict {
        public boolean mBasedOnRealCaller;
        public final int mCode;
        public boolean mOnlyCreatorAllows;
        public static final BalVerdict BLOCK = new BalVerdict(0);
        public static final BalVerdict ALLOW_BY_DEFAULT = new BalVerdict(1);
        public static final BalVerdict ALLOW_PRIVILEGED = new BalVerdict(2);

        public BalVerdict(int i) {
            this.mCode = i;
        }

        public final boolean allows() {
            return !(this.mCode == 0);
        }

        public BalVerdict setBasedOnRealCaller() {
            this.mBasedOnRealCaller = true;
            return this;
        }

        public final String toString() {
            return BackgroundActivityStartController.balCodeToString(this.mCode);
        }
    }

    public final class BlockActivityStart {
        public ActivityRecord mActivityOptedIn;
        public boolean mTopActivityMatchesSource;
        public boolean mTopActivityOptedIn;

        public final void optedIn(ActivityRecord activityRecord) {
            this.mTopActivityOptedIn = true;
            if (this.mActivityOptedIn == null) {
                this.mActivityOptedIn = activityRecord;
            }
        }
    }

    public final class FinishedActivityEntry {
        public final String mDebugInfo;
        public final int mLaunchCount;
        public final int mTaskId;
        public final int mUid;

        public FinishedActivityEntry(ActivityRecord activityRecord) {
            FinishedActivityEntry finishedActivityEntry = (FinishedActivityEntry) BackgroundActivityStartController.this.mTaskIdToFinishedActivity.get(Integer.valueOf(activityRecord.task.mTaskId));
            final int i = activityRecord.task.mTaskId;
            this.mUid = activityRecord.getUid();
            this.mTaskId = i;
            int i2 = 1;
            if (finishedActivityEntry != null && activityRecord.isUid(finishedActivityEntry.mUid)) {
                i2 = 1 + finishedActivityEntry.mLaunchCount;
            }
            this.mLaunchCount = i2;
            this.mDebugInfo = BackgroundActivityStartController.getDebugStringForActivityRecord(activityRecord);
            BackgroundActivityStartController.this.mService.mH.postDelayed(new Runnable() { // from class: com.android.server.wm.BackgroundActivityStartController$FinishedActivityEntry$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BackgroundActivityStartController.FinishedActivityEntry finishedActivityEntry2 = BackgroundActivityStartController.FinishedActivityEntry.this;
                    int i3 = i;
                    WindowManagerGlobalLock windowManagerGlobalLock = BackgroundActivityStartController.this.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            if (BackgroundActivityStartController.this.mTaskIdToFinishedActivity.get(Integer.valueOf(i3)) == finishedActivityEntry2) {
                                BackgroundActivityStartController.this.mTaskIdToFinishedActivity.remove(Integer.valueOf(i3));
                            }
                            BackgroundActivityStartController backgroundActivityStartController = BackgroundActivityStartController.this;
                            if (backgroundActivityStartController.mTopFinishedActivity == finishedActivityEntry2) {
                                backgroundActivityStartController.mTopFinishedActivity = null;
                            }
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            }, 3000L);
        }
    }

    public BackgroundActivityStartController(ActivityTaskManagerService activityTaskManagerService, ActivityTaskSupervisor activityTaskSupervisor) {
        this.mService = activityTaskManagerService;
        this.mSupervisor = activityTaskSupervisor;
    }

    public static String balCodeToString(int i) {
        switch (i) {
            case 0:
                return "BAL_BLOCK";
            case 1:
                return "BAL_ALLOW_DEFAULT";
            case 2:
                return "BAL_ALLOW_ALLOWLISTED_UID";
            case 3:
                return "BAL_ALLOW_ALLOWLISTED_COMPONENT";
            case 4:
                return "BAL_ALLOW_VISIBLE_WINDOW";
            case 5:
                return "BAL_ALLOW_PENDING_INTENT";
            case 6:
                return "BAL_ALLOW_PERMISSION";
            case 7:
                return "BAL_ALLOW_SAW_PERMISSION";
            case 8:
                return "BAL_ALLOW_GRACE_PERIOD";
            case 9:
                return "BAL_ALLOW_FOREGROUND";
            case 10:
                return "BAL_ALLOW_SDK_SANDBOX";
            case 11:
                return "BAL_ALLOW_NON_APP_VISIBLE_WINDOW";
            default:
                throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unexpected value: "));
        }
    }

    public static String getDebugStringForActivityRecord(ActivityRecord activityRecord) {
        return activityRecord + " :: visible=" + activityRecord.mVisible + ", visibleRequested=" + activityRecord.isVisibleRequested() + ", finishing=" + activityRecord.finishing + ", alwaysOnTop=" + activityRecord.isAlwaysOnTop() + ", lastLaunchTime=" + activityRecord.lastLaunchTime + ", lastVisibleTime=" + activityRecord.lastVisibleTime + ", taskFragment=" + activityRecord.getTaskFragment();
    }

    public final void abortLaunch(BalState balState) {
        Slog.wtf("ActivityTaskManager", "Background activity launch blocked! " + balState);
        if (Flags.balShowToastsBlocked() && (balState.mResultForCaller.allows() || balState.mResultForRealCaller.allows())) {
            showToast("BAL blocked. goo.gle/android-bal");
        }
        statsLog(BalVerdict.BLOCK, balState);
    }

    public final void checkActivityAllowedToClearTask(Task task, int i, int i2) {
        TaskDisplayArea taskDisplayArea;
        CharSequence charSequence;
        String str;
        String str2;
        if (i == 1000 || !task.isVisible() || task.inMultiWindowMode()) {
            return;
        }
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        int i3 = checkBackgroundActivityStartAllowedByCaller(new BalState(this, i, i2, activityTaskManagerService.mContext.getPackageManager().getNameForUid(i), -1, -1, null, null, null, null, null, ActivityOptions.makeBasic())).mCode;
        if (i3 == 2 || i3 == 3 || i3 == 6 || i3 == 7 || i3 == 4 || i3 == 11 || (taskDisplayArea = task.getTaskDisplayArea()) == null) {
            return;
        }
        ActivityRecord activityRecord = null;
        BlockActivityStart checkTopActivityForAsm = checkTopActivityForAsm(task, i, null, new BlockActivityStart());
        if (checkTopActivityForAsm.mTopActivityMatchesSource) {
            return;
        }
        ActivityRecord activity = task.getActivity(new BackgroundActivityStartController$$ExternalSyntheticLambda0(0));
        FrameworkStatsLog.write(FrameworkStatsLog.ACTIVITY_ACTION_BLOCKED, i, (String) null, activity == null ? -1 : activity.getUid(), activity == null ? null : activity.info.name, false, -1, (String) null, (String) null, 0, 4, 11, false, -1, (String) null);
        boolean z = ActivitySecurityModelFeatureFlags.shouldRestrictActivitySwitch(i) && checkTopActivityForAsm.mTopActivityOptedIn;
        PackageManager packageManager = activityTaskManagerService.mContext.getPackageManager();
        String nameForUid = packageManager.getNameForUid(i);
        if (nameForUid == null) {
            str = String.valueOf(i);
            str2 = str;
        } else {
            try {
                charSequence = packageManager.getApplicationLabel(packageManager.getApplicationInfo(nameForUid, PackageManager.ApplicationInfoFlags.of(0L)));
            } catch (PackageManager.NameNotFoundException unused) {
                charSequence = nameForUid;
            }
            str = charSequence;
            str2 = nameForUid;
        }
        if (ActivitySecurityModelFeatureFlags.shouldShowToast(i)) {
            StringBuilder sb = new StringBuilder("go/android-asm");
            sb.append(z ? " returned home due to " : " would return home due to ");
            sb.append((Object) str);
            showToast(sb.toString());
        }
        if (!z) {
            Slog.i("ActivityTaskManager", "[ASM] Would return to home as source: " + str2 + " is not on top of task t: " + task);
            return;
        }
        Slog.w("ActivityTaskManager", "[ASM] Return to home as source: " + str2 + " is not on top of task t: " + task);
        int i4 = taskDisplayArea.mRootWindowContainer.mCurrentUser;
        Task task2 = taskDisplayArea.mRootHomeTask;
        if (task2 != null) {
            PooledPredicate obtainPredicate = PooledLambda.obtainPredicate(new TaskDisplayArea$$ExternalSyntheticLambda2(), PooledLambda.__(ActivityRecord.class), Integer.valueOf(i4));
            activityRecord = task2.getActivity(obtainPredicate);
            obtainPredicate.recycle();
        }
        if (activityRecord == null) {
            taskDisplayArea.moveHomeRootTaskToFront("taskRemoved");
        } else {
            activityRecord.moveFocusableActivityToTop("taskRemoved");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:95:0x01dc, code lost:
    
        if ((r14.mCheckedOptions.getPendingIntentBackgroundActivityStartMode() == 2) == false) goto L149;
     */
    /* JADX WARN: Removed duplicated region for block: B:135:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01d1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.wm.BackgroundActivityStartController.BalVerdict checkBackgroundActivityStart(int r16, int r17, java.lang.String r18, int r19, int r20, com.android.server.wm.WindowProcessController r21, com.android.server.am.PendingIntentRecord r22, android.app.BackgroundStartPrivileges r23, com.android.server.wm.ActivityRecord r24, android.content.Intent r25, android.app.ActivityOptions r26) {
        /*
            Method dump skipped, instructions count: 561
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.BackgroundActivityStartController.checkBackgroundActivityStart(int, int, java.lang.String, int, int, com.android.server.wm.WindowProcessController, com.android.server.am.PendingIntentRecord, android.app.BackgroundStartPrivileges, com.android.server.wm.ActivityRecord, android.content.Intent, android.app.ActivityOptions):com.android.server.wm.BackgroundActivityStartController$BalVerdict");
    }

    public final BalVerdict checkBackgroundActivityStartAllowedByCaller(BalState balState) {
        int i = balState.mAppSwitchState;
        if ((i == 2 || i == 1) && balState.mCallingUidHasAnyVisibleWindow) {
            return new BalVerdict(4);
        }
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        MirrorActiveUids mirrorActiveUids = activityTaskManagerService.mActiveUids;
        int i2 = balState.mCallingUid;
        if (mirrorActiveUids.hasNonAppVisibleWindow(i2)) {
            return new BalVerdict(11);
        }
        int appId = UserHandle.getAppId(i2);
        if (i2 == 0 || appId == 1000 || appId == 1027) {
            return new BalVerdict(2);
        }
        String str = balState.mCallingPackage;
        if (isHomeApp(i2, str)) {
            return new BalVerdict(3);
        }
        WindowState currentInputMethodWindow = activityTaskManagerService.mRootWindowContainer.getCurrentInputMethodWindow();
        if ((currentInputMethodWindow == null || appId != currentInputMethodWindow.mOwnerUid) && !balState.mIsCallingUidPersistentSystemProcess) {
            int i3 = balState.mCallingPid;
            if (hasBalPermission(i2, i3)) {
                return new BalVerdict(6);
            }
            if (this.mSupervisor.mRecentTasks.isCallerRecents(i2)) {
                return new BalVerdict(3);
            }
            if (i2 >= 0 && activityTaskManagerService.mDeviceOwnerUid == i2) {
                return new BalVerdict(3);
            }
            if (i2 >= 0 && activityTaskManagerService.mProfileOwnerUids.contains(Integer.valueOf(i2)) && DeviceStateCache.getInstance().hasAffiliationWithDevice(UserHandle.getUserId(i2))) {
                return new BalVerdict(3);
            }
            Set set = (Set) ((ArrayMap) activityTaskManagerService.mCompanionAppUidsMap).get(Integer.valueOf(UserHandle.getUserId(i2)));
            if (set == null ? false : set.contains(Integer.valueOf(i2))) {
                return new BalVerdict(3);
            }
            if (activityTaskManagerService.hasSystemAlertWindowPermission(i2, i3, str)) {
                Slog.w("ActivityTaskManager", "Background activity start for " + str + " allowed because SYSTEM_ALERT_WINDOW permission is granted.");
                return new BalVerdict(7);
            }
            if (DeviceConfig.getBoolean("window_manager", "system_exempt_from_activity_bg_start_restriction_enabled", true) && activityTaskManagerService.getAppOpsManager().checkOpNoThrow(130, i2, str) == 0) {
                return new BalVerdict(6);
            }
            BalVerdict checkProcessAllowsBal = checkProcessAllowsBal(balState.mCallerApp, balState);
            return checkProcessAllowsBal.allows() ? checkProcessAllowsBal : BalVerdict.BLOCK;
        }
        return new BalVerdict(3);
    }

    public final BlockActivityStart checkCrossUidActivitySwitchFromBelow(ActivityRecord activityRecord, int i, BlockActivityStart blockActivityStart) {
        if (activityRecord.isUid(i)) {
            blockActivityStart.mTopActivityMatchesSource = true;
            return blockActivityStart;
        }
        if (activityRecord.mAllowCrossUidActivitySwitchFromBelow) {
            blockActivityStart.mTopActivityOptedIn = false;
            blockActivityStart.mTopActivityMatchesSource = true;
            return blockActivityStart;
        }
        if (activityRecord.isUid(1000)) {
            if (android.security.Flags.asmOptSystemIntoEnforcement()) {
                blockActivityStart.optedIn(activityRecord);
            }
            return blockActivityStart;
        }
        if (!CompatChanges.isChangeEnabled(230590090L, activityRecord.getUid())) {
            return blockActivityStart;
        }
        String str = activityRecord.packageName;
        if (str == null) {
            Slog.wtf("ActivityTaskManager", "Package name: " + activityRecord + " not found.");
            blockActivityStart.optedIn(activityRecord);
            return blockActivityStart;
        }
        try {
            if (!this.mService.mContext.getPackageManager().getApplicationInfo(str, 0).allowCrossUidActivitySwitchFromBelow) {
                blockActivityStart.optedIn(activityRecord);
            }
            return blockActivityStart;
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.wtf("ActivityTaskManager", "Package name: " + str + " not found.");
            blockActivityStart.optedIn(activityRecord);
            return blockActivityStart;
        }
    }

    public BalVerdict checkProcessAllowsBal(WindowProcessController windowProcessController, BalState balState) {
        BalVerdict balVerdict = BalVerdict.BLOCK;
        if (windowProcessController == null) {
            return balVerdict;
        }
        BalVerdict areBackgroundActivityStartsAllowed = windowProcessController.areBackgroundActivityStartsAllowed(balState.mAppSwitchState, false);
        if (areBackgroundActivityStartsAllowed.allows()) {
            return areBackgroundActivityStartsAllowed;
        }
        ArraySet arraySet = (ArraySet) ((HashMap) this.mService.mProcessMap.mUidMap).get(Integer.valueOf(windowProcessController.mUid));
        if (arraySet != null) {
            for (int size = arraySet.size() - 1; size >= 0; size--) {
                WindowProcessController windowProcessController2 = (WindowProcessController) arraySet.valueAt(size);
                if (windowProcessController2 != windowProcessController) {
                    BalVerdict areBackgroundActivityStartsAllowed2 = windowProcessController2.areBackgroundActivityStartsAllowed(balState.mAppSwitchState, false);
                    if (areBackgroundActivityStartsAllowed2.allows()) {
                        return areBackgroundActivityStartsAllowed2;
                    }
                }
            }
        }
        return balVerdict;
    }

    public final BlockActivityStart checkTopActivityForAsm(Task task, final int i, final ActivityRecord activityRecord, BlockActivityStart blockActivityStart) {
        TaskFragment taskFragment;
        TaskFragment taskFragment2;
        ActivityRecord activity;
        if (activityRecord != null && activityRecord.isVisibleRequested()) {
            blockActivityStart.mTopActivityMatchesSource = true;
            return blockActivityStart;
        }
        ActivityRecord topMostActivity = task.getTopMostActivity();
        if (topMostActivity == null) {
            Slog.wtf("ActivityTaskManager", "Activities for task: " + task + " not found.");
            blockActivityStart.optedIn(topMostActivity);
            return blockActivityStart;
        }
        BlockActivityStart checkCrossUidActivitySwitchFromBelow = checkCrossUidActivitySwitchFromBelow(topMostActivity, i, blockActivityStart);
        if (checkCrossUidActivitySwitchFromBelow.mTopActivityMatchesSource) {
            return checkCrossUidActivitySwitchFromBelow;
        }
        if (task.forAllActivities(new Predicate() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                return activityRecord2.isUid(i) && activityRecord2.isVisibleRequested();
            }
        })) {
            checkCrossUidActivitySwitchFromBelow.mTopActivityMatchesSource = true;
            return checkCrossUidActivitySwitchFromBelow;
        }
        Predicate predicate = new Predicate() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                return activityRecord2.equals(ActivityRecord.this) || !(activityRecord2.finishing || activityRecord2.isAlwaysOnTop());
            }
        };
        ActivityRecord activity2 = task.getActivity(predicate);
        if (activity2 == null) {
            return checkCrossUidActivitySwitchFromBelow;
        }
        BlockActivityStart checkCrossUidActivitySwitchFromBelow2 = checkCrossUidActivitySwitchFromBelow(activity2, i, checkCrossUidActivitySwitchFromBelow);
        return (checkCrossUidActivitySwitchFromBelow2.mTopActivityMatchesSource || (taskFragment = activity2.getTaskFragment()) == null || (taskFragment2 = taskFragment.mAdjacentTaskFragment) == null || (activity = taskFragment2.getActivity(predicate)) == null) ? checkCrossUidActivitySwitchFromBelow2 : checkCrossUidActivitySwitchFromBelow(activity, i, checkCrossUidActivitySwitchFromBelow2);
    }

    /* JADX WARN: Type inference failed for: r8v0, types: [com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda5] */
    public final String getDebugInfoForActivitySecurity(String str, final ActivityRecord activityRecord, final ActivityRecord activityRecord2, Task task, final ActivityRecord activityRecord3, int i, int i2, boolean z, boolean z2, boolean z3, boolean z4, ActivityRecord activityRecord4) {
        Task task2 = task;
        final ?? r8 = new Function() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ActivityRecord activityRecord5 = ActivityRecord.this;
                ActivityRecord activityRecord6 = activityRecord3;
                ActivityRecord activityRecord7 = activityRecord2;
                ActivityRecord activityRecord8 = (ActivityRecord) obj;
                if (activityRecord8 == null) {
                    return null;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(activityRecord8 == activityRecord5 ? " [source]=> " : activityRecord8 == activityRecord6 ? " [ top  ]=> " : activityRecord8 == activityRecord7 ? " [target]=> " : "         => ");
                sb.append(BackgroundActivityStartController.getDebugStringForActivityRecord(activityRecord8));
                return sb.toString();
            }
        };
        final StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add("[ASM] ------ Activity Security " + str + " Debug Logging Start ------");
        StringBuilder sb = new StringBuilder("[ASM] Block Enabled: ");
        sb.append(z);
        stringJoiner.add(sb.toString());
        boolean z5 = false;
        if (!z) {
            stringJoiner.add("[ASM] Feature Flag Enabled: " + android.security.Flags.asmRestrictionsEnabled());
            StringBuilder sb2 = new StringBuilder("[ASM] Mendel Override: ");
            sb2.append(ActivitySecurityModelFeatureFlags.sAsmRestrictionsEnabled == 2);
            stringJoiner.add(sb2.toString());
        }
        stringJoiner.add("[ASM] ASM Version: 11");
        stringJoiner.add("[ASM] System Time: " + SystemClock.uptimeMillis());
        stringJoiner.add("[ASM] Activity Opted In: " + ((String) r8.apply(activityRecord4)));
        if (task2 != null && activityRecord != null && activityRecord.task == task2) {
            z5 = true;
        }
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        if (activityRecord == null) {
            stringJoiner.add("[ASM] Source Package: " + activityRecord2.launchedFromPackage);
            stringJoiner.add("[ASM] Real Calling Uid Package: " + activityTaskManagerService.mContext.getPackageManager().getNameForUid(i));
        } else {
            StringBuilder m = AmFmRegionConfig$$ExternalSyntheticOutline0.m(activityRecord.launchedFromPackage, "[ASM] Source Launch Intent: ", AmFmRegionConfig$$ExternalSyntheticOutline0.m((String) r8.apply(activityRecord), "[ASM] Source Launch Package: ", new StringBuilder("[ASM] Source Record: "), stringJoiner), stringJoiner);
            m.append(activityRecord.intent);
            stringJoiner.add(m.toString());
            if (z5) {
                stringJoiner.add("[ASM] Source/Target Task: " + activityRecord.task);
                stringJoiner.add("[ASM] Source/Target Task Stack: ");
            } else {
                stringJoiner.add("[ASM] Source Task: " + activityRecord.task);
                stringJoiner.add("[ASM] Source Task Stack: ");
            }
            final int i3 = 0;
            activityRecord.task.forAllActivities(new Consumer() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i3) {
                        case 0:
                            stringJoiner.add("[ASM] " + ((String) r8.apply((ActivityRecord) obj)));
                            break;
                        default:
                            stringJoiner.add("[ASM] " + ((String) r8.apply((ActivityRecord) obj)));
                            break;
                    }
                }
            });
        }
        stringJoiner.add("[ASM] Target Task Top: " + ((String) r8.apply(activityRecord3)));
        if (!z5) {
            stringJoiner.add("[ASM] Target Task: " + task2);
            if (task2 != null) {
                stringJoiner.add("[ASM] Target Task Stack: ");
                final int i4 = 1;
                task2.forAllActivities(new Consumer() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        switch (i4) {
                            case 0:
                                stringJoiner.add("[ASM] " + ((String) r8.apply((ActivityRecord) obj)));
                                break;
                            default:
                                stringJoiner.add("[ASM] " + ((String) r8.apply((ActivityRecord) obj)));
                                break;
                        }
                    }
                });
            }
        }
        StringBuilder m2 = AmFmRegionConfig$$ExternalSyntheticOutline0.m((String) r8.apply(activityRecord2), "[ASM] Intent: ", new StringBuilder("[ASM] Target Record: "), stringJoiner);
        m2.append(activityRecord2.intent);
        stringJoiner.add(m2.toString());
        stringJoiner.add("[ASM] TaskToFront: " + z2);
        stringJoiner.add("[ASM] AvoidMoveToFront: " + z3);
        stringJoiner.add("[ASM] BalCode: ".concat(balCodeToString(i2)));
        stringJoiner.add("[ASM] Allowed By Grace Period: " + z4);
        StringBuilder m3 = AmFmRegionConfig$$ExternalSyntheticOutline0.m((String) r8.apply(activityTaskManagerService.mLastResumedActivity), "[ASM] System opted into enforcement: ", new StringBuilder("[ASM] LastResumedActivity: "), stringJoiner);
        m3.append(android.security.Flags.asmOptSystemIntoEnforcement());
        stringJoiner.add(m3.toString());
        if (this.mTopFinishedActivity != null) {
            stringJoiner.add("[ASM] TopFinishedActivity: " + this.mTopFinishedActivity.mDebugInfo);
        }
        if (!this.mTaskIdToFinishedActivity.isEmpty()) {
            stringJoiner.add("[ASM] TaskIdToFinishedActivity: ");
            final int i5 = 0;
            this.mTaskIdToFinishedActivity.values().forEach(new Consumer() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i6 = i5;
                    StringJoiner stringJoiner2 = stringJoiner;
                    switch (i6) {
                        case 0:
                            stringJoiner2.add("[ASM]   " + ((BackgroundActivityStartController.FinishedActivityEntry) obj).mDebugInfo);
                            break;
                        default:
                            stringJoiner2.add("[ASM]    T: " + ((Task) obj).toFullString());
                            break;
                    }
                }
            });
        }
        if (i2 == 4 || i2 == 11 || i2 == 9) {
            if (activityRecord != null) {
                task2 = activityRecord.task;
            }
            if (task2 != null && task2.getDisplayArea() != null) {
                stringJoiner.add("[ASM] Tasks: ");
                final int i6 = 1;
                task2.getDisplayArea().forAllTasks(new Consumer() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i62 = i6;
                        StringJoiner stringJoiner2 = stringJoiner;
                        switch (i62) {
                            case 0:
                                stringJoiner2.add("[ASM]   " + ((BackgroundActivityStartController.FinishedActivityEntry) obj).mDebugInfo);
                                break;
                            default:
                                stringJoiner2.add("[ASM]    T: " + ((Task) obj).toFullString());
                                break;
                        }
                    }
                });
            }
        }
        stringJoiner.add("[ASM] ------ Activity Security " + str + " Debug Logging End ------");
        return stringJoiner.toString();
    }

    public final int getTargetSdk(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return this.mService.mContext.getPackageManager().getTargetSdkVersion(str);
        } catch (Exception unused) {
            return -1;
        }
    }

    public boolean hasBalPermission(int i, int i2) {
        Boolean bool = ActivityTaskManagerService.sIsPip2ExperimentEnabled;
        return ActivityManagerService.checkComponentPermission(i2, i, "android.permission.START_ACTIVITIES_FROM_BACKGROUND", 0, -1, true) == 0;
    }

    public final boolean isHomeApp(int i, String str) {
        if (this.mService.mHomeProcess != null) {
            return i == this.mService.mHomeProcess.mUid;
        }
        if (str == null) {
            return false;
        }
        ComponentName defaultHomeActivity = ((PackageManagerService.PackageManagerInternalImpl) this.mService.getPackageManagerInternalLocked()).mService.snapshotComputer().getDefaultHomeActivity(UserHandle.getUserId(i));
        return defaultHomeActivity != null && str.equals(defaultHomeActivity.getPackageName());
    }

    public final void onActivityRequestedFinishing(ActivityRecord activityRecord) {
        FinishedActivityEntry finishedActivityEntry = (FinishedActivityEntry) this.mTaskIdToFinishedActivity.get(Integer.valueOf(activityRecord.task.mTaskId));
        if (finishedActivityEntry == null || !activityRecord.isUid(finishedActivityEntry.mUid) || finishedActivityEntry.mLaunchCount <= 5) {
            if (activityRecord.isVisibleRequested() || activityRecord == activityRecord.task.getTopMostActivity()) {
                FinishedActivityEntry finishedActivityEntry2 = new FinishedActivityEntry(activityRecord);
                this.mTaskIdToFinishedActivity.put(Integer.valueOf(activityRecord.task.mTaskId), finishedActivityEntry2);
                if (activityRecord.task.mVisibleRequested) {
                    this.mTopFinishedActivity = finishedActivityEntry2;
                }
            }
        }
    }

    public final void onNewActivityLaunched(ActivityRecord activityRecord) {
        Task task = activityRecord.task;
        if (task == null) {
            return;
        }
        if (task.mVisibleRequested) {
            this.mTopFinishedActivity = null;
        }
        FinishedActivityEntry finishedActivityEntry = (FinishedActivityEntry) this.mTaskIdToFinishedActivity.get(Integer.valueOf(task.mTaskId));
        if (finishedActivityEntry != null) {
            Task task2 = activityRecord.task;
            int i = finishedActivityEntry.mTaskId;
            if (task2.mTaskId == i) {
                this.mTaskIdToFinishedActivity.remove(Integer.valueOf(i));
            }
        }
    }

    public boolean shouldLogIntentActivity(BalVerdict balVerdict, BalState balState) {
        if (balVerdict.mBasedOnRealCaller) {
            if (balState.mRealCallingUid >= 10000) {
                return false;
            }
        } else if (balState.mCallingUid >= 10000) {
            return false;
        }
        return true;
    }

    public boolean shouldLogStats(BalVerdict balVerdict, BalState balState) {
        if (balVerdict.mCode == 4) {
            if (!((balState.mOriginatingPendingIntent == null || balState.mRealCallingUid == -1) ? false : true) || balVerdict.mBasedOnRealCaller) {
                return false;
            }
        }
        return true;
    }

    public void showToast(final String str) {
        UiThread.getHandler().post(new Runnable() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BackgroundActivityStartController backgroundActivityStartController = BackgroundActivityStartController.this;
                Toast.makeText(backgroundActivityStartController.mService.mContext, str, 1).show();
            }
        });
    }

    public final void statsLog(BalVerdict balVerdict, BalState balState) {
        String str;
        BackgroundActivityStartController backgroundActivityStartController;
        String flattenToShortString;
        if (balVerdict.mCode == 0 && this.mService.mAmInternal.isActivityStartsLoggingEnabled()) {
            ActivityMetricsLogger activityMetricsLogger = this.mSupervisor.mActivityMetricsLogger;
            Intent intent = balState.mIntent;
            WindowProcessController windowProcessController = balState.mCallerApp;
            int i = balState.mCallingUid;
            String str2 = balState.mCallingPackage;
            int i2 = balState.mCallingUidProcState;
            boolean z = balState.mCallingUidHasAnyVisibleWindow;
            int i3 = balState.mRealCallingUid;
            int i4 = balState.mRealCallingUidProcState;
            boolean z2 = balState.mRealCallingUidHasAnyVisibleWindow;
            int i5 = balState.mOriginatingPendingIntent != null ? 1 : 0;
            activityMetricsLogger.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long uptimeMillis = SystemClock.uptimeMillis();
            LogMaker logMaker = new LogMaker(1513);
            logMaker.setTimestamp(System.currentTimeMillis());
            logMaker.addTaggedData(1514, Integer.valueOf(i));
            logMaker.addTaggedData(1515, str2);
            logMaker.addTaggedData(1516, Integer.valueOf(ActivityManager.processStateAmToProto(i2)));
            logMaker.addTaggedData(1517, Integer.valueOf(z ? 1 : 0));
            logMaker.addTaggedData(1518, Integer.valueOf(i3));
            logMaker.addTaggedData(1519, Integer.valueOf(ActivityManager.processStateAmToProto(i4)));
            logMaker.addTaggedData(1520, Integer.valueOf(z2 ? 1 : 0));
            logMaker.addTaggedData(1527, Integer.valueOf(i5));
            if (intent != null) {
                logMaker.addTaggedData(1528, intent.getAction());
                ComponentName component = intent.getComponent();
                if (component != null) {
                    logMaker.addTaggedData(1526, component.flattenToShortString());
                }
            }
            if (windowProcessController != null) {
                logMaker.addTaggedData(1529, windowProcessController.mName);
                logMaker.addTaggedData(1530, Integer.valueOf(ActivityManager.processStateAmToProto(windowProcessController.mCurProcState)));
                logMaker.addTaggedData(1531, Integer.valueOf(windowProcessController.mHasClientActivities ? 1 : 0));
                logMaker.addTaggedData(1532, Integer.valueOf(windowProcessController.mHasForegroundServices ? 1 : 0));
                logMaker.addTaggedData(1533, Integer.valueOf((windowProcessController.mAtm.mTopApp == windowProcessController || (windowProcessController.mActivityStateFlags & 458752) != 0) ? 1 : 0));
                logMaker.addTaggedData(1534, Integer.valueOf(windowProcessController.mHasTopUi ? 1 : 0));
                logMaker.addTaggedData(1535, Integer.valueOf(windowProcessController.mHasOverlayUi ? 1 : 0));
                logMaker.addTaggedData(FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM, Integer.valueOf(windowProcessController.mPendingUiClean ? 1 : 0));
                if (windowProcessController.mInteractionEventTime != 0) {
                    logMaker.addTaggedData(1537, Long.valueOf(elapsedRealtime - windowProcessController.mInteractionEventTime));
                }
                if (windowProcessController.mFgInteractionTime != 0) {
                    logMaker.addTaggedData(1538, Long.valueOf(elapsedRealtime - windowProcessController.mFgInteractionTime));
                }
                if (windowProcessController.mWhenUnimportant != 0) {
                    logMaker.addTaggedData(1539, Long.valueOf(uptimeMillis - windowProcessController.mWhenUnimportant));
                }
            }
            activityMetricsLogger.mMetricsLogger.write(logMaker);
        }
        int i6 = 5;
        String str3 = "";
        if (Flags.balImprovedMetrics()) {
            if (shouldLogStats(balVerdict, balState)) {
                if (shouldLogIntentActivity(balVerdict, balState)) {
                    Intent intent2 = balState.mIntent;
                    if (intent2 == null) {
                        flattenToShortString = "noIntent";
                    } else {
                        ComponentName component2 = intent2.getComponent();
                        Objects.requireNonNull(component2);
                        flattenToShortString = component2.flattenToShortString();
                    }
                    str3 = flattenToShortString;
                }
                boolean z3 = balVerdict.mBasedOnRealCaller;
                int i7 = balVerdict.mCode;
                if (!z3 || i7 == 0) {
                    backgroundActivityStartController = this;
                    i6 = i7;
                } else {
                    backgroundActivityStartController = this;
                }
                backgroundActivityStartController.writeBalAllowedLog(str3, i6, balState);
                return;
            }
            return;
        }
        boolean z4 = balVerdict.mBasedOnRealCaller;
        int i8 = balVerdict.mCode;
        if (z4 && i8 != 0) {
            i8 = 5;
        }
        int i9 = balState.mCallingUid;
        int i10 = balState.mRealCallingUid;
        Intent intent3 = balState.mIntent;
        if (i8 == 5 && (i9 < 10000 || i10 < 10000)) {
            if (intent3 != null) {
                ComponentName component3 = intent3.getComponent();
                Objects.requireNonNull(component3);
                str = component3.flattenToShortString();
            } else {
                str = "";
            }
            writeBalAllowedLog(str, 5, balState);
        }
        if (i8 == 6 || i8 == 9 || i8 == 7) {
            writeBalAllowedLog("", i8, balState);
        }
    }

    public void writeBalAllowedLog(String str, int i, BalState balState) {
        int i2 = balState.mCallingUid;
        BalVerdict balVerdict = balState.mResultForCaller;
        int i3 = balVerdict == null ? 0 : balVerdict.mCode;
        boolean allowsBackgroundActivityStarts = balState.mBalAllowedByPiCreator.allowsBackgroundActivityStarts();
        boolean z = balState.mCheckedOptions.getPendingIntentCreatorBackgroundActivityStartMode() != 0;
        BalVerdict balVerdict2 = balState.mResultForRealCaller;
        FrameworkStatsLog.write(FrameworkStatsLog.BAL_ALLOWED, str, i, i2, balState.mRealCallingUid, i3, allowsBackgroundActivityStarts, z, balVerdict2 == null ? 0 : balVerdict2.mCode, balState.mBalAllowedByPiSender.allowsBackgroundActivityStarts(), balState.mCheckedOptions.getPendingIntentBackgroundActivityStartMode() != 0, getTargetSdk(balState.mCallingPackage), getTargetSdk(balState.mRealCallingPackage));
    }
}
