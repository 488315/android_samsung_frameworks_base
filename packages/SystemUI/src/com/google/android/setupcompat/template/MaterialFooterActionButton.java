package com.google.android.setupcompat.template;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.google.android.material.button.MaterialButton;

/* loaded from: classes4.dex */
public class MaterialFooterActionButton extends MaterialButton implements IFooterActionButton {
    FooterButton footerButton;

    public MaterialFooterActionButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        motionEvent.getAction();
        return super.onTouchEvent(motionEvent);
    }

    public MaterialFooterActionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
