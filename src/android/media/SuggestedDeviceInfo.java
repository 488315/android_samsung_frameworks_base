package android.media;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class SuggestedDeviceInfo implements Parcelable {
    public static final Parcelable.Creator<SuggestedDeviceInfo> CREATOR = new Parcelable.Creator<SuggestedDeviceInfo>() { // from class: android.media.SuggestedDeviceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SuggestedDeviceInfo createFromParcel(Parcel parcel) {
            return new SuggestedDeviceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SuggestedDeviceInfo[] newArray(int i) {
            return new SuggestedDeviceInfo[i];
        }
    };
    private final String mDeviceDisplayName;
    private final Bundle mExtras;
    private final String mRouteId;
    private final int mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SuggestedDeviceInfo(Builder builder) {
        this.mDeviceDisplayName = builder.mDeviceDisplayName;
        this.mRouteId = builder.mRouteId;
        this.mType = builder.mType.intValue();
        this.mExtras = builder.mExtras;
    }

    private SuggestedDeviceInfo(Parcel parcel) {
        this.mDeviceDisplayName = parcel.readString();
        this.mRouteId = parcel.readString();
        this.mType = parcel.readInt();
        this.mExtras = parcel.readBundle();
    }

    public String getDeviceDisplayName() {
        return this.mDeviceDisplayName;
    }

    public String getRouteId() {
        return this.mRouteId;
    }

    public int getType() {
        return this.mType;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mDeviceDisplayName);
        parcel.writeString(this.mRouteId);
        parcel.writeInt(this.mType);
        parcel.writeBundle(this.mExtras);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SuggestedDeviceInfo)) {
            return false;
        }
        SuggestedDeviceInfo suggestedDeviceInfo = (SuggestedDeviceInfo) obj;
        return Objects.equals(this.mDeviceDisplayName, suggestedDeviceInfo.mDeviceDisplayName) && Objects.equals(this.mRouteId, suggestedDeviceInfo.mRouteId) && this.mType == suggestedDeviceInfo.mType;
    }

    public int hashCode() {
        return Objects.hash(this.mDeviceDisplayName, this.mRouteId, Integer.valueOf(this.mType));
    }

    public String toString() {
        return this.mDeviceDisplayName + " | " + this.mRouteId + " | " + this.mType;
    }

    public static final class Builder {
        private String mDeviceDisplayName;
        private Bundle mExtras = Bundle.EMPTY;
        private String mRouteId;
        private Integer mType;

        public SuggestedDeviceInfo build() {
            if (this.mDeviceDisplayName == null) {
                throw new IllegalArgumentException("Device display name cannot be null");
            }
            if (this.mRouteId == null) {
                throw new IllegalArgumentException("Route ID cannot be null.");
            }
            if (this.mType == null) {
                throw new IllegalArgumentException("Device type cannot be null.");
            }
            if (this.mExtras == null) {
                throw new IllegalArgumentException("Extras cannot be null.");
            }
            return new SuggestedDeviceInfo(this);
        }

        public Builder setDeviceDisplayName(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("Device display name cannot be null");
            }
            this.mDeviceDisplayName = str;
            return this;
        }

        public Builder setRouteId(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("Device display name cannot be null");
            }
            this.mRouteId = str;
            return this;
        }

        public Builder setType(int i) {
            this.mType = Integer.valueOf(i);
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = (Bundle) Objects.requireNonNull(bundle, "extras must not be null");
            return this;
        }
    }
}
