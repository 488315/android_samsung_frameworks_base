package com.android.wm.shell.windowdecor;

import android.graphics.Rect;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class AbstractTaskPositionerDecorator implements TaskPositioner {
    public final TaskPositioner taskPositioner;

    public AbstractTaskPositionerDecorator(TaskPositioner taskPositioner) {
        this.taskPositioner = taskPositioner;
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final void addDragEventListener(DesktopTilingWindowDecoration desktopTilingWindowDecoration) {
        this.taskPositioner.addDragEventListener(desktopTilingWindowDecoration);
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final void close() {
        this.taskPositioner.close();
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final boolean isResizingOrAnimating() {
        return this.taskPositioner.isResizingOrAnimating();
    }

    @Override // com.android.wm.shell.windowdecor.DragPositioningCallback
    public Rect onDragPositioningEnd(float f, float f2, int i) {
        return this.taskPositioner.onDragPositioningEnd(f, f2, i);
    }

    @Override // com.android.wm.shell.windowdecor.DragPositioningCallback
    public Rect onDragPositioningMove(float f, float f2, int i) {
        return this.taskPositioner.onDragPositioningMove(f, f2, i);
    }

    @Override // com.android.wm.shell.windowdecor.DragPositioningCallback
    public Rect onDragPositioningStart(int i, float f, float f2, int i2) {
        return this.taskPositioner.onDragPositioningStart(i, f, f2, i2);
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final void removeDragEventListener(DesktopTilingWindowDecoration desktopTilingWindowDecoration) {
        this.taskPositioner.removeDragEventListener(desktopTilingWindowDecoration);
    }
}
