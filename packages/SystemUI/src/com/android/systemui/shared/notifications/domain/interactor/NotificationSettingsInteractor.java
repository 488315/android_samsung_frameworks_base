package com.android.systemui.shared.notifications.domain.interactor;

import com.android.systemui.shared.notifications.data.repository.NotificationSettingsRepository;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationSettingsInteractor {
    public final ReadonlyStateFlow isCooldownEnabled;
    public final Flow isNotificationHistoryEnabled;

    public NotificationSettingsInteractor(NotificationSettingsRepository notificationSettingsRepository) {
        this.isNotificationHistoryEnabled = notificationSettingsRepository.isNotificationHistoryEnabled;
        this.isCooldownEnabled = notificationSettingsRepository.isCooldownEnabled;
    }
}
