package com.samsung.android.server.hwrs;

import android.content.Context;
import android.util.Log;
import com.android.server.SystemService;

/* loaded from: classes2.dex */
public class SemHwrsService extends SystemService {
    public final SemHwrsServiceImpl mSvcImpl;

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
    }

    public SemHwrsService(Context context) {
        super(context);
        Log.d("[HWRS_SYS]Service", "create SemHwrsService");
        this.mSvcImpl = SemHwrsServiceImpl.createInstance(context);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        Log.i("[HWRS_SYS]Service", "onStart entered");
        publishBinderService("SemHwrsService", this.mSvcImpl);
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        Log.d("[HWRS_SYS]Service", "onUserSwitching : " + (targetUser != null ? targetUser.getUserHandle().toString() : null) + "->" + targetUser2.getUserHandle().toString());
        this.mSvcImpl.onUserSwitching(targetUser2.getUserHandle());
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(SystemService.TargetUser targetUser) {
        Log.d("[HWRS_SYS]Service", "onUserUnlocking : " + targetUser.getUserHandle().toString());
        this.mSvcImpl.onUserUnlocking(targetUser.getUserHandle());
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(SystemService.TargetUser targetUser) {
        Log.d("[HWRS_SYS]Service", "onUserStarting : " + targetUser.getUserHandle().toString());
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(SystemService.TargetUser targetUser) {
        Log.d("[HWRS_SYS]Service", "onUserStopping : " + targetUser.getUserHandle().toString());
    }
}
