package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.HeadsUpNotificationIconViewStateRepository;
import kotlinx.coroutines.flow.StateFlowImpl;

public final class HeadsUpNotificationIconInteractor {
    public final StateFlowImpl isolatedIconLocation;
    public final StateFlowImpl isolatedNotification;

    public HeadsUpNotificationIconInteractor(HeadsUpNotificationIconViewStateRepository headsUpNotificationIconViewStateRepository) {
        this.isolatedIconLocation = headsUpNotificationIconViewStateRepository.isolatedIconLocation;
        this.isolatedNotification = headsUpNotificationIconViewStateRepository.isolatedNotification;
    }
}
