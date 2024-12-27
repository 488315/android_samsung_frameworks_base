package com.android.systemui.user.utils;

import android.content.Context;
import android.os.UserHandle;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
