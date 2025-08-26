package com.android.wm.shell.recents;

import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.recents.RecentTasksController;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class RecentTasksController$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RecentTasksController$$ExternalSyntheticLambda7(Object obj, int i) {
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
            case 1:
                ((RecentTasksController) obj).registerRecentTasksListener(((RecentTasksController.IRecentTasksImpl) obj2).mRecentTasksListener);
                break;
            default:
                ((RecentTasksController.IRecentTasksImpl) obj2).mListener.unregister();
                break;
        }
    }
}
