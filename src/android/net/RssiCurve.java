package android.net;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

@SystemApi
@Deprecated
/* loaded from: classes3.dex */
public class RssiCurve implements Parcelable {
    public static final Parcelable.Creator<RssiCurve> CREATOR = new Parcelable.Creator<RssiCurve>() { // from class: android.net.RssiCurve.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RssiCurve createFromParcel(Parcel parcel) {
            return new RssiCurve(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RssiCurve[] newArray(int i) {
            return new RssiCurve[i];
        }
    };
    private static final int DEFAULT_ACTIVE_NETWORK_RSSI_BOOST = 25;
    public final int activeNetworkRssiBoost;
    public final int bucketWidth;
    public final byte[] rssiBuckets;
    public final int start;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RssiCurve(int i, int i2, byte[] bArr) {
        this(i, i2, bArr, 25);
    }

    public RssiCurve(int i, int i2, byte[] bArr, int i3) {
        this.start = i;
        this.bucketWidth = i2;
        if (bArr == null || bArr.length == 0) {
            throw new IllegalArgumentException("rssiBuckets must be at least one element large.");
        }
        this.rssiBuckets = bArr;
        this.activeNetworkRssiBoost = i3;
    }

    private RssiCurve(Parcel parcel) {
        this.start = parcel.readInt();
        this.bucketWidth = parcel.readInt();
        byte[] bArr = new byte[parcel.readInt()];
        this.rssiBuckets = bArr;
        parcel.readByteArray(bArr);
        this.activeNetworkRssiBoost = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.start);
        parcel.writeInt(this.bucketWidth);
        parcel.writeInt(this.rssiBuckets.length);
        parcel.writeByteArray(this.rssiBuckets);
        parcel.writeInt(this.activeNetworkRssiBoost);
    }

    public byte lookupScore(int i) {
        return lookupScore(i, false);
    }

    public byte lookupScore(int i, boolean z) {
        if (z) {
            i += this.activeNetworkRssiBoost;
        }
        int length = (i - this.start) / this.bucketWidth;
        if (length < 0) {
            length = 0;
        } else {
            byte[] bArr = this.rssiBuckets;
            if (length > bArr.length - 1) {
                length = bArr.length - 1;
            }
        }
        return this.rssiBuckets[length];
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            RssiCurve rssiCurve = (RssiCurve) obj;
            if (this.start == rssiCurve.start && this.bucketWidth == rssiCurve.bucketWidth && Arrays.equals(this.rssiBuckets, rssiCurve.rssiBuckets) && this.activeNetworkRssiBoost == rssiCurve.activeNetworkRssiBoost) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(this.rssiBuckets) ^ Objects.hash(Integer.valueOf(this.start), Integer.valueOf(this.bucketWidth), Integer.valueOf(this.activeNetworkRssiBoost));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("RssiCurve[start=");
        sb.append(this.start);
        sb.append(",bucketWidth=");
        sb.append(this.bucketWidth);
        sb.append(",activeNetworkRssiBoost=");
        sb.append(this.activeNetworkRssiBoost);
        sb.append(",buckets=");
        int i = 0;
        while (true) {
            byte[] bArr = this.rssiBuckets;
            if (i < bArr.length) {
                sb.append((int) bArr[i]);
                if (i < this.rssiBuckets.length - 1) {
                    sb.append(",");
                }
                i++;
            } else {
                sb.append(NavigationBarInflaterView.SIZE_MOD_END);
                return sb.toString();
            }
        }
    }
}
