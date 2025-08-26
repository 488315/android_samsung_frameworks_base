package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class PackageInfo implements Parcelable {
    public static final Parcelable.Creator<PackageInfo> CREATOR = new Parcelable.Creator<PackageInfo>() { // from class: android.content.pm.PackageInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageInfo createFromParcel(Parcel parcel) {
            return new PackageInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageInfo[] newArray(int i) {
            return new PackageInfo[i];
        }
    };
    public static final int INSTALL_LOCATION_AUTO = 0;
    public static final int INSTALL_LOCATION_INTERNAL_ONLY = 1;
    public static final int INSTALL_LOCATION_PREFER_EXTERNAL = 2;
    public static final int INSTALL_LOCATION_UNSPECIFIED = -1;
    public static final int REQUESTED_PERMISSION_GRANTED = 2;
    public static final int REQUESTED_PERMISSION_IMPLICIT = 4;
    public static final int REQUESTED_PERMISSION_NEVER_FOR_LOCATION = 65536;
    public static final int REQUESTED_PERMISSION_REQUIRED = 1;
    public ActivityInfo[] activities;
    public ApplicationInfo applicationInfo;
    public Attribution[] attributions;
    public int baseRevisionCode;
    public int compileSdkVersion;
    public String compileSdkVersionCodename;
    public ConfigurationInfo[] configPreferences;
    public boolean coreApp;
    public FeatureGroupInfo[] featureGroups;
    public long firstInstallTime;
    public int[] gids;
    public int installLocation;
    public InstrumentationInfo[] instrumentation;
    public boolean isActiveApex;
    public boolean isApex;
    public boolean isStub;
    public long lastUpdateTime;
    private String mApexPackageName;
    private long mArchiveTimeMillis;
    public boolean mOverlayIsStatic;
    public String overlayCategory;
    public int overlayPriority;
    public String overlayTarget;
    public String packageName;
    public PermissionInfo[] permissions;
    public ProviderInfo[] providers;
    public ActivityInfo[] receivers;
    public FeatureInfo[] reqFeatures;
    public String[] requestedPermissions;
    public int[] requestedPermissionsFlags;
    public String requiredAccountType;
    public boolean requiredForAllUsers;
    public String restrictedAccountType;
    public ServiceInfo[] services;
    public String sharedUserId;
    public int sharedUserLabel;

    @Deprecated
    public Signature[] signatures;
    public SigningInfo signingInfo;
    public String[] splitNames;
    public int[] splitRevisionCodes;
    public String targetOverlayableName;

    @Deprecated
    public int versionCode;
    public int versionCodeMajor;
    public String versionName;

    public static long composeLongVersionCode(int i, int i2) {
        return (i2 & 4294967295L) | (i << 32);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public long getLongVersionCode() {
        return composeLongVersionCode(this.versionCodeMajor, this.versionCode);
    }

    public void setLongVersionCode(long j) {
        this.versionCodeMajor = (int) (j >> 32);
        this.versionCode = (int) j;
    }

    public PackageInfo() {
        this.installLocation = 1;
    }

    public boolean isOverlayPackage() {
        return this.overlayTarget != null;
    }

    public boolean isStaticOverlayPackage() {
        return this.overlayTarget != null && this.mOverlayIsStatic;
    }

    public long getArchiveTimeMillis() {
        return this.mArchiveTimeMillis;
    }

    public void setArchiveTimeMillis(long j) {
        this.mArchiveTimeMillis = j;
    }

    public String getApexPackageName() {
        return this.mApexPackageName;
    }

    public void setApexPackageName(String str) {
        this.mApexPackageName = str;
    }

    public String toString() {
        return "PackageInfo{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.packageName + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        boolean zAllowSquashing = parcel.allowSquashing();
        parcel.writeString8(this.packageName);
        parcel.writeString8Array(this.splitNames);
        parcel.writeInt(this.versionCode);
        parcel.writeInt(this.versionCodeMajor);
        parcel.writeString8(this.versionName);
        parcel.writeInt(this.baseRevisionCode);
        parcel.writeIntArray(this.splitRevisionCodes);
        parcel.writeString8(this.sharedUserId);
        parcel.writeInt(this.sharedUserLabel);
        if (this.applicationInfo != null) {
            parcel.writeInt(1);
            this.applicationInfo.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.firstInstallTime);
        parcel.writeLong(this.lastUpdateTime);
        parcel.writeIntArray(this.gids);
        parcel.writeTypedArray(this.activities, i);
        parcel.writeTypedArray(this.receivers, i);
        parcel.writeTypedArray(this.services, i);
        parcel.writeTypedArray(this.providers, i);
        parcel.writeTypedArray(this.instrumentation, i);
        parcel.writeTypedArray(this.permissions, i);
        parcel.writeString8Array(this.requestedPermissions);
        parcel.writeIntArray(this.requestedPermissionsFlags);
        parcel.writeTypedArray(this.signatures, i);
        parcel.writeTypedArray(this.configPreferences, i);
        parcel.writeTypedArray(this.reqFeatures, i);
        parcel.writeTypedArray(this.featureGroups, i);
        parcel.writeTypedArray(this.attributions, i);
        parcel.writeInt(this.installLocation);
        parcel.writeInt(this.isStub ? 1 : 0);
        parcel.writeInt(this.coreApp ? 1 : 0);
        parcel.writeInt(this.requiredForAllUsers ? 1 : 0);
        parcel.writeString8(this.restrictedAccountType);
        parcel.writeString8(this.requiredAccountType);
        parcel.writeString8(this.overlayTarget);
        parcel.writeString8(this.overlayCategory);
        parcel.writeInt(this.overlayPriority);
        parcel.writeBoolean(this.mOverlayIsStatic);
        parcel.writeInt(this.compileSdkVersion);
        parcel.writeString8(this.compileSdkVersionCodename);
        if (this.signingInfo != null) {
            parcel.writeInt(1);
            this.signingInfo.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeBoolean(this.isApex);
        parcel.writeBoolean(this.isActiveApex);
        parcel.writeLong(this.mArchiveTimeMillis);
        if (this.mApexPackageName != null) {
            parcel.writeInt(1);
            parcel.writeString8(this.mApexPackageName);
        } else {
            parcel.writeInt(0);
        }
        parcel.restoreAllowSquashing(zAllowSquashing);
    }

    private PackageInfo(Parcel parcel) {
        this.installLocation = 1;
        this.packageName = parcel.readString8();
        this.splitNames = parcel.createString8Array();
        this.versionCode = parcel.readInt();
        this.versionCodeMajor = parcel.readInt();
        this.versionName = parcel.readString8();
        this.baseRevisionCode = parcel.readInt();
        this.splitRevisionCodes = parcel.createIntArray();
        this.sharedUserId = parcel.readString8();
        this.sharedUserLabel = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.applicationInfo = ApplicationInfo.CREATOR.createFromParcel(parcel);
        }
        this.firstInstallTime = parcel.readLong();
        this.lastUpdateTime = parcel.readLong();
        this.gids = parcel.createIntArray();
        this.activities = (ActivityInfo[]) parcel.createTypedArray(ActivityInfo.CREATOR);
        this.receivers = (ActivityInfo[]) parcel.createTypedArray(ActivityInfo.CREATOR);
        this.services = (ServiceInfo[]) parcel.createTypedArray(ServiceInfo.CREATOR);
        this.providers = (ProviderInfo[]) parcel.createTypedArray(ProviderInfo.CREATOR);
        this.instrumentation = (InstrumentationInfo[]) parcel.createTypedArray(InstrumentationInfo.CREATOR);
        this.permissions = (PermissionInfo[]) parcel.createTypedArray(PermissionInfo.CREATOR);
        this.requestedPermissions = parcel.createString8Array();
        this.requestedPermissionsFlags = parcel.createIntArray();
        this.signatures = (Signature[]) parcel.createTypedArray(Signature.CREATOR);
        this.configPreferences = (ConfigurationInfo[]) parcel.createTypedArray(ConfigurationInfo.CREATOR);
        this.reqFeatures = (FeatureInfo[]) parcel.createTypedArray(FeatureInfo.CREATOR);
        this.featureGroups = (FeatureGroupInfo[]) parcel.createTypedArray(FeatureGroupInfo.CREATOR);
        this.attributions = (Attribution[]) parcel.createTypedArray(Attribution.CREATOR);
        this.installLocation = parcel.readInt();
        this.isStub = parcel.readInt() != 0;
        this.coreApp = parcel.readInt() != 0;
        this.requiredForAllUsers = parcel.readInt() != 0;
        this.restrictedAccountType = parcel.readString8();
        this.requiredAccountType = parcel.readString8();
        this.overlayTarget = parcel.readString8();
        this.overlayCategory = parcel.readString8();
        this.overlayPriority = parcel.readInt();
        this.mOverlayIsStatic = parcel.readBoolean();
        this.compileSdkVersion = parcel.readInt();
        this.compileSdkVersionCodename = parcel.readString8();
        if (parcel.readInt() != 0) {
            this.signingInfo = SigningInfo.CREATOR.createFromParcel(parcel);
        }
        this.isApex = parcel.readBoolean();
        this.isActiveApex = parcel.readBoolean();
        this.mArchiveTimeMillis = parcel.readLong();
        if (parcel.readInt() != 0) {
            this.mApexPackageName = parcel.readString8();
        }
    }
}
