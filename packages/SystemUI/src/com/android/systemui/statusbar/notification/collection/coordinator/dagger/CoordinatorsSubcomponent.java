package com.android.systemui.statusbar.notification.collection.coordinator.dagger;

import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
/* loaded from: classes2.dex */
public interface CoordinatorsSubcomponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        CoordinatorsSubcomponent create();
    }

    @Internal
    NotifCoordinators getNotifCoordinators();
}
