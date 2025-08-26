package com.android.systemui.user.utils;

import android.content.Context;
import android.os.UserHandle;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class UserScopedServiceImpl implements UserScopedService {
    public final Context context;
    public final Class serviceType;

    public UserScopedServiceImpl(Context context, Class<Object> cls) {
        this.context = context;
        this.serviceType = cls;
    }

    public final Object forUser(UserHandle userHandle) {
        Context contextCreateContextAsUser;
        if (Intrinsics.areEqual(this.context.getUser(), userHandle)) {
            contextCreateContextAsUser = this.context;
        } else {
            contextCreateContextAsUser = this.context.createContextAsUser(userHandle, 0);
            contextCreateContextAsUser.getClass();
        }
        Object systemService = contextCreateContextAsUser.getSystemService((Class<Object>) this.serviceType);
        if (systemService != null) {
            return systemService;
        }
        throw new IllegalArgumentException("Required value was null.");
    }
}
