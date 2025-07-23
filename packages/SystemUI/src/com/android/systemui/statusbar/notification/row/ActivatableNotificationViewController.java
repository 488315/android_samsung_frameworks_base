package com.android.systemui.statusbar.notification.row;

import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.util.ViewController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ActivatableNotificationViewController extends ViewController {
    public final AccessibilityManager mAccessibilityManager;
    public final ExpandableOutlineViewController mExpandableOutlineViewController;
    public final FalsingManager mFalsingManager;
    public final TouchHandler mTouchHandler;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TouchHandler implements Gefingerpoken, View.OnTouchListener {
        public TouchHandler() {
        }

        @Override // com.android.systemui.Gefingerpoken
        public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return false;
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 1) {
                ((ActivatableNotificationView) ((ViewController) ActivatableNotificationViewController.this).mView).mLastActionUpTime = motionEvent.getEventTime();
            }
            if (!ActivatableNotificationViewController.this.mAccessibilityManager.isTouchExplorationEnabled() && motionEvent.getAction() == 1) {
                return ActivatableNotificationViewController.this.mFalsingManager.isFalseTap(1);
            }
            return false;
        }
    }

    public ActivatableNotificationViewController(ActivatableNotificationView activatableNotificationView, ExpandableOutlineViewController expandableOutlineViewController, AccessibilityManager accessibilityManager, FalsingManager falsingManager) {
        super(activatableNotificationView);
        this.mTouchHandler = new TouchHandler();
        this.mExpandableOutlineViewController = expandableOutlineViewController;
        this.mAccessibilityManager = accessibilityManager;
        this.mFalsingManager = falsingManager;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        this.mExpandableOutlineViewController.mExpandableViewController.getClass();
        ActivatableNotificationView activatableNotificationView = (ActivatableNotificationView) this.mView;
        TouchHandler touchHandler = this.mTouchHandler;
        activatableNotificationView.setOnTouchListener(touchHandler);
        ((ActivatableNotificationView) this.mView).mTouchHandler = touchHandler;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
    }
}
