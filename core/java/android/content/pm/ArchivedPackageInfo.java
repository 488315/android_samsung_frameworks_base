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

    public ArchivedPackageInfo(
            String packageName,
            SigningInfo signingInfo,
            List<ArchivedActivityInfo> launcherActivities) {
        this.mVersionCode = 0;
        this.mVersionCodeMajor = 0;
        this.mTargetSdkVersion = 0;
        Objects.requireNonNull(packageName);
        Objects.requireNonNull(signingInfo);
        Objects.requireNonNull(launcherActivities);
        this.mPackageName = packageName;
        this.mSigningInfo = signingInfo;
        this.mLauncherActivities = launcherActivities;
    }

    public ArchivedPackageInfo(ArchivedPackageParcel parcel) {
        this.mVersionCode = 0;
        this.mVersionCodeMajor = 0;
        this.mTargetSdkVersion = 0;
        this.mPackageName = parcel.packageName;
        this.mSigningInfo = new SigningInfo(parcel.signingDetails);
        this.mVersionCode = parcel.versionCode;
        this.mVersionCodeMajor = parcel.versionCodeMajor;
        this.mTargetSdkVersion = parcel.targetSdkVersion;
        this.mDefaultToDeviceProtectedStorage = parcel.defaultToDeviceProtectedStorage;
        this.mRequestLegacyExternalStorage = parcel.requestLegacyExternalStorage;
        this.mUserDataFragile = parcel.userDataFragile;
        this.mLauncherActivities = new ArrayList();
        if (parcel.archivedActivities != null) {
            for (ArchivedActivityParcel activityParcel : parcel.archivedActivities) {
                this.mLauncherActivities.add(new ArchivedActivityInfo(activityParcel));
            }
        }
    }

    ArchivedPackageParcel getParcel() {
        ArchivedPackageParcel parcel = new ArchivedPackageParcel();
        parcel.packageName = this.mPackageName;
        parcel.signingDetails = this.mSigningInfo.getSigningDetails();
        parcel.versionCode = this.mVersionCode;
        parcel.versionCodeMajor = this.mVersionCodeMajor;
        parcel.targetSdkVersion = this.mTargetSdkVersion;
        parcel.defaultToDeviceProtectedStorage = this.mDefaultToDeviceProtectedStorage;
        parcel.requestLegacyExternalStorage = this.mRequestLegacyExternalStorage;
        parcel.userDataFragile = this.mUserDataFragile;
        parcel.archivedActivities = new ArchivedActivityParcel[this.mLauncherActivities.size()];
        int size = parcel.archivedActivities.length;
        for (int i = 0; i < size; i++) {
            parcel.archivedActivities[i] = this.mLauncherActivities.get(i).getParcel();
        }
        return parcel;
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

    public ArchivedPackageInfo setPackageName(String value) {
        this.mPackageName = value;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mPackageName);
        return this;
    }

    public ArchivedPackageInfo setSigningInfo(SigningInfo value) {
        this.mSigningInfo = value;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSigningInfo);
        return this;
    }

    public ArchivedPackageInfo setVersionCode(int value) {
        this.mVersionCode = value;
        return this;
    }

    public ArchivedPackageInfo setVersionCodeMajor(int value) {
        this.mVersionCodeMajor = value;
        return this;
    }

    public ArchivedPackageInfo setTargetSdkVersion(int value) {
        this.mTargetSdkVersion = value;
        return this;
    }

    public ArchivedPackageInfo setDefaultToDeviceProtectedStorage(String value) {
        this.mDefaultToDeviceProtectedStorage = value;
        return this;
    }

    public ArchivedPackageInfo setRequestLegacyExternalStorage(String value) {
        this.mRequestLegacyExternalStorage = value;
        return this;
    }

    public ArchivedPackageInfo setUserDataFragile(String value) {
        this.mUserDataFragile = value;
        return this;
    }

    public ArchivedPackageInfo setLauncherActivities(List<ArchivedActivityInfo> value) {
        this.mLauncherActivities = value;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mLauncherActivities);
        return this;
    }

    @Deprecated
    private void __metadata() {}
}
