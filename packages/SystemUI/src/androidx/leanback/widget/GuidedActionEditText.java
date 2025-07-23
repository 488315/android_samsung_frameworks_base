package androidx.leanback.widget;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.TextView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class GuidedActionEditText extends EditText {
    public final NoPaddingDrawable mNoPaddingDrawable;
    public final Drawable mSavedBackground;

    public GuidedActionEditText(Context context) {
        this(context, null);
    }

    @Override // android.widget.TextView, android.view.View
    public final int getAutofillType() {
        return 1;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (z) {
            setBackground(this.mSavedBackground);
        } else {
            setBackground(this.mNoPaddingDrawable);
        }
        if (z) {
            return;
        }
        setFocusable(false);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((isFocused() ? EditText.class : TextView.class).getName());
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isInTouchMode() || isFocusableInTouchMode() || isTextSelectable()) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }

    public GuidedActionEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.editTextStyle);
    }

    public GuidedActionEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSavedBackground = getBackground();
        NoPaddingDrawable noPaddingDrawable = new NoPaddingDrawable();
        this.mNoPaddingDrawable = noPaddingDrawable;
        setBackground(noPaddingDrawable);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NoPaddingDrawable extends Drawable {
        @Override // android.graphics.drawable.Drawable
        public final int getOpacity() {
            return -2;
        }

        @Override // android.graphics.drawable.Drawable
        public final boolean getPadding(Rect rect) {
            rect.set(0, 0, 0, 0);
            return true;
        }

        @Override // android.graphics.drawable.Drawable
        public final void draw(Canvas canvas) {
        }

        @Override // android.graphics.drawable.Drawable
        public final void setAlpha(int i) {
        }

        @Override // android.graphics.drawable.Drawable
        public final void setColorFilter(ColorFilter colorFilter) {
        }
    }
}
