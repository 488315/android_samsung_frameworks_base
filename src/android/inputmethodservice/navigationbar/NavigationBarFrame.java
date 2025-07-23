package android.inputmethodservice.navigationbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/* loaded from: classes2.dex */
public final class NavigationBarFrame extends FrameLayout {
    private DeadZone mDeadZone;

    public NavigationBarFrame(Context context) {
        super(context);
        this.mDeadZone = null;
    }

    public NavigationBarFrame(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDeadZone = null;
    }

    public NavigationBarFrame(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDeadZone = null;
    }

    public void setDeadZone(DeadZone deadZone) {
        this.mDeadZone = deadZone;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        DeadZone deadZone;
        if (motionEvent.getAction() == 4 && (deadZone = this.mDeadZone) != null) {
            return deadZone.onTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
