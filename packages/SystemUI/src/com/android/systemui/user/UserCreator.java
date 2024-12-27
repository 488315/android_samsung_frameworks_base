package com.android.systemui.user;

import android.content.Context;
import android.os.UserManager;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
