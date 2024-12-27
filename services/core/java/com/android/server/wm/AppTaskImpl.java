package com.android.server.wm;

import android.app.ActivityManager;
import android.app.BackgroundStartPrivileges;
import android.app.IAppTask;
import android.app.IApplicationThread;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.UserHandle;
import android.util.Slog;

import com.samsung.android.rune.CoreRune;

public final class AppTaskImpl extends IAppTask.Stub {
    public final int mCallingUid;
    public final ActivityTaskManagerService mService;
    public final int mTaskId;

    public AppTaskImpl(ActivityTaskManagerService activityTaskManagerService, int i, int i2) {
        this.mService = activityTaskManagerService;
        this.mTaskId = i;
        this.mCallingUid = i2;
    }

    public final void checkCallerOrSystemOrRoot() {
        if (this.mCallingUid == Binder.getCallingUid()
                || 1000 == Binder.getCallingUid()
                || Binder.getCallingUid() == 0) {
            return;
        }
        throw new SecurityException(
                "Caller "
                        + this.mCallingUid
                        + " does not match caller of getAppTasks(): "
                        + Binder.getCallingUid());
    }

    public final void finishAndRemoveTask() {
        checkCallerOrSystemOrRoot();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int callingUid = Binder.getCallingUid();
                int callingPid = Binder.getCallingPid();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (!this.mService.mTaskSupervisor.removeTaskById(
                            this.mTaskId,
                            callingUid,
                            callingPid,
                            "finish-and-remove-task",
                            false,
                            true,
                            false)) {
                        throw new IllegalArgumentException(
                                "Unable to find task ID " + this.mTaskId);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final ActivityManager.RecentTaskInfo getTaskInfo() {
        ActivityManager.RecentTaskInfo createRecentTaskInfo;
        checkCallerOrSystemOrRoot();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Task anyTaskForId =
                            this.mService.mRootWindowContainer.anyTaskForId(
                                    this.mTaskId, 1, null, false);
                    if (anyTaskForId == null) {
                        throw new IllegalArgumentException(
                                "Unable to find task ID " + this.mTaskId);
                    }
                    createRecentTaskInfo =
                            this.mService.mRecentTasks.createRecentTaskInfo(
                                    anyTaskForId, false, true);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return createRecentTaskInfo;
    }

    public final void moveToFront(IApplicationThread iApplicationThread, String str) {
        WindowProcessController processController;
        PackageManager packageManager;
        checkCallerOrSystemOrRoot();
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        this.mService.assertPackageMatchesCallingUid(str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (CoreRune.SYSFW_APP_SPEG
                    && (packageManager = this.mService.mContext.getPackageManager()) != null
                    && packageManager.isSpeg(callingUid)) {
                Slog.w(
                        "SPEG",
                        "Not allowed app transition for callingPackage " + str + ":" + callingUid);
                return;
            }
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (iApplicationThread != null) {
                        try {
                            processController =
                                    this.mService.getProcessController(iApplicationThread);
                        } catch (Throwable th) {
                            th = th;
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    } else {
                        processController = null;
                    }
                    BackgroundActivityStartController.BalVerdict checkBackgroundActivityStart =
                            this.mService.mTaskSupervisor.mBalController
                                    .checkBackgroundActivityStart(
                                            callingUid,
                                            callingPid,
                                            str,
                                            -1,
                                            -1,
                                            processController,
                                            null,
                                            BackgroundStartPrivileges.NONE,
                                            null,
                                            null,
                                            null);
                    if (checkBackgroundActivityStart.mCode != 0
                            || this.mService.mAmInternal.isBackgroundActivityStartsEnabled()) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        this.mService.mTaskSupervisor.startActivityFromRecents(
                                callingPid, callingUid, this.mTaskId, null, true);
                    } else {
                        Slog.w(
                                "AppTaskImpl",
                                "moveTaskToFront blocked: : " + checkBackgroundActivityStart);
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (RuntimeException e) {
            ActivityTaskManagerService.logAndRethrowRuntimeExceptionOnTransact(e, "AppTaskImpl");
            throw null;
        }
    }

    public final void setExcludeFromRecents(boolean z) {
        checkCallerOrSystemOrRoot();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Task anyTaskForId =
                            this.mService.mRootWindowContainer.anyTaskForId(
                                    this.mTaskId, 1, null, false);
                    if (anyTaskForId == null) {
                        throw new IllegalArgumentException(
                                "Unable to find task ID " + this.mTaskId);
                    }
                    Intent baseIntent = anyTaskForId.getBaseIntent();
                    if (z) {
                        baseIntent.addFlags(8388608);
                    } else {
                        baseIntent.setFlags(baseIntent.getFlags() & (-8388609));
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (Throwable th2) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final int startActivity(
            IBinder iBinder, String str, String str2, Intent intent, String str3, Bundle bundle) {
        Task anyTaskForId;
        IApplicationThread asInterface;
        checkCallerOrSystemOrRoot();
        this.mService.assertPackageMatchesCallingUid(str);
        int callingUserId = UserHandle.getCallingUserId();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                anyTaskForId =
                        this.mService.mRootWindowContainer.anyTaskForId(
                                this.mTaskId, 1, null, false);
                if (anyTaskForId == null) {
                    throw new IllegalArgumentException("Unable to find task ID " + this.mTaskId);
                }
                asInterface = IApplicationThread.Stub.asInterface(iBinder);
                if (asInterface == null) {
                    throw new IllegalArgumentException("Bad app thread " + asInterface);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        ActivityStarter obtainStarter =
                this.mService.mActivityStartController.obtainStarter(intent, "AppTaskImpl");
        ActivityStarter.Request request = obtainStarter.mRequest;
        request.caller = asInterface;
        request.callingPackage = str;
        request.callingFeatureId = str2;
        request.resolvedType = str3;
        obtainStarter.setActivityOptions(bundle);
        ActivityStarter.Request request2 = obtainStarter.mRequest;
        request2.userId = callingUserId;
        request2.inTask = anyTaskForId;
        return obtainStarter.execute();
    }
}
