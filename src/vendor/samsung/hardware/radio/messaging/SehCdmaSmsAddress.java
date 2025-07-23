package vendor.samsung.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehCdmaSmsAddress implements Parcelable {
    public static final Parcelable.Creator<SehCdmaSmsAddress> CREATOR = new Parcelable.Creator<SehCdmaSmsAddress>() { // from class: vendor.samsung.hardware.radio.messaging.SehCdmaSmsAddress.1
        @Override // android.os.Parcelable.Creator
        public SehCdmaSmsAddress createFromParcel(Parcel parcel) {
            SehCdmaSmsAddress sehCdmaSmsAddress = new SehCdmaSmsAddress();
            sehCdmaSmsAddress.readFromParcel(parcel);
            return sehCdmaSmsAddress;
        }

        @Override // android.os.Parcelable.Creator
        public SehCdmaSmsAddress[] newArray(int i) {
            return new SehCdmaSmsAddress[i];
        }
    };
    public static final int DIGIT_MODE_EIGHT_BIT = 1;
    public static final int DIGIT_MODE_FOUR_BIT = 0;
    public static final int NUMBER_PLAN_DATA = 3;
    public static final int NUMBER_PLAN_PRIVATE = 9;
    public static final int NUMBER_PLAN_RESERVED_10 = 10;
    public static final int NUMBER_PLAN_RESERVED_11 = 11;
    public static final int NUMBER_PLAN_RESERVED_12 = 12;
    public static final int NUMBER_PLAN_RESERVED_13 = 13;
    public static final int NUMBER_PLAN_RESERVED_14 = 14;
    public static final int NUMBER_PLAN_RESERVED_15 = 15;
    public static final int NUMBER_PLAN_RESERVED_2 = 2;
    public static final int NUMBER_PLAN_RESERVED_5 = 5;
    public static final int NUMBER_PLAN_RESERVED_6 = 6;
    public static final int NUMBER_PLAN_RESERVED_7 = 7;
    public static final int NUMBER_PLAN_RESERVED_8 = 8;
    public static final int NUMBER_PLAN_TELEPHONY = 1;
    public static final int NUMBER_PLAN_TELEX = 4;
    public static final int NUMBER_PLAN_UNKNOWN = 0;
    public static final int NUMBER_TYPE_ABBREVIATED = 6;
    public static final int NUMBER_TYPE_ALPHANUMERIC = 5;
    public static final int NUMBER_TYPE_INTERNATIONAL_OR_DATA_IP = 1;
    public static final int NUMBER_TYPE_NATIONAL_OR_INTERNET_MAIL = 2;
    public static final int NUMBER_TYPE_NETWORK = 3;
    public static final int NUMBER_TYPE_RESERVED_7 = 7;
    public static final int NUMBER_TYPE_SUBSCRIBER = 4;
    public static final int NUMBER_TYPE_UNKNOWN = 0;
    public byte[] digits;
    public int digitMode = 0;
    public boolean isNumberModeDataNetwork = false;
    public int numberType = 0;
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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.digitMode);
        parcel.writeBoolean(this.isNumberModeDataNetwork);
        parcel.writeInt(this.numberType);
        parcel.writeInt(this.numberPlan);
        parcel.writeByteArray(this.digits);
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
                this.digitMode = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.isNumberModeDataNetwork = parcel.readBoolean();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.numberType = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.numberPlan = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.digits = parcel.createByteArray();
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
        stringJoiner.add("digitMode: " + this.digitMode);
        stringJoiner.add("isNumberModeDataNetwork: " + this.isNumberModeDataNetwork);
        stringJoiner.add("numberType: " + this.numberType);
        stringJoiner.add("numberPlan: " + this.numberPlan);
        stringJoiner.add("digits: " + Arrays.toString(this.digits));
        return "SehCdmaSmsAddress" + stringJoiner.toString();
    }
}
