package com.android.keyguard;

import android.os.PowerManager;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class UserActivityNotifier {
    public final PowerManager powerManager;
    public final Executor uiBgExecutor;

    public UserActivityNotifier(Executor executor, PowerManager powerManager) {
        this.uiBgExecutor = executor;
        this.powerManager = powerManager;
    }
}
