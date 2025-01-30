package com.android.server.vr;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.content.PackageMonitor;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.vr.SettingsObserver;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public class EnabledComponentsObserver implements SettingsObserver.SettingChangeListener {
    public static final String TAG = "EnabledComponentsObserver";
    public final Context mContext;
    public final Set mEnabledComponentListeners;
    public final Object mLock;
    public final String mServiceName;
    public final String mServicePermission;
    public final String mSettingName;
    public final SparseArray mInstalledSet = new SparseArray();
    public final SparseArray mEnabledSet = new SparseArray();

    public interface EnabledComponentChangeListener {
        void onEnabledComponentChanged();
    }

    public EnabledComponentsObserver(Context context, String str, String str2, String str3, Object obj, Collection collection) {
        ArraySet arraySet = new ArraySet();
        this.mEnabledComponentListeners = arraySet;
        this.mLock = obj;
        this.mContext = context;
        this.mSettingName = str;
        this.mServiceName = str3;
        this.mServicePermission = str2;
        arraySet.addAll(collection);
    }

    public static EnabledComponentsObserver build(Context context, Handler handler, String str, Looper looper, String str2, String str3, Object obj, Collection collection) {
        SettingsObserver build = SettingsObserver.build(context, handler, str);
        EnabledComponentsObserver enabledComponentsObserver = new EnabledComponentsObserver(context, str, str2, str3, obj, collection);
        new PackageMonitor() { // from class: com.android.server.vr.EnabledComponentsObserver.1
            public void onSomePackagesChanged() {
                EnabledComponentsObserver.this.onPackagesChanged();
            }

            public void onPackageDisappeared(String str4, int i) {
                EnabledComponentsObserver.this.onPackagesChanged();
            }

            public void onPackageModified(String str4) {
                EnabledComponentsObserver.this.onPackagesChanged();
            }

            public boolean onHandleForceStop(Intent intent, String[] strArr, int i, boolean z) {
                EnabledComponentsObserver.this.onPackagesChanged();
                return super.onHandleForceStop(intent, strArr, i, z);
            }
        }.register(context, looper, UserHandle.ALL, true);
        build.addListener(enabledComponentsObserver);
        return enabledComponentsObserver;
    }

    public void onPackagesChanged() {
        rebuildAll();
    }

    @Override // com.android.server.vr.SettingsObserver.SettingChangeListener
    public void onSettingChanged() {
        rebuildAll();
    }

    @Override // com.android.server.vr.SettingsObserver.SettingChangeListener
    public void onSettingRestored(String str, String str2, int i) {
        rebuildAll();
    }

    public void onUsersChanged() {
        rebuildAll();
    }

    public void rebuildAll() {
        synchronized (this.mLock) {
            this.mInstalledSet.clear();
            this.mEnabledSet.clear();
            for (int i : getCurrentProfileIds()) {
                ArraySet loadComponentNamesForUser = loadComponentNamesForUser(i);
                ArraySet loadComponentNamesFromSetting = loadComponentNamesFromSetting(this.mSettingName, i);
                loadComponentNamesFromSetting.retainAll(loadComponentNamesForUser);
                this.mInstalledSet.put(i, loadComponentNamesForUser);
                this.mEnabledSet.put(i, loadComponentNamesFromSetting);
            }
        }
        sendSettingChanged();
    }

    public int isValid(ComponentName componentName, int i) {
        synchronized (this.mLock) {
            ArraySet arraySet = (ArraySet) this.mInstalledSet.get(i);
            if (arraySet != null && arraySet.contains(componentName)) {
                ArraySet arraySet2 = (ArraySet) this.mEnabledSet.get(i);
                if (arraySet2 != null && arraySet2.contains(componentName)) {
                    return 0;
                }
                return -1;
            }
            return -2;
        }
    }

    public ArraySet getInstalled(int i) {
        synchronized (this.mLock) {
            ArraySet arraySet = (ArraySet) this.mInstalledSet.get(i);
            if (arraySet != null) {
                return arraySet;
            }
            return new ArraySet();
        }
    }

    public ArraySet getEnabled(int i) {
        synchronized (this.mLock) {
            ArraySet arraySet = (ArraySet) this.mEnabledSet.get(i);
            if (arraySet != null) {
                return arraySet;
            }
            return new ArraySet();
        }
    }

    public final int[] getCurrentProfileIds() {
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        if (userManager == null) {
            return null;
        }
        return userManager.getEnabledProfileIds(ActivityManager.getCurrentUser());
    }

    public static ArraySet loadComponentNames(PackageManager packageManager, int i, String str, String str2) {
        ArraySet arraySet = new ArraySet();
        List queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(new Intent(str), 786564, i);
        if (queryIntentServicesAsUser != null) {
            int size = queryIntentServicesAsUser.size();
            for (int i2 = 0; i2 < size; i2++) {
                ServiceInfo serviceInfo = ((ResolveInfo) queryIntentServicesAsUser.get(i2)).serviceInfo;
                ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
                if (!str2.equals(serviceInfo.permission)) {
                    Slog.w(TAG, "Skipping service " + serviceInfo.packageName + "/" + serviceInfo.name + ": it does not require the permission " + str2);
                } else {
                    arraySet.add(componentName);
                }
            }
        }
        return arraySet;
    }

    public final ArraySet loadComponentNamesForUser(int i) {
        return loadComponentNames(this.mContext.getPackageManager(), i, this.mServiceName, this.mServicePermission);
    }

    public final ArraySet loadComponentNamesFromSetting(String str, int i) {
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), str, i);
        if (TextUtils.isEmpty(stringForUser)) {
            return new ArraySet();
        }
        String[] split = stringForUser.split(XmlUtils.STRING_ARRAY_SEPARATOR);
        ArraySet arraySet = new ArraySet(split.length);
        for (String str2 : split) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(str2);
            if (unflattenFromString != null) {
                arraySet.add(unflattenFromString);
            }
        }
        return arraySet;
    }

    public final void sendSettingChanged() {
        Iterator it = this.mEnabledComponentListeners.iterator();
        while (it.hasNext()) {
            ((EnabledComponentChangeListener) it.next()).onEnabledComponentChanged();
        }
    }
}
