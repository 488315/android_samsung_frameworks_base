package com.android.server.wm;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.BackgroundStartPrivileges;
import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.app.WaitResult;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.AuxiliaryResolveInfo;
import android.content.pm.InstantAppRequest;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Trace;
import android.os.UserHandle;
import android.service.voice.IVoiceInteractionSession;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pools;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.webkit.MimeTypeMap;
import android.window.RemoteTransition;
import com.android.internal.app.HeavyWeightSwitcherActivity;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.os.SomeArgs;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.am.PendingIntentRecord;
import com.android.server.pm.ContentDispatcher;
import com.android.server.pm.InstantAppResolver;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.SpegService;
import com.android.server.uri.NeededUriGrants;
import com.android.server.wm.ActivityInterceptorCallback;
import com.android.server.wm.ActivityMetricsLogger;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.ActivityTaskSupervisor;
import com.android.server.wm.BackgroundActivityStartController;
import com.android.server.wm.LaunchParamsController;
import com.android.server.wm.TaskChangeNotificationController;
import com.android.server.wm.Transition;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.localservice.ApplicationPolicyInternal;
import com.samsung.android.knox.localservice.PasswordPolicyInternal;
import com.samsung.android.knox.localservice.SecurityPolicyInternal;
import com.samsung.android.knox.mtd.KMTDManager;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.TaskOrganizerInfo;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ActivityStarter {
    public static final long REACTIVE_COOLDOWN_TIME;
    public static KMTDManager mtdManager;
    public static long prevReactiveTime;
    public static String prevReactiveUrl;
    boolean mAddingToTask;
    public TaskFragment mAddingToTaskFragment;
    public ApplicationPolicyInternal mApplicationPolicy;
    public int mBalCode;
    public int mCallingUid;
    public final ActivityStartController mController;
    public boolean mDisplayLockAndOccluded;
    public boolean mDoResume;
    public boolean mFrozeTaskList;
    public Task mInTask;
    public TaskFragment mInTaskFragment;
    public Intent mIntent;
    public boolean mIntentDelivered;
    public final ActivityStartInterceptor mInterceptor;
    public boolean mIsTaskCleared;
    public ActivityRecord mLastStartActivityRecord;
    public int mLastStartActivityResult;
    public long mLastStartActivityTimeMs;
    public String mLastStartReason;
    public int mLaunchFlags;
    public int mLaunchMode;
    public boolean mLaunchTaskBehind;
    public boolean mMovedToFront;
    ActivityRecord mMovedToTopActivity;
    public boolean mNoAnimation;
    public ActivityRecord mNotTop;
    public ActivityOptions mOptions;
    public PasswordPolicyInternal mPasswordPolicy;
    public TaskDisplayArea mPreferredTaskDisplayArea;
    public int mPreferredWindowingMode;
    public Task mPriorAboveTask;
    public int mRealCallingUid;
    public final RootWindowContainer mRootWindowContainer;
    public SecurityPolicyInternal mSecurityPolicy;
    public final ActivityTaskManagerService mService;
    public ActivityRecord mSourceRecord;
    public Task mSourceRootTask;
    ActivityRecord mStartActivity;
    public int mStartFlags;
    public final ActivityTaskSupervisor mSupervisor;
    public Task mTargetRootTask;
    public Task mTargetTask;
    public boolean mTransientLaunch;
    public IVoiceInteractor mVoiceInteractor;
    public IVoiceInteractionSession mVoiceSession;
    public final LaunchParamsController.LaunchParams mLaunchParams = new LaunchParamsController.LaunchParams();
    public int mCanMoveToFrontCode = 0;
    public final SparseBooleanArray mSavedFrontTaskIds = new SparseBooleanArray();
    Request mRequest = new Request();
    public boolean mIsFreeformLaunching = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DefaultFactory implements Factory {
        public ActivityStartController mController;
        public ActivityStartInterceptor mInterceptor;
        public ActivityTaskManagerService mService;
        public Pools.SynchronizedPool mStarterPool;
        public ActivityTaskSupervisor mSupervisor;

        @Override // com.android.server.wm.ActivityStarter.Factory
        public final ActivityStarter obtain() {
            ActivityStarter activityStarter = (ActivityStarter) this.mStarterPool.acquire();
            if (activityStarter != null) {
                return activityStarter;
            }
            ActivityTaskManagerService activityTaskManagerService = this.mService;
            if (activityTaskManagerService.mRootWindowContainer != null) {
                return new ActivityStarter(this.mController, activityTaskManagerService, this.mSupervisor, this.mInterceptor);
            }
            throw new IllegalStateException("Too early to start activity.");
        }

        @Override // com.android.server.wm.ActivityStarter.Factory
        public final void recycle(ActivityStarter activityStarter) {
            activityStarter.reset(true);
            this.mStarterPool.release(activityStarter);
        }

        @Override // com.android.server.wm.ActivityStarter.Factory
        public final void setController(ActivityStartController activityStartController) {
            this.mController = activityStartController;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Factory {
        ActivityStarter obtain();

        void recycle(ActivityStarter activityStarter);

        void setController(ActivityStartController activityStartController);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Request {
        public ActivityInfo activityInfo;
        public SafeActivityOptions activityOptions;
        public boolean allowPendingRemoteAnimationRegistryLookup;
        public IApplicationThread caller;
        public String callingFeatureId;
        public String callingPackage;
        public boolean componentSpecified;
        public Intent ephemeralIntent;
        public IBinder errorCallbackToken;
        public int filterCallingUid;
        public BackgroundStartPrivileges forcedBalByPiSender;
        public boolean freezeScreen;
        public Configuration globalConfig;
        public boolean ignoreTargetSecurity;
        public Task inTask;
        public TaskFragment inTaskFragment;
        public Intent intent;
        public NeededUriGrants intentGrants;
        public PendingIntentRecord originatingPendingIntent;
        public ActivityRecord[] outActivity;
        public ProfilerInfo profilerInfo;
        public String reason;
        public int requestCode;
        public ResolveInfo resolveInfo;
        public String resolvedType;
        public IBinder resultTo;
        public String resultWho;
        public int startFlags;
        public int userId;
        public IVoiceInteractor voiceInteractor;
        public IVoiceInteractionSession voiceSession;
        public WaitResult waitResult;
        public int callingPid = 0;
        public int callingUid = -1;
        public int realCallingPid = 0;
        public int realCallingUid = -1;
        public final StringBuilder logMessage = new StringBuilder();

        public Request() {
            reset();
        }

        public final void reset() {
            this.caller = null;
            this.intent = null;
            this.intentGrants = null;
            this.ephemeralIntent = null;
            this.resolvedType = null;
            this.activityInfo = null;
            this.resolveInfo = null;
            this.voiceSession = null;
            this.voiceInteractor = null;
            this.resultTo = null;
            this.resultWho = null;
            this.requestCode = 0;
            this.callingPid = 0;
            this.callingUid = -1;
            this.callingPackage = null;
            this.callingFeatureId = null;
            this.realCallingPid = 0;
            this.realCallingUid = -1;
            this.startFlags = 0;
            this.activityOptions = null;
            this.ignoreTargetSecurity = false;
            this.componentSpecified = false;
            this.outActivity = null;
            this.inTask = null;
            this.inTaskFragment = null;
            this.reason = null;
            this.profilerInfo = null;
            this.globalConfig = null;
            this.userId = 0;
            this.waitResult = null;
            this.allowPendingRemoteAnimationRegistryLookup = true;
            this.filterCallingUid = -10000;
            this.originatingPendingIntent = null;
            this.forcedBalByPiSender = BackgroundStartPrivileges.NONE;
            this.freezeScreen = false;
            this.errorCallbackToken = null;
        }

        /* JADX WARN: Removed duplicated region for block: B:60:0x011d  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void resolveActivity(com.android.server.wm.ActivityTaskSupervisor r13) {
            /*
                Method dump skipped, instructions count: 404
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityStarter.Request.resolveActivity(com.android.server.wm.ActivityTaskSupervisor):void");
        }
    }

    static {
        Executors.newFixedThreadPool(4);
        REACTIVE_COOLDOWN_TIME = 500L;
        prevReactiveUrl = "";
        prevReactiveTime = 0L;
    }

    public ActivityStarter(ActivityStartController activityStartController, ActivityTaskManagerService activityTaskManagerService, ActivityTaskSupervisor activityTaskSupervisor, ActivityStartInterceptor activityStartInterceptor) {
        this.mController = activityStartController;
        this.mService = activityTaskManagerService;
        this.mRootWindowContainer = activityTaskManagerService.mRootWindowContainer;
        this.mSupervisor = activityTaskSupervisor;
        this.mInterceptor = activityStartInterceptor;
        reset(true);
    }

    public static int canEmbedActivity(TaskFragment taskFragment, ActivityRecord activityRecord, Task task) {
        Task task2 = taskFragment.getTask();
        if (task2 == null || task != task2) {
            return 3;
        }
        return taskFragment.isAllowedToEmbedActivity(taskFragment.mTaskFragmentOrganizerUid, activityRecord);
    }

    public static int computeResolveFilterUid(int i, int i2, int i3) {
        return i3 != -10000 ? i3 : i >= 0 ? i : i2;
    }

    public static int getExternalResult(int i) {
        if (i != 102) {
            return i;
        }
        return 0;
    }

    public static boolean shouldWriteStartActivityDebugLog(int i) {
        return (i == 0 || i == 1 || i == 2 || i == 3) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:110:0x0115, code lost:
    
        if (canEmbedActivity(r0, r11.mStartActivity, r13) == 0) goto L62;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void addOrReparentStartingActivity(java.lang.String r12, com.android.server.wm.Task r13) {
        /*
            Method dump skipped, instructions count: 449
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityStarter.addOrReparentStartingActivity(java.lang.String, com.android.server.wm.Task):void");
    }

    public final boolean avoidMoveToFront() {
        return this.mCanMoveToFrontCode != 0;
    }

    public final boolean canMoveTaskToBottomTask(int i, Task task) {
        Task task2;
        if (task == null || task.mTaskId == i) {
            return false;
        }
        if (task.inFreeformWindowingMode() && (!CoreRune.MT_NEW_DEX_LAUNCH_POLICY || !task.isNewDexMode() || task.isVisible() || !this.mSavedFrontTaskIds.get(i, false))) {
            return false;
        }
        if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION && (task2 = this.mService.mMultiTaskingController.mAffordanceTargetTask) != null && task2 == task) {
            return false;
        }
        if (task.getTopVisibleActivity(true, false) != null) {
            if (this.mSavedFrontTaskIds.get(task.mTaskId, false)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x02cb, code lost:
    
        if (r2.isDodBannerVisibleAsUser(r7) != false) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x009c, code lost:
    
        r27.setFlags(8388608);
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x00bb, code lost:
    
        r20 = r18;
        r21 = "ActivityManager";
        r22 = r11;
        r23 = "Start activity ";
        r18 = " succeeded";
        r6 = "ActivityTaskManager";
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x00d1, code lost:
    
        android.sec.enterprise.auditlog.AuditLog.logAsUser(5, 5, false, android.os.Process.myPid(), "ActivityStarter", "Start activity " + r32 + " succeeded", "", r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x00da, code lost:
    
        r19 = r6;
        r15 = r20;
        r14 = r21;
        r6 = r23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x0118, code lost:
    
        r22 = r11;
        r23 = "Start activity ";
        r18 = " succeeded";
        r6 = "ActivityTaskManager";
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x010c, code lost:
    
        r22 = r11;
        r23 = "Start activity ";
        r6 = "ActivityTaskManager";
        r20 = r18;
        r21 = "ActivityManager";
        r18 = " succeeded";
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0397, code lost:
    
        if (r30.getOptions(null, null, null, r2).getStartedByMDMAdmin() == false) goto L165;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:23:0x037e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x038f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x039d  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0298 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0341 A[Catch: Exception -> 0x0372, TryCatch #6 {Exception -> 0x0372, blocks: (B:93:0x033d, B:95:0x0341, B:96:0x034b, B:98:0x034f, B:100:0x0358), top: B:92:0x033d }] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x034f A[Catch: Exception -> 0x0372, TryCatch #6 {Exception -> 0x0372, blocks: (B:93:0x033d, B:95:0x0341, B:96:0x034b, B:98:0x034f, B:100:0x0358), top: B:92:0x033d }] */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v19 */
    /* JADX WARN: Type inference failed for: r6v30 */
    /* JADX WARN: Type inference failed for: r6v35 */
    /* JADX WARN: Type inference failed for: r6v36 */
    /* JADX WARN: Type inference failed for: r6v37 */
    /* JADX WARN: Type inference failed for: r6v40 */
    /* JADX WARN: Type inference failed for: r6v42 */
    /* JADX WARN: Type inference failed for: r6v45 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int checkStartActivityAllowedByEDM(android.content.Intent r27, android.content.pm.ActivityInfo r28, int r29, com.android.server.wm.SafeActivityOptions r30, android.content.ComponentName r31, java.lang.String r32) {
        /*
            Method dump skipped, instructions count: 1111
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityStarter.checkStartActivityAllowedByEDM(android.content.Intent, android.content.pm.ActivityInfo, int, com.android.server.wm.SafeActivityOptions, android.content.ComponentName, java.lang.String):int");
    }

    public final Intent createLaunchIntent(AuxiliaryResolveInfo auxiliaryResolveInfo, Intent intent, String str, String str2, Bundle bundle, String str3, int i) {
        if (auxiliaryResolveInfo != null && auxiliaryResolveInfo.needsPhaseTwo) {
            PackageManagerInternal packageManagerInternalLocked = this.mService.getPackageManagerInternalLocked();
            boolean isInstantApp = packageManagerInternalLocked.isInstantApp(str, i);
            PackageManagerService packageManagerService = ((PackageManagerService.PackageManagerInternalImpl) packageManagerInternalLocked).mService;
            packageManagerService.getClass();
            InstantAppRequest instantAppRequest = new InstantAppRequest(auxiliaryResolveInfo, intent, str3, str, str2, isInstantApp, i, bundle, false, auxiliaryResolveInfo.hostDigestPrefixSecure, auxiliaryResolveInfo.token);
            Handler handler = packageManagerService.mHandler;
            handler.sendMessage(handler.obtainMessage(20, instantAppRequest));
        }
        return InstantAppResolver.buildEphemeralInstallerIntent(intent, InstantAppResolver.sanitizeIntent(intent), auxiliaryResolveInfo == null ? null : auxiliaryResolveInfo.failureIntent, str, str2, bundle, str3, i, auxiliaryResolveInfo == null ? null : auxiliaryResolveInfo.installFailureActivity, auxiliaryResolveInfo == null ? null : auxiliaryResolveInfo.token, auxiliaryResolveInfo != null && auxiliaryResolveInfo.needsPhaseTwo, auxiliaryResolveInfo != null ? auxiliaryResolveInfo.filters : null);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(17:5|(1:7)(1:58)|8|(4:47|48|(2:50|(1:52))|53)|(1:46)(1:13)|14|(3:16|(1:18)(1:44)|(8:20|21|(6:34|35|(1:37)|38|30|31)|26|(1:28)|29|30|31))|45|21|(1:23)|32|34|35|(0)|38|30|31) */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00f7, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0109, code lost:
    
        android.util.Slog.w("ActivityTaskManager", r5 + r18, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00f5, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00f9, code lost:
    
        android.util.Slog.w("ActivityTaskManager", r5 + r18, r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00ec  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void deliverNewIntent(com.android.server.wm.ActivityRecord r18, com.android.server.uri.NeededUriGrants r19) {
        /*
            Method dump skipped, instructions count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityStarter.deliverNewIntent(com.android.server.wm.ActivityRecord, com.android.server.uri.NeededUriGrants):void");
    }

    public final int deliverToCurrentTopIfNeeded(Task task, NeededUriGrants neededUriGrants) {
        ActivityRecord activityRecord = task.topRunningNonDelayedActivityLocked(this.mNotTop);
        if (activityRecord == null || !activityRecord.mActivityComponent.equals(this.mStartActivity.mActivityComponent) || activityRecord.mUserId != this.mStartActivity.mUserId || !activityRecord.attachedToProcess() || (((this.mLaunchFlags & 536870912) == 0 && 1 != this.mLaunchMode) || (activityRecord.isActivityTypeHome() && activityRecord.getDisplayArea() != this.mPreferredTaskDisplayArea))) {
            return 0;
        }
        ActivityRecord activityRecord2 = this.mSourceRecord;
        if (activityRecord2 != null && activityRecord2.mIsAliasActivity && (activityRecord2.getWindowingMode() != task.getWindowingMode() || (this.mSourceRecord.inSplitScreenWindowingMode() && this.mSourceRecord.getWindowConfiguration().getStageType() != task.getWindowConfiguration().getStageType()))) {
            return 0;
        }
        activityRecord.getTaskFragment().clearLastPausedActivity();
        if (this.mDoResume) {
            this.mRootWindowContainer.resumeFocusedTasksTopActivities();
        }
        ActivityOptions.abort(this.mOptions);
        if ((this.mStartFlags & 1) != 0) {
            return 1;
        }
        ActivityRecord activityRecord3 = this.mStartActivity;
        ActivityRecord activityRecord4 = activityRecord3.resultTo;
        if (activityRecord4 != null) {
            activityRecord4.sendResult(-1, activityRecord3.resultWho, activityRecord3.requestCode, 0, null, null, null, false);
            this.mStartActivity.resultTo = null;
        }
        deliverNewIntent(activityRecord, neededUriGrants);
        this.mSupervisor.handleNonResizableTaskIfNeeded(activityRecord.task, this.mLaunchParams.mWindowingMode, this.mPreferredTaskDisplayArea, task, false);
        return 3;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:37|(4:(3:98|99|(19:103|104|40|41|(1:43)|44|(1:46)(1:94)|47|(1:49)|50|(2:54|55)|57|58|17d|71|72|73|74|75))|57|58|17d)|39|40|41|(0)|44|(0)(0)|47|(0)|50|(3:52|54|55)) */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0116, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0117, code lost:
    
        r5 = r0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0102 A[Catch: all -> 0x0027, RuntimeException -> 0x002b, IllegalArgumentException -> 0x0116, TryCatch #5 {IllegalArgumentException -> 0x0116, blocks: (B:41:0x00f8, B:43:0x0102, B:44:0x0119, B:46:0x0123, B:49:0x012c, B:50:0x0135, B:52:0x0142, B:54:0x0149), top: B:40:0x00f8 }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0123 A[Catch: all -> 0x0027, RuntimeException -> 0x002b, IllegalArgumentException -> 0x0116, TryCatch #5 {IllegalArgumentException -> 0x0116, blocks: (B:41:0x00f8, B:43:0x0102, B:44:0x0119, B:46:0x0123, B:49:0x012c, B:50:0x0135, B:52:0x0142, B:54:0x0149), top: B:40:0x00f8 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x012c A[Catch: all -> 0x0027, RuntimeException -> 0x002b, IllegalArgumentException -> 0x0116, TryCatch #5 {IllegalArgumentException -> 0x0116, blocks: (B:41:0x00f8, B:43:0x0102, B:44:0x0119, B:46:0x0123, B:49:0x012c, B:50:0x0135, B:52:0x0142, B:54:0x0149), top: B:40:0x00f8 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x017e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0128  */
    /* JADX WARN: Type inference failed for: r4v34, types: [android.content.pm.ActivityInfo] */
    /* JADX WARN: Type inference failed for: r4v36, types: [long] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int execute() {
        /*
            Method dump skipped, instructions count: 1411
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityStarter.execute():int");
    }

    /* JADX WARN: Removed duplicated region for block: B:114:0x0318  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0467 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x04d0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:142:0x04e5 A[Catch: RemoteException -> 0x04fa, TRY_LEAVE, TryCatch #6 {RemoteException -> 0x04fa, blocks: (B:140:0x04d2, B:142:0x04e5), top: B:139:0x04d2 }] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0503  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x050a  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0540  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x073b  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x0761  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0799  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x09ca  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0a2b  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x0be6  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x0c36 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:305:0x0c51  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x0c58  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x0c8b  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x0c94 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:317:0x0ca4  */
    /* JADX WARN: Removed duplicated region for block: B:322:0x0cde A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:323:0x0ce1  */
    /* JADX WARN: Removed duplicated region for block: B:352:0x0c54  */
    /* JADX WARN: Removed duplicated region for block: B:393:0x0bce  */
    /* JADX WARN: Removed duplicated region for block: B:400:0x0bd4  */
    /* JADX WARN: Removed duplicated region for block: B:425:0x0759  */
    /* JADX WARN: Removed duplicated region for block: B:426:0x06fb  */
    /* JADX WARN: Removed duplicated region for block: B:427:0x06b5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:477:0x032a  */
    /* JADX WARN: Removed duplicated region for block: B:557:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:558:0x01f8  */
    /* JADX WARN: Removed duplicated region for block: B:559:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01fe  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int executeRequest(com.android.server.wm.ActivityStarter.Request r91) {
        /*
            Method dump skipped, instructions count: 3504
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityStarter.executeRequest(com.android.server.wm.ActivityStarter$Request):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0071, code lost:
    
        if (r15.mSavedFrontTaskIds.get(r1.mTaskId, false) != false) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00d6, code lost:
    
        if (r1 != null) goto L62;
     */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0092  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.wm.Task getOrCreateRootTask(com.android.server.wm.ActivityRecord r16, int r17, com.android.server.wm.Task r18, android.app.ActivityOptions r19) {
        /*
            Method dump skipped, instructions count: 318
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityStarter.getOrCreateRootTask(com.android.server.wm.ActivityRecord, int, com.android.server.wm.Task, android.app.ActivityOptions):com.android.server.wm.Task");
    }

    public final String getStartInfo() {
        return this.mRequest.reason + ":" + this.mRequest.callingPackage + ":" + this.mRequest.callingPid;
    }

    /* JADX WARN: Removed duplicated region for block: B:118:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0219  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x022d  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x01b5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.wm.Task handleStartResult(com.android.server.wm.ActivityRecord r18, android.app.ActivityOptions r19, int r20, com.android.server.wm.Transition r21, android.window.RemoteTransition r22) {
        /*
            Method dump skipped, instructions count: 677
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityStarter.handleStartResult(com.android.server.wm.ActivityRecord, android.app.ActivityOptions, int, com.android.server.wm.Transition, android.window.RemoteTransition):com.android.server.wm.Task");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:109:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0314  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x032a  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0335  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x033d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0367 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0391  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x03d0  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x040b  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x03cc  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0331  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0326  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x031b  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x040f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0410 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01b1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int isAllowedToStart(com.android.server.wm.ActivityRecord r40, boolean r41, com.android.server.wm.Task r42) {
        /*
            Method dump skipped, instructions count: 1041
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityStarter.isAllowedToStart(com.android.server.wm.ActivityRecord, boolean, com.android.server.wm.Task):int");
    }

    public final boolean isExternalStartForSpeg() {
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        if (activityTaskManagerService.mSpeg == null) {
            return false;
        }
        Request request = this.mRequest;
        if (request.intent != null && request.callingPackage != null) {
            int i = request.realCallingUid;
            if (i == -1) {
                i = Binder.getCallingUid();
            }
            SpegService spegService = activityTaskManagerService.mSpeg;
            if (spegService.mIsSpegInOpeartion && i == spegService.mSpegUid) {
                if (!spegService.isSpegInOpeartion(this.mRequest.callingPackage)) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m(i, "App with uid ", " is trying to start an external app "), this.mRequest.callingPackage, "SPEG");
                    return true;
                }
                Request request2 = this.mRequest;
                String str = request2.callingPackage;
                if (request2.intent.getComponent() == null ? true : !str.equals(r0.getComponent().getPackageName())) {
                    Slog.w("SPEG", "App " + this.mRequest.callingPackage + " is trying to start an external intent: " + this.mRequest.intent);
                    return true;
                }
            } else if (spegService.isSpegInOpeartion(this.mRequest.callingPackage)) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m(i, "App with uid ", " is trying to launch SPEG app "), this.mRequest.callingPackage, "SPEG");
                return true;
            }
        }
        return false;
    }

    public final void logPIOnlyCreatorAllowsBAL() {
        if (this.mCanMoveToFrontCode == 1) {
            String nameForUid = this.mService.mContext.getPackageManager().getNameForUid(this.mRealCallingUid);
            if (nameForUid == null) {
                nameForUid = "uid=" + this.mRealCallingUid;
            }
            StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Without Android 15 BAL hardening this activity would be moved to the foreground. The activity is started by a PendingIntent. However, only the creator of the PendingIntent allows BAL while the sender does not allow BAL. realCallingPackage: ", nameForUid, "; callingPackage: ");
            m.append(this.mRequest.callingPackage);
            m.append("; mTargetRootTask:");
            m.append(this.mTargetRootTask);
            m.append("; mIntent: ");
            m.append(this.mIntent);
            m.append("; mTargetRootTask.getTopNonFinishingActivity: ");
            m.append(this.mTargetRootTask.getTopNonFinishingActivity(true, true));
            m.append("; mTargetRootTask.getRootActivity: ");
            m.append(this.mTargetRootTask.getRootActivity(true, false));
            Slog.wtf("ActivityTaskManager", m.toString());
        }
    }

    public final void onExecutionComplete() {
        ActivityStartController activityStartController = this.mController;
        activityStartController.mInExecution = false;
        ActivityStarter activityStarter = activityStartController.mLastStarter;
        Factory factory = activityStartController.mFactory;
        if (activityStarter == null) {
            activityStartController.mLastStarter = factory.obtain();
        }
        ActivityStarter activityStarter2 = activityStartController.mLastStarter;
        activityStarter2.getClass();
        activityStarter2.mStartActivity = this.mStartActivity;
        activityStarter2.mIntent = this.mIntent;
        activityStarter2.mCallingUid = this.mCallingUid;
        activityStarter2.mRealCallingUid = this.mRealCallingUid;
        activityStarter2.mOptions = this.mOptions;
        activityStarter2.mBalCode = this.mBalCode;
        activityStarter2.mLaunchTaskBehind = this.mLaunchTaskBehind;
        activityStarter2.mLaunchFlags = this.mLaunchFlags;
        activityStarter2.mLaunchMode = this.mLaunchMode;
        activityStarter2.mLaunchParams.set(this.mLaunchParams);
        activityStarter2.mNotTop = this.mNotTop;
        activityStarter2.mDoResume = this.mDoResume;
        activityStarter2.mStartFlags = this.mStartFlags;
        activityStarter2.mSourceRecord = this.mSourceRecord;
        activityStarter2.mPreferredTaskDisplayArea = this.mPreferredTaskDisplayArea;
        activityStarter2.mPreferredWindowingMode = this.mPreferredWindowingMode;
        activityStarter2.mInTask = this.mInTask;
        activityStarter2.mInTaskFragment = this.mInTaskFragment;
        activityStarter2.mAddingToTask = this.mAddingToTask;
        activityStarter2.mSourceRootTask = this.mSourceRootTask;
        activityStarter2.mTargetTask = this.mTargetTask;
        activityStarter2.mTargetRootTask = this.mTargetRootTask;
        activityStarter2.mIsTaskCleared = this.mIsTaskCleared;
        activityStarter2.mMovedToFront = this.mMovedToFront;
        activityStarter2.mNoAnimation = this.mNoAnimation;
        activityStarter2.mCanMoveToFrontCode = this.mCanMoveToFrontCode;
        activityStarter2.mFrozeTaskList = this.mFrozeTaskList;
        activityStarter2.mVoiceSession = this.mVoiceSession;
        activityStarter2.mVoiceInteractor = this.mVoiceInteractor;
        activityStarter2.mIntentDelivered = this.mIntentDelivered;
        activityStarter2.mLastStartActivityResult = this.mLastStartActivityResult;
        activityStarter2.mLastStartActivityTimeMs = this.mLastStartActivityTimeMs;
        activityStarter2.mLastStartReason = this.mLastStartReason;
        Request request = activityStarter2.mRequest;
        Request request2 = this.mRequest;
        request.getClass();
        request.caller = request2.caller;
        request.intent = request2.intent;
        request.intentGrants = request2.intentGrants;
        request.ephemeralIntent = request2.ephemeralIntent;
        request.resolvedType = request2.resolvedType;
        request.activityInfo = request2.activityInfo;
        request.resolveInfo = request2.resolveInfo;
        request.voiceSession = request2.voiceSession;
        request.voiceInteractor = request2.voiceInteractor;
        request.resultTo = request2.resultTo;
        request.resultWho = request2.resultWho;
        request.requestCode = request2.requestCode;
        request.callingPid = request2.callingPid;
        request.callingUid = request2.callingUid;
        request.callingPackage = request2.callingPackage;
        request.callingFeatureId = request2.callingFeatureId;
        request.realCallingPid = request2.realCallingPid;
        request.realCallingUid = request2.realCallingUid;
        request.startFlags = request2.startFlags;
        request.activityOptions = request2.activityOptions;
        request.ignoreTargetSecurity = request2.ignoreTargetSecurity;
        request.componentSpecified = request2.componentSpecified;
        request.outActivity = request2.outActivity;
        request.inTask = request2.inTask;
        request.inTaskFragment = request2.inTaskFragment;
        request.reason = request2.reason;
        request.profilerInfo = request2.profilerInfo;
        request.globalConfig = request2.globalConfig;
        request.userId = request2.userId;
        request.waitResult = request2.waitResult;
        request.allowPendingRemoteAnimationRegistryLookup = request2.allowPendingRemoteAnimationRegistryLookup;
        request.filterCallingUid = request2.filterCallingUid;
        request.originatingPendingIntent = request2.originatingPendingIntent;
        request.forcedBalByPiSender = request2.forcedBalByPiSender;
        request.freezeScreen = request2.freezeScreen;
        request.errorCallbackToken = request2.errorCallbackToken;
        factory.recycle(this);
    }

    public final void onExecutionStarted() {
        this.mController.mInExecution = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.server.wm.ActivityStartInterceptor$$ExternalSyntheticLambda1] */
    public final void postStartActivityProcessing(int i, final ActivityRecord activityRecord, Task task) {
        boolean isStartResultSuccessful = ActivityManager.isStartResultSuccessful(i);
        ActivityTaskSupervisor activityTaskSupervisor = this.mSupervisor;
        if (!isStartResultSuccessful && this.mFrozeTaskList) {
            activityTaskSupervisor.mRecentTasks.resetFreezeTaskListReorderingOnTimeout();
        }
        if (ActivityManager.isStartResultFatalError(i)) {
            return;
        }
        if (!activityTaskSupervisor.mWaitingActivityLaunched.isEmpty() && (i == 3 || i == 2)) {
            int size = activityTaskSupervisor.mWaitingActivityLaunched.size() - 1;
            boolean z = false;
            while (size >= 0) {
                ActivityTaskSupervisor.WaitInfo waitInfo = (ActivityTaskSupervisor.WaitInfo) activityTaskSupervisor.mWaitingActivityLaunched.get(size);
                ActivityMetricsLogger.TransitionInfo transitionInfo = waitInfo.mLaunchingState.mAssociatedTransitionInfo;
                if (transitionInfo != null ? activityRecord == transitionInfo.mLastLaunchedActivity : waitInfo.mTargetComponent.equals(activityRecord.mActivityComponent)) {
                    WaitResult waitResult = waitInfo.mResult;
                    waitResult.result = i;
                    if (i == 3) {
                        waitResult.who = activityRecord.mActivityComponent;
                        activityTaskSupervisor.mWaitingActivityLaunched.remove(size);
                        z = true;
                    }
                }
                size--;
                z = z;
            }
            if (z) {
                activityTaskSupervisor.mService.mGlobalLock.notifyAll();
            }
        }
        Task task2 = activityRecord.task;
        if (task2 == null) {
            task2 = this.mTargetTask;
        }
        if (task == null || task2 == null || !task2.isAttached()) {
            return;
        }
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        if (i == 2 || i == 3) {
            Task task3 = task2.getDisplayArea().mRootHomeTask;
            int i2 = (task3 == null || !task3.shouldBeVisible(null)) ? 0 : 1;
            ActivityRecord topNonFinishingActivity = task2.getTopNonFinishingActivity(true, true);
            int i3 = (topNonFinishingActivity == null || !topNonFinishingActivity.mVisible) ? 0 : 1;
            TaskChangeNotificationController taskChangeNotificationController = activityTaskManagerService.mTaskChangeNotificationController;
            ActivityManager.RunningTaskInfo taskInfo = task2.getTaskInfo();
            boolean z2 = this.mIsTaskCleared;
            TaskChangeNotificationController.MainHandler mainHandler = taskChangeNotificationController.mHandler;
            mainHandler.removeMessages(4);
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = taskInfo;
            obtain.argi1 = i2;
            obtain.argi2 = z2 ? 1 : 0;
            obtain.argi3 = i3;
            Message obtainMessage = mainHandler.obtainMessage(4, obtain);
            taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyActivityRestartAttempt, obtainMessage);
            obtainMessage.sendToTarget();
        }
        if (ActivityManager.isStartResultSuccessful(i)) {
            ActivityManager.RunningTaskInfo taskInfo2 = task2.getTaskInfo();
            final ActivityStartInterceptor activityStartInterceptor = this.mInterceptor;
            SparseArray sparseArray = activityStartInterceptor.mService.mActivityInterceptorCallbacks;
            ActivityInterceptorCallback.ActivityInterceptorInfo interceptorInfo = activityStartInterceptor.getInterceptorInfo(new Runnable() { // from class: com.android.server.wm.ActivityStartInterceptor$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityStartInterceptor activityStartInterceptor2 = ActivityStartInterceptor.this;
                    ActivityRecord activityRecord2 = activityRecord;
                    WindowManagerGlobalLock windowManagerGlobalLock = activityStartInterceptor2.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            Task task4 = activityRecord2.task;
                            if (task4 == null) {
                                activityRecord2.mPendingOptions = null;
                                activityRecord2.mPendingRemoteAnimation = null;
                                activityRecord2.mPendingRemoteTransition = null;
                            } else {
                                task4.forAllActivities(new ActivityRecord$$ExternalSyntheticLambda3(4));
                            }
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            });
            for (int i4 = 0; i4 < sparseArray.size(); i4++) {
                ((ActivityInterceptorCallback) sparseArray.valueAt(i4)).onActivityLaunched(taskInfo2, activityRecord.info, interceptorInfo);
            }
        }
        if (activityRecord.isDexMode() && (activityRecord.isActivityTypeHome() || activityRecord.isActivityTypeRecents())) {
            DexController dexController = activityTaskManagerService.mDexController;
            int displayId = activityRecord.getDisplayId();
            if (displayId == -1) {
                dexController.getClass();
            } else if (dexController.mTargetDisplayId == displayId) {
                ActivityTaskManagerService activityTaskManagerService2 = dexController.mAtm;
                DisplayContent displayContent = activityTaskManagerService2.mRootWindowContainer.getDisplayContent(dexController.mSourceDisplayId);
                DisplayContent displayContent2 = activityTaskManagerService2.mRootWindowContainer.getDisplayContent(dexController.mTargetDisplayId);
                if (displayContent != null && displayContent2 != null) {
                    displayContent.forAllTasks(new DexController$$ExternalSyntheticLambda4(0, dexController, displayContent2));
                    dexController.mSourceDisplayId = -1;
                    dexController.mTargetDisplayId = -1;
                }
            }
        }
        if (CoreRune.MW_SA_LOGGING && this.mIsFreeformLaunching && activityRecord.inFreeformWindowingMode()) {
            if (!activityRecord.isDexMode() && !"startActivityFromRecents".equals(this.mLastStartReason) && !"android.server.wm.app".equals(this.mRequest.callingPackage) && !TextUtils.isEmpty(this.mRequest.callingPackage)) {
                if (!UserHandle.isSameApp(this.mRequest.realCallingUid, activityTaskManagerService.mRecentTasks.mRecentsUid)) {
                    if (!UserHandle.isSameApp(this.mRequest.realCallingUid, activityTaskManagerService.mRecentTasks.mLauncherInfo)) {
                        MultiTaskingController multiTaskingController = activityTaskManagerService.mMultiTaskingController;
                        if (multiTaskingController.mSystemUIUid == -1) {
                            multiTaskingController.mSystemUIUid = multiTaskingController.mAtm.getPackageManagerInternalLocked().getPackageUid(Constants.SYSTEMUI_PACKAGE_NAME, 1048576L, 0);
                        }
                        if (multiTaskingController.mSystemUIUid != this.mRequest.realCallingUid) {
                            return;
                        }
                    }
                }
            }
            CoreSaLogger.logForAdvanced("2004", "From application");
            CoreSaLogger.logForAdvanced("2013", this.mRequest.callingPackage);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:184:0x0445  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x02f4  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0327  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int recycleTask(com.android.server.wm.Task r31, com.android.server.wm.ActivityRecord r32, com.android.server.wm.Task r33, com.android.server.uri.NeededUriGrants r34, com.android.server.wm.BackgroundActivityStartController.BalVerdict r35) {
        /*
            Method dump skipped, instructions count: 1667
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityStarter.recycleTask(com.android.server.wm.Task, com.android.server.wm.ActivityRecord, com.android.server.wm.Task, com.android.server.uri.NeededUriGrants, com.android.server.wm.BackgroundActivityStartController$BalVerdict):int");
    }

    public final void reset(boolean z) {
        this.mStartActivity = null;
        this.mIntent = null;
        this.mCallingUid = -1;
        this.mRealCallingUid = -1;
        this.mOptions = null;
        this.mBalCode = 1;
        this.mLaunchTaskBehind = false;
        this.mLaunchFlags = 0;
        this.mLaunchMode = -1;
        this.mLaunchParams.reset();
        this.mNotTop = null;
        this.mDoResume = false;
        this.mStartFlags = 0;
        this.mSourceRecord = null;
        this.mPreferredTaskDisplayArea = null;
        this.mPreferredWindowingMode = 0;
        this.mInTask = null;
        this.mInTaskFragment = null;
        this.mAddingToTaskFragment = null;
        this.mAddingToTask = false;
        this.mSourceRootTask = null;
        this.mTargetRootTask = null;
        this.mTargetTask = null;
        this.mIsTaskCleared = false;
        this.mMovedToFront = false;
        this.mNoAnimation = false;
        this.mCanMoveToFrontCode = 0;
        this.mFrozeTaskList = false;
        this.mTransientLaunch = false;
        this.mPriorAboveTask = null;
        this.mDisplayLockAndOccluded = false;
        this.mVoiceSession = null;
        this.mVoiceInteractor = null;
        this.mIntentDelivered = false;
        if (z) {
            this.mRequest.reset();
        }
        this.mSavedFrontTaskIds.clear();
        if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION) {
            MultiTaskingController multiTaskingController = this.mService.mMultiTaskingController;
            if (multiTaskingController.mAffordanceTargetTask != null) {
                multiTaskingController.mAffordanceTargetTask = null;
                Slog.d("MultiTaskingController", "setAffordanceTargetTask: null");
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:77:0x00f7, code lost:
    
        if (r5 != null) goto L62;
     */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.wm.Task resolveReusableTask() {
        /*
            Method dump skipped, instructions count: 434
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityStarter.resolveReusableTask():com.android.server.wm.Task");
    }

    public final int resolveToHeavyWeightSwitcherIfNeeded() {
        ActivityInfo activityInfo = this.mRequest.activityInfo;
        if (activityInfo != null && this.mService.mHasHeavyWeightFeature) {
            ApplicationInfo applicationInfo = activityInfo.applicationInfo;
            if ((applicationInfo.privateFlags & 2) != 0) {
                if (!activityInfo.processName.equals(applicationInfo.packageName)) {
                    return 0;
                }
                WindowProcessController windowProcessController = this.mService.mHeavyWeightProcess;
                if (windowProcessController != null) {
                    int i = windowProcessController.mInfo.uid;
                    ActivityInfo activityInfo2 = this.mRequest.activityInfo;
                    if (i != activityInfo2.applicationInfo.uid || !windowProcessController.mName.equals(activityInfo2.processName)) {
                        Request request = this.mRequest;
                        int i2 = request.callingUid;
                        IApplicationThread iApplicationThread = request.caller;
                        if (iApplicationThread != null) {
                            WindowProcessController processController = this.mService.getProcessController(iApplicationThread);
                            if (processController == null) {
                                Slog.w("ActivityTaskManager", "Unable to find app for caller " + this.mRequest.caller + " (pid=" + this.mRequest.callingPid + ") when starting: " + this.mRequest.intent.toString());
                                SafeActivityOptions.abort(this.mRequest.activityOptions);
                                return -94;
                            }
                            i2 = processController.mInfo.uid;
                        }
                        int i3 = i2;
                        ActivityTaskManagerService activityTaskManagerService = this.mService;
                        Request request2 = this.mRequest;
                        PendingIntentRecord intentSenderLocked = activityTaskManagerService.getIntentSenderLocked(2, i3, request2.userId, 0, 1342177280, null, null, "android", null, null, new Intent[]{request2.intent}, new String[]{request2.resolvedType});
                        Intent intent = new Intent();
                        if (this.mRequest.requestCode >= 0) {
                            intent.putExtra("has_result", true);
                        }
                        intent.putExtra(KnoxCustomManagerService.INTENT, new IntentSender(intentSenderLocked));
                        if (!windowProcessController.mActivities.isEmpty()) {
                            ActivityRecord activityRecord = (ActivityRecord) windowProcessController.mActivities.get(0);
                            intent.putExtra("cur_app", activityRecord.packageName);
                            intent.putExtra("cur_task", activityRecord.task.mTaskId);
                        }
                        intent.putExtra("new_app", this.mRequest.activityInfo.packageName);
                        intent.setFlags(this.mRequest.intent.getFlags());
                        intent.setClassName("android", HeavyWeightSwitcherActivity.class.getName());
                        Request request3 = this.mRequest;
                        request3.intent = intent;
                        request3.resolvedType = null;
                        request3.caller = null;
                        request3.callingUid = Binder.getCallingUid();
                        this.mRequest.callingPid = Binder.getCallingPid();
                        Request request4 = this.mRequest;
                        request4.componentSpecified = true;
                        request4.resolveInfo = this.mSupervisor.resolveIntent(request4.intent, null, request4.userId, 0, computeResolveFilterUid(request4.callingUid, request4.realCallingUid, request4.filterCallingUid), this.mRequest.realCallingPid);
                        Request request5 = this.mRequest;
                        ResolveInfo resolveInfo = request5.resolveInfo;
                        ActivityInfo activityInfo3 = resolveInfo != null ? resolveInfo.activityInfo : null;
                        request5.activityInfo = activityInfo3;
                        if (activityInfo3 != null) {
                            request5.activityInfo = this.mService.mAmInternal.getActivityInfoForUser(activityInfo3, request5.userId);
                        }
                        return 0;
                    }
                }
                return 0;
            }
        }
        return 0;
    }

    public final void resumeTargetRootTaskIfNeeded() {
        boolean z = this.mDoResume;
        RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
        if (z) {
            ActivityRecord activityRecord = this.mTargetRootTask.topRunningActivity(true);
            if (activityRecord != null) {
                activityRecord.mCurrentLaunchCanTurnScreenOn = true;
            }
            if (this.mTargetRootTask.isFocusable()) {
                rootWindowContainer.resumeFocusedTasksTopActivities(this.mTargetRootTask, null, this.mOptions, this.mTransientLaunch);
            } else {
                rootWindowContainer.ensureActivitiesVisible();
            }
        } else {
            ActivityOptions.abort(this.mOptions);
        }
        int i = this.mStartActivity.mUserId;
        Task task = this.mTargetRootTask;
        if (i != rootWindowContainer.mCurrentUser) {
            if (task == null) {
                task = rootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea().getOrCreateRootHomeTask(false);
            }
            rootWindowContainer.mUserRootTaskInFront.put(i, task.getRootTask().mTaskId);
        }
    }

    public final void setActivityOptions(Bundle bundle) {
        this.mRequest.activityOptions = SafeActivityOptions.fromBundle(bundle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r10v13 */
    /* JADX WARN: Type inference failed for: r10v6 */
    /* JADX WARN: Type inference failed for: r10v7, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r10v8 */
    public final void setInitialState(ActivityRecord activityRecord, ActivityOptions activityOptions, Task task, TaskFragment taskFragment, int i, ActivityRecord activityRecord2, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor, int i2, int i3) {
        ?? r10;
        TaskFragment taskFragment2;
        TaskFragment taskFragment3;
        int i4;
        Task task2;
        Task topDisplayFocusedRootTask;
        Task task3;
        ActivityRecord activityRecord3;
        int i5;
        reset(false);
        if (!CoreRune.MW_SA_LOGGING || activityOptions == null || activityOptions.getLaunchBounds() == null || activityOptions.getLaunchBounds().isEmpty()) {
            this.mIsFreeformLaunching = false;
        } else {
            this.mIsFreeformLaunching = true;
        }
        this.mStartActivity = activityRecord;
        this.mIntent = activityRecord.intent;
        this.mOptions = activityOptions;
        this.mCallingUid = activityRecord.launchedFromUid;
        this.mRealCallingUid = i3;
        this.mSourceRecord = activityRecord2;
        this.mSourceRootTask = activityRecord2 != null ? activityRecord2.getRootTask() : null;
        this.mVoiceSession = iVoiceInteractionSession;
        this.mVoiceInteractor = iVoiceInteractor;
        this.mBalCode = i2;
        LaunchParamsController.LaunchParams launchParams = this.mLaunchParams;
        launchParams.reset();
        ActivityTaskSupervisor activityTaskSupervisor = this.mSupervisor;
        activityTaskSupervisor.mLaunchParamsController.calculate(task, activityRecord.info.windowLayout, activityRecord, activityRecord2, activityOptions, this.mRequest, 0, this.mLaunchParams, null);
        TaskDisplayArea taskDisplayArea = launchParams.mPreferredTaskDisplayArea;
        boolean z = taskDisplayArea != null;
        RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
        if (!z) {
            taskDisplayArea = rootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea();
        }
        this.mPreferredTaskDisplayArea = taskDisplayArea;
        this.mPreferredWindowingMode = launchParams.mWindowingMode;
        int i6 = activityRecord.launchMode;
        this.mLaunchMode = i6;
        boolean z2 = 3 == i6;
        boolean z3 = 2 == i6;
        int flags = this.mIntent.getFlags();
        int i7 = flags & 524288;
        if (i7 == 0 || !(z2 || z3)) {
            int i8 = activityRecord.info.documentLaunchMode;
            r10 = 1;
            r10 = 1;
            r10 = 1;
            r10 = 1;
            if (i8 == 1 || i8 == 2) {
                flags |= 524288;
            } else if (i8 == 3 && (this.mLaunchMode != 4 || i7 != 0)) {
                flags &= -134742017;
            }
        } else {
            Slog.i("ActivityTaskManager", "Ignoring FLAG_ACTIVITY_NEW_DOCUMENT, launchMode is \"singleInstance\" or \"singleTask\"");
            flags &= -134742017;
            r10 = 1;
        }
        this.mLaunchFlags = flags;
        this.mLaunchTaskBehind = (!activityRecord.mLaunchTaskBehind || 2 == (i5 = this.mLaunchMode) || 3 == i5 || (flags & 524288) == 0) ? false : r10;
        if (this.mLaunchMode == 4) {
            this.mLaunchFlags = flags | 268435456;
        }
        String str = activityRecord.info.requiredDisplayCategory;
        if (str != null && (activityRecord3 = this.mSourceRecord) != null && !str.equals(activityRecord3.info.requiredDisplayCategory)) {
            this.mLaunchFlags |= 268435456;
        }
        if ("startResolvedActivity".equals(this.mLastStartReason) || this.mStartActivity.resultTo == null || (this.mLaunchFlags & 268435456) == 0) {
            taskFragment2 = null;
        } else {
            Slog.w("ActivityTaskManager", "Activity is launching as a new task, so cancelling activity result.");
            ActivityRecord activityRecord4 = this.mStartActivity;
            activityRecord4.resultTo.sendResult(-1, activityRecord4.resultWho, activityRecord4.requestCode, 0, null, null, null, false);
            taskFragment2 = null;
            this.mStartActivity.resultTo = null;
        }
        int i9 = this.mLaunchFlags;
        if ((i9 & 524288) != 0 && activityRecord.resultTo == null) {
            this.mLaunchFlags = i9 | 268435456;
        }
        int i10 = this.mLaunchFlags;
        if ((i10 & 268435456) != 0 && (this.mLaunchTaskBehind || activityRecord.info.documentLaunchMode == 2)) {
            this.mLaunchFlags = i10 | 134217728;
        }
        if (activityRecord.isLaunchAdjacent() && !MultiWindowCoreState.MW_ENABLED) {
            this.mLaunchFlags &= -4097;
        }
        activityTaskSupervisor.mUserLeaving = (this.mLaunchFlags & 262144) == 0 ? r10 : false;
        boolean showToCurrentUser = activityRecord.showToCurrentUser();
        if (!showToCurrentUser) {
            Slog.w("ActivityTaskManager", "Can't resume non-current user r=" + activityRecord);
        }
        if (!showToCurrentUser || this.mLaunchTaskBehind) {
            activityRecord.delayedResume = r10;
            this.mDoResume = false;
        } else {
            this.mDoResume = r10;
        }
        ActivityOptions activityOptions2 = this.mOptions;
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        if (activityOptions2 != null) {
            if (activityOptions2.getLaunchTaskId() != -1 && this.mOptions.getTaskOverlay()) {
                activityRecord.mTaskOverlay = r10;
                activityRecord.setAlwaysOnTop(r10);
                if (!this.mOptions.canTaskOverlayResume()) {
                    Task anyTaskForId = rootWindowContainer.anyTaskForId(this.mOptions.getLaunchTaskId());
                    ActivityRecord topNonFinishingActivity = anyTaskForId != 0 ? anyTaskForId.getTopNonFinishingActivity(r10, r10) : taskFragment2;
                    if (topNonFinishingActivity != 0 && !topNonFinishingActivity.isState(ActivityRecord.State.RESUMED)) {
                        this.mDoResume = false;
                        this.mCanMoveToFrontCode = 2;
                    }
                }
            } else if (this.mOptions.getAvoidMoveToFront()) {
                this.mDoResume = false;
                this.mCanMoveToFrontCode = 2;
            }
            this.mTransientLaunch = this.mOptions.getTransientLaunch();
            boolean isKeyguardOccluded = activityTaskSupervisor.mKeyguardController.isKeyguardOccluded(this.mPreferredTaskDisplayArea.mDisplayContent.mDisplayId);
            this.mDisplayLockAndOccluded = isKeyguardOccluded;
            if (this.mTransientLaunch && isKeyguardOccluded && activityTaskManagerService.mWindowOrganizerController.mTransitionController.isShellTransitionsEnabled()) {
                this.mDoResume = false;
                this.mCanMoveToFrontCode = 2;
            }
            this.mTargetRootTask = Task.fromWindowContainerToken(this.mOptions.getLaunchRootTask());
            if (taskFragment == null) {
                IBinder launchTaskFragmentToken = this.mOptions.getLaunchTaskFragmentToken();
                taskFragment3 = launchTaskFragmentToken == null ? taskFragment2 : (TaskFragment) activityTaskManagerService.mWindowOrganizerController.mLaunchTaskFragments.get(launchTaskFragmentToken);
                if (taskFragment3 != null && taskFragment3.isEmbeddedTaskFragmentInPip()) {
                    Slog.w("ActivityTaskManager", "Can not start activity in TaskFragment in PIP: " + taskFragment3);
                    taskFragment3 = taskFragment2;
                }
            } else {
                taskFragment3 = taskFragment;
            }
            if (CoreRune.MW_EMBED_ACTIVITY && taskFragment3 != null && this.mOptions.isActivityEmbeddedPlaceholder()) {
                Task task4 = taskFragment3.getTask();
                if (task4 != null && task4.inFullscreenWindowingMode() && task4.getDisplayArea() != null && (task3 = task4.getDisplayArea().getTask(new ActivityStarter$$ExternalSyntheticLambda0(0))) != null && task3 != task4) {
                    this.mCanMoveToFrontCode = 2;
                    this.mDoResume = false;
                }
                taskFragment3.mIsPlaceholderTaskFragment = r10;
            }
        } else {
            taskFragment3 = taskFragment;
        }
        this.mNotTop = (this.mLaunchFlags & 16777216) != 0 ? activityRecord2 : null;
        this.mInTask = task;
        if (task != null && !task.inRecents) {
            Slog.w("ActivityTaskManager", "Starting activity in task not in recents: " + task);
            this.mInTask = null;
        }
        Task task5 = this.mInTask;
        if (task5 != null) {
            ActivityInfo activityInfo = activityRecord.info;
            String str2 = task5.mRequiredDisplayCategory;
            if ((str2 == null || !str2.equals(activityInfo.requiredDisplayCategory)) && (task5.mRequiredDisplayCategory != null || activityInfo.requiredDisplayCategory != null)) {
                Slog.w("ActivityTaskManager", "Starting activity in task with different display category: " + this.mInTask);
                this.mInTask = null;
            }
        }
        this.mInTaskFragment = taskFragment3;
        this.mStartFlags = i;
        if ((i & 1) != 0) {
            ActivityRecord activityRecord5 = (activityRecord2 != null || (topDisplayFocusedRootTask = rootWindowContainer.getTopDisplayFocusedRootTask()) == null) ? activityRecord2 : topDisplayFocusedRootTask.topRunningNonDelayedActivityLocked(this.mNotTop);
            if (activityRecord5 == null || !activityRecord5.mActivityComponent.equals(activityRecord.mActivityComponent)) {
                this.mStartFlags &= -2;
            }
        }
        this.mNoAnimation = (this.mLaunchFlags & EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT) != 0 ? r10 : false;
        if (this.mBalCode == 0 && !activityTaskManagerService.mAmInternal.isBackgroundActivityStartsEnabled()) {
            this.mCanMoveToFrontCode = 2;
            this.mDoResume = false;
        }
        if (activityRecord.intent.getLaunchOverTargetTaskId() != -1) {
            TaskDisplayArea defaultTaskDisplayArea = rootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea();
            if (defaultTaskDisplayArea.isSplitScreenModeActivated()) {
                ArrayList arrayList = new ArrayList(Arrays.asList(Integer.valueOf((int) r10), 2));
                if (CoreRune.MW_MULTI_SPLIT && defaultTaskDisplayArea.isMultiSplitActive()) {
                    i4 = 4;
                    arrayList.add(4);
                } else {
                    i4 = 4;
                }
                for (int i11 = 0; i11 < arrayList.size(); i11++) {
                    Task topRootTaskInStageType = defaultTaskDisplayArea.getTopRootTaskInStageType(((Integer) arrayList.get(i11)).intValue());
                    if (topRootTaskInStageType != 0 && topRootTaskInStageType.getTopVisibleActivity(r10, false) != null) {
                        this.mSavedFrontTaskIds.put(topRootTaskInStageType.getTopMostTask().mTaskId, r10);
                    }
                }
            } else {
                i4 = 4;
                Task rootTask = defaultTaskDisplayArea.getRootTask((int) r10, 0);
                if (rootTask != 0 && rootTask.getTopVisibleActivity(r10, false) != null) {
                    this.mSavedFrontTaskIds.put(rootTask.getTopMostTask().mTaskId, r10);
                }
            }
        } else {
            i4 = 4;
        }
        if ((this.mLaunchFlags & 268439552) != 268439552 || activityRecord2 == null || activityRecord2.isDexMode() || activityRecord2.getWindowingMode() != r10 || (task2 = activityRecord2.task) == 0 || !task2.isResizeable(r10)) {
            return;
        }
        if (activityOptions == null || !activityOptions.hasValidLaunchAdjacentExt()) {
            Task task6 = rootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea().mRootSideStageTask;
            if (task6 != null) {
                int stagePosition = task6.getWindowConfiguration().getStagePosition();
                if (CoreRune.MW_MULTI_SPLIT_LAUNCH_ADJACENT) {
                    boolean z4 = (stagePosition == 8 || stagePosition == 32) ? r10 : false;
                    if ((z4 && stagePosition == 8) || !z4) {
                        activityTaskManagerService.mMultiTaskingController.mDeferEnsureConfig = r10;
                    }
                } else if (stagePosition == 16 || stagePosition == 8) {
                    activityTaskManagerService.mMultiTaskingController.mDeferEnsureConfig = r10;
                }
            }
        } else {
            int i12 = activityOptions.hasValidHorizontalSplitLayoutWithAdjacentFlag() ? activityOptions.launchToTopSideWithAdjacentFlag() ? 5 : 3 : activityOptions.launchToRightSideWithAdjacentFlag() ? 2 : i4;
            TaskOrganizerController taskOrganizerController = activityTaskManagerService.mTaskOrganizerController;
            taskOrganizerController.getClass();
            TaskOrganizerInfo taskOrganizerInfo = new TaskOrganizerInfo();
            taskOrganizerInfo.setSplitScreenCreateModeForLaunchAdjacent(i12);
            taskOrganizerController.onSplitLayoutChangeRequested(taskOrganizerInfo.toBundle());
            activityTaskManagerService.mMultiTaskingController.mDeferEnsureConfig = r10;
        }
        if (CoreRune.MW_SPLIT_LAUNCH_ADJACENT_SA_LOGGING) {
            CoreSaLogger.logForAdvanced("1000", "From application");
        }
    }

    public final void setNewTask(Task task) {
        boolean z = (this.mLaunchTaskBehind || avoidMoveToFront()) ? false : true;
        Task task2 = this.mTargetRootTask;
        ActivityRecord activityRecord = this.mStartActivity;
        Task reuseOrCreateTask = task2.reuseOrCreateTask(activityRecord.info, this.mIntent, this.mVoiceSession, this.mVoiceInteractor, z, activityRecord, this.mSourceRecord, this.mOptions);
        Transition transition = reuseOrCreateTask.mTransitionController.mCollectingTransition;
        if (transition != null) {
            transition.collectExistenceChange(reuseOrCreateTask);
        }
        addOrReparentStartingActivity("setTaskFromReuseOrCreateNewTask", reuseOrCreateTask);
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_TASKS_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_TASKS, -2190454940975874759L, 0, null, String.valueOf(this.mStartActivity), String.valueOf(this.mStartActivity.task));
        }
        if (task != null) {
            this.mStartActivity.setTaskToAffiliateWith(task);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x0293, code lost:
    
        r7.intent.setLaunchTaskIdForSingleInstancePerTask(r8.mTaskId);
     */
    /* JADX WARN: Removed duplicated region for block: B:307:0x06ae  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x06d9  */
    /* JADX WARN: Removed duplicated region for block: B:480:0x0ac3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int startActivityInner(com.android.server.wm.ActivityRecord r42, com.android.server.wm.ActivityRecord r43, android.service.voice.IVoiceInteractionSession r44, com.android.internal.app.IVoiceInteractor r45, int r46, android.app.ActivityOptions r47, com.android.server.wm.Task r48, com.android.server.wm.TaskFragment r49, com.android.server.wm.BackgroundActivityStartController.BalVerdict r50, com.android.server.uri.NeededUriGrants r51, int r52) {
        /*
            Method dump skipped, instructions count: 3011
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityStarter.startActivityInner(com.android.server.wm.ActivityRecord, com.android.server.wm.ActivityRecord, android.service.voice.IVoiceInteractionSession, com.android.internal.app.IVoiceInteractor, int, android.app.ActivityOptions, com.android.server.wm.Task, com.android.server.wm.TaskFragment, com.android.server.wm.BackgroundActivityStartController$BalVerdict, com.android.server.uri.NeededUriGrants, int):int");
    }

    public final int startActivityUnchecked(ActivityRecord activityRecord, ActivityRecord activityRecord2, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor, int i, ActivityOptions activityOptions, Task task, TaskFragment taskFragment, BackgroundActivityStartController.BalVerdict balVerdict, NeededUriGrants neededUriGrants, int i2) {
        Transition transition;
        Intent intent;
        ApplicationInfo applicationInfo;
        Task task2;
        Transition transition2;
        Intent intent2;
        int i3;
        TransitionController transitionController = activityRecord.mTransitionController;
        Transition transition3 = transitionController.mCollectingTransition;
        if (activityRecord.isLaunchAdjacent() && activityRecord2 != null && activityRecord2.finishing && activityRecord2.getTaskDisplayArea() != null && !activityRecord2.getTaskDisplayArea().isSplitScreenModeActivated() && transition3 != null && transition3.mType == 2) {
            Slog.w("ActivityTaskManager", "Start Adjacent Activity, Collecting Transition is TRANSIT_CLOSE");
            transition3.abort();
        }
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE && (intent2 = activityRecord.intent) != null && "android.intent.action.AUTORUN_FLEX_PANEL".equals(intent2.getAction()) && transition3 != null && transition3.isCollecting() && ((i3 = transition3.mType) == 3 || i3 == 1)) {
            int i4 = 0;
            while (true) {
                if (i4 >= transition3.mChanges.size()) {
                    break;
                }
                Transition.ChangeInfo changeInfo = (Transition.ChangeInfo) transition3.mChanges.valueAt(i4);
                if (changeInfo.mContainer.asTask() != null) {
                    WindowContainer windowContainer = changeInfo.mContainer;
                    if ((changeInfo.getTransitMode(windowContainer) == 1 || changeInfo.getTransitMode(windowContainer) == 3) && changeInfo.mWindowingMode == 1) {
                        int i5 = 0;
                        while (true) {
                            if (i5 >= transition3.mChanges.size()) {
                                Slog.w("ActivityTaskManager", "transition abort for flex panel, t=" + transition3);
                                transition3.abort();
                                break;
                            }
                            Transition.ChangeInfo changeInfo2 = (Transition.ChangeInfo) transition3.mChanges.valueAt(i5);
                            if (changeInfo2.mContainer.asActivityRecord() != null) {
                                WindowContainer windowContainer2 = changeInfo2.mContainer;
                                if ((changeInfo2.getTransitMode(windowContainer2) == 1 || changeInfo2.getTransitMode(windowContainer2) == 3) && windowContainer2.asActivityRecord().isActivityTypeHomeOrRecents()) {
                                    break;
                                }
                            }
                            i5++;
                        }
                    }
                }
                i4++;
            }
        }
        if (transitionController.isShellTransitionsEnabled()) {
            if (!transitionController.mTransitionPlayers.isEmpty() && transitionController.mQueuedTransitions.isEmpty()) {
                if (!transitionController.mSyncEngine.hasActiveSync()) {
                    transition2 = new Transition(1, 0, transitionController, transitionController.mSyncEngine);
                    transitionController.moveToCollecting(transition2);
                } else if (!transitionController.isCollecting()) {
                    Slog.w("TransitionController", "Ongoing Sync outside of transition.");
                } else if (transitionController.canStartCollectingNow(null)) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled[1]) {
                        ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, -1611886029896664304L, 1, "Moving #%d from collecting to waiting.", Long.valueOf(transitionController.mCollectingTransition.mSyncId));
                    }
                    transitionController.mWaitingTransitions.add(transitionController.mCollectingTransition);
                    transitionController.mCollectingTransition = null;
                    transition2 = new Transition(1, 0, transitionController, transitionController.mSyncEngine);
                    transitionController.moveToCollecting(transition2);
                }
                transition = transition2;
            }
            transition2 = null;
            transition = transition2;
        } else {
            transition = null;
        }
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE && activityRecord.mIsFlexPanel && transition == null) {
            Log.d("ActivityTaskManager", "if the flex panel does not run with the new transition, cancel start activity, transition=" + transition3);
            TaskDisplayArea defaultTaskDisplayArea = this.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea();
            Task rootTask = defaultTaskDisplayArea != null ? defaultTaskDisplayArea.getRootTask(1, 0) : null;
            ActivityRecord topVisibleActivity = rootTask != null ? rootTask.getTopVisibleActivity(true, false) : null;
            if (topVisibleActivity == null) {
                return -96;
            }
            Log.d("ActivityTaskManager", "flex panel cancel Task=" + rootTask + " topActivity=" + topVisibleActivity + " orientation=" + topVisibleActivity.getOrientation());
            return -96;
        }
        RemoteTransition remoteTransition = activityRecord.mPendingRemoteTransition;
        activityRecord.mPendingRemoteTransition = null;
        RemoteTransition remoteTransition2 = (!CoreRune.FW_SHELL_TRANSITION_BUG_FIX || (activityRecord.intent.getFlags() & EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT) == 0) ? remoteTransition : null;
        if (transition != null && this.mRequest.freezeScreen) {
            TaskDisplayArea taskDisplayArea = this.mLaunchParams.mPreferredTaskDisplayArea;
            if (taskDisplayArea == null) {
                taskDisplayArea = this.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea();
            }
            DisplayContent displayContentOrCreate = this.mRootWindowContainer.getDisplayContentOrCreate(taskDisplayArea.mDisplayContent.mDisplayId);
            if (displayContentOrCreate != null) {
                transitionController.collect(displayContentOrCreate);
                transitionController.collectVisibleChange(displayContentOrCreate);
            }
        } else if (transition != null && (intent = activityRecord.intent) != null && intent.getBooleanExtra("com.sec.intent.extra.FREEZE_TASK_DISPLAY_AREA", false)) {
            TaskDisplayArea taskDisplayArea2 = this.mLaunchParams.mPreferredTaskDisplayArea;
            if (taskDisplayArea2 == null) {
                taskDisplayArea2 = this.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea();
            }
            transitionController.collect(taskDisplayArea2);
            transitionController.collectVisibleChange(taskDisplayArea2);
        }
        try {
            this.mService.deferWindowLayout();
            transitionController.collect(activityRecord);
            int i6 = -96;
            try {
                try {
                    Trace.traceBegin(32L, "startActivityInner");
                    i6 = startActivityInner(activityRecord, activityRecord2, iVoiceInteractionSession, iVoiceInteractor, i, activityOptions, task, taskFragment, balVerdict, neededUriGrants, i2);
                    Trace.traceEnd(32L);
                } catch (Throwable th) {
                    Trace.traceEnd(32L);
                    handleStartResult(activityRecord, activityOptions, -96, transition, remoteTransition2);
                    throw th;
                }
            } catch (Exception e) {
                Slog.e("ActivityTaskManager", "Exception on startActivityInner", e);
                Trace.traceEnd(32L);
            }
            Task handleStartResult = handleStartResult(activityRecord, activityOptions, i6, transition, remoteTransition2);
            this.mService.continueWindowLayout();
            if (!this.mService.mWindowOrganizerController.mEnterSplitWithSingleStage.isEmpty()) {
                this.mService.mWindowOrganizerController.onEnterSplitWithSingleStageFinished("adjacent");
            }
            postStartActivityProcessing(i6, activityRecord, handleStartResult);
            Request request = this.mRequest;
            if (request != null && handleStartResult != null && this.mStartActivity != null) {
                int i7 = (activityRecord2 == null || (task2 = activityRecord2.task) == null) ? -1 : task2.mTaskId;
                int i8 = request.callingUid;
                if (request.caller != null) {
                    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            WindowProcessController processController = this.mService.getProcessController(this.mRequest.caller);
                            if (processController != null) {
                                i8 = processController.mInfo.uid;
                            }
                        } catch (Throwable th2) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th2;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
                final ContentDispatcher contentDispatcher = this.mService.mContentDispatcher;
                Request request2 = this.mRequest;
                final String str = request2.callingPackage;
                Intent intent3 = request2.intent;
                ActivityInfo activityInfo = request2.activityInfo;
                final int i9 = handleStartResult.mTaskId;
                contentDispatcher.getClass();
                if (activityInfo != null && (applicationInfo = activityInfo.applicationInfo) != null && applicationInfo.packageName != null && intent3 != null) {
                    final Intent intent4 = (Intent) intent3.clone();
                    final String str2 = activityInfo.applicationInfo.packageName;
                    final int i10 = i8;
                    final int i11 = i7;
                    contentDispatcher.mHandler.post(new Runnable() { // from class: com.android.server.pm.ContentDispatcher$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ContentDispatcher contentDispatcher2 = ContentDispatcher.this;
                            Intent intent5 = intent4;
                            String str3 = str2;
                            int i12 = i10;
                            int i13 = i9;
                            String str4 = str;
                            int i14 = i11;
                            contentDispatcher2.getClass();
                            boolean z = "android.intent.action.VIEW".equals(intent5.getAction()) && !"android".equals(str3) && intent5.getData() != null && i12 >= 0;
                            String extensionFromMimeType = z ? MimeTypeMap.getSingleton().getExtensionFromMimeType(contentDispatcher2.mContext.getContentResolver().getType(intent5.getData())) : "";
                            synchronized (contentDispatcher2.mPdfInfos) {
                                try {
                                    if (contentDispatcher2.mPdfInfos.get(Integer.valueOf(i13)) != null && !"android".equals(str4)) {
                                        if (!str3.equals(((ContentDispatcher.PdfInfo) contentDispatcher2.mPdfInfos.get(Integer.valueOf(i13))).mPackageName) && !"android.content.pm.action.REQUEST_PERMISSIONS".equals(intent5.getAction()) && !"android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION".equals(intent5.getAction()) && !"android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION".equals(intent5.getAction())) {
                                            contentDispatcher2.clearPdfTask(i13);
                                        } else if (str4 == null || !str4.equals(((ContentDispatcher.PdfInfo) contentDispatcher2.mPdfInfos.get(Integer.valueOf(i13))).mPackageName)) {
                                            contentDispatcher2.clearPdfTask(i13);
                                        }
                                    }
                                    if (z) {
                                        contentDispatcher2.clearPdfTask(i13);
                                        if (!"pdf".equals(extensionFromMimeType) && !"application/pdf".equals(intent5.getType())) {
                                            return;
                                        }
                                        ArrayMap arrayMap = contentDispatcher2.mPdfInfos;
                                        Integer valueOf = Integer.valueOf(i13);
                                        ContentDispatcher.PdfInfo pdfInfo = new ContentDispatcher.PdfInfo();
                                        pdfInfo.mCallingUid = i12;
                                        pdfInfo.mIntent = intent5;
                                        pdfInfo.mPackageName = str3;
                                        arrayMap.put(valueOf, pdfInfo);
                                    } else if (contentDispatcher2.mPdfInfos.get(Integer.valueOf(i14)) != null && contentDispatcher2.mPdfInfos.get(Integer.valueOf(i13)) == null && str3.equals(str4) && ((ContentDispatcher.PdfInfo) contentDispatcher2.mPdfInfos.get(Integer.valueOf(i14))).mPackageName.equals(str4)) {
                                        contentDispatcher2.mPdfInfos.put(Integer.valueOf(i13), (ContentDispatcher.PdfInfo) contentDispatcher2.mPdfInfos.get(Integer.valueOf(i14)));
                                    }
                                } finally {
                                }
                            }
                        }
                    });
                }
            }
            return i6;
        } catch (Throwable th3) {
            this.mService.continueWindowLayout();
            throw th3;
        }
    }

    public final void startResolvedActivity(ActivityRecord activityRecord, ActivityRecord activityRecord2, int i, ActivityOptions activityOptions, Task task, NeededUriGrants neededUriGrants) {
        ActivityTaskSupervisor activityTaskSupervisor = this.mSupervisor;
        try {
            ActivityMetricsLogger.LaunchingState notifyActivityLaunching = activityTaskSupervisor.mActivityMetricsLogger.notifyActivityLaunching(activityRecord.intent, activityRecord.resultTo, Binder.getCallingUid());
            this.mLastStartReason = "startResolvedActivity";
            this.mLastStartActivityTimeMs = System.currentTimeMillis();
            this.mLastStartActivityRecord = activityRecord;
            int startActivityUnchecked = startActivityUnchecked(activityRecord, activityRecord2, null, null, i, activityOptions, task, null, BackgroundActivityStartController.BalVerdict.ALLOW_PRIVILEGED, neededUriGrants, 0);
            this.mLastStartActivityResult = startActivityUnchecked;
            ActivityRecord activityRecord3 = this.mStartActivity;
            ActivityRecord activityRecord4 = this.mLastStartActivityRecord;
            activityTaskSupervisor.mActivityMetricsLogger.notifyActivityLaunched(notifyActivityLaunching, startActivityUnchecked, activityRecord3 == activityRecord4, activityRecord4, activityOptions);
        } finally {
            onExecutionComplete();
        }
    }

    public final void useSpegDisplayIfNeeded() {
        int i;
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        if (activityTaskManagerService.mSpeg == null) {
            return;
        }
        Request request = this.mRequest;
        if (request.intent == null || request.callingPackage == null) {
            return;
        }
        int i2 = request.realCallingUid;
        if (i2 == -1) {
            i2 = Binder.getCallingUid();
        }
        SpegService spegService = activityTaskManagerService.mSpeg;
        if (spegService.mIsSpegInOpeartion && i2 == (i = spegService.mSpegUid)) {
            int i3 = i2 == i ? spegService.mSpegDisplayId : -1;
            SafeActivityOptions safeActivityOptions = this.mRequest.activityOptions;
            ActivityOptions options = safeActivityOptions != null ? safeActivityOptions.getOptions(null, null, null, this.mSupervisor) : ActivityOptions.makeBasic();
            if (options.getLaunchDisplayId() != i3) {
                Request request2 = this.mRequest;
                options.setLaunchDisplayId(i3);
                options.setPendingIntentCreatorBackgroundActivityStartMode(1);
                request2.activityOptions = new SafeActivityOptions(options);
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i3, "Reuse hidden display #", " for ");
                m.append(this.mRequest.intent);
                Slog.d("SPEG", m.toString());
            }
        }
    }

    public final int waitResultIfNeeded(WaitResult waitResult, ActivityRecord activityRecord, ActivityMetricsLogger.LaunchingState launchingState) {
        int i = waitResult.result;
        if (i == 3 || (i == 2 && activityRecord.nowVisible && activityRecord.isState(ActivityRecord.State.RESUMED))) {
            waitResult.timeout = false;
            waitResult.who = activityRecord.mActivityComponent;
            waitResult.totalTime = 0L;
            return i;
        }
        ActivityTaskSupervisor activityTaskSupervisor = this.mSupervisor;
        activityTaskSupervisor.getClass();
        int i2 = waitResult.result;
        if (i2 == 2 || i2 == 0) {
            ActivityTaskSupervisor.WaitInfo waitInfo = new ActivityTaskSupervisor.WaitInfo(waitResult, activityRecord.mActivityComponent, launchingState);
            activityTaskSupervisor.mWaitingActivityLaunched.add(waitInfo);
            do {
                try {
                    activityTaskSupervisor.mService.mGlobalLock.wait();
                } catch (InterruptedException unused) {
                }
            } while (activityTaskSupervisor.mWaitingActivityLaunched.contains(waitInfo));
        }
        if (i == 0 && waitResult.result == 2) {
            return 2;
        }
        return i;
    }
}
