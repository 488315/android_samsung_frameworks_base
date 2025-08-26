package androidx.leanback.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;

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
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, androidx.leanback.R$styleable.lbResizingTextView, i, i2);
        try {
            this.mTriggerConditions = typedArrayObtainStyledAttributes.getInt(1, 1);
            this.mResizedTextSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(4, -1);
            this.mMaintainLineSpacing = typedArrayObtainStyledAttributes.getBoolean(0, false);
            this.mResizedPaddingAdjustmentTop = typedArrayObtainStyledAttributes.getDimensionPixelOffset(3, 0);
            this.mResizedPaddingAdjustmentBottom = typedArrayObtainStyledAttributes.getDimensionPixelOffset(2, 0);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d3 A[PHI: r2
      0x00d3: PHI (r2v6 boolean) = (r2v2 boolean), (r2v8 boolean) binds: [B:43:0x00d0, B:28:0x0099] A[DONT_GENERATE, DONT_INLINE]] */
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
        if (layout == null || (this.mTriggerConditions & 1) <= 0) {
            z = false;
        } else {
            int lineCount = layout.getLineCount();
            int maxLines = getMaxLines();
            if (maxLines > 1 && lineCount == maxLines) {
                z = true;
            }
        }
        int textSize = (int) getTextSize();
        if (z) {
            int i4 = this.mResizedTextSize;
            if (i4 != -1 && textSize != i4) {
                setTextSize(0, i4);
                z3 = true;
            }
            float f = (this.mDefaultLineSpacingExtra + this.mDefaultTextSize) - this.mResizedTextSize;
            if (this.mMaintainLineSpacing && getLineSpacingExtra() != f) {
                setLineSpacing(f, getLineSpacingMultiplier());
                z3 = true;
            }
            int i5 = this.mDefaultPaddingTop + this.mResizedPaddingAdjustmentTop;
            int i6 = this.mDefaultPaddingBottom + this.mResizedPaddingAdjustmentBottom;
            if (getPaddingTop() == i5 && getPaddingBottom() == i6) {
                z2 = z3;
            } else {
                setPaddingTopAndBottom(i5, i6);
            }
        } else {
            if (this.mResizedTextSize != -1 && textSize != (i3 = this.mDefaultTextSize)) {
                setTextSize(0, i3);
                z3 = true;
            }
            if (this.mMaintainLineSpacing) {
                float lineSpacingExtra = getLineSpacingExtra();
                float f2 = this.mDefaultLineSpacingExtra;
                if (lineSpacingExtra != f2) {
                    setLineSpacing(f2, getLineSpacingMultiplier());
                    z3 = true;
                }
            }
            if (getPaddingTop() != this.mDefaultPaddingTop || getPaddingBottom() != this.mDefaultPaddingBottom) {
                setPaddingTopAndBottom(this.mDefaultPaddingTop, this.mDefaultPaddingBottom);
            }
        }
        if (z2) {
            super.onMeasure(i, i2);
        }
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
