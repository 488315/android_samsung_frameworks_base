package com.android.server.wm;

public final class DexActivityStartInterceptor {
    public final ActivityTaskManagerService mAtmService;
    public final DexController mDexController;

    public DexActivityStartInterceptor(
            ActivityTaskManagerService activityTaskManagerService, DexController dexController) {
        this.mDexController = dexController;
        this.mAtmService = activityTaskManagerService;
    }
}
