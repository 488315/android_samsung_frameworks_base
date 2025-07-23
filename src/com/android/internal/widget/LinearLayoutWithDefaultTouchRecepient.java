package com.android.internal.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/* loaded from: classes6.dex */
public class LinearLayoutWithDefaultTouchRecepient extends LinearLayout {
    private View mDefaultTouchRecepient;
    private final Rect mTempRect;

    public LinearLayoutWithDefaultTouchRecepient(Context context) {
        super(context);
        this.mTempRect = new Rect();
    }

    public LinearLayoutWithDefaultTouchRecepient(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTempRect = new Rect();
    }

    public void setDefaultTouchRecepient(View view) {
        this.mDefaultTouchRecepient = view;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mDefaultTouchRecepient == null) {
            return super.dispatchTouchEvent(motionEvent);
        }
        if (super.dispatchTouchEvent(motionEvent)) {
            return true;
        }
        this.mTempRect.set(0, 0, 0, 0);
        offsetRectIntoDescendantCoords(this.mDefaultTouchRecepient, this.mTempRect);
        motionEvent.setLocation(motionEvent.getX() + this.mTempRect.left, motionEvent.getY() + this.mTempRect.top);
        return this.mDefaultTouchRecepient.dispatchTouchEvent(motionEvent);
    }
}
