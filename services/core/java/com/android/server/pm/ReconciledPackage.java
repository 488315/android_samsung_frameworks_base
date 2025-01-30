package com.android.server.pm;

import android.content.pm.SigningDetails;
import android.util.ArrayMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class ReconciledPackage {
    public final Map mAllPackages;
    public final List mAllowedSharedLibraryInfos;
    public ArrayList mCollectedSharedLibraryInfos;
    public final DeletePackageAction mDeletePackageAction;
    public final InstallRequest mInstallRequest;
    public final List mInstallRequests;
    public final boolean mRemoveAppKeySetData;
    public final boolean mSharedUserSignaturesChanged;
    public final SigningDetails mSigningDetails;

    public ReconciledPackage(List list, Map map, InstallRequest installRequest, DeletePackageAction deletePackageAction, List list2, SigningDetails signingDetails, boolean z, boolean z2) {
        this.mInstallRequests = list;
        this.mAllPackages = map;
        this.mInstallRequest = installRequest;
        this.mDeletePackageAction = deletePackageAction;
        this.mAllowedSharedLibraryInfos = list2;
        this.mSigningDetails = signingDetails;
        this.mSharedUserSignaturesChanged = z;
        this.mRemoveAppKeySetData = z2;
    }

    public Map getCombinedAvailablePackages() {
        ArrayMap arrayMap = new ArrayMap(this.mAllPackages.size() + this.mInstallRequests.size());
        arrayMap.putAll(this.mAllPackages);
        for (InstallRequest installRequest : this.mInstallRequests) {
            arrayMap.put(installRequest.getScannedPackageSetting().getPackageName(), installRequest.getParsedPackage());
        }
        return arrayMap;
    }
}
