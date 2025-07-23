package com.android.wm.shell.windowdecor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class WindowDecorLinearLayout extends LinearLayout implements TaskFocusStateConsumer {
    public static final int[] TASK_FOCUSED_STATE = {R.attr.state_task_focused};
    public boolean mIsTaskFocused;

    public WindowDecorLinearLayout(Context context) {
        super(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i) {
        if (!this.mIsTaskFocused) {
            return super.onCreateDrawableState(i);
        }
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        LinearLayout.mergeDrawableStates(onCreateDrawableState, TASK_FOCUSED_STATE);
        return onCreateDrawableState;
    }

    @Override // com.android.wm.shell.windowdecor.TaskFocusStateConsumer
    public final void setTaskFocusState(boolean z) {
        this.mIsTaskFocused = z;
        refreshDrawableState();
        if (CoreRune.MW_CAPTION_HANDLE) {
            KeyEvent.Callback findViewById = findViewById(R.id.caption_handle);
            if (findViewById instanceof TaskFocusStateConsumer) {
                ((TaskFocusStateConsumer) findViewById).setTaskFocusState(z);
            }
        }
    }

    public WindowDecorLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public WindowDecorLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public WindowDecorLinearLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
