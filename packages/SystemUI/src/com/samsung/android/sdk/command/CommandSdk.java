package com.samsung.android.sdk.command;

import com.android.systemui.bixby2.SystemUICommandActionHandler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CommandSdk {
    public static final Object sWaitLock = new Object();
    public SystemUICommandActionHandler mActionHandler;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LazyHolder {
        public static final CommandSdk INSTANCE = new CommandSdk();

        private LazyHolder() {
        }
    }

    private CommandSdk() {
    }
}
