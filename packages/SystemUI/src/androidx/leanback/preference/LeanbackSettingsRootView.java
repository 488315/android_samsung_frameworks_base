package androidx.leanback.preference;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class LeanbackSettingsRootView extends FrameLayout {
    public LeanbackSettingsRootView(Context context) {
        super(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1) {
            keyEvent.getKeyCode();
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public LeanbackSettingsRootView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public LeanbackSettingsRootView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
