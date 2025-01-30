package com.android.systemui.p014qp.util;

import com.android.systemui.Dependency;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SubscreenUtil$$ExternalSyntheticLambda2 implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        SubscreenDeviceModelParent subscreenDeviceModelParent = ((SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class)).mDeviceModel;
        if (subscreenDeviceModelParent != null) {
            subscreenDeviceModelParent.closeFullscreenFullPopupWindow();
        }
    }
}
