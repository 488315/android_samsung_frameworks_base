package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.NotificationsKeyguardViewStateRepository;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes3.dex */
public final class NotificationsKeyguardInteractor {
    public final StateFlowImpl areNotificationsFullyHidden;
    public final NotificationsKeyguardViewStateRepository repository;

    public NotificationsKeyguardInteractor(NotificationsKeyguardViewStateRepository notificationsKeyguardViewStateRepository) {
        this.repository = notificationsKeyguardViewStateRepository;
        this.areNotificationsFullyHidden = notificationsKeyguardViewStateRepository.areNotificationsFullyHidden;
    }
}
