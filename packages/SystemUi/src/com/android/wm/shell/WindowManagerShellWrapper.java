package com.android.wm.shell;

import android.view.WindowManagerGlobal;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.pip.PinnedStackListenerForwarder;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class WindowManagerShellWrapper {
    public final PinnedStackListenerForwarder mPinnedStackListenerForwarder;

    public WindowManagerShellWrapper(ShellExecutor shellExecutor) {
        this.mPinnedStackListenerForwarder = new PinnedStackListenerForwarder(shellExecutor);
    }

    public final void addPinnedStackListener(PinnedStackListenerForwarder.PinnedTaskListener pinnedTaskListener) {
        PinnedStackListenerForwarder pinnedStackListenerForwarder = this.mPinnedStackListenerForwarder;
        pinnedStackListenerForwarder.mListeners.add(pinnedTaskListener);
        WindowManagerGlobal.getWindowManagerService().registerPinnedTaskListener(0, pinnedStackListenerForwarder.mListenerImpl);
    }
}
