package com.samsung.android.sdk.command;

import com.android.systemui.bixby2.SystemUICommandActionHandler;

/* loaded from: classes4.dex */
public class CommandSdk {
    public static final Object sWaitLock = new Object();
    public SystemUICommandActionHandler mActionHandler;

    public class LazyHolder {
        public static final CommandSdk INSTANCE = new CommandSdk();

        private LazyHolder() {
        }
    }

    private CommandSdk() {
    }
}
