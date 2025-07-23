package android.hardware.contexthub;

import android.annotation.SystemApi;
import android.media.MediaMetrics;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public final class HubServiceInfo implements Parcelable {
    public static final Parcelable.Creator<HubServiceInfo> CREATOR = new Parcelable.Creator<HubServiceInfo>() { // from class: android.hardware.contexthub.HubServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HubServiceInfo createFromParcel(Parcel parcel) {
            return new HubServiceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HubServiceInfo[] newArray(int i) {
            return new HubServiceInfo[i];
        }
    };
    public static final int FORMAT_AIDL = 1;
    public static final int FORMAT_CUSTOM = 0;
    public static final int FORMAT_PW_RPC_PROTOBUF = 2;
    private final int mFormat;
    private final int mMajorVersion;
    private final int mMinorVersion;
    private final String mServiceDescriptor;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ServiceFormat {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public HubServiceInfo(Service service) {
        this.mServiceDescriptor = service.serviceDescriptor;
        this.mFormat = service.format;
        this.mMajorVersion = service.majorVersion;
        this.mMinorVersion = service.minorVersion;
    }

    private HubServiceInfo(Parcel parcel) {
        this.mServiceDescriptor = (String) Objects.requireNonNull(parcel.readString());
        this.mFormat = parcel.readInt();
        this.mMajorVersion = parcel.readInt();
        this.mMinorVersion = parcel.readInt();
    }

    public HubServiceInfo(String str, int i, int i2, int i3) {
        this.mServiceDescriptor = str;
        this.mFormat = i;
        this.mMajorVersion = i2;
        this.mMinorVersion = i3;
    }

    public String getServiceDescriptor() {
        return this.mServiceDescriptor;
    }

    public int getFormat() {
        return this.mFormat;
    }

    public int getMajorVersion() {
        return this.mMajorVersion;
    }

    public int getMinorVersion() {
        return this.mMinorVersion;
    }

    public String toString() {
        return "Service: descriptor=" + this.mServiceDescriptor + ", format=" + this.mFormat + ", version=" + Integer.toHexString(this.mMajorVersion) + MediaMetrics.SEPARATOR + Integer.toHexString(this.mMinorVersion);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mServiceDescriptor);
        parcel.writeInt(this.mFormat);
        parcel.writeInt(this.mMajorVersion);
        parcel.writeInt(this.mMinorVersion);
    }

    public static final class Builder {
        private final int mFormat;
        private final int mMajorVersion;
        private final int mMinorVersion;
        private final String mServiceDescriptor;

        public Builder(String str, int i, int i2, int i3) {
            if (i != 0 && i != 1 && i != 2) {
                throw new IllegalArgumentException("Invalid format type.");
            }
            this.mFormat = i;
            if (i2 < 0) {
                throw new IllegalArgumentException("Major version cannot be set to negative number.");
            }
            this.mMajorVersion = i2;
            if (i3 < 0) {
                throw new IllegalArgumentException("Minor version cannot be set to negative number.");
            }
            this.mMinorVersion = i3;
            if (str.isBlank()) {
                throw new IllegalArgumentException("Invalid service descriptor.");
            }
            this.mServiceDescriptor = str;
        }

        public HubServiceInfo build() {
            if (this.mMajorVersion < 0 || this.mMinorVersion < 0) {
                throw new IllegalStateException("Major and minor version must be set.");
            }
            return new HubServiceInfo(this.mServiceDescriptor, this.mFormat, this.mMajorVersion, this.mMinorVersion);
        }
    }
}
