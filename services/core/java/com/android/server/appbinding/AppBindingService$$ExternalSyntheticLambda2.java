package com.android.server.appbinding;

import android.os.UserHandle;

import com.android.internal.os.BackgroundThread;
import com.android.server.appbinding.finders.CarrierMessagingClientServiceFinder;

import java.util.function.Consumer;

public final /* synthetic */ class AppBindingService$$ExternalSyntheticLambda2 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        CarrierMessagingClientServiceFinder carrierMessagingClientServiceFinder =
                (CarrierMessagingClientServiceFinder) obj;
        carrierMessagingClientServiceFinder.mRoleManager.addOnRoleHoldersChangedListenerAsUser(
                BackgroundThread.getExecutor(),
                carrierMessagingClientServiceFinder.mRoleHolderChangedListener,
                UserHandle.ALL);
    }
}
