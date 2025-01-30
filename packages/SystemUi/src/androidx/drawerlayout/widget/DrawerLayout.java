package androidx.drawerlayout.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.graphics.Insets;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import androidx.drawerlayout.R$styleable;
import com.samsung.android.knox.p045ex.peripheral.PeripheralConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class DrawerLayout extends ViewGroup {
    public final C01771 mActionDismiss;
    public Rect mChildHitRect;
    public Matrix mChildInvertedMatrix;
    public boolean mChildrenCanceledTouch;
    public boolean mDrawStatusBarBackground;
    public final float mDrawerElevation;
    public int mDrawerState;
    public boolean mFirstLayout;
    public boolean mInLayout;
    public float mInitialMotionX;
    public float mInitialMotionY;
    public Object mLastInsets;
    public final ViewDragCallback mLeftCallback;
    public final ViewDragHelper mLeftDragger;
    public int mLockModeEnd;
    public int mLockModeLeft;
    public int mLockModeRight;
    public int mLockModeStart;
    public final int mMinDrawerMargin;
    public final ArrayList mNonDrawerViews;
    public final ViewDragCallback mRightCallback;
    public final ViewDragHelper mRightDragger;
    public final int mScrimColor;
    public float mScrimOpacity;
    public final Paint mScrimPaint;
    public final Drawable mStatusBarBackground;
    public static final int[] THEME_ATTRS = {R.attr.colorPrimaryDark};
    public static final int[] LAYOUT_ATTRS = {R.attr.layout_gravity};

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AccessibilityDelegate extends AccessibilityDelegateCompat {
        public AccessibilityDelegate() {
            new Rect();
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            if (accessibilityEvent.getEventType() != 32) {
                return super.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
            }
            accessibilityEvent.getText();
            DrawerLayout drawerLayout = DrawerLayout.this;
            View findVisibleDrawer = drawerLayout.findVisibleDrawer();
            if (findVisibleDrawer == null) {
                return true;
            }
            int drawerViewAbsoluteGravity = drawerLayout.getDrawerViewAbsoluteGravity(findVisibleDrawer);
            drawerLayout.getClass();
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            Gravity.getAbsoluteGravity(drawerViewAbsoluteGravity, ViewCompat.Api17Impl.getLayoutDirection(drawerLayout));
            return true;
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName("androidx.drawerlayout.widget.DrawerLayout");
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int[] iArr = DrawerLayout.THEME_ATTRS;
            View.AccessibilityDelegate accessibilityDelegate = this.mOriginalDelegate;
            AccessibilityNodeInfo accessibilityNodeInfo = accessibilityNodeInfoCompat.mInfo;
            accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfoCompat.setClassName("androidx.drawerlayout.widget.DrawerLayout");
            accessibilityNodeInfo.setFocusable(false);
            accessibilityNodeInfo.setFocused(false);
            accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_FOCUS);
            accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLEAR_FOCUS);
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            int[] iArr = DrawerLayout.THEME_ATTRS;
            return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ChildAccessibilityDelegate extends AccessibilityDelegateCompat {
        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            View.AccessibilityDelegate accessibilityDelegate = this.mOriginalDelegate;
            AccessibilityNodeInfo accessibilityNodeInfo = accessibilityNodeInfoCompat.mInfo;
            accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            int[] iArr = DrawerLayout.THEME_ATTRS;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if ((ViewCompat.Api16Impl.getImportantForAccessibility(view) == 4 || ViewCompat.Api16Impl.getImportantForAccessibility(view) == 2) ? false : true) {
                return;
            }
            accessibilityNodeInfoCompat.mParentVirtualDescendantId = -1;
            accessibilityNodeInfo.setParent(null);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ViewDragCallback extends ViewDragHelper.Callback {
        public final int mAbsGravity;
        public ViewDragHelper mDragger;
        public final RunnableC01801 mPeekRunnable = new Runnable() { // from class: androidx.drawerlayout.widget.DrawerLayout.ViewDragCallback.1
            @Override // java.lang.Runnable
            public final void run() {
                View findDrawerWithGravity;
                int width;
                ViewDragCallback viewDragCallback = ViewDragCallback.this;
                int i = viewDragCallback.mDragger.mEdgeSize;
                int i2 = viewDragCallback.mAbsGravity;
                boolean z = i2 == 3;
                DrawerLayout drawerLayout = DrawerLayout.this;
                if (z) {
                    findDrawerWithGravity = drawerLayout.findDrawerWithGravity(3);
                    width = (findDrawerWithGravity != null ? -findDrawerWithGravity.getWidth() : 0) + i;
                } else {
                    findDrawerWithGravity = drawerLayout.findDrawerWithGravity(5);
                    width = drawerLayout.getWidth() - i;
                }
                if (findDrawerWithGravity != null) {
                    if (((!z || findDrawerWithGravity.getLeft() >= width) && (z || findDrawerWithGravity.getLeft() <= width)) || drawerLayout.getDrawerLockMode(findDrawerWithGravity) != 0) {
                        return;
                    }
                    LayoutParams layoutParams = (LayoutParams) findDrawerWithGravity.getLayoutParams();
                    viewDragCallback.mDragger.smoothSlideViewTo(findDrawerWithGravity, width, findDrawerWithGravity.getTop());
                    layoutParams.isPeeking = true;
                    drawerLayout.invalidate();
                    View findDrawerWithGravity2 = drawerLayout.findDrawerWithGravity(i2 == 3 ? 5 : 3);
                    if (findDrawerWithGravity2 != null) {
                        drawerLayout.closeDrawer(findDrawerWithGravity2);
                    }
                    if (drawerLayout.mChildrenCanceledTouch) {
                        return;
                    }
                    long uptimeMillis = SystemClock.uptimeMillis();
                    MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                    int childCount = drawerLayout.getChildCount();
                    for (int i3 = 0; i3 < childCount; i3++) {
                        drawerLayout.getChildAt(i3).dispatchTouchEvent(obtain);
                    }
                    obtain.recycle();
                    drawerLayout.mChildrenCanceledTouch = true;
                }
            }
        };

        /* JADX WARN: Type inference failed for: r1v1, types: [androidx.drawerlayout.widget.DrawerLayout$ViewDragCallback$1] */
        public ViewDragCallback(int i) {
            this.mAbsGravity = i;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final int clampViewPositionHorizontal(View view, int i) {
            DrawerLayout drawerLayout = DrawerLayout.this;
            if (drawerLayout.checkDrawerViewAbsoluteGravity(view, 3)) {
                return Math.max(-view.getWidth(), Math.min(i, 0));
            }
            int width = drawerLayout.getWidth();
            return Math.max(width - view.getWidth(), Math.min(i, width));
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final int clampViewPositionVertical(View view, int i) {
            return view.getTop();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final int getViewHorizontalDragRange(View view) {
            DrawerLayout.this.getClass();
            if (DrawerLayout.isDrawerView(view)) {
                return view.getWidth();
            }
            return 0;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final void onEdgeDragStarted(int i, int i2) {
            int i3 = i & 1;
            DrawerLayout drawerLayout = DrawerLayout.this;
            View findDrawerWithGravity = i3 == 1 ? drawerLayout.findDrawerWithGravity(3) : drawerLayout.findDrawerWithGravity(5);
            if (findDrawerWithGravity == null || drawerLayout.getDrawerLockMode(findDrawerWithGravity) != 0) {
                return;
            }
            this.mDragger.captureChildView(findDrawerWithGravity, i2);
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final void onEdgeTouched() {
            DrawerLayout.this.postDelayed(this.mPeekRunnable, 160L);
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final void onViewCaptured(View view, int i) {
            ((LayoutParams) view.getLayoutParams()).isPeeking = false;
            int i2 = this.mAbsGravity == 3 ? 5 : 3;
            DrawerLayout drawerLayout = DrawerLayout.this;
            View findDrawerWithGravity = drawerLayout.findDrawerWithGravity(i2);
            if (findDrawerWithGravity != null) {
                drawerLayout.closeDrawer(findDrawerWithGravity);
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final void onViewDragStateChanged(int i) {
            DrawerLayout.this.updateDrawerState(this.mDragger.mCapturedView, i);
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final void onViewPositionChanged(View view, int i, int i2, int i3) {
            float width;
            int width2 = view.getWidth();
            DrawerLayout drawerLayout = DrawerLayout.this;
            if (width2 == 0) {
                width = 0.0f;
            } else {
                width = (drawerLayout.checkDrawerViewAbsoluteGravity(view, 3) ? i + width2 : drawerLayout.getWidth() - i) / width2;
            }
            drawerLayout.getClass();
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (width != layoutParams.onScreen) {
                layoutParams.onScreen = width;
            }
            view.setVisibility(width == 0.0f ? 4 : 0);
            drawerLayout.invalidate();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final void onViewReleased(View view, float f, float f2) {
            int i;
            DrawerLayout drawerLayout = DrawerLayout.this;
            drawerLayout.getClass();
            float f3 = ((LayoutParams) view.getLayoutParams()).onScreen;
            int width = view.getWidth();
            if (drawerLayout.checkDrawerViewAbsoluteGravity(view, 3)) {
                i = (f > 0.0f || (f == 0.0f && f3 > 0.5f)) ? 0 : -width;
            } else {
                int width2 = drawerLayout.getWidth();
                if (f < 0.0f || (f == 0.0f && f3 > 0.5f)) {
                    width2 -= width;
                }
                i = width2;
            }
            this.mDragger.settleCapturedViewAt(i, view.getTop());
            drawerLayout.invalidate();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final boolean tryCaptureView(View view, int i) {
            DrawerLayout drawerLayout = DrawerLayout.this;
            drawerLayout.getClass();
            return DrawerLayout.isDrawerView(view) && drawerLayout.checkDrawerViewAbsoluteGravity(view, this.mAbsGravity) && drawerLayout.getDrawerLockMode(view) == 0;
        }
    }

    public DrawerLayout(Context context) {
        this(context, null);
    }

    public static boolean isContentView(View view) {
        return ((LayoutParams) view.getLayoutParams()).gravity == 0;
    }

    public static boolean isDrawerOpen(View view) {
        if (isDrawerView(view)) {
            return (((LayoutParams) view.getLayoutParams()).openState & 1) == 1;
        }
        throw new IllegalArgumentException("View " + view + " is not a drawer");
    }

    public static boolean isDrawerView(View view) {
        int i = ((LayoutParams) view.getLayoutParams()).gravity;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int absoluteGravity = Gravity.getAbsoluteGravity(i, ViewCompat.Api17Impl.getLayoutDirection(view));
        return ((absoluteGravity & 3) == 0 && (absoluteGravity & 5) == 0) ? false : true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void addFocusables(ArrayList arrayList, int i, int i2) {
        if (getDescendantFocusability() == 393216) {
            return;
        }
        int childCount = getChildCount();
        boolean z = false;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (!isDrawerView(childAt)) {
                this.mNonDrawerViews.add(childAt);
            } else if (isDrawerOpen(childAt)) {
                childAt.addFocusables(arrayList, i, i2);
                z = true;
            }
        }
        if (!z) {
            int size = this.mNonDrawerViews.size();
            for (int i4 = 0; i4 < size; i4++) {
                View view = (View) this.mNonDrawerViews.get(i4);
                if (view.getVisibility() == 0) {
                    view.addFocusables(arrayList, i, i2);
                }
            }
        }
        this.mNonDrawerViews.clear();
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        if (findOpenDrawer() != null || isDrawerView(view)) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setImportantForAccessibility(view, 4);
        } else {
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setImportantForAccessibility(view, 1);
        }
    }

    public final boolean checkDrawerViewAbsoluteGravity(View view, int i) {
        return (getDrawerViewAbsoluteGravity(view) & i) == i;
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public final void closeDrawer(View view) {
        if (!isDrawerView(view)) {
            throw new IllegalArgumentException("View " + view + " is not a sliding drawer");
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (this.mFirstLayout) {
            layoutParams.onScreen = 0.0f;
            layoutParams.openState = 0;
        } else if (shouldSkipScroll()) {
            moveDrawerToOffset(view, 0.0f);
            updateDrawerState(view, 0);
            view.setVisibility(4);
        } else {
            layoutParams.openState |= 4;
            if (checkDrawerViewAbsoluteGravity(view, 3)) {
                this.mLeftDragger.smoothSlideViewTo(view, -view.getWidth(), view.getTop());
            } else {
                this.mRightDragger.smoothSlideViewTo(view, getWidth(), view.getTop());
            }
        }
        invalidate();
    }

    public final void closeDrawers(boolean z) {
        boolean smoothSlideViewTo;
        int childCount = getChildCount();
        boolean z2 = false;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (isDrawerView(childAt) && (!z || layoutParams.isPeeking)) {
                int width = childAt.getWidth();
                if (checkDrawerViewAbsoluteGravity(childAt, 3)) {
                    if (shouldSkipScroll()) {
                        moveDrawerToOffset(childAt, 0.0f);
                        updateDrawerState(childAt, 0);
                        childAt.setVisibility(4);
                        layoutParams.isPeeking = false;
                    } else {
                        smoothSlideViewTo = this.mLeftDragger.smoothSlideViewTo(childAt, -width, childAt.getTop());
                        z2 |= smoothSlideViewTo;
                        layoutParams.isPeeking = false;
                    }
                } else if (shouldSkipScroll()) {
                    moveDrawerToOffset(childAt, 0.0f);
                    updateDrawerState(childAt, 0);
                    childAt.setVisibility(4);
                    layoutParams.isPeeking = false;
                } else {
                    smoothSlideViewTo = this.mRightDragger.smoothSlideViewTo(childAt, getWidth(), childAt.getTop());
                    z2 |= smoothSlideViewTo;
                    layoutParams.isPeeking = false;
                }
            }
        }
        ViewDragCallback viewDragCallback = this.mLeftCallback;
        DrawerLayout.this.removeCallbacks(viewDragCallback.mPeekRunnable);
        ViewDragCallback viewDragCallback2 = this.mRightCallback;
        DrawerLayout.this.removeCallbacks(viewDragCallback2.mPeekRunnable);
        if (z2) {
            invalidate();
        }
    }

    @Override // android.view.View
    public final void computeScroll() {
        int childCount = getChildCount();
        float f = 0.0f;
        for (int i = 0; i < childCount; i++) {
            f = Math.max(f, ((LayoutParams) getChildAt(i).getLayoutParams()).onScreen);
        }
        this.mScrimOpacity = f;
        boolean continueSettling = this.mLeftDragger.continueSettling();
        boolean continueSettling2 = this.mRightDragger.continueSettling();
        if (continueSettling || continueSettling2) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.View
    public final boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        boolean dispatchGenericMotionEvent;
        if ((motionEvent.getSource() & 2) == 0 || motionEvent.getAction() == 10 || this.mScrimOpacity <= 0.0f) {
            return super.dispatchGenericMotionEvent(motionEvent);
        }
        int childCount = getChildCount();
        if (childCount == 0) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        for (int i = childCount - 1; i >= 0; i--) {
            View childAt = getChildAt(i);
            if (this.mChildHitRect == null) {
                this.mChildHitRect = new Rect();
            }
            childAt.getHitRect(this.mChildHitRect);
            if (this.mChildHitRect.contains((int) x, (int) y) && !isContentView(childAt)) {
                if (childAt.getMatrix().isIdentity()) {
                    float scrollX = getScrollX() - childAt.getLeft();
                    float scrollY = getScrollY() - childAt.getTop();
                    motionEvent.offsetLocation(scrollX, scrollY);
                    dispatchGenericMotionEvent = childAt.dispatchGenericMotionEvent(motionEvent);
                    motionEvent.offsetLocation(-scrollX, -scrollY);
                } else {
                    float scrollX2 = getScrollX() - childAt.getLeft();
                    float scrollY2 = getScrollY() - childAt.getTop();
                    MotionEvent obtain = MotionEvent.obtain(motionEvent);
                    obtain.offsetLocation(scrollX2, scrollY2);
                    Matrix matrix = childAt.getMatrix();
                    if (!matrix.isIdentity()) {
                        if (this.mChildInvertedMatrix == null) {
                            this.mChildInvertedMatrix = new Matrix();
                        }
                        matrix.invert(this.mChildInvertedMatrix);
                        obtain.transform(this.mChildInvertedMatrix);
                    }
                    dispatchGenericMotionEvent = childAt.dispatchGenericMotionEvent(obtain);
                    obtain.recycle();
                }
                if (dispatchGenericMotionEvent) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        int height = getHeight();
        boolean isContentView = isContentView(view);
        int width = getWidth();
        int save = canvas.save();
        int i = 0;
        if (isContentView) {
            int childCount = getChildCount();
            int i2 = 0;
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt != view && childAt.getVisibility() == 0) {
                    Drawable background = childAt.getBackground();
                    if ((background != null && background.getOpacity() == -1) && isDrawerView(childAt) && childAt.getHeight() >= height) {
                        if (checkDrawerViewAbsoluteGravity(childAt, 3)) {
                            int right = childAt.getRight();
                            if (right > i2) {
                                i2 = right;
                            }
                        } else {
                            int left = childAt.getLeft();
                            if (left < width) {
                                width = left;
                            }
                        }
                    }
                }
            }
            canvas.clipRect(i2, 0, width, getHeight());
            i = i2;
        }
        boolean drawChild = super.drawChild(canvas, view, j);
        canvas.restoreToCount(save);
        float f = this.mScrimOpacity;
        if (f > 0.0f && isContentView) {
            this.mScrimPaint.setColor((((int) ((((-16777216) & r15) >>> 24) * f)) << 24) | (this.mScrimColor & 16777215));
            canvas.drawRect(i, 0.0f, width, getHeight(), this.mScrimPaint);
        }
        return drawChild;
    }

    public final View findDrawerWithGravity(int i) {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int absoluteGravity = Gravity.getAbsoluteGravity(i, ViewCompat.Api17Impl.getLayoutDirection(this)) & 7;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if ((getDrawerViewAbsoluteGravity(childAt) & 7) == absoluteGravity) {
                return childAt;
            }
        }
        return null;
    }

    public final View findOpenDrawer() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if ((((LayoutParams) childAt.getLayoutParams()).openState & 1) == 1) {
                return childAt;
            }
        }
        return null;
    }

    public final View findVisibleDrawer() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (isDrawerView(childAt)) {
                if (!isDrawerView(childAt)) {
                    throw new IllegalArgumentException("View " + childAt + " is not a drawer");
                }
                if (((LayoutParams) childAt.getLayoutParams()).onScreen > 0.0f) {
                    return childAt;
                }
            }
        }
        return null;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    public final int getDrawerLockMode(View view) {
        if (!isDrawerView(view)) {
            throw new IllegalArgumentException("View " + view + " is not a drawer");
        }
        int i = ((LayoutParams) view.getLayoutParams()).gravity;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int layoutDirection = ViewCompat.Api17Impl.getLayoutDirection(this);
        if (i == 3) {
            int i2 = this.mLockModeLeft;
            if (i2 != 3) {
                return i2;
            }
            int i3 = layoutDirection == 0 ? this.mLockModeStart : this.mLockModeEnd;
            if (i3 != 3) {
                return i3;
            }
        } else if (i == 5) {
            int i4 = this.mLockModeRight;
            if (i4 != 3) {
                return i4;
            }
            int i5 = layoutDirection == 0 ? this.mLockModeEnd : this.mLockModeStart;
            if (i5 != 3) {
                return i5;
            }
        } else if (i == 8388611) {
            int i6 = this.mLockModeStart;
            if (i6 != 3) {
                return i6;
            }
            int i7 = layoutDirection == 0 ? this.mLockModeLeft : this.mLockModeRight;
            if (i7 != 3) {
                return i7;
            }
        } else if (i == 8388613) {
            int i8 = this.mLockModeEnd;
            if (i8 != 3) {
                return i8;
            }
            int i9 = layoutDirection == 0 ? this.mLockModeRight : this.mLockModeLeft;
            if (i9 != 3) {
                return i9;
            }
        }
        return 0;
    }

    public final int getDrawerViewAbsoluteGravity(View view) {
        int i = ((LayoutParams) view.getLayoutParams()).gravity;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        return Gravity.getAbsoluteGravity(i, ViewCompat.Api17Impl.getLayoutDirection(this));
    }

    public final void moveDrawerToOffset(View view, float f) {
        float f2 = ((LayoutParams) view.getLayoutParams()).onScreen;
        float width = view.getWidth();
        int i = ((int) (width * f)) - ((int) (f2 * width));
        if (!checkDrawerViewAbsoluteGravity(view, 3)) {
            i = -i;
        }
        view.offsetLeftAndRight(i);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (f == layoutParams.onScreen) {
            return;
        }
        layoutParams.onScreen = f;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.mDrawStatusBarBackground || this.mStatusBarBackground == null) {
            return;
        }
        Object obj = this.mLastInsets;
        int systemWindowInsetTop = obj != null ? ((WindowInsets) obj).getSystemWindowInsetTop() : 0;
        if (systemWindowInsetTop > 0) {
            this.mStatusBarBackground.setBounds(0, 0, getWidth(), systemWindowInsetTop);
            this.mStatusBarBackground.draw(canvas);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001b, code lost:
    
        if (r0 != 3) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0057 A[LOOP:1: B:31:0x0024->B:40:0x0057, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0055 A[SYNTHETIC] */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        View findTopChildUnder;
        boolean z2;
        boolean z3;
        boolean z4;
        int actionMasked = motionEvent.getActionMasked();
        boolean shouldInterceptTouchEvent = this.mLeftDragger.shouldInterceptTouchEvent(motionEvent) | this.mRightDragger.shouldInterceptTouchEvent(motionEvent);
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    ViewDragHelper viewDragHelper = this.mLeftDragger;
                    int length = viewDragHelper.mInitialMotionX.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            z3 = false;
                            break;
                        }
                        if ((viewDragHelper.mPointersDown & (1 << i)) != 0) {
                            float f = viewDragHelper.mLastMotionX[i] - viewDragHelper.mInitialMotionX[i];
                            float f2 = viewDragHelper.mLastMotionY[i] - viewDragHelper.mInitialMotionY[i];
                            float f3 = (f2 * f2) + (f * f);
                            int i2 = viewDragHelper.mTouchSlop;
                            if (f3 > i2 * i2) {
                                z4 = true;
                                if (!z4) {
                                    z3 = true;
                                    break;
                                }
                                i++;
                            }
                        }
                        z4 = false;
                        if (!z4) {
                        }
                    }
                    if (z3) {
                        ViewDragCallback viewDragCallback = this.mLeftCallback;
                        DrawerLayout.this.removeCallbacks(viewDragCallback.mPeekRunnable);
                        ViewDragCallback viewDragCallback2 = this.mRightCallback;
                        DrawerLayout.this.removeCallbacks(viewDragCallback2.mPeekRunnable);
                    }
                }
                z = false;
            }
            closeDrawers(true);
            this.mChildrenCanceledTouch = false;
            z = false;
        } else {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            this.mInitialMotionX = x;
            this.mInitialMotionY = y;
            z = this.mScrimOpacity > 0.0f && (findTopChildUnder = this.mLeftDragger.findTopChildUnder((int) x, (int) y)) != null && isContentView(findTopChildUnder);
            this.mChildrenCanceledTouch = false;
        }
        if (shouldInterceptTouchEvent || z) {
            return true;
        }
        int childCount = getChildCount();
        int i3 = 0;
        while (true) {
            if (i3 >= childCount) {
                z2 = false;
                break;
            }
            if (((LayoutParams) getChildAt(i3).getLayoutParams()).isPeeking) {
                z2 = true;
                break;
            }
            i3++;
        }
        return z2 || this.mChildrenCanceledTouch;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            if (findVisibleDrawer() != null) {
                keyEvent.startTracking();
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyUp(i, keyEvent);
        }
        View findVisibleDrawer = findVisibleDrawer();
        if (findVisibleDrawer != null && getDrawerLockMode(findVisibleDrawer) == 0) {
            closeDrawers(false);
        }
        return findVisibleDrawer != null;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00da A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0075  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float f;
        int i5;
        boolean z2;
        int i6;
        int i7;
        boolean z3 = true;
        this.mInLayout = true;
        int i8 = i3 - i;
        int childCount = getChildCount();
        int i9 = 0;
        while (i9 < childCount) {
            View childAt = getChildAt(i9);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (isContentView(childAt)) {
                    int i10 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
                    childAt.layout(i10, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, childAt.getMeasuredWidth() + i10, childAt.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin);
                } else {
                    int measuredWidth = childAt.getMeasuredWidth();
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (checkDrawerViewAbsoluteGravity(childAt, 3)) {
                        float f2 = measuredWidth;
                        i5 = (-measuredWidth) + ((int) (layoutParams.onScreen * f2));
                        if (measuredWidth != 0) {
                            f = (measuredWidth + i5) / f2;
                            z2 = f == layoutParams.onScreen ? z3 : false;
                            i6 = layoutParams.gravity & 112;
                            if (i6 != 16) {
                                int i11 = i4 - i2;
                                int i12 = (i11 - measuredHeight) / 2;
                                int i13 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
                                if (i12 < i13) {
                                    i12 = i13;
                                } else {
                                    int i14 = i12 + measuredHeight;
                                    int i15 = i11 - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
                                    if (i14 > i15) {
                                        i12 = i15 - measuredHeight;
                                    }
                                }
                                childAt.layout(i5, i12, measuredWidth + i5, measuredHeight + i12);
                            } else if (i6 != 80) {
                                int i16 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
                                childAt.layout(i5, i16, measuredWidth + i5, measuredHeight + i16);
                            } else {
                                int i17 = i4 - i2;
                                childAt.layout(i5, (i17 - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin) - childAt.getMeasuredHeight(), measuredWidth + i5, i17 - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
                            }
                            if (z2) {
                                LayoutParams layoutParams2 = (LayoutParams) childAt.getLayoutParams();
                                if (f != layoutParams2.onScreen) {
                                    layoutParams2.onScreen = f;
                                }
                            }
                            i7 = layoutParams.onScreen <= 0.0f ? 0 : 4;
                            if (childAt.getVisibility() == i7) {
                                childAt.setVisibility(i7);
                            }
                        }
                        f = 0.0f;
                        if (f == layoutParams.onScreen) {
                        }
                        i6 = layoutParams.gravity & 112;
                        if (i6 != 16) {
                        }
                        if (z2) {
                        }
                        if (layoutParams.onScreen <= 0.0f) {
                        }
                        if (childAt.getVisibility() == i7) {
                        }
                    } else {
                        float f3 = measuredWidth;
                        int i18 = i8 - ((int) (layoutParams.onScreen * f3));
                        if (measuredWidth == 0) {
                            i5 = i18;
                            f = 0.0f;
                            if (f == layoutParams.onScreen) {
                            }
                            i6 = layoutParams.gravity & 112;
                            if (i6 != 16) {
                            }
                            if (z2) {
                            }
                            if (layoutParams.onScreen <= 0.0f) {
                            }
                            if (childAt.getVisibility() == i7) {
                            }
                        } else {
                            f = (i8 - i18) / f3;
                            i5 = i18;
                            if (f == layoutParams.onScreen) {
                            }
                            i6 = layoutParams.gravity & 112;
                            if (i6 != 16) {
                            }
                            if (z2) {
                            }
                            if (layoutParams.onScreen <= 0.0f) {
                            }
                            if (childAt.getVisibility() == i7) {
                            }
                        }
                    }
                }
            }
            i9++;
            z3 = true;
        }
        WindowInsets rootWindowInsets = getRootWindowInsets();
        if (rootWindowInsets != null) {
            Insets systemGestureInsets = WindowInsetsCompat.toWindowInsetsCompat(null, rootWindowInsets).mImpl.getSystemGestureInsets();
            ViewDragHelper viewDragHelper = this.mLeftDragger;
            viewDragHelper.mEdgeSize = Math.max(viewDragHelper.mDefaultEdgeSize, systemGestureInsets.left);
            ViewDragHelper viewDragHelper2 = this.mRightDragger;
            viewDragHelper2.mEdgeSize = Math.max(viewDragHelper2.mDefaultEdgeSize, systemGestureInsets.right);
        }
        this.mInLayout = false;
        this.mFirstLayout = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0048  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMeasure(int i, int i2) {
        boolean z;
        int childCount;
        int i3;
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode != 1073741824 || mode2 != 1073741824) {
            if (!isInEditMode()) {
                throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
            }
            if (mode == 0) {
                size = 300;
            }
            if (mode2 == 0) {
                size2 = 300;
            }
        }
        setMeasuredDimension(size, size2);
        if (this.mLastInsets != null) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api16Impl.getFitsSystemWindows(this)) {
                z = true;
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                int layoutDirection = ViewCompat.Api17Impl.getLayoutDirection(this);
                childCount = getChildCount();
                boolean z2 = false;
                boolean z3 = false;
                for (i3 = 0; i3 < childCount; i3++) {
                    View childAt = getChildAt(i3);
                    if (childAt.getVisibility() != 8) {
                        LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                        if (z) {
                            int absoluteGravity = Gravity.getAbsoluteGravity(layoutParams.gravity, layoutDirection);
                            if (ViewCompat.Api16Impl.getFitsSystemWindows(childAt)) {
                                WindowInsets windowInsets = (WindowInsets) this.mLastInsets;
                                if (absoluteGravity == 3) {
                                    windowInsets = windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), 0, windowInsets.getSystemWindowInsetBottom());
                                } else if (absoluteGravity == 5) {
                                    windowInsets = windowInsets.replaceSystemWindowInsets(0, windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
                                }
                                childAt.dispatchApplyWindowInsets(windowInsets);
                            } else {
                                WindowInsets windowInsets2 = (WindowInsets) this.mLastInsets;
                                if (absoluteGravity == 3) {
                                    windowInsets2 = windowInsets2.replaceSystemWindowInsets(windowInsets2.getSystemWindowInsetLeft(), windowInsets2.getSystemWindowInsetTop(), 0, windowInsets2.getSystemWindowInsetBottom());
                                } else if (absoluteGravity == 5) {
                                    windowInsets2 = windowInsets2.replaceSystemWindowInsets(0, windowInsets2.getSystemWindowInsetTop(), windowInsets2.getSystemWindowInsetRight(), windowInsets2.getSystemWindowInsetBottom());
                                }
                                ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = windowInsets2.getSystemWindowInsetLeft();
                                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = windowInsets2.getSystemWindowInsetTop();
                                ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = windowInsets2.getSystemWindowInsetRight();
                                ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = windowInsets2.getSystemWindowInsetBottom();
                            }
                        }
                        if (isContentView(childAt)) {
                            childAt.measure(View.MeasureSpec.makeMeasureSpec((size - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec((size2 - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                        } else {
                            if (!isDrawerView(childAt)) {
                                throw new IllegalStateException("Child " + childAt + " at index " + i3 + " does not have a valid layout_gravity - must be Gravity.LEFT, Gravity.RIGHT or Gravity.NO_GRAVITY");
                            }
                            float elevation = ViewCompat.Api21Impl.getElevation(childAt);
                            float f = this.mDrawerElevation;
                            if (elevation != f) {
                                ViewCompat.Api21Impl.setElevation(childAt, f);
                            }
                            int drawerViewAbsoluteGravity = getDrawerViewAbsoluteGravity(childAt) & 7;
                            boolean z4 = drawerViewAbsoluteGravity == 3;
                            if ((z4 && z2) || (!z4 && z3)) {
                                throw new IllegalStateException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("Child drawer has absolute gravity "), (drawerViewAbsoluteGravity & 3) != 3 ? (drawerViewAbsoluteGravity & 5) == 5 ? "RIGHT" : Integer.toHexString(drawerViewAbsoluteGravity) : "LEFT", " but this DrawerLayout already has a drawer view along that edge"));
                            }
                            if (z4) {
                                z2 = true;
                            } else {
                                z3 = true;
                            }
                            childAt.measure(ViewGroup.getChildMeasureSpec(i, this.mMinDrawerMargin + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, ((ViewGroup.MarginLayoutParams) layoutParams).width), ViewGroup.getChildMeasureSpec(i2, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin, ((ViewGroup.MarginLayoutParams) layoutParams).height));
                        }
                    }
                }
            }
        }
        z = false;
        WeakHashMap weakHashMap22 = ViewCompat.sViewPropertyAnimatorMap;
        int layoutDirection2 = ViewCompat.Api17Impl.getLayoutDirection(this);
        childCount = getChildCount();
        boolean z22 = false;
        boolean z32 = false;
        while (i3 < childCount) {
        }
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        View findDrawerWithGravity;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.mSuperState);
        int i = savedState.openDrawerGravity;
        if (i != 0 && (findDrawerWithGravity = findDrawerWithGravity(i)) != null) {
            openDrawer(findDrawerWithGravity);
        }
        int i2 = savedState.lockModeLeft;
        if (i2 != 3) {
            setDrawerLockMode(i2, 3);
        }
        int i3 = savedState.lockModeRight;
        if (i3 != 3) {
            setDrawerLockMode(i3, 5);
        }
        int i4 = savedState.lockModeStart;
        if (i4 != 3) {
            setDrawerLockMode(i4, 8388611);
        }
        int i5 = savedState.lockModeEnd;
        if (i5 != 3) {
            setDrawerLockMode(i5, 8388613);
        }
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
            int i2 = layoutParams.openState;
            boolean z = i2 == 1;
            boolean z2 = i2 == 2;
            if (z || z2) {
                savedState.openDrawerGravity = layoutParams.gravity;
                break;
            }
        }
        savedState.lockModeLeft = this.mLockModeLeft;
        savedState.lockModeRight = this.mLockModeRight;
        savedState.lockModeStart = this.mLockModeStart;
        savedState.lockModeEnd = this.mLockModeEnd;
        return savedState;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0056, code lost:
    
        if (getDrawerLockMode(r7) != 2) goto L20;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        this.mLeftDragger.processTouchEvent(motionEvent);
        this.mRightDragger.processTouchEvent(motionEvent);
        int action = motionEvent.getAction() & 255;
        boolean z = false;
        if (action == 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            this.mInitialMotionX = x;
            this.mInitialMotionY = y;
            this.mChildrenCanceledTouch = false;
        } else if (action == 1) {
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            View findTopChildUnder = this.mLeftDragger.findTopChildUnder((int) x2, (int) y2);
            if (findTopChildUnder != null && isContentView(findTopChildUnder)) {
                float f = x2 - this.mInitialMotionX;
                float f2 = y2 - this.mInitialMotionY;
                int i = this.mLeftDragger.mTouchSlop;
                if ((f2 * f2) + (f * f) < i * i) {
                    View findOpenDrawer = findOpenDrawer();
                    if (findOpenDrawer != null) {
                    }
                }
            }
            z = true;
            closeDrawers(z);
        } else if (action == 3) {
            closeDrawers(true);
            this.mChildrenCanceledTouch = false;
        }
        return true;
    }

    public final void openDrawer(View view) {
        if (!isDrawerView(view)) {
            throw new IllegalArgumentException("View " + view + " is not a sliding drawer");
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (this.mFirstLayout) {
            layoutParams.onScreen = 1.0f;
            layoutParams.openState = 1;
            updateChildrenImportantForAccessibility(view, true);
            updateChildAccessibilityAction(view);
        } else if (shouldSkipScroll()) {
            moveDrawerToOffset(view, 1.0f);
            updateDrawerState(view, 0);
            view.setVisibility(0);
        } else {
            layoutParams.openState |= 2;
            if (checkDrawerViewAbsoluteGravity(view, 3)) {
                this.mLeftDragger.smoothSlideViewTo(view, 0, view.getTop());
            } else {
                this.mRightDragger.smoothSlideViewTo(view, getWidth() - view.getWidth(), view.getTop());
            }
        }
        invalidate();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
        if (z) {
            closeDrawers(true);
        }
    }

    @Override // android.view.View, android.view.ViewParent
    public final void requestLayout() {
        if (this.mInLayout) {
            return;
        }
        super.requestLayout();
    }

    public final void setDrawerLockMode(int i, int i2) {
        View findDrawerWithGravity;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int absoluteGravity = Gravity.getAbsoluteGravity(i2, ViewCompat.Api17Impl.getLayoutDirection(this));
        if (i2 == 3) {
            this.mLockModeLeft = i;
        } else if (i2 == 5) {
            this.mLockModeRight = i;
        } else if (i2 == 8388611) {
            this.mLockModeStart = i;
        } else if (i2 == 8388613) {
            this.mLockModeEnd = i;
        }
        if (i != 0) {
            (absoluteGravity == 3 ? this.mLeftDragger : this.mRightDragger).cancel();
        }
        if (i != 1) {
            if (i == 2 && (findDrawerWithGravity = findDrawerWithGravity(absoluteGravity)) != null) {
                openDrawer(findDrawerWithGravity);
                return;
            }
            return;
        }
        View findDrawerWithGravity2 = findDrawerWithGravity(absoluteGravity);
        if (findDrawerWithGravity2 != null) {
            closeDrawer(findDrawerWithGravity2);
        }
    }

    public final boolean shouldSkipScroll() {
        return Settings.System.getInt(getContext().getContentResolver(), "remove_animations", 0) == 1;
    }

    public final void updateChildAccessibilityAction(View view) {
        AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat = AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS;
        ViewCompat.removeActionWithId(view, accessibilityActionCompat.getId());
        ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
        if (!isDrawerOpen(view) || getDrawerLockMode(view) == 2) {
            return;
        }
        ViewCompat.replaceAccessibilityAction(view, accessibilityActionCompat, null, this.mActionDismiss);
    }

    public final void updateChildrenImportantForAccessibility(View view, boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if ((z || isDrawerView(childAt)) && !(z && childAt == view)) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.setImportantForAccessibility(childAt, 4);
            } else {
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.setImportantForAccessibility(childAt, 1);
            }
        }
    }

    public final void updateDrawerState(View view, int i) {
        int i2;
        View rootView;
        int i3 = this.mLeftDragger.mDragState;
        int i4 = this.mRightDragger.mDragState;
        if (i3 == 1 || i4 == 1) {
            i2 = 1;
        } else {
            i2 = 2;
            if (i3 != 2 && i4 != 2) {
                i2 = 0;
            }
        }
        if (view != null && i == 0) {
            float f = ((LayoutParams) view.getLayoutParams()).onScreen;
            if (f == 0.0f) {
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                if ((layoutParams.openState & 1) == 1) {
                    layoutParams.openState = 0;
                    updateChildrenImportantForAccessibility(view, false);
                    updateChildAccessibilityAction(view);
                    if (hasWindowFocus() && (rootView = getRootView()) != null) {
                        rootView.sendAccessibilityEvent(32);
                    }
                }
            } else if (f == 1.0f) {
                LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
                if ((layoutParams2.openState & 1) == 0) {
                    layoutParams2.openState = 1;
                    updateChildrenImportantForAccessibility(view, true);
                    updateChildAccessibilityAction(view);
                    if (hasWindowFocus()) {
                        sendAccessibilityEvent(32);
                    }
                }
            }
        }
        if (i2 != this.mDrawerState) {
            this.mDrawerState = i2;
        }
    }

    public DrawerLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.drawerLayoutStyle);
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [androidx.drawerlayout.widget.DrawerLayout$1] */
    public DrawerLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        new ChildAccessibilityDelegate();
        this.mScrimColor = -1728053248;
        this.mScrimPaint = new Paint();
        this.mFirstLayout = true;
        this.mLockModeLeft = 3;
        this.mLockModeRight = 3;
        this.mLockModeStart = 3;
        this.mLockModeEnd = 3;
        this.mActionDismiss = new AccessibilityViewCommand() { // from class: androidx.drawerlayout.widget.DrawerLayout.1
            @Override // androidx.core.view.accessibility.AccessibilityViewCommand
            public final boolean perform(View view) {
                DrawerLayout drawerLayout = DrawerLayout.this;
                drawerLayout.getClass();
                if (!DrawerLayout.isDrawerOpen(view) || drawerLayout.getDrawerLockMode(view) == 2) {
                    return false;
                }
                drawerLayout.closeDrawer(view);
                return true;
            }
        };
        setDescendantFocusability(262144);
        float f = getResources().getDisplayMetrics().density;
        this.mMinDrawerMargin = (int) ((56.0f * f) + 0.5f);
        float f2 = f * 400.0f;
        ViewDragCallback viewDragCallback = new ViewDragCallback(3);
        this.mLeftCallback = viewDragCallback;
        ViewDragCallback viewDragCallback2 = new ViewDragCallback(5);
        this.mRightCallback = viewDragCallback2;
        ViewDragHelper create = ViewDragHelper.create((ViewGroup) this, viewDragCallback);
        this.mLeftDragger = create;
        create.mTrackingEdges = 1;
        create.mMinVelocity = f2;
        viewDragCallback.mDragger = create;
        ViewDragHelper create2 = ViewDragHelper.create((ViewGroup) this, viewDragCallback2);
        this.mRightDragger = create2;
        create2.mTrackingEdges = 2;
        create2.mMinVelocity = f2;
        viewDragCallback2.mDragger = create2;
        setFocusableInTouchMode(true);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setImportantForAccessibility(this, 1);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
        setMotionEventSplittingEnabled(false);
        if (ViewCompat.Api16Impl.getFitsSystemWindows(this)) {
            setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener(this) { // from class: androidx.drawerlayout.widget.DrawerLayout.2
                @Override // android.view.View.OnApplyWindowInsetsListener
                public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    DrawerLayout drawerLayout = (DrawerLayout) view;
                    boolean z = windowInsets.getSystemWindowInsetTop() > 0;
                    drawerLayout.mLastInsets = windowInsets;
                    drawerLayout.mDrawStatusBarBackground = z;
                    drawerLayout.setWillNotDraw(!z && drawerLayout.getBackground() == null);
                    drawerLayout.requestLayout();
                    return windowInsets.consumeSystemWindowInsets();
                }
            });
            setSystemUiVisibility(PeripheralConstants.ErrorCode.ERROR_PLUGIN_CUSTOM_BASE);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(THEME_ATTRS);
            try {
                this.mStatusBarBackground = obtainStyledAttributes.getDrawable(0);
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R$styleable.DrawerLayout, i, 0);
        try {
            if (obtainStyledAttributes2.hasValue(0)) {
                this.mDrawerElevation = obtainStyledAttributes2.getDimension(0, 0.0f);
            } else {
                this.mDrawerElevation = getResources().getDimension(com.android.systemui.R.dimen.def_drawer_elevation);
            }
            obtainStyledAttributes2.recycle();
            this.mNonDrawerViews = new ArrayList();
        } catch (Throwable th) {
            obtainStyledAttributes2.recycle();
            throw th;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public final int gravity;
        public boolean isPeeking;
        public float onScreen;
        public int openState;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.gravity = 0;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, DrawerLayout.LAYOUT_ATTRS);
            this.gravity = obtainStyledAttributes.getInt(0, 0);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.gravity = 0;
        }

        public LayoutParams(int i, int i2, int i3) {
            this(i, i2);
            this.gravity = i3;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.gravity = 0;
            this.gravity = layoutParams.gravity;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = 0;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.gravity = 0;
        }
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: androidx.drawerlayout.widget.DrawerLayout.SavedState.1
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
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
        public int lockModeEnd;
        public int lockModeLeft;
        public int lockModeRight;
        public int lockModeStart;
        public int openDrawerGravity;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.openDrawerGravity = 0;
            this.openDrawerGravity = parcel.readInt();
            this.lockModeLeft = parcel.readInt();
            this.lockModeRight = parcel.readInt();
            this.lockModeStart = parcel.readInt();
            this.lockModeEnd = parcel.readInt();
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mSuperState, i);
            parcel.writeInt(this.openDrawerGravity);
            parcel.writeInt(this.lockModeLeft);
            parcel.writeInt(this.lockModeRight);
            parcel.writeInt(this.lockModeStart);
            parcel.writeInt(this.lockModeEnd);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
            this.openDrawerGravity = 0;
        }
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
    }
}
