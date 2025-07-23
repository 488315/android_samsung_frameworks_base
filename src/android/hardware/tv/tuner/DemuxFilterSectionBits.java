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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.filter);
        parcel.writeByteArray(this.mask);
        parcel.writeByteArray(this.mode);
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
                this.filter = parcel.createByteArray();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.mask = parcel.createByteArray();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.mode = parcel.createByteArray();
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
