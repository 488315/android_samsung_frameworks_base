package com.android.systemui.navigationbar.interactor;

import android.content.IntentFilter;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.UserTracker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
