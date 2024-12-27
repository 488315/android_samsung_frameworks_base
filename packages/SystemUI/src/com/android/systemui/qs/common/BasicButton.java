package com.android.systemui.qs.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BasicButton extends TextView {
    public BasicButton(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    @Override // android.widget.TextView, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return "android.widget.Button";
    }

    public /* synthetic */ BasicButton(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public BasicButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
