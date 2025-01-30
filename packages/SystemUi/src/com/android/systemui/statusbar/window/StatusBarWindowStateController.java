package com.android.systemui.statusbar.window;

import com.android.systemui.statusbar.CommandQueue;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
                    Iterator it = ((HashSet) statusBarWindowStateController.listeners).iterator();
                    while (it.hasNext()) {
                        ((StatusBarWindowStateListener) it.next()).onStatusBarWindowStateChanged(i4);
                    }
                }
            }
        };
        this.listeners = new HashSet();
        commandQueue.addCallback(callbacks);
    }
}
