package com.android.wm.shell.windowdecor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.samsung.android.rune.CoreRune;

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
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + 1);
        LinearLayout.mergeDrawableStates(iArrOnCreateDrawableState, TASK_FOCUSED_STATE);
        return iArrOnCreateDrawableState;
    }

    @Override // com.android.wm.shell.windowdecor.TaskFocusStateConsumer
    public final void setTaskFocusState(boolean z) {
        this.mIsTaskFocused = z;
        refreshDrawableState();
        if (CoreRune.MW_CAPTION_HANDLE) {
            KeyEvent.Callback callbackFindViewById = findViewById(R.id.caption_handle);
            if (callbackFindViewById instanceof TaskFocusStateConsumer) {
                ((TaskFocusStateConsumer) callbackFindViewById).setTaskFocusState(z);
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
