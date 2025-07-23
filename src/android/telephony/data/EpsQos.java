package android.telephony.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.data.Qos;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class EpsQos extends Qos implements Parcelable {
    public static final Parcelable.Creator<EpsQos> CREATOR = new Parcelable.Creator<EpsQos>() { // from class: android.telephony.data.EpsQos.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EpsQos createFromParcel(Parcel parcel) {
            return new EpsQos(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EpsQos[] newArray(int i) {
            return new EpsQos[i];
        }
    };
    int qosClassId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public EpsQos(Qos.QosBandwidth qosBandwidth, Qos.QosBandwidth qosBandwidth2, int i) {
        super(1, qosBandwidth, qosBandwidth2);
        this.qosClassId = i;
    }

    private EpsQos(Parcel parcel) {
        super(parcel);
        this.qosClassId = parcel.readInt();
    }

    public int getQci() {
        return this.qosClassId;
    }

    public static EpsQos createFromParcelBody(Parcel parcel) {
        return new EpsQos(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(1, parcel, i);
        parcel.writeInt(this.qosClassId);
    }

    @Override // android.telephony.data.Qos
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), Integer.valueOf(this.qosClassId));
    }

    @Override // android.telephony.data.Qos
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof EpsQos)) {
            EpsQos epsQos = (EpsQos) obj;
            if (this.qosClassId == epsQos.qosClassId && super.equals(epsQos)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "EpsQos { qosClassId=" + this.qosClassId + " downlink=" + this.downlink + " uplink=" + this.uplink + "}";
    }
}
