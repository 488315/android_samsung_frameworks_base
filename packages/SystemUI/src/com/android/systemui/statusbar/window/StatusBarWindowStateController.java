package com.android.systemui.statusbar.window;

import com.android.systemui.statusbar.CommandQueue;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class StatusBarWindowStateController {
    public final Set listeners;
    public final int thisDisplayId;
    public int windowState;

    public StatusBarWindowStateController(int i, CommandQueue commandQueue) {
        this.thisDisplayId = i;
        CommandQueue.Callbacks callbacks = new CommandQueue.Callbacks() { // from class: com.android.systemui.statusbar.window.StatusBarWindowStateController$commandQueueCallback$1
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void setWindowState(int i2, int i3, int i4) {
                StatusBarWindowStateController statusBarWindowStateController = StatusBarWindowStateController.this;
                if (i2 == statusBarWindowStateController.thisDisplayId && i3 == 1 && statusBarWindowStateController.windowState != i4) {
                    statusBarWindowStateController.windowState = i4;
                    Iterator it = statusBarWindowStateController.listeners.iterator();
                    while (it.hasNext()) {
                        ((StatusBarWindowStateListener) it.next()).onStatusBarWindowStateChanged(i4);
                    }
                }
            }
        };
        this.listeners = new HashSet();
        commandQueue.addCallback(callbacks);
    }

    public final void addListener(StatusBarWindowStateListener statusBarWindowStateListener) {
        this.listeners.add(statusBarWindowStateListener);
    }
}
