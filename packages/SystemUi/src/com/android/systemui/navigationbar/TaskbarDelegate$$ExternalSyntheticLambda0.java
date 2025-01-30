package com.android.systemui.navigationbar;

import android.graphics.Rect;
import com.android.wm.shell.pip.Pip;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class TaskbarDelegate$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TaskbarDelegate f$0;

    public /* synthetic */ TaskbarDelegate$$ExternalSyntheticLambda0(TaskbarDelegate taskbarDelegate, int i) {
        this.$r8$classId = i;
        this.f$0 = taskbarDelegate;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((Pip) obj).addPipExclusionBoundsChangeListener(this.f$0.mPipListener);
                break;
            case 1:
                ((Pip) obj).removePipExclusionBoundsChangeListener(this.f$0.mPipListener);
                break;
            default:
                this.f$0.mEdgeBackGestureHandler.mPipExcludedBounds.set((Rect) obj);
                break;
        }
    }
}
