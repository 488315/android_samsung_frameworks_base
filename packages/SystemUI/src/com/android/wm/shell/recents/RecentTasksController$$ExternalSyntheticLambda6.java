package com.android.wm.shell.recents;

import android.app.ActivityManager;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class RecentTasksController$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RecentTasksController$$ExternalSyntheticLambda6(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((DesktopUserRepositories) obj).getCurrent().activeTasksListeners.add((RecentTasksController) obj2);
                break;
            default:
                ((DesktopUserRepositories) obj).getCurrent().removeTask(-1, ((ActivityManager.RunningTaskInfo) obj2).taskId);
                break;
        }
    }
}
