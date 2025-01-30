package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.IntProperty;
import android.util.Log;
import android.util.Property;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import androidx.appcompat.animation.SeslAnimationUtils;
import androidx.core.math.MathUtils;
import androidx.recyclerview.R$styleable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.reflect.view.SeslHapticFeedbackConstantsReflector;
import com.android.systemui.R;
import com.samsung.android.nexus.video.VideoPlayer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslRecyclerViewFastScroller {
    public static final C04816 BOTTOM;
    public static final C04783 LEFT;
    public static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    public static final C04805 RIGHT;
    public static final C04794 TOP;
    public final float mAdditionalTouchArea;
    public int mColorPrimary;
    public AnimatorSet mDecorAnimation;
    public boolean mEnabled;
    public int mImmersiveBottomPadding;
    public boolean mLayoutFromRight;
    public RecyclerView.Adapter mListAdapter;
    public boolean mLongList;
    public final boolean mMatchDragPosition;
    public int mOldChildCount;
    public int mOldItemCount;
    public final int mOverlayPosition;
    public AnimatorSet mPreviewAnimation;
    public final View mPreviewImage;
    public final int mPreviewMarginEnd;
    public final int[] mPreviewResId;
    public final TextView mPrimaryText;
    public final RecyclerView mRecyclerView;
    public final int mScaledTouchSlop;
    public final int mScrollBarStyle;
    public boolean mScrollCompleted;
    public final TextView mSecondaryText;
    public SectionIndexer mSectionIndexer;
    public Object[] mSections;
    public boolean mShowingPreview;
    public boolean mShowingPrimary;
    public int mState;
    public int mThumbBackgroundColor;
    public final Drawable mThumbDrawable;
    public final ImageView mThumbImage;
    public final int mThumbMarginEnd;
    public float mThumbOffset;
    public final int mThumbPosition;
    public float mThumbRange;
    public final int mTrackBottomPadding;
    public final ImageView mTrackImage;
    public final int mTrackTopPadding;
    public boolean mUpdatingLayout;
    public final int mVibrateIndex;
    public int mWidth;
    public final Rect mTempBounds = new Rect();
    public final Rect mTempMargins = new Rect();
    public final Rect mContainerRect = new Rect();
    public int mCurrentSection = -1;
    public int mScrollbarPosition = -1;
    public long mPendingDrag = -1;
    public float mScrollY = 0.0f;
    public float mOldThumbPosition = -1.0f;
    public final RunnableC04761 mDeferHide = new Runnable() { // from class: androidx.recyclerview.widget.SeslRecyclerViewFastScroller.1
        @Override // java.lang.Runnable
        public final void run() {
            SeslRecyclerViewFastScroller seslRecyclerViewFastScroller = SeslRecyclerViewFastScroller.this;
            Interpolator interpolator = SeslRecyclerViewFastScroller.LINEAR_INTERPOLATOR;
            seslRecyclerViewFastScroller.setState(0);
        }
    };
    public final C04772 mSwitchPrimaryListener = new AnimatorListenerAdapter() { // from class: androidx.recyclerview.widget.SeslRecyclerViewFastScroller.2
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            SeslRecyclerViewFastScroller.this.mShowingPrimary = !r0.mShowingPrimary;
        }
    };

    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.recyclerview.widget.SeslRecyclerViewFastScroller$3] */
    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.recyclerview.widget.SeslRecyclerViewFastScroller$4] */
    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.recyclerview.widget.SeslRecyclerViewFastScroller$5] */
    /* JADX WARN: Type inference failed for: r0v4, types: [androidx.recyclerview.widget.SeslRecyclerViewFastScroller$6] */
    static {
        ViewConfiguration.getTapTimeout();
        LEFT = new IntProperty("left") { // from class: androidx.recyclerview.widget.SeslRecyclerViewFastScroller.3
            @Override // android.util.Property
            public final Integer get(Object obj) {
                return Integer.valueOf(((View) obj).getLeft());
            }

            @Override // android.util.IntProperty
            public final void setValue(Object obj, int i) {
                ((View) obj).setLeft(i);
            }
        };
        TOP = new IntProperty("top") { // from class: androidx.recyclerview.widget.SeslRecyclerViewFastScroller.4
            @Override // android.util.Property
            public final Integer get(Object obj) {
                return Integer.valueOf(((View) obj).getTop());
            }

            @Override // android.util.IntProperty
            public final void setValue(Object obj, int i) {
                ((View) obj).setTop(i);
            }
        };
        RIGHT = new IntProperty("right") { // from class: androidx.recyclerview.widget.SeslRecyclerViewFastScroller.5
            @Override // android.util.Property
            public final Integer get(Object obj) {
                return Integer.valueOf(((View) obj).getRight());
            }

            @Override // android.util.IntProperty
            public final void setValue(Object obj, int i) {
                ((View) obj).setRight(i);
            }
        };
        BOTTOM = new IntProperty("bottom") { // from class: androidx.recyclerview.widget.SeslRecyclerViewFastScroller.6
            @Override // android.util.Property
            public final Integer get(Object obj) {
                return Integer.valueOf(((View) obj).getBottom());
            }

            @Override // android.util.IntProperty
            public final void setValue(Object obj, int i) {
                ((View) obj).setBottom(i);
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5, types: [android.view.ViewGroupOverlay] */
    /* JADX WARN: Type inference failed for: r2v10, types: [android.view.View, android.widget.TextView] */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4, types: [android.view.View, android.widget.TextView] */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r5v2, types: [androidx.recyclerview.widget.SeslRecyclerViewFastScroller$1] */
    /* JADX WARN: Type inference failed for: r5v3, types: [androidx.recyclerview.widget.SeslRecyclerViewFastScroller$2] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v9, types: [boolean, int] */
    public SeslRecyclerViewFastScroller(RecyclerView recyclerView) {
        ?? r3;
        ?? r2;
        ?? r6;
        int[] iArr = new int[2];
        this.mPreviewResId = iArr;
        this.mColorPrimary = -1;
        this.mThumbBackgroundColor = -1;
        this.mAdditionalTouchArea = 0.0f;
        this.mRecyclerView = recyclerView;
        RecyclerView.Adapter adapter = recyclerView.mAdapter;
        this.mOldItemCount = adapter == null ? 0 : adapter.getItemCount();
        this.mOldChildCount = recyclerView.getChildCount();
        Context context = recyclerView.getContext();
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mScrollBarStyle = recyclerView.getScrollBarStyle();
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
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, R$styleable.FastScroll, 0, 2132019190);
        try {
            this.mOverlayPosition = obtainStyledAttributes.getInt(8, 0);
            iArr[0] = obtainStyledAttributes.getResourceId(6, 0);
            iArr[1] = obtainStyledAttributes.getResourceId(7, 0);
            Drawable drawable = obtainStyledAttributes.getDrawable(9);
            this.mThumbDrawable = drawable;
            Drawable drawable2 = obtainStyledAttributes.getDrawable(13);
            int resourceId = obtainStyledAttributes.getResourceId(0, 0);
            ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(2);
            float dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(1, 0);
            int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(4, 0);
            int dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(5, 0);
            int dimensionPixelSize4 = obtainStyledAttributes.getDimensionPixelSize(11, 0);
            int dimensionPixelSize5 = obtainStyledAttributes.getDimensionPixelSize(10, 0);
            int dimensionPixelSize6 = obtainStyledAttributes.getDimensionPixelSize(3, 0);
            this.mThumbPosition = obtainStyledAttributes.getInt(12, 0);
            obtainStyledAttributes.recycle();
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            int color = context.getResources().getColor(typedValue.resourceId);
            this.mColorPrimary = Color.argb(Math.round(Color.alpha(color) * 0.9f), Color.red(color), Color.green(color), Color.blue(color));
            this.mThumbBackgroundColor = context.getResources().getColor(R.color.sesl_fast_scrollbar_bg_color);
            imageView.setImageDrawable(drawable2);
            int max = drawable2 != null ? Math.max(0, drawable2.getIntrinsicWidth()) : 0;
            if (drawable != null) {
                drawable.setTint(this.mThumbBackgroundColor);
            }
            imageView2.setImageDrawable(drawable);
            imageView2.setMinimumWidth(dimensionPixelSize4);
            imageView2.setMinimumHeight(dimensionPixelSize5);
            this.mWidth = Math.max(drawable != null ? Math.max(max, drawable.getIntrinsicWidth()) : max, dimensionPixelSize4);
            view.setMinimumWidth(dimensionPixelSize2);
            view.setMinimumHeight(dimensionPixelSize3);
            if (resourceId != 0) {
                TextView textView = createPreviewTextView;
                textView.setTextAppearance(context, resourceId);
                TextView textView2 = createPreviewTextView2;
                textView2.setTextAppearance(context, resourceId);
                r2 = textView;
                r3 = textView2;
            } else {
                r3 = createPreviewTextView2;
                r2 = createPreviewTextView;
            }
            if (colorStateList != null) {
                r2.setTextColor(colorStateList);
                r3.setTextColor(colorStateList);
            }
            if (dimensionPixelSize > 0.0f) {
                r6 = 0;
                r2.setTextSize(0, dimensionPixelSize);
                r3.setTextSize(0, dimensionPixelSize);
            } else {
                r6 = 0;
            }
            int max2 = Math.max((int) r6, dimensionPixelSize3);
            r2.setMinimumWidth(dimensionPixelSize2);
            r2.setMinimumHeight(max2);
            r2.setIncludeFontPadding(r6);
            r3.setMinimumWidth(dimensionPixelSize2);
            r3.setMinimumHeight(max2);
            r3.setIncludeFontPadding(r6);
            boolean z = this.mState == 2;
            imageView2.setPressed(z);
            imageView.setPressed(z);
            ?? overlay = recyclerView.getOverlay();
            overlay.add(imageView);
            overlay.add(imageView2);
            overlay.add(view);
            overlay.add(r2);
            overlay.add(r3);
            Resources resources = context.getResources();
            this.mPreviewMarginEnd = resources.getDimensionPixelOffset(R.dimen.sesl_fast_scroll_preview_margin_end);
            this.mThumbMarginEnd = resources.getDimensionPixelOffset(R.dimen.sesl_fast_scroll_thumb_margin_end);
            this.mAdditionalTouchArea = resources.getDimension(R.dimen.sesl_fast_scroll_additional_touch_area);
            this.mTrackTopPadding = resources.getDimensionPixelOffset(R.dimen.sesl_fast_scroller_track_top_padding);
            this.mTrackBottomPadding = resources.getDimensionPixelOffset(R.dimen.sesl_fast_scroller_track_bottom_padding);
            this.mImmersiveBottomPadding = 0;
            r2.setPadding(dimensionPixelSize6, 0, dimensionPixelSize6, 0);
            r3.setPadding(dimensionPixelSize6, 0, dimensionPixelSize6, 0);
            getSectionsFromIndexer();
            updateLongList(this.mOldChildCount);
            setScrollbarPosition(recyclerView.getVerticalScrollbarPosition());
            postAutoHide();
            this.mVibrateIndex = SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(26);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public static Animator groupAnimatorOfFloat(Property property, float f, View... viewArr) {
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSet.Builder builder = null;
        for (int length = viewArr.length - 1; length >= 0; length--) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(viewArr[length], (Property<View, Float>) property, f);
            if (builder == null) {
                builder = animatorSet.play(ofFloat);
            } else {
                builder.with(ofFloat);
            }
        }
        return animatorSet;
    }

    public final void applyLayout(Rect rect, View view) {
        view.layout(rect.left, rect.top, rect.right, rect.bottom);
        view.setPivotX(this.mLayoutFromRight ? rect.right - rect.left : 0.0f);
    }

    public final void beginDrag() {
        this.mPendingDrag = -1L;
        if (this.mListAdapter == null) {
            getSectionsFromIndexer();
        }
        RecyclerView recyclerView = this.mRecyclerView;
        recyclerView.requestDisallowInterceptTouchEvent(true);
        MotionEvent obtain = MotionEvent.obtain(0L, 0L, 3, 0.0f, 0.0f, 0);
        recyclerView.onTouchEvent(obtain);
        obtain.recycle();
        setState(2);
    }

    public final boolean canScrollList(int i) {
        RecyclerView recyclerView = this.mRecyclerView;
        int childCount = recyclerView.getChildCount();
        if (childCount == 0) {
            return false;
        }
        int findFirstVisibleItemPosition = recyclerView.findFirstVisibleItemPosition();
        Rect rect = recyclerView.mListPadding;
        if (i <= 0) {
            return findFirstVisibleItemPosition > 0 || recyclerView.getChildAt(0).getTop() < rect.top;
        }
        int bottom = recyclerView.getChildAt(childCount - 1).getBottom();
        int i2 = findFirstVisibleItemPosition + childCount;
        RecyclerView.Adapter adapter = recyclerView.mAdapter;
        return i2 < (adapter == null ? 0 : adapter.getItemCount()) || bottom > recyclerView.getHeight() - rect.bottom;
    }

    public final TextView createPreviewTextView(Context context) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -2);
        TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        textView.setGravity(17);
        textView.setAlpha(0.0f);
        textView.setLayoutDirection(this.mRecyclerView.getLayoutDirection());
        return textView;
    }

    public final float getPosFromItemCount(int i, int i2, int i3) {
        int i4;
        float f;
        int i5;
        Object[] objArr;
        if (this.mSectionIndexer == null || this.mListAdapter == null) {
            getSectionsFromIndexer();
        }
        if (i2 == 0 || i3 == 0) {
            return 0.0f;
        }
        SectionIndexer sectionIndexer = this.mSectionIndexer;
        RecyclerView recyclerView = this.mRecyclerView;
        int paddingTop = recyclerView.getPaddingTop();
        RecyclerView.LayoutManager layoutManager = recyclerView.mLayout;
        if (paddingTop > 0 && (layoutManager instanceof LinearLayoutManager)) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            while (i > 0) {
                int i6 = i - 1;
                if (linearLayoutManager.findViewByPosition(i6) == null) {
                    break;
                }
                i = i6;
            }
        }
        int childAdapterPosition = i - RecyclerView.getChildAdapterPosition(recyclerView.getChildAt(0));
        if (childAdapterPosition < 0) {
            childAdapterPosition = 0;
        }
        View childAt = recyclerView.getChildAt(childAdapterPosition);
        float top = (childAt == null || childAt.getHeight() == 0) ? 0.0f : i == 0 ? (paddingTop - childAt.getTop()) / (childAt.getHeight() + paddingTop) : (-childAt.getTop()) / childAt.getHeight();
        if (((sectionIndexer == null || (objArr = this.mSections) == null || objArr.length <= 0) ? false : true) && this.mMatchDragPosition) {
            if (i < 0) {
                return 0.0f;
            }
            int sectionForPosition = sectionIndexer.getSectionForPosition(i);
            int positionForSection = sectionIndexer.getPositionForSection(sectionForPosition);
            int length = this.mSections.length;
            if (sectionForPosition < length - 1) {
                int i7 = sectionForPosition + 1;
                i5 = (i7 < length ? sectionIndexer.getPositionForSection(i7) : i3 - 1) - positionForSection;
            } else {
                i5 = i3 - positionForSection;
            }
            f = (sectionForPosition + (i5 != 0 ? ((i + top) - positionForSection) / i5 : 0.0f)) / length;
        } else {
            if (i2 == i3 && (i == 0 || (layoutManager instanceof StaggeredGridLayoutManager))) {
                if ((layoutManager instanceof StaggeredGridLayoutManager) && i != 0 && childAt != null) {
                    ((StaggeredGridLayoutManager.LayoutParams) childAt.getLayoutParams()).getClass();
                }
                return 0.0f;
            }
            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                i4 = gridLayoutManager.mSpanCount / gridLayoutManager.mSpanSizeLookup.getSpanSize(i);
            } else {
                i4 = layoutManager instanceof StaggeredGridLayoutManager ? ((StaggeredGridLayoutManager) layoutManager).mSpanCount : 1;
            }
            f = ((top * i4) + i) / i3;
        }
        if (i + i2 != i3) {
            return f;
        }
        View childAt2 = recyclerView.getChildAt(i2 - 1);
        View childAt3 = recyclerView.getChildAt(0);
        int paddingBottom = recyclerView.getPaddingBottom() + (childAt2.getBottom() - recyclerView.getHeight());
        int top2 = paddingBottom - (childAt3.getTop() - recyclerView.getPaddingTop());
        if (top2 > childAt2.getHeight() || i > 0) {
            top2 = childAt2.getHeight();
        }
        int i8 = top2 - paddingBottom;
        return (i8 <= 0 || top2 <= 0) ? f : f + ((i8 / top2) * (1.0f - f));
    }

    public final float getPosFromMotionEvent(float f) {
        float f2 = this.mThumbRange;
        if (f2 <= 0.0f) {
            return 0.0f;
        }
        return MathUtils.clamp((f - this.mThumbOffset) / f2, 0.0f, 1.0f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void getSectionsFromIndexer() {
        this.mSectionIndexer = null;
        RecyclerView.Adapter adapter = this.mRecyclerView.mAdapter;
        if (!(adapter instanceof SectionIndexer)) {
            this.mListAdapter = adapter;
            this.mSections = null;
        } else {
            this.mListAdapter = adapter;
            SectionIndexer sectionIndexer = (SectionIndexer) adapter;
            this.mSectionIndexer = sectionIndexer;
            this.mSections = sectionIndexer.getSections();
        }
    }

    public final boolean isEnabled() {
        if (this.mEnabled && !this.mLongList) {
            this.mLongList = canScrollList(1) || canScrollList(-1);
        }
        return this.mEnabled && this.mLongList;
    }

    public final boolean isPointInside(float f, float f2) {
        boolean z = this.mLayoutFromRight;
        ImageView imageView = this.mThumbImage;
        float f3 = this.mAdditionalTouchArea;
        if (!(!z ? f > ((float) imageView.getRight()) + f3 : f < ((float) imageView.getLeft()) - f3)) {
            return false;
        }
        float translationY = imageView.getTranslationY();
        return ((f2 > (((float) imageView.getTop()) + translationY) ? 1 : (f2 == (((float) imageView.getTop()) + translationY) ? 0 : -1)) >= 0 && (f2 > (((float) imageView.getBottom()) + translationY) ? 1 : (f2 == (((float) imageView.getBottom()) + translationY) ? 0 : -1)) <= 0) && this.mState != 0;
    }

    public final void measurePreview(Rect rect, View view) {
        View view2 = this.mPreviewImage;
        int paddingLeft = view2.getPaddingLeft();
        Rect rect2 = this.mTempMargins;
        rect2.left = paddingLeft;
        rect2.top = view2.getPaddingTop();
        rect2.right = view2.getPaddingRight();
        rect2.bottom = view2.getPaddingBottom();
        if (this.mOverlayPosition != 0) {
            measureViewToSide(view, this.mThumbImage, rect);
            return;
        }
        int i = rect2.left;
        int i2 = rect2.top;
        int i3 = rect2.right;
        Rect rect3 = this.mContainerRect;
        int width = rect3.width();
        view.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, (width - i) - i3), VideoPlayer.MEDIA_ERROR_SYSTEM), View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(Math.max(0, rect3.height())), 0));
        int height = rect3.height();
        int measuredWidth = view.getMeasuredWidth();
        int i4 = (height / 10) + i2 + rect3.top;
        int measuredHeight = view.getMeasuredHeight() + i4;
        int i5 = ((width - measuredWidth) / 2) + rect3.left;
        rect.set(i5, i4, measuredWidth + i5, measuredHeight);
    }

    public final void measureViewToSide(View view, View view2, Rect rect) {
        int i;
        int right;
        int i2;
        boolean z = this.mLayoutFromRight;
        int i3 = this.mThumbMarginEnd;
        int i4 = this.mPreviewMarginEnd;
        if (z) {
            if (view2 != null) {
                i3 = i4;
            }
            i = i3;
            i3 = 0;
        } else {
            if (view2 != null) {
                i3 = i4;
            }
            i = 0;
        }
        Rect rect2 = this.mContainerRect;
        int width = rect2.width();
        if (view2 != null) {
            width = this.mLayoutFromRight ? view2.getLeft() : width - view2.getRight();
        }
        int max = Math.max(0, rect2.height());
        int max2 = Math.max(0, (width - i3) - i);
        view.measure(View.MeasureSpec.makeMeasureSpec(max2, VideoPlayer.MEDIA_ERROR_SYSTEM), View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(max), 0));
        int min = Math.min(max2, view.getMeasuredWidth());
        if (this.mLayoutFromRight) {
            i2 = (view2 == null ? rect2.right : view2.getLeft()) - i;
            right = i2 - min;
        } else {
            right = (view2 == null ? rect2.left : view2.getRight()) + i3;
            i2 = right + min;
        }
        rect.set(right, 0, i2, view.getMeasuredHeight() + 0);
    }

    public final void onScroll(int i, int i2, int i3) {
        if (!isEnabled()) {
            setState(0);
            return;
        }
        if ((canScrollList(1) || canScrollList(-1)) && this.mState != 2) {
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

    public final void onStateDependencyChanged() {
        if (!isEnabled()) {
            setState(0);
        } else if (this.mState == 1) {
            postAutoHide();
        } else {
            setState(1);
            postAutoHide();
        }
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
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
                }
                if (this.mState == 2) {
                    this.mRecyclerView.requestDisallowInterceptTouchEvent(false);
                    setState(1);
                    postAutoHide();
                    this.mScrollY = 0.0f;
                    return true;
                }
            } else if (actionMasked == 2) {
                if (this.mPendingDrag >= 0 && Math.abs(motionEvent.getY() - 0.0f) > this.mScaledTouchSlop) {
                    beginDrag();
                    float f = this.mScrollY;
                    float f2 = i;
                    if (f > f2 && f < i2) {
                        float f3 = f2 + top;
                        if (f < f3) {
                            this.mScrollY = f3;
                        } else if (f > bottom) {
                            this.mScrollY = bottom;
                        }
                    }
                }
                if (this.mState == 2) {
                    float posFromMotionEvent2 = getPosFromMotionEvent(motionEvent.getY());
                    this.mOldThumbPosition = posFromMotionEvent2;
                    setThumbPos(posFromMotionEvent2);
                    if (this.mScrollCompleted) {
                        scrollTo(posFromMotionEvent2);
                    }
                    float f4 = this.mScrollY;
                    float f5 = i;
                    if (f4 > f5 && f4 < i2) {
                        float f6 = f5 + top;
                        if (f4 < f6) {
                            this.mScrollY = f6;
                        } else if (f4 > bottom) {
                            this.mScrollY = bottom;
                        }
                    }
                    return true;
                }
            } else if (actionMasked == 3) {
                this.mPendingDrag = -1L;
                if (this.mState == 2) {
                    setState(0);
                }
                this.mScrollY = 0.0f;
            }
        } else if (isPointInside(motionEvent.getX(), motionEvent.getY())) {
            beginDrag();
            return true;
        }
        return false;
    }

    public final void postAutoHide() {
        RecyclerView recyclerView = this.mRecyclerView;
        RunnableC04761 runnableC04761 = this.mDeferHide;
        recyclerView.removeCallbacks(runnableC04761);
        recyclerView.postDelayed(runnableC04761, 1500L);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void scrollTo(float f) {
        int clamp;
        int i;
        int i2;
        int i3;
        float f2;
        this.mScrollCompleted = false;
        RecyclerView recyclerView = this.mRecyclerView;
        RecyclerView.Adapter adapter = recyclerView.mAdapter;
        int itemCount = adapter == null ? 0 : adapter.getItemCount();
        Object[] objArr = this.mSections;
        int length = objArr == null ? 0 : objArr.length;
        if (objArr == null || length <= 0) {
            clamp = MathUtils.clamp((int) (itemCount * f), 0, itemCount - 1);
            i = -1;
        } else {
            float f3 = length;
            int i4 = length - 1;
            int clamp2 = MathUtils.clamp((int) (f * f3), 0, i4);
            int positionForSection = this.mSectionIndexer.getPositionForSection(clamp2);
            int i5 = clamp2 + 1;
            int positionForSection2 = clamp2 < i4 ? this.mSectionIndexer.getPositionForSection(i5) : itemCount;
            int i6 = clamp2;
            if (positionForSection2 == positionForSection) {
                i2 = positionForSection;
                do {
                    if (i6 <= 0) {
                        i6 = clamp2;
                    } else {
                        i6--;
                        i2 = this.mSectionIndexer.getPositionForSection(i6);
                        if (i2 != positionForSection) {
                        }
                    }
                    positionForSection = i2;
                } while (i6 != 0);
                i = 0;
                i6 = clamp2;
                i3 = i5 + 1;
                while (i3 < length && this.mSectionIndexer.getPositionForSection(i3) == positionForSection2) {
                    i3++;
                    i5++;
                }
                f2 = i6 / f3;
                float f4 = i5 / f3;
                float f5 = itemCount != 0 ? Float.MAX_VALUE : 0.125f / itemCount;
                if (i6 == clamp2 || f - f2 >= f5) {
                    i2 += (int) (((f - f2) * (positionForSection2 - i2)) / (f4 - f2));
                }
                clamp = MathUtils.clamp(i2, 0, itemCount - 1);
            }
            i2 = positionForSection;
            i = i6;
            i3 = i5 + 1;
            while (i3 < length) {
                i3++;
                i5++;
            }
            f2 = i6 / f3;
            float f42 = i5 / f3;
            if (itemCount != 0) {
            }
            if (i6 == clamp2) {
            }
            i2 += (int) (((f - f2) * (positionForSection2 - i2)) / (f42 - f2));
            clamp = MathUtils.clamp(i2, 0, itemCount - 1);
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.mLayout;
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(clamp, 0);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager) layoutManager).scrollToPositionWithOffset(clamp, true);
        }
        int findFirstVisibleItemPosition = recyclerView.findFirstVisibleItemPosition();
        int childCount = recyclerView.getChildCount();
        RecyclerView.Adapter adapter2 = recyclerView.mAdapter;
        onScroll(findFirstVisibleItemPosition, childCount, adapter2 != null ? adapter2.getItemCount() : 0);
        this.mCurrentSection = i;
        boolean transitionPreviewLayout = transitionPreviewLayout(i);
        Log.d("SeslFastScroller", "scrollTo() called transitionPreviewLayout() sectionIndex =" + i + ", position = " + f);
        boolean z = this.mShowingPreview;
        if (z || !transitionPreviewLayout) {
            if (!z || transitionPreviewLayout) {
                return;
            }
            transitionToVisible();
            return;
        }
        AnimatorSet animatorSet = this.mDecorAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        Animator duration = groupAnimatorOfFloat(View.ALPHA, 1.0f, this.mThumbImage, this.mTrackImage, this.mPreviewImage).setDuration(167L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mDecorAnimation = animatorSet2;
        animatorSet2.playTogether(duration);
        this.mDecorAnimation.setInterpolator(SeslAnimationUtils.SINE_IN_OUT_70);
        this.mDecorAnimation.start();
        this.mShowingPreview = true;
    }

    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v3 */
    public final void setScrollbarPosition(int i) {
        RecyclerView.LayoutManager layoutManager = this.mRecyclerView.mLayout;
        if (i == 0 && layoutManager != null) {
            i = layoutManager.getLayoutDirection() == 1 ? 1 : 2;
        }
        if (this.mScrollbarPosition != i) {
            this.mScrollbarPosition = i;
            ?? r1 = i == 1 ? 0 : 1;
            this.mLayoutFromRight = r1;
            int i2 = this.mPreviewResId[r1];
            View view = this.mPreviewImage;
            view.setBackgroundResource(i2);
            view.getBackground().setTintMode(PorterDuff.Mode.MULTIPLY);
            view.getBackground().setTint(this.mColorPrimary);
            updateLayout();
        }
    }

    public final void setState(int i) {
        int i2;
        this.mRecyclerView.removeCallbacks(this.mDeferHide);
        if (i == this.mState) {
            return;
        }
        ImageView imageView = this.mThumbImage;
        ImageView imageView2 = this.mTrackImage;
        if (i != 0) {
            Drawable drawable = this.mThumbDrawable;
            if (i == 1) {
                if (drawable != null) {
                    drawable.setTint(this.mThumbBackgroundColor);
                }
                transitionToVisible();
            } else if (i == 2) {
                if (drawable != null) {
                    drawable.setTint(this.mColorPrimary);
                }
                transitionPreviewLayout(this.mCurrentSection);
            }
        } else {
            this.mShowingPreview = false;
            this.mCurrentSection = -1;
            AnimatorSet animatorSet = this.mDecorAnimation;
            if (animatorSet != null) {
                animatorSet.cancel();
                i2 = 150;
            } else {
                i2 = 0;
            }
            Animator duration = groupAnimatorOfFloat(View.ALPHA, 0.0f, imageView, imageView2, this.mPreviewImage, this.mPrimaryText, this.mSecondaryText).setDuration(i2);
            AnimatorSet animatorSet2 = new AnimatorSet();
            this.mDecorAnimation = animatorSet2;
            animatorSet2.playTogether(duration);
            this.mDecorAnimation.setInterpolator(LINEAR_INTERPOLATOR);
            this.mDecorAnimation.start();
        }
        this.mState = i;
        boolean z = i == 2;
        imageView.setPressed(z);
        imageView2.setPressed(z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0011, code lost:
    
        if (r6 < 0.0f) goto L4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setThumbPos(float f) {
        Rect rect = this.mContainerRect;
        int i = rect.top;
        int i2 = rect.bottom;
        float f2 = f <= 1.0f ? 0.0f : 1.0f;
        f = f2;
        float f3 = (f * this.mThumbRange) + this.mThumbOffset;
        this.mThumbImage.setTranslationY(f3 - (r2.getHeight() / 2.0f));
        View view = this.mPreviewImage;
        float height = view.getHeight() / 2.0f;
        float clamp = MathUtils.clamp(f3, i + height, i2 - height) - height;
        view.setTranslationY(clamp);
        this.mPrimaryText.setTranslationY(clamp);
        this.mSecondaryText.setTranslationY(clamp);
    }

    public final boolean transitionPreviewLayout(int i) {
        boolean isEmpty;
        Object obj;
        Object[] objArr = this.mSections;
        String obj2 = (objArr == null || i < 0 || i >= objArr.length || (obj = objArr[i]) == null) ? null : obj.toString();
        boolean z = this.mShowingPrimary;
        TextView textView = this.mPrimaryText;
        TextView textView2 = this.mSecondaryText;
        if (z) {
            textView2 = textView;
            textView = textView2;
        }
        textView.setText(obj2);
        Rect rect = this.mTempBounds;
        measurePreview(rect, textView);
        applyLayout(rect, textView);
        int i2 = this.mState;
        if (i2 == 1) {
            textView2.setText("");
        } else if (i2 == 2 && textView2.getText().equals(obj2) && textView2.getAlpha() != 0.0f) {
            isEmpty = TextUtils.isEmpty(obj2);
            return !isEmpty;
        }
        AnimatorSet animatorSet = this.mPreviewAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        if (!textView2.getText().equals("")) {
            this.mRecyclerView.performHapticFeedback(this.mVibrateIndex);
        }
        Animator duration = ObjectAnimator.ofFloat(textView, (Property<TextView, Float>) View.ALPHA, 1.0f).setDuration(0L);
        Animator duration2 = ObjectAnimator.ofFloat(textView2, (Property<TextView, Float>) View.ALPHA, 0.0f).setDuration(0L);
        duration2.addListener(this.mSwitchPrimaryListener);
        int i3 = rect.left;
        View view = this.mPreviewImage;
        rect.left = i3 - view.getPaddingLeft();
        rect.top -= view.getPaddingTop();
        rect.right = view.getPaddingRight() + rect.right;
        rect.bottom = view.getPaddingBottom() + rect.bottom;
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofInt(LEFT, rect.left), PropertyValuesHolder.ofInt(TOP, rect.top), PropertyValuesHolder.ofInt(RIGHT, rect.right), PropertyValuesHolder.ofInt(BOTTOM, rect.bottom));
        ofPropertyValuesHolder.setDuration(100L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mPreviewAnimation = animatorSet2;
        AnimatorSet.Builder with = animatorSet2.play(duration2).with(duration);
        with.with(ofPropertyValuesHolder);
        int width = (view.getWidth() - view.getPaddingLeft()) - view.getPaddingRight();
        int width2 = textView.getWidth();
        if (width2 > width) {
            textView.setScaleX(width / width2);
            with.with(ObjectAnimator.ofFloat(textView, (Property<TextView, Float>) View.SCALE_X, 1.0f).setDuration(100L));
        } else {
            textView.setScaleX(1.0f);
        }
        int width3 = textView2.getWidth();
        if (width3 > width2) {
            with.with(ObjectAnimator.ofFloat(textView2, (Property<TextView, Float>) View.SCALE_X, width2 / width3).setDuration(100L));
        }
        this.mPreviewAnimation.setInterpolator(SeslAnimationUtils.SINE_IN_OUT_70);
        this.mPreviewAnimation.start();
        isEmpty = TextUtils.isEmpty(obj2);
        return !isEmpty;
    }

    public final void transitionToVisible() {
        AnimatorSet animatorSet = this.mDecorAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        Animator duration = groupAnimatorOfFloat(View.ALPHA, 1.0f, this.mThumbImage, this.mTrackImage).setDuration(167L);
        Animator duration2 = groupAnimatorOfFloat(View.ALPHA, 0.0f, this.mPreviewImage, this.mPrimaryText, this.mSecondaryText).setDuration(150L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mDecorAnimation = animatorSet2;
        animatorSet2.playTogether(duration, duration2);
        this.mDecorAnimation.setInterpolator(SeslAnimationUtils.SINE_IN_OUT_70);
        this.mShowingPreview = false;
        this.mDecorAnimation.start();
    }

    public final void updateLayout() {
        int i;
        int i2;
        if (this.mUpdatingLayout) {
            return;
        }
        this.mUpdatingLayout = true;
        Rect rect = this.mContainerRect;
        rect.left = 0;
        rect.top = 0;
        RecyclerView recyclerView = this.mRecyclerView;
        rect.right = recyclerView.getWidth();
        rect.bottom = recyclerView.getHeight();
        int i3 = this.mScrollBarStyle;
        if (i3 == 16777216 || i3 == 0) {
            rect.left = recyclerView.getPaddingLeft() + rect.left;
            rect.top = recyclerView.getPaddingTop() + rect.top;
            rect.right -= recyclerView.getPaddingRight();
            rect.bottom -= recyclerView.getPaddingBottom();
            if (i3 == 16777216) {
                int i4 = this.mWidth;
                if (this.mScrollbarPosition == 2) {
                    rect.right += i4;
                } else {
                    rect.left -= i4;
                }
            }
        }
        ImageView imageView = this.mThumbImage;
        Rect rect2 = this.mTempBounds;
        measureViewToSide(imageView, null, rect2);
        applyLayout(rect2, imageView);
        int max = Math.max(0, rect.width());
        int max2 = Math.max(0, rect.height());
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(max, VideoPlayer.MEDIA_ERROR_SYSTEM);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(max2), 0);
        ImageView imageView2 = this.mTrackImage;
        imageView2.measure(makeMeasureSpec, makeMeasureSpec2);
        int i5 = this.mThumbPosition;
        int i6 = this.mTrackBottomPadding;
        int i7 = this.mTrackTopPadding;
        if (i5 == 1) {
            i2 = rect.top + i7 + 0;
            i = (rect.bottom - i6) - 0;
        } else {
            int height = imageView.getHeight() / 2;
            int i8 = rect.top + height + i7 + 0;
            i = ((rect.bottom - height) - i6) - 0;
            i2 = i8;
        }
        if (i < i2) {
            Log.e("SeslFastScroller", SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("Error occured during layoutTrack() because bottom[", i, "] is less than top[", i, "]."));
            i = i2;
        }
        int measuredWidth = imageView2.getMeasuredWidth();
        int width = ((imageView.getWidth() - measuredWidth) / 2) + imageView.getLeft();
        imageView2.layout(width, i2, measuredWidth + width, i);
        updateOffsetAndRange();
        this.mUpdatingLayout = false;
        TextView textView = this.mPrimaryText;
        measurePreview(rect2, textView);
        applyLayout(rect2, textView);
        TextView textView2 = this.mSecondaryText;
        measurePreview(rect2, textView2);
        applyLayout(rect2, textView2);
        int i9 = rect2.left;
        View view = this.mPreviewImage;
        rect2.left = i9 - view.getPaddingLeft();
        rect2.top -= view.getPaddingTop();
        rect2.right = view.getPaddingRight() + rect2.right;
        rect2.bottom = view.getPaddingBottom() + rect2.bottom;
        applyLayout(rect2, view);
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x000e, code lost:
    
        if (canScrollList(-1) == false) goto L8;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateLongList(int i) {
        boolean z;
        if (i > 0) {
            z = true;
            if (!canScrollList(1)) {
            }
            if (this.mLongList == z) {
                this.mLongList = z;
                onStateDependencyChanged();
                return;
            }
            return;
        }
        z = false;
        if (this.mLongList == z) {
        }
    }

    public final void updateOffsetAndRange() {
        float top;
        float bottom;
        int i = this.mThumbPosition;
        ImageView imageView = this.mTrackImage;
        if (i == 1) {
            float height = this.mThumbImage.getHeight() / 2.0f;
            top = imageView.getTop() + height;
            bottom = imageView.getBottom() - height;
        } else {
            top = imageView.getTop();
            bottom = imageView.getBottom();
        }
        this.mThumbOffset = top;
        float f = (bottom - top) - this.mImmersiveBottomPadding;
        this.mThumbRange = f;
        if (f < 0.0f) {
            this.mThumbRange = 0.0f;
        }
    }
}
