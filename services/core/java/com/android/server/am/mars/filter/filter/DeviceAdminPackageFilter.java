package com.android.server.am.mars.filter.filter;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.ArrayMap;

import com.android.server.am.mars.filter.IFilter;

import com.samsung.android.knox.SemPersonaManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DeviceAdminPackageFilter implements IFilter {
    public ArrayMap mActiveAdmins;
    public Context mContext;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class DeviceAdminPackageFilterHolder {
        public static final DeviceAdminPackageFilter INSTANCE;

        static {
            DeviceAdminPackageFilter deviceAdminPackageFilter = new DeviceAdminPackageFilter();
            deviceAdminPackageFilter.mContext = null;
            deviceAdminPackageFilter.mActiveAdmins = new ArrayMap();
            INSTANCE = deviceAdminPackageFilter;
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {}

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        synchronized (this.mActiveAdmins) {
            try {
                ArrayList arrayList = (ArrayList) this.mActiveAdmins.get(Integer.valueOf(i));
                return (arrayList == null || !arrayList.contains(str)) ? 0 : 9;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void getActiveAdminsPackages(int i) {
        synchronized (this.mActiveAdmins) {
            try {
                ArrayList arrayList = (ArrayList) this.mActiveAdmins.get(Integer.valueOf(i));
                List<ComponentName> activeAdmins =
                        ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
                                .getActiveAdmins();
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                if (activeAdmins != null) {
                    Iterator<ComponentName> it = activeAdmins.iterator();
                    while (it.hasNext()) {
                        String packageName = it.next().getPackageName();
                        if (!arrayList.contains(packageName)) {
                            arrayList.add(packageName);
                            this.mActiveAdmins.put(Integer.valueOf(i), arrayList);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        this.mContext = context;
        getActiveAdminsPackages(context.getUserId());
        SemPersonaManager semPersonaManager =
                (SemPersonaManager) this.mContext.getSystemService("persona");
        if (semPersonaManager != null) {
            int knoxId = semPersonaManager.getKnoxId(2, true);
            if (this.mContext.getUserId() != 0 || knoxId < 150 || knoxId > 160) {
                return;
            }
            getActiveAdminsPackages(knoxId);
        }
    }

    public final void onDeviceAdminEnabled(int i, String str) {
        synchronized (this.mActiveAdmins) {
            try {
                ArrayList arrayList = (ArrayList) this.mActiveAdmins.get(Integer.valueOf(i));
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                if (!arrayList.contains(str)) {
                    arrayList.add(str);
                    this.mActiveAdmins.put(Integer.valueOf(i), arrayList);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
