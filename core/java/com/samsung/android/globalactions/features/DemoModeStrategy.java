package com.samsung.android.globalactions.features;

import com.android.internal.C4337R;
import com.samsung.android.globalactions.presentation.strategies.ActionInteractionStrategy;
import com.samsung.android.globalactions.util.ResourcesWrapper;
import com.samsung.android.globalactions.util.ToastController;

/* loaded from: classes5.dex */
public class DemoModeStrategy implements ActionInteractionStrategy {
    private final ResourcesWrapper mResourcesWrapper;
    private final ToastController mToastController;

    public DemoModeStrategy(ToastController toastController, ResourcesWrapper resourcesWrapper) {
        this.mToastController = toastController;
        this.mResourcesWrapper = resourcesWrapper;
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.ActionInteractionStrategy
    public boolean onPressEmergencyModeAction() {
        this.mToastController.showToast(this.mResourcesWrapper.getString(C4337R.string.globalactions_unable_emergency_mode_msg), 1);
        return true;
    }
}
