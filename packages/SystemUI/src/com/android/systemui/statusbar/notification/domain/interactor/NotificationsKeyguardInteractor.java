package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.NotificationsKeyguardViewStateRepository;
import kotlinx.coroutines.flow.StateFlowImpl;

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
