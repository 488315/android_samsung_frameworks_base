package android.view;

import android.graphics.Rect;
import android.view.WindowManager;
import android.window.ClientWindowFrames;

/* loaded from: classes4.dex */
public class WindowlessWindowLayout extends WindowLayout {
    private static int calculateLength(int i, int i2, int i3) {
        return i == -1 ? i3 : i == -2 ? i2 : i;
    }

    @Override // android.view.WindowLayout
    public void computeFrames(WindowManager.LayoutParams layoutParams, InsetsState insetsState, Rect rect, Rect rect2, int i, int i2, int i3, int i4, float f, ClientWindowFrames clientWindowFrames) {
        computeFrames(layoutParams, insetsState, rect, rect2, i, i2, i3, i4, f, clientWindowFrames, 0, null, false);
    }

    @Override // android.view.WindowLayout
    public void computeFrames(WindowManager.LayoutParams layoutParams, InsetsState insetsState, Rect rect, Rect rect2, int i, int i2, int i3, int i4, float f, ClientWindowFrames clientWindowFrames, int i5, Rect rect3, boolean z) {
        if (clientWindowFrames.attachedFrame == null) {
            clientWindowFrames.frame.set(0, 0, layoutParams.width, layoutParams.height);
            clientWindowFrames.parentFrame.set(clientWindowFrames.frame);
            clientWindowFrames.displayFrame.set(clientWindowFrames.frame);
        } else {
            Gravity.apply(layoutParams.gravity, calculateLength(layoutParams.width, i2, clientWindowFrames.attachedFrame.width()), calculateLength(layoutParams.height, i3, clientWindowFrames.attachedFrame.height()), clientWindowFrames.attachedFrame, (int) (layoutParams.x + layoutParams.horizontalMargin), (int) (layoutParams.y + layoutParams.verticalMargin), clientWindowFrames.frame);
            clientWindowFrames.displayFrame.set(clientWindowFrames.frame);
            clientWindowFrames.parentFrame.set(clientWindowFrames.attachedFrame);
        }
    }
}
