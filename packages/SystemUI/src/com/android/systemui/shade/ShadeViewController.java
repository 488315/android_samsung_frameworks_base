package com.android.systemui.shade;

import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController;
import com.android.systemui.statusbar.policy.BrightnessMirrorController$$ExternalSyntheticLambda0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface ShadeViewController {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    void cancelInputFocusTransfer();

    void closeQsIfPossible();

    void finishInputFocusTransfer(float f);

    KeyguardBottomAreaViewController getKeyguardBottomAreaViewController();

    NotificationStackScrollLayoutController getNotificationStackScrollLayoutController();

    ShadeFoldAnimator getShadeFoldAnimator();

    ShadeHeadsUpTracker getShadeHeadsUpTracker$1();

    boolean handleExternalInterceptTouch(MotionEvent motionEvent);

    boolean handleExternalTouch(MotionEvent motionEvent);

    boolean isInFaceWidgetContainer(MotionEvent motionEvent);

    boolean isInLockStarContainer(MotionEvent motionEvent);

    boolean isLaunchTransitionFinished();

    boolean isLaunchTransitionRunning();

    boolean isTouchableArea(MotionEvent motionEvent);

    boolean isViewEnabled();

    void registerAODDoubleTouchListener(View.OnTouchListener onTouchListener);

    void setAlpha(int i, boolean z);

    void setAlphaChangeAnimationEndAction(BrightnessMirrorController$$ExternalSyntheticLambda0 brightnessMirrorController$$ExternalSyntheticLambda0);

    void setQsScrimEnabled(boolean z);

    void startExpandLatencyTracking();

    void startInputFocusTransfer();

    void unregisterAODDoubleTouchListener();

    void updateSystemUiStateFlags();

    void updateTouchableRegion();

    default void setOnStatusBarDownEvent(MotionEvent motionEvent) {
    }
}
