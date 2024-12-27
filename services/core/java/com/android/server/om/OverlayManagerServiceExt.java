package com.android.server.om;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.Context;
import android.content.om.CriticalOverlayInfo;
import android.content.om.ISamsungOverlayCallback;
import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import android.content.om.OverlayInfoExt;
import android.content.om.SamsungThemeConstants;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ServiceInfo;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.view.textservice.SpellCheckerInfo;
import android.view.textservice.TextServicesManager;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda1;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageUserStateInternal;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class OverlayManagerServiceExt {
    public final Context mContext;
    public final IdmapManagerWrapper mIdmapManager;
    public boolean mIsInitOnBoot;
    public final PMSHelperImpl mPackageManager;
    public final OverlayManagerService.PackageUpdateHelper mPackageUpdateHelper;
    public final OverlayManagerSettings mSettings;
    public final UserManagerService mUserManager = UserManagerService.getInstance();

    public final class PMSHelperImpl {
        public final OverlayManagerService.PackageManagerHelperImpl packageManagerHelper;

        public PMSHelperImpl(
                OverlayManagerService.PackageManagerHelperImpl packageManagerHelperImpl) {
            this.packageManagerHelper = packageManagerHelperImpl;
        }
    }

    public OverlayManagerServiceExt(
            Context context,
            OverlayManagerService.PackageManagerHelperImpl packageManagerHelperImpl,
            OverlayManagerSettings overlayManagerSettings,
            IdmapManager idmapManager,
            OverlayManagerService.PackageUpdateHelper packageUpdateHelper) {
        this.mContext = context;
        this.mPackageManager = new PMSHelperImpl(packageManagerHelperImpl);
        if (IdmapDaemon.sInstance == null) {
            IdmapDaemon.sInstance = new IdmapDaemon();
        }
        this.mIdmapManager = new IdmapManagerWrapper(idmapManager, IdmapDaemon.sInstance);
        this.mSettings = overlayManagerSettings;
        this.mPackageUpdateHelper = packageUpdateHelper;
        for (int i : overlayManagerSettings.getUsers()) {
            for (OverlayInfo overlayInfo :
                    (List)
                            getSafeStream(overlayManagerSettings.getOverlaysForUser(i).values())
                                    .flatMap(
                                            new OverlayManagerService$1$$ExternalSyntheticLambda1(
                                                    0))
                                    .collect(Collectors.toList())) {
                if (OverlayInfoExt.isOverlayInfoExt(overlayInfo)
                        && !new File(overlayInfo.baseCodePath).exists()) {
                    overlayManagerSettings.remove(overlayInfo.getOverlayIdentifier(), i);
                    removeIdmap(OverlayInfoExt.initFromInfo(overlayInfo));
                }
            }
        }
        OverlayInfoExtPolicy overlayInfoExtPolicy = new OverlayInfoExtPolicy();
        synchronized (OverlayPolicyManager.mLock) {
            ((ArrayList) OverlayPolicyManager.policies).add(overlayInfoExtPolicy);
        }
        try {
            int applicationEnabledSetting =
                    this.mContext
                            .getPackageManager()
                            .getApplicationEnabledSetting("com.samsung.android.themecenter");
            if (applicationEnabledSetting > 1) {
                Slog.i(
                        "OverlayManagerExt",
                        "themecenter state is "
                                + applicationEnabledSetting
                                + ", but it'll be enabled.");
                this.mContext
                        .getPackageManager()
                        .setApplicationEnabledSetting("com.samsung.android.themecenter", 1, 0);
            }
        } catch (Exception e) {
            Slog.e("OverlayManagerExt", "Failed to enable themecenter " + e.toString());
        }
        try {
            PackageUserStateInternal userStateOrDefault =
                    ((PackageManagerInternal)
                                    LocalServices.getService(PackageManagerInternal.class))
                            .getPackageStateInternal("com.samsung.android.themestore")
                            .getUserStateOrDefault(0);
            if (userStateOrDefault.getEnabledState() <= 1
                    || !"shell:1000".equals(userStateOrDefault.getLastDisableAppCaller())) {
                return;
            }
            Slog.i(
                    "OverlayManagerExt",
                    "themestore state is "
                            + userStateOrDefault.getEnabledState()
                            + " and the last disabler is "
                            + userStateOrDefault.getLastDisableAppCaller()
                            + ", but it'll be enabled.");
            this.mContext
                    .getPackageManager()
                    .setApplicationEnabledSetting("com.samsung.android.themestore", 1, 0);
        } catch (Exception e2) {
            Slog.e("OverlayManagerExt", "Failed to enable themestore " + e2.toString());
        }
    }

    public static Stream getSafeStream(Collection collection) {
        return ((Collection)
                        Optional.ofNullable(collection)
                                .orElseGet(
                                        new OverlayManagerServiceExt$$ExternalSyntheticLambda6()))
                .stream().filter(new OverlayManagerServiceExt$$ExternalSyntheticLambda2(2));
    }

    public final OverlayInfoExt[] getAllOverlaysInCategory(int i, int i2) {
        ArrayMap overlaysForUser = this.mSettings.getOverlaysForUser(i2);
        ArrayList arrayList = new ArrayList();
        Iterator it = overlaysForUser.values().iterator();
        while (it.hasNext()) {
            for (OverlayInfo overlayInfo : (List) it.next()) {
                if (OverlayInfoExt.isOverlayInfoExtOfCategory(overlayInfo, i)) {
                    arrayList.add(OverlayInfoExt.initFromInfo(overlayInfo));
                }
            }
        }
        return (OverlayInfoExt[]) arrayList.toArray(new OverlayInfoExt[0]);
    }

    public final String getTargetPath(String str) {
        IdmapManagerWrapper idmapManagerWrapper = this.mIdmapManager;
        idmapManagerWrapper.getClass();
        Slog.d("OverlayManagerExt", "getTargetPath for " + str);
        try {
            return idmapManagerWrapper.mIdmapDaemon.getTargetPath(str);
        } catch (Exception e) {
            Slog.w("OverlayManagerExt", "failed to getTargetPath for " + str, e);
            return null;
        }
    }

    public final int[] getUserIds(int i, int i2) {
        int[] userIds = this.mUserManager.getUserIds();
        if (-1 == i2) {
            return userIds;
        }
        if (i2 != 0) {
            return new int[] {i2};
        }
        if ((i & 16) != 0) {
            return userIds;
        }
        if ((i & 32) == 0) {
            return new int[] {i2};
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(i2));
        for (int i3 : userIds) {
            if (SemPersonaManager.isKnoxId(i3) || SemPersonaManager.isDualAppId(i3)) {
                arrayList.add(Integer.valueOf(i3));
            }
        }
        return getSafeStream(arrayList)
                .mapToInt(new AudioService$$ExternalSyntheticLambda1(2))
                .toArray();
    }

    public final int handleStateUpdate(
            AndroidPackage androidPackage, CriticalOverlayInfo criticalOverlayInfo, int i, int i2) {
        int i3;
        boolean z;
        OverlayManagerSettings overlayManagerSettings = this.mSettings;
        int i4 = 0;
        try {
            OverlayInfo overlayInfo =
                    overlayManagerSettings.getOverlayInfo(
                            new OverlayIdentifier(
                                    ((OverlayInfo) criticalOverlayInfo).getPackageName()),
                            i);
            Slog.e(
                    "OverlayManagerExt",
                    "handleStateUpdate info exists as " + overlayInfo + " for user " + i);
            OverlayInfoExt initFromInfo = OverlayInfoExt.initFromInfo(overlayInfo);
            if (initFromInfo == null) {
                return 0;
            }
            if ((i2 & 24) != 0
                    && overlayInfo.getCategory() != null
                    && overlayInfo.getCategory().startsWith("zipped-overlay")) {
                return 1;
            }
            IdmapManagerWrapper idmapManagerWrapper = this.mIdmapManager;
            if (androidPackage != null) {
                i3 = idmapManagerWrapper.createIdmap(androidPackage, initFromInfo, i);
                z = (i3 & 2) != 0;
            } else {
                i3 = 0;
                z = false;
            }
            int state =
                    overlayManagerSettings.getState(initFromInfo.info.getOverlayIdentifier(), i);
            OverlayInfo overlayInfo2 = initFromInfo.info;
            if ((i2 & 1) != 0) {
                i4 = 4;
            } else if ((i2 & 2) != 0) {
                i4 = 5;
            } else if ((i2 & 4) != 0) {
                i4 = 7;
            } else if (androidPackage != null) {
                if ((i3 & 1) == 0) {
                    idmapManagerWrapper.getClass();
                    if (!idmapManagerWrapper.mIdmapDaemon.idmapExists(
                            overlayInfo2.userId, overlayInfo2.baseCodePath)) {
                        i4 = 1;
                    }
                }
                i4 =
                        overlayManagerSettings.getEnabled(overlayInfo2.getOverlayIdentifier(), i)
                                ? 3
                                : 2;
            }
            if (state != i4) {
                updateOverlayState(androidPackage, initFromInfo, i, i4);
                z = true;
            }
            return z ? 2 : 1;
        } catch (OverlayManagerSettings.BadKeyException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public final void handleUserSwitch(int i) {
        OverlayManagerSettings overlayManagerSettings = this.mSettings;
        List list =
                (List)
                        getSafeStream(
                                        new ArrayList(
                                                (Collection)
                                                        overlayManagerSettings
                                                                .getOverlaysForUser(i)
                                                                .values()
                                                                .stream()
                                                                .flatMap(
                                                                        new OverlayManagerService$1$$ExternalSyntheticLambda1(
                                                                                0))
                                                                .collect(Collectors.toSet())))
                                .map(new OverlayManagerServiceExt$$ExternalSyntheticLambda1(0))
                                .filter(new OverlayManagerServiceExt$$ExternalSyntheticLambda2(0))
                                .collect(Collectors.toList());
        HashSet hashSet = new HashSet();
        hashSet.addAll(list);
        if (i != 0) {
            ArrayList arrayList =
                    new ArrayList(
                            (Collection)
                                    overlayManagerSettings.getOverlaysForUser(0).values().stream()
                                            .flatMap(
                                                    new OverlayManagerService$1$$ExternalSyntheticLambda1(
                                                            0))
                                            .collect(Collectors.toSet()));
            final int i2 =
                    (SemPersonaManager.isKnoxId(i) || SemPersonaManager.isDualAppId(i)) ? 48 : 16;
            hashSet.addAll(
                    (List)
                            getSafeStream(arrayList)
                                    .map(new OverlayManagerServiceExt$$ExternalSyntheticLambda1(0))
                                    .filter(
                                            new OverlayManagerServiceExt$$ExternalSyntheticLambda2(
                                                    0))
                                    .filter(
                                            new Predicate() { // from class:
                                                              // com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda3
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    return (i2 & ((OverlayInfoExt) obj).configFlags)
                                                            != 0;
                                                }
                                            })
                                    .collect(Collectors.toList()));
        }
        ArrayList arrayList2 = new ArrayList(hashSet);
        if (arrayList2.isEmpty()) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    i, "Working without any overlayInfoExts ? + for user ", "OverlayManagerExt");
        } else {
            notifySystemServices(
                    i,
                    (List)
                            ((Stream) getSafeStream(arrayList2).parallel())
                                    .map(
                                            new OverlayManagerServiceExt$$ExternalSyntheticLambda0(
                                                    this, i, null, 0))
                                    .collect(Collectors.toList()),
                    arrayList2);
            this.mIsInitOnBoot = false;
        }
    }

    public final boolean isOverlayUsedByOtherUser(OverlayInfoExt overlayInfoExt, boolean z) {
        OverlayInfo overlayInfo;
        if (overlayInfoExt != null && overlayInfoExt.info != null) {
            OverlayManagerSettings overlayManagerSettings = this.mSettings;
            for (int i : overlayManagerSettings.getUsers()) {
                try {
                    overlayInfo =
                            overlayManagerSettings.getOverlayInfo(
                                    overlayInfoExt.getOverlayIdentifier(), i);
                } catch (OverlayManagerSettings.BadKeyException unused) {
                }
                if (overlayInfo != null) {
                    if (!z || i == 0) {
                        return overlayInfo.isEnabled();
                    }
                    return true;
                }
                continue;
            }
        }
        return false;
    }

    public final void notifySystemServices(final int i, List list, List list2) {
        boolean z;
        final List updatePackageManagerLocked;
        ServiceInfo serviceInfo;
        String str;
        ApplicationInfo applicationInfo;
        List list3 =
                (List)
                        ((Stream) getSafeStream(list2).parallel())
                                .map(
                                        new Function() { // from class:
                                                         // com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda8
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                OverlayManagerServiceExt overlayManagerServiceExt =
                                                        OverlayManagerServiceExt.this;
                                                int i2 = i;
                                                OverlayInfoExt overlayInfoExt =
                                                        (OverlayInfoExt) obj;
                                                overlayManagerServiceExt.getClass();
                                                if (overlayInfoExt == null
                                                        || overlayInfoExt.info == null) {
                                                    return null;
                                                }
                                                return (List)
                                                        Arrays.stream(
                                                                        overlayManagerServiceExt
                                                                                .getUserIds(
                                                                                        overlayInfoExt
                                                                                                .configFlags,
                                                                                        i2))
                                                                .boxed()
                                                                .collect(Collectors.toList());
                                            }
                                        })
                                .collect(Collectors.toList());
        HashSet hashSet = new HashSet();
        Iterator it = list3.iterator();
        while (it.hasNext()) {
            hashSet.addAll((List) it.next());
        }
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            final int intValue = ((Integer) it2.next()).intValue();
            Iterator it3 = list2.iterator();
            while (true) {
                if (!it3.hasNext()) {
                    z = true;
                    break;
                }
                OverlayInfoExt overlayInfoExt = (OverlayInfoExt) it3.next();
                if ("android".equals(overlayInfoExt.info.targetPackageName)
                        && (overlayInfoExt.configFlags & 1024) == 0) {
                    Slog.e(
                            "OverlayManagerExt",
                            "Not pruning targets because of overlayInfoExt of category - "
                                    + overlayInfoExt.category
                                    + " "
                                    + overlayInfoExt.info);
                    z = false;
                    break;
                }
            }
            if (list.contains("android") && z) {
                list.addAll(SamsungThemeConstants.changeableApps);
                Iterator it4 = this.mSettings.getOverlaysForUser(intValue).values().iterator();
                while (it4.hasNext()) {
                    list.addAll(
                            (List)
                                    getSafeStream((List) it4.next())
                                            .map(
                                                    new OverlayManagerServiceExt$$ExternalSyntheticLambda1(
                                                            1))
                                            .collect(Collectors.toList()));
                }
            }
            ArrayList arrayList = new ArrayList(new HashSet(list));
            OverlayManagerService.PackageUpdateHelper packageUpdateHelper =
                    this.mPackageUpdateHelper;
            synchronized (OverlayManagerService.this.mLock) {
                updatePackageManagerLocked =
                        OverlayManagerService.this.updatePackageManagerLocked(
                                intValue, arrayList, z);
            }
            OverlayManagerService.PackageUpdateHelper packageUpdateHelper2 =
                    this.mPackageUpdateHelper;
            synchronized (OverlayManagerService.this.mLock) {
                OverlayManagerService.this.persistSettingsLocked();
            }
            StringBuilder sb =
                    new StringBuilder("notifyActivityManager() called with: targetPackageNames = ");
            sb.append(updatePackageManagerLocked);
            sb.append(", userId = [");
            sb.append(intValue);
            sb.append("], targetsPruned = ");
            AnyMotionDetector$$ExternalSyntheticOutline0.m("OverlayManagerExt", sb, z);
            try {
                SpellCheckerInfo currentSpellCheckerInfo =
                        ((TextServicesManager) this.mContext.getSystemService("textservices"))
                                .getCurrentSpellCheckerInfo();
                if (currentSpellCheckerInfo != null) {
                    serviceInfo = currentSpellCheckerInfo.getServiceInfo();
                    str = currentSpellCheckerInfo.getPackageName();
                } else {
                    serviceInfo = null;
                    str = null;
                }
                if (((ArrayList) updatePackageManagerLocked).contains(str)
                        && serviceInfo != null
                        && (applicationInfo =
                                        this.mContext
                                                .getPackageManager()
                                                .getApplicationInfo(str, 0))
                                != null) {
                    ApplicationInfo applicationInfo2 = serviceInfo.applicationInfo;
                    applicationInfo2.resourceDirs = applicationInfo.resourceDirs;
                    applicationInfo2.overlayPaths = applicationInfo.overlayPaths;
                }
            } catch (Exception e) {
                Slog.d(
                        "OverlayManagerExt",
                        "Exception during getting spell checker service " + e.getMessage());
            }
            if (z) {
                ArrayList arrayList2 = (ArrayList) updatePackageManagerLocked;
                if (arrayList2.contains("android")) {
                    arrayList2.remove("android");
                    arrayList2.add("framework-res");
                }
            }
            FgThread.getHandler()
                    .post(
                            new Runnable() { // from class:
                                // com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda11
                                @Override // java.lang.Runnable
                                public final void run() {
                                    List list4 = updatePackageManagerLocked;
                                    int i2 = intValue;
                                    IActivityManager service = ActivityManager.getService();
                                    if (service != null) {
                                        try {
                                            service.scheduleApplicationInfoChanged(list4, i2);
                                        } catch (RemoteException e2) {
                                            Slog.e(
                                                    "OverlayManagerExt",
                                                    "updateActivityManagerforSamsungOverlay"
                                                        + " scheduleApplicationInfoChanged",
                                                    e2);
                                        } catch (Exception e3) {
                                            Slog.e(
                                                    "OverlayManagerExt",
                                                    "Unkonwn error in"
                                                        + " scheduleApplicationInfoChanged()",
                                                    e3);
                                        }
                                    }
                                }
                            });
        }
    }

    public final void removeIdmap(OverlayInfoExt overlayInfoExt) {
        OverlayInfo overlayInfo;
        if (overlayInfoExt == null || (overlayInfo = overlayInfoExt.info) == null) {
            return;
        }
        IdmapManagerWrapper idmapManagerWrapper = this.mIdmapManager;
        idmapManagerWrapper.getClass();
        if (idmapManagerWrapper.mIdmapDaemon.idmapExists(
                        overlayInfo.userId, overlayInfo.baseCodePath)
                && !isOverlayUsedByOtherUser(overlayInfoExt, false)) {
            StringBuilder sb = new StringBuilder("removing idmap for ");
            sb.append(overlayInfoExt.getTargetPackageName());
            sb.append(" - ");
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    sb, overlayInfoExt.info.packageName, "OverlayManagerExt");
            OverlayInfo overlayInfo2 = overlayInfoExt.info;
            idmapManagerWrapper.mIdmapManager.removeIdmap(overlayInfo2, overlayInfo2.userId);
        }
    }

    public final List removeOverlaysInternal(
            List list, ISamsungOverlayCallback iSamsungOverlayCallback, int i) {
        return (List)
                ((Stream) getSafeStream(list).parallel())
                        .map(
                                new OverlayManagerServiceExt$$ExternalSyntheticLambda0(
                                        this, i, iSamsungOverlayCallback, 1))
                        .filter(new OverlayManagerServiceExt$$ExternalSyntheticLambda2(1))
                        .collect(Collectors.toList());
    }

    public final void replaceOverlays(
            List list, List list2, ISamsungOverlayCallback iSamsungOverlayCallback, int i) {
        if (list == null) {
            list = Collections.emptyList();
        }
        if (list2 == null) {
            list2 = Collections.emptyList();
        }
        List removeOverlaysInternal = removeOverlaysInternal(list, iSamsungOverlayCallback, i);
        ArrayList arrayList =
                new ArrayList(
                        (List)
                                ((Stream) getSafeStream(list2).parallel())
                                        .map(
                                                new OverlayManagerServiceExt$$ExternalSyntheticLambda0(
                                                        this, i, iSamsungOverlayCallback, 0))
                                        .collect(Collectors.toList()));
        arrayList.addAll(removeOverlaysInternal);
        List arrayList2 = new ArrayList(new HashSet(arrayList));
        ArrayList arrayList3 = new ArrayList();
        arrayList3.addAll(list);
        arrayList3.addAll(list2);
        notifySystemServices(i, arrayList2, arrayList3);
    }

    public final void updateOverlayState(
            AndroidPackage androidPackage, OverlayInfoExt overlayInfoExt, int i, int i2) {
        int i3;
        int i4;
        String baseApkPath;
        OverlayManagerSettings overlayManagerSettings = this.mSettings;
        if (i2 == 0) {
            OverlayInfo overlayInfo = overlayInfoExt.info;
            if (i == 0 && androidPackage == null) {
                Slog.i(
                        "OverlayManagerExt",
                        "updateOverlayState(): target package is null, removing idmap for overlay: "
                                + overlayInfo);
                removeIdmap(overlayInfoExt);
                if (overlayInfo != null
                        && overlayInfo.getCategory() != null
                        && overlayInfo.getCategory().startsWith("zipped-overlay")) {
                    if (!isOverlayUsedByOtherUser(overlayInfoExt, true)) {
                        Slog.i(
                                "OverlayManagerExt",
                                "updateOverlayState(): deleting overlay " + overlayInfo);
                        String str = overlayInfo.baseCodePath;
                        List list = SemSamsungThemeUtils.disableOverlayList;
                        if (str == null) {
                            Log.d("OverlayManager", "deleteFile, path is null");
                        } else {
                            try {
                                if (!new File(str).delete()) {
                                    Log.d(
                                            "OverlayManager",
                                            "deleteFile, file deletion failed for path ="
                                                    .concat(str));
                                }
                            } catch (SecurityException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    overlayManagerSettings.remove(overlayInfoExt.getOverlayIdentifier(), i);
                    return;
                }
                if (overlayInfo != null
                        && overlayInfo.getBaseCodePath() != null
                        && overlayInfo
                                .getBaseCodePath()
                                .startsWith("/data/overlays/currentstyle")) {
                    i3 = 0;
                }
            }
            i3 = -1;
        } else if (this.mIsInitOnBoot) {
            i3 = overlayInfoExt.info.state;
        } else if (i2 == 3) {
            if ((overlayInfoExt.configFlags & 512) != 0) {
                List list2 = SemSamsungThemeUtils.disableOverlayList;
                if (androidPackage != null
                        && androidPackage.getMetaData() != null
                        && androidPackage.getMetaData().containsKey("resource-map")
                        && (baseApkPath = androidPackage.getBaseApkPath()) != null) {
                    File file =
                            new File(
                                    "/data/overlays/remaps/"
                                            + baseApkPath.replace("/", ".")
                                            + ".map");
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
            if (i2 == 3) {
                int i5 =
                        (SemDualAppManager.isDualAppId(i) || SemPersonaManager.isKnoxId(i)) ? 0 : i;
                IdmapManagerWrapper idmapManagerWrapper = this.mIdmapManager;
                int createIdmap =
                        idmapManagerWrapper.createIdmap(androidPackage, overlayInfoExt, i5);
                int createIdmap2 =
                        ((createIdmap & 2) != 0 || (overlayInfoExt.configFlags & 8) == 0)
                                ? createIdmap
                                : idmapManagerWrapper.createIdmap(
                                        androidPackage, overlayInfoExt, i5);
                StringBuilder sb = new StringBuilder("createidmap for ");
                sb.append(androidPackage);
                sb.append(" ");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i5, overlayInfoExt.info.packageName, " for user= ", " created ? ", sb);
                VaultKeeperService$$ExternalSyntheticOutline0.m(
                        sb, createIdmap2, "OverlayManagerExt");
                i4 = createIdmap2;
            } else {
                if (i2 == 2) {
                    removeIdmap(overlayInfoExt);
                }
                i4 = 0;
            }
            int i6 = ((i4 & 2) == 0 && (i4 & 1) == 0) ? 1 : 3;
            if ((i4 & 128) != 0) {
                i6 = -1;
            }
            i3 = i6;
        } else {
            i3 = i2;
        }
        if (overlayInfoExt.info != null) {
            try {
                overlayManagerSettings.setState(overlayInfoExt.getOverlayIdentifier(), i, i3);
            } catch (OverlayManagerSettings.BadKeyException e2) {
                e2.printStackTrace();
                StringBuilder sb2 = new StringBuilder("Couldnt update overlay state ");
                sb2.append(overlayInfoExt.info.packageName);
                sb2.append(" for ");
                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                        sb2, overlayInfoExt.info.targetPackageName, "OverlayManagerExt");
            }
        }
    }
}
