package com.android.systemui.statusbar.notification.shelf.ui.viewmodel;

import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.ActivatableNotificationViewModel;
import com.android.systemui.statusbar.notification.shelf.domain.interactor.NotificationShelfInteractor;
import com.android.systemui.window.domain.interactor.WindowRootViewBlurInteractor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class NotificationShelfViewModel implements ActivatableNotificationViewModel {
    public final /* synthetic */ ActivatableNotificationViewModel $$delegate_0;
    public final NotificationShelfButtonViewModel clearAllButton;
    public final NotificationShelfInteractor interactor;
    public final Lazy isAlignedToEnd$delegate = LazyKt__LazyJVMKt.lazy(new NotificationShelfViewModel$$ExternalSyntheticLambda0());
    public final ReadonlyStateFlow isBlurSupported;

    public NotificationShelfViewModel(NotificationShelfInteractor notificationShelfInteractor, WindowRootViewBlurInteractor windowRootViewBlurInteractor, ActivatableNotificationViewModel activatableNotificationViewModel, ActiveNotificationsInteractor activeNotificationsInteractor) {
        this.$$delegate_0 = activatableNotificationViewModel;
        this.interactor = notificationShelfInteractor;
        this.isBlurSupported = windowRootViewBlurInteractor.isBlurCurrentlySupported;
        this.clearAllButton = new NotificationShelfButtonViewModel(activeNotificationsInteractor.hasClearableNotifications);
    }

    @Override // com.android.systemui.statusbar.notification.row.ui.viewmodel.ActivatableNotificationViewModel
    public final Flow isTouchable() {
        return this.$$delegate_0.isTouchable();
    }
}
