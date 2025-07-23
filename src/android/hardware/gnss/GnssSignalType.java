package android.hardware.gnss;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class GnssSignalType implements Parcelable {
    public static final String CODE_TYPE_A = "A";
    public static final String CODE_TYPE_B = "B";
    public static final String CODE_TYPE_C = "C";
    public static final String CODE_TYPE_D = "D";
    public static final String CODE_TYPE_I = "I";
    public static final String CODE_TYPE_L = "L";
    public static final String CODE_TYPE_M = "M";
    public static final String CODE_TYPE_N = "N";
    public static final String CODE_TYPE_P = "P";
    public static final String CODE_TYPE_Q = "Q";
    public static final String CODE_TYPE_S = "S";
    public static final String CODE_TYPE_UNKNOWN = "UNKNOWN";
    public static final String CODE_TYPE_W = "W";
    public static final String CODE_TYPE_X = "X";
    public static final String CODE_TYPE_Y = "Y";
    public static final String CODE_TYPE_Z = "Z";
    public static final Parcelable.Creator<GnssSignalType> CREATOR = new Parcelable.Creator<GnssSignalType>() { // from class: android.hardware.gnss.GnssSignalType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GnssSignalType createFromParcel(Parcel parcel) {
            GnssSignalType gnssSignalType = new GnssSignalType();
            gnssSignalType.readFromParcel(parcel);
            return gnssSignalType;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GnssSignalType[] newArray(int i) {
            return new GnssSignalType[i];
        }
    };
    public String codeType;
    public int constellation = 0;
    public double carrierFrequencyHz = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

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
        parcel.writeInt(this.constellation);
        parcel.writeDouble(this.carrierFrequencyHz);
        parcel.writeString(this.codeType);
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
                this.constellation = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.carrierFrequencyHz = parcel.readDouble();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.codeType = parcel.readString();
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
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
}
