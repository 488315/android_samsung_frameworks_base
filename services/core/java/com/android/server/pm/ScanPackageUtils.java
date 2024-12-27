package com.android.server.pm;

import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.jar.StrictJarFile;

import com.android.internal.pm.parsing.pkg.AndroidPackageHidden;
import com.android.internal.pm.parsing.pkg.ParsedPackage;
import com.android.internal.pm.pkg.component.ParsedMainComponent;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.pm.parsing.library.PackageBackwardCompatibility;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageUserStateImpl;
import com.android.server.utils.WatchedArraySet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class ScanPackageUtils {
    public static boolean apkHasCode(String str) {
        StrictJarFile strictJarFile;
        StrictJarFile strictJarFile2 = null;
        try {
            strictJarFile = new StrictJarFile(str, false, false);
        } catch (IOException unused) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            boolean z = strictJarFile.findEntry("classes.dex") != null;
            try {
                strictJarFile.close();
            } catch (IOException unused2) {
            }
            return z;
        } catch (IOException unused3) {
            strictJarFile2 = strictJarFile;
            if (strictJarFile2 != null) {
                try {
                    strictJarFile2.close();
                } catch (IOException unused4) {
                }
            }
            return false;
        } catch (Throwable th2) {
            th = th2;
            strictJarFile2 = strictJarFile;
            if (strictJarFile2 != null) {
                try {
                    strictJarFile2.close();
                } catch (IOException unused5) {
                }
            }
            throw th;
        }
    }

    public static List applyAdjustedAbiToSharedUser(
            SharedUserSetting sharedUserSetting, ParsedPackage parsedPackage, String str) {
        if (parsedPackage != null) {
            parsedPackage.setPrimaryCpuAbi(str);
        }
        WatchedArraySet watchedArraySet = sharedUserSetting.mPackages;
        ArrayList arrayList = null;
        for (int i = 0; i < watchedArraySet.mStorage.size(); i++) {
            PackageSetting packageSetting = (PackageSetting) watchedArraySet.mStorage.valueAt(i);
            if ((parsedPackage == null
                            || !parsedPackage.getPackageName().equals(packageSetting.mName))
                    && packageSetting.mPrimaryCpuAbi == null) {
                packageSetting.mPrimaryCpuAbi = str;
                packageSetting.onChanged$2();
                packageSetting.onChanged$2();
                AndroidPackageHidden androidPackageHidden = packageSetting.pkg;
                if (androidPackageHidden != null
                        && !TextUtils.equals(str, androidPackageHidden.getPrimaryCpuAbi())) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(packageSetting.mPathString);
                }
            }
        }
        return arrayList;
    }

    public static void applyPolicy(
            ParsedPackage parsedPackage, int i, AndroidPackage androidPackage, boolean z) {
        boolean z2;
        boolean z3 = false;
        if ((65536 & i) != 0) {
            parsedPackage.setSystem(true);
            if (parsedPackage.isDirectBootAware()) {
                parsedPackage.setAllComponentsDirectBootAware(true);
            }
            File[] compressedFiles =
                    PackageManagerServiceUtils.getCompressedFiles(parsedPackage.getPath());
            if (compressedFiles != null && compressedFiles.length > 0) {
                parsedPackage.setStub(true);
            }
            z2 = true;
        } else {
            parsedPackage
                    .clearProtectedBroadcasts()
                    .setCoreApp(false)
                    .setPersistent(false)
                    .setDefaultToDeviceProtectedStorage(false)
                    .setDirectBootAware(false)
                    .capPermissionPriorities();
            z2 = z;
        }
        int i2 = 131072 & i;
        if (i2 == 0) {
            parsedPackage.markNotActivitiesAsNotExportedIfSingleUser();
        }
        parsedPackage.setApex((67108864 & i) != 0);
        parsedPackage
                .setPrivileged(i2 != 0)
                .setOem((262144 & i) != 0)
                .setVendor((524288 & i) != 0)
                .setProduct((1048576 & i) != 0)
                .setSystemExt((2097152 & i) != 0)
                .setOdm((i & 4194304) != 0);
        if ("android".equals(parsedPackage.getPackageName())
                || (androidPackage != null
                        && PackageManagerServiceUtils.compareSignatures(
                                        androidPackage.getSigningDetails(),
                                        parsedPackage.getSigningDetails())
                                == 0)) {
            z3 = true;
        }
        parsedPackage.setSignedWithPlatformKey(z3);
        if (!z2) {
            parsedPackage.clearOriginalPackages().clearAdoptPermissions();
        }
        PackageBackwardCompatibility.modifySharedLibraries(parsedPackage, z2, z);
    }

    public static void assertPackageProcesses(
            AndroidPackage androidPackage, List list, Map map, String str) {
        if (list == null) {
            return;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            ParsedMainComponent parsedMainComponent = (ParsedMainComponent) list.get(size);
            if (!map.containsKey(parsedMainComponent.getProcessName())) {
                StringBuilder m =
                        DumpUtils$$ExternalSyntheticOutline0.m("Can't install because ", str, " ");
                m.append(parsedMainComponent.getClassName());
                m.append("'s process attribute ");
                m.append(parsedMainComponent.getProcessName());
                m.append(" (in package ");
                m.append(androidPackage.getPackageName());
                m.append(") is not included in the <processes> list");
                throw new PackageManagerException(-122, m.toString());
            }
        }
    }

    public static void assertProcessesAreValid(AndroidPackage androidPackage) {
        Map processes = androidPackage.getProcesses();
        if (processes.isEmpty()) {
            return;
        }
        if (processes.containsKey(androidPackage.getProcessName())) {
            assertPackageProcesses(
                    androidPackage, androidPackage.getActivities(), processes, "activity");
            assertPackageProcesses(
                    androidPackage, androidPackage.getServices(), processes, "service");
            assertPackageProcesses(
                    androidPackage, androidPackage.getReceivers(), processes, "receiver");
            assertPackageProcesses(
                    androidPackage, androidPackage.getProviders(), processes, "provider");
            return;
        }
        throw new PackageManagerException(
                -122,
                "Can't install because application tag's process attribute "
                        + androidPackage.getProcessName()
                        + " (in package "
                        + androidPackage.getPackageName()
                        + ") is not included in the <processes> list");
    }

    public static void assertStaticSharedLibraryIsValid(AndroidPackage androidPackage, int i) {
        if (androidPackage.getTargetSdkVersion() < 26) {
            throw PackageManagerException.ofInternalError(
                    -22, "Packages declaring static-shared libs must target O SDK or higher");
        }
        if ((i & 8192) != 0) {
            throw PackageManagerException.ofInternalError(
                    -23, "Packages declaring static-shared libs cannot be instant apps");
        }
        if (!ArrayUtils.isEmpty(androidPackage.getOriginalPackages())) {
            throw PackageManagerException.ofInternalError(
                    -24, "Packages declaring static-shared libs cannot be renamed");
        }
        if (!ArrayUtils.isEmpty(androidPackage.getLibraryNames())) {
            throw PackageManagerException.ofInternalError(
                    -25, "Packages declaring static-shared libs cannot declare dynamic libs");
        }
        if (androidPackage.getSharedUserId() != null) {
            throw PackageManagerException.ofInternalError(
                    -26, "Packages declaring static-shared libs cannot declare shared users");
        }
        if (!androidPackage.getActivities().isEmpty()) {
            throw PackageManagerException.ofInternalError(
                    -27, "Static shared libs cannot declare activities");
        }
        if (!androidPackage.getServices().isEmpty()) {
            throw PackageManagerException.ofInternalError(
                    -28, "Static shared libs cannot declare services");
        }
        if (!androidPackage.getProviders().isEmpty()) {
            throw PackageManagerException.ofInternalError(
                    -29, "Static shared libs cannot declare content providers");
        }
        if (!androidPackage.getReceivers().isEmpty()) {
            throw PackageManagerException.ofInternalError(
                    -30, "Static shared libs cannot declare broadcast receivers");
        }
        if (!androidPackage.getPermissionGroups().isEmpty()) {
            throw PackageManagerException.ofInternalError(
                    -31, "Static shared libs cannot declare permission groups");
        }
        if (!androidPackage.getAttributions().isEmpty()) {
            throw PackageManagerException.ofInternalError(
                    -32, "Static shared libs cannot declare features");
        }
        if (!androidPackage.getPermissions().isEmpty()) {
            throw PackageManagerException.ofInternalError(
                    -33, "Static shared libs cannot declare permissions");
        }
        if (!androidPackage.getProtectedBroadcasts().isEmpty()) {
            throw PackageManagerException.ofInternalError(
                    -34, "Static shared libs cannot declare protected broadcasts");
        }
        if (androidPackage.getOverlayTarget() != null) {
            throw PackageManagerException.ofInternalError(
                    -35, "Static shared libs cannot be overlay targets");
        }
    }

    public static int getVendorPartitionVersion() {
        String str = SystemProperties.get("ro.vndk.version");
        if (str.isEmpty()) {
            return 28;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return ArrayUtils.contains(Build.VERSION.ACTIVE_CODENAMES, str) ? 10000 : 28;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:131:0x09d5  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x09eb  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0b8d  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0bd3  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0bec  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0c41  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0c56  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x0ca1  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x0cda  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0d11  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0d65  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x0d06  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0ccf  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x0bff  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x0bda  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x0b0b  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x09d7  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x08d1  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x08b1  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x0662  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x0654  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x064c  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:386:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:389:0x038a  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:421:0x041e  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:495:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:496:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:497:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0648  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0652  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x08af  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x08b7  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0905  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.pm.ScanResult scanPackageOnlyLI(
            com.android.server.pm.ScanRequest r81,
            com.android.server.pm.PackageManagerServiceInjector r82,
            boolean r83,
            long r84)
            throws com.android.server.pm.PackageManagerException {
        /*
            Method dump skipped, instructions count: 3449
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.ScanPackageUtils.scanPackageOnlyLI(com.android.server.pm.ScanRequest,"
                    + " com.android.server.pm.PackageManagerServiceInjector, boolean,"
                    + " long):com.android.server.pm.ScanResult");
    }

    public static void setInstantAppForUser(
            PackageManagerServiceInjector packageManagerServiceInjector,
            PackageSetting packageSetting,
            int i,
            boolean z,
            boolean z2) {
        if (z || z2) {
            if (i != -1) {
                if (z && !packageSetting.getInstantApp(i)) {
                    PackageUserStateImpl modifyUserState = packageSetting.modifyUserState(i);
                    modifyUserState.setBoolean$1(16, true);
                    modifyUserState.onChanged$4();
                    packageSetting.onChanged$2();
                    return;
                }
                if (z2 && packageSetting.getInstantApp(i)) {
                    PackageUserStateImpl modifyUserState2 = packageSetting.modifyUserState(i);
                    modifyUserState2.setBoolean$1(16, false);
                    modifyUserState2.onChanged$4();
                    packageSetting.onChanged$2();
                    return;
                }
                return;
            }
            for (int i2 : UserManagerService.this.getUserIds()) {
                if (z && !packageSetting.getInstantApp(i2)) {
                    PackageUserStateImpl modifyUserState3 = packageSetting.modifyUserState(i2);
                    modifyUserState3.setBoolean$1(16, true);
                    modifyUserState3.onChanged$4();
                    packageSetting.onChanged$2();
                } else if (z2 && packageSetting.getInstantApp(i2)) {
                    PackageUserStateImpl modifyUserState4 = packageSetting.modifyUserState(i2);
                    modifyUserState4.setBoolean$1(16, false);
                    modifyUserState4.onChanged$4();
                    packageSetting.onChanged$2();
                }
            }
        }
    }
}
