package android.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.android.internal.R;

@Deprecated
/* loaded from: classes3.dex */
public class PreferenceFrameLayout extends FrameLayout {
    private static final int DEFAULT_BORDER_BOTTOM = 0;
    private static final int DEFAULT_BORDER_LEFT = 0;
    private static final int DEFAULT_BORDER_RIGHT = 0;
    private static final int DEFAULT_BORDER_TOP = 0;
    private final int mBorderBottom;
    private final int mBorderLeft;
    private final int mBorderRight;
    private final int mBorderTop;
    private boolean mPaddingApplied;

    public PreferenceFrameLayout(Context context) {
        this(context, null);
    }

    public PreferenceFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.preferenceFrameLayoutStyle);
    }

    public PreferenceFrameLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public PreferenceFrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PreferenceFrameLayout, i, i2);
        int i3 = (int) ((context.getResources().getDisplayMetrics().density * 0.0f) + 0.5f);
        this.mBorderTop = typedArrayObtainStyledAttributes.getDimensionPixelSize(3, i3);
        this.mBorderBottom = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, i3);
        this.mBorderLeft = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, i3);
        this.mBorderRight = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, i3);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public void addView(View view) {
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        LayoutParams layoutParams = view.getLayoutParams() instanceof LayoutParams ? (LayoutParams) view.getLayoutParams() : null;
        if (layoutParams != null && layoutParams.removeBorders) {
            if (this.mPaddingApplied) {
                paddingTop -= this.mBorderTop;
                paddingBottom -= this.mBorderBottom;
                paddingLeft -= this.mBorderLeft;
                paddingRight -= this.mBorderRight;
                this.mPaddingApplied = false;
            }
        } else if (!this.mPaddingApplied) {
            paddingTop += this.mBorderTop;
            paddingBottom += this.mBorderBottom;
            paddingLeft += this.mBorderLeft;
            paddingRight += this.mBorderRight;
            this.mPaddingApplied = true;
        }
        int paddingTop2 = getPaddingTop();
        int paddingBottom2 = getPaddingBottom();
        int paddingLeft2 = getPaddingLeft();
        int paddingRight2 = getPaddingRight();
        if (paddingTop2 != paddingTop || paddingBottom2 != paddingBottom || paddingLeft2 != paddingLeft || paddingRight2 != paddingRight) {
            setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        }
        super.addView(view);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {
        public boolean removeBorders;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.removeBorders = false;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PreferenceFrameLayout_Layout);
            this.removeBorders = typedArrayObtainStyledAttributes.getBoolean(0, false);
            typedArrayObtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.removeBorders = false;
        }
    }
}
