package android.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CarrierInfo implements Parcelable {
    public static final Parcelable.Creator<CarrierInfo> CREATOR = new Parcelable.Creator<CarrierInfo>() { // from class: android.hardware.radio.sim.CarrierInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CarrierInfo createFromParcel(Parcel parcel) {
            CarrierInfo carrierInfo = new CarrierInfo();
            carrierInfo.readFromParcel(parcel);
            return carrierInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CarrierInfo[] newArray(int i) {
            return new CarrierInfo[i];
        }
    };
    public List<Plmn> ehplmn;
    public String gid1;
    public String gid2;
    public String iccid;
    public String impi;
    public String imsiPrefix;
    public String mcc;
    public String mnc;
    public String spn;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.mcc);
        parcel.writeString(this.mnc);
        parcel.writeString(this.spn);
        parcel.writeString(this.gid1);
        parcel.writeString(this.gid2);
        parcel.writeString(this.imsiPrefix);
        parcel.writeTypedList(this.ehplmn, i);
        parcel.writeString(this.iccid);
        parcel.writeString(this.impi);
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
                this.mcc = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.mnc = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.spn = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.gid1 = parcel.readString();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.gid2 = parcel.readString();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.imsiPrefix = parcel.readString();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.ehplmn = parcel.createTypedArrayList(Plmn.CREATOR);
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.iccid = parcel.readString();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.impi = parcel.readString();
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
        stringJoiner.add("mcc: " + Objects.toString(this.mcc));
        stringJoiner.add("mnc: " + Objects.toString(this.mnc));
        stringJoiner.add("spn: " + Objects.toString(this.spn));
        stringJoiner.add("gid1: " + Objects.toString(this.gid1));
        stringJoiner.add("gid2: " + Objects.toString(this.gid2));
        stringJoiner.add("imsiPrefix: " + Objects.toString(this.imsiPrefix));
        stringJoiner.add("ehplmn: " + Objects.toString(this.ehplmn));
        stringJoiner.add("iccid: " + Objects.toString(this.iccid));
        stringJoiner.add("impi: " + Objects.toString(this.impi));
        return "CarrierInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.ehplmn);
    }

    private int describeContents(Object obj) {
        int iDescribeContents = 0;
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Collection) {
            Iterator it = ((Collection) obj).iterator();
            while (it.hasNext()) {
                iDescribeContents |= describeContents(it.next());
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
