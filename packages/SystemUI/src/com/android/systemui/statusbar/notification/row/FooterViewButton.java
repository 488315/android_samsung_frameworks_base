package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import com.android.systemui.statusbar.AlphaOptimizedButton;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
