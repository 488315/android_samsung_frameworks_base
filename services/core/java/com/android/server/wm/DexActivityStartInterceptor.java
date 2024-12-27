package com.android.server.wm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DexActivityStartInterceptor {
    public final ActivityTaskManagerService mAtmService;
    public final DexController mDexController;

    public DexActivityStartInterceptor(
            ActivityTaskManagerService activityTaskManagerService, DexController dexController) {
        this.mDexController = dexController;
        this.mAtmService = activityTaskManagerService;
    }
}
