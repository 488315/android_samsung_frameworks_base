package android.content.pm;

import android.content.res.Resources;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.multiuser.Flags;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.DebugUtils;
import com.android.internal.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class UserInfo implements Parcelable {
    public static final int ATTR_ADMIN_LOCKED = 8;
    public static final int ATTR_DEVICE_COMPROMISED = 4;
    public static final int ATTR_EXT_SDCARD = 128;
    public static final int ATTR_LICENSE_LOCKED = 16;
    public static final int ATTR_NEED_SETUP_CREDENTIAL = 536870912;
    public static final int ATTR_NONE = 0;
    public static final int ATTR_PREMIUM_CONTAINER = 268435456;
    public static final int ATTR_PWD_EXPIRED = 32;
    public static final int ATTR_RESET_ON_BOOT = 64;
    public static final int ATTR_SUPER_LOCKED = 12;
    public static final int ATTR_TRUST_AGENT_UI_ENABLED = 256;
    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() { // from class: android.content.pm.UserInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserInfo createFromParcel(Parcel parcel) {
            return new UserInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserInfo[] newArray(int i) {
            return new UserInfo[i];
        }
    };
    public static final int FLAG_ADMIN = 2;
    public static final int FLAG_BMODE = 134217728;
    public static final int FLAG_BMODE_LEGACY = 65536;

    @Deprecated
    public static final int FLAG_DEMO = 512;
    public static final int FLAG_DIGITAL_LEGACY_MODE = 16777216;
    public static final int FLAG_DISABLED = 64;
    public static final int FLAG_DUALAPP_PROFILE = 536870912;
    public static final int FLAG_DUAL_DAR_CUSTOM_CRYPTO = 67108864;
    public static final int FLAG_DUAL_DAR_SAMSUNG_CRYPTO = 33554432;
    public static final int FLAG_EPHEMERAL = 256;
    public static final int FLAG_EPHEMERAL_ON_CREATE = 8192;
    public static final int FLAG_FIRST_CONTAINER = 1048576;
    public static final int FLAG_FOR_TESTING = 32768;
    public static final int FLAG_FULL = 1024;

    @Deprecated
    public static final int FLAG_GUEST = 4;
    public static final int FLAG_INITIALIZED = 16;
    public static final int FLAG_KNOX_APPSEPARATION = 1073741824;
    public static final int FLAG_KNOX_WORKSPACE = 268435456;
    public static final int FLAG_MAIN = 16384;
    public static final int FLAG_MAINTENANCE_MODE = 524288;

    @Deprecated
    public static final int FLAG_MANAGED_PROFILE = 32;

    @Deprecated
    public static final int FLAG_PRIMARY = 1;
    public static final int FLAG_PROFILE = 4096;
    public static final int FLAG_QUIET_MODE = 128;

    @Deprecated
    public static final int FLAG_RESTRICTED = 8;
    public static final int FLAG_SDP_NOT_SUPPORTED_SECURE_FOLDER = 262144;
    public static final int FLAG_SECURE_FOLDER = 131072;
    public static final int FLAG_SYSTEM = 2048;
    public static final int FLAG_VIRTUAL_USER = Integer.MIN_VALUE;
    public static final int NO_PROFILE_GROUP_ID = -10000;
    private int attributes;
    public boolean convertedFromPreCreated;
    public long creationTime;
    public int flags;
    public boolean guestToRemove;
    public String iconPath;
    public int id;
    public String lastLoggedInFingerprint;
    public long lastLoggedInTime;
    public String name;
    public boolean partial;
    public boolean preCreated;
    public int profileBadge;
    public int profileGroupId;
    public int restrictedProfileParentId;
    public int serialNumber;
    public String userType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface UserInfoFlag {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UserInfo(int i, String str, int i2) {
        this(i, str, null, i2);
    }

    public UserInfo(int i, String str, String str2, int i2) {
        this(i, str, str2, i2, getDefaultUserType(i2));
    }

    public UserInfo(int i, String str, String str2, int i2, String str3) {
        this.id = i;
        this.name = str;
        this.flags = i2;
        this.userType = str3;
        this.iconPath = str2;
        this.profileGroupId = -10000;
        this.restrictedProfileParentId = -10000;
        this.attributes = 0;
    }

    public static String getDefaultUserType(int i) {
        if ((i & 2048) != 0) {
            throw new IllegalArgumentException("Cannot getDefaultUserType for flags " + Integer.toHexString(i) + " because it corresponds to a SYSTEM user type.");
        }
        int i2 = i & 556;
        if (i2 == 0) {
            return UserManager.USER_TYPE_FULL_SECONDARY;
        }
        if (i2 == 4) {
            return UserManager.USER_TYPE_FULL_GUEST;
        }
        if (i2 == 8) {
            return UserManager.USER_TYPE_FULL_RESTRICTED;
        }
        if (i2 == 32) {
            return UserManager.USER_TYPE_PROFILE_MANAGED;
        }
        if (i2 != 512) {
            throw new IllegalArgumentException("Cannot getDefaultUserType for flags " + Integer.toHexString(i) + " because it doesn't correspond to a valid user type.");
        }
        return UserManager.USER_TYPE_FULL_DEMO;
    }

    @Deprecated
    public boolean isPrimary() {
        return (this.flags & 1) == 1;
    }

    public boolean isAdmin() {
        return (this.flags & 2) == 2;
    }

    public boolean isGuest() {
        return UserManager.isUserTypeGuest(this.userType);
    }

    public boolean isRestricted() {
        return UserManager.isUserTypeRestricted(this.userType);
    }

    public boolean isProfile() {
        return (this.flags & 4096) != 0;
    }

    public boolean isManagedProfile() {
        return UserManager.isUserTypeManagedProfile(this.userType);
    }

    public boolean isCloneProfile() {
        return UserManager.isUserTypeCloneProfile(this.userType);
    }

    public boolean isCommunalProfile() {
        return UserManager.isUserTypeCommunalProfile(this.userType);
    }

    public boolean isPrivateProfile() {
        return UserManager.isUserTypePrivateProfile(this.userType);
    }

    public boolean isSupervisingProfile() {
        return UserManager.isUserTypeSupervisingProfile(this.userType);
    }

    public boolean isEnabled() {
        return (this.flags & 64) != 64;
    }

    public boolean isQuietModeEnabled() {
        return (this.flags & 128) == 128;
    }

    public boolean isEphemeral() {
        return (this.flags & 256) == 256;
    }

    public boolean isForTesting() {
        return (this.flags & 32768) == 32768;
    }

    public boolean isInitialized() {
        return (this.flags & 16) == 16;
    }

    public boolean isDemo() {
        return UserManager.isUserTypeDemo(this.userType) || (this.flags & 512) != 0;
    }

    public boolean isDigitalLegacyMode() {
        return (this.flags & 16777216) == 16777216;
    }

    public boolean isBMode() {
        int i = this.flags;
        return (i & 134217728) == 134217728 || (i & 65536) == 65536;
    }

    public boolean isFull() {
        return (this.flags & 1024) == 1024;
    }

    public boolean isMain() {
        return (this.flags & 16384) == 16384;
    }

    public boolean isDualAppProfile() {
        return (this.flags & 536870912) == 536870912;
    }

    public boolean supportsSwitchTo() {
        if (this.partial || !isEnabled() || this.preCreated) {
            return false;
        }
        return isFull() || canSwitchToHeadlessSystemUser();
    }

    private boolean canSwitchToHeadlessSystemUser() {
        return UserManager.USER_TYPE_SYSTEM_HEADLESS.equals(this.userType) && Resources.getSystem().getBoolean(R.bool.config_canSwitchToHeadlessSystemUser);
    }

    @Deprecated
    public boolean supportsSwitchToByUser() {
        return supportsSwitchTo();
    }

    public boolean canHaveProfile() {
        if (!isFull() || isProfile() || isGuest() || isRestricted() || isDemo()) {
            return false;
        }
        return isMain() || Flags.profilesForAll();
    }

    public boolean isKnoxWorkspace() {
        return isManagedProfile();
    }

    public boolean isUserTypeAppSeparation() {
        return (this.flags & 1073741824) == 1073741824;
    }

    public boolean isSecureFolder() {
        return (this.flags & 131072) == 131072;
    }

    public boolean isFirstContainer() {
        return isManagedProfile();
    }

    public boolean isSuperLocked() {
        return (this.flags & 100663296) > 0 ? (this.attributes & 28) > 0 : (this.attributes & 12) > 0;
    }

    public boolean isAdminLocked() {
        return (this.attributes & 8) > 0;
    }

    public boolean isLicenseLocked() {
        return (this.attributes & 16) > 0;
    }

    public boolean isDeviceCompromised() {
        return (this.attributes & 4) > 0;
    }

    public boolean isPremiumContainer() {
        return (this.attributes & 268435456) == 268435456;
    }

    public boolean needSetupCredential() {
        return (this.attributes & 536870912) == 536870912;
    }

    public boolean isVirtualUser() {
        return (this.flags & Integer.MIN_VALUE) == Integer.MIN_VALUE;
    }

    public boolean isSdpNotSupportedSecureFolder() {
        return (this.flags & 262144) == 262144;
    }

    @Deprecated
    public UserInfo() {
    }

    public UserInfo(UserInfo userInfo) {
        this.name = userInfo.name;
        this.iconPath = userInfo.iconPath;
        this.id = userInfo.id;
        this.flags = userInfo.flags;
        this.userType = userInfo.userType;
        this.serialNumber = userInfo.serialNumber;
        this.creationTime = userInfo.creationTime;
        this.lastLoggedInTime = userInfo.lastLoggedInTime;
        this.lastLoggedInFingerprint = userInfo.lastLoggedInFingerprint;
        this.partial = userInfo.partial;
        this.preCreated = userInfo.preCreated;
        this.convertedFromPreCreated = userInfo.convertedFromPreCreated;
        this.profileGroupId = userInfo.profileGroupId;
        this.restrictedProfileParentId = userInfo.restrictedProfileParentId;
        this.guestToRemove = userInfo.guestToRemove;
        this.profileBadge = userInfo.profileBadge;
        this.attributes = userInfo.attributes;
    }

    public UserHandle getUserHandle() {
        return UserHandle.of(this.id);
    }

    public String toString() {
        return "UserInfo{" + this.id + ":" + this.name + ":" + Integer.toHexString(this.flags) + "}";
    }

    public String toFullString() {
        StringBuilder sb = new StringBuilder("UserInfo[id=");
        sb.append(this.id);
        sb.append(", name=");
        sb.append(this.name);
        sb.append(", type=");
        sb.append(this.userType);
        sb.append(", flags=");
        sb.append(flagsToString(this.flags));
        sb.append(this.preCreated ? " (pre-created)" : "");
        sb.append(this.convertedFromPreCreated ? " (converted)" : "");
        sb.append(this.partial ? " (partial)" : "");
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    public static String flagsToString(int i) {
        return DebugUtils.flagsToString(UserInfo.class, "FLAG_", i);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString8(this.name);
        parcel.writeString8(this.iconPath);
        parcel.writeInt(this.flags);
        parcel.writeString8(this.userType);
        parcel.writeInt(this.serialNumber);
        parcel.writeLong(this.creationTime);
        parcel.writeLong(this.lastLoggedInTime);
        parcel.writeString8(this.lastLoggedInFingerprint);
        parcel.writeBoolean(this.partial);
        parcel.writeBoolean(this.preCreated);
        parcel.writeInt(this.profileGroupId);
        parcel.writeBoolean(this.guestToRemove);
        parcel.writeInt(this.restrictedProfileParentId);
        parcel.writeInt(this.profileBadge);
        parcel.writeInt(this.attributes);
    }

    private UserInfo(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString8();
        this.iconPath = parcel.readString8();
        this.flags = parcel.readInt();
        this.userType = parcel.readString8();
        this.serialNumber = parcel.readInt();
        this.creationTime = parcel.readLong();
        this.lastLoggedInTime = parcel.readLong();
        this.lastLoggedInFingerprint = parcel.readString8();
        this.partial = parcel.readBoolean();
        this.preCreated = parcel.readBoolean();
        this.profileGroupId = parcel.readInt();
        this.guestToRemove = parcel.readBoolean();
        this.restrictedProfileParentId = parcel.readInt();
        this.profileBadge = parcel.readInt();
        this.attributes = parcel.readInt();
    }

    public int getAttributes() {
        return this.attributes;
    }

    public void setAttributes(int i) {
        this.attributes = i;
    }
}
