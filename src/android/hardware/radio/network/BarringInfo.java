package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class BarringInfo implements Parcelable {
    public static final int BARRING_TYPE_CONDITIONAL = 1;
    public static final int BARRING_TYPE_NONE = 0;
    public static final int BARRING_TYPE_UNCONDITIONAL = 2;
    public static final Parcelable.Creator<BarringInfo> CREATOR = new Parcelable.Creator<BarringInfo>() { // from class: android.hardware.radio.network.BarringInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BarringInfo createFromParcel(Parcel parcel) {
            BarringInfo barringInfo = new BarringInfo();
            barringInfo.readFromParcel(parcel);
            return barringInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BarringInfo[] newArray(int i) {
            return new BarringInfo[i];
        }
    };
    public static final int SERVICE_TYPE_CS_FALLBACK = 5;
    public static final int SERVICE_TYPE_CS_SERVICE = 0;
    public static final int SERVICE_TYPE_CS_VOICE = 2;
    public static final int SERVICE_TYPE_EMERGENCY = 8;
    public static final int SERVICE_TYPE_MMTEL_VIDEO = 7;
    public static final int SERVICE_TYPE_MMTEL_VOICE = 6;
    public static final int SERVICE_TYPE_MO_DATA = 4;
    public static final int SERVICE_TYPE_MO_SIGNALLING = 3;
    public static final int SERVICE_TYPE_OPERATOR_1 = 1001;
    public static final int SERVICE_TYPE_OPERATOR_10 = 1010;
    public static final int SERVICE_TYPE_OPERATOR_11 = 1011;
    public static final int SERVICE_TYPE_OPERATOR_12 = 1012;
    public static final int SERVICE_TYPE_OPERATOR_13 = 1013;
    public static final int SERVICE_TYPE_OPERATOR_14 = 1014;
    public static final int SERVICE_TYPE_OPERATOR_15 = 1015;
    public static final int SERVICE_TYPE_OPERATOR_16 = 1016;
    public static final int SERVICE_TYPE_OPERATOR_17 = 1017;
    public static final int SERVICE_TYPE_OPERATOR_18 = 1018;
    public static final int SERVICE_TYPE_OPERATOR_19 = 1019;
    public static final int SERVICE_TYPE_OPERATOR_2 = 1002;
    public static final int SERVICE_TYPE_OPERATOR_20 = 1020;
    public static final int SERVICE_TYPE_OPERATOR_21 = 1021;
    public static final int SERVICE_TYPE_OPERATOR_22 = 1022;
    public static final int SERVICE_TYPE_OPERATOR_23 = 1023;
    public static final int SERVICE_TYPE_OPERATOR_24 = 1024;
    public static final int SERVICE_TYPE_OPERATOR_25 = 1025;
    public static final int SERVICE_TYPE_OPERATOR_26 = 1026;
    public static final int SERVICE_TYPE_OPERATOR_27 = 1027;
    public static final int SERVICE_TYPE_OPERATOR_28 = 1028;
    public static final int SERVICE_TYPE_OPERATOR_29 = 1029;
    public static final int SERVICE_TYPE_OPERATOR_3 = 1003;
    public static final int SERVICE_TYPE_OPERATOR_30 = 1030;
    public static final int SERVICE_TYPE_OPERATOR_31 = 1031;
    public static final int SERVICE_TYPE_OPERATOR_32 = 1032;
    public static final int SERVICE_TYPE_OPERATOR_4 = 1004;
    public static final int SERVICE_TYPE_OPERATOR_5 = 1005;
    public static final int SERVICE_TYPE_OPERATOR_6 = 1006;
    public static final int SERVICE_TYPE_OPERATOR_7 = 1007;
    public static final int SERVICE_TYPE_OPERATOR_8 = 1008;
    public static final int SERVICE_TYPE_OPERATOR_9 = 1009;
    public static final int SERVICE_TYPE_PS_SERVICE = 1;
    public static final int SERVICE_TYPE_SMS = 9;
    public BarringTypeSpecificInfo barringTypeSpecificInfo;
    public int serviceType = 0;
    public int barringType = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.serviceType);
        parcel.writeInt(this.barringType);
        parcel.writeTypedObject(this.barringTypeSpecificInfo, i);
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
                this.serviceType = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.barringType = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.barringTypeSpecificInfo = (BarringTypeSpecificInfo) parcel.readTypedObject(BarringTypeSpecificInfo.CREATOR);
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
        stringJoiner.add("serviceType: " + this.serviceType);
        stringJoiner.add("barringType: " + this.barringType);
        stringJoiner.add("barringTypeSpecificInfo: " + Objects.toString(this.barringTypeSpecificInfo));
        return "BarringInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.barringTypeSpecificInfo);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
