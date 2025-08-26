package android.hardware.radio.data;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class NrQos implements Parcelable {
    public static final int AVERAGING_WINDOW_UNKNOWN = -1;
    public static final Parcelable.Creator<NrQos> CREATOR = new Parcelable.Creator<NrQos>() { // from class: android.hardware.radio.data.NrQos.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NrQos createFromParcel(Parcel parcel) {
            NrQos nrQos = new NrQos();
            nrQos.readFromParcel(parcel);
            return nrQos;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NrQos[] newArray(int i) {
            return new NrQos[i];
        }
    };
    public static final byte FLOW_ID_RANGE_MAX = 63;
    public static final byte FLOW_ID_RANGE_MIN = 1;
    public QosBandwidth downlink;
    public QosBandwidth uplink;
    public int fiveQi = 0;
    public byte qfi = 0;

    @Deprecated
    public char averagingWindowMs = 0;
    public int averagingWindowMillis = -1;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.fiveQi);
        parcel.writeTypedObject(this.downlink, i);
        parcel.writeTypedObject(this.uplink, i);
        parcel.writeByte(this.qfi);
        parcel.writeInt(this.averagingWindowMs);
        parcel.writeInt(this.averagingWindowMillis);
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
                this.fiveQi = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.downlink = (QosBandwidth) parcel.readTypedObject(QosBandwidth.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.uplink = (QosBandwidth) parcel.readTypedObject(QosBandwidth.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.qfi = parcel.readByte();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.averagingWindowMs = (char) parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.averagingWindowMillis = parcel.readInt();
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
        stringJoiner.add("fiveQi: " + this.fiveQi);
        stringJoiner.add("downlink: " + Objects.toString(this.downlink));
        stringJoiner.add("uplink: " + Objects.toString(this.uplink));
        stringJoiner.add("qfi: " + ((int) this.qfi));
        stringJoiner.add("averagingWindowMs: " + this.averagingWindowMs);
        stringJoiner.add("averagingWindowMillis: " + this.averagingWindowMillis);
        return "NrQos" + stringJoiner.toString();
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
