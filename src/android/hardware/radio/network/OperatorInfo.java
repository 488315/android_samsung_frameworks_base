package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class OperatorInfo implements Parcelable {
    public static final Parcelable.Creator<OperatorInfo> CREATOR = new Parcelable.Creator<OperatorInfo>() { // from class: android.hardware.radio.network.OperatorInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OperatorInfo createFromParcel(Parcel parcel) {
            OperatorInfo operatorInfo = new OperatorInfo();
            operatorInfo.readFromParcel(parcel);
            return operatorInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OperatorInfo[] newArray(int i) {
            return new OperatorInfo[i];
        }
    };
    public static final int STATUS_AVAILABLE = 1;
    public static final int STATUS_CURRENT = 2;
    public static final int STATUS_FORBIDDEN = 3;
    public static final int STATUS_UNKNOWN = 0;
    public String alphaLong;
    public String alphaShort;
    public String operatorNumeric;
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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.alphaLong);
        parcel.writeString(this.alphaShort);
        parcel.writeString(this.operatorNumeric);
        parcel.writeInt(this.status);
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
                this.alphaLong = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.alphaShort = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.operatorNumeric = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.status = parcel.readInt();
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
        stringJoiner.add("alphaLong: " + Objects.toString(this.alphaLong));
        stringJoiner.add("alphaShort: " + Objects.toString(this.alphaShort));
        stringJoiner.add("operatorNumeric: " + Objects.toString(this.operatorNumeric));
        stringJoiner.add("status: " + this.status);
        return "OperatorInfo" + stringJoiner.toString();
    }
}
