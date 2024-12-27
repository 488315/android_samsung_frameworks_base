package com.android.systemui.shade;

import android.view.MotionEvent;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback$$ExternalSyntheticLambda1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ShadeController extends CoreStartable {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface ShadeVisibilityListener {
    }

    void animateCollapseShade(float f, int i, boolean z, boolean z2);

    default void animateCollapseShade(int i) {
        animateCollapseShade(1.0f, i, false, false);
    }

    default void animateCollapseShadeForcedDelayed() {
        animateCollapseShade(1.0f, 2, true, true);
    }

    void cancelExpansionAndCollapseShade();

    void closeShadeIfOpen();

    void collapseOnMainThread();

    void collapseShade();

    void collapseShade(boolean z);

    void collapseShadeForActivityStart();

    void collapseWithDuration(int i);

    void instantCollapseShade();

    void instantExpandShade();

    boolean isExpandedVisible();

    boolean isExpandingOrCollapsing();

    boolean isShadeEnabled();

    boolean isShadeFullyOpen();

    void makeExpandedInvisible();

    void makeExpandedVisible(boolean z);

    void onStatusBarTouch(MotionEvent motionEvent);

    void performHapticFeedback();

    void postAnimateCollapseShade();

    void postAnimateForceCollapseShade();

    void postOnShadeExpanded(StatusBarRemoteInputCallback$$ExternalSyntheticLambda1 statusBarRemoteInputCallback$$ExternalSyntheticLambda1);

    default void setNotificationShadeWindowViewController(NotificationShadeWindowViewController notificationShadeWindowViewController) {
    }

    default void setVisibilityListener(CentralSurfacesImpl.AnonymousClass4 anonymousClass4) {
    }

    default void animateCollapseShadeFromCommand(int i, boolean z) {
    }
}
