package com.android.systemui.user;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.Gefingerpoken;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
