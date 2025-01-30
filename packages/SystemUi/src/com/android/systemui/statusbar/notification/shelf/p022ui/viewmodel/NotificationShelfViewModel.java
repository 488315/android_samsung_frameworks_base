package com.android.systemui.statusbar.notification.shelf.p022ui.viewmodel;

import com.android.systemui.statusbar.notification.row.p021ui.viewmodel.ActivatableNotificationViewModel;
import com.android.systemui.statusbar.notification.shelf.domain.interactor.NotificationShelfInteractor;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationShelfViewModel implements ActivatableNotificationViewModel {
    public final /* synthetic */ ActivatableNotificationViewModel $$delegate_0;
    public final NotificationShelfInteractor interactor;

    public NotificationShelfViewModel(NotificationShelfInteractor notificationShelfInteractor, ActivatableNotificationViewModel activatableNotificationViewModel) {
        this.interactor = notificationShelfInteractor;
        this.$$delegate_0 = activatableNotificationViewModel;
    }

    @Override // com.android.systemui.statusbar.notification.row.p021ui.viewmodel.ActivatableNotificationViewModel
    public final Flow isTouchable() {
        return this.$$delegate_0.isTouchable();
    }
}
