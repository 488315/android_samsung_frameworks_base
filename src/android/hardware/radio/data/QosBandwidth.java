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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.maxBitrateKbps);
        parcel.writeInt(this.guaranteedBitrateKbps);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.maxBitrateKbps = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.guaranteedBitrateKbps = parcel.readInt();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
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
