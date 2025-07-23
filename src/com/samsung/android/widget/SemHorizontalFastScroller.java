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
import android.os.SystemClock;
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
import android.widget.ListAdapter;
import android.widget.SectionIndexer;
import android.widget.SemHorizontalAbsListView;
import android.widget.TextView;
import com.android.internal.R;
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
        public void setValue(View view, int i) {
            view.setLeft(i);
        }

        @Override // android.util.Property
        public Integer get(View view) {
            return Integer.valueOf(view.getLeft());
        }
    };
    private static Property<View, Integer> TOP = new IntProperty<View>(GenerateXML.TOP) { // from class: com.samsung.android.widget.SemHorizontalFastScroller.4
        @Override // android.util.IntProperty
        public void setValue(View view, int i) {
            view.setTop(i);
        }

        @Override // android.util.Property
        public Integer get(View view) {
            return Integer.valueOf(view.getTop());
        }
    };
    private static Property<View, Integer> RIGHT = new IntProperty<View>("right") { // from class: com.samsung.android.widget.SemHorizontalFastScroller.5
        @Override // android.util.IntProperty
        public void setValue(View view, int i) {
            view.setRight(i);
        }

        @Override // android.util.Property
        public Integer get(View view) {
            return Integer.valueOf(view.getRight());
        }
    };
    private static Property<View, Integer> BOTTOM = new IntProperty<View>(GenerateXML.BOTTOM) { // from class: com.samsung.android.widget.SemHorizontalFastScroller.6
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
    private final Runnable mDeferHide = new Runnable() { // from class: com.samsung.android.widget.SemHorizontalFastScroller.1
        @Override // java.lang.Runnable
        public void run() {
            SemHorizontalFastScroller.this.setState(0);
        }
    };
    private final Animator.AnimatorListener mSwitchPrimaryListener = new AnimatorListenerAdapter() { // from class: com.samsung.android.widget.SemHorizontalFastScroller.2
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            SemHorizontalFastScroller.this.mShowingPrimary = !r0.mShowingPrimary;
        }
    };

    public SemHorizontalFastScroller(SemHorizontalAbsListView semHorizontalAbsListView, int i) {
        this.mList = semHorizontalAbsListView;
        this.mOldItemCount = semHorizontalAbsListView.getCount();
        this.mOldChildCount = semHorizontalAbsListView.getChildCount();
        Context context = semHorizontalAbsListView.getContext();
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mScrollBarStyle = semHorizontalAbsListView.getScrollBarStyle();
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
        setStyle(i);
        ViewGroupOverlay overlay = semHorizontalAbsListView.getOverlay();
        this.mOverlay = overlay;
        overlay.add(imageView);
        overlay.add(imageView2);
        overlay.add(view);
        overlay.add(createPreviewTextView);
        overlay.add(createPreviewTextView2);
        getSectionsFromIndexer();
        updateLongList(this.mOldChildCount, this.mOldItemCount);
        setScrollbarPosition(semHorizontalAbsListView.semGetHorizontalScrollbarPosition());
        postAutoHide();
    }

    private void updateAppearance() {
        Context context = this.mList.getContext();
        this.mTrackImage.lambda$setImageURIAsync$0(this.mTrackDrawable);
        Drawable drawable = this.mTrackDrawable;
        int max = drawable != null ? Math.max(0, drawable.getIntrinsicHeight()) : 0;
        this.mThumbImage.lambda$setImageURIAsync$0(this.mThumbDrawable);
        this.mThumbImage.setMinimumWidth(this.mThumbMinWidth);
        this.mThumbImage.setMinimumHeight(this.mThumbMinHeight);
        this.mThumbImage.setRotation(270.0f);
        Drawable drawable2 = this.mThumbDrawable;
        if (drawable2 != null) {
            max = Math.max(max, drawable2.getIntrinsicWidth());
        }
        this.mHeight = Math.max(max, this.mThumbMinHeight);
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
        int max2 = Math.max(0, this.mPreviewMinWidth);
        this.mPrimaryText.setMinimumWidth(max2);
        this.mPrimaryText.setMinimumHeight(max2);
        this.mPrimaryText.setIncludeFontPadding(false);
        this.mSecondaryText.setMinimumWidth(max2);
        this.mSecondaryText.setMinimumHeight(max2);
        this.mSecondaryText.setIncludeFontPadding(false);
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
    /* JADX WARN: Type inference failed for: r0v5 */
    public void setScrollbarPosition(int i) {
        if (i == 0) {
            i = this.mList.isLayoutRtl() ? 1 : 2;
        }
        if (this.mScrollbarPosition != i) {
            this.mScrollbarPosition = i;
            ?? r0 = i == 1 ? 0 : 1;
            this.mLayoutFromBottom = r0;
            this.mPreviewImage.setBackgroundResource(this.mPreviewResId[r0]);
            Drawable background = this.mPreviewImage.getBackground();
            if (background != null) {
                Rect rect = this.mTempBounds;
                background.getPadding(rect);
                int i2 = this.mPreviewPadding;
                rect.offset(i2, i2);
                this.mPreviewImage.setPadding(rect.left, rect.top, rect.right, rect.bottom);
            }
            updateLayout();
        }
    }

    public int getHeight() {
        return this.mHeight;
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
        this.mUpdatingLayout = false;
    }

    private void applyLayout(View view, Rect rect) {
        view.layout(rect.left, rect.top, rect.right, rect.bottom);
        view.setPivotY(this.mLayoutFromBottom ? rect.bottom - rect.top : 0.0f);
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
        int bottom;
        int i4;
        if (rect == null) {
            i3 = 0;
            i = 0;
            i2 = 0;
        } else {
            i = rect.left;
            i2 = rect.top;
            i3 = rect.bottom;
        }
        Rect rect3 = this.mContainerRect;
        int height = rect3.height();
        if (view2 != null) {
            if (this.mLayoutFromBottom) {
                height = view2.getTop();
            } else {
                height -= view2.getBottom();
            }
        }
        int i5 = (height - i2) - i3;
        view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(i5, Integer.MIN_VALUE));
        int min = Math.min(i5, view.getMeasuredHeight());
        if (this.mLayoutFromBottom) {
            i4 = (view2 == null ? rect3.bottom : view2.getTop()) - i3;
            bottom = i4 - min;
        } else {
            bottom = (view2 == null ? rect3.top : view2.getBottom()) + i2;
            i4 = bottom + min;
        }
        rect2.set(i, bottom, view.getMeasuredWidth() + i, i4);
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
            i3 = rect.bottom;
        }
        Rect rect3 = this.mContainerRect;
        int height = rect3.height();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec((height - i2) - i3, Integer.MIN_VALUE));
        int width = rect3.width();
        int measuredHeight = view.getMeasuredHeight();
        int i4 = (width / 10) + i + rect3.left;
        rect2.set(i4, ((height - measuredHeight) / 2) + rect3.top, view.getMeasuredWidth() + i4, measuredHeight + i4);
    }

    private void updateContainerRect() {
        SemHorizontalAbsListView semHorizontalAbsListView = this.mList;
        semHorizontalAbsListView.resolvePadding();
        Rect rect = this.mContainerRect;
        rect.left = 0;
        rect.top = 0;
        rect.right = semHorizontalAbsListView.getWidth();
        rect.bottom = semHorizontalAbsListView.getHeight();
        int i = this.mScrollBarStyle;
        if (i == 16777216 || i == 0) {
            rect.left += semHorizontalAbsListView.getPaddingLeft();
            rect.top += semHorizontalAbsListView.getPaddingTop();
            rect.right -= semHorizontalAbsListView.getPaddingRight();
            rect.bottom -= semHorizontalAbsListView.getPaddingBottom();
            if (i == 16777216) {
                int height = getHeight();
                if (this.mScrollbarPosition == 2) {
                    rect.bottom += height;
                } else {
                    rect.top -= height;
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
        ImageView imageView = this.mTrackImage;
        ImageView imageView2 = this.mThumbImage;
        Rect rect = this.mContainerRect;
        imageView.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(rect.height(), Integer.MIN_VALUE));
        int measuredHeight = imageView.getMeasuredHeight();
        int width = imageView2.getWidth() / 2;
        int top = imageView2.getTop() + ((imageView2.getHeight() - measuredHeight) / 2);
        imageView.layout(rect.left + width, top, rect.right - width, measuredHeight + top);
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
        Animator duration2 = groupAnimatorOfFloat(View.TRANSLATION_Y, this.mLayoutFromBottom ? this.mThumbImage.getHeight() : -this.mThumbImage.getHeight(), this.mThumbImage, this.mTrackImage).setDuration(300L);
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
        Animator duration3 = groupAnimatorOfFloat(View.TRANSLATION_Y, 0.0f, this.mThumbImage, this.mTrackImage).setDuration(150L);
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
        Animator duration2 = groupAnimatorOfFloat(View.TRANSLATION_Y, 0.0f, this.mThumbImage, this.mTrackImage).setDuration(150L);
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
        if (adapter instanceof SemHorizontalHeaderViewListAdapter) {
            SemHorizontalHeaderViewListAdapter semHorizontalHeaderViewListAdapter = (SemHorizontalHeaderViewListAdapter) adapter;
            this.mHeaderCount = semHorizontalHeaderViewListAdapter.getHeadersCount();
            adapter = semHorizontalHeaderViewListAdapter.getWrappedAdapter();
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

    /* JADX WARN: Removed duplicated region for block: B:27:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0066  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void scrollTo(float r14) {
        /*
            Method dump skipped, instructions count: 208
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.widget.SemHorizontalFastScroller.scrollTo(float):void");
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
        int height = (view.getHeight() - view.getPaddingTop()) - view.getPaddingBottom();
        int height2 = textView2.getHeight();
        if (height2 > height) {
            textView2.setScaleY(height / height2);
            with.with(animateScaleY(textView2, 1.0f).setDuration(100L));
        } else {
            textView2.setScaleY(1.0f);
        }
        int height3 = textView.getHeight();
        if (height3 > height2) {
            with.with(animateScaleY(textView, height2 / height3).setDuration(100L));
        }
        this.mPreviewAnimation.start();
        return !TextUtils.isEmpty(obj2);
    }

    private void setThumbPos(float f) {
        Rect rect = this.mContainerRect;
        int i = rect.left;
        int i2 = rect.right;
        ImageView imageView = this.mTrackImage;
        ImageView imageView2 = this.mThumbImage;
        float left = imageView.getLeft();
        float right = (f * (imageView.getRight() - left)) + left;
        imageView2.setTranslationX((imageView2.getWidth() / 2.0f) + right);
        View view = this.mPreviewImage;
        float width = view.getWidth() / 2.0f;
        int i3 = this.mOverlayPosition;
        if (i3 != 1) {
            right = i3 != 2 ? 0.0f : right - width;
        }
        float constrain = MathUtils.constrain(right, i + width, i2 - width) - width;
        view.setTranslationX(constrain);
        this.mPrimaryText.setTranslationX(constrain);
        this.mSecondaryText.setTranslationX(constrain);
    }

    private float getPosFromMotionEvent(float f) {
        float left = this.mTrackImage.getLeft();
        float right = r3.getRight() - left;
        if (right <= 0.0f) {
            return 0.0f;
        }
        return MathUtils.constrain((f - left) / right, 0.0f, 1.0f);
    }

    private float getPosFromItemCount(int i, int i2, int i3) {
        Object[] objArr;
        int i4;
        int width;
        int width2;
        int left;
        if (this.mSectionIndexer == null || this.mListAdapter == null) {
            getSectionsFromIndexer();
        }
        if (this.mSectionIndexer == null || (objArr = this.mSections) == null || objArr.length <= 0 || !this.mMatchDragPosition) {
            return i / (i3 - i2);
        }
        int i5 = this.mHeaderCount;
        int i6 = i - i5;
        if (i6 < 0) {
            return 0.0f;
        }
        int i7 = i3 - i5;
        View childAt = this.mList.getChildAt(0);
        float paddingLeft = (childAt == null || childAt.getWidth() == 0) ? 0.0f : (this.mList.getPaddingLeft() - childAt.getLeft()) / childAt.getWidth();
        int sectionForPosition = this.mSectionIndexer.getSectionForPosition(i6);
        int positionForSection = this.mSectionIndexer.getPositionForSection(sectionForPosition);
        int length = this.mSections.length;
        if (sectionForPosition < length - 1) {
            int i8 = sectionForPosition + 1;
            i4 = (i8 < length ? this.mSectionIndexer.getPositionForSection(i8) : i7 - 1) - positionForSection;
        } else {
            i4 = i7 - positionForSection;
        }
        float f = (sectionForPosition + (i4 != 0 ? ((i6 + paddingLeft) - positionForSection) / i4 : 0.0f)) / length;
        if (i6 <= 0 || i6 + i2 != i7) {
            return f;
        }
        View childAt2 = this.mList.getChildAt(i2 - 1);
        int paddingRight = this.mList.getPaddingRight();
        if (this.mList.getClipToPadding()) {
            width = childAt2.getWidth();
            width2 = this.mList.getWidth() - paddingRight;
            left = childAt2.getLeft();
        } else {
            width = childAt2.getWidth() + paddingRight;
            width2 = this.mList.getWidth();
            left = childAt2.getLeft();
        }
        int i9 = width2 - left;
        return (i9 <= 0 || width <= 0) ? f : f + ((1.0f - f) * (i9 / width));
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
        if (this.mListAdapter == null) {
            getSectionsFromIndexer();
        }
        this.mList.requestDisallowInterceptTouchEvent(true);
        this.mList.reportScrollStateChange(1);
        cancelFling();
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0015, code lost:
    
        if (r0 != 3) goto L30;
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
            goto L72
        L18:
            float r0 = r7.getX()
            float r2 = r7.getY()
            boolean r0 = r6.isPointInside(r0, r2)
            if (r0 != 0) goto L2a
            r6.cancelPendingDrag()
            goto L72
        L2a:
            long r2 = r6.mPendingDrag
            r4 = 0
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 < 0) goto L72
            long r4 = android.os.SystemClock.uptimeMillis()
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 > 0) goto L72
            r6.beginDrag()
            float r0 = r6.mInitialTouchX
            float r0 = r6.getPosFromMotionEvent(r0)
            r6.scrollTo(r0)
            boolean r6 = r6.onTouchEvent(r7)
            return r6
        L4b:
            r6.cancelPendingDrag()
            goto L72
        L4f:
            float r0 = r7.getX()
            float r3 = r7.getY()
            boolean r0 = r6.isPointInside(r0, r3)
            if (r0 == 0) goto L72
            android.widget.SemHorizontalAbsListView r0 = r6.mList
            boolean r0 = r0.isInScrollingContainer()
            if (r0 != 0) goto L69
            r6.beginDrag()
            return r2
        L69:
            float r7 = r7.getX()
            r6.mInitialTouchX = r7
            r6.startPendingDrag()
        L72:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.widget.SemHorizontalFastScroller.onInterceptTouchEvent(android.view.MotionEvent):boolean");
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

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                if (this.mPendingDrag >= 0) {
                    beginDrag();
                    float posFromMotionEvent = getPosFromMotionEvent(motionEvent.getX());
                    setThumbPos(posFromMotionEvent);
                    scrollTo(posFromMotionEvent);
                }
                if (this.mState == 2) {
                    this.mList.requestDisallowInterceptTouchEvent(false);
                    this.mList.reportScrollStateChange(0);
                    setState(1);
                    postAutoHide();
                    return true;
                }
            } else if (actionMasked == 2) {
                if (this.mPendingDrag >= 0 && Math.abs(motionEvent.getX() - this.mInitialTouchX) > this.mScaledTouchSlop) {
                    beginDrag();
                }
                if (this.mState == 2) {
                    float posFromMotionEvent2 = getPosFromMotionEvent(motionEvent.getX());
                    setThumbPos(posFromMotionEvent2);
                    if (this.mScrollCompleted) {
                        scrollTo(posFromMotionEvent2);
                    }
                    return true;
                }
            } else if (actionMasked == 3) {
                cancelPendingDrag();
            }
        } else if (isPointInside(motionEvent.getX(), motionEvent.getY())) {
            if (!this.mList.isInScrollingContainer()) {
                beginDrag();
                return true;
            }
            this.mInitialTouchX = motionEvent.getX();
            startPendingDrag();
        }
        return false;
    }

    private boolean isPointInside(float f, float f2) {
        if (isPointInsideY(f2)) {
            return this.mTrackDrawable != null || isPointInsideX(f);
        }
        return false;
    }

    private boolean isPointInsideY(float f) {
        return this.mLayoutFromBottom ? f >= ((float) this.mThumbImage.getTop()) : f <= ((float) this.mThumbImage.getBottom());
    }

    private boolean isPointInsideX(float f) {
        float translationX = this.mThumbImage.getTranslationX();
        return f >= ((float) this.mThumbImage.getLeft()) + translationX && f <= ((float) this.mThumbImage.getRight()) + translationX;
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

    private static Animator animateScaleY(View view, float f) {
        return ObjectAnimator.ofFloat(view, View.SCALE_Y, f);
    }

    private static Animator animateAlpha(View view, float f) {
        return ObjectAnimator.ofFloat(view, View.ALPHA, f);
    }

    private static Animator animateBounds(View view, Rect rect) {
        return ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofInt(LEFT, rect.left), PropertyValuesHolder.ofInt(TOP, rect.top), PropertyValuesHolder.ofInt(RIGHT, rect.right), PropertyValuesHolder.ofInt(BOTTOM, rect.bottom));
    }
}
