package com.samsung.android.widget;

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
import android.p009os.SystemClock;
import android.text.TextUtils;
import android.util.IntProperty;
import android.util.MathUtils;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.SemHorizontalAbsListView;
import android.widget.SemHorizontalListView;
import android.widget.TextView;
import com.android.internal.C4337R;
import com.samsung.android.wallpaperbackup.GenerateXML;

/* loaded from: classes6.dex */
public class SemHorizontalFastScroller {
    private static final int DURATION_CROSS_FADE = 50;
    private static final int DURATION_FADE_IN = 150;
    private static final int DURATION_FADE_OUT = 300;
    private static final int DURATION_RESIZE = 100;
    private static final long FADE_TIMEOUT = 1500;
    private static final int MIN_PAGES = 4;
    private static final int OVERLAY_ABOVE_THUMB = 2;
    private static final int OVERLAY_AT_THUMB = 1;
    private static final int OVERLAY_FLOATING = 0;
    private static final int PREVIEW_BOTTOM = 1;
    private static final int PREVIEW_TOP = 0;
    private static final int STATE_DRAGGING = 2;
    private static final int STATE_NONE = 0;
    private static final int STATE_VISIBLE = 1;
    private boolean mAlwaysShow;
    private AnimatorSet mDecorAnimation;
    private boolean mEnabled;
    private int mFirstVisibleItem;
    private int mHeaderCount;
    private int mHeight;
    private float mInitialTouchX;
    private boolean mLayoutFromBottom;
    private final SemHorizontalAbsListView mList;
    private Adapter mListAdapter;
    private boolean mLongList;
    private boolean mMatchDragPosition;
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
    private Drawable mTrackDrawable;
    private final ImageView mTrackImage;
    private boolean mUpdatingLayout;
    private static final long TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
    private static Property<View, Integer> LEFT = new IntProperty<View>("left") { // from class: com.samsung.android.widget.SemHorizontalFastScroller.3
        @Override // android.util.IntProperty
        public void setValue(View object, int value) {
            object.setLeft(value);
        }

        @Override // android.util.Property
        public Integer get(View object) {
            return Integer.valueOf(object.getLeft());
        }
    };
    private static Property<View, Integer> TOP = new IntProperty<View>(GenerateXML.TOP) { // from class: com.samsung.android.widget.SemHorizontalFastScroller.4
        @Override // android.util.IntProperty
        public void setValue(View object, int value) {
            object.setTop(value);
        }

        @Override // android.util.Property
        public Integer get(View object) {
            return Integer.valueOf(object.getTop());
        }
    };
    private static Property<View, Integer> RIGHT = new IntProperty<View>("right") { // from class: com.samsung.android.widget.SemHorizontalFastScroller.5
        @Override // android.util.IntProperty
        public void setValue(View object, int value) {
            object.setRight(value);
        }

        @Override // android.util.Property
        public Integer get(View object) {
            return Integer.valueOf(object.getRight());
        }
    };
    private static Property<View, Integer> BOTTOM = new IntProperty<View>(GenerateXML.BOTTOM) { // from class: com.samsung.android.widget.SemHorizontalFastScroller.6
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
    private final Runnable mDeferHide = new Runnable() { // from class: com.samsung.android.widget.SemHorizontalFastScroller.1
        @Override // java.lang.Runnable
        public void run() {
            SemHorizontalFastScroller.this.setState(0);
        }
    };
    private final Animator.AnimatorListener mSwitchPrimaryListener = new AnimatorListenerAdapter() { // from class: com.samsung.android.widget.SemHorizontalFastScroller.2
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            SemHorizontalFastScroller.this.mShowingPrimary = !r0.mShowingPrimary;
        }
    };

    public SemHorizontalFastScroller(SemHorizontalAbsListView listView, int styleResId) {
        this.mList = listView;
        this.mOldItemCount = listView.getCount();
        this.mOldChildCount = listView.getChildCount();
        Context context = listView.getContext();
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mScrollBarStyle = listView.getScrollBarStyle();
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
        setStyle(styleResId);
        ViewGroupOverlay overlay = listView.getOverlay();
        this.mOverlay = overlay;
        overlay.add(imageView);
        overlay.add(imageView2);
        overlay.add(view);
        overlay.add(createPreviewTextView);
        overlay.add(createPreviewTextView2);
        getSectionsFromIndexer();
        updateLongList(this.mOldChildCount, this.mOldItemCount);
        setScrollbarPosition(listView.semGetHorizontalScrollbarPosition());
        postAutoHide();
    }

    private void updateAppearance() {
        Context context = this.mList.getContext();
        this.mTrackImage.lambda$setImageURIAsync$2(this.mTrackDrawable);
        Drawable drawable = this.mTrackDrawable;
        int height = drawable != null ? Math.max(0, drawable.getIntrinsicHeight()) : 0;
        this.mThumbImage.lambda$setImageURIAsync$2(this.mThumbDrawable);
        this.mThumbImage.setMinimumWidth(this.mThumbMinWidth);
        this.mThumbImage.setMinimumHeight(this.mThumbMinHeight);
        this.mThumbImage.setRotation(270.0f);
        Drawable drawable2 = this.mThumbDrawable;
        if (drawable2 != null) {
            height = Math.max(height, drawable2.getIntrinsicWidth());
        }
        this.mHeight = Math.max(height, this.mThumbMinHeight);
        this.mPreviewImage.setMinimumWidth(this.mPreviewMinWidth);
        this.mPreviewImage.setMinimumHeight(this.mPreviewMinHeight);
        int i = this.mTextAppearance;
        if (i != 0) {
            this.mPrimaryText.setTextAppearance(context, i);
            this.mSecondaryText.setTextAppearance(context, this.mTextAppearance);
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
        int textMinSize = Math.max(0, this.mPreviewMinWidth);
        this.mPrimaryText.setMinimumWidth(textMinSize);
        this.mPrimaryText.setMinimumHeight(textMinSize);
        this.mPrimaryText.setIncludeFontPadding(false);
        this.mSecondaryText.setMinimumWidth(textMinSize);
        this.mSecondaryText.setMinimumHeight(textMinSize);
        this.mSecondaryText.setIncludeFontPadding(false);
        refreshDrawablePressedState();
    }

    public void setStyle(int resId) {
        Context context = this.mList.getContext();
        TypedArray ta = context.obtainStyledAttributes(null, C4337R.styleable.FastScroll, 16843767, resId);
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
        updateAppearance();
    }

    public void remove() {
        this.mOverlay.remove(this.mTrackImage);
        this.mOverlay.remove(this.mThumbImage);
        this.mOverlay.remove(this.mPreviewImage);
        this.mOverlay.remove(this.mPrimaryText);
        this.mOverlay.remove(this.mSecondaryText);
    }

    public void setEnabled(boolean enabled) {
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
            updateLayout();
        }
    }

    public void stop() {
        setState(0);
    }

    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v3 */
    public void setScrollbarPosition(int position) {
        if (position == 0) {
            position = this.mList.isLayoutRtl() ? 1 : 2;
        }
        if (this.mScrollbarPosition != position) {
            this.mScrollbarPosition = position;
            ?? r0 = position == 1 ? 0 : 1;
            this.mLayoutFromBottom = r0;
            int previewResId = this.mPreviewResId[r0];
            this.mPreviewImage.setBackgroundResource(previewResId);
            Drawable background = this.mPreviewImage.getBackground();
            if (background != null) {
                Rect padding = this.mTempBounds;
                background.getPadding(padding);
                int i = this.mPreviewPadding;
                padding.offset(i, i);
                this.mPreviewImage.setPadding(padding.left, padding.top, padding.right, padding.bottom);
            }
            updateLayout();
        }
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
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
        boolean longList = childCount > 0 && itemCount / childCount >= 4;
        if (this.mLongList != longList) {
            this.mLongList = longList;
            onStateDependencyChanged(false);
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
        if (this.mUpdatingLayout) {
            return;
        }
        this.mUpdatingLayout = true;
        updateContainerRect();
        layoutThumb();
        layoutTrack();
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
        this.mUpdatingLayout = false;
    }

    private void applyLayout(View view, Rect bounds) {
        view.layout(bounds.left, bounds.top, bounds.right, bounds.bottom);
        view.setPivotY(this.mLayoutFromBottom ? bounds.bottom - bounds.top : 0.0f);
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
        int marginTop;
        int marginBottom;
        int maxHeight;
        int top;
        int bottom;
        if (margins == null) {
            marginLeft = 0;
            marginTop = 0;
            marginBottom = 0;
        } else {
            marginLeft = margins.left;
            marginTop = margins.top;
            marginBottom = margins.bottom;
        }
        Rect container = this.mContainerRect;
        int containerHeight = container.height();
        if (adjacent == null) {
            maxHeight = containerHeight;
        } else if (this.mLayoutFromBottom) {
            maxHeight = adjacent.getTop();
        } else {
            int maxHeight2 = adjacent.getBottom();
            maxHeight = containerHeight - maxHeight2;
        }
        int adjMaxHeight = (maxHeight - marginTop) - marginBottom;
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(adjMaxHeight, Integer.MIN_VALUE);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        int height = Math.min(adjMaxHeight, view.getMeasuredHeight());
        if (this.mLayoutFromBottom) {
            bottom = (adjacent == null ? container.bottom : adjacent.getTop()) - marginBottom;
            top = bottom - height;
        } else {
            top = (adjacent == null ? container.top : adjacent.getBottom()) + marginTop;
            bottom = top + height;
        }
        int left = marginLeft;
        int right = left + view.getMeasuredWidth();
        out.set(left, top, right, bottom);
    }

    private void measureFloating(View preview, Rect margins, Rect out) {
        int marginLeft;
        int marginTop;
        int marginBottom;
        if (margins == null) {
            marginLeft = 0;
            marginTop = 0;
            marginBottom = 0;
        } else {
            marginLeft = margins.left;
            marginTop = margins.top;
            marginBottom = margins.bottom;
        }
        Rect container = this.mContainerRect;
        int containerHeight = container.height();
        int adjMaxHeight = (containerHeight - marginTop) - marginBottom;
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(adjMaxHeight, Integer.MIN_VALUE);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        preview.measure(widthMeasureSpec, heightMeasureSpec);
        int containerWidth = container.width();
        int height = preview.getMeasuredHeight();
        int left = (containerWidth / 10) + marginLeft + container.left;
        int right = preview.getMeasuredWidth() + left;
        int top = ((containerHeight - height) / 2) + container.top;
        int bottom = left + height;
        out.set(left, top, right, bottom);
    }

    private void updateContainerRect() {
        SemHorizontalAbsListView list = this.mList;
        list.resolvePadding();
        Rect container = this.mContainerRect;
        container.left = 0;
        container.top = 0;
        container.right = list.getWidth();
        container.bottom = list.getHeight();
        int scrollbarStyle = this.mScrollBarStyle;
        if (scrollbarStyle == 16777216 || scrollbarStyle == 0) {
            container.left += list.getPaddingLeft();
            container.top += list.getPaddingTop();
            container.right -= list.getPaddingRight();
            container.bottom -= list.getPaddingBottom();
            if (scrollbarStyle == 16777216) {
                int height = getHeight();
                if (this.mScrollbarPosition == 2) {
                    container.bottom += height;
                } else {
                    container.top -= height;
                }
            }
        }
    }

    private void layoutThumb() {
        Rect bounds = this.mTempBounds;
        measureViewToSide(this.mThumbImage, null, null, bounds);
        applyLayout(this.mThumbImage, bounds);
    }

    private void layoutTrack() {
        View track = this.mTrackImage;
        View thumb = this.mThumbImage;
        Rect container = this.mContainerRect;
        int containerHeight = container.height();
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(containerHeight, Integer.MIN_VALUE);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        track.measure(widthMeasureSpec, heightMeasureSpec);
        int trackHeight = track.getMeasuredHeight();
        int thumbHalfWidth = thumb.getWidth() / 2;
        int top = thumb.getTop() + ((thumb.getHeight() - trackHeight) / 2);
        int bottom = top + trackHeight;
        int left = container.left + thumbHalfWidth;
        int right = container.right - thumbHalfWidth;
        track.layout(left, top, right, bottom);
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
                if (transitionPreviewLayout(this.mCurrentSection)) {
                    transitionToDragging();
                    break;
                } else {
                    transitionToVisible();
                    break;
                }
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
        AnimatorSet animatorSet = this.mDecorAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        Animator fadeOut = groupAnimatorOfFloat(View.ALPHA, 0.0f, this.mThumbImage, this.mTrackImage, this.mPreviewImage, this.mPrimaryText, this.mSecondaryText).setDuration(300L);
        float offset = this.mLayoutFromBottom ? this.mThumbImage.getHeight() : -this.mThumbImage.getHeight();
        Animator slideOut = groupAnimatorOfFloat(View.TRANSLATION_Y, offset, this.mThumbImage, this.mTrackImage).setDuration(300L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mDecorAnimation = animatorSet2;
        animatorSet2.playTogether(fadeOut, slideOut);
        this.mDecorAnimation.start();
        this.mShowingPreview = false;
    }

    private void transitionToVisible() {
        AnimatorSet animatorSet = this.mDecorAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        Animator fadeIn = groupAnimatorOfFloat(View.ALPHA, 1.0f, this.mThumbImage, this.mTrackImage).setDuration(150L);
        Animator fadeOut = groupAnimatorOfFloat(View.ALPHA, 0.0f, this.mPreviewImage, this.mPrimaryText, this.mSecondaryText).setDuration(300L);
        Animator slideIn = groupAnimatorOfFloat(View.TRANSLATION_Y, 0.0f, this.mThumbImage, this.mTrackImage).setDuration(150L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mDecorAnimation = animatorSet2;
        animatorSet2.playTogether(fadeIn, fadeOut, slideIn);
        this.mDecorAnimation.start();
        this.mShowingPreview = false;
    }

    private void transitionToDragging() {
        AnimatorSet animatorSet = this.mDecorAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        Animator fadeIn = groupAnimatorOfFloat(View.ALPHA, 1.0f, this.mThumbImage, this.mTrackImage, this.mPreviewImage).setDuration(150L);
        Animator slideIn = groupAnimatorOfFloat(View.TRANSLATION_Y, 0.0f, this.mThumbImage, this.mTrackImage).setDuration(150L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mDecorAnimation = animatorSet2;
        animatorSet2.playTogether(fadeIn, slideIn);
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
        boolean hasMoreItems = totalItemCount - visibleItemCount > 0;
        if (hasMoreItems && this.mState != 2) {
            setThumbPos(getPosFromItemCount(firstVisibleItem, visibleItemCount, totalItemCount));
        }
        this.mScrollCompleted = true;
        if (this.mFirstVisibleItem != firstVisibleItem) {
            this.mFirstVisibleItem = firstVisibleItem;
            if (this.mState != 2) {
                setState(1);
                postAutoHide();
            }
        }
    }

    private void getSectionsFromIndexer() {
        this.mSectionIndexer = null;
        Adapter adapter = this.mList.getAdapter();
        if (adapter instanceof SemHorizontalHeaderViewListAdapter) {
            this.mHeaderCount = ((SemHorizontalHeaderViewListAdapter) adapter).getHeadersCount();
            adapter = ((SemHorizontalHeaderViewListAdapter) adapter).getWrappedAdapter();
        }
        if (adapter instanceof SectionIndexer) {
            this.mListAdapter = adapter;
            SectionIndexer sectionIndexer = (SectionIndexer) adapter;
            this.mSectionIndexer = sectionIndexer;
            this.mSections = sectionIndexer.getSections();
            return;
        }
        this.mListAdapter = adapter;
        this.mSections = null;
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
        if (sections != null && sectionCount > 1) {
            int exactSection = MathUtils.constrain((int) (sectionCount * position), 0, sectionCount - 1);
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
            while (nextNextSection < sectionCount && this.mSectionIndexer.getPositionForSection(nextNextSection) == nextIndex) {
                nextNextSection++;
                nextSection++;
            }
            float prevPosition = prevSection / sectionCount;
            float nextPosition = nextSection / sectionCount;
            float snapThreshold = count == 0 ? Float.MAX_VALUE : 0.125f / count;
            if (prevSection == exactSection && position - prevPosition < snapThreshold) {
                targetIndex = prevIndex;
            } else {
                targetIndex = ((int) (((nextIndex - prevIndex) * (position - prevPosition)) / (nextPosition - prevPosition))) + prevIndex;
            }
            int targetIndex3 = MathUtils.constrain(targetIndex, 0, count - 1);
            SemHorizontalAbsListView semHorizontalAbsListView = this.mList;
            if (semHorizontalAbsListView instanceof SemHorizontalListView) {
                ((SemHorizontalListView) semHorizontalAbsListView).setSelectionFromStart(this.mHeaderCount + targetIndex3, 0);
            } else {
                semHorizontalAbsListView.setSelection(this.mHeaderCount + targetIndex3);
            }
        } else {
            int index = MathUtils.constrain((int) (count * position), 0, count - 1);
            SemHorizontalAbsListView semHorizontalAbsListView2 = this.mList;
            if (semHorizontalAbsListView2 instanceof SemHorizontalListView) {
                ((SemHorizontalListView) semHorizontalAbsListView2).setSelectionFromStart(this.mHeaderCount + index, 0);
            } else {
                semHorizontalAbsListView2.setSelection(this.mHeaderCount + index);
            }
            sectionIndex = -1;
        }
        if (this.mCurrentSection != sectionIndex) {
            this.mCurrentSection = sectionIndex;
            boolean hasPreview = transitionPreviewLayout(sectionIndex);
            boolean z = this.mShowingPreview;
            if (!z && hasPreview) {
                transitionToDragging();
            } else if (z && !hasPreview) {
                transitionToVisible();
            }
        }
    }

    private boolean transitionPreviewLayout(int sectionIndex) {
        TextView showing;
        TextView target;
        Object section;
        Object[] sections = this.mSections;
        String text = null;
        if (sections != null && sectionIndex >= 0 && sectionIndex < sections.length && (section = sections[sectionIndex]) != null) {
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
        target.setText(text);
        measurePreview(target, bounds);
        applyLayout(target, bounds);
        AnimatorSet animatorSet = this.mPreviewAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        Animator showTarget = animateAlpha(target, 1.0f).setDuration(50L);
        Animator hideShowing = animateAlpha(showing, 0.0f).setDuration(50L);
        hideShowing.addListener(this.mSwitchPrimaryListener);
        bounds.left -= preview.getPaddingLeft();
        bounds.top -= preview.getPaddingTop();
        bounds.right += preview.getPaddingRight();
        bounds.bottom += preview.getPaddingBottom();
        Animator resizePreview = animateBounds(preview, bounds);
        resizePreview.setDuration(100L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mPreviewAnimation = animatorSet2;
        AnimatorSet.Builder builder = animatorSet2.play(hideShowing).with(showTarget);
        builder.with(resizePreview);
        int previewHeight = (preview.getHeight() - preview.getPaddingTop()) - preview.getPaddingBottom();
        int targetHeight = target.getHeight();
        if (targetHeight <= previewHeight) {
            target.setScaleY(1.0f);
        } else {
            target.setScaleY(previewHeight / targetHeight);
            Animator scaleAnim = animateScaleY(target, 1.0f).setDuration(100L);
            builder.with(scaleAnim);
        }
        int showingHeight = showing.getHeight();
        if (showingHeight > targetHeight) {
            float scale = targetHeight / showingHeight;
            Animator scaleAnim2 = animateScaleY(showing, scale).setDuration(100L);
            builder.with(scaleAnim2);
        }
        this.mPreviewAnimation.start();
        return !TextUtils.isEmpty(text);
    }

    private void setThumbPos(float position) {
        float previewPos;
        Rect container = this.mContainerRect;
        int left = container.left;
        int right = container.right;
        View trackImage = this.mTrackImage;
        View thumbImage = this.mThumbImage;
        float min = trackImage.getLeft();
        float max = trackImage.getRight();
        float range = max - min;
        float thumbMiddle = (position * range) + min;
        thumbImage.setTranslationX((thumbImage.getWidth() / 2.0f) + thumbMiddle);
        View previewImage = this.mPreviewImage;
        float previewHalfWidth = previewImage.getWidth() / 2.0f;
        switch (this.mOverlayPosition) {
            case 1:
                previewPos = thumbMiddle;
                break;
            case 2:
                previewPos = thumbMiddle - previewHalfWidth;
                break;
            default:
                previewPos = 0.0f;
                break;
        }
        float minP = left + previewHalfWidth;
        float maxP = right - previewHalfWidth;
        float previewMiddle = MathUtils.constrain(previewPos, minP, maxP);
        float previewLeft = previewMiddle - previewHalfWidth;
        previewImage.setTranslationX(previewLeft);
        this.mPrimaryText.setTranslationX(previewLeft);
        this.mSecondaryText.setTranslationX(previewLeft);
    }

    private float getPosFromMotionEvent(float x) {
        View trackImage = this.mTrackImage;
        float min = trackImage.getLeft();
        float max = trackImage.getRight();
        float range = max - min;
        if (range <= 0.0f) {
            return 0.0f;
        }
        return MathUtils.constrain((x - min) / range, 0.0f, 1.0f);
    }

    private float getPosFromItemCount(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        float incrementalPos;
        int nextSectionPos;
        float posWithinSection;
        int maxSize;
        int currentVisibleSize;
        int nextSectionPos2;
        Object[] objArr;
        if (this.mSectionIndexer == null || this.mListAdapter == null) {
            getSectionsFromIndexer();
        }
        boolean hasSections = (this.mSectionIndexer == null || (objArr = this.mSections) == null || objArr.length <= 0) ? false : true;
        if (!hasSections || !this.mMatchDragPosition) {
            return firstVisibleItem / (totalItemCount - visibleItemCount);
        }
        int i = this.mHeaderCount;
        int firstVisibleItem2 = firstVisibleItem - i;
        if (firstVisibleItem2 < 0) {
            return 0.0f;
        }
        int totalItemCount2 = totalItemCount - i;
        View child = this.mList.getChildAt(0);
        if (child == null || child.getWidth() == 0) {
            incrementalPos = 0.0f;
        } else {
            incrementalPos = (this.mList.getPaddingLeft() - child.getLeft()) / child.getWidth();
        }
        int section = this.mSectionIndexer.getSectionForPosition(firstVisibleItem2);
        int sectionPos = this.mSectionIndexer.getPositionForSection(section);
        int sectionCount = this.mSections.length;
        if (section < sectionCount - 1) {
            if (section + 1 < sectionCount) {
                nextSectionPos2 = this.mSectionIndexer.getPositionForSection(section + 1);
            } else {
                nextSectionPos2 = totalItemCount2 - 1;
            }
            nextSectionPos = nextSectionPos2 - sectionPos;
        } else {
            nextSectionPos = totalItemCount2 - sectionPos;
        }
        if (nextSectionPos == 0) {
            posWithinSection = 0.0f;
        } else {
            float posWithinSection2 = firstVisibleItem2;
            posWithinSection = ((posWithinSection2 + incrementalPos) - sectionPos) / nextSectionPos;
        }
        float result = (section + posWithinSection) / sectionCount;
        if (firstVisibleItem2 > 0 && firstVisibleItem2 + visibleItemCount == totalItemCount2) {
            View lastChild = this.mList.getChildAt(visibleItemCount - 1);
            int rightPadding = this.mList.getPaddingRight();
            if (this.mList.getClipToPadding()) {
                maxSize = lastChild.getWidth();
                currentVisibleSize = (this.mList.getWidth() - rightPadding) - lastChild.getLeft();
            } else {
                int maxSize2 = lastChild.getWidth();
                maxSize = maxSize2 + rightPadding;
                currentVisibleSize = this.mList.getWidth() - lastChild.getLeft();
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
        this.mPendingDrag = -1L;
        setState(2);
        if (this.mListAdapter == null) {
            getSectionsFromIndexer();
        }
        this.mList.requestDisallowInterceptTouchEvent(true);
        this.mList.reportScrollStateChange(1);
        cancelFling();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isEnabled()) {
            return false;
        }
        switch (ev.getActionMasked()) {
            case 0:
                if (isPointInside(ev.getX(), ev.getY())) {
                    if (!this.mList.isInScrollingContainer()) {
                        beginDrag();
                        return true;
                    }
                    this.mInitialTouchX = ev.getX();
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
                } else {
                    long j = this.mPendingDrag;
                    if (j >= 0 && j <= SystemClock.uptimeMillis()) {
                        beginDrag();
                        float pos = getPosFromMotionEvent(this.mInitialTouchX);
                        scrollTo(pos);
                        return onTouchEvent(ev);
                    }
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
        if ((actionMasked == 9 || actionMasked == 7) && this.mState == 0 && isPointInside(ev.getX(), ev.getY())) {
            setState(1);
            postAutoHide();
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean onTouchEvent(MotionEvent me) {
        if (!isEnabled()) {
            return false;
        }
        switch (me.getActionMasked()) {
            case 0:
                if (isPointInside(me.getX(), me.getY())) {
                    if (!this.mList.isInScrollingContainer()) {
                        beginDrag();
                        return true;
                    }
                    this.mInitialTouchX = me.getX();
                    startPendingDrag();
                }
                return false;
            case 1:
                if (this.mPendingDrag >= 0) {
                    beginDrag();
                    float pos = getPosFromMotionEvent(me.getX());
                    setThumbPos(pos);
                    scrollTo(pos);
                }
                if (this.mState == 2) {
                    this.mList.requestDisallowInterceptTouchEvent(false);
                    this.mList.reportScrollStateChange(0);
                    setState(1);
                    postAutoHide();
                    return true;
                }
                return false;
            case 2:
                if (this.mPendingDrag >= 0 && Math.abs(me.getX() - this.mInitialTouchX) > this.mScaledTouchSlop) {
                    beginDrag();
                }
                if (this.mState == 2) {
                    float pos2 = getPosFromMotionEvent(me.getX());
                    setThumbPos(pos2);
                    if (this.mScrollCompleted) {
                        scrollTo(pos2);
                    }
                    return true;
                }
                return false;
            case 3:
                cancelPendingDrag();
                return false;
            default:
                return false;
        }
    }

    private boolean isPointInside(float x, float y) {
        return isPointInsideY(y) && (this.mTrackDrawable != null || isPointInsideX(x));
    }

    private boolean isPointInsideY(float y) {
        return this.mLayoutFromBottom ? y >= ((float) this.mThumbImage.getTop()) : y <= ((float) this.mThumbImage.getBottom());
    }

    private boolean isPointInsideX(float x) {
        float offset = this.mThumbImage.getTranslationX();
        float left = this.mThumbImage.getLeft() + offset;
        float right = this.mThumbImage.getRight() + offset;
        return x >= left && x <= right;
    }

    private static Animator groupAnimatorOfFloat(Property<View, Float> property, float value, View... views) {
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

    private static Animator animateScaleY(View v, float target) {
        return ObjectAnimator.ofFloat(v, View.SCALE_Y, target);
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
}
