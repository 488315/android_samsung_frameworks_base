package com.android.systemui.qs.tiles;

import android.content.Intent;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MobileDataTile$$ExternalSyntheticLambda14 implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        Intent intent = MobileDataTile.DATA_SETTINGS;
        SubscreenDeviceModelParent subscreenDeviceModelParent = ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel;
        if (subscreenDeviceModelParent != null) {
            subscreenDeviceModelParent.closeFullscreenFullPopupWindow();
        }
    }
}
