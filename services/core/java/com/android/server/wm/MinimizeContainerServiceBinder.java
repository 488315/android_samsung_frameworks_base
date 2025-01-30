package com.android.server.wm;

import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class MinimizeContainerServiceBinder extends FreeformContainerServiceBinder {
    public MinimizeContainerServiceBinder(ActivityTaskManagerService activityTaskManagerService) {
        super(activityTaskManagerService);
        setServiceComponent("com.android.systemui", "com.android.wm.shell.freeform.MinimizeContainerService");
    }

    @Override // com.android.server.wm.FreeformContainerServiceBinder
    public boolean okToBind() {
        return CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER && super.okToBind() && hasFreeformTask();
    }

    @Override // com.android.server.wm.FreeformContainerServiceBinder
    public boolean okToUnbind() {
        if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER) {
            return super.okToUnbind() || !hasFreeformTask();
        }
        return false;
    }

    public final boolean hasFreeformTask() {
        boolean z;
        WindowManagerGlobalLock globalLock = this.mAtm.getGlobalLock();
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (globalLock) {
            try {
                z = this.mAtm.mRootWindowContainer.getDefaultTaskDisplayArea().getTopRootTaskInWindowingMode(5) != null;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return z;
    }
}
