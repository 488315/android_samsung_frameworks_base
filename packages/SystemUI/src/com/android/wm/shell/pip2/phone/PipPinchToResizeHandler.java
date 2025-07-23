package com.android.wm.shell.pip2.phone;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipPerfHintController;
import com.android.wm.shell.common.pip.PipPinchResizingAlgorithm;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipPinchToResizeHandler {
    public final PhonePipMenuController mPhonePipMenuController;
    public final PipBoundsState mPipBoundsState;
    public final PipResizeGestureHandler mPipResizeGestureHandler;
    public final PipScheduler mPipScheduler;
    public int mFirstIndex = -1;
    public int mSecondIndex = -1;
    public final PipPinchResizingAlgorithm mPinchResizingAlgorithm = new PipPinchResizingAlgorithm();

    public PipPinchToResizeHandler(PipResizeGestureHandler pipResizeGestureHandler, PipBoundsState pipBoundsState, PhonePipMenuController phonePipMenuController, PipScheduler pipScheduler) {
        this.mPipResizeGestureHandler = pipResizeGestureHandler;
        this.mPipBoundsState = pipBoundsState;
        this.mPhonePipMenuController = phonePipMenuController;
        this.mPipScheduler = pipScheduler;
    }

    public final void onPinchResize(MotionEvent motionEvent, PointF pointF, PointF pointF2, Rect rect, PointF pointF3, PointF pointF4, Rect rect2, float f, Point point, Point point2) {
        int i;
        int actionMasked = motionEvent.getActionMasked();
        final PipResizeGestureHandler pipResizeGestureHandler = this.mPipResizeGestureHandler;
        if (actionMasked == 1 || actionMasked == 3) {
            this.mFirstIndex = -1;
            this.mSecondIndex = -1;
            pipResizeGestureHandler.mAllowGesture = false;
            pipResizeGestureHandler.finishResize();
        }
        if (motionEvent.getPointerCount() != 2) {
            return;
        }
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Rect bounds = pipBoundsState.getBounds();
        if (actionMasked == 5 && this.mFirstIndex == -1 && this.mSecondIndex == -1 && bounds.contains((int) motionEvent.getRawX(0), (int) motionEvent.getRawY(0)) && bounds.contains((int) motionEvent.getRawX(1), (int) motionEvent.getRawY(1))) {
            pipResizeGestureHandler.mAllowGesture = true;
            this.mFirstIndex = 0;
            this.mSecondIndex = 1;
            pointF.set(motionEvent.getRawX(0), motionEvent.getRawY(this.mFirstIndex));
            pointF2.set(motionEvent.getRawX(this.mSecondIndex), motionEvent.getRawY(this.mSecondIndex));
            rect.set(bounds);
            pointF3.set(pointF);
            pointF4.set(pointF4);
            rect2.set(rect);
            PipPerfHintController pipPerfHintController = pipResizeGestureHandler.mPipPerfHintController;
            if (pipPerfHintController != null) {
                pipResizeGestureHandler.mPipHighPerfSession = pipPerfHintController.startSession(new Consumer() { // from class: com.android.wm.shell.pip2.phone.PipResizeGestureHandler$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PipResizeGestureHandler.this.getClass();
                    }
                }, "onPinchResize");
            }
        }
        if (actionMasked != 2 || (i = this.mFirstIndex) == -1 || this.mSecondIndex == -1) {
            return;
        }
        float rawX = motionEvent.getRawX(i);
        float rawY = motionEvent.getRawY(this.mFirstIndex);
        float rawX2 = motionEvent.getRawX(this.mSecondIndex);
        float rawY2 = motionEvent.getRawY(this.mSecondIndex);
        pointF3.set(rawX, rawY);
        pointF4.set(rawX2, rawY2);
        if (!pipResizeGestureHandler.mThresholdCrossed && (((float) Math.hypot(pointF4.x - pointF2.x, pointF4.y - pointF2.y)) > f || ((float) Math.hypot(pointF3.x - pointF.x, pointF3.y - pointF.y)) > f)) {
            pipResizeGestureHandler.pilferPointers();
            pipResizeGestureHandler.mThresholdCrossed = true;
            pointF.set(pointF3);
            pointF2.set(pointF4);
            PhonePipMenuController phonePipMenuController = this.mPhonePipMenuController;
            if (phonePipMenuController.isMenuVisible()) {
                phonePipMenuController.hideMenu();
            }
        }
        if (pipResizeGestureHandler.mThresholdCrossed) {
            float calculateBoundsAndAngle = this.mPinchResizingAlgorithm.calculateBoundsAndAngle(pointF, pointF2, pointF3, pointF4, point, point2, rect, rect2);
            pipResizeGestureHandler.mAngle = calculateBoundsAndAngle;
            this.mPipScheduler.scheduleUserResizePip(rect2, calculateBoundsAndAngle);
            pipBoundsState.setHasUserResizedPip();
        }
    }
}
