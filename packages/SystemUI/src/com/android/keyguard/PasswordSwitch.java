package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Switch;
import com.android.systemui.widget.SystemUIImageView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
