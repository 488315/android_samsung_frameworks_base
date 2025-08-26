package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.window.DesktopModeFlags;

/* loaded from: classes3.dex */
public final class HandleMenuImageButton extends ImageButton {
    public ActivityManager.RunningTaskInfo taskInfo;

    public HandleMenuImageButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        if (!DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue()) {
            ActivityManager.RunningTaskInfo runningTaskInfo = this.taskInfo;
            if (runningTaskInfo == null) {
                runningTaskInfo = null;
            }
            if (!runningTaskInfo.isFreeform()) {
                return false;
            }
        }
        return super.onHoverEvent(motionEvent);
    }
}
