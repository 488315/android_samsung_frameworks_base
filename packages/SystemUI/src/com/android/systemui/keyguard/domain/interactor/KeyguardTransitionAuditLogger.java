package com.android.systemui.keyguard.domain.interactor;

import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class KeyguardTransitionAuditLogger {
    public final AodBurnInViewModel aodBurnInViewModel;
    public final DeviceEntryInteractor deviceEntryInteractor;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardOcclusionInteractor keyguardOcclusionInteractor;
    public final KeyguardLogger logger;
    public final PowerInteractor powerInteractor;
    public final CoroutineScope scope;
    public final ShadeInteractor shadeInteractor;
    public final SharedNotificationContainerViewModel sharedNotificationContainerViewModel;

    public KeyguardTransitionAuditLogger(CoroutineScope coroutineScope, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, KeyguardLogger keyguardLogger, PowerInteractor powerInteractor, SharedNotificationContainerViewModel sharedNotificationContainerViewModel, KeyguardRootViewModel keyguardRootViewModel, AodBurnInViewModel aodBurnInViewModel, ShadeInteractor shadeInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor, DeviceEntryInteractor deviceEntryInteractor) {
        this.scope = coroutineScope;
        this.keyguardInteractor = keyguardInteractor;
        this.logger = keyguardLogger;
        this.powerInteractor = powerInteractor;
        this.sharedNotificationContainerViewModel = sharedNotificationContainerViewModel;
        this.aodBurnInViewModel = aodBurnInViewModel;
        this.shadeInteractor = shadeInteractor;
        this.keyguardOcclusionInteractor = keyguardOcclusionInteractor;
        this.deviceEntryInteractor = deviceEntryInteractor;
    }
}
