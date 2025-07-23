package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AnimatedActionButton extends Button {
    public AnimatedActionButton(Context context) {
        this(context, null, 0, 6, null);
    }

    public AnimatedActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ AnimatedActionButton(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public AnimatedActionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setBackground(new AnimatedActionBackgroundDrawable(context));
    }
}
