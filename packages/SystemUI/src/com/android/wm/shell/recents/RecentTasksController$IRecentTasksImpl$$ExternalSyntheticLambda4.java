package com.android.wm.shell.recents;

import com.android.wm.shell.recents.RecentTasksController;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda4 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = RecentTasksController.IRecentTasksImpl.$r8$clinit;
        ((RecentTasksController) obj).unregisterRecentTasksListener();
    }
}
