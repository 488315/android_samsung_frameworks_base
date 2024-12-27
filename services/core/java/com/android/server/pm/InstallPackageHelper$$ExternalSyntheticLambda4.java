package com.android.server.pm;

import android.app.ResourcesManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.XmlResourceParser;
import android.os.IBinder;
import android.util.ArraySet;
import android.util.Slog;

import com.android.internal.pm.parsing.pkg.AndroidPackageHidden;
import com.android.server.SystemConfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final /* synthetic */ class InstallPackageHelper$$ExternalSyntheticLambda4
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InstallPackageHelper f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ InstallPackageHelper$$ExternalSyntheticLambda4(
            InstallPackageHelper installPackageHelper, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = installPackageHelper;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AndroidPackageHidden androidPackageHidden;
        ArraySet arraySet;
        switch (this.$r8$classId) {
            case 0:
                InstallPackageHelper installPackageHelper = this.f$0;
                PackageSetting packageSetting = (PackageSetting) this.f$1;
                installPackageHelper.mUpdateOwnershipHelper.getClass();
                if (UpdateOwnershipHelper.hasValidOwnershipDenyList(packageSetting)
                        && (androidPackageHidden = packageSetting.pkg) != null) {
                    arraySet = new ArraySet(500);
                    try {
                        int resourceId =
                                ((PackageManager.Property)
                                                androidPackageHidden
                                                        .getProperties()
                                                        .get(
                                                                "android.app.PROPERTY_LEGACY_UPDATE_OWNERSHIP_DENYLIST"))
                                        .getResourceId();
                        ApplicationInfo appInfoWithoutState =
                                androidPackageHidden.toAppInfoWithoutState();
                        XmlResourceParser xml =
                                ResourcesManager.getInstance()
                                        .getResources(
                                                (IBinder) null,
                                                appInfoWithoutState.sourceDir,
                                                appInfoWithoutState.splitSourceDirs,
                                                appInfoWithoutState.resourceDirs,
                                                appInfoWithoutState.overlayPaths,
                                                appInfoWithoutState.sharedLibraryFiles,
                                                (Integer) null,
                                                Configuration.EMPTY,
                                                (CompatibilityInfo) null,
                                                (ClassLoader) null,
                                                (List) null)
                                        .getXml(resourceId);
                        while (true) {
                            try {
                                if (xml.getEventType() != 1) {
                                    if (xml.next() == 2 && "deny-ownership".equals(xml.getName())) {
                                        xml.next();
                                        String text = xml.getText();
                                        if (text != null && !text.isBlank()) {
                                            arraySet.add(text);
                                            if (arraySet.size() > 500) {
                                                Slog.w(
                                                        "PackageManager",
                                                        "Deny list defined by "
                                                                + androidPackageHidden
                                                                        .getPackageName()
                                                                + " was trucated to maximum size of"
                                                                + " 500");
                                            }
                                        }
                                    }
                                }
                            } finally {
                            }
                        }
                        xml.close();
                    } catch (Exception e) {
                        Slog.e(
                                "PackageManager",
                                "Failed to parse update owner list for " + packageSetting.mName,
                                e);
                    }
                    if (arraySet != null || arraySet.isEmpty()) {
                        return;
                    }
                    UpdateOwnershipHelper updateOwnershipHelper =
                            installPackageHelper.mUpdateOwnershipHelper;
                    String str = packageSetting.mName;
                    synchronized (updateOwnershipHelper.mLock) {
                        for (int i = 0; i < arraySet.size(); i++) {
                            try {
                                ArraySet arraySet2 =
                                        (ArraySet)
                                                updateOwnershipHelper.mUpdateOwnerOptOutsToOwners
                                                        .putIfAbsent(
                                                                (String) arraySet.valueAt(i),
                                                                new ArraySet(new String[] {str}));
                                if (arraySet2 != null) {
                                    arraySet2.add(str);
                                }
                            } finally {
                            }
                        }
                    }
                    SystemConfig systemConfig = SystemConfig.getInstance();
                    PackageManagerTracedLock packageManagerTracedLock =
                            installPackageHelper.mPm.mLock;
                    boolean z = PackageManagerService.DEBUG_COMPRESSION;
                    synchronized (packageManagerTracedLock) {
                        try {
                            Iterator it = arraySet.iterator();
                            while (it.hasNext()) {
                                String str2 = (String) it.next();
                                PackageSetting packageLPr =
                                        installPackageHelper.mPm.mSettings.getPackageLPr(str2);
                                if (packageLPr != null
                                        && ((String)
                                                        systemConfig.mUpdateOwnersForSystemApps.get(
                                                                str2))
                                                == null) {
                                    packageLPr.setUpdateOwnerPackage(null);
                                }
                            }
                        } finally {
                            boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                        }
                    }
                    boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                    return;
                }
                arraySet = null;
                if (arraySet != null) {
                    return;
                } else {
                    return;
                }
            default:
                InstallPackageHelper installPackageHelper2 = this.f$0;
                installPackageHelper2.mBroadcastHelper.sendResourcesChangedBroadcastAndNotify(
                        installPackageHelper2.mPm.snapshotComputer(),
                        true,
                        true,
                        (ArrayList) this.f$1,
                        null);
                return;
        }
    }
}
