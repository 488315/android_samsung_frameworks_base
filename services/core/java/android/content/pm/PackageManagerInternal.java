package android.content.pm;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import com.android.server.pm.LauncherAppsService;
import com.android.server.pm.PackageList;
import com.android.server.pm.permission.LegacyPermissionSettings;
import com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda10;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class PackageManagerInternal {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface PackageListObserver {
        void onPackageAdded(String str, int i);

        default void onPackageChanged(int i, String str) {
        }

        default void onPackageRemoved(String str, int i) {
        }
    }

    public abstract boolean canAccessComponent(int i, int i2, ComponentName componentName);

    public abstract boolean canQueryPackage(int i, String str);

    public abstract boolean filterAppAccess(int i, int i2, String str, boolean z);

    public abstract void forEachInstalledPackage(int i, Consumer consumer);

    public abstract void forEachPackage(Consumer consumer);

    public abstract void forEachPackageSetting(PermissionManagerServiceImpl$$ExternalSyntheticLambda10 permissionManagerServiceImpl$$ExternalSyntheticLambda10);

    public abstract void forEachPackageState(Consumer consumer);

    public abstract ApplicationInfo getApplicationInfo(int i, int i2, long j, String str);

    public abstract IncrementalStatesInfo getIncrementalStatesInfo(int i, int i2, String str);

    public abstract List getInstalledApplications(int i, int i2, long j);

    public abstract String getInstantAppPackageName(int i);

    public abstract String[] getKnownPackageNames(int i, int i2);

    public abstract LegacyPermissionSettings getLegacyPermissions();

    public abstract String getNameForUid(int i);

    public abstract AndroidPackage getPackage(int i);

    public abstract AndroidPackage getPackage(String str);

    public abstract PackageInfo getPackageInfo(int i, int i2, long j, String str);

    public abstract PackageList getPackageList(PackageListObserver packageListObserver);

    public abstract PackageStateInternal getPackageStateInternal(String str);

    public abstract int getPackageUid(String str, long j, int i);

    public abstract String[] getSharedUserPackagesForPackage(int i, String str);

    public abstract SuspendDialogInfo getSuspendedDialogInfo(String str, UserPackage userPackage, int i);

    public abstract Bundle getSuspendedPackageLauncherExtras(int i, String str);

    public abstract ComponentName getSystemUiServiceComponent();

    public abstract int getUidTargetSdkVersion(int i);

    public abstract void grantImplicitAccess(int i, Intent intent, int i2, int i3, boolean z, boolean z2);

    public abstract boolean hasInstantApplicationMetadata(int i, String str);

    public abstract boolean hasSignatureCapability(int i, int i2);

    public abstract boolean isInstantApp(String str, int i);

    public abstract boolean isPackageEphemeral(int i, String str);

    public abstract boolean isPackageFrozen(int i, int i2, String str);

    public abstract boolean isPackageSuspended(int i, String str);

    public abstract boolean isPermissionsReviewRequired(int i, String str);

    public abstract boolean isPlatformSigned(String str);

    public abstract boolean isSameApp(int i, int i2, long j, String str);

    public abstract void notifyComponentUsed(int i, String str, String str2, String str3);

    public abstract void onPackageProcessKilledForUninstall(String str);

    public abstract List queryIntentActivities(Intent intent, String str, long j, int i, int i2);

    public abstract void reconcileAppsData(int i, int i2, boolean z);

    public abstract boolean registerInstalledLoadingProgressCallback(String str, LauncherAppsService.LauncherAppsImpl.PackageLoadingProgressCallback packageLoadingProgressCallback, int i);

    public abstract void removeIsolatedUid(int i);

    public abstract void setEnabledOverlayPackages(int i, ArrayMap arrayMap, Set set, Set set2);

    public abstract void setIntegrityVerificationResult(int i, int i2);

    public abstract String[] setPackagesSuspendedByAdmin(int i, boolean z, String[] strArr);

    public abstract void shutdown();

    public abstract boolean wasPackageEverLaunched(int i, String str);

    public abstract boolean wasPackageStopped(int i, String str);

    public abstract void writePermissionSettings(int[] iArr, boolean z);

    public abstract void writeSettings(boolean z);
}
