package com.android.keyguard;

import android.os.PowerManager;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class UserActivityNotifier {
    public final PowerManager powerManager;
    public final Executor uiBgExecutor;

    public UserActivityNotifier(Executor executor, PowerManager powerManager) {
        this.uiBgExecutor = executor;
        this.powerManager = powerManager;
    }
}
