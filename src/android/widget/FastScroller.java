package android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.IntProperty;
import android.util.MathUtils;
import android.util.Property;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.ImageView;
import com.android.internal.R;
import com.samsung.android.wallpaperbackup.GenerateXML;

/* loaded from: classes5.dex */
class FastScroller {
    private static final int DURATION_CROSS_FADE = 50;
    private static final int DURATION_FADE_IN = 150;
    private static final int DURATION_FADE_OUT = 300;
    private static final int DURATION_RESIZE = 100;
    private static final long FADE_TIMEOUT = 1500;
    private static final int MIN_PAGES = 4;
    private static final int OVERLAY_ABOVE_THUMB = 2;
    private static final int OVERLAY_AT_THUMB = 1;
    private static final int OVERLAY_FLOATING = 0;
    private static final int PREVIEW_LEFT = 0;
    private static final int PREVIEW_RIGHT = 1;
    private static final int STATE_DRAGGING = 2;
    private static final int STATE_NONE = 0;
    private static final int STATE_VISIBLE = 1;
    private static final int THUMB_POSITION_INSIDE = 1;
    private static final int THUMB_POSITION_MIDPOINT = 0;
    private boolean mAlwaysShow;
    private AnimatorSet mDecorAnimation;
    private boolean mEnabled;
    private int mFirstVisibleItem;
    private int mHeaderCount;
    private float mInitialTouchY;
    private boolean mLayoutFromRight;
    private final AbsListView mList;
    private Adapter mListAdapter;
    private boolean mLongList;
    private boolean mMatchDragPosition;
    private final int mMinimumTouchTarget;
    private int mOldChildCount;
    private int mOldItemCount;
    private final ViewGroupOverlay mOverlay;
    private int mOverlayPosition;
    private AnimatorSet mPreviewAnimation;
    private final View mPreviewImage;
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
    private int mThumbMinHeight;
    private int mThumbMinWidth;
    private float mThumbOffset;
    private int mThumbPosition;
    private float mThumbRange;
    private Drawable mTrackDrawable;
    private final ImageView mTrackImage;
    private boolean mUpdatingLayout;
    private int mWidth;
    private static final long TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
    private static Property<View, Integer> LEFT = new IntProperty<View>("left") { // from class: android.widget.FastScroller.3
        @Override // android.util.IntProperty
        public void setValue(View view, int i) {
            view.setLeft(i);
        }

        @Override // android.util.Property
        public Integer get(View view) {
            return Integer.valueOf(view.getLeft());
        }
    };
    private static Property<View, Integer> TOP = new IntProperty<View>(GenerateXML.TOP) { // from class: android.widget.FastScroller.4
        @Override // android.util.IntProperty
        public void setValue(View view, int i) {
            view.setTop(i);
        }

        @Override // android.util.Property
        public Integer get(View view) {
            return Integer.valueOf(view.getTop());
        }
    };
    private static Property<View, Integer> RIGHT = new IntProperty<View>("right") { // from class: android.widget.FastScroller.5
        @Override // android.util.IntProperty
        public void setValue(View view, int i) {
            view.setRight(i);
        }

        @Override // android.util.Property
        public Integer get(View view) {
            return Integer.valueOf(view.getRight());
        }
    };
    private static Property<View, Integer> BOTTOM = new IntProperty<View>(GenerateXML.BOTTOM) { // from class: android.widget.FastScroller.6
        @Override // android.util.IntProperty
        public void setValue(View view, int i) {
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
    private final Runnable mDeferHide = new Runnable() { // from class: android.widget.FastScroller.1
        @Override // java.lang.Runnable
        public void run() {
            FastScroller.this.setState(0);
        }
    };
    private final Animator.AnimatorListener mSwitchPrimaryListener = new AnimatorListenerAdapter() { // from class: android.widget.FastScroller.2
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            FastScroller.this.mShowingPrimary = !r0.mShowingPrimary;
        }
    };

    public FastScroller(AbsListView absListView, int i) {
        this.mList = absListView;
        this.mOldItemCount = absListView.getCount();
        this.mOldChildCount = absListView.getChildCount();
        Context context = absListView.getContext();
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mScrollBarStyle = absListView.getScrollBarStyle();
        this.mScrollCompleted = true;
        this.mState = 1;
        this.mMatchDragPosition = context.getApplicationInfo().targetSdkVersion >= 11;
        ImageView imageView = new ImageView(context);
        this.mTrackImage = imageView;
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageView imageView2 = new ImageView(context);
        this.mThumbImage = imageView2;
        imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
        View view = new View(context);
        this.mPreviewImage = view;
        view.setAlpha(0.0f);
        TextView createPreviewTextView = createPreviewTextView(context);
        this.mPrimaryText = createPreviewTextView;
        TextView createPreviewTextView2 = createPreviewTextView(context);
        this.mSecondaryText = createPreviewTextView2;
        this.mMinimumTouchTarget = absListView.getResources().getDimensionPixelSize(R.dimen.fast_scroller_minimum_touch_target);
        setStyle(i);
        ViewGroupOverlay overlay = absListView.getOverlay();
        this.mOverlay = overlay;
        overlay.add(imageView);
        overlay.add(imageView2);
        overlay.add(view);
        overlay.add(createPreviewTextView);
        overlay.add(createPreviewTextView2);
        getSectionsFromIndexer();
        updateLongList(this.mOldChildCount, this.mOldItemCount);
        setScrollbarPosition(absListView.getVerticalScrollbarPosition());
        postAutoHide();
    }

    private void updateAppearance() {
        this.mTrackImage.lambda$setImageURIAsync$0(this.mTrackDrawable);
        Drawable drawable = this.mTrackDrawable;
        int max = drawable != null ? Math.max(0, drawable.getIntrinsicWidth()) : 0;
        this.mThumbImage.lambda$setImageURIAsync$0(this.mThumbDrawable);
        this.mThumbImage.setMinimumWidth(this.mThumbMinWidth);
        this.mThumbImage.setMinimumHeight(this.mThumbMinHeight);
        Drawable drawable2 = this.mThumbDrawable;
        if (drawable2 != null) {
            max = Math.max(max, drawable2.getIntrinsicWidth());
        }
        this.mWidth = Math.max(max, this.mThumbMinWidth);
        int i = this.mTextAppearance;
        if (i != 0) {
            this.mPrimaryText.setTextAppearance(i);
            this.mSecondaryText.setTextAppearance(this.mTextAppearance);
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
        int i2 = this.mPreviewPadding;
        this.mPrimaryText.setIncludeFontPadding(false);
        this.mPrimaryText.setPadding(i2, i2, i2, i2);
        this.mSecondaryText.setIncludeFontPadding(false);
        this.mSecondaryText.setPadding(i2, i2, i2, i2);
        refreshDrawablePressedState();
    }

    public void setStyle(int i) {
        TypedArray obtainStyledAttributes = this.mList.getContext().obtainStyledAttributes(null, R.styleable.FastScroll, 16843767, i);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = obtainStyledAttributes.getIndex(i2);
            switch (index) {
                case 0:
                    this.mTextAppearance = obtainStyledAttributes.getResourceId(index, 0);
                    break;
                case 1:
                    this.mTextSize = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 2:
                    this.mTextColor = obtainStyledAttributes.getColorStateList(index);
                    break;
                case 3:
                    this.mPreviewPadding = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 4:
                    this.mPreviewMinWidth = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 5:
                    this.mPreviewMinHeight = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 6:
                    this.mThumbPosition = obtainStyledAttributes.getInt(index, 0);
                    break;
                case 7:
                    this.mPreviewResId[0] = obtainStyledAttributes.getResourceId(index, 0);
                    break;
                case 8:
                    this.mPreviewResId[1] = obtainStyledAttributes.getResourceId(index, 0);
                    break;
                case 9:
                    this.mOverlayPosition = obtainStyledAttributes.getInt(index, 0);
                    break;
                case 10:
                    this.mThumbDrawable = obtainStyledAttributes.getDrawable(index);
                    break;
                case 11:
                    this.mThumbMinHeight = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 12:
                    this.mThumbMinWidth = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 13:
                    this.mTrackDrawable = obtainStyledAttributes.getDrawable(index);
                    break;
            }
        }
        obtainStyledAttributes.recycle();
        updateAppearance();
    }

    public void remove() {
        this.mOverlay.remove(this.mTrackImage);
        this.mOverlay.remove(this.mThumbImage);
        this.mOverlay.remove(this.mPreviewImage);
        this.mOverlay.remove(this.mPrimaryText);
        this.mOverlay.remove(this.mSecondaryText);
    }

    public void setEnabled(boolean z) {
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

    public void setAlwaysShow(boolean z) {
        if (this.mAlwaysShow != z) {
            this.mAlwaysShow = z;
            onStateDependencyChanged(false);
        }
    }

    public boolean isAlwaysShowEnabled() {
        return this.mAlwaysShow;
    }

    private void onStateDependencyChanged(boolean z) {
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
            updateLayout();
        }
    }

    public void stop() {
        setState(0);
    }

    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v15 */
    public void setScrollbarPosition(int i) {
        if (i == 0) {
            i = this.mList.isLayoutRtl() ? 1 : 2;
        }
        if (this.mScrollbarPosition != i) {
            this.mScrollbarPosition = i;
            ?? r0 = i == 1 ? 0 : 1;
            this.mLayoutFromRight = r0;
            this.mPreviewImage.setBackgroundResource(this.mPreviewResId[r0]);
            int max = Math.max(0, (this.mPreviewMinWidth - this.mPreviewImage.getPaddingLeft()) - this.mPreviewImage.getPaddingRight());
            this.mPrimaryText.setMinimumWidth(max);
            this.mSecondaryText.setMinimumWidth(max);
            int max2 = Math.max(0, (this.mPreviewMinHeight - this.mPreviewImage.getPaddingTop()) - this.mPreviewImage.getPaddingBottom());
            this.mPrimaryText.setMinimumHeight(max2);
            this.mSecondaryText.setMinimumHeight(max2);
            updateLayout();
        }
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
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

    private void updateLongList(int i, int i2) {
        boolean z = i > 0 && i2 / i >= 4;
        if (this.mLongList != z) {
            this.mLongList = z;
            onStateDependencyChanged(false);
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
        if (this.mUpdatingLayout) {
            return;
        }
        this.mUpdatingLayout = true;
        updateContainerRect();
        layoutThumb();
        layoutTrack();
        updateOffsetAndRange();
        Rect rect = this.mTempBounds;
        measurePreview(this.mPrimaryText, rect);
        applyLayout(this.mPrimaryText, rect);
        measurePreview(this.mSecondaryText, rect);
        applyLayout(this.mSecondaryText, rect);
        if (this.mPreviewImage != null) {
            rect.left -= this.mPreviewImage.getPaddingLeft();
            rect.top -= this.mPreviewImage.getPaddingTop();
            rect.right += this.mPreviewImage.getPaddingRight();
            rect.bottom += this.mPreviewImage.getPaddingBottom();
            applyLayout(this.mPreviewImage, rect);
        }
        this.mUpdatingLayout = false;
    }

    private void applyLayout(View view, Rect rect) {
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
        int i3;
        int right;
        int i4;
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
        int width = rect3.width();
        if (view2 != null) {
            if (this.mLayoutFromRight) {
                width = view2.getLeft();
            } else {
                width -= view2.getRight();
            }
        }
        int max = Math.max(0, rect3.height());
        int max2 = Math.max(0, (width - i) - i3);
        view.measure(View.MeasureSpec.makeMeasureSpec(max2, Integer.MIN_VALUE), View.MeasureSpec.makeSafeMeasureSpec(max, 0));
        int min = Math.min(max2, view.getMeasuredWidth());
        if (this.mLayoutFromRight) {
            i4 = (view2 == null ? rect3.right : view2.getLeft()) - i3;
            right = i4 - min;
        } else {
            right = (view2 == null ? rect3.left : view2.getRight()) + i;
            i4 = right + min;
        }
        rect2.set(right, i2, i4, view.getMeasuredHeight() + i2);
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
        int width = rect3.width();
        view.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, (width - i) - i3), Integer.MIN_VALUE), View.MeasureSpec.makeSafeMeasureSpec(Math.max(0, rect3.height()), 0));
        int height = rect3.height();
        int measuredWidth = view.getMeasuredWidth();
        int i4 = (height / 10) + i2 + rect3.top;
        int measuredHeight = view.getMeasuredHeight() + i4;
        int i5 = ((width - measuredWidth) / 2) + rect3.left;
        rect2.set(i5, i4, measuredWidth + i5, measuredHeight);
    }

    private void updateContainerRect() {
        AbsListView absListView = this.mList;
        absListView.resolvePadding();
        Rect rect = this.mContainerRect;
        rect.left = 0;
        rect.top = 0;
        rect.right = absListView.getWidth();
        rect.bottom = absListView.getHeight();
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

    private void layoutThumb() {
        Rect rect = this.mTempBounds;
        measureViewToSide(this.mThumbImage, null, null, rect);
        applyLayout(this.mThumbImage, rect);
    }

    private void layoutTrack() {
        int i;
        int i2;
        ImageView imageView = this.mTrackImage;
        ImageView imageView2 = this.mThumbImage;
        Rect rect = this.mContainerRect;
        imageView.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, rect.width()), Integer.MIN_VALUE), View.MeasureSpec.makeSafeMeasureSpec(Math.max(0, rect.height()), 0));
        if (this.mThumbPosition == 1) {
            i2 = rect.top;
            i = rect.bottom;
        } else {
            int height = imageView2.getHeight() / 2;
            int i3 = rect.top + height;
            i = rect.bottom - height;
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
    public void setState(int i) {
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
            if (transitionPreviewLayout(this.mCurrentSection)) {
                transitionToDragging();
            } else {
                transitionToVisible();
            }
        }
        this.mState = i;
        refreshDrawablePressedState();
    }

    private void refreshDrawablePressedState() {
        boolean z = this.mState == 2;
        this.mThumbImage.setPressed(z);
        this.mTrackImage.setPressed(z);
    }

    private void transitionToHidden() {
        AnimatorSet animatorSet = this.mDecorAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        Animator duration = groupAnimatorOfFloat(View.ALPHA, 0.0f, this.mThumbImage, this.mTrackImage, this.mPreviewImage, this.mPrimaryText, this.mSecondaryText).setDuration(300L);
        Animator duration2 = groupAnimatorOfFloat(View.TRANSLATION_X, this.mLayoutFromRight ? this.mThumbImage.getWidth() : -this.mThumbImage.getWidth(), this.mThumbImage, this.mTrackImage).setDuration(300L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mDecorAnimation = animatorSet2;
        animatorSet2.playTogether(duration, duration2);
        this.mDecorAnimation.start();
        this.mShowingPreview = false;
    }

    private void transitionToVisible() {
        AnimatorSet animatorSet = this.mDecorAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        Animator duration = groupAnimatorOfFloat(View.ALPHA, 1.0f, this.mThumbImage, this.mTrackImage).setDuration(150L);
        Animator duration2 = groupAnimatorOfFloat(View.ALPHA, 0.0f, this.mPreviewImage, this.mPrimaryText, this.mSecondaryText).setDuration(300L);
        Animator duration3 = groupAnimatorOfFloat(View.TRANSLATION_X, 0.0f, this.mThumbImage, this.mTrackImage).setDuration(150L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mDecorAnimation = animatorSet2;
        animatorSet2.playTogether(duration, duration2, duration3);
        this.mDecorAnimation.start();
        this.mShowingPreview = false;
    }

    private void transitionToDragging() {
        AnimatorSet animatorSet = this.mDecorAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        Animator duration = groupAnimatorOfFloat(View.ALPHA, 1.0f, this.mThumbImage, this.mTrackImage, this.mPreviewImage).setDuration(150L);
        Animator duration2 = groupAnimatorOfFloat(View.TRANSLATION_X, 0.0f, this.mThumbImage, this.mTrackImage).setDuration(150L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mDecorAnimation = animatorSet2;
        animatorSet2.playTogether(duration, duration2);
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
        if (i3 - i2 > 0 && this.mState != 2) {
            setThumbPos(getPosFromItemCount(i, i2, i3));
        }
        this.mScrollCompleted = true;
        if (this.mFirstVisibleItem != i) {
            this.mFirstVisibleItem = i;
            if (this.mState != 2) {
                setState(1);
                postAutoHide();
            }
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

    /* JADX WARN: Removed duplicated region for block: B:27:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0066  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void scrollTo(float r14) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.FastScroller.scrollTo(float):void");
    }

    private boolean transitionPreviewLayout(int i) {
        TextView textView;
        TextView textView2;
        Object obj;
        Object[] objArr = this.mSections;
        String obj2 = (objArr == null || i < 0 || i >= objArr.length || (obj = objArr[i]) == null) ? null : obj.toString();
        Rect rect = this.mTempBounds;
        View view = this.mPreviewImage;
        if (this.mShowingPrimary) {
            textView = this.mPrimaryText;
            textView2 = this.mSecondaryText;
        } else {
            textView = this.mSecondaryText;
            textView2 = this.mPrimaryText;
        }
        textView2.lambda$setTextAsync$0(obj2);
        measurePreview(textView2, rect);
        applyLayout(textView2, rect);
        AnimatorSet animatorSet = this.mPreviewAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        Animator duration = animateAlpha(textView2, 1.0f).setDuration(50L);
        Animator duration2 = animateAlpha(textView, 0.0f).setDuration(50L);
        duration2.addListener(this.mSwitchPrimaryListener);
        rect.left -= view.getPaddingLeft();
        rect.top -= view.getPaddingTop();
        rect.right += view.getPaddingRight();
        rect.bottom += view.getPaddingBottom();
        Animator animateBounds = animateBounds(view, rect);
        animateBounds.setDuration(100L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mPreviewAnimation = animatorSet2;
        AnimatorSet.Builder with = animatorSet2.play(duration2).with(duration);
        with.with(animateBounds);
        int width = (view.getWidth() - view.getPaddingLeft()) - view.getPaddingRight();
        int width2 = textView2.getWidth();
        if (width2 > width) {
            textView2.setScaleX(width / width2);
            with.with(animateScaleX(textView2, 1.0f).setDuration(100L));
        } else {
            textView2.setScaleX(1.0f);
        }
        int width3 = textView.getWidth();
        if (width3 > width2) {
            with.with(animateScaleX(textView, width2 / width3).setDuration(100L));
        }
        this.mPreviewAnimation.start();
        return !TextUtils.isEmpty(obj2);
    }

    private void setThumbPos(float f) {
        float f2 = (f * this.mThumbRange) + this.mThumbOffset;
        this.mThumbImage.setTranslationY(f2 - (r0.getHeight() / 2.0f));
        View view = this.mPreviewImage;
        float height = view.getHeight() / 2.0f;
        int i = this.mOverlayPosition;
        if (i != 1) {
            f2 = i != 2 ? 0.0f : f2 - height;
        }
        Rect rect = this.mContainerRect;
        float constrain = MathUtils.constrain(f2, rect.top + height, rect.bottom - height) - height;
        view.setTranslationY(constrain);
        this.mPrimaryText.setTranslationY(constrain);
        this.mSecondaryText.setTranslationY(constrain);
    }

    private float getPosFromMotionEvent(float f) {
        float f2 = this.mThumbRange;
        if (f2 <= 0.0f) {
            return 0.0f;
        }
        return MathUtils.constrain((f - this.mThumbOffset) / f2, 0.0f, 1.0f);
    }

    private float getPosFromItemCount(int i, int i2, int i3) {
        Object[] objArr;
        int i4;
        int height;
        int height2;
        int top;
        SectionIndexer sectionIndexer = this.mSectionIndexer;
        if (sectionIndexer == null || this.mListAdapter == null) {
            getSectionsFromIndexer();
        }
        if (i2 == 0 || i3 == 0) {
            return 0.0f;
        }
        if (sectionIndexer == null || (objArr = this.mSections) == null || objArr.length <= 0 || !this.mMatchDragPosition) {
            if (i2 == i3) {
                return 0.0f;
            }
            return i / (i3 - i2);
        }
        int i5 = this.mHeaderCount;
        int i6 = i - i5;
        if (i6 < 0) {
            return 0.0f;
        }
        int i7 = i3 - i5;
        View childAt = this.mList.getChildAt(0);
        float paddingTop = (childAt == null || childAt.getHeight() == 0) ? 0.0f : (this.mList.getPaddingTop() - childAt.getTop()) / childAt.getHeight();
        int sectionForPosition = sectionIndexer.getSectionForPosition(i6);
        int positionForSection = sectionIndexer.getPositionForSection(sectionForPosition);
        int length = this.mSections.length;
        if (sectionForPosition < length - 1) {
            int i8 = sectionForPosition + 1;
            i4 = (i8 < length ? sectionIndexer.getPositionForSection(i8) : i7 - 1) - positionForSection;
        } else {
            i4 = i7 - positionForSection;
        }
        float f = (sectionForPosition + (i4 != 0 ? ((i6 + paddingTop) - positionForSection) / i4 : 0.0f)) / length;
        if (i6 <= 0 || i6 + i2 != i7) {
            return f;
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
        int i9 = height2 - top;
        return (i9 <= 0 || height <= 0) ? f : f + ((1.0f - f) * (i9 / height));
    }

    private void cancelFling() {
        MotionEvent obtain = MotionEvent.obtain(0L, 0L, 3, 0.0f, 0.0f, 0);
        this.mList.onTouchEvent(obtain);
        obtain.recycle();
    }

    private void cancelPendingDrag() {
        this.mPendingDrag = -1L;
    }

    private void startPendingDrag() {
        this.mPendingDrag = SystemClock.uptimeMillis() + TAP_TIMEOUT;
    }

    private void beginDrag() {
        this.mPendingDrag = -1L;
        setState(2);
        if (this.mListAdapter == null && this.mList != null) {
            getSectionsFromIndexer();
        }
        AbsListView absListView = this.mList;
        if (absListView != null) {
            absListView.requestDisallowInterceptTouchEvent(true);
            this.mList.reportScrollStateChange(1);
        }
        cancelFling();
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0015, code lost:
    
        if (r0 != 3) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            boolean r0 = r6.isEnabled()
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            int r0 = r7.getActionMasked()
            r2 = 1
            if (r0 == 0) goto L4f
            if (r0 == r2) goto L4b
            r2 = 2
            if (r0 == r2) goto L18
            r7 = 3
            if (r0 == r7) goto L4b
            goto L6f
        L18:
            float r0 = r7.getX()
            float r2 = r7.getY()
            boolean r0 = r6.isPointInside(r0, r2)
            if (r0 != 0) goto L2a
            r6.cancelPendingDrag()
            goto L6f
        L2a:
            long r2 = r6.mPendingDrag
            r4 = 0
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 < 0) goto L6f
            long r4 = android.os.SystemClock.uptimeMillis()
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 > 0) goto L6f
            r6.beginDrag()
            float r0 = r6.mInitialTouchY
            float r0 = r6.getPosFromMotionEvent(r0)
            r6.scrollTo(r0)
            boolean r6 = r6.onTouchEvent(r7)
            return r6
        L4b:
            r6.cancelPendingDrag()
            goto L6f
        L4f:
            float r0 = r7.getX()
            float r3 = r7.getY()
            boolean r0 = r6.isPointInside(r0, r3)
            if (r0 == 0) goto L6f
            android.widget.AbsListView r0 = r6.mList
            boolean r0 = r0.isInScrollingContainer()
            if (r0 != 0) goto L66
            return r2
        L66:
            float r7 = r7.getY()
            r6.mInitialTouchY = r7
            r6.startPendingDrag()
        L6f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.FastScroller.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean onInterceptHoverEvent(MotionEvent motionEvent) {
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
        if (!isEnabled()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                if (this.mPendingDrag >= 0) {
                    beginDrag();
                    float posFromMotionEvent = getPosFromMotionEvent(motionEvent.getY());
                    setThumbPos(posFromMotionEvent);
                    scrollTo(posFromMotionEvent);
                }
                if (this.mState == 2) {
                    AbsListView absListView = this.mList;
                    if (absListView != null) {
                        absListView.requestDisallowInterceptTouchEvent(false);
                        this.mList.reportScrollStateChange(0);
                    }
                    setState(1);
                    postAutoHide();
                    return true;
                }
            } else if (actionMasked == 2) {
                if (this.mPendingDrag >= 0 && Math.abs(motionEvent.getY() - this.mInitialTouchY) > this.mScaledTouchSlop) {
                    beginDrag();
                }
                if (this.mState == 2) {
                    float posFromMotionEvent2 = getPosFromMotionEvent(motionEvent.getY());
                    setThumbPos(posFromMotionEvent2);
                    if (this.mScrollCompleted) {
                        scrollTo(posFromMotionEvent2);
                    }
                    return true;
                }
            } else if (actionMasked == 3) {
                cancelPendingDrag();
            }
        } else if (isPointInside(motionEvent.getX(), motionEvent.getY()) && !this.mList.isInScrollingContainer()) {
            beginDrag();
            return true;
        }
        return false;
    }

    private boolean isPointInside(float f, float f2) {
        if (isPointInsideX(f)) {
            return this.mTrackDrawable != null || isPointInsideY(f2);
        }
        return false;
    }

    private boolean isPointInsideX(float f) {
        float translationX = this.mThumbImage.getTranslationX();
        float right = this.mMinimumTouchTarget - ((this.mThumbImage.getRight() + translationX) - (this.mThumbImage.getLeft() + translationX));
        if (right <= 0.0f) {
            right = 0.0f;
        }
        return this.mLayoutFromRight ? f >= ((float) this.mThumbImage.getLeft()) - right : f <= ((float) this.mThumbImage.getRight()) + right;
    }

    private boolean isPointInsideY(float f) {
        float translationY = this.mThumbImage.getTranslationY();
        float top = this.mThumbImage.getTop() + translationY;
        float bottom = this.mThumbImage.getBottom() + translationY;
        float f2 = this.mMinimumTouchTarget - (bottom - top);
        float f3 = f2 > 0.0f ? f2 / 2.0f : 0.0f;
        return f >= top - f3 && f <= bottom + f3;
    }

    private static Animator groupAnimatorOfFloat(Property<View, Float> property, float f, View... viewArr) {
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSet.Builder builder = null;
        for (int length = viewArr.length - 1; length >= 0; length--) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(viewArr[length], property, f);
            if (builder == null) {
                builder = animatorSet.play(ofFloat);
            } else {
                builder.with(ofFloat);
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
}
