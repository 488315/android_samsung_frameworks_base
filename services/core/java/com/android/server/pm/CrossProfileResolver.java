package com.android.server.pm;

import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.Binder;

import com.android.internal.util.CollectionUtils;
import com.android.server.pm.resolution.ComponentResolverApi;

import java.util.List;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class CrossProfileResolver {
    public final ComponentResolverApi mComponentResolver;
    public final UserManagerService mUserManager;

    public CrossProfileResolver(
            ComponentResolverApi componentResolverApi, UserManagerService userManagerService) {
        this.mComponentResolver = componentResolverApi;
        this.mUserManager = userManagerService;
    }

    public static void filterIfNotSystemUser(int i, List list) {
        if (i == 0) {
            return;
        }
        for (int size = CollectionUtils.size(list) - 1; size >= 0; size--) {
            if ((((CrossProfileDomainInfo) list.get(size)).mResolveInfo.activityInfo.flags
                            & 536870912)
                    != 0) {
                list.remove(size);
            }
        }
    }

    public abstract List filterResolveInfoWithDomainPreferredActivity(int i, List list);

    public final boolean isUserEnabled(int i) {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UserInfo userInfo = this.mUserManager.getUserInfo(i);
            if (userInfo != null) {
                if (userInfo.isEnabled()) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public abstract List resolveIntent(
            Computer computer,
            Intent intent,
            String str,
            int i,
            int i2,
            long j,
            String str2,
            List list,
            boolean z,
            Function function);
}
