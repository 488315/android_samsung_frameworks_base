package com.android.server.pm;

import android.text.TextUtils;

import com.android.internal.util.ArrayUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class KnownPackages {
    public final String mAmbientContextDetectionPackage;
    public final String mAppPredictionServicePackage;
    public final String mConfiguratorPackage;
    public final DefaultAppProvider mDefaultAppProvider;
    public final String mDefaultTextClassifierPackage;
    public final String mIncidentReportApproverPackage;
    public final String mOverlayConfigSignaturePackage;
    public final String mRecentsPackage;
    public final String mRequiredInstallerPackage;
    public final String mRequiredPermissionControllerPackage;
    public final String mRequiredUninstallerPackage;
    public final String[] mRequiredVerifierPackages;
    public final String mRetailDemoPackage;
    public final String mSetupWizardPackage;
    public final String mSystemTextClassifierPackageName;
    public final String mWearableSensingPackage;

    public KnownPackages(
            DefaultAppProvider defaultAppProvider,
            String str,
            String str2,
            String str3,
            String[] strArr,
            String str4,
            String str5,
            String str6,
            String str7,
            String str8,
            String str9,
            String str10,
            String str11,
            String str12,
            String str13,
            String str14) {
        this.mDefaultAppProvider = defaultAppProvider;
        this.mRequiredInstallerPackage = str;
        this.mRequiredUninstallerPackage = str2;
        this.mSetupWizardPackage = str3;
        this.mRequiredVerifierPackages = strArr;
        this.mDefaultTextClassifierPackage = str4;
        this.mSystemTextClassifierPackageName = str5;
        this.mRequiredPermissionControllerPackage = str6;
        this.mConfiguratorPackage = str7;
        this.mIncidentReportApproverPackage = str8;
        this.mAmbientContextDetectionPackage = str9;
        this.mWearableSensingPackage = str10;
        this.mAppPredictionServicePackage = str11;
        this.mRetailDemoPackage = str12;
        this.mOverlayConfigSignaturePackage = str13;
        this.mRecentsPackage = str14;
    }

    public final String[] getKnownPackageNames(Computer computer, int i, int i2) {
        switch (i) {
            case 0:
                return new String[] {"android"};
            case 1:
                return computer.filterOnlySystemPackages(this.mSetupWizardPackage);
            case 2:
                return computer.filterOnlySystemPackages(this.mRequiredInstallerPackage);
            case 3:
                return computer.filterOnlySystemPackages(this.mRequiredUninstallerPackage);
            case 4:
                return computer.filterOnlySystemPackages(this.mRequiredVerifierPackages);
            case 5:
                return new String[] {
                    this.mDefaultAppProvider.getRoleHolder(i2, "android.app.role.BROWSER")
                };
            case 6:
                return computer.filterOnlySystemPackages(
                        this.mDefaultTextClassifierPackage, this.mSystemTextClassifierPackageName);
            case 7:
                return computer.filterOnlySystemPackages(this.mRequiredPermissionControllerPackage);
            case 8:
            case 9:
            case 14:
            default:
                return (String[]) ArrayUtils.emptyArray(String.class);
            case 10:
                return computer.filterOnlySystemPackages(this.mConfiguratorPackage);
            case 11:
                return computer.filterOnlySystemPackages(this.mIncidentReportApproverPackage);
            case 12:
                return computer.filterOnlySystemPackages(this.mAppPredictionServicePackage);
            case 13:
                return computer.filterOnlySystemPackages(this.mOverlayConfigSignaturePackage);
            case 15:
                return computer.filterOnlySystemPackages("com.android.companiondevicemanager");
            case 16:
                String str = this.mRetailDemoPackage;
                return TextUtils.isEmpty(str)
                        ? (String[]) ArrayUtils.emptyArray(String.class)
                        : new String[] {str};
            case 17:
                return computer.filterOnlySystemPackages(this.mRecentsPackage);
            case 18:
                return computer.filterOnlySystemPackages(this.mAmbientContextDetectionPackage);
            case 19:
                return computer.filterOnlySystemPackages(this.mWearableSensingPackage);
        }
    }
}
