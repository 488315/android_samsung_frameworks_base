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
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SwipeDismissBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public boolean interceptingEvents;
    public OnDismissListener listener;
    public ViewDragHelper viewDragHelper;
    public int swipeDirection = 2;
    public final float dragDismissThreshold = 0.5f;
    public float alphaStartSwipeDistance = 0.0f;
    public float alphaEndSwipeDistance = 0.5f;
    public final C42191 dragCallback = new ViewDragHelper.Callback() { // from class: com.google.android.material.behavior.SwipeDismissBehavior.1
        public int activePointerId = -1;
        public int originalCapturedViewLeft;

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final int clampViewPositionHorizontal(View view, int i) {
            int width;
            int width2;
            int i2;
            int width3;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            boolean z = ViewCompat.Api17Impl.getLayoutDirection(view) == 1;
            int i3 = SwipeDismissBehavior.this.swipeDirection;
            if (i3 == 0) {
                if (z) {
                    width = this.originalCapturedViewLeft - view.getWidth();
                    width2 = this.originalCapturedViewLeft;
                } else {
                    i2 = this.originalCapturedViewLeft;
                    width3 = view.getWidth();
                    width = i2;
                    width2 = width3 + width;
                }
            } else if (i3 != 1) {
                width = this.originalCapturedViewLeft - view.getWidth();
                width2 = this.originalCapturedViewLeft + view.getWidth();
            } else if (z) {
                i2 = this.originalCapturedViewLeft;
                width3 = view.getWidth();
                width = i2;
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
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final void onViewDragStateChanged(int i) {
            OnDismissListener onDismissListener = SwipeDismissBehavior.this.listener;
            if (onDismissListener != null) {
                BaseTransientBottomBar baseTransientBottomBar = BaseTransientBottomBar.this;
                if (i == 0) {
                    SnackbarManager snackbarManager = SnackbarManager.getInstance();
                    BaseTransientBottomBar.C43685 c43685 = baseTransientBottomBar.managerCallback;
                    synchronized (snackbarManager.lock) {
                        if (snackbarManager.isCurrentSnackbarLocked(c43685)) {
                            SnackbarManager.SnackbarRecord snackbarRecord = snackbarManager.currentSnackbar;
                            if (snackbarRecord.paused) {
                                snackbarRecord.paused = false;
                                snackbarManager.scheduleTimeoutLocked(snackbarRecord);
                            }
                        }
                    }
                    return;
                }
                if (i == 1 || i == 2) {
                    SnackbarManager snackbarManager2 = SnackbarManager.getInstance();
                    BaseTransientBottomBar.C43685 c436852 = baseTransientBottomBar.managerCallback;
                    synchronized (snackbarManager2.lock) {
                        if (snackbarManager2.isCurrentSnackbarLocked(c436852)) {
                            SnackbarManager.SnackbarRecord snackbarRecord2 = snackbarManager2.currentSnackbar;
                            if (!snackbarRecord2.paused) {
                                snackbarRecord2.paused = true;
                                snackbarManager2.handler.removeCallbacksAndMessages(snackbarRecord2);
                            }
                        }
                    }
                }
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final void onViewPositionChanged(View view, int i, int i2, int i3) {
            float f = this.originalCapturedViewLeft;
            float width = view.getWidth();
            SwipeDismissBehavior swipeDismissBehavior = SwipeDismissBehavior.this;
            float f2 = (width * swipeDismissBehavior.alphaStartSwipeDistance) + f;
            float width2 = (view.getWidth() * swipeDismissBehavior.alphaEndSwipeDistance) + this.originalCapturedViewLeft;
            float f3 = i;
            if (f3 <= f2) {
                view.setAlpha(1.0f);
            } else if (f3 >= width2) {
                view.setAlpha(0.0f);
            } else {
                view.setAlpha(Math.min(Math.max(0.0f, 1.0f - ((f3 - f2) / (width2 - f2))), 1.0f));
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:42:0x0050, code lost:
        
            if (java.lang.Math.abs(r9.getLeft() - r8.originalCapturedViewLeft) >= java.lang.Math.round(r9.getWidth() * r3.dragDismissThreshold)) goto L27;
         */
        @Override // androidx.customview.widget.ViewDragHelper.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onViewReleased(View view, float f, float f2) {
            boolean z;
            int i;
            OnDismissListener onDismissListener;
            this.activePointerId = -1;
            int width = view.getWidth();
            boolean z2 = true;
            SwipeDismissBehavior swipeDismissBehavior = SwipeDismissBehavior.this;
            if (f != 0.0f) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                boolean z3 = ViewCompat.Api17Impl.getLayoutDirection(view) == 1;
                int i2 = swipeDismissBehavior.swipeDirection;
                if (i2 != 2) {
                    z = i2 == 0 ? false : false;
                }
                z = true;
            }
            if (z) {
                if (f >= 0.0f) {
                    int left = view.getLeft();
                    int i3 = this.originalCapturedViewLeft;
                    if (left >= i3) {
                        i = i3 + width;
                    }
                }
                i = this.originalCapturedViewLeft - width;
            } else {
                i = this.originalCapturedViewLeft;
                z2 = false;
            }
            if (swipeDismissBehavior.viewDragHelper.settleCapturedViewAt(i, view.getTop())) {
                SettleRunnable settleRunnable = new SettleRunnable(view, z2);
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postOnAnimation(view, settleRunnable);
            } else {
                if (!z2 || (onDismissListener = swipeDismissBehavior.listener) == null) {
                    return;
                }
                ((BaseTransientBottomBar.C43707) onDismissListener).onDismiss(view);
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public final boolean tryCaptureView(View view, int i) {
            int i2 = this.activePointerId;
            return (i2 == -1 || i2 == i) && SwipeDismissBehavior.this.canSwipeDismissView(view);
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnDismissListener {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SettleRunnable implements Runnable {
        public final boolean dismiss;
        public final View view;

        public SettleRunnable(View view, boolean z) {
            this.view = view;
            this.dismiss = z;
        }

        @Override // java.lang.Runnable
        public final void run() {
            OnDismissListener onDismissListener;
            ViewDragHelper viewDragHelper = SwipeDismissBehavior.this.viewDragHelper;
            if (viewDragHelper != null && viewDragHelper.continueSettling()) {
                View view = this.view;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postOnAnimation(view, this);
            } else {
                if (!this.dismiss || (onDismissListener = SwipeDismissBehavior.this.listener) == null) {
                    return;
                }
                ((BaseTransientBottomBar.C43707) onDismissListener).onDismiss(this.view);
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
        if (!z) {
            return false;
        }
        if (this.viewDragHelper == null) {
            this.viewDragHelper = ViewDragHelper.create(coordinatorLayout, this.dragCallback);
        }
        return this.viewDragHelper.shouldInterceptTouchEvent(motionEvent);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api16Impl.getImportantForAccessibility(view) == 0) {
            ViewCompat.Api16Impl.setImportantForAccessibility(view, 1);
            ViewCompat.removeActionWithId(view, QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING);
            ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
            if (canSwipeDismissView(view)) {
                ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, null, new AccessibilityViewCommand() { // from class: com.google.android.material.behavior.SwipeDismissBehavior.2
                    @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                    public final boolean perform(View view2) {
                        SwipeDismissBehavior swipeDismissBehavior = SwipeDismissBehavior.this;
                        boolean z = false;
                        if (!swipeDismissBehavior.canSwipeDismissView(view2)) {
                            return false;
                        }
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        boolean z2 = ViewCompat.Api17Impl.getLayoutDirection(view2) == 1;
                        int i2 = swipeDismissBehavior.swipeDirection;
                        if ((i2 == 0 && z2) || (i2 == 1 && !z2)) {
                            z = true;
                        }
                        int width = view2.getWidth();
                        if (z) {
                            width = -width;
                        }
                        view2.offsetLeftAndRight(width);
                        view2.setAlpha(0.0f);
                        OnDismissListener onDismissListener = swipeDismissBehavior.listener;
                        if (onDismissListener != null) {
                            ((BaseTransientBottomBar.C43707) onDismissListener).onDismiss(view2);
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
        ViewDragHelper viewDragHelper = this.viewDragHelper;
        if (viewDragHelper == null) {
            return false;
        }
        viewDragHelper.processTouchEvent(motionEvent);
        return true;
    }
}
