package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class LteVopsInfo implements Parcelable {
    public static final Parcelable.Creator<LteVopsInfo> CREATOR = new Parcelable.Creator<LteVopsInfo>() { // from class: android.hardware.radio.network.LteVopsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LteVopsInfo createFromParcel(Parcel parcel) {
            LteVopsInfo lteVopsInfo = new LteVopsInfo();
            lteVopsInfo.readFromParcel(parcel);
            return lteVopsInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LteVopsInfo[] newArray(int i) {
            return new LteVopsInfo[i];
        }
    };
    public boolean isVopsSupported = false;
    public boolean isEmcBearerSupported = false;

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
        parcel.writeBoolean(this.isVopsSupported);
        parcel.writeBoolean(this.isEmcBearerSupported);
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
                this.isVopsSupported = parcel.readBoolean();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.isEmcBearerSupported = parcel.readBoolean();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
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
        stringJoiner.add("isVopsSupported: " + this.isVopsSupported);
        stringJoiner.add("isEmcBearerSupported: " + this.isEmcBearerSupported);
        return "LteVopsInfo" + stringJoiner.toString();
    }
}
