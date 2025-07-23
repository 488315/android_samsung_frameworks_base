package android.net.wifi.nl80211;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class PnoNetwork implements Parcelable {
    public static final Parcelable.Creator<PnoNetwork> CREATOR = new Parcelable.Creator<PnoNetwork>() { // from class: android.net.wifi.nl80211.PnoNetwork.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PnoNetwork createFromParcel(Parcel parcel) {
            PnoNetwork pnoNetwork = new PnoNetwork();
            pnoNetwork.mIsHidden = parcel.readInt() != 0;
            pnoNetwork.mSsid = parcel.createByteArray();
            if (pnoNetwork.mSsid == null) {
                pnoNetwork.mSsid = new byte[0];
            }
            pnoNetwork.mFrequencies = parcel.createIntArray();
            if (pnoNetwork.mFrequencies == null) {
                pnoNetwork.mFrequencies = new int[0];
            }
            return pnoNetwork;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PnoNetwork[] newArray(int i) {
            return new PnoNetwork[i];
        }
    };
    private int[] mFrequencies;
    private boolean mIsHidden;
    private byte[] mSsid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isHidden() {
        return this.mIsHidden;
    }

    public void setHidden(boolean z) {
        this.mIsHidden = z;
    }

    public byte[] getSsid() {
        return this.mSsid;
    }

    public void setSsid(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("null argument");
        }
        this.mSsid = bArr;
    }

    public int[] getFrequenciesMhz() {
        return this.mFrequencies;
    }

    public void setFrequenciesMhz(int[] iArr) {
        if (iArr == null) {
            throw new IllegalArgumentException("null argument");
        }
        this.mFrequencies = iArr;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PnoNetwork)) {
            return false;
        }
        PnoNetwork pnoNetwork = (PnoNetwork) obj;
        return Arrays.equals(this.mSsid, pnoNetwork.mSsid) && Arrays.equals(this.mFrequencies, pnoNetwork.mFrequencies) && this.mIsHidden == pnoNetwork.mIsHidden;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mIsHidden), Integer.valueOf(Arrays.hashCode(this.mSsid)), Integer.valueOf(Arrays.hashCode(this.mFrequencies)));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mIsHidden ? 1 : 0);
        parcel.writeByteArray(this.mSsid);
        parcel.writeIntArray(this.mFrequencies);
    }
}
