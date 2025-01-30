package com.android.systemui.statusbar.phone;

import android.graphics.Rect;
import com.android.internal.widget.ViewClippingUtil;
import com.android.systemui.statusbar.HeadsUpStatusBarView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class HeadsUpAppearanceController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HeadsUpAppearanceController f$0;

    public /* synthetic */ HeadsUpAppearanceController$$ExternalSyntheticLambda2(HeadsUpAppearanceController headsUpAppearanceController, int i) {
        this.$r8$classId = i;
        this.f$0 = headsUpAppearanceController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                HeadsUpAppearanceController headsUpAppearanceController = this.f$0;
                Rect rect = ((HeadsUpStatusBarView) headsUpAppearanceController.mView).mIconDrawingRect;
                NotificationIconContainer notificationIconContainer = headsUpAppearanceController.mNotificationIconAreaController.mNotificationIcons;
                notificationIconContainer.resetViewStates();
                notificationIconContainer.calculateIconXTranslations();
                notificationIconContainer.applyIconStates();
                break;
            default:
                HeadsUpAppearanceController headsUpAppearanceController2 = this.f$0;
                ViewClippingUtil.setClippingDeactivated(headsUpAppearanceController2.mView, false, headsUpAppearanceController2.mParentClippingParams);
                break;
        }
    }
}
