package com.android.wm.shell.splitscreen;

import com.android.wm.shell.recents.RecentTasksController;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda14 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RecentTasksController f$0;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda14(RecentTasksController recentTasksController, int i) {
        this.$r8$classId = i;
        this.f$0 = recentTasksController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.removeSplitPair(((Integer) obj).intValue());
                break;
            default:
                this.f$0.removeSplitPair(((Integer) obj).intValue());
                break;
        }
    }
}
