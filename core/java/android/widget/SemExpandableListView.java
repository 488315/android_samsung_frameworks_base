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
import com.android.internal.C4337R;
import com.samsung.android.animation.SemAnimatorUtils;
import com.samsung.android.rune.ViewRune;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class SemExpandableListView extends ListView {
  private static final int ANIMATION_STATE_COLLAPSING = 3;
  private static final int ANIMATION_STATE_COLLAPSING_ALL = 5;
  private static final int ANIMATION_STATE_EXPANDING = 2;
  private static final int ANIMATION_STATE_EXPANDING_ALL = 4;
  private static final int ANIMATION_STATE_IDLE = 1;
  public static final int CHILD_INDICATOR_INHERIT = -1;
  private static final int COLLAPSE_ALL_PENDING = 2;
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
  private final boolean DEBUGGABLE;
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
  private static final String TAG = SemExpandableListView.class.getSimpleName();
  private static final boolean DEBUGGABLE_LOW = ViewRune.COMMON_IS_PRODUCT_DEV;
  private static final int[] CHILD_LAST_STATE_SET = {16842918};
  private static Interpolator EXPAND_COLLAPSE_INTERPOLATOR =
      new PathInterpolator(0.33f, 0.0f, 0.2f, 1.0f);
  private static ElasticCustom mExpandInterpolator = new ElasticCustom(1.0f, 0.8f);

  public interface OnChildClickListener {
    boolean onChildClick(
        SemExpandableListView semExpandableListView, View view, int i, int i2, long j);
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

  static {
    int[] iArr = new int[0];
    EMPTY_STATE_SET = iArr;
    int[] iArr2 = {16842920};
    GROUP_EXPANDED_STATE_SET = iArr2;
    int[] iArr3 = {16842921};
    GROUP_EMPTY_STATE_SET = iArr3;
    int[] iArr4 = {16842920, 16842921};
    GROUP_EXPANDED_EMPTY_STATE_SET = iArr4;
    GROUP_STATE_SETS = new int[][] {iArr, iArr2, iArr3, iArr4};
  }

  public SemExpandableListView(Context context) {
    this(context, null);
  }

  public SemExpandableListView(Context context, AttributeSet attrs) {
    this(context, attrs, 16842863);
  }

  public SemExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, 0);
  }

  public SemExpandableListView(
      Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    Drawable drawable;
    this.DEBUGGABLE = false;
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
    this.mItemDecorator =
        new SemExpandableListConnector
            .ItemDecorator() { // from class: android.widget.SemExpandableListView.1
          static final int WRAPPING_VIEW_ID = 2147482647;

          @Override // android.widget.SemExpandableListConnector.ItemDecorator
          public View onItemDecorate(
              View convertView, View itemView, SemExpandableListConnector.PositionMetadata pos) {
            int indicatorLeft;
            int indicatorRight;
            int indicatorLeft2;
            int indicatorRight2;
            Drawable indicator;
            FrameLayout.LayoutParams lp;
            boolean isLastChild =
                pos.position.flatListPos == SemExpandableListView.this.mConnector.getCount() - 1;
            int dividerHeight = SemExpandableListView.this.mExpListDividerHeight[0];
            if (convertView != null && ((ViewGroup) convertView).getChildAt(0) == itemView) {
              if (convertView.getId() != WRAPPING_VIEW_ID
                  || !(convertView instanceof FrameLayout)) {
                throw new IllegalStateException(
                    "convertView is neither null nor the wrapping FrameLayout");
              }
              DecoratedItemViewHolder holder =
                  (DecoratedItemViewHolder)
                      convertView.getTag(SemExpandableListView.DECORATED_VIEW_TAG);
              Drawable indicator2 = SemExpandableListView.this.getIndicator(pos);
              if (holder != null) {
                if (holder.indicatorImgView != null) {
                  holder.indicatorImgView.setIndicatorPos(pos.position);
                  if (SemExpandableListView.this.mChildIndicator == null
                      && SemExpandableListView.this.mGroupIndicator == null) {
                    holder.indicatorImgView.setVisibility(8);
                  } else {
                    holder.indicatorImgView.setVisibility(0);
                  }
                  holder.indicatorImgView.refreshDrawableState();
                  holder.indicatorImgView.setContentDescription(
                      pos.isExpanded()
                          ? SemExpandableListView.this.mDescriptionCollapse
                          : SemExpandableListView.this.mDescriptionExpand);
                  initIndicatorImageLayoutParams(
                      (FrameLayout.LayoutParams) holder.indicatorImgView.getLayoutParams());
                } else if (indicator2 != null) {
                  SemExpandableListView semExpandableListView = SemExpandableListView.this;
                  IndicatorImageView indicatorImgView =
                      semExpandableListView.new IndicatorImageView(semExpandableListView.mContext);
                  indicatorImgView.setIndicatorPos(pos.position);
                  indicatorImgView.setImageDrawable(indicator2.getConstantState().newDrawable());
                  indicatorImgView.refreshDrawableState();
                  FrameLayout.LayoutParams lp2 = new FrameLayout.LayoutParams(-2, -2);
                  initIndicatorImageLayoutParams(lp2);
                  indicatorImgView.setLayoutParams(lp2);
                  ((FrameLayout) convertView).addView(indicatorImgView);
                  holder.indicatorImgView = indicatorImgView;
                  holder.indicatorImgView.setContentDescription(
                      pos.isExpanded()
                          ? SemExpandableListView.this.mDescriptionCollapse
                          : SemExpandableListView.this.mDescriptionExpand);
                }
                adjustDivider(holder, isLastChild);
              }
              return convertView;
            }
            FrameLayout frameLayout = new FrameLayout(SemExpandableListView.this.mContext);
            DecoratedItemViewHolder holder2 = new DecoratedItemViewHolder();
            frameLayout.setTag(SemExpandableListView.DECORATED_VIEW_TAG, holder2);
            frameLayout.setId(WRAPPING_VIEW_ID);
            frameLayout.addView(itemView);
            holder2.itemView = itemView;
            int t = itemView.getTop();
            int b = itemView.getBottom();
            int myB = SemExpandableListView.this.mBottom;
            if (b < 0 || t > myB) {
              return frameLayout;
            }
            boolean isLayoutRtl = SemExpandableListView.this.isLayoutRtl();
            int width = SemExpandableListView.this.getWidth();
            if (pos.position.type == 1) {
              if (SemExpandableListView.this.mChildIndicatorLeft != -1) {
                indicatorLeft = SemExpandableListView.this.mChildIndicatorLeft;
              } else {
                indicatorLeft = SemExpandableListView.this.mIndicatorLeft;
              }
              if (SemExpandableListView.this.mChildIndicatorRight != -1) {
                indicatorRight = SemExpandableListView.this.mChildIndicatorRight;
              } else {
                indicatorRight = SemExpandableListView.this.mIndicatorRight;
              }
            } else {
              indicatorLeft = SemExpandableListView.this.mIndicatorLeft;
              indicatorRight = SemExpandableListView.this.mIndicatorRight;
            }
            if (!isLayoutRtl) {
              indicatorLeft2 = indicatorLeft + SemExpandableListView.this.mPaddingLeft;
              indicatorRight2 = indicatorRight + SemExpandableListView.this.mPaddingLeft;
            } else {
              int temp = indicatorLeft;
              int indicatorLeft3 = width - indicatorRight;
              int indicatorRight3 = width - temp;
              indicatorLeft2 = indicatorLeft3 - SemExpandableListView.this.mPaddingRight;
              indicatorRight2 = indicatorRight3 - SemExpandableListView.this.mPaddingRight;
            }
            if (indicatorLeft2 != indicatorRight2
                && (indicator = SemExpandableListView.this.getIndicator(pos)) != null) {
              SemExpandableListView semExpandableListView2 = SemExpandableListView.this;
              IndicatorImageView indicatorImgView2 =
                  semExpandableListView2.new IndicatorImageView(semExpandableListView2.mContext);
              indicatorImgView2.setIndicatorPos(pos.position);
              indicatorImgView2.setImageDrawable(indicator.getConstantState().newDrawable());
              indicatorImgView2.refreshDrawableState();
              if (SemExpandableListView.this.mIndicatorAnimationType == 1) {
                lp = new FrameLayout.LayoutParams(-2, -2);
              } else {
                int paddingHeight =
                    Math.round(SemExpandableListView.this.mIndicatorPaddingHeight * 2.0f);
                if (SemExpandableListView.DEBUGGABLE_LOW) {
                  Log.m94d(
                      SemExpandableListView.TAG,
                      "onItemDecorate : mGroupIndicatorWidth = "
                          + SemExpandableListView.this.mGroupIndicatorWidth
                          + ", mGroupIndicatorHeight = "
                          + SemExpandableListView.this.mGroupIndicatorHeight);
                  Log.m94d(
                      SemExpandableListView.TAG,
                      "onItemDecorate : paddingHeight = " + paddingHeight);
                }
                lp =
                    new FrameLayout.LayoutParams(
                        SemExpandableListView.this.mGroupIndicatorWidth,
                        SemExpandableListView.this.mGroupIndicatorHeight + paddingHeight);
              }
              initIndicatorImageLayoutParams(lp);
              indicatorImgView2.setLayoutParams(lp);
              frameLayout.addView(indicatorImgView2);
              holder2.indicatorImgView = indicatorImgView2;
              holder2.indicatorImgView.setContentDescription(
                  pos.isExpanded()
                      ? SemExpandableListView.this.mDescriptionCollapse
                      : SemExpandableListView.this.mDescriptionExpand);
            }
            if (dividerHeight > 0) {
              View dividerView = new View(SemExpandableListView.this.mContext);
              dividerView.setFocusable(false);
              FrameLayout.LayoutParams lp3 = new FrameLayout.LayoutParams(-1, dividerHeight);
              lp3.gravity = 80;
              dividerView.setLayoutParams(lp3);
              Drawable dividerDrawable = SemExpandableListView.this.getDivider(pos);
              dividerView.setBackground(dividerDrawable);
              frameLayout.addView(dividerView);
              holder2.dividerView = dividerView;
              adjustDivider(holder2, isLastChild);
            }
            return frameLayout;
          }

          private void initIndicatorImageLayoutParams(FrameLayout.LayoutParams lp) {
            lp.gravity = SemExpandableListView.this.mIndicatorGravity | 16;
            lp.leftMargin = SemExpandableListView.this.mIndicatorPaddingLeft;
            lp.rightMargin = SemExpandableListView.this.mIndicatorPaddingRight;
          }

          private void adjustDivider(DecoratedItemViewHolder holder, boolean isLastChild) {
            if (holder.dividerView == null) {
              return;
            }
            ViewGroup.MarginLayoutParams itemViewLayoutParams =
                (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            int dividerHeight = SemExpandableListView.this.mExpListDividerHeight[0];
            int dividerVisibility = isLastChild ? 8 : 0;
            int bottomMargin = isLastChild ? 0 : dividerHeight;
            holder.dividerView.setVisibility(dividerVisibility);
            itemViewLayoutParams.bottomMargin = bottomMargin;
            if (holder.indicatorImgView != null) {
              FrameLayout.LayoutParams lp =
                  (FrameLayout.LayoutParams) holder.indicatorImgView.getLayoutParams();
              lp.bottomMargin = bottomMargin / 2;
            }
          }

          @Override // android.widget.SemExpandableListConnector.ItemDecorator
          public View unfoldDecoratedView(View convertView) {
            if (convertView == null) {
              return null;
            }
            if (convertView.getId() == WRAPPING_VIEW_ID && (convertView instanceof FrameLayout)) {
              return ((FrameLayout) convertView).getChildAt(0);
            }
            return convertView;
          }
        };
    this.mBitmapUpdateListener =
        new ValueAnimator
            .AnimatorUpdateListener() { // from class: android.widget.SemExpandableListView.9
          @Override // android.animation.ValueAnimator.AnimatorUpdateListener
          public void onAnimationUpdate(ValueAnimator anim) {
            float fraction = anim.getAnimatedFraction();
            int ghostViewCount = SemExpandableListView.this.mGhostViews.size();
            int ghostExpandingViewsCount =
                SemExpandableListView.this.mGhostExpandCollapseChildViews.size();
            if (SemExpandableListView.this.mAnimationState == 2) {
              SemExpandableListView.this.mGhostViewsVisibleArea.bottom =
                  SemExpandableListView.this.mGhostViewsVisibleArea.top
                      + (SemExpandableListView.this.mTranslationOffset * fraction);
            } else if (SemExpandableListView.this.mAnimationState == 3) {
              SemExpandableListView.this.mGhostViewsVisibleArea.top =
                  SemExpandableListView.this.mCollapsedGroupTopStart
                      + ((SemExpandableListView.this.mCollapsedGroupTopEnd
                              - SemExpandableListView.this.mCollapsedGroupTopStart)
                          * fraction);
              SemExpandableListView.this.mGhostViewsVisibleArea.bottom =
                  SemExpandableListView.this.mGhostViewsVisibleArea.top
                      + (SemExpandableListView.this.mTranslationOffset * (1.0f - fraction));
            } else {
              int i = 0;
              if (SemExpandableListView.this.mAnimationState == 4
                  && SemExpandableListView.this.mExpandingRects != null) {
                ExpandingRect[] expandingRectArr = SemExpandableListView.this.mExpandingRects;
                int length = expandingRectArr.length;
                while (i < length) {
                  ExpandingRect expRect = expandingRectArr[i];
                  if (expRect != null) {
                    expRect.update(fraction);
                  }
                  i++;
                }
              } else if (SemExpandableListView.this.mAnimationState == 5
                  && SemExpandableListView.this.mCollapsingRects != null) {
                CollapsingRect[] collapsingRectArr = SemExpandableListView.this.mCollapsingRects;
                int length2 = collapsingRectArr.length;
                while (i < length2) {
                  CollapsingRect collapsingRect = collapsingRectArr[i];
                  if (collapsingRect != null) {
                    collapsingRect.update(fraction);
                  }
                  i++;
                }
              }
            }
            if (ghostViewCount + ghostExpandingViewsCount == 0) {
              return;
            }
            SemExpandableListView.this.mBitmapUpdateBounds.setEmpty();
            for (int i2 = 0; i2 < ghostViewCount; i2++) {
              ViewInfo vInfo = (ViewInfo) SemExpandableListView.this.mGhostViews.get(i2);
              SemExpandableListView.this.mBitmapUpdateBounds.union(vInfo.snapshot.getBounds());
            }
            for (int i3 = 0; i3 < ghostExpandingViewsCount; i3++) {
              ViewInfo vInfo2 =
                  (ViewInfo) SemExpandableListView.this.mGhostExpandCollapseChildViews.get(i3);
              SemExpandableListView.this.mBitmapUpdateBounds.union(vInfo2.snapshot.getBounds());
            }
            SemExpandableListView semExpandableListView = SemExpandableListView.this;
            semExpandableListView.invalidate(semExpandableListView.mBitmapUpdateBounds);
          }
        };
    TypedArray a =
        context.obtainStyledAttributes(
            attrs, C4337R.styleable.ExpandableListView, defStyleAttr, defStyleRes);
    this.mGroupIndicator = a.getDrawable(0);
    this.mChildIndicator = a.getDrawable(1);
    this.mIndicatorLeft = a.getDimensionPixelSize(2, 0);
    int dimensionPixelSize = a.getDimensionPixelSize(3, 0);
    this.mIndicatorRight = dimensionPixelSize;
    if (dimensionPixelSize == 0 && (drawable = this.mGroupIndicator) != null) {
      this.mIndicatorRight = this.mIndicatorLeft + drawable.getIntrinsicWidth();
    }
    this.mChildIndicatorLeft = a.getDimensionPixelSize(4, -1);
    this.mChildIndicatorRight = a.getDimensionPixelSize(5, -1);
    this.mChildDivider = a.getDrawable(6);
    if (!isRtlCompatibilityMode()) {
      this.mIndicatorStart = a.getDimensionPixelSize(7, -2);
      this.mIndicatorEnd = a.getDimensionPixelSize(8, -2);
      this.mChildIndicatorStart = a.getDimensionPixelSize(9, -1);
      this.mChildIndicatorEnd = a.getDimensionPixelSize(10, -1);
    }
    a.recycle();
    if (this.mExpListDividerHeight == null) {
      this.mExpListDividerHeight = new int[1];
    }
    this.mDescriptionExpand =
        this.mContext
            .getResources()
            .getString(
                C4337R.string.expandablelist_indicator_description,
                this.mContext.getResources().getString(C4337R.string.expandablelist_expand));
    this.mDescriptionCollapse =
        this.mContext
            .getResources()
            .getString(
                C4337R.string.expandablelist_indicator_description,
                this.mContext.getResources().getString(C4337R.string.expandablelist_collapse));
    this.mGroupIndicatorWidth =
        (int)
            this.mContext.getResources().getDimension(C4337R.dimen.expandablelist_indicator_width);
    this.mGroupIndicatorHeight =
        (int)
            this.mContext.getResources().getDimension(C4337R.dimen.expandablelist_indicator_height);
    this.mIndicatorPaddingHeight =
        this.mContext
            .getResources()
            .getDimension(C4337R.dimen.expandablelist_indicator_padding_height);
    Paint paint = new Paint();
    this.mGroupIndicatorPaint = paint;
    paint.setAntiAlias(true);
    this.mGroupIndicatorPaint.setColor(this.mGroupIndicatorColor);
    this.mGroupIndicatorPaint.setAlpha(127);
    this.mGroupIndicatorPaint.setStyle(Paint.Style.STROKE);
    int strokeSize =
        (int) ((this.mContext.getResources().getDisplayMetrics().density * 2.0f) + 0.5f);
    this.mGroupIndicatorPaint.setStrokeWidth(strokeSize);
  }

  private boolean isRtlCompatibilityMode() {
    int targetSdkVersion = this.mContext.getApplicationInfo().targetSdkVersion;
    return targetSdkVersion < 17 || !hasRtlSupport();
  }

  private boolean hasRtlSupport() {
    return this.mContext.getApplicationInfo().hasRtlSupport();
  }

  @Override // android.widget.AbsListView, android.view.View
  public void onRtlPropertiesChanged(int layoutDirection) {
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
  public boolean onHoverEvent(MotionEvent event) {
    int action = event.getActionMasked();
    if (!isHoverable() && isHovered() && action == 10) {
      setHovered(false);
      return false;
    }
    return super.onHoverEvent(event);
  }

  private void resolveIndicator() {
    Drawable drawable;
    boolean isLayoutRtl = isLayoutRtl();
    if (isLayoutRtl) {
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
    if (this.mIndicatorRight == 0 && (drawable = this.mGroupIndicator) != null) {
      this.mIndicatorRight = this.mIndicatorLeft + drawable.getIntrinsicWidth();
    }
  }

  private void resolveChildIndicator() {
    boolean isLayoutRtl = isLayoutRtl();
    if (isLayoutRtl) {
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

  @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup,
            // android.view.View
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
    int saveCount = canvas.save();
    if (this.mAnimationState == 3) {
      canvas.clipRect(this.mGhostViewsVisibleArea);
    }
    Iterator<ViewInfo> it = this.mGhostViews.iterator();
    while (it.hasNext()) {
      ViewInfo vInfo = it.next();
      vInfo.snapshot.draw(canvas);
    }
    if (this.mAnimationState == 2) {
      canvas.clipRect(this.mGhostViewsVisibleArea);
      Iterator<ViewInfo> it2 = this.mGhostExpandCollapseChildViews.iterator();
      while (it2.hasNext()) {
        ViewInfo vInfo2 = it2.next();
        vInfo2.snapshot.draw(canvas);
      }
    }
    if (this.mAnimationState == 4) {
      int len = this.mExpandingRects.length;
      RectF expandUnionRect = new RectF();
      for (int i = 0; i < len; i++) {
        ExpandingRect expandingRect = this.mExpandingRects[i];
        if (expandingRect != null) {
          expandUnionRect.union(expandingRect.destinationRect);
        }
      }
      canvas.clipRect(expandUnionRect);
      Iterator<ViewInfo> it3 = this.mGhostExpandCollapseChildViews.iterator();
      while (it3.hasNext()) {
        ViewInfo vInfo3 = it3.next();
        vInfo3.snapshot.draw(canvas);
      }
    }
    int len2 = this.mAnimationState;
    if (len2 == 5) {
      int len3 = this.mCollapsingRects.length;
      RectF collapseUnionRect = new RectF();
      for (int i2 = 0; i2 < len3; i2++) {
        CollapsingRect collapsingRect = this.mCollapsingRects[i2];
        if (collapsingRect != null) {
          collapseUnionRect.union(collapsingRect.destinationRect);
        }
      }
      canvas.clipRect(collapseUnionRect);
      Iterator<ViewInfo> it4 = this.mGhostExpandCollapseChildViews.iterator();
      while (it4.hasNext()) {
        ViewInfo vInfo4 = it4.next();
        vInfo4.snapshot.draw(canvas);
      }
    }
    canvas.restoreToCount(saveCount);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public Drawable getIndicator(SemExpandableListConnector.PositionMetadata positionMetadata) {
    Drawable drawable;
    int[] iArr;
    if (positionMetadata.position.type == 2) {
      drawable = this.mGroupIndicator;
      if (drawable != null && drawable.isStateful()) {
        drawable.setState(
            GROUP_STATE_SETS[
                (positionMetadata.groupMetadata == null
                            || positionMetadata.groupMetadata.lastChildFlPos
                                == positionMetadata.groupMetadata.flPos
                        ? 2
                        : 0)
                    | (positionMetadata.isExpanded() ? 1 : 0)]);
      }
    } else {
      drawable = this.mChildIndicator;
      if (drawable != null && drawable.isStateful()) {
        if (positionMetadata.position.flatListPos
            == positionMetadata.groupMetadata.lastChildFlPos) {
          iArr = CHILD_LAST_STATE_SET;
        } else {
          iArr = EMPTY_STATE_SET;
        }
        drawable.setState(iArr);
      }
    }
    return drawable;
  }

  public void setChildDivider(Drawable childDivider) {
    this.mChildDivider = childDivider;
  }

  @Override // android.widget.ListView
  void drawDivider(Canvas canvas, Rect bounds, int childIndex) {
    int flatListPosition = this.mFirstPosition + childIndex;
    if (flatListPosition >= 0) {
      int adjustedPosition = getFlatPositionForConnector(flatListPosition);
      SemExpandableListConnector.PositionMetadata pos =
          this.mConnector.getUnflattenedPos(adjustedPosition);
      if (pos.position.type == 1
          || (pos.isExpanded() && pos.groupMetadata.lastChildFlPos != pos.groupMetadata.flPos)) {
        Drawable divider = this.mChildDivider;
        divider.setBounds(bounds);
        divider.draw(canvas);
        pos.recycle();
        return;
      }
      pos.recycle();
    }
    super.drawDivider(canvas, bounds, flatListPosition);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public Drawable getDivider(SemExpandableListConnector.PositionMetadata pos) {
    if (pos.position.type == 1) {
      return this.mChildDivider;
    }
    return this.mDivider;
  }

  @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView
  public void setAdapter(ListAdapter adapter) {
    throw new RuntimeException(
        "For ExpandableListView, use setAdapter(ExpandableListAdapter) instead of"
            + " setAdapter(ListAdapter)");
  }

  @Override // android.widget.ListView, android.widget.AdapterView
  public ListAdapter getAdapter() {
    return super.getAdapter();
  }

  @Override // android.widget.AdapterView
  public void setOnItemClickListener(AdapterView.OnItemClickListener l) {
    super.setOnItemClickListener(l);
  }

  public void setAdapter(ExpandableListAdapter adapter) {
    this.mAdapter = adapter;
    if (adapter != null) {
      SemExpandableListConnector semExpandableListConnector =
          new SemExpandableListConnector(adapter);
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
  public boolean isHeaderOrFooterPosition(int position) {
    int footerViewsStart = this.mItemCount - getFooterViewsCount();
    return position < getHeaderViewsCount() || position >= footerViewsStart;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public int getFlatPositionForConnector(int flatListPosition) {
    return flatListPosition - getHeaderViewsCount();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public int getAbsoluteFlatPosition(int flatListPosition) {
    return getHeaderViewsCount() + flatListPosition;
  }

  @Override // android.widget.AbsListView, android.widget.AdapterView
  public boolean performItemClick(View v, int position, long id) {
    View unfoldedDecoratedItemView = this.mItemDecorator.unfoldDecoratedView(v);
    if (isHeaderOrFooterPosition(position)) {
      return super.performItemClick(unfoldedDecoratedItemView, position, id);
    }
    int adjustedPosition = getFlatPositionForConnector(position);
    return handleItemClick(unfoldedDecoratedItemView, adjustedPosition, id);
  }

  boolean handleItemClick(View view, int position, long id) {
    boolean returnValue;
    SemExpandableListConnector.PositionMetadata posMetadata =
        this.mConnector.getUnflattenedPos(position);
    long id2 = getChildOrGroupId(posMetadata.position);
    if (posMetadata.position.type == 2) {
      OnGroupClickListener onGroupClickListener = this.mOnGroupClickListener;
      if (onGroupClickListener != null
          && onGroupClickListener.onGroupClick(this, view, posMetadata.position.groupPos, id2)) {
        playSoundEffect(0);
        posMetadata.recycle();
        return true;
      }
      if (this.mAnimationEnabled) {
        captureViewsPriorAnimation();
      }
      final int groupPos = posMetadata.position.groupPos;
      if (posMetadata.isExpanded()) {
        Runnable animationEndRunnable =
            new Runnable() { // from class: android.widget.SemExpandableListView.2
              @Override // java.lang.Runnable
              public void run() {
                if (SemExpandableListView.this.mOnGroupCollapseListener != null) {
                  SemExpandableListView.this.mOnGroupCollapseListener.onGroupCollapse(groupPos);
                }
              }
            };
        if (this.mAnimationEnabled) {
          startCollapseAnimation(groupPos, animationEndRunnable);
        }
        this.mConnector.collapseGroup(posMetadata);
        post(
            new Runnable() { // from class: android.widget.SemExpandableListView.3
              @Override // java.lang.Runnable
              public void run() {
                SemExpandableListView.this.requestLayout();
              }
            });
        playSoundEffect(0);
        if (!this.mAnimationEnabled) {
          animationEndRunnable.run();
        }
      } else {
        this.mConnector.expandGroup(posMetadata);
        playSoundEffect(0);
        int headerViewsCount = posMetadata.position.flatListPos + getHeaderViewsCount();
        Runnable animationEndRunnable2 =
            new Runnable() { // from class: android.widget.SemExpandableListView.4
              @Override // java.lang.Runnable
              public void run() {
                if (SemExpandableListView.this.mOnGroupExpandListener != null) {
                  SemExpandableListView.this.mOnGroupExpandListener.onGroupExpand(groupPos);
                }
              }
            };
        if (this.mAnimationEnabled) {
          startExpandAnimation(groupPos, animationEndRunnable2);
        } else {
          animationEndRunnable2.run();
        }
      }
      returnValue = true;
    } else {
      if (this.mOnChildClickListener != null) {
        playSoundEffect(0);
        return this.mOnChildClickListener.onChildClick(
            this, view, posMetadata.position.groupPos, posMetadata.position.childPos, id2);
      }
      returnValue = false;
    }
    posMetadata.recycle();
    return returnValue;
  }

  private void startExpandAnimation(final int groupPos, final Runnable animationEndRunnable) {
    this.mBlockTouchEvent = true;
    getViewTreeObserver()
        .addOnPreDrawListener(
            new ViewTreeObserver
                .OnPreDrawListener() { // from class: android.widget.SemExpandableListView.5
              @Override // android.view.ViewTreeObserver.OnPreDrawListener
              public boolean onPreDraw() {
                long nextExpGroupPackedPosition;
                boolean z;
                SemExpandableListView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                int childCount = SemExpandableListView.this.getChildCount();
                if (childCount == 0) {
                  SemExpandableListView.this.resetExpandAnimationState();
                  animationEndRunnable.run();
                  return true;
                }
                long expGroupPackedPosition =
                    SemExpandableListView.getPackedPositionForGroup(groupPos);
                long nextExpGroupPackedPosition2 =
                    SemExpandableListView.getPackedPositionForGroup(groupPos + 1);
                int expGroupFlatPos =
                    SemExpandableListView.this.getFlatListPosition(expGroupPackedPosition);
                int nextExpGroupFlatPos =
                    SemExpandableListView.this.getFlatListPosition(nextExpGroupPackedPosition2);
                int firstVisiblePos = SemExpandableListView.this.getFirstVisiblePosition();
                View expandedGroup =
                    SemExpandableListView.this.getChildAt(expGroupFlatPos - firstVisiblePos);
                if (expandedGroup == null) {
                  Log.m96e(
                      SemExpandableListView.TAG,
                      "startExpandAnimation() groupPos="
                          + groupPos
                          + ", firstPos="
                          + firstVisiblePos
                          + ", expGroupFlatPos="
                          + expGroupFlatPos);
                  SemExpandableListView.this.resetExpandAnimationState();
                  animationEndRunnable.run();
                  return true;
                }
                View nextExpandedGroup =
                    SemExpandableListView.this.getChildAt(nextExpGroupFlatPos - firstVisiblePos);
                if (nextExpandedGroup == null) {
                  int height = SemExpandableListView.this.getHeight();
                  SemExpandableListView semExpandableListView = SemExpandableListView.this;
                  int listBottom =
                      Math.min(
                          height,
                          semExpandableListView
                              .getChildAt(semExpandableListView.getChildCount() - 1)
                              .getBottom());
                  SemExpandableListView.this.mTranslationOffset =
                      listBottom - expandedGroup.getBottom();
                } else {
                  SemExpandableListView.this.mTranslationOffset =
                      nextExpandedGroup.getTop() - expandedGroup.getBottom();
                }
                SemExpandableListView.this.mGhostViewsVisibleArea.left = 0.0f;
                SemExpandableListView.this.mGhostViewsVisibleArea.right =
                    SemExpandableListView.this.getWidth();
                SemExpandableListView.this.mGhostViewsVisibleArea.top = expandedGroup.getBottom();
                SemExpandableListView.this.mGhostViewsVisibleArea.bottom =
                    SemExpandableListView.this.mGhostViewsVisibleArea.top;
                int animationDuration = SemExpandableListView.this.getExpandCollapseDuration();
                ArrayList<Animator> animations = new ArrayList<>();
                int i = 0;
                while (i < childCount) {
                  int position = i + firstVisiblePos;
                  View child = SemExpandableListView.this.getChildAt(i);
                  int childCount2 = childCount;
                  long expGroupPackedPosition2 = expGroupPackedPosition;
                  long packedPos = SemExpandableListView.this.getExpandableListPosition(position);
                  ViewInfo oldViewInfo =
                      (ViewInfo) SemExpandableListView.this.mViewSnapshots.get(packedPos);
                  if (oldViewInfo != null) {
                    nextExpGroupPackedPosition = nextExpGroupPackedPosition2;
                    SemExpandableListView.this.mViewSnapshots.remove(packedPos);
                    if (child.getTop() == oldViewInfo.top) {
                      z = false;
                    } else {
                      child.setTranslationY(-SemExpandableListView.this.mTranslationOffset);
                      ObjectAnimator translateAnim =
                          ObjectAnimator.ofFloat(child, View.TRANSLATION_Y, 0.0f);
                      animations.add(translateAnim);
                      z = false;
                    }
                  } else {
                    nextExpGroupPackedPosition = nextExpGroupPackedPosition2;
                    if (child.getWidth() == 0) {
                      z = false;
                    } else if (child.getHeight() == 0) {
                      z = false;
                    } else {
                      int adjustedPosition =
                          SemExpandableListView.this.getFlatPositionForConnector(position);
                      SemExpandableListConnector.PositionMetadata pos =
                          SemExpandableListView.this.mConnector.getUnflattenedPos(adjustedPosition);
                      if (pos.isExpanded()) {
                        int i2 = pos.position.groupPos;
                        int adjustedPosition2 = groupPos;
                        if (i2 != adjustedPosition2 || pos.position.childPos == -1) {
                          z = false;
                        } else {
                          SemExpandableListView.this.mGhostExpandCollapseChildViews.add(
                              new ViewInfo(child));
                          z = false;
                          child.setAlpha(0.0f);
                        }
                      } else {
                        z = false;
                      }
                      pos.recycle();
                    }
                  }
                  i++;
                  childCount = childCount2;
                  expGroupPackedPosition = expGroupPackedPosition2;
                  nextExpGroupPackedPosition2 = nextExpGroupPackedPosition;
                }
                SemExpandableListView.this.startIndicatorAnimation(
                    expandedGroup, true, animationDuration);
                int viewSnapshotsCount = SemExpandableListView.this.mViewSnapshots.size();
                for (int i3 = 0; i3 < viewSnapshotsCount; i3++) {
                  ViewInfo viewInfo =
                      (ViewInfo) SemExpandableListView.this.mViewSnapshots.valueAt(i3);
                  SemExpandableListView semExpandableListView2 = SemExpandableListView.this;
                  ObjectAnimator animBounds =
                      semExpandableListView2.createViewSnapshotAnimation(
                          semExpandableListView2.mTranslationOffset, viewInfo);
                  animations.add(animBounds);
                  SemExpandableListView.this.mGhostViews.add(viewInfo);
                }
                ValueAnimator anim = ValueAnimator.ofFloat(0.0f, 1.0f);
                anim.addUpdateListener(SemExpandableListView.this.mBitmapUpdateListener);
                animations.add(anim);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(animations);
                set.setDuration(animationDuration);
                set.setInterpolator(SemExpandableListView.EXPAND_COLLAPSE_INTERPOLATOR);
                set.addListener(
                    new AnimatorListenerAdapter() { // from class:
                                                    // android.widget.SemExpandableListView.5.1
                      @Override // android.animation.AnimatorListenerAdapter,
                                // android.animation.Animator.AnimatorListener
                      public void onAnimationStart(Animator animation) {
                        SemExpandableListView.this.mAnimationState = 2;
                      }

                      @Override // android.animation.AnimatorListenerAdapter,
                                // android.animation.Animator.AnimatorListener
                      public void onAnimationEnd(Animator animation) {
                        Log.m94d(SemExpandableListView.TAG, "expand animation finished");
                        animationEndRunnable.run();
                        SemExpandableListView.this.resetExpandAnimationState();
                      }
                    });
                set.start();
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

  private void startExpandAllAnimation(
      final boolean[] expanded, final Runnable animationEndRunnable) {
    this.mBlockTouchEvent = true;
    getViewTreeObserver()
        .addOnPreDrawListener(
            new ViewTreeObserver
                .OnPreDrawListener() { // from class: android.widget.SemExpandableListView.6
              /* JADX WARN: Removed duplicated region for block: B:24:0x00f2  */
              /* JADX WARN: Removed duplicated region for block: B:43:0x01b5  */
              @Override // android.view.ViewTreeObserver.OnPreDrawListener
              /*
                  Code decompiled incorrectly, please refer to instructions dump.
              */
              public boolean onPreDraw() {
                int lastValidPos;
                int lastValidPos2;
                int childCount;
                int lastGroupId;
                int listBottom;
                ViewInfo lastVisPrevViewInfo;
                View child;
                int nextGroupTop;
                boolean allExpanded;
                SemExpandableListView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                int childCount2 = SemExpandableListView.this.getChildCount();
                if (childCount2 == 0) {
                  SemExpandableListView.this.resetExpandAnimationState();
                  animationEndRunnable.run();
                  return true;
                }
                ArrayList<Animator> animations = new ArrayList<>();
                int firstVisiblePos = SemExpandableListView.this.getFirstVisiblePosition();
                int lastValidPos3 = SemExpandableListView.this.getLastNonFooterPosition();
                if (lastValidPos3 < firstVisiblePos) {
                  animationEndRunnable.run();
                  SemExpandableListView.this.resetExpandAnimationState();
                  return true;
                }
                long lastPosPackedPos =
                    SemExpandableListView.this.getExpandableListPosition(lastValidPos3);
                int lastGroupId2 = SemExpandableListView.getPackedPositionGroup(lastPosPackedPos);
                int[] groupOffsets = new int[lastGroupId2 + 1];
                SemExpandableListView.this.mGhostViewsVisibleAreas = new RectF[lastGroupId2 + 1];
                SemExpandableListView.this.mExpandingRects = new ExpandingRect[lastGroupId2 + 1];
                int height = SemExpandableListView.this.getHeight();
                SemExpandableListView semExpandableListView = SemExpandableListView.this;
                int listBottom2 =
                    Math.min(
                        height,
                        semExpandableListView
                            .getChildAt(semExpandableListView.getChildCount() - 1)
                            .getBottom());
                ViewInfo lastVisPrevViewInfo2 =
                    (ViewInfo) SemExpandableListView.this.mViewSnapshots.get(lastPosPackedPos);
                if (lastVisPrevViewInfo2 != null) {
                  SemExpandableListView.this.mTranslationOffset =
                      listBottom2 - lastVisPrevViewInfo2.bottom;
                } else {
                  long lastGroupPosPackedPos =
                      SemExpandableListView.getPackedPositionForGroup(lastGroupId2);
                  ViewInfo lastVisGroupPrevViewInfo =
                      (ViewInfo)
                          SemExpandableListView.this.mViewSnapshots.get(lastGroupPosPackedPos);
                  if (lastVisGroupPrevViewInfo == null) {
                    SemExpandableListView.this.mTranslationOffset = listBottom2;
                  } else {
                    SemExpandableListView semExpandableListView2 = SemExpandableListView.this;
                    int firstVisiblePos2 = lastVisGroupPrevViewInfo.bottom;
                    semExpandableListView2.mTranslationOffset = listBottom2 - firstVisiblePos2;
                  }
                }
                boolean allExpanded2 = true;
                int position = 0;
                while (position < childCount2) {
                  View child2 = SemExpandableListView.this.getChildAt(position);
                  long packedPos = SemExpandableListView.this.getExpandableListPosition(position);
                  ViewInfo oldViewInfo =
                      (ViewInfo) SemExpandableListView.this.mViewSnapshots.get(packedPos);
                  if (SemExpandableListView.getPackedPositionType(packedPos) != 0) {
                    lastValidPos = lastValidPos3;
                  } else {
                    lastValidPos = lastValidPos3;
                    if (!SemExpandableListView.this.isHeaderOrFooterPosition(position)) {
                      lastValidPos2 = 1;
                      int groupPos = SemExpandableListView.getPackedPositionGroup(packedPos);
                      long lastPosPackedPos2 = lastPosPackedPos;
                      if (oldViewInfo == null) {
                        SemExpandableListView.this.mViewSnapshots.remove(packedPos);
                        int offset = child2.getTop() - oldViewInfo.top;
                        if (lastValidPos2 == 0) {
                          childCount = childCount2;
                          lastGroupId = lastGroupId2;
                          listBottom = listBottom2;
                          lastVisPrevViewInfo = lastVisPrevViewInfo2;
                          child = child2;
                        } else {
                          groupOffsets[groupPos] = offset;
                          SemExpandableListView.this.mGhostViewsVisibleAreas[groupPos] =
                              new RectF();
                          listBottom = listBottom2;
                          lastVisPrevViewInfo = lastVisPrevViewInfo2;
                          long nextExpGroupPackedPosition =
                              SemExpandableListView.getPackedPositionForGroup(groupPos + 1);
                          int nextExpGroupFlatPos =
                              SemExpandableListView.this.getFlatListPosition(
                                  nextExpGroupPackedPosition);
                          if (nextExpGroupFlatPos >= childCount2) {
                            childCount = childCount2;
                            nextGroupTop = SemExpandableListView.this.getHeight();
                          } else {
                            childCount = childCount2;
                            nextGroupTop =
                                SemExpandableListView.this.getChildAt(nextExpGroupFlatPos).getTop();
                          }
                          boolean z = expanded[groupPos];
                          boolean allExpanded3 = allExpanded2 & z;
                          if (!z) {
                            lastGroupId = lastGroupId2;
                            child = child2;
                            allExpanded = allExpanded3;
                            SemExpandableListView.this.startIndicatorAnimation(child, true, 700);
                            RectF endRect =
                                new RectF(
                                    oldViewInfo.left,
                                    child.getBottom(),
                                    oldViewInfo.right,
                                    nextGroupTop);
                            SemExpandableListView.this.mExpandingRects[groupPos] =
                                new ExpandingRect(
                                    oldViewInfo.bottom,
                                    endRect,
                                    SemExpandableListView.this.mGhostViewsVisibleAreas[groupPos]);
                          } else {
                            lastGroupId = lastGroupId2;
                            child = child2;
                            allExpanded = allExpanded3;
                          }
                          allExpanded2 = allExpanded;
                        }
                        if (child.getTop() != oldViewInfo.top) {
                          child.setTranslationY(-offset);
                          ObjectAnimator translateAnim =
                              ObjectAnimator.ofFloat(child, View.TRANSLATION_Y, 0.0f);
                          animations.add(translateAnim);
                        }
                      } else {
                        childCount = childCount2;
                        lastGroupId = lastGroupId2;
                        listBottom = listBottom2;
                        lastVisPrevViewInfo = lastVisPrevViewInfo2;
                        if (child2.getWidth() != 0 && child2.getHeight() != 0) {
                          if (lastValidPos2 != 0) {
                            throw new RuntimeException("How on Earth this is possible?");
                          }
                          ViewInfo viewInfo = new ViewInfo(child2);
                          SemExpandableListView.this.mGhostExpandCollapseChildViews.add(viewInfo);
                          child2.setAlpha(0.0f);
                          ObjectAnimator animBounds =
                              SemExpandableListView.this.createViewSnapshotAnimationReverse(
                                  -groupOffsets[groupPos], viewInfo);
                          animations.add(animBounds);
                        }
                      }
                      position++;
                      lastValidPos3 = lastValidPos;
                      lastPosPackedPos = lastPosPackedPos2;
                      listBottom2 = listBottom;
                      lastVisPrevViewInfo2 = lastVisPrevViewInfo;
                      childCount2 = childCount;
                      lastGroupId2 = lastGroupId;
                    }
                  }
                  lastValidPos2 = 0;
                  int groupPos2 = SemExpandableListView.getPackedPositionGroup(packedPos);
                  long lastPosPackedPos22 = lastPosPackedPos;
                  if (oldViewInfo == null) {}
                  position++;
                  lastValidPos3 = lastValidPos;
                  lastPosPackedPos = lastPosPackedPos22;
                  listBottom2 = listBottom;
                  lastVisPrevViewInfo2 = lastVisPrevViewInfo;
                  childCount2 = childCount;
                  lastGroupId2 = lastGroupId;
                }
                int viewSnapshotsCount = SemExpandableListView.this.mViewSnapshots.size();
                for (int i = 0; i < viewSnapshotsCount; i++) {
                  ViewInfo viewInfo2 =
                      (ViewInfo) SemExpandableListView.this.mViewSnapshots.valueAt(i);
                  SemExpandableListView semExpandableListView3 = SemExpandableListView.this;
                  ObjectAnimator animBounds2 =
                      semExpandableListView3.createViewSnapshotAnimation(
                          semExpandableListView3.mTranslationOffset, viewInfo2);
                  animations.add(animBounds2);
                  SemExpandableListView.this.mGhostViews.add(viewInfo2);
                }
                Animator.AnimatorListener listener =
                    new AnimatorListenerAdapter() { // from class:
                                                    // android.widget.SemExpandableListView.6.1
                      @Override // android.animation.AnimatorListenerAdapter,
                                // android.animation.Animator.AnimatorListener
                      public void onAnimationStart(Animator animation) {
                        SemExpandableListView.this.mAnimationState = 4;
                      }

                      @Override // android.animation.AnimatorListenerAdapter,
                                // android.animation.Animator.AnimatorListener
                      public void onAnimationEnd(Animator animation) {
                        Log.m94d(SemExpandableListView.TAG, "expand animation finished");
                        animationEndRunnable.run();
                        SemExpandableListView.this.resetExpandAnimationState();
                      }
                    };
                if (allExpanded2) {
                  listener.onAnimationEnd(null);
                  return false;
                }
                ValueAnimator anim = ValueAnimator.ofFloat(0.0f, 1.0f);
                anim.addUpdateListener(SemExpandableListView.this.mBitmapUpdateListener);
                animations.add(anim);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(animations);
                set.setDuration(700);
                set.setInterpolator(SemExpandableListView.EXPAND_COLLAPSE_INTERPOLATOR);
                set.addListener(listener);
                set.start();
                SemExpandableListView.this.mViewSnapshots.clear();
                return false;
              }
            });
  }

  private static class ExpandingRect {
    RectF destinationRect;
    RectF endRect;
    int startY;

    ExpandingRect(int startY, RectF endRect, RectF destRect) {
      this.startY = startY;
      this.endRect = endRect;
      this.destinationRect = destRect;
    }

    void update(float fraction) {
      this.destinationRect.left = this.endRect.left;
      this.destinationRect.right = this.endRect.right;
      this.destinationRect.top = this.startY + ((this.endRect.top - this.startY) * fraction);
      RectF rectF = this.destinationRect;
      rectF.bottom = rectF.top + (this.endRect.height() * fraction);
    }
  }

  private static class CollapsingRect {
    RectF destinationRect;
    int finishY;
    RectF startRect;

    CollapsingRect(RectF endRect, RectF destRect) {
      this.startRect = endRect;
      this.destinationRect = destRect;
    }

    void setFinishY(int finishY) {
      this.finishY = finishY;
    }

    void update(float fraction) {
      this.destinationRect.left = this.startRect.left;
      this.destinationRect.right = this.startRect.right;
      this.destinationRect.top =
          this.startRect.top + ((this.finishY - this.startRect.top) * fraction);
      RectF rectF = this.destinationRect;
      rectF.bottom = rectF.top + (this.startRect.height() * (1.0f - fraction));
    }
  }

  protected int getExpandCollapseDuration() {
    int animationDuration = (int) (Math.sqrt(this.mTranslationOffset / getHeight()) * 700.0d);
    if (animationDuration < 400) {
      return 400;
    }
    return animationDuration;
  }

  @Override // android.widget.ListView, android.widget.AbsListView
  protected void layoutChildren() {
    Rect before = null;
    if (this.mAnimationState == 3 && this.mSelectorRect != null) {
      before = new Rect(this.mSelectorRect);
    }
    super.layoutChildren();
    if (this.mAnimationState == 3 && before != null) {
      this.mSelectorRect.set(before);
    }
  }

  private void startCollapseAnimation(
      final int groupPosBefore, final Runnable animationEndRunnable) {
    long collapsedGroupPackedPosition = getPackedPositionForGroup(groupPosBefore);
    long nextCollapsedGroupPackedPosition = getPackedPositionForGroup(groupPosBefore + 1);
    final int collapsedGroupFlatPosBefore = getFlatListPosition(collapsedGroupPackedPosition);
    int nextCollapsedGroupFlatPos = getFlatListPosition(nextCollapsedGroupPackedPosition);
    final int firstVisiblePosBefore = getFirstVisiblePosition();
    View collapsedGroupBefore = getChildAt(collapsedGroupFlatPosBefore - firstVisiblePosBefore);
    if (collapsedGroupBefore == null) {
      Log.m96e(
          TAG,
          "startCollapseAnimation() BEFORE: groupPos="
              + groupPosBefore
              + ", flatPos="
              + collapsedGroupFlatPosBefore
              + ", firstPos="
              + firstVisiblePosBefore);
      resetCollapseAnimationState();
      animationEndRunnable.run();
      return;
    }
    final int collapsedGroupTopBefore = collapsedGroupBefore.getTop();
    View nextCollapsedGroup = getChildAt(nextCollapsedGroupFlatPos - firstVisiblePosBefore);
    if (nextCollapsedGroup == null) {
      int listBottom = Math.min(getHeight(), getChildAt(getChildCount() - 1).getBottom());
      this.mTranslationOffset = listBottom - collapsedGroupBefore.getBottom();
    } else {
      this.mTranslationOffset = nextCollapsedGroup.getTop() - collapsedGroupBefore.getBottom();
    }
    final int groupCountBefore = this.mAdapter.getGroupCount();
    final int listTotalChildrenCountBefore = getChildCount();
    this.mGhostViewsVisibleArea.left = 0.0f;
    this.mGhostViewsVisibleArea.right = getWidth();
    this.mCollapsedGroupTopStart = collapsedGroupBefore.getBottom();
    this.mBlockTouchEvent = true;
    getViewTreeObserver()
        .addOnPreDrawListener(
            new ViewTreeObserver
                .OnPreDrawListener() { // from class: android.widget.SemExpandableListView.7
              @Override // android.view.ViewTreeObserver.OnPreDrawListener
              public boolean onPreDraw() {
                int groupPosAfter;
                int childCount;
                int firstVisiblePosAfter;
                int childCount2;
                SemExpandableListView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                int childCount3 = SemExpandableListView.this.getChildCount();
                if (childCount3 == 0) {
                  SemExpandableListView.this.resetCollapseAnimationState();
                  animationEndRunnable.run();
                  return true;
                }
                int animationDuration = SemExpandableListView.this.getExpandCollapseDuration();
                ArrayList<Animator> animations = new ArrayList<>();
                int firstVisiblePosAfter2 = SemExpandableListView.this.getFirstVisiblePosition();
                int collapsedGroupFlatPosAfter =
                    SemExpandableListView.this.getFlatListPosition(
                        SemExpandableListView.getPackedPositionForGroup(groupPosBefore));
                View collapsedGroupAfter =
                    SemExpandableListView.this.getChildAt(
                        collapsedGroupFlatPosAfter - firstVisiblePosAfter2);
                if (collapsedGroupAfter == null) {
                  Log.m96e(
                      SemExpandableListView.TAG,
                      "startCollapseAnimation() BEFORE: groupPos="
                          + groupPosBefore
                          + ", flatPos="
                          + collapsedGroupFlatPosBefore
                          + ", groupCount="
                          + groupCountBefore
                          + ", firstPos="
                          + firstVisiblePosBefore
                          + ", totalListChildrenCount="
                          + listTotalChildrenCountBefore
                          + "; AFTER: flatPos="
                          + collapsedGroupFlatPosAfter
                          + ", groupCount="
                          + SemExpandableListView.this.mAdapter.getGroupCount()
                          + ", firstPos="
                          + firstVisiblePosAfter2
                          + ", totalListChildrenCount="
                          + SemExpandableListView.this.getChildCount());
                  SemExpandableListView.this.resetCollapseAnimationState();
                  animationEndRunnable.run();
                  return true;
                }
                int collapsedGroupTopAfter = collapsedGroupAfter.getTop();
                int collapsedGroupShift = collapsedGroupTopAfter - collapsedGroupTopBefore;
                int i = 0;
                while (i < childCount3) {
                  View child = SemExpandableListView.this.getChildAt(i);
                  long packedPos =
                      SemExpandableListView.this.getExpandableListPosition(
                          i + firstVisiblePosAfter2);
                  ViewInfo oldViewInfo =
                      (ViewInfo) SemExpandableListView.this.mViewSnapshots.get(packedPos);
                  if (oldViewInfo != null) {
                    SemExpandableListView.this.mViewSnapshots.remove(packedPos);
                    groupPosAfter = oldViewInfo.top - child.getTop();
                  } else {
                    int groupPosAfter2 = SemExpandableListView.getPackedPositionGroup(packedPos);
                    if (groupPosAfter2 == -3) {
                      groupPosAfter =
                          SemExpandableListView.this.mTranslationOffset - collapsedGroupShift;
                    } else if (groupPosAfter2 == -2) {
                      groupPosAfter = -collapsedGroupShift;
                    } else {
                      int offset = groupPosBefore;
                      if (groupPosAfter2 > offset) {
                        groupPosAfter =
                            SemExpandableListView.this.mTranslationOffset - collapsedGroupShift;
                      } else {
                        int offset2 = -collapsedGroupShift;
                        groupPosAfter = offset2;
                      }
                    }
                  }
                  if (groupPosAfter == 0) {
                    childCount = childCount3;
                    firstVisiblePosAfter = firstVisiblePosAfter2;
                    childCount2 = 1;
                  } else {
                    if (i + firstVisiblePosAfter2 == collapsedGroupFlatPosAfter
                        && SemExpandableListView.this.mSelectorRect != null) {
                      animations.add(SemExpandableListView.this.getSelectorRectAnim(groupPosAfter));
                    }
                    child.setTranslationY(groupPosAfter);
                    childCount = childCount3;
                    firstVisiblePosAfter = firstVisiblePosAfter2;
                    childCount2 = 1;
                    ObjectAnimator translateAnim =
                        ObjectAnimator.ofFloat(child, View.TRANSLATION_Y, 0.0f);
                    animations.add(translateAnim);
                  }
                  i++;
                  firstVisiblePosAfter2 = firstVisiblePosAfter;
                  childCount3 = childCount;
                }
                SemExpandableListView.this.startIndicatorAnimation(
                    collapsedGroupAfter, false, animationDuration);
                int viewSnapshotsCount = SemExpandableListView.this.mViewSnapshots.size();
                for (int i2 = 0; i2 < viewSnapshotsCount; i2++) {
                  ViewInfo viewInfo =
                      (ViewInfo) SemExpandableListView.this.mViewSnapshots.valueAt(i2);
                  ObjectAnimator animBounds =
                      SemExpandableListView.this.createViewSnapshotAnimation(
                          collapsedGroupShift, viewInfo);
                  SemExpandableListView.this.mGhostViews.add(viewInfo);
                  animations.add(animBounds);
                }
                if (viewSnapshotsCount > 0) {
                  ValueAnimator anim = ValueAnimator.ofFloat(0.0f, 1.0f);
                  anim.addUpdateListener(SemExpandableListView.this.mBitmapUpdateListener);
                  animations.add(anim);
                }
                SemExpandableListView semExpandableListView = SemExpandableListView.this;
                semExpandableListView.mCollapsedGroupTopEnd =
                    semExpandableListView.mCollapsedGroupTopStart + collapsedGroupShift;
                AnimatorSet set = new AnimatorSet();
                set.playTogether(animations);
                set.setDuration(animationDuration);
                set.setInterpolator(SemExpandableListView.EXPAND_COLLAPSE_INTERPOLATOR);
                set.addListener(
                    new AnimatorListenerAdapter() { // from class:
                                                    // android.widget.SemExpandableListView.7.1
                      @Override // android.animation.AnimatorListenerAdapter,
                                // android.animation.Animator.AnimatorListener
                      public void onAnimationStart(Animator animation) {
                        SemExpandableListView.this.mAnimationState = 3;
                      }

                      @Override // android.animation.AnimatorListenerAdapter,
                                // android.animation.Animator.AnimatorListener
                      public void onAnimationEnd(Animator animation) {
                        Log.m94d(SemExpandableListView.TAG, "expand animation finished");
                        animationEndRunnable.run();
                        SemExpandableListView.this.resetCollapseAnimationState();
                      }
                    });
                set.start();
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
  public Animator getSelectorRectAnim(int offset) {
    Rect newViewBounds = new Rect(this.mSelectorRect);
    Rect oldViewBounds = new Rect(newViewBounds);
    oldViewBounds.offset(0, offset);
    ObjectAnimator animBounds =
        ObjectAnimator.ofObject(
            this.mSelectorRect,
            "",
            SemAnimatorUtils.BOUNDS_EVALUATOR,
            oldViewBounds,
            newViewBounds);
    return animBounds;
  }

  private void startCollapseAllAnimation(
      final boolean[] expanded, final Runnable animationEndRunnable) {
    RectF startRect;
    int firstVisiblePos = getFirstVisiblePosition();
    int lastValidPos = getLastNonFooterPosition();
    if (lastValidPos < firstVisiblePos) {
      return;
    }
    long lastPosPackedPos = getExpandableListPosition(lastValidPos);
    final int lastGroupIdBefore = getPackedPositionGroup(lastPosPackedPos);
    final int lastPositionBottomBefore = getChildAt(lastValidPos).getBottom();
    int[] groupOffsets = new int[lastGroupIdBefore + 1];
    this.mGhostViewsVisibleAreas = new RectF[lastGroupIdBefore + 1];
    this.mCollapsingRects = new CollapsingRect[lastGroupIdBefore + 1];
    int childCount = getChildCount();
    int firstGroupPosition = getAbsoluteFlatPosition(0);
    View lastGroupView = getChildAt(firstGroupPosition);
    int i = 1;
    View lastGroupView2 = lastGroupView;
    while (i < groupOffsets.length) {
      long groupPackedPos = getPackedPositionForGroup(i);
      int pos = getFlatListPosition(groupPackedPos);
      View groupView = getChildAt(pos);
      groupOffsets[i] = (groupOffsets[i - 1] + groupView.getTop()) - lastGroupView2.getBottom();
      int pos2 = lastGroupView2.getLeft();
      RectF startRect2 =
          new RectF(
              pos2, lastGroupView2.getBottom(), lastGroupView2.getRight(), groupView.getTop());
      this.mGhostViewsVisibleAreas[i - 1] = new RectF();
      this.mCollapsingRects[i - 1] =
          new CollapsingRect(startRect2, this.mGhostViewsVisibleAreas[i - 1]);
      lastGroupView2 = groupView;
      i++;
      firstGroupPosition = firstGroupPosition;
    }
    View lastChild = getChildAt(lastValidPos);
    RectF startRect3 =
        new RectF(
            lastChild.getLeft(),
            lastGroupView2.getBottom(),
            lastChild.getRight(),
            lastChild.getBottom());
    this.mGhostViewsVisibleAreas[lastGroupIdBefore] = new RectF();
    this.mCollapsingRects[lastGroupIdBefore] =
        new CollapsingRect(startRect3, this.mGhostViewsVisibleAreas[lastGroupIdBefore]);
    final ArrayList<Animator> animations = new ArrayList<>();
    int i2 = 0;
    while (true) {
      if (i2 < childCount) {
        if (isHeaderOrFooterPosition(i2 + firstVisiblePos)) {
          startRect = startRect3;
        } else {
          long expandableListPos = getExpandableListPosition(i2);
          boolean isGroup = getPackedPositionType(expandableListPos) == 0;
          if (isGroup) {
            startRect = startRect3;
          } else {
            int groupId = getPackedPositionGroup(expandableListPos);
            ViewInfo viewInfo = this.mViewSnapshots.get(expandableListPos);
            startRect = startRect3;
            this.mGhostExpandCollapseChildViews.add(viewInfo);
            this.mViewSnapshots.remove(expandableListPos);
            ObjectAnimator animBounds =
                createViewSnapshotAnimation(-groupOffsets[groupId], viewInfo);
            animations.add(animBounds);
          }
        }
        i2++;
        startRect3 = startRect;
      } else {
        this.mBlockTouchEvent = true;
        getViewTreeObserver()
            .addOnPreDrawListener(
                new ViewTreeObserver
                    .OnPreDrawListener() { // from class: android.widget.SemExpandableListView.8
                  @Override // android.view.ViewTreeObserver.OnPreDrawListener
                  public boolean onPreDraw() {
                    int offset;
                    int childCount2;
                    boolean z;
                    SemExpandableListView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                    int childCount3 = SemExpandableListView.this.getChildCount();
                    if (childCount3 != 0) {
                      int previousLastGroupPos =
                          SemExpandableListView.this.getAbsoluteFlatPosition(lastGroupIdBefore);
                      SemExpandableListView semExpandableListView = SemExpandableListView.this;
                      semExpandableListView.mTranslationOffset =
                          lastPositionBottomBefore
                              - semExpandableListView.getChildAt(previousLastGroupPos).getBottom();
                      int firstVisiblePos2 = SemExpandableListView.this.getFirstVisiblePosition();
                      boolean allCollapsed = true;
                      int i3 = 0;
                      while (i3 < childCount3) {
                        boolean isHeaderOrFooter =
                            SemExpandableListView.this.isHeaderOrFooterPosition(
                                i3 + firstVisiblePos2);
                        View child = SemExpandableListView.this.getChildAt(i3);
                        long packedPos = SemExpandableListView.this.getExpandableListPosition(i3);
                        ViewInfo oldViewInfo =
                            (ViewInfo) SemExpandableListView.this.mViewSnapshots.get(packedPos);
                        if (oldViewInfo != null) {
                          offset = oldViewInfo.top - child.getTop();
                        } else {
                          offset = SemExpandableListView.this.mTranslationOffset;
                        }
                        int groupId2 = SemExpandableListView.getPackedPositionGroup(packedPos);
                        if (!isHeaderOrFooter && groupId2 <= lastGroupIdBefore) {
                          SemExpandableListView.this.mCollapsingRects[groupId2].setFinishY(
                              child.getBottom());
                        }
                        SemExpandableListView.this.mViewSnapshots.remove(packedPos);
                        if (!isHeaderOrFooter) {
                          boolean[] zArr = expanded;
                          if (groupId2 < zArr.length) {
                            boolean z2 = zArr[groupId2];
                            allCollapsed &= !z2;
                            if (z2) {
                              SemExpandableListView.this.startIndicatorAnimation(child, false, 700);
                            }
                          }
                        }
                        if (offset == 0) {
                          childCount2 = childCount3;
                          z = true;
                        } else {
                          child.setTranslationY(offset);
                          childCount2 = childCount3;
                          z = true;
                          ObjectAnimator translateAnim =
                              ObjectAnimator.ofFloat(child, View.TRANSLATION_Y, 0.0f);
                          animations.add(translateAnim);
                        }
                        i3++;
                        childCount3 = childCount2;
                      }
                      Animator.AnimatorListener listener =
                          new AnimatorListenerAdapter() { // from class:
                                                          // android.widget.SemExpandableListView.8.1
                            @Override // android.animation.AnimatorListenerAdapter,
                                      // android.animation.Animator.AnimatorListener
                            public void onAnimationStart(Animator animation) {
                              SemExpandableListView.this.mAnimationState = 5;
                            }

                            @Override // android.animation.AnimatorListenerAdapter,
                                      // android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animation) {
                              Log.m94d(SemExpandableListView.TAG, "expand animation finished");
                              animationEndRunnable.run();
                              SemExpandableListView.this.resetCollapseAnimationState();
                            }
                          };
                      if (allCollapsed) {
                        listener.onAnimationEnd(null);
                        return false;
                      }
                      ValueAnimator anim = ValueAnimator.ofFloat(0.0f, 1.0f);
                      anim.addUpdateListener(SemExpandableListView.this.mBitmapUpdateListener);
                      animations.add(anim);
                      AnimatorSet set = new AnimatorSet();
                      set.playTogether(animations);
                      set.setDuration(700);
                      set.setInterpolator(SemExpandableListView.EXPAND_COLLAPSE_INTERPOLATOR);
                      set.addListener(listener);
                      set.start();
                      SemExpandableListView.this.mViewSnapshots.clear();
                      return false;
                    }
                    animationEndRunnable.run();
                    SemExpandableListView.this.resetCollapseAnimationState();
                    return true;
                  }
                });
        return;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void startIndicatorAnimation(View child, boolean isExpanding, int duration) {
    int startAngle = this.mRotationAngle;
    if (!isExpanding) {
      startAngle = -startAngle;
    }
    DecoratedItemViewHolder holder = (DecoratedItemViewHolder) child.getTag(DECORATED_VIEW_TAG);
    if (holder == null || holder.indicatorImgView == null) {
      Log.m96e(
          TAG,
          "startIndicatorAnimation() holder or indicatorImgView is null, startAngle=" + startAngle);
      return;
    }
    IndicatorImageView img = holder.indicatorImgView;
    if (this.mIndicatorAnimationType == 1) {
      img.setRotation(startAngle);
      img.animate()
          .rotation(0.0f)
          .setInterpolator(EXPAND_COLLAPSE_INTERPOLATOR)
          .setDuration(duration)
          .withLayer();
    } else {
      img.startIndicatorMorphAimation();
    }
    img.setContentDescription(isExpanding ? this.mDescriptionCollapse : this.mDescriptionExpand);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public ObjectAnimator createViewSnapshotAnimationReverse(
      int translationOffset, ViewInfo viewInfo) {
    Rect newViewBounds = new Rect(viewInfo.left, viewInfo.top, viewInfo.right, viewInfo.bottom);
    Rect oldViewBounds = new Rect(newViewBounds);
    oldViewBounds.offset(0, translationOffset);
    ObjectAnimator animBounds =
        ObjectAnimator.ofObject(
            viewInfo.snapshot,
            "bounds",
            SemAnimatorUtils.BOUNDS_EVALUATOR,
            oldViewBounds,
            newViewBounds);
    return animBounds;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public ObjectAnimator createViewSnapshotAnimation(int translationOffset, ViewInfo viewInfo) {
    Rect oldViewBounds = new Rect(viewInfo.left, viewInfo.top, viewInfo.right, viewInfo.bottom);
    Rect newViewBounds = new Rect(oldViewBounds);
    newViewBounds.offset(0, translationOffset);
    ObjectAnimator animBounds =
        ObjectAnimator.ofObject(
            viewInfo.snapshot,
            "bounds",
            SemAnimatorUtils.BOUNDS_EVALUATOR,
            oldViewBounds,
            newViewBounds);
    return animBounds;
  }

  private void captureViewsPriorAnimation() {
    int childCount = getChildCount();
    int firstVisiblePos = getFirstVisiblePosition();
    for (int i = 0; i < childCount; i++) {
      View child = getChildAt(i);
      long packedPos = getExpandableListPosition(i + firstVisiblePos);
      if (child.getWidth() != 0 && child.getHeight() != 0) {
        ViewInfo viewInfo = new ViewInfo(child);
        if (viewInfo.snapshot != null) {
          this.mViewSnapshots.put(packedPos, viewInfo);
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

  public boolean expandGroup(int groupPos) {
    return expandGroup(groupPos, false);
  }

  public boolean expandGroup(int groupPos, boolean animate) {
    SemExpandableListPosition elGroupPos = SemExpandableListPosition.obtain(2, groupPos, -1, -1);
    SemExpandableListConnector.PositionMetadata pm = this.mConnector.getFlattenedPos(elGroupPos);
    if (pm == null) {
      return false;
    }
    elGroupPos.recycle();
    boolean retValue = this.mConnector.expandGroup(pm);
    OnGroupExpandListener onGroupExpandListener = this.mOnGroupExpandListener;
    if (onGroupExpandListener != null) {
      onGroupExpandListener.onGroupExpand(groupPos);
    }
    if (animate) {
      int groupFlatPos = pm.position.flatListPos;
      int shiftedGroupPosition = getHeaderViewsCount() + groupFlatPos;
      smoothScrollToPosition(
          this.mAdapter.getChildrenCount(groupPos) + shiftedGroupPosition, shiftedGroupPosition);
    }
    pm.recycle();
    return retValue;
  }

  public boolean collapseGroup(int groupPos) {
    boolean retValue = this.mConnector.collapseGroup(groupPos);
    OnGroupCollapseListener onGroupCollapseListener = this.mOnGroupCollapseListener;
    if (onGroupCollapseListener != null) {
      onGroupCollapseListener.onGroupCollapse(groupPos);
    }
    return retValue;
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

  public long getExpandableListPosition(int flatListPosition) {
    if (isHeaderOrFooterPosition(flatListPosition)) {
      return getHeaderFooterPackedPosition(flatListPosition);
    }
    int adjustedPosition = getFlatPositionForConnector(flatListPosition);
    SemExpandableListConnector.PositionMetadata pm =
        this.mConnector.getUnflattenedPos(adjustedPosition);
    long packedPos = pm.position.getPackedPosition();
    pm.recycle();
    return packedPos;
  }

  public int getFlatListPosition(long packedPosition) {
    SemExpandableListPosition elPackedPos =
        SemExpandableListPosition.obtainPosition(packedPosition);
    if (elPackedPos == null) {
      return -1;
    }
    SemExpandableListConnector.PositionMetadata pm = this.mConnector.getFlattenedPos(elPackedPos);
    elPackedPos.recycle();
    if (pm == null) {
      return -1;
    }
    int flatListPosition = pm.position.flatListPos;
    pm.recycle();
    return getAbsoluteFlatPosition(flatListPosition);
  }

  public long getSelectedPosition() {
    int selectedPos = getSelectedItemPosition();
    return getExpandableListPosition(selectedPos);
  }

  public long getSelectedId() {
    long packedPos = getSelectedPosition();
    if (packedPos == 4294967295L) {
      return -1L;
    }
    int groupPos = getPackedPositionGroup(packedPos);
    if (getPackedPositionType(packedPos) == 0) {
      return this.mAdapter.getGroupId(groupPos);
    }
    return this.mAdapter.getChildId(groupPos, getPackedPositionChild(packedPos));
  }

  public void setSelectedGroup(int groupPosition) {
    SemExpandableListPosition elGroupPos =
        SemExpandableListPosition.obtainGroupPosition(groupPosition);
    SemExpandableListConnector.PositionMetadata pm = this.mConnector.getFlattenedPos(elGroupPos);
    if (pm == null) {
      return;
    }
    elGroupPos.recycle();
    int absoluteFlatPosition = getAbsoluteFlatPosition(pm.position.flatListPos);
    super.setSelection(absoluteFlatPosition);
    pm.recycle();
  }

  public boolean setSelectedChild(int groupPosition, int childPosition, boolean shouldExpandGroup) {
    SemExpandableListPosition elChildPos =
        SemExpandableListPosition.obtainChildPosition(groupPosition, childPosition);
    SemExpandableListConnector.PositionMetadata flatChildPos =
        this.mConnector.getFlattenedPos(elChildPos);
    if (flatChildPos == null) {
      if (!shouldExpandGroup) {
        return false;
      }
      expandGroup(groupPosition);
      flatChildPos = this.mConnector.getFlattenedPos(elChildPos);
      if (flatChildPos == null) {
        throw new IllegalStateException("Could not find child");
      }
    }
    int absoluteFlatPosition = getAbsoluteFlatPosition(flatChildPos.position.flatListPos);
    super.setSelection(absoluteFlatPosition);
    elChildPos.recycle();
    flatChildPos.recycle();
    return true;
  }

  public boolean isGroupExpanded(int groupPosition) {
    return this.mConnector.isGroupExpanded(groupPosition);
  }

  public static int getPackedPositionType(long packedPosition) {
    if (packedPosition == 4294967295L) {
      return 2;
    }
    if ((packedPosition & Long.MIN_VALUE) == Long.MIN_VALUE) {
      return 1;
    }
    return 0;
  }

  public static int getPackedPositionGroup(long packedPosition) {
    if ((packedPosition & PACKED_POSITION_FOOTER_VIEW_BASE) == PACKED_POSITION_FOOTER_VIEW_BASE) {
      return -3;
    }
    if ((packedPosition & 9223372032559808512L) == 9223372032559808512L) {
      return -2;
    }
    return (int) ((9223372032559808512L & packedPosition) >> 32);
  }

  public static int getPackedPositionChild(long packedPosition) {
    if (packedPosition != 4294967295L && (packedPosition & Long.MIN_VALUE) == Long.MIN_VALUE) {
      return (int) (4294967295L & packedPosition);
    }
    return -1;
  }

  public static long getPackedPositionForChild(int groupPosition, int childPosition) {
    return ((groupPosition & PACKED_POSITION_INT_MASK_GROUP) << 32)
        | Long.MIN_VALUE
        | (childPosition & (-1));
  }

  public static long getPackedPositionForGroup(int groupPosition) {
    return (groupPosition & PACKED_POSITION_INT_MASK_GROUP) << 32;
  }

  private long getHeaderFooterPackedPosition(int flatListPosition) {
    if (flatListPosition < getHeaderViewsCount()) {
      return 9223372032559808512L | flatListPosition;
    }
    int headerViewPosition = this.mItemCount;
    int footerViewsStart = headerViewPosition - getFooterViewsCount();
    int footerViewPosition = flatListPosition - footerViewsStart;
    return PACKED_POSITION_FOOTER_VIEW_BASE | footerViewPosition;
  }

  @Override // android.widget.AbsListView
  ContextMenu.ContextMenuInfo createContextMenuInfo(View view, int flatListPosition, long id) {
    if (!isHeaderOrFooterPosition(flatListPosition)) {
      int adjustedPosition = getFlatPositionForConnector(flatListPosition);
      SemExpandableListConnector.PositionMetadata pm =
          this.mConnector.getUnflattenedPos(adjustedPosition);
      SemExpandableListPosition pos = pm.position;
      long id2 = getChildOrGroupId(pos);
      long packedPosition = pos.getPackedPosition();
      pm.recycle();
      return new ExpandableListContextMenuInfo(view, packedPosition, id2);
    }
    return new AdapterView.AdapterContextMenuInfo(view, flatListPosition, id);
  }

  private long getChildOrGroupId(SemExpandableListPosition position) {
    if (position.type == 1) {
      return this.mAdapter.getChildId(position.groupPos, position.childPos);
    }
    return this.mAdapter.getGroupId(position.groupPos);
  }

  public void setChildIndicator(Drawable childIndicator) {
    this.mChildIndicator = childIndicator;
  }

  public void setChildIndicatorBounds(int left, int right) {
    this.mChildIndicatorLeft = left;
    this.mChildIndicatorRight = right;
    resolveChildIndicator();
  }

  public void setChildIndicatorBoundsRelative(int start, int end) {
    this.mChildIndicatorStart = start;
    this.mChildIndicatorEnd = end;
    resolveChildIndicator();
  }

  public void setGroupIndicator(Drawable groupIndicator) {
    this.mGroupIndicator = groupIndicator;
    if (this.mIndicatorRight == 0 && groupIndicator != null) {
      this.mIndicatorRight = this.mIndicatorLeft + groupIndicator.getIntrinsicWidth();
    }
  }

  public void setGroupIndicatorAnimationType(int animationType) {
    this.mIndicatorAnimationType = animationType;
  }

  public void setIndicatorBounds(int left, int right) {
    this.mIndicatorLeft = left;
    this.mIndicatorRight = right;
    resolveIndicator();
  }

  public void setIndicatorBoundsRelative(int start, int end) {
    this.mIndicatorStart = start;
    this.mIndicatorEnd = end;
    resolveIndicator();
  }

  public static class ExpandableListContextMenuInfo implements ContextMenu.ContextMenuInfo {

    /* renamed from: id */
    public long f617id;
    public long packedPosition;
    public View targetView;

    public ExpandableListContextMenuInfo(View targetView, long packedPosition, long id) {
      this.targetView = targetView;
      this.packedPosition = packedPosition;
      this.f617id = id;
    }
  }

  static class SavedState extends View.BaseSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR =
        new Parcelable.Creator<
            SavedState>() { // from class: android.widget.SemExpandableListView.SavedState.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public SavedState createFromParcel(Parcel in) {
            return new SavedState(in);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public SavedState[] newArray(int size) {
            return new SavedState[size];
          }
        };
    ArrayList<SemExpandableListConnector.GroupMetadata> expandedGroupMetadataList;

    SavedState(
        Parcelable superState,
        ArrayList<SemExpandableListConnector.GroupMetadata> expandedGroupMetadataList) {
      super(superState);
      this.expandedGroupMetadataList = expandedGroupMetadataList;
    }

    private SavedState(Parcel in) {
      super(in, SemExpandableListConnector.class.getClassLoader());
      ArrayList<SemExpandableListConnector.GroupMetadata> arrayList = new ArrayList<>();
      this.expandedGroupMetadataList = arrayList;
      in.readList(arrayList, SemExpandableListConnector.class.getClassLoader());
    }

    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState,
              // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
      super.writeToParcel(out, flags);
      out.writeList(this.expandedGroupMetadataList);
    }
  }

  @Override // android.widget.AbsListView, android.view.View
  public Parcelable onSaveInstanceState() {
    Parcelable superState = super.onSaveInstanceState();
    SemExpandableListConnector semExpandableListConnector = this.mConnector;
    return new SavedState(
        superState,
        semExpandableListConnector != null
            ? semExpandableListConnector.getExpandedGroupMetadataList()
            : null);
  }

  @Override // android.widget.AbsListView, android.view.View
  public void onRestoreInstanceState(Parcelable state) {
    if (!(state instanceof SavedState)) {
      super.onRestoreInstanceState(state);
      return;
    }
    SavedState ss = (SavedState) state;
    super.onRestoreInstanceState(ss.getSuperState());
    if (this.mConnector != null && ss.expandedGroupMetadataList != null) {
      this.mConnector.setExpandedGroupMetadataList(ss.expandedGroupMetadataList);
    }
  }

  @Override // android.view.View
  public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
    super.onInitializeAccessibilityEvent(event);
    event.setClassName(SemExpandableListView.class.getName());
  }

  @Override // android.view.View
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
    super.onInitializeAccessibilityNodeInfo(info);
    info.setClassName(SemExpandableListView.class.getName());
  }

  @Override // android.widget.ListView
  public void setDivider(Drawable divider) {
    super.setDivider(divider);
    this.mDividerHeight = 0;
    if (divider != null) {
      if (this.mExpListDividerHeight == null) {
        this.mExpListDividerHeight = new int[1];
      }
      this.mExpListDividerHeight[0] = divider.getIntrinsicHeight();
      Log.m96e(TAG, "setDivider() height=" + this.mExpListDividerHeight[0]);
    }
  }

  @Override // android.widget.ListView
  public void setDividerHeight(int height) {
    super.setDividerHeight(height);
    this.mDividerHeight = 0;
    if (this.mExpListDividerHeight == null) {
      this.mExpListDividerHeight = new int[1];
    }
    this.mExpListDividerHeight[0] = height;
    Log.m96e(TAG, "setDividerHeight() height=" + height);
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
      this.mUpdateListener =
          new ValueAnimator
              .AnimatorUpdateListener() { // from class:
                                          // android.widget.SemExpandableListView.IndicatorImageView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
              float val = ((Float) valueAnimator.getAnimatedValue()).floatValue();
              IndicatorImageView.this.mMorphingAnimatedValue = val;
              IndicatorImageView.this.invalidate();
            }
          };
      this.mAnimatorListener =
          new Animator
              .AnimatorListener() { // from class:
                                    // android.widget.SemExpandableListView.IndicatorImageView.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
              if (SemExpandableListView.DEBUGGABLE_LOW) {
                Log.m94d(SemExpandableListView.TAG, "mAnimatorListener : onAnimationCancel");
                IndicatorImageView.this.printAnimationInfo(animation);
              }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
              if (SemExpandableListView.DEBUGGABLE_LOW) {
                Log.m94d(SemExpandableListView.TAG, "mAnimatorListener : onAnimationEnd");
                IndicatorImageView.this.printAnimationInfo(animation);
              }
              IndicatorImageView.this.mMorphingAnimatedValue = 0.0f;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {}

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
              if (SemExpandableListView.DEBUGGABLE_LOW) {
                Log.m94d(SemExpandableListView.TAG, "mAnimatorListener : onAnimationStart");
                IndicatorImageView.this.printAnimationInfo(animation);
              }
            }
          };
      semSetHoverPopupType(1);
    }

    void setIndicatorPos(SemExpandableListPosition position) {
      this.groupPos = position.groupPos;
      this.childPos = position.childPos;
    }

    @Override // android.widget.ImageView, android.view.View
    public int[] onCreateDrawableState(int extraSpace) {
      int[] result = super.onCreateDrawableState(extraSpace + 1);
      if (this.childPos == -1
          && SemExpandableListView.this.mConnector.isGroupExpanded(this.groupPos)) {
        mergeDrawableStates(result, SemExpandableListView.GROUP_EXPANDED_STATE_SET);
      }
      return result;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void drawableStateChanged() {
      super.drawableStateChanged();
    }

    private void printState(int[] drawableState) {
      StringBuilder sb = new StringBuilder();
      for (int state : drawableState) {
        if (state == 0) {
          Log.m96e(SemExpandableListView.TAG, "drawableStateChanged() state=" + state);
        } else {
          sb.append(getResources().getResourceEntryName(state));
          sb.append(", ");
        }
      }
      Log.m98i(
          SemExpandableListView.TAG,
          "drawableStateChanged() gr="
              + this.groupPos
              + ", child="
              + this.childPos
              + ", state="
              + sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void printAnimationInfo(Animator animation) {
      if (animation != null) {
        Log.m94d(SemExpandableListView.TAG, "printAnimationInfo : animation = " + animation);
        if (animation.equals(this.mMorphingAnimationToDown)) {
          Log.m94d(
              SemExpandableListView.TAG,
              "printAnimationInfo : this animation => mMorphingAnimationToDown");
        } else if (animation.equals(this.mMorphingAnimationToUp)) {
          Log.m94d(
              SemExpandableListView.TAG,
              "printAnimationInfo : this animation => mMorphingAnimationToUp");
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
      this.mCenterX = 0.0f + (f3 / 2.0f);
      this.mCenterY = f;
      this.mPath = new Path();
      float morphingEndValue = this.mHeight - (this.mPaddingValue * 2.0f);
      this.mIndicatorArrowHeight = morphingEndValue;
      this.mMorphingAnimationToDown = ValueAnimator.ofFloat(0.0f, morphingEndValue);
      if (SemExpandableListView.DEBUGGABLE_LOW) {
        Log.m94d(
            SemExpandableListView.TAG,
            "IndicatorImageView : initArrow : mMorphingAnimationToDown = "
                + this.mMorphingAnimationToDown);
      }
      this.mMorphingAnimationToDown.setInterpolator(
          AnimationUtils.loadInterpolator(getContext(), 17563654));
      this.mMorphingAnimationToDown.setDuration(300L);
      this.mMorphingAnimationToDown.addUpdateListener(this.mUpdateListener);
      this.mMorphingAnimationToDown.addListener(this.mAnimatorListener);
      float morphingStartValue = this.mHeight - (this.mPaddingValue * 2.0f);
      this.mMorphingAnimationToUp = ValueAnimator.ofFloat(morphingStartValue, 0.0f);
      if (SemExpandableListView.DEBUGGABLE_LOW) {
        Log.m94d(
            SemExpandableListView.TAG,
            "IndicatorImageView : initArrow : mMorphingAnimationToUp = "
                + this.mMorphingAnimationToUp);
      }
      this.mMorphingAnimationToUp.setInterpolator(
          AnimationUtils.loadInterpolator(getContext(), 17563654));
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
          if (!this.mMorphingAnimationToDown.isRunning()
              && !this.mMorphingAnimationToUp.isRunning()) {
            if (SemExpandableListView.this.mConnector.isGroupExpanded(this.groupPos)) {
              if (SemExpandableListView.DEBUGGABLE_LOW) {
                Log.m94d(
                    SemExpandableListView.TAG,
                    "IndicatorImageView : onDraw : group(" + this.groupPos + ") collapse !!");
              }
              this.mMorphingAnimatedValue = 0.0f;
            } else {
              if (SemExpandableListView.DEBUGGABLE_LOW) {
                Log.m94d(
                    SemExpandableListView.TAG,
                    "IndicatorImageView : onDraw : group(" + this.groupPos + ") expand !!");
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
        if ((valueAnimator2 == null || !valueAnimator2.isRunning())
            && SemExpandableListView.this.mConnector != null) {
          if (SemExpandableListView.this.mConnector.isGroupExpanded(this.groupPos)) {
            if (SemExpandableListView.DEBUGGABLE_LOW) {
              Log.m94d(
                  SemExpandableListView.TAG,
                  "IndicatorImageView : startIndicatorMorphAimation : group("
                      + this.groupPos
                      + ") collapse !!");
              Log.m94d(
                  SemExpandableListView.TAG,
                  "IndicatorImageView : startIndicatorMorphAimation : start morphingAnimation to"
                      + " UP");
            }
            ValueAnimator valueAnimator3 = this.mMorphingAnimationToUp;
            if (valueAnimator3 != null) {
              valueAnimator3.start();
              return;
            }
            return;
          }
          if (SemExpandableListView.DEBUGGABLE_LOW) {
            Log.m94d(
                SemExpandableListView.TAG,
                "IndicatorImageView : startIndicatorMorphAimation : group("
                    + this.groupPos
                    + ") expand !!");
            Log.m94d(
                SemExpandableListView.TAG,
                "IndicatorImageView : startIndicatorMorphAimation : start morphingAnimation to"
                    + " DOWN");
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

    private DecoratedItemViewHolder() {}
  }

  private String printArrays(int[] arr) {
    StringBuffer sb = new StringBuffer();
    for (int item : arr) {
      if (item > 0) {
        sb.append(this.mContext.getResources().getResourceEntryName(item));
        sb.append(", ");
      }
    }
    return sb.toString();
  }

  public void setGroupIndicatorRotationAngle(int angle) {
    this.mRotationAngle = angle;
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
      boolean[] expanded = getExpandedState();
      expandAllGroups();
      Runnable animationEndRunnable =
          new Runnable() { // from class: android.widget.SemExpandableListView.10
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
          };
      startExpandAllAnimation(expanded, animationEndRunnable);
    } else if (i == 2) {
      captureViewsPriorAnimation();
      Runnable animationEndRunnable2 =
          new Runnable() { // from class: android.widget.SemExpandableListView.11
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
          };
      boolean[] expanded2 = getExpandedState();
      startCollapseAllAnimation(expanded2, animationEndRunnable2);
      collapseAllGroups();
    }
    this.mExpandCollapseAllState = 0;
  }

  private boolean[] getExpandedState() {
    int lastValidPos = getLastNonFooterPosition();
    long lastPosPackedPos = getExpandableListPosition(lastValidPos);
    int lastGroupId = getPackedPositionGroup(lastPosPackedPos);
    boolean[] expandedState = new boolean[lastGroupId + 1];
    for (int i = 0; i <= lastGroupId; i++) {
      expandedState[i] = this.mConnector.isGroupExpanded(i);
    }
    return expandedState;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public int getLastNonFooterPosition() {
    int lastPos = getLastVisiblePosition();
    int lastValidPos = lastPos;
    int firstPos = getFirstVisiblePosition();
    while (lastValidPos >= firstPos && isHeaderOrFooterPosition(lastValidPos)) {
      lastValidPos--;
    }
    return lastValidPos;
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

  public void setAnimationEnabled(boolean enable) {
    this.mAnimationEnabled = enable;
  }

  public void setExpandingAnimationEnabled(boolean enable) {
    this.mAnimationEnabled = enable;
  }

  public void setIndicatorPaddings(int left, int right) {
    this.mIndicatorPaddingLeft = left;
    this.mIndicatorPaddingRight = right;
  }

  public void setIndicatorGravity(int gravity) {
    this.mIndicatorGravity = gravity;
    Log.m98i(
        TAG,
        "setIndicatorGravity() gravity="
            + gravity
            + ", mIndicatorGravity="
            + this.mIndicatorGravity);
  }

  public void setGroupIndicatorColor(int color) {
    Log.m98i(TAG, "setGroupIndicatorColor() color= " + Integer.toHexString(color));
    this.mGroupIndicatorColor = color;
    Paint paint = this.mGroupIndicatorPaint;
    if (paint != null) {
      paint.setColor(color);
    }
  }

  public View getUnfoldedChildAt(int index) {
    return this.mItemDecorator.unfoldDecoratedView(super.getChildAt(index));
  }

  @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup
  public boolean onInterceptTouchEvent(MotionEvent event) {
    if (this.mBlockTouchEvent) {
      return true;
    }
    return super.onInterceptTouchEvent(event);
  }

  @Override // android.widget.ListView, android.widget.AbsListView, android.view.View
  public boolean onTouchEvent(MotionEvent event) {
    if (this.mBlockTouchEvent) {
      return true;
    }
    return super.onTouchEvent(event);
  }

  @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    SemExpandableListConnector semExpandableListConnector = this.mConnector;
    if (semExpandableListConnector != null) {
      semExpandableListConnector.semRegisterDataSetObserver();
    }
  }

  @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView,
            // android.view.ViewGroup, android.view.View
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    SemExpandableListConnector semExpandableListConnector = this.mConnector;
    if (semExpandableListConnector != null) {
      semExpandableListConnector.semUnregisterDataSetObserver();
    }
  }

  @Override // android.view.View
  protected void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    this.mDescriptionExpand =
        this.mContext
            .getResources()
            .getString(
                C4337R.string.expandablelist_indicator_description,
                this.mContext.getResources().getString(C4337R.string.expandablelist_expand));
    this.mDescriptionCollapse =
        this.mContext
            .getResources()
            .getString(
                C4337R.string.expandablelist_indicator_description,
                this.mContext.getResources().getString(C4337R.string.expandablelist_collapse));
    if (this.mGroupIndicatorPaint != null) {
      int strokeSize =
          (int) ((this.mContext.getResources().getDisplayMetrics().density * 2.0f) + 0.5f);
      this.mGroupIndicatorPaint.setStrokeWidth(strokeSize);
    }
  }

  @Deprecated
  public void setExpandSpeedFactor(float expandSpeedFactor) {}

  @Deprecated
  public void setCollapseSpeedFactor(float collapseSpeedFactor) {}

  @Deprecated
  public void setExpandAllSpeedFactor(float expandAllSpeedFactor) {}

  @Deprecated
  public void setCollapseAllSpeedFactor(float collapseAllSpeedFactor) {}

  @Deprecated
  public boolean scrollTo(
      View v,
      int groupPos,
      int expandingChildCount,
      SemExpandableListConnector.PositionMetadata pos) {
    return false;
  }

  @Deprecated
  public boolean expandCollapseAll(boolean isExpand) {
    return false;
  }
}
