package com.android.server.pm;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import com.android.internal.config.appcloning.AppCloningDeviceConfigHelper;
import com.android.server.pm.resolution.ComponentResolverApi;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class NoFilteringResolver extends CrossProfileResolver {
    @Override // com.android.server.pm.CrossProfileResolver
    public List filterResolveInfoWithDomainPreferredActivity(Intent intent, List list, long j, int i, int i2, int i3) {
        return list;
    }

    public static boolean isIntentRedirectionAllowed(Context context, AppCloningDeviceConfigHelper appCloningDeviceConfigHelper, boolean z, long j) {
        return isAppCloningBuildingBlocksEnabled(context, appCloningDeviceConfigHelper) && (z || ((536870912 & j) != 0 && hasPermission(context, "android.permission.QUERY_CLONED_APPS")));
    }

    public NoFilteringResolver(ComponentResolverApi componentResolverApi, UserManagerService userManagerService) {
        super(componentResolverApi, userManagerService);
    }

    @Override // com.android.server.pm.CrossProfileResolver
    public List resolveIntent(Computer computer, Intent intent, String str, int i, int i2, long j, String str2, List list, boolean z, Function function) {
        List queryActivities = this.mComponentResolver.queryActivities(computer, intent, str, j, i2);
        ArrayList arrayList = new ArrayList();
        if (queryActivities != null) {
            for (int i3 = 0; i3 < queryActivities.size(); i3++) {
                arrayList.add(new CrossProfileDomainInfo((ResolveInfo) queryActivities.get(i3), 0, i2));
            }
        }
        return filterIfNotSystemUser(arrayList, i);
    }

    public static boolean hasPermission(Context context, String str) {
        return context.checkCallingOrSelfPermission(str) == 0;
    }

    public static boolean isAppCloningBuildingBlocksEnabled(Context context, AppCloningDeviceConfigHelper appCloningDeviceConfigHelper) {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (context.getResources().getBoolean(R.bool.config_dontPreferApn)) {
                if (appCloningDeviceConfigHelper.getEnableAppCloningBuildingBlocks()) {
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
}
