package com.android.wm.shell.compatui;

import android.app.TaskInfo;
import com.android.wm.shell.ShellTaskOrganizer;
import java.util.function.BiConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class CompatUIController$$ExternalSyntheticLambda0 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CompatUIController f$0;

    public /* synthetic */ CompatUIController$$ExternalSyntheticLambda0(CompatUIController compatUIController, int i) {
        this.$r8$classId = i;
        this.f$0 = compatUIController;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        int i = this.$r8$classId;
        CompatUIController compatUIController = this.f$0;
        TaskInfo taskInfo = (TaskInfo) obj;
        ShellTaskOrganizer.TaskListener taskListener = (ShellTaskOrganizer.TaskListener) obj2;
        switch (i) {
            case 0:
                CompatUIController.launchUserAspectRatioSettings(compatUIController.mContext, taskInfo);
                break;
            default:
                compatUIController.mIsFirstReachabilityEducationRunning = false;
                compatUIController.createOrUpdateUserAspectRatioSettingsLayout(taskInfo, taskListener);
                break;
        }
    }
}
