package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.NotificationsKeyguardViewStateRepository;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationsKeyguardInteractor {
    public final StateFlowImpl areNotificationsFullyHidden;
    public final StateFlowImpl isPulseExpanding;
    public final NotificationsKeyguardViewStateRepository repository;

    public NotificationsKeyguardInteractor(NotificationsKeyguardViewStateRepository notificationsKeyguardViewStateRepository) {
        this.repository = notificationsKeyguardViewStateRepository;
        this.isPulseExpanding = notificationsKeyguardViewStateRepository.isPulseExpanding;
        this.areNotificationsFullyHidden = notificationsKeyguardViewStateRepository.areNotificationsFullyHidden;
    }
}
