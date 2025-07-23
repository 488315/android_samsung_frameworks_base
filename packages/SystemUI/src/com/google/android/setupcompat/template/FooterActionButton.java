package com.google.android.setupcompat.template;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class FooterActionButton extends Button implements IFooterActionButton {
    FooterButton footerButton;
    public boolean isPrimaryButtonStyle;

    public FooterActionButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isPrimaryButtonStyle = false;
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        motionEvent.getAction();
        return super.onTouchEvent(motionEvent);
    }
}
