package com.android.systemui.navigationbar;

import android.graphics.Rect;
import com.android.wm.shell.pip.Pip;
import java.util.function.Consumer;

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
                ((Pip) obj).removePipExclusionBoundsChangeListener(taskbarDelegate.mPipListener);
                break;
            default:
                ((Pip) obj).addPipExclusionBoundsChangeListener(taskbarDelegate.mPipListener);
                break;
        }
    }
}
