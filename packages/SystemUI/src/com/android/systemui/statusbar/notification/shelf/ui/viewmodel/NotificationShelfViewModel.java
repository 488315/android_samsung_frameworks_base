package com.android.systemui.statusbar.notification.shelf.ui.viewmodel;

import com.android.systemui.statusbar.notification.row.ui.viewmodel.ActivatableNotificationViewModel;
import com.android.systemui.statusbar.notification.shelf.domain.interactor.NotificationShelfInteractor;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationShelfViewModel implements ActivatableNotificationViewModel {
    public final /* synthetic */ ActivatableNotificationViewModel $$delegate_0;
    public final NotificationShelfInteractor interactor;

    public NotificationShelfViewModel(NotificationShelfInteractor notificationShelfInteractor, ActivatableNotificationViewModel activatableNotificationViewModel) {
        this.interactor = notificationShelfInteractor;
        this.$$delegate_0 = activatableNotificationViewModel;
    }

    @Override // com.android.systemui.statusbar.notification.row.ui.viewmodel.ActivatableNotificationViewModel
    public final Flow isTouchable() {
        return this.$$delegate_0.isTouchable();
    }
}
