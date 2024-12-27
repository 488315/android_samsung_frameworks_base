package com.android.server.wm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DexController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DexController f$0;

    public /* synthetic */ DexController$$ExternalSyntheticLambda3(DexController dexController, int i) {
        this.$r8$classId = i;
        this.f$0 = dexController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        DexController dexController = this.f$0;
        switch (i) {
            case 0:
                dexController.mAtm.mMultiTaskingController.mSettingsObserver.readSettings(true, null);
                return;
            case 1:
                WindowManagerGlobalLock windowManagerGlobalLock = dexController.mAtm.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        int dexModeLocked = dexController.mAtm.mDexController.getDexModeLocked();
                        DisplayContent displayContent = dexModeLocked == 2 ? dexController.mAtm.mRootWindowContainer.getDisplayContent(2) : dexModeLocked == 1 ? dexController.mAtm.mRootWindowContainer.mDefaultDisplay : null;
                        if (displayContent != null) {
                            displayContent.mDisplayPolicy.mDecorInsets.invalidate();
                            displayContent.reconfigureDisplayLocked();
                        }
                    } finally {
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            default:
                WindowManagerGlobalLock windowManagerGlobalLock2 = dexController.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        int dexModeLocked2 = dexController.getDexModeLocked();
                        int i2 = 2;
                        if (dexModeLocked2 != 2) {
                            i2 = dexModeLocked2 == 1 ? 0 : -1;
                        }
                        DisplayContent displayContent2 = dexController.mAtm.mRootWindowContainer.getDisplayContent(i2);
                        if (displayContent2 != null) {
                            displayContent2.setLayoutNeeded();
                            dexController.mAtm.mWindowManager.mWindowPlacerLocked.performSurfacePlacement(false);
                        }
                    } finally {
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
        }
    }
}
