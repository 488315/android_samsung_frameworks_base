package android.telephony.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.data.Qos;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class NrQos extends Qos implements Parcelable {
    public static final Parcelable.Creator<NrQos> CREATOR = new Parcelable.Creator<NrQos>() { // from class: android.telephony.data.NrQos.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NrQos createFromParcel(Parcel parcel) {
            return new NrQos(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NrQos[] newArray(int i) {
            return new NrQos[i];
        }
    };
    int averagingWindowMs;
    int fiveQi;
    int qosFlowId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NrQos(Qos.QosBandwidth qosBandwidth, Qos.QosBandwidth qosBandwidth2, int i, int i2, int i3) {
        super(2, qosBandwidth, qosBandwidth2);
        this.qosFlowId = i;
        this.fiveQi = i2;
        this.averagingWindowMs = i3;
    }

    private NrQos(Parcel parcel) {
        super(parcel);
        this.qosFlowId = parcel.readInt();
        this.fiveQi = parcel.readInt();
        this.averagingWindowMs = parcel.readInt();
    }

    public static NrQos createFromParcelBody(Parcel parcel) {
        return new NrQos(parcel);
    }

    public int get5Qi() {
        return this.fiveQi;
    }

    public int getQfi() {
        return this.qosFlowId;
    }

    public int getAveragingWindow() {
        return this.averagingWindowMs;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(2, parcel, i);
        parcel.writeInt(this.qosFlowId);
        parcel.writeInt(this.fiveQi);
        parcel.writeInt(this.averagingWindowMs);
    }

    @Override // android.telephony.data.Qos
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), Integer.valueOf(this.qosFlowId), Integer.valueOf(this.fiveQi), Integer.valueOf(this.averagingWindowMs));
    }

    @Override // android.telephony.data.Qos
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof NrQos)) {
            NrQos nrQos = (NrQos) obj;
            if (super.equals(nrQos) && this.qosFlowId == nrQos.qosFlowId && this.fiveQi == nrQos.fiveQi && this.averagingWindowMs == nrQos.averagingWindowMs) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "NrQos { fiveQi=" + this.fiveQi + " downlink=" + this.downlink + " uplink=" + this.uplink + " qosFlowId=" + this.qosFlowId + " averagingWindowMs=" + this.averagingWindowMs + "}";
    }
}
