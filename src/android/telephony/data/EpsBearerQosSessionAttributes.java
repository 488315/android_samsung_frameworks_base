package android.telephony.data;

import android.annotation.SystemApi;
import android.net.QosSessionAttributes;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class EpsBearerQosSessionAttributes implements Parcelable, QosSessionAttributes {
    public static final Parcelable.Creator<EpsBearerQosSessionAttributes> CREATOR = new Parcelable.Creator<EpsBearerQosSessionAttributes>() { // from class: android.telephony.data.EpsBearerQosSessionAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EpsBearerQosSessionAttributes createFromParcel(Parcel parcel) {
            return new EpsBearerQosSessionAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EpsBearerQosSessionAttributes[] newArray(int i) {
            return new EpsBearerQosSessionAttributes[i];
        }
    };
    private static final String TAG = "EpsBearerQosSessionAttributes";
    private final long mGuaranteedDownlinkBitRate;
    private final long mGuaranteedUplinkBitRate;
    private final long mMaxDownlinkBitRate;
    private final long mMaxUplinkBitRate;
    private final int mQci;
    private final List<InetSocketAddress> mRemoteAddresses;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getQosIdentifier() {
        return this.mQci;
    }

    public long getGuaranteedUplinkBitRateKbps() {
        return this.mGuaranteedUplinkBitRate;
    }

    public long getGuaranteedDownlinkBitRateKbps() {
        return this.mGuaranteedDownlinkBitRate;
    }

    public long getMaxUplinkBitRateKbps() {
        return this.mMaxUplinkBitRate;
    }

    public long getMaxDownlinkBitRateKbps() {
        return this.mMaxDownlinkBitRate;
    }

    public List<InetSocketAddress> getRemoteAddresses() {
        return this.mRemoteAddresses;
    }

    public EpsBearerQosSessionAttributes(int i, long j, long j2, long j3, long j4, List<InetSocketAddress> list) {
        Objects.requireNonNull(list, "remoteAddress must be non-null");
        this.mQci = i;
        this.mMaxDownlinkBitRate = j;
        this.mMaxUplinkBitRate = j2;
        this.mGuaranteedDownlinkBitRate = j3;
        this.mGuaranteedUplinkBitRate = j4;
        this.mRemoteAddresses = Collections.unmodifiableList(copySocketAddresses(list));
    }

    private static List<InetSocketAddress> copySocketAddresses(List<InetSocketAddress> list) {
        ArrayList arrayList = new ArrayList();
        for (InetSocketAddress inetSocketAddress : list) {
            if (inetSocketAddress != null && inetSocketAddress.getAddress() != null) {
                arrayList.add(inetSocketAddress);
            }
        }
        return arrayList;
    }

    private EpsBearerQosSessionAttributes(Parcel parcel) {
        this.mQci = parcel.readInt();
        this.mMaxDownlinkBitRate = parcel.readLong();
        this.mMaxUplinkBitRate = parcel.readLong();
        this.mGuaranteedDownlinkBitRate = parcel.readLong();
        this.mGuaranteedUplinkBitRate = parcel.readLong();
        int i = parcel.readInt();
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            try {
                arrayList.add(new InetSocketAddress(InetAddress.getByAddress(parcel.createByteArray()), parcel.readInt()));
            } catch (UnknownHostException e) {
                Log.e(TAG, "unable to unparcel remote address at index: " + i2, e);
            }
        }
        this.mRemoteAddresses = Collections.unmodifiableList(arrayList);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mQci);
        parcel.writeLong(this.mMaxDownlinkBitRate);
        parcel.writeLong(this.mMaxUplinkBitRate);
        parcel.writeLong(this.mGuaranteedDownlinkBitRate);
        parcel.writeLong(this.mGuaranteedUplinkBitRate);
        int size = this.mRemoteAddresses.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            InetSocketAddress inetSocketAddress = this.mRemoteAddresses.get(i2);
            parcel.writeByteArray(inetSocketAddress.getAddress().getAddress());
            parcel.writeInt(inetSocketAddress.getPort());
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            EpsBearerQosSessionAttributes epsBearerQosSessionAttributes = (EpsBearerQosSessionAttributes) obj;
            if (this.mQci == epsBearerQosSessionAttributes.mQci && this.mMaxUplinkBitRate == epsBearerQosSessionAttributes.mMaxUplinkBitRate && this.mMaxDownlinkBitRate == epsBearerQosSessionAttributes.mMaxDownlinkBitRate && this.mGuaranteedUplinkBitRate == epsBearerQosSessionAttributes.mGuaranteedUplinkBitRate && this.mGuaranteedDownlinkBitRate == epsBearerQosSessionAttributes.mGuaranteedDownlinkBitRate && this.mRemoteAddresses.size() == epsBearerQosSessionAttributes.mRemoteAddresses.size() && this.mRemoteAddresses.containsAll(epsBearerQosSessionAttributes.mRemoteAddresses)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mQci), Long.valueOf(this.mMaxUplinkBitRate), Long.valueOf(this.mMaxDownlinkBitRate), Long.valueOf(this.mGuaranteedUplinkBitRate), Long.valueOf(this.mGuaranteedDownlinkBitRate), this.mRemoteAddresses);
    }
}
