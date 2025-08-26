package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxFilterSectionBits implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterSectionBits> CREATOR = new Parcelable.Creator<DemuxFilterSectionBits>() { // from class: android.hardware.tv.tuner.DemuxFilterSectionBits.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterSectionBits createFromParcel(Parcel parcel) {
            DemuxFilterSectionBits demuxFilterSectionBits = new DemuxFilterSectionBits();
            demuxFilterSectionBits.readFromParcel(parcel);
            return demuxFilterSectionBits;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterSectionBits[] newArray(int i) {
            return new DemuxFilterSectionBits[i];
        }
    };
    public byte[] filter;
    public byte[] mask;
    public byte[] mode;

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
        parcel.writeByteArray(this.filter);
        parcel.writeByteArray(this.mask);
        parcel.writeByteArray(this.mode);
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
                this.filter = parcel.createByteArray();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.mask = parcel.createByteArray();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.mode = parcel.createByteArray();
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
