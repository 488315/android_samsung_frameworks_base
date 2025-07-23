package android.inputmethodservice.navigationbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/* loaded from: classes2.dex */
public class NavigationHandle extends View implements ButtonInterface {
    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.inputmethodservice.navigationbar.ButtonInterface
    public void setDarkIntensity(float f) {
    }

    @Override // android.inputmethodservice.navigationbar.ButtonInterface
    public void setDelayTouchFeedback(boolean z) {
    }

    @Override // android.inputmethodservice.navigationbar.ButtonInterface
    public void setImageDrawable(Drawable drawable) {
    }

    public NavigationHandle(Context context) {
        this(context, null);
    }

    public NavigationHandle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setFocusable(false);
    }
}
