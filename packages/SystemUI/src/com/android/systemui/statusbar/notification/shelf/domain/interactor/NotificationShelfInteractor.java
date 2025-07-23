package com.android.systemui.statusbar.notification.shelf.domain.interactor;

import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepository;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationShelfInteractor {
    public final DeviceEntryFaceAuthRepository deviceEntryFaceAuthRepository;
    public final KeyguardRepository keyguardRepository;
    public final LockscreenShadeTransitionController keyguardTransitionController;
    public final PowerInteractor powerInteractor;
    public final ShadeControllerImpl shadeControllerImpl;
    public final StatusBarStateControllerImpl statusBarStateControllerImpl;

    public NotificationShelfInteractor(KeyguardRepository keyguardRepository, DeviceEntryFaceAuthRepository deviceEntryFaceAuthRepository, PowerInteractor powerInteractor, ShadeModeInteractor shadeModeInteractor, LockscreenShadeTransitionController lockscreenShadeTransitionController, StatusBarStateControllerImpl statusBarStateControllerImpl, ShadeControllerImpl shadeControllerImpl) {
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
