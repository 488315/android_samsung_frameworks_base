package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.HeadsUpNotificationIconViewStateRepository;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes3.dex */
public final class HeadsUpNotificationIconInteractor {
    public final StateFlowImpl isolatedIconLocation;
    public final StateFlowImpl isolatedNotification;
    public final HeadsUpNotificationIconViewStateRepository repository;

    public HeadsUpNotificationIconInteractor(HeadsUpNotificationIconViewStateRepository headsUpNotificationIconViewStateRepository) {
        this.repository = headsUpNotificationIconViewStateRepository;
        this.isolatedIconLocation = headsUpNotificationIconViewStateRepository.isolatedIconLocation;
        this.isolatedNotification = headsUpNotificationIconViewStateRepository.isolatedNotification;
    }
}
