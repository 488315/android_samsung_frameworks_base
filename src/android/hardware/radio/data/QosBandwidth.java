package android.hardware.radio.data;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class QosBandwidth implements Parcelable {
    public static final Parcelable.Creator<QosBandwidth> CREATOR = new Parcelable.Creator<QosBandwidth>() { // from class: android.hardware.radio.data.QosBandwidth.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QosBandwidth createFromParcel(Parcel parcel) {
            QosBandwidth qosBandwidth = new QosBandwidth();
            qosBandwidth.readFromParcel(parcel);
            return qosBandwidth;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QosBandwidth[] newArray(int i) {
            return new QosBandwidth[i];
        }
    };
    public int maxBitrateKbps = 0;
    public int guaranteedBitrateKbps = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.maxBitrateKbps);
        parcel.writeInt(this.guaranteedBitrateKbps);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.maxBitrateKbps = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.guaranteedBitrateKbps = parcel.readInt();
                    if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("maxBitrateKbps: " + this.maxBitrateKbps);
        stringJoiner.add("guaranteedBitrateKbps: " + this.guaranteedBitrateKbps);
        return "QosBandwidth" + stringJoiner.toString();
    }
}
