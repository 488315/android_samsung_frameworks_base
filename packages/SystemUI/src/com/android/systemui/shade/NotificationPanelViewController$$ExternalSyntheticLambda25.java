package com.android.systemui.shade;

import android.graphics.Rect;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda25 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda25(NotificationPanelViewController notificationPanelViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationPanelViewController;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                return notificationPanelViewController.mCentralSurfaces.mQSPanelController;
            case 1:
                return notificationPanelViewController.mCentralSurfaces.mQuickQSPanelController;
            default:
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_EDIT_MODE);
                notificationPanelViewController.mIsLaunchTransitionFinished = true;
                notificationPanelViewController.mStatusBarKeyguardViewManager.setLaunchEditMode();
                return null;
        }
    }
}
