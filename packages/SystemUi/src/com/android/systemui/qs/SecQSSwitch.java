package com.android.systemui.qs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.SwitchCompat;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SecQSSwitch extends SwitchCompat {
    public boolean mIsCheckedWhenTouchDown;
    public boolean mIsClicked;

    public SecQSSwitch(Context context) {
        super(context);
    }

    @Override // androidx.appcompat.widget.SwitchCompat, android.widget.TextView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.mIsCheckedWhenTouchDown = isChecked();
            this.mIsClicked = false;
        }
        boolean onTouchEvent = super.onTouchEvent(motionEvent);
        int action = motionEvent.getAction();
        if (action == 1 || action == 3) {
            boolean isChecked = isChecked();
            if (!this.mIsClicked && this.mIsCheckedWhenTouchDown != isChecked) {
                callOnClick();
            }
        }
        return onTouchEvent;
    }

    @Override // android.widget.CompoundButton, android.view.View
    public final boolean performClick() {
        this.mIsClicked = true;
        return super.performClick();
    }

    public SecQSSwitch(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SecQSSwitch(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
