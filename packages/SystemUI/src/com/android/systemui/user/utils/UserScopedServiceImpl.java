package com.android.systemui.user.utils;

import android.content.Context;
import android.os.UserHandle;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UserScopedServiceImpl implements UserScopedService {
    public final Context context;
    public final Class serviceType;

    public UserScopedServiceImpl(Context context, Class<Object> cls) {
        this.context = context;
        this.serviceType = cls;
    }

    public final Object forUser(UserHandle userHandle) {
        Context createContextAsUser;
        if (Intrinsics.areEqual(this.context.getUser(), userHandle)) {
            createContextAsUser = this.context;
        } else {
            createContextAsUser = this.context.createContextAsUser(userHandle, 0);
            createContextAsUser.getClass();
        }
        Object systemService = createContextAsUser.getSystemService((Class<Object>) this.serviceType);
        if (systemService != null) {
            return systemService;
        }
        throw new IllegalArgumentException("Required value was null.");
    }
}
