package com.android.server.wm;

import android.app.ActivityOptions;
import android.app.BackgroundStartPrivileges;
import android.app.IApplicationThread;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.util.ArrayUtils;
import com.android.server.DualAppManagerService;
import com.android.server.am.BroadcastStats$$ExternalSyntheticOutline0;
import com.android.server.am.PendingIntentRecord;
import com.android.server.uri.NeededUriGrants;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.uri.UriGrantsManagerService;

import com.samsung.android.app.SemDualAppManager;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ActivityStartController {
    public final ActivityStarter.Factory mFactory;
    public final Handler mHandler;
    public ActivityRecord mLastHomeActivityStartRecord;
    public int mLastHomeActivityStartResult;
    public ActivityStarter mLastStarter;
    public final PendingRemoteAnimationRegistry mPendingRemoteAnimationRegistry;
    public final ActivityTaskManagerService mService;
    public final ActivityTaskSupervisor mSupervisor;
    public final ActivityRecord[] tmpOutRecord = new ActivityRecord[1];
    public boolean mCheckedForSetup = false;
    public boolean mInExecution = false;

    public ActivityStartController(
            ActivityTaskManagerService activityTaskManagerService,
            ActivityTaskSupervisor activityTaskSupervisor,
            ActivityStarter.Factory factory) {
        this.mService = activityTaskManagerService;
        this.mSupervisor = activityTaskSupervisor;
        this.mHandler = new Handler(activityTaskManagerService.mH.getLooper(), null, true);
        this.mFactory = factory;
        factory.setController(this);
        this.mPendingRemoteAnimationRegistry =
                new PendingRemoteAnimationRegistry(
                        activityTaskManagerService.mGlobalLock, activityTaskManagerService.mH);
    }

    public final int checkTargetUser(int i, int i2, String str, int i3, boolean z) {
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        if (z) {
            return activityTaskManagerService.handleIncomingUser(i2, i3, i, str);
        }
        activityTaskManagerService.mAmInternal.ensureNotSpecialUser(i);
        return i;
    }

    public final void dump(PrintWriter printWriter, String str, String str2) {
        ActivityRecord activityRecord;
        ActivityRecord activityRecord2;
        ActivityRecord activityRecord3;
        boolean z = false;
        boolean z2 = str2 != null;
        ActivityRecord activityRecord4 = this.mLastHomeActivityStartRecord;
        if (activityRecord4 != null && (!z2 || str2.equals(activityRecord4.packageName))) {
            printWriter.print(str);
            printWriter.print("mLastHomeActivityStartResult=");
            printWriter.println(this.mLastHomeActivityStartResult);
            printWriter.print(str);
            printWriter.println("mLastHomeActivityStartRecord:");
            this.mLastHomeActivityStartRecord.dump(printWriter, str.concat("  "), true);
            z = true;
        }
        ActivityStarter activityStarter = this.mLastStarter;
        if (activityStarter != null
                && (!z2
                        || (((activityRecord = activityStarter.mLastStartActivityRecord) != null
                                        && str2.equals(activityRecord.packageName))
                                || (((activityRecord2 = activityStarter.mStartActivity) != null
                                                && str2.equals(activityRecord2.packageName))
                                        || ((activityRecord3 = this.mLastHomeActivityStartRecord)
                                                        != null
                                                && str2.equals(activityRecord3.packageName)))))) {
            if (!z) {
                printWriter.print(str);
                printWriter.print("mLastHomeActivityStartResult=");
                printWriter.println(this.mLastHomeActivityStartResult);
                z = true;
            }
            printWriter.print(str);
            printWriter.println("mLastStarter:");
            ActivityStarter activityStarter2 = this.mLastStarter;
            String concat = str.concat("  ");
            activityStarter2.getClass();
            printWriter.print(concat);
            printWriter.print("mCurrentUser=");
            BroadcastStats$$ExternalSyntheticOutline0.m(
                    activityStarter2.mRootWindowContainer.mCurrentUser,
                    printWriter,
                    concat,
                    "mLastStartReason=");
            printWriter.println(activityStarter2.mLastStartReason);
            printWriter.print(concat);
            printWriter.print("mLastStartActivityTimeMs=");
            printWriter.println(
                    DateFormat.getDateTimeInstance()
                            .format(new Date(activityStarter2.mLastStartActivityTimeMs)));
            printWriter.print(concat);
            printWriter.print("mLastStartActivityResult=");
            printWriter.println(activityStarter2.mLastStartActivityResult);
            if (activityStarter2.mLastStartActivityRecord != null) {
                printWriter.print(concat);
                printWriter.println("mLastStartActivityRecord:");
                activityStarter2.mLastStartActivityRecord.dump(printWriter, concat + "  ", true);
            }
            if (activityStarter2.mStartActivity != null) {
                printWriter.print(concat);
                printWriter.println("mStartActivity:");
                activityStarter2.mStartActivity.dump(printWriter, concat + "  ", true);
            }
            if (activityStarter2.mIntent != null) {
                printWriter.print(concat);
                printWriter.print("mIntent=");
                printWriter.println(activityStarter2.mIntent);
            }
            if (activityStarter2.mOptions != null) {
                printWriter.print(concat);
                printWriter.print("mOptions=");
                printWriter.println(activityStarter2.mOptions);
            }
            printWriter.print(concat);
            printWriter.print("mLaunchMode=");
            printWriter.print(ActivityInfo.launchModeToString(activityStarter2.mLaunchMode));
            printWriter.print(concat);
            printWriter.print("mLaunchFlags=0x");
            printWriter.print(Integer.toHexString(activityStarter2.mLaunchFlags));
            printWriter.print(" mDoResume=");
            printWriter.print(activityStarter2.mDoResume);
            printWriter.print(" mAddingToTask=");
            printWriter.print(activityStarter2.mAddingToTask);
            printWriter.print(" mInTaskFragment=");
            printWriter.println(activityStarter2.mInTaskFragment);
            if (z2) {
                return;
            }
        }
        if (z) {
            return;
        }
        printWriter.print(str);
        printWriter.println("(nothing)");
    }

    public final ActivityStarter obtainStarter(Intent intent, String str) {
        ActivityStarter obtain = this.mFactory.obtain();
        ActivityStarter.Request request = obtain.mRequest;
        request.intent = intent;
        request.reason = str;
        return obtain;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int startActivities(
            IApplicationThread iApplicationThread,
            int i,
            int i2,
            int i3,
            String str,
            String str2,
            Intent[] intentArr,
            String[] strArr,
            IBinder iBinder,
            SafeActivityOptions safeActivityOptions,
            int i4,
            String str3,
            PendingIntentRecord pendingIntentRecord,
            BackgroundStartPrivileges backgroundStartPrivileges) {
        int i5;
        int i6;
        SparseArray sparseArray;
        int i7;
        int i8;
        int i9;
        SafeActivityOptions safeActivityOptions2;
        int i10;
        SafeActivityOptions safeActivityOptions3;
        int i11;
        int i12;
        ActivityStarter[] activityStarterArr;
        NeededUriGrants internalCheckGrantUriPermissionFromIntent;
        SparseArray sparseArray2;
        SafeActivityOptions safeActivityOptions4;
        Rect rect;
        int i13;
        Rect rect2;
        IApplicationThread iApplicationThread2 = iApplicationThread;
        if (intentArr == null) {
            throw new NullPointerException("intents is null");
        }
        if (strArr == null) {
            throw new NullPointerException("resolvedTypes is null");
        }
        if (intentArr.length != strArr.length) {
            throw new IllegalArgumentException("intents are length different than resolvedTypes");
        }
        int callingPid = i2 != 0 ? i2 : Binder.getCallingPid();
        int i14 = i3;
        if (i14 == -1) {
            i14 = Binder.getCallingUid();
        }
        if (i >= 0) {
            i5 = i;
            i6 = -1;
        } else if (iApplicationThread2 == null) {
            i6 = callingPid;
            i5 = i14;
        } else {
            i5 = -1;
            i6 = -1;
        }
        int computeResolveFilterUid = ActivityStarter.computeResolveFilterUid(i5, i14, -10000);
        SparseArray sparseArray3 = new SparseArray();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (safeActivityOptions != null) {
            safeActivityOptions.mShouldCheckFreeform = true;
            ActivityOptions cloneLaunchingOptions =
                    SafeActivityOptions.cloneLaunchingOptions(safeActivityOptions.mOriginalOptions);
            ActivityOptions cloneLaunchingOptions2 =
                    SafeActivityOptions.cloneLaunchingOptions(safeActivityOptions.mCallerOptions);
            if (cloneLaunchingOptions == null && cloneLaunchingOptions2 == null) {
                sparseArray = sparseArray3;
                i7 = computeResolveFilterUid;
                i8 = i6;
                safeActivityOptions4 = null;
                i9 = 0;
            } else {
                if (safeActivityOptions.mShouldCheckFreeform) {
                    ActivityOptions activityOptions = safeActivityOptions.mOriginalOptions;
                    int launchWindowingMode =
                            activityOptions != null ? activityOptions.getLaunchWindowingMode() : 0;
                    ActivityOptions activityOptions2 = safeActivityOptions.mOriginalOptions;
                    if (activityOptions2 != null) {
                        rect = activityOptions2.getLaunchBounds();
                        sparseArray = sparseArray3;
                    } else {
                        sparseArray = sparseArray3;
                        rect = null;
                    }
                    ActivityOptions activityOptions3 = safeActivityOptions.mCallerOptions;
                    if (activityOptions3 != null) {
                        i13 = activityOptions3.getLaunchWindowingMode();
                        i7 = computeResolveFilterUid;
                    } else {
                        i7 = computeResolveFilterUid;
                        i13 = 0;
                    }
                    ActivityOptions activityOptions4 = safeActivityOptions.mCallerOptions;
                    if (activityOptions4 != null) {
                        rect2 = activityOptions4.getLaunchBounds();
                        i8 = i6;
                    } else {
                        i8 = i6;
                        rect2 = null;
                    }
                    if (launchWindowingMode == 5) {
                        cloneLaunchingOptions.setLaunchWindowingMode(5);
                    }
                    if (rect != null) {
                        cloneLaunchingOptions.setLaunchBounds(rect);
                    }
                    if (i13 == 5) {
                        cloneLaunchingOptions2.setLaunchWindowingMode(5);
                    }
                    if (rect2 != null) {
                        cloneLaunchingOptions2.setLaunchBounds(rect2);
                    }
                    i9 = 0;
                    safeActivityOptions.mShouldCheckFreeform = false;
                } else {
                    sparseArray = sparseArray3;
                    i7 = computeResolveFilterUid;
                    i8 = i6;
                    i9 = 0;
                }
                safeActivityOptions4 =
                        new SafeActivityOptions(
                                cloneLaunchingOptions,
                                safeActivityOptions.mOriginalCallingPid,
                                safeActivityOptions.mOriginalCallingUid);
                safeActivityOptions4.mCallerOptions = cloneLaunchingOptions2;
                safeActivityOptions4.mRealCallingPid = safeActivityOptions.mRealCallingPid;
                safeActivityOptions4.mRealCallingUid = safeActivityOptions.mRealCallingUid;
            }
            safeActivityOptions2 = safeActivityOptions4;
        } else {
            sparseArray = sparseArray3;
            i7 = computeResolveFilterUid;
            i8 = i6;
            i9 = 0;
            safeActivityOptions2 = null;
        }
        try {
            Intent[] intentArr2 =
                    (Intent[])
                            ArrayUtils.filterNotNull(
                                    intentArr,
                                    new ActivityStartController$$ExternalSyntheticLambda0());
            int length = intentArr2.length;
            ActivityStarter[] activityStarterArr2 = new ActivityStarter[length];
            int i15 = i9;
            while (i15 < intentArr2.length) {
                Intent intent = intentArr2[i15];
                if (intent.hasFileDescriptors()) {
                    throw new IllegalArgumentException("File descriptors passed in Intent");
                }
                if (intent.getComponent() != null) {
                    activityStarterArr = activityStarterArr2;
                    i12 = 1;
                } else {
                    i12 = i9;
                    activityStarterArr = activityStarterArr2;
                }
                Intent intent2 = new Intent(intent);
                intent2.removeExtendedFlags(1);
                SafeActivityOptions safeActivityOptions5 = safeActivityOptions2;
                ActivityTaskSupervisor activityTaskSupervisor = this.mSupervisor;
                int i16 = i9;
                boolean z = i12;
                int i17 = i15;
                SparseArray sparseArray4 = sparseArray;
                int i18 = i14;
                ActivityStarter[] activityStarterArr3 = activityStarterArr;
                int i19 = i7;
                int i20 = length;
                int i21 = i8;
                ActivityInfo activityInfoForUser =
                        this.mService.mAmInternal.getActivityInfoForUser(
                                activityTaskSupervisor.resolveActivity(
                                        intent2,
                                        activityTaskSupervisor.resolveIntent(
                                                intent2, strArr[i15], i4, 0, i19, i21),
                                        i16,
                                        null),
                                i4);
                if (activityInfoForUser != null) {
                    try {
                        UriGrantsManagerInternal uriGrantsManagerInternal =
                                this.mSupervisor.mService.mUgmInternal;
                        ApplicationInfo applicationInfo = activityInfoForUser.applicationInfo;
                        internalCheckGrantUriPermissionFromIntent =
                                ((UriGrantsManagerService.LocalService) uriGrantsManagerInternal)
                                        .internalCheckGrantUriPermissionFromIntent(
                                                intent2,
                                                i19,
                                                applicationInfo.packageName,
                                                UserHandle.getUserId(applicationInfo.uid),
                                                null);
                        ApplicationInfo applicationInfo2 = activityInfoForUser.applicationInfo;
                        if ((applicationInfo2.privateFlags & 2) != 0) {
                            throw new IllegalArgumentException(
                                    "FLAG_CANT_SAVE_STATE not supported here");
                        }
                        sparseArray2 = sparseArray4;
                        sparseArray2.put(applicationInfo2.uid, applicationInfo2.packageName);
                    } catch (SecurityException unused) {
                        Slog.d(
                                "ActivityTaskManager",
                                "Not allowed to start activity since no uri permission.");
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -96;
                    }
                } else {
                    sparseArray2 = sparseArray4;
                    internalCheckGrantUriPermissionFromIntent = null;
                }
                boolean z2 = i17 == intentArr2.length - 1 ? 1 : i16;
                SafeActivityOptions safeActivityOptions6 =
                        z2 != 0 ? safeActivityOptions : safeActivityOptions5;
                ActivityStarter obtainStarter = obtainStarter(intent2, str3);
                Intent[] intentArr3 = intentArr2;
                ActivityStarter.Request request = obtainStarter.mRequest;
                request.intentGrants = internalCheckGrantUriPermissionFromIntent;
                request.caller = iApplicationThread2;
                request.resolvedType = strArr[i17];
                request.activityInfo = activityInfoForUser;
                request.requestCode = -1;
                request.callingPid = i21;
                request.callingUid = i5;
                request.callingPackage = str;
                request.callingFeatureId = str2;
                request.realCallingPid = callingPid;
                request.realCallingUid = i18;
                request.activityOptions = safeActivityOptions6;
                request.componentSpecified = z;
                request.allowPendingRemoteAnimationRegistryLookup = z2;
                request.originatingPendingIntent = pendingIntentRecord;
                request.forcedBalByPiSender = backgroundStartPrivileges;
                activityStarterArr3[i17] = obtainStarter;
                i7 = i19;
                safeActivityOptions2 = safeActivityOptions5;
                i14 = i18;
                i15 = i17 + 1;
                i8 = i21;
                intentArr2 = intentArr3;
                activityStarterArr2 = activityStarterArr3;
                i9 = 0;
                iApplicationThread2 = iApplicationThread;
                sparseArray = sparseArray2;
                length = i20;
            }
            ActivityStarter[] activityStarterArr4 = activityStarterArr2;
            int i22 = length;
            SparseArray sparseArray5 = sparseArray;
            int i23 = i7;
            if (sparseArray5.size() > 1) {
                StringBuilder sb = new StringBuilder("startActivities: different apps [");
                int size = sparseArray5.size();
                int i24 = 0;
                while (i24 < size) {
                    sb.append((String) sparseArray5.valueAt(i24));
                    sb.append(i24 == size + (-1) ? "]" : ", ");
                    i24++;
                }
                sb.append(" from ");
                sb.append(str);
                Slog.wtf("ActivityTaskManager", sb.toString());
                i10 = 1;
            } else {
                i10 = 1;
            }
            ActivityRecord[] activityRecordArr = new ActivityRecord[i10];
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mService.deferWindowLayout();
                    this.mService
                                    .mWindowManager
                                    .mStartingSurfaceController
                                    .mDeferringAddStartingWindow =
                            true;
                    IBinder iBinder2 = iBinder;
                    int i25 = 0;
                    while (i25 < i22) {
                        try {
                            ActivityStarter activityStarter = activityStarterArr4[i25];
                            ActivityStarter.Request request2 = activityStarter.mRequest;
                            request2.resultTo = iBinder2;
                            request2.outActivity = activityRecordArr;
                            int execute = activityStarter.execute();
                            if (execute < 0) {
                                for (int i26 = i25 + 1; i26 < i22; i26++) {
                                    this.mFactory.recycle(activityStarterArr4[i26]);
                                }
                                try {
                                    this.mService.mWindowManager.mStartingSurfaceController
                                            .endDeferAddStartingWindow(
                                                    safeActivityOptions != null
                                                            ? safeActivityOptions.mOriginalOptions
                                                            : null);
                                    this.mService.continueWindowLayout();
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return execute;
                                } catch (Exception e) {
                                    String stackTraceString = Log.getStackTraceString(e);
                                    Slog.e(
                                            "ActivityTaskManager",
                                            "endDeferAddStartingWindow failed, "
                                                    + stackTraceString);
                                    WindowManagerServiceExt.logCriticalInfo(stackTraceString, null);
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return -96;
                                }
                            }
                            safeActivityOptions3 = safeActivityOptions;
                            try {
                                ActivityRecord activityRecord = activityRecordArr[0];
                                if (activityRecord != null) {
                                    i11 = i23;
                                    if (activityRecord.getUid() == i11) {
                                        iBinder2 = activityRecord.token;
                                        i25++;
                                        i23 = i11;
                                    }
                                } else {
                                    i11 = i23;
                                }
                                if (i25 < i22 - 1) {
                                    activityStarterArr4[i25 + 1].mRequest.intent.addFlags(
                                            268435456);
                                }
                                iBinder2 = iBinder;
                                i25++;
                                i23 = i11;
                            } catch (Throwable th) {
                                th = th;
                                try {
                                    this.mService.mWindowManager.mStartingSurfaceController
                                            .endDeferAddStartingWindow(
                                                    safeActivityOptions3 != null
                                                            ? safeActivityOptions3.mOriginalOptions
                                                            : null);
                                    this.mService.continueWindowLayout();
                                    throw th;
                                } catch (Exception e2) {
                                    String stackTraceString2 = Log.getStackTraceString(e2);
                                    Slog.e(
                                            "ActivityTaskManager",
                                            "endDeferAddStartingWindow failed, "
                                                    + stackTraceString2);
                                    WindowManagerServiceExt.logCriticalInfo(
                                            stackTraceString2, null);
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return -96;
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            safeActivityOptions3 = safeActivityOptions;
                        }
                    }
                    try {
                        this.mService.mWindowManager.mStartingSurfaceController
                                .endDeferAddStartingWindow(
                                        safeActivityOptions != null
                                                ? safeActivityOptions.mOriginalOptions
                                                : null);
                        this.mService.continueWindowLayout();
                        WindowManagerService.resetPriorityAfterLockedSection();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    } catch (Exception e3) {
                        String stackTraceString3 = Log.getStackTraceString(e3);
                        Slog.e(
                                "ActivityTaskManager",
                                "endDeferAddStartingWindow failed, " + stackTraceString3);
                        WindowManagerServiceExt.logCriticalInfo(stackTraceString3, null);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -96;
                    }
                } catch (Throwable th3) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th3;
                }
            }
        } catch (Throwable th4) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th4;
        }
    }

    public final int startActivityInPackage(
            int i,
            int i2,
            int i3,
            String str,
            String str2,
            Intent intent,
            String str3,
            IBinder iBinder,
            String str4,
            int i4,
            SafeActivityOptions safeActivityOptions,
            int i5,
            Task task,
            String str5,
            PendingIntentRecord pendingIntentRecord,
            BackgroundStartPrivileges backgroundStartPrivileges) {
        String str6;
        String str7;
        Intent intent2;
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        if ((activityTaskManagerService.mSIOPLevel >= 6
                        || activityTaskManagerService.mBatteryOverheatLevel > 0)
                && !activityTaskManagerService.isPossibleToStart(intent)) {
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(7));
            return -102;
        }
        int checkTargetUser = checkTargetUser(i5, i2, str5, i3, false);
        if (!SemDualAppManager.isDualAppId(SemDualAppManager.getDualAppProfileId())
                || intent == null
                || intent.getComponent() == null
                || (!("com.tencent.mm".equals(intent.getComponent().getPackageName())
                                || "com.tencent.mobileqq"
                                        .equals(intent.getComponent().getPackageName()))
                        || i3 == 1000)) {
            str6 = str3;
            str7 = str5;
            intent2 = intent;
        } else {
            int userId = UserHandle.getUserId(i3);
            intent2 =
                    DualAppManagerService.startDAChooserActivity(
                            userId, checkTargetUser, i3, intent, str);
            if (intent2 != null) {
                str6 = null;
            } else {
                str6 = str3;
                intent2 = intent;
            }
            if (SemDualAppManager.isDualAppId(userId)) {
                String packageName =
                        intent2.getComponent() != null
                                ? intent2.getComponent().getPackageName()
                                : intent2.getPackage();
                if (str != null
                        && packageName != null
                        && !str.equals(packageName)
                        && DualAppManagerService.shouldForwardToOwner(packageName)) {
                    str7 = str5;
                    checkTargetUser = 0;
                }
            }
            str7 = str5;
        }
        ActivityStarter obtainStarter = obtainStarter(intent2, str7);
        ActivityStarter.Request request = obtainStarter.mRequest;
        request.callingUid = i;
        request.realCallingPid = i2;
        request.realCallingUid = i3;
        request.callingPackage = str;
        request.callingFeatureId = str2;
        request.resolvedType = str6;
        request.resultTo = iBinder;
        request.resultWho = str4;
        request.requestCode = i4;
        request.startFlags = 0;
        request.activityOptions = safeActivityOptions;
        request.userId = checkTargetUser;
        request.inTask = task;
        request.originatingPendingIntent = pendingIntentRecord;
        request.forcedBalByPiSender = backgroundStartPrivileges;
        return obtainStarter.execute();
    }

    public final boolean startExistingRecents(Intent intent, ActivityOptions activityOptions) {
        ActivityRecord activityRecord;
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        Task rootTask =
                activityTaskManagerService
                        .mRootWindowContainer
                        .mDefaultDisplay
                        .getDefaultTaskDisplayArea()
                        .getRootTask(
                                0,
                                activityTaskManagerService.mRecentTasks.mRecentsComponent.equals(
                                                intent.getComponent())
                                        ? 3
                                        : 2);
        if (rootTask != null
                && (activityRecord = rootTask.topRunningActivity(false)) != null
                && ((!activityRecord.isVisibleRequested() || !rootTask.isTopRootTaskInDisplayArea())
                        && activityRecord.attachedToProcess()
                        && activityRecord.mActivityComponent.equals(intent.getComponent()))) {
            if (activityTaskManagerService.mRecentTasks.isCallerRecents(activityRecord.getUid())
                    && !activityRecord.mDisplayContent.isKeyguardLocked()) {
                if (activityTaskManagerService.mController != null
                        && activityRecord.isActivityTypeRecents()) {
                    try {
                        if (!activityTaskManagerService.mController.activityStarting(
                                intent.cloneFilter(), activityRecord.packageName)) {
                            return false;
                        }
                    } catch (RemoteException unused) {
                        activityTaskManagerService.mController = null;
                    }
                }
                DisplayContent displayContent = activityRecord.getDisplayContent();
                if (displayContent != null && !displayContent.mIsInExitingRecents) {
                    displayContent.mIsInExitingRecents = true;
                }
                activityTaskManagerService.mRootWindowContainer.startPowerModeLaunchIfNeeded(
                        true, activityRecord);
                ActivityTaskSupervisor activityTaskSupervisor = this.mSupervisor;
                ActivityMetricsLogger.LaunchingState notifyActivityLaunching =
                        activityTaskSupervisor.mActivityMetricsLogger.notifyActivityLaunching(
                                intent, null, -1);
                Task task = activityRecord.task;
                activityTaskManagerService.deferWindowLayout();
                try {
                    TransitionController transitionController =
                            activityRecord.mTransitionController;
                    Transition transition = transitionController.mCollectingTransition;
                    if (transition != null) {
                        IApplicationThread iApplicationThread = activityRecord.app.mThread;
                        TransitionController transitionController2 = transition.mController;
                        WindowProcessController processController =
                                transitionController2.mAtm.getProcessController(iApplicationThread);
                        if (processController != null) {
                            transitionController2.mRemotePlayer.update(
                                    processController, true, true);
                        }
                        transitionController.setTransientLaunch(
                                TaskDisplayArea.getRootTaskAbove(rootTask), activityRecord);
                    }
                    task.moveToFront("startExistingRecents", null);
                    task.mInResumeTopActivity = true;
                    task.resumeTopActivity(null, activityOptions, true);
                    activityTaskSupervisor.mActivityMetricsLogger.notifyActivityLaunched(
                            notifyActivityLaunching, 2, false, activityRecord, activityOptions);
                    if (displayContent != null && displayContent.mIsInExitingRecents) {
                        displayContent.mIsInExitingRecents = false;
                    }
                    task.mInResumeTopActivity = false;
                    activityTaskManagerService.continueWindowLayout();
                    return true;
                } catch (Throwable th) {
                    if (displayContent != null && displayContent.mIsInExitingRecents) {
                        displayContent.mIsInExitingRecents = false;
                    }
                    task.mInResumeTopActivity = false;
                    activityTaskManagerService.continueWindowLayout();
                    throw th;
                }
            }
        }
        return false;
    }
}
