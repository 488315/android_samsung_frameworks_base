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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.plmn);
        parcel.writeByte(this.rat);
        parcel.writeByte(this.regState);
        parcel.writeByte(this.opResult);
        parcel.writeByteArray(this.date);
        parcel.writeTypedArray(this.arfcnInfo, i);
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
                this.plmn = parcel.createByteArray();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.rat = parcel.readByte();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.regState = parcel.readByte();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.opResult = parcel.readByte();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.date = parcel.createByteArray();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.arfcnInfo = (SehArfcnInfo[]) parcel.createTypedArray(SehArfcnInfo.CREATOR);
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
