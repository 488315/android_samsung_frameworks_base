package com.android.systemui.scene.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class WindowRootViewVisibilityInteractor implements CoreStartable {
    public final ActiveNotificationsInteractor activeNotificationsInteractor;
    public final HeadsUpManager headsUpManager;
    public final ReadonlyStateFlow isLockscreenOrShadeVisible;
    public final ReadonlyStateFlow isLockscreenOrShadeVisibleAndInteractive;
    public final KeyguardRepository keyguardRepository;
    public NotificationPresenter notificationPresenter;
    public final CoroutineScope scope;
    public final WindowRootViewVisibilityRepository windowRootViewVisibilityRepository;

    public WindowRootViewVisibilityInteractor(CoroutineScope coroutineScope, WindowRootViewVisibilityRepository windowRootViewVisibilityRepository, KeyguardRepository keyguardRepository, HeadsUpManager headsUpManager, PowerInteractor powerInteractor, ActiveNotificationsInteractor activeNotificationsInteractor, Provider provider, PrimaryBouncerInteractor primaryBouncerInteractor) {
        this.scope = coroutineScope;
        this.windowRootViewVisibilityRepository = windowRootViewVisibilityRepository;
        this.keyguardRepository = keyguardRepository;
        this.headsUpManager = headsUpManager;
        this.activeNotificationsInteractor = activeNotificationsInteractor;
        ReadonlyStateFlow readonlyStateFlow = windowRootViewVisibilityRepository.isLockscreenOrShadeVisible;
        this.isLockscreenOrShadeVisible = readonlyStateFlow;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine = FlowKt.combine(primaryBouncerInteractor.isShowing, readonlyStateFlow, powerInteractor.isAwake, new WindowRootViewVisibilityInteractor$isLockscreenOrShadeVisibleAndInteractive$1(null));
        SharingStarted.Companion.getClass();
        this.isLockscreenOrShadeVisibleAndInteractive = FlowKt.stateIn(combine, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new WindowRootViewVisibilityInteractor$start$1(this, null), 7);
    }
}
