package android.widget;

import android.app.slice.Slice;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.TtmlUtils;
import android.security.keystore.KeyProperties;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.View$InspectionCompanion$$ExternalSyntheticLambda0;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class LinearLayout extends ViewGroup {
    public static final int HORIZONTAL = 0;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private static boolean sCompatibilityDone = false;
    private static boolean sRemeasureWeightedChildren = true;
    private final boolean mAllowInconsistentMeasurement;

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    private boolean mBaselineAligned;

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    private int mBaselineAlignedChildIndex;

    @ViewDebug.ExportedProperty(category = "measurement")
    private int mBaselineChildTop;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;

    @ViewDebug.ExportedProperty(category = "measurement", flagMapping = {@ViewDebug.FlagToString(equals = -1, mask = -1, name = KeyProperties.DIGEST_NONE), @ViewDebug.FlagToString(equals = 0, mask = 0, name = KeyProperties.DIGEST_NONE), @ViewDebug.FlagToString(equals = 48, mask = 48, name = "TOP"), @ViewDebug.FlagToString(equals = 80, mask = 80, name = "BOTTOM"), @ViewDebug.FlagToString(equals = 3, mask = 3, name = "LEFT"), @ViewDebug.FlagToString(equals = 5, mask = 5, name = "RIGHT"), @ViewDebug.FlagToString(equals = Gravity.START, mask = Gravity.START, name = "START"), @ViewDebug.FlagToString(equals = Gravity.END, mask = Gravity.END, name = "END"), @ViewDebug.FlagToString(equals = 16, mask = 16, name = "CENTER_VERTICAL"), @ViewDebug.FlagToString(equals = 112, mask = 112, name = "FILL_VERTICAL"), @ViewDebug.FlagToString(equals = 1, mask = 1, name = "CENTER_HORIZONTAL"), @ViewDebug.FlagToString(equals = 7, mask = 7, name = "FILL_HORIZONTAL"), @ViewDebug.FlagToString(equals = 17, mask = 17, name = "CENTER"), @ViewDebug.FlagToString(equals = 119, mask = 119, name = "FILL"), @ViewDebug.FlagToString(equals = 8388608, mask = 8388608, name = "RELATIVE")}, formatToHexString = true)
    private int mGravity;
    private int mLayoutDirection;
    private int[] mMaxAscent;
    private int[] mMaxDescent;

    @ViewDebug.ExportedProperty(category = "measurement")
    private int mOrientation;
    private int mShowDividers;

    @ViewDebug.ExportedProperty(category = "measurement")
    private int mTotalLength;

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    private boolean mUseLargestChild;

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    private float mWeightSum;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DividerMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationMode {
    }

    int getChildrenSkipCount(View view, int i) {
        return 0;
    }

    int getLocationOffset(View view) {
        return 0;
    }

    int getNextLocationOffset(View view) {
        return 0;
    }

    int measureNullChild(int i) {
        return 0;
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT, mapping = {@ViewDebug.IntToString(from = -1, to = KeyProperties.DIGEST_NONE), @ViewDebug.IntToString(from = 0, to = KeyProperties.DIGEST_NONE), @ViewDebug.IntToString(from = 48, to = "TOP"), @ViewDebug.IntToString(from = 80, to = "BOTTOM"), @ViewDebug.IntToString(from = 3, to = "LEFT"), @ViewDebug.IntToString(from = 5, to = "RIGHT"), @ViewDebug.IntToString(from = Gravity.START, to = "START"), @ViewDebug.IntToString(from = Gravity.END, to = "END"), @ViewDebug.IntToString(from = 16, to = "CENTER_VERTICAL"), @ViewDebug.IntToString(from = 112, to = "FILL_VERTICAL"), @ViewDebug.IntToString(from = 1, to = "CENTER_HORIZONTAL"), @ViewDebug.IntToString(from = 7, to = "FILL_HORIZONTAL"), @ViewDebug.IntToString(from = 17, to = "CENTER"), @ViewDebug.IntToString(from = 119, to = "FILL")})
        public int gravity;

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public float weight;

        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<LayoutParams> {
            private int mLayout_gravityId;
            private int mLayout_weightId;
            private boolean mPropertiesMapped = false;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(PropertyMapper propertyMapper) {
                this.mLayout_gravityId = propertyMapper.mapGravity("layout_gravity", 16842931);
                this.mLayout_weightId = propertyMapper.mapFloat("layout_weight", 16843137);
                this.mPropertiesMapped = true;
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(LayoutParams layoutParams, PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new InspectionCompanion.UninitializedPropertyMapException();
                }
                propertyReader.readGravity(this.mLayout_gravityId, layoutParams.gravity);
                propertyReader.readFloat(this.mLayout_weightId, layoutParams.weight);
            }
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.gravity = -1;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LinearLayout_Layout);
            this.weight = obtainStyledAttributes.getFloat(3, 0.0f);
            this.gravity = obtainStyledAttributes.getInt(0, -1);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.gravity = -1;
            this.weight = 0.0f;
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2);
            this.gravity = -1;
            this.weight = f;
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
            this.weight = layoutParams.weight;
            this.gravity = layoutParams.gravity;
        }

        @Override // android.view.ViewGroup.LayoutParams
        public String debug(String str) {
            return str + "LinearLayout.LayoutParams={width=" + sizeToString(this.width) + ", height=" + sizeToString(this.height) + " weight=" + this.weight + "}";
        }

        @Override // android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("layout:weight", this.weight);
            viewHierarchyEncoder.addProperty("layout:gravity", this.gravity);
        }
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<LinearLayout> {
        private int mBaselineAlignedChildIndexId;
        private int mBaselineAlignedId;
        private int mDividerId;
        private int mGravityId;
        private int mMeasureWithLargestChildId;
        private int mOrientationId;
        private boolean mPropertiesMapped = false;
        private int mWeightSumId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mBaselineAlignedId = propertyMapper.mapBoolean("baselineAligned", 16843046);
            this.mBaselineAlignedChildIndexId = propertyMapper.mapInt("baselineAlignedChildIndex", 16843047);
            this.mDividerId = propertyMapper.mapObject("divider", 16843049);
            this.mGravityId = propertyMapper.mapGravity("gravity", 16842927);
            this.mMeasureWithLargestChildId = propertyMapper.mapBoolean("measureWithLargestChild", 16843476);
            SparseArray sparseArray = new SparseArray();
            sparseArray.put(0, Slice.HINT_HORIZONTAL);
            sparseArray.put(1, "vertical");
            this.mOrientationId = propertyMapper.mapIntEnum("orientation", 16842948, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            this.mWeightSumId = propertyMapper.mapFloat("weightSum", 16843048);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(LinearLayout linearLayout, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mBaselineAlignedId, linearLayout.isBaselineAligned());
            propertyReader.readInt(this.mBaselineAlignedChildIndexId, linearLayout.getBaselineAlignedChildIndex());
            propertyReader.readObject(this.mDividerId, linearLayout.getDividerDrawable());
            propertyReader.readGravity(this.mGravityId, linearLayout.getGravity());
            propertyReader.readBoolean(this.mMeasureWithLargestChildId, linearLayout.isMeasureWithLargestChildEnabled());
            propertyReader.readIntEnum(this.mOrientationId, linearLayout.getOrientation());
            propertyReader.readFloat(this.mWeightSumId, linearLayout.getWeightSum());
        }
    }

    public LinearLayout(Context context) {
        this(context, null);
    }

    public LinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LinearLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public LinearLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        this.mLayoutDirection = -1;
        if (!sCompatibilityDone && context != null) {
            sRemeasureWeightedChildren = context.getApplicationInfo().targetSdkVersion >= 28;
            sCompatibilityDone = true;
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LinearLayout, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.LinearLayout, attributeSet, obtainStyledAttributes, i, i2);
        int i3 = obtainStyledAttributes.getInt(1, -1);
        if (i3 >= 0) {
            setOrientation(i3);
        }
        int i4 = obtainStyledAttributes.getInt(0, -1);
        if (i4 >= 0) {
            setGravity(i4);
        }
        boolean z = obtainStyledAttributes.getBoolean(2, true);
        if (!z) {
            setBaselineAligned(z);
        }
        this.mWeightSum = obtainStyledAttributes.getFloat(4, -1.0f);
        this.mBaselineAlignedChildIndex = obtainStyledAttributes.getInt(3, -1);
        this.mUseLargestChild = obtainStyledAttributes.getBoolean(6, false);
        this.mShowDividers = obtainStyledAttributes.getInt(7, 0);
        this.mDividerPadding = obtainStyledAttributes.getDimensionPixelSize(8, 0);
        setDividerDrawable(obtainStyledAttributes.getDrawable(5));
        this.mAllowInconsistentMeasurement = context.getApplicationInfo().targetSdkVersion <= 23;
        obtainStyledAttributes.recycle();
    }

    private boolean isShowingDividers() {
        return (this.mShowDividers == 0 || this.mDivider == null) ? false : true;
    }

    public void setShowDividers(int i) {
        if (i == this.mShowDividers) {
            return;
        }
        this.mShowDividers = i;
        setWillNotDraw(!isShowingDividers());
        requestLayout();
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable == this.mDivider) {
            return;
        }
        this.mDivider = drawable;
        if (drawable != null) {
            this.mDividerWidth = drawable.getIntrinsicWidth();
            this.mDividerHeight = drawable.getIntrinsicHeight();
        } else {
            this.mDividerWidth = 0;
            this.mDividerHeight = 0;
        }
        setWillNotDraw(!isShowingDividers());
        requestLayout();
    }

    public void setDividerPadding(int i) {
        if (i == this.mDividerPadding) {
            return;
        }
        this.mDividerPadding = i;
        if (isShowingDividers()) {
            requestLayout();
            invalidate();
        }
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mDivider == null) {
            return;
        }
        if (this.mOrientation == 1) {
            drawDividersVertical(canvas);
        } else {
            drawDividersHorizontal(canvas);
        }
    }

    void drawDividersVertical(Canvas canvas) {
        int bottom;
        int virtualChildCount = getVirtualChildCount();
        for (int i = 0; i < virtualChildCount; i++) {
            View virtualChildAt = getVirtualChildAt(i);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
                drawHorizontalDivider(canvas, (virtualChildAt.getTop() - ((LayoutParams) virtualChildAt.getLayoutParams()).topMargin) - this.mDividerHeight);
            }
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            View lastNonGoneChild = getLastNonGoneChild();
            if (lastNonGoneChild == null) {
                bottom = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
            } else {
                bottom = lastNonGoneChild.getBottom() + ((LayoutParams) lastNonGoneChild.getLayoutParams()).bottomMargin;
            }
            drawHorizontalDivider(canvas, bottom);
        }
    }

    private View getLastNonGoneChild() {
        for (int virtualChildCount = getVirtualChildCount() - 1; virtualChildCount >= 0; virtualChildCount--) {
            View virtualChildAt = getVirtualChildAt(virtualChildCount);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8) {
                return virtualChildAt;
            }
        }
        return null;
    }

    void drawDividersHorizontal(Canvas canvas) {
        int right;
        int left;
        int i;
        int left2;
        int virtualChildCount = getVirtualChildCount();
        boolean isLayoutRtl = isLayoutRtl();
        for (int i2 = 0; i2 < virtualChildCount; i2++) {
            View virtualChildAt = getVirtualChildAt(i2);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8 && hasDividerBeforeChildAt(i2)) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                if (isLayoutRtl) {
                    left2 = virtualChildAt.getRight() + layoutParams.rightMargin;
                } else {
                    left2 = (virtualChildAt.getLeft() - layoutParams.leftMargin) - this.mDividerWidth;
                }
                drawVerticalDivider(canvas, left2);
            }
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            View lastNonGoneChild = getLastNonGoneChild();
            if (lastNonGoneChild != null) {
                LayoutParams layoutParams2 = (LayoutParams) lastNonGoneChild.getLayoutParams();
                if (isLayoutRtl) {
                    left = lastNonGoneChild.getLeft() - layoutParams2.leftMargin;
                    i = this.mDividerWidth;
                    right = left - i;
                } else {
                    right = lastNonGoneChild.getRight() + layoutParams2.rightMargin;
                }
            } else if (isLayoutRtl) {
                right = getPaddingLeft();
            } else {
                left = getWidth() - getPaddingRight();
                i = this.mDividerWidth;
                right = left - i;
            }
            drawVerticalDivider(canvas, right);
        }
    }

    void drawHorizontalDivider(Canvas canvas, int i) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, i, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + i);
        this.mDivider.draw(canvas);
    }

    void drawVerticalDivider(Canvas canvas, int i) {
        this.mDivider.setBounds(i, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + i, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    public boolean isBaselineAligned() {
        return this.mBaselineAligned;
    }

    @RemotableViewMethod
    public void setBaselineAligned(boolean z) {
        this.mBaselineAligned = z;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    @RemotableViewMethod
    public void setMeasureWithLargestChildEnabled(boolean z) {
        this.mUseLargestChild = z;
    }

    @Override // android.view.View
    public int getBaseline() {
        int i;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        int childCount = getChildCount();
        int i2 = this.mBaselineAlignedChildIndex;
        if (childCount <= i2) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View childAt = getChildAt(i2);
        int baseline = childAt.getBaseline();
        if (baseline == -1) {
            if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            }
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
        int i3 = this.mBaselineChildTop;
        if (this.mOrientation == 1 && (i = this.mGravity & 112) != 48) {
            if (i == 16) {
                i3 += ((((this.mBottom - this.mTop) - this.mPaddingTop) - this.mPaddingBottom) - this.mTotalLength) / 2;
            } else if (i == 80) {
                i3 = ((this.mBottom - this.mTop) - this.mPaddingBottom) - this.mTotalLength;
            }
        }
        return i3 + ((LayoutParams) childAt.getLayoutParams()).topMargin + baseline;
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    @RemotableViewMethod
    public void setBaselineAlignedChildIndex(int i) {
        if (i < 0 || i >= getChildCount()) {
            throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mBaselineAlignedChildIndex = i;
    }

    View getVirtualChildAt(int i) {
        return getChildAt(i);
    }

    int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    @RemotableViewMethod
    public void setWeightSum(float f) {
        this.mWeightSum = Math.max(0.0f, f);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.mOrientation == 1) {
            measureVertical(i, i2);
        } else {
            measureHorizontal(i, i2);
        }
    }

    protected boolean hasDividerBeforeChildAt(int i) {
        if (this.mShowDividers == 0) {
            return false;
        }
        return i == getVirtualChildCount() ? (this.mShowDividers & 4) != 0 : allViewsAreGoneBefore(i) ? (this.mShowDividers & 1) != 0 : (this.mShowDividers & 2) != 0;
    }

    private boolean hasDividerAfterChildAt(int i) {
        if (this.mShowDividers == 0) {
            return false;
        }
        return allViewsAreGoneAfter(i) ? (this.mShowDividers & 4) != 0 : (this.mShowDividers & 2) != 0;
    }

    private boolean allViewsAreGoneBefore(int i) {
        for (int i2 = i - 1; i2 >= 0; i2--) {
            View virtualChildAt = getVirtualChildAt(i2);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8) {
                return false;
            }
        }
        return true;
    }

    private boolean allViewsAreGoneAfter(int i) {
        int virtualChildCount = getVirtualChildCount();
        for (int i2 = i + 1; i2 < virtualChildCount; i2++) {
            View virtualChildAt = getVirtualChildAt(i2);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:179:0x02b1, code lost:
    
        if (r1 == 1073741824) goto L147;
     */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0165  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void measureVertical(int r30, int r31) {
        /*
            Method dump skipped, instructions count: 920
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.LinearLayout.measureVertical(int, int):void");
    }

    private void forceUniformWidth(int i, int i2) {
        LinearLayout linearLayout;
        int i3;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        int i4 = 0;
        while (i4 < i) {
            View virtualChildAt = this.getVirtualChildAt(i4);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                if (layoutParams.width == -1) {
                    int i5 = layoutParams.height;
                    layoutParams.height = virtualChildAt.getMeasuredHeight();
                    linearLayout = this;
                    i3 = i2;
                    linearLayout.measureChildWithMargins(virtualChildAt, makeMeasureSpec, 0, i3, 0);
                    layoutParams.height = i5;
                    i4++;
                    this = linearLayout;
                    i2 = i3;
                }
            }
            linearLayout = this;
            i3 = i2;
            i4++;
            this = linearLayout;
            i2 = i3;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:227:0x0400, code lost:
    
        if (r9 == 1073741824) goto L188;
     */
    /* JADX WARN: Removed duplicated region for block: B:212:0x04aa  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x04ad  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void measureHorizontal(int r42, int r43) {
        /*
            Method dump skipped, instructions count: 1385
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.LinearLayout.measureHorizontal(int, int):void");
    }

    private void forceUniformHeight(int i, int i2) {
        LinearLayout linearLayout;
        int i3;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        int i4 = 0;
        while (i4 < i) {
            View virtualChildAt = this.getVirtualChildAt(i4);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                if (layoutParams.height == -1) {
                    int i5 = layoutParams.width;
                    layoutParams.width = virtualChildAt.getMeasuredWidth();
                    linearLayout = this;
                    i3 = i2;
                    linearLayout.measureChildWithMargins(virtualChildAt, i3, 0, makeMeasureSpec, 0);
                    layoutParams.width = i5;
                    i4++;
                    this = linearLayout;
                    i2 = i3;
                }
            }
            linearLayout = this;
            i3 = i2;
            i4++;
            this = linearLayout;
            i2 = i3;
        }
    }

    void measureChildBeforeLayout(View view, int i, int i2, int i3, int i4, int i5) {
        measureChildWithMargins(view, i2, i3, i4, i5);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mOrientation == 1) {
            layoutVertical(i, i2, i3, i4);
        } else {
            layoutHorizontal(i, i2, i3, i4);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x008b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void layoutVertical(int r12, int r13, int r14, int r15) {
        /*
            r11 = this;
            int r0 = r11.mPaddingLeft
            int r14 = r14 - r12
            int r12 = r11.mPaddingRight
            int r12 = r14 - r12
            int r14 = r14 - r0
            int r1 = r11.mPaddingRight
            int r14 = r14 - r1
            int r1 = r11.getVirtualChildCount()
            int r2 = r11.mGravity
            r3 = r2 & 112(0x70, float:1.57E-43)
            r4 = 8388615(0x800007, float:1.1754953E-38)
            r2 = r2 & r4
            r4 = 16
            if (r3 == r4) goto L2b
            r4 = 80
            if (r3 == r4) goto L22
            int r13 = r11.mPaddingTop
            goto L35
        L22:
            int r3 = r11.mPaddingTop
            int r3 = r3 + r15
            int r3 = r3 - r13
            int r13 = r11.mTotalLength
            int r13 = r3 - r13
            goto L35
        L2b:
            int r3 = r11.mPaddingTop
            int r15 = r15 - r13
            int r13 = r11.mTotalLength
            int r15 = r15 - r13
            int r15 = r15 / 2
            int r13 = r3 + r15
        L35:
            r15 = 0
        L36:
            if (r15 >= r1) goto Lae
            android.view.View r4 = r11.getVirtualChildAt(r15)
            r9 = 1
            if (r4 != 0) goto L45
            int r3 = r11.measureNullChild(r15)
            int r13 = r13 + r3
            goto Laa
        L45:
            int r3 = r4.getVisibility()
            r5 = 8
            if (r3 == r5) goto Laa
            int r7 = r4.getMeasuredWidth()
            int r8 = r4.getMeasuredHeight()
            android.view.ViewGroup$LayoutParams r3 = r4.getLayoutParams()
            r10 = r3
            android.widget.LinearLayout$LayoutParams r10 = (android.widget.LinearLayout.LayoutParams) r10
            int r3 = r10.gravity
            if (r3 >= 0) goto L61
            r3 = r2
        L61:
            int r5 = r11.getLayoutDirection()
            int r3 = android.view.Gravity.getAbsoluteGravity(r3, r5)
            r3 = r3 & 7
            if (r3 == r9) goto L79
            r5 = 5
            if (r3 == r5) goto L74
            int r3 = r10.leftMargin
            int r3 = r3 + r0
            goto L84
        L74:
            int r3 = r12 - r7
            int r5 = r10.rightMargin
            goto L83
        L79:
            int r3 = r14 - r7
            int r3 = r3 / 2
            int r3 = r3 + r0
            int r5 = r10.leftMargin
            int r3 = r3 + r5
            int r5 = r10.rightMargin
        L83:
            int r3 = r3 - r5
        L84:
            r5 = r3
            boolean r3 = r11.hasDividerBeforeChildAt(r15)
            if (r3 == 0) goto L8e
            int r3 = r11.mDividerHeight
            int r13 = r13 + r3
        L8e:
            int r3 = r10.topMargin
            int r13 = r13 + r3
            int r3 = r11.getLocationOffset(r4)
            int r6 = r13 + r3
            r3 = r11
            r3.setChildFrame(r4, r5, r6, r7, r8)
            int r11 = r10.bottomMargin
            int r8 = r8 + r11
            int r11 = r3.getNextLocationOffset(r4)
            int r8 = r8 + r11
            int r13 = r13 + r8
            int r11 = r3.getChildrenSkipCount(r4, r15)
            int r15 = r15 + r11
            goto Lab
        Laa:
            r3 = r11
        Lab:
            int r15 = r15 + r9
            r11 = r3
            goto L36
        Lae:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.LinearLayout.layoutVertical(int, int, int, int):void");
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (i != this.mLayoutDirection) {
            this.mLayoutDirection = i;
            if (this.mOrientation == 0) {
                requestLayout();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00dd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void layoutHorizontal(int r24, int r25, int r26, int r27) {
        /*
            Method dump skipped, instructions count: 322
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.LinearLayout.layoutHorizontal(int, int, int, int):void");
    }

    private void setChildFrame(View view, int i, int i2, int i3, int i4) {
        view.layout(i, i2, i3 + i, i4 + i2);
    }

    public void setOrientation(int i) {
        if (this.mOrientation != i) {
            this.mOrientation = i;
            requestLayout();
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    @RemotableViewMethod
    public void setGravity(int i) {
        if (this.mGravity != i) {
            if ((8388615 & i) == 0) {
                i |= Gravity.START;
            }
            if ((i & 112) == 0) {
                i |= 48;
            }
            this.mGravity = i;
            requestLayout();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    @RemotableViewMethod
    public void setHorizontalGravity(int i) {
        int i2 = i & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int i3 = this.mGravity;
        if ((8388615 & i3) != i2) {
            this.mGravity = i2 | ((-8388616) & i3);
            requestLayout();
        }
    }

    @RemotableViewMethod
    public void setVerticalGravity(int i) {
        int i2 = i & 112;
        int i3 = this.mGravity;
        if ((i3 & 112) != i2) {
            this.mGravity = i2 | (i3 & (-113));
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        int i = this.mOrientation;
        if (i == 0) {
            return new LayoutParams(-2, -2);
        }
        if (i == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
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

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return LinearLayout.class.getName();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("layout:baselineAligned", this.mBaselineAligned);
        viewHierarchyEncoder.addProperty("layout:baselineAlignedChildIndex", this.mBaselineAlignedChildIndex);
        viewHierarchyEncoder.addProperty("measurement:baselineChildTop", this.mBaselineChildTop);
        viewHierarchyEncoder.addProperty("measurement:orientation", this.mOrientation);
        viewHierarchyEncoder.addProperty("measurement:gravity", this.mGravity);
        viewHierarchyEncoder.addProperty("measurement:totalLength", this.mTotalLength);
        viewHierarchyEncoder.addProperty("layout:totalLength", this.mTotalLength);
        viewHierarchyEncoder.addProperty("layout:useLargestChild", this.mUseLargestChild);
    }
}
