package com.android.server.appbinding;

import android.os.UserHandle;
import com.android.internal.os.BackgroundThread;
import com.android.server.appbinding.finders.CarrierMessagingClientServiceFinder;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppBindingService$$ExternalSyntheticLambda2 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        CarrierMessagingClientServiceFinder carrierMessagingClientServiceFinder = (CarrierMessagingClientServiceFinder) obj;
        carrierMessagingClientServiceFinder.mRoleManager.addOnRoleHoldersChangedListenerAsUser(BackgroundThread.getExecutor(), carrierMessagingClientServiceFinder.mRoleHolderChangedListener, UserHandle.ALL);
    }
}
