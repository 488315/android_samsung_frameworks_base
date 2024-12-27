package android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.IntProperty;
import android.util.Log;
import android.util.MathUtils;
import android.util.Property;
import android.util.TypedValue;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.animation.PathInterpolator;

import com.android.internal.R;

import com.samsung.android.wallpaperbackup.GenerateXML;

/* loaded from: classes4.dex */
class SemFastScroller {
    private static final int DURATION_CROSS_FADE = 0;
    private static final int DURATION_FADE_IN = 167;
    private static final int DURATION_FADE_OUT = 167;
    private static final int DURATION_RESIZE = 100;
    public static final int EFFECT_STATE_CLOSE = 0;
    public static final int EFFECT_STATE_OPEN = 1;
    private static final long FADE_TIMEOUT = 2500;
    private static final int FASTSCROLL_VIBRATE_INDEX = 26;
    private static final int MIN_PAGES = 1;
    private static final int OVERLAY_ABOVE_THUMB = 2;
    private static final int OVERLAY_AT_THUMB = 1;
    private static final int OVERLAY_FLOATING = 0;
    private static final int PREVIEW_LEFT = 0;
    private static final int PREVIEW_RIGHT = 1;
    private static final int STATE_DRAGGING = 2;
    private static final int STATE_NONE = 0;
    private static final int STATE_VISIBLE = 1;
    private static final String TAG = "SemFastScroller";
    private static final int THUMB_POSITION_INSIDE = 1;
    private static final int THUMB_POSITION_MIDPOINT = 0;
    private int mAdditionalBottomPadding;
    private float mAdditionalTouchArea;
    private boolean mAlwaysShow;
    private Context mContext;
    private AnimatorSet mDecorAnimation;
    private boolean mEnabled;
    private int mHeaderCount;
    private float mInitialTouchY;
    private boolean mLayoutFromRight;
    private final AbsListView mList;
    private Adapter mListAdapter;
    private boolean mLongList;
    private boolean mMatchDragPosition;
    private int mOldChildCount;
    private int mOldItemCount;
    private final ViewGroupOverlay mOverlay;
    private int mOverlayPosition;
    private AnimatorSet mPreviewAnimation;
    private final View mPreviewImage;
    private int mPreviewMarginEnd;
    private int mPreviewMinHeight;
    private int mPreviewMinWidth;
    private int mPreviewPadding;
    private final TextView mPrimaryText;
    private int mScaledTouchSlop;
    private int mScrollBarStyle;
    private final TextView mSecondaryText;
    private SectionIndexer mSectionIndexer;
    private Object[] mSections;
    private boolean mShowingPreview;
    private boolean mShowingPrimary;
    private int mTextAppearance;
    private ColorStateList mTextColor;
    private float mTextSize;
    private Drawable mThumbDrawable;
    private final ImageView mThumbImage;
    private int mThumbMarginEnd;
    private int mThumbMinHeight;
    private int mThumbMinWidth;
    private float mThumbOffset;
    private int mThumbPosition;
    private float mThumbRange;
    private SemFastScrollThumbAnimator mThumbWidthAnimator;
    private Drawable mTrackDrawable;
    private final ImageView mTrackImage;
    private int mTrackPadding;
    private boolean mUpdatingLayout;
    private int mWidth;
    private static final long TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
    private static Property<View, Integer> LEFT =
            new IntProperty<View>("left") { // from class: android.widget.SemFastScroller.3
                @Override // android.util.IntProperty
                public void setValue(View object, int value) {
                    object.setLeft(value);
                }

                @Override // android.util.Property
                public Integer get(View object) {
                    return Integer.valueOf(object.getLeft());
                }
            };
    private static Property<View, Integer> TOP =
            new IntProperty<View>(GenerateXML.TOP) { // from class: android.widget.SemFastScroller.4
                @Override // android.util.IntProperty
                public void setValue(View object, int value) {
                    object.setTop(value);
                }

                @Override // android.util.Property
                public Integer get(View object) {
                    return Integer.valueOf(object.getTop());
                }
            };
    private static Property<View, Integer> RIGHT =
            new IntProperty<View>("right") { // from class: android.widget.SemFastScroller.5
                @Override // android.util.IntProperty
                public void setValue(View object, int value) {
                    object.setRight(value);
                }

                @Override // android.util.Property
                public Integer get(View object) {
                    return Integer.valueOf(object.getRight());
                }
            };
    private static Property<View, Integer> BOTTOM =
            new IntProperty<View>(
                    GenerateXML.BOTTOM) { // from class: android.widget.SemFastScroller.6
                @Override // android.util.IntProperty
                public void setValue(View object, int value) {
                    object.setBottom(value);
                }

                @Override // android.util.Property
                public Integer get(View object) {
                    return Integer.valueOf(object.getBottom());
                }
            };
    private final Rect mTempBounds = new Rect();
    private final Rect mTempMargins = new Rect();
    private final Rect mContainerRect = new Rect();
    private final int[] mPreviewResId = new int[2];
    private int mCurrentSection = -1;
    private int mScrollbarPosition = -1;
    private long mPendingDrag = -1;
    private int mColorPrimary = -1;
    private float mScrollY = 0.0f;
    private int mEffectState = 0;
    private float mOldThumbPosition = -1.0f;
    private int mScrollBarBottomPadding = 0;
    private int mScrollBarTopPadding = 0;
    private final Runnable mDeferHide =
            new Runnable() { // from class: android.widget.SemFastScroller.1
                @Override // java.lang.Runnable
                public void run() {
                    SemFastScroller.this.setState(0);
                }
            };
    private final Animator.AnimatorListener mSwitchPrimaryListener =
            new AnimatorListenerAdapter() { // from class: android.widget.SemFastScroller.2
                @Override // android.animation.AnimatorListenerAdapter,
                          // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    SemFastScroller.this.mShowingPrimary = !SemFastScroller.this.mShowingPrimary;
                }
            };
    private int mListScrollRange = -1;
    private int mListScrollExtent = -1;
    private boolean mScrollCompleted = true;
    private int mState = 1;

    public SemFastScroller(AbsListView listView, int styleResId) {
        this.mAdditionalTouchArea = 0.0f;
        this.mList = listView;
        this.mOldItemCount = listView.getCount();
        this.mOldChildCount = listView.getChildCount();
        this.mContext = listView.getContext();
        this.mScaledTouchSlop = ViewConfiguration.get(this.mContext).getScaledTouchSlop();
        this.mScrollBarStyle = listView.getScrollBarStyle();
        this.mMatchDragPosition = this.mContext.getApplicationInfo().targetSdkVersion >= 11;
        this.mTrackImage = new ImageView(this.mContext);
        this.mTrackImage.setScaleType(ImageView.ScaleType.FIT_XY);
        this.mThumbImage = new ImageView(this.mContext);
        this.mThumbImage.setScaleType(ImageView.ScaleType.FIT_XY);
        this.mPreviewImage = new View(this.mContext);
        this.mPreviewImage.setAlpha(0.0f);
        this.mPrimaryText = createPreviewTextView(this.mContext);
        this.mSecondaryText = createPreviewTextView(this.mContext);
        setStyle(styleResId);
        ViewGroupOverlay overlay = listView.getOverlay();
        this.mOverlay = overlay;
        overlay.add(this.mTrackImage);
        overlay.add(this.mThumbImage);
        overlay.add(this.mPreviewImage);
        overlay.add(this.mPrimaryText);
        overlay.add(this.mSecondaryText);
        this.mPreviewMarginEnd =
                this.mContext
                        .getResources()
                        .getDimensionPixelOffset(R.dimen.fastscroll_preview_margin_end);
        this.mThumbMarginEnd =
                this.mContext
                        .getResources()
                        .getDimensionPixelOffset(R.dimen.fastscroll_thumb_margin_end);
        this.mAdditionalTouchArea =
                this.mContext
                        .getResources()
                        .getDimension(R.dimen.tw_fluid_scroller_additional_touch_area);
        this.mTrackPadding =
                this.mContext
                        .getResources()
                        .getDimensionPixelOffset(R.dimen.sem_fast_scroller_track_padding);
        this.mAdditionalBottomPadding =
                this.mContext
                        .getResources()
                        .getDimensionPixelOffset(
                                R.dimen.sem_fast_scroller_additional_bottom_padding);
        this.mPrimaryText.setPadding(this.mPreviewPadding, 0, this.mPreviewPadding, 0);
        this.mSecondaryText.setPadding(this.mPreviewPadding, 0, this.mPreviewPadding, 0);
        getSectionsFromIndexer();
        updateLongList(this.mOldChildCount, this.mOldItemCount);
        setScrollbarPosition(listView.getVerticalScrollbarPosition());
        postAutoHide();
    }

    private void updateAppearance() {
        TypedValue outValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(16843827, outValue, true);
        this.mColorPrimary = this.mContext.getResources().getColor(outValue.resourceId, null);
        this.mTrackImage.lambda$setImageURIAsync$0(this.mTrackDrawable);
        int width =
                this.mTrackDrawable != null
                        ? Math.max(0, this.mTrackDrawable.getIntrinsicWidth())
                        : 0;
        this.mThumbImage.lambda$setImageURIAsync$0(this.mThumbDrawable);
        this.mThumbImage.setMinimumWidth(this.mThumbMinWidth);
        this.mThumbImage.setMinimumHeight(this.mThumbMinHeight);
        if (this.mThumbDrawable != null) {
            width = Math.max(width, this.mThumbDrawable.getIntrinsicWidth());
        }
        this.mWidth = Math.max(width, this.mThumbMinWidth);
        this.mPreviewImage.setMinimumWidth(this.mPreviewMinWidth);
        this.mPreviewImage.setMinimumHeight(this.mPreviewMinHeight);
        if (this.mTextAppearance != 0) {
            this.mPrimaryText.setTextAppearance(this.mContext, this.mTextAppearance);
            this.mSecondaryText.setTextAppearance(this.mContext, this.mTextAppearance);
        }
        if (this.mTextColor != null) {
            this.mPrimaryText.setTextColor(this.mTextColor);
            this.mSecondaryText.setTextColor(this.mTextColor);
        }
        if (this.mTextSize > 0.0f) {
            this.mPrimaryText.setTextSize(0, this.mTextSize);
            this.mSecondaryText.setTextSize(0, this.mTextSize);
        }
        int textMinSize = Math.max(0, this.mPreviewMinHeight);
        this.mPrimaryText.setMinimumWidth(this.mPreviewMinWidth);
        this.mPrimaryText.setMinimumHeight(textMinSize);
        this.mPrimaryText.setIncludeFontPadding(false);
        this.mSecondaryText.setMinimumWidth(this.mPreviewMinWidth);
        this.mSecondaryText.setMinimumHeight(textMinSize);
        this.mSecondaryText.setIncludeFontPadding(false);
        refreshDrawablePressedState();
    }

    public void setStyle(int resId) {
        TypedArray ta =
                this.mContext.obtainStyledAttributes(null, R.styleable.FastScroll, 16843767, resId);
        int N = ta.getIndexCount();
        for (int i = 0; i < N; i++) {
            int index = ta.getIndex(i);
            switch (index) {
                case 0:
                    this.mTextAppearance = ta.getResourceId(index, 0);
                    break;
                case 1:
                    this.mTextSize = ta.getDimensionPixelSize(index, 0);
                    break;
                case 2:
                    this.mTextColor = ta.getColorStateList(index);
                    break;
                case 3:
                    this.mPreviewPadding = ta.getDimensionPixelSize(index, 0);
                    break;
                case 4:
                    this.mPreviewMinWidth = ta.getDimensionPixelSize(index, 0);
                    break;
                case 5:
                    this.mPreviewMinHeight = ta.getDimensionPixelSize(index, 0);
                    break;
                case 6:
                    this.mThumbPosition = ta.getInt(index, 0);
                    break;
                case 7:
                    this.mPreviewResId[0] = ta.getResourceId(index, 0);
                    break;
                case 8:
                    this.mPreviewResId[1] = ta.getResourceId(index, 0);
                    break;
                case 9:
                    this.mOverlayPosition = ta.getInt(index, 0);
                    break;
                case 10:
                    this.mThumbDrawable = ta.getDrawable(index);
                    break;
                case 11:
                    this.mThumbMinHeight = ta.getDimensionPixelSize(index, 0);
                    break;
                case 12:
                    this.mThumbMinWidth = ta.getDimensionPixelSize(index, 0);
                    break;
                case 13:
                    this.mTrackDrawable = ta.getDrawable(index);
                    break;
            }
        }
        ta.recycle();
        if (this.mThumbDrawable instanceof LayerDrawable) {
            this.mThumbWidthAnimator =
                    new SemFastScrollThumbAnimator(
                            this.mContext, (LayerDrawable) this.mThumbDrawable);
        } else {
            this.mThumbWidthAnimator = null;
        }
        updateAppearance();
    }

    public void remove() {
        this.mOverlay.remove(this.mTrackImage);
        this.mOverlay.remove(this.mThumbImage);
        this.mOverlay.remove(this.mPreviewImage);
        this.mOverlay.remove(this.mPrimaryText);
        this.mOverlay.remove(this.mSecondaryText);
        if (this.mThumbWidthAnimator != null) {
            this.mThumbWidthAnimator.dispose();
        }
    }

    public void setEnabled(boolean enabled) {
        Log.d(TAG, "setEnabled() enabled = " + enabled);
        if (this.mEnabled != enabled) {
            this.mEnabled = enabled;
            onStateDependencyChanged(true);
        }
    }

    public boolean isEnabled() {
        return this.mEnabled && (this.mLongList || this.mAlwaysShow);
    }

    public void setAlwaysShow(boolean alwaysShow) {
        if (this.mAlwaysShow != alwaysShow) {
            this.mAlwaysShow = alwaysShow;
            onStateDependencyChanged(false);
        }
    }

    public boolean isAlwaysShowEnabled() {
        return this.mAlwaysShow;
    }

    private void onStateDependencyChanged(boolean peekIfEnabled) {
        if (isEnabled()) {
            if (isAlwaysShowEnabled()) {
                setState(1);
            } else if (this.mState == 1) {
                postAutoHide();
            } else if (peekIfEnabled) {
                setState(1);
                postAutoHide();
            }
        } else {
            stop();
        }
        this.mList.resolvePadding();
    }

    public void setScrollBarStyle(int style) {
        if (this.mScrollBarStyle != style) {
            this.mScrollBarStyle = style;
            resetScrollDatas();
            updateLayout();
        }
    }

    public void stop() {
        setState(0);
    }

    public void setScrollbarPosition(int i) {
        if (i == 0) {
            i = this.mList.isLayoutRtl() ? 1 : 2;
        }
        if (this.mScrollbarPosition != i) {
            this.mScrollbarPosition = i;
            this.mLayoutFromRight = i != 1;
            this.mPreviewImage.setBackgroundResource(
                    this.mPreviewResId[this.mLayoutFromRight ? 1 : 0]);
            this.mPreviewImage.getBackground().setTintMode(PorterDuff.Mode.MULTIPLY);
            this.mPreviewImage.getBackground().setTint(this.mColorPrimary);
            resetScrollDatas();
            updateLayout();
        }
    }

    public int getWidth() {
        return this.mWidth;
    }

    int getEffectState() {
        return this.mEffectState;
    }

    float getScrollY() {
        return this.mScrollY;
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        resetScrollDatas();
        updateLayout();
    }

    public void onItemCountChanged(int childCount, int itemCount) {
        if (this.mOldItemCount != itemCount || this.mOldChildCount != childCount) {
            this.mOldItemCount = itemCount;
            this.mOldChildCount = childCount;
            boolean hasMoreItems = itemCount - childCount > 0;
            if (hasMoreItems && this.mState != 2) {
                int firstVisibleItem = this.mList.getFirstVisiblePosition();
                setThumbPos(getPosFromItemCount(firstVisibleItem, childCount, itemCount));
            }
            updateLongList(childCount, itemCount);
        }
    }

    private void updateLongList(int childCount, int itemCount) {
        boolean longList =
                childCount > 0 && (this.mList.canScrollList(1) || this.mList.canScrollList(-1));
        if (this.mLongList != longList) {
            this.mLongList = longList;
            onStateDependencyChanged(true);
        }
    }

    private TextView createPreviewTextView(Context context) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-2, -2);
        TextView textView = new TextView(context);
        textView.setLayoutParams(params);
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        textView.setGravity(17);
        textView.setAlpha(0.0f);
        textView.setLayoutDirection(this.mList.getLayoutDirection());
        return textView;
    }

    public void updateLayout() {
        AbsListView list = this.mList;
        int range = list.computeVerticalScrollRange();
        int extent = list.computeVerticalScrollExtent();
        if ((this.mListScrollRange > 0
                        && range == this.mListScrollRange
                        && this.mListScrollExtent > 0
                        && extent == this.mListScrollExtent
                        && this.mContainerRect.width() > 0)
                || this.mUpdatingLayout) {
            return;
        }
        this.mUpdatingLayout = true;
        this.mListScrollRange = range;
        this.mListScrollExtent = extent;
        updateContainerRect();
        layoutThumb();
        layoutTrack();
        updateOffsetAndRange();
        this.mUpdatingLayout = false;
        Rect bounds = this.mTempBounds;
        measurePreview(this.mPrimaryText, bounds);
        applyLayout(this.mPrimaryText, bounds);
        measurePreview(this.mSecondaryText, bounds);
        applyLayout(this.mSecondaryText, bounds);
        bounds.left -= this.mPreviewImage.getPaddingLeft();
        bounds.top -= this.mPreviewImage.getPaddingTop();
        bounds.right += this.mPreviewImage.getPaddingRight();
        bounds.bottom += this.mPreviewImage.getPaddingBottom();
        applyLayout(this.mPreviewImage, bounds);
    }

    private void applyLayout(View view, Rect bounds) {
        view.layout(bounds.left, bounds.top, bounds.right, bounds.bottom);
        view.setPivotX(this.mLayoutFromRight ? bounds.right - bounds.left : 0.0f);
    }

    private void measurePreview(View v, Rect out) {
        Rect margins = this.mTempMargins;
        margins.left = this.mPreviewImage.getPaddingLeft();
        margins.top = this.mPreviewImage.getPaddingTop();
        margins.right = this.mPreviewImage.getPaddingRight();
        margins.bottom = this.mPreviewImage.getPaddingBottom();
        if (this.mOverlayPosition == 0) {
            measureFloating(v, margins, out);
        } else {
            measureViewToSide(v, this.mThumbImage, margins, out);
        }
    }

    private void measureViewToSide(View view, View adjacent, Rect margins, Rect out) {
        int marginLeft;
        int marginRight;
        int maxWidth;
        int left;
        int right;
        if (this.mLayoutFromRight) {
            if (adjacent == null) {
                marginRight = this.mThumbMarginEnd;
                marginLeft = 0;
            } else {
                marginRight = this.mPreviewMarginEnd;
                marginLeft = 0;
            }
        } else if (adjacent == null) {
            marginLeft = this.mThumbMarginEnd;
            marginRight = 0;
        } else {
            marginLeft = this.mPreviewMarginEnd;
            marginRight = 0;
        }
        Rect container = this.mContainerRect;
        int containerWidth = container.width();
        if (adjacent == null) {
            maxWidth = containerWidth;
        } else if (this.mLayoutFromRight) {
            maxWidth = adjacent.getLeft();
        } else {
            int maxWidth2 = adjacent.getRight();
            maxWidth = containerWidth - maxWidth2;
        }
        int adjMaxHeight = Math.max(0, container.height());
        int adjMaxWidth = Math.max(0, (maxWidth - marginLeft) - marginRight);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(adjMaxWidth, Integer.MIN_VALUE);
        int heightMeasureSpec = View.MeasureSpec.makeSafeMeasureSpec(adjMaxHeight, 0);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        int width = Math.min(adjMaxWidth, view.getMeasuredWidth());
        if (this.mLayoutFromRight) {
            right = (adjacent == null ? container.right : adjacent.getLeft()) - marginRight;
            left = right - width;
        } else {
            left = (adjacent == null ? container.left : adjacent.getRight()) + marginLeft;
            right = left + width;
        }
        int bottom = 0 + view.getMeasuredHeight();
        out.set(left, 0, right, bottom);
    }

    private void measureFloating(View preview, Rect margins, Rect out) {
        int marginLeft;
        int marginTop;
        int marginRight;
        if (margins == null) {
            marginLeft = 0;
            marginTop = 0;
            marginRight = 0;
        } else {
            marginLeft = margins.left;
            marginTop = margins.top;
            marginRight = margins.right;
        }
        Rect container = this.mContainerRect;
        int containerWidth = container.width();
        int adjMaxHeight = Math.max(0, container.height());
        int adjMaxWidth = Math.max(0, (containerWidth - marginLeft) - marginRight);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(adjMaxWidth, Integer.MIN_VALUE);
        int heightMeasureSpec = View.MeasureSpec.makeSafeMeasureSpec(adjMaxHeight, 0);
        preview.measure(widthMeasureSpec, heightMeasureSpec);
        int containerHeight = container.height();
        int width = preview.getMeasuredWidth();
        int top = (containerHeight / 10) + marginTop + container.top;
        int bottom = preview.getMeasuredHeight() + top;
        int left = ((containerWidth - width) / 2) + container.left;
        int marginLeft2 = left + width;
        out.set(left, top, marginLeft2, bottom);
    }

    private void updateContainerRect() {
        AbsListView list = this.mList;
        list.resolvePadding();
        Rect container = this.mContainerRect;
        container.left = 0;
        container.top = this.mScrollBarTopPadding;
        container.right = list.getWidth();
        container.bottom = list.getHeight() - this.mScrollBarBottomPadding;
        int scrollbarStyle = this.mScrollBarStyle;
        if (scrollbarStyle == 16777216 || scrollbarStyle == 0) {
            container.left += list.getPaddingLeft();
            container.top += list.getPaddingTop();
            container.right -= list.getPaddingRight();
            container.bottom -= list.getPaddingBottom();
            if (scrollbarStyle == 16777216) {
                int width = getWidth();
                if (this.mScrollbarPosition == 2) {
                    container.right += width;
                } else {
                    container.left -= width;
                }
            }
        }
    }

    private void resetScrollDatas() {
        this.mListScrollRange = -1;
        this.mListScrollExtent = -1;
    }

    private int getThumbLength(int size, int minLength, int extent, int range) {
        int length = Math.round((size * extent) / range);
        if (length < minLength) {
            return minLength;
        }
        return length;
    }

    private void layoutThumb() {
        AbsListView list = this.mList;
        Rect bounds = this.mTempBounds;
        if (this.mLayoutFromRight) {
            bounds.right = this.mContainerRect.width();
            bounds.left =
                    bounds.right
                            - this.mContext
                                    .getResources()
                                    .getDimensionPixelOffset(R.dimen.sem_fast_scroller_thumb_width);
        } else {
            bounds.right =
                    this.mContext
                            .getResources()
                            .getDimensionPixelOffset(R.dimen.sem_fast_scroller_thumb_width);
            bounds.left = 0;
        }
        bounds.top = 0;
        bounds.bottom =
                getThumbLength(
                        list.getHeight(),
                        this.mContext
                                .getResources()
                                .getDimensionPixelOffset(
                                        R.dimen.sem_fast_scroller_thumb_min_height),
                        this.mListScrollExtent,
                        this.mListScrollRange);
        applyLayout(this.mThumbImage, bounds);
    }

    private void layoutTrack() {
        int thumbHalfHeight;
        int top;
        View track = this.mTrackImage;
        View thumb = this.mThumbImage;
        Rect container = this.mContainerRect;
        int maxWidth = Math.max(0, container.width());
        int maxHeight = Math.max(0, container.height());
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(maxWidth, Integer.MIN_VALUE);
        int heightMeasureSpec = View.MeasureSpec.makeSafeMeasureSpec(maxHeight, 0);
        track.measure(widthMeasureSpec, heightMeasureSpec);
        if (this.mThumbPosition == 1) {
            thumbHalfHeight = container.top + this.mTrackPadding;
            top = (container.bottom - this.mTrackPadding) - this.mAdditionalBottomPadding;
        } else {
            int top2 = thumb.getHeight();
            int thumbHalfHeight2 = top2 / 2;
            int top3 = container.top + thumbHalfHeight2 + this.mTrackPadding;
            int i =
                    ((container.bottom - thumbHalfHeight2) - this.mTrackPadding)
                            - this.mAdditionalBottomPadding;
            thumbHalfHeight = top3;
            top = i;
        }
        int trackWidth = track.getMeasuredWidth();
        int left = thumb.getLeft() + ((thumb.getWidth() - trackWidth) / 2);
        int right = left + trackWidth;
        track.layout(left, thumbHalfHeight, right, top);
    }

    private void updateOffsetAndRange() {
        float min;
        float max;
        View trackImage = this.mTrackImage;
        View thumbImage = this.mThumbImage;
        if (this.mThumbPosition == 1) {
            float halfThumbHeight = thumbImage.getHeight() / 2.0f;
            min = trackImage.getTop() + halfThumbHeight;
            max = trackImage.getBottom() - halfThumbHeight;
        } else {
            min = trackImage.getTop();
            max = trackImage.getBottom();
        }
        this.mThumbOffset = min;
        this.mThumbRange = max - min;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setState(int state) {
        this.mList.removeCallbacks(this.mDeferHide);
        if (this.mAlwaysShow && state == 0) {
            state = 1;
        }
        if (state == this.mState) {
            return;
        }
        switch (state) {
            case 0:
                transitionToHidden();
                break;
            case 1:
                transitionToVisible();
                break;
            case 2:
                transitionPreviewLayout(this.mCurrentSection);
                break;
        }
        if (this.mThumbWidthAnimator != null) {
            this.mThumbWidthAnimator.setDragging(state == 2);
        }
        this.mState = state;
        refreshDrawablePressedState();
    }

    private void refreshDrawablePressedState() {
        boolean isPressed = this.mState == 2;
        this.mThumbImage.setPressed(isPressed);
        this.mTrackImage.setPressed(isPressed);
    }

    private void transitionToHidden() {
        Log.d(TAG, "transitionToHidden() mState = " + this.mState);
        if (this.mState != 2) {
            this.mList.semSetupGoToTop(0);
        } else {
            this.mList.semAutoHide(1);
        }
        int duration = 0;
        this.mShowingPreview = false;
        this.mCurrentSection = -1;
        if (this.mDecorAnimation != null) {
            this.mDecorAnimation.cancel();
            duration = 167;
        }
        Animator fadeOut =
                groupAnimatorOfFloat(
                                View.ALPHA,
                                0.0f,
                                this.mThumbImage,
                                this.mTrackImage,
                                this.mPreviewImage,
                                this.mPrimaryText,
                                this.mSecondaryText)
                        .setDuration(duration);
        this.mDecorAnimation = new AnimatorSet();
        this.mDecorAnimation.playTogether(fadeOut);
        this.mDecorAnimation.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
        this.mDecorAnimation.start();
    }

    private void transitionToVisible() {
        Log.d(TAG, "transitionToVisible()");
        if (this.mDecorAnimation != null) {
            this.mDecorAnimation.cancel();
        }
        this.mList.semSetupGoToTop(1);
        Animator fadeIn =
                groupAnimatorOfFloat(View.ALPHA, 1.0f, this.mThumbImage, this.mTrackImage)
                        .setDuration(167L);
        Animator fadeOut =
                groupAnimatorOfFloat(
                                View.ALPHA,
                                0.0f,
                                this.mPreviewImage,
                                this.mPrimaryText,
                                this.mSecondaryText)
                        .setDuration(167L);
        this.mDecorAnimation = new AnimatorSet();
        this.mDecorAnimation.playTogether(fadeIn, fadeOut);
        this.mDecorAnimation.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
        this.mShowingPreview = false;
        this.mDecorAnimation.start();
    }

    private void transitionToDragging() {
        Log.d(TAG, "transitionToDragging()");
        if (this.mDecorAnimation != null) {
            this.mDecorAnimation.cancel();
        }
        Animator fadeIn =
                groupAnimatorOfFloat(
                                View.ALPHA,
                                1.0f,
                                this.mThumbImage,
                                this.mTrackImage,
                                this.mPreviewImage)
                        .setDuration(167L);
        this.mDecorAnimation = new AnimatorSet();
        this.mDecorAnimation.playTogether(fadeIn);
        this.mDecorAnimation.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
        this.mDecorAnimation.start();
        this.mShowingPreview = true;
    }

    private void postAutoHide() {
        this.mList.removeCallbacks(this.mDeferHide);
        this.mList.postDelayed(this.mDeferHide, FADE_TIMEOUT);
    }

    public void onScroll(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!isEnabled()) {
            setState(0);
            return;
        }
        if ((this.mList.canScrollList(1) || this.mList.canScrollList(-1)) && this.mState != 2) {
            if (this.mOldThumbPosition != -1.0f) {
                setThumbPos(this.mOldThumbPosition);
                this.mOldThumbPosition = -1.0f;
            } else {
                setThumbPos(
                        getPosFromItemCount(firstVisibleItem, visibleItemCount, totalItemCount));
            }
        }
        this.mScrollCompleted = true;
        if (this.mState != 2) {
            setState(1);
            postAutoHide();
        }
    }

    private void getSectionsFromIndexer() {
        this.mSectionIndexer = null;
        Adapter adapter = this.mList.getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            this.mHeaderCount = ((HeaderViewListAdapter) adapter).getHeadersCount();
            adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
        }
        if (adapter instanceof ExpandableListConnector) {
            ExpandableListAdapter expAdapter = ((ExpandableListConnector) adapter).getAdapter();
            if (expAdapter instanceof SectionIndexer) {
                this.mSectionIndexer = (SectionIndexer) expAdapter;
                this.mListAdapter = adapter;
                this.mSections = this.mSectionIndexer.getSections();
                return;
            }
            return;
        }
        if (adapter instanceof SectionIndexer) {
            this.mListAdapter = adapter;
            this.mSectionIndexer = (SectionIndexer) adapter;
            this.mSections = this.mSectionIndexer.getSections();
        } else {
            this.mListAdapter = adapter;
            this.mSections = null;
        }
    }

    public void onSectionsChanged() {
        this.mListAdapter = null;
    }

    private void scrollTo(float position) {
        int sectionIndex;
        int targetIndex;
        this.mScrollCompleted = false;
        int count = this.mList.getCount();
        Object[] sections = this.mSections;
        int sectionCount = sections == null ? 0 : sections.length;
        if (sections != null && sectionCount > 0) {
            int exactSection =
                    MathUtils.constrain((int) (sectionCount * position), 0, sectionCount - 1);
            int targetSection = exactSection;
            int targetIndex2 = this.mSectionIndexer.getPositionForSection(targetSection);
            sectionIndex = targetSection;
            int nextIndex = count;
            int prevIndex = targetIndex2;
            int prevSection = targetSection;
            int nextSection = targetSection + 1;
            if (targetSection < sectionCount - 1) {
                nextIndex = this.mSectionIndexer.getPositionForSection(targetSection + 1);
            }
            if (nextIndex == targetIndex2) {
                while (true) {
                    if (targetSection <= 0) {
                        break;
                    }
                    targetSection--;
                    prevIndex = this.mSectionIndexer.getPositionForSection(targetSection);
                    if (prevIndex != targetIndex2) {
                        prevSection = targetSection;
                        sectionIndex = targetSection;
                        break;
                    } else if (targetSection == 0) {
                        sectionIndex = 0;
                        break;
                    }
                }
            }
            int nextNextSection = nextSection + 1;
            while (nextNextSection < sectionCount
                    && this.mSectionIndexer.getPositionForSection(nextNextSection) == nextIndex) {
                nextNextSection++;
                nextSection++;
            }
            float prevPosition = prevSection / sectionCount;
            float nextPosition = nextSection / sectionCount;
            float snapThreshold = count == 0 ? Float.MAX_VALUE : 0.125f / count;
            if (prevSection == exactSection && position - prevPosition < snapThreshold) {
                targetIndex = prevIndex;
            } else {
                targetIndex =
                        ((int)
                                        (((nextIndex - prevIndex) * (position - prevPosition))
                                                / (nextPosition - prevPosition)))
                                + prevIndex;
            }
            int targetIndex3 = MathUtils.constrain(targetIndex, 0, count - 1);
            if (!(this.mList instanceof ExpandableListView)) {
                if (this.mList instanceof ListView) {
                    ((ListView) this.mList)
                            .setSelectionFromTop(this.mHeaderCount + targetIndex3, 0);
                } else {
                    this.mList.setSelection(this.mHeaderCount + targetIndex3);
                }
            } else {
                ExpandableListView expList = (ExpandableListView) this.mList;
                expList.setSelectionFromTop(
                        expList.getFlatListPosition(
                                ExpandableListView.getPackedPositionForGroup(
                                        this.mHeaderCount + targetIndex3)),
                        0);
            }
        } else {
            int index = MathUtils.constrain((int) (count * position), 0, count - 1);
            if (!(this.mList instanceof ExpandableListView)) {
                if (this.mList instanceof ListView) {
                    ((ListView) this.mList).setSelectionFromTop(this.mHeaderCount + index, 0);
                } else {
                    this.mList.setSelection(this.mHeaderCount + index);
                }
            } else {
                ExpandableListView expList2 = (ExpandableListView) this.mList;
                expList2.setSelectionFromTop(
                        expList2.getFlatListPosition(
                                ExpandableListView.getPackedPositionForGroup(
                                        this.mHeaderCount + index)),
                        0);
            }
            sectionIndex = -1;
        }
        if (this.mCurrentSection != sectionIndex) {
            this.mList.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(26));
        }
        this.mCurrentSection = sectionIndex;
        boolean hasPreview = transitionPreviewLayout(sectionIndex);
        Log.d(
                TAG,
                "scrollTo() called transitionPreviewLayout() sectionIndex ="
                        + sectionIndex
                        + ", position = "
                        + position);
        if (!this.mShowingPreview && hasPreview) {
            transitionToDragging();
        } else if (this.mShowingPreview && !hasPreview) {
            transitionToVisible();
        }
    }

    private boolean transitionPreviewLayout(int sectionIndex) {
        TextView showing;
        TextView target;
        AnimatorSet.Builder builder;
        Object section;
        Object[] sections = this.mSections;
        String text = null;
        if (sections != null
                && sectionIndex >= 0
                && sectionIndex < sections.length
                && (section = sections[sectionIndex]) != null) {
            text = section.toString();
        }
        Rect bounds = this.mTempBounds;
        View preview = this.mPreviewImage;
        if (this.mShowingPrimary) {
            showing = this.mPrimaryText;
            target = this.mSecondaryText;
        } else {
            showing = this.mSecondaryText;
            target = this.mPrimaryText;
        }
        target.lambda$setTextAsync$0(text);
        measurePreview(target, bounds);
        applyLayout(target, bounds);
        if (this.mState == 1) {
            showing.lambda$setTextAsync$0("");
        } else if (this.mState == 2 && target.getText() == showing.getText()) {
            return !TextUtils.isEmpty(text);
        }
        if (this.mPreviewAnimation != null) {
            this.mPreviewAnimation.cancel();
        }
        Animator showTarget = animateAlpha(target, 1.0f).setDuration(0L);
        Animator hideShowing = animateAlpha(showing, 0.0f).setDuration(0L);
        hideShowing.addListener(this.mSwitchPrimaryListener);
        bounds.left -= preview.getPaddingLeft();
        bounds.top -= preview.getPaddingTop();
        bounds.right += preview.getPaddingRight();
        bounds.bottom += preview.getPaddingBottom();
        Animator resizePreview = animateBounds(preview, bounds);
        resizePreview.setDuration(100L);
        this.mPreviewAnimation = new AnimatorSet();
        AnimatorSet.Builder builder2 = this.mPreviewAnimation.play(hideShowing).with(showTarget);
        builder2.with(resizePreview);
        int previewWidth =
                (preview.getWidth() - preview.getPaddingLeft()) - preview.getPaddingRight();
        int targetWidth = target.getWidth();
        if (targetWidth > previewWidth) {
            target.setScaleX(previewWidth / targetWidth);
            Animator scaleAnim = animateScaleX(target, 1.0f).setDuration(100L);
            builder = builder2;
            builder.with(scaleAnim);
        } else {
            builder = builder2;
            target.setScaleX(1.0f);
        }
        int showingWidth = showing.getWidth();
        if (showingWidth > targetWidth) {
            float scale = targetWidth / showingWidth;
            Animator scaleAnim2 = animateScaleX(showing, scale).setDuration(100L);
            builder.with(scaleAnim2);
        }
        this.mPreviewAnimation.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
        this.mPreviewAnimation.start();
        return !TextUtils.isEmpty(text);
    }

    private void setThumbPos(float position) {
        Rect container = this.mContainerRect;
        int top = container.top;
        int bottom = container.bottom;
        if (position > 1.0f) {
            position = 1.0f;
        } else if (position < 0.0f) {
            position = 0.0f;
        }
        float thumbMiddle = (this.mThumbRange * position) + this.mThumbOffset;
        this.mThumbImage.setTranslationY(thumbMiddle - (this.mThumbImage.getHeight() / 2.0f));
        View previewImage = this.mPreviewImage;
        float previewHalfHeight = previewImage.getHeight() / 2.0f;
        float minP = top + previewHalfHeight;
        float maxP = bottom - previewHalfHeight;
        float previewMiddle = MathUtils.constrain(thumbMiddle, minP, maxP);
        float previewTop = previewMiddle - previewHalfHeight;
        previewImage.setTranslationY(previewTop);
        this.mPrimaryText.setTranslationY(previewTop);
        this.mSecondaryText.setTranslationY(previewTop);
    }

    private float getPosFromMotionEvent(float y) {
        if (this.mThumbRange <= 0.0f) {
            return 0.0f;
        }
        return MathUtils.constrain((y - this.mThumbOffset) / this.mThumbRange, 0.0f, 1.0f);
    }

    private float getPosFromItemCount(
            int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        float incrementalPos;
        float result;
        int maxSize;
        int currentVisibleSize;
        int nextSectionPos;
        float posWithinSection;
        int nextSectionPos2;
        SectionIndexer sectionIndexer = this.mSectionIndexer;
        if (sectionIndexer == null || this.mListAdapter == null) {
            getSectionsFromIndexer();
        }
        if (visibleItemCount == 0 || totalItemCount == 0) {
            return 0.0f;
        }
        boolean hasSections = false;
        View child = this.mList.getChildAt(0);
        if (child == null || child.getHeight() == 0) {
            incrementalPos = 0.0f;
        } else {
            incrementalPos = (this.mList.getPaddingTop() - child.getTop()) / child.getHeight();
        }
        if (sectionIndexer != null && this.mSections != null && this.mSections.length > 0) {
            hasSections = true;
        }
        if (!hasSections || !this.mMatchDragPosition) {
            if (visibleItemCount == totalItemCount) {
                return 0.0f;
            }
            int span = 1;
            if (this.mList instanceof GridView) {
                span = ((GridView) this.mList).getNumColumns();
            }
            result = (firstVisibleItem + (span * incrementalPos)) / totalItemCount;
        } else {
            firstVisibleItem -= this.mHeaderCount;
            if (firstVisibleItem < 0) {
                return 0.0f;
            }
            totalItemCount -= this.mHeaderCount;
            int section = sectionIndexer.getSectionForPosition(firstVisibleItem);
            int sectionPos = sectionIndexer.getPositionForSection(section);
            int sectionCount = this.mSections.length;
            if (section < sectionCount - 1) {
                if (section + 1 < sectionCount) {
                    nextSectionPos2 = sectionIndexer.getPositionForSection(section + 1);
                } else {
                    nextSectionPos2 = totalItemCount - 1;
                }
                nextSectionPos = nextSectionPos2 - sectionPos;
            } else {
                nextSectionPos = totalItemCount - sectionPos;
            }
            if (nextSectionPos == 0) {
                posWithinSection = 0.0f;
            } else {
                float posWithinSection2 = firstVisibleItem;
                posWithinSection =
                        ((posWithinSection2 + incrementalPos) - sectionPos) / nextSectionPos;
            }
            result = (section + posWithinSection) / sectionCount;
        }
        if (firstVisibleItem > 0 && firstVisibleItem + visibleItemCount == totalItemCount) {
            View lastChild = this.mList.getChildAt(visibleItemCount - 1);
            int bottomPadding = this.mList.getPaddingBottom();
            if (this.mList.getClipToPadding()) {
                maxSize = lastChild.getHeight();
                currentVisibleSize = (this.mList.getHeight() - bottomPadding) - lastChild.getTop();
            } else {
                int maxSize2 = lastChild.getHeight();
                maxSize = maxSize2 + bottomPadding;
                currentVisibleSize = this.mList.getHeight() - lastChild.getTop();
            }
            if (currentVisibleSize > 0 && maxSize > 0) {
                return result + ((1.0f - result) * (currentVisibleSize / maxSize));
            }
            return result;
        }
        return result;
    }

    private void cancelFling() {
        MotionEvent cancelFling = MotionEvent.obtain(0L, 0L, 3, 0.0f, 0.0f, 0);
        this.mList.onTouchEvent(cancelFling);
        cancelFling.recycle();
    }

    private void cancelPendingDrag() {
        this.mPendingDrag = -1L;
    }

    private void startPendingDrag() {
        this.mPendingDrag = SystemClock.uptimeMillis() + TAP_TIMEOUT;
    }

    private void beginDrag() {
        Log.d(TAG, "beginDrag() !!!");
        this.mPendingDrag = -1L;
        if (this.mListAdapter == null) {
            getSectionsFromIndexer();
        }
        this.mList.requestDisallowInterceptTouchEvent(true);
        this.mList.reportScrollStateChange(1);
        cancelFling();
        setState(2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isEnabled()) {
            return false;
        }
        switch (ev.getActionMasked()) {
            case 0:
                Log.d(TAG, "onInterceptTouchEvent() ACTION_DOWN ev.getY() = " + ev.getY());
                if (isPointInside(ev.getX(), ev.getY())) {
                    this.mList.performHapticFeedback(
                            HapticFeedbackConstants.semGetVibrationIndex(26));
                    if (!this.mList.isInScrollingContainer()) {
                        return true;
                    }
                    this.mInitialTouchY = ev.getY();
                    startPendingDrag();
                }
                return false;
            case 1:
            case 3:
                cancelPendingDrag();
                return false;
            case 2:
                if (!isPointInside(ev.getX(), ev.getY())) {
                    cancelPendingDrag();
                } else if (this.mPendingDrag >= 0
                        && this.mPendingDrag <= SystemClock.uptimeMillis()) {
                    beginDrag();
                    float pos = getPosFromMotionEvent(this.mInitialTouchY);
                    this.mOldThumbPosition = pos;
                    scrollTo(pos);
                    Log.d(TAG, "onInterceptTouchEvent() ACTION_MOVE pendingdrag open()");
                    return onTouchEvent(ev);
                }
                return false;
            default:
                return false;
        }
    }

    public boolean onInterceptHoverEvent(MotionEvent ev) {
        if (!isEnabled()) {
            return false;
        }
        int actionMasked = ev.getActionMasked();
        if ((actionMasked == 9 || actionMasked == 7)
                && this.mState == 0
                && isPointInside(ev.getX(), ev.getY())) {
            setState(1);
            postAutoHide();
        }
        return false;
    }

    public PointerIcon onResolvePointerIcon(MotionEvent event, int pointerIndex) {
        if (this.mState == 2 || isPointInside(event.getX(), event.getY())) {
            return PointerIcon.getSystemIcon(this.mList.getContext(), 1000);
        }
        return null;
    }

    public boolean onTouchEvent(MotionEvent me) {
        Rect container = this.mContainerRect;
        int top = container.top;
        int bottom = container.bottom;
        View trackImage = this.mTrackImage;
        float min = trackImage.getTop();
        float max = trackImage.getBottom();
        this.mScrollY = me.getY();
        if (!isEnabled()) {
            return false;
        }
        switch (me.getActionMasked()) {
            case 0:
                if (isPointInside(me.getX(), me.getY()) && !this.mList.isInScrollingContainer()) {
                    beginDrag();
                    this.mEffectState = 1;
                    Log.d(TAG, "onTouchEvent() ACTION_DOWN.. open() called with posY " + me.getY());
                    return true;
                }
                return false;
            case 1:
                if (this.mPendingDrag >= 0) {
                    beginDrag();
                    float pos = getPosFromMotionEvent(me.getY());
                    this.mOldThumbPosition = pos;
                    setThumbPos(pos);
                    scrollTo(pos);
                    this.mEffectState = 1;
                    Log.d(TAG, "onTouchEvent() ACTION_UP.. open() called with posY " + me.getY());
                }
                if (this.mState == 2) {
                    this.mList.requestDisallowInterceptTouchEvent(false);
                    this.mList.reportScrollStateChange(0);
                    setState(1);
                    postAutoHide();
                    this.mEffectState = 0;
                    this.mScrollY = 0.0f;
                    return true;
                }
                return false;
            case 2:
                Log.d(
                        TAG,
                        "onTouchEvent() ACTION_MOVE.. mState= "
                                + this.mState
                                + ", mInitialTouchY="
                                + this.mInitialTouchY);
                if (this.mPendingDrag >= 0
                        && Math.abs(me.getY() - this.mInitialTouchY) > this.mScaledTouchSlop) {
                    beginDrag();
                    if (this.mScrollY > top && this.mScrollY < bottom) {
                        Log.d(
                                TAG,
                                "onTouchEvent() ACTION_MOVE 1 mScrollY="
                                        + this.mScrollY
                                        + ", min="
                                        + min
                                        + ", max="
                                        + max);
                        if (this.mScrollY < top + min) {
                            this.mScrollY = top + min;
                        } else if (this.mScrollY > max) {
                            this.mScrollY = max;
                        }
                        this.mEffectState = 1;
                    }
                }
                if (this.mState == 2) {
                    float pos2 = getPosFromMotionEvent(me.getY());
                    this.mOldThumbPosition = pos2;
                    setThumbPos(pos2);
                    if (this.mScrollCompleted) {
                        scrollTo(pos2);
                    }
                    if (this.mScrollY > top && this.mScrollY < bottom) {
                        Log.d(
                                TAG,
                                "onTouchEvent() ACTION_MOVE 2 mScrollY="
                                        + this.mScrollY
                                        + ", min="
                                        + min
                                        + ", max="
                                        + max);
                        if (this.mScrollY < top + min) {
                            this.mScrollY = top + min;
                        } else if (this.mScrollY > max) {
                            this.mScrollY = max;
                        }
                        this.mEffectState = 1;
                    }
                    return true;
                }
                return false;
            case 3:
                cancelPendingDrag();
                if (this.mState == 2) {
                    setState(0);
                }
                this.mEffectState = 0;
                this.mScrollY = 0.0f;
                return false;
            default:
                return false;
        }
    }

    private boolean isPointInside(float x, float y) {
        return isPointInsideX(x) && isPointInsideY(y) && this.mState != 0;
    }

    private boolean isPointInsideX(float x) {
        return this.mLayoutFromRight
                ? x >= ((float) this.mThumbImage.getLeft()) - this.mAdditionalTouchArea
                : x <= ((float) this.mThumbImage.getRight()) + this.mAdditionalTouchArea;
    }

    private boolean isPointInsideY(float y) {
        float offset = this.mThumbImage.getTranslationY();
        float top = this.mThumbImage.getTop() + offset;
        float bottom = this.mThumbImage.getBottom() + offset;
        return y >= top && y <= bottom;
    }

    private static Animator groupAnimatorOfFloat(
            Property<View, Float> property, float value, View... views) {
        AnimatorSet animSet = new AnimatorSet();
        AnimatorSet.Builder builder = null;
        for (int i = views.length - 1; i >= 0; i--) {
            Animator anim = ObjectAnimator.ofFloat(views[i], property, value);
            if (builder == null) {
                builder = animSet.play(anim);
            } else {
                builder.with(anim);
            }
        }
        return animSet;
    }

    private static Animator animateScaleX(View v, float target) {
        return ObjectAnimator.ofFloat(v, View.SCALE_X, target);
    }

    private static Animator animateAlpha(View v, float alpha) {
        return ObjectAnimator.ofFloat(v, View.ALPHA, alpha);
    }

    private static Animator animateBounds(View v, Rect bounds) {
        PropertyValuesHolder left = PropertyValuesHolder.ofInt(LEFT, bounds.left);
        PropertyValuesHolder top = PropertyValuesHolder.ofInt(TOP, bounds.top);
        PropertyValuesHolder right = PropertyValuesHolder.ofInt(RIGHT, bounds.right);
        PropertyValuesHolder bottom = PropertyValuesHolder.ofInt(BOTTOM, bounds.bottom);
        return ObjectAnimator.ofPropertyValuesHolder(v, left, top, right, bottom);
    }

    private int getColorWithAlpha(int color, float ratio) {
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        int newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }

    public void semSetScrollBarBottomPadding(int scrollBarBottomPadding) {
        this.mScrollBarBottomPadding = scrollBarBottomPadding;
        resetScrollDatas();
        updateLayout();
    }

    public void semSetScrollBarTopPadding(int scrollBarTopPadding) {
        this.mScrollBarTopPadding = scrollBarTopPadding;
        resetScrollDatas();
        updateLayout();
    }

    public void semSetUseOpenThemeResources(boolean useOpenThemeResources) {
        updateAppearance();
    }

    private static class SemFastScrollThumbAnimator {
        private static final float DEFAULT_SCROLL_BAR_VALUE = 0.0f;
        private static final float FAST_SCROLL_BAR_VALUE = 1.0f;
        private final int mActivatedColor;
        private SemFastScrollerBgDrawable mBgDrawable;
        private final ValueAnimator mColorAnimator;
        private final int mDefaultColor;
        private boolean mIsDragging = false;
        private final float mMaxWidthPx;
        private final float mMinWidthPx;
        private final ValueAnimator mWidthAnimator;

        SemFastScrollThumbAnimator(Context context, LayerDrawable drawable) {
            this.mBgDrawable =
                    (SemFastScrollerBgDrawable) drawable.findDrawableByLayerId(R.id.thumb_bg);
            this.mMinWidthPx =
                    context.getResources().getDimension(R.dimen.sem_fast_scroller_thumb_min_width);
            this.mMaxWidthPx =
                    context.getResources().getDimension(R.dimen.sem_fast_scroller_thumb_max_width);
            int colorPrimary = getPrimaryColor(context);
            boolean isLightTheme = isLightTheme(context);
            this.mDefaultColor =
                    setAlphaComponent(
                            context.getResources()
                                    .getColor(
                                            isLightTheme
                                                    ? R.color
                                                            .tw_scrollbar_handle_tint_color_mtrl_light
                                                    : R.color
                                                            .tw_scrollbar_handle_tint_color_mtrl_dark),
                            255);
            this.mActivatedColor = setAlphaComponent(colorPrimary, 153);
            this.mBgDrawable.setValue(this.mMinWidthPx);
            this.mBgDrawable.setArgb(this.mDefaultColor);
            this.mBgDrawable.invalidateSelf();
            this.mWidthAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mWidthAnimator.setDuration(350L);
            this.mWidthAnimator.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
            this.mWidthAnimator.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // android.widget.SemFastScroller.SemFastScrollThumbAnimator.1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value =
                                    SemFastScrollThumbAnimator.this.mMinWidthPx
                                            + ((SemFastScrollThumbAnimator.this.mMaxWidthPx
                                                            - SemFastScrollThumbAnimator.this
                                                                    .mMinWidthPx)
                                                    * ((Float) animation.getAnimatedValue())
                                                            .floatValue());
                            SemFastScrollThumbAnimator.this.mBgDrawable.setValue(value);
                            SemFastScrollThumbAnimator.this.mBgDrawable.invalidateSelf();
                        }
                    });
            this.mColorAnimator = ValueAnimator.ofArgb(this.mDefaultColor, this.mActivatedColor);
            this.mColorAnimator.setDuration(350L);
            this.mColorAnimator.setInterpolator(new PathInterpolator(0.0f, 0.0f, 1.0f, 1.0f));
            this.mColorAnimator.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // android.widget.SemFastScroller.SemFastScrollThumbAnimator.2
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator animation) {
                            SemFastScrollThumbAnimator.this.mBgDrawable.setArgb(
                                    ((Integer) animation.getAnimatedValue()).intValue());
                            SemFastScrollThumbAnimator.this.mBgDrawable.invalidateSelf();
                        }
                    });
        }

        public void setDragging(boolean dragging) {
            if (this.mIsDragging != dragging) {
                this.mIsDragging = dragging;
                if (dragging) {
                    this.mWidthAnimator.setFloatValues(0.0f, 1.0f);
                    this.mColorAnimator.setIntValues(this.mDefaultColor, this.mActivatedColor);
                } else {
                    this.mWidthAnimator.setFloatValues(1.0f, 0.0f);
                    this.mColorAnimator.setIntValues(this.mActivatedColor, this.mDefaultColor);
                }
                this.mWidthAnimator.start();
                this.mColorAnimator.start();
            }
        }

        public void dispose() {
            this.mWidthAnimator.removeAllUpdateListeners();
            this.mWidthAnimator.cancel();
            this.mColorAnimator.removeAllUpdateListeners();
            this.mColorAnimator.cancel();
        }

        private int setAlphaComponent(int color, int alpha) {
            if (alpha < 0 || alpha > 255) {
                throw new IllegalArgumentException("alpha must be between 0 and 255.");
            }
            return (16777215 & color) | (alpha << 24);
        }

        private int getPrimaryColor(Context context) {
            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(16843827, outValue, true);
            return context.getResources().getColor(outValue.resourceId, null);
        }

        private boolean isLightTheme(Context context) {
            TypedValue value = new TypedValue();
            return context.getTheme().resolveAttribute(16844176, value, true) && value.data != 0;
        }
    }
}
