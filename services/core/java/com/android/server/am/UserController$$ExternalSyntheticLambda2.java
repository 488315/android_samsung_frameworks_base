package com.android.server.am;

import android.content.IIntentReceiver;
import android.content.Intent;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.storage.IStorageManager;
import android.os.storage.StorageManager;

import com.android.server.utils.Slogf;
import com.android.server.utils.TimingsTraceAndSlog;

import java.util.List;

public final /* synthetic */ class UserController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UserController f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ UserController$$ExternalSyntheticLambda2(
            UserController userController, int i, Object obj, int i2) {
        this.$r8$classId = i2;
        this.f$0 = userController;
        this.f$1 = i;
        this.f$2 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                UserController userController = this.f$0;
                int i = this.f$1;
                Runnable runnable = (Runnable) this.f$2;
                userController.getClass();
                Intent intent = new Intent("android.intent.action.USER_STOPPING");
                intent.addFlags(1073741824);
                intent.putExtra("android.intent.extra.user_handle", i);
                intent.putExtra("android.intent.extra.SHUTDOWN_USERSPACE_ONLY", true);
                IIntentReceiver anonymousClass5 =
                        new UserController.AnonymousClass5(
                                i, (UserController$$ExternalSyntheticLambda1) runnable);
                TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
                timingsTraceAndSlog.traceBegin("clearBroadcastQueueForUser-" + i + "-[stopUser]");
                UserController.Injector injector = userController.mInjector;
                ActivityManagerService activityManagerService = injector.mService;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        injector.mService.mBroadcastQueue.cleanupDisabledPackageReceiversLocked(
                                i, null, null);
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                timingsTraceAndSlog.traceEnd();
                UserController.asyncTraceBegin(
                        i, "broadcast-ACTION_USER_STOPPING-" + i + "-[stopUser]");
                userController.mInjector.broadcastIntent(
                        intent,
                        anonymousClass5,
                        new String[] {"android.permission.INTERACT_ACROSS_USERS"},
                        null,
                        ActivityManagerService.MY_PID,
                        Binder.getCallingUid(),
                        Binder.getCallingPid(),
                        -1);
                return;
            case 1:
                UserController userController2 = this.f$0;
                int i2 = this.f$1;
                List list = (List) this.f$2;
                synchronized (userController2.mLock) {
                    try {
                        if (userController2.mStartedUsers.get(i2) != null) {
                            Slogf.w("ActivityManager", "User was restarted, skipping key eviction");
                            return;
                        }
                        try {
                            Slogf.i("ActivityManager", "Locking CE storage for user #" + i2);
                            userController2.mInjector.getClass();
                            IStorageManager.Stub.asInterface(ServiceManager.getService("mount"))
                                    .lockCeStorage(i2);
                            if (list == null) {
                                return;
                            }
                            for (int i3 = 0; i3 < list.size(); i3++) {
                                UserController.AnonymousClass4 anonymousClass4 =
                                        (UserController.AnonymousClass4) list.get(i3);
                                UserController.this.mHandler.post(
                                        new UserController$$ExternalSyntheticLambda6(
                                                i2,
                                                anonymousClass4.val$userStartMode,
                                                1,
                                                anonymousClass4));
                            }
                            return;
                        } catch (RemoteException e) {
                            throw e.rethrowAsRuntimeException();
                        }
                    } finally {
                    }
                }
            default:
                UserController userController3 = this.f$0;
                int i4 = this.f$1;
                UserState userState = (UserState) this.f$2;
                userController3.getClass();
                if (!StorageManager.isCeStorageUnlocked(i4)) {
                    Slogf.w(
                            "ActivityManager",
                            "User's CE storage got locked unexpectedly, leaving user locked.");
                    return;
                }
                UserController.showEventLog(
                        i4,
                        userState.state,
                        1,
                        "finishUserUnlocking",
                        "Start getUserManager().onBeforeUnlockUser");
                TimingsTraceAndSlog timingsTraceAndSlog2 = new TimingsTraceAndSlog();
                timingsTraceAndSlog2.traceBegin("UM.onBeforeUnlockUser-" + i4);
                userController3.mInjector.getUserManager().onBeforeUnlockUser(i4);
                timingsTraceAndSlog2.traceEnd();
                UserController.showEventLog(
                        i4,
                        userState.state,
                        1,
                        "finishUserUnlocking",
                        "End getUserManager().onBeforeUnlockUser");
                synchronized (userController3.mLock) {
                    try {
                        if (userState.setState(1, 2)) {
                            userController3
                                    .mInjector
                                    .getUserManagerInternal()
                                    .setUserState(i4, userState.state);
                            userState.mUnlockProgress.setProgress(20);
                            userController3.mLastUserUnlockingUptime = SystemClock.uptimeMillis();
                            userController3
                                    .mHandler
                                    .obtainMessage(100, i4, 0, userState)
                                    .sendToTarget();
                            UserController.showEventLog(
                                    i4,
                                    userState.state,
                                    1,
                                    "finishUserUnlocking",
                                    "sendToTarget USER_UNLOCK_MSG");
                        }
                    } finally {
                    }
                }
                return;
        }
    }
}
