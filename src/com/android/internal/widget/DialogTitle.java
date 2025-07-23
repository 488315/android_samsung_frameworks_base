package com.android.internal.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/* loaded from: classes6.dex */
public class DialogTitle extends TextView {
    public DialogTitle(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public DialogTitle(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public DialogTitle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DialogTitle(Context context) {
        super(context);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        int lineCount;
        super.onMeasure(i, i2);
        Layout layout = getLayout();
        if (layout == null || (lineCount = layout.getLineCount()) <= 0 || layout.getEllipsisCount(lineCount - 1) <= 0) {
            return;
        }
        setSingleLine(false);
        setMaxLines(2);
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(null, R.styleable.TextAppearance, 16842817, 16973892);
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(com.android.internal.R.attr.parentIsDeviceDefault, typedValue, true);
        if (typedValue.data != 0) {
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(com.android.internal.R.dimen.sem_dialog_title_text_size);
            float f = this.mContext.getResources().getConfiguration().fontScale;
            float f2 = dimensionPixelSize;
            if (f > 1.3f) {
                f2 = (f2 / f) * 1.3f;
            }
            setTextSize(0, f2);
        } else {
            int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(0, 0);
            if (dimensionPixelSize2 != 0) {
                setTextSize(0, dimensionPixelSize2);
            }
        }
        obtainStyledAttributes.recycle();
        super.onMeasure(i, i2);
    }
}
