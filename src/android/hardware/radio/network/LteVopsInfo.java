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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.isVopsSupported);
        parcel.writeBoolean(this.isEmcBearerSupported);
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
                this.isVopsSupported = parcel.readBoolean();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.isEmcBearerSupported = parcel.readBoolean();
                    if (iDataPosition > Integer.MAX_VALUE - i) {
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
        stringJoiner.add("isVopsSupported: " + this.isVopsSupported);
        stringJoiner.add("isEmcBearerSupported: " + this.isEmcBearerSupported);
        return "LteVopsInfo" + stringJoiner.toString();
    }
}
