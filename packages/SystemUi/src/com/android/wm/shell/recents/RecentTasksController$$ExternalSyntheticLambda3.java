package com.android.wm.shell.recents;

import com.android.wm.shell.desktopmode.DesktopModeTaskRepository;
import com.android.wm.shell.recents.RecentTasksController;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class RecentTasksController$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RecentTasksController$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                RecentTasksController recentTasksController = (RecentTasksController) this.f$0;
                recentTasksController.getClass();
                ((DesktopModeTaskRepository) obj).activeTasksListeners.add(recentTasksController);
                break;
            case 1:
                ((RecentTasksController) obj).registerRecentTasksListener(((RecentTasksController.IRecentTasksImpl) this.f$0).mRecentTasksListener);
                break;
            default:
                ((RecentTasksController.IRecentTasksImpl) this.f$0).mListener.unregister();
                break;
        }
    }
}
