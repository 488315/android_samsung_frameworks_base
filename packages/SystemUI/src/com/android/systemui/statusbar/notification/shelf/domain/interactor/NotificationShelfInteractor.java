package com.android.systemui.statusbar.notification.shelf.domain.interactor;

import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepository;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationShelfInteractor {
    public final DeviceEntryFaceAuthRepository deviceEntryFaceAuthRepository;
    public final KeyguardRepository keyguardRepository;
    public final LockscreenShadeTransitionController keyguardTransitionController;
    public final PowerInteractor powerInteractor;
    public final ShadeControllerImpl shadeControllerImpl;
    public final StatusBarStateControllerImpl statusBarStateControllerImpl;

    public NotificationShelfInteractor(KeyguardRepository keyguardRepository, DeviceEntryFaceAuthRepository deviceEntryFaceAuthRepository, PowerInteractor powerInteractor, LockscreenShadeTransitionController lockscreenShadeTransitionController, StatusBarStateControllerImpl statusBarStateControllerImpl, ShadeControllerImpl shadeControllerImpl) {
        this.keyguardRepository = keyguardRepository;
        this.deviceEntryFaceAuthRepository = deviceEntryFaceAuthRepository;
        this.powerInteractor = powerInteractor;
        this.keyguardTransitionController = lockscreenShadeTransitionController;
        this.statusBarStateControllerImpl = statusBarStateControllerImpl;
        this.shadeControllerImpl = shadeControllerImpl;
    }

    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isShelfStatic() {
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((KeyguardRepositoryImpl) this.keyguardRepository).isKeyguardShowing, ((DeviceEntryFaceAuthRepositoryImpl) this.deviceEntryFaceAuthRepository).isBypassEnabled, new NotificationShelfInteractor$isShelfStatic$1(null));
    }
}
