package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.disableflags.data.repository.DisableFlagsRepository;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationAlertsInteractor {
    public final DisableFlagsRepository disableFlagsRepository;

    public NotificationAlertsInteractor(DisableFlagsRepository disableFlagsRepository) {
        this.disableFlagsRepository = disableFlagsRepository;
    }
}
