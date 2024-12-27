package com.android.server.pm;

import android.content.pm.SigningDetails;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
}
