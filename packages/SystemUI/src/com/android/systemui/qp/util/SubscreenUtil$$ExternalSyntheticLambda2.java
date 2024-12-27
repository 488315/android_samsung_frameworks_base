package com.android.systemui.qp.util;

import com.android.systemui.Dependency;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;

public final /* synthetic */ class SubscreenUtil$$ExternalSyntheticLambda2 implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        SubscreenDeviceModelParent subscreenDeviceModelParent = ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel;
        if (subscreenDeviceModelParent != null) {
            subscreenDeviceModelParent.closeFullscreenFullPopupWindow();
        }
    }
}
