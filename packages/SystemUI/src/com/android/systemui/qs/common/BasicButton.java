package com.android.systemui.qs.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
