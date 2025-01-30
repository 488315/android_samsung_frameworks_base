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
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.util.Log;
import com.android.internal.appwidget.IAppWidgetService;
import com.android.internal.content.PackageMonitor;
import com.android.internal.util.FunctionalUtils;
import com.android.server.enterprise.adapter.IPackageManagerAdapter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class PackageManagerAdapter implements IPackageManagerAdapter {
    public static Context mContext;
    public static IPackageManager mIPackageManager;
    public static PackageManagerAdapter mInstance;

    public static String getValidStr(String str) {
        if (str == null) {
            return null;
        }
        try {
            String trim = str.trim();
            if (trim.length() > 0) {
                return trim;
            }
            return null;
        } catch (Exception e) {
            Log.w("PackageManagerAdapter", e.getMessage());
            return null;
        }
    }

    public static synchronized PackageManagerAdapter getInstance(Context context) {
        PackageManagerAdapter packageManagerAdapter;
        synchronized (PackageManagerAdapter.class) {
            if (mInstance == null) {
                mContext = context;
                mInstance = new PackageManagerAdapter();
                mIPackageManager = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
            }
            packageManagerAdapter = mInstance;
        }
        return packageManagerAdapter;
    }

    public class ClearCacheObserver extends IPackageDataObserver.Stub {
        public boolean finished;
        public String packageName;
        public boolean success;

        public void onRemoveCompleted(String str, boolean z) {
            synchronized (this) {
                this.finished = true;
                this.packageName = str;
                this.success = z;
                notifyAll();
            }
        }
    }

    public boolean clearUserData(String str, int i) {
        boolean z;
        String validStr = getValidStr(str);
        if (validStr == null) {
            return false;
        }
        ClearUserDataObserver clearUserDataObserver = new ClearUserDataObserver();
        try {
            if (!ActivityManagerNative.getDefault().clearApplicationUserData(validStr, false, clearUserDataObserver, i)) {
                Log.d("PackageManagerAdapter", "Couldn't clear application user data for package: " + validStr);
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
                if (true != clearUserDataObserver.success || !clearUserDataObserver.packageName.equalsIgnoreCase(validStr)) {
                    z = false;
                }
            }
            return z;
        } catch (Exception e) {
            Log.w("PackageManagerAdapter", e.getMessage());
            return false;
        }
    }

    public class ClearUserDataObserver extends IPackageDataObserver.Stub {
        public boolean finished;
        public String packageName;
        public boolean success;

        public ClearUserDataObserver() {
        }

        public void onRemoveCompleted(String str, boolean z) {
            synchronized (this) {
                this.finished = true;
                this.packageName = str;
                this.success = z;
                notifyAll();
            }
        }
    }

    public boolean deletePackageAsUser(String str, int i, int i2) {
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

    public boolean deletePackage(String str, int i) {
        IPackageDeleteObserver packageDeleteObserver = new PackageDeleteObserver();
        mContext.getPackageManager().deletePackage(str, packageDeleteObserver, i);
        synchronized (packageDeleteObserver) {
            while (!packageDeleteObserver.finished) {
                try {
                    packageDeleteObserver.wait();
                } catch (InterruptedException unused) {
                }
            }
        }
        return packageDeleteObserver.result;
    }

    public class PackageDeleteObserver extends IPackageDeleteObserver.Stub {
        public boolean finished;
        public boolean result;

        public PackageDeleteObserver() {
        }

        public void packageDeleted(String str, int i) {
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

    public List getInstalledApplications(int i, int i2) {
        try {
            return mIPackageManager.getInstalledApplications(i, i2).getList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ApplicationInfo getApplicationInfo(String str, int i, int i2) {
        try {
            return mIPackageManager.getApplicationInfo(str, i, i2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getApplicationEnabledSetting(String str, int i) {
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

    public boolean setApplicationEnabledSetting(String str, int i, int i2, int i3, String str2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            mIPackageManager.setApplicationEnabledSetting(str, i, i2, i3, str2);
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

    public int getComponentEnabledSetting(ComponentName componentName, int i) {
        try {
            return mIPackageManager.getComponentEnabledSetting(componentName, i);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean setComponentEnabledSetting(ComponentName componentName, int i, int i2, int i3, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            mIPackageManager.setComponentEnabledSetting(componentName, i, i2, i3, str);
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

    public PackageInfo getPackageInfo(String str, int i, int i2) {
        try {
            return mIPackageManager.getPackageInfo(str, i, i2);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List getInstalledWidgetProviders(int i) {
        UserManager userManager;
        List<UserInfo> enabledProfiles;
        ArrayList arrayList = new ArrayList();
        IAppWidgetService asInterface = IAppWidgetService.Stub.asInterface(ServiceManager.getService("appwidget"));
        if (asInterface == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        HashSet hashSet = new HashSet();
        List allProvidersForProfile = asInterface.getAllProvidersForProfile(769, i, false);
        if (allProvidersForProfile != null) {
            hashSet.addAll(allProvidersForProfile);
        }
        if (i == 0 && (userManager = (UserManager) mContext.getSystemService("user")) != null && (enabledProfiles = userManager.getEnabledProfiles(i)) != null && !enabledProfiles.isEmpty()) {
            for (UserInfo userInfo : enabledProfiles) {
                int i2 = userInfo.id;
                if (i2 != i) {
                    if (!isUserRunningAndUnlocked(i2)) {
                        Log.v("PackageManagerAdapter", String.format("cannot getAllProvidersForProfile for user %s because it is in locked state", Integer.valueOf(userInfo.id)));
                    } else {
                        List allProvidersForProfile2 = asInterface.getAllProvidersForProfile(769, userInfo.id, true);
                        if (allProvidersForProfile2 != null) {
                            hashSet.addAll(allProvidersForProfile2);
                        }
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

    public Map getAllWidgets(String str, int i) {
        IAppWidgetService asInterface = IAppWidgetService.Stub.asInterface(ServiceManager.getService("appwidget"));
        if (asInterface == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Map allWidgets = asInterface.getAllWidgets(str, i);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return allWidgets;
    }

    public int installExistingPackageAsUserForMDM(final String str, final int i) {
        return ((Integer) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.adapterlayer.PackageManagerAdapter$$ExternalSyntheticLambda0
            public final Object getOrThrow() {
                Integer lambda$installExistingPackageAsUserForMDM$0;
                lambda$installExistingPackageAsUserForMDM$0 = PackageManagerAdapter.lambda$installExistingPackageAsUserForMDM$0(str, i);
                return lambda$installExistingPackageAsUserForMDM$0;
            }
        })).intValue();
    }

    public static /* synthetic */ Integer lambda$installExistingPackageAsUserForMDM$0(String str, int i) {
        return Integer.valueOf(mIPackageManager.installExistingPackageAsUser(str, i, 4194304, 1, (List) null));
    }

    public abstract class MyPackageMonitor extends PackageMonitor {
        public void onPackageUpdateFinished(String str, int i) {
        }

        public void onSomePackagesChanged() {
        }

        public int isPackageDisappearing(String str) {
            return super.isPackageDisappearing(str);
        }

        public boolean isPackageModified(String str) {
            return super.isPackageModified(str);
        }
    }

    public static boolean getUserRestrictions(int i, String str, boolean z) {
        boolean z2;
        try {
            z2 = ((UserManager) mContext.getSystemService("user")).getUserRestrictions(new UserHandle(i)).getBoolean(str, z);
        } catch (Exception e) {
            Log.d("PackageManagerAdapter", "getUserRestrictions() failed. (" + i + ", " + str + ")", e);
            z2 = z;
        }
        if (z2 != z) {
            Log.d("PackageManagerAdapter", "getUserRestrictions(" + i + ", " + str + ") = " + z2);
        }
        return z2;
    }

    public final boolean isUserRunningAndUnlocked(int i) {
        return ((UserManager) mContext.getSystemService("user")).isUserRunning(i) && StorageManager.isUserKeyUnlocked(i);
    }

    public static List getUsers(boolean z) {
        return ((UserManager) mContext.getSystemService("user")).getUsers(z);
    }

    public boolean isApplicationInstalled(String str, int i) {
        String validStr = getValidStr(str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return getApplicationInfo(validStr, 128, i) != null;
        } catch (Exception unused) {
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
