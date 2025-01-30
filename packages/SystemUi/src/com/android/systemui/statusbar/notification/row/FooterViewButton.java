package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import com.android.systemui.statusbar.AlphaOptimizedButton;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class FooterViewButton extends AlphaOptimizedButton {
    public FooterViewButton(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void getDrawingRect(Rect rect) {
        super.getDrawingRect(rect);
        float translationX = ((ViewGroup) ((Button) this).mParent).getTranslationX();
        float translationY = ((ViewGroup) ((Button) this).mParent).getTranslationY();
        rect.left = (int) (rect.left + translationX);
        rect.right = (int) (rect.right + translationX);
        rect.top = (int) (rect.top + translationY);
        rect.bottom = (int) (rect.bottom + translationY);
    }

    public FooterViewButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FooterViewButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public FooterViewButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
