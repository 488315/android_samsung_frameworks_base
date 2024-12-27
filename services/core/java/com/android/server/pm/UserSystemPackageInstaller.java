package com.android.server.pm;

import android.R;
import android.content.pm.PackageManagerInternal;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.util.IndentingPrintWriter;
import android.util.Slog;

import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemConfig;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public final class UserSystemPackageInstaller {
    public final UserManagerService mUm;
    public final String[] mUserTypes;
    public final ArrayMap mWhitelistedPackagesForUserTypes;

    public UserSystemPackageInstaller(UserManagerService userManagerService, ArrayMap arrayMap) {
        this.mUm = userManagerService;
        int size = arrayMap.size();
        String[] strArr = new String[size];
        for (int i = 0; i < arrayMap.size(); i++) {
            strArr[i] = (String) arrayMap.keyAt(i);
        }
        Arrays.sort(strArr);
        this.mUserTypes = strArr;
        if (size <= 64) {
            this.mWhitelistedPackagesForUserTypes =
                    determineWhitelistedPackagesForUserTypes(SystemConfig.getInstance());
            return;
        }
        throw new IllegalArgumentException(
                "Device contains "
                        + arrayMap.size()
                        + " user types. However, UserSystemPackageInstaller does not work if there"
                        + " are more than 64 user types.");
    }

    public UserSystemPackageInstaller(
            UserManagerService userManagerService, ArrayMap arrayMap, String[] strArr) {
        this.mUm = userManagerService;
        this.mUserTypes = strArr;
        this.mWhitelistedPackagesForUserTypes = arrayMap;
    }

    public static int getWhitelistMode() {
        int i = SystemProperties.getInt("persist.debug.user.package_whitelist_mode", -1);
        return i != -1
                ? i
                : Resources.getSystem().getInteger(R.integer.device_idle_motion_inactive_to_ms);
    }

    public static String modeToString(int i) {
        return i != -1000
                ? i != -1
                        ? DebugUtils.flagsToString(
                                UserSystemPackageInstaller.class,
                                "USER_TYPE_PACKAGE_WHITELIST_MODE_",
                                i)
                        : "DEVICE_DEFAULT"
                : "NONE";
    }

    public static boolean shouldInstallPackage(
            AndroidPackage androidPackage, ArrayMap arrayMap, Set set, boolean z) {
        String overlayTarget =
                androidPackage.isOverlayIsStatic()
                        ? androidPackage.getOverlayTarget()
                        : androidPackage.getManifestPackageName();
        return (z && !arrayMap.containsKey(overlayTarget))
                || set.contains(overlayTarget)
                || androidPackage.isApex();
    }

    public static void showIssues(
            IndentingPrintWriter indentingPrintWriter, boolean z, List list, String str) {
        int size = list.size();
        if (size == 0) {
            if (z) {
                indentingPrintWriter.print("No ");
                indentingPrintWriter.println(str);
                return;
            }
            return;
        }
        if (z) {
            indentingPrintWriter.print(size);
            indentingPrintWriter.print(' ');
            indentingPrintWriter.println(str);
            indentingPrintWriter.increaseIndent();
        }
        for (int i = 0; i < size; i++) {
            indentingPrintWriter.println((String) list.get(i));
        }
        if (z) {
            indentingPrintWriter.decreaseIndent();
        }
    }

    public ArrayMap determineWhitelistedPackagesForUserTypes(SystemConfig systemConfig) {
        int i = 0;
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        while (true) {
            String[] strArr = this.mUserTypes;
            if (i >= strArr.length) {
                break;
            }
            String str = strArr[i];
            UserManagerService userManagerService = this.mUm;
            UserTypeDetails userTypeDetails =
                    (UserTypeDetails) userManagerService.mUserTypes.get(str);
            if (userTypeDetails != null && (userTypeDetails.mBaseType & 1024) != 0) {
                j |= 1 << i;
            }
            UserTypeDetails userTypeDetails2 =
                    (UserTypeDetails) userManagerService.mUserTypes.get(strArr[i]);
            if (userTypeDetails2 != null && (userTypeDetails2.mBaseType & 2048) != 0) {
                j2 |= 1 << i;
            }
            UserTypeDetails userTypeDetails3 =
                    (UserTypeDetails) userManagerService.mUserTypes.get(strArr[i]);
            if (userTypeDetails3 != null && userTypeDetails3.isProfile()) {
                j3 |= 1 << i;
            }
            i++;
        }
        ArrayMap arrayMap = new ArrayMap(3);
        arrayMap.put("FULL", Long.valueOf(j));
        arrayMap.put("SYSTEM", Long.valueOf(j2));
        arrayMap.put("PROFILE", Long.valueOf(j3));
        ArrayMap arrayMap2 = systemConfig.mPackageToUserTypeWhitelist;
        systemConfig.mPackageToUserTypeWhitelist = new ArrayMap(0);
        ArrayMap arrayMap3 = new ArrayMap(arrayMap2.size() + 1);
        for (int i2 = 0; i2 < arrayMap2.size(); i2++) {
            String intern = ((String) arrayMap2.keyAt(i2)).intern();
            long typesBitSet = getTypesBitSet((Iterable) arrayMap2.valueAt(i2), arrayMap);
            if (typesBitSet != 0) {
                arrayMap3.put(intern, Long.valueOf(typesBitSet));
            }
        }
        ArrayMap arrayMap4 = systemConfig.mPackageToUserTypeBlacklist;
        systemConfig.mPackageToUserTypeBlacklist = new ArrayMap(0);
        for (int i3 = 0; i3 < arrayMap4.size(); i3++) {
            String intern2 = ((String) arrayMap4.keyAt(i3)).intern();
            long typesBitSet2 = getTypesBitSet((Iterable) arrayMap4.valueAt(i3), arrayMap);
            Long l = (Long) arrayMap3.get(intern2);
            if (l != null) {
                arrayMap3.put(intern2, Long.valueOf((~typesBitSet2) & l.longValue()));
            } else if (typesBitSet2 != 0) {
                arrayMap3.put(intern2, 0L);
            }
        }
        arrayMap3.put("android", -1L);
        return arrayMap3;
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        String[] strArr;
        int whitelistMode = getWhitelistMode();
        indentingPrintWriter.println("Whitelisted packages per user type");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("Mode: ");
        indentingPrintWriter.print(whitelistMode);
        indentingPrintWriter.print((whitelistMode & 1) != 0 ? " (enforced)" : "");
        indentingPrintWriter.print((whitelistMode & 2) != 0 ? " (logged)" : "");
        indentingPrintWriter.print((whitelistMode & 4) != 0 ? " (implicit)" : "");
        indentingPrintWriter.print((whitelistMode & 16) != 0 ? " (ignore OTAs)" : "");
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("Legend");
        indentingPrintWriter.increaseIndent();
        int i = 0;
        while (true) {
            strArr = this.mUserTypes;
            if (i >= strArr.length) {
                break;
            }
            indentingPrintWriter.println(i + " -> " + strArr[i]);
            i++;
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.increaseIndent();
        int size = this.mWhitelistedPackagesForUserTypes.size();
        if (size == 0) {
            indentingPrintWriter.println("No packages");
            indentingPrintWriter.decreaseIndent();
            return;
        }
        indentingPrintWriter.print(size);
        indentingPrintWriter.println(" packages:");
        indentingPrintWriter.increaseIndent();
        for (int i2 = 0; i2 < size; i2++) {
            indentingPrintWriter.print((String) this.mWhitelistedPackagesForUserTypes.keyAt(i2));
            indentingPrintWriter.print(": ");
            long longValue = ((Long) this.mWhitelistedPackagesForUserTypes.valueAt(i2)).longValue();
            for (int i3 = 0; i3 < strArr.length; i3++) {
                if (((1 << i3) & longValue) != 0) {
                    indentingPrintWriter.print(i3);
                    indentingPrintWriter.print(" ");
                }
            }
            indentingPrintWriter.println();
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.increaseIndent();
        dumpPackageWhitelistProblems(indentingPrintWriter, whitelistMode, true, false);
        indentingPrintWriter.decreaseIndent();
    }

    public final void dumpPackageWhitelistProblems(
            IndentingPrintWriter indentingPrintWriter, int i, boolean z, boolean z2) {
        if (i == -1000) {
            i = getWhitelistMode();
        } else if (i == -1) {
            i = Resources.getSystem().getInteger(R.integer.device_idle_motion_inactive_to_ms);
        }
        if (z2) {
            i &= -3;
        }
        Slog.v(
                "UserSystemPackageInstaller",
                "dumpPackageWhitelistProblems(): using mode " + modeToString(i));
        showIssues(indentingPrintWriter, z, getPackagesWhitelistErrors(i), "errors");
        if (z2) {
            return;
        }
        showIssues(indentingPrintWriter, z, getPackagesWhitelistWarnings(), "warnings");
    }

    public final Set getInstallablePackagesForUserType(String str) {
        UserTypeDetails userTypeDetails;
        int whitelistMode = getWhitelistMode();
        if ((whitelistMode & 1) == 0) {
            return null;
        }
        final boolean z =
                ((whitelistMode & 4) == 0
                                && ((whitelistMode & 8) == 0
                                        || (userTypeDetails =
                                                        (UserTypeDetails)
                                                                this.mUm.mUserTypes.get(str))
                                                == null
                                        || (userTypeDetails.mBaseType & 2048) == 0))
                        ? false
                        : true;
        final Set whitelistedPackagesForUserType = getWhitelistedPackagesForUserType(str);
        final ArraySet arraySet = new ArraySet();
        ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
                .forEachPackageState(
                        new Consumer() { // from class:
                                         // com.android.server.pm.UserSystemPackageInstaller$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                UserSystemPackageInstaller userSystemPackageInstaller =
                                        UserSystemPackageInstaller.this;
                                Set set = whitelistedPackagesForUserType;
                                boolean z2 = z;
                                Set set2 = arraySet;
                                PackageStateInternal packageStateInternal =
                                        (PackageStateInternal) obj;
                                userSystemPackageInstaller.getClass();
                                AndroidPackage androidPackage =
                                        packageStateInternal.getAndroidPackage();
                                if (androidPackage != null
                                        && packageStateInternal.isSystem()
                                        && UserSystemPackageInstaller.shouldInstallPackage(
                                                androidPackage,
                                                userSystemPackageInstaller
                                                        .mWhitelistedPackagesForUserTypes,
                                                set,
                                                z2)) {
                                    set2.add(androidPackage.getPackageName());
                                }
                            }
                        });
        return arraySet;
    }

    public final List getPackagesWhitelistErrors(int i) {
        if (((i & 1) == 0 || (i & 4) != 0) && (i & 2) == 0) {
            return Collections.emptyList();
        }
        final ArrayList arrayList = new ArrayList();
        final Set keySet = this.mWhitelistedPackagesForUserTypes.keySet();
        final PackageManagerInternal packageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        packageManagerInternal.forEachPackageState(
                new Consumer() { // from class:
                    // com.android.server.pm.UserSystemPackageInstaller$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        Set set = keySet;
                        PackageManagerInternal packageManagerInternal2 = packageManagerInternal;
                        List list = arrayList;
                        PackageStateInternal packageStateInternal = (PackageStateInternal) obj;
                        AndroidPackage androidPackage = packageStateInternal.getAndroidPackage();
                        if (androidPackage == null
                                || !packageStateInternal.isSystem()
                                || androidPackage.isApex()) {
                            return;
                        }
                        String manifestPackageName = androidPackage.getManifestPackageName();
                        if (set.contains(manifestPackageName)
                                || packageManagerInternal2
                                        .getPackage(manifestPackageName)
                                        .isOverlayIsStatic()) {
                            return;
                        }
                        list.add(
                                "System package "
                                        + manifestPackageName
                                        + " is not whitelisted using 'install-in-user-type' in"
                                        + " SystemConfig for any user types!");
                    }
                });
        return arrayList;
    }

    public final List getPackagesWhitelistWarnings() {
        Set<String> keySet = this.mWhitelistedPackagesForUserTypes.keySet();
        ArrayList arrayList = new ArrayList();
        PackageManagerInternal packageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        for (String str : keySet) {
            PackageStateInternal packageStateInternal =
                    packageManagerInternal.getPackageStateInternal(str);
            AndroidPackage androidPackage =
                    packageStateInternal == null ? null : packageStateInternal.getAndroidPackage();
            if (androidPackage == null) {
                arrayList.add(str + " is allowlisted but not present.");
            } else if (!packageStateInternal.isSystem()) {
                arrayList.add(str + " is allowlisted and present but not a system package.");
            } else if (androidPackage.isOverlayIsStatic()) {
                arrayList.add(str + " is allowlisted unnecessarily since it's a static overlay.");
            }
        }
        return arrayList;
    }

    public final long getTypesBitSet(Iterable iterable, Map map) {
        Iterator it = iterable.iterator();
        long j = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            Long l = (Long) ((ArrayMap) map).get(str);
            if (l != null) {
                j |= l.longValue();
            } else {
                long userTypeMask = getUserTypeMask(str);
                if (userTypeMask != 0) {
                    j |= userTypeMask;
                } else {
                    HeimdAllFsService$$ExternalSyntheticOutline0.m(
                            "SystemConfig contained an invalid user type: ",
                            str,
                            "UserSystemPackageInstaller");
                }
            }
        }
        return j;
    }

    public long getUserTypeMask(String str) {
        if (Arrays.binarySearch(this.mUserTypes, str) >= 0) {
            return 1 << r0;
        }
        return 0L;
    }

    public Set getWhitelistedPackagesForUserType(String str) {
        long userTypeMask = getUserTypeMask(str);
        ArraySet arraySet = new ArraySet(this.mWhitelistedPackagesForUserTypes.size());
        for (int i = 0; i < this.mWhitelistedPackagesForUserTypes.size(); i++) {
            String str2 = (String) this.mWhitelistedPackagesForUserTypes.keyAt(i);
            if ((((Long) this.mWhitelistedPackagesForUserTypes.valueAt(i)).longValue()
                            & userTypeMask)
                    != 0) {
                arraySet.add(str2);
            }
        }
        return arraySet;
    }
}
