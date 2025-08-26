package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendDvbsCapabilities implements Parcelable {
    public static final Parcelable.Creator<FrontendDvbsCapabilities> CREATOR = new Parcelable.Creator<FrontendDvbsCapabilities>() { // from class: android.hardware.tv.tuner.FrontendDvbsCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDvbsCapabilities createFromParcel(Parcel parcel) {
            FrontendDvbsCapabilities frontendDvbsCapabilities = new FrontendDvbsCapabilities();
            frontendDvbsCapabilities.readFromParcel(parcel);
            return frontendDvbsCapabilities;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDvbsCapabilities[] newArray(int i) {
            return new FrontendDvbsCapabilities[i];
        }
    };
    public int modulationCap = 0;
    public long innerfecCap = 0;
    public byte standard = 0;

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
        parcel.writeLong(this.innerfecCap);
        parcel.writeByte(this.standard);
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
                    this.innerfecCap = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.standard = parcel.readByte();
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
