package com.android.wm.shell.recents;

import com.android.wm.shell.recents.RecentTasksController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RecentTasksController.IRecentTasksImpl f$0;

    public /* synthetic */ RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda3(RecentTasksController.IRecentTasksImpl iRecentTasksImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = iRecentTasksImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        RecentTasksController.IRecentTasksImpl iRecentTasksImpl = this.f$0;
        RecentTasksController recentTasksController = (RecentTasksController) obj;
        switch (i) {
            case 0:
                recentTasksController.registerRecentTasksListener(iRecentTasksImpl.mRecentTasksListener);
                break;
            default:
                iRecentTasksImpl.mListener.unregister();
                break;
        }
    }
}
