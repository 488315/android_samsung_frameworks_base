package com.android.systemui.user;

import android.content.Context;
import android.os.UserManager;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UserCreator {
    public final Executor bgExecutor;
    public final Context context;
    public final Executor mainExecutor;
    public final UserManager userManager;

    public UserCreator(Context context, UserManager userManager, Executor executor, Executor executor2) {
        this.context = context;
        this.userManager = userManager;
        this.mainExecutor = executor;
        this.bgExecutor = executor2;
    }
}
