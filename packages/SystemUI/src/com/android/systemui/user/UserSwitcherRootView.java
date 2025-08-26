package com.android.systemui.user;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.user.ui.binder.UserSwitcherViewBinder;

/* loaded from: classes3.dex */
public final class UserSwitcherRootView extends ConstraintLayout {
    public UserSwitcherViewBinder.AnonymousClass1 touchHandler;

    public UserSwitcherRootView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        UserSwitcherViewBinder.AnonymousClass1 anonymousClass1 = this.touchHandler;
        if (anonymousClass1 != null) {
            anonymousClass1.$falsingCollector.onTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
