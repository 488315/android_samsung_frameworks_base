package android.widget;

import android.app.slice.Slice;
import android.content.Context;
import android.content.res.Resources;
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
import java.io.IOException;
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
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LinearLayout_Layout);
            this.weight = typedArrayObtainStyledAttributes.getFloat(3, 0.0f);
            this.gravity = typedArrayObtainStyledAttributes.getInt(0, -1);
            typedArrayObtainStyledAttributes.recycle();
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
        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) throws IOException {
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
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LinearLayout, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.LinearLayout, attributeSet, typedArrayObtainStyledAttributes, i, i2);
        int i3 = typedArrayObtainStyledAttributes.getInt(1, -1);
        if (i3 >= 0) {
            setOrientation(i3);
        }
        int i4 = typedArrayObtainStyledAttributes.getInt(0, -1);
        if (i4 >= 0) {
            setGravity(i4);
        }
        boolean z = typedArrayObtainStyledAttributes.getBoolean(2, true);
        if (!z) {
            setBaselineAligned(z);
        }
        this.mWeightSum = typedArrayObtainStyledAttributes.getFloat(4, -1.0f);
        this.mBaselineAlignedChildIndex = typedArrayObtainStyledAttributes.getInt(3, -1);
        this.mUseLargestChild = typedArrayObtainStyledAttributes.getBoolean(6, false);
        this.mShowDividers = typedArrayObtainStyledAttributes.getInt(7, 0);
        this.mDividerPadding = typedArrayObtainStyledAttributes.getDimensionPixelSize(8, 0);
        setDividerDrawable(typedArrayObtainStyledAttributes.getDrawable(5));
        this.mAllowInconsistentMeasurement = context.getApplicationInfo().targetSdkVersion <= 23;
        typedArrayObtainStyledAttributes.recycle();
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
        boolean zIsLayoutRtl = isLayoutRtl();
        for (int i2 = 0; i2 < virtualChildCount; i2++) {
            View virtualChildAt = getVirtualChildAt(i2);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8 && hasDividerBeforeChildAt(i2)) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                if (zIsLayoutRtl) {
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
                if (zIsLayoutRtl) {
                    left = lastNonGoneChild.getLeft() - layoutParams2.leftMargin;
                    i = this.mDividerWidth;
                    right = left - i;
                } else {
                    right = lastNonGoneChild.getRight() + layoutParams2.rightMargin;
                }
            } else if (zIsLayoutRtl) {
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

    /* JADX WARN: Removed duplicated region for block: B:139:0x02a7  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0332  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0165  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void measureVertical(int i, int i2) {
        int i3;
        int iMax;
        int i4;
        int i5;
        float f;
        int i6;
        int i7;
        int i8;
        int measuredHeight;
        View view;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        boolean z;
        int i15;
        View view2;
        boolean z2;
        int i16;
        int iMax2;
        int i17;
        int i18;
        this.mTotalLength = 0;
        int virtualChildCount = getVirtualChildCount();
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int i19 = this.mBaselineAlignedChildIndex;
        boolean z3 = this.mUseLargestChild;
        int i20 = 0;
        int childrenSkipCount = 0;
        int iMax3 = 0;
        int i21 = 0;
        int iMax4 = 0;
        int iCombineMeasuredStates = 0;
        int i22 = 0;
        boolean z4 = false;
        boolean z5 = false;
        float f2 = 0.0f;
        int iMax5 = Integer.MIN_VALUE;
        boolean z6 = true;
        while (true) {
            int i23 = 8;
            if (childrenSkipCount < virtualChildCount) {
                int i24 = i20;
                View virtualChildAt = getVirtualChildAt(childrenSkipCount);
                if (virtualChildAt == null) {
                    this.mTotalLength += measureNullChild(childrenSkipCount);
                } else if (virtualChildAt.getVisibility() == 8) {
                    childrenSkipCount += getChildrenSkipCount(virtualChildAt, childrenSkipCount);
                } else {
                    int i25 = i24 + 1;
                    if (hasDividerBeforeChildAt(childrenSkipCount)) {
                        view = virtualChildAt;
                        this.mTotalLength += this.mDividerHeight;
                    } else {
                        view = virtualChildAt;
                    }
                    LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                    float f3 = f2 + layoutParams.weight;
                    boolean z7 = layoutParams.height == 0 && layoutParams.weight > 0.0f;
                    if (mode2 == 1073741824 && z7) {
                        int i26 = this.mTotalLength;
                        this.mTotalLength = Math.max(i26, layoutParams.topMargin + i26 + layoutParams.bottomMargin);
                        i11 = i25;
                        z4 = true;
                        view2 = view;
                        i13 = childrenSkipCount;
                        i12 = iMax3;
                        i14 = mode2;
                        z = z3;
                        i15 = i21;
                    } else {
                        int i27 = childrenSkipCount;
                        if (z7) {
                            layoutParams.height = -2;
                        }
                        if (f3 == 0.0f) {
                            int i28 = iMax5;
                            i10 = this.mTotalLength;
                            i9 = i28;
                        } else {
                            i9 = iMax5;
                            i10 = 0;
                        }
                        i11 = i25;
                        i12 = iMax3;
                        i13 = i27;
                        i14 = mode2;
                        int i29 = i9;
                        View view3 = view;
                        z = z3;
                        i15 = i21;
                        measureChildBeforeLayout(view3, i13, i, 0, i2, i10);
                        view2 = view3;
                        int measuredHeight2 = view2.getMeasuredHeight();
                        if (z7) {
                            layoutParams.height = 0;
                            i22 += measuredHeight2;
                        }
                        int i30 = this.mTotalLength;
                        this.mTotalLength = Math.max(i30, i30 + measuredHeight2 + layoutParams.topMargin + layoutParams.bottomMargin + getNextLocationOffset(view2));
                        iMax5 = z ? Math.max(measuredHeight2, i29) : i29;
                    }
                    if (i19 >= 0 && i19 == i13 + 1) {
                        this.mBaselineChildTop = this.mTotalLength;
                    }
                    if (i13 < i19 && layoutParams.weight > 0.0f) {
                        throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                    }
                    if (mode == 1073741824 || layoutParams.width != -1) {
                        z2 = false;
                    } else {
                        z2 = true;
                        z5 = true;
                    }
                    int i31 = layoutParams.leftMargin + layoutParams.rightMargin;
                    int measuredWidth = view2.getMeasuredWidth() + i31;
                    iMax4 = Math.max(iMax4, measuredWidth);
                    boolean z8 = z2;
                    iCombineMeasuredStates = combineMeasuredStates(iCombineMeasuredStates, view2.getMeasuredState());
                    if (z6) {
                        i16 = i31;
                        z6 = layoutParams.width == -1;
                        if (layoutParams.weight <= 0.0f) {
                            iMax2 = Math.max(i15, z8 ? i16 : measuredWidth);
                            iMax3 = i12;
                        } else {
                            iMax3 = Math.max(i12, z8 ? i16 : measuredWidth);
                            iMax2 = i15;
                        }
                        childrenSkipCount = i13 + getChildrenSkipCount(view2, i13);
                        i17 = iMax5;
                        f2 = f3;
                        i18 = iMax2;
                        i20 = i11;
                        childrenSkipCount++;
                        i21 = i18;
                        iMax5 = i17;
                        z3 = z;
                        mode2 = i14;
                    } else {
                        i16 = i31;
                    }
                    if (layoutParams.weight <= 0.0f) {
                    }
                    childrenSkipCount = i13 + getChildrenSkipCount(view2, i13);
                    i17 = iMax5;
                    f2 = f3;
                    i18 = iMax2;
                    i20 = i11;
                    childrenSkipCount++;
                    i21 = i18;
                    iMax5 = i17;
                    z3 = z;
                    mode2 = i14;
                }
                i17 = iMax5;
                i14 = mode2;
                z = z3;
                i20 = i24;
                i18 = i21;
                childrenSkipCount++;
                i21 = i18;
                iMax5 = i17;
                z3 = z;
                mode2 = i14;
            } else {
                int i32 = iMax3;
                int i33 = mode2;
                boolean z9 = z3;
                int i34 = i21;
                int i35 = iMax5;
                if (i20 > 0 && hasDividerBeforeChildAt(virtualChildCount)) {
                    this.mTotalLength += this.mDividerHeight;
                }
                int i36 = i33;
                if (z9 && (i36 == Integer.MIN_VALUE || i36 == 0)) {
                    this.mTotalLength = 0;
                    int childrenSkipCount2 = 0;
                    while (childrenSkipCount2 < virtualChildCount) {
                        View virtualChildAt2 = getVirtualChildAt(childrenSkipCount2);
                        if (virtualChildAt2 == null) {
                            this.mTotalLength += measureNullChild(childrenSkipCount2);
                        } else if (virtualChildAt2.getVisibility() == i23) {
                            childrenSkipCount2 += getChildrenSkipCount(virtualChildAt2, childrenSkipCount2);
                        } else {
                            LayoutParams layoutParams2 = (LayoutParams) virtualChildAt2.getLayoutParams();
                            int i37 = this.mTotalLength;
                            this.mTotalLength = Math.max(i37, i37 + i35 + layoutParams2.topMargin + layoutParams2.bottomMargin + getNextLocationOffset(virtualChildAt2));
                        }
                        childrenSkipCount2++;
                        i23 = 8;
                    }
                }
                int i38 = this.mTotalLength + this.mPaddingTop + this.mPaddingBottom;
                this.mTotalLength = i38;
                int iResolveSizeAndState = resolveSizeAndState(Math.max(i38, getSuggestedMinimumHeight()), i2, 0);
                int i39 = (16777215 & iResolveSizeAndState) - this.mTotalLength;
                if (this.mAllowInconsistentMeasurement) {
                    i22 = 0;
                }
                int i40 = i39 + i22;
                if (z4 || ((sRemeasureWeightedChildren || i40 != 0) && f2 > 0.0f)) {
                    float f4 = this.mWeightSum;
                    if (f4 > 0.0f) {
                        f2 = f4;
                    }
                    this.mTotalLength = 0;
                    int i41 = i40;
                    int i42 = i32;
                    int i43 = 0;
                    while (i43 < virtualChildCount) {
                        View virtualChildAt3 = getVirtualChildAt(i43);
                        if (virtualChildAt3 == null || virtualChildAt3.getVisibility() == 8) {
                            i4 = i36;
                            i5 = i43;
                            i41 = i41;
                            f2 = f2;
                        } else {
                            LayoutParams layoutParams3 = (LayoutParams) virtualChildAt3.getLayoutParams();
                            float f5 = layoutParams3.weight;
                            if (f5 > 0.0f) {
                                float f6 = f2;
                                int i44 = (int) ((i41 * f5) / f6);
                                int i45 = i41 - i44;
                                float f7 = f6 - f5;
                                if (this.mUseLargestChild) {
                                    int i46 = 1073741824;
                                    if (i36 != 1073741824) {
                                        i4 = i36;
                                        i8 = i45;
                                        measuredHeight = i35;
                                    } else {
                                        if (layoutParams3.height == 0) {
                                            if (this.mAllowInconsistentMeasurement) {
                                                i46 = 1073741824;
                                                if (i36 == 1073741824) {
                                                }
                                            } else {
                                                i46 = 1073741824;
                                            }
                                            i4 = i36;
                                            measuredHeight = i44;
                                            i8 = i45;
                                        } else {
                                            i46 = 1073741824;
                                        }
                                        i4 = i36;
                                        i8 = i45;
                                        measuredHeight = virtualChildAt3.getMeasuredHeight() + i44;
                                    }
                                    i5 = i43;
                                    virtualChildAt3.measure(getChildMeasureSpec(i, this.mPaddingLeft + this.mPaddingRight + layoutParams3.leftMargin + layoutParams3.rightMargin, layoutParams3.width), View.MeasureSpec.makeMeasureSpec(Math.max(0, measuredHeight), i46));
                                    iCombineMeasuredStates = combineMeasuredStates(iCombineMeasuredStates, virtualChildAt3.getMeasuredState() & (-256));
                                    f2 = f7;
                                    i41 = i8;
                                }
                            } else {
                                i4 = i36;
                                i5 = i43;
                            }
                            int i47 = layoutParams3.leftMargin + layoutParams3.rightMargin;
                            int measuredWidth2 = virtualChildAt3.getMeasuredWidth() + i47;
                            iMax4 = Math.max(iMax4, measuredWidth2);
                            if (mode != 1073741824) {
                                f = f2;
                                i6 = -1;
                                if (layoutParams3.width == -1) {
                                    i7 = i47;
                                }
                                int iMax6 = Math.max(i42, i7);
                                boolean z10 = !z6 && layoutParams3.width == i6;
                                int i48 = this.mTotalLength;
                                this.mTotalLength = Math.max(i48, i48 + virtualChildAt3.getMeasuredHeight() + layoutParams3.topMargin + layoutParams3.bottomMargin + getNextLocationOffset(virtualChildAt3));
                                z6 = z10;
                                f2 = f;
                                i42 = iMax6;
                            } else {
                                f = f2;
                                i6 = -1;
                            }
                            i7 = measuredWidth2;
                            int iMax62 = Math.max(i42, i7);
                            if (z6) {
                                int i482 = this.mTotalLength;
                                this.mTotalLength = Math.max(i482, i482 + virtualChildAt3.getMeasuredHeight() + layoutParams3.topMargin + layoutParams3.bottomMargin + getNextLocationOffset(virtualChildAt3));
                                z6 = z10;
                                f2 = f;
                                i42 = iMax62;
                            }
                        }
                        i43 = i5 + 1;
                        i36 = i4;
                    }
                    i3 = i;
                    this.mTotalLength += this.mPaddingTop + this.mPaddingBottom;
                    iMax = i42;
                } else {
                    iMax = Math.max(i32, i34);
                    if (z9 && i36 != 1073741824) {
                        for (int i49 = 0; i49 < virtualChildCount; i49++) {
                            View virtualChildAt4 = getVirtualChildAt(i49);
                            if (virtualChildAt4 != null && virtualChildAt4.getVisibility() != 8 && ((LayoutParams) virtualChildAt4.getLayoutParams()).weight > 0.0f) {
                                virtualChildAt4.measure(View.MeasureSpec.makeMeasureSpec(virtualChildAt4.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(i35, 1073741824));
                            }
                        }
                    }
                    i3 = i;
                }
                if (z6 || mode == 1073741824) {
                    iMax = iMax4;
                }
                setMeasuredDimension(resolveSizeAndState(Math.max(iMax + this.mPaddingLeft + this.mPaddingRight, getSuggestedMinimumWidth()), i3, iCombineMeasuredStates), iResolveSizeAndState);
                if (z5) {
                    forceUniformWidth(virtualChildCount, i2);
                    return;
                }
                return;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0038  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void forceUniformWidth(int i, int i2) {
        LinearLayout linearLayout;
        int i3;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        int i4 = 0;
        while (i4 < i) {
            View virtualChildAt = this.getVirtualChildAt(i4);
            if (virtualChildAt == null || virtualChildAt.getVisibility() == 8) {
                linearLayout = this;
                i3 = i2;
            } else {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                if (layoutParams.width == -1) {
                    int i5 = layoutParams.height;
                    layoutParams.height = virtualChildAt.getMeasuredHeight();
                    linearLayout = this;
                    i3 = i2;
                    linearLayout.measureChildWithMargins(virtualChildAt, iMakeMeasureSpec, 0, i3, 0);
                    layoutParams.height = i5;
                }
            }
            i4++;
            this = linearLayout;
            i2 = i3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:178:0x03f6  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x04aa  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x04ad  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void measureHorizontal(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int iMax;
        int i8;
        int i9;
        int i10;
        int i11;
        int baseline;
        int i12;
        int i13;
        int i14;
        int i15;
        LayoutParams layoutParams;
        int i16;
        LayoutParams layoutParams2;
        boolean z;
        int[] iArr;
        int i17;
        boolean z2;
        int baseline2;
        boolean z3 = false;
        this.mTotalLength = 0;
        int virtualChildCount = getVirtualChildCount();
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        if (this.mMaxAscent == null || this.mMaxDescent == null) {
            this.mMaxAscent = new int[4];
            this.mMaxDescent = new int[4];
        }
        int[] iArr2 = this.mMaxAscent;
        int[] iArr3 = this.mMaxDescent;
        iArr2[3] = -1;
        iArr2[2] = -1;
        iArr2[1] = -1;
        iArr2[0] = -1;
        iArr3[3] = -1;
        iArr3[2] = -1;
        iArr3[1] = -1;
        iArr3[0] = -1;
        boolean z4 = this.mBaselineAligned;
        boolean z5 = this.mUseLargestChild;
        int i18 = 1073741824;
        boolean z6 = mode == 1073741824;
        boolean z7 = z5;
        int childrenSkipCount = 0;
        int i19 = 0;
        int i20 = 0;
        int iMax2 = 0;
        boolean z8 = false;
        int iCombineMeasuredStates = 0;
        boolean z9 = false;
        boolean z10 = true;
        float f = 0.0f;
        int iMax3 = Integer.MIN_VALUE;
        int iMax4 = 0;
        int iMax5 = 0;
        while (true) {
            i3 = i19;
            if (childrenSkipCount >= virtualChildCount) {
                break;
            }
            boolean z11 = z4;
            View virtualChildAt = getVirtualChildAt(childrenSkipCount);
            if (virtualChildAt == null) {
                this.mTotalLength += measureNullChild(childrenSkipCount);
            } else if (virtualChildAt.getVisibility() == 8) {
                childrenSkipCount += getChildrenSkipCount(virtualChildAt, childrenSkipCount);
            } else {
                int i21 = i3 + 1;
                if (hasDividerBeforeChildAt(childrenSkipCount)) {
                    this.mTotalLength += this.mDividerWidth;
                }
                LayoutParams layoutParams3 = (LayoutParams) virtualChildAt.getLayoutParams();
                f += layoutParams3.weight;
                boolean z12 = (layoutParams3.width != 0 || layoutParams3.weight <= 0.0f) ? z3 : true;
                if (mode == i18 && z12) {
                    if (z6) {
                        this.mTotalLength += layoutParams3.leftMargin + layoutParams3.rightMargin;
                    } else {
                        int i22 = this.mTotalLength;
                        this.mTotalLength = Math.max(i22, layoutParams3.leftMargin + i22 + layoutParams3.rightMargin);
                    }
                    if (z11) {
                        virtualChildAt.measure(View.MeasureSpec.makeSafeMeasureSpec(View.MeasureSpec.getSize(i), 0), View.MeasureSpec.makeSafeMeasureSpec(View.MeasureSpec.getSize(i2), 0));
                        layoutParams2 = layoutParams3;
                        z = z7;
                        iArr = iArr2;
                    } else {
                        layoutParams2 = layoutParams3;
                        z = z7;
                        iArr = iArr2;
                        z8 = true;
                    }
                    i17 = 1073741824;
                } else {
                    if (z12) {
                        layoutParams3.width = -2;
                    }
                    if (f == 0.0f) {
                        layoutParams = layoutParams3;
                        i16 = this.mTotalLength;
                    } else {
                        layoutParams = layoutParams3;
                        i16 = 0;
                    }
                    layoutParams2 = layoutParams;
                    z = z7;
                    iArr = iArr2;
                    measureChildBeforeLayout(virtualChildAt, childrenSkipCount, i, i16, i2, 0);
                    int measuredWidth = virtualChildAt.getMeasuredWidth();
                    if (z12) {
                        layoutParams2.width = 0;
                        i20 += measuredWidth;
                    }
                    if (z6) {
                        this.mTotalLength += layoutParams2.leftMargin + measuredWidth + layoutParams2.rightMargin + getNextLocationOffset(virtualChildAt);
                    } else {
                        int i23 = this.mTotalLength;
                        this.mTotalLength = Math.max(i23, i23 + measuredWidth + layoutParams2.leftMargin + layoutParams2.rightMargin + getNextLocationOffset(virtualChildAt));
                    }
                    if (z) {
                        iMax3 = Math.max(measuredWidth, iMax3);
                    }
                    i17 = 1073741824;
                }
                if (mode2 == i17 || layoutParams2.height != -1) {
                    z2 = false;
                } else {
                    z2 = true;
                    z9 = true;
                }
                int i24 = layoutParams2.topMargin + layoutParams2.bottomMargin;
                int measuredHeight = virtualChildAt.getMeasuredHeight() + i24;
                boolean z13 = z2;
                iCombineMeasuredStates = combineMeasuredStates(iCombineMeasuredStates, virtualChildAt.getMeasuredState());
                if (z11 && (baseline2 = virtualChildAt.getBaseline()) != -1) {
                    int i25 = ((((layoutParams2.gravity < 0 ? this.mGravity : layoutParams2.gravity) & 112) >> 4) & (-2)) >> 1;
                    iArr[i25] = Math.max(iArr[i25], baseline2);
                    iArr3[i25] = Math.max(iArr3[i25], measuredHeight - baseline2);
                }
                iMax4 = Math.max(iMax4, measuredHeight);
                z10 = z10 && layoutParams2.height == -1;
                if (layoutParams2.weight > 0.0f) {
                    iMax2 = Math.max(iMax2, z13 ? i24 : measuredHeight);
                } else {
                    int i26 = iMax2;
                    iMax5 = Math.max(iMax5, z13 ? i24 : measuredHeight);
                    iMax2 = i26;
                }
                childrenSkipCount += getChildrenSkipCount(virtualChildAt, childrenSkipCount);
                i19 = i21;
                childrenSkipCount++;
                z7 = z;
                z4 = z11;
                iArr2 = iArr;
                i18 = 1073741824;
                z3 = false;
            }
            z = z7;
            iArr = iArr2;
            i19 = i3;
            childrenSkipCount++;
            z7 = z;
            z4 = z11;
            iArr2 = iArr;
            i18 = 1073741824;
            z3 = false;
        }
        boolean z14 = z4;
        boolean z15 = z7;
        int[] iArr4 = iArr2;
        int i27 = iMax2;
        int i28 = iCombineMeasuredStates;
        if (i3 > 0 && hasDividerBeforeChildAt(virtualChildCount)) {
            this.mTotalLength += this.mDividerWidth;
        }
        int i29 = iArr4[1];
        if (i29 != -1 || iArr4[0] != -1 || iArr4[2] != -1 || iArr4[3] != -1) {
            iMax4 = Math.max(iMax4, Math.max(iArr4[3], Math.max(iArr4[0], Math.max(i29, iArr4[2]))) + Math.max(iArr3[3], Math.max(iArr3[0], Math.max(iArr3[1], iArr3[2]))));
        }
        if (z15 && (mode == Integer.MIN_VALUE || mode == 0)) {
            this.mTotalLength = 0;
            int childrenSkipCount2 = 0;
            int i30 = 0;
            while (childrenSkipCount2 < virtualChildCount) {
                View virtualChildAt2 = getVirtualChildAt(childrenSkipCount2);
                if (virtualChildAt2 == null) {
                    this.mTotalLength += measureNullChild(childrenSkipCount2);
                    i15 = i28;
                } else {
                    int i31 = i30;
                    if (virtualChildAt2.getVisibility() == 8) {
                        childrenSkipCount2 += getChildrenSkipCount(virtualChildAt2, childrenSkipCount2);
                        i15 = i28;
                        i30 = i31;
                    } else {
                        int i32 = i31 + 1;
                        if (hasDividerBeforeChildAt(childrenSkipCount2)) {
                            i13 = childrenSkipCount2;
                            this.mTotalLength += this.mDividerWidth;
                        } else {
                            i13 = childrenSkipCount2;
                        }
                        LayoutParams layoutParams4 = (LayoutParams) virtualChildAt2.getLayoutParams();
                        if (z6) {
                            i14 = i32;
                            this.mTotalLength += layoutParams4.leftMargin + iMax3 + layoutParams4.rightMargin + getNextLocationOffset(virtualChildAt2);
                            i15 = i28;
                        } else {
                            i14 = i32;
                            int i33 = this.mTotalLength;
                            i15 = i28;
                            this.mTotalLength = Math.max(i33, i33 + iMax3 + layoutParams4.leftMargin + layoutParams4.rightMargin + getNextLocationOffset(virtualChildAt2));
                        }
                        i30 = i14;
                        childrenSkipCount2 = i13;
                    }
                }
                childrenSkipCount2++;
                i28 = i15;
            }
            i4 = i28;
            if (i30 > 0 && hasDividerBeforeChildAt(virtualChildCount)) {
                this.mTotalLength += this.mDividerWidth;
            }
        } else {
            i4 = i28;
        }
        int i34 = this.mTotalLength + this.mPaddingLeft + this.mPaddingRight;
        this.mTotalLength = i34;
        int iResolveSizeAndState = resolveSizeAndState(Math.max(i34, getSuggestedMinimumWidth()), i, 0);
        int i35 = (16777215 & iResolveSizeAndState) - this.mTotalLength;
        if (this.mAllowInconsistentMeasurement) {
            i20 = 0;
        }
        int i36 = i35 + i20;
        if (z8 || ((sRemeasureWeightedChildren || i36 != 0) && f > 0.0f)) {
            float f2 = this.mWeightSum;
            if (f2 > 0.0f) {
                f = f2;
            }
            iArr4[3] = -1;
            iArr4[2] = -1;
            iArr4[1] = -1;
            iArr4[0] = -1;
            iArr3[3] = -1;
            iArr3[2] = -1;
            iArr3[1] = -1;
            iArr3[0] = -1;
            this.mTotalLength = 0;
            int iCombineMeasuredStates2 = i4;
            int iMax6 = -1;
            int i37 = 0;
            int i38 = 0;
            while (i37 < virtualChildCount) {
                View virtualChildAt3 = getVirtualChildAt(i37);
                int i39 = iResolveSizeAndState;
                if (virtualChildAt3 != null) {
                    i8 = i38;
                    if (virtualChildAt3.getVisibility() != 8) {
                        int i40 = i8 + 1;
                        if (hasDividerBeforeChildAt(i37)) {
                            i8 = i40;
                            this.mTotalLength += this.mDividerWidth;
                        } else {
                            i8 = i40;
                        }
                        LayoutParams layoutParams5 = (LayoutParams) virtualChildAt3.getLayoutParams();
                        float f3 = layoutParams5.weight;
                        if (f3 > 0.0f) {
                            i9 = i37;
                            int measuredWidth2 = (int) ((i36 * f3) / f);
                            int i41 = i36 - measuredWidth2;
                            f -= f3;
                            if (this.mUseLargestChild) {
                                int i42 = 1073741824;
                                if (mode != 1073741824) {
                                    i12 = i41;
                                    measuredWidth2 = iMax3;
                                } else {
                                    if (layoutParams5.width == 0) {
                                        if (this.mAllowInconsistentMeasurement) {
                                            i42 = 1073741824;
                                            if (mode != 1073741824) {
                                            }
                                        } else {
                                            i42 = 1073741824;
                                        }
                                        i12 = i41;
                                    } else {
                                        i42 = 1073741824;
                                    }
                                    measuredWidth2 = virtualChildAt3.getMeasuredWidth() + measuredWidth2;
                                    i12 = i41;
                                }
                                virtualChildAt3.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, measuredWidth2), i42), getChildMeasureSpec(i2, this.mPaddingTop + this.mPaddingBottom + layoutParams5.topMargin + layoutParams5.bottomMargin, layoutParams5.height));
                                iCombineMeasuredStates2 = combineMeasuredStates(iCombineMeasuredStates2, virtualChildAt3.getMeasuredState() & (-16777216));
                                i36 = i12;
                            }
                        } else {
                            i9 = i37;
                        }
                        if (z6) {
                            i10 = i36;
                            this.mTotalLength += virtualChildAt3.getMeasuredWidth() + layoutParams5.leftMargin + layoutParams5.rightMargin + getNextLocationOffset(virtualChildAt3);
                        } else {
                            i10 = i36;
                            int i43 = this.mTotalLength;
                            this.mTotalLength = Math.max(i43, virtualChildAt3.getMeasuredWidth() + i43 + layoutParams5.leftMargin + layoutParams5.rightMargin + getNextLocationOffset(virtualChildAt3));
                        }
                        boolean z16 = mode2 != 1073741824 && layoutParams5.height == -1;
                        int i44 = layoutParams5.topMargin + layoutParams5.bottomMargin;
                        int measuredHeight2 = virtualChildAt3.getMeasuredHeight() + i44;
                        iMax6 = Math.max(iMax6, measuredHeight2);
                        if (!z16) {
                            i44 = measuredHeight2;
                        }
                        int iMax7 = Math.max(iMax5, i44);
                        if (z10) {
                            i11 = -1;
                            boolean z17 = layoutParams5.height == -1;
                            if (z14 && (baseline = virtualChildAt3.getBaseline()) != i11) {
                                int i45 = ((((layoutParams5.gravity >= 0 ? this.mGravity : layoutParams5.gravity) & 112) >> 4) & (-2)) >> 1;
                                iArr4[i45] = Math.max(iArr4[i45], baseline);
                                iArr3[i45] = Math.max(iArr3[i45], measuredHeight2 - baseline);
                            }
                            iMax5 = iMax7;
                            z10 = z17;
                            i36 = i10;
                        } else {
                            i11 = -1;
                        }
                        if (z14) {
                            int i452 = ((((layoutParams5.gravity >= 0 ? this.mGravity : layoutParams5.gravity) & 112) >> 4) & (-2)) >> 1;
                            iArr4[i452] = Math.max(iArr4[i452], baseline);
                            iArr3[i452] = Math.max(iArr3[i452], measuredHeight2 - baseline);
                        }
                        iMax5 = iMax7;
                        z10 = z17;
                        i36 = i10;
                    }
                    i38 = i8;
                    i37 = i9 + 1;
                    iResolveSizeAndState = i39;
                } else {
                    i8 = i38;
                }
                i9 = i37;
                i38 = i8;
                i37 = i9 + 1;
                iResolveSizeAndState = i39;
            }
            i5 = iResolveSizeAndState;
            i6 = -16777216;
            if (i38 > 0 && hasDividerBeforeChildAt(virtualChildCount)) {
                this.mTotalLength += this.mDividerWidth;
            }
            this.mTotalLength += this.mPaddingLeft + this.mPaddingRight;
            int i46 = iArr4[1];
            iMax4 = (i46 == -1 && iArr4[0] == -1 && iArr4[2] == -1 && iArr4[3] == -1) ? iMax6 : Math.max(iMax6, Math.max(iArr4[3], Math.max(iArr4[0], Math.max(i46, iArr4[2]))) + Math.max(iArr3[3], Math.max(iArr3[0], Math.max(iArr3[1], iArr3[2]))));
            i7 = iCombineMeasuredStates2;
            iMax = iMax5;
        } else {
            iMax = Math.max(iMax5, i27);
            if (z15 && mode != 1073741824) {
                for (int i47 = 0; i47 < virtualChildCount; i47++) {
                    View virtualChildAt4 = getVirtualChildAt(i47);
                    if (virtualChildAt4 != null && virtualChildAt4.getVisibility() != 8 && ((LayoutParams) virtualChildAt4.getLayoutParams()).weight > 0.0f) {
                        virtualChildAt4.measure(View.MeasureSpec.makeMeasureSpec(iMax3, 1073741824), View.MeasureSpec.makeMeasureSpec(virtualChildAt4.getMeasuredHeight(), 1073741824));
                    }
                }
            }
            i5 = iResolveSizeAndState;
            i7 = i4;
            i6 = -16777216;
        }
        if (z10 || mode2 == 1073741824) {
            iMax = iMax4;
        }
        setMeasuredDimension(i5 | (i7 & i6), resolveSizeAndState(Math.max(iMax + this.mPaddingTop + this.mPaddingBottom, getSuggestedMinimumHeight()), i2, i7 << 16));
        if (z9) {
            forceUniformHeight(virtualChildCount, i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0038  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void forceUniformHeight(int i, int i2) {
        LinearLayout linearLayout;
        int i3;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        int i4 = 0;
        while (i4 < i) {
            View virtualChildAt = this.getVirtualChildAt(i4);
            if (virtualChildAt == null || virtualChildAt.getVisibility() == 8) {
                linearLayout = this;
                i3 = i2;
            } else {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                if (layoutParams.height == -1) {
                    int i5 = layoutParams.width;
                    layoutParams.width = virtualChildAt.getMeasuredWidth();
                    linearLayout = this;
                    i3 = i2;
                    linearLayout.measureChildWithMargins(virtualChildAt, i3, 0, iMakeMeasureSpec, 0);
                    layoutParams.width = i5;
                }
            }
            i4++;
            this = linearLayout;
            i2 = i3;
        }
    }

    void measureChildBeforeLayout(View view, int i, int i2, int i3, int i4, int i5) {
        measureChildWithMargins(view, i2, i3, i4, i5);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        if (this.mOrientation == 1) {
            layoutVertical(i, i2, i3, i4);
        } else {
            layoutHorizontal(i, i2, i3, i4);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x008b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void layoutVertical(int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        int nextLocationOffset;
        int i5;
        int i6;
        int i7;
        LinearLayout linearLayout;
        int i8 = this.mPaddingLeft;
        int i9 = i3 - i;
        int i10 = i9 - this.mPaddingRight;
        int i11 = (i9 - i8) - this.mPaddingRight;
        int virtualChildCount = getVirtualChildCount();
        int i12 = this.mGravity;
        int i13 = i12 & 112;
        int i14 = i12 & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i13 == 16) {
            nextLocationOffset = this.mPaddingTop + (((i4 - i2) - this.mTotalLength) / 2);
        } else if (i13 == 80) {
            nextLocationOffset = ((this.mPaddingTop + i4) - i2) - this.mTotalLength;
        } else {
            nextLocationOffset = this.mPaddingTop;
        }
        int childrenSkipCount = 0;
        while (childrenSkipCount < virtualChildCount) {
            View virtualChildAt = this.getVirtualChildAt(childrenSkipCount);
            if (virtualChildAt == null) {
                nextLocationOffset += this.measureNullChild(childrenSkipCount);
            } else {
                if (virtualChildAt.getVisibility() != 8) {
                    int measuredWidth = virtualChildAt.getMeasuredWidth();
                    int measuredHeight = virtualChildAt.getMeasuredHeight();
                    LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                    int i15 = layoutParams.gravity;
                    if (i15 < 0) {
                        i15 = i14;
                    }
                    int absoluteGravity = Gravity.getAbsoluteGravity(i15, this.getLayoutDirection()) & 7;
                    if (absoluteGravity == 1) {
                        i5 = ((i11 - measuredWidth) / 2) + i8 + layoutParams.leftMargin;
                        i6 = layoutParams.rightMargin;
                    } else if (absoluteGravity == 5) {
                        i5 = i10 - measuredWidth;
                        i6 = layoutParams.rightMargin;
                    } else {
                        i7 = layoutParams.leftMargin + i8;
                        int i16 = i7;
                        if (this.hasDividerBeforeChildAt(childrenSkipCount)) {
                            nextLocationOffset += this.mDividerHeight;
                        }
                        int i17 = nextLocationOffset + layoutParams.topMargin;
                        linearLayout = this;
                        linearLayout.setChildFrame(virtualChildAt, i16, i17 + this.getLocationOffset(virtualChildAt), measuredWidth, measuredHeight);
                        nextLocationOffset = i17 + measuredHeight + layoutParams.bottomMargin + linearLayout.getNextLocationOffset(virtualChildAt);
                        childrenSkipCount += linearLayout.getChildrenSkipCount(virtualChildAt, childrenSkipCount);
                    }
                    i7 = i5 - i6;
                    int i162 = i7;
                    if (this.hasDividerBeforeChildAt(childrenSkipCount)) {
                    }
                    int i172 = nextLocationOffset + layoutParams.topMargin;
                    linearLayout = this;
                    linearLayout.setChildFrame(virtualChildAt, i162, i172 + this.getLocationOffset(virtualChildAt), measuredWidth, measuredHeight);
                    nextLocationOffset = i172 + measuredHeight + layoutParams.bottomMargin + linearLayout.getNextLocationOffset(virtualChildAt);
                    childrenSkipCount += linearLayout.getChildrenSkipCount(virtualChildAt, childrenSkipCount);
                }
                childrenSkipCount++;
                this = linearLayout;
            }
            linearLayout = this;
            childrenSkipCount++;
            this = linearLayout;
        }
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

    /* JADX WARN: Removed duplicated region for block: B:28:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void layoutHorizontal(int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        int iMeasureNullChild;
        int i5;
        int i6;
        char c;
        boolean z;
        boolean z2;
        int i7;
        int i8;
        int i9;
        int i10;
        int measuredHeight;
        int i11;
        int i12;
        int i13;
        boolean zIsLayoutRtl = isLayoutRtl();
        int i14 = this.mPaddingTop;
        int i15 = i4 - i2;
        int i16 = i15 - this.mPaddingBottom;
        int i17 = (i15 - i14) - this.mPaddingBottom;
        int virtualChildCount = getVirtualChildCount();
        int i18 = this.mGravity;
        int i19 = i18 & 112;
        boolean z3 = this.mBaselineAligned;
        int[] iArr = this.mMaxAscent;
        int[] iArr2 = this.mMaxDescent;
        int absoluteGravity = Gravity.getAbsoluteGravity(8388615 & i18, getLayoutDirection());
        char c2 = 2;
        boolean z4 = true;
        if (absoluteGravity == 1) {
            iMeasureNullChild = this.mPaddingLeft + (((i3 - i) - this.mTotalLength) / 2);
        } else if (absoluteGravity == 5) {
            iMeasureNullChild = ((this.mPaddingLeft + i3) - i) - this.mTotalLength;
        } else {
            iMeasureNullChild = this.mPaddingLeft;
        }
        int childrenSkipCount = 0;
        if (zIsLayoutRtl) {
            i6 = virtualChildCount - 1;
            i5 = -1;
        } else {
            i5 = 1;
            i6 = 0;
        }
        while (childrenSkipCount < virtualChildCount) {
            int i20 = i6 + (i5 * childrenSkipCount);
            int i21 = iMeasureNullChild;
            View virtualChildAt = getVirtualChildAt(i20);
            if (virtualChildAt == null) {
                iMeasureNullChild = i21 + measureNullChild(i20);
                z = z4;
                z2 = zIsLayoutRtl;
                c = c2;
            } else {
                boolean z5 = z4;
                c = c2;
                if (virtualChildAt.getVisibility() != 8) {
                    int measuredWidth = virtualChildAt.getMeasuredWidth();
                    int measuredHeight2 = virtualChildAt.getMeasuredHeight();
                    LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                    if (z3) {
                        i7 = childrenSkipCount;
                        int baseline = layoutParams.height != -1 ? virtualChildAt.getBaseline() : -1;
                        i8 = layoutParams.gravity;
                        if (i8 < 0) {
                            i8 = i19;
                        }
                        i9 = i8 & 112;
                        z2 = zIsLayoutRtl;
                        if (i9 != 16) {
                            i10 = ((i17 - measuredHeight2) / 2) + i14 + layoutParams.topMargin;
                            measuredHeight = layoutParams.bottomMargin;
                        } else {
                            if (i9 == 48) {
                                i10 = layoutParams.topMargin + i14;
                                if (baseline != -1) {
                                    i10 += iArr[z5 ? 1 : 0] - baseline;
                                }
                            } else if (i9 == 80) {
                                i10 = (i16 - measuredHeight2) - layoutParams.bottomMargin;
                                if (baseline != -1) {
                                    measuredHeight = iArr2[c] - (virtualChildAt.getMeasuredHeight() - baseline);
                                }
                            } else {
                                i11 = i14;
                                if (!z2) {
                                    if (hasDividerAfterChildAt(i20)) {
                                        i12 = this.mDividerWidth;
                                        i13 = i21 + i12;
                                    }
                                    i13 = i21;
                                } else {
                                    if (hasDividerBeforeChildAt(i20)) {
                                        i12 = this.mDividerWidth;
                                        i13 = i21 + i12;
                                    }
                                    i13 = i21;
                                }
                                int i22 = layoutParams.leftMargin + i13;
                                int locationOffset = getLocationOffset(virtualChildAt) + i22;
                                z = z5 ? 1 : 0;
                                setChildFrame(virtualChildAt, locationOffset, i11, measuredWidth, measuredHeight2);
                                int nextLocationOffset = i22 + layoutParams.rightMargin + measuredWidth + getNextLocationOffset(virtualChildAt);
                                childrenSkipCount = i7 + getChildrenSkipCount(virtualChildAt, i20);
                                iMeasureNullChild = nextLocationOffset;
                            }
                            i11 = i10;
                            if (!z2) {
                            }
                            int i222 = layoutParams.leftMargin + i13;
                            int locationOffset2 = getLocationOffset(virtualChildAt) + i222;
                            z = z5 ? 1 : 0;
                            setChildFrame(virtualChildAt, locationOffset2, i11, measuredWidth, measuredHeight2);
                            int nextLocationOffset2 = i222 + layoutParams.rightMargin + measuredWidth + getNextLocationOffset(virtualChildAt);
                            childrenSkipCount = i7 + getChildrenSkipCount(virtualChildAt, i20);
                            iMeasureNullChild = nextLocationOffset2;
                        }
                        i10 -= measuredHeight;
                        i11 = i10;
                        if (!z2) {
                        }
                        int i2222 = layoutParams.leftMargin + i13;
                        int locationOffset22 = getLocationOffset(virtualChildAt) + i2222;
                        z = z5 ? 1 : 0;
                        setChildFrame(virtualChildAt, locationOffset22, i11, measuredWidth, measuredHeight2);
                        int nextLocationOffset22 = i2222 + layoutParams.rightMargin + measuredWidth + getNextLocationOffset(virtualChildAt);
                        childrenSkipCount = i7 + getChildrenSkipCount(virtualChildAt, i20);
                        iMeasureNullChild = nextLocationOffset22;
                    } else {
                        i7 = childrenSkipCount;
                    }
                    i8 = layoutParams.gravity;
                    if (i8 < 0) {
                    }
                    i9 = i8 & 112;
                    z2 = zIsLayoutRtl;
                    if (i9 != 16) {
                    }
                    i10 -= measuredHeight;
                    i11 = i10;
                    if (!z2) {
                    }
                    int i22222 = layoutParams.leftMargin + i13;
                    int locationOffset222 = getLocationOffset(virtualChildAt) + i22222;
                    z = z5 ? 1 : 0;
                    setChildFrame(virtualChildAt, locationOffset222, i11, measuredWidth, measuredHeight2);
                    int nextLocationOffset222 = i22222 + layoutParams.rightMargin + measuredWidth + getNextLocationOffset(virtualChildAt);
                    childrenSkipCount = i7 + getChildrenSkipCount(virtualChildAt, i20);
                    iMeasureNullChild = nextLocationOffset222;
                } else {
                    z = z5 ? 1 : 0;
                    z2 = zIsLayoutRtl;
                    iMeasureNullChild = i21;
                }
            }
            childrenSkipCount++;
            c2 = c;
            zIsLayoutRtl = z2;
            z4 = z;
        }
    }

    private void setChildFrame(View view, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
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
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) throws Resources.NotFoundException, IOException {
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
