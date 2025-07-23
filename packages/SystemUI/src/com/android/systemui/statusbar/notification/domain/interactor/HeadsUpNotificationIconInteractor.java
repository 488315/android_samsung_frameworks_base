package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.HeadsUpNotificationIconViewStateRepository;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
