package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.RemoteViews;
import com.android.internal.R;

@RemoteViews.RemoteView
@Deprecated
/* loaded from: classes5.dex */
public class AbsoluteLayout extends ViewGroup {
    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        public int x;
        public int y;

        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<LayoutParams> {
            private int mLayout_xId;
            private int mLayout_yId;
            private boolean mPropertiesMapped = false;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(PropertyMapper propertyMapper) {
                this.mLayout_xId = propertyMapper.mapInt("layout_x", 16843135);
                this.mLayout_yId = propertyMapper.mapInt("layout_y", 16843136);
                this.mPropertiesMapped = true;
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(LayoutParams layoutParams, PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new InspectionCompanion.UninitializedPropertyMapException();
                }
                propertyReader.readInt(this.mLayout_xId, layoutParams.x);
                propertyReader.readInt(this.mLayout_yId, layoutParams.y);
            }
        }

        public LayoutParams(int i, int i2, int i3, int i4) {
            super(i, i2);
            this.x = i3;
            this.y = i4;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AbsoluteLayout_Layout);
            this.x = obtainStyledAttributes.getDimensionPixelOffset(0, 0);
            this.y = obtainStyledAttributes.getDimensionPixelOffset(1, 0);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        @Override // android.view.ViewGroup.LayoutParams
        public String debug(String str) {
            return str + "Absolute.LayoutParams={width=" + sizeToString(this.width) + ", height=" + sizeToString(this.height) + " x=" + this.x + " y=" + this.y + "}";
        }
    }

    public AbsoluteLayout(Context context) {
        this(context, null);
    }

    public AbsoluteLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AbsoluteLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AbsoluteLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        measureChildren(i, i2);
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredWidth = layoutParams.x + childAt.getMeasuredWidth();
                int measuredHeight = layoutParams.y + childAt.getMeasuredHeight();
                i3 = Math.max(i3, measuredWidth);
                i4 = Math.max(i4, measuredHeight);
            }
        }
        setMeasuredDimension(resolveSizeAndState(Math.max(i3 + this.mPaddingLeft + this.mPaddingRight, getSuggestedMinimumWidth()), i, 0), resolveSizeAndState(Math.max(i4 + this.mPaddingTop + this.mPaddingBottom, getSuggestedMinimumHeight()), i2, 0));
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2, 0, 0);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int i6 = this.mPaddingLeft + layoutParams.x;
                int i7 = this.mPaddingTop + layoutParams.y;
                childAt.layout(i6, i7, childAt.getMeasuredWidth() + i6, childAt.getMeasuredHeight() + i7);
            }
        }
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }
}
