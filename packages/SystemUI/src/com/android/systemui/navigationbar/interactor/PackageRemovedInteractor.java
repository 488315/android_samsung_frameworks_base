package com.android.systemui.navigationbar.interactor;

import android.content.IntentFilter;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.UserTracker;

public final class PackageRemovedInteractor {
    public final BroadcastDispatcher broadcastDispatcher;
    public PackageRemovedInteractor$addCallback$2 broadcastReceiver;
    public final IntentFilter intentFilter;
    public final UserTracker userTracker;

    public PackageRemovedInteractor(BroadcastDispatcher broadcastDispatcher, UserTracker userTracker) {
        this.broadcastDispatcher = broadcastDispatcher;
        this.userTracker = userTracker;
        IntentFilter intentFilter = new IntentFilter();
        this.intentFilter = intentFilter;
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
    }
}
