package com.android.systemui.shared.notifications.domain.interactor;

import com.android.systemui.shared.notifications.data.repository.NotificationSettingsRepository;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationSettingsInteractor {
    public final Flow isNotificationHistoryEnabled;

    public NotificationSettingsInteractor(NotificationSettingsRepository notificationSettingsRepository) {
        this.isNotificationHistoryEnabled = notificationSettingsRepository.isNotificationHistoryEnabled;
    }
}
