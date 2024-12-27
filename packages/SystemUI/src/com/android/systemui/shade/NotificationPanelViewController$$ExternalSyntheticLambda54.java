package com.android.systemui.shade;

import com.android.systemui.Flags;
import com.android.systemui.keyguard.KeyguardBottomAreaRefactor;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherController;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda54 implements Consumer {
    public final /* synthetic */ NotificationPanelViewController f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ NotificationStackScrollLayoutController f$2;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda54(NotificationPanelViewController notificationPanelViewController, boolean z, NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.f$0 = notificationPanelViewController;
        this.f$1 = z;
        this.f$2 = notificationStackScrollLayoutController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        boolean z = this.f$1;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.f$2;
        Float f = (Float) obj;
        notificationPanelViewController.mKeyguardStatusViewController.setAlpha(f.floatValue());
        if (!z) {
            notificationStackScrollLayoutController.mMaxAlphaForKeyguard = f.floatValue();
            notificationStackScrollLayoutController.mMaxAlphaForKeyguardSource = "NPVC.setTransitionAlpha()";
            notificationStackScrollLayoutController.updateAlpha$1$1();
        }
        KeyguardBottomAreaRefactor keyguardBottomAreaRefactor = KeyguardBottomAreaRefactor.INSTANCE;
        Flags.keyguardBottomAreaRefactor();
        ((KeyguardRepositoryImpl) notificationPanelViewController.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.updateState(null, f);
        notificationPanelViewController.mLockIconViewController.mView.setAlpha(f.floatValue());
        KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController.mKeyguardQsUserSwitchController;
        if (keyguardQsUserSwitchController != null) {
            keyguardQsUserSwitchController.setAlpha(f.floatValue());
        }
        KeyguardUserSwitcherController keyguardUserSwitcherController = notificationPanelViewController.mKeyguardUserSwitcherController;
        if (keyguardUserSwitcherController != null) {
            keyguardUserSwitcherController.setAlpha(f.floatValue());
        }
    }
}
