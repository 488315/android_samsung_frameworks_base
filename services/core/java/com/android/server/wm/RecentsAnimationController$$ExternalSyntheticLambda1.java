package com.android.server.wm;

import java.util.ArrayList;
import java.util.function.Consumer;

public final /* synthetic */ class RecentsAnimationController$$ExternalSyntheticLambda1
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RecentsAnimationController$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ArrayList arrayList = (ArrayList) obj2;
                Task task = (Task) obj;
                if (arrayList.contains(task)) {
                    return;
                }
                arrayList.add(task);
                return;
            default:
                RecentsAnimationController recentsAnimationController =
                        (RecentsAnimationController) obj2;
                WallpaperAnimationAdapter wallpaperAnimationAdapter =
                        (WallpaperAnimationAdapter) obj;
                WindowManagerGlobalLock windowManagerGlobalLock =
                        recentsAnimationController.mService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        recentsAnimationController.mPendingWallpaperAnimations.remove(
                                wallpaperAnimationAdapter);
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
        }
    }
}
