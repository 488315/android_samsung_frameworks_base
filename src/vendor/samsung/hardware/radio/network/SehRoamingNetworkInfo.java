package vendor.samsung.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehRoamingNetworkInfo implements Parcelable {
    public static final Parcelable.Creator<SehRoamingNetworkInfo> CREATOR = new Parcelable.Creator<SehRoamingNetworkInfo>() { // from class: vendor.samsung.hardware.radio.network.SehRoamingNetworkInfo.1
        @Override // android.os.Parcelable.Creator
        public SehRoamingNetworkInfo createFromParcel(Parcel parcel) {
            SehRoamingNetworkInfo sehRoamingNetworkInfo = new SehRoamingNetworkInfo();
            sehRoamingNetworkInfo.readFromParcel(parcel);
            return sehRoamingNetworkInfo;
        }

        @Override // android.os.Parcelable.Creator
        public SehRoamingNetworkInfo[] newArray(int i) {
            return new SehRoamingNetworkInfo[i];
        }
    };
    public SehArfcnInfo[] arfcnInfo;
    public byte[] date;
    public byte[] plmn;
    public byte rat = 0;
    public byte regState = 0;
    public byte opResult = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.plmn);
        parcel.writeByte(this.rat);
        parcel.writeByte(this.regState);
        parcel.writeByte(this.opResult);
        parcel.writeByteArray(this.date);
        parcel.writeTypedArray(this.arfcnInfo, i);
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
                this.plmn = parcel.createByteArray();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.rat = parcel.readByte();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.regState = parcel.readByte();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.opResult = parcel.readByte();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.date = parcel.createByteArray();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.arfcnInfo = (SehArfcnInfo[]) parcel.createTypedArray(SehArfcnInfo.CREATOR);
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
        stringJoiner.add("plmn: " + Arrays.toString(this.plmn));
        stringJoiner.add("rat: " + ((int) this.rat));
        stringJoiner.add("regState: " + ((int) this.regState));
        stringJoiner.add("opResult: " + ((int) this.opResult));
        stringJoiner.add("date: " + Arrays.toString(this.date));
        stringJoiner.add("arfcnInfo: " + Arrays.toString(this.arfcnInfo));
        return "SehRoamingNetworkInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.arfcnInfo);
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
