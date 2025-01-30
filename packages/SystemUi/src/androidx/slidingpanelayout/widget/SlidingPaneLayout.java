package androidx.slidingpanelayout.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import androidx.slidingpanelayout.R$styleable;
import com.android.systemui.R;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SlidingPaneLayout extends ViewGroup {
    public boolean mCanSlide;
    public int mDoubleCheckState;
    public final ViewDragHelper mDragHelper;
    public final boolean mDrawRoundedCorner;
    public View mDrawerPanel;
    public boolean mFirstLayout;
    public int mFixedPaneStartX;
    public float mInitialMotionX;
    public float mInitialMotionY;
    public boolean mIsAnimate;
    public boolean mIsLock;
    public boolean mIsNeedClose;
    public boolean mIsNeedOpen;
    public final boolean mIsSinglePanel;
    public boolean mIsSlideableViewTouched;
    public boolean mIsUnableToDrag;
    public int mLastValidVelocity;
    public final int mMarginBottom;
    public final int mMarginTop;
    public final int mOverhangSize;
    public int mPendingAction;
    public final ArrayList mPostedRunnables;
    public final TypedValue mPrefContentWidth;
    public final TypedValue mPrefDrawerWidth;
    public boolean mPreservedOpenState;
    public float mPrevMotionX;
    public int mPrevOrientation;
    public int mPrevWindowVisibility;
    public View mResizeChild;
    public final boolean mResizeOff;
    public final int mRoundedColor;
    public float mSlideOffset;
    public int mSlideRange;
    public View mSlideableView;
    public final int mSliderFadeColor;
    public final int mSlidingPaneDragArea;
    public final SlidingPaneRoundedCorner mSlidingPaneRoundedCorner;
    public final SeslSlidingState mSlidingState;
    public int mSmoothWidth;
    public int mStartMargin;
    public float mStartOffset;
    public int mStartSlideX;
    public final Rect mTmpRect;
    public final int mUserPreferredContentSize;
    public final int mUserPreferredDrawerSize;
    public VelocityTracker mVelocityTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AccessibilityDelegate extends AccessibilityDelegateCompat {
        public final Rect mTmpRect = new Rect();

        public AccessibilityDelegate() {
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName("androidx.slidingpanelayout.widget.SlidingPaneLayout");
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            AccessibilityNodeInfoCompat obtain = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat);
            View.AccessibilityDelegate accessibilityDelegate = this.mOriginalDelegate;
            AccessibilityNodeInfo accessibilityNodeInfo = obtain.mInfo;
            accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            Rect rect = this.mTmpRect;
            accessibilityNodeInfo.getBoundsInScreen(rect);
            AccessibilityNodeInfo accessibilityNodeInfo2 = accessibilityNodeInfoCompat.mInfo;
            accessibilityNodeInfo2.setBoundsInScreen(rect);
            accessibilityNodeInfo2.setVisibleToUser(accessibilityNodeInfo.isVisibleToUser());
            accessibilityNodeInfo2.setPackageName(accessibilityNodeInfo.getPackageName());
            accessibilityNodeInfoCompat.setClassName(accessibilityNodeInfo.getClassName());
            accessibilityNodeInfoCompat.setContentDescription(accessibilityNodeInfo.getContentDescription());
            accessibilityNodeInfo2.setEnabled(accessibilityNodeInfo.isEnabled());
            accessibilityNodeInfoCompat.setClickable(accessibilityNodeInfo.isClickable());
            accessibilityNodeInfo2.setFocusable(accessibilityNodeInfo.isFocusable());
            accessibilityNodeInfo2.setFocused(accessibilityNodeInfo.isFocused());
            accessibilityNodeInfo2.setAccessibilityFocused(accessibilityNodeInfo.isAccessibilityFocused());
            accessibilityNodeInfoCompat.setSelected(accessibilityNodeInfo.isSelected());
            accessibilityNodeInfo2.setLongClickable(accessibilityNodeInfo.isLongClickable());
            accessibilityNodeInfoCompat.addAction(accessibilityNodeInfo.getActions());
            accessibilityNodeInfo2.setMovementGranularities(accessibilityNodeInfo.getMovementGranularities());
            accessibilityNodeInfo.recycle();
            accessibilityNodeInfoCompat.setClassName("androidx.slidingpanelayout.widget.SlidingPaneLayout");
            accessibilityNodeInfoCompat.mVirtualDescendantId = -1;
            accessibilityNodeInfo2.setSource(view);
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            Object parentForAccessibility = ViewCompat.Api16Impl.getParentForAccessibility(view);
            if (parentForAccessibility instanceof View) {
                accessibilityNodeInfoCompat.mParentVirtualDescendantId = -1;
                accessibilityNodeInfo2.setParent((View) parentForAccessibility);
            }
            SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
            int childCount = slidingPaneLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = slidingPaneLayout.getChildAt(i);
                if (!slidingPaneLayout.isDimmed(childAt) && childAt.getVisibility() == 0) {
                    ViewCompat.Api16Impl.setImportantForAccessibility(childAt, 1);
                    accessibilityNodeInfo2.addChild(childAt);
                }
            }
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
            int dimensionPixelSize = slidingPaneLayout.getResources().getDimensionPixelSize(R.dimen.sesl_sliding_pane_contents_drag_width_default);
            boolean z = true;
            if (slidingPaneLayout.mSlideOffset != 0.0f || slidingPaneLayout.mStartMargin >= dimensionPixelSize) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.setImportantForAccessibility(view, 1);
            } else {
                View view2 = slidingPaneLayout.mDrawerPanel;
                if (view != view2) {
                    if (view2 instanceof ViewGroup) {
                        ViewGroup viewGroup2 = (ViewGroup) view2;
                        int childCount = viewGroup2.getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            if (view == viewGroup2.getChildAt(i)) {
                                break;
                            }
                        }
                    }
                    z = false;
                }
                if (z) {
                    WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api16Impl.setImportantForAccessibility(view, 4);
                }
            }
            if (slidingPaneLayout.isDimmed(view)) {
                return false;
            }
            return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DisableLayerRunnable implements Runnable {
        public final View mChildView;

        public DisableLayerRunnable(View view) {
            this.mChildView = view;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (this.mChildView.getParent() == SlidingPaneLayout.this) {
                this.mChildView.setLayerType(0, null);
                SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
                View view = this.mChildView;
                slidingPaneLayout.getClass();
                Paint paint = ((LayoutParams) view.getLayoutParams()).dimPaint;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api17Impl.setLayerPaint(view, paint);
            }
            SlidingPaneLayout.this.mPostedRunnables.remove(this);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DragHelperCallback extends ViewDragHelper.Callback {
        public DragHelperCallback() {
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final int clampViewPositionHorizontal(View view, int i) {
            SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
            LayoutParams layoutParams = (LayoutParams) slidingPaneLayout.mSlideableView.getLayoutParams();
            if (!slidingPaneLayout.isLayoutRtlSupport()) {
                int paddingLeft = slidingPaneLayout.getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
                return Math.min(Math.max(i, paddingLeft), slidingPaneLayout.mSlideRange + paddingLeft);
            }
            int width = slidingPaneLayout.getWidth() - (slidingPaneLayout.mSlideableView.getWidth() + (slidingPaneLayout.getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin));
            return Math.max(Math.min(i, width), width - slidingPaneLayout.mSlideRange);
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final int clampViewPositionVertical(View view, int i) {
            return view.getTop();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final int getViewHorizontalDragRange(View view) {
            return SlidingPaneLayout.this.mSlideRange;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final void onEdgeDragStarted(int i, int i2) {
            SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
            slidingPaneLayout.mDragHelper.captureChildView(slidingPaneLayout.mSlideableView, i2);
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final void onViewCaptured(View view, int i) {
            SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
            int childCount = slidingPaneLayout.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = slidingPaneLayout.getChildAt(i2);
                if (childAt.getVisibility() == 4) {
                    childAt.setVisibility(0);
                }
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final void onViewDragStateChanged(int i) {
            SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
            if (slidingPaneLayout.mDragHelper.mDragState == 0) {
                slidingPaneLayout.mIsAnimate = false;
                float f = slidingPaneLayout.mSlideOffset;
                if (f != 0.0f) {
                    slidingPaneLayout.mStartOffset = f;
                    slidingPaneLayout.sendAccessibilityEvent(32);
                    slidingPaneLayout.mPreservedOpenState = true;
                } else {
                    slidingPaneLayout.updateObscuredViewsVisibility(slidingPaneLayout.mSlideableView);
                    slidingPaneLayout.mStartOffset = slidingPaneLayout.mSlideOffset;
                    slidingPaneLayout.sendAccessibilityEvent(32);
                    slidingPaneLayout.mPreservedOpenState = false;
                }
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final void onViewPositionChanged(View view, int i, int i2, int i3) {
            SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
            float f = slidingPaneLayout.mStartOffset;
            if (f != 0.0f || slidingPaneLayout.mLastValidVelocity <= 0 || slidingPaneLayout.mSlideOffset <= 0.2f) {
                if (f == 1.0f && slidingPaneLayout.mLastValidVelocity < 0 && slidingPaneLayout.mSlideOffset < 0.8f && i3 > 0) {
                    return;
                }
            } else if (i3 < 0) {
                return;
            }
            slidingPaneLayout.onPanelDragged(i);
            slidingPaneLayout.invalidate();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final void onViewReleased(View view, float f, float f2) {
            int paddingLeft;
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
            if (slidingPaneLayout.isLayoutRtlSupport()) {
                int paddingRight = slidingPaneLayout.getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
                if (f < 0.0f || (f == 0.0f && slidingPaneLayout.mSlideOffset > 0.5f)) {
                    paddingRight += slidingPaneLayout.mSlideRange;
                }
                paddingLeft = (slidingPaneLayout.getWidth() - paddingRight) - slidingPaneLayout.mSlideableView.getWidth();
            } else {
                paddingLeft = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + slidingPaneLayout.getPaddingLeft();
                if (f > 0.0f || (f == 0.0f && slidingPaneLayout.mSlideOffset > 0.5f)) {
                    paddingLeft += slidingPaneLayout.mSlideRange;
                }
            }
            slidingPaneLayout.mDragHelper.settleCapturedViewAt(paddingLeft, view.getTop());
            slidingPaneLayout.invalidate();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final boolean tryCaptureView(View view, int i) {
            if (SlidingPaneLayout.this.mIsUnableToDrag) {
                return false;
            }
            return ((LayoutParams) view.getLayoutParams()).slideable;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: androidx.slidingpanelayout.widget.SlidingPaneLayout.SavedState.1
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, null);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }
        };
        public boolean isOpen;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mSuperState, i);
            parcel.writeInt(this.isOpen ? 1 : 0);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.isOpen = parcel.readInt() != 0;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SeslSlidingState {
        public int mCurrentState = 2;
    }

    public SlidingPaneLayout(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public final boolean closePane(boolean z) {
        if (this.mIsAnimate) {
            return true;
        }
        if (this.mSlideableView == null || this.mIsLock) {
            return false;
        }
        if (z) {
            if (!this.mFirstLayout && !smoothSlideTo(0.0f)) {
                return false;
            }
            this.mPreservedOpenState = false;
            return true;
        }
        onPanelDragged(isLayoutRtlSupport() ? this.mSlideRange : this.mStartMargin);
        if (this.mResizeOff) {
            resizeSlideableView(0.0f);
            if (isLayoutRtlSupport()) {
                this.mSlideableView.setRight(getWindowWidth() - this.mStartMargin);
                View view = this.mSlideableView;
                view.setLeft((view.getRight() - getWindowWidth()) + this.mStartMargin);
            } else {
                this.mSlideableView.setLeft(isLayoutRtlSupport() ? this.mSlideRange : this.mStartMargin);
            }
        } else {
            resizeSlideableView(0.0f);
        }
        this.mPreservedOpenState = false;
        return true;
    }

    @Override // android.view.View
    public final void computeScroll() {
        if (this.mDragHelper.continueSettling()) {
            if (!this.mCanSlide) {
                this.mDragHelper.abort();
            } else {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
            }
        }
    }

    public final void dimChildView(float f, int i, View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (f <= 0.0f || i == 0) {
            if (view.getLayerType() != 0) {
                Paint paint = layoutParams.dimPaint;
                if (paint != null) {
                    paint.setColorFilter(null);
                }
                DisableLayerRunnable disableLayerRunnable = new DisableLayerRunnable(view);
                this.mPostedRunnables.add(disableLayerRunnable);
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postOnAnimation(this, disableLayerRunnable);
                return;
            }
            return;
        }
        int i2 = (((int) ((((-16777216) & i) >>> 24) * f)) << 24) | (16777215 & i);
        if (layoutParams.dimPaint == null) {
            layoutParams.dimPaint = new Paint();
        }
        layoutParams.dimPaint.setColorFilter(new PorterDuffColorFilter(i2, PorterDuff.Mode.SRC_OVER));
        if (view.getLayerType() != 2) {
            view.setLayerType(2, layoutParams.dimPaint);
        }
        Paint paint2 = ((LayoutParams) view.getLayoutParams()).dimPaint;
        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api17Impl.setLayerPaint(view, paint2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        int left;
        int top;
        super.dispatchDraw(canvas);
        if (!this.mDrawRoundedCorner || this.mSlideableView == null) {
            return;
        }
        SlidingPaneRoundedCorner slidingPaneRoundedCorner = this.mSlidingPaneRoundedCorner;
        int i = this.mRoundedColor;
        if (slidingPaneRoundedCorner.mStartTopDrawable == null || slidingPaneRoundedCorner.mStartBottomDrawable == null || slidingPaneRoundedCorner.mEndTopDrawable == null || slidingPaneRoundedCorner.mEndBottomDrawable == null) {
            slidingPaneRoundedCorner.initRoundedCorner();
        }
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_IN);
        slidingPaneRoundedCorner.mStartTopDrawable.setColorFilter(porterDuffColorFilter);
        slidingPaneRoundedCorner.mEndTopDrawable.setColorFilter(porterDuffColorFilter);
        slidingPaneRoundedCorner.mEndBottomDrawable.setColorFilter(porterDuffColorFilter);
        slidingPaneRoundedCorner.mStartBottomDrawable.setColorFilter(porterDuffColorFilter);
        SlidingPaneRoundedCorner slidingPaneRoundedCorner2 = this.mSlidingPaneRoundedCorner;
        View view = this.mSlideableView;
        slidingPaneRoundedCorner2.getClass();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api17Impl.getLayoutDirection(view) == 1) {
            slidingPaneRoundedCorner2.mRoundedCornerMode = 1;
        } else {
            slidingPaneRoundedCorner2.mRoundedCornerMode = 0;
        }
        if (view.getTranslationY() != 0.0f) {
            left = Math.round(view.getX());
            top = Math.round(view.getY());
        } else {
            left = view.getLeft();
            top = view.getTop();
        }
        int i2 = slidingPaneRoundedCorner2.mMarginTop + top;
        int width = view.getWidth() + left + slidingPaneRoundedCorner2.mRoundRadius;
        int height = (view.getHeight() + top) - slidingPaneRoundedCorner2.mMarginBottom;
        Rect rect = slidingPaneRoundedCorner2.mTmpRect;
        canvas.getClipBounds(rect);
        rect.right = Math.max(rect.left, view.getRight() + slidingPaneRoundedCorner2.mRoundRadius);
        canvas.clipRect(rect);
        Rect rect2 = slidingPaneRoundedCorner2.mRoundedCornerBounds;
        rect2.set(left, i2, width, height);
        int i3 = rect2.left;
        int i4 = rect2.right;
        int i5 = rect2.top;
        int i6 = rect2.bottom;
        if (slidingPaneRoundedCorner2.mRoundedCornerMode == 0) {
            Drawable drawable = slidingPaneRoundedCorner2.mStartTopDrawable;
            int i7 = slidingPaneRoundedCorner2.mRoundRadius;
            drawable.setBounds(i3 - i7, i5, i3, i7 + i5);
            slidingPaneRoundedCorner2.mStartTopDrawable.draw(canvas);
            Drawable drawable2 = slidingPaneRoundedCorner2.mStartBottomDrawable;
            int i8 = slidingPaneRoundedCorner2.mRoundRadius;
            drawable2.setBounds(i3 - i8, i6 - i8, i3, i6);
            slidingPaneRoundedCorner2.mStartBottomDrawable.draw(canvas);
            return;
        }
        Drawable drawable3 = slidingPaneRoundedCorner2.mEndTopDrawable;
        int i9 = slidingPaneRoundedCorner2.mRoundRadius;
        drawable3.setBounds(i4 - i9, i5, i4, i9 + i5);
        slidingPaneRoundedCorner2.mEndTopDrawable.draw(canvas);
        Drawable drawable4 = slidingPaneRoundedCorner2.mEndBottomDrawable;
        int i10 = slidingPaneRoundedCorner2.mRoundRadius;
        drawable4.setBounds(i4 - i10, i6 - i10, i4, i6);
        slidingPaneRoundedCorner2.mEndBottomDrawable.draw(canvas);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        isLayoutRtlSupport();
        if (getChildCount() > 1) {
            getChildAt(1);
        }
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int save = canvas.save();
        if (this.mCanSlide && !layoutParams.slideable && this.mSlideableView != null) {
            canvas.getClipBounds(this.mTmpRect);
            if (isLayoutRtlSupport()) {
                Rect rect = this.mTmpRect;
                rect.left = Math.max(rect.left, this.mSlideableView.getRight());
            } else {
                Rect rect2 = this.mTmpRect;
                rect2.right = Math.min(rect2.right, this.mSlideableView.getLeft());
            }
            canvas.clipRect(this.mTmpRect);
        }
        boolean drawChild = super.drawChild(canvas, view, j);
        canvas.restoreToCount(save);
        return drawChild;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    public final int getWindowWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    public final boolean isDimmed(View view) {
        if (view == null) {
            return false;
        }
        return this.mCanSlide && ((LayoutParams) view.getLayoutParams()).dimWhenOffset && this.mSlideOffset > 0.0f;
    }

    public final boolean isLayoutRtlSupport() {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        return ViewCompat.Api17Impl.getLayoutDirection(this) == 1;
    }

    public final boolean isOpen() {
        return !this.mCanSlide || this.mSlideOffset == 1.0f;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onConfigurationChanged(Configuration configuration) {
        int i;
        float dimension;
        super.onConfigurationChanged(configuration);
        if (!isOpen() || (configuration.orientation == 1 && this.mPrevOrientation == 2)) {
            this.mPendingAction = 2;
        } else {
            this.mPendingAction = 1;
        }
        if (this.mIsLock) {
            if (isOpen()) {
                this.mPendingAction = 1;
            } else {
                this.mPendingAction = 2;
            }
        }
        this.mPrevOrientation = configuration.orientation;
        if (this.mDrawerPanel == null) {
            Log.e("SeslSlidingPaneLayout", "mDrawerPanel is null");
            return;
        }
        TypedValue typedValue = new TypedValue();
        getResources().getValue(R.dimen.sesl_sliding_pane_drawer_width, typedValue, true);
        int i2 = typedValue.type;
        if (i2 == 4) {
            dimension = typedValue.getFloat() * getWindowWidth();
        } else {
            if (i2 != 5) {
                i = -1;
                if (i == -1) {
                    ViewGroup.LayoutParams layoutParams = this.mDrawerPanel.getLayoutParams();
                    layoutParams.width = i;
                    this.mDrawerPanel.setLayoutParams(layoutParams);
                    return;
                }
                return;
            }
            dimension = typedValue.getDimension(getResources().getDisplayMetrics());
        }
        i = (int) dimension;
        if (i == -1) {
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
        int size = this.mPostedRunnables.size();
        for (int i = 0; i < size; i++) {
            ((DisableLayerRunnable) this.mPostedRunnables.get(i)).run();
        }
        this.mPostedRunnables.clear();
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x001e, code lost:
    
        if (r0 != 3) goto L67;
     */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        View childAt;
        int actionMasked = motionEvent.getActionMasked();
        if (!this.mCanSlide || this.mIsLock || (this.mIsUnableToDrag && actionMasked != 0)) {
            this.mDragHelper.cancel();
            return super.onInterceptTouchEvent(motionEvent);
        }
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    float abs = Math.abs(x - this.mInitialMotionX);
                    Math.abs(y - this.mInitialMotionY);
                    int i = this.mDragHelper.mTouchSlop;
                    float f = this.mPrevMotionX;
                    float f2 = x - f;
                    if (f != x) {
                        this.mPrevMotionX = x;
                    }
                    if (!this.mIsUnableToDrag && abs > i) {
                        if (!isLayoutRtlSupport()) {
                            f2 = Math.max(this.mSlideableView.getLeft() + f2, this.mStartMargin);
                        } else if (this.mResizeOff) {
                            f2 = (this.mSlideableView.getRight() - getWindowWidth()) + this.mStartMargin;
                        }
                        onPanelDragged((int) f2);
                        return true;
                    }
                }
            }
            if (Math.abs(this.mStartOffset - this.mSlideOffset) < 0.1f) {
                return false;
            }
            this.mSmoothWidth = this.mSlideableView.getWidth();
            this.mDoubleCheckState = -1;
            if (!this.mIsAnimate) {
                float f3 = this.mSlideOffset;
                if (f3 != 0.0f && f3 != 1.0f) {
                    if (f3 >= 0.5f) {
                        this.mDoubleCheckState = 1;
                        this.mLastValidVelocity = 0;
                        this.mIsNeedOpen = true;
                        this.mIsNeedClose = false;
                        openPane(true);
                    } else {
                        this.mDoubleCheckState = 0;
                        this.mLastValidVelocity = 0;
                        this.mIsNeedOpen = false;
                        this.mIsNeedClose = true;
                        closePane(true);
                    }
                    return true;
                }
            }
        } else {
            this.mStartOffset = this.mSlideOffset;
            this.mIsNeedOpen = false;
            this.mIsNeedClose = false;
            this.mIsUnableToDrag = false;
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            this.mInitialMotionX = x2;
            this.mInitialMotionY = y2;
            this.mSmoothWidth = 0;
            this.mPrevMotionX = x2;
            int right = isLayoutRtlSupport() ? this.mSlideableView.getRight() : this.mSlideableView.getLeft();
            if (isLayoutRtlSupport()) {
                if (x2 < right - this.mSlidingPaneDragArea || this.mIsLock) {
                    this.mDragHelper.cancel();
                    this.mIsUnableToDrag = true;
                }
            } else if (x2 > right + this.mSlidingPaneDragArea || this.mIsLock) {
                this.mDragHelper.cancel();
                this.mIsUnableToDrag = true;
            }
            this.mDragHelper.getClass();
            boolean isViewUnder = ViewDragHelper.isViewUnder(this.mSlideableView, (int) x2, (int) y2);
            this.mIsSlideableViewTouched = isViewUnder;
            if (isViewUnder && isDimmed(this.mSlideableView)) {
                z = true;
                if (!this.mCanSlide && actionMasked == 0 && getChildCount() > 1 && (childAt = getChildAt(1)) != null) {
                    ViewDragHelper viewDragHelper = this.mDragHelper;
                    int x3 = (int) motionEvent.getX();
                    int y3 = (int) motionEvent.getY();
                    viewDragHelper.getClass();
                    this.mPreservedOpenState = !ViewDragHelper.isViewUnder(childAt, x3, y3);
                }
                if (actionMasked == 3 && actionMasked != 1) {
                    return this.mDragHelper.shouldInterceptTouchEvent(motionEvent) || z;
                }
                this.mDragHelper.cancel();
                return false;
            }
        }
        z = false;
        if (!this.mCanSlide) {
            ViewDragHelper viewDragHelper2 = this.mDragHelper;
            int x32 = (int) motionEvent.getX();
            int y32 = (int) motionEvent.getY();
            viewDragHelper2.getClass();
            this.mPreservedOpenState = !ViewDragHelper.isViewUnder(childAt, x32, y32);
        }
        if (actionMasked == 3) {
        }
        this.mDragHelper.cancel();
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:90:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:97:? A[RETURN, SYNTHETIC] */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        int i5;
        int i6;
        int i7;
        int right;
        boolean isLayoutRtlSupport = isLayoutRtlSupport();
        if (isLayoutRtlSupport) {
            this.mDragHelper.mTrackingEdges = 2;
        } else {
            this.mDragHelper.mTrackingEdges = 1;
        }
        int i8 = i3 - i;
        int paddingRight = isLayoutRtlSupport ? getPaddingRight() : getPaddingLeft();
        int paddingLeft = isLayoutRtlSupport ? getPaddingLeft() : getPaddingRight();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        if (this.mFirstLayout) {
            this.mSlideOffset = (this.mCanSlide && (this.mPreservedOpenState || this.mPendingAction == 1)) ? 1.0f : 0.0f;
        }
        int i9 = paddingRight;
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt = getChildAt(i10);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                if (layoutParams.slideable) {
                    int i11 = i8 - paddingLeft;
                    int min = (Math.min(paddingRight, i11 - this.mOverhangSize) - i9) - (((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin);
                    int i12 = isLayoutRtlSupport ? ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin : ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
                    this.mStartMargin = i12;
                    this.mSlideRange = min;
                    layoutParams.dimWhenOffset = (measuredWidth / 2) + ((i9 + i12) + min) > i11;
                    float f = min;
                    int i13 = (int) (this.mSlideOffset * f);
                    i6 = i12 + i13 + i9;
                    this.mSlideOffset = i13 / f;
                } else {
                    i6 = paddingRight;
                }
                if (isLayoutRtlSupport) {
                    right = (i8 - i6) + 0;
                    if (this.mResizeOff) {
                        if (layoutParams.slideable) {
                            i7 = right - (i8 - this.mStartMargin);
                            this.mFixedPaneStartX = 0;
                        }
                        i7 = right - measuredWidth;
                        this.mFixedPaneStartX = 0;
                    } else {
                        if (layoutParams.slideable) {
                            i7 = -getLeft();
                            this.mFixedPaneStartX = 0;
                        }
                        i7 = right - measuredWidth;
                        this.mFixedPaneStartX = 0;
                    }
                } else {
                    i7 = i6 + 0;
                    if (this.mResizeOff) {
                        if (layoutParams.slideable) {
                            right = (i8 - this.mStartMargin) + i7;
                            this.mFixedPaneStartX = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
                        }
                        right = measuredWidth + i7;
                        this.mFixedPaneStartX = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
                    } else {
                        if (layoutParams.slideable) {
                            right = getRight();
                            this.mFixedPaneStartX = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
                        }
                        right = measuredWidth + i7;
                        this.mFixedPaneStartX = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
                    }
                }
                this.mStartSlideX = isLayoutRtlSupport ? ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin : ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
                int measuredHeight = childAt.getMeasuredHeight() + paddingTop;
                if (layoutParams.slideable) {
                    childAt.layout(i7, paddingTop, right, measuredHeight);
                } else {
                    int i14 = this.mMarginTop;
                    childAt.layout(i7, paddingTop + i14, right, measuredHeight + i14);
                }
                paddingRight = childAt.getWidth() + paddingRight;
                i9 = i6;
            }
        }
        if (this.mFirstLayout) {
            if (!this.mCanSlide) {
                for (int i15 = 0; i15 < childCount; i15++) {
                    dimChildView(0.0f, this.mSliderFadeColor, getChildAt(i15));
                }
            } else if (((LayoutParams) this.mSlideableView.getLayoutParams()).dimWhenOffset) {
                dimChildView(this.mSlideOffset, this.mSliderFadeColor, this.mSlideableView);
            }
            updateObscuredViewsVisibility(this.mSlideableView);
        }
        this.mFirstLayout = false;
        int i16 = this.mPendingAction;
        if (i16 == 1) {
            if (this.mIsLock) {
                resizeSlideableView(1.0f);
            }
            openPane(false);
            this.mPendingAction = 0;
        } else {
            if (i16 != 2) {
                if (i16 == 257) {
                    this.mIsLock = false;
                    openPane(false);
                    z2 = true;
                    this.mIsLock = true;
                    this.mPendingAction = 0;
                } else {
                    z2 = true;
                    if (i16 == 258) {
                        this.mIsLock = false;
                        closePane(false);
                        this.mIsLock = true;
                        this.mPendingAction = 0;
                    }
                }
                updateSlidingState();
                i5 = this.mDoubleCheckState;
                if (i5 == -1) {
                    if (i5 == z2) {
                        openPane(z2);
                    } else if (i5 == 0) {
                        closePane(z2);
                    }
                    this.mDoubleCheckState = -1;
                    return;
                }
                return;
            }
            if (this.mIsLock) {
                resizeSlideableView(0.0f);
            }
            closePane(false);
            this.mPendingAction = 0;
        }
        z2 = true;
        updateSlidingState();
        i5 = this.mDoubleCheckState;
        if (i5 == -1) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x00aa, code lost:
    
        if (((android.view.ViewGroup.MarginLayoutParams) r7).width == 0) goto L34;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMeasure(int i, int i2) {
        int paddingTop;
        int i3;
        int i4;
        int makeMeasureSpec;
        int i5;
        int i6;
        int makeMeasureSpec2;
        int i7;
        int makeMeasureSpec3;
        int makeMeasureSpec4;
        float dimension;
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode != 1073741824) {
            if (!isInEditMode()) {
                throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
            }
            if (mode != Integer.MIN_VALUE && mode == 0) {
                size = 300;
            }
        } else if (mode2 == 0) {
            if (!isInEditMode()) {
                throw new IllegalStateException("Height must not be UNSPECIFIED");
            }
            size2 = 300;
            mode2 = Integer.MIN_VALUE;
        }
        boolean z = false;
        if (mode2 != Integer.MIN_VALUE) {
            i3 = mode2 != 1073741824 ? 0 : (size2 - getPaddingTop()) - getPaddingBottom();
            paddingTop = i3;
        } else {
            paddingTop = (size2 - getPaddingTop()) - getPaddingBottom();
            i3 = 0;
        }
        int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
        int childCount = getChildCount();
        if (childCount > 2) {
            Log.e("SeslSlidingPaneLayout", "onMeasure: More than two child views are not supported.");
        }
        this.mSlideableView = null;
        this.mDrawerPanel = null;
        int i8 = 0;
        boolean z2 = false;
        int i9 = paddingLeft;
        float f = 0.0f;
        while (true) {
            i4 = 8;
            if (i8 >= childCount) {
                break;
            }
            View childAt = getChildAt(i8);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (childAt.getVisibility() == 8) {
                layoutParams.dimWhenOffset = z;
            } else {
                float f2 = layoutParams.weight;
                if (f2 > 0.0f) {
                    f += f2;
                }
                int i10 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
                int i11 = ((ViewGroup.MarginLayoutParams) layoutParams).width;
                if (i11 != -2) {
                    i7 = childCount;
                    makeMeasureSpec3 = i11 == -1 ? View.MeasureSpec.makeMeasureSpec(paddingLeft - i10, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) : View.MeasureSpec.makeMeasureSpec(i11, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                } else if (layoutParams.slideable) {
                    makeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(paddingLeft - i10, VideoPlayer.MEDIA_ERROR_SYSTEM);
                    i7 = childCount;
                } else {
                    int i12 = this.mUserPreferredDrawerSize;
                    if (i12 != -1) {
                        i7 = childCount;
                    } else {
                        TypedValue typedValue = this.mPrefDrawerWidth;
                        if (typedValue != null) {
                            i7 = childCount;
                        } else {
                            typedValue = new TypedValue();
                            i7 = childCount;
                            getResources().getValue(R.dimen.sesl_sliding_pane_drawer_width, typedValue, true);
                        }
                        int i13 = typedValue.type;
                        if (i13 == 4) {
                            dimension = typedValue.getFloat() * getWindowWidth();
                        } else if (i13 == 5) {
                            dimension = typedValue.getDimension(getResources().getDisplayMetrics());
                        } else {
                            i12 = size;
                        }
                        i12 = (int) dimension;
                    }
                    if (i12 > size) {
                        i12 = size;
                    }
                    makeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(i12, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                }
                int i14 = ((ViewGroup.MarginLayoutParams) layoutParams).height;
                if (i14 == -2) {
                    makeMeasureSpec4 = View.MeasureSpec.makeMeasureSpec(layoutParams.slideable ? paddingTop : (paddingTop - this.mMarginTop) - this.mMarginBottom, VideoPlayer.MEDIA_ERROR_SYSTEM);
                } else if (i14 == -1) {
                    makeMeasureSpec4 = View.MeasureSpec.makeMeasureSpec(layoutParams.slideable ? paddingTop : (paddingTop - this.mMarginTop) - this.mMarginBottom, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                } else {
                    makeMeasureSpec4 = View.MeasureSpec.makeMeasureSpec(i14, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                }
                childAt.measure(makeMeasureSpec3, makeMeasureSpec4);
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                if (mode2 == Integer.MIN_VALUE && measuredHeight > i3) {
                    i3 = Math.min(measuredHeight, paddingTop);
                }
                i9 -= measuredWidth;
                boolean z3 = i9 < 0;
                layoutParams.slideable = z3;
                z2 |= z3;
                if (z3) {
                    this.mSlideableView = childAt;
                } else {
                    this.mDrawerPanel = childAt;
                }
                i8++;
                childCount = i7;
                z = false;
            }
            i7 = childCount;
            i8++;
            childCount = i7;
            z = false;
        }
        int i15 = childCount;
        if (z2 || f > 0.0f) {
            int i16 = paddingLeft - this.mOverhangSize;
            int i17 = 0;
            while (i17 < i15) {
                View childAt2 = getChildAt(i17);
                if (childAt2.getVisibility() != i4) {
                    LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                    if (childAt2.getVisibility() != i4) {
                        boolean z4 = ((ViewGroup.MarginLayoutParams) layoutParams2).width == 0 && layoutParams2.weight > 0.0f;
                        int measuredWidth2 = z4 ? 0 : childAt2.getMeasuredWidth();
                        if (!z2 || childAt2 == this.mSlideableView) {
                            if (layoutParams2.weight > 0.0f) {
                                if (((ViewGroup.MarginLayoutParams) layoutParams2).width == 0) {
                                    int i18 = ((ViewGroup.MarginLayoutParams) layoutParams2).height;
                                    makeMeasureSpec = i18 == -2 ? View.MeasureSpec.makeMeasureSpec(paddingTop, VideoPlayer.MEDIA_ERROR_SYSTEM) : i18 == -1 ? View.MeasureSpec.makeMeasureSpec(paddingTop, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) : View.MeasureSpec.makeMeasureSpec(i18, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                                } else {
                                    makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(childAt2.getMeasuredHeight(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                                }
                                if (z2) {
                                    int i19 = paddingLeft - (((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin);
                                    i5 = i16;
                                    int makeMeasureSpec5 = View.MeasureSpec.makeMeasureSpec(i19, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                                    if (measuredWidth2 != i19) {
                                        childAt2.measure(makeMeasureSpec5, makeMeasureSpec);
                                    }
                                    i17++;
                                    i16 = i5;
                                    i4 = 8;
                                } else {
                                    i5 = i16;
                                    childAt2.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth2 + ((int) ((layoutParams2.weight * Math.max(0, i9)) / f)), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), makeMeasureSpec);
                                    i17++;
                                    i16 = i5;
                                    i4 = 8;
                                }
                            }
                        } else if (((ViewGroup.MarginLayoutParams) layoutParams2).width < 0 && (measuredWidth2 > i16 || layoutParams2.weight > 0.0f)) {
                            if (z4) {
                                int i20 = ((ViewGroup.MarginLayoutParams) layoutParams2).height;
                                if (i20 == -2) {
                                    makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(paddingTop, VideoPlayer.MEDIA_ERROR_SYSTEM);
                                    i6 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
                                } else if (i20 == -1) {
                                    i6 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
                                    makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(paddingTop, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                                } else {
                                    i6 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
                                    makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i20, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                                }
                            } else {
                                i6 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
                                makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(childAt2.getMeasuredHeight(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                            }
                            childAt2.measure(View.MeasureSpec.makeMeasureSpec(i16, i6), makeMeasureSpec2);
                        }
                    }
                }
                i5 = i16;
                i17++;
                i16 = i5;
                i4 = 8;
            }
        }
        int windowWidth = getWindowWidth();
        if (windowWidth > 0) {
            size = windowWidth;
        }
        setMeasuredDimension(size, getPaddingBottom() + getPaddingTop() + i3);
        this.mCanSlide = z2;
        ViewDragHelper viewDragHelper = this.mDragHelper;
        if (viewDragHelper.mDragState == 0 || z2) {
            return;
        }
        viewDragHelper.abort();
    }

    public final void onPanelDragged(int i) {
        if (this.mIsLock) {
            return;
        }
        if (this.mSlideableView == null) {
            this.mSlideOffset = 0.0f;
            return;
        }
        boolean isLayoutRtlSupport = isLayoutRtlSupport();
        LayoutParams layoutParams = (LayoutParams) this.mSlideableView.getLayoutParams();
        int paddingRight = (isLayoutRtlSupport ? getPaddingRight() : getPaddingLeft()) + (isLayoutRtlSupport ? ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin : ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin);
        int width = this.mSlideableView.getWidth();
        if (isLayoutRtlSupport && this.mResizeOff) {
            width = getWidth() - paddingRight;
        } else if (this.mIsNeedClose) {
            width = Math.max((getWidth() - this.mSlideRange) - paddingRight, this.mSmoothWidth);
        } else if (this.mIsNeedOpen) {
            int width2 = getWidth() - paddingRight;
            int i2 = this.mSmoothWidth;
            if (i2 == 0) {
                i2 = getWidth() - paddingRight;
            }
            width = Math.min(width2, i2);
        }
        if (isLayoutRtlSupport) {
            i = (getWidth() - i) - width;
        }
        float f = i - paddingRight;
        int i3 = this.mSlideRange;
        if (i3 == 0) {
            i3 = 1;
        }
        float f2 = f / i3;
        this.mSlideOffset = f2;
        this.mSlideOffset = f2 <= 1.0f ? Math.max(f2, 0.0f) : 1.0f;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null && velocityTracker.getXVelocity() != 0.0f) {
            this.mLastValidVelocity = (int) this.mVelocityTracker.getXVelocity();
        }
        updateSlidingState();
        if (layoutParams.dimWhenOffset) {
            dimChildView(this.mSlideOffset, this.mSliderFadeColor, this.mSlideableView);
        }
        if (this.mResizeOff) {
            return;
        }
        resizeSlideableView(this.mSlideOffset);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.mSuperState);
        if (savedState.isOpen) {
            this.mIsNeedOpen = true;
            this.mIsNeedClose = false;
            openPane(!(Settings.System.getInt(getContext().getContentResolver(), "remove_animations", 0) == 1));
        } else {
            this.mIsNeedOpen = false;
            this.mIsNeedClose = true;
            closePane(!(Settings.System.getInt(getContext().getContentResolver(), "remove_animations", 0) == 1));
        }
        this.mPreservedOpenState = savedState.isOpen;
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.isOpen = this.mCanSlide ? isOpen() : this.mPreservedOpenState;
        return savedState;
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3) {
            this.mFirstLayout = true;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0033, code lost:
    
        if (r0 != 3) goto L88;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int right;
        int i;
        if (!this.mCanSlide || this.mIsLock) {
            return super.onTouchEvent(motionEvent);
        }
        this.mDragHelper.processTouchEvent(motionEvent);
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    float x = motionEvent.getX();
                    float abs = Math.abs(x - this.mInitialMotionX);
                    float f = this.mPrevMotionX;
                    float f2 = x - f;
                    if (f != x) {
                        this.mPrevMotionX = x;
                    }
                    int i2 = this.mDragHelper.mTouchSlop;
                    if (!this.mIsUnableToDrag && abs > i2) {
                        if (this.mIsSlideableViewTouched) {
                            if (!isLayoutRtlSupport()) {
                                int right2 = isLayoutRtlSupport() ? this.mSlideableView.getRight() : this.mSlideableView.getLeft();
                                int width = this.mSlideableView.getWidth();
                                if (right2 == 0) {
                                    right2 = 1;
                                }
                                int i3 = width / right2;
                                float left = this.mSlideableView.getLeft();
                                if (i3 == 0) {
                                    i3 = 1;
                                }
                                f2 = Math.max((f2 / i3) + left, this.mStartMargin);
                                if (this.mResizeOff) {
                                    this.mSlideableView.setLeft((int) Math.max(this.mStartMargin, f2));
                                    this.mSlideableView.setRight((getWindowWidth() + this.mSlideableView.getLeft()) - this.mStartMargin);
                                }
                            } else if (this.mResizeOff) {
                                right = this.mSlideableView.getRight() - getWindowWidth();
                                i = this.mStartMargin;
                                f2 = right + i;
                            }
                            onPanelDragged((int) f2);
                        } else {
                            if (isLayoutRtlSupport()) {
                                float max = Math.max(Math.min(this.mSlideableView.getRight() + f2, getWidth() - this.mStartMargin), (getWidth() - this.mStartMargin) - this.mSlideRange);
                                if (this.mResizeOff) {
                                    this.mSlideableView.setRight((int) max);
                                    this.mSlideableView.setLeft((this.mSlideableView.getRight() - getWindowWidth()) + this.mStartMargin);
                                    right = this.mSlideableView.getRight() - getWindowWidth();
                                    i = this.mStartMargin;
                                    f2 = right + i;
                                }
                            } else {
                                int i4 = this.mStartMargin;
                                int i5 = this.mSlideRange;
                                float f3 = (i4 + i5) / (i5 == 0 ? 1.0f : i5);
                                this.mVelocityTracker.computeCurrentVelocity(1000, 2.0f);
                                if (this.mVelocityTracker.getXVelocity() > 0.0f) {
                                    f3 *= this.mVelocityTracker.getXVelocity();
                                }
                                f2 = Math.min((f2 / (f3 != 0.0f ? f3 : 1.0f)) + this.mSlideableView.getLeft(), this.mStartMargin + this.mSlideRange);
                                if (this.mResizeOff) {
                                    this.mSlideableView.setRight((getWindowWidth() + this.mSlideableView.getLeft()) - this.mStartMargin);
                                }
                                this.mSlideableView.setLeft((int) Math.max(this.mStartMargin, f2));
                            }
                            onPanelDragged((int) f2);
                        }
                    }
                }
            }
            VelocityTracker velocityTracker2 = this.mVelocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.recycle();
                this.mVelocityTracker = null;
            }
            if (isDimmed(this.mSlideableView)) {
                float x2 = motionEvent.getX();
                float y = motionEvent.getY();
                float f4 = x2 - this.mInitialMotionX;
                float f5 = y - this.mInitialMotionY;
                int i6 = this.mDragHelper.mTouchSlop;
                if ((f5 * f5) + (f4 * f4) < i6 * i6 && ViewDragHelper.isViewUnder(this.mSlideableView, (int) x2, (int) y)) {
                    closePane(true);
                }
            }
            this.mSmoothWidth = this.mSlideableView.getWidth();
            this.mDoubleCheckState = -1;
            float f6 = this.mSlideOffset;
            if (f6 != 0.0f && f6 != 1.0f) {
                if (f6 >= 0.5f) {
                    this.mDoubleCheckState = 1;
                    this.mLastValidVelocity = 0;
                    this.mIsNeedOpen = true;
                    this.mIsNeedClose = false;
                    openPane(true);
                } else {
                    this.mDoubleCheckState = 0;
                    this.mLastValidVelocity = 0;
                    this.mIsNeedOpen = false;
                    this.mIsNeedClose = true;
                    closePane(true);
                }
            }
        } else {
            float x3 = motionEvent.getX();
            float y2 = motionEvent.getY();
            this.mInitialMotionX = x3;
            this.mInitialMotionY = y2;
            this.mStartOffset = this.mSlideOffset;
            this.mIsNeedOpen = false;
            this.mIsNeedClose = false;
            this.mPrevMotionX = x3;
            this.mSmoothWidth = 0;
        }
        return true;
    }

    @Override // android.view.View
    public final void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        int i2 = this.mPrevWindowVisibility;
        if ((i2 == 8 || i2 == 4) && i == 0) {
            if (isOpen()) {
                this.mPendingAction = 1;
            } else {
                this.mPendingAction = 2;
            }
        }
        if (this.mPrevWindowVisibility != i) {
            this.mPrevWindowVisibility = i;
        }
    }

    public final boolean openPane(boolean z) {
        if (this.mIsAnimate) {
            return true;
        }
        if (this.mSlideableView == null || this.mIsLock) {
            return false;
        }
        if (z) {
            if (!this.mFirstLayout && !smoothSlideTo(1.0f)) {
                return false;
            }
            this.mPreservedOpenState = true;
            return true;
        }
        int i = this.mFixedPaneStartX + (isLayoutRtlSupport() ? -this.mSlideRange : this.mSlideRange);
        onPanelDragged(i);
        if (this.mResizeOff) {
            resizeSlideableView(0.0f);
            if (isLayoutRtlSupport()) {
                this.mSlideableView.setRight((getWindowWidth() - this.mStartMargin) - this.mSlideRange);
                this.mSlideableView.setLeft(this.mSlideableView.getRight() - (getWindowWidth() - this.mStartMargin));
            } else {
                this.mSlideableView.setLeft(i);
                this.mSlideableView.setRight((getWindowWidth() + i) - this.mStartMargin);
            }
        } else {
            resizeSlideableView(1.0f);
        }
        this.mPreservedOpenState = true;
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        if (isInTouchMode() || this.mCanSlide) {
            return;
        }
        this.mPreservedOpenState = view == this.mSlideableView;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x008d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void resizeSlideableView(float f) {
        ViewGroup.LayoutParams layoutParams;
        int i;
        float dimension;
        int i2;
        int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
        View view = this.mSlideableView;
        if (view instanceof ViewGroup) {
            int paddingEnd = this.mSlideableView.getPaddingEnd() + view.getPaddingStart();
            ViewGroup viewGroup = (ViewGroup) this.mSlideableView;
            int childCount = viewGroup.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = viewGroup.getChildAt(i3);
                if (childAt != null && (layoutParams = childAt.getLayoutParams()) != null) {
                    int paddingEnd2 = (((width - this.mStartSlideX) - ((int) (this.mSlideRange * f))) - paddingEnd) - (childAt.getPaddingEnd() + childAt.getPaddingStart());
                    TypedValue typedValue = this.mPrefContentWidth;
                    if (typedValue == null) {
                        typedValue = new TypedValue();
                        getResources().getValue(R.dimen.sesl_sliding_pane_contents_width, typedValue, true);
                    }
                    int i4 = typedValue.type;
                    if (i4 == 4) {
                        dimension = typedValue.getFloat() * width;
                    } else if (i4 == 5) {
                        dimension = typedValue.getDimension(getResources().getDisplayMetrics());
                    } else {
                        i = paddingEnd2;
                        i2 = this.mUserPreferredContentSize;
                        if (i2 != -1) {
                            i = i2;
                        }
                        int min = Math.min(paddingEnd2, i);
                        if (this.mIsSinglePanel) {
                            if (!((childAt instanceof Toolbar) || (childAt instanceof android.widget.Toolbar) || (childAt instanceof SPLToolbarContainer))) {
                                if (childAt instanceof CoordinatorLayout) {
                                    if (childAt instanceof ViewGroup) {
                                        ViewGroup viewGroup2 = (ViewGroup) childAt;
                                        if (viewGroup2.getChildCount() == 2) {
                                            this.mResizeChild = viewGroup2.getChildAt(1);
                                        }
                                    }
                                    View view2 = this.mResizeChild;
                                    ViewGroup.LayoutParams layoutParams2 = view2 == null ? null : view2.getLayoutParams();
                                    if (layoutParams2 != null) {
                                        layoutParams2.width = min;
                                    }
                                } else {
                                    paddingEnd2 = min;
                                }
                            }
                        }
                        layoutParams.width = paddingEnd2;
                        childAt.requestLayout();
                    }
                    i = (int) dimension;
                    i2 = this.mUserPreferredContentSize;
                    if (i2 != -1) {
                    }
                    int min2 = Math.min(paddingEnd2, i);
                    if (this.mIsSinglePanel) {
                    }
                    layoutParams.width = paddingEnd2;
                    childAt.requestLayout();
                }
            }
        }
    }

    public final boolean smoothSlideTo(float f) {
        int paddingLeft;
        int width;
        this.mIsAnimate = false;
        if (!this.mCanSlide) {
            return false;
        }
        boolean isLayoutRtlSupport = isLayoutRtlSupport();
        LayoutParams layoutParams = (LayoutParams) this.mSlideableView.getLayoutParams();
        if (isLayoutRtlSupport) {
            int paddingRight = getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
            int width2 = this.mSlideableView.getWidth();
            if (this.mIsNeedClose) {
                width = this.mResizeOff ? getWidth() : getWidth() - this.mSlideRange;
            } else {
                if (this.mIsNeedOpen) {
                    width = getWidth();
                }
                paddingLeft = (int) (getWidth() - (((f * this.mSlideRange) + paddingRight) + width2));
            }
            width2 = width - paddingRight;
            paddingLeft = (int) (getWidth() - (((f * this.mSlideRange) + paddingRight) + width2));
        } else {
            paddingLeft = (int) ((f * this.mSlideRange) + getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin);
        }
        ViewDragHelper viewDragHelper = this.mDragHelper;
        View view = this.mSlideableView;
        if (!viewDragHelper.smoothSlideViewTo(view, paddingLeft, view.getTop())) {
            return false;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 4) {
                childAt.setVisibility(0);
            }
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
        this.mIsAnimate = true;
        return true;
    }

    public final void updateObscuredViewsVisibility(View view) {
        int i;
        int i2;
        int i3;
        int i4;
        boolean z;
        View view2 = view;
        boolean isLayoutRtlSupport = isLayoutRtlSupport();
        int width = isLayoutRtlSupport ? getWidth() - getPaddingRight() : getPaddingLeft();
        int paddingLeft = isLayoutRtlSupport ? getPaddingLeft() : getWidth() - getPaddingRight();
        int paddingTop = getPaddingTop();
        int height = getHeight() - getPaddingBottom();
        if (view2 == null || !view.isOpaque()) {
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
        } else {
            i = view.getLeft();
            i2 = view.getRight();
            i3 = view.getTop();
            i4 = view.getBottom();
        }
        int childCount = getChildCount();
        int i5 = 0;
        while (i5 < childCount) {
            View childAt = getChildAt(i5);
            if (childAt == view2) {
                return;
            }
            if (childAt.getVisibility() == 8) {
                z = isLayoutRtlSupport;
            } else {
                z = isLayoutRtlSupport;
                childAt.setVisibility((Math.max(isLayoutRtlSupport ? paddingLeft : width, childAt.getLeft()) < i || Math.max(paddingTop, childAt.getTop()) < i3 || Math.min(isLayoutRtlSupport ? width : paddingLeft, childAt.getRight()) > i2 || Math.min(height, childAt.getBottom()) > i4) ? 0 : 4);
            }
            i5++;
            view2 = view;
            isLayoutRtlSupport = z;
        }
    }

    public final void updateSlidingState() {
        SeslSlidingState seslSlidingState = this.mSlidingState;
        if (seslSlidingState == null || this.mSlideableView == null) {
            return;
        }
        float f = this.mSlideOffset;
        if (f == 0.0f) {
            if (seslSlidingState.mCurrentState != 0) {
                seslSlidingState.mCurrentState = 0;
                this.mStartOffset = f;
                sendAccessibilityEvent(32);
                return;
            }
            return;
        }
        if (f != 1.0f) {
            if (seslSlidingState.mCurrentState != 2) {
                seslSlidingState.mCurrentState = 2;
            }
        } else if (seslSlidingState.mCurrentState != 1) {
            seslSlidingState.mCurrentState = 1;
            this.mStartOffset = f;
            sendAccessibilityEvent(32);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public static final int[] ATTRS = {android.R.attr.layout_weight};
        public Paint dimPaint;
        public boolean dimWhenOffset;
        public boolean slideable;
        public final float weight;

        public LayoutParams() {
            super(-1, -1);
            this.weight = 0.0f;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.weight = 0.0f;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.weight = 0.0f;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.weight = 0.0f;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.weight = 0.0f;
            this.weight = layoutParams.weight;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.weight = 0.0f;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS);
            this.weight = obtainStyledAttributes.getFloat(0, 0.0f);
            obtainStyledAttributes.recycle();
        }
    }

    public SlidingPaneLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingPaneLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int color;
        this.mSliderFadeColor = -858993460;
        new CopyOnWriteArrayList();
        this.mFirstLayout = true;
        this.mTmpRect = new Rect();
        this.mPostedRunnables = new ArrayList();
        this.mDoubleCheckState = -1;
        this.mIsNeedClose = false;
        this.mIsNeedOpen = false;
        this.mSmoothWidth = 0;
        this.mStartOffset = 0.0f;
        this.mSlidingPaneDragArea = 0;
        this.mVelocityTracker = null;
        this.mStartMargin = 0;
        this.mRoundedColor = -1;
        this.mPrevWindowVisibility = 0;
        this.mFixedPaneStartX = 0;
        this.mPrevOrientation = 0;
        this.mStartSlideX = 0;
        this.mLastValidVelocity = 0;
        this.mResizeChild = null;
        this.mIsLock = false;
        this.mMarginTop = 0;
        this.mMarginBottom = 0;
        this.mUserPreferredContentSize = -1;
        this.mUserPreferredDrawerSize = -1;
        float f = context.getResources().getDisplayMetrics().density;
        this.mOverhangSize = (int) ((32.0f * f) + 0.5f);
        setWillNotDraw(false);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
        ViewCompat.Api16Impl.setImportantForAccessibility(this, 1);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SlidingPaneLayout, i, 0);
        this.mIsSinglePanel = obtainStyledAttributes.getBoolean(4, false);
        boolean z = obtainStyledAttributes.getBoolean(0, true);
        this.mDrawRoundedCorner = z;
        if (SeslMisc.isLightTheme(context)) {
            color = getResources().getColor(R.color.sesl_sliding_pane_background_light, null);
        } else {
            color = getResources().getColor(R.color.sesl_sliding_pane_background_dark, null);
        }
        this.mRoundedColor = obtainStyledAttributes.getColor(1, color);
        boolean z2 = obtainStyledAttributes.getBoolean(7, false);
        this.mResizeOff = z2;
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(3, 0);
        this.mMarginTop = dimensionPixelSize;
        int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        this.mMarginBottom = dimensionPixelSize2;
        if (obtainStyledAttributes.hasValue(6)) {
            TypedValue typedValue = new TypedValue();
            this.mPrefDrawerWidth = typedValue;
            obtainStyledAttributes.getValue(6, typedValue);
        }
        if (obtainStyledAttributes.hasValue(5)) {
            TypedValue typedValue2 = new TypedValue();
            this.mPrefContentWidth = typedValue2;
            obtainStyledAttributes.getValue(5, typedValue2);
        }
        obtainStyledAttributes.recycle();
        ViewDragHelper seslCreate = ViewDragHelper.seslCreate(this, new DragHelperCallback());
        this.mDragHelper = seslCreate;
        seslCreate.mMinVelocity = f * 400.0f;
        seslCreate.mIsUpdateOffsetLR = z2;
        if (z) {
            SlidingPaneRoundedCorner slidingPaneRoundedCorner = new SlidingPaneRoundedCorner(context);
            this.mSlidingPaneRoundedCorner = slidingPaneRoundedCorner;
            slidingPaneRoundedCorner.mRoundedCornerMode = 0;
            if (slidingPaneRoundedCorner.mStartTopDrawable == null || slidingPaneRoundedCorner.mStartBottomDrawable == null || slidingPaneRoundedCorner.mEndTopDrawable == null || slidingPaneRoundedCorner.mEndBottomDrawable == null) {
                slidingPaneRoundedCorner.initRoundedCorner();
            }
            slidingPaneRoundedCorner.mMarginTop = dimensionPixelSize;
            slidingPaneRoundedCorner.mMarginBottom = dimensionPixelSize2;
        }
        Resources resources = getResources();
        boolean z3 = resources.getBoolean(R.dimen.sesl_sliding_layout_default_open);
        this.mSlidingPaneDragArea = resources.getDimensionPixelSize(R.dimen.sesl_sliding_pane_contents_drag_width_default);
        this.mPendingAction = z3 ? 1 : 2;
        this.mPrevOrientation = resources.getConfiguration().orientation;
        this.mSlidingState = new SeslSlidingState();
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }
}
