package com.samsung.android.server.hwrs;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.samsung.android.server.hwrs.common.HwrsUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SemHwrsService extends SystemService {
    public final SemHwrsServiceImpl mSvcImpl;

    public SemHwrsService(Context context) {
        super(context);
        Log.d("[HWRS_SYS]Service", "create SemHwrsService");
        int i = SemHwrsServiceImpl.$r8$clinit;
        Log.d("[HWRS_SYS]SemHwrsService", "createInstance entered");
        this.mSvcImpl = new SemHwrsServiceImpl(context, new PreconditionObserver(context));
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        Log.i("[HWRS_SYS]Service", "onStart entered");
        publishBinderService("SemHwrsService", this.mSvcImpl);
    }

    @Override // com.android.server.SystemService
    public final void onUserStarting(SystemService.TargetUser targetUser) {
        Log.d("[HWRS_SYS]Service", "onUserStarting : " + targetUser.getUserHandle().toString());
    }

    @Override // com.android.server.SystemService
    public final void onUserStopping(SystemService.TargetUser targetUser) {
        Log.d("[HWRS_SYS]Service", "onUserStopping : " + targetUser.getUserHandle().toString());
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("onUserSwitching : ", targetUser != null ? targetUser.getUserHandle().toString() : null, "->");
        m.append(targetUser2.getUserHandle().toString());
        Log.d("[HWRS_SYS]Service", m.toString());
        final UserHandle userHandle = targetUser2.getUserHandle();
        final SemHwrsServiceImpl semHwrsServiceImpl = this.mSvcImpl;
        semHwrsServiceImpl.getClass();
        Runnable runnable = new Runnable() { // from class: com.samsung.android.server.hwrs.AbstractSemHwrsServiceImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AbstractSemHwrsServiceImpl abstractSemHwrsServiceImpl = AbstractSemHwrsServiceImpl.this;
                UserHandle userHandle2 = userHandle;
                if (abstractSemHwrsServiceImpl.mCurrentUserId != -10000) {
                    Log.d("[HWRS_SYS]SemHwrsService", "stopUser entered");
                    abstractSemHwrsServiceImpl.mPrecondManager.stopUser();
                }
                abstractSemHwrsServiceImpl.mCurrentUserId = userHandle2.semGetIdentifier();
                abstractSemHwrsServiceImpl.setCurrentUserHandle(userHandle2);
                abstractSemHwrsServiceImpl.mPrecondManager.startUser(userHandle2);
                Log.d("[HWRS_SYS]SemHwrsService", "startUser entered");
            }
        };
        if (Looper.getMainLooper().equals(Looper.myLooper())) {
            runnable.run();
        } else {
            HwrsUtils.sHandler.post(runnable);
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        Log.d("[HWRS_SYS]Service", "onUserUnlocking : " + targetUser.getUserHandle().toString());
        UserHandle userHandle = targetUser.getUserHandle();
        SemHwrsServiceImpl semHwrsServiceImpl = this.mSvcImpl;
        semHwrsServiceImpl.getClass();
        int semGetIdentifier = userHandle.semGetIdentifier();
        boolean isManagedProfile = semHwrsServiceImpl.mUserManager.isManagedProfile();
        Log.i("[HWRS_SYS]SemHwrsService", "onUserUnlocking - " + semGetIdentifier + ", " + isManagedProfile);
        if (semGetIdentifier != ActivityManager.semGetCurrentUser() || isManagedProfile) {
            return;
        }
        semHwrsServiceImpl.mCurrentUserId = semGetIdentifier;
        semHwrsServiceImpl.mCurrentUserHandle = userHandle;
        semHwrsServiceImpl.mPrecondManager.startUser(userHandle);
    }
}
