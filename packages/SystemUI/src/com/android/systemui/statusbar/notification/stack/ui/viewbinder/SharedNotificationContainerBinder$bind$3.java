package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SharedNotificationContainerBinder$bind$3 implements Runnable {
    public final /* synthetic */ SharedNotificationContainerViewModel $viewModel;

    public SharedNotificationContainerBinder$bind$3(SharedNotificationContainerViewModel sharedNotificationContainerViewModel) {
        this.$viewModel = sharedNotificationContainerViewModel;
    }

    @Override // java.lang.Runnable
    public final void run() {
        StateFlowImpl stateFlowImpl = this.$viewModel.interactor._notificationStackChanged;
        LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl.getValue(), 1L, stateFlowImpl, null);
    }
}
