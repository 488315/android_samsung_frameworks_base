package com.android.systemui.user;

import android.content.Context;
import android.os.UserManager;
import java.util.concurrent.Executor;

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
