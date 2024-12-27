package com.android.systemui.user.utils;

import android.content.Context;
import android.os.UserHandle;
import kotlin.jvm.internal.Intrinsics;

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
            Intrinsics.checkNotNull(createContextAsUser);
        }
        Object systemService = createContextAsUser.getSystemService((Class<Object>) this.serviceType);
        if (systemService != null) {
            return systemService;
        }
        throw new IllegalArgumentException("Required value was null.".toString());
    }
}
