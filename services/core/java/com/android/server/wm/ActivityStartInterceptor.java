package com.android.server.wm;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.Process;
import android.os.UserManager;

import com.samsung.android.knox.PersonaManagerInternal;

public final class ActivityStartInterceptor {
    public ActivityInfo mAInfo;
    public ActivityOptions mActivityOptions;
    public String mCallingFeatureId;
    public String mCallingPackage;
    public int mCallingPid;
    public int mCallingUid;
    public Task mInTask;
    public TaskFragment mInTaskFragment;
    public Intent mIntent;
    public boolean mIsInterceptedForAliasActivity;
    public boolean mIsInterceptedForCarLife;
    public PersonaManagerInternal mPersonaManagerInternal;
    public TaskDisplayArea mPresumableLaunchDisplayArea;
    public ResolveInfo mRInfo;
    public int mRealCallingPid;
    public int mRealCallingUid;
    public String mResolvedType;
    public final ActivityTaskManagerService mService;
    public final Context mServiceContext;
    public ActivityRecord mSourceRecord;
    public int mStartFlags;
    public final ActivityTaskSupervisor mSupervisor;
    public int mUserId;
    public UserManager mUserManager;

    public ActivityStartInterceptor(
            ActivityTaskManagerService activityTaskManagerService,
            ActivityTaskSupervisor activityTaskSupervisor,
            Context context) {
        this.mService = activityTaskManagerService;
        this.mSupervisor = activityTaskSupervisor;
        this.mServiceContext = context;
    }

    public final IntentSender createIntentSenderForOriginalIntent(int i, int i2) {
        ActivityOptions makeBasic;
        IBinder launchTaskFragmentToken;
        ActivityOptions activityOptions = this.mActivityOptions;
        TaskFragment taskFragment = null;
        if (activityOptions == null || activityOptions.getAnimationType() != 12) {
            makeBasic = ActivityOptions.makeBasic();
        } else {
            this.mActivityOptions = null;
            makeBasic = ActivityOptions.makeOpenCrossProfileAppsAnimation();
        }
        makeBasic.setPendingIntentCreatorBackgroundActivityStartMode(1);
        TaskFragment taskFragment2 = this.mInTaskFragment;
        if (taskFragment2 != null) {
            taskFragment = taskFragment2;
        } else {
            ActivityOptions activityOptions2 = this.mActivityOptions;
            if (activityOptions2 != null
                    && (launchTaskFragmentToken = activityOptions2.getLaunchTaskFragmentToken())
                            != null) {
                taskFragment =
                        (TaskFragment)
                                this.mService.mWindowOrganizerController.mLaunchTaskFragments.get(
                                        launchTaskFragmentToken);
            }
        }
        if (taskFragment != null) {
            makeBasic.setLaunchTaskFragmentToken(taskFragment.mFragmentToken);
        }
        return new IntentSender(
                this.mService.getIntentSenderLocked(
                        2,
                        i,
                        this.mUserId,
                        0,
                        i2,
                        makeBasic.toBundle(),
                        null,
                        this.mCallingPackage,
                        this.mCallingFeatureId,
                        null,
                        new Intent[] {this.mIntent},
                        new String[] {this.mResolvedType}));
    }

    public final DisplayContent getActualDisplayContent(int i) {
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        DisplayContent displayContent =
                activityTaskManagerService.mRootWindowContainer.getDisplayContent(i);
        return displayContent == null
                ? activityTaskManagerService.mRootWindowContainer.mDefaultDisplay
                : displayContent;
    }

    public final ActivityInterceptorCallback.ActivityInterceptorInfo getInterceptorInfo(
            ActivityStartInterceptor$$ExternalSyntheticLambda1
                    activityStartInterceptor$$ExternalSyntheticLambda1) {
        return new ActivityInterceptorCallback.ActivityInterceptorInfo.Builder(
                        this.mCallingUid,
                        this.mCallingPid,
                        this.mRealCallingUid,
                        this.mRealCallingPid,
                        this.mUserId,
                        this.mIntent,
                        this.mRInfo,
                        this.mAInfo)
                .setResolvedType(this.mResolvedType)
                .setCallingPackage(this.mCallingPackage)
                .setCallingFeatureId(this.mCallingFeatureId)
                .setCheckedOptions(this.mActivityOptions)
                .setClearOptionsAnimationRunnable(
                        activityStartInterceptor$$ExternalSyntheticLambda1)
                .build();
    }

    public final boolean hasAliasActivity(Intent intent) {
        return this.mIsInterceptedForAliasActivity && this.mIntent == intent;
    }

    public final boolean hasCarLifeDisplay() {
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        for (int childCount = activityTaskManagerService.mRootWindowContainer.getChildCount() - 1;
                childCount >= 0;
                childCount--) {
            if (((DisplayContent)
                            activityTaskManagerService.mRootWindowContainer.getChildAt(childCount))
                    .isCarLifeDisplay()) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x035f, code lost:

       r1 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x035d, code lost:

       if ("android.intent.action.MAIN".equals(r18.mIntent.getAction()) != false) goto L129;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean intercept(
            android.content.Intent r19,
            android.content.pm.ResolveInfo r20,
            android.content.pm.ActivityInfo r21,
            java.lang.String r22,
            com.android.server.wm.Task r23,
            com.android.server.wm.TaskFragment r24,
            int r25,
            int r26,
            android.app.ActivityOptions r27,
            com.android.server.wm.TaskDisplayArea r28) {
        /*
            Method dump skipped, instructions count: 2586
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.ActivityStartInterceptor.intercept(android.content.Intent,"
                    + " android.content.pm.ResolveInfo, android.content.pm.ActivityInfo,"
                    + " java.lang.String, com.android.server.wm.Task,"
                    + " com.android.server.wm.TaskFragment, int, int, android.app.ActivityOptions,"
                    + " com.android.server.wm.TaskDisplayArea):boolean");
    }

    public final void resolveLaunchTaskIdForAliasManagedTarget(Task task) {
        Intent intent = new Intent(this.mIntent);
        intent.setComponent(task.getBaseIntent().getComponent());
        intent.setLaunchTaskIdForAliasManagedTarget(task.mTaskId);
        ResolveInfo resolveIntent =
                this.mSupervisor.resolveIntent(
                        intent, this.mResolvedType, this.mUserId, 0, 1000, Process.myPid());
        ActivityInfo resolveActivity =
                this.mSupervisor.resolveActivity(intent, resolveIntent, this.mStartFlags, null);
        if (resolveIntent == null || resolveActivity == null) {
            return;
        }
        this.mIntent = intent;
        this.mRInfo = resolveIntent;
        this.mAInfo = resolveActivity;
    }
}
