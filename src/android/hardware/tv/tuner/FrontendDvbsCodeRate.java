package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendDvbsCodeRate implements Parcelable {
    public static final Parcelable.Creator<FrontendDvbsCodeRate> CREATOR = new Parcelable.Creator<FrontendDvbsCodeRate>() { // from class: android.hardware.tv.tuner.FrontendDvbsCodeRate.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDvbsCodeRate createFromParcel(Parcel parcel) {
            FrontendDvbsCodeRate frontendDvbsCodeRate = new FrontendDvbsCodeRate();
            frontendDvbsCodeRate.readFromParcel(parcel);
            return frontendDvbsCodeRate;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDvbsCodeRate[] newArray(int i) {
            return new FrontendDvbsCodeRate[i];
        }
    };
    public long fec = 0;
    public boolean isLinear = false;
    public boolean isShortFrames = false;
    public int bitsPer1000Symbol = 0;

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
        parcel.writeLong(this.fec);
        parcel.writeBoolean(this.isLinear);
        parcel.writeBoolean(this.isShortFrames);
        parcel.writeInt(this.bitsPer1000Symbol);
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
                this.fec = parcel.readLong();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.isLinear = parcel.readBoolean();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.isShortFrames = parcel.readBoolean();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.bitsPer1000Symbol = parcel.readInt();
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
}
