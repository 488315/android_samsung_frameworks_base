package android.hardware.radio.modem;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class ImeiInfo implements Parcelable {
    public static final Parcelable.Creator<ImeiInfo> CREATOR = new Parcelable.Creator<ImeiInfo>() { // from class: android.hardware.radio.modem.ImeiInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImeiInfo createFromParcel(Parcel parcel) {
            ImeiInfo imeiInfo = new ImeiInfo();
            imeiInfo.readFromParcel(parcel);
            return imeiInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImeiInfo[] newArray(int i) {
            return new ImeiInfo[i];
        }
    };
    public String imei;
    public String svn;
    public int type = 0;

    public @interface ImeiType {
        public static final int INVALID = 0;
        public static final int PRIMARY = 1;
        public static final int SECONDARY = 2;
    }

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
        parcel.writeInt(this.type);
        parcel.writeString(this.imei);
        parcel.writeString(this.svn);
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
                this.type = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.imei = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.svn = parcel.readString();
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
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("imei: " + Objects.toString(this.imei));
        stringJoiner.add("svn: " + Objects.toString(this.svn));
        return "ImeiInfo" + stringJoiner.toString();
    }
}
