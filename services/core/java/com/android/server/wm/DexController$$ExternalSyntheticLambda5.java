package com.android.server.wm;

import android.content.Context;
import android.widget.Toast;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DexController$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DexController$$ExternalSyntheticLambda5(Context context, String str) {
        this.f$0 = context;
        this.f$1 = str;
    }

    public /* synthetic */ DexController$$ExternalSyntheticLambda5(
            DexController dexController, Task task) {
        this.f$0 = dexController;
        this.f$1 = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                Toast.makeText((Context) this.f$0, (String) this.f$1, 0).show();
                return;
            default:
                DexController dexController = (DexController) this.f$0;
                Task task = (Task) this.f$1;
                WindowManagerGlobalLock windowManagerGlobalLock = dexController.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        dexController.mAtm.mTaskSupervisor.removeTask(
                                task, false, true, "moveTasksToFreeformLocked");
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
