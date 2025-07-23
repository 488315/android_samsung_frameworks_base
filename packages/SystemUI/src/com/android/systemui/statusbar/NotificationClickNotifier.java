package com.android.systemui.statusbar;

import android.os.RemoteException;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
        ArrayList arrayList = (ArrayList) notificationClickNotifier.listeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((NotificationInteractionTracker) obj).interactions.put(str, Boolean.TRUE);
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
