package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.util.ArrayList;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class FrameLayout extends ViewGroup {
    private static final int DEFAULT_CHILD_GRAVITY = 8388659;
    private static final String TAG = "FrameLayout";

    @ViewDebug.ExportedProperty(category = "padding")
    private int mForegroundPaddingBottom;

    @ViewDebug.ExportedProperty(category = "padding")
    private int mForegroundPaddingLeft;

    @ViewDebug.ExportedProperty(category = "padding")
    private int mForegroundPaddingRight;

    @ViewDebug.ExportedProperty(category = "padding")
    private int mForegroundPaddingTop;
    private final ArrayList<View> mMatchParentChildren;

    @ViewDebug.ExportedProperty(category = "measurement")
    boolean mMeasureAllChildren;

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public static final int UNSPECIFIED_GRAVITY = -1;
        public int gravity;

        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<LayoutParams> {
            private int mLayout_gravityId;
            private boolean mPropertiesMapped = false;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(PropertyMapper propertyMapper) {
                this.mLayout_gravityId = propertyMapper.mapGravity("layout_gravity", 16842931);
                this.mPropertiesMapped = true;
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(LayoutParams layoutParams, PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new InspectionCompanion.UninitializedPropertyMapException();
                }
                propertyReader.readGravity(this.mLayout_gravityId, layoutParams.gravity);
            }
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.gravity = -1;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FrameLayout_Layout);
            this.gravity = obtainStyledAttributes.getInt(0, -1);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.gravity = -1;
        }

        public LayoutParams(int i, int i2, int i3) {
            super(i, i2);
            this.gravity = i3;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = -1;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.gravity = -1;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.gravity = -1;
            this.gravity = layoutParams.gravity;
        }
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<FrameLayout> {
        private int mMeasureAllChildrenId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mMeasureAllChildrenId = propertyMapper.mapBoolean("measureAllChildren", 16843018);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(FrameLayout frameLayout, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mMeasureAllChildrenId, frameLayout.getMeasureAllChildren());
        }
    }

    public FrameLayout(Context context) {
        super(context);
        this.mMeasureAllChildren = false;
        this.mForegroundPaddingLeft = 0;
        this.mForegroundPaddingTop = 0;
        this.mForegroundPaddingRight = 0;
        this.mForegroundPaddingBottom = 0;
        this.mMatchParentChildren = new ArrayList<>(1);
    }

    public FrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FrameLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public FrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMeasureAllChildren = false;
        this.mForegroundPaddingLeft = 0;
        this.mForegroundPaddingTop = 0;
        this.mForegroundPaddingRight = 0;
        this.mForegroundPaddingBottom = 0;
        this.mMatchParentChildren = new ArrayList<>(1);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FrameLayout, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.FrameLayout, attributeSet, obtainStyledAttributes, i, i2);
        if (obtainStyledAttributes.getBoolean(0, false)) {
            setMeasureAllChildren(true);
        }
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    @RemotableViewMethod
    public void setForegroundGravity(int i) {
        if (getForegroundGravity() != i) {
            super.setForegroundGravity(i);
            Drawable foreground = getForeground();
            if (getForegroundGravity() == 119 && foreground != null) {
                Rect rect = new Rect();
                if (foreground.getPadding(rect)) {
                    this.mForegroundPaddingLeft = rect.left;
                    this.mForegroundPaddingTop = rect.top;
                    this.mForegroundPaddingRight = rect.right;
                    this.mForegroundPaddingBottom = rect.bottom;
                }
            } else {
                this.mForegroundPaddingLeft = 0;
                this.mForegroundPaddingTop = 0;
                this.mForegroundPaddingRight = 0;
                this.mForegroundPaddingBottom = 0;
            }
            requestLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    int getPaddingLeftWithForeground() {
        return isForegroundInsidePadding() ? Math.max(this.mPaddingLeft, this.mForegroundPaddingLeft) : this.mPaddingLeft + this.mForegroundPaddingLeft;
    }

    int getPaddingRightWithForeground() {
        return isForegroundInsidePadding() ? Math.max(this.mPaddingRight, this.mForegroundPaddingRight) : this.mPaddingRight + this.mForegroundPaddingRight;
    }

    private int getPaddingTopWithForeground() {
        return isForegroundInsidePadding() ? Math.max(this.mPaddingTop, this.mForegroundPaddingTop) : this.mPaddingTop + this.mForegroundPaddingTop;
    }

    private int getPaddingBottomWithForeground() {
        return isForegroundInsidePadding() ? Math.max(this.mPaddingBottom, this.mForegroundPaddingBottom) : this.mPaddingBottom + this.mForegroundPaddingBottom;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int childMeasureSpec;
        int childMeasureSpec2;
        int childCount = getChildCount();
        boolean z = (View.MeasureSpec.getMode(i) == 1073741824 && View.MeasureSpec.getMode(i2) == 1073741824) ? false : true;
        this.mMatchParentChildren.clear();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (this.mMeasureAllChildren || childAt.getVisibility() != 8) {
                measureChildWithMargins(childAt, i, 0, i2, 0);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                i3 = Math.max(i3, childAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
                i4 = Math.max(i4, childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                i5 = combineMeasuredStates(i5, childAt.getMeasuredState());
                if (z && (layoutParams.width == -1 || layoutParams.height == -1)) {
                    this.mMatchParentChildren.add(childAt);
                }
            }
        }
        int paddingLeftWithForeground = i3 + getPaddingLeftWithForeground() + getPaddingRightWithForeground();
        int max = Math.max(i4 + getPaddingTopWithForeground() + getPaddingBottomWithForeground(), getSuggestedMinimumHeight());
        int max2 = Math.max(paddingLeftWithForeground, getSuggestedMinimumWidth());
        Drawable foreground = getForeground();
        if (foreground != null) {
            max = Math.max(max, foreground.getMinimumHeight());
            max2 = Math.max(max2, foreground.getMinimumWidth());
        }
        setMeasuredDimension(resolveSizeAndState(max2, i, i5), resolveSizeAndState(max, i2, i5 << 16));
        int size = this.mMatchParentChildren.size();
        if (size > 1) {
            for (int i7 = 0; i7 < size; i7++) {
                View view = this.mMatchParentChildren.get(i7);
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                if (marginLayoutParams.width == -1) {
                    childMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.max(0, (((getMeasuredWidth() - getPaddingLeftWithForeground()) - getPaddingRightWithForeground()) - marginLayoutParams.leftMargin) - marginLayoutParams.rightMargin), 1073741824);
                } else {
                    childMeasureSpec = getChildMeasureSpec(i, getPaddingLeftWithForeground() + getPaddingRightWithForeground() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin, marginLayoutParams.width);
                }
                if (marginLayoutParams.height == -1) {
                    childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(Math.max(0, (((getMeasuredHeight() - getPaddingTopWithForeground()) - getPaddingBottomWithForeground()) - marginLayoutParams.topMargin) - marginLayoutParams.bottomMargin), 1073741824);
                } else {
                    childMeasureSpec2 = getChildMeasureSpec(i2, getPaddingTopWithForeground() + getPaddingBottomWithForeground() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height);
                }
                view.measure(childMeasureSpec, childMeasureSpec2);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        layoutChildren(i, i2, i3, i4, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void layoutChildren(int r10, int r11, int r12, int r13, boolean r14) {
        /*
            r9 = this;
            int r0 = r9.getChildCount()
            int r1 = r9.getPaddingLeftWithForeground()
            int r12 = r12 - r10
            int r10 = r9.getPaddingRightWithForeground()
            int r12 = r12 - r10
            int r10 = r9.getPaddingTopWithForeground()
            int r13 = r13 - r11
            int r11 = r9.getPaddingBottomWithForeground()
            int r13 = r13 - r11
            r11 = 0
        L19:
            if (r11 >= r0) goto L94
            android.view.View r2 = r9.getChildAt(r11)
            int r3 = r2.getVisibility()
            r4 = 8
            if (r3 == r4) goto L91
            android.view.ViewGroup$LayoutParams r3 = r2.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r3 = (android.widget.FrameLayout.LayoutParams) r3
            int r4 = r2.getMeasuredWidth()
            int r5 = r2.getMeasuredHeight()
            int r6 = r3.gravity
            r7 = -1
            if (r6 != r7) goto L3d
            r6 = 8388659(0x800033, float:1.1755015E-38)
        L3d:
            int r7 = r9.getLayoutDirection()
            int r7 = android.view.Gravity.getAbsoluteGravity(r6, r7)
            r6 = r6 & 112(0x70, float:1.57E-43)
            r7 = r7 & 7
            r8 = 1
            if (r7 == r8) goto L5b
            r8 = 5
            if (r7 == r8) goto L50
            goto L57
        L50:
            if (r14 != 0) goto L57
            int r7 = r12 - r4
            int r8 = r3.rightMargin
            goto L66
        L57:
            int r7 = r3.leftMargin
            int r7 = r7 + r1
            goto L67
        L5b:
            int r7 = r12 - r1
            int r7 = r7 - r4
            int r7 = r7 / 2
            int r7 = r7 + r1
            int r8 = r3.leftMargin
            int r7 = r7 + r8
            int r8 = r3.rightMargin
        L66:
            int r7 = r7 - r8
        L67:
            r8 = 16
            if (r6 == r8) goto L7f
            r8 = 48
            if (r6 == r8) goto L7b
            r8 = 80
            if (r6 == r8) goto L76
            int r3 = r3.topMargin
            goto L7d
        L76:
            int r6 = r13 - r5
            int r3 = r3.bottomMargin
            goto L8a
        L7b:
            int r3 = r3.topMargin
        L7d:
            int r3 = r3 + r10
            goto L8c
        L7f:
            int r6 = r13 - r10
            int r6 = r6 - r5
            int r6 = r6 / 2
            int r6 = r6 + r10
            int r8 = r3.topMargin
            int r6 = r6 + r8
            int r3 = r3.bottomMargin
        L8a:
            int r3 = r6 - r3
        L8c:
            int r4 = r4 + r7
            int r5 = r5 + r3
            r2.layout(r7, r3, r4, r5)
        L91:
            int r11 = r11 + 1
            goto L19
        L94:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.FrameLayout.layoutChildren(int, int, int, int, boolean):void");
    }

    @RemotableViewMethod
    public void setMeasureAllChildren(boolean z) {
        this.mMeasureAllChildren = z;
    }

    @Deprecated
    public boolean getConsiderGoneChildrenWhenMeasuring() {
        return getMeasureAllChildren();
    }

    public boolean getMeasureAllChildren() {
        return this.mMeasureAllChildren;
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (sPreserveMarginParamsInLayoutParamConversion) {
            if (layoutParams instanceof LayoutParams) {
                return new LayoutParams((LayoutParams) layoutParams);
            }
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
            }
        }
        return new LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return FrameLayout.class.getName();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("measurement:measureAllChildren", this.mMeasureAllChildren);
        viewHierarchyEncoder.addProperty("padding:foregroundPaddingLeft", this.mForegroundPaddingLeft);
        viewHierarchyEncoder.addProperty("padding:foregroundPaddingTop", this.mForegroundPaddingTop);
        viewHierarchyEncoder.addProperty("padding:foregroundPaddingRight", this.mForegroundPaddingRight);
        viewHierarchyEncoder.addProperty("padding:foregroundPaddingBottom", this.mForegroundPaddingBottom);
    }

    @RemotableViewMethod
    public void semEnableAppWidgetImmersiveScroll(boolean z) {
        Log.w(TAG, "This appwidget feature is not supported on Framelayout");
    }

    @RemotableViewMethod
    public void semSetToolBarViewId(int i) {
        Log.w(TAG, "This appwidget feature is not supported");
    }

    @RemotableViewMethod
    public void semSetListViewId(int i) {
        Log.w(TAG, "This appwidget feature is not supported");
    }
}
