package com.android.server.wm;

import android.os.Looper;
import android.util.Slog;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.MotionEvent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DragInputEventReceiver extends InputEventReceiver {
    public final DragDropController mDragDropController;
    public boolean mIsStartEvent;
    public boolean mMuteInput;
    public boolean mStylusButtonDownAtStart;

    public DragInputEventReceiver(InputChannel inputChannel, Looper looper, DragDropController dragDropController) {
        super(inputChannel, looper);
        this.mIsStartEvent = true;
        this.mMuteInput = false;
        this.mDragDropController = dragDropController;
    }

    public final void onInputEvent(InputEvent inputEvent) {
        try {
            try {
                if ((inputEvent instanceof MotionEvent) && (inputEvent.getSource() & 2) != 0 && !this.mMuteInput) {
                    MotionEvent motionEvent = (MotionEvent) inputEvent;
                    float rawX = motionEvent.getRawX();
                    float rawY = motionEvent.getRawY();
                    boolean z = (motionEvent.getButtonState() & 32) != 0;
                    if (this.mIsStartEvent) {
                        this.mStylusButtonDownAtStart = z;
                        this.mIsStartEvent = false;
                    }
                    int action = motionEvent.getAction();
                    if (action == 0) {
                        Slog.w("WindowManager", "Unexpected ACTION_DOWN in drag layer");
                        finishInputEvent(inputEvent, false);
                        return;
                    }
                    if (action == 1) {
                        Slog.d("WindowManager", "Got UP on move channel; dropping at " + rawX + "," + rawY);
                        this.mMuteInput = true;
                    } else if (action != 2) {
                        if (action != 3) {
                            finishInputEvent(inputEvent, false);
                            return;
                        } else {
                            Slog.d("WindowManager", "Drag cancelled!");
                            this.mMuteInput = true;
                        }
                    } else if (this.mStylusButtonDownAtStart && !z) {
                        Slog.d("WindowManager", "Button no longer pressed; dropping at " + rawX + "," + rawY);
                        this.mMuteInput = true;
                    }
                    this.mDragDropController.handleMotionEvent(!this.mMuteInput, rawX, rawY);
                    finishInputEvent(inputEvent, true);
                }
            } catch (Exception e) {
                Slog.e("WindowManager", "Exception caught by drag handleMotion", e);
                finishInputEvent(inputEvent, false);
            }
        } finally {
            finishInputEvent(inputEvent, false);
        }
    }
}
