package android.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CarrierRestrictions implements Parcelable {
    public static final Parcelable.Creator<CarrierRestrictions> CREATOR = new Parcelable.Creator<CarrierRestrictions>() { // from class: android.hardware.radio.sim.CarrierRestrictions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CarrierRestrictions createFromParcel(Parcel parcel) {
            CarrierRestrictions carrierRestrictions = new CarrierRestrictions();
            carrierRestrictions.readFromParcel(parcel);
            return carrierRestrictions;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CarrierRestrictions[] newArray(int i) {
            return new CarrierRestrictions[i];
        }
    };

    @Deprecated
    public Carrier[] allowedCarriers;

    @Deprecated
    public Carrier[] excludedCarriers;
    public boolean allowedCarriersPrioritized = false;
    public int status = 0;
    public CarrierInfo[] allowedCarrierInfoList = new CarrierInfo[0];
    public CarrierInfo[] excludedCarrierInfoList = new CarrierInfo[0];

    public @interface CarrierRestrictionStatus {
        public static final int NOT_RESTRICTED = 1;
        public static final int RESTRICTED = 2;
        public static final int UNKNOWN = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.allowedCarriers, i);
        parcel.writeTypedArray(this.excludedCarriers, i);
        parcel.writeBoolean(this.allowedCarriersPrioritized);
        parcel.writeInt(this.status);
        parcel.writeTypedArray(this.allowedCarrierInfoList, i);
        parcel.writeTypedArray(this.excludedCarrierInfoList, i);
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
                this.allowedCarriers = (Carrier[]) parcel.createTypedArray(Carrier.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.excludedCarriers = (Carrier[]) parcel.createTypedArray(Carrier.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.allowedCarriersPrioritized = parcel.readBoolean();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.status = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.allowedCarrierInfoList = (CarrierInfo[]) parcel.createTypedArray(CarrierInfo.CREATOR);
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.excludedCarrierInfoList = (CarrierInfo[]) parcel.createTypedArray(CarrierInfo.CREATOR);
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
        stringJoiner.add("allowedCarriers: " + Arrays.toString(this.allowedCarriers));
        stringJoiner.add("excludedCarriers: " + Arrays.toString(this.excludedCarriers));
        stringJoiner.add("allowedCarriersPrioritized: " + this.allowedCarriersPrioritized);
        stringJoiner.add("status: " + this.status);
        stringJoiner.add("allowedCarrierInfoList: " + Arrays.toString(this.allowedCarrierInfoList));
        stringJoiner.add("excludedCarrierInfoList: " + Arrays.toString(this.excludedCarrierInfoList));
        return "CarrierRestrictions" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.excludedCarrierInfoList) | describeContents(this.allowedCarriers) | describeContents(this.excludedCarriers) | describeContents(this.allowedCarrierInfoList);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
