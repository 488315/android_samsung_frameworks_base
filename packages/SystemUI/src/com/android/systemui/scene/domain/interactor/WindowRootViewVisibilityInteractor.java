package com.android.systemui.scene.domain.interactor;

import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import javax.inject.Provider;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class WindowRootViewVisibilityInteractor implements CoreStartable {
    public final HeadsUpManager headsUpManager;
    public final ReadonlyStateFlow isLockscreenOrShadeVisible;
    public final ReadonlyStateFlow isLockscreenOrShadeVisibleAndInteractive;
    public final KeyguardRepository keyguardRepository;
    public NotificationPresenter notificationPresenter;
    public NotificationsController notificationsController;
    public final CoroutineScope scope;
    public final WindowRootViewVisibilityRepository windowRootViewVisibilityRepository;

    public WindowRootViewVisibilityInteractor(CoroutineScope coroutineScope, WindowRootViewVisibilityRepository windowRootViewVisibilityRepository, KeyguardRepository keyguardRepository, HeadsUpManager headsUpManager, PowerInteractor powerInteractor, ActiveNotificationsInteractor activeNotificationsInteractor, Provider provider) {
        this.scope = coroutineScope;
        this.windowRootViewVisibilityRepository = windowRootViewVisibilityRepository;
        this.keyguardRepository = keyguardRepository;
        this.headsUpManager = headsUpManager;
        Flags.sceneContainer();
        ReadonlyStateFlow readonlyStateFlow = windowRootViewVisibilityRepository.isLockscreenOrShadeVisible;
        this.isLockscreenOrShadeVisible = readonlyStateFlow;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, powerInteractor.isAwake, new WindowRootViewVisibilityInteractor$isLockscreenOrShadeVisibleAndInteractive$1(null));
        SharingStarted.Companion.getClass();
        this.isLockscreenOrShadeVisibleAndInteractive = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BuildersKt.launch$default(this.scope, null, null, new WindowRootViewVisibilityInteractor$start$1(this, null), 3);
    }
}
