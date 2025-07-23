package vendor.samsung.hardware.radio.satellite;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehSatSimAuthReqData implements Parcelable {
    public static final Parcelable.Creator<SehSatSimAuthReqData> CREATOR = new Parcelable.Creator<SehSatSimAuthReqData>() { // from class: vendor.samsung.hardware.radio.satellite.SehSatSimAuthReqData.1
        @Override // android.os.Parcelable.Creator
        public SehSatSimAuthReqData createFromParcel(Parcel parcel) {
            SehSatSimAuthReqData sehSatSimAuthReqData = new SehSatSimAuthReqData();
            sehSatSimAuthReqData.readFromParcel(parcel);
            return sehSatSimAuthReqData;
        }

        @Override // android.os.Parcelable.Creator
        public SehSatSimAuthReqData[] newArray(int i) {
            return new SehSatSimAuthReqData[i];
        }
    };
    public String auth;
    public String rand;
    public int randLen = 0;
    public int authLen = 0;

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
        parcel.writeInt(this.randLen);
        parcel.writeString(this.rand);
        parcel.writeInt(this.authLen);
        parcel.writeString(this.auth);
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
                this.randLen = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.rand = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.authLen = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.auth = parcel.readString();
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
        stringJoiner.add("randLen: " + this.randLen);
        stringJoiner.add("rand: " + Objects.toString(this.rand));
        stringJoiner.add("authLen: " + this.authLen);
        stringJoiner.add("auth: " + Objects.toString(this.auth));
        return "SehSatSimAuthReqData" + stringJoiner.toString();
    }
}
