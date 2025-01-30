package com.google.android.setupcompat.template;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class FooterActionButton extends Button {
    public FooterButton footerButton;
    public boolean isPrimaryButtonStyle;

    public FooterActionButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isPrimaryButtonStyle = false;
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        FooterButton footerButton;
        if (motionEvent.getAction() == 0 && (footerButton = this.footerButton) != null) {
            boolean z = footerButton.enabled;
        }
        return super.onTouchEvent(motionEvent);
    }
}
