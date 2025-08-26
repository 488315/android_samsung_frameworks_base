package androidx.coordinatorlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import androidx.collection.SimpleArrayMap;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.coordinatorlayout.R$styleable;
import androidx.core.util.Pools$SynchronizedPool;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.android.systemui.R;
import com.google.android.material.appbar.AppBarLayout;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class CoordinatorLayout extends ViewGroup implements NestedScrollingParent2, NestedScrollingParent3 {
    public static final Class[] CONSTRUCTOR_PARAMS;
    public static final ViewElevationComparator TOP_SORTED_CHILDREN_COMPARATOR;
    public static final String WIDGET_PACKAGE_NAME;
    public static final ThreadLocal sConstructors;
    public static final Pools$SynchronizedPool sRectPool;
    public AnonymousClass1 mApplyWindowInsetsListener;
    public final int[] mBehaviorConsumed;
    public View mBehaviorTouchView;
    public final DirectedAcyclicGraph mChildDag;
    public final List mDependencySortedChildren;
    public boolean mDisallowInterceptReset;
    public boolean mDrawStatusBarBackground;
    public final boolean mEnableAutoCollapsingKeyEvent;
    public boolean mIsAttachedToWindow;
    public final int[] mKeylines;
    public WindowInsetsCompat mLastInsets;
    public View mLastNestedScrollingChild;
    public boolean mNeedsPreDrawListener;
    public final NestedScrollingParentHelper mNestedScrollingParentHelper;
    public View mNestedScrollingTarget;
    public final int[] mNestedScrollingV2ConsumedCompat;
    public ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;
    public OnPreDrawListener mOnPreDrawListener;
    public final Drawable mStatusBarBackground;
    public final List mTempList1;
    public boolean mToolIsMouse;

    public interface AttachedBehavior {
        Behavior getBehavior();
    }

    public abstract class Behavior {
        public Behavior() {
        }

        public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
            return false;
        }

        public boolean getInsetDodgeRect(Rect rect, View view) {
            return false;
        }

        public boolean layoutDependsOn(View view, View view2) {
            return false;
        }

        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return false;
        }

        public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            return false;
        }

        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            return false;
        }

        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
            return false;
        }

        public boolean onNestedPreFling(View view, View view2, float f) {
            return false;
        }

        public void onNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int i3, int i4, int i5, int[] iArr) {
            iArr[0] = iArr[0] + i3;
            iArr[1] = iArr[1] + i4;
        }

        public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean z) {
            return false;
        }

        public Parcelable onSaveInstanceState(View view) {
            return View.BaseSavedState.EMPTY_STATE;
        }

        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i, int i2) {
            return false;
        }

        public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            return false;
        }

        public Behavior(Context context, AttributeSet attributeSet) {
        }

        public void onDetachedFromLayoutParams() {
        }

        public void onAttachedToLayoutParams(LayoutParams layoutParams) {
        }

        public void onDependentViewRemoved(CoordinatorLayout coordinatorLayout, View view) {
        }

        public void onRestoreInstanceState(View view, Parcelable parcelable) {
        }

        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i) {
        }

        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int[] iArr, int i3) {
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface DefaultBehavior {
        Class value();
    }

    public class HierarchyChangeListener implements ViewGroup.OnHierarchyChangeListener {
        public HierarchyChangeListener() {
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public final void onChildViewAdded(View view, View view2) {
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = CoordinatorLayout.this.mOnHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public final void onChildViewRemoved(View view, View view2) {
            CoordinatorLayout.this.onChildViewsChanged(2);
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = CoordinatorLayout.this.mOnHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }

    public class OnPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        public OnPreDrawListener() {
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public final boolean onPreDraw() {
            CoordinatorLayout.this.onChildViewsChanged(0);
            return true;
        }
    }

    public class ViewElevationComparator implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            float z = ViewCompat.Api21Impl.getZ((View) obj);
            float z2 = ViewCompat.Api21Impl.getZ((View) obj2);
            if (z > z2) {
                return -1;
            }
            return z < z2 ? 1 : 0;
        }
    }

    static {
        Package r0 = CoordinatorLayout.class.getPackage();
        WIDGET_PACKAGE_NAME = r0 != null ? r0.getName() : null;
        TOP_SORTED_CHILDREN_COMPARATOR = new ViewElevationComparator();
        CONSTRUCTOR_PARAMS = new Class[]{Context.class, AttributeSet.class};
        sConstructors = new ThreadLocal();
        sRectPool = new Pools$SynchronizedPool(12);
    }

    public CoordinatorLayout(Context context) {
        this(context, null);
    }

    public static Rect acquireTempRect() {
        Rect rect = (Rect) sRectPool.acquire();
        return rect == null ? new Rect() : rect;
    }

    public static void getDesiredAnchoredChildRectWithoutConstraints(int i, Rect rect, Rect rect2, LayoutParams layoutParams, int i2, int i3) {
        int i4 = layoutParams.gravity;
        if (i4 == 0) {
            i4 = 17;
        }
        int absoluteGravity = Gravity.getAbsoluteGravity(i4, i);
        int i5 = layoutParams.anchorGravity;
        if ((i5 & 7) == 0) {
            i5 |= 8388611;
        }
        if ((i5 & 112) == 0) {
            i5 |= 48;
        }
        int absoluteGravity2 = Gravity.getAbsoluteGravity(i5, i);
        int i6 = absoluteGravity & 7;
        int i7 = absoluteGravity & 112;
        int i8 = absoluteGravity2 & 7;
        int i9 = absoluteGravity2 & 112;
        int iWidth = i8 != 1 ? i8 != 5 ? rect.left : rect.right : rect.left + (rect.width() / 2);
        int iHeight = i9 != 16 ? i9 != 80 ? rect.top : rect.bottom : rect.top + (rect.height() / 2);
        if (i6 == 1) {
            iWidth -= i2 / 2;
        } else if (i6 != 5) {
            iWidth -= i2;
        }
        if (i7 == 16) {
            iHeight -= i3 / 2;
        } else if (i7 != 80) {
            iHeight -= i3;
        }
        rect2.set(iWidth, iHeight, i2 + iWidth, i3 + iHeight);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static LayoutParams getResolvedLayoutParams(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (!layoutParams.mBehaviorResolved) {
            if (view instanceof AttachedBehavior) {
                Behavior behavior = ((AttachedBehavior) view).getBehavior();
                if (behavior == null) {
                    Log.e("CoordinatorLayout", "Attached behavior class is null");
                }
                layoutParams.setBehavior(behavior);
                layoutParams.mBehaviorResolved = true;
                return layoutParams;
            }
            DefaultBehavior defaultBehavior = null;
            for (Class<?> superclass = view.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
                defaultBehavior = (DefaultBehavior) superclass.getAnnotation(DefaultBehavior.class);
                if (defaultBehavior != null) {
                    break;
                }
            }
            if (defaultBehavior != null) {
                try {
                    Class[] clsArr = new Class[0];
                    layoutParams.setBehavior((Behavior) defaultBehavior.value().getDeclaredConstructor(null).newInstance(null));
                } catch (Exception e) {
                    Log.e("CoordinatorLayout", "Default behavior class " + defaultBehavior.value().getName() + " could not be instantiated. Did you forget a default constructor?", e);
                }
            }
            layoutParams.mBehaviorResolved = true;
        }
        return layoutParams;
    }

    public static void setInsetOffsetX(View view, int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i2 = layoutParams.mInsetOffsetX;
        if (i2 != i) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            view.offsetLeftAndRight(i - i2);
            layoutParams.mInsetOffsetX = i;
        }
    }

    public static void setInsetOffsetY(View view, int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i2 = layoutParams.mInsetOffsetY;
        if (i2 != i) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            view.offsetTopAndBottom(i - i2);
            layoutParams.mInsetOffsetY = i;
        }
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public final void constrainChildRect(LayoutParams layoutParams, Rect rect, int i, int i2) {
        int width = getWidth();
        int height = getHeight();
        int iMax = Math.max(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, Math.min(rect.left, ((width - getPaddingRight()) - i) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin));
        int iMax2 = Math.max(getPaddingTop() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, Math.min(rect.top, ((height - getPaddingBottom()) - i2) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin));
        rect.set(iMax, iMax2, i + iMax, i2 + iMax2);
    }

    public final void dispatchDependentViewsChanged(View view) {
        ArrayList arrayList = (ArrayList) this.mChildDag.mGraph.get(view);
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            View view2 = (View) arrayList.get(i);
            Behavior behavior = ((LayoutParams) view2.getLayoutParams()).mBehavior;
            if (behavior != null) {
                behavior.onDependentViewChanged(this, view2, view);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View
    public final boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        int childCount = getChildCount() - 1;
        while (true) {
            if (childCount < 0) {
                break;
            }
            View childAt = getChildAt(childCount);
            Behavior behavior = ((LayoutParams) childAt.getLayoutParams()).mBehavior;
            if (behavior != null) {
                behavior.dispatchGenericMotionEvent(motionEvent);
            }
            if (childAt instanceof AppBarLayoutBehavior) {
                AppBarLayoutBehavior appBarLayoutBehavior = (AppBarLayoutBehavior) childAt;
                boolean z = motionEvent.getToolType(0) == 3;
                if (this.mToolIsMouse != z) {
                    this.mToolIsMouse = z;
                    ((AppBarLayout) appBarLayoutBehavior).isMouse = z;
                }
                if (motionEvent.getAction() == 8) {
                    if (this.mLastNestedScrollingChild != null) {
                        if (motionEvent.getAxisValue(9) < 0.0f) {
                            ((AppBarLayout) appBarLayoutBehavior).setExpanded(false);
                        } else if (motionEvent.getAxisValue(9) > 0.0f && !this.mLastNestedScrollingChild.canScrollVertically(-1)) {
                            ((AppBarLayout) appBarLayoutBehavior).setExpanded(true);
                        }
                    } else if (motionEvent.getAxisValue(9) < 0.0f) {
                        ((AppBarLayout) appBarLayoutBehavior).setExpanded(false);
                    } else if (motionEvent.getAxisValue(9) > 0.0f) {
                        ((AppBarLayout) appBarLayoutBehavior).setExpanded(true);
                    }
                }
            } else {
                childCount--;
            }
        }
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.mEnableAutoCollapsingKeyEvent && (keyEvent.getKeyCode() == 61 || keyEvent.getKeyCode() == 19 || keyEvent.getKeyCode() == 20 || keyEvent.getKeyCode() == 21 || keyEvent.getKeyCode() == 22)) {
            int childCount = getChildCount();
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    break;
                }
                KeyEvent.Callback childAt = getChildAt(i);
                if (childAt instanceof AppBarLayoutBehavior) {
                    AppBarLayout appBarLayout = (AppBarLayout) ((AppBarLayoutBehavior) childAt);
                    if (!appBarLayout.lifted) {
                        appBarLayout.setExpanded(false);
                        break;
                    }
                }
                i++;
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        Behavior behavior = ((LayoutParams) view.getLayoutParams()).mBehavior;
        if (behavior != null) {
            behavior.getClass();
        }
        return super.drawChild(canvas, view, j);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.mStatusBarBackground;
        if ((drawable == null || !drawable.isStateful()) ? false : drawable.setState(drawableState)) {
            invalidate();
        }
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public final void getChildRect(View view, Rect rect, boolean z) {
        if (view.isLayoutRequested() || view.getVisibility() == 8) {
            rect.setEmpty();
        } else if (z) {
            getDescendantRect(rect, view);
        } else {
            rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
    }

    public final List getDependencies(View view) {
        SimpleArrayMap simpleArrayMap = this.mChildDag.mGraph;
        int i = simpleArrayMap.size;
        ArrayList arrayList = null;
        for (int i2 = 0; i2 < i; i2++) {
            ArrayList arrayList2 = (ArrayList) simpleArrayMap.valueAt(i2);
            if (arrayList2 != null && arrayList2.contains(view)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(simpleArrayMap.keyAt(i2));
            }
        }
        return arrayList == null ? Collections.EMPTY_LIST : arrayList;
    }

    public final void getDescendantRect(Rect rect, View view) {
        ThreadLocal threadLocal = ViewGroupUtils.sMatrix;
        rect.set(0, 0, view.getWidth(), view.getHeight());
        ThreadLocal threadLocal2 = ViewGroupUtils.sMatrix;
        Matrix matrix = (Matrix) threadLocal2.get();
        if (matrix == null) {
            matrix = new Matrix();
            threadLocal2.set(matrix);
        } else {
            matrix.reset();
        }
        ViewGroupUtils.offsetDescendantMatrix(this, view, matrix);
        ThreadLocal threadLocal3 = ViewGroupUtils.sRectF;
        RectF rectF = (RectF) threadLocal3.get();
        if (rectF == null) {
            rectF = new RectF();
            threadLocal3.set(rectF);
        }
        rectF.set(rect);
        matrix.mapRect(rectF);
        rect.set((int) (rectF.left + 0.5f), (int) (rectF.top + 0.5f), (int) (rectF.right + 0.5f), (int) (rectF.bottom + 0.5f));
    }

    public final int getKeyline(int i) {
        int[] iArr = this.mKeylines;
        if (iArr == null) {
            Log.e("CoordinatorLayout", "No keylines defined for " + this + " - attempted index lookup " + i);
            return 0;
        }
        if (i >= 0 && i < iArr.length) {
            return iArr[i];
        }
        Log.e("CoordinatorLayout", "Keyline index " + i + " out of range for " + this);
        return 0;
    }

    @Override // android.view.ViewGroup
    public final int getNestedScrollAxes() {
        NestedScrollingParentHelper nestedScrollingParentHelper = this.mNestedScrollingParentHelper;
        return nestedScrollingParentHelper.mNestedScrollAxesNonTouch | nestedScrollingParentHelper.mNestedScrollAxesTouch;
    }

    @Override // android.view.View
    public final int getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), getPaddingBottom() + getPaddingTop());
    }

    @Override // android.view.View
    public final int getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), getPaddingRight() + getPaddingLeft());
    }

    public final boolean isPointInChildBounds(View view, int i, int i2) {
        Rect rectAcquireTempRect = acquireTempRect();
        getDescendantRect(rectAcquireTempRect, view);
        try {
            return rectAcquireTempRect.contains(i, i2);
        } finally {
            rectAcquireTempRect.setEmpty();
            sRectPool.release(rectAcquireTempRect);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        resetTouchBehaviors();
        if (this.mNeedsPreDrawListener) {
            if (this.mOnPreDrawListener == null) {
                this.mOnPreDrawListener = new OnPreDrawListener();
            }
            getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
        }
        if (this.mLastInsets == null) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (getFitsSystemWindows()) {
                ViewCompat.Api20Impl.requestApplyInsets(this);
            }
        }
        this.mIsAttachedToWindow = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x026f  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x029f  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00e1  */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [int] */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.util.ArrayList] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onChildViewsChanged(int i) {
        Rect rect;
        int i2;
        int i3;
        int i4;
        int i5;
        boolean zOnDependentViewChanged;
        boolean z;
        boolean z2;
        int width;
        int i6;
        int i7;
        int i8;
        int height;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        Rect rect2;
        int i14;
        View view;
        LayoutParams layoutParams;
        Behavior behavior;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int layoutDirection = getLayoutDirection();
        int size = ((ArrayList) this.mDependencySortedChildren).size();
        Rect rectAcquireTempRect = acquireTempRect();
        Rect rectAcquireTempRect2 = acquireTempRect();
        Rect rectAcquireTempRect3 = acquireTempRect();
        boolean z3 = false;
        int i15 = 0;
        while (i15 < size) {
            View view2 = (View) ((ArrayList) this.mDependencySortedChildren).get(i15);
            LayoutParams layoutParams2 = (LayoutParams) view2.getLayoutParams();
            if (i == 0 && view2.getVisibility() == 8) {
                i4 = size;
                rect = rectAcquireTempRect3;
                i2 = i15;
            } else {
                ?? r2 = z3;
                while (r2 < i15) {
                    if (layoutParams2.mAnchorDirectChild == ((View) ((ArrayList) this.mDependencySortedChildren).get(r2))) {
                        LayoutParams layoutParams3 = (LayoutParams) view2.getLayoutParams();
                        if (layoutParams3.mAnchorView != null) {
                            rect2 = rectAcquireTempRect3;
                            Rect rectAcquireTempRect4 = acquireTempRect();
                            Rect rectAcquireTempRect5 = acquireTempRect();
                            Rect rectAcquireTempRect6 = acquireTempRect();
                            getDescendantRect(rectAcquireTempRect4, layoutParams3.mAnchorView);
                            getChildRect(view2, rectAcquireTempRect5, z3);
                            View view3 = view2;
                            int measuredWidth = view3.getMeasuredWidth();
                            LayoutParams layoutParams4 = layoutParams2;
                            int measuredHeight = view3.getMeasuredHeight();
                            i12 = r2;
                            layoutParams = layoutParams4;
                            view = view3;
                            getDesiredAnchoredChildRectWithoutConstraints(layoutDirection, rectAcquireTempRect4, rectAcquireTempRect6, layoutParams3, measuredWidth, measuredHeight);
                            i14 = i15;
                            i13 = size;
                            boolean z4 = (rectAcquireTempRect6.left == rectAcquireTempRect5.left && rectAcquireTempRect6.top == rectAcquireTempRect5.top) ? false : true;
                            constrainChildRect(layoutParams3, rectAcquireTempRect6, measuredWidth, measuredHeight);
                            int i16 = rectAcquireTempRect6.left - rectAcquireTempRect5.left;
                            int i17 = rectAcquireTempRect6.top - rectAcquireTempRect5.top;
                            if (i16 != 0) {
                                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                                view.offsetLeftAndRight(i16);
                            }
                            if (i17 != 0) {
                                WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                                view.offsetTopAndBottom(i17);
                            }
                            if (z4 && (behavior = layoutParams3.mBehavior) != null) {
                                behavior.onDependentViewChanged(this, view, layoutParams3.mAnchorView);
                            }
                            rectAcquireTempRect4.setEmpty();
                            Pools$SynchronizedPool pools$SynchronizedPool = sRectPool;
                            pools$SynchronizedPool.release(rectAcquireTempRect4);
                            rectAcquireTempRect5.setEmpty();
                            pools$SynchronizedPool.release(rectAcquireTempRect5);
                            rectAcquireTempRect6.setEmpty();
                            pools$SynchronizedPool.release(rectAcquireTempRect6);
                        } else {
                            i12 = r2;
                            i13 = size;
                            rect2 = rectAcquireTempRect3;
                            i14 = i15;
                            view = view2;
                            layoutParams = layoutParams2;
                        }
                    }
                    layoutParams2 = layoutParams;
                    view2 = view;
                    rectAcquireTempRect3 = rect2;
                    r2 = i12 + 1;
                    i15 = i14;
                    size = i13;
                    z3 = false;
                }
                int i18 = size;
                rect = rectAcquireTempRect3;
                i2 = i15;
                View view4 = view2;
                LayoutParams layoutParams5 = layoutParams2;
                getChildRect(view4, rectAcquireTempRect2, true);
                if (layoutParams5.insetEdge != 0 && !rectAcquireTempRect2.isEmpty()) {
                    int absoluteGravity = Gravity.getAbsoluteGravity(layoutParams5.insetEdge, layoutDirection);
                    int i19 = absoluteGravity & 112;
                    if (i19 == 48) {
                        rectAcquireTempRect.top = Math.max(rectAcquireTempRect.top, rectAcquireTempRect2.bottom);
                    } else if (i19 == 80) {
                        rectAcquireTempRect.bottom = Math.max(rectAcquireTempRect.bottom, getHeight() - rectAcquireTempRect2.top);
                    }
                    int i20 = absoluteGravity & 7;
                    if (i20 == 3) {
                        rectAcquireTempRect.left = Math.max(rectAcquireTempRect.left, rectAcquireTempRect2.right);
                    } else if (i20 == 5) {
                        rectAcquireTempRect.right = Math.max(rectAcquireTempRect.right, getWidth() - rectAcquireTempRect2.left);
                    }
                }
                if (layoutParams5.dodgeInsetEdges != 0 && view4.getVisibility() == 0) {
                    WeakHashMap weakHashMap4 = ViewCompat.sViewPropertyAnimatorMap;
                    if (view4.isLaidOut() && view4.getWidth() > 0 && view4.getHeight() > 0) {
                        LayoutParams layoutParams6 = (LayoutParams) view4.getLayoutParams();
                        Behavior behavior2 = layoutParams6.mBehavior;
                        Rect rectAcquireTempRect7 = acquireTempRect();
                        Rect rectAcquireTempRect8 = acquireTempRect();
                        rectAcquireTempRect8.set(view4.getLeft(), view4.getTop(), view4.getRight(), view4.getBottom());
                        if (behavior2 == null || !behavior2.getInsetDodgeRect(rectAcquireTempRect7, view4)) {
                            rectAcquireTempRect7.set(rectAcquireTempRect8);
                        } else if (!rectAcquireTempRect8.contains(rectAcquireTempRect7)) {
                            throw new IllegalArgumentException("Rect should be within the child's bounds. Rect:" + rectAcquireTempRect7.toShortString() + " | Bounds:" + rectAcquireTempRect8.toShortString());
                        }
                        rectAcquireTempRect8.setEmpty();
                        Pools$SynchronizedPool pools$SynchronizedPool2 = sRectPool;
                        pools$SynchronizedPool2.release(rectAcquireTempRect8);
                        if (rectAcquireTempRect7.isEmpty()) {
                            rectAcquireTempRect7.setEmpty();
                            pools$SynchronizedPool2.release(rectAcquireTempRect7);
                            if (i != 2) {
                            }
                        } else {
                            int absoluteGravity2 = Gravity.getAbsoluteGravity(layoutParams6.dodgeInsetEdges, layoutDirection);
                            if ((absoluteGravity2 & 48) != 48 || (i10 = (rectAcquireTempRect7.top - ((ViewGroup.MarginLayoutParams) layoutParams6).topMargin) - layoutParams6.mInsetOffsetY) >= (i11 = rectAcquireTempRect.top)) {
                                z = false;
                            } else {
                                setInsetOffsetY(view4, i11 - i10);
                                z = true;
                            }
                            if ((absoluteGravity2 & 80) == 80 && (height = ((getHeight() - rectAcquireTempRect7.bottom) - ((ViewGroup.MarginLayoutParams) layoutParams6).bottomMargin) + layoutParams6.mInsetOffsetY) < (i9 = rectAcquireTempRect.bottom)) {
                                setInsetOffsetY(view4, height - i9);
                                z = true;
                            }
                            if (!z) {
                                setInsetOffsetY(view4, 0);
                            }
                            if ((absoluteGravity2 & 3) != 3 || (i7 = (rectAcquireTempRect7.left - ((ViewGroup.MarginLayoutParams) layoutParams6).leftMargin) - layoutParams6.mInsetOffsetX) >= (i8 = rectAcquireTempRect.left)) {
                                z2 = false;
                            } else {
                                setInsetOffsetX(view4, i8 - i7);
                                z2 = true;
                            }
                            if ((absoluteGravity2 & 5) == 5 && (width = ((getWidth() - rectAcquireTempRect7.right) - ((ViewGroup.MarginLayoutParams) layoutParams6).rightMargin) + layoutParams6.mInsetOffsetX) < (i6 = rectAcquireTempRect.right)) {
                                setInsetOffsetX(view4, width - i6);
                                z2 = true;
                            }
                            if (!z2) {
                                setInsetOffsetX(view4, 0);
                            }
                            rectAcquireTempRect7.setEmpty();
                            pools$SynchronizedPool2.release(rectAcquireTempRect7);
                            if (i != 2) {
                            }
                        }
                    }
                } else if (i != 2) {
                    rect.set(((LayoutParams) view4.getLayoutParams()).mLastChildRect);
                    if (rect.equals(rectAcquireTempRect2)) {
                        i4 = i18;
                        z3 = false;
                    } else {
                        ((LayoutParams) view4.getLayoutParams()).mLastChildRect.set(rectAcquireTempRect2);
                        i3 = i2 + 1;
                        i4 = i18;
                        while (i3 < i4) {
                            View view5 = (View) ((ArrayList) this.mDependencySortedChildren).get(i3);
                            LayoutParams layoutParams7 = (LayoutParams) view5.getLayoutParams();
                            Behavior behavior3 = layoutParams7.mBehavior;
                            if (behavior3 != null && behavior3.layoutDependsOn(view5, view4)) {
                                if (i == 0 && layoutParams7.mDidChangeAfterNestedScroll) {
                                    layoutParams7.mDidChangeAfterNestedScroll = false;
                                } else {
                                    if (i != 2) {
                                        zOnDependentViewChanged = behavior3.onDependentViewChanged(this, view5, view4);
                                    } else {
                                        behavior3.onDependentViewRemoved(this, view4);
                                        zOnDependentViewChanged = true;
                                    }
                                    i5 = 1;
                                    if (i == 1) {
                                        layoutParams7.mDidChangeAfterNestedScroll = zOnDependentViewChanged;
                                    }
                                    i3 += i5;
                                }
                            }
                            i5 = 1;
                            i3 += i5;
                        }
                        z3 = false;
                    }
                } else {
                    i3 = i2 + 1;
                    i4 = i18;
                    while (i3 < i4) {
                    }
                    z3 = false;
                }
            }
            i15 = i2 + 1;
            rectAcquireTempRect3 = rect;
            size = i4;
        }
        Rect rect3 = rectAcquireTempRect3;
        rectAcquireTempRect.setEmpty();
        Pools$SynchronizedPool pools$SynchronizedPool3 = sRectPool;
        pools$SynchronizedPool3.release(rectAcquireTempRect);
        rectAcquireTempRect2.setEmpty();
        pools$SynchronizedPool3.release(rectAcquireTempRect2);
        rect3.setEmpty();
        pools$SynchronizedPool3.release(rect3);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        resetTouchBehaviors();
        if (this.mNeedsPreDrawListener && this.mOnPreDrawListener != null) {
            getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
        }
        View view = this.mNestedScrollingTarget;
        if (view != null) {
            this.mLastNestedScrollingChild = view;
            onStopNestedScroll(view, 0);
        }
        this.mIsAttachedToWindow = false;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.mDrawStatusBarBackground || this.mStatusBarBackground == null) {
            return;
        }
        WindowInsetsCompat windowInsetsCompat = this.mLastInsets;
        int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
        if (systemWindowInsetTop > 0) {
            this.mStatusBarBackground.setBounds(0, 0, getWidth(), systemWindowInsetTop);
            this.mStatusBarBackground.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                KeyEvent.Callback childAt = getChildAt(childCount);
                if (childAt instanceof AppBarLayoutBehavior) {
                    AppBarLayoutBehavior appBarLayoutBehavior = (AppBarLayoutBehavior) childAt;
                    boolean z = motionEvent.getToolType(0) == 3;
                    if (this.mToolIsMouse != z) {
                        this.mToolIsMouse = z;
                        ((AppBarLayout) appBarLayoutBehavior).isMouse = z;
                    }
                }
            }
            resetTouchBehaviors();
        }
        boolean zPerformIntercept = performIntercept(motionEvent, 0);
        if (actionMasked != 1 && actionMasked != 3) {
            return zPerformIntercept;
        }
        this.mBehaviorTouchView = null;
        resetTouchBehaviors();
        return zPerformIntercept;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Behavior behavior;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int layoutDirection = getLayoutDirection();
        int size = ((ArrayList) this.mDependencySortedChildren).size();
        for (int i5 = 0; i5 < size; i5++) {
            View view = (View) ((ArrayList) this.mDependencySortedChildren).get(i5);
            if (view.getVisibility() != 8 && ((behavior = ((LayoutParams) view.getLayoutParams()).mBehavior) == null || !behavior.onLayoutChild(this, view, layoutDirection))) {
                onLayoutChild(view, layoutDirection);
            }
        }
    }

    public final void onLayoutChild(View view, int i) {
        Rect rectAcquireTempRect;
        Rect rectAcquireTempRect2;
        Pools$SynchronizedPool pools$SynchronizedPool;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        View view2 = layoutParams.mAnchorView;
        if (view2 == null && layoutParams.mAnchorId != -1) {
            throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
        }
        if (view2 != null) {
            rectAcquireTempRect = acquireTempRect();
            rectAcquireTempRect2 = acquireTempRect();
            try {
                getDescendantRect(rectAcquireTempRect, view2);
                LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
                int measuredWidth = view.getMeasuredWidth();
                int measuredHeight = view.getMeasuredHeight();
                getDesiredAnchoredChildRectWithoutConstraints(i, rectAcquireTempRect, rectAcquireTempRect2, layoutParams2, measuredWidth, measuredHeight);
                constrainChildRect(layoutParams2, rectAcquireTempRect2, measuredWidth, measuredHeight);
                view.layout(rectAcquireTempRect2.left, rectAcquireTempRect2.top, rectAcquireTempRect2.right, rectAcquireTempRect2.bottom);
                return;
            } finally {
                rectAcquireTempRect.setEmpty();
                pools$SynchronizedPool = sRectPool;
                pools$SynchronizedPool.release(rectAcquireTempRect);
                rectAcquireTempRect2.setEmpty();
                pools$SynchronizedPool.release(rectAcquireTempRect2);
            }
        }
        int i2 = layoutParams.keyline;
        if (i2 < 0) {
            LayoutParams layoutParams3 = (LayoutParams) view.getLayoutParams();
            rectAcquireTempRect = acquireTempRect();
            rectAcquireTempRect.set(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams3).leftMargin, getPaddingTop() + ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin, (getWidth() - getPaddingRight()) - ((ViewGroup.MarginLayoutParams) layoutParams3).rightMargin, (getHeight() - getPaddingBottom()) - ((ViewGroup.MarginLayoutParams) layoutParams3).bottomMargin);
            if (this.mLastInsets != null) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (getFitsSystemWindows() && !view.getFitsSystemWindows()) {
                    rectAcquireTempRect.left = this.mLastInsets.getSystemWindowInsetLeft() + rectAcquireTempRect.left;
                    rectAcquireTempRect.top = this.mLastInsets.getSystemWindowInsetTop() + rectAcquireTempRect.top;
                    rectAcquireTempRect.right -= this.mLastInsets.getSystemWindowInsetRight();
                    rectAcquireTempRect.bottom -= this.mLastInsets.getSystemWindowInsetBottom();
                }
            }
            rectAcquireTempRect2 = acquireTempRect();
            int i3 = layoutParams3.gravity;
            if ((i3 & 7) == 0) {
                i3 |= 8388611;
            }
            if ((i3 & 112) == 0) {
                i3 |= 48;
            }
            Gravity.apply(i3, view.getMeasuredWidth(), view.getMeasuredHeight(), rectAcquireTempRect, rectAcquireTempRect2, i);
            view.layout(rectAcquireTempRect2.left, rectAcquireTempRect2.top, rectAcquireTempRect2.right, rectAcquireTempRect2.bottom);
            return;
        }
        LayoutParams layoutParams4 = (LayoutParams) view.getLayoutParams();
        int i4 = layoutParams4.gravity;
        if (i4 == 0) {
            i4 = 8388661;
        }
        int absoluteGravity = Gravity.getAbsoluteGravity(i4, i);
        int i5 = absoluteGravity & 7;
        int i6 = absoluteGravity & 112;
        int width = getWidth();
        int height = getHeight();
        int measuredWidth2 = view.getMeasuredWidth();
        int measuredHeight2 = view.getMeasuredHeight();
        if (i == 1) {
            i2 = width - i2;
        }
        int keyline = getKeyline(i2) - measuredWidth2;
        if (i5 == 1) {
            keyline += measuredWidth2 / 2;
        } else if (i5 == 5) {
            keyline += measuredWidth2;
        }
        int i7 = i6 != 16 ? i6 != 80 ? 0 : measuredHeight2 : measuredHeight2 / 2;
        int iMax = Math.max(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams4).leftMargin, Math.min(keyline, ((width - getPaddingRight()) - measuredWidth2) - ((ViewGroup.MarginLayoutParams) layoutParams4).rightMargin));
        int iMax2 = Math.max(getPaddingTop() + ((ViewGroup.MarginLayoutParams) layoutParams4).topMargin, Math.min(i7, ((height - getPaddingBottom()) - measuredHeight2) - ((ViewGroup.MarginLayoutParams) layoutParams4).bottomMargin));
        view.layout(iMax, iMax2, measuredWidth2 + iMax, measuredHeight2 + iMax2);
    }

    /* JADX WARN: Removed duplicated region for block: B:167:0x030a  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0314  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0336  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x010b  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMeasure(int i, int i2) {
        boolean z;
        int i3;
        int i4;
        int i5;
        int i6;
        int iMakeMeasureSpec;
        int iMakeMeasureSpec2;
        Behavior behavior;
        int i7;
        View view;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        boolean z2;
        int i13;
        int i14;
        int iMax;
        View viewFindViewById;
        Behavior behavior2;
        CoordinatorLayout coordinatorLayout = this;
        boolean z3 = true;
        ((ArrayList) coordinatorLayout.mDependencySortedChildren).clear();
        DirectedAcyclicGraph directedAcyclicGraph = coordinatorLayout.mChildDag;
        SimpleArrayMap simpleArrayMap = directedAcyclicGraph.mGraph;
        int i15 = simpleArrayMap.size;
        for (int i16 = 0; i16 < i15; i16++) {
            ArrayList arrayList = (ArrayList) simpleArrayMap.valueAt(i16);
            if (arrayList != null) {
                arrayList.clear();
                directedAcyclicGraph.mListPool.release(arrayList);
            }
        }
        simpleArrayMap.clear();
        int childCount = coordinatorLayout.getChildCount();
        for (int i17 = 0; i17 < childCount; i17++) {
            View childAt = coordinatorLayout.getChildAt(i17);
            LayoutParams resolvedLayoutParams = getResolvedLayoutParams(childAt);
            if (resolvedLayoutParams.mAnchorId == -1) {
                resolvedLayoutParams.mAnchorDirectChild = null;
                resolvedLayoutParams.mAnchorView = null;
            } else {
                View view2 = resolvedLayoutParams.mAnchorView;
                if (view2 == null || view2.getId() != resolvedLayoutParams.mAnchorId) {
                    viewFindViewById = coordinatorLayout.findViewById(resolvedLayoutParams.mAnchorId);
                    resolvedLayoutParams.mAnchorView = viewFindViewById;
                    if (viewFindViewById != null) {
                        if (!coordinatorLayout.isInEditMode()) {
                            throw new IllegalStateException("Could not find CoordinatorLayout descendant view with id " + coordinatorLayout.getResources().getResourceName(resolvedLayoutParams.mAnchorId) + " to anchor view " + childAt);
                        }
                        resolvedLayoutParams.mAnchorDirectChild = null;
                        resolvedLayoutParams.mAnchorView = null;
                    } else if (viewFindViewById != coordinatorLayout) {
                        for (ViewParent parent = viewFindViewById.getParent(); parent != coordinatorLayout && parent != null; parent = parent.getParent()) {
                            if (parent != childAt) {
                                if (parent instanceof View) {
                                    viewFindViewById = parent;
                                }
                            } else {
                                if (!coordinatorLayout.isInEditMode()) {
                                    throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
                                }
                                resolvedLayoutParams.mAnchorDirectChild = null;
                                resolvedLayoutParams.mAnchorView = null;
                            }
                        }
                        resolvedLayoutParams.mAnchorDirectChild = viewFindViewById;
                    } else {
                        if (!coordinatorLayout.isInEditMode()) {
                            throw new IllegalStateException("View can not be anchored to the the parent CoordinatorLayout");
                        }
                        resolvedLayoutParams.mAnchorDirectChild = null;
                        resolvedLayoutParams.mAnchorView = null;
                    }
                } else {
                    View view3 = resolvedLayoutParams.mAnchorView;
                    for (ViewParent parent2 = view3.getParent(); parent2 != coordinatorLayout; parent2 = parent2.getParent()) {
                        if (parent2 == null || parent2 == childAt) {
                            resolvedLayoutParams.mAnchorDirectChild = null;
                            resolvedLayoutParams.mAnchorView = null;
                            viewFindViewById = coordinatorLayout.findViewById(resolvedLayoutParams.mAnchorId);
                            resolvedLayoutParams.mAnchorView = viewFindViewById;
                            if (viewFindViewById != null) {
                            }
                        } else {
                            if (parent2 instanceof View) {
                                view3 = parent2;
                            }
                        }
                    }
                    resolvedLayoutParams.mAnchorDirectChild = view3;
                }
            }
            SimpleArrayMap simpleArrayMap2 = coordinatorLayout.mChildDag.mGraph;
            if (!simpleArrayMap2.containsKey(childAt)) {
                simpleArrayMap2.put(childAt, null);
            }
            for (int i18 = 0; i18 < childCount; i18++) {
                if (i18 != i17) {
                    View childAt2 = coordinatorLayout.getChildAt(i18);
                    if (childAt2 != resolvedLayoutParams.mAnchorDirectChild) {
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        int layoutDirection = coordinatorLayout.getLayoutDirection();
                        int absoluteGravity = Gravity.getAbsoluteGravity(((LayoutParams) childAt2.getLayoutParams()).insetEdge, layoutDirection);
                        if ((absoluteGravity != 0 && (Gravity.getAbsoluteGravity(resolvedLayoutParams.dodgeInsetEdges, layoutDirection) & absoluteGravity) == absoluteGravity) || ((behavior2 = resolvedLayoutParams.mBehavior) != null && behavior2.layoutDependsOn(childAt, childAt2))) {
                            if (!coordinatorLayout.mChildDag.mGraph.containsKey(childAt2)) {
                                SimpleArrayMap simpleArrayMap3 = coordinatorLayout.mChildDag.mGraph;
                                if (!simpleArrayMap3.containsKey(childAt2)) {
                                    simpleArrayMap3.put(childAt2, null);
                                }
                            }
                            DirectedAcyclicGraph directedAcyclicGraph2 = coordinatorLayout.mChildDag;
                            SimpleArrayMap simpleArrayMap4 = directedAcyclicGraph2.mGraph;
                            if (!simpleArrayMap4.containsKey(childAt2) || !simpleArrayMap4.containsKey(childAt)) {
                                throw new IllegalArgumentException("All nodes must be present in the graph before being added as an edge");
                            }
                            ArrayList arrayList2 = (ArrayList) simpleArrayMap4.get(childAt2);
                            if (arrayList2 == null) {
                                ArrayList arrayList3 = (ArrayList) directedAcyclicGraph2.mListPool.acquire();
                                if (arrayList3 == null) {
                                    arrayList3 = new ArrayList();
                                }
                                arrayList2 = arrayList3;
                                simpleArrayMap4.put(childAt2, arrayList2);
                            }
                            arrayList2.add(childAt);
                        }
                    }
                }
            }
        }
        List list = coordinatorLayout.mDependencySortedChildren;
        DirectedAcyclicGraph directedAcyclicGraph3 = coordinatorLayout.mChildDag;
        directedAcyclicGraph3.mSortResult.clear();
        directedAcyclicGraph3.mSortTmpMarked.clear();
        SimpleArrayMap simpleArrayMap5 = directedAcyclicGraph3.mGraph;
        int i19 = simpleArrayMap5.size;
        for (int i20 = 0; i20 < i19; i20++) {
            directedAcyclicGraph3.dfs(simpleArrayMap5.keyAt(i20), directedAcyclicGraph3.mSortResult, directedAcyclicGraph3.mSortTmpMarked);
        }
        ((ArrayList) list).addAll(directedAcyclicGraph3.mSortResult);
        Collections.reverse(coordinatorLayout.mDependencySortedChildren);
        int childCount2 = coordinatorLayout.getChildCount();
        int i21 = 0;
        loop6: while (true) {
            if (i21 >= childCount2) {
                z = false;
                break;
            }
            View childAt3 = coordinatorLayout.getChildAt(i21);
            SimpleArrayMap simpleArrayMap6 = coordinatorLayout.mChildDag.mGraph;
            int i22 = simpleArrayMap6.size;
            for (int i23 = 0; i23 < i22; i23++) {
                ArrayList arrayList4 = (ArrayList) simpleArrayMap6.valueAt(i23);
                if (arrayList4 != null && arrayList4.contains(childAt3)) {
                    z = true;
                    break loop6;
                }
            }
            i21++;
        }
        if (z != coordinatorLayout.mNeedsPreDrawListener) {
            if (z) {
                if (coordinatorLayout.mIsAttachedToWindow) {
                    if (coordinatorLayout.mOnPreDrawListener == null) {
                        coordinatorLayout.mOnPreDrawListener = coordinatorLayout.new OnPreDrawListener();
                    }
                    coordinatorLayout.getViewTreeObserver().addOnPreDrawListener(coordinatorLayout.mOnPreDrawListener);
                }
                coordinatorLayout.mNeedsPreDrawListener = true;
            } else {
                if (coordinatorLayout.mIsAttachedToWindow && coordinatorLayout.mOnPreDrawListener != null) {
                    coordinatorLayout.getViewTreeObserver().removeOnPreDrawListener(coordinatorLayout.mOnPreDrawListener);
                }
                coordinatorLayout.mNeedsPreDrawListener = false;
            }
        }
        int paddingLeft = coordinatorLayout.getPaddingLeft();
        int paddingTop = coordinatorLayout.getPaddingTop();
        int paddingRight = coordinatorLayout.getPaddingRight();
        int paddingBottom = coordinatorLayout.getPaddingBottom();
        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
        int layoutDirection2 = coordinatorLayout.getLayoutDirection();
        boolean z4 = layoutDirection2 == 1;
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        int i24 = paddingLeft + paddingRight;
        int i25 = paddingTop + paddingBottom;
        int suggestedMinimumWidth = coordinatorLayout.getSuggestedMinimumWidth();
        int suggestedMinimumHeight = coordinatorLayout.getSuggestedMinimumHeight();
        boolean z5 = coordinatorLayout.mLastInsets != null && coordinatorLayout.getFitsSystemWindows();
        int size3 = ((ArrayList) coordinatorLayout.mDependencySortedChildren).size();
        int i26 = 0;
        int iCombineMeasuredStates = 0;
        while (i26 < size3) {
            boolean z6 = z3;
            View view4 = (View) ((ArrayList) coordinatorLayout.mDependencySortedChildren).get(i26);
            int i27 = suggestedMinimumWidth;
            if (view4.getVisibility() == 8) {
                i6 = i26;
                i13 = paddingLeft;
                i11 = paddingRight;
                suggestedMinimumWidth = i27;
                z2 = false;
                i9 = size3;
            } else {
                LayoutParams layoutParams = (LayoutParams) view4.getLayoutParams();
                int i28 = layoutParams.keyline;
                if (i28 < 0 || mode == 0) {
                    i3 = suggestedMinimumHeight;
                } else {
                    int keyline = coordinatorLayout.getKeyline(i28);
                    int i29 = layoutParams.gravity;
                    if (i29 == 0) {
                        i29 = 8388661;
                    }
                    int absoluteGravity2 = Gravity.getAbsoluteGravity(i29, layoutDirection2) & 7;
                    i3 = suggestedMinimumHeight;
                    if ((absoluteGravity2 == 3 && !z4) || (absoluteGravity2 == 5 && z4)) {
                        iMax = Math.max(0, (size - paddingRight) - keyline);
                    } else if ((absoluteGravity2 == 5 && !z4) || (absoluteGravity2 == 3 && z4)) {
                        iMax = Math.max(0, keyline - paddingLeft);
                    }
                    int i30 = i26;
                    i5 = iMax;
                    i4 = i30;
                    if (z5 || view4.getFitsSystemWindows()) {
                        i6 = i4;
                        iMakeMeasureSpec = i;
                        iMakeMeasureSpec2 = i2;
                    } else {
                        i6 = i4;
                        int systemWindowInsetRight = coordinatorLayout.mLastInsets.getSystemWindowInsetRight() + coordinatorLayout.mLastInsets.getSystemWindowInsetLeft();
                        int systemWindowInsetBottom = coordinatorLayout.mLastInsets.getSystemWindowInsetBottom() + coordinatorLayout.mLastInsets.getSystemWindowInsetTop();
                        iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size - systemWindowInsetRight, mode);
                        iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(size2 - systemWindowInsetBottom, mode2);
                    }
                    behavior = layoutParams.mBehavior;
                    if (behavior == null) {
                        int i31 = size3;
                        int i32 = iMakeMeasureSpec;
                        i8 = i27;
                        i9 = i31;
                        z2 = false;
                        i11 = paddingRight;
                        i12 = i3;
                        i13 = paddingLeft;
                        i14 = iCombineMeasuredStates;
                        int i33 = iMakeMeasureSpec2;
                        boolean zOnMeasureChild = behavior.onMeasureChild(this, view4, i32, i5, i33);
                        view = view4;
                        iMakeMeasureSpec = i32;
                        i10 = i5;
                        i7 = i33;
                        if (zOnMeasureChild) {
                            coordinatorLayout = this;
                        }
                        int iMax2 = Math.max(i8, view.getMeasuredWidth() + i24 + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin);
                        int iMax3 = Math.max(i12, view.getMeasuredHeight() + i25 + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
                        iCombineMeasuredStates = View.combineMeasuredStates(i14, view.getMeasuredState());
                        suggestedMinimumWidth = iMax2;
                        suggestedMinimumHeight = iMax3;
                    } else {
                        int i34 = i5;
                        i7 = iMakeMeasureSpec2;
                        view = view4;
                        i8 = i27;
                        i9 = size3;
                        i10 = i34;
                        i11 = paddingRight;
                        i12 = i3;
                        z2 = false;
                        i13 = paddingLeft;
                        i14 = iCombineMeasuredStates;
                    }
                    coordinatorLayout = this;
                    coordinatorLayout.measureChildWithMargins(view, iMakeMeasureSpec, i10, i7, 0);
                    int iMax22 = Math.max(i8, view.getMeasuredWidth() + i24 + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin);
                    int iMax32 = Math.max(i12, view.getMeasuredHeight() + i25 + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
                    iCombineMeasuredStates = View.combineMeasuredStates(i14, view.getMeasuredState());
                    suggestedMinimumWidth = iMax22;
                    suggestedMinimumHeight = iMax32;
                }
                i4 = i26;
                i5 = 0;
                if (z5) {
                    i6 = i4;
                    iMakeMeasureSpec = i;
                    iMakeMeasureSpec2 = i2;
                    behavior = layoutParams.mBehavior;
                    if (behavior == null) {
                    }
                    coordinatorLayout = this;
                    coordinatorLayout.measureChildWithMargins(view, iMakeMeasureSpec, i10, i7, 0);
                    int iMax222 = Math.max(i8, view.getMeasuredWidth() + i24 + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin);
                    int iMax322 = Math.max(i12, view.getMeasuredHeight() + i25 + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
                    iCombineMeasuredStates = View.combineMeasuredStates(i14, view.getMeasuredState());
                    suggestedMinimumWidth = iMax222;
                    suggestedMinimumHeight = iMax322;
                }
            }
            i26 = i6 + 1;
            z3 = z6;
            size3 = i9;
            paddingLeft = i13;
            paddingRight = i11;
        }
        int i35 = iCombineMeasuredStates;
        coordinatorLayout.setMeasuredDimension(View.resolveSizeAndState(suggestedMinimumWidth, i, (-16777216) & i35), View.resolveSizeAndState(suggestedMinimumHeight, i2, i35 << 16));
    }

    public final void onMeasureChild(View view, int i, int i2, int i3) {
        measureChildWithMargins(view, i, i2, i3, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedFling(View view, float f, float f2, boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isNestedScrollAccepted(0)) {
                    Behavior behavior = layoutParams.mBehavior;
                }
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedPreFling(View view, float f, float f2) {
        Behavior behavior;
        int childCount = getChildCount();
        boolean zOnNestedPreFling = false;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isNestedScrollAccepted(0) && (behavior = layoutParams.mBehavior) != null) {
                    zOnNestedPreFling |= behavior.onNestedPreFling(childAt, view, f2);
                }
            }
        }
        return zOnNestedPreFling;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        onNestedPreScroll(view, i, i2, iArr, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        onNestedScroll(view, i, i2, i3, i4, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScrollAccepted(View view, View view2, int i) {
        onNestedScrollAccepted(view, view2, i, 0);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable parcelable2;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.mSuperState);
        SparseArray sparseArray = savedState.behaviorStates;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int id = childAt.getId();
            Behavior behavior = getResolvedLayoutParams(childAt).mBehavior;
            if (id != -1 && behavior != null && (parcelable2 = (Parcelable) sparseArray.get(id)) != null) {
                behavior.onRestoreInstanceState(childAt, parcelable2);
            }
        }
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        Parcelable parcelableOnSaveInstanceState;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SparseArray sparseArray = new SparseArray();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int id = childAt.getId();
            Behavior behavior = ((LayoutParams) childAt.getLayoutParams()).mBehavior;
            if (id != -1 && behavior != null && (parcelableOnSaveInstanceState = behavior.onSaveInstanceState(childAt)) != null) {
                sparseArray.append(id, parcelableOnSaveInstanceState);
            }
        }
        savedState.behaviorStates = sparseArray;
        return savedState;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onStartNestedScroll(View view, View view2, int i) {
        return onStartNestedScroll(view, view2, i, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onStopNestedScroll(View view) {
        onStopNestedScroll(view, 0);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zPerformIntercept;
        int actionMasked = motionEvent.getActionMasked();
        View view = this.mBehaviorTouchView;
        boolean z = false;
        if (view != null) {
            Behavior behavior = ((LayoutParams) view.getLayoutParams()).mBehavior;
            zPerformIntercept = behavior != null ? behavior.onTouchEvent(this, this.mBehaviorTouchView, motionEvent) : false;
        } else {
            zPerformIntercept = performIntercept(motionEvent, 1);
            if (actionMasked != 0 && zPerformIntercept) {
                z = true;
            }
        }
        if (this.mBehaviorTouchView == null || actionMasked == 3) {
            zPerformIntercept |= super.onTouchEvent(motionEvent);
        } else if (z) {
            MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
            motionEventObtain.setAction(3);
            super.onTouchEvent(motionEventObtain);
            motionEventObtain.recycle();
        }
        if (actionMasked != 1 && actionMasked != 3) {
            return zPerformIntercept;
        }
        this.mBehaviorTouchView = null;
        resetTouchBehaviors();
        return zPerformIntercept;
    }

    public final boolean performEvent(Behavior behavior, View view, MotionEvent motionEvent, int i) {
        if (i == 0) {
            return behavior.onInterceptTouchEvent(this, view, motionEvent);
        }
        if (i == 1) {
            return behavior.onTouchEvent(this, view, motionEvent);
        }
        throw new IllegalArgumentException();
    }

    public final boolean performIntercept(MotionEvent motionEvent, int i) {
        int actionMasked = motionEvent.getActionMasked();
        ArrayList arrayList = (ArrayList) this.mTempList1;
        arrayList.clear();
        boolean zIsChildrenDrawingOrderEnabled = isChildrenDrawingOrderEnabled();
        int childCount = getChildCount();
        for (int i2 = childCount - 1; i2 >= 0; i2--) {
            arrayList.add(getChildAt(zIsChildrenDrawingOrderEnabled ? getChildDrawingOrder(childCount, i2) : i2));
        }
        ViewElevationComparator viewElevationComparator = TOP_SORTED_CHILDREN_COMPARATOR;
        if (viewElevationComparator != null) {
            Collections.sort(arrayList, viewElevationComparator);
        }
        int size = arrayList.size();
        MotionEvent motionEventObtain = null;
        boolean zPerformEvent = false;
        for (int i3 = 0; i3 < size; i3++) {
            View view = (View) arrayList.get(i3);
            Behavior behavior = ((LayoutParams) view.getLayoutParams()).mBehavior;
            if (zPerformEvent && actionMasked != 0) {
                if (behavior != null) {
                    if (motionEventObtain == null) {
                        motionEventObtain = MotionEvent.obtain(motionEvent);
                        motionEventObtain.setAction(3);
                    }
                    performEvent(behavior, view, motionEventObtain, i);
                }
            } else if (!zPerformEvent && behavior != null && (zPerformEvent = performEvent(behavior, view, motionEvent, i))) {
                this.mBehaviorTouchView = view;
                if (actionMasked != 3 && actionMasked != 1) {
                    for (int i4 = 0; i4 < i3; i4++) {
                        View view2 = (View) arrayList.get(i4);
                        Behavior behavior2 = ((LayoutParams) view2.getLayoutParams()).mBehavior;
                        if (behavior2 != null) {
                            if (motionEventObtain == null) {
                                motionEventObtain = MotionEvent.obtain(motionEvent);
                                motionEventObtain.setAction(3);
                            }
                            performEvent(behavior2, view2, motionEventObtain, i);
                        }
                    }
                }
            }
        }
        arrayList.clear();
        if (motionEventObtain != null) {
            motionEventObtain.recycle();
        }
        return zPerformEvent;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        Behavior behavior = ((LayoutParams) view.getLayoutParams()).mBehavior;
        if (behavior == null || !behavior.onRequestChildRectangleOnScreen(this, view, rect, z)) {
            return super.requestChildRectangleOnScreen(view, rect, z);
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
        if (!z || this.mDisallowInterceptReset) {
            return;
        }
        if (this.mBehaviorTouchView == null) {
            int childCount = getChildCount();
            MotionEvent motionEventObtain = null;
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                Behavior behavior = ((LayoutParams) childAt.getLayoutParams()).mBehavior;
                if (behavior != null) {
                    if (motionEventObtain == null) {
                        long jUptimeMillis = SystemClock.uptimeMillis();
                        motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                    }
                    behavior.onInterceptTouchEvent(this, childAt, motionEventObtain);
                }
            }
            if (motionEventObtain != null) {
                motionEventObtain.recycle();
            }
        }
        resetTouchBehaviors();
        this.mDisallowInterceptReset = true;
    }

    public final void resetTouchBehaviors() {
        View view = this.mBehaviorTouchView;
        if (view != null) {
            Behavior behavior = ((LayoutParams) view.getLayoutParams()).mBehavior;
            if (behavior != null) {
                long jUptimeMillis = SystemClock.uptimeMillis();
                MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                behavior.onTouchEvent(this, this.mBehaviorTouchView, motionEventObtain);
                motionEventObtain.recycle();
            }
            this.mBehaviorTouchView = null;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ((LayoutParams) getChildAt(i).getLayoutParams()).getClass();
        }
        this.mDisallowInterceptReset = false;
    }

    @Override // android.view.View
    public final void setFitsSystemWindows(boolean z) {
        super.setFitsSystemWindows(z);
        setupForInsets();
    }

    @Override // android.view.ViewGroup
    public final void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mOnHierarchyChangeListener = onHierarchyChangeListener;
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        Drawable drawable = this.mStatusBarBackground;
        if (drawable == null || drawable.isVisible() == z) {
            return;
        }
        this.mStatusBarBackground.setVisible(z, false);
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [androidx.coordinatorlayout.widget.CoordinatorLayout$1] */
    public final void setupForInsets() {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (!getFitsSystemWindows()) {
            ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(this, null);
            return;
        }
        if (this.mApplyWindowInsetsListener == null) {
            this.mApplyWindowInsetsListener = new OnApplyWindowInsetsListener() { // from class: androidx.coordinatorlayout.widget.CoordinatorLayout.1
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public final WindowInsetsCompat onApplyWindowInsets(WindowInsetsCompat windowInsetsCompat, View view) {
                    CoordinatorLayout coordinatorLayout = CoordinatorLayout.this;
                    if (!Objects.equals(coordinatorLayout.mLastInsets, windowInsetsCompat)) {
                        coordinatorLayout.mLastInsets = windowInsetsCompat;
                        boolean z = windowInsetsCompat.getSystemWindowInsetTop() > 0;
                        coordinatorLayout.mDrawStatusBarBackground = z;
                        coordinatorLayout.setWillNotDraw(!z && coordinatorLayout.getBackground() == null);
                        WindowInsetsCompat.Impl impl = windowInsetsCompat.mImpl;
                        if (!impl.isConsumed()) {
                            int childCount = coordinatorLayout.getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                View childAt = coordinatorLayout.getChildAt(i);
                                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                                if (childAt.getFitsSystemWindows() && ((LayoutParams) childAt.getLayoutParams()).mBehavior != null && impl.isConsumed()) {
                                    break;
                                }
                            }
                        }
                        coordinatorLayout.requestLayout();
                    }
                    return windowInsetsCompat;
                }
            };
        }
        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(this, this.mApplyWindowInsetsListener);
        setSystemUiVisibility(PeripheralConstants.ErrorCode.ERROR_PLUGIN_CUSTOM_BASE);
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mStatusBarBackground;
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.coordinatorLayoutStyle);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedPreScroll(View view, int i, int i2, int[] iArr, int i3) {
        Behavior behavior;
        int childCount = getChildCount();
        boolean z = false;
        int iMax = 0;
        int iMax2 = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isNestedScrollAccepted(i3) && (behavior = layoutParams.mBehavior) != null) {
                    int[] iArr2 = this.mBehaviorConsumed;
                    iArr2[0] = 0;
                    iArr2[1] = 0;
                    behavior.onNestedPreScroll(this, childAt, view, i, i2, iArr2, i3);
                    iMax = i > 0 ? Math.max(iMax, this.mBehaviorConsumed[0]) : Math.min(iMax, this.mBehaviorConsumed[0]);
                    iMax2 = i2 > 0 ? Math.max(iMax2, this.mBehaviorConsumed[1]) : Math.min(iMax2, this.mBehaviorConsumed[1]);
                    z = true;
                }
            }
        }
        iArr[0] = iMax;
        iArr[1] = iMax2;
        if (z) {
            onChildViewsChanged(1);
        }
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5) {
        onNestedScroll(view, i, i2, i3, i4, 0, this.mNestedScrollingV2ConsumedCompat);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedScrollAccepted(View view, View view2, int i, int i2) {
        NestedScrollingParentHelper nestedScrollingParentHelper = this.mNestedScrollingParentHelper;
        if (i2 == 1) {
            nestedScrollingParentHelper.mNestedScrollAxesNonTouch = i;
        } else {
            nestedScrollingParentHelper.mNestedScrollAxesTouch = i;
        }
        this.mNestedScrollingTarget = view2;
        this.mLastNestedScrollingChild = view2;
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            ((LayoutParams) getChildAt(i3).getLayoutParams()).getClass();
        }
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        int childCount = getChildCount();
        boolean z = false;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                Behavior behavior = layoutParams.mBehavior;
                if (behavior != null) {
                    boolean zOnStartNestedScroll = behavior.onStartNestedScroll(this, childAt, view, view2, i, i2);
                    z |= zOnStartNestedScroll;
                    if (i2 == 0) {
                        layoutParams.mDidAcceptNestedScrollTouch = zOnStartNestedScroll;
                    } else if (i2 == 1) {
                        layoutParams.mDidAcceptNestedScrollNonTouch = zOnStartNestedScroll;
                    }
                } else if (i2 == 0) {
                    layoutParams.mDidAcceptNestedScrollTouch = false;
                } else if (i2 == 1) {
                    layoutParams.mDidAcceptNestedScrollNonTouch = false;
                }
            }
        }
        return z;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onStopNestedScroll(View view, int i) {
        NestedScrollingParentHelper nestedScrollingParentHelper = this.mNestedScrollingParentHelper;
        if (i == 1) {
            nestedScrollingParentHelper.mNestedScrollAxesNonTouch = 0;
        } else {
            nestedScrollingParentHelper.mNestedScrollAxesTouch = 0;
        }
        this.mLastNestedScrollingChild = view;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (layoutParams.isNestedScrollAccepted(i)) {
                Behavior behavior = layoutParams.mBehavior;
                if (behavior != null) {
                    behavior.onStopNestedScroll(this, childAt, view, i);
                }
                if (i == 0) {
                    layoutParams.mDidAcceptNestedScrollTouch = false;
                } else if (i == 1) {
                    layoutParams.mDidAcceptNestedScrollNonTouch = false;
                }
                layoutParams.mDidChangeAfterNestedScroll = false;
            }
        }
        this.mNestedScrollingTarget = null;
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes;
        CoordinatorLayout coordinatorLayout;
        Context context2;
        super(context, attributeSet, i);
        this.mDependencySortedChildren = new ArrayList();
        this.mChildDag = new DirectedAcyclicGraph();
        this.mTempList1 = new ArrayList();
        this.mBehaviorConsumed = new int[2];
        this.mNestedScrollingV2ConsumedCompat = new int[2];
        this.mEnableAutoCollapsingKeyEvent = true;
        this.mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        int[] iArr = R$styleable.CoordinatorLayout;
        if (i == 0) {
            typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, 0, 2132019698);
        } else {
            typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, 0);
        }
        TypedArray typedArray = typedArrayObtainStyledAttributes;
        if (i == 0) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            coordinatorLayout = this;
            context2 = context;
            ViewCompat.Api29Impl.saveAttributeDataForStyleable(coordinatorLayout, context2, iArr, attributeSet, typedArray, 0, 2132019698);
        } else {
            coordinatorLayout = this;
            context2 = context;
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api29Impl.saveAttributeDataForStyleable(coordinatorLayout, context2, iArr, attributeSet, typedArray, i, 0);
        }
        int resourceId = typedArray.getResourceId(0, 0);
        if (resourceId != 0) {
            Resources resources = context2.getResources();
            int[] intArray = resources.getIntArray(resourceId);
            coordinatorLayout.mKeylines = intArray;
            float f = resources.getDisplayMetrics().density;
            int length = intArray.length;
            for (int i2 = 0; i2 < length; i2++) {
                coordinatorLayout.mKeylines[i2] = (int) (r11[i2] * f);
            }
        }
        coordinatorLayout.mStatusBarBackground = typedArray.getDrawable(1);
        typedArray.recycle();
        coordinatorLayout.setupForInsets();
        super.setOnHierarchyChangeListener(coordinatorLayout.new HierarchyChangeListener());
        WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
        if (coordinatorLayout.getImportantForAccessibility() == 0) {
            coordinatorLayout.setImportantForAccessibility(1);
        }
    }

    @Override // androidx.core.view.NestedScrollingParent3
    public final void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        Behavior behavior;
        int iMin;
        int iMin2;
        int childCount = getChildCount();
        boolean z = false;
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isNestedScrollAccepted(i5) && (behavior = layoutParams.mBehavior) != null) {
                    int[] iArr2 = this.mBehaviorConsumed;
                    iArr2[0] = 0;
                    iArr2[1] = 0;
                    behavior.onNestedScroll(this, childAt, view, i, i2, i3, i4, i5, iArr2);
                    if (i3 > 0) {
                        iMin = Math.max(i6, this.mBehaviorConsumed[0]);
                    } else {
                        iMin = Math.min(i6, this.mBehaviorConsumed[0]);
                    }
                    i6 = iMin;
                    if (i4 > 0) {
                        iMin2 = Math.max(i7, this.mBehaviorConsumed[1]);
                    } else {
                        iMin2 = Math.min(i7, this.mBehaviorConsumed[1]);
                    }
                    i7 = iMin2;
                    z = true;
                }
            }
        }
        iArr[0] = iArr[0] + i6;
        iArr[1] = iArr[1] + i7;
        if (z) {
            onChildViewsChanged(1);
        }
    }

    public class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: androidx.coordinatorlayout.widget.CoordinatorLayout.SavedState.1
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
        public SparseArray behaviorStates;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            int i = parcel.readInt();
            int[] iArr = new int[i];
            parcel.readIntArray(iArr);
            Parcelable[] parcelableArray = parcel.readParcelableArray(classLoader);
            this.behaviorStates = new SparseArray(i);
            for (int i2 = 0; i2 < i; i2++) {
                this.behaviorStates.append(iArr[i2], parcelableArray[i2]);
            }
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            SparseArray sparseArray = this.behaviorStates;
            int size = sparseArray != null ? sparseArray.size() : 0;
            parcel.writeInt(size);
            int[] iArr = new int[size];
            Parcelable[] parcelableArr = new Parcelable[size];
            for (int i2 = 0; i2 < size; i2++) {
                iArr[i2] = this.behaviorStates.keyAt(i2);
                parcelableArr[i2] = (Parcelable) this.behaviorStates.valueAt(i2);
            }
            parcel.writeIntArray(iArr);
            parcel.writeParcelableArray(parcelableArr, i);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int anchorGravity;
        public int dodgeInsetEdges;
        public final int gravity;
        public int insetEdge;
        public final int keyline;
        public View mAnchorDirectChild;
        public final int mAnchorId;
        public View mAnchorView;
        public Behavior mBehavior;
        public boolean mBehaviorResolved;
        public boolean mDidAcceptNestedScrollNonTouch;
        public boolean mDidAcceptNestedScrollTouch;
        public boolean mDidChangeAfterNestedScroll;
        public int mInsetOffsetX;
        public int mInsetOffsetY;
        public final Rect mLastChildRect;

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.mBehaviorResolved = false;
            this.gravity = 0;
            this.anchorGravity = 0;
            this.keyline = -1;
            this.mAnchorId = -1;
            this.insetEdge = 0;
            this.dodgeInsetEdges = 0;
            this.mLastChildRect = new Rect();
        }

        public final boolean isNestedScrollAccepted(int i) {
            if (i == 0) {
                return this.mDidAcceptNestedScrollTouch;
            }
            if (i != 1) {
                return false;
            }
            return this.mDidAcceptNestedScrollNonTouch;
        }

        public final void setBehavior(Behavior behavior) {
            Behavior behavior2 = this.mBehavior;
            if (behavior2 != behavior) {
                if (behavior2 != null) {
                    behavior2.onDetachedFromLayoutParams();
                }
                this.mBehavior = behavior;
                this.mBehaviorResolved = true;
                if (behavior != null) {
                    behavior.onAttachedToLayoutParams(this);
                }
            }
        }

        public LayoutParams(Context context, AttributeSet attributeSet) throws NoSuchMethodException, SecurityException {
            Behavior behavior;
            super(context, attributeSet);
            this.mBehaviorResolved = false;
            this.gravity = 0;
            this.anchorGravity = 0;
            this.keyline = -1;
            this.mAnchorId = -1;
            this.insetEdge = 0;
            this.dodgeInsetEdges = 0;
            this.mLastChildRect = new Rect();
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CoordinatorLayout_Layout);
            this.gravity = typedArrayObtainStyledAttributes.getInteger(0, 0);
            this.mAnchorId = typedArrayObtainStyledAttributes.getResourceId(1, -1);
            this.anchorGravity = typedArrayObtainStyledAttributes.getInteger(2, 0);
            this.keyline = typedArrayObtainStyledAttributes.getInteger(6, -1);
            this.insetEdge = typedArrayObtainStyledAttributes.getInt(5, 0);
            this.dodgeInsetEdges = typedArrayObtainStyledAttributes.getInt(4, 0);
            boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(3);
            this.mBehaviorResolved = zHasValue;
            if (zHasValue) {
                String string = typedArrayObtainStyledAttributes.getString(3);
                String str = CoordinatorLayout.WIDGET_PACKAGE_NAME;
                if (TextUtils.isEmpty(string)) {
                    behavior = null;
                } else {
                    if (string.startsWith(".")) {
                        string = context.getPackageName() + string;
                    } else if (string.indexOf(46) < 0) {
                        String str2 = CoordinatorLayout.WIDGET_PACKAGE_NAME;
                        if (!TextUtils.isEmpty(str2)) {
                            string = str2 + '.' + string;
                        }
                    }
                    try {
                        ThreadLocal threadLocal = CoordinatorLayout.sConstructors;
                        Map map = (Map) threadLocal.get();
                        if (map == null) {
                            map = new HashMap();
                            threadLocal.set(map);
                        }
                        Constructor<?> constructor = (Constructor) map.get(string);
                        if (constructor == null) {
                            constructor = Class.forName(string, false, context.getClassLoader()).getConstructor(CoordinatorLayout.CONSTRUCTOR_PARAMS);
                            constructor.setAccessible(true);
                            map.put(string, constructor);
                        }
                        behavior = (Behavior) constructor.newInstance(context, attributeSet);
                    } catch (Exception e) {
                        throw new RuntimeException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Could not inflate Behavior subclass ", string), e);
                    }
                }
                this.mBehavior = behavior;
            }
            typedArrayObtainStyledAttributes.recycle();
            Behavior behavior2 = this.mBehavior;
            if (behavior2 != null) {
                behavior2.onAttachedToLayoutParams(this);
            }
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.mBehaviorResolved = false;
            this.gravity = 0;
            this.anchorGravity = 0;
            this.keyline = -1;
            this.mAnchorId = -1;
            this.insetEdge = 0;
            this.dodgeInsetEdges = 0;
            this.mLastChildRect = new Rect();
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.mBehaviorResolved = false;
            this.gravity = 0;
            this.anchorGravity = 0;
            this.keyline = -1;
            this.mAnchorId = -1;
            this.insetEdge = 0;
            this.dodgeInsetEdges = 0;
            this.mLastChildRect = new Rect();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mBehaviorResolved = false;
            this.gravity = 0;
            this.anchorGravity = 0;
            this.keyline = -1;
            this.mAnchorId = -1;
            this.insetEdge = 0;
            this.dodgeInsetEdges = 0;
            this.mLastChildRect = new Rect();
        }
    }
}
