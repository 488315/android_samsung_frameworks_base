package com.android.systemui.statusbar.notification.row;

import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.util.ViewController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ActivatableNotificationViewController extends ViewController {
    public final AccessibilityManager mAccessibilityManager;
    public final ExpandableOutlineViewController mExpandableOutlineViewController;
    public final FalsingManager mFalsingManager;
    public final TouchHandler mTouchHandler;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TouchHandler implements Gefingerpoken, View.OnTouchListener {
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

        @Override // com.android.systemui.Gefingerpoken
        public final boolean onTouchEvent(MotionEvent motionEvent) {
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
