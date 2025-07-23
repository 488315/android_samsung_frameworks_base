package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Switch;
import com.android.systemui.widget.SystemUIImageView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class PasswordSwitch extends SystemUIImageView {
    public PasswordSwitch(Context context) {
        super(context);
    }

    @Override // android.widget.ImageView, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return Switch.class.getName();
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
