package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.graphics.PointF;
import android.graphics.Rect;
import com.android.wm.shell.desktopmode.DesktopModeUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FixedAspectRatioTaskPositionerDecorator extends AbstractTaskPositionerDecorator {
    public int edgeResizeCtrlType;
    public boolean isTaskPortrait;
    public final Rect lastRepositionedBounds;
    public final PointF lastValidPoint;
    public int originalCtrlType;
    public float startingAspectRatio;
    public final PointF startingPoint;
    public final DesktopModeWindowDecoration windowDecoration;

    public FixedAspectRatioTaskPositionerDecorator(DesktopModeWindowDecoration desktopModeWindowDecoration, TaskPositioner taskPositioner) {
        super(taskPositioner);
        this.windowDecoration = desktopModeWindowDecoration;
        this.lastRepositionedBounds = new Rect();
        this.startingPoint = new PointF();
        this.lastValidPoint = new PointF();
    }

    public final Rect dragAdjustedEnd(float f, float f2, int i) {
        return Math.abs(f2 - this.lastValidPoint.y) < Math.abs(f - this.lastValidPoint.x) ? super.onDragPositioningEnd(getScaledChangeForX(f2), f2, i) : super.onDragPositioningEnd(f, getScaledChangeForY(f), i);
    }

    public final Rect dragAdjustedMove(float f, float f2, int i) {
        if (Math.abs(f2 - this.lastValidPoint.y) < Math.abs(f - this.lastValidPoint.x)) {
            this.lastValidPoint.set(getScaledChangeForX(f2), f2);
            return super.onDragPositioningMove(getScaledChangeForX(f2), f2, i);
        }
        this.lastValidPoint.set(f, getScaledChangeForY(f));
        return super.onDragPositioningMove(f, getScaledChangeForY(f), i);
    }

    public final Rect getBounds(ActivityManager.RunningTaskInfo runningTaskInfo) {
        return runningTaskInfo.configuration.windowConfiguration.getBounds();
    }

    public final float getScaledChangeForX(float f) {
        int i;
        PointF pointF = this.startingPoint;
        float f2 = f - pointF.y;
        float f3 = this.isTaskPortrait ? f2 / this.startingAspectRatio : f2 * this.startingAspectRatio;
        int i2 = this.originalCtrlType;
        return (i2 == 10 || i2 == 5 || (i = this.edgeResizeCtrlType) == 10 || i == 5) ? pointF.x + f3 : pointF.x - f3;
    }

    public final float getScaledChangeForY(float f) {
        int i;
        PointF pointF = this.startingPoint;
        float f2 = f - pointF.x;
        float f3 = this.isTaskPortrait ? f2 * this.startingAspectRatio : f2 / this.startingAspectRatio;
        int i2 = this.originalCtrlType;
        return (i2 == 10 || i2 == 5 || (i = this.edgeResizeCtrlType) == 10 || i == 5) ? pointF.y + f3 : pointF.y - f3;
    }

    @Override // com.android.wm.shell.windowdecor.AbstractTaskPositionerDecorator, com.android.wm.shell.windowdecor.DragPositioningCallback
    public final Rect onDragPositioningEnd(float f, float f2, int i) {
        if (!requiresFixedAspectRatio()) {
            return super.onDragPositioningEnd(f, f2, i);
        }
        PointF pointF = this.lastValidPoint;
        float f3 = pointF.x;
        float f4 = f - f3;
        float f5 = pointF.y;
        float f6 = f2 - f5;
        switch (this.originalCtrlType) {
            case 5:
            case 10:
                if ((f4 > 0.0f && f6 > 0.0f) || (f4 < 0.0f && f6 < 0.0f)) {
                    break;
                } else {
                    break;
                }
                break;
            case 6:
            case 9:
                if ((f4 > 0.0f && f6 < 0.0f) || (f4 < 0.0f && f6 > 0.0f)) {
                    break;
                } else {
                    break;
                }
                break;
        }
        return super.onDragPositioningEnd(f, f2, i);
    }

    @Override // com.android.wm.shell.windowdecor.AbstractTaskPositionerDecorator, com.android.wm.shell.windowdecor.DragPositioningCallback
    public final Rect onDragPositioningMove(float f, float f2, int i) {
        if (!requiresFixedAspectRatio()) {
            return super.onDragPositioningMove(f, f2, i);
        }
        PointF pointF = this.lastValidPoint;
        float f3 = f - pointF.x;
        float f4 = f2 - pointF.y;
        switch (this.originalCtrlType) {
            case 1:
            case 2:
                float scaledChangeForY = getScaledChangeForY(f);
                this.lastValidPoint.set(f, scaledChangeForY);
                this.lastRepositionedBounds.set(super.onDragPositioningMove(f, scaledChangeForY, i));
                break;
            case 4:
            case 8:
                float scaledChangeForX = getScaledChangeForX(f2);
                this.lastValidPoint.set(scaledChangeForX, f2);
                this.lastRepositionedBounds.set(super.onDragPositioningMove(scaledChangeForX, f2, i));
                break;
            case 5:
            case 10:
                if ((f3 > 0.0f && f4 > 0.0f) || (f3 < 0.0f && f4 < 0.0f)) {
                    this.lastRepositionedBounds.set(dragAdjustedMove(f, f2, i));
                    break;
                }
                break;
            case 6:
            case 9:
                if ((f3 > 0.0f && f4 < 0.0f) || (f3 < 0.0f && f4 > 0.0f)) {
                    this.lastRepositionedBounds.set(dragAdjustedMove(f, f2, i));
                    break;
                }
                break;
        }
        return this.lastRepositionedBounds;
    }

    @Override // com.android.wm.shell.windowdecor.AbstractTaskPositionerDecorator, com.android.wm.shell.windowdecor.DragPositioningCallback
    public final Rect onDragPositioningStart(int i, float f, float f2, int i2) {
        Rect onDragPositioningStart;
        this.originalCtrlType = i;
        boolean requiresFixedAspectRatio = requiresFixedAspectRatio();
        TaskPositioner taskPositioner = this.taskPositioner;
        if (!requiresFixedAspectRatio) {
            return taskPositioner.onDragPositioningStart(this.originalCtrlType, f, f2, i2);
        }
        Rect rect = this.lastRepositionedBounds;
        DesktopModeWindowDecoration desktopModeWindowDecoration = this.windowDecoration;
        rect.set(getBounds(desktopModeWindowDecoration.mTaskInfo));
        this.startingPoint.set(f, f2);
        this.lastValidPoint.set(f, f2);
        int width = this.lastRepositionedBounds.width();
        int height = this.lastRepositionedBounds.height();
        this.startingAspectRatio = DesktopModeUtils.calculateAspectRatio(desktopModeWindowDecoration.mTaskInfo);
        this.isTaskPortrait = width <= height;
        Rect rect2 = this.lastRepositionedBounds;
        int i3 = this.originalCtrlType;
        if (i3 == 1 || i3 == 2) {
            int i4 = i3 + (f2 < ((float) ((height / 2) + rect2.top)) ? 4 : 8);
            this.edgeResizeCtrlType = i4;
            onDragPositioningStart = taskPositioner.onDragPositioningStart(i4, f, f2, i2);
        } else if (i3 == 4 || i3 == 8) {
            int i5 = i3 + (f >= ((float) ((width / 2) + rect2.left)) ? 2 : 1);
            this.edgeResizeCtrlType = i5;
            onDragPositioningStart = taskPositioner.onDragPositioningStart(i5, f, f2, i2);
        } else {
            this.edgeResizeCtrlType = 0;
            onDragPositioningStart = taskPositioner.onDragPositioningStart(i3, f, f2, i2);
        }
        rect2.set(onDragPositioningStart);
        return this.lastRepositionedBounds;
    }

    public final boolean requiresFixedAspectRatio() {
        int i = this.originalCtrlType;
        return (((i & 4) == 0 && (i & 8) == 0 && (i & 1) == 0 && (i & 2) == 0) || this.windowDecoration.mTaskInfo.isResizeable) ? false : true;
    }
}
