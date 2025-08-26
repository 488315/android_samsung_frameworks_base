package android.content.pm;

import android.Manifest;
import android.app.AppGlobals;
import android.content.Intent;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArraySet;
import android.view.inputmethod.InputMethod;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class AppsQueryHelper {
    public static int GET_APPS_WITH_INTERACT_ACROSS_USERS_PERM = 2;
    public static int GET_IMES = 4;
    public static int GET_NON_LAUNCHABLE_APPS = 1;
    public static int GET_REQUIRED_FOR_SYSTEM_USER = 8;
    private List<ApplicationInfo> mAllApps;
    private final IPackageManager mPackageManager;

    public AppsQueryHelper(IPackageManager iPackageManager) {
        this.mPackageManager = iPackageManager;
    }

    public AppsQueryHelper() {
        this(AppGlobals.getPackageManager());
    }

    public List<String> queryApps(int i, boolean z, UserHandle userHandle) {
        int i2 = 0;
        boolean z2 = (GET_NON_LAUNCHABLE_APPS & i) > 0;
        boolean z3 = (GET_APPS_WITH_INTERACT_ACROSS_USERS_PERM & i) > 0;
        boolean z4 = (GET_IMES & i) > 0;
        boolean z5 = (GET_REQUIRED_FOR_SYSTEM_USER & i) > 0;
        if (this.mAllApps == null) {
            this.mAllApps = getAllApps(userHandle.getIdentifier());
        }
        ArrayList arrayList = new ArrayList();
        if (i == 0) {
            int size = this.mAllApps.size();
            while (i2 < size) {
                ApplicationInfo applicationInfo = this.mAllApps.get(i2);
                if (!z || applicationInfo.isSystemApp()) {
                    arrayList.add(applicationInfo.packageName);
                }
                i2++;
            }
        } else {
            if (z2) {
                List<ResolveInfo> listQueryIntentActivitiesAsUser = queryIntentActivitiesAsUser(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER), userHandle.getIdentifier());
                ArraySet arraySet = new ArraySet();
                int size2 = listQueryIntentActivitiesAsUser.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    arraySet.add(listQueryIntentActivitiesAsUser.get(i3).activityInfo.packageName);
                }
                int size3 = this.mAllApps.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    ApplicationInfo applicationInfo2 = this.mAllApps.get(i4);
                    if (!z || applicationInfo2.isSystemApp()) {
                        String str = applicationInfo2.packageName;
                        if (!arraySet.contains(str)) {
                            arrayList.add(str);
                        }
                    }
                }
            }
            if (z3) {
                List<PackageInfo> packagesHoldingPermission = getPackagesHoldingPermission(Manifest.permission.INTERACT_ACROSS_USERS, userHandle.getIdentifier());
                int size4 = packagesHoldingPermission.size();
                for (int i5 = 0; i5 < size4; i5++) {
                    PackageInfo packageInfo = packagesHoldingPermission.get(i5);
                    if ((!z || packageInfo.applicationInfo.isSystemApp()) && !arrayList.contains(packageInfo.packageName)) {
                        arrayList.add(packageInfo.packageName);
                    }
                }
            }
            if (z4) {
                List<ResolveInfo> listQueryIntentServicesAsUser = queryIntentServicesAsUser(new Intent(InputMethod.SERVICE_INTERFACE), userHandle.getIdentifier());
                int size5 = listQueryIntentServicesAsUser.size();
                for (int i6 = 0; i6 < size5; i6++) {
                    ServiceInfo serviceInfo = listQueryIntentServicesAsUser.get(i6).serviceInfo;
                    if ((!z || serviceInfo.applicationInfo.isSystemApp()) && !arrayList.contains(serviceInfo.packageName)) {
                        arrayList.add(serviceInfo.packageName);
                    }
                }
            }
            if (z5) {
                int size6 = this.mAllApps.size();
                while (i2 < size6) {
                    ApplicationInfo applicationInfo3 = this.mAllApps.get(i2);
                    if ((!z || applicationInfo3.isSystemApp()) && applicationInfo3.isRequiredForSystemUser()) {
                        arrayList.add(applicationInfo3.packageName);
                    }
                    i2++;
                }
            }
        }
        return arrayList;
    }

    protected List<ApplicationInfo> getAllApps(int i) {
        try {
            return this.mPackageManager.getInstalledApplications(8704L, i).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected List<ResolveInfo> queryIntentActivitiesAsUser(Intent intent, int i) {
        try {
            return this.mPackageManager.queryIntentActivities(intent, null, 795136L, i).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected List<ResolveInfo> queryIntentServicesAsUser(Intent intent, int i) {
        try {
            return this.mPackageManager.queryIntentServices(intent, null, 819328L, i).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected List<PackageInfo> getPackagesHoldingPermission(String str, int i) {
        try {
            return this.mPackageManager.getPackagesHoldingPermissions(new String[]{str}, 0L, i).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
