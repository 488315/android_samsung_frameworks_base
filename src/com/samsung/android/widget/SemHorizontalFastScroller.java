package com.samsung.android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
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
import android.widget.SemHorizontalListView;
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
        public void setValue(View view, int i) throws Resources.NotFoundException {
            view.setLeft(i);
        }

        @Override // android.util.Property
        public Integer get(View view) {
            return Integer.valueOf(view.getLeft());
        }
    };
    private static Property<View, Integer> TOP = new IntProperty<View>(GenerateXML.TOP) { // from class: com.samsung.android.widget.SemHorizontalFastScroller.4
        @Override // android.util.IntProperty
        public void setValue(View view, int i) throws Resources.NotFoundException {
            view.setTop(i);
        }

        @Override // android.util.Property
        public Integer get(View view) {
            return Integer.valueOf(view.getTop());
        }
    };
    private static Property<View, Integer> RIGHT = new IntProperty<View>("right") { // from class: com.samsung.android.widget.SemHorizontalFastScroller.5
        @Override // android.util.IntProperty
        public void setValue(View view, int i) throws Resources.NotFoundException {
            view.setRight(i);
        }

        @Override // android.util.Property
        public Integer get(View view) {
            return Integer.valueOf(view.getRight());
        }
    };
    private static Property<View, Integer> BOTTOM = new IntProperty<View>(GenerateXML.BOTTOM) { // from class: com.samsung.android.widget.SemHorizontalFastScroller.6
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
        TextView textViewCreatePreviewTextView = createPreviewTextView(context);
        this.mPrimaryText = textViewCreatePreviewTextView;
        TextView textViewCreatePreviewTextView2 = createPreviewTextView(context);
        this.mSecondaryText = textViewCreatePreviewTextView2;
        setStyle(i);
        ViewGroupOverlay overlay = semHorizontalAbsListView.getOverlay();
        this.mOverlay = overlay;
        overlay.add(imageView);
        overlay.add(imageView2);
        overlay.add(view);
        overlay.add(textViewCreatePreviewTextView);
        overlay.add(textViewCreatePreviewTextView2);
        getSectionsFromIndexer();
        updateLongList(this.mOldChildCount, this.mOldItemCount);
        setScrollbarPosition(semHorizontalAbsListView.semGetHorizontalScrollbarPosition());
        postAutoHide();
    }

    private void updateAppearance() throws Resources.NotFoundException {
        Context context = this.mList.getContext();
        this.mTrackImage.lambda$setImageURIAsync$0(this.mTrackDrawable);
        Drawable drawable = this.mTrackDrawable;
        int iMax = drawable != null ? Math.max(0, drawable.getIntrinsicHeight()) : 0;
        this.mThumbImage.lambda$setImageURIAsync$0(this.mThumbDrawable);
        this.mThumbImage.setMinimumWidth(this.mThumbMinWidth);
        this.mThumbImage.setMinimumHeight(this.mThumbMinHeight);
        this.mThumbImage.setRotation(270.0f);
        Drawable drawable2 = this.mThumbDrawable;
        if (drawable2 != null) {
            iMax = Math.max(iMax, drawable2.getIntrinsicWidth());
        }
        this.mHeight = Math.max(iMax, this.mThumbMinHeight);
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
        int iMax2 = Math.max(0, this.mPreviewMinWidth);
        this.mPrimaryText.setMinimumWidth(iMax2);
        this.mPrimaryText.setMinimumHeight(iMax2);
        this.mPrimaryText.setIncludeFontPadding(false);
        this.mSecondaryText.setMinimumWidth(iMax2);
        this.mSecondaryText.setMinimumHeight(iMax2);
        this.mSecondaryText.setIncludeFontPadding(false);
        refreshDrawablePressedState();
    }

    public void setStyle(int i) {
        TypedArray typedArrayObtainStyledAttributes = this.mList.getContext().obtainStyledAttributes(null, R.styleable.FastScroll, 16843767, i);
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

    private void applyLayout(View view, Rect rect) throws Resources.NotFoundException {
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
        int top;
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
        int iHeight = rect3.height();
        if (view2 != null) {
            if (this.mLayoutFromBottom) {
                iHeight = view2.getTop();
            } else {
                iHeight -= view2.getBottom();
            }
        }
        int i4 = (iHeight - i2) - i3;
        view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(i4, Integer.MIN_VALUE));
        int iMin = Math.min(i4, view.getMeasuredHeight());
        if (this.mLayoutFromBottom) {
            top = (view2 == null ? rect3.bottom : view2.getTop()) - i3;
            bottom = top - iMin;
        } else {
            bottom = (view2 == null ? rect3.top : view2.getBottom()) + i2;
            top = bottom + iMin;
        }
        rect2.set(i, bottom, view.getMeasuredWidth() + i, top);
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
        int iHeight = rect3.height();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec((iHeight - i2) - i3, Integer.MIN_VALUE));
        int iWidth = rect3.width();
        int measuredHeight = view.getMeasuredHeight();
        int i4 = (iWidth / 10) + i + rect3.left;
        rect2.set(i4, ((iHeight - measuredHeight) / 2) + rect3.top, view.getMeasuredWidth() + i4, measuredHeight + i4);
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

    private void layoutThumb() throws Resources.NotFoundException {
        Rect rect = this.mTempBounds;
        measureViewToSide(this.mThumbImage, null, null, rect);
        applyLayout(this.mThumbImage, rect);
    }

    private void layoutTrack() throws Resources.NotFoundException {
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

    /* JADX WARN: Removed duplicated region for block: B:32:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x008f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void scrollTo(float f) throws Resources.NotFoundException {
        int i;
        int i2;
        float f2;
        float f3;
        SemHorizontalAbsListView semHorizontalAbsListView;
        this.mScrollCompleted = false;
        int count = this.mList.getCount();
        Object[] objArr = this.mSections;
        int length = objArr == null ? 0 : objArr.length;
        if (objArr != null && length > 1) {
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
                semHorizontalAbsListView = this.mList;
                if (!(semHorizontalAbsListView instanceof SemHorizontalListView)) {
                    ((SemHorizontalListView) semHorizontalAbsListView).setSelectionFromStart(iConstrain2 + this.mHeaderCount, 0);
                } else {
                    semHorizontalAbsListView.setSelection(iConstrain2 + this.mHeaderCount);
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
                    semHorizontalAbsListView = this.mList;
                    if (!(semHorizontalAbsListView instanceof SemHorizontalListView)) {
                    }
                }
            }
        } else {
            int iConstrain3 = MathUtils.constrain((int) (f * count), 0, count - 1);
            SemHorizontalAbsListView semHorizontalAbsListView2 = this.mList;
            if (semHorizontalAbsListView2 instanceof SemHorizontalListView) {
                ((SemHorizontalListView) semHorizontalAbsListView2).setSelectionFromStart(iConstrain3 + this.mHeaderCount, 0);
            } else {
                semHorizontalAbsListView2.setSelection(iConstrain3 + this.mHeaderCount);
            }
            i = -1;
        }
        if (this.mCurrentSection != i) {
            this.mCurrentSection = i;
            boolean zTransitionPreviewLayout = transitionPreviewLayout(i);
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
        Animator animatorAnimateBounds = animateBounds(view, rect);
        animatorAnimateBounds.setDuration(100L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mPreviewAnimation = animatorSet2;
        AnimatorSet.Builder builderWith = animatorSet2.play(duration2).with(duration);
        builderWith.with(animatorAnimateBounds);
        int height = (view.getHeight() - view.getPaddingTop()) - view.getPaddingBottom();
        int height2 = textView2.getHeight();
        if (height2 > height) {
            textView2.setScaleY(height / height2);
            builderWith.with(animateScaleY(textView2, 1.0f).setDuration(100L));
        } else {
            textView2.setScaleY(1.0f);
        }
        int height3 = textView.getHeight();
        if (height3 > height2) {
            builderWith.with(animateScaleY(textView, height2 / height3).setDuration(100L));
        }
        this.mPreviewAnimation.start();
        return !TextUtils.isEmpty(string);
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
        float fConstrain = MathUtils.constrain(right, i + width, i2 - width) - width;
        view.setTranslationX(fConstrain);
        this.mPrimaryText.setTranslationX(fConstrain);
        this.mSecondaryText.setTranslationX(fConstrain);
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
        int positionForSection;
        int width;
        int width2;
        int left;
        if (this.mSectionIndexer == null || this.mListAdapter == null) {
            getSectionsFromIndexer();
        }
        if (this.mSectionIndexer == null || (objArr = this.mSections) == null || objArr.length <= 0 || !this.mMatchDragPosition) {
            return i / (i3 - i2);
        }
        int i4 = this.mHeaderCount;
        int i5 = i - i4;
        if (i5 < 0) {
            return 0.0f;
        }
        int i6 = i3 - i4;
        View childAt = this.mList.getChildAt(0);
        float paddingLeft = (childAt == null || childAt.getWidth() == 0) ? 0.0f : (this.mList.getPaddingLeft() - childAt.getLeft()) / childAt.getWidth();
        int sectionForPosition = this.mSectionIndexer.getSectionForPosition(i5);
        int positionForSection2 = this.mSectionIndexer.getPositionForSection(sectionForPosition);
        int length = this.mSections.length;
        if (sectionForPosition < length - 1) {
            int i7 = sectionForPosition + 1;
            positionForSection = (i7 < length ? this.mSectionIndexer.getPositionForSection(i7) : i6 - 1) - positionForSection2;
        } else {
            positionForSection = i6 - positionForSection2;
        }
        float f = (sectionForPosition + (positionForSection != 0 ? ((i5 + paddingLeft) - positionForSection2) / positionForSection : 0.0f)) / length;
        if (i5 <= 0 || i5 + i2 != i6) {
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
        int i8 = width2 - left;
        return (i8 <= 0 || width <= 0) ? f : f + ((1.0f - f) * (i8 / width));
    }

    private void cancelFling() {
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x004b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        if (!isEnabled()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1) {
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
                    scrollTo(getPosFromMotionEvent(this.mInitialTouchX));
                    return onTouchEvent(motionEvent);
                }
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

    public boolean onTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
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
