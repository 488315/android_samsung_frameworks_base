package com.android.wm.shell.splitscreen;

import com.android.wm.shell.recents.RecentTasksController;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda11 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((RecentTasksController) obj).clearAllSplitTaskIdsInfo();
    }
}
