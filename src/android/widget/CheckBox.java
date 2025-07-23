package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RemoteViews;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class CheckBox extends CompoundButton {
    @Override // android.view.View
    public boolean isHighContrastTextEnabled() {
        return false;
    }

    public CheckBox(Context context) {
        this(context, null);
    }

    public CheckBox(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842860);
    }

    public CheckBox(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public CheckBox(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.widget.CompoundButton, android.widget.Button, android.widget.TextView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return CheckBox.class.getName();
    }
}
