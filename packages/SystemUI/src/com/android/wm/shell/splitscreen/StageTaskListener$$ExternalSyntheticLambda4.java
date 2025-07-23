package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StageTaskListener$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityManager.RunningTaskInfo f$0;

    public /* synthetic */ StageTaskListener$$ExternalSyntheticLambda4(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        this.$r8$classId = i;
        this.f$0 = runningTaskInfo;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.f$0;
        WindowDecorViewModel windowDecorViewModel = (WindowDecorViewModel) obj;
        switch (i) {
            case 0:
                windowDecorViewModel.onTaskInfoChanged(runningTaskInfo);
                break;
            default:
                windowDecorViewModel.onTaskVanished(runningTaskInfo);
                break;
        }
    }
}
