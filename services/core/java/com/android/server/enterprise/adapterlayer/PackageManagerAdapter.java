package com.android.server.enterprise.adapterlayer;

import android.app.ActivityManagerNative;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.util.Log;

import com.android.internal.appwidget.IAppWidgetService;
import com.android.server.enterprise.adapter.IPackageManagerAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class PackageManagerAdapter implements IPackageManagerAdapter {
    public static Context mContext;
    public static IPackageManager mIPackageManager;
    public static PackageManagerAdapter mInstance;

    public final class ClearUserDataObserver extends IPackageDataObserver.Stub {
        public boolean finished;
        public String packageName;
        public boolean success;

        public final void onRemoveCompleted(String str, boolean z) {
            synchronized (this) {
                this.finished = true;
                this.packageName = str;
                this.success = z;
                notifyAll();
            }
        }
    }

    public final class PackageDeleteObserver extends IPackageDeleteObserver.Stub {
        public boolean finished;
        public boolean result;

        public final void packageDeleted(String str, int i) {
            synchronized (this) {
                boolean z = true;
                this.finished = true;
                if (i != 1) {
                    z = false;
                }
                this.result = z;
                notifyAll();
            }
        }
    }

    public static boolean clearUserData(int i, String str) {
        boolean z;
        String str2 = null;
        if (str != null) {
            try {
                String trim = str.trim();
                if (trim.length() > 0) {
                    str2 = trim;
                }
            } catch (Exception e) {
                Log.w("PackageManagerAdapter", e.getMessage());
            }
        }
        if (str2 == null) {
            return false;
        }
        ClearUserDataObserver clearUserDataObserver = new ClearUserDataObserver();
        try {
            if (!ActivityManagerNative.getDefault()
                    .clearApplicationUserData(str2, false, clearUserDataObserver, i)) {
                Log.d(
                        "PackageManagerAdapter",
                        "Couldn't clear application user data for package: ".concat(str2));
                return false;
            }
            synchronized (clearUserDataObserver) {
                while (!clearUserDataObserver.finished) {
                    try {
                        clearUserDataObserver.wait();
                    } catch (InterruptedException unused) {
                    }
                }
                z = true;
                if (true != clearUserDataObserver.success
                        || !clearUserDataObserver.packageName.equalsIgnoreCase(str2)) {
                    z = false;
                }
            }
            return z;
        } catch (Exception e2) {
            Log.w("PackageManagerAdapter", e2.getMessage());
            return false;
        }
    }

    public static boolean deletePackageAsUser(int i, int i2, String str) {
        try {
            PackageDeleteObserver packageDeleteObserver = new PackageDeleteObserver();
            mIPackageManager.deletePackageAsUser(str, -1, packageDeleteObserver, i, i2);
            synchronized (packageDeleteObserver) {
                while (!packageDeleteObserver.finished) {
                    try {
                        packageDeleteObserver.wait();
                    } catch (InterruptedException unused) {
                    }
                }
            }
            return packageDeleteObserver.result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getApplicationEnabledSetting(String str, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return mIPackageManager.getApplicationEnabledSetting(str, i);
            } catch (Exception e) {
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static ApplicationInfo getApplicationInfo(int i, int i2, String str) {
        try {
            return mIPackageManager.getApplicationInfo(str, i, i2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List getInstalledWidgetProviders(int i) {
        UserManager userManager;
        List<UserInfo> enabledProfiles;
        ArrayList arrayList = new ArrayList();
        IAppWidgetService asInterface =
                IAppWidgetService.Stub.asInterface(ServiceManager.getService("appwidget"));
        if (asInterface == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        HashSet hashSet = new HashSet();
        List allProvidersForProfile = asInterface.getAllProvidersForProfile(769, i, false);
        if (allProvidersForProfile != null) {
            hashSet.addAll(allProvidersForProfile);
        }
        if (i == 0
                && (userManager = (UserManager) mContext.getSystemService("user")) != null
                && (enabledProfiles = userManager.getEnabledProfiles(i)) != null
                && !enabledProfiles.isEmpty()) {
            for (UserInfo userInfo : enabledProfiles) {
                int i2 = userInfo.id;
                if (i2 != i) {
                    if (((UserManager) mContext.getSystemService("user")).isUserRunning(i2)
                            && StorageManager.isCeStorageUnlocked(i2)) {
                        List allProvidersForProfile2 =
                                asInterface.getAllProvidersForProfile(769, userInfo.id, true);
                        if (allProvidersForProfile2 != null) {
                            hashSet.addAll(allProvidersForProfile2);
                        }
                    } else {
                        Log.v(
                                "PackageManagerAdapter",
                                "cannot getAllProvidersForProfile for user "
                                        + userInfo.id
                                        + " because it is in locked state");
                    }
                }
            }
        }
        if (hashSet.size() > 0) {
            arrayList.addAll(hashSet);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return arrayList;
    }

    public static synchronized PackageManagerAdapter getInstance(Context context) {
        PackageManagerAdapter packageManagerAdapter;
        synchronized (PackageManagerAdapter.class) {
            try {
                if (mInstance == null) {
                    mContext = context;
                    mInstance = new PackageManagerAdapter();
                    mIPackageManager =
                            IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
                }
                packageManagerAdapter = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return packageManagerAdapter;
    }

    public static PackageInfo getPackageInfo(int i, int i2, String str) {
        try {
            return mIPackageManager.getPackageInfo(str, i, i2);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List getUsers(boolean z) {
        return ((UserManager) mContext.getSystemService("user")).getUsers(z);
    }

    public static boolean isApplicationInstalled(int i, String str) {
        String str2 = null;
        if (str != null) {
            try {
                String trim = str.trim();
                if (trim.length() > 0) {
                    str2 = trim;
                }
            } catch (Exception e) {
                Log.w("PackageManagerAdapter", e.getMessage());
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return getApplicationInfo(128, i, str2) != null;
        } catch (Exception unused) {
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean setApplicationEnabledSetting(int i, int i2, String str, String str2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            mIPackageManager.setApplicationEnabledSetting(str, i, 0, i2, str2);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Exception unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static void setComponentEnabledSetting(
            ComponentName componentName, String str, int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            mIPackageManager.setComponentEnabledSetting(componentName, i, 1, i2, str);
        } catch (Exception unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }
}
