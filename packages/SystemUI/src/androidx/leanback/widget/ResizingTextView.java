package androidx.leanback.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
class ResizingTextView extends TextView {
    public float mDefaultLineSpacingExtra;
    public int mDefaultPaddingBottom;
    public int mDefaultPaddingTop;
    public int mDefaultTextSize;
    public boolean mDefaultsInitialized;
    public final boolean mMaintainLineSpacing;
    public final int mResizedPaddingAdjustmentBottom;
    public final int mResizedPaddingAdjustmentTop;
    public final int mResizedTextSize;
    public final int mTriggerConditions;

    public ResizingTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
        this.mDefaultsInitialized = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, androidx.leanback.R$styleable.lbResizingTextView, i, i2);
        try {
            this.mTriggerConditions = obtainStyledAttributes.getInt(1, 1);
            this.mResizedTextSize = obtainStyledAttributes.getDimensionPixelSize(4, -1);
            this.mMaintainLineSpacing = obtainStyledAttributes.getBoolean(0, false);
            this.mResizedPaddingAdjustmentTop = obtainStyledAttributes.getDimensionPixelOffset(3, 0);
            this.mResizedPaddingAdjustmentBottom = obtainStyledAttributes.getDimensionPixelOffset(2, 0);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x009f  */
    @Override // android.widget.TextView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onMeasure(int r6, int r7) {
        /*
            Method dump skipped, instructions count: 226
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.ResizingTextView.onMeasure(int, int):void");
    }

    public final void setPaddingTopAndBottom(int i, int i2) {
        if (isPaddingRelative()) {
            setPaddingRelative(getPaddingStart(), i, getPaddingEnd(), i2);
        } else {
            setPadding(getPaddingLeft(), i, getPaddingRight(), i2);
        }
    }

    public ResizingTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ResizingTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.textViewStyle);
    }

    public ResizingTextView(Context context) {
        this(context, null);
    }
}
