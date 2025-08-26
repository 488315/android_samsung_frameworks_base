package com.android.wm.shell.windowdecor.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.widget.ImageButton;
import com.android.systemui.R;
import com.android.wm.shell.windowdecor.TaskFocusStateConsumer;

/* loaded from: classes3.dex */
public class CaptionButton extends ImageButton implements TaskFocusStateConsumer {
    public static final int[] TASK_FOCUSED_STATE = {R.attr.state_task_focused};
    public boolean mIsTaskFocused;

    public CaptionButton(Context context, AttributeSet attributeSet) {
        super(new ContextThemeWrapper(context, android.R.style.Theme.DeviceDefault.DayNight), attributeSet);
        CharSequence contentDescription = getContentDescription();
        if (contentDescription != null) {
            semSetHoverPopupType(0);
            setTooltipText(contentDescription);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public final int[] onCreateDrawableState(int i) {
        if (!this.mIsTaskFocused) {
            return super.onCreateDrawableState(i);
        }
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + 1);
        ImageButton.mergeDrawableStates(iArrOnCreateDrawableState, TASK_FOCUSED_STATE);
        return iArrOnCreateDrawableState;
    }

    @Override // android.view.View
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        setAlpha(z ? 1.0f : 0.4f);
    }

    @Override // com.android.wm.shell.windowdecor.TaskFocusStateConsumer
    public final void setTaskFocusState(boolean z) {
        this.mIsTaskFocused = z;
        refreshDrawableState();
    }
}
