package android.hardware.radio.data;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class EpsQos implements Parcelable {
    public static final Parcelable.Creator<EpsQos> CREATOR = new Parcelable.Creator<EpsQos>() { // from class: android.hardware.radio.data.EpsQos.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EpsQos createFromParcel(Parcel parcel) {
            EpsQos epsQos = new EpsQos();
            epsQos.readFromParcel(parcel);
            return epsQos;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EpsQos[] newArray(int i) {
            return new EpsQos[i];
        }
    };
    public QosBandwidth downlink;
    public int qci = 0;
    public QosBandwidth uplink;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.qci);
        parcel.writeTypedObject(this.downlink, i);
        parcel.writeTypedObject(this.uplink, i);
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
                this.qci = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.downlink = (QosBandwidth) parcel.readTypedObject(QosBandwidth.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.uplink = (QosBandwidth) parcel.readTypedObject(QosBandwidth.CREATOR);
                        if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
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
        stringJoiner.add("qci: " + this.qci);
        stringJoiner.add("downlink: " + Objects.toString(this.downlink));
        stringJoiner.add("uplink: " + Objects.toString(this.uplink));
        return "EpsQos" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.uplink) | describeContents(this.downlink);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
