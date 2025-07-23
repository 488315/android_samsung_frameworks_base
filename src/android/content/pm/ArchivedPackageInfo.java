package android.content.pm;

import android.annotation.NonNull;
import com.android.internal.util.AnnotationValidations;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ArchivedPackageInfo {
    private String mDefaultToDeviceProtectedStorage;
    private List<ArchivedActivityInfo> mLauncherActivities;
    private String mPackageName;
    private String mRequestLegacyExternalStorage;
    private SigningInfo mSigningInfo;
    private int mTargetSdkVersion;
    private String mUserDataFragile;
    private int mVersionCode;
    private int mVersionCodeMajor;

    @Deprecated
    private void __metadata() {
    }

    public ArchivedPackageInfo(String str, SigningInfo signingInfo, List<ArchivedActivityInfo> list) {
        this.mVersionCode = 0;
        this.mVersionCodeMajor = 0;
        this.mTargetSdkVersion = 0;
        Objects.requireNonNull(str);
        Objects.requireNonNull(signingInfo);
        Objects.requireNonNull(list);
        this.mPackageName = str;
        this.mSigningInfo = signingInfo;
        this.mLauncherActivities = list;
    }

    public ArchivedPackageInfo(ArchivedPackageParcel archivedPackageParcel) {
        this.mVersionCode = 0;
        this.mVersionCodeMajor = 0;
        this.mTargetSdkVersion = 0;
        this.mPackageName = archivedPackageParcel.packageName;
        this.mSigningInfo = new SigningInfo(archivedPackageParcel.signingDetails);
        this.mVersionCode = archivedPackageParcel.versionCode;
        this.mVersionCodeMajor = archivedPackageParcel.versionCodeMajor;
        this.mTargetSdkVersion = archivedPackageParcel.targetSdkVersion;
        this.mDefaultToDeviceProtectedStorage = archivedPackageParcel.defaultToDeviceProtectedStorage;
        this.mRequestLegacyExternalStorage = archivedPackageParcel.requestLegacyExternalStorage;
        this.mUserDataFragile = archivedPackageParcel.userDataFragile;
        this.mLauncherActivities = new ArrayList();
        if (archivedPackageParcel.archivedActivities != null) {
            for (ArchivedActivityParcel archivedActivityParcel : archivedPackageParcel.archivedActivities) {
                this.mLauncherActivities.add(new ArchivedActivityInfo(archivedActivityParcel));
            }
        }
    }

    ArchivedPackageParcel getParcel() {
        ArchivedPackageParcel archivedPackageParcel = new ArchivedPackageParcel();
        archivedPackageParcel.packageName = this.mPackageName;
        archivedPackageParcel.signingDetails = this.mSigningInfo.getSigningDetails();
        archivedPackageParcel.versionCode = this.mVersionCode;
        archivedPackageParcel.versionCodeMajor = this.mVersionCodeMajor;
        archivedPackageParcel.targetSdkVersion = this.mTargetSdkVersion;
        archivedPackageParcel.defaultToDeviceProtectedStorage = this.mDefaultToDeviceProtectedStorage;
        archivedPackageParcel.requestLegacyExternalStorage = this.mRequestLegacyExternalStorage;
        archivedPackageParcel.userDataFragile = this.mUserDataFragile;
        archivedPackageParcel.archivedActivities = new ArchivedActivityParcel[this.mLauncherActivities.size()];
        int length = archivedPackageParcel.archivedActivities.length;
        for (int i = 0; i < length; i++) {
            archivedPackageParcel.archivedActivities[i] = this.mLauncherActivities.get(i).getParcel();
        }
        return archivedPackageParcel;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public SigningInfo getSigningInfo() {
        return this.mSigningInfo;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public int getVersionCodeMajor() {
        return this.mVersionCodeMajor;
    }

    public int getTargetSdkVersion() {
        return this.mTargetSdkVersion;
    }

    public String getDefaultToDeviceProtectedStorage() {
        return this.mDefaultToDeviceProtectedStorage;
    }

    public String getRequestLegacyExternalStorage() {
        return this.mRequestLegacyExternalStorage;
    }

    public String getUserDataFragile() {
        return this.mUserDataFragile;
    }

    public List<ArchivedActivityInfo> getLauncherActivities() {
        return this.mLauncherActivities;
    }

    public ArchivedPackageInfo setPackageName(String str) {
        this.mPackageName = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        return this;
    }

    public ArchivedPackageInfo setSigningInfo(SigningInfo signingInfo) {
        this.mSigningInfo = signingInfo;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) signingInfo);
        return this;
    }

    public ArchivedPackageInfo setVersionCode(int i) {
        this.mVersionCode = i;
        return this;
    }

    public ArchivedPackageInfo setVersionCodeMajor(int i) {
        this.mVersionCodeMajor = i;
        return this;
    }

    public ArchivedPackageInfo setTargetSdkVersion(int i) {
        this.mTargetSdkVersion = i;
        return this;
    }

    public ArchivedPackageInfo setDefaultToDeviceProtectedStorage(String str) {
        this.mDefaultToDeviceProtectedStorage = str;
        return this;
    }

    public ArchivedPackageInfo setRequestLegacyExternalStorage(String str) {
        this.mRequestLegacyExternalStorage = str;
        return this;
    }

    public ArchivedPackageInfo setUserDataFragile(String str) {
        this.mUserDataFragile = str;
        return this;
    }

    public ArchivedPackageInfo setLauncherActivities(List<ArchivedActivityInfo> list) {
        this.mLauncherActivities = list;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list);
        return this;
    }
}
