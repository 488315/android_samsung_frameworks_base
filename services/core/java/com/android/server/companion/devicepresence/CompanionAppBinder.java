package com.android.server.companion.devicepresence;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.util.Pair;
import android.util.Slog;

import com.android.internal.infra.PerUser;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.companion.utils.PackageUtils;
import com.android.server.companion.utils.PackageUtils$$ExternalSyntheticLambda0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CompanionAppBinder {
    public final Context mContext;
    public final CompanionServicesRegister mCompanionServicesRegister =
            new CompanionServicesRegister();
    public final Map mBoundCompanionApplications = new HashMap();
    public final Set mScheduledForRebindingCompanionApplications = new HashSet();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CompanionServicesRegister extends PerUser {
        public CompanionServicesRegister() {}

        public final Object create(int i) {
            boolean z;
            Context context = CompanionAppBinder.this.mContext;
            Intent intent = PackageUtils.COMPANION_SERVICE_INTENT;
            PackageManager packageManager = context.getPackageManager();
            List queryIntentServicesAsUser =
                    packageManager.queryIntentServicesAsUser(
                            PackageUtils.COMPANION_SERVICE_INTENT,
                            PackageManager.ResolveInfoFlags.of(0L),
                            i);
            HashMap hashMap = new HashMap(queryIntentServicesAsUser.size());
            Iterator it = queryIntentServicesAsUser.iterator();
            while (it.hasNext()) {
                ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
                if ("android.permission.BIND_COMPANION_DEVICE_SERVICE"
                        .equals(serviceInfo.permission)) {
                    ArrayList arrayList =
                            (ArrayList)
                                    hashMap.computeIfAbsent(
                                            serviceInfo.packageName,
                                            new PackageUtils$$ExternalSyntheticLambda0());
                    ComponentName componentName = serviceInfo.getComponentName();
                    try {
                        z =
                                packageManager
                                        .getPropertyAsUser(
                                                "android.companion.PROPERTY_PRIMARY_COMPANION_DEVICE_SERVICE",
                                                componentName.getPackageName(),
                                                componentName.getClassName(),
                                                i)
                                        .getBoolean();
                    } catch (PackageManager.NameNotFoundException unused) {
                        z = false;
                    }
                    if (z) {
                        arrayList.add(0, componentName);
                    } else {
                        arrayList.add(componentName);
                    }
                } else {
                    Slog.w(
                            "CDM_PackageUtils",
                            "CompanionDeviceService "
                                    + serviceInfo.getComponentName().flattenToShortString()
                                    + " must require"
                                    + " android.permission.BIND_COMPANION_DEVICE_SERVICE");
                }
            }
            return hashMap;
        }

        public final synchronized Map forUser(int i) {
            return (Map) super.forUser(i);
        }
    }

    public CompanionAppBinder(Context context) {
        this.mContext = context;
    }

    public final CompanionServiceConnector getPrimaryServiceConnector(int i, String str) {
        List list;
        synchronized (this.mBoundCompanionApplications) {
            list =
                    (List)
                            ((HashMap) this.mBoundCompanionApplications)
                                    .get(new Pair(Integer.valueOf(i), str));
        }
        if (list != null) {
            return (CompanionServiceConnector) list.get(0);
        }
        return null;
    }

    public final boolean isCompanionApplicationBound(int i, String str) {
        boolean containsKey;
        synchronized (this.mBoundCompanionApplications) {
            containsKey =
                    ((HashMap) this.mBoundCompanionApplications)
                            .containsKey(new Pair(Integer.valueOf(i), str));
        }
        return containsKey;
    }

    public final void unbindCompanionApp(int i, String str) {
        List<CompanionServiceConnector> list;
        Slog.i(
                "CDM_CompanionAppBinder",
                AccountManagerService$$ExternalSyntheticOutline0.m(
                        i, "Unbinding user=[", "], package=[", str, "]..."));
        synchronized (this.mBoundCompanionApplications) {
            list =
                    (List)
                            ((HashMap) this.mBoundCompanionApplications)
                                    .remove(new Pair(Integer.valueOf(i), str));
        }
        synchronized (this.mScheduledForRebindingCompanionApplications) {
            ((HashSet) this.mScheduledForRebindingCompanionApplications)
                    .remove(new Pair(Integer.valueOf(i), str));
        }
        if (list == null) {
            Slog.e("CDM_CompanionAppBinder", "The package is not bound.");
            return;
        }
        for (final CompanionServiceConnector companionServiceConnector : list) {
            companionServiceConnector
                    .getJobHandler()
                    .postDelayed(
                            new Runnable() { // from class:
                                             // com.android.server.companion.devicepresence.CompanionServiceConnector$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CompanionServiceConnector.this.unbind();
                                }
                            },
                            5000L);
        }
    }
}
