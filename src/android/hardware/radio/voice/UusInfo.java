package android.hardware.radio.voice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class UusInfo implements Parcelable {
    public static final Parcelable.Creator<UusInfo> CREATOR = new Parcelable.Creator<UusInfo>() { // from class: android.hardware.radio.voice.UusInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UusInfo createFromParcel(Parcel parcel) {
            UusInfo uusInfo = new UusInfo();
            uusInfo.readFromParcel(parcel);
            return uusInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UusInfo[] newArray(int i) {
            return new UusInfo[i];
        }
    };
    public static final int UUS_DCS_IA5C = 4;
    public static final int UUS_DCS_OSIHLP = 1;
    public static final int UUS_DCS_RMCF = 3;
    public static final int UUS_DCS_USP = 0;
    public static final int UUS_DCS_X244 = 2;
    public static final int UUS_TYPE_TYPE1_IMPLICIT = 0;
    public static final int UUS_TYPE_TYPE1_NOT_REQUIRED = 2;
    public static final int UUS_TYPE_TYPE1_REQUIRED = 1;
    public static final int UUS_TYPE_TYPE2_NOT_REQUIRED = 4;
    public static final int UUS_TYPE_TYPE2_REQUIRED = 3;
    public static final int UUS_TYPE_TYPE3_NOT_REQUIRED = 6;
    public static final int UUS_TYPE_TYPE3_REQUIRED = 5;
    public String uusData;
    public int uusType = 0;
    public int uusDcs = 0;

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
        parcel.writeInt(this.uusType);
        parcel.writeInt(this.uusDcs);
        parcel.writeString(this.uusData);
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
                this.uusType = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.uusDcs = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.uusData = parcel.readString();
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
        stringJoiner.add("uusType: " + this.uusType);
        stringJoiner.add("uusDcs: " + this.uusDcs);
        stringJoiner.add("uusData: " + Objects.toString(this.uusData));
        return "UusInfo" + stringJoiner.toString();
    }
}
