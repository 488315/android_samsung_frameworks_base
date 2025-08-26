package android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
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
import android.widget.ImageView;
import com.android.internal.R;
import com.samsung.android.wallpaperbackup.GenerateXML;

/* loaded from: classes5.dex */
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
    private boolean mScrollCompleted;
    private final TextView mSecondaryText;
    private SectionIndexer mSectionIndexer;
    private Object[] mSections;
    private boolean mShowingPreview;
    private boolean mShowingPrimary;
    private int mState;
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
    private static Property<View, Integer> LEFT = new IntProperty<View>("left") { // from class: android.widget.SemFastScroller.3
        @Override // android.util.IntProperty
        public void setValue(View view, int i) throws Resources.NotFoundException {
            view.setLeft(i);
        }

        @Override // android.util.Property
        public Integer get(View view) {
            return Integer.valueOf(view.getLeft());
        }
    };
    private static Property<View, Integer> TOP = new IntProperty<View>(GenerateXML.TOP) { // from class: android.widget.SemFastScroller.4
        @Override // android.util.IntProperty
        public void setValue(View view, int i) throws Resources.NotFoundException {
            view.setTop(i);
        }

        @Override // android.util.Property
        public Integer get(View view) {
            return Integer.valueOf(view.getTop());
        }
    };
    private static Property<View, Integer> RIGHT = new IntProperty<View>("right") { // from class: android.widget.SemFastScroller.5
        @Override // android.util.IntProperty
        public void setValue(View view, int i) throws Resources.NotFoundException {
            view.setRight(i);
        }

        @Override // android.util.Property
        public Integer get(View view) {
            return Integer.valueOf(view.getRight());
        }
    };
    private static Property<View, Integer> BOTTOM = new IntProperty<View>(GenerateXML.BOTTOM) { // from class: android.widget.SemFastScroller.6
        @Override // android.util.IntProperty
        public void setValue(View view, int i) throws Resources.NotFoundException {
            view.setBottom(i);
        }

        @Override // android.util.Property
        public Integer get(View view) {
            return Integer.valueOf(view.getBottom());
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
    private final Runnable mDeferHide = new Runnable() { // from class: android.widget.SemFastScroller.1
        @Override // java.lang.Runnable
        public void run() throws Resources.NotFoundException {
            SemFastScroller.this.setState(0);
        }
    };
    private final Animator.AnimatorListener mSwitchPrimaryListener = new AnimatorListenerAdapter() { // from class: android.widget.SemFastScroller.2
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            SemFastScroller.this.mShowingPrimary = !r0.mShowingPrimary;
        }
    };
    private int mListScrollRange = -1;
    private int mListScrollExtent = -1;

    public SemFastScroller(AbsListView absListView, int i) throws Resources.NotFoundException {
        this.mAdditionalTouchArea = 0.0f;
        this.mList = absListView;
        this.mOldItemCount = absListView.getCount();
        this.mOldChildCount = absListView.getChildCount();
        Context context = absListView.getContext();
        this.mContext = context;
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mScrollBarStyle = absListView.getScrollBarStyle();
        this.mScrollCompleted = true;
        this.mState = 1;
        this.mMatchDragPosition = this.mContext.getApplicationInfo().targetSdkVersion >= 11;
        ImageView imageView = new ImageView(this.mContext);
        this.mTrackImage = imageView;
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageView imageView2 = new ImageView(this.mContext);
        this.mThumbImage = imageView2;
        imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
        View view = new View(this.mContext);
        this.mPreviewImage = view;
        view.setAlpha(0.0f);
        TextView textViewCreatePreviewTextView = createPreviewTextView(this.mContext);
        this.mPrimaryText = textViewCreatePreviewTextView;
        TextView textViewCreatePreviewTextView2 = createPreviewTextView(this.mContext);
        this.mSecondaryText = textViewCreatePreviewTextView2;
        setStyle(i);
        ViewGroupOverlay overlay = absListView.getOverlay();
        this.mOverlay = overlay;
        overlay.add(imageView);
        overlay.add(imageView2);
        overlay.add(view);
        overlay.add(textViewCreatePreviewTextView);
        overlay.add(textViewCreatePreviewTextView2);
        this.mPreviewMarginEnd = this.mContext.getResources().getDimensionPixelOffset(R.dimen.fastscroll_preview_margin_end);
        this.mThumbMarginEnd = this.mContext.getResources().getDimensionPixelOffset(R.dimen.fastscroll_thumb_margin_end);
        this.mAdditionalTouchArea = this.mContext.getResources().getDimension(R.dimen.tw_fluid_scroller_additional_touch_area);
        this.mTrackPadding = this.mContext.getResources().getDimensionPixelOffset(R.dimen.sem_fast_scroller_track_padding);
        this.mAdditionalBottomPadding = this.mContext.getResources().getDimensionPixelOffset(R.dimen.sem_fast_scroller_additional_bottom_padding);
        int i2 = this.mPreviewPadding;
        textViewCreatePreviewTextView.setPadding(i2, 0, i2, 0);
        int i3 = this.mPreviewPadding;
        textViewCreatePreviewTextView2.setPadding(i3, 0, i3, 0);
        getSectionsFromIndexer();
        updateLongList(this.mOldChildCount, this.mOldItemCount);
        setScrollbarPosition(absListView.getVerticalScrollbarPosition());
        postAutoHide();
    }

    private void updateAppearance() throws Resources.NotFoundException {
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(16843827, typedValue, true);
        this.mColorPrimary = this.mContext.getResources().getColor(typedValue.resourceId, null);
        this.mTrackImage.lambda$setImageURIAsync$0(this.mTrackDrawable);
        Drawable drawable = this.mTrackDrawable;
        int iMax = drawable != null ? Math.max(0, drawable.getIntrinsicWidth()) : 0;
        this.mThumbImage.lambda$setImageURIAsync$0(this.mThumbDrawable);
        this.mThumbImage.setMinimumWidth(this.mThumbMinWidth);
        this.mThumbImage.setMinimumHeight(this.mThumbMinHeight);
        Drawable drawable2 = this.mThumbDrawable;
        if (drawable2 != null) {
            iMax = Math.max(iMax, drawable2.getIntrinsicWidth());
        }
        this.mWidth = Math.max(iMax, this.mThumbMinWidth);
        this.mPreviewImage.setMinimumWidth(this.mPreviewMinWidth);
        this.mPreviewImage.setMinimumHeight(this.mPreviewMinHeight);
        int i = this.mTextAppearance;
        if (i != 0) {
            this.mPrimaryText.setTextAppearance(this.mContext, i);
            this.mSecondaryText.setTextAppearance(this.mContext, this.mTextAppearance);
        }
        ColorStateList colorStateList = this.mTextColor;
        if (colorStateList != null) {
            this.mPrimaryText.setTextColor(colorStateList);
            this.mSecondaryText.setTextColor(this.mTextColor);
        }
        float f = this.mTextSize;
        if (f > 0.0f) {
            this.mPrimaryText.setTextSize(0, f);
            this.mSecondaryText.setTextSize(0, this.mTextSize);
        }
        int iMax2 = Math.max(0, this.mPreviewMinHeight);
        this.mPrimaryText.setMinimumWidth(this.mPreviewMinWidth);
        this.mPrimaryText.setMinimumHeight(iMax2);
        this.mPrimaryText.setIncludeFontPadding(false);
        this.mSecondaryText.setMinimumWidth(this.mPreviewMinWidth);
        this.mSecondaryText.setMinimumHeight(iMax2);
        this.mSecondaryText.setIncludeFontPadding(false);
        refreshDrawablePressedState();
    }

    public void setStyle(int i) {
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(null, R.styleable.FastScroll, 16843767, i);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            switch (index) {
                case 0:
                    this.mTextAppearance = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                    break;
                case 1:
                    this.mTextSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 2:
                    this.mTextColor = typedArrayObtainStyledAttributes.getColorStateList(index);
                    break;
                case 3:
                    this.mPreviewPadding = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 4:
                    this.mPreviewMinWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 5:
                    this.mPreviewMinHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 6:
                    this.mThumbPosition = typedArrayObtainStyledAttributes.getInt(index, 0);
                    break;
                case 7:
                    this.mPreviewResId[0] = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                    break;
                case 8:
                    this.mPreviewResId[1] = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                    break;
                case 9:
                    this.mOverlayPosition = typedArrayObtainStyledAttributes.getInt(index, 0);
                    break;
                case 10:
                    this.mThumbDrawable = typedArrayObtainStyledAttributes.getDrawable(index);
                    break;
                case 11:
                    this.mThumbMinHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 12:
                    this.mThumbMinWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 13:
                    this.mTrackDrawable = typedArrayObtainStyledAttributes.getDrawable(index);
                    break;
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        if (this.mThumbDrawable instanceof LayerDrawable) {
            this.mThumbWidthAnimator = new SemFastScrollThumbAnimator(this.mContext, (LayerDrawable) this.mThumbDrawable);
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
        SemFastScrollThumbAnimator semFastScrollThumbAnimator = this.mThumbWidthAnimator;
        if (semFastScrollThumbAnimator != null) {
            semFastScrollThumbAnimator.dispose();
        }
    }

    public void setEnabled(boolean z) {
        Log.d(TAG, "setEnabled() enabled = " + z);
        if (this.mEnabled != z) {
            this.mEnabled = z;
            onStateDependencyChanged(true);
        }
    }

    public boolean isEnabled() {
        if (this.mEnabled) {
            return this.mLongList || this.mAlwaysShow;
        }
        return false;
    }

    public void setAlwaysShow(boolean z) throws Resources.NotFoundException {
        if (this.mAlwaysShow != z) {
            this.mAlwaysShow = z;
            onStateDependencyChanged(false);
        }
    }

    public boolean isAlwaysShowEnabled() {
        return this.mAlwaysShow;
    }

    private void onStateDependencyChanged(boolean z) throws Resources.NotFoundException {
        if (isEnabled()) {
            if (isAlwaysShowEnabled()) {
                setState(1);
            } else if (this.mState == 1) {
                postAutoHide();
            } else if (z) {
                setState(1);
                postAutoHide();
            }
        } else {
            stop();
        }
        this.mList.resolvePadding();
    }

    public void setScrollBarStyle(int i) {
        if (this.mScrollBarStyle != i) {
            this.mScrollBarStyle = i;
            resetScrollDatas();
            updateLayout();
        }
    }

    public void stop() throws Resources.NotFoundException {
        setState(0);
    }

    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v5 */
    public void setScrollbarPosition(int i) {
        if (i == 0) {
            i = this.mList.isLayoutRtl() ? 1 : 2;
        }
        if (this.mScrollbarPosition != i) {
            this.mScrollbarPosition = i;
            ?? r0 = i == 1 ? 0 : 1;
            this.mLayoutFromRight = r0;
            this.mPreviewImage.setBackgroundResource(this.mPreviewResId[r0]);
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

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        resetScrollDatas();
        updateLayout();
    }

    public void onItemCountChanged(int i, int i2) {
        if (this.mOldItemCount == i2 && this.mOldChildCount == i) {
            return;
        }
        this.mOldItemCount = i2;
        this.mOldChildCount = i;
        if (i2 - i > 0 && this.mState != 2) {
            setThumbPos(getPosFromItemCount(this.mList.getFirstVisiblePosition(), i, i2));
        }
        updateLongList(i, i2);
    }

    private void updateLongList(int i, int i2) throws Resources.NotFoundException {
        boolean z = i > 0 && (this.mList.canScrollList(1) || this.mList.canScrollList(-1));
        if (this.mLongList != z) {
            this.mLongList = z;
            onStateDependencyChanged(true);
        }
    }

    private TextView createPreviewTextView(Context context) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -2);
        TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        textView.setGravity(17);
        textView.setAlpha(0.0f);
        textView.setLayoutDirection(this.mList.getLayoutDirection());
        return textView;
    }

    public void updateLayout() {
        int i;
        AbsListView absListView = this.mList;
        int iComputeVerticalScrollRange = absListView.computeVerticalScrollRange();
        int iComputeVerticalScrollExtent = absListView.computeVerticalScrollExtent();
        int i2 = this.mListScrollRange;
        if ((i2 <= 0 || iComputeVerticalScrollRange != i2 || (i = this.mListScrollExtent) <= 0 || iComputeVerticalScrollExtent != i || this.mContainerRect.width() <= 0) && !this.mUpdatingLayout) {
            this.mUpdatingLayout = true;
            this.mListScrollRange = iComputeVerticalScrollRange;
            this.mListScrollExtent = iComputeVerticalScrollExtent;
            updateContainerRect();
            layoutThumb();
            layoutTrack();
            updateOffsetAndRange();
            this.mUpdatingLayout = false;
            Rect rect = this.mTempBounds;
            measurePreview(this.mPrimaryText, rect);
            applyLayout(this.mPrimaryText, rect);
            measurePreview(this.mSecondaryText, rect);
            applyLayout(this.mSecondaryText, rect);
            rect.left -= this.mPreviewImage.getPaddingLeft();
            rect.top -= this.mPreviewImage.getPaddingTop();
            rect.right += this.mPreviewImage.getPaddingRight();
            rect.bottom += this.mPreviewImage.getPaddingBottom();
            applyLayout(this.mPreviewImage, rect);
        }
    }

    private void applyLayout(View view, Rect rect) throws Resources.NotFoundException {
        view.layout(rect.left, rect.top, rect.right, rect.bottom);
        view.setPivotX(this.mLayoutFromRight ? rect.right - rect.left : 0.0f);
    }

    private void measurePreview(View view, Rect rect) {
        Rect rect2 = this.mTempMargins;
        rect2.left = this.mPreviewImage.getPaddingLeft();
        rect2.top = this.mPreviewImage.getPaddingTop();
        rect2.right = this.mPreviewImage.getPaddingRight();
        rect2.bottom = this.mPreviewImage.getPaddingBottom();
        if (this.mOverlayPosition == 0) {
            measureFloating(view, rect2, rect);
        } else {
            measureViewToSide(view, this.mThumbImage, rect2, rect);
        }
    }

    private void measureViewToSide(View view, View view2, Rect rect, Rect rect2) {
        int i;
        int i2;
        int right;
        int left;
        int i3;
        if (this.mLayoutFromRight) {
            if (view2 == null) {
                i3 = this.mThumbMarginEnd;
            } else {
                i3 = this.mPreviewMarginEnd;
            }
            i2 = i3;
            i = 0;
        } else {
            if (view2 == null) {
                i = this.mThumbMarginEnd;
            } else {
                i = this.mPreviewMarginEnd;
            }
            i2 = 0;
        }
        Rect rect3 = this.mContainerRect;
        int iWidth = rect3.width();
        if (view2 != null) {
            if (this.mLayoutFromRight) {
                iWidth = view2.getLeft();
            } else {
                iWidth -= view2.getRight();
            }
        }
        int iMax = Math.max(0, rect3.height());
        int iMax2 = Math.max(0, (iWidth - i) - i2);
        view.measure(View.MeasureSpec.makeMeasureSpec(iMax2, Integer.MIN_VALUE), View.MeasureSpec.makeSafeMeasureSpec(iMax, 0));
        int iMin = Math.min(iMax2, view.getMeasuredWidth());
        if (this.mLayoutFromRight) {
            left = (view2 == null ? rect3.right : view2.getLeft()) - i2;
            right = left - iMin;
        } else {
            right = (view2 == null ? rect3.left : view2.getRight()) + i;
            left = right + iMin;
        }
        rect2.set(right, 0, left, view.getMeasuredHeight());
    }

    private void measureFloating(View view, Rect rect, Rect rect2) {
        int i;
        int i2;
        int i3;
        if (rect == null) {
            i3 = 0;
            i = 0;
            i2 = 0;
        } else {
            i = rect.left;
            i2 = rect.top;
            i3 = rect.right;
        }
        Rect rect3 = this.mContainerRect;
        int iWidth = rect3.width();
        view.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, (iWidth - i) - i3), Integer.MIN_VALUE), View.MeasureSpec.makeSafeMeasureSpec(Math.max(0, rect3.height()), 0));
        int iHeight = rect3.height();
        int measuredWidth = view.getMeasuredWidth();
        int i4 = (iHeight / 10) + i2 + rect3.top;
        int measuredHeight = view.getMeasuredHeight() + i4;
        int i5 = ((iWidth - measuredWidth) / 2) + rect3.left;
        rect2.set(i5, i4, measuredWidth + i5, measuredHeight);
    }

    private void updateContainerRect() {
        AbsListView absListView = this.mList;
        absListView.resolvePadding();
        Rect rect = this.mContainerRect;
        rect.left = 0;
        rect.top = this.mScrollBarTopPadding;
        rect.right = absListView.getWidth();
        rect.bottom = absListView.getHeight() - this.mScrollBarBottomPadding;
        int i = this.mScrollBarStyle;
        if (i == 16777216 || i == 0) {
            rect.left += absListView.getPaddingLeft();
            rect.top += absListView.getPaddingTop();
            rect.right -= absListView.getPaddingRight();
            rect.bottom -= absListView.getPaddingBottom();
            if (i == 16777216) {
                int width = getWidth();
                if (this.mScrollbarPosition == 2) {
                    rect.right += width;
                } else {
                    rect.left -= width;
                }
            }
        }
    }

    private void resetScrollDatas() {
        this.mListScrollRange = -1;
        this.mListScrollExtent = -1;
    }

    private int getThumbLength(int i, int i2, int i3, int i4) {
        int iRound = Math.round((i * i3) / i4);
        return iRound < i2 ? i2 : iRound;
    }

    private void layoutThumb() throws Resources.NotFoundException {
        AbsListView absListView = this.mList;
        Rect rect = this.mTempBounds;
        if (this.mLayoutFromRight) {
            rect.right = this.mContainerRect.width();
            rect.left = rect.right - this.mContext.getResources().getDimensionPixelOffset(R.dimen.sem_fast_scroller_thumb_width);
        } else {
            rect.right = this.mContext.getResources().getDimensionPixelOffset(R.dimen.sem_fast_scroller_thumb_width);
            rect.left = 0;
        }
        rect.top = 0;
        rect.bottom = getThumbLength(absListView.getHeight(), this.mContext.getResources().getDimensionPixelOffset(R.dimen.sem_fast_scroller_thumb_min_height), this.mListScrollExtent, this.mListScrollRange);
        applyLayout(this.mThumbImage, rect);
    }

    private void layoutTrack() throws Resources.NotFoundException {
        int i;
        int i2;
        ImageView imageView = this.mTrackImage;
        ImageView imageView2 = this.mThumbImage;
        Rect rect = this.mContainerRect;
        imageView.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, rect.width()), Integer.MIN_VALUE), View.MeasureSpec.makeSafeMeasureSpec(Math.max(0, rect.height()), 0));
        if (this.mThumbPosition == 1) {
            i2 = rect.top + this.mTrackPadding;
            i = (rect.bottom - this.mTrackPadding) - this.mAdditionalBottomPadding;
        } else {
            int height = imageView2.getHeight() / 2;
            int i3 = rect.top + height + this.mTrackPadding;
            i = ((rect.bottom - height) - this.mTrackPadding) - this.mAdditionalBottomPadding;
            i2 = i3;
        }
        int measuredWidth = imageView.getMeasuredWidth();
        int left = imageView2.getLeft() + ((imageView2.getWidth() - measuredWidth) / 2);
        imageView.layout(left, i2, measuredWidth + left, i);
    }

    private void updateOffsetAndRange() {
        float top;
        float bottom;
        ImageView imageView = this.mTrackImage;
        ImageView imageView2 = this.mThumbImage;
        if (this.mThumbPosition == 1) {
            float height = imageView2.getHeight() / 2.0f;
            top = imageView.getTop() + height;
            bottom = imageView.getBottom() - height;
        } else {
            top = imageView.getTop();
            bottom = imageView.getBottom();
        }
        this.mThumbOffset = top;
        this.mThumbRange = bottom - top;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setState(int i) throws Resources.NotFoundException {
        this.mList.removeCallbacks(this.mDeferHide);
        if (this.mAlwaysShow && i == 0) {
            i = 1;
        }
        if (i == this.mState) {
            return;
        }
        if (i == 0) {
            transitionToHidden();
        } else if (i == 1) {
            transitionToVisible();
        } else if (i == 2) {
            transitionPreviewLayout(this.mCurrentSection);
        }
        SemFastScrollThumbAnimator semFastScrollThumbAnimator = this.mThumbWidthAnimator;
        if (semFastScrollThumbAnimator != null) {
            semFastScrollThumbAnimator.setDragging(i == 2);
        }
        this.mState = i;
        refreshDrawablePressedState();
    }

    private void refreshDrawablePressedState() {
        boolean z = this.mState == 2;
        this.mThumbImage.setPressed(z);
        this.mTrackImage.setPressed(z);
    }

    private void transitionToHidden() throws Resources.NotFoundException {
        int i;
        Log.d(TAG, "transitionToHidden() mState = " + this.mState);
        if (this.mState != 2) {
            this.mList.semSetupGoToTop(0);
        } else {
            this.mList.semAutoHide(1);
        }
        this.mShowingPreview = false;
        this.mCurrentSection = -1;
        AnimatorSet animatorSet = this.mDecorAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
            i = 167;
        } else {
            i = 0;
        }
        Animator duration = groupAnimatorOfFloat(View.ALPHA, 0.0f, this.mThumbImage, this.mTrackImage, this.mPreviewImage, this.mPrimaryText, this.mSecondaryText).setDuration(i);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mDecorAnimation = animatorSet2;
        animatorSet2.playTogether(duration);
        this.mDecorAnimation.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
        this.mDecorAnimation.start();
    }

    private void transitionToVisible() throws Resources.NotFoundException {
        Log.d(TAG, "transitionToVisible()");
        AnimatorSet animatorSet = this.mDecorAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        this.mList.semSetupGoToTop(1);
        Animator duration = groupAnimatorOfFloat(View.ALPHA, 1.0f, this.mThumbImage, this.mTrackImage).setDuration(167L);
        Animator duration2 = groupAnimatorOfFloat(View.ALPHA, 0.0f, this.mPreviewImage, this.mPrimaryText, this.mSecondaryText).setDuration(167L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mDecorAnimation = animatorSet2;
        animatorSet2.playTogether(duration, duration2);
        this.mDecorAnimation.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
        this.mShowingPreview = false;
        this.mDecorAnimation.start();
    }

    private void transitionToDragging() {
        Log.d(TAG, "transitionToDragging()");
        AnimatorSet animatorSet = this.mDecorAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        Animator duration = groupAnimatorOfFloat(View.ALPHA, 1.0f, this.mThumbImage, this.mTrackImage, this.mPreviewImage).setDuration(167L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mDecorAnimation = animatorSet2;
        animatorSet2.playTogether(duration);
        this.mDecorAnimation.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
        this.mDecorAnimation.start();
        this.mShowingPreview = true;
    }

    private void postAutoHide() {
        this.mList.removeCallbacks(this.mDeferHide);
        this.mList.postDelayed(this.mDeferHide, FADE_TIMEOUT);
    }

    public void onScroll(int i, int i2, int i3) {
        if (!isEnabled()) {
            setState(0);
            return;
        }
        if ((this.mList.canScrollList(1) || this.mList.canScrollList(-1)) && this.mState != 2) {
            float f = this.mOldThumbPosition;
            if (f != -1.0f) {
                setThumbPos(f);
                this.mOldThumbPosition = -1.0f;
            } else {
                setThumbPos(getPosFromItemCount(i, i2, i3));
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
        ListAdapter adapter = this.mList.getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
            this.mHeaderCount = headerViewListAdapter.getHeadersCount();
            adapter = headerViewListAdapter.getWrappedAdapter();
        }
        if (adapter instanceof ExpandableListConnector) {
            ExpandableListAdapter adapter2 = ((ExpandableListConnector) adapter).getAdapter();
            if (adapter2 instanceof SectionIndexer) {
                SectionIndexer sectionIndexer = (SectionIndexer) adapter2;
                this.mSectionIndexer = sectionIndexer;
                this.mListAdapter = adapter;
                this.mSections = sectionIndexer.getSections();
                return;
            }
            return;
        }
        if (adapter instanceof SectionIndexer) {
            this.mListAdapter = adapter;
            SectionIndexer sectionIndexer2 = (SectionIndexer) adapter;
            this.mSectionIndexer = sectionIndexer2;
            this.mSections = sectionIndexer2.getSections();
            return;
        }
        this.mListAdapter = adapter;
        this.mSections = null;
    }

    public void onSectionsChanged() {
        this.mListAdapter = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0098  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void scrollTo(float f) throws Resources.NotFoundException {
        int i;
        int i2;
        float f2;
        float f3;
        AbsListView absListView;
        this.mScrollCompleted = false;
        int count = this.mList.getCount();
        Object[] objArr = this.mSections;
        int length = objArr == null ? 0 : objArr.length;
        if (objArr != null && length > 0) {
            float f4 = length;
            int i3 = length - 1;
            int iConstrain = MathUtils.constrain((int) (f * f4), 0, i3);
            int positionForSection = this.mSectionIndexer.getPositionForSection(iConstrain);
            int i4 = iConstrain + 1;
            int positionForSection2 = iConstrain < i3 ? this.mSectionIndexer.getPositionForSection(i4) : count;
            int i5 = iConstrain;
            if (positionForSection2 == positionForSection) {
                int positionForSection3 = positionForSection;
                do {
                    if (i5 <= 0) {
                        i5 = iConstrain;
                    } else {
                        i5--;
                        positionForSection3 = this.mSectionIndexer.getPositionForSection(i5);
                        if (positionForSection3 != positionForSection) {
                        }
                    }
                    positionForSection = positionForSection3;
                    i = i5;
                    break;
                } while (i5 != 0);
                i5 = iConstrain;
                positionForSection = positionForSection3;
                i = 0;
                i2 = iConstrain + 2;
                while (i2 < length && this.mSectionIndexer.getPositionForSection(i2) == positionForSection2) {
                    i2++;
                    i4++;
                }
                f2 = i5 / f4;
                f3 = i4 / f4;
                float f5 = count != 0 ? Float.MAX_VALUE : 0.125f / count;
                if (i5 == iConstrain || f - f2 >= f5) {
                    positionForSection += (int) (((positionForSection2 - positionForSection) * (f - f2)) / (f3 - f2));
                }
                int iConstrain2 = MathUtils.constrain(positionForSection, 0, count - 1);
                absListView = this.mList;
                if (!(absListView instanceof ExpandableListView)) {
                    ExpandableListView expandableListView = (ExpandableListView) absListView;
                    expandableListView.setSelectionFromTop(expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(iConstrain2 + this.mHeaderCount)), 0);
                } else if (absListView instanceof ListView) {
                    ((ListView) absListView).setSelectionFromTop(iConstrain2 + this.mHeaderCount, 0);
                } else {
                    absListView.setSelection(iConstrain2 + this.mHeaderCount);
                }
            } else {
                i = i5;
                i2 = iConstrain + 2;
                while (i2 < length) {
                    i2++;
                    i4++;
                }
                f2 = i5 / f4;
                f3 = i4 / f4;
                if (count != 0) {
                }
                if (i5 == iConstrain) {
                    positionForSection += (int) (((positionForSection2 - positionForSection) * (f - f2)) / (f3 - f2));
                    int iConstrain22 = MathUtils.constrain(positionForSection, 0, count - 1);
                    absListView = this.mList;
                    if (!(absListView instanceof ExpandableListView)) {
                    }
                }
            }
        } else {
            int iConstrain3 = MathUtils.constrain((int) (count * f), 0, count - 1);
            AbsListView absListView2 = this.mList;
            if (absListView2 instanceof ExpandableListView) {
                ExpandableListView expandableListView2 = (ExpandableListView) absListView2;
                expandableListView2.setSelectionFromTop(expandableListView2.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(iConstrain3 + this.mHeaderCount)), 0);
            } else if (absListView2 instanceof ListView) {
                ((ListView) absListView2).setSelectionFromTop(iConstrain3 + this.mHeaderCount, 0);
            } else {
                absListView2.setSelection(iConstrain3 + this.mHeaderCount);
            }
            i = -1;
        }
        if (this.mCurrentSection != i) {
            this.mList.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(26));
        }
        this.mCurrentSection = i;
        boolean zTransitionPreviewLayout = transitionPreviewLayout(i);
        Log.d(TAG, "scrollTo() called transitionPreviewLayout() sectionIndex =" + i + ", position = " + f);
        boolean z = this.mShowingPreview;
        if (!z && zTransitionPreviewLayout) {
            transitionToDragging();
        } else {
            if (!z || zTransitionPreviewLayout) {
                return;
            }
            transitionToVisible();
        }
    }

    private boolean transitionPreviewLayout(int i) throws Resources.NotFoundException {
        TextView textView;
        TextView textView2;
        Object obj;
        Object[] objArr = this.mSections;
        String string = (objArr == null || i < 0 || i >= objArr.length || (obj = objArr[i]) == null) ? null : obj.toString();
        Rect rect = this.mTempBounds;
        View view = this.mPreviewImage;
        if (this.mShowingPrimary) {
            textView = this.mPrimaryText;
            textView2 = this.mSecondaryText;
        } else {
            textView = this.mSecondaryText;
            textView2 = this.mPrimaryText;
        }
        textView2.lambda$setTextAsync$0(string);
        measurePreview(textView2, rect);
        applyLayout(textView2, rect);
        int i2 = this.mState;
        if (i2 == 1) {
            textView.lambda$setTextAsync$0("");
        } else if (i2 == 2 && textView2.getText() == textView.getText()) {
            return !TextUtils.isEmpty(string);
        }
        AnimatorSet animatorSet = this.mPreviewAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        Animator duration = animateAlpha(textView2, 1.0f).setDuration(0L);
        Animator duration2 = animateAlpha(textView, 0.0f).setDuration(0L);
        duration2.addListener(this.mSwitchPrimaryListener);
        rect.left -= view.getPaddingLeft();
        rect.top -= view.getPaddingTop();
        rect.right += view.getPaddingRight();
        rect.bottom += view.getPaddingBottom();
        Animator animatorAnimateBounds = animateBounds(view, rect);
        animatorAnimateBounds.setDuration(100L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mPreviewAnimation = animatorSet2;
        AnimatorSet.Builder builderWith = animatorSet2.play(duration2).with(duration);
        builderWith.with(animatorAnimateBounds);
        int width = (view.getWidth() - view.getPaddingLeft()) - view.getPaddingRight();
        int width2 = textView2.getWidth();
        if (width2 > width) {
            textView2.setScaleX(width / width2);
            builderWith.with(animateScaleX(textView2, 1.0f).setDuration(100L));
        } else {
            textView2.setScaleX(1.0f);
        }
        int width3 = textView.getWidth();
        if (width3 > width2) {
            builderWith.with(animateScaleX(textView, width2 / width3).setDuration(100L));
        }
        this.mPreviewAnimation.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
        this.mPreviewAnimation.start();
        return !TextUtils.isEmpty(string);
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x000c A[PHI: r2
      0x000c: PHI (r2v6 float) = (r2v0 float), (r2v1 float) binds: [B:3:0x000a, B:6:0x0011] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setThumbPos(float f) {
        Rect rect = this.mContainerRect;
        int i = rect.top;
        int i2 = rect.bottom;
        float f2 = 1.0f;
        if (f > 1.0f) {
            f = f2;
        } else {
            f2 = 0.0f;
            if (f < 0.0f) {
            }
        }
        float f3 = (f * this.mThumbRange) + this.mThumbOffset;
        this.mThumbImage.setTranslationY(f3 - (r2.getHeight() / 2.0f));
        View view = this.mPreviewImage;
        float height = view.getHeight() / 2.0f;
        float fConstrain = MathUtils.constrain(f3, i + height, i2 - height) - height;
        view.setTranslationY(fConstrain);
        this.mPrimaryText.setTranslationY(fConstrain);
        this.mSecondaryText.setTranslationY(fConstrain);
    }

    private float getPosFromMotionEvent(float f) {
        float f2 = this.mThumbRange;
        if (f2 <= 0.0f) {
            return 0.0f;
        }
        return MathUtils.constrain((f - this.mThumbOffset) / f2, 0.0f, 1.0f);
    }

    private float getPosFromItemCount(int i, int i2, int i3) {
        float numColumns;
        int height;
        int height2;
        int top;
        Object[] objArr;
        int positionForSection;
        SectionIndexer sectionIndexer = this.mSectionIndexer;
        if (sectionIndexer == null || this.mListAdapter == null) {
            getSectionsFromIndexer();
        }
        if (i2 == 0 || i3 == 0) {
            return 0.0f;
        }
        View childAt = this.mList.getChildAt(0);
        float paddingTop = (childAt == null || childAt.getHeight() == 0) ? 0.0f : (this.mList.getPaddingTop() - childAt.getTop()) / childAt.getHeight();
        if (sectionIndexer != null && (objArr = this.mSections) != null && objArr.length > 0 && this.mMatchDragPosition) {
            int i4 = this.mHeaderCount;
            i -= i4;
            if (i < 0) {
                return 0.0f;
            }
            i3 -= i4;
            int sectionForPosition = sectionIndexer.getSectionForPosition(i);
            int positionForSection2 = sectionIndexer.getPositionForSection(sectionForPosition);
            int length = this.mSections.length;
            if (sectionForPosition < length - 1) {
                int i5 = sectionForPosition + 1;
                positionForSection = (i5 < length ? sectionIndexer.getPositionForSection(i5) : i3 - 1) - positionForSection2;
            } else {
                positionForSection = i3 - positionForSection2;
            }
            numColumns = (sectionForPosition + (positionForSection != 0 ? ((i + paddingTop) - positionForSection2) / positionForSection : 0.0f)) / length;
        } else {
            if (i2 == i3) {
                return 0.0f;
            }
            numColumns = (i + (paddingTop * (this.mList instanceof GridView ? ((GridView) r0).getNumColumns() : 1))) / i3;
        }
        if (i <= 0 || i + i2 != i3) {
            return numColumns;
        }
        View childAt2 = this.mList.getChildAt(i2 - 1);
        int paddingBottom = this.mList.getPaddingBottom();
        if (this.mList.getClipToPadding()) {
            height = childAt2.getHeight();
            height2 = this.mList.getHeight() - paddingBottom;
            top = childAt2.getTop();
        } else {
            height = childAt2.getHeight() + paddingBottom;
            height2 = this.mList.getHeight();
            top = childAt2.getTop();
        }
        int i6 = height2 - top;
        return (i6 <= 0 || height <= 0) ? numColumns : numColumns + ((1.0f - numColumns) * (i6 / height));
    }

    private void cancelFling() throws Resources.NotFoundException {
        MotionEvent motionEventObtain = MotionEvent.obtain(0L, 0L, 3, 0.0f, 0.0f, 0);
        this.mList.onTouchEvent(motionEventObtain);
        motionEventObtain.recycle();
    }

    private void cancelPendingDrag() {
        this.mPendingDrag = -1L;
    }

    private void startPendingDrag() {
        this.mPendingDrag = SystemClock.uptimeMillis() + TAP_TIMEOUT;
    }

    private void beginDrag() throws Resources.NotFoundException {
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        if (!isEnabled()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            Log.d(TAG, "onInterceptTouchEvent() ACTION_DOWN ev.getY() = " + motionEvent.getY());
            if (isPointInside(motionEvent.getX(), motionEvent.getY())) {
                this.mList.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(26));
                if (!this.mList.isInScrollingContainer()) {
                    return true;
                }
                this.mInitialTouchY = motionEvent.getY();
                startPendingDrag();
            }
        } else if (actionMasked == 1) {
            cancelPendingDrag();
        } else if (actionMasked != 2) {
            if (actionMasked == 3) {
            }
        } else if (!isPointInside(motionEvent.getX(), motionEvent.getY())) {
            cancelPendingDrag();
        } else {
            long j = this.mPendingDrag;
            if (j >= 0 && j <= SystemClock.uptimeMillis()) {
                beginDrag();
                float posFromMotionEvent = getPosFromMotionEvent(this.mInitialTouchY);
                this.mOldThumbPosition = posFromMotionEvent;
                scrollTo(posFromMotionEvent);
                Log.d(TAG, "onInterceptTouchEvent() ACTION_MOVE pendingdrag open()");
                return onTouchEvent(motionEvent);
            }
        }
        return false;
    }

    public boolean onInterceptHoverEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        if (!isEnabled()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if ((actionMasked == 9 || actionMasked == 7) && this.mState == 0 && isPointInside(motionEvent.getX(), motionEvent.getY())) {
            setState(1);
            postAutoHide();
        }
        return false;
    }

    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        if (this.mState == 2 || isPointInside(motionEvent.getX(), motionEvent.getY())) {
            return PointerIcon.getSystemIcon(this.mList.getContext(), 1000);
        }
        return null;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        Rect rect = this.mContainerRect;
        int i = rect.top;
        int i2 = rect.bottom;
        ImageView imageView = this.mTrackImage;
        float top = imageView.getTop();
        float bottom = imageView.getBottom();
        this.mScrollY = motionEvent.getY();
        if (!isEnabled()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                if (this.mPendingDrag >= 0) {
                    beginDrag();
                    float posFromMotionEvent = getPosFromMotionEvent(motionEvent.getY());
                    this.mOldThumbPosition = posFromMotionEvent;
                    setThumbPos(posFromMotionEvent);
                    scrollTo(posFromMotionEvent);
                    this.mEffectState = 1;
                    Log.d(TAG, "onTouchEvent() ACTION_UP.. open() called with posY " + motionEvent.getY());
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
            } else if (actionMasked == 2) {
                Log.d(TAG, "onTouchEvent() ACTION_MOVE.. mState= " + this.mState + ", mInitialTouchY=" + this.mInitialTouchY);
                if (this.mPendingDrag >= 0 && Math.abs(motionEvent.getY() - this.mInitialTouchY) > this.mScaledTouchSlop) {
                    beginDrag();
                    float f = this.mScrollY;
                    float f2 = i;
                    if (f > f2 && f < i2) {
                        Log.d(TAG, "onTouchEvent() ACTION_MOVE 1 mScrollY=" + this.mScrollY + ", min=" + top + ", max=" + bottom);
                        float f3 = this.mScrollY;
                        float f4 = f2 + top;
                        if (f3 < f4) {
                            this.mScrollY = f4;
                        } else if (f3 > bottom) {
                            this.mScrollY = bottom;
                        }
                        this.mEffectState = 1;
                    }
                }
                if (this.mState == 2) {
                    float posFromMotionEvent2 = getPosFromMotionEvent(motionEvent.getY());
                    this.mOldThumbPosition = posFromMotionEvent2;
                    setThumbPos(posFromMotionEvent2);
                    if (this.mScrollCompleted) {
                        scrollTo(posFromMotionEvent2);
                    }
                    float f5 = this.mScrollY;
                    float f6 = i;
                    if (f5 > f6 && f5 < i2) {
                        Log.d(TAG, "onTouchEvent() ACTION_MOVE 2 mScrollY=" + this.mScrollY + ", min=" + top + ", max=" + bottom);
                        float f7 = this.mScrollY;
                        float f8 = f6 + top;
                        if (f7 < f8) {
                            this.mScrollY = f8;
                        } else if (f7 > bottom) {
                            this.mScrollY = bottom;
                        }
                        this.mEffectState = 1;
                    }
                    return true;
                }
            } else if (actionMasked == 3) {
                cancelPendingDrag();
                if (this.mState == 2) {
                    setState(0);
                }
                this.mEffectState = 0;
                this.mScrollY = 0.0f;
            }
        } else if (isPointInside(motionEvent.getX(), motionEvent.getY()) && !this.mList.isInScrollingContainer()) {
            beginDrag();
            this.mEffectState = 1;
            Log.d(TAG, "onTouchEvent() ACTION_DOWN.. open() called with posY " + motionEvent.getY());
            return true;
        }
        return false;
    }

    private boolean isPointInside(float f, float f2) {
        return isPointInsideX(f) && isPointInsideY(f2) && this.mState != 0;
    }

    private boolean isPointInsideX(float f) {
        return this.mLayoutFromRight ? f >= ((float) this.mThumbImage.getLeft()) - this.mAdditionalTouchArea : f <= ((float) this.mThumbImage.getRight()) + this.mAdditionalTouchArea;
    }

    private boolean isPointInsideY(float f) {
        float translationY = this.mThumbImage.getTranslationY();
        return f >= ((float) this.mThumbImage.getTop()) + translationY && f <= ((float) this.mThumbImage.getBottom()) + translationY;
    }

    private static Animator groupAnimatorOfFloat(Property<View, Float> property, float f, View... viewArr) {
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSet.Builder builderPlay = null;
        for (int length = viewArr.length - 1; length >= 0; length--) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(viewArr[length], property, f);
            if (builderPlay == null) {
                builderPlay = animatorSet.play(objectAnimatorOfFloat);
            } else {
                builderPlay.with(objectAnimatorOfFloat);
            }
        }
        return animatorSet;
    }

    private static Animator animateScaleX(View view, float f) {
        return ObjectAnimator.ofFloat(view, View.SCALE_X, f);
    }

    private static Animator animateAlpha(View view, float f) {
        return ObjectAnimator.ofFloat(view, View.ALPHA, f);
    }

    private static Animator animateBounds(View view, Rect rect) {
        return ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofInt(LEFT, rect.left), PropertyValuesHolder.ofInt(TOP, rect.top), PropertyValuesHolder.ofInt(RIGHT, rect.right), PropertyValuesHolder.ofInt(BOTTOM, rect.bottom));
    }

    private int getColorWithAlpha(int i, float f) {
        return Color.argb(Math.round(Color.alpha(i) * f), Color.red(i), Color.green(i), Color.blue(i));
    }

    public void semSetScrollBarBottomPadding(int i) {
        this.mScrollBarBottomPadding = i;
        resetScrollDatas();
        updateLayout();
    }

    public void semSetScrollBarTopPadding(int i) {
        this.mScrollBarTopPadding = i;
        resetScrollDatas();
        updateLayout();
    }

    public void semSetUseOpenThemeResources(boolean z) {
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

        SemFastScrollThumbAnimator(Context context, LayerDrawable layerDrawable) throws Resources.NotFoundException {
            this.mBgDrawable = (SemFastScrollerBgDrawable) layerDrawable.findDrawableByLayerId(R.id.thumb_bg);
            float dimension = context.getResources().getDimension(R.dimen.sem_fast_scroller_thumb_min_width);
            this.mMinWidthPx = dimension;
            this.mMaxWidthPx = context.getResources().getDimension(R.dimen.sem_fast_scroller_thumb_max_width);
            int primaryColor = getPrimaryColor(context);
            int alphaComponent = setAlphaComponent(context.getResources().getColor(isLightTheme(context) ? R.color.tw_scrollbar_handle_tint_color_mtrl_light : R.color.tw_scrollbar_handle_tint_color_mtrl_dark), 255);
            this.mDefaultColor = alphaComponent;
            int alphaComponent2 = setAlphaComponent(primaryColor, 153);
            this.mActivatedColor = alphaComponent2;
            this.mBgDrawable.setValue(dimension);
            this.mBgDrawable.setArgb(alphaComponent);
            this.mBgDrawable.invalidateSelf();
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mWidthAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.setDuration(350L);
            valueAnimatorOfFloat.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.SemFastScroller.SemFastScrollThumbAnimator.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SemFastScrollThumbAnimator.this.mBgDrawable.setValue(SemFastScrollThumbAnimator.this.mMinWidthPx + ((SemFastScrollThumbAnimator.this.mMaxWidthPx - SemFastScrollThumbAnimator.this.mMinWidthPx) * ((Float) valueAnimator.getAnimatedValue()).floatValue()));
                    SemFastScrollThumbAnimator.this.mBgDrawable.invalidateSelf();
                }
            });
            ValueAnimator valueAnimatorOfArgb = ValueAnimator.ofArgb(alphaComponent, alphaComponent2);
            this.mColorAnimator = valueAnimatorOfArgb;
            valueAnimatorOfArgb.setDuration(350L);
            valueAnimatorOfArgb.setInterpolator(new PathInterpolator(0.0f, 0.0f, 1.0f, 1.0f));
            valueAnimatorOfArgb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.SemFastScroller.SemFastScrollThumbAnimator.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SemFastScrollThumbAnimator.this.mBgDrawable.setArgb(((Integer) valueAnimator.getAnimatedValue()).intValue());
                    SemFastScrollThumbAnimator.this.mBgDrawable.invalidateSelf();
                }
            });
        }

        public void setDragging(boolean z) {
            if (this.mIsDragging != z) {
                this.mIsDragging = z;
                if (z) {
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

        private int setAlphaComponent(int i, int i2) {
            if (i2 < 0 || i2 > 255) {
                throw new IllegalArgumentException("alpha must be between 0 and 255.");
            }
            return (16777215 & i) | (i2 << 24);
        }

        private int getPrimaryColor(Context context) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(16843827, typedValue, true);
            return context.getResources().getColor(typedValue.resourceId, null);
        }

        private boolean isLightTheme(Context context) {
            TypedValue typedValue = new TypedValue();
            return context.getTheme().resolveAttribute(16844176, typedValue, true) && typedValue.data != 0;
        }
    }
}
