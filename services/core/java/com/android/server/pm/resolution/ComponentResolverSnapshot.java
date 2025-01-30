package com.android.server.pm.resolution;

import android.util.ArrayMap;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.UserNeedsBadgingCache;
import com.android.server.pm.resolution.ComponentResolver;

/* loaded from: classes3.dex */
public class ComponentResolverSnapshot extends ComponentResolverBase {
    public ComponentResolverSnapshot(ComponentResolver componentResolver, UserNeedsBadgingCache userNeedsBadgingCache) {
        super(UserManagerService.getInstance());
        this.mActivities = new ComponentResolver.ActivityIntentResolver(componentResolver.mActivities, this.mUserManager, userNeedsBadgingCache);
        this.mProviders = new ComponentResolver.ProviderIntentResolver(componentResolver.mProviders, this.mUserManager);
        this.mReceivers = new ComponentResolver.ReceiverIntentResolver(componentResolver.mReceivers, this.mUserManager, userNeedsBadgingCache);
        this.mServices = new ComponentResolver.ServiceIntentResolver(componentResolver.mServices, this.mUserManager);
        this.mProvidersByAuthority = new ArrayMap(componentResolver.mProvidersByAuthority);
    }
}
