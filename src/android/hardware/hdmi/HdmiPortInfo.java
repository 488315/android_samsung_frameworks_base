package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public final class HdmiPortInfo implements Parcelable {
    public static final Parcelable.Creator<HdmiPortInfo> CREATOR = new Parcelable.Creator<HdmiPortInfo>() { // from class: android.hardware.hdmi.HdmiPortInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HdmiPortInfo createFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            boolean z = parcel.readInt() == 1;
            return new Builder(i, i2, i3).setCecSupported(z).setArcSupported(parcel.readInt() == 1).setEarcSupported(parcel.readInt() == 1).setMhlSupported(parcel.readInt() == 1).build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HdmiPortInfo[] newArray(int i) {
            return new HdmiPortInfo[i];
        }
    };
    public static final int PORT_INPUT = 0;
    public static final int PORT_OUTPUT = 1;
    private final int mAddress;
    private final boolean mArcSupported;
    private final boolean mCecSupported;
    private final boolean mEarcSupported;
    private final int mId;
    private final boolean mMhlSupported;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PortType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public HdmiPortInfo(int i, int i2, int i3, boolean z, boolean z2, boolean z3) {
        this.mId = i;
        this.mType = i2;
        this.mAddress = i3;
        this.mCecSupported = z;
        this.mArcSupported = z3;
        this.mEarcSupported = false;
        this.mMhlSupported = z2;
    }

    public Builder toBuilder() {
        return new Builder();
    }

    private HdmiPortInfo(Builder builder) {
        this.mId = builder.mId;
        this.mType = builder.mType;
        this.mAddress = builder.mAddress;
        this.mCecSupported = builder.mCecSupported;
        this.mArcSupported = builder.mArcSupported;
        this.mEarcSupported = builder.mEarcSupported;
        this.mMhlSupported = builder.mMhlSupported;
    }

    public int getId() {
        return this.mId;
    }

    public int getType() {
        return this.mType;
    }

    public int getAddress() {
        return this.mAddress;
    }

    public boolean isCecSupported() {
        return this.mCecSupported;
    }

    public boolean isMhlSupported() {
        return this.mMhlSupported;
    }

    public boolean isArcSupported() {
        return this.mArcSupported;
    }

    public boolean isEarcSupported() {
        return this.mEarcSupported;
    }

    @Override // android.os.Parcelable
    @SystemApi
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mAddress);
        parcel.writeInt(this.mCecSupported ? 1 : 0);
        parcel.writeInt(this.mArcSupported ? 1 : 0);
        parcel.writeInt(this.mMhlSupported ? 1 : 0);
        parcel.writeInt(this.mEarcSupported ? 1 : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("port_id: ");
        sb.append(this.mId);
        sb.append(", type: ");
        sb.append(this.mType == 0 ? "HDMI_IN" : "HDMI_OUT");
        sb.append(", address: ");
        sb.append(String.format("0x%04x", Integer.valueOf(this.mAddress)));
        sb.append(", cec: ");
        sb.append(this.mCecSupported);
        sb.append(", arc: ");
        sb.append(this.mArcSupported);
        sb.append(", mhl: ");
        sb.append(this.mMhlSupported);
        sb.append(", earc: ");
        sb.append(this.mEarcSupported);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof HdmiPortInfo)) {
            return false;
        }
        HdmiPortInfo hdmiPortInfo = (HdmiPortInfo) obj;
        return this.mId == hdmiPortInfo.mId && this.mType == hdmiPortInfo.mType && this.mAddress == hdmiPortInfo.mAddress && this.mCecSupported == hdmiPortInfo.mCecSupported && this.mArcSupported == hdmiPortInfo.mArcSupported && this.mMhlSupported == hdmiPortInfo.mMhlSupported && this.mEarcSupported == hdmiPortInfo.mEarcSupported;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mId), Integer.valueOf(this.mType), Integer.valueOf(this.mAddress), Boolean.valueOf(this.mCecSupported), Boolean.valueOf(this.mArcSupported), Boolean.valueOf(this.mMhlSupported), Boolean.valueOf(this.mEarcSupported));
    }

    public static final class Builder {
        private int mAddress;
        private boolean mArcSupported;
        private boolean mCecSupported;
        private boolean mEarcSupported;
        private int mId;
        private boolean mMhlSupported;
        private int mType;

        public Builder(int i, int i2, int i3) {
            if (i2 != 0 && i2 != 1) {
                throw new IllegalArgumentException("type should be 0 or 1.");
            }
            if (i3 < 0) {
                throw new IllegalArgumentException("address should be positive.");
            }
            this.mId = i;
            this.mType = i2;
            this.mAddress = i3;
        }

        private Builder(HdmiPortInfo hdmiPortInfo) {
            this.mId = hdmiPortInfo.mId;
            this.mType = hdmiPortInfo.mType;
            this.mAddress = hdmiPortInfo.mAddress;
            this.mCecSupported = hdmiPortInfo.mCecSupported;
            this.mArcSupported = hdmiPortInfo.mArcSupported;
            this.mEarcSupported = hdmiPortInfo.mEarcSupported;
            this.mMhlSupported = hdmiPortInfo.mMhlSupported;
        }

        public HdmiPortInfo build() {
            return new HdmiPortInfo(this);
        }

        public Builder setCecSupported(boolean z) {
            this.mCecSupported = z;
            return this;
        }

        public Builder setArcSupported(boolean z) {
            this.mArcSupported = z;
            return this;
        }

        public Builder setEarcSupported(boolean z) {
            this.mEarcSupported = z;
            return this;
        }

        public Builder setMhlSupported(boolean z) {
            this.mMhlSupported = z;
            return this;
        }
    }
}
