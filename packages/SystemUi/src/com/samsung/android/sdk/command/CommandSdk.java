package com.samsung.android.sdk.command;

import com.samsung.android.sdk.command.provider.ICommandActionHandler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CommandSdk {
    public static final Object sWaitLock = new Object();
    public ICommandActionHandler mActionHandler;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LazyHolder {
        public static final CommandSdk INSTANCE = new CommandSdk();

        private LazyHolder() {
        }
    }

    private CommandSdk() {
    }
}
