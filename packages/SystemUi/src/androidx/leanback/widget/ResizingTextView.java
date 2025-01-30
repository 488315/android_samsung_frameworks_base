package androidx.leanback.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.widget.TextView;
import androidx.leanback.R$styleable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.lbResizingTextView, i, i2);
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
    */
    public final void onMeasure(int i, int i2) {
        boolean z;
        int i3;
        boolean z2 = true;
        if (!this.mDefaultsInitialized) {
            this.mDefaultTextSize = (int) getTextSize();
            this.mDefaultLineSpacingExtra = getLineSpacingExtra();
            this.mDefaultPaddingTop = getPaddingTop();
            this.mDefaultPaddingBottom = getPaddingBottom();
            this.mDefaultsInitialized = true;
        }
        boolean z3 = false;
        setTextSize(0, this.mDefaultTextSize);
        setLineSpacing(this.mDefaultLineSpacingExtra, getLineSpacingMultiplier());
        setPaddingTopAndBottom(this.mDefaultPaddingTop, this.mDefaultPaddingBottom);
        super.onMeasure(i, i2);
        Layout layout = getLayout();
        if (layout != null && (this.mTriggerConditions & 1) > 0) {
            int lineCount = layout.getLineCount();
            int maxLines = getMaxLines();
            if (maxLines > 1 && lineCount == maxLines) {
                z = true;
                int textSize = (int) getTextSize();
                if (z) {
                    if (this.mResizedTextSize != -1 && textSize != (i3 = this.mDefaultTextSize)) {
                        setTextSize(0, i3);
                        z3 = true;
                    }
                    if (this.mMaintainLineSpacing) {
                        float lineSpacingExtra = getLineSpacingExtra();
                        float f = this.mDefaultLineSpacingExtra;
                        if (lineSpacingExtra != f) {
                            setLineSpacing(f, getLineSpacingMultiplier());
                            z3 = true;
                        }
                    }
                    if (getPaddingTop() != this.mDefaultPaddingTop || getPaddingBottom() != this.mDefaultPaddingBottom) {
                        setPaddingTopAndBottom(this.mDefaultPaddingTop, this.mDefaultPaddingBottom);
                    }
                    z2 = z3;
                } else {
                    int i4 = this.mResizedTextSize;
                    if (i4 != -1 && textSize != i4) {
                        setTextSize(0, i4);
                        z3 = true;
                    }
                    float f2 = (this.mDefaultLineSpacingExtra + this.mDefaultTextSize) - this.mResizedTextSize;
                    if (this.mMaintainLineSpacing && getLineSpacingExtra() != f2) {
                        setLineSpacing(f2, getLineSpacingMultiplier());
                        z3 = true;
                    }
                    int i5 = this.mDefaultPaddingTop + this.mResizedPaddingAdjustmentTop;
                    int i6 = this.mDefaultPaddingBottom + this.mResizedPaddingAdjustmentBottom;
                    if (getPaddingTop() != i5 || getPaddingBottom() != i6) {
                        setPaddingTopAndBottom(i5, i6);
                    }
                    z2 = z3;
                }
                if (z2) {
                    return;
                }
                super.onMeasure(i, i2);
                return;
            }
        }
        z = false;
        int textSize2 = (int) getTextSize();
        if (z) {
        }
        if (z2) {
        }
    }

    @Override // android.widget.TextView
    public final void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(callback);
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
