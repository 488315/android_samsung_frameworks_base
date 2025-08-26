package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendDvbcCapabilities implements Parcelable {
    public static final Parcelable.Creator<FrontendDvbcCapabilities> CREATOR = new Parcelable.Creator<FrontendDvbcCapabilities>() { // from class: android.hardware.tv.tuner.FrontendDvbcCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDvbcCapabilities createFromParcel(Parcel parcel) {
            FrontendDvbcCapabilities frontendDvbcCapabilities = new FrontendDvbcCapabilities();
            frontendDvbcCapabilities.readFromParcel(parcel);
            return frontendDvbcCapabilities;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDvbcCapabilities[] newArray(int i) {
            return new FrontendDvbcCapabilities[i];
        }
    };
    public int modulationCap = 0;
    public long fecCap = 0;
    public byte annexCap = 0;

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
        parcel.writeInt(this.modulationCap);
        parcel.writeLong(this.fecCap);
        parcel.writeByte(this.annexCap);
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
                this.modulationCap = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.fecCap = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.annexCap = parcel.readByte();
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
}
