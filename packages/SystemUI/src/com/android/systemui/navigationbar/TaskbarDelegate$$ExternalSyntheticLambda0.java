package com.android.systemui.navigationbar;

import android.graphics.Rect;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.pip.phone.PipController;
import com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda0;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class TaskbarDelegate$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TaskbarDelegate f$0;

    public /* synthetic */ TaskbarDelegate$$ExternalSyntheticLambda0(TaskbarDelegate taskbarDelegate, int i) {
        this.$r8$classId = i;
        this.f$0 = taskbarDelegate;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        TaskbarDelegate taskbarDelegate = this.f$0;
        switch (i) {
            case 0:
                taskbarDelegate.mEdgeBackGestureHandler.mPipExcludedBounds.set((Rect) obj);
                break;
            case 1:
                TaskbarDelegate$$ExternalSyntheticLambda0 taskbarDelegate$$ExternalSyntheticLambda0 = taskbarDelegate.mPipListener;
                PipController.PipImpl pipImpl = (PipController.PipImpl) ((Pip) obj);
                ((HandlerExecutor) PipController.this.mMainExecutor).execute(new PipController$$ExternalSyntheticLambda0(1, pipImpl, taskbarDelegate$$ExternalSyntheticLambda0));
                break;
            default:
                TaskbarDelegate$$ExternalSyntheticLambda0 taskbarDelegate$$ExternalSyntheticLambda02 = taskbarDelegate.mPipListener;
                PipController.PipImpl pipImpl2 = (PipController.PipImpl) ((Pip) obj);
                ((HandlerExecutor) PipController.this.mMainExecutor).execute(new PipController$$ExternalSyntheticLambda0(2, pipImpl2, taskbarDelegate$$ExternalSyntheticLambda02));
                break;
        }
    }
}
