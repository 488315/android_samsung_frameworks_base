package com.android.systemui.qp.flashlight;

import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SubscreenFlashLightController$$ExternalSyntheticLambda0 implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        ((FlashlightControllerImpl) ((FlashlightController) Dependency.get(FlashlightController.class))).showUnavailableMessage();
    }
}
