package com.android.systemui.shade;

import android.graphics.Rect;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.BrightnessMirrorController$$ExternalSyntheticLambda0;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda16 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda16(NotificationPanelViewController notificationPanelViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationPanelViewController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        switch (i) {
            case 0:
                float floatValue = ((Float) obj).floatValue();
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                notificationPanelViewController.setExpandedHeightInternal(floatValue);
                break;
            case 1:
                boolean booleanValue = ((Boolean) obj).booleanValue();
                Rect rect2 = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                notificationPanelViewController.onTrackingStopped(booleanValue);
                break;
            case 2:
                BrightnessMirrorController$$ExternalSyntheticLambda0 brightnessMirrorController$$ExternalSyntheticLambda0 = notificationPanelViewController.mPanelAlphaEndAction;
                if (brightnessMirrorController$$ExternalSyntheticLambda0 != null) {
                    brightnessMirrorController$$ExternalSyntheticLambda0.run();
                }
                notificationPanelViewController.mView.post(new NotificationPanelViewController$$ExternalSyntheticLambda18(notificationPanelViewController, 9));
                break;
            case 3:
                Rect rect3 = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                notificationPanelViewController.getClass();
                if (((TransitionStep) obj).transitionState == TransitionState.FINISHED) {
                    notificationPanelViewController.updateExpandedHeightToMaxHeight();
                    break;
                }
                break;
            case 4:
                KeyguardState keyguardState = (KeyguardState) obj;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
                NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController != null ? notificationStackScrollLayoutController.mView : null;
                if (notificationStackScrollLayout != null && keyguardState == KeyguardState.OCCLUDED && notificationPanelViewController.mMediaNowBarExpandState == 1) {
                    notificationStackScrollLayout.mVislbeNSSLWhileMediaExpanded = true;
                    notificationStackScrollLayout.setAlpha(1.0f);
                    break;
                }
                break;
            case 5:
                Rect rect4 = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                notificationPanelViewController.getClass();
                if (((Boolean) obj).booleanValue()) {
                    notificationPanelViewController.updateSystemUiStateFlags();
                    break;
                }
                break;
            default:
                notificationPanelViewController.mKeyguardStatusBarViewController.setAlpha(((Float) obj).floatValue());
                break;
        }
    }
}
