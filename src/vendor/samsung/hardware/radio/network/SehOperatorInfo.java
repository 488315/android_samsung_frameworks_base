package vendor.samsung.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehOperatorInfo implements Parcelable {
    public static final Parcelable.Creator<SehOperatorInfo> CREATOR = new Parcelable.Creator<SehOperatorInfo>() { // from class: vendor.samsung.hardware.radio.network.SehOperatorInfo.1
        @Override // android.os.Parcelable.Creator
        public SehOperatorInfo createFromParcel(Parcel parcel) {
            SehOperatorInfo sehOperatorInfo = new SehOperatorInfo();
            sehOperatorInfo.readFromParcel(parcel);
            return sehOperatorInfo;
        }

        @Override // android.os.Parcelable.Creator
        public SehOperatorInfo[] newArray(int i) {
            return new SehOperatorInfo[i];
        }
    };
    public static final int STATUS_AVAILABLE = 1;
    public static final int STATUS_CURRENT = 2;
    public static final int STATUS_FORBIDDEN = 3;
    public static final int STATUS_UNKNOWN = 0;
    public String alphaLong;
    public String alphaShort;
    public String lac;
    public String operatorNumeric;
    public String rat;
    public int status = 0;

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
        parcel.writeString(this.alphaLong);
        parcel.writeString(this.alphaShort);
        parcel.writeString(this.operatorNumeric);
        parcel.writeInt(this.status);
        parcel.writeString(this.rat);
        parcel.writeString(this.lac);
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
                this.alphaLong = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.alphaShort = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.operatorNumeric = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.status = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.rat = parcel.readString();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.lac = parcel.readString();
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
        stringJoiner.add("alphaLong: " + Objects.toString(this.alphaLong));
        stringJoiner.add("alphaShort: " + Objects.toString(this.alphaShort));
        stringJoiner.add("operatorNumeric: " + Objects.toString(this.operatorNumeric));
        stringJoiner.add("status: " + this.status);
        stringJoiner.add("rat: " + Objects.toString(this.rat));
        stringJoiner.add("lac: " + Objects.toString(this.lac));
        return "SehOperatorInfo" + stringJoiner.toString();
    }
}
