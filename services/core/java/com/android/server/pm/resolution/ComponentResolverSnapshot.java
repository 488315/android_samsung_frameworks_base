package com.android.server.pm.resolution;

import android.util.ArrayMap;

import com.android.server.pm.UserManagerService;
import com.android.server.pm.UserNeedsBadgingCache;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ComponentResolverSnapshot extends ComponentResolverBase {
    public ComponentResolverSnapshot(
            ComponentResolver componentResolver, UserNeedsBadgingCache userNeedsBadgingCache) {
        super(UserManagerService.getInstance());
        this.mActivities =
                new ComponentResolver.ActivityIntentResolver(
                        componentResolver.mActivities, this.mUserManager, userNeedsBadgingCache);
        this.mProviders =
                new ComponentResolver.ServiceIntentResolver(
                        componentResolver.mProviders, this.mUserManager, (byte) 0);
        this.mReceivers =
                new ComponentResolver.ReceiverIntentResolver(
                        componentResolver.mReceivers, this.mUserManager, userNeedsBadgingCache);
        this.mServices =
                new ComponentResolver.ServiceIntentResolver(
                        componentResolver.mServices, this.mUserManager);
        this.mProvidersByAuthority = new ArrayMap(componentResolver.mProvidersByAuthority);
    }
}
