package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.disableflags.data.repository.DisableFlagsRepository;

public final class NotificationAlertsInteractor {
    public final DisableFlagsRepository disableFlagsRepository;

    public NotificationAlertsInteractor(DisableFlagsRepository disableFlagsRepository) {
        this.disableFlagsRepository = disableFlagsRepository;
    }
}
