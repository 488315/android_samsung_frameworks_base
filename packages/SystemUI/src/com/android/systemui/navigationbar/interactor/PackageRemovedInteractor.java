package com.android.systemui.navigationbar.interactor;

import android.content.IntentFilter;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.UserTracker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addDataScheme("package");
    }
}
