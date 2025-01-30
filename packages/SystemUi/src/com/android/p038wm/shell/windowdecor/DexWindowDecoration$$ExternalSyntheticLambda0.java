package com.android.p038wm.shell.windowdecor;

import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.windowdecor.WindowDecoration;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class DexWindowDecoration$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DexWindowDecoration f$0;

    public /* synthetic */ DexWindowDecoration$$ExternalSyntheticLambda0(DexWindowDecoration dexWindowDecoration, int i) {
        this.$r8$classId = i;
        this.f$0 = dexWindowDecoration;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DexWindowDecoration dexWindowDecoration = this.f$0;
                WindowDecoration.AdditionalWindow additionalWindow = dexWindowDecoration.mRestart;
                if (additionalWindow != null) {
                    additionalWindow.releaseView();
                    ShellTaskOrganizer shellTaskOrganizer = dexWindowDecoration.mTaskOrganizer;
                    int i = dexWindowDecoration.mTaskInfo.taskId;
                    synchronized (shellTaskOrganizer.mLock) {
                        shellTaskOrganizer.mDisplayChangingTasks.remove(i);
                    }
                    dexWindowDecoration.mIsShowingRestart = false;
                    return;
                }
                return;
            default:
                DexWindowDecoration dexWindowDecoration2 = this.f$0;
                dexWindowDecoration2.mLayoutParam.width = dexWindowDecoration2.mTaskInfo.getConfiguration().windowConfiguration.getBounds().width();
                dexWindowDecoration2.mLayoutParam.height = dexWindowDecoration2.mTaskInfo.getConfiguration().windowConfiguration.getBounds().height();
                dexWindowDecoration2.mRestart.mWindowViewHost.relayout(dexWindowDecoration2.mLayoutParam);
                return;
        }
    }
}
