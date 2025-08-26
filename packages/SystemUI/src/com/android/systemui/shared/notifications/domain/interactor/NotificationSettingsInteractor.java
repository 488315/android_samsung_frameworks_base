package com.android.systemui.shared.notifications.domain.interactor;

import com.android.systemui.shared.notifications.data.repository.NotificationSettingsRepository;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class NotificationSettingsInteractor {
    public final ReadonlyStateFlow isCooldownEnabled;
    public final Flow isNotificationHistoryEnabled;

    public NotificationSettingsInteractor(NotificationSettingsRepository notificationSettingsRepository) {
        this.isNotificationHistoryEnabled = notificationSettingsRepository.isNotificationHistoryEnabled;
        this.isCooldownEnabled = notificationSettingsRepository.isCooldownEnabled;
    }
}
