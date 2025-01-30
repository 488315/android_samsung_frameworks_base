package android.content.p002pm;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes.dex */
public class PackageInfo implements Parcelable {
  public static final Parcelable.Creator<PackageInfo> CREATOR =
      new Parcelable.Creator<PackageInfo>() { // from class: android.content.pm.PackageInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageInfo createFromParcel(Parcel source) {
          return new PackageInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageInfo[] newArray(int size) {
          return new PackageInfo[size];
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

  @Deprecated public Signature[] signatures;
  public SigningInfo signingInfo;
  public String[] splitNames;
  public int[] splitRevisionCodes;
  public String targetOverlayableName;

  @Deprecated public int versionCode;
  public int versionCodeMajor;
  public String versionName;

  public long getLongVersionCode() {
    return composeLongVersionCode(this.versionCodeMajor, this.versionCode);
  }

  public void setLongVersionCode(long longVersionCode) {
    this.versionCodeMajor = (int) (longVersionCode >> 32);
    this.versionCode = (int) longVersionCode;
  }

  public static long composeLongVersionCode(int major, int minor) {
    return (major << 32) | (minor & 4294967295L);
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

  public String toString() {
    return "PackageInfo{"
        + Integer.toHexString(System.identityHashCode(this))
        + " "
        + this.packageName
        + "}";
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    boolean allowSquashing = parcel.allowSquashing();
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
    parcel.restoreAllowSquashing(allowSquashing);
  }

  private PackageInfo(Parcel source) {
    this.installLocation = 1;
    this.packageName = source.readString8();
    this.splitNames = source.createString8Array();
    this.versionCode = source.readInt();
    this.versionCodeMajor = source.readInt();
    this.versionName = source.readString8();
    this.baseRevisionCode = source.readInt();
    this.splitRevisionCodes = source.createIntArray();
    this.sharedUserId = source.readString8();
    this.sharedUserLabel = source.readInt();
    int hasApp = source.readInt();
    if (hasApp != 0) {
      this.applicationInfo = ApplicationInfo.CREATOR.createFromParcel(source);
    }
    this.firstInstallTime = source.readLong();
    this.lastUpdateTime = source.readLong();
    this.gids = source.createIntArray();
    this.activities = (ActivityInfo[]) source.createTypedArray(ActivityInfo.CREATOR);
    this.receivers = (ActivityInfo[]) source.createTypedArray(ActivityInfo.CREATOR);
    this.services = (ServiceInfo[]) source.createTypedArray(ServiceInfo.CREATOR);
    this.providers = (ProviderInfo[]) source.createTypedArray(ProviderInfo.CREATOR);
    this.instrumentation =
        (InstrumentationInfo[]) source.createTypedArray(InstrumentationInfo.CREATOR);
    this.permissions = (PermissionInfo[]) source.createTypedArray(PermissionInfo.CREATOR);
    this.requestedPermissions = source.createString8Array();
    this.requestedPermissionsFlags = source.createIntArray();
    this.signatures = (Signature[]) source.createTypedArray(Signature.CREATOR);
    this.configPreferences =
        (ConfigurationInfo[]) source.createTypedArray(ConfigurationInfo.CREATOR);
    this.reqFeatures = (FeatureInfo[]) source.createTypedArray(FeatureInfo.CREATOR);
    this.featureGroups = (FeatureGroupInfo[]) source.createTypedArray(FeatureGroupInfo.CREATOR);
    this.attributions = (Attribution[]) source.createTypedArray(Attribution.CREATOR);
    this.installLocation = source.readInt();
    this.isStub = source.readInt() != 0;
    this.coreApp = source.readInt() != 0;
    this.requiredForAllUsers = source.readInt() != 0;
    this.restrictedAccountType = source.readString8();
    this.requiredAccountType = source.readString8();
    this.overlayTarget = source.readString8();
    this.overlayCategory = source.readString8();
    this.overlayPriority = source.readInt();
    this.mOverlayIsStatic = source.readBoolean();
    this.compileSdkVersion = source.readInt();
    this.compileSdkVersionCodename = source.readString8();
    int hasSigningInfo = source.readInt();
    if (hasSigningInfo != 0) {
      this.signingInfo = SigningInfo.CREATOR.createFromParcel(source);
    }
    this.isApex = source.readBoolean();
    this.isActiveApex = source.readBoolean();
  }
}
