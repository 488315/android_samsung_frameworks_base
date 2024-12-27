package com.android.server.vr;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;

import com.android.server.utils.ManagedApplicationService;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class EnabledComponentsObserver {
    public final Context mContext;
    public final Set mEnabledComponentListeners;
    public final Object mLock;
    public final String mServiceName;
    public final String mServicePermission;
    public final String mSettingName;
    public final SparseArray mInstalledSet = new SparseArray();
    public final SparseArray mEnabledSet = new SparseArray();

    public EnabledComponentsObserver(Context context, Object obj, Collection collection) {
        ArraySet arraySet = new ArraySet();
        this.mEnabledComponentListeners = arraySet;
        this.mLock = obj;
        this.mContext = context;
        this.mSettingName = "enabled_vr_listeners";
        this.mServiceName = "android.service.vr.VrListenerService";
        this.mServicePermission = "android.permission.BIND_VR_LISTENER_SERVICE";
        arraySet.addAll(collection);
    }

    public static ArraySet loadComponentNames(
            PackageManager packageManager, int i, String str, String str2) {
        ArraySet arraySet = new ArraySet();
        List queryIntentServicesAsUser =
                packageManager.queryIntentServicesAsUser(new Intent(str), 786564, i);
        if (queryIntentServicesAsUser != null) {
            int size = queryIntentServicesAsUser.size();
            for (int i2 = 0; i2 < size; i2++) {
                ServiceInfo serviceInfo =
                        ((ResolveInfo) queryIntentServicesAsUser.get(i2)).serviceInfo;
                ComponentName componentName =
                        new ComponentName(serviceInfo.packageName, serviceInfo.name);
                if (str2.equals(serviceInfo.permission)) {
                    arraySet.add(componentName);
                } else {
                    Slog.w(
                            "EnabledComponentsObserver",
                            "Skipping service "
                                    + serviceInfo.packageName
                                    + "/"
                                    + serviceInfo.name
                                    + ": it does not require the permission "
                                    + str2);
                }
            }
        }
        return arraySet;
    }

    public final ArraySet getEnabled(int i) {
        synchronized (this.mLock) {
            try {
                ArraySet arraySet = (ArraySet) this.mEnabledSet.get(i);
                if (arraySet != null) {
                    return arraySet;
                }
                return new ArraySet();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int isValid(int i, ComponentName componentName) {
        synchronized (this.mLock) {
            try {
                ArraySet arraySet = (ArraySet) this.mInstalledSet.get(i);
                if (arraySet != null && arraySet.contains(componentName)) {
                    ArraySet arraySet2 = (ArraySet) this.mEnabledSet.get(i);
                    if (arraySet2 != null && arraySet2.contains(componentName)) {
                        return 0;
                    }
                    return -1;
                }
                return -2;
            } finally {
            }
        }
    }

    public final ArraySet loadComponentNamesFromSetting(int i, String str) {
        String stringForUser =
                Settings.Secure.getStringForUser(this.mContext.getContentResolver(), str, i);
        if (TextUtils.isEmpty(stringForUser)) {
            return new ArraySet();
        }
        String[] split = stringForUser.split(":");
        ArraySet arraySet = new ArraySet(split.length);
        for (String str2 : split) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(str2);
            if (unflattenFromString != null) {
                arraySet.add(unflattenFromString);
            }
        }
        return arraySet;
    }

    public final void rebuildAll() {
        ApplicationInfo applicationInfo;
        synchronized (this.mLock) {
            try {
                this.mInstalledSet.clear();
                this.mEnabledSet.clear();
                UserManager userManager = (UserManager) this.mContext.getSystemService("user");
                for (int i :
                        userManager == null
                                ? null
                                : userManager.getEnabledProfileIds(
                                        ActivityManager.getCurrentUser())) {
                    ArraySet loadComponentNames =
                            loadComponentNames(
                                    this.mContext.getPackageManager(),
                                    i,
                                    this.mServiceName,
                                    this.mServicePermission);
                    ArraySet loadComponentNamesFromSetting =
                            loadComponentNamesFromSetting(i, this.mSettingName);
                    loadComponentNamesFromSetting.retainAll(loadComponentNames);
                    this.mInstalledSet.put(i, loadComponentNames);
                    this.mEnabledSet.put(i, loadComponentNamesFromSetting);
                }
            } finally {
            }
        }
        Iterator it = ((ArraySet) this.mEnabledComponentListeners).iterator();
        while (it.hasNext()) {
            VrManagerService vrManagerService = (VrManagerService) it.next();
            synchronized (vrManagerService.mLock) {
                try {
                    ArraySet enabled =
                            vrManagerService.mComponentObserver.getEnabled(
                                    ActivityManager.getCurrentUser());
                    ArraySet arraySet = new ArraySet();
                    Iterator it2 = enabled.iterator();
                    while (it2.hasNext()) {
                        ComponentName componentName = (ComponentName) it2.next();
                        try {
                            applicationInfo =
                                    vrManagerService
                                            .mContext
                                            .getPackageManager()
                                            .getApplicationInfo(
                                                    componentName.getPackageName(), 128);
                        } catch (PackageManager.NameNotFoundException unused) {
                            applicationInfo = null;
                        }
                        if (applicationInfo != null
                                && (applicationInfo.isSystemApp()
                                        || applicationInfo.isUpdatedSystemApp())) {
                            arraySet.add(componentName.getPackageName());
                        }
                    }
                    vrManagerService.mNotifAccessManager.update(arraySet);
                    if (vrManagerService.mVrModeAllowed) {
                        vrManagerService.consumeAndApplyPendingStateLocked(false);
                        ManagedApplicationService managedApplicationService =
                                vrManagerService.mCurrentVrService;
                        if (managedApplicationService != null) {
                            vrManagerService.updateCurrentVrServiceLocked(
                                    vrManagerService.mVrModeEnabled,
                                    vrManagerService.mRunning2dInVr,
                                    managedApplicationService.mComponent,
                                    managedApplicationService.mUserId,
                                    vrManagerService.mVrAppProcessId,
                                    vrManagerService.mCurrentVrModeComponent);
                        }
                    }
                } finally {
                }
            }
        }
    }
}
