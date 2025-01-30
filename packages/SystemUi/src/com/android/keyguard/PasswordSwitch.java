package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Switch;
import com.android.systemui.widget.SystemUIImageView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
