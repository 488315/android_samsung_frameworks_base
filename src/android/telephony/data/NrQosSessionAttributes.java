package android.telephony.data;

import android.annotation.SystemApi;
import android.net.QosSessionAttributes;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class NrQosSessionAttributes implements Parcelable, QosSessionAttributes {
    public static final Parcelable.Creator<NrQosSessionAttributes> CREATOR = new Parcelable.Creator<NrQosSessionAttributes>() { // from class: android.telephony.data.NrQosSessionAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NrQosSessionAttributes createFromParcel(Parcel parcel) {
            return new NrQosSessionAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NrQosSessionAttributes[] newArray(int i) {
            return new NrQosSessionAttributes[i];
        }
    };
    private static final String TAG = "NrQosSessionAttributes";
    private final int m5Qi;
    private final long mAveragingWindow;
    private final long mGuaranteedDownlinkBitRate;
    private final long mGuaranteedUplinkBitRate;
    private final long mMaxDownlinkBitRate;
    private final long mMaxUplinkBitRate;
    private final int mQfi;
    private final List<InetSocketAddress> mRemoteAddresses;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getQosIdentifier() {
        return this.m5Qi;
    }

    public int getQosFlowIdentifier() {
        return this.mQfi;
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

    public Duration getBitRateWindowDuration() {
        return Duration.ofMillis(this.mAveragingWindow);
    }

    public List<InetSocketAddress> getRemoteAddresses() {
        return this.mRemoteAddresses;
    }

    public NrQosSessionAttributes(int i, int i2, long j, long j2, long j3, long j4, long j5, List<InetSocketAddress> list) {
        Objects.requireNonNull(list, "remoteAddress must be non-null");
        this.m5Qi = i;
        this.mQfi = i2;
        this.mMaxDownlinkBitRate = j;
        this.mMaxUplinkBitRate = j2;
        this.mGuaranteedDownlinkBitRate = j3;
        this.mGuaranteedUplinkBitRate = j4;
        this.mAveragingWindow = j5;
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

    private NrQosSessionAttributes(Parcel parcel) {
        this.m5Qi = parcel.readInt();
        this.mQfi = parcel.readInt();
        this.mMaxDownlinkBitRate = parcel.readLong();
        this.mMaxUplinkBitRate = parcel.readLong();
        this.mGuaranteedDownlinkBitRate = parcel.readLong();
        this.mGuaranteedUplinkBitRate = parcel.readLong();
        this.mAveragingWindow = parcel.readLong();
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
        parcel.writeInt(this.m5Qi);
        parcel.writeInt(this.mQfi);
        parcel.writeLong(this.mMaxDownlinkBitRate);
        parcel.writeLong(this.mMaxUplinkBitRate);
        parcel.writeLong(this.mGuaranteedDownlinkBitRate);
        parcel.writeLong(this.mGuaranteedUplinkBitRate);
        parcel.writeLong(this.mAveragingWindow);
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
            NrQosSessionAttributes nrQosSessionAttributes = (NrQosSessionAttributes) obj;
            if (this.m5Qi == nrQosSessionAttributes.m5Qi && this.mQfi == nrQosSessionAttributes.mQfi && this.mMaxUplinkBitRate == nrQosSessionAttributes.mMaxUplinkBitRate && this.mMaxDownlinkBitRate == nrQosSessionAttributes.mMaxDownlinkBitRate && this.mGuaranteedUplinkBitRate == nrQosSessionAttributes.mGuaranteedUplinkBitRate && this.mGuaranteedDownlinkBitRate == nrQosSessionAttributes.mGuaranteedDownlinkBitRate && this.mAveragingWindow == nrQosSessionAttributes.mAveragingWindow && this.mRemoteAddresses.size() == nrQosSessionAttributes.mRemoteAddresses.size() && this.mRemoteAddresses.containsAll(nrQosSessionAttributes.mRemoteAddresses)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.m5Qi), Integer.valueOf(this.mQfi), Long.valueOf(this.mMaxUplinkBitRate), Long.valueOf(this.mMaxDownlinkBitRate), Long.valueOf(this.mGuaranteedUplinkBitRate), Long.valueOf(this.mGuaranteedDownlinkBitRate), Long.valueOf(this.mAveragingWindow), this.mRemoteAddresses);
    }
}
