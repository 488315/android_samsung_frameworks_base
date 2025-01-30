package com.android.p038wm.shell.windowdecor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
class WindowMenuDivider extends View implements TaskFocusStateConsumer {
    public static final int[] TASK_FOCUSED_STATE = {R.attr.state_task_focused};
    public boolean mIsTaskFocused;

    public WindowMenuDivider(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public final int[] onCreateDrawableState(int i) {
        if (!this.mIsTaskFocused) {
            return super.onCreateDrawableState(i);
        }
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        View.mergeDrawableStates(onCreateDrawableState, TASK_FOCUSED_STATE);
        return onCreateDrawableState;
    }

    @Override // com.android.p038wm.shell.windowdecor.TaskFocusStateConsumer
    public final void setTaskFocusState(boolean z) {
        this.mIsTaskFocused = z;
        refreshDrawableState();
    }
}
