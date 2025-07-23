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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.allowedCarriers, i);
        parcel.writeTypedArray(this.excludedCarriers, i);
        parcel.writeBoolean(this.allowedCarriersPrioritized);
        parcel.writeInt(this.status);
        parcel.writeTypedArray(this.allowedCarrierInfoList, i);
        parcel.writeTypedArray(this.excludedCarrierInfoList, i);
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
                this.allowedCarriers = (Carrier[]) parcel.createTypedArray(Carrier.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.excludedCarriers = (Carrier[]) parcel.createTypedArray(Carrier.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.allowedCarriersPrioritized = parcel.readBoolean();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.status = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.allowedCarrierInfoList = (CarrierInfo[]) parcel.createTypedArray(CarrierInfo.CREATOR);
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.excludedCarrierInfoList = (CarrierInfo[]) parcel.createTypedArray(CarrierInfo.CREATOR);
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
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
