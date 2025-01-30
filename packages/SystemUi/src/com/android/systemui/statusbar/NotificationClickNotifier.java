package com.android.systemui.statusbar;

import com.android.internal.statusbar.IStatusBarService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationClickNotifier {
    public final IStatusBarService barService;
    public final List listeners = new ArrayList();
    public final Executor mainExecutor;

    public NotificationClickNotifier(IStatusBarService iStatusBarService, Executor executor) {
        this.barService = iStatusBarService;
        this.mainExecutor = executor;
    }

    public static final void access$notifyListenersAboutInteraction(NotificationClickNotifier notificationClickNotifier, String str) {
        Iterator it = ((ArrayList) notificationClickNotifier.listeners).iterator();
        while (it.hasNext()) {
            ((NotificationInteractionTracker) it.next()).interactions.put(str, Boolean.TRUE);
        }
    }
}
