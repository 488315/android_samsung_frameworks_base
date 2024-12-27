package com.android.systemui.shared.notifications.domain.interactor;

import com.android.systemui.shared.notifications.data.repository.NotificationSettingsRepository;
import kotlinx.coroutines.flow.Flow;

public final class NotificationSettingsInteractor {
    public final Flow isNotificationHistoryEnabled;

    public NotificationSettingsInteractor(NotificationSettingsRepository notificationSettingsRepository) {
        this.isNotificationHistoryEnabled = notificationSettingsRepository.isNotificationHistoryEnabled;
    }
}
