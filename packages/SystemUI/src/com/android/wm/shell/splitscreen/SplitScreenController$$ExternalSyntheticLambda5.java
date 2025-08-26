package com.android.wm.shell.splitscreen;

import android.window.WindowContainerToken;
import com.android.wm.shell.recents.RecentTasksController;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenController$$ExternalSyntheticLambda5 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WindowContainerToken f$0;

    public /* synthetic */ SplitScreenController$$ExternalSyntheticLambda5(int i, WindowContainerToken windowContainerToken) {
        this.$r8$classId = i;
        this.f$0 = windowContainerToken;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        WindowContainerToken windowContainerToken = this.f$0;
        RecentTasksController recentTasksController = (RecentTasksController) obj;
        switch (i) {
        }
        return recentTasksController.getTopRunningTask(windowContainerToken);
    }
}
