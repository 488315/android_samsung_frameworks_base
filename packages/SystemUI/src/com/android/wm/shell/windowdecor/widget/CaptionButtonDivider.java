package com.android.wm.shell.windowdecor.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.R;
import com.android.wm.shell.windowdecor.TaskFocusStateConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CaptionButtonDivider extends View implements TaskFocusStateConsumer {
    public static final int[] TASK_FOCUSED_STATE = {R.attr.state_task_focused};
    public boolean mIsTaskFocused;

    public CaptionButtonDivider(Context context, AttributeSet attributeSet) {
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

    @Override // com.android.wm.shell.windowdecor.TaskFocusStateConsumer
    public final void setTaskFocusState(boolean z) {
        this.mIsTaskFocused = z;
        refreshDrawableState();
    }
}
