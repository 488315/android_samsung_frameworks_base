package com.android.server.appfunctions;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.UserHandle;

import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ServiceHelperImpl implements ServiceHelper {
    public final Context mContext;

    public ServiceHelperImpl(Context context) {
        Objects.requireNonNull(context);
        this.mContext = context;
    }

    @Override // com.android.server.appfunctions.ServiceHelper
    public final Intent resolveAppFunctionService(String str, UserHandle userHandle) {
        ServiceInfo serviceInfo;
        Intent m =
                ExplicitHealthCheckController$$ExternalSyntheticOutline0.m(
                        "android.app.appfunctions.AppFunctionService", str);
        ResolveInfo resolveService =
                this.mContext
                        .createContextAsUser(userHandle, 0)
                        .getPackageManager()
                        .resolveService(m, 0);
        if (resolveService == null
                || (serviceInfo = resolveService.serviceInfo) == null
                || !"android.permission.BIND_APP_FUNCTION_SERVICE".equals(serviceInfo.permission)) {
            return null;
        }
        m.setComponent(new ComponentName(serviceInfo.packageName, serviceInfo.name));
        return m;
    }
}
