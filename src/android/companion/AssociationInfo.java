package android.companion;

import android.annotation.SystemApi;
import android.graphics.drawable.Icon;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.MacAddress;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.hidden_from_bootclasspath.android.companion.Flags;
import java.util.Date;
import java.util.Objects;

/* loaded from: classes.dex */
public final class AssociationInfo implements Parcelable {
    public static final Parcelable.Creator<AssociationInfo> CREATOR = new Parcelable.Creator<AssociationInfo>() { // from class: android.companion.AssociationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssociationInfo[] newArray(int i) {
            return new AssociationInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssociationInfo createFromParcel(Parcel parcel) {
            return new AssociationInfo(parcel);
        }
    };
    private static final String LAST_TIME_CONNECTED_NONE = "None";
    private final AssociatedDevice mAssociatedDevice;
    private final Icon mDeviceIcon;
    private final DeviceId mDeviceId;
    private final MacAddress mDeviceMacAddress;
    private final String mDeviceProfile;
    private final CharSequence mDisplayName;
    private final int mId;
    private final long mLastTimeConnectedMs;
    private final boolean mNotifyOnDeviceNearby;
    private final String mPackageName;
    private final boolean mPending;
    private final boolean mRevoked;
    private final boolean mSelfManaged;
    private final int mSystemDataSyncFlags;
    private final long mTimeApprovedMs;
    private final int mUserId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AssociationInfo(int i, int i2, String str, MacAddress macAddress, CharSequence charSequence, String str2, AssociatedDevice associatedDevice, boolean z, boolean z2, boolean z3, boolean z4, long j, long j2, int i3, Icon icon, DeviceId deviceId) {
        if (i <= 0) {
            throw new IllegalArgumentException("Association ID should be greater than 0");
        }
        if (macAddress == null && charSequence == null) {
            throw new IllegalArgumentException("MAC address and the Display Name must NOT be null at the same time");
        }
        this.mId = i;
        this.mUserId = i2;
        this.mPackageName = str;
        this.mDeviceMacAddress = macAddress;
        this.mDisplayName = charSequence;
        this.mDeviceProfile = str2;
        this.mAssociatedDevice = associatedDevice;
        this.mSelfManaged = z;
        this.mNotifyOnDeviceNearby = z2;
        this.mRevoked = z3;
        this.mPending = z4;
        this.mTimeApprovedMs = j;
        this.mLastTimeConnectedMs = j2;
        this.mSystemDataSyncFlags = i3;
        this.mDeviceIcon = icon;
        this.mDeviceId = deviceId;
    }

    public int getId() {
        return this.mId;
    }

    public int getUserId() {
        return this.mUserId;
    }

    @SystemApi
    public String getPackageName() {
        return this.mPackageName;
    }

    public DeviceId getDeviceId() {
        return this.mDeviceId;
    }

    public MacAddress getDeviceMacAddress() {
        return this.mDeviceMacAddress;
    }

    public String getDeviceMacAddressAsString() {
        MacAddress macAddress = this.mDeviceMacAddress;
        if (macAddress != null) {
            return macAddress.toString().toUpperCase();
        }
        return null;
    }

    public CharSequence getDisplayName() {
        return this.mDisplayName;
    }

    public String getDeviceProfile() {
        return this.mDeviceProfile;
    }

    public AssociatedDevice getAssociatedDevice() {
        return this.mAssociatedDevice;
    }

    public boolean isSelfManaged() {
        return this.mSelfManaged;
    }

    public boolean isNotifyOnDeviceNearby() {
        return this.mNotifyOnDeviceNearby;
    }

    public long getTimeApprovedMs() {
        return this.mTimeApprovedMs;
    }

    public boolean belongsToPackage(int i, String str) {
        return this.mUserId == i && Objects.equals(this.mPackageName, str);
    }

    public boolean isRevoked() {
        return this.mRevoked;
    }

    public boolean isPending() {
        return this.mPending;
    }

    public boolean isActive() {
        return (this.mRevoked || this.mPending) ? false : true;
    }

    public long getLastTimeConnectedMs() {
        return this.mLastTimeConnectedMs;
    }

    public int getSystemDataSyncFlags() {
        return this.mSystemDataSyncFlags;
    }

    public Icon getDeviceIcon() {
        return this.mDeviceIcon;
    }

    public boolean isLinkedTo(String str) {
        if (this.mSelfManaged || str == null) {
            return false;
        }
        try {
            return MacAddress.fromString(str).equals(this.mDeviceMacAddress);
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public boolean shouldBindWhenPresent() {
        return this.mNotifyOnDeviceNearby || this.mSelfManaged;
    }

    public String toShortString() {
        StringBuilder sb = new StringBuilder("id=");
        sb.append(this.mId);
        if (this.mDeviceMacAddress != null) {
            sb.append(", addr=");
            sb.append(getDeviceMacAddressAsString());
        }
        if (this.mSelfManaged) {
            sb.append(", self-managed");
        }
        sb.append(", pkg=u");
        sb.append(this.mUserId);
        sb.append('/');
        sb.append(this.mPackageName);
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Association{mId=");
        sb.append(this.mId);
        sb.append(", mUserId=");
        sb.append(this.mUserId);
        sb.append(", mPackageName='");
        sb.append(this.mPackageName);
        sb.append("', mDeviceMacAddress=");
        sb.append(this.mDeviceMacAddress);
        sb.append(", mDisplayName='");
        sb.append((Object) this.mDisplayName);
        sb.append("', mDeviceProfile='");
        sb.append(this.mDeviceProfile);
        sb.append("', mSelfManaged=");
        sb.append(this.mSelfManaged);
        sb.append(", mAssociatedDevice=");
        sb.append(this.mAssociatedDevice);
        sb.append(", mNotifyOnDeviceNearby=");
        sb.append(this.mNotifyOnDeviceNearby);
        sb.append(", mRevoked=");
        sb.append(this.mRevoked);
        sb.append(", mPending=");
        sb.append(this.mPending);
        sb.append(", mTimeApprovedMs=");
        sb.append(new Date(this.mTimeApprovedMs));
        sb.append(", mLastTimeConnectedMs=");
        sb.append(this.mLastTimeConnectedMs == Long.MAX_VALUE ? "None" : new Date(this.mLastTimeConnectedMs));
        sb.append(", mSystemDataSyncFlags=");
        sb.append(this.mSystemDataSyncFlags);
        sb.append(", mDeviceId='");
        sb.append(this.mDeviceId);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AssociationInfo)) {
            return false;
        }
        AssociationInfo associationInfo = (AssociationInfo) obj;
        return this.mId == associationInfo.mId && this.mUserId == associationInfo.mUserId && this.mSelfManaged == associationInfo.mSelfManaged && this.mNotifyOnDeviceNearby == associationInfo.mNotifyOnDeviceNearby && this.mRevoked == associationInfo.mRevoked && this.mPending == associationInfo.mPending && this.mTimeApprovedMs == associationInfo.mTimeApprovedMs && this.mLastTimeConnectedMs == associationInfo.mLastTimeConnectedMs && Objects.equals(this.mPackageName, associationInfo.mPackageName) && Objects.equals(this.mDeviceMacAddress, associationInfo.mDeviceMacAddress) && Objects.equals(this.mDisplayName, associationInfo.mDisplayName) && Objects.equals(this.mDeviceProfile, associationInfo.mDeviceProfile) && Objects.equals(this.mAssociatedDevice, associationInfo.mAssociatedDevice) && this.mSystemDataSyncFlags == associationInfo.mSystemDataSyncFlags && isSameIcon(this.mDeviceIcon, associationInfo.mDeviceIcon) && Objects.equals(this.mDeviceId, associationInfo.mDeviceId);
    }

    private boolean isSameIcon(Icon icon, Icon icon2) {
        if (icon == null && icon2 == null) {
            return true;
        }
        return (icon == null || icon2 == null || !icon.getBitmap().sameAs(icon2.getBitmap())) ? false : true;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mId), Integer.valueOf(this.mUserId), this.mPackageName, this.mDeviceMacAddress, this.mDisplayName, this.mDeviceProfile, this.mAssociatedDevice, Boolean.valueOf(this.mSelfManaged), Boolean.valueOf(this.mNotifyOnDeviceNearby), Boolean.valueOf(this.mRevoked), Boolean.valueOf(this.mPending), Long.valueOf(this.mTimeApprovedMs), Long.valueOf(this.mLastTimeConnectedMs), Integer.valueOf(this.mSystemDataSyncFlags), this.mDeviceIcon, this.mDeviceId);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mUserId);
        parcel.writeString(this.mPackageName);
        parcel.writeTypedObject(this.mDeviceMacAddress, 0);
        parcel.writeCharSequence(this.mDisplayName);
        parcel.writeString(this.mDeviceProfile);
        parcel.writeTypedObject(this.mAssociatedDevice, 0);
        parcel.writeBoolean(this.mSelfManaged);
        parcel.writeBoolean(this.mNotifyOnDeviceNearby);
        parcel.writeBoolean(this.mRevoked);
        parcel.writeBoolean(this.mPending);
        parcel.writeLong(this.mTimeApprovedMs);
        parcel.writeLong(this.mLastTimeConnectedMs);
        parcel.writeInt(this.mSystemDataSyncFlags);
        if (Flags.associationDeviceIcon() && this.mDeviceIcon != null) {
            parcel.writeInt(1);
            this.mDeviceIcon.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (Flags.associationTag() && this.mDeviceId != null) {
            parcel.writeInt(1);
            parcel.writeTypedObject(this.mDeviceId, i);
        } else {
            parcel.writeInt(0);
        }
    }

    private AssociationInfo(Parcel parcel) {
        this.mId = parcel.readInt();
        this.mUserId = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mDeviceMacAddress = (MacAddress) parcel.readTypedObject(MacAddress.CREATOR);
        this.mDisplayName = parcel.readCharSequence();
        this.mDeviceProfile = parcel.readString();
        this.mAssociatedDevice = (AssociatedDevice) parcel.readTypedObject(AssociatedDevice.CREATOR);
        this.mSelfManaged = parcel.readBoolean();
        this.mNotifyOnDeviceNearby = parcel.readBoolean();
        this.mRevoked = parcel.readBoolean();
        this.mPending = parcel.readBoolean();
        this.mTimeApprovedMs = parcel.readLong();
        this.mLastTimeConnectedMs = parcel.readLong();
        this.mSystemDataSyncFlags = parcel.readInt();
        int i = parcel.readInt();
        if (Flags.associationDeviceIcon() && i == 1) {
            this.mDeviceIcon = Icon.CREATOR.createFromParcel(parcel);
        } else {
            this.mDeviceIcon = null;
        }
        int i2 = parcel.readInt();
        if (Flags.associationTag() && i2 == 1) {
            this.mDeviceId = (DeviceId) parcel.readTypedObject(DeviceId.CREATOR);
        } else {
            this.mDeviceId = null;
        }
    }

    public static final class Builder {
        private AssociatedDevice mAssociatedDevice;
        private Icon mDeviceIcon;
        private DeviceId mDeviceId;
        private MacAddress mDeviceMacAddress;
        private String mDeviceProfile;
        private CharSequence mDisplayName;
        private final int mId;
        private long mLastTimeConnectedMs;
        private boolean mNotifyOnDeviceNearby;
        private final String mPackageName;
        private boolean mPending;
        private boolean mRevoked;
        private boolean mSelfManaged;
        private int mSystemDataSyncFlags;
        private long mTimeApprovedMs;
        private final int mUserId;

        public Builder(int i, int i2, String str) {
            this.mId = i;
            this.mUserId = i2;
            this.mPackageName = str;
        }

        public Builder(AssociationInfo associationInfo) {
            this.mId = associationInfo.mId;
            this.mUserId = associationInfo.mUserId;
            this.mPackageName = associationInfo.mPackageName;
            this.mDeviceMacAddress = associationInfo.mDeviceMacAddress;
            this.mDisplayName = associationInfo.mDisplayName;
            this.mDeviceProfile = associationInfo.mDeviceProfile;
            this.mAssociatedDevice = associationInfo.mAssociatedDevice;
            this.mSelfManaged = associationInfo.mSelfManaged;
            this.mNotifyOnDeviceNearby = associationInfo.mNotifyOnDeviceNearby;
            this.mRevoked = associationInfo.mRevoked;
            this.mPending = associationInfo.mPending;
            this.mTimeApprovedMs = associationInfo.mTimeApprovedMs;
            this.mLastTimeConnectedMs = associationInfo.mLastTimeConnectedMs;
            this.mSystemDataSyncFlags = associationInfo.mSystemDataSyncFlags;
            this.mDeviceIcon = associationInfo.mDeviceIcon;
            this.mDeviceId = associationInfo.mDeviceId;
        }

        public Builder(int i, int i2, String str, AssociationInfo associationInfo) {
            this.mId = i;
            this.mUserId = i2;
            this.mPackageName = str;
            this.mDeviceMacAddress = associationInfo.mDeviceMacAddress;
            this.mDisplayName = associationInfo.mDisplayName;
            this.mDeviceProfile = associationInfo.mDeviceProfile;
            this.mAssociatedDevice = associationInfo.mAssociatedDevice;
            this.mSelfManaged = associationInfo.mSelfManaged;
            this.mNotifyOnDeviceNearby = associationInfo.mNotifyOnDeviceNearby;
            this.mRevoked = associationInfo.mRevoked;
            this.mPending = associationInfo.mPending;
            this.mTimeApprovedMs = associationInfo.mTimeApprovedMs;
            this.mLastTimeConnectedMs = associationInfo.mLastTimeConnectedMs;
            this.mSystemDataSyncFlags = associationInfo.mSystemDataSyncFlags;
            this.mDeviceIcon = associationInfo.mDeviceIcon;
            this.mDeviceId = associationInfo.mDeviceId;
        }

        public Builder setDeviceId(DeviceId deviceId) {
            this.mDeviceId = deviceId;
            return this;
        }

        public Builder setDeviceMacAddress(MacAddress macAddress) {
            this.mDeviceMacAddress = macAddress;
            return this;
        }

        public Builder setDisplayName(CharSequence charSequence) {
            this.mDisplayName = charSequence;
            return this;
        }

        public Builder setDeviceProfile(String str) {
            this.mDeviceProfile = str;
            return this;
        }

        public Builder setAssociatedDevice(AssociatedDevice associatedDevice) {
            this.mAssociatedDevice = associatedDevice;
            return this;
        }

        public Builder setSelfManaged(boolean z) {
            this.mSelfManaged = z;
            return this;
        }

        public Builder setNotifyOnDeviceNearby(boolean z) {
            this.mNotifyOnDeviceNearby = z;
            return this;
        }

        public Builder setRevoked(boolean z) {
            this.mRevoked = z;
            return this;
        }

        public Builder setPending(boolean z) {
            this.mPending = z;
            return this;
        }

        public Builder setTimeApproved(long j) {
            if (j < 0) {
                throw new IllegalArgumentException("timeApprovedMs must be positive. Was given (" + j + NavigationBarInflaterView.KEY_CODE_END);
            }
            this.mTimeApprovedMs = j;
            return this;
        }

        public Builder setLastTimeConnected(long j) {
            if (j < 0) {
                throw new IllegalArgumentException("lastTimeConnectedMs must not be negative! (Given " + j + " )");
            }
            this.mLastTimeConnectedMs = j;
            return this;
        }

        public Builder setSystemDataSyncFlags(int i) {
            this.mSystemDataSyncFlags = i;
            return this;
        }

        public Builder setDeviceIcon(Icon icon) {
            this.mDeviceIcon = icon;
            return this;
        }

        public AssociationInfo build() {
            if (this.mId <= 0) {
                throw new IllegalArgumentException("Association ID should be greater than 0");
            }
            if (this.mDeviceMacAddress == null && this.mDisplayName == null) {
                throw new IllegalArgumentException("MAC address and the display name must NOT be null at the same time");
            }
            return new AssociationInfo(this.mId, this.mUserId, this.mPackageName, this.mDeviceMacAddress, this.mDisplayName, this.mDeviceProfile, this.mAssociatedDevice, this.mSelfManaged, this.mNotifyOnDeviceNearby, this.mRevoked, this.mPending, this.mTimeApprovedMs, this.mLastTimeConnectedMs, this.mSystemDataSyncFlags, this.mDeviceIcon, this.mDeviceId);
        }
    }
}
