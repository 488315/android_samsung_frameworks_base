package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.HeadsUpNotificationIconViewStateRepository;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HeadsUpNotificationIconInteractor {
    public final StateFlowImpl isolatedIconLocation;
    public final StateFlowImpl isolatedNotification;

    public HeadsUpNotificationIconInteractor(HeadsUpNotificationIconViewStateRepository headsUpNotificationIconViewStateRepository) {
        this.isolatedIconLocation = headsUpNotificationIconViewStateRepository.isolatedIconLocation;
        this.isolatedNotification = headsUpNotificationIconViewStateRepository.isolatedNotification;
    }
}
