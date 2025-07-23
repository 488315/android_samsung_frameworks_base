package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes5.dex */
public class TextSwitcher extends ViewSwitcher {
    public TextSwitcher(Context context) {
        super(context);
    }

    public TextSwitcher(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.ViewSwitcher, android.widget.ViewAnimator, android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (!(view instanceof TextView)) {
            throw new IllegalArgumentException("TextSwitcher children must be instances of TextView");
        }
        super.addView(view, i, layoutParams);
    }

    public void setText(CharSequence charSequence) {
        ((TextView) getNextView()).lambda$setTextAsync$0(charSequence);
        showNext();
    }

    public void setCurrentText(CharSequence charSequence) {
        ((TextView) getCurrentView()).lambda$setTextAsync$0(charSequence);
    }

    @Override // android.widget.ViewSwitcher, android.widget.ViewAnimator, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return TextSwitcher.class.getName();
    }
}
