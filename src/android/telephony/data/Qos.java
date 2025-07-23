package android.telephony.data;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public abstract class Qos implements Parcelable {
    static final int QOS_TYPE_EPS = 1;
    static final int QOS_TYPE_NR = 2;
    final QosBandwidth downlink;
    final int type;
    final QosBandwidth uplink;

    @Retention(RetentionPolicy.SOURCE)
    public @interface QosType {
    }

    Qos(int i, QosBandwidth qosBandwidth, QosBandwidth qosBandwidth2) {
        this.type = i;
        this.downlink = qosBandwidth;
        this.uplink = qosBandwidth2;
    }

    public QosBandwidth getDownlinkBandwidth() {
        return this.downlink;
    }

    public QosBandwidth getUplinkBandwidth() {
        return this.uplink;
    }

    public static class QosBandwidth implements Parcelable {
        public static final Parcelable.Creator<QosBandwidth> CREATOR = new Parcelable.Creator<QosBandwidth>() { // from class: android.telephony.data.Qos.QosBandwidth.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public QosBandwidth createFromParcel(Parcel parcel) {
                return new QosBandwidth(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public QosBandwidth[] newArray(int i) {
                return new QosBandwidth[i];
            }
        };
        int guaranteedBitrateKbps;
        int maxBitrateKbps;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public QosBandwidth(int i, int i2) {
            this.maxBitrateKbps = i;
            this.guaranteedBitrateKbps = i2;
        }

        private QosBandwidth(Parcel parcel) {
            this.maxBitrateKbps = parcel.readInt();
            this.guaranteedBitrateKbps = parcel.readInt();
        }

        public int getMaxBitrateKbps() {
            return this.maxBitrateKbps;
        }

        public int getGuaranteedBitrateKbps() {
            return this.guaranteedBitrateKbps;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.maxBitrateKbps);
            parcel.writeInt(this.guaranteedBitrateKbps);
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.maxBitrateKbps), Integer.valueOf(this.guaranteedBitrateKbps));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && (obj instanceof QosBandwidth)) {
                QosBandwidth qosBandwidth = (QosBandwidth) obj;
                if (this.maxBitrateKbps == qosBandwidth.maxBitrateKbps && this.guaranteedBitrateKbps == qosBandwidth.guaranteedBitrateKbps) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return "Bandwidth { maxBitrateKbps=" + this.maxBitrateKbps + " guaranteedBitrateKbps=" + this.guaranteedBitrateKbps + "}";
        }
    }

    protected Qos(Parcel parcel) {
        this.type = parcel.readInt();
        this.downlink = (QosBandwidth) parcel.readParcelable(QosBandwidth.class.getClassLoader(), QosBandwidth.class);
        this.uplink = (QosBandwidth) parcel.readParcelable(QosBandwidth.class.getClassLoader(), QosBandwidth.class);
    }

    public void writeToParcel(int i, Parcel parcel, int i2) {
        parcel.writeInt(i);
        parcel.writeParcelable(this.downlink, i2);
        parcel.writeParcelable(this.uplink, i2);
    }

    public int getType() {
        return this.type;
    }

    public int hashCode() {
        return Objects.hash(this.downlink, this.uplink);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        Qos qos = (Qos) obj;
        return this.type == qos.type && this.downlink.equals(qos.downlink) && this.uplink.equals(qos.uplink);
    }
}
