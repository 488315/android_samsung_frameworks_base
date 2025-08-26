package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxFilterTemiEvent implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterTemiEvent> CREATOR = new Parcelable.Creator<DemuxFilterTemiEvent>() { // from class: android.hardware.tv.tuner.DemuxFilterTemiEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterTemiEvent createFromParcel(Parcel parcel) {
            DemuxFilterTemiEvent demuxFilterTemiEvent = new DemuxFilterTemiEvent();
            demuxFilterTemiEvent.readFromParcel(parcel);
            return demuxFilterTemiEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterTemiEvent[] newArray(int i) {
            return new DemuxFilterTemiEvent[i];
        }
    };
    public byte[] descrData;
    public long pts = 0;
    public byte descrTag = 0;

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
        parcel.writeLong(this.pts);
        parcel.writeByte(this.descrTag);
        parcel.writeByteArray(this.descrData);
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
                this.pts = parcel.readLong();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.descrTag = parcel.readByte();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.descrData = parcel.createByteArray();
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
