package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.Switch;
import com.android.systemui.widget.SystemUIImageView;

/* loaded from: classes.dex */
public class PasswordSwitch extends SystemUIImageView {
    public PasswordSwitch(Context context) {
        super(context);
    }

    @Override // android.widget.ImageView, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return Switch.class.getName();
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 66) {
            return super.onKeyDown(i, keyEvent);
        }
        performClick();
        return true;
    }

    public PasswordSwitch(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PasswordSwitch(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public PasswordSwitch(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
