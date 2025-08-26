package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class BarringTypeSpecificInfo implements Parcelable {
    public static final Parcelable.Creator<BarringTypeSpecificInfo> CREATOR = new Parcelable.Creator<BarringTypeSpecificInfo>() { // from class: android.hardware.radio.network.BarringTypeSpecificInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BarringTypeSpecificInfo createFromParcel(Parcel parcel) {
            BarringTypeSpecificInfo barringTypeSpecificInfo = new BarringTypeSpecificInfo();
            barringTypeSpecificInfo.readFromParcel(parcel);
            return barringTypeSpecificInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BarringTypeSpecificInfo[] newArray(int i) {
            return new BarringTypeSpecificInfo[i];
        }
    };
    public int factor = 0;
    public int timeSeconds = 0;
    public boolean isBarred = false;

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
        parcel.writeInt(this.factor);
        parcel.writeInt(this.timeSeconds);
        parcel.writeBoolean(this.isBarred);
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
                this.factor = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.timeSeconds = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.isBarred = parcel.readBoolean();
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
        stringJoiner.add("factor: " + this.factor);
        stringJoiner.add("timeSeconds: " + this.timeSeconds);
        stringJoiner.add("isBarred: " + this.isBarred);
        return "BarringTypeSpecificInfo" + stringJoiner.toString();
    }
}
