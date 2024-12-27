package com.android.server.wm;

import com.samsung.android.rune.CoreRune;

public final class MinimizeContainerServiceBinder extends FreeformContainerServiceBinder {
    public final boolean hasFreeformTask() {
        boolean z;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                z =
                        this.mAtm
                                        .mRootWindowContainer
                                        .mDefaultDisplay
                                        .getDefaultTaskDisplayArea()
                                        .getRootTask(5, 0)
                                != null;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return z;
    }

    @Override // com.android.server.wm.FreeformContainerServiceBinder
    public final boolean okToBind() {
        return CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER && super.okToBind() && hasFreeformTask();
    }

    @Override // com.android.server.wm.FreeformContainerServiceBinder
    public final boolean okToUnbind() {
        if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER) {
            return (okToBind() ^ true) || !hasFreeformTask();
        }
        return false;
    }
}
