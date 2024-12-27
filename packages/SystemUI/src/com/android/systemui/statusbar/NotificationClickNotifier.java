package com.android.systemui.statusbar;

import android.os.RemoteException;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

public final class NotificationClickNotifier {
    public final Executor backgroundExecutor;
    public final IStatusBarService barService;
    public final List listeners = new ArrayList();
    public final Executor mainExecutor;

    public NotificationClickNotifier(IStatusBarService iStatusBarService, Executor executor, Executor executor2) {
        this.barService = iStatusBarService;
        this.mainExecutor = executor;
        this.backgroundExecutor = executor2;
    }

    public static final void access$notifyListenersAboutInteraction(NotificationClickNotifier notificationClickNotifier, String str) {
        Iterator it = ((ArrayList) notificationClickNotifier.listeners).iterator();
        while (it.hasNext()) {
            ((NotificationInteractionTracker) it.next()).interactions.put(str, Boolean.TRUE);
        }
    }

    public final void onNotificationClick(final String str, NotificationVisibility notificationVisibility) {
        try {
            this.barService.onNotificationClick(str, notificationVisibility);
        } catch (RemoteException unused) {
        }
        this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationClickNotifier$onNotificationClick$1
            @Override // java.lang.Runnable
            public final void run() {
                NotificationClickNotifier.access$notifyListenersAboutInteraction(NotificationClickNotifier.this, str);
            }
        });
    }
}
