package com.android.systemui.user;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UserSwitcherRootView extends ConstraintLayout {
    public UserSwitcherViewBinder$bind$1 touchHandler;

    public UserSwitcherRootView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        UserSwitcherViewBinder$bind$1 userSwitcherViewBinder$bind$1 = this.touchHandler;
        if (userSwitcherViewBinder$bind$1 != null) {
            userSwitcherViewBinder$bind$1.$falsingCollector.onTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
