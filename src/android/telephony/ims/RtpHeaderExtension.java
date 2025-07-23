package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class RtpHeaderExtension implements Parcelable {
    public static final Parcelable.Creator<RtpHeaderExtension> CREATOR = new Parcelable.Creator<RtpHeaderExtension>() { // from class: android.telephony.ims.RtpHeaderExtension.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RtpHeaderExtension createFromParcel(Parcel parcel) {
            return new RtpHeaderExtension(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RtpHeaderExtension[] newArray(int i) {
            return new RtpHeaderExtension[i];
        }
    };
    private byte[] mExtensionData;
    private int mLocalIdentifier;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RtpHeaderExtension(int i, byte[] bArr) {
        if (i < 1 || i > 13) {
            throw new IllegalArgumentException("localIdentifier must be in range 1-14");
        }
        if (bArr == null) {
            throw new NullPointerException("extensionDa is required.");
        }
        this.mLocalIdentifier = i;
        this.mExtensionData = bArr;
    }

    private RtpHeaderExtension(Parcel parcel) {
        this.mLocalIdentifier = parcel.readInt();
        this.mExtensionData = parcel.createByteArray();
    }

    public int getLocalIdentifier() {
        return this.mLocalIdentifier;
    }

    public byte[] getExtensionData() {
        return this.mExtensionData;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mLocalIdentifier);
        parcel.writeByteArray(this.mExtensionData);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            RtpHeaderExtension rtpHeaderExtension = (RtpHeaderExtension) obj;
            if (this.mLocalIdentifier == rtpHeaderExtension.mLocalIdentifier && Arrays.equals(this.mExtensionData, rtpHeaderExtension.mExtensionData)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (Objects.hash(Integer.valueOf(this.mLocalIdentifier)) * 31) + Arrays.hashCode(this.mExtensionData);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("RtpHeaderExtension{mLocalIdentifier=");
        sb.append(this.mLocalIdentifier);
        sb.append(", mData=");
        for (byte b : this.mExtensionData) {
            sb.append(Integer.toBinaryString(b));
            sb.append("b_");
        }
        sb.append("}");
        return sb.toString();
    }
}
