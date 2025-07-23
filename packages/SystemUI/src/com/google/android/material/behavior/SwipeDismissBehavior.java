package com.google.android.material.behavior;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.widget.ViewDragHelper;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.SnackbarManager;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SwipeDismissBehavior<V extends View> extends CoordinatorLayout.Behavior {
    public boolean interceptingEvents;
    public BaseTransientBottomBar.AnonymousClass7 listener;
    public boolean requestingDisallowInterceptTouchEvent;
    public ViewDragHelper viewDragHelper;
    public int swipeDirection = 2;
    public final float dragDismissThreshold = 0.5f;
    public float alphaStartSwipeDistance = 0.0f;
    public float alphaEndSwipeDistance = 0.5f;
    public final AnonymousClass1 dragCallback = new ViewDragHelper.Callback() { // from class: com.google.android.material.behavior.SwipeDismissBehavior.1
        public int activePointerId = -1;
        public int originalCapturedViewLeft;

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final int clampViewPositionHorizontal(View view, int i) {
            int width;
            int width2;
            int width3;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            boolean z = view.getLayoutDirection() == 1;
            int i2 = SwipeDismissBehavior.this.swipeDirection;
            if (i2 == 0) {
                if (z) {
                    width = this.originalCapturedViewLeft - view.getWidth();
                    width2 = this.originalCapturedViewLeft;
                } else {
                    width = this.originalCapturedViewLeft;
                    width3 = view.getWidth();
                    width2 = width3 + width;
                }
            } else if (i2 != 1) {
                width = this.originalCapturedViewLeft - view.getWidth();
                width2 = this.originalCapturedViewLeft + view.getWidth();
            } else if (z) {
                width = this.originalCapturedViewLeft;
                width3 = view.getWidth();
                width2 = width3 + width;
            } else {
                width = this.originalCapturedViewLeft - view.getWidth();
                width2 = this.originalCapturedViewLeft;
            }
            return Math.min(Math.max(width, i), width2);
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final int clampViewPositionVertical(View view, int i) {
            return view.getTop();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final int getViewHorizontalDragRange(View view) {
            return view.getWidth();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final void onViewCaptured(View view, int i) {
            this.activePointerId = i;
            this.originalCapturedViewLeft = view.getLeft();
            ViewParent parent = view.getParent();
            if (parent != null) {
                SwipeDismissBehavior swipeDismissBehavior = SwipeDismissBehavior.this;
                swipeDismissBehavior.requestingDisallowInterceptTouchEvent = true;
                parent.requestDisallowInterceptTouchEvent(true);
                swipeDismissBehavior.requestingDisallowInterceptTouchEvent = false;
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final void onViewDragStateChanged(int i) {
            BaseTransientBottomBar.AnonymousClass7 anonymousClass7 = SwipeDismissBehavior.this.listener;
            if (anonymousClass7 != null) {
                BaseTransientBottomBar baseTransientBottomBar = BaseTransientBottomBar.this;
                if (i == 0) {
                    SnackbarManager.getInstance().restoreTimeoutIfPaused(baseTransientBottomBar.managerCallback);
                } else if (i == 1 || i == 2) {
                    SnackbarManager.getInstance().pauseTimeout(baseTransientBottomBar.managerCallback);
                }
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final void onViewPositionChanged(View view, int i, int i2) {
            float width = view.getWidth();
            SwipeDismissBehavior swipeDismissBehavior = SwipeDismissBehavior.this;
            float f = width * swipeDismissBehavior.alphaStartSwipeDistance;
            float width2 = view.getWidth() * swipeDismissBehavior.alphaEndSwipeDistance;
            float abs = Math.abs(i - this.originalCapturedViewLeft);
            if (abs <= f) {
                view.setAlpha(1.0f);
            } else if (abs >= width2) {
                view.setAlpha(0.0f);
            } else {
                view.setAlpha(Math.min(Math.max(0.0f, 1.0f - ((abs - f) / (width2 - f))), 1.0f));
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:39:0x0050, code lost:
        
            if (java.lang.Math.abs(r9.getLeft() - r8.originalCapturedViewLeft) >= java.lang.Math.round(r9.getWidth() * r3.dragDismissThreshold)) goto L27;
         */
        @Override // androidx.customview.widget.ViewDragHelper.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onViewReleased(android.view.View r9, float r10, float r11) {
            /*
                r8 = this;
                r11 = -1
                r8.activePointerId = r11
                int r11 = r9.getWidth()
                r0 = 0
                int r1 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
                r2 = 1
                com.google.android.material.behavior.SwipeDismissBehavior r3 = com.google.android.material.behavior.SwipeDismissBehavior.this
                r4 = 0
                if (r1 == 0) goto L39
                java.util.WeakHashMap r5 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
                int r5 = r9.getLayoutDirection()
                if (r5 != r2) goto L1a
                r5 = r2
                goto L1b
            L1a:
                r5 = r4
            L1b:
                int r6 = r3.swipeDirection
                r7 = 2
                if (r6 != r7) goto L21
                goto L52
            L21:
                if (r6 != 0) goto L2d
                if (r5 == 0) goto L2a
                int r1 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
                if (r1 >= 0) goto L66
                goto L52
            L2a:
                if (r1 <= 0) goto L66
                goto L52
            L2d:
                if (r6 != r2) goto L66
                if (r5 == 0) goto L34
                if (r1 <= 0) goto L66
                goto L52
            L34:
                int r1 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
                if (r1 >= 0) goto L66
                goto L52
            L39:
                int r1 = r9.getLeft()
                int r5 = r8.originalCapturedViewLeft
                int r1 = r1 - r5
                int r5 = r9.getWidth()
                float r5 = (float) r5
                float r6 = r3.dragDismissThreshold
                float r5 = r5 * r6
                int r5 = java.lang.Math.round(r5)
                int r1 = java.lang.Math.abs(r1)
                if (r1 < r5) goto L66
            L52:
                int r10 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
                if (r10 < 0) goto L61
                int r10 = r9.getLeft()
                int r0 = r8.originalCapturedViewLeft
                if (r10 >= r0) goto L5f
                goto L61
            L5f:
                int r0 = r0 + r11
                goto L69
            L61:
                int r8 = r8.originalCapturedViewLeft
                int r0 = r8 - r11
                goto L69
            L66:
                int r0 = r8.originalCapturedViewLeft
                r2 = r4
            L69:
                androidx.customview.widget.ViewDragHelper r8 = r3.viewDragHelper
                int r10 = r9.getTop()
                boolean r8 = r8.settleCapturedViewAt(r0, r10)
                if (r8 == 0) goto L80
                com.google.android.material.behavior.SwipeDismissBehavior$SettleRunnable r8 = new com.google.android.material.behavior.SwipeDismissBehavior$SettleRunnable
                r8.<init>(r9, r2)
                java.util.WeakHashMap r10 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
                r9.postOnAnimation(r8)
                return
            L80:
                if (r2 == 0) goto L89
                com.google.android.material.snackbar.BaseTransientBottomBar$7 r8 = r3.listener
                if (r8 == 0) goto L89
                r8.onDismiss(r9)
            L89:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.behavior.SwipeDismissBehavior.AnonymousClass1.onViewReleased(android.view.View, float, float):void");
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final boolean tryCaptureView(View view, int i) {
            int i2 = this.activePointerId;
            return (i2 == -1 || i2 == i) && SwipeDismissBehavior.this.canSwipeDismissView(view);
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnDismissListener {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SettleRunnable implements Runnable {
        public final boolean dismiss;
        public final View view;

        public SettleRunnable(View view, boolean z) {
            this.view = view;
            this.dismiss = z;
        }

        @Override // java.lang.Runnable
        public final void run() {
            BaseTransientBottomBar.AnonymousClass7 anonymousClass7;
            ViewDragHelper viewDragHelper = SwipeDismissBehavior.this.viewDragHelper;
            if (viewDragHelper != null && viewDragHelper.continueSettling()) {
                View view = this.view;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                view.postOnAnimation(this);
            } else {
                if (!this.dismiss || (anonymousClass7 = SwipeDismissBehavior.this.listener) == null) {
                    return;
                }
                anonymousClass7.onDismiss(this.view);
            }
        }
    }

    public boolean canSwipeDismissView(View view) {
        return true;
    }

    public OnDismissListener getListener() {
        return this.listener;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        boolean z = this.interceptingEvents;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            z = coordinatorLayout.isPointInChildBounds(view, (int) motionEvent.getX(), (int) motionEvent.getY());
            this.interceptingEvents = z;
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.interceptingEvents = false;
        }
        if (z) {
            if (this.viewDragHelper == null) {
                this.viewDragHelper = ViewDragHelper.create(coordinatorLayout, this.dragCallback);
            }
            if (!this.requestingDisallowInterceptTouchEvent && this.viewDragHelper.shouldInterceptTouchEvent(motionEvent)) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (view.getImportantForAccessibility() == 0) {
            view.setImportantForAccessibility(1);
            ViewCompat.removeActionWithId(view, 1048576);
            ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
            if (canSwipeDismissView(view)) {
                ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, null, new AccessibilityViewCommand() { // from class: com.google.android.material.behavior.SwipeDismissBehavior.2
                    @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                    public final boolean perform(View view2) {
                        SwipeDismissBehavior swipeDismissBehavior = SwipeDismissBehavior.this;
                        if (!swipeDismissBehavior.canSwipeDismissView(view2)) {
                            return false;
                        }
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        boolean z = view2.getLayoutDirection() == 1;
                        int i2 = swipeDismissBehavior.swipeDirection;
                        view2.offsetLeftAndRight((!(i2 == 0 && z) && (i2 != 1 || z)) ? view2.getWidth() : -view2.getWidth());
                        view2.setAlpha(0.0f);
                        BaseTransientBottomBar.AnonymousClass7 anonymousClass7 = swipeDismissBehavior.listener;
                        if (anonymousClass7 != null) {
                            anonymousClass7.onDismiss(view2);
                        }
                        return true;
                    }
                });
            }
        }
        return false;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        if (this.viewDragHelper == null) {
            return false;
        }
        if (this.requestingDisallowInterceptTouchEvent && motionEvent.getActionMasked() == 3) {
            return true;
        }
        this.viewDragHelper.processTouchEvent(motionEvent);
        return true;
    }
}
