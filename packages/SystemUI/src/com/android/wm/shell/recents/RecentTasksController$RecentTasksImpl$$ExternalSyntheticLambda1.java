package com.android.wm.shell.recents;

import android.graphics.Color;
import com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider$getTasks$2$1;
import com.android.wm.shell.recents.RecentTasksController;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class RecentTasksController$RecentTasksImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ RecentTasksController$RecentTasksImpl$$ExternalSyntheticLambda1(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((ShellRecentTaskListProvider$getTasks$2$1) this.f$0).accept((List) this.f$1);
                break;
            default:
                RecentTasksController.RecentTasksImpl recentTasksImpl = (RecentTasksController.RecentTasksImpl) this.f$0;
                Color color = (Color) this.f$1;
                RecentsTransitionHandler recentsTransitionHandler = RecentTasksController.this.mTransitionHandler;
                if (recentsTransitionHandler != null) {
                    recentsTransitionHandler.mBackgroundColor = color;
                    break;
                }
                break;
        }
    }
}
