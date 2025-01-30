package com.android.server.sepunion;

import android.content.Context;
import com.android.server.SystemService;
import com.samsung.android.sepunion.Log;
import com.samsung.android.sepunion.SemUnionManagerLocal;

/* loaded from: classes3.dex */
public class SemUnionMainService extends SystemService {
    public static final String TAG = "SemUnionMainService";
    public final SemUnionMainServiceImpl mImpl;

    public SemUnionMainService(Context context) {
        super(context);
        this.mImpl = new SemUnionMainServiceImpl(context);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        Log.d(TAG, "onStart");
        publishBinderService("sepunion", this.mImpl);
        publishLocalService(SemUnionManagerLocal.class, this.mImpl.mSemUnionManagerLocal);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        Log.d(TAG, "onBootPhase = " + i);
        this.mImpl.onBootPhase(i);
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(SystemService.TargetUser targetUser) {
        this.mImpl.onUserStarting(targetUser.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(SystemService.TargetUser targetUser) {
        this.mImpl.onUserUnlocking(targetUser.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocked(SystemService.TargetUser targetUser) {
        this.mImpl.onUserUnlocked(targetUser.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        this.mImpl.onUserSwitching(targetUser.getUserIdentifier(), targetUser2.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(SystemService.TargetUser targetUser) {
        this.mImpl.onUserStopping(targetUser.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public void onUserStopped(SystemService.TargetUser targetUser) {
        this.mImpl.onUserStopped(targetUser.getUserIdentifier());
    }
}
