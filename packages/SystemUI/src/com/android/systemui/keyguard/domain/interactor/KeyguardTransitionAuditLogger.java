package com.android.systemui.keyguard.domain.interactor;

import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardTransitionAuditLogger {
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardOcclusionInteractor keyguardOcclusionInteractor;
    public final KeyguardRootViewModel keyguardRootViewModel;
    public final KeyguardLogger logger;
    public final PowerInteractor powerInteractor;
    public final CoroutineScope scope;
    public final ShadeInteractor shadeInteractor;
    public final SharedNotificationContainerViewModel sharedNotificationContainerViewModel;

    public KeyguardTransitionAuditLogger(CoroutineScope coroutineScope, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, KeyguardLogger keyguardLogger, PowerInteractor powerInteractor, SharedNotificationContainerViewModel sharedNotificationContainerViewModel, KeyguardRootViewModel keyguardRootViewModel, ShadeInteractor shadeInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor) {
        this.scope = coroutineScope;
        this.keyguardInteractor = keyguardInteractor;
        this.logger = keyguardLogger;
        this.powerInteractor = powerInteractor;
        this.sharedNotificationContainerViewModel = sharedNotificationContainerViewModel;
        this.keyguardRootViewModel = keyguardRootViewModel;
        this.shadeInteractor = shadeInteractor;
        this.keyguardOcclusionInteractor = keyguardOcclusionInteractor;
    }
}
