package com.android.systemui.shade;

import android.graphics.Rect;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda20 implements Function0 {
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda20(NotificationPanelViewController notificationPanelViewController) {
        this.f$0 = notificationPanelViewController;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_EDIT_MODE);
        notificationPanelViewController.mIsLaunchTransitionFinished = true;
        notificationPanelViewController.mStatusBarKeyguardViewManager.setLaunchEditMode();
        return null;
    }
}
