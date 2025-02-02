package com.android.wm.shell.shortcut;

import android.view.KeyEvent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ShortcutLaunchPolicy {
    public final ShortcutController mShortcutController;
    public final boolean mSupportMultiSplitStatus;

    public ShortcutLaunchPolicy(ShortcutController shortcutController, boolean z) {
        this.mShortcutController = shortcutController;
        this.mSupportMultiSplitStatus = z;
    }

    public abstract void handleShortCutKeys(KeyEvent keyEvent);
}
