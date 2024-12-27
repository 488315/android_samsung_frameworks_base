package com.samsung.android.server.continuity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.SystemService;

import com.samsung.android.mcfds.lib.DeviceSyncManager;
import com.samsung.android.server.continuity.common.ExecutorUtil;
import com.samsung.android.server.continuity.sem.SemWrapper;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SemContinuityService extends SystemService {
    public final SemContinuityServiceImpl mSvcImpl;

    public SemContinuityService(Context context) {
        super(context);
        int i = SemContinuityServiceImpl.$r8$clinit;
        this.mSvcImpl =
                new SemContinuityServiceImpl(
                        context,
                        new McfDeviceSyncManager(
                                new PreconditionObserver(context), new DeviceSyncManager(context)));
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {}

    @Override // com.android.server.SystemService
    public final void onStart() {
        Log.i("[MCF_DS_SYS]_Service", "onStart");
        publishBinderService("SemContinuityService", this.mSvcImpl);
    }

    @Override // com.android.server.SystemService
    public final void onUserStarting(SystemService.TargetUser targetUser) {
        Log.d("[MCF_DS_SYS]_Service", "onUserStarting : " + targetUser.getUserHandle().toString());
    }

    @Override // com.android.server.SystemService
    public final void onUserStopping(SystemService.TargetUser targetUser) {
        Log.d("[MCF_DS_SYS]_Service", "onUserStopping : " + targetUser.getUserHandle().toString());
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(
            SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        StringBuilder m =
                DumpUtils$$ExternalSyntheticOutline0.m(
                        "onUserSwitching : ",
                        targetUser != null ? targetUser.getUserHandle().toString() : null,
                        "->");
        m.append(targetUser2.getUserHandle().toString());
        Log.d("[MCF_DS_SYS]_Service", m.toString());
        final UserHandle userHandle = targetUser2.getUserHandle();
        final SemContinuityServiceImpl semContinuityServiceImpl = this.mSvcImpl;
        semContinuityServiceImpl.getClass();
        Runnable runnable =
                new Runnable() { // from class:
                                 // com.samsung.android.server.continuity.AbstractSemContinuityServiceImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AbstractSemContinuityServiceImpl abstractSemContinuityServiceImpl =
                                AbstractSemContinuityServiceImpl.this;
                        UserHandle userHandle2 = userHandle;
                        if (abstractSemContinuityServiceImpl.mCurrentUserId != -10000) {
                            abstractSemContinuityServiceImpl.mMcfDsManager.stopUser();
                        }
                        UserHandle userHandle3 = SemWrapper.SEM_ALL;
                        abstractSemContinuityServiceImpl.mCurrentUserId =
                                userHandle2.semGetIdentifier();
                        abstractSemContinuityServiceImpl.setCurrentUserHandle(userHandle2);
                        abstractSemContinuityServiceImpl.mMcfDsManager.startUser(userHandle2);
                    }
                };
        if (Looper.getMainLooper().equals(Looper.myLooper())) {
            runnable.run();
        } else {
            ExecutorUtil.sHandler.post(runnable);
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        Log.d("[MCF_DS_SYS]_Service", "onUserUnlocking : " + targetUser.getUserHandle().toString());
        UserHandle userHandle = targetUser.getUserHandle();
        SemContinuityServiceImpl semContinuityServiceImpl = this.mSvcImpl;
        semContinuityServiceImpl.getClass();
        UserHandle userHandle2 = SemWrapper.SEM_ALL;
        int semGetIdentifier = userHandle.semGetIdentifier();
        boolean isManagedProfile = semContinuityServiceImpl.mUserManager.isManagedProfile();
        Log.i(
                "[MCF_DS_SYS]_SemContinuityServiceImpl",
                "onUserUnlocking - " + semGetIdentifier + ", " + isManagedProfile);
        if (semGetIdentifier != ActivityManager.semGetCurrentUser() || isManagedProfile) {
            return;
        }
        semContinuityServiceImpl.mCurrentUserId = semGetIdentifier;
        semContinuityServiceImpl.mCurrentUserHandle = userHandle;
        semContinuityServiceImpl.mMcfDsManager.startUser(userHandle);
    }
}
