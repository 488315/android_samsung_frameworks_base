package android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Property;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.animation.ElasticCustom;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.SemExpandableListConnector;
import com.android.internal.R;
import com.samsung.android.animation.SemAnimatorUtils;
import com.samsung.android.rune.ViewRune;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class SemExpandableListView extends ListView {
    private static final int ANIMATION_STATE_COLLAPSING = 3;
    private static final int ANIMATION_STATE_COLLAPSING_ALL = 5;
    private static final int ANIMATION_STATE_EXPANDING = 2;
    private static final int ANIMATION_STATE_EXPANDING_ALL = 4;
    private static final int ANIMATION_STATE_IDLE = 1;
    public static final int CHILD_INDICATOR_INHERIT = -1;
    private static final int COLLAPSE_ALL_PENDING = 2;
    private static final boolean DEBUGGABLE = false;
    private static final int DECORATED_VIEW_TAG = 2047483647;
    private static final int[] EMPTY_STATE_SET;
    private static final int EXPAND_ALL_PENDING = 1;
    private static final int EXPAND_COLLAPSE_ALL_IDLE = 0;
    private static final int EXPAND_COLLAPSE_BASE_DURATION = 700;
    private static final int EXPAND_COLLAPSE_MIN_DURATION = 400;
    private static final int[] GROUP_EMPTY_STATE_SET;
    private static final int[] GROUP_EXPANDED_EMPTY_STATE_SET;
    private static final int[] GROUP_EXPANDED_STATE_SET;
    private static final int[][] GROUP_STATE_SETS;
    public static final int INDICATOR_ANIMATION_TYPE_MORPH = 2;
    public static final int INDICATOR_ANIMATION_TYPE_ROTATE = 1;
    private static final int INDICATOR_UNDEFINED = -2;
    private static final long PACKED_POSITION_FOOTER_VIEW_BASE = -4294967296L;
    private static final int PACKED_POSITION_GROUP_FOOTER_TYPE = -3;
    private static final int PACKED_POSITION_GROUP_HEADER_TYPE = -2;
    private static final long PACKED_POSITION_HEADER_VIEW_BASE = 9223372032559808512L;
    private static final long PACKED_POSITION_INT_MASK_CHILD = -1;
    private static final long PACKED_POSITION_INT_MASK_GROUP = 2147483647L;
    private static final long PACKED_POSITION_MASK_CHILD = 4294967295L;
    private static final long PACKED_POSITION_MASK_GROUP = 9223372032559808512L;
    private static final long PACKED_POSITION_MASK_TYPE = Long.MIN_VALUE;
    private static final long PACKED_POSITION_SHIFT_GROUP = 32;
    private static final long PACKED_POSITION_SHIFT_TYPE = 63;
    public static final int PACKED_POSITION_TYPE_CHILD = 1;
    public static final int PACKED_POSITION_TYPE_GROUP = 0;
    public static final int PACKED_POSITION_TYPE_NULL = 2;
    public static final long PACKED_POSITION_VALUE_NULL = 4294967295L;
    private static final int PAINT_ALPHA = 127;
    private static final int PAINT_STROKE_SIZE = 2;
    private static final String TAG = "SemExpandableListView";
    private ExpandableListAdapter mAdapter;
    private boolean mAnimationEnabled;
    private int mAnimationState;
    private Rect mBitmapUpdateBounds;
    ValueAnimator.AnimatorUpdateListener mBitmapUpdateListener;
    private boolean mBlockTouchEvent;
    private Drawable mChildDivider;
    private Drawable mChildIndicator;
    private int mChildIndicatorEnd;
    private int mChildIndicatorLeft;
    private int mChildIndicatorRight;
    private int mChildIndicatorStart;
    private int mCollapsedGroupTopEnd;
    private int mCollapsedGroupTopStart;
    private CollapsingRect[] mCollapsingRects;
    private SemExpandableListConnector mConnector;
    private String mDescriptionCollapse;
    private String mDescriptionExpand;
    private int[] mExpListDividerHeight;
    private int mExpandCollapseAllState;
    private ExpandingRect[] mExpandingRects;
    private ArrayList<ViewInfo> mGhostExpandCollapseChildViews;
    private ArrayList<ViewInfo> mGhostViews;
    private RectF mGhostViewsVisibleArea;
    private RectF[] mGhostViewsVisibleAreas;
    private Drawable mGroupIndicator;
    private int mGroupIndicatorColor;
    private int mGroupIndicatorHeight;
    private Paint mGroupIndicatorPaint;
    private int mGroupIndicatorWidth;
    private int mIndicatorAnimationType;
    private int mIndicatorEnd;
    private int mIndicatorGravity;
    private int mIndicatorLeft;
    public float mIndicatorPaddingHeight;
    private int mIndicatorPaddingLeft;
    private int mIndicatorPaddingRight;
    private int mIndicatorRight;
    private int mIndicatorStart;
    private SemExpandableListConnector.ItemDecorator mItemDecorator;
    private OnChildClickListener mOnChildClickListener;
    private OnGroupClickListener mOnGroupClickListener;
    private OnGroupCollapseListener mOnGroupCollapseListener;
    private OnGroupExpandListener mOnGroupExpandListener;
    private int mRotationAngle;
    private int mTranslationOffset;
    private LongSparseArray<ViewInfo> mViewSnapshots;
    private static final boolean DEBUGGABLE_LOW = ViewRune.COMMON_IS_PRODUCT_DEV;
    private static final int[] CHILD_LAST_STATE_SET = {16842918};
    private static Interpolator EXPAND_COLLAPSE_INTERPOLATOR = new PathInterpolator(0.33f, 0.0f, 0.2f, 1.0f);
    private static ElasticCustom mExpandInterpolator = new ElasticCustom(1.0f, 0.8f);

    public interface OnChildClickListener {
        boolean onChildClick(SemExpandableListView semExpandableListView, View view, int i, int i2, long j);
    }

    public interface OnGroupClickListener {
        boolean onGroupClick(SemExpandableListView semExpandableListView, View view, int i, long j);
    }

    public interface OnGroupCollapseListener {
        void onGroupCollapse(int i);
    }

    public interface OnGroupExpandListener {
        void onGroupExpand(int i);
    }

    public static int getPackedPositionChild(long j) {
        if (j != 4294967295L && (j & Long.MIN_VALUE) == Long.MIN_VALUE) {
            return (int) (j & 4294967295L);
        }
        return -1;
    }

    public static long getPackedPositionForChild(int i, int i2) {
        return i2 | ((i & PACKED_POSITION_INT_MASK_GROUP) << 32) | Long.MIN_VALUE;
    }

    public static long getPackedPositionForGroup(int i) {
        return (i & PACKED_POSITION_INT_MASK_GROUP) << 32;
    }

    public static int getPackedPositionGroup(long j) {
        if ((j & PACKED_POSITION_FOOTER_VIEW_BASE) == PACKED_POSITION_FOOTER_VIEW_BASE) {
            return -3;
        }
        long j2 = j & 9223372032559808512L;
        if (j2 == 9223372032559808512L) {
            return -2;
        }
        return (int) (j2 >> 32);
    }

    public static int getPackedPositionType(long j) {
        if (j == 4294967295L) {
            return 2;
        }
        return (j & Long.MIN_VALUE) == Long.MIN_VALUE ? 1 : 0;
    }

    @Deprecated
    public boolean expandCollapseAll(boolean z) {
        return false;
    }

    @Deprecated
    public boolean scrollTo(View view, int i, int i2, SemExpandableListConnector.PositionMetadata positionMetadata) {
        return false;
    }

    @Deprecated
    public void setCollapseAllSpeedFactor(float f) {
    }

    @Deprecated
    public void setCollapseSpeedFactor(float f) {
    }

    @Deprecated
    public void setExpandAllSpeedFactor(float f) {
    }

    @Deprecated
    public void setExpandSpeedFactor(float f) {
    }

    static {
        int[] iArr = new int[0];
        EMPTY_STATE_SET = iArr;
        int[] iArr2 = {16842920};
        GROUP_EXPANDED_STATE_SET = iArr2;
        int[] iArr3 = {16842921};
        GROUP_EMPTY_STATE_SET = iArr3;
        int[] iArr4 = {16842920, 16842921};
        GROUP_EXPANDED_EMPTY_STATE_SET = iArr4;
        GROUP_STATE_SETS = new int[][]{iArr, iArr2, iArr3, iArr4};
    }

    public SemExpandableListView(Context context) {
        this(context, null);
    }

    public SemExpandableListView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842863);
    }

    public SemExpandableListView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SemExpandableListView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        Drawable drawable;
        this.mBlockTouchEvent = false;
        this.mAnimationEnabled = true;
        this.mViewSnapshots = new LongSparseArray<>();
        this.mGhostViews = new ArrayList<>();
        this.mGhostExpandCollapseChildViews = new ArrayList<>();
        this.mBitmapUpdateBounds = new Rect();
        this.mTranslationOffset = 0;
        this.mCollapsedGroupTopStart = 0;
        this.mCollapsedGroupTopEnd = 0;
        this.mGhostViewsVisibleArea = new RectF();
        this.mIndicatorPaddingLeft = 0;
        this.mIndicatorPaddingRight = 0;
        this.mIndicatorGravity = 3;
        this.mRotationAngle = 180;
        this.mAnimationState = 1;
        this.mExpandCollapseAllState = 0;
        this.mIndicatorAnimationType = 1;
        this.mGroupIndicatorColor = -16777216;
        this.mItemDecorator = new SemExpandableListConnector.ItemDecorator() { // from class: android.widget.SemExpandableListView.1
            static final int WRAPPING_VIEW_ID = 2147482647;

            @Override // android.widget.SemExpandableListConnector.ItemDecorator
            public View onItemDecorate(View view, View view2, SemExpandableListConnector.PositionMetadata positionMetadata) {
                int i3;
                int i4;
                int i5;
                int i6;
                Drawable indicator;
                FrameLayout.LayoutParams layoutParams;
                boolean z = positionMetadata.position.flatListPos == SemExpandableListView.this.mConnector.getCount() - 1;
                int i7 = SemExpandableListView.this.mExpListDividerHeight[0];
                if (view != null && (view instanceof ViewGroup) && ((ViewGroup) view).getChildAt(0) == view2) {
                    if (view.getId() != WRAPPING_VIEW_ID || !(view instanceof FrameLayout)) {
                        throw new IllegalStateException("convertView is neither null nor the wrapping FrameLayout");
                    }
                    DecoratedItemViewHolder decoratedItemViewHolder = (DecoratedItemViewHolder) view.getTag(SemExpandableListView.DECORATED_VIEW_TAG);
                    Drawable indicator2 = SemExpandableListView.this.getIndicator(positionMetadata);
                    if (decoratedItemViewHolder != null) {
                        if (decoratedItemViewHolder.indicatorImgView != null) {
                            decoratedItemViewHolder.indicatorImgView.setIndicatorPos(positionMetadata.position);
                            if (SemExpandableListView.this.mChildIndicator == null && SemExpandableListView.this.mGroupIndicator == null) {
                                decoratedItemViewHolder.indicatorImgView.setVisibility(8);
                            } else {
                                decoratedItemViewHolder.indicatorImgView.setVisibility(0);
                            }
                            decoratedItemViewHolder.indicatorImgView.refreshDrawableState();
                            decoratedItemViewHolder.indicatorImgView.setContentDescription(positionMetadata.isExpanded() ? SemExpandableListView.this.mDescriptionCollapse : SemExpandableListView.this.mDescriptionExpand);
                            initIndicatorImageLayoutParams((FrameLayout.LayoutParams) decoratedItemViewHolder.indicatorImgView.getLayoutParams());
                        } else if (indicator2 != null) {
                            SemExpandableListView semExpandableListView = SemExpandableListView.this;
                            IndicatorImageView indicatorImageView = semExpandableListView.new IndicatorImageView(semExpandableListView.mContext);
                            indicatorImageView.setIndicatorPos(positionMetadata.position);
                            indicatorImageView.setImageDrawable(indicator2.getConstantState().newDrawable());
                            indicatorImageView.refreshDrawableState();
                            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, -2);
                            initIndicatorImageLayoutParams(layoutParams2);
                            indicatorImageView.setLayoutParams(layoutParams2);
                            ((FrameLayout) view).addView(indicatorImageView);
                            decoratedItemViewHolder.indicatorImgView = indicatorImageView;
                            decoratedItemViewHolder.indicatorImgView.setContentDescription(positionMetadata.isExpanded() ? SemExpandableListView.this.mDescriptionCollapse : SemExpandableListView.this.mDescriptionExpand);
                        }
                        adjustDivider(decoratedItemViewHolder, z);
                    }
                    return view;
                }
                FrameLayout frameLayout = new FrameLayout(SemExpandableListView.this.mContext);
                DecoratedItemViewHolder decoratedItemViewHolder2 = new DecoratedItemViewHolder();
                frameLayout.setTag(SemExpandableListView.DECORATED_VIEW_TAG, decoratedItemViewHolder2);
                frameLayout.setId(WRAPPING_VIEW_ID);
                frameLayout.addView(view2);
                decoratedItemViewHolder2.itemView = view2;
                int top = view2.getTop();
                int bottom = view2.getBottom();
                int i8 = SemExpandableListView.this.mBottom;
                if (bottom >= 0 && top <= i8) {
                    boolean isLayoutRtl = SemExpandableListView.this.isLayoutRtl();
                    int width = SemExpandableListView.this.getWidth();
                    if (positionMetadata.position.type == 1) {
                        i3 = SemExpandableListView.this.mChildIndicatorLeft == -1 ? SemExpandableListView.this.mIndicatorLeft : SemExpandableListView.this.mChildIndicatorLeft;
                        i4 = SemExpandableListView.this.mChildIndicatorRight == -1 ? SemExpandableListView.this.mIndicatorRight : SemExpandableListView.this.mChildIndicatorRight;
                    } else {
                        i3 = SemExpandableListView.this.mIndicatorLeft;
                        i4 = SemExpandableListView.this.mIndicatorRight;
                    }
                    if (isLayoutRtl) {
                        i5 = (width - i4) - SemExpandableListView.this.mPaddingRight;
                        i6 = (width - i3) - SemExpandableListView.this.mPaddingRight;
                    } else {
                        i5 = SemExpandableListView.this.mPaddingLeft + i3;
                        i6 = SemExpandableListView.this.mPaddingLeft + i4;
                    }
                    if (i5 != i6 && (indicator = SemExpandableListView.this.getIndicator(positionMetadata)) != null) {
                        SemExpandableListView semExpandableListView2 = SemExpandableListView.this;
                        IndicatorImageView indicatorImageView2 = semExpandableListView2.new IndicatorImageView(semExpandableListView2.mContext);
                        indicatorImageView2.setIndicatorPos(positionMetadata.position);
                        indicatorImageView2.setImageDrawable(indicator.getConstantState().newDrawable());
                        indicatorImageView2.refreshDrawableState();
                        if (SemExpandableListView.this.mIndicatorAnimationType == 1) {
                            layoutParams = new FrameLayout.LayoutParams(-2, -2);
                        } else {
                            int round = Math.round(SemExpandableListView.this.mIndicatorPaddingHeight * 2.0f);
                            if (SemExpandableListView.DEBUGGABLE_LOW) {
                                Log.d(SemExpandableListView.TAG, "onItemDecorate : mGroupIndicatorWidth = " + SemExpandableListView.this.mGroupIndicatorWidth + ", mGroupIndicatorHeight = " + SemExpandableListView.this.mGroupIndicatorHeight);
                                String str = SemExpandableListView.TAG;
                                StringBuilder sb = new StringBuilder("onItemDecorate : paddingHeight = ");
                                sb.append(round);
                                Log.d(str, sb.toString());
                            }
                            layoutParams = new FrameLayout.LayoutParams(SemExpandableListView.this.mGroupIndicatorWidth, SemExpandableListView.this.mGroupIndicatorHeight + round);
                        }
                        initIndicatorImageLayoutParams(layoutParams);
                        indicatorImageView2.setLayoutParams(layoutParams);
                        frameLayout.addView(indicatorImageView2);
                        decoratedItemViewHolder2.indicatorImgView = indicatorImageView2;
                        decoratedItemViewHolder2.indicatorImgView.setContentDescription(positionMetadata.isExpanded() ? SemExpandableListView.this.mDescriptionCollapse : SemExpandableListView.this.mDescriptionExpand);
                    }
                    if (i7 > 0) {
                        View view3 = new View(SemExpandableListView.this.mContext);
                        view3.setFocusable(false);
                        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(-1, i7);
                        layoutParams3.gravity = 80;
                        view3.setLayoutParams(layoutParams3);
                        view3.setBackground(SemExpandableListView.this.getDivider(positionMetadata));
                        frameLayout.addView(view3);
                        decoratedItemViewHolder2.dividerView = view3;
                        adjustDivider(decoratedItemViewHolder2, z);
                    }
                }
                return frameLayout;
            }

            private void initIndicatorImageLayoutParams(FrameLayout.LayoutParams layoutParams) {
                layoutParams.gravity = SemExpandableListView.this.mIndicatorGravity | 16;
                layoutParams.leftMargin = SemExpandableListView.this.mIndicatorPaddingLeft;
                layoutParams.rightMargin = SemExpandableListView.this.mIndicatorPaddingRight;
            }

            private void adjustDivider(DecoratedItemViewHolder decoratedItemViewHolder, boolean z) {
                if (decoratedItemViewHolder.dividerView == null) {
                    return;
                }
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) decoratedItemViewHolder.itemView.getLayoutParams();
                int i3 = SemExpandableListView.this.mExpListDividerHeight[0];
                int i4 = z ? 8 : 0;
                int i5 = z ? 0 : i3;
                decoratedItemViewHolder.dividerView.setVisibility(i4);
                marginLayoutParams.bottomMargin = i5;
                if (decoratedItemViewHolder.indicatorImgView != null) {
                    ((FrameLayout.LayoutParams) decoratedItemViewHolder.indicatorImgView.getLayoutParams()).bottomMargin = i5 / 2;
                }
            }

            @Override // android.widget.SemExpandableListConnector.ItemDecorator
            public View unfoldDecoratedView(View view) {
                if (view == null) {
                    return null;
                }
                return (view.getId() == WRAPPING_VIEW_ID && (view instanceof FrameLayout)) ? ((FrameLayout) view).getChildAt(0) : view;
            }
        };
        this.mBitmapUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.SemExpandableListView.9
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                int size = SemExpandableListView.this.mGhostViews.size();
                int size2 = SemExpandableListView.this.mGhostExpandCollapseChildViews.size();
                if (SemExpandableListView.this.mAnimationState == 2) {
                    SemExpandableListView.this.mGhostViewsVisibleArea.bottom = SemExpandableListView.this.mGhostViewsVisibleArea.top + (SemExpandableListView.this.mTranslationOffset * animatedFraction);
                } else if (SemExpandableListView.this.mAnimationState == 3) {
                    SemExpandableListView.this.mGhostViewsVisibleArea.top = SemExpandableListView.this.mCollapsedGroupTopStart + ((SemExpandableListView.this.mCollapsedGroupTopEnd - SemExpandableListView.this.mCollapsedGroupTopStart) * animatedFraction);
                    SemExpandableListView.this.mGhostViewsVisibleArea.bottom = SemExpandableListView.this.mGhostViewsVisibleArea.top + (SemExpandableListView.this.mTranslationOffset * (1.0f - animatedFraction));
                } else if (SemExpandableListView.this.mAnimationState == 4 && SemExpandableListView.this.mExpandingRects != null) {
                    for (ExpandingRect expandingRect : SemExpandableListView.this.mExpandingRects) {
                        if (expandingRect != null) {
                            expandingRect.update(animatedFraction);
                        }
                    }
                } else if (SemExpandableListView.this.mAnimationState == 5 && SemExpandableListView.this.mCollapsingRects != null) {
                    for (CollapsingRect collapsingRect : SemExpandableListView.this.mCollapsingRects) {
                        if (collapsingRect != null) {
                            collapsingRect.update(animatedFraction);
                        }
                    }
                }
                if (size + size2 == 0) {
                    return;
                }
                SemExpandableListView.this.mBitmapUpdateBounds.setEmpty();
                for (int i3 = 0; i3 < size; i3++) {
                    SemExpandableListView.this.mBitmapUpdateBounds.union(((ViewInfo) SemExpandableListView.this.mGhostViews.get(i3)).snapshot.getBounds());
                }
                for (int i4 = 0; i4 < size2; i4++) {
                    SemExpandableListView.this.mBitmapUpdateBounds.union(((ViewInfo) SemExpandableListView.this.mGhostExpandCollapseChildViews.get(i4)).snapshot.getBounds());
                }
                SemExpandableListView semExpandableListView = SemExpandableListView.this;
                semExpandableListView.invalidate(semExpandableListView.mBitmapUpdateBounds);
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ExpandableListView, i, i2);
        this.mGroupIndicator = obtainStyledAttributes.getDrawable(0);
        this.mChildIndicator = obtainStyledAttributes.getDrawable(1);
        this.mIndicatorLeft = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(3, 0);
        this.mIndicatorRight = dimensionPixelSize;
        if (dimensionPixelSize == 0 && (drawable = this.mGroupIndicator) != null) {
            this.mIndicatorRight = this.mIndicatorLeft + drawable.getIntrinsicWidth();
        }
        this.mChildIndicatorLeft = obtainStyledAttributes.getDimensionPixelSize(4, -1);
        this.mChildIndicatorRight = obtainStyledAttributes.getDimensionPixelSize(5, -1);
        this.mChildDivider = obtainStyledAttributes.getDrawable(6);
        if (!isRtlCompatibilityMode()) {
            this.mIndicatorStart = obtainStyledAttributes.getDimensionPixelSize(7, -2);
            this.mIndicatorEnd = obtainStyledAttributes.getDimensionPixelSize(8, -2);
            this.mChildIndicatorStart = obtainStyledAttributes.getDimensionPixelSize(9, -1);
            this.mChildIndicatorEnd = obtainStyledAttributes.getDimensionPixelSize(10, -1);
        }
        obtainStyledAttributes.recycle();
        if (this.mExpListDividerHeight == null) {
            this.mExpListDividerHeight = new int[1];
        }
        this.mDescriptionExpand = this.mContext.getResources().getString(R.string.expandablelist_indicator_description, this.mContext.getResources().getString(R.string.expandablelist_expand));
        this.mDescriptionCollapse = this.mContext.getResources().getString(R.string.expandablelist_indicator_description, this.mContext.getResources().getString(R.string.expandablelist_collapse));
        this.mGroupIndicatorWidth = (int) this.mContext.getResources().getDimension(R.dimen.expandablelist_indicator_width);
        this.mGroupIndicatorHeight = (int) this.mContext.getResources().getDimension(R.dimen.expandablelist_indicator_height);
        this.mIndicatorPaddingHeight = this.mContext.getResources().getDimension(R.dimen.expandablelist_indicator_padding_height);
        Paint paint = new Paint();
        this.mGroupIndicatorPaint = paint;
        paint.setAntiAlias(true);
        this.mGroupIndicatorPaint.setColor(this.mGroupIndicatorColor);
        this.mGroupIndicatorPaint.setAlpha(127);
        this.mGroupIndicatorPaint.setStyle(Paint.Style.STROKE);
        this.mGroupIndicatorPaint.setStrokeWidth((int) ((this.mContext.getResources().getDisplayMetrics().density * 2.0f) + 0.5f));
    }

    private boolean isRtlCompatibilityMode() {
        return this.mContext.getApplicationInfo().targetSdkVersion < 17 || !hasRtlSupport();
    }

    private boolean hasRtlSupport() {
        return this.mContext.getApplicationInfo().hasRtlSupport();
    }

    @Override // android.widget.AbsListView, android.view.View
    public void onRtlPropertiesChanged(int i) {
        resolveIndicator();
        resolveChildIndicator();
    }

    private boolean isHoverable() {
        if (isEnabled()) {
            return isClickable() || isLongClickable();
        }
        return false;
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (!isHoverable() && isHovered() && actionMasked == 10) {
            setHovered(false);
            return false;
        }
        return super.onHoverEvent(motionEvent);
    }

    private void resolveIndicator() {
        Drawable drawable;
        if (isLayoutRtl()) {
            int i = this.mIndicatorStart;
            if (i >= 0) {
                this.mIndicatorRight = i;
            }
            int i2 = this.mIndicatorEnd;
            if (i2 >= 0) {
                this.mIndicatorLeft = i2;
            }
        } else {
            int i3 = this.mIndicatorStart;
            if (i3 >= 0) {
                this.mIndicatorLeft = i3;
            }
            int i4 = this.mIndicatorEnd;
            if (i4 >= 0) {
                this.mIndicatorRight = i4;
            }
        }
        if (this.mIndicatorRight != 0 || (drawable = this.mGroupIndicator) == null) {
            return;
        }
        this.mIndicatorRight = this.mIndicatorLeft + drawable.getIntrinsicWidth();
    }

    private void resolveChildIndicator() {
        if (isLayoutRtl()) {
            int i = this.mChildIndicatorStart;
            if (i >= -1) {
                this.mChildIndicatorRight = i;
            }
            int i2 = this.mChildIndicatorEnd;
            if (i2 >= -1) {
                this.mChildIndicatorLeft = i2;
                return;
            }
            return;
        }
        int i3 = this.mChildIndicatorStart;
        if (i3 >= -1) {
            this.mChildIndicatorLeft = i3;
        }
        int i4 = this.mChildIndicatorEnd;
        if (i4 >= -1) {
            this.mChildIndicatorRight = i4;
        }
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        int i = this.mAnimationState;
        if (i == 3 || i == 5) {
            drawGhostViews(canvas);
        }
        super.dispatchDraw(canvas);
        int i2 = this.mAnimationState;
        if (i2 == 2 || i2 == 4) {
            drawGhostViews(canvas);
        }
    }

    private void drawGhostViews(Canvas canvas) {
        if (this.mGhostViews.size() + this.mGhostExpandCollapseChildViews.size() == 0) {
            return;
        }
        int save = canvas.save();
        if (this.mAnimationState == 3) {
            canvas.clipRect(this.mGhostViewsVisibleArea);
        }
        Iterator<ViewInfo> it = this.mGhostViews.iterator();
        while (it.hasNext()) {
            it.next().snapshot.draw(canvas);
        }
        if (this.mAnimationState == 2) {
            canvas.clipRect(this.mGhostViewsVisibleArea);
            Iterator<ViewInfo> it2 = this.mGhostExpandCollapseChildViews.iterator();
            while (it2.hasNext()) {
                it2.next().snapshot.draw(canvas);
            }
        }
        if (this.mAnimationState == 4) {
            int length = this.mExpandingRects.length;
            RectF rectF = new RectF();
            for (int i = 0; i < length; i++) {
                ExpandingRect expandingRect = this.mExpandingRects[i];
                if (expandingRect != null) {
                    rectF.union(expandingRect.destinationRect);
                }
            }
            canvas.clipRect(rectF);
            Iterator<ViewInfo> it3 = this.mGhostExpandCollapseChildViews.iterator();
            while (it3.hasNext()) {
                it3.next().snapshot.draw(canvas);
            }
        }
        if (this.mAnimationState == 5) {
            int length2 = this.mCollapsingRects.length;
            RectF rectF2 = new RectF();
            for (int i2 = 0; i2 < length2; i2++) {
                CollapsingRect collapsingRect = this.mCollapsingRects[i2];
                if (collapsingRect != null) {
                    rectF2.union(collapsingRect.destinationRect);
                }
            }
            canvas.clipRect(rectF2);
            Iterator<ViewInfo> it4 = this.mGhostExpandCollapseChildViews.iterator();
            while (it4.hasNext()) {
                it4.next().snapshot.draw(canvas);
            }
        }
        canvas.restoreToCount(save);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Drawable getIndicator(SemExpandableListConnector.PositionMetadata positionMetadata) {
        int[] iArr;
        if (positionMetadata.position.type == 2) {
            Drawable drawable = this.mGroupIndicator;
            if (drawable != null && drawable.isStateful()) {
                boolean z = positionMetadata.groupMetadata == null || positionMetadata.groupMetadata.lastChildFlPos == positionMetadata.groupMetadata.flPos;
                drawable.setState(GROUP_STATE_SETS[(positionMetadata.isExpanded() ? 1 : 0) | (z ? 2 : 0)]);
            }
            return drawable;
        }
        Drawable drawable2 = this.mChildIndicator;
        if (drawable2 != null && drawable2.isStateful()) {
            if (positionMetadata.position.flatListPos == positionMetadata.groupMetadata.lastChildFlPos) {
                iArr = CHILD_LAST_STATE_SET;
            } else {
                iArr = EMPTY_STATE_SET;
            }
            drawable2.setState(iArr);
        }
        return drawable2;
    }

    public void setChildDivider(Drawable drawable) {
        this.mChildDivider = drawable;
    }

    @Override // android.widget.ListView
    void drawDivider(Canvas canvas, Rect rect, int i) {
        int i2 = i + this.mFirstPosition;
        if (i2 >= 0) {
            SemExpandableListConnector.PositionMetadata unflattenedPos = this.mConnector.getUnflattenedPos(getFlatPositionForConnector(i2));
            if (unflattenedPos.position.type == 1 || (unflattenedPos.isExpanded() && unflattenedPos.groupMetadata.lastChildFlPos != unflattenedPos.groupMetadata.flPos)) {
                Drawable drawable = this.mChildDivider;
                drawable.setBounds(rect);
                drawable.draw(canvas);
                unflattenedPos.recycle();
                return;
            }
            unflattenedPos.recycle();
        }
        super.drawDivider(canvas, rect, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Drawable getDivider(SemExpandableListConnector.PositionMetadata positionMetadata) {
        if (positionMetadata.position.type == 1) {
            return this.mChildDivider;
        }
        return this.mDivider;
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView
    public void setAdapter(ListAdapter listAdapter) {
        throw new RuntimeException("For ExpandableListView, use setAdapter(ExpandableListAdapter) instead of setAdapter(ListAdapter)");
    }

    @Override // android.widget.ListView, android.widget.AdapterView
    public ListAdapter getAdapter() {
        return super.getAdapter();
    }

    @Override // android.widget.AdapterView
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        super.setOnItemClickListener(onItemClickListener);
    }

    public void setAdapter(ExpandableListAdapter expandableListAdapter) {
        this.mAdapter = expandableListAdapter;
        if (expandableListAdapter != null) {
            SemExpandableListConnector semExpandableListConnector = new SemExpandableListConnector(expandableListAdapter);
            this.mConnector = semExpandableListConnector;
            semExpandableListConnector.setItemDecorator(this.mItemDecorator);
        } else {
            this.mConnector = null;
        }
        super.setAdapter((ListAdapter) this.mConnector);
    }

    public ExpandableListAdapter getExpandableListAdapter() {
        return this.mAdapter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isHeaderOrFooterPosition(int i) {
        return i < getHeaderViewsCount() || i >= this.mItemCount - getFooterViewsCount();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getFlatPositionForConnector(int i) {
        return i - getHeaderViewsCount();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getAbsoluteFlatPosition(int i) {
        return i + getHeaderViewsCount();
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView
    public boolean performItemClick(View view, int i, long j) {
        View unfoldDecoratedView = this.mItemDecorator.unfoldDecoratedView(view);
        if (isHeaderOrFooterPosition(i)) {
            return super.performItemClick(unfoldDecoratedView, i, j);
        }
        return handleItemClick(unfoldDecoratedView, getFlatPositionForConnector(i), j);
    }

    boolean handleItemClick(View view, int i, long j) {
        SemExpandableListView semExpandableListView;
        SemExpandableListConnector.PositionMetadata unflattenedPos = this.mConnector.getUnflattenedPos(i);
        long childOrGroupId = getChildOrGroupId(unflattenedPos.position);
        boolean z = false;
        if (unflattenedPos.position.type == 2) {
            OnGroupClickListener onGroupClickListener = this.mOnGroupClickListener;
            if (onGroupClickListener != null) {
                semExpandableListView = this;
                if (onGroupClickListener.onGroupClick(semExpandableListView, view, unflattenedPos.position.groupPos, childOrGroupId)) {
                    semExpandableListView.playSoundEffect(0);
                    unflattenedPos.recycle();
                    return true;
                }
            } else {
                semExpandableListView = this;
            }
            if (semExpandableListView.mAnimationEnabled) {
                semExpandableListView.captureViewsPriorAnimation();
            }
            final int i2 = unflattenedPos.position.groupPos;
            if (unflattenedPos.isExpanded()) {
                Runnable runnable = new Runnable() { // from class: android.widget.SemExpandableListView.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (SemExpandableListView.this.mOnGroupCollapseListener != null) {
                            SemExpandableListView.this.mOnGroupCollapseListener.onGroupCollapse(i2);
                        }
                    }
                };
                if (semExpandableListView.mAnimationEnabled) {
                    semExpandableListView.startCollapseAnimation(i2, runnable);
                }
                semExpandableListView.mConnector.collapseGroup(unflattenedPos);
                semExpandableListView.post(new Runnable() { // from class: android.widget.SemExpandableListView.3
                    @Override // java.lang.Runnable
                    public void run() {
                        SemExpandableListView.this.requestLayout();
                    }
                });
                semExpandableListView.playSoundEffect(0);
                if (!semExpandableListView.mAnimationEnabled) {
                    runnable.run();
                }
            } else {
                semExpandableListView.mConnector.expandGroup(unflattenedPos);
                semExpandableListView.playSoundEffect(0);
                Runnable runnable2 = new Runnable() { // from class: android.widget.SemExpandableListView.4
                    @Override // java.lang.Runnable
                    public void run() {
                        if (SemExpandableListView.this.mOnGroupExpandListener != null) {
                            SemExpandableListView.this.mOnGroupExpandListener.onGroupExpand(i2);
                        }
                    }
                };
                if (semExpandableListView.mAnimationEnabled) {
                    semExpandableListView.startExpandAnimation(i2, runnable2);
                } else {
                    runnable2.run();
                }
            }
            z = true;
        } else if (this.mOnChildClickListener != null) {
            playSoundEffect(0);
            return this.mOnChildClickListener.onChildClick(this, view, unflattenedPos.position.groupPos, unflattenedPos.position.childPos, childOrGroupId);
        }
        unflattenedPos.recycle();
        return z;
    }

    private void startExpandAnimation(final int i, final Runnable runnable) {
        this.mBlockTouchEvent = true;
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: android.widget.SemExpandableListView.5
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                SemExpandableListView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                int childCount = SemExpandableListView.this.getChildCount();
                if (childCount == 0) {
                    SemExpandableListView.this.resetExpandAnimationState();
                    runnable.run();
                    return true;
                }
                long packedPositionForGroup = SemExpandableListView.getPackedPositionForGroup(i);
                long packedPositionForGroup2 = SemExpandableListView.getPackedPositionForGroup(i + 1);
                int flatListPosition = SemExpandableListView.this.getFlatListPosition(packedPositionForGroup);
                int flatListPosition2 = SemExpandableListView.this.getFlatListPosition(packedPositionForGroup2);
                int firstVisiblePosition = SemExpandableListView.this.getFirstVisiblePosition();
                View childAt = SemExpandableListView.this.getChildAt(flatListPosition - firstVisiblePosition);
                if (childAt == null) {
                    Log.e(SemExpandableListView.TAG, "startExpandAnimation() groupPos=" + i + ", firstPos=" + firstVisiblePosition + ", expGroupFlatPos=" + flatListPosition);
                    SemExpandableListView.this.resetExpandAnimationState();
                    runnable.run();
                    return true;
                }
                View childAt2 = SemExpandableListView.this.getChildAt(flatListPosition2 - firstVisiblePosition);
                if (childAt2 == null) {
                    int height = SemExpandableListView.this.getHeight();
                    SemExpandableListView semExpandableListView = SemExpandableListView.this;
                    SemExpandableListView.this.mTranslationOffset = Math.min(height, semExpandableListView.getChildAt(semExpandableListView.getChildCount() - 1).getBottom()) - childAt.getBottom();
                } else {
                    SemExpandableListView.this.mTranslationOffset = childAt2.getTop() - childAt.getBottom();
                }
                SemExpandableListView.this.mGhostViewsVisibleArea.left = 0.0f;
                SemExpandableListView.this.mGhostViewsVisibleArea.right = SemExpandableListView.this.getWidth();
                SemExpandableListView.this.mGhostViewsVisibleArea.top = childAt.getBottom();
                SemExpandableListView.this.mGhostViewsVisibleArea.bottom = SemExpandableListView.this.mGhostViewsVisibleArea.top;
                int expandCollapseDuration = SemExpandableListView.this.getExpandCollapseDuration();
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < childCount; i2++) {
                    int i3 = i2 + firstVisiblePosition;
                    View childAt3 = SemExpandableListView.this.getChildAt(i2);
                    long expandableListPosition = SemExpandableListView.this.getExpandableListPosition(i3);
                    ViewInfo viewInfo = (ViewInfo) SemExpandableListView.this.mViewSnapshots.get(expandableListPosition);
                    if (viewInfo != null) {
                        SemExpandableListView.this.mViewSnapshots.remove(expandableListPosition);
                        if (childAt3.getTop() != viewInfo.top) {
                            childAt3.setTranslationY(-SemExpandableListView.this.mTranslationOffset);
                            arrayList.add(ObjectAnimator.ofFloat(childAt3, View.TRANSLATION_Y, 0.0f));
                        }
                    } else if (childAt3.getWidth() != 0 && childAt3.getHeight() != 0) {
                        SemExpandableListConnector.PositionMetadata unflattenedPos = SemExpandableListView.this.mConnector.getUnflattenedPos(SemExpandableListView.this.getFlatPositionForConnector(i3));
                        if (unflattenedPos.isExpanded() && unflattenedPos.position.groupPos == i && unflattenedPos.position.childPos != -1) {
                            SemExpandableListView.this.mGhostExpandCollapseChildViews.add(new ViewInfo(childAt3));
                            childAt3.setAlpha(0.0f);
                        }
                        unflattenedPos.recycle();
                    }
                }
                SemExpandableListView.this.startIndicatorAnimation(childAt, true, expandCollapseDuration);
                int size = SemExpandableListView.this.mViewSnapshots.size();
                for (int i4 = 0; i4 < size; i4++) {
                    ViewInfo viewInfo2 = (ViewInfo) SemExpandableListView.this.mViewSnapshots.valueAt(i4);
                    SemExpandableListView semExpandableListView2 = SemExpandableListView.this;
                    arrayList.add(semExpandableListView2.createViewSnapshotAnimation(semExpandableListView2.mTranslationOffset, viewInfo2));
                    SemExpandableListView.this.mGhostViews.add(viewInfo2);
                }
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.addUpdateListener(SemExpandableListView.this.mBitmapUpdateListener);
                arrayList.add(ofFloat);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(arrayList);
                animatorSet.setDuration(expandCollapseDuration);
                animatorSet.setInterpolator(SemExpandableListView.EXPAND_COLLAPSE_INTERPOLATOR);
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: android.widget.SemExpandableListView.5.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        SemExpandableListView.this.mAnimationState = 2;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        Log.d(SemExpandableListView.TAG, "expand animation finished");
                        runnable.run();
                        SemExpandableListView.this.resetExpandAnimationState();
                    }
                });
                animatorSet.start();
                SemExpandableListView.this.mViewSnapshots.clear();
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetExpandAnimationState() {
        this.mGhostViews.clear();
        this.mGhostExpandCollapseChildViews.clear();
        this.mTranslationOffset = 0;
        this.mAnimationState = 1;
        this.mGhostViewsVisibleAreas = null;
        this.mExpandingRects = null;
        this.mBlockTouchEvent = false;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).setAlpha(1.0f);
        }
    }

    private void startExpandAllAnimation(final boolean[] zArr, final Runnable runnable) {
        this.mBlockTouchEvent = true;
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: android.widget.SemExpandableListView.6
            /* JADX WARN: Removed duplicated region for block: B:35:0x0162  */
            /* JADX WARN: Removed duplicated region for block: B:38:0x01b0 A[SYNTHETIC] */
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public boolean onPreDraw() {
                /*
                    Method dump skipped, instructions count: 564
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: android.widget.SemExpandableListView.AnonymousClass6.onPreDraw():boolean");
            }
        });
    }

    private static class ExpandingRect {
        RectF destinationRect;
        RectF endRect;
        int startY;

        ExpandingRect(int i, RectF rectF, RectF rectF2) {
            this.startY = i;
            this.endRect = rectF;
            this.destinationRect = rectF2;
        }

        void update(float f) {
            this.destinationRect.left = this.endRect.left;
            this.destinationRect.right = this.endRect.right;
            this.destinationRect.top = this.startY + ((this.endRect.top - this.startY) * f);
            RectF rectF = this.destinationRect;
            rectF.bottom = rectF.top + (this.endRect.height() * f);
        }
    }

    private static class CollapsingRect {
        RectF destinationRect;
        int finishY;
        RectF startRect;

        CollapsingRect(RectF rectF, RectF rectF2) {
            this.startRect = rectF;
            this.destinationRect = rectF2;
        }

        void setFinishY(int i) {
            this.finishY = i;
        }

        void update(float f) {
            this.destinationRect.left = this.startRect.left;
            this.destinationRect.right = this.startRect.right;
            this.destinationRect.top = this.startRect.top + ((this.finishY - this.startRect.top) * f);
            RectF rectF = this.destinationRect;
            rectF.bottom = rectF.top + (this.startRect.height() * (1.0f - f));
        }
    }

    protected int getExpandCollapseDuration() {
        int sqrt = (int) (Math.sqrt(this.mTranslationOffset / getHeight()) * 700.0d);
        if (sqrt < 400) {
            return 400;
        }
        return sqrt;
    }

    @Override // android.widget.ListView, android.widget.AbsListView
    protected void layoutChildren() {
        Rect rect = (this.mAnimationState != 3 || this.mSelectorRect == null) ? null : new Rect(this.mSelectorRect);
        super.layoutChildren();
        if (this.mAnimationState != 3 || rect == null) {
            return;
        }
        this.mSelectorRect.set(rect);
    }

    private void startCollapseAnimation(final int i, final Runnable runnable) {
        long packedPositionForGroup = getPackedPositionForGroup(i);
        long packedPositionForGroup2 = getPackedPositionForGroup(i + 1);
        final int flatListPosition = getFlatListPosition(packedPositionForGroup);
        int flatListPosition2 = getFlatListPosition(packedPositionForGroup2);
        final int firstVisiblePosition = getFirstVisiblePosition();
        View childAt = getChildAt(flatListPosition - firstVisiblePosition);
        if (childAt == null) {
            Log.e(TAG, "startCollapseAnimation() BEFORE: groupPos=" + i + ", flatPos=" + flatListPosition + ", firstPos=" + firstVisiblePosition);
            resetCollapseAnimationState();
            runnable.run();
            return;
        }
        final int top = childAt.getTop();
        View childAt2 = getChildAt(flatListPosition2 - firstVisiblePosition);
        if (childAt2 == null) {
            this.mTranslationOffset = Math.min(getHeight(), getChildAt(getChildCount() - 1).getBottom()) - childAt.getBottom();
        } else {
            this.mTranslationOffset = childAt2.getTop() - childAt.getBottom();
        }
        final int groupCount = this.mAdapter.getGroupCount();
        final int childCount = getChildCount();
        this.mGhostViewsVisibleArea.left = 0.0f;
        this.mGhostViewsVisibleArea.right = getWidth();
        this.mCollapsedGroupTopStart = childAt.getBottom();
        this.mBlockTouchEvent = true;
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: android.widget.SemExpandableListView.7
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                char c;
                int i2;
                int i3;
                SemExpandableListView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                int childCount2 = SemExpandableListView.this.getChildCount();
                if (childCount2 == 0) {
                    SemExpandableListView.this.resetCollapseAnimationState();
                    runnable.run();
                    return true;
                }
                int expandCollapseDuration = SemExpandableListView.this.getExpandCollapseDuration();
                ArrayList arrayList = new ArrayList();
                int firstVisiblePosition2 = SemExpandableListView.this.getFirstVisiblePosition();
                int flatListPosition3 = SemExpandableListView.this.getFlatListPosition(SemExpandableListView.getPackedPositionForGroup(i));
                View childAt3 = SemExpandableListView.this.getChildAt(flatListPosition3 - firstVisiblePosition2);
                if (childAt3 == null) {
                    Log.e(SemExpandableListView.TAG, "startCollapseAnimation() BEFORE: groupPos=" + i + ", flatPos=" + flatListPosition + ", groupCount=" + groupCount + ", firstPos=" + firstVisiblePosition + ", totalListChildrenCount=" + childCount + "; AFTER: flatPos=" + flatListPosition3 + ", groupCount=" + SemExpandableListView.this.mAdapter.getGroupCount() + ", firstPos=" + firstVisiblePosition2 + ", totalListChildrenCount=" + SemExpandableListView.this.getChildCount());
                    SemExpandableListView.this.resetCollapseAnimationState();
                    runnable.run();
                    return true;
                }
                int top2 = childAt3.getTop() - top;
                for (int i4 = 0; i4 < childCount2; i4++) {
                    View childAt4 = SemExpandableListView.this.getChildAt(i4);
                    int i5 = i4 + firstVisiblePosition2;
                    long expandableListPosition = SemExpandableListView.this.getExpandableListPosition(i5);
                    ViewInfo viewInfo = (ViewInfo) SemExpandableListView.this.mViewSnapshots.get(expandableListPosition);
                    if (viewInfo != null) {
                        c = 0;
                        SemExpandableListView.this.mViewSnapshots.remove(expandableListPosition);
                        i3 = viewInfo.top - childAt4.getTop();
                    } else {
                        c = 0;
                        int packedPositionGroup = SemExpandableListView.getPackedPositionGroup(expandableListPosition);
                        if (packedPositionGroup == -3) {
                            i2 = SemExpandableListView.this.mTranslationOffset;
                        } else if (packedPositionGroup != -2 && packedPositionGroup > i) {
                            i2 = SemExpandableListView.this.mTranslationOffset;
                        } else {
                            i3 = -top2;
                        }
                        i3 = i2 - top2;
                    }
                    if (i3 != 0) {
                        if (i5 == flatListPosition3 && SemExpandableListView.this.mSelectorRect != null) {
                            arrayList.add(SemExpandableListView.this.getSelectorRectAnim(i3));
                        }
                        childAt4.setTranslationY(i3);
                        Property<View, Float> property = View.TRANSLATION_Y;
                        float[] fArr = new float[1];
                        fArr[c] = 0.0f;
                        arrayList.add(ObjectAnimator.ofFloat(childAt4, property, fArr));
                    }
                }
                SemExpandableListView.this.startIndicatorAnimation(childAt3, false, expandCollapseDuration);
                int size = SemExpandableListView.this.mViewSnapshots.size();
                for (int i6 = 0; i6 < size; i6++) {
                    ViewInfo viewInfo2 = (ViewInfo) SemExpandableListView.this.mViewSnapshots.valueAt(i6);
                    ObjectAnimator createViewSnapshotAnimation = SemExpandableListView.this.createViewSnapshotAnimation(top2, viewInfo2);
                    SemExpandableListView.this.mGhostViews.add(viewInfo2);
                    arrayList.add(createViewSnapshotAnimation);
                }
                if (size > 0) {
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                    ofFloat.addUpdateListener(SemExpandableListView.this.mBitmapUpdateListener);
                    arrayList.add(ofFloat);
                }
                SemExpandableListView semExpandableListView = SemExpandableListView.this;
                semExpandableListView.mCollapsedGroupTopEnd = semExpandableListView.mCollapsedGroupTopStart + top2;
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(arrayList);
                animatorSet.setDuration(expandCollapseDuration);
                animatorSet.setInterpolator(SemExpandableListView.EXPAND_COLLAPSE_INTERPOLATOR);
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: android.widget.SemExpandableListView.7.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        SemExpandableListView.this.mAnimationState = 3;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        Log.d(SemExpandableListView.TAG, "expand animation finished");
                        runnable.run();
                        SemExpandableListView.this.resetCollapseAnimationState();
                    }
                });
                animatorSet.start();
                SemExpandableListView.this.mViewSnapshots.clear();
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetCollapseAnimationState() {
        this.mCollapsedGroupTopStart = 0;
        this.mCollapsedGroupTopEnd = 0;
        this.mGhostViews.clear();
        this.mGhostExpandCollapseChildViews.clear();
        this.mTranslationOffset = 0;
        this.mAnimationState = 1;
        this.mGhostViewsVisibleAreas = null;
        this.mCollapsingRects = null;
        this.mBlockTouchEvent = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Animator getSelectorRectAnim(int i) {
        Rect rect = new Rect(this.mSelectorRect);
        Rect rect2 = new Rect(rect);
        rect2.offset(0, i);
        return ObjectAnimator.ofObject(this.mSelectorRect, "", SemAnimatorUtils.BOUNDS_EVALUATOR, rect2, rect);
    }

    private void startCollapseAllAnimation(final boolean[] zArr, final Runnable runnable) {
        int firstVisiblePosition = getFirstVisiblePosition();
        int lastNonFooterPosition = getLastNonFooterPosition();
        if (lastNonFooterPosition < firstVisiblePosition) {
            return;
        }
        final int packedPositionGroup = getPackedPositionGroup(getExpandableListPosition(lastNonFooterPosition));
        final int bottom = getChildAt(lastNonFooterPosition).getBottom();
        int i = packedPositionGroup + 1;
        int[] iArr = new int[i];
        this.mGhostViewsVisibleAreas = new RectF[i];
        this.mCollapsingRects = new CollapsingRect[i];
        int childCount = getChildCount();
        View childAt = getChildAt(getAbsoluteFlatPosition(0));
        int i2 = 1;
        while (i2 < i) {
            View childAt2 = getChildAt(getFlatListPosition(getPackedPositionForGroup(i2)));
            int i3 = i2 - 1;
            iArr[i2] = (iArr[i3] + childAt2.getTop()) - childAt.getBottom();
            RectF rectF = new RectF(childAt.getLeft(), childAt.getBottom(), childAt.getRight(), childAt2.getTop());
            this.mGhostViewsVisibleAreas[i3] = new RectF();
            this.mCollapsingRects[i3] = new CollapsingRect(rectF, this.mGhostViewsVisibleAreas[i3]);
            i2++;
            childAt = childAt2;
        }
        View childAt3 = getChildAt(lastNonFooterPosition);
        RectF rectF2 = new RectF(childAt3.getLeft(), childAt.getBottom(), childAt3.getRight(), childAt3.getBottom());
        this.mGhostViewsVisibleAreas[packedPositionGroup] = new RectF();
        this.mCollapsingRects[packedPositionGroup] = new CollapsingRect(rectF2, this.mGhostViewsVisibleAreas[packedPositionGroup]);
        final ArrayList arrayList = new ArrayList();
        for (int i4 = 0; i4 < childCount; i4++) {
            if (!isHeaderOrFooterPosition(i4 + firstVisiblePosition)) {
                long expandableListPosition = getExpandableListPosition(i4);
                if (getPackedPositionType(expandableListPosition) != 0) {
                    int packedPositionGroup2 = getPackedPositionGroup(expandableListPosition);
                    ViewInfo viewInfo = this.mViewSnapshots.get(expandableListPosition);
                    this.mGhostExpandCollapseChildViews.add(viewInfo);
                    this.mViewSnapshots.remove(expandableListPosition);
                    arrayList.add(createViewSnapshotAnimation(-iArr[packedPositionGroup2], viewInfo));
                }
            }
        }
        this.mBlockTouchEvent = true;
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: android.widget.SemExpandableListView.8
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                int i5;
                SemExpandableListView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                int childCount2 = SemExpandableListView.this.getChildCount();
                if (childCount2 == 0) {
                    runnable.run();
                    SemExpandableListView.this.resetCollapseAnimationState();
                    return true;
                }
                int absoluteFlatPosition = SemExpandableListView.this.getAbsoluteFlatPosition(packedPositionGroup);
                SemExpandableListView semExpandableListView = SemExpandableListView.this;
                semExpandableListView.mTranslationOffset = bottom - semExpandableListView.getChildAt(absoluteFlatPosition).getBottom();
                int firstVisiblePosition2 = SemExpandableListView.this.getFirstVisiblePosition();
                boolean z = true;
                for (int i6 = 0; i6 < childCount2; i6++) {
                    boolean isHeaderOrFooterPosition = SemExpandableListView.this.isHeaderOrFooterPosition(i6 + firstVisiblePosition2);
                    View childAt4 = SemExpandableListView.this.getChildAt(i6);
                    long expandableListPosition2 = SemExpandableListView.this.getExpandableListPosition(i6);
                    ViewInfo viewInfo2 = (ViewInfo) SemExpandableListView.this.mViewSnapshots.get(expandableListPosition2);
                    if (viewInfo2 != null) {
                        i5 = viewInfo2.top - childAt4.getTop();
                    } else {
                        i5 = SemExpandableListView.this.mTranslationOffset;
                    }
                    int packedPositionGroup3 = SemExpandableListView.getPackedPositionGroup(expandableListPosition2);
                    if (!isHeaderOrFooterPosition && packedPositionGroup3 <= packedPositionGroup) {
                        SemExpandableListView.this.mCollapsingRects[packedPositionGroup3].setFinishY(childAt4.getBottom());
                    }
                    SemExpandableListView.this.mViewSnapshots.remove(expandableListPosition2);
                    if (!isHeaderOrFooterPosition) {
                        boolean[] zArr2 = zArr;
                        if (packedPositionGroup3 < zArr2.length) {
                            boolean z2 = zArr2[packedPositionGroup3];
                            z &= !z2;
                            if (z2) {
                                SemExpandableListView.this.startIndicatorAnimation(childAt4, false, 700);
                            }
                        }
                    }
                    if (i5 != 0) {
                        childAt4.setTranslationY(i5);
                        arrayList.add(ObjectAnimator.ofFloat(childAt4, View.TRANSLATION_Y, 0.0f));
                    }
                }
                AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: android.widget.SemExpandableListView.8.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        SemExpandableListView.this.mAnimationState = 5;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        Log.d(SemExpandableListView.TAG, "expand animation finished");
                        runnable.run();
                        SemExpandableListView.this.resetCollapseAnimationState();
                    }
                };
                if (z) {
                    animatorListenerAdapter.onAnimationEnd(null);
                    return false;
                }
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.addUpdateListener(SemExpandableListView.this.mBitmapUpdateListener);
                arrayList.add(ofFloat);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(arrayList);
                animatorSet.setDuration(700);
                animatorSet.setInterpolator(SemExpandableListView.EXPAND_COLLAPSE_INTERPOLATOR);
                animatorSet.addListener(animatorListenerAdapter);
                animatorSet.start();
                SemExpandableListView.this.mViewSnapshots.clear();
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startIndicatorAnimation(View view, boolean z, int i) {
        int i2 = this.mRotationAngle;
        if (!z) {
            i2 = -i2;
        }
        DecoratedItemViewHolder decoratedItemViewHolder = (DecoratedItemViewHolder) view.getTag(DECORATED_VIEW_TAG);
        if (decoratedItemViewHolder == null || decoratedItemViewHolder.indicatorImgView == null) {
            Log.e(TAG, "startIndicatorAnimation() holder or indicatorImgView is null, startAngle=" + i2);
            return;
        }
        IndicatorImageView indicatorImageView = decoratedItemViewHolder.indicatorImgView;
        if (this.mIndicatorAnimationType == 1) {
            indicatorImageView.setRotation(i2);
            indicatorImageView.animate().rotation(0.0f).setInterpolator(EXPAND_COLLAPSE_INTERPOLATOR).setDuration(i).withLayer();
        } else {
            indicatorImageView.startIndicatorMorphAimation();
        }
        indicatorImageView.setContentDescription(z ? this.mDescriptionCollapse : this.mDescriptionExpand);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ObjectAnimator createViewSnapshotAnimationReverse(int i, ViewInfo viewInfo) {
        Rect rect = new Rect(viewInfo.left, viewInfo.top, viewInfo.right, viewInfo.bottom);
        Rect rect2 = new Rect(rect);
        rect2.offset(0, i);
        return ObjectAnimator.ofObject(viewInfo.snapshot, "bounds", SemAnimatorUtils.BOUNDS_EVALUATOR, rect2, rect);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ObjectAnimator createViewSnapshotAnimation(int i, ViewInfo viewInfo) {
        Rect rect = new Rect(viewInfo.left, viewInfo.top, viewInfo.right, viewInfo.bottom);
        Rect rect2 = new Rect(rect);
        rect2.offset(0, i);
        return ObjectAnimator.ofObject(viewInfo.snapshot, "bounds", SemAnimatorUtils.BOUNDS_EVALUATOR, rect, rect2);
    }

    private void captureViewsPriorAnimation() {
        int childCount = getChildCount();
        int firstVisiblePosition = getFirstVisiblePosition();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            long expandableListPosition = getExpandableListPosition(i + firstVisiblePosition);
            if (childAt.getWidth() != 0 && childAt.getHeight() != 0) {
                ViewInfo viewInfo = new ViewInfo(childAt);
                if (viewInfo.snapshot != null) {
                    this.mViewSnapshots.put(expandableListPosition, viewInfo);
                }
            }
        }
    }

    private static class ViewInfo {
        int bottom;
        int left;
        int right;
        BitmapDrawable snapshot;
        int top;

        ViewInfo(View view) {
            this.snapshot = SemAnimatorUtils.getBitmapDrawableFromView(view);
            this.top = view.getTop();
            this.bottom = view.getBottom();
            this.left = view.getLeft();
            int right = view.getRight();
            this.right = right;
            this.snapshot.setBounds(this.left, this.top, right, this.bottom);
        }
    }

    public boolean expandGroup(int i) {
        return expandGroup(i, false);
    }

    public boolean expandGroup(int i, boolean z) {
        SemExpandableListPosition obtain = SemExpandableListPosition.obtain(2, i, -1, -1);
        SemExpandableListConnector.PositionMetadata flattenedPos = this.mConnector.getFlattenedPos(obtain);
        if (flattenedPos == null) {
            return false;
        }
        obtain.recycle();
        boolean expandGroup = this.mConnector.expandGroup(flattenedPos);
        OnGroupExpandListener onGroupExpandListener = this.mOnGroupExpandListener;
        if (onGroupExpandListener != null) {
            onGroupExpandListener.onGroupExpand(i);
        }
        if (z) {
            int headerViewsCount = flattenedPos.position.flatListPos + getHeaderViewsCount();
            smoothScrollToPosition(this.mAdapter.getChildrenCount(i) + headerViewsCount, headerViewsCount);
        }
        flattenedPos.recycle();
        return expandGroup;
    }

    public boolean collapseGroup(int i) {
        boolean collapseGroup = this.mConnector.collapseGroup(i);
        OnGroupCollapseListener onGroupCollapseListener = this.mOnGroupCollapseListener;
        if (onGroupCollapseListener != null) {
            onGroupCollapseListener.onGroupCollapse(i);
        }
        return collapseGroup;
    }

    public void setOnGroupCollapseListener(OnGroupCollapseListener onGroupCollapseListener) {
        this.mOnGroupCollapseListener = onGroupCollapseListener;
    }

    public void setOnGroupExpandListener(OnGroupExpandListener onGroupExpandListener) {
        this.mOnGroupExpandListener = onGroupExpandListener;
    }

    public void setOnGroupClickListener(OnGroupClickListener onGroupClickListener) {
        this.mOnGroupClickListener = onGroupClickListener;
    }

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.mOnChildClickListener = onChildClickListener;
    }

    public long getExpandableListPosition(int i) {
        if (isHeaderOrFooterPosition(i)) {
            return getHeaderFooterPackedPosition(i);
        }
        SemExpandableListConnector.PositionMetadata unflattenedPos = this.mConnector.getUnflattenedPos(getFlatPositionForConnector(i));
        long packedPosition = unflattenedPos.position.getPackedPosition();
        unflattenedPos.recycle();
        return packedPosition;
    }

    public int getFlatListPosition(long j) {
        SemExpandableListPosition obtainPosition = SemExpandableListPosition.obtainPosition(j);
        if (obtainPosition == null) {
            return -1;
        }
        SemExpandableListConnector.PositionMetadata flattenedPos = this.mConnector.getFlattenedPos(obtainPosition);
        obtainPosition.recycle();
        if (flattenedPos == null) {
            return -1;
        }
        int i = flattenedPos.position.flatListPos;
        flattenedPos.recycle();
        return getAbsoluteFlatPosition(i);
    }

    public long getSelectedPosition() {
        return getExpandableListPosition(getSelectedItemPosition());
    }

    public long getSelectedId() {
        long selectedPosition = getSelectedPosition();
        if (selectedPosition == 4294967295L) {
            return -1L;
        }
        int packedPositionGroup = getPackedPositionGroup(selectedPosition);
        if (getPackedPositionType(selectedPosition) == 0) {
            return this.mAdapter.getGroupId(packedPositionGroup);
        }
        return this.mAdapter.getChildId(packedPositionGroup, getPackedPositionChild(selectedPosition));
    }

    public void setSelectedGroup(int i) {
        SemExpandableListPosition obtainGroupPosition = SemExpandableListPosition.obtainGroupPosition(i);
        SemExpandableListConnector.PositionMetadata flattenedPos = this.mConnector.getFlattenedPos(obtainGroupPosition);
        if (flattenedPos == null) {
            return;
        }
        obtainGroupPosition.recycle();
        super.setSelection(getAbsoluteFlatPosition(flattenedPos.position.flatListPos));
        flattenedPos.recycle();
    }

    public boolean setSelectedChild(int i, int i2, boolean z) {
        SemExpandableListPosition obtainChildPosition = SemExpandableListPosition.obtainChildPosition(i, i2);
        SemExpandableListConnector.PositionMetadata flattenedPos = this.mConnector.getFlattenedPos(obtainChildPosition);
        if (flattenedPos == null) {
            if (!z) {
                return false;
            }
            expandGroup(i);
            flattenedPos = this.mConnector.getFlattenedPos(obtainChildPosition);
            if (flattenedPos == null) {
                throw new IllegalStateException("Could not find child");
            }
        }
        super.setSelection(getAbsoluteFlatPosition(flattenedPos.position.flatListPos));
        obtainChildPosition.recycle();
        flattenedPos.recycle();
        return true;
    }

    public boolean isGroupExpanded(int i) {
        return this.mConnector.isGroupExpanded(i);
    }

    private long getHeaderFooterPackedPosition(int i) {
        long j;
        if (i < getHeaderViewsCount()) {
            j = 9223372032559808512L;
        } else {
            i -= this.mItemCount - getFooterViewsCount();
            j = PACKED_POSITION_FOOTER_VIEW_BASE;
        }
        return i | j;
    }

    @Override // android.widget.AbsListView
    ContextMenu.ContextMenuInfo createContextMenuInfo(View view, int i, long j) {
        if (isHeaderOrFooterPosition(i)) {
            return new AdapterView.AdapterContextMenuInfo(view, i, j);
        }
        SemExpandableListConnector.PositionMetadata unflattenedPos = this.mConnector.getUnflattenedPos(getFlatPositionForConnector(i));
        SemExpandableListPosition semExpandableListPosition = unflattenedPos.position;
        long childOrGroupId = getChildOrGroupId(semExpandableListPosition);
        long packedPosition = semExpandableListPosition.getPackedPosition();
        unflattenedPos.recycle();
        return new ExpandableListContextMenuInfo(view, packedPosition, childOrGroupId);
    }

    private long getChildOrGroupId(SemExpandableListPosition semExpandableListPosition) {
        if (semExpandableListPosition.type == 1) {
            return this.mAdapter.getChildId(semExpandableListPosition.groupPos, semExpandableListPosition.childPos);
        }
        return this.mAdapter.getGroupId(semExpandableListPosition.groupPos);
    }

    public void setChildIndicator(Drawable drawable) {
        this.mChildIndicator = drawable;
    }

    public void setChildIndicatorBounds(int i, int i2) {
        this.mChildIndicatorLeft = i;
        this.mChildIndicatorRight = i2;
        resolveChildIndicator();
    }

    public void setChildIndicatorBoundsRelative(int i, int i2) {
        this.mChildIndicatorStart = i;
        this.mChildIndicatorEnd = i2;
        resolveChildIndicator();
    }

    public void setGroupIndicator(Drawable drawable) {
        this.mGroupIndicator = drawable;
        if (this.mIndicatorRight != 0 || drawable == null) {
            return;
        }
        this.mIndicatorRight = this.mIndicatorLeft + drawable.getIntrinsicWidth();
    }

    public void setGroupIndicatorAnimationType(int i) {
        this.mIndicatorAnimationType = i;
    }

    public void setIndicatorBounds(int i, int i2) {
        this.mIndicatorLeft = i;
        this.mIndicatorRight = i2;
        resolveIndicator();
    }

    public void setIndicatorBoundsRelative(int i, int i2) {
        this.mIndicatorStart = i;
        this.mIndicatorEnd = i2;
        resolveIndicator();
    }

    public static class ExpandableListContextMenuInfo implements ContextMenu.ContextMenuInfo {
        public long id;
        public long packedPosition;
        public View targetView;

        public ExpandableListContextMenuInfo(View view, long j, long j2) {
            this.targetView = view;
            this.packedPosition = j;
            this.id = j2;
        }
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.SemExpandableListView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        ArrayList<SemExpandableListConnector.GroupMetadata> expandedGroupMetadataList;

        SavedState(Parcelable parcelable, ArrayList<SemExpandableListConnector.GroupMetadata> arrayList) {
            super(parcelable);
            this.expandedGroupMetadataList = arrayList;
        }

        private SavedState(Parcel parcel) {
            super(parcel, SemExpandableListConnector.class.getClassLoader());
            ArrayList<SemExpandableListConnector.GroupMetadata> arrayList = new ArrayList<>();
            this.expandedGroupMetadataList = arrayList;
            parcel.readList(arrayList, SemExpandableListConnector.class.getClassLoader());
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeList(this.expandedGroupMetadataList);
        }
    }

    @Override // android.widget.AbsListView, android.view.View
    public Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        SemExpandableListConnector semExpandableListConnector = this.mConnector;
        return new SavedState(onSaveInstanceState, semExpandableListConnector != null ? semExpandableListConnector.getExpandedGroupMetadataList() : null);
    }

    @Override // android.widget.AbsListView, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (this.mConnector == null || savedState.expandedGroupMetadataList == null) {
            return;
        }
        this.mConnector.setExpandedGroupMetadataList(savedState.expandedGroupMetadataList);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(SemExpandableListView.class.getName());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(SemExpandableListView.class.getName());
    }

    @Override // android.widget.ListView
    public void setDivider(Drawable drawable) {
        super.setDivider(drawable);
        this.mDividerHeight = 0;
        if (drawable != null) {
            if (this.mExpListDividerHeight == null) {
                this.mExpListDividerHeight = new int[1];
            }
            this.mExpListDividerHeight[0] = drawable.getIntrinsicHeight();
            Log.e(TAG, "setDivider() height=" + this.mExpListDividerHeight[0]);
        }
    }

    @Override // android.widget.ListView
    public void setDividerHeight(int i) {
        super.setDividerHeight(i);
        this.mDividerHeight = 0;
        if (this.mExpListDividerHeight == null) {
            this.mExpListDividerHeight = new int[1];
        }
        this.mExpListDividerHeight[0] = i;
        Log.e(TAG, "setDividerHeight() height=" + i);
    }

    private class IndicatorImageView extends ImageView {
        private static final int ANIMATION_DURATION = 300;
        private float ARROW_PADDING;
        int childPos;
        int groupPos;
        Animator.AnimatorListener mAnimatorListener;
        private float mCenterX;
        private float mCenterY;
        private float mEndPointX;
        private float mEndPointY;
        private float mHeight;
        private float mIndicatorArrowHeight;
        private float mMorphingAnimatedValue;
        private ValueAnimator mMorphingAnimationToDown;
        private ValueAnimator mMorphingAnimationToUp;
        private float mPaddingValue;
        private Path mPath;
        private float mStartPointX;
        private float mStartPointY;
        ValueAnimator.AnimatorUpdateListener mUpdateListener;
        private float mWidth;

        IndicatorImageView(Context context) {
            super(context);
            this.groupPos = -1;
            this.mPath = null;
            this.mMorphingAnimatedValue = 0.0f;
            this.ARROW_PADDING = SemExpandableListView.this.mIndicatorPaddingHeight;
            this.mIndicatorArrowHeight = 0.0f;
            this.mUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.SemExpandableListView.IndicatorImageView.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    IndicatorImageView.this.mMorphingAnimatedValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    IndicatorImageView.this.invalidate();
                }
            };
            this.mAnimatorListener = new Animator.AnimatorListener() { // from class: android.widget.SemExpandableListView.IndicatorImageView.2
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    if (SemExpandableListView.DEBUGGABLE_LOW) {
                        Log.d(SemExpandableListView.TAG, "mAnimatorListener : onAnimationCancel");
                        IndicatorImageView.this.printAnimationInfo(animator);
                    }
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (SemExpandableListView.DEBUGGABLE_LOW) {
                        Log.d(SemExpandableListView.TAG, "mAnimatorListener : onAnimationEnd");
                        IndicatorImageView.this.printAnimationInfo(animator);
                    }
                    IndicatorImageView.this.mMorphingAnimatedValue = 0.0f;
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    if (SemExpandableListView.DEBUGGABLE_LOW) {
                        Log.d(SemExpandableListView.TAG, "mAnimatorListener : onAnimationStart");
                        IndicatorImageView.this.printAnimationInfo(animator);
                    }
                }
            };
            semSetHoverPopupType(1);
        }

        void setIndicatorPos(SemExpandableListPosition semExpandableListPosition) {
            this.groupPos = semExpandableListPosition.groupPos;
            this.childPos = semExpandableListPosition.childPos;
        }

        @Override // android.widget.ImageView, android.view.View
        public int[] onCreateDrawableState(int i) {
            int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
            if (this.childPos == -1 && SemExpandableListView.this.mConnector.isGroupExpanded(this.groupPos)) {
                mergeDrawableStates(onCreateDrawableState, SemExpandableListView.GROUP_EXPANDED_STATE_SET);
            }
            return onCreateDrawableState;
        }

        @Override // android.widget.ImageView, android.view.View
        protected void drawableStateChanged() {
            super.drawableStateChanged();
        }

        private void printState(int[] iArr) {
            StringBuilder sb = new StringBuilder();
            for (int i : iArr) {
                if (i == 0) {
                    Log.e(SemExpandableListView.TAG, "drawableStateChanged() state=" + i);
                } else {
                    sb.append(getResources().getResourceEntryName(i));
                    sb.append(", ");
                }
            }
            Log.i(SemExpandableListView.TAG, "drawableStateChanged() gr=" + this.groupPos + ", child=" + this.childPos + ", state=" + sb.toString());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void printAnimationInfo(Animator animator) {
            if (animator != null) {
                Log.d(SemExpandableListView.TAG, "printAnimationInfo : animation = " + animator);
                if (animator.equals(this.mMorphingAnimationToDown)) {
                    Log.d(SemExpandableListView.TAG, "printAnimationInfo : this animation => mMorphingAnimationToDown");
                } else if (animator.equals(this.mMorphingAnimationToUp)) {
                    Log.d(SemExpandableListView.TAG, "printAnimationInfo : this animation => mMorphingAnimationToUp");
                }
            }
        }

        private void initArrow() {
            this.mWidth = getWidth();
            float height = getHeight();
            this.mHeight = height;
            float f = this.ARROW_PADDING;
            this.mPaddingValue = f;
            this.mStartPointX = 0.0f;
            float f2 = height / 2.0f;
            this.mStartPointY = f2;
            float f3 = this.mWidth;
            this.mEndPointX = 0.0f + f3;
            this.mEndPointY = f2;
            this.mCenterX = (f3 / 2.0f) + 0.0f;
            this.mCenterY = f;
            this.mPath = new Path();
            float f4 = this.mHeight - (this.mPaddingValue * 2.0f);
            this.mIndicatorArrowHeight = f4;
            this.mMorphingAnimationToDown = ValueAnimator.ofFloat(0.0f, f4);
            if (SemExpandableListView.DEBUGGABLE_LOW) {
                Log.d(SemExpandableListView.TAG, "IndicatorImageView : initArrow : mMorphingAnimationToDown = " + this.mMorphingAnimationToDown);
            }
            this.mMorphingAnimationToDown.setInterpolator(AnimationUtils.loadInterpolator(getContext(), 17563654));
            this.mMorphingAnimationToDown.setDuration(300L);
            this.mMorphingAnimationToDown.addUpdateListener(this.mUpdateListener);
            this.mMorphingAnimationToDown.addListener(this.mAnimatorListener);
            this.mMorphingAnimationToUp = ValueAnimator.ofFloat(this.mHeight - (this.mPaddingValue * 2.0f), 0.0f);
            if (SemExpandableListView.DEBUGGABLE_LOW) {
                Log.d(SemExpandableListView.TAG, "IndicatorImageView : initArrow : mMorphingAnimationToUp = " + this.mMorphingAnimationToUp);
            }
            this.mMorphingAnimationToUp.setInterpolator(AnimationUtils.loadInterpolator(getContext(), 17563654));
            this.mMorphingAnimationToUp.setDuration(300L);
            this.mMorphingAnimationToUp.addUpdateListener(this.mUpdateListener);
            this.mMorphingAnimationToUp.addListener(this.mAnimatorListener);
        }

        @Override // android.widget.ImageView, android.view.View
        protected void onDraw(Canvas canvas) {
            if (SemExpandableListView.this.mIndicatorAnimationType == 2) {
                if (this.mPath == null) {
                    initArrow();
                }
                Path path = this.mPath;
                if (path != null) {
                    path.reset();
                    this.mPath.moveTo(this.mStartPointX, this.mStartPointY);
                    if (!this.mMorphingAnimationToDown.isRunning() && !this.mMorphingAnimationToUp.isRunning()) {
                        if (SemExpandableListView.this.mConnector.isGroupExpanded(this.groupPos)) {
                            if (SemExpandableListView.DEBUGGABLE_LOW) {
                                Log.d(SemExpandableListView.TAG, "IndicatorImageView : onDraw : group(" + this.groupPos + ") collapse !!");
                            }
                            this.mMorphingAnimatedValue = 0.0f;
                        } else {
                            if (SemExpandableListView.DEBUGGABLE_LOW) {
                                Log.d(SemExpandableListView.TAG, "IndicatorImageView : onDraw : group(" + this.groupPos + ") expand !!");
                            }
                            this.mMorphingAnimatedValue = this.mIndicatorArrowHeight;
                        }
                    }
                    this.mPath.lineTo(this.mCenterX, this.mCenterY + this.mMorphingAnimatedValue);
                    this.mPath.lineTo(this.mEndPointX, this.mEndPointY);
                    canvas.drawPath(this.mPath, SemExpandableListView.this.mGroupIndicatorPaint);
                    return;
                }
                return;
            }
            super.onDraw(canvas);
        }

        public void startIndicatorMorphAimation() {
            ValueAnimator valueAnimator = this.mMorphingAnimationToUp;
            if (valueAnimator == null || !valueAnimator.isRunning()) {
                ValueAnimator valueAnimator2 = this.mMorphingAnimationToDown;
                if ((valueAnimator2 == null || !valueAnimator2.isRunning()) && SemExpandableListView.this.mConnector != null) {
                    if (SemExpandableListView.this.mConnector.isGroupExpanded(this.groupPos)) {
                        if (SemExpandableListView.DEBUGGABLE_LOW) {
                            Log.d(SemExpandableListView.TAG, "IndicatorImageView : startIndicatorMorphAimation : group(" + this.groupPos + ") collapse !!");
                            Log.d(SemExpandableListView.TAG, "IndicatorImageView : startIndicatorMorphAimation : start morphingAnimation to UP");
                        }
                        ValueAnimator valueAnimator3 = this.mMorphingAnimationToUp;
                        if (valueAnimator3 != null) {
                            valueAnimator3.start();
                            return;
                        }
                        return;
                    }
                    if (SemExpandableListView.DEBUGGABLE_LOW) {
                        Log.d(SemExpandableListView.TAG, "IndicatorImageView : startIndicatorMorphAimation : group(" + this.groupPos + ") expand !!");
                        Log.d(SemExpandableListView.TAG, "IndicatorImageView : startIndicatorMorphAimation : start morphingAnimation to DOWN");
                    }
                    ValueAnimator valueAnimator4 = this.mMorphingAnimationToDown;
                    if (valueAnimator4 != null) {
                        valueAnimator4.start();
                    }
                }
            }
        }
    }

    private static class DecoratedItemViewHolder {
        View dividerView;
        IndicatorImageView indicatorImgView;
        View itemView;

        private DecoratedItemViewHolder() {
        }
    }

    private String printArrays(int[] iArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i : iArr) {
            if (i > 0) {
                stringBuffer.append(this.mContext.getResources().getResourceEntryName(i));
                stringBuffer.append(", ");
            }
        }
        return stringBuffer.toString();
    }

    public void setGroupIndicatorRotationAngle(int i) {
        this.mRotationAngle = i;
    }

    public void expandAll() {
        if (this.mAdapter.getGroupCount() != 0 && this.mAnimationState == 1) {
            this.mExpandCollapseAllState = 1;
            if (!this.mAnimationEnabled) {
                expandAllGroups();
            } else {
                triggerJumpScrollToTop();
            }
        }
    }

    public void collapseAll() {
        if (this.mAdapter.getGroupCount() != 0 && this.mAnimationState == 1) {
            this.mExpandCollapseAllState = 2;
            if (!this.mAnimationEnabled) {
                collapseAllGroups();
            }
            triggerJumpScrollToTop();
        }
    }

    @Override // android.widget.AbsListView
    void onJumpScrollToTopFinished() {
        super.onJumpScrollToTopFinished();
        if (this.mAdapter.getGroupCount() == 0) {
            return;
        }
        int i = this.mExpandCollapseAllState;
        if (i == 1) {
            captureViewsPriorAnimation();
            boolean[] expandedState = getExpandedState();
            expandAllGroups();
            startExpandAllAnimation(expandedState, new Runnable() { // from class: android.widget.SemExpandableListView.10
                @Override // java.lang.Runnable
                public void run() {
                    if (SemExpandableListView.this.mOnGroupExpandListener == null) {
                        return;
                    }
                    int groupCount = SemExpandableListView.this.mAdapter.getGroupCount();
                    for (int i2 = 0; i2 < groupCount; i2++) {
                        SemExpandableListView.this.mOnGroupExpandListener.onGroupExpand(i2);
                    }
                }
            });
        } else if (i == 2) {
            captureViewsPriorAnimation();
            startCollapseAllAnimation(getExpandedState(), new Runnable() { // from class: android.widget.SemExpandableListView.11
                @Override // java.lang.Runnable
                public void run() {
                    if (SemExpandableListView.this.mOnGroupCollapseListener == null) {
                        return;
                    }
                    int groupCount = SemExpandableListView.this.mAdapter.getGroupCount();
                    for (int i2 = 0; i2 < groupCount; i2++) {
                        SemExpandableListView.this.mOnGroupCollapseListener.onGroupCollapse(i2);
                    }
                }
            });
            collapseAllGroups();
        }
        this.mExpandCollapseAllState = 0;
    }

    private boolean[] getExpandedState() {
        int packedPositionGroup = getPackedPositionGroup(getExpandableListPosition(getLastNonFooterPosition()));
        boolean[] zArr = new boolean[packedPositionGroup + 1];
        for (int i = 0; i <= packedPositionGroup; i++) {
            zArr[i] = this.mConnector.isGroupExpanded(i);
        }
        return zArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getLastNonFooterPosition() {
        int lastVisiblePosition = getLastVisiblePosition();
        int firstVisiblePosition = getFirstVisiblePosition();
        while (lastVisiblePosition >= firstVisiblePosition && isHeaderOrFooterPosition(lastVisiblePosition)) {
            lastVisiblePosition--;
        }
        return lastVisiblePosition;
    }

    private void expandAllGroups() {
        int groupCount = this.mAdapter.getGroupCount();
        for (int i = 0; i < groupCount; i++) {
            this.mConnector.expandGroup(i);
        }
    }

    private void collapseAllGroups() {
        int groupCount = this.mAdapter.getGroupCount();
        for (int i = 0; i < groupCount; i++) {
            this.mConnector.collapseGroup(i);
        }
    }

    public void setAnimationEnabled(boolean z) {
        this.mAnimationEnabled = z;
    }

    public void setExpandingAnimationEnabled(boolean z) {
        this.mAnimationEnabled = z;
    }

    public void setIndicatorPaddings(int i, int i2) {
        this.mIndicatorPaddingLeft = i;
        this.mIndicatorPaddingRight = i2;
    }

    public void setIndicatorGravity(int i) {
        this.mIndicatorGravity = i;
        Log.i(TAG, "setIndicatorGravity() gravity=" + i + ", mIndicatorGravity=" + this.mIndicatorGravity);
    }

    public void setGroupIndicatorColor(int i) {
        Log.i(TAG, "setGroupIndicatorColor() color= " + Integer.toHexString(i));
        this.mGroupIndicatorColor = i;
        Paint paint = this.mGroupIndicatorPaint;
        if (paint != null) {
            paint.setColor(i);
        }
    }

    public View getUnfoldedChildAt(int i) {
        return this.mItemDecorator.unfoldDecoratedView(super.getChildAt(i));
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mBlockTouchEvent) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mBlockTouchEvent) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        SemExpandableListConnector semExpandableListConnector = this.mConnector;
        if (semExpandableListConnector != null) {
            semExpandableListConnector.semRegisterDataSetObserver();
        }
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SemExpandableListConnector semExpandableListConnector = this.mConnector;
        if (semExpandableListConnector != null) {
            semExpandableListConnector.semUnregisterDataSetObserver();
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDescriptionExpand = this.mContext.getResources().getString(R.string.expandablelist_indicator_description, this.mContext.getResources().getString(R.string.expandablelist_expand));
        this.mDescriptionCollapse = this.mContext.getResources().getString(R.string.expandablelist_indicator_description, this.mContext.getResources().getString(R.string.expandablelist_collapse));
        if (this.mGroupIndicatorPaint != null) {
            this.mGroupIndicatorPaint.setStrokeWidth((int) ((this.mContext.getResources().getDisplayMetrics().density * 2.0f) + 0.5f));
        }
    }
}
