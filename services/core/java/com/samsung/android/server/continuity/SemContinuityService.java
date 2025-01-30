package com.samsung.android.server.continuity;

import android.content.Context;
import android.util.Log;
import com.android.server.SystemService;

/* loaded from: classes2.dex */
public class SemContinuityService extends SystemService {
    public final SemContinuityServiceImpl mSvcImpl;

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
    }

    public SemContinuityService(Context context) {
        super(context);
        this.mSvcImpl = SemContinuityServiceImpl.createInstance(context);
    }

    public static boolean isSupported() {
        return AbstractPreconditionObserver.isSupported();
    }

    public static boolean isSupportedCopyPaste() {
        return AbstractPreconditionObserver.isSupported(8);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        Log.i("[MCF_DS_SYS]_Service", "onStart");
        publishBinderService("SemContinuityService", this.mSvcImpl);
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        Log.d("[MCF_DS_SYS]_Service", "onUserSwitching : " + (targetUser != null ? targetUser.getUserHandle().toString() : null) + "->" + targetUser2.getUserHandle().toString());
        this.mSvcImpl.onUserSwitching(targetUser2.getUserHandle());
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(SystemService.TargetUser targetUser) {
        Log.d("[MCF_DS_SYS]_Service", "onUserUnlocking : " + targetUser.getUserHandle().toString());
        this.mSvcImpl.onUserUnlocking(targetUser.getUserHandle());
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(SystemService.TargetUser targetUser) {
        Log.d("[MCF_DS_SYS]_Service", "onUserStarting : " + targetUser.getUserHandle().toString());
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(SystemService.TargetUser targetUser) {
        Log.d("[MCF_DS_SYS]_Service", "onUserStopping : " + targetUser.getUserHandle().toString());
    }
}
