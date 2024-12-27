package com.android.systemui.user;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.Gefingerpoken;

public final class UserSwitcherRootView extends ConstraintLayout {
    public Gefingerpoken touchHandler;

    public UserSwitcherRootView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        Gefingerpoken gefingerpoken = this.touchHandler;
        if (gefingerpoken != null) {
            gefingerpoken.onTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
