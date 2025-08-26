package android.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CdmaSmsAddress implements Parcelable {
    public static final Parcelable.Creator<CdmaSmsAddress> CREATOR = new Parcelable.Creator<CdmaSmsAddress>() { // from class: android.hardware.radio.messaging.CdmaSmsAddress.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaSmsAddress createFromParcel(Parcel parcel) {
            CdmaSmsAddress cdmaSmsAddress = new CdmaSmsAddress();
            cdmaSmsAddress.readFromParcel(parcel);
            return cdmaSmsAddress;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaSmsAddress[] newArray(int i) {
            return new CdmaSmsAddress[i];
        }
    };

    @Deprecated
    public static final int DIGIT_MODE_EIGHT_BIT = 1;

    @Deprecated
    public static final int DIGIT_MODE_FOUR_BIT = 0;

    @Deprecated
    public static final int NUMBER_PLAN_DATA = 3;

    @Deprecated
    public static final int NUMBER_PLAN_PRIVATE = 9;

    @Deprecated
    public static final int NUMBER_PLAN_RESERVED_10 = 10;

    @Deprecated
    public static final int NUMBER_PLAN_RESERVED_11 = 11;

    @Deprecated
    public static final int NUMBER_PLAN_RESERVED_12 = 12;

    @Deprecated
    public static final int NUMBER_PLAN_RESERVED_13 = 13;

    @Deprecated
    public static final int NUMBER_PLAN_RESERVED_14 = 14;

    @Deprecated
    public static final int NUMBER_PLAN_RESERVED_15 = 15;

    @Deprecated
    public static final int NUMBER_PLAN_RESERVED_2 = 2;

    @Deprecated
    public static final int NUMBER_PLAN_RESERVED_5 = 5;

    @Deprecated
    public static final int NUMBER_PLAN_RESERVED_6 = 6;

    @Deprecated
    public static final int NUMBER_PLAN_RESERVED_7 = 7;

    @Deprecated
    public static final int NUMBER_PLAN_RESERVED_8 = 8;

    @Deprecated
    public static final int NUMBER_PLAN_TELEPHONY = 1;

    @Deprecated
    public static final int NUMBER_PLAN_TELEX = 4;

    @Deprecated
    public static final int NUMBER_PLAN_UNKNOWN = 0;

    @Deprecated
    public static final int NUMBER_TYPE_ABBREVIATED = 6;

    @Deprecated
    public static final int NUMBER_TYPE_ALPHANUMERIC = 5;

    @Deprecated
    public static final int NUMBER_TYPE_INTERNATIONAL_OR_DATA_IP = 1;

    @Deprecated
    public static final int NUMBER_TYPE_NATIONAL_OR_INTERNET_MAIL = 2;

    @Deprecated
    public static final int NUMBER_TYPE_NETWORK = 3;

    @Deprecated
    public static final int NUMBER_TYPE_RESERVED_7 = 7;

    @Deprecated
    public static final int NUMBER_TYPE_SUBSCRIBER = 4;

    @Deprecated
    public static final int NUMBER_TYPE_UNKNOWN = 0;

    @Deprecated
    public byte[] digits;

    @Deprecated
    public int digitMode = 0;

    @Deprecated
    public boolean isNumberModeDataNetwork = false;

    @Deprecated
    public int numberType = 0;

    @Deprecated
    public int numberPlan = 0;

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
        parcel.writeInt(this.digitMode);
        parcel.writeBoolean(this.isNumberModeDataNetwork);
        parcel.writeInt(this.numberType);
        parcel.writeInt(this.numberPlan);
        parcel.writeByteArray(this.digits);
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
                this.digitMode = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.isNumberModeDataNetwork = parcel.readBoolean();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.numberType = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.numberPlan = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.digits = parcel.createByteArray();
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
        stringJoiner.add("digitMode: " + this.digitMode);
        stringJoiner.add("isNumberModeDataNetwork: " + this.isNumberModeDataNetwork);
        stringJoiner.add("numberType: " + this.numberType);
        stringJoiner.add("numberPlan: " + this.numberPlan);
        stringJoiner.add("digits: " + Arrays.toString(this.digits));
        return "CdmaSmsAddress" + stringJoiner.toString();
    }
}
