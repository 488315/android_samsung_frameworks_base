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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.modulationCap);
        parcel.writeLong(this.fecCap);
        parcel.writeByte(this.annexCap);
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
                this.modulationCap = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.fecCap = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.annexCap = parcel.readByte();
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
}
