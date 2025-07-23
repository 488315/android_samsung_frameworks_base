package com.google.android.material.carousel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        float centerY = rect.centerY();
        if (isHorizontal()) {
            centerY = rect.centerX();
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
            float abs = Math.abs(f5 - centerY);
            if (f5 <= centerY && abs <= f) {
                i = i5;
                f = abs;
            }
            if (f5 > centerY && abs <= f3) {
                i3 = i5;
                f3 = abs;
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
        float lerp = AnimationUtils.lerp(f6, keyline2.maskedItemSize, keyline.locOffset, keyline2.locOffset, centerY);
        float width = isHorizontal() ? (rect.width() - lerp) / 2.0f : 0.0f;
        float height = isHorizontal() ? 0.0f : (rect.height() - lerp) / 2.0f;
        rect.set((int) (rect.left + width), (int) (rect.top + height), (int) (rect.right - width), (int) (rect.bottom - height));
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
    public final void onAttachedToWindow(RecyclerView recyclerView) {
        CarouselStrategy carouselStrategy = this.carouselStrategy;
        Context context = recyclerView.getContext();
        float f = carouselStrategy.smallSizeMin;
        if (f <= 0.0f) {
            f = context.getResources().getDimension(R.dimen.m3_carousel_small_item_size_min);
        }
        carouselStrategy.smallSizeMin = f;
        float f2 = carouselStrategy.smallSizeMax;
        if (f2 <= 0.0f) {
            f2 = context.getResources().getDimension(R.dimen.m3_carousel_small_item_size_max);
        }
        carouselStrategy.smallSizeMax = f2;
        requestLayout();
        recyclerView.addOnLayoutChangeListener(this.recyclerViewSizeChangeListener);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onDetachedFromWindow(RecyclerView recyclerView) {
        recyclerView.removeOnLayoutChangeListener(this.recyclerViewSizeChangeListener);
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x002e, code lost:
    
        if (r8 == 1) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0038, code lost:
    
        if (isLayoutRtl() != false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x003c, code lost:
    
        if (r8 == 1) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0045, code lost:
    
        if (isLayoutRtl() != false) goto L20;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View onFocusSearchFailed(android.view.View r5, int r6, androidx.recyclerview.widget.RecyclerView.Recycler r7, androidx.recyclerview.widget.RecyclerView.State r8) {
        /*
            r4 = this;
            int r8 = r4.getChildCount()
            if (r8 != 0) goto L8
            goto L9a
        L8:
            com.google.android.material.carousel.CarouselOrientationHelper r8 = r4.orientationHelper
            int r8 = r8.orientation
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = -1
            r2 = 1
            if (r6 == r2) goto L3a
            r3 = 2
            if (r6 == r3) goto L30
            r3 = 17
            if (r6 == r3) goto L3f
            r3 = 33
            if (r6 == r3) goto L3c
            r3 = 66
            if (r6 == r3) goto L32
            r3 = 130(0x82, float:1.82E-43)
            if (r6 == r3) goto L2e
            java.lang.String r8 = "Unknown focus request:"
            java.lang.String r3 = "CarouselLayoutManager"
            androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0.m(r6, r8, r3)
        L2c:
            r6 = r0
            goto L48
        L2e:
            if (r8 != r2) goto L2c
        L30:
            r6 = r2
            goto L48
        L32:
            if (r8 != 0) goto L2c
            boolean r6 = r4.isLayoutRtl()
            if (r6 == 0) goto L30
        L3a:
            r6 = r1
            goto L48
        L3c:
            if (r8 != r2) goto L2c
            goto L3a
        L3f:
            if (r8 != 0) goto L2c
            boolean r6 = r4.isLayoutRtl()
            if (r6 == 0) goto L3a
            goto L30
        L48:
            if (r6 != r0) goto L4b
            goto L9a
        L4b:
            java.lang.String r8 = "All children of a RecyclerView using CarouselLayoutManager must use MaskableFrameLayout as their root ViewGroup."
            r0 = 0
            if (r6 != r1) goto L8f
            int r5 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r5)
            if (r5 != 0) goto L57
            goto L9a
        L57:
            android.view.View r5 = r4.getChildAt(r0)
            int r5 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r5)
            int r5 = r5 - r2
            if (r5 < 0) goto L7e
            int r6 = r4.getItemCount()
            if (r5 < r6) goto L69
            goto L7e
        L69:
            com.google.android.material.carousel.CarouselOrientationHelper r6 = r4.orientationHelper
            r6.getParentStart()
            com.google.android.material.carousel.KeylineState r6 = r4.currentKeylineState
            float r6 = r6.itemSize
            r4.isLayoutRtl()
            r7.getViewForPosition(r5)
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            r4.<init>(r8)
            throw r4
        L7e:
            boolean r5 = r4.isLayoutRtl()
            if (r5 == 0) goto L8a
            int r5 = r4.getChildCount()
            int r0 = r5 + (-1)
        L8a:
            android.view.View r4 = r4.getChildAt(r0)
            return r4
        L8f:
            int r5 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r5)
            int r6 = r4.getItemCount()
            int r6 = r6 - r2
            if (r5 != r6) goto L9c
        L9a:
            r4 = 0
            return r4
        L9c:
            int r5 = r4.getChildCount()
            int r5 = r5 - r2
            android.view.View r5 = r4.getChildAt(r5)
            int r5 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r5)
            int r5 = r5 + r2
            if (r5 < 0) goto Lc8
            int r6 = r4.getItemCount()
            if (r5 < r6) goto Lb3
            goto Lc8
        Lb3:
            com.google.android.material.carousel.CarouselOrientationHelper r6 = r4.orientationHelper
            r6.getParentStart()
            com.google.android.material.carousel.KeylineState r6 = r4.currentKeylineState
            float r6 = r6.itemSize
            r4.isLayoutRtl()
            r7.getViewForPosition(r5)
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            r4.<init>(r8)
            throw r4
        Lc8:
            boolean r5 = r4.isLayoutRtl()
            if (r5 == 0) goto Lcf
            goto Ld5
        Lcf:
            int r5 = r4.getChildCount()
            int r0 = r5 + (-1)
        Ld5:
            android.view.View r4 = r4.getChildAt(r0)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.carousel.CarouselLayoutManager.onFocusSearchFailed(android.view.View, int, androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State):android.view.View");
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
                final CarouselLayoutManager carouselLayoutManager = CarouselLayoutManager.this;
                if (i2 == i6 && i3 == i7 && i4 == i8 && i5 == i9) {
                    return;
                }
                view.post(new Runnable() { // from class: com.google.android.material.carousel.CarouselLayoutManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CarouselLayoutManager.this.requestLayout();
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
                final CarouselLayoutManager carouselLayoutManager = CarouselLayoutManager.this;
                if (i22 == i6 && i3 == i7 && i4 == i8 && i5 == i9) {
                    return;
                }
                view.post(new Runnable() { // from class: com.google.android.material.carousel.CarouselLayoutManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CarouselLayoutManager.this.requestLayout();
                    }
                });
            }
        };
        this.carouselStrategy = new MultiBrowseCarouselStrategy();
        requestLayout();
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Carousel);
            obtainStyledAttributes.getInt(0, 0);
            requestLayout();
            setOrientation(obtainStyledAttributes.getInt(0, 0));
            obtainStyledAttributes.recycle();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void scrollToPosition(int i) {
    }
}
