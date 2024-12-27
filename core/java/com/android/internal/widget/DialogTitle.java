package com.android.internal.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/* loaded from: classes5.dex */
public class DialogTitle extends TextView {
    public DialogTitle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DialogTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DialogTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DialogTitle(Context context) {
        super(context);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int lineCount;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Layout layout = getLayout();
        if (layout != null && (lineCount = layout.getLineCount()) > 0) {
            int ellipsisCount = layout.getEllipsisCount(lineCount - 1);
            if (ellipsisCount > 0) {
                setSingleLine(false);
                setMaxLines(2);
                TypedArray a =
                        this.mContext.obtainStyledAttributes(
                                null, R.styleable.TextAppearance, 16842817, 16973892);
                TypedValue outValue = new TypedValue();
                this.mContext
                        .getTheme()
                        .resolveAttribute(
                                com.android.internal.R.attr.parentIsDeviceDefault, outValue, true);
                boolean mIsDeviceDefault = outValue.data != 0;
                if (mIsDeviceDefault) {
                    int textSize =
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            com.android.internal.R.dimen
                                                    .sem_dialog_title_text_size);
                    float currentFontScale =
                            this.mContext.getResources().getConfiguration().fontScale;
                    float scaleBase = textSize;
                    if (currentFontScale > 1.3f) {
                        scaleBase = (textSize / currentFontScale) * 1.3f;
                    }
                    setTextSize(0, scaleBase);
                } else {
                    int textSize2 = a.getDimensionPixelSize(0, 0);
                    if (textSize2 != 0) {
                        setTextSize(0, textSize2);
                    }
                }
                a.recycle();
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }
}
