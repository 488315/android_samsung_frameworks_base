package android.companion;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.MacAddress;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;

import java.util.Date;
import java.util.Objects;

public final class AssociationInfo implements Parcelable {
    public static final Parcelable.Creator<AssociationInfo> CREATOR =
            new Parcelable.Creator<
                    AssociationInfo>() { // from class: android.companion.AssociationInfo.1
                @Override // android.os.Parcelable.Creator
                public AssociationInfo[] newArray(int size) {
                    return new AssociationInfo[size];
                }

                @Override // android.os.Parcelable.Creator
                public AssociationInfo createFromParcel(Parcel in) {
                    return new AssociationInfo(in);
                }
            };
    private static final String LAST_TIME_CONNECTED_NONE = "None";
    private final AssociatedDevice mAssociatedDevice;
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
    private final String mTag;
    private final long mTimeApprovedMs;
    private final int mUserId;

    public AssociationInfo(
            int id,
            int userId,
            String packageName,
            String tag,
            MacAddress macAddress,
            CharSequence displayName,
            String deviceProfile,
            AssociatedDevice associatedDevice,
            boolean selfManaged,
            boolean notifyOnDeviceNearby,
            boolean revoked,
            boolean pending,
            long timeApprovedMs,
            long lastTimeConnectedMs,
            int systemDataSyncFlags) {
        if (id <= 0) {
            throw new IllegalArgumentException("Association ID should be greater than 0");
        }
        if (macAddress == null && displayName == null) {
            throw new IllegalArgumentException(
                    "MAC address and the Display Name must NOT be null at the same time");
        }
        this.mId = id;
        this.mUserId = userId;
        this.mPackageName = packageName;
        this.mDeviceMacAddress = macAddress;
        this.mDisplayName = displayName;
        this.mTag = tag;
        this.mDeviceProfile = deviceProfile;
        this.mAssociatedDevice = associatedDevice;
        this.mSelfManaged = selfManaged;
        this.mNotifyOnDeviceNearby = notifyOnDeviceNearby;
        this.mRevoked = revoked;
        this.mPending = pending;
        this.mTimeApprovedMs = timeApprovedMs;
        this.mLastTimeConnectedMs = lastTimeConnectedMs;
        this.mSystemDataSyncFlags = systemDataSyncFlags;
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

    public String getTag() {
        return this.mTag;
    }

    public MacAddress getDeviceMacAddress() {
        return this.mDeviceMacAddress;
    }

    public String getDeviceMacAddressAsString() {
        if (this.mDeviceMacAddress != null) {
            return this.mDeviceMacAddress.toString().toUpperCase();
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

    public boolean belongsToPackage(int userId, String packageName) {
        return this.mUserId == userId && Objects.equals(this.mPackageName, packageName);
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

    public boolean isLinkedTo(String addr) {
        if (this.mSelfManaged || addr == null) {
            return false;
        }
        try {
            MacAddress macAddress = MacAddress.fromString(addr);
            return macAddress.equals(this.mDeviceMacAddress);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean shouldBindWhenPresent() {
        return this.mNotifyOnDeviceNearby || this.mSelfManaged;
    }

    public String toShortString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id=").append(this.mId);
        if (this.mDeviceMacAddress != null) {
            sb.append(", addr=").append(getDeviceMacAddressAsString());
        }
        if (this.mSelfManaged) {
            sb.append(", self-managed");
        }
        sb.append(", pkg=u").append(this.mUserId).append('/').append(this.mPackageName);
        return sb.toString();
    }

    public String toString() {
        return "Association{mId="
                + this.mId
                + ", mUserId="
                + this.mUserId
                + ", mPackageName='"
                + this.mPackageName
                + DateFormat.QUOTE
                + ", mTag='"
                + this.mTag
                + DateFormat.QUOTE
                + ", mDeviceMacAddress="
                + this.mDeviceMacAddress
                + ", mDisplayName='"
                + ((Object) this.mDisplayName)
                + DateFormat.QUOTE
                + ", mDeviceProfile='"
                + this.mDeviceProfile
                + DateFormat.QUOTE
                + ", mSelfManaged="
                + this.mSelfManaged
                + ", mAssociatedDevice="
                + this.mAssociatedDevice
                + ", mNotifyOnDeviceNearby="
                + this.mNotifyOnDeviceNearby
                + ", mRevoked="
                + this.mRevoked
                + ", mPending="
                + this.mPending
                + ", mTimeApprovedMs="
                + new Date(this.mTimeApprovedMs)
                + ", mLastTimeConnectedMs="
                + (this.mLastTimeConnectedMs == Long.MAX_VALUE
                        ? "None"
                        : new Date(this.mLastTimeConnectedMs))
                + ", mSystemDataSyncFlags="
                + this.mSystemDataSyncFlags
                + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssociationInfo)) {
            return false;
        }
        AssociationInfo that = (AssociationInfo) o;
        return this.mId == that.mId
                && this.mUserId == that.mUserId
                && this.mSelfManaged == that.mSelfManaged
                && this.mNotifyOnDeviceNearby == that.mNotifyOnDeviceNearby
                && this.mRevoked == that.mRevoked
                && this.mPending == that.mPending
                && this.mTimeApprovedMs == that.mTimeApprovedMs
                && this.mLastTimeConnectedMs == that.mLastTimeConnectedMs
                && Objects.equals(this.mPackageName, that.mPackageName)
                && Objects.equals(this.mTag, that.mTag)
                && Objects.equals(this.mDeviceMacAddress, that.mDeviceMacAddress)
                && Objects.equals(this.mDisplayName, that.mDisplayName)
                && Objects.equals(this.mDeviceProfile, that.mDeviceProfile)
                && Objects.equals(this.mAssociatedDevice, that.mAssociatedDevice)
                && this.mSystemDataSyncFlags == that.mSystemDataSyncFlags;
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mId),
                Integer.valueOf(this.mUserId),
                this.mPackageName,
                this.mTag,
                this.mDeviceMacAddress,
                this.mDisplayName,
                this.mDeviceProfile,
                this.mAssociatedDevice,
                Boolean.valueOf(this.mSelfManaged),
                Boolean.valueOf(this.mNotifyOnDeviceNearby),
                Boolean.valueOf(this.mRevoked),
                Boolean.valueOf(this.mPending),
                Long.valueOf(this.mTimeApprovedMs),
                Long.valueOf(this.mLastTimeConnectedMs),
                Integer.valueOf(this.mSystemDataSyncFlags));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeInt(this.mUserId);
        dest.writeString(this.mPackageName);
        dest.writeString(this.mTag);
        dest.writeTypedObject(this.mDeviceMacAddress, 0);
        dest.writeCharSequence(this.mDisplayName);
        dest.writeString(this.mDeviceProfile);
        dest.writeTypedObject(this.mAssociatedDevice, 0);
        dest.writeBoolean(this.mSelfManaged);
        dest.writeBoolean(this.mNotifyOnDeviceNearby);
        dest.writeBoolean(this.mRevoked);
        dest.writeBoolean(this.mPending);
        dest.writeLong(this.mTimeApprovedMs);
        dest.writeLong(this.mLastTimeConnectedMs);
        dest.writeInt(this.mSystemDataSyncFlags);
    }

    private AssociationInfo(Parcel in) {
        this.mId = in.readInt();
        this.mUserId = in.readInt();
        this.mPackageName = in.readString();
        this.mTag = in.readString();
        this.mDeviceMacAddress = (MacAddress) in.readTypedObject(MacAddress.CREATOR);
        this.mDisplayName = in.readCharSequence();
        this.mDeviceProfile = in.readString();
        this.mAssociatedDevice = (AssociatedDevice) in.readTypedObject(AssociatedDevice.CREATOR);
        this.mSelfManaged = in.readBoolean();
        this.mNotifyOnDeviceNearby = in.readBoolean();
        this.mRevoked = in.readBoolean();
        this.mPending = in.readBoolean();
        this.mTimeApprovedMs = in.readLong();
        this.mLastTimeConnectedMs = in.readLong();
        this.mSystemDataSyncFlags = in.readInt();
    }

    public static final class Builder {
        private AssociatedDevice mAssociatedDevice;
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
        private String mTag;
        private long mTimeApprovedMs;
        private final int mUserId;

        public Builder(int id, int userId, String packageName) {
            this.mId = id;
            this.mUserId = userId;
            this.mPackageName = packageName;
        }

        public Builder(AssociationInfo info) {
            this.mId = info.mId;
            this.mUserId = info.mUserId;
            this.mPackageName = info.mPackageName;
            this.mTag = info.mTag;
            this.mDeviceMacAddress = info.mDeviceMacAddress;
            this.mDisplayName = info.mDisplayName;
            this.mDeviceProfile = info.mDeviceProfile;
            this.mAssociatedDevice = info.mAssociatedDevice;
            this.mSelfManaged = info.mSelfManaged;
            this.mNotifyOnDeviceNearby = info.mNotifyOnDeviceNearby;
            this.mRevoked = info.mRevoked;
            this.mPending = info.mPending;
            this.mTimeApprovedMs = info.mTimeApprovedMs;
            this.mLastTimeConnectedMs = info.mLastTimeConnectedMs;
            this.mSystemDataSyncFlags = info.mSystemDataSyncFlags;
        }

        public Builder(int id, int userId, String packageName, AssociationInfo info) {
            this.mId = id;
            this.mUserId = userId;
            this.mPackageName = packageName;
            this.mTag = info.mTag;
            this.mDeviceMacAddress = info.mDeviceMacAddress;
            this.mDisplayName = info.mDisplayName;
            this.mDeviceProfile = info.mDeviceProfile;
            this.mAssociatedDevice = info.mAssociatedDevice;
            this.mSelfManaged = info.mSelfManaged;
            this.mNotifyOnDeviceNearby = info.mNotifyOnDeviceNearby;
            this.mRevoked = info.mRevoked;
            this.mPending = info.mPending;
            this.mTimeApprovedMs = info.mTimeApprovedMs;
            this.mLastTimeConnectedMs = info.mLastTimeConnectedMs;
            this.mSystemDataSyncFlags = info.mSystemDataSyncFlags;
        }

        public Builder setTag(String tag) {
            this.mTag = tag;
            return this;
        }

        public Builder setDeviceMacAddress(MacAddress deviceMacAddress) {
            this.mDeviceMacAddress = deviceMacAddress;
            return this;
        }

        public Builder setDisplayName(CharSequence displayName) {
            this.mDisplayName = displayName;
            return this;
        }

        public Builder setDeviceProfile(String deviceProfile) {
            this.mDeviceProfile = deviceProfile;
            return this;
        }

        public Builder setAssociatedDevice(AssociatedDevice associatedDevice) {
            this.mAssociatedDevice = associatedDevice;
            return this;
        }

        public Builder setSelfManaged(boolean selfManaged) {
            this.mSelfManaged = selfManaged;
            return this;
        }

        public Builder setNotifyOnDeviceNearby(boolean notifyOnDeviceNearby) {
            this.mNotifyOnDeviceNearby = notifyOnDeviceNearby;
            return this;
        }

        public Builder setRevoked(boolean revoked) {
            this.mRevoked = revoked;
            return this;
        }

        public Builder setPending(boolean pending) {
            this.mPending = pending;
            return this;
        }

        public Builder setTimeApproved(long timeApprovedMs) {
            if (timeApprovedMs < 0) {
                throw new IllegalArgumentException(
                        "timeApprovedMs must be positive. Was given ("
                                + timeApprovedMs
                                + NavigationBarInflaterView.KEY_CODE_END);
            }
            this.mTimeApprovedMs = timeApprovedMs;
            return this;
        }

        public Builder setLastTimeConnected(long lastTimeConnectedMs) {
            if (lastTimeConnectedMs < 0) {
                throw new IllegalArgumentException(
                        "lastTimeConnectedMs must not be negative! (Given "
                                + lastTimeConnectedMs
                                + " )");
            }
            this.mLastTimeConnectedMs = lastTimeConnectedMs;
            return this;
        }

        public Builder setSystemDataSyncFlags(int flags) {
            this.mSystemDataSyncFlags = flags;
            return this;
        }

        public AssociationInfo build() {
            if (this.mId <= 0) {
                throw new IllegalArgumentException("Association ID should be greater than 0");
            }
            if (this.mDeviceMacAddress == null && this.mDisplayName == null) {
                throw new IllegalArgumentException(
                        "MAC address and the display name must NOT be null at the same time");
            }
            return new AssociationInfo(
                    this.mId,
                    this.mUserId,
                    this.mPackageName,
                    this.mTag,
                    this.mDeviceMacAddress,
                    this.mDisplayName,
                    this.mDeviceProfile,
                    this.mAssociatedDevice,
                    this.mSelfManaged,
                    this.mNotifyOnDeviceNearby,
                    this.mRevoked,
                    this.mPending,
                    this.mTimeApprovedMs,
                    this.mLastTimeConnectedMs,
                    this.mSystemDataSyncFlags);
        }
    }
}
