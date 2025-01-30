package com.android.server.pm;

import android.content.pm.SharedLibraryInfo;
import android.content.pm.SigningDetails;
import android.os.SystemProperties;
import android.util.ArrayMap;
import com.android.server.pm.Settings;
import com.android.server.pm.parsing.pkg.ParsedPackage;
import com.android.server.pm.pkg.AndroidPackage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public abstract class ReconcilePackageUtils {
    public static List reconcilePackages(List list, Map map, Map map2, SharedLibrariesImpl sharedLibrariesImpl, KeySetManagerService keySetManagerService, Settings settings) {
        DeletePackageAction deletePackageAction;
        boolean z;
        boolean z2;
        SigningDetails signingDetails;
        boolean z3;
        boolean z4;
        Iterator it;
        ReconciledPackage reconciledPackage;
        KeySetManagerService keySetManagerService2 = keySetManagerService;
        ArrayList arrayList = new ArrayList(list.size());
        ArrayMap arrayMap = new ArrayMap(map.size() + list.size());
        arrayMap.putAll(map);
        Map arrayMap2 = new ArrayMap();
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            InstallRequest installRequest = (InstallRequest) it2.next();
            installRequest.onReconcileStarted();
            arrayMap.put(installRequest.getScannedPackageSetting().getPackageName(), installRequest.getParsedPackage());
            List<SharedLibraryInfo> allowedSharedLibInfos = sharedLibrariesImpl.getAllowedSharedLibInfos(installRequest);
            if (allowedSharedLibInfos != null) {
                for (SharedLibraryInfo sharedLibraryInfo : allowedSharedLibInfos) {
                    if (!SharedLibraryUtils.addSharedLibraryToPackageVersionMap(arrayMap2, sharedLibraryInfo)) {
                        throw ReconcileFailure.ofInternalError("Shared Library " + sharedLibraryInfo.getName() + " is being installed twice in this set!", -6);
                    }
                }
            }
        }
        Iterator it3 = list.iterator();
        while (it3.hasNext()) {
            InstallRequest installRequest2 = (InstallRequest) it3.next();
            String packageName = installRequest2.getParsedPackage().getPackageName();
            List allowedSharedLibInfos2 = sharedLibrariesImpl.getAllowedSharedLibInfos(installRequest2);
            if (!installRequest2.isInstallReplace() || installRequest2.isInstallSystem()) {
                deletePackageAction = null;
            } else {
                DeletePackageAction mayDeletePackageLocked = DeletePackageHelper.mayDeletePackageLocked(installRequest2.getRemovedInfo(), installRequest2.getOriginalPackageSetting(), installRequest2.getDisabledPackageSetting(), ((installRequest2.getScanFlags() & 1024) == 0 ? 0 : 8) | 1, null);
                if (mayDeletePackageLocked == null) {
                    throw new ReconcileFailure(-10, "May not delete " + packageName + " to replace");
                }
                deletePackageAction = mayDeletePackageLocked;
            }
            int scanFlags = installRequest2.getScanFlags();
            int parseFlags = installRequest2.getParseFlags();
            ParsedPackage parsedPackage = installRequest2.getParsedPackage();
            PackageSetting disabledPackageSetting = installRequest2.getDisabledPackageSetting();
            PackageSetting staticSharedLibLatestVersionSetting = installRequest2.getStaticSharedLibraryInfo() == null ? null : sharedLibrariesImpl.getStaticSharedLibLatestVersionSetting(installRequest2);
            if (staticSharedLibLatestVersionSetting == null) {
                staticSharedLibLatestVersionSetting = installRequest2.getScannedPackageSetting();
            }
            PackageSetting packageSetting = staticSharedLibLatestVersionSetting;
            SigningDetails signingDetails2 = parsedPackage != null ? parsedPackage.getSigningDetails() : null;
            SharedUserSetting sharedUserSettingLPr = settings.getSharedUserSettingLPr(packageSetting);
            ArrayList arrayList2 = arrayList;
            if (keySetManagerService2.shouldCheckUpgradeKeySetLocked(packageSetting, sharedUserSettingLPr, scanFlags)) {
                if (!keySetManagerService2.checkUpgradeKeySetLocked(packageSetting, parsedPackage)) {
                    if ((parseFlags & 16) == 0) {
                        throw new ReconcileFailure(-7, "Package " + parsedPackage.getPackageName() + " upgrade keys do not match the previously installed version");
                    }
                    PackageManagerService.reportSettingsProblem(5, "System package " + parsedPackage.getPackageName() + " signature changed; retaining data.");
                }
                signingDetails = signingDetails2;
                z3 = false;
                z4 = false;
            } else {
                try {
                    try {
                        Settings.VersionInfo versionInfo = (Settings.VersionInfo) map2.get(packageName);
                        z = PackageManagerServiceUtils.verifySignatures(packageSetting, sharedUserSettingLPr, disabledPackageSetting, signingDetails2, isCompatSignatureUpdateNeeded(versionInfo), isRecoverSignatureUpdateNeeded(versionInfo), installRequest2.isRollback());
                        if (sharedUserSettingLPr != null) {
                            try {
                                SigningDetails signingDetails3 = sharedUserSettingLPr.signatures.mSigningDetails;
                                SigningDetails mergeLineageWith = signingDetails3.mergeLineageWith(signingDetails2);
                                if (mergeLineageWith != signingDetails3) {
                                    Iterator it4 = sharedUserSettingLPr.getPackages().iterator();
                                    while (it4.hasNext()) {
                                        AndroidPackage androidPackage = (AndroidPackage) it4.next();
                                        if (androidPackage.getPackageName() != null) {
                                            it = it4;
                                            if (!androidPackage.getPackageName().equals(parsedPackage.getPackageName())) {
                                                mergeLineageWith = mergeLineageWith.mergeLineageWith(androidPackage.getSigningDetails(), 2);
                                            }
                                        } else {
                                            it = it4;
                                        }
                                        it4 = it;
                                    }
                                    sharedUserSettingLPr.signatures.mSigningDetails = mergeLineageWith;
                                }
                                if (sharedUserSettingLPr.signaturesChanged == null) {
                                    sharedUserSettingLPr.signaturesChanged = Boolean.FALSE;
                                }
                            } catch (PackageManagerException e) {
                                e = e;
                                if ((parseFlags & 16) == 0) {
                                    throw new ReconcileFailure(e);
                                }
                                SigningDetails signingDetails4 = parsedPackage.getSigningDetails();
                                if (sharedUserSettingLPr == null) {
                                    z2 = false;
                                } else {
                                    if (sharedUserSettingLPr.signaturesChanged != null && !PackageManagerServiceUtils.canJoinSharedUserId(parsedPackage.getPackageName(), parsedPackage.getSigningDetails(), sharedUserSettingLPr, 2)) {
                                        if (SystemProperties.getInt("ro.product.first_api_level", 0) <= 29) {
                                            throw new ReconcileFailure(-104, "Signature mismatch for shared user: " + sharedUserSettingLPr);
                                        }
                                        throw new IllegalStateException("Signature mismatch on system package " + parsedPackage.getPackageName() + " for shared user " + sharedUserSettingLPr);
                                    }
                                    sharedUserSettingLPr.signatures.mSigningDetails = parsedPackage.getSigningDetails();
                                    sharedUserSettingLPr.signaturesChanged = Boolean.TRUE;
                                    z2 = true;
                                }
                                PackageManagerService.reportSettingsProblem(5, "System package " + parsedPackage.getPackageName() + " signature changed; retaining data.");
                                signingDetails = signingDetails4;
                                z3 = z2;
                                z4 = z;
                                reconciledPackage = new ReconciledPackage(list, map, installRequest2, deletePackageAction, allowedSharedLibInfos2, signingDetails, z3, z4);
                                if ((installRequest2.getScanFlags() & 16) == 0) {
                                    try {
                                        reconciledPackage.mCollectedSharedLibraryInfos = sharedLibrariesImpl.collectSharedLibraryInfos(installRequest2.getParsedPackage(), arrayMap, arrayMap2);
                                    } catch (PackageManagerException e2) {
                                        throw new ReconcileFailure(e2.error, e2.getMessage());
                                    }
                                }
                                installRequest2.onReconcileFinished();
                                arrayList2.add(reconciledPackage);
                                arrayList = arrayList2;
                                keySetManagerService2 = keySetManagerService;
                            }
                        }
                        z4 = z;
                        signingDetails = signingDetails2;
                        z3 = false;
                    } catch (PackageManagerException e3) {
                        e = e3;
                        z = false;
                    }
                } catch (IllegalArgumentException e4) {
                    throw new RuntimeException("Signing certificates comparison made on incomparable signing details but somehow passed verifySignatures!", e4);
                }
            }
            reconciledPackage = new ReconciledPackage(list, map, installRequest2, deletePackageAction, allowedSharedLibInfos2, signingDetails, z3, z4);
            if ((installRequest2.getScanFlags() & 16) == 0 && (installRequest2.getParseFlags() & 16) == 0) {
                reconciledPackage.mCollectedSharedLibraryInfos = sharedLibrariesImpl.collectSharedLibraryInfos(installRequest2.getParsedPackage(), arrayMap, arrayMap2);
            }
            installRequest2.onReconcileFinished();
            arrayList2.add(reconciledPackage);
            arrayList = arrayList2;
            keySetManagerService2 = keySetManagerService;
        }
        return arrayList;
    }

    public static boolean isCompatSignatureUpdateNeeded(Settings.VersionInfo versionInfo) {
        return versionInfo.databaseVersion < 2;
    }

    public static boolean isRecoverSignatureUpdateNeeded(Settings.VersionInfo versionInfo) {
        return versionInfo.databaseVersion < 3;
    }
}
