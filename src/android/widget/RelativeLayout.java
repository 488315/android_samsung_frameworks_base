package android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ResourceId;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.media.TtmlUtils;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pools;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.PathInterpolator;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.AbsListView;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class RelativeLayout extends ViewGroup {
    public static final int ABOVE = 2;
    public static final int ALIGN_BASELINE = 4;
    public static final int ALIGN_BOTTOM = 8;
    public static final int ALIGN_END = 19;
    public static final int ALIGN_LEFT = 5;
    public static final int ALIGN_PARENT_BOTTOM = 12;
    public static final int ALIGN_PARENT_END = 21;
    public static final int ALIGN_PARENT_LEFT = 9;
    public static final int ALIGN_PARENT_RIGHT = 11;
    public static final int ALIGN_PARENT_START = 20;
    public static final int ALIGN_PARENT_TOP = 10;
    public static final int ALIGN_RIGHT = 7;
    public static final int ALIGN_START = 18;
    public static final int ALIGN_TOP = 6;
    private static final int APPWIDGET_EXPAND_ACTION_DELAY = 1500;
    private static final int APPWIDGET_RELEASE_ACTION_DELAY = 50;
    private static final int APPWIDGET_RELEASE_SCROLL_DURATION = 400;
    public static final int BELOW = 3;
    public static final int CENTER_HORIZONTAL = 14;
    public static final int CENTER_IN_PARENT = 13;
    public static final int CENTER_VERTICAL = 15;
    private static final int DEFAULT_WIDTH = 65536;
    public static final int END_OF = 17;
    public static final int LEFT_OF = 0;
    public static final int RIGHT_OF = 1;
    public static final int START_OF = 16;
    private static final String TAG = "RelativeLayout";
    public static final int TRUE = -1;
    private static final int VALUE_NOT_SET = Integer.MIN_VALUE;
    private static final int VERB_COUNT = 22;
    private boolean mAllowBrokenMeasureSpecs;
    private boolean mAppWidgetImmersiveEnabled;
    private AbsListView mAppWidgetListView;
    private View mAppWidgetToolBar;
    private View mBaselineView;
    private final Rect mContentBounds;
    private boolean mDirtyHierarchy;
    private ValueAnimator mExpandOffsetAnimator;
    private ExpandTopBarRunnable mExpandTopBarRunnable;
    private final DependencyGraph mGraph;
    private int mGravity;
    private int mIgnoreGravity;
    private boolean mMeasureVerticalWithPaddingMargin;
    private ReleaseScrollRunnable mReleaseScrollRunnable;
    private AppWidgetListScrollListener mScrollListener;
    private final Rect mSelfBounds;
    private View[] mSortedHorizontalChildren;
    private View[] mSortedVerticalChildren;
    private SortedSet<View> mTopToBottomLeftToRightSet;
    private static final int[] RULES_VERTICAL = {2, 3, 4, 6, 8};
    private static final int[] RULES_HORIZONTAL = {0, 1, 5, 7, 16, 17, 18, 19};

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<RelativeLayout> {
        private int mGravityId;
        private int mIgnoreGravityId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mGravityId = propertyMapper.mapGravity("gravity", 16842927);
            this.mIgnoreGravityId = propertyMapper.mapInt("ignoreGravity", 16843263);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(RelativeLayout relativeLayout, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readGravity(this.mGravityId, relativeLayout.getGravity());
            propertyReader.readInt(this.mIgnoreGravityId, relativeLayout.getIgnoreGravity());
        }
    }

    public RelativeLayout(Context context) {
        this(context, null);
    }

    public RelativeLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RelativeLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RelativeLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mBaselineView = null;
        this.mGravity = 8388659;
        this.mContentBounds = new Rect();
        this.mSelfBounds = new Rect();
        this.mTopToBottomLeftToRightSet = null;
        this.mGraph = new DependencyGraph();
        this.mAllowBrokenMeasureSpecs = false;
        this.mMeasureVerticalWithPaddingMargin = false;
        this.mAppWidgetImmersiveEnabled = false;
        initFromAttributes(context, attributeSet, i, i2);
        queryCompatibilityModes(context);
    }

    private void initFromAttributes(Context context, AttributeSet attributeSet, int i, int i2) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RelativeLayout, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.RelativeLayout, attributeSet, typedArrayObtainStyledAttributes, i, i2);
        this.mIgnoreGravity = typedArrayObtainStyledAttributes.getResourceId(1, -1);
        this.mGravity = typedArrayObtainStyledAttributes.getInt(0, this.mGravity);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void queryCompatibilityModes(Context context) {
        int i = context.getApplicationInfo().targetSdkVersion;
        this.mAllowBrokenMeasureSpecs = i <= 17;
        this.mMeasureVerticalWithPaddingMargin = i >= 18;
    }

    @RemotableViewMethod
    public void setIgnoreGravity(int i) {
        this.mIgnoreGravity = i;
    }

    public int getIgnoreGravity() {
        return this.mIgnoreGravity;
    }

    public int getGravity() {
        return this.mGravity;
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

    @Override // android.view.View
    public int getBaseline() {
        View view = this.mBaselineView;
        return view != null ? view.getBaseline() : super.getBaseline();
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        super.requestLayout();
        this.mDirtyHierarchy = true;
    }

    private void sortChildren() {
        int childCount = getChildCount();
        View[] viewArr = this.mSortedVerticalChildren;
        if (viewArr == null || viewArr.length != childCount) {
            this.mSortedVerticalChildren = new View[childCount];
        }
        View[] viewArr2 = this.mSortedHorizontalChildren;
        if (viewArr2 == null || viewArr2.length != childCount) {
            this.mSortedHorizontalChildren = new View[childCount];
        }
        DependencyGraph dependencyGraph = this.mGraph;
        dependencyGraph.clear();
        for (int i = 0; i < childCount; i++) {
            dependencyGraph.add(getChildAt(i));
        }
        dependencyGraph.getSortedViews(this.mSortedVerticalChildren, RULES_VERTICAL);
        dependencyGraph.getSortedViews(this.mSortedHorizontalChildren, RULES_HORIZONTAL);
    }

    /* JADX WARN: Removed duplicated region for block: B:74:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0164  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onMeasure(int i, int i2) {
        int i3;
        boolean z;
        int i4 = 0;
        if (this.mDirtyHierarchy) {
            this.mDirtyHierarchy = false;
            sortChildren();
        }
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode == 0) {
            size = -1;
        }
        if (mode2 == 0) {
            size2 = -1;
        }
        int iResolveSize = mode == 1073741824 ? size : 0;
        int iResolveSize2 = mode2 == 1073741824 ? size2 : 0;
        int i5 = this.mGravity;
        int i6 = 8388615 & i5;
        boolean z2 = (i6 == 8388611 || i6 == 0) ? false : true;
        int i7 = i5 & 112;
        boolean z3 = (i7 == 48 || i7 == 0) ? false : true;
        View viewFindViewById = ((z2 || z3) && (i3 = this.mIgnoreGravity) != -1) ? findViewById(i3) : null;
        boolean z4 = mode != 1073741824;
        boolean z5 = mode2 != 1073741824;
        int layoutDirection = getLayoutDirection();
        if (isLayoutRtl() && size == -1) {
            size = 65536;
        }
        View[] viewArr = this.mSortedHorizontalChildren;
        int length = viewArr.length;
        boolean z6 = false;
        while (i4 < length) {
            View view = viewArr[i4];
            int i8 = i4;
            if (view.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                applyHorizontalSizeRules(layoutParams, size, layoutParams.getRules(layoutDirection));
                measureChildHorizontal(view, layoutParams, size, size2);
                if (positionChildHorizontal(view, layoutParams, size, z4)) {
                    z6 = true;
                }
            }
            i4 = i8 + 1;
        }
        View[] viewArr2 = this.mSortedVerticalChildren;
        int length2 = viewArr2.length;
        int i9 = getContext().getApplicationInfo().targetSdkVersion;
        int iMax = Integer.MIN_VALUE;
        int iMax2 = Integer.MIN_VALUE;
        int iMin = Integer.MAX_VALUE;
        int iMin2 = Integer.MAX_VALUE;
        int i10 = 0;
        boolean z7 = false;
        while (i10 < length2) {
            boolean z8 = z4;
            View view2 = viewArr2[i10];
            View[] viewArr3 = viewArr2;
            boolean z9 = z3;
            if (view2.getVisibility() != 8) {
                LayoutParams layoutParams2 = (LayoutParams) view2.getLayoutParams();
                applyVerticalSizeRules(layoutParams2, size2, view2.getBaseline());
                measureChild(view2, layoutParams2, size, size2);
                if (positionChildVertical(view2, layoutParams2, size2, z5)) {
                    z7 = true;
                }
                if (z8) {
                    if (!isLayoutRtl()) {
                        z = z5;
                        if (i9 < 19) {
                            iResolveSize = Math.max(iResolveSize, layoutParams2.mRight);
                        } else {
                            iResolveSize = Math.max(iResolveSize, layoutParams2.mRight + layoutParams2.rightMargin);
                        }
                    } else if (i9 < 19) {
                        iResolveSize = Math.max(iResolveSize, size - layoutParams2.mLeft);
                        z = z5;
                    } else {
                        z = z5;
                        iResolveSize = Math.max(iResolveSize, (size - layoutParams2.mLeft) + layoutParams2.leftMargin);
                    }
                    if (z) {
                    }
                    if (view2 == viewFindViewById) {
                        iMin2 = Math.min(iMin2, layoutParams2.mLeft - layoutParams2.leftMargin);
                        iMin = Math.min(iMin, layoutParams2.mTop - layoutParams2.topMargin);
                        if (view2 == viewFindViewById) {
                            iMax = Math.max(iMax, layoutParams2.mRight + layoutParams2.rightMargin);
                            iMax2 = Math.max(iMax2, layoutParams2.mBottom + layoutParams2.bottomMargin);
                        }
                    }
                } else {
                    z = z5;
                    if (z) {
                        if (i9 < 19) {
                            iResolveSize2 = Math.max(iResolveSize2, layoutParams2.mBottom);
                        } else {
                            iResolveSize2 = Math.max(iResolveSize2, layoutParams2.mBottom + layoutParams2.bottomMargin);
                        }
                    }
                    if (view2 == viewFindViewById || z9) {
                        iMin2 = Math.min(iMin2, layoutParams2.mLeft - layoutParams2.leftMargin);
                        iMin = Math.min(iMin, layoutParams2.mTop - layoutParams2.topMargin);
                    }
                    if (view2 == viewFindViewById || z2) {
                        iMax = Math.max(iMax, layoutParams2.mRight + layoutParams2.rightMargin);
                        iMax2 = Math.max(iMax2, layoutParams2.mBottom + layoutParams2.bottomMargin);
                    }
                }
            } else {
                z = z5;
            }
            i10++;
            z4 = z8;
            viewArr2 = viewArr3;
            z3 = z9;
            z5 = z;
        }
        boolean z10 = z4;
        View[] viewArr4 = viewArr2;
        boolean z11 = z5;
        boolean z12 = z3;
        int i11 = iMax2;
        int i12 = iMin;
        int i13 = iMin2;
        LayoutParams layoutParams3 = null;
        int i14 = 0;
        View view3 = null;
        while (i14 < length2) {
            View view4 = viewArr4[i14];
            int i15 = i12;
            int i16 = i11;
            if (view4.getVisibility() != 8) {
                LayoutParams layoutParams4 = (LayoutParams) view4.getLayoutParams();
                if (view3 == null || layoutParams3 == null || compareLayoutPosition(layoutParams4, layoutParams3) < 0) {
                    layoutParams3 = layoutParams4;
                    view3 = view4;
                }
            }
            i14++;
            i11 = i16;
            i12 = i15;
        }
        int i17 = i12;
        int i18 = i11;
        this.mBaselineView = view3;
        if (z10) {
            int iMax3 = iResolveSize + this.mPaddingRight;
            if (this.mLayoutParams != null && this.mLayoutParams.width >= 0) {
                iMax3 = Math.max(iMax3, this.mLayoutParams.width);
            }
            iResolveSize = resolveSize(Math.max(iMax3, getSuggestedMinimumWidth()), i);
            if (z6) {
                for (int i19 = 0; i19 < length2; i19++) {
                    View view5 = viewArr4[i19];
                    if (view5.getVisibility() != 8) {
                        LayoutParams layoutParams5 = (LayoutParams) view5.getLayoutParams();
                        int[] rules = layoutParams5.getRules(layoutDirection);
                        if (rules[13] != 0 || rules[14] != 0) {
                            centerHorizontal(view5, layoutParams5, iResolveSize);
                        } else if (rules[11] != 0) {
                            int measuredWidth = view5.getMeasuredWidth();
                            layoutParams5.mLeft = (iResolveSize - this.mPaddingRight) - measuredWidth;
                            layoutParams5.mRight = layoutParams5.mLeft + measuredWidth;
                        }
                    }
                }
            }
        }
        if (z11) {
            int iMax4 = iResolveSize2 + this.mPaddingBottom;
            if (this.mLayoutParams != null && this.mLayoutParams.height >= 0) {
                iMax4 = Math.max(iMax4, this.mLayoutParams.height);
            }
            iResolveSize2 = resolveSize(Math.max(iMax4, getSuggestedMinimumHeight()), i2);
            if (z7) {
                for (int i20 = 0; i20 < length2; i20++) {
                    View view6 = viewArr4[i20];
                    if (view6.getVisibility() != 8) {
                        LayoutParams layoutParams6 = (LayoutParams) view6.getLayoutParams();
                        int[] rules2 = layoutParams6.getRules(layoutDirection);
                        if (rules2[13] != 0 || rules2[15] != 0) {
                            centerVertical(view6, layoutParams6, iResolveSize2);
                        } else if (rules2[12] != 0) {
                            int measuredHeight = view6.getMeasuredHeight();
                            layoutParams6.mTop = (iResolveSize2 - this.mPaddingBottom) - measuredHeight;
                            layoutParams6.mBottom = layoutParams6.mTop + measuredHeight;
                        }
                    }
                }
            }
        }
        if (z2 || z12) {
            Rect rect = this.mSelfBounds;
            rect.set(this.mPaddingLeft, this.mPaddingTop, iResolveSize - this.mPaddingRight, iResolveSize2 - this.mPaddingBottom);
            Rect rect2 = this.mContentBounds;
            Gravity.apply(this.mGravity, iMax - i13, i18 - i17, rect, rect2, layoutDirection);
            int i21 = rect2.left - i13;
            int i22 = rect2.top - i17;
            if (i21 != 0 || i22 != 0) {
                for (int i23 = 0; i23 < length2; i23++) {
                    View view7 = viewArr4[i23];
                    if (view7.getVisibility() != 8 && view7 != viewFindViewById) {
                        LayoutParams layoutParams7 = (LayoutParams) view7.getLayoutParams();
                        if (z2) {
                            layoutParams7.mLeft += i21;
                            layoutParams7.mRight += i21;
                        }
                        if (z12) {
                            layoutParams7.mTop += i22;
                            layoutParams7.mBottom += i22;
                        }
                    }
                }
            }
        }
        if (isLayoutRtl()) {
            int i24 = size - iResolveSize;
            for (int i25 = 0; i25 < length2; i25++) {
                View view8 = viewArr4[i25];
                if (view8.getVisibility() != 8) {
                    LayoutParams layoutParams8 = (LayoutParams) view8.getLayoutParams();
                    layoutParams8.mLeft -= i24;
                    layoutParams8.mRight -= i24;
                }
            }
        }
        setMeasuredDimension(iResolveSize, iResolveSize2);
    }

    private int compareLayoutPosition(LayoutParams layoutParams, LayoutParams layoutParams2) {
        int i = layoutParams.mTop - layoutParams2.mTop;
        return i != 0 ? i : layoutParams.mLeft - layoutParams2.mLeft;
    }

    private void measureChild(View view, LayoutParams layoutParams, int i, int i2) {
        view.measure(getChildMeasureSpec(layoutParams.mLeft, layoutParams.mRight, layoutParams.width, layoutParams.leftMargin, layoutParams.rightMargin, this.mPaddingLeft, this.mPaddingRight, i), getChildMeasureSpec(layoutParams.mTop, layoutParams.mBottom, layoutParams.height, layoutParams.topMargin, layoutParams.bottomMargin, this.mPaddingTop, this.mPaddingBottom, i2));
    }

    private void measureChildHorizontal(View view, LayoutParams layoutParams, int i, int i2) {
        int iMax;
        int iMakeMeasureSpec;
        int childMeasureSpec = getChildMeasureSpec(layoutParams.mLeft, layoutParams.mRight, layoutParams.width, layoutParams.leftMargin, layoutParams.rightMargin, this.mPaddingLeft, this.mPaddingRight, i);
        if (i2 < 0 && !this.mAllowBrokenMeasureSpecs) {
            if (layoutParams.height >= 0) {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824);
            } else {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            }
        } else {
            if (this.mMeasureVerticalWithPaddingMargin) {
                iMax = Math.max(0, (((i2 - this.mPaddingTop) - this.mPaddingBottom) - layoutParams.topMargin) - layoutParams.bottomMargin);
            } else {
                iMax = Math.max(0, i2);
            }
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iMax, layoutParams.height != -1 ? Integer.MIN_VALUE : 1073741824);
        }
        view.measure(childMeasureSpec, iMakeMeasureSpec);
    }

    private int getChildMeasureSpec(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int i9 = 0;
        boolean z = i8 < 0;
        if (z && !this.mAllowBrokenMeasureSpecs) {
            if (i != Integer.MIN_VALUE && i2 != Integer.MIN_VALUE) {
                i3 = Math.max(0, i2 - i);
            } else {
                if (i3 < 0) {
                    i3 = 0;
                }
                return View.MeasureSpec.makeMeasureSpec(i3, i9);
            }
            i9 = 1073741824;
            return View.MeasureSpec.makeMeasureSpec(i3, i9);
        }
        int i10 = (i2 == Integer.MIN_VALUE ? (i8 - i7) - i5 : i2) - (i == Integer.MIN_VALUE ? i6 + i4 : i);
        if (i != Integer.MIN_VALUE && i2 != Integer.MIN_VALUE) {
            i = z ? 0 : 1073741824;
            i3 = Math.max(0, i10);
        } else if (i3 >= 0) {
            if (i10 >= 0) {
                i3 = Math.min(i10, i3);
            }
        } else if (i3 == -1) {
            i = z ? 0 : 1073741824;
            i3 = Math.max(0, i10);
        } else {
            if (i3 != -2 || i10 < 0) {
                i3 = 0;
            } else {
                i3 = i10;
                i9 = Integer.MIN_VALUE;
            }
            return View.MeasureSpec.makeMeasureSpec(i3, i9);
        }
        i9 = i;
        return View.MeasureSpec.makeMeasureSpec(i3, i9);
    }

    private boolean positionChildHorizontal(View view, LayoutParams layoutParams, int i, boolean z) {
        int[] rules = layoutParams.getRules(getLayoutDirection());
        if (layoutParams.mLeft == Integer.MIN_VALUE && layoutParams.mRight != Integer.MIN_VALUE) {
            layoutParams.mLeft = layoutParams.mRight - view.getMeasuredWidth();
        } else if (layoutParams.mLeft != Integer.MIN_VALUE && layoutParams.mRight == Integer.MIN_VALUE) {
            layoutParams.mRight = layoutParams.mLeft + view.getMeasuredWidth();
        } else if (layoutParams.mLeft == Integer.MIN_VALUE && layoutParams.mRight == Integer.MIN_VALUE) {
            if (rules[13] != 0 || rules[14] != 0) {
                if (!z) {
                    centerHorizontal(view, layoutParams, i);
                } else {
                    positionAtEdge(view, layoutParams, i);
                }
                return true;
            }
            positionAtEdge(view, layoutParams, i);
        }
        return rules[21] != 0;
    }

    private void positionAtEdge(View view, LayoutParams layoutParams, int i) {
        if (isLayoutRtl()) {
            layoutParams.mRight = (i - this.mPaddingRight) - layoutParams.rightMargin;
            layoutParams.mLeft = layoutParams.mRight - view.getMeasuredWidth();
        } else {
            layoutParams.mLeft = this.mPaddingLeft + layoutParams.leftMargin;
            layoutParams.mRight = layoutParams.mLeft + view.getMeasuredWidth();
        }
    }

    private boolean positionChildVertical(View view, LayoutParams layoutParams, int i, boolean z) {
        int[] rules = layoutParams.getRules();
        if (layoutParams.mTop == Integer.MIN_VALUE && layoutParams.mBottom != Integer.MIN_VALUE) {
            layoutParams.mTop = layoutParams.mBottom - view.getMeasuredHeight();
        } else if (layoutParams.mTop != Integer.MIN_VALUE && layoutParams.mBottom == Integer.MIN_VALUE) {
            layoutParams.mBottom = layoutParams.mTop + view.getMeasuredHeight();
        } else if (layoutParams.mTop == Integer.MIN_VALUE && layoutParams.mBottom == Integer.MIN_VALUE) {
            if (rules[13] != 0 || rules[15] != 0) {
                if (!z) {
                    centerVertical(view, layoutParams, i);
                } else {
                    layoutParams.mTop = this.mPaddingTop + layoutParams.topMargin;
                    layoutParams.mBottom = layoutParams.mTop + view.getMeasuredHeight();
                }
                return true;
            }
            layoutParams.mTop = this.mPaddingTop + layoutParams.topMargin;
            layoutParams.mBottom = layoutParams.mTop + view.getMeasuredHeight();
        }
        return rules[12] != 0;
    }

    private void applyHorizontalSizeRules(LayoutParams layoutParams, int i, int[] iArr) {
        layoutParams.mLeft = Integer.MIN_VALUE;
        layoutParams.mRight = Integer.MIN_VALUE;
        LayoutParams relatedViewParams = getRelatedViewParams(iArr, 0);
        if (relatedViewParams != null) {
            layoutParams.mRight = relatedViewParams.mLeft - (relatedViewParams.leftMargin + layoutParams.rightMargin);
        } else if (layoutParams.alignWithParent && iArr[0] != 0 && i >= 0) {
            layoutParams.mRight = (i - this.mPaddingRight) - layoutParams.rightMargin;
        }
        LayoutParams relatedViewParams2 = getRelatedViewParams(iArr, 1);
        if (relatedViewParams2 != null) {
            layoutParams.mLeft = relatedViewParams2.mRight + relatedViewParams2.rightMargin + layoutParams.leftMargin;
        } else if (layoutParams.alignWithParent && iArr[1] != 0) {
            layoutParams.mLeft = this.mPaddingLeft + layoutParams.leftMargin;
        }
        LayoutParams relatedViewParams3 = getRelatedViewParams(iArr, 5);
        if (relatedViewParams3 != null) {
            layoutParams.mLeft = relatedViewParams3.mLeft + layoutParams.leftMargin;
        } else if (layoutParams.alignWithParent && iArr[5] != 0) {
            layoutParams.mLeft = this.mPaddingLeft + layoutParams.leftMargin;
        }
        LayoutParams relatedViewParams4 = getRelatedViewParams(iArr, 7);
        if (relatedViewParams4 != null) {
            layoutParams.mRight = relatedViewParams4.mRight - layoutParams.rightMargin;
        } else if (layoutParams.alignWithParent && iArr[7] != 0 && i >= 0) {
            layoutParams.mRight = (i - this.mPaddingRight) - layoutParams.rightMargin;
        }
        if (iArr[9] != 0) {
            layoutParams.mLeft = this.mPaddingLeft + layoutParams.leftMargin;
        }
        if (iArr[11] == 0 || i < 0) {
            return;
        }
        layoutParams.mRight = (i - this.mPaddingRight) - layoutParams.rightMargin;
    }

    private void applyVerticalSizeRules(LayoutParams layoutParams, int i, int i2) {
        int[] rules = layoutParams.getRules();
        int relatedViewBaselineOffset = getRelatedViewBaselineOffset(rules);
        if (relatedViewBaselineOffset != -1) {
            if (i2 != -1) {
                relatedViewBaselineOffset -= i2;
            }
            layoutParams.mTop = relatedViewBaselineOffset;
            layoutParams.mBottom = Integer.MIN_VALUE;
            return;
        }
        layoutParams.mTop = Integer.MIN_VALUE;
        layoutParams.mBottom = Integer.MIN_VALUE;
        LayoutParams relatedViewParams = getRelatedViewParams(rules, 2);
        if (relatedViewParams != null) {
            layoutParams.mBottom = relatedViewParams.mTop - (relatedViewParams.topMargin + layoutParams.bottomMargin);
        } else if (layoutParams.alignWithParent && rules[2] != 0 && i >= 0) {
            layoutParams.mBottom = (i - this.mPaddingBottom) - layoutParams.bottomMargin;
        }
        LayoutParams relatedViewParams2 = getRelatedViewParams(rules, 3);
        if (relatedViewParams2 != null) {
            layoutParams.mTop = relatedViewParams2.mBottom + relatedViewParams2.bottomMargin + layoutParams.topMargin;
        } else if (layoutParams.alignWithParent && rules[3] != 0) {
            layoutParams.mTop = this.mPaddingTop + layoutParams.topMargin;
        }
        LayoutParams relatedViewParams3 = getRelatedViewParams(rules, 6);
        if (relatedViewParams3 != null) {
            layoutParams.mTop = relatedViewParams3.mTop + layoutParams.topMargin;
        } else if (layoutParams.alignWithParent && rules[6] != 0) {
            layoutParams.mTop = this.mPaddingTop + layoutParams.topMargin;
        }
        LayoutParams relatedViewParams4 = getRelatedViewParams(rules, 8);
        if (relatedViewParams4 != null) {
            layoutParams.mBottom = relatedViewParams4.mBottom - layoutParams.bottomMargin;
        } else if (layoutParams.alignWithParent && rules[8] != 0 && i >= 0) {
            layoutParams.mBottom = (i - this.mPaddingBottom) - layoutParams.bottomMargin;
        }
        if (rules[10] != 0) {
            layoutParams.mTop = this.mPaddingTop + layoutParams.topMargin;
        }
        if (rules[12] == 0 || i < 0) {
            return;
        }
        layoutParams.mBottom = (i - this.mPaddingBottom) - layoutParams.bottomMargin;
    }

    private View getRelatedView(int[] iArr, int i) {
        DependencyGraph.Node node;
        int i2 = iArr[i];
        if (i2 == 0 || (node = (DependencyGraph.Node) this.mGraph.mKeyNodes.get(i2)) == null) {
            return null;
        }
        View view = node.view;
        while (view.getVisibility() == 8) {
            DependencyGraph.Node node2 = (DependencyGraph.Node) this.mGraph.mKeyNodes.get(((LayoutParams) view.getLayoutParams()).getRules(view.getLayoutDirection())[i]);
            if (node2 == null || view == node2.view) {
                return null;
            }
            view = node2.view;
        }
        return view;
    }

    private LayoutParams getRelatedViewParams(int[] iArr, int i) {
        View relatedView = getRelatedView(iArr, i);
        if (relatedView == null || !(relatedView.getLayoutParams() instanceof LayoutParams)) {
            return null;
        }
        return (LayoutParams) relatedView.getLayoutParams();
    }

    private int getRelatedViewBaselineOffset(int[] iArr) {
        int baseline;
        View relatedView = getRelatedView(iArr, 4);
        if (relatedView == null || (baseline = relatedView.getBaseline()) == -1 || !(relatedView.getLayoutParams() instanceof LayoutParams)) {
            return -1;
        }
        return ((LayoutParams) relatedView.getLayoutParams()).mTop + baseline;
    }

    private static void centerHorizontal(View view, LayoutParams layoutParams, int i) {
        int measuredWidth = view.getMeasuredWidth();
        int i2 = (i - measuredWidth) / 2;
        layoutParams.mLeft = i2;
        layoutParams.mRight = i2 + measuredWidth;
    }

    private static void centerVertical(View view, LayoutParams layoutParams, int i) {
        int measuredHeight = view.getMeasuredHeight();
        int i2 = (i - measuredHeight) / 2;
        layoutParams.mTop = i2;
        layoutParams.mBottom = i2 + measuredHeight;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        int childCount = getChildCount();
        if (!this.mAppWidgetImmersiveEnabled) {
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                if (childAt.getVisibility() != 8) {
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    childAt.layout(layoutParams.mLeft, layoutParams.mTop, layoutParams.mRight, layoutParams.mBottom);
                }
            }
            return;
        }
        int i6 = 0;
        while (i6 < childCount) {
            View childAt2 = getChildAt(i6);
            int measuredHeight = childAt2.getMeasuredHeight() + i2;
            childAt2.layout(0, i2, childAt2.getMeasuredWidth(), measuredHeight);
            i6++;
            i2 = measuredHeight;
        }
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
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
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        if (this.mTopToBottomLeftToRightSet == null) {
            this.mTopToBottomLeftToRightSet = new TreeSet(new TopToBottomLeftToRightComparator());
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            this.mTopToBottomLeftToRightSet.add(getChildAt(i));
        }
        for (View view : this.mTopToBottomLeftToRightSet) {
            if (view.getVisibility() == 0 && view.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                this.mTopToBottomLeftToRightSet.clear();
                return true;
            }
        }
        this.mTopToBottomLeftToRightSet.clear();
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return RelativeLayout.class.getName();
    }

    private class TopToBottomLeftToRightComparator implements Comparator<View> {
        private TopToBottomLeftToRightComparator(RelativeLayout relativeLayout) {
        }

        @Override // java.util.Comparator
        public int compare(View view, View view2) {
            int top = view.getTop() - view2.getTop();
            if (top != 0) {
                return top;
            }
            int left = view.getLeft() - view2.getLeft();
            if (left != 0) {
                return left;
            }
            int height = view.getHeight() - view2.getHeight();
            if (height != 0) {
                return height;
            }
            int width = view.getWidth() - view2.getWidth();
            if (width != 0) {
                return width;
            }
            return 0;
        }
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public boolean alignWithParent;
        private int mBottom;
        private int[] mInitialRules;
        private boolean mIsRtlCompatibilityMode;
        private int mLeft;
        private boolean mNeedsLayoutResolution;
        private int mRight;

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT, indexMapping = {@ViewDebug.IntToString(from = 2, to = "above"), @ViewDebug.IntToString(from = 4, to = "alignBaseline"), @ViewDebug.IntToString(from = 8, to = "alignBottom"), @ViewDebug.IntToString(from = 5, to = "alignLeft"), @ViewDebug.IntToString(from = 12, to = "alignParentBottom"), @ViewDebug.IntToString(from = 9, to = "alignParentLeft"), @ViewDebug.IntToString(from = 11, to = "alignParentRight"), @ViewDebug.IntToString(from = 10, to = "alignParentTop"), @ViewDebug.IntToString(from = 7, to = "alignRight"), @ViewDebug.IntToString(from = 6, to = "alignTop"), @ViewDebug.IntToString(from = 3, to = "below"), @ViewDebug.IntToString(from = 14, to = "centerHorizontal"), @ViewDebug.IntToString(from = 13, to = "center"), @ViewDebug.IntToString(from = 15, to = "centerVertical"), @ViewDebug.IntToString(from = 0, to = "leftOf"), @ViewDebug.IntToString(from = 1, to = "rightOf"), @ViewDebug.IntToString(from = 18, to = "alignStart"), @ViewDebug.IntToString(from = 19, to = "alignEnd"), @ViewDebug.IntToString(from = 20, to = "alignParentStart"), @ViewDebug.IntToString(from = 21, to = "alignParentEnd"), @ViewDebug.IntToString(from = 16, to = "startOf"), @ViewDebug.IntToString(from = 17, to = "endOf")}, mapping = {@ViewDebug.IntToString(from = -1, to = "true"), @ViewDebug.IntToString(from = 0, to = "false/NO_ID")}, resolveId = true)
        private int[] mRules;
        private boolean mRulesChanged;
        private int mTop;

        private boolean isRelativeRule(int i) {
            return i == 16 || i == 17 || i == 18 || i == 19 || i == 20 || i == 21;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mRules = new int[22];
            this.mInitialRules = new int[22];
            this.mRulesChanged = false;
            this.mIsRtlCompatibilityMode = false;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RelativeLayout_Layout);
            this.mIsRtlCompatibilityMode = context.getApplicationInfo().targetSdkVersion < 17 || !context.getApplicationInfo().hasRtlSupport();
            int[] iArr = this.mRules;
            int[] iArr2 = this.mInitialRules;
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i);
                switch (index) {
                    case 0:
                        iArr[0] = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 1:
                        iArr[1] = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 2:
                        iArr[2] = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 3:
                        iArr[3] = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 4:
                        iArr[4] = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 5:
                        iArr[5] = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 6:
                        iArr[6] = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 7:
                        iArr[7] = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 8:
                        iArr[8] = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 9:
                        iArr[9] = typedArrayObtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                    case 10:
                        iArr[10] = typedArrayObtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                    case 11:
                        iArr[11] = typedArrayObtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                    case 12:
                        iArr[12] = typedArrayObtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                    case 13:
                        iArr[13] = typedArrayObtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                    case 14:
                        iArr[14] = typedArrayObtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                    case 15:
                        iArr[15] = typedArrayObtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                    case 16:
                        this.alignWithParent = typedArrayObtainStyledAttributes.getBoolean(index, false);
                        break;
                    case 17:
                        iArr[16] = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 18:
                        iArr[17] = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 19:
                        iArr[18] = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 20:
                        iArr[19] = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 21:
                        iArr[20] = typedArrayObtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                    case 22:
                        iArr[21] = typedArrayObtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                }
            }
            this.mRulesChanged = true;
            System.arraycopy(iArr, 0, iArr2, 0, 22);
            typedArrayObtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.mRules = new int[22];
            this.mInitialRules = new int[22];
            this.mRulesChanged = false;
            this.mIsRtlCompatibilityMode = false;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mRules = new int[22];
            this.mInitialRules = new int[22];
            this.mRulesChanged = false;
            this.mIsRtlCompatibilityMode = false;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.mRules = new int[22];
            this.mInitialRules = new int[22];
            this.mRulesChanged = false;
            this.mIsRtlCompatibilityMode = false;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            int[] iArr = new int[22];
            this.mRules = iArr;
            this.mInitialRules = new int[22];
            this.mRulesChanged = false;
            this.mIsRtlCompatibilityMode = false;
            this.mIsRtlCompatibilityMode = layoutParams.mIsRtlCompatibilityMode;
            this.mRulesChanged = layoutParams.mRulesChanged;
            this.alignWithParent = layoutParams.alignWithParent;
            System.arraycopy(layoutParams.mRules, 0, iArr, 0, 22);
            System.arraycopy(layoutParams.mInitialRules, 0, this.mInitialRules, 0, 22);
        }

        @Override // android.view.ViewGroup.LayoutParams
        public String debug(String str) {
            return str + "ViewGroup.LayoutParams={ width=" + sizeToString(this.width) + ", height=" + sizeToString(this.height) + " }";
        }

        public void addRule(int i) {
            addRule(i, -1);
        }

        public void addRule(int i, int i2) {
            if (!this.mNeedsLayoutResolution && isRelativeRule(i) && this.mInitialRules[i] != 0 && i2 == 0) {
                this.mNeedsLayoutResolution = true;
            }
            this.mRules[i] = i2;
            this.mInitialRules[i] = i2;
            this.mRulesChanged = true;
        }

        public void removeRule(int i) {
            addRule(i, 0);
        }

        public int getRule(int i) {
            return this.mRules[i];
        }

        private boolean hasRelativeRules() {
            int[] iArr = this.mInitialRules;
            return (iArr[16] == 0 && iArr[17] == 0 && iArr[18] == 0 && iArr[19] == 0 && iArr[20] == 0 && iArr[21] == 0) ? false : true;
        }

        private void resolveRules(int i) {
            char c = i == 1 ? (char) 1 : (char) 0;
            System.arraycopy(this.mInitialRules, 0, this.mRules, 0, 22);
            if (this.mIsRtlCompatibilityMode) {
                int[] iArr = this.mRules;
                int i2 = iArr[18];
                if (i2 != 0) {
                    if (iArr[5] == 0) {
                        iArr[5] = i2;
                    }
                    iArr[18] = 0;
                }
                int i3 = iArr[19];
                if (i3 != 0) {
                    if (iArr[7] == 0) {
                        iArr[7] = i3;
                    }
                    iArr[19] = 0;
                }
                int i4 = iArr[16];
                if (i4 != 0) {
                    if (iArr[0] == 0) {
                        iArr[0] = i4;
                    }
                    iArr[16] = 0;
                }
                int i5 = iArr[17];
                if (i5 != 0) {
                    if (iArr[1] == 0) {
                        iArr[1] = i5;
                    }
                    iArr[17] = 0;
                }
                int i6 = iArr[20];
                if (i6 != 0) {
                    if (iArr[9] == 0) {
                        iArr[9] = i6;
                    }
                    iArr[20] = 0;
                }
                int i7 = iArr[21];
                if (i7 != 0) {
                    if (iArr[11] == 0) {
                        iArr[11] = i7;
                    }
                    iArr[21] = 0;
                }
            } else {
                int[] iArr2 = this.mRules;
                int i8 = iArr2[18];
                if ((i8 != 0 || iArr2[19] != 0) && (iArr2[5] != 0 || iArr2[7] != 0)) {
                    iArr2[5] = 0;
                    iArr2[7] = 0;
                }
                if (i8 != 0) {
                    iArr2[c != 0 ? (char) 7 : (char) 5] = i8;
                    iArr2[18] = 0;
                }
                int i9 = iArr2[19];
                if (i9 != 0) {
                    iArr2[c != 0 ? (char) 5 : (char) 7] = i9;
                    iArr2[19] = 0;
                }
                int i10 = iArr2[16];
                if ((i10 != 0 || iArr2[17] != 0) && (iArr2[0] != 0 || iArr2[1] != 0)) {
                    iArr2[0] = 0;
                    iArr2[1] = 0;
                }
                if (i10 != 0) {
                    iArr2[c] = i10;
                    iArr2[16] = 0;
                }
                int i11 = iArr2[17];
                if (i11 != 0) {
                    iArr2[c ^ 1] = i11;
                    iArr2[17] = 0;
                }
                int i12 = iArr2[20];
                if ((i12 != 0 || iArr2[21] != 0) && (iArr2[9] != 0 || iArr2[11] != 0)) {
                    iArr2[9] = 0;
                    iArr2[11] = 0;
                }
                if (i12 != 0) {
                    iArr2[c != 0 ? (char) 11 : '\t'] = i12;
                    iArr2[20] = 0;
                }
                int i13 = iArr2[21];
                if (i13 != 0) {
                    iArr2[c != 0 ? '\t' : (char) 11] = i13;
                    iArr2[21] = 0;
                }
            }
            this.mRulesChanged = false;
            this.mNeedsLayoutResolution = false;
        }

        public int[] getRules(int i) {
            resolveLayoutDirection(i);
            return this.mRules;
        }

        public int[] getRules() {
            return this.mRules;
        }

        @Override // android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
        public void resolveLayoutDirection(int i) {
            if (shouldResolveLayoutDirection(i)) {
                resolveRules(i);
            }
            super.resolveLayoutDirection(i);
        }

        private boolean shouldResolveLayoutDirection(int i) {
            if (this.mNeedsLayoutResolution || hasRelativeRules()) {
                return this.mRulesChanged || i != getLayoutDirection();
            }
            return false;
        }

        @Override // android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) throws IOException {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("layout:alignWithParent", this.alignWithParent);
        }

        public static final class InspectionCompanion implements android.view.inspector.InspectionCompanion<LayoutParams> {
            private int mAboveId;
            private int mAlignBaselineId;
            private int mAlignBottomId;
            private int mAlignEndId;
            private int mAlignLeftId;
            private int mAlignParentBottomId;
            private int mAlignParentEndId;
            private int mAlignParentLeftId;
            private int mAlignParentRightId;
            private int mAlignParentStartId;
            private int mAlignParentTopId;
            private int mAlignRightId;
            private int mAlignStartId;
            private int mAlignTopId;
            private int mAlignWithParentIfMissingId;
            private int mBelowId;
            private int mCenterHorizontalId;
            private int mCenterInParentId;
            private int mCenterVerticalId;
            private boolean mPropertiesMapped;
            private int mToEndOfId;
            private int mToLeftOfId;
            private int mToRightOfId;
            private int mToStartOfId;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(PropertyMapper propertyMapper) {
                this.mPropertiesMapped = true;
                this.mAboveId = propertyMapper.mapResourceId("layout_above", 16843140);
                this.mAlignBaselineId = propertyMapper.mapResourceId("layout_alignBaseline", 16843142);
                this.mAlignBottomId = propertyMapper.mapResourceId("layout_alignBottom", 16843146);
                this.mAlignEndId = propertyMapper.mapResourceId("layout_alignEnd", 16843706);
                this.mAlignLeftId = propertyMapper.mapResourceId("layout_alignLeft", 16843143);
                this.mAlignParentBottomId = propertyMapper.mapBoolean("layout_alignParentBottom", 16843150);
                this.mAlignParentEndId = propertyMapper.mapBoolean("layout_alignParentEnd", 16843708);
                this.mAlignParentLeftId = propertyMapper.mapBoolean("layout_alignParentLeft", 16843147);
                this.mAlignParentRightId = propertyMapper.mapBoolean("layout_alignParentRight", 16843149);
                this.mAlignParentStartId = propertyMapper.mapBoolean("layout_alignParentStart", 16843707);
                this.mAlignParentTopId = propertyMapper.mapBoolean("layout_alignParentTop", 16843148);
                this.mAlignRightId = propertyMapper.mapResourceId("layout_alignRight", 16843145);
                this.mAlignStartId = propertyMapper.mapResourceId("layout_alignStart", 16843705);
                this.mAlignTopId = propertyMapper.mapResourceId("layout_alignTop", 16843144);
                this.mAlignWithParentIfMissingId = propertyMapper.mapBoolean("layout_alignWithParentIfMissing", 16843154);
                this.mBelowId = propertyMapper.mapResourceId("layout_below", 16843141);
                this.mCenterHorizontalId = propertyMapper.mapBoolean("layout_centerHorizontal", 16843152);
                this.mCenterInParentId = propertyMapper.mapBoolean("layout_centerInParent", 16843151);
                this.mCenterVerticalId = propertyMapper.mapBoolean("layout_centerVertical", 16843153);
                this.mToEndOfId = propertyMapper.mapResourceId("layout_toEndOf", 16843704);
                this.mToLeftOfId = propertyMapper.mapResourceId("layout_toLeftOf", 16843138);
                this.mToRightOfId = propertyMapper.mapResourceId("layout_toRightOf", 16843139);
                this.mToStartOfId = propertyMapper.mapResourceId("layout_toStartOf", 16843703);
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(LayoutParams layoutParams, PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new InspectionCompanion.UninitializedPropertyMapException();
                }
                int[] rules = layoutParams.getRules();
                propertyReader.readResourceId(this.mAboveId, rules[2]);
                propertyReader.readResourceId(this.mAlignBaselineId, rules[4]);
                propertyReader.readResourceId(this.mAlignBottomId, rules[8]);
                propertyReader.readResourceId(this.mAlignEndId, rules[19]);
                propertyReader.readResourceId(this.mAlignLeftId, rules[5]);
                propertyReader.readBoolean(this.mAlignParentBottomId, rules[12] == -1);
                propertyReader.readBoolean(this.mAlignParentEndId, rules[21] == -1);
                propertyReader.readBoolean(this.mAlignParentLeftId, rules[9] == -1);
                propertyReader.readBoolean(this.mAlignParentRightId, rules[11] == -1);
                propertyReader.readBoolean(this.mAlignParentStartId, rules[20] == -1);
                propertyReader.readBoolean(this.mAlignParentTopId, rules[10] == -1);
                propertyReader.readResourceId(this.mAlignRightId, rules[7]);
                propertyReader.readResourceId(this.mAlignStartId, rules[18]);
                propertyReader.readResourceId(this.mAlignTopId, rules[6]);
                propertyReader.readBoolean(this.mAlignWithParentIfMissingId, layoutParams.alignWithParent);
                propertyReader.readResourceId(this.mBelowId, rules[3]);
                propertyReader.readBoolean(this.mCenterHorizontalId, rules[14] == -1);
                propertyReader.readBoolean(this.mCenterInParentId, rules[13] == -1);
                propertyReader.readBoolean(this.mCenterVerticalId, rules[15] == -1);
                propertyReader.readResourceId(this.mToEndOfId, rules[17]);
                propertyReader.readResourceId(this.mToLeftOfId, rules[0]);
                propertyReader.readResourceId(this.mToRightOfId, rules[1]);
                propertyReader.readResourceId(this.mToStartOfId, rules[16]);
            }
        }
    }

    private static class DependencyGraph {
        private SparseArray<Node> mKeyNodes;
        private ArrayList<Node> mNodes;
        private ArrayDeque<Node> mRoots;

        private DependencyGraph() {
            this.mNodes = new ArrayList<>();
            this.mKeyNodes = new SparseArray<>();
            this.mRoots = new ArrayDeque<>();
        }

        void clear() {
            ArrayList<Node> arrayList = this.mNodes;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                arrayList.get(i).release();
            }
            arrayList.clear();
            this.mKeyNodes.clear();
            this.mRoots.clear();
        }

        void add(View view) {
            int id = view.getId();
            Node nodeAcquire = Node.acquire(view);
            if (id != -1) {
                this.mKeyNodes.put(id, nodeAcquire);
            }
            this.mNodes.add(nodeAcquire);
        }

        void getSortedViews(View[] viewArr, int... iArr) {
            ArrayDeque<Node> arrayDequeFindRoots = findRoots(iArr);
            int i = 0;
            while (true) {
                Node nodePollLast = arrayDequeFindRoots.pollLast();
                if (nodePollLast == null) {
                    break;
                }
                View view = nodePollLast.view;
                int id = view.getId();
                int i2 = i + 1;
                viewArr[i] = view;
                ArrayMap<Node, DependencyGraph> arrayMap = nodePollLast.dependents;
                int size = arrayMap.size();
                for (int i3 = 0; i3 < size; i3++) {
                    Node nodeKeyAt = arrayMap.keyAt(i3);
                    SparseArray<Node> sparseArray = nodeKeyAt.dependencies;
                    sparseArray.remove(id);
                    if (sparseArray.size() == 0) {
                        arrayDequeFindRoots.add(nodeKeyAt);
                    }
                }
                i = i2;
            }
            if (i < viewArr.length) {
                throw new IllegalStateException("Circular dependencies cannot exist in RelativeLayout");
            }
        }

        private ArrayDeque<Node> findRoots(int[] iArr) {
            Node node;
            SparseArray<Node> sparseArray = this.mKeyNodes;
            ArrayList<Node> arrayList = this.mNodes;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                Node node2 = arrayList.get(i);
                node2.dependents.clear();
                node2.dependencies.clear();
            }
            for (int i2 = 0; i2 < size; i2++) {
                Node node3 = arrayList.get(i2);
                int[] iArr2 = ((LayoutParams) node3.view.getLayoutParams()).mRules;
                for (int i3 : iArr) {
                    int i4 = iArr2[i3];
                    if ((i4 > 0 || ResourceId.isValid(i4)) && (node = sparseArray.get(i4)) != null && node != node3) {
                        node.dependents.put(node3, this);
                        node3.dependencies.put(i4, node);
                    }
                }
            }
            ArrayDeque<Node> arrayDeque = this.mRoots;
            arrayDeque.clear();
            for (int i5 = 0; i5 < size; i5++) {
                Node node4 = arrayList.get(i5);
                if (node4.dependencies.size() == 0) {
                    arrayDeque.addLast(node4);
                }
            }
            return arrayDeque;
        }

        static class Node {
            private static final int POOL_LIMIT = 100;
            private static final Pools.SynchronizedPool<Node> sPool = new Pools.SynchronizedPool<>(100);
            View view;
            final ArrayMap<Node, DependencyGraph> dependents = new ArrayMap<>();
            final SparseArray<Node> dependencies = new SparseArray<>();

            Node() {
            }

            static Node acquire(View view) {
                Node nodeAcquire = sPool.acquire();
                if (nodeAcquire == null) {
                    nodeAcquire = new Node();
                }
                nodeAcquire.view = view;
                return nodeAcquire;
            }

            void release() {
                this.view = null;
                this.dependents.clear();
                this.dependencies.clear();
                sPool.release(this);
            }
        }
    }

    private class AppWidgetListScrollListener implements AbsListView.OnScrollListener {
        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        }

        private AppWidgetListScrollListener() {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView absListView, int i) {
            if (RelativeLayout.this.mAppWidgetListView == null) {
                return;
            }
            if (RelativeLayout.this.mReleaseScrollRunnable == null) {
                RelativeLayout.this.mReleaseScrollRunnable = new ReleaseScrollRunnable();
            } else {
                RelativeLayout.this.mAppWidgetListView.removeCallbacks(RelativeLayout.this.mReleaseScrollRunnable);
            }
            if (RelativeLayout.this.mExpandTopBarRunnable == null) {
                RelativeLayout.this.mExpandTopBarRunnable = new ExpandTopBarRunnable();
            } else {
                RelativeLayout.this.mAppWidgetListView.removeCallbacks(RelativeLayout.this.mExpandTopBarRunnable);
            }
            if (i == 0) {
                if (RelativeLayout.this.mAppWidgetToolBar.getBottom() > 0 && RelativeLayout.this.mAppWidgetToolBar.getBottom() < RelativeLayout.this.mAppWidgetToolBar.getHeight()) {
                    RelativeLayout.this.mAppWidgetListView.postDelayed(RelativeLayout.this.mReleaseScrollRunnable, 50L);
                    if (RelativeLayout.this.mAppWidgetToolBar.getBottom() < RelativeLayout.this.mAppWidgetToolBar.getHeight() / 2) {
                        RelativeLayout.this.mAppWidgetListView.postDelayed(RelativeLayout.this.mExpandTopBarRunnable, 1550L);
                        return;
                    }
                    return;
                }
                if (RelativeLayout.this.mAppWidgetToolBar.getBottom() == 0) {
                    RelativeLayout.this.mAppWidgetListView.postDelayed(RelativeLayout.this.mExpandTopBarRunnable, 1500L);
                }
            }
        }
    }

    private class ReleaseScrollRunnable implements Runnable {
        private ValueAnimator mExpandOffsetAnimator;
        int mLastOffset;

        private ReleaseScrollRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            int bottom;
            ValueAnimator valueAnimator = new ValueAnimator();
            this.mExpandOffsetAnimator = valueAnimator;
            valueAnimator.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
            this.mExpandOffsetAnimator.setDuration(400L);
            if (RelativeLayout.this.mAppWidgetToolBar.getBottom() > RelativeLayout.this.mAppWidgetToolBar.getHeight() / 2) {
                bottom = RelativeLayout.this.mAppWidgetToolBar.getTop();
            } else {
                bottom = RelativeLayout.this.mAppWidgetToolBar.getBottom();
            }
            this.mExpandOffsetAnimator.setIntValues(0, bottom);
            this.mExpandOffsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.RelativeLayout.ReleaseScrollRunnable.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    if (RelativeLayout.this.mAppWidgetListView == null || RelativeLayout.this.mAppWidgetToolBar == null) {
                        return;
                    }
                    int iIntValue = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                    int i = -(iIntValue - ReleaseScrollRunnable.this.mLastOffset);
                    RelativeLayout.this.mAppWidgetToolBar.offsetTopAndBottom(i);
                    RelativeLayout.this.mAppWidgetListView.offsetTopAndBottom(i);
                    ReleaseScrollRunnable.this.mLastOffset = iIntValue;
                }
            });
            this.mExpandOffsetAnimator.addListener(new AnimatorListenerAdapter() { // from class: android.widget.RelativeLayout.ReleaseScrollRunnable.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    if (animator instanceof ValueAnimator) {
                        ValueAnimator valueAnimator2 = (ValueAnimator) animator;
                        if (valueAnimator2.getAnimatedValue() != null) {
                            ReleaseScrollRunnable.this.mLastOffset = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                        }
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ReleaseScrollRunnable.this.mLastOffset = 0;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    ReleaseScrollRunnable.this.mLastOffset = 0;
                }
            });
            this.mExpandOffsetAnimator.start();
        }

        public void cancel() {
            ValueAnimator valueAnimator = this.mExpandOffsetAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.mExpandOffsetAnimator = null;
            }
        }
    }

    private class ExpandTopBarRunnable implements Runnable {
        private ValueAnimator mExpandOffsetAnimator;
        int mLastOffset;

        private ExpandTopBarRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (RelativeLayout.this.mAppWidgetToolBar.getTop() == 0) {
                return;
            }
            ValueAnimator valueAnimator = new ValueAnimator();
            this.mExpandOffsetAnimator = valueAnimator;
            valueAnimator.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
            this.mExpandOffsetAnimator.setDuration(400L);
            this.mExpandOffsetAnimator.setIntValues(0, RelativeLayout.this.mAppWidgetToolBar.getHeight());
            this.mExpandOffsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.RelativeLayout.ExpandTopBarRunnable.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator2) throws Resources.NotFoundException {
                    if (RelativeLayout.this.mAppWidgetListView == null || RelativeLayout.this.mAppWidgetToolBar == null) {
                        return;
                    }
                    int iIntValue = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                    int i = iIntValue - ExpandTopBarRunnable.this.mLastOffset;
                    if (RelativeLayout.this.mAppWidgetToolBar.getTop() == 0) {
                        return;
                    }
                    if (RelativeLayout.this.mAppWidgetToolBar.getTop() + i > 0) {
                        i = -RelativeLayout.this.mAppWidgetToolBar.getTop();
                    }
                    RelativeLayout.this.mAppWidgetListView.scrollListBy(i);
                    RelativeLayout.this.mAppWidgetListView.offsetTopAndBottom(i);
                    RelativeLayout.this.mAppWidgetToolBar.offsetTopAndBottom(i);
                    ExpandTopBarRunnable.this.mLastOffset = iIntValue;
                }
            });
            this.mExpandOffsetAnimator.addListener(new AnimatorListenerAdapter() { // from class: android.widget.RelativeLayout.ExpandTopBarRunnable.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    if (animator instanceof ValueAnimator) {
                        ValueAnimator valueAnimator2 = (ValueAnimator) animator;
                        if (valueAnimator2.getAnimatedValue() != null) {
                            ExpandTopBarRunnable.this.mLastOffset = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                        }
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ExpandTopBarRunnable.this.mLastOffset = 0;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    ExpandTopBarRunnable.this.mLastOffset = 0;
                }
            });
            this.mExpandOffsetAnimator.start();
        }

        public void cancel() {
            ValueAnimator valueAnimator = this.mExpandOffsetAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.mExpandOffsetAnimator = null;
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        AbsListView absListView = this.mAppWidgetListView;
        if (absListView != null) {
            absListView.removeCallbacks(this.mExpandTopBarRunnable);
            this.mAppWidgetListView.removeCallbacks(this.mReleaseScrollRunnable);
            this.mExpandTopBarRunnable.cancel();
            this.mExpandTopBarRunnable = null;
            this.mReleaseScrollRunnable.cancel();
            this.mReleaseScrollRunnable = null;
            this.mAppWidgetListView = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onStartNestedScroll(View view, View view2, int i) {
        if (this.mAppWidgetImmersiveEnabled) {
            return true;
        }
        return super.onStartNestedScroll(view, view2, i);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        View view2;
        if (!this.mAppWidgetImmersiveEnabled) {
            super.onNestedPreScroll(view, i, i2, iArr);
            return;
        }
        if (this.mAppWidgetListView == null || (view2 = this.mAppWidgetToolBar) == null) {
            super.onNestedPreScroll(view, i, i2, iArr);
            return;
        }
        if (i2 > 0) {
            if (view2.getBottom() > 0) {
                if (i2 > this.mAppWidgetToolBar.getBottom()) {
                    iArr[1] = this.mAppWidgetToolBar.getBottom();
                    int i3 = -this.mAppWidgetToolBar.getBottom();
                    this.mAppWidgetToolBar.offsetTopAndBottom(i3);
                    this.mAppWidgetListView.offsetTopAndBottom(i3);
                } else {
                    iArr[1] = i2;
                    int i4 = -i2;
                    this.mAppWidgetToolBar.offsetTopAndBottom(i4);
                    this.mAppWidgetListView.offsetTopAndBottom(i4);
                }
            }
        } else if (i2 < 0 && view2.getBottom() < this.mAppWidgetToolBar.getHeight()) {
            if (i2 < this.mAppWidgetToolBar.getTop()) {
                iArr[1] = i2 - this.mAppWidgetToolBar.getTop();
                int i5 = -this.mAppWidgetToolBar.getTop();
                this.mAppWidgetToolBar.offsetTopAndBottom(i5);
                this.mAppWidgetListView.offsetTopAndBottom(i5);
            } else {
                iArr[1] = i2;
                int i6 = -i2;
                this.mAppWidgetToolBar.offsetTopAndBottom(i6);
                this.mAppWidgetListView.offsetTopAndBottom(i6);
            }
        }
        super.onNestedPreScroll(view, i, i2, iArr);
    }

    @RemotableViewMethod
    public void semEnableAppWidgetImmersiveScroll(boolean z) {
        if (getChildCount() != 2) {
            Log.w(TAG, "Invalid child count for ImmersiveScroll");
            return;
        }
        if (!(getChildAt(1) instanceof AbsListView)) {
            Log.w(TAG, "Second view must ListView");
            return;
        }
        this.mAppWidgetToolBar = getChildAt(0);
        AbsListView absListView = (AbsListView) getChildAt(1);
        this.mAppWidgetListView = absListView;
        absListView.setNestedScrollingEnabled(true);
        AppWidgetListScrollListener appWidgetListScrollListener = new AppWidgetListScrollListener();
        this.mScrollListener = appWidgetListScrollListener;
        this.mAppWidgetListView.setOnScrollListener(appWidgetListScrollListener);
        this.mAppWidgetImmersiveEnabled = true;
        this.mReleaseScrollRunnable = new ReleaseScrollRunnable();
        this.mExpandTopBarRunnable = new ExpandTopBarRunnable();
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
