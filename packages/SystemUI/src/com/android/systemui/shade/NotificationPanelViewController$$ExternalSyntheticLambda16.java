package com.android.systemui.shade;

import android.content.res.Resources;
import android.graphics.Rect;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.BrightnessMirrorController$$ExternalSyntheticLambda0;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda16 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda16(NotificationPanelViewController notificationPanelViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationPanelViewController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) throws Resources.NotFoundException {
        int i = this.$r8$classId;
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        switch (i) {
            case 0:
                float fFloatValue = ((Float) obj).floatValue();
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                notificationPanelViewController.setExpandedHeightInternal(fFloatValue);
                break;
            case 1:
                boolean zBooleanValue = ((Boolean) obj).booleanValue();
                Rect rect2 = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                notificationPanelViewController.onTrackingStopped(zBooleanValue);
                break;
            case 2:
                BrightnessMirrorController$$ExternalSyntheticLambda0 brightnessMirrorController$$ExternalSyntheticLambda0 = notificationPanelViewController.mPanelAlphaEndAction;
                if (brightnessMirrorController$$ExternalSyntheticLambda0 != null) {
                    brightnessMirrorController$$ExternalSyntheticLambda0.run();
                }
                notificationPanelViewController.mView.post(new NotificationPanelViewController$$ExternalSyntheticLambda18(notificationPanelViewController, 11));
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
                if (notificationStackScrollLayoutController != null && keyguardState == KeyguardState.OCCLUDED && notificationPanelViewController.mMediaNowBarExpandState == 1) {
                    notificationStackScrollLayoutController.mMaxAlphaForKeyguard = 1.0f;
                    notificationStackScrollLayoutController.mMaxAlphaForKeyguardSource = "MediaNowBar occluded";
                    notificationStackScrollLayoutController.updateAlpha$1$1();
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
