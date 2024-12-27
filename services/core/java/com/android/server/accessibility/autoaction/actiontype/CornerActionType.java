package com.android.server.accessibility.autoaction.actiontype;

import android.content.Context;
import android.hardware.input.InputManager;
import android.view.MotionEvent;

import com.android.server.accessibility.autoaction.CornerActionCircleCue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class CornerActionType {
    public static CornerActionType create(String str, Context context, MotionEvent motionEvent) {
        str.getClass();
        switch (str) {
            case "click_and_hold":
                ClickAndHold clickAndHold = new ClickAndHold();
                clickAndHold.mLastMotionEvent = null;
                clickAndHold.mContext = context;
                clickAndHold.mLastMotionEvent = MotionEvent.obtain(motionEvent);
                return clickAndHold;
            case "zoom_in":
            case "zoom_out":
                Zoom zoom = new Zoom();
                zoom.mLastMotionEvent = null;
                zoom.mContext = context;
                zoom.mInputManager = (InputManager) context.getSystemService("input");
                zoom.mType = str;
                zoom.mLastMotionEvent = MotionEvent.obtain(motionEvent);
                return zoom;
            case "swipe_up":
            case "swipe_down":
            case "swipe_left":
            case "swipe_right":
                Swipe swipe = new Swipe();
                swipe.mLastMotionEvent = null;
                swipe.mContext = context;
                swipe.mType = str;
                swipe.mLastMotionEvent = MotionEvent.obtain(motionEvent);
                return swipe;
            case "drag":
            case "drag_and_drop":
                DragAction dragAction = new DragAction();
                dragAction.mFirstEvent = null;
                dragAction.mSecondEvent = null;
                dragAction.mContext = context;
                dragAction.mType = str;
                dragAction.mFirstEvent = MotionEvent.obtain(motionEvent);
                dragAction.mFirstPoint = new CornerActionCircleCue(context, 1);
                dragAction.mSecondPoint = new CornerActionCircleCue(context, 1);
                return dragAction;
            case "double_click":
                DoubleClick doubleClick = new DoubleClick();
                doubleClick.mLastMotionEvent = null;
                doubleClick.mInputManager = (InputManager) context.getSystemService("input");
                doubleClick.mLastMotionEvent = MotionEvent.obtain(motionEvent);
                return doubleClick;
            default:
                throw new IllegalArgumentException("Wrong Corner Action Type");
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x0396, code lost:

       if (r25.equals("recents") == false) goto L251;
    */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x02a1, code lost:

       if (r25.equals("swipe_right") == false) goto L165;
    */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getTitleResId(java.lang.String r25) {
        /*
            Method dump skipped, instructions count: 1280
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accessibility.autoaction.actiontype.CornerActionType.getTitleResId(java.lang.String):int");
    }

    public abstract void performCornerAction(int i);

    public void setMotionEventForDragAction(MotionEvent motionEvent) {}
}
