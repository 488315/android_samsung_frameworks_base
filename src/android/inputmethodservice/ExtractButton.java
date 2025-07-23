package android.inputmethodservice;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/* loaded from: classes2.dex */
class ExtractButton extends Button {
    public ExtractButton(Context context) {
        super(context, null);
    }

    public ExtractButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 16842824);
    }

    public ExtractButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ExtractButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.view.View
    public boolean hasWindowFocus() {
        return isEnabled() && getVisibility() == 0;
    }
}
