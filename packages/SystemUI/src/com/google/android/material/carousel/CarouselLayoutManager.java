package com.google.android.material.carousel;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.carousel.KeylineState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class CarouselLayoutManager extends RecyclerView.LayoutManager implements RecyclerView.SmoothScroller.ScrollVectorProvider {
    public final CarouselStrategy carouselStrategy;
    public final KeylineState currentKeylineState;
    public final DebugItemDecoration debugItemDecoration;
    int maxScroll;
    int minScroll;
    public CarouselOrientationHelper orientationHelper;
    public final View.OnLayoutChangeListener recyclerViewSizeChangeListener;
    int scrollOffset;

    public class DebugItemDecoration extends RecyclerView.ItemDecoration {
        public final List keylines;
        public final Paint linePaint;

        public DebugItemDecoration() {
            Paint paint = new Paint();
            this.linePaint = paint;
            this.keylines = Collections.unmodifiableList(new ArrayList());
            paint.setStrokeWidth(5.0f);
            paint.setColor(-65281);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
            super.onDrawOver(canvas, recyclerView, state);
            this.linePaint.setStrokeWidth(recyclerView.getResources().getDimension(R.dimen.m3_carousel_debug_keyline_width));
            for (KeylineState.Keyline keyline : this.keylines) {
                this.linePaint.setColor(ColorUtils.blendARGB(keyline.mask, -65281, -16776961));
                if (((CarouselLayoutManager) recyclerView.getLayoutManager()).isHorizontal()) {
                    float parentTop = ((CarouselLayoutManager) recyclerView.getLayoutManager()).orientationHelper.getParentTop();
                    float parentBottom = ((CarouselLayoutManager) recyclerView.getLayoutManager()).orientationHelper.getParentBottom();
                    Paint paint = this.linePaint;
                    float f = keyline.locOffset;
                    canvas.drawLine(f, parentTop, f, parentBottom, paint);
                } else {
                    float parentLeft = ((CarouselLayoutManager) recyclerView.getLayoutManager()).orientationHelper.getParentLeft();
                    float parentRight = ((CarouselLayoutManager) recyclerView.getLayoutManager()).orientationHelper.getParentRight();
                    Paint paint2 = this.linePaint;
                    float f2 = keyline.locOffset;
                    canvas.drawLine(parentLeft, f2, parentRight, f2, paint2);
                }
            }
        }
    }

    public class KeylineRange {
        public final KeylineState.Keyline leftOrTop;
        public final KeylineState.Keyline rightOrBottom;

        public KeylineRange(KeylineState.Keyline keyline, KeylineState.Keyline keyline2) {
            if (keyline.loc > keyline2.loc) {
                throw new IllegalArgumentException();
            }
            this.leftOrTop = keyline;
            this.rightOrBottom = keyline2;
        }
    }

    public CarouselLayoutManager() {
        this(new MultiBrowseCarouselStrategy());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean canScrollHorizontally() {
        return isHorizontal();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean canScrollVertically() {
        return !isHorizontal();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollExtent(RecyclerView.State state) {
        getChildCount();
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollOffset(RecyclerView.State state) {
        return this.scrollOffset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollRange(RecyclerView.State state) {
        return this.maxScroll - this.minScroll;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
    public final PointF computeScrollVectorForPosition(int i) {
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollExtent(RecyclerView.State state) {
        getChildCount();
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollOffset(RecyclerView.State state) {
        return this.scrollOffset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollRange(RecyclerView.State state) {
        return this.maxScroll - this.minScroll;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void getDecoratedBoundsWithMargins(Rect rect, View view) {
        RecyclerView.getDecoratedBoundsWithMarginsInt(rect, view);
        float fCenterY = rect.centerY();
        if (isHorizontal()) {
            fCenterY = rect.centerX();
        }
        List list = this.currentKeylineState.keylines;
        float f = Float.MAX_VALUE;
        int i = -1;
        int i2 = -1;
        int i3 = -1;
        int i4 = -1;
        float f2 = -3.4028235E38f;
        float f3 = Float.MAX_VALUE;
        float f4 = Float.MAX_VALUE;
        for (int i5 = 0; i5 < list.size(); i5++) {
            float f5 = ((KeylineState.Keyline) list.get(i5)).locOffset;
            float fAbs = Math.abs(f5 - fCenterY);
            if (f5 <= fCenterY && fAbs <= f) {
                i = i5;
                f = fAbs;
            }
            if (f5 > fCenterY && fAbs <= f3) {
                i3 = i5;
                f3 = fAbs;
            }
            if (f5 <= f4) {
                i2 = i5;
                f4 = f5;
            }
            if (f5 > f2) {
                i4 = i5;
                f2 = f5;
            }
        }
        if (i == -1) {
            i = i2;
        }
        if (i3 == -1) {
            i3 = i4;
        }
        KeylineRange keylineRange = new KeylineRange((KeylineState.Keyline) list.get(i), (KeylineState.Keyline) list.get(i3));
        KeylineState.Keyline keyline = keylineRange.leftOrTop;
        float f6 = keyline.maskedItemSize;
        KeylineState.Keyline keyline2 = keylineRange.rightOrBottom;
        float fLerp = AnimationUtils.lerp(f6, keyline2.maskedItemSize, keyline.locOffset, keyline2.locOffset, fCenterY);
        float fWidth = isHorizontal() ? (rect.width() - fLerp) / 2.0f : 0.0f;
        float fHeight = isHorizontal() ? 0.0f : (rect.height() - fLerp) / 2.0f;
        rect.set((int) (rect.left + fWidth), (int) (rect.top + fHeight), (int) (rect.right - fWidth), (int) (rect.bottom - fHeight));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean isAutoMeasureEnabled() {
        return true;
    }

    public final boolean isHorizontal() {
        return this.orientationHelper.orientation == 0;
    }

    public final boolean isLayoutRtl() {
        return isHorizontal() && getLayoutDirection() == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onAttachedToWindow(RecyclerView recyclerView) throws Resources.NotFoundException {
        CarouselStrategy carouselStrategy = this.carouselStrategy;
        Context context = recyclerView.getContext();
        float dimension = carouselStrategy.smallSizeMin;
        if (dimension <= 0.0f) {
            dimension = context.getResources().getDimension(R.dimen.m3_carousel_small_item_size_min);
        }
        carouselStrategy.smallSizeMin = dimension;
        float dimension2 = carouselStrategy.smallSizeMax;
        if (dimension2 <= 0.0f) {
            dimension2 = context.getResources().getDimension(R.dimen.m3_carousel_small_item_size_max);
        }
        carouselStrategy.smallSizeMax = dimension2;
        requestLayout();
        recyclerView.addOnLayoutChangeListener(this.recyclerViewSizeChangeListener);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onDetachedFromWindow(RecyclerView recyclerView) {
        recyclerView.removeOnLayoutChangeListener(this.recyclerViewSizeChangeListener);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003a  */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final View onFocusSearchFailed(View view, int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        char c;
        if (getChildCount() == 0) {
            return null;
        }
        int i2 = this.orientationHelper.orientation;
        if (i == 1) {
            c = 65535;
        } else if (i == 2) {
            c = 1;
        } else if (i == 17) {
            if (i2 == 0) {
                if (isLayoutRtl()) {
                }
            }
            c = 0;
        } else if (i != 33) {
            if (i != 66) {
                if (i != 130) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Unknown focus request:", "CarouselLayoutManager");
                } else if (i2 == 1) {
                }
                c = 0;
            } else {
                if (i2 == 0) {
                    if (isLayoutRtl()) {
                    }
                }
                c = 0;
            }
        } else if (i2 != 1) {
            c = 0;
        }
        if (c == 0) {
            return null;
        }
        if (c == 65535) {
            if (RecyclerView.LayoutManager.getPosition(view) == 0) {
                return null;
            }
            int position = RecyclerView.LayoutManager.getPosition(getChildAt(0)) - 1;
            if (position < 0 || position >= getItemCount()) {
                return getChildAt(isLayoutRtl() ? getChildCount() - 1 : 0);
            }
            this.orientationHelper.getParentStart();
            float f = this.currentKeylineState.itemSize;
            isLayoutRtl();
            recycler.getViewForPosition(position);
            throw new IllegalStateException("All children of a RecyclerView using CarouselLayoutManager must use MaskableFrameLayout as their root ViewGroup.");
        }
        if (RecyclerView.LayoutManager.getPosition(view) == getItemCount() - 1) {
            return null;
        }
        int position2 = RecyclerView.LayoutManager.getPosition(getChildAt(getChildCount() - 1)) + 1;
        if (position2 < 0 || position2 >= getItemCount()) {
            return getChildAt(isLayoutRtl() ? 0 : getChildCount() - 1);
        }
        this.orientationHelper.getParentStart();
        float f2 = this.currentKeylineState.itemSize;
        isLayoutRtl();
        recycler.getViewForPosition(position2);
        throw new IllegalStateException("All children of a RecyclerView using CarouselLayoutManager must use MaskableFrameLayout as their root ViewGroup.");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            accessibilityEvent.setFromIndex(RecyclerView.LayoutManager.getPosition(getChildAt(0)));
            accessibilityEvent.setToIndex(RecyclerView.LayoutManager.getPosition(getChildAt(getChildCount() - 1)));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsAdded(int i, int i2) {
        getItemCount();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsRemoved(int i, int i2) {
        getItemCount();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.getItemCount() > 0) {
            if ((isHorizontal() ? this.mWidth : this.mHeight) > 0.0f) {
                isLayoutRtl();
                recycler.getViewForPosition(0);
                throw new IllegalStateException("All children of a RecyclerView using CarouselLayoutManager must use MaskableFrameLayout as their root ViewGroup.");
            }
        }
        removeAndRecycleAllViews(recycler);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onLayoutCompleted(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return;
        }
        RecyclerView.LayoutManager.getPosition(getChildAt(0));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!isHorizontal() || getChildCount() == 0 || i == 0) {
            return 0;
        }
        recycler.getViewForPosition(0);
        throw new IllegalStateException("All children of a RecyclerView using CarouselLayoutManager must use MaskableFrameLayout as their root ViewGroup.");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!canScrollVertically() || getChildCount() == 0 || i == 0) {
            return 0;
        }
        recycler.getViewForPosition(0);
        throw new IllegalStateException("All children of a RecyclerView using CarouselLayoutManager must use MaskableFrameLayout as their root ViewGroup.");
    }

    public final void setOrientation(int i) {
        CarouselOrientationHelper carouselOrientationHelper;
        int i2 = 1;
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "invalid orientation:"));
        }
        assertNotInLayoutOrScroll(null);
        CarouselOrientationHelper carouselOrientationHelper2 = this.orientationHelper;
        if (carouselOrientationHelper2 == null || i != carouselOrientationHelper2.orientation) {
            if (i == 0) {
                carouselOrientationHelper = new CarouselOrientationHelper(0) { // from class: com.google.android.material.carousel.CarouselOrientationHelper.2
                    @Override // com.google.android.material.carousel.CarouselOrientationHelper
                    public final int getParentBottom() {
                        CarouselLayoutManager carouselLayoutManager = this;
                        return carouselLayoutManager.mHeight - carouselLayoutManager.getPaddingBottom();
                    }

                    @Override // com.google.android.material.carousel.CarouselOrientationHelper
                    public final int getParentLeft() {
                        return 0;
                    }

                    @Override // com.google.android.material.carousel.CarouselOrientationHelper
                    public final int getParentRight() {
                        return this.mWidth;
                    }

                    @Override // com.google.android.material.carousel.CarouselOrientationHelper
                    public final int getParentStart() {
                        CarouselLayoutManager carouselLayoutManager = this;
                        if (carouselLayoutManager.isLayoutRtl()) {
                            return carouselLayoutManager.mWidth;
                        }
                        return 0;
                    }

                    @Override // com.google.android.material.carousel.CarouselOrientationHelper
                    public final int getParentTop() {
                        return this.getPaddingTop();
                    }
                };
            } else {
                if (i != 1) {
                    throw new IllegalArgumentException("invalid orientation");
                }
                carouselOrientationHelper = new CarouselOrientationHelper(i2) { // from class: com.google.android.material.carousel.CarouselOrientationHelper.1
                    @Override // com.google.android.material.carousel.CarouselOrientationHelper
                    public final int getParentBottom() {
                        return this.mHeight;
                    }

                    @Override // com.google.android.material.carousel.CarouselOrientationHelper
                    public final int getParentLeft() {
                        return this.getPaddingLeft();
                    }

                    @Override // com.google.android.material.carousel.CarouselOrientationHelper
                    public final int getParentRight() {
                        CarouselLayoutManager carouselLayoutManager = this;
                        return carouselLayoutManager.mWidth - carouselLayoutManager.getPaddingRight();
                    }

                    @Override // com.google.android.material.carousel.CarouselOrientationHelper
                    public final int getParentStart() {
                        return 0;
                    }

                    @Override // com.google.android.material.carousel.CarouselOrientationHelper
                    public final int getParentTop() {
                        return 0;
                    }
                };
            }
            this.orientationHelper = carouselOrientationHelper;
            requestLayout();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void smoothScrollToPosition(RecyclerView recyclerView, int i) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) { // from class: com.google.android.material.carousel.CarouselLayoutManager.1
            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public final int calculateDxToMakeVisible(View view, int i2) {
                CarouselLayoutManager.this.getClass();
                return 0;
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public final int calculateDyToMakeVisible(View view, int i2) {
                CarouselLayoutManager.this.getClass();
                return 0;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller
            public final PointF computeScrollVectorForPosition(int i2) {
                CarouselLayoutManager.this.getClass();
                return null;
            }
        };
        linearSmoothScroller.mTargetPosition = i;
        startSmoothScroll(linearSmoothScroller);
    }

    public CarouselLayoutManager(CarouselStrategy carouselStrategy) {
        this(carouselStrategy, 0);
    }

    public CarouselLayoutManager(CarouselStrategy carouselStrategy, int i) {
        this.debugItemDecoration = new DebugItemDecoration();
        this.recyclerViewSizeChangeListener = new View.OnLayoutChangeListener() { // from class: com.google.android.material.carousel.CarouselLayoutManager$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                final CarouselLayoutManager carouselLayoutManager = this.f$0;
                if (i2 == i6 && i3 == i7 && i4 == i8 && i5 == i9) {
                    return;
                }
                view.post(new Runnable() { // from class: com.google.android.material.carousel.CarouselLayoutManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        carouselLayoutManager.requestLayout();
                    }
                });
            }
        };
        this.carouselStrategy = carouselStrategy;
        requestLayout();
        setOrientation(i);
    }

    public CarouselLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        this.debugItemDecoration = new DebugItemDecoration();
        this.recyclerViewSizeChangeListener = new View.OnLayoutChangeListener() { // from class: com.google.android.material.carousel.CarouselLayoutManager$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i22, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                final CarouselLayoutManager carouselLayoutManager = this.f$0;
                if (i22 == i6 && i3 == i7 && i4 == i8 && i5 == i9) {
                    return;
                }
                view.post(new Runnable() { // from class: com.google.android.material.carousel.CarouselLayoutManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        carouselLayoutManager.requestLayout();
                    }
                });
            }
        };
        this.carouselStrategy = new MultiBrowseCarouselStrategy();
        requestLayout();
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Carousel);
            typedArrayObtainStyledAttributes.getInt(0, 0);
            requestLayout();
            setOrientation(typedArrayObtainStyledAttributes.getInt(0, 0));
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void scrollToPosition(int i) {
    }
}
