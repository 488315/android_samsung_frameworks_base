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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.constellation);
        parcel.writeDouble(this.carrierFrequencyHz);
        parcel.writeString(this.codeType);
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
                this.constellation = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.carrierFrequencyHz = parcel.readDouble();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.codeType = parcel.readString();
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
}
